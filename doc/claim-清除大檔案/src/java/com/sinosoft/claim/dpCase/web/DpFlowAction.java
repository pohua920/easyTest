package com.sinosoft.claim.dpCase.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ins.framework.common.DateTime;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.lang3.StringUtils;

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
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDautoDpLog;
import com.sinosoft.claim.schema.model.PrpDautoDpLogId;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLcheck;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLdeductCond;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfoId;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.model.UwNotion;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.claim.schema.service.facade.PrpDautoDpLogService;
import com.sinosoft.claim.schema.service.facade.PrpLcheckService;
import com.sinosoft.claim.schema.service.facade.PrpLdeductCondService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.claim.schema.service.facade.UwNotionService;
import com.sinosoft.claim.ui.control.action.UIQuickCaseAction;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.tlg.commons.util.api.rest.adLogin.util.IPUtil;

/**
 * mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
 */
public class DpFlowAction extends Struts2Action {
	@Resource
	private WebServiceContext wsCtxt;

	private static final long serialVersionUID = 1L;

	/**工作流viewHelper*/
	private WorkFlowViewHelper workFlowViewHelper;

	

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
	/** 工作流日志服务 */
	private SwfLogService swfLogService;
	/** 工作流处理服务 */
	private WorkFlowService workFlowService;
	/** 核心地址 */
	private String coreURL;
	
	private CommonService commonService;
	
	private PrpLpersonLossService PrpLpersonLossService;
	
	private PrpDautoDpLogService prpDautoDpLogService;
	
	/** 人员级别设置信息服务 */
	private UtiUwLevelService utiUwLevelService;
	
	private String nodeType = "";

	/**
	 * 查询工作流状态信息,整理输入，用於初始界面显示
	 * @return
	 * @throws Exception
	 */
	public String payObjectInfoQuery() throws Exception {
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String BusinessNo = request.getParameter("BusinessNo");
		String InputStatusCondition = request.getParameter("InputStatusCondition");
		request.setAttribute("InputStatusCondition", InputStatusCondition);
		System.out.println("InputStatusCondition:"+InputStatusCondition);
		String nodeType = request.getParameter("nodeType");
		if(StringUtils.isBlank(nodeType)){
			nodeType = "nodeType";
			request.setAttribute("nodeType", nodeType);
		}
		String status = request.getParameter("status"); // 立案号
		if(StringUtils.isBlank(status)){
			status = "4";
			request.setAttribute("status", status);
		}
		String method = request.getParameter("method");
		request.setAttribute("method", method);
		UserDto user = (UserDto) request.getSession().getAttribute("user");
		String comLevel = "";
		// 看看是否是总公司-----------------------------
		comLevel = user.getComLevel();
		request.setAttribute("comLevel", comLevel);
		
		String operatorCode = request.getParameter("operatorCode");
		String preOperatorCode = request.getParameter("preOperatorCode");
		request.setAttribute("operatorCode", operatorCode);
		String queryConditionChange = request.getParameter("queryConditionChange");
		
		
		// 查询工作流状态信息,整理输入，用於初始界面显示
		// 需要进行翻页处理
		// 每页显示的行数
		String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null || pageNo.trim().equals("") || !operatorCode.equals(preOperatorCode) || queryConditionChange.equals("1") )
			pageNo = "1";
		try {
			this.getWorkFlowViewHelper().getWorkFlowLogListForDpFlow(request,Integer.parseInt(pageNo),Integer.parseInt(recordPerPage));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "dpFlowQuery";
	}
	
