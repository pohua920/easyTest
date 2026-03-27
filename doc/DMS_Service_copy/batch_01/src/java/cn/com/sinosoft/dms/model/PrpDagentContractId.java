package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO��prpdagentcontractId
 */
@Embeddable
public class PrpDagentContractId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Դ����˴��� */
	private String agentCode;

	/** ����contractno */
	private String contractNo;

	/**
	 * ��prpdagentcontractId��Ĭ�Ϲ��췽��
	 */
	public PrpDagentContractId() {
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
	 * ����contractno��getter����
	 */

	@Column(name = "contractno")
	public String getContractNo() {
		return this.contractNo;
	}

	/**       
	 * ����contractno��setter����
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDagentContractId))
			return false;
		PrpDagentContractId castOther = (PrpDagentContractId) other;

		return ((this.getAgentCode() == castOther.getAgentCode()) || (this
				.getAgentCode() != null
				&& castOther.getAgentCode() != null && this.getAgentCode()
				.equals(castOther.getAgentCode())))
				&& ((this.getContractNo() == castOther.getContractNo()) || (this
						.getContractNo() != null
						&& castOther.getContractNo() != null && this
						.getContractNo().equals(castOther.getContractNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getAgentCode() == null ? 0 : this.getAgentCode().hashCode());
		result = 37
				* result
				+ (getContractNo() == null ? 0 : this.getContractNo()
						.hashCode());
		return result;
	}

}
