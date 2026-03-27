package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO��prpdagentextId
 */
@Embeddable
public class PrpDagentExtId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Դ����˴��� */
	private String agentCode;

	/** ���Ի���� */
	private String comCode;

	/** �������ִ��� */
	private String classCode;

	/**
	 * ��prpdagentextId��Ĭ�Ϲ��췽��
	 */
	public PrpDagentExtId() {
	}

	/**       
	 * ���Դ����˴����getter����
	 */

	@Column(name = "agentcode")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**       
	 * ���Դ����˴����setter����
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**       
	 * ���Ի�����getter����
	 */

	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}

	/**       
	 * ���Ի�����setter����
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**       
	 * �������ִ����getter����
	 */

	@Column(name = "classcode")
	public String getClassCode() {
		return this.classCode;
	}

	/**       
	 * �������ִ����setter����
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDagentExtId))
			return false;
		PrpDagentExtId castOther = (PrpDagentExtId) other;

		return ((this.getAgentCode() == castOther.getAgentCode()) || (this
				.getAgentCode() != null
				&& castOther.getAgentCode() != null && this.getAgentCode()
				.equals(castOther.getAgentCode())))
				&& ((this.getComCode() == castOther.getComCode()) || (this
						.getComCode() != null
						&& castOther.getComCode() != null && this.getComCode()
						.equals(castOther.getComCode())))
				&& ((this.getClassCode() == castOther.getClassCode()) || (this
						.getClassCode() != null
						&& castOther.getClassCode() != null && this
						.getClassCode().equals(castOther.getClassCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getAgentCode() == null ? 0 : this.getAgentCode().hashCode());
		result = 37 * result
				+ (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result
				+ (getClassCode() == null ? 0 : this.getClassCode().hashCode());
		return result;
	}

}
