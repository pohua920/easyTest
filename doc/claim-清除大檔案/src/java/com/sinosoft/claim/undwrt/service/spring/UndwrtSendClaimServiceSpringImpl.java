package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.bl.facade.BLMailSenderFacade;
import com.sinosoft.claim.bl.facade.BLPrpDuserFacade;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.service.facade.PrpLagentService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.EndcaseService;
import com.sinosoft.claim.endcase.service.facade.RecaseService;
import com.sinosoft.claim.endcase.vo.EndcaseDto;
import com.sinosoft.claim.endcase.vo.ReCaseDto;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.reins.vo.ReinsCaseStatus;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLcaseNo;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.SwfConfigService;
import com.sinosoft.claim.schema.service.facade.SwfFlowMainService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfNotionService;
import com.sinosoft.claim.undwrt.service.facade.UndwrtSendClaimService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.undwrt.dto.custom.SubmitTaskDto;

public class UndwrtSendClaimServiceSpringImpl extends GenericDaoHibernate implements UndwrtSendClaimService {

	private PrpDuserService prpDuserService;
	private PrpLcompensateService prpLcompensateService;
	private PrpLprepayService prpLprepayService;
	private UtiCodeTransferService utiCodeTransferService;
	private PrpLclaimService prpLclaimService;
	private PolicyService policyService;
	private PrpDriskConfigService prpDriskConfigService;
	private PrpLregistService prpLregistService;
	private PrpLagentService prpLagentService;
	private RecaseService recaseService;
	private BillService billService;
	private ClaimService claimService;
	private PrpLrecaseService prpLrecaseService;
	private ReinsServiceManager reinsServiceManager;
	private EndcaseService endcaseService;
	private WorkFlowService workFlowService;
	private WorkFlowViewHelper workFlowViewHelper;
	private SwfFlowMainService swfFlowMainService;
	private SwfLogService swfLogService;
	private SwfNotionService swfNotionService;
	private CodeService codeService;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private SwfConfigService swfConfigService;
	private BusinessViewHelper businessViewHelper;

	public int sendClaimData(SubmitTaskDto submitTaskDto) throws Exception {
		int flag = 0;
		String ADDVERIC = "addInformationOnVeric"; // 双核流程流转中对理赔中的核赔节点的内容变更
		String BACKVERIC = "backVericToCompp"; // 核赔节点的退回（退回到计算书）
		String PASSVERIC = "passVeric"; // 核赔节点的通过（手工结案）
		String interMethod;
		if (submitTaskDto != null) {
			interMethod = submitTaskDto.getInterMethod();
			// 判断函数类型的调用
			if (interMethod.equals(ADDVERIC)) {
				// System.out.println("增加信息开始");
				flag = addInformationOnVeric(submitTaskDto);
			} else if (interMethod.equals(BACKVERIC)) {
				// System.out.println("退回信息开始");
				flag = backVericToCompp(submitTaskDto);
			} else if (interMethod.equals(PASSVERIC)) {
				// System.out.println("通过信息开始");
				flag = passVeric(submitTaskDto);
			} else {
				throw new UserException(-1, 1, "", "不明確的業務類型數據！請與系統管理員聯系！");
			}
			super.getSession().flush();
		}
		return flag;
	}

	/**
	 * 双核流程流转中对理赔中的核赔节点的内容变更
	 * @param LflowID String 理赔工作流号码
	 * @param LlogNo int 理赔工作流节点号码
	 * @param businessNo String 业务号码
	 * @param notionInfo String 审批意见 人员名 时间时间
	 * @param handlerCode String 操作员
	 * @throws Exception
	 * @return boolean
	 */

