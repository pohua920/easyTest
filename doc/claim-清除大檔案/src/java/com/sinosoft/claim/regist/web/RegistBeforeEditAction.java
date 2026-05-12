package com.sinosoft.claim.regist.web;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.claim.service.facade.ClaimService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainSub;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpCmainSubService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.prpall.ui.UIPrpJFeeCheck;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.utility.log.Log;


//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;

/**
 * 报案处理Action
 * @author 中科软
 */
public class RegistBeforeEditAction extends Struts2Action {
	private static final long serialVersionUID = 1L;
	/** 编辑类型 */
	private String editType = "ADD";
	/** 理赔信息 */
	private PrpLclaim prpLclaim;
	/** 代码翻译服务 */
	private CodeService codeService;
	/** 立案服务 */
	private ClaimService claimService;
	/** 报案服务 */
	private PrpLregistService prpLregistService;
	/** 报案数据收集 */
	private DAARegistViewHelper daaRegistViewHelper;
	/** 保单服务 */
	private PolicyService policyService;
	private String updateExt = "";
	/** 自动提交调度参数 */
	private String strSchedule = "";
	/**是否关闭*/
	private String ifclose = "";
	/** 其他操作类型*/
	private String editTypeOther = "";
	/** 承保系统Url */
	private String core_URL = "";
	/** 保单在几天能的出险次数 */
	private String registViewLimitDay = "";
	/** 是否有过批改 */
	private int checkFlag = 0;
	/** 当前年份 */
	private int now_year = 0;
	/** 当前时间 */
	private String alterTime = "";// 修改时间
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業
	private String registSharingFlagDisabled = "false";

	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
	/** 是否顯示[是否為強制險區塊鏈攤賠案件] **/
	private String isCompulsoryBchainClaimDisabled = "false";
	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
	/** 结案数据收集 */
	private EndorseViewHelper endorseViewHelper;
	/** 结案服务 */
	private EndorseService endorseService;
	/** 赔案保单关联服务 */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 保险地址服务 */
	private PrpCaddressService prpCaddressService;
	/** 保单服务 */
	private PrpCmainService prpCmainService;
	/** 保单隶属信息服务 */
	private PrpCmainSubService prpCmainSubService;
	/** 保险关系人服务 */
	private PrpCinsuredService prpCinsuredService;
	/** 险别配置信息服务 */
	private PrpDriskConfigService prpDriskConfigService;

	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業
	private UtiUserGradeService utiUserGradeService;
	
	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	private HttpServletRequest webServHttpServletRequest;

