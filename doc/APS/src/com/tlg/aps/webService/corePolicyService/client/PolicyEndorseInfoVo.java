
package com.tlg.aps.webService.corePolicyService.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>policyEndorseInfoVo complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="policyEndorseInfoVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flowNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operateSite" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isPremiumCal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="policyNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="endorseNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endorseType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="startHour" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="endHour" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="policyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="directBusiness" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessNature" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extraComCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extraComName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="handlerIdentifyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handlerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler1Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="travelArea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="travelpalce" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insuredCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identifyType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insuredName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="headName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phoneTeleNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="postAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insuredIdentity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bearer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countryEName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isHighDengerOccupation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domicile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listedCabinetCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insurantInfoVos" type="{http://sinosoft.com.cn}insurantInfoVo" maxOccurs="unbounded"/>
 *         &lt;element name="itemkindInfoVos" type="{http://sinosoft.com.cn}itemkindInfoVos" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="epolicy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paymentMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endorseText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="autotransrenewflag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prodCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="togetherDealFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="packageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderSeq" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="printVirtualCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditcardNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="creditcardExpiryDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorizationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="schengenFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="travelVehicle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flightNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "policyEndorseInfoVo", propOrder = {
    "flowNo",
    "operateSite",
    "isPremiumCal",
    "policyNo",
    "endorseNo",
    "endorseType",
    "startDate",
    "startHour",
    "endDate",
    "endHour",
    "policyType",
    "directBusiness",
    "businessNature",
    "extraComCode",
    "extraComName",
    "handlerIdentifyNumber",
    "handlerName",
    "agentCode",
    "handler1Code",
    "remark",
    "travelArea",
    "travelpalce",
    "insuredCode",
    "identifyType",
    "insuredName",
    "headName",
    "birthday",
    "phoneTeleNumber",
    "mobile",
    "postCode",
    "postAddress",
    "insuredIdentity",
    "bearer",
    "countryEName",
    "isHighDengerOccupation",
    "domicile",
    "listedCabinetCompany",
    "insurantInfoVos",
    "itemkindInfoVos",
    "email",
    "epolicy",
    "paymentMethod",
    "endorseText",
    "autotransrenewflag",
    "prodCode",
    "togetherDealFlag",
    "packageCode",
    "orderSeq",
    "printVirtualCode",
    "payerName",
    "payerCode",
    "creditcardNO",
    "creditcardExpiryDate",
    "authorizationCode",
    "schengenFlag",
    "accountType",
    "travelVehicle",
    "flightNo"
})
public class PolicyEndorseInfoVo {

    @XmlElement(required = true)
    protected String flowNo;
    @XmlElement(required = true)
    protected String operateSite;
    @XmlElement(required = true)
    protected String isPremiumCal;
    @XmlElement(required = true)
    protected String policyNo;
    protected String endorseNo;
    protected String endorseType;
    @XmlElement(required = true)
    protected String startDate;
    protected int startHour;
    @XmlElement(required = true)
    protected String endDate;
    protected int endHour;
    protected String policyType;
    protected String directBusiness;
    @XmlElement(required = true)
    protected String businessNature;
    @XmlElement(required = true)
    protected String extraComCode;
    @XmlElement(required = true)
    protected String extraComName;
    protected String handlerIdentifyNumber;
    protected String handlerName;
    protected String agentCode;
    @XmlElement(required = true)
    protected String handler1Code;
    protected String remark;
    @XmlElement(required = true)
    protected String travelArea;
    @XmlElement(required = true)
    protected String travelpalce;
    @XmlElement(required = true)
    protected String insuredCode;
    @XmlElement(required = true)
    protected String identifyType;
    @XmlElement(required = true)
    protected String insuredName;
    protected String headName;
    protected String birthday;
    protected String phoneTeleNumber;
    protected String mobile;
    @XmlElement(required = true)
    protected String postCode;
    @XmlElement(required = true)
    protected String postAddress;
    @XmlElement(required = true)
    protected String insuredIdentity;
    protected String bearer;
    protected String countryEName;
    protected String isHighDengerOccupation;
    protected String domicile;
    protected String listedCabinetCompany;
    @XmlElement(required = true)
    protected List<InsurantInfoVo> insurantInfoVos;
    @XmlElement(nillable = true)
    protected List<ItemkindInfoVos> itemkindInfoVos;
    protected String email;
    protected String epolicy;
    protected String paymentMethod;
    protected String endorseText;
    protected String autotransrenewflag;
    protected String prodCode;
    protected String togetherDealFlag;
    protected String packageCode;
    protected String orderSeq;
    protected String printVirtualCode;
    protected String payerName;
    protected String payerCode;
    protected String creditcardNO;
    protected String creditcardExpiryDate;
    protected String authorizationCode;
    protected String schengenFlag;
    protected String accountType;
    protected String travelVehicle;
    protected String flightNo;

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
     * 取得 policyType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyType() {
        return policyType;
    }

