// Copyright 2022 John Hurst
// John Hurst
// 2022-01-18

package redx.filter

import redx.filter.TokenType.AND
import redx.filter.TokenType.DATE
import redx.filter.TokenType.DATETIME
import redx.filter.TokenType.EOF
import redx.filter.TokenType.EQUALS
import redx.filter.TokenType.GREATER_EQUALS
import redx.filter.TokenType.GREATER_THAN
import redx.filter.TokenType.INTEGER
import redx.filter.TokenType.LESS_EQUALS
import redx.filter.TokenType.LESS_THAN
import redx.filter.TokenType.LPAREN
import redx.filter.TokenType.MINUS
import redx.filter.TokenType.NOT
import redx.filter.TokenType.NOT_EQUALS
import redx.filter.TokenType.OR
import redx.filter.TokenType.RPAREN
import redx.filter.TokenType.STRING
import redx.filter.TokenType.TEXT
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * @link https://google.aip.dev/160
 * @link https://google.aip.dev/assets/misc/ebnf-filtering.txt
 * @link https://craftinginterpreters.com/
 *
 * <pre>
 * expression : sequence {AND sequence}
 * sequence : factor {factor}
 * factor : term {OR term}
 * term : [(NOT | MINUS)] simple
 * simple : restriction | composite
 * composite : '(' expression ')'
 * restriction : value | binary
 * binary : variable operator value
 * variable : identifier {'.' identifier}
 * operator : '=' | '!=' | '<' | '<=' | '>' | '>='
 * value : string : text
 * text : integer | date | timestamp | text_string
 * integer : digit {digit}
 * date : digit{4}'-'digit{2}'-'digit{2}
 * timestamp : digit{4}'-'digit{2}-digit{2}'T'digit{2}:digit{2}:{digit}
 * string : '"' character {character} '"'
 * text_string : (alpha digit '_' '-' '.')+
 * </pre>

 */
class Parser(private val tokens: List<Token>) {

    private var current = 0

    fun parse(): Expr = expression()

    // expression : sequence {AND sequence}
    private fun expression(): Expr {
        val result = sequence()
        if (isAtEnd() || peek().type != AND) {
            return result
        }
        val resultList = mutableListOf(result)
        while (match(AND)) {
            resultList += sequence()
        }
        return Expr.Expression(resultList)
    }

    // sequence : factor {factor}
    private fun sequence(): Expr {
        return factor() // TODO: repeated factors result in Expr.Sequence(resultList)
    }

    // factor : term {OR term}
    private fun factor(): Expr {
        val result = term()
        if (isAtEnd() || peek().type != OR) {
            return result
        }
        val resultList = mutableListOf(result)
        while (match(OR)) {
            resultList += term()
        }
        return Expr.Factor(resultList)
    }

    // term : [(NOT | MINUS)] simple
    private fun term(): Expr {
        return when {
            match(NOT) -> Expr.Not(simple())
            match(MINUS) -> Expr.Not(simple())
            else -> simple()
        }
    }

    // simple : restriction | composite
    private fun simple(): Expr {
        return if (peek().type == LPAREN) {
            composite()
        } else {
            restriction()
        }
    }

    // composite : '(' expression ')'
    private fun composite(): Expr {
        consume(LPAREN, "Expected '('")
        val result = expression()
        consume(RPAREN, "Expected ')'")
        return Expr.Composite(result)
    }

    // restriction : value | binary
    // binary : variable operator value
    private fun restriction(): Expr {
        if (match(STRING)) {
            return Expr.StringRestriction(previous().literal.toString())
        }
        if (match(TEXT)) {
            val lhs = previous().lexeme
            return if (match(EQUALS) || match(NOT_EQUALS) || match(LESS_THAN) || match(LESS_EQUALS) || match(GREATER_THAN) || match(GREATER_EQUALS)) {
                val operator = BinaryOperator.valueOf(previous().type.toString())
                val value = when {
                    match(STRING) -> Value.StringValue(previous().literal.toString())
                    match(TEXT) -> Value.StringValue(previous().lexeme)
                    match(DATETIME) -> Value.DateTimeValue(previous().literal as LocalDateTime)
                    match(DATE) -> Value.DateValue(previous().literal as LocalDate)
                    match(INTEGER) -> Value.IntegerValue(previous().literal as Int)
                    else -> throw RuntimeException("Unexpected value")
                }
                Expr.BinaryRestriction(lhs, operator, value)
            } else {
                Expr.StringRestriction(lhs)
            }
        } else {
            throw RuntimeException("Expected value or binary expression")
        }
    }

    private fun match(vararg types: TokenType): Boolean {
        for (type in types) {
            if (check(type)) {
                advance()
                return true
            }
        }
        return false
    }

    private fun consume(type: TokenType, message: String): Token {
        if (check(type)) return advance()
        throw RuntimeException(message) // TODO: errors
    }

    private fun check(type: TokenType): Boolean = if (isAtEnd()) false else peek().type == type

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    private fun isAtEnd() = peek().type == EOF

    private fun peek() = tokens[current]

    private fun previous() = tokens[current - 1]

}
