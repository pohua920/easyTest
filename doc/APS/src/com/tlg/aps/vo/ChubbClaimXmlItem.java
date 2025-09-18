package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** mantis：MOB0020，處理人員：BI086，需求單編號：MOB0020 理賠盜打詐騙API及XML作業 */

@XmlRootElement(name="Item")
public class ChubbClaimXmlItem {

	
	
	private String mtnNo;
	
	private String contractId;

	private String matnrNo;

	private String imei;

	private String claimType;

	private String notifDate;

	private Integer listprice1;

	private String causeDescription1;

	private String finalAuth;

	private String specialApproval;

	private String eventDate;

	private String eventCause;

	private String policyNo;
	
	private Integer asuClaimAmount;
	
	private String erDate;

	private String finishDate;

	private String partDescription1;
	
	private String partDescription2;
	
	private String partDescription3;
	
	private String partDescription4;
	
	private String partDescription5;
	
	private String partDescription6;
	
	private String partDescription7;
	
	private String partDescription8;
	
	private String partDescription9;
	
	private String partDescription10;

	@XmlElement(name="MTN_NO")
	public String getMtnNo() {
		return mtnNo;
	}

	public void setMtnNo(String mtnNo) {
		this.mtnNo = mtnNo;
	}

	@XmlElement(name="CONTRACT_ID")
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@XmlElement(name="MATNR_NO")
	public String getMatnrNo() {
		return matnrNo;
	}

	public void setMatnrNo(String matnrNo) {
		this.matnrNo = matnrNo;
	}

	@XmlElement(name="IMEI")
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@XmlElement(name="CLAIM_TYPE")
	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	@XmlElement(name="NOTIF_DATE")
	public String getNotifDate() {
		return notifDate;
	}

	public void setNotifDate(String notifDate) {
		this.notifDate = notifDate;
	}

	@XmlElement(name="LISTPRICE1")
	public Integer getListprice1() {
		return listprice1;
	}

	public void setListprice1(Integer listprice1) {
		this.listprice1 = listprice1;
	}

	@XmlElement(name="CAUSE_DESCRIPTION1")
	public String getCauseDescription1() {
		return causeDescription1;
	}

	public void setCauseDescription1(String causeDescription1) {
		this.causeDescription1 = causeDescription1;
	}
	
	@XmlElement(name="FINAL_AUTH")
	public String getFinalAuth() {
		return finalAuth;
	}

	public void setFinalAuth(String finalAuth) {
		this.finalAuth = finalAuth;
	}

	@XmlElement(name="SPECIAL_APPROVAL")
	public String getSpecialApproval() {
		return specialApproval;
	}

	public void setSpecialApproval(String specialApproval) {
		this.specialApproval = specialApproval;
	}

	@XmlElement(name="EVENT_DATE")
	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	@XmlElement(name="EVENT_CAUSE")
	public String getEventCause() {
		return eventCause;
	}

	public void setEventCause(String eventCause) {
		this.eventCause = eventCause;
	}

	@XmlElement(name="POLICY_NO")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@XmlElement(name="ASU_CLAIM_AMOUNT")
	public Integer getAsuClaimAmount() {
		return asuClaimAmount;
	}

	public void setAsuClaimAmount(Integer asuClaimAmount) {
		this.asuClaimAmount = asuClaimAmount;
	}

	@XmlElement(name="ER_DATE")
	public String getErDate() {
		return erDate;
	}

	public void setErDate(String erDate) {
		this.erDate = erDate;
	}

	@XmlElement(name="FINISH_DATE")
	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	@XmlElement(name="PART_DESCRIPTION1")
	public String getPartDescription1() {
		return partDescription1;
	}

	public void setPartDescription1(String partDescription1) {
		this.partDescription1 = partDescription1;
	}

	@XmlElement(name="PART_DESCRIPTION2")
	public String getPartDescription2() {
		return partDescription2;
	}

	public void setPartDescription2(String partDescription2) {
		this.partDescription2 = partDescription2;
	}

	@XmlElement(name="PART_DESCRIPTION3")
	public String getPartDescription3() {
		return partDescription3;
	}

	public void setPartDescription3(String partDescription3) {
		this.partDescription3 = partDescription3;
	}

	@XmlElement(name="PART_DESCRIPTION4")
	public String getPartDescription4() {
		return partDescription4;
	}

	public void setPartDescription4(String partDescription4) {
		this.partDescription4 = partDescription4;
	}
	
	@XmlElement(name="PART_DESCRIPTION5")
	public String getPartDescription5() {
		return partDescription5;
	}

	public void setPartDescription5(String partDescription5) {
		this.partDescription5 = partDescription5;
	}

	@XmlElement(name="PART_DESCRIPTION6")
	public String getPartDescription6() {
		return partDescription6;
	}

	public void setPartDescription6(String partDescription6) {
		this.partDescription6 = partDescription6;
	}

	@XmlElement(name="PART_DESCRIPTION7")
	public String getPartDescription7() {
		return partDescription7;
	}

	public void setPartDescription7(String partDescription7) {
		this.partDescription7 = partDescription7;
	}

	@XmlElement(name="PART_DESCRIPTION8")
	public String getPartDescription8() {
		return partDescription8;
	}

	public void setPartDescription8(String partDescription8) {
		this.partDescription8 = partDescription8;
	}

	@XmlElement(name="PART_DESCRIPTION9")
	public String getPartDescription9() {
		return partDescription9;
	}

	public void setPartDescription9(String partDescription9) {
		this.partDescription9 = partDescription9;
	}

	@XmlElement(name="PART_DESCRIPTION10")
	public String getPartDescription10() {
		return partDescription10;
	}

	public void setPartDescription10(String partDescription10) {
		this.partDescription10 = partDescription10;
	}
}