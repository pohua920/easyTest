package com.tlg.aps.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;

@SuppressWarnings("serial")
/** mantis：OTH0159，處理人員：CD094，需求單編號：OTH0159 電子保單系統條款檢核不通過資料通知(APS)  **/
public class NewepolicyVo extends IBatisBaseEntity<BigDecimal> {
    
	private String logfilename;
	private String filename;
    private String policyno;
    private String endorseno;
    private String resultcode;
    private String resultmessage;
    private String errormessage;
    private String timestamp;
    private Date dcreate;
    private Date dmodify;
    private String resultcode2;
    private String resultmessage2;
    private String errormessage2;
    private String timestamp2;
    private String resultcode3;
    private String resultmessage3;
    private String errormessage3;
    private String timestamp3;
    private String riskcode;
    public String getPolicyno() {
        return policyno;
    }

    public String getLogfilename() {
		return logfilename;
	}

	public void setLogfilename(String logfilename) {
		this.logfilename = logfilename;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

    public String getResultcode() {
        return resultcode;
    }
    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getResultmessage() {
        return resultmessage;
    }
    public void setResultmessage(String resultmessage) {
        this.resultmessage = resultmessage;
    }

    public String getErrormessage() {
        return errormessage;
    }
    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public Date getDcreate() {
        return dcreate;
    }
    public void setDcreate(Date dcreate) {
        this.dcreate = dcreate;
    }
    public Date getDmodify() {
        return dmodify;
    }
    public void setDmodify(Date dmodify) {
        this.dmodify = dmodify;
    }
    public String getResultcode2() {
        return resultcode2;
    }
    public void setResultcode2(String resultcode2) {
        this.resultcode2 = resultcode2;
    }
    public String getResultmessage2() {
        return resultmessage2;
    }
    public void setResultmessage2(String resultmessage2) {
        this.resultmessage2 = resultmessage2;
    }
    public String getErrormessage2() {
        return errormessage2;
    }

    public void setErrormessage2(String errormessage2) {
        this.errormessage2 = errormessage2;
    }
    public String getTimestamp2() {
        return timestamp2;
    }
    public void setTimestamp2(String timestamp2) {
        this.timestamp2 = timestamp2;
    }
    public String getResultcode3() {
        return resultcode3;
    }
    public void setResultcode3(String resultcode3) {
        this.resultcode3 = resultcode3;
    }
    public String getResultmessage3() {
        return resultmessage3;
    }
    public void setResultmessage3(String resultmessage3) {
        this.resultmessage3 = resultmessage3;
    }

    public String getErrormessage3() {
        return errormessage3;
    }
    public void setErrormessage3(String errormessage3) {
        this.errormessage3 = errormessage3;
    }
    public String getTimestamp3() {
        return timestamp3;
    }
    public void setTimestamp3(String timestamp3) {
        this.timestamp3 = timestamp3;
    }

	public String getRiskcode() {
		return riskcode;
	}

	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}
    
}