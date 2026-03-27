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
 * POJO��utiIExceptionLog
 */
@Entity
@Table(name = "UTIIEXCEPTIONLOG")
public class UtiIExceptionLog implements java.io.Serializable {
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

	/** �����쳣����ʱ�� */
	private Date occurTime;

	/** �����쳣����ʱ�� */
	private Date overTime;

	/** ��������URL */
	private String requestURL;

	/** ���Դ�����Ϣ */
	private String errorMsg;

	/** ������ϸ����켣 */
	private String errorStack;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��utiIExceptionLog��Ĭ�Ϲ��췽��
	 */
	public UtiIExceptionLog() {
	}

	/**       
	 * ����״̬��ŵ�getter����
	 */
	@SequenceGenerator(name = "generator", allocationSize = 1, sequenceName = "SEQ_EXCEPTIONLOG")
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
	 * �����쳣����ʱ���getter����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OCCURTIME")
	public Date getOccurTime() {
		return this.occurTime;
	}

	/**       
	 * �����쳣����ʱ���setter����
	 */
	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	/**       
	 * �����쳣����ʱ���getter����
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OVERTIME")
	public Date getOverTime() {
		return this.overTime;
	}

	/**       
	 * �����쳣����ʱ���setter����
	 */
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
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
	 * ���Դ�����Ϣ��getter����
	 */

	@Column(name = "ERRORMSG")
	public String getErrorMsg() {
		return this.errorMsg;
	}

	/**       
	 * ���Դ�����Ϣ��setter����
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**       
	 * ������ϸ����켣��getter����
	 */

	@Column(name = "ERRORSTACK")
	public String getErrorStack() {
		return this.errorStack;
	}

	/**       
	 * ������ϸ����켣��setter����
	 */
	public void setErrorStack(String errorStack) {
		this.errorStack = errorStack;
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
