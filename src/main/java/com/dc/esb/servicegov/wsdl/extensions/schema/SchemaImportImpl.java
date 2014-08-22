
package com.dc.esb.servicegov.wsdl.extensions.schema;

import javax.wsdl.extensions.schema.SchemaImport;

public class SchemaImportImpl extends SchemaReferenceImpl implements SchemaImport {
    public static final long serialVersionUID = 1;

    private String namespace = null;

    /**
     * @return Returns the namespace.
     */
    public String getNamespaceURI() {
        return this.namespace;
    }

    /**
     * @param namespace The namespace to set.
     */
    public void setNamespaceURI(String namespace) {
        this.namespace = namespace;
    }
}