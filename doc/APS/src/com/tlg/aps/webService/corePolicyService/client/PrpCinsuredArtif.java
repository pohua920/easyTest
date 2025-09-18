
package com.tlg.aps.webService.corePolicyService.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>prpCinsuredArtif complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="prpCinsuredArtif">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://sinosoft.com.cn}prpCinsuredArtifId" minOccurs="0"/>
 *         &lt;element name="prpCinsured" type="{http://sinosoft.com.cn}prpCinsured" minOccurs="0"/>
 *         &lt;element name="insuredFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="leaderName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="leaderID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="revenueRegistNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cartype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prpCinsuredArtif", propOrder = {
    "id",
    "prpCinsured",
    "insuredFlag",
    "leaderName",
    "leaderID",
    "phoneNumber",
    "postCode",
    "businessCode",
    "revenueRegistNo",
    "cartype",
    "flag"
})
public class PrpCinsuredArtif {

    protected PrpCinsuredArtifId id;
    protected PrpCinsured prpCinsured;
    protected String insuredFlag;
    protected String leaderName;
    protected String leaderID;
    protected String phoneNumber;
    protected String postCode;
    protected String businessCode;
    protected String revenueRegistNo;
    protected String cartype;
    protected String flag;

    /**
     * 取得 id 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCinsuredArtifId }
     *     
     */
    public PrpCinsuredArtifId getId() {
        return id;
    }

    /**
     * 設定 id 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCinsuredArtifId }
     *     
     */
    public void setId(PrpCinsuredArtifId value) {
        this.id = value;
    }

    /**
     * 取得 prpCinsured 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCinsured }
     *     
     */
    public PrpCinsured getPrpCinsured() {
        return prpCinsured;
    }

    /**
     * 設定 prpCinsured 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCinsured }
     *     
     */
    public void setPrpCinsured(PrpCinsured value) {
        this.prpCinsured = value;
    }

    /**
     * 取得 insuredFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredFlag() {
        return insuredFlag;
    }

    /**
     * 設定 insuredFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredFlag(String value) {
        this.insuredFlag = value;
    }

    /**
     * 取得 leaderName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeaderName() {
        return leaderName;
    }

    /**
     * 設定 leaderName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeaderName(String value) {
        this.leaderName = value;
    }

    /**
     * 取得 leaderID 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeaderID() {
        return leaderID;
    }

    /**
     * 設定 leaderID 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeaderID(String value) {
        this.leaderID = value;
    }

    /**
     * 取得 phoneNumber 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 設定 phoneNumber 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * 取得 postCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * 設定 postCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostCode(String value) {
        this.postCode = value;
    }

    /**
     * 取得 businessCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * 設定 businessCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessCode(String value) {
        this.businessCode = value;
    }

    /**
     * 取得 revenueRegistNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevenueRegistNo() {
        return revenueRegistNo;
    }

    /**
     * 設定 revenueRegistNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevenueRegistNo(String value) {
        this.revenueRegistNo = value;
    }

    /**
     * 取得 cartype 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCartype() {
        return cartype;
    }

    /**
     * 設定 cartype 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCartype(String value) {
        this.cartype = value;
    }

    /**
     * 取得 flag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 設定 flag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

}
