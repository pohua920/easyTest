package com.tlg.aps.vo;

import java.util.Date;

/**
 * mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
 */
public class TiiTvbcmVo {

    private Long id;
    private String barcode1;
    private String barcode2;
    private String barcode3;
    private String billduedate;
    private String insuredcardno;
    private String carno;
    private String oldcarno;
    private String citizenid;
    private String cartype;
    private String insfee;
    private Date operatedate;
    private String filename;
    private String proposalno;
    private String checktype;
    private String batchno;
    private String underWriteFlag;
    private String printvirtualcode;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBarcode1() {
		return barcode1;
	}
	public void setBarcode1(String barcode1) {
		this.barcode1 = barcode1;
	}
	public String getBarcode2() {
		return barcode2;
	}
	public void setBarcode2(String barcode2) {
		this.barcode2 = barcode2;
	}
	public String getBarcode3() {
		return barcode3;
	}
	public void setBarcode3(String barcode3) {
		this.barcode3 = barcode3;
	}
	public String getBillduedate() {
		return billduedate;
	}
	public void setBillduedate(String billduedate) {
		this.billduedate = billduedate;
	}
	public String getInsuredcardno() {
		return insuredcardno;
	}
	public void setInsuredcardno(String insuredcardno) {
		this.insuredcardno = insuredcardno;
	}
	public String getCarno() {
		return carno;
	}
	public void setCarno(String carno) {
		this.carno = carno;
	}
	public String getOldcarno() {
		return oldcarno;
	}
	public void setOldcarno(String oldcarno) {
		this.oldcarno = oldcarno;
	}
	public String getCitizenid() {
		return citizenid;
	}
	public void setCitizenid(String citizenid) {
		this.citizenid = citizenid;
	}
	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public String getInsfee() {
		return insfee;
	}
	public void setInsfee(String insfee) {
		this.insfee = insfee;
	}
	public Date getOperatedate() {
		return operatedate;
	}
	public void setOperatedate(Date operatedate) {
		this.operatedate = operatedate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getProposalno() {
		return proposalno;
	}
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	public String getChecktype() {
		return checktype;
	}
	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}
	public String getBatchno() {
		return batchno;
	}
	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	public String getUnderWriteFlag() {
		return underWriteFlag;
	}
	public void setUnderWriteFlag(String underWriteFlag) {
		this.underWriteFlag = underWriteFlag;
	}
	public String getPrintvirtualcode() {
		return printvirtualcode;
	}
	public void setPrintvirtualcode(String printvirtualcode) {
		this.printvirtualcode = printvirtualcode;
	}
}
