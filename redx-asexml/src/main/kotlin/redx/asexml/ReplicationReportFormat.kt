// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element

class ReplicationReportFormat(element: Element): BaseTypedElement(element), ReportFormat {
    val replicationBlocks: List<ReplicationBlock> by lazy { elements("ReplicationBlock").map(::ReplicationBlock) }
}
