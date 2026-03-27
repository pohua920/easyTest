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

import cn.com.sinosoft.dms.model.PrpDriskShortRateId;

/**
 * POJO��PrpDriskShortRate
 */
@Entity
@Table(name = "prpdriskshortrate")
public class PrpDriskShortRate implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������̶����ʶ */
	private PrpDriskShortRateId id;

	/** ���Զ��ڷ������ */
	private String shortRateName;

	/** ���Զ��ڷ������� */
	private String rateType;

	/** �������޼���� */
	private String lowerOperator;

	/** �������� */
	private BigDecimal lower;

	/** �������޼���� */
	private String upperOperator;

	/** �������� */
	private BigDecimal upper;

	/** ���Զ��ڷ��ʷ��� */
	private BigDecimal shortRateNumerator;

	/** ����shortratedenominat */
	private BigDecimal shortratedenominat;

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
	 * ��PrpDriskShortRate��Ĭ�Ϲ��췽��
	 */
	public PrpDriskShortRate() {
	}

	/**       
	 * ������̶����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskcode")),
			@AttributeOverride(name = "shortRateID", column = @Column(name = "shortrateid")),
			@AttributeOverride(name = "clauseCode", column = @Column(name = "clausecode")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "serialno")) })
	public PrpDriskShortRateId getId() {
		return this.id;
	}

	/**       
	 * ������̶����ʶ��setter����
	 */
	public void setId(PrpDriskShortRateId id) {
		this.id = id;
	}

	/**       
	 * ���Զ��ڷ�����Ƶ�getter����
	 */

	@Column(name = "shortratename")
	public String getShortRateName() {
		return this.shortRateName;
	}

	/**       
	 * ���Զ��ڷ�����Ƶ�setter����
	 */
	public void setShortRateName(String shortRateName) {
		this.shortRateName = shortRateName;
	}

	/**       
	 * ���Զ��ڷ������͵�getter����
	 */

	@Column(name = "ratetype")
	public String getRateType() {
		return this.rateType;
	}

	/**       
	 * ���Զ��ڷ������͵�setter����
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**       
	 * �������޼�����getter����
	 */

	@Column(name = "loweroperator")
	public String getLowerOperator() {
		return this.lowerOperator;
	}

	/**       
	 * �������޼�����setter����
	 */
	public void setLowerOperator(String lowerOperator) {
		this.lowerOperator = lowerOperator;
	}

	/**       
	 * �������޵�getter����
	 */

	@Column(name = "lower")
	public BigDecimal getLower() {
		return this.lower;
	}

	/**       
	 * �������޵�setter����
	 */
	public void setLower(BigDecimal lower) {
		this.lower = lower;
	}

	/**       
	 * �������޼�����getter����
	 */

	@Column(name = "upperoperator")
	public String getUpperOperator() {
		return this.upperOperator;
	}

	/**       
	 * �������޼�����setter����
	 */
	public void setUpperOperator(String upperOperator) {
		this.upperOperator = upperOperator;
	}

	/**       
	 * �������޵�getter����
	 */

	@Column(name = "upper")
	public BigDecimal getUpper() {
		return this.upper;
	}

	/**       
	 * �������޵�setter����
	 */
	public void setUpper(BigDecimal upper) {
		this.upper = upper;
	}

	/**       
	 * ���Զ��ڷ��ʷ��ӵ�getter����
	 */

	@Column(name = "shortratenumerator")
	public BigDecimal getShortRateNumerator() {
		return this.shortRateNumerator;
	}

	/**       
	 * ���Զ��ڷ��ʷ��ӵ�setter����
	 */
	public void setShortRateNumerator(BigDecimal shortRateNumerator) {
		this.shortRateNumerator = shortRateNumerator;
	}

	/**       
	 * ����shortratedenominat��getter����
	 */

	@Column(name = "shortratedenominat")
	public BigDecimal getShortratedenominat() {
		return this.shortratedenominat;
	}

	/**       
	 * ����shortratedenominat��setter����
	 */
	public void setShortratedenominat(BigDecimal shortratedenominat) {
		this.shortratedenominat = shortratedenominat;
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
