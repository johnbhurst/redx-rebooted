// Copyright 2022 JOhn Hurst
// John Hurst
// 2022-02-01

package redx.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.persistence.EntityManager
import javax.persistence.criteria.JoinType

@SpringBootApplication
open class Main @Autowired constructor(private val documentRepository: DocumentRepository, private val entityManager: EntityManager) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        log.info("Hello, world")
        findAll()
        queryTransactionId()
        querySearchTerm()
    }

    private fun findAll() {
        documentRepository.findAll().forEach { document ->
            log.info(
                "Found document ID [${document.id}], fileName(${document.fileName}), fileSize(${document.fileSize}), fileDate(${document.fileDate}), " +
                        "messageFrom(${document.messageFrom}), fromDescription(${document.fromDescription}), " +
                        "messageTo(${document.messageTo}), toDescription(${document.toDescription}), " +
                        "messageId(${document.messageId}), messageDate(${document.messageDate}), " +
                        "priority(${document.priority}), securityContext(${document.securityContext}), market(${document.market})"
            )
            document.transactions.forEach { transaction ->
                log.info(
                    "  Transaction ID [${transaction.id}], transactionDate(${transaction.transactionDate}), " +
                            "transactionId(${transaction.transactionId}), initiatingTransactionId(${transaction.initiatingTransactionId}), " +
                            "transactionName(${transaction.transactionName})"
                )
                transaction.searchTerms.forEach { term ->
                    log.info("    Search term: [$term]")
                }
            }
        }
    }

    private fun queryTransactionId() {
        log.info("Criteria Query: Query on transaction ID")
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Document::class.java)
        val documentRoot = criteriaQuery.from(Document::class.java)
        val transactions = documentRoot.joinSet<Document, Transaction>("transactions", JoinType.LEFT)
        val predicate = criteriaBuilder.equal(transactions.get<Any>("transactionId"), "XXX")
        criteriaQuery.select(documentRoot).where(predicate)
        entityManager.createQuery(criteriaQuery).resultList.forEach { result ->
            log.info("Document: [${result.fileName}]")
            result.transactions.forEach { transaction ->
                log.info("  Transaction: [${transaction.transactionId}]")
            }
        }
    }

    private fun querySearchTerm() {
        log.info("Criteria Query: Query on search term")
        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Document::class.java)
        val documentRoot = criteriaQuery.from(Document::class.java)
        val transactions = documentRoot.joinSet<Document, Transaction>("transactions", JoinType.LEFT)
        val searchTerms = transactions.joinSet<Transaction, String>("searchTerms", JoinType.LEFT)
        val predicate = criteriaBuilder.`in`(criteriaBuilder.literal("6012345621")).value(searchTerms)
        criteriaQuery.select(documentRoot).where(predicate)
        entityManager.createQuery(criteriaQuery).resultList.forEach { result ->
            log.info("Document: [${result.fileName}]")
            result.transactions.forEach { transaction ->
                log.info("  Transaction: [${transaction.transactionId}]")
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Main>(*args)
}
