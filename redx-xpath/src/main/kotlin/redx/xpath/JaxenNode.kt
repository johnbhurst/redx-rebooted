// Copyright 2022 John Hurst
// John Hurst
// 2022-01-04

package redx.xpath

import org.jaxen.dom.DOMXPath
import org.w3c.dom.Node
import java.lang.IllegalArgumentException

class JaxenNode(private val node: Node): XPathOperations {

    override fun getNode(): Node {
        return this.node
    }

    override fun text(expr: String): String? {
        val nodes = nodes(expr)
        return if (nodes.isEmpty()) null else nodes.joinToString("") { it.textContent }
    }

    override fun node(expr: String): Node? {
        val nodes = nodes(expr)
        return if (nodes.isEmpty()) null else if (nodes.size == 1) nodes[0] else throw IllegalArgumentException("More than one node matches $expr")
    }

    override fun nodes(expr: String): List<Node> {
        return DOMXPath(expr).selectNodes(node) as List<Node>
    }
}