package cn.com.sinosoft.inf.PMS.resDomains.prpDframeListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDframeResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDframeResInfo> PRPDFRAME = new ArrayList<PrpDframeResInfo>();

	public void validate() throws Exception {
	}

	public PrpDframeResInfo[] getPRPDFRAME() {
		return PRPDFRAME.toArray(new PrpDframeResInfo[0]);
	}

	public void setPRPDFRAME(PrpDframeResInfo[] prpDframeInfo) {
		PRPDFRAME.clear();
		for(int i=0;i<prpDframeInfo.length;i++){
			PRPDFRAME.add(prpDframeInfo[i]);
		}
	}

}
