
package com.tlg.aps.webService.corePolicyService.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PetInformatVo complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="PetInformatVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serialNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="certifiedDocuments" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="picName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identifynumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="variety" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="species" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="otherinsuredflag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PetInformatVo", propOrder = {
    "serialNo",
    "name",
    "birthday",
    "certifiedDocuments",
    "picName",
    "identifynumber",
    "variety",
    "species",
    "age",
    "sex",
    "weight",
    "unit",
    "otherinsuredflag"
})
public class PetInformatVo {

    @XmlElement(required = true)
    protected String serialNo;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String birthday;
    @XmlElement(required = true)
    protected String certifiedDocuments;
    @XmlElement(required = true)
    protected String picName;
    @XmlElement(required = true)
    protected String identifynumber;
    @XmlElement(required = true)
    protected String variety;
    @XmlElement(required = true)
    protected String species;
    @XmlElement(required = true)
    protected String age;
    @XmlElement(required = true)
    protected String sex;
    @XmlElement(required = true)
    protected String weight;
    @XmlElement(required = true)
    protected String unit;
    @XmlElement(required = true)
    protected String otherinsuredflag;

    /**
     * 取得 serialNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * 設定 serialNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNo(String value) {
        this.serialNo = value;
    }

    /**
     * 取得 name 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 設定 name 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
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
     * 取得 certifiedDocuments 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertifiedDocuments() {
        return certifiedDocuments;
    }

    /**
     * 設定 certifiedDocuments 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertifiedDocuments(String value) {
        this.certifiedDocuments = value;
    }

    /**
     * 取得 picName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicName() {
        return picName;
    }

    /**
     * 設定 picName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicName(String value) {
        this.picName = value;
    }

    /**
     * 取得 identifynumber 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifynumber() {
        return identifynumber;
    }

    /**
     * 設定 identifynumber 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifynumber(String value) {
        this.identifynumber = value;
    }

    /**
     * 取得 variety 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariety() {
        return variety;
    }

    /**
     * 設定 variety 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariety(String value) {
        this.variety = value;
    }

    /**
     * 取得 species 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecies() {
        return species;
    }

    /**
     * 設定 species 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecies(String value) {
        this.species = value;
    }

    /**
     * 取得 age 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAge() {
        return age;
    }

    /**
     * 設定 age 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAge(String value) {
        this.age = value;
    }

    /**
     * 取得 sex 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * 設定 sex 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * 取得 weight 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 設定 weight 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeight(String value) {
        this.weight = value;
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
     * 取得 otherinsuredflag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherinsuredflag() {
        return otherinsuredflag;
    }

    /**
     * 設定 otherinsuredflag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherinsuredflag(String value) {
        this.otherinsuredflag = value;
    }

}
