package com.sinosoft.claim.workflow.util;

import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PowerService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.DateCompute;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schedule.service.facade.ScheduleService;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDuser;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.Prplregistrpolicy;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.model.SwfLogStore;
import com.sinosoft.claim.schema.model.SwfNotion;
import com.sinosoft.claim.schema.model.SwfPath;
import com.sinosoft.claim.schema.model.UtiUwLevel;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.model.PrpDautoDpLog;//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
import com.sinosoft.claim.schema.service.facade.PrpDautoDpLogService;//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrplregistrpolicyService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.schema.service.facade.SwfLogStoreService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.ui.control.action.UIPowerInterface;
import com.sinosoft.claim.undwrt.util.TaskDealViewHelper;
import com.sinosoft.claim.workflow.service.facade.WorkFlowService;
import com.sinosoft.claim.workflow.vo.MidResultConfigDto;
import com.sinosoft.claim.workflow.vo.StatStatusDto;
import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.sysframework.common.util.DataUtils;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
/**
 * <p>Title: WorkFlowViewHelper</p>c
 * <p>Description:工作流ViewHelper类，在该类中完成页面数据的整理</p>
 * <p>Copyright: Copyright 中科软科技股份有限公司(c)</p>
 * @author 中科软
 * @version 1.0
 * <br>
 */
public class WorkFlowViewHelper {

	/** 工作流日志服务 */
	private SwfLogService swfLogService;
	/** 工作流日志转储信息服务 */
	private SwfLogStoreService swfLogStoreService;
	/** 代码服务 */
	private CodeService codeService;
	/** 赔案保单关联信息服务 */
	private PrplregistrpolicyService prpLregistrpolicyService;
	/** 工作流服务 */
	private WorkFlowService workFlowService;
	/** 工作流图象展现ViewHelper */
	private WorkFlowImageViewHelper workFlowImageViewHelper;
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 用户基本信息服务 */
	private PrpDuserService prpDuserService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	/** 调度服务 */
	private ScheduleService scheduleService;
	/** 工作流引擎viewHelper */
	private WorkFlowEngineViewHelper workFlowEngineViewHelper;
	/** 理算实赔服务 */
	private CompensateService compensateService;
	/** 保单数据传输对象服务 */
	private PolicyService policyService;
	/** 人员级别设置信息服务 */
	private UtiUwLevelService utiUwLevelService;
	/** 任务处理viewHelper */
	private TaskDealViewHelper taskDealViewHelper;
	/** 工作流一些统计viewHelper */
	private WorkFlowStatViewHelper workFlowStatViewHelper;
	/** 保险关系人信息服务 */
	private PrpCinsuredService prpCinsuredService;
	private PowerService powerService;
	
	private WfLogService wfLogService;
	
	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
	private PrpDautoDpLogService prpDautoDpLogService;

	// private SwfLog swfLog = new SwfLog();

	/* ========================第一部分：工作引擎正向操作======================== */
	/**
	 * 操作工作流的数据整理,此函数就是所有工作流流转的引擎入口， 想知道工作流是如何运转的，从这个函数看起吧。
	 * @param user UserDto 用户dto
	 * @param swfLogFunctionIn 传参数
	 * @throws Exception
	 * @return WorkFlowDto 工作流程数据传输数据结构
	 */
	public WorkFlowDto viewToDto(UserDto user, SwfLog swfLogFunctionIn) throws Exception {
		// 取得当前用户信息，写操作员信息到Dto中
		return this.getWorkFlowEngineViewHelper().viewToDto(user, swfLogFunctionIn);
	}

	/* ========================第二部分：工作流引擎逆向操作======================== */
	/**
	 * 回退的工作流(利用模板进行回退)
	 * @param request HttpServletRequest
	 * @param flowID String 工作流流程编码
	 * @param logNo int 工作流流程顺序号
	 * @param keyOut String 记录新节点的KeyOut的值，这样退回的数据就可以直接按业务的号码修改
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto getBackFlowInfo(HttpServletRequest request, String flowID, int logNo, String keyOut) throws Exception {
		return this.getWorkFlowEngineViewHelper().getBackFlowInfo(request, flowID, logNo, keyOut);
	}

	/**
	 * 回退的工作流(利用人对人的方式进行回退，即只想上一个节点是什么，复制後提交就行了)
	 * @param flowID String 工作流流程编码
	 * @param logNo int 工作流流程顺序号
	 * @param keyOut String 记录新节点的KeyOut的值，这样退回的数据就可以直接按业务的号码修改
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto getBackFlowInfo(UserDto user, String flowID, int logNo) throws Exception {
		return this.getWorkFlowEngineViewHelper().getBackFlowInfo(user, flowID, logNo);
	}

	/* ========================（工作流引擎逆向操作）结束============================ */

	/* ========================第三部分：工作流图像展现操作======================== */
	/**
	 * 设置工作流流程到界面
	 * @param httpServletRequest HttpServletRequest
	 * @param flowID String
	 * @throws Exception
	 */
	public void setFlowDtoToView(HttpServletRequest httpServletRequest, String flowID) throws Exception {
		SwfLog swfLog = this.getWorkFlowService().findNodeByPrimaryKey(flowID, 1);
		if (swfLog != null) {
			httpServletRequest.setAttribute("wfLogBusinessNo", swfLog.getBusinessNo());
			httpServletRequest.setAttribute("riskCode", swfLog.getRiskCode());
		} else {
			SwfLogStore swfLogStore = this.getWorkFlowService().findSwfLogStoreDtoByPrimaryKey(flowID, 1);
			httpServletRequest.setAttribute("wfLogBusinessNo", swfLogStore.getBusinessNo());
			httpServletRequest.setAttribute("riskCode", swfLogStore.getRiskCode());
		}
		this.workFlowImageViewHelper.setFlowDtoToView(httpServletRequest, flowID);
	}

	/* ========================（工作流图像展现操作）结束============================ */
	/**
	 * 检查需要进行关联多个保单的,用单的工作流的
	 * @param nodeType 节点类型
	 * @throws Exception
	 * @return boolean
	 */
	private SwfLog translateRelatePolicyOnTaskQueryOne(SwfLog swfLogTemp) throws Exception {
		List<Prplregistrpolicy> relatePolicyList = new ArrayList<Prplregistrpolicy>();
		if (this.checkNeedFindRelatePolicy(swfLogTemp.getNodeType())) {
			// 取得多个关联的保单信息
			relatePolicyList = this.getPrpLregistrpolicyService().findByRegistNo(swfLogTemp.getRegistNo());
		} else {
			// 如果不需要的话，直接将流程中的保单号码放到显示用的relatePolicyList变量中
			Prplregistrpolicy prpLregistRPolicy = new Prplregistrpolicy();
			prpLregistRPolicy.getId().setPolicyNo(swfLogTemp.getPolicyNo());
			prpLregistRPolicy.getId().setRegistNo(swfLogTemp.getRegistNo());
			relatePolicyList.add(prpLregistRPolicy);
		}
		swfLogTemp.setRelatePolicyList(relatePolicyList);
		return swfLogTemp;
	}
	
	/***
	 * 工作流查询入口
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @throws Exception 
	 */
	public void getWorkFlowLogList(HttpServletRequest request, int pageNo, int pageSize) throws Exception {
		StringBuffer conditions = null;
		String searchFlag = request.getParameter("searchFlag");
		if (!"true".equals(searchFlag)) {//翻页的以上次查询为条件
			Object condition = request.getSession().getAttribute("workflowQueryConditions");
			if(condition != null ){
				conditions = new StringBuffer(String.valueOf(condition));
			}
		} 
		if(conditions == null){//按钮查询
			conditions = new StringBuffer(this.getWorkFlowQueryConditions(request));
		}
		String nodeType = request.getParameter("nodeType");
		String status = request.getParameter("status");
		Page page = null;
		if ("cance".equals(nodeType.trim()) && "4".equals(status)) {
			String statements = conditions.toString().replaceAll("SwfLog", "View_SwflogAll");
			page = this.getWorkFlowService().findViewSwfLogAll(statements, pageNo, pageSize);
		} else {
			page = this.getWorkFlowService().findNodesByConditions(conditions.toString(), pageNo, pageSize);
		}
		List<?> pageResult = page.getResult();
		if (pageResult != null && !pageResult.isEmpty()) {
			SwfLog tempSwfLog = null;
			List<SwfLog> tempList = null;
			String tempStr = "";
			PrpCmain tempPrpCmain = null;
			PrpLcompensate prpLcompensate = null;
			PrpLclaim prpLclaim = null;
			for (Iterator<?> it = pageResult.iterator(); it.hasNext();) {
				tempSwfLog = (SwfLog) it.next();
				tempSwfLog.setClassCode(this.getCodeService().translateClassCodeByRiskCode(tempSwfLog.getRiskCode()));
				tempSwfLog.setRiskType(this.getCodeService().translateRiskCodetoRiskType(tempSwfLog.getRiskCode()));
				tempList = this.getWorkFlowService().findByConditions(" flowID = '"+tempSwfLog.getId().getFlowID()+"' and TypeFlag = '8'");
				if(tempList!=null && !tempList.isEmpty()){
					tempSwfLog.setDfFlag("Y");
				}
				if("claim".equals(nodeType) && status.equals("0")){
					tempPrpCmain = this.getPolicyService().findPrpCmainDtoByPrimaryKey(tempSwfLog.getPolicyNo());
					if(tempPrpCmain!=null){
						tempStr = tempPrpCmain.getOthFlag();
						if(tempStr!=null && tempStr.length() > 3){
							tempSwfLog.setOtherFlag(tempStr.substring(3,4));
						}
					}
					tempSwfLog.setLeftHour(this.getLeftHour(tempSwfLog));
				}
				if("sched".equals(nodeType) && "0".equals(status)){//未处理分案,处理停留时间
					tempSwfLog.setStopTimeDesc(DateCompute.betweenDate(tempSwfLog.getFlowInTime(), DateTime.current().toString(DateTime.YEAR_TO_SECOND), DateTime.DAY_TO_MINUTE));
				}
				if("compe".equals(nodeType)){//未处理理算、理算注销拒赔，计算书状态
					tempStr = this.getCompensateService().getCompFlagByConditions(tempSwfLog.getKeyIn());
					String[] flags = tempStr.split("-");
					tempSwfLog.setCompeFlag(flags[0]);
					tempSwfLog.setCompeCount(Integer.parseInt(flags[1]));
					//設置簡易賠案的撤銷
					if(ConstantCodes.RISKCODE_DAA.equals(tempSwfLog.getRiskCode())
							|| ConstantCodes.RISKCODE_DAZ.equals(tempSwfLog.getRiskCode())){
						prpLclaim = this.prpLclaimService.findPrpLclaim(tempSwfLog.getKeyIn());
						if("1".equals(prpLclaim.getSimpleFlag())){
							tempSwfLog.setSimpleFlag(true);
						}
					}
				}
				if("compp".equals(nodeType) && "4".equals(status)){
					prpLcompensate = this.getPrpLcompensateService().findPrpLcompensate(tempSwfLog.getBusinessNo());
					if(prpLcompensate != null){
						tempSwfLog.setSumPaid(prpLcompensate.getSumPaid());
					}
					prpLclaim = this.getPrpLclaimService().findPrpLclaim(tempSwfLog.getKeyIn());
					if(prpLclaim != null){
						tempSwfLog.setDamageDate(prpLclaim.getDamageStartDate());
					}
				}
				this.translateRelatePolicyOnTaskQueryOne(tempSwfLog);
			}
		}
		SwfLog swfLog = new SwfLog();
		// 转换保单号码用的
		swfLog.setSwfLogList(page.getResult());
		request.setAttribute("page", page);
		request.setAttribute("swfLog", swfLog);
		// 防止再次刷新的时候会失去值
		request.setAttribute("status", status);
		request.setAttribute("nodeType", nodeType);
		request.getSession().setAttribute("workflowQueryConditions", conditions.toString());
	}
	
	/***
	 * 工作流查询入口(FOR DpFlow)
	 * mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @throws Exception 
	 */
	public void getWorkFlowLogListForDpFlow(HttpServletRequest request, int pageNo, int pageSize) throws Exception {
		
		String operatorCode = request.getParameter("operatorCode");//0:一般修改作業/1:審核作業
		
		//原路徑給一般修改作業用
		StringBuffer conditions = null;
		String searchFlag = request.getParameter("searchFlag");
		String InputStatusCondition = request.getParameter("InputStatusCondition");
//		if (!"true".equals(searchFlag)) {//翻页的以上次查询为条件
//			Object condition = request.getSession().getAttribute("workflowQueryConditions");
//			if(condition != null ){
//				conditions = new StringBuffer(String.valueOf(condition));
//			}
//		} 
		if(conditions == null){//按钮查询
			conditions = new StringBuffer(this.getDPworkFlowQueryConditions(request));
		}
		String nodeType = request.getParameter("nodeType");//compp
		String status = request.getParameter("status");//4
		Page page = null;
		String queryConditionChange = request.getParameter("queryConditionChange");
		
//		if ("cance".equals(nodeType.trim()) && "4".equals(status)) {
//			String statements = conditions.toString().replaceAll("SwfLog", "View_SwflogAll");
//			page = this.getWorkFlowService().findViewSwfLogAll(statements, pageNo, pageSize);
//		} else {
		if(null!=queryConditionChange){
			page = this.getWorkFlowService().findNodesByConditions(conditions.toString(), pageNo, pageSize);
		}else{
			page = new Page();
		}
//		}
		List<?> pageResult = page.getResult();
		if (pageResult != null && !pageResult.isEmpty()) {
			SwfLog tempSwfLog = null;
			List<SwfLog> tempList = null;
			String tempStr = "";
			PrpCmain tempPrpCmain = null;
			PrpLcompensate prpLcompensate = null;
			PrpLclaim prpLclaim = null;
			for (Iterator<?> it = pageResult.iterator(); it.hasNext();) {
				tempSwfLog = (SwfLog) it.next();
				tempSwfLog.setClassCode(this.getCodeService().translateClassCodeByRiskCode(tempSwfLog.getRiskCode()));
				tempSwfLog.setRiskType(this.getCodeService().translateRiskCodetoRiskType(tempSwfLog.getRiskCode()));
				tempList = this.getWorkFlowService().findByConditions(" flowID = '"+tempSwfLog.getId().getFlowID()+"' and TypeFlag = '8'");
				if(tempList!=null && !tempList.isEmpty()){
					tempSwfLog.setDfFlag("Y");
				}
//				if("claim".equals(nodeType) && status.equals("0")){
//					
//				}
//				if("sched".equals(nodeType) && "0".equals(status)){//未处理分案,处理停留时间
//					
//				}
//				if("compe".equals(nodeType)){//未处理理算、理算注销拒赔，计算书状态
//					
//				}
//				if("compp".equals(nodeType) && "4".equals(status)){
					prpLcompensate = this.getPrpLcompensateService().findPrpLcompensate(tempSwfLog.getBusinessNo());
					if(prpLcompensate != null){
						tempSwfLog.setSumPaid(prpLcompensate.getSumPaid());
					}
					prpLclaim = this.getPrpLclaimService().findPrpLclaim(tempSwfLog.getKeyIn());
					if(prpLclaim != null){
						tempSwfLog.setDamageDate(prpLclaim.getDamageStartDate());
					}
					String[] rtnStatus = new String[3];
					List<PrpDautoDpLog> prpDautoDpLogList = null;
					if(null!=prpLcompensate){
						if(prpLcompensate.getCompensateNo().equals("C181221AL0006301") || prpLcompensate.getCompensateNo().equals("C180225BL00000101")){
							System.out.println("debugger:"+prpLcompensate.getCompensateNo());
						}
						PrpDautoDpLog prpDautoDpLog = new PrpDautoDpLog();
						prpDautoDpLog.setCompensateNo(prpLcompensate.getCompensateNo());
						//這裡query prpdautodplog
						prpDautoDpLogList = this.getPrpDautoDpLogService().findPrpDautoDpLogStatus(prpDautoDpLog);
						if(null!=prpDautoDpLogList && prpDautoDpLogList.size()>0){
							tempSwfLog.setDpLogInputStatus(prpDautoDpLogList.get(0).getInputStatus());
							tempSwfLog.setDpLogInputStatus(prpDautoDpLogList.get(0).getInputStatus());
							tempSwfLog.setDpLogId(prpDautoDpLogList.get(0).getId().getLogId());
							//StringConvert.getParam(request, "userName", ConstantCodes.YUI_CHARSET);
							if(null!=prpDautoDpLogList.get(0).getInputUser()){
								tempSwfLog.setInputUser(prpDuserService.findPrpDuser(prpDautoDpLogList.get(0).getInputUser()).getUserName());
							}
							if(null!=prpDautoDpLogList.get(0).getReviewInputUser()){
								tempSwfLog.setReviewUser(prpDuserService.findPrpDuser(prpDautoDpLogList.get(0).getReviewInputUser()).getUserName());
							}
//							if(null!=InputStatusCondition){
//								for(PrpDautoDpLog pd:prpDautoDpLogList){
//									if(pd.getInputStatus().equals(InputStatusCondition)){
//									}
//								}
//							}
						}
					}
//				}
				this.translateRelatePolicyOnTaskQueryOne(tempSwfLog);
			}
		}
		SwfLog swfLog = new SwfLog();
		// 转换保单号码用的
		swfLog.setSwfLogList(page.getResult());
		request.setAttribute("page", page);
		request.setAttribute("swfLog", swfLog);
		// 防止再次刷新的时候会失去值
		request.setAttribute("status", status);
		request.setAttribute("nodeType", nodeType);
		request.getSession().setAttribute("workflowQueryConditions", conditions.toString());
	}
	
