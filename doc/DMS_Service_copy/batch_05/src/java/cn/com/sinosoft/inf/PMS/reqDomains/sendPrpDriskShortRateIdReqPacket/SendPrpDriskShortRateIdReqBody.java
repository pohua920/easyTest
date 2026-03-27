package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskShortRateIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskShortRateIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String RISKCODE = "";
	private String SHORTRATEID = "";
	private String SERIALNO = "";

	public String getRISKCODE() {
		return RISKCODE;
	}
	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}
	public String getSHORTRATEID() {
		return SHORTRATEID;
	}
	public void setSHORTRATEID(String shortrateid) {
		SHORTRATEID = shortrateid;
	}
	public String getSERIALNO() {
		return SERIALNO;
	}
	public void setSERIALNO(String serialno) {
		SERIALNO = serialno;
	}

}
