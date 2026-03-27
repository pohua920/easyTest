package cn.com.sinosoft.inf.PMS.resDomains.prpDriskListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDriskResInfo> PRPDRISK = new ArrayList<PrpDriskResInfo>();

	public void validate() throws Exception {
	}

	public PrpDriskResInfo[] getPRPDRISK() {
		return PRPDRISK.toArray(new PrpDriskResInfo[0]);
	}

	public void setPRPDRISK(PrpDriskResInfo[] prpDriskInfo) {
		PRPDRISK.clear();
		for(int i=0;i<prpDriskInfo.length;i++){
			PRPDRISK.add(prpDriskInfo[i]);
		}
	}

}