	public int addInformationOnVeric(SubmitTaskDto submitTaskDto) throws Exception {
		// 取赔款计算书号
		// UIWorkFlowAction uiWorkFlowAction = new UIWorkFlowAction();
		SwfLog swfLogDto = new SwfLog();
		swfLogDto = this.checkFlowNode(submitTaskDto.getFlowID(), submitTaskDto.getLogNo(), submitTaskDto.getBusinessNo());

		int checkFlag = swfLogDto.getId().getLogNo();
		if (checkFlag > 0) {
			// 如果成功的话，存在工作流，那么就需要提交工作流，如果没有就算了
			WorkFlowDto workFlowDto = new WorkFlowDto();
			swfLogDto.setNodeStatus("2");
			swfLogDto.setHandleTime(DateTime.current().toString(DateTime.YEAR_TO_SECOND));
			workFlowDto.setUpdate(true);
			workFlowDto.setUpdateSwfLog(swfLogDto);
			this.addNotionToWorkFlowDto(workFlowDto, submitTaskDto.getFlowID(), submitTaskDto.getLogNo(), submitTaskDto.getNotionInfo());
			this.getWorkFlowService().deal(workFlowDto);
//			MDC.put("workFlowDto", workFlowDto);
		}
		return checkFlag;
	}

	/**
	 * 检查增加内容是不是合法
	 * @param flowID String
	 * @param logNo int
	 * @param businessNo String
	 * @param swfLogDto SwfLogDto
	 * @throws Exception
	 * @return int
	 */
	private SwfLog checkFlowNode(String flowID, int logNo, String businessNo) throws Exception {
		// 检查工作流是否合法。。
		SwfLog swfLogTemp = this.getSwfLogService().findSwfLog(flowID, logNo);

		SwfLog swfLogDto = new SwfLog();
		// 没查询到工作流，有错误
		if (swfLogTemp == null) {
			swfLogDto.getId().setLogNo(-1);
			return swfLogDto;
		}
		// 业务号不是这个工作流上的业务号码，直接返回false
		if (!swfLogTemp.getBusinessNo().equals(businessNo)) {
			swfLogDto.getId().setLogNo(-2);
			//避免由於数据错误，造成理赔工作流数据不提交
			if (swfLogDto.getId().getLogNo() < 0) {
				throw new Exception("核賠節點任務工作流數據業務號錯誤，應該為計算書號！");
			}
			return swfLogDto;
		}
		// 已经回退过了
		if (swfLogTemp.getNodeStatus().equals("5")) {
			swfLogDto.getId().setLogNo(-3);
			//("已经回退过了的案件");
			return swfLogDto;
		}

		// 已经提交过了，直接返回ture
		if (swfLogTemp.getNodeStatus().equals("4")) {
			swfLogDto.getId().setLogNo(0);
			//("已经提交过了的案件");
			return swfLogDto;
		}

		swfLogDto = swfLogTemp;
		//避免由於数据错误，造成理赔工作流数据不提交
		if (swfLogDto.getId().getLogNo() > 0) {

		} else {
			throw new Exception("核賠節點任務工作流數據錯誤，請聯系管理員處理！");
		}
		// 没有问题的
		return swfLogDto;
	}

