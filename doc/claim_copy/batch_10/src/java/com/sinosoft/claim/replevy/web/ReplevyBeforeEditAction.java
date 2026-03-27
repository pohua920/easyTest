/*
 * @(#)ReplevyBeforeEditAction.java	Mar 11, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.replevy.web;

import java.util.List;

import ins.framework.common.DateTime;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.replevy.util.ReplevyViewHelper;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class ReplevyBeforeEditAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	
	/**追偿viewHelper*/
	private ReplevyViewHelper replevyViewHelper;
	/**用户service*/
	private PrpDuserService prpDuserService;
	/**部门service*/
	private PrpDcompanyService prpDcompanyService;
	/** 理算服務service */
	private PrpLcompensateService prpLcompensateService;
	private CompensateService compensateService;
	/**周日*/
	private String strSunday = null;
	/**周一*/
	private String strMonday = null;
	/**今天*/
	private String strToday = null;
	/**外部代码*/
	private String outerCode = null;
	/**编辑类型*/
	private String editType = "";
	/**出单机构*/
	private String makeComName = "";
	
	private WorkFlowService workFlowService;

	/**
	 * 处理追偿信息的方法
	 * @return
	 * @throws Exception
	 */
	public String replevyBeforeEdit() throws Exception {
		String forward = "";
		HttpServletRequest httpServletRequest = this.getRequest();
		String claimNo = httpServletRequest.getParameter("claimNo");
		String compensateNo = httpServletRequest.getParameter("compensateNo");
		String pageNo = httpServletRequest.getParameter("pageNo");
		String pageSize = httpServletRequest.getParameter("pageSize");
		//支付对象 帳號歸屬人證件類型
		httpServletRequest.setAttribute("prpdpaymentaccountCertificateTypeList", ConstantsCollection.prpdpaymentaccountCertificateTypeList);
		if (pageSize == null || "".equals(pageSize)) {
			pageSize = AppConfig.get("sysconst.ROWS_PERPAGE");
		}
		int intRecordPerPage = Integer.parseInt(pageSize);
		int intPageNo = 1;
		if (pageNo != null && !pageNo.trim().equals("")) {
			intPageNo = Integer.parseInt(pageNo);
		}
		if ("QUERY".equals(editType)) {
			replevyViewHelper.replevyQueryDtoToView(httpServletRequest, intPageNo, intRecordPerPage);
			forward = "success";
		}
		if ("addQuery".equals(editType)) {
			PrpLcompensate prpLcompensate = this.prpLcompensateService.getReplevyPrpLcompensate(claimNo);
			if (prpLcompensate != null) {
				throw new UserException(1, 3, "追償登錄", "該賠案已追償登錄！");
			}
			prpLcompensate = replevyViewHelper.EndCaseToView(httpServletRequest, claimNo);
			httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
			forward = "ADD";
		}
		if ("editQuery".equals(editType)|| "ADD".equals(editType) || "EDIT".equals(editType)) {
			if("ADD".equals(editType)){//追償登錄
				String conditions = " compensateno like 'R"+claimNo+"%' order by compensateno desc ";
				List<PrpLcompensate> list = this.compensateService.findByConditions(conditions);
				if(list.size() >=2 && !"1".equals(list.get(0).getUnderWriteFlag())){
					//包括登录的计算书，存在2张以上（含），且最后一张未审核通过，则不能再做登录处理
					throw new UserException(1, 3, "追償處理", "該賠案已有追償任務正處理進行中！");
				}
			}
			PrpLcompensate prpLcompensate = replevyViewHelper.CompensateToView(httpServletRequest, compensateNo);
			httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
			forward = "EDIT";
		}
		if ("UNDWRT".equals(editType)) {
			String flowID = httpServletRequest.getParameter("swfLogFlowID");
			if (DataUtils.emptyToNull(DataUtils.dbNullToEmpty(flowID)) != null) {
				String logNo = httpServletRequest.getParameter("swfLogLogNo");
				UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
				SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
				if (swfLogDto.getHoldNode() == false) {
					String msg = "該任務已經被代碼:'" + swfLogDto.getHandlerCode() + "',名稱:'" + swfLogDto.getHandlerName() + "'的用戶所占用,請選擇其它該任務進行處理!";
					throw new UserException(1, 3, "追償審批", msg);
				}
			}
			PrpLcompensate prpLcompensate = replevyViewHelper.CompensateToView(httpServletRequest, compensateNo);
			httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
			forward = "UNDWRT";
		}
		if ("SHOW".equals(editType)) {
			PrpLcompensate prpLcompensate = replevyViewHelper.CompensateToView(httpServletRequest, compensateNo);
			httpServletRequest.setAttribute("prpLcompensate", prpLcompensate);
			httpServletRequest.setAttribute("editType", "SHOW");
			forward = "SHOW";
		}
		PrpLcompensate prpLcompensateTemp = (PrpLcompensate) httpServletRequest.getAttribute("prpLcompensate");
		if (prpLcompensateTemp != null) {
			makeComName = prpDcompanyService.getComName(prpLcompensateTemp.getMakeCom());
			String comName = prpDcompanyService.getComName(prpLcompensateTemp.getComCode());
			prpLcompensateTemp.setComName(comName);
			String userName = prpDuserService.getUserName(prpLcompensateTemp.getHandlerCode());
			prpLcompensateTemp.setHandlerName(userName);
			userName = prpDuserService.getUserName(prpLcompensateTemp.getHandler1Code());
			prpLcompensateTemp.setHandler1Name(userName);
		}
		if ("".equals(makeComName)) {
			makeComName = prpLcompensateTemp.getMakeCom();
		}

		outerCode = BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ");
		return forward;
	}

	public String getStrSunday() {
		if (strSunday == null || "".equals(strSunday)) {
			strSunday = DateTime.current().toString();
		}
		return strSunday;
	}

	public void setStrSunday(String strSunday) {
		this.strSunday = strSunday;
	}

	public String getStrMonday() {
		if (strMonday == null || "".equals(strMonday)) {
			strMonday = new DateTime(DateTime.current().addMonth(-1), DateTime.YEAR_TO_DAY).toString();
		}
		return strMonday;
	}

	public void setStrMonday(String strMonday) {
		this.strMonday = strMonday;
	}

	public String getStrToday() {
		if (strToday == null || "".equals(strToday)) {
			strToday = new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).toString();
		}
		return strToday;
	}

	public void setStrToday(String strToday) {
		this.strToday = strToday;
	}

	public String getOuterCode() {
		return outerCode;
	}

	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}

	public String getMakeComName() {
		return makeComName;
	}

	public void setMakeComName(String makeComName) {
		this.makeComName = makeComName;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public ReplevyViewHelper getReplevyViewHelper() {
		return replevyViewHelper;
	}

	public void setReplevyViewHelper(ReplevyViewHelper replevyViewHelper) {
		this.replevyViewHelper = replevyViewHelper;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}
	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}
	
	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}
