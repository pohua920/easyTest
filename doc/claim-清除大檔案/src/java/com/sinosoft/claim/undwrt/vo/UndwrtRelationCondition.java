package com.sinosoft.claim.undwrt.vo;

import java.io.Serializable;

import com.sinosoft.one.rule.domain.InputBOM;

public class UndwrtRelationCondition implements InputBOM, Serializable{
	private static final long serialVersionUID = 1L;
	private String kindCode;
	/**所有计算书核赔金额之和*/
	private double sumSumPaid = 0;
	private boolean result = false;
	public String getKindCode() {
		return kindCode;
	}
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	public double getSumSumPaid() {
		return sumSumPaid;
	}
	public void setSumSumPaid(double sumSumPaid) {
		this.sumSumPaid = sumSumPaid;
	}
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
}
