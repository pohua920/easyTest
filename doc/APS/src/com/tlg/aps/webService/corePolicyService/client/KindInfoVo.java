
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>kindInfoVo complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="kindInfoVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kindCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="deductible" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="modeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDamagedB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="discountCharge" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
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
@XmlType(name = "kindInfoVo", propOrder = {
    "kindCode",
    "amount",
    "deductible",
    "model",
    "quantity",
    "modeCode",
    "lastDamagedB",
    "discountCharge",
    "enfVrulecnt",
    "enfVaddprice"
})
public class KindInfoVo {

    @XmlElement(required = true)
    protected String kindCode;
    protected BigDecimal amount;
    protected BigDecimal deductible;
    protected String model;
    protected BigDecimal quantity;
    protected String modeCode;
    protected String lastDamagedB;
    protected BigDecimal discountCharge;
    protected BigDecimal enfVrulecnt;
    protected BigDecimal enfVaddprice;

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
