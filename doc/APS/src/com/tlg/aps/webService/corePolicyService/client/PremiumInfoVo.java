
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>premiumInfoVo complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="premiumInfoVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kindCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="kindName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="premium" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="rate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="basePremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="benchMarkPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="purePremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="lastPurePremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="pureRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="rateValidDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="addCostRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="reliefFund" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="reliefFundRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="stabilityFund" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="stabilityFundRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="manageCharge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="specialCharge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="discountCharge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="costRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="costFee" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="fycproportion" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="fycfee" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="recruitfee" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="recruitrate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "premiumInfoVo", propOrder = {
    "kindCode",
    "kindName",
    "premium",
    "rate",
    "basePremium",
    "benchMarkPremium",
    "purePremium",
    "lastPurePremium",
    "pureRate",
    "rateValidDate",
    "addCostRate",
    "reliefFund",
    "reliefFundRate",
    "stabilityFund",
    "stabilityFundRate",
    "manageCharge",
    "specialCharge",
    "discountCharge",
    "costRate",
    "costFee",
    "fycproportion",
    "fycfee",
    "recruitfee",
    "recruitrate"
})
public class PremiumInfoVo {

    @XmlElement(required = true)
    protected String kindCode;
    @XmlElement(required = true)
    protected String kindName;
    @XmlElement(required = true)
    protected BigDecimal premium;
    protected BigDecimal rate;
    protected BigDecimal basePremium;
    protected BigDecimal benchMarkPremium;
    protected BigDecimal purePremium;
    protected BigDecimal lastPurePremium;
    protected BigDecimal pureRate;
    protected String rateValidDate;
    protected BigDecimal addCostRate;
    protected BigDecimal reliefFund;
    protected BigDecimal reliefFundRate;
    protected BigDecimal stabilityFund;
    protected BigDecimal stabilityFundRate;
    protected BigDecimal manageCharge;
    protected BigDecimal specialCharge;
    protected BigDecimal discountCharge;
    protected BigDecimal costRate;
    @XmlElement(required = true)
    protected BigDecimal costFee;
    protected BigDecimal fycproportion;
    protected BigDecimal fycfee;
    protected BigDecimal recruitfee;
    protected BigDecimal recruitrate;

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
     * 取得 costRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCostRate() {
        return costRate;
    }

    /**
     * 設定 costRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCostRate(BigDecimal value) {
        this.costRate = value;
    }

    /**
     * 取得 costFee 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCostFee() {
        return costFee;
    }

    /**
     * 設定 costFee 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCostFee(BigDecimal value) {
        this.costFee = value;
    }

    /**
     * 取得 fycproportion 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFycproportion() {
        return fycproportion;
    }

    /**
     * 設定 fycproportion 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFycproportion(BigDecimal value) {
        this.fycproportion = value;
    }

    /**
     * 取得 fycfee 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFycfee() {
        return fycfee;
    }

    /**
     * 設定 fycfee 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFycfee(BigDecimal value) {
        this.fycfee = value;
    }

    /**
     * 取得 recruitfee 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRecruitfee() {
        return recruitfee;
    }

    /**
     * 設定 recruitfee 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRecruitfee(BigDecimal value) {
        this.recruitfee = value;
    }

    /**
     * 取得 recruitrate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRecruitrate() {
        return recruitrate;
    }

    /**
     * 設定 recruitrate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRecruitrate(BigDecimal value) {
        this.recruitrate = value;
    }

}
