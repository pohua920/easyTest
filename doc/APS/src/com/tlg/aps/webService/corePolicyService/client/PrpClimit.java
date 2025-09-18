
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>prpClimit complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="prpClimit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://sinosoft.com.cn}prpClimitId" minOccurs="0"/>
 *         &lt;element name="prpCmain" type="{http://sinosoft.com.cn}prpCmain" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="limitFee" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="calculateFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="limitFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="limitFeeRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="deductible" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="deductibleRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="extend1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extend2" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="extend3" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="extend4" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="extend5" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="extend6" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="isRecorded" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prpClimit", propOrder = {
    "id",
    "prpCmain",
    "riskCode",
    "limitFee",
    "calculateFlag",
    "limitFlag",
    "flag",
    "remark",
    "limitFeeRate",
    "deductible",
    "deductibleRate",
    "extend1",
    "extend2",
    "extend3",
    "extend4",
    "extend5",
    "extend6",
    "isRecorded"
})
public class PrpClimit {

    protected PrpClimitId id;
    protected PrpCmain prpCmain;
    protected String riskCode;
    protected BigDecimal limitFee;
    protected String calculateFlag;
    protected String limitFlag;
    protected String flag;
    protected String remark;
    protected BigDecimal limitFeeRate;
    protected BigDecimal deductible;
    protected BigDecimal deductibleRate;
    protected String extend1;
    protected BigDecimal extend2;
    protected BigDecimal extend3;
    protected BigDecimal extend4;
    protected BigDecimal extend5;
    protected BigDecimal extend6;
    protected BigDecimal isRecorded;

    /**
     * 取得 id 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpClimitId }
     *     
     */
    public PrpClimitId getId() {
        return id;
    }

    /**
     * 設定 id 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpClimitId }
     *     
     */
    public void setId(PrpClimitId value) {
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
     * 取得 limitFee 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLimitFee() {
        return limitFee;
    }

    /**
     * 設定 limitFee 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLimitFee(BigDecimal value) {
        this.limitFee = value;
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
     * 取得 limitFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitFlag() {
        return limitFlag;
    }

    /**
     * 設定 limitFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitFlag(String value) {
        this.limitFlag = value;
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
     * 取得 limitFeeRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLimitFeeRate() {
        return limitFeeRate;
    }

    /**
     * 設定 limitFeeRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLimitFeeRate(BigDecimal value) {
        this.limitFeeRate = value;
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
     * 取得 extend1 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtend1() {
        return extend1;
    }

    /**
     * 設定 extend1 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtend1(String value) {
        this.extend1 = value;
    }

    /**
     * 取得 extend2 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExtend2() {
        return extend2;
    }

    /**
     * 設定 extend2 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExtend2(BigDecimal value) {
        this.extend2 = value;
    }

    /**
     * 取得 extend3 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExtend3() {
        return extend3;
    }

    /**
     * 設定 extend3 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExtend3(BigDecimal value) {
        this.extend3 = value;
    }

    /**
     * 取得 extend4 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExtend4() {
        return extend4;
    }

    /**
     * 設定 extend4 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExtend4(BigDecimal value) {
        this.extend4 = value;
    }

    /**
     * 取得 extend5 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExtend5() {
        return extend5;
    }

    /**
     * 設定 extend5 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExtend5(BigDecimal value) {
        this.extend5 = value;
    }

    /**
     * 取得 extend6 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExtend6() {
        return extend6;
    }

    /**
     * 設定 extend6 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExtend6(BigDecimal value) {
        this.extend6 = value;
    }

    /**
     * 取得 isRecorded 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIsRecorded() {
        return isRecorded;
    }

    /**
     * 設定 isRecorded 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIsRecorded(BigDecimal value) {
        this.isRecorded = value;
    }

}
