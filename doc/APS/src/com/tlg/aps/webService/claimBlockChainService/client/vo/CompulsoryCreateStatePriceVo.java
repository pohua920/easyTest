package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumAmountType;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumStateCode;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
public class CompulsoryCreateStatePriceVo {
	/**
	 * @see EnumAmountType
	 */
	@JsonProperty("type")
	private String type = null;

	@JsonProperty("amount")
	private BigDecimal amount = null;
	
	/**
	 * @see EnumStateCode
	 */
	@JsonProperty("code")
	private String code = null;

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CompulsoryCreateStatePriceVo statePriceDto = (CompulsoryCreateStatePriceVo) o;
		return Objects.equals(this.type, statePriceDto.type)
				&& Objects.equals(this.amount, statePriceDto.amount)
				&& Objects.equals(this.code, statePriceDto.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, amount, code);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class StatePriceVo {\n");

		sb.append("    type: ").append(toIndentedString(type)).append("\n");
		sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
		sb.append("    code: ").append(toIndentedString(code)).append("\n");
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
