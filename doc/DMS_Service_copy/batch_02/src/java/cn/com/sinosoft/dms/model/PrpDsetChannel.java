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

@Entity
@Table(name="PrpDsetChannel")
public class PrpDsetChannel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**��Ʒ���� */
	private	PrpDsetChannelId id;
	/**��Ʒ����*/
	private PrpDset prpDset;
	/**ͨ·�e��a */
	private	String channelCode;
	/**ͨ·�e��Q*/
	private String channelName;
	/**�I�Ձ�Դ��a */
	private	String businessOriginCode;
	/**�I�Ձ�Դ��Q*/
	private String businessOriginName;
	/**���k�˾�̖*/
	private	String handlerCode;
	/**���k����Q*/
	private String handlerName;
	/**�����ˆT��̖*/
	private	String waiterCode;
	/**�����ˆT��Q*/
	private String waiterName;
	/**��Ч���� */
	private	Date validDate;
	/**ʧЧ���� */
	private	Date invalidDate;
	/**��Ч��� */
	private	String validInd;
	/**�����ߴ�̖*/
	private	String createrCode;
	/**��������*/
	private Date createTime;
	/**���´�̖*/
	private String updaterCode;
	/**�޸�����*/
	private Date updateTime;

	/**�A���ֶ�1*/
	private String tcol1;
	/**�A���ֶ�2*/
	private String tcol2;
	/**�A���ֶ�3*/
	private String tcol3;
	
	public PrpDsetChannel() {
	}
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "setCode", column = @Column(name = "setCode")),
			@AttributeOverride(name = "setChannelNo", column = @Column(name = "setChannelNo")) })
	public PrpDsetChannelId getId() {
		return id;
	}

	public void setId(PrpDsetChannelId id) {
		this.id = id;
	}
	@Column(name="channelCode")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	@Column(name="channelName")
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	@Column(name="businessOriginCode")
	public String getBusinessOriginCode() {
		return businessOriginCode;
	}

	public void setBusinessOriginCode(String businessOriginCode) {
		this.businessOriginCode = businessOriginCode;
	}
	@Column(name="businessOriginName")
	public String getBusinessOriginName() {
		return businessOriginName;
	}

	public void setBusinessOriginName(String businessOriginName) {
		this.businessOriginName = businessOriginName;
	}
	@Column(name="handlerCode")
	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}
	@Column(name="handlerName")
	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	@Column(name="waiterCode")
	public String getWaiterCode() {
		return waiterCode;
	}

	public void setWaiterCode(String waiterCode) {
		this.waiterCode = waiterCode;
	}
	@Column(name="waiterName")
	public String getWaiterName() {
		return waiterName;
	}

	public void setWaiterName(String waiterName) {
		this.waiterName = waiterName;
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
	@Column(name="createrCode")
	public String getCreaterCode() {
		return createrCode;
	}
	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name="updaterCode")
	public String getUpdaterCode() {
		return updaterCode;
	}
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="setCode", nullable=false, insertable=false, updatable=false)
	public PrpDset getPrpDset() {
		return prpDset;
	}
	public void setPrpDset(PrpDset prpDset) {
		this.prpDset = prpDset;
	}
	
}
