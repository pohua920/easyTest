package com.sinosoft.claim.common.vo;

import java.io.Serializable;

import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLcertifyCollect;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.PrpLverifyLoss;

/**
 * 案件相关节点对象
 * @ClassName CaseRelateNodeDto
 * @Description 案件相关节点对象
 * @author 中科软
 */
public class CaseRelateNodeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 保单 */
	PrpCmain prpCmain = null;//  保单对象暂时未得到迁移对象，稍後处理
	/** 报案 */
	PrpLregist prpLregist = null;
	/** 调度 */
	PrpLscheduleMainWF prpLscheduleMainWF = null;
	/** 查勘 */
	PrpLcheck prpLcheck = null;
	/** 定损/核损 */
	PrpLverifyLoss prpLverifyLoss = null;
	/** 立案 */
	PrpLclaim prpLclaim = null;
	/** 单证 */
	PrpLcertifyCollect prpLcertifyCollect = null;
	/** 预赔 */
	PrpLprepay prpLprepay = null;
	/** 实赔 */
	PrpLcompensate prpLcompensate = null;
	/** 结案 */
	EndcaseDto endcaseDto = null;
	/** 险别 */
	private String riskCode = null;

	public PrpCmain getPrpCmain() {
		return prpCmain;
	}

	public void setPrpCmain(PrpCmain prpCmain) {
		this.prpCmain = prpCmain;
	}

	public PrpLregist getPrpLregist() {
		return prpLregist;
	}

	public void setPrpLregist(PrpLregist prpLregist) {
		this.prpLregist = prpLregist;
	}

	public PrpLscheduleMainWF getPrpLscheduleMainWF() {
		return prpLscheduleMainWF;
	}

	public void setPrpLscheduleMainWF(PrpLscheduleMainWF prpLscheduleMainWF) {
		this.prpLscheduleMainWF = prpLscheduleMainWF;
	}

	public PrpLcheck getPrpLcheck() {
		return prpLcheck;
	}

	public void setPrpLcheck(PrpLcheck prpLcheck) {
		this.prpLcheck = prpLcheck;
	}

	public PrpLverifyLoss getPrpLverifyLoss() {
		return prpLverifyLoss;
	}

	public void setPrpLverifyLoss(PrpLverifyLoss prpLverifyLoss) {
		this.prpLverifyLoss = prpLverifyLoss;
	}

	public PrpLclaim getPrpLclaim() {
		return prpLclaim;
	}

	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}

	public PrpLcertifyCollect getPrpLcertifyCollect() {
		return prpLcertifyCollect;
	}

	public void setPrpLcertifyCollect(PrpLcertifyCollect prpLcertifyCollect) {
		this.prpLcertifyCollect = prpLcertifyCollect;
	}

	public PrpLprepay getPrpLprepay() {
		return prpLprepay;
	}

	public void setPrpLprepay(PrpLprepay prpLprepay) {
		this.prpLprepay = prpLprepay;
	}

	public PrpLcompensate getPrpLcompensate() {
		return prpLcompensate;
	}

	public void setPrpLcompensate(PrpLcompensate prpLcompensate) {
		this.prpLcompensate = prpLcompensate;
	}

	public EndcaseDto getEndcaseDto() {
		return endcaseDto;
	}

	public void setEndcaseDto(EndcaseDto endcaseDto) {
		this.endcaseDto = endcaseDto;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public CaseRelateNodeDto() {

	}

}
