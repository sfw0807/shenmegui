/*
 * (c) Copyright IBM Corp 2001, 2005 
 */

package com.dc.esb.servicegov.refactoring.wsdl.extensions.http;

import com.dc.esb.servicegov.wsdl.Constants;

import javax.xml.namespace.QName;

public class HTTPConstants {
    // Namespace URIs.
    public static final String NS_URI_HTTP =
            "http://schemas.xmlsoap.org/wsdl/http/";

    // Element names.
    public static final String ELEM_ADDRESS = "address";
    public static final String ELEM_URL_ENCODED = "urlEncoded";
    public static final String ELEM_URL_REPLACEMENT = "urlReplacement";

    // Qualified element names.
    public static final QName Q_ELEM_HTTP_BINDING =
            new QName(NS_URI_HTTP, Constants.ELEM_BINDING);
    public static final QName Q_ELEM_HTTP_OPERATION =
            new QName(NS_URI_HTTP, Constants.ELEM_OPERATION);
    public static final QName Q_ELEM_HTTP_ADDRESS =
            new QName(NS_URI_HTTP, ELEM_ADDRESS);
    public static final QName Q_ELEM_HTTP_URL_ENCODED =
            new QName(NS_URI_HTTP, ELEM_URL_ENCODED);
    public static final QName Q_ELEM_HTTP_URL_REPLACEMENT =
            new QName(NS_URI_HTTP, ELEM_URL_REPLACEMENT);

    // Attribute names.
    public static final String ATTR_VERB = "verb";
}