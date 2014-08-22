package com.dc.esb.servicegov.wsdl;

import javax.wsdl.Types;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the &lt;types&gt; section of a WSDL document.
 */
public class TypesImpl extends AbstractWSDLElement implements Types {
    protected List nativeAttributeNames =
            Arrays.asList(Constants.TYPES_ATTR_NAMES);

    public static final long serialVersionUID = 1;

    public String toString() {
        StringBuffer strBuf = new StringBuffer();

        strBuf.append("Types:");

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
