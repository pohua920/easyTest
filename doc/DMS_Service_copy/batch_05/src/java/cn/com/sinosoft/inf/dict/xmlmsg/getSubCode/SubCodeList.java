package cn.com.sinosoft.inf.dict.xmlmsg.getSubCode;

import java.util.ArrayList;
import java.util.Collection;

import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class SubCodeList implements SchemaNode {

	private static final long serialVersionUID = 1L;

	private Collection<SubCodeResInfo> SUBCODE = new ArrayList<SubCodeResInfo>();

	public void validate() throws Exception {
	}

	public SubCodeResInfo[] getSUBCODE() {
		return SUBCODE.toArray(new SubCodeResInfo[0]);
	}

	public void setSUBCODE(SubCodeResInfo[] codeResInfo) {
		SUBCODE.clear();
		for(int i=0;i<codeResInfo.length;i++){
			SUBCODE.add(codeResInfo[i]);
		}
	}

}
