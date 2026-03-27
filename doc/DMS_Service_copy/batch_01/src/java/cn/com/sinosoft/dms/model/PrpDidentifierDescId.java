package cn.com.sinosoft.dms.model;

// default package
// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO��prpDidentifierDescId
 */
@Embeddable
public class PrpDidentifierDescId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Լ����˴��� */
	private String identifierCode;

	/** ��������� */
	private BigDecimal lineNo;

	/**
	 * ��prpDidentifierDescId��Ĭ�Ϲ��췽��
	 */
	public PrpDidentifierDescId() {
	}

	/**       
	 * ���Լ����˴����getter����
	 */

	@Column(name = "IDENTIFIERCODE")
	public String getIdentifierCode() {
		return this.identifierCode;
	}

	/**       
	 * ���Լ����˴����setter����
	 */
	public void setIdentifierCode(String identifierCode) {
		this.identifierCode = identifierCode;
	}

	/**       
	 * ��������ŵ�getter����
	 */

	@Column(name = "LINENO")
	public BigDecimal getLineNo() {
		return this.lineNo;
	}

	/**       
	 * ��������ŵ�setter����
	 */
	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDidentifierDescId))
			return false;
		PrpDidentifierDescId castOther = (PrpDidentifierDescId) other;

		return ((this.getIdentifierCode() == castOther.getIdentifierCode()) || (this
				.getIdentifierCode() != null
				&& castOther.getIdentifierCode() != null && this
				.getIdentifierCode().equals(castOther.getIdentifierCode())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this
						.getLineNo() != null
						&& castOther.getLineNo() != null && this.getLineNo()
						.equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdentifierCode() == null ? 0 : this.getIdentifierCode()
						.hashCode());
		result = 37 * result
				+ (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
