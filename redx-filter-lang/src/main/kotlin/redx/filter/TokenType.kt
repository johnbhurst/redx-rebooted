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
    EQUALS, LPAREN, RPAREN, MINUS, DOT, HAS,
    // One or two-character tokens.
    LESS_EQUALS, LESS_THAN, GREATER_EQUALS, GREATER_THAN, NOT_EQUALS,
    // Literals
    IDENTIFIER, TEXT, STRING, NUMBER,
    // Keywords
    AND, OR, NOT,

    EOF,
}
