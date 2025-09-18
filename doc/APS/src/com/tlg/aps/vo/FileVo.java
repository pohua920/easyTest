package com.tlg.aps.vo;

public class FileVo {

	private String businessNo;
	private String oid;
	private String name;
	private String downloadPath;
	private String dcreate;
	//mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業
	private String modifyer;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDcreate() {
		return dcreate;
	}

	public void setDcreate(String dcreate) {
		this.dcreate = dcreate;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start*/
	public String getModifyer() {
		return modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}
	/* mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 end*/

}
