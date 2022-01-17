package redx.filter

import spock.lang.Specification

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
        new Scanner(line).scanTokens() == result + [EOF]

        where:
        line         || result
        "=()-.:"      | [token(TokenType.EQUALS, "="), token(TokenType.LPAREN, "("), token(TokenType.RPAREN, ")"), token(TokenType.MINUS, "-"), token(TokenType.DOT, "."), token(TokenType.HAS, ":")]
        "<=<>=>!="    | [token(TokenType.LESS_EQUALS, "<="), token(TokenType.LESS_THAN, "<"), token(TokenType.GREATER_EQUALS, ">="), token(TokenType.GREATER_THAN, ">"), token(TokenType.NOT_EQUALS, "!=")]
        "five"        | [token(TokenType.IDENTIFIER, "five")]
        "\"six\""     | [token(TokenType.STRING, "\"six\"", "six")]
        "123"         | [token(TokenType.NUMBER, "123", 123.0 as Double)]
        "and or not"  | [token(TokenType.AND, "and"), token(TokenType.OR, "or"), token(TokenType.NOT, "not")]
    }
}
