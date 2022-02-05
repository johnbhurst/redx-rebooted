// Copyright 2022 John Hurst
// John Hurst
// 2022-02-04

package redx.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import redx.filter.BinaryOperator
import redx.filter.BinaryOperator.*
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
import javax.persistence.criteria.From
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root
import javax.persistence.criteria.SetJoin

class CustomizedDocumentRepositoryImpl(@Resource val entityManager: EntityManager) : CustomizedDocumentRepository {

    class PredicateBuilder(
        private val criteriaBuilder: CriteriaBuilder,
        private val criteriaQuery: CriteriaQuery<Document>,
        private val documentRoot: Root<Document>,
        private val transactions: SetJoin<Document, Transaction>,
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
                "fileName" -> stringExpr(documentRoot, variable, operator, value)
                "fileSize" -> integerExpr(documentRoot, variable, operator, value)
                "fileDate" -> dateTimeExpr(documentRoot, variable, operator, value)
                "messageFrom" -> stringExpr(documentRoot, variable, operator, value)
                "fromDescription" -> stringExpr(documentRoot, variable, operator, value)
                "messageTo" -> stringExpr(documentRoot, variable, operator, value)
                "toDescription" -> stringExpr(documentRoot, variable, operator, value)
                "messageId" -> stringExpr(documentRoot, variable, operator, value)
                "messageDate" -> dateTimeExpr(documentRoot, variable, operator, value)
                "transactionGroup" -> stringExpr(documentRoot, variable, operator, value)
                "priority" -> stringExpr(documentRoot, variable, operator, value)
                "market" -> stringExpr(documentRoot, variable, operator, value)
                "txn.transactionDate" -> dateTimeExpr(transactions, "transactionDate", operator, value)
                "txn.transactionId" -> stringExpr(transactions, "transactionId", operator, value)
                "txn.initiatingTransactionId" -> stringExpr(transactions, "initiatingTransactionId", operator, value)
                "txn.transactionName" -> stringExpr(transactions, "transactionName", operator, value)
                else -> throw RuntimeException("Unsupported variable $variable")
            }

        private fun <X,Y> stringExpr(target: From<X,Y>, variable: String, operator: BinaryOperator, value: Value): Expression<Boolean> =
            if (value is Value.StringValue) when (operator) {
                EQUALS -> criteriaBuilder.equal(target.get<String>(variable), value.value)
                GREATER_EQUALS -> criteriaBuilder.greaterThanOrEqualTo(target.get(variable), value.value)
                GREATER_THAN -> criteriaBuilder.greaterThan(target.get(variable), value.value)
                LESS_EQUALS -> criteriaBuilder.lessThanOrEqualTo(target.get(variable), value.value)
                LESS_THAN -> criteriaBuilder.lessThan(target.get(variable), value.value)
                NOT_EQUALS -> criteriaBuilder.notEqual(target.get<String>(variable), value.value)
            } else {
                throw RuntimeException("Expected string value for $variable, got ${value.javaClass.simpleName}")
            }

        private fun <X,Y> integerExpr(target: From<X,Y>, variable: String, operator: BinaryOperator, value: Value): Expression<Boolean> =
            if (value is Value.IntegerValue) when (operator) {
                EQUALS -> criteriaBuilder.equal(target.get<Int>(variable), value.value)
                GREATER_EQUALS -> criteriaBuilder.greaterThanOrEqualTo(target.get(variable), value.value)
                GREATER_THAN -> criteriaBuilder.greaterThan(target.get(variable), value.value)
                LESS_EQUALS -> criteriaBuilder.lessThanOrEqualTo(target.get(variable), value.value)
                LESS_THAN -> criteriaBuilder.lessThan(target.get(variable), value.value)
                NOT_EQUALS -> criteriaBuilder.notEqual(target.get<Int>(variable), value.value)
            } else {
                throw RuntimeException("Expected integer value for $variable, got ${value.javaClass.simpleName}")
            }

        private fun <X,Y> dateTimeExpr(target: From<X,Y>, variable: String, operator: BinaryOperator, value: Value): Expression<Boolean> =
            if (value is Value.DateTimeValue) when (operator) {
                EQUALS -> criteriaBuilder.equal(target.get<LocalDateTime>(variable), value.value)
                GREATER_EQUALS -> criteriaBuilder.greaterThanOrEqualTo(target.get(variable), value.value)
                GREATER_THAN -> criteriaBuilder.greaterThan(target.get(variable), value.value)
                LESS_EQUALS -> criteriaBuilder.lessThanOrEqualTo(target.get(variable), value.value)
                LESS_THAN -> criteriaBuilder.lessThan(target.get(variable), value.value)
                NOT_EQUALS -> criteriaBuilder.notEqual(target.get<LocalDateTime>(variable), value.value)
            } else {
                throw RuntimeException("Expected datetime value for $variable, got ${value.javaClass.simpleName}")
            }

        private fun searchTermExpr(value: String) : Expression<Boolean> {
            val searchQuery = criteriaQuery.subquery(String::class.java)
            val transactionsSubJoin = searchQuery.correlate(transactions)
            val searchTerms = transactionsSubJoin.joinSet<Transaction, String>("searchTerms", JoinType.LEFT)
            searchQuery.select(searchTerms).where(criteriaBuilder.equal(searchTerms, criteriaBuilder.literal(value)))
            return criteriaBuilder.exists(searchQuery)
        }
    }

    private fun createQuery(filter: String): TypedQuery<Document> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Document::class.java)
        val documentRoot: Root<Document> = criteriaQuery.from(Document::class.java)
        val transactions = documentRoot.joinSet<Document, Transaction>("transactions", JoinType.LEFT)
        val tokens = Scanner(filter).scanTokens()
        val expr = Parser(tokens).parse()
        val predicate = PredicateBuilder(criteriaBuilder, criteriaQuery, documentRoot, transactions).build(expr)
        criteriaQuery.select(documentRoot)
            .where(predicate)
            .distinct(true)
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