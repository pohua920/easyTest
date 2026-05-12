package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * POJO类PrpLgroovyKind
 */
@Entity
@Table(name = "PrpLgroovyKind")
public class PrpLgroovyKind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 主车损失 */
	public static final String MAINCARLOSS = "11";
	/** 三者車 */
	public static final String THIRDCARLOSS = "12";
	/** 主车财损 */
	public static final String MAINPROPLOSS = "21";
	/** 三者物 */
	public static final String THIRDPROPLOSS = "22";
	/** 主车人 伤 */
	public static final String MAINPERSONLOSS = "31";
	/** 三者人 */
	public static final String THIRDPERSONLOSS = "32";
	/** 被保險人/駕駛人 */
	public static final String INSANDDRIVER = "33";

	/** 没有限额 */
	public static final String KINDCODEFORNOLIMIT = "00";
	/** 限额取值类型（计次） */
	public static final String LIMITFORMETERTYPE = "01";
	/** 限额取值类型（保險期間累計） */
	public static final String LIMITFORCUMULATIVETYPE = "02";
	/** 限额取值类型 (每一人/每次事故) */
	public static final String LIMITFORPERPERSONTYPE = "03";
	/** 保额依赖期主险 */
	public static final String AMOUNTREFERMAINKIND = "04";
	/** 赔付时限额依赖的险别 */
	public static final String LIMITREFEROTHERKIND = "05";

	/** 属性id */
	private String id;

	/** 属性险种 */
	private String riskCode = "";

	/** 属性险种名称 */
	private String riskName = "";

	/** 属性险别 */
	private String kindCode = "";

	/** 属性险别名称 */
	private String kindName = "";

	/** 属性赔付类型 */
	private String compensateType = "";
	/** 属性是否是主险 0：否； 1：是 */
	private String isMainKind = "1";
	/** 关联别的险别 依赖险别 */
	private String referOtherKind = "";
	/** 限额类型 0，保險期間累計；1，每次事故；2，每一人； */
	private String limitType = "0";
	/** 限额项目 0 ，保額； A，人傷 + 財產 ；B，財產；C，人傷；D，醫療；E，失能；F，死亡；G，計次*/
	private String limitKind = "";
	/** 限额取值 */
	private String limitValue = "";
	/** 限制次序  */
	private String limitOrder = "";
	/** 項目取值代碼 */
	private String limitJsCode = "";
	/** 限额取值类型（计次） 0：非计次 */
	private String meterType = "0";
	/** 属性标志 */
	private String flag;
	/** 备注 */
	private String remark;
	/** 有效表示 0：否，1：是 */
	private String validStatus = "";
	/**  限额从prpCitemkind的字段，只能是Number类型  */
	private String kindAmount;
	/** 配置sql取值限额 例如： select amount from prpcitemkind where policyNo=${policyNo} and kindCode=${kindCode} */
	private String sqlAmount;

	public PrpLgroovyKind() {
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "riskCode")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	@Column(name = "riskName")
	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	@Column(name = "kindCode")
	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	@Column(name = "kindName")
	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	@Column(name = "compensateType")
	public String getCompensateType() {
		return compensateType;
	}

	public void setCompensateType(String compensateType) {
		this.compensateType = compensateType;
	}

	@Column(name = "isMainKind")
	public String getIsMainKind() {
		return isMainKind;
	}

	public void setIsMainKind(String isMainKind) {
		this.isMainKind = isMainKind;
	}

	@Column(name = "referOtherKind")
	public String getReferOtherKind() {
		return referOtherKind;
	}

	public void setReferOtherKind(String referOtherKind) {
		this.referOtherKind = referOtherKind;
	}

	@Column(name = "limitType")
	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	@Column(name = "meterType")
	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	@Column(name = "flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "validStatus")
	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	@Column(name = "kindAmount")
	public String getKindAmount() {
		return kindAmount;
	}

	public void setKindAmount(String kindAmount) {
		this.kindAmount = kindAmount;
	}
	@Column(name = "sqlAmount")
	public String getSqlAmount() {
		return sqlAmount;
	}

	public void setSqlAmount(String sqlAmount) {
		this.sqlAmount = sqlAmount;
	}
	
	@Column(name = "LIMITKIND")
	public String getLimitKind() {
		return limitKind;
	}

	public void setLimitKind(String limitKind) {
		this.limitKind = limitKind;
	}

	@Column(name = "LIMITVALUE")
	public String getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(String limitValue) {
		this.limitValue = limitValue;
	}

	
	@Column(name = "LIMITORDER")
	public String getLimitOrder() {
		return limitOrder;
	}

	public void setLimitOrder(String limitOrder) {
		this.limitOrder = limitOrder;
	}

	@Column(name = "LIMITJSCODE")
	public String getLimitJsCode() {
		return limitJsCode;
	}

	public void setLimitJsCode(String limitJsCode) {
		this.limitJsCode = limitJsCode;
	}

}
