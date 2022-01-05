// Copyright 2022 John Hurst
// John Hurst
// 2022-01-04

package redx.xpath

import org.w3c.dom.Node

/**
 * Simple API for extracting data from an XML W3C DOM node using XPath.
 *
 * Single-valued results such as element text or single node children are returned as nullable values.
 * Multiple value results such as child node collections are returned as lists wrapping an underlying DOM <code>NodeList</code>.
 */
interface XPathOperations {
    /**
     * Returns the DOM node wrapped by this object.
     * @return The DOM node wrapped by this object.
     */
    fun getNode(): Node

    /**
     * Returns the element text for all elements matching the expression.
     * If the element exists but contains no text (i.e. &lt;elt&gt;&lt;/elt&gt; or &lt;elt/&gt;) then an empty string is returned.
     * If the element does not exist then <code>null</code> is returned.
     *
     * This is supposed to match the normal behaviour of XPath when matching multiple nodes,
     * but also provide a distinction between no node found and node with empty text.
     *
     * @param expr XPath expression.
     * @return <code>String</code> or <code>null</code>.
     */
    fun text(expr: String): String?

    /**
     * Returns the node matching the expression, or <code>null</code> if no such node is found.
     *
     * @param expr XPath expression.
     * @return <code>Node</code> or <code>null</code>.
     * @throws IllegalArgumentException if more than one node matches.
     */
    fun node(expr: String): Node?

    /**
     * Returns a <code>List</code> of <code>Node</code>s matching the expression.
     * If no nodes match the expression, an empty <code>List</code> is returned.
     *
     * @param expr XPath expression.
     * @return <code>List</code> of matching <code>Node</code>s.
     */
    fun nodes(expr: String): List<Node>
}
