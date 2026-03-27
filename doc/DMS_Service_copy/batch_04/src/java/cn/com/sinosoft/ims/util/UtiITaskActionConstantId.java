package cn.com.sinosoft.ims.util;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO��UtiITaskActionConstantId
 */
@Embeddable
public class UtiITaskActionConstantId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Բ������� */
	private String actionType;

	/** ���Թ��ܴ��� */
	private String taskCode;

	/**
	 * ��UtiITaskActionConstantId��Ĭ�Ϲ��췽��
	 */
	public UtiITaskActionConstantId() {
	}

	/**       
	 * ���Բ������͵�getter����
	 */

	@Column(name = "ACTIONTYPE")
	public String getActionType() {
		return this.actionType;
	}

	/**       
	 * ���Բ������͵�setter����
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**       
	 * ���Թ��ܴ����getter����
	 */

	@Column(name = "TASKCODE")
	public String getTaskCode() {
		return this.taskCode;
	}

	/**       
	 * ���Թ��ܴ����setter����
	 */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UtiITaskActionConstantId))
			return false;
		UtiITaskActionConstantId castOther = (UtiITaskActionConstantId) other;

		return ((this.getActionType() == castOther.getActionType()) || (this
				.getActionType() != null
				&& castOther.getActionType() != null && this.getActionType()
				.equals(castOther.getActionType())))
				&& ((this.getTaskCode() == castOther.getTaskCode()) || (this
						.getTaskCode() != null
						&& castOther.getTaskCode() != null && this
						.getTaskCode().equals(castOther.getTaskCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getActionType() == null ? 0 : this.getActionType()
						.hashCode());
		result = 37 * result
				+ (getTaskCode() == null ? 0 : this.getTaskCode().hashCode());
		return result;
	}

}
