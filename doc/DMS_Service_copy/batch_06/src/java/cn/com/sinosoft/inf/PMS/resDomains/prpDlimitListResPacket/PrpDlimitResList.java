package cn.com.sinosoft.inf.PMS.resDomains.prpDlimitListResPacket;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDlimitResList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDlimitResInfo> PRPDLIMIT = new ArrayList<PrpDlimitResInfo>();

	public void validate() throws Exception {
	}

	public PrpDlimitResInfo[] getPRPDLIMIT() {
		return PRPDLIMIT.toArray(new PrpDlimitResInfo[0]);
	}

	public void setPRPDLIMIT(PrpDlimitResInfo[] prpDlimitInfo) {
		PRPDLIMIT.clear();
		for(int i=0;i<prpDlimitInfo.length;i++){
			PRPDLIMIT.add(prpDlimitInfo[i]);
		}
	}

}
