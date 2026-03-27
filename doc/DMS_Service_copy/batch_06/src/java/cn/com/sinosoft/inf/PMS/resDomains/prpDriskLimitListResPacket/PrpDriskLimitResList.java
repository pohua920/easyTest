package cn.com.sinosoft.inf.PMS.resDomains.prpDriskLimitListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskLimitResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDriskLimitResInfo> PRPDRISKLIMIT = new ArrayList<PrpDriskLimitResInfo>();

	public void validate() throws Exception {
	}

	public PrpDriskLimitResInfo[] getPRPDRISKLIMIT() {
		return PRPDRISKLIMIT.toArray(new PrpDriskLimitResInfo[0]);
	}

	public void setPRPDRISKLIMIT(PrpDriskLimitResInfo[] prpDriskLimitInfo) {
		PRPDRISKLIMIT.clear();
		for(int i=0;i<prpDriskLimitInfo.length;i++){
			PRPDRISKLIMIT.add(prpDriskLimitInfo[i]);
		}
	}

}
