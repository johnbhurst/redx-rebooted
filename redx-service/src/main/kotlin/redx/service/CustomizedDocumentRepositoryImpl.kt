// Copyright 2022 John Hurst
// John Hurst
// 2022-02-04

package redx.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import javax.annotation.Resource
import javax.persistence.EntityManager
import javax.persistence.criteria.JoinType

class CustomizedDocumentRepositoryImpl(@Resource val entityManager: EntityManager) : CustomizedDocumentRepository {

    override fun findForFilter(filter: String): Iterable<Document> {
        // TODO: parse filter and build criteria expression
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Document::class.java)
        val documentRoot = criteriaQuery.from(Document::class.java)
        val transactions = documentRoot.joinSet<Document, Transaction>("transactions", JoinType.LEFT)
        val searchTerms = transactions.joinSet<Transaction, String>("searchTerms", JoinType.LEFT)
        val predicate = criteriaBuilder.`in`(criteriaBuilder.literal(filter)).value(searchTerms)
        criteriaQuery.select(documentRoot).where(predicate)
        return entityManager.createQuery(criteriaQuery).resultList
    }

    override fun findForFilter(filter: String, sort: Sort): Iterable<Document> {
        TODO("Not yet implemented")
    }

    override fun findForFilter(filter: String, pageable: Pageable): Page<Document> {
        TODO("Not yet implemented")
    }

}