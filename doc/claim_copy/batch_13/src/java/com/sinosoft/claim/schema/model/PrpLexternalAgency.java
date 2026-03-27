package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.claim.dto.custom.TurnPageDto;

/**
 * POJO类
 */
@Entity
@Table(name = "PRPLEXTERNALAGENCY")
public class PrpLexternalAgency implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLexternalAgencyId id;

	/** 属性机构名称 */
	private String comcname;

	/** 属性机构英文名 */
	private String comename;

	/** 属性JURIDICALPERSON */
	private String juridicalperson;

	/** 属性地址 */
	private String address;

	/** 属性电话 */
	private String telephone;

	/** 属性传真 */
	private String faxno;

	/** 属性网址 */
	private String webaddress;

	/** 属性??人 */
	private String linkerName;

	/** 属性联系人电话 */
	private String linkernametel;

	/** 属性联系人邮箱 */
	private String linkeremail;

	/** 属性SPECIALTY */
	private String specialty;

	/** 属性CREATORCODE */
	private String creatorcode;

	/** 属性CREATETIME */
	private Date createtime;

	/** 属性UPDATERCODE */
	private String updatercode;

	/** 属性更新日期 */
	private Date updatetime;

	/** 属性批单生效日期 */
	private Date validDate;

	/** 属性INVALIDDATE */
	private Date invaliddate;

	/** 属性有效状态 */
	private String validStatus;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性银行帳号 */
	private String accountCode;

	/** 属性总行代码 */
	private String bankCode;

	/** 属性总行名称 */
	private String bankName;

	/** 属性分行名称 */
	private String customBankName;

	/** 属性分行代码 */
	private String customBankCode;

	/** 属性帳户归属人证件代码 */
	private String certifiCateCode;

	/** 属性帳户归属人名称 */
	private String ownerName;

	/** 属性帳户归属人电话 */
	private String ownerPhoneNo;

	/** 属性帳户类型 */
	private String accountType;

	/** 属性帳户币别 */
	private String accountCurrency;
	private TurnPageDto turnPageDto = null;
	private String editType = null;

	/** 属性代理商NO */
	private String agentNo;
	/** 属性PO BOX */
	private String postCode;
	/** 属性歸屬公司 */
	private String vestingCom;
	/** 属性國別 */
	private String countryType;
	/** 属性地區別 */
	private String areaCode;
	/** 属性地址2 */
	private String address2;

	/**
	 * 类的默认构造方法
	 */
	public PrpLexternalAgency() {
		id = new PrpLexternalAgencyId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")), @AttributeOverride(name = "comtype", column = @Column(name = "COMTYPE")) })
	public PrpLexternalAgencyId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLexternalAgencyId id) {
		this.id = id;
	}

	@Column(name = "AGENTNO")
	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	@Column(name = "POSTCODE")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "VESTINGCOM")
	public String getVestingCom() {
		return vestingCom;
	}

	public void setVestingCom(String vestingCom) {
		this.vestingCom = vestingCom;
	}

	@Column(name = "COUNTRYTYPE")
	public String getCountryType() {
		return countryType;
	}

	public void setCountryType(String countryType) {
		this.countryType = countryType;
	}

	@Column(name = "AREACODE")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "ADDRESS2")
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * 属性机构名称的getter方法
	 */

	@Column(name = "COMCNAME")
	public String getComcname() {
		return this.comcname;
	}

	/**
	 * 属性机构名称的setter方法
	 */
	public void setComcname(String comcname) {
		this.comcname = comcname;
	}

	/**
	 * 属性机构英文名的getter方法
	 */

	@Column(name = "COMENAME")
	public String getComename() {
		return this.comename;
	}

	/**
	 * 属性机构英文名的setter方法
	 */
	public void setComename(String comename) {
		this.comename = comename;
	}

	/**
	 * 属性JURIDICALPERSON的getter方法
	 */

	@Column(name = "JURIDICALPERSON")
	public String getJuridicalperson() {
		return this.juridicalperson;
	}

	/**
	 * 属性JURIDICALPERSON的setter方法
	 */
	public void setJuridicalperson(String juridicalperson) {
		this.juridicalperson = juridicalperson;
	}

	/**
	 * 属性地址的getter方法
	 */

	@Column(name = "ADDRESS")
	public String getAddress() {
		return this.address;
	}

	/**
	 * 属性地址的setter方法
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 属性电话的getter方法
	 */

	@Column(name = "TELEPHONE")
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * 属性电话的setter方法
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 属性传真的getter方法
	 */

	@Column(name = "FAXNO")
	public String getFaxno() {
		return this.faxno;
	}

	/**
	 * 属性传真的setter方法
	 */
	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	/**
	 * 属性网址的getter方法
	 */

	@Column(name = "WEBADDRESS")
	public String getWebaddress() {
		return this.webaddress;
	}

	/**
	 * 属性网址的setter方法
	 */
	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	/**
	 * 属性??人的getter方法
	 */

	@Column(name = "LINKERNAME")
	public String getLinkerName() {
		return this.linkerName;
	}

	/**
	 * 属性??人的setter方法
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	/**
	 * 属性联系人电话的getter方法
	 */

	@Column(name = "LINKERNAMETEL")
	public String getLinkernametel() {
		return this.linkernametel;
	}

	/**
	 * 属性联系人电话的setter方法
	 */
	public void setLinkernametel(String linkernametel) {
		this.linkernametel = linkernametel;
	}

	/**
	 * 属性联系人邮箱的getter方法
	 */

	@Column(name = "LINKEREMAIL")
	public String getLinkeremail() {
		return this.linkeremail;
	}

	/**
	 * 属性联系人邮箱的setter方法
	 */
	public void setLinkeremail(String linkeremail) {
		this.linkeremail = linkeremail;
	}

	/**
	 * 属性SPECIALTY的getter方法
	 */

	@Column(name = "SPECIALTY")
	public String getSpecialty() {
		return this.specialty;
	}

	/**
	 * 属性SPECIALTY的setter方法
	 */
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	/**
	 * 属性CREATORCODE的getter方法
	 */

	@Column(name = "CREATORCODE")
	public String getCreatorcode() {
		return this.creatorcode;
	}

	/**
	 * 属性CREATORCODE的setter方法
	 */
	public void setCreatorcode(String creatorcode) {
		this.creatorcode = creatorcode;
	}

	/**
	 * 属性CREATETIME的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME")
	public Date getCreatetime() {
		return this.createtime;
	}

	/**
	 * 属性CREATETIME的setter方法
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 属性UPDATERCODE的getter方法
	 */

	@Column(name = "UPDATERCODE")
	public String getUpdatercode() {
		return this.updatercode;
	}

	/**
	 * 属性UPDATERCODE的setter方法
	 */
	public void setUpdatercode(String updatercode) {
		this.updatercode = updatercode;
	}

	/**
	 * 属性更新日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATETIME")
	public Date getUpdatetime() {
		return this.updatetime;
	}

	/**
	 * 属性更新日期的setter方法
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * 属性追回日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDDATE")
	public Date getValidDate() {
		return this.validDate;
	}

	/**
	 * 属性追回日期的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * 属性INVALIDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INVALIDDATE")
	public Date getInvaliddate() {
		return this.invaliddate;
	}

	/**
	 * 属性INVALIDDATE的setter方法
	 */
	public void setInvaliddate(Date invaliddate) {
		this.invaliddate = invaliddate;
	}

	/**
	 * 属性有效状态的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效状态的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
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

	/**
	 * 属性银行帳号的getter方法
	 */

	@Column(name = "ACCOUNTCODE")
	public String getAccountCode() {
		return this.accountCode;
	}

	/**
	 * 属性银行帳号的setter方法
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * 属性总行代码的getter方法
	 */

	@Column(name = "BANKCODE")
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * 属性总行代码的setter方法
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 属性开户行的getter方法
	 */

	@Column(name = "BANKNAME")
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * 属性开户行的setter方法
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * 属性CUSTOMBANKNAME的getter方法
	 */

	@Column(name = "CUSTOMBANKNAME")
	public String getCustomBankName() {
		return this.customBankName;
	}

	/**
	 * 属性CUSTOMBANKCODE的setter方法
	 */
	public void setCustomBankCode(String customBankCode) {
		this.customBankCode = customBankCode;
	}

	/**
	 * 属性CUSTOMBANKCODE的getter方法
	 */

	@Column(name = "CUSTOMBANKCODE")
	public String getCustomBankCode() {
		return this.customBankCode;
	}

	/**
	 * 属性CUSTOMBANKNAME的setter方法
	 */
	public void setCustomBankName(String customBankName) {
		this.customBankName = customBankName;
	}

	/**
	 * 属性帳户归属人证件代码的getter方法
	 */

	@Column(name = "CERTIFICATECODE")
	public String getCertifiCateCode() {
		return this.certifiCateCode;
	}

	/**
	 * 属性帳户归属人证件代码的setter方法
	 */
	public void setCertifiCateCode(String certifiCateCode) {
		this.certifiCateCode = certifiCateCode;
	}

	/**
	 * 属性帳户归属人名称的getter方法
	 */

	@Column(name = "OWNERNAME")
	public String getOwnerName() {
		return this.ownerName;
	}

	/**
	 * 属性帳户归属人名称的setter方法
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * 属性帳户归属人电话的getter方法
	 */

	@Column(name = "OWNERPHONENO")
	public String getOwnerPhoneNo() {
		return this.ownerPhoneNo;
	}

	/**
	 * 属性帳户归属人电话的setter方法
	 */
	public void setOwnerPhoneNo(String ownerPhoneNo) {
		this.ownerPhoneNo = ownerPhoneNo;
	}

	/**
	 * 属性帳户类型的getter方法
	 */

	@Column(name = "ACCOUNTTYPE")
	public String getAccountType() {
		return this.accountType;
	}

	/**
	 * 属性帳户类型的setter方法
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * 属性帳户币别的getter方法
	 */

	@Column(name = "ACCOUNTCURRENCY")
	public String getAccountCurrency() {
		return this.accountCurrency;
	}

	/**
	 * 属性帳户币别的setter方法
	 */
	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	@Transient
	public TurnPageDto getTurnPageDto() {
		return turnPageDto;
	}

	public void setTurnPageDto(TurnPageDto turnPageDto) {
		this.turnPageDto = turnPageDto;
	}

	@Transient
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

}
