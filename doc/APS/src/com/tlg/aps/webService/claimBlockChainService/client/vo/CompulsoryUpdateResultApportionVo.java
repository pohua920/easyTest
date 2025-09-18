package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumApportionActions;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumApportionTableType;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumLossCity;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumLossReason;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumRecoveryItem;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumVehiclePayloadCapacityUnit;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumVehicleType;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * 攤回內容
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryUpdateResultApportionVo {

	
	/**
	 * 攤回內容
	 */
	@JsonProperty("state_prices")
	private ArrayList<CompulsoryUpdateResultStatePriceVo> statePrices;
	/**
	 * 
	 */
	@JsonProperty("files")
	private List<CompulsoryUpdateResultFiles> files;
	/**
	 * 攤出方資料
	 */
	@JsonProperty("charges")
	private List<CompulsoryUpdateResultCharges> charges;
	/**
	 * [v1.0.13] 其他肇事責任百分比(行人/腳踏車/乘客)
	 */
	@JsonProperty("other_responsibility_rate")
	private BigDecimal otherResponsibilityRate;
	/**
	 * 受保人肇責比例
	 */
	@JsonProperty("applicant_responsibility_rate")
	private BigDecimal applicantResponsibilityRate;
	/**
	 * 肇責百分比
	 */
	@JsonProperty("responsibility_rate")
	private BigDecimal responsibilityRate;
	/**
	 * 是否有接續攤賠
	 * @see EnumApportionActions
	 */
	@JsonProperty("apportion_actions")
	private List<String> apportionActions;
	
	/**
	 * 是否為健保攤賠(預設false)
	 */
	@JsonProperty("health_insurance_apportion")
	private Boolean healthInsuranceApportion;
	
	/**
	 * 分攤表類型
	 * @see EnumApportionTableType
	 */
	@JsonProperty("apportion_type")
	private String apportionType;
	
	/**
	 * 保險車號
	 */
	@JsonProperty("insurance_car_number")
	private String insuranceCarNumber;
	
	/**
	 * 是否健保就醫
	 */
	@JsonProperty("health_insurance")
	private Boolean healthInsurance;

	/**
	 * 追償事項
	 * @see EnumRecoveryItem
	 */
	@JsonProperty("recovery_item")
	private String recoveryItem;
	/**
	 * 肇事縣市
	 * @see EnumLossCity
	 */
	@JsonProperty("loss_city")
	private String lossCity;

	/**
	 * 出險原因
	 * @see EnumLossReason
	 */
	@JsonProperty("loss_reason")
	private String lossReason;

	/**
	 * 乘載限制單位
	 * @see EnumVehiclePayloadCapacityUnit
	 */
	@JsonProperty("vehicle_payload_capacity_unit")
	private String vehiclePayloadCapacityUnit;

	/**
	 * 車輛乘載限制
	 */
	@JsonProperty("vehicle_payload_capacity")
	private Double vehiclePayloadCapacity;

	/**
	 * 車輛類型
	 * @see EnumVehicleType
	 */
	@JsonProperty("vehicle_type")
	private String vehicleType;

	/**
	 * 駕駛姓名
	 */
	@JsonProperty("driver_name")
	private String driverName;

	/**
	 * 強制險案件
	 */
	@JsonProperty("compulsory_case")
	private Object compulsoryCase;

	/**
	 * 保險案證號
	 */
	@JsonProperty("insurance_number")
	private String insuranceNumber;

	/**
	 * 肇責類型類型
	 */
	@JsonProperty("responsibility_type")
	private String responsibilityType;

	/**
	 * 第幾次攤賠
	 */
	@JsonProperty("apportion_index")
	private Double apportionIndex;

	/**
	 * 攤回唯一鍵
	 */
	@JsonProperty("apportion_key")
	private String apportionKey;

	/**
	 * 攤回拒絕時間 如未拒絕則回傳 null
	 */
	@JsonProperty("rejected_at")
	private String rejectedAt;
	
	/**
	 * 攤回同意時間 如未同意則回傳 null
	 */
	@JsonProperty("approved_at")
	private String approvedAt;

	/**
	 * 攤回完成時間 如未完成則回傳 null
	 */
	@JsonProperty("completed_at")
	private String completedAt;

	/**
	 * 攤回通知時間 如未通知則回傳 null
	 */
	@JsonProperty("noticed_at")
	private String noticedAt;
	
	/**
	 * 攤回金額
	 */
	@JsonProperty("amount")
	private BigDecimal amount;
	
	/**
	 * 攤回狀態
	 */
	@JsonProperty("status")
	private String status;

	/**
	 * 攤回刪除時間, 如未刪除則回傳 null
	 */
	@JsonProperty("deleted_at")
	private String deletedAt;

	/**
	 * 攤回更新時間
	 */
	@JsonProperty("updated_at")
	private String updatedAt;

	/**
	 * 攤回建立時間
	 */
	@JsonProperty("created_at")
	private String createdAt;

	/**
	 * 攤回流水號
	 */
	@JsonProperty("id")
	private Double id;
	
	/**
	 * 案件id
	 */
	@JsonProperty("compulsoryCaseId")
	private String compulsoryCaseId;
	
	/**
	 * 被保險人身份代號
	 */
	@JsonProperty("insured_role")
	private String insuredRole;

	public ArrayList<CompulsoryUpdateResultStatePriceVo> getStatePrices() {
		return statePrices;
	}

	public void setStatePrices(
			ArrayList<CompulsoryUpdateResultStatePriceVo> statePrices) {
		this.statePrices = statePrices;
	}

	public List<CompulsoryUpdateResultFiles> getFiles() {
		return files;
	}

	public void setFiles(List<CompulsoryUpdateResultFiles> files) {
		this.files = files;
	}

	public List<CompulsoryUpdateResultCharges> getCharges() {
		return charges;
	}

	public void setCharges(List<CompulsoryUpdateResultCharges> charges) {
		this.charges = charges;
	}

	public BigDecimal getOtherResponsibilityRate() {
		return otherResponsibilityRate;
	}

	public void setOtherResponsibilityRate(BigDecimal otherResponsibilityRate) {
		this.otherResponsibilityRate = otherResponsibilityRate;
	}

	public BigDecimal getApplicantResponsibilityRate() {
		return applicantResponsibilityRate;
	}

	public void setApplicantResponsibilityRate(
			BigDecimal applicantResponsibilityRate) {
		this.applicantResponsibilityRate = applicantResponsibilityRate;
	}

	public BigDecimal getResponsibilityRate() {
		return responsibilityRate;
	}

	public void setResponsibilityRate(BigDecimal responsibilityRate) {
		this.responsibilityRate = responsibilityRate;
	}

	public List<String> getApportionActions() {
		return apportionActions;
	}

	public void setApportionActions(List<String> apportionActions) {
		this.apportionActions = apportionActions;
	}

	public Boolean getHealthInsuranceApportion() {
		return healthInsuranceApportion;
	}

	public void setHealthInsuranceApportion(Boolean healthInsuranceApportion) {
		this.healthInsuranceApportion = healthInsuranceApportion;
	}

	public String getApportionType() {
		return apportionType;
	}

	public void setApportionType(String apportionType) {
		this.apportionType = apportionType;
	}

	public String getInsuranceCarNumber() {
		return insuranceCarNumber;
	}

	public void setInsuranceCarNumber(String insuranceCarNumber) {
		this.insuranceCarNumber = insuranceCarNumber;
	}

	public Boolean getHealthInsurance() {
		return healthInsurance;
	}

	public void setHealthInsurance(Boolean healthInsurance) {
		this.healthInsurance = healthInsurance;
	}

	public String getRecoveryItem() {
		return recoveryItem;
	}

	public void setRecoveryItem(String recoveryItem) {
		this.recoveryItem = recoveryItem;
	}

	public String getLossCity() {
		return lossCity;
	}

	public void setLossCity(String lossCity) {
		this.lossCity = lossCity;
	}

	public String getLossReason() {
		return lossReason;
	}

	public void setLossReason(String lossReason) {
		this.lossReason = lossReason;
	}

	public String getVehiclePayloadCapacityUnit() {
		return vehiclePayloadCapacityUnit;
	}

	public void setVehiclePayloadCapacityUnit(String vehiclePayloadCapacityUnit) {
		this.vehiclePayloadCapacityUnit = vehiclePayloadCapacityUnit;
	}

	public Double getVehiclePayloadCapacity() {
		return vehiclePayloadCapacity;
	}

	public void setVehiclePayloadCapacity(Double vehiclePayloadCapacity) {
		this.vehiclePayloadCapacity = vehiclePayloadCapacity;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Object getCompulsoryCase() {
		return compulsoryCase;
	}

	public void setCompulsoryCase(Object compulsoryCase) {
		this.compulsoryCase = compulsoryCase;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public String getResponsibilityType() {
		return responsibilityType;
	}

	public void setResponsibilityType(String responsibilityType) {
		this.responsibilityType = responsibilityType;
	}

	public Double getApportionIndex() {
		return apportionIndex;
	}

	public void setApportionIndex(Double apportionIndex) {
		this.apportionIndex = apportionIndex;
	}

	public String getApportionKey() {
		return apportionKey;
	}

	public void setApportionKey(String apportionKey) {
		this.apportionKey = apportionKey;
	}

	public String getRejectedAt() {
		return rejectedAt;
	}

	public void setRejectedAt(String rejectedAt) {
		this.rejectedAt = rejectedAt;
	}

	public String getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(String approvedAt) {
		this.approvedAt = approvedAt;
	}

	public String getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}

	public String getNoticedAt() {
		return noticedAt;
	}

	public void setNoticedAt(String noticedAt) {
		this.noticedAt = noticedAt;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public String getCompulsoryCaseId() {
		return compulsoryCaseId;
	}

	public void setCompulsoryCaseId(String compulsoryCaseId) {
		this.compulsoryCaseId = compulsoryCaseId;
	}

	public String getInsuredRole() {
		return insuredRole;
	}

	public void setInsuredRole(String insuredRole) {
		this.insuredRole = insuredRole;
	}
}
