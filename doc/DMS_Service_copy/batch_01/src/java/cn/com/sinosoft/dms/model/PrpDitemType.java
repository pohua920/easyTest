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
 * POJO��prpDitemType
 */
@Entity
@Table(name = "PRPDITEMTYPE")
public class PrpDitemType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Ա������ */
	private String itemType;

	/** ���Ա�����ͼ���������� */
	private String itemTypeCName;

	/** ���Ա�����ͷ���������� */
	private String itemTypeTName;

	/** ���Ա������Ӣ����� */
	private String itemTypeEName;

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
	 * ��prpDitemType��Ĭ�Ϲ��췽��
	 */
	public PrpDitemType() {
	}

	/**       
	 * ���Ա�����͵�getter����
	 */
	@Id
	@Column(name = "ITEMTYPE")
	public String getItemType() {
		return this.itemType;
	}

	/**       
	 * ���Ա�����͵�setter����
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**       
	 * ���Ա�����ͼ���������Ƶ�getter����
	 */

	@Column(name = "ITEMTYPECNAME")
	public String getItemTypeCName() {
		return this.itemTypeCName;
	}

	/**       
	 * ���Ա�����ͼ���������Ƶ�setter����
	 */
	public void setItemTypeCName(String itemTypeCName) {
		this.itemTypeCName = itemTypeCName;
	}

	/**       
	 * ���Ա�����ͷ���������Ƶ�getter����
	 */

	@Column(name = "ITEMTYPETNAME")
	public String getItemTypeTName() {
		return this.itemTypeTName;
	}

	/**       
	 * ���Ա�����ͷ���������Ƶ�setter����
	 */
	public void setItemTypeTName(String itemTypeTName) {
		this.itemTypeTName = itemTypeTName;
	}

	/**       
	 * ���Ա������Ӣ����Ƶ�getter����
	 */

	@Column(name = "ITEMTYPEENAME")
	public String getItemTypeEName() {
		return this.itemTypeEName;
	}

	/**       
	 * ���Ա������Ӣ����Ƶ�setter����
	 */
	public void setItemTypeEName(String itemTypeEName) {
		this.itemTypeEName = itemTypeEName;
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
	@Column(name = "INVALIDDATE")
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
