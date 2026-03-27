package cn.com.sinosoft.dms.vo;

// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Type;

import cn.com.sinosoft.dms.model.PrpDriskEngageId;

/**
 * POJO��prpDriskEngage modify by liuhao 20140221
 */
@Entity
@Table(name = "prpdriskengage")
public class PrpDriskEngage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ������ˮ��ID */
	private PrpDriskEngageId id;

	/** �����ر�Լ��������� */
	private String engageCName;

	/** �����ر�Լ��Ӣ����� */
	private String engageEName;

	/** ���Կ�ѡ���δ��� */
	private String kindCodeContent;

	/** �������ֱ�ʶ */
	private String language;

	/** �����ر�Լ������ */
	private String engageDesc;

	/** ���Գб��Ƿ�ɸ�(0���ɸ�,1�ɸ�) */
	private String changeAble;

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

	/** ���Ա�־�ֶ� */
	private String flag;

	/** ���Գб��Զ�����ʶ */
	private String autoFlag;
	private String oldClauseCode;
	private String oldEngageCode;
	/**
	 * ��prpDriskEngage��Ĭ�Ϲ��췽��
	 */
	public PrpDriskEngage() {
	}

	/**       
	 * ������ˮ��ID��getter����
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskcode")),
			@AttributeOverride(name = "clauseCode", column = @Column(name = "clausecode")),
			@AttributeOverride(name = "engageCode", column = @Column(name = "engagecode")) })
	public PrpDriskEngageId getId() {
		return this.id;
	}

	/**       
	 * ������ˮ��ID��setter����
	 */
	public void setId(PrpDriskEngageId id) {
		this.id = id;
	}

	/**       
	 * �����ر�Լ��������Ƶ�getter����
	 */

	@Column(name = "engagecname")
	public String getEngageCName() {
		return this.engageCName;
	}

	/**       
	 * �����ر�Լ��������Ƶ�setter����
	 */
	public void setEngageCName(String engageCName) {
		this.engageCName = engageCName;
	}

	/**       
	 * �����ر�Լ��Ӣ����Ƶ�getter����
	 */

	@Column(name = "engageename")
	public String getEngageEName() {
		return this.engageEName;
	}

	/**       
	 * �����ر�Լ��Ӣ����Ƶ�setter����
	 */
	public void setEngageEName(String engageEName) {
		this.engageEName = engageEName;
	}

	/**       
	 * ���Կ�ѡ���δ����getter����
	 */

	@Column(name = "kindcodecontent")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
	public String getKindCodeContent() {
		return this.kindCodeContent;
	}

	/**       
	 * ���Կ�ѡ���δ����setter����
	 */
	public void setKindCodeContent(String kindCodeContent) {
		this.kindCodeContent = kindCodeContent;
	}

	/**       
	 * �������ֱ�ʶ��getter����
	 */

	@Column(name = "language")
	public String getLanguage() {
		return this.language;
	}

	/**       
	 * �������ֱ�ʶ��setter����
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**       
	 * �����ر�Լ�������getter����
	 */

	@Column(name = "engagedesc")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
	public String getEngageDesc() {
		return this.engageDesc;
	}

	/**       
	 * �����ر�Լ�������setter����
	 */
	public void setEngageDesc(String engageDesc) {
		this.engageDesc = engageDesc;
	}

	/**       
	 * ���Գб��Ƿ�ɸ�(0���ɸ�,1�ɸ�)��getter����
	 */

	@Column(name = "changeable")
	public String getChangeAble() {
		return this.changeAble;
	}

	/**       
	 * ���Գб��Ƿ�ɸ�(0���ɸ�,1�ɸ�)��setter����
	 */
	public void setChangeAble(String changeAble) {
		this.changeAble = changeAble;
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
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
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
	 * ���Ա�־�ֶε�getter����
	 */

	@Column(name = "flag")
	public String getFlag() {
		return this.flag;
	}

	/**       
	 * ���Ա�־�ֶε�setter����
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**       
	 * ���Գб��Զ�����ʶ��getter����
	 */

	@Column(name = "autoflag")
	public String getAutoFlag() {
		return this.autoFlag;
	}

	/**       
	 * ���Գб��Զ�����ʶ��setter����
	 */
	public void setAutoFlag(String autoFlag) {
		this.autoFlag = autoFlag;
	}

	@Column(name = "OLDCLAUSECODE")
	public String getOldClauseCode() {
		return this.oldClauseCode;
	}

	/**       
	 * ���Գб��Զ�����ʶ��setter����
	 */
	public void setOldClauseCode(String oldClauseCode) {
		this.oldClauseCode = oldClauseCode;
	}
	@Column(name = "OLDENGAGECODE")
	public String getOldEngageCode() {
		return this.oldEngageCode;
	}

	/**       
	 * ���Գб��Զ�����ʶ��setter����
	 */
	public void setOldEngageCode(String oldEngageCode) {
		this.oldEngageCode = oldEngageCode;
	}

}
