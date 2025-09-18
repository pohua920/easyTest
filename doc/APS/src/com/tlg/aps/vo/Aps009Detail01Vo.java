package com.tlg.aps.vo;

import java.util.Date;

public class Aps009Detail01Vo {
	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start*/
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
    private Date startDate;
    private String insuredName;
    private String extraComcode;
    private String comcode;
    private String handler1Name;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getExtraComcode() {
		return extraComcode;
	}

	public void setExtraComcode(String extraComcode) {
		this.extraComcode = extraComcode;
	}

	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	public String getHandler1Name() {
		return handler1Name;
	}

	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}
}
