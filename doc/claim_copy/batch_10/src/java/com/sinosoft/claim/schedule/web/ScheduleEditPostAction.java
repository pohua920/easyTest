package com.sinosoft.claim.schedule.web;

import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fubon.util.CreateThread;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.ProcessTokenException;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schedule.util.DAAScheduleViewHelper;
import com.sinosoft.claim.schedule.vo.ScheduleDto;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDagent;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLscheduleMainWF;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpDagentService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpLscheduleMainWFService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 分发HTTP GET 理赔调度节点的保存任务
 * <p>
 * Title: 理赔调度节点的保存任务
 * </p>
 * <p>
 * Description: 理赔调度节点的保存任务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */

public class ScheduleEditPostAction extends Struts2Action {

	private static final long serialVersionUID = 1L;
	/** 报案服务 */
	private PrpLregistService prpLregistService;
	/** 调度服务 */
	private ScheduleService scheduleService;
	/** 调度主服务 */
	private PrpLscheduleMainWFService prpLscheduleMainWFService;
	/** 保单服务 */
	private PolicyService policyService;
	/** 危险单位服务 */
	private PrpDagentService prpDagentService;
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/** 工作流帮助类 */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 调度帮助类服务 */
	private DAAScheduleViewHelper daaScheduleViewHelper;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private BusinessViewHelper businessViewHelper;

	/**
	 * 分案暂存、提交
	 * @return
	 * @throws Exception
	 */
	public String scheduleEditPost() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest httpServletRequest = getRequest();
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logno
		String endflag = httpServletRequest.getParameter("endflag");
		// 需要保存车牌号码，
		String forward = "";
		// 保单条款代码
		try {
			String registNo = httpServletRequest.getParameter("prpLscheduleMainWFRegistNo"); // 报案号
			int scheduleID = -1; // 调度号
			UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
			// 判断在PrpLscheduleMainWF表中是否已存调度信息
			PrpLscheduleMainWF prpLscheduleMainWF = prpLscheduleMainWFService.findPrpLscheduleMainWF(1, registNo);
			int strFlag = 0;
			if (prpLscheduleMainWF == null || prpLscheduleMainWF.getNextHandlerCode() == null || prpLscheduleMainWF.getNextHandlerCode() == "") {
				strFlag = 0;
			} else {
				strFlag = 1;
			}
			// reason: 防止重复提交
			String strLastAccessedTime = "" + httpServletRequest.getSession().getLastAccessedTime() / 1000;
			String oldLastAccessedTime = (String) httpServletRequest.getSession().getAttribute("oldScheduleLastAccessedTime");
			String userMessage = "";
			if (oldLastAccessedTime.trim().equals("")) {
				// reason: 防止重复提交
				httpServletRequest.getSession().setAttribute("oldScheduleLastAccessedTime", strLastAccessedTime);
				// Reason:把调度案件由正在处理改为待处理
				String buttonSaveType = httpServletRequest.getParameter("buttonSaveType");
				if ("20".equals(buttonSaveType)) {
					// 从数据库中查询符合条件的工作流节点
					WorkFlowDto workFlowDto = new WorkFlowDto();
					SwfLog swfLogDto = this.getWorkFlowService().findByPrimaryKey(swfLogFlowID, Integer.parseInt(swfLogLogNo));
					swfLogDto.setNodeStatus("0");
					swfLogDto.setHandlerCode("");
					swfLogDto.setHandlerName("");
					swfLogDto.setFlowStatus("1");
					workFlowDto.setUpdate(true);
					workFlowDto.setUpdateSwfLog(swfLogDto);
					this.getWorkFlowService().deal(workFlowDto);
					forward = "success";
					return forward;
				}
				// 判断是哪种类型的保存
				String editType = httpServletRequest.getParameter("saveType");
				scheduleID = 1;
				httpServletRequest.setAttribute("registNo", registNo);
				httpServletRequest.setAttribute("scheduleID", String.valueOf(scheduleID));
				// 2.用viewHelper整理界面输入
				ScheduleDto scheduleDto = daaScheduleViewHelper.viewToDto(httpServletRequest);
				if (editType.equals("GETBACKEDIT")) {
					// 判断是哪种类型的改派，是定损的，还是
					String getbackNodeType = httpServletRequest.getParameter("getbackNodeType");
					String scheduleObjectID = "";
					String newHandlerCode = "";
					if (!"check".equals(getbackNodeType)) { // 定损改派
						scheduleDto.setPrpLscheduleMainWF(null);
						if (scheduleDto.getPrpLscheduleItemList() != null && scheduleDto.getPrpLscheduleItemList().size() > 0) {
							scheduleDto.setPrpLscheduleItem(scheduleDto.getPrpLscheduleItemList().get(0));
							scheduleObjectID = scheduleDto.getPrpLscheduleItem().getScheduleObjectID();
						}
						// 有没有做换人操作
						// 修改更新原来人员的取值
						newHandlerCode = httpServletRequest.getParameter("nextHandlerCode"); // 若是定损调度
					} else { // 查勘改派
						scheduleObjectID = scheduleDto.getPrpLscheduleMainWF().getScheduleObjectID();
						// 有没有做换人操作
						// 修改更新原来人员的取值
						newHandlerCode = httpServletRequest.getParameter("nextHandlerCode1"); // 若是定损调度
					}
					// 目前改派只保存业务数据，但是，如果是修改节点上的人的话。。。
					WorkFlowDto workFlowDto = new WorkFlowDto();
					// 如果是查勘调度改派，则取的内容不一样
					workFlowDto = workFlowViewHelper.changeFlowNodeHandler(swfLogFlowID, swfLogLogNo, newHandlerCode, user.getUserName() + "进行了调度改派人员" + newHandlerCode, scheduleObjectID);
					// }
					if (workFlowViewHelper.checkDealDto(workFlowDto)) {
						// 将改派後的任务变成待处理
						if (workFlowDto.getUpdateSwfLog() != null) {
							workFlowDto.getUpdateSwfLog().setNodeStatus("0");
						}
						scheduleService.save(scheduleDto, workFlowDto);
					} else {
						scheduleService.save(scheduleDto);
					}
				} else {
					WorkFlowDto workFlowDto = null;
					String actorId = httpServletRequest.getParameter("swfLogActorId");
					if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(actorId)) != null) {
						// 新工作流引擎处理入口
						workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowDto(super.getRequest(), true, true, "2", null, null, registNo, null, null);
					} else {
						//workFlowDto = this.getWorkFlowDto(scheduleDto);
	                       workFlowDto = this.businessViewHelper.getWorkFlowDto(super.getRequest(), true, true, "2", null, null, registNo, null, null);
					}
					if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
						if ("1".equals(endflag) && workFlowDto.getUpdateSwfLog() != null) {
							if (workFlowDto.getUpdateSwfLog().getNodeStatus().equals("4")) {
								workFlowDto.setClose(true);
							} else {
								workFlowDto.setClose(false);
							}
						}
						this.scheduleService.save(scheduleDto, workFlowDto);
//						this.getJbpmBusinessViewHelper().saveBusiness(this.scheduleService,"save",workFlowDto,scheduleDto);
//						user.setUserMessage("備案號:" + registNo);
						String nextHandlerCode1 = scheduleDto.getPrpLscheduleMainWF().getNextHandlerCode();
						String scheduleType1 = scheduleDto.getPrpLscheduleMainWF().getScheduleType();
						PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);// 取得报案信息表--
						// 是否为第一现场标志　0为否，1为是
						if (strFlag == 0 && "1".equals(prpLregist.getSendMesFlag())) {// 当不为调度改派和报案时选择发短信时发信息
							new CreateThread(registNo, nextHandlerCode1, scheduleType1);
							PrpCmain prpCmainDto = this.policyService.findPrpCmainDtoByPrimaryKey(prpLregist.getPolicyNo());
							if (!"".equals(DataUtils.nullToEmpty(prpCmainDto.getAgentCode()))) {// 保单信息中存在代理人代码
								PrpDagent prpDagentDto = this.prpDagentService.findPrpDagent(prpCmainDto.getAgentCode());
								if (prpDagentDto != null && !"".equals(DataUtils.nullToEmpty(prpDagentDto.getMobileNo()))) {// 代理人手机号存在
								}
							}
						}
					} else {
						scheduleService.save(scheduleDto, workFlowDto);
//						user.setUserMessage("備案號:" + registNo + ";註意:沒有發現與工作流流程相關任何數據！！");
						userMessage = "註意：沒有發現與工作流流程相關任何數據！";
					}
				}
				this.addActionMessage(getText("prompt.schedule.submit"));
			} else {// 重复提交
				this.addActionMessage(getText("prompt.again.value"));
			}
			this.addActionMessage("備案號碼");
			this.addActionMessage(registNo);
			if (!CommonUtils.isEmpty(userMessage)) {
				this.addActionMessage(userMessage);
			}
			forward = "success";
		} catch (UserException usee) {
			usee.printStackTrace();
			// 错误信息处理
			forward = "failure";
		} catch (ProcessTokenException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			forward = "failure";
			throw new UserException(-97, -1003, this.getClass().getSimpleName(), e.getMessage());
		}
		return forward;
	}

	/***
	 * 旧工作流引擎处理分案任务
	 * @param scheduleDto 分案大对象
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(ScheduleDto scheduleDto) throws Exception{
		HttpServletRequest request = super.getRequest();
		String activeSchedule = "";// 查勘通知调度进行定损调度
		String swfLogFlowID = request.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = request.getParameter("swfLogLogNo"); // 工作流logno
		String registNo = request.getParameter("prpLscheduleMainWFRegistNo"); // 报案号
		// 查勘调度的选择
		String checkSelectSend = request.getParameter("checkSelectSend");
		String oldcheckFlag = request.getParameter("prpLscheduleMainWFScheduleFlag");
		String endflag = request.getParameter("endflag");
		String[] nextHandlerCode = request.getParameterValues("nextHandlerCode"); // 指定下一个节点操作人代码
		String[] nextHandlerName = request.getParameterValues("nextHandlerName"); // 指定下一个节点操作人姓名
		String[] strNextNode = request.getParameterValues("nextNodeNo"); // 指定下一个节点名
		// 定损调度选择
		String[] selectSend = request.getParameterValues("prpLscheduleItemSelectSend");// 选择进行调度
		String[] prpLscheduleItemItemNo = request.getParameterValues("prpLscheduleItemItemNo");// 调度标底号
		String[] prpLscheduleItemLicenseNo = request.getParameterValues("prpLscheduleItemLicenseNo");// 车牌号码
		String[] prpLscheduleInsureCarFlag = request.getParameterValues("prpLscheduleItemInsureCarFlag");// 是否为本保单投保车辆
		String[] surveyTimes = request.getParameterValues("prpLscheduleItemSurveyTimes");// 是否为已经调度过的？
		String[] prpLscheduleItemScheduleObjectID = request.getParameterValues("prpLscheduleItemScheduleObjectID");
		boolean selectCheckNow = false;
		// 4.以下是工作流处理的过程
		// -----------------------------------------------------
		// 1requst对象,2本节点的节点类型,3本节点需要更新的状态,4本节点的业务号码,5以後节点的业务号码,6本节点的业务流入号码,7以後节点的业务流出号码
		boolean finishSchedule = true;// 是否完成调度
		SwfLog swfLogDtoDealNode = new SwfLog();
		swfLogDtoDealNode.setNodeStatus(scheduleDto.getPrpLclaimStatus().getStatus());
		swfLogDtoDealNode.getId().setFlowID(swfLogFlowID);
		swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
		swfLogDtoDealNode.setNextBusinessNo(registNo);
		swfLogDtoDealNode.setKeyIn(registNo);
		swfLogDtoDealNode.setKeyOut(registNo);
		SwfLog swfLogDtoTemp = this.getWorkFlowService().findNodeByPrimaryKey(swfLogDtoDealNode.getId().getFlowID(), swfLogDtoDealNode.getId().getLogNo());
		String condition = "FLOWID = '" + swfLogFlowID + "' AND NODENO = '3'";
		List<SwfLog> swfLogDtoCheck = this.getWorkFlowService().findByConditions(condition);
		boolean isScheduleCheck = false;
		if (swfLogDtoCheck != null && swfLogDtoCheck.size() > 0) {
			isScheduleCheck = true;
		}
		if ("sched".equals(swfLogDtoTemp.getNodeType())) {
			activeSchedule = swfLogDtoTemp.getTypeFlag();
		}
		// 根据类型不同，操作不同
		List<SwfLog> nextNodeList = new ArrayList<SwfLog>();
		if ("15".equals(activeSchedule)) {
			// 不再查勘了
		}else {
			if ("0".equals(oldcheckFlag) && "1".equals(checkSelectSend) && !isScheduleCheck) {
				String strSchedFlag = "true";
				for (int index = 0; index < Integer.parseInt((String) request.getParameter("maxrow")); index++) {
					if (selectSend[index].equals("0"))
						strSchedFlag = "false";
				}
				// 选择了查勘调度
				SwfLog swfLogNextNode = new SwfLog();
				String nextHandlerCode1 = request.getParameter("nextHandlerCode1");
				String nextHandlerName1 = request.getParameter("nextHandlerName1");
				swfLogNextNode.setNodeNo(0);
				swfLogNextNode.setNodeType("check");
				swfLogNextNode.setHandlerCode(nextHandlerCode1);
				swfLogNextNode.setHandlerName(nextHandlerName1);
				if ("true".equals(strSchedFlag))
					swfLogNextNode.setTypeFlag("15");
				swfLogNextNode.setNewNewHandleDept(scheduleDto.getPrpLscheduleMainWF().getScheduleObjectID());
				nextNodeList.add(swfLogNextNode);
				selectCheckNow = true;// 表示本次选择了查勘调度
			}
		}
		// 因为人到人的原因,但是又2因为不止一个查勘对象，那么。。。主车到调度，三者直接到定损
		int maxRow = Integer.parseInt((String) request.getParameter("maxrow"));
		// 紧急标志位
		String[] exigenceGree = request.getParameterValues("exigenceGree");
		// 调度标的部分开始
		// 如果本次选择了查勘调度，在全流程条件下无论定损是否都选择了，都认为调度就是没有完成
		// endflag=1,表示半流程
		if (selectCheckNow && "0".equals(endflag))
			finishSchedule = false;
		// 如果没选查勘调度，则没有完成
		if ("0".equals(checkSelectSend))
			finishSchedule = false;
		// 定损调度，在都调度结束後，新增定损调度後，产生新的check有问题
		String scheduleID =  (String)request.getAttribute("scheduleID");
		for (int index = 0; index < maxRow; index++) {
			// 只要存在一个没有调度的定损标的，就是认为定损调度没有完成
			if (selectSend[index].equals("0")) {
				finishSchedule = false;
			}

			if (selectSend[index].equals("1") && surveyTimes[index].equals("0")) {
				SwfLog swfLogNextNode = new SwfLog();
				swfLogNextNode.setNodeNo(0);
				swfLogNextNode.setNodeType(strNextNode[index]);// 在界面用js指定了各个调度类型，如定损，人伤什么的。
				swfLogNextNode.setHandlerCode(nextHandlerCode[index]);
				swfLogNextNode.setHandlerName(nextHandlerName[index]);
				// 保存调度号码和itemitemNo的号码
				swfLogNextNode.setScheduleID(Integer.valueOf(scheduleID));
				swfLogNextNode.setLossItemCode(prpLscheduleItemItemNo[index]);
				// 保存车牌的号码
				swfLogNextNode.setLossItemName(prpLscheduleItemLicenseNo[index]);
				// 保存是否保单车辆的标志
				swfLogNextNode.setInsureCarFlag(prpLscheduleInsureCarFlag[index]);
				// 紧急标志位
				swfLogNextNode.setExigenceGree(exigenceGree[index]);
				swfLogNextNode.setNewNewHandleDept(prpLscheduleItemScheduleObjectID[index]);
				// Reason: F22(机动车辆第三者责任保险条款),标的车没有定损，在此自动撤销。
				String strClauseType = request.getParameter("clauseType");
				if ("F22".equals(strClauseType) && "certa".equals(strNextNode[index])&&"1".equals(prpLscheduleItemItemNo[index])) {
					swfLogNextNode.setNodeStatus("6");
				}
				String conditions = "FLOWID = '" + swfLogFlowID + "' and nodeType = '"+strNextNode[index]+"' AND LOSSITEMCODE = '" + prpLscheduleItemItemNo[index] + "' AND (NODENO='4' OR NODENO='15' OR NODENO='19')";
				List<SwfLog> swfLogDtoCerta = this.getWorkFlowService().findByConditions(conditions);
				if (swfLogDtoCerta == null || swfLogDtoCerta.size() <= 0) {
					nextNodeList.add(swfLogNextNode);
				}
			}
		}
		if (nextNodeList.size() > 0) {
			swfLogDtoDealNode.setNextNodeListType("1");// 如果得1，就是需要指定下一个节点的序列，如果不是，就是从模板上寻找下面的节点
			swfLogDtoDealNode.setSwfLogList(nextNodeList);
		}
		// 半流程的特殊处理
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		WorkFlowDto workFlowDto = workFlowViewHelper.viewToDto(user, swfLogDtoDealNode);
		// 3.保存调度信息,没有完成的话，设置状态为2,未完成查勘的，没有 把所有定损调度做完的，都是正在处理的状态
		if (!finishSchedule && workFlowDto.getUpdate()) {
			workFlowDto.getUpdateSwfLog().setNodeStatus("2");
		} else {
			if (workFlowDto.getUpdate()) { // 检查之前是否有查勘，並且还没结束的节点
				// 双代案件,由於查勘是由出险地来做的所以,承保方(commiflag=2)提交时不用判断查勘是否做完
				String msg = "";
				if (!"2".equals(scheduleDto.getPrpLscheduleMainWF().getCommiFlag())) {
					msg = workFlowViewHelper.checkNodeSubmit(swfLogFlowID, swfLogLogNo);
					if (!msg.equals("")) {
						workFlowDto.getUpdateSwfLog().setNodeStatus("2");
					}
				}
			}
		}
		return workFlowDto;
	} 
	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public PrpDagentService getPrpDagentService() {
		return prpDagentService;
	}

	public void setPrpDagentService(PrpDagentService prpDagentService) {
		this.prpDagentService = prpDagentService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public DAAScheduleViewHelper getDaaScheduleViewHelper() {
		return daaScheduleViewHelper;
	}

	public void setDaaScheduleViewHelper(DAAScheduleViewHelper daaScheduleViewHelper) {
		this.daaScheduleViewHelper = daaScheduleViewHelper;
	}

	public PrpLscheduleMainWFService getPrpLscheduleMainWFService() {
		return prpLscheduleMainWFService;
	}

	public void setPrpLscheduleMainWFService(PrpLscheduleMainWFService prpLscheduleMainWFService) {
		this.prpLscheduleMainWFService = prpLscheduleMainWFService;
	}

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
