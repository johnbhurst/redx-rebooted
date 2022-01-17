// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element

class CSVReportFormat(element: Element): BaseTypedElement(element), ReportFormat {
    val csvData: String? by lazy { text("CSVData") }
}
