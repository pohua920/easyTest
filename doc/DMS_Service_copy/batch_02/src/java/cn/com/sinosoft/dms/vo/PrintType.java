package cn.com.sinosoft.dms.vo;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO��printtype
 */
@Entity
@Table(name = "printtype")
public class PrintType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ����printtypecode */
	private String printTypeCode;

	/** ����printtypename */
	private String printTypeName;

	/** ���Ա�ע */
	private String remark;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��printtype��Ĭ�Ϲ��췽��
	 */
	public PrintType() {
	}

	/**       
	 * ����printtypecode��getter����
	 */
	@Id
	@Column(name = "printtypecode")
	public String getPrintTypeCode() {
		return this.printTypeCode;
	}

	/**       
	 * ����printtypecode��setter����
	 */
	public void setPrintTypeCode(String printTypeCode) {
		this.printTypeCode = printTypeCode;
	}

	/**       
	 * ����printtypename��getter����
	 */

	@Column(name = "printtypename")
	public String getPrintTypeName() {
		return this.printTypeName;
	}

	/**       
	 * ����printtypename��setter����
	 */
	public void setPrintTypeName(String printTypeName) {
		this.printTypeName = printTypeName;
	}

	/**       
	 * ���Ա�ע��getter����
	 */

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	/**       
	 * ���Ա�ע��setter����
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
