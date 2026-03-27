package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskEngageIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskEngageIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String RISKCODE = "";
	private String ENGAGECODE = "";

	public String getRISKCODE() {
		return RISKCODE;
	}
	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}
	public String getENGAGECODE() {
		return ENGAGECODE;
	}
	public void setENGAGECODE(String engagecode) {
		ENGAGECODE = engagecode;
	}



}
