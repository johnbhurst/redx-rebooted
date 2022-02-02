// Copyright 2022 John Hurst
// John Hurst
// 2022-02-01

package redx.service

import java.time.LocalDateTime
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Transaction(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: String,
    @ManyToOne @JoinColumn(name = "document") val document: Document,
    @Column(nullable = false) val transactionDate: LocalDateTime,
    @Column(nullable = false) val transactionId: String,
    @Column(nullable = true) val initiatingTransactionId: String?,
    @Column(nullable = false) val transactionName: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "search_term")
    @CollectionTable(name = "transaction_search_term", joinColumns = [JoinColumn(name = "transaction")]) val searchTerms: Set<String>,
)
