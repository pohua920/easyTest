package cn.com.sinosoft.dms.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDSimpleTreatyId
 */
@Embeddable
public class PrpDSimpleTreatyId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性合同编码 */
	private String treatyNo;

	/** 属性合约分项编码 */
	private String sectionNo;

	/** 属性险种代码 */
	private String classCode;

	/** 属性产品代码 */
	private String riskCode;

	/** 属性其他合约条件 */
	private String othCondition;

	/**
	 * 类PrpDSimpleTreatyId的默认构造方法
	 */
	public PrpDSimpleTreatyId() {
	}

	/**       
	 * 属性合同编码的getter方法
	 */

	@Column(name = "treatyno")
	public String getTreatyNo() {
		return this.treatyNo;
	}

	/**       
	 * 属性合同编码的setter方法
	 */
	public void setTreatyNo(String treatyNo) {
		this.treatyNo = treatyNo;
	}

	/**       
	 * 属性合约分项编码的getter方法
	 */

	@Column(name = "sectionno")
	public String getSectionNo() {
		return this.sectionNo;
	}

	/**       
	 * 属性合约分项编码的setter方法
	 */
	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	/**       
	 * 属性险种代码的getter方法
	 */

	@Column(name = "classcode")
	public String getClassCode() {
		return this.classCode;
	}

	/**       
	 * 属性险种代码的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**       
	 * 属性产品代码的getter方法
	 */

	@Column(name = "riskcode")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**       
	 * 属性产品代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**       
	 * 属性其他合约条件的getter方法
	 */

	@Column(name = "othcondition")
	public String getOthCondition() {
		return this.othCondition;
	}

	/**       
	 * 属性其他合约条件的setter方法
	 */
	public void setOthCondition(String othCondition) {
		this.othCondition = othCondition;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDSimpleTreatyId))
			return false;
		PrpDSimpleTreatyId castOther = (PrpDSimpleTreatyId) other;

		return ((this.getTreatyNo() == castOther.getTreatyNo()) || (this
				.getTreatyNo() != null
				&& castOther.getTreatyNo() != null && this.getTreatyNo()
				.equals(castOther.getTreatyNo())))
				&& ((this.getSectionNo() == castOther.getSectionNo()) || (this
						.getSectionNo() != null
						&& castOther.getSectionNo() != null && this
						.getSectionNo().equals(castOther.getSectionNo())))
				&& ((this.getClassCode() == castOther.getClassCode()) || (this
						.getClassCode() != null
						&& castOther.getClassCode() != null && this
						.getClassCode().equals(castOther.getClassCode())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this
						.getRiskCode() != null
						&& castOther.getRiskCode() != null && this
						.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getOthCondition() == castOther.getOthCondition()) || (this
						.getOthCondition() != null
						&& castOther.getOthCondition() != null && this
						.getOthCondition().equals(castOther.getOthCondition())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTreatyNo() == null ? 0 : this.getTreatyNo().hashCode());
		result = 37 * result
				+ (getSectionNo() == null ? 0 : this.getSectionNo().hashCode());
		result = 37 * result
				+ (getClassCode() == null ? 0 : this.getClassCode().hashCode());
		result = 37 * result
				+ (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37
				* result
				+ (getOthCondition() == null ? 0 : this.getOthCondition()
						.hashCode());
		return result;
	}

}
