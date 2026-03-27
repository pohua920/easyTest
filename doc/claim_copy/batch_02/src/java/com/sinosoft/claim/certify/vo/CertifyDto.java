package com.sinosoft.claim.certify.vo;

import java.io.Serializable;
import java.util.*;

import com.sinosoft.claim.schema.model.PrpLcertifyCollect;
import com.sinosoft.claim.schema.model.PrpLcertifyDirect;
import com.sinosoft.claim.schema.model.PrpLcertifyImg;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLqualityCheck;
import com.sinosoft.claim.schema.model.PrpLregistExt;
import com.sinosoft.claim.schema.model.PrpLcertifyPayee;

/**
 * 自定义单证数据传输对象
 * <p>
 * Title: 车险理赔单证DTO
 * </p>
 * <p>
 * Description: 车险理赔单证样本程序
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
public class CertifyDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 单证收集主信息 */
	private List<PrpLcertifyCollect> prpLcertifyCollectList;
	/** 单证收集主信息 */
	private PrpLcertifyCollect prpLcertifyCollect;
	/** 单证及影像信息 */
	private List<PrpLcertifyImg> prpLcertifyImgList;
	/** 索赔指引信息 */
	private List<PrpLcertifyDirect> prpLcertifyDirectList;
	/** 质量评审内容 */
	private List<PrpLqualityCheck> prpLqualityCheckList;
	/** 操作状态信息 */
	private PrpLclaimStatus prpLclaimStatus;
	/** 报案信息补充说明 */
	private List<PrpLregistExt> prpLregistExtList;
	/** 节点名称 */
	private List<PrpLcertifyPayee> prpLcertifyPayeeList;
	/** 领款人信息 */
	private String nodeType;

	/**
	 * 巨灾代码显示与修改功能
	 */
	private PrpLclaim prpLclaim;

	public List<PrpLcertifyCollect> getPrpLcertifyCollectList() {
		return prpLcertifyCollectList;
	}

	public void setPrpLcertifyCollectList(List<PrpLcertifyCollect> prpLcertifyCollectList) {
		this.prpLcertifyCollectList = prpLcertifyCollectList;
	}

	public PrpLcertifyCollect getPrpLcertifyCollect() {
		return prpLcertifyCollect;
	}

	public void setPrpLcertifyCollect(PrpLcertifyCollect prpLcertifyCollect) {
		this.prpLcertifyCollect = prpLcertifyCollect;
	}

	public List<PrpLcertifyImg> getPrpLcertifyImgList() {
		return prpLcertifyImgList;
	}

	public void setPrpLcertifyImgList(List<PrpLcertifyImg> prpLcertifyImgList) {
		this.prpLcertifyImgList = prpLcertifyImgList;
	}

	public List<PrpLcertifyDirect> getPrpLcertifyDirectList() {
		return prpLcertifyDirectList;
	}

	public void setPrpLcertifyDirectList(List<PrpLcertifyDirect> prpLcertifyDirectList) {
		this.prpLcertifyDirectList = prpLcertifyDirectList;
	}

	public List<PrpLqualityCheck> getPrpLqualityCheckList() {
		return prpLqualityCheckList;
	}

	public void setPrpLqualityCheckList(List<PrpLqualityCheck> prpLqualityCheckList) {
		this.prpLqualityCheckList = prpLqualityCheckList;
	}

	public PrpLclaimStatus getPrpLclaimStatus() {
		return prpLclaimStatus;
	}

	public void setPrpLclaimStatus(PrpLclaimStatus prpLclaimStatus) {
		this.prpLclaimStatus = prpLclaimStatus;
	}

	public List<PrpLregistExt> getPrpLregistExtList() {
		return prpLregistExtList;
	}

	public void setPrpLregistExtList(List<PrpLregistExt> prpLregistExtList) {
		this.prpLregistExtList = prpLregistExtList;
	}

	public List<PrpLcertifyPayee> getPrpLcertifyPayeeList() {
		return prpLcertifyPayeeList;
	}

	public void setPrpLcertifyPayeeList(List<PrpLcertifyPayee> prpLcertifyPayeeList) {
		this.prpLcertifyPayeeList = prpLcertifyPayeeList;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public PrpLclaim getPrpLclaim() {
		return prpLclaim;
	}

	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}

}
