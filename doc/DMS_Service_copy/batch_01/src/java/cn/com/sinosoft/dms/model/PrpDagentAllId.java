package cn.com.sinosoft.dms.model;
// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDagentAllId
 */
@Embeddable
public class PrpDagentAllId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性渠道代码 */
	private String agentCode;

	/** 属性本地机构代码 */
	private String locateComCode;

	/**
	 * 类PrpDagentAllId的默认构造方法
	 */
	public PrpDagentAllId() {
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
	 * 属性本地机构代码的getter方法
	 */

	@Column(name = "locateComCode")
	public String getLocateComCode() {
		return this.locateComCode;
	}

	/**       
	 * 属性本地机构代码的setter方法
	 */
	public void setLocateComCode(String locateComCode) {
		this.locateComCode = locateComCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDagentAllId))
			return false;
		PrpDagentAllId castOther = (PrpDagentAllId) other;

		return ((this.getAgentCode() == castOther.getAgentCode()) || (this.getAgentCode() != null
				&& castOther.getAgentCode() != null && this.getAgentCode().equals(castOther.getAgentCode())))
				&& ((this.getLocateComCode() == castOther.getLocateComCode()) || (this.getLocateComCode() != null
						&& castOther.getLocateComCode() != null && this.getLocateComCode().equals(
						castOther.getLocateComCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getAgentCode() == null ? 0 : this.getAgentCode().hashCode());
		result = 37 * result + (getLocateComCode() == null ? 0 : this.getLocateComCode().hashCode());
		return result;
	}

}
