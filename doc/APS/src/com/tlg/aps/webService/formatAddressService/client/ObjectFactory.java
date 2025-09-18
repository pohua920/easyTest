
package com.tlg.aps.webService.formatAddressService.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 *  mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tlg.aps.webService.formatAddressService.client package. 
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

    private final static QName _FormatResponse_QNAME = new QName("http://server.addressFormatService.webService.cwp.tlg.com/", "formatResponse");
    private final static QName _QueryStatus_QNAME = new QName("http://server.addressFormatService.webService.cwp.tlg.com/", "queryStatus");
    private final static QName _Exception_QNAME = new QName("http://server.addressFormatService.webService.cwp.tlg.com/", "Exception");
    private final static QName _Compare_QNAME = new QName("http://server.addressFormatService.webService.cwp.tlg.com/", "compare");
    private final static QName _CompareResponse_QNAME = new QName("http://server.addressFormatService.webService.cwp.tlg.com/", "compareResponse");
    private final static QName _FormatAddress_QNAME = new QName("http://server.addressFormatService.webService.cwp.tlg.com/", "formatAddress");
    private final static QName _Format_QNAME = new QName("http://server.addressFormatService.webService.cwp.tlg.com/", "format");
    private final static QName _FormatAddressResponse_QNAME = new QName("http://server.addressFormatService.webService.cwp.tlg.com/", "formatAddressResponse");
    private final static QName _QueryStatusResponse_QNAME = new QName("http://server.addressFormatService.webService.cwp.tlg.com/", "queryStatusResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tlg.aps.webService.formatAddressService.client
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
     * Create an instance of {@link QueryStatus }
     * 
     */
    public QueryStatus createQueryStatus() {
        return new QueryStatus();
    }

    /**
     * Create an instance of {@link FormatResponse }
     * 
     */
    public FormatResponse createFormatResponse() {
        return new FormatResponse();
    }

    /**
     * Create an instance of {@link FormatAddress }
     * 
     */
    public FormatAddress createFormatAddress() {
        return new FormatAddress();
    }

    /**
     * Create an instance of {@link CompareResponse }
     * 
     */
    public CompareResponse createCompareResponse() {
        return new CompareResponse();
    }

    /**
     * Create an instance of {@link Compare }
     * 
     */
    public Compare createCompare() {
        return new Compare();
    }

    /**
     * Create an instance of {@link FormatAddressResponse }
     * 
     */
    public FormatAddressResponse createFormatAddressResponse() {
        return new FormatAddressResponse();
    }

    /**
     * Create an instance of {@link Format }
     * 
     */
    public Format createFormat() {
        return new Format();
    }

    /**
     * Create an instance of {@link QueryStatusResponse }
     * 
     */
    public QueryStatusResponse createQueryStatusResponse() {
        return new QueryStatusResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormatResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressFormatService.webService.cwp.tlg.com/", name = "formatResponse")
    public JAXBElement<FormatResponse> createFormatResponse(FormatResponse value) {
        return new JAXBElement<FormatResponse>(_FormatResponse_QNAME, FormatResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressFormatService.webService.cwp.tlg.com/", name = "queryStatus")
    public JAXBElement<QueryStatus> createQueryStatus(QueryStatus value) {
        return new JAXBElement<QueryStatus>(_QueryStatus_QNAME, QueryStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressFormatService.webService.cwp.tlg.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Compare }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressFormatService.webService.cwp.tlg.com/", name = "compare")
    public JAXBElement<Compare> createCompare(Compare value) {
        return new JAXBElement<Compare>(_Compare_QNAME, Compare.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompareResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressFormatService.webService.cwp.tlg.com/", name = "compareResponse")
    public JAXBElement<CompareResponse> createCompareResponse(CompareResponse value) {
        return new JAXBElement<CompareResponse>(_CompareResponse_QNAME, CompareResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormatAddress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressFormatService.webService.cwp.tlg.com/", name = "formatAddress")
    public JAXBElement<FormatAddress> createFormatAddress(FormatAddress value) {
        return new JAXBElement<FormatAddress>(_FormatAddress_QNAME, FormatAddress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Format }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressFormatService.webService.cwp.tlg.com/", name = "format")
    public JAXBElement<Format> createFormat(Format value) {
        return new JAXBElement<Format>(_Format_QNAME, Format.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FormatAddressResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressFormatService.webService.cwp.tlg.com/", name = "formatAddressResponse")
    public JAXBElement<FormatAddressResponse> createFormatAddressResponse(FormatAddressResponse value) {
        return new JAXBElement<FormatAddressResponse>(_FormatAddressResponse_QNAME, FormatAddressResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.addressFormatService.webService.cwp.tlg.com/", name = "queryStatusResponse")
    public JAXBElement<QueryStatusResponse> createQueryStatusResponse(QueryStatusResponse value) {
        return new JAXBElement<QueryStatusResponse>(_QueryStatusResponse_QNAME, QueryStatusResponse.class, null, value);
    }

}
