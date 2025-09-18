package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tlg.aps.webService.claimBlockChainService.client.enums.EnumAmountType;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryUpdateResultStatePriceVo {
	
	@JsonProperty("apportion")
	private Object apportion;
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
	
	/**
	 * 
	 */
	@JsonProperty("id")
	private String id;
	
	/**
	 * 建立時間
	 */
	@JsonProperty("created_at")
	private String createdAt;
	
	/**
	 * 更新時間
	 */
	@JsonProperty("updated_at")
	private String updatedAt;
	
	/**
	 * 刪除時間
	 */
	@JsonProperty("deleted_at")
	private String deletedAt;

	/**
	 *
	 */
	@JsonProperty("apportionId")
	private String apportionId;
	
	public Object getApportion() {
		return apportion;
	}

	public void setApportion(Object apportion) {
		this.apportion = apportion;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getApportionId() {
		return apportionId;
	}

	public void setApportionId(String apportionId) {
		this.apportionId = apportionId;
	}

}
