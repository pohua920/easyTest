
package com.tlg.aps.webService.corePolicyService.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>policyEndorseInfoListRequest complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="policyEndorseInfoListRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="policyEndorseInfoRequests" type="{http://webServices.tlg.com/}policyEndorseInfoRequest" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "policyEndorseInfoListRequest", namespace = "http://webServices.tlg.com/", propOrder = {
    "policyEndorseInfoRequests"
})
public class PolicyEndorseInfoListRequest {

    @XmlElement(namespace = "", nillable = true)
    protected List<PolicyEndorseInfoRequest> policyEndorseInfoRequests;

    /**
     * Gets the value of the policyEndorseInfoRequests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the policyEndorseInfoRequests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolicyEndorseInfoRequests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PolicyEndorseInfoRequest }
     * 
     * 
     */
    public List<PolicyEndorseInfoRequest> getPolicyEndorseInfoRequests() {
        if (policyEndorseInfoRequests == null) {
            policyEndorseInfoRequests = new ArrayList<PolicyEndorseInfoRequest>();
        }
        return this.policyEndorseInfoRequests;
    }

}
