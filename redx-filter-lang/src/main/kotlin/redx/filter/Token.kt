// Copyright 2022 John Hurst
// John Hurst
// 2022-01-18

package redx.filter

/**
 * @see https://google.aip.dev/160
 * @see https://google.aip.dev/assets/misc/ebnf-filtering.txt
 * @see https://craftinginterpreters.com/
 */
class Token(val type: TokenType, val lexeme: String, val literal: Any?, val line: Int) {
    override fun toString(): String {
        return "$type $lexeme $literal"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Token
        if (type != other.type) return false
        if (lexeme != other.lexeme) return false
        if (literal != other.literal) return false
        if (line != other.line) return false
        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + lexeme.hashCode()
        result = 31 * result + (literal?.hashCode() ?: 0)
        result = 31 * result + line
        return result
    }

}
