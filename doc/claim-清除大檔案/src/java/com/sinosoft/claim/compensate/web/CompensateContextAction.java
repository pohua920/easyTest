package com.sinosoft.claim.compensate.web;

import ins.framework.web.Struts2Action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.schema.model.PrpLltextModel;
import com.sinosoft.claim.schema.service.facade.PrpLltextModelService;

/***
 * 根据理算说明类型，取理算说明的明细内容
 * @author 中科软
 *
 */
public class CompensateContextAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	
	private PrpLltextModelService prpLltextModelService;
	private CodeService codeService;
	
	public String getCompeContext(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String contextNo = request.getParameter("contextNo");
		String riskCode = request.getParameter("riskCode");
		String riskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
		String conditions = " riskType = '"+riskType+"' and contextNo = '"+contextNo+"' order by lineNo asc ";
		List<PrpLltextModel> list = this.getPrpLltextModelService().findByConditions(conditions);
		StringBuffer tempContext = new StringBuffer("");
		if(list!=null && !list.isEmpty()){
			for(PrpLltextModel p : list){
				tempContext.append(p.getContext()).append("\r\n");
			}
		}
		try {
			response.setContentType("text/html;charset=GBK");
			response.getWriter().print(tempContext.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	public PrpLltextModelService getPrpLltextModelService() {
		return prpLltextModelService;
	}

	public void setPrpLltextModelService(PrpLltextModelService prpLltextModelService) {
		this.prpLltextModelService = prpLltextModelService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}
}
