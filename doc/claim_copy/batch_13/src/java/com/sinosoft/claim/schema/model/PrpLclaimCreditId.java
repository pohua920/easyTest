package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class PrpLclaimCreditId implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 业务号码  */
	private String businessNo;
	/** 节点名称  */
	private String nodeType;
	/** 序号  */
	private Integer serialNo = 1;
	
	@Column(name = "businessNo")
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	@Column(name = "nodeType")
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	@Column(name = "serialNo")
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(!(obj instanceof PrpLclaimCreditId)){
			return false;
		}
		PrpLclaimCreditId id = (PrpLclaimCreditId) obj;
		return (this.getBusinessNo() == id.getBusinessNo() || (this.getBusinessNo() != null&&this.getBusinessNo().equals(id.getBusinessNo())))
				&&(this.getNodeType() == id.getNodeType() || (this.getNodeType() != null && this.getNodeType().equals(id.getNodeType())))
				&&(this.getSerialNo() == id.getSerialNo() || (this.getSerialNo()!=null && this.getSerialNo().equals(id.getSerialNo())));
	}
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getBusinessNo() == null ? 0 : this.getBusinessNo().hashCode());
		result = 37 * result + (this.getNodeType() == null ? 0 : this.getNodeType().hashCode());
		result = 37 * result + (this.getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
