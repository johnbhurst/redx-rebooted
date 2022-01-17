// Copyright 2022 John Hurst
// John Hurst
// 2022-01-18

package redx.filter

import redx.filter.TokenType.AND
import redx.filter.TokenType.DOT
import redx.filter.TokenType.EOF
import redx.filter.TokenType.EQUALS
import redx.filter.TokenType.GREATER_EQUALS
import redx.filter.TokenType.GREATER_THAN
import redx.filter.TokenType.HAS
import redx.filter.TokenType.IDENTIFIER
import redx.filter.TokenType.LESS_EQUALS
import redx.filter.TokenType.LESS_THAN
import redx.filter.TokenType.LPAREN
import redx.filter.TokenType.MINUS
import redx.filter.TokenType.NOT
import redx.filter.TokenType.NOT_EQUALS
import redx.filter.TokenType.NUMBER
import redx.filter.TokenType.OR
import redx.filter.TokenType.RPAREN

/**
 * @link https://google.aip.dev/160
 * @link https://google.aip.dev/assets/misc/ebnf-filtering.txt
 * @link https://craftinginterpreters.com/
 */
class Scanner(val source: String) {
    val tokens = ArrayList<Token>()
    var start = 1
    var current = 0
    var line = 1
    val keywords = mapOf(
        "and" to AND,
        "or" to OR,
        "not" to NOT,
    )

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            start = current
            scanToken()
        }
        tokens.add(Token(EOF, "", null, line))
        return tokens
    }

    private fun scanToken() {
        val c = advance()
        when (c) {
            '(' -> addToken(LPAREN)
            ')' -> addToken(RPAREN)
            '-' -> addToken(MINUS)
            '.' -> addToken(DOT)
            '<' -> addToken(if (match('=')) LESS_EQUALS else LESS_THAN)
            '>' -> addToken(if (match('=')) GREATER_EQUALS else GREATER_THAN)
            '!' -> addToken(if (match('=')) NOT_EQUALS else error("Expected '=' following '!'")) // TODO: errors
            '=' -> addToken(EQUALS)
            ':' -> addToken(HAS)
            ' ', '\r', '\t' -> {}
            '\n' -> line++
            '"' -> string()
            else -> if (isDigits(c)) {
                number()
            } else if (isAlpha(c)) {
                identifier()
            } else {
                error("Unexpected character.") // TODO: errors
            }

        }
    }

    fun identifier() {
        while (isAlphanumeric(peek())) advance()
        val text = source.substring(start, current)
        val type = keywords.getOrDefault(text, IDENTIFIER)
        addToken(type)
    }

    fun number() {
        while (isDigits(peek())) advance()
        if (peek() == '.' && isDigits(peekNext())) {
            advance() // Consume the '.'
            while (isDigits(peek())) advance()
        }
        addToken(NUMBER, source.substring(start, current).toDouble())
    }

    fun string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++
            advance()
        }
        if (isAtEnd()) {
            error("Unterminated string") // TODO: errors
        }
        advance()
        val value = source.substring(start + 1, current - 1)
        addToken(TokenType.STRING, value)
    }

    fun match(expected: Char): Boolean {
        if (isAtEnd()) return false
        if (source[current] != expected) return false
        current++
        return true
    }

    fun peek() = if (isAtEnd()) '\u0000' else source[current]

    fun peekNext() = if (current + 1 >= source.length) '\u0000' else source[current + 1]

    fun isAlpha(c: Char) = c in 'a' .. 'z' || c in 'A' .. 'Z' || c == '_'

    fun isAlphanumeric(c: Char) = isAlpha(c) || isDigits(c)

    fun isDigits(c: Char) = c in '0'..'9'

    fun isAtEnd(): Boolean = current >= source.length

    fun advance(): Char = source[current++]

    fun addToken(type: TokenType) = addToken(type, null)

    fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }
}