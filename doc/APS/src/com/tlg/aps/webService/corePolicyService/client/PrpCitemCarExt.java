
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>prpCitemCarExt complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="prpCitemCarExt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://sinosoft.com.cn}prpCitemCarExtId" minOccurs="0"/>
 *         &lt;element name="prpCmain" type="{http://sinosoft.com.cn}prpCmain" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDamagedA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDamagedB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thisDamagedA" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="thisDamagedB" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="carGoodsType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noClaimFavorType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastDamagedTimes" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="thisDamagedTimes" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="damagedFactorGrade" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="floatingType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noDamageYears" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cartypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gtFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialCarFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="groupCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jhtimes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zjtimes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gtFloatRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="untreatedJHtimes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="untreatedZJtimes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inDoorFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="floatRateA" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="floatRateG" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="myrRateA" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="myrRateB" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="ageSexRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="carAgeRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="myrRateACode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="myrRateBCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prpCitemCarExt", propOrder = {
    "id",
    "prpCmain",
    "riskCode",
    "lastDamagedA",
    "lastDamagedB",
    "thisDamagedA",
    "thisDamagedB",
    "carGoodsType",
    "noClaimFavorType",
    "flag",
    "lastDamagedTimes",
    "thisDamagedTimes",
    "damagedFactorGrade",
    "floatingType",
    "noDamageYears",
    "cartypeCode",
    "gtFlag",
    "specialCarFlag",
    "groupCode",
    "jhtimes",
    "zjtimes",
    "startPoint",
    "endPoint",
    "gtFloatRate",
    "untreatedJHtimes",
    "untreatedZJtimes",
    "inDoorFlag",
    "floatRateA",
    "floatRateG",
    "myrRateA",
    "myrRateB",
    "ageSexRate",
    "carAgeRate",
    "myrRateACode",
    "myrRateBCode"
})
public class PrpCitemCarExt {

    protected PrpCitemCarExtId id;
    protected PrpCmain prpCmain;
    protected String riskCode;
    protected String lastDamagedA;
    protected String lastDamagedB;
    protected Long thisDamagedA;
    protected Long thisDamagedB;
    protected String carGoodsType;
    protected String noClaimFavorType;
    protected String flag;
    protected Long lastDamagedTimes;
    protected Long thisDamagedTimes;
    protected Long damagedFactorGrade;
    protected String floatingType;
    protected String noDamageYears;
    protected String cartypeCode;
    protected String gtFlag;
    protected String specialCarFlag;
    protected String groupCode;
    protected String jhtimes;
    protected String zjtimes;
    protected String startPoint;
    protected String endPoint;
    protected BigDecimal gtFloatRate;
    protected String untreatedJHtimes;
    protected String untreatedZJtimes;
    protected String inDoorFlag;
    protected BigDecimal floatRateA;
    protected BigDecimal floatRateG;
    protected BigDecimal myrRateA;
    protected BigDecimal myrRateB;
    protected BigDecimal ageSexRate;
    protected BigDecimal carAgeRate;
    protected String myrRateACode;
    protected String myrRateBCode;

    /**
     * 取得 id 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCitemCarExtId }
     *     
     */
    public PrpCitemCarExtId getId() {
        return id;
    }

