package cn.com.sinosoft.inf.dict.xmlmsg.countWorkDay;

import java.util.Date;

public class CountWorkDayReqBody {

	private static final long serialVersionUID = 1L;
	
	private Date date;
	private int n ;
	private String flag = "";
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}
