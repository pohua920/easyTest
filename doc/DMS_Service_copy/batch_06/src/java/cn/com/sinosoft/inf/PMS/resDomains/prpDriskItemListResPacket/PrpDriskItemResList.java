package cn.com.sinosoft.inf.PMS.resDomains.prpDriskItemListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskItemResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDriskItemResInfo> PRPDRISKITEM = new ArrayList<PrpDriskItemResInfo>();

	public void validate() throws Exception {
	}

	public PrpDriskItemResInfo[] getPRPDRISKITEM() {
		return PRPDRISKITEM.toArray(new PrpDriskItemResInfo[0]);
	}

	public void setPRPDRISKITEM(PrpDriskItemResInfo[] prpDriskItemInfo) {
		PRPDRISKITEM.clear();
		for(int i=0;i<prpDriskItemInfo.length;i++){
			PRPDRISKITEM.add(prpDriskItemInfo[i]);
		}
	}

}
