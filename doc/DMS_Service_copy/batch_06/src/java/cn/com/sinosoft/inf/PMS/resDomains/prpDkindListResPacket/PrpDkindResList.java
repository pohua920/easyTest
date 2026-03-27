package cn.com.sinosoft.inf.PMS.resDomains.prpDkindListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDkindResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDkindResInfo> PRPDKIND = new ArrayList<PrpDkindResInfo>();

	public void validate() throws Exception {
	}

	public PrpDkindResInfo[] getPRPDKIND() {
		return PRPDKIND.toArray(new PrpDkindResInfo[0]);
	}

	public void setPRPDKIND(PrpDkindResInfo[] prpDkindInfo) {
		PRPDKIND.clear();
		for(int i=0;i<prpDkindInfo.length;i++){
			PRPDKIND.add(prpDkindInfo[i]);
		}
	}

}
