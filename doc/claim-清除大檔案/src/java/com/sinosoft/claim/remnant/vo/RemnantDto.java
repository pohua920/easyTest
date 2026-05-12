package com.sinosoft.claim.remnant.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLbuyer;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLctext;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLremnant;

public class RemnantDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**赔款计算书信息*/
	private PrpLcompensate prpLcompensate;
	/**残余物信息*/
	private List<PrpLremnant> prpLremnantList = new ArrayList<PrpLremnant>(0);
	/**买受人信息*/
	private List<PrpLbuyer> prpLbuyerList = new ArrayList<PrpLbuyer>(0);
	private List<PrpLcharge> prpLchargeList = new ArrayList<PrpLcharge>(0);
	/** 立案主信息 */
	private PrpLclaim prpLclaim;
	/**支付对象信息*/
	private List<PrpLpayObjectInfo> prpLpayObjectInfoList = new ArrayList<PrpLpayObjectInfo>(0);
	private List<PrpLctext> prpLctextList = new ArrayList<PrpLctext>(0);
	private PrpLctext prpLctext;
	
	private RemnantUndwrtDto remnantUndwrtDto;
	
	/**  总赔款金额 */
	private Double sumPaid = 0d;
	/**  理赔确认日期 */
	private Date nowDate;
	
	/** 標的號碼 */
	private String itemNo;
	
	public PrpLcompensate getPrpLcompensate() {
		return prpLcompensate;
	}
	public void setPrpLcompensate(PrpLcompensate prpLcompensate) {
		this.prpLcompensate = prpLcompensate;
	}
	public PrpLclaim getPrpLclaim() {
		return prpLclaim;
	}
	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}
	public List<PrpLpayObjectInfo> getPrpLpayObjectInfoList() {
		return prpLpayObjectInfoList;
	}
	public void setPrpLpayObjectInfoList(List<PrpLpayObjectInfo> prpLpayObjectInfoList) {
		this.prpLpayObjectInfoList = prpLpayObjectInfoList;
	}
	public RemnantUndwrtDto getRemnantUndwrtDto() {
		return remnantUndwrtDto;
	}
	public void setRemnantUndwrtDto(RemnantUndwrtDto remnantUndwrtDto) {
		this.remnantUndwrtDto = remnantUndwrtDto;
	}
	public List<PrpLremnant> getPrpLremnantList() {
		return prpLremnantList;
	}
	public void setPrpLremnantList(List<PrpLremnant> prpLremnantList) {
		this.prpLremnantList = prpLremnantList;
	}
	public List<PrpLbuyer> getPrpLbuyerList() {
		return prpLbuyerList;
	}
	public void setPrpLbuyerList(List<PrpLbuyer> prpLbuyerList) {
		this.prpLbuyerList = prpLbuyerList;
	}
	public List<PrpLcharge> getPrpLchargeList() {
		return prpLchargeList;
	}
	public void setPrpLchargeList(List<PrpLcharge> prpLchargeList) {
		this.prpLchargeList = prpLchargeList;
	}
	public Double getSumPaid() {
		return sumPaid;
	}
	public void setSumPaid(Double sumPaid) {
		this.sumPaid = sumPaid;
	}
	public Date getNowDate() {
		return nowDate;
	}
	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public PrpLctext getPrpLctext() {
		return prpLctext;
	}
	public void setPrpLctext(PrpLctext prpLctext) {
		this.prpLctext = prpLctext;
	}
	public List<PrpLctext> getPrpLctextList() {
		return prpLctextList;
	}
	public void setPrpLctextList(List<PrpLctext> prpLctextList) {
		this.prpLctextList = prpLctextList;
	}

}
