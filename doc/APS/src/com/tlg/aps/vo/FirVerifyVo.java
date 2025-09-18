package com.tlg.aps.vo;

/** mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業
 *  火險外銀轉入續件共用檢核回傳物件
 *  dquakeStatus 複保險查詢狀態
 *  dquakeNo 複保險查詢序號
 *  addrStatus 地址正確性檢核狀態
 *  addrDetail 地址正確性檢核比對差異明細
 *  warnMsg 提醒訊息
 *  errMsg 錯誤訊息
 */
public class FirVerifyVo {

    private String dquakeStatus;
    private String dquakeNo;
    private String addrStatus;
    private String addrDetail;
    private String warnMsg;
    private String errMsg;
    
	public String getDquakeStatus() {
		return dquakeStatus;
	}
	public void setDquakeStatus(String dquakeStatus) {
		this.dquakeStatus = dquakeStatus;
	}
	public String getDquakeNo() {
		return dquakeNo;
	}
	public void setDquakeNo(String dquakeNo) {
		this.dquakeNo = dquakeNo;
	}
	public String getAddrStatus() {
		return addrStatus;
	}
	public void setAddrStatus(String addrStatus) {
		this.addrStatus = addrStatus;
	}
	public String getAddrDetail() {
		return addrDetail;
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	public String getWarnMsg() {
		return warnMsg;
	}
	public void setWarnMsg(String warnMsg) {
		this.warnMsg = warnMsg;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
