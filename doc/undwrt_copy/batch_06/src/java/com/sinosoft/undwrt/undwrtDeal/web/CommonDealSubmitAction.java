package com.sinosoft.undwrt.undwrtDeal.web;

import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import ins.framework.web.Struts2Action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpPhead;
import com.sinosoft.common.schema.model.PrpPmain;
import com.sinosoft.common.schema.model.PrpTitemKind;//mantis：HAS0288，處理人員：DP0706，需求單編號：TA新增海外突發疾病健康保險(含法傳)TR47(TR30改TR47)
//mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266 保發輔助平台,擴增欄位--核心變更需求
import com.sinosoft.common.schema.model.PrpQinsured;
import com.sinosoft.common.schema.model.PrpQmain;
import com.sinosoft.common.schema.model.PrpQmainSub;
//mantis： CAR0394，處理人員：DP0717，需求單編號：CAR0394.新核心增加要被保險人關係欄位相關檢核
import com.sinosoft.common.schema.model.PrpTinsured;
import com.sinosoft.common.schema.model.PrpTmain;
import com.sinosoft.common.schema.model.PrpTmainSub;
import com.sinosoft.platform.bl.facade.BLPrpDcompanyFacade;
import com.sinosoft.platform.bl.facade.BLPrpDriskFacade;
import com.sinosoft.platform.dto.domain.PrpDcompanyDto;
import com.sinosoft.platform.dto.domain.PrpDriskDto;
import com.sinosoft.platform.dto.domain.PrpDuserDto;
import com.sinosoft.platform.ui.control.action.LogUtils;
import com.sinosoft.prpall.blsvr.pg.BLPrpPhead;
import com.sinosoft.prpall.blsvr.pg.BLPrpPheadCovernote;
import com.sinosoft.prpall.blsvr.pg.BLPrpPmain;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmain;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmainSub;
import com.sinosoft.prpins.policy.service.facade.EndorseService;
import com.sinosoft.prpins.policy.service.facade.PolicyService;
import com.sinosoft.prpins.policy.web.PolicyAction;
import com.sinosoft.reins.common.service.facade.BLReinsLTrialService;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.common.util.Constants;
import com.sinosoft.undwrt.common.util.MsgAction;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtBase.model.SwfPath;
import com.sinosoft.undwrt.undwrtBase.model.UtiUwLevel;
import com.sinosoft.undwrt.undwrtBase.model.UwNotion;
import com.sinosoft.undwrt.undwrtBase.model.UwNotionId;
import com.sinosoft.undwrt.undwrtBase.model.WfLog;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathService;
import com.sinosoft.undwrt.undwrtBase.service.facade.UtiUwLevelService;
import com.sinosoft.undwrt.undwrtBase.service.facade.WfLogService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.CommonDealSubmitService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.ExpenseControlDealService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpFeedBackService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.PrpallService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.ReinsService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.TaskDealService;
import com.sinosoft.undwrt.undwrtDeal.service.facade.WfCheckAdvanceService;
import com.sinosoft.undwrt.undwrtDeal.vo.WfGradeVo;
import com.sinosoft.undwrt.undwrtInterface.service.spring.TaskServiceSpringImpl;
import com.sinosoft.undwrt.undwrtRule.service.facade.GetBusinessDataService;
import com.sinosoft.undwrt.undwrtRule.service.facade.UndwrtRuleService;
import com.sinosoft.undwrt.undwrtRule.vo.BusinessProposalData;
import org.hibernate.Session;
/**
 * 提交任務處理類
 */
public class CommonDealSubmitAction extends Struts2Action {

	/** 屬性業務類型. */
	private String businessType;

	/** 屬性任務代碼. */
	private String taskCode;

	/** 屬性選中的節點號. */
	private String selectNodeNo;

	/** 屬性選中的節點名稱. */
	private String selectNodeName;

	/** 屬性處理意見. */
	private String HandleText;

	/** 屬性要請求的ip地址. */
	private String submitTip;

	/** 屬性標題. */
	private String handTitle;

	/** 屬性機構名稱. */
	private String ComName;

	/** 屬性風險名稱. */
	private String RiskName;

	/** 屬性跳轉頁面返回結果. */
	private String content;

	/** 屬性強制險保單號. */
	private String policyNoCI;

	/** 屬性要保書保單號. */
	private String policyNoForT;

	/** 屬性工作流路徑定義接口. */
	private SwfPathService swfPathService;

	/** 屬性消息發送接口. */
	private MsgAction msgAction;

	/** 屬性核保回寫數據服務接口. */
	private PrpFeedBackService prpFeedBackService;

	/** 屬性核保審核處理接口. */
	private CommonDealSubmitService commonDealSubmitService;

	/** 屬性再保服務接口. */
	private ReinsService reinsService;

	/** 屬性工作流日誌接口. */
	private WfLogService wfLogService;

	/** 屬性權限校驗接口. */
	private WfCheckAdvanceService wfCheckAdvanceService;

	/** 屬性核定費用結余服務接口. */
	private ExpenseControlDealService expenseControlDealService;

	/** 屬性要保書處理接口. */
	private PolicyService policyService;

	/** 屬性核保審核規則引擎處理接口. */
	private UndwrtRuleService undwrtRuleService;

	/** 屬性分攤試算處理接口. */
	private BLReinsLTrialService blReinsLTrialService;

	/** 屬性獲取業務數據接口. */
	private GetBusinessDataService getBusinessDataService;

	/** 屬性批單處理接口. */
	private EndorseService endorseService;

	/** 核保系統查詢接口 */
	private PrpallService prpallService;

	/** 核保級別設定接口 */
	private UtiUwLevelService utiUwLevelService;
	
	private String iRiskCode;
	
	//add by xuhuiling 20160829 begin
	/** 查詢作業狀態和人工開關 */
	private TaskDealService taskDealService;
	//add by xuhuiling 20160829 end
	
	
	private static Logger logger = Logger.getLogger(CommonDealSubmitAction.class);
	
	/**
	 * 提交任務處理.
	 * 
	 * @return 頁面跳轉結果
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public String commonDealSubmit() throws UserException, Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		HttpSession session = this.getSession();
		HttpServletRequest req = this.getRequest();
		String forward = "";
		String submitFlag=req.getParameter("flag");
		PrpDuserDto prpDuserDto = (PrpDuserDto) session.getAttribute("user");
		String businessType = req.getParameter("BusinessType");
		// 处理业务类型
		String handTitle = (String) session.getAttribute("HandTitle");
		String logMessage = handTitle + getText("undwrt.action.commonDealSubmit.task");
		String logModule = handTitle + getText("undwrt.action.commonCheckTask.dealWith");

//		
		// 授权处理
		if ("Authorize".equals(req.getParameter("Authorize"))) {
			forward = "success";
			try {
				this.submitAuthorizeTask(req);
			} catch (UserException usee) {
				forward = "failure";

				session.setAttribute("userException", usee);
			} catch (InvocationTargetException inEx) {
				forward = "failure";
				if (inEx.getTargetException() instanceof UserException) {
					UserException ue = (UserException) inEx.getTargetException();
					session.setAttribute("userException", ue);
				}
			} catch (Exception e) {
				forward = "failure";
				throw e;
			}
			return forward;
		}

		try {
			if ("B".equals(businessType)) {
				String iTaskCode = req.getParameter("taskCode");
				String businessNo = req.getParameter("BusinessNo");

				PrpQmain prpQmain = new PrpQmain();
				prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
				if (null != iTaskCode && (iTaskCode.equals("2") || iTaskCode.equals("1") || iTaskCode.equals("3"))) {// 报价审核打回、通过,提交
																														// 上级
					if (!prpQmain.getUnderWriteFlag().equals("9")) {// 不是待审核状态
						throw new UserException(-98, -1149, "", internal.getText("undwrt.service.commonDealSubmit.maybeOperated"));
					}
				}

				if (null != iTaskCode && iTaskCode.equals("8")) {// 投保单核保通过
					if (!prpQmain.getUnderWriteFlag().equals("7")) {// 不是生成投保单状态
						throw new UserException(-98, -1149, "", internal.getText("undwrt.service.commonDealSubmit.maybeOperated2"));
					}
				}
				/*
				mantis： CAR0123，處理人員：Sam，需求單編號：CAR0123--- start
				延續原CAR0107議題,新增關聯單卡控條件
				*/
				PrpQmainSub prpqmainsub = policyService.getPrpQmainSubByQuoteno(businessNo);
				if(prpqmainsub != null){
					session.setAttribute("relevUndwrtBusiNo", prpqmainsub.getId().getMainPolicyNo());
				}
				/* mantis： CAR0123，處理人員：Sam，需求單編號：CAR0123 --- end */
			}

			this.submitTask(req);
			if(submitFlag!=null&&"submitPass".equals(submitFlag)){
				content = getText("undwrt.action.commonDealSubmit.checkPass");
			}else{
				content = getText("undwrt.action.batchTaskSubmit.taskSubmitSuccess");	
			}
			forward = "success";
			LogUtils.info(prpDuserDto, logModule, prpDuserDto.getUserName() + " " + logMessage + getText("undwrt.action.commonDealSubmit.submitSucWorkflow")
					+ req.getParameter("FlowID") + getText("oaUser.userId") + " ：" + req.getParameter("LogNo"));
		} catch (UserException usee) {
			forward = "failure";
			session.setAttribute("userException", usee);
			usee.printStackTrace();
		} catch (InvocationTargetException inEx) {
			forward = "failure";
			if (inEx.getTargetException() instanceof UserException) {
				UserException ue = (UserException) inEx.getTargetException();
				session.setAttribute("userException", ue);
			}
		} catch (Exception e) {
			forward = "failure";
			LogUtils.info(prpDuserDto, logModule, prpDuserDto.getUserName() + " " + logMessage + getText("undwrt.action.commonDealSubmit.submitFailWorkflow")
					+ req.getParameter("FlowID") + getText("oaUser.userId") + " ：" + req.getParameter("LogNo"));
			throw e;
		}
		return forward;
	}

	/**
	 * 授權控制審核.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 */
	public void submitAuthorizeTask(HttpServletRequest req) throws Exception {
		String iBussinessType = req.getParameter("BusinessType");
		String iBussinessNo = req.getParameter("BusinessNo");
		String info = prpFeedBackService.echoAuthorizePrp(iBussinessType, iBussinessNo);
		req.setAttribute("Authorize", info);
	}

	/**
	 * 报价审核提交.
	 * 
	 * @param req
	 *            請求對象
	 * @throws Exception
	 *             異常
	 * @throws UserException
	 *             自定義異常
	 */
	public void submitTaskQta(HttpServletRequest req) throws Exception, UserException {
		try {
			String taskCode = req.getParameter("taskCode");
			String BusinessNo = req.getParameter("BusinessNo");
			String UserCode = (String) req.getSession(false).getAttribute("myUserCode");

			commonDealSubmitService.submitTaskQta(BusinessNo, UserCode, taskCode);

			// 保存审批意见，更新工作流日志表中处理部门、处理人员代码及名称、处理时间、节点状态(3)。
			String HandleText = StringUtils.replace(req.getParameter("HandleText"), "'", "''");
			if (HandleText == null) {
				HandleText = "";
			}
			UwNotion uwNotion = new UwNotion();
			UwNotionId uwNotionId = new UwNotionId();
			uwNotion.setId(uwNotionId);
			uwNotion.getId().setFlowId(BusinessNo);
			uwNotion.getId().setLogNo(1);
			uwNotion.setHandleText(HandleText);

			// 将HandleText拆分成多条 变成多个uwNotionDto对象批量插入uwNotion表
			commonDealSubmitService.saveNotion(uwNotion);
		} catch (UserException usee) {
			throw usee;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 提交任務.
	 * 
	 * @param req
	 *            請求對象
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public void submitTask(HttpServletRequest req) throws UserException, Exception {
		try {
			String FlowId = req.getParameter("FlowId");
			int ModelNo = Integer.parseInt((String) req.getParameter("ModelNo"));
			int NodeNo = Integer.parseInt((String) req.getParameter("selectNodeNo"));
			int nodeNo = 0;
			BLPrpPmain blPrpPmain = new BLPrpPmain();
			BLPrpPhead blPrpPhead = new BLPrpPhead();
			BLPrpPheadCovernote blPrpPheadCovernote = new BLPrpPheadCovernote();
			String BusinessType = req.getParameter("BusinessType");
			String BusinessNo = req.getParameter("BusinessNo");
			String FlowStatus = req.getParameter("FlowStatus");
			String Flag = req.getParameter("Flag");
			String selectNodeName = req.getParameter("selectNodeName");
			String UserCode = (String) req.getSession(false).getAttribute("myUserCode");
			String OperatorCode = req.getParameter("SelectUser");
			BLPrpTmain blPrpTmain = new BLPrpTmain();
			String strRiskcode = "";
			boolean ifOffLineCal;
			Flag = "1"; // 0表示从业务系统提交到双核，1表示双核系统内部提交
			String strExpenseCtrlComCode = "";// 业务归属机构
			String strExpenseCtrlRiskCode = "";// 险种代码
			// 增加定级信息
			WfGradeVo wfGradeDto = new WfGradeVo();
			
			// 是否為審核通過節點
			if (selectNodeName.equals(getText("undwrt.action.commonDealSubmit.checkPass"))) {//審核通過
				// 审核通过后也进行保存审核意见
				// 保存审批意见，更新工作流日志表中处理部门、处理人员代码及名称、处理时间、节点状态(3)。
				String HandleText = StringUtils.replace(req.getParameter("HandleText"), "'", "''");
				if (HandleText == null) {
					HandleText = "";
				}
				UwNotion uwNotionDto = new UwNotion();
				UwNotionId uwId = new UwNotionId();
				uwId.setFlowId(req.getParameter("FlowId"));
				uwId.setLogNo(Integer.parseInt(req.getParameter("LogNo")));
				uwNotionDto.setId(uwId);
				uwNotionDto.setHandleText(HandleText);

				// 将HandleText拆分成多条 变成多个uwNotionDto对象批量插入uwNotion表
				commonDealSubmitService.saveNotion(uwNotionDto);
//				System.out.println("update");

				if ("T".equals(BusinessType)) {
					blPrpTmain.getData(BusinessNo);
					if (blPrpTmain.getSize() > 0) {
						System.out.println("blPrpTmain.getAgentCode1==="+blPrpTmain.getArr(0).getAgentCode());
						System.out.println("blPrpTmain.getProposalNo1==="+blPrpTmain.getArr(0).getProposalNo());
						System.out.println("blPrpTmain.getBusinessNature1==="+blPrpTmain.getArr(0).getBusinessNature());
						// 增加一个标志，用于判断此业务是否需要进行强制试算
						ifOffLineCal = reinsService.ifOffLineCal(blPrpTmain.getArr(0).getRiskCode(), blPrpTmain.getArr(0).getStartDate().substring(0, 4),
								BusinessNo, BusinessType);
						System.out.println(getText("undwrt.action.commonDealSubmit.interactiveBegin") + ifOffLineCal);
						// 由于需要控制附加自留，但再保没有进行强制试算，在这里需要进行强制试算
						// 需要离线计算,则不进行分保试算
						if (ifOffLineCal != true) {
							blReinsLTrialService.simulateRepolicyByDangerNo(BusinessNo, blPrpTmain.getArr(0).getClassCode(), BusinessType);// 在批改时第一个参数没有用
						}
						System.out.println(getText("undwrt.action.commonDealSubmit.interactionEnd") + ifOffLineCal);
						/*
						mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092--- start
						普通批改-核保&保費試算
						*/
						if ("PE".equals(iRiskCode)){
							TaskServiceSpringImpl taskService = (TaskServiceSpringImpl) ServiceFactory.getService("taskService");
							boolean isDoubleInsurance = taskService.checkDoubleInsuranceByPE(BusinessNo);
							if(isDoubleInsurance){
								System.out.println(getText("undwrt.action.commonDealSubmit.doubleInsurance"));
								throw new UserException(-98, -9999, "被保險寵物於保期內重複投保寵物險", "");
							}
						}
						/* mantis： LIA0092，處理人員：Sam，需求單編號：LIA0092 --- end */		
					}
				}
				if ("E".equals(BusinessType)) {
					blPrpPmain.getData(BusinessNo);
					blPrpPheadCovernote.getData(BusinessNo);
					if (blPrpPheadCovernote.getSize() > 0) {// 当时预约协议或暂保单时不送再保数据

					} else {
						if (blPrpPmain.getSize() > 0) {
							// 增加一个标志，用于判断此业务是否需要进行强制试算
							ifOffLineCal = reinsService.ifOffLineCal(blPrpPmain.getArr(0).getRiskCode(), blPrpPmain.getArr(0).getStartDate().substring(0, 4),
									BusinessNo, BusinessType);
							System.out.println(getText("undwrt.action.commonDealSubmit.interactiveBegin") + ifOffLineCal);
							// 由于需要控制附加自留，但再保没有进行强制试算，在这里需要进行强制试算
							if (ifOffLineCal != true) {
								blReinsLTrialService.simulateRepolicyByDangerNo(BusinessNo, blPrpPmain.getArr(0).getClassCode(), BusinessType);
							}
							System.out.println(getText("undwrt.action.commonDealSubmit.interactionEnd") + ifOffLineCal);
						}
					}
				}
				if (!("B".equals(BusinessType) && !("A01".equals(iRiskCode) || "B01".equals(iRiskCode)))){
					BusinessProposalData condition;
					// 获取业务数据
					if("A01".equals(iRiskCode) || "B01".equals(iRiskCode))
					{
						condition = getBusinessDataService.getBusinessProposalData(BusinessNo, BusinessType);
					} else
					{
						condition = getBusinessDataService.getUnCarBusinessData(BusinessNo, BusinessType);
					}

					HttpSession session = this.getSession();
					PrpDuserDto prpDuser = (PrpDuserDto) session.getAttribute("user");
					QueryRule queryRule = QueryRule.getInstance();
					queryRule.addEqual("id.uwType", Constants.UWTYPE_T);
					queryRule.addEqual("id.validStatus", "1");
					queryRule.addEqual("id.userCode", prpDuser.getUserCode());
					queryRule.addEqual("id.comCode", prpDuser.getLoginComCode());
					queryRule.addEqual("id.modelNo", ModelNo);
					if(!"B".equals(condition.getClassCode())&&!"A".equals(condition.getClassCode())){
						String sql=" (riskCode like '%"+condition.getRiskCode()+"%' or classCode='"+condition.getClassCode()+"')";
						queryRule.addSql(sql);
					}
					
					
					Collection<UtiUwLevel> utiUwLevelCollection = utiUwLevelService.getUtiUwLevelList(queryRule);

					if (null != utiUwLevelCollection && utiUwLevelCollection.iterator().hasNext()) {
						nodeNo = utiUwLevelCollection.iterator().next().getId().getNodeNo();
					}

					System.out.println("核保人員：" + prpDuser.getUserCode() + "核保級別：" + nodeNo);
					/*
					mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245--- start
					強任費率不一致的的需求
					*/
					boolean hasPath = true;
					if ("B".equals(BusinessType) || "T".equals(BusinessType)) {
						String undwrtmark = getBusinessDataService.getUndwrMark(BusinessType, BusinessNo);
						if("Y".equals(undwrtmark)){
							//需六級(db的 nodeNo : 7)以上核保人員才可核保
							if(nodeNo < 7 ){
								hasPath = false;
								condition.setStrResultMessage("權限不足，因強制和任意適用費率不一致，請洽區核組長(等級6)審核");
							}
						}
						/*
						mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266--- start
						保發輔助平台,擴增欄位--核心變更需求
						*/
						if("B".equals(BusinessType)){//報價單
							String notifyOrNot = null;
							PrpQmain prpQmain = policyService.getPrpQmainByProposalNo(BusinessNo, "quotation");
							//先檢查
							if("A01".equals(prpQmain.getRiskCode())){//因要保人為必填欄位 故不防null
								//單任不卡控 關聯單時需檢查強制險是否有填寫
								if(prpQmain.getPrpQmainSubs() !=  null && prpQmain.getPrpQmainSubs().size() > 0 ){
									PrpQmain prpQmainCI = policyService.getPrpQmainByProposalNo(prpQmain.getPrpQmainSubs().get(0).getId().getMainPolicyNo(), "quotation");
									if(prpQmainCI != null){//有關聯單 強制險
										PrpQinsured insured = null;
										for(PrpQinsured q:prpQmainCI.getPrpQinsureds()){
											if("2".equals(q.getInsuredFlag())){//要保人
												insured = q;
												break;
											}
										}
										if(StringUtils.isBlank(insured.getMobile()) && StringUtils.isBlank(insured.getEmail())){
											notifyOrNot = "Y";//當報價單時 電子信箱與手機未輸入 即改人工核保
										}
										//mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266
										if("Y".equals(notifyOrNot) && nodeNo < 6 ){
											hasPath = false;
											condition.setStrResultMessage("權限不足，保戶未留存要保人手機或電子信箱，請洽核保人員(等級5)審核");
										}else{
											getBusinessDataService.updateNotifyOrNot(prpQmain.getProposalNo());
											getBusinessDataService.updateNotifyOrNot(prpQmainCI.getProposalNo());
										}
									}
								}
							}else if("B01".equals(prpQmain.getRiskCode())){//任意險 因要保人為必填欄位 故不防null
								PrpQinsured insured = null;
								for(PrpQinsured q:prpQmain.getPrpQinsureds()){
									if("2".equals(q.getInsuredFlag())){//要保人
										insured = q;
										break;
									}
								}
								if(StringUtils.isBlank(insured.getMobile()) && StringUtils.isBlank(insured.getEmail())){
									notifyOrNot = "Y";//當報價單時 電子信箱與手機未輸入 即改人工核保
								}
								//mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266
								if("Y".equals(notifyOrNot) && nodeNo < 6 ){
									hasPath = false;
									condition.setStrResultMessage("權限不足，保戶未留存要保人手機或電子信箱，請洽核保人員(等級5)審核");
								}else{
									getBusinessDataService.updateNotifyOrNot(prpQmain.getProposalNo());
								}
							}
						}
						/* mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266 --- end */
						
						//mantis： CAR0394，處理人員：DP0717，需求單編號：CAR0394.新核心增加要被保險人關係欄位相關檢核 start
						if("T".equals(BusinessType)){ //要保書
							PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(BusinessNo);
							PrpTinsured insured = null;
							for(PrpTinsured t : prpTmain.getPrpTinsureds()){
								if("1".equals(t.getInsuredFlag())){//被保人
									insured = t;
									break;
								}
							}
							if("05".equals(insured.getInsuredIdentity())
									&& nodeNo < 6){
								//需五級(db的 nodeNo : 6)以上核保人員才可核保
								hasPath = false;
								condition.setStrResultMessage("要保人與被保人關係為 「5.其他」，需核保等級 5 級(含)以上人員審核");
							}
						}
						//mantis： CAR0394，處理人員：DP0717，需求單編號：CAR0394.新核心增加要被保險人關係欄位相關檢核 end
					}
					// 如果hasPath==true，表明有权限
					if(hasPath){
						hasPath = undwrtRuleService.checkUndwrtRules(nodeNo+"", condition);
						//mantis： OTH0139，處理人員：DP0713，需求單編號：OTH0139 保單內容批改規則異動 Start
						if(commonDealSubmitService.endorChangeBusiness(BusinessType,BusinessNo)){//代表有修改過業務員
							hasPath = undwrtRuleService.checkUndwrtRules((nodeNo - 1 )+"", condition);
							if (!hasPath) {
								System.out.println("權限不足，因業務員失效，故核保等級需提高一級審核，您的等級為:"+nodeNo+"無法審核。");
								throw new UserException(-98, -9999, "權限不足，因業務員失效，故核保等級需提高一級審核，您的等級為:"+nodeNo+"無法審核。", "");
							}
						}
						//mantis： OTH0139，處理人員：DP0713，需求單編號：OTH0139 保單內容批改規則異動 End
					}
					/* mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245 --- end */
					//mantis：HAS0288，處理人員：DP0706，需求單編號：TA新增海外突發疾病健康保險(含法傳)TR47(TR30改TR47)START
					if(hasPath){
						if("T".equals(BusinessType)){ //要保書
							if ("TA".equals(iRiskCode)){
								PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(BusinessNo);
								boolean hasTr47 = false;
								for(PrpTitemKind t : prpTmain.getPrpTitemKinds()){
									if("TR47".equals(t.getKindCode())){//
										hasTr47 = true;
										break;
									}
								}
								//為符合風險胃納量卡控機制，線下出含有TR47海外突發疾病保額(含法傳)之要保，需卡控核保等級5級以上之核保人員才可以審核通過
								if(hasTr47 && nodeNo < 6){
									//需五級(db的 nodeNo : 6)以上核保人員才可核保
									hasPath = false;
									condition.setStrResultMessage("TR47海外突發疾病保額(含法傳)，需核保等級 5 級(含)以上人員審核");
								}
							}
							
						}
					}
					//mantis：HAS0288，處理人員：DP0706，需求單編號：TA新增海外突發疾病健康保險(含法傳)TR47(TR30改TR47)END
					System.out.println(getText("undwrt.action.commonDealSubmit.haveLimitToCheck") + hasPath);

					if (!hasPath) {
						System.out.println(getText("undwrt.action.commonDealSubmit.returnNotPassReason") + condition.getStrResultMessage());
						throw new UserException(-98, -9999, condition.getStrResultMessage(), "");
					}
					if(hasPath){
						//add by lidongdong 20160317 reason:对级别进行判断 begin
						if(nodeNo<6){
						//add by lidongdong 20160317 reason:对级别进行判断 end
						  String hasTA = getBusinessDataService.checkUndwrtRules(BusinessNo, BusinessType);
						if(!"".equals(hasTA)){
							System.out.println(getText("undwrt.action.commonDealSubmit.returnNotPassReason") +hasTA);
							throw new UserException(-98, -9999,hasTA, "");
						}
					  }
					}
					if(hasPath&&"T".equals(BusinessType)){
						  boolean  hasTA = getBusinessDataService.checkUndwrtRules(BusinessNo);
						if(!hasTA&&nodeNo<5){
							System.out.println(getText("undwrt.action.commonDealSubmit.returnNotPassReason")+"被保險人在考核名單里，需四級核保人員核保");
							throw new UserException(-98, -9999,"被保險人在考核名單里，需四級核保人員核保", "");
						}
					}
					
				}
			}

			// 提交任务
			//add by xuhuiling 20160906 begin 
			//mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245 比對PRODclass無此程式 先拿掉同步
//			wfLogService.setHttpSession(this.getSession());//獲取當前的作用域的session,傳遞到WflogServiceSpringImpl中
			//add by xuhuiling 20160906 end 
			commonDealSubmitService.submitTask(FlowId, ModelNo, NodeNo, BusinessType, BusinessNo, FlowStatus, Flag, UserCode, OperatorCode, nodeNo, wfGradeDto);

			// 增加单子工作流状态的判断以区别是否为提交上级操作
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("businessNo", BusinessNo);
			WfLog wflog = wfLogService.findByQueryRule(queryRule).get(0);
			String nodeStatus = wflog.getNodeStatus();
			// 核保通过，返回保单号处理
			if (BusinessType.equals("T")) {
				PrpCmain prpCmain = new PrpCmain();
				PrpCmain prpCmain1 = new PrpCmain();
				PrpTmain prpTmain = new PrpTmain();
				prpTmain = policyService.getPrpTmainByProposalNo(BusinessNo);
				strRiskcode = prpTmain.getRiskCode();
				System.out.println("blPrpTmain.getAgentCode2==="+prpTmain.getAgentCode());
				System.out.println("blPrpTmain.getProposalNo2==="+prpTmain.getProposalNo());
				System.out.println("blPrpTmain.getBusinessNature2==="+prpTmain.getBusinessNature());
				logger.info("進入生成虛擬編碼---------------" + BusinessNo);
				if (nodeStatus == "0") {
					logger.info("生成虛擬編號開始------------------genDummyCode begin" + BusinessNo);
					// 生成虚拟编号20131326 by wangJun
					commonDealSubmitService.genDummyCode(BusinessNo, BusinessType);
					logger.info("生成虛擬編號結束------------------genDummyCode end" + BusinessNo);
				}
				logger.info("結束生成虛擬編碼----------------" + BusinessNo);
				System.out.println("blPrpTmain.getAgentCode3==="+prpTmain.getAgentCode());
				System.out.println("blPrpTmain.getProposalNo3==="+prpTmain.getProposalNo());
				System.out.println("blPrpTmain.getBusinessNature3==="+prpTmain.getBusinessNature());

				if ("A01".equals(strRiskcode) || "0502".equals(strRiskcode) || "0503".equals(strRiskcode) || "0510".equals(strRiskcode)) {
					List list = prpTmain.getPrpTmainSubs();
					if (list.size() > 0 && "111".equals(((PrpTmainSub) list.get(0)).getFlag())) {
						prpCmain = policyService.getPrpCmainByProposalNo(((PrpTmainSub) list.get(0)).getId().getMainPolicyNo());
						//需求变更，关联单只生成一个虚拟编码20140126xdw
						/*if (nodeStatus == "0") {
							// 生成虚拟编号20131326 by wangJun
							commonDealSubmitService.genDummyCode(((PrpTmainSub) list.get(0)).getId().getMainPolicyNo(), BusinessType);
						}*/
						if (prpCmain != null && nodeStatus == "0") {
							policyNoCI = prpCmain.getPolicyNo();
						}
					}
				}
				// 根据投保单号来查询保单的信息
				prpCmain1 = policyService.getPrpCmainByProposalNo(BusinessNo);
				if (prpCmain1 != null && nodeStatus == "0") {
					policyNoForT = prpCmain1.getPolicyNo();
					// 投保成功发送短信,此功能暂时不再使用20131130 by wangJun
					// ArrayList policyList = new ArrayList();
					// policyList.add(getText("undwrt.pages.undwrtDeal.insurancePolicy")+"："
					// + policyNoForT + "</br>");
					// msgAction.succesInsuredAsynchronousSend(policyList);
				}
			}
			// 核批通过 费用结余的处理
			if (BusinessType.equals("E")) {
				if (nodeStatus == "0") {
					// 生成虚拟编号20131326 by wangJun
					PrpPhead prpPhead = endorseService.getPrpPheadByEndorseNo(BusinessNo);
					PrpPmain prpPmain = prpPhead.getPrpPmains().get(0);
					//PrpPmain prpPmain = endorseService.getPrpPheadByEndorseNo(BusinessNo).getPrpPmains().get(0);
					if (null != prpPmain.getChgPremium() && prpPmain.getChgPremium().doubleValue() > 0) {
						commonDealSubmitService.genDummyCode(BusinessNo, BusinessType);
					}
					//收費出單的批單都要生成虛擬編號 modefied by zhangruofei 20150126
					if (null != prpPmain.getChgPremium() && (prpPmain.getChgPremium().compareTo(BigDecimal.ZERO)<1)) {
						if(commonDealSubmitService.checkIsNeadPaid(prpPhead)) {
							commonDealSubmitService.genDummyCode(BusinessNo, BusinessType);
						}						
					}
				}
				boolean blnStatus = false;
				blPrpPhead.getData(BusinessNo);
				blPrpPheadCovernote.getData(BusinessNo);
				if (blPrpPheadCovernote.getSize() > 0) {
					strExpenseCtrlComCode = blPrpPheadCovernote.getArr(0).getComCode();
					strExpenseCtrlRiskCode = blPrpPheadCovernote.getArr(0).getRiskCode();
					if (blPrpPheadCovernote.getArr(0).getUnderWriteFlag().equals("1") || blPrpPheadCovernote.getArr(0).getUnderWriteFlag().equals("3")
							|| blPrpPheadCovernote.getArr(0).getUnderWriteFlag().equals("5") || blPrpPheadCovernote.getArr(0).getUnderWriteFlag().equals("6")) {
						blnStatus = true;
					}
				} else {
					if (blPrpPhead.getSize() > 0) {
						strExpenseCtrlComCode = blPrpPhead.getArr(0).getComCode();
						strExpenseCtrlRiskCode = blPrpPhead.getArr(0).getRiskCode();
						if (blPrpPhead.getArr(0).getUnderWriteFlag().equals("1") || blPrpPhead.getArr(0).getUnderWriteFlag().equals("3")
								|| blPrpPhead.getArr(0).getUnderWriteFlag().equals("5") || blPrpPhead.getArr(0).getUnderWriteFlag().equals("6")) {
							blnStatus = true;
						}
					}
				}
				if (blnStatus) {
					double dblExpenseBalance = expenseControlDealService.getExpenseBalance(BusinessType, BusinessNo);
					req.setAttribute("ExpenseBalance", "" + dblExpenseBalance);
					// 对该笔业务的归属机构和险种代码的处理
					this.setExpenseBalanceInfo(req, strExpenseCtrlComCode, strExpenseCtrlRiskCode);
				}
			}
			//modify by dongfan 核心繫統配合需求170作出調整  20170216 
			//車險報價單核保時不生成虛擬編碼 modefied by zhangruofei 20150325
			/*
			mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245--- start
			 比對PRODclass有此程式 加回來同步
			*/
			if(!"A01".equals(iRiskCode) && !"B01".equals(iRiskCode)) {
				if(businessType.equals("B")) {
					PrpQmain prpQmain = new PrpQmain();
					prpQmain = policyService.getPrpQmainByProposalNo(BusinessNo, "quotation");
					strRiskcode = prpQmain.getRiskCode();
					if (nodeStatus == "0") {
						// 生成虚拟编号20131326 by wangJun
						commonDealSubmitService.genDummyCode(BusinessNo, BusinessType);
					}
				}
			}
			/* mantis： CAR0245，處理人員：Sam，需求單編號：CAR0245 --- end */
			if ("T".equals(BusinessType)) {
				PrpTmain  prpTmainTemp = policyService.getPrpTmainByProposalNo(BusinessNo);
				System.out.println("blPrpTmain.getAgentCode4==="+prpTmainTemp.getAgentCode());
				System.out.println("blPrpTmain.getProposalNo4==="+prpTmainTemp.getProposalNo());
				System.out.println("blPrpTmain.getBusinessNature4==="+prpTmainTemp.getBusinessNature());
			}
		} catch (UserException usee) {
			logger.info("拋出異常  UserException--------------------");
			logger.error(getTrace(usee));
			String businessType = req.getParameter("BusinessType");
			String businessNo = req.getParameter("BusinessNo");
			if("B".equals(businessType)) {
				this.checkMainSubQat(businessType, businessNo, selectNodeName);
			} else {
				this.checkMainSub(businessType, businessNo, selectNodeName);
			}					
			usee.printStackTrace();
			throw usee;
		} catch (Exception e) {
			logger.info("拋出異常  Exception--------------------");
			logger.error(getTrace(e));
			String businessType = req.getParameter("BusinessType");
			String businessNo = req.getParameter("BusinessNo");
			//this.checkMainSub(businessType, businessNo, selectNodeName);
			if("B".equals(businessType)) {
				this.checkMainSubQat(businessType, businessNo, selectNodeName);
			} else {
				this.checkMainSub(businessType, businessNo, selectNodeName);
			}
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 檢查是否關聯單.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param passNodeName
	 *            審核通過節點名稱
	 */
	public void checkMainSub(String businessType, String businessNo, String passNodeName){
		try{
			BLPrpTmainSub blPrpTmainSub = new BLPrpTmainSub();
			blPrpTmainSub.query("proposalNo = '" + businessNo + "' and flag = '111'");
			if(blPrpTmainSub.getSize()>0){
				deleteCmain(businessType , blPrpTmainSub.getArr(0).getMainPolicyNo(), passNodeName);
			}
			deleteCmain(businessType , businessNo, passNodeName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void checkMainSubQat(String businessType, String businessNo, String passNodeName) {
		TaskServiceSpringImpl taskService = (TaskServiceSpringImpl) ServiceFactory.getService("taskService");
		try{
			PrpQmainSub prpqmainsub = policyService.getPrpQmainSubByQuoteno(businessNo);
			if(null!=prpqmainsub && null!=prpqmainsub.getId()) {
				if("111".equals(prpqmainsub.getFlag())) {
					deleteQmain(businessType , prpqmainsub.getId().getMainPolicyNo());
				}
			}
			deleteQmain(businessType , businessNo);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 系統異常刪除保單數據，更新報價要報書狀態-Servlet生成保單數據無法回滾.
	 * 
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param passNodeName
	 *            審核通過節點名稱
	 */
	public void deleteCmain(String businessType, String businessNo, String passNodeName){
		if (passNodeName.equals(getText("undwrt.action.commonDealSubmit.checkPass")) && "T".equals(businessType)) {
			PrpCmain prpCmain = policyService.getPrpCmainByProposalNo(businessNo);
			PrpTmain prpTmain = policyService.getPrpTmainByProposalNo(businessNo);
			String quoteNo = prpTmain.getQuoteno();
			PrpQmain prpQmain = null;
			if (null != quoteNo && "" != quoteNo) {
				prpQmain = policyService.getPrpQmainByProposalNo(quoteNo, "quotation");
			}
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("businessNo", businessNo);
			WfLog wflog = wfLogService.findByQueryRule(queryRule).get(0);
			if (null != prpCmain && !"0".equals(wflog.getNodeStatus())) {
				String policyNo = prpCmain.getPolicyNo();
				prpallService.delete(prpCmain);
				prpallService.RecoveryStatus(policyNo);
				prpTmain.setUnderWriteFlag("9");
				//modefied by zhangruofei 为避免跳号问题，T表的保单号不清空，在下次核保时使用这次生成的保单号
				//prpTmain.setPolicyNo("");
				if (null != prpQmain) {
					prpQmain.setUnderWriteFlag("7");
					writeBackStatus(prpQmain);
				}
				writeBackStatus(prpTmain);
			} else if(!"0".equals(wflog.getNodeStatus()) && null!=prpTmain.getJfeeFlag() 
					&& "1".equals(prpTmain.getJfeeFlag())) {
				//收費出單件核保出错时也需要处理相关数据 modefied by zhangruofei 20150326
				prpallService.RecoveryStatus(businessNo);
			}
		}
	}
	public void deleteQmain(String businessType, String businessNo){
		PrpQmain prpQmain = null;
		prpQmain = policyService.getPrpQmainByProposalNo(businessNo, "quotation");
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("businessNo", businessNo);
		WfLog wflog = wfLogService.findByQueryRule(queryRule).get(0);
		prpallService.RecoveryStatusQta(businessNo);
		if(null!=prpQmain.getRiskCode() && "A01".equals(prpQmain.getRiskCode())) {
			prpQmain.setUnderWriteFlag("9");
			writeBackStatus(prpQmain);
		}		
    }
	
	/**
	 * Write back status.
	 * 
	 * 目前系统走Servlet 无法正常控制事务,发生异常时回写underwriteflag的值为9
	 * 
	 * @param obj
	 *            the obj
	 */
	public void writeBackStatus(Object obj) {
		if (obj.getClass().equals(PrpTmain.class)) {
			prpallService.updateTmain((PrpTmain) obj);
		}
		if (obj.getClass().equals(PrpCmain.class)) {
			prpallService.updateCmain((PrpCmain) obj);
		}
		if (obj.getClass().equals(PrpQmain.class)) {
			prpallService.updateQmain((PrpQmain) obj);
		}
	}

	/**
	 * 獲取前進路徑列表.
	 * 
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param defaultFlag
	 *            默認標誌位
	 * @param comCode
	 *            機構代碼
	 * @return 前進路徑列表
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public Collection<SwfPath> getPathes(int modelNo, int nodeNo, String businessType, String businessNo, String defaultFlag, String comCode)
			throws UserException, Exception {
		return (ArrayList<SwfPath>) swfPathService.getPathes(modelNo, nodeNo, businessType, businessNo, defaultFlag, comCode);
	}

	/**
	 * 獲取前進路徑列表.
	 * 
	 * @param modelNo
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param businessType
	 *            業務類型
	 * @param businessNo
	 *            業務號
	 * @param defaultFlag
	 *            默認標誌位
	 * @param comCode
	 *            機構代碼
	 * @param batchFlag
	 *            標志
	 * @return 前進路徑列表
	 * @throws UserException
	 *             自定義異常
	 * @throws Exception
	 *             異常
	 */
	public Collection<SwfPath> getPathes(int modelNo, int nodeNo, String businessType, String businessNo, String defaultFlag, String comCode, String batchFlag)
			throws UserException, Exception {
		try {
			// 增加高级条件判断
			//boolean hasPath = wfCheckAdvanceService.checkAdvanceCondition(modelNo, nodeNo, businessType, businessNo, "1");

			return (ArrayList<SwfPath>) swfPathService.getPathes(modelNo, nodeNo, businessType, businessNo, defaultFlag, comCode, batchFlag);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 設置費用聯動信息.
	 * 
	 * @param req
	 *            請求對象
	 * @param iComCode
	 *            機構代碼
	 * @param iRiskCode
	 *            險種代碼
	 * @throws Exception
	 *             異常
	 */
	public void setExpenseBalanceInfo(HttpServletRequest req, String iComCode, String iRiskCode) throws Exception {
		BLPrpDcompanyFacade blPrpDcompanyFacade = new BLPrpDcompanyFacade();
		BLPrpDriskFacade blPrpDriskFacade = new BLPrpDriskFacade();
		PrpDcompanyDto prpDcompanyDto = null;
		PrpDriskDto prpDriskDto = null;
		String strComName = "";
		String strRiskName = "";

		if (blPrpDcompanyFacade.findByPrimaryKey(iComCode) != null) {
			prpDcompanyDto = blPrpDcompanyFacade.findByPrimaryKey(iComCode);
			strComName = prpDcompanyDto.getComCName();
			req.setAttribute("ComName", strComName);
		}
		if (blPrpDriskFacade.findByPrimaryKey(iRiskCode) != null) {
			prpDriskDto = blPrpDriskFacade.findByPrimaryKey(iRiskCode);
			strRiskName = prpDriskDto.getRiskCName();
			req.setAttribute("RiskName", strRiskName);
		}
	}

	/**
	 * 獲取屬性業務類型.
	 * 
	 * @return 屬性業務類型的值
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 設置屬性業務類型.
	 * 
	 * @param businessType
	 *            待設置的業務類型的值
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 獲取屬性任務代碼.
	 * 
	 * @return 屬性任務代碼的值
	 */
	public String getTaskCode() {
		return taskCode;
	}

	/**
	 * 設置屬性任務代碼.
	 * 
	 * @param taskCode
	 *            待設置的任務代碼的值
	 */
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	/**
	 * 獲取屬性選中的節點號.
	 * 
	 * @return 屬性選中的節點號的值
	 */
	public String getSelectNodeNo() {
		return selectNodeNo;
	}

	/**
	 * 設置屬性選中的節點號.
	 * 
	 * @param selectNodeNo
	 *            待設置的選中的節點號的值
	 */
	public void setSelectNodeNo(String selectNodeNo) {
		this.selectNodeNo = selectNodeNo;
	}

	/**
	 * 獲取屬性選中的節點名稱.
	 * 
	 * @return 屬性選中的節點名稱的值
	 */
	public String getSelectNodeName() {
		return selectNodeName;
	}

	/**
	 * 設置屬性選中的節點名稱.
	 * 
	 * @param selectNodeName
	 *            待設置的選中的節點名稱的值
	 */
	public void setSelectNodeName(String selectNodeName) {
		this.selectNodeName = selectNodeName;
	}

	/**
	 * 獲取屬性要請求的ip地址.
	 * 
	 * @return 屬性要請求的ip地址的值
	 */
	public String getSubmitTip() {
		return submitTip;
	}

	/**
	 * 設置屬性要請求的ip地址.
	 * 
	 * @param submitTip
	 *            待設置的要請求的ip地址的值
	 */
	public void setSubmitTip(String submitTip) {
		this.submitTip = submitTip;
	}

	/**
	 * 獲取屬性處理意見.
	 * 
	 * @return 屬性處理意見的值
	 */
	public String getHandleText() {
		return HandleText;
	}

	/**
	 * 設置屬性處理意見.
	 * 
	 * @param handleText
	 *            待設置的處理意見的值
	 */
	public void setHandleText(String handleText) {
		this.HandleText = handleText;
	}

	/**
	 * 獲取屬性標題.
	 * 
	 * @return 屬性標題的值
	 */
	public String getHandTitle() {
		return handTitle;
	}

	/**
	 * 設置屬性標題.
	 * 
	 * @param handTitle
	 *            待設置的標題的值
	 */
	public void setHandTitle(String handTitle) {
		this.handTitle = handTitle;
	}

	/**
	 * 獲取屬性機構名稱.
	 * 
	 * @return 屬性機構名稱的值
	 */
	public String getComName() {
		return ComName;
	}

	/**
	 * 設置屬性機構名稱.
	 * 
	 * @param comName
	 *            待設置的機構名稱的值
	 */
	public void setComName(String comName) {
		this.ComName = comName;
	}

	/**
	 * 獲取屬性風險名稱.
	 * 
	 * @return 屬性風險名稱的值
	 */
	public String getRiskName() {
		return RiskName;
	}

	/**
	 * 設置屬性風險名稱.
	 * 
	 * @param riskName
	 *            待設置的風險名稱的值
	 */
	public void setRiskName(String riskName) {
		this.RiskName = riskName;
	}

	/**
	 * 獲取屬性跳轉頁面返回結果.
	 * 
	 * @return 屬性跳轉頁面返回結果的值
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 設置屬性跳轉頁面返回結果.
	 * 
	 * @param content
	 *            待設置的跳轉頁面返回結果的值
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 獲取屬性強制險保單號.
	 * 
	 * @return 屬性強制險保單號的值
	 */
	public String getPolicyNoCI() {
		return policyNoCI;
	}

	/**
	 * 設置屬性強制險保單號.
	 * 
	 * @param policyNoCI
	 *            待設置的強制險保單號的值
	 */
	public void setPolicyNoCI(String policyNoCI) {
		this.policyNoCI = policyNoCI;
	}

	/**
	 * 獲取屬性要保書保單號.
	 * 
	 * @return 屬性要保書保單號的值
	 */
	public String getPolicyNoForT() {
		return policyNoForT;
	}

	/**
	 * 設置屬性要保書保單號.
	 * 
	 * @param policyNoForT
	 *            待設置的要保書保單號的值
	 */
	public void setPolicyNoForT(String policyNoForT) {
		this.policyNoForT = policyNoForT;
	}

	/**
	 * 獲取屬性工作流日誌接口.
	 * 
	 * @return 屬性工作流日誌接口的值
	 */
	public WfLogService getWfLogService() {
		return wfLogService;
	}

	/**
	 * 設置屬性工作流日誌接口.
	 * 
	 * @param wfLogService
	 *            待設置的工作流日誌接口的值
	 */
	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	/**
	 * 獲取屬性工作流路徑定義接口.
	 * 
	 * @return 屬性工作流路徑定義接口的值
	 */
	public SwfPathService getSwfPathService() {
		return swfPathService;
	}

	/**
	 * 設置屬性工作流路徑定義接口.
	 * 
	 * @param swfPathService
	 *            待設置的工作流路徑定義接口的值
	 */
	public void setSwfPathService(SwfPathService swfPathService) {
		this.swfPathService = swfPathService;
	}

	/**
	 * 獲取屬性權限校驗接口.
	 * 
	 * @return 屬性權限校驗接口的值
	 */
	public WfCheckAdvanceService getWfCheckAdvanceService() {
		return wfCheckAdvanceService;
	}

	/**
	 * 設置屬性權限校驗接口.
	 * 
	 * @param wfCheckAdvanceService
	 *            待設置的權限校驗接口的值
	 */
	public void setWfCheckAdvanceService(WfCheckAdvanceService wfCheckAdvanceService) {
		this.wfCheckAdvanceService = wfCheckAdvanceService;
	}

	/**
	 * 獲取屬性消息發送接口.
	 * 
	 * @return 屬性消息發送接口的值
	 */
	public MsgAction getMsgAction() {
		return msgAction;
	}

	/**
	 * 設置屬性消息發送接口.
	 * 
	 * @param msgAction
	 *            待設置的消息發送接口的值
	 */
	public void setMsgAction(MsgAction msgAction) {
		this.msgAction = msgAction;
	}

	/**
	 * 獲取屬性要保書處理接口.
	 * 
	 * @return 屬性要保書處理接口的值
	 */
	public PolicyService getPolicyService() {
		return policyService;
	}

	/**
	 * 設置屬性要保書處理接口.
	 * 
	 * @param policyService
	 *            待設置的要保書處理接口的值
	 */
	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	/**
	 * 獲取屬性核定費用結余服務接口.
	 * 
	 * @return 屬性核定費用結余服務接口的值
	 */
	public ExpenseControlDealService getExpenseControlDealService() {
		return expenseControlDealService;
	}

	/**
	 * 設置屬性核定費用結余服務接口.
	 * 
	 * @param expenseControlDealService
	 *            待設置的核定費用結余服務接口的值
	 */
	public void setExpenseControlDealService(ExpenseControlDealService expenseControlDealService) {
		this.expenseControlDealService = expenseControlDealService;
	}

	/**
	 * 獲取屬性核保回寫數據服務接口.
	 * 
	 * @return 屬性核保回寫數據服務接口的值
	 */
	public PrpFeedBackService getPrpFeedBackService() {
		return prpFeedBackService;
	}

	/**
	 * 設置屬性核保回寫數據服務接口.
	 * 
	 * @param prpFeedBackService
	 *            待設置的核保回寫數據服務接口的值
	 */
	public void setPrpFeedBackService(PrpFeedBackService prpFeedBackService) {
		this.prpFeedBackService = prpFeedBackService;
	}

	/**
	 * 獲取屬性核保審核處理接口.
	 * 
	 * @return 屬性核保審核處理接口的值
	 */
	public CommonDealSubmitService getCommonDealSubmitService() {
		return commonDealSubmitService;
	}

	/**
	 * 設置屬性核保審核處理接口.
	 * 
	 * @param commonDealSubmitService
	 *            待設置的核保審核處理接口的值
	 */
	public void setCommonDealSubmitService(CommonDealSubmitService commonDealSubmitService) {
		this.commonDealSubmitService = commonDealSubmitService;
	}

	/**
	 * 獲取屬性再保服務接口.
	 * 
	 * @return 屬性再保服務接口的值
	 */
	public ReinsService getReinsService() {
		return reinsService;
	}

	/**
	 * 設置屬性再保服務接口.
	 * 
	 * @param reinsService
	 *            待設置的再保服務接口的值
	 */
	public void setReinsService(ReinsService reinsService) {
		this.reinsService = reinsService;
	}

	/**
	 * 獲取屬性核保審核規則引擎處理接口.
	 * 
	 * @return 屬性核保審核規則引擎處理接口的值
	 */
	public UndwrtRuleService getUndwrtRuleService() {
		return undwrtRuleService;
	}

	/**
	 * 設置屬性核保審核規則引擎處理接口.
	 * 
	 * @param undwrtRuleService
	 *            待設置的核保審核規則引擎處理接口的值
	 */
	public void setUndwrtRuleService(UndwrtRuleService undwrtRuleService) {
		this.undwrtRuleService = undwrtRuleService;
	}

	/**
	 * 獲取屬性分攤試算處理接口.
	 * 
	 * @return 屬性分攤試算處理接口的值
	 */
	public BLReinsLTrialService getBlReinsLTrialService() {
		return blReinsLTrialService;
	}

	/**
	 * 設置屬性分攤試算處理接口.
	 * 
	 * @param blReinsLTrialService
	 *            待設置的分攤試算處理接口的值
	 */
	public void setBlReinsLTrialService(BLReinsLTrialService blReinsLTrialService) {
		this.blReinsLTrialService = blReinsLTrialService;
	}

	/**
	 * 獲取屬性業務數據接口.
	 * 
	 * @return 屬性業務數據接口的值
	 */
	public GetBusinessDataService getGetBusinessDataService() {
		return getBusinessDataService;
	}

	/**
	 * 設置屬性業務數據接口.
	 * 
	 * @param getBusinessDataService
	 *            待設置的業務數據接口的值
	 */
	public void setGetBusinessDataService(GetBusinessDataService getBusinessDataService) {
		this.getBusinessDataService = getBusinessDataService;
	}

	/**
	 * 獲取屬性批單處理接口.
	 * 
	 * @return 屬性批單處理接口的值
	 */
	public EndorseService getEndorseService() {
		return endorseService;
	}

	/**
	 * 設置屬性批單處理接口.
	 * 
	 * @param endorseService
	 *            待設置的批單處理接口的值
	 */
	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	/**
	 * 獲取核保系統查詢接口.
	 * 
	 * @return the 核保系統查詢接口
	 */
	public PrpallService getPrpallService() {
		return prpallService;
	}

	/**
	 * 設置核保系統查詢接口.
	 * 
	 * @param prpallService
	 *            待設置的核保系統查詢接口
	 */
	public void setPrpallService(PrpallService prpallService) {
		this.prpallService = prpallService;
	}

	/**
	 * 獲取核保級別設定接口.
	 * 
	 * @return the 核保級別設定接口
	 */
	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	/**
	 * 設置核保級別設定接口.
	 * 
	 * @param utiUwLevelService
	 *            the new 核保級別設定接口
	 */
	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public String getiRiskCode() {
		return iRiskCode;
	}

	public void setiRiskCode(String iRiskCode) {
		this.iRiskCode = iRiskCode;
	}
	
	public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }

	/**
	 * 查詢作業狀態和人工開關
	 * @author xuhuing
	 * @return
	 */
	public TaskDealService getTaskDealService() {
		return taskDealService;
	}

	/**
	 * 查詢作業狀態和人工開關
	 * @author Administrator
	 * @param taskDealService
	 */
	public void setTaskDealService(TaskDealService taskDealService) {
		this.taskDealService = taskDealService;
	}
	
}