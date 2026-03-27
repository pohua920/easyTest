package cn.com.sinosoft.dms.model;


// ���ù��� Hibernate Tools 3.2.4.GA (sinosoft version) ��ɣ������ֹ��޸ġ�

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO��prpDdisaster
 */
@Entity
@Table(name = "prpddisaster")
public class PrpDdisaster implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** ���Ծ��ֱ��� */
	private String disasterCode;

	/** ����disastername */
	private String disasterName;

	/** ���Կ�ʼ���� */
	private Date startDate;

	/** ���Կ�ʼСʱ */
	private Integer startHour;

	/** ������ֹ���� */
	private Date endDate;

	/** ������ֹСʱ */
	private Integer endHour;

	/** ���Բ�����Χ */
	private String affectArea;

	/** ���Ծ������� */
	private String details;

	/** ���Ծ���ͬ���� */
	private String synoCode;

	/** ���Ծ��ֹر�ʱ�� */
	private Date closeDate;

	/** ���Ա�ע */
	private String marks;

	/** ���Բ���Ա */
	private String operatorCode;

	/** ������������ */
	private Date inputDate;

	/** ���Գ�� */
	private String makeCom;

	/** ������Ч״̬(0��Ч1��Ч) */
	private String validStatus;

	/** ���Ա�־�ֶ� */
	private String flag;

	/**
	 * ��prpDdisaster��Ĭ�Ϲ��췽��
	 */
	public PrpDdisaster() {
	}

	/**       
	 * ���Ծ��ֱ����getter����
	 */
	@Id
	@Column(name = "disastercode")
	public String getDisasterCode() {
		return this.disasterCode;
	}

	/**       
	 * ���Ծ��ֱ����setter����
	 */
	public void setDisasterCode(String disasterCode) {
		this.disasterCode = disasterCode;
	}

	/**       
	 * ����disastername��getter����
	 */

	@Column(name = "disastername")
	public String getDisasterName() {
		return this.disasterName;
	}

	/**       
	 * ����disastername��setter����
	 */
	public void setDisasterName(String disasterName) {
		this.disasterName = disasterName;
	}

	/**       
	 * ���Կ�ʼ���ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "startdate")
	public Date getStartDate() {
		return this.startDate;
	}

	/**       
	 * ���Կ�ʼ���ڵ�setter����
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**       
	 * ���Կ�ʼСʱ��getter����
	 */

	@Column(name = "starthour")
	public Integer getStartHour() {
		return this.startHour;
	}

	/**       
	 * ���Կ�ʼСʱ��setter����
	 */
	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	/**       
	 * ������ֹ���ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "enddate")
	public Date getEndDate() {
		return this.endDate;
	}

	/**       
	 * ������ֹ���ڵ�setter����
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**       
	 * ������ֹСʱ��getter����
	 */

	@Column(name = "endhour")
	public Integer getEndHour() {
		return this.endHour;
	}

	/**       
	 * ������ֹСʱ��setter����
	 */
	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	/**       
	 * ���Բ�����Χ��getter����
	 */

	@Column(name = "affectarea")
	public String getAffectArea() {
		return this.affectArea;
	}

	/**       
	 * ���Բ�����Χ��setter����
	 */
	public void setAffectArea(String affectArea) {
		this.affectArea = affectArea;
	}

	/**       
	 * ���Ծ��������getter����
	 */

	@Column(name = "details")
	public String getDetails() {
		return this.details;
	}

	/**       
	 * ���Ծ��������setter����
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**       
	 * ���Ծ���ͬ�����getter����
	 */

	@Column(name = "synocode")
	public String getSynoCode() {
		return this.synoCode;
	}

	/**       
	 * ���Ծ���ͬ�����setter����
	 */
	public void setSynoCode(String synoCode) {
		this.synoCode = synoCode;
	}

	/**       
	 * ���Ծ��ֹر�ʱ���getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "closedate")
	public Date getCloseDate() {
		return this.closeDate;
	}

	/**       
	 * ���Ծ��ֹر�ʱ���setter����
	 */
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	/**       
	 * ���Ա�ע��getter����
	 */

	@Column(name = "marks")
	public String getMarks() {
		return this.marks;
	}

	/**       
	 * ���Ա�ע��setter����
	 */
	public void setMarks(String marks) {
		this.marks = marks;
	}

	/**       
	 * ���Բ���Ա��getter����
	 */

	@Column(name = "operatorcode")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**       
	 * ���Բ���Ա��setter����
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**       
	 * �����������ڵ�getter����
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "inputdate")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**       
	 * �����������ڵ�setter����
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**       
	 * ���Գ���getter����
	 */

	@Column(name = "makecom")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**       
	 * ���Գ���setter����
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	/**       
	 * ������Ч״̬(0��Ч1��Ч)��getter����
	 */

	@Column(name = "validstatus")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**       
	 * ������Ч״̬(0��Ч1��Ч)��setter����
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
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

}