    /**
     * 設定 policyType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyType(String value) {
        this.policyType = value;
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
     * 取得 travelArea 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTravelArea() {
        return travelArea;
    }

    /**
     * 設定 travelArea 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTravelArea(String value) {
        this.travelArea = value;
    }

    /**
     * 取得 travelpalce 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTravelpalce() {
        return travelpalce;
    }

    /**
     * 設定 travelpalce 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTravelpalce(String value) {
        this.travelpalce = value;
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
     * 取得 bearer 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBearer() {
        return bearer;
    }

    /**
     * 設定 bearer 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBearer(String value) {
        this.bearer = value;
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
     * Gets the value of the insurantInfoVos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the insurantInfoVos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInsurantInfoVos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InsurantInfoVo }
     * 
     * 
     */
    public List<InsurantInfoVo> getInsurantInfoVos() {
        if (insurantInfoVos == null) {
            insurantInfoVos = new ArrayList<InsurantInfoVo>();
        }
        return this.insurantInfoVos;
    }

    /**
     * Gets the value of the itemkindInfoVos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemkindInfoVos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemkindInfoVos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemkindInfoVos }
     * 
     * 
     */
    public List<ItemkindInfoVos> getItemkindInfoVos() {
        if (itemkindInfoVos == null) {
            itemkindInfoVos = new ArrayList<ItemkindInfoVos>();
        }
        return this.itemkindInfoVos;
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
     * 取得 paymentMethod 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * 設定 paymentMethod 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMethod(String value) {
        this.paymentMethod = value;
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
     * 取得 prodCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdCode() {
        return prodCode;
    }

    /**
     * 設定 prodCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdCode(String value) {
        this.prodCode = value;
    }

    /**
     * 取得 togetherDealFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTogetherDealFlag() {
        return togetherDealFlag;
    }

    /**
     * 設定 togetherDealFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTogetherDealFlag(String value) {
        this.togetherDealFlag = value;
    }

    /**
     * 取得 packageCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageCode() {
        return packageCode;
    }

    /**
     * 設定 packageCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageCode(String value) {
        this.packageCode = value;
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
     * 取得 printVirtualCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintVirtualCode() {
        return printVirtualCode;
    }

    /**
     * 設定 printVirtualCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintVirtualCode(String value) {
        this.printVirtualCode = value;
    }

    /**
     * 取得 payerName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayerName() {
        return payerName;
    }

    /**
     * 設定 payerName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayerName(String value) {
        this.payerName = value;
    }

    /**
     * 取得 payerCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayerCode() {
        return payerCode;
    }

    /**
     * 設定 payerCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayerCode(String value) {
        this.payerCode = value;
    }

    /**
     * 取得 creditcardNO 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditcardNO() {
        return creditcardNO;
    }

    /**
     * 設定 creditcardNO 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditcardNO(String value) {
        this.creditcardNO = value;
    }

    /**
     * 取得 creditcardExpiryDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditcardExpiryDate() {
        return creditcardExpiryDate;
    }

    /**
     * 設定 creditcardExpiryDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditcardExpiryDate(String value) {
        this.creditcardExpiryDate = value;
    }

    /**
     * 取得 authorizationCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * 設定 authorizationCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationCode(String value) {
        this.authorizationCode = value;
    }

    /**
     * 取得 schengenFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchengenFlag() {
        return schengenFlag;
    }

    /**
     * 設定 schengenFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchengenFlag(String value) {
        this.schengenFlag = value;
    }

    /**
     * 取得 accountType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * 設定 accountType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountType(String value) {
        this.accountType = value;
    }

    /**
     * 取得 travelVehicle 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTravelVehicle() {
        return travelVehicle;
    }

    /**
     * 設定 travelVehicle 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTravelVehicle(String value) {
        this.travelVehicle = value;
    }

    /**
     * 取得 flightNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 設定 flightNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightNo(String value) {
        this.flightNo = value;
    }

}
