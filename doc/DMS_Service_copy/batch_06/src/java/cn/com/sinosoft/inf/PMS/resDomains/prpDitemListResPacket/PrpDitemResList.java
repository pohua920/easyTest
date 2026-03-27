package cn.com.sinosoft.inf.PMS.resDomains.prpDitemListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDitemResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDitemResInfo> PRPDITEM = new ArrayList<PrpDitemResInfo>();

	public void validate() throws Exception {
	}

	public PrpDitemResInfo[] getPRPDITEM() {
		return PRPDITEM.toArray(new PrpDitemResInfo[0]);
	}

	public void setPRPDITEM(PrpDitemResInfo[] prpDitemInfo) {
		PRPDITEM.clear();
		for(int i=0;i<prpDitemInfo.length;i++){
			PRPDITEM.add(prpDitemInfo[i]);
		}
	}

}
