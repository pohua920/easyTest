/**
 * 2014-6-13
 */
package com.sinosoft.claim.print.vo;

import java.util.ArrayList;
import java.util.List;


/**
 * 工程险  残余物理算书  数据对象 
 * @author 中科軟
 */
public class CompensateObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** 险别 */
	private String riskCode = "";
	/**承保端大險種名稱*/
	private String riskCodeName = "";
	/**殘餘物理賠計算書號碼（同車險）*/
	private String compensateNo = "";
	/**殘餘物賠付次數（同車險）*/
	private String times = "";
	/**保單號碼*/
	private String policyNo = "";
	/**要保人*/
	private String appliName = "";
	/**要保人統一編號*/
	private String appliIdentifyNumber = "";
	/**被保險人（備案選擇之被保險人）*/
	private String insuredName = "";
	/**被保險人身分證字號/統一編號*/
	private String insuredIdentifyNumber = "";
	/**保單收費情形*/
	private String intReturn = "";
	/**收費日：收付方式的收費日期 prpJPayRefRecHis.payRefDate  */
	private String payRefDate = "";
	/**票据到期日*/
	private String billEndDate = "";
	/**批改生效日 賠案出險時批單日期  prpPhead.validDate*/
	private String validDate = "";
	/**出單日期 保單核保通過日期  com.sinosoft.claim.schema.model.PrpCmain.underwriteEndDate*/
	private String inputDate = "";
	/**出險日期*/
	private String damageStartDate = "";
	/**受理日期 備案的收件日期  PrpLclaim*/
	private String receiptDate = "";
	/**結案日 殘餘物處理提交日期com.sinosoft.claim.schema.model.PrpLcompensate.inputDate*/
	private String endCaseDate = "";
	/**保險期間(年月日小時)*/
	private String startDate  = "";
	/**簽單保費 承保端保險費*/
	private String sumPremium  = "";
	/**賠付總額   總賠付金額+總費用金額 */
	private String sumPaid  = "";
	/**凈損金額  總賠付金額 */
	private String sumClaim  = "";
	/**公證費  費用類型為“公證費”的費用金額  prpLcharge.ChargeAmount()*prpLcharge.exchRate  chargeCode='5' */
	private String  assessmentFee = "";
	/**估理費：費用類型為“估理費”的費用金額 0*/
	private String  richardFee  = "";
	/**其他費用 總費用金額-公證費-估理費   prpLcharge.ChargeAmount()*prpLcharge.exchRate  没有条件*/
	private String  otherCosts = "";
	/** 残余物险别对象 */
	private List<CompensateSubreportObject> compensateSubreport0Object = new ArrayList<CompensateSubreportObject>(0);
	/** 残余物费用对象 */
	private List<CompensateSubreportObject> compensateSubreport1Object = new ArrayList<CompensateSubreportObject>(0);
	/** 公證公司列表 */
	private List<CompensateSubreportObject> compensateSubreport2Object = new ArrayList<CompensateSubreportObject>(0);
	/**理算說明*/
	private String ctext = "";
	/** 理算說明 */
	private List<CompensateContextObject> contextList = new ArrayList<CompensateContextObject>();
	/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --start */
	//經辦人信息查詢
	private String handleCode = "";//總公司經辦人code
	private String handleName = "";//總公司經辦人name
	private String handleCode1 = "";//分公司經辦人code
	private String handleName1 = "";//分公司經辦人name
	//服務人員信息查詢
	private String handler1Name = "";
	/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --end */
	
	public CompensateObject() {
		super();
	}
	public CompensateObject(CompensateObject compensateObject) {
		super();
		this.riskCodeName = compensateObject.riskCodeName;
		this.compensateNo = compensateObject.compensateNo;
		this.times = compensateObject.times;
		this.policyNo = compensateObject.policyNo;
		this.appliName = compensateObject.appliName;
		this.appliIdentifyNumber = compensateObject.appliIdentifyNumber;
		this.insuredName = compensateObject.insuredName;
		this.insuredIdentifyNumber = compensateObject.insuredIdentifyNumber;
		this.intReturn = compensateObject.intReturn;
		this.payRefDate = compensateObject.payRefDate;
		this.billEndDate = compensateObject.billEndDate;
		this.validDate = compensateObject.validDate;
		this.inputDate = compensateObject.inputDate;
		this.damageStartDate = compensateObject.damageStartDate;
		this.receiptDate = compensateObject.receiptDate;
		this.endCaseDate = compensateObject.endCaseDate;
		this.startDate = compensateObject.startDate;
		this.sumPremium = compensateObject.sumPremium;
		this.sumPaid = compensateObject.sumPaid;
		this.sumClaim = compensateObject.sumClaim;
		this.assessmentFee = compensateObject.assessmentFee;
		this.richardFee = compensateObject.richardFee;
		this.otherCosts = compensateObject.otherCosts;
		this.compensateSubreport0Object = compensateObject.compensateSubreport0Object;
		this.compensateSubreport1Object = compensateObject.compensateSubreport1Object;
		this.ctext = compensateObject.ctext;
	}
	public String getRiskCodeName() {
		return riskCodeName;
	}
	public void setRiskCodeName(String riskCodeName) {
		this.riskCodeName = riskCodeName;
	}
	public String getCompensateNo() {
		return compensateNo;
	}
	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getAppliName() {
		return appliName;
	}
	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}
	public String getAppliIdentifyNumber() {
		return appliIdentifyNumber;
	}
	public void setAppliIdentifyNumber(String appliIdentifyNumber) {
		this.appliIdentifyNumber = appliIdentifyNumber;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getInsuredIdentifyNumber() {
		return insuredIdentifyNumber;
	}
	public void setInsuredIdentifyNumber(String insuredIdentifyNumber) {
		this.insuredIdentifyNumber = insuredIdentifyNumber;
	}
	public String getIntReturn() {
		return intReturn;
	}
	public void setIntReturn(String intReturn) {
		this.intReturn = intReturn;
	}
	public String getPayRefDate() {
		return payRefDate;
	}
	public void setPayRefDate(String payRefDate) {
		this.payRefDate = payRefDate;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getEndCaseDate() {
		return endCaseDate;
	}
	public void setEndCaseDate(String endCaseDate) {
		this.endCaseDate = endCaseDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getSumPremium() {
		return sumPremium;
	}
	public void setSumPremium(String sumPremium) {
		this.sumPremium = sumPremium;
	}
	public String getSumPaid() {
		return sumPaid;
	}
	public void setSumPaid(String sumPaid) {
		this.sumPaid = sumPaid;
	}
	public String getSumClaim() {
		return sumClaim;
	}
	public void setSumClaim(String sumClaim) {
		this.sumClaim = sumClaim;
	}
	public String getAssessmentFee() {
		return assessmentFee;
	}
	public void setAssessmentFee(String assessmentFee) {
		this.assessmentFee = assessmentFee;
	}
	public String getRichardFee() {
		return richardFee;
	}
	public void setRichardFee(String richardFee) {
		this.richardFee = richardFee;
	}
	public String getOtherCosts() {
		return otherCosts;
	}
	public void setOtherCosts(String otherCosts) {
		this.otherCosts = otherCosts;
	}
	
	public String getCtext() {
		return ctext;
	}
	public void setCtext(String ctext) {
		this.ctext = ctext;
	}
	public String getBillEndDate() {
		return billEndDate;
	}
	public void setBillEndDate(String billEndDate) {
		this.billEndDate = billEndDate;
	}
	public List<CompensateSubreportObject> getCompensateSubreport0Object() {
		return compensateSubreport0Object;
	}
	public void setCompensateSubreport0Object(List<CompensateSubreportObject> compensateSubreport0Object) {
		this.compensateSubreport0Object = compensateSubreport0Object;
	}
	public List<CompensateSubreportObject> getCompensateSubreport1Object() {
		return compensateSubreport1Object;
	}
	public void setCompensateSubreport1Object(List<CompensateSubreportObject> compensateSubreport1Object) {
		this.compensateSubreport1Object = compensateSubreport1Object;
	}
	public List<CompensateSubreportObject> getCompensateSubreport2Object() {
		return compensateSubreport2Object;
	}
	public void setCompensateSubreport2Object(List<CompensateSubreportObject> compensateSubreport2Object) {
		this.compensateSubreport2Object = compensateSubreport2Object;
	}
	public List<CompensateContextObject> getContextList() {
		return contextList;
	}
	public void setContextList(List<CompensateContextObject> contextList) {
		this.contextList = contextList;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --start */
	public String getHandleCode() {
		return handleCode;
	}
	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}
	public String getHandleName() {
		return handleName;
	}
	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}
	public String getHandleCode1() {
		return handleCode1;
	}
	public void setHandleCode1(String handleCode1) {
		this.handleCode1 = handleCode1;
	}
	public String getHandleName1() {
		return handleName1;
	}
	public void setHandleName1(String handleName1) {
		this.handleName1 = handleName1;
	}
	public String getHandler1Name() {
		return handler1Name;
	}
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}
	/* mantis： CLM0045 ，處理人員：BK007 蘇哲，需求單編號：CLM0045理賠計算書將開票單位異動成服務人員(非車) --end */
}