	/**
	 * 追加披办信息
	 * @param workFlowDto WorkFlowDto
	 * @param flowID String
	 * @param logNo int
	 * @param notion String
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	private WorkFlowDto addNotionToWorkFlowDto(WorkFlowDto workFlowDto, String flowID, int logNo, String notion) throws Exception {
		int maxLineNo = this.getSwfNotionService().getMaxLineNo(flowID, logNo);
		SwfNotion swfNotionDto = new SwfNotion();
		swfNotionDto.getId().setFlowID(flowID);
		swfNotionDto.getId().setLogNo(logNo);
		swfNotionDto.getId().setLineNo(maxLineNo);
		swfNotionDto.setHandleText(notion);
		List<SwfNotion> notionList = new ArrayList<SwfNotion>();
		notionList.add(swfNotionDto);
		workFlowDto.setSwfNotionList(notionList);
		return workFlowDto;
	}

	/**
	 * 核赔节点的退回
	 * @param LflowID String 理赔工作流号码
	 * @param LlogNo int 理赔工作流节点号码
	 * @param businessNo String 业务号码
	 * @param notionInfo String 审批意见 人员名 时间时间
	 * @param handlerCode String 操作员
	 * @throws Exception
	 * @return boolean
	 */
	public int backVericToCompp(SubmitTaskDto submitTaskDto) throws Exception {

		// 取赔款计算书号
		String compensateNo = submitTaskDto.getBusinessNo();
		String swfLogFlowID = submitTaskDto.getFlowID(); // 工作流号码
		int swfLogLogNo = submitTaskDto.getLogNo(); // 工作流logno
		String keyString = ""; // 工作流keyIn
		UserDto user = new UserDto(); // 因为不是用户自己操作的，所以目前暂时认为就是计算机做的
		user.setUserCode(submitTaskDto.getOperatorCode());
		//核赔先前处理人名称存到具体人名+审核状态
		BLPrpDuserFacade blPrpDuserFacade = new BLPrpDuserFacade();
		String strUserName = (blPrpDuserFacade.findByPrimaryKey(submitTaskDto.getOperatorCode())).getUserName();
		user.setUserName(strUserName);

		SwfLog swfLogDto = new SwfLog();
		// 保存赔款计算书信息,如果双核可以直接写这边的业务数据库，就不需要这一步了。
		swfLogDto = this.checkFlowNode(swfLogFlowID, swfLogLogNo, submitTaskDto.getBusinessNo());
		int checkFlag = swfLogDto.getId().getLogNo();
		if (checkFlag > 0) {

			keyString = swfLogDto.getKeyIn();// 获得立案号码
			user.setComCode(swfLogDto.getHandleDept());
			user.setComName(swfLogDto.getDeptName());
			// 目前在接口中，如果双核没有写我们的业务库，那么就用这个保存，如果已经写了我们的业务库，那么只要保存工作流数据就可以了
			// 如果成功的话，存在工作流，那么就需要提交工作流，如果没有就算了
			WorkFlowDto workFlowDto = getWorkFlowDto(user, swfLogFlowID, swfLogLogNo, "5", compensateNo, keyString, keyString, compensateNo, false);
			if (workFlowDto == null) {
				return -5;
			}
			// 追加意见
			// 关联保单时，存入swfnotion表的logno不能一一对应，修改为logno取最新的-1
			int logNo = this.getSwfLogService().getMaxLogNo(swfLogFlowID);// 得到最大的logNo值
			this.addNotionToWorkFlowDto(workFlowDto, swfLogFlowID, logNo - 1, submitTaskDto.getNotionInfo());
			this.getWorkFlowService().deal(workFlowDto);//工作流部分在Action层处理调用
//			MDC.put("workFlowDto", workFlowDto);
		}
		//CLM0277 可能要把退回撤回撤銷 3.14/ 3.17 寫這裡
		return checkFlag;
	}

