package com.sinosoft.claim.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpPengage;
import com.sinosoft.claim.schema.model.PrpPfee;
import com.sinosoft.claim.schema.model.PrpPhead;
import com.sinosoft.claim.schema.model.PrpPitemCar;
import com.sinosoft.claim.schema.model.PrpPitemKind;
import com.sinosoft.claim.schema.model.PrpPmain;
import com.sinosoft.claim.schema.model.PrpPprofit;
import com.sinosoft.claim.schema.model.PrpPtext;

/**
 * 自定义批单数据传输对象
 * <p>
 * Title: 车险理赔批单DTO
 * </p>
 * <p>
 * Description: 车险理赔批单样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */

public class EndorseDto implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 批单号码 */
	private String endorseNo;
	/** 批改信息表信息 */
	private PrpPhead prpPhead;
	/** 批改保单信息表信息 */
	private PrpPmain prpPmain;
	/** 保险标的信息 */
	private List<PrpPitemKind> prpPitemKindList = new ArrayList<PrpPitemKind>(0);
	/** 批文对象 */
	private List<PrpPtext> prpPtextList = new ArrayList<PrpPtext>(0);
	/** 机动车险标的信息 */
	private List<PrpPitemCar> prpPitemcarList = new ArrayList<PrpPitemCar>(0);
	/** 批改保额保费变化表 */
	private List<PrpPfee> prpPfeeList = new ArrayList<PrpPfee>(0);
	/** 优惠信息表 */
	private List<PrpPprofit> prpPprofitList = new ArrayList<PrpPprofit>(0);
	/** 特别约定表 */
	private List<PrpPengage> prpPengageList = new ArrayList<PrpPengage>(0);
	/** 批改信息表信息 列表 */
	private List<PrpPhead> prpPheadList = new ArrayList<PrpPhead>(0);
	/** 批改保单信息表信息 列表 */
	private List<PrpPmain> prpPmainList = new ArrayList<PrpPmain>(0);

	public EndorseDto() {
	}
	
	public EndorseDto(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	public String getEndorseNo() {
		return endorseNo;
	}

	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	public PrpPhead getPrpPhead() {
		return prpPhead;
	}

	public void setPrpPhead(PrpPhead prpPhead) {
		this.prpPhead = prpPhead;
	}

	public PrpPmain getPrpPmain() {
		return prpPmain;
	}

	public void setPrpPmain(PrpPmain prpPmain) {
		this.prpPmain = prpPmain;
	}

	public List<PrpPitemKind> getPrpPitemKindList() {
		return prpPitemKindList;
	}

	public void setPrpPitemKindList(List<PrpPitemKind> prpPitemKindList) {
		this.prpPitemKindList = prpPitemKindList;
	}

	public List<PrpPtext> getPrpPtextList() {
		return prpPtextList;
	}

	public void setPrpPtextList(List<PrpPtext> prpPtextList) {
		this.prpPtextList = prpPtextList;
	}

	public List<PrpPitemCar> getPrpPitemcarList() {
		return prpPitemcarList;
	}

	public void setPrpPitemcarList(List<PrpPitemCar> prpPitemcarList) {
		this.prpPitemcarList = prpPitemcarList;
	}

	public List<PrpPfee> getPrpPfeeList() {
		return prpPfeeList;
	}

	public void setPrpPfeeList(List<PrpPfee> prpPfeeList) {
		this.prpPfeeList = prpPfeeList;
	}

	public List<PrpPprofit> getPrpPprofitList() {
		return prpPprofitList;
	}

	public void setPrpPprofitList(List<PrpPprofit> prpPprofitList) {
		this.prpPprofitList = prpPprofitList;
	}

	public List<PrpPengage> getPrpPengageList() {
		return prpPengageList;
	}

	public void setPrpPengageList(List<PrpPengage> prpPengageList) {
		this.prpPengageList = prpPengageList;
	}

	public List<PrpPhead> getPrpPheadList() {
		return prpPheadList;
	}

	public void setPrpPheadList(List<PrpPhead> prpPheadList) {
		this.prpPheadList = prpPheadList;
	}

	public List<PrpPmain> getPrpPmainList() {
		return prpPmainList;
	}

	public void setPrpPmainList(List<PrpPmain> prpPmainList) {
		this.prpPmainList = prpPmainList;
	}

}
