package com.sinosoft.claim.common.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopyCarDriver;
import com.sinosoft.claim.schema.model.PrpCopyFee;
import com.sinosoft.claim.schema.model.PrpCopyInsured;
import com.sinosoft.claim.schema.model.PrpCopyItemCar;
import com.sinosoft.claim.schema.model.PrpCopyItemKind;
import com.sinosoft.claim.schema.model.PrpCopyLimit;
import com.sinosoft.claim.schema.model.PrpCopyMain;
import com.sinosoft.claim.schema.model.PrpCopyPlan;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.PrpCopyInsuredNature;
import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * 自定义保单数据传输对象
 * <p>
 * Title: 车险理赔保单DTO
 * </p>
 * <p>
 * Description: 车险理赔保单样本程序
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
public class PolicyCopyDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2455394769509919586L;

	/** 保单主信息 */
	private PrpCopyMain prpCopyMain;
	
	/** 保单主信息 */
	private PrpPhead prpPhead;

	/** 保费信息 */
	private PrpCopyFee prpCopyFee;

	/** 保费信息 */
	private List<PrpCopyFee> prpCopyFeeList;

	/** 保险标的信息 */
	private List<PrpCopyItemKind> prpCopyItemKindList;

//	/** 保险地址信息 */
//	private List<PrpCopyAddress> prpCopyAddressList;

	/** 保单客户信息 */
	private List<PrpCopyInsured> prpCopyInsuredList;

	/** 车辆信息 */
	private List<PrpCopyItemCar> prpCopyItemCarList;

	/** 优惠信息表 */
//	private List<PrpCopyProfit> prpCopyProfitList;

	/** 优惠折扣明细表信息 */
//	private List<PrpCopyProfitDetail> prpCopyProfitDetailList;

	/** 交费计划信息 */
	private List<PrpCopyPlan> prpCopyPlanList;

	/** 特别约定信息 */
//	private List<PrpCopyEngage> prpCopyEngageList;

	/** 驾驶员信息 */
	private List<PrpCopyCarDriver> prpCopyCarDriverList;

	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;
//
//	/** 财产险标的信息 */
//	private List<PrpCopyItemProp> prpCopyItemPropList;
//
//	/** 建安工险保单信息 */
//	private List<PrpCopyMainConstruct> prpCopyMainConstructList;
//
//	/** 责任险保单信息 */
//	private List<PrpCopyMainLiab> prpCopyMainLiabList;
//
//	/** 货运险保单信息 */
//	private PrpCopyMainCargo prpCopyMainCargo;

	/** 责任险限额信息 */
	private List<PrpCopyLimit> prpCopyLimitList;

	/** 增加责任险追溯期 */
	private DateTime liabStartDate;

//	/** 个贷险房屋信息 */
//	private List<PrpCopyItemHouse> prpCopyItemHouseList;
//
//	private PrpCopyMainLoan prpCopyMainLoan;

//	/** 保单关联信息 */
//	private List<PrpCopyMainSub> prpCopyMainSubList;
	
	private List<PrpCopyInsuredNature> prpCopyInsuredNatureList;

	/** 保单主信息 */
	private List<Prplregistrpolicy> prpLRegistRPolicyList;

	public void Policy() {
	}

	/** 责任险限额信息 */
	public List<PrpCopyLimit> getPrpCopyLimitList() {
		return prpCopyLimitList;
	}

	public void setPrpCopyLimitList(List<PrpCopyLimit> prpCopyLimitList) {
		this.prpCopyLimitList = prpCopyLimitList;
	}

