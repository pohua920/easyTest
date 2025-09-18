package com.tlg.aps.vo;

import java.util.Date;

/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 */
@SuppressWarnings("serial")
public class Aps024ExportVo {
    private String policyno;
    private String endorseno;
    private Date underwriteenddate;
    private Date startdate;
    private Date enddate;
    private String p1;
    private String p2;
    private String p3;
    private String addrNo;
    private String addrCode;
    private String oriAddress;
    private String stdAddress;
    private String addrStructure;
    private String addrSumfloors;
    private String validFlag;
    private String icreate;
    private Date dcreate;
    private String iupdate;
    private Date dupdate;
    private String remark;
	private String formattedCode;
	private String formattedMsg;
	private String formattedResult;
	private String formattedAddr;
	private String formattedZip;
	private String formattedCity;
	private String formattedDistrict;
	private String formattedNeighborhood;
	private String formattedRoad;
	private String formattedLane;
	private String formattedAlley1;
	private String formattedAlley2;
	private String formattedNo;
	private String formattedFloor;
	private String formattedOther;
	
	/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 start*/
	private String wallmaterial;
	private String roofmaterial;
	private String buildyears;
	/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 end*/
	
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	public String getEndorseno() {
		return endorseno;
	}
	public void setEndorseno(String endorseno) {
		this.endorseno = endorseno;
	}
	public Date getUnderwriteenddate() {
		return underwriteenddate;
	}
	public void setUnderwriteenddate(Date underwriteenddate) {
		this.underwriteenddate = underwriteenddate;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	public String getP3() {
		return p3;
	}
	public void setP3(String p3) {
		this.p3 = p3;
	}
	public String getAddrNo() {
		return addrNo;
	}
	public void setAddrNo(String addrNo) {
		this.addrNo = addrNo;
	}
	public String getAddrCode() {
		return addrCode;
	}
	public void setAddrCode(String addrCode) {
		this.addrCode = addrCode;
	}
	public String getOriAddress() {
		return oriAddress;
	}
	public void setOriAddress(String oriAddress) {
		this.oriAddress = oriAddress;
	}
	public String getStdAddress() {
		return stdAddress;
	}
	public void setStdAddress(String stdAddress) {
		this.stdAddress = stdAddress;
	}
	public String getAddrStructure() {
		return addrStructure;
	}
	public void setAddrStructure(String addrStructure) {
		this.addrStructure = addrStructure;
	}
	public String getAddrSumfloors() {
		return addrSumfloors;
	}
	public void setAddrSumfloors(String addrSumfloors) {
		this.addrSumfloors = addrSumfloors;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public String getIcreate() {
		return icreate;
	}
	public void setIcreate(String icreate) {
		this.icreate = icreate;
	}
	public Date getDcreate() {
		return dcreate;
	}
	public void setDcreate(Date dcreate) {
		this.dcreate = dcreate;
	}
	public String getIupdate() {
		return iupdate;
	}
	public void setIupdate(String iupdate) {
		this.iupdate = iupdate;
	}
	public Date getDupdate() {
		return dupdate;
	}
	public void setDupdate(Date dupdate) {
		this.dupdate = dupdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFormattedCode() {
		return formattedCode;
	}
	public void setFormattedCode(String formattedCode) {
		this.formattedCode = formattedCode;
	}
	public String getFormattedMsg() {
		return formattedMsg;
	}
	public void setFormattedMsg(String formattedMsg) {
		this.formattedMsg = formattedMsg;
	}
	public String getFormattedResult() {
		return formattedResult;
	}
	public void setFormattedResult(String formattedResult) {
		this.formattedResult = formattedResult;
	}
	public String getFormattedAddr() {
		return formattedAddr;
	}
	public void setFormattedAddr(String formattedAddr) {
		this.formattedAddr = formattedAddr;
	}
	public String getFormattedZip() {
		return formattedZip;
	}
	public void setFormattedZip(String formattedZip) {
		this.formattedZip = formattedZip;
	}
	public String getFormattedCity() {
		return formattedCity;
	}
	public void setFormattedCity(String formattedCity) {
		this.formattedCity = formattedCity;
	}
	public String getFormattedDistrict() {
		return formattedDistrict;
	}
	public void setFormattedDistrict(String formattedDistrict) {
		this.formattedDistrict = formattedDistrict;
	}
	public String getFormattedNeighborhood() {
		return formattedNeighborhood;
	}
	public void setFormattedNeighborhood(String formattedNeighborhood) {
		this.formattedNeighborhood = formattedNeighborhood;
	}
	public String getFormattedRoad() {
		return formattedRoad;
	}
	public void setFormattedRoad(String formattedRoad) {
		this.formattedRoad = formattedRoad;
	}
	public String getFormattedLane() {
		return formattedLane;
	}
	public void setFormattedLane(String formattedLane) {
		this.formattedLane = formattedLane;
	}
	public String getFormattedAlley1() {
		return formattedAlley1;
	}
	public void setFormattedAlley1(String formattedAlley1) {
		this.formattedAlley1 = formattedAlley1;
	}
	public String getFormattedAlley2() {
		return formattedAlley2;
	}
	public void setFormattedAlley2(String formattedAlley2) {
		this.formattedAlley2 = formattedAlley2;
	}
	public String getFormattedNo() {
		return formattedNo;
	}
	public void setFormattedNo(String formattedNo) {
		this.formattedNo = formattedNo;
	}
	public String getFormattedFloor() {
		return formattedFloor;
	}
	public void setFormattedFloor(String formattedFloor) {
		this.formattedFloor = formattedFloor;
	}
	public String getFormattedOther() {
		return formattedOther;
	}
	public void setFormattedOther(String formattedOther) {
		this.formattedOther = formattedOther;
	}
	/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 start*/
	public String getWallmaterial() {
		return wallmaterial;
	}
	public void setWallmaterial(String wallmaterial) {
		this.wallmaterial = wallmaterial;
	}
	public String getRoofmaterial() {
		return roofmaterial;
	}
	public void setRoofmaterial(String roofmaterial) {
		this.roofmaterial = roofmaterial;
	}
	public String getBuildyears() {
		return buildyears;
	}
	public void setBuildyears(String buildyears) {
		this.buildyears = buildyears;
	}
	/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 end*/
}