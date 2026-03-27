package cn.com.sinosoft.dms.model;

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

/**
 * POJO类prpdnewcodecom
 */
@Entity
@Table(name = "prpdnewcodecom")
public class PrpDnewCodeCom implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDnewCodeComId id;

	/** 属性业务代码中文含义 */
	private String codeCName;

	/** 属性业务代码英文含义 */
	private String codeEName;

	/** 属性uppercode */
	private String upperCode;

	/** 属性新的业务代码 */
	private String newCodeCode;

	/** 属性旧业务代码 */
	private String oldCodeCode;

	/** 属性是否常用代码 */
	private String commonFlag;

	/** 属性生效日期 */
	private Date validDate;

	/** 属性失效日期 */
	private Date invalidDate;

	/** 属性有效状态(0无效1有效) */
	private String validStatus;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类prpdnewcode的默认构造方法
	 */
	public PrpDnewCodeCom() {
	}

	/**       
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "codeType", column = @Column(name = "codetype")),
			@AttributeOverride(name = "comCode", column = @Column(name = "comcode")),
			@AttributeOverride(name = "codeCode", column = @Column(name = "codecode")) })
	public PrpDnewCodeComId getId() {
		return this.id;
	}

	/**       
	 * 属性id的setter方法
	 */
	public void setId(PrpDnewCodeComId id) {
		this.id = id;
	}

	/**       
	 * 属性业务代码中文含义的getter方法
	 */

	@Column(name = "codecname")
	public String getCodeCName() {
		return this.codeCName;
	}

	/**       
	 * 属性业务代码中文含义的setter方法
	 */
	public void setCodeCName(String codeCName) {
		this.codeCName = codeCName;
	}

	/**       
	 * 属性业务代码英文含义的getter方法
	 */

	@Column(name = "codeename")
	public String getCodeEName() {
		return this.codeEName;
	}

	/**       
	 * 属性业务代码英文含义的setter方法
	 */
	public void setCodeEName(String codeEName) {
		this.codeEName = codeEName;
	}

	/**       
	 * 属性uppercode的getter方法
	 */

	@Column(name = "uppercode")
	public String getUpperCode() {
		return this.upperCode;
	}

	/**       
	 * 属性uppercode的setter方法
	 */
	public void setUpperCode(String uppercode) {
		this.upperCode = uppercode;
	}

	/**       
	 * 属性新的业务代码的getter方法
	 */

	@Column(name = "newcodecode")
	public String getNewCodeCode() {
		return this.newCodeCode;
	}

	/**       
	 * 属性新的业务代码的setter方法
	 */
	public void setNewCodeCode(String newCodeCode) {
		this.newCodeCode = newCodeCode;
	}

	/**       
	 * 属性旧业务代码的getter方法
	 */

	@Column(name = "oldcodecode")
	public String getOldCodeCode() {
		return this.oldCodeCode;
	}

	/**       
	 * 属性旧业务代码的setter方法
	 */
	public void setOldCodeCode(String oldCodeCode) {
		this.oldCodeCode = oldCodeCode;
	}

	/**       
	 * 属性是否常用代码的getter方法
	 */

	@Column(name = "commonflag")
	public String getCommonFlag() {
		return this.commonFlag;
	}

	/**       
	 * 属性是否常用代码的setter方法
	 */
	public void setCommonFlag(String commonFlag) {
		this.commonFlag = commonFlag;
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

}
