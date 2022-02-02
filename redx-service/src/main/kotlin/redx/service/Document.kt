// Copyright 2022 John Hurst
// John Hurst
// 2022-02-01

package redx.service

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Document(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: String,
    @Column(nullable = false) val fileName: String,
    @Column(nullable = false) val fileSize: Int,
    @Column(nullable = false) val fileDate: LocalDateTime,
    @Column(nullable = false) val messageFrom: String,
    @Column(nullable = true) val fromDescription: String?,
    @Column(nullable = false) val messageTo: String,
    @Column(nullable = true) val toDescription: String?,
    @Column(nullable = false) val messageId: String,
    @Column(nullable = false) val messageDate: LocalDateTime,
    @Column(nullable = true) val priority: String?,
    @Column(nullable = true) val securityContext: String?,
    @Column(nullable = true) val market: String?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "document") val transactions: Set<Transaction>,
)