	/**
	 * 理算处理前信息
	 * @return
	 * @throws Exception
	 */
	public String payObjectInfoEdit() throws Exception {
		coreURL = AppConfig.get("sysconst.Core_URL");
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		// 业务类型：ADD-新增 EDIT-修改 SHOW-显示 DELETE-删除
		String compensateNo = request.getParameter("prpLcompensateCompensateNo"); // 赔款计算书号
		String riskCode = request.getParameter("riskCode");// 险种
		request.setAttribute("riskCode", riskCode);
		String caseType = request.getParameter("caseType");// 特殊赔案标志
		String forward = ""; // 向前
		request.setAttribute("logId", java.util.UUID.randomUUID());
		try {
			UserDto user = (UserDto) request.getSession().getAttribute("user");
			String strRiskType = this.getCodeService().translateRiskCodetoRiskType(riskCode);
			String operatorCode = request.getParameter("operatorCode");//0:一般修改作業/1:審核作業
			
			// 2.修改和查询显示的过程
//			if ("EDIT".equals(editType) || "SHOW".equals(editType) || "DELETE".equals(editType)) {
				// 查询实赔信息,整理输入，用於初始界面显示
				CompensateDto compensateDto = this.compensateService.findByPrimaryKey(compensateNo, caseType);
				PrpLcompensate prpLcompensate = null;
				if (compensateDto != null) {
					prpLcompensate = compensateDto.getPrpLcompensate();
					if (compensateDto.getPrpLcompensate() == null) {
						//計算書		"計算書信息不存在！"
						throw new UserException(0, 0, getText("check.calculation"), getText("prompt.compensate.pagesNotExist"));
					}
//					claimNo = prpLcompensate.getClaimNo().trim();
					Map<String, Double> limitMap = new HashMap<String, Double>();
					// 车险 和 非车 计算书 走不同 ViewHelper
					if (ConstantCodes.CLASSCODE_D.equals(strRiskType)) {
						this.sunnyCompensateViewHelper.compensateDtoView(request, compensateNo, editType);
					} else {
						this.accidentCompensateViewHelper.compensateDtoView(request, compensateNo, editType);
					}
				}
				PrpLpayObjectInfo prpLpayObjectInfo = (PrpLpayObjectInfo)request.getAttribute("prpLpayObjectInfo");
				List<PrpLpayObjectInfo> prpLpayObjectInfoList= prpLpayObjectInfo.getPrpLpayObjectInfoList();
						//調出審核中的資料
				PrpDautoDpLog prpDautoDpLog = new PrpDautoDpLog();
				prpDautoDpLog.setCompensateNo(compensateNo);
				prpDautoDpLog.setInputStatus("1");
				//這裡query prpdautodplog
				List<PrpDautoDpLog> prpDautoDpLogList = this.getPrpDautoDpLogService().findPrpDautoDpLogStatus(prpDautoDpLog);
				String modifyColumn = "";
				for(PrpDautoDpLog pdLogInfo:prpDautoDpLogList){
					if(null!=prpLpayObjectInfoList && prpLpayObjectInfoList.size()>0){
						for(PrpLpayObjectInfo payInfo:prpLpayObjectInfoList){
							System.out.println("payInfo:"+payInfo +( payInfo.getId().getSerialNo().equals(pdLogInfo.getSerialNo())));
							if(payInfo.getId().getSerialNo().toString().equals(pdLogInfo.getSerialNo()) &&
								payInfo.getId().getCompensateNo().equals(pdLogInfo.getCompensateNo()) &&
								payInfo.getId().getCertiType().equals(pdLogInfo.getCertiType())){
									request.setAttribute("changeCheckBoxNumber", payInfo.getId().getSerialNo());
									
									//把 prpLpayObjectInfo調出來 更改為最新的  當
									if(pdLogInfo.getId().getColumnName().equals("OWNERNAME")){
										payInfo.setOwnerName(pdLogInfo.getValue());
										modifyColumn+=","+pdLogInfo.getId().getColumnName();
									}
									if(pdLogInfo.getId().getColumnName().equals("UNIFORMNO")){
										payInfo.setUniformNo(pdLogInfo.getValue());
										modifyColumn+=","+pdLogInfo.getId().getColumnName();
									}
									if(pdLogInfo.getId().getColumnName().equals("ACCOUNTCODE")){
										payInfo.setAccountCode(pdLogInfo.getValue());
										modifyColumn+=","+pdLogInfo.getId().getColumnName();
									}
									//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START
									if(pdLogInfo.getId().getColumnName().equals("BANKCODE")){
										payInfo.setBankCode(pdLogInfo.getValue());
										modifyColumn+=","+pdLogInfo.getId().getColumnName();
									}
									if(pdLogInfo.getId().getColumnName().equals("BANKNAME")){
										payInfo.setBankName(pdLogInfo.getValue());
										modifyColumn+=","+pdLogInfo.getId().getColumnName();
									}
									if(pdLogInfo.getId().getColumnName().equals("CUSTOMBANKCODE")){
										payInfo.setCustomBankCode(pdLogInfo.getValue());
										modifyColumn+=","+pdLogInfo.getId().getColumnName();
									}
									if(pdLogInfo.getId().getColumnName().equals("CUSTOMBANKNAME")){
										payInfo.setCustomBankName(pdLogInfo.getValue());
										modifyColumn+=","+pdLogInfo.getId().getColumnName();
									}
									//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 END
									request.setAttribute("logId", pdLogInfo.getId().getLogId());
							}
						}
					}
			}
			request.setAttribute("modifyColumn", modifyColumn.length()>0?modifyColumn.substring(1):"");
			//查出PRPDAUTODPLOG inputstatus 有 1的資料 表 有審核中 就帶資料進去不可以更改 並且鎖上
			if(null!=prpDautoDpLogList && prpDautoDpLogList.size()>0 && 
					prpDautoDpLogList.get(0).getInputStatus().equals("1")){//有審核中
				request.setAttribute("inputStatus", prpDautoDpLogList.get(0).getInputStatus());//inputStatus等於1的  且operatorCode 0:一般修改作業>> 不可以更改
				request.setAttribute("inputUserCode", prpDautoDpLogList.get(0).getInputUser());
				request.setAttribute("logId", prpDautoDpLogList.get(0).getId().getLogId());
				
				//01.查出inputUserCode 的 lv
				String conditionsInput = "";
				String conditionsReview= "";
				int inputLvMax = 0;
				int reviewLvMax = 0;
				conditionsInput = " VALIDSTATUS  = '1' AND USERCODE = '" + prpDautoDpLogList.get(0).getInputUser() + "'";
				List<UtiUwLevel> utiUwLevelListInput = this.getUtiUwLevelService().findByConditions(conditionsInput);
				if(null!=utiUwLevelListInput && utiUwLevelListInput.size()>0){
					for(UtiUwLevel utiUwLevel:utiUwLevelListInput){
						if(null!=utiUwLevel)
							inputLvMax = utiUwLevel.getId().getNodeNo()>inputLvMax?utiUwLevel.getId().getNodeNo():inputLvMax;
					}
				}
				System.out.println(prpDautoDpLogList.get(0).getInputUser()+"inputLvMax:"+inputLvMax);
				request.setAttribute("inputLvMax", inputLvMax);
				
				//02.查出reviewUserCode的 lv(登入的)
				conditionsReview = " VALIDSTATUS  = '1' AND USERCODE = '" + user.getUserCode() + "'";
				List<UtiUwLevel> utiUwLevelListReview = this.getUtiUwLevelService().findByConditions(conditionsReview);
				if(null!=utiUwLevelListReview && utiUwLevelListReview.size()>0){
					for(UtiUwLevel utiUwLevel:utiUwLevelListReview){
						if(null!=utiUwLevel)
							reviewLvMax = utiUwLevel.getId().getNodeNo()>reviewLvMax?utiUwLevel.getId().getNodeNo():reviewLvMax;
					}
				}
				System.out.println(user.getUserCode()+"reviewLvMax:"+reviewLvMax);
				request.setAttribute("reviewLvMax", reviewLvMax);
				
				//operatorCode = 1 >審核作業
					//01 必須小於  02(登入的) 可以審核這筆嗎?
					request.setAttribute("reviewOfPower", (reviewLvMax>inputLvMax));
					
			}
			//inputstatus 是 1 
			request.setAttribute("compensateNo", compensateNo);
			if (user == null) {
				user = new UserDto();
			}
			user.setRiskCode(riskCode);
			request.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
//			request.setAttribute("content", "發生錯誤，請聯繫資訊人員！");
//			return "FFFF";
		}
		System.out.println("forward:"+forward);
		
		return "success";
	}
	
