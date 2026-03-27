package cn.com.sinosoft.inf.PMS.resDomains.prpDmaterialInfoListResPacket;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDmaterialInfoListResBody implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private PrpDmaterialInfoResList PRPDMATERIALINFOLIST = new PrpDmaterialInfoResList();

	public void validate() throws Exception {
	}

	public PrpDmaterialInfoResList getPRPDMATERIALINFOLIST() {
		return PRPDMATERIALINFOLIST;
	}

	public void setPRPDMATERIALINFOLIST(PrpDmaterialInfoResList PRPDMATERIALINFOLIST) {
		this.PRPDMATERIALINFOLIST = PRPDMATERIALINFOLIST;
	}

}
