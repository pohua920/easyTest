package com.sinosoft.claim.endcase.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.dto.domain.PrpCmainDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLrecase;

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
public class ReCaseDto implements Serializable {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** 保单主信息 */
	private PrpCmainDto prpCmainDto;
	/** 保费信息 */
	private PrpLclaim prpLclaim;
	private PrpLrecase prpLrecase;
	/** 保费信息 */
	private List<PrpLrecase> prpLrecaseList;

	/**
	 * 默认的构造函数
	 */
	public ReCaseDto() {
	}

	public List<PrpLrecase> getPrpLrecaseDtoList() {
		return prpLrecaseList;
	}

	/**
	 * 设置itemkind信息
	 * @param prpCitemKindList itemkind表信息
	 */
	public void setPrpLrecaseList(List<PrpLrecase> prpLrecaseList) {
		this.prpLrecaseList = prpLrecaseList;
	}

	/**
	 * 得到保单主表信息
	 * @return 保单主表信息
	 */
	public PrpCmainDto getPrpCmainDto() {
		return prpCmainDto;
	}

	/**
	 * 设置保单主表信息
	 * @param prpCmainDto 保单主表信息
	 */
	public void setPrpCmainDto(PrpCmainDto prpCmainDto) {
		this.prpCmainDto = prpCmainDto;
	}

	/**
	 * 得到保单主表信息
	 * @return 保单主表信息
	 */
	public PrpLclaim getPrpLclaim() {
		return prpLclaim;
	}

	/**
	 * 设置保单主表信息
	 * @param prpCmainDto 保单主表信息
	 */
	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}

	/**
	 * 得到保费信息
	 * @return 保费信息
	 */
	public PrpLrecase getPrpLrecase() {
		return prpLrecase;
	}

	/**
	 * 设置费表信息
	 * @param PrpCfeeDto 保费表信息
	 */
	public void setPrpLrecase(PrpLrecase prpLrecase) {
		this.prpLrecase = prpLrecase;
	}
}
