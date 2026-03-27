package com.sinosoft.claim.endcase.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcaseNo;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLrecase;

/**
 * 自定义结案数据传输对象
 * <p>
 * Title: 车险理赔结案DTO
 * </p>
 * <p>
 * Description: 车险理赔结案样本程序
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
public class EndcaseDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 赔案号表主信息 */
	private List<PrpLcaseNo> prpLcaseNoList;
	/** 立案信息 */
	private PrpLclaim prpLclaim;
	private PrpLrecase prpLrecase;
	/** 赔款计算书信息 */
	private List<PrpLcompensate> prpLcompensateList;
	/** 结案报告 */
	private List<PrpLltext> prpLltextList;
	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;

	/**
	 * 得到赔款计算书主信息
	 * @return 赔款计算书主信息
	 */
	public List<PrpLcompensate> getPrpLcompensateList() {
		return prpLcompensateList;
	}

	/**
	 * 得到结案文本信息
	 * @return 结案文本信息
	 */
	public List<PrpLltext> getPrpLltextList() {
		return prpLltextList;
	}

	/**
	 * 得到立案主表信息
	 * @return 立案主表信息
	 */
	public PrpLclaim getPrpLclaim() {
		return prpLclaim;
	}

	/**
	 * 得到保费信息
	 * @return 保费信息
	 */
	public PrpLrecase getPrpLrecase() {
		return prpLrecase;
	}

	/**
	 * 得到结案主表信息
	 * @return 结案主表信息
	 */
	public List<PrpLcaseNo> getPrpLcaseNoList() {
		return prpLcaseNoList;
	}

	/**
	 * 设置操作状态信息
	 * @param prpLclaimStatus 操作状态信息
	 */
	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	/**
	 * 设置赔款计算书主信息
	 * @param setPrpLcompensateList 赔款计算书主信息
	 */
	public void setPrpLcompensateList(List<PrpLcompensate> prpLcompensateList) {
		this.prpLcompensateList = prpLcompensateList;
	}

	/**
	 * 设置结案文本信息
	 * @param prpLltextList 结案文本信息
	 */
	public void setPrpLltextList(List<PrpLltext> prpLltextList) {
		this.prpLltextList = prpLltextList;
	}

	/**
	 * 设置立案主表信息
	 * @param prpLclaim 立案主表信息
	 */
	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}

	public void setPrpLrecase(PrpLrecase prpLrecase) {
		this.prpLrecase = prpLrecase;
	}

	/**
	 * 设置结案主表信息
	 * @param prpLperpay 结案主表信息
	 */
	public void setPrpLcaseNoList(List<PrpLcaseNo> prpLcaseNoList) {
		this.prpLcaseNoList = prpLcaseNoList;
	}

	/**
	 * 得到操作状态信息
	 * @return 操作状态信息
	 */
	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	public EndcaseDto() {
	}

}
