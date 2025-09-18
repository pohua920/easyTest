package com.tlg.aps.webService.claimBlockChainService.client.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumMspId;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumVehiclePayloadCapacityUnit;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumVehicleType;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * 攤出方資料
 */
public class CompulsoryUpdateResultCharges {
	
	/**
	 * 
	 */
	@JsonProperty("apportions")
	private Object apportions;

	/**
	 * 對車乘載限制單位(可能為null)
	 * @see EnumVehiclePayloadCapacityUnit
	 */
	@JsonProperty("vehicle_payload_capacity_unit")
	private String vehiclePayloadCapacityUnit;

	/**
	 * 對車乘載限制(可能為null)
	 */
	@JsonProperty("vehicle_payload_capacity")
	private Double vehiclePayloadCapacity;

	/**
	 * 對車駕駛車輛類型(可能為null)
	 * @see EnumVehicleType
	 */
	@JsonProperty("vehicle_type")
	private String vehicleType;

	/**
	 * 攤出比數順序
	 */
	@JsonProperty("charge_index")
	private Double chargeIndex;

	/**
	 * 攤出駕駛姓名
	 */
	@JsonProperty("driver_name")
	private String driverName;

	/**
	 * 攤出駕駛姓名
	 */
	@JsonProperty("responsibility_rate")
	private Double responsibilityRate;

	/**
	 * 攤出金額
	 */
	@JsonProperty("amount")
	private Double amount;

	/**
	 * 保險證號
	 */
	@JsonProperty("insurance_number")
	private String insuranceNumber;

	/**
	 * 攤出車號
	 */
	@JsonProperty("insurance_car_number")
	private String insuranceCarNumber;
	
	/**
	 * 公司代號
	 * @see EnumMspId
	 */
	@JsonProperty("msp_id")
	private String mspId;

	/**
	 * 攤出流水號
	 */
	@JsonProperty("id")
	private Double id;

	/**
	 * 攤回建立時間
	 */
	@JsonProperty("created_at")
	private String createdAt;
	
	/**
	 * 攤回更新時間
	 */
	@JsonProperty("updated_at")
	private String updatedAt;
	
	/**
	 * 攤回刪除時間, 如未刪除則回傳 null
	 */
	@JsonProperty("deleted_at")
	private String deletedAt;
	
	/**
	 * 攤回 id
	 */
	@JsonProperty("apportionsId")
	private String apportionsId;
	
	/**
	 * 
	 */
	@JsonProperty("status")
	private String status;

	/**
	 * 
	 */
	@JsonProperty("approved_at")
	private String approvedAt;
	
	/**
	 * 
	 */
	@JsonProperty("rejected_at")
	private String rejectedAt;
	
	
	
	public Object getApportions() {
		return apportions;
	}

	public void setApportions(Object apportions) {
		this.apportions = apportions;
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

	public Double getChargeIndex() {
		return chargeIndex;
	}

	public void setChargeIndex(Double chargeIndex) {
		this.chargeIndex = chargeIndex;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Double getResponsibilityRate() {
		return responsibilityRate;
	}

	public void setResponsibilityRate(Double responsibilityRate) {
		this.responsibilityRate = responsibilityRate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public String getInsuranceCarNumber() {
		return insuranceCarNumber;
	}

	public void setInsuranceCarNumber(String insuranceCarNumber) {
		this.insuranceCarNumber = insuranceCarNumber;
	}

	public String getMspId() {
		return mspId;
	}

	public void setMspId(String mspId) {
		this.mspId = mspId;
	}

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getApportionsId() {
		return apportionsId;
	}

	public void setApportionsId(String apportionsId) {
		this.apportionsId = apportionsId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(String approvedAt) {
		this.approvedAt = approvedAt;
	}

	public String getRejectedAt() {
		return rejectedAt;
	}

	public void setRejectedAt(String rejectedAt) {
		this.rejectedAt = rejectedAt;
	}
}