	/**
	 * 报案查询前处理
	 * @return 页面类型
	 * @throws Exception
	 */
	public String registBeforeQuery() throws Exception {
		logger.info("准备查询报案信息");
		HttpServletRequest httpServletRequest = getRequest();
		if (pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == 0) {
			pageSize = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
		}
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		core_URL = AppConfig.get("sysconst.Core_URL");
		registViewLimitDay = AppConfig.get("sysconst.RegistViewLimitDay");
		editType = httpServletRequest.getParameter("editType");// 增加通过request获取参数，避免前台有两个editType，获取到的是editType=ADD，ADD的问题。
		String policyNo = httpServletRequest.getParameter("PolicyNo"); // 保单号
		String registNo = httpServletRequest.getParameter("RegistNo"); // 报案号
		String licenseNo = httpServletRequest.getParameter("LicenseNo"); // 车牌号
		String status = httpServletRequest.getParameter("caseFlag"); // 案件状态
		String operateDate = httpServletRequest.getParameter("OperateDate");// 操作时间
		String riskCode = httpServletRequest.getParameter("RiskCode");// 险种
		String insuredName = httpServletRequest.getParameter("InsuredName"); // 被保险人
		String cancelFlag = httpServletRequest.getParameter("cancelFlag"); // 是否注销
		// 投保人
		// 去掉status中最後一个逗号
		if (status != null && status.trim().length() > 0) {
			status = status.substring(0, status.length() - 1);
		}
		String registStartCancelDate = httpServletRequest.getParameter("registStartCancelDate");
		String registEndCancelDate = httpServletRequest.getParameter("registEndCancelDate");
		String registNoSign = httpServletRequest.getParameter("RegistNoSign");
		String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
		String riskCodeSign = httpServletRequest.getParameter("RiskCodeSign");
		String licenseNoSign = httpServletRequest.getParameter("LicenseNoSign");
		String operateDateSign = httpServletRequest.getParameter("OperateDateSign");
		String insuredNameSign = httpServletRequest.getParameter("InsuredNameSign");
		String appliNameCodeSign = httpServletRequest.getParameter("AppliNameCodeSign");
		WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
		workFlowQueryDto.setAppliNameCodeSign(appliNameCodeSign);
		workFlowQueryDto.setRegistStartCancelDate(registStartCancelDate);
		workFlowQueryDto.setRegistEndCancelDate(registEndCancelDate);
		workFlowQueryDto.setPolicyNo(policyNo);
		workFlowQueryDto.setRegistNo(registNo);
		workFlowQueryDto.setLicenseNo(licenseNo);
		workFlowQueryDto.setStatus(status);
		workFlowQueryDto.setOperateDate(operateDate);
		workFlowQueryDto.setRiskCode(riskCode);
		workFlowQueryDto.setInsuredName(insuredName);
		workFlowQueryDto.setCancelFlag(cancelFlag);
		workFlowQueryDto.setRegistNoSign(registNoSign);
		workFlowQueryDto.setPolicyNoSign(policyNoSign);
		workFlowQueryDto.setRiskCodeSign(riskCodeSign);
		workFlowQueryDto.setLicenseNoSign(licenseNoSign);
		workFlowQueryDto.setOperateDateSign(operateDateSign);
		workFlowQueryDto.setInsuredNameSign(insuredNameSign);
		String forward = ""; // 向前
		// 尚未加入type异常处理{}、其它必须参数异常处理{}
		// 1。报案一般的查询，查询理赔节点状态信息,整理输入，用於初始界面显示
		if (editType.equals("ADD") || editType.equals("EDIT")) {
			daaRegistViewHelper.setPrpLregistDtoToView(httpServletRequest, workFlowQueryDto);
			forward = "success";
		}
		checkFlag = endorseService.checkStatus(policyNo);
		// 报案查询分页
		if (editType.equals("SHOW")) {
			daaRegistViewHelper.setPrpLregistDtoToView(httpServletRequest, workFlowQueryDto);
			forward = "success";
		}
		// 2。报案撤消的查询
		if (editType.equals("DELETE")) {
			daaRegistViewHelper.registCancelDtoToView(httpServletRequest, registNo);
			forward = "registCancel";
		} // 3。录入报案前查询保单
		if (editType.equals("RegistBeforeQuery")) {
			// 需要进行翻页处理
			daaRegistViewHelper.policyListToView(httpServletRequest, pageNo, pageSize);
			forward = "target1";
		}
		if (editType.equals("PRINT")) {
			daaRegistViewHelper.setPrpLregistDtoToPrint(httpServletRequest, registNo, policyNo, insuredName, licenseNo);
			forward = "PRINT";
		}
		return forward;
	}