	public String payObjectInfoCheck() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		
		String compensateNo = httpServletRequest.getParameter("compensateNo");
		PrpDautoDpLog prpDautoDpLogQurey = new PrpDautoDpLog();
		prpDautoDpLogQurey.setCompensateNo(compensateNo);
		prpDautoDpLogQurey.setInputStatus("1");
		
		
		//這裡query prpdautodplog
		List<PrpDautoDpLog> prpDautoDpLogList = this.getPrpDautoDpLogService().findPrpDautoDpLogStatus(prpDautoDpLogQurey);
		if(prpDautoDpLogList!=null && prpDautoDpLogList.size()>0){
			httpServletRequest.setAttribute("content", "案件已經在審核中，請不要重復送件！");
			return "success";
		}
		String logId = httpServletRequest.getParameter("logId");
		String riskCode = httpServletRequest.getParameter("riskCode");
		String[] changeCheckBox = httpServletRequest.getParameterValues("changeCheckBox");//第幾筆 +key
		String[] prpLpayObjectInfoSerialNo = httpServletRequest.getParameterValues("prpLpayObjectInfoSerialNo");
		String[] prpLpayObjectInfoCertiType = httpServletRequest.getParameterValues("prpLpayObjectInfoCertiType");
		
		String[] prpLpayObjectInfoOwnerName = httpServletRequest.getParameterValues("prpLpayObjectInfoOwnerName");
		String[] prpLpayObjectInfoOwnerNameOrg = httpServletRequest.getParameterValues("ownerNameOrg");
		String[] prpLpayObjectInfoUniformNo = httpServletRequest.getParameterValues("prpLpayObjectInfoUniformNo");
		String[] prpLpayObjectInfoUniformNoOrg = httpServletRequest.getParameterValues("uniformNoOrg");
		String[] prpLpayObjectInfoAccountCode = httpServletRequest.getParameterValues("prpLpayObjectInfoAccountCode");
		String[] prpLpayObjectInfoAccountCodeOrg = httpServletRequest.getParameterValues("accountCodeOrg");
		
