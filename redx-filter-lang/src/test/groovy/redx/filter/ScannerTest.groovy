package redx.filter

import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class ScannerTest extends Specification {

    static Token token(TokenType tokenType, String lexeme) {
        new Token(tokenType, lexeme, null, 1)
    }

    static Token token(TokenType tokenType, String lexeme, Object literal) {
        new Token(tokenType, lexeme, literal, 1)
    }

    static Token text(String s) {
        token(TokenType.TEXT, s)
    }

    static Token doubleString(String s) {
        token(TokenType.STRING, "\"$s\"", s)
    }

    static Token singleString(String s) {
        token(TokenType.STRING, "'$s'", s)
    }

    static Token integer(Integer i) {
        token(TokenType.INTEGER, i.toString(), i)
    }

    static Token date(LocalDate d) {
        token(TokenType.DATE, d.toString(), d)
    }

    static Token datetime(LocalDateTime dt) {
        token(TokenType.DATETIME, dt.toString(), dt)
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
    static final Token AND = token(TokenType.AND, "AND")
    static final Token OR = token(TokenType.OR, "OR")
    static final Token NOT = token(TokenType.NOT, "NOT")
    static final Token EOF = token(TokenType.EOF, "")

    def "Scanner scans tokens"() {
        expect:
        new Scanner(input).scanTokens() == result + [EOF]

        where:
        input                || result
        "=()-,.:"             | [EQUALS, LPAREN, RPAREN, MINUS, COMMA, DOT, HAS]
        "<=<>=>!="            | [LESS_EQUALS, LESS_THAN, GREATER_EQUALS, GREATER_THAN, NOT_EQUALS]
        "five"                | [text("five")]
        "one.two.three"       | [text("one.two.three")]
        "\"six\""             | [doubleString("six")]
        "'seven'"             | [singleString("seven")]
        "123"                 | [integer(123)]
        "2022-01-24"          | [date(LocalDate.of(2022, 1, 24))]
        "2022-01-24T09:17:30" | [datetime(LocalDateTime.of(2022, 1, 24, 9, 17, 30))]
        "this OR that"        | [text("this"), OR, text("that")]
        "this or that"        | [text("this"), text("or"), text("that")]
        "AND OR NOT"          | [AND, OR, NOT]
        "and or not"          | [text("and"), text("or"), text("not")]
        "one=two,three"       | [text("one"), EQUALS, text("two"), COMMA, text("three")]
        "(simple)"            | [LPAREN, text("simple"), RPAREN]
        // TODO: more robust raw text tokenization, tests for invalid characters when not matching datetime, date, int.
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
