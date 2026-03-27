package cn.com.sinosoft.ims.log.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO��utiISyncLog
 */
@Entity
@Table(name = "UTIISYNCLOG")
public class UtiISyncLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ����״̬��� */
	private Long id;

	/** �������û���� */
	private String className;

	/** �����˺Ŵ��� */
	private String strKey;

	/** ������˻���� */
	private String operUserCode;

	/** ����ͬ����Դ���� */
	private String editType;

	/** ����ͬ��Ŀ�ط��� */
	private String destComCode;

	/** ����ͬ������ʱ�� */
	private Date sendDate;
	
	private Date lastSendDate;
	
	private Integer replayTimes;
	
	private String isSuccess;

	/** ���Դ�����Ϣ */
	private String errorMsg;
	
	private String hasDelData;

	private String userCode;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��utiISyncLog��Ĭ�Ϲ��췽��
	 */
	public UtiISyncLog() {
	}

	/**       
	 * ����״̬��ŵ�getter����
	 */
	@Id
	@Column(name = "SERIALNO")
	public Long getId() {
		return this.id;
	}

	/**       
	 * ����״̬��ŵ�setter����
	 */
	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "CLASSNAME")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@Column(name = "STRKEY")
	public String getStrKey() {
		return strKey;
	}

	public void setStrKey(String strKey) {
		this.strKey = strKey;
	}

	@Column(name = "OPERUSERCODE")
	public String getOperUserCode() {
		return operUserCode;
	}

	public void setOperUserCode(String operUserCode) {
		this.operUserCode = operUserCode;
	}

	@Column(name = "EDITTYPE")
	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	@Column(name = "DESTCOMCODE")
	public String getDestComCode() {
		return destComCode;
	}

	public void setDestComCode(String destComCode) {
		this.destComCode = destComCode;
	}

	@Column(name = "SENDDATE")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "LASTSENDDATE")
	public Date getLastSendDate() {
		return lastSendDate;
	}

	public void setLastSendDate(Date lastSendDate) {
		this.lastSendDate = lastSendDate;
	}

	@Column(name = "REPLAYTIMES")
	public Integer getReplayTimes() {
		return replayTimes;
	}

	public void setReplayTimes(Integer replayTimes) {
		this.replayTimes = replayTimes;
	}

	@Column(name = "ISSUCCESS")
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
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


	@Column(name = "HASDELDATA")
	public String getHasDelData() {
		return hasDelData;
	}

	public void setHasDelData(String hasDelData) {
		this.hasDelData = hasDelData;
	}
	@Column(name = "USERCODE")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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
