package com.sinosoft.claim.claim.web;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.util.DAAClaimViewHelper;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.ProcessTokenException;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.email.service.facade.EmailService;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.reins.util.ReinsTranslateViewHelper;
import com.sinosoft.claim.reins.vo.ReinsClaimSummary;
import com.sinosoft.claim.reins.vo.ReinsLargeCase;
import com.sinosoft.claim.reins.vo.ReinsRepayCalResult;
import com.sinosoft.claim.schema.model.PrpLclaimLoss;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLemailLogService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.sms.util.SmsViewHelper;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.JbpmDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
//mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 立案提交处理action
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
@SuppressWarnings("serial")
public class ClaimEditPostAction extends Struts2Action {
	/** 立案信息表接口service */
	private PrpLclaimService prpLclaimService;
	/** 立案viewHelper */
	private DAAClaimViewHelper daaClaimViewHelper;
	/** 立案service */
	private ClaimService claimService;
	/** 赔案保单关联接口service */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 生成单号的service */
	private BillService billService;
	/** 提示消息 */
	private String message = "";
	/** 再保接口service */
	private ReinsServiceManager reinsServiceManager;
	/** 工作流viewHelper */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 向再保发邮件 */
	private EmailService emailService;
	/** 邮件服务service */
	private PrpLemailLogService prpLemailLogService;

	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	private SmsViewHelper smsViewHelper;

