package cn.com.sinosoft.inf.dict.xmlmsg.productSYN;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDclass;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class ClassObj implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private List<PrpDclass> prpDclass = new ArrayList<PrpDclass>();

	public List<PrpDclass> getPrpDclass() {
		return prpDclass;
	}

	public void setPrpDclass(List<PrpDclass> prpDclass) {
		this.prpDclass = prpDclass;
	}


	
}
