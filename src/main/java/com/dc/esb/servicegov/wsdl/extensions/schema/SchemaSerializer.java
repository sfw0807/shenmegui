
package com.dc.esb.servicegov.wsdl.extensions.schema;

import com.dc.esb.servicegov.wsdl.Constants;
import com.dc.esb.servicegov.wsdl.util.xml.DOMUtils;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.ExtensionSerializer;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.extensions.schema.SchemaImport;
import javax.xml.namespace.QName;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import static com.dc.esb.servicegov.wsdl.extensions.schema.SchemaConstants.*;

/**
 * This class is used to serialize Schema instances
 * into the PrintWriter.
 *
 * @see SchemaImpl
 * @see SchemaDeserializer
 */
public class SchemaSerializer implements ExtensionSerializer, Serializable {
    public static final long serialVersionUID = 1;

    public void marshall(Class parentType,
                         QName elementType,
                         ExtensibilityElement extension,
                         PrintWriter pw,
                         Definition def,
                         ExtensionRegistry extReg)
            throws WSDLException {
        Schema schema = (Schema) extension;

        pw.print("    ");
        //DOM2Writer.serializeAsXML(schema.getElement(), def.getNamespaces(), pw);
        //print schema when element is null
        String tagName = DOMUtils.getQualifiedValue(SchemaConstants.NS_URI_XSD_2001, "schema", def);
        pw.print("    <" + tagName);
        if(null != schema.getTargetNamespace()){
            DOMUtils.printAttribute(Constants.ATTR_TARGET_NAMESPACE, schema.getTargetNamespace(), pw);
        }
        DOMUtils.printAttribute(ATTR_ELEMENT_FORM_DEFAULT, "qualified", pw);
        DOMUtils.printAttribute(ATTR_ATTR_FORM_DEFAULT, "qualified", pw);
        pw.println(">");
        Map importMap = schema.getImports();
        for(Iterator iterator = importMap.entrySet().iterator(); iterator.hasNext();){
            Map.Entry entry = (Map.Entry)iterator.next();
            Vector schemaImports = (Vector) entry.getValue();
            if(null != schemaImports){
                for(int i =0 ; i < schemaImports.size(); i ++){

                    SchemaImport schemaImport = (SchemaImport) schemaImports.get(i);
                    if(null != schemaImport){
                        pw.print("    ");
                        pw.print("    ");
                        String importTagName =  DOMUtils.getQualifiedValue(SchemaConstants.NS_URI_XSD_2001, "import", def);
                        pw.print("    <" + importTagName);
                        String nameSpace = schemaImport.getNamespaceURI();
                        if(null != nameSpace){
                            DOMUtils.printAttribute(Constants.ATTR_NAMESPACE, nameSpace, pw);
                        }
                        String schemaLocation = schemaImport.getSchemaLocationURI();
                        if(null != schemaLocation){
                            DOMUtils.printAttribute(ATTR_SCHEMA_LOCATION, schemaLocation, pw);
                        }
                        pw.println("/>");
                    }

                }
            }
        }

        pw.print("    ");
        pw.println("    </" +tagName+">");

    }
}