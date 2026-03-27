package cn.com.sinosoft.inf.dict.xmlmsg.productSYN;

import java.util.ArrayList;
import java.util.List;


import cn.com.sinosoft.dms.model.PrpDclauseReport;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;

public class ClauseReportObj implements SchemaNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void validate() throws Exception {
		// TODO Auto-generated method stub
		
	}
	private List<PrpDclauseReport> prpdClauseReport=new ArrayList<PrpDclauseReport>();
	public List<PrpDclauseReport> getPrpdClauseReport() {
		return prpdClauseReport;
	}
	public void setPrpdClauseReport(List<PrpDclauseReport> prpdClauseReport) {
		this.prpdClauseReport = prpdClauseReport;
	}
	
}
