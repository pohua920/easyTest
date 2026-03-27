package com.sinosoft.claim.common.web;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.util.DAAClaimViewHelper;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.endcase.service.facade.EndcaseService;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLquickCase;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.PrplregistrpolicyId;
import com.sinosoft.claim.schema.model.SwfFlowMain;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLquickCaseService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.util.BusinessRuleUtil;
import com.sinosoft.claim.workflow.service.facade.JbpmBusinessService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @ClassName ClaimCancelAction
 * @Description 车险理赔立案拒赔界面信息保存
 * @author 中科软
 */
public class ClaimCancelAction extends Struts2Action {
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** 车险立案数据收集工具类 */
	private DAAClaimViewHelper daaClaimViewHelper;
	/** 结案处理接口 */
	private EndcaseService endcaseService;
	/** 立案处理接口 */
	private ClaimService claimService;
	/** 理算处理接口 */
	private PrpLcompensateService prpLcompensateService;
	/** 预赔处理接口 */
	private PrpLprepayService prpLprepayService;
	/** 关联报案处理接口 */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 立案主表接口 */
	private PrpLclaimService prpLclaimService;
	/** 快速赔案处理接口 */
	private PrpLquickCaseService prpLquickCaseService;
	/** 重开赔案接口 */
	private PrpLrecaseService prpLrecaseService;
	/** 单号生成接口 */
	private BillService billService;
	/** 险种配置信息接口 */
	private PrpDriskConfigService prpDriskConfigService;
	/** 工作流处理接口 */
	private WorkFlowService workFlowService;
	/** 工作流引擎数据收集类 */
	private WorkFlowViewHelper workFlowViewHelper;
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private JbpmBusinessService jbpmBusinessService;

