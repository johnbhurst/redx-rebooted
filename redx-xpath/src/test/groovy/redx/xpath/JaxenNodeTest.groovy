// Copyright 2022 John Hurst
// John Hurst
// 2022-01-04

package redx.xpath

import groovy.xml.DOMBuilder
import spock.lang.Specification

class JaxenNodeTest extends Specification {

    private static JaxenNode jaxenNode(String xml) {
        return new JaxenNode(DOMBuilder.newInstance().parseText(xml))
    }

    JaxenNode xml = jaxenNode("""<doc attr1="one" attr2="">
<val1>one</val1>
<val2></val2>
<val3/>
<val>one</val>
<val>two</val>
<val>three</val>
<mixed>The quick brown <bold>fox</bold> jumps over the lazy <bold>dog</bold></mixed>
</doc>
""")

    void "text() returns text content for matching element(s)"() {
        expect: "text() returns element text for element with text"
        xml.text("doc/val1") == "one"
        and: "text() returns empty text for element with no text"
        xml.text("doc/val2") == ""
        xml.text("doc/val3") == ""
        and: "text() returns null when element is not present"
        xml.text("doc/val4") == null
        and: "text() returns text from all matching elements"
        xml.text("doc/val") == "onetwothree"
        and: "text() returns text content from elements and descendents"
        xml.text("doc/mixed") == "The quick brown fox jumps over the lazy dog"
    }

    void "text() returns attribute values for matching attributes"() {
        expect: "text() returns attribute value(s) for matching attributes"
        xml.text("doc/@attr1") == "one"
        and: "text() returns empty text for attribute with empty value"
        xml.text("doc/@attr2") == ""
        and: "text() returns null when attribute is not present"
        xml.text("doc/@attr3") == null
    }

    void "node() returns zero or one matching element"() {
        expect: "node() returns node for nodes that exist"
        xml.node("doc/val1").textContent == "one"
        xml.node("doc/val2").textContent == ""
        xml.node("doc/val3").textContent == ""
        and: "node() returns null for node that does not exist"
        xml.node("doc/val4") == null
    }

    void "node() throws an exception when more than one match"() {
        when: "node() called with more than one match"
        xml.node("doc/val")
        then: "An exception should be thrown"
        IllegalArgumentException ex = thrown()
        ex.message == "More than one node matches doc/val"
    }

    void "nodes() returns matching elements"() {
        expect: "nodes() returns list of matching elements"
        xml.nodes("doc/val")*.textContent == ["one", "two", "three"]
        and: "nodes() returns empty list when no elements match"
        xml.nodes("doc/val4").isEmpty()
    }

}
