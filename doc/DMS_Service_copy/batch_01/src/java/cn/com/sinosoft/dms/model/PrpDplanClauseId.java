package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类prpDplanClauseId
 */
@Embeddable
public class PrpDplanClauseId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性方案代码 */
	private String planCode;

	/** 属性条款代码 */
	private String clauseCode;

	/**
	 * 类prpDplanClauseId的默认构造方法
	 */
	public PrpDplanClauseId() {
	}

	/**       
	 * 属性方案代码的getter方法
	 */

	@Column(name = "plancode")
	public String getPlanCode() {
		return this.planCode;
	}

	/**       
	 * 属性方案代码的setter方法
	 */
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	/**       
	 * 属性条款代码的getter方法
	 */

	@Column(name = "clausecode")
	public String getClauseCode() {
		return this.clauseCode;
	}

	/**       
	 * 属性条款代码的setter方法
	 */
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDplanClauseId))
			return false;
		PrpDplanClauseId castOther = (PrpDplanClauseId) other;

		return ((this.getPlanCode() == castOther.getPlanCode()) || (this
				.getPlanCode() != null
				&& castOther.getPlanCode() != null && this.getPlanCode()
				.equals(castOther.getPlanCode())))
				&& ((this.getClauseCode() == castOther.getClauseCode()) || (this
						.getClauseCode() != null
						&& castOther.getClauseCode() != null && this
						.getClauseCode().equals(castOther.getClauseCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPlanCode() == null ? 0 : this.getPlanCode().hashCode());
		result = 37
				* result
				+ (getClauseCode() == null ? 0 : this.getClauseCode()
						.hashCode());
		return result;
	}

}
