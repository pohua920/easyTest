package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO��saOldAgent
 */
@Entity
@Table(name = "saoldagent")
public class SaOldAgent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������̶����ʶ */
	private SaOldAgentId id;

	/** �����µĴ����˴��� */
	private String newAgentCode;

	/** ������Ч��־ */
	private String validStatus;

	/** ���Ա�־λ */
	private String flag;

	/** ����ACTION_ */
	private String action;

	/** ���Բ�����Ա���� */
	private String makeUserCode;

	/** ���Բ�����Ա���� */
	private String makeUserName;

	/** ������������ */
	private Date applyDate;

	/**
	 * ��saOldAgent��Ĭ�Ϲ��췽��
	 */
	public SaOldAgent() {
	}

	/**       
	 * ������̶����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "oldAgentCode", column = @Column(name = "oldagentcode")),
			@AttributeOverride(name = "comCode", column = @Column(name = "comcode")) })
	public SaOldAgentId getId() {
		return this.id;
	}

	/**       
	 * ������̶����ʶ��setter����
	 */
	public void setId(SaOldAgentId id) {
		this.id = id;
	}

	/**       
	 * �����µĴ����˴����getter����
	 */

	@Column(name = "newagentcode")
	public String getNewAgentCode() {
		return this.newAgentCode;
	}

	/**       
	 * �����µĴ����˴����setter����
	 */
	public void setNewAgentCode(String newAgentCode) {
		this.newAgentCode = newAgentCode;
	}

	/**       
	 * ������Ч��־��getter����
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * ������Ч��־��setter����
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**       
	 * ���Ա�־λ��getter����
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * ���Ա�־λ��setter����
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**       
	 * ����ACTION_��getter����
	 */

	@Column(name = "action")
	public String getAction() {
		return this.action;
	}

	/**       
	 * ����ACTION_��setter����
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**       
	 * ���Բ�����Ա�����getter����
	 */

	@Column(name = "makeusercode")
	public String getMakeUserCode() {
		return this.makeUserCode;
	}

	/**       
	 * ���Բ�����Ա�����setter����
	 */
	public void setMakeUserCode(String makeUserCode) {
		this.makeUserCode = makeUserCode;
	}

	/**       
	 * ���Բ�����Ա�����getter����
	 */

	@Column(name = "makeusername")
	public String getMakeUserName() {
		return this.makeUserName;
	}

	/**       
	 * ���Բ�����Ա�����setter����
	 */
	public void setMakeUserName(String makeUserName) {
		this.makeUserName = makeUserName;
	}

	/**       
	 * �����������ڵ�getter����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "applydate")
	public Date getApplyDate() {
		return this.applyDate;
	}

	/**       
	 * �����������ڵ�setter����
	 */
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

}
