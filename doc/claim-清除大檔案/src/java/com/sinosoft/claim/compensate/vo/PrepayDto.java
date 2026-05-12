package com.sinosoft.claim.compensate.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLptext;

/**
 * 自定义预赔数据传输对象
 * <p>
 * Title: 车险理赔预赔DTO
 * </p>
 * @Description 由com.sinosoft.claim.dto.custom.PrepayDto
 * @author 中科软
 */
public class PrepayDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 预赔主信息 */
	private PrpLprepay prpLprepay;
	/** 预赔报告 */
	private List<PrpLptext> prpLptextList = new ArrayList<PrpLptext>();
	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;
	/** 立案信息 */
	private PrpLclaim prpLclaim;

	/**
	 * 得到预赔主表信息
	 * @return 预赔主表信息
	 */
	public PrpLprepay getPrpLprepay() {
		return prpLprepay;
	}

	/**
	 * 设置预赔主表信息
	 * @param prpLperpay 预赔主表信息
	 */
	public void setPrpLprepay(PrpLprepay prpLprepay) {
		this.prpLprepay = prpLprepay;
	}

	/**
	 * 得到预赔文本信息
	 * @return 预赔文本信息
	 */
	public List<PrpLptext> getPrpLptextList() {
		return prpLptextList;
	}

	/**
	 * 设置预赔文本信息
	 * @param prpLperpayTextList 预赔文本信息
	 */
	public void setPrpLptextList(List<PrpLptext> prpLptextList) {
		this.prpLptextList = prpLptextList;
	}

	/**
	 * 得到操作状态信息
	 * @return 操作状态信息
	 */
	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	/**
	 * 设置操作状态信息
	 * @param prpLclaimStatus 操作状态信息
	 */
	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	/**
	 * 得到立案信息
	 * @return 立案信息F
	 */
	public PrpLclaim getPrpLclaim() {
		return prpLclaim;
	}

	/**
	 * 设置立案信息
	 * @param prpLclaim 立案信息
	 */
	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}
}
