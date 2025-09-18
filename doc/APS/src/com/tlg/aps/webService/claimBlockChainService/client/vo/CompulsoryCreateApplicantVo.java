package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumIdNumberType;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * 受害人個人資訊
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryCreateApplicantVo {

	/**
	 * @see EnumIdNumberType
	 */
	@JsonProperty("applicant_id_number_type")
	private String applicantIdNumberType;

	@JsonProperty("applicant_id_number")
	private String applicantIdNumber;

	@JsonProperty("applicant_birthday")
	private String applicantBirthday;

	@JsonProperty("applicant_name")
	private String applicantName;

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CompulsoryCreateApplicantVo compulsoryCreateApplicantVo = (CompulsoryCreateApplicantVo) o;
		return Objects.equals(this.applicantIdNumberType, compulsoryCreateApplicantVo.applicantIdNumberType)
				&& Objects.equals(this.applicantIdNumber, compulsoryCreateApplicantVo.applicantIdNumber)
				&& Objects.equals(this.applicantBirthday, compulsoryCreateApplicantVo.applicantBirthday)
				&& Objects.equals(this.applicantName, compulsoryCreateApplicantVo.applicantName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicantIdNumberType, applicantIdNumber,
				applicantBirthday, applicantName);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CompulsoryApplicantVo {\n");
		sb.append("    applicantIdNumberType: ").append(toIndentedString(applicantIdNumberType)).append("\n");
		sb.append("    applicantIdNumber: ").append(toIndentedString(applicantIdNumber)).append("\n");
		sb.append("    applicantBirthday: ").append(toIndentedString(applicantBirthday)).append("\n");
		sb.append("    applicantName: ").append(toIndentedString(applicantName)).append("\n");
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

	public String getApplicantIdNumberType() {
		return applicantIdNumberType;
	}

	public void setApplicantIdNumberType(String applicantIdNumberType) {
		this.applicantIdNumberType = applicantIdNumberType;
	}

	public String getApplicantIdNumber() {
		return applicantIdNumber;
	}

	public void setApplicantIdNumber(String applicantIdNumber) {
		this.applicantIdNumber = applicantIdNumber;
	}

	public String getApplicantBirthday() {
		return applicantBirthday;
	}

	public void setApplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

}