	/**
	 * 报案处理
	 * @return 页面类型
	 * @throws Exception
	 */
	public String registBeforeEdit() throws Exception {
		String forward = ""; // 向前
		try {
			logger.info("准备查询报案信息");
			java.util.Date inTime = new java.util.Date();
			HttpServletRequest httpServletRequest = getRequest();
			core_URL = AppConfig.get("sysconst.Core_URL");
			registViewLimitDay = AppConfig.get("sysconst.RegistViewLimitDay");
			editType = httpServletRequest.getParameter("editType");// 增加通过request获取参数，避免前台有两个editType，获取到的是editType=ADD，ADD的问题。
			// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
			String policyNo = httpServletRequest.getParameter("prpCmainPolicyNo"); // 保单号
			String strPolicyNo = httpServletRequest.getParameter("policyNo"); // 保单号
			String strRiskCode = "";
			String strClassCode = "";
			String strPolicyNo1 = "";
			String othFlag = httpServletRequest.getParameter("othFlag");
			String registNo = httpServletRequest.getParameter("prpLregistRegistNo"); // 报案号
			String damageDate = httpServletRequest.getParameter("damageDate");// 出险日期
			//damageDate = "2024-03-19";//dp0713 備案做資料 改出險時間
			String damageHour = httpServletRequest.getParameter("damageHour");// 出险小时
			httpServletRequest.setAttribute("registNo", registNo);
			String strSql = "";
			if (DataUtils.emptyToNull(registNo) != null) {
				PrpLregist prpLregist = this.prpLregistService.findPrpLregist(registNo);
				if (prpLregist != null) {
					strPolicyNo1 = prpLregist.getPolicyNo();
					damageDate = new DateTime(prpLregist.getDamageStartDate()).toString();// 出险日期
					damageHour = prpLregist.getDamageStartHour();// 出险小时
				}
			}
			String regisPolicyNo = "";
			if (DataUtils.emptyToNull(policyNo) != null) {
				strSql = " policyNo='" + policyNo + "'";
				regisPolicyNo = policyNo ;
			}
			if (DataUtils.emptyToNull(strPolicyNo) != null) {
				strSql = " policyNo='" + strPolicyNo + "'";
				regisPolicyNo = strPolicyNo ;
			}
			if (DataUtils.emptyToNull(strPolicyNo1) != null) {
				strSql = " policyNo='" + strPolicyNo1 + "'";
				regisPolicyNo = strPolicyNo1 ;
			}
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(strSql);
			List<PrpCaddress> prpCaddressList = prpCaddressService.findPrpCaddress(queryRule);
			checkFlag = endorseService.checkStatus(policyNo);
			List<PrpCmain> prpCmainList = prpCmainService.findPrpCmain(queryRule);
			StringBuffer strAddress = new StringBuffer();
			strAddress.append("");
			PrpCaddress prpCaddress = null;
			PrpCmain prpCmain = null;
			int count = prpCaddressList.size();
			for (int m = 0; m < prpCmainList.size(); m++) {
				prpCmain = prpCmainList.get(m);
				strRiskCode = prpCmain.getRiskCode();
				strClassCode = prpCmain.getClassCode();
			}
			for (int n = 0; n < count; n++) {
				prpCaddress = prpCaddressList.get(n);
				if (count > 1) {
					strAddress.append((n + 1) + "、");
					strAddress.append(prpCaddress.getAddressName());
					strAddress.append("\n");
				} else {
					strAddress.append(prpCaddress.getAddressName());
				}
			}
			httpServletRequest.setAttribute("strAddress", strAddress.toString());
			httpServletRequest.setAttribute("strRiskCode", strRiskCode);
			httpServletRequest.setAttribute("othFlag", othFlag);
			strSchedule = AppConfig.get("sysconst.SCHEDULE_AUTOCOMMIT");
			if ("D".equals(ConstantCodes.carClassMap.get(strClassCode))) {
				List<PrpCmainSub> prpCmainSubList = prpCmainSubService.findPrpCmainSub(queryRule);
				queryRule = QueryRule.getInstance();
				queryRule.addSql(strSql + " and insuredflag = '1'");
				List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(regisPolicyNo, damageDate, damageHour);
				PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, null, null);
				PrpCmainSub prpCmainSub = null;
				String remark = "";
				for (int temp = 0; temp < prpCmainSubList.size(); temp++) {
					prpCmainSub = prpCmainSubList.get(temp);
					remark = prpCmainSub.getRemark();
					httpServletRequest.setAttribute("remark", remark);
				}
				if (prpCinsured != null) {
					String postcode = prpCinsured.getPostCode();
					httpServletRequest.setAttribute("postcode", postcode);
				}
			}
			String riskCode = ""; // 险种
			forward = "";
			HttpSession session = httpServletRequest.getSession();
			UserDto user = (UserDto) session.getAttribute("user");
			
			//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
			// mantis：CLM0254，處理人員：DP0714，新核心-理賠權限問題調整，以利承保系統進行備案查詢 -- start
			if (user!=null) {
				List<String> list = this.utiUserGradeService.findGradeCodeByUserCode(user.getUserCode());
				if (list != null && !list.isEmpty()) {
					if(list.contains("005")){
						//(2)	同業已賠付radio button限制崗位代號【005-理賠人員】僅供查看不提供修改
						registSharingFlagDisabled="true";
					}
				}
			}
			// mantis：CLM0254，處理人員：DP0714，新核心-理賠權限問題調整，以利承保系統進行備案查詢 -- end
			//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
			//保存初始的请求路径 begin
			String statusTemp = httpServletRequest.getParameter("status");
			String editTypeTemp = httpServletRequest.getParameter("editType");
			String flushflag = httpServletRequest.getParameter("flushflag");
			if (!"true".equals(flushflag)) { //不是通过改变出险时间后刷新过来的请求
				session.setAttribute("editTypeTemp", editTypeTemp);
				String originalRequestURITemp ="";
				if ("ADD".equals(editTypeTemp)) {//处理报案登记
					String prpCmainPolicyNoTemp = httpServletRequest.getParameter("prpCmainPolicyNo");
					String damageDateTemp = httpServletRequest.getParameter("damageDate");
					String damageHourTemp = httpServletRequest.getParameter("damageHour");			
					originalRequestURITemp = "/claim/registBeforeEdit.do?prpCmainPolicyNo=" + prpCmainPolicyNoTemp + "&editType="+editTypeTemp+"&damageDate=" + damageDateTemp + "&damageHour=" + damageHourTemp;
				
				} else if ("EDIT".equals(editTypeTemp) || "SHOW".equals(editTypeTemp)) {//正在處理備案任務 和 已處理備案任務
					String prpLregistRegistNoTemp = httpServletRequest.getParameter("prpLregistRegistNo");
					String updateExtTemp = httpServletRequest.getParameter("updateExt");
					String swfLogFlowIDTemp = httpServletRequest.getParameter("swfLogFlowID");
					String swfLogLogNoTemp = httpServletRequest.getParameter("swfLogLogNo");
					String riskCodeTemp = httpServletRequest.getParameter("riskCode");
					String nodeTypeTemp = httpServletRequest.getParameter("nodeType");
					String businessNoTemp = httpServletRequest.getParameter("businessNo");
					String keyInTemp = httpServletRequest.getParameter("keyIn");
					String policyNoTemp = httpServletRequest.getParameter("policyNo");
					String modelNoTemp = httpServletRequest.getParameter("modelNo");
					String nodeNoTemp = httpServletRequest.getParameter("nodeNo");
					String dfFlagTemp = httpServletRequest.getParameter("dfFlag");
					String actorIdTemp = httpServletRequest.getParameter("actorId");
					String processIdTemp = httpServletRequest.getParameter("processId");
					originalRequestURITemp = "/claim/registFinishQueryList.do?prpLregistRegistNo="+prpLregistRegistNoTemp+"&updateExt="+updateExtTemp+"&swfLogFlowID="+swfLogFlowIDTemp+"&swfLogLogNo="+swfLogLogNoTemp+"&status="+statusTemp+"&riskCode="+riskCodeTemp+"&editType="+editTypeTemp+"&nodeType="+nodeTypeTemp+"&businessNo="+businessNoTemp+"&keyIn="+keyInTemp+"&policyNo="+policyNoTemp+"&modelNo="+modelNoTemp+"&nodeNo="+nodeNoTemp+"&dfFlag="+dfFlagTemp+"&actorId="+actorIdTemp+"&processId="+processIdTemp;
				}else if ("PERFECT".equals(editTypeTemp)) {//报案修改
					String prpLregistRegistNoTemp = httpServletRequest.getParameter("prpLregistRegistNo");
					String prpCmainPolicyNoTemp = httpServletRequest.getParameter("prpCmainPolicyNo");
					originalRequestURITemp ="/claim/regist/registBeforeEdit.do?editType="+editTypeTemp+"&prpLregistRegistNo="+prpLregistRegistNoTemp+"&prpCmainPolicyNo="+prpCmainPolicyNoTemp;
				}
					session.setAttribute("originalRequestURITemp", originalRequestURITemp);
			}
			//保存初始的请求路径 end			
			// 如果以商业保单查询-----------------
			// 注： 1。获取是否保强三标志qsflag
			// 2。获取强三保单 mainPolicyNo
			String mainPolicyNo = "";
			String quaryPolicyNo = policyNo;
			String relateMainPolicyNo = ""; // 关联强三保单
			String relatePolicyNo = ""; // 关联商业保单
			String flag = "";
			String qsFlag = "N"; // N：没有关联 Y：有关联
			String isMainPolicyNo = "N";// 是否是以交强险保单查询 N：不是 Y：是
			int intPayFee = 0;
			boolean qs_valid = false;
			boolean sy_valid = false;
			PrpCmain qs_prpCmain = new PrpCmain();
			// 取得强制保险的险种代码
			String compelRiskCode = BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ");
			if ("TimeOut".equals(editType))
				editType = "SHOW";
			// 尚未加入type异常处理{}、其它必须参数异常处理{}
			// 1。查询保单信息,整理输入，用於初始界面显示
			if (editType.equals("ADD")) {
//				PolicyDto policySub = new PolicyDto();
				PrpCmainSub prpCmainSub = new PrpCmainSub();
				PrpCmain qs_prpCmainTemp = new PrpCmain();
				// 山东见费出单批改时判断本期是否实收
				UIPrpJFeeCheck uiPrpJFeeCheck = new UIPrpJFeeCheck();
				if (uiPrpJFeeCheck.IsInDebt("P", policyNo, new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).toString())) {
					throw new UserException(-98, -2, "UIEndorSpecialSubmit", getText("regist.refuseOfPremium"));//此保單當前存在未按期繳納的保費，不允許報案！
				}
				// 山东见费出单批改时判断本期是否实收
				/**  備案優化處理，需要什麼查什麼，替換直接使用大保單  */
				//PolicyDto policyDto = policyService.findByPrimaryKey(policyNo);
				prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate, damageHour);
				queryRule = QueryRule.getInstance();
				queryRule.addSql(" ( mainpolicyno= '" + policyNo + "' or  policyno= '" + policyNo + "' ) and flag = '111' ");
				List<PrpCmainSub> prpCmainSubList = this.getPrpCmainSubService().findPrpCmainSub(queryRule);
				String riskType = codeService.translateRiskCodetoRiskType(prpCmain.getRiskCode());
				if (prpCmain != null && ConstantCodes.CLASSCODE_D.equals(riskType)) {// 車險保單
					if (!CommonUtils.isEmpty(prpCmainSubList)) {// 商業險或者關聯單存在mainsub數據。
						for (int i = 0; i < prpCmainSubList.size(); i++) {
							// 出险时间在保险期限内 && flag[2]=1
							prpCmainSub = (PrpCmainSub) prpCmainSubList.get(i);
							relateMainPolicyNo = prpCmainSub.getId().getMainPolicyNo();
							relatePolicyNo = prpCmainSub.getId().getPolicyNo();
							flag = prpCmainSub.getFlag();
							// 如果强制保单号码是错误的，则不加理会。
							if (!policyService.isExist(relateMainPolicyNo)){
								continue;
							}
							if (!endorseViewHelper.checkRelate(relatePolicyNo , relateMainPolicyNo, damageDate, damageHour )) {
								continue;
							}
							if (flag.length() > 1 && flag.substring(0, 1).equals("1")) {
								if (relatePolicyNo.equals(policyNo)) { // 以商业保单查询
									//policySub = endorseViewHelper.findForEndorBefore(relateMainPolicyNo, damageDate, damageHour);
									qs_prpCmainTemp = this.endorseViewHelper.findPrpCmain(relateMainPolicyNo, damageDate, damageHour);
									if (damageHour == null || "".equals(damageHour)) {
										damageHour = "0";
									}
									sy_valid = daaRegistViewHelper.checkDate(httpServletRequest, relatePolicyNo, damageDate, Integer.parseInt(damageHour));
									qs_valid = daaRegistViewHelper.checkDate(httpServletRequest, relateMainPolicyNo, damageDate, Integer.parseInt(damageHour));
									if (qs_valid && sy_valid) { // 查询到关联的有效强三保单
										policyNo = relatePolicyNo;
										mainPolicyNo = relateMainPolicyNo;
										qsFlag = "Y";
										isMainPolicyNo = "N";
										qs_prpCmain = qs_prpCmainTemp;
										intPayFee = daaRegistViewHelper.checkPay(httpServletRequest, mainPolicyNo);
										daaRegistViewHelper.getQsRegistInfo(httpServletRequest, mainPolicyNo);
										break;
									}
								} else { // 以强三保单查询
									//policySub = endorseViewHelper.findForEndorBefore(relatePolicyNo, damageDate, damageHour);
									qs_prpCmainTemp = this.endorseViewHelper.findPrpCmain(relatePolicyNo, damageDate, damageHour);
									if ("".equals(damageHour)) {
										damageHour = "0";
									}
									qs_valid = daaRegistViewHelper.checkDate(httpServletRequest, relateMainPolicyNo, damageDate, Integer.parseInt(damageHour));
									sy_valid = daaRegistViewHelper.checkDate(httpServletRequest, relatePolicyNo, damageDate, Integer.parseInt(damageHour));
									if (sy_valid && qs_valid) { // 查询到关联的有效商业保单
										policyNo = relatePolicyNo;
										mainPolicyNo = relateMainPolicyNo;
										qsFlag = "Y";
										isMainPolicyNo = "Y";
										qs_prpCmain = qs_prpCmainTemp;
										intPayFee = daaRegistViewHelper.checkPay(httpServletRequest, mainPolicyNo);
										daaRegistViewHelper.getQsRegistInfo(httpServletRequest, mainPolicyNo);
										break;
									}
								}
							}
						}
					} /*else {
						isMainPolicyNo = "Y";
						mainPolicyNo = policyNo;
					}*/
				}
				policyNo = policyNo.trim();
				httpServletRequest.setAttribute("quaryPolicyNo", quaryPolicyNo);
				httpServletRequest.setAttribute("intPayFee", String.valueOf(intPayFee));
				httpServletRequest.setAttribute("qs_prpCmainDto", qs_prpCmain);
				httpServletRequest.setAttribute("mainPolicyNo", mainPolicyNo);
				httpServletRequest.setAttribute("qsFlag", qsFlag);
				httpServletRequest.setAttribute("isMainPolicyNo", isMainPolicyNo);
				// 强制保单关联信息写到报案中
				Prplregistrpolicy prpLRegistRPolicyOfCompel = null;
				if ("Y".equals(qsFlag)) {
					prpLRegistRPolicyOfCompel = new Prplregistrpolicy();
					prpLRegistRPolicyOfCompel.getId().setPolicyNo(mainPolicyNo);
					prpLRegistRPolicyOfCompel.setRiskCode(compelRiskCode);
				}
				httpServletRequest.setAttribute("prpLregistRPolicyNo", prpLRegistRPolicyOfCompel);
				if (httpServletRequest.getParameter("flushflag") != null) {// 修改出险时间,保留修改部分前的数据。
					daaRegistViewHelper.setRegistDtoView(httpServletRequest, null);
				} else {// 正常备案
					daaRegistViewHelper.policyDtoToView(httpServletRequest, policyNo, damageDate, damageHour);
				}
				riskCode = BusinessRuleUtil.getRiskCode(policyNo, "PolicyNo");
				httpServletRequest.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
				httpServletRequest.setAttribute("RISKCODE", riskCode);
			}
			// 2。查询保单信息,整理输入，用於初始界面显示
			if (editType.equals("EDIT") || editType.equals("SHOW") || editType.equals("DELETE") || editType.equals("TimeOut") || editType.equals("PERFECT")) {
				Prplregistrpolicy prpLRegistRPolicy = new Prplregistrpolicy();
				Collection<?> collection = prpLregistrpolicyService.findByRegistNo(registNo);
				if (collection != null && collection.size() > 1) {
					qsFlag = "Y";
					Iterator<?> it = collection.iterator();
					while (it.hasNext()) {
						prpLRegistRPolicy = (Prplregistrpolicy) it.next();
						if (prpLRegistRPolicy.getPolicyType().equals(Prplregistrpolicy.COMPEL_POLICY)) {
							mainPolicyNo = prpLRegistRPolicy.getId().getPolicyNo();
//							PolicyDto policyDto = policyService.findByPrimaryKey(mainPolicyNo);
							qs_prpCmain = policyService.findPrpCmainDtoByPrimaryKey(mainPolicyNo);
							intPayFee = daaRegistViewHelper.checkPay(httpServletRequest, mainPolicyNo);
						}
						if (prpLRegistRPolicy.getRegistFlag().equals("1")) {
							quaryPolicyNo = prpLRegistRPolicy.getId().getPolicyNo();
						}
					}
				} else if (collection != null && collection.size() == 1) {
					Iterator<?> it = collection.iterator();
					while (it.hasNext()) {
						prpLRegistRPolicy = (Prplregistrpolicy) it.next();
						quaryPolicyNo = prpLRegistRPolicy.getId().getPolicyNo();
					}
				} else {
					// 历史数据？
				}
				httpServletRequest.setAttribute("quaryPolicyNo", quaryPolicyNo);
				httpServletRequest.setAttribute("intPayFee", String.valueOf(intPayFee));
				httpServletRequest.setAttribute("qs_prpCmainDto", qs_prpCmain);
				httpServletRequest.setAttribute("mainPolicyNo", mainPolicyNo);
				httpServletRequest.setAttribute("qsFlag", qsFlag);
				httpServletRequest.setAttribute("editType", editType);
				// 强制保单关联信息写到报案中
				Prplregistrpolicy prpLRegistRPolicyOfCompel = null;
				if ("Y".equals(qsFlag)) {
					prpLRegistRPolicyOfCompel = new Prplregistrpolicy();
					prpLRegistRPolicyOfCompel.getId().setPolicyNo(mainPolicyNo);
					prpLRegistRPolicyOfCompel.setRiskCode(compelRiskCode); // 先暂时写定。。以後修改
				}
				httpServletRequest.setAttribute("prpLregistRPolicyNo", prpLRegistRPolicyOfCompel);
				daaRegistViewHelper.setRegistDtoView(httpServletRequest, registNo);
				registNo = registNo.trim();
				riskCode = BusinessRuleUtil.getRiskCode(registNo, "RegistNo");
				httpServletRequest.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
				httpServletRequest.setAttribute("RISKCODE", riskCode);
			}
			if (editType.equals("EDIT") || editType.equals("ADD") || editType.equals("DELETE") || editType.equals("PERFECT")) {
				String configValue = ""; // 是否是准许团单免导的险种 1表示准许
				configValue = prpDriskConfigService.getConfigValue("ALLOW_TERM_TYPE", riskCode);
				if ("1".equals(configValue)) {
					httpServletRequest.setAttribute("termFlag", "1");
				} else {
					httpServletRequest.setAttribute("termFlag", "0");
				}
			}
			PrpLregist prpLregist = (PrpLregist) httpServletRequest.getAttribute("prpLregist");

			//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
			//頁面需求 ，WS不需要
			if (ConstantCodes.RISKCODE_DAA.equals(riskCode) || ConstantCodes.RISKCODE_DAZ.equals(riskCode)) {
				isCompulsoryBchainClaimDisabled = "false";//車險開啟 顯示[是否為強制險區塊鏈攤賠案件]
				if(null==prpLregist.getIsCompulsoryBchainClaim() || prpLregist.getIsCompulsoryBchainClaim()==""){
					prpLregist.setIsCompulsoryBchainClaim("N");
				}
			}
			//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
			if (DataUtils.emptyToNull(prpLregist.getRegistType()) == null) {
				if ("Y".equals(qsFlag)) {
					prpLregist.setRegistType("2");// 可关联报案的单子默认关联报案
				} else {
					if (ConstantCodes.RISKCODE_DAZ.equals(riskCode)) {// 强制险险种
						prpLregist.setRegistType("1");
					} else {
						prpLregist.setRegistType("0");
					}
				}
			}
			// 取得forward
			forward = BusinessRuleUtil.getForward(httpServletRequest, riskCode, "regis", editType, 1);
			httpServletRequest.setAttribute("com_sinosoft_forward", forward);
			java.util.Date outTime = new java.util.Date();
			long between = (outTime.getTime() - inTime.getTime()) / 1000;// 除以1000是为了转换成秒
			if (between > 120) {
				Log.init("CPU_Max_Error", "CPU_Max_Error", true);
				Log.println(new java.util.Date() + "====editType===" + editType + "===" + "policyNo===" + policyNo + "===" + "registNo===" + registNo + "===" + "user===" + user.getUserCode() + "===" + "TimeUsed==" + between);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return forward;
	}

	/**
	 * 报案暂存、提交
	 * @return 页面类型
	 * @throws Exception
	 */
	public String registEditPost() throws Exception {
		logger.info("准备查询报案信息");
		editType = "query";
		return SUCCESS;
	}

	/**
	 * 准备增加报案信息
	 * @return 页面类型
	 * @throws Exception
	 */
	public String prepareAdd() throws Exception {
		logger.info("准备增加报案信息");
		editType = "add";
		return SUCCESS;
	}

	/**
	 * 保存报案信息
	 * @return 页面类型
	 */
	public String add() throws Exception {
		/*
		 * SessionAgentBean agentBean = SessionAgentManager .getSessionAgentBean
		 * (this.getSession());// 获取登录用户信息 if (null != agentBean) {// 判断登录用户是否存在
		 * prpLclaim .setCreateCode(agentBean.getAgentId ());
		 * prpLclaim.setCreateName(agentBean .getAgentName()); }
		 * prpLclaimService.save(prpLclaim);
		 * getRequest().setAttribute("message", "保存成功，报案编号:" +
		 * prpLclaim.getSerialNo());
		 */
		return SUCCESS;
	}

	/**
	 * 准备查询报案信息
	 * @return 页面类型
	 * @throws Exception
	 */
	public String prepareQuery() throws Exception {
		logger.info("准备查询报案信息");
		editType = "query";
		return SUCCESS;
	}

	/**
	 * 查询报案信息 查询条件 礼品编号,礼品名称,二级机构,礼品状态,预警标识,方案制定状态,登记日期,
	 * @return 页面类型
	 */
	public String query() throws Exception {
		return NONE;
	}
	
    /**
     * 以列表形式显示报案信息
     * @return
     * @throws Exception
     */
	public String showPrpLclaimList() throws Exception {
		return NONE;
	}

	/**
	 * 准备编辑
	 * @return 页面类型
	 */
	public String prepareEdit() throws Exception {
		/*
		 * editType = "edit"; this.prpLclaim =
		 * prpLclaimService.findPrpLclaim(serialNo);
		 */
		return SUCCESS;
	}

	/**
	 * 修改报案信息
	 * @return 页面类型
	 */
	public String edit() throws Exception {
		// 编辑数据
		/*
		 * prpLclaimService.edit(prpLclaim);
		 * getRequest().setAttribute("message", "修改成功，报案编号:" +
		 * prpLclaim.getSerialNo());
		 */
		return SUCCESS;
	}

	/**
	 * 删除报案信息
	 * @return 页面类型
	 */
	public String delete() throws Exception {
		/*
		 * logger.info("删除报案编号为:" + serialNo);
		 * prpLclaimService.delete(serialNo);
		 */
		return NONE;
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

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public ClaimService getClaimService() {
		return claimService;
	}

	public void setClaimService(ClaimService claimService) {
		this.claimService = claimService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public DAARegistViewHelper getDaaRegistViewHelper() {
		return daaRegistViewHelper;
	}

	public void setDaaRegistViewHelper(DAARegistViewHelper daaRegistViewHelper) {
		this.daaRegistViewHelper = daaRegistViewHelper;
	}

	public String getUpdateExt() {
		return updateExt;
	}

	public void setUpdateExt(String updateExt) {
		this.updateExt = updateExt;
	}

	public String getStrSchedule() {
		return strSchedule;
	}

	public void setStrSchedule(String strSchedule) {
		this.strSchedule = strSchedule;
	}

	public String getIfclose() {
		return ifclose;
	}

	public void setIfclose(String ifclose) {
		this.ifclose = ifclose;
	}

	public String getEditTypeOther() {
		return editTypeOther;
	}

	public void setEditTypeOther(String editTypeOther) {
		this.editTypeOther = editTypeOther;
	}

	public String getCore_URL() {
		return core_URL;
	}

	public void setCore_URL(String core_URL) {
		this.core_URL = core_URL;
	}

	public String getRegistViewLimitDay() {
		return registViewLimitDay;
	}

	public void setRegistViewLimitDay(String registViewLimitDay) {
		this.registViewLimitDay = registViewLimitDay;
	}

	public int getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(int checkFlag) {
		this.checkFlag = checkFlag;
	}

	public int getNow_year() {
		if (now_year < 0) {
			now_year = DateTime.current().getYear();
		}
		return now_year;
	}

	public void setNow_year(int now_year) {
		this.now_year = now_year;
	}

	public String getAlterTime() {
		if (alterTime == null || alterTime.length() < 1) {
			alterTime = DateTime.current().toString(DateTime.YEAR_TO_SECOND);
		}
		return alterTime;
	}

	public void setAlterTime(String alterTime) {
		this.alterTime = alterTime;
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

	public EndorseService getEndorseService() {
		return endorseService;
	}

	public void setEndorseService(EndorseService endorseService) {
		this.endorseService = endorseService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		return prpLregistrpolicyService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public PrpCaddressService getPrpCaddressService() {
		return prpCaddressService;
	}

	public void setPrpCaddressService(PrpCaddressService prpCaddressService) {
		this.prpCaddressService = prpCaddressService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpCmainSubService getPrpCmainSubService() {
		return prpCmainSubService;
	}

	public void setPrpCmainSubService(PrpCmainSubService prpCmainSubService) {
		this.prpCmainSubService = prpCmainSubService;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
	public UtiUserGradeService getUtiUserGradeService() {
		return utiUserGradeService;
	}

	public void setUtiUserGradeService(UtiUserGradeService utiUserGradeService) {
		this.utiUserGradeService = utiUserGradeService;
	}

	public String getRegistSharingFlagDisabled() {
		return registSharingFlagDisabled;
	}

	public void setRegistSharingFlagDisabled(String registSharingFlagDisabled) {
		this.registSharingFlagDisabled = registSharingFlagDisabled;
	}	
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 Start

	public HttpServletRequest getWebServHttpServletRequest() {
		return webServHttpServletRequest;
	}
	public void setWebServHttpServletRequest(
			HttpServletRequest webServHttpServletRequest) {
		this.webServHttpServletRequest = webServHttpServletRequest;
	}
	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 END
	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
	public String getIsCompulsoryBchainClaimDisabled() {
		return isCompulsoryBchainClaimDisabled;
	}
	
	public void setIsCompulsoryBchainClaimDisabled(
			String isCompulsoryBchainClaimDisabled) {
		this.isCompulsoryBchainClaimDisabled = isCompulsoryBchainClaimDisabled;
	}
	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
}