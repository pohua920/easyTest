
package com.tlg.aps.webService.firRuleService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.firRuleService.client package. 
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
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
    private final static QName _Exception_QNAME = new QName("http://server.ruleService.webService.cwp.tlg.com/", "Exception");
    private final static QName _RuleCheck_QNAME = new QName("http://server.ruleService.webService.cwp.tlg.com/", "ruleCheck");
    private final static QName _RuleCheckResponse_QNAME = new QName("http://server.ruleService.webService.cwp.tlg.com/", "ruleCheckResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.firRuleService.client
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
     * Create an instance of {@link RuleCheck }
     * 
     */
    public RuleCheck createRuleCheck() {
        return new RuleCheck();
    }

    /**
     * Create an instance of {@link RuleCheckResponse }
     * 
     */
    public RuleCheckResponse createRuleCheckResponse() {
        return new RuleCheckResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ruleService.webService.cwp.tlg.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RuleCheck }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ruleService.webService.cwp.tlg.com/", name = "ruleCheck")
    public JAXBElement<RuleCheck> createRuleCheck(RuleCheck value) {
        return new JAXBElement<RuleCheck>(_RuleCheck_QNAME, RuleCheck.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RuleCheckResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ruleService.webService.cwp.tlg.com/", name = "ruleCheckResponse")
    public JAXBElement<RuleCheckResponse> createRuleCheckResponse(RuleCheckResponse value) {
        return new JAXBElement<RuleCheckResponse>(_RuleCheckResponse_QNAME, RuleCheckResponse.class, null, value);
    }

}
