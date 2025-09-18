package com.tlg.aps.vo;

import java.util.Date;

/* mantis：FIR0349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 */
public class FirBopRenewalDataVo {
	private String batchNo;
	private String oldpolicyno;
	private String orderseq;
	private Date transPpsTime;
	private String proposalno;
	private String policyno;
	private String comcode;
	private String comcname;
	private String handler1code;
	private String username;
	private int ntotal;
	private int npm;
	private String extracomcode;
	private String temp1;
	private String temp2;
	private String temp3;
	private String temp4;
	private String temp5;
	private String temp6;
	private String temp7;
	private String collateralnumber1;
	private String loanaccount1;
	private String creditnumber1;
	//mantis：FIR0608，處理人員：BJ085，需求單編號：FIR0608 外銀板信續件扣款前置檔產生作業_新增保單序號欄位
	private String temp8;
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getOldpolicyno() {
		return oldpolicyno;
	}
	public void setOldpolicyno(String oldpolicyno) {
		this.oldpolicyno = oldpolicyno;
	}
	public String getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(String orderseq) {
		this.orderseq = orderseq;
	}
	public Date getTransPpsTime() {
		return transPpsTime;
	}
	public void setTransPpsTime(Date transPpsTime) {
		this.transPpsTime = transPpsTime;
	}
	public String getProposalno() {
		return proposalno;
	}
	public void setProposalno(String proposalno) {
		this.proposalno = proposalno;
	}
	public String getPolicyno() {
		return policyno;
	}
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
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
	public String getHandler1code() {
		return handler1code;
	}
	public void setHandler1code(String handler1code) {
		this.handler1code = handler1code;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getNtotal() {
		return ntotal;
	}
	public void setNtotal(int ntotal) {
		this.ntotal = ntotal;
	}
	public int getNpm() {
		return npm;
	}
	public void setNpm(int npm) {
		this.npm = npm;
	}
	public String getExtracomcode() {
		return extracomcode;
	}
	public void setExtracomcode(String extracomcode) {
		this.extracomcode = extracomcode;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getTemp3() {
		return temp3;
	}
	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}
	public String getTemp4() {
		return temp4;
	}
	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}
	public String getTemp5() {
		return temp5;
	}
	public void setTemp5(String temp5) {
		this.temp5 = temp5;
	}
	public String getTemp6() {
		return temp6;
	}
	public void setTemp6(String temp6) {
		this.temp6 = temp6;
	}
	public String getTemp7() {
		return temp7;
	}
	public void setTemp7(String temp7) {
		this.temp7 = temp7;
	}
	public String getCollateralnumber1() {
		return collateralnumber1;
	}
	public void setCollateralnumber1(String collateralnumber1) {
		this.collateralnumber1 = collateralnumber1;
	}
	public String getLoanaccount1() {
		return loanaccount1;
	}
	public void setLoanaccount1(String loanaccount1) {
		this.loanaccount1 = loanaccount1;
	}
	public String getCreditnumber1() {
		return creditnumber1;
	}
	public void setCreditnumber1(String creditnumber1) {
		this.creditnumber1 = creditnumber1;
	}
	/*mantis：FIR0608，處理人員：BJ085，需求單編號：FIR0608 外銀板信續件扣款前置檔產生作業_新增保單序號欄位 start*/
	public String getTemp8() {
		return temp8;
	}
	public void setTemp8(String temp8) {
		this.temp8 = temp8;
	}
	/*mantis：FIR0608，處理人員：BJ085，需求單編號：FIR0608 外銀板信續件扣款前置檔產生作業_新增保單序號欄位 end*/
}
