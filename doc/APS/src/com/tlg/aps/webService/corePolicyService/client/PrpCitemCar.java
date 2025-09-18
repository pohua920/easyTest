
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>prpCitemCar complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="prpCitemCar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://sinosoft.com.cn}prpCitemCarId" minOccurs="0"/>
 *         &lt;element name="prpCmain" type="{http://sinosoft.com.cn}prpCmain" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carInsuredRelation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carOwner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clauseType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agreeDriverFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="newDeviceFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carPolicyNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="licenseNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="licenseColorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carKindCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carKindName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hkFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hkLicenseNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="engineNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vinNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="frameNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="runAreaCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="runAreaName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="runMiles" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="enrollDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="useYears" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="modelCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brandName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countryNature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="useNatureCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessClassCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seatCount" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="tonCount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="exhaustScale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="colorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="safeDevice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parkSite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownerAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="otherNature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="makeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="carusAge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="purchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="actualValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="invoiceNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carLoanFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insurerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastInsurer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carCheckStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carChecker" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carCheckTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialTreat" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="relievingAreaCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addOnCount" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="carDealerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carDealerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carCheckReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lviolatedTimes" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="sviolatedTimes" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="licenseKindCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registModelCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondHandCarFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondHandCarPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="runAreaDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="visaCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="originCarPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="vehicleStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="wholeWeight" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="vehicleBrand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vehicleStyleDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carModelID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vehicleCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chgOwnerFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loanVehicleFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transferDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cardealerflag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="helpcardusercode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certificateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fueltype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xubaoflag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deprecateRateFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prpCitemCar", propOrder = {
    "id",
    "prpCmain",
    "riskCode",
    "insuredTypeCode",
    "carInsuredRelation",
    "carOwner",
    "clauseType",
    "agreeDriverFlag",
    "newDeviceFlag",
    "carPolicyNo",
    "licenseNo",
    "licenseColorCode",
    "carKindCode",
    "carKindName",
    "hkFlag",
    "hkLicenseNo",
    "engineNo",
    "vinNo",
    "frameNo",
    "runAreaCode",
    "runAreaName",
    "runMiles",
    "enrollDate",
    "useYears",
    "modelCode",
    "brandName",
    "countryNature",
    "countryCode",
    "useNatureCode",
    "businessClassCode",
    "seatCount",
    "tonCount",
    "exhaustScale",
    "colorCode",
    "safeDevice",
    "parkSite",
    "ownerAddress",
    "otherNature",
    "rateCode",
    "makeDate",
    "carusAge",
    "currency",
    "purchasePrice",
    "actualValue",
    "invoiceNo",
    "carLoanFlag",
    "insurerCode",
    "lastInsurer",
    "carCheckStatus",
    "carChecker",
    "carCheckTime",
    "specialTreat",
    "relievingAreaCode",
    "addOnCount",
    "carDealerCode",
    "carDealerName",
    "remark",
    "flag",
    "carCheckReason",
    "lviolatedTimes",
    "sviolatedTimes",
    "licenseKindCode",
    "registModelCode",
    "secondHandCarFlag",
    "secondHandCarPrice",
    "runAreaDesc",
    "visaCode",
    "originCarPrice",
    "vehicleStyle",
    "wholeWeight",
    "vehicleBrand",
    "vehicleStyleDesc",
    "carModelID",
    "vehicleCode",
    "chgOwnerFlag",
    "loanVehicleFlag",
    "transferDate",
    "cardealerflag",
    "helpcardusercode",
    "certificateDate",
    "fueltype",
    "xubaoflag",
    "deprecateRateFlag"
})
public class PrpCitemCar {

