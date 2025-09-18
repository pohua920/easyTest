
package com.tlg.aps.webService.addressCheckService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.addressCheckService.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddressCheck_QNAME = new QName("http://server.addressCheckService.webService.cwp.tlg.com/", "addressCheck");
    private final static QName _Exception_QNAME = new QName("http://server.addressCheckService.webService.cwp.tlg.com/", "Exception");
    private final static QName _AddressCheckResponse_QNAME = new QName("http://server.addressCheckService.webService.cwp.tlg.com/", "addressCheckResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.addressCheckService.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddressCheck }
     * 
     */
    public AddressCheck createAddressCheck() {
        return new AddressCheck();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link AddressCheckResponse }
     * 
     */
    public AddressCheckResponse createAddressCheckResponse() {
        return new AddressCheckResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressCheck }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressCheckService.webService.cwp.tlg.com/", name = "addressCheck")
    public JAXBElement<AddressCheck> createAddressCheck(AddressCheck value) {
        return new JAXBElement<AddressCheck>(_AddressCheck_QNAME, AddressCheck.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressCheckService.webService.cwp.tlg.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressCheckResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressCheckService.webService.cwp.tlg.com/", name = "addressCheckResponse")
    public JAXBElement<AddressCheckResponse> createAddressCheckResponse(AddressCheckResponse value) {
        return new JAXBElement<AddressCheckResponse>(_AddressCheckResponse_QNAME, AddressCheckResponse.class, null, value);
    }

}
