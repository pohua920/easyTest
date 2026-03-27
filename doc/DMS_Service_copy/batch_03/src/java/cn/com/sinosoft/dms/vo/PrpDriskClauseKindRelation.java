package cn.com.sinosoft.dms.vo;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.com.sinosoft.dms.model.PrpDriskClauseKindRelationId;

/**
 * POJO��PrpDriskClauseKindRelation
 */
@Entity
@Table(name = "prpdriskclausekindrelation")
public class PrpDriskClauseKindRelation implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������̶����ʶ */
	private PrpDriskClauseKindRelationId id;

	/** ���Դ�������(1.��� 2.��� 3.��Ʒ 4.���� 5.�ο�����) */
	private String codeType;

	/** ���Ա�У��������(����-�ָ�) */
	private String checkedCode;

	/** ���Ի������5��ʶ(0���⡢1��5) */
	private String relationFlag;

	/** ���Կ������ͱ�ʶ(0����ơ�1Ӳ����) */
	private String controlFlag;

	/** ����У�����(����-�ָ�) */
	private String checkCode;

	/** ������Ч���� */
	private Date validDate;

	/** ����ʧЧ���� */
	private Date invalidDate;

	/** ������Ч��־ */
	private String validInd;

	/** ����clausecontentnumbe */
	private String clausecontentnumbe;

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
	 * ��PrpDriskClauseKindRelation��Ĭ�Ϲ��췽��
	 */
	public PrpDriskClauseKindRelation() {
	}

	/**       
	 * ������̶����ʶ��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskcode")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "serialno")),
			@AttributeOverride(name = "clauseCode", column = @Column(name = "clausecode")),
			@AttributeOverride(name = "relationCode", column = @Column(name = "relationcode")) })
	public PrpDriskClauseKindRelationId getId() {
		return this.id;
	}

	/**       
	 * ������̶����ʶ��setter����
	 */
	public void setId(PrpDriskClauseKindRelationId id) {
		this.id = id;
	}

	/**       
	 * ���Դ�������(1.��� 2.��� 3.��Ʒ 4.���� 5.�ο�����)��getter����
	 */

	@Column(name = "codetype")
	public String getCodeType() {
		return this.codeType;
	}

	/**       
	 * ���Դ�������(1.��� 2.��� 3.��Ʒ 4.���� 5.�ο�����)��setter����
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**       
	 * ���Ա�У��������(����-�ָ�)��getter����
	 */

	@Column(name = "checkedcode")
	public String getCheckedCode() {
		return this.checkedCode;
	}

	/**       
	 * ���Ա�У��������(����-�ָ�)��setter����
	 */
	public void setCheckedCode(String checkedCode) {
		this.checkedCode = checkedCode;
	}

	/**       
	 * ���Ի������5��ʶ(0���⡢1��5)��getter����
	 */

	@Column(name = "relationflag")
	public String getRelationFlag() {
		return this.relationFlag;
	}

	/**       
	 * ���Ի������5��ʶ(0���⡢1��5)��setter����
	 */
	public void setRelationFlag(String relationFlag) {
		this.relationFlag = relationFlag;
	}

	/**       
	 * ���Կ������ͱ�ʶ(0����ơ�1Ӳ����)��getter����
	 */

	@Column(name = "controlflag")
	public String getControlFlag() {
		return this.controlFlag;
	}

	/**       
	 * ���Կ������ͱ�ʶ(0����ơ�1Ӳ����)��setter����
	 */
	public void setControlFlag(String controlFlag) {
		this.controlFlag = controlFlag;
	}

	/**       
	 * ����У�����(����-�ָ�)��getter����
	 */

	@Column(name = "checkcode")
	public String getCheckCode() {
		return this.checkCode;
	}

	/**       
	 * ����У�����(����-�ָ�)��setter����
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
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
	 * ����clausecontentnumbe��getter����
	 */

	@Column(name = "clausecontentnumbe")
	public String getClausecontentnumbe() {
		return this.clausecontentnumbe;
	}

	/**       
	 * ����clausecontentnumbe��setter����
	 */
	public void setClausecontentnumbe(String clausecontentnumbe) {
		this.clausecontentnumbe = clausecontentnumbe;
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
