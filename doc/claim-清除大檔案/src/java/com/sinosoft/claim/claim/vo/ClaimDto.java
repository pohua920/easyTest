package com.sinosoft.claim.claim.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.dto.domain.PrpCcoinsDto;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpLacciPerson;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimCredit;
import com.sinosoft.claim.schema.model.PrpLclaimFee;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLdoc;
import com.sinosoft.claim.schema.model.PrpLdriver;
import com.sinosoft.claim.schema.model.PrpLext;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLpersonTrace;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLquickCase;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLthirdCarLoss;
import com.sinosoft.claim.schema.model.PrpLthirdParty;
import com.sinosoft.claim.schema.model.PrpLthirdProp;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;

/**
 * 自定义立案数据传输对象
 * <p>
 * Title: 车险理赔立案DTO
 * </p>
 * <p>
 * Description: 车险理赔立案样本程序
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

public class ClaimDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5282024537241195361L;
	/** 立案主信息 */
	private PrpLclaim prpLclaim;
	// （特殊赔案：垫支付）
	private List<PrpLprepay> prpLprepayList;
	/** 特别约定信息 */
	private List<PrpCengage> prpCengageList;

	/** 三者车辆信息 */
	private List<PrpLthirdParty> prpLthirdPartyList;

	/** 驾驶员信息 */
	private List<PrpLdriver> prpLdriverList;

	/** 索赔人信息 */
	private List<PrpLacciPerson> prpLacciPersonList;

	/** 险别估损金额 */
	private List<PrpLclaimLoss> prpLclaimLossList;

	/** 估损金额 */
	private List<PrpLclaimFee> prpLclaimFeeList;

	/** 单证信息 */
	private List<PrpLdoc> prpLdocList;

	/** 文本信息 */
	private List<PrpLltext> prpLltextList;

	/** 损失部位信息 */
	private List<PrpLthirdCarLoss> prpLthirdCarLossList;

	/** 损失部位信息 */
	private List<PrpLthirdProp> prpLthirdPropList;

	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;

	/** 出险人员信息表 */
	private PrpLacciPerson prpLacciPerson;

	/** 报案信息补充说明 */
	private List<PrpLregistExt> prpLregistExtList;

	/** 人员伤亡跟踪信息 */
	private List<PrpLpersonTrace> prpLpersonTraceList;

	/** 风险单位信息 */
	private List<?> prpLRiskUnitList;

	/** 立案信息扩展表 */
	private PrpLext prpLext;

	/** 关联表 强三 */
	private Prplregistrpolicy prplregistrpolicy;

	/** 送再保联共保信息表 */
	private List<PrpCcoinsDto> prpCcoinsList;

	/** 简易赔案主表信息 */
	private PrpLquickCase prpLquickCase;
	
	/**
	 * 信用卡信息
	 */
	private PrpLclaimCredit prpLclaimCredit;

	/**
	 * 得到人伤跟踪信息
	 * @return 人伤跟踪 信息
	 */
	public List<PrpLpersonTrace> getPrpLpersonTraceList() {
		return prpLpersonTraceList;
	}

	/**
	 * 设置人伤跟踪信息
	 * @param PrpLpersonTraceList 人伤跟踪表信息
	 */
	public void setPrpLpersonTraceList(List<PrpLpersonTrace> prpLpersonTraceList) {
		this.prpLpersonTraceList = prpLpersonTraceList;
	}

	/**
	 * @return Returns the prpLregistExtList.
	 */
	public List<PrpLregistExt> getPrpLregistExtList() {
		return prpLregistExtList;
	}

	/**
	 * @param prpLregistExtList The prpLregistExtList to set.
	 */
	public void setPrpLregistExtList(List<PrpLregistExt> prpLregistExtList) {
		this.prpLregistExtList = prpLregistExtList;
	}

	/**
	 * 设置出险人员信息表
	 * @param prpLacciPerson 出险人员信息表
	 */
	public void setPrpLacciPerson(PrpLacciPerson prpLacciPerson) {
		this.prpLacciPerson = prpLacciPerson;
	}

	/**
	 * 得到出险人员信息表
	 * @return 出险人员信息表
	 */
	public PrpLacciPerson getPrpLacciPerson() {
		return this.prpLacciPerson;
	}

	/**
	 * 得到立案主表信息
	 * @return 立案主表信息
	 */
	public PrpLclaim getPrpLclaim() {
		return prpLclaim;
	}

	/**
	 * 设置立案主表信息
	 * @param prpLclaim 立案主表信息
	 */
	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}

	/**
	 * 得到立案文本信息
	 * @return 立案文本信息
	 */
	public List<PrpLltext> getPrpLltextList() {
		return prpLltextList;
	}

	/**
	 * 设置立案文本信息
	 * @param prpLclaimTextList 立案文本信息
	 */
	public void setPrpLltextList(List<PrpLltext> prpLltextList) {
		this.prpLltextList = prpLltextList;
	}

	/**
	 * 得到三者车辆信息
	 * @return 三者车辆信息
	 */
	public List<PrpLthirdParty> getPrpLthirdPartyList() {
		return prpLthirdPartyList;
	}

	/**
	 * 设置三者车辆信息
	 * @param PrpLthirdPartyList 三者车辆表信息
	 */
	public void setPrpLthirdPartyList(List<PrpLthirdParty> prpLthirdPartyList) {
		this.prpLthirdPartyList = prpLthirdPartyList;
	}

	/**
	 * 得到驾驭员信息
	 * @return 驾驭员 信息
	 */
	public List<PrpLdriver> getPrpLdriverList() {
		return prpLdriverList;
	}

	/**
	 * 设置驾驭员信息
	 * @param PrpLdriverList 驾驭员表信息
	 */
	public void setPrpLdriverList(List<PrpLdriver> prpLdriverList) {
		this.prpLdriverList = prpLdriverList;
	}

	/**
	 * 得到索赔申请人信息
	 * @return 索赔申请人 信息
	 */
	public List<PrpLacciPerson> getPrpLacciPersonList() {
		return prpLacciPersonList;
	}

	/**
	 * 设置索赔申请人信息
	 * @param PrpLaccipersonList 索赔申请人信息
	 */
	public void setPrpLacciPersonList(List<PrpLacciPerson> prpLacciPersonList) {
		this.prpLacciPersonList = prpLacciPersonList;
	}

	/**
	 * 得到估损金额信息
	 * @return 估损金额 信息
	 */
	public List<PrpLclaimFee> getPrpLclaimFeeList() {
		return prpLclaimFeeList;
	}

	/**
	 * 设置估损金额信息
	 * @param PrpLclaimFeeList 估损金额表信息
	 */
	public void setPrpLclaimFeeList(List<PrpLclaimFee> prpLclaimFeeList) {
		this.prpLclaimFeeList = prpLclaimFeeList;
	}

	/**
	 * 得到单证信息
	 * @return 单证信息
	 */
	public List<PrpLdoc> getPrpLdocList() {
		return prpLdocList;
	}

	/**
	 * 设置单证信息
	 * @param PrpLdocList 单证表信息
	 */
	public void setPrpLdocList(List<PrpLdoc> prpLdocList) {
		this.prpLdocList = prpLdocList;
	}

	/**
	 * 得到立案操作状态信息
	 * @return 立案操作状态信息
	 */
	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	/**
	 * 设置立案操作状态信息
	 * @param prpLclaimStuats 立案操作状态信息
	 */
	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	/**
	 * 得到险别估损金额信息
	 * @return 险别估损金额 信息
	 */
	public List<PrpLclaimLoss> getPrpLclaimLossList() {
		return prpLclaimLossList;
	}

	public List<PrpCengage> getPrpCengageList() {
		return prpCengageList;
	}

	/**
	 * 设置险别估损金额信息
	 * @param PrpLclaimLossList 险别估损金额表信息
	 */
	public void setPrpLclaimLossList(List<PrpLclaimLoss> prpLclaimLossList) {
		this.prpLclaimLossList = prpLclaimLossList;
	}

	public void setPrpCengageList(List<PrpCengage> prpCengageList) {
		this.prpCengageList = prpCengageList;
	}

	/**
	 * 得到部件损失信息
	 * @return 部件损失 信息
	 */

	public List<PrpLthirdCarLoss> getPrpLthirdCarLossList() {
		return prpLthirdCarLossList;
	}

	/**
	 * 设置部件损失信息
	 * @param prpLthirdCarLossList 部件损失信息
	 */

	public void setPrpLthirdCarLossList(List<PrpLthirdCarLoss> prpLthirdCarLossList) {
		this.prpLthirdCarLossList = prpLthirdCarLossList;
	}

	/**
	 * 设置其它损失信息
	 * @param prpLthirdPropList 其它损失信息
	 */

	public void setPrpLthirdPropList(List<PrpLthirdProp> prpLthirdPropList) {
		this.prpLthirdPropList = prpLthirdPropList;
	}

	/**
	 * 得到其它损失信息
	 * @return 其它损失信息
	 */

	public List<PrpLthirdProp> getPrpLthirdPropList() {
		return prpLthirdPropList;
	}

	/**
	 * @param prpLRiskUnitList
	 */
	public void setPrplRiskUnitList(List<?> prpLRiskUnitList) {
		this.prpLRiskUnitList = prpLRiskUnitList;
	}

	/**
	 * @return List<?>
	 */
	public List<?> getPrpLRiskUnitList() {
		return prpLRiskUnitList;
	}

	/**
	 * @param 立案信息扩展
	 */
	public void setPrpLext(PrpLext prpLext) {
		this.prpLext = prpLext;
	}

	/**
	 * @return 立案信息扩展
	 */
	public PrpLext getPrpLext() {
		return prpLext;
	}

	// add by lym 20060809 start for （特殊赔案：垫支付）
	/**
	 * @return Returns the prpLprepayList.
	 */
	public List<PrpLprepay> getPrpLprepayList() {
		return prpLprepayList;
	}

	/**
	 * @param prpLprepayList The prpLprepayList to set.
	 */
	public void setPrpLprepayList(List<PrpLprepay> prpLprepayList) {
		this.prpLprepayList = prpLprepayList;
	}

	// add by lym 20060809 start for （特殊赔案：垫支付）

	public List<PrpCcoinsDto> getPrpCcoinsList() {
		return prpCcoinsList;
	}

	/**
	 * 设置联共保信息
	 * @param prpCcoins
	 */
	public void setPrpCcoinsList(List<PrpCcoinsDto> prpCcoinsList) {
		this.prpCcoinsList = prpCcoinsList;
	}

	/**
	 * 得到联共保信息
	 * @param prpLRiskUnitList
	 */
	public void setPrpLRiskUnitList(List<?> prpLRiskUnitList) {
		this.prpLRiskUnitList = prpLRiskUnitList;
	}

	/** 自动立案操作 */
	private boolean autoClaim = false;

	/**
	 * 得到自动立案操作
	 * @return 自动立案操作
	 */
	public boolean getAutoClaim() {
		return autoClaim;
	}

	/**
	 * 设置自动立案操作
	 * @param boolean 自动立案操作
	 */
	public void setAutoClaim(boolean autoClaim) {
		this.autoClaim = autoClaim;
	}

	/**
	 * 获取简易赔案主表
	 * @return 简易赔案主表
	 */
	public PrpLquickCase getPrpLquickCase() {
		return prpLquickCase;
	}

	/**
	 * 设置属性简易赔案主表
	 * @param prpLquickCase 简易赔案主表
	 */
	public void setPrpLquickCase(PrpLquickCase prpLquickCase) {
		this.prpLquickCase = prpLquickCase;
	}

	public Prplregistrpolicy getPrplregistrpolicy() {
		return prplregistrpolicy;
	}

	public void setPrplregistrpolicy(Prplregistrpolicy prplregistrpolicy) {
		this.prplregistrpolicy = prplregistrpolicy;
	}

	public PrpLclaimCredit getPrpLclaimCredit() {
		return prpLclaimCredit;
	}

	public void setPrpLclaimCredit(PrpLclaimCredit prpLclaimCredit) {
		this.prpLclaimCredit = prpLclaimCredit;
	}
}
