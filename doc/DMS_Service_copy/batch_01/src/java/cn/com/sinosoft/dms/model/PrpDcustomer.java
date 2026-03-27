package cn.com.sinosoft.dms.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpDcustomer
 */
@Entity
@Table(name = "PRPDCUSTOMER")
public class PrpDcustomer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性客户代码 */
	private String customerCode;

	/** 属性客户类型(1个人/2集体) */
	private String customerType;

	/** 属性速查索引码 */
	private String shortHandCode;

	/** 属性客户中文名称 */
	private String customerCName;

	/** 属性客户英文名称 */
	private String customerEName;

	/** 属性地址中文名称 */
	private String addressCName;

	/** 属性地址英文名称 */
	private String addressEName;

	/** 属性法人组织机构代码 个人身份证号码 */
	private String organizeCode;

	/** 属性上级客户代码 */
	private String fatherCode;

	/** 属性黑名单标志 [1]:0:正常 1：黑名单 */
	private String blackState;

	/** 属性客户类型 */
	private String customerKind;

	/** 属性临时/正式标志(0:临时/1:正式 */
	private String customerFlag;

	/** 属性专项代码(对应会计科目 */
	private String articleCode;

	/** 属性效力状态(0失效/1有效) */
	private String validStatus;

	/** 属性备用1 */
	private String customerClass;

	/** 属性备用2 */
	private String customerSubClass;

	/** 属性备用3 */
	private String operatorCode;

	/** 属性备用4 */
	private Date inputDate;

	/** 属性备用5 */
	private Date lastOperateDate;

	/** 属性标记 */
	private String remark;

	/** 属性PRPDCUSTOMERUNIT */
//	private PrpDcustomerUnit prpDcustomerUnit;

	/** 属性PRPDCUSTOMERIDV */
//	private PrpDcustomerIdv prpDcustomerIdv;

	/**
	 * 类PrpDcustomer的默认构造方法
	 */
	public PrpDcustomer() {
	}

	/**
	 * 属性客户代码的getter方法
	 */
	@Id
	@Column(name = "CUSTOMERCODE")
	public String getCustomerCode() {
		return this.customerCode;
	}

	/**
	 * 属性客户代码的setter方法
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 属性客户类型(1个人/2集体)的getter方法
	 */

	@Column(name = "CUSTOMERTYPE")
	public String getCustomerType() {
		return this.customerType;
	}

	/**
	 * 属性客户类型(1个人/2集体)的setter方法
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * 属性速查索引码的getter方法
	 */

	@Column(name = "SHORTHANDCODE")
	public String getShortHandCode() {
		return this.shortHandCode;
	}

	/**
	 * 属性速查索引码的setter方法
	 */
	public void setShortHandCode(String shortHandCode) {
		this.shortHandCode = shortHandCode;
	}

	/**
	 * 属性客户中文名称的getter方法
	 */

	@Column(name = "CUSTOMERCNAME")
	public String getCustomerCName() {
		return this.customerCName;
	}

	/**
	 * 属性客户中文名称的setter方法
	 */
	public void setCustomerCName(String customerCName) {
		this.customerCName = customerCName;
	}

	/**
	 * 属性客户英文名称的getter方法
	 */

	@Column(name = "CUSTOMERENAME")
	public String getCustomerEName() {
		return this.customerEName;
	}

	/**
	 * 属性客户英文名称的setter方法
	 */
	public void setCustomerEName(String customerEName) {
		this.customerEName = customerEName;
	}

	/**
	 * 属性地址中文名称的getter方法
	 */

	@Column(name = "ADDRESSCNAME")
	public String getAddressCName() {
		return this.addressCName;
	}

	/**
	 * 属性地址中文名称的setter方法
	 */
	public void setAddressCName(String addressCName) {
		this.addressCName = addressCName;
	}

	/**
	 * 属性地址英文名称的getter方法
	 */

	@Column(name = "ADDRESSENAME")
	public String getAddressEName() {
		return this.addressEName;
	}

	/**
	 * 属性地址英文名称的setter方法
	 */
	public void setAddressEName(String addressEName) {
		this.addressEName = addressEName;
	}

	/**
	 * 属性法人组织机构代码 个人身份证号码的getter方法
	 */

	@Column(name = "ORGANIZECODE")
	public String getOrganizeCode() {
		return this.organizeCode;
	}

	/**
	 * 属性法人组织机构代码 个人身份证号码的setter方法
	 */
	public void setOrganizeCode(String organizeCode) {
		this.organizeCode = organizeCode;
	}

	/**
	 * 属性上级客户代码的getter方法
	 */

	@Column(name = "FATHERCODE")
	public String getFatherCode() {
		return this.fatherCode;
	}

	/**
	 * 属性上级客户代码的setter方法
	 */
	public void setFatherCode(String fatherCode) {
		this.fatherCode = fatherCode;
	}

	/**
	 * 属性黑名单标志 [1]:0:正常 1：黑名单的getter方法
	 */

	@Column(name = "BLACKSTATE")
	public String getBlackState() {
		return this.blackState;
	}

	/**
	 * 属性黑名单标志 [1]:0:正常 1：黑名单的setter方法
	 */
	public void setBlackState(String blackState) {
		this.blackState = blackState;
	}

	/**
	 * 属性客户类型的getter方法
	 */

	@Column(name = "CUSTOMERKIND")
	public String getCustomerKind() {
		return this.customerKind;
	}

	/**
	 * 属性客户类型的setter方法
	 */
	public void setCustomerKind(String customerKind) {
		this.customerKind = customerKind;
	}

	/**
	 * 属性临时/正式标志(0:临时/1:正式的getter方法
	 */

	@Column(name = "CUSTOMERFLAG")
	public String getCustomerFlag() {
		return this.customerFlag;
	}

	/**
	 * 属性临时/正式标志(0:临时/1:正式的setter方法
	 */
	public void setCustomerFlag(String customerFlag) {
		this.customerFlag = customerFlag;
	}

	/**
	 * 属性专项代码(对应会计科目的getter方法
	 */

	@Column(name = "ARTICLECODE")
	public String getArticleCode() {
		return this.articleCode;
	}

	/**
	 * 属性专项代码(对应会计科目的setter方法
	 */
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	/**
	 * 属性效力状态(0失效/1有效)的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态(0失效/1有效)的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性备用1的getter方法
	 */

	@Column(name = "CUSTOMERCLASS")
	public String getCustomerClass() {
		return this.customerClass;
	}

	/**
	 * 属性备用1的setter方法
	 */
	public void setCustomerClass(String customerClass) {
		this.customerClass = customerClass;
	}

	/**
	 * 属性备用2的getter方法
	 */

	@Column(name = "CUSTOMERSUBCLASS")
	public String getCustomerSubClass() {
		return this.customerSubClass;
	}

	/**
	 * 属性备用2的setter方法
	 */
	public void setCustomerSubClass(String customerSubClass) {
		this.customerSubClass = customerSubClass;
	}

	/**
	 * 属性备用3的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性备用3的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性备用4的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性备用4的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性备用5的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LASTOPERATEDATE")
	public Date getLastOperateDate() {
		return this.lastOperateDate;
	}

	/**
	 * 属性备用5的setter方法
	 */
	public void setLastOperateDate(Date lastOperateDate) {
		this.lastOperateDate = lastOperateDate;
	}

	/**
	 * 属性标记的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性标记的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性PRPDCUSTOMERUNIT的getter方法
	 */
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "prpDcustomer")
//	public PrpDcustomerUnit getPrpDcustomerUnit() {
//		return this.prpDcustomerUnit;
//	}

	/**
	 * 属性PRPDCUSTOMERUNIT的setter方法
//	 */
//	public void setPrpDcustomerUnit(PrpDcustomerUnit prpDcustomerUnit) {
//		this.prpDcustomerUnit = prpDcustomerUnit;
//	}

	/**
	 * 属性PRPDCUSTOMERIDV的getter方法
	 */
//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "prpDcustomer")
//	public PrpDcustomerIdv getPrpDcustomerIdv() {
//		return this.prpDcustomerIdv;
//	}

	/**
	 * 属性PRPDCUSTOMERIDV的setter方法
	 */
//	public void setPrpDcustomerIdv(PrpDcustomerIdv prpDcustomerIdv) {
//		this.prpDcustomerIdv = prpDcustomerIdv;
//	}

}
