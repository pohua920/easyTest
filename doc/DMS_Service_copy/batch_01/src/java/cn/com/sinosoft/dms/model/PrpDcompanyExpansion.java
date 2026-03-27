package cn.com.sinosoft.dms.model;
// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO��prpDcompanyExpansion
 */
@Entity
@Table(name = "PRPDCOMPANYEXPANSION")
public class PrpDcompanyExpansion implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������˻���� */
	private String comCode;
	private String comCodeCIRC;
	private String licenseNo;
	private String email;
	private String remark;
	/**
	 * ��prpDcompanyExpansion��Ĭ�Ϲ��췽��
	 */
	public PrpDcompanyExpansion() {
	}

	/**       
	 * ������˻�����getter����
	 */
	@Id
	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**       
	 * ������˻�����setter����
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	@Column(name = "COMCODECIRC")
	public String getComCodeCIRC() {
		return comCodeCIRC;
	}

	public void setComCodeCIRC(String comCodeCIRC) {
		this.comCodeCIRC = comCodeCIRC;
	}
	
	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/**       
	 * ���Ա�ע��getter����
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**       
	 * ���Ա�ע��setter����
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
