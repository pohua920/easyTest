package cn.com.sinosoft.dms.vo;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO��prpDclass
 */
@Entity
@Table(name = "prpdclass")
public class PrpDclass implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Թ���������� */
	private String classCode;

	/** ���Ծ����ַ������ */
	private String oldClassCode;

	/** �������ּ�������ȫ�� */
	private String classCName;

	/** �������ּ������ļ�� */
	private String classSCName;

	/** �������ַ���������� */
	private String classTName;

	/** ��������Ӣ��ȫ�� */
	private String classEName;

	/** ��������Ӣ�ļ�� */
	private String classSEName;

	/** ������Ϣ������Ա */
	private String creatorCode;

	/** ���Դ���ʱ�� */
	private Date createTime;

	/** �������¸��²�����Ա */
	private String updaterCode;

	/** ��������޸�ʱ�� */
	private Date updateTime;

	/** ������Ч���� */
	private Date validDate;

	/** ����ʧЧ���� */
	private Date invalidDate;

	/** ������Ч��־ */
	private String validInd;

	/** ����Ԥ���ֶ�1 */
	private String tcol1;

	/** ����Ԥ���ֶ�2 */
	private String tcol2;

	/** ����Ԥ���ֶ�3 */
	private String tcol3;

	/** ���Ա�ע */
	private String remark;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��prpDclass��Ĭ�Ϲ��췽��
	 */
	public PrpDclass() {
	}

	/**       
	 * ���Թ�����������getter����
	 */
	@Id
	@Column(name = "classcode")
	public String getClassCode() {
		return this.classCode;
	}

	/**       
	 * ���Թ�����������setter����
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**       
	 * ���Ծ����ַ�������getter����
	 */

	@Column(name = "oldclasscode")
	public String getOldClassCode() {
		return this.oldClassCode;
	}

	/**       
	 * ���Ծ����ַ�������setter����
	 */
	public void setOldClassCode(String oldClassCode) {
		this.oldClassCode = oldClassCode;
	}

	/**       
	 * �������ּ�������ȫ�Ƶ�getter����
	 */

	@Column(name = "classcname")
	public String getClassCName() {
		return this.classCName;
	}

	/**       
	 * �������ּ�������ȫ�Ƶ�setter����
	 */
	public void setClassCName(String classCName) {
		this.classCName = classCName;
	}

	/**       
	 * �������ּ������ļ�Ƶ�getter����
	 */

	@Column(name = "classscname")
	public String getClassSCName() {
		return this.classSCName;
	}

	/**       
	 * �������ּ������ļ�Ƶ�setter����
	 */
	public void setClassSCName(String classSCName) {
		this.classSCName = classSCName;
	}

	/**       
	 * �������ַ���������Ƶ�getter����
	 */

	@Column(name = "classtname")
	public String getClassTName() {
		return this.classTName;
	}

	/**       
	 * �������ַ���������Ƶ�setter����
	 */
	public void setClassTName(String classTName) {
		this.classTName = classTName;
	}

	/**       
	 * ��������Ӣ��ȫ�Ƶ�getter����
	 */

	@Column(name = "classename")
	public String getClassEName() {
		return this.classEName;
	}

	/**       
	 * ��������Ӣ��ȫ�Ƶ�setter����
	 */
	public void setClassEName(String classEName) {
		this.classEName = classEName;
	}

	/**       
	 * ��������Ӣ�ļ�Ƶ�getter����
	 */

	@Column(name = "classsename")
	public String getClassSEName() {
		return this.classSEName;
	}

	/**       
	 * ��������Ӣ�ļ�Ƶ�setter����
	 */
	public void setClassSEName(String classSEName) {
		this.classSEName = classSEName;
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
	 * ������Ч���ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "validdate")
	public Date getValidDate() {
		return this.validDate;
	}

	/**       
	 * ������Ч���ڵ�setter����
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**       
	 * ����ʧЧ���ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "invaliddate")
	public Date getInvalidDate() {
		return this.invalidDate;
	}

	/**       
	 * ����ʧЧ���ڵ�setter����
	 */
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**       
	 * ������Ч��־��getter����
	 */

	@Column(name = "validind")
	public String getValidInd() {
		return this.validInd;
	}

	/**       
	 * ������Ч��־��setter����
	 */
	public void setValidInd(String validInd) {
		this.validInd = validInd;
	}

	/**       
	 * ����Ԥ���ֶ�1��getter����
	 */

	@Column(name = "tcol1")
	public String getTcol1() {
		return this.tcol1;
	}

	/**       
	 * ����Ԥ���ֶ�1��setter����
	 */
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}

	/**       
	 * ����Ԥ���ֶ�2��getter����
	 */

	@Column(name = "tcol2")
	public String getTcol2() {
		return this.tcol2;
	}

	/**       
	 * ����Ԥ���ֶ�2��setter����
	 */
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}

	/**       
	 * ����Ԥ���ֶ�3��getter����
	 */

	@Column(name = "tcol3")
	public String getTcol3() {
		return this.tcol3;
	}

	/**       
	 * ����Ԥ���ֶ�3��setter����
	 */
	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
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
