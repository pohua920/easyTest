package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * CompulsoryCreateVo
 */
public class CompulsoryCreateVo {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("case")
	private CompulsoryCreateCaseVo createCase;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("applicant")
	private CompulsoryCreateApplicantVo createApplicant;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("apportion")
	private CompulsoryCreateApportionVo createApportion;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("compulsory_charges")
	private List<CompulsoryCreateChargesVo> createCharges;

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CompulsoryCreateVo compulsoryCreateVo = (CompulsoryCreateVo) o;
		return Objects.equals(this.createCase, compulsoryCreateVo.createCase)
				&& Objects.equals(this.createApplicant, compulsoryCreateVo.createApplicant)
				&& Objects.equals(this.createApportion, compulsoryCreateVo.createApportion)
				&& Objects.equals(this.createCharges, compulsoryCreateVo.createCharges);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createCase, createApplicant, createApportion, createCharges);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CompulsoryVo {\n");
		sb.append("    _case: ").append(toIndentedString(createCase)).append("\n");
		sb.append("    applicant: ").append(toIndentedString(createApplicant)).append("\n");
		sb.append("    apportion: ").append(toIndentedString(createApportion)).append("\n");
		sb.append("    compulsoryCharges: ").append(toIndentedString(createCharges)).append("\n");
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

	public CompulsoryCreateCaseVo getCreateCase() {
		return createCase;
	}

	public void setCreateCase(CompulsoryCreateCaseVo createCase) {
		this.createCase = createCase;
	}

	public CompulsoryCreateApplicantVo getCreateApplicant() {
		return createApplicant;
	}

	public void setCreateApplicant(CompulsoryCreateApplicantVo createApplicant) {
		this.createApplicant = createApplicant;
	}

	public CompulsoryCreateApportionVo getCreateApportion() {
		return createApportion;
	}

	public void setCreateApportion(CompulsoryCreateApportionVo createApportion) {
		this.createApportion = createApportion;
	}

	public List<CompulsoryCreateChargesVo> getCreateCharges() {
		return createCharges;
	}

	public void setCreateCharges(List<CompulsoryCreateChargesVo> createCharges) {
		this.createCharges = createCharges;
	}
}
