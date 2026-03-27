package cn.com.sinosoft.dms.model;
// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDnewCodeId
 */
@Embeddable
public class PrpDnewCodeId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性代码类型(1.条款 2.框架 3.产品 4.方案 5.参考资料) */
	private String codeType;

	/** 属性业务代码 */
	private String codeCode;

	/**
	 * 类PrpDnewCodeId的默认构造方法
	 */
	public PrpDnewCodeId() {
	}

	/**       
	 * 属性代码类型(1.条款 2.框架 3.产品 4.方案 5.参考资料)的getter方法
	 */

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
	 * 属性业务代码的getter方法
	 */

	@Column(name = "codecode")
	public String getCodeCode() {
		return this.codeCode;
	}

	/**       
	 * 属性业务代码的setter方法
	 */
	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDnewCodeId))
			return false;
		PrpDnewCodeId castOther = (PrpDnewCodeId) other;

		return ((this.getCodeType() == castOther.getCodeType()) || (this.getCodeType() != null
				&& castOther.getCodeType() != null && this.getCodeType().equals(castOther.getCodeType())))
				&& ((this.getCodeCode() == castOther.getCodeCode()) || (this.getCodeCode() != null
						&& castOther.getCodeCode() != null && this.getCodeCode().equals(castOther.getCodeCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCodeType() == null ? 0 : this.getCodeType().hashCode());
		result = 37 * result + (getCodeCode() == null ? 0 : this.getCodeCode().hashCode());
		return result;
	}

}
