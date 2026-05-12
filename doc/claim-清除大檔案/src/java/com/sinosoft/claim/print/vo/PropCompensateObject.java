package com.sinosoft.claim.print.vo;

public class PropCompensateObject extends CompensateObject {

	/** 出險原因代號 */
	private String damageCode = "";
	/** 出險原因名稱 */
	private String damageName = "";

	public String getDamageCode() {
		return damageCode;
	}

	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}

	public String getDamageName() {
		return damageName;
	}

	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}

}
