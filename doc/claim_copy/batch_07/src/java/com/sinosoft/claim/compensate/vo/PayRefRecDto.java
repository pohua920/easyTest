package com.sinosoft.claim.compensate.vo;

/**
 * 这是计算书赔付情况对象的数据传输对象类<br>
 * @Description 不与数据库表映射,由ApHeadService从AP_HEAD表查询支付信息
 * @author 中科软
 */
public class PayRefRecDto {
	/** 属性计算书号 */
	private String compensateNo = "";
	/** 属性科目代码 */
	private String payRefReason = "";
	/** 属性科目名称 */
	private String reasonName = "";
	/** 属性支付状态 */
	private String status = "";
	/** 属性实际支付时间 */
	private String payDate = "";
	/** 属性支付金额 */
	private double amount = 0D;
	/** 属性支付币别 */
	private String currency = "";
	/** 属性支付对象名称 */
	private String payName = "";
	/** 属性支付方式 */
	private String payMethod = "";

	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	public String getPayRefReason() {
		return payRefReason;
	}

	public void setPayRefReason(String payRefReason) {
		this.payRefReason = payRefReason;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
}
