package com.sinosoft.claim.check.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLacciCheck;
import com.sinosoft.claim.schema.model.PrpLacciCheckCharge;
import com.sinosoft.claim.schema.model.PrpLacciCheckText;

/**
 * 自定义意健险调查数据传输对象
 * <p>
 * Title: 意健险调查DTO
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AcciCheckDto implements Serializable {
	/** 赔款费用信息 */
	private List<PrpLcharge> prpLchargeList;

	/** 意健险调查主信息 */
	private PrpLacciCheck prpLacciCheck;
	/** 意键险调查文本信息 */
	private PrpLacciCheckText prpLacciCheckText;
	/** 意键险调查文本信息（多行） */
	private List<PrpLacciCheckText> prpLacciCheckTextList;
	/** 设置报案信息 */
	private PrpLregist prpLregist;

	/**
	 * 意健险时的调查费用列表
	 */
	private List<PrpLacciCheckCharge> prpLacciCheckChargeList = new ArrayList<PrpLacciCheckCharge>();

	/**
	 * 设置报案信息
	 * @param 设置报案信息
	 */
	public void setPrpLregist(PrpLregist prpLregist) {
		this.prpLregist = prpLregist;
	}

	/**
	 * 获得报案信息
	 * @return 获得报案信息
	 */
	public PrpLregist getPrpLregist() {
		return this.prpLregist;
	}

	/**
	 * 设置意健险调查主信息
	 * @param prpLacciCheck 意健险调查主信息
	 */
	public void setPrpLacciCheck(PrpLacciCheck prpLacciCheck) {
		this.prpLacciCheck = prpLacciCheck;
	}

	/**
	 * 获得意健险调查主信息
	 * @return 意健险调查主信息
	 */
	public PrpLacciCheck getPrpLacciCheck() {
		return prpLacciCheck;
	}

	/**
	 * 设置意键险调查文本信息（多行）
	 * @param prpLacciCheckText
	 */
	public void setPrpLacciCheckTextList(List<PrpLacciCheckText> prpLacciCheckTextList) {
		this.prpLacciCheckTextList = prpLacciCheckTextList;
	}

	/**
	 * 获得意键险调查文本信息（多行）
	 * @return 意键险调查文本信息
	 */
	public List<PrpLacciCheckText> getPrpLacciCheckTextList() {
		return this.prpLacciCheckTextList;
	}

	/**
	 * 设置意键险调查文本信息
	 * @param prpLacciCheckText
	 */
	public void setPrpLacciCheckText(PrpLacciCheckText prpLacciChecktext) {
		this.prpLacciCheckText = prpLacciChecktext;
	}

	/**
	 * 获得意键险调查文本信息
	 * @return 意键险调查文本信息
	 */
	public PrpLacciCheckText getPrpLacciCheckText() {
		return prpLacciCheckText;
	}

	/**
	 * 设置意健险调查费用列表
	 */

	public void setPrpLacciCheckChargeList(List<PrpLacciCheckCharge> prpLacciCheckChargeList) {
		this.prpLacciCheckChargeList = prpLacciCheckChargeList;
	}

	/**
	 * 获得意健险调查费用列表
	 * @return prpLacciCheckChargeList 意健险调查费用列表
	 */
	public List<PrpLacciCheckCharge> getPrpLacciCheckChargeList() {
		return prpLacciCheckChargeList;
	}

	public List<PrpLcharge> getPrpLchargeList() {
		return prpLchargeList;
	}

	public void setPrpLchargeList(List<PrpLcharge> prpLchargeList) {
		this.prpLchargeList = prpLchargeList;
	}

}
