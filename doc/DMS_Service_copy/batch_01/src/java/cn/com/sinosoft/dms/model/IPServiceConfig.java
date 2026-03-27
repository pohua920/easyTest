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
 * POJO��ipServiceConfig
 */
@Entity
@Table(name = "ipserviceconfig")
public class IPServiceConfig implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������̶����ʶ */
	private IPServiceConfigId id;

	/** ���Է������ */
	private String serverName;

	/** ���Է���Э������(http��https��) */
	private String proteclType;

	/** ���Է���IP��ַ */
	private String serverIP;

	/** ���Է���˿� */
	private String serverPort;

	/** ���Է���Ӧ���� */
	private String serverAppName;

	/** ���Է������ */
	private String methods;

	/** ��������������� */
	private String areaCode;

	/** ���Է���Ӧ���û��� */
	private String appUserName;

	/** ���Է���Ӧ������ */
	private String appPassword;

	/** ������Ϣ������Ա */
	private String creatorCode;

	/** ���Դ���ʱ�� */
	private Date createTime;

	/** �������¸��²�����Ա */
	private String updaterCode;

	/** ��������޸�ʱ�� */
	private Date updateTime;

	/** ������Ч��־ */
	private String validStatus;

	/**
	 * ��ipServiceConfig��Ĭ�Ϲ��췽��
	 */
	public IPServiceConfig() {
	}

	/**       
	 * ������̶����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "serverCode", column = @Column(name = "servercode")),
			@AttributeOverride(name = "environmentCode", column = @Column(name = "environmentcode")) })
	public IPServiceConfigId getId() {
		return this.id;
	}

	/**       
	 * ������̶����ʶ��setter����
	 */
	public void setId(IPServiceConfigId id) {
		this.id = id;
	}

	/**       
	 * ���Է�����Ƶ�getter����
	 */

	@Column(name = "servername")
	public String getServerName() {
		return this.serverName;
	}

	/**       
	 * ���Է�����Ƶ�setter����
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**       
	 * ���Է���Э������(http��https��)��getter����
	 */

	@Column(name = "protecltype")
	public String getProteclType() {
		return this.proteclType;
	}

	/**       
	 * ���Է���Э������(http��https��)��setter����
	 */
	public void setProteclType(String proteclType) {
		this.proteclType = proteclType;
	}

	/**       
	 * ���Է���IP��ַ��getter����
	 */

	@Column(name = "serverip")
	public String getServerIP() {
		return this.serverIP;
	}

	/**       
	 * ���Է���IP��ַ��setter����
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	/**       
	 * ���Է���˿ڵ�getter����
	 */

	@Column(name = "serverport")
	public String getServerPort() {
		return this.serverPort;
	}

	/**       
	 * ���Է���˿ڵ�setter����
	 */
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	/**       
	 * ���Է���Ӧ�����getter����
	 */

	@Column(name = "serverappname")
	public String getServerAppName() {
		return this.serverAppName;
	}

	/**       
	 * ���Է���Ӧ�����setter����
	 */
	public void setServerAppName(String serverAppName) {
		this.serverAppName = serverAppName;
	}

	/**       
	 * ���Է�����Ƶ�getter����
	 */

	@Column(name = "methods")
	public String getMethods() {
		return this.methods;
	}

	/**       
	 * ���Է�����Ƶ�setter����
	 */
	public void setMethods(String methods) {
		this.methods = methods;
	}

	/**       
	 * ����������������getter����
	 */

	@Column(name = "areacode")
	public String getAreaCode() {
		return this.areaCode;
	}

	/**       
	 * ����������������setter����
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**       
	 * ���Է���Ӧ���û����getter����
	 */

	@Column(name = "appusername")
	public String getAppUserName() {
		return this.appUserName;
	}

	/**       
	 * ���Է���Ӧ���û����setter����
	 */
	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	/**       
	 * ���Է���Ӧ�������getter����
	 */

	@Column(name = "apppassword")
	public String getAppPassword() {
		return this.appPassword;
	}

	/**       
	 * ���Է���Ӧ�������setter����
	 */
	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
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
	@Column(name = "createtime")
	public Date getCreateTime() {
		return this.createTime;
	}

	/**       
	 * ���Դ���ʱ���setter����
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
