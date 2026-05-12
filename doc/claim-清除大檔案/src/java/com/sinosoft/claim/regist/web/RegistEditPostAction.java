package com.sinosoft.claim.regist.web;

import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 分发HTTP Post 车险理赔报案编辑界面
 * <p>
 * Title: 车险理赔报案编辑界面信息
 * </p>
 * <p>
 * Description: 车险理赔报案编辑界面信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: sinosoft.com.cn
 * </p>
 * @author 中科软
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RegistEditPostAction extends Struts2Action {
	/** 报案数据收集信息 */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 提升信息 */
	private String message = "";
	/** 报案业务处理服务 */
	private RegistService registService;
	/** 承保险别服务 */
	private PrpCitemKindService prpCitemKindService;
	/** 单号生成规则服务 */
	private BillService billService;
	/** 代码翻译服务 */
	private CodeService codeService;
	/** 工作流数据收集信息 */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 赔案保单关联服务 */
	private PrplregistrpolicyService prpLregistrpolicyService;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private WorkFlowService workFlowService;
	private PrpCmainService prpCmainService;
	private BusinessViewHelper businessViewHelper;

	/**
	 * 报案提交
	 * @return 页面类型
	 * @throws Exception
	 */
	public String registEditPost() throws Exception {
		this.clearErrorsAndMessages();
		String forward = ""; // 向前流转
		String registNo = "";
		/*
		 * 程序思路：========================================================
		 * [1]根据claimNo在界面是不是为空，判断是不是第一次保存报案表
		 * [2]为空，则取报案号，並使得intCreateWorkFlowFlag =1[3]保存报案表信息
		 * [4]保存案件状态表信息，strNodeStatus[5]如果intCreateWorkFlowFlag =1 创建新流程
		 * [6]判断strNodeStatus是不是等於提交，不是的话，直接修改工作流Map中的状态位做Update
		 * [7]如是提交，执行Complate操作。
		 * ========================================================
		 */
		// 取报案号
		HttpServletRequest httpServletRequest = getRequest();
		registNo = httpServletRequest.getParameter("prpLregistRegistNo");
		// 交强险迁移 报案类型 0 ：商业险单独报案 1：交强险单独报案 2：商业、交强险关联报案
		String registType = httpServletRequest.getParameter("registType");
		// 如果是新登记，则从取号表中取赔案号码，如果是修改，则保持原来的claimNo不变
		String createWorkFlowFlag = "0"; // 是否需要创建工作流，如果第一次保存，需要创建工作流 0				// 表示不需要
		String registPolicyNo = httpServletRequest.getParameter("prpLregistPolicyNo");
		String riskCode = httpServletRequest.getParameter("prpLregistRiskCode");
		// 交强险迁移
		if (registType != null && registType.equals("1")) {
			registPolicyNo = httpServletRequest.getParameter("mainPolicyNo");
			riskCode = ConstantCodes.RISKCODE_DAZ;
		}
		String scheduleType = httpServletRequest.getParameter("scheduleType");
		String editType = httpServletRequest.getParameter("editType");
		String typeFlag = "";
		if ("ALLS".equals(scheduleType)) {
			typeFlag = "10";
		}
		String mainPolicyNo = httpServletRequest.getParameter("mainPolicyNo");
		String qsFlag = httpServletRequest.getParameter("qsFlag");
		String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		String comCodePolicy = httpServletRequest.getParameter("prpLregistComCode");
		// 传入参数是 节点的类型
		// 是否查勘调度
		String strScheduleTypeCheck = httpServletRequest.getParameter("nextScheduleTypeCheck");
		String strLastAccessedTime = "" + httpServletRequest.getSession().getLastAccessedTime() / 1000;
		String oldLastAccessedTime = (String) httpServletRequest.getSession().getAttribute("oldRegistLastAccessedTime");
		if (DataUtils.emptyToNull(oldLastAccessedTime) != null) {
			throw new UserException(1, 3, "0000", getText("prompt.regist.multipleSubmit"));//請不要重複提交！
		}
		if (registNo == null || registNo.length() < 1) { // 取报案号码
			String tableName = "prplregist";
			// 报案号生成规则调整
			// 机构设置如果获取不到，就按照总公司读取
			String prpLregistDamageCode = httpServletRequest.getParameter("prpLregistDamageCode");
			Map<String, Object> infoMap = new HashMap<String, Object>();
			infoMap.put("damageCode", prpLregistDamageCode);
			infoMap.put("policyNo", registPolicyNo);
			registNo = billService.getNoByPolciyYear(tableName, riskCode, infoMap);
			httpServletRequest.setAttribute("com.sinosoft.registno", registNo);
			httpServletRequest.setAttribute("riskCode", riskCode);
			createWorkFlowFlag = "1";
		}
		httpServletRequest.setAttribute("registNo", registNo);
		httpServletRequest.setAttribute("newWorkFlow", createWorkFlowFlag);
		String status = httpServletRequest.getParameter("buttonSaveType");
		// 用viewHelper整理界面输入
		RegistDto registDto = daaRegistViewHelper.viewToDto(httpServletRequest);
		WorkFlowDto workFlowDto = new WorkFlowDto();
		String actorId = httpServletRequest.getParameter("swfLogActorId");
		// 创建工作流、处理启用新工作流的生产的工作任务时
		boolean create = "1".equals(createWorkFlowFlag);
		if (WorkFlowDto.isWorkflowswitch() && ("1".equals(createWorkFlowFlag) || !"".equals(DataUtils.dbNullToEmpty(actorId)))) {
			workFlowDto = this.jbpmBusinessViewHelper.getJbpmWorkFlowDto(super.getRequest(), !create, create, status, null, null, null, null, null);
			workFlowDto.setCreate("1".equals(createWorkFlowFlag));
			workFlowDto.setBessinessNo(registNo);
			if(workFlowDto.getSubmit()){
				workFlowDto.getJbpmDto().putParamsMap("registType", registDto.getPrpLregist().getRegistType());// 备案类型
				if("Q".equals(strRiskType)){//火險簡易流程標誌 --22-分進業務 走簡易流程
					PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(registDto.getPrpLregist().getPolicyNo());
					workFlowDto.getJbpmDto().putParamsMap("simpleFlowFlag", "3".equals(prpCmain.getCoinsFlag()));
				}
			}
		} else {// 旧工作流处理入口
			//workFlowDto = this.getWorkFlowDto(registDto, strRiskType, typeFlag, registPolicyNo, mainPolicyNo, qsFlag);
		    workFlowDto = this.businessViewHelper.getWorkFlowDto(super.getRequest(), !create, create, status, null, null, null, null, null);
            workFlowDto.setCreate("1".equals(createWorkFlowFlag));
            workFlowDto.setBessinessNo(registNo);
		}
		if (workFlowViewHelper.checkDealDto(workFlowDto) && !"PERFECT".equals(editType)) {
			// 判断是否关联报案
			String tempPolicyNo = "";// 用于判断同业共摊的保单号
			if (registDto.getPrpLRegistRPolicyList() != null && registDto.getPrpLRegistRPolicyList().size() > 1) {
				for (Prplregistrpolicy p : registDto.getPrpLRegistRPolicyList()) {// 关联备案
					if (Prplregistrpolicy.COMPEL_POLICY.equals(p.getPolicyType())) {
						tempPolicyNo = p.getId().getPolicyNo();// 取强制单号
						break;
					}
				}
			}
			PrpLregist tempPrpLregist = registDto.getPrpLregist();
			if ("1".equals(tempPrpLregist.getRegistType())) {// 强制险单独备案情况
				tempPolicyNo = tempPrpLregist.getPolicyNo();
			}
			if (DataUtils.emptyToNull(tempPolicyNo) != null) {// 判断该保单是否存在同业共摊情况
				String hisSharingRegistNo = prpLregistrpolicyService.getSharingRegistNo(tempPolicyNo, tempPrpLregist);
				if (DataUtils.emptyToNull(hisSharingRegistNo) != null) {
					//備案	此保單涉及同業共攤，相關備案號為：		。如需備案，請通過人工判取消此標記。
					throw new UserException(-1, 0, getText("check.report"), getText("prompt.regist.commonBusinessRegistNo") + hisSharingRegistNo + getText("prompt.regist.ifRegistPleaseCancelFlagHandle"));
				}
			}
			this.registService.save(registDto, workFlowDto);
//			this.jbpmBusinessViewHelper.saveBusiness(this.registService,"save",workFlowDto,registDto);
//			user.setUserMessage(registNo);
		} else {
			if (workFlowDto.getOperateResult() < 0) {
				//注意:創建工作流流程時，未找到相關工作流模板的設定，請聯系系統管理員進行相應配置！
				user.setUserMessage(getText("prompt.regist.workFlowNeedConfig"));
			} else {
				if ("PERFECT".equals(editType)) {
					registService.saveCallCenter(registDto, null, null);
				} else {
					registService.save(registDto);
				}
//				if (!"1".equals(httpServletRequest.getParameter("callCenterFlag"))) {
//					//;注意:沒有發現與工作流流程相關任何數據！
//					user.setUserMessage(registNo + getText("prompt.regist.noWorkFlowDataFound"));
//				}
			}
		}
		httpServletRequest.getSession().setAttribute("oldRegistLastAccessedTime", strLastAccessedTime);
		// 跟据配置项 SCHEDULE_AUTOCOMMIT，对於需要自动跳转到调度的部门加入自动跳转的功能
		String strSchedule = AppConfig.get("sysconst.SCHEDULE_AUTOCOMMIT");
		String comCodeTemp = user.getComCode();
		if ("4".equals(status) && "D".equals(strRiskType) && strSchedule.indexOf(comCodeTemp) >= 0 && (!("3100".equals(comCodeTemp)))) {
			// 需要自动跳转,必须是提交後才能进行的跳转
			// 要判断是查勘调度，还是定损调度
			String scheduleRef = "/claim/scheduleDealRegist.do?prpLscheduleMainWFRegistNo=" + registNo + "&prpLscheduleMainWFSurveyNo=0" + "&status=0" + "&riskCode=" + riskCode + "&editType=ADD";
			String goFlowID = "";
			int goLogNo = 0;
			// 查找跳转位置
			if (workFlowDto.getSubmitSwfLogList() != null) {
				for (int j = 0; j < workFlowDto.getSubmitSwfLogList().size(); j++) {
					SwfLog sfgo = (workFlowDto.getSubmitSwfLogList()).get(j);
					if (strScheduleTypeCheck.equals("1")) { // 查勘调度
						if (sfgo.getNodeType().equals("sched")) {
							goFlowID = sfgo.getId().getFlowID();
							goLogNo = sfgo.getId().getLogNo();
							scheduleRef = scheduleRef + "&nodeType=sched" + "&scheduleType=sched";
							break;
						}
					} else {
						if (sfgo.getNodeType().equals("schel")) {
							goFlowID = sfgo.getId().getFlowID();
							goLogNo = sfgo.getId().getLogNo();
							scheduleRef = scheduleRef + "&nodeType=schel" + "&scheduleType=schel";
							break;
						}
					}
				}
			}
			if (goLogNo > 0) { // 正确找到跳转的位置後
				scheduleRef = scheduleRef + "&swfLogFlowID=" + goFlowID + "&swfLogLogNo=" + goLogNo;
				HttpServletResponse httpServletResponse = getResponse();
				httpServletResponse.sendRedirect(scheduleRef);
				return NONE;
			}
		}
		httpServletRequest.setAttribute("prpLregist", registDto.getPrpLregist());
		// 取得承保的險別信息
		String policyNo = (String) httpServletRequest.getParameter("prpLregistPolicyNo");
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(" 1=1 and policyNo='" + policyNo + "'");
		List<PrpCitemKind> collections = prpCitemKindService.findPrpCitemKind(queryRule);
		StringBuffer strRiskName = new StringBuffer();
		for (int i = 0; i < collections.size(); i++) {
			PrpCitemKind prpCitemKindDto = collections.get(i);
			// 判斷是國廠還是進口的
			if ("F".equals(prpCitemKindDto.getKindCode())) {
				if ("4".equals(prpCitemKindDto.getModeCode()) || "6".equals(prpCitemKindDto.getModeCode())) {
					strRiskName.append(prpCitemKindDto.getKindName());
					strRiskName.append(getText("prompt.regist.madeInMotherland"));//(國廠)
				} else if ("5".equals(prpCitemKindDto.getModeCode()) || "7".equals(prpCitemKindDto.getModeCode())) {
					strRiskName.append(prpCitemKindDto.getKindName());
					strRiskName.append(getText("prompt.regist.import"));//(進口)
				}
			} else {
				strRiskName.append(prpCitemKindDto.getKindName());
			}
			strRiskName.append(',');
		}
		String riskNames = strRiskName.toString();
		riskNames = riskNames.substring(0, riskNames.length() - 1);
		if (httpServletRequest.getParameter("buttonSaveType").trim().equals("4")) {
			if ("PERFECT".equals(editType)) {
				this.addActionMessage(getText("prompt.registEdit.submit"));
			} else {
				this.addActionMessage(getText("prompt.regist.submit"));
			}
		} else {
			this.addActionMessage(getText("prompt.regist.save"));
		}
		this.addActionMessage(getText("db.prpLregist.registNo"));
		this.addActionMessage(registNo);
		// 报案後直接调度
		httpServletRequest.setAttribute("policyNo", registPolicyNo);
		httpServletRequest.setAttribute("handleDept", comCodePolicy);
		if (!"1".equals(httpServletRequest.getParameter("callCenterFlag"))) {
			if (workFlowDto.getSwfFlowMain() != null) {
				String swfLogFlowID = workFlowDto.getSwfFlowMain().getFlowID();
				httpServletRequest.setAttribute("swfLogFlowID", swfLogFlowID);
			}
		}
		// 默认不需要自动跳转
		forward = "success";
		return forward;
	}

	/***
	 * 旧工作流获取工作流处理讯息的方法
	 * @param registDto 备案大对象
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(RegistDto registDto, String strRiskType, String typeFlag, String registPolicyNo, String mainPolicyNo, String qsFlag) throws Exception {
		HttpServletRequest request = super.getRequest();
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		String createWorkFlowFlag = (String) request.getAttribute("newWorkFlow");
		int nextNodeNo = 0;
		// 工作流的viewHelper整理界面输入
		SwfLog swfLogDtoDealNode = new SwfLog();
		if (createWorkFlowFlag.equals("1")) {
			swfLogDtoDealNode.setCreateFlow(true);
		}
		// 判断是否受理的状态,如果不受理，设置工作流的参数为工作流程结束状态。
		if (registDto.getPrpLregist().getAcceptFlag().equals("N")) {
			swfLogDtoDealNode.setEndFlag("1");
		}
		PrpLregist prpLregist = registDto.getPrpLregist();
		String registNo = prpLregist.getRegistNo();
		// 设置报案传入工作流的各个状态
		swfLogDtoDealNode.setNodeType("regis");
		swfLogDtoDealNode.setNodeStatus(registDto.getPrpLclaimStatus().getStatus());
		swfLogDtoDealNode.setBusinessNo(registNo);
		swfLogDtoDealNode.setNextBusinessNo(registNo);
		swfLogDtoDealNode.setKeyIn(registNo);
		swfLogDtoDealNode.setKeyOut(registNo);
		swfLogDtoDealNode.setRiskCode(prpLregist.getRiskCode());
		swfLogDtoDealNode.setComCode(user.getComCode());
		swfLogDtoDealNode.setPolicyNo(registPolicyNo);
		// 待处理的查询条件，增加报案号，被保险人，车牌号(工作流需要添加)
		swfLogDtoDealNode.setRegistNo(registNo);
		swfLogDtoDealNode.setInsuredName(prpLregist.getInsuredName());
		swfLogDtoDealNode.setLossItemName(prpLregist.getLicenseNo());
		swfLogDtoDealNode.setFlowInTime(prpLregist.getFlowInTime().toString());
		// 因为人到人的原因/指定节点的问题，目前只有车险这样处理的。
		// 车险代码调整，从原来的就一个车险0501改为05××都为车险
		if ("D".equals(strRiskType) && swfLogDtoDealNode.getNodeStatus().equals("4")) {
			String[] strNodeNo = request.getParameterValues("nextNodeNoList");
			List<SwfLog> nextNodeList = new ArrayList<SwfLog>();
			if (strNodeNo != null) {
				for (int i = 0; i < strNodeNo.length; i++) {
					SwfLog swfLogNextNode = new SwfLog();
					nextNodeNo = Integer.parseInt(strNodeNo[i]);
					swfLogNextNode.setNodeNo(nextNodeNo);
					nextNodeList.add(swfLogNextNode);
				}
			}
			// 以上是从模板中读取的必须走的模板信息(注意，上面是节点号码，以下是客户自己选择的信息
			// 因为item需要多写内容的
			String strScheduleTypeCheck = request.getParameter("nextScheduleTypeCheck");
			if (strScheduleTypeCheck.equals("1")) { // 加入查勘调度
				SwfLog swfLogNextNode = new SwfLog();
				swfLogNextNode.setNodeNo(0);
				swfLogNextNode.setNodeType("sched");
				swfLogNextNode.setScheduleID(1);
				swfLogNextNode.setLossItemName(prpLregist.getScheduleItemNote());
				// 设置itemcode的值的大小。
				swfLogNextNode.setLossItemCode(prpLregist.getLossItemCode());
				swfLogNextNode.setTypeFlag(typeFlag);
				nextNodeList.add(swfLogNextNode);
			}
			// 强三 一个报案产生2个立案
			// 取得强制保险的险种代码
			String compelRiskCode = BusinessRuleUtil.getOuterCode(request, "RISKCODE_DAZ");
			if ("Y".equals(qsFlag) && "2".equals(prpLregist.getRegistType())) {
				SwfLog swfLogNextNode = new SwfLog();
				swfLogNextNode.setNodeNo(0);
				swfLogNextNode.setNodeType("claim");
				swfLogNextNode.setPolicyNo(mainPolicyNo); // 强三保单号
				swfLogNextNode.setRiskCode(compelRiskCode); // 强三 险种
				swfLogNextNode.setLossItemCode(prpLregist.getLossItemCode());
				nextNodeList.add(swfLogNextNode);
			}
			if (nextNodeList.size() > 0) {
				swfLogDtoDealNode.setNextNodeListType("1");// 如果得1，就是需要指定下一个节点的序列，如果不是，就是从模板上寻找下面的节点
				swfLogDtoDealNode.setSwfLogList(nextNodeList);
			}
		}
		return workFlowViewHelper.viewToDto(user, swfLogDtoDealNode);
	}

	// 增加出错後放号回归函数
	public boolean putNoback(String registNo,Map<String,Object> infoMap) throws Exception {
		String tableName = "prplregist";
		if (billService.putNo(tableName, registNo,infoMap)) {
		} else {
		}
		return true;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