		//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START
		String[] prpLpayObjectInfoBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoBankCode");
		String[] prpLpayObjectInfoBankCodeOrg = httpServletRequest.getParameterValues("bankCodeOrg");
		String[] prpLpayObjectInfoBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoBankName");
		String[] prpLpayObjectInfoBankNameOrg = httpServletRequest.getParameterValues("bankNameOrg");
		String[] prpLpayObjectInfoCustomBankCode = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankCode");
		String[] prpLpayObjectInfoCustomBankCodeOrg = httpServletRequest.getParameterValues("customBankCodeOrg");
		String[] prpLpayObjectInfoCustomBankName = httpServletRequest.getParameterValues("prpLpayObjectInfoCustomBankName");
		String[] prpLpayObjectInfoCustomBankNameOrg = httpServletRequest.getParameterValues("customBankNameOrg");
		//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位END
		
		for(int i=0;i<changeCheckBox.length;i++){
			int checkBoxChecked = Integer.parseInt(changeCheckBox[i],10);
			String serialNo = (String)prpLpayObjectInfoSerialNo[checkBoxChecked];
			String certiType = (String)prpLpayObjectInfoCertiType[checkBoxChecked];

			String ownerName = (String)prpLpayObjectInfoOwnerName[checkBoxChecked];
			String ownerNameOrg = (String)prpLpayObjectInfoOwnerNameOrg[checkBoxChecked];
			String uniformNo = (String)prpLpayObjectInfoUniformNo[checkBoxChecked];
			String uniformNoOrg = (String)prpLpayObjectInfoUniformNoOrg[checkBoxChecked];
			String accountCode = (String)prpLpayObjectInfoAccountCode[checkBoxChecked];
			String accountCodeOrg = (String)prpLpayObjectInfoAccountCodeOrg[checkBoxChecked];

			//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START
			String bankCode = (String)prpLpayObjectInfoBankCode[checkBoxChecked];
			String bankCodeOrg = (String)prpLpayObjectInfoBankCodeOrg[checkBoxChecked];
			String bankName = (String)prpLpayObjectInfoBankName[checkBoxChecked];
			String bankNameOrg = (String)prpLpayObjectInfoBankNameOrg[checkBoxChecked];
			String customBankCode = (String)prpLpayObjectInfoCustomBankCode[checkBoxChecked];
			String customBankCodeOrg = (String)prpLpayObjectInfoCustomBankCodeOrg[checkBoxChecked];
			String customBankName = (String)prpLpayObjectInfoCustomBankName[checkBoxChecked];
			String customBankNameOrg = (String)prpLpayObjectInfoCustomBankNameOrg[checkBoxChecked];
			//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 END
			
			if(!ownerName.equals(ownerNameOrg)){
				PrpDautoDpLogId prpDautoDpLogId = new PrpDautoDpLogId();
				prpDautoDpLogId.setColumnName("OWNERNAME");
				prpDautoDpLogId.setLogId(logId);
				
				PrpDautoDpLog prpDautoDpLog = new PrpDautoDpLog();
				prpDautoDpLog.setModule("CLAIM");
				prpDautoDpLog.setFunctionName("賠付對象修改(payObjectInfoEdit)");
				prpDautoDpLog.setColumnCName("賠付對象");
				

				prpDautoDpLog.setId(prpDautoDpLogId);
				prpDautoDpLog = setDpLogData(prpDautoDpLogId,prpDautoDpLog,ownerNameOrg,ownerName,compensateNo,serialNo,certiType,riskCode);
			}

			if(!uniformNo.equals(uniformNoOrg)){
				PrpDautoDpLogId prpDautoDpLogId = new PrpDautoDpLogId();
				prpDautoDpLogId.setColumnName("UNIFORMNO");
				prpDautoDpLogId.setLogId(logId);
				
				PrpDautoDpLog prpDautoDpLog = new PrpDautoDpLog();
				prpDautoDpLog.setModule("CLAIM");
				prpDautoDpLog.setFunctionName("賠付對象修改(payObjectInfoEdit)");
				prpDautoDpLog.setColumnCName("統一編號/身分證號");
				

				prpDautoDpLog.setId(prpDautoDpLogId);
				prpDautoDpLog = setDpLogData(prpDautoDpLogId,prpDautoDpLog,uniformNoOrg,uniformNo,compensateNo,serialNo,certiType,riskCode);
			}

			if(!accountCode.equals(accountCodeOrg)){
				PrpDautoDpLogId prpDautoDpLogId = new PrpDautoDpLogId();
				prpDautoDpLogId.setColumnName("ACCOUNTCODE");
				prpDautoDpLogId.setLogId(logId);
				
				PrpDautoDpLog prpDautoDpLog = new PrpDautoDpLog();
				prpDautoDpLog.setModule("CLAIM");
				prpDautoDpLog.setFunctionName("賠付對象修改(payObjectInfoEdit)");
				prpDautoDpLog.setColumnCName("匯款帳號");
				
				
				prpDautoDpLog.setId(prpDautoDpLogId);
				prpDautoDpLog = setDpLogData(prpDautoDpLogId,prpDautoDpLog,accountCodeOrg,accountCode,compensateNo,serialNo,certiType,riskCode);
			}
			
			//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START
			if(!bankCode.equals(bankCodeOrg)){
				PrpDautoDpLogId prpDautoDpLogId = new PrpDautoDpLogId();
				prpDautoDpLogId.setColumnName("BANKCODE");
				prpDautoDpLogId.setLogId(logId);
				
				PrpDautoDpLog prpDautoDpLog = new PrpDautoDpLog();
				prpDautoDpLog.setModule("CLAIM");
				prpDautoDpLog.setFunctionName("賠付對象修改(payObjectInfoEdit)");
				prpDautoDpLog.setColumnCName("總行代號");
				
				prpDautoDpLog.setId(prpDautoDpLogId);
				prpDautoDpLog = setDpLogData(prpDautoDpLogId,prpDautoDpLog,bankCodeOrg,bankCode,compensateNo,serialNo,certiType,riskCode);
			}
			if(!bankName.equals(bankNameOrg)){
				PrpDautoDpLogId prpDautoDpLogId = new PrpDautoDpLogId();
				prpDautoDpLogId.setColumnName("BANKNAME");
				prpDautoDpLogId.setLogId(logId);
				
				PrpDautoDpLog prpDautoDpLog = new PrpDautoDpLog();
				prpDautoDpLog.setModule("CLAIM");
				prpDautoDpLog.setFunctionName("賠付對象修改(payObjectInfoEdit)");
				prpDautoDpLog.setColumnCName("總行名稱");
				
				prpDautoDpLog.setId(prpDautoDpLogId);
				prpDautoDpLog = setDpLogData(prpDautoDpLogId,prpDautoDpLog,bankNameOrg,bankName,compensateNo,serialNo,certiType,riskCode);
			}
			if(!customBankCode.equals(customBankCodeOrg)){
				PrpDautoDpLogId prpDautoDpLogId = new PrpDautoDpLogId();
				prpDautoDpLogId.setColumnName("CUSTOMBANKCODE");
				prpDautoDpLogId.setLogId(logId);
				
				PrpDautoDpLog prpDautoDpLog = new PrpDautoDpLog();
				prpDautoDpLog.setModule("CLAIM");
				prpDautoDpLog.setFunctionName("賠付對象修改(payObjectInfoEdit)");
				prpDautoDpLog.setColumnCName("分行代號");
				
				prpDautoDpLog.setId(prpDautoDpLogId);
				prpDautoDpLog = setDpLogData(prpDautoDpLogId,prpDautoDpLog,customBankCodeOrg,customBankCode,compensateNo,serialNo,certiType,riskCode);
			}
			if(!customBankName.equals(customBankNameOrg)){
				PrpDautoDpLogId prpDautoDpLogId = new PrpDautoDpLogId();
				prpDautoDpLogId.setColumnName("CUSTOMBANKNAME");
				prpDautoDpLogId.setLogId(logId);
				
				PrpDautoDpLog prpDautoDpLog = new PrpDautoDpLog();
				prpDautoDpLog.setModule("CLAIM");
				prpDautoDpLog.setFunctionName("賠付對象修改(payObjectInfoEdit)");
				prpDautoDpLog.setColumnCName("分行名稱");
				
				prpDautoDpLog.setId(prpDautoDpLogId);
				prpDautoDpLog = setDpLogData(prpDautoDpLogId,prpDautoDpLog,customBankNameOrg,customBankName,compensateNo,serialNo,certiType,riskCode);
			}
			//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 END
		}
		