    protected PrpCitemCarId id;
    protected PrpCmain prpCmain;
    protected String riskCode;
    protected String insuredTypeCode;
    protected String carInsuredRelation;
    protected String carOwner;
    protected String clauseType;
    protected String agreeDriverFlag;
    protected String newDeviceFlag;
    protected String carPolicyNo;
    protected String licenseNo;
    protected String licenseColorCode;
    protected String carKindCode;
    protected String carKindName;
    protected String hkFlag;
    protected String hkLicenseNo;
    protected String engineNo;
    protected String vinNo;
    protected String frameNo;
    protected String runAreaCode;
    protected String runAreaName;
    protected BigDecimal runMiles;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar enrollDate;
    protected Long useYears;
    protected String modelCode;
    protected String brandName;
    protected String countryNature;
    protected String countryCode;
    protected String useNatureCode;
    protected String businessClassCode;
    protected Long seatCount;
    protected BigDecimal tonCount;
    protected BigDecimal exhaustScale;
    protected String colorCode;
    protected String safeDevice;
    protected String parkSite;
    protected String ownerAddress;
    protected String otherNature;
    protected String rateCode;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar makeDate;
    protected String carusAge;
    protected String currency;
    protected BigDecimal purchasePrice;
    protected BigDecimal actualValue;
    protected String invoiceNo;
    protected String carLoanFlag;
    protected String insurerCode;
    protected String lastInsurer;
    protected String carCheckStatus;
    protected String carChecker;
    protected String carCheckTime;
    protected BigDecimal specialTreat;
    protected String relievingAreaCode;
    protected Long addOnCount;
    protected String carDealerCode;
    protected String carDealerName;
    protected String remark;
    protected String flag;
    protected String carCheckReason;
    protected Long lviolatedTimes;
    protected Long sviolatedTimes;
    protected String licenseKindCode;
    protected String registModelCode;
    protected String secondHandCarFlag;
    protected BigDecimal secondHandCarPrice;
    protected String runAreaDesc;
    protected String visaCode;
    protected BigDecimal originCarPrice;
    protected String vehicleStyle;
    protected BigDecimal wholeWeight;
    protected String vehicleBrand;
    protected String vehicleStyleDesc;
    protected String carModelID;
    protected String vehicleCode;
    protected String chgOwnerFlag;
    protected String loanVehicleFlag;
    protected String transferDate;
    protected String cardealerflag;
    protected String helpcardusercode;
    protected String certificateDate;
    protected String fueltype;
    protected String xubaoflag;
    protected String deprecateRateFlag;

    /**
     * 取得 id 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCitemCarId }
     *     
     */
    public PrpCitemCarId getId() {
        return id;
    }

