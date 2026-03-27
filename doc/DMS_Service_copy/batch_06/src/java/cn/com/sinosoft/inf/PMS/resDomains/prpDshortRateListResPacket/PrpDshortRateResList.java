package cn.com.sinosoft.inf.PMS.resDomains.prpDshortRateListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDshortRateResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDshortRateResInfo> PRPDSHORTRATE = new ArrayList<PrpDshortRateResInfo>();

	public void validate() throws Exception {
	}

	public PrpDshortRateResInfo[] getPRPDSHORTRATE() {
		return PRPDSHORTRATE.toArray(new PrpDshortRateResInfo[0]);
	}

	public void setPRPDSHORTRATE(PrpDshortRateResInfo[] prpDshortRateInfo) {
		PRPDSHORTRATE.clear();
		for(int i=0;i<prpDshortRateInfo.length;i++){
			PRPDSHORTRATE.add(prpDshortRateInfo[i]);
		}
	}

}
