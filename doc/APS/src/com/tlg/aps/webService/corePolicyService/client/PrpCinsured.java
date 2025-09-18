
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>prpCinsured complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="prpCinsured">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://sinosoft.com.cn}prpCinsuredId" minOccurs="0"/>
 *         &lt;element name="prpCmain" type="{http://sinosoft.com.cn}prpCmain" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredNature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredIdentity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relateSerialNo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="identifyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identifyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="possessNature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessSort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="occupationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="educationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="linkerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="benefitRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="benefitFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="occupationGrade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredSort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="benefitType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nationFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prpCinsuredNatures" type="{http://sinosoft.com.cn}prpCinsuredNature" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prpCinsuredArtifs" type="{http://sinosoft.com.cn}prpCinsuredArtif" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prpCinsured", propOrder = {
    "id",
    "prpCmain",
    "riskCode",
    "language",
    "insuredType",
    "insuredCode",
    "insuredName",
    "insuredAddress",
    "insuredNature",
    "insuredFlag",
    "insuredIdentity",
    "relateSerialNo",
    "identifyType",
    "identifyNumber",
    "creditLevel",
    "possessNature",
    "businessSource",
    "businessSort",
    "occupationCode",
    "educationCode",
    "bank",
    "accountName",
    "account",
    "linkerName",
    "postAddress",
    "postCode",
    "phoneNumber",
    "mobile",
    "email",
    "benefitRate",
    "benefitFlag",
    "flag",
    "occupationGrade",
    "insuredSort",
    "benefitType",
    "nationFlag",
    "age",
    "sex",
    "prpCinsuredNatures",
    "prpCinsuredArtifs"
})
public class PrpCinsured {

    protected PrpCinsuredId id;
    protected PrpCmain prpCmain;
    protected String riskCode;
    protected String language;
    protected String insuredType;
    protected String insuredCode;
    protected String insuredName;
    protected String insuredAddress;
    protected String insuredNature;
    protected String insuredFlag;
    protected String insuredIdentity;
    protected Long relateSerialNo;
    protected String identifyType;
    protected String identifyNumber;
    protected String creditLevel;
    protected String possessNature;
    protected String businessSource;
    protected String businessSort;
    protected String occupationCode;
    protected String educationCode;
    protected String bank;
    protected String accountName;
    protected String account;
    protected String linkerName;
    protected String postAddress;
    protected String postCode;
    protected String phoneNumber;
    protected String mobile;
    protected String email;
    protected BigDecimal benefitRate;
    protected String benefitFlag;
    protected String flag;
    protected String occupationGrade;
    protected String insuredSort;
    protected String benefitType;
    protected String nationFlag;
    protected String age;
    protected String sex;
    @XmlElement(nillable = true)
    protected List<PrpCinsuredNature> prpCinsuredNatures;
    @XmlElement(nillable = true)
    protected List<PrpCinsuredArtif> prpCinsuredArtifs;

    /**
     * 取得 id 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCinsuredId }
     *     
     */
    public PrpCinsuredId getId() {
        return id;
    }

    /**
     * 設定 id 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCinsuredId }
     *     
     */
    public void setId(PrpCinsuredId value) {
        this.id = value;
    }

    /**
     * 取得 prpCmain 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCmain }
     *     
     */
    public PrpCmain getPrpCmain() {
        return prpCmain;
    }

    /**
     * 設定 prpCmain 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCmain }
     *     
     */
    public void setPrpCmain(PrpCmain value) {
        this.prpCmain = value;
    }

    /**
     * 取得 riskCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskCode() {
        return riskCode;
    }

    /**
     * 設定 riskCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskCode(String value) {
        this.riskCode = value;
    }

    /**
     * 取得 language 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 設定 language 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * 取得 insuredType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredType() {
        return insuredType;
    }

    /**
     * 設定 insuredType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredType(String value) {
        this.insuredType = value;
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
     * 取得 insuredAddress 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredAddress() {
        return insuredAddress;
    }

    /**
     * 設定 insuredAddress 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredAddress(String value) {
        this.insuredAddress = value;
    }

    /**
     * 取得 insuredNature 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredNature() {
        return insuredNature;
    }

    /**
     * 設定 insuredNature 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredNature(String value) {
        this.insuredNature = value;
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
     * 取得 insuredIdentity 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredIdentity() {
        return insuredIdentity;
    }

    /**
     * 設定 insuredIdentity 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredIdentity(String value) {
        this.insuredIdentity = value;
    }

    /**
     * 取得 relateSerialNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRelateSerialNo() {
        return relateSerialNo;
    }

    /**
     * 設定 relateSerialNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRelateSerialNo(Long value) {
        this.relateSerialNo = value;
    }

    /**
     * 取得 identifyType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifyType() {
        return identifyType;
    }

    /**
     * 設定 identifyType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifyType(String value) {
        this.identifyType = value;
    }

    /**
     * 取得 identifyNumber 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifyNumber() {
        return identifyNumber;
    }

    /**
     * 設定 identifyNumber 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifyNumber(String value) {
        this.identifyNumber = value;
    }

    /**
     * 取得 creditLevel 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditLevel() {
        return creditLevel;
    }

    /**
     * 設定 creditLevel 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditLevel(String value) {
        this.creditLevel = value;
    }

    /**
     * 取得 possessNature 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPossessNature() {
        return possessNature;
    }

    /**
     * 設定 possessNature 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPossessNature(String value) {
        this.possessNature = value;
    }

    /**
     * 取得 businessSource 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessSource() {
        return businessSource;
    }

    /**
     * 設定 businessSource 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessSource(String value) {
        this.businessSource = value;
    }

    /**
     * 取得 businessSort 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessSort() {
        return businessSort;
    }

    /**
     * 設定 businessSort 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessSort(String value) {
        this.businessSort = value;
    }

    /**
     * 取得 occupationCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupationCode() {
        return occupationCode;
    }

    /**
     * 設定 occupationCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupationCode(String value) {
        this.occupationCode = value;
    }

    /**
     * 取得 educationCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEducationCode() {
        return educationCode;
    }

    /**
     * 設定 educationCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEducationCode(String value) {
        this.educationCode = value;
    }

    /**
     * 取得 bank 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBank() {
        return bank;
    }

    /**
     * 設定 bank 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBank(String value) {
        this.bank = value;
    }

    /**
     * 取得 accountName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 設定 accountName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountName(String value) {
        this.accountName = value;
    }

    /**
     * 取得 account 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccount() {
        return account;
    }

    /**
     * 設定 account 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccount(String value) {
        this.account = value;
    }

    /**
     * 取得 linkerName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkerName() {
        return linkerName;
    }

    /**
     * 設定 linkerName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkerName(String value) {
        this.linkerName = value;
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
     * 取得 benefitRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBenefitRate() {
        return benefitRate;
    }

    /**
     * 設定 benefitRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBenefitRate(BigDecimal value) {
        this.benefitRate = value;
    }

    /**
     * 取得 benefitFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenefitFlag() {
        return benefitFlag;
    }

    /**
     * 設定 benefitFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenefitFlag(String value) {
        this.benefitFlag = value;
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

    /**
     * 取得 occupationGrade 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupationGrade() {
        return occupationGrade;
    }

    /**
     * 設定 occupationGrade 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupationGrade(String value) {
        this.occupationGrade = value;
    }

    /**
     * 取得 insuredSort 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredSort() {
        return insuredSort;
    }

    /**
     * 設定 insuredSort 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredSort(String value) {
        this.insuredSort = value;
    }

    /**
     * 取得 benefitType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenefitType() {
        return benefitType;
    }

    /**
     * 設定 benefitType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenefitType(String value) {
        this.benefitType = value;
    }

    /**
     * 取得 nationFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationFlag() {
        return nationFlag;
    }

    /**
     * 設定 nationFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationFlag(String value) {
        this.nationFlag = value;
    }

    /**
     * 取得 age 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAge() {
        return age;
    }

    /**
     * 設定 age 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAge(String value) {
        this.age = value;
    }

    /**
     * 取得 sex 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * 設定 sex 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * Gets the value of the prpCinsuredNatures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCinsuredNatures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCinsuredNatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCinsuredNature }
     * 
     * 
     */
    public List<PrpCinsuredNature> getPrpCinsuredNatures() {
        if (prpCinsuredNatures == null) {
            prpCinsuredNatures = new ArrayList<PrpCinsuredNature>();
        }
        return this.prpCinsuredNatures;
    }

    /**
     * Gets the value of the prpCinsuredArtifs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCinsuredArtifs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCinsuredArtifs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCinsuredArtif }
     * 
     * 
     */
    public List<PrpCinsuredArtif> getPrpCinsuredArtifs() {
        if (prpCinsuredArtifs == null) {
            prpCinsuredArtifs = new ArrayList<PrpCinsuredArtif>();
        }
        return this.prpCinsuredArtifs;
    }

}