		httpServletRequest.setAttribute("content", "提交完成！");
		return "success";
	}
	
	public PrpDautoDpLog setDpLogData(PrpDautoDpLogId prpDautoDpLogId,PrpDautoDpLog prpDautoDpLog,String beforeValue,String newValue,String compenNo,String serNo,String certNo,String riskCode) throws Exception{

		HttpServletRequest httpServletRequest = super.getRequest();
		prpDautoDpLog.setBeforeValue(beforeValue);
		prpDautoDpLog.setValue(newValue);
		prpDautoDpLog.setCompensateNo(compenNo);
		prpDautoDpLog.setSerialNo(serNo);
		prpDautoDpLog.setCertiType(certNo);
		prpDautoDpLog.setRiskCode(riskCode);
		
		prpDautoDpLog.setInputStatus("1");//進入 [1:待審核] 狀態
		
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		prpDautoDpLog.setInputUser(user.getUserCode());
		prpDautoDpLog.setInputDate(new Date());

		prpDautoDpLog.setInputIp(IPUtil.getIpAddr(httpServletRequest));
		
		String conditions = "comCode='" + user.getComCode() + "' AND userCode='" + user.getUserCode() + "' AND VALIDSTATUS='1' AND (uwtype='C' or uwtype='Y') ORDER BY NODENO";
		List<UtiUwLevel> list = utiUwLevelService.findByConditions(conditions);
		Integer nodeNo = 0;
		for(UtiUwLevel utiPower:list){
			nodeNo = nodeNo < utiPower.getId().getNodeNo()?utiPower.getId().getNodeNo():nodeNo;
		}
		prpDautoDpLog.setInputNodeNo(nodeNo+"");
		
		String sqlExpress = "UPDATE PRPLPAYOBJECTINFO SET "+prpDautoDpLogId.getColumnName()+"='"+prpDautoDpLog.getValue()+"' WHERE COMPENSATENO='"+prpDautoDpLog.getCompensateNo()+"' AND SERIALNO="+prpDautoDpLog.getSerialNo()+" AND CERTITYPE='"+prpDautoDpLog.getCertiType()+"' ";
		prpDautoDpLog.setSqlExpress(sqlExpress);
		prpDautoDpLogService.save(prpDautoDpLog);
		return prpDautoDpLog;
	}

	public String payObjectInfoPass() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		
		String logId = httpServletRequest.getParameter("logId");
		List<PrpDautoDpLog> list = prpDautoDpLogService.findPrpDautoDpLog(logId);
		
		
		for(PrpDautoDpLog pdd:list){
			pdd.setReviewInputDate(new Date());
			pdd.setReviewInputIp(IPUtil.getIpAddr(httpServletRequest));
			pdd.setReviewInputUser(user.getUserCode());
			
			PrpLpayObjectInfoId prpLpayObjectInfoId = new PrpLpayObjectInfoId();
			prpLpayObjectInfoId.setCompensateNo(pdd.getCompensateNo());
			prpLpayObjectInfoId.setSerialNo(Integer.parseInt(pdd.getSerialNo(),10));
			prpLpayObjectInfoId.setCertiType(pdd.getCertiType());
			PrpLpayObjectInfo prpLpayObjectInfo = prpLpayObjectInfoService.findPrpLpayObjectInfo(prpLpayObjectInfoId);
			if(null!=prpLpayObjectInfo){
				if(pdd.getId().getColumnName().equals("OWNERNAME")){
					prpLpayObjectInfo.setOwnerName(pdd.getValue());
				}
				if(pdd.getId().getColumnName().equals("UNIFORMNO")){
					prpLpayObjectInfo.setUniformNo(pdd.getValue());
				}
				if(pdd.getId().getColumnName().equals("ACCOUNTCODE")){
					prpLpayObjectInfo.setAccountCode(pdd.getValue());
				}
				//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 START
				if(pdd.getId().getColumnName().equals("BANKCODE")){
					prpLpayObjectInfo.setBankCode(pdd.getValue());
				}
				if(pdd.getId().getColumnName().equals("BANKNAME")){
					prpLpayObjectInfo.setBankName(pdd.getValue());
				}
				if(pdd.getId().getColumnName().equals("CUSTOMBANKCODE")){
					prpLpayObjectInfo.setCustomBankCode(pdd.getValue());
				}
				if(pdd.getId().getColumnName().equals("CUSTOMBANKNAME")){
					prpLpayObjectInfo.setCustomBankName(pdd.getValue());
				}
				//mantis：CLM0289，處理人員：DP0713，需求單編號：理賠DP自動化-開放總行代號及分行代號欄位 END
				prpLpayObjectInfoService.save(prpLpayObjectInfo);
			}

			pdd.setRemark("審核通過");
			pdd.setInputStatus("2");//下發修改
		}
		prpDautoDpLogService.save(list);
		
		httpServletRequest.setAttribute("content", "審核通過！");
		return "success";
	}
	
	public String payObjectInfoReject() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