	public String claimCancel() throws Exception {
		this.clearErrorsAndMessages();
		HttpServletRequest httpServletRequest = super.getRequest();
		HttpServletResponse httpServletResponse = super.getResponse();
		String forward = ""; // 向前流转
		// 用viewHelper整理界面输入
		ClaimDto claimDto = this.daaClaimViewHelper.cancelViewToDto(httpServletRequest);
		// 以下为工作流使用中的
		String editType = httpServletRequest.getParameter("editType");
		String strClaimNo = httpServletRequest.getParameter("prpLclaimClaimNo");
		String prpLcancelclaimPrintFlag = httpServletRequest.getParameter("prpLcancelclaimPrintFlag");
		String swfLogFlowID = httpServletRequest.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = httpServletRequest.getParameter("swfLogLogNo"); // 工作流logno
		// Reason: 注销/拒赔添加退回功能
		String strSubmitType = httpServletRequest.getParameter("submitType"); // 提交类型
		if ("5".equals(strSubmitType.trim())) {
			JbpmDto jbpmDto = null;
			SwfLog swfLog = this.getWorkFlowService().findByPrimaryKey(swfLogFlowID, Integer.parseInt(swfLogLogNo));
			if (WorkFlowDto.isWorkflowswitch() && DataUtils.dbNullToEmpty(swfLog.getActorId()) != null) {
				jbpmDto = new JbpmDto();
				jbpmDto.setActorId(swfLog.getActorId());
				jbpmDto.setBusinessId(swfLog.getBusinessId());
				jbpmDto.setProcessId(swfLog.getProcessId());
//				this.getJbpmBusinessService().processTask(jbpmDto);
			}
			this.getWorkFlowService().cancelBack(swfLogFlowID, Integer.parseInt(swfLogLogNo), jbpmDto);
			this.addActionMessage(this.getText("prompt.claimCancel.cancelback"));
			return SUCCESS;
		}
		/*
		 * 立案注销提交时如果存在已核赔通过的实赔或预赔，申请的注销拒赔就作废，避免立案注销已实付的问题 begin
		 */
		if (editType.equals("EDIT")) {
			long count = this.prpLcompensateService.getCount(" underwriteflag in ('1','3') and claimno='" + strClaimNo + "'");
			if (count > 0) {
				throw new UserException(1, 3, "註銷/拒賠", "存在已核賠通過的實賠,不准許注銷拒賠！");
			} else {
				int preCount = this.prpLprepayService.getCount(" underwriteflag in ('1','3') and claimno='" + strClaimNo + "'");
				if (preCount > 0) {
					throw new UserException(1, 3, "註銷/拒賠", "存在已核賠通過的實賠,不准許注銷拒賠！");
				}
			}
		}
		WorkFlowDto workFlowDto = null;
		SwfLog swfLog = this.getWorkFlowService().findByPrimaryKey(swfLogFlowID, Integer.parseInt(swfLogLogNo));
		if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(swfLog.getActorId()) != null) {
			workFlowDto = this.getJbpmWorkFlowDto(claimDto, swfLog);
		} else {
//			workFlowDto = this.getWorkFlowDto(claimDto);
		    workFlowDto = this.getWorkFlowDto(claimDto, swfLog);
		}
		if (workFlowViewHelper.checkDealDto(workFlowDto)) {
			this.claimService.save(claimDto, workFlowDto);
//			this.getJbpmBusinessViewHelper().saveBusiness(this.claimService, "save", workFlowDto,claimDto);
		} else {
			this.claimService.save(claimDto);
		}
		if ("ADD".equals(editType)) {
			this.addActionMessage(this.getText("prompt.claimCancel.cancelsuccess"));
		} else {
			this.addActionMessage(this.getText("prompt.claimCancel.refusalsuccess"));
		}
		if ("1".equals(prpLcancelclaimPrintFlag)) {
			httpServletResponse.sendRedirect("/claim/ClaimPrint.do?printType=Canceltrans&ClaimNo=" + strClaimNo);
			forward = NONE;
		} else {
			forward = "success";
		}
		return forward;
	}
	/***
	 * 新工作流引擎处理注销拒赔
	 * @param claimDto
	 * @param swfLog
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getJbpmWorkFlowDto(ClaimDto claimDto,SwfLog swfLog) throws Exception {
		HttpServletRequest request = super.getRequest();
		String editType = request.getParameter("editType");
		WorkFlowDto workFlowDto = new WorkFlowDto();
		workFlowDto.setNewWorkFlow(true);//启用新工作流引擎处理
		workFlowDto.setSubmit(true);
		workFlowDto.setCurrSwfLog(swfLog);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		JbpmDto jbpmDto = new JbpmDto();
		if ("ADD".equals(editType)) {
			workFlowDto.setClaimCancel(true);
			jbpmDto.setActorId("request_cancel");
			paramMap.put("nextBusinessNo", prpLclaim.getClaimNo());
			paramMap.put("nextKeyIn", prpLclaim.getClaimNo());
			paramMap.put("typeFlag", request.getParameter("caseType"));
		}else{// 直接接收或退回
			workFlowDto.setUpdate(true);
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			paramMap.put("nodeStatus", "4");
			paramMap.put("keyOut", prpLclaim.getClaimNo());
			paramMap.put("typeFlag", request.getParameter("caseType"));
			paramMap.put("handlerCode", user.getUserCode());
			paramMap.put("handlerName", user.getUserName());
			paramMap.put("handleTime",new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
			this.setPrplclaim(claimDto);
			String sqlstr = "registNo='" + prpLclaim.getRegistNo() + "' and claimNo<>'" + prpLclaim.getClaimNo() + "' and  endcasedate is null ";
			List<PrpLclaim> claimList = this.prpLclaimService.findPrpLclaim(QueryRule.getInstance().addSql(sqlstr));
			if (claimList == null || claimList.isEmpty()) {//判断本案是否有其他未注销的立案
				sqlstr = "registNo='" + prpLclaim.getRegistNo() + "' and (claimNo<>'" + prpLclaim.getClaimNo() + "' or claimNo is null ) and  validstatus='1' ";
				List<Prplregistrpolicy> registRPolciyList = prpLregistrpolicyService.findPrplregistrpolicy(QueryRule.getInstance().addSql(sqlstr));
				if (registRPolciyList == null || registRPolciyList.size() == 0) {// 整个案子全都注销了。。。
					paramMap.put("endFlag", "1");
					jbpmDto.putParamsMap("endFlag", true);
				}
			}
			String flowID = swfLog.getId().getFlowID();
			// 查找是否只有一个活动的理算，如果理算超过1个，则不能关闭流程
			String tempStr = "flowId='" + flowID + "' and nodeType='compe' and nodestatus<4";
			List<SwfLog> compeList = this.getWorkFlowService().findNodesByConditions(tempStr);
			int size = compeList.size();
			if(compeList!=null && size > 0){
				if(size > 1){// 假设发现还没有结完案子的流程，则不将流程结束， 只是关闭掉目前立案的相关的那个理算
					for (int i = 0; i < size; i++) {
						SwfLog compeSwfLog = compeList.get(i);
						if (compeSwfLog.getKeyIn().equals(swfLog.getKeyIn())) {
							compeSwfLog.setNodeStatus("4");// 关闭理算
							workFlowDto.setUpdateSwfLog2(compeSwfLog);
							break;
						}
					}
				}else if(size == 1){
					jbpmDto.putParamsMap("endFlag", true);
				}
			}
			// reason:注销/拒赔通过後，将此任务的立案工作流节点变成 4，说明已经提交。
			String strSql = "flowid='" + flowID + "' and nodeType='claim' and nodestatus='2' and keyOut='" + prpLclaim.getClaimNo() + "'";
			List<SwfLog> claimSwfLogList =this.getWorkFlowService().findNodesByConditions(strSql);
			if (claimSwfLogList != null && claimSwfLogList.size() > 0) {
				claimSwfLogList.get(0).setNodeStatus("4");
				workFlowDto.setUpdateSwfLog2(claimSwfLogList.get(0));
			}
		}
		workFlowDto.setJbpmDto(jbpmDto);//工作流引擎参数
		workFlowDto.setParamMap(paramMap);
		return workFlowDto;
	}
	
	/***
	 * 旧工作流处理注销拒赔任务
	 * @param claimDto
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(ClaimDto claimDto,SwfLog swfLog) throws Exception {
//		HttpServletRequest request = super.getRequest();
//		String swfLogFlowID = request.getParameter("swfLogFlowID"); // 工作流号码
//		String swfLogLogNo = request.getParameter("swfLogLogNo"); // 工作流logno
//		String editType = request.getParameter("editType");
//		String nodeType = request.getParameter("nodeType");
//		String businessNo = request.getParameter("businessNo");
//		String caseType = request.getParameter("caseType");
//		UserDto user = (UserDto) request.getSession().getAttribute("user");
//		// 保存立案拒赔注销信息
//		// 工作流处理过程,如果是ADD的类型，则先指定要到注销/核赔的申请，如果是注销核赔的确定，直接完成注销核赔的确认
//		// 1requst对象,2本节点的节点类型,3本节点需要更新的状态,4本节点的业务号码,5以後节点的业务号码,6本节点的业务流入号码,7以後节点的业务流出号码
//		SwfLog swfLogDtoDealNode = new SwfLog();
//		SwfLog swfLogNextNode = new SwfLog();
//		List<SwfLog> nextNodeList = new ArrayList<SwfLog>();
//		// 申请登记
//		if (!"".equals(DataUtils.dbNullToEmpty(swfLogFlowID)) && !"".equals(DataUtils.dbNullToEmpty(swfLogLogNo))) {
//			swfLogDtoDealNode.getId().setFlowID(swfLogFlowID);
//			swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
//		}
//		// reasion:增加简易赔案注销申请的保存，因为简易赔案的节点，本身没有工作流，需要从某个节点来拉出一条边到注销拒赔，
//		// 但是由於立案是两个，所以不容易拉线，而且不容易判断，万一两个都是不同人立案的並提交，则会出错。暂时考虑用这个人所操作的查勘或者定损（没提交的），来进行工作流开始节点的设置。
//		if ("ADD".equals(editType) && "quickCase".equals(nodeType)) {
//			String registNo = request.getParameter("prpLclaimRegistNo");
//			String flowID = this.getWorkFlowService().findFlowIDByRegistNo(registNo);
//			String strSql = "flowid='" + flowID + "' and handlercode='" + user.getUserCode() + "'" + " and nodeType in ('certa','check','propc')" + " and nodestatus<4";
//			List<SwfLog> checkCertaList = this.getWorkFlowService().findNodesByConditions(strSql);
//			if (checkCertaList == null || checkCertaList.size() < 1) {
//				throw new UserException(1, 3, "註銷/拒賠", "存在已核賠通過的實賠,不准許注銷拒賠！");
//			}
//			int logNo = checkCertaList.get(0).getId().getLogNo();
//			swfLogDtoDealNode.getId().setFlowID(flowID);
//			swfLogDtoDealNode.getId().setLogNo(logNo);
//		}
//		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
//		if ("ADD".equals(editType)) {
//			swfLogDtoDealNode.setNodeType(nodeType);
//			swfLogDtoDealNode.setNodeStatus("4");
//			if (nodeType.equals("compe")) {
//				businessNo = prpLclaim.getClaimNo();
//			}
//			swfLogDtoDealNode.setBusinessNo(businessNo);
//			swfLogDtoDealNode.setNextBusinessNo(prpLclaim.getClaimNo());
//			// 设置流转到注销/拒赔的受理节点去
//			swfLogNextNode.setNodeNo(0);
//			swfLogNextNode.setNodeType("cance");
//			swfLogNextNode.setKeyIn(prpLclaim.getClaimNo());
//			swfLogNextNode.setPolicyNo(prpLclaim.getPolicyNo());
//			swfLogNextNode.setRiskCode(prpLclaim.getRiskCode());
//			// 判断是注销还是拒赔，是要放在工作流上的
//			swfLogNextNode.setTypeFlag(caseType);
//			nextNodeList.add(swfLogNextNode);
//			swfLogDtoDealNode.setNextNodeListType("1");// 如果得1，就是需要指定下一个节点的序列，如果不是，就是从模板上寻找下面的节点
//			swfLogDtoDealNode.setSwfLogList(nextNodeList);
//		} else {// 直接接收或退回
//			swfLogDtoDealNode.setNodeType("cance");
//			swfLogDtoDealNode.setNodeStatus("2");
//			swfLogDtoDealNode.setBusinessNo(prpLclaim.getClaimNo());
//			swfLogDtoDealNode.setTypeFlag(caseType);
//			this.setPrplclaim(claimDto);
//			// 需要增加是否需要结束流程的判断如果需要结束则，才结束工作流程。
//			String sqlstr = "registNo='" + prpLclaim.getRegistNo() + "' and claimNo<>'" + prpLclaim.getClaimNo() + "' and  endcasedate is null ";
//			QueryRule queryRule = QueryRule.getInstance();
//			queryRule.addSql(sqlstr);
//			List<PrpLclaim> claimList1 = this.prpLclaimService.findPrpLclaim(queryRule);
//			if (claimList1 == null || claimList1.size() == 0) {
//				// 判断报案中是否还有没立案的数据。。。
//				sqlstr = "registNo='" + prpLclaim.getRegistNo() + "' and (claimNo<>'" + prpLclaim.getClaimNo() + "' or claimNo is null ) and  validstatus='1' ";
//				queryRule = QueryRule.getInstance();
//				queryRule.addSql(sqlstr);
//				List<Prplregistrpolicy> registRPolciyList1 = prpLregistrpolicyService.findPrplregistrpolicy(queryRule);
//				if (registRPolciyList1 == null || registRPolciyList1.size() == 0) {
//					swfLogDtoDealNode.setNodeStatus("4");
//					swfLogDtoDealNode.setEndFlag("1");
//					// 整个案子全都注销了。。。
//				}
//			}
//			// reasion:简易赔案的影响，需要在没有全部注销前，保单相关简易赔案数目-1
//			// 若全部注销，考虑要把简易赔案的状态设置成无效。
//			// 思路：1）原注销/拒赔的保存
//			// 2）删除简易赔案录入的计算书信息
//			// 3)更新简易赔案的状态，比如 quickCasestatus='05',表示是注销/拒赔才导致简易赔案失效的。
//			// 4）若简易赔案都注销了，那么设置工作流主表的
//			PrpLquickCase prpLquickCase = this.prpLquickCaseService.findPrpLquickCase(prpLclaim.getRegistNo());
//			if (prpLquickCase != null && prpLquickCase.getValidStatus().equals("1")) {
//				// 是有效的简易赔案，则需要判断是否是全部注销，如果是全部，则要设置此状态为无效，且是因为注销/拒赔引起的
//				if (swfLogDtoDealNode.getEndFlag().equals("1")) {
//					prpLquickCase.setValidStatus("0");
//					prpLquickCase.setQuickCaseStatus("05");// 注销的
//				}
//				claimDto.setPrpLquickCase(prpLquickCase);
//			}
//		}
//		// 考虑 到後来几乎都是用clamno做keyin的数值的。。
//		swfLogDtoDealNode.setKeyIn(prpLclaim.getClaimNo());
//		swfLogDtoDealNode.setKeyOut(prpLclaim.getClaimNo());
//		WorkFlowDto workFlowDto = workFlowViewHelper.viewToDto(user, swfLogDtoDealNode);
//		// 保存立案信息並查找工作流程
//		if (editType.equals("ADD")) {
//			// reason:注销/拒赔後，当任务没有被全部注销/拒赔时，可以继续操作，不受流程的申请的影响。
//			workFlowDto.setUpdate(false);
//			workFlowDto.setUpdateSwfLog(null);
//		} else {
//			if (workFlowDto.getUpdateSwfLog() != null) {
//				workFlowDto.getUpdateSwfLog().setNodeStatus("4");
//				if (workFlowDto.getUpdateSwfLog().getNodeType().equals("cance")) {
//					// 查找是否只有一个活动的理算，如果理算超过1个，则不能关闭流程
//					String conditonss = "flowId='" + workFlowDto.getUpdateSwfLog().getId().getFlowID() + "' and nodeType='compe' and nodestatus<4";
//					List<SwfLog> compeList = this.getWorkFlowService().findNodesByConditions(conditonss);
//					// 假设发现还没有结完案子的流程，则不将流程结束，
//					// 只是关闭掉目前立案的相关的那个理算，直接返回现有的workFlowDto.
//					if (compeList != null && compeList.size() > 1) {
//						for (int i = 0; i < compeList.size(); i++) {
//							SwfLog swfLogDtoCompe = compeList.get(i);
//							if (swfLogDtoCompe.getKeyIn().equals(workFlowDto.getUpdateSwfLog().getKeyIn())) {
//								swfLogDtoCompe.setNodeStatus("4");// 关闭理算
//								workFlowDto.setUpdateSwfLog2(swfLogDtoCompe);
//								break;
//							}
//						}
//					} else if (compeList != null && compeList.size() == 1) {// 注销时，没有活动的理算信息或者只有一个活动的理算信息
//						String conditonEndce = "flowId='" + workFlowDto.getUpdateSwfLog().getId().getFlowID() + "' and (nodeType='endca' or nodeType='cance') and nodestatus=4";
//						List<SwfLog> endcaList = this.getWorkFlowService().findNodesByConditions(conditonEndce);
//						if (compeList != null && compeList.size() > 0 && endcaList.size() > 0) {// 注销时有结案信息或者已经有注销完成信息，就关闭工作流，转储工作流数据
//							workFlowDto.setClose(true);
//							// 如果是第一个节点，现在还没有工作流主表内容呢，所以不需要查询的。
//							SwfFlowMain swfFlowMainDto = this.getWorkFlowService().findFlowMainByPrimaryKey(workFlowDto.getUpdateSwfLog().getId().getFlowID().trim());
//							swfFlowMainDto.setCloseDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND));// 设置closedate
//							workFlowDto.setCloseSwfFlowMain(swfFlowMainDto); // 解决商业已结案，交强在理算环节申请注销，没有转储的问题。
//						} else if (endcaList.size() < 1) {
//							// reason:解决车险关联报案：理算打回，单证为未处理状态，在理算环节注销ADDBZ或ADDDAA即转储的问题
//							String conditonEndce1 = "flowId='" + workFlowDto.getUpdateSwfLog().getId().getFlowID() + "' and (nodeType='certa' or nodeType='verif' or nodeType='certi' or nodeType='veric' Or nodeType='endca') and nodestatus<4";
//							List<SwfLog> certaList = this.getWorkFlowService().findNodesByConditions(conditonEndce1);
//							int count = this.getWorkFlowService().getCount(conditonEndce1);
//							if (count == 0) {
//								workFlowDto.setClose(true);
//								// 如果是第一个节点，现在还没有工作流主表内容呢，所以不需要查询的。
//								SwfFlowMain swfFlowMainDto = this.getWorkFlowService().findFlowMainByPrimaryKey(workFlowDto.getUpdateSwfLog().getId().getFlowID().trim());
//								swfFlowMainDto.setCloseDate(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND)); // 设置closedate
//								workFlowDto.setCloseSwfFlowMain(swfFlowMainDto);
//							} else {
//								SwfLog swfLogDtoCerta = certaList.get(0);
//								swfLogDtoCerta.setNodeStatus("4");
//								workFlowDto.setUpdateSwfLog2(swfLogDtoCerta);
//							}
//						}
//					}
//
//				}
//				// reason:注销/拒赔通过後，将此任务的立案工作流节点变成 4，说明已经提交。
//				String strSql = "flowid='" + swfLogFlowID + "' and nodeType='claim' and nodestatus='2' and keyOut='" + prpLclaim.getClaimNo() + "'";
//				List<SwfLog> claimSwfLogList = this.getWorkFlowService().findNodesByConditions(strSql);
//				if (claimSwfLogList != null && claimSwfLogList.size() > 0) {
//					claimSwfLogList.get(0).setNodeStatus("4");
//					workFlowDto.setUpdateSwfLog2(claimSwfLogList.get(0));
//				}
//				// reason:需要变更简易赔案的工作流的状态
//				if (claimDto.getPrpLquickCase() != null && workFlowDto.getCloseSwfFlowMain() != null) {
//					workFlowDto.getCloseSwfFlowMain().setClaimTypeFlag("05");// 表示简易赔案注销！！！
//				}
//			}
//		}
//		return workFlowDto;
	    
	    HttpServletRequest request = super.getRequest();
        String editType = request.getParameter("editType");
        WorkFlowDto workFlowDto = new WorkFlowDto();
        workFlowDto.setNewWorkFlow(false);//启用新工作流引擎处理
        workFlowDto.setSubmit(true);
        workFlowDto.setCurrSwfLog(swfLog);
        PrpLclaim prpLclaim = claimDto.getPrpLclaim();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if ("ADD".equals(editType)) {
            workFlowDto.setClaimCancel(true);//產生註銷拒賠節點
            paramMap.put("nextBusinessNo", prpLclaim.getClaimNo());
            paramMap.put("nextKeyIn", prpLclaim.getClaimNo());
            paramMap.put("typeFlag", request.getParameter("caseType"));
        } else {// 直接接收或退回
            workFlowDto.setUpdate(true);
            workFlowDto.setStatus("4");
            UserDto user = (UserDto) request.getSession().getAttribute("user");
            paramMap.put("nodeStatus", "4");
            paramMap.put("keyOut", prpLclaim.getClaimNo());
            paramMap.put("typeFlag", request.getParameter("caseType"));
            paramMap.put("handlerCode", user.getUserCode());
            paramMap.put("handlerName", user.getUserName());
            paramMap.put("handleTime",new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
            this.setPrplclaim(claimDto);
            String sqlstr = "registNo='" + prpLclaim.getRegistNo() + "' and claimNo<>'" + prpLclaim.getClaimNo() + "' and  endcasedate is null ";
            List<PrpLclaim> claimList = this.prpLclaimService.findPrpLclaim(QueryRule.getInstance().addSql(sqlstr));
            if (claimList == null || claimList.isEmpty()) {//判断本案是否有其他未注销的立案
                sqlstr = "registNo='" + prpLclaim.getRegistNo() + "' and (claimNo<>'" + prpLclaim.getClaimNo() + "' or claimNo is null ) and  validstatus='1' ";
                List<Prplregistrpolicy> registRPolciyList = prpLregistrpolicyService.findPrplregistrpolicy(QueryRule.getInstance().addSql(sqlstr));
                boolean endFlag = CommonUtils.isEmpty(registRPolciyList);
                workFlowDto.getFlowParamMap().put("endFlag", endFlag);
                workFlowDto.setClose(endFlag);
            }
            String flowID = swfLog.getId().getFlowID();
            // 查找是否只有一个活动的理算，如果理算超过1个，则不能关闭流程
            String tempStr = "flowId='" + flowID + "' and nodeType='compe' and nodestatus < 4";
            List<SwfLog> compeList = this.getWorkFlowService().findNodesByConditions(tempStr);
            int size = compeList.size();
            if(!CommonUtils.isEmpty(compeList)){//如果已經產生的理算
                boolean endFlag = false;
                for (int i = 0; i < size; i++) {//註銷審核通過，同時關閉本案的理算。
                    SwfLog compeSwfLog = compeList.get(i);
                    if (compeSwfLog.getKeyIn().equals(swfLog.getKeyIn())) {
                        compeSwfLog.setNodeStatus("4");// 关闭理算
                        workFlowDto.setUpdateSwfLog2(compeSwfLog);
                        endFlag = size == 1;// 只有一個理算節點，且恰好是本賠案對應的理算節點，則可結案
                        break;
                    }
                }
                workFlowDto.getFlowParamMap().put("endFlag", endFlag);
                workFlowDto.setClose(size == 1);
            }
            // reason:注销/拒赔通过後，将此任务的立案工作流节点变成 6，说明已经提交。
            String strSql = "flowid='" + flowID + "' and nodeType='claim' and nodestatus='2' and keyOut='" + prpLclaim.getClaimNo() + "'";
            List<SwfLog> claimSwfLogList =this.getWorkFlowService().findNodesByConditions(strSql);
            if (claimSwfLogList != null && claimSwfLogList.size() > 0) {
                claimSwfLogList.get(0).setNodeStatus("6");// 改成6吧，4可能會引起介接的重送
                workFlowDto.setUpdateSwfLog2(claimSwfLogList.get(0));
            }
        }
        workFlowDto.setParamMap(paramMap);
        return workFlowDto;
	}
	/***
	 * 设置立案讯息
	 * @param claimDto
	 * @throws Exception
	 */
	private void setPrplclaim(ClaimDto claimDto) throws Exception{
		HttpServletRequest request = super.getRequest();
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		// 生成陪案号
		String tableName = "prplcaseno";
		String comCode = request.getParameter("prpLclaimComCode");
		String riskCode = BusinessRuleUtil.getRiskCode(prpLclaim.getClaimNo(), "ClaimNo");
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("damageCode",prpLclaim.getDamageCode());
		infoMap.put("policyNo",prpLclaim.getPolicyNo());
		String caseNo = billService.getNoByPolciyYear(tableName, riskCode,infoMap);
		prpLclaim.setCaseNo(caseNo);
		prpLclaim.setEndCaseDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		prpLclaim.setEndCaserCode(user.getUserCode());
		prpLclaim.setCaseNo(caseNo);
		// 最後通过的时候才写这个数据的的
		prpLclaim.setCancelDate(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY));
		String caseType = request.getParameter("caseType");
		prpLclaim.setCaseType(caseType);
		// 注销、拒赔原因
		String cancelReason = request.getParameter("prpLclaimContext");
		prpLclaim.setCancelReason(cancelReason);
		Prplregistrpolicy prplregistrpolicy = this.prpLregistrpolicyService.findPrplregistrpolicy(new PrplregistrpolicyId(prpLclaim.getRegistNo(), prpLclaim.getPolicyNo()));
		if (prplregistrpolicy != null) {
			prplregistrpolicy.setValidStatus("0");
			claimDto.setPrplregistrpolicy(prplregistrpolicy);
		}
	}

	public DAAClaimViewHelper getDaaClaimViewHelper() {
		return daaClaimViewHelper;
	}

	public void setDaaClaimViewHelper(DAAClaimViewHelper daaClaimViewHelper) {
		this.daaClaimViewHelper = daaClaimViewHelper;
	}

	public EndcaseService getEndcaseService() {
		return endcaseService;
	}

	public void setEndcaseService(EndcaseService endcaseService) {
		this.endcaseService = endcaseService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
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

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLquickCaseService getPrpLquickCaseService() {
		return prpLquickCaseService;
	}

	public void setPrpLquickCaseService(PrpLquickCaseService prpLquickCaseService) {
		this.prpLquickCaseService = prpLquickCaseService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
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
	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}
	
	public JbpmBusinessService getJbpmBusinessService() {
		return jbpmBusinessService;
	}
	public void setJbpmBusinessService(JbpmBusinessService jbpmBusinessService) {
		this.jbpmBusinessService = jbpmBusinessService;
	}
}
