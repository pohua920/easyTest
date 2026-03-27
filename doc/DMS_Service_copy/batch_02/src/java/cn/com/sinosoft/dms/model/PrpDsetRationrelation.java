package cn.com.sinosoft.dms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;



@Entity
@Table(name="PrpDsetRationrelation")
public class PrpDsetRationrelation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrpDsetRationrelationId id;
	private PrpDration ration;
	/**��Ʒ����*/
	private PrpDset prpDset;
	/**������Q */
	private String rationName;
	/**��Ч���� */
	private Date validDate;
	/**ʧЧ����*/
	private Date invalidDate;
	/**��Ч��ӛ*/
	private String validInd;
	/**�A���ֶ�1*/
	private String tcol1;
	/**�A���ֶ�2*/
	private String tcol2;
	/**�A���ֶ�3*/
	private String tcol3;
	public PrpDsetRationrelation() {
	}
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "setCode", column = @Column(name = "SETCODE")),
			@AttributeOverride(name = "rationCode", column = @Column(name = "RATIONCODE")) })
	public PrpDsetRationrelationId getId() {
		return id;
	}
	public void setId(PrpDsetRationrelationId id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="RATIONCODE", nullable=false, insertable=false, updatable=false)
	//@NotFound(action = NotFoundAction.IGNORE)
	public PrpDration getRation() {
		return ration;
	}
	public void setRation(PrpDration ration) {
		this.ration = ration;
	}
	@Column(name="rationName")
	public String getRationName() {
		return rationName;
	}
	public void setRationName(String rationName) {
		this.rationName = rationName;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="validDate")
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="invalidDate")
	public Date getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}
	@Column(name="validInd")
	public String getValidInd() {
		return validInd;
	}
	public void setValidInd(String validInd) {
		this.validInd = validInd;
	}
	@Column(name="tcol1")
	public String getTcol1() {
		return tcol1;
	}
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}
	@Column(name="tcol2")
	public String getTcol2() {
		return tcol2;
	}
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}
	@Column(name="tcol3")
	public String getTcol3() {
		return tcol3;
	}
	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="SETCODE", nullable=false, insertable=false, updatable=false)
	//@NotFound(action = NotFoundAction.IGNORE)
	public PrpDset getPrpDset() {
		return prpDset;
	}
	public void setPrpDset(PrpDset prpDset) {
		this.prpDset = prpDset;
	}
	
}
