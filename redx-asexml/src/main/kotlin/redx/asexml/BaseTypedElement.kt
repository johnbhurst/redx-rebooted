// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import org.w3c.dom.Element
import redx.asexml.AseXmlConstants.XSI_TYPE_ATTR

abstract class BaseTypedElement(element: Element): BaseAseXmlElement(element), TypedElement {
    override val type: String by lazy { mandatoryText(XSI_TYPE_ATTR) }
}