//	/**
//	 * 得到保单关联表信息
//	 * @return 保单关联表信息
//	 */
//	public List<PrpCopyMainSub> getPrpCopyMainSubList() {
//		return prpCopyMainSubList;
//	}
//
//	/**
//	 * 设置保单关联表信息
//	 * @param prpCopyMain 保单关联表信息
//	 */
//	public void setPrpCopyMainSubList(List<PrpCopyMainSub> prpCopyMainSubList) {
//		this.prpCopyMainSubList = prpCopyMainSubList;
//	}

	/**
	 * 得到保单关联表信息
	 * @return 保单关联表信息
	 */
	public List<Prplregistrpolicy> getPrpLRegistRPolicyList() {
		return prpLRegistRPolicyList;
	}

	/**
	 * 设置保单关联表信息
	 * @param prpCopyMain 保单关联表信息
	 */
	public void setPrpLRegistRPolicyList(List<Prplregistrpolicy> prpLRegistRPolicyList) {
		this.prpLRegistRPolicyList = prpLRegistRPolicyList;
	}

	/**
	 * 得到保单主表信息
	 * @return 保单主表信息
	 */
	public PrpCopyMain getPrpCopyMain() {
		return prpCopyMain;
	}

	/**
	 * 设置保单主表信息
	 * @param prpCopyMain 保单主表信息
	 */
	public void setPrpCopyMain(PrpCopyMain prpCopyMain) {
		this.prpCopyMain = prpCopyMain;
	}

	public PrpPhead getPrpPhead() {
		return prpPhead;
	}

	public void setPrpPhead(PrpPhead prpPhead) {
		this.prpPhead = prpPhead;
	}

	/**
	 * 得到保费信息
	 * @return 保费信息
	 */
	public PrpCopyFee getPrpCopyFee() {
		return prpCopyFee;
	}

	/**
	 * 设置费表信息
	 * @param PrpCopyFee 保费表信息
	 */
	public void setPrpCopyFee(PrpCopyFee prpCopyFee) {
		this.prpCopyFee = prpCopyFee;
	}

	/**
	 * 得到itemkind信息
	 * @return itemkind信息
	 */
	public List<PrpCopyItemKind> getPrpCopyItemKindList() {
		return prpCopyItemKindList;
	}

	/**
	 * 设置itemkind信息
	 * @param PrpCopyItemKindList itemkind表信息
	 */
	public void setPrpCopyItemKindList(List<PrpCopyItemKind> PrpCopyItemKindList) {
		this.prpCopyItemKindList = PrpCopyItemKindList;
	}

//	/**
//	 * 得到address信息
//	 * @return address 信息
//	 */
//	public List<PrpCopyAddress> getPrpCopyAddressList() {
//		return prpCopyAddressList;
//	}
//
//	/**
//	 * 设置address信息
//	 * @param prpCopyAddressList address表信息
//	 */
//	public void setPrpCopyAddressList(List<PrpCopyAddress> prpCopyAddressList) {
//		this.prpCopyAddressList = prpCopyAddressList;
//	}

	/**
	 * 得到insured信息
	 * @return insured 信息
	 */
	public List<PrpCopyInsured> getPrpCopyInsuredList() {
		return prpCopyInsuredList;
	}

	/**
	 * 设置insured信息
	 * @param prpCopyInsuredList insured表信息
	 */
	public void setPrpCopyInsuredList(List<PrpCopyInsured> prpCopyInsuredList) {
		this.prpCopyInsuredList = prpCopyInsuredList;
	}

	/**
	 * 得到PrpCopyitem_car信息
	 * @return itemcar 信息 PrpCopyItemCar
	 */
	public List<PrpCopyItemCar> getPrpCopyItemCarList() {
		return prpCopyItemCarList;
	}

	/**
	 * 设置itemcar信息
	 * @param PrpCopyItemCarList itemcar表信息
	 */
	public void setPrpCopyItemCarList(List<PrpCopyItemCar> prpCopyItemCarList) {
		this.prpCopyItemCarList = prpCopyItemCarList;
	}

	/**
	 * 得到carDriver信息
	 * @return carDriver 信息
	 */
	public List<PrpCopyCarDriver> getPrpCopyCarDriverList() {
		return prpCopyCarDriverList;
	}

	/**
	 * 得到立案操作状态信息
	 * @return 立案操作状态信息
	 */

	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

//	public List<PrpCopyProfitDetail> getPrpCopyProfitDetailList() {
//		return prpCopyProfitDetailList;
//	}

	public List<PrpCopyPlan> getPrpCopyPlanList() {
		return prpCopyPlanList;
	}

	/**
	 * 设置insured信息
	 * @param prpCopyInsuredList insured表信息
	 */
	public void setPrpCopyCarDriverList(List<PrpCopyCarDriver> prpCopyCarDriverList) {
		this.prpCopyCarDriverList = prpCopyCarDriverList;
	}

	/**
	 * 设置立案操作状态信息
	 * @param prpLclaimStuats 立案操作状态信息
	 */
	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

