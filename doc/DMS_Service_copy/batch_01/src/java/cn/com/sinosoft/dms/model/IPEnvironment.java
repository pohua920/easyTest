package cn.com.sinosoft.dms.model;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO��ipEnvironment
 */
@Entity
@Table(name = "ipenvironment")
public class IPEnvironment implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Ի������� */
	private String environmentCode;

	/** ���Ի������ */
	private String environmentName;

	/** ������Ϣ������Ա */
	private String creatorCode;

	/** ���Դ���ʱ�� */
	private Date creatorTime;

	/** �������¸��²�����Ա */
	private String updaterCode;

	/** ��������޸�ʱ�� */
	private Date updateTime;

	/** �������绮�� */
	private String netType;

	/** ������Ч��־ */
	private String validStatus;

	/**
	 * ��ipEnvironment��Ĭ�Ϲ��췽��
	 */
	public IPEnvironment() {
	}

	/**       
	 * ���Ի��������getter����
	 */
	@Id
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

	/**       
	 * ���Ի�����Ƶ�getter����
	 */

	@Column(name = "environmentname")
	public String getEnvironmentName() {
		return this.environmentName;
	}

	/**       
	 * ���Ի�����Ƶ�setter����
	 */
	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	/**       
	 * ������Ϣ������Ա��getter����
	 */

	@Column(name = "creatorcode")
	public String getCreatorCode() {
		return this.creatorCode;
	}

	/**       
	 * ������Ϣ������Ա��setter����
	 */
	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	/**       
	 * ���Դ���ʱ���getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "creatortime")
	public Date getCreatorTime() {
		return this.creatorTime;
	}

	/**       
	 * ���Դ���ʱ���setter����
	 */
	public void setCreatorTime(Date creatorTime) {
		this.creatorTime = creatorTime;
	}

	/**       
	 * �������¸��²�����Ա��getter����
	 */

	@Column(name = "updatercode")
	public String getUpdaterCode() {
		return this.updaterCode;
	}

	/**       
	 * �������¸��²�����Ա��setter����
	 */
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	/**       
	 * ��������޸�ʱ���getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "updatetime")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**       
	 * ��������޸�ʱ���setter����
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**       
	 * �������绮�ֵ�getter����
	 */

	@Column(name = "nettype")
	public String getNetType() {
		return this.netType;
	}

	/**       
	 * �������绮�ֵ�setter����
	 */
	public void setNetType(String netType) {
		this.netType = netType;
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

}
