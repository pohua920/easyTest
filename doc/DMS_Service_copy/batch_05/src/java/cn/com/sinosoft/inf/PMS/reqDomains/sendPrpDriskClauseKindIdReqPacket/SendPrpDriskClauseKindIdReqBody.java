package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDriskClauseKindIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDriskClauseKindIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String RISKCODE = "";
	private String CLAUSEKINDID = "";

	public String getRISKCODE() {
		return RISKCODE;
	}
	public void setRISKCODE(String riskcode) {
		RISKCODE = riskcode;
	}
	public String getCLAUSEKINDID() {
		return CLAUSEKINDID;
	}
	public void setCLAUSEKINDID(String clausekindid) {
		CLAUSEKINDID = clausekindid;
	}



}
