
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>itemkindInfoVos complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="itemkindInfoVos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kindcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="kindAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="kindPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemkindInfoVos", propOrder = {
    "kindcode",
    "kindAmount",
    "kindPremium"
})
public class ItemkindInfoVos {

    protected String kindcode;
    protected BigDecimal kindAmount;
    protected BigDecimal kindPremium;

    /**
     * 取得 kindcode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKindcode() {
        return kindcode;
    }

    /**
     * 設定 kindcode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKindcode(String value) {
        this.kindcode = value;
    }

    /**
     * 取得 kindAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKindAmount() {
        return kindAmount;
    }

    /**
     * 設定 kindAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKindAmount(BigDecimal value) {
        this.kindAmount = value;
    }

    /**
     * 取得 kindPremium 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKindPremium() {
        return kindPremium;
    }

    /**
     * 設定 kindPremium 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKindPremium(BigDecimal value) {
        this.kindPremium = value;
    }

}
