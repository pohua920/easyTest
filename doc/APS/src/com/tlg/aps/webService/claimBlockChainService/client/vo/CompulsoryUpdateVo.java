package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * CompulsoryUpdateVo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryUpdateVo {
	
	@JsonIgnore
	private String caseId;

	/**
	 * 案件資料
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("case")
	private CompulsoryUpdateCaseVo updateCase;
	
	/**
	 * 受害人資料
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("applicant")
	private CompulsoryUpdateApplicantVo updateApplicant;

	/**
	 * 賠付公司資料
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("apportion")
	private CompulsoryUpdateApportionVo updateApportion;
	
	/**
	 * [v1.0.13] 攤賠次號 變為必填
	 */
	@JsonProperty("compulsory_apportion_id")
	private BigDecimal compulsoryApportionId;

	/**
	 * 攤賠公司資料
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("compulsory_charges")
	private List<CompulsoryUpdateChargesVo> updateCharges;
	
	/**
	 * 檔案內容 可接受輸入空陣列，會刪除原本的state_prices
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("compulsory_files")
	private List<CompulsoryUpdateFilesVo> updateFiles;
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CompulsoryUpdateVo compulsoryUpdateVo = (CompulsoryUpdateVo) o;
		return Objects.equals(this.caseId, compulsoryUpdateVo.caseId)
				&& Objects.equals(this.updateCase, compulsoryUpdateVo.updateCase)
				&& Objects.equals(this.updateApplicant, compulsoryUpdateVo.updateApplicant)
				&& Objects.equals(this.updateApportion, compulsoryUpdateVo.updateApportion)
				&& Objects.equals(this.updateCharges, compulsoryUpdateVo.updateCharges)
				&& Objects.equals(this.compulsoryApportionId, compulsoryUpdateVo.compulsoryApportionId)
				&& Objects.equals(this.updateFiles, compulsoryUpdateVo.updateFiles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(caseId, updateCase, updateApplicant, updateApportion, updateCharges, compulsoryApportionId, updateFiles);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CompulsoryUpdateVo {\n");
		sb.append("    caseId: ").append(toIndentedString(caseId)).append("\n");
		sb.append("    updateCase: ").append(toIndentedString(updateCase)).append("\n");
		sb.append("    updateApplicant: ").append(toIndentedString(updateApplicant)).append("\n");
		sb.append("    updateApportion: ").append(toIndentedString(updateApportion)).append("\n");
		sb.append("    compulsoryApportionId: ").append(toIndentedString(compulsoryApportionId)).append("\n");
		sb.append("    updateFiles: ").append(toIndentedString(updateFiles)).append("\n");
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

	public CompulsoryUpdateCaseVo getUpdateCase() {
		return updateCase;
	}

	public void setUpdateCase(CompulsoryUpdateCaseVo updateCase) {
		this.updateCase = updateCase;
	}

	public CompulsoryUpdateApplicantVo getUpdateApplicant() {
		return updateApplicant;
	}

	public void setUpdateApplicant(CompulsoryUpdateApplicantVo updateApplicant) {
		this.updateApplicant = updateApplicant;
	}

	public CompulsoryUpdateApportionVo getUpdateApportion() {
		return updateApportion;
	}

	public void setUpdateApportion(CompulsoryUpdateApportionVo updateApportion) {
		this.updateApportion = updateApportion;
	}

	public BigDecimal getCompulsoryApportionId() {
		return compulsoryApportionId;
	}

	public void setCompulsoryApportionId(BigDecimal compulsoryApportionId) {
		this.compulsoryApportionId = compulsoryApportionId;
	}

	public List<CompulsoryUpdateChargesVo> getUpdateCharges() {
		return updateCharges;
	}

	public void setUpdateCharges(List<CompulsoryUpdateChargesVo> updateCharges) {
		this.updateCharges = updateCharges;
	}

	public List<CompulsoryUpdateFilesVo> getUpdateFiles() {
		return updateFiles;
	}

	public void setUpdateFiles(List<CompulsoryUpdateFilesVo> updateFiles) {
		this.updateFiles = updateFiles;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
}
