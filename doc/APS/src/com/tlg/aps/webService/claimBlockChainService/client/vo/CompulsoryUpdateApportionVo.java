package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumLossCity;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumRecoveryItem;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumResponsibilityType;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumVehiclePayloadCapacityUnit;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumVehicleType;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 * 賠付公司資料
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryUpdateApportionVo {

	/**
	 * 是否為健保攤賠(預設false)
	 * 
	 * @return healthInsuranceApportion
	 **/
	@JsonProperty("health_insurance_apportion")
	private Boolean healthInsuranceApportion;

	/**
	 * 被保險人身份代號
	 */
	@JsonProperty("insured_role")
	private String insuredRole;
	
	/**
	 * 肇事責任類型
	 * @see EnumResponsibilityType
	 */
	@JsonProperty("responsibility_type")
	private String responsibilityType;

	/**
	 * 出險原因
	 */
	@JsonProperty("loss_reason")
	private String lossReason;

	/**
	 * 出險城市
	 * @see EnumLossCity
	 */
	@JsonProperty("loss_city")
	private String lossCity;

	/**
	 * 賠付金額
	 */
	@JsonProperty("amount")
	private BigDecimal amount;

	/**
	 * 給付醫療種類與金額 可接受輸入空陣列，會刪除原本的state_prices
	 */
	@JsonProperty("state_prices")
	private List<CompulsoryUpdateStatePriceVo> statePriceVo;

	/**
	 * 車輛乘載限制(單位名稱)
	 * @see EnumVehiclePayloadCapacityUnit
	 */
	@JsonProperty("vehicle_payload_capacity_unit")
	private String vehiclePayloadCapacityUnit;

	/**
	 * 車輛乘載限制
	 */
	@JsonProperty("vehicle_payload_capacity")
	private BigDecimal vehiclePayloadCapacity;

	/**
	 * 駕駛車輛類型
	 * @see EnumVehicleType
	 */
	@JsonProperty("vehicle_type")
	private String vehicleType;

	/**
	 * 追償事項
	 * @see EnumRecoveryItem
	 */
	@JsonProperty("recovery_item")
	private String recoveryItem;

	/**
	 * 保險證號
	 */
	@JsonProperty("insurance_number")
	private String insuranceNumber;

	/**
	 * 牌照號碼/保險車號
	 */
	@JsonProperty("insurance_car_number")
	private String insuranceCarNumber;

	/**
	 * 駕駛員名字
	 */
	@JsonProperty("driver_name")
	private String driverName;

	/**
	 * 是否健保就醫
	 */
	@JsonProperty("health_insurance")
	private Boolean healthInsurance;

	/**
	 * [v1.0.13] 其他肇事責任百分比(行人/腳踏車/乘客)
	 */
	@JsonProperty("other_responsibility_rate")
	private BigDecimal otherResponsibilityRate;

	/**
	 * 肇事責任百分比(受害人)
	 */
	@JsonProperty("applicant_responsibility_rate")
	private BigDecimal applicantResponsibilityRate;

	/**
	 * 肇事責任百分比
	 */
	@JsonProperty("responsibility_rate")
	private BigDecimal responsibilityRate; 
	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CompulsoryUpdateApportionVo compulsoryUpdateApportionVo = (CompulsoryUpdateApportionVo) o;
		return Objects.equals(this.healthInsuranceApportion,
				compulsoryUpdateApportionVo.healthInsuranceApportion)
				&& Objects.equals(this.responsibilityType,	compulsoryUpdateApportionVo.responsibilityType)
				&& Objects.equals(this.lossReason, compulsoryUpdateApportionVo.lossReason)
				&& Objects.equals(this.lossCity, compulsoryUpdateApportionVo.lossCity)
				&& Objects.equals(this.amount, compulsoryUpdateApportionVo.amount)
				&& Objects.equals(this.statePriceVo, compulsoryUpdateApportionVo.statePriceVo)
				&& Objects.equals(this.vehiclePayloadCapacityUnit, compulsoryUpdateApportionVo.vehiclePayloadCapacityUnit)
				&& Objects.equals(this.vehiclePayloadCapacity, compulsoryUpdateApportionVo.vehiclePayloadCapacity)
				&& Objects.equals(this.vehicleType, compulsoryUpdateApportionVo.vehicleType)
				&& Objects.equals(this.recoveryItem, compulsoryUpdateApportionVo.recoveryItem)
				&& Objects.equals(this.driverName, compulsoryUpdateApportionVo.driverName)
				&& Objects.equals(this.healthInsurance, compulsoryUpdateApportionVo.healthInsurance)
				&& Objects.equals(this.otherResponsibilityRate, compulsoryUpdateApportionVo.otherResponsibilityRate)
				&& Objects.equals(this.applicantResponsibilityRate, compulsoryUpdateApportionVo.applicantResponsibilityRate)
				&& Objects.equals(this.responsibilityRate, compulsoryUpdateApportionVo.responsibilityRate)
				&& Objects.equals(this.insuranceNumber, compulsoryUpdateApportionVo.insuranceNumber)
				&& Objects.equals(this.insuranceCarNumber, compulsoryUpdateApportionVo.insuranceCarNumber);
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(healthInsuranceApportion, responsibilityType,
				lossReason, lossCity, amount, statePriceVo,
				vehiclePayloadCapacityUnit, vehiclePayloadCapacity,
				vehicleType, recoveryItem, driverName, healthInsurance,
				otherResponsibilityRate, applicantResponsibilityRate,
				responsibilityRate, insuranceNumber, insuranceCarNumber);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CompulsoryUpdateApportionVo {\n");
		sb.append("    healthInsuranceApportion: ").append(toIndentedString(healthInsuranceApportion)).append("\n");
		sb.append("    responsibilityType: ").append(toIndentedString(responsibilityType)).append("\n");
		sb.append("    lossReason: ").append(toIndentedString(lossReason)).append("\n");
		sb.append("    lossCity: ").append(toIndentedString(lossCity)).append("\n");
		sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
		sb.append("    statePrices: ").append(toIndentedString(statePriceVo)).append("\n");
		sb.append("    vehiclePayloadCapacityUnit: ").append(toIndentedString(vehiclePayloadCapacityUnit)).append("\n");
		sb.append("    vehiclePayloadCapacity: ").append(toIndentedString(vehiclePayloadCapacity)).append("\n");
		sb.append("    vehicleType: ").append(toIndentedString(vehicleType)).append("\n");
		sb.append("    recoveryItem: ").append(toIndentedString(recoveryItem)).append("\n");
		sb.append("    driverName: ").append(toIndentedString(driverName)).append("\n");
		sb.append("    healthInsurance: ").append(toIndentedString(healthInsurance)).append("\n");
		sb.append("    otherResponsibilityRate: ").append(toIndentedString(otherResponsibilityRate)).append("\n");
		sb.append("    applicantResponsibilityRate: ").append(toIndentedString(applicantResponsibilityRate)).append("\n");
		sb.append("    responsibilityRate: ").append(toIndentedString(responsibilityRate)).append("\n");
		sb.append("    insuranceNumber: ").append(toIndentedString(insuranceNumber)).append("\n");
		sb.append("    insuranceCarNumber: ").append(toIndentedString(insuranceCarNumber)).append("\n");
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

	public Boolean getHealthInsuranceApportion() {
		return healthInsuranceApportion;
	}

	public void setHealthInsuranceApportion(Boolean healthInsuranceApportion) {
		this.healthInsuranceApportion = healthInsuranceApportion;
	}

	public String getResponsibilityType() {
		return responsibilityType;
	}

	public void setResponsibilityType(String responsibilityType) {
		this.responsibilityType = responsibilityType;
	}

	public String getLossReason() {
		return lossReason;
	}

	public void setLossReason(String lossReason) {
		this.lossReason = lossReason;
	}

	public String getLossCity() {
		return lossCity;
	}

	public void setLossCity(String lossCity) {
		this.lossCity = lossCity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getVehiclePayloadCapacityUnit() {
		return vehiclePayloadCapacityUnit;
	}

	public void setVehiclePayloadCapacityUnit(String vehiclePayloadCapacityUnit) {
		this.vehiclePayloadCapacityUnit = vehiclePayloadCapacityUnit;
	}

	public BigDecimal getVehiclePayloadCapacity() {
		return vehiclePayloadCapacity;
	}

	public void setVehiclePayloadCapacity(BigDecimal vehiclePayloadCapacity) {
		this.vehiclePayloadCapacity = vehiclePayloadCapacity;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getRecoveryItem() {
		return recoveryItem;
	}

	public void setRecoveryItem(String recoveryItem) {
		this.recoveryItem = recoveryItem;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Boolean getHealthInsurance() {
		return healthInsurance;
	}

	public void setHealthInsurance(Boolean healthInsurance) {
		this.healthInsurance = healthInsurance;
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

	public void setApplicantResponsibilityRate(BigDecimal applicantResponsibilityRate) {
		this.applicantResponsibilityRate = applicantResponsibilityRate;
	}

	public BigDecimal getResponsibilityRate() {
		return responsibilityRate;
	}

	public void setResponsibilityRate(BigDecimal responsibilityRate) {
		this.responsibilityRate = responsibilityRate;
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

	public List<CompulsoryUpdateStatePriceVo> getStatePriceVo() {
		return statePriceVo;
	}

	public void setStatePriceVo(List<CompulsoryUpdateStatePriceVo> statePriceVo) {
		this.statePriceVo = statePriceVo;
	}

	public String getInsuredRole() {
		return insuredRole;
	}

	public void setInsuredRole(String insuredRole) {
		this.insuredRole = insuredRole;
	}
}
