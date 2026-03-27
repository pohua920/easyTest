package com.sinosoft.claim.workflow.util;

import ins.framework.common.DateTime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.StatStatusDto;

/**
 * <p>
 * Title: WorkFlwoStatViewHelper
 * </p>
 * c
 * <p>
 * Description:工作流一些统计的整理类，在该类中完成页面数据的整理
 * </p>
 * <p>
 * Copyright: Copyright 中科软科技股份有限公司
 * </p>
 * @author 中科软
 * @version 1.0 <br>
 */

public class WorkFlowStatViewHelper {
	/** 代码服务 */
	private CodeService codeService;
	/** 工作流服务 */
	private WorkFlowService workFlowService;

	/**
	 * 默认构造方法
	 */
	public WorkFlowStatViewHelper() {// COM.IIDIDispatch
	}

	/* ========================第五部分：工作流统计操作======================== */
	/**
	 * 工作流统计--节点状态：统计工作流节点状态数量的功能
	 * @param httpServletRequest HttpServletRequest
	 * @param strStartDate String
	 * @param strEndDate String
	 * @throws Exception
	 * @return StatStatusDto
	 */

	public StatStatusDto getNodeStatusStat(HttpServletRequest httpServletRequest, String strStartDate, String strEndDate) throws Exception {
		// 计算日期间隔
		String endDateLast = new DateTime(strEndDate).addDay(1).toString();
		String conditions = " handleTime>='" + strStartDate + "' and handleTime<'" + endDateLast + "' ";
		StatStatusDto statStatusDto = new StatStatusDto();
		List<StatStatusDto> statStatusDtoList = this.getWorkFlowService().getNodeStatusStat(conditions);
		statStatusDto.setStatStatusList(statStatusDtoList);
		statStatusDto.setStartDate(strStartDate);
		statStatusDto.setEndDate(strEndDate);
		// 将查询的结果放入到结果集中
		httpServletRequest.setAttribute("statStatusDto", statStatusDto);
		// 将所有状态数据查询出来
		List<PrpDcode> claimStatusList = this.getCodeService().getCodeType("ClaimStatus", BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAA"));
		httpServletRequest.setAttribute("claimStatusList", claimStatusList);
		return statStatusDto;
	}

	/**
	 * 工作流统计--用户节点状态：统计工作流节点用户状态数量的功能
	 * @param httpServletRequest HttpServletRequest
	 * @param strStartDate String
	 * @param strEndDate String
	 * @throws Exception
	 * @return StatStatusDto
	 */
	public StatStatusDto getNodeUserStatusStat(HttpServletRequest httpServletRequest, String strStartDate, String strEndDate) throws Exception {
		// 计算日期间隔
		String endDateLast = new DateTime(strEndDate).addDay(1).toString();

		String conditions = " handleTime>='" + strStartDate + "' and handleTime<'" + endDateLast + "' ";
		StatStatusDto statStatusDto = new StatStatusDto();
		List<StatStatusDto> statStatusDtoList = this.getWorkFlowService().getNodeUserStatusStat(conditions);
		statStatusDto.setStatStatusList(statStatusDtoList);
		statStatusDto.setStartDate(strStartDate);
		statStatusDto.setEndDate(strEndDate);
		// 将查询的结果放入到结果集中
		httpServletRequest.setAttribute("statStatusDto", statStatusDto);
		return statStatusDto;
	}

	/**
	 * 工作流查询--超时工作流查看：按条件查询超时案件的信息
	 * @param httpServletRequest HttpServletRequest
	 * @param conditions String 查询条件
	 * @throws Exception
	 * @return List<SwfLog>
	 */

	public List<SwfLog> getNodeTimeOutInfo(HttpServletRequest httpServletRequest, String conditions) throws Exception {
		List<SwfLog> swfLogLastList = new ArrayList<SwfLog>();
		SwfLog swfLog = null;
		List<SwfLog> swfLogList = this.getWorkFlowService().findNodesByConditions(conditions);
		Iterator<SwfLog> it = swfLogList.iterator();
		String riskCodeName = "";
		String nodeStatusName = "";
		String nodeTypeName = "";
		int count = 0;
		while (it.hasNext()) {
			// 转换名称
			swfLog = it.next();
			// 计算超时的时间长度
			count = DateTime.intervalDay(new DateTime(swfLog.getHandleTime(), DateTime.YEAR_TO_DAY), 0, new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY), 0) - 1;
			// -1是为了出掉最後统计的当天
			swfLog.setTimeLimit(count);
			// 转换名称
			riskCodeName = this.getCodeService().translateRiskCode(swfLog.getRiskCode(), true);
			swfLog.setRiskCodeName(riskCodeName);
			nodeStatusName = this.getCodeService().translateCodeCode("ClaimStatus", swfLog.getNodeStatus(), true);
			swfLog.setNodeStatusName(nodeStatusName);
			nodeTypeName = this.getCodeService().translateCodeCode("ClaimNodeType", swfLog.getNodeType(), true);
			swfLog.setNodeTypeName(nodeTypeName);
			swfLogLastList.add(swfLog);
		}
		swfLog = new SwfLog();
		swfLog.setSwfLogList(swfLogLastList);
		// 将查询的结果放入到结果集中
		httpServletRequest.setAttribute("swfLog", swfLog);
		return swfLogLastList;
	}

