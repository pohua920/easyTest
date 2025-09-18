
package com.tlg.aps.webService.corePolicyService.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>policyEndorseInfoListVo complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="policyEndorseInfoListVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="policyEndorseInfoVos" type="{http://sinosoft.com.cn}policyEndorseInfoVo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "policyEndorseInfoListVo", propOrder = {
    "policyEndorseInfoVos"
})
public class PolicyEndorseInfoListVo {

    @XmlElement(nillable = true)
    protected List<PolicyEndorseInfoVo> policyEndorseInfoVos;

    /**
     * Gets the value of the policyEndorseInfoVos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the policyEndorseInfoVos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolicyEndorseInfoVos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PolicyEndorseInfoVo }
     * 
     * 
     */
    public List<PolicyEndorseInfoVo> getPolicyEndorseInfoVos() {
        if (policyEndorseInfoVos == null) {
            policyEndorseInfoVos = new ArrayList<PolicyEndorseInfoVo>();
        }
        return this.policyEndorseInfoVos;
    }

}