	/**
	 * 整理dto
	 * @param user UserDto
	 * @param flowID String
	 * @param logNo int
	 * @param nodeStatus String
	 * @param nextBusinessNo String
	 * @param keyIn String
	 * @param keyOut String
	 * @param wclose boolean
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	private WorkFlowDto getWorkFlowDto(UserDto user, String flowID, int logNo, String nodeStatus, String businessNo, String nextBusinessNo, String keyIn, String keyOut, boolean wclose) throws Exception {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		SwfLog currSwfLog = this.getWorkFlowService().findByPrimaryKey(flowID, logNo);
		if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(currSwfLog.getActorId()) != null) {
			// 新工作流引擎处理入口
			workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowDto(null, true, false, nodeStatus, businessNo, businessNo, keyOut, keyIn, currSwfLog);
			workFlowDto.setAutoClose(wclose);
			workFlowDto.getJbpmDto().putParamsMap("autoEndCase", wclose);
			Map<String, Object> paramMap = workFlowDto.getParamMap();
			paramMap.put("handlerCode", user.getUserCode());
			paramMap.put("handlerName", user.getUserName());
			paramMap.put("handleTime", new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		} else {
		    workFlowDto = this.businessViewHelper.getWorkFlowDto(null, true, true, nodeStatus, businessNo, businessNo, keyOut, keyIn, currSwfLog);
            workFlowDto.getFlowParamMap().put("claimNo", nextBusinessNo);
            workFlowDto.getFlowParamMap().put("compensateNo", businessNo);
            workFlowDto.setAutoClose(wclose);//是否自動結案
            workFlowDto.setClose(wclose);
//          workFlowDto.getFlowParamMap().put("autoEndCase", wclose);
            Map<String, Object> paramMap = workFlowDto.getParamMap();
            paramMap.put("handlerCode", user.getUserCode());
            paramMap.put("handlerName", user.getUserName());
            paramMap.put("handleTime", new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
//			SwfLog swfLogDtoDealNode = new SwfLog();
//			swfLogDtoDealNode.getId().setFlowID(flowID);
//			swfLogDtoDealNode.getId().setLogNo(logNo);
//			swfLogDtoDealNode.setNodeStatus(nodeStatus);
//			swfLogDtoDealNode.setBusinessNo(businessNo);// 计算书号码/赔付号码等
//			swfLogDtoDealNode.setNextBusinessNo(nextBusinessNo);
//			swfLogDtoDealNode.setKeyIn(keyIn);
//			swfLogDtoDealNode.setKeyOut(keyOut);
//			if (nodeStatus.equals("5")) { // 回退
//				// 查询工作流状态信息,整理输入，用於初始界面显示
//				workFlowDto = this.getWorkFlowViewHelper().getBackFlowInfo(user, flowID, logNo);
//			} else {
//				workFlowDto = this.getWorkFlowViewHelper().viewToDto(user, swfLogDtoDealNode);
//			}
//			if (wclose) {
//				// 关闭操作
//				SwfFlowMain swfFlowMainDto = this.getSwfFlowMainService().findSwfFlowMain(flowID);
//				// 由於强三的问题，需要考虑如果商业没有做完的情况下，是不能结束案件的
//				// 1。查找是否只有一个活动的理算，如果理算超过1个，则不能关闭流程
//				String conditonss = "flowId='" + flowID + "' and nodeType='compe' and nodestatus<4";
//				List<SwfLog> compeList = this.getSwfLogService().findByConditions(conditonss);
//				// 假设发现还没有结完案子的流程，则不将流程结束，
//				// 只是关闭掉目前立案的相关的那个理算，直接返回现有的workFlowDto.
//				if (compeList != null && compeList.size() > 1) {
//					for (int i = 0; i < compeList.size(); i++) {
//						SwfLog swfLogDtoCompe = compeList.get(i);
//						if (swfLogDtoCompe.getKeyIn().equals(keyIn)) {
//							swfLogDtoCompe.setNodeStatus("4");// 关闭理算
//							workFlowDto.setUpdateSwfLog2(swfLogDtoCompe);
//							break;
//						}
//					}
//				} else {
//					if (swfFlowMainDto != null) {
//						swfFlowMainDto.setCloseDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY));
//						swfFlowMainDto.setFlowStatus("0");
//					}
//					// 结束整个案子
//					workFlowDto.setCloseSwfFlowMain(swfFlowMainDto);
//					workFlowDto.setClose(true);
//				}
//				// 设置submit中的swflog为都提交
//				// 出现问题处
//				if (workFlowDto.getSubmit()) {
//					if (workFlowDto.getSubmitSwfLogList() != null) {
//						List<SwfLog> nodeList = workFlowDto.getSubmitSwfLogList();
//						List<SwfLog> nodeLastList = new ArrayList<SwfLog>();
//						// 设置的提交节点都自动结束的
//						for (int i = 0; i < nodeList.size(); i++) {
//							SwfLog swfLogDto = nodeList.get(i);
//							swfLogDto.setHandlerName("自動結案");
//							swfLogDto.setKeyOut(prpLclaimService.findPrpLclaim(swfLogDto.getBusinessNo()).getCaseNo());
//							swfLogDto.setNodeStatus("4");
//							swfLogDto.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
//							nodeLastList.add(swfLogDto);
//						}
//						workFlowDto.setSubmitSwfLogList(nodeLastList);
//					}
//				}
//			}
		}
		if (!this.getWorkFlowViewHelper().checkDealDto(workFlowDto)) {
			throw new Exception("請聯系管理員，確認工作流信息是否有誤");
		}
		return workFlowDto;
	}

	/**
	 * 核赔节点的通过（手工结案）
	 * @param LflowID String 理赔工作流号码
	 * @param LlogNo int 理赔工作流节点号码
	 * @param businessNo String 业务号码
	 * @param notionInfo String 审批意见 人员名 时间时间
	 * @param handlerCode String 操作员
	 * @throws Exception
	 * @return boolean
	 */

	public int passVeric(SubmitTaskDto submitTaskDto) throws Exception {
		// 取赔款计算书号
		String policyNo = "";
		String riskCode = "";
//		String businessNo = submitTaskDto.getBusinessNo();
		String keyString = ""; // 工作流keyIn
		UserDto user = new UserDto(); // 因为不是用户自己操作的，所以目前暂时认为就是计算机做的
		user.setUserCode(submitTaskDto.getOperatorCode());
		//核赔先前处理人名称存到具体人名+审核状态
		BLPrpDuserFacade blPrpDuserFacade = new BLPrpDuserFacade();
		String strUserName = (blPrpDuserFacade.findByPrimaryKey(submitTaskDto.getOperatorCode())).getUserName();
		user.setUserName(strUserName + "-通過");

		// 保存赔款计算书信息,如果双核可以直接写这边的业务数据库，就不需要这一步了。
		// ???判断是不是自动结案(从 appconfig+计算书的最终标志)，如果是做passVericAndCloseFlow();
		String autoEndCaseFlag = AppConfig.get("sysconst.AutoEndCase");
		// 是否需要自动结案，是否是案终赔付,'3'为实赔
		String caseType = "";
		PrpLcompensate prpLcompensate = null;
		PrpLprepay prpLprepay = null;
		if (submitTaskDto.getBusinessNo().substring(0, 1).equals("3") || submitTaskDto.getBusinessNo().substring(0, 1).equals("C")|| submitTaskDto.getBusinessNo().substring(0, 1).equals("D")) {
			prpLcompensate = prpLcompensateService.findPrpLcompensate(submitTaskDto.getBusinessNo());
			policyNo = prpLcompensate.getPolicyNo();
			riskCode = prpLcompensate.getRiskCode();

		} else if (submitTaskDto.getBusinessNo().substring(0, 1).equals("Y")) {
			prpLprepay = prpLprepayService.findPrpLprepay(submitTaskDto.getBusinessNo());
			policyNo = prpLprepay.getPolicyNo();
			riskCode = prpLprepay.getRiskCode();
			caseType = prpLprepay.getCaseType();
		}
		// 自动结案，只有当最後一张计算书，並且设置的了自动结案的功能，才能用这个程序。
		if ((submitTaskDto.getBusinessNo().substring(0, 1).equals("3") || submitTaskDto.getBusinessNo().substring(0, 1).equals("C") || submitTaskDto.getBusinessNo().substring(0, 1).equals("D")) && "1".equals(autoEndCaseFlag)
				&& ("1".equals(prpLcompensate.getFinallyFlag()) || "2".equals(prpLcompensate.getFinallyFlag()))) {
			return passVericAndCloseFlow(submitTaskDto);
		}
		SwfLog swfLogDto = this.checkFlowNode(submitTaskDto.getFlowID(), submitTaskDto.getLogNo(), submitTaskDto.getBusinessNo());
		int checkFlag = swfLogDto.getId().getLogNo();
		if (checkFlag > 0) {
			keyString = swfLogDto.getKeyIn();
			user.setComCode(swfLogDto.getHandleDept());
			user.setComName(swfLogDto.getDeptName());
			// 目前在接口中，如果双核没有写我们的业务库，那么就用这个保存，如果已经写了我们的业务库，那么只要保存工作流数据就可以了
			// 如果成功的话，存在工作流，那么就需要提交工作流，如果没有就算了
//			String swfLogFlowID = swfLogDto.getId().getFlowID();
//			SwfLog swfLogDtoTemp = this.getWorkFlowService().findNodeByPrimaryKey(swfLogFlowID, 1);
			if (!submitTaskDto.getBusinessNo().substring(0, 1).equals("Y")) {
				PrpCmain prpCmain = policyService.findPrpCmainDtoByPrimaryKey(policyNo);
				String comCode = prpCmain.getComCode();
//				String comCodeSub = comCode.substring(0, 2);
				// 无责垫付案件核赔通过之後去prplagent表中置一个标志位
				comCode = comCode.substring(0, 2);
				PrpDriskConfig prpDriskConfig = prpDriskConfigService.findByPrimaryKey(comCode, riskCode, "advance_case");
				if (prpDriskConfig != null && "1".equals(prpDriskConfig.getConfigValue())) {
					PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(prpLcompensate.getClaimNo());
					PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
					if ("1".equals(prpLregist.getAdvanceType()) || "2".equals(prpLregist.getAdvanceType())) {
						// 核赔通过置标志位
						String conditions = "";
						if ("1".equals(prpLregist.getAdvanceType()))// 全责垫付案件
						{
							conditions = " fullreportNo = '" + prpLregist.getRegistNo() + "' and claimType = '1'";
						} else if ("2".equals(prpLregist.getAdvanceType()))// 无责垫付案件
						{
							conditions = " nullreportNo = '" + prpLregist.getRegistNo() + "' and claimType = '2'";
						}
						prpLagentService.updateUndwrt(conditions);
					}
				}
			}
			WorkFlowDto workFlowDto = getWorkFlowDto(user, submitTaskDto.getFlowID(), submitTaskDto.getLogNo(), "4", keyString, keyString, keyString, submitTaskDto.getBusinessNo(), false);
			if (workFlowDto == null) {
				throw new Exception("簡易賠案提交，工作流數據錯誤，請聯系管理員！");
			}
			// 追加意见
			this.addNotionToWorkFlowDto(workFlowDto, submitTaskDto.getFlowID(), submitTaskDto.getLogNo(), submitTaskDto.getNotionInfo());
			if (caseType != null && !caseType.equals("") && caseType.equals("7")) {// 支付不产生结案
				swfLogDto.setNodeStatus("4"); //
				swfLogDto.setHandlerCode(submitTaskDto.getOperatorCode());
				swfLogDto.setHandlerName(user.getUserName());
				swfLogDto.setSubmitTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
				this.getSwfLogService().update(swfLogDto);
			} else {
				this.getWorkFlowService().deal(workFlowDto);
//				MDC.put("workFlowDto", workFlowDto);
			}
		}
		return checkFlag;
	}

	/**
	 * 核赔节点的通过（自动结案）
	 * @param LflowID String 理赔工作流号码
	 * @param LlogNo int 理赔工作流节点号码
	 * @param businessNo String 业务号码
	 * @param notionInfo String 审批意见 人员名 时间时间
	 * @param handlerCode String 操作员
	 * @throws Exception
	 * @return boolean
	 */
	private int passVericAndCloseFlow(SubmitTaskDto submitTaskDto) throws Exception {
		// 取赔款计算书号
		String compensateNo = submitTaskDto.getBusinessNo();
		String swfLogFlowID = submitTaskDto.getFlowID(); // 工作流号码
		int swfLogLogNo = submitTaskDto.getLogNo(); // 工作流logno
		String keyString = ""; // 工作流keyIn
		UserDto user = new UserDto(); // 因为不是用户自己操作的，所以目前暂时认为就是计算机做的
		user.setUserCode(submitTaskDto.getOperatorCode());
		PrpDuser prpDuser = prpDuserService.findPrpDuser(submitTaskDto.getOperatorCode());
		String strUserName = "";
		if (prpDuser != null) {
			strUserName = prpDuser.getUserName();
		}
		user.setUserName(strUserName + "-通過");

		SwfLog swfLogDto = this.checkFlowNode(swfLogFlowID, swfLogLogNo, submitTaskDto.getBusinessNo());
		int checkFlag = swfLogDto.getId().getLogNo();

		if (checkFlag < 0)
			return checkFlag;
		if (checkFlag == 0)
			return checkFlag;

		keyString = swfLogDto.getKeyIn();
		user.setComCode(swfLogDto.getHandleDept());
		user.setComName(swfLogDto.getDeptName());
		WorkFlowDto workFlowDto = getWorkFlowDto(user, swfLogFlowID, swfLogLogNo, "4", keyString, keyString, keyString, compensateNo, true);
		if (workFlowDto == null) {
			return -5;
		}
		// 追加意见
		this.addNotionToWorkFlowDto(workFlowDto, swfLogFlowID, swfLogLogNo, submitTaskDto.getNotionInfo());
		// 以下是 的业务需求:要求结案和归档要在一步完成,以避免只结案不归档带来的问题)
		PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(submitTaskDto.getBusinessNo());
		String claimNo = prpLcompensate.getClaimNo();
		String policyNo = prpLcompensate.getPolicyNo();
		boolean isRecase = recaseService.isRecase(claimNo);
		/** *******************自动结案开始******************** */
		// 结案主表
		String riskCode = BusinessRuleUtil.getRiskCode(swfLogDto.getRegistNo(), "RegistNo");
		EndcaseDto endcaseDto = new EndcaseDto();
		String conditions = "claimNo ='" + claimNo.trim() + "'";
		// 判断是否是重开赔案
		if (isRecase) {
			int maxSerialNo = 0;
			maxSerialNo = recaseService.getMaxSerialNo(claimNo);
			ReCaseDto reCaseDto = recaseService.findByPrimaryKey(claimNo, maxSerialNo);
			PrpLrecase prpLrecase = reCaseDto.getPrpLrecase();
			prpLrecase.setCloseCaseDate(DateTime.current());
			prpLrecase.setCloseCaseUserCode(user.getUserCode());
			endcaseDto.setPrpLrecase(prpLrecase);
		} else {
			// 非重开赔案
			// 生成陪案号
			String tableName = "prplcaseno";
//			String comCode = user.getComCode();
			double sumPaid = 0.0;
			// 取得立案信息
			ClaimDto claimDto = claimService.findByPrimaryKey(claimNo);
			PrpLclaim prpLclaim = claimDto.getPrpLclaim();
			//取立案的出险原因
			Map<String,Object> infoMap = new HashMap<String,Object>();
			infoMap.put("damageCode",prpLclaim.getDamageCode());
			infoMap.put("policyNo",policyNo);
			String caseNo = billService.getNoByPolciyYear(tableName, riskCode,infoMap);
			// 赔案表集合
			List<PrpLcaseNo> prpLperpayList = new ArrayList<PrpLcaseNo>();
			List<PrpLcompensate> arraylist = prpLcompensateService.findByConditions(conditions);
			if (arraylist != null) {
				for (int i = 0; i < arraylist.size(); i++) {
					PrpLcaseNo prpLcaseNo = new PrpLcaseNo();
					PrpLcompensate compe = (PrpLcompensate) arraylist.get(i);
					compe.setCaseNo(caseNo);
					if(!("R"+claimNo+"00").equals(compe.getCompensateNo())
							&& ("1".equals(compe.getUnderWriteFlag()) || "3".equals(compe.getUnderWriteFlag()))){
						sumPaid = sumPaid + compe.getSumDutyPaid();//除去追償登錄計算書的
					}
					prpLcaseNo.getId().setCertiNo(compe.getCompensateNo());
					prpLcaseNo.getId().setCertiType("C");
					prpLcaseNo.getId().setCaseNo(caseNo);
					prpLcaseNo.setFlag("");
					prpLcaseNo.setClaimNo(claimNo.trim());
					prpLperpayList.add(prpLcaseNo);
				}
			}
			endcaseDto.setPrpLcaseNoList(prpLperpayList);
			endcaseDto.setPrpLcompensateList(arraylist);
			// 取得结案报告
			List<PrpLltext> prpLltextList = claimDto.getPrpLltextList();
			List<PrpLltext> prpLltextListNew = new ArrayList<PrpLltext>();
			for (int i = 0; i < prpLltextList.size(); i++) {
				PrpLltext prpLltext = (PrpLltext) prpLltextList.get(i);
				// 只保留“08”为结案报告
				if (prpLltext.getId().getTextType().equals("08")) {
					prpLltextListNew.add(prpLltext);
				}
			}
			endcaseDto.setPrpLltextList(prpLltextListNew);
			prpLclaim.setCaseNo(caseNo);
			prpLclaim.setSumPaid(sumPaid);
			//自动结案时将caseType置为2(赔案)
			prpLclaim.setCaseType("2");
			prpLclaim.setEndCaseDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
			prpLclaim.setEndCaserCode(user.getUserCode());
			endcaseDto.setPrpLclaim(prpLclaim);
			// 保存结案号到swflog表keyout中，以便流程查询中显示
			if (workFlowDto.getUpdateSwfLog2() != null && "endca".equals(workFlowDto.getUpdateSwfLog2().getNodeType())) {
				workFlowDto.getUpdateSwfLog2().setKeyOut(caseNo);
			}
			Iterator<SwfLog> iterator = workFlowDto.getSubmitSwfLogList().iterator();
			while (iterator.hasNext()) {
				SwfLog swfLogDtoEndCa = iterator.next();
				if ("endca".equals(swfLogDtoEndCa.getNodeType())) {
					swfLogDtoEndCa.setKeyOut(caseNo);
					break;
				}
			}
			if (DataUtils.emptyToNull(caseNo)!=null) {
				PrpLclaimStatus prpLclaimStatus = new PrpLclaimStatus();
				prpLclaimStatus.setStatus("4");
				prpLclaimStatus.getId().setBusinessNo(caseNo.trim());
				prpLclaimStatus.setPolicyNo(prpLclaim.getPolicyNo());
				prpLclaimStatus.setRiskCode(riskCode);
				prpLclaimStatus.getId().setNodeType("endca");
				prpLclaimStatus.getId().setSerialNo(0);
				// 取得当前用户信息，写操作员信息到结案中
				prpLclaimStatus.setComCode(user.getComCode());
				prpLclaimStatus.setHandlerCode(user.getUserCode());
				prpLclaimStatus.setInputDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
				prpLclaimStatus.setOperateDate(new DateTime(new Date(), DateTime.YEAR_TO_DAY));
				endcaseDto.setPrpLclaimStatus(prpLclaimStatus);
			}
		} // 到此处都是新结案的情况。
		// 保存结案信息
		try {
//			MDC.put("workFlowDto", workFlowDto);//工作流部分在Action层处理调用
			if (isRecase == true) { // 重开赔案只保存流的东西,回写 prplrecase表
				// 回写 prplrecase表
				prpLrecaseService.update(endcaseDto.getPrpLrecase());
				this.getWorkFlowService().deal(workFlowDto);
				// 送再保
//				String codeName = "";
				List<UtiCodeTransfer> utiCodeTransferList = utiCodeTransferService.findByConditions(" outercode='" + riskCode + "'");
				if (utiCodeTransferList != null && utiCodeTransferList.size() != 0) {
					UtiCodeTransfer utiCodeTransfer = utiCodeTransferList.get(0);
//					codeName = utiCodeTransfer.getRiskType();
				}
				// 国寿财公司调整，由於再保不处理车险信息，车险理赔不需要与再保进行交互
				ReinsCaseStatus reinsCaseStatus = new ReinsCaseStatus();
				reinsCaseStatus.setClaimNo(claimNo);
				reinsCaseStatus.setBusinessType(ReinsCaseStatus.BusinessType.ENDCASE);
				reinsCaseStatus.setOperateComCode(user.getComCode());
				reinsCaseStatus.setOperaterCode(user.getUserCode());
				reinsCaseStatus.setOperateDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));
				reinsServiceManager.getReinsService().changeCaseStatus(reinsCaseStatus);
			} else {
				// 一般案件结案的保存
				endcaseService.save(endcaseDto, workFlowDto);
			}
			//结案提交发邮件通知经办
			BLMailSenderFacade blMailSenderFacade = new BLMailSenderFacade();
			blMailSenderFacade.MailSend("endca", claimNo, user);

		} catch (Exception ex) {
			throw ex;
		}
		/** *******************自动结案结束******************** */
		return checkFlag;
	}

	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLagentService getPrpLagentService() {
		return prpLagentService;
	}

	public void setPrpLagentService(PrpLagentService prpLagentService) {
		this.prpLagentService = prpLagentService;
	}

	public RecaseService getRecaseService() {
		return recaseService;
	}

	public void setRecaseService(RecaseService recaseService) {
		this.recaseService = recaseService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}

	public EndcaseService getEndcaseService() {
		return endcaseService;
	}

	public void setEndcaseService(EndcaseService endcaseService) {
		this.endcaseService = endcaseService;
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

	public SwfFlowMainService getSwfFlowMainService() {
		return swfFlowMainService;
	}

	public void setSwfFlowMainService(SwfFlowMainService swfFlowMainService) {
		this.swfFlowMainService = swfFlowMainService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public SwfNotionService getSwfNotionService() {
		return swfNotionService;
	}

	public void setSwfNotionService(SwfNotionService swfNotionService) {
		this.swfNotionService = swfNotionService;
	}

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public SwfConfigService getSwfConfigService() {
		return swfConfigService;
	}

	public void setSwfConfigService(SwfConfigService swfConfigService) {
		this.swfConfigService = swfConfigService;
	}

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }
	
}
