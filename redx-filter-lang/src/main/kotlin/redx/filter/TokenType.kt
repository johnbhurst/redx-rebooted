// Copyright 2022 John Hurst
// John Hurst
// 2022-01-18

package redx.filter

/**
 * @link https://google.aip.dev/160
 * @link https://google.aip.dev/assets/misc/ebnf-filtering.txt
 * @link https://craftinginterpreters.com/
 */
enum class TokenType {
    // Single-character tokens
    EQUALS, LPAREN, RPAREN, MINUS, COMMA, DOT, HAS,
    // One or two-character tokens.
    LESS_EQUALS, LESS_THAN, GREATER_EQUALS, GREATER_THAN, NOT_EQUALS,
    // Literals
    TEXT, STRING, INTEGER, DATE, DATETIME,
    // Keywords
    AND, OR, NOT,

    EOF,
}
