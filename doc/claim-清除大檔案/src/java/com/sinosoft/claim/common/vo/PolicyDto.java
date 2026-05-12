package com.sinosoft.claim.common.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpCCargoItem;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCcarDriver;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCfee;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemHouse;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCitemProp;
import com.sinosoft.claim.schema.model.PrpCitemShip;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainCarGoSub;
import com.sinosoft.claim.schema.model.PrpCmainCargo;
import com.sinosoft.claim.schema.model.PrpCmainConstruct;
import com.sinosoft.claim.schema.model.PrpCmainLiab;
import com.sinosoft.claim.schema.model.PrpCmainLoan;
import com.sinosoft.claim.schema.model.PrpCmainSub;
import com.sinosoft.claim.schema.model.PrpCplan;
import com.sinosoft.claim.schema.model.PrpCplane;
import com.sinosoft.claim.schema.model.PrpCprofit;
import com.sinosoft.claim.schema.model.PrpCprofitDetail;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
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
public class PolicyDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2455394769509919586L;

	/** 保单主信息 */
	private PrpCmain prpCmain;

	/** 保费信息 */
	private PrpCfee prpCfee;

	/** 保费信息 */
	private List<PrpCfee> prpCfeeList;

	/** 保险标的信息 */
	private List<PrpCitemKind> prpCitemKindList;

	/** 保险地址信息 */
	private List<PrpCaddress> prpCaddressList;

	/** 保单客户信息 */
	private List<PrpCinsured> prpCinsuredList;

	/** 车辆信息 */
	private List<PrpCitemCar> prpCitemCarList;

	/** 优惠信息表 */
	private List<PrpCprofit> prpCprofitList;

	/** 优惠折扣明细表信息 */
	private List<PrpCprofitDetail> prpCprofitDetailList;

	/** 交费计划信息 */
	private List<PrpCplan> prpCplanList;

	/** 特别约定信息 */
	private List<PrpCengage> prpCengageList;

	/** 驾驶员信息 */
	private List<PrpCcarDriver> prpCcarDriverList;

	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;

	/** 财产险标的信息 */
	private List<PrpCitemProp> prpCitemPropList;

	/** 建安工险保单信息 */
	private List<PrpCmainConstruct> prpCmainConstructList;

	/** 责任险保单信息 */
	private List<PrpCmainLiab> prpCmainLiabList;

	/** 货运险保单信息 */
	private PrpCmainCargo prpCmainCargo;

	/** 责任险限额信息 */
	private List<PrpClimit> prpClimitList;

	/** 增加责任险追溯期 */
	private DateTime liabStartDate;

	/** 个贷险房屋信息 */
	private List<PrpCitemHouse> prpCitemHouseList;

	private PrpCmainLoan prpCmainLoan;

	/** 保单关联信息 */
	private List<PrpCmainSub> prpCmainSubList;

	/** 保单主信息 */
	private List<Prplregistrpolicy> prpLRegistRPolicyList;
	/** 货物运输信息 */
	private List<PrpCmainCarGoSub> prpCmainCarGoSubList;
	/** 船舶险标的信息 */
	private PrpCitemShip prpCitemShip;
	/** 航空信息 */
	private PrpCplane prpCplane;
	/** 货物运输标的讯息 */
	private List<PrpCCargoItem> prpCCargoItemList;
	
	private PrpCmainCarGoSub prpCmainCarGoSub;
	
	private List<PrpCinsuredNature> prpCinsuredNatureList;


	public PrpCmainCarGoSub getPrpCmainCarGoSub() {
		if (this.prpCmainCarGoSubList != null && !prpCmainCarGoSubList.isEmpty()) {
			return prpCmainCarGoSubList.get(0);
		}
		return prpCmainCarGoSub;
	}

	public void setPrpCmainCarGoSub(PrpCmainCarGoSub prpCmainCarGoSub) {
		this.prpCmainCarGoSub = prpCmainCarGoSub;
	}

	public List<PrpCmainCarGoSub> getPrpCmainCarGoSubList() {
		return prpCmainCarGoSubList;
	}

	public void setPrpCmainCarGoSubList(List<PrpCmainCarGoSub> prpCmainCarGoSubList) {
		this.prpCmainCarGoSubList = prpCmainCarGoSubList;
	}

	public List<PrpCCargoItem> getPrpCCargoItemList() {
		return prpCCargoItemList;
	}

	public void setPrpCCargoItemList(List<PrpCCargoItem> prpCCargoItemList) {
		this.prpCCargoItemList = prpCCargoItemList;
	}

	public PrpCitemShip getPrpCitemShip() {
		return prpCitemShip;
	}

	public void setPrpCitemShip(PrpCitemShip prpCitemShip) {
		this.prpCitemShip = prpCitemShip;
	}

	public PrpCplane getPrpCplane() {
		return prpCplane;
	}

	public void setPrpCplane(PrpCplane prpCplane) {
		this.prpCplane = prpCplane;
	}

	public void Policy() {
	}

	/** 责任险限额信息 */
	public List<PrpClimit> getPrpClimitList() {
		return prpClimitList;
	}

	public void setPrpClimitList(List<PrpClimit> prpClimitList) {
		this.prpClimitList = prpClimitList;
	}

	/**
	 * 得到保单关联表信息
	 * @return 保单关联表信息
	 */
	public List<PrpCmainSub> getPrpCmainSubList() {
		return prpCmainSubList;
	}

	/**
	 * 设置保单关联表信息
	 * @param prpCmain 保单关联表信息
	 */
	public void setPrpCmainSubList(List<PrpCmainSub> prpCmainSubList) {
		this.prpCmainSubList = prpCmainSubList;
	}

	/**
	 * 得到保单关联表信息
	 * @return 保单关联表信息
	 */
	public List<Prplregistrpolicy> getPrpLRegistRPolicyList() {
		return prpLRegistRPolicyList;
	}

	/**
	 * 设置保单关联表信息
	 * @param prpCmain 保单关联表信息
	 */
	public void setPrpLRegistRPolicyList(List<Prplregistrpolicy> prpLRegistRPolicyList) {
		this.prpLRegistRPolicyList = prpLRegistRPolicyList;
	}

	/**
	 * 得到保单主表信息
	 * @return 保单主表信息
	 */
	public PrpCmain getPrpCmain() {
		return prpCmain;
	}

	/**
	 * 设置保单主表信息
	 * @param prpCmain 保单主表信息
	 */
	public void setPrpCmain(PrpCmain prpCmain) {
		this.prpCmain = prpCmain;
	}

	/**
	 * 得到保费信息
	 * @return 保费信息
	 */
	public PrpCfee getPrpCfee() {
		return prpCfee;
	}

	/**
	 * 设置费表信息
	 * @param PrpCfee 保费表信息
	 */
	public void setPrpCfee(PrpCfee prpCfee) {
		this.prpCfee = prpCfee;
	}

	/**
	 * 得到itemkind信息
	 * @return itemkind信息
	 */
	public List<PrpCitemKind> getPrpCitemKindList() {
		return prpCitemKindList;
	}

	/**
	 * 设置itemkind信息
	 * @param prpCitemKindList itemkind表信息
	 */
	public void setPrpCitemKindList(List<PrpCitemKind> prpCitemKindList) {
		this.prpCitemKindList = prpCitemKindList;
	}

	/**
	 * 得到address信息
	 * @return address 信息
	 */
	public List<PrpCaddress> getPrpCaddressList() {
		return prpCaddressList;
	}

	/**
	 * 设置address信息
	 * @param prpCaddressList address表信息
	 */
	public void setPrpCaddressList(List<PrpCaddress> prpCaddressList) {
		this.prpCaddressList = prpCaddressList;
	}

	/**
	 * 得到insured信息
	 * @return insured 信息
	 */
	public List<PrpCinsured> getPrpCinsuredList() {
		return prpCinsuredList;
	}

	/**
	 * 设置insured信息
	 * @param prpCinsuredList insured表信息
	 */
	public void setPrpCinsuredList(List<PrpCinsured> prpCinsuredList) {
		this.prpCinsuredList = prpCinsuredList;
	}

	/**
	 * 得到PrpCitem_car信息
	 * @return itemcar 信息 PrpCitemCar
	 */
	public List<PrpCitemCar> getPrpCitemCarList() {
		return prpCitemCarList;
	}

	/**
	 * 设置itemcar信息
	 * @param PrpCitemCarList itemcar表信息
	 */
	public void setPrpCitemCarList(List<PrpCitemCar> prpCitemCarList) {
		this.prpCitemCarList = prpCitemCarList;
	}

	/**
	 * 得到carDriver信息
	 * @return carDriver 信息
	 */
	public List<PrpCcarDriver> getPrpCcarDriverList() {
		return prpCcarDriverList;
	}

	/**
	 * 得到立案操作状态信息
	 * @return 立案操作状态信息
	 */

	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	public List<PrpCprofitDetail> getPrpCprofitDetailList() {
		return prpCprofitDetailList;
	}

	public List<PrpCplan> getPrpCplanList() {
		return prpCplanList;
	}

	/**
	 * 设置insured信息
	 * @param prpCinsuredList insured表信息
	 */
	public void setPrpCcarDriverList(List<PrpCcarDriver> prpCcarDriverList) {
		this.prpCcarDriverList = prpCcarDriverList;
	}

	/**
	 * 设置立案操作状态信息
	 * @param prpLclaimStuats 立案操作状态信息
	 */
	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	public void setPrpCprofitDetailList(List<PrpCprofitDetail> prpCprofitDetailList) {
		this.prpCprofitDetailList = prpCprofitDetailList;
	}

	public void setPrpCplanList(List<PrpCplan> prpCplanList) {
		this.prpCplanList = prpCplanList;
	}

	/**
	 * 得到特别约定信息
	 * @return 特别约定信息
	 */
	public List<PrpCengage> getPrpCengageList() {
		return prpCengageList;
	}

	public List<PrpCprofit> getPrpCprofitList() {
		return prpCprofitList;
	}

	public List<PrpCfee> getPrpCfeeList() {
		return prpCfeeList;
	}

	/**
	 * 设置特别约定信息
	 * @param prpCengageList 特别约定信息
	 */
	public void setPrpCengageList(List<PrpCengage> prpCengageList) {
		this.prpCengageList = prpCengageList;
	}

	public void setPrpCprofitList(List<PrpCprofit> prpCprofitList) {
		this.prpCprofitList = prpCprofitList;
	}

	public void setPrpCfeeList(List<PrpCfee> prpCfeeList) {
		this.prpCfeeList = prpCfeeList;
	}

	/**
	 * 设置货运险保单信息
	 * @return 货运险保单信息
	 */
	public PrpCmainCargo getPrpCmainCargo() {
		return this.prpCmainCargo;
	}

	/**
	 * 得到货运险保单信息
	 * @param prpCmain_cargo 货运险保单信息
	 */
	public void setPrpCmainCargo(PrpCmainCargo prpCmainCargo) {
		this.prpCmainCargo = prpCmainCargo;
	}

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

	public List<PrpCitemHouse> getPrpCitemHouseList() {
		return prpCitemHouseList;
	}

	public void setPrpCitemHouseList(List<PrpCitemHouse> prpCitemHouseList) {
		this.prpCitemHouseList = prpCitemHouseList;
	}

	public PrpCmainLoan getPrpCmainLoan() {
		return prpCmainLoan;
	}

	public void setPrpCmainLoanList(PrpCmainLoan prpCmainLoan) {
		this.prpCmainLoan = prpCmainLoan;
	}

	public List<PrpCitemProp> getPrpCitemPropList() {
		return prpCitemPropList;
	}

	public void setPrpCitemPropList(List<PrpCitemProp> prpCitemPropList) {
		this.prpCitemPropList = prpCitemPropList;
	}

	public List<PrpCmainConstruct> getPrpCmainConstructList() {
		return prpCmainConstructList;
	}

	public void setPrpCmainConstructList(List<PrpCmainConstruct> prpCmainConstructList) {
		this.prpCmainConstructList = prpCmainConstructList;
	}

	public List<PrpCmainLiab> getPrpCmainLiabList() {
		return prpCmainLiabList;
	}

	public void setPrpCmainLiabList(List<PrpCmainLiab> prpCmainLiabList) {
		this.prpCmainLiabList = prpCmainLiabList;
	}

	public List<PrpCinsuredNature> getPrpCinsuredNatureList() {
		return prpCinsuredNatureList;
	}

	public void setPrpCinsuredNatureList(List<PrpCinsuredNature> prpCinsuredNatureList) {
		this.prpCinsuredNatureList = prpCinsuredNatureList;
	}
	
}
