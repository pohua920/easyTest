/**
 * 2014-6-13
 */
package com.sinosoft.claim.print.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.common.ConstantCodes;


/**
 * 工程险 理賠計算書  数据对象 
 * @author 中科軟
 */
public class GAAReinsCompensateObject extends CompensateObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**  共保业务  */
	private String coinsType = "";
	/**  共保摊赔年月  */
	private String coinsDate = "";
	/** 共保摊赔号码   */
	private String coinsClaimNo = "";
	/** 共保统一编号   */
	private String uniformNo = "";
	/** 帳户币别 */
	private String currency = ConstantCodes.LOCAL_CURRENCY;
	/** 共保号码 */
	private String coinsBusiness = "";
	/** 承保公司  */
	private String partyInsure = "";
	/** 保单号码  */
//	private String policyNo = "";
	/** 赔案号码  */
	private String claimNo = "";
	/** 填表日期 */
//	private String inputDate = "";
	/** 被保险人 */
//	private String insuredName = "";
	/** 定作人 */
	private String hirer = "";
	/** 定作人代号 */
	private String hirerCode = "";
	/** 标的述要 */
	private String itemRemark = "";
	/** 标的述要代码 */
	private String itemRemarkCode = "";
	/** 施工或保险标的物处所 */
	private String itemAddress = "";
	/** 施工或保险标的物处所 代码 */
	private String itemAddressCode = "";
	/** 保险期间 */
//	private String startDate = "";
//	/** 出险日期 */
//	private String damageStartDate = "";
	/**理算說明*/
	private String ctext = "";
	/** 出险原因代码 */
	private String damageCode = "";
	/** 出险原因名称 */
	private String damageName = "";
	/** 同险代号 */
	private String sameAddressNo = "";
	/** 注记 */
	private String remark = "";
	/** 承保范围 */
	Map<String,Object> compensateSubreport = new HashMap<String,Object>();
	
	public String getCoinsType() {
		return coinsType;
	}
	public void setCoinsType(String coinsType) {
		this.coinsType = coinsType;
	}
	public String getCoinsDate() {
		return coinsDate;
	}
	public void setCoinsDate(String coinsDate) {
		this.coinsDate = coinsDate;
	}
	public String getCoinsClaimNo() {
		return coinsClaimNo;
	}
	public void setCoinsClaimNo(String coinsClaimNo) {
		this.coinsClaimNo = coinsClaimNo;
	}
	public String getUniformNo() {
		return uniformNo;
	}
	public void setUniformNo(String uniformNo) {
		this.uniformNo = uniformNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCoinsBusiness() {
		return coinsBusiness;
	}
	public void setCoinsBusiness(String coinsBusiness) {
		this.coinsBusiness = coinsBusiness;
	}
	public String getPartyInsure() {
		return partyInsure;
	}
	public void setPartyInsure(String partyInsure) {
		this.partyInsure = partyInsure;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getItemRemark() {
		return itemRemark;
	}
	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
	}
	public String getItemAddress() {
		return itemAddress;
	}
	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}
	public String getCtext() {
		return ctext;
	}
	public void setCtext(String ctext) {
		this.ctext = ctext;
	}
	public String getDamageCode() {
		return damageCode;
	}
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	public String getSameAddressNo() {
		return sameAddressNo;
	}
	public void setSameAddressNo(String sameAddressNo) {
		this.sameAddressNo = sameAddressNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHirer() {
		return hirer;
	}
	public void setHirer(String hirer) {
		this.hirer = hirer;
	}
	public String getHirerCode() {
		return hirerCode;
	}
	public void setHirerCode(String hirerCode) {
		this.hirerCode = hirerCode;
	}
	public String getItemRemarkCode() {
		return itemRemarkCode;
	}
	public void setItemRemarkCode(String itemRemarkCode) {
		this.itemRemarkCode = itemRemarkCode;
	}
	public String getItemAddressCode() {
		return itemAddressCode;
	}
	public void setItemAddressCode(String itemAddressCode) {
		this.itemAddressCode = itemAddressCode;
	}
	public Map<String, Object> getCompensateSubreport() {
		return compensateSubreport;
	}
	public void setCompensateSubreport(Map<String, Object> compensateSubreport) {
		this.compensateSubreport = compensateSubreport;
	}
	
	public String getDamageName() {
		return damageName;
	}
	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}
	public void putCompensateSubreport(List<Double> sumLoss,String... keys) {
		if(keys==null||sumLoss==null){
			return;
		}
		String key = null;
		for(int i = 0;i<keys.length;i++){
			if(sumLoss.size()<=i){
				break;
			}
			key = keys[i];
			this.putCompensateSubreport(key, sumLoss.get(i));
		}
	}
	public void putCompensateSubreport(String key,Object value) {
		if(key==null||value==null){
			return;
		}
		Map<String,Object> map = this.getCompensateSubreport();
		if(value instanceof Double){
			Double data = 0D;
			if(this.getCompensateSubreport().get(key)!=null){
				data = (Double)map.get(key);
			}
			data += (Double)value;
			map.put(key,data);
		}else{
			map.put(key, value);
		}
	}
}
