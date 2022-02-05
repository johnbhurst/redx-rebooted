// Copyright 2022 John Hurst
// John Hurst
// 2022-02-04

package redx.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import redx.filter.Expr
import redx.filter.Parser
import redx.filter.Scanner
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
                is Expr.StringRestriction -> searchTermExpr(expr.value)
                else -> TODO("case not yet implemented")
            }
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