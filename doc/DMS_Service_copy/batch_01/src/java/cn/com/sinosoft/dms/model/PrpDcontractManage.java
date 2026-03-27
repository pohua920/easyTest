package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类prpDcontractManage
 */
@Entity
@Table(name = "prpdcontractmanage")
public class PrpDcontractManage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流程动作标识 */
	private PrpDcontractManageId id;

	/** 属性合同起始日期 */
	private Date contractStartDate;

	/** 属性合同终止日期 */
	private Date contractEndDate;

	/** 属性许可证号 */
	private String permitNo;

	/** 属性许可证到期日期 */
	private Date permitEndDate;

	/** 属性许可证号状态 */
	private String permitFlag;

	/** 属性许可证号码 */
	private String licenseNo;

	/** 属性资格证到期日期 */
	private Date licenseEndDate;

	/** 属性展业证号 */
	private String acquisitionNo;

	/** 属性展业证到期日期 */
	private Date acquisitionEndDate;

	/** 属性开户银行 */
	private String bank;

	/** 属性docfee */
	private BigDecimal docfee;

	/** 属性支付账号 */
	private String payAccount;

	/** 属性赋码机构 */
	private String givenCodeCom;

	/** 属性有效状态(0无效1有效) */
	private String validStatus;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类prpDcontractManage的默认构造方法
	 */
	public PrpDcontractManage() {
	}

	/**       
	 * 属性流程动作标识的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "contractObjectType", column = @Column(name = "contractobjecttype")),
			@AttributeOverride(name = "contractObjectCode", column = @Column(name = "contractobjectcode")),
			@AttributeOverride(name = "contractNo", column = @Column(name = "contractno")) })
	public PrpDcontractManageId getId() {
		return this.id;
	}

	/**       
	 * 属性流程动作标识的setter方法
	 */
	public void setId(PrpDcontractManageId id) {
		this.id = id;
	}

	/**       
	 * 属性合同起始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "contractstartdate")
	public Date getContractStartDate() {
		return this.contractStartDate;
	}

	/**       
	 * 属性合同起始日期的setter方法
	 */
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	/**       
	 * 属性合同终止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "contractenddate")
	public Date getContractEndDate() {
		return this.contractEndDate;
	}

	/**       
	 * 属性合同终止日期的setter方法
	 */
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	/**       
	 * 属性许可证号的getter方法
	 */

	@Column(name = "permitno")
	public String getPermitNo() {
		return this.permitNo;
	}

	/**       
	 * 属性许可证号的setter方法
	 */
	public void setPermitNo(String permitNo) {
		this.permitNo = permitNo;
	}

	/**       
	 * 属性许可证到期日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "permitenddate")
	public Date getPermitEndDate() {
		return this.permitEndDate;
	}

	/**       
	 * 属性许可证到期日期的setter方法
	 */
	public void setPermitEndDate(Date permitEndDate) {
		this.permitEndDate = permitEndDate;
	}

	/**       
	 * 属性许可证号状态的getter方法
	 */

	@Column(name = "permitflag")
	public String getPermitFlag() {
		return this.permitFlag;
	}

	/**       
	 * 属性许可证号状态的setter方法
	 */
	public void setPermitFlag(String permitFlag) {
		this.permitFlag = permitFlag;
	}

	/**       
	 * 属性许可证号码的getter方法
	 */

	@Column(name = "licenseno")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**       
	 * 属性许可证号码的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**       
	 * 属性资格证到期日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "licenseenddate")
	public Date getLicenseEndDate() {
		return this.licenseEndDate;
	}

	/**       
	 * 属性资格证到期日期的setter方法
	 */
	public void setLicenseEndDate(Date licenseEndDate) {
		this.licenseEndDate = licenseEndDate;
	}

	/**       
	 * 属性展业证号的getter方法
	 */

	@Column(name = "acquisitionno")
	public String getAcquisitionNo() {
		return this.acquisitionNo;
	}

	/**       
	 * 属性展业证号的setter方法
	 */
	public void setAcquisitionNo(String acquisitionNo) {
		this.acquisitionNo = acquisitionNo;
	}

	/**       
	 * 属性展业证到期日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "acquisitionenddate")
	public Date getAcquisitionEndDate() {
		return this.acquisitionEndDate;
	}

	/**       
	 * 属性展业证到期日期的setter方法
	 */
	public void setAcquisitionEndDate(Date acquisitionEndDate) {
		this.acquisitionEndDate = acquisitionEndDate;
	}

	/**       
	 * 属性开户银行的getter方法
	 */

	@Column(name = "bank")
	public String getBank() {
		return this.bank;
	}

	/**       
	 * 属性开户银行的setter方法
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**       
	 * 属性docfee的getter方法
	 */

	@Column(name = "docfee")
	public BigDecimal getDocfee() {
		return this.docfee;
	}

	/**       
	 * 属性docfee的setter方法
	 */
	public void setDocfee(BigDecimal docfee) {
		this.docfee = docfee;
	}

	/**       
	 * 属性支付账号的getter方法
	 */

	@Column(name = "payaccount")
	public String getPayAccount() {
		return this.payAccount;
	}

	/**       
	 * 属性支付账号的setter方法
	 */
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	/**       
	 * 属性赋码机构的getter方法
	 */

	@Column(name = "givencodecom")
	public String getGivenCodeCom() {
		return this.givenCodeCom;
	}

	/**       
	 * 属性赋码机构的setter方法
	 */
	public void setGivenCodeCom(String givenCodeCom) {
		this.givenCodeCom = givenCodeCom;
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
