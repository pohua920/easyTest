package com.tlg.sales.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;
/**mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知 */
@SuppressWarnings("serial")
public class PrpdagentIdv extends IBatisBaseEntity<BigDecimal> {

	private String agentCode;//中介代碼
	
	private String certiNo;//證件號碼
	
	private Date loginDate;//登錄日期
	
	private Date loginEndDate;//登錄到日期
	
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

	
}