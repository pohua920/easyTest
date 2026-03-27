package cn.com.sinosoft.dms.vo;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.com.sinosoft.dms.model.PrpDriskClauseKindMinPremiumId;

/**
 * POJO��PrpDriskClauseKindMinPremium
 */
@Entity
@Table(name = "prpdriskclausekindminpremium")
public class PrpDriskClauseKindMinPremium implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������̶����ʶ */
	private PrpDriskClauseKindMinPremiumId id;

	/** �������δ��� */
	private String kindCode;

	/** ������˻���� */
	private String comCode;

	/** ��������շѱұ� */
	private String lowestPremCurrency;

	/** ��������շѽ�� */
	private BigDecimal lowestPremium;

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

	/** ���Ա�ע */
	private String remark;

	/** ���Ա�־λ */
	private String flag;

	/**
	 * ��PrpDriskClauseKindMinPremium��Ĭ�Ϲ��췽��
	 */
	public PrpDriskClauseKindMinPremium() {
	}

	/**       
	 * ������̶����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskcode")),
			@AttributeOverride(name = "riskKCSerialNo", column = @Column(name = "riskkcserialno")),
			@AttributeOverride(name = "clauseCode", column = @Column(name = "clausecode")),
			@AttributeOverride(name = "clauseKindID", column = @Column(name = "clausekindid")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "serialno")) })
	public PrpDriskClauseKindMinPremiumId getId() {
		return this.id;
	}

	/**       
	 * ������̶����ʶ��setter����
	 */
	public void setId(PrpDriskClauseKindMinPremiumId id) {
		this.id = id;
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
	 * ��������շѱұ��getter����
	 */

	@Column(name = "lowestpremcurrency")
	public String getLowestPremCurrency() {
		return this.lowestPremCurrency;
	}

	/**       
	 * ��������շѱұ��setter����
	 */
	public void setLowestPremCurrency(String lowestPremCurrency) {
		this.lowestPremCurrency = lowestPremCurrency;
	}

	/**       
	 * ��������շѽ���getter����
	 */

	@Column(name = "lowestpremium")
	public BigDecimal getLowestPremium() {
		return this.lowestPremium;
	}

	/**       
	 * ��������շѽ���setter����
	 */
	public void setLowestPremium(BigDecimal lowestPremium) {
		this.lowestPremium = lowestPremium;
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
