package com.dc.esb.servicegov.wsdl.util.xml;

import org.w3c.dom.Node;

import javax.xml.namespace.QName;

public class QNameUtils {
    public static boolean matches(QName qname, Node node) {
        return (node != null && qname.equals(newQName(node)));
    }

    public static QName newQName(Node node) {
        if (node != null) {
            return new QName(node.getNamespaceURI(), node.getLocalName());
        } else {
            return new QName(null, null);
        }
    }
}