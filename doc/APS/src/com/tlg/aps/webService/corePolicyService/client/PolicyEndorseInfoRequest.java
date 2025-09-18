
package com.tlg.aps.webService.corePolicyService.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>policyEndorseInfoRequest complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="policyEndorseInfoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flowNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operateSite" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isPremiumCal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="policyNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="endorseNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endorseType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endorseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="startHour" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="endHour" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="directBusiness" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessNature" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extraComCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extraComName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="handlerIdentifyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handlerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler1Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insuredName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="headName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listedCabinetCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phoneTeleNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="postAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insuredIdentity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryEName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isHighDengerOccupation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domicile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insurantInfoVoByMobiles" type="{http://sinosoft.com.cn}insurantInfoVoByMobile" maxOccurs="unbounded"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="epolicy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endorseText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="autotransrenewflag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="applicationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="branchcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="branchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recommenderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distributorId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="promotionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="planStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handlerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "policyEndorseInfoRequest", namespace = "http://webServices.tlg.com/", propOrder = {
    "flowNo",
    "operateSite",
    "isPremiumCal",
    "policyNo",
    "endorseNo",
    "endorseType",
    "endorseDate",
    "startDate",
    "startHour",
    "endDate",
    "endHour",
    "directBusiness",
    "businessNature",
    "extraComCode",
    "extraComName",
    "handlerIdentifyNumber",
    "handlerName",
    "agentCode",
    "handler1Code",
    "remark",
    "insuredCode",
    "insuredName",
    "headName",
    "birthday",
    "listedCabinetCompany",
    "phoneTeleNumber",
    "mobile",
    "postCode",
    "postAddress",
    "insuredIdentity",
    "countryEName",
    "isHighDengerOccupation",
    "domicile",
    "insurantInfoVoByMobiles",
    "email",
    "epolicy",
    "endorseText",
    "autotransrenewflag",
    "applicationId",
    "orderSeq",
    "branchcode",
    "branchName",
    "recommenderId",
    "distributorId",
    "promotionId",
    "payNo",
    "planStartDate",
    "planEndDate",
    "agentName",
    "channelType",
    "handlerCode"
})
public class PolicyEndorseInfoRequest {

    @XmlElement(namespace = "", required = true)
    protected String flowNo;
    @XmlElement(namespace = "", required = true)
    protected String operateSite;
    @XmlElement(namespace = "", required = true)
    protected String isPremiumCal;
    @XmlElement(namespace = "", required = true)
    protected String policyNo;
    @XmlElement(namespace = "")
    protected String endorseNo;
    @XmlElement(namespace = "")
    protected String endorseType;
    @XmlElement(namespace = "")
    protected String endorseDate;
    @XmlElement(namespace = "", required = true)
    protected String startDate;
    @XmlElement(namespace = "")
    protected int startHour;
    @XmlElement(namespace = "", required = true)
    protected String endDate;
    @XmlElement(namespace = "")
    protected int endHour;
    @XmlElement(namespace = "")
    protected String directBusiness;
    @XmlElement(namespace = "", required = true)
    protected String businessNature;
    @XmlElement(namespace = "", required = true)
    protected String extraComCode;
    @XmlElement(namespace = "", required = true)
    protected String extraComName;
    @XmlElement(namespace = "")
    protected String handlerIdentifyNumber;
    @XmlElement(namespace = "")
    protected String handlerName;
    @XmlElement(namespace = "")
    protected String agentCode;
    @XmlElement(namespace = "", required = true)
    protected String handler1Code;
    @XmlElement(namespace = "")
    protected String remark;
    @XmlElement(namespace = "", required = true)
    protected String insuredCode;
    @XmlElement(namespace = "", required = true)
    protected String insuredName;
    @XmlElement(namespace = "")
    protected String headName;
    @XmlElement(namespace = "")
    protected String birthday;
    @XmlElement(namespace = "")
    protected String listedCabinetCompany;
    @XmlElement(namespace = "")
    protected String phoneTeleNumber;
    @XmlElement(namespace = "")
    protected String mobile;
    @XmlElement(namespace = "", required = true)
    protected String postCode;
    @XmlElement(namespace = "", required = true)
    protected String postAddress;
    @XmlElement(namespace = "", required = true)
    protected String insuredIdentity;
    @XmlElement(namespace = "")
    protected String countryEName;
    @XmlElement(namespace = "")
    protected String isHighDengerOccupation;
    @XmlElement(namespace = "")
    protected String domicile;
    @XmlElement(namespace = "", required = true)
    protected List<InsurantInfoVoByMobile> insurantInfoVoByMobiles;
    @XmlElement(namespace = "")
    protected String email;
    @XmlElement(namespace = "")
    protected String epolicy;
    @XmlElement(namespace = "")
    protected String endorseText;
    @XmlElement(namespace = "")
    protected String autotransrenewflag;
    @XmlElement(namespace = "")
    protected String applicationId;
    @XmlElement(namespace = "")
    protected String orderSeq;
    @XmlElement(namespace = "")
    protected String branchcode;
    @XmlElement(namespace = "")
    protected String branchName;
    @XmlElement(namespace = "")
    protected String recommenderId;
    @XmlElement(namespace = "")
    protected String distributorId;
    @XmlElement(namespace = "")
    protected String promotionId;
    @XmlElement(namespace = "")
    protected Integer payNo;
    @XmlElement(namespace = "")
    protected String planStartDate;
    @XmlElement(namespace = "")
    protected String planEndDate;
    @XmlElement(namespace = "")
    protected String agentName;
    @XmlElement(namespace = "")
    protected String channelType;
    @XmlElement(namespace = "")
    protected String handlerCode;

