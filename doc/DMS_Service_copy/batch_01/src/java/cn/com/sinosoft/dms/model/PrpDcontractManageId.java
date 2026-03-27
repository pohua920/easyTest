package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类prpDcontractManageId
 */
@Embeddable
public class PrpDcontractManageId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性合同对象类型 */
	private String contractObjectType;

	/** 属性合同对象代码 */
	private String contractObjectCode;

	/** 属性合同号 */
	private String contractNo;

	/**
	 * 类prpDcontractManageId的默认构造方法
	 */
	public PrpDcontractManageId() {
	}

	/**       
	 * 属性合同对象类型的getter方法
	 */

	@Column(name = "contractobjecttype")
	public String getContractObjectType() {
		return this.contractObjectType;
	}

	/**       
	 * 属性合同对象类型的setter方法
	 */
	public void setContractObjectType(String contractObjectType) {
		this.contractObjectType = contractObjectType;
	}

	/**       
	 * 属性合同对象代码的getter方法
	 */

	@Column(name = "contractobjectcode")
	public String getContractObjectCode() {
		return this.contractObjectCode;
	}

	/**       
	 * 属性合同对象代码的setter方法
	 */
	public void setContractObjectCode(String contractObjectCode) {
		this.contractObjectCode = contractObjectCode;
	}

	/**       
	 * 属性合同号的getter方法
	 */

	@Column(name = "contractno")
	public String getContractNo() {
		return this.contractNo;
	}

	/**       
	 * 属性合同号的setter方法
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDcontractManageId))
			return false;
		PrpDcontractManageId castOther = (PrpDcontractManageId) other;

		return ((this.getContractObjectType() == castOther.getContractObjectType()) || (this.getContractObjectType() != null
				&& castOther.getContractObjectType() != null && this.getContractObjectType().equals(
				castOther.getContractObjectType())))
				&& ((this.getContractObjectCode() == castOther.getContractObjectCode()) || (this
						.getContractObjectCode() != null
						&& castOther.getContractObjectCode() != null && this.getContractObjectCode().equals(
						castOther.getContractObjectCode())))
				&& ((this.getContractNo() == castOther.getContractNo()) || (this.getContractNo() != null
						&& castOther.getContractNo() != null && this.getContractNo().equals(castOther.getContractNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getContractObjectType() == null ? 0 : this.getContractObjectType().hashCode());
		result = 37 * result + (getContractObjectCode() == null ? 0 : this.getContractObjectCode().hashCode());
		result = 37 * result + (getContractNo() == null ? 0 : this.getContractNo().hashCode());
		return result;
	}

}
