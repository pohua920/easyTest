package com.sinosoft.claim.print.vo;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;

/***
 * 理算書 賠付對象訊息
 * @author 中科软
 */
public class CompensatePayInfoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 赔付对象 */
	private String ownerName = "";
	/** 证件类型 */
	private String certificateCode = "";
	/** 支付币种 */
	private String currency;
	/** 理賠金額 */
	private String payAmount = "";
	/** 總行代號 */
	private String bankCode = "";
	/** 分行代码 */
	private String customBankCode = "";
	/** 匯款帳號 */
	private String accountCode = "";
	/** 銀行名称 */
	private String bankName = "";
	/** 統一編號 */
	private String uniformNo = "";
	/** 受款人電話 */
	private String beneficiaryPhone = "";
	
	
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getCertificateCode() {
		return certificateCode;
	}
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCustomBankCode() {
		return customBankCode;
	}
	public void setCustomBankCode(String customBankCode) {
		this.customBankCode = customBankCode;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getUniformNo() {
		return uniformNo;
	}
	public void setUniformNo(String uniformNo) {
		this.uniformNo = uniformNo;
	}
	public String getBeneficiaryPhone() {
		return beneficiaryPhone;
	}
	public void setBeneficiaryPhone(String beneficiaryPhone) {
		this.beneficiaryPhone = beneficiaryPhone;
	}
	
	public void init(PrpLpayObjectInfo obj) {
		DecimalFormat df = new DecimalFormat("#,###");
		this.ownerName = obj.getOwnerName();
		this.certificateCode = ConstantsCollection.prpdpaymentaccountCertificateTypeList.get(obj.getCertificateCode());
		this.uniformNo = DataUtils.dbNullToEmpty(obj.getUniformNo());
		this.currency = DataUtils.dbNullToEmpty(obj.getCurrency());
		this.payAmount = DataUtils.dbNullToEmpty(df.format(obj.getPayAmount()));
		this.bankCode = DataUtils.dbNullToEmpty(obj.getBankCode());
		this.customBankCode = DataUtils.dbNullToEmpty(obj.getCustomBankCode());
		this.accountCode = DataUtils.dbNullToEmpty(obj.getAccountCode());
		this.bankName = DataUtils.dbNullToEmpty(obj.getBankName());
	}
}