//	public void setPrpCopyProfitDetailList(List<PrpCopyProfitDetail> prpCopyProfitDetailList) {
//		this.prpCopyProfitDetailList = prpCopyProfitDetailList;
//	}

	public void setPrpCopyPlanList(List<PrpCopyPlan> prpCopyPlanList) {
		this.prpCopyPlanList = prpCopyPlanList;
	}

//	/**
//	 * 得到特别约定信息
//	 * @return 特别约定信息
//	 */
//	public List<PrpCopyEngage> getPrpCopyEngageList() {
//		return prpCopyEngageList;
//	}
//
//	public List<PrpCopyProfit> getPrpCopyProfitList() {
//		return prpCopyProfitList;
//	}

	public List<PrpCopyFee> getPrpCopyFeeList() {
		return prpCopyFeeList;
	}

	/**
	 * 设置特别约定信息
	 * @param prpCopyEngageList 特别约定信息
	 */
//	public void setPrpCopyEngageList(List<PrpCopyEngage> prpCopyEngageList) {
//		this.prpCopyEngageList = prpCopyEngageList;
//	}
//
//	public void setPrpCopyProfitList(List<PrpCopyProfit> prpCopyProfitList) {
//		this.prpCopyProfitList = prpCopyProfitList;
//	}

	public void setPrpCopyFeeList(List<PrpCopyFee> prpCopyFeeList) {
		this.prpCopyFeeList = prpCopyFeeList;
	}

//	/**
//	 * 设置货运险保单信息
//	 * @return 货运险保单信息
//	 */
//	public PrpCopyMainCargo getPrpCopyMainCargo() {
//		return this.prpCopyMainCargo;
//	}

//	/**
//	 * 得到货运险保单信息
//	 * @param prpCopyMain_cargo 货运险保单信息
//	 */
//	public void setPrpCopyMainCargo(PrpCopyMainCargo prpCopyMainCargo) {
//		this.prpCopyMainCargo = prpCopyMainCargo;
//	}

	/**
	 * 设置责任险追溯期
	 * @return 责任险追溯期
	 */
	public DateTime getLiabStartDate() {
		return this.liabStartDate;
	}

	/**
	 * 得到责任险追溯期
	 * @param 责任险追溯期
	 */
	public void setLiabStartDate(DateTime liabStartDate) {
		this.liabStartDate = liabStartDate;
	}

	public List<PrpCopyInsuredNature> getPrpCopyInsuredNatureList() {
		return prpCopyInsuredNatureList;
	}

	public void setPrpCopyInsuredNatureList(List<PrpCopyInsuredNature> prpCopyInsuredNatureList) {
		this.prpCopyInsuredNatureList = prpCopyInsuredNatureList;
	}

//	public List<PrpCopyItemHouse> getPrpCopyItemHouseList() {
//		return prpCopyItemHouseList;
//	}
//
//	public void setPrpCopyItemHouseList(List<PrpCopyItemHouse> prpCopyItemHouseList) {
//		this.prpCopyItemHouseList = prpCopyItemHouseList;
//	}
//
//	public PrpCopyMainLoan getPrpCopyMainLoan() {
//		return prpCopyMainLoan;
//	}
//
//	public void setPrpCopyMainLoanList(PrpCopyMainLoan prpCopyMainLoan) {
//		this.prpCopyMainLoan = prpCopyMainLoan;
//	}
//
//	public List<PrpCopyItemProp> getPrpCopyItemPropList() {
//		return prpCopyItemPropList;
//	}
//
//	public void setPrpCopyItemPropList(List<PrpCopyItemProp> prpCopyItemPropList) {
//		this.prpCopyItemPropList = prpCopyItemPropList;
//	}
//
//	public List<PrpCopyMainConstruct> getPrpCopyMainConstructList() {
//		return prpCopyMainConstructList;
//	}
//
//	public void setPrpCopyMainConstructList(List<PrpCopyMainConstruct> prpCopyMainConstructList) {
//		this.prpCopyMainConstructList = prpCopyMainConstructList;
//	}
//
//	public List<PrpCopyMainLiab> getPrpCopyMainLiabList() {
//		return prpCopyMainLiabList;
//	}
//
//	public void setPrpCopyMainLiabList(List<PrpCopyMainLiab> prpCopyMainLiabList) {
//		this.prpCopyMainLiabList = prpCopyMainLiabList;
//	}
	
	
}
