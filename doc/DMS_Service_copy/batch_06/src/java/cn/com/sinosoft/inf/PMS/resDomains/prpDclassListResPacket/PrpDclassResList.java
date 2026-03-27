package cn.com.sinosoft.inf.PMS.resDomains.prpDclassListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDclassResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDclassResInfo> PRPDCLASS = new ArrayList<PrpDclassResInfo>();

	public void validate() throws Exception {
	}

	public PrpDclassResInfo[] getPRPDCLASS() {
		return PRPDCLASS.toArray(new PrpDclassResInfo[0]);
	}

	public void setPRPDCLASS(PrpDclassResInfo[] prpDclassInfo) {
		PRPDCLASS.clear();
		for(int i=0;i<prpDclassInfo.length;i++){
			PRPDCLASS.add(prpDclassInfo[i]);
		}
	}

}
