package com.tlg.prpins.entity;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.tlg.iBatis.IBatisBaseEntity;

@XmlRootElement
public class PbPremcalcCklist extends IBatisBaseEntity<BigDecimal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal oid;
	private BigDecimal oidFirPremcalcTmp;
	private String groupNo;
	private String gitemNo;
	private String cklistNo;
	private String cklistResult;
	private BigDecimal cklistScore;
	private BigDecimal cklistY;
	private BigDecimal cklistN;
	private BigDecimal groupLimitUpper;
	private BigDecimal groupLimitLower;
	private BigDecimal maxLimit;

	public BigDecimal getOid() {
		return oid;
	}

	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}

	public BigDecimal getOidFirPremcalcTmp() {
		return oidFirPremcalcTmp;
	}

	public void setOidFirPremcalcTmp(BigDecimal oidFirPremcalcTmp) {
		this.oidFirPremcalcTmp = oidFirPremcalcTmp;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGitemNo() {
		return gitemNo;
	}

	public void setGitemNo(String gitemNo) {
		this.gitemNo = gitemNo;
	}

	public String getCklistNo() {
		return cklistNo;
	}

	public void setCklistNo(String cklistNo) {
		this.cklistNo = cklistNo;
	}

	public String getCklistResult() {
		return cklistResult;
	}

	public void setCklistResult(String cklistResult) {
		this.cklistResult = cklistResult;
	}

	public BigDecimal getCklistScore() {
		return cklistScore;
	}

	public void setCklistScore(BigDecimal cklistScore) {
		this.cklistScore = cklistScore;
	}

	public BigDecimal getCklistY() {
		return cklistY;
	}

	public void setCklistY(BigDecimal cklistY) {
		this.cklistY = cklistY;
	}

	public BigDecimal getCklistN() {
		return cklistN;
	}

	public void setCklistN(BigDecimal cklistN) {
		this.cklistN = cklistN;
	}

	public BigDecimal getGroupLimitUpper() {
		return groupLimitUpper;
	}

	public void setGroupLimitUpper(BigDecimal groupLimitUpper) {
		this.groupLimitUpper = groupLimitUpper;
	}

	public BigDecimal getGroupLimitLower() {
		return groupLimitLower;
	}

	public void setGroupLimitLower(BigDecimal groupLimitLower) {
		this.groupLimitLower = groupLimitLower;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

}