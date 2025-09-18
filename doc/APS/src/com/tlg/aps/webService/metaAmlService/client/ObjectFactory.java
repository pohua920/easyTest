
package com.tlg.aps.webService.metaAmlService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.metaAmlService.client package. 
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

    private final static QName _AmlQueryResponse_QNAME = new QName("http://server.amlService.webService.cwp.tlg.com/", "amlQueryResponse");
    private final static QName _Exception_QNAME = new QName("http://server.amlService.webService.cwp.tlg.com/", "Exception");
    private final static QName _AmlQuery_QNAME = new QName("http://server.amlService.webService.cwp.tlg.com/", "amlQuery");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.metaAmlService.client
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
     * Create an instance of {@link AmlQueryResponse }
     * 
     */
    public AmlQueryResponse createAmlQueryResponse() {
        return new AmlQueryResponse();
    }

    /**
     * Create an instance of {@link AmlQuery }
     * 
     */
    public AmlQuery createAmlQuery() {
        return new AmlQuery();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AmlQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.amlService.webService.cwp.tlg.com/", name = "amlQueryResponse")
    public JAXBElement<AmlQueryResponse> createAmlQueryResponse(AmlQueryResponse value) {
        return new JAXBElement<AmlQueryResponse>(_AmlQueryResponse_QNAME, AmlQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.amlService.webService.cwp.tlg.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AmlQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.amlService.webService.cwp.tlg.com/", name = "amlQuery")
    public JAXBElement<AmlQuery> createAmlQuery(AmlQuery value) {
        return new JAXBElement<AmlQuery>(_AmlQuery_QNAME, AmlQuery.class, null, value);
    }

}
