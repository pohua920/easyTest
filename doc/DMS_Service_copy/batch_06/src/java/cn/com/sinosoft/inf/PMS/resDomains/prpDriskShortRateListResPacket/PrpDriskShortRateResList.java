package cn.com.sinosoft.inf.PMS.resDomains.prpDriskShortRateListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskShortRateResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDriskShortRateResInfo> PRPDRISKSHORTRATE = new ArrayList<PrpDriskShortRateResInfo>();

	public void validate() throws Exception {
	}

	public PrpDriskShortRateResInfo[] getPRPDRISKSHORTRATE() {
		return PRPDRISKSHORTRATE.toArray(new PrpDriskShortRateResInfo[0]);
	}

	public void setPRPDRISKSHORTRATE(PrpDriskShortRateResInfo[] prpDriskShortRateInfo) {
		PRPDRISKSHORTRATE.clear();
		for(int i=0;i<prpDriskShortRateInfo.length;i++){
			PRPDRISKSHORTRATE.add(prpDriskShortRateInfo[i]);
		}
	}

}