    /**
     * 設定 id 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCitemCarExtId }
     *     
     */
    public void setId(PrpCitemCarExtId value) {
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
     * 取得 thisDamagedA 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getThisDamagedA() {
        return thisDamagedA;
    }

    /**
     * 設定 thisDamagedA 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setThisDamagedA(Long value) {
        this.thisDamagedA = value;
    }

    /**
     * 取得 thisDamagedB 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getThisDamagedB() {
        return thisDamagedB;
    }

    /**
     * 設定 thisDamagedB 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setThisDamagedB(Long value) {
        this.thisDamagedB = value;
    }

    /**
     * 取得 carGoodsType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarGoodsType() {
        return carGoodsType;
    }

    /**
     * 設定 carGoodsType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarGoodsType(String value) {
        this.carGoodsType = value;
    }

    /**
     * 取得 noClaimFavorType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoClaimFavorType() {
        return noClaimFavorType;
    }

    /**
     * 設定 noClaimFavorType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoClaimFavorType(String value) {
        this.noClaimFavorType = value;
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
     * 取得 lastDamagedTimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLastDamagedTimes() {
        return lastDamagedTimes;
    }

    /**
     * 設定 lastDamagedTimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLastDamagedTimes(Long value) {
        this.lastDamagedTimes = value;
    }

    /**
     * 取得 thisDamagedTimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getThisDamagedTimes() {
        return thisDamagedTimes;
    }

    /**
     * 設定 thisDamagedTimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setThisDamagedTimes(Long value) {
        this.thisDamagedTimes = value;
    }

    /**
     * 取得 damagedFactorGrade 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDamagedFactorGrade() {
        return damagedFactorGrade;
    }

    /**
     * 設定 damagedFactorGrade 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDamagedFactorGrade(Long value) {
        this.damagedFactorGrade = value;
    }

    /**
     * 取得 floatingType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloatingType() {
        return floatingType;
    }

    /**
     * 設定 floatingType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloatingType(String value) {
        this.floatingType = value;
    }

    /**
     * 取得 noDamageYears 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoDamageYears() {
        return noDamageYears;
    }

    /**
     * 設定 noDamageYears 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoDamageYears(String value) {
        this.noDamageYears = value;
    }

    /**
     * 取得 cartypeCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCartypeCode() {
        return cartypeCode;
    }

    /**
     * 設定 cartypeCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCartypeCode(String value) {
        this.cartypeCode = value;
    }

    /**
     * 取得 gtFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGtFlag() {
        return gtFlag;
    }

    /**
     * 設定 gtFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGtFlag(String value) {
        this.gtFlag = value;
    }

    /**
     * 取得 specialCarFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialCarFlag() {
        return specialCarFlag;
    }

    /**
     * 設定 specialCarFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialCarFlag(String value) {
        this.specialCarFlag = value;
    }

    /**
     * 取得 groupCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * 設定 groupCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupCode(String value) {
        this.groupCode = value;
    }

    /**
     * 取得 jhtimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJhtimes() {
        return jhtimes;
    }

    /**
     * 設定 jhtimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJhtimes(String value) {
        this.jhtimes = value;
    }

    /**
     * 取得 zjtimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZjtimes() {
        return zjtimes;
    }

    /**
     * 設定 zjtimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZjtimes(String value) {
        this.zjtimes = value;
    }

    /**
     * 取得 startPoint 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartPoint() {
        return startPoint;
    }

    /**
     * 設定 startPoint 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartPoint(String value) {
        this.startPoint = value;
    }

    /**
     * 取得 endPoint 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * 設定 endPoint 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndPoint(String value) {
        this.endPoint = value;
    }

    /**
     * 取得 gtFloatRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGtFloatRate() {
        return gtFloatRate;
    }

    /**
     * 設定 gtFloatRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGtFloatRate(BigDecimal value) {
        this.gtFloatRate = value;
    }

    /**
     * 取得 untreatedJHtimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUntreatedJHtimes() {
        return untreatedJHtimes;
    }

    /**
     * 設定 untreatedJHtimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUntreatedJHtimes(String value) {
        this.untreatedJHtimes = value;
    }

    /**
     * 取得 untreatedZJtimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUntreatedZJtimes() {
        return untreatedZJtimes;
    }

    /**
     * 設定 untreatedZJtimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUntreatedZJtimes(String value) {
        this.untreatedZJtimes = value;
    }

    /**
     * 取得 inDoorFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInDoorFlag() {
        return inDoorFlag;
    }

    /**
     * 設定 inDoorFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInDoorFlag(String value) {
        this.inDoorFlag = value;
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
     * 取得 carAgeRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCarAgeRate() {
        return carAgeRate;
    }

    /**
     * 設定 carAgeRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCarAgeRate(BigDecimal value) {
        this.carAgeRate = value;
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

}
