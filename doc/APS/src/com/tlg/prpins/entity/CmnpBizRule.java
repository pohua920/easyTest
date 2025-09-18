package com.tlg.prpins.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;
import com.tlg.util.StringUtil;

public class CmnpBizRule extends IBatisBaseEntity<BigDecimal> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal oid;
	/**
	 * 險別
	 */
    private String riskcode;
	/**
	 * 承保規則
	 */
    private String ruleId;
	/**
	 * 承保規則序號
	 */
    private String ruleSeq;
	/**
	 * 作業類型
	 * 
	 * 作業類別 P承保 E批改
	 */
    private String funcType;
//	/**
//	 * 保批單別
//	 * 
//	 * 理賠 保單 批單 估價
//	 */
//    private String fpe;
	/**
	 * 使用註記 Y使用 N停用
	 */
    private String fused;

    private String ruleMsg;
//	/**
//	 * 審核層級1
//	 */
//    private BigDecimal iaction1;
//	/**
//	 * 審核層級2
//	 */
//    private BigDecimal iaction2;
//	/**
//	 * 審核層級3
//	 */
//    private BigDecimal iaction3;
	/**
	 * 設定值1
	 */
    private String qset1;
	/**
	 * 設定值2
	 */
    private String qset2;
	/**
	 * 設定值3
	 */
    private String qset3;
//	/**
//	 * 字串1
//	 */
//    private String nstr1;
//	/**
//	 * 字串2
//	 */
//    private String nstr2;
	/**
	 * (對應至ResultDetailMessage.status及ResultObject.status)
	 * 檢核方式 0提示 1禁止
	 */
    private String checkType;
    
    private String icreate;

    private Date dcreate;

    private String iupdate;

    private Date dupdate;

	public BigDecimal getOid() {
		return oid;
	}

	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}



	public String getFused() {
		return fused;
	}

	public void setFused(String fused) {
		this.fused = fused;
	}

	public String getRuleMsg() throws Exception {
		
		this.ruleMsg=this.ruleMsg.replaceAll("\\{0\\}", StringUtil.nullToSpace("" + this.qset1));
		this.ruleMsg=this.ruleMsg.replaceAll("\\{1\\}", StringUtil.nullToSpace("" + this.qset2));	
		this.ruleMsg=this.ruleMsg.replaceAll("\\{2\\}", StringUtil.nullToSpace("" + this.qset3));	
		
		return ruleMsg;
	}

	public void setRuleMsg(String ruleMsg) {
		this.ruleMsg = ruleMsg;
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



	public String getQset1() {
		return qset1;
	}

	public void setQset1(String qset1) {
		this.qset1 = qset1;
	}

	public String getQset2() {
		return qset2;
	}

	public void setQset2(String qset2) {
		this.qset2 = qset2;
	}

	public String getQset3() {
		return qset3;
	}

	public void setQset3(String qset3) {
		this.qset3 = qset3;
	}

	public String getRiskcode() {
		return riskcode;
	}

	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleSeq() {
		return ruleSeq;
	}

	public void setRuleSeq(String ruleSeq) {
		this.ruleSeq = ruleSeq;
	}

	public String getFuncType() {
		return funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}



    
}