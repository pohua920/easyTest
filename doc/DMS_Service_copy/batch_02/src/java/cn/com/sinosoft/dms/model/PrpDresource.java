package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类prpDresource
 */
@Entity
@Table(name = "prpdresource")
public class PrpDresource implements java.io.Serializable {
	private static final long	serialVersionUID	= 1L;

	/** 属性专管专营代码 */
	private String				resourceCode;

	/** 属性专管专营名称 */
	private String				resourceName;

	/** 属性项目代码 */
	private String				projectCode;

	/** 属性渠道代码 */
	private String				agentCode;

	/** 属性归属机构代码 */
	private String				comCode;

	/** 属性合作单位名称 */
	private String				companyName;

	/** 属性合作单位地址 */
	private String				companyAddr;

	/** 属性邮政编码 */
	private String				postCode;

	/** 属性联系人 */
	private String				linkerName;

	/** 属性电话 */
	private String				phoneNumber;

	/** 属性传真 */
	private String				faxNumber;

	/** 属性专营团队负责人姓名 */
	private String				managerName;

	/** 属性专营团队负责人办公电话 */
	private String				managerPhone;

	/** 属性专营团队负责人手机 */
	private String				managerMobile;

	/** 属性专营团队负责人邮件 */
	private String				managerEmail;

	/** 属性备注 */
	private String				remark;

	/** 属性有效状态(0无效1有效) */
	private String				validStatus;

	/** 属性标志字段 */
	private String				flag;

	/**
	 * 类prpDresource的默认构造方法
	 */
	public PrpDresource() {
	}

	/**       
	 * 属性专管专营代码的getter方法
	 */
	@Id
	@Column(name = "resourcecode")
	public String getResourceCode() {
		return this.resourceCode;
	}

	/**       
	 * 属性专管专营代码的setter方法
	 */
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	/**       
	 * 属性专管专营名称的getter方法
	 */

	@Column(name = "resourcename")
	public String getResourceName() {
		return this.resourceName;
	}

	/**       
	 * 属性专管专营名称的setter方法
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**       
	 * 属性项目代码的getter方法
	 */

	@Column(name = "projectcode")
	public String getProjectCode() {
		return this.projectCode;
	}

	/**       
	 * 属性项目代码的setter方法
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**       
	 * 属性渠道代码的getter方法
	 */

	@Column(name = "agentcode")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**       
	 * 属性渠道代码的setter方法
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**       
	 * 属性归属机构代码的getter方法
	 */

	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}

	/**       
	 * 属性归属机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**       
	 * 属性合作单位名称的getter方法
	 */

	@Column(name = "companyname")
	public String getCompanyName() {
		return this.companyName;
	}

	/**       
	 * 属性合作单位名称的setter方法
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**       
	 * 属性合作单位地址的getter方法
	 */

	@Column(name = "companyaddr")
	public String getCompanyAddr() {
		return this.companyAddr;
	}

	/**       
	 * 属性合作单位地址的setter方法
	 */
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	/**       
	 * 属性邮政编码的getter方法
	 */

	@Column(name = "postcode")
	public String getPostCode() {
		return this.postCode;
	}

	/**       
	 * 属性邮政编码的setter方法
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**       
	 * 属性联系人的getter方法
	 */

	@Column(name = "linkername")
	public String getLinkerName() {
		return this.linkerName;
	}

	/**       
	 * 属性联系人的setter方法
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	/**       
	 * 属性电话的getter方法
	 */

	@Column(name = "phonenumber")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**       
	 * 属性电话的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**       
	 * 属性传真的getter方法
	 */

	@Column(name = "faxnumber")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**       
	 * 属性传真的setter方法
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**       
	 * 属性专营团队负责人姓名的getter方法
	 */

	@Column(name = "managername")
	public String getManagerName() {
		return this.managerName;
	}

	/**       
	 * 属性专营团队负责人姓名的setter方法
	 */
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	/**       
	 * 属性专营团队负责人办公电话的getter方法
	 */

	@Column(name = "managerphone")
	public String getManagerPhone() {
		return this.managerPhone;
	}

	/**       
	 * 属性专营团队负责人办公电话的setter方法
	 */
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	/**       
	 * 属性专营团队负责人手机的getter方法
	 */

	@Column(name = "managermobile")
	public String getManagerMobile() {
		return this.managerMobile;
	}

	/**       
	 * 属性专营团队负责人手机的setter方法
	 */
	public void setManagerMobile(String managerMobile) {
		this.managerMobile = managerMobile;
	}

	/**       
	 * 属性专营团队负责人邮件的getter方法
	 */

	@Column(name = "manageremail")
	public String getManagerEmail() {
		return this.managerEmail;
	}

	/**       
	 * 属性专营团队负责人邮件的setter方法
	 */
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	/**       
	 * 属性备注的getter方法
	 */

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	/**       
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**       
	 * 属性有效状态(0无效1有效)的getter方法
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * 属性有效状态(0无效1有效)的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * 属性标志字段的getter方法
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