	/**
	 * 根据节点种类和操作状态和办理人员编码查询查勘信息
	 * @param httpServletRequest 返回给页面的request
	 * @param status 操作状态
	 * @param user 办理人员/用户对象
	 * @param nodeType 节点种类
	 * @throws Exception
	 */

	public void getWorkFLowNodeStatsStat(HttpServletRequest httpServletRequest, UserDto user, String nodeType) throws Exception {
		// 根据输入的状态，用户ID生成SQL where 子句
		String conditions = "";
		String riskType = httpServletRequest.getParameter("type"); // add
		// 实赔和其它节点不同
		if (nodeType.equals("compe")) {
			// 意健险审核任务统计区分於其他险种的理算任务统计
			if (nodeType.equals("compe") && (httpServletRequest.getParameter("type") != null && httpServletRequest.getParameter("type").equals("acci"))) {
				conditions = conditions + " ((nodeStatus='0' AND NodeType ='compe'" + " and (flowStatus='1' or flowStatus='2') " + " and (riskcode like '27%'))" + " or (((nodeType='compp'  and  (riskcode like '27%') "
						+ " and ((flowStatus='0' and (nodeStatus='4' or nodeStatus='5' ))" + " or (flowStatus='1' or flowStatus='2'))) and HandlerCode like '%" + user.getUserCode() + "%'))) ";
			} else {
				conditions = conditions + " ((nodeStatus='0' AND NodeType ='compe'" + " and (flowStatus='1' or flowStatus='2') " + " and (riskcode not like '27%'))" + " or (((nodeType='compp'  and  (riskcode like '27%') "
						+ " and ((flowStatus='0' and (nodeStatus='4' or nodeStatus='5' ))" + " or (flowStatus='1' or flowStatus='2'))) and HandlerCode like '%" + user.getUserCode() + "%'))) ";
			}
		} else {// 其他节点的统计条件
			// 原因：根据不同的查询条件，进行意健险和非意健险的区别查询.
			if (httpServletRequest.getParameter("type") != null && httpServletRequest.getParameter("type").equals("acci")) {
				// 以下条件为意键险查询条件
				conditions = conditions + " ((HandlerCode like '%" + user.getUserCode() + "%') or (handlerCode is Null and nodeStatus='0')) AND (NodeType like '%" + nodeType + "%') and (flowStatus='1' or flowStatus='2' or "
						+ "(flowStatus='0' and (nodeStatus='4' or nodeStatus='5' ))) " + " and (riskcode like '27%')";
			} else {
				// 以下条件为非意键险查询条件
				if (nodeType.equals("verip")) { // 核价要包括向外询价任务
					conditions = conditions + " ((HandlerCode like '%" + user.getUserCode() + "%') or (handlerCode is Null and nodeStatus='0')) AND (NodeType like '%verip%'" + " or NodeType like '%verpo%') and (flowStatus='1' or flowStatus='2' or "
							+ "(flowStatus='0' and (nodeStatus='4' or nodeStatus='5' ))) " + " and riskcode not like '27%'";
				} else {
					conditions = conditions + " ((HandlerCode like '%" + user.getUserCode() + "%') or (handlerCode is Null and nodeStatus='0')) AND (NodeType like '%" + nodeType + "%') and (flowStatus='1' or flowStatus='2' or "
							+ "(flowStatus='0' and (nodeStatus='4' or nodeStatus='5' ))) " + " and riskcode not like '27%'";
				}
			}
		}
		// 起始时间
		String statStartDate = httpServletRequest.getParameter("statStartDate");
		if (statStartDate != null && statStartDate.trim().length() > 0) {
			conditions = conditions + " AND HandleTime >= '" + statStartDate + "'";
		}
		// 结束时间
		String statEndDate = httpServletRequest.getParameter("statEndDate");
		if (statEndDate != null && statEndDate.trim().length() > 0) {
			conditions = conditions + " AND HandleTime <= '" + statEndDate + "'";
		}
		// 拼权限
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		// 查询理赔节点状态信息
		// 得到多行报案主表信息
		List<StatStatusDto> claimNodeListLast = new ArrayList<StatStatusDto>();
		String statusCode = "";
		String statusName = "";
		List<StatStatusDto> claimStatusStatList = this.getWorkFlowService().getStatStatus(conditions);
		int intRetrun = 0;
		Iterator<StatStatusDto> it = claimStatusStatList.iterator();
		while (it.hasNext()) {
			StatStatusDto statStatusDtoTemp = it.next();
			statusCode = statStatusDtoTemp.getStatus();
			statusName = this.getCodeService().translateCodeCode("ClaimStatus", statusCode, true);
			// 重新设置统计中的部分数据内容
			if ("0".equals(statusCode) || "1".equals(statusCode)) {
				statStatusDtoTemp.setStatusName("待處理");
			} else {
				statStatusDtoTemp.setStatusName(statusName);
			}
			if ("3".equals(statusCode)) {
				intRetrun = statStatusDtoTemp.getCount();
				statStatusDtoTemp.setStatus(statusCode);
				statStatusDtoTemp.setUserCode(user.getUserCode());
				statStatusDtoTemp.setUserName(user.getUserName());
				claimNodeListLast.add(statStatusDtoTemp);
			}
			for (int i = 0; i < claimNodeListLast.size(); i++) {
				StatStatusDto statStatusDtoTemp1 = (StatStatusDto) claimNodeListLast.get(i);
				if (statStatusDtoTemp1.getStatus().equals("0"))
					statStatusDtoTemp1.setCount(statStatusDtoTemp1.getCount() + intRetrun);
			}
			StatStatusDto statStatusDto = new StatStatusDto();
			statStatusDto.setStatStatusList(claimNodeListLast);
			statStatusDto.setEditType((String) httpServletRequest.getAttribute("editType"));
			statStatusDto.setNodeType(nodeType);
			statStatusDto.setUserCode(user.getUserCode());
			httpServletRequest.setAttribute("statStatusDto", statStatusDto);
			List<PrpDcode> claimStatusList = this.getCodeService().getCodeType("ClaimStatus", "0000");
			httpServletRequest.setAttribute("claimStatusList", claimStatusList);
			httpServletRequest.setAttribute("riskType", riskType);
		}
		/* ========================（工作流统计操作）结束============================ */
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}
