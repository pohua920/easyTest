package cn.com.sinosoft.inf.dict.xmlmsg.productSYN;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDframe;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;


public class FrameObj implements SchemaNode{
	private static final long serialVersionUID = 1L;
	public void validate() throws Exception {
	}
	private List<PrpDframe> prpDframe = new ArrayList<PrpDframe>();
	public List<PrpDframe> getPrpDframe() {
		return prpDframe;
	}
	public void setPrpDframe(List<PrpDframe> prpDframe) {
		this.prpDframe = prpDframe;
	}
	
}
