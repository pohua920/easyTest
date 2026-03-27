package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO��saOldAgentId
 */
@Embeddable
public class SaOldAgentId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Ծ������� */
	private String oldAgentCode;

	/** ������˻���� */
	private String comCode;

	/**
	 * ��saOldAgentId��Ĭ�Ϲ��췽��
	 */
	public SaOldAgentId() {
	}

	/**       
	 * ���Ծ��������getter����
	 */

	@Column(name = "oldagentcode")
	public String getOldAgentCode() {
		return this.oldAgentCode;
	}

	/**       
	 * ���Ծ��������setter����
	 */
	public void setOldAgentCode(String oldAgentCode) {
		this.oldAgentCode = oldAgentCode;
	}

	/**       
	 * ������˻�����getter����
	 */

	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}

	/**       
	 * ������˻�����setter����
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SaOldAgentId))
			return false;
		SaOldAgentId castOther = (SaOldAgentId) other;

		return ((this.getOldAgentCode() == castOther.getOldAgentCode()) || (this
				.getOldAgentCode() != null
				&& castOther.getOldAgentCode() != null && this
				.getOldAgentCode().equals(castOther.getOldAgentCode())))
				&& ((this.getComCode() == castOther.getComCode()) || (this
						.getComCode() != null
						&& castOther.getComCode() != null && this.getComCode()
						.equals(castOther.getComCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getOldAgentCode() == null ? 0 : this.getOldAgentCode()
						.hashCode());
		result = 37 * result
				+ (getComCode() == null ? 0 : this.getComCode().hashCode());
		return result;
	}

}
