package com.sinosoft.claim.claim.web;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;//mantis： CLM0231，處理人員：DP0706，需求單編號：CLM0231.新核心-傷害險高保額新商品檢核
import ins.framework.web.Struts2Action;

//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.claim.util.DAAClaimViewHelper;
import com.sinosoft.claim.claim.vo.ClaimDto;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.service.facade.UtiCodeTransferService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCinsured;//mantis： CLM0231，處理人員：DP0706，需求單編號：CLM0231.新核心-傷害險高保額新商品檢核
//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainLiab;
import com.sinosoft.claim.schema.model.PrpDcode;
//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
import com.sinosoft.claim.schema.model.PrpDpolicyRules;
import com.sinosoft.claim.schema.model.PrpDrisk;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLcheckId;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLclaimStatus;
import com.sinosoft.claim.schema.model.PrpLclaimStatusId;
import com.sinosoft.claim.schema.model.PrpLltext;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.UtiCodeTransfer;
//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
import com.sinosoft.claim.schema.model.UtiUserGrade;
import com.sinosoft.claim.schema.service.facade.PrpCmainLiabService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
import com.sinosoft.claim.schema.service.facade.PrpDpolicyRulesService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimStatusService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrplexcludeclaimService;
//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;
import com.sinosoft.claim.ui.control.action.UIQuickCaseAction;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * 立案处理Action
 * @author 中科软
 */
