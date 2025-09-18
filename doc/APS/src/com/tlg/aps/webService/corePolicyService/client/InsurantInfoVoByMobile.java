
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>insurantInfoVoByMobile complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="insurantInfoVoByMobile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serialno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insuredCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insuredName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phoneTeleNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countryEName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isHighDengerOccupation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domicile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listedCabinetCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectCodePremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="mobileVos" type="{http://sinosoft.com.cn}MobileVo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="headName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "insurantInfoVoByMobile", propOrder = {
    "serialno",
    "insuredCode",
    "insuredName",
    "birthday",
    "phoneTeleNumber",
    "mobile",
    "postCode",
    "postAddress",
    "countryEName",
    "isHighDengerOccupation",
    "domicile",
    "listedCabinetCompany",
    "projectCode",
    "projectCodePremium",
    "mobileVos",
    "email",
    "headName"
})
public class InsurantInfoVoByMobile {

    @XmlElement(required = true)
    protected String serialno;
    @XmlElement(required = true)
    protected String insuredCode;
    @XmlElement(required = true)
    protected String insuredName;
    @XmlElement(required = true)
    protected String birthday;
    protected String phoneTeleNumber;
    protected String mobile;
    protected String postCode;
    protected String postAddress;
    protected String countryEName;
    protected String isHighDengerOccupation;
    protected String domicile;
    protected String listedCabinetCompany;
    protected String projectCode;
    protected BigDecimal projectCodePremium;
    @XmlElement(nillable = true)
    protected List<MobileVo> mobileVos;
    protected String email;
    protected String headName;

    /**
     * 取得 serialno 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialno() {
        return serialno;
    }

    /**
     * 設定 serialno 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialno(String value) {
        this.serialno = value;
    }

    /**
     * 取得 insuredCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredCode() {
        return insuredCode;
    }

    /**
     * 設定 insuredCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredCode(String value) {
        this.insuredCode = value;
    }

    /**
     * 取得 insuredName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredName() {
        return insuredName;
    }

    /**
     * 設定 insuredName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredName(String value) {
        this.insuredName = value;
    }

    /**
     * 取得 birthday 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 設定 birthday 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthday(String value) {
        this.birthday = value;
    }

    /**
     * 取得 phoneTeleNumber 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneTeleNumber() {
        return phoneTeleNumber;
    }

    /**
     * 設定 phoneTeleNumber 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneTeleNumber(String value) {
        this.phoneTeleNumber = value;
    }

    /**
     * 取得 mobile 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 設定 mobile 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
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
     * 取得 postAddress 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostAddress() {
        return postAddress;
    }

    /**
     * 設定 postAddress 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostAddress(String value) {
        this.postAddress = value;
    }

    /**
     * 取得 countryEName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryEName() {
        return countryEName;
    }

    /**
     * 設定 countryEName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryEName(String value) {
        this.countryEName = value;
    }

    /**
     * 取得 isHighDengerOccupation 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHighDengerOccupation() {
        return isHighDengerOccupation;
    }

    /**
     * 設定 isHighDengerOccupation 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHighDengerOccupation(String value) {
        this.isHighDengerOccupation = value;
    }

    /**
     * 取得 domicile 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicile() {
        return domicile;
    }

    /**
     * 設定 domicile 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicile(String value) {
        this.domicile = value;
    }

    /**
     * 取得 listedCabinetCompany 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListedCabinetCompany() {
        return listedCabinetCompany;
    }

    /**
     * 設定 listedCabinetCompany 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListedCabinetCompany(String value) {
        this.listedCabinetCompany = value;
    }

    /**
     * 取得 projectCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * 設定 projectCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

    /**
     * 取得 projectCodePremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getProjectCodePremium() {
        return projectCodePremium;
    }

    /**
     * 設定 projectCodePremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setProjectCodePremium(BigDecimal value) {
        this.projectCodePremium = value;
    }

    /**
     * Gets the value of the mobileVos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mobileVos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMobileVos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MobileVo }
     * 
     * 
     */
    public List<MobileVo> getMobileVos() {
        if (mobileVos == null) {
            mobileVos = new ArrayList<MobileVo>();
        }
        return this.mobileVos;
    }

    /**
     * 取得 email 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * 設定 email 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * 取得 headName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeadName() {
        return headName;
    }

    /**
     * 設定 headName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeadName(String value) {
        this.headName = value;
    }

}
