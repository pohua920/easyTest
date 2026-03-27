package cn.com.sinosoft.inf.PMS.resDomains.prpDcodeComListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDcodeComResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDcodeComResInfo> PRPDCODECOM = new ArrayList<PrpDcodeComResInfo>();

	public void validate() throws Exception {
	}
	public PrpDcodeComResInfo[] getPRPDCODECOM() {
		return PRPDCODECOM.toArray(new PrpDcodeComResInfo[0]);
	}

	public void setPRPDCODECOM(PrpDcodeComResInfo[] prpDcodeComInfo) {
		PRPDCODECOM.clear();
		for(int i=0;i<prpDcodeComInfo.length;i++){
			PRPDCODECOM.add(prpDcodeComInfo[i]);
		}
	}

}
