// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

object AseXmlConstants {
    val XSI_TYPE_ATTR = "@*[local-name()='type' and namespace-uri()='http://www.w3.org/2001/XMLSchema-instance']"
    val ASEXML = "/*[local-name()='aseXML' and starts-with(namespace-uri(), 'urn:aseXML:r')]"
}
