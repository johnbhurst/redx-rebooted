// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element
import redx.asexml.AseXmlUtils.localDateTime
import java.time.LocalDateTime

class ReplicationBaseRow(element: Element): BaseTypedElement(element) {
    val sequenceNumber: Int by lazy { mandatoryText("SequenceNumber").toInt() }
    val creationDate: LocalDateTime by lazy { localDateTime(mandatoryText("CreationDate")) }
    val maintenanceDate: LocalDateTime by lazy { localDateTime(mandatoryText("MaintenanceDate")) }
    val rowStatus: String by lazy { mandatoryText("RowStatus") }
}
