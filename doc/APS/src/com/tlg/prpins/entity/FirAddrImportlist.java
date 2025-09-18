package com.tlg.prpins.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.tlg.iBatis.IBatisBaseEntity;

@SuppressWarnings("serial")
public class FirAddrImportlist extends IBatisBaseEntity<BigDecimal>{
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */
  
    private BigDecimal oid;		//唯一序號
    private String filenameOri;	//匯入檔名
    private String filenameNew;	//對應檔名
    private String fileStatus;	//檔案狀態
    private String remark;		//備註
    private Integer qtyAll;		//資料筆數_全部
    private Integer qtyOk;		//資料筆數_成功
    private Integer qtyNg;		//資料筆數_失敗
    private String deleteFlag;	//刪除註記
    private String icreate;		//建檔人員
    private Date dcreate;		//建檔日期
    private String iupdate;		//最後異動人員
    private Date dupdate;		//最後異動日期
    
    /**mantis：FIR0183，處理人員：BJ016，需求單編號：FIR0183 火險地址資料匯入:排程依據上傳方式決定全刪全增或是部份增修*/
    private String ultype; //上傳類型

    public BigDecimal getOid() {
        return oid;
    }
    public void setOid(BigDecimal oid) {
        this.oid = oid;
    }
    public String getFilenameOri() {
        return filenameOri;
    }
    public void setFilenameOri(String filenameOri) {
        this.filenameOri = filenameOri;
    }
    public String getFilenameNew() {
        return filenameNew;
    }
    public void setFilenameNew(String filenameNew) {
        this.filenameNew = filenameNew;
    }
    public String getFileStatus() {
        return fileStatus;
    }
    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }
    public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getQtyAll() {
        return qtyAll;
    }
    public void setQtyAll(Integer qtyAll) {
        this.qtyAll = qtyAll;
    }
    public Integer getQtyOk() {
        return qtyOk;
    }
    public void setQtyOk(Integer qtyOk) {
        this.qtyOk = qtyOk;
    }
    public Integer getQtyNg() {
        return qtyNg;
    }
    public void setQtyNg(Integer qtyNg) {
        this.qtyNg = qtyNg;
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
	public String getUltype() {
		return ultype;
	}
	public void setUltype(String ultype) {
		this.ultype = ultype;
	}
}