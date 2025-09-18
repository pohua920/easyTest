package com.tlg.db2.entity;

import java.math.BigDecimal;

import com.tlg.iBatis.IBatisBaseEntity;

/**
 * mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
 * @author dp0706
 *
 */
public class As400FilToRptCoredata  extends IBatisBaseEntity<String>{
	
	private static final long serialVersionUID = 1L;
	
	private String polDate;
	
	private String policyNo;
	
	private String endorseNo;
	
	private String inType;
	
	private String type;
	
	private String insUid;
	
	private String insuName;
	
	private String effDate;
	
	private String startPeriodDate;
	
	private String endPeriodDate;
	
	private BigDecimal totalPrem;

	private String channelId;

	private String channelName;

	private String chnlAgentBussNo;
	
	private String chnlAgentName;

	private String polAgentId;

	private String polAgentName;

	public String getPolDate() {
		return polDate;
	}

	public void setPolDate(String polDate) {
		this.polDate = polDate;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getEndorseNo() {
		return endorseNo;
	}

	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInsUid() {
		return insUid;
	}

	public void setInsUid(String insUid) {
		this.insUid = insUid;
	}

	public String getInsuName() {
		return insuName;
	}

	public void setInsuName(String insuName) {
		this.insuName = insuName;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getStartPeriodDate() {
		return startPeriodDate;
	}

	public void setStartPeriodDate(String startPeriodDate) {
		this.startPeriodDate = startPeriodDate;
	}

	public String getEndPeriodDate() {
		return endPeriodDate;
	}

	public void setEndPeriodDate(String endPeriodDate) {
		this.endPeriodDate = endPeriodDate;
	}

	public BigDecimal getTotalPrem() {
		return totalPrem;
	}

	public void setTotalPrem(BigDecimal totalPrem) {
		this.totalPrem = totalPrem;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChnlAgentBussNo() {
		return chnlAgentBussNo;
	}

	public void setChnlAgentBussNo(String chnlAgentBussNo) {
		this.chnlAgentBussNo = chnlAgentBussNo;
	}

	public String getChnlAgentName() {
		return chnlAgentName;
	}

	public void setChnlAgentName(String chnlAgentName) {
		this.chnlAgentName = chnlAgentName;
	}

	public String getPolAgentId() {
		return polAgentId;
	}

	public void setPolAgentId(String polAgentId) {
		this.polAgentId = polAgentId;
	}

	public String getPolAgentName() {
		return polAgentName;
	}

	public void setPolAgentName(String polAgentName) {
		this.polAgentName = polAgentName;
	}

	public String getInType() {
		return inType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}
	
	
	
	
	
	

}
