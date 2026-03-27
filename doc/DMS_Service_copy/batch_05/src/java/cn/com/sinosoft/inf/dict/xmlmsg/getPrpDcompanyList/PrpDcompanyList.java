package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcompanyList;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;
import cn.com.sinosoft.inf.dict.xmlmsg.getprpdcompany.PrpDcompanyResInfo;

public class PrpDcompanyList implements SchemaNode{
	private static final long serialVersionUID = 1L;
	private Collection<PrpDcompanyResInfo> PRPDCOMPANY = new ArrayList<PrpDcompanyResInfo>();

	public void validate() throws Exception {
	}

	public PrpDcompanyResInfo[] getPRPDCOMPANY() {
		return PRPDCOMPANY.toArray(new PrpDcompanyResInfo[0]);
	}

	public void setPRPDCOMPANY(PrpDcompanyResInfo[] companyInfo) {
		PRPDCOMPANY.clear();
		for(int i=0;i<companyInfo.length;i++){
			PRPDCOMPANY.add(companyInfo[i]);
		}
	}


}