	private long getLeftHour(SwfLog swfLog) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return swfLog.getTimeLimit()-(new Date().getTime()-sdf.parse(swfLog.getFlowInTime()).getTime())/(1000*60*60);
	}
	/***
	 * 根据查询条件组织conditions
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	private String getWorkFlowQueryConditions(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String nodeType = request.getParameter("nodeType");//节点名
		String status = request.getParameter("status");//状态
		String type = request.getParameter("type");//险类
		String funcName = request.getParameter("FuncName");
		//备案号码
		String strRegistNo = request.getParameter("RegistNo");
		String strRegistNoSign = request.getParameter("RegistNoSign");
		//保单号码
		String strPolicyNo = request.getParameter("PolicyNo");
		String strPolicyNoSign = request.getParameter("PolicyNoSign");
		//险种
		String strRiskCode = request.getParameter("RiskCode");
		String strRiskCodeNoSign = request.getParameter("RiskCodeNoSign");
		//开始时间
		String statStartDate = request.getParameter("statStartDate");
		String statEndDate = request.getParameter("statEndDate");
		//被保險人名稱
		String insuredName = request.getParameter("insuredName");
		String insuredNameSign = request.getParameter("insuredNameSign");
		//当前节点的业务号码可以是备案号、计算书号、调查号==
		String strBusinessNo = request.getParameter("BusinessNo");
		String strBusinessNoSign = request.getParameter("BusinessNoSign");
		//要保人ID
		String strAppliIdentifyNumber = request.getParameter("AppliIdentifyNumber");
		String strAppliIdentifyNumberSign = request.getParameter("AppliIdentifyNumberSign");
		//被保險人ID
		String strInsuredIdentifyNumber = request.getParameter("InsuredIdentifyNumber");
		String strInsuredIdentifyNumberSign = request.getParameter("InsuredIdentifyNumberSign");
		//車牌號碼
		String strLicenseNo = request.getParameter("LicenseNo");
		String strLicenseNoSign = request.getParameter("LicenseNoSign");
		//事故日期
		String strDamageStartDate = request.getParameter("damageStartDate");
		String strDamageEndDate = request.getParameter("damageEndDate");
		// 要保人名称
		String strAppliName = request.getParameter("AppliName");
		String strAppliNameSign = request.getParameter("AppliNameSign");
		
		StringBuffer conditions = new StringBuffer("");
		conditions.append(" nodeType = '"+nodeType+"' ");
		conditions.append(StringConvert.convertString("RegistNo", strRegistNo, strRegistNoSign));
		if (DataUtils.emptyToNull(strPolicyNo) != null) {
			//立案或者结案，或者是计算书处，关联和转换的吧？
			String temp = StringConvert.convertString("policyNo",strPolicyNo,strPolicyNoSign);
			if (!this.checkNeedFindRelatePolicy(nodeType)) {
				conditions.append(temp);
			} else {
				conditions.append(" and exists (select 0 from prplregistrpolicy p where SwfLog.registno = p.registno " + temp+ ") ");
			}
		}
		conditions.append(StringConvert.convertString("RiskCode", strRiskCode, strRiskCodeNoSign));
		conditions.append(StringConvert.convertString("insuredName", insuredName, insuredNameSign));
		if("-1".equals(status) && ("cancelApply".equals(funcName) || "specialApply".equals(funcName))){//注销拒赔
			conditions.append(" and NodeStatus <4 ");
		} else {
			conditions.append(" and ( NodeStatus='" + status + "' ");
			if("certi".equals(nodeType) && "0".equals(status)){
				conditions.append(" or NodeStatus='3' ");
			}
			conditions.append(" )");
		}
		if("check".equals(nodeType)){
			conditions.append(" and (handlerCode='" + user.getUserCode() + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "' or exists (select receiveOperatorCode from PrpLgeneralClaimTaskLog log where log.registNo = SwfLog.registNo and log.nodeStatus !='4' and log.receiveOperatorCode='" + user.getUserCode() + "')) ");
		}else{
			conditions.append(" and (handlerCode='" + user.getUserCode() + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "') ");
		}
		conditions.append(this.getDateCondtions(status, statStartDate, statEndDate));
		// 将待处理和正在处理的情况中加入不显示已经关闭流程的节点信息
		if (!("cance".equals(nodeType) && "4".equals(status))) {//流程状态控制
			conditions.append(" and (flowStatus='1' or flowStatus='2') ");
		}
		if("check".equals(nodeType)){
			conditions.append(" and ");
			if(!"acci".equals(type)){
				conditions.append(" not ");
			}
			conditions.append(" exists (select 0 from uticodetransfer where SwfLog.riskcode = uticodetransfer.outercode and risktype= 'E' and validstatus = '1')  ");
		}
		String typeFlag = request.getParameter("typeFlag");
		if ("1".equals(typeFlag)) {
			conditions.append(" and typeFlag='1' ");
		}
		//除了单证、理算环节的申请注销拒赔，其他都得要求已立案
		if("-1".equals(status) && "cancelApply".equals(funcName) && 
				!"certi".equals(nodeType) && !"compe".equals(nodeType)){
			if("claim".equals(nodeType)){
				conditions.append(" and exists (select 0 from prplclaim a where SwfLog.keyOut = a.claimNo )"); 
			}else{
				conditions.append(" and exists (select 0 from prplclaim a where SwfLog.registNo = a.registNo )"); 
			}
		}
		if("compe".equals(nodeType)){
			if("-1".equals(status) && "cancelApply".equals(funcName)){//理算申请注销拒赔(没有出计算书，没有预赔，没有申请注销)
				conditions.append(" and not exists (select 0 from SwfLog s where SwfLog.keyIn = s.keyIn and (nodeType='compp' or nodeType='speci' or nodeType='cance') )"); 
			}else{//未处理理算 只查未出计算书的、已核赔通过的
				conditions.append(" and not exists (select 0 from SwfLog s where SwfLog.keyIn = s.keyIn and nodeType='cance' and nodestatus = '0')"); 
				conditions.append(" and not exists (select 0 from prplcompensate where SwfLog.keyIn = prplcompensate.claimNo and (prplcompensate.compensateno like 'C%' or prplcompensate.compensateno like 'D%') and prplcompensate.underWriteFlag <>'1' and prplcompensate.underWriteFlag <>'3')"); 
			}
		}
		if ("endca".equals(nodeType) && "0".equals(status)) {
			conditions.append(" and not exists (select 0 from prplcompensate where SwfLog.keyIn = prplcompensate.claimNo and prplcompensate.underWriteFlag <>'1'");
			conditions.append(" and prplcompensate.underWriteFlag <>'3' and ( prplcompensate.compensateNo like 'C%' or prplcompensate.compensateno like 'D%' or prplcompensate.compensateNo like 'Y%' ))");
		}
		if(DataUtils.emptyToNull(strBusinessNo) != null ){
			if("check".equals(nodeType) && "acci".equals(type)){//调查号码
				conditions.append(StringConvert.convertString("keyIn", strBusinessNo, strBusinessNoSign));
			}else if ("claim".equals(nodeType)){
				conditions.append(StringConvert.convertString("keyOut", strBusinessNo, strBusinessNoSign));
			}else{
				conditions.append(StringConvert.convertString("BusinessNo", strBusinessNo, strBusinessNoSign));
			}
		}
		// 要保人ID 参与检索
		if (DataUtils.emptyToNull(strAppliIdentifyNumber) != null || DataUtils.emptyToNull(strAppliName) != null) {// 检索了要保人
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpcinsured where SwfLog.policyno = prpcinsured.policyno ");
			conditions.append(" and prpcinsured.insuredflag = '2' ");
			conditions.append(StringConvert.convertString("prpcinsured.identifynumber", strAppliIdentifyNumber, strAppliIdentifyNumberSign));
			conditions.append(StringConvert.convertString("prpcinsured.insuredName", strAppliName, strAppliNameSign));
			conditions.append(" ) ");
		}
		// 检索了被保险人、或其身份证字号、统一编号
		if (DataUtils.emptyToNull(strInsuredIdentifyNumber) != null) {
			conditions.append(" and exists (");
			conditions.append(" select 0 from prpcinsured where SwfLog.policyno = prpcinsured.policyno ");
			conditions.append(" and prpcinsured.insuredflag = '1' ");
			conditions.append(StringConvert.convertString("prpcinsured.identifynumber", strInsuredIdentifyNumber, strInsuredIdentifyNumberSign));
			conditions.append(" ) ");
		}
		// 車牌號碼
		if (DataUtils.emptyToNull(strLicenseNo) != null) {
			conditions.append(" and exists (");
			conditions.append(" select 0 from prplregist where SwfLog.policyno = prplregist.policyno ");
			conditions.append(StringConvert.convertString("prplregist.licenseNo", strLicenseNo, strLicenseNoSign));
			conditions.append(" ) ");
		}
		//事故日期参与查询
		if(DataUtils.emptyToNull(strDamageStartDate) != null
				|| DataUtils.emptyToNull(strDamageEndDate) != null){
			conditions.append(" and exists (");
			conditions.append(" select 0 from prplregist where SwfLog.registno = prplregist.registno ");
			conditions.append(StringConvert.convertDate("prplregist.damagestartdate", strDamageStartDate, ">="));
			conditions.append(StringConvert.convertDate("prplregist.damagestartdate", strDamageEndDate, "<="));
			conditions.append(" ) ");
		}
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(uiPowerInterface.addPower(user, "SwfLog", "", "ComCode"));
//		conditions.append(uiPowerInterface.addRiskPower(user, "SwfLog"));//险种权限限制
		conditions.append(powerService.addRiskPower(user, "SwfLog","claim"));//险种权限限制
		
		//排序
		conditions.append(this.getOrderSql(status, nodeType));
		return conditions.toString();
	}
	
	
	/***
	 * mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能
	 * FROM : 根据查询条件组织conditions
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	private String getDPworkFlowQueryConditions(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String nodeType = request.getParameter("nodeType");//节点名
		nodeType = StringUtils.rightTrim(nodeType).equals("")?"compp":nodeType;
		String status = request.getParameter("status");//状态
		status = StringUtils.rightTrim(status).equals("")?"4":status;
		String type = request.getParameter("type");//险类
		String funcName = request.getParameter("FuncName");
		//备案号码
		String strRegistNo = request.getParameter("RegistNo");
		String strRegistNoSign = request.getParameter("RegistNoSign");
		//保单号码
		String strPolicyNo = request.getParameter("PolicyNo");
		String strPolicyNoSign = request.getParameter("PolicyNoSign");
		//险种
		String strRiskCode = request.getParameter("RiskCode");
		String strRiskCodeNoSign = request.getParameter("RiskCodeNoSign");
		//开始时间
		String statStartDate = request.getParameter("statStartDate");
		String statEndDate = request.getParameter("statEndDate");
		//被保險人名稱
		String insuredName = request.getParameter("insuredName");
		String insuredNameSign = request.getParameter("insuredNameSign");
		//当前节点的业务号码可以是备案号、计算书号、调查号==
		String strBusinessNo = request.getParameter("BusinessNo");
		String strBusinessNoSign = request.getParameter("BusinessNoSign");
		//要保人ID
		String strAppliIdentifyNumber = request.getParameter("AppliIdentifyNumber");
		String strAppliIdentifyNumberSign = request.getParameter("AppliIdentifyNumberSign");
		//被保險人ID
		String strInsuredIdentifyNumber = request.getParameter("InsuredIdentifyNumber");
		String strInsuredIdentifyNumberSign = request.getParameter("InsuredIdentifyNumberSign");
		//車牌號碼
		String strLicenseNo = request.getParameter("LicenseNo");
		String strLicenseNoSign = request.getParameter("LicenseNoSign");
		//事故日期
		String strDamageStartDate = request.getParameter("damageStartDate");
		String strDamageEndDate = request.getParameter("damageEndDate");
		// 要保人名称
		String strAppliName = request.getParameter("AppliName");
		String strAppliNameSign = request.getParameter("AppliNameSign");
		
		StringBuffer conditions = new StringBuffer("");
		conditions.append(" nodeType = '"+nodeType+"' ");
		conditions.append(StringConvert.convertString("RegistNo", strRegistNo, strRegistNoSign));
		if (DataUtils.emptyToNull(strPolicyNo) != null) {
			//立案或者结案，或者是计算书处，关联和转换的吧？
			String temp = StringConvert.convertString("policyNo",strPolicyNo,strPolicyNoSign);
			if (!this.checkNeedFindRelatePolicy(nodeType)) {
				conditions.append(temp);
			} else {
				conditions.append(" and exists (select 0 from prplregistrpolicy p where SwfLog.registno = p.registno " + temp+ ") ");
			}
		}
		conditions.append(StringConvert.convertString("RiskCode", strRiskCode, strRiskCodeNoSign));
		conditions.append(StringConvert.convertString("insuredName", insuredName, insuredNameSign));
		if("-1".equals(status) && ("cancelApply".equals(funcName) || "specialApply".equals(funcName))){//注销拒赔
			conditions.append(" and NodeStatus <4 ");
		} else {
			conditions.append(" and ( NodeStatus='" + status + "' ");
			if("certi".equals(nodeType) && "0".equals(status)){
				conditions.append(" or NodeStatus='3' ");
			}
			conditions.append(" )");
		}
		
		conditions.append(this.getDateCondtions(status, statStartDate, statEndDate));
		// 将待处理和正在处理的情况中加入不显示已经关闭流程的节点信息
		if (!("cance".equals(nodeType) && "4".equals(status))) {//流程状态控制
			conditions.append(" and (flowStatus='1' or flowStatus='2') ");
		}
		
		String typeFlag = request.getParameter("typeFlag");
		if ("1".equals(typeFlag)) {
			conditions.append(" and typeFlag='1' ");
		}
		
		if(DataUtils.emptyToNull(strBusinessNo) != null ){
			if("check".equals(nodeType) && "acci".equals(type)){//调查号码
				conditions.append(StringConvert.convertString("keyIn", strBusinessNo, strBusinessNoSign));
			}else if ("claim".equals(nodeType)){
				conditions.append(StringConvert.convertString("keyOut", strBusinessNo, strBusinessNoSign));
			}else{
				conditions.append(StringConvert.convertString("BusinessNo", strBusinessNo, strBusinessNoSign));
			}
		}
		//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 START
//		conditions.append( " AND  CERTITYPE = '01' "); // -- 01賠付資訊   02費用支付對象
		String InputStatusCondition = request.getParameter("InputStatusCondition");
		String operatorCode = request.getParameter("operatorCode");//0:一般修改作業/1:審核作業
		if(null!=operatorCode && "undefined"!=operatorCode){//選項來自PayObjectInfoQueryEdit.jsp
			if("1".equals(operatorCode) || ("0".equals(operatorCode) && !StringUtils.rightTrim(InputStatusCondition).equals(""))){//1:審核作業
				
				if((InputStatusCondition == null || InputStatusCondition.trim().equals(""))){
					InputStatusCondition = "1";
				}
				conditions.append( 
						" AND BUSINESSNO IN (");
				conditions.append( 
//						" SELECT dplog.COMPENSATENO FROM PRPDAUTODPLOG dplog LEFT JOIN PRPLPAYOBJECTINFO payinfo "+
//								" ON dplog.COMPENSATENO = payinfo.COMPENSATENO "+
//								" AND dplog.SERIALNO = payinfo.SERIALNO "+
//								" AND dplog.CERTITYPE = payinfo.CERTITYPE "+
//								" WHERE dplog.INPUTSTATUS = "+InputStatusCondition +" )"
						" SELECT COMPENSATENO FROM ( "+
						"	SELECT PRPDAUTODPLOG.*,ROW_NUMBER() OVER (PARTITION BY COMPENSATENO ORDER BY INPUTDATE DESC) as rn FROM PRPDAUTODPLOG "+
						" ) WHERE rn = 1 AND INPUTSTATUS = "+InputStatusCondition +" )"
						);
			}else{
				//SwfLog 內的 計算書  共幾筆   賠付對象 
				conditions.append("AND ( "+
						 	" SELECT COUNT(poi.COMPENSATENO) from PRPLPAYOBJECTINFO poi  "+
							" WHERE 1=1  "+
						 	" and poi.COMPENSATENO = SwfLog.BUSINESSNO   "+
							" AND poi.CERTITYPE = '01'  "+//-- 01賠付資訊   02費用支付對象
						 	" GROUP BY poi.COMPENSATENO "+
							" ) > 0");	
			}
		}
		//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 END
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		conditions.append(uiPowerInterface.addPower(user, "SwfLog", "", "ComCode"));
//		conditions.append(uiPowerInterface.addRiskPower(user, "SwfLog"));//险种权限限制
//		conditions.append(powerService.addRiskPower(user, "SwfLog","claim"));//险种权限限制
		conditions.append(" AND RiskCode IN ('A01','B01','AB','AE','AR','AT','BB','BL','BN','BR','CB','CC','CN','CV','DI','DO','DS','EL','EM','ER','FC','FD','GC','GF','GS','LF','MD','MF','MI','MN','MP','PB','PC','PE','PM','PR','SB','SC','SP','ST','TC','TD','TE','TL','TP','GA','PA','TA','BP','CA','CE','CP','EA','EE','MB','F01','F02','AV','CF','CL','EV','EW','FL','FV','FW','MC','OH','V01') ");//僅車險
		
		//排序
		conditions.append(this.getOrderSql(status, nodeType));
		return conditions.toString();
	}
	/***
	 * 获取流程查询排序规则
	 * @param status
	 * @param nodeType
	 * @return
	 */
	private String getOrderSql(String status,String nodeType){
		StringBuffer conditions = new StringBuffer("");
		//排序
		if("-1".equals(status) || "0".equals(status)|| "3".equals(status)){
			conditions.append(" order by flowInTime");
			if("claim".equals(nodeType)){
				conditions.append(",TimeLimit");
			}
		}else if("2".equals(status)){
			conditions.append(" order by handleTime ");
		}else if("4".equals(status)){
			conditions.append(" order by submitTime desc ");
		}
		return conditions.toString();
	}
	
	/***
	 * 根据不同的状态取其时间查询的范围
	 * @param status
	 * @param statStartDate
	 * @param statEndDate
	 * @return
	 */
	private String getDateCondtions(String status,String statStartDate,String statEndDate){
		if(DataUtils.emptyToNull(statStartDate)!=null || DataUtils.emptyToNull(statEndDate)!=null){
			String temp = "2".equals(status)?"handleTime":("4".equals(status)?"submitTime":"flowInTime");
			StringBuffer sb = new StringBuffer("");
			if (DataUtils.emptyToNull(statStartDate)!=null) {
				sb.append(" and " + temp + " >='" + statStartDate+"'");
			}
			if (DataUtils.emptyToNull(statEndDate)!=null) {
				sb.append(" and " + temp + " <='" + statEndDate+"'");
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * 根据节点种类和操作状态工作流信息(翻页)
	 * @param httpServletRequest 返回给页面的request
	 * @param status 操作状态
	 * @param nodeType 节点种类
	 * @throws Exception
	 */
	public void getWorkFlowLogList(HttpServletRequest request, String nodeType, String status, String alertMessage, String pageNo, String recordPerPage) throws Exception {
		// 查询理赔节点状态信息
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		// 得到多行报案主表信息
		List<SwfLog> swfLogList = new ArrayList<SwfLog>();
		List<SwfLog> swfLogListTemp = new ArrayList<SwfLog>();
		if (DataUtils.emptyToNull(pageNo) == null) {
			pageNo = "1";
		}
		int intRecordPerPage = Integer.parseInt(recordPerPage);
		int intPageNo = Integer.parseInt(pageNo);
		// 判断节点，有的需要根据用户名称进行查询，有的不需要用户名字进行查询
		// 如果有condition参数就不要去生成SQL条件conditions，减少权限查询
		String conditions = null;
		String condition = request.getParameter("condition");
		String searchFlag = request.getParameter("searchFlag");
		if (DataUtils.emptyToNull(searchFlag) != null && !searchFlag.trim().equals("true")) {
			if (DataUtils.emptyToNull(condition) != null) {
				conditions = condition;
			}
		}
		if (conditions == null) {
			if (DataUtils.emptyToNull(nodeType) == null) {
				conditions = this.getNodeTaskListConditions(request, status, user.getUserCode());
			} else {
				conditions = this.getNodeTaskListConditionsByNodeType(request, nodeType, status, user.getUserCode());
			}
		}
		Page page = null;
		if ("cance".equals(nodeType.trim()) && "4".equals(status.trim())) {
			page = this.getWorkFlowService().findViewSwfLogAll(conditions, intPageNo, intRecordPerPage);
		} else {
			page = this.getWorkFlowService().findNodesByConditions(conditions, intPageNo, intRecordPerPage);
		}
		List<?> pageResult = page.getResult();
		String dfFlag = "N";
		for (Iterator<?> it = pageResult.iterator(); it.hasNext();) {
			SwfLog swflogtemp = (SwfLog) it.next();
			swflogtemp.setClassCode(this.getCodeService().translateClassCodeByRiskCode(swflogtemp.getRiskCode()));
			swflogtemp.setRiskType(this.getCodeService().translateRiskCodetoRiskType(swflogtemp.getRiskCode()));
			Page tempPage = this.getWorkFlowService().findNodesByConditions("  flowid='" + swflogtemp.getId().getFlowID() + "'", intPageNo, intRecordPerPage);
			List<?> tempSwfLoglist = tempPage.getResult();
			for (Iterator<?> iter = tempSwfLoglist.iterator(); iter.hasNext();) {
				SwfLog element = (SwfLog) iter.next();
				if ("8".equals(element.getTypeFlag())) { // 表示该案件申请了垫付
					dfFlag = "Y";
					swflogtemp.setDfFlag(dfFlag);
					break;
				}
			}
			swfLogList.add(swflogtemp);
		}
		String compFlag = "";
		// 如果nodeType=定损/核损的话，还需要转换定损/核损的类型名称
		if (nodeType != null) {
			if ((nodeType.equals("certa") || nodeType.equals("verif")||nodeType.equals("wound") || nodeType.equals("veriw")||nodeType.equals("propv") || nodeType.equals("propc") || nodeType.equals("sched")) && !status.equals("-1")) {
				if (swfLogList != null && swfLogList.size() > 0) {
					for (int i = 0; i < swfLogList.size(); i++) {
						SwfLog swfLogTemp = swfLogList.get(i);
						if (nodeType.equals("sched")) {
							// 加入等候时间
							if (swfLogTemp.getNodeStatus().equals("0"))
								swfLogTemp.setStopTimeDesc(DateCompute.betweenDate(swfLogTemp.getFlowInTime(), new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString(),DateTime.DAY_TO_MINUTE));
							if (swfLogTemp.getNodeStatus().equals("2"))
								swfLogTemp.setStopTimeDesc(DateCompute.betweenDate(swfLogTemp.getHandleTime(), new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString(),DateTime.DAY_TO_MINUTE));
							int index = swfLogTemp.getStopTimeDesc().indexOf("钟");
							if (index != -1) {
								swfLogTemp.setStopTimeDesc(swfLogTemp.getStopTimeDesc().substring(0, index));
							}
							String swfLogTemp1 = swfLogTemp.getStopTimeDesc().replace("时", "時");
							swfLogTemp.setStopTimeDesc(swfLogTemp1);
						} else {
							if ("1".equals(swfLogTemp.getTypeFlag())) {
								swfLogTemp.setTypeFlagName("1-修理換件");
							} else if ("2".equals(swfLogTemp.getTypeFlag())) {
								swfLogTemp.setTypeFlagName("2-人員定損");
							} else if ("3".equals(swfLogTemp.getTypeFlag())) {
								swfLogTemp.setTypeFlagName("3-財產定損");
							} else if ("4".equals(swfLogTemp.getTypeFlag())) {
								swfLogTemp.setTypeFlagName("4-全損/推定全損");
							} else {
								swfLogTemp.setTypeFlagName("");
							}
						}
						swfLogListTemp.add(swfLogTemp);
					}
					swfLogList = swfLogListTemp;
				}
			} else if (nodeType.equals("compe") && status.equals("0")) { // 用於处理计算书:如果还有未审核通过的计算书，就不得再生成计算书。
				if (swfLogList != null && swfLogList.size() > 0) {
					for (int i = 0; i < swfLogList.size(); i++) {
						SwfLog swfLogTemp = swfLogList.get(i);
						compFlag = this.getCompensateService().getCompFlagByConditions(swfLogTemp.getKeyIn());
						String[] flags = compFlag.split("-");
						swfLogTemp.setCompeFlag(flags[0]);
						swfLogTemp.setCompeCount(Integer.parseInt(flags[1]));
						if ("0".equals(flags[2])) {
							swfLogListTemp.add(swfLogTemp);
						}
					}
					swfLogList = swfLogListTemp;
				}
			} else if (nodeType.equals("compe") && status.equals("2")) { // 用於处理计算书:如果还有未审核通过的计算书，就不得再生成计算书。
				if (swfLogList != null && swfLogList.size() > 0) {
					for (int i = 0; i < swfLogList.size(); i++) {
						SwfLog swfLogTemp = swfLogList.get(i);
						compFlag = this.getCompensateService().getCompFlagByConditions(swfLogTemp.getKeyIn());
						String[] flags = compFlag.split("-");
						swfLogTemp.setCompeFlag(flags[0]);
						swfLogTemp.setCompeCount(Integer.parseInt(flags[1]));
						if ("0".equals(flags[2])) {
							swfLogListTemp.add(swfLogTemp);
						}
					}
					swfLogList = swfLogListTemp;
				}
			} else if (request.getParameter("FuncName").equals("cancelApply") && status.equals("-1")) { // 申请注销拒赔 时进入 ，查询哪些可以申请
				if (swfLogList != null && swfLogList.size() > 0) {
					if(swfLogList.size() > 1){//输入备案号查询时，只有关联单的立案环节会大于1
						for (int i = 0; i < swfLogList.size(); i++) {
							SwfLog swfLogTemp = swfLogList.get(i);
							String statement = "UnderWriteFlag  in ('0','1','3','9') ";
							boolean isReject = this.getCompensateService().isRejectByConditions(swfLogList.get(i).getKeyOut(), statement);
							compFlag = this.getCompensateService().getCompFlagByConditions(swfLogTemp.getKeyIn());
							if (isReject) {
								String[] flags = compFlag.split("-");
								swfLogTemp.setCompeFlag(flags[0]);
								swfLogTemp.setCompeCount(Integer.parseInt(flags[1]));
								swfLogListTemp.add(swfLogTemp);
							}
						}
					} else {
						for (int i = 0; i < swfLogList.size(); i++) {
							SwfLog swfLogTemp = swfLogList.get(i);
							String claimNo = swfLogTemp.getKeyIn();
							String statement = "";
							boolean isReject = false;
							if ("6".equals(swfLogTemp.getKeyIn().substring(0, 1))) {
								List<PrpLclaim> prpLclaimList = this.getPrpLclaimService().findByRegistNo(swfLogTemp.getKeyIn());
								if (prpLclaimList != null && !prpLclaimList.isEmpty()) {
									for (int j = 0; j < prpLclaimList.size(); j++) {
										claimNo = prpLclaimList.get(j).getClaimNo();
										statement = "claimno = '" + claimNo + "' and ";
										statement += "UnderWriteFlag  in ('0','1','3','9') ";
										isReject = this.getCompensateService().isRejectByConditions(claimNo, statement);
										if (isReject) {
											break;
										}
									}
								} else {
									statement += "UnderWriteFlag  in ('0','1','3','9') ";
									isReject = this.getCompensateService().isRejectByConditions(claimNo, statement);
								}
							} else {
								statement += "UnderWriteFlag  in ('0','1','3','9') ";
								isReject = this.getCompensateService().isRejectByConditions(claimNo, statement);
							}
							compFlag = this.getCompensateService().getCompFlagByConditions(swfLogTemp.getKeyIn());
							if (isReject) {
								String[] flags = compFlag.split("-");
								swfLogTemp.setCompeFlag(flags[0]);
								swfLogTemp.setCompeCount(Integer.parseInt(flags[1]));
								swfLogListTemp.add(swfLogTemp);
							}
						}
					}
					swfLogList = swfLogListTemp;
				}
			} else if (nodeType.equals("compp") && status.equals("3")) { // 申请注销拒赔
				if (swfLogList != null && swfLogList.size() > 0) {
					for (int i = 0; i < swfLogList.size(); i++) {
						SwfLog swfLogTemp = swfLogList.get(i);
						compFlag = this.getCompensateService().getCompFlagByConditions(swfLogTemp.getKeyIn());
						String[] flags = compFlag.split("-");
						if (flags[2].equals("0")) {
							swfLogTemp.setCompeFlag(flags[0]);
							swfLogTemp.setCompeCount(0);
							swfLogListTemp.add(swfLogTemp);
						}
					}
					swfLogList = swfLogListTemp;
				}
			} else if (nodeType.equals("claim") && status.equals("0")) { // 用於处理注销保单：注销的保单不得立案
				if (swfLogList != null && swfLogList.size() > 0) {
					for (int i = 0; i < swfLogList.size(); i++) {
						SwfLog swfLogTemp = swfLogList.get(i);
						PrpCmain prpCmaintemp = this.getPolicyService().findPrpCmainDtoByPrimaryKey(swfLogTemp.getPolicyNo());
						if (prpCmaintemp != null) {
							swfLogTemp.setOtherFlag(prpCmaintemp.getOthFlag());
						}
						swfLogListTemp.add(swfLogTemp);
					}
					swfLogList = swfLogListTemp;
				}
			}
		} else {
			if (swfLogList != null && swfLogList.size() > 0) {
				for (int i = 0; i < swfLogList.size(); i++) {
					SwfLog swfLogTemp = swfLogList.get(i);
					swfLogTemp.setNodeTypeName(this.getCodeService().translateCodeCode("ClaimNodeType", swfLogTemp.getNodeType(), true));
					swfLogListTemp.add(swfLogTemp);
				}
				swfLogList = swfLogListTemp;
			}
		}
		SwfLog swfLog = new SwfLog();
		// 转换保单号码用的
		swfLogList = this.translateRelatePolicyOnTaskQuery(swfLogList, nodeType);
		swfLog.setSwfLogList(swfLogList);
		swfLog.setAlertMessage(alertMessage);
		if (DataUtils.emptyToNull(nodeType) == null) {
			swfLog.setNodeType("commo");
		} else {
			swfLog.setNodeType(nodeType);
		}
		if (swfLogList == null || swfLogList.isEmpty()) {
			page = new Page(1, 0, intRecordPerPage, swfLogList);
		}
		request.setAttribute("page", page);
		request.setAttribute("swfLog", swfLog);
		// 防止再次刷新的时候会失去值
		request.setAttribute("status", status);
		request.setAttribute("nodeType", nodeType);
		// 原因：把险种类型信息放入Request中，用於区别意健险和其他险种。
		request.setAttribute("com_sinosoft_type", request.getParameter("type"));
	}

	/**
	 * 检查需要进行关联多个保单的
	 * @param nodeType 节点类型
	 * @throws Exception
	 * @return boolean
	 */
	private List<SwfLog> translateRelatePolicyOnTaskQuery(List<SwfLog> swfLogList, String nodeType) throws Exception {
		List<SwfLog> swfLogListTemp = new ArrayList<SwfLog>();
		for (SwfLog swfLogTemp : swfLogList) {
			swfLogListTemp.add(this.translateRelatePolicyOnTaskQueryOne(swfLogTemp));
		}
		return swfLogListTemp;
	}

	/**
	 * 获得任务显示列表的条件（不区分节点的所有处理的任务）
	 * @param nodeType String
	 * @param status String
	 * @param handlerCode String
	 * @throws Exception
	 * @return String
	 */
	private String getNodeTaskListConditions(HttpServletRequest httpServletRequest, String status, String handlerCode) throws Exception {
		StringBuffer condition = new StringBuffer("");
		// 注销条件
		if (status.equals("-1")) {
			condition.append("  Nodestatus < 4 and handlerCode='" + handlerCode + "'");
			// 新任务条件
		} else if (status.equals("0")) {
			// 状态等於0，分配的为指定的人和公共部分的，没有被指定用户的数据
			condition.append(" ((NodeStatus='").append(status).append("' and (handlerCode='").append(handlerCode).append("' or handlerCode ='").append(SwfLog.HANDLERCODE_NONE).append("'))  or ( NodeStatus='3' and handlerCode='").append(handlerCode)
					.append("')) ");
		} else {
			condition.append(" NodeStatus='").append(status).append("' and handlerCode='").append(handlerCode).append("'");
		}
		// 将待处理和正在处理的情况中加入不显示已经关闭流程的节点信息
		condition.append(" and (flowStatus='1' or flowStatus='2')");
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		condition.append(uiPowerInterface.addPower(userDto, "swflog", "", "ComCode"));
		condition.append(" order by nodeType,handleTime desc");
		return condition.toString();
	}

	/**
	 * 获得任务显示列表的条件,一般情况(待处理任务，正在处理任务，已提交任务)
	 * @param nodeType String
	 * @param status String
	 * @param handlerCode String
	 * @throws Exception
	 * @return String
	 */
	private String getNodeTaskListConditionsByNodeType(HttpServletRequest httpServletRequest, String nodeType, String status, String handlerCode) throws Exception {
		String condition = "";
		String orderString = " order by handleTime desc";
		String riskType = httpServletRequest.getParameter("type");
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String conSignType = httpServletRequest.getParameter("conSignType");
		String funcName = httpServletRequest.getParameter("FuncName");
		if ("verip".equals(nodeType)) {
			String comLevel = user.getComLevel();
			if ("1".equals(comLevel)) {
				nodeType = "verpo";
			}
		}
		// 如果查询对外核价任务则修改节点类型
		if (DataUtils.emptyToNull(conSignType) != null && "verpo".equals(conSignType)) {
			nodeType = conSignType;
			condition = " handleDept = '" + user.getComCode() + "' ";
		}
		// 开始拼条件。。。。
		condition = "";
		if (DataUtils.emptyToNull(handlerCode) == null) {
			condition = " nodeType='" + nodeType + "' and NodeStatus='" + status + "'";
		} else {
			condition = " (nodeType='" + nodeType + "' and NodeStatus='" + status + "') and (handlerCode='" + handlerCode + "' or handlerCode is null or handlerCode='' or handlerCode ='0')";
		}
		// 注销条件
		// 非车只能在报案环节注销
		if ("-1".equals(status)) {
			if (DataUtils.emptyToNull(funcName) != null && funcName.equals("specialApply")) {
				condition = " nodeType='" + nodeType + "' and ( handlerCode='" + handlerCode + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "') " + "and ((Nodestatus <4 and (riskCode like '" + ConstantCodes.CLASSCODE_D_A
						+ "%' or riskCode like '" + ConstantCodes.CLASSCODE_D_B + "%')) or (riskCode not like '" + ConstantCodes.CLASSCODE_D_A + "%' or riskCode not like '" + ConstantCodes.CLASSCODE_D_B + "%'))";
			} else {
				condition = " nodeType='" + nodeType + "' and ( handlerCode='" + handlerCode + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "') " + "and ((Nodestatus <4 ))";// claimkind，暂时去掉此条件：and
				// riskCode
				// like
				// '05%'))";
			}
		}

		// 新任务条件
		if (status.equals("0")) {
			// 定损退回的任务单独分出来，不放在待处理中
			if (nodeType.equals("certa") || nodeType.equals("wound") || nodeType.equals("propc")) { //
				condition = " nodeType='" + nodeType + "' and (NodeStatus='" + status + "') and (handlerCode='" + handlerCode + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "')";
			} else if (nodeType.equals("compe")) {
				condition = " nodeType='" + nodeType + "' and (NodeStatus='" + status + "' or NodeStatus='3' ) and (handlerCode='" + handlerCode + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "')";
				condition = condition
						+ " and not exists (select 1 from swflog a where a.nodeType = 'compp' and a.NodeStatus in ( '2','3') and (a.flowStatus = '1' or a.flowStatus = '2') and SwfLog.registno = a.registno  and SwfLog.keyin=a.keyin) and not exists (select 1 from swflog a where a.nodeType = 'veric' and a.NodeStatus in ('0','1','2') and (a.flowStatus = '1' or a.flowStatus = '2') and SwfLog.registno = a.registno  and SwfLog.keyin=a.keyin)";
			} else {
				// 状态等於0，分配的为指定的人和公共部分的，没有被指定用户的数据
				condition = " nodeType='" + nodeType + "' and (NodeStatus='" + status + "' or NodeStatus='3' ) and (handlerCode='" + handlerCode + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "')";
			}
			// 定损调度太特殊了
			if (nodeType.equals("sched")) {
				condition = " (nodeType='" + nodeType + "' ) and (NodeStatus='" + status + "') and (handlerCode='" + handlerCode + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "')";
			}
		}
		// 将待处理和正在处理的情况中加入不显示已经关闭流程的节点信息
		if ("cance".equals(nodeType) && "4".equals(status)) {
			// condition = condition + " and flowStatus='0'";为了在已注销拒赔中能查询到
		} else {
			condition = condition + " and (flowStatus='1' or flowStatus='2')";
		}
		if (nodeType.equals("verpo")) {
			condition = condition + " and   handleDept like  '" + user.getComCode().substring(0, 3) + "%' ";
		}

		if (nodeType.equals("claim") && status.equals("0")) {
			// 立案排序显示
			orderString = " order by flowintime,TimeLimit";
		}

		if (nodeType.equals("sched") && status.equals("0")) {
			orderString = " order by flowintime";
		}
		// 新任务条件
		if (status.equals("99")) {// 9是查询所有状态的。。
			condition = " nodeType='" + nodeType + "'";
		}
		if (nodeType.equals("veric") && status.equals("0")) {
			condition = " nodeType='" + nodeType + "' and nodestatus <4";
		}

		// 以下是特殊的
		// [意健险调查]***************************************************************************
		// 原因：加入一个查寻条件，区别意键险和其他险种
		if ("check".equals(nodeType)) {
			if ("acci".equals(riskType)) {
				// 这个IF中的查询条件用於查询意健险的信息,在每个查询语句中加入查询条件
				// + "and riskCode like '07%' or riskCode like '06%'"。
				if (handlerCode.equals("")) {
					condition = " nodeType='" + nodeType + "' and NodeStatus='" + status + "'" + " and (riskcode in ('PA','GA','HG','TA','TE','TR','PL'))";
				} else {
					condition = " (nodeType='" + nodeType + "' and NodeStatus='" + status + "' and handlerCode='" + handlerCode + "')" + " and (riskcode in ('PA','GA','HG','TA','TE','TR','PL'))";
				}
				// 注销条件
				if (status.equals("-1")) {
					condition = " nodeType='" + nodeType + "' and handlerCode='" + handlerCode + "' and Nodestatus <4 " + "and (riskcode in ('PA','GA','HG','TA','TE','TR','PL'))";
				}
				// 新任务条件
				if (status.equals("0")) {
					condition = " nodeType='" + nodeType + "' and (NodeStatus='" + status + "' or NodeStatus='3' ) and (handlerCode='" + handlerCode + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "')" + " and (riskcode in ('PA','GA','HG','TA','TE','TR','PL'))";
				}
				condition = condition + " and (flowStatus='1' or flowStatus='2')";
				// 新任务条件
				if (status.equals("99")) {// 9是查询所有状态的。。
					condition = " nodeType='" + nodeType + "'";
				}
				// 返回意健康险的查勘的查询条件
			} else {
				condition += " and (riskcode not in ('PA','GA','HG','TA','TE','TR','PL')) ";
			}
		}

		// 重新开始[意健险审核、计算书]
		if ("acci".equals(riskType) && (nodeType.equals("compe") || nodeType.equals("compp"))) {
			// 这个IF中的查询条件用於查询意健险的信息,在每个查询语句中加入查询条件
			// + "and riskCode like '07%' or riskCode like '06%'"。
			if (handlerCode.equals("")) {
				condition = " nodeType='" + nodeType + "' and NodeStatus='" + status + "'" + " and (riskcode in ('PA','GA','HG','TA','TE','TR','PL'))";
			} else {
				condition = " (nodeType='" + nodeType + "' and NodeStatus='" + status + "' and handlerCode='" + handlerCode + "')" + " and (riskcode in ('PA','GA','HG','TA','TE','TR','PL'))";
			}
			// 注销条件
			if (status.equals("-1")) {
				condition = " nodeType='" + nodeType + "' and handlerCode='" + handlerCode + "' and Nodestatus <4 " + "and (riskcode in ('PA','GA','HG','TA','TE','TR','PL'))";
			}
			// 新任务条件
			if (status.equals("0")) {
				// 状态等於0，分配的为指定的人和公共部分的，没有被指定用户的数据
				condition = " nodeType='" + nodeType + "' and (NodeStatus='" + status + "' or NodeStatus='3' ) and (handlerCode='" + handlerCode + "' or handlerCode ='" + SwfLog.HANDLERCODE_NONE + "')" + " and (riskcode in ('PA','GA','HG','TA','TE','TR','PL'))";
			}
			condition = condition + " and (flowStatus='1' or flowStatus='2')";
			// 新任务条件
			if (status.equals("99")) {// 9是查询所有状态的。。
				condition = " nodeType='" + nodeType + "'";
			}
			// 返回意健康险的理算的查询条件
		}

		// 从待处理界面获取待处理过滤的条件，从界面上得业务号码的keyIn,keyOut还是businessNO上进行查询
		String claimNo = httpServletRequest.getParameter("BusinessNo");
		if (claimNo != null && claimNo.length() > 1 && "cance".equals(nodeType)) {
			condition = condition + "and registNo in (select registNo from prplclaim where claimNo = '" + claimNo.trim() + "')";
		}
		// 支持多保单的查询
		String policyNo = httpServletRequest.getParameter("PolicyNo");
		if (policyNo != null && policyNo.length() > 1) {
			// 考虑一下，如果是立案或者结案，或者是计算书处，是如何处理的呢？应该是不用这样关联和转换的吧？
			if (!this.checkNeedFindRelatePolicy(nodeType)) {
				condition = condition + StringConvert.convertString("policyNo", httpServletRequest.getParameter("PolicyNo"), httpServletRequest.getParameter("PolicyNoSign"));
			} else {
				condition = condition + " and registno in (select registno from " + "prplregistrpolicy where 1=1 " + StringConvert.convertString(" policyNo", policyNo, httpServletRequest.getParameter("PolicyNoSign")) + ") ";
			}
		}

		condition = condition + StringConvert.convertString("RiskCode", httpServletRequest.getParameter("RiskCode"), httpServletRequest.getParameter("RiskCodeNoSign"));
		condition = condition + StringConvert.convertString("insuredName", httpServletRequest.getParameter("insuredName"), httpServletRequest.getParameter("insuredNameSign"));

		// 正在处理立案任务 查询条件需报案号
		if (nodeType.equals("claim") && !status.equals("0")) {
			condition = condition + " And exists (select * from prplclaim a where swflog.keyout=a.claimno "
					+ StringConvert.convertString("a.registno", httpServletRequest.getParameter("ClaimRegistNo"), httpServletRequest.getParameter("ClaimRegistNoSign"));
			condition = condition + ")";
		}
		if (nodeType.equals("compp") && !status.equals("1")) {
			condition = condition + StringConvert.convertString("registno", httpServletRequest.getParameter("ComppRegistNo"), httpServletRequest.getParameter("ComppRegistNoSign"));
		}

		String deptname = httpServletRequest.getParameter("DeptName");
		if (DataUtils.emptyToNull(deptname) != null) {
			condition = condition + StringConvert.convertString("DeptName", deptname, httpServletRequest.getParameter("DeptNameSign"));
		}
		String operateDate = httpServletRequest.getParameter("OperateDate");
		if (DataUtils.emptyToNull(operateDate) != null) {
			condition = condition + StringConvert.convertDate("FlowInTime", operateDate, httpServletRequest.getParameter("OperateDateSign"));
		}
		String statEndDate = httpServletRequest.getParameter("statEndDate");
		String statStartDate = httpServletRequest.getParameter("statStartDate");
		String damageStartDate = httpServletRequest.getParameter("damageStartDate");
		String damageEndDate = httpServletRequest.getParameter("damageEndDate");
		if (DataUtils.emptyToNull(statEndDate) != null) {
			condition = condition + " AND HandleTime <='" + statEndDate + "'";
		}
		if (DataUtils.emptyToNull(statStartDate) != null) {
			condition = condition + " AND HandleTime >='" + statStartDate + "'";
		}
		if(DataUtils.emptyToNull(damageEndDate) != null || DataUtils.emptyToNull(damageStartDate) != null){
			condition = condition + " AND keyIn in (select registNo from prpLregist where 1=1 ";
			if(DataUtils.emptyToNull(damageEndDate) != null){
				condition = condition + StringConvert.convertDate("damageStartDate", damageEndDate, "<=");
			}
			if(DataUtils.emptyToNull(damageStartDate) != null){
				condition = condition + StringConvert.convertDate("damageStartDate", damageStartDate, ">=");
			}
			condition = condition + ")";
		}
		// 报案号
		String registNo = httpServletRequest.getParameter("RegistNo");
		if (DataUtils.emptyToNull(registNo) != null) {
			condition = condition + StringConvert.convertString("RegistNo", registNo, httpServletRequest.getParameter("RegistNoSign"));
		}

		// 损失标的/车牌
		String lossitemName = httpServletRequest.getParameter("LicenseNo");
		if (DataUtils.emptyToNull(lossitemName) != null) {
			condition = condition + StringConvert.convertString("LossItemName", lossitemName, httpServletRequest.getParameter("LicenseNoSign"));
		}
		String InsuredName = httpServletRequest.getParameter("InsuredName");
		if (DataUtils.emptyToNull(InsuredName) != null) {
			// 被保人名称
			condition = condition + StringConvert.convertString("InsuredName", InsuredName, httpServletRequest.getParameter("InsuredNameSign"));
		}

		String conditionscompe = "";
		String endcaRegistNo = httpServletRequest.getParameter("EndcaRegistNo");
		String compeRegistNo = httpServletRequest.getParameter("CompeRegistNo");
		if (DataUtils.emptyToNull(endcaRegistNo) != null) {
			conditionscompe = endcaRegistNo;
		}
		if (DataUtils.emptyToNull(compeRegistNo) != null) {
			conditionscompe = compeRegistNo;
		}
		if (DataUtils.emptyToNull(conditionscompe) != null) {
			condition = condition + StringConvert.convertString("RegistNo", conditionscompe, "=");
		}

		String typeFlag = httpServletRequest.getParameter("typeFlag");
		if ("1".equals(typeFlag)) {
			condition = condition + " AND typeFlag='1' ";
		}

		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		if (DataUtils.emptyToNull(riskType) != null && (nodeType.equals("compe") || nodeType.equals("compp")) && riskType.equals("acci")) {
			condition = condition + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		} else if (DataUtils.emptyToNull(riskType) != null && nodeType.equals("check") && riskType.equals("acci")) {
			condition = condition + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		} else if (nodeType.equals("verpo")) {
			// 对外核价不需要
		} else if ("cance".equals(nodeType.trim()) && "4".equals(status.trim())) {
			condition = condition + uiPowerInterface.addPower(userDto, "view_swflogall", "", "ComCode");
		} else {
			condition = condition + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		}
		condition = condition + orderString;
		return condition;
	}

	/**
	 * 检查需要进行关联多个保单的
	 * @param nodeType 节点类型
	 * @throws Exception
	 * @return boolean
	 */
	private boolean checkNeedFindRelatePolicy(String nodeType) throws Exception {
		boolean blresut = true;
		// 立案，计算书，结案是不需要进行关联多个保单的
		if ("claim".equals(nodeType) || "compp".equals(nodeType) || "endca".equals(nodeType)) {
			blresut = false;
		}
		return blresut;
	}

	/**
	 * 理算环节紧急案件清单查询(翻页)
	 * @param request 返回给页面的request
	 * @throws Exception
	 */
	public void getUrgentCaseList(HttpServletRequest request, String pageNo, String recordPerPage) throws Exception {
		// 查询理赔节点状态信息
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		// 得到多行报案主表信息
		if (DataUtils.emptyToNull(pageNo) == null) {
			pageNo = "1";
		}
		int intRecordPerPage = Integer.parseInt(recordPerPage);
		int intPageNo = Integer.parseInt(pageNo);
		String conditions = null;
		String condition = request.getParameter("condition");
		String searchFlag = request.getParameter("searchFlag");
		if (DataUtils.emptyToNull(searchFlag) != null && !searchFlag.trim().equals("true")) {
			if (DataUtils.emptyToNull(condition) != null) {
				conditions = condition;
			}
		}
		if (conditions == null) {
			condition = " (swflog.nodetype = 'compe' or swflog.nodetype = 'compp') and  swflog.NodeStatus < 4 and" + " (swflog.handlerCode='" + user.getUserCode()
					+ "' or swflog.handlerCode ='' or swflog.handlerCode is null or swflog.handlerCode = '0')" + " and (swflog.Flowstatus = '1' Or swflog.Flowstatus = '2')";
			UIPowerInterface uiPowerInterface = new UIPowerInterface();
			UserDto userDto = (UserDto) request.getSession().getAttribute("user");
			condition = condition + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
			conditions = condition;
		}
		Page page = this.getWorkFlowService().getUrgentCaseList(conditions, intPageNo, intRecordPerPage);
		List<?> swfLoglist = page.getResult();
		if (swfLoglist != null && swfLoglist.size() > 0) {
			SwfLog swfLog = null;
			for (int i = 0; i < swfLoglist.size(); i++) {// 根据紧急程度，设置显示颜色
				swfLog = (SwfLog) swfLoglist.get(i);
				swfLog.setHandlerName(DataUtils.dbNullToEmpty(swfLog.getHandlerName()));
				if (swfLog.getTimeLimit() >= 25) {
					swfLog.setFlag("red");
				} else if (swfLog.getTimeLimit() >= 15 && swfLog.getTimeLimit() < 25) {
					swfLog.setFlag("yellow");
				} else {
					swfLog.setFlag("#F7F7F7");
				}
			}
		}
		request.setAttribute("page", page);
		request.setAttribute("swfLoglist", swfLoglist);
		request.setAttribute("flag", "compensate");
	}

	/**
	 * 导出理算紧急案件清单至Excel
	 * @param httpServletRequest 返回给页面的request
	 * @throws Exception
	 */
	public void exportToExcel(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		// 查询理赔节点状态信息
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String exportType = httpServletRequest.getParameter("exportType");
		// 得到多行报案主表信息
		String conditions = "";
		String condition = " (swflog.nodetype = 'compe' or swflog.nodetype = 'compp') and  swflog.NodeStatus < 4 and" + " (swflog.handlerCode='" + user.getUserCode()
				+ "' or swflog.handlerCode ='' or swflog.handlerCode is null or swflog.handlerCode = '0')" + " and (swflog.Flowstatus = '1' Or swflog.Flowstatus = '2')";
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) httpServletRequest.getSession().getAttribute("user");
		condition = condition + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		conditions = condition;
		Page page = this.getWorkFlowService().getUrgentCaseList(conditions, 1, 1000);
		// 组织数据
		// 定义列名信息
		List<MidResultConfigDto> arrConfigs = new ArrayList<MidResultConfigDto>();
		String[] columnS = { "registNo", "policyNo", "insuredName", "handlerName", "comCode", "flowInTime", "nodeStatus", "timeLimit" };
		String[] columnNameS = { "備案號碼", "保單號碼", "被保險人", "操作人員", "承保機構代碼", "流入時間", "案件狀態", "等待時間（天）" };
		for (int i = 0; i < columnNameS.length; i++) {
			MidResultConfigDto midResultConfigDto = new MidResultConfigDto();
			midResultConfigDto.setItemColumn(columnS[i]);
			midResultConfigDto.setItemColumnName(columnNameS[i]);
			midResultConfigDto.setDataType("String");
			arrConfigs.add(midResultConfigDto);
		}
		List<?> swfLoglist = page.getResult();
		List<Map<String, String>> swfLoglistRed = new ArrayList<Map<String, String>>();
		List<Map<String, String>> swfLoglistYellow = new ArrayList<Map<String, String>>();
		List<Map<String, String>> swfLoglistAll = new ArrayList<Map<String, String>>();
		if (swfLoglist != null && swfLoglist.size() > 0) {
			SwfLog swfLog = null;
			for (int i = 0; i < swfLoglist.size(); i++) {// 根据紧急程度，拆分为三个List
				swfLog = (SwfLog) swfLoglist.get(i);
				Map<String, String> swfLogHashMap = new HashMap<String, String>();
				swfLogHashMap.put("registNo", swfLog.getRegistNo());
				swfLogHashMap.put("policyNo", swfLog.getPolicyNo());
				swfLogHashMap.put("insuredName", swfLog.getInsuredName());
				swfLogHashMap.put("handlerName", swfLog.getHandlerName());
				swfLogHashMap.put("comCode", swfLog.getComCode());
				swfLogHashMap.put("flowInTime", swfLog.getFlowInTime());
				swfLogHashMap.put("nodeStatus", swfLog.getNodeStatus());
				swfLogHashMap.put("timeLimit", "" + swfLog.getTimeLimit());
				swfLoglistAll.add(swfLogHashMap);
				if (swfLog.getTimeLimit() >= 25) {
					swfLoglistRed.add(swfLogHashMap);
				} else if (swfLog.getTimeLimit() >= 15 && swfLog.getTimeLimit() < 25) {
					swfLoglistYellow.add(swfLogHashMap);
				}
			}
		} else {
			throw new UserException(0, 1, "理算導出數據", "沒有符合要求的數據，請重新選擇導出類型！");
		}
		if (exportType != null) {
			if ("1".equals(exportType)) {// 红色预警
				this.exportExcel(swfLoglistRed, arrConfigs, httpServletResponse);
			} else if ("2".equals(exportType)) {// 黄色预警
				this.exportExcel(swfLoglistYellow, arrConfigs, httpServletResponse);
			} else {// 全部导出
				this.exportExcel(swfLoglistAll, arrConfigs, httpServletResponse);
			}
			// 提交成功後显示信息
			user.setUserMessage("導出Excel文件成功！");
			httpServletRequest.setAttribute("user", user);
		} else {
			throw new UserException(0, 1, "理算導出數據", "導出Excel發生異常，請聯系管理員！");
		}
	}

	/**
	 * 保存EXCEL
	 * @Parameter ArrayList
	 *            arrHashResult（arrHashResult是hashResult的集合，hashResult：Key
	 *            MidResultConfig.ItemColumn，Value 对应表的Dto对应字段的值）
	 * @Parameter ArrayList arrConfigs（页面配置项集合）
	 * @Parameter HttpServletResponse httpServletResponse
	 */
	public void exportExcel(List<Map<String, String>> arrHashResult, List<MidResultConfigDto> arrConfigs, HttpServletResponse response) throws Exception {
		MidResultConfigDto midResultConfigDto = null;
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); // 建立新的HSSFWorkbook对象
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("sheet1"); // 建立新的HSSFSheet对象
		hssfSheet.setDefaultColumnWidth(20);// 指定默认列宽
		hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, arrConfigs.size() - 1));// 指定合並区域,前二个参数为开始处X,Y坐标.後二个为结束的坐标.
		HSSFRow hssfRow = null;
		HSSFCell hssfCell = null;
		Map<String, String> hashResult = null;

		String strItemColumn = "";
		String strDataType = "";

		int i = 0;
		int j = 0;

		response.setContentType("application/ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String("緊急案件清單.xls".getBytes(), "iso-8859-1"));
		// 表名格式
		HSSFFont tableName_font = hssfWorkbook.createFont();
		tableName_font.setFontName(HSSFFont.FONT_ARIAL);
		tableName_font.setFontHeightInPoints((short) 15);

		HSSFCellStyle tableName_cellNumStyle = hssfWorkbook.createCellStyle();
		tableName_cellNumStyle.setFont(tableName_font);
		tableName_cellNumStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		tableName_cellNumStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		tableName_cellNumStyle.setWrapText(true);// 文本区域随内容多少自动调整
		// 列名格式
		HSSFFont title_font = hssfWorkbook.createFont();
		title_font.setFontName(HSSFFont.FONT_ARIAL);
		title_font.setFontHeightInPoints((short) 8);

		HSSFCellStyle title_cellNumStyle = hssfWorkbook.createCellStyle();
		title_cellNumStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		title_cellNumStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		title_cellNumStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
		title_cellNumStyle.setFont(title_font);
		title_cellNumStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		title_cellNumStyle.setWrapText(true);// 文本区域随内容多少自动调整

		HSSFCellStyle cellNumStyle_red = hssfWorkbook.createCellStyle();
		cellNumStyle_red.setFillForegroundColor(HSSFColor.RED.index);
		cellNumStyle_red.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellNumStyle_red.setWrapText(true);
		HSSFCellStyle cellNumStyle_yellow = hssfWorkbook.createCellStyle();
		cellNumStyle_yellow.setFillForegroundColor(HSSFColor.YELLOW.index);
		cellNumStyle_yellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellNumStyle_yellow.setWrapText(true);
		HSSFCellStyle cellNumStyle_normal = hssfWorkbook.createCellStyle();
		cellNumStyle_normal.setWrapText(true);

		// 表名
		hssfRow = hssfSheet.createRow(0);// 建立新行，行号从0开始
		hssfRow.setHeight((short) 400);
		hssfCell = hssfRow.createCell(0);
		hssfCell.setCellValue("緊急案件清單");
		hssfCell.setCellStyle(tableName_cellNumStyle);
		// 列名
		hssfRow = hssfSheet.createRow((short) 1);// 建立新行，行号从1开始
		for (i = 0; i < arrConfigs.size(); i++) {
			midResultConfigDto = new MidResultConfigDto();
			midResultConfigDto = (MidResultConfigDto) arrConfigs.get(i);
			hssfCell = hssfRow.createCell(i);
			hssfCell.setCellValue(midResultConfigDto.getItemColumnName());
			hssfCell.setCellStyle(title_cellNumStyle);
		}

		// 数据
		midResultConfigDto = null;
		HSSFCellStyle row_cellNumStyle = hssfWorkbook.createCellStyle();
		for (i = 0; i < arrHashResult.size(); i++) {
			hashResult = arrHashResult.get(i);
			hssfRow = hssfSheet.createRow((short) (i + 2)); // 建立新行，行号从2开始
			int Dalydays = 0;
			Dalydays = Integer.parseInt((String) hashResult.get("timeLimit").toString());
			if (Dalydays >= 25) {
				row_cellNumStyle = cellNumStyle_red;
			} else if (Dalydays >= 15 && Dalydays < 25) {
				row_cellNumStyle = cellNumStyle_yellow;
			} else {
				row_cellNumStyle = cellNumStyle_normal;
			}
			for (j = 0; j < arrConfigs.size(); j++) {
				midResultConfigDto = new MidResultConfigDto();
				midResultConfigDto = (MidResultConfigDto) arrConfigs.get(j);
				hssfCell = hssfRow.createCell(j);

				hssfCell.setCellStyle(row_cellNumStyle);
				strItemColumn = midResultConfigDto.getItemColumn();
				strDataType = midResultConfigDto.getDataType();

				if (strDataType.equals("String")) {
					hssfCell.setCellValue(((String) hashResult.get(strItemColumn).toString()));
				} else if (strDataType.equals("Double"))
					hssfCell.setCellValue(Double.valueOf(hashResult.get(strItemColumn)).doubleValue());
				else if (strDataType.equals("Float"))
					hssfCell.setCellValue(Float.valueOf(hashResult.get(strItemColumn)).floatValue());
				else if (strDataType.equals("Integer"))
					hssfCell.setCellValue(Integer.valueOf(hashResult.get(strItemColumn)).intValue());
			}
		}

		OutputStream out = response.getOutputStream();
		hssfWorkbook.write(out);
		out.close();
		hssfWorkbook = null;
		hashResult = null;
		arrConfigs = null;

	}

	/**
	 * 核赔环节紧急案件清单查询(翻页)
	 * @param request 返回给页面的request
	 * @throws Exception
	 */
	public void getUndwrtUrgentCaseList(HttpServletRequest request, String pageNo, String recordPerPage) throws Exception {
		// 查询理赔节点状态信息
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		// 得到多行报案主表信息
		if (DataUtils.emptyToNull(pageNo) == null) {
			pageNo = "1";
		}
		int intRecordPerPage = Integer.parseInt(recordPerPage);
		int intPageNo = Integer.parseInt(pageNo);
		String conditions = null;
		String condition = request.getParameter("condition");
		if (DataUtils.emptyToNull(condition) != null) {
			conditions = condition;
		}
		if (conditions == null) {
			String userCode = user.getUserCode();
			String strConditionAll = "";
			String strConditionCom = "";
			String strConditionClassRisk = "";
			String strConditionNode = "";
			String utiUwLevelConditions = "";
			String classCode = "";
			String riskCode = "";
			String comCodeTemp = "";
			int nodeNo = 0;
			boolean result = true;// 没有查到双核权限数据则result = true,返回1=0
			String statementCommon = " AND WFLOG.LOGNO <> 1 AND WFLOG.NODENO <> 1 AND WFLOG.NODESTATUS IN('1','2','3') AND (" + "WFLOG.BUSINESSTYPE = 'C' OR WFLOG.BUSINESSTYPE = 'Y')" + " and (wflog.operatorcode = '" + user.getUserCode()
					+ "' or wflog.operatorcode = '' or wflog.operatorcode = '0' or wflog.operatorcode is null) ";
			UtiUwLevel utiUwLevel = new UtiUwLevel();
			utiUwLevelConditions = "UWTYPE = 'C' AND VALIDSTATUS  = '1' AND USERCODE = '" + userCode + "' order by nodeno desc";
			List<UtiUwLevel> utiUwLevelList = this.getUtiUwLevelService().findByConditions(utiUwLevelConditions);
			if (utiUwLevelList != null && utiUwLevelList.size() > 0) {
				result = false;
				utiUwLevel = utiUwLevelList.get(0);
				comCodeTemp = utiUwLevel.getId().getComCode();
				classCode = utiUwLevel.getClassCode();
				riskCode = utiUwLevel.getId().getRiskCode();
				nodeNo = utiUwLevel.getId().getNodeNo();
				strConditionNode = "wflog.modelno in('31','40') and wflog.NODENO <=" + nodeNo;
				// 拼接机构条件
				strConditionCom = this.getTaskDealViewHelper().addPowerCom(comCodeTemp, "wflog", "ComCode");
				// 拼接险种条件
				strConditionClassRisk = this.getTaskDealViewHelper().addPowerClassRisk(classCode, riskCode, "wflog");
				strConditionAll = strConditionNode + statementCommon + " AND (" + strConditionCom + " AND " + strConditionClassRisk + ")";
			}
			if (result) {
				throw new UserException(0, 1, "", "人員沒有核賠的權限!");
			} else {
				conditions = strConditionAll;
			}
		}
		Page page = this.getWorkFlowService().getUndwrtUrgentCaseList(conditions, intPageNo, intRecordPerPage);
		List<?> swfLogList = page.getResult();
		if (swfLogList != null && swfLogList.size() > 0) {
			SwfLog swfLog = null;
			for (int i = 0; i < swfLogList.size(); i++) {// 根据紧急程度，设置显示颜色
				swfLog = (SwfLog) swfLogList.get(i);
				if (swfLog.getTimeLimit() >= 25) {
					swfLog.setFlag("red");
				} else if (swfLog.getTimeLimit() >= 15 && swfLog.getTimeLimit() < 25) {
					swfLog.setFlag("yellow");
				} else {
					swfLog.setFlag("#F7F7F7");
				}
			}
		}
		request.setAttribute("page", page);
		request.setAttribute("swfLoglist", swfLogList);
		request.setAttribute("flag", "undwrt");
	}

	/**
	 * 导出核赔紧急案件清单至Excel
	 * @param httpServletRequest 返回给页面的request
	 * @throws Exception
	 */
	public void undwrtExportToExcel(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		// 查询理赔节点状态信息
		HttpSession session = httpServletRequest.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String exportType = httpServletRequest.getParameter("exportType");
		// 得到多行报案主表信息
		String conditions = "";
		String userCode = user.getUserCode();
		String strConditionAll = "";
		String strConditionCom = "";
		String strConditionClassRisk = "";
		String strConditionNode = "";
		String utiUwLevelConditions = "";
		String classCode = "";
		String riskCode = "";
		String comCodeTemp = "";
		int nodeNo = 0;
		boolean result = true;// 没有查到双核权限数据则result = true,返回1=0
		String statementCommon = " AND WFLOG.LOGNO <> 1 AND WFLOG.NODENO <> 1 AND WFLOG.NODESTATUS IN('1','2','3') AND (" + "WFLOG.BUSINESSTYPE = 'C' OR WFLOG.BUSINESSTYPE = 'Y')" + " and (wflog.operatorcode = '" + user.getUserCode()
				+ "' or wflog.operatorcode = '' or wflog.operatorcode = '0' or wflog.operatorcode is null) ";
		UtiUwLevel utiUwLevel = new UtiUwLevel();
		utiUwLevelConditions = "UWTYPE = 'C' AND VALIDSTATUS  = '1' AND USERCODE = '" + userCode + "' order by nodeno desc";
		List<UtiUwLevel> utiUwLevelList = this.getUtiUwLevelService().findByConditions(utiUwLevelConditions);
		if (utiUwLevelList != null && utiUwLevelList.size() > 0) {
			result = false;
			utiUwLevel = utiUwLevelList.get(0);
			comCodeTemp = utiUwLevel.getId().getComCode();
			classCode = utiUwLevel.getClassCode();
			riskCode = utiUwLevel.getId().getRiskCode();
			nodeNo = utiUwLevel.getId().getNodeNo();
			strConditionNode = "wflog.modelno = '40' and wflog.NODENO <=" + nodeNo;
			// 拼接机构条件
			strConditionCom = this.getTaskDealViewHelper().addPowerCom(comCodeTemp, "wflog", "ComCode");
			// 拼接险种条件
			strConditionClassRisk = this.getTaskDealViewHelper().addPowerClassRisk(classCode, riskCode, "wflog");
			strConditionAll = strConditionNode + statementCommon + " AND (" + strConditionCom + " AND " + strConditionClassRisk + ")";
		}
		if (result) {
			throw new UserException(0, 1, "", "人員沒有核賠的權限!");
		} else {
			conditions = strConditionAll;
		}
		Page page = this.getWorkFlowService().getUndwrtUrgentCaseList(conditions, 1, 1000);
		// 组织数据
		// 定义列名信息
		List<MidResultConfigDto> arrConfigs = new ArrayList<MidResultConfigDto>();
		String[] columnS = { "registNo", "policyNo", "insuredName", "handlerName", "comCode", "flowInTime", "nodeStatus", "timeLimit" };
		String[] columnNameS = { "備案號碼", "保單號碼", "被保險人", "操作人員", "承保機構代碼", "流入時間", "案件狀態", "等待時間（天）" };
		for (int i = 0; i < columnNameS.length; i++) {
			MidResultConfigDto midResultConfigDto = new MidResultConfigDto();
			midResultConfigDto.setItemColumn(columnS[i]);
			midResultConfigDto.setItemColumnName(columnNameS[i]);
			midResultConfigDto.setDataType("String");
			arrConfigs.add(midResultConfigDto);
		}
		List<?> swfLoglist = page.getResult();
		List<Map<String, String>> swfLoglistRed = new ArrayList<Map<String, String>>();
		List<Map<String, String>> swfLoglistYellow = new ArrayList<Map<String, String>>();
		List<Map<String, String>> swfLoglistAll = new ArrayList<Map<String, String>>();
		if (swfLoglist != null && swfLoglist.size() > 0) {
			SwfLog swfLog = null;
			for (int i = 0; i < swfLoglist.size(); i++) {// 根据紧急程度，拆分为三个List
				swfLog = (SwfLog) swfLoglist.get(i);
				Map<String, String> swfLogDtoHashMap = new HashMap<String, String>();
				swfLogDtoHashMap.put("registNo", swfLog.getRegistNo());
				swfLogDtoHashMap.put("policyNo", swfLog.getPolicyNo());
				swfLogDtoHashMap.put("insuredName", swfLog.getInsuredName());
				swfLogDtoHashMap.put("handlerName", swfLog.getHandlerName());
				swfLogDtoHashMap.put("comCode", swfLog.getComCode());
				swfLogDtoHashMap.put("flowInTime", swfLog.getFlowInTime());
				swfLogDtoHashMap.put("nodeStatus", swfLog.getNodeStatus());
				swfLogDtoHashMap.put("timeLimit", "" + swfLog.getTimeLimit());
				swfLoglistAll.add(swfLogDtoHashMap);
				if (swfLog.getTimeLimit() >= 25) {
					swfLoglistRed.add(swfLogDtoHashMap);
				} else if (swfLog.getTimeLimit() >= 15 && swfLog.getTimeLimit() < 25) {
					swfLoglistYellow.add(swfLogDtoHashMap);
				}
			}
		} else {
			throw new UserException(0, 1, "核賠導出數據", "沒有符合要求的數據，請重新選擇導出類型！");
		}
		if (exportType != null) {
			if ("1".equals(exportType)) {// 红色预警
				this.exportExcel(swfLoglistRed, arrConfigs, httpServletResponse);
			} else if ("2".equals(exportType)) {// 黄色预警
				this.exportExcel(swfLoglistYellow, arrConfigs, httpServletResponse);
			} else {// 全部导出
				this.exportExcel(swfLoglistAll, arrConfigs, httpServletResponse);
			}
			// 提交成功後显示信息
			user.setUserMessage("導出Excel文件成功！");
			httpServletRequest.setAttribute("user", user);
		} else {
			throw new UserException(0, 1, "核賠導出數據", "導出Excel發生異常，請聯系管理員！");
		}
	}

	/**
	 * 查询 - 工作流查询
	 * @param httpServletRequest 封装查询条件的request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page getWorkFlowList(HttpServletRequest httpServletRequest, int pageNo, int pageSize) throws Exception {
		// 根据输入的保单号，报案号生成SQL where 子句
		String registNo = StringUtils.rightTrim(httpServletRequest.getParameter("prpLregistRegistNo")); // 报案号
		String policyNo = StringUtils.rightTrim(httpServletRequest.getParameter("prpLregistPolicyNo")); // 保单号
		String licenseNo = StringConvert.getParam(httpServletRequest, "prpLregistLicenseNo", ConstantCodes.YUI_CHARSET);// 车牌号
		String insuredName = StringConvert.getParam(httpServletRequest, "prpLregistInsuredName", ConstantCodes.YUI_CHARSET);// 被保险人姓名
		String claimNo = StringUtils.rightTrim(httpServletRequest.getParameter("prpLregistClaimNo"));// 立案号
		String caseType = httpServletRequest.getParameter("caseType");
		String printNo = StringConvert.getParam(httpServletRequest, "prpLregistCompelLicenseNo", ConstantCodes.YUI_CHARSET);// 强制证号码
		// 财车车牌
		String thirdLicenseNo = StringConvert.getParam(httpServletRequest, "prpLregistThirdLicenseNo", ConstantCodes.YUI_CHARSET);
		// 被保险人ID - 被保险人身份证号/法人代码
		String insuredId = StringUtils.rightTrim(httpServletRequest.getParameter("prpLregistInsuredId"));
		// 受害人ID - 受害人身份证号
		String identifyNumber = StringConvert.getParam(httpServletRequest, "prpLregistIdentifyNumber", ConstantCodes.YUI_CHARSET);
		// 任意保險卡號 
		String visaCodeBI = StringConvert.getParam(httpServletRequest, "prpLregistVisaCodeBI", ConstantCodes.YUI_CHARSET);
		
		String conditions = " ( nodeType='regis' or nodeType = 'Broker' ) ";
		List<String> registNoList = null;
		Page page = new Page((pageNo - 1) * pageSize, 0, pageSize, new ArrayList<Object>());
		String tempStr = "";
		if (DataUtils.emptyToNull(policyNo) != null) {
			// 由於可能出现交强混合的方式，需要改变查询保单的方式，为转换为从混合表中registNo的号码的查询
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByPolicyNo(policyNo, httpServletRequest.getParameter("PolicyNoSign"));
			if (tempList.isEmpty()) {// 查询条件中有保单、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (DataUtils.emptyToNull(claimNo) != null) {
			// 改变查询赔案（立案）号的方式，为转换为从混合表中registNo的号码的查询
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByClaimNo(claimNo, httpServletRequest.getParameter("ClaimNoSign"));
			if (tempList.isEmpty()) {// 查询条件中有立案号、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (DataUtils.emptyToNull(printNo) != null) {// 强制保险证号码
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByPrintNo(printNo, httpServletRequest.getParameter("CompelLicenseNo"));
			if (tempList.isEmpty()) {// 查询条件中有立案号、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (DataUtils.emptyToNull(thirdLicenseNo) != null) {// 财车车牌
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByThirdLicenseNo(thirdLicenseNo, httpServletRequest.getParameter("ThirdLicenseNoSign"));
			if (tempList.isEmpty()) {// 查询条件中有立案号、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (DataUtils.emptyToNull(identifyNumber) != null) {// 受害人身份证
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByPersonIdentifyNumber(identifyNumber, httpServletRequest.getParameter("IdentifyNumberSign"));
			if (tempList.isEmpty()) {// 查询条件中有立案号、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (DataUtils.emptyToNull(visaCodeBI) != null) {// 保险卡号
			List<String> tempList = this.prpLregistrpolicyService.getPolicyNoByVisaCodeBI(visaCodeBI, httpServletRequest.getParameter("visaCodeBISign"));
			if (tempList.isEmpty()) {// 查询条件中有保险卡号、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (registNoList != null) {
			tempStr = "";
			if (registNoList.size() > 10) {
				for (String tempRegistNo : registNoList) {
					tempStr += ",'" + tempRegistNo + "'";
				}
				conditions += " and RegistNo in (" + tempStr.substring(1) + ")";
			} else {
				for (String tempRegistNo : registNoList) {
					tempStr += " RegistNo='" + tempRegistNo + "' or ";
				}
				conditions += " and (" + tempStr.substring(0, tempStr.lastIndexOf("or")) + ") ";
			}
		}
		if (DataUtils.emptyToNull(insuredId) != null) {// 被保险人身份证号
			tempStr = this.prpLregistrpolicyService.getPolicyNoByInsuredIdentifyNumber(insuredId);
			if (tempStr == null) {
				return page;
			}
			conditions += " and (" + tempStr + ") ";
		}
		if (DataUtils.emptyToNull(insuredName) != null) {
			conditions = conditions + StringConvert.convertString(" insuredName", insuredName, httpServletRequest.getParameter("InsuredNameSign"));
		}
		if (DataUtils.emptyToNull(licenseNo) != null) {
			conditions = conditions + StringConvert.convertString(" lossitemName", licenseNo, httpServletRequest.getParameter("LicenseNoSign"));
		}
		if (DataUtils.emptyToNull(registNo) != null) {
			conditions = conditions + StringConvert.convertString("RegistNO", registNo, httpServletRequest.getParameter("RegistNoSign"));
		}
		// 判断流程流转数据是否已经结束
		boolean boolCaseType = false;
		if (caseType != null && !caseType.equals("")) {
			if (caseType.equals("1")) { // 流程流转结束
				boolCaseType = true;
				conditions = conditions + " and  flowstatus='0'";
			} else if (caseType.equals("0")) { // 流程流转未结束
				conditions = conditions + " and  (flowstatus='1' or flowstatus='2') ";
			}
		}
		conditions = conditions + " order by handleTime desc";
		// 判断条件限制
		if (boolCaseType) {
			page = this.getSwfLogService().findByPageFromView(conditions, pageNo, pageSize);
		} else {
			page = this.getSwfLogService().findSwfLog(QueryRule.getInstance().addSql(conditions), pageNo, pageSize);
		}
		List<?> pageResult = page.getResult();
		if (pageResult != null && !pageResult.isEmpty()) {
			SwfLog swfLog = null;
			List<Prplregistrpolicy> temp = null;// 关联保单
			for (Iterator<?> it = pageResult.iterator(); it.hasNext();) {
				swfLog = (SwfLog) it.next();
				swfLog.setRiskCodeName(this.getCodeService().translateRiskCode(swfLog.getRiskCode(), true));
				if ("6".equals(swfLog.getNodeStatus())) {
					swfLog.setOtherFlag("備案已註銷");
				}
				swfLog.setComName(this.getCodeService().translateComCode(swfLog.getComCode(), true));
				// 强三多个保单号的显示
				temp = this.getPrpLregistrpolicyService().findByRegistNo(swfLog.getRegistNo());
				for (Prplregistrpolicy p : temp) {
					swfLog.getRelatePolicyList().add(p.getId().getPolicyNo());
				}
				swfLog.setEditType(httpServletRequest.getParameter("editType"));
			}
		}
		httpServletRequest.setAttribute("page", page);
		return page;
	}

	/**
	 * 集合处理，若initList存在则返回与tempList的交集，不存在则用tempList初始化initList
	 * @author 中科软
	 * @param tempList
	 * @param initList
	 * @return
	 */
	private List<String> getRegistList(List<String> tempList, List<String> initList) {
		if (initList == null) {
			initList = new ArrayList<String>();
			initList.addAll(tempList);
		} else {
			initList.retainAll(tempList);
		}
		return initList;
	}

	/**
	 * 综合查询
	 * @author 中科软
	 * @date May 28, 2013 12:59:42 PM
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Page integratedQuery(HttpServletRequest request, int pageNo, int pageSize) throws Exception {
		Page page = new Page((pageNo - 1) * pageSize, 0, pageSize, new ArrayList<Object>());
		// 归属机构
		String comCode = request.getParameter("comCode");
		if (DataUtils.emptyToNull(comCode) == null) {
			UserDto userDto = (UserDto) request.getSession().getAttribute("user");
			comCode = userDto.getComCode();
		}
		// 操作人员
		String userName = StringConvert.getParam(request, "userName", ConstantCodes.YUI_CHARSET);
		String userCode = "";
		if (DataUtils.emptyToNull(userName) != null) {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("userName", userName);
			List<PrpDuser> prpDuserList = prpDuserService.findPrpDuser(queryRule);
			if (prpDuserList == null || prpDuserList.isEmpty()) {
				return page;
			}
			for (int i = 0; i < prpDuserList.size(); i++) {
				PrpDuser prpDuser = prpDuserList.get(i);
				userCode += " userCode='" + prpDuser.getUserCode() + "' or ";// 先用“?”代替参数名称
			}
			userCode = userCode.substring(0, userCode.lastIndexOf("or"));
		}

		String registNo = StringUtils.rightTrim(request.getParameter("prpLregistRegistNo")); // 报案号
		String policyNo = StringUtils.rightTrim(request.getParameter("prpLregistPolicyNo")); // 保单号
		String licenseNo = StringConvert.getParam(request, "prpLregistLicenseNo", ConstantCodes.YUI_CHARSET);// 车牌号
		String insuredName = StringConvert.getParam(request, "prpLregistInsuredName", ConstantCodes.YUI_CHARSET);// 被保险人姓名
		String claimNo = StringUtils.rightTrim(request.getParameter("prpLregistClaimNo"));// 立案号
		String caseType = request.getParameter("caseType");
		String printNo = StringConvert.getParam(request, "prpLregistCompelLicenseNo", ConstantCodes.YUI_CHARSET);// 强制证号码
		// 财车车牌
		String thirdLicenseNo = StringConvert.getParam(request, "prpLregistThirdLicenseNo", ConstantCodes.YUI_CHARSET);
		// 被保险人ID - 被保险人身份证号/法人代码
		String insuredId = StringUtils.rightTrim(request.getParameter("prpLregistInsuredId"));
		// 受害人ID - 受害人身份证号
		String identifyNumber = StringConvert.getParam(request, "prpLregistIdentifyNumber", ConstantCodes.YUI_CHARSET);

		String claimNodeCode = request.getParameter("claimNodeCode");
		String statStartDate = request.getParameter("statStartDate");
		String statEndDate = request.getParameter("statEndDate");

		/* 得到页面选择查询情况 */
		List<String> registNoList = null;
		String tempStr = "";
		if (DataUtils.emptyToNull(policyNo) != null) {
			// 由於可能出现交强混合的方式，需要改变查询保单的方式，为转换为从混合表中registNo的号码的查询
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByPolicyNo(policyNo, request.getParameter("PolicyNoSign"));
			if (tempList.isEmpty()) {// 查询条件中有保单、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (DataUtils.emptyToNull(claimNo) != null) {
			// 改变查询赔案（立案）号的方式，为转换为从混合表中registNo的号码的查询
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByClaimNo(claimNo, request.getParameter("ClaimNoSign"));
			if (tempList.isEmpty()) {// 查询条件中有立案号、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (DataUtils.emptyToNull(printNo) != null) {// 强制保险证号码
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByPrintNo(printNo, request.getParameter("CompelLicenseNo"));
			if (tempList.isEmpty()) {// 查询条件中有立案号、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (DataUtils.emptyToNull(thirdLicenseNo) != null) {// 财车车牌
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByThirdLicenseNo(thirdLicenseNo, request.getParameter("ThirdLicenseNoSign"));
			if (tempList.isEmpty()) {// 查询条件中有立案号、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		if (DataUtils.emptyToNull(identifyNumber) != null) {// 受害人身份证
			List<String> tempList = this.prpLregistrpolicyService.getRegistNoByPersonIdentifyNumber(identifyNumber, request.getParameter("IdentifyNumberSign"));
			if (tempList.isEmpty()) {// 查询条件中有立案号、但无与之相关的备案号
				return page;
			}
			registNoList = this.getRegistList(tempList, registNoList);
		}
		StringBuffer conditions = new StringBuffer("");
		if (registNoList != null) {
			tempStr = "";
			if (registNoList.size() > 10) {
				for (String tempRegistNo : registNoList) {
					tempStr += ",'" + tempRegistNo + "'";
				}
				conditions.append(" and RegistNo in (" + tempStr.substring(1) + ")");
			} else {
				for (String tempRegistNo : registNoList) {
					tempStr += " RegistNo='" + tempRegistNo + "' or ";
				}
				conditions.append(" and (" + tempStr.substring(0, tempStr.lastIndexOf("or")) + ") ");
			}
		}
		if (DataUtils.emptyToNull(insuredId) != null) {// 被保险人身份证号
			tempStr = this.prpLregistrpolicyService.getPolicyNoByInsuredIdentifyNumber(insuredId);
			if (tempStr == null) {
				return page;
			}
			conditions.append(" and (" + tempStr + ") ");
		}
		if (DataUtils.emptyToNull(insuredName) != null) {
			conditions.append(StringConvert.convertString("insuredName", insuredName, request.getParameter("InsuredNameSign")));
		}
		if (DataUtils.emptyToNull(licenseNo) != null) {
			conditions.append(StringConvert.convertString("lossitemName", licenseNo, request.getParameter("LicenseNoSign")));
		}
		if (DataUtils.emptyToNull(registNo) != null) {
			conditions.append(StringConvert.convertString("RegistNO", registNo, request.getParameter("RegistNoSign")));
		}
		// 判断流程流转数据是否已经结束
		boolean boolCaseType = false;
		if (DataUtils.emptyToNull(caseType) != null) {
			if (caseType.equals("1")) { // 流程流转结束
				boolCaseType = true;
			}
		}
		String sql = "";
		StringBuffer search = new StringBuffer();
		search.append(" select flowid,logno,nodestatus,riskcode,nodetype,businessno,policyno,modelno,nodeno,insuredName,handlerCode,handlerName,");
		search.append(" typeflag,registno,handleTime,keyin,keyout,LossItemCode,LossItemName,'' as iflowid,0 as imodelno,0 as inodeno,'' as businesstype,'' as ibusinessno,0 as ilogno,'' as ioperatorName");
		search.append(" from ").append(boolCaseType ? " swflogstore " : " swflog ");
		if("65".equals(comCode)){
			search.append(" where (handleDept in (Select ComCode from prpdCompany Start With ComCode  = '" + comCode + "' Connect By Prior comCode = uppercomCode  and prior ComCode != ComCode  and validstatus='1')");
			//理赔中60,61机构的上级机构为65机构，承保系统上级机构是35机构，所有数据库中配置的是35机构，65特殊处理
			search.append(" or handleDept in ('60','61'))");
		}else{
			search.append(" where handleDept in (Select ComCode from prpdCompany Start With ComCode  = '" + comCode + "' Connect By Prior comCode = uppercomCode  and prior ComCode != ComCode  and validstatus='1')");
		}
		if (conditions.length() != 0) {
			search.append(" " + conditions);
		}
		if (!"".equals(userCode)) {
			search.append(" and (" + userCode.replaceAll("userCode", "handlerCode") + " )");
		}
		if (DataUtils.emptyToNull(statStartDate) != null && DataUtils.emptyToNull(statEndDate) != null) {
			search.append(" and to_date(substr(handleTime,1,10),'yyyy-MM-dd') between to_date('" + statStartDate + "','yyyy-MM-dd') and to_date('" + statEndDate + "','yyyy-MM-dd')");
		}
		if ("endca".equals(claimNodeCode)) {// 已结案件
			search.append(" and flowstatus='0'");
			search.append(" and flowid in (select flowid from swflogstore where nodetype='endca')");
			search.append(" and nodeType in ('regis','scahed','check','claim','certa','wound','propc','verif','certi','compp','speci','veric','endca')");
			search.append(" order by registno desc,logno asc ");
			sql = search.toString();
		} else if ("noendca".equals(claimNodeCode)) {// 未决案件
			search.append(" and flowstatus!='0'");
			search.append(" and nodeType in ('regis','sched','check','claim','certa','wound','propc','verif','certi','compp','speci','veric','endca')");
			search.append(" order by registno desc,logno asc ");
			sql = search.toString();
		} else if ("veric".equals(claimNodeCode)) {
			StringBuffer swflog = new StringBuffer("");
			swflog.append(" select * from " + (boolCaseType ? " swflogstore " : " swflog ") + "");
			swflog.append(" where nodetype='veric' ");
			swflog.append(conditions);
			StringBuffer veric = new StringBuffer("");
			veric.append("select a.flowid,a.logno,a.nodestatus,a.riskcode,a.nodetype,a.businessno,a.policyno,a.modelno,a.nodeno,a.insuredName,a.handlerCode,a.handlerName,");
			veric.append("a.typeflag,a.registno,a.handleTime,a.keyin,a.keyout,a.LossItemCode,a.LossItemName,b.flowid iFlowid,b.modelno iModelno,b.nodeno iNodeno,b.businesstype,b.businessno iBusinessno,b.logno iLogno,b.operatorName ");
			veric.append(" from ");
			veric.append("(" + swflog + ") a ,wflog b ");
			veric.append(" where  a.flowid=b.relateflowid and a.logno = b.relatelogno and b.nodeNo not in (1,11) ");
			if (DataUtils.emptyToNull(userCode) != null) {
				veric.append(" and (" + userCode.replaceAll("userCode", "operatorCode") + ")");
			}
			veric.append(" and to_date(substr(a.handleTime,1,10),'yyyy-MM-dd') between to_date('" + statStartDate + "','yyyy-MM-dd') and to_date('" + statEndDate + "','yyyy-MM-dd')");
			if("65".equals(comCode)){
				veric.append(" and (a.handleDept in (Select ComCode from prpdCompany Start With ComCode  = '" + comCode + "' Connect By Prior comCode = uppercomCode  and prior ComCode != ComCode  and validstatus='1')");
				//理赔中60,61机构的上级机构为65机构，承保系统上级机构是35机构，所有数据库中配置的是35机构，65特殊处理
				veric.append(" or a.handleDept in ('60','61'))");
				veric.append(" and (b.deptcode in (Select ComCode from prpdCompany Start With ComCode  = '" + comCode + "' Connect By Prior comCode = uppercomCode  and prior ComCode != ComCode  and validstatus='1')");
				veric.append(" or b.deptcode in ('60','61'))");
			}else{
				veric.append(" and a.handleDept in (Select ComCode from prpdCompany Start With ComCode  = '" + comCode + "' Connect By Prior comCode = uppercomCode  and prior ComCode != ComCode  and validstatus='1')");
				veric.append(" and b.deptcode in (Select ComCode from prpdCompany Start With ComCode  = '" + comCode + "' Connect By Prior comCode = uppercomCode  and prior ComCode != ComCode  and validstatus='1')");
			}
			veric.append(" order by a.flowintime desc,b.flowintime asc");
			sql = veric.toString();
		} else {
			search.append(StringConvert.convertString("nodeType", claimNodeCode, null));
			search.append(" order by flowintime desc");
			sql = search.toString();
		}
		page = swfLogService.findByStatement(sql, pageNo, pageSize);
		if(!"veric".equals(claimNodeCode)){
			List<SwfLog> resultList = page.getResult();
			for(SwfLog swfLog : resultList){
				//已处理的核赔要处理
				if("veric".equals(swfLog.getNodeType())){
					String wflogSql = " relateflowid = '"+swfLog.getId().getFlowID()+"' and relatelogno = "+swfLog.getId().getLogNo();//查询核赔的
					wflogSql += " and nodeNo not in (1,11) ";//过滤出单员与审核通过节点
					wflogSql += " order by logno desc";
					List<WfLog> wflogList = this.getWfLogService().findByConditions(wflogSql);
					WfLog wfLog = null;
					if(wflogList!=null && !wflogList.isEmpty()){
						wfLog = wflogList.get(0);
						swfLog.setiFlowID(wfLog.getId().getFlowId());
						swfLog.setiLogNo(wfLog.getId().getLogNo());
						swfLog.setiModelNo(wfLog.getModelNo());
						swfLog.setiNodeNo(wfLog.getId().getLogNo());
						swfLog.setiBusinessNo(wfLog.getBusinessNo());
						swfLog.setBusinessType(wfLog.getBusinessType());
					}
				}
			}
		}
		return page;
	}

	/**
	 * 根据条件显示新增定损调度任务清单
	 * @param httpServletRequest 返回给页面的request
	 * @param conditions 查询条件
	 * @throws Exception
	 */
	public Page getScheduleAddCertainLossSwfLogList(HttpServletRequest httpServletRequest, int pageNo, int pageSize) throws Exception {
		String registNo = httpServletRequest.getParameter("registNo");
		String startDate = httpServletRequest.getParameter("startDate");
		String endDate = httpServletRequest.getParameter("endDate");
		String conditions = " NodeType = 'sched' ";
		conditions = conditions + StringConvert.convertString("keyin", registNo, httpServletRequest.getParameter("registNoSign"));
		conditions = conditions + StringConvert.convertString("LossItemName", StringConvert.getParam(httpServletRequest, "prpLscheduleItemLicenseNo", ConstantCodes.YUI_CHARSET), httpServletRequest.getParameter("prpLscheduleItemLicenseNoSign"));
		conditions = conditions + StringConvert.convertString("InsuredName", StringConvert.getParam(httpServletRequest, "InsuredName", ConstantCodes.YUI_CHARSET), httpServletRequest.getParameter("InsuredNameSign"));

		if (startDate != null && startDate.trim().length() > 0) {
			conditions = conditions + " AND (FlowInTime>='" + startDate + "') ";
		}
		if (endDate != null && endDate.trim().length() > 0) {
			conditions = conditions + " AND (FlowInTime<='" + new DateTime(endDate, DateTime.YEAR_TO_DAY).addDay(1) + "') ";
		}
		// 从翻页取数据
		String condition = httpServletRequest.getParameter("condition");
		String searchFlag = httpServletRequest.getParameter("searchFlag");
		if ("true".equals(searchFlag)) {

		} else {
			if (condition != null && condition.trim().length() > 0) {
				conditions = condition;
			}
		}
		Page page = this.scheduleService.findByQueryConditions(conditions, pageNo, pageSize);
		return page;
	}

	/**
	 * 根据节点种类和操作状态工作流信息(目前只做了特殊赔案的查询)
	 * @param request 返回给页面的request
	 * @param status 操作状态
	 * @param nodeType 节点种类
	 * @throws Exception
	 */
	public void getSwfLogList(HttpServletRequest request, String caseFlag, String userCode, String nodeType) throws Exception {
		if (DataUtils.emptyToNull(caseFlag) != null) {
			caseFlag = caseFlag.substring(0, caseFlag.length() - 1);
		}
		String conditions = " handlerCode='" + userCode + "' AND NodeType = '" + nodeType + "'";
		conditions = conditions + StringConvert.convertString("registno", request.getParameter("RegistNo"), request.getParameter("RegistNoSign"));
		// 支持多保单的查询
		String policyNo = request.getParameter("PolicyNo");
		if (policyNo != null && policyNo.length() > 1) {
			// 考虑一下，如果是立案或者结案，或者是计算书处，是如何处理的呢？应该是不用这样关联和转换的吧？
			if (!checkNeedFindRelatePolicy(nodeType)) {
				conditions = conditions + StringConvert.convertString("policyno", request.getParameter("PolicyNo"), request.getParameter("PolicyNoSign"));
			} else {
				conditions = conditions + " and registno in (select registno from " + "prplregistrpolicy where 1=1 " + StringConvert.convertString(" policyNo", policyNo, request.getParameter("PolicyNoSign")) + ") ";
			}
		}
		if (DataUtils.emptyToNull(caseFlag) != null) {
			conditions = conditions + "  AND NodeStatus in (" + caseFlag + ")";
		}
		UIPowerInterface uiPowerInterface = new UIPowerInterface();
		UserDto userDto = (UserDto) request.getSession().getAttribute("user");
		conditions = conditions + uiPowerInterface.addPower(userDto, "swflog", "", "ComCode");
		// 判断节点，有的需要根据用户名称进行查询，有的不需要用户名字进行查询
		// 目前只有为4的状态，以提交需要进行翻页
		List<SwfLog> swfLogList = this.getWorkFlowService().findNodesByConditions(conditions);
		List<SwfLog> swfLogListTemp = new ArrayList<SwfLog>();
		// 如果nodeType=定损/核损的话，还需要转换定损/核损的类型名称
		if (nodeType.equals("speci")) {
			for (SwfLog swfLogTemp : swfLogList) {
				if (swfLogTemp.getTypeFlag().equals("3")) {
					swfLogTemp.setTypeFlagName("通融");
				} else if (swfLogTemp.getTypeFlag().equals("4")) {
					swfLogTemp.setTypeFlagName("預付");
				} else if (swfLogTemp.getTypeFlag().equals("5")) {
					swfLogTemp.setTypeFlagName("預賠");
				} else {
					swfLogTemp.setTypeFlagName("");
				}
				swfLogListTemp.add(swfLogTemp);
			}
			swfLogList = swfLogListTemp;
		}
		SwfLog swfLog = new SwfLog();
		// 转换保单号码用的
		swfLogList = this.translateRelatePolicyOnTaskQuery(swfLogList, nodeType);
		swfLog.setSwfLogList(swfLogList);
		swfLog.setNodeType(nodeType);
		request.setAttribute("swfLog", swfLog);
		// 防止再次刷新的时候会失去值
		request.setAttribute("nodeType", nodeType);
	}

	/* ========================第五部分：工作流统计操作======================== */
	/**
	 * 工作流统计--节点状态：统计工作流节点状态数量的功能
	 * @param httpServletRequest HttpServletRequest
	 * @param strStartDate String
	 * @param strEndDate String
	 * @throws Exception
	 * @return StatStatusDto
	 */

	public StatStatusDto getNodeStatusStat(HttpServletRequest httpServletRequest, String strStartDate, String strEndDate) throws Exception {
		return this.getWorkFlowStatViewHelper().getNodeStatusStat(httpServletRequest, strStartDate, strEndDate);
	}

	/**
	 * 工作流统计--用户节点状态：统计工作流节点用户状态数量的功能
	 * @param httpServletRequest HttpServletRequest
	 * @param strStartDate String
	 * @param strEndDate String
	 * @throws Exception
	 * @return StatStatusDto
	 */
	public StatStatusDto getNodeUserStatusStat(HttpServletRequest httpServletRequest, String strStartDate, String strEndDate) throws Exception {
		return this.getWorkFlowStatViewHelper().getNodeUserStatusStat(httpServletRequest, strStartDate, strEndDate);

	}

	/**
	 * 工作流查询--超时工作流查看：按条件查询超时案件的信息
	 * @param httpServletRequest HttpServletRequest
	 * @param conditions String 查询条件
	 * @throws Exception
	 * @return List<SwfLog>
	 */
	public List<SwfLog> getNodeTimeOutInfo(HttpServletRequest httpServletRequest, String conditions) throws Exception {
		return this.getWorkFlowStatViewHelper().getNodeTimeOutInfo(httpServletRequest, conditions);

	}

	/**
	 * 根据节点种类和操作状态和办理人员编码查询查勘信息
	 * @param httpServletRequest 返回给页面的request
	 * @param status 操作状态
	 * @param user 办理人员/用户对象
	 * @param nodeType 节点种类
	 * @throws Exception
	 */
	public void getWorkFLowNodeStatsStat(HttpServletRequest httpServletRequest, UserDto user, String nodeType) throws Exception {
		this.getWorkFlowStatViewHelper().getWorkFLowNodeStatsStat(httpServletRequest, user, nodeType);
	}

	/* ========================（工作流统计操作）结束============================ */
	/* ========================（工作流检查校验操作）开始======================== */
	/**
	 * 检查该节点是否可以被提交，如果不能提交丢出理由原因
	 * @param swfLogCurrent SwfLog 需要被检查的节点对象
	 * @throws Exception
	 */
	public void checkNodeSubmit(SwfLog swfLogCurrent) throws Exception {
		// 目前只检查单证的提交，如果它之前的立案没有做，定损，核损，人伤和人伤核损只要有一个没做完就不许提交
		// 单证节点判断思路
		// ----------------------------------------------------------
		// 1.首先判断该流程中是否存在如下的节点（立案，定损，核损，人伤和人伤核损）
		// 2.这些节点是不是处在活动状态，如果是的话，不允许提交
		// ----------------------------------------------------------
		String nodeType = swfLogCurrent.getNodeType();
		String conditions = "";
		String nodeMsg = "";
		String msg = "";
		int checkCount = 0;
		List<SwfLog> swfLogList = new ArrayList<SwfLog>();
		Iterator<?> it = null;
		if (nodeType.equals("certi")) {
			conditions = " flowid='" + swfLogCurrent.getId().getFlowID() + "' and (nodeType in ('claim','verif'," + "'veriw','propv','check')) and  nodeStatus < 4";
			swfLogList = this.getWorkFlowService().findNodesByConditions(conditions);
			it = swfLogList.iterator();
			while (it.hasNext()) {
				SwfLog swfLog = (SwfLog) it.next();
				nodeMsg = nodeMsg + swfLog.getNodeName() + ",";
				checkCount++;
			}
			if (checkCount > 0) {
				msg = nodeMsg.substring(0, nodeMsg.length() - 1) + "節點沒有處理完畢，不可以進行單證提交";
				throw new UserException(1, 3, "工作流", msg);
			}
		}
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(swfLogCurrent.getKeyIn());
		//间意赔案不做判断
		if (nodeType.equals("compe")&&prpLclaim!=null&&!"1".equals(prpLclaim.getSimpleFlag())) {
			conditions = " flowid='" + swfLogCurrent.getId().getFlowID() + "' and (nodeType in ('claim','verif'," + "'veriw','propv','certi')) and  nodeStatus < 4";
			swfLogList = this.getWorkFlowService().findNodesByConditions(conditions);
			it = swfLogList.iterator();
			while (it.hasNext()) {
				SwfLog swfLog = (SwfLog) it.next();
				nodeMsg = nodeMsg + swfLog.getNodeName() + ",";
				checkCount++;
			}
			if (checkCount > 0) {
				msg = nodeMsg.substring(0, nodeMsg.length() - 1) + "節點沒有處理完畢，不可以進行理算的操作";
				throw new UserException(1, 3, "工作流", msg);
			}
		}
		// 结束单证的判断
	}

	/**
	 * 检查该节点是否可以被提交，如果不能提交丢出理由原因
	 * @param swfLogFlowID 工作流号
	 * @param swfLogLogNo 序号
	 * @throws Exception
	 */
	public String checkNodeSubmit(String swfLogFlowID, String swfLogLogNo) throws Exception {
		// 目前只检查单证的提交，如果它之前的立案没有做，定损，核损，人伤和人伤核损只要有一个没做完就不许提交
		// 单证节点判断思路
		// ----------------------------------------------------------
		// 1.首先判断该流程中是否存在如下的节点（立案，定损，核损，人伤和人伤核损）
		// 2.这些节点是不是处在活动状态，如果是的话，不允许提交
		// ----------------------------------------------------------
		String nodeType = "";
		String conditions = "";
		String nodeMsg = "";
		String msg = "";
		String nodeName = "";
		int checkCount = 0;
		int logNo = Integer.parseInt(DataUtils.nullToZero(swfLogLogNo));
		if (swfLogFlowID == null || logNo < 1) {
			return msg;
		}
		SwfLog swfLogDtoCurrent = this.getWorkFlowService().findNodeByPrimaryKey(swfLogFlowID, logNo);
		if (swfLogDtoCurrent == null)
			return msg;
		nodeType = swfLogDtoCurrent.getNodeType();
		if (nodeType.equals("verip")) {// 核价检查是否已经向外询价
			nodeName = "核價";
			conditions = " flowid='" + swfLogDtoCurrent.getId().getFlowID() + "' and nodeType='verpo' ";
		}
		if (nodeType.equals("certi")) {// 单正检查是不是可以提交
			nodeName = "單證";
			//'wound', cerat ,'propc',
			conditions = " flowid='" + swfLogDtoCurrent.getId().getFlowID() + "' and (nodeType in ('claim','verif','verip','verpo'," + "'veriw','propv','check')) and  nodeStatus <4";
		}
		if (nodeType.equals("sched")) { // 定损检查是不是可以提交
			nodeName = "分案";
			conditions = " flowid='" + swfLogDtoCurrent.getId().getFlowID() + "' and (nodeType in('check')) and  nodeStatus <4";
		}
		List<SwfLog> swfLogList = this.getWorkFlowService().findNodesByConditions(conditions);
		Iterator<SwfLog> it = swfLogList.iterator();
		while (it.hasNext()) {
			SwfLog swfLog = (SwfLog) it.next();
			if ("claim".equals(swfLog.getNodeType())) {
				nodeMsg = nodeMsg + "'" + swfLog.getNodeName() + "',";
			} else {
				if("propc".equals(swfLog.getNodeType())||"propv".equals(swfLog.getNodeType())){
					nodeMsg = nodeMsg + "'" + swfLog.getNodeName() + "',";
				}else if("wound".equals(swfLog.getNodeType())||"veriw".equals(swfLog.getNodeType())){
					nodeMsg = nodeMsg + "'人傷名稱為：" + DataUtils.nullToEmpty(swfLog.getLossItemName()) + "的" + swfLog.getNodeName() + "',";
				}else {
					nodeMsg = nodeMsg + "'牌照號碼爲：" + DataUtils.nullToEmpty(swfLog.getLossItemName()) + "的" + swfLog.getNodeName() + "',";
				}
			}
			checkCount++;
		}
		if (checkCount > 0) {
			msg = nodeMsg.substring(0, nodeMsg.length() - 1) + "節點沒有處理完畢，不能結束" + nodeName;
		}
		// 结束单证的判断
		return msg;
	}

	/**
	 * 检查该节点在工作流中是否存在，如果存在，返回节点名称
	 * @param swfLogFlowID 工作流号
	 * @param swfLogLogNo 序号
	 * @throws Exception
	 */
	public String checkNodeExistInFlow(String swfLogFlowID, String strNodeTypes) throws Exception {
		String conditions = "";
		String nodeMsg = "";
		String msg = "";
		int checkCount = 0;
		conditions = " flowid='" + swfLogFlowID + "' and (nodeType in (" + strNodeTypes + ")) and  nodeStatus <4";
		List<SwfLog> swfLogList = this.getWorkFlowService().findNodesByConditions(conditions);
		Iterator<SwfLog> it = swfLogList.iterator();
		while (it.hasNext()) {
			SwfLog swfLog = (SwfLog) it.next();
			nodeMsg = nodeMsg + "'" + swfLog.getNodeName() + "',";
			checkCount++;
		}
		if (checkCount > 0) {
			msg = nodeMsg.substring(0, nodeMsg.length() - 1);
		}
		// 结束判断
		// 如果msg的长度大
		return msg;
	}

	/**
	 * 校验工作流DTO是不是合法可以处理的dto
	 * @param workFlowDto WorkFlowDto
	 * @throws Exception
	 * @return boolean
	 */
	public boolean checkDealDto(WorkFlowDto workFlowDto) throws Exception {
		boolean result = false;
		if ((workFlowDto.getCreate()) || (workFlowDto.getUpdate()) || (workFlowDto.getSubmit()) || (workFlowDto.getClose())) {
			result = true;
		}
		return result;
	}

	/**
	 * 查询本节点，如果提交的话，可以有多种
	 * @param modelNo String 模板号码
	 * @param nodeNo String 节点号码
	 * @throws Exception
	 * @return Collection
	 */
	public List<SwfPath> getNextSumbitNodes(String modelNo, String nodeNo) throws Exception { // 程序思路：
		// ---------------------------------------------------
		// 1。根据模板号码，节点号码查询出（从swfPath）数据
		// 2。排序注意,defaultFlag asc,priority asc
		// ---------------------------------------------------
		String conditions = "modelNo=" + modelNo + " and startNodeNo=" + nodeNo + " order by defaultFlag,priority";
		return this.getWorkFlowService().findModelPathNodes(conditions);
	}

	/**
	 * 查询本节点，根据险种和操作用户获得使用的模板号码
	 * @param modelNo String 模板号码
	 * @param nodeNo String 节点号码
	 * @throws Exception
	 * @return Collection
	 */
	public int getModelNoByRiskComCode(String RiskCode, String comCode) throws Exception {
		return this.getWorkFlowService().getModelNo(RiskCode, comCode);
	}

	/* ========================（工作流检查校验操作）结束======================== */
	/**
	 * 有原因的更换工作流上的处理原因
	 * @param flowID String
	 * @param logNo String
	 * @param handlerCode String
	 * @param reasion String
	 * @throws Exception
	 * @return WorkFlowDto
	 */
	public WorkFlowDto changeFlowNodeHandler(String flowID, String logNo, String handlerCode, String reasion, String handleDept) throws Exception {
		WorkFlowDto workFlowDto = new WorkFlowDto();
		int intLogNo = Integer.parseInt(DataUtils.nullToZero(logNo));
		SwfLog swfLog = this.getWorkFlowService().findNodeByPrimaryKey(flowID, intLogNo);
		if (swfLog == null) {
			return workFlowDto;
		}
		if (handlerCode.equals("")) {
			handlerCode = "0"; // 由於没有人的情况下，人员代码默认成0
		}
		// 默认handleDept为空，则不判断。
		if (!handleDept.equals("")) {
			if (swfLog.getHandleDept().equals(handleDept) && swfLog.getHandlerCode().equals(handlerCode))
				return workFlowDto;
		} else {
			if (swfLog.getHandlerCode().equals(handlerCode))
				return workFlowDto;
		}
		if (handlerCode.equals("0")) {
			swfLog.setHandlerCode(handlerCode);
			swfLog.setHandlerName("");
		} else {
			String handlerName = this.getCodeService().translateUserCode(handlerCode, true);
			swfLog.setHandlerCode(handlerCode);
			swfLog.setHandlerName(handlerName);
		}
		// }
		swfLog.setFlowInTime(new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND).toString());
		// 更新时间
		if (!handleDept.equals("")) {
			swfLog.setHandleDept(handleDept);
		}
		workFlowDto.setUpdateSwfLog(swfLog);
		workFlowDto.setUpdate(true);
		SwfNotion swfNotion = new SwfNotion();
		int lineNo = this.getWorkFlowService().getSwfNotionMaxLineNo(flowID, intLogNo);
		swfNotion.getId().setFlowID(flowID);
		swfNotion.getId().setLogNo(intLogNo);
		swfNotion.getId().setLineNo(lineNo);
		swfNotion.setHandleText(reasion);
		List<SwfNotion> swfNotionList = new ArrayList<SwfNotion>();
		swfNotionList.add(swfNotion);
		workFlowDto.setSwfNotionList(swfNotionList);
		return workFlowDto;
	}

	/* ========================（工作流查询操作）开始============================ */
	/**
	 * 根据条件显示新增定损调度任务清单
	 * @param httpServletRequest 返回给页面的request
	 * @param conditions 查询条件
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getScheduleAddCertainLossSwfLogList(HttpServletRequest httpServletRequest, String conditions) throws Exception {

		// 查询理赔节点状态信息
		// 得到多行主表信息
		// 目前只有为4的状态，以提交需要进行翻页
		// 每页显示的行数
		String recordPerPage = AppConfig.get("sysconst.ROWS_PERPAGE");
		String pageNo = httpServletRequest.getParameter("pageNo");
		if (pageNo == null || pageNo.trim().equals("")) {
			pageNo = "1";
		}
		int intRecordPerPage = Integer.parseInt(recordPerPage);
		int intPageNo = Integer.parseInt(pageNo);
		Page page = this.getWorkFlowService().findNodesByConditions(conditions, intPageNo, intRecordPerPage);
		SwfLog swfLog = new SwfLog();
		swfLog.setSwfLogList(page.getResult());
		httpServletRequest.setAttribute("page", page);
		httpServletRequest.setAttribute("swfLog", swfLog);
	}

	/**
	 * 检查该节点是否可以被提交，如果不能提交丢出理由原因
	 * @param swfLogDtoCurrent SwfLogDto 需要被检查的节点对象
	 * @throws Exception
	 */
	public int checkCompensateCount(String flowID, String nodeType) throws Exception {
		// 目前只检查单证的提交，如果它之前的立案没有做，定损，核损，人伤和人伤核损只要有一个没做完就不许提交
		// 单证节点判断思路
		// ----------------------------------------------------------
		// 1.首先判断该流程中是否存在如下的节点（立案，定损，核损，人伤和人伤核损）
		// 2.这些节点是不是处在活动状态，如果是的话，不允许提交
		// ----------------------------------------------------------
		String conditions = " flowid='" + flowID + "'" + " and  nodeType='" + nodeType + "'";
		List<SwfLog> swfLogList = this.getWorkFlowService().findNodesByConditions(conditions);
		// 结束单证的判断
		return swfLogList.size();
	}

	/**
	 * 查找符合条件的流程节点信息
	 * @param condition 条件
	 * @throws Exception
	 * @return List<SwfLog>
	 */
	public List<SwfLog> findStoreNodesByConditions(String condition) throws Exception {
		return this.getWorkFlowService().findByConditions(condition);
	}

	public SwfLogService getSwfLogService() {
		if (swfLogService == null) {
			return (SwfLogService) ServiceFactory.getService("swfLogService");
		}
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public SwfLogStoreService getSwfLogStoreService() {
		if (swfLogStoreService == null) {
			return (SwfLogStoreService) ServiceFactory.getService("swfLogStoreService");
		}
		return swfLogStoreService;
	}

	public void setSwfLogStoreService(SwfLogStoreService swfLogStoreService) {
		this.swfLogStoreService = swfLogStoreService;
	}

	public CodeService getCodeService() {
		if (codeService == null) {
			return (CodeService) ServiceFactory.getService("codeService");
		}
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrplregistrpolicyService getPrpLregistrpolicyService() {
		if (prpLregistrpolicyService == null) {
			return (PrplregistrpolicyService) ServiceFactory.getService("prpLregistrpolicyService");
		}
		return this.prpLregistrpolicyService;
	}

	public void setPrplregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public WorkFlowService getWorkFlowService() {
		if (workFlowService == null) {
			return (WorkFlowService) ServiceFactory.getService("workFlowService");
		}
		return workFlowService;
	}

	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public WorkFlowImageViewHelper getWorkFlowImageViewHelper() {
		if (workFlowImageViewHelper == null) {
			return (WorkFlowImageViewHelper) ServiceFactory.getService("workFlowImageViewHelper");
		}
		return workFlowImageViewHelper;
	}

	public void setWorkFlowImageViewHelper(WorkFlowImageViewHelper workFlowImageViewHelper) {
		this.workFlowImageViewHelper = workFlowImageViewHelper;
	}

	public ScheduleService getScheduleService() {
		if (scheduleService == null) {
			return (ScheduleService) ServiceFactory.getService("scheduleService");
		}
		return scheduleService;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		if (prpLcompensateService == null) {
			return (PrpLcompensateService) ServiceFactory.getService("prpLcompensateService");
		}
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpDuserService getPrpDuserService() {
		if (prpDuserService == null) {
			return (PrpDuserService) ServiceFactory.getService("prpDuserService");
		}
		return prpDuserService;
	}

	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}

	public PrpLclaimService getPrpLclaimService() {
		if (prpLclaimService == null) {
			return (PrpLclaimService) ServiceFactory.getService("prpLclaimService");
		}
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public void setPrpLregistrpolicyService(PrplregistrpolicyService prpLregistrpolicyService) {
		this.prpLregistrpolicyService = prpLregistrpolicyService;
	}

	public WorkFlowEngineViewHelper getWorkFlowEngineViewHelper() {
		if (workFlowEngineViewHelper == null) {
			return (WorkFlowEngineViewHelper) ServiceFactory.getService("workFlowEngineViewHelper");
		}
		return workFlowEngineViewHelper;
	}

	public void setWorkFlowEngineViewHelper(WorkFlowEngineViewHelper workFlowEngineViewHelper) {
		this.workFlowEngineViewHelper = workFlowEngineViewHelper;
	}

	public CompensateService getCompensateService() {
		if (compensateService == null) {
			return (CompensateService) ServiceFactory.getService("compensateService");
		}
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public PolicyService getPolicyService() {
		if (policyService == null) {
			return (PolicyService) ServiceFactory.getService("policyService");
		}
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		if (utiUwLevelService == null) {
			return (UtiUwLevelService) ServiceFactory.getService("utiUwLevelService");
		}
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public TaskDealViewHelper getTaskDealViewHelper() {
		if (taskDealViewHelper == null) {
			return (TaskDealViewHelper) ServiceFactory.getService("taskDealViewHelper");
		}
		return taskDealViewHelper;
	}

	public void setTaskDealViewHelper(TaskDealViewHelper taskDealViewHelper) {
		this.taskDealViewHelper = taskDealViewHelper;
	}

	public WorkFlowStatViewHelper getWorkFlowStatViewHelper() {
		return workFlowStatViewHelper;
	}

	public void setWorkFlowStatViewHelper(WorkFlowStatViewHelper workFlowStatViewHelper) {
		this.workFlowStatViewHelper = workFlowStatViewHelper;
	}

	public PrpCinsuredService getPrpCinsuredService() {
		return prpCinsuredService;
	}

	public void setPrpCinsuredService(PrpCinsuredService prpCinsuredService) {
		this.prpCinsuredService = prpCinsuredService;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public PowerService getPowerService() {
		return powerService;
	}

	public void setPowerService(PowerService powerService) {
		this.powerService = powerService;
	}

	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 START
	public PrpDautoDpLogService getPrpDautoDpLogService() {
		if (prpDautoDpLogService == null) {
			return (PrpDautoDpLogService) ServiceFactory.getService("prpDautoDpLogService");
		}
		return prpDautoDpLogService;
	}

	public void setPrpDautoDpLogService(PrpDautoDpLogService prpDautoDpLogService) {
		this.prpDautoDpLogService = prpDautoDpLogService;
	}
	//mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能 END
}
