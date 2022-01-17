// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element
import redx.asexml.AseXmlConstants.ASEXML
import redx.asexml.AseXmlUtils.localDateTime
import java.time.LocalDateTime

class Document(document: org.w3c.dom.Document): BaseAseXmlElement(document) {

    val from: String  by lazy { mandatoryText("$ASEXML/Header/From") }
    val fromDescription: String? by lazy { text("$ASEXML/Header/From/@description") }
    val to: String by lazy { mandatoryText( "$ASEXML/Header/To") }
    val toDescription: String? by lazy { text("$ASEXML/Header/To/@description") }
    val messageID: String by lazy { mandatoryText("$ASEXML/Header/MessageID") }
    val messageDate: LocalDateTime by lazy { localDateTime(mandatoryText("$ASEXML/Header/MessageDate")) }
    val transactionGroup: TransactionGroup by lazy { TransactionGroup.valueOf(mandatoryText("$ASEXML/Header/TransactionGroup")) }
    val priority: Priority? by lazy { text("$ASEXML/Header/Priority")?.let {Priority.valueOf(it) } }
    val securityContext: String? by lazy { text("$ASEXML/Header/SecurityContext") }
    val market: String? by lazy { text("$ASEXML/Header/Market") }
    val documentElement: Element by lazy { (getNode() as org.w3c.dom.Document).documentElement }

    val messageAcknowledgements: List<MessageAcknowledgement> by lazy { elements("$ASEXML/Acknowledgements/MessageAcknowledgement").map { MessageAcknowledgement(it, this) } }
    val transactionAcknowledgements: List<TransactionAcknowledgement> by lazy { elements("$ASEXML/Acknowledgements/TransactionAcknowledgement").map { TransactionAcknowledgement(it, this) } }
    val transactions: List<Transaction> by lazy { elements("$ASEXML/Transactions/Transaction/*").map(::createTransaction) }

    private fun createTransaction(element: Element): Transaction =
        when(element.nodeName) {
            "MeterDataNotification" -> MeterDataNotification(element, this)
            "ReportResponse" -> ReportResponse(element, this)
            else -> Transaction(element, this)
        }
}
