
package com.tlg.aps.webService.firCalPremService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.firCalPremService.client package. 
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
    private final static QName _FirPremCal_QNAME = new QName("http://server.firCalService.webService.cwp.tlg.com/", "firPremCal");
    private final static QName _FirPremCalResponse_QNAME = new QName("http://server.firCalService.webService.cwp.tlg.com/", "firPremCalResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.firCalPremService.client
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
     * Create an instance of {@link FirPremCal }
     * 
     */
    public FirPremCal createFirPremCal() {
        return new FirPremCal();
    }

    /**
     * Create an instance of {@link FirPremCalResponse }
     * 
     */
    public FirPremCalResponse createFirPremCalResponse() {
        return new FirPremCalResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link FirPremCal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.firCalService.webService.cwp.tlg.com/", name = "firPremCal")
    public JAXBElement<FirPremCal> createFirPremCal(FirPremCal value) {
        return new JAXBElement<FirPremCal>(_FirPremCal_QNAME, FirPremCal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FirPremCalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.firCalService.webService.cwp.tlg.com/", name = "firPremCalResponse")
    public JAXBElement<FirPremCalResponse> createFirPremCalResponse(FirPremCalResponse value) {
        return new JAXBElement<FirPremCalResponse>(_FirPremCalResponse_QNAME, FirPremCalResponse.class, null, value);
    }

}
