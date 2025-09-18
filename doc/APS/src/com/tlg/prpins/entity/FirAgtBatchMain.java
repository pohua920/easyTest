package com.tlg.prpins.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;

@SuppressWarnings("serial")
public class FirAgtBatchMain extends IBatisBaseEntity<BigDecimal> {
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程 start */
    private String batchNo;
    private String businessnature;
    private String batchType;
    private String fileName;
    private Integer fileQty;
    private Integer filePqty;
    private Integer as400Qty;
    private String fileStatus;
    private String deleteFlag;
    private String remark;
    private String icreate;
    private Date dcreate;
    private String iupdate;
    private Date dupdate;
    private String tempStatus;
    private String tempMsg;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBusinessnature() {
        return businessnature;
    }

    public void setBusinessnature(String businessnature) {
        this.businessnature = businessnature;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileQty() {
        return fileQty;
    }

    public void setFileQty(Integer fileQty) {
        this.fileQty = fileQty;
    }

    public Integer getFilePqty() {
        return filePqty;
    }

    public void setFilePqty(Integer filePqty) {
        this.filePqty = filePqty;
    }

    public Integer getAs400Qty() {
        return as400Qty;
    }

    public void setAs400Qty(Integer as400Qty) {
        this.as400Qty = as400Qty;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

	public String getTempStatus() {
		return tempStatus;
	}

	public void setTempStatus(String tempStatus) {
		this.tempStatus = tempStatus;
	}

	public String getTempMsg() {
		return tempMsg;
	}

	public void setTempMsg(String tempMsg) {
		this.tempMsg = tempMsg;
	}
}