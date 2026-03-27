package com.sinosoft.undwrt.undwrtRule.service;

import java.util.HashMap;
import java.util.Map;

import com.sinosoft.one.rule.domain.InputBOM;
import com.sinosoft.undwrt.pub.InternationalizationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 规则引擎的条件对象.
 */
public class UndwrtRuleCondition implements InputBOM {

	/** 屬性日志讯息. */
	private Logger logger = LoggerFactory.getLogger(UndwrtRuleCondition.class);

	/** 屬性車齡. */
	private String carAge;

	/** 屬性車種. */
	private String carType;

	/** 屬性車輛使用性質. */
	private String carNature;

	/** 屬性審核結果. */
	private boolean result = true;

	/** 屬性不能審核通過的原因. */
	private String strResultMessage;

	/** 屬性規則校驗結果. */
	private boolean isRule = false;

	/** 新增设备. */
	private double carDevice;

	/** 屬性險別保額存儲訊息. */
	private Map<String, UndwrtRuleRiskKind> riskKind = new HashMap<String, UndwrtRuleRiskKind>();

	/** 屬性限額訊息. */
	private double limitAmount;

	/**
	 * 將險別保額訊息按照險別代碼放入Map.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @param undwrtRiskKind
	 *            險別保額訊息
	 */
	public void addRiskKind(String kindCode, UndwrtRuleRiskKind undwrtRiskKind) {
		riskKind.put(kindCode, undwrtRiskKind);
	}

	/**
	 * 獲取屬性險別保額存儲訊息.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 屬性險別保額存儲訊息的值
	 */
	public UndwrtRuleRiskKind getRiskKind(String kindCode) {
		return riskKind.get(kindCode);
	}

	/**
	 * 檢查是否包含此險別.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 檢查結果
	 */
	public boolean containsRiskKind(String kindCode) {
		this.isRule = true;// true表示已经进行了规则校验
		boolean flag = getRiskKind(kindCode) != null;
		logger.info("kindCode is {},flag is {}", kindCode, flag);
		return flag;
	}

	/**
	 * 獲取屬性險別的保額.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 屬性險別保額的值
	 */
	public double getKindAmount(String kindCode) {
		double amount = this.getRiskKind(kindCode).getAmount();
		logger.info("kindCode is {},amount is {}", kindCode, amount);
		return amount;
	}

	/**
	 * 匹配車齡.
	 * 
	 * @param carAge
	 *            車齡
	 * @return 匹配結果 true 匹配成功 false 匹配失敗
	 */
	public boolean matchAge(String carAge) {
		InternationalizationUtil internal = new InternationalizationUtil();
		boolean hasCarAge = false;
		System.out.println(internal.getText("undwrt.service.task.business")+"==" + this.carAge);
		System.out.println(internal.getText("undwrt.service.task.configuration")+"==" + carAge);
		if (Integer.parseInt(this.carAge) > Integer.parseInt(carAge)) {
			hasCarAge = true;
		}
		return hasCarAge;
	}

	/**
	 * 设置審核結果返回结果.
	 */
	public void resultIsTrue() {
		this.result = true;
	}

	/**
	 * 设置審核結果返回结果.
	 * 
	 * @param strMessage
	 *            審核結果訊息
	 */
	public void resultIsFalse(String strMessage) {
		this.strResultMessage = strMessage;
		this.result = false;
	}

	/**
	 * 返回结果.
	 * 
	 * @return 屬性審核結果的值
	 */
	public boolean getResult() {
		return result;
	}

	/**
	 * 判斷是否包含此險別.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 判斷結果 true包含 false 不包含
	 */
	public boolean hasKind(String kindCode) {
		return this.riskKind.containsKey(kindCode);
	}

	/**
	 * 獲取車種.
	 * 
	 * @return 屬性車種的值
	 */
	public String getCarType() {
		return carType;
	}

	/**
	 * 設置屬性車種.
	 * 
	 * @param carType
	 *            待設置的車種的值
	 */
	public void setCarType(String carType) {
		this.carType = carType;
	}

	/**
	 * 獲取使用性質.
	 * 
	 * @return 屬性車輛使用性質的值
	 */
	public String getCarNature() {
		return carNature;
	}

	/**
	 * 設置屬性車輛使用性質.
	 * 
	 * @param carNature
	 *            待設置的車輛使用性質的值
	 */
	public void setCarNature(String carNature) {
		this.carNature = carNature;
	}

	/**
	 * 獲取新增設備.
	 * 
	 * @return 新增設備的值
	 */
	public double getCarDevice() {
		return carDevice;
	}

	/**
	 * 設置新增設備.
	 * 
	 * @param carDevice
	 *            待設置的新增設備值
	 */
	public void setCarDevice(double carDevice) {
		this.carDevice = carDevice;
	}

	/**
	 * 獲取屬性不能審核通過的原因.
	 * 
	 * @return 屬性不能審核通過的原因的值
	 */
	public String getStrResultMessage() {
		return strResultMessage;
	}

	/**
	 * 設置屬性不能審核通過的原因.
	 * 
	 * @param strResultMessage
	 *            待設置的不能審核通過的原因的值
	 */
	public void setStrResultMessage(String strResultMessage) {
		this.strResultMessage = strResultMessage;
	}

	/**
	 * 獲取屬性限額訊息.
	 * 
	 * @return 屬性限額訊息的值
	 */
	public double getLimitAmount() {
		return limitAmount;
	}

	/**
	 * 設置屬性限額訊息.
	 * 
	 * @param limitAmount
	 *            待設置的限額訊息的值
	 */
	public void setLimitAmount(double limitAmount) {
		this.limitAmount = limitAmount;
	}

	/**
	 * 獲取屬性車齡.
	 * 
	 * @return 屬性車齡的值
	 */
	public String getCarAge() {
		return carAge;
	}

	/**
	 * 設置屬性車齡.
	 * 
	 * @param carAge
	 *            待設置的車齡的值
	 */
	public void setCarAge(String carAge) {
		this.carAge = carAge;
	}

	/**
	 * 檢查是否進行了規則校驗.
	 * 
	 * @return 規則校驗結果
	 */
	public boolean isRule() {
		return isRule;
	}

	/**
	 * 設置屬性規則校驗結果.
	 * 
	 * @param isRule
	 *            待設置的規則校驗結果的值
	 */
	public void setRule(boolean isRule) {
		this.isRule = isRule;
	}
}
