
package com.tlg.aps.webService.corePolicyService.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>prpCinsuredNature complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="prpCinsuredNature">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://sinosoft.com.cn}prpCinsuredNatureId" minOccurs="0"/>
 *         &lt;element name="prpCinsured" type="{http://sinosoft.com.cn}prpCinsured" minOccurs="0"/>
 *         &lt;element name="insuredFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="health" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jobTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loacalworkyears" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="education" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalWorkYears" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitPhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitPostCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dutyLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dutyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="occupationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="houseProperty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="localPoliceStation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roomAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roomPostCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="selfMonthIncome" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="familyMonthIncome" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="incomeSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="roomPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="familySumQuantity" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="marriage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spouseName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spouseborndate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="spouseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spouseUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spouseJobTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="spouseUnitPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}decimal" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="stature" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prpCinsuredNature", propOrder = {
    "id",
    "prpCinsured",
    "insuredFlag",
    "sex",
    "age",
    "birthday",
    "health",
    "jobTitle",
    "loacalworkyears",
    "education",
    "totalWorkYears",
    "unit",
    "unitPhoneNumber",
    "unitAddress",
    "unitPostCode",
    "unitType",
    "dutyLevel",
    "dutyType",
    "occupationCode",
    "houseProperty",
    "localPoliceStation",
    "roomAddress",
    "roomPostCode",
    "selfMonthIncome",
    "familyMonthIncome",
    "incomeSource",
    "roomPhone",
    "mobile",
    "familySumQuantity",
    "marriage",
    "spouseName",
    "spouseborndate",
    "spouseId",
    "spouseUnit",
    "spouseJobTitle",
    "spouseUnitPhone",
    "flag",
    "weight",
    "stature"
})
public class PrpCinsuredNature {

    protected PrpCinsuredNatureId id;
    protected PrpCinsured prpCinsured;
    protected String insuredFlag;
    protected String sex;
    protected Long age;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthday;
    protected String health;
    protected String jobTitle;
    protected Long loacalworkyears;
    protected String education;
    protected Long totalWorkYears;
    protected String unit;
    protected String unitPhoneNumber;
    protected String unitAddress;
    protected String unitPostCode;
    protected String unitType;
    protected String dutyLevel;
    protected String dutyType;
    protected String occupationCode;
    protected String houseProperty;
    protected String localPoliceStation;
    protected String roomAddress;
    protected String roomPostCode;
    protected BigDecimal selfMonthIncome;
    protected BigDecimal familyMonthIncome;
    protected String incomeSource;
    protected String roomPhone;
    protected String mobile;
    protected Long familySumQuantity;
    protected String marriage;
    protected String spouseName;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar spouseborndate;
    protected String spouseId;
    protected String spouseUnit;
    protected String spouseJobTitle;
    protected String spouseUnitPhone;
    @XmlElement(nillable = true)
    protected List<String> flag;
    @XmlElement(nillable = true)
    protected List<BigDecimal> weight;
    @XmlElement(nillable = true)
    protected List<String> stature;

    /**
     * 取得 id 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCinsuredNatureId }
     *     
     */
    public PrpCinsuredNatureId getId() {
        return id;
    }

    /**
     * 設定 id 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCinsuredNatureId }
     *     
     */
    public void setId(PrpCinsuredNatureId value) {
        this.id = value;
    }

    /**
     * 取得 prpCinsured 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link PrpCinsured }
     *     
     */
    public PrpCinsured getPrpCinsured() {
        return prpCinsured;
    }

    /**
     * 設定 prpCinsured 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link PrpCinsured }
     *     
     */
    public void setPrpCinsured(PrpCinsured value) {
        this.prpCinsured = value;
    }

    /**
     * 取得 insuredFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredFlag() {
        return insuredFlag;
    }

    /**
     * 設定 insuredFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredFlag(String value) {
        this.insuredFlag = value;
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
     * 取得 age 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAge() {
        return age;
    }

    /**
     * 設定 age 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAge(Long value) {
        this.age = value;
    }

    /**
     * 取得 birthday 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthday() {
        return birthday;
    }

    /**
     * 設定 birthday 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthday(XMLGregorianCalendar value) {
        this.birthday = value;
    }

    /**
     * 取得 health 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHealth() {
        return health;
    }

    /**
     * 設定 health 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHealth(String value) {
        this.health = value;
    }

    /**
     * 取得 jobTitle 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * 設定 jobTitle 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobTitle(String value) {
        this.jobTitle = value;
    }

    /**
     * 取得 loacalworkyears 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLoacalworkyears() {
        return loacalworkyears;
    }

    /**
     * 設定 loacalworkyears 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLoacalworkyears(Long value) {
        this.loacalworkyears = value;
    }

    /**
     * 取得 education 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEducation() {
        return education;
    }

    /**
     * 設定 education 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEducation(String value) {
        this.education = value;
    }

    /**
     * 取得 totalWorkYears 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalWorkYears() {
        return totalWorkYears;
    }

    /**
     * 設定 totalWorkYears 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalWorkYears(Long value) {
        this.totalWorkYears = value;
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
     * 取得 unitPhoneNumber 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitPhoneNumber() {
        return unitPhoneNumber;
    }

    /**
     * 設定 unitPhoneNumber 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitPhoneNumber(String value) {
        this.unitPhoneNumber = value;
    }

    /**
     * 取得 unitAddress 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitAddress() {
        return unitAddress;
    }

    /**
     * 設定 unitAddress 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitAddress(String value) {
        this.unitAddress = value;
    }

    /**
     * 取得 unitPostCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitPostCode() {
        return unitPostCode;
    }

    /**
     * 設定 unitPostCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitPostCode(String value) {
        this.unitPostCode = value;
    }

    /**
     * 取得 unitType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitType() {
        return unitType;
    }

    /**
     * 設定 unitType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitType(String value) {
        this.unitType = value;
    }

    /**
     * 取得 dutyLevel 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDutyLevel() {
        return dutyLevel;
    }

    /**
     * 設定 dutyLevel 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDutyLevel(String value) {
        this.dutyLevel = value;
    }

    /**
     * 取得 dutyType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDutyType() {
        return dutyType;
    }

    /**
     * 設定 dutyType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDutyType(String value) {
        this.dutyType = value;
    }

    /**
     * 取得 occupationCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupationCode() {
        return occupationCode;
    }

    /**
     * 設定 occupationCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupationCode(String value) {
        this.occupationCode = value;
    }

    /**
     * 取得 houseProperty 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseProperty() {
        return houseProperty;
    }

    /**
     * 設定 houseProperty 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseProperty(String value) {
        this.houseProperty = value;
    }

    /**
     * 取得 localPoliceStation 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalPoliceStation() {
        return localPoliceStation;
    }

    /**
     * 設定 localPoliceStation 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalPoliceStation(String value) {
        this.localPoliceStation = value;
    }

    /**
     * 取得 roomAddress 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomAddress() {
        return roomAddress;
    }

    /**
     * 設定 roomAddress 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomAddress(String value) {
        this.roomAddress = value;
    }

    /**
     * 取得 roomPostCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomPostCode() {
        return roomPostCode;
    }

    /**
     * 設定 roomPostCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomPostCode(String value) {
        this.roomPostCode = value;
    }

    /**
     * 取得 selfMonthIncome 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSelfMonthIncome() {
        return selfMonthIncome;
    }

    /**
     * 設定 selfMonthIncome 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSelfMonthIncome(BigDecimal value) {
        this.selfMonthIncome = value;
    }

    /**
     * 取得 familyMonthIncome 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFamilyMonthIncome() {
        return familyMonthIncome;
    }

    /**
     * 設定 familyMonthIncome 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFamilyMonthIncome(BigDecimal value) {
        this.familyMonthIncome = value;
    }

    /**
     * 取得 incomeSource 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeSource() {
        return incomeSource;
    }

    /**
     * 設定 incomeSource 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeSource(String value) {
        this.incomeSource = value;
    }

    /**
     * 取得 roomPhone 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomPhone() {
        return roomPhone;
    }

    /**
     * 設定 roomPhone 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomPhone(String value) {
        this.roomPhone = value;
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
     * 取得 familySumQuantity 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFamilySumQuantity() {
        return familySumQuantity;
    }

    /**
     * 設定 familySumQuantity 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFamilySumQuantity(Long value) {
        this.familySumQuantity = value;
    }

    /**
     * 取得 marriage 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarriage() {
        return marriage;
    }

    /**
     * 設定 marriage 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarriage(String value) {
        this.marriage = value;
    }

    /**
     * 取得 spouseName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseName() {
        return spouseName;
    }

    /**
     * 設定 spouseName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseName(String value) {
        this.spouseName = value;
    }

    /**
     * 取得 spouseborndate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSpouseborndate() {
        return spouseborndate;
    }

    /**
     * 設定 spouseborndate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSpouseborndate(XMLGregorianCalendar value) {
        this.spouseborndate = value;
    }

    /**
     * 取得 spouseId 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseId() {
        return spouseId;
    }

    /**
     * 設定 spouseId 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseId(String value) {
        this.spouseId = value;
    }

    /**
     * 取得 spouseUnit 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseUnit() {
        return spouseUnit;
    }

    /**
     * 設定 spouseUnit 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseUnit(String value) {
        this.spouseUnit = value;
    }

    /**
     * 取得 spouseJobTitle 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseJobTitle() {
        return spouseJobTitle;
    }

    /**
     * 設定 spouseJobTitle 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseJobTitle(String value) {
        this.spouseJobTitle = value;
    }

    /**
     * 取得 spouseUnitPhone 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseUnitPhone() {
        return spouseUnitPhone;
    }

    /**
     * 設定 spouseUnitPhone 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseUnitPhone(String value) {
        this.spouseUnitPhone = value;
    }

    /**
     * Gets the value of the flag property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flag property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlag().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFlag() {
        if (flag == null) {
            flag = new ArrayList<String>();
        }
        return this.flag;
    }

    /**
     * Gets the value of the weight property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the weight property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWeight().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigDecimal }
     * 
     * 
     */
    public List<BigDecimal> getWeight() {
        if (weight == null) {
            weight = new ArrayList<BigDecimal>();
        }
        return this.weight;
    }

    /**
     * Gets the value of the stature property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stature property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStature().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStature() {
        if (stature == null) {
            stature = new ArrayList<String>();
        }
        return this.stature;
    }

}
