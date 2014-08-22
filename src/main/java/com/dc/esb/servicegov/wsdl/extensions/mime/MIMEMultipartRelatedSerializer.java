
package com.dc.esb.servicegov.wsdl.extensions.mime;

import com.dc.esb.servicegov.wsdl.Constants;
import com.dc.esb.servicegov.wsdl.util.xml.DOMUtils;
import com.dc.esb.servicegov.wsdl.util.xml.QNameUtils;
import com.dc.esb.servicegov.wsdl.util.xml.XPathUtils;
import org.w3c.dom.Element;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionDeserializer;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.ExtensionSerializer;
import javax.wsdl.extensions.mime.MIMEMultipartRelated;
import javax.wsdl.extensions.mime.MIMEPart;
import javax.xml.namespace.QName;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class MIMEMultipartRelatedSerializer implements ExtensionSerializer,
        ExtensionDeserializer,
        Serializable {
    public static final long serialVersionUID = 1;

    public void marshall(Class parentType,
                         QName elementType,
                         ExtensibilityElement extension,
                         PrintWriter pw,
                         Definition def,
                         ExtensionRegistry extReg)
            throws WSDLException {
        MIMEMultipartRelated mimeMultipartRelated =
                (MIMEMultipartRelated) extension;

        if (mimeMultipartRelated != null) {
            String tagName =
                    DOMUtils.getQualifiedValue(MIMEConstants.NS_URI_MIME,
                            "multipartRelated",
                            def);

            if (parentType != null
                    && MIMEPart.class.isAssignableFrom(parentType)) {
                pw.print("    ");
            }

            pw.print("        <" + tagName);

            Boolean required = mimeMultipartRelated.getRequired();

            if (required != null) {
                DOMUtils.printQualifiedAttribute(Constants.Q_ATTR_REQUIRED,
                        required.toString(),
                        def,
                        pw);
            }

            pw.println('>');

            printMIMEParts(mimeMultipartRelated.getMIMEParts(), pw, def, extReg);

            if (parentType != null
                    && MIMEPart.class.isAssignableFrom(parentType)) {
                pw.print("    ");
            }

            pw.println("        </" + tagName + '>');
        }
    }

    private void printMIMEParts(List mimeParts,
                                PrintWriter pw,
                                Definition def,
                                ExtensionRegistry extReg)
            throws WSDLException {
        if (mimeParts != null) {
            String tagName =
                    DOMUtils.getQualifiedValue(MIMEConstants.NS_URI_MIME,
                            "part",
                            def);
            Iterator mimePartIterator = mimeParts.iterator();

            while (mimePartIterator.hasNext()) {
                MIMEPart mimePart = (MIMEPart) mimePartIterator.next();

                if (mimePart != null) {
                    pw.print("          <" + tagName);

                    Boolean required = mimePart.getRequired();

                    if (required != null) {
                        DOMUtils.printQualifiedAttribute(Constants.Q_ATTR_REQUIRED,
                                required.toString(),
                                def,
                                pw);
                    }

                    pw.println('>');

                    List extensibilityElements = mimePart.getExtensibilityElements();

                    if (extensibilityElements != null) {
                        Iterator extensibilityElementIterator =
                                extensibilityElements.iterator();

                        while (extensibilityElementIterator.hasNext()) {
                            ExtensibilityElement ext =
                                    (ExtensibilityElement) extensibilityElementIterator.next();
                            QName elementType = ext.getElementType();
                            ExtensionSerializer extSer =
                                    extReg.querySerializer(MIMEPart.class, elementType);

                            extSer.marshall(MIMEPart.class,
                                    elementType,
                                    ext,
                                    pw,
                                    def,
                                    extReg);
                        }
                    }

                    pw.println("          </" + tagName + '>');
                }
            }
        }
    }

    public ExtensibilityElement unmarshall(Class parentType,
                                           QName elementType,
                                           Element el,
                                           Definition def,
                                           ExtensionRegistry extReg)
            throws WSDLException {
        MIMEMultipartRelated mimeMultipartRelated =
                (MIMEMultipartRelated) extReg.createExtension(parentType, elementType);
        String requiredStr = DOMUtils.getAttributeNS(el,
                Constants.NS_URI_WSDL,
                Constants.ATTR_REQUIRED);
        Element tempEl = DOMUtils.getFirstChildElement(el);

        while (tempEl != null) {
            if (QNameUtils.matches(MIMEConstants.Q_ELEM_MIME_PART, tempEl)) {
                mimeMultipartRelated.addMIMEPart(
                        parseMIMEPart(MIMEMultipartRelated.class,
                                MIMEConstants.Q_ELEM_MIME_PART,
                                tempEl,
                                def,
                                extReg));
            } else {
                DOMUtils.throwWSDLException(tempEl);
            }

            tempEl = DOMUtils.getNextSiblingElement(tempEl);
        }

        if (requiredStr != null) {
            mimeMultipartRelated.setRequired(new Boolean(requiredStr));
        }

        return mimeMultipartRelated;
    }

    private MIMEPart parseMIMEPart(Class parentType,
                                   QName elementType,
                                   Element el,
                                   Definition def,
                                   ExtensionRegistry extReg)
            throws WSDLException {
        MIMEPart mimePart = (MIMEPart) extReg.createExtension(parentType,
                elementType);
        String requiredStr = DOMUtils.getAttributeNS(el,
                Constants.NS_URI_WSDL,
                Constants.ATTR_REQUIRED);

        if (requiredStr != null) {
            mimePart.setRequired(new Boolean(requiredStr));
        }

        Element tempEl = DOMUtils.getFirstChildElement(el);

        while (tempEl != null) {
            try {
                QName tempElType = QNameUtils.newQName(tempEl);
                ExtensionDeserializer extDS = extReg.queryDeserializer(MIMEPart.class,
                        tempElType);
                ExtensibilityElement ext =
                        extDS.unmarshall(MIMEPart.class, tempElType, tempEl, def, extReg);

                mimePart.addExtensibilityElement(ext);
            } catch (WSDLException e) {
                if (e.getLocation() == null) {
                    e.setLocation(XPathUtils.getXPathExprFromNode(tempEl));
                }

                throw e;
            }

            tempEl = DOMUtils.getNextSiblingElement(tempEl);
        }

        return mimePart;
    }
}