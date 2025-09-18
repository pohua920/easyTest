package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumAmountType;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryUpdateStatePriceVo {
	
	/**
	 * @see EnumAmountType
	 */
	@JsonProperty("type")
	private String type;
	/**
	 * 金額
	 */
	@JsonProperty("amount")
	private BigDecimal amount;
	/**
	 * 代號
	 */
	@JsonProperty("code")
	private String code;


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
