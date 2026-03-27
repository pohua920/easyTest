package cn.com.sinosoft.inf.PMS.resDomains.prpDmaterialInfoListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDmaterialInfoResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDmaterialInfoResInfo> PRPDMATERIALINFO = new ArrayList<PrpDmaterialInfoResInfo>();

	public void validate() throws Exception {
	}

	public PrpDmaterialInfoResInfo[] getPRPDMATERIALINFO() {
		return PRPDMATERIALINFO.toArray(new PrpDmaterialInfoResInfo[0]);
	}

	public void setPRPDMATERIALINFO(PrpDmaterialInfoResInfo[] prpDmaterialInfoInfo) {
		PRPDMATERIALINFO.clear();
		for(int i=0;i<prpDmaterialInfoInfo.length;i++){
			PRPDMATERIALINFO.add(prpDmaterialInfoInfo[i]);
		}
	}

}
