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
import javax.persistence.criteria.Expression
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Root
import javax.persistence.criteria.SetJoin

class CustomizedDocumentRepositoryImpl(@Resource val entityManager: EntityManager) : CustomizedDocumentRepository {

    class PredicateBuilder(val criteriaBuilder: CriteriaBuilder, documentRoot: Root<Document>) {
        val transactions: SetJoin<Document, Transaction> = documentRoot.joinSet<Document, Transaction>("transactions", JoinType.LEFT)
        val searchTerms: SetJoin<Transaction, String> = transactions.joinSet<Transaction, String>("searchTerms", JoinType.LEFT)

        fun build(expr: Expr): Expression<Boolean> {
            when (expr) {
                is Expr.StringRestriction -> return criteriaBuilder.`in`(criteriaBuilder.literal(expr.value)).value(searchTerms)
                else -> return TODO("case not yet implemented")
            }

        }
    }

    private fun createQuery(filter: String): TypedQuery<Document> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Document::class.java)
        val documentRoot: Root<Document> = criteriaQuery.from(Document::class.java)
        val tokens = Scanner(filter).scanTokens()
        val expr = Parser(tokens).parse()
        val predicate = PredicateBuilder(criteriaBuilder, documentRoot).build(expr)
        criteriaQuery.select(documentRoot).where(predicate)
        return entityManager.createQuery(criteriaQuery)
    }

    override fun findForFilter(filter: String): Iterable<Document> {
        val query = createQuery(filter)
        return query.resultList
    }

    override fun findForFilter(filter: String, sort: Sort): Iterable<Document> {
        TODO("Not yet implemented")
    }

    override fun findForFilter(filter: String, pageable: Pageable): Page<Document> {
        TODO("Not yet implemented")
    }

}