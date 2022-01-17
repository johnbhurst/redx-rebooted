// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element
import redx.asexml.AseXmlConstants.XSI_TYPE_ATTR
import redx.xpath.JaxenNode

class ReportResponse(element: Element, document: Document): Transaction(element, document) {

    val reportParameters: Map<String, String> by lazy { nodes("ReportParameters/*").associate { it.nodeName to it.textContent } }
    val resultBlock: ResultBlock? by lazy { element("ResultBlock")?.let(::ResultBlock) }
    val reportResults: ReportFormat? by lazy { element("ReportResults")?.let(::createReportFormat) }
    val events: List<Event> by lazy { elements("Event").map(::Event) }

    private fun createReportFormat(element: Element): ReportFormat {
        val type = JaxenNode(element).text(XSI_TYPE_ATTR) ?: throw IllegalStateException("ReportResults type not specified")
        return when(type) {
            "ase:CSVReportFormat" -> CSVReportFormat(element)
            "ase:ReplicationReportFormat" -> ReplicationReportFormat(element)
            else -> throw IllegalStateException("Unknown ReportResults type [$type]")
        }
    }
}
