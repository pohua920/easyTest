package com.tlg.sales.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;

/**mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知 */
@SuppressWarnings("serial")
public class PrpdagentLoginDateReserve extends IBatisBaseEntity<BigDecimal> {
	
	private String agentCode;//中介代碼
	
	private String certiNo;//證件號碼
	
	private String loginCode;//登錄代碼
	
	private String businessSource;//業務來源代碼
	
	private Date loginDate;//登錄日期
	
	private Date loginEndDate;//登錄到日期
	
	private Date createDate;//接單日期
	
	private Date runDate;//覆蓋時間後戳記時間
	
	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getCertiNo() {
		return certiNo;
	}

	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getBusinessSource() {
		return businessSource;
	}

	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}


	public Date getLoginEndDate() {
		return loginEndDate;
	}

	public void setLoginEndDate(Date loginEndDate) {
		this.loginEndDate = loginEndDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}
	
}