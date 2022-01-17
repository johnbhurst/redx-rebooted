// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element
import redx.asexml.AseXmlUtils.localDateTime
import java.time.LocalDateTime

abstract class Acknowledgement(val element: Element, val document: Document): BaseAseXmlElement(element) {
    val receiptID: String? by lazy { text("@receiptID") }
    val receiptDate: LocalDateTime by lazy { localDateTime(mandatoryText("@receiptDate")) }
    val status: TransactionStatus by lazy { TransactionStatus.valueOf("@status") }
    val isDuplicate: Boolean by lazy { text("@duplicate") == "Yes" }
    val events: List<Event> by lazy { elements("Event").map {Event(it)}}
}
