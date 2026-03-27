package cn.com.sinosoft.inf.PMS.resDomains.prpDriskClauseKindListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDriskClauseKindResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDriskClauseKindResInfo> PRPDRISKCLAUSEKIND = new ArrayList<PrpDriskClauseKindResInfo>();

	public void validate() throws Exception {
	}

	public PrpDriskClauseKindResInfo[] getPRPDRISKCLAUSEKIND() {
		return PRPDRISKCLAUSEKIND.toArray(new PrpDriskClauseKindResInfo[0]);
	}

	public void setPRPDRISKCLAUSEKIND(PrpDriskClauseKindResInfo[] prpDriskClauseKindInfo) {
		PRPDRISKCLAUSEKIND.clear();
		for(int i=0;i<prpDriskClauseKindInfo.length;i++){
			PRPDRISKCLAUSEKIND.add(prpDriskClauseKindInfo[i]);
		}
	}

}
