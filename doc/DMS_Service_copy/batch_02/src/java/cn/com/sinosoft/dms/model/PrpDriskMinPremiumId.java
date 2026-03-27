package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO��PrpDriskMinPremiumId
 */
@Embeddable
public class PrpDriskMinPremiumId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Թ������ִ��� */
	private String riskCode;

	/** ����״̬��� */
	private BigDecimal serialNo;

	/**
	 * ��PrpDriskMinPremiumId��Ĭ�Ϲ��췽��
	 */
	public PrpDriskMinPremiumId() {
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
		if (!(other instanceof PrpDriskMinPremiumId))
			return false;
		PrpDriskMinPremiumId castOther = (PrpDriskMinPremiumId) other;

		return ((this.getRiskCode() == castOther.getRiskCode()) || (this
				.getRiskCode() != null
				&& castOther.getRiskCode() != null && this.getRiskCode()
				.equals(castOther.getRiskCode())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this
						.getSerialNo() != null
						&& castOther.getSerialNo() != null && this
						.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result
				+ (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
