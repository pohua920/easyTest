package cn.com.sinosoft.ims.log.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO��utiIOperateLog
 */
@Entity
@Table(name = "UTIIOPERATELOG")
public class UtiIOperateLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ����״̬��� */
	private Long serialNo;

	/** �������û���� */
	private String userCode;

	/** �����˺Ŵ��� */
	private String accCode;

	/** ���Թ���ģ����� */
	private String funcCode;

	/** ���Թ��ܴ��� */
	private String taskCode;

	/** ������˻���� */
	private String svrCode;

	/** ���Ե�¼ʱ�� */
	private Date loginTime;

	/** �����뿪ʱ�� */
	private Date exitTime;

	/** ���Գ���ʱ�� */
	private String holdTime;

	/** ��������URL */
	private String requestURL;

	/** ����DESCRIPTION_ */
	private String description;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��utiIOperateLog��Ĭ�Ϲ��췽��
	 */
	public UtiIOperateLog() {
	}

	/**       
	 * ����״̬��ŵ�getter����
	 */
	@SequenceGenerator(name = "generator", allocationSize = 1, sequenceName = "SEQ_OPERATELOG")
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "SERIALNO")
	public Long getSerialNo() {
		return this.serialNo;
	}

	/**       
	 * ����״̬��ŵ�setter����
	 */
	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	/**       
	 * �������û���ŵ�getter����
	 */

	@Column(name = "USERCODE")
	public String getUserCode() {
		return this.userCode;
	}

	/**       
	 * �������û���ŵ�setter����
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**       
	 * �����˺Ŵ����getter����
	 */

	@Column(name = "ACCCODE")
	public String getAccCode() {
		return this.accCode;
	}

	/**       
	 * �����˺Ŵ����setter����
	 */
	public void setAccCode(String accCode) {
		this.accCode = accCode;
	}

	/**       
	 * ���Թ���ģ������getter����
	 */

	@Column(name = "FUNCCODE")
	public String getFuncCode() {
		return this.funcCode;
	}

	/**       
	 * ���Թ���ģ������setter����
	 */
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
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

	/**       
	 * ������˻�����getter����
	 */

	@Column(name = "SVRCODE")
	public String getSvrCode() {
		return this.svrCode;
	}

	/**       
	 * ������˻�����setter����
	 */
	public void setSvrCode(String svrCode) {
		this.svrCode = svrCode;
	}

	/**       
	 * ���Ե�¼ʱ���getter����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGINTIME")
	public Date getLoginTime() {
		return this.loginTime;
	}

	/**       
	 * ���Ե�¼ʱ���setter����
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**       
	 * �����뿪ʱ���getter����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXITTIME")
	public Date getExitTime() {
		return this.exitTime;
	}

	/**       
	 * �����뿪ʱ���setter����
	 */
	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	/**       
	 * ���Գ���ʱ���getter����
	 */
	@Column(name = "HOLDTIME")
	public String getHoldTime() {
		return this.holdTime;
	}

	/**       
	 * ���Գ���ʱ���setter����
	 */
	public void setHoldTime(String holdTime) {
		this.holdTime = holdTime;
	}

	/**       
	 * ��������URL��getter����
	 */

	@Column(name = "REQUESTURL")
	public String getRequestURL() {
		return this.requestURL;
	}

	/**       
	 * ��������URL��setter����
	 */
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	/**       
	 * ����DESCRIPTION_��getter����
	 */

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	/**       
	 * ����DESCRIPTION_��setter����
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**       
	 * ���Ա�־λ��getter����
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * ���Ա�־λ��setter����
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
