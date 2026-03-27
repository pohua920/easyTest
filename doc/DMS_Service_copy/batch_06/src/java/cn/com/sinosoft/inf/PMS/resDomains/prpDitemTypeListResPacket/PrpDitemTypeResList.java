package cn.com.sinosoft.inf.PMS.resDomains.prpDitemTypeListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDitemTypeResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDitemTypeResInfo> PRPDITEMTYPE = new ArrayList<PrpDitemTypeResInfo>();

	public void validate() throws Exception {
	}

	public PrpDitemTypeResInfo[] getPRPDITEMTYPE() {
		return PRPDITEMTYPE.toArray(new PrpDitemTypeResInfo[0]);
	}

	public void setPRPDITEMTYPE(PrpDitemTypeResInfo[] prpDitemTypeInfo) {
		PRPDITEMTYPE.clear();
		for(int i=0;i<prpDitemTypeInfo.length;i++){
			PRPDITEMTYPE.add(prpDitemTypeInfo[i]);
		}
	}

}
