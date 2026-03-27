package com.sinosoft.intf.jfcd.model.vo.prpinsCancelConfirmJfcd;

import java.io.Serializable;
import com.sinosoft.intf.jfcd.model.vo.common.BUSINESSLIST;

public class PrpinsCancelConfirmJfcdRespBody implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BUSINESSLIST BUSINESSLIST;
	
	private String RESPONSECODE;

	public BUSINESSLIST getBUSINESSLIST() {
		return BUSINESSLIST;
	}

	public void setBUSINESSLIST(BUSINESSLIST businessList) {
		BUSINESSLIST = businessList;
	}

	public String getRESPONSECODE() {
		return RESPONSECODE;
	}

	public void setRESPONSECODE(String responsecode) {
		RESPONSECODE = responsecode;
	}
	
	

}
