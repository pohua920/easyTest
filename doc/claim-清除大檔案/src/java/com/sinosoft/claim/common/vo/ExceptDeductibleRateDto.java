package com.sinosoft.claim.common.vo;

import java.util.Map;

public class ExceptDeductibleRateDto {

	/** 险别代码 */
	private String kindCode = "";

	/** 险别名称 */
	private String kindName = "";

	/** 不计免赔率 */
	Double exceptDeductibleRate = null;

	/** 不计免赔金额 */
	double exceptDeductibleRatePay = 0.0;

	/** 绝对免赔率 */
	double deductibleRate = 0.0;

	/** 责任免除後的绝对免赔率 */
	double afterDeductibleRate = 0.0;

	/** 按险别事故责任免赔责任免除後的不计免赔率 */
	Map<?, ?> afterDutyDeductibleRateMap = null;

	/** 按险别绝对免赔责任免除後的不计免赔率 */
	Map<?, ?> afterDeductibleRateMap = null;

	public double getAfterDeductibleRate() {
		return afterDeductibleRate;
	}

	public void setAfterDeductibleRate(double afterDeductibleRate) {
		this.afterDeductibleRate = afterDeductibleRate;
	}

	public double getDeductibleRate() {
		return deductibleRate;
	}

	public void setDeductibleRate(double deductibleRate) {
		this.deductibleRate = deductibleRate;
	}

	public Double getExceptDeductibleRate() {
		return exceptDeductibleRate;
	}

	public void setExceptDeductibleRate(Double exceptDeductibleRate) {
		if (exceptDeductibleRate == null) {
			exceptDeductibleRate = new Double(0.0);
		}
		this.exceptDeductibleRate = exceptDeductibleRate;
	}

	public double getExceptDeductibleRatePay() {
		return exceptDeductibleRatePay;
	}

	public void setExceptDeductibleRatePay(double exceptDeductibleRatePay) {
		this.exceptDeductibleRatePay = exceptDeductibleRatePay;
	}

	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public Map<?, ?> getAfterDeductibleRateMap() {
		return afterDeductibleRateMap;
	}

	public void setAfterDeductibleRateMap(Map<?, ?> afterDeductibleRateMap) {
		this.afterDeductibleRateMap = afterDeductibleRateMap;
	}

	public Map<?, ?> getAfterDutyDeductibleRateMap() {
		return afterDutyDeductibleRateMap;
	}

	public void setAfterDutyDeductibleRateMap(Map<?, ?> afterDutyDeductibleRateMap) {
		this.afterDutyDeductibleRateMap = afterDutyDeductibleRateMap;
	}

}
