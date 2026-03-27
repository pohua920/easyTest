package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskLimitIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskLimitIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String RISKCODE = "";
	private String SERIALNO = "";
	private String LIMITCODE = "";

	public String getRISKCODE() {
		return RISKCODE;
	}
	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}
	public String getSERIALNO() {
		return SERIALNO;
	}
	public void setSERIALNO(String serialno) {
		SERIALNO = serialno;
	}
	public String getLIMITCODE() {
		return LIMITCODE;
	}
	public void setLIMITCODE(String limitcode) {
		LIMITCODE = limitcode;
	}



}
