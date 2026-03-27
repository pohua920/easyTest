package cn.com.sinosoft.inf.dict.xmlmsg.productSYN;

import java.util.ArrayList;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDarea;
import cn.com.sinosoft.dms.model.PrpDplanClauseKind;
import cn.com.sinosoft.dms.model.PrpDplanLimit;
import cn.com.sinosoft.dms.vo.PrpDplan;
import cn.com.sinosoft.inf.dict.xmlmsg.common.SchemaNode;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo.TranslateObj;

public class PlanObj implements SchemaNode {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}

	private List<PrpDplan> prpDplan = new ArrayList<PrpDplan>();
	private List<PrpDplanClauseKind> prpDplanClauseKind = new ArrayList<PrpDplanClauseKind>();
	private List<PrpDplanLimit> prpDplanLimit = new ArrayList<PrpDplanLimit>();
	private List<PrpDarea> prpDarea = new ArrayList<PrpDarea>();
	private List<TranslateObj> translateObj = new ArrayList<TranslateObj>();
	
	public List<PrpDplan> getPrpDplan() {
		return prpDplan;
	}

	public List<TranslateObj> getTranslateObj() {
		return translateObj;
	}

	public void setTranslateObj(List<TranslateObj> translateObj) {
		this.translateObj = translateObj;
	}

	public void setPrpDplan(List<PrpDplan> prpDplan) {
		this.prpDplan = prpDplan;
	}

	public List<PrpDplanClauseKind> getPrpDplanClauseKind() {
		return prpDplanClauseKind;
	}

	public void setPrpDplanClauseKind(
			List<PrpDplanClauseKind> prpDplanClauseKind) {
		this.prpDplanClauseKind = prpDplanClauseKind;
	}

	public List<PrpDplanLimit> getPrpDplanLimit() {
		return prpDplanLimit;
	}

	public void setPrpDplanLimit(List<PrpDplanLimit> prpDplanLimit) {
		this.prpDplanLimit = prpDplanLimit;
	}

	public List<PrpDarea> getPrpDarea() {
		return prpDarea;
	}

	public void setPrpDarea(List<PrpDarea> prpDarea) {
		this.prpDarea = prpDarea;
	}

}
