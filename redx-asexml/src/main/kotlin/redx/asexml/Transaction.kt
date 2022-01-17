// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element
import redx.asexml.AseXmlUtils.localDateTime
import java.time.LocalDateTime

open class Transaction(element: Element, val document: Document): BaseAseXmlElement(element) {
    val transactionDate: LocalDateTime by lazy { localDateTime(mandatoryText("../@transactionDate")) }
    val transactionID: String by lazy { mandatoryText("../@transactionID") }
    val initiatingTransactionID: String? by lazy { text("../@initiatingTransactionID") }
    val isDuplicate: Boolean by lazy { text("../@duplicate") == "Yes" }
    val element: Element by lazy { getNode() as Element }
    val transactionName: String by lazy { element.nodeName }
}
