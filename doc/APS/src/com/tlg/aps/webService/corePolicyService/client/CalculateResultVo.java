
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>calculateResultVo complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="calculateResultVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="returnCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="returnMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ageSexRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="myrRateACode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="myrRateA" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="myrRateBCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="myrRateB" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="actualValue" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="purchasePrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="tradeVanID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDamagedA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDamagedB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="floatRateA" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="floatRateG" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tradeVanIDCI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDamagedBCI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="floatRateGCI" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="enfVrulecnt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="enfVaddprice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="fycFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fycFlagCI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oldStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oldEndate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="premiumInfoVos" type="{http://sinosoft.com.cn}premiumInfoVo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calculateResultVo", propOrder = {
    "returnCode",
    "returnMsg",
    "ageSexRate",
    "myrRateACode",
    "myrRateA",
    "myrRateBCode",
    "myrRateB",
    "actualValue",
    "purchasePrice",
    "tradeVanID",
    "lastDamagedA",
    "lastDamagedB",
    "floatRateA",
    "floatRateG",
    "tradeVanIDCI",
    "lastDamagedBCI",
    "floatRateGCI",
    "enfVrulecnt",
    "enfVaddprice",
    "fycFlag",
    "fycFlagCI",
    "oldStartDate",
    "oldEndate",
    "premiumInfoVos"
})
public class CalculateResultVo {

    @XmlElement(required = true)
    protected String returnCode;
    @XmlElement(required = true)
    protected String returnMsg;
    @XmlElement(required = true)
    protected BigDecimal ageSexRate;
    protected String myrRateACode;
    @XmlElement(required = true)
    protected BigDecimal myrRateA;
    protected String myrRateBCode;
    @XmlElement(required = true)
    protected BigDecimal myrRateB;
    @XmlElement(required = true)
    protected BigDecimal actualValue;
    @XmlElement(required = true)
    protected BigDecimal purchasePrice;
    protected String tradeVanID;
    protected String lastDamagedA;
    protected String lastDamagedB;
    protected BigDecimal floatRateA;
    protected BigDecimal floatRateG;
    protected String tradeVanIDCI;
    protected String lastDamagedBCI;
    protected BigDecimal floatRateGCI;
    protected BigDecimal enfVrulecnt;
    protected BigDecimal enfVaddprice;
    protected String fycFlag;
    protected String fycFlagCI;
    protected String oldStartDate;
    protected String oldEndate;
    @XmlElement(nillable = true)
    protected List<PremiumInfoVo> premiumInfoVos;

    /**
     * 取得 returnCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     * 設定 returnCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnCode(String value) {
        this.returnCode = value;
    }

    /**
     * 取得 returnMsg 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnMsg() {
        return returnMsg;
    }

    /**
     * 設定 returnMsg 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnMsg(String value) {
        this.returnMsg = value;
    }

    /**
     * 取得 ageSexRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAgeSexRate() {
        return ageSexRate;
    }

    /**
     * 設定 ageSexRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAgeSexRate(BigDecimal value) {
        this.ageSexRate = value;
    }

    /**
     * 取得 myrRateACode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMyrRateACode() {
        return myrRateACode;
    }

    /**
     * 設定 myrRateACode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMyrRateACode(String value) {
        this.myrRateACode = value;
    }

    /**
     * 取得 myrRateA 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMyrRateA() {
        return myrRateA;
    }

    /**
     * 設定 myrRateA 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMyrRateA(BigDecimal value) {
        this.myrRateA = value;
    }

    /**
     * 取得 myrRateBCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMyrRateBCode() {
        return myrRateBCode;
    }

    /**
     * 設定 myrRateBCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMyrRateBCode(String value) {
        this.myrRateBCode = value;
    }

    /**
     * 取得 myrRateB 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMyrRateB() {
        return myrRateB;
    }

    /**
     * 設定 myrRateB 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMyrRateB(BigDecimal value) {
        this.myrRateB = value;
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
     * 取得 tradeVanID 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeVanID() {
        return tradeVanID;
    }

    /**
     * 設定 tradeVanID 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeVanID(String value) {
        this.tradeVanID = value;
    }

    /**
     * 取得 lastDamagedA 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastDamagedA() {
        return lastDamagedA;
    }

    /**
     * 設定 lastDamagedA 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastDamagedA(String value) {
        this.lastDamagedA = value;
    }

    /**
     * 取得 lastDamagedB 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastDamagedB() {
        return lastDamagedB;
    }

    /**
     * 設定 lastDamagedB 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastDamagedB(String value) {
        this.lastDamagedB = value;
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
     * 取得 tradeVanIDCI 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeVanIDCI() {
        return tradeVanIDCI;
    }

    /**
     * 設定 tradeVanIDCI 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeVanIDCI(String value) {
        this.tradeVanIDCI = value;
    }

    /**
     * 取得 lastDamagedBCI 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastDamagedBCI() {
        return lastDamagedBCI;
    }

    /**
     * 設定 lastDamagedBCI 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastDamagedBCI(String value) {
        this.lastDamagedBCI = value;
    }

    /**
     * 取得 floatRateGCI 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFloatRateGCI() {
        return floatRateGCI;
    }

    /**
     * 設定 floatRateGCI 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFloatRateGCI(BigDecimal value) {
        this.floatRateGCI = value;
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

    /**
     * 取得 fycFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFycFlag() {
        return fycFlag;
    }

    /**
     * 設定 fycFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFycFlag(String value) {
        this.fycFlag = value;
    }

    /**
     * 取得 fycFlagCI 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFycFlagCI() {
        return fycFlagCI;
    }

    /**
     * 設定 fycFlagCI 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFycFlagCI(String value) {
        this.fycFlagCI = value;
    }

    /**
     * 取得 oldStartDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldStartDate() {
        return oldStartDate;
    }

    /**
     * 設定 oldStartDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldStartDate(String value) {
        this.oldStartDate = value;
    }

    /**
     * 取得 oldEndate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldEndate() {
        return oldEndate;
    }

    /**
     * 設定 oldEndate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldEndate(String value) {
        this.oldEndate = value;
    }

    /**
     * Gets the value of the premiumInfoVos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the premiumInfoVos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPremiumInfoVos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PremiumInfoVo }
     * 
     * 
     */
    public List<PremiumInfoVo> getPremiumInfoVos() {
        if (premiumInfoVos == null) {
            premiumInfoVos = new ArrayList<PremiumInfoVo>();
        }
        return this.premiumInfoVos;
    }

}
