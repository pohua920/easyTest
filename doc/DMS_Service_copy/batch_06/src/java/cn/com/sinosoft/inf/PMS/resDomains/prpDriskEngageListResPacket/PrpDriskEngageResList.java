package cn.com.sinosoft.inf.PMS.resDomains.prpDriskEngageListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskEngageResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDriskEngageResInfo> PRPDRISKENGAGE = new ArrayList<PrpDriskEngageResInfo>();

	public void validate() throws Exception {
	}

	public PrpDriskEngageResInfo[] getPRPDRISKENGAGE() {
		return PRPDRISKENGAGE.toArray(new PrpDriskEngageResInfo[0]);
	}

	public void setPRPDRISKENGAGE(PrpDriskEngageResInfo[] prpDriskEngageInfo) {
		PRPDRISKENGAGE.clear();
		for(int i=0;i<prpDriskEngageInfo.length;i++){
			PRPDRISKENGAGE.add(prpDriskEngageInfo[i]);
		}
	}

}
