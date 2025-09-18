
package com.tlg.aps.webService.blasklistQueryService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.blasklistQueryService.client package. 
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

    private final static QName _BlacklistQuery_QNAME = new QName("http://server.blacklistQueryService.webService.cwp.tlg.com/", "blacklistQuery");
    private final static QName _BlacklistQueryResponse_QNAME = new QName("http://server.blacklistQueryService.webService.cwp.tlg.com/", "blacklistQueryResponse");
    private final static QName _Exception_QNAME = new QName("http://server.blacklistQueryService.webService.cwp.tlg.com/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.blasklistQueryService.client
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
     * Create an instance of {@link BlacklistQueryResponse }
     * 
     */
    public BlacklistQueryResponse createBlacklistQueryResponse() {
        return new BlacklistQueryResponse();
    }

    /**
     * Create an instance of {@link BlacklistQuery }
     * 
     */
    public BlacklistQuery createBlacklistQuery() {
        return new BlacklistQuery();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BlacklistQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.blacklistQueryService.webService.cwp.tlg.com/", name = "blacklistQuery")
    public JAXBElement<BlacklistQuery> createBlacklistQuery(BlacklistQuery value) {
        return new JAXBElement<BlacklistQuery>(_BlacklistQuery_QNAME, BlacklistQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BlacklistQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.blacklistQueryService.webService.cwp.tlg.com/", name = "blacklistQueryResponse")
    public JAXBElement<BlacklistQueryResponse> createBlacklistQueryResponse(BlacklistQueryResponse value) {
        return new JAXBElement<BlacklistQueryResponse>(_BlacklistQueryResponse_QNAME, BlacklistQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.blacklistQueryService.webService.cwp.tlg.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
