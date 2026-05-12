package com.sinosoft.claim.reins.vo;

import java.io.Serializable;

public class ReinsClaimDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class PayType {
		private String payTypeCode;

		private PayType(String payTypeCode) {
			this.payTypeCode = payTypeCode;
		}

		private String getPayTypeCode() {
			return this.payTypeCode;
		}

		/**
		 * 赔款
		 */
		public static PayType PAY = new PayType("1");
		/**
		 * 费用
		 */
		public static PayType CHARGE = new PayType("2");
	}

	private Integer dangerNo;
	private String itemName;

	private String kindCode;
	private String kindName;
	private PayType payType;
	private String currency;
	private Double exchang;
	private Double sumPaid;

	// SAP添加属性 start
	private String businessNature; // 业务渠道
	private String channelType; // 渠道类型
	private String cartypeCode;// 车型
	private double exchangeRate = 0;// 本位币和签单币别兑换率

	// SAP添加属性 start

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public Double getSumPaid() {
		return sumPaid;
	}

	public void setSumPaid(Double sumPaid) {
		this.sumPaid = sumPaid;
	}

	public String getPayTypeCode() {
		return this.getPayType().getPayTypeCode();
	}

	public Integer getDangerNo() {
		return dangerNo;
	}

	public void setDangerNo(Integer dangerNo) {
		this.dangerNo = dangerNo;
	}

	public Double getExchang() {
		return exchang;
	}

	public void setExchang(Double exchang) {
		this.exchang = exchang;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the businessNature
	 */
	public String getBusinessNature() {
		return businessNature;
	}

	/**
	 * @param businessNature the businessNature to set
	 */
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	/**
	 * @return the cartypeCode
	 */
	public String getCartypeCode() {
		return cartypeCode;
	}

	/**
	 * @param cartypeCode the cartypeCode to set
	 */
	public void setCartypeCode(String cartypeCode) {
		this.cartypeCode = cartypeCode;
	}

	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}

	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * @return the exchangeRate
	 */
	public double getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param exchangeRate the exchangeRate to set
	 */
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

}
