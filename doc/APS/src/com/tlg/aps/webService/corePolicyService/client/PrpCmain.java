
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
 * <p>prpCmain complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
 * 
 * <pre>
 * &lt;complexType name="prpCmain">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="policyNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quoteno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="classCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proposalNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="policySort" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="printNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessNature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appliCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appliName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="appliaddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="insuredAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operateDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="startHour" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="endHour" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="pureRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="disRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="discount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sumValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumDiscount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumPremium" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumSubPrem" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumQuantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="judicalCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="judicalScope" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="autoTransRenewFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="argueSolution" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arbitBoardName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payTimes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="endorseTimes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="claimTimes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="makeCom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operateSite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="loginCom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handlerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handlerIdentifyNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler1Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="approverCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="underWriteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="underWriteName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="inputhour" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="underWriteEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="statisticsYM" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="agentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coinsFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reinsFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="allinsFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="underWriteFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="othFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="disRate1" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="businessFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateRCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="updateHour" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="shareHolderFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agreementNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inquiryNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="visaCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="manualType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nationFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startMinute" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *         &lt;element name="endMinute" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/>
 *         &lt;element name="jfeeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preCheckDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="handlerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler1Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payRefCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payRefName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="payRefTime" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="printTime" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="agriType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subBusinessNature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="projectsFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proposalLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stopTimes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="effectiveimmediatelyFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="newStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="newEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="groupType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startStages" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="lockerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="editFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rsnNorenewal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notRenewalRegist" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contributionLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caseno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="declareFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="biznosysflag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessRecmark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="undwrtmark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isundwrtflag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentmaxcomission" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="bankflag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ratePeriodType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ratePeriod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ratePeriodOld" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rateStartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="rateEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="govPurchaseFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fycFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="directBusiness" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extraComCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extraComName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="introducerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="introducerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agent1Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agent1Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler2Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler2Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler2IDType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler2ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler2Mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler2Post" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="handler2Address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessTypeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tradeVanID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="batchNO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="asPolicyNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seriesCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prpCitemKinds" type="{http://sinosoft.com.cn}prpCitemKind" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prpCinsureds" type="{http://sinosoft.com.cn}prpCinsured" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prpCcommissions" type="{http://sinosoft.com.cn}prpCcommission" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prpCcommissionDetails" type="{http://sinosoft.com.cn}prpCcommissionDetail" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prpCitemCars" type="{http://sinosoft.com.cn}prpCitemCar" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prpCitemCarExts" type="{http://sinosoft.com.cn}prpCitemCarExt" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prpClimits" type="{http://sinosoft.com.cn}prpClimit" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prpCfees" type="{http://sinosoft.com.cn}prpCfee" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prpCplans" type="{http://sinosoft.com.cn}prpCplan" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prpCmain", propOrder = {
    "policyNo",
    "quoteno",
    "classCode",
    "riskCode",
    "proposalNo",
    "contractNo",
    "policySort",
    "printNo",
    "businessNature",
    "language",
    "appliCode",
    "appliName",
    "appliaddress",
    "insuredCode",
    "insuredName",
    "insuredAddress",
    "operateDate",
    "startDate",
    "startHour",
    "endDate",
    "endHour",
    "pureRate",
    "disRate",
    "discount",
    "currency",
    "sumValue",
    "sumAmount",
    "sumDiscount",
    "sumPremium",
    "sumSubPrem",
    "sumQuantity",
    "judicalCode",
    "judicalScope",
    "autoTransRenewFlag",
    "argueSolution",
    "arbitBoardName",
    "payTimes",
    "endorseTimes",
    "claimTimes",
    "makeCom",
    "operateSite",
    "comCode",
    "loginCom",
    "handlerCode",
    "handlerIdentifyNumber",
    "handler1Code",
    "approverCode",
    "underWriteCode",
    "underWriteName",
    "operatorCode",
    "operatorName",
    "inputDate",
    "inputhour",
    "underWriteEndDate",
    "statisticsYM",
    "agentCode",
    "agentName",
    "coinsFlag",
    "reinsFlag",
    "allinsFlag",
    "underWriteFlag",
    "othFlag",
    "flag",
    "disRate1",
    "businessFlag",
    "updateRCode",
    "updateDate",
    "updateHour",
    "signDate",
    "shareHolderFlag",
    "agreementNo",
    "inquiryNo",
    "payMode",
    "remark",
    "visaCode",
    "manualType",
    "nationFlag",
    "startMinute",
    "endMinute",
    "jfeeFlag",
    "preCheckDate",
    "handlerName",
    "handler1Name",
    "payRefCode",
    "payRefName",
    "payRefTime",
    "printTime",
    "agriType",
    "subBusinessNature",
    "bankCode",
    "channelType",
    "exchangeRate",
    "projectsFlag",
    "proposalLevel",
    "stopTimes",
    "effectiveimmediatelyFlag",
    "newStartDate",
    "newEndDate",
    "groupType",
    "startStages",
    "lockerCode",
    "editFlag",
    "rsnNorenewal",
    "notRenewalRegist",
    "contributionLevel",
    "caseno",
    "declareFlag",
    "channelcode",
    "biznosysflag",
    "businessRecmark",
    "undwrtmark",
    "isundwrtflag",
    "agentmaxcomission",
    "bankflag",
    "ratePeriodType",
    "ratePeriod",
    "ratePeriodOld",
    "rateStartDate",
    "rateEndDate",
    "govPurchaseFlag",
    "fycFlag",
    "directBusiness",
    "extraComCode",
    "extraComName",
    "introducerID",
    "introducerName",
    "agent1Code",
    "agent1Name",
    "handler2Code",
    "handler2Name",
    "handler2IDType",
    "handler2ID",
    "handler2Mobile",
    "handler2Post",
    "handler2Address",
    "businessTypeFlag",
    "tradeVanID",
    "projectCode",
    "batchNO",
    "asPolicyNo",
    "seriesCode",
    "prpCitemKinds",
    "prpCinsureds",
    "prpCcommissions",
    "prpCcommissionDetails",
    "prpCitemCars",
    "prpCitemCarExts",
    "prpClimits",
    "prpCfees",
    "prpCplans"
})
public class PrpCmain {

    protected String policyNo;
    protected String quoteno;
    protected String classCode;
    protected String riskCode;
    protected String proposalNo;
    protected String contractNo;
    protected String policySort;
    protected String printNo;
    protected String businessNature;
    protected String language;
    protected String appliCode;
    protected String appliName;
    protected String appliaddress;
    protected String insuredCode;
    protected String insuredName;
    protected String insuredAddress;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar operateDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    protected Integer startHour;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    protected Integer endHour;
    protected BigDecimal pureRate;
    protected BigDecimal disRate;
    protected BigDecimal discount;
    protected String currency;
    protected BigDecimal sumValue;
    protected BigDecimal sumAmount;
    protected BigDecimal sumDiscount;
    protected BigDecimal sumPremium;
    protected BigDecimal sumSubPrem;
    protected Integer sumQuantity;
    protected String judicalCode;
    protected String judicalScope;
    protected String autoTransRenewFlag;
    protected String argueSolution;
    protected String arbitBoardName;
    protected Integer payTimes;
    protected Integer endorseTimes;
    protected Integer claimTimes;
    protected String makeCom;
    protected String operateSite;
    protected String comCode;
    protected String loginCom;
    protected String handlerCode;
    protected String handlerIdentifyNumber;
    protected String handler1Code;
    protected String approverCode;
    protected String underWriteCode;
    protected String underWriteName;
    protected String operatorCode;
    protected String operatorName;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar inputDate;
    protected Integer inputhour;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar underWriteEndDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar statisticsYM;
    protected String agentCode;
    protected String agentName;
    protected String coinsFlag;
    protected String reinsFlag;
    protected String allinsFlag;
    protected String underWriteFlag;
    protected String othFlag;
    protected String flag;
    protected BigDecimal disRate1;
    protected String businessFlag;
    protected String updateRCode;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar updateDate;
    protected String updateHour;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar signDate;
    protected String shareHolderFlag;
    protected String agreementNo;
    protected String inquiryNo;
    protected String payMode;
    protected String remark;
    protected String visaCode;
    protected String manualType;
    protected String nationFlag;
    protected Byte startMinute;
    protected Byte endMinute;
    protected String jfeeFlag;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar preCheckDate;
    protected String handlerName;
    protected String handler1Name;
    protected String payRefCode;
    protected String payRefName;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar payRefTime;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar printTime;
    protected String agriType;
    protected String subBusinessNature;
    protected String bankCode;
    protected String channelType;
    protected BigDecimal exchangeRate;
    protected String projectsFlag;
    protected String proposalLevel;
    protected String stopTimes;
    protected String effectiveimmediatelyFlag;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar newStartDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar newEndDate;
    protected String groupType;
    protected Integer startStages;
    protected String lockerCode;
    protected String editFlag;
    protected String rsnNorenewal;
    protected String notRenewalRegist;
    protected String contributionLevel;
    protected String caseno;
    protected String declareFlag;
    protected String channelcode;
    protected String biznosysflag;
    protected String businessRecmark;
    protected String undwrtmark;
    protected String isundwrtflag;
    protected BigDecimal agentmaxcomission;
    protected String bankflag;
    protected String ratePeriodType;
    protected String ratePeriod;
    protected String ratePeriodOld;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar rateStartDate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar rateEndDate;
    protected String govPurchaseFlag;
    protected String fycFlag;
    protected String directBusiness;
    protected String extraComCode;
    protected String extraComName;
    protected String introducerID;
    protected String introducerName;
    protected String agent1Code;
    protected String agent1Name;
    protected String handler2Code;
    protected String handler2Name;
    protected String handler2IDType;
    protected String handler2ID;
    protected String handler2Mobile;
    protected String handler2Post;
    protected String handler2Address;
    protected String businessTypeFlag;
    protected String tradeVanID;
    protected String projectCode;
    protected String batchNO;
    protected String asPolicyNo;
    protected String seriesCode;
    @XmlElement(nillable = true)
    protected List<PrpCitemKind> prpCitemKinds;
    @XmlElement(nillable = true)
    protected List<PrpCinsured> prpCinsureds;
    @XmlElement(nillable = true)
    protected List<PrpCcommission> prpCcommissions;
    @XmlElement(nillable = true)
    protected List<PrpCcommissionDetail> prpCcommissionDetails;
    @XmlElement(nillable = true)
    protected List<PrpCitemCar> prpCitemCars;
    @XmlElement(nillable = true)
    protected List<PrpCitemCarExt> prpCitemCarExts;
    @XmlElement(nillable = true)
    protected List<PrpClimit> prpClimits;
    @XmlElement(nillable = true)
    protected List<PrpCfee> prpCfees;
    @XmlElement(nillable = true)
    protected List<PrpCplan> prpCplans;

    /**
     * 取得 policyNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyNo() {
        return policyNo;
    }

    /**
     * 設定 policyNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyNo(String value) {
        this.policyNo = value;
    }

    /**
     * 取得 quoteno 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuoteno() {
        return quoteno;
    }

    /**
     * 設定 quoteno 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuoteno(String value) {
        this.quoteno = value;
    }

    /**
     * 取得 classCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * 設定 classCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassCode(String value) {
        this.classCode = value;
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
     * 取得 proposalNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalNo() {
        return proposalNo;
    }

    /**
     * 設定 proposalNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalNo(String value) {
        this.proposalNo = value;
    }

    /**
     * 取得 contractNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 設定 contractNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNo(String value) {
        this.contractNo = value;
    }

    /**
     * 取得 policySort 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicySort() {
        return policySort;
    }

    /**
     * 設定 policySort 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicySort(String value) {
        this.policySort = value;
    }

    /**
     * 取得 printNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintNo() {
        return printNo;
    }

    /**
     * 設定 printNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintNo(String value) {
        this.printNo = value;
    }

    /**
     * 取得 businessNature 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessNature() {
        return businessNature;
    }

    /**
     * 設定 businessNature 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessNature(String value) {
        this.businessNature = value;
    }

    /**
     * 取得 language 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 設定 language 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * 取得 appliCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppliCode() {
        return appliCode;
    }

    /**
     * 設定 appliCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppliCode(String value) {
        this.appliCode = value;
    }

    /**
     * 取得 appliName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppliName() {
        return appliName;
    }

    /**
     * 設定 appliName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppliName(String value) {
        this.appliName = value;
    }

    /**
     * 取得 appliaddress 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppliaddress() {
        return appliaddress;
    }

    /**
     * 設定 appliaddress 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppliaddress(String value) {
        this.appliaddress = value;
    }

    /**
     * 取得 insuredCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredCode() {
        return insuredCode;
    }

    /**
     * 設定 insuredCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredCode(String value) {
        this.insuredCode = value;
    }

    /**
     * 取得 insuredName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredName() {
        return insuredName;
    }

    /**
     * 設定 insuredName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredName(String value) {
        this.insuredName = value;
    }

    /**
     * 取得 insuredAddress 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsuredAddress() {
        return insuredAddress;
    }

    /**
     * 設定 insuredAddress 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsuredAddress(String value) {
        this.insuredAddress = value;
    }

    /**
     * 取得 operateDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOperateDate() {
        return operateDate;
    }

    /**
     * 設定 operateDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOperateDate(XMLGregorianCalendar value) {
        this.operateDate = value;
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
     * 取得 disRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisRate() {
        return disRate;
    }

    /**
     * 設定 disRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisRate(BigDecimal value) {
        this.disRate = value;
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
     * 取得 sumValue 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumValue() {
        return sumValue;
    }

    /**
     * 設定 sumValue 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumValue(BigDecimal value) {
        this.sumValue = value;
    }

    /**
     * 取得 sumAmount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    /**
     * 設定 sumAmount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumAmount(BigDecimal value) {
        this.sumAmount = value;
    }

    /**
     * 取得 sumDiscount 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumDiscount() {
        return sumDiscount;
    }

    /**
     * 設定 sumDiscount 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumDiscount(BigDecimal value) {
        this.sumDiscount = value;
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
     * 取得 sumSubPrem 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumSubPrem() {
        return sumSubPrem;
    }

    /**
     * 設定 sumSubPrem 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumSubPrem(BigDecimal value) {
        this.sumSubPrem = value;
    }

    /**
     * 取得 sumQuantity 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSumQuantity() {
        return sumQuantity;
    }

    /**
     * 設定 sumQuantity 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSumQuantity(Integer value) {
        this.sumQuantity = value;
    }

    /**
     * 取得 judicalCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJudicalCode() {
        return judicalCode;
    }

    /**
     * 設定 judicalCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJudicalCode(String value) {
        this.judicalCode = value;
    }

    /**
     * 取得 judicalScope 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJudicalScope() {
        return judicalScope;
    }

    /**
     * 設定 judicalScope 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJudicalScope(String value) {
        this.judicalScope = value;
    }

    /**
     * 取得 autoTransRenewFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoTransRenewFlag() {
        return autoTransRenewFlag;
    }

    /**
     * 設定 autoTransRenewFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoTransRenewFlag(String value) {
        this.autoTransRenewFlag = value;
    }

    /**
     * 取得 argueSolution 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArgueSolution() {
        return argueSolution;
    }

    /**
     * 設定 argueSolution 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArgueSolution(String value) {
        this.argueSolution = value;
    }

    /**
     * 取得 arbitBoardName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArbitBoardName() {
        return arbitBoardName;
    }

    /**
     * 設定 arbitBoardName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArbitBoardName(String value) {
        this.arbitBoardName = value;
    }

    /**
     * 取得 payTimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPayTimes() {
        return payTimes;
    }

    /**
     * 設定 payTimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPayTimes(Integer value) {
        this.payTimes = value;
    }

    /**
     * 取得 endorseTimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEndorseTimes() {
        return endorseTimes;
    }

    /**
     * 設定 endorseTimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEndorseTimes(Integer value) {
        this.endorseTimes = value;
    }

    /**
     * 取得 claimTimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClaimTimes() {
        return claimTimes;
    }

    /**
     * 設定 claimTimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClaimTimes(Integer value) {
        this.claimTimes = value;
    }

    /**
     * 取得 makeCom 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMakeCom() {
        return makeCom;
    }

    /**
     * 設定 makeCom 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMakeCom(String value) {
        this.makeCom = value;
    }

    /**
     * 取得 operateSite 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperateSite() {
        return operateSite;
    }

    /**
     * 設定 operateSite 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperateSite(String value) {
        this.operateSite = value;
    }

    /**
     * 取得 comCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComCode() {
        return comCode;
    }

    /**
     * 設定 comCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComCode(String value) {
        this.comCode = value;
    }

    /**
     * 取得 loginCom 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginCom() {
        return loginCom;
    }

    /**
     * 設定 loginCom 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginCom(String value) {
        this.loginCom = value;
    }

    /**
     * 取得 handlerCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlerCode() {
        return handlerCode;
    }

    /**
     * 設定 handlerCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlerCode(String value) {
        this.handlerCode = value;
    }

    /**
     * 取得 handlerIdentifyNumber 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlerIdentifyNumber() {
        return handlerIdentifyNumber;
    }

    /**
     * 設定 handlerIdentifyNumber 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlerIdentifyNumber(String value) {
        this.handlerIdentifyNumber = value;
    }

    /**
     * 取得 handler1Code 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler1Code() {
        return handler1Code;
    }

    /**
     * 設定 handler1Code 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler1Code(String value) {
        this.handler1Code = value;
    }

    /**
     * 取得 approverCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproverCode() {
        return approverCode;
    }

    /**
     * 設定 approverCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproverCode(String value) {
        this.approverCode = value;
    }

    /**
     * 取得 underWriteCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnderWriteCode() {
        return underWriteCode;
    }

    /**
     * 設定 underWriteCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnderWriteCode(String value) {
        this.underWriteCode = value;
    }

    /**
     * 取得 underWriteName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnderWriteName() {
        return underWriteName;
    }

    /**
     * 設定 underWriteName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnderWriteName(String value) {
        this.underWriteName = value;
    }

    /**
     * 取得 operatorCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * 設定 operatorCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorCode(String value) {
        this.operatorCode = value;
    }

    /**
     * 取得 operatorName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 設定 operatorName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorName(String value) {
        this.operatorName = value;
    }

    /**
     * 取得 inputDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInputDate() {
        return inputDate;
    }

    /**
     * 設定 inputDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInputDate(XMLGregorianCalendar value) {
        this.inputDate = value;
    }

    /**
     * 取得 inputhour 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInputhour() {
        return inputhour;
    }

    /**
     * 設定 inputhour 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInputhour(Integer value) {
        this.inputhour = value;
    }

    /**
     * 取得 underWriteEndDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUnderWriteEndDate() {
        return underWriteEndDate;
    }

    /**
     * 設定 underWriteEndDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUnderWriteEndDate(XMLGregorianCalendar value) {
        this.underWriteEndDate = value;
    }

    /**
     * 取得 statisticsYM 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStatisticsYM() {
        return statisticsYM;
    }

    /**
     * 設定 statisticsYM 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStatisticsYM(XMLGregorianCalendar value) {
        this.statisticsYM = value;
    }

    /**
     * 取得 agentCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentCode() {
        return agentCode;
    }

    /**
     * 設定 agentCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentCode(String value) {
        this.agentCode = value;
    }

    /**
     * 取得 agentName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * 設定 agentName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
    }

    /**
     * 取得 coinsFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoinsFlag() {
        return coinsFlag;
    }

    /**
     * 設定 coinsFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoinsFlag(String value) {
        this.coinsFlag = value;
    }

    /**
     * 取得 reinsFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReinsFlag() {
        return reinsFlag;
    }

    /**
     * 設定 reinsFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReinsFlag(String value) {
        this.reinsFlag = value;
    }

    /**
     * 取得 allinsFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllinsFlag() {
        return allinsFlag;
    }

    /**
     * 設定 allinsFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllinsFlag(String value) {
        this.allinsFlag = value;
    }

    /**
     * 取得 underWriteFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnderWriteFlag() {
        return underWriteFlag;
    }

    /**
     * 設定 underWriteFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnderWriteFlag(String value) {
        this.underWriteFlag = value;
    }

    /**
     * 取得 othFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOthFlag() {
        return othFlag;
    }

    /**
     * 設定 othFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOthFlag(String value) {
        this.othFlag = value;
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
     * 取得 disRate1 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDisRate1() {
        return disRate1;
    }

    /**
     * 設定 disRate1 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDisRate1(BigDecimal value) {
        this.disRate1 = value;
    }

    /**
     * 取得 businessFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessFlag() {
        return businessFlag;
    }

    /**
     * 設定 businessFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessFlag(String value) {
        this.businessFlag = value;
    }

    /**
     * 取得 updateRCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateRCode() {
        return updateRCode;
    }

    /**
     * 設定 updateRCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateRCode(String value) {
        this.updateRCode = value;
    }

    /**
     * 取得 updateDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateDate() {
        return updateDate;
    }

    /**
     * 設定 updateDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateDate(XMLGregorianCalendar value) {
        this.updateDate = value;
    }

    /**
     * 取得 updateHour 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateHour() {
        return updateHour;
    }

    /**
     * 設定 updateHour 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateHour(String value) {
        this.updateHour = value;
    }

    /**
     * 取得 signDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSignDate() {
        return signDate;
    }

    /**
     * 設定 signDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSignDate(XMLGregorianCalendar value) {
        this.signDate = value;
    }

    /**
     * 取得 shareHolderFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShareHolderFlag() {
        return shareHolderFlag;
    }

    /**
     * 設定 shareHolderFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShareHolderFlag(String value) {
        this.shareHolderFlag = value;
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
     * 取得 inquiryNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInquiryNo() {
        return inquiryNo;
    }

    /**
     * 設定 inquiryNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInquiryNo(String value) {
        this.inquiryNo = value;
    }

    /**
     * 取得 payMode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayMode() {
        return payMode;
    }

    /**
     * 設定 payMode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayMode(String value) {
        this.payMode = value;
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
     * 取得 visaCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisaCode() {
        return visaCode;
    }

    /**
     * 設定 visaCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisaCode(String value) {
        this.visaCode = value;
    }

    /**
     * 取得 manualType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManualType() {
        return manualType;
    }

    /**
     * 設定 manualType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManualType(String value) {
        this.manualType = value;
    }

    /**
     * 取得 nationFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationFlag() {
        return nationFlag;
    }

    /**
     * 設定 nationFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationFlag(String value) {
        this.nationFlag = value;
    }

    /**
     * 取得 startMinute 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getStartMinute() {
        return startMinute;
    }

    /**
     * 設定 startMinute 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setStartMinute(Byte value) {
        this.startMinute = value;
    }

    /**
     * 取得 endMinute 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getEndMinute() {
        return endMinute;
    }

    /**
     * 設定 endMinute 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setEndMinute(Byte value) {
        this.endMinute = value;
    }

    /**
     * 取得 jfeeFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJfeeFlag() {
        return jfeeFlag;
    }

    /**
     * 設定 jfeeFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJfeeFlag(String value) {
        this.jfeeFlag = value;
    }

    /**
     * 取得 preCheckDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreCheckDate() {
        return preCheckDate;
    }

    /**
     * 設定 preCheckDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreCheckDate(XMLGregorianCalendar value) {
        this.preCheckDate = value;
    }

    /**
     * 取得 handlerName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlerName() {
        return handlerName;
    }

    /**
     * 設定 handlerName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlerName(String value) {
        this.handlerName = value;
    }

    /**
     * 取得 handler1Name 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler1Name() {
        return handler1Name;
    }

    /**
     * 設定 handler1Name 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler1Name(String value) {
        this.handler1Name = value;
    }

    /**
     * 取得 payRefCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayRefCode() {
        return payRefCode;
    }

    /**
     * 設定 payRefCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayRefCode(String value) {
        this.payRefCode = value;
    }

    /**
     * 取得 payRefName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayRefName() {
        return payRefName;
    }

    /**
     * 設定 payRefName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayRefName(String value) {
        this.payRefName = value;
    }

    /**
     * 取得 payRefTime 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPayRefTime() {
        return payRefTime;
    }

    /**
     * 設定 payRefTime 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPayRefTime(XMLGregorianCalendar value) {
        this.payRefTime = value;
    }

    /**
     * 取得 printTime 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPrintTime() {
        return printTime;
    }

    /**
     * 設定 printTime 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPrintTime(XMLGregorianCalendar value) {
        this.printTime = value;
    }

    /**
     * 取得 agriType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgriType() {
        return agriType;
    }

    /**
     * 設定 agriType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgriType(String value) {
        this.agriType = value;
    }

    /**
     * 取得 subBusinessNature 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubBusinessNature() {
        return subBusinessNature;
    }

    /**
     * 設定 subBusinessNature 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubBusinessNature(String value) {
        this.subBusinessNature = value;
    }

    /**
     * 取得 bankCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 設定 bankCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    /**
     * 取得 channelType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * 設定 channelType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelType(String value) {
        this.channelType = value;
    }

    /**
     * 取得 exchangeRate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * 設定 exchangeRate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExchangeRate(BigDecimal value) {
        this.exchangeRate = value;
    }

    /**
     * 取得 projectsFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectsFlag() {
        return projectsFlag;
    }

    /**
     * 設定 projectsFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectsFlag(String value) {
        this.projectsFlag = value;
    }

    /**
     * 取得 proposalLevel 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposalLevel() {
        return proposalLevel;
    }

    /**
     * 設定 proposalLevel 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposalLevel(String value) {
        this.proposalLevel = value;
    }

    /**
     * 取得 stopTimes 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStopTimes() {
        return stopTimes;
    }

    /**
     * 設定 stopTimes 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStopTimes(String value) {
        this.stopTimes = value;
    }

    /**
     * 取得 effectiveimmediatelyFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveimmediatelyFlag() {
        return effectiveimmediatelyFlag;
    }

    /**
     * 設定 effectiveimmediatelyFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveimmediatelyFlag(String value) {
        this.effectiveimmediatelyFlag = value;
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
     * 取得 groupType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupType() {
        return groupType;
    }

    /**
     * 設定 groupType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupType(String value) {
        this.groupType = value;
    }

    /**
     * 取得 startStages 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStartStages() {
        return startStages;
    }

    /**
     * 設定 startStages 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStartStages(Integer value) {
        this.startStages = value;
    }

    /**
     * 取得 lockerCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockerCode() {
        return lockerCode;
    }

    /**
     * 設定 lockerCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockerCode(String value) {
        this.lockerCode = value;
    }

    /**
     * 取得 editFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditFlag() {
        return editFlag;
    }

    /**
     * 設定 editFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditFlag(String value) {
        this.editFlag = value;
    }

    /**
     * 取得 rsnNorenewal 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRsnNorenewal() {
        return rsnNorenewal;
    }

    /**
     * 設定 rsnNorenewal 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRsnNorenewal(String value) {
        this.rsnNorenewal = value;
    }

    /**
     * 取得 notRenewalRegist 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotRenewalRegist() {
        return notRenewalRegist;
    }

    /**
     * 設定 notRenewalRegist 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotRenewalRegist(String value) {
        this.notRenewalRegist = value;
    }

    /**
     * 取得 contributionLevel 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContributionLevel() {
        return contributionLevel;
    }

    /**
     * 設定 contributionLevel 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContributionLevel(String value) {
        this.contributionLevel = value;
    }

    /**
     * 取得 caseno 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseno() {
        return caseno;
    }

    /**
     * 設定 caseno 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseno(String value) {
        this.caseno = value;
    }

    /**
     * 取得 declareFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeclareFlag() {
        return declareFlag;
    }

    /**
     * 設定 declareFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeclareFlag(String value) {
        this.declareFlag = value;
    }

    /**
     * 取得 channelcode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelcode() {
        return channelcode;
    }

    /**
     * 設定 channelcode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelcode(String value) {
        this.channelcode = value;
    }

    /**
     * 取得 biznosysflag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBiznosysflag() {
        return biznosysflag;
    }

    /**
     * 設定 biznosysflag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBiznosysflag(String value) {
        this.biznosysflag = value;
    }

    /**
     * 取得 businessRecmark 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessRecmark() {
        return businessRecmark;
    }

    /**
     * 設定 businessRecmark 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessRecmark(String value) {
        this.businessRecmark = value;
    }

    /**
     * 取得 undwrtmark 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUndwrtmark() {
        return undwrtmark;
    }

    /**
     * 設定 undwrtmark 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUndwrtmark(String value) {
        this.undwrtmark = value;
    }

    /**
     * 取得 isundwrtflag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsundwrtflag() {
        return isundwrtflag;
    }

    /**
     * 設定 isundwrtflag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsundwrtflag(String value) {
        this.isundwrtflag = value;
    }

    /**
     * 取得 agentmaxcomission 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAgentmaxcomission() {
        return agentmaxcomission;
    }

    /**
     * 設定 agentmaxcomission 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAgentmaxcomission(BigDecimal value) {
        this.agentmaxcomission = value;
    }

    /**
     * 取得 bankflag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankflag() {
        return bankflag;
    }

    /**
     * 設定 bankflag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankflag(String value) {
        this.bankflag = value;
    }

    /**
     * 取得 ratePeriodType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePeriodType() {
        return ratePeriodType;
    }

    /**
     * 設定 ratePeriodType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePeriodType(String value) {
        this.ratePeriodType = value;
    }

    /**
     * 取得 ratePeriod 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePeriod() {
        return ratePeriod;
    }

    /**
     * 設定 ratePeriod 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePeriod(String value) {
        this.ratePeriod = value;
    }

    /**
     * 取得 ratePeriodOld 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePeriodOld() {
        return ratePeriodOld;
    }

    /**
     * 設定 ratePeriodOld 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePeriodOld(String value) {
        this.ratePeriodOld = value;
    }

    /**
     * 取得 rateStartDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRateStartDate() {
        return rateStartDate;
    }

    /**
     * 設定 rateStartDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRateStartDate(XMLGregorianCalendar value) {
        this.rateStartDate = value;
    }

    /**
     * 取得 rateEndDate 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRateEndDate() {
        return rateEndDate;
    }

    /**
     * 設定 rateEndDate 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRateEndDate(XMLGregorianCalendar value) {
        this.rateEndDate = value;
    }

    /**
     * 取得 govPurchaseFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGovPurchaseFlag() {
        return govPurchaseFlag;
    }

    /**
     * 設定 govPurchaseFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGovPurchaseFlag(String value) {
        this.govPurchaseFlag = value;
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
     * 取得 directBusiness 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectBusiness() {
        return directBusiness;
    }

    /**
     * 設定 directBusiness 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectBusiness(String value) {
        this.directBusiness = value;
    }

    /**
     * 取得 extraComCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtraComCode() {
        return extraComCode;
    }

    /**
     * 設定 extraComCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtraComCode(String value) {
        this.extraComCode = value;
    }

    /**
     * 取得 extraComName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtraComName() {
        return extraComName;
    }

    /**
     * 設定 extraComName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtraComName(String value) {
        this.extraComName = value;
    }

    /**
     * 取得 introducerID 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntroducerID() {
        return introducerID;
    }

    /**
     * 設定 introducerID 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntroducerID(String value) {
        this.introducerID = value;
    }

    /**
     * 取得 introducerName 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntroducerName() {
        return introducerName;
    }

    /**
     * 設定 introducerName 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntroducerName(String value) {
        this.introducerName = value;
    }

    /**
     * 取得 agent1Code 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgent1Code() {
        return agent1Code;
    }

    /**
     * 設定 agent1Code 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgent1Code(String value) {
        this.agent1Code = value;
    }

    /**
     * 取得 agent1Name 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgent1Name() {
        return agent1Name;
    }

    /**
     * 設定 agent1Name 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgent1Name(String value) {
        this.agent1Name = value;
    }

    /**
     * 取得 handler2Code 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler2Code() {
        return handler2Code;
    }

    /**
     * 設定 handler2Code 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler2Code(String value) {
        this.handler2Code = value;
    }

    /**
     * 取得 handler2Name 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler2Name() {
        return handler2Name;
    }

    /**
     * 設定 handler2Name 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler2Name(String value) {
        this.handler2Name = value;
    }

    /**
     * 取得 handler2IDType 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler2IDType() {
        return handler2IDType;
    }

    /**
     * 設定 handler2IDType 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler2IDType(String value) {
        this.handler2IDType = value;
    }

    /**
     * 取得 handler2ID 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler2ID() {
        return handler2ID;
    }

    /**
     * 設定 handler2ID 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler2ID(String value) {
        this.handler2ID = value;
    }

    /**
     * 取得 handler2Mobile 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler2Mobile() {
        return handler2Mobile;
    }

    /**
     * 設定 handler2Mobile 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler2Mobile(String value) {
        this.handler2Mobile = value;
    }

    /**
     * 取得 handler2Post 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler2Post() {
        return handler2Post;
    }

    /**
     * 設定 handler2Post 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler2Post(String value) {
        this.handler2Post = value;
    }

    /**
     * 取得 handler2Address 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandler2Address() {
        return handler2Address;
    }

    /**
     * 設定 handler2Address 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandler2Address(String value) {
        this.handler2Address = value;
    }

    /**
     * 取得 businessTypeFlag 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessTypeFlag() {
        return businessTypeFlag;
    }

    /**
     * 設定 businessTypeFlag 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessTypeFlag(String value) {
        this.businessTypeFlag = value;
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
     * 取得 projectCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * 設定 projectCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

    /**
     * 取得 batchNO 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchNO() {
        return batchNO;
    }

    /**
     * 設定 batchNO 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchNO(String value) {
        this.batchNO = value;
    }

    /**
     * 取得 asPolicyNo 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsPolicyNo() {
        return asPolicyNo;
    }

    /**
     * 設定 asPolicyNo 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsPolicyNo(String value) {
        this.asPolicyNo = value;
    }

    /**
     * 取得 seriesCode 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeriesCode() {
        return seriesCode;
    }

    /**
     * 設定 seriesCode 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeriesCode(String value) {
        this.seriesCode = value;
    }

    /**
     * Gets the value of the prpCitemKinds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCitemKinds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCitemKinds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCitemKind }
     * 
     * 
     */
    public List<PrpCitemKind> getPrpCitemKinds() {
        if (prpCitemKinds == null) {
            prpCitemKinds = new ArrayList<PrpCitemKind>();
        }
        return this.prpCitemKinds;
    }

    /**
     * Gets the value of the prpCinsureds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCinsureds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCinsureds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCinsured }
     * 
     * 
     */
    public List<PrpCinsured> getPrpCinsureds() {
        if (prpCinsureds == null) {
            prpCinsureds = new ArrayList<PrpCinsured>();
        }
        return this.prpCinsureds;
    }

    /**
     * Gets the value of the prpCcommissions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCcommissions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCcommissions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCcommission }
     * 
     * 
     */
    public List<PrpCcommission> getPrpCcommissions() {
        if (prpCcommissions == null) {
            prpCcommissions = new ArrayList<PrpCcommission>();
        }
        return this.prpCcommissions;
    }

    /**
     * Gets the value of the prpCcommissionDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCcommissionDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCcommissionDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCcommissionDetail }
     * 
     * 
     */
    public List<PrpCcommissionDetail> getPrpCcommissionDetails() {
        if (prpCcommissionDetails == null) {
            prpCcommissionDetails = new ArrayList<PrpCcommissionDetail>();
        }
        return this.prpCcommissionDetails;
    }

    /**
     * Gets the value of the prpCitemCars property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCitemCars property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCitemCars().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCitemCar }
     * 
     * 
     */
    public List<PrpCitemCar> getPrpCitemCars() {
        if (prpCitemCars == null) {
            prpCitemCars = new ArrayList<PrpCitemCar>();
        }
        return this.prpCitemCars;
    }

    /**
     * Gets the value of the prpCitemCarExts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCitemCarExts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCitemCarExts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCitemCarExt }
     * 
     * 
     */
    public List<PrpCitemCarExt> getPrpCitemCarExts() {
        if (prpCitemCarExts == null) {
            prpCitemCarExts = new ArrayList<PrpCitemCarExt>();
        }
        return this.prpCitemCarExts;
    }

    /**
     * Gets the value of the prpClimits property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpClimits property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpClimits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpClimit }
     * 
     * 
     */
    public List<PrpClimit> getPrpClimits() {
        if (prpClimits == null) {
            prpClimits = new ArrayList<PrpClimit>();
        }
        return this.prpClimits;
    }

    /**
     * Gets the value of the prpCfees property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCfees property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCfees().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCfee }
     * 
     * 
     */
    public List<PrpCfee> getPrpCfees() {
        if (prpCfees == null) {
            prpCfees = new ArrayList<PrpCfee>();
        }
        return this.prpCfees;
    }

    /**
     * Gets the value of the prpCplans property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prpCplans property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrpCplans().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrpCplan }
     * 
     * 
     */
    public List<PrpCplan> getPrpCplans() {
        if (prpCplans == null) {
            prpCplans = new ArrayList<PrpCplan>();
        }
        return this.prpCplans;
    }

}
