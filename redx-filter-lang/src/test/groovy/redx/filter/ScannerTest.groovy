package redx.filter

import spock.lang.Specification

class ScannerTest extends Specification {

    static Token token(TokenType tokenType, String lexeme) {
        new Token(tokenType, lexeme, null, 1)
    }

    static Token token(TokenType tokenType, String lexeme, Object literal) {
        new Token(tokenType, lexeme, literal, 1)
    }

    static Token string(String s) {
        token(TokenType.STRING, "\"$s\"", s)
    }

    static Token text(String s) {
        token(TokenType.TEXT, s)
    }

    static final Token EQUALS = token(TokenType.EQUALS, "=")
    static final Token LPAREN = token(TokenType.LPAREN, "(")
    static final Token RPAREN = token(TokenType.RPAREN, ")")
    static final Token MINUS = token(TokenType.MINUS, "-")
    static final Token COMMA = token(TokenType.COMMA, ",")
    static final Token DOT = token(TokenType.DOT, ".")
    static final Token HAS = token(TokenType.HAS, ":")
    static final Token LESS_EQUALS = token(TokenType.LESS_EQUALS, "<=")
    static final Token LESS_THAN = token(TokenType.LESS_THAN, "<")
    static final Token GREATER_EQUALS = token(TokenType.GREATER_EQUALS, ">=")
    static final Token GREATER_THAN = token(TokenType.GREATER_THAN, ">")
    static final Token NOT_EQUALS = token(TokenType.NOT_EQUALS, "!=")
    static final Token AND = token(TokenType.AND, "and")
    static final Token OR = token(TokenType.OR, "or")
    static final Token NOT = token(TokenType.NOT, "not")
    static final Token EOF = token(TokenType.EOF, "")

    def "Scanner scans tokens"() {
        expect:
        new Scanner(input).scanTokens() == result + [EOF]

        where:
        input           || result
        "=()-,.:"        | [EQUALS, LPAREN, RPAREN, MINUS, COMMA, DOT, HAS]
        "<=<>=>!="       | [LESS_EQUALS, LESS_THAN, GREATER_EQUALS, GREATER_THAN, NOT_EQUALS]
        "\"six\""        | [string("six")]
        "five"           | [text("five")]
        "123"            | [text("123")]
        "this or that"   | [text("this"), OR, text("that")]
        "and or not"     | [AND, OR, NOT]
        "one=two,three"  | [text("one"), EQUALS, text("two"), COMMA, text("three")]
        "(simple)"       | [LPAREN, text("simple"), RPAREN]
    }

    def "Unexpected characters"() {
        when:
        new Scanner(input).scanTokens()

        then:
        def ex = thrown(RuntimeException)
        ex.message == "Unexpected character."

        where:
        input << ["~", "`", "#"]
    }

    def "Incomplete token"() {
        when:
        new Scanner("! ").scanTokens()

        then:
        def ex = thrown(RuntimeException)
        ex.message == "Expected '=' following '!'"
    }
}
