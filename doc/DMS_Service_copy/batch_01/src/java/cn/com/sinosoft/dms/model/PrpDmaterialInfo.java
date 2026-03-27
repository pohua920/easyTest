package cn.com.sinosoft.dms.model;
// default package
// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO��prpDmaterialInfo
 */
@Entity
@Table(name = "PRPDMATERIALINFO")
public class PrpDmaterialInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** �������ϱ�� */
	private String materialID;

	/** �������ϱ��� */
	private String materialName;

	/** �������Ϲؼ��� */
	private String keyword;

	/** �������Ϸ���(1���߷��桢9��������) */
	private String materialType;

	/** �������ò㼶 */
	private String areaLevel;

	/** ������������ */
	private String areaCode;

	/** ���԰䲼���� */
	private Date enactmentTime;

	/** �����ļ�ϵͳ��Ӧ��� */
	private String documentNumber;

	/** �����ı����� */
	private String contentNumber;

	/** ���Դ����� */
	private String creatorCode;

	/** ���Դ���ʱ�� */
	private Date createTime;

	/** ��������޸��� */
	private String updaterCode;

	/** ��������޸�ʱ�� */
	private Date updateTime;

	/** ������Ч���� */
	private Date validDate;

	/** ����ʧЧ���� */
	private Date invaidDate;

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
	 * ��prpDmaterialInfo��Ĭ�Ϲ��췽��
	 */
	public PrpDmaterialInfo() {
	}

	/**       
	 * �������ϱ�ŵ�getter����
	 */
	@Id
	@Column(name = "MATERIALID")
	public String getMaterialID() {
		return this.materialID;
	}

	/**       
	 * �������ϱ�ŵ�setter����
	 */
	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}

	/**       
	 * �������ϱ����getter����
	 */

	@Column(name = "MATERIALNAME")
	public String getMaterialName() {
		return this.materialName;
	}

	/**       
	 * �������ϱ����setter����
	 */
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	/**       
	 * �������Ϲؼ��ֵ�getter����
	 */

	@Column(name = "KEYWORD")
	public String getKeyword() {
		return this.keyword;
	}

	/**       
	 * �������Ϲؼ��ֵ�setter����
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**       
	 * �������Ϸ���(1���߷��桢9��������)��getter����
	 */

	@Column(name = "MATERIALTYPE")
	public String getMaterialType() {
		return this.materialType;
	}

	/**       
	 * �������Ϸ���(1���߷��桢9��������)��setter����
	 */
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	/**       
	 * �������ò㼶��getter����
	 */

	@Column(name = "AREALEVEL")
	public String getAreaLevel() {
		return this.areaLevel;
	}

	/**       
	 * �������ò㼶��setter����
	 */
	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	/**       
	 * �������������getter����
	 */

	@Column(name = "AREACODE")
	public String getAreaCode() {
		return this.areaCode;
	}

	/**       
	 * �������������setter����
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**       
	 * ���԰䲼���ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENACTMENTTIME")
	public Date getEnactmentTime() {
		return this.enactmentTime;
	}

	/**       
	 * ���԰䲼���ڵ�setter����
	 */
	public void setEnactmentTime(Date enactmentTime) {
		this.enactmentTime = enactmentTime;
	}

	/**       
	 * �����ļ�ϵͳ��Ӧ��ŵ�getter����
	 */

	@Column(name = "DOCUMENTNUMBER")
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

	@Column(name = "CONTENTNUMBER")
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
	 * ���Դ����˵�getter����
	 */

	@Column(name = "CREATORCODE")
	public String getCreatorCode() {
		return this.creatorCode;
	}

	/**       
	 * ���Դ����˵�setter����
	 */
	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	/**       
	 * ���Դ���ʱ���getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATETIME")
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
	 * ��������޸��˵�getter����
	 */

	@Column(name = "UPDATERCODE")
	public String getUpdaterCode() {
		return this.updaterCode;
	}

	/**       
	 * ��������޸��˵�setter����
	 */
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	/**       
	 * ��������޸�ʱ���getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATETIME")
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
	@Column(name = "VALIDDATE")
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
	@Column(name = "INVAIDDATE")
	public Date getInvaidDate() {
		return this.invaidDate;
	}

	/**       
	 * ����ʧЧ���ڵ�setter����
	 */
	public void setInvaidDate(Date invaidDate) {
		this.invaidDate = invaidDate;
	}

	/**       
	 * ������Ч��־��getter����
	 */

	@Column(name = "VALIDIND")
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

	@Column(name = "TCOL1")
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

	@Column(name = "TCOL2")
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

	@Column(name = "TCOL3")
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
