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
 * POJO��prpDaccountInfo
 */
@Entity
@Table(name = "prpdaccountinfo")
public class PrpDaccountInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Ժ�����ˮ�� */
	private String accountID;

	/** ���Ժ������� */
	private String accountDesc;

	/** ���Թ���������� */
	private String classCode;

	/** ���Թ������ִ��� */
	private String riskCode;

	/** ���������� */
	private String clauseCode;

	/** �������δ��� */
	private String kindCode;

	/** ���Դ����㼶(1���֡�2��Ʒ��3��4���Ρ�5�������) */
	private String createLevel;

	/** ���Ժ�������(1����2�ٱ���3ͳ�ơ�4������) */
	private String accountType;

	/** ���Ժ������ */
	private String accountCode;

	/** ���Դ���ʱ�� */
	private Date createTime;

	/** ������Ϣ������Ա */
	private String creatorCode;

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
	 * ��prpDaccountInfo��Ĭ�Ϲ��췽��
	 */
	public PrpDaccountInfo() {
	}

	/**       
	 * ���Ժ�����ˮ�ŵ�getter����
	 */
	@Id
	@Column(name = "accountid")
	public String getAccountID() {
		return this.accountID;
	}

	/**       
	 * ���Ժ�����ˮ�ŵ�setter����
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	/**       
	 * ���Ժ��������getter����
	 */

	@Column(name = "accountdesc")
	public String getAccountDesc() {
		return this.accountDesc;
	}

	/**       
	 * ���Ժ��������setter����
	 */
	public void setAccountDesc(String accountDesc) {
		this.accountDesc = accountDesc;
	}

	/**       
	 * ���Թ�����������getter����
	 */

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
	 * �����������getter����
	 */

	@Column(name = "clausecode")
	public String getClauseCode() {
		return this.clauseCode;
	}

	/**       
	 * �����������setter����
	 */
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}

	/**       
	 * �������δ����getter����
	 */

	@Column(name = "kindcode")
	public String getKindCode() {
		return this.kindCode;
	}

	/**       
	 * �������δ����setter����
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**       
	 * ���Դ����㼶(1���֡�2��Ʒ��3��4���Ρ�5�������)��getter����
	 */

	@Column(name = "createlevel")
	public String getCreateLevel() {
		return this.createLevel;
	}

	/**       
	 * ���Դ����㼶(1���֡�2��Ʒ��3��4���Ρ�5�������)��setter����
	 */
	public void setCreateLevel(String createLevel) {
		this.createLevel = createLevel;
	}

	/**       
	 * ���Ժ�������(1����2�ٱ���3ͳ�ơ�4������)��getter����
	 */

	@Column(name = "accounttype")
	public String getAccountType() {
		return this.accountType;
	}

	/**       
	 * ���Ժ�������(1����2�ٱ���3ͳ�ơ�4������)��setter����
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**       
	 * ���Ժ�������getter����
	 */

	@Column(name = "accountcode")
	public String getAccountCode() {
		return this.accountCode;
	}

	/**       
	 * ���Ժ�������setter����
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
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
