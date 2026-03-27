package com.sinosoft.claim.compensate.web;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// mantis：CLM0219，處理人員：DP0714，新核心-理算暫存功能異常(未處理理算)
import org.apache.commons.lang3.StringUtils;

//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
import com.sinosoft.claim.schema.service.facade.PrpDpolicyRulesService;
import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.common.vo.ExceptDeductibleRateDto;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.compensate.service.facade.PrepayService;
import com.sinosoft.claim.compensate.util.AccidentCompensateViewHelper;
import com.sinosoft.claim.compensate.util.CompensateLimitViewHelper;
import com.sinosoft.claim.compensate.util.SunnyCompensateViewHelper;
import com.sinosoft.claim.compensate.util.UIDeductCondAction;
import com.sinosoft.claim.compensate.vo.CompensateDto;
import com.sinosoft.claim.dto.custom.UserDto;
//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單START
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.VehicleClaimApiLog;
import com.sinosoft.claim.schema.service.facade.VehicleClaimApiLogService;
//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單END
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDcode;
//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
import com.sinosoft.claim.schema.model.PrpDpolicyRules;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLdeductCond;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.UwNotion;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
import com.sinosoft.claim.schema.service.facade.PrpDpolicyRulesService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLdeductCondService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.UwNotionService;
import com.sinosoft.claim.ui.control.action.UIQuickCaseAction;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
/**
 * 分发HTTP GET 车险理赔实赔前查询保单请求
 * <p>
 * Title: 车险理赔实赔前查询保单信息
 * </p>
 * <p>
 * Description: 车险理赔实赔前查询保单信息系统样本程序
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
public class CompensateBeforeEditAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/** 查勘服务 */
	private PrpLcheckService prpLcheckService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 计算书免赔条件服务 */
	private PrpLdeductCondService prpLdeductCondService;
	/** 理算数据收集 */
	private SunnyCompensateViewHelper sunnyCompensateViewHelper;
	/** 限额/免赔服务 */
	private PrpClimitService prpClimitService;
	/** 非车险数据收集 */
	private AccidentCompensateViewHelper accidentCompensateViewHelper;
	/** 理算服务 */
	private CompensateService compensateService;
	/** 预陪服务 */
	private PrepayService prepayService;
	/** 报案数据收集 */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 保单服务 */
	private PolicyService policyService;
	/** 结案数据收集 */
	private EndorseViewHelper endorseViewHelper;
	/** 代码翻译服务 */
	private CodeService codeService;
	/** 编辑类型 */
	private String editType;
	/** 支付信息服务 */
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 核保核赔处理意见服务 */
	private UwNotionService uwNotionService;
	/** 工作流数据收集 */
	private WorkFlowViewHelper workFlowViewHelper;
	/** 工作流日志服务 */
	private SwfLogService swfLogService;
	/** 工作流处理服务 */
	private WorkFlowService workFlowService;
	/** 核心地址 */
	private String coreURL;
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
	/** 备案service */
	private RegistService registService;
	
	private CommonService commonService;
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
	private PrpLpersonLossService PrpLpersonLossService;
	/** mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 */
	private PrpDpolicyRulesService prpDpolicyRulesService;
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	private String isCompulsoryBchainClaimDisabled="true";
	private String registSharingFlagDisabled = "false";

	private VehicleClaimApiLogService vehicleClaimApiLogService;
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
	/**
	 * 理算处理前信息
	 * @return
	 * @throws Exception
	 */
	public String compensateBeforeEdit() throws Exception {
		coreURL = AppConfig.get("sysconst.Core_URL");
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示 DELETE-删除
		String claimNo = request.getParameter("ClaimNo"); // 赔案号
		String compensateNo = request.getParameter("prpLcompensateCompensateNo"); // 赔款计算书号
		String riskCode = request.getParameter("riskCode");// 险种
		String caseType = request.getParameter("caseType");// 特殊赔案标志
		String forward = ""; // 向前
		String swfLogFlowID = request.getParameter("swfLogFlowID");
		String swfLogLogNo = request.getParameter("swfLogLogNo");
		String businessNo = request.getParameter("businessNo");
		String rechoseFlag = request.getParameter("rechoseFlag");
		String chargeType = request.getParameter("chargeType");
		// 用于独立处理费用的判断，如果是强制险则显示，任意险隐藏
		request.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
		// 支付对象 帳號歸屬人證件類型
		request.setAttribute("prpdpaymentaccountCertificateTypeList", ConstantsCollection.prpdpaymentaccountCertificateTypeList);
		if (rechoseFlag != null && !rechoseFlag.equals("")) {
			editType = "RECHOSE";
		}
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
		request.setAttribute("configCode", configCode);
		try {
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			String conditions = " claimno='" + claimNo + "' and casetype in ('5','7')";
			List<PrpLprepay> prepayList = this.prepayService.findByConditions(conditions);
			if (prepayList != null && prepayList.size() > 0) {
				for (PrpLprepay prpLprepay : prepayList) {
					if (!"1".equals(prpLprepay.getUnderWriteFlag())) {
						String msg = getText("prompt.compensate.prePayUnpass");//此賠案預賠未通過，不能出計算書！
						throw new UserException(1, 3, getText("prompt.compensate.specialClaim"), msg);//特殊賠案
					}
				}
			}

			// 增被保险人联系电话
			String registNo = this.getCodeService().translateBusinessCode(claimNo, false);
			conditions = " registNo='" + registNo + "'";
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(conditions);
			List<PrpLcheck> checkList = this.prpLcheckService.findPrpLcheck(queryRule);
			PrpLcheck prpLcheck = new PrpLcheck();
			prpLcheck.setCheckList(checkList);
			request.setAttribute("prpLcheck", prpLcheck);
			//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
			RegistDto registDto = registService.findByPrimaryKey(registNo);
			//PrpLregist tempPrpLregist = registDto.getPrpLregist();
			request.setAttribute("registDto", registDto);
			request.setAttribute("riskCode", riskCode);
			
			PrpLregist prpLregist =  registDto.getPrpLregist();
			if(null!=riskCode && null!=prpLregist){
				request.setAttribute("registType", prpLregist.getRegistType());
			}
			//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
			// 进行占号分析，只有当有工作流号码和LogNo的时候才能使用。
			// 如果没有flowID和logno则不进行判断。
			String flowID = swfLogFlowID;
			String logNo = swfLogLogNo;
			if (DataUtils.emptyToNull(flowID) != null && DataUtils.emptyToNull(logNo) != null && !"SHOW".equalsIgnoreCase(editType)) {
				SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
				if (swfLogDto.getHoldNode() == false) {
					//案件'		'已經被代碼:'	',名稱:'		'的用戶所占用,請選擇其它案件進行處理!
					String msg = getText("prompt.certify.case") + businessNo + getText("prompt.certify.alreadyByCode") + swfLogDto.getHandlerCode() + getText("prompt.certify.codeName") + swfLogDto.getHandlerName() + getText("prompt.certify.userOperating");
					throw new UserException(1, 3, getText("prompt.certify.workFlow"), msg);//工作流
				}
			}
			conditions = " claimno='" + claimNo + "' and underwriteflag in ('1','3')";
			List<PrpLcompensate> compensateList = this.compensateService.findByConditions(conditions);
			// 获取免赔条件
			List<PrpLdeductCond> prpLdeductCondList = new ArrayList<PrpLdeductCond>();
			if (DataUtils.emptyToNull(compensateNo) != null) {
				if (DataUtils.emptyToNull(claimNo) == null) {
					conditions = " claimno=(select ClaimNo from PrpLcompensate where compensateNo='" + compensateNo + "') and underwriteflag in ('1','3')";
					compensateList = this.compensateService.findByConditions(conditions);
				}
				queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.compensateNo", compensateNo);
				prpLdeductCondList = this.prpLdeductCondService.findPrpLdeductCond(queryRule);
				request.setAttribute("prpLdeductCondlist", prpLdeductCondList);
			}
			List<PrpDcode> prpDcodeList = this.getCodeService().getDeductCondition(riskCode);
			request.setAttribute("prpDCodeList", prpDcodeList);
			// 获取核赔审批片语和审批意见
			List<UwNotion> uwNotionList = null;
			if (claimNo == null || "".equals(claimNo)) {
				uwNotionList = (ArrayList<UwNotion>) uwNotionService.findByConditions(" claimNo=(select ClaimNo from PrpLcompensate where compensateNo='" + compensateNo + "') order by businessno,logno,lineno");
			} else {
				uwNotionList = (ArrayList<UwNotion>) uwNotionService.findByConditions(" claimNo='" + claimNo + "' order by businessno,logno,lineno");
			}
			Map<String, UwNotion> map = new HashMap<String, UwNotion>();
			for (Iterator<UwNotion> iterator = uwNotionList.iterator(); iterator.hasNext();) {
				UwNotion uwNotion = iterator.next();
				String keyString = uwNotion.getBusinessNo() + uwNotion.getId().getLogNo();
				if (map.containsKey(keyString)) {
					String handletext1 = uwNotion.getHandleText();
					String handletext2 = ((UwNotion) map.get(keyString)).getHandleText();
					uwNotion.setHandleText(handletext2 + handletext1);
					map.put(keyString, uwNotion);
				} else {
					map.put(keyString, uwNotion);
				}
			}
			Set<String> set = map.keySet();
			List<UwNotion> uwNotionList2 = new ArrayList<UwNotion>();
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				UwNotion uwNotion = (UwNotion) map.get(iterator.next());
				uwNotionList2.add(uwNotion);
			}
			request.setAttribute("uwNotionList", uwNotionList2);
			String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
			double sosMedicFee = 0.00;
			request.setAttribute("editType", editType);
			// 尚未加入type异常处理{}、其它必须参数异常处理{}
			// 1.查询立案信息,整理输入，用於初始界面显示
			//mantis：CLM0180，處理人員：DP0713，需求單編號：新核心-無法產生未處理理算核損註記確認
			System.out.println("CLM0180:editType="+editType);
			if (editType.equals("ADD")) {
				// 存在未处理的查勘或单证节点时不准许进行理算
				int count = this.getSwfLogService().getCount("(NODETYPE='check' OR NODETYPE='certi') AND NODESTATUS<4 AND flowID='" + flowID + "'");
				if (count > 0) {
					String msg = getText("prompt.compensate.notCheckOrCertify");//存在未處理的查勘或單證，不能出計算書！
					throw new UserException(1, 3, getText("query.adjustments"), msg);//理算
				}
				ClaimDto claimDto = this.claimService.findByPrimaryKey(claimNo);
				List<PrpLprepay> prpLprepayList = claimDto.getPrpLprepayList();
				// 增加接收客户索赔申请与当前时间比较结果
				boolean havePaidFlag = false;
				if (null == compensateList || compensateList.isEmpty()) {
					if (prpLprepayList != null && !prpLprepayList.isEmpty()) {
						havePaidFlag = true;
						for (PrpLprepay prpLprepay : prpLprepayList) {
							if ("5".equals(prpLprepay.getCaseType()) || "7".equals(prpLprepay.getCaseType())) {
								sosMedicFee += prpLprepay.getSumPrePaid();
							}
						}
					}
				} else {
					havePaidFlag = true;
				}

				PrpLclaim prpLclaim = claimDto.getPrpLclaim();
				DateTime startApplyPayDate = new DateTime(prpLclaim.getStartApplyPayDate());
				String passDay = "0";
				int passDayInt = 0;
				if (startApplyPayDate != null && startApplyPayDate.isEmpty() == false) {
					passDayInt = DateTime.intervalDay(startApplyPayDate, 0, new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY), 0);
					if (passDayInt > 15 && !havePaidFlag) {
						passDay = passDayInt + "";
					}
				}
				request.setAttribute("passDay", passDay);
				request.setAttribute("sosMedicFee", String.valueOf(sosMedicFee));
				// 设置缴费情况明细
				setPayCase(request, prpLclaim.getPolicyNo());
				// new 取事故责任免赔率------------------
				String policyNo = prpLclaim.getPolicyNo();
				String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
				String damageHour = prpLclaim.getDamageStartHour();
				String insuredCode = prpLclaim.getInsuredCode();
				String insuredName = prpLclaim.getInsuredName();
				PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
				List<PrpCitemKind> prpCitemKindList = null;
				List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
				PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
				String riskType = this.codeService.translateRiskCodetoRiskType(riskCode);
				if(ConstantCodes.CLASSCODE_E.equals(riskType)){
					prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
				} else {
					prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
				}
				PolicyDto policyDto = new PolicyDto();
				policyDto.setPrpCitemKindList(prpCitemKindList);
				policyDto.setPrpCmain(prpCmain);
				// 取强制保单限额----------------------------------------------
				Map<String, Double> limitMap = new HashMap<String, Double>();
				if ("RISKCODE_DAZ".equals(configCode)) {
					List<PrpClimit> limitList = this.prpClimitService.findPrpClimit(" policyNo='" + prpLclaim.getPolicyNo() + "'", new DateTime(prpLclaim.getDamageStartDate()).toString(), new DateTime(prpLclaim.getStartDate()).toString());
					if (limitList != null && limitList.size() > 0) {
						for (PrpClimit prpClimit : limitList) {
							limitMap.put(prpClimit.getId().getLimitType(), prpClimit.getLimitFee());
						}
					}
				}
				request.setAttribute("limitMap", limitMap);
				request.setAttribute("configCode", configCode);

				// 关於调查的判断
				conditions = " businessno='" + registNo + "' and nodeType='check' and (nodeStatus in ('0','1','2','3'))";
				List<SwfLog> pageRecord = this.getWorkFlowService().findNodesByConditions(conditions);
				if (pageRecord.size() > 1) {
					request.setAttribute("checkFlag12", "N");
				} else {
					request.setAttribute("checkFlag12", "Y");
				}
				// 只有正常实赔才判断定损的
				// 没有定损的案件不能进入实赔理算
				int conditionFlag = 0;
				if (!"3".equals(caseType) && !"4".equals(caseType)) {
					conditionFlag = this.sunnyCompensateViewHelper.checkCondition(request, claimNo);
				}

				if (DataUtils.emptyToNull(riskCode) == null) {
					riskCode = BusinessRuleUtil.getRiskCode(claimNo, "ClaimNo");
				}
				// 如果不是特殊陪案，需要进行验证
				this.clearErrorsAndMessages();
				//mantis：CLM0180，處理人員：DP0713，需求單編號：新核心-無法產生未處理理算核損註記確認
				System.out.println("CLM0180:caseType="+caseType+"/"+DataUtils.emptyToNull(caseType));
				if (DataUtils.emptyToNull(caseType) == null) {
					if (conditionFlag == 1) {
						throw new UserException(1, 3, "理算", super.getText("title.compensateEidt.notClaim"));//理算
					}
					// 有预赔的案件复核不通过的案件不能进入实赔理算
					boolean prepayFlag = this.sunnyCompensateViewHelper.checkPrepay(request, claimNo);
					if (!prepayFlag) {
						throw new UserException(1, 3, "理算", super.getText("title.compensateEidt.notPrepay"));//理算
					}
					//mantis：CLM0180，處理人員：DP0713，需求單編號：新核心-無法產生未處理理算核損註記確認 START
					System.out.println("CLM0180:strRiskType="+strRiskType);
					// 核损不通过的案件不能进入实赔理算
					if(prpLclaim.getRiskCode().equals("A01")|| prpLclaim.getRiskCode().equals("B01")){
						System.out.println("CLM0180:riskcode="+prpLclaim.getRiskCode()+"/simpleFlag="+prpLclaim.getSimpleFlag());
						if("D".equals(strRiskType) && (prpLclaim.getSimpleFlag().isEmpty() ||!prpLclaim.getSimpleFlag().equals("1"))){
							boolean verifyFlag = this.sunnyCompensateViewHelper.checkVerifyLoss(request, claimNo);
							if (!verifyFlag) {
								throw new UserException(1, 3, "理算", super.getText("title.compensateEidt.notVertify"));
							}
						}
					} else 
					if ("D".equals(strRiskType)) {
						boolean verifyFlag = this.sunnyCompensateViewHelper.checkVerifyLoss(request, claimNo);
						if (!verifyFlag) {
							throw new UserException(1, 3, "理算", super.getText("title.compensateEidt.notVertify"));
						}
					}
					//mantis：CLM0180，處理人員：DP0713，需求單編號：新核心-無法產生未處理理算核損註記確認 END
				}
				// 车险 和 非车 计算书 走不同 ViewHelper
				if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
					// 取车险限额---------------------------
//					Map<String, Double> amountMap = new HashMap<String, Double>();
//					List<PrpCitemKind> prpCitemKindList = policyDto.getPrpCitemKindList();
//					if (prpCitemKindList != null && prpCitemKindList.size() > 0) {
//						PrpCitemKind prpCitemKind = null;
//						for (Iterator<PrpCitemKind> it = prpCitemKindList.iterator(); it.hasNext();) {
//							prpCitemKind = it.next();
//							amountMap.put(prpCitemKind.getKindCode(), prpCitemKind.getAmount());
//						}
//					}
//					request.setAttribute("amountMap", amountMap);
					this.avoidUpdateSampCompe(request); // 为防止两个人同时操作同一个待处理的理算任务，临时写了实赔结点
					this.sunnyCompensateViewHelper.claimDtoToView(request, claimNo, editType);
					//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒
					this.setCloseBetween(request,prpCmain);
					this.setCarKindCode(request, riskCode);
				} else {
					this.avoidUpdateSampCompe(request); // 为防止两个人同时操作同一个待处理的理算任务，临时写了实赔结点
					this.accidentCompensateViewHelper.claimDtoToView(request, claimNo, editType);
					CompensateLimitViewHelper.getInstance().setLimitInfo(policyDto, prpLclaim, request);//限额控制
				}
				// 取限额
				Map<String, Double> amountMap = new HashMap<String, Double>();
				// 财产险取限额
				Map<String, Double> amountProp = new HashMap<String, Double>();
//				List<PrpCitemKind> prpCitemKindList = policyDto.getPrpCitemKindList();
				if (!CommonUtils.isEmpty(prpCitemKindList)) {
					PrpCitemKind prpCitemKind = null;
					for (Iterator<PrpCitemKind> it = prpCitemKindList.iterator(); it.hasNext();) {
						prpCitemKind = it.next();
						amountMap.put(prpCitemKind.getKindCode(), prpCitemKind.getAmount());
						amountProp.put(prpCitemKind.getItemCode(), prpCitemKind.getAmount());
					}
				}
				request.setAttribute("amountMap", amountMap);
				request.setAttribute("amountProp", amountProp);
				request.setAttribute("propFlag", strRiskType);
				//可以互沖的計算書
				List<String> mutualCompensateNoList = new ArrayList<String>();
				if(!"D".equals(chargeType)) {//强制险延迟利息不显示理算互冲计算书。
					mutualCompensateNoList = this.compensateService.getMutualCompensateNo(claimNo);
				}
				request.setAttribute("mutualCompensateNoList", mutualCompensateNoList);
				request.setAttribute("prpLclaim", prpLclaim);
				

				//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String todayStr = sdf.format(new Date());
				if(null!=prpLclaim && null!=prpLclaim.getDamageStartDate()){
					todayStr = sdf.format(prpLclaim.getDamageStartDate());//出險日
				}
				
				List<PrpDpolicyRules> prpDpolicyRulesList = this.prpDpolicyRulesService.findByConditions
						(" codeCode in ('901','902','903') AND codetype ='CountryCode_CTN' " +
						" AND startdate <= TO_DATE('"+todayStr+"', 'YYYY-MM-DD') AND (enddate > TO_DATE('"+todayStr+"', 'YYYY-MM-DD')  OR enddate IS NULL)");
				request.setAttribute("prpDpolicyRulesList", prpDpolicyRulesList);//span_prpDpolicyRules
				//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END

				//mantis：CLM0292 ，處理人員：DP0713，需求單編號：新核心-日額保險金額卡控 START  應該不是裡算 START
				String statements = "select p.payType, p.accident, p.KINDCODE, p.KINDCNAME " +
	                    "from ccicpms.prpDclauseKind p " +
	                    "WHERE REGEXP_LIKE(p.KINDCODE, '^(GA|PA|GZ)') " +
	                    "AND p.payType = '1' " +
	                    "AND p.ACCIDENT IS NOT NULL " +
	                    "ORDER BY p.KINDCODE";
				System.out.println("SQL: [" + statements + "]");
				List<Object[]> list = (List<Object[]>) this.commonService.findByStatements(statements);
				List<Map> prpDclauseKindList = new ArrayList<Map>();
				if (null != list && list.size() > 0) {
				    for (Object[] data : list) {
				        if (null != data && data.length >= 4) {
				            Map prpDclauseKindMap = new HashMap();
				            prpDclauseKindMap.put("payType",   data[0]);
				            prpDclauseKindMap.put("accident",  data[1]);
				            prpDclauseKindMap.put("kindCode",  data[2]);
				            prpDclauseKindMap.put("kindCName", data[3]);
				            prpDclauseKindList.add(prpDclauseKindMap);
				        }
				    }
				}
				request.setAttribute("prpDclauseKindList", prpDclauseKindList);
				//mantis：CLM0292 ，處理人員：DP0713，需求單編號：新核心-日額保險金額卡控 START  應該不是裡算 END
			}
			// 2.修改和查询显示的过程
			if ("EDIT".equals(editType) || "SHOW".equals(editType) || "DELETE".equals(editType)) {
				// 查询实赔信息,整理输入，用於初始界面显示
				CompensateDto compensateDto = this.compensateService.findByPrimaryKey(compensateNo, caseType);
				PrpLcompensate prpLcompensate = null;
				if (compensateDto != null) {
					//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START
					List<PrpLloss> prpLlossList = compensateDto.getPrpLlossList();
					if(null!=prpLlossList && prpLlossList.size()>0){
						for(PrpLloss prpLloss:prpLlossList){
							// mantis：CLM0219，處理人員：DP0714，新核心-理算暫存功能異常(未處理理算)
							if(StringUtils.isNotBlank(prpLloss.getKindCode()) && prpLloss.getKindCode().equals("07")){
								prpLloss.setDeductible(0);
								prpLloss.setDeductiblerate(0);
							}
						}
					}
					//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END
					prpLcompensate = compensateDto.getPrpLcompensate();
					if (compensateDto.getPrpLcompensate() == null) {
						//計算書		"計算書信息不存在！"
						throw new UserException(0, 0, getText("check.calculation"), getText("prompt.compensate.pagesNotExist"));
					}
					claimNo = prpLcompensate.getClaimNo().trim();
					//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
					//理算|正在理算  取住院天數
					//加總 住院天數
					String conditions_forCompensateHis = "compensateNo like 'C"+claimNo+"%' order by compensateNo ";
					List<PrpLcompensate> PrpLcompensateHis = this.compensateService.findByConditions(conditions_forCompensateHis);

					int sumHospitalizedDay = 0;
					String tailCompensateNo = "";//本案計算書號尾數
					if(prpLcompensate.getCompensateNo().indexOf("C"+claimNo)!=-1){
						tailCompensateNo = prpLcompensate.getCompensateNo().replace("C"+claimNo, "");
					}
					for(PrpLcompensate compensateHis :PrpLcompensateHis){
						if("1".equals(compensateHis.getUnderWriteFlag()) || "3".equals(compensateHis.getUnderWriteFlag())){
							//本次的 住院天數
							if(prpLcompensate.getCompensateNo().equals(compensateHis.getCompensateNo())){
								//compensateHis.getPolicyNo();
								prpLcompensate.setHospitalizedDays(compensateHis.getHospitalizedDays());
							}
	
							String tailCompensateNoHit="";
							if(compensateHis.getCompensateNo().indexOf("C"+claimNo)!=-1){
								tailCompensateNoHit = compensateHis.getCompensateNo().replace("C"+claimNo, "");
							}
							
							if(Integer.parseInt(tailCompensateNoHit,10)<Integer.parseInt(tailCompensateNo,10)){//當前這筆及大於的  都不要計算至累加天
								if(null!=compensateHis.getHospitalizedDays()){
									sumHospitalizedDay+=compensateHis.getHospitalizedDays();
									
									double paf4SumLoss = 0.0;
									String conditions_forPrpLpersonLoss = 
											" 	 COMPENSATENO = '" + compensateHis.getCompensateNo() 
											+ "' and POLICYNO = '"+compensateHis.getPolicyNo()
											+ "' and KINDCODE = 'PAF4'";
									List<PrpLpersonLoss> prpLpersonLoss = PrpLpersonLossService.findByConditions(conditions_forPrpLpersonLoss);
									for(PrpLpersonLoss pll:prpLpersonLoss){
										paf4SumLoss+=pll.getSumLoss();
									}
									prpLcompensate.setPaf4SumLoss(paf4SumLoss);
								}
							}
						}
					}
					prpLcompensate.setSumHospitalizedDay(sumHospitalizedDay);//本次事故累計住院天數(不含本次)
					//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
					
					
					request.setAttribute("prpLcompensate", prpLcompensate);
					ClaimDto claimDto = this.claimService.findByPrimaryKey(claimNo);
					// 增加对简易赔案的判断
					UIQuickCaseAction uiQuickCaseAction = new UIQuickCaseAction();
					PrpLclaim prpLclaim = claimDto.getPrpLclaim();
					// UIQuickCaseAction有连接需要修改
					// 後续程序是否执行，赠加控制
					boolean blFwd = uiQuickCaseAction.checkQuickCaseAndForwadToSHOW(prpLclaim.getRegistNo(), response);
					if (blFwd) {
						return NONE;
					}
					// 预赔处理，第一个暂存理算和查看时显示预赔
					if (((null == compensateList || compensateList.isEmpty()) && "EDIT".equals(editType)) || "SHOW".equals(editType) || "DELETE".equals(editType)) {
						List<PrpLprepay> prpLprepayList = claimDto.getPrpLprepayList();
						if (prpLprepayList != null && prpLprepayList.size() > 0) {
							for (PrpLprepay prpLprepay : prpLprepayList) {
								if ("5".equals(prpLprepay.getCaseType()) || "7".equals(prpLprepay.getCaseType())) {
									sosMedicFee += prpLprepay.getSumPrePaid();
								}
							}
						}
					}
					request.setAttribute("sosMedicFee", String.valueOf(sosMedicFee));
					// 增加接收客户索赔申请与当前时间比较结果 begin
					boolean havePaidFlag = false;
					if (null == compensateList || compensateList.size() <= 0) {
						List<PrpLprepay> prpLprepayList = claimDto.getPrpLprepayList();
						if (prpLprepayList != null && prpLprepayList.size() <= 0) {
						} else {
							havePaidFlag = true;
						}
					} else {
						havePaidFlag = true;
					}
					DateTime startApplyPayDate = new DateTime(prpLclaim.getStartApplyPayDate());
					String passDay = "0";
					int passDayInt = 0;
					if (startApplyPayDate != null && startApplyPayDate.isEmpty() == false) {
						passDayInt = DateTime.intervalDay(startApplyPayDate, 0, new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY), 0);
						if (passDayInt > 15 && !havePaidFlag) {
							passDay = passDayInt + "";
						}
					}
					request.setAttribute("passDay", passDay);
					// 设置缴费情况明细
					this.setPayCase(request, prpLclaim.getPolicyNo());
					// 取事故责任免赔率------------------
//					PolicyDto policyDto = this.getEndorseViewHelper().findForEndorBefore(prpLclaim.getPolicyNo(), new DateTime(prpLclaim.getDamageStartDate()).toString(), prpLclaim.getDamageStartHour());
					// 取强制保单限额----------------------------------------------
					String policyNo = prpLclaim.getPolicyNo();
					String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
					String damageHour = prpLclaim.getDamageStartHour();
					String insuredCode = prpLclaim.getInsuredCode();
					String insuredName = prpLclaim.getInsuredName();
					PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
					Map<String, Double> limitMap = new HashMap<String, Double>();
					if ("RISKCODE_DAZ".equals(configCode)) {
						List<PrpClimit> limitList = this.prpClimitService.findPrpClimit(" policyNo='" + prpLclaim.getPolicyNo() + "'", new DateTime(prpLclaim.getDamageStartDate()).toString(), new DateTime(prpCmain.getStartDate())
								.toString());
						if (limitList != null && limitList.size() > 0) {
							for (PrpClimit prpClimit : limitList) {
								limitMap.put(prpClimit.getId().getLimitType(), prpClimit.getLimitFee());
							}
						}
					}
					request.setAttribute("limitMap", limitMap);
					request.setAttribute("configCode", configCode);
					// 车险 和 非车 计算书 走不同 ViewHelper
					if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
						this.sunnyCompensateViewHelper.compensateDtoView(request, compensateNo, editType);
						//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒
						this.setCloseBetween(request, prpCmain);
						this.setCarKindCode(request, riskCode);
					} else {
						this.accidentCompensateViewHelper.compensateDtoView(request, compensateNo, editType);
						if ("EDIT".equals(editType)) {
							List<PrpCitemKind> prpCitemKindList = null;
							List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
							PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
							String riskType = this.codeService.translateRiskCodetoRiskType(riskCode);
							if(ConstantCodes.CLASSCODE_E.equals(riskType)){
								prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
							} else {
								prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
							}
							PolicyDto policyDto = new PolicyDto();
							policyDto.setPrpCitemKindList(prpCitemKindList);
							policyDto.setPrpCmain(prpCmain);
							CompensateLimitViewHelper.getInstance().setLimitInfo(policyDto, prpLclaim, request);//限额控制
							
							//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
							request.setAttribute("prpCitemKindList", prpCitemKindList);
						}
					}
					if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
						riskCode = BusinessRuleUtil.getRiskCode(compensateNo, "CompensateNo");
					}
					

					//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 STARTSimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String todayStr = sdf.format(new Date());
					if(null!=prpLclaim && null!=prpLclaim.getDamageStartDate()){
						todayStr = sdf.format(prpLclaim.getDamageStartDate());//出險日
					}
					List<PrpDpolicyRules> prpDpolicyRulesList = this.prpDpolicyRulesService.findByConditions
							(" codeCode in ('901','902','903') AND codetype ='CountryCode_CTN' " +
							" AND startdate <= TO_DATE('"+todayStr+"', 'YYYY-MM-DD') AND (enddate > TO_DATE('"+todayStr+"', 'YYYY-MM-DD')  OR enddate IS NULL)");
					request.setAttribute("prpDpolicyRulesList", prpDpolicyRulesList);//span_prpDpolicyRules
					//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
				}
				request.setAttribute("prpLclaim", compensateDto.getPrpLclaim());
			}
			// 免赔率选择
			if ("RECHOSE".equals(editType)) {
				double dblDeductibleRate = 0;
				double dblDutyDeductibleRate = 0;
				List<String> it = new ArrayList<String>(3);
				PrpCitemCar prpCitemCar = new PrpCitemCar();
				PrpCitemKind prpCitemKind = new PrpCitemKind();
				PrpCmain prpCmain = new PrpCmain();
				List<PrpCitemKind> prpCitemKindlist = new ArrayList<PrpCitemKind>();
				ClaimDto claimDto = this.claimService.findByPrimaryKey(claimNo);
				PrpLclaim prpLclaim = claimDto.getPrpLclaim();
				String policyNo = prpLclaim.getPolicyNo();

				UIDeductCondAction uiDeductCondAction = UIDeductCondAction.getInstance();
				if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
					// 取得出险时保单的信息
					String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
					String damageHour = prpLclaim.getDamageStartHour();
					List<PrpCitemCar> prpCitemCarList = this.endorseViewHelper.findPrpCitemCar(policyNo, damageDate, damageHour);
					if (!CommonUtils.isEmpty(prpCitemCarList)) {
						prpCitemCar = prpCitemCarList.get(0);
						prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
						prpCitemKindlist = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
					}
					PrpLcompensate prpLcompensate = new PrpLcompensate();
					prpLcompensate.setPrpLdeductCondList(UIDeductCondAction.getInstance().getDeductCondList(request, false));
					request.setAttribute("prpLcompensate", prpLcompensate);

					String kindCode = "";
					if (prpCitemKindlist.size() > 0) {
						for (int k = 0; k < prpCitemKindlist.size(); k++) {
							prpCitemKind = prpCitemKindlist.get(k);
							kindCode = prpCitemKind.getKindCode();
							// 事故责任免赔率
							if (prpCmain.getOperateDate() == null) {
								throw new UserException(0, 0, "保單生效期爲空");
							}
							dblDutyDeductibleRate = uiDeductCondAction.getDeductibleRateOfAccident(riskCode, kindCode, prpLclaim.getIndemnityDuty(), "0", prpCitemCar.getClauseType(), prpCitemCarList.get(0).toString());
							// 绝对免赔率
							ExceptDeductibleRateDto exceptDeductibleRateDto = uiDeductCondAction.getDeductibleRateOfAbsolute(prpCitemCar.getClauseType(), kindCode, prpLdeductCondList, riskCode, prpCitemCarList.get(0).toString());
							dblDeductibleRate = exceptDeductibleRateDto.getDeductibleRate();
							it.add(0, kindCode);
							it.add(1, String.valueOf(dblDutyDeductibleRate));
							it.add(2, String.valueOf(dblDeductibleRate));
						}// end for
					}// end if
					request.setAttribute("it", it);
					return "RECHOSE";
				}
			}
			// 免赔率选择
			// 3。如果是SHOW类型的，目前和EDIT用同一个目的jsp所以
			if (editType.equals("Approve")) {
				// 查询实赔信息,整理输入，用於初始界面显示
				// 车险 和 非车 计算书 走不同 ViewHelper
				if ("D".equals(strRiskType)) {
					this.sunnyCompensateViewHelper.compensateDtoView(request, compensateNo, editType);
				} else {
					this.accidentCompensateViewHelper.compensateDtoView(request, compensateNo, editType);
				}
				return forward;
			}
			// 未处理理算任务的放弃处理
			if (editType.equals("GIVUP")) {
				this.giveUpLockCompe(request);
				this.clearErrorsAndMessages();
				this.addActionMessage(super.getText("prompt.compensate.giveup"));
				return SUCCESS;
			}
			// 取得forward
			forward = BusinessRuleUtil.getForward(request, riskCode, "compe", editType, 1);
			// 强三 ---start
			if ("RISKCODE_DAZ".equals(configCode) && editType.equals("ADD")) {
				forward = editType + "BZ";
			}
			if ("RISKCODE_DAZ".equals(configCode) && (editType.equals("EDIT") || editType.equals("SHOW"))) {
				forward = "EDITBZ";
			}
			if (user == null) {
				user = new UserDto();
			}
			user.setRiskCode(riskCode);
			request.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return forward;
	}

	//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 - start
	private void setCloseBetween(HttpServletRequest request, PrpCmain prpCmain) {
		PrpLcompensate prpLcompensate = (PrpLcompensate)request.getAttribute("prpLcompensate");
		Calendar date = Calendar.getInstance();
	    date.setTime(prpCmain.getEndDate());
	    date.add(Calendar.DATE, -93);
	    //是否在閉鎖期內理算
		if(isEffectiveDate(prpLcompensate.getInputDate(),date.getTime(),prpCmain.getEndDate())){
			request.setAttribute("isCloseBetween", true);
		}
	}
	private boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
	    if (nowTime.getTime() == startTime.getTime()
	            || nowTime.getTime() == endTime.getTime()) {
	        return true;
	    }

	    Calendar date = Calendar.getInstance();
	    date.setTime(nowTime);

	    Calendar begin = Calendar.getInstance();
	    begin.setTime(startTime);

	    Calendar end = Calendar.getInstance();
	    end.setTime(endTime);

	    if (date.after(begin) && date.before(end)) {
	        return true;
	    } else {
	        return false;
	    }
	}
	//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 - end

	/**
	 * 设置缴费情况(是否缴费以及缴费情况)
	 * @param httpServletRequest
	 * @param policyNo
	 * @throws Exception
	 */
	private void setPayCase(HttpServletRequest httpServletRequest, String policyNo) throws Exception {
		String conditions = " policyno = '" + policyNo + "'";
		// 获得缴费情况
		int intReturn = 0;
		intReturn = this.getPolicyService().checkPay(conditions);// -1为未缴费，0为未缴全，1为缴全
		String strPayFlag = String.valueOf(intReturn);
		httpServletRequest.setAttribute("payFlag", strPayFlag);

		// 当缴费不足时,要显示相应的缴费情况
		PrpCmain cmainDto = this.getPolicyService().findPrpCmainDtoByPrimaryKey(policyNo);
		// 欠费情况
		String delinquentfeeCase = "";
		// 若费用未缴全,则针对分期付款的情况要提示哪几期费用未缴
		if (intReturn == 0 && cmainDto.getPayTimes() > 1) {
			delinquentfeeCase = this.daaRegistViewHelper.getDelinquentfeeCase(cmainDto);
		}
		// 设置分期付款未缴期数
		httpServletRequest.setAttribute("delinquentfeeCase", delinquentfeeCase);
		// 当缴费不足时,要显示相应的缴费情况
		PrpLregist prpLregist = new PrpLregist();
		prpLregist.setPayFlag(intReturn + "");
		httpServletRequest.setAttribute("prpLregist", prpLregist);
	}

	/**
	 * 为防止两个人同时操作同一个待处理的理算任务，临时写了实赔结点
	 * @param request
	 * @throws Exception
	 */
	private void avoidUpdateSampCompe(HttpServletRequest request) throws Exception {
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		// 为防止两个人同时操作同一个待处理的理算任务，临时写了实赔结点
		String FlowID = request.getParameter("swfLogFlowID");
		int LogNo = Integer.parseInt((String) request.getParameter("swfLogLogNo"));
		SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(FlowID, LogNo);
		if (swfLogDto != null) {
			if ("compe".equals(swfLogDto.getNodeType())) {
				swfLogDto.setHandlerCode(user.getUserCode());
				swfLogDto.setHandlerName(user.getUserName());
			}
			this.getWorkFlowService().updateFlow(swfLogDto);
			// 如果是车险的话，需要判断是否前面的节点都已经处理完毕
			if ("D".equals(ConstantCodes.carClassMap.get(this.getCodeService().translateClassCodeByRiskCode(swfLogDto.getRiskCode())))) {
				// 检查前面是否有没做完的节点
				this.getWorkFlowViewHelper().checkNodeSubmit(swfLogDto);
			}
		}
	}

	/**
	 * 放弃未暂存和提交的理算任务，删去临时写的实赔结点操作人，使其他人可见可处理
	 * @param request
	 * @throws Exception
	 */
	private void giveUpLockCompe(HttpServletRequest request) throws Exception {
		// 放弃未暂存和提交的理算任务，删去临时写的实赔结点操作人，使其他人可见可处理
		String FlowID = request.getParameter("swfLogFlowID");
		int LogNo = Integer.parseInt((String) request.getParameter("swfLogLogNo"));
		SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(FlowID, LogNo);
		if (swfLogDto.getNodeType().equals("compe")) {
			swfLogDto.setHandlerCode("");
			swfLogDto.setHandlerName("");
		}
		this.getWorkFlowService().updateFlow(swfLogDto);
	}

	/***
	 * 選擇互沖號碼以後的預備工作，將互沖計算書的內容帶出
	 * @return
	 * @throws Exception 
	 */
	public String beforeCompeMutualImpulse() throws Exception{
		HttpServletRequest request = super.getRequest();
		String claimNo = request.getParameter("ClaimNo"); // 赔案号
		String compensateNo = request.getParameter("prpLcompensateMutualCompensateNo"); //本次要沖抵的計算書號碼
		String riskCode = request.getParameter("riskCode");// 险种
		String forward = ""; // 向前
		String swfLogFlowID = request.getParameter("swfLogFlowID");
		String swfLogLogNo = request.getParameter("swfLogLogNo");
		String businessNo = request.getParameter("businessNo");
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		// 增被保险人联系电话
		String registNo = this.getCodeService().translateBusinessCode(claimNo, false);
		List<PrpLcheck> checkList = this.prpLcheckService.findPrpLcheck(QueryRule.getInstance().addSql(" registNo='" + registNo + "'"));
		PrpLcheck prpLcheck = new PrpLcheck();
		prpLcheck.setCheckList(checkList);
		request.setAttribute("prpLcheck", prpLcheck);
		// 进行占号分析，只有当有工作流号码和LogNo的时候才能使用。
		String flowID = swfLogFlowID;
		String logNo = swfLogLogNo;
		if (DataUtils.emptyToNull(flowID) != null && DataUtils.emptyToNull(logNo) != null && !"SHOW".equalsIgnoreCase(editType)) {
			SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
			if (swfLogDto.getHoldNode() == false) {
				String msg = "案件'" + businessNo + "'已經被代碼:'" + swfLogDto.getHandlerCode() + "',名稱:'" + swfLogDto.getHandlerName() + "'的用戶所占用,請選擇其它案件進行處理!";
				throw new UserException(1, 3, "工作流", msg);
			}
		}
		String conditions = "";
		List<PrpDcode> prpDcodeList = this.getCodeService().getDeductCondition(riskCode);
		request.setAttribute("prpDCodeList", prpDcodeList);
		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
		double sosMedicFee = 0.00;
		request.setAttribute("editType", editType);
		ClaimDto claimDto = this.claimService.findByPrimaryKey(claimNo);
		PrpLclaim prpLclaim = claimDto.getPrpLclaim();
		String passDay = "0";
		request.setAttribute("passDay", passDay);
		request.setAttribute("sosMedicFee", String.valueOf(sosMedicFee));
		// 设置缴费情况明细
		setPayCase(request, prpLclaim.getPolicyNo());
		// 取强制保单限额----------------------------------------------
		String configCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
		request.setAttribute("configCode", configCode);
		// 关於调查的判断
		conditions = " businessno='" + registNo + "' and nodeType='check' and (nodeStatus in ('0','1','2','3'))";
		List<SwfLog> pageRecord = this.getWorkFlowService().findNodesByConditions(conditions);
		if (pageRecord.size() > 1) {
			request.setAttribute("checkFlag12", "N");
		} else {
			request.setAttribute("checkFlag12", "Y");
		}
		if (DataUtils.emptyToNull(riskCode) == null) {
			riskCode = BusinessRuleUtil.getRiskCode(claimNo, "ClaimNo");
		}
		if ("D".equals(strRiskType)) {
			this.sunnyCompensateViewHelper.compensateDtoView(request, compensateNo, editType);
			this.setCarKindCode(request, riskCode);
		} else {
			this.accidentCompensateViewHelper.compensateDtoView(request, compensateNo, editType);
		}
		request.setAttribute("prpLclaim", prpLclaim);
		//互沖處理
		PrpLcompensate prpLcompensate = (PrpLcompensate)request.getAttribute("prpLcompensate");
		prpLcompensate.setCompensateNo("");//原計算書的內容置空
		prpLcompensate.setSumDutyPaid(this.getAntiAmount(prpLcompensate.getSumDutyPaid()));
		prpLcompensate.setSumNoDutyFee(this.getAntiAmount(prpLcompensate.getSumNoDutyFee()));
		prpLcompensate.setSumThisPaid(this.getAntiAmount(prpLcompensate.getSumThisPaid()));
		prpLcompensate.setSumPaid(this.getAntiAmount(prpLcompensate.getSumPaid()));
		prpLcompensate.setSumPaidAll(this.getAntiAmount(prpLcompensate.getSumPaidAll()));
		//聯共保部份如何處理待定
		PrpLloss prpLloss = (PrpLloss)request.getAttribute("prpLloss");
		List<PrpLloss> prpLlossList = prpLloss.getPrpLlossList();
		if(prpLlossList!=null && !prpLlossList.isEmpty()){//標的部份
			for(PrpLloss temp : prpLlossList){
				temp.setSumRealPay(this.getAntiAmount(temp.getSumRealPay()));
				temp.setPayObjectSerialNo(this.getAntiPayObjectSerialNo(temp.getPayObjectSerialNo()));
			}
		}
		PrpLpersonLoss prpLpersonLoss = (PrpLpersonLoss)request.getAttribute("prpLpersonLoss");
		List<PrpLpersonLoss> prpLpersonLossList = prpLpersonLoss.getPrpLpersonLossList();
		if(prpLpersonLossList!=null && !prpLpersonLossList.isEmpty()){//人傷部份
			for(PrpLpersonLoss temp : prpLpersonLossList){
				temp.setSumRealPay(this.getAntiAmount(temp.getSumRealPay()));
				if ("RISKCODE_DAZ".equals(configCode)) {
					temp.setSumDefPay(this.getAntiAmount(temp.getSumDefPay()));
				}
				temp.setPayObjectSerialNo(this.getAntiPayObjectSerialNo(temp.getPayObjectSerialNo()));
			}
		}
		PrpLpayObjectInfo prpLpayObjectInfo = (PrpLpayObjectInfo)request.getAttribute("prpLpayObjectInfo");
		List<PrpLpayObjectInfo> prpLpayObjectInfoList = prpLpayObjectInfo.getPrpLpayObjectInfoList();
		if(prpLpayObjectInfoList!=null && !prpLpayObjectInfoList.isEmpty()){//賠付對象部份
			for(PrpLpayObjectInfo temp : prpLpayObjectInfoList){
				temp.setPayAmount(this.getAntiAmount(temp.getPayAmount()));
			}
		}
		PrpLcharge prpLcharge = (PrpLcharge)request.getAttribute("prpLcharge");
		List<PrpLcharge> prpLchargeList = prpLcharge.getPrpLchargeList();
		if(prpLchargeList!=null && !prpLchargeList.isEmpty()){//賠付對象部份
			for(PrpLcharge temp : prpLchargeList){
				temp.setChargeAmount(this.getAntiAmount(temp.getChargeAmount()));
			}
		}
		request.setAttribute("propFlag", strRiskType);
		// 可以互沖的計算書
		List<String> mutualCompensateNoList = this.compensateService.getMutualCompensateNo(claimNo);
		request.setAttribute("mutualCompensateNoList", mutualCompensateNoList);
		// 取得forward
		forward = BusinessRuleUtil.getForward(request, riskCode, "compe", editType, 1);
		String strConfigCode = this.getCodeService().translateRiskCodetoConfigCode(riskCode);
		if ("RISKCODE_DAZ".equals(strConfigCode) && editType.equals("ADD")) {
			forward = editType + "BZ";
		}
		if ("RISKCODE_DAZ".equals(strConfigCode) && (editType.equals("EDIT") || editType.equals("SHOW"))) {
			forward = "EDITBZ";
		}
		if (user == null) {
			user = new UserDto();
		}
		user.setRiskCode(riskCode);
		request.setAttribute("user", user);
		return forward;
	}
	
	/***
	 * 獲取各項置反的賠付對象訊息，金額取反
	 * @param currSerialNo
	 * @return
	 */
	public String getAntiPayObjectSerialNo(String currSerialNo){
		String tempStr = "";
		if(DataUtils.emptyToNull(currSerialNo)!=null){
			DecimalFormat df=new DecimalFormat("#");  
			String[] payObject = currSerialNo.split(";");
			String str = null;
			for(int i =0;i < payObject.length;i++){
				str = payObject[i];
				tempStr += ";";
				if(str.endsWith(":0")){
					tempStr += str;
				}else{
					String[] temp = str.split(":");
					tempStr +=temp[0]+":"+df.format(Double.parseDouble(temp[1])*(-1));
				}
			}
			return tempStr.substring(1);
		}
		return tempStr;
	}
	
	/***
	 * 金額取負
	 * @param amount
	 * @return
	 */
	public double getAntiAmount(double amount){
		if(amount==0){
			return 0;
		}
		return -1*amount;
	}
	/***
	 * 設置車體險責任險險種
	 * @param request
	 * @param riskCode
	 */
	private void setCarKindCode(HttpServletRequest request , String riskCode){
		List<String> carKindCode = this.codeService.getResponKindCode(1);//車體險險種
		String[] str = new String[carKindCode.size()];
		request.setAttribute("CarKindCode", CommonUtils.join(carKindCode.toArray(str), ","));
		List<String> propKindCode = this.codeService.getResponKindCode(0);//責任險險種
		str = new String[propKindCode.size()];
		request.setAttribute("PropKindCode", CommonUtils.join(propKindCode.toArray(str), ","));
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLdeductCondService getPrpLdeductCondService() {
		return prpLdeductCondService;
	}

	public void setPrpLdeductCondService(PrpLdeductCondService prpLdeductCondService) {
		this.prpLdeductCondService = prpLdeductCondService;
	}

	public SunnyCompensateViewHelper getSunnyCompensateViewHelper() {
		return sunnyCompensateViewHelper;
	}

	public void setSunnyCompensateViewHelper(SunnyCompensateViewHelper sunnyCompensateViewHelper) {
		this.sunnyCompensateViewHelper = sunnyCompensateViewHelper;
	}

	public PrpClimitService getPrpClimitService() {
		return prpClimitService;
	}

	public void setPrpClimitService(PrpClimitService prpClimitService) {
		this.prpClimitService = prpClimitService;
	}

	public AccidentCompensateViewHelper getAccidentCompensateViewHelper() {
		return accidentCompensateViewHelper;
	}

	public void setAccidentCompensateViewHelper(AccidentCompensateViewHelper accidentCompensateViewHelper) {
		this.accidentCompensateViewHelper = accidentCompensateViewHelper;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public PrepayService getPrepayService() {
		return prepayService;
	}

	public void setPrepayService(PrepayService prepayService) {
		this.prepayService = prepayService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
	}

	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public WorkFlowService getWorkFlowService() {
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public String getCoreURL() {
		return coreURL;
	}

	public void setCoreURL(String coreURL) {
		this.coreURL = coreURL;
	}
	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
	public PrpLpersonLossService getPrpLpersonLossService() {
		return PrpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		PrpLpersonLossService = prpLpersonLossService;
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
	public PrpDpolicyRulesService getPrpDpolicyRulesService() {
		return prpDpolicyRulesService;
	}

	public void setPrpDpolicyRulesService(
			PrpDpolicyRulesService prpDpolicyRulesService) {
		this.prpDpolicyRulesService = prpDpolicyRulesService;
	}
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	public String getIsCompulsoryBchainClaimDisabled() {
		return isCompulsoryBchainClaimDisabled;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public void setIsCompulsoryBchainClaimDisabled(
			String isCompulsoryBchainClaimDisabled) {
		this.isCompulsoryBchainClaimDisabled = isCompulsoryBchainClaimDisabled;
	}

	public String getRegistSharingFlagDisabled() {
		return registSharingFlagDisabled;
	}

	public void setRegistSharingFlagDisabled(String registSharingFlagDisabled) {
		this.registSharingFlagDisabled = registSharingFlagDisabled;
	}

	public VehicleClaimApiLogService getVehicleClaimApiLogService() {
		return vehicleClaimApiLogService;
	}

	public void setVehicleClaimApiLogService(
			VehicleClaimApiLogService vehicleClaimApiLogService) {
		this.vehicleClaimApiLogService = vehicleClaimApiLogService;
	}	
	
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
	
}
