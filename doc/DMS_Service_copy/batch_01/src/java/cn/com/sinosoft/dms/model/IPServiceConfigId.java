package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO��ipServiceConfigId
 */
@Embeddable
public class IPServiceConfigId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Է������ */
	private String serverCode;

	/** ���Ի������� */
	private String environmentCode;

	/**
	 * ��ipServiceConfigId��Ĭ�Ϲ��췽��
	 */
	public IPServiceConfigId() {
	}

	/**       
	 * ���Է�������getter����
	 */

	@Column(name = "servercode")
	public String getServerCode() {
		return this.serverCode;
	}

	/**       
	 * ���Է�������setter����
	 */
	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	/**       
	 * ���Ի��������getter����
	 */

	@Column(name = "environmentcode")
	public String getEnvironmentCode() {
		return this.environmentCode;
	}

	/**       
	 * ���Ի��������setter����
	 */
	public void setEnvironmentCode(String environmentCode) {
		this.environmentCode = environmentCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IPServiceConfigId))
			return false;
		IPServiceConfigId castOther = (IPServiceConfigId) other;

		return ((this.getServerCode() == castOther.getServerCode()) || (this
				.getServerCode() != null
				&& castOther.getServerCode() != null && this.getServerCode()
				.equals(castOther.getServerCode())))
				&& ((this.getEnvironmentCode() == castOther
						.getEnvironmentCode()) || (this.getEnvironmentCode() != null
						&& castOther.getEnvironmentCode() != null && this
						.getEnvironmentCode().equals(
								castOther.getEnvironmentCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getServerCode() == null ? 0 : this.getServerCode()
						.hashCode());
		result = 37
				* result
				+ (getEnvironmentCode() == null ? 0 : this.getEnvironmentCode()
						.hashCode());
		return result;
	}

}
