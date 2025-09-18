
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>insurantInfoVo complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="insurantInfoVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serialno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insuredCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insuredName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identifyType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phoneTeleNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="postAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insurantNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brigadeAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="brigadePremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="deathDisabilityAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="deathDisabilityPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="disabilityAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="disabilityPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="actualPaidAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="actualPaidPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="overseasAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="overseasPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="newOverseasAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="newOverseasPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="travelInc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="foodpoisoningAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="foodpoisoningPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="majorburnsAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="majorburnsPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="publictransportAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="publictransportPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="travelPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="injuryFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tourismInsFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="benefitInfoVos" type="{http://sinosoft.com.cn}benefitInfoVo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="countryEName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isHighDengerOccupation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domicile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listedCabinetCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemkindInfoVos" type="{http://sinosoft.com.cn}itemkindInfoVos" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="rrpreInsured" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rrpreIDNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rrpreIDType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rrpreIdentify" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rrpreCountryEName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="petInformatVos" type="{http://sinosoft.com.cn}PetInformatVo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectCodePremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="togetherDealFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "insurantInfoVo", propOrder = {
    "serialno",
    "insuredCode",
    "insuredName",
    "identifyType",
    "birthday",
    "phoneTeleNumber",
    "mobile",
    "postCode",
    "postAddress",
    "insurantNumber",
    "brigadeAmount",
    "brigadePremium",
    "deathDisabilityAmount",
    "deathDisabilityPremium",
    "disabilityAmount",
    "disabilityPremium",
    "actualPaidAmount",
    "actualPaidPremium",
    "overseasAmount",
    "overseasPremium",
    "newOverseasAmount",
    "newOverseasPremium",
    "travelInc",
    "foodpoisoningAmount",
    "foodpoisoningPremium",
    "majorburnsAmount",
    "majorburnsPremium",
    "publictransportAmount",
    "publictransportPremium",
    "travelPremium",
    "injuryFlag",
    "tourismInsFlag",
    "benefitInfoVos",
    "countryEName",
    "isHighDengerOccupation",
    "domicile",
    "listedCabinetCompany",
    "itemkindInfoVos",
    "rrpreInsured",
    "rrpreIDNumber",
    "rrpreIDType",
    "rrpreIdentify",
    "rrpreCountryEName",
    "petInformatVos",
    "projectCode",
    "projectCodePremium",
    "togetherDealFlag",
    "email"
})
public class InsurantInfoVo {

    @XmlElement(required = true)
    protected String serialno;
    @XmlElement(required = true)
    protected String insuredCode;
    @XmlElement(required = true)
    protected String insuredName;
    @XmlElement(required = true)
    protected String identifyType;
    @XmlElement(required = true)
    protected String birthday;
    protected String phoneTeleNumber;
    protected String mobile;
    protected String postCode;
    protected String postAddress;
    protected String insurantNumber;
    protected BigDecimal brigadeAmount;
    protected BigDecimal brigadePremium;
    protected BigDecimal deathDisabilityAmount;
    protected BigDecimal deathDisabilityPremium;
    protected BigDecimal disabilityAmount;
    protected BigDecimal disabilityPremium;
    protected BigDecimal actualPaidAmount;
    protected BigDecimal actualPaidPremium;
    protected BigDecimal overseasAmount;
    protected BigDecimal overseasPremium;
    protected BigDecimal newOverseasAmount;
    protected BigDecimal newOverseasPremium;
    protected String travelInc;
    protected BigDecimal foodpoisoningAmount;
    protected BigDecimal foodpoisoningPremium;
    protected BigDecimal majorburnsAmount;
    protected BigDecimal majorburnsPremium;
    protected BigDecimal publictransportAmount;
    protected BigDecimal publictransportPremium;
    protected BigDecimal travelPremium;
    @XmlElement(required = true)
    protected String injuryFlag;
    protected String tourismInsFlag;
    @XmlElement(nillable = true)
    protected List<BenefitInfoVo> benefitInfoVos;
    protected String countryEName;
    protected String isHighDengerOccupation;
    protected String domicile;
    protected String listedCabinetCompany;
    @XmlElement(nillable = true)
    protected List<ItemkindInfoVos> itemkindInfoVos;
    protected String rrpreInsured;
    protected String rrpreIDNumber;
    protected String rrpreIDType;
    protected String rrpreIdentify;
    protected String rrpreCountryEName;
    @XmlElement(nillable = true)
    protected List<PetInformatVo> petInformatVos;
    protected String projectCode;
    protected BigDecimal projectCodePremium;
    protected String togetherDealFlag;
    protected String email;

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
     * 取得 insurantNumber 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsurantNumber() {
        return insurantNumber;
    }

    /**
     * 設定 insurantNumber 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsurantNumber(String value) {
        this.insurantNumber = value;
    }

    /**
     * 取得 brigadeAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBrigadeAmount() {
        return brigadeAmount;
    }

    /**
     * 設定 brigadeAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBrigadeAmount(BigDecimal value) {
        this.brigadeAmount = value;
    }

    /**
     * 取得 brigadePremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBrigadePremium() {
        return brigadePremium;
    }

    /**
     * 設定 brigadePremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBrigadePremium(BigDecimal value) {
        this.brigadePremium = value;
    }

    /**
     * 取得 deathDisabilityAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDeathDisabilityAmount() {
        return deathDisabilityAmount;
    }

    /**
     * 設定 deathDisabilityAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDeathDisabilityAmount(BigDecimal value) {
        this.deathDisabilityAmount = value;
    }

    /**
     * 取得 deathDisabilityPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDeathDisabilityPremium() {
        return deathDisabilityPremium;
    }

    /**
     * 設定 deathDisabilityPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDeathDisabilityPremium(BigDecimal value) {
        this.deathDisabilityPremium = value;
    }

    /**
     * 取得 disabilityAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisabilityAmount() {
        return disabilityAmount;
    }

    /**
     * 設定 disabilityAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisabilityAmount(BigDecimal value) {
        this.disabilityAmount = value;
    }

    /**
     * 取得 disabilityPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisabilityPremium() {
        return disabilityPremium;
    }

    /**
     * 設定 disabilityPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisabilityPremium(BigDecimal value) {
        this.disabilityPremium = value;
    }

    /**
     * 取得 actualPaidAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getActualPaidAmount() {
        return actualPaidAmount;
    }

    /**
     * 設定 actualPaidAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setActualPaidAmount(BigDecimal value) {
        this.actualPaidAmount = value;
    }

    /**
     * 取得 actualPaidPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getActualPaidPremium() {
        return actualPaidPremium;
    }

    /**
     * 設定 actualPaidPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setActualPaidPremium(BigDecimal value) {
        this.actualPaidPremium = value;
    }

    /**
     * 取得 overseasAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOverseasAmount() {
        return overseasAmount;
    }

    /**
     * 設定 overseasAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOverseasAmount(BigDecimal value) {
        this.overseasAmount = value;
    }

    /**
     * 取得 overseasPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOverseasPremium() {
        return overseasPremium;
    }

    /**
     * 設定 overseasPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOverseasPremium(BigDecimal value) {
        this.overseasPremium = value;
    }

    /**
     * 取得 newOverseasAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNewOverseasAmount() {
        return newOverseasAmount;
    }

    /**
     * 設定 newOverseasAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNewOverseasAmount(BigDecimal value) {
        this.newOverseasAmount = value;
    }

    /**
     * 取得 newOverseasPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNewOverseasPremium() {
        return newOverseasPremium;
    }

    /**
     * 設定 newOverseasPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNewOverseasPremium(BigDecimal value) {
        this.newOverseasPremium = value;
    }

    /**
     * 取得 travelInc 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTravelInc() {
        return travelInc;
    }

    /**
     * 設定 travelInc 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTravelInc(String value) {
        this.travelInc = value;
    }

    /**
     * 取得 foodpoisoningAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFoodpoisoningAmount() {
        return foodpoisoningAmount;
    }

    /**
     * 設定 foodpoisoningAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFoodpoisoningAmount(BigDecimal value) {
        this.foodpoisoningAmount = value;
    }

    /**
     * 取得 foodpoisoningPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFoodpoisoningPremium() {
        return foodpoisoningPremium;
    }

    /**
     * 設定 foodpoisoningPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFoodpoisoningPremium(BigDecimal value) {
        this.foodpoisoningPremium = value;
    }

    /**
     * 取得 majorburnsAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMajorburnsAmount() {
        return majorburnsAmount;
    }

    /**
     * 設定 majorburnsAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMajorburnsAmount(BigDecimal value) {
        this.majorburnsAmount = value;
    }

    /**
     * 取得 majorburnsPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMajorburnsPremium() {
        return majorburnsPremium;
    }

    /**
     * 設定 majorburnsPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMajorburnsPremium(BigDecimal value) {
        this.majorburnsPremium = value;
    }

    /**
     * 取得 publictransportAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPublictransportAmount() {
        return publictransportAmount;
    }

    /**
     * 設定 publictransportAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPublictransportAmount(BigDecimal value) {
        this.publictransportAmount = value;
    }

    /**
     * 取得 publictransportPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPublictransportPremium() {
        return publictransportPremium;
    }

    /**
     * 設定 publictransportPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPublictransportPremium(BigDecimal value) {
        this.publictransportPremium = value;
    }

    /**
     * 取得 travelPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTravelPremium() {
        return travelPremium;
    }

    /**
     * 設定 travelPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTravelPremium(BigDecimal value) {
        this.travelPremium = value;
    }

    /**
     * 取得 injuryFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInjuryFlag() {
        return injuryFlag;
    }

    /**
     * 設定 injuryFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInjuryFlag(String value) {
        this.injuryFlag = value;
    }

    /**
     * 取得 tourismInsFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTourismInsFlag() {
        return tourismInsFlag;
    }

    /**
     * 設定 tourismInsFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTourismInsFlag(String value) {
        this.tourismInsFlag = value;
    }

    /**
     * Gets the value of the benefitInfoVos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the benefitInfoVos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBenefitInfoVos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BenefitInfoVo }
     * 
     * 
     */
    public List<BenefitInfoVo> getBenefitInfoVos() {
        if (benefitInfoVos == null) {
            benefitInfoVos = new ArrayList<BenefitInfoVo>();
        }
        return this.benefitInfoVos;
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
     * 取得 rrpreInsured 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRrpreInsured() {
        return rrpreInsured;
    }

    /**
     * 設定 rrpreInsured 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRrpreInsured(String value) {
        this.rrpreInsured = value;
    }

    /**
     * 取得 rrpreIDNumber 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRrpreIDNumber() {
        return rrpreIDNumber;
    }

    /**
     * 設定 rrpreIDNumber 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRrpreIDNumber(String value) {
        this.rrpreIDNumber = value;
    }

    /**
     * 取得 rrpreIDType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRrpreIDType() {
        return rrpreIDType;
    }

    /**
     * 設定 rrpreIDType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRrpreIDType(String value) {
        this.rrpreIDType = value;
    }

    /**
     * 取得 rrpreIdentify 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRrpreIdentify() {
        return rrpreIdentify;
    }

    /**
     * 設定 rrpreIdentify 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRrpreIdentify(String value) {
        this.rrpreIdentify = value;
    }

    /**
     * 取得 rrpreCountryEName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRrpreCountryEName() {
        return rrpreCountryEName;
    }

    /**
     * 設定 rrpreCountryEName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRrpreCountryEName(String value) {
        this.rrpreCountryEName = value;
    }

    /**
     * Gets the value of the petInformatVos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the petInformatVos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPetInformatVos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PetInformatVo }
     * 
     * 
     */
    public List<PetInformatVo> getPetInformatVos() {
        if (petInformatVos == null) {
            petInformatVos = new ArrayList<PetInformatVo>();
        }
        return this.petInformatVos;
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

}
