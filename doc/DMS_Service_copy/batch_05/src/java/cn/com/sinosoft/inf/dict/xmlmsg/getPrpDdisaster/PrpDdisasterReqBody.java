package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDdisaster;

import java.util.Date;



public class PrpDdisasterReqBody {
	
	private String DISASTERCODE;
	
	private Date DAMAGEDATE;
	
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	
	public String getDISASTERCODE() {
		return DISASTERCODE;
	}
	public void setDISASTERCODE(String disastercode) {
		DISASTERCODE = disastercode;
	}

	public Date getDAMAGEDATE() {
		return DAMAGEDATE;
	}

	public void setDAMAGEDATE(Date damagedate) {
		DAMAGEDATE = damagedate;
	}

}
