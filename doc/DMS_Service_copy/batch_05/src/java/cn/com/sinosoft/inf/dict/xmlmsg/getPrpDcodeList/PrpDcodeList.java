package cn.com.sinosoft.inf.dict.xmlmsg.getPrpDcodeList;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class PrpDcodeList implements SchemaNode {

	private static final long serialVersionUID = 1L;

	private Collection<PrpDcodeListResInfo> CODEINFO = new ArrayList<PrpDcodeListResInfo>();

	public void validate() throws Exception {
	}

	public PrpDcodeListResInfo[] getCODEINFO() {
		return CODEINFO.toArray(new PrpDcodeListResInfo[0]);
	}

	public void setCODEINFO(PrpDcodeListResInfo[] codeResInfo) {
		CODEINFO.clear();
		for(int i=0;i<codeResInfo.length;i++){
			CODEINFO.add(codeResInfo[i]);
		}
	}

}
