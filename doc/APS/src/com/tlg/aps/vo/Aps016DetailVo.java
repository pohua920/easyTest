package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Aps016DetailVo {
	/* mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業  start */
	
	//tocoreMain
	private BigDecimal oid;
	private String batchNo;
	private Integer batchSeq;
	private String isNew;
	private String printvirtualcode;
	private String policyno;
	private String orderseq;
	private String rationcode;
	private String oldpolicyno;
	private String handleridentifynumber;
	private String handler1code;
	private String comcode;
	private String agentcode;
	private String extracomcode;
	private String extracomname;
	private String businessnature;
	private String channeltype;
	private String projectcode;
	private String introducerid;
	private Date startdate;
	private String startdateCheck;
	private Date maildate;
	private Date updatedate;
	private Integer serialno1;
	private String mortgageepcode1;
	private Integer sequenceno1;
	private String creditnumber1;
	private String collateralnumber1;
	private String loansbehalfnumber1;
	private String loanaccount1;
	private String loansdepartment1;
	private Integer serialno2;
	private String mortgageepcode2;
	private Integer sequenceno2;
	private String creditnumber2;
	private String collateralnumber2;
	private String loansbehalfnumber2;
	private String loanaccount2;
	private String loansdepartment2;
	private Integer addressno;
	private String addresscode;
	private String addressname;
	private String addressdetailinfo;
	private String repeatpolicyno;
	private String possessnaturecode;
	private Integer buildingno;
	private Integer propAddressno;
	private String wallmaterial;
	private String roofmaterial;
	private String structure;
	private String structureText;
	private String sumfloors;
	private String buildarea;
	private String buildyears;
	private String kindcodeF;
	private String kindnameF;
	private String itemcodeF;
	private String itemnameF;
	private String itemnatureF;
	private Integer buildingnoF;
	private BigDecimal highrisefee;
	private String amountF;
	private String premiumF;
	private BigDecimal commrateF;
	private String kindcodeQ;
	private String kindnameQ;
	private String itemcodeQ;
	private String itemnameQ;
	private String itemnatureQ;
	private Integer buildingnoQ;
	private String amountQ;
	private String premiumQ;
	private BigDecimal commrateQ;
	private String remark;
	private String isAutoRenew;
	private String premiumT;
	private String icreate;
	private Date dcreate;
	private String iupdate;
	private Date dupdate;
	private Long amountFLast;
	private Long amountQLast;
	private Long premiumFLast;
	private Long premiumQLast;
	private String amountFAgt;
	private String amountQAgt;
	private String premiumFAgt;
	private String premiumQAgt;
	
	//Dtl
	private String dquakeStatus;
	private String prpinsBatchStatus;
	private Date transPpsTime;
	private String fixUser;
	private Date fixDate;
	private String dataStatus;
	private String checkErrMsg;
	private String checkWarnMsg;
	private Long oidFirPremcalcTmp;
	private String qamtStatus;
	private String famtStatus;
	private String addrDetail;
	private String addrStatus;
	private Long wsFirAmt;
	private Integer wsQuakeAmt;
	private Long oidFirPremcalcTmp2;
	private String dquakeNo;
	private String wallname;
	private String roofname;
	
	private String identifynumber;
	private String insuredname;
	//insured1
	private String insuredflag1;
	private Integer insuredSeq1;
	private String insurednature1;
	private String insuredname1;
	private String identifytype1;
	private String identifynumber1;
	private String phonenumber1;
	private String mobile1;
	private String postcode1;
	private String postaddress1;
	private String ishighdengeroccupation1;
	private String domicile1;
	private String countryename1;
	private String birthday1;
	private String birthday1Check;
	private String listedcabinetcompany1;
	private String headname1;
	private String postname1;
	
	//insure2
	private String insuredflag2;
	private Integer insuredSeq2;
	private String insurednature2;
	private String insuredname2;
	private String identifytype2;
	private String identifynumber2;
	private String phonenumber2;
	private String mobile2;
	private String postcode2;
	private String postaddress2;
	private String ishighdengeroccupation2;
	private String domicile2;
	private String countryename2;
	private String birthday2;
	private String birthday2Check;
	private String listedcabinetcompany2;
	private String headname2;
	private String postname2;
	
	private String proposalno;
	//判斷是否鎖定 Y為鎖定 N為未鎖定
	private String locking;
	private String username;
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	private String isupdate;
	private String sfFlag;
	private String sfReason;
	private String sfUser;
	private Date sfDate;
	private String temp8;
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
	public String getWallname() {
		return wallname;
	}
	public void setWallname(String wallname) {
		this.wallname = wallname;
	}
	public String getRoofname() {
		return roofname;
	}
	public void setRoofname(String roofname) {
		this.roofname = roofname;
	}
	public String getIdentifynumber() {
		return identifynumber;
	}
	public void setIdentifynumber(String identifynumber) {
		this.identifynumber = identifynumber;
	}
	public String getInsuredname() {
		return insuredname;
	}
	public void setInsuredname(String insuredname) {
		this.insuredname = insuredname;
	}
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
	public Integer getBatchSeq() {
		return batchSeq;
	}
	public void setBatchSeq(Integer batchSeq) {
		this.batchSeq = batchSeq;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public String getPrintvirtualcode() {
		return printvirtualcode;
	}
	public void setPrintvirtualcode(String printvirtualcode) {
		this.printvirtualcode = printvirtualcode;
	}
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public String getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(String orderseq) {
		this.orderseq = orderseq;
	}
	public String getRationcode() {
		return rationcode;
	}
	public void setRationcode(String rationcode) {
		this.rationcode = rationcode;
	}
	public String getOldpolicyno() {
		return oldpolicyno;
	}
	public void setOldpolicyno(String oldpolicyno) {
		this.oldpolicyno = oldpolicyno;
	}
	public String getHandleridentifynumber() {
		return handleridentifynumber;
	}
	public void setHandleridentifynumber(String handleridentifynumber) {
		this.handleridentifynumber = handleridentifynumber;
	}
	public String getHandler1code() {
		return handler1code;
	}
	public void setHandler1code(String handler1code) {
		this.handler1code = handler1code;
	}
	public String getComcode() {
		return comcode;
	}
	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
	public String getAgentcode() {
		return agentcode;
	}
	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}
	public String getExtracomcode() {
		return extracomcode;
	}
	public void setExtracomcode(String extracomcode) {
		this.extracomcode = extracomcode;
	}
	public String getExtracomname() {
		return extracomname;
	}
	public void setExtracomname(String extracomname) {
		this.extracomname = extracomname;
	}
	public String getBusinessnature() {
		return businessnature;
	}
	public void setBusinessnature(String businessnature) {
		this.businessnature = businessnature;
	}
	public String getChanneltype() {
		return channeltype;
	}
	public void setChanneltype(String channeltype) {
		this.channeltype = channeltype;
	}
	public String getProjectcode() {
		return projectcode;
	}
	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	public String getIntroducerid() {
		return introducerid;
	}
	public void setIntroducerid(String introducerid) {
		this.introducerid = introducerid;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public String getStartdateCheck() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if(startdate!=null) {
			startdateCheck = sdf.format(startdate);			
		}
		return startdateCheck;
	}
	public void setStartdateCheck(String startdateCheck) {
		this.startdateCheck = startdateCheck;
	}
	public Date getMaildate() {
		return maildate;
	}
	public void setMaildate(Date maildate) {
		this.maildate = maildate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public Integer getSerialno1() {
		return serialno1;
	}
	public void setSerialno1(Integer serialno1) {
		this.serialno1 = serialno1;
	}
	public String getMortgageepcode1() {
		return mortgageepcode1;
	}
	public void setMortgageepcode1(String mortgageepcode1) {
		this.mortgageepcode1 = mortgageepcode1;
	}
	public Integer getSequenceno1() {
		return sequenceno1;
	}
	public void setSequenceno1(Integer sequenceno1) {
		this.sequenceno1 = sequenceno1;
	}
	public String getCreditnumber1() {
		return creditnumber1;
	}
	public void setCreditnumber1(String creditnumber1) {
		this.creditnumber1 = creditnumber1;
	}
	public String getCollateralnumber1() {
		return collateralnumber1;
	}
	public void setCollateralnumber1(String collateralnumber1) {
		this.collateralnumber1 = collateralnumber1;
	}
	public String getLoansbehalfnumber1() {
		return loansbehalfnumber1;
	}
	public void setLoansbehalfnumber1(String loansbehalfnumber1) {
		this.loansbehalfnumber1 = loansbehalfnumber1;
	}
	public String getLoanaccount1() {
		return loanaccount1;
	}
	public void setLoanaccount1(String loanaccount1) {
		this.loanaccount1 = loanaccount1;
	}
	public String getLoansdepartment1() {
		return loansdepartment1;
	}
	public void setLoansdepartment1(String loansdepartment1) {
		this.loansdepartment1 = loansdepartment1;
	}
	public Integer getSerialno2() {
		return serialno2;
	}
	public void setSerialno2(Integer serialno2) {
		this.serialno2 = serialno2;
	}
	public String getMortgageepcode2() {
		return mortgageepcode2;
	}
	public void setMortgageepcode2(String mortgageepcode2) {
		this.mortgageepcode2 = mortgageepcode2;
	}
	public Integer getSequenceno2() {
		return sequenceno2;
	}
	public void setSequenceno2(Integer sequenceno2) {
		this.sequenceno2 = sequenceno2;
	}
	public String getCreditnumber2() {
		return creditnumber2;
	}
	public void setCreditnumber2(String creditnumber2) {
		this.creditnumber2 = creditnumber2;
	}
	public String getCollateralnumber2() {
		return collateralnumber2;
	}
	public void setCollateralnumber2(String collateralnumber2) {
		this.collateralnumber2 = collateralnumber2;
	}
	public String getLoansbehalfnumber2() {
		return loansbehalfnumber2;
	}
	public void setLoansbehalfnumber2(String loansbehalfnumber2) {
		this.loansbehalfnumber2 = loansbehalfnumber2;
	}
	public String getLoanaccount2() {
		return loanaccount2;
	}
	public void setLoanaccount2(String loanaccount2) {
		this.loanaccount2 = loanaccount2;
	}
	public String getLoansdepartment2() {
		return loansdepartment2;
	}
	public void setLoansdepartment2(String loansdepartment2) {
		this.loansdepartment2 = loansdepartment2;
	}
	public Integer getAddressno() {
		return addressno;
	}
	public void setAddressno(Integer addressno) {
		this.addressno = addressno;
	}
	public String getAddresscode() {
		return addresscode;
	}
	public void setAddresscode(String addresscode) {
		this.addresscode = addresscode;
	}
	public String getAddressname() {
		return addressname;
	}
	public void setAddressname(String addressname) {
		this.addressname = addressname;
	}
	public String getAddressdetailinfo() {
		return addressdetailinfo;
	}
	public void setAddressdetailinfo(String addressdetailinfo) {
		this.addressdetailinfo = addressdetailinfo;
	}
	public String getRepeatpolicyno() {
		return repeatpolicyno;
	}
	public void setRepeatpolicyno(String repeatpolicyno) {
		this.repeatpolicyno = repeatpolicyno;
	}
	public String getPossessnaturecode() {
		return possessnaturecode;
	}
	public void setPossessnaturecode(String possessnaturecode) {
		this.possessnaturecode = possessnaturecode;
	}
	public Integer getBuildingno() {
		return buildingno;
	}
	public void setBuildingno(Integer buildingno) {
		this.buildingno = buildingno;
	}
	public Integer getPropAddressno() {
		return propAddressno;
	}
	public void setPropAddressno(Integer propAddressno) {
		this.propAddressno = propAddressno;
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
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getStructureText() {
		return structureText;
	}
	public void setStructureText(String structureText) {
		this.structureText = structureText;
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
	public String getKindcodeF() {
		return kindcodeF;
	}
	public void setKindcodeF(String kindcodeF) {
		this.kindcodeF = kindcodeF;
	}
	public String getKindnameF() {
		return kindnameF;
	}
	public void setKindnameF(String kindnameF) {
		this.kindnameF = kindnameF;
	}
	public String getItemcodeF() {
		return itemcodeF;
	}
	public void setItemcodeF(String itemcodeF) {
		this.itemcodeF = itemcodeF;
	}
	public String getItemnameF() {
		return itemnameF;
	}
	public void setItemnameF(String itemnameF) {
		this.itemnameF = itemnameF;
	}
	public String getItemnatureF() {
		return itemnatureF;
	}
	public void setItemnatureF(String itemnatureF) {
		this.itemnatureF = itemnatureF;
	}
	public Integer getBuildingnoF() {
		return buildingnoF;
	}
	public void setBuildingnoF(Integer buildingnoF) {
		this.buildingnoF = buildingnoF;
	}
	public BigDecimal getHighrisefee() {
		return highrisefee;
	}
	public void setHighrisefee(BigDecimal highrisefee) {
		this.highrisefee = highrisefee;
	}
	public String getAmountF() {
		return amountF;
	}
	public void setAmountF(String amountF) {
		this.amountF = amountF;
	}
	public String getPremiumF() {
		return premiumF;
	}
	public void setPremiumF(String premiumF) {
		this.premiumF = premiumF;
	}
	public BigDecimal getCommrateF() {
		return commrateF;
	}
	public void setCommrateF(BigDecimal commrateF) {
		this.commrateF = commrateF;
	}
	public String getKindcodeQ() {
		return kindcodeQ;
	}
	public void setKindcodeQ(String kindcodeQ) {
		this.kindcodeQ = kindcodeQ;
	}
	public String getKindnameQ() {
		return kindnameQ;
	}
	public void setKindnameQ(String kindnameQ) {
		this.kindnameQ = kindnameQ;
	}
	public String getItemcodeQ() {
		return itemcodeQ;
	}
	public void setItemcodeQ(String itemcodeQ) {
		this.itemcodeQ = itemcodeQ;
	}
	public String getItemnameQ() {
		return itemnameQ;
	}
	public void setItemnameQ(String itemnameQ) {
		this.itemnameQ = itemnameQ;
	}
	public String getItemnatureQ() {
		return itemnatureQ;
	}
	public void setItemnatureQ(String itemnatureQ) {
		this.itemnatureQ = itemnatureQ;
	}
	public Integer getBuildingnoQ() {
		return buildingnoQ;
	}
	public void setBuildingnoQ(Integer buildingnoQ) {
		this.buildingnoQ = buildingnoQ;
	}
	public String getAmountQ() {
		return amountQ;
	}
	public void setAmountQ(String amountQ) {
		this.amountQ = amountQ;
	}
	public String getPremiumQ() {
		return premiumQ;
	}
	public void setPremiumQ(String premiumQ) {
		this.premiumQ = premiumQ;
	}
	public BigDecimal getCommrateQ() {
		return commrateQ;
	}
	public void setCommrateQ(BigDecimal commrateQ) {
		this.commrateQ = commrateQ;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Long getAmountFLast() {
		return amountFLast;
	}
	public void setAmountFLast(Long amountFLast) {
		this.amountFLast = amountFLast;
	}
	public Long getAmountQLast() {
		return amountQLast;
	}
	public void setAmountQLast(Long amountQLast) {
		this.amountQLast = amountQLast;
	}
	public Long getPremiumFLast() {
		return premiumFLast;
	}
	public void setPremiumFLast(Long premiumFLast) {
		this.premiumFLast = premiumFLast;
	}
	public Long getPremiumQLast() {
		return premiumQLast;
	}
	public void setPremiumQLast(Long premiumQLast) {
		this.premiumQLast = premiumQLast;
	}
	public String getAmountFAgt() {
		return amountFAgt;
	}
	public void setAmountFAgt(String amountFAgt) {
		this.amountFAgt = amountFAgt;
	}
	public String getAmountQAgt() {
		return amountQAgt;
	}
	public void setAmountQAgt(String amountQAgt) {
		this.amountQAgt = amountQAgt;
	}
	public String getPremiumFAgt() {
		return premiumFAgt;
	}
	public void setPremiumFAgt(String premiumFAgt) {
		this.premiumFAgt = premiumFAgt;
	}
	public String getPremiumQAgt() {
		return premiumQAgt;
	}
	public void setPremiumQAgt(String premiumQAgt) {
		this.premiumQAgt = premiumQAgt;
	}
	public String getDquakeStatus() {
		return dquakeStatus;
	}
	public void setDquakeStatus(String dquakeStatus) {
		this.dquakeStatus = dquakeStatus;
	}
	public String getPrpinsBatchStatus() {
		return prpinsBatchStatus;
	}
	public void setPrpinsBatchStatus(String prpinsBatchStatus) {
		this.prpinsBatchStatus = prpinsBatchStatus;
	}
	public Date getTransPpsTime() {
		return transPpsTime;
	}
	public void setTransPpsTime(Date transPpsTime) {
		this.transPpsTime = transPpsTime;
	}
	public String getFixUser() {
		return fixUser;
	}
	public void setFixUser(String fixUser) {
		this.fixUser = fixUser;
	}
	public Date getFixDate() {
		return fixDate;
	}
	public void setFixDate(Date fixDate) {
		this.fixDate = fixDate;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getCheckErrMsg() {
		return checkErrMsg;
	}
	public void setCheckErrMsg(String checkErrMsg) {
		this.checkErrMsg = checkErrMsg;
	}
	public String getCheckWarnMsg() {
		return checkWarnMsg;
	}
	public void setCheckWarnMsg(String checkWarnMsg) {
		this.checkWarnMsg = checkWarnMsg;
	}
	public Long getOidFirPremcalcTmp() {
		return oidFirPremcalcTmp;
	}
	public void setOidFirPremcalcTmp(Long oidFirPremcalcTmp) {
		this.oidFirPremcalcTmp = oidFirPremcalcTmp;
	}
	public String getQamtStatus() {
		return qamtStatus;
	}
	public void setQamtStatus(String qamtStatus) {
		this.qamtStatus = qamtStatus;
	}
	public String getFamtStatus() {
		return famtStatus;
	}
	public void setFamtStatus(String famtStatus) {
		this.famtStatus = famtStatus;
	}
	public String getAddrDetail() {
		return addrDetail;
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public String getAddrStatus() {
		return addrStatus;
	}
	public void setAddrStatus(String addrStatus) {
		this.addrStatus = addrStatus;
	}
	public Long getWsFirAmt() {
		return wsFirAmt;
	}
	public void setWsFirAmt(Long wsFirAmt) {
		this.wsFirAmt = wsFirAmt;
	}
	public Integer getWsQuakeAmt() {
		return wsQuakeAmt;
	}
	public void setWsQuakeAmt(Integer wsQuakeAmt) {
		this.wsQuakeAmt = wsQuakeAmt;
	}
	public Long getOidFirPremcalcTmp2() {
		return oidFirPremcalcTmp2;
	}
	public void setOidFirPremcalcTmp2(Long oidFirPremcalcTmp2) {
		this.oidFirPremcalcTmp2 = oidFirPremcalcTmp2;
	}
	public String getDquakeNo() {
		return dquakeNo;
	}
	public void setDquakeNo(String dquakeNo) {
		this.dquakeNo = dquakeNo;
	}
	public String getInsuredflag1() {
		return insuredflag1;
	}
	public void setInsuredflag1(String insuredflag1) {
		this.insuredflag1 = insuredflag1;
	}
	public Integer getInsuredSeq1() {
		return insuredSeq1;
	}
	public void setInsuredSeq1(Integer insuredSeq1) {
		this.insuredSeq1 = insuredSeq1;
	}
	public String getInsurednature1() {
		return insurednature1;
	}
	public void setInsurednature1(String insurednature1) {
		this.insurednature1 = insurednature1;
	}
	public String getInsuredname1() {
		return insuredname1;
	}
	public void setInsuredname1(String insuredname1) {
		this.insuredname1 = insuredname1;
	}
	public String getIdentifytype1() {
		return identifytype1;
	}
	public void setIdentifytype1(String identifytype1) {
		this.identifytype1 = identifytype1;
	}
	public String getIdentifynumber1() {
		return identifynumber1;
	}
	public void setIdentifynumber1(String identifynumber1) {
		this.identifynumber1 = identifynumber1;
	}
	public String getPhonenumber1() {
		return phonenumber1;
	}
	public void setPhonenumber1(String phonenumber1) {
		this.phonenumber1 = phonenumber1;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getPostcode1() {
		return postcode1;
	}
	public void setPostcode1(String postcode1) {
		this.postcode1 = postcode1;
	}
	public String getPostaddress1() {
		return postaddress1;
	}
	public void setPostaddress1(String postaddress1) {
		this.postaddress1 = postaddress1;
	}
	public String getIshighdengeroccupation1() {
		return ishighdengeroccupation1;
	}
	public void setIshighdengeroccupation1(String ishighdengeroccupation1) {
		this.ishighdengeroccupation1 = ishighdengeroccupation1;
	}
	public String getDomicile1() {
		return domicile1;
	}
	public void setDomicile1(String domicile1) {
		this.domicile1 = domicile1;
	}
	public String getCountryename1() {
		return countryename1;
	}
	public void setCountryename1(String countryename1) {
		this.countryename1 = countryename1;
	}
	public String getBirthday1() {
		return birthday1;
	}
	public void setBirthday1(String birthday1) {
		this.birthday1 = birthday1;
	}
	public String getBirthday1Check() throws Exception {
		return birthday1Check;
	}
	public void setBirthday1Check(String birthday1Check) {
		this.birthday1Check = birthday1Check;
	}
	public String getListedcabinetcompany1() {
		return listedcabinetcompany1;
	}
	public void setListedcabinetcompany1(String listedcabinetcompany1) {
		this.listedcabinetcompany1 = listedcabinetcompany1;
	}
	public String getHeadname1() {
		return headname1;
	}
	public void setHeadname1(String headname1) {
		this.headname1 = headname1;
	}
	
	public String getInsuredflag2() {
		return insuredflag2;
	}
	public void setInsuredflag2(String insuredflag2) {
		this.insuredflag2 = insuredflag2;
	}
	public Integer getInsuredSeq2() {
		return insuredSeq2;
	}
	public void setInsuredSeq2(Integer insuredSeq2) {
		this.insuredSeq2 = insuredSeq2;
	}
	public String getInsurednature2() {
		return insurednature2;
	}
	public void setInsurednature2(String insurednature2) {
		this.insurednature2 = insurednature2;
	}
	public String getInsuredname2() {
		return insuredname2;
	}
	public void setInsuredname2(String insuredname2) {
		this.insuredname2 = insuredname2;
	}
	public String getIdentifytype2() {
		return identifytype2;
	}
	public void setIdentifytype2(String identifytype2) {
		this.identifytype2 = identifytype2;
	}
	public String getIdentifynumber2() {
		return identifynumber2;
	}
	public void setIdentifynumber2(String identifynumber2) {
		this.identifynumber2 = identifynumber2;
	}
	public String getPhonenumber2() {
		return phonenumber2;
	}
	public void setPhonenumber2(String phonenumber2) {
		this.phonenumber2 = phonenumber2;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getPostcode2() {
		return postcode2;
	}
	public void setPostcode2(String postcode2) {
		this.postcode2 = postcode2;
	}
	public String getPostaddress2() {
		return postaddress2;
	}
	public void setPostaddress2(String postaddress2) {
		this.postaddress2 = postaddress2;
	}
	public String getIshighdengeroccupation2() {
		return ishighdengeroccupation2;
	}
	public void setIshighdengeroccupation2(String ishighdengeroccupation2) {
		this.ishighdengeroccupation2 = ishighdengeroccupation2;
	}
	public String getDomicile2() {
		return domicile2;
	}
	public void setDomicile2(String domicile2) {
		this.domicile2 = domicile2;
	}
	public String getCountryename2() {
		return countryename2;
	}
	public void setCountryename2(String countryename2) {
		this.countryename2 = countryename2;
	}
	public String getBirthday2() {
		return birthday2;
	}
	public void setBirthday2(String birthday2) {
		this.birthday2 = birthday2;
	}
	public String getBirthday2Check() throws Exception {
		return birthday2Check;
	}
	public void setBirthday2Check(String birthday2Check) {
		this.birthday2Check = birthday2Check;
	}
	public String getListedcabinetcompany2() {
		return listedcabinetcompany2;
	}
	public void setListedcabinetcompany2(String listedcabinetcompany2) {
		this.listedcabinetcompany2 = listedcabinetcompany2;
	}
	public String getHeadname2() {
		return headname2;
	}
	public void setHeadname2(String headname2) {
		this.headname2 = headname2;
	}
	public String getLocking() {
		return locking;
	}
	public void setLocking(String locking) {
		this.locking = locking;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPostname1() {
		return postname1;
	}
	public void setPostname1(String postname1) {
		this.postname1 = postname1;
	}
	public String getPostname2() {
		return postname2;
	}
	public void setPostname2(String postname2) {
		this.postname2 = postname2;
	}
	public String getProposalno() {
		return proposalno;
	}
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	public String getIsupdate() {
		return isupdate;
	}
	public void setIsupdate(String isupdate) {
		this.isupdate = isupdate;
	}
	public String getSfFlag() {
		return sfFlag;
	}
	public void setSfFlag(String sfFlag) {
		this.sfFlag = sfFlag;
	}
	public String getSfReason() {
		return sfReason;
	}
	public void setSfReason(String sfReason) {
		this.sfReason = sfReason;
	}
	public String getSfUser() {
		return sfUser;
	}
	public void setSfUser(String sfUser) {
		this.sfUser = sfUser;
	}
	public Date getSfDate() {
		return sfDate;
	}
	public void setSfDate(Date sfDate) {
		this.sfDate = sfDate;
	}
	public String getTemp8() {
		return temp8;
	}
	public void setTemp8(String temp8) {
		this.temp8 = temp8;
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
}
