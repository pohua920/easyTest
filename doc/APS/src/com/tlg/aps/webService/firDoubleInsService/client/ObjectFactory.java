
package com.tlg.aps.webService.firDoubleInsService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.firDoubleInsService.client package. 
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
    private final static QName _QueryDoubleInsResponse_QNAME = new QName("http://server.firCalService.webService.cwp.tlg.com/", "queryDoubleInsResponse");
    private final static QName _QueryDoubleIns_QNAME = new QName("http://server.firCalService.webService.cwp.tlg.com/", "queryDoubleIns");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.firDoubleInsService.client
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
     * Create an instance of {@link QueryDoubleInsResponse }
     * 
     */
    public QueryDoubleInsResponse createQueryDoubleInsResponse() {
        return new QueryDoubleInsResponse();
    }

    /**
     * Create an instance of {@link QueryDoubleIns }
     * 
     */
    public QueryDoubleIns createQueryDoubleIns() {
        return new QueryDoubleIns();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDoubleInsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.firCalService.webService.cwp.tlg.com/", name = "queryDoubleInsResponse")
    public JAXBElement<QueryDoubleInsResponse> createQueryDoubleInsResponse(QueryDoubleInsResponse value) {
        return new JAXBElement<QueryDoubleInsResponse>(_QueryDoubleInsResponse_QNAME, QueryDoubleInsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryDoubleIns }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.firCalService.webService.cwp.tlg.com/", name = "queryDoubleIns")
    public JAXBElement<QueryDoubleIns> createQueryDoubleIns(QueryDoubleIns value) {
        return new JAXBElement<QueryDoubleIns>(_QueryDoubleIns_QNAME, QueryDoubleIns.class, null, value);
    }

}
