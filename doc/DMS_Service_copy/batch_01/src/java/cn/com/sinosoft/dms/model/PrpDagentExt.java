package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO��prpdagentext
 */
@Entity
@Table(name = "prpdagentext")
public class PrpDagentExt implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������ˮ��ID */
	private PrpDagentExtId id;

	/** ����groupcode */
	private String groupCode;

	/** �����Ƿ�����������¼���ʹ�� */
	private String agentNature;

	/** ����Чf״̬(0ʧЧ/1��Ч) */
	private String validStatus;

	/** ���Ա�־�ֶ� */
	private String flag;

	/**
	 * ��prpdagentext��Ĭ�Ϲ��췽��
	 */
	public PrpDagentExt() {
	}

	/**       
	 * ������ˮ��ID��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "agentCode", column = @Column(name = "agentcode")),
			@AttributeOverride(name = "comCode", column = @Column(name = "comcode")),
			@AttributeOverride(name = "classCode", column = @Column(name = "classcode")) })
	public PrpDagentExtId getId() {
		return this.id;
	}

	/**       
	 * ������ˮ��ID��setter����
	 */
	public void setId(PrpDagentExtId id) {
		this.id = id;
	}

	/**       
	 * ����groupcode��getter����
	 */

	@Column(name = "groupcode")
	public String getGroupCode() {
		return this.groupCode;
	}

	/**       
	 * ����groupcode��setter����
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**       
	 * �����Ƿ�����������¼���ʹ�õ�getter����
	 */

	@Column(name = "agentnature")
	public String getAgentNature() {
		return this.agentNature;
	}

	/**       
	 * �����Ƿ�����������¼���ʹ�õ�setter����
	 */
	public void setAgentNature(String agentNature) {
		this.agentNature = agentNature;
	}

	/**       
	 * ����Чf״̬(0ʧЧ/1��Ч)��getter����
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * ����Чf״̬(0ʧЧ/1��Ч)��setter����
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * ���Ա�־�ֶε�getter����
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * ���Ա�־�ֶε�setter����
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