//		String compensateNo = httpServletRequest.getParameter("compensateNo");
		String logId = httpServletRequest.getParameter("logId");
		UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
		List<PrpDautoDpLog> list = prpDautoDpLogService.findPrpDautoDpLog(logId);
		
		for(PrpDautoDpLog pdd:list){
			pdd.setReviewInputDate(new Date());
			pdd.setReviewInputIp(IPUtil.getIpAddr(httpServletRequest));
			pdd.setReviewInputUser(user.getUserCode());
			
			pdd.setRemark("下發修改完成");
			pdd.setInputStatus("-1");//下發修改
		}
		prpDautoDpLogService.save(list);
		
		httpServletRequest.setAttribute("content", "下發修改完成！");
		return "success";
	}
	
	public String getNodeType() {
		return nodeType;
	}
	
	public WorkFlowViewHelper getWorkFlowViewHelper() {
		return workFlowViewHelper;
	}

	public void setWorkFlowViewHelper(WorkFlowViewHelper workFlowViewHelper) {
		this.workFlowViewHelper = workFlowViewHelper;
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

	public void setSunnyCompensateViewHelper(
			SunnyCompensateViewHelper sunnyCompensateViewHelper) {
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

	public void setAccidentCompensateViewHelper(
			AccidentCompensateViewHelper accidentCompensateViewHelper) {
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

	public void setPrpLpayObjectInfoService(
			PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
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

	public PrpLpersonLossService getPrpLpersonLossService() {
		return PrpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		PrpLpersonLossService = prpLpersonLossService;
	}


	public PrpDautoDpLogService getPrpDautoDpLogService() {
		if (prpDautoDpLogService == null) {
			return (PrpDautoDpLogService) ServiceFactory.getService("prpDautoDpLogService");
		}
		return prpDautoDpLogService;
	}

	public void setPrpDautoDpLogService(PrpDautoDpLogService prpDautoDpLogService) {
		this.prpDautoDpLogService = prpDautoDpLogService;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}
	public WebServiceContext getWsCtxt() {
		return wsCtxt;
	}

	public void setWsCtxt(WebServiceContext wsCtxt) {
		this.wsCtxt = wsCtxt;
	}

	
	
}