	private UtiCodeTransferService utiCodeTransferService;
	private PrpLregistService prpLregistService;
	private WorkFlowService workFlowService;
	private BusinessViewHelper businessViewHelper;
	
	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	private PolicyService policyService;
	private PrpDriskConfigService prpDriskConfigService;

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	/**
	 * 立案处理
	 * @return 返回成功页面
	 * @throws Exception
	 */
	public String claimEditPost() throws Exception {
		this.clearErrorsAndMessages();
		String forward = ""; // 向前流转
		String claimNo = "";
		/*
		 * 程序思路： ========================================================
		 * [1]保存立案表信息 [2]保存案件状态表信息，strNodeStatus
		 * [3]判断strNodeStatus是不是等於提交，不是的话，直接修改工作流Map中的状态位做Update
		 * [4]如是提交，执行Complate操作。
		 * ========================================================
		 */
		// 如果是新登记，则从取号表中取赔案号码，如果是修改，则保持原来的claimNo不变
		// 取赔案号
		// 0 表示不需要， 1表示需要
		ClaimDto claimDto = null;
		try {
			HttpServletRequest httpServletRequest = getRequest();
			String strLastAccessedTime = String.valueOf(httpServletRequest.getSession().getLastAccessedTime() / 1000);
			String oldLastAccessedTime = (String) httpServletRequest.getSession().getAttribute("oldClaimLastAccessedTime");
			oldLastAccessedTime = "";
			String buttonSaveType = httpServletRequest.getParameter("buttonSaveType");
			String userMessage = "";
			if ("".equals(oldLastAccessedTime.trim())) {
				httpServletRequest.getSession().setAttribute("oldClaimLastAccessedTime", strLastAccessedTime);
				claimNo = httpServletRequest.getParameter("prpLclaimClaimNo"); // 赔案号
				String registNo = httpServletRequest.getParameter("prpLclaimRegistNo");
				String policyNo = httpServletRequest.getParameter("prpLclaimPolicyNo");// 保单号
				String riskCode = httpServletRequest.getParameter("prpLclaimRiskCode"); // 
				String userLastActionNow = "claimAdd?regist=" + registNo; // 防止用户重复提交
				UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
				//車險簡易賠案設置
				UtiCodeTransfer transfer = this.utiCodeTransferService.findUtiCodeTransfer(riskCode);
				String simpleFlag = httpServletRequest.getParameter("prpLclaimSimpleFlag");//簡易賠案標記
				if("D".equals(transfer.getRiskType()) && "1".equals(DataUtils.dbNullToEmpty(simpleFlag))){
//					PrpLregist tempPrpLregist = this.prpLregistService.findPrpLregist(registNo);
//					if("2".equals(tempPrpLregist.getRegistType()) && !"RISKCODE_DAZ".equals(transfer.getConfigCode())){//
//						throw new UserException(1,3,"","關聯備案任意險不得進行簡易賠案處理！");
//					}
					//單獨備案可做，關聯備案強制險可做
					String flowID = httpServletRequest.getParameter("swfLogFlowID");
					//mantis： CLM0058 ，處理人員：CLM0058車臉出險只走簡易流程，並將一般流程隱藏
					String sql = " flowID = '" + flowID + "' and nodeType = 'sched' and nodeStatus IN ('0','6') ";
					List<SwfLog> tempList = this.workFlowService.findByConditions(sql);
					if(CommonUtils.isEmpty(tempList)){//分案未處理，可以進行簡易賠案
						throw new UserException(1,3,"","本案分案正在處理中或已處理完畢，不得進行簡易賠案處理！");
					}
				}
				if (claimNo.length() < 1 || claimNo == null) {
					// 增加控制，防止一个报案生成两个立案号 begin
					String claimNoStr = prpLclaimService.isClaim(registNo, policyNo);
					if (claimNoStr != null && !"".equals(claimNoStr)) {
						throw new Exception("該備案已經立案，不允許再進行立案任務提交！");
					}
					// 不可以重复进行刷新提交
					// 取号过程还需要进一步完善
					String tableName = "prplclaim";
					String prpLclaimDamageCode = httpServletRequest.getParameter("prpLclaimDamageCode");
					Map<String,Object> infoMap = new HashMap<String,Object>();
					infoMap.put("damageCode",prpLclaimDamageCode);
					infoMap.put("policyNo",policyNo);
					infoMap.put("registNo", registNo);
					claimNo = billService.getNoByPolciyYear(tableName, riskCode,infoMap);
				}
				httpServletRequest.setAttribute("claimNo", claimNo);
				// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能-Start
//				claimDto = daaClaimViewHelper.viewToDto(httpServletRequest); 
				String editSpecial = httpServletRequest.getParameter("editSpecial");
				if (editSpecial != null && editSpecial.equals("EDITSPECIAL")){
					claimDto = claimService.findByPrimaryKey(claimNo);
					claimDto.getPrpLclaim().setDamageStartDate(new DateTime(httpServletRequest.getParameter("prpLclaimDamageStartDate").toString()));
					String minute = httpServletRequest.getParameter("prpLclaimDamageStartMinute");
					//mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整 START
					claimDto.getPrpLclaim().setDamageStartHour(httpServletRequest.getParameter("prpLclaimDamageStartHour")+":"+(null!=minute&&!"".equals(minute)?minute:"01")+":00");
//					claimDto.getPrpLclaim().setDamageStartMinute(httpServletRequest.getParameter("prpLclaimDamageStartMinute"));
					claimDto.getPrpLclaim().setDamageAreaCode(httpServletRequest.getParameter("prpLclaimDamageAreaCode"));
					claimDto.getPrpLclaim().setDamageAreaName(httpServletRequest.getParameter("prpLclaimDamageAreaName"));
					claimDto.getPrpLclaim().setDamageAddress(httpServletRequest.getParameter("prpLclaimDamageAddress"));
					claimDto.getPrpLclaim().setReceiptDate(httpServletRequest.getParameter("prpLclaimReceiptDate"));
					//mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整 END
					//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 START
					if(null!= riskCode && (riskCode.equals("F01")|| riskCode.equals("F02"))){
						claimDto.getPrpLclaim().setDamageCode(httpServletRequest.getParameter("prpLclaimDamageCode"));
						claimDto.getPrpLclaim().setDamageName(httpServletRequest.getParameter("prpLclaimDamageName"));
					}
					//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 END
					// 立案修改更新出險時間及出險地區
					claimService.updateSpecialEditCase(claimDto); 
				}else {
					claimDto = daaClaimViewHelper.viewToDto(httpServletRequest); 
					// 用viewHelper整理界面输入
					// 工作流处理过程
					WorkFlowDto workFlowDto = null;
					String actorId = httpServletRequest.getParameter("swfLogActorId");
					if (WorkFlowDto.isWorkflowswitch() && DataUtils.emptyToNull(DataUtils.dbNullToEmpty(actorId)) != null) {
						workFlowDto = this.getJbpmBusinessViewHelper().getJbpmWorkFlowDto(super.getRequest(), true, false, null, claimNo, null, claimNo, null, null);
						JbpmDto jbpmDto = workFlowDto.getJbpmDto();
						if (workFlowDto.getSubmit() && jbpmDto != null && "1".equals(DataUtils.dbNullToEmpty(simpleFlag))) {
							jbpmDto.putParamsMap("simpleFlag", true);// 設置簡易賠案的標記
						}
					} else {
						//workFlowDto = this.getWorkFlowDto(claimNo);
						workFlowDto = this.businessViewHelper.getWorkFlowDto(super.getRequest(), true, false, null, claimNo, null, claimNo, null, null);
						workFlowDto.getFlowParamMap().put("claimNo", claimNo);
					}
					// 保存报案信息
					if (workFlowViewHelper.checkDealDto(workFlowDto)) {
						claimService.save(claimDto, workFlowDto);
						//发送简讯
						if ("4".equals(buttonSaveType)) {
							smsViewHelper.sendSms(claimNo,"claim");
						} 
//				this.getJbpmBusinessViewHelper().saveBusiness(claimService,"save", workFlowDto, claimDto);
//				user.setUserMessage(claimNo);
					} else {
//				user.setUserMessage(claimNo + ";註意:沒有發現與工作流流程相關任何數據！");
						userMessage += ";註意:沒有發現與工作流流程相關任何數據！";
					}
				}
				// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能-End
				// 防止重复刷新
				user.setUserLastAction(userLastActionNow);
				httpServletRequest.setAttribute("prpLclaim", claimDto.getPrpLclaim());
			}
			this.clearErrorsAndMessages();
			if ("4".equals(buttonSaveType.trim())) {
				this.addActionMessage(getText("prompt.claim.submit"));
			} else {
				this.addActionMessage(getText("prompt.claim.save"));
			}
			this.addActionMessage(getText("db.prpLafterward.claimNo"));
			this.addActionMessage(claimNo);
			if (!CommonUtils.isEmpty(userMessage)) {
				this.addActionMessage(userMessage);
			}
			// 再保中的现金赔款、共同理赔处理: 需要给予提示. 2005-9-28
			httpServletRequest.setAttribute("com.sinosoft.flag", findControlFlag(claimDto, httpServletRequest));
			httpServletRequest.setAttribute("com.sinosoft.reinsFlag", findIsReinsControl(claimDto, httpServletRequest));
		} catch (ProcessTokenException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		forward = "success";
		return forward;
	}

	/***
	 * 旧工作流引擎处理立案任务
	 * @param claimNo 立案号码
	 * @return
	 * @throws Exception
	 */
	private WorkFlowDto getWorkFlowDto(String claimNo) throws Exception {
		HttpServletRequest request = super.getRequest();
		String swfLogFlowID = request.getParameter("swfLogFlowID"); // 工作流号码
		String swfLogLogNo = request.getParameter("swfLogLogNo"); // 工作流logno
		String registNo = request.getParameter("prpLclaimRegistNo");
		String buttonSaveType = request.getParameter("buttonSaveType");
		// 1requst对象,2本节点的节点类型,3本节点需要更新的状态,4本节点的业务号码,5以後节点的业务号码,6本节点的业务流入号码,7以後节点的业务流出号码
		SwfLog swfLogDtoDealNode = new SwfLog();
		if (!(swfLogFlowID == null || swfLogFlowID.equals("")) && !(swfLogLogNo == null || swfLogLogNo.equals(""))) {
			swfLogDtoDealNode.getId().setFlowID(swfLogFlowID);
			swfLogDtoDealNode.getId().setLogNo(Integer.parseInt(DataUtils.nullToZero(swfLogLogNo)));
		}
		swfLogDtoDealNode.setNodeType("claim");
		swfLogDtoDealNode.setNodeStatus(buttonSaveType);
		swfLogDtoDealNode.setBusinessNo(registNo);
		swfLogDtoDealNode.setNextBusinessNo(claimNo);
		// 考虑 到後来几乎都是用clamno做keyin的数值的。。
		swfLogDtoDealNode.setKeyIn(claimNo);
		swfLogDtoDealNode.setKeyOut(claimNo);
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		return workFlowViewHelper.viewToDto(user, swfLogDtoDealNode);
	}

	/**
	 * 处理再保的现金赔款、共同理赔方案。 当立案提交且是现金摊赔时，向再保人员发送邮件
	 * @param ClaimDto 立案Dto
	 * @return String 提示信息:是否需要现金赔款、共同理赔
	 */
	private String findControlFlag(ClaimDto claimDto,HttpServletRequest httpServletRequest) throws Exception {
		ReinsClaimSummary reinsClaimSummary = ReinsTranslateViewHelper.getReinsClaimSummary(claimDto);
		Collection<ReinsLargeCase> reinsLargeCaseCollection = reinsServiceManager.getReinsService().getLargeCashLoss(reinsClaimSummary);
		String strInfo = "";
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		if (reinsLargeCaseCollection != null) {
			for (Iterator<ReinsLargeCase> i = reinsLargeCaseCollection.iterator(); i.hasNext();) {
				ReinsLargeCase reinsLargeCase = i.next();
				if (reinsLargeCase.getLargeLoss() == Boolean.TRUE) {
					strInfo = "該業務爲重大賠案,請盡快通知總公司相關險種承保人,進行相應處理!";
				}
				if (reinsLargeCase.getCashLoss() == Boolean.TRUE) {
					strInfo = "該業務需進行現金賠款攤回,請盡快通知總公司相關險種承保人,進行相應攤回處理!";
					//向再保发邮件
					String reinsMode = "現金攤賠";
					if("4".equals(httpServletRequest.getParameter("buttonSaveType"))){
						try {
							Map<String, Object> emailInfoMap = this.getEmailModel(claimDto, reinsMode);
							this.getEmailService().mailSend(claimDto.getPrpLclaim().getClaimNo(), "10002", "01", emailInfoMap);
							user.setUserMessage(user.getUserMessage() + "<br/>" + "郵件:現金攤賠案件Email通知函發送成功！");
						} catch (Exception e) {
							user.setUserMessage(user.getUserMessage() + "<br/>" + "郵件:現金攤賠案件Email通知函發送失败！");
						}
					}
					break;
				}
			}
		}
		return strInfo;
		// 需要在什么地方显示危险单位是否重大赔案、是否需要现金赔款
	}

	/**
	 * 处理再保的临分业务理赔方案
	 * 当立案提交且是临分业务时，向再保人员发送邮件
	 * @param ClaimDto 立案Dto
	 * @return String 提示信息:是否需要临分业务 
	 *         
	 */
	private String findIsReinsControl(ClaimDto claimDto,HttpServletRequest httpServletRequest) throws Exception {
		ReinsClaimSummary reinsClaimSummary = ReinsTranslateViewHelper.getReinsClaimSummary(claimDto);
		Collection<ReinsRepayCalResult> reinsRepayCalResultCollection = reinsServiceManager.getReinsService().repaySimulate(reinsClaimSummary);
		String strInfo = "";
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		if (reinsRepayCalResultCollection != null) {
			for (Iterator<ReinsRepayCalResult> i = reinsRepayCalResultCollection.iterator(); i.hasNext();) {
				ReinsRepayCalResult reinsRepayCalResult = i.next();
				String modeName = reinsRepayCalResult.getReinsModeName();
				if (modeName != null && getText("prompt.java44").equals(modeName.trim())) {
					strInfo = "該業務涉及臨時分出,請盡快通知總公司相關險種核保人,進行臨時賠案通知及攤賠處理!";
					if("4".equals(httpServletRequest.getParameter("buttonSaveType"))){
						try {
							String reinsMode = "臨分案";
							Map<String, Object> emailInfoMap = this.getEmailModel(claimDto, reinsMode);
							this.getEmailService().mailSend(claimDto.getPrpLclaim().getClaimNo(), "10001", "01", emailInfoMap);
							user.setUserMessage(user.getUserMessage()+"<br/>"+"郵件:臨分件Email通知函通知函發送成功！");
						} catch (Exception e) {
							user.setUserMessage(user.getUserMessage()+"<br/>"+"郵件:臨分件Email通知函通知函發送失败！");
						}
					}
					break;
				}
			}
		}
		return strInfo;
	}

	// 增加出错後放号回归函数
	// private boolean putNoback(String claimNo) throws Exception {
	// String tableName = "prplclaim";
	// if (billService.putNo(tableName, claimNo)) {
	// logger.debug("单号放回成功" + claimNo);
	// } else {
	// logger.debug("单号放回失败" + claimNo);
	// }
	// return true;
	// }

	/**
	 * 收集发送邮件的内容
	 * @param claimDto
	 * @return
	 */
	private Map<String,Object> getEmailModel(ClaimDto claimDto, String reinsMode) {
		String claimNo = "";
		String policyNo = "";
		double sumLossPaid = 0d;
		Date damageStartDate = null;
		String damageStartHour = "";
		String damageName = "";
		DecimalFormat decimalFormat = new DecimalFormat("#,##0");

		Map<String, Object> emailInfoMap = new HashMap<String, Object>();

		claimNo = claimDto.getPrpLclaim().getClaimNo();
		policyNo = claimDto.getPrpLclaim().getPolicyNo();
		for(PrpLclaimLoss prpLclaimLoss : claimDto.getPrpLclaimLossList()){
			sumLossPaid += prpLclaimLoss.getSumClaim();
		}
		damageStartDate = claimDto.getPrpLclaim().getDamageStartDate();
		String[] dateArray = damageStartDate.toString().split("-");//拼接成民国年
		String date = String.valueOf(Integer.parseInt(dateArray[0]) - 1911) + "-" + dateArray[1] + "-" + dateArray[2];
		damageStartHour = claimDto.getPrpLclaim().getDamageStartHour();
		damageName = claimDto.getPrpLclaim().getDamageName();
		
		emailInfoMap.put("reinsMode", reinsMode);//再保類型
		emailInfoMap.put("businessNo", claimNo.trim());//賠案號碼
		emailInfoMap.put("policyNo", policyNo.trim());//保單號碼
		emailInfoMap.put("sumLossPaid",decimalFormat.format(sumLossPaid));//損失金額
		emailInfoMap.put("damageStartDate",date);//出險日期
		emailInfoMap.put("damageStartHour",damageStartHour);//出险的小时，分钟
		emailInfoMap.put("damageName", damageName);//損失原因
		
		return emailInfoMap;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public DAAClaimViewHelper getDaaClaimViewHelper() {
		return daaClaimViewHelper;
	}

	public void setDaaClaimViewHelper(DAAClaimViewHelper daaClaimViewHelper) {
		this.daaClaimViewHelper = daaClaimViewHelper;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public PrpLemailLogService getPrpLemailLogService() {
		return prpLemailLogService;
	}

	public void setPrpLemailLogService(PrpLemailLogService prpLemailLogService) {
		this.prpLemailLogService = prpLemailLogService;
	}

	public JbpmBusinessViewHelper getJbpmBusinessViewHelper() {
		return jbpmBusinessViewHelper;
	}

	public void setJbpmBusinessViewHelper(JbpmBusinessViewHelper jbpmBusinessViewHelper) {
		this.jbpmBusinessViewHelper = jbpmBusinessViewHelper;
	}

	public SmsViewHelper getSmsViewHelper() {
		return smsViewHelper;
	}

	public void setSmsViewHelper(SmsViewHelper smsViewHelper) {
		this.smsViewHelper = smsViewHelper;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

    public BusinessViewHelper getBusinessViewHelper() {
        return businessViewHelper;
    }

    public void setBusinessViewHelper(BusinessViewHelper businessViewHelper) {
        this.businessViewHelper = businessViewHelper;
    }

}
