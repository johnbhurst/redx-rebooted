// Copyright 2022 John Hurst
// John Hurst
// 2022-01-18

package redx.filter

/**
 * @link https://google.aip.dev/160
 * @link https://google.aip.dev/assets/misc/ebnf-filtering.txt
 * @link https://craftinginterpreters.com/
 */
interface Expr {
    data class Expression(val sequences: List<Expr>) : Expr
    data class Sequence(val factors: List<Expr>) : Expr
    data class Factor(val terms: List<Expr>) : Expr
    data class Term(val expr: Expr) : Expr
    data class Not(val expr: Expr) : Expr
    data class StringRestriction(val value: String) : Expr
    data class BinaryRestriction(val variable: String, val operator: BinaryOperator, val value: Value) : Expr
    data class Composite(val expr: Expr) : Expr
}
