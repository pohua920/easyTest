package com.sinosoft.claim.reins.vo;

import java.io.Serializable;
import java.util.Collection;

import com.sinosoft.sysframework.common.datatype.DateTime;

/**
 * 再保立案主信息
 * @author 中科软
 *
 */
public class ReinsClaimMain implements Serializable {

	private static final long serialVersionUID = 1L;

	public static class CertiType {
		private String certiTypeCode;

		private CertiType(String certiTypeCode) {
			this.certiTypeCode = certiTypeCode;
		}

		private String getCertiTypeCode() {
			return this.certiTypeCode;
		}

		/**
		 * 立案估损
		 */
		public static CertiType CLAIM = new CertiType("1");
		/**
		 * 预赔
		 */
		public static CertiType PREPAY = new CertiType("2");
		/**
		 * 实赔
		 */
		public static CertiType PAY = new CertiType("3");

	}

	private CertiType certiType;

	private String claimNo;
	private String policyNo;

	private String certiNo;

	private DateTime damageDate;

	private String damageCode;
	private String damageReason;
	private String postCode;
	private String addressName;
	private Boolean coinsFlag;
	private Boolean endCaseFlag;

	private String makeComCode;
	private String createrCode;
	private DateTime createDate;

	// add 20070124 start 立案估损金额
	private Double sumClaim;
	// add 20070124 end
	// SAP添加属性 start
	private String businessNature; // 业务渠道
	private String channelType; // 渠道类型
	private String cartypeCode;// 车型
	private double exchangeRate = 0;// 本位币和签单币别兑换率
	// SAP添加属性 start
	private Collection<ReinsClaimDetail> ReinsClaimDetailList;

	private Collection<ReinsCLaimCoinsDetail> ReinsCLaimCoinsDetailList;

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getCertiNo() {
		return certiNo;
	}

	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public Boolean getCoinsFlag() {
		return coinsFlag;
	}

	public void setCoinsFlag(Boolean coinsFlag) {
		this.coinsFlag = coinsFlag;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public String getCreaterCode() {
		return createrCode;
	}

	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
	}

	public String getDamageCode() {
		return damageCode;
	}

	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}

	public DateTime getDamageDate() {
		return damageDate;
	}

	public void setDamageDate(DateTime damageDate) {
		this.damageDate = damageDate;
	}

	public String getDamageReason() {
		return damageReason;
	}

	public void setDamageReason(String damageReason) {
		this.damageReason = damageReason;
	}

	public Boolean getEndCaseFlag() {
		return endCaseFlag;
	}

	public void setEndCaseFlag(Boolean endCaseFlag) {
		this.endCaseFlag = endCaseFlag;
	}

	public String getMakeComCode() {
		return makeComCode;
	}

	public void setMakeComCode(String makeComCode) {
		this.makeComCode = makeComCode;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public CertiType getCertiType() {
		return certiType;
	}

	public void setCertiType(CertiType certiType) {
		this.certiType = certiType;
	}

	public String getCertiTypeCode() {
		return getCertiType().getCertiTypeCode();
	}

	public Collection<ReinsClaimDetail> getReinsClaimDetailList() {
		return ReinsClaimDetailList;
	}

	public void setReinsClaimDetailList(Collection<ReinsClaimDetail> reinsClaimDetailList) {
		this.ReinsClaimDetailList = reinsClaimDetailList;
	}

	public Collection<ReinsCLaimCoinsDetail> getReinsCLaimCoinsDetailList() {
		return ReinsCLaimCoinsDetailList;
	}

	public void setReinsCLaimCoinsDetailList(Collection<ReinsCLaimCoinsDetail> ReinsCLaimCoinsDetailList) {
		this.ReinsCLaimCoinsDetailList = ReinsCLaimCoinsDetailList;
	}

	public Double getSumClaim() {
		return sumClaim;
	}

	public void setSumClaim(Double sumClaim) {
		this.sumClaim = sumClaim;
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
