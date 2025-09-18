package com.tlg.aps.vo;

import java.util.Date;

/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
public class Aps060DownloadlVo {

	private String batchNo;
	private Integer dataqtyT;
	private Integer dataqtyS;
	private Integer dataqtyF;
	private String f01Nrec;
	private String f02Nrec;
	private String rnYyyymm;
	
    private String reUser;
    private Date reTime;
    private String reMemo;
    private String reBno;
    private String rnproUser;
    private Date rnproTime;
    private String rnproMemo;
    private String rnproBno;
    private String enUser;
    private Date enTime;
    private String enMemo;
    private String enBno;
    private String icreate;
    private Date dcreate;
    private String iupdate;
    private Date dupdate;
    
    private String rnUser;
    private Date rnTime;
    private String rnMemo;
    private String rnBno;
    
    //FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔  start
    private String poUser;
    private Date poTime;
    private String poMemo;
    private String poBno;
    
    private String coUser;
    private Date coTime;
    private String coMemo;
    private String coBno;
    //FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔  end 
    
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Integer getDataqtyT() {
		return dataqtyT;
	}
	public void setDataqtyT(Integer dataqtyT) {
		this.dataqtyT = dataqtyT;
	}
	public Integer getDataqtyS() {
		return dataqtyS;
	}
	public void setDataqtyS(Integer dataqtyS) {
		this.dataqtyS = dataqtyS;
	}
	public Integer getDataqtyF() {
		return dataqtyF;
	}
	public void setDataqtyF(Integer dataqtyF) {
		this.dataqtyF = dataqtyF;
	}
	public String getF01Nrec() {
		return f01Nrec;
	}
	public void setF01Nrec(String f01Nrec) {
		this.f01Nrec = f01Nrec;
	}
	public String getF02Nrec() {
		return f02Nrec;
	}
	public void setF02Nrec(String f02Nrec) {
		this.f02Nrec = f02Nrec;
	}
	public String getRnYyyymm() {
		return rnYyyymm;
	}
	public void setRnYyyymm(String rnYyyymm) {
		this.rnYyyymm = rnYyyymm;
	}
	public String getReUser() {
		return reUser;
	}
	public void setReUser(String reUser) {
		this.reUser = reUser;
	}
	public Date getReTime() {
		return reTime;
	}
	public void setReTime(Date reTime) {
		this.reTime = reTime;
	}
	public String getReMemo() {
		return reMemo;
	}
	public void setReMemo(String reMemo) {
		this.reMemo = reMemo;
	}
	public String getReBno() {
		return reBno;
	}
	public void setReBno(String reBno) {
		this.reBno = reBno;
	}
	public String getRnproUser() {
		return rnproUser;
	}
	public void setRnproUser(String rnproUser) {
		this.rnproUser = rnproUser;
	}
	public Date getRnproTime() {
		return rnproTime;
	}
	public void setRnproTime(Date rnproTime) {
		this.rnproTime = rnproTime;
	}
	public String getRnproMemo() {
		return rnproMemo;
	}
	public void setRnproMemo(String rnproMemo) {
		this.rnproMemo = rnproMemo;
	}
	public String getRnproBno() {
		return rnproBno;
	}
	public void setRnproBno(String rnproBno) {
		this.rnproBno = rnproBno;
	}
	public String getEnUser() {
		return enUser;
	}
	public void setEnUser(String enUser) {
		this.enUser = enUser;
	}
	public Date getEnTime() {
		return enTime;
	}
	public void setEnTime(Date enTime) {
		this.enTime = enTime;
	}
	public String getEnMemo() {
		return enMemo;
	}
	public void setEnMemo(String enMemo) {
		this.enMemo = enMemo;
	}
	public String getEnBno() {
		return enBno;
	}
	public void setEnBno(String enBno) {
		this.enBno = enBno;
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
	public String getRnUser() {
		return rnUser;
	}
	public void setRnUser(String rnUser) {
		this.rnUser = rnUser;
	}
	public Date getRnTime() {
		return rnTime;
	}
	public void setRnTime(Date rnTime) {
		this.rnTime = rnTime;
	}
	public String getRnMemo() {
		return rnMemo;
	}
	public void setRnMemo(String rnMemo) {
		this.rnMemo = rnMemo;
	}
	public String getRnBno() {
		return rnBno;
	}
	public void setRnBno(String rnBno) {
		this.rnBno = rnBno;
	}
	
	public String getPoUser() {
		return poUser;
	}
	public void setPoUser(String poUser) {
		this.poUser = poUser;
	}
	public Date getPoTime() {
		return poTime;
	}
	public void setPoTime(Date poTime) {
		this.poTime = poTime;
	}
	public String getPoMemo() {
		return poMemo;
	}
	public void setPoMemo(String poMemo) {
		this.poMemo = poMemo;
	}
	public String getPoBno() {
		return poBno;
	}
	public void setPoBno(String poBno) {
		this.poBno = poBno;
	}
	public String getCoUser() {
		return coUser;
	}
	public void setCoUser(String coUser) {
		this.coUser = coUser;
	}
	public Date getCoTime() {
		return coTime;
	}
	public void setCoTime(Date coTime) {
		this.coTime = coTime;
	}
	public String getCoMemo() {
		return coMemo;
	}
	public void setCoMemo(String coMemo) {
		this.coMemo = coMemo;
	}
	public String getCoBno() {
		return coBno;
	}
	public void setCoBno(String coBno) {
		this.coBno = coBno;
	}
	
}
