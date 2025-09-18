
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>policyInfoVo complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="policyInfoVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestSystem" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="channelType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="businessNature" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="comCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="agentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handlerIdentifyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="startDateCI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="endDateCI" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="directBusiness" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ratePeriodType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ratePeriod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ratePeriodCI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="floatRateG" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="floatRateA" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="shortRateFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carDealerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carKindCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="licenseNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seatCount" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="tonCount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="exhaustScale" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="makeDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="enrollDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="purchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="modelCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isMotorcade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="insuredType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identifyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extraComCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kindInfoVos" type="{http://sinosoft.com.cn}kindInfoVo" maxOccurs="unbounded"/>
 *         &lt;element name="enfinschk" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="liabinschk" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="dvrinschk" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "policyInfoVo", propOrder = {
    "requestSystem",
    "channelType",
    "businessNature",
    "comCode",
    "agentCode",
    "handlerIdentifyNumber",
    "startDate",
    "endDate",
    "startDateCI",
    "endDateCI",
    "projectCode",
    "directBusiness",
    "ratePeriodType",
    "ratePeriod",
    "ratePeriodCI",
    "floatRateG",
    "floatRateA",
    "shortRateFlag",
    "carDealerCode",
    "carKindCode",
    "licenseNo",
    "seatCount",
    "tonCount",
    "exhaustScale",
    "makeDate",
    "enrollDate",
    "purchasePrice",
    "modelCode",
    "isMotorcade",
    "sex",
    "birthday",
    "insuredType",
    "identifyNumber",
    "extraComCode",
    "kindInfoVos",
    "enfinschk",
    "liabinschk",
    "dvrinschk"
})
public class PolicyInfoVo {

    @XmlElement(required = true)
    protected String requestSystem;
    @XmlElement(required = true)
    protected String channelType;
    @XmlElement(required = true)
    protected String businessNature;
    @XmlElement(required = true)
    protected String comCode;
    protected String agentCode;
    protected String handlerIdentifyNumber;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDateCI;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDateCI;
    protected String projectCode;
    @XmlElement(required = true)
    protected String directBusiness;
    protected String ratePeriodType;
    protected String ratePeriod;
    protected String ratePeriodCI;
    protected BigDecimal floatRateG;
    protected BigDecimal floatRateA;
    protected String shortRateFlag;
    protected String carDealerCode;
    @XmlElement(required = true)
    protected String carKindCode;
    protected String licenseNo;
    protected Long seatCount;
    protected BigDecimal tonCount;
    @XmlElement(required = true)
    protected BigDecimal exhaustScale;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar makeDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar enrollDate;
    @XmlElement(required = true)
    protected BigDecimal purchasePrice;
    @XmlElement(required = true)
    protected String modelCode;
    protected String isMotorcade;
    protected String sex;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthday;
    @XmlElement(required = true)
    protected String insuredType;
    protected String identifyNumber;
    protected String extraComCode;
    @XmlElement(required = true)
    protected List<KindInfoVo> kindInfoVos;
    protected boolean enfinschk;
    protected boolean liabinschk;
    protected boolean dvrinschk;

    /**
     * 取得 requestSystem 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestSystem() {
        return requestSystem;
    }

    /**
     * 設定 requestSystem 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestSystem(String value) {
        this.requestSystem = value;
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
     * 取得 comCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComCode() {
        return comCode;
    }

    /**
     * 設定 comCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComCode(String value) {
        this.comCode = value;
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
     * 取得 startDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * 設定 startDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * 取得 endDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * 設定 endDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * 取得 startDateCI 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDateCI() {
        return startDateCI;
    }

    /**
     * 設定 startDateCI 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDateCI(XMLGregorianCalendar value) {
        this.startDateCI = value;
    }

    /**
     * 取得 endDateCI 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDateCI() {
        return endDateCI;
    }

    /**
     * 設定 endDateCI 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDateCI(XMLGregorianCalendar value) {
        this.endDateCI = value;
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
     * 取得 ratePeriodType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePeriodType() {
        return ratePeriodType;
    }

    /**
     * 設定 ratePeriodType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePeriodType(String value) {
        this.ratePeriodType = value;
    }

    /**
     * 取得 ratePeriod 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePeriod() {
        return ratePeriod;
    }

    /**
     * 設定 ratePeriod 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePeriod(String value) {
        this.ratePeriod = value;
    }

    /**
     * 取得 ratePeriodCI 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePeriodCI() {
        return ratePeriodCI;
    }

    /**
     * 設定 ratePeriodCI 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePeriodCI(String value) {
        this.ratePeriodCI = value;
    }

    /**
     * 取得 floatRateG 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFloatRateG() {
        return floatRateG;
    }

    /**
     * 設定 floatRateG 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFloatRateG(BigDecimal value) {
        this.floatRateG = value;
    }

    /**
     * 取得 floatRateA 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFloatRateA() {
        return floatRateA;
    }

    /**
     * 設定 floatRateA 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFloatRateA(BigDecimal value) {
        this.floatRateA = value;
    }

    /**
     * 取得 shortRateFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortRateFlag() {
        return shortRateFlag;
    }

    /**
     * 設定 shortRateFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortRateFlag(String value) {
        this.shortRateFlag = value;
    }

    /**
     * 取得 carDealerCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarDealerCode() {
        return carDealerCode;
    }

    /**
     * 設定 carDealerCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarDealerCode(String value) {
        this.carDealerCode = value;
    }

    /**
     * 取得 carKindCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarKindCode() {
        return carKindCode;
    }

    /**
     * 設定 carKindCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarKindCode(String value) {
        this.carKindCode = value;
    }

    /**
     * 取得 licenseNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseNo() {
        return licenseNo;
    }

    /**
     * 設定 licenseNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseNo(String value) {
        this.licenseNo = value;
    }

    /**
     * 取得 seatCount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSeatCount() {
        return seatCount;
    }

    /**
     * 設定 seatCount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSeatCount(Long value) {
        this.seatCount = value;
    }

    /**
     * 取得 tonCount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTonCount() {
        return tonCount;
    }

    /**
     * 設定 tonCount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTonCount(BigDecimal value) {
        this.tonCount = value;
    }

    /**
     * 取得 exhaustScale 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExhaustScale() {
        return exhaustScale;
    }

    /**
     * 設定 exhaustScale 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExhaustScale(BigDecimal value) {
        this.exhaustScale = value;
    }

    /**
     * 取得 makeDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMakeDate() {
        return makeDate;
    }

    /**
     * 設定 makeDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMakeDate(XMLGregorianCalendar value) {
        this.makeDate = value;
    }

    /**
     * 取得 enrollDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnrollDate() {
        return enrollDate;
    }

    /**
     * 設定 enrollDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnrollDate(XMLGregorianCalendar value) {
        this.enrollDate = value;
    }

    /**
     * 取得 purchasePrice 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * 設定 purchasePrice 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPurchasePrice(BigDecimal value) {
        this.purchasePrice = value;
    }

    /**
     * 取得 modelCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * 設定 modelCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelCode(String value) {
        this.modelCode = value;
    }

    /**
     * 取得 isMotorcade 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsMotorcade() {
        return isMotorcade;
    }

    /**
     * 設定 isMotorcade 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsMotorcade(String value) {
        this.isMotorcade = value;
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
     * 取得 birthday 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthday() {
        return birthday;
    }

    /**
     * 設定 birthday 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthday(XMLGregorianCalendar value) {
        this.birthday = value;
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
     * Gets the value of the kindInfoVos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kindInfoVos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKindInfoVos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KindInfoVo }
     * 
     * 
     */
    public List<KindInfoVo> getKindInfoVos() {
        if (kindInfoVos == null) {
            kindInfoVos = new ArrayList<KindInfoVo>();
        }
        return this.kindInfoVos;
    }

    /**
     * 取得 enfinschk 特性的值.
     * 
     */
    public boolean isEnfinschk() {
        return enfinschk;
    }

    /**
     * 設定 enfinschk 特性的值.
     * 
     */
    public void setEnfinschk(boolean value) {
        this.enfinschk = value;
    }

    /**
     * 取得 liabinschk 特性的值.
     * 
     */
    public boolean isLiabinschk() {
        return liabinschk;
    }

    /**
     * 設定 liabinschk 特性的值.
     * 
     */
    public void setLiabinschk(boolean value) {
        this.liabinschk = value;
    }

    /**
     * 取得 dvrinschk 特性的值.
     * 
     */
    public boolean isDvrinschk() {
        return dvrinschk;
    }

    /**
     * 設定 dvrinschk 特性的值.
     * 
     */
    public void setDvrinschk(boolean value) {
        this.dvrinschk = value;
    }

}
