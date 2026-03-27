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
 * POJO��prpDframe
 */
@Entity
@Table(name = "prpdframe")
public class PrpDframe implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Կ�ܴ��� */
	private String frameCode;

	/** ���Կ�ܼ���������� */
	private String frameCName;

	/** ���Կ�ܷ���Ӣ����� */
	private String frameTName;

	/** ���Կ�ܼ�� */
	private String frameSName;

	/** ���Կ��Ӣ����� */
	private String frameEName;

	/** ���Թ�����Ŀ */
	private String project;

	/** ������������㼶(ͳ�ܹ�˾ʡ��˾�й�˾) */
	private String areaLevel;

	/** ��������������� */
	private String areaCode;

	/** ��������������� */
	private String areaName;

	/** �������ÿͻ����� */
	private String customerType;

	/** ������������ */
	private String materialContxt;

	/** �����ļ�ϵͳ��Ӧ��� */
	private String documentNumber;

	/** �����ı����� */
	private String contentNumber;

	/** ���Բο����ϱ�� */
	private String materialID;

	/** ������˱�־ */
	private String auditFlag;

	/** ���Դ����� */
	private String createrCode;

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
	 * ��prpDframe��Ĭ�Ϲ��췽��
	 */
	public PrpDframe() {
	}

	/**       
	 * ���Կ�ܴ����getter����
	 */
	@Id
	@Column(name = "framecode")
	public String getFrameCode() {
		return this.frameCode;
	}

	/**       
	 * ���Կ�ܴ����setter����
	 */
	public void setFrameCode(String frameCode) {
		this.frameCode = frameCode;
	}

	/**       
	 * ���Կ�ܼ���������Ƶ�getter����
	 */

	@Column(name = "framecname")
	public String getFrameCName() {
		return this.frameCName;
	}

	/**       
	 * ���Կ�ܼ���������Ƶ�setter����
	 */
	public void setFrameCName(String frameCName) {
		this.frameCName = frameCName;
	}

	/**       
	 * ���Կ�ܷ���Ӣ����Ƶ�getter����
	 */

	@Column(name = "frametname")
	public String getFrameTName() {
		return this.frameTName;
	}

	/**       
	 * ���Կ�ܷ���Ӣ����Ƶ�setter����
	 */
	public void setFrameTName(String frameTName) {
		this.frameTName = frameTName;
	}

	/**       
	 * ���Կ�ܼ�Ƶ�getter����
	 */

	@Column(name = "framesname")
	public String getFrameSName() {
		return this.frameSName;
	}

	/**       
	 * ���Կ�ܼ�Ƶ�setter����
	 */
	public void setFrameSName(String frameSName) {
		this.frameSName = frameSName;
	}

	/**       
	 * ���Կ��Ӣ����Ƶ�getter����
	 */

	@Column(name = "frameename")
	public String getFrameEName() {
		return this.frameEName;
	}

	/**       
	 * ���Կ��Ӣ����Ƶ�setter����
	 */
	public void setFrameEName(String frameEName) {
		this.frameEName = frameEName;
	}

	/**       
	 * ���Թ�����Ŀ��getter����
	 */

	@Column(name = "project")
	public String getProject() {
		return this.project;
	}

	/**       
	 * ���Թ�����Ŀ��setter����
	 */
	public void setProject(String project) {
		this.project = project;
	}

	/**       
	 * ������������㼶(ͳ�ܹ�˾ʡ��˾�й�˾)��getter����
	 */

	@Column(name = "arealevel")
	public String getAreaLevel() {
		return this.areaLevel;
	}

	/**       
	 * ������������㼶(ͳ�ܹ�˾ʡ��˾�й�˾)��setter����
	 */
	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
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
	 * ��������������Ƶ�getter����
	 */

	@Column(name = "areaname")
	public String getAreaName() {
		return this.areaName;
	}

	/**       
	 * ��������������Ƶ�setter����
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**       
	 * �������ÿͻ����͵�getter����
	 */

	@Column(name = "customertype")
	public String getCustomerType() {
		return this.customerType;
	}

	/**       
	 * �������ÿͻ����͵�setter����
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**       
	 * �����������ݵ�getter����
	 */

	@Column(name = "materialcontxt")
	public String getMaterialContxt() {
		return this.materialContxt;
	}

	/**       
	 * �����������ݵ�setter����
	 */
	public void setMaterialContxt(String materialContxt) {
		this.materialContxt = materialContxt;
	}

	/**       
	 * �����ļ�ϵͳ��Ӧ��ŵ�getter����
	 */

	@Column(name = "documentnumber")
	public String getDocumentNumber() {
		return this.documentNumber;
	}

	/**       
	 * �����ļ�ϵͳ��Ӧ��ŵ�setter����
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**       
	 * �����ı����ݵ�getter����
	 */

	@Column(name = "contentnumber")
	public String getContentNumber() {
		return this.contentNumber;
	}

	/**       
	 * �����ı����ݵ�setter����
	 */
	public void setContentNumber(String contentNumber) {
		this.contentNumber = contentNumber;
	}

	/**       
	 * ���Բο����ϱ�ŵ�getter����
	 */

	@Column(name = "materialid")
	public String getMaterialID() {
		return this.materialID;
	}

	/**       
	 * ���Բο����ϱ�ŵ�setter����
	 */
	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}

	/**       
	 * ������˱�־��getter����
	 */

	@Column(name = "auditflag")
	public String getAuditFlag() {
		return this.auditFlag;
	}

	/**       
	 * ������˱�־��setter����
	 */
	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	/**       
	 * ���Դ����˵�getter����
	 */

	@Column(name = "creatercode")
	public String getCreaterCode() {
		return this.createrCode;
	}

	/**       
	 * ���Դ����˵�setter����
	 */
	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
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
