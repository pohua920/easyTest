
package com.tlg.aps.webService.prpinsAgentService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 */
/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.prpinsAgentService.client package. 
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

    private final static QName _PrpinsAgentQueryResponse_QNAME = new QName("http://server.prpinsAgentService.webService.cwp.tlg.com/", "prpinsAgentQueryResponse");
    private final static QName _Exception_QNAME = new QName("http://server.prpinsAgentService.webService.cwp.tlg.com/", "Exception");
    private final static QName _PrpinsAgentQuery_QNAME = new QName("http://server.prpinsAgentService.webService.cwp.tlg.com/", "prpinsAgentQuery");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.prpinsAgentService.client
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
     * Create an instance of {@link PrpinsAgentQueryResponse }
     * 
     */
    public PrpinsAgentQueryResponse createPrpinsAgentQueryResponse() {
        return new PrpinsAgentQueryResponse();
    }

    /**
     * Create an instance of {@link PrpinsAgentQuery }
     * 
     */
    public PrpinsAgentQuery createPrpinsAgentQuery() {
        return new PrpinsAgentQuery();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrpinsAgentQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.prpinsAgentService.webService.cwp.tlg.com/", name = "prpinsAgentQueryResponse")
    public JAXBElement<PrpinsAgentQueryResponse> createPrpinsAgentQueryResponse(PrpinsAgentQueryResponse value) {
        return new JAXBElement<PrpinsAgentQueryResponse>(_PrpinsAgentQueryResponse_QNAME, PrpinsAgentQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.prpinsAgentService.webService.cwp.tlg.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrpinsAgentQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.prpinsAgentService.webService.cwp.tlg.com/", name = "prpinsAgentQuery")
    public JAXBElement<PrpinsAgentQuery> createPrpinsAgentQuery(PrpinsAgentQuery value) {
        return new JAXBElement<PrpinsAgentQuery>(_PrpinsAgentQuery_QNAME, PrpinsAgentQuery.class, null, value);
    }

}
