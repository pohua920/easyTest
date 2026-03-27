package com.sinosoft.claim.reins.vo;

/**
 * 联共保详细信息
 * @author 中科软
 *
 */
public class ReinsCLaimCoinsDetail extends ReinsClaimDetail {
	private static final long serialVersionUID = 1L;

	public static class CoinsType {
		private String coinsTypeCode;

		private CoinsType(String coinsTypeCode) {
			this.coinsTypeCode = coinsTypeCode;
		}

		private String getCoinsTypeCode() {
			return this.coinsTypeCode;
		}

		/**
		 * 系统内
		 */
		public static CoinsType SYSTEM_INSIDE = new CoinsType("1");
		/**
		 * 系统内他方
		 */
		public static CoinsType SYSTEM_INSIDE_THIRD = new CoinsType("2");
		/**
		 * 系统外他方
		 */
		public static CoinsType SYSTEM_OUTSIDE_THIRD = new CoinsType("2");

	}

	private String coinsCode;
	private String coinsName;
	private Double coinsRate;
	private Boolean chiefFlag;
	private CoinsType coinsType;

	public Boolean getChiefFlag() {
		return chiefFlag;
	}

	public void setChiefFlag(Boolean chiefFlag) {
		this.chiefFlag = chiefFlag;
	}

	public String getCoinsCode() {
		return coinsCode;
	}

	public void setCoinsCode(String coinsCode) {
		this.coinsCode = coinsCode;
	}

	public String getCoinsName() {
		return coinsName;
	}

	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}

	public Double getCoinsRate() {
		return coinsRate;
	}

	public void setCoinsRate(Double coinsRate) {
		this.coinsRate = coinsRate;
	}

	public CoinsType getCoinsType() {
		return coinsType;
	}

	public void setCoinsType(CoinsType coinsType) {
		this.coinsType = coinsType;
	}

	public String getCoinsTypeCode() {
		return this.getCoinsType().getCoinsTypeCode();
	}

}