public class ClaimBeforeEditAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/** 编辑类型 */
	private String editType = "add";
	/** 立案主表 */
	private PrpLclaim prpLclaim;
	/** 立案大对象 */
	private ClaimDto claimDto;
	/** 代码服务 */
	private CodeService codeService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 立案主表服务 */
	private PrpLclaimService prpLclaimService;
	/** 备案service */
	private PrpLregistService prpLregistService;
	/** 立案号码查询条件 */
	private String ClaimNoSign;
	/** 立案号码 */
	private String ClaimNo;
	/** 保单号码查询条件 */
	private String PolicyNoSign;
	/** 保单号码 */
	private String PolicyNo;
	/** 备案号码 */
	private String RegistNo;
	private String RegistNoSign;
	/** 车牌号码 */
	private String LicenseNo;
	private String LicenseNoSign;
	/** 日期 */
	private String OperateDate;
	private String OperateDateSign;
	/** 被保险人名称 */
	private String InsuredName;
	private String InsuredNameSign;
	/**  */
	private String caseFlag;
	/** 状态 */
	private String status;
	/** 立案文字表 */
	private PrpLltext prpLltext;
	/** 险种 */
	private String riskCode;
	/** 三者车车牌号 */
	private String prpLthirdPartyLicenseNo;
	
	/** 立案viewHelper */
	private DAAClaimViewHelper daaClaimViewHelper;
	/** 查勘/代查勘接口service */
	private PrpLcheckService prpLcheckService;
	/** 备案service */
	private RegistService registService;
	/** 备案viewHelper */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 立案除外信息接口service */
	private PrplexcludeclaimService prplexcludeclaimService;
	/** 理赔节点状态接口service */
	private PrpLclaimStatusService prpLclaimStatusService;
	/** swfLog联合主键 */
	private String swfLogFlowID = null;
	/** swfLog联合主键 */
	private String swfLogLogNo = null;
	/** 承保url */
	private String coreURL = "";
	/**  */
	private String ifclose = "";
	/** 标志位 */
	private String dfFlag = "";
	/** 当前时间 */
	private String nowDate = "";
	/** 当前年 */
	private int nowYear = 0;
	/** 保单service */
	private PolicyService policyService;
	/** 理赔节点状态viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 基础配置service */
	private PrpDcodeService prpDcodeService;
	/** 基础配置service */
	private PrpDriskConfigService prpDriskConfigService;
	/** 工作流service */
	private WorkFlowService workFlowService;
	/** 险类service */
	private PrpDriskService prpDriskService;
	
	private UtiCodeTransferService utiCodeTransferService;
	private PrpCmainLiabService prpCmainLiabService;
	private CommonService commonService;

	/** mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 */
	private PrpDpolicyRulesService prpDpolicyRulesService;
	
	//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
	/** 權限service */
	private UtiUserGradeService utiUserGradeService;
	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項
	private String isCompulsoryBchainClaimDisabled="true";
	
	/**
	 * 准备增加立案信息
	 * @return 页面类型
	 * @throws Exception
	 */
	public String prepareAdd() throws Exception {
		logger.info("准备增加立案信息");
		editType = "add";
		System.err.println(prpLclaimService);
		prpLclaim = prpLclaimService.findPrpLclaim("505012011000001000004");
		PrpLregist PrpLregist = prpLregistService.findPrpLregist("605012011000099000004");
		System.err.println("--" + prpLclaim.getClaimNo());
		System.err.println("--" + PrpLregist.getRegistNo());

		return SUCCESS;
	}

	/**
	 * 车险理赔立案前查询保单/立案报案信息 Description: 车险理赔报案前查询保单信息系统
	 * @author 中科软
	 */
	public String claimBeforeEdit() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();
		HttpServletResponse httpServletResponse = getResponse();
		
		//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題 START
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		int gradeLevel = getGradeLevel(httpServletRequest,userDto);
		httpServletRequest.setAttribute("gradeLevel", ""+gradeLevel);
		//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題 END
		
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示 CANCEL-注销
		if (editType == null) {
			editType = "ADD"; // 操作类型
		}
		String forward = ""; // 向前
		try {
			String msg = ""; // 抛出错误的信息的具体内容
			coreURL = AppConfig.get("sysconst.Core_URL");
			HttpSession session = httpServletRequest.getSession();
			UserDto user = (UserDto) session.getAttribute("user");
			String riskClass = codeService.translateClassCodeByRiskCode(riskCode);
			//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
//			isCompulsoryBchainClaimDisabled = "true";
			if (ConstantCodes.RISKCODE_DAA.equals(riskCode) || ConstantCodes.RISKCODE_DAZ.equals(riskCode)) {
				isCompulsoryBchainClaimDisabled = "false";//車險開啟 顯示[是否為強制險區塊鏈攤賠案件]
			}
			//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
			// 3 立案注销和拒赔的赔案号验证
			if (editType.equals("CANCEL")) {
				// 查询立案信息的拒赔过程
				daaClaimViewHelper.cancelDtoToView(httpServletRequest, user);
				forward = editType + "DAA";
				if (!"".equals(riskCode) && riskCode != null) {
					httpServletRequest.setAttribute("com_sinosoft_type", riskClass);
				}
				return forward;
			}
			// 4 立案注销和拒赔的受理
			if (editType.equals("CANCELEDIT")) {
				// 查询立案信息的拒赔过程
				daaClaimViewHelper.cancelDtoToCancelView(httpServletRequest, user);
				forward = "CANCELDAA";
				httpServletRequest.setAttribute("com_sinosoft_type", riskClass);
				return forward;
			}
			// 添加意健险不予立案的处理 2005-09-06
			if ("NOTGRANDCLAIM".equals(editType)) {
				daaClaimViewHelper.notGrandClaimDtoToView(httpServletRequest, user);
				forward = "NOTGRANDCLAIM";
				return forward;
			}
			// 尚未加入type异常处理{}、其它必须参数异常处理{}
			if (editType.equals("ADD")) {
				// 查询报案信息,整理输入，用於初始界面显示
				// 增加对简易赔案的判断
				UIQuickCaseAction uiQuickCaseAction = new UIQuickCaseAction();
				// 後续程序是否执行，赠加控制
				boolean blFwd = uiQuickCaseAction.checkQuickCaseAndForwadToSHOW(RegistNo, httpServletResponse);
				if (blFwd) {
					return null;
				}
				PrpLcheckId prpLcheckId = new PrpLcheckId(RegistNo, 1);
				PrpLcheck prpLcheck = prpLcheckService.findPrpLcheck(prpLcheckId);
				PrpDcode prpDcode = null;
				if (prpLcheck != null) {
					httpServletRequest.setAttribute("prpLcheck", prpLcheck);
					List<PrpDcode> prpDcodeList = prpDcodeService.findByConditions(" codeCode = '" + prpLcheck.getDamageAreaCode() + "' AND codetype ='DamageAreaCode'  AND validstatus='1'");
					for (int j = 0; j < prpDcodeList.size(); j++) {
						prpDcode = prpDcodeList.get(j);
					}
					if (prpDcode != null) {
						httpServletRequest.setAttribute("prpDcodeSchema", prpDcode);
					}
				}

				if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
					riskCode = BusinessRuleUtil.getRiskCode(RegistNo, "RegistNo");
				}
				// 若是倒签单，在起保日期到签单日期之间提示不能报案，这个控制是硬控制
				RegistDto registDto = registService.findByPrimaryKey(RegistNo);
				PrpLregist tempPrpLregist = registDto.getPrpLregist();
				httpServletRequest.setAttribute("registDto", registDto);
				httpServletRequest.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
				httpServletRequest.setAttribute("RISKCODE", riskCode);
//				PolicyDto policyDto = endorseViewHelper.findForEndorBefore(PolicyNo, new DateTime(tempPrpLregist.getDamageStartDate()).toString(), tempPrpLregist.getDamageStartHour());
				String strDamageDate = new DateTime(tempPrpLregist.getDamageStartDate()).toString();
				String strDamageHour = tempPrpLregist.getDamageStartHour();
				// 当团保的时候输入域为可手工输入
				PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(PolicyNo, strDamageDate, strDamageHour);
				if (prpCmain != null) {
					// 保单退保或者注销，案件不允许立案
					if (prpCmain.getOthFlag().substring(2, 3).equals("1")) {
						throw new UserException(1,3,"","保單已退保，不允許立案。");
					}
					if (prpCmain.getOthFlag().substring(3, 4).equals("1")) {
						throw new UserException(1,3,"","保单已註銷，不允許立案。");
					}
					if ("8".equals(prpCmain.getPolicySort())) {
						httpServletRequest.setAttribute("insuredNameFlag", "Ture");
					}
				}
				//已超時立案規則:系統時間-備案時間>=168小時
				PrpDrisk  prpDrisk = prpDriskService.findPrpDrisk(riskCode); 
				riskClass = prpDrisk.getClassCode();
				if("C,E,C".indexOf(riskClass) > -1){
					long now = System.currentTimeMillis();
					long reportTime = new DateTime(tempPrpLregist.getReportDate()).getTime();
					if((now - reportTime)/ (1000 * 60 * 60 ) > 168){
						httpServletRequest.setAttribute("reportTimeMessage", "已超时，請慎重處理立案！");
					}
				}
				// 加入出险日期等於起保日期，核保日期晚於起保日期，具体到小时的判断
				// **************判断保费是否已经实收
				String conditions = " policyno = '" + PolicyNo + "'";
				int intReturn = 0;
				intReturn = policyService.checkPay(conditions);// -1为未缴费，0为未缴全，1为缴全
				String strPayFlag = String.valueOf(intReturn);
				httpServletRequest.setAttribute("payFlag", strPayFlag);
				// 当缴费不足时,要显示相应的缴费情况
				// 欠费情况
				String delinquentfeeCase = "";
				// 若费用未缴全,则针对分期付款的情况要提示哪几期费用未缴
				if (intReturn == 0 && prpCmain.getPayTimes() > 1) {
					delinquentfeeCase = daaRegistViewHelper.getDelinquentfeeCase(prpCmain);
				}
				// 设置分期付款未缴期数
				httpServletRequest.setAttribute("delinquentfeeCase", delinquentfeeCase);
				PrpLregist prpLregist = new PrpLregist();
				//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
				if(null!=registDto && null!=registDto.getPrpLregist()){
					prpLregist.setIsCompulsoryBchainClaim(registDto.getPrpLregist().getIsCompulsoryBchainClaim());
				}
				//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
				prpLregist.setPayFlag(intReturn + "");
				httpServletRequest.setAttribute("prpLregist", prpLregist);
				// 当缴费不足时,要显示相应的缴费情况
				// 获取系统设置信息
				// 获取保费未实收是否立案信息
				String configValue =  prpDriskConfigService.getConfigValue("ALLOW_UNPAYED_CLAIM", prpCmain.getRiskCode());
				// 报案出险延期天数
				String standard_stringDays = prpDriskConfigService.getConfigValue("REPORT_DEFER_DAYS", prpCmain.getRiskCode());
				if (configValue == null || configValue.equals("")) {
					throw new UserException(1, 3, "platform", "請聯系系統管理員，在平台配置系統中進行險種" + prpCmain.getRiskCode() + "'保費未實收是否允許立案'的初始化！");
				}
				if (standard_stringDays == null || standard_stringDays.equals("")) {
					throw new UserException(1, 3, "platform", "請聯系系統管理員，在平台配置系統中進行險種" + prpCmain.getRiskCode() + "'報案出險延期天數'的初始化！");
				}
				long standard_days = Long.parseLong(standard_stringDays);
				// 如果configValue =2 intReturn！=1则表示未交费不能立案
				if (configValue.equals("2") && intReturn != 1) {
					this.clearErrorsAndMessages();
					this.addActionMessage(getText("prompt.claim.feeFailure"));
					forward = "ADDDAAFALSE";
					return forward;
				}
				// **************判断报案出险延期天数是否大於系统规定时间，大於不允许立案
				DateTime damageDate = new DateTime(tempPrpLregist.getDamageStartDate());
				DateTime reportDate = new DateTime(tempPrpLregist.getReportDate());
				long report_damage_days = (reportDate.getTime() - damageDate.getTime()) / (1000 * 60 * 60 * 24);
//				 获取系统规定时间 standard_days
				if (report_damage_days > standard_days) {
//					this.clearErrorsAndMessages();
//					this.addActionMessage(getText("prompt.claim.report_damage_standardDays"));
					httpServletRequest.setAttribute("reportDamageMessage", getText("prompt.claim.report_damage_standardDays"));
//					forward = "ADDDAAFALSE";
					// ("判断报案出险延期天数大於系统规定时间，不允许立案！");
//					return forward;
				}
				// 根据保单号取得保单信息
//				PrpCmain prpCmain = claimService.findByPolicyNoKey(PolicyNo);
				//貨物運輸險、商動險、貨物運送人責任險、海運和空運承攬人責任險是“保險期不確定，不需要提示訊息” 
				if(!("MC,OP,TB,CF,CL".indexOf(riskCode) > -1)){
					Date damageStartDate = tempPrpLregist.getDamageStartDate();
					// 出险时间不在保险期间内的案件，报案时系统进行提示，立案时硬控制，走拒赔或特殊案件流程。
					if (damageStartDate.before(prpCmain.getStartDate()) || damageStartDate.after(prpCmain.getEndDate())) {
						String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
						PrpCmainLiab prpCmainLiab = prpCmainLiabService.findPrpCmainLiab(prpCmain.getPolicyNo());
						Date liabStartDate = new DateTime();
						if (prpCmainLiab != null) {
							liabStartDate = new DateTime(prpCmainLiab.getBkWardStartDate());
						}
						// 新需求，不考虑责任险的追溯期，所有出险时间不再保险起见内的案件都不允许立案
						if ("Z".equals(strRiskType) // 判断是不是责任险
								&& damageStartDate.after(liabStartDate) && damageStartDate.before(prpCmain.getStartDate())) {
							// 责任险，在追溯期内允许立案,自动跳过
							// 新增立案除外功能，如果总公司同意了，则可以立案
						} else if (prplexcludeclaimService.isExcluded(RegistNo)) {
							// 如果总公司做过除外设置，则允许立案
						} else {
							this.clearErrorsAndMessages();
							this.addActionMessage(getText("prompt.claimAdd.damageStartDate"));
							forward = "ADDDAAFALSE";
							return forward;
						}
						if(("MP,FD,TL,PR,MF,PF".indexOf(riskCode) > -1)){
							Date newDamageStartDate ;
							Date newEndDate = new Date();;
							Date endDate = prpCmain.getStartDate();
							newDamageStartDate = liabStartDate;
							newEndDate.setTime(endDate.getTime() + 60 * 24 * 60 *60 * 1000);//保險止期增加60天
							if(newDamageStartDate.before(damageStartDate) || newDamageStartDate.after(newEndDate)){
								this.clearErrorsAndMessages();
								this.addActionMessage(getText("prompt.claimAdd.damageStartDate"));
								forward = "ADDDAAFALSE";
								return forward;
							}
						}
					}
					//如果沒有退保，則只需要判斷出險時間在保險開始時間之後即可，無需和保險截止日期做比較
					if(("AP,PF".indexOf(riskCode) > -1)){
						String otherFlag = prpCmain.getOthFlag();
						// 没有保单退保且没有注销
						if (!otherFlag.substring(2, 3).equals("1") && !otherFlag.substring(3, 4).equals("1")) {
							if(damageStartDate.before(prpCmain.getStartDate())){
								this.clearErrorsAndMessages();
								this.addActionMessage(getText("prompt.claimAdd.damageStartDate"));
								forward = "ADDDAAFALSE";
								return forward;
							}
						}
					}
				}
				//貨物運輸險、商動險、貨物運送人責任險、海運和空運承攬人責任險是“保險期不確定，不需要提示訊息” end
				// 首先判断该报案是否已经立过案
				// 在uiCLaimAcction里自动识别是否要怎么查询的具体操作
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("registNo", RegistNo);
				queryRule.addEqual("riskCode", riskCode);
				List<PrpLclaim> claimList = prpLclaimService.findPrpLclaim(queryRule);
				// 说明查询到该报案已经立过案了
				if (claimList.size() > 0) {
					this.clearErrorsAndMessages();
					this.addActionMessage(getText("prompt.claimAdd.false"));// 说明查询到该报案已经立过案了
					forward = "ADDDAAFALSE";
					return forward;
				}
				// 报案提交状态的案件才可立案
				PrpLclaimStatusId prpLclaimStatusId = new PrpLclaimStatusId(RegistNo, "regis", 0);
				PrpLclaimStatus prpLclaimStatus = prpLclaimStatusService.findPrpLclaimStatus(prpLclaimStatusId);
				if (prpLclaimStatus != null) {
					if (!prpLclaimStatus.getStatus().equals("4")) {
						this.clearErrorsAndMessages();
						this.addActionMessage(getText("prompt.claimAdd.notSubmit"));// 报案提交状态的案件才可立案
						forward = "ADDDAAFALSE";
						return forward;
					}
				}


				//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
				httpServletRequest.setAttribute("riskCode", riskCode);
				httpServletRequest.setAttribute("registType", "0");
				if(null!=riskCode && null!=RegistNo){
					prpLregist = prpLregistService.findPrpLregist(RegistNo);
					httpServletRequest.setAttribute("registType", prpLregist.getRegistType());
				}
				//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
				
				String flowID = swfLogFlowID;
				String logNo = swfLogLogNo;
				if (flowID != null && logNo != null) {
					SwfLog swfLogDto = this.getWorkFlowService().holdNode(flowID, Integer.parseInt(logNo), user.getUserCode(), user.getUserName());
					if (swfLogDto.getHoldNode() == false) {
						msg = "案件'" + RegistNo + "'已經被代碼:'" + swfLogDto.getHandlerCode() + "',名稱:'" + swfLogDto.getHandlerName() + "'的用戶所佔用,請選擇其它案件進行處理!";
						throw new UserException(1, 3, "工作流", msg);
					}
				}
				// ===============================================
				daaClaimViewHelper.registDtoToView(httpServletRequest, RegistNo);
				
				//車險簡易賠案設置
				UtiCodeTransfer transfer = this.utiCodeTransferService.findUtiCodeTransfer(riskCode);
				if("D".equals(transfer.getRiskType())){
					this.setCarKindCode(httpServletRequest, riskCode);
					//mantis： CLM0058 ，處理人員：CLM0058車臉出險只走簡易流程，並將一般流程隱藏
					String sql = " flowID = '" + swfLogFlowID + "' and nodeType = 'sched' and nodeStatus IN ('0','6') ";
					List<SwfLog> tempList = this.workFlowService.findByConditions(sql);
					if(!CommonUtils.isEmpty(tempList)){//分案未處理，可以進行簡易賠案
						httpServletRequest.setAttribute("simpleFlag", true);
					}
				}

				//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
				String policyNo = prpLregist.getPolicyNo();
				String damageDate2 = new DateTime(prpLregist.getDamageStartDate()).toString();
				String damageHour = prpLregist.getDamageStartHour();
				String insuredCode = prpLregist.getInsuredCode();
				String insuredName = prpLregist.getInsuredName();
				List<PrpCitemKind> prpCitemKindList = null;
				List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate2, damageHour, insuredCode, insuredName);
				PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
				prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate2, damageHour, prpCinsured.getId().getSerialNo());
				httpServletRequest.setAttribute("prpCitemKindList", prpCitemKindList);//span_prpCitemKind
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String todayStr = sdf.format(new Date());
				if(null!=prpLregist && null!=prpLregist.getDamageStartDate()){
					todayStr = sdf.format(prpLregist.getDamageStartDate());//出險日
				}
				
				List<PrpDpolicyRules> prpDpolicyRulesList = this.prpDpolicyRulesService.findByConditions
						(" codeCode in ('901','902','903') AND codetype ='CountryCode_CTN' " +
						" AND startdate <= TO_DATE('"+todayStr+"', 'YYYY-MM-DD') AND (enddate > TO_DATE('"+todayStr+"', 'YYYY-MM-DD')  OR enddate IS NULL)");
				httpServletRequest.setAttribute("prpDpolicyRulesList", prpDpolicyRulesList);//span_prpDpolicyRules
				//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
			}
			if (editType.equals("EDIT") || editType.equals("SHOW")) {
				// 查询立案信息,整理输入，用於初始界面显示
				ClaimNo = httpServletRequest.getParameter("prpLclaimClaimNo"); // 赔案号
				if (riskCode == null || riskCode.length() < 1 || riskCode.trim().equals("")) {
					riskCode = BusinessRuleUtil.getRiskCode(ClaimNo, "ClaimNo");
				}
				PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(ClaimNo);
				// 增加对简易赔案的判断
				UIQuickCaseAction uiQuickCaseAction = new UIQuickCaseAction();
				// 後续程序是否执行，赠加控制
				boolean blFwd = uiQuickCaseAction.checkQuickCaseAndForwadToSHOW(prpLclaim.getRegistNo(), httpServletResponse);
				if (blFwd) {
					return "";
				}
				String conditions1 = " policyno = '" + PolicyNo + "'";
				int intReturn = 0;
				intReturn = this.policyService.checkPay(conditions1);// -1为未缴费，0为未缴全，1为缴全
				String strPayFlag = String.valueOf(intReturn);
				httpServletRequest.setAttribute("payFlag", strPayFlag);
				
				// 当缴费不足时,要显示相应的缴费情况
				String policyNo = prpLclaim.getPolicyNo();
				String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
				String damageHour = prpLclaim.getDamageStartHour();
				PrpCmain cmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
				// 欠费情况
				String delinquentfeeCase = "";
				// 若费用未缴全,则针对分期付款的情况要提示哪几期费用未缴
				if (intReturn == 0 && cmain.getPayTimes() > 1) {
					delinquentfeeCase = this.daaRegistViewHelper.getDelinquentfeeCase(cmain);
				}
				// 设置分期付款未缴期数
				httpServletRequest.setAttribute("delinquentfeeCase", delinquentfeeCase);
				// 当缴费不足时,要显示相应的缴费情况
				PrpLregist prpLregist = new PrpLregist();
				prpLregist.setPayFlag(intReturn + "");
				//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
				httpServletRequest.setAttribute("riskCode", prpLclaim.getRiskCode());
				httpServletRequest.setAttribute("registType", "0");
				if(null!=prpLclaim && null!=prpLclaim.getRegistNo()){
					prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
					httpServletRequest.setAttribute("registType", prpLregist.getRegistType());
				}
				//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
				httpServletRequest.setAttribute("prpLregist", prpLregist);
				daaClaimViewHelper.claimDtoToView(httpServletRequest, ClaimNo);
				httpServletRequest.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
				httpServletRequest.setAttribute("RISKCODE", riskCode);
				//mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能Start
				httpServletRequest.setAttribute("prpLregistStartDate", cmain.getStartDate());
				httpServletRequest.setAttribute("prpLregistStartHour", cmain.getStartHour());
				//mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整
				httpServletRequest.setAttribute("prpLregistStartMinute", prpLclaim.getDamageStartMinute());
				httpServletRequest.setAttribute("prpLregistEndDate", cmain.getEndDate());
				httpServletRequest.setAttribute("prpLregistEndHour", cmain.getEndHour());
				//mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整
				httpServletRequest.setAttribute("prpLregistEndMinute", cmain.getEndMinute());
				httpServletRequest.setAttribute("specialEditCase", httpServletRequest.getParameter("specialEditCase"));
				//mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能End
				
				//車險簡易賠案設置
				//mantis： CLM0058 ，處理人員：CLM0058車臉出險只走簡易流程，並將一般流程隱藏-start
				UtiCodeTransfer transfer = this.utiCodeTransferService.findUtiCodeTransfer(riskCode);
				if("D".equals(transfer.getRiskType())){
					this.setCarKindCode(httpServletRequest, riskCode);
//					PrpLregist tempPrpLregist = this.registService.findByPrimaryKeyForPrpLRegist(prpLclaim.getRegistNo());
//					if("RISKCODE_DAZ".equals(transfer.getConfigCode())
//							|| (!"2".equals(tempPrpLregist.getRegistType()))){//
					//mantis： CLM0058 ，處理人員：CLM0058車臉出險只走簡易流程，並將一般流程隱藏
//						//單獨備案可做，關聯備案強制險可做
						String sql = " flowID = '" + swfLogFlowID + "' and nodeType = 'sched' and nodeStatus IN ('0','6') ";
						List<SwfLog> tempList = this.workFlowService.findByConditions(sql);
						if(!CommonUtils.isEmpty(tempList)){//分案未處理，可以進行簡易賠案
							httpServletRequest.setAttribute("simpleFlag", true);
						}
//					}
				}
				//mantis： CLM0058 ，處理人員：CLM0058車臉出險只走簡易流程，並將一般流程隱藏-end
				//mantis： CLM0231，處理人員：DP0706，需求單編號：CLM0231.新核心-傷害險高保額新商品檢核START
				String insuredCode = prpLclaim.getInsuredCode();
				String insuredName = prpLclaim.getInsuredName();
				String identifyNumber = prpLclaim.getIdentifyNumber();
				List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName, identifyNumber);
				PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
				//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 START
				if(null!=prpCinsured){
					httpServletRequest.setAttribute("familyno",DataUtils.nullToEmpty(String.valueOf(prpCinsured.getId().getSerialNo())));
				}
				//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 END
				//mantis： CLM0231，處理人員：DP0706，需求單編號：CLM0231.新核心-傷害險高保額新商品檢核END

				//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
				List<PrpCitemKind> prpCitemKindList = null;
				Integer familyno = null!=prpCinsured?prpCinsured.getId().getSerialNo():0;
				prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, familyno);
				httpServletRequest.setAttribute("prpCitemKindList", prpCitemKindList);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String todayStr = sdf.format(new Date());
				if(null!=prpLclaim && null!=prpLclaim.getDamageStartDate()){
					todayStr = sdf.format(prpLclaim.getDamageStartDate());//出險日
				}
				
				List<PrpDpolicyRules> prpDpolicyRulesList = this.prpDpolicyRulesService.findByConditions
						(" codeCode in ('901','902','903') AND codetype ='CountryCode_CTN' " +
						" AND startdate <= TO_DATE('"+todayStr+"', 'YYYY-MM-DD') AND (enddate > TO_DATE('"+todayStr+"', 'YYYY-MM-DD')  OR enddate IS NULL)");
				httpServletRequest.setAttribute("prpDpolicyRulesList", prpDpolicyRulesList);//span_prpDpolicyRules
				//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
			}
			// 未处理立案任务的放弃处理
			if (editType.equals("GIVUP")) {
				// 放弃未暂存和提交的立案任务，删去结点操作人，使其他人可见可处理
				String FlowID = swfLogFlowID;
				int LogNo = Integer.parseInt(swfLogLogNo);
				SwfLog swfLogDto = this.getWorkFlowService().findNodeByPrimaryKey(FlowID, LogNo);
				if (swfLogDto.getNodeType().equals("claim")) {
					swfLogDto.setHandlerCode("");
					swfLogDto.setHandlerName("");
					swfLogDto.setFlowStatus("1");
				}
				this.getWorkFlowService().updateFlow(swfLogDto);
				this.clearErrorsAndMessages();
				this.addActionMessage(getText("prompt.compensate.giveup"));
				forward = "success";
				return forward;
			}
			// 取得forward
			forward = BusinessRuleUtil.getForward(httpServletRequest, riskCode.trim(), "claim", editType, 1);
			if (editType.equals("LOSS")) {
				ClaimNo = httpServletRequest.getParameter("prpLclaimClaimNo"); // 赔案号
				daaClaimViewHelper.claimDtoToView(httpServletRequest, ClaimNo);
				forward = "ADDLOSS";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改
		System.out.println("CLM0272 : "+forward);
		return forward;

	}
	
	/**
	 * mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
	 *  FROM CLM0098
	 *  理賠權限:
	 *  0 = 003/009(理賠助理/部門理賠科長)&comcode =00(總公司)
	 *  1 = 003/009(理賠助理/部門理賠科長)&comcode !=00(非公司)
	 *  2 = 005(一般理賠人員)
	 * @param userDto
	 * @return
	 * @throws Exception
	 */
	private int getGradeLevel(HttpServletRequest request,UserDto userDto) throws Exception {
		int gradeLevel = 2;//一般理賠人員
		String userCode = userDto.getUserCode();
		String comCode = userDto.getComCode();
		for(UtiUserGrade grade : utiUserGradeService.findByConditions("USERCODE='"+userCode+"' and COMCODE = '"+comCode+"'")){
			if("00".equals(comCode) && ConstantCodes.GRADECODE_003.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 0);// 總公司理賠助理
				break;
			}else if("00".equals(comCode) && ConstantCodes.GRADECODE_009.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 0);// 總公司理賠科長
				break;
			}else if(!"00".equals(comCode) && ConstantCodes.GRADECODE_003.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 1);// 分公司理賠助理
			}else if(!"00".equals(comCode) && ConstantCodes.GRADECODE_009.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 1);// 分公司理賠科長
			}else if(ConstantCodes.GRADECODE_005.equals(grade.getId().getGradeCode())){
				gradeLevel = Math.min(gradeLevel, 2);//一般理賠人員
			}
		}
		if(gradeLevel == 2){//一般理賠人員只能查詢自己的案子
			request.setAttribute("userCode", userCode);
		}else if(gradeLevel == 1){// 非總公司理賠助理or理賠科長只能查詢相同單位(comcode)理賠案件
			request.setAttribute("comCode", comCode);
		}
		return gradeLevel;
	}

	/**
	 * 准备编辑
	 * @return 页面类型
	 */
	public String prepareEdit() throws Exception {
		return SUCCESS;
	}

	/**
	 * 修改立案信息
	 * @return 页面类型
	 */
	public String edit() throws Exception {
		// 编辑数据
		return SUCCESS;
	}

	/**
	 * 删除立案信息
	 * @return 页面类型
	 */
	public String delete() throws Exception {
		return NONE;
	}

	/**
	 * 查看立案详情
	 * @return 页面类型
	 */
	public String view() throws Exception {
		editType = ConstantCodes.EditType.SHOW;
		claimDto = claimService.findByPrimaryKey(ClaimNo);
		this.prpLclaim = claimDto.getPrpLclaim();
		initVariableView();
		return SUCCESS;
	}
	/***
	 * 設置車體險險種
	 * @param request
	 * @param riskCode
	 */
	@SuppressWarnings("unchecked")
	private void setCarKindCode(HttpServletRequest request , String riskCode){
		String statements = "SELECT distinct kindcode FROM fdkindconfig where riskcode = '"+ riskCode +"' and (accriskcode = 'A901' or accriskcode = 'A902') ";
		List<String> list = (List<String>) this.commonService.findByStatements(statements);
		String[] str = new String[list.size()];
		CommonUtils.join(list.toArray(str), ",");
		request.setAttribute("CarKindCode", CommonUtils.join(list.toArray(str), ","));
		statements = "SELECT distinct kindcode FROM fdkindconfig where riskcode = '"+ riskCode +"' and (accriskcode = 'A903' or accriskcode = 'A904') ";
		list = (List<String>) this.commonService.findByStatements(statements);
		str = new String[list.size()];
		CommonUtils.join(list.toArray(str), ",");
		request.setAttribute("PropKindCode", CommonUtils.join(list.toArray(str), ","));
	}
	
	/**
	 * 初始化
	 * @throws Exception
	 */
	private void initVariableView() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();

		List<PrpCengage> list = claimService.findByPrimaryKey(prpLclaim.getClaimNo()).getPrpCengageList();
		claimDto.setPrpCengageList(list);
		daaClaimViewHelper.claimDtoToView(httpServletRequest, claimDto);
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

	public PrpLclaim getPrpLclaim() {
		return prpLclaim;
	}

	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public String getClaimNoSign() {
		return ClaimNoSign;
	}

	public void setClaimNoSign(String claimNoSign) {
		ClaimNoSign = claimNoSign;
	}

	public String getClaimNo() {
		return ClaimNo;
	}

	public void setClaimNo(String claimNo) {
		ClaimNo = claimNo;
	}

	public String getPolicyNo() {
		return PolicyNo;
	}

	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}

	public String getPolicyNoSign() {
		return PolicyNoSign;
	}

	public void setPolicyNoSign(String policyNoSign) {
		PolicyNoSign = policyNoSign;
	}

	public String getRegistNoSign() {
		return RegistNoSign;
	}

	public void setRegistNoSign(String registNoSign) {
		RegistNoSign = registNoSign;
	}

	public String getRegistNo() {
		return RegistNo;
	}

	public void setRegistNo(String registNo) {
		RegistNo = registNo;
	}

	public String getLicenseNoSign() {
		return LicenseNoSign;
	}

	public void setLicenseNoSign(String licenseNoSign) {
		LicenseNoSign = licenseNoSign;
	}

	public String getLicenseNo() {
		return LicenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		LicenseNo = licenseNo;
	}

	public String getOperateDateSign() {
		return OperateDateSign;
	}

	public void setOperateDateSign(String operateDateSign) {
		OperateDateSign = operateDateSign;
	}

	public String getInsuredNameSign() {
		return InsuredNameSign;
	}

	public void setInsuredNameSign(String insuredNameSign) {
		InsuredNameSign = insuredNameSign;
	}

	public String getInsuredName() {
		return InsuredName;
	}

	public void setInsuredName(String insuredName) {
		InsuredName = insuredName;
	}

	public String getCaseFlag() {
		return caseFlag;
	}

	public void setCaseFlag(String caseFlag) {
		this.caseFlag = caseFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ClaimDto getClaimDto() {
		return claimDto;
	}

	public void setClaimDto(ClaimDto claimDto) {
		this.claimDto = claimDto;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public String getOperateDate() {
		return OperateDate;
	}

	public void setOperateDate(String operateDate) {
		OperateDate = operateDate;
	}

	public PrpLltext getPrpLltext() {
		return prpLltext;
	}

	public void setPrpLltext(PrpLltext prpLltext) {
		this.prpLltext = prpLltext;
	}

	public String getPrpLthirdPartyLicenseNo() {
		return prpLthirdPartyLicenseNo;
	}

	public void setPrpLthirdPartyLicenseNo(String prpLthirdPartyLicenseNo) {
		this.prpLthirdPartyLicenseNo = prpLthirdPartyLicenseNo;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public DAAClaimViewHelper getDaaClaimViewHelper() {
		return daaClaimViewHelper;
	}

	public void setDaaClaimViewHelper(DAAClaimViewHelper daaClaimViewHelper) {
		this.daaClaimViewHelper = daaClaimViewHelper;
	}

	public PrpLcheckService getPrpLcheckService() {
		return prpLcheckService;
	}

	public void setPrpLcheckService(PrpLcheckService prpLcheckService) {
		this.prpLcheckService = prpLcheckService;
	}

	public RegistService getRegistService() {
		return registService;
	}

	public void setRegistService(RegistService registService) {
		this.registService = registService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public PrplexcludeclaimService getPrplexcludeclaimService() {
		return prplexcludeclaimService;
	}

	public void setPrplexcludeclaimService(PrplexcludeclaimService prplexcludeclaimService) {
		this.prplexcludeclaimService = prplexcludeclaimService;
	}

	public PrpLclaimStatusService getPrpLclaimStatusService() {
		return prpLclaimStatusService;
	}

	public void setPrpLclaimStatusService(PrpLclaimStatusService prpLclaimStatusService) {
		this.prpLclaimStatusService = prpLclaimStatusService;
	}

	public String getSwfLogFlowID() {
		return swfLogFlowID;
	}

	public void setSwfLogFlowID(String swfLogFlowID) {
		this.swfLogFlowID = swfLogFlowID;
	}

	public String getSwfLogLogNo() {
		return swfLogLogNo;
	}

	public void setSwfLogLogNo(String swfLogLogNo) {
		this.swfLogLogNo = swfLogLogNo;
	}

	public String getCoreURL() {
		return coreURL;
	}

	public void setCoreURL(String coreURL) {
		this.coreURL = coreURL;
	}

	public String getIfclose() {
		return ifclose;
	}

	public void setIfclose(String ifclose) {
		this.ifclose = ifclose;
	}

	public String getDfFlag() {
		return dfFlag;
	}

	public void setDfFlag(String dfFlag) {
		this.dfFlag = dfFlag;
	}

	/**
	 * @return 获取系统当前的时间
	 */
	public String getNowDate() {
		if (nowDate == null || "".equals(nowDate)) {
			nowDate = new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).toString();
		}
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public int getNowYear() {
		if (nowYear <= 0) {
			nowYear = new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear();
		}
		return nowYear;
	}

	public void setNowYear(int nowYear) {
		this.nowYear = nowYear;
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

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
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

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public UtiCodeTransferService getUtiCodeTransferService() {
		return utiCodeTransferService;
	}

	public void setUtiCodeTransferService(UtiCodeTransferService utiCodeTransferService) {
		this.utiCodeTransferService = utiCodeTransferService;
	}

	public PrpCmainLiabService getPrpCmainLiabService() {
		return prpCmainLiabService;
	}

	public void setPrpCmainLiabService(PrpCmainLiabService prpCmainLiabService) {
		this.prpCmainLiabService = prpCmainLiabService;
	}
	
	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題START
	public UtiUserGradeService getUtiUserGradeService() {
		return utiUserGradeService;
	}

	public void setUtiUserGradeService(UtiUserGradeService utiUserGradeService) {
		this.utiUserGradeService = utiUserGradeService;
	}
	//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題END
	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
	public String getIsCompulsoryBchainClaimDisabled() {
		return isCompulsoryBchainClaimDisabled;
	}

	public void setIsCompulsoryBchainClaimDisabled(
			String isCompulsoryBchainClaimDisabled) {
		this.isCompulsoryBchainClaimDisabled = isCompulsoryBchainClaimDisabled;
	}
	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
	public PrpDpolicyRulesService getPrpDpolicyRulesService() {
		return prpDpolicyRulesService;
	}

	public void setPrpDpolicyRulesService(
			PrpDpolicyRulesService prpDpolicyRulesService) {
		this.prpDpolicyRulesService = prpDpolicyRulesService;
	}
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
}