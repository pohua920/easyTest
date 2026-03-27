package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO��PrpDriskClauseKindMinPremiumId
 */
@Embeddable
public class PrpDriskClauseKindMinPremiumId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Թ������ִ��� */
	private String riskCode;

	/** ���Բ�Ʒ���������� */
	private BigDecimal riskKCSerialNo;

	/** ���������� */
	private String clauseCode;

	/** �������������� */
	private String clauseKindID;

	/** ����״̬��� */
	private BigDecimal serialNo;

	/**
	 * ��PrpDriskClauseKindMinPremiumId��Ĭ�Ϲ��췽��
	 */
	public PrpDriskClauseKindMinPremiumId() {
	}

	/**       
	 * ���Թ������ִ����getter����
	 */

	@Column(name = "riskcode")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**       
	 * ���Թ������ִ����setter����
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**       
	 * ���Բ�Ʒ���������ŵ�getter����
	 */

	@Column(name = "riskkcserialno")
	public BigDecimal getRiskKCSerialNo() {
		return this.riskKCSerialNo;
	}

	/**       
	 * ���Բ�Ʒ���������ŵ�setter����
	 */
	public void setRiskKCSerialNo(BigDecimal riskKCSerialNo) {
		this.riskKCSerialNo = riskKCSerialNo;
	}

	/**       
	 * �����������getter����
	 */

	@Column(name = "clausecode")
	public String getClauseCode() {
		return this.clauseCode;
	}

	/**       
	 * �����������setter����
	 */
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}

	/**       
	 * �������������ŵ�getter����
	 */

	@Column(name = "clausekindid")
	public String getClauseKindID() {
		return this.clauseKindID;
	}

	/**       
	 * �������������ŵ�setter����
	 */
	public void setClauseKindID(String clauseKindID) {
		this.clauseKindID = clauseKindID;
	}

	/**       
	 * ����״̬��ŵ�getter����
	 */

	@Column(name = "serialno")
	public BigDecimal getSerialNo() {
		return this.serialNo;
	}

	/**       
	 * ����״̬��ŵ�setter����
	 */
	public void setSerialNo(BigDecimal serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDriskClauseKindMinPremiumId))
			return false;
		PrpDriskClauseKindMinPremiumId castOther = (PrpDriskClauseKindMinPremiumId) other;

		return ((this.getRiskCode() == castOther.getRiskCode()) || (this
				.getRiskCode() != null
				&& castOther.getRiskCode() != null && this.getRiskCode()
				.equals(castOther.getRiskCode())))
				&& ((this.getRiskKCSerialNo() == castOther.getRiskKCSerialNo()) || (this
						.getRiskKCSerialNo() != null
						&& castOther.getRiskKCSerialNo() != null && this
						.getRiskKCSerialNo().equals(
								castOther.getRiskKCSerialNo())))
				&& ((this.getClauseCode() == castOther.getClauseCode()) || (this
						.getClauseCode() != null
						&& castOther.getClauseCode() != null && this
						.getClauseCode().equals(castOther.getClauseCode())))
				&& ((this.getClauseKindID() == castOther.getClauseKindID()) || (this
						.getClauseKindID() != null
						&& castOther.getClauseKindID() != null && this
						.getClauseKindID().equals(castOther.getClauseKindID())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this
						.getSerialNo() != null
						&& castOther.getSerialNo() != null && this
						.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37
				* result
				+ (getRiskKCSerialNo() == null ? 0 : this.getRiskKCSerialNo()
						.hashCode());
		result = 37
				* result
				+ (getClauseCode() == null ? 0 : this.getClauseCode()
						.hashCode());
		result = 37
				* result
				+ (getClauseKindID() == null ? 0 : this.getClauseKindID()
						.hashCode());
		result = 37 * result
				+ (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
