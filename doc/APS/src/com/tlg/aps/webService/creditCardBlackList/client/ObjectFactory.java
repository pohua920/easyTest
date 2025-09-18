
package com.tlg.aps.webService.creditCardBlackList.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.creditCardBlackList.client package. 
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

    private final static QName _CreditCardBlackListQuery_QNAME = new QName("http://server.creditCardBlackList.webService.aps.tlg.com/", "creditCardBlackListQuery");
    private final static QName _Exception_QNAME = new QName("http://server.creditCardBlackList.webService.aps.tlg.com/", "Exception");
    private final static QName _CreditCardBlackListQueryResponse_QNAME = new QName("http://server.creditCardBlackList.webService.aps.tlg.com/", "creditCardBlackListQueryResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.creditCardBlackList.client
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
     * Create an instance of {@link CreditCardBlackListQuery }
     * 
     */
    public CreditCardBlackListQuery createCreditCardBlackListQuery() {
        return new CreditCardBlackListQuery();
    }

    /**
     * Create an instance of {@link CreditCardBlackListQueryResponse }
     * 
     */
    public CreditCardBlackListQueryResponse createCreditCardBlackListQueryResponse() {
        return new CreditCardBlackListQueryResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardBlackListQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.creditCardBlackList.webService.aps.tlg.com/", name = "creditCardBlackListQuery")
    public JAXBElement<CreditCardBlackListQuery> createCreditCardBlackListQuery(CreditCardBlackListQuery value) {
        return new JAXBElement<CreditCardBlackListQuery>(_CreditCardBlackListQuery_QNAME, CreditCardBlackListQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.creditCardBlackList.webService.aps.tlg.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardBlackListQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.creditCardBlackList.webService.aps.tlg.com/", name = "creditCardBlackListQueryResponse")
    public JAXBElement<CreditCardBlackListQueryResponse> createCreditCardBlackListQueryResponse(CreditCardBlackListQueryResponse value) {
        return new JAXBElement<CreditCardBlackListQueryResponse>(_CreditCardBlackListQueryResponse_QNAME, CreditCardBlackListQueryResponse.class, null, value);
    }

}
