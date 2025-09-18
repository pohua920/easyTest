
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>prpCitemKind complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="prpCitemKind">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://sinosoft.com.cn}prpCitemKindId" minOccurs="0"/>
 *         &lt;element name="prpCmain" type="{http://sinosoft.com.cn}prpCmain" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="familyNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="familyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kindCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kindName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="itemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemDetailName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="startHour" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="endHour" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buyDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="addressNoNew" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="calculateFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="ratePeriod" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="rate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="shortRateFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shortRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="basePremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="benchMarkPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="discount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="adjustRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="premium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="deductibleRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="deductible" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="profitScale" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="currency2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exchangeRate2" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="premium2" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="exchangeRateCNY" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="premiumCny" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="newStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="newEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="insuredValueType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredValueTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="storageRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lowerrate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="structureno" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="clauseCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="replyNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reliefFund" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="reliefFundRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="stabilityFund" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="stabilityFundRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="manageCharge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="specialCharge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="purePremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="lastPurePremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="discountCharge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="deductibleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rateValidDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="motorRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="pureRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="addCostRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="enfVrulecnt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="enfVaddprice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prpCitemKind", propOrder = {
    "id",
    "prpCmain",
    "riskCode",
    "familyNo",
    "familyName",
    "rationType",
    "kindCode",
    "kindName",
    "itemNo",
    "itemCode",
    "itemDetailName",
    "modeCode",
    "modeName",
    "startDate",
    "startHour",
    "endDate",
    "endHour",
    "model",
    "buyDate",
    "addressNoNew",
    "calculateFlag",
    "currency",
    "unitAmount",
    "quantity",
    "unit",
    "value",
    "amount",
    "ratePeriod",
    "rate",
    "shortRateFlag",
    "shortRate",
    "basePremium",
    "benchMarkPremium",
    "discount",
    "adjustRate",
    "premium",
    "deductibleRate",
    "deductible",
    "flag",
    "profitScale",
    "currency2",
    "exchangeRate2",
    "premium2",
    "exchangeRateCNY",
    "premiumCny",
    "newStartDate",
    "newEndDate",
    "insuredValueType",
    "insuredValueTypeName",
    "storageRate",
    "lowerrate",
    "structureno",
    "clauseCode",
    "replyNo",
    "reliefFund",
    "reliefFundRate",
    "stabilityFund",
    "stabilityFundRate",
    "manageCharge",
    "specialCharge",
    "purePremium",
    "lastPurePremium",
    "discountCharge",
    "deductibleType",
    "rateValidDate",
    "rateType",
    "productCode",
    "motorRate",
    "pureRate",
    "addCostRate",
    "enfVrulecnt",
    "enfVaddprice"
})
public class PrpCitemKind {

    protected PrpCitemKindId id;
    protected PrpCmain prpCmain;
    protected String riskCode;
    protected Integer familyNo;
    protected String familyName;
    protected String rationType;
    protected String kindCode;
    protected String kindName;
    protected Integer itemNo;
    protected String itemCode;
    protected String itemDetailName;
    protected String modeCode;
    protected String modeName;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    protected Integer startHour;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    protected Integer endHour;
    protected String model;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar buyDate;
    protected Long addressNoNew;
    protected String calculateFlag;
    protected String currency;
    protected BigDecimal unitAmount;
    protected BigDecimal quantity;
    protected String unit;
    protected BigDecimal value;
    protected BigDecimal amount;
    protected Long ratePeriod;
    protected BigDecimal rate;
    protected String shortRateFlag;
    protected BigDecimal shortRate;
    protected BigDecimal basePremium;
    protected BigDecimal benchMarkPremium;
    protected BigDecimal discount;
    protected BigDecimal adjustRate;
    protected BigDecimal premium;
    protected BigDecimal deductibleRate;
    protected BigDecimal deductible;
    protected String flag;
    protected BigDecimal profitScale;
    protected String currency2;
    protected BigDecimal exchangeRate2;
    protected BigDecimal premium2;
    protected BigDecimal exchangeRateCNY;
    protected BigDecimal premiumCny;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar newStartDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar newEndDate;
    protected String insuredValueType;
    protected String insuredValueTypeName;
    protected String storageRate;
    protected String lowerrate;
    protected Long structureno;
    protected String clauseCode;
    protected String replyNo;
    protected BigDecimal reliefFund;
    protected BigDecimal reliefFundRate;
    protected BigDecimal stabilityFund;
    protected BigDecimal stabilityFundRate;
    protected BigDecimal manageCharge;
    protected BigDecimal specialCharge;
    protected BigDecimal purePremium;
    protected BigDecimal lastPurePremium;
    protected BigDecimal discountCharge;
    protected String deductibleType;
    protected String rateValidDate;
    protected String rateType;
    protected String productCode;
    protected BigDecimal motorRate;
    protected BigDecimal pureRate;
    protected BigDecimal addCostRate;
    protected BigDecimal enfVrulecnt;
    protected BigDecimal enfVaddprice;

    /**
     * 取得 id 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCitemKindId }
     *     
     */
    public PrpCitemKindId getId() {
        return id;
    }

    /**
     * 設定 id 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCitemKindId }
     *     
     */
    public void setId(PrpCitemKindId value) {
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
     * 取得 familyNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFamilyNo() {
        return familyNo;
    }

    /**
     * 設定 familyNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFamilyNo(Integer value) {
        this.familyNo = value;
    }

    /**
     * 取得 familyName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * 設定 familyName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyName(String value) {
        this.familyName = value;
    }

    /**
     * 取得 rationType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRationType() {
        return rationType;
    }

    /**
     * 設定 rationType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRationType(String value) {
        this.rationType = value;
    }

    /**
     * 取得 kindCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKindCode() {
        return kindCode;
    }

    /**
     * 設定 kindCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKindCode(String value) {
        this.kindCode = value;
    }

    /**
     * 取得 kindName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKindName() {
        return kindName;
    }

    /**
     * 設定 kindName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKindName(String value) {
        this.kindName = value;
    }

    /**
     * 取得 itemNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getItemNo() {
        return itemNo;
    }

    /**
     * 設定 itemNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setItemNo(Integer value) {
        this.itemNo = value;
    }

    /**
     * 取得 itemCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 設定 itemCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemCode(String value) {
        this.itemCode = value;
    }

    /**
     * 取得 itemDetailName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemDetailName() {
        return itemDetailName;
    }

    /**
     * 設定 itemDetailName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemDetailName(String value) {
        this.itemDetailName = value;
    }

    /**
     * 取得 modeCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeCode() {
        return modeCode;
    }

    /**
     * 設定 modeCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeCode(String value) {
        this.modeCode = value;
    }

    /**
     * 取得 modeName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModeName() {
        return modeName;
    }

    /**
     * 設定 modeName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModeName(String value) {
        this.modeName = value;
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
     * 取得 startHour 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStartHour() {
        return startHour;
    }

    /**
     * 設定 startHour 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStartHour(Integer value) {
        this.startHour = value;
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
     * 取得 endHour 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEndHour() {
        return endHour;
    }

    /**
     * 設定 endHour 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEndHour(Integer value) {
        this.endHour = value;
    }

    /**
     * 取得 model 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * 設定 model 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * 取得 buyDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBuyDate() {
        return buyDate;
    }

    /**
     * 設定 buyDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBuyDate(XMLGregorianCalendar value) {
        this.buyDate = value;
    }

    /**
     * 取得 addressNoNew 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAddressNoNew() {
        return addressNoNew;
    }

    /**
     * 設定 addressNoNew 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAddressNoNew(Long value) {
        this.addressNoNew = value;
    }

    /**
     * 取得 calculateFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalculateFlag() {
        return calculateFlag;
    }

    /**
     * 設定 calculateFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalculateFlag(String value) {
        this.calculateFlag = value;
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
     * 取得 unitAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    /**
     * 設定 unitAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setUnitAmount(BigDecimal value) {
        this.unitAmount = value;
    }

    /**
     * 取得 quantity 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * 設定 quantity 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantity(BigDecimal value) {
        this.quantity = value;
    }

    /**
     * 取得 unit 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 設定 unit 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * 取得 value 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * 設定 value 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * 取得 amount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 設定 amount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * 取得 ratePeriod 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRatePeriod() {
        return ratePeriod;
    }

    /**
     * 設定 ratePeriod 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRatePeriod(Long value) {
        this.ratePeriod = value;
    }

    /**
     * 取得 rate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 設定 rate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRate(BigDecimal value) {
        this.rate = value;
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
     * 取得 shortRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getShortRate() {
        return shortRate;
    }

    /**
     * 設定 shortRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setShortRate(BigDecimal value) {
        this.shortRate = value;
    }

    /**
     * 取得 basePremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBasePremium() {
        return basePremium;
    }

    /**
     * 設定 basePremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBasePremium(BigDecimal value) {
        this.basePremium = value;
    }

    /**
     * 取得 benchMarkPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBenchMarkPremium() {
        return benchMarkPremium;
    }

    /**
     * 設定 benchMarkPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBenchMarkPremium(BigDecimal value) {
        this.benchMarkPremium = value;
    }

    /**
     * 取得 discount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 設定 discount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDiscount(BigDecimal value) {
        this.discount = value;
    }

    /**
     * 取得 adjustRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAdjustRate() {
        return adjustRate;
    }

    /**
     * 設定 adjustRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAdjustRate(BigDecimal value) {
        this.adjustRate = value;
    }

    /**
     * 取得 premium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPremium() {
        return premium;
    }

    /**
     * 設定 premium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPremium(BigDecimal value) {
        this.premium = value;
    }

    /**
     * 取得 deductibleRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDeductibleRate() {
        return deductibleRate;
    }

    /**
     * 設定 deductibleRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDeductibleRate(BigDecimal value) {
        this.deductibleRate = value;
    }

    /**
     * 取得 deductible 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDeductible() {
        return deductible;
    }

    /**
     * 設定 deductible 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDeductible(BigDecimal value) {
        this.deductible = value;
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
     * 取得 profitScale 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getProfitScale() {
        return profitScale;
    }

    /**
     * 設定 profitScale 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setProfitScale(BigDecimal value) {
        this.profitScale = value;
    }

    /**
     * 取得 currency2 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency2() {
        return currency2;
    }

    /**
     * 設定 currency2 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency2(String value) {
        this.currency2 = value;
    }

    /**
     * 取得 exchangeRate2 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExchangeRate2() {
        return exchangeRate2;
    }

    /**
     * 設定 exchangeRate2 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExchangeRate2(BigDecimal value) {
        this.exchangeRate2 = value;
    }

    /**
     * 取得 premium2 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPremium2() {
        return premium2;
    }

    /**
     * 設定 premium2 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPremium2(BigDecimal value) {
        this.premium2 = value;
    }

    /**
     * 取得 exchangeRateCNY 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExchangeRateCNY() {
        return exchangeRateCNY;
    }

    /**
     * 設定 exchangeRateCNY 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExchangeRateCNY(BigDecimal value) {
        this.exchangeRateCNY = value;
    }

    /**
     * 取得 premiumCny 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPremiumCny() {
        return premiumCny;
    }

    /**
     * 設定 premiumCny 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPremiumCny(BigDecimal value) {
        this.premiumCny = value;
    }

    /**
     * 取得 newStartDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNewStartDate() {
        return newStartDate;
    }

    /**
     * 設定 newStartDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNewStartDate(XMLGregorianCalendar value) {
        this.newStartDate = value;
    }

    /**
     * 取得 newEndDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNewEndDate() {
        return newEndDate;
    }

    /**
     * 設定 newEndDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNewEndDate(XMLGregorianCalendar value) {
        this.newEndDate = value;
    }

    /**
     * 取得 insuredValueType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredValueType() {
        return insuredValueType;
    }

    /**
     * 設定 insuredValueType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredValueType(String value) {
        this.insuredValueType = value;
    }

    /**
     * 取得 insuredValueTypeName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredValueTypeName() {
        return insuredValueTypeName;
    }

    /**
     * 設定 insuredValueTypeName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredValueTypeName(String value) {
        this.insuredValueTypeName = value;
    }

    /**
     * 取得 storageRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageRate() {
        return storageRate;
    }

    /**
     * 設定 storageRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageRate(String value) {
        this.storageRate = value;
    }

    /**
     * 取得 lowerrate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLowerrate() {
        return lowerrate;
    }

    /**
     * 設定 lowerrate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLowerrate(String value) {
        this.lowerrate = value;
    }

    /**
     * 取得 structureno 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStructureno() {
        return structureno;
    }

    /**
     * 設定 structureno 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStructureno(Long value) {
        this.structureno = value;
    }

    /**
     * 取得 clauseCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClauseCode() {
        return clauseCode;
    }

    /**
     * 設定 clauseCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClauseCode(String value) {
        this.clauseCode = value;
    }

    /**
     * 取得 replyNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplyNo() {
        return replyNo;
    }

    /**
     * 設定 replyNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplyNo(String value) {
        this.replyNo = value;
    }

    /**
     * 取得 reliefFund 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReliefFund() {
        return reliefFund;
    }

    /**
     * 設定 reliefFund 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReliefFund(BigDecimal value) {
        this.reliefFund = value;
    }

    /**
     * 取得 reliefFundRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReliefFundRate() {
        return reliefFundRate;
    }

    /**
     * 設定 reliefFundRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReliefFundRate(BigDecimal value) {
        this.reliefFundRate = value;
    }

    /**
     * 取得 stabilityFund 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStabilityFund() {
        return stabilityFund;
    }

    /**
     * 設定 stabilityFund 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStabilityFund(BigDecimal value) {
        this.stabilityFund = value;
    }

    /**
     * 取得 stabilityFundRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStabilityFundRate() {
        return stabilityFundRate;
    }

    /**
     * 設定 stabilityFundRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStabilityFundRate(BigDecimal value) {
        this.stabilityFundRate = value;
    }

    /**
     * 取得 manageCharge 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getManageCharge() {
        return manageCharge;
    }

    /**
     * 設定 manageCharge 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setManageCharge(BigDecimal value) {
        this.manageCharge = value;
    }

    /**
     * 取得 specialCharge 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSpecialCharge() {
        return specialCharge;
    }

    /**
     * 設定 specialCharge 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSpecialCharge(BigDecimal value) {
        this.specialCharge = value;
    }

    /**
     * 取得 purePremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPurePremium() {
        return purePremium;
    }

    /**
     * 設定 purePremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPurePremium(BigDecimal value) {
        this.purePremium = value;
    }

    /**
     * 取得 lastPurePremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLastPurePremium() {
        return lastPurePremium;
    }

    /**
     * 設定 lastPurePremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLastPurePremium(BigDecimal value) {
        this.lastPurePremium = value;
    }

    /**
     * 取得 discountCharge 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDiscountCharge() {
        return discountCharge;
    }

    /**
     * 設定 discountCharge 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDiscountCharge(BigDecimal value) {
        this.discountCharge = value;
    }

    /**
     * 取得 deductibleType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeductibleType() {
        return deductibleType;
    }

    /**
     * 設定 deductibleType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeductibleType(String value) {
        this.deductibleType = value;
    }

    /**
     * 取得 rateValidDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateValidDate() {
        return rateValidDate;
    }

    /**
     * 設定 rateValidDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateValidDate(String value) {
        this.rateValidDate = value;
    }

    /**
     * 取得 rateType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateType() {
        return rateType;
    }

    /**
     * 設定 rateType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateType(String value) {
        this.rateType = value;
    }

    /**
     * 取得 productCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * 設定 productCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCode(String value) {
        this.productCode = value;
    }

    /**
     * 取得 motorRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMotorRate() {
        return motorRate;
    }

    /**
     * 設定 motorRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMotorRate(BigDecimal value) {
        this.motorRate = value;
    }

    /**
     * 取得 pureRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPureRate() {
        return pureRate;
    }

    /**
     * 設定 pureRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPureRate(BigDecimal value) {
        this.pureRate = value;
    }

    /**
     * 取得 addCostRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAddCostRate() {
        return addCostRate;
    }

    /**
     * 設定 addCostRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAddCostRate(BigDecimal value) {
        this.addCostRate = value;
    }

    /**
     * 取得 enfVrulecnt 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEnfVrulecnt() {
        return enfVrulecnt;
    }

    /**
     * 設定 enfVrulecnt 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEnfVrulecnt(BigDecimal value) {
        this.enfVrulecnt = value;
    }

    /**
     * 取得 enfVaddprice 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEnfVaddprice() {
        return enfVaddprice;
    }

    /**
     * 設定 enfVaddprice 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEnfVaddprice(BigDecimal value) {
        this.enfVaddprice = value;
    }

}
