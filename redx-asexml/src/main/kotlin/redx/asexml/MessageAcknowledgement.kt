// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element

class MessageAcknowledgement(element: Element, document: Document): Acknowledgement(element, document) {
    val initiatingMessageID: String by lazy { mandatoryText("@initiatingMessageID") }
}
