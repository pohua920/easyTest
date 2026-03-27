package cn.com.sinosoft.ims.util;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO��UtiITaskActionConstant
 */
@Entity
@Table(name = "UTIITASKACTIONCONSTANT")
public class UtiITaskActionConstant implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** �������̶�����ʶ */
	private UtiITaskActionConstantId id;

	/** ���Թ������ */
	private String taskName;

	/**
	 * ��UtiITaskActionConstant��Ĭ�Ϲ��췽��
	 */
	public UtiITaskActionConstant() {
	}

	/**       
	 * �������̶�����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "actionType", column = @Column(name = "ACTIONTYPE")),
			@AttributeOverride(name = "taskCode", column = @Column(name = "TASKCODE")) })
	public UtiITaskActionConstantId getId() {
		return this.id;
	}

	/**       
	 * �������̶�����ʶ��setter����
	 */
	public void setId(UtiITaskActionConstantId id) {
		this.id = id;
	}

	/**       
	 * ���Թ�����Ƶ�getter����
	 */

	@Column(name = "TASKNAME")
	public String getTaskName() {
		return this.taskName;
	}

	/**       
	 * ���Թ�����Ƶ�setter����
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
