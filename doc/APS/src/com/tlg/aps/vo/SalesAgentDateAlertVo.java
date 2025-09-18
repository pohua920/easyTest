package com.tlg.aps.vo;

import java.util.Date;

/** mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 */
public class SalesAgentDateAlertVo {
	private String agentcode;
	private String mobile;
	private String email;
	private String agentname;
	private String logincode;
	private Date loginenddate;
	private Date maillog;
	
	
	public String getAgentcode() {
		return agentcode;
	}
	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAgentname() {
		return agentname;
	}
	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	public String getLogincode() {
		return logincode;
	}
	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}
	public Date getLoginenddate() {
		return loginenddate;
	}
	public void setLoginenddate(Date loginenddate) {
		this.loginenddate = loginenddate;
	}
	public Date getMaillog() {
		return maillog;
	}
	public void setMaillog(Date maillog) {
		this.maillog = maillog;
	}
}
