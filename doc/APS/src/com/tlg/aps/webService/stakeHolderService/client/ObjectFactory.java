
package com.tlg.aps.webService.stakeHolderService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.stakeHolderService.client package. 
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

    private final static QName _Exception_QNAME = new QName("http://server.stakeHolderService.webService.cwp.tlg.com/", "Exception");
    private final static QName _StakeHolderQueryResponse_QNAME = new QName("http://server.stakeHolderService.webService.cwp.tlg.com/", "stakeHolderQueryResponse");
    private final static QName _StakeHolderQuery_QNAME = new QName("http://server.stakeHolderService.webService.cwp.tlg.com/", "stakeHolderQuery");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.stakeHolderService.client
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
     * Create an instance of {@link StakeHolderQuery }
     * 
     */
    public StakeHolderQuery createStakeHolderQuery() {
        return new StakeHolderQuery();
    }

    /**
     * Create an instance of {@link StakeHolderQueryResponse }
     * 
     */
    public StakeHolderQueryResponse createStakeHolderQueryResponse() {
        return new StakeHolderQueryResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.stakeHolderService.webService.cwp.tlg.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StakeHolderQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.stakeHolderService.webService.cwp.tlg.com/", name = "stakeHolderQueryResponse")
    public JAXBElement<StakeHolderQueryResponse> createStakeHolderQueryResponse(StakeHolderQueryResponse value) {
        return new JAXBElement<StakeHolderQueryResponse>(_StakeHolderQueryResponse_QNAME, StakeHolderQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StakeHolderQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.stakeHolderService.webService.cwp.tlg.com/", name = "stakeHolderQuery")
    public JAXBElement<StakeHolderQuery> createStakeHolderQuery(StakeHolderQuery value) {
        return new JAXBElement<StakeHolderQuery>(_StakeHolderQuery_QNAME, StakeHolderQuery.class, null, value);
    }

}
