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
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import javax.persistence.criteria.SetJoin
import kotlin.reflect.KClass
import kotlin.reflect.KFunction2

class CustomizedDocumentRepositoryImpl(@Resource val entityManager: EntityManager) : CustomizedDocumentRepository {

    class PredicateBuilder(
        private val criteriaBuilder: CriteriaBuilder,
        private val criteriaQuery: CriteriaQuery<Document>,
        private val document: Root<Document>,
        private val transactions: SetJoin<Document, Transaction>,
    ) {
        private val documentProperties = mapOf(
            "fileName" to String::class,
            "fileSize" to Int::class,
            "fileDate" to LocalDateTime::class,
            "messageFrom" to String::class,
            "fromDescription" to String::class,
            "messageTo" to String::class,
            "toDescription" to String::class,
            "messageId" to String::class,
            "messageDate" to LocalDateTime::class,
            "transactionGroup" to String::class,
            "priority" to String::class,
            "market" to String::class,
        )
        private val transactionProperties = mapOf(
            "transactionDate" to LocalDateTime::class,
            "transactionId" to String::class,
            "initiatingTransactionId" to String::class,
            "transactionName" to String::class,
        )

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
            if (documentProperties.containsKey(variable)) {
                buildBinaryExpr(document, variable, operator, value, documentProperties.getValue(variable))
            } else if (variable.startsWith("txn.") && transactionProperties.containsKey(variable.substring(4))) {
                val txnVariable = variable.substring(4)
                buildBinaryExpr(transactions, txnVariable, operator, value, transactionProperties.getValue(txnVariable))
            } else {
                throw RuntimeException("Unrecognised variable $variable")
            }

        private fun <X,Y> buildBinaryExpr(target: From<X,Y>, variable: String, operator: BinaryOperator, value: Value, type: KClass<out Any>): Expression<Boolean> =
            when (type) {
                String::class -> if (value is Value.StringValue) {
                    val variableExpr = target.get<String>(variable)
                    val criteriaOp = getOperator<String>(operator)
                    val valueExpr = criteriaBuilder.literal(value.value)
                    criteriaOp(variableExpr, valueExpr)
                } else {
                    throw RuntimeException("Expected string value for $variable, got ${value.javaClass.simpleName}")
                }
                Integer::class -> if (value is Value.IntegerValue) {
                    val variableExpr = target.get<Int>(variable)
                    val criteriaOp = getOperator<Int>(operator)
                    val valueExpr = criteriaBuilder.literal(value.value)
                    criteriaOp(variableExpr, valueExpr)
                } else {
                    throw RuntimeException("Expected integer value for $variable, got ${value.javaClass.simpleName}")
                }
                LocalDateTime::class -> if (value is Value.DateTimeValue) {
                    val variableExpr = target.get<LocalDateTime>(variable)
                    val criteriaOp = getOperator<LocalDateTime>(operator)
                    val valueExpr = criteriaBuilder.literal(value.value)
                    criteriaOp(variableExpr, valueExpr)
                } else {
                    throw RuntimeException("Expected datetime value for $variable, got ${value.javaClass.simpleName}")
                }
                else -> throw RuntimeException("Invalid variable type $type")
            }

        private fun <T: Comparable<T>> getOperator(op: BinaryOperator) : KFunction2<Expression<T>, Expression<T>, Predicate> =
            when (op) {
                EQUALS -> criteriaBuilder::equal
                GREATER_EQUALS -> criteriaBuilder::greaterThanOrEqualTo
                GREATER_THAN -> criteriaBuilder::greaterThan
                LESS_EQUALS -> criteriaBuilder::lessThanOrEqualTo
                LESS_THAN -> criteriaBuilder::lessThan
                NOT_EQUALS -> criteriaBuilder::notEqual
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
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Document::class.java)
        val document = criteriaQuery.from(Document::class.java)
        val transactions = document.joinSet<Document, Transaction>("transactions", JoinType.LEFT)
        val tokens = Scanner(filter).scanTokens()
        val expr = Parser(tokens).parse()
        val predicate = PredicateBuilder(criteriaBuilder, criteriaQuery, document, transactions).build(expr)
        criteriaQuery.select(document)
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