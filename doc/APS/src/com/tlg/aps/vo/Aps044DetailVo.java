package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.util.Date;

/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業*/
public class Aps044DetailVo {
	private BigDecimal oid;
	private String batchNo;
	private String oldPolicyno;
	private Date oldEnddate;
	private String rnType;
	private String businessnature;
	private String proposalno;
	private String isAddIns;
	private String isAddCalc;
	private String isAutoRenew;
	private String rnPayway;
	private String rnPaydata;
	private Long oldAmtF1;
	private Long oldAmtF2;
	private Long oldAmtQ;
	private Integer oldPremF1;
	private Integer oldPremF2;
	private Integer oldPremQ;
	private Integer oldPremA;
	private Long oidFirPremcalcTmp;
	private BigDecimal amtF1;
	private BigDecimal amtF2;
	private BigDecimal amtQ;
	private Long oidFirPremcalcTmp2;
	private BigDecimal premF1;
	private BigDecimal premF2;
	private BigDecimal premQ;
	private BigDecimal premA;
	private String insuredname;
	private String appliname;
	private String postcode;
	private String postaddress;
	private String dataStatus;
	private String remark;
	private String deleteFlag;
	private String deleteUser;
	private Date deleteDate;
	private String icreate;
	private Date dcreate;
	private String iupdate;
	private Date dupdate;
	private String isRenewal;
	private String isPrintvirtualcode;
	private String isInsured;
	private String policyno;
	private String handler1code;
	private String printvirtualcode;
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	private String hasEndorse;
	private String mortgagee;
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
	
	public BigDecimal getOid() {
		return oid;
	}
	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getOldPolicyno() {
		return oldPolicyno;
	}
	public void setOldPolicyno(String oldPolicyno) {
		this.oldPolicyno = oldPolicyno;
	}
	public Date getOldEnddate() {
		return oldEnddate;
	}
	public void setOldEnddate(Date oldEnddate) {
		this.oldEnddate = oldEnddate;
	}
	public String getRnType() {
		return rnType;
	}
	public void setRnType(String rnType) {
		this.rnType = rnType;
	}
	public String getBusinessnature() {
		return businessnature;
	}
	public void setBusinessnature(String businessnature) {
		this.businessnature = businessnature;
	}
	public String getProposalno() {
		return proposalno;
	}
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	public String getIsAddIns() {
		return isAddIns;
	}
	public void setIsAddIns(String isAddIns) {
		this.isAddIns = isAddIns;
	}
	public String getIsAddCalc() {
		return isAddCalc;
	}
	public void setIsAddCalc(String isAddCalc) {
		this.isAddCalc = isAddCalc;
	}
	public String getIsAutoRenew() {
		return isAutoRenew;
	}
	public void setIsAutoRenew(String isAutoRenew) {
		this.isAutoRenew = isAutoRenew;
	}
	public String getRnPayway() {
		return rnPayway;
	}
	public void setRnPayway(String rnPayway) {
		this.rnPayway = rnPayway;
	}
	public String getRnPaydata() {
		return rnPaydata;
	}
	public void setRnPaydata(String rnPaydata) {
		this.rnPaydata = rnPaydata;
	}
	public Long getOldAmtF1() {
		return oldAmtF1;
	}
	public void setOldAmtF1(Long oldAmtF1) {
		this.oldAmtF1 = oldAmtF1;
	}
	public Long getOldAmtF2() {
		return oldAmtF2;
	}
	public void setOldAmtF2(Long oldAmtF2) {
		this.oldAmtF2 = oldAmtF2;
	}
	public Long getOldAmtQ() {
		return oldAmtQ;
	}
	public void setOldAmtQ(Long oldAmtQ) {
		this.oldAmtQ = oldAmtQ;
	}
	public Integer getOldPremF1() {
		return oldPremF1;
	}
	public void setOldPremF1(Integer oldPremF1) {
		this.oldPremF1 = oldPremF1;
	}
	public Integer getOldPremF2() {
		return oldPremF2;
	}
	public void setOldPremF2(Integer oldPremF2) {
		this.oldPremF2 = oldPremF2;
	}
	public Integer getOldPremQ() {
		return oldPremQ;
	}
	public void setOldPremQ(Integer oldPremQ) {
		this.oldPremQ = oldPremQ;
	}
	public Integer getOldPremA() {
		return oldPremA;
	}
	public void setOldPremA(Integer oldPremA) {
		this.oldPremA = oldPremA;
	}
	public Long getOidFirPremcalcTmp() {
		return oidFirPremcalcTmp;
	}
	public void setOidFirPremcalcTmp(Long oidFirPremcalcTmp) {
		this.oidFirPremcalcTmp = oidFirPremcalcTmp;
	}
	public BigDecimal getAmtF1() {
		return amtF1;
	}
	public void setAmtF1(BigDecimal amtF1) {
		this.amtF1 = amtF1;
	}
	public BigDecimal getAmtF2() {
		return amtF2;
	}
	public void setAmtF2(BigDecimal amtF2) {
		this.amtF2 = amtF2;
	}
	public BigDecimal getAmtQ() {
		return amtQ;
	}
	public void setAmtQ(BigDecimal amtQ) {
		this.amtQ = amtQ;
	}
	public Long getOidFirPremcalcTmp2() {
		return oidFirPremcalcTmp2;
	}
	public void setOidFirPremcalcTmp2(Long oidFirPremcalcTmp2) {
		this.oidFirPremcalcTmp2 = oidFirPremcalcTmp2;
	}
	public BigDecimal getPremF1() {
		return premF1;
	}
	public void setPremF1(BigDecimal premF1) {
		this.premF1 = premF1;
	}
	public BigDecimal getPremF2() {
		return premF2;
	}
	public void setPremF2(BigDecimal premF2) {
		this.premF2 = premF2;
	}
	public BigDecimal getPremQ() {
		return premQ;
	}
	public void setPremQ(BigDecimal premQ) {
		this.premQ = premQ;
	}
	public BigDecimal getPremA() {
		return premA;
	}
	public void setPremA(BigDecimal premA) {
		this.premA = premA;
	}
	public String getInsuredname() {
		return insuredname;
	}
	public void setInsuredname(String insuredname) {
		this.insuredname = insuredname;
	}
	public String getAppliname() {
		return appliname;
	}
	public void setAppliname(String appliname) {
		this.appliname = appliname;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPostaddress() {
		return postaddress;
	}
	public void setPostaddress(String postaddress) {
		this.postaddress = postaddress;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getDeleteUser() {
		return deleteUser;
	}
	public void setDeleteUser(String deleteUser) {
		this.deleteUser = deleteUser;
	}
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	public String getIcreate() {
		return icreate;
	}
	public void setIcreate(String icreate) {
		this.icreate = icreate;
	}
	public Date getDcreate() {
		return dcreate;
	}
	public void setDcreate(Date dcreate) {
		this.dcreate = dcreate;
	}
	public String getIupdate() {
		return iupdate;
	}
	public void setIupdate(String iupdate) {
		this.iupdate = iupdate;
	}
	public Date getDupdate() {
		return dupdate;
	}
	public void setDupdate(Date dupdate) {
		this.dupdate = dupdate;
	}
	public String getIsRenewal() {
		return isRenewal;
	}
	public void setIsRenewal(String isRenewal) {
		this.isRenewal = isRenewal;
	}
	public String getIsPrintvirtualcode() {
		return isPrintvirtualcode;
	}
	public void setIsPrintvirtualcode(String isPrintvirtualcode) {
		this.isPrintvirtualcode = isPrintvirtualcode;
	}
	public String getIsInsured() {
		return isInsured;
	}
	public void setIsInsured(String isInsured) {
		this.isInsured = isInsured;
	}
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public String getHandler1code() {
		return handler1code;
	}
	public void setHandler1code(String handler1code) {
		this.handler1code = handler1code;
	}
	public String getPrintvirtualcode() {
		return printvirtualcode;
	}
	public void setPrintvirtualcode(String printvirtualcode) {
		this.printvirtualcode = printvirtualcode;
	}
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	public String getHasEndorse() {
		return hasEndorse;
	}
	public void setHasEndorse(String hasEndorse) {
		this.hasEndorse = hasEndorse;
	}
	public String getMortgagee() {
		return mortgagee;
	}
	public void setMortgagee(String mortgagee) {
		this.mortgagee = mortgagee;
	}
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
}
