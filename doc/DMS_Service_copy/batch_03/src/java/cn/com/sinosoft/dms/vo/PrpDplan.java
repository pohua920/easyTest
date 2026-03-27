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
 * POJO��prpDplan
 */
@Entity
@Table(name = "prpdplan")
public class PrpDplan implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Է������� */
	private String planCode;

	/** ���Է�������������� */
	private String planCName;

	/** ���Է�������������� */
	private String planTName;

	/** ���Է��������� */
	private String planSName;

	/** ���Է���Ӣ����� */
	private String planEName;

	/** �����Ƿ񶨶� */
	private String isFixedFlag;

	/** ���Կ�ܴ��� */
	private String frameCode;

	/** ���Կ�ܼ���������� */
	private String frameCName;

	/** ���Թ������ִ��� */
	private String riskCode;

	/** ������ϼ���������� */
	private String riskCName;

	/** ���Բ�Ʒ����(��һ��Ʒ���ۺϲ�Ʒ����ϲ�Ʒ) */
	private String riskAttribute;

	/** ������������㼶(ͳ�ܹ�˾ʡ��˾�й�˾) */
	private String saleAreaLevel;

	/** ���������������(�ö��ŷָ�) */
	private String saleAreaCode;

	/** ���������������(�ö��ŷָ�) */
	private String saleAreaName;

	/** ���Դ����� */
	private String createrCode;

	/** ���Դ���ʱ�� */
	private Date createTime;

	/** �������¸��²�����Ա */
	private String updaterCode;

	/** ��������޸�ʱ�� */
	private Date updateTime;

	/** �����ı����� */
	private String contentNumber;

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
	 * ��prpDplan��Ĭ�Ϲ��췽��
	 */
	public PrpDplan() {
	}

	/**       
	 * ���Է��������getter����
	 */
	@Id
	@Column(name = "plancode")
	public String getPlanCode() {
		return this.planCode;
	}

	/**       
	 * ���Է��������setter����
	 */
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	/**       
	 * ���Է�������������Ƶ�getter����
	 */

	@Column(name = "plancname")
	public String getPlanCName() {
		return this.planCName;
	}

	/**       
	 * ���Է�������������Ƶ�setter����
	 */
	public void setPlanCName(String planCName) {
		this.planCName = planCName;
	}

	/**       
	 * ���Է�������������Ƶ�getter����
	 */

	@Column(name = "plantname")
	public String getPlanTName() {
		return this.planTName;
	}

	/**       
	 * ���Է�������������Ƶ�setter����
	 */
	public void setPlanTName(String planTName) {
		this.planTName = planTName;
	}

	/**       
	 * ���Է��������Ƶ�getter����
	 */

	@Column(name = "plansname")
	public String getPlanSName() {
		return this.planSName;
	}

	/**       
	 * ���Է��������Ƶ�setter����
	 */
	public void setPlanSName(String planSName) {
		this.planSName = planSName;
	}

	/**       
	 * ���Է���Ӣ����Ƶ�getter����
	 */

	@Column(name = "planename")
	public String getPlanEName() {
		return this.planEName;
	}

	/**       
	 * ���Է���Ӣ����Ƶ�setter����
	 */
	public void setPlanEName(String planEName) {
		this.planEName = planEName;
	}

	/**       
	 * �����Ƿ񶨶��getter����
	 */

	@Column(name = "isfixedflag")
	public String getIsFixedFlag() {
		return this.isFixedFlag;
	}

	/**       
	 * �����Ƿ񶨶��setter����
	 */
	public void setIsFixedFlag(String isFixedFlag) {
		this.isFixedFlag = isFixedFlag;
	}

	/**       
	 * ���Կ�ܴ����getter����
	 */

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
	 * ���Թ������ִ����getter����
	 */

	@Column(name = "riskcode")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**       
	 * ���Թ������ִ����setter����
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**       
	 * ������ϼ���������Ƶ�getter����
	 */

	@Column(name = "riskcname")
	public String getRiskCName() {
		return this.riskCName;
	}

	/**       
	 * ������ϼ���������Ƶ�setter����
	 */
	public void setRiskCName(String riskCName) {
		this.riskCName = riskCName;
	}

	/**       
	 * ���Բ�Ʒ����(��һ��Ʒ���ۺϲ�Ʒ����ϲ�Ʒ)��getter����
	 */

	@Column(name = "riskattribute")
	public String getRiskAttribute() {
		return this.riskAttribute;
	}

	/**       
	 * ���Բ�Ʒ����(��һ��Ʒ���ۺϲ�Ʒ����ϲ�Ʒ)��setter����
	 */
	public void setRiskAttribute(String riskAttribute) {
		this.riskAttribute = riskAttribute;
	}

	/**       
	 * ������������㼶(ͳ�ܹ�˾ʡ��˾�й�˾)��getter����
	 */

	@Column(name = "salearealevel")
	public String getSaleAreaLevel() {
		return this.saleAreaLevel;
	}

	/**       
	 * ������������㼶(ͳ�ܹ�˾ʡ��˾�й�˾)��setter����
	 */
	public void setSaleAreaLevel(String saleAreaLevel) {
		this.saleAreaLevel = saleAreaLevel;
	}

	/**       
	 * ���������������(�ö��ŷָ�)��getter����
	 */

	@Column(name = "saleareacode")
	public String getSaleAreaCode() {
		return this.saleAreaCode;
	}

	/**       
	 * ���������������(�ö��ŷָ�)��setter����
	 */
	public void setSaleAreaCode(String saleAreaCode) {
		this.saleAreaCode = saleAreaCode;
	}

	/**       
	 * ���������������(�ö��ŷָ�)��getter����
	 */

	@Column(name = "saleareaname")
	public String getSaleAreaName() {
		return this.saleAreaName;
	}

	/**       
	 * ���������������(�ö��ŷָ�)��setter����
	 */
	public void setSaleAreaName(String saleAreaName) {
		this.saleAreaName = saleAreaName;
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
