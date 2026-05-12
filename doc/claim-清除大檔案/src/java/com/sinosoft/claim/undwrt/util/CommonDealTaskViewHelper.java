/*
 * @(#)CommonDealTaskViewHelper.java	Feb 20, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.undwrt.bl.facade.BLUwNotionFacade;
import com.sinosoft.undwrt.bl.facade.BLWfLogFacade;
import com.sinosoft.undwrt.dto.domain.UwNotionDto;
import com.sinosoft.undwrt.ui.control.action.UICommonDealSubmitAction;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 核賠處理viewHelper
 */
public class CommonDealTaskViewHelper {
	private PrpLcompensateService prpLcompensateService;
	private PrpLclaimService prpLclaimService;

	/**
	 * 保存审批意见
	 * @param req
	 * @throws Exception
	 */
	public void saveTask(HttpServletRequest req) throws Exception {

		// 保存审批意见，更新工作流日志表中处理部门、处理人员代码及名称、处理时间、节点状态(3)。
		String HandleText = StringUtils.replace(req.getParameter("HandleText"), "'", "''");
		if (HandleText == null) {
			HandleText = "";
		}
		UwNotionDto uwNotionDto = new UwNotionDto();
		uwNotionDto.setFlowID(req.getParameter("FlowId"));
		uwNotionDto.setLogNo(Integer.parseInt(req.getParameter("LogNo")));
		uwNotionDto.setHandleText(HandleText);
		String businessNo = req.getParameter("BusinessNo");
		uwNotionDto.setBusinessNo(businessNo);
		uwNotionDto.setClaimNo(req.getParameter("ClaimNo"));
		uwNotionDto.setNotion(req.getParameter("notion"));

		UserDto userDto = (UserDto) req.getSession(true).getAttribute("user");
		// 将HandleText拆分成多条 变成多个uwNotionDto对象批量插入uwNotion表
		new BLUwNotionFacade().saveTask(uwNotionDto, userDto);
		//快速赔案
		String prpLcompensateSpeedFlag = req.getParameter("prpLcompensateSpeedFlag");
		String replevyFlag = req.getParameter("replevyFlag");
		String replevyRemark = req.getParameter("prpLcompensateReplevyRemark");
		
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(req.getParameter("ClaimNo"));
		if(prpLclaim!=null&&!CommonUtils.isEmpty(replevyFlag)){
			prpLclaim.setReplevyFlag(replevyFlag);
			prpLclaim.setReplevyRemark(replevyRemark);
			prpLclaimService.saveOrUpdate(prpLclaim);
		}
		if(prpLcompensate!=null&&!CommonUtils.isEmpty(prpLcompensateSpeedFlag)){
			prpLcompensate.setSpeedFlag(prpLcompensateSpeedFlag);
			prpLcompensateService.saveOrUpdate(prpLcompensate);
		}
	}

	/**
	 * 审核通过
	 * @param req
	 * @throws Exception
	 */
	public void submitTaskBefore(HttpServletRequest req) throws Exception {
		Collection<?> colSubmitList = null;
		Collection<?> colBackList = null;
		// 保存任务
		this.saveTask(req);
		// 查询提交路径
		int modelNo = Integer.parseInt((String) req.getParameter("ModelNo"));
		int nodeNo = Integer.parseInt((String) req.getParameter("NodeNo"));
		String businessType = (String) req.getParameter("BusinessType");
		String businessNo = (String) req.getParameter("BusinessNo");
		String defaultFlag = (String) req.getParameter("DefaultFlag");
		String comCode = ((UserDto) req.getSession(false).getAttribute("user")).getComCode();
		req.setAttribute("HandTitle", "核賠");
		String flowId = req.getParameter("FlowId");
		int logNo = Integer.parseInt(req.getParameter("LogNo"));
		// 提交路径列表
		colSubmitList = new UICommonDealSubmitAction().getPathes(modelNo, nodeNo, businessType, businessNo, defaultFlag, comCode);
		req.setAttribute("submitList", colSubmitList);
		// 回退路径
		UICommonDealSubmitAction uiCommonDealSubmitAction = new UICommonDealSubmitAction();
		colBackList = uiCommonDealSubmitAction.getBackList(flowId, logNo, nodeNo);
		req.setAttribute("submitBackList", colBackList);
	}

	/**
	 * 批量核保任务审批意见保存
	 * @param req HttpServletRequest
	 * @throws SQLException
	 * @throws Exception
	 */
	public void saveBatchTask(HttpServletRequest req) throws SQLException, Exception {
		String HandleText = StringUtils.replace(req.getParameter("HandleText"), "'", "''");
		if (HandleText == null) {
			HandleText = "";
		}
		Collection<UwNotionDto> uwNotionList = new ArrayList<UwNotionDto>();
		String[] operateFlag = req.getParameterValues("operateFlag");
		String[] flowID = req.getParameterValues("flowID");
		String[] logNo = req.getParameterValues("logNo");
		UwNotionDto uwNotionDto = null;
		for (int i = 0; i < operateFlag.length; i++) {
			if (operateFlag[i].equals("Y") && !logNo[i].equals("0")) {
				uwNotionDto = new UwNotionDto();
				uwNotionDto.setFlowID(flowID[i]);
				uwNotionDto.setLogNo(Integer.parseInt(logNo[i]));
				uwNotionDto.setHandleText(HandleText);
				uwNotionList.add(uwNotionDto);
			}
		}
		PrpDuserDto prpDuserDto = new PrpDuserDto();
		prpDuserDto.setUserCode((String) req.getSession(false).getAttribute("myUserCode"));
		prpDuserDto.setUserName((String) req.getSession(false).getAttribute("myUserName"));
		prpDuserDto.setComCode((String) req.getSession(false).getAttribute("myComCode"));
		// 将HandleText拆分成多条 变成多个uwNotionDto对象批量插入uwNotion表
		new BLUwNotionFacade().saveBatchTask(uwNotionList, prpDuserDto);
	}

	/**
	 * 增加放弃任务功能
	 * @param flowId
	 * @param logNo
	 * @throws Exception
	 */
	public void undoTask(String flowId, int logNo) throws Exception {
		BLWfLogFacade blWflogFacade = new BLWfLogFacade();
		blWflogFacade.undoTask(flowId, logNo);
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

}
