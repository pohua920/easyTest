package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumApplicantRole;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumApplicantsTypes;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * 理賠相關資料
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryCreateCaseVo {

	/**
	 * 受害者身份
	 * @see EnumApplicantRole
	 */
	@JsonProperty("applicant_role")
	private String applicantRole;

	/**
	 * 憲警名稱
	 */
	@JsonProperty("police_name")
	private String policeName;

	/**
	 * 憲警單位
	 */
	@JsonProperty("police_unit")
	private String policeUnit;

	/*
	 *事故路段 
	 */
	@JsonProperty("hit_road")
	private String hitRoad;

	/**
	 * 乘坐車號
	 */
	@JsonProperty("in_car_number")
	private String inCarNumber;

	/**
	 * 事故時間
	 */
	@JsonProperty("hit_time")
	private String hitTime;

	/**
	 * @see EnumApplicantsTypes
	 */
	@JsonProperty("applicant_type")
	private String applicantType;
	
	/**
	 * 案號
	 */
	@JsonProperty("case_number")
	private String caseNumber;

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CompulsoryCreateCaseVo compulsoryCreateCaseVo = (CompulsoryCreateCaseVo) o;
		return Objects.equals(this.applicantRole, compulsoryCreateCaseVo.applicantRole)
				&& Objects.equals(this.policeName, compulsoryCreateCaseVo.policeName)
				&& Objects.equals(this.policeUnit, compulsoryCreateCaseVo.policeUnit)
				&& Objects.equals(this.hitRoad, compulsoryCreateCaseVo.hitRoad)
				&& Objects.equals(this.inCarNumber, compulsoryCreateCaseVo.inCarNumber)
				&& Objects.equals(this.hitTime, compulsoryCreateCaseVo.hitTime)
				&& Objects.equals(this.applicantType, compulsoryCreateCaseVo.applicantType)
				&& Objects.equals(this.caseNumber, compulsoryCreateCaseVo.caseNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicantRole, policeName, policeUnit, hitRoad,
				inCarNumber, hitTime, applicantType, caseNumber);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CompulsoryCaseVo {\n");
		sb.append("    applicantRole: ").append(toIndentedString(applicantRole)).append("\n");
		sb.append("    policeName: ").append(toIndentedString(policeName)).append("\n");
		sb.append("    policeUnit: ").append(toIndentedString(policeUnit)).append("\n");
		sb.append("    hitRoad: ").append(toIndentedString(hitRoad)).append("\n");
		sb.append("    inCarNumber: ").append(toIndentedString(inCarNumber)).append("\n");
		sb.append("    hitTime: ").append(toIndentedString(hitTime)).append("\n");
		sb.append("    applicantType: ").append(toIndentedString(applicantType)).append("\n");
		sb.append("    caseNumber: ").append(toIndentedString(caseNumber)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

	public String getApplicantRole() {
		return applicantRole;
	}

	public void setApplicantRole(String applicantRole) {
		this.applicantRole = applicantRole;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getPoliceUnit() {
		return policeUnit;
	}

	public void setPoliceUnit(String policeUnit) {
		this.policeUnit = policeUnit;
	}

	public String getHitRoad() {
		return hitRoad;
	}

	public void setHitRoad(String hitRoad) {
		this.hitRoad = hitRoad;
	}

	public String getInCarNumber() {
		return inCarNumber;
	}

	public void setInCarNumber(String inCarNumber) {
		this.inCarNumber = inCarNumber;
	}

	public String getHitTime() {
		return hitTime;
	}

	public void setHitTime(String hitTime) {
		this.hitTime = hitTime;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
}
