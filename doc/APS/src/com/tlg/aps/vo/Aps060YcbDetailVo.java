package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.tlg.prpins.entity.FirAgtTocoreInsured;

/** mantis：FIR0668_0217，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
public class Aps060YcbDetailVo {
	
	private String batchNo;
	private Integer batchSeq;
	private String dataStatus;
	private String oldpolicyno;
	private String orderseq;
	private String handler1code;
	private String comcode;
	private String prpinsBatchStatus;
	private String checkErrMsg;
	private String checkWarnMsg;
	
	private String dquakeStatus;
	private Date transPpsTime;
	private String icreate;
	private Date dcreate;
	private String iupdate;
	private Date dupdate;
	private String fixUser;
	private Date fixDate;
	private String sfFlag;
	private String sfReason;
	private String sfUser;
	private Date sfDate;
	private String prpinsBatchNo;
	private String proposalno;
	
	private BigDecimal oid;
	private String isNew;
	private String printvirtualcode;
	private String policyno;
	private String rationcode;
	private String handleridentifynumber;
	private String agentcode;
	private String extracomcode;
	private String extracomname;
	private String businessnature;
	private String channeltype;
	private String projectcode;
	private String introducerid;
	private Date startdate;
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
	private String epolicy;
	private String clauseSendtype;
	private String temp1;
	private String temp2;
	private String temp3;
	private String temp4;
	private String temp5;
	private String temp6;
	private String temp7;
	private Long amountFLast;
	private Long amountQLast;
	private Long premiumFLast;
	private Long premiumQLast;
	private String amountFAgt;
	private String amountQAgt;
	private String premiumFAgt;
	private String premiumQAgt;
	
	private int dataqtyT;
	private int dataqtyS;
	private int dataqtyF;
	private String fileStatus; 
	private String transStatus; 
	private String dataqtySf; 
	private String dataqtyFix; 
	private String rnYyyymm;
	private String isupdate;
	private String wallname;
	private String roofname;
	private String nameA;
	private String nameI;
	//mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 第二階段
	private String accountno;
	/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start */
	//判斷是否鎖定 Y為鎖定 N為未鎖定
	private String locking;
	/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end */
	
	
	private List<FirAgtTocoreInsured> insuredList;

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

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getOldpolicyno() {
		return oldpolicyno;
	}

	public void setOldpolicyno(String oldpolicyno) {
		this.oldpolicyno = oldpolicyno;
	}

	public String getOrderseq() {
		return orderseq;
	}

	public void setOrderseq(String orderseq) {
		this.orderseq = orderseq;
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

	public String getPrpinsBatchStatus() {
		return prpinsBatchStatus;
	}

	public void setPrpinsBatchStatus(String prpinsBatchStatus) {
		this.prpinsBatchStatus = prpinsBatchStatus;
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

	public String getDquakeStatus() {
		return dquakeStatus;
	}

	public void setDquakeStatus(String dquakeStatus) {
		this.dquakeStatus = dquakeStatus;
	}

	public Date getTransPpsTime() {
		return transPpsTime;
	}

	public void setTransPpsTime(Date transPpsTime) {
		this.transPpsTime = transPpsTime;
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

	public String getPrpinsBatchNo() {
		return prpinsBatchNo;
	}

	public void setPrpinsBatchNo(String prpinsBatchNo) {
		this.prpinsBatchNo = prpinsBatchNo;
	}

	public String getProposalno() {
		return proposalno;
	}

	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}

	public BigDecimal getOid() {
		return oid;
	}

	public void setOid(BigDecimal oid) {
		this.oid = oid;
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

	public String getRationcode() {
		return rationcode;
	}

	public void setRationcode(String rationcode) {
		this.rationcode = rationcode;
	}

	public String getHandleridentifynumber() {
		return handleridentifynumber;
	}

	public void setHandleridentifynumber(String handleridentifynumber) {
		this.handleridentifynumber = handleridentifynumber;
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

	public String getStartdate() {
//		return startdate;
		return new SimpleDateFormat("yyyy/MM/dd").format(startdate);
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
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

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getTemp3() {
		return temp3;
	}

	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}

	public String getTemp4() {
		return temp4;
	}

	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}

	public String getTemp5() {
		return temp5;
	}

	public void setTemp5(String temp5) {
		this.temp5 = temp5;
	}

	public String getTemp6() {
		return temp6;
	}

	public void setTemp6(String temp6) {
		this.temp6 = temp6;
	}

	public String getTemp7() {
		return temp7;
	}

	public void setTemp7(String temp7) {
		this.temp7 = temp7;
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

	public List<FirAgtTocoreInsured> getInsuredList() {
		return insuredList;
	}

	public void setInsuredList(List<FirAgtTocoreInsured> insuredList) {
		this.insuredList = insuredList;
	}

	public int getDataqtyT() {
		return dataqtyT;
	}

	public void setDataqtyT(int dataqtyT) {
		this.dataqtyT = dataqtyT;
	}

	public int getDataqtyS() {
		return dataqtyS;
	}

	public void setDataqtyS(int dataqtyS) {
		this.dataqtyS = dataqtyS;
	}

	public int getDataqtyF() {
		return dataqtyF;
	}

	public void setDataqtyF(int dataqtyF) {
		this.dataqtyF = dataqtyF;
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

	public String getDataqtySf() {
		return dataqtySf;
	}

	public void setDataqtySf(String dataqtySf) {
		this.dataqtySf = dataqtySf;
	}

	public String getDataqtyFix() {
		return dataqtyFix;
	}

	public void setDataqtyFix(String dataqtyFix) {
		this.dataqtyFix = dataqtyFix;
	}

	public String getRnYyyymm() {
		return rnYyyymm;
	}

	public void setRnYyyymm(String rnYyyymm) {
		this.rnYyyymm = rnYyyymm;
	}

	public String getIsupdate() {
		return isupdate;
	}

	public void setIsupdate(String isupdate) {
		this.isupdate = isupdate;
	}

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

	public void setNameA(String nameA) {
		this.nameA = nameA;
	}

	public String getNameI() {
		return nameI;
	}

	public void setNameI(String nameI) {
		this.nameI = nameI;
	}
	
	/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 第二階段 start*/
	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	/*mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 第二階段 end*/

	public String getNameA() {
		return nameA;
	}

	/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 start */
	public String getLocking() {
		return locking;
	}

	public void setLocking(String locking) {
		this.locking = locking;
	}
	/*mantis：FIR0668，處理人員：BJ085，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 end */
}
