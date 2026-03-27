/**
 * 2014-6-13
 */
package com.sinosoft.claim.print.vo;

/**
 * 工程险  理賠申請書  数据对象 
 * @author 中科軟
 */
public class GAAClaimApplicationObject {
	private String className;
	private String claimNo;
	private String policyNo;
	private String startDate;
	private String endDate;
	private String insuredName;
	/**工程名稱:承保端承保工程述要  prpCmainConstruct.constructName */
	private String projectName;
	private String damageStartDate;
	/**備案中第一聯繫人姓名  prpLregist.linkerName*/
	private String linkman;
	/**備案中第一聯繫人聯繫電話 prpLregist.linkPhone*/
	private String linkPhone;
	/**立案中的出險地點 PrpLclaim.damageAddress*/
	private String damageAddress;
	/**立案中的出險原因 PrpLclaim.damageName*/
	private String damageName;
	/**保险金额*/
	private String sumAmount;
	 
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getDamageAddress() {
		return damageAddress;
	}
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}
	public String getDamageName() {
		return damageName;
	}
	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}

}
