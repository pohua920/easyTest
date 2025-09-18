package com.tlg.prpins.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;

@SuppressWarnings("serial")
public class FirAgtBatchDtl extends IBatisBaseEntity<BigDecimal> {
	/*mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
	private String batchNo;
	private String orderseq;
    private String businessnature;
    private String orderseqStatus;
    private String riskcode;
    private String dataType;
    private String coreNo;
    private String deleteFlag;
    private String remark;
    private String dataSource;
    private String icreate;
    private Date dcreate;
    private String iupdate;
    private Date dupdate;

    public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getOrderseq() {
		return orderseq;
	}

	public void setOrderseq(String orderseq) {
		this.orderseq = orderseq;
	}

	public String getBusinessnature() {
        return businessnature;
    }

    public void setBusinessnature(String businessnature) {
        this.businessnature = businessnature;
    }

    public String getOrderseqStatus() {
        return orderseqStatus;
    }

    public void setOrderseqStatus(String orderseqStatus) {
        this.orderseqStatus = orderseqStatus;
    }

    public String getRiskcode() {
        return riskcode;
    }

    public void setRiskcode(String riskcode) {
        this.riskcode = riskcode;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCoreNo() {
        return coreNo;
    }

    public void setCoreNo(String coreNo) {
        this.coreNo = coreNo;
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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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
}