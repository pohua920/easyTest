package cn.com.sinosoft.dms.model;


// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO��prpDsettlementLkr
 */
@Entity
@Table(name = "prpdsettlementlkr")
public class PrpDsettlementLkr implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ����jϵ�˴��� */
	private String linkerCode;

	/** ����jϵ�� */
	private String linkerName;

	/** ������˻���� */
	private String comCode;

	/** ���Ե绰 */
	private String phoneNumber;

	/** �����ֻ���� */
	private String mobile;

	/** ���Դ������ */
	private String faxNumber;

	/** ������Ч��־ */
	private String validStatus;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��prpDsettlementLkr��Ĭ�Ϲ��췽��
	 */
	public PrpDsettlementLkr() {
	}

	/**       
	 * ����jϵ�˴����getter����
	 */
	@Id
	@Column(name = "linkercode")
	public String getLinkerCode() {
		return this.linkerCode;
	}

	/**       
	 * ����jϵ�˴����setter����
	 */
	public void setLinkerCode(String linkerCode) {
		this.linkerCode = linkerCode;
	}

	/**       
	 * ����jϵ�˵�getter����
	 */

	@Column(name = "linkername")
	public String getLinkerName() {
		return this.linkerName;
	}

	/**       
	 * ����jϵ�˵�setter����
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	/**       
	 * ������˻�����getter����
	 */

	@Column(name = "comcode")
	public String getComCode() {
		return this.comCode;
	}

	/**       
	 * ������˻�����setter����
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**       
	 * ���Ե绰��getter����
	 */

	@Column(name = "phonenumber")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**       
	 * ���Ե绰��setter����
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**       
	 * �����ֻ�����getter����
	 */

	@Column(name = "mobile")
	public String getMobile() {
		return this.mobile;
	}

	/**       
	 * �����ֻ�����setter����
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**       
	 * ���Դ�������getter����
	 */

	@Column(name = "faxnumber")
	public String getFaxNumber() {
		return this.faxNumber;
	}

	/**       
	 * ���Դ�������setter����
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
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

}