    /**
     * 設定 id 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCitemCarId }
     *     
     */
    public void setId(PrpCitemCarId value) {
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
     * 取得 insuredTypeCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredTypeCode() {
        return insuredTypeCode;
    }

    /**
     * 設定 insuredTypeCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredTypeCode(String value) {
        this.insuredTypeCode = value;
    }

    /**
     * 取得 carInsuredRelation 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarInsuredRelation() {
        return carInsuredRelation;
    }

    /**
     * 設定 carInsuredRelation 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarInsuredRelation(String value) {
        this.carInsuredRelation = value;
    }

    /**
     * 取得 carOwner 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarOwner() {
        return carOwner;
    }

    /**
     * 設定 carOwner 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarOwner(String value) {
        this.carOwner = value;
    }

    /**
     * 取得 clauseType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClauseType() {
        return clauseType;
    }

    /**
     * 設定 clauseType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClauseType(String value) {
        this.clauseType = value;
    }

    /**
     * 取得 agreeDriverFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreeDriverFlag() {
        return agreeDriverFlag;
    }

    /**
     * 設定 agreeDriverFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreeDriverFlag(String value) {
        this.agreeDriverFlag = value;
    }

    /**
     * 取得 newDeviceFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDeviceFlag() {
        return newDeviceFlag;
    }

    /**
     * 設定 newDeviceFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDeviceFlag(String value) {
        this.newDeviceFlag = value;
    }

    /**
     * 取得 carPolicyNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarPolicyNo() {
        return carPolicyNo;
    }

    /**
     * 設定 carPolicyNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarPolicyNo(String value) {
        this.carPolicyNo = value;
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
     * 取得 licenseColorCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseColorCode() {
        return licenseColorCode;
    }

    /**
     * 設定 licenseColorCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseColorCode(String value) {
        this.licenseColorCode = value;
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
     * 取得 carKindName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarKindName() {
        return carKindName;
    }

    /**
     * 設定 carKindName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarKindName(String value) {
        this.carKindName = value;
    }

    /**
     * 取得 hkFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHkFlag() {
        return hkFlag;
    }

    /**
     * 設定 hkFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHkFlag(String value) {
        this.hkFlag = value;
    }

    /**
     * 取得 hkLicenseNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHkLicenseNo() {
        return hkLicenseNo;
    }

    /**
     * 設定 hkLicenseNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHkLicenseNo(String value) {
        this.hkLicenseNo = value;
    }

    /**
     * 取得 engineNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEngineNo() {
        return engineNo;
    }

    /**
     * 設定 engineNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEngineNo(String value) {
        this.engineNo = value;
    }

    /**
     * 取得 vinNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVinNo() {
        return vinNo;
    }

    /**
     * 設定 vinNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVinNo(String value) {
        this.vinNo = value;
    }

    /**
     * 取得 frameNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrameNo() {
        return frameNo;
    }

    /**
     * 設定 frameNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrameNo(String value) {
        this.frameNo = value;
    }

    /**
     * 取得 runAreaCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunAreaCode() {
        return runAreaCode;
    }

    /**
     * 設定 runAreaCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunAreaCode(String value) {
        this.runAreaCode = value;
    }

    /**
     * 取得 runAreaName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunAreaName() {
        return runAreaName;
    }

    /**
     * 設定 runAreaName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunAreaName(String value) {
        this.runAreaName = value;
    }

    /**
     * 取得 runMiles 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRunMiles() {
        return runMiles;
    }

    /**
     * 設定 runMiles 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRunMiles(BigDecimal value) {
        this.runMiles = value;
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
     * 取得 useYears 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUseYears() {
        return useYears;
    }

    /**
     * 設定 useYears 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUseYears(Long value) {
        this.useYears = value;
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
     * 取得 brandName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 設定 brandName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrandName(String value) {
        this.brandName = value;
    }

    /**
     * 取得 countryNature 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryNature() {
        return countryNature;
    }

    /**
     * 設定 countryNature 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryNature(String value) {
        this.countryNature = value;
    }

    /**
     * 取得 countryCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 設定 countryCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * 取得 useNatureCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseNatureCode() {
        return useNatureCode;
    }

    /**
     * 設定 useNatureCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseNatureCode(String value) {
        this.useNatureCode = value;
    }

    /**
     * 取得 businessClassCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessClassCode() {
        return businessClassCode;
    }

    /**
     * 設定 businessClassCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessClassCode(String value) {
        this.businessClassCode = value;
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
     * 取得 colorCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * 設定 colorCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColorCode(String value) {
        this.colorCode = value;
    }

    /**
     * 取得 safeDevice 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSafeDevice() {
        return safeDevice;
    }

    /**
     * 設定 safeDevice 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSafeDevice(String value) {
        this.safeDevice = value;
    }

    /**
     * 取得 parkSite 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParkSite() {
        return parkSite;
    }

    /**
     * 設定 parkSite 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParkSite(String value) {
        this.parkSite = value;
    }

    /**
     * 取得 ownerAddress 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerAddress() {
        return ownerAddress;
    }

    /**
     * 設定 ownerAddress 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerAddress(String value) {
        this.ownerAddress = value;
    }

    /**
     * 取得 otherNature 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherNature() {
        return otherNature;
    }

    /**
     * 設定 otherNature 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherNature(String value) {
        this.otherNature = value;
    }

    /**
     * 取得 rateCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateCode() {
        return rateCode;
    }

    /**
     * 設定 rateCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateCode(String value) {
        this.rateCode = value;
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
     * 取得 carusAge 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarusAge() {
        return carusAge;
    }

    /**
     * 設定 carusAge 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarusAge(String value) {
        this.carusAge = value;
    }

    /**
     * 取得 currency 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 設定 currency 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
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
     * 取得 actualValue 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getActualValue() {
        return actualValue;
    }

    /**
     * 設定 actualValue 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setActualValue(BigDecimal value) {
        this.actualValue = value;
    }

    /**
     * 取得 invoiceNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 設定 invoiceNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceNo(String value) {
        this.invoiceNo = value;
    }

    /**
     * 取得 carLoanFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarLoanFlag() {
        return carLoanFlag;
    }

    /**
     * 設定 carLoanFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarLoanFlag(String value) {
        this.carLoanFlag = value;
    }

    /**
     * 取得 insurerCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsurerCode() {
        return insurerCode;
    }

    /**
     * 設定 insurerCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsurerCode(String value) {
        this.insurerCode = value;
    }

    /**
     * 取得 lastInsurer 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastInsurer() {
        return lastInsurer;
    }

    /**
     * 設定 lastInsurer 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastInsurer(String value) {
        this.lastInsurer = value;
    }

    /**
     * 取得 carCheckStatus 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarCheckStatus() {
        return carCheckStatus;
    }

    /**
     * 設定 carCheckStatus 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarCheckStatus(String value) {
        this.carCheckStatus = value;
    }

    /**
     * 取得 carChecker 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarChecker() {
        return carChecker;
    }

    /**
     * 設定 carChecker 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarChecker(String value) {
        this.carChecker = value;
    }

    /**
     * 取得 carCheckTime 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarCheckTime() {
        return carCheckTime;
    }

    /**
     * 設定 carCheckTime 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarCheckTime(String value) {
        this.carCheckTime = value;
    }

    /**
     * 取得 specialTreat 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSpecialTreat() {
        return specialTreat;
    }

    /**
     * 設定 specialTreat 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSpecialTreat(BigDecimal value) {
        this.specialTreat = value;
    }

    /**
     * 取得 relievingAreaCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelievingAreaCode() {
        return relievingAreaCode;
    }

    /**
     * 設定 relievingAreaCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelievingAreaCode(String value) {
        this.relievingAreaCode = value;
    }

    /**
     * 取得 addOnCount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAddOnCount() {
        return addOnCount;
    }

    /**
     * 設定 addOnCount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAddOnCount(Long value) {
        this.addOnCount = value;
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
     * 取得 carDealerName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarDealerName() {
        return carDealerName;
    }

    /**
     * 設定 carDealerName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarDealerName(String value) {
        this.carDealerName = value;
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
     * 取得 carCheckReason 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarCheckReason() {
        return carCheckReason;
    }

    /**
     * 設定 carCheckReason 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarCheckReason(String value) {
        this.carCheckReason = value;
    }

    /**
     * 取得 lviolatedTimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLviolatedTimes() {
        return lviolatedTimes;
    }

    /**
     * 設定 lviolatedTimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLviolatedTimes(Long value) {
        this.lviolatedTimes = value;
    }

    /**
     * 取得 sviolatedTimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSviolatedTimes() {
        return sviolatedTimes;
    }

    /**
     * 設定 sviolatedTimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSviolatedTimes(Long value) {
        this.sviolatedTimes = value;
    }

    /**
     * 取得 licenseKindCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenseKindCode() {
        return licenseKindCode;
    }

    /**
     * 設定 licenseKindCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenseKindCode(String value) {
        this.licenseKindCode = value;
    }

    /**
     * 取得 registModelCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistModelCode() {
        return registModelCode;
    }

    /**
     * 設定 registModelCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistModelCode(String value) {
        this.registModelCode = value;
    }

    /**
     * 取得 secondHandCarFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondHandCarFlag() {
        return secondHandCarFlag;
    }

    /**
     * 設定 secondHandCarFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondHandCarFlag(String value) {
        this.secondHandCarFlag = value;
    }

    /**
     * 取得 secondHandCarPrice 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSecondHandCarPrice() {
        return secondHandCarPrice;
    }

    /**
     * 設定 secondHandCarPrice 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSecondHandCarPrice(BigDecimal value) {
        this.secondHandCarPrice = value;
    }

    /**
     * 取得 runAreaDesc 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunAreaDesc() {
        return runAreaDesc;
    }

    /**
     * 設定 runAreaDesc 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunAreaDesc(String value) {
        this.runAreaDesc = value;
    }

    /**
     * 取得 visaCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisaCode() {
        return visaCode;
    }

    /**
     * 設定 visaCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisaCode(String value) {
        this.visaCode = value;
    }

    /**
     * 取得 originCarPrice 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOriginCarPrice() {
        return originCarPrice;
    }

    /**
     * 設定 originCarPrice 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOriginCarPrice(BigDecimal value) {
        this.originCarPrice = value;
    }

    /**
     * 取得 vehicleStyle 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleStyle() {
        return vehicleStyle;
    }

    /**
     * 設定 vehicleStyle 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleStyle(String value) {
        this.vehicleStyle = value;
    }

    /**
     * 取得 wholeWeight 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWholeWeight() {
        return wholeWeight;
    }

    /**
     * 設定 wholeWeight 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWholeWeight(BigDecimal value) {
        this.wholeWeight = value;
    }

    /**
     * 取得 vehicleBrand 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    /**
     * 設定 vehicleBrand 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleBrand(String value) {
        this.vehicleBrand = value;
    }

    /**
     * 取得 vehicleStyleDesc 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleStyleDesc() {
        return vehicleStyleDesc;
    }

    /**
     * 設定 vehicleStyleDesc 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleStyleDesc(String value) {
        this.vehicleStyleDesc = value;
    }

    /**
     * 取得 carModelID 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarModelID() {
        return carModelID;
    }

    /**
     * 設定 carModelID 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarModelID(String value) {
        this.carModelID = value;
    }

    /**
     * 取得 vehicleCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleCode() {
        return vehicleCode;
    }

    /**
     * 設定 vehicleCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehicleCode(String value) {
        this.vehicleCode = value;
    }

    /**
     * 取得 chgOwnerFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChgOwnerFlag() {
        return chgOwnerFlag;
    }

    /**
     * 設定 chgOwnerFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChgOwnerFlag(String value) {
        this.chgOwnerFlag = value;
    }

    /**
     * 取得 loanVehicleFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanVehicleFlag() {
        return loanVehicleFlag;
    }

    /**
     * 設定 loanVehicleFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanVehicleFlag(String value) {
        this.loanVehicleFlag = value;
    }

    /**
     * 取得 transferDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransferDate() {
        return transferDate;
    }

    /**
     * 設定 transferDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransferDate(String value) {
        this.transferDate = value;
    }

    /**
     * 取得 cardealerflag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardealerflag() {
        return cardealerflag;
    }

    /**
     * 設定 cardealerflag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardealerflag(String value) {
        this.cardealerflag = value;
    }

    /**
     * 取得 helpcardusercode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHelpcardusercode() {
        return helpcardusercode;
    }

    /**
     * 設定 helpcardusercode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHelpcardusercode(String value) {
        this.helpcardusercode = value;
    }

    /**
     * 取得 certificateDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertificateDate() {
        return certificateDate;
    }

    /**
     * 設定 certificateDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertificateDate(String value) {
        this.certificateDate = value;
    }

    /**
     * 取得 fueltype 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFueltype() {
        return fueltype;
    }

    /**
     * 設定 fueltype 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFueltype(String value) {
        this.fueltype = value;
    }

    /**
     * 取得 xubaoflag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXubaoflag() {
        return xubaoflag;
    }

    /**
     * 設定 xubaoflag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXubaoflag(String value) {
        this.xubaoflag = value;
    }

    /**
     * 取得 deprecateRateFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeprecateRateFlag() {
        return deprecateRateFlag;
    }

    /**
     * 設定 deprecateRateFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeprecateRateFlag(String value) {
        this.deprecateRateFlag = value;
    }

}
