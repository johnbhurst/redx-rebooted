// Copyright 2022 John Hurst
// John Hurst
// 2022-02-04

package redx.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import redx.filter.BinaryOperator
import redx.filter.Expr
import redx.filter.Parser
import redx.filter.Scanner
import redx.filter.Value
import java.time.LocalDateTime
import javax.annotation.Resource
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Expression
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root

class CustomizedDocumentRepositoryImpl(@Resource val entityManager: EntityManager) : CustomizedDocumentRepository {

    class PredicateBuilder(
        private val criteriaBuilder: CriteriaBuilder,
        private val criteriaQuery: CriteriaQuery<Document>,
        private val documentRoot: Root<Document>
    ) {

        fun build(expr: Expr): Expression<Boolean> {
            return when (expr) {
                is Expr.And -> expr.sequences.map(::build).reduce(criteriaBuilder::and)
                is Expr.Or -> expr.terms.map(::build).reduce(criteriaBuilder::or)
                is Expr.Not -> criteriaBuilder.not(build(expr.expr))
                is Expr.Composite -> build(expr.expr)
                is Expr.BinaryExpression -> binaryExpr(expr.variable, expr.operator, expr.value)
                is Expr.StringRestriction -> searchTermExpr(expr.value)
                else -> throw RuntimeException("Unsupported expression type ${expr.javaClass}")
            }
        }

        private fun binaryExpr(variable: String, operator: BinaryOperator, value: Value): Expression<Boolean> =
            when (variable) {
                "fileName" -> stringExpr(variable, operator, value)
                "fileSize" -> integerExpr(variable, operator, value)
                "fileDate" -> dateTimeExpr(variable, operator, value)
                "messageFrom" -> stringExpr(variable, operator, value)
                "fromDescription" -> stringExpr(variable, operator, value)
                "messageTo" -> stringExpr(variable, operator, value)
                "toDescription" -> stringExpr(variable, operator, value)
                "messageId" -> stringExpr(variable, operator, value)
                "messageDate" -> dateTimeExpr(variable, operator, value)
                "transactionGroup" -> stringExpr(variable, operator, value)
                "priority" -> stringExpr(variable, operator, value)
                "market" -> stringExpr(variable, operator, value)
                else -> throw RuntimeException("Unsupported variable $variable")
            }

        private fun stringExpr(variable: String, operator: BinaryOperator, value: Value): Expression<Boolean> =
            if (value is Value.StringValue) when (operator) {
                BinaryOperator.EQUALS -> criteriaBuilder.equal(documentRoot.get<String>(variable), value.value)
                BinaryOperator.GREATER_EQUALS -> criteriaBuilder.greaterThanOrEqualTo(documentRoot.get(variable), value.value)
                BinaryOperator.GREATER_THAN -> criteriaBuilder.greaterThan(documentRoot.get(variable), value.value)
                BinaryOperator.LESS_EQUALS -> criteriaBuilder.lessThanOrEqualTo(documentRoot.get(variable), value.value)
                BinaryOperator.LESS_THAN -> criteriaBuilder.lessThan(documentRoot.get(variable), value.value)
                BinaryOperator.NOT_EQUALS -> criteriaBuilder.notEqual(documentRoot.get<String>(variable), value.value)
            } else {
                throw RuntimeException("Expected string value for $variable, got ${value.javaClass.simpleName}")
            }

        private fun integerExpr(variable: String, operator: BinaryOperator, value: Value): Expression<Boolean> =
            if (value is Value.IntegerValue) when (operator) {
                BinaryOperator.EQUALS -> criteriaBuilder.equal(documentRoot.get<Int>(variable), value.value)
                BinaryOperator.GREATER_EQUALS -> criteriaBuilder.greaterThanOrEqualTo(documentRoot.get(variable), value.value)
                BinaryOperator.GREATER_THAN -> criteriaBuilder.greaterThan(documentRoot.get(variable), value.value)
                BinaryOperator.LESS_EQUALS -> criteriaBuilder.lessThanOrEqualTo(documentRoot.get(variable), value.value)
                BinaryOperator.LESS_THAN -> criteriaBuilder.lessThan(documentRoot.get(variable), value.value)
                BinaryOperator.NOT_EQUALS -> criteriaBuilder.notEqual(documentRoot.get<Int>(variable), value.value)
            } else {
                throw RuntimeException("Expected integer value for $variable, got ${value.javaClass.simpleName}")
            }

        private fun dateTimeExpr(variable: String, operator: BinaryOperator, value: Value): Expression<Boolean> =
            if (value is Value.DateTimeValue) when (operator) {
                BinaryOperator.EQUALS -> criteriaBuilder.equal(documentRoot.get<LocalDateTime>(variable), value.value)
                BinaryOperator.GREATER_EQUALS -> criteriaBuilder.greaterThanOrEqualTo(documentRoot.get(variable), value.value)
                BinaryOperator.GREATER_THAN -> criteriaBuilder.greaterThan(documentRoot.get(variable), value.value)
                BinaryOperator.LESS_EQUALS -> criteriaBuilder.lessThanOrEqualTo(documentRoot.get(variable), value.value)
                BinaryOperator.LESS_THAN -> criteriaBuilder.lessThan(documentRoot.get(variable), value.value)
                BinaryOperator.NOT_EQUALS -> criteriaBuilder.notEqual(documentRoot.get<LocalDateTime>(variable), value.value)
            } else {
                throw RuntimeException("Expected datetime value for $variable, got ${value.javaClass.simpleName}")
            }

        private fun searchTermExpr(value: String) : Expression<Boolean> {
            val searchQuery = criteriaQuery.subquery(String::class.java)
            val transactions = searchQuery.from(Transaction::class.java)
            val searchTerms = transactions.joinSet<Transaction, String>("searchTerms", JoinType.LEFT)
            searchQuery.select(transactions.get("document")).where(criteriaBuilder.equal(searchTerms, criteriaBuilder.literal(value)))
            return criteriaBuilder.`in`(documentRoot.get<String>("id")).value(searchQuery)
        }
    }

    private fun createQuery(filter: String): TypedQuery<Document> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Document::class.java)
        val documentRoot: Root<Document> = criteriaQuery.from(Document::class.java)
        val tokens = Scanner(filter).scanTokens()
        val expr = Parser(tokens).parse()
        val predicate = PredicateBuilder(criteriaBuilder, criteriaQuery, documentRoot).build(expr)
        criteriaQuery.select(documentRoot).where(predicate)
        return entityManager.createQuery(criteriaQuery)
    }

    override fun findForFilter(filter: String): Iterable<Document> {
        return createQuery(filter).resultList
    }

    override fun findForFilter(filter: String, sort: Sort): Iterable<Document> {
        TODO("Not yet implemented")
    }

    override fun findForFilter(filter: String, pageable: Pageable): Page<Document> {
        TODO("Not yet implemented")
    }

}