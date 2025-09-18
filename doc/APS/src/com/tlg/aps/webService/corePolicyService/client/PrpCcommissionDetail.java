
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>prpCcommissionDetail complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="prpCcommissionDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://sinosoft.com.cn}prpCcommissionDetailId" minOccurs="0"/>
 *         &lt;element name="prpCmain" type="{http://sinosoft.com.cn}prpCmain" minOccurs="0"/>
 *         &lt;element name="agreementNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerGroupCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="costType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clauseCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kindCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sumPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="costRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="costRateUpper" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="adjustFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="upperFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auditRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="auditFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coinsRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="coinsDeduct" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="costFee" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="costFeeOrg" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="configCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amortizeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clauseKindFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insertTimeForHis" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="operateTimeForHis" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="chgCostFee" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prpCcommissionDetail", propOrder = {
    "id",
    "prpCmain",
    "agreementNo",
    "customerGroupCode",
    "costType",
    "payNo",
    "riskCode",
    "clauseCode",
    "kindCode",
    "sumPremium",
    "costRate",
    "costRateUpper",
    "adjustFlag",
    "upperFlag",
    "auditRate",
    "auditFlag",
    "coinsRate",
    "coinsDeduct",
    "currency",
    "costFee",
    "costFeeOrg",
    "configCode",
    "amortizeFlag",
    "clauseKindFlag",
    "remark",
    "flag",
    "insertTimeForHis",
    "operateTimeForHis",
    "chgCostFee"
})
public class PrpCcommissionDetail {

    protected PrpCcommissionDetailId id;
    protected PrpCmain prpCmain;
    protected String agreementNo;
    protected String customerGroupCode;
    protected String costType;
    protected Integer payNo;
    protected String riskCode;
    protected String clauseCode;
    protected String kindCode;
    protected BigDecimal sumPremium;
    protected BigDecimal costRate;
    protected BigDecimal costRateUpper;
    protected String adjustFlag;
    protected String upperFlag;
    protected BigDecimal auditRate;
    protected String auditFlag;
    protected BigDecimal coinsRate;
    protected String coinsDeduct;
    protected String currency;
    protected BigDecimal costFee;
    protected BigDecimal costFeeOrg;
    protected String configCode;
    protected String amortizeFlag;
    protected String clauseKindFlag;
    protected String remark;
    protected String flag;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar insertTimeForHis;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar operateTimeForHis;
    protected BigDecimal chgCostFee;

    /**
     * 取得 id 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCcommissionDetailId }
     *     
     */
    public PrpCcommissionDetailId getId() {
        return id;
    }

    /**
     * 設定 id 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCcommissionDetailId }
     *     
     */
    public void setId(PrpCcommissionDetailId value) {
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
     * 取得 agreementNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementNo() {
        return agreementNo;
    }

    /**
     * 設定 agreementNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementNo(String value) {
        this.agreementNo = value;
    }

    /**
     * 取得 customerGroupCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerGroupCode() {
        return customerGroupCode;
    }

    /**
     * 設定 customerGroupCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerGroupCode(String value) {
        this.customerGroupCode = value;
    }

    /**
     * 取得 costType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCostType() {
        return costType;
    }

    /**
     * 設定 costType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCostType(String value) {
        this.costType = value;
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
     * 取得 sumPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumPremium() {
        return sumPremium;
    }

    /**
     * 設定 sumPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumPremium(BigDecimal value) {
        this.sumPremium = value;
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
     * 取得 costRateUpper 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCostRateUpper() {
        return costRateUpper;
    }

    /**
     * 設定 costRateUpper 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCostRateUpper(BigDecimal value) {
        this.costRateUpper = value;
    }

    /**
     * 取得 adjustFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdjustFlag() {
        return adjustFlag;
    }

    /**
     * 設定 adjustFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdjustFlag(String value) {
        this.adjustFlag = value;
    }

    /**
     * 取得 upperFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpperFlag() {
        return upperFlag;
    }

    /**
     * 設定 upperFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpperFlag(String value) {
        this.upperFlag = value;
    }

    /**
     * 取得 auditRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAuditRate() {
        return auditRate;
    }

    /**
     * 設定 auditRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAuditRate(BigDecimal value) {
        this.auditRate = value;
    }

    /**
     * 取得 auditFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditFlag() {
        return auditFlag;
    }

    /**
     * 設定 auditFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditFlag(String value) {
        this.auditFlag = value;
    }

    /**
     * 取得 coinsRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCoinsRate() {
        return coinsRate;
    }

    /**
     * 設定 coinsRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCoinsRate(BigDecimal value) {
        this.coinsRate = value;
    }

    /**
     * 取得 coinsDeduct 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoinsDeduct() {
        return coinsDeduct;
    }

    /**
     * 設定 coinsDeduct 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoinsDeduct(String value) {
        this.coinsDeduct = value;
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
     * 取得 costFeeOrg 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCostFeeOrg() {
        return costFeeOrg;
    }

    /**
     * 設定 costFeeOrg 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCostFeeOrg(BigDecimal value) {
        this.costFeeOrg = value;
    }

    /**
     * 取得 configCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigCode() {
        return configCode;
    }

    /**
     * 設定 configCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigCode(String value) {
        this.configCode = value;
    }

    /**
     * 取得 amortizeFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmortizeFlag() {
        return amortizeFlag;
    }

    /**
     * 設定 amortizeFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmortizeFlag(String value) {
        this.amortizeFlag = value;
    }

    /**
     * 取得 clauseKindFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClauseKindFlag() {
        return clauseKindFlag;
    }

    /**
     * 設定 clauseKindFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClauseKindFlag(String value) {
        this.clauseKindFlag = value;
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
     * 取得 insertTimeForHis 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInsertTimeForHis() {
        return insertTimeForHis;
    }

    /**
     * 設定 insertTimeForHis 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInsertTimeForHis(XMLGregorianCalendar value) {
        this.insertTimeForHis = value;
    }

    /**
     * 取得 operateTimeForHis 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOperateTimeForHis() {
        return operateTimeForHis;
    }

    /**
     * 設定 operateTimeForHis 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOperateTimeForHis(XMLGregorianCalendar value) {
        this.operateTimeForHis = value;
    }

    /**
     * 取得 chgCostFee 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getChgCostFee() {
        return chgCostFee;
    }

    /**
     * 設定 chgCostFee 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setChgCostFee(BigDecimal value) {
        this.chgCostFee = value;
    }

}
