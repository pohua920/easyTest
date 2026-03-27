package cn.com.sinosoft.inf.PMS.reqDomains.sendPrpDmaterialInfoIdReqPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class SendPrpDmaterialInfoIdReqBody implements SchemaNode{

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
		// TODO Auto-generated method stub
	}
	private String MATERIALID = "";

	public String getMATERIALID() {
		return MATERIALID;
	}
	public void setMATERIALID(String materialid) {
		MATERIALID = materialid;
	}



}
