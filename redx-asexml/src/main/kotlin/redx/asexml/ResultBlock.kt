// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element

class ResultBlock(element: Element): BaseAseXmlElement(element) {
    val currentBlockNumber: Int by lazy { mandatoryText("CurrentBlockNumber").toInt() }
    val totalBlockNumber: Int by lazy { mandatoryText("TotalBlockNumber").toInt() }
}
