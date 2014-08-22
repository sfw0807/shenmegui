
package com.dc.esb.servicegov.wsdl.extensions.mime;

import com.dc.esb.servicegov.wsdl.Constants;

import javax.xml.namespace.QName;

public class MIMEConstants {
    // Namespace URIs.
    public static final String NS_URI_MIME =
            "http://schemas.xmlsoap.org/wsdl/mime/";

    // Element names.
    public static final String ELEM_CONTENT = "content";
    public static final String ELEM_MULTIPART_RELATED = "multipartRelated";
    public static final String ELEM_MIME_XML = "mimeXml";

    // Qualified element names.
    public static final QName Q_ELEM_MIME_CONTENT =
            new QName(NS_URI_MIME, ELEM_CONTENT);
    public static final QName Q_ELEM_MIME_MULTIPART_RELATED =
            new QName(NS_URI_MIME, ELEM_MULTIPART_RELATED);
    public static final QName Q_ELEM_MIME_PART =
            new QName(NS_URI_MIME, Constants.ELEM_PART);
    public static final QName Q_ELEM_MIME_MIME_XML =
            new QName(NS_URI_MIME, ELEM_MIME_XML);

    // Attribute names.
    public static final String ATTR_PART = "part";
}