package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * CompulsoryChargesVo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryCreateChargesVo {

	/**
	 * 公司代號
	 */
	@JsonProperty("msp_id")
	private String mspId;

	/**
	 * 牌照號碼/保險車號
	 */
	@JsonProperty("insurance_car_number")
	private String insuranceCarNumber;

	/**
	 * 保險證號
	 */
	@JsonProperty("insurance_number")
	private String insuranceNumber;

	/**
	 * 攤出金額
	 */
	@JsonProperty("amount")
	private BigDecimal amount;

	/**
	 * 肇事責任比例（攤出方）
	 */
	@JsonProperty("responsibility_rate")
	private BigDecimal responsibilityRate;

	/**
	 * 駕駛名稱
	 */
	@JsonProperty("driver_name")
	private String driverName;

	/**
	 * 駕駛車輛類型
	 */
	@JsonProperty("vehicle_type")
	private String vehicleType;

	/**
	 * 乘載限制
	 */
	@JsonProperty("vehicle_payload_capacity")
	private BigDecimal vehiclePayloadCapacity;

	/**
	 * 乘載限制單位
	 */
	@JsonProperty("vehicle_payload_capacity_unit")
	private String vehiclePayloadCapacityUnit;

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CompulsoryCreateChargesVo compulsoryCreateChargesVo = (CompulsoryCreateChargesVo) o;
		return Objects.equals(this.mspId, compulsoryCreateChargesVo.mspId)
				&& Objects.equals(this.insuranceCarNumber, compulsoryCreateChargesVo.insuranceCarNumber)
				&& Objects.equals(this.insuranceNumber, compulsoryCreateChargesVo.insuranceNumber)
				&& Objects.equals(this.amount, compulsoryCreateChargesVo.amount)
				&& Objects.equals(this.responsibilityRate, compulsoryCreateChargesVo.responsibilityRate)
				&& Objects.equals(this.driverName, compulsoryCreateChargesVo.driverName)
				&& Objects.equals(this.vehicleType, compulsoryCreateChargesVo.vehicleType)
				&& Objects.equals(this.vehiclePayloadCapacity, compulsoryCreateChargesVo.vehiclePayloadCapacity)
				&& Objects.equals(this.vehiclePayloadCapacityUnit, compulsoryCreateChargesVo.vehiclePayloadCapacityUnit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mspId, insuranceCarNumber, insuranceNumber, amount,
				responsibilityRate, driverName, vehicleType,
				vehiclePayloadCapacity, vehiclePayloadCapacityUnit);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CompulsoryCreateChargesVo {\n");
		sb.append("    mspId: ").append(toIndentedString(mspId)).append("\n");
		sb.append("    insuranceCarNumber: ").append(toIndentedString(insuranceCarNumber)).append("\n");
		sb.append("    insuranceNumber: ").append(toIndentedString(insuranceNumber)).append("\n");
		sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
		sb.append("    responsibilityRate: ").append(toIndentedString(responsibilityRate)).append("\n");
		sb.append("    driverName: ").append(toIndentedString(driverName)).append("\n");
		sb.append("    vehicleType: ").append(toIndentedString(vehicleType)).append("\n");
		sb.append("    vehiclePayloadCapacity: ").append(toIndentedString(vehiclePayloadCapacity)).append("\n");
		sb.append("    vehiclePayloadCapacityUnit: ").append(toIndentedString(vehiclePayloadCapacityUnit)).append("\n");
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

	public String getMspId() {
		return mspId;
	}

	public void setMspId(String mspId) {
		this.mspId = mspId;
	}

	public String getInsuranceCarNumber() {
		return insuranceCarNumber;
	}

	public void setInsuranceCarNumber(String insuranceCarNumber) {
		this.insuranceCarNumber = insuranceCarNumber;
	}

	public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getResponsibilityRate() {
		return responsibilityRate;
	}

	public void setResponsibilityRate(BigDecimal responsibilityRate) {
		this.responsibilityRate = responsibilityRate;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public BigDecimal getVehiclePayloadCapacity() {
		return vehiclePayloadCapacity;
	}

	public void setVehiclePayloadCapacity(BigDecimal vehiclePayloadCapacity) {
		this.vehiclePayloadCapacity = vehiclePayloadCapacity;
	}

	public String getVehiclePayloadCapacityUnit() {
		return vehiclePayloadCapacityUnit;
	}

	public void setVehiclePayloadCapacityUnit(String vehiclePayloadCapacityUnit) {
		this.vehiclePayloadCapacityUnit = vehiclePayloadCapacityUnit;
	}
}