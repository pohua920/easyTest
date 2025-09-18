
package com.tlg.aps.webService.firCalAmountService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.firCalAmountService.client package. 
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

    private final static QName _Exception_QNAME = new QName("http://server.firCalService.webService.cwp.tlg.com/", "Exception");
    private final static QName _FirAmountCal_QNAME = new QName("http://server.firCalService.webService.cwp.tlg.com/", "firAmountCal");
    private final static QName _FirAmountCalResponse_QNAME = new QName("http://server.firCalService.webService.cwp.tlg.com/", "firAmountCalResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.firCalAmountService.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link FirAmountCal }
     * 
     */
    public FirAmountCal createFirAmountCal() {
        return new FirAmountCal();
    }

    /**
     * Create an instance of {@link FirAmountCalResponse }
     * 
     */
    public FirAmountCalResponse createFirAmountCalResponse() {
        return new FirAmountCalResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.firCalService.webService.cwp.tlg.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FirAmountCal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.firCalService.webService.cwp.tlg.com/", name = "firAmountCal")
    public JAXBElement<FirAmountCal> createFirAmountCal(FirAmountCal value) {
        return new JAXBElement<FirAmountCal>(_FirAmountCal_QNAME, FirAmountCal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FirAmountCalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.firCalService.webService.cwp.tlg.com/", name = "firAmountCalResponse")
    public JAXBElement<FirAmountCalResponse> createFirAmountCalResponse(FirAmountCalResponse value) {
        return new JAXBElement<FirAmountCalResponse>(_FirAmountCalResponse_QNAME, FirAmountCalResponse.class, null, value);
    }

}
