package cn.com.sinosoft.dms.model;


// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类prpDtype
 */
@Entity
@Table(name = "prpdtype")
public class PrpDtype implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性代码类型(1.条款 2.框架 3.产品 4.方案 5.参考资料) */
	private String codeType;

	/** 属性代码类型描述 */
	private String codeTypeDesc;

	/** 属性新的代码类型 */
	private String newCodeType;

	/** 属性生效日期 */
	private Date validDate;

	/** 属性失效日期 */
	private Date invalidDate;

	/** 属性有效状态(0无效1有效) */
	private String validStatus;

	/** 属性标志字段 */
	private String flag;
	
	/** 是否需要审核 add by duanfa 2011-06-22 */
	private String isAudit;

	/** 属性prpDnewCodes */
	private List<PrpDnewCode> prpDnewCodes = new ArrayList<PrpDnewCode>(0);

	/**
	 * 类prpDtype的默认构造方法
	 */
	public PrpDtype() {
	}

	/**       
	 * 属性代码类型(1.条款 2.框架 3.产品 4.方案 5.参考资料)的getter方法
	 */
	@Id
	@Column(name = "codetype")
	public String getCodeType() {
		return this.codeType;
	}

	/**       
	 * 属性代码类型(1.条款 2.框架 3.产品 4.方案 5.参考资料)的setter方法
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**       
	 * 属性代码类型描述的getter方法
	 */

	@Column(name = "codetypedesc")
	public String getCodeTypeDesc() {
		return this.codeTypeDesc;
	}

	/**       
	 * 属性代码类型描述的setter方法
	 */
	public void setCodeTypeDesc(String codeTypeDesc) {
		this.codeTypeDesc = codeTypeDesc;
	}

	/**       
	 * 属性新的代码类型的getter方法
	 */

	@Column(name = "newcodetype")
	public String getNewCodeType() {
		return this.newCodeType;
	}

	/**       
	 * 属性新的代码类型的setter方法
	 */
	public void setNewCodeType(String newCodeType) {
		this.newCodeType = newCodeType;
	}

	/**       
	 * 属性生效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "validdate")
	public Date getValidDate() {
		return this.validDate;
	}

	/**       
	 * 属性生效日期的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**       
	 * 属性失效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "invaliddate")
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	/**       
	 * 属性失效日期的setter方法
	 */
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
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

	/**       
	 * 属性prpDnewCodes的getter方法
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prpDtype")
	public List<PrpDnewCode> getPrpDnewCodes() {
		return this.prpDnewCodes;
	}

	/**       
	 * 属性prpDnewCodes的setter方法
	 */
	public void setPrpDnewCodes(List<PrpDnewCode> prpDnewCodes) {
		this.prpDnewCodes = prpDnewCodes;
	}
	@Column(name = "isaudit")
	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

}
