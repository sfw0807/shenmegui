
package com.dc.esb.servicegov.wsdl.extensions.http;

import javax.wsdl.extensions.http.HTTPOperation;
import javax.xml.namespace.QName;

public class HTTPOperationImpl implements HTTPOperation {
    protected QName elementType = HTTPConstants.Q_ELEM_HTTP_OPERATION;
    // Uses the wrapper type so we can tell if it was set or not.
    protected Boolean required = null;
    protected String locationURI = null;

    public static final long serialVersionUID = 1;

    /**
     * Set the type of this extensibility element.
     *
     * @param elementType the type
     */
    public void setElementType(QName elementType) {
        this.elementType = elementType;
    }

    /**
     * Get the type of this extensibility element.
     *
     * @return the extensibility element's type
     */
    public QName getElementType() {
        return elementType;
    }

    /**
     * Set whether or not the semantics of this extension
     * are required. Relates to the wsdl:required attribute.
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * Get whether or not the semantics of this extension
     * are required. Relates to the wsdl:required attribute.
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * Set the location URI for this HTTP operation.
     *
     * @param locationURI the desired location URI
     */
    public void setLocationURI(String locationURI) {
        this.locationURI = locationURI;
    }

    /**
     * Get the location URI for this HTTP operation.
     */
    public String getLocationURI() {
        return locationURI;
    }

    public String toString() {
        StringBuffer strBuf = new StringBuffer();

        strBuf.append("HTTPOperation (" + elementType + "):");
        strBuf.append("\nrequired=" + required);

        if (locationURI != null) {
            strBuf.append("\nlocationURI=" + locationURI);
        }

        return strBuf.toString();
    }
}