package com.sinosoft.claim.replevy.vo;

import ins.framework.common.DateTime;
import ins.framework.utils.DataUtils;

import java.io.Serializable;

import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.SwfLog;

/**
 * 追偿审核讯息临时存储对象
 * @author 中科软
 *
 */
public class ReplevyUndwrtDto extends PrpLcompensate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String START = "Broker";//出單員ActorId
	/** 追偿计算书 */
	private String compensateNo;
	private String claimNo;
	private String policyNo;
	private String operatorCode;
	private String operatorName;
	private String flowID = "";
	private Integer logNo = 0;
	private String nodeName = "";
	private String handlerCode = "0";
	private String handlerName = "";
	private String flowInTime = "";

	public ReplevyUndwrtDto() {
	}
	
	public ReplevyUndwrtDto(PrpLcompensate prpLcompensate, SwfLog swfLog) {
		if (prpLcompensate != null) {
			this.compensateNo = prpLcompensate.getCompensateNo();
			this.claimNo = prpLcompensate.getClaimNo();
			this.policyNo = prpLcompensate.getPolicyNo();
			this.operatorCode = prpLcompensate.getOperatorCode();
			this.operatorName = prpLcompensate.getOperatorName();
			this.flowInTime = new DateTime(prpLcompensate.getInputDate(), DateTime.YEAR_TO_DAY).toString();
			if (swfLog != null) {
				this.flowID = swfLog.getId().getFlowID();
				this.logNo = swfLog.getId().getLogNo();
				this.nodeName = swfLog.getNodeName();
				if(!"0".equals(swfLog.getHandlerCode())){
					this.handlerCode = swfLog.getHandlerCode();
					this.handlerName = DataUtils.dbNullToEmpty(swfLog.getHandlerName());
				}
				this.flowInTime = swfLog.getFlowInTime();
			}
		}
	}

	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getFlowID() {
		return flowID;
	}

	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	public Integer getLogNo() {
		return logNo;
	}

	public void setLogNo(Integer logNo) {
		this.logNo = logNo;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getFlowInTime() {
		return flowInTime;
	}

	public void setFlowInTime(String flowInTime) {
		this.flowInTime = flowInTime;
	}
}
