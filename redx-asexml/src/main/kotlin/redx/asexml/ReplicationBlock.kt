// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element

class ReplicationBlock(element: Element): BaseAseXmlElement(element) {
    val tableName: String? by lazy { text("@tableName") }
    val rows: List<ReplicationBaseRow> by lazy { elements("Row").map(::ReplicationBaseRow) }
}