    /**
     * 取得 flowNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowNo() {
        return flowNo;
    }

    /**
     * 設定 flowNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowNo(String value) {
        this.flowNo = value;
    }

    /**
     * 取得 operateSite 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperateSite() {
        return operateSite;
    }

    /**
     * 設定 operateSite 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperateSite(String value) {
        this.operateSite = value;
    }

    /**
     * 取得 isPremiumCal 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPremiumCal() {
        return isPremiumCal;
    }

    /**
     * 設定 isPremiumCal 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPremiumCal(String value) {
        this.isPremiumCal = value;
    }

    /**
     * 取得 policyNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyNo() {
        return policyNo;
    }

    /**
     * 設定 policyNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyNo(String value) {
        this.policyNo = value;
    }

    /**
     * 取得 endorseNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndorseNo() {
        return endorseNo;
    }

    /**
     * 設定 endorseNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndorseNo(String value) {
        this.endorseNo = value;
    }

    /**
     * 取得 endorseType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndorseType() {
        return endorseType;
    }

    /**
     * 設定 endorseType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndorseType(String value) {
        this.endorseType = value;
    }

    /**
     * 取得 endorseDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndorseDate() {
        return endorseDate;
    }

    /**
     * 設定 endorseDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndorseDate(String value) {
        this.endorseDate = value;
    }

    /**
     * 取得 startDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 設定 startDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(String value) {
        this.startDate = value;
    }

    /**
     * 取得 startHour 特性的值.
     * 
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * 設定 startHour 特性的值.
     * 
     */
    public void setStartHour(int value) {
        this.startHour = value;
    }

    /**
     * 取得 endDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 設定 endDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

    /**
     * 取得 endHour 特性的值.
     * 
     */
    public int getEndHour() {
        return endHour;
    }

    /**
     * 設定 endHour 特性的值.
     * 
     */
    public void setEndHour(int value) {
        this.endHour = value;
    }

    /**
     * 取得 directBusiness 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectBusiness() {
        return directBusiness;
    }

    /**
     * 設定 directBusiness 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectBusiness(String value) {
        this.directBusiness = value;
    }

    /**
     * 取得 businessNature 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessNature() {
        return businessNature;
    }

    /**
     * 設定 businessNature 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessNature(String value) {
        this.businessNature = value;
    }

    /**
     * 取得 extraComCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtraComCode() {
        return extraComCode;
    }

    /**
     * 設定 extraComCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtraComCode(String value) {
        this.extraComCode = value;
    }

    /**
     * 取得 extraComName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtraComName() {
        return extraComName;
    }

    /**
     * 設定 extraComName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtraComName(String value) {
        this.extraComName = value;
    }

    /**
     * 取得 handlerIdentifyNumber 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlerIdentifyNumber() {
        return handlerIdentifyNumber;
    }

    /**
     * 設定 handlerIdentifyNumber 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlerIdentifyNumber(String value) {
        this.handlerIdentifyNumber = value;
    }

    /**
     * 取得 handlerName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlerName() {
        return handlerName;
    }

    /**
     * 設定 handlerName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlerName(String value) {
        this.handlerName = value;
    }

    /**
     * 取得 agentCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCode() {
        return agentCode;
    }

    /**
     * 設定 agentCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCode(String value) {
        this.agentCode = value;
    }

    /**
     * 取得 handler1Code 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler1Code() {
        return handler1Code;
    }

    /**
     * 設定 handler1Code 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler1Code(String value) {
        this.handler1Code = value;
    }

    /**
     * 取得 remark 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 設定 remark 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
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
     * Gets the value of the insurantInfoVoByMobiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the insurantInfoVoByMobiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInsurantInfoVoByMobiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InsurantInfoVoByMobile }
     * 
     * 
     */
    public List<InsurantInfoVoByMobile> getInsurantInfoVoByMobiles() {
        if (insurantInfoVoByMobiles == null) {
            insurantInfoVoByMobiles = new ArrayList<InsurantInfoVoByMobile>();
        }
        return this.insurantInfoVoByMobiles;
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
     * 取得 epolicy 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEpolicy() {
        return epolicy;
    }

    /**
     * 設定 epolicy 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEpolicy(String value) {
        this.epolicy = value;
    }

    /**
     * 取得 endorseText 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndorseText() {
        return endorseText;
    }

    /**
     * 設定 endorseText 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndorseText(String value) {
        this.endorseText = value;
    }

    /**
     * 取得 autotransrenewflag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutotransrenewflag() {
        return autotransrenewflag;
    }

    /**
     * 設定 autotransrenewflag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutotransrenewflag(String value) {
        this.autotransrenewflag = value;
    }

    /**
     * 取得 applicationId 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * 設定 applicationId 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationId(String value) {
        this.applicationId = value;
    }

    /**
     * 取得 orderSeq 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderSeq() {
        return orderSeq;
    }

    /**
     * 設定 orderSeq 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderSeq(String value) {
        this.orderSeq = value;
    }

    /**
     * 取得 branchcode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchcode() {
        return branchcode;
    }

    /**
     * 設定 branchcode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchcode(String value) {
        this.branchcode = value;
    }

    /**
     * 取得 branchName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 設定 branchName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchName(String value) {
        this.branchName = value;
    }

    /**
     * 取得 recommenderId 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommenderId() {
        return recommenderId;
    }

    /**
     * 設定 recommenderId 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommenderId(String value) {
        this.recommenderId = value;
    }

    /**
     * 取得 distributorId 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistributorId() {
        return distributorId;
    }

    /**
     * 設定 distributorId 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistributorId(String value) {
        this.distributorId = value;
    }

    /**
     * 取得 promotionId 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionId() {
        return promotionId;
    }

    /**
     * 設定 promotionId 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionId(String value) {
        this.promotionId = value;
    }

    /**
     * 取得 payNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPayNo() {
        return payNo;
    }

    /**
     * 設定 payNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPayNo(Integer value) {
        this.payNo = value;
    }

    /**
     * 取得 planStartDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanStartDate() {
        return planStartDate;
    }

    /**
     * 設定 planStartDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanStartDate(String value) {
        this.planStartDate = value;
    }

    /**
     * 取得 planEndDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanEndDate() {
        return planEndDate;
    }

    /**
     * 設定 planEndDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanEndDate(String value) {
        this.planEndDate = value;
    }

    /**
     * 取得 agentName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * 設定 agentName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
    }

    /**
     * 取得 channelType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * 設定 channelType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelType(String value) {
        this.channelType = value;
    }

    /**
     * 取得 handlerCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlerCode() {
        return handlerCode;
    }

    /**
     * 設定 handlerCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlerCode(String value) {
        this.handlerCode = value;
    }

}
