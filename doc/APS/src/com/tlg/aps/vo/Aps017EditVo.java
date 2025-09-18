package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.util.Date;

public class Aps017EditVo {
	private BigDecimal oid;
    private String businessnature;
    private String branchNo;
    private String branchName;
    private String handler1code;
    private String remark;
    private String deleteFlag;
    private String icreate;
    private Date dcreate;
    private String iupdate;
    private Date dupdate;

    private String handler1name;
    private String comcode;
    private String comcname;
    private String codecname;
    
    
	public BigDecimal getOid() {
		return oid;
	}
	public void setOid(BigDecimal oid) {
		this.oid = oid;
	}
	public String getBusinessnature() {
		return businessnature;
	}
	public void setBusinessnature(String businessnature) {
		this.businessnature = businessnature;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getHandler1code() {
		return handler1code;
	}
	public void setHandler1code(String handler1code) {
		this.handler1code = handler1code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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
	public String getComcode() {
		return comcode;
	}
	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
	public String getComcname() {
		return comcname;
	}
	public void setComcname(String comcname) {
		this.comcname = comcname;
	}
	public String getCodecname() {
		return codecname;
	}
	public void setCodecname(String codecname) {
		this.codecname = codecname;
	}
	public String getHandler1name() {
		return handler1name;
	}
	public void setHandler1name(String handler1name) {
		this.handler1name = handler1name;
	}

}
