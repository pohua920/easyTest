package com.tlg.aps.vo;

import java.util.Date;

/* mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程  */
public class FirPahsinRenewalVo {
	
	//型態暫時都給字串，若比對有用到再調整
	private String policyno;
	private String handleridentifynumber;
	private Date enddate;
	private String othflag;
	private String epolicy;
	private String clauseSendtype;
	private String addresscode;
	private String addressdetailinfo;
	private String mortgageepcode2;
	private Integer serialno2;
	private String wallmaterial;
	private String roofmaterial;
	private String sumfloors;
	private String buildarea;
	private String buildyears;
	private String structure;
	private String isAutoRenew;
	private String premiumT;
	private String amtQ;
	private String premQ;
	private String amtF;
	private String premF;
	
	private String identifynumber;
	private String postcode;
	private Date birthday;
	private String insuredname;
	
	private String batchNo;
	private Integer dataqtyT;
	private Integer dataqtyS;
	private Integer dataqtyF;
	private Integer dataqtySf;
	private String fileStatus;
	private String transStatus;
	private String dataqtyFix;
	private Date dcreate;
	private String isEndorse;
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	private String identifynumber2;
	private String postcode2;
	private String birthday2;
	private String identifynumber1;
	private String postcode1;
	private String birthday1;
	private String warnMsg;
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 start*/
	private String amtFLast;
	private String amtQLast;
	private String premFLast;
	private String premQLast;
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 end*/
	
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public String getHandleridentifynumber() {
		return handleridentifynumber;
	}
	public void setHandleridentifynumber(String handleridentifynumber) {
		this.handleridentifynumber = handleridentifynumber;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getOthflag() {
		return othflag;
	}
	public void setOthflag(String othflag) {
		this.othflag = othflag;
	}
	public String getEpolicy() {
		return epolicy;
	}
	public void setEpolicy(String epolicy) {
		this.epolicy = epolicy;
	}
	public String getClauseSendtype() {
		return clauseSendtype;
	}
	public void setClauseSendtype(String clauseSendtype) {
		this.clauseSendtype = clauseSendtype;
	}
	public String getAddresscode() {
		return addresscode;
	}
	public void setAddresscode(String addresscode) {
		this.addresscode = addresscode;
	}
	public String getAddressdetailinfo() {
		return addressdetailinfo;
	}
	public void setAddressdetailinfo(String addressdetailinfo) {
		this.addressdetailinfo = addressdetailinfo;
	}
	public String getMortgageepcode2() {
		return mortgageepcode2;
	}
	public void setMortgageepcode2(String mortgageepcode2) {
		this.mortgageepcode2 = mortgageepcode2;
	}
	public Integer getSerialno2() {
		return serialno2;
	}
	public void setSerialno2(Integer serialno2) {
		this.serialno2 = serialno2;
	}
	public String getWallmaterial() {
		return wallmaterial;
	}
	public void setWallmaterial(String wallmaterial) {
		this.wallmaterial = wallmaterial;
	}
	public String getRoofmaterial() {
		return roofmaterial;
	}
	public void setRoofmaterial(String roofmaterial) {
		this.roofmaterial = roofmaterial;
	}
	public String getSumfloors() {
		return sumfloors;
	}
	public void setSumfloors(String sumfloors) {
		this.sumfloors = sumfloors;
	}
	public String getBuildarea() {
		return buildarea;
	}
	public void setBuildarea(String buildarea) {
		this.buildarea = buildarea;
	}
	public String getBuildyears() {
		return buildyears;
	}
	public void setBuildyears(String buildyears) {
		this.buildyears = buildyears;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getIsAutoRenew() {
		return isAutoRenew;
	}
	public void setIsAutoRenew(String isAutoRenew) {
		this.isAutoRenew = isAutoRenew;
	}
	public String getPremiumT() {
		return premiumT;
	}
	public void setPremiumT(String premiumT) {
		this.premiumT = premiumT;
	}
	public String getAmtQ() {
		return amtQ;
	}
	public void setAmtQ(String amtQ) {
		this.amtQ = amtQ;
	}
	public String getPremQ() {
		return premQ;
	}
	public void setPremQ(String premQ) {
		this.premQ = premQ;
	}
	public String getAmtF() {
		return amtF;
	}
	public void setAmtF(String amtF) {
		this.amtF = amtF;
	}
	public String getPremF() {
		return premF;
	}
	public void setPremF(String premF) {
		this.premF = premF;
	}
	public String getIdentifynumber() {
		return identifynumber;
	}
	public void setIdentifynumber(String identifynumber) {
		this.identifynumber = identifynumber;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Integer getDataqtyT() {
		return dataqtyT;
	}
	public void setDataqtyT(Integer dataqtyT) {
		this.dataqtyT = dataqtyT;
	}
	public Integer getDataqtyS() {
		return dataqtyS;
	}
	public void setDataqtyS(Integer dataqtyS) {
		this.dataqtyS = dataqtyS;
	}
	public Integer getDataqtyF() {
		return dataqtyF;
	}
	public void setDataqtyF(Integer dataqtyF) {
		this.dataqtyF = dataqtyF;
	}
	public Integer getDataqtySf() {
		return dataqtySf;
	}
	public void setDataqtySf(Integer dataqtySf) {
		this.dataqtySf = dataqtySf;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getDataqtyFix() {
		return dataqtyFix;
	}
	public void setDataqtyFix(String dataqtyFix) {
		this.dataqtyFix = dataqtyFix;
	}
	public Date getDcreate() {
		return dcreate;
	}
	public void setDcreate(Date dcreate) {
		this.dcreate = dcreate;
	}
	public String getInsuredname() {
		return insuredname;
	}
	public void setInsuredname(String insuredname) {
		this.insuredname = insuredname;
	}
	public String getIsEndorse() {
		return isEndorse;
	}
	public void setIsEndorse(String isEndorse) {
		this.isEndorse = isEndorse;
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	public String getIdentifynumber2() {
		return identifynumber2;
	}
	public void setIdentifynumber2(String identifynumber2) {
		this.identifynumber2 = identifynumber2;
	}
	public String getPostcode2() {
		return postcode2;
	}
	public void setPostcode2(String postcode2) {
		this.postcode2 = postcode2;
	}
	public String getBirthday2() {
		return birthday2;
	}
	public void setBirthday2(String birthday2) {
		this.birthday2 = birthday2;
	}
	public String getIdentifynumber1() {
		return identifynumber1;
	}
	public void setIdentifynumber1(String identifynumber1) {
		this.identifynumber1 = identifynumber1;
	}
	public String getPostcode1() {
		return postcode1;
	}
	public void setPostcode1(String postcode1) {
		this.postcode1 = postcode1;
	}
	public String getBirthday1() {
		return birthday1;
	}
	public void setBirthday1(String birthday1) {
		this.birthday1 = birthday1;
	}
	public String getWarnMsg() {
		return warnMsg;
	}
	public void setWarnMsg(String warnMsg) {
		this.warnMsg = warnMsg;
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 start*/
	public String getAmtFLast() {
		return amtFLast;
	}
	public void setAmtFLast(String amtFLast) {
		this.amtFLast = amtFLast;
	}
	public String getAmtQLast() {
		return amtQLast;
	}
	public void setAmtQLast(String amtQLast) {
		this.amtQLast = amtQLast;
	}
	public String getPremFLast() {
		return premFLast;
	}
	public void setPremFLast(String premFLast) {
		this.premFLast = premFLast;
	}
	public String getPremQLast() {
		return premQLast;
	}
	public void setPremQLast(String premQLast) {
		this.premQLast = premQLast;
	}
	/*mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 end*/
}
