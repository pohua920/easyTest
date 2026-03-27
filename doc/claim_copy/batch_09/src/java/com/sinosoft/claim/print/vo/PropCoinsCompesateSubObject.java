package com.sinosoft.claim.print.vo;

/**
 *  聯共保計算書 數據子對象
 * @author 中科軟
 *
 */
public class PropCoinsCompesateSubObject {
	/**聯共保方*/
	private String index;
	/**聯共保方 名稱*/
	private String coinsName;
	/**份額*/
	private String coinsRate;
	/**賠款幣種*/
	private String claimPaidCurrency;
	/**賠款金額*/
	private String claimPaid;
	/**費用幣種*/
	private String chargePaidCurrency;
	/**費用金額*/
	private String chargePaid;
	/**合計的金額幣種*/
	private String coinsCurrency;
	/**合計金額*/
	private String coinsSumPaid;
	/**保單號*/
	private String policyNo;
	/**賠案號*/
	private String claimNo;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getCoinsName() {
		return coinsName;
	}
	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}
	public String getCoinsRate() {
		return coinsRate;
	}
	public void setCoinsRate(String coinsRate) {
		this.coinsRate = coinsRate;
	}
	public String getClaimPaidCurrency() {
		return claimPaidCurrency;
	}
	public void setClaimPaidCurrency(String claimPaidCurrency) {
		this.claimPaidCurrency = claimPaidCurrency;
	}
	public String getClaimPaid() {
		return claimPaid;
	}
	public void setClaimPaid(String claimPaid) {
		this.claimPaid = claimPaid;
	}
	public String getChargePaidCurrency() {
		return chargePaidCurrency;
	}
	public void setChargePaidCurrency(String chargePaidCurrency) {
		this.chargePaidCurrency = chargePaidCurrency;
	}
	public String getChargePaid() {
		return chargePaid;
	}
	public void setChargePaid(String chargePaid) {
		this.chargePaid = chargePaid;
	}
	public String getCoinsCurrency() {
		return coinsCurrency;
	}
	public void setCoinsCurrency(String coinsCurrency) {
		this.coinsCurrency = coinsCurrency;
	}
	public String getCoinsSumPaid() {
		return coinsSumPaid;
	}
	public void setCoinsSumPaid(String coinsSumPaid) {
		this.coinsSumPaid = coinsSumPaid;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	
}
