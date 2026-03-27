package cn.com.sinosoft.inf.PMS.resDomains.prpDplanListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDplanResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDplanResInfo> PRPDPLAN = new ArrayList<PrpDplanResInfo>();

	public void validate() throws Exception {
	}

	public PrpDplanResInfo[] getPRPDPLAN() {
		return PRPDPLAN.toArray(new PrpDplanResInfo[0]);
	}

	public void setPRPDPLAN(PrpDplanResInfo[] prpDplanInfo) {
		PRPDPLAN.clear();
		for(int i=0;i<prpDplanInfo.length;i++){
			PRPDPLAN.add(prpDplanInfo[i]);
		}
	}

}
