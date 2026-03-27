package cn.com.sinosoft.dms.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prpyddagent")
public class PrpYDDagent {

	private String id;
	
	private String serialNo;
	
	private String businessSource;
	
	private String businessSourceName;
	
	private String saleComCode;
	
	private String saleComName;
	
	private String agentId;
	
	private String agentName;
	
	private String servicePersonCode;
	
	private String servicePersonName;
	
	private String verifyRemark;
	
	private String unitCode;
	
	private String introducerId;
	
	private String introducerName;
	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name="serialNo")
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	@Column(name="businessSource")
	public String getBusinessSource() {
		return businessSource;
	}

	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}
	@Column(name="businessSourceName")
	public String getBusinessSourceName() {
		return businessSourceName;
	}

	public void setBusinessSourceName(String businessSourceName) {
		this.businessSourceName = businessSourceName;
	}
	@Column(name="saleComCode")
	public String getSaleComCode() {
		return saleComCode;
	}

	public void setSaleComCode(String saleComCode) {
		this.saleComCode = saleComCode;
	}
	@Column(name="saleComName")
	public String getSaleComName() {
		return saleComName;
	}

	public void setSaleComName(String saleComName) {
		this.saleComName = saleComName;
	}
	@Column(name="agentId")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	@Column(name="agentName")
	public String getAgentName() {
		return agentName;
	}
	
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	@Column(name="servicePersonCode")
	public String getServicePersonCode() {
		return servicePersonCode;
	}

	public void setServicePersonCode(String servicePersonCode) {
		this.servicePersonCode = servicePersonCode;
	}
	@Column(name="servicePersonName")
	public String getServicePersonName() {
		return servicePersonName;
	}

	public void setServicePersonName(String servicePersonName) {
		this.servicePersonName = servicePersonName;
	}
	@Column(name="verifyRemark")
	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}
	@Column(name="unitCode")
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	@Column(name="introducerId")
	public String getIntroducerId() {
		return introducerId;
	}

	public void setIntroducerId(String introducerId) {
		this.introducerId = introducerId;
	}
	@Column(name="introducerName")
	public String getIntroducerName() {
		return introducerName;
	}

	public void setIntroducerName(String introducerName) {
		this.introducerName = introducerName;
	}
	
	
}
