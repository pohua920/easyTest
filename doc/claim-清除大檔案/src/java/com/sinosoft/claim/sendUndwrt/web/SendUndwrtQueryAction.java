package com.sinosoft.claim.sendUndwrt.web;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLSendUndwrt;
import com.sinosoft.claim.schema.service.facade.PrpLSendUndwrtService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfLogStoreService;
import com.sinosoft.sysframework.reference.AppConfig;

/*******************************************************************************
 * 送审查询Action
 * @author 中科软
 */
@SuppressWarnings("serial")
public class SendUndwrtQueryAction extends Struts2Action {
	private PrpLSendUndwrtService prpLSendUndwrtService;
	private SwfLogService swfLogService;
	private SwfLogStoreService swfLogStoreService;

	@SuppressWarnings("unchecked")
	public String sendUndwrtQuery() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		String actionType = httpServletRequest.getParameter("actionType");
		HttpSession session = httpServletRequest.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");
		// 审核任务查询
		if (actionType.equals("Query")) {
			String nodeType = httpServletRequest.getParameter("prpLSendUndwrtNodeType");
			String operatorCode = httpServletRequest.getParameter("prpLSendUndwrtOperatorCode");
			String operatorName = httpServletRequest.getParameter("prpLSendUndwrtOperatorName");
			String comCode = httpServletRequest.getParameter("prpLSendUndwrtComCode");
			String businessNo = httpServletRequest.getParameter("prpLSendUndwrtBusinessNo");

			String conditions = " undwrtcode = '" + userDto.getUserCode() + "' and undwrtflag = '1'";
			if (!"".equals(nodeType)) {
				conditions += " and nodeType = '" + nodeType + "'";
			}
			if (!"".equals(operatorCode)) {
				conditions += " and operatorCode = '" + operatorCode + "'";
			}
			if (!"".equals(operatorName)) {
				conditions += " and operatorName = '" + operatorName + "'";
			}
			if (!"".equals(comCode)) {
				conditions += " and comCode = '" + comCode + "'";
			}
			if (!"".equals(businessNo)) {
				conditions += " and businessNo = '" + businessNo + "'";
			}

			conditions += " order by nodetype,comcode,operatorcode";

			if (pageNo == 0) {
				pageNo = 1;
			}
			if (pageSize == 0) {
				pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
			}
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(conditions);
			Page page = prpLSendUndwrtService.findPrpLSendUndwrt(queryRule, pageNo, pageSize);
			Iterator<PrpLSendUndwrt> iterator = page.getResult().iterator();
			while (iterator.hasNext()) {
				PrpLSendUndwrt prpLSendUndwrt = iterator.next();
				if ("rcase".equals(nodeType)) {
					prpLSendUndwrt.setSwfLogStore(swfLogStoreService.findSwfLogStore(prpLSendUndwrt.getFlowId(), prpLSendUndwrt.getId().getLogNo()));
				} else {
					prpLSendUndwrt.setSwfLog(swfLogService.findSwfLog(prpLSendUndwrt.getFlowId(), prpLSendUndwrt.getId().getLogNo()));
				}
				prpLSendUndwrt.setSwfLog(swfLogService.findSwfLog(prpLSendUndwrt.getFlowId(), prpLSendUndwrt.getId().getLogNo()));
			}
			this.writeJSONData(page, "nodeType", "id", "swfLog", "operatorName", "inputDate");
		}
		return NONE;
	}

	public PrpLSendUndwrtService getPrpLSendUndwrtService() {
		return prpLSendUndwrtService;
	}

	public void setPrpLSendUndwrtService(PrpLSendUndwrtService prpLSendUndwrtService) {
		this.prpLSendUndwrtService = prpLSendUndwrtService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public SwfLogStoreService getSwfLogStoreService() {
		return swfLogStoreService;
	}

	public void setSwfLogStoreService(SwfLogStoreService swfLogStoreService) {
		this.swfLogStoreService = swfLogStoreService;
	}
	

}
