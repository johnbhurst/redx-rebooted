package redx.asexml

import org.w3c.dom.Element
import org.w3c.dom.Node
import redx.xpath.JaxenNode
import redx.xpath.XPathOperations
import java.util.Objects

abstract class BaseAseXmlElement(private val xpath: XPathOperations) : XPathOperations by xpath {

    constructor(node: Node) : this(JaxenNode(node))

    fun mandatoryText(name: String): String =
        text(name) ?: throw IllegalStateException("Mandatory node $name not found on ${getNode().nodeName}")

    fun element(expr: String): Element? = when (val result = node(expr)) {
        null -> null
        is Element -> result
        else -> throw IllegalStateException("Expected node to be element (${Node.ELEMENT_NODE}), was ${result.nodeType}")
    }

    fun elements(expr: String): List<Element> = nodes(expr).filterIsInstance<Element>()

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || this.javaClass != other.javaClass) {
            return false
        }
        val that: BaseAseXmlElement = other as BaseAseXmlElement
        return this.getNode().isSameNode(that.getNode())
    }

    override fun hashCode(): Int = Objects.hashCode(getNode())
}