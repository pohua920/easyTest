package cn.com.sinosoft.dms.model;
// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpDcode
 */
@Entity
@Table(name = "prpdcode")
public class PrpDcode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流程动作标识 */
	private PrpDcodeId id;

	/** 属性业务代码中文含义 */
	private String codeCName;

	/** 属性业务代码英文含义 */
	private String codeEName;

	/** 属性新的代码类型 */
	private String newCodeType;

	/** 属性新的业务代码 */
	private String newCodeCode;

	/** 属性有效状态(0无效1有效) */
	private String validStatus;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpDcode的默认构造方法
	 */
	public PrpDcode() {
	}

	/**       
	 * 属性流程动作标识的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "codeType", column = @Column(name = "codetype")),
			@AttributeOverride(name = "codeCode", column = @Column(name = "codecode")) })
	public PrpDcodeId getId() {
		return this.id;
	}

	/**       
	 * 属性流程动作标识的setter方法
	 */
	public void setId(PrpDcodeId id) {
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
