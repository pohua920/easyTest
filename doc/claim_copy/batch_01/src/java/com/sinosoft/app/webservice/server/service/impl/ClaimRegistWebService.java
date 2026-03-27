package com.sinosoft.app.webservice.server.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

//import java.text.SimpleDateFormat;
//
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import bsh.StringUtil;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinosoft.app.common.util.FileUtil;
import com.sinosoft.app.webservice.server.schema.model.common.RespClaimData;
import com.sinosoft.app.webservice.server.schema.model.common.RespJsonInfo;
import com.sinosoft.app.webservice.server.schema.model.regist.ReqRegist;
import com.sinosoft.app.webservice.server.schema.model.regist.ReqRegistTemp;
import com.sinosoft.app.webservice.server.service.ClaimRegistService;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.EndorseService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.BusinessRuleUtil;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.email.service.facade.EmailService;
import com.sinosoft.claim.regist.service.facade.RegistService;
import com.sinosoft.claim.regist.util.DAARegistViewHelper;
import com.sinosoft.claim.regist.vo.RegistDto;
import com.sinosoft.claim.schema.model.PrpCaddress;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpCmainSub;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.PrpLuser;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpCaddressService;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainSubService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLuserService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.UtiUserGradeService;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.util.BusinessViewHelper;
import com.sinosoft.claim.workflow.util.JbpmBusinessViewHelper;
import com.sinosoft.claim.workflow.util.WorkFlowViewHelper;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.claim.workflow.vo.WorkFlowQueryDto;
import com.sinosoft.prpall.ui.UIPrpJFeeCheck;
import com.sinosoft.sysframework.common.util.SqlUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.utility.log.Log;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimExternalSourceVo;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimPrpLregistVo;
import com.sinosoft.app.webservice.server.schema.model.regist.vo.PersonTrace;
import com.sinosoft.app.webservice.util.JsonUtil;


/**
 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
public class ClaimRegistWebService extends GenericDaoHibernate<PrpLregist, String> implements ClaimRegistService {
	@Resource
	private WebServiceContext wsCtxt;
	/** 编辑类型 */
	private String editType = "ADD";
	
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
	
	private String registSharingFlagDisabled = "false";
	/**備案服務**/
	private PrpLregistService prpLregistService;
    /**單號服務*/
    private BillService billService;
	
	private UtiUserGradeService utiUserGradeService;
	
	private CodeService codeService;
	
	private PolicyService policyService;
	/** 报案数据收集 */
	private DAARegistViewHelper daaRegistViewHelper;
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
	
	private JbpmBusinessViewHelper jbpmBusinessViewHelper;
	/** 工作流数据收集信息 */
	private WorkFlowViewHelper workFlowViewHelper;
	
	private BusinessViewHelper businessViewHelper;

	private WorkFlowService workFlowService;
	
	private RegistService registService;
	/** 承保险别服务 */
	private PrpCitemKindService prpCitemKindService;

	private EmailService emailService;

	private static CacheService cacheManager = CacheManager.getInstance("perfCodeTransfer");
	
	/**
	 * 报案查询前处理(2025年05月 新增，目前只給車險用，設計上盡量與原備案程式相同RegistBeforeEditAction.java，未來原程式修正這裡也須跟著修正)
	 * @return 页面类型
	 * @throws Exception
	 */
	public RespClaimData registByWs(ReqRegist requestIn) {
		ReqRegistTemp request = new ReqRegistTemp();
		//來源由CWP進入
		//連結需求 http://localhost:7001/claim/registBeforeEdit.do?prpCmainPolicyNo=180624Z00078&editType=ADD&damageDate=2025-05-07&damageHour=11&insuredCode=506206719580&insuredName=許裴文
		//RegistBeforeEditAction.java registBeforeQuery()+registBeforeEdit()
		RespClaimData respClaimData_in = new RespClaimData();
		//進來跟出去的把它分開
		RespClaimData respClaimData_out = new RespClaimData();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ClaimExternalSourceVo claimExternalSourceVo = new ClaimExternalSourceVo();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date current = new Date();
		RespJsonInfo respJsonInfo = new RespJsonInfo();

		prpLregistService = (PrpLregistService)ServiceFactory.getService("prpLregistService");
		String logTitle = "claim:registByWs";
		String policyNoLog = "";
		Date startDate = new Date();
		
		try {
			//驗證時候直接回覆簡易錯誤
			respJsonInfo.setOperateDate(sdFormat.format(current));
//			respClaimData_in.setRtnJson(JsonUtil.getJSONString(respJsonInfo));
//			respClaimData_out.setRtnJson(JsonUtil.getJSONString(respJsonInfo));
			
			String sendJsonStr = requestIn.getSendJson();
			if(null!=sendJsonStr && sendJsonStr.length()>0){
				String decodeStr = new String(Base64.decodeBase64(sendJsonStr), "UTF-8");
				claimExternalSourceVo = objectMapper.readValue(decodeStr,ClaimExternalSourceVo.class);
			}
//			claimExternalSourceVo.getMultiRecepNo()
			Object sendCheck = cacheManager.getCache(claimExternalSourceVo.getMultiRecepNo());
			if(sendCheck != null){
				boolean checkKeyDate = false;
				//檢查是否超過5分鐘
				long diff = new Date().getTime() - Long.parseLong(sendCheck.toString()) ;
				if(diff > 300000){
					checkKeyDate = true;
					cacheManager.putCache(claimExternalSourceVo.getMultiRecepNo(), new Date());
				}
				if(!checkKeyDate){
					FileUtil.insertLOG(logTitle, policyNoLog, "mp:"+request.getMainPolicyNo(), "Start", startDate, "多元理賠備案介接(BASE64)-重複送單", wsCtxt, requestIn);

					//正在處理中，請確認是否重覆送單!!\n

					respClaimData_in.setCode("E0014");
					respClaimData_in.setMsg("正在處理中，請確認是否重覆送單!!");//直接的錯誤 直接返回
					FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
					return respClaimData_in;
					
				}
				
			}else{
				cacheManager.putCache(claimExternalSourceVo.getMultiRecepNo(), new Date().getTime());
			}
			
			String sqlStr = " 1=1 "+SqlUtils.convertString("PRPLREGIST.MULTIRECEPNO", claimExternalSourceVo.getMultiRecepNo());
			Page page = this.prpLregistService.findPrpLregist(sqlStr,0,1);
			List<PrpLregist> result = page.getResult();
			if(result.size()>0){
				respClaimData_in.setCode("E0015");
				respClaimData_in.setRegistNo(result.get(0).getPolicyNo());
				respClaimData_in.setMsg("此多元理賠單號"+claimExternalSourceVo.getMultiRecepNo()+"已經使用，請確認是否重覆送單!!");//直接的錯誤 直接返回
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);

	        	cacheManager.clearCache(claimExternalSourceVo.getMultiRecepNo());
				return respClaimData_in;
			}
			
			//外部轉換主要參數
			request.setPolicyNo(claimExternalSourceVo.getPolicyNo());
			request.setMainPolicyNo(claimExternalSourceVo.getMainPolicyNo());
			request.setRegistNo(claimExternalSourceVo.getRegistNo());
			request.setDamageDate(claimExternalSourceVo.getDamageStartDate());
			request.setDamageHour(claimExternalSourceVo.getDamageStartHour());
			
			policyNoLog = StringUtils.isNotBlank(request.getPolicyNo())?"pn:"+request.getPolicyNo():"mp:"+request.getMainPolicyNo();
			FileUtil.insertLOG(logTitle, policyNoLog, "rn:"+request.getRegistNo(), "Start", startDate, "多元理賠備案介接(BASE64)", wsCtxt, requestIn);

			request.setEditType((null!=request.getRegistNo() && !"".equals(request.getRegistNo().trim()))?"EDID":"ADD");
		}catch(Exception e){
	        respClaimData_in.setCode("E0000");
			respClaimData_in.setMsg("未知的錯誤-"+sdFormat.format(current));
			StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        e.printStackTrace(pw);
	        String stackTrace = sw.toString();
	        try {
				respJsonInfo.setOperateDate(sdFormat.format(current));
				respJsonInfo.setStackTrace(stackTrace);
				System.out.println("未知的錯誤-"+sdFormat.format(current));
				System.out.println(stackTrace);
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+stackTrace.substring(0,stackTrace.length()>1500?1499:stackTrace.length()), wsCtxt, null);
//				respClaimData_in.setRtnJson(JsonUtil.getJSONString(respJsonInfo));
			}catch(Exception ex){
			}finally{
	        	cacheManager.clearCache(claimExternalSourceVo.getMultiRecepNo());
	        	return respClaimData_in;
	        }
		}
		
		MessageContext msgCtxt = wsCtxt.getMessageContext();
	    HttpServletRequest httpServletRequest = (HttpServletRequest)msgCtxt.get(MessageContext.SERVLET_REQUEST);
