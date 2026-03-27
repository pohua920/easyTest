package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskItemIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskItemIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String RISKCODE = "";
	private String ITEMCODE = "";

	public String getRISKCODE() {
		return RISKCODE;
	}
	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}
	public String getITEMCODE() {
		return ITEMCODE;
	}
	public void setITEMCODE(String itemcode) {
		ITEMCODE = itemcode;
	}



}
