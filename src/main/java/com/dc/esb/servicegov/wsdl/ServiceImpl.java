package com.dc.esb.servicegov.wsdl;

import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.xml.namespace.QName;
import java.util.*;

/**
 * This class represents a service, which groups related
 * ports to provide some functionality.
 */
public class ServiceImpl extends AbstractWSDLElement implements Service {
    protected QName name = null;
    protected Map ports = new HashMap();
    protected List nativeAttributeNames =
            Arrays.asList(Constants.SERVICE_ATTR_NAMES);

    public static final long serialVersionUID = 1;

    /**
     * Set the name of this service.
     *
     * @param name the desired name
     */
    public void setQName(QName name) {
        this.name = name;
    }

    /**
     * Get the name of this service.
     *
     * @return the service name
     */
    public QName getQName() {
        return name;
    }

    /**
     * Add a port to this service.
     *
     * @param port the port to be added
     */
    public void addPort(Port port) {
        ports.put(port.getName(), port);
    }

    /**
     * Get the specified port.
     *
     * @param name the name of the desired port.
     * @return the corresponding port, or null if there wasn't
     *         any matching port
     */
    public Port getPort(String name) {
        return (Port) ports.get(name);
    }

    /**
     * Remove the specified port.
     *
     * @param name the name of the port to be removed.
     * @return the port which was removed
     */
    public Port removePort(String name) {
        return (Port) ports.remove(name);
    }

    /**
     * Get all the ports defined here.
     */
    public Map getPorts() {
        return ports;
    }

    public String toString() {
        StringBuffer strBuf = new StringBuffer();

        strBuf.append("Service: name=" + name);

        if (ports != null) {
            Iterator portIterator = ports.values().iterator();

            while (portIterator.hasNext()) {
                strBuf.append("\n" + portIterator.next());
            }
        }

        String superString = super.toString();
        if (!superString.equals("")) {
            strBuf.append("\n");
            strBuf.append(superString);
        }

        return strBuf.toString();
    }

    /**
     * Get the list of local attribute names defined for this element in
     * the WSDL specification.
     *
     * @return a List of Strings, one for each local attribute name
     */
    public List getNativeAttributeNames() {
        return nativeAttributeNames;
    }
}
