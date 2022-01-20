package redx.filter

import spock.lang.Specification

import static redx.filter.TokenType.AND
import static redx.filter.TokenType.COMMA
import static redx.filter.TokenType.DOT
import static redx.filter.TokenType.EQUALS
import static redx.filter.TokenType.GREATER_EQUALS
import static redx.filter.TokenType.GREATER_THAN
import static redx.filter.TokenType.HAS
import static redx.filter.TokenType.LESS_EQUALS
import static redx.filter.TokenType.LESS_THAN
import static redx.filter.TokenType.LPAREN
import static redx.filter.TokenType.MINUS
import static redx.filter.TokenType.NOT
import static redx.filter.TokenType.NOT_EQUALS
import static redx.filter.TokenType.OR
import static redx.filter.TokenType.RPAREN
import static redx.filter.TokenType.STRING
import static redx.filter.TokenType.TEXT

class ScannerTest extends Specification {

    static final Token EOF = new Token(TokenType.EOF, "", null, 1)

    def token(TokenType tokenType, String lexeme) {
        new Token(tokenType, lexeme, null, 1)
    }

    def token(TokenType tokenType, String lexeme, Object literal) {
        new Token(tokenType, lexeme, literal, 1)
    }

    def "Scanner scans tokens"() {
        expect:
        new Scanner(input).scanTokens() == result + [EOF]

        where:
        input           || result
        "=()-,.:"        | [token(EQUALS, "="), token(LPAREN, "("), token(RPAREN, ")"), token(MINUS, "-"), token(COMMA, ","), token(DOT, "."), token(HAS, ":")]
        "<=<>=>!="       | [token(LESS_EQUALS, "<="), token(LESS_THAN, "<"), token(GREATER_EQUALS, ">="), token(GREATER_THAN, ">"), token(NOT_EQUALS, "!=")]
        "\"six\""        | [token(STRING, "\"six\"", "six")]
        "five"           | [token(TEXT, "five")]
        "123"            | [token(TEXT, "123")]
        "this or that"   | [token(TEXT, "this"), token(OR, "or"), token(TEXT, "that")]
        "and or not"     | [token(AND, "and"), token(OR, "or"), token(NOT, "not")]
        "one=two,three"  | [token(TEXT, "one"), token(EQUALS, "="), token(TEXT, "two"), token(COMMA, ","), token(TEXT, "three")]
    }

    def "Unexpected characters"() {
        when:
        new Scanner(input).scanTokens()

        then:
        thrown(RuntimeException)

        where:
        input << ["~", "`", "!", "#"]
    }
}