//	    HttpServletResponse httpServletResponse = (HttpServletResponse)msgCtxt.get(MessageContext.SERVLET_RESPONSE);
	    String clientIP = httpServletRequest.getRemoteAddr();
	    System.out.println("IP:"+clientIP);
	    
	    httpServletRequest.setAttribute("ReqRegistQuery", request);
	  //連結需求 http://localhost:7001/claim/registBeforeEdit.do?prpCmainPolicyNo=180624Z00078&editType=ADD&damageDate=2025-05-07&damageHour=11&insuredCode=506206719580&insuredName=許裴文
	    editType = "RegistBeforeQuery";
	    httpServletRequest.setAttribute("editType", "RegistBeforeQuery");//第一段用這個editType後面會變成add ..

		prpCaddressService = (PrpCaddressService)ServiceFactory.getService("prpCaddressService");
		endorseService = (EndorseService)ServiceFactory.getService("endorseService");
		prpCmainService = (PrpCmainService)ServiceFactory.getService("prpCmainService");
		prpCmainSubService = (PrpCmainSubService)ServiceFactory.getService("prpCmainSubService");
		endorseViewHelper = (EndorseViewHelper)ServiceFactory.getService("endorseViewHelper");
		utiUserGradeService = (UtiUserGradeService)ServiceFactory.getService("utiUserGradeService");
		codeService = (CodeService)ServiceFactory.getService("codeService");
		policyService = (PolicyService)ServiceFactory.getService("policyService");
		daaRegistViewHelper = (DAARegistViewHelper)ServiceFactory.getService("daaRegistViewHelper");
		prpLregistrpolicyService = (PrplregistrpolicyService)ServiceFactory.getService("prpLregistrpolicyService");
		prpDriskConfigService = (PrpDriskConfigService)ServiceFactory.getService("prpDriskConfigService");
		billService = (BillService)ServiceFactory.getService("billService");
		jbpmBusinessViewHelper = (JbpmBusinessViewHelper)ServiceFactory.getService("jbpmBusinessViewHelper");
		businessViewHelper = (BusinessViewHelper)ServiceFactory.getService("businessViewHelper");
		workFlowViewHelper = (WorkFlowViewHelper)ServiceFactory.getService("workFlowViewHelper");
		workFlowService = (WorkFlowService)ServiceFactory.getService("workFlowService");
		registService = (RegistService)ServiceFactory.getService("registService");
		prpCitemKindService = (PrpCitemKindService)ServiceFactory.getService("prpCitemKindService");
		
		registService = (RegistService)ServiceFactory.getService("registService");
		
		PrpLuserService prpLuserService = ((PrpLuserService)ServiceFactory.getService("prpLuserService"));
		PrpDuserService prpDuserService = (PrpDuserService)ServiceFactory.getService("prpDuserService");
		
		/**
		 * 查詢列表(仿製來源) 製作方法:盡量與原功能相同不要變化太大，因為之後有改也需要改這裡
		 * >>>===source: RegistBeforeEditAction.java registBeforeQuery()=========================================
		 * 走 if (editType.equals("RegistBeforeQuery")) { 所以不需要workFlowQueryDto.set......
		 */
		PrpCmain prpCmain = new PrpCmain();
	    /**报案查询前置处理*/
		try {
			if("001".equals(claimExternalSourceVo.getChannelSource())){
				System.out.println("001:RTC");
			}else{
				respClaimData_in.setCode("E0001");
				respClaimData_in.setMsg("用戶端沒有訪問內容的權限");//直接的錯誤 直接返回
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
				return respClaimData_in;
			}
			if(null == claimExternalSourceVo.getHandlerCode() || "".equals(claimExternalSourceVo.getHandlerCode().trim())){
				respClaimData_in.setCode("E0002");
				respClaimData_in.setMsg("使用者帳號必須輸入");//直接的錯誤 直接返回
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
				return respClaimData_in;
			}
			PrpLuser prpLuser = null;
			PrpDuser prpDuser = prpDuserService.findPrpDuser(claimExternalSourceVo.getHandlerCode());
			if(prpDuser == null){
				prpLuser = prpLuserService.findPrpLuserByUserCode(claimExternalSourceVo.getHandlerCode());
			}
			if(prpLuser == null && prpDuser == null){
				respClaimData_in.setCode("E0003");
				respClaimData_in.setMsg("無法取得使用者帳號");//直接的錯誤 直接返回
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
				return respClaimData_in;
			}		    
			UserDto user = new UserDto();
			if(prpDuser != null){
			    user.setUserCode(prpDuser.getUserCode());
			    user.setUserName(prpDuser.getUserName());
			    user.setComCode(prpDuser.getComCode());
			    user.setComName(codeService.translateComCode(prpDuser.getComCode(), true));
			}else if(prpLuser!=null){
			    user.setUserCode(prpLuser.getUserCode());
			    user.setUserName(prpLuser.getUserName());
			    user.setComCode(prpLuser.getComcode());
			    user.setComName(codeService.translateComCode(prpLuser.getComcode(), true));
			}
		    httpServletRequest.getSession().setAttribute("user", user);
		    
		    if(null == request.getDamageDate() || "".equals(request.getDamageDate().trim())){
				respClaimData_in.setCode("E0004");
				respClaimData_in.setMsg("出險日期必須輸入");//直接的錯誤 直接返回
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
				return respClaimData_in;
			}else{
				respClaimData_in.setCode("E0005");
				String[] chkDate = request.getDamageDate().split("-");
				if(!(chkDate[0].length()==4 && chkDate[1].length()==2 && chkDate[2].length()==2)){
					respClaimData_in.setMsg("出險日期格式錯誤");//直接的錯誤 直接返回
					FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
					return respClaimData_in;
				}
			}
		    
		    if(null == request.getDamageHour() || "".equals(request.getDamageHour().trim())){
				respClaimData_in.setCode("E0006");
				respClaimData_in.setMsg("出險小時必須輸入");//直接的錯誤 直接返回
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
				return respClaimData_in;
			}else{
				respClaimData_in.setCode("E0007");
				if(request.getDamageHour().length()>2 || !StringUtils.isNumeric(request.getDamageHour()) 
						|| Integer.parseInt(request.getDamageHour())>24 || Integer.parseInt(request.getDamageHour())<0){
					respClaimData_in.setMsg("出險小時格式錯誤");//直接的錯誤 直接返回
					FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
					return respClaimData_in;
				}
			}
		    
			// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
			core_URL = AppConfig.get("sysconst.Core_URL");
			registViewLimitDay = AppConfig.get("sysconst.RegistViewLimitDay");
//			editType = (null!=request.getRegistNo() && !"".equals(request.getRegistNo().trim()))?"EDID":"ADD";// 增加通过request获取参数，避免前台有两个editType，获取到的是editType=ADD，ADD的问题。
			String policyNo = request.getPolicyNo(); // 保单号
			
		    if((null == request.getPolicyNo() || "".equals(request.getPolicyNo().trim())) && 
		    		(null == request.getMainPolicyNo() || "".equals(request.getMainPolicyNo().trim()))){
				respClaimData_in.setCode("E0008");
				respClaimData_in.setMsg("保單號必須輸入");//直接的錯誤 直接返回
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
				return respClaimData_in;
			}else{//僅輸入mainPolicyNo 導到policyNo
				if((null == request.getPolicyNo() || "".equals(request.getPolicyNo().trim()))){
					request.setPolicyNo(request.getMainPolicyNo());
					request.setMainPolicyNo("");
					
					policyNo = request.getPolicyNo();
				}
			}
//			String riskCode = httpServletRequest.getParameter("RiskCode");// 险种
			// 投保人
			// 去掉status中最後一个逗号
			//del..
			String policyNoSign = httpServletRequest.getParameter("PolicyNoSign");
			
			
			WorkFlowQueryDto workFlowQueryDto = new WorkFlowQueryDto();
			workFlowQueryDto.setPolicyNo(policyNo);
			workFlowQueryDto.setPolicyNoSign(policyNoSign);
			
			// 尚未加入type异常处理{}、其它必须参数异常处理{}
			// 1。报案一般的查询，查询理赔节点状态信息,整理输入，用於初始界面显示
			List<PrpCmain> prpCmainList = new ArrayList<PrpCmain>();
			//daaRegistViewHelper.policyListToView(httpServletRequest, pageNo, pageSize);
			prpCmainList = daaRegistViewHelper.policyListToView4Ws(httpServletRequest, request, 1, 1);

//			respClaimData_in.setPrpCmainList(prpCmainList);
			if(null!=prpCmainList && prpCmainList.size()>0){
				prpCmain = prpCmainList.get(0);
			}else{
				respClaimData_in.setCode("E0009");
				respClaimData_in.setMsg("查無保單號");//直接的錯誤 直接返回
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
				return respClaimData_in;
			}
			//===source:RegistBeforeEditAction.java registBeforeQuery()=====================================
		
	    
	    /**
		 * 備案登錄--選擇某個保單號連結進入備案資料填寫頁面(仿製來源)
		 * RegistBeforeEditAction.java >> registBeforeEdit()
		 */
			editType = request.getEditType();
			//===source:RegistBeforeEditAction.java >> registBeforeEdit()=========================================================
			
//			logger.info("准备查询报案信息");
			java.util.Date inTime = new java.util.Date();
			////HttpServletRequest httpServletRequest = getRequest();
			core_URL = AppConfig.get("sysconst.Core_URL");
			registViewLimitDay = AppConfig.get("sysconst.RegistViewLimitDay");
//			editType = httpServletRequest.getParameter("editType");// 增加通过request获取参数，避免前台有两个editType，获取到的是editType=ADD，ADD的问题。
			// 业务类型：ADD-新增 EDIT-修改 SHOW-显示
//			policyNo = request.getMainPolicyNo();//httpServletRequest.getParameter("prpCmainPolicyNo"); // 保单号
			String strPolicyNo = request.getPolicyNo();//httpServletRequest.getParameter("policyNo"); // 保单号
			String strRiskCode = "";
			String strClassCode = "";
			String strPolicyNo1 = "";
			String othFlag = httpServletRequest.getParameter("othFlag");//??
			String registNo = request.getRegistNo();//httpServletRequest.getParameter("prpLregistRegistNo"); // 报案号
			String damageDate = request.getDamageDate();//httpServletRequest.getParameter("damageDate");// 出险日期
//			//damageDate = "2024-03-19";//dp0713 備案做資料 改出險時間
			String damageHour = request.getDamageHour();//httpServletRequest.getParameter("damageHour");// 出险小时
//			httpServletRequest.setAttribute("registNo", registNo);
			String strSql = "";
			if (DataUtils.emptyToNull(request.getRegistNo()) != null) {
				PrpLregist prpLregist = this.prpLregistService.findPrpLregist(request.getRegistNo());
				if (prpLregist != null) {
					strPolicyNo1 = prpLregist.getPolicyNo();
					request.setDamageDate(new DateTime(prpLregist.getDamageStartDate()).toString());// 出险日期
					request.setDamageHour(prpLregist.getDamageStartHour());// 出险小时
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
//			List<PrpCmain> prpCmainList = prpCmainService.findPrpCmain(queryRule);
			StringBuffer strAddress = new StringBuffer();
			strAddress.append("");
			PrpCaddress prpCaddress = null;
//			////PrpCmain prpCmain = null;
			int count = prpCaddressList.size();
//			for (int m = 0; m < prpCmainList.size(); m++) {
//				prpCmain = prpCmainList.get(m);
				strRiskCode = prpCmain.getRiskCode();
				strClassCode = prpCmain.getClassCode();
//			}
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
				List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(regisPolicyNo, request.getDamageDate(), request.getDamageHour());
				PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, null, null);
				PrpCmainSub prpCmainSub = null;
				String remark = "";
				boolean hasB01 = false;
				for (int temp = 0; temp < prpCmainSubList.size(); temp++) {
					prpCmainSub = prpCmainSubList.get(temp);
					
					if(null!= request.getMainPolicyNo() && !"".equals(request.getMainPolicyNo()) &&
							request.getMainPolicyNo().equals(prpCmainSub.getId().getMainPolicyNo())){
						hasB01 = true;
					}
					request.setPolicyNo(prpCmainSub.getId().getPolicyNo());
//					if(StringUtils.isNotBlank(claimExternalSourceVo.getMainPolicyNo())){//如果RTC 單筆不混搭 換這裡
						request.setMainPolicyNo(prpCmainSub.getId().getMainPolicyNo());
//					}
					remark = prpCmainSub.getRemark();
					httpServletRequest.setAttribute("remark", remark);
				}
				if((null!= request.getMainPolicyNo() && !"".equals(request.getMainPolicyNo())
						&& !hasB01) //如果USER輸入任意正確了  就必須正確的強制
						&& !"".equals(claimExternalSourceVo.getMainPolicyNo())){
					respClaimData_in.setCode("E0010");
					respClaimData_in.setMsg("您所輸入的強制險關聯單錯誤！");//直接的錯誤 直接返回
					FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
					return respClaimData_in;
				}
				if (prpCinsured != null) {
					String postcode = prpCinsured.getPostCode();
					httpServletRequest.setAttribute("postcode", postcode);
				}
			}
//		    if((null!=strRiskCode && strRiskCode.equals("B01"))||(!"".equals(request.getMainPolicyNo())) ){
//		    	String err="";
//		    	if(null==claimExternalSourceVo.getPersonTraceList() || claimExternalSourceVo.getPersonTraceList().size()<=0){
//		    		err=",至少必填寫一組人傷跟蹤受害人訊息";
//		    	}
//		    	for(PersonTrace personTrace:claimExternalSourceVo.getPersonTraceList()){
//		    		if("".equals(personTrace.getPersonName().trim())){
//		    			err+=",受害人姓名";
//		    		}
//		    		if("".equals(personTrace.getPersonSex().trim())){
//		    			err+=",受害人性別";
//		    		}
//		    		if("".equals(personTrace.getIdentifyNumber().trim())){
//		    			err+=",受害人身分證號";
//		    		}
//		    		if("".equals(personTrace.getIdNumberType().trim())){
//		    			err+=",受害人身分證號類別";
//		    		}
//		    		if("".equals(personTrace.getRideSituation().trim())){
//		    			err+=",受害人乘坐狀況";
//		    		}
//		    		if(!"".equals(personTrace.getApplicantBirthday().trim())){
//		    			if(personTrace.getApplicantBirthday().length()!=10){
//		    				err+=",受害人出生年月日";
//		    			}
//		    		}else{
//		    			err+=",受害人出生年月日";
//		    		}
//		    	}
//		    	if(!"".equals(err)){
//		    		respClaimData_in.setCode("E0011");
//					respClaimData_in.setMsg("強制險"+err.substring(1)+"必填或格式錯誤");//直接的錯誤 直接返回
//					FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
//					return respClaimData_in;
//		    	}
//		    }
			String riskCode = ""; // 险种
//			////forward = "";
			HttpSession session = httpServletRequest.getSession();
//			UserDto user = (UserDto) session.getAttribute("user");
//			
			if (user!=null) {
				List<String> list = this.utiUserGradeService.findGradeCodeByUserCode(user.getUserCode());
				if (list != null && !list.isEmpty()) {
					if(list.contains("005")){
						//(2)	同業已賠付radio button限制崗位代號【005-理賠人員】僅供查看不提供修改
						registSharingFlagDisabled="true";
					}
				}
			}
			
			//保存初始的请求路径 begin
//			String statusTemp = httpServletRequest.getParameter("status");
			String editTypeTemp = request.getEditType();//httpServletRequest.getParameter("editType");
			String flushflag = (String)httpServletRequest.getAttribute("flushflag");
			if (!"true".equals(flushflag)) { //不是通过改变出险时间后刷新过来的请求
				session.setAttribute("editTypeTemp", editTypeTemp);
				String originalRequestURITemp ="";
				if ("ADD".equals(editTypeTemp)) {//处理报案登记
//					String prpCmainPolicyNoTemp = request.getPolicyNo();//httpServletRequest.getParameter("prpCmainPolicyNo");
//					String damageDateTemp = request.getDamageDate();//httpServletRequest.getParameter("damageDate");
//					String damageHourTemp = request.getDamageHour();//httpServletRequest.getParameter("damageHour");			
//					originalRequestURITemp = "/claim/registBeforeEdit.do?prpCmainPolicyNo=" + prpCmainPolicyNoTemp + "&editType="+editTypeTemp+"&damageDate=" + damageDateTemp + "&damageHour=" + damageHourTemp;
					
				} else if ("EDIT".equals(editTypeTemp) || "SHOW".equals(editTypeTemp)) {//正在處理備案任務 和 已處理備案任務
//					String prpLregistRegistNoTemp = httpServletRequest.getParameter("prpLregistRegistNo");
//					String updateExtTemp = httpServletRequest.getParameter("updateExt");
//					String swfLogFlowIDTemp = httpServletRequest.getParameter("swfLogFlowID");
//					String swfLogLogNoTemp = httpServletRequest.getParameter("swfLogLogNo");
//					String riskCodeTemp = httpServletRequest.getParameter("riskCode");
//					String nodeTypeTemp = httpServletRequest.getParameter("nodeType");
//					String businessNoTemp = httpServletRequest.getParameter("businessNo");
//					String keyInTemp = httpServletRequest.getParameter("keyIn");
//					String policyNoTemp = httpServletRequest.getParameter("policyNo");
//					String modelNoTemp = httpServletRequest.getParameter("modelNo");
//					String nodeNoTemp = httpServletRequest.getParameter("nodeNo");
//					String dfFlagTemp = httpServletRequest.getParameter("dfFlag");
//					String actorIdTemp = httpServletRequest.getParameter("actorId");
//					String processIdTemp = httpServletRequest.getParameter("processId");
//					originalRequestURITemp = "/claim/registFinishQueryList.do?prpLregistRegistNo="+prpLregistRegistNoTemp+"&updateExt="+updateExtTemp+"&swfLogFlowID="+swfLogFlowIDTemp+"&swfLogLogNo="+swfLogLogNoTemp+"&status="+statusTemp+"&riskCode="+riskCodeTemp+"&editType="+editTypeTemp+"&nodeType="+nodeTypeTemp+"&businessNo="+businessNoTemp+"&keyIn="+keyInTemp+"&policyNo="+policyNoTemp+"&modelNo="+modelNoTemp+"&nodeNo="+nodeNoTemp+"&dfFlag="+dfFlagTemp+"&actorId="+actorIdTemp+"&processId="+processIdTemp;
					
				}else if ("PERFECT".equals(editTypeTemp)) {//报案修改
//					String prpLregistRegistNoTemp = request.getRegistNo();//httpServletRequest.getParameter("prpLregistRegistNo");
//					String prpCmainPolicyNoTemp = request.getPolicyNo();//httpServletRequest.getParameter("prpCmainPolicyNo");
//					originalRequestURITemp ="/claim/regist/registBeforeEdit.do?editType="+editTypeTemp+"&prpLregistRegistNo="+prpLregistRegistNoTemp+"&prpCmainPolicyNo="+prpCmainPolicyNoTemp;
					
				}
//				session.setAttribute("originalRequestURITemp", originalRequestURITemp);
			}
//			//保存初始的请求路径 end			
//			// 如果以商业保单查询-----------------
//			// 注： 1。获取是否保强三标志qsflag
//			// 2。获取强三保单 mainPolicyNo
			String mainPolicyNo = "";
			String quaryPolicyNo = policyNo;
			String relateMainPolicyNo = request.getMainPolicyNo(); // 关联强三保单
			String relatePolicyNo = ""; // 关联商业保单
			String flag = "";
			String qsFlag = "N"; // N：没有关联 Y：有关联
			String isMainPolicyNo = "N";// 是否是以交强险保单查询 N：不是 Y：是
			int intPayFee = 0;
			boolean qs_valid = false;
			boolean sy_valid = false;
			PrpCmain qs_prpCmain = new PrpCmain();
//			// 取得强制保险的险种代码
			String compelRiskCode = BusinessRuleUtil.getOuterCode(httpServletRequest, "RISKCODE_DAZ");
			if ("TimeOut".equals(editType))
				editType = "SHOW";
//			// 尚未加入type异常处理{}、其它必须参数异常处理{}
//			// 1。查询保单信息,整理输入，用於初始界面显示
			if (editType.equals("ADD")) {
//				//PolicyDto policySub = new PolicyDto();
				PrpCmainSub prpCmainSub = new PrpCmainSub();
				PrpCmain qs_prpCmainTemp = new PrpCmain();
//				// 山东见费出单批改时判断本期是否实收
				UIPrpJFeeCheck uiPrpJFeeCheck = new UIPrpJFeeCheck();
				if (uiPrpJFeeCheck.IsInDebt("P", policyNo, new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).toString())) {
//					////throw new UserException(-98, -2, "UIEndorSpecialSubmit", getText("regist.refuseOfPremium"));//此保單當前存在未按期繳納的保費，不允許報案！
					respClaimData_in.setCode("E0012");
					respClaimData_in.setMsg("此保單當前存在未按期繳納的保費，不允許報案！");//直接的錯誤 直接返回
					FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
					return respClaimData_in;
				}
				// 山东见费出单批改时判断本期是否实收
				/**  備案優化處理，需要什麼查什麼，替換直接使用大保單  */
				//PolicyDto policyDto = policyService.findByPrimaryKey(policyNo);
//				prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, request.getDamageDate(), request.getDamageHour());
				queryRule = QueryRule.getInstance();
				queryRule.addSql(" ( mainpolicyno= '" + policyNo + "' or  policyno= '" + policyNo + "' ) and flag = '111' ");
//				List<PrpCmainSub> prpCmainSubList = null;//如果RTC 單筆不混搭 換這裡
//				if(StringUtils.isNotBlank(claimExternalSourceVo.getMainPolicyNo())){//如果RTC 單筆不混搭 換這裡
//					prpCmainSubList = prpCmainSubService.findPrpCmainSub(queryRule);
//				}
				List<PrpCmainSub> prpCmainSubList = prpCmainSubService.findPrpCmainSub(queryRule);
				String riskType = codeService.translateRiskCodetoRiskType(prpCmain.getRiskCode());
				if (prpCmain != null && ConstantCodes.CLASSCODE_D.equals(riskType)) {// 車險保單
					if (!CommonUtils.isEmpty(prpCmainSubList)) {// 商業險或者關聯單存在mainsub數據。
						for (int i = 0; i < prpCmainSubList.size(); i++) {
//							// 出险时间在保险期限内 && flag[2]=1
							prpCmainSub = (PrpCmainSub) prpCmainSubList.get(i);
							relateMainPolicyNo = prpCmainSub.getId().getMainPolicyNo();
							relatePolicyNo = prpCmainSub.getId().getPolicyNo();
							flag = prpCmainSub.getFlag();
//							// 如果强制保单号码是错误的，则不加理会。
							if (!policyService.isExist(relateMainPolicyNo)){
								continue;
							}
							if (!endorseViewHelper.checkRelate(relatePolicyNo , relateMainPolicyNo, request.getDamageDate(), request.getDamageHour() )) {
								continue;
							}
							if (flag.length() > 1 && flag.substring(0, 1).equals("1")) {
								if (relatePolicyNo.equals(policyNo)) { // 以商业保单查询
									//policySub = endorseViewHelper.findForEndorBefore(relateMainPolicyNo, damageDate, damageHour);
									qs_prpCmainTemp = endorseViewHelper.findPrpCmain(relateMainPolicyNo, request.getDamageDate(), request.getDamageHour());
									if (request.getDamageHour() == null || "".equals(request.getDamageHour())) {
//										damageHour = "0";
										request.setDamageHour("0");
									}
									sy_valid = daaRegistViewHelper.checkDate(httpServletRequest, relatePolicyNo, request.getDamageDate(), Integer.parseInt(request.getDamageHour()));
									qs_valid = daaRegistViewHelper.checkDate(httpServletRequest, relateMainPolicyNo, request.getDamageDate(), Integer.parseInt(request.getDamageHour()));
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
									qs_prpCmainTemp = this.endorseViewHelper.findPrpCmain(relatePolicyNo, request.getDamageDate(), request.getDamageHour());
									if ("".equals(request.getDamageHour())) {
//										damageHour = "0";
										request.setDamageHour("0");
									}
									qs_valid = daaRegistViewHelper.checkDate(httpServletRequest, relateMainPolicyNo, request.getDamageDate(), Integer.parseInt(request.getDamageHour()));
									sy_valid = daaRegistViewHelper.checkDate(httpServletRequest, relatePolicyNo, request.getDamageDate(), Integer.parseInt(request.getDamageHour()));
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
				httpServletRequest.setAttribute("qsFlag", qsFlag);//這區不能刪 下方內有用到 daaRegistViewHelper.policyDtoToView4Ws(
				httpServletRequest.setAttribute("isMainPolicyNo", isMainPolicyNo);
//				// 强制保单关联信息写到报案中
				Prplregistrpolicy prpLRegistRPolicyOfCompel = null;
				if ("Y".equals(qsFlag)) {
					prpLRegistRPolicyOfCompel = new Prplregistrpolicy();
					prpLRegistRPolicyOfCompel.getId().setPolicyNo(mainPolicyNo);
					prpLRegistRPolicyOfCompel.setRiskCode(compelRiskCode);
				}
				httpServletRequest.setAttribute("prpLregistRPolicyNo", prpLRegistRPolicyOfCompel);
				if (flushflag != null) {// 修改出险时间,保留修改部分前的数据。
					daaRegistViewHelper.setRegistDtoView(httpServletRequest, null);
				} else {// 正常备案
					daaRegistViewHelper.policyDtoToView4Ws(httpServletRequest,request,claimExternalSourceVo,prpCmain);
				}
				riskCode = BusinessRuleUtil.getRiskCode(policyNo, "PolicyNo");
				httpServletRequest.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
				httpServletRequest.setAttribute("RISKCODE", riskCode);
			}
//			// 2。查询保单信息,整理输入，用於初始界面显示
			if (editType.equals("EDIT") || editType.equals("SHOW") || editType.equals("DELETE") || editType.equals("TimeOut") || editType.equals("PERFECT")) {
				Prplregistrpolicy prpLRegistRPolicy = new Prplregistrpolicy();
				Collection<?> collection = prpLregistrpolicyService.findByRegistNo(request.getRegistNo());
				if (collection != null && collection.size() > 1) {
					qsFlag = "Y";
					Iterator<?> it = collection.iterator();
					while (it.hasNext()) {
						prpLRegistRPolicy = (Prplregistrpolicy) it.next();
						if (prpLRegistRPolicy.getPolicyType().equals(Prplregistrpolicy.COMPEL_POLICY)) {
							mainPolicyNo = prpLRegistRPolicy.getId().getPolicyNo();
//								PolicyDto policyDto = policyService.findByPrimaryKey(mainPolicyNo);
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
//					// 历史数据？
				}
				httpServletRequest.setAttribute("quaryPolicyNo", quaryPolicyNo);
				httpServletRequest.setAttribute("intPayFee", String.valueOf(intPayFee));
				httpServletRequest.setAttribute("qs_prpCmainDto", qs_prpCmain);
				httpServletRequest.setAttribute("mainPolicyNo", mainPolicyNo);
				httpServletRequest.setAttribute("qsFlag", qsFlag);
				httpServletRequest.setAttribute("editType", editType);
//				// 强制保单关联信息写到报案中
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
			if (null!=prpLregist && DataUtils.emptyToNull(prpLregist.getRegistType()) == null) {
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
			////forward = BusinessRuleUtil.getForward(httpServletRequest, riskCode, "regis", editType, 1);
			////httpServletRequest.setAttribute("com_sinosoft_forward", forward);
			java.util.Date outTime = new java.util.Date();
			long between = (outTime.getTime() - inTime.getTime()) / 1000;// 除以1000是为了转换成秒
			if (between > 120) {
				Log.init("CPU_Max_Error", "CPU_Max_Error", true);
				Log.println(new java.util.Date() + "====editType===" + editType + "===" + "policyNo===" + request.getPolicyNo() + "===" + "registNo===" + request.getRegistNo() + "===" + "user===" + user.getUserCode() + "===" + "TimeUsed==" + between);
			}
			//============================================================
			
//		    List<PrpCmain> prpCmainList = new ArrayList<PrpCmain>();
	//	    try{
	//		    prpCmainList = (List<PrpCmain>)httpServletRequest.getAttribute("prpCmainList");
	//	    }catch(Exception e){
	//	    	
	//	    }
	//	    	
		    httpServletRequest.setAttribute("othFlag", othFlag);//request.getOthFlag());
		    httpServletRequest.setAttribute("registNo", request.getRegistNo());
		    
			
			/**
			 * ===最後提交後送出================================================
			 * >>RegistEditPostAction.java registEditPost()
			 */
		    
//		    this.clearErrorsAndMessages();
//			String forward = ""; // 向前流转
//			String registNo = "";
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
//			HttpServletRequest httpServletRequest = getRequest();
//			registNo = httpServletRequest.getParameter("prpLregistRegistNo");
		    httpServletRequest.setAttribute("prpLregistRegistNo", registNo);
			// 交强险迁移 报案类型 0 ：商业险单独报案 1：交强险单独报案 2：商业、交强险关联报案
			String registType = prpLregist.getRegistType();//httpServletRequest.getParameter("registType");
		    httpServletRequest.setAttribute("registType", registType);
			// 如果是新登记，则从取号表中取赔案号码，如果是修改，则保持原来的claimNo不变
			String createWorkFlowFlag = "0"; // 是否需要创建工作流，如果第一次保存，需要创建工作流 0				// 表示不需要
			String registPolicyNo = request.getPolicyNo();//httpServletRequest.getParameter("prpLregistPolicyNo");
			httpServletRequest.setAttribute("prpLregistPolicyNo", registPolicyNo);
			//String riskCode = httpServletRequest.getParameter("prpLregistRiskCode");
			httpServletRequest.setAttribute("prpLregistRiskCode", riskCode);
			// 交强险迁移
			if (registType != null && registType.equals("1")) {
				registPolicyNo = (String)httpServletRequest.getAttribute("mainPolicyNo");
				riskCode = ConstantCodes.RISKCODE_DAZ;
			}
			String scheduleType = (String)httpServletRequest.getAttribute("scheduleType");
			//??String editType = httpServletRequest.getParameter("editType");
			httpServletRequest.setAttribute("editType", editType);
			String typeFlag = "";
			if ("ALLS".equals(scheduleType)) {
				typeFlag = "10";
			}
			//??String mainPolicyNo = httpServletRequest.getParameter("mainPolicyNo");
			httpServletRequest.setAttribute("mainPolicyNo", mainPolicyNo);
			//??String qsFlag = httpServletRequest.getParameter("qsFlag");
			httpServletRequest.setAttribute("qsFlag", qsFlag);
			String strRiskType = codeService.translateRiskCodetoRiskType(riskCode);
			//??UserDto user = (UserDto) httpServletRequest.getSession().getAttribute("user");
			String comCodePolicy = user.getComCode();//httpServletRequest.getParameter("prpLregistComCode");
			httpServletRequest.setAttribute("prpLregistComCode", comCodePolicy);
			// 传入参数是 节点的类型
			// 是否查勘调度
			String strScheduleTypeCheck = (String)httpServletRequest.getAttribute("nextScheduleTypeCheck");
			String strLastAccessedTime = "" + httpServletRequest.getSession().getLastAccessedTime() / 1000;
			String oldLastAccessedTime = (String) httpServletRequest.getSession().getAttribute("oldRegistLastAccessedTime");
//			if (DataUtils.emptyToNull(oldLastAccessedTime) != null) {
//				throw new UserException(1, 3, "0000", getText("prompt.regist.multipleSubmit"));//請不要重複提交！
//			}
			if (registNo == null || registNo.length() < 1) { // 取报案号码
				String tableName = "prplregist";
				// 报案号生成规则调整
				// 机构设置如果获取不到，就按照总公司读取
				String prpLregistDamageCode = user.getComCode();//httpServletRequest.getParameter("prpLregistDamageCode");
				httpServletRequest.setAttribute("prpLregistDamageCode", prpLregistDamageCode);
				Map<String, Object> infoMap = new HashMap<String, Object>();
				infoMap.put("damageCode", prpLregistDamageCode);
				infoMap.put("policyNo", request.getPolicyNo());//registPolicyNo
				registNo = billService.getNoByPolciyYear(tableName, riskCode, infoMap);
				httpServletRequest.setAttribute("com.sinosoft.registno", registNo);
				httpServletRequest.setAttribute("riskCode", riskCode);
				createWorkFlowFlag = "1";
			}
			httpServletRequest.setAttribute("registNo", registNo);
			httpServletRequest.setAttribute("newWorkFlow", createWorkFlowFlag);
			String status = "4";//return beforeSaveForm(this,'4'); JSP存檔送出4//(String)httpServletRequest.getAttribute("buttonSaveType");
			httpServletRequest.setAttribute("buttonSaveType", status);//這參數後面method會用到
			// 用viewHelper整理界面输入****************************************************************************
			RegistDto registDto = daaRegistViewHelper.externalToDto(httpServletRequest,claimExternalSourceVo,prpCmain,prpLregist);
			WorkFlowDto workFlowDto = new WorkFlowDto();
			String actorId = (String)httpServletRequest.getAttribute("swfLogActorId");
			// 创建工作流、处理启用新工作流的生产的工作任务时
			boolean create = "1".equals(createWorkFlowFlag);
			if (WorkFlowDto.isWorkflowswitch() && ("1".equals(createWorkFlowFlag) || !"".equals(DataUtils.dbNullToEmpty(actorId)))) {
				workFlowDto = this.jbpmBusinessViewHelper.getJbpmWorkFlowDto4Ws(httpServletRequest, !create, create, status, null, null, null, null, null);
				workFlowDto.setCreate("1".equals(createWorkFlowFlag));
				workFlowDto.setBessinessNo(registNo);
				if(workFlowDto.getSubmit()){
					workFlowDto.getJbpmDto().putParamsMap("registType", registDto.getPrpLregist().getRegistType());// 备案类型
//					if("Q".equals(strRiskType)){//(這裡WS不是火險)火險簡易流程標誌 --22-分進業務 走簡易流程
						//??PrpCmain prpCmain = this.prpCmainService.findByPrimaryKey(registDto.getPrpLregist().getPolicyNo());
//						workFlowDto.getJbpmDto().putParamsMap("simpleFlowFlag", "3".equals(prpCmain.getCoinsFlag()));
//					}
				}
			} else {// 旧工作流处理入口
				//workFlowDto = this.getWorkFlowDto(registDto, strRiskType, typeFlag, registPolicyNo, mainPolicyNo, qsFlag);
			    workFlowDto = this.businessViewHelper.getWorkFlowDto4Ws(httpServletRequest, !create, create, status, null, null, null, null, null);
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
//						throw new UserException(-1, 0, getText("check.report"), getText("prompt.regist.commonBusinessRegistNo") + hisSharingRegistNo + getText("prompt.regist.ifRegistPleaseCancelFlagHandle"));
						respClaimData_in.setCode("E0013");
						respClaimData_in.setMsg("此保單涉及同業共攤，相關備案號為：" + hisSharingRegistNo + "。如需備案，請通過人工判取消此標記。");//直接的錯誤 直接返回
						FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+respClaimData_in.getMsg(), wsCtxt, null);
						return respClaimData_in;
					}
				}
				this.registService.save4Ws(registDto, workFlowDto, httpServletRequest.getSession());
//				//this.jbpmBusinessViewHelper.saveBusiness(this.registService,"save",workFlowDto,registDto);
//				//user.setUserMessage(registNo);
			} else {
				if (workFlowDto.getOperateResult() < 0) {
					//注意:創建工作流流程時，未找到相關工作流模板的設定，請聯系系統管理員進行相應配置！
					user.setUserMessage("注意創建工作流流程時，未找到相關工作流模板的設定，請聯繫系統管理員進行相應配置！");
				} else {
					if ("PERFECT".equals(editType)) {
						registService.saveCallCenter(registDto, null, null);
					} else {
						registService.save(registDto);
					}
					//if (!"1".equals(httpServletRequest.getParameter("callCenterFlag"))) {
//						////;注意:沒有發現與工作流流程相關任何數據！
//						//user.setUserMessage(registNo + getText("prompt.regist.noWorkFlowDataFound"));
//					//}
				}
			}
			httpServletRequest.getSession().setAttribute("oldRegistLastAccessedTime", strLastAccessedTime);
			// 跟据配置项 SCHEDULE_AUTOCOMMIT，对於需要自动跳转到调度的部门加入自动跳转的功能
			String strSchedule = AppConfig.get("sysconst.SCHEDULE_AUTOCOMMIT");
			String comCodeTemp = user.getComCode();
			if ("4".equals(status) && "D".equals(strRiskType) && strSchedule.indexOf(comCodeTemp) >= 0 && (!("3100".equals(comCodeTemp)))) {
//				// 需要自动跳转,必须是提交後才能进行的跳转
//				// 要判断是查勘调度，还是定损调度
				String scheduleRef = "/claim/scheduleDealRegist.do?prpLscheduleMainWFRegistNo=" + registNo + "&prpLscheduleMainWFSurveyNo=0" + "&status=0" + "&riskCode=" + riskCode + "&editType=ADD";
				String goFlowID = "";
				int goLogNo = 0;
//				// 查找跳转位置
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
//					HttpServletResponse httpServletResponse = getResponse();
//					httpServletResponse.sendRedirect(scheduleRef);
//					return NONE;
				}
			}
			httpServletRequest.setAttribute("prpLregist", registDto.getPrpLregist());
			// 取得承保的險別信息
//			policyNo = (String) httpServletRequest.getAttribute("prpLregistPolicyNo");
			QueryRule queryRule2 = QueryRule.getInstance();
			queryRule2.addSql(" 1=1 and policyNo='" + policyNo + "'");
			List<PrpCitemKind> collections = prpCitemKindService.findPrpCitemKind(queryRule2);
			StringBuffer strRiskName = new StringBuffer();
			for (int i = 0; i < collections.size(); i++) {
				PrpCitemKind prpCitemKindDto = collections.get(i);
				// 判斷是國廠還是進口的
				if ("F".equals(prpCitemKindDto.getKindCode())) {
					if ("4".equals(prpCitemKindDto.getModeCode()) || "6".equals(prpCitemKindDto.getModeCode())) {
						strRiskName.append(prpCitemKindDto.getKindName());
						strRiskName.append("(國廠)");//(國廠)
					} else if ("5".equals(prpCitemKindDto.getModeCode()) || "7".equals(prpCitemKindDto.getModeCode())) {
						strRiskName.append(prpCitemKindDto.getKindName());
						strRiskName.append("(進口)");//(進口)
					}
				} else {
					strRiskName.append(prpCitemKindDto.getKindName());
				}
				strRiskName.append(',');
			}
			String riskNames = strRiskName.toString();
			riskNames = riskNames.substring(0, riskNames.length() - 1);
//			if (httpServletRequest.getParameter("buttonSaveType").trim().equals("4")) {
//				if ("PERFECT".equals(editType)) {
//					this.addActionMessage(getText("prompt.registEdit.submit"));//備案修改任務提交成功
//				} else {
//					this.addActionMessage(getText("prompt.regist.submit"));//備案訊息提交成功
//				}
//			} else {
//				this.addActionMessage(getText("prompt.regist.save"));//備案訊息儲存成功
//			}
//			this.addActionMessage(getText("db.prpLregist.registNo"));//備案號碼
//			this.addActionMessage(registNo);<<<<
			// 报案後直接调度
			httpServletRequest.setAttribute("policyNo", registPolicyNo);
			httpServletRequest.setAttribute("handleDept", comCodePolicy);
			String swfLogFlowID = "";
//			if (!"1".equals(httpServletRequest.getParameter("callCenterFlag"))) {
				if (workFlowDto.getSwfFlowMain() != null) {
					swfLogFlowID = workFlowDto.getSwfFlowMain().getFlowID();
					httpServletRequest.setAttribute("swfLogFlowID", swfLogFlowID);
				}
//			}
			// 默认不需要自动跳转
//			forward = "success";
//			return forward;
			
			//===最後提交階段，並回返參數================================================
			respClaimData_in.setCode("S0000");
			respClaimData_in.setMsg("備案成功");
			respClaimData_in.setRegistNo(registNo);

			claimExternalSourceVo.getMemo();
			String[] sendTo = new String[]{"h02"};
			if(1==1){//CLM9001 sit uat 留下	prod刪掉			
				sendTo = new String[]{"dp0713"};
			}
			if(null!=claimExternalSourceVo.getMemo() && !"".equals(claimExternalSourceVo.getMemo().trim())){
				this.registService.sendMail(sendTo,registNo,claimExternalSourceVo);
			}
//			respClaimData_out.setRtnJson(JsonUtil.getJSONString(respJsonInfo));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        e.printStackTrace(pw);
	        String stackTrace = sw.toString();
			respJsonInfo.setOperateDate(sdFormat.format(current));
			respJsonInfo.setStackTrace(stackTrace);
			respClaimData_in.setCode("E0000");
			String tmpStr = stackTrace.substring(0,stackTrace.indexOf("\t"));
			respClaimData_in.setMsg("發生未知的錯誤請聯繫系統人員-"+sdFormat.format(current));
			if(tmpStr.indexOf("無效的理賠人員")!=-1){
				//無效的理賠人員
				respClaimData_in.setCode("E0016");
				respClaimData_in.setMsg("無效的理賠人員");
			}
	        try {
				System.out.println("未知的錯誤-"+sdFormat.format(current));
				System.out.println(stackTrace);
				FileUtil.insertLOG(logTitle, policyNoLog, "code:"+respClaimData_in.getCode(), "Excep", startDate, "多元理賠備案介接(BASE64)"+stackTrace.substring(0,stackTrace.length()>1500?1499:stackTrace.length()), wsCtxt, null);
//				respClaimData_in.setRtnJson(JsonUtil.getJSONString(respJsonInfo));
			}catch(Exception ex){
			}
	        return respClaimData_in;
	        
		}finally{
        	cacheManager.clearCache(claimExternalSourceVo.getMultiRecepNo());
        }
//		respClaimData_in.setRtnJson("rtnJson test");
		//返回CWP
		
		respClaimData_out.setCode(respClaimData_in.getCode());
		respClaimData_out.setMsg(respClaimData_in.getMsg());
		respClaimData_out.setRegistNo(respClaimData_in.getRegistNo());
//		respClaimData_out.setOperateDate(respClaimData_in.getOperateDate());
//		respClaimData_out.setRtnJson(respClaimData_in.getRtnJson());
		Date endDate = new Date();
		String registNoLog = "";
		if(null!=respClaimData_out.getRegistNo() && respClaimData_out.getRegistNo().length()>10){
			registNoLog = respClaimData_out.getRegistNo().substring(0,3)+"~"+respClaimData_out.getRegistNo().substring(respClaimData_out.getRegistNo().length()-10);
		}
		FileUtil.insertLOG(logTitle, policyNoLog, "rtn:"+registNoLog, "END", endDate, "多元理賠備案介接(BASE64)", wsCtxt, null);
		return respClaimData_out;
	}
	
	public RespClaimData test(ReqRegist request) {
		RespClaimData vo = new RespClaimData();

		MessageContext msgCtxt = wsCtxt.getMessageContext();
	    HttpServletRequest httpServletRequest = (HttpServletRequest)msgCtxt.get(MessageContext.SERVLET_REQUEST);
	    String clientIP = httpServletRequest.getRemoteAddr();
	    System.out.println("IP:"+clientIP);
	    httpServletRequest.setAttribute("myPhone", "0910123765");
		try{
			String testAttribute = (String)httpServletRequest.getAttribute("testAttribute");
			System.out.println("testAttribute="+testAttribute);
			PrpLregistService prpLregistService = (PrpLregistService)ServiceFactory.getService("prpLregistService");
			PrpCmainService prpCmainService = (PrpCmainService)ServiceFactory.getService("prpCmainService");
			
			String strSql = " policyNo='" + request.getSendJson() + "'";//錯誤的測試
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addSql(strSql);
	        //保單險類
			List<PrpCmain> prpCmainList = prpCmainService.findPrpCmain(queryRule);
			if(null!=prpCmainList && prpCmainList.size()>0){
				List<PrpLregist> prpLregistList = prpLregistService.findPrpLregist(queryRule);
				if(null!=prpLregistList && prpLregistList.size()>0){
					vo.setCode("200");
					vo.setMsg("備案號: "+((String)httpServletRequest.getAttribute("myPhone"))+prpLregistList.get(0).getRegistNo());
					return vo;
				}
			}else{
				vo.setCode("400");
				vo.setMsg("查無此保單 ");
				return vo;
			}

//			vo.setMsg(request.getCode()+"/"+prpLregist.getAlterType());
        } catch (Exception e) {
            vo.setCode("400");
            vo.setMsg("查詢失敗!!! ");
        } 
		vo.setCode("200");
		return vo;
	}
	
}
