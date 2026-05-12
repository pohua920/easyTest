package com.sinosoft.claim.generalClaim.vo;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTaskLog;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.SwfLog;

public class GeneralClaimDto implements java.io.Serializable{
	/** */
	private static final long serialVersionUID = 1L;
	/**  编辑类型 */
	private String actionType="";
	/**  报案表 */
	private PrpLregist prpLregist;
	/**  swflog数据 */
	private List<SwfLog> swflogList;
	/**  处理状态 */
	private List<String> claimStatusList;
	/** 日志讯息  */
	private List<PrpLgeneralClaimTaskLog> prpLgeneralClaimTaskLogList;
	/** 立案讯息  */
	private List<PrpLclaim> prpLclaimList;
	private String flowId = "";
	private String comCode = "";
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public PrpLregist getPrpLregist() {
		return prpLregist;
	}
	public void setPrpLregist(PrpLregist prpLregist) {
		this.prpLregist = prpLregist;
	}
	public List<SwfLog> getSwflogList() {
		return swflogList;
	}
	public void setSwflogList(List<SwfLog> swflogList) {
		this.swflogList = swflogList;
	}
	public List<String> getClaimStatusList() {
		return claimStatusList;
	}
	public void setClaimStatusList(List<String> claimStatusList) {
		this.claimStatusList = claimStatusList;
	}
	public List<PrpLgeneralClaimTaskLog> getPrpLgeneralClaimTaskLogList() {
		return prpLgeneralClaimTaskLogList;
	}
	public void setPrpLgeneralClaimTaskLogList(List<PrpLgeneralClaimTaskLog> prpLgeneralClaimTaskLogList) {
		this.prpLgeneralClaimTaskLogList = prpLgeneralClaimTaskLogList;
	}
	public List<PrpLclaim> getPrpLclaimList() {
		return prpLclaimList;
	}
	public void setPrpLclaimList(List<PrpLclaim> prpLclaimList) {
		this.prpLclaimList = prpLclaimList;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	
}
