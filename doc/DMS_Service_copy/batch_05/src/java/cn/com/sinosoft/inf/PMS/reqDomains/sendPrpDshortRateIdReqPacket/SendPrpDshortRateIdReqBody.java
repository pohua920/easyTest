package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDshortRateIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDshortRateIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String SHORTRATEID = "";
	private String SERIALNO = "";

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
