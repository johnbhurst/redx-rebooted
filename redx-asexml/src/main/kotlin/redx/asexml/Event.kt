// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element

class Event(element: Element): BaseAseXmlElement(element) {
    val eventClass: EventClass? by lazy { text("@class")?.let { EventClass.valueOf(it) } }
    val eventSeverity: EventSeverity? by lazy { text("@severity")?.let { EventSeverity.valueOf(it) } }
    val code: Int by lazy { mandatoryText("Code").toInt() }
    val codeDescription: String? by lazy { text("Code/@description") }
    val keyInfo: String? by lazy { text("KeyInfo") }
    val context: String? by lazy { text("Context") }
    val explanation: String? by lazy { text("Explanation") }
}
