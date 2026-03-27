package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLbackVisit
 */
@Entity
@Table(name = "PRPLBACKVISIT")
public class PrpLbackVisit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLbackVisitId id;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性被保险人代码 */
	private String insuredCode;

	/** 属性被保险人名称 */
	private String insuredName;

	/** 属性客户类型 */
	private String customType;

	/** 属性联系电话 */
	private String phone;

	/** 属性传真 */
	private String fax;

	/** 属性邮政编码 */
	private String postCode;

	/** 属性电子邮件 */
	private String email;

	/** 属性联系人 */
	private String linkerName;

	/** 属性联系地址 */
	private String address;

	/** 属性处理人员代码 */
	private String handlerCode;

	/** 属性处理人员名称 */
	private String handlerName;

	/** 属性处理部门 */
	private String handleDept;

	/** 属性回访时间 */
	private String backVisitTime;

	/** 属性客户满意度 */
	private String customOpinion;

	/** 属性转接部门 */
	private String deliverDepart;

	/** 属性转接时间 */
	private String deliverTime;

	/** 属性转接经办人 */
	private String deliverPerson;

	/** 属性反馈时间 */
	private String deliverBackTime;

	/** 属性答复客户时间 */
	private String backCustomTime;

	/** 属性催办标志 */
	private String hurryFlag;

	/** 属性回访状态 */
	private String status;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLbackVisit的默认构造方法
	 */
	public PrpLbackVisit() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "backVisitID", column = @Column(name = "BACKVISITID")), @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")),
			@AttributeOverride(name = "backVisitType", column = @Column(name = "BACKVISITTYPE")) })
	public PrpLbackVisitId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLbackVisitId id) {
		this.id = id;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性车牌号码的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号码的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 属性被保险人代码的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性被保险人代码的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性被保险人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保险人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性客户类型的getter方法
	 */

	@Column(name = "CUSTOMTYPE")
	public String getCustomType() {
		return this.customType;
	}

	/**
	 * 属性客户类型的setter方法
	 */
	public void setCustomType(String customType) {
		this.customType = customType;
	}

	/**
	 * 属性联系电话的getter方法
	 */

	@Column(name = "PHONE")
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 属性联系电话的setter方法
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 属性传真的getter方法
	 */

	@Column(name = "FAX")
	public String getFax() {
		return this.fax;
	}

	/**
	 * 属性传真的setter方法
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * 属性邮政编码的getter方法
	 */

	@Column(name = "POSTCODE")
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
	 * 属性电子邮件的getter方法
	 */

	@Column(name = "EMAIL")
	public String getEmail() {
		return this.email;
	}

	/**
	 * 属性电子邮件的setter方法
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 属性联系人的getter方法
	 */

	@Column(name = "LINKERNAME")
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
	 * 属性联系地址的getter方法
	 */

	@Column(name = "ADDRESS")
	public String getAddress() {
		return this.address;
	}

	/**
	 * 属性联系地址的setter方法
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 属性处理人员代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性处理人员代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性处理人员名称的getter方法
	 */

	@Column(name = "HANDLERNAME")
	public String getHandlerName() {
		return this.handlerName;
	}

	/**
	 * 属性处理人员名称的setter方法
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * 属性处理部门的getter方法
	 */

	@Column(name = "HANDLEDEPT")
	public String getHandleDept() {
		return this.handleDept;
	}

	/**
	 * 属性处理部门的setter方法
	 */
	public void setHandleDept(String handleDept) {
		this.handleDept = handleDept;
	}

	/**
	 * 属性回访时间的getter方法
	 */

	@Column(name = "BACKVISITTIME")
	public String getBackVisitTime() {
		return this.backVisitTime;
	}

	/**
	 * 属性回访时间的setter方法
	 */
	public void setBackVisitTime(String backVisitTime) {
		this.backVisitTime = backVisitTime;
	}

	/**
	 * 属性客户满意度的getter方法
	 */

	@Column(name = "CUSTOMOPINION")
	public String getCustomOpinion() {
		return this.customOpinion;
	}

	/**
	 * 属性客户满意度的setter方法
	 */
	public void setCustomOpinion(String customOpinion) {
		this.customOpinion = customOpinion;
	}

	/**
	 * 属性转接部门的getter方法
	 */

	@Column(name = "DELIVERDEPART")
	public String getDeliverDepart() {
		return this.deliverDepart;
	}

	/**
	 * 属性转接部门的setter方法
	 */
	public void setDeliverDepart(String deliverDepart) {
		this.deliverDepart = deliverDepart;
	}

	/**
	 * 属性转接时间的getter方法
	 */

	@Column(name = "DELIVERTIME")
	public String getDeliverTime() {
		return this.deliverTime;
	}

	/**
	 * 属性转接时间的setter方法
	 */
	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	/**
	 * 属性转接经办人的getter方法
	 */

	@Column(name = "DELIVERPERSON")
	public String getDeliverPerson() {
		return this.deliverPerson;
	}

	/**
	 * 属性转接经办人的setter方法
	 */
	public void setDeliverPerson(String deliverPerson) {
		this.deliverPerson = deliverPerson;
	}

	/**
	 * 属性反馈时间的getter方法
	 */

	@Column(name = "DELIVERBACKTIME")
	public String getDeliverBackTime() {
		return this.deliverBackTime;
	}

	/**
	 * 属性反馈时间的setter方法
	 */
	public void setDeliverBackTime(String deliverBackTime) {
		this.deliverBackTime = deliverBackTime;
	}

	/**
	 * 属性答复客户时间的getter方法
	 */

	@Column(name = "BACKCUSTOMTIME")
	public String getBackCustomTime() {
		return this.backCustomTime;
	}

	/**
	 * 属性答复客户时间的setter方法
	 */
	public void setBackCustomTime(String backCustomTime) {
		this.backCustomTime = backCustomTime;
	}

	/**
	 * 属性催办标志的getter方法
	 */

	@Column(name = "HURRYFLAG")
	public String getHurryFlag() {
		return this.hurryFlag;
	}

	/**
	 * 属性催办标志的setter方法
	 */
	public void setHurryFlag(String hurryFlag) {
		this.hurryFlag = hurryFlag;
	}

	/**
	 * 属性回访状态的getter方法
	 */

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	/**
	 * 属性回访状态的setter方法
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 属性标志字段的getter方法
	 */

	@Column(name = "FLAG")
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
