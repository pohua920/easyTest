package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO��prpdtreatyretenId
 */
@Embeddable
public class PrpDtreatyRetenId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ����uwyear */
	private String uwYear;

	/** ���Թ���������� */
	private String classCode;

	/** ���Թ������ִ��� */
	private String riskCode;

	/** ����״̬��� */
	private Integer serialNo;

	/**
	 * ��prpdtreatyretenId��Ĭ�Ϲ��췽��
	 */
	public PrpDtreatyRetenId() {
	}

	/**       
	 * ����uwyear��getter����
	 */

	@Column(name = "UWYEAR")
	public String getUwYear() {
		return this.uwYear;
	}

	/**       
	 * ����uwyear��setter����
	 */
	public void setUwYear(String uwYear) {
		this.uwYear = uwYear;
	}

	/**       
	 * ���Թ�����������getter����
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**       
	 * ���Թ�����������setter����
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**       
	 * ���Թ������ִ����getter����
	 */

	@Column(name = "RISKCODE")
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

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**       
	 * ����״̬��ŵ�setter����
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDtreatyRetenId))
			return false;
		PrpDtreatyRetenId castOther = (PrpDtreatyRetenId) other;

		return ((this.getUwYear() == castOther.getUwYear()) || (this
				.getUwYear() != null
				&& castOther.getUwYear() != null && this.getUwYear().equals(
				castOther.getUwYear())))
				&& ((this.getClassCode() == castOther.getClassCode()) || (this
						.getClassCode() != null
						&& castOther.getClassCode() != null && this
						.getClassCode().equals(castOther.getClassCode())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this
						.getRiskCode() != null
						&& castOther.getRiskCode() != null && this
						.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this
						.getSerialNo() != null
						&& castOther.getSerialNo() != null && this
						.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUwYear() == null ? 0 : this.getUwYear().hashCode());
		result = 37 * result
				+ (getClassCode() == null ? 0 : this.getClassCode().hashCode());
		result = 37 * result
				+ (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result
				+ (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
