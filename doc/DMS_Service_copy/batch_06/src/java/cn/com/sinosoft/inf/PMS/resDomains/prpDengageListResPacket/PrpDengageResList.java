package cn.com.sinosoft.inf.PMS.resDomains.prpDengageListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDengageResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDengageResInfo> PRPDENGAGE = new ArrayList<PrpDengageResInfo>();

	public void validate() throws Exception {
	}

	public PrpDengageResInfo[] getPRPDENGAGE() {
		return PRPDENGAGE.toArray(new PrpDengageResInfo[0]);
	}

	public void setPRPDENGAGE(PrpDengageResInfo[] prpDengageInfo) {
		PRPDENGAGE.clear();
		for(int i=0;i<prpDengageInfo.length;i++){
			PRPDENGAGE.add(prpDengageInfo[i]);
		}
	}

}
