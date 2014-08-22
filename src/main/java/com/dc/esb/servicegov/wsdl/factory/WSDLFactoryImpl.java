package com.dc.esb.servicegov.wsdl.factory;


import com.dc.esb.servicegov.wsdl.DefinitionImpl;
import com.dc.esb.servicegov.wsdl.extensions.PopulatedExtensionRegistry;
import com.dc.esb.servicegov.wsdl.xml.WSDLReaderImpl;
import com.dc.esb.servicegov.wsdl.xml.WSDLWriterImpl;

import javax.wsdl.Definition;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.wsdl.xml.WSDLWriter;

/**
 * This class is a concrete implementation of the abstract class
 * WSDLFactory. Some ideas used here have been shamelessly
 * copied from the wonderful JAXP and Xerces work.
 */
public class WSDLFactoryImpl extends WSDLFactory {
    /**
     * Create a new instance of a Definition, with an instance
     * of a PopulatedExtensionRegistry as its ExtensionRegistry.
     *
     */
    public Definition newDefinition() {
        Definition def = new DefinitionImpl();
        ExtensionRegistry extReg = newPopulatedExtensionRegistry();

        def.setExtensionRegistry(extReg);

        return def;
    }

    /**
     * Create a new instance of a WSDLReader.
     */
    public WSDLReader newWSDLReader() {
        return new WSDLReaderImpl();
    }

    /**
     * Create a new instance of a WSDLWriter.
     */
    public WSDLWriter newWSDLWriter() {
        return new WSDLWriterImpl();
    }

    /**
     * Create a new instance of an ExtensionRegistry with pre-registered
     * serializers/deserializers for the SOAP, HTTP and MIME
     * extensions. Java extensionTypes are also mapped for all
     * the SOAP, HTTP and MIME extensions.
     */
    public ExtensionRegistry newPopulatedExtensionRegistry() {
        return new PopulatedExtensionRegistry();
    }
}