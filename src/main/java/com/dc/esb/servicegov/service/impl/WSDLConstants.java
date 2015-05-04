package com.dc.esb.servicegov.service.impl;

import javax.xml.namespace.QName;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-16
 * Time: 下午3:49
 */
public class WSDLConstants {
    public static final String WSDL_NAMEPACE = "http://www.w3.org/2001/XMLSchema";
    public static final String WSDL_PREFIX = "wsdl";
    public static final String HTTP_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/http/";
    public static final String HTTP_PREFIX = "http";
    public static final String MIME_NAMEPACE = "http://schemas.xmlsoap.org/wsdl/mime/";
    public static final String MIME_PREFIX = "mime";
    public static final String SOAP_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
    public static final String SOAP_PREFIX = "soap";
    public static final String SOAP_NC_NAMESPACE = "http://schemas.xmlsoap.org/soap/encoding/";
    public static final String SOAP_NC_PREFIX = "soapnc";
    public static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    public static final String XSD_PREFIX = "xsd";

    public static final QName WSDL_DEFINITIONS_QNAME = new QName(WSDL_NAMEPACE);
}
