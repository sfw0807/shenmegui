
package javax.wsdl.extensions;

import com.dc.esb.servicegov.wsdl.Constants;
import com.dc.esb.servicegov.wsdl.util.xml.DOMUtils;
import org.w3c.dom.Element;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.xml.namespace.QName;

/**
 * This class is used to deserialize arbitrary elements into
 * UnknownExtensibilityElement instances.
 *
 * @see javax.wsdl.extensions.UnknownExtensibilityElement
 * @see javax.wsdl.extensions.UnknownExtensionSerializer
 */
public class UnknownExtensionDeserializer implements ExtensionDeserializer,
        java.io.Serializable {
    public static final long serialVersionUID = 1;

    public ExtensibilityElement unmarshall(Class parentType,
                                           QName elementType,
                                           Element el,
                                           Definition def,
                                           ExtensionRegistry extReg)
            throws WSDLException {
        UnknownExtensibilityElement unknownExt = new UnknownExtensibilityElement();
        String requiredStr = DOMUtils.getAttributeNS(el,
                Constants.NS_URI_WSDL,
                Constants.ATTR_REQUIRED);

        unknownExt.setElementType(elementType);

        if (requiredStr != null) {
            unknownExt.setRequired(new Boolean(requiredStr));
        }

        unknownExt.setElement(el);

        return unknownExt;
    }
}