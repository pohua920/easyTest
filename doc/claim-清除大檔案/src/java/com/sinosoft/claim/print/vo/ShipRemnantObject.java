/**
 * 2014-6-13
 */
package com.sinosoft.claim.print.vo;


/**
 * 责任险  残余物理算书  数据对象 ,继承工程险理算，不在添加重复的属性
 * @author 中科軟
 */
public class ShipRemnantObject  extends CompensateObject{
	
	/** 批单号码 */
	private String endorseNo = "";
	/** 保单年度  */
	private String policyYear = "";
	/** 保险条件  */
	private String policyCondition = "";
	/** 保险金额  */
	private String sumAmount = "";
	/** 属性出险原因名称 */
	private String damageName = "";
	/** 属性出险原因名称 */
	private String shipName = "";
	/**運輸方式  ,1-海運、2-空運、3-陸運、4-郵寄*/
	private String transportType = "";
	/**貨物类别代号  */
	private String cargoType = "";
	/** 航程 */
	private String startSitePort = "";
	/** 航程 */
	private String endSitePort = "";
	/** 开航日期  */
	private String portDate = "";
	/** 备案日期 */
	private String registDate = "";
	

	public ShipRemnantObject() {
		super();
	}
	public ShipRemnantObject(CompensateObject compensateObject) {
		super(compensateObject);
	}
	public String getEndorseNo() {
		return endorseNo;
	}
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}
	public String getPolicyYear() {
		return policyYear;
	}
	public void setPolicyYear(String policyYear) {
		this.policyYear = policyYear;
	}
	public String getPolicyCondition() {
		return policyCondition;
	}
	public void setPolicyCondition(String policyCondition) {
		this.policyCondition = policyCondition;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getDamageName() {
		return damageName;
	}
	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getCargoType() {
		return cargoType;
	}
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	public String getStartSitePort() {
		return startSitePort;
	}
	public void setStartSitePort(String startSitePort) {
		this.startSitePort = startSitePort;
	}
	public String getEndSitePort() {
		return endSitePort;
	}
	public void setEndSitePort(String endSitePort) {
		this.endSitePort = endSitePort;
	}
	public String getPortDate() {
		return portDate;
	}
	public void setPortDate(String portDate) {
		this.portDate = portDate;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	
}
