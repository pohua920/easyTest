package com.sinosoft.claim.generalClaim.util;

import ins.framework.common.QueryRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.DataUtils;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.generalClaim.vo.GeneralClaimDto;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLgeneralClaimTaskLog;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.model.SwfLog;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLgeneralClaimTaskLogService;
import com.sinosoft.claim.schema.service.facade.PrpLgeneralClaimTaskService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.specailCase.service.facade.GeneralClaimService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.ParamUtils;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * <p>
 * Title: 通赔
 * </p>
 * <p>
 * Description: 通赔UIAction
 * </p>
 * @author 中科软
 * @version
 */
/**
 * @author Administrator
 *
 */
public class GeneralClaimViewHelper {
	/** 通赔服务 */
	private GeneralClaimService generalClaimService;
	/** 通赔服务 */
	private PrpLgeneralClaimTaskLogService prpLgeneralClaimTaskLogService;
	/**报案信息service*/
	private PrpLregistService prpLregistService;
	/**立案基本信息service*/
	private PrpLclaimService prpLclaimService;
	/**赔款计算书信息service*/
	private PrpLcompensateService prpLcompensateService;
	/**预赔登记信息service*/
	private PrpLprepayService prpLprepayService;
	/**重开赔案service*/
	private PrpLrecaseService prpLrecaseService;
	/**通赔任务信息service*/
	/**工作流日志service*/
	private SwfLogService swfLogService;
	/** 机构讯息 */
	private PrpDcompanyService prpDcompanyService;
	/** 通赔 */
	private PrpLgeneralClaimTaskService prpLgeneralClaimTaskService;

	/**
	 * 构造方法
	 */
	public GeneralClaimViewHelper() {

	}
	/**
	 *  检查是否可以做代查勘
	 * @param userDto
	 * @param registNo
	 * @param infoMap
	 * @return
	 * @throws Exception
	 */
	public List<String> checkGreneralClaim(UserDto userDto, String registNo, GeneralClaimDto generalClaimDto) throws Exception{
		List<String> claimStatusList = new ArrayList<String>();
		String actionType = generalClaimDto.getActionType();
		// 是否已报案注销
		PrpLregist prpLregist = generalClaimDto.getPrpLregist();
		if (prpLregist == null) {
			throw new Exception("查詢不到相關信息，請確認您輸入的報案號無誤！");
		}
		String message ="";
		if("Guide".equals(actionType)) {//代查勘只处理查勘节点任务。
			message = "不能再進行代查勘！";
		} else if("CaseTransfer".equals(actionType)) {//案件转移，查询一条记录即可。
			message = "不能再進行案件转移！";
		} else if("TaskTransfer".equals(actionType)){
			message = "不能再進行任務轉移！";
		}
//		claimStatusList.add(prpLregist);// 放入报案信息，用於页面展示
//		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLregist.getRiskCode());
		if (prpLregist != null && prpLregist.getCancelDate() != null) {
			claimStatusList.add("本案已於" + prpLregist.getCancelDate() + "註銷，"+message);
		}
		// 是否已立案注销或结案
		String claimNo = null;
		List<PrpLclaim> prpLclaimList = generalClaimDto.getPrpLclaimList();
		if (prpLclaimList != null && prpLclaimList.size() > 0) {
			PrpLcompensate prpLcompensate = null;
			PrpLprepay prplprepay = null;
			boolean cancelDateFlag = true;
			Date cancelDate = null;
			for(PrpLclaim prpLclaim : prpLclaimList){
				claimNo = prpLclaim.getClaimNo();
				//mantis：CLM0127，處理人員：DP0706，需求單編號：_新核心-案件移轉規則調整START
//				if (prpLclaim.getEndCaseDate() != null) {
//					claimStatusList.add("本案已於" + prpLclaim.getEndCaseDate() + "結案，"+message);
//				}
				//mantis：CLM0127，處理人員：DP0706，需求單編號：_新核心-案件移轉規則調整END
				if(!"TaskTransfer".equals(actionType)){
					message = "不能再進行任務轉移！";
					// 是否存在核赔通过的计算书
					//mantis：CLM0127，處理人員：DP0706，需求單編號：_新核心-案件移轉規則調整START
					//因案件已結案後無法轉移至分公司，調整功能開放皆可轉移
//					List<PrpLcompensate> prpLcompensateList = prpLcompensateService.findByClaimNo(claimNo);
//					List<PrpLprepay> prplprepayList = prpLprepayService.findByClaimNo(claimNo);
//					if (prpLcompensateList != null && prpLcompensateList.size() > 0) {
//						for (int i = 0; i < prpLcompensateList.size(); i++) {
//							prpLcompensate = prpLcompensateList.get(i);
//							if ("1".equals(prpLcompensate.getUnderWriteFlag()) || "3".equals(prpLcompensate.getUnderWriteFlag())) {
//								claimStatusList.add("本案存在核賠通過的計算書（" + prpLcompensate.getCompensateNo() + "），"+message);
//							}
//						}
//					}
//					if (prplprepayList != null && prplprepayList.size() > 0) {
//						for (int i = 0; i < prplprepayList.size(); i++) {
//							prplprepay = prplprepayList.get(i);
//							if ("1".equals(prplprepay.getUnderWriteFlag()) || "3".equals(prplprepay.getUnderWriteFlag())) {
//								claimStatusList.add("本案存在核賠通過的計算書（" + prplprepay.getPreCompensateNo() + "），"+message);
//							}
//						}
//					}
					//mantis：CLM0127，處理人員：DP0706，需求單編號：_新核心-案件移轉規則調整END
				}
				// 是否重开赔案
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.claimNo", claimNo);
				List<PrpLrecase> prpLrecaseList = prpLrecaseService.findPrpLrecase(queryRule);
				//mantis：CLM0127，處理人員：DP0706，需求單編號：_新核心-案件移轉規則調整START
//				if (prpLrecaseList != null && prpLrecaseList.size() > 0) {
//					claimStatusList.add("本案屬於重開賠案，"+message);
//				}
				//mantis：CLM0127，處理人員：DP0706，需求單編號：_新核心-案件移轉規則調整END
				if(prpLclaim.getCancelDate()==null){
					cancelDateFlag =  false;
				}else{
					cancelDate = new DateTime(prpLclaim.getCancelDate());
				}
			}
			if (cancelDateFlag) {
				claimStatusList.add("本案已於" + cancelDate + "註銷，"+message);
			}
		}
		// 是否存在活动节点（本省）
		String conditions = "";
		if("Guide".equals(actionType)) {//代查勘只处理查勘节点任务。
			conditions += "REGISTNO = '" + registNo +"' AND NODETYPE ='check' AND NODESTATUS IN ('0','2','3') ";
//			if(!ConstantCodes.CLASSCODE_Q.equals(strRiskType)) {//仅开放火险
//				claimStatusList.add("本案不是火險案件，不能進行代查勘！");
//			}
		} else if("CaseTransfer".equals(actionType)) {//案件转移，查询一条记录即可。
			conditions += " REGISTNO = '" + registNo + "' and NODETYPE ='regis' ";
		} else if("TaskTransfer".equals(actionType)){
			conditions += "REGISTNO = '" + registNo +"' AND NODETYPE not in ('compe','veric') AND NODESTATUS IN ('0','2','3') ";
		}
		//业务权限未添加
		conditions += " ORDER BY NODENO";
//		List<SwfLog> swflogDtoList = swfLogService.findByConditions(conditions);
		int count = swfLogService.getCount(conditions);
		if (count < 1) {
			claimStatusList.add("本案目前沒有可操作的節點，"+message);
		}
		return claimStatusList;
	}
	/**
	 * 查询代查勘的节点
	 * @param userDto
	 * @param registNo
	 * @param infoMap
	 * @return
	 * @throws Exception
	 */
	public List<SwfLog> findSwflog(UserDto userDto, String registNo, GeneralClaimDto generalClaimDto)throws Exception{
		// 是否存在活动节点（本省）
		String actionType = generalClaimDto.getActionType();
		String conditions = "";
		if("Guide".equals(actionType)) {//代查勘只处理查勘节点任务。
			conditions += "REGISTNO = '" + registNo +"' AND NODETYPE ='check' AND NODESTATUS IN ('0','2','3') ";
		} else if("CaseTransfer".equals(actionType)) {//案件转移，查询一条记录即可。
			conditions += "REGISTNO = '" + registNo + "' and NODETYPE ='regis' ";
		} else if("TaskTransfer".equals(actionType)){
			conditions += "REGISTNO = '" + registNo +"' AND NODETYPE not in ('compe','veric') AND NODESTATUS IN ('0','2','3') ";
		}
		//业务权限未添加
		conditions += " ORDER BY NODENO";
		List<SwfLog> swflogDtoList = swfLogService.findByConditions(conditions);
		for(SwfLog swfLog : swflogDtoList){
			if("0".equals(swfLog.getHandlerCode())){
				swfLog.setHandlerCode("");
			}
			swfLog.setComName(prpDcompanyService.findByPrimaryKey(swfLog.getComCode()).getComCName());
		}
		return swflogDtoList;
	}
	/**
	 * 封装通赔接收查询的条件
	 * @param request
	 * @throws Exception
	 */
//	public String generateReceiveConditions(HttpServletRequest request) throws Exception {
//		ParamUtils paramUtils = new ParamUtils(request);
//		String conditions = " 1=1";
//
//		String registNo = (paramUtils.getParameter("RegistNo")).trim();
//		String policyNo = (paramUtils.getParameter("PolicyNo")).trim();
//		String giveComCode = (paramUtils.getParameter("GiveComCode")).trim();
//		String riskCode = (paramUtils.getParameter("RiskCode")).trim();
//		String startOperateDate = (paramUtils.getParameter("StartOperateDate")).trim();
//		String endOperateDate = (paramUtils.getParameter("EndOperateDate")).trim();
//
//		if (!"".equals(registNo)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASK.REGISTNO", registNo);
//		}
//		if (!"".equals(policyNo)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASK.POLICYNO", policyNo);
//		}
//		if (!"".equals(giveComCode)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASK.GIVECOMCODE", giveComCode);
//		}
//		if (!"".equals(riskCode)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASK.RISKCODE", riskCode);
//		}
//
//		if (!"".equals(startOperateDate)) {
//			conditions += " AND PRPLGENERALCLAIMTASK.GIVETIME >=TO_DATE('" + startOperateDate + "','yyyy-mm-dd hh24:mi:ss')";
//		}
//		if (!"".equals(endOperateDate)) {
//			conditions += " AND PRPLGENERALCLAIMTASK.GIVETIME <=TO_DATE('" + endOperateDate + "','yyyy-mm-dd hh24:mi:ss')";
//		}
//
//		return conditions;
//	}

	/**
	 * 封装通赔收回查询的条件
	 * @param request
	 * @throws Exception
	 */
//	public String generateRegainConditions(HttpServletRequest request) throws Exception {
//		ParamUtils paramUtils = new ParamUtils(request);
//		String conditions = " 1=1";
//
//		String registNo = (paramUtils.getParameter("RegistNo")).trim();
//		String policyNo = (paramUtils.getParameter("PolicyNo")).trim();
//		String giveComCode = (paramUtils.getParameter("ReceiveComCode")).trim();
//		String riskCode = (paramUtils.getParameter("RiskCode")).trim();
//		String startOperateDate = (paramUtils.getParameter("StartOperateDate")).trim();
//		String endOperateDate = (paramUtils.getParameter("EndOperateDate")).trim();
//
//		if (!"".equals(registNo)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASK.REGISTNO", registNo);
//		}
//		if (!"".equals(policyNo)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASK.POLICYNO", policyNo);
//		}
//		if (!"".equals(giveComCode)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASK.RECEIVECOMCODE", giveComCode);
//		}
//		if (!"".equals(riskCode)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASK.RISKCODE", riskCode);
//		}
//
//		if (!"".equals(startOperateDate)) {
//			conditions += " AND PRPLGENERALCLAIMTASK.GIVETIME >=TO_DATE('" + startOperateDate + "','yyyy-mm-dd hh24:mi:ss')";
//		}
//		if (!"".equals(endOperateDate)) {
//			conditions += " AND PRPLGENERALCLAIMTASK.GIVETIME <=TO_DATE('" + endOperateDate + "','yyyy-mm-dd hh24:mi:ss')";
//		}
//
//		return conditions;
//	}

	/**
	 * 封装通赔历史查询的条件
	 * @param request
	 * @throws Exception
	 */
//	public String generateHistoryConditions(HttpServletRequest request) throws Exception {
//		ParamUtils paramUtils = new ParamUtils(request);
//		String conditions = " 1=1";
//
//		String registNo = (paramUtils.getParameter("RegistNo")).trim();
//		String policyNo = (paramUtils.getParameter("PolicyNo")).trim();
//		String generalType = (paramUtils.getParameter("generalType")).trim();
//		String comCode = (paramUtils.getParameter("ComCode")).trim();
//		String riskCode = (paramUtils.getParameter("RiskCode")).trim();
//		String startOperateDate = (paramUtils.getParameter("StartOperateDate")).trim();
//		String endOperateDate = (paramUtils.getParameter("EndOperateDate")).trim();
//
//		if (!"".equals(registNo)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASKLOG.REGISTNO", registNo);
//		}
//		if (!"".equals(policyNo)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASKLOG.POLICYNO", policyNo);
//		}
//		if (!"".equals(riskCode)) {
//			conditions += SqlUtils.convertString("PRPLGENERALCLAIMTASKLOG.RISKCODE", riskCode);
//		}
//
//		if (!"".equals(startOperateDate)) {
//			conditions += " AND PRPLGENERALCLAIMTASKLOG.GIVETIME >=TO_DATE('" + startOperateDate + "','yyyy-mm-dd hh24:mi:ss')";
//		}
//		if (!"".equals(endOperateDate)) {
//			conditions += " AND PRPLGENERALCLAIMTASKLOG.GIVETIME <=TO_DATE('" + endOperateDate + "','yyyy-mm-dd hh24:mi:ss')";
//		}
//		if (!"".equals(comCode)) {
//			if ("all".equals(generalType)) {
//				conditions += " and (PRPLGENERALCLAIMTASKLOG.GIVECOMCODE = '" + comCode + "' OR PRPLGENERALCLAIMTASKLOG.RECEIVECOMCODE = '" + comCode + "')";
//			} else if ("give".equals(generalType)) {
//				conditions += " and PRPLGENERALCLAIMTASKLOG.RECEIVECOMCODE = '" + comCode + "'";
//			} else {
//				conditions += " and PRPLGENERALCLAIMTASKLOG.GIVECOMCODE = '" + comCode + "'";
//			}
//		}
//		return conditions;
//	}

	/**
	 * 理賠任務轉移,案件转移数据收集,代查勘数据,数据整理
	 * @param request
	 * @throws Exception
	 */
	public void guideDtoToView(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");
		GeneralClaimDto generalClaimDto = new GeneralClaimDto();
		String actionType = (String)request.getAttribute("actionType");
		generalClaimDto.setActionType(actionType);
		String registNo = request.getParameter("registNo");
		if (registNo == null || "".equals(registNo)) {
			throw new UserException(-98, -1007, this.getClass().getName(), "您輸入的備案號有誤，請重新輸入！");
		} 
		registNo = registNo.trim();
		// 根据报案号查询该案状态，只有满足要求的案件才可以进行通赔
		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		generalClaimDto.setPrpLregist(prpLregist);
		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(registNo);
		generalClaimDto.setPrpLclaimList(prpLclaimList);
		List<String> claimStatusList = this.checkGreneralClaim(userDto, registNo, generalClaimDto);
		generalClaimDto.setClaimStatusList(claimStatusList);
		List<SwfLog> swflogDtoList = this.findSwflog(userDto, registNo, generalClaimDto);
		
		generalClaimDto.setSwflogList(swflogDtoList);
		//获取委托记录
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("registNo", prpLregist.getRegistNo());
		queryRule.addEqual("actionType", actionType);
		queryRule.addAscOrder("serialNo");
		//只有代查勘委託時候才取歷史記錄。
		List<PrpLgeneralClaimTaskLog> prpLgeneralClaimTaskLogList =  prpLgeneralClaimTaskLogService.findPrpLgeneralClaimTaskLog(queryRule);
		generalClaimDto.setPrpLgeneralClaimTaskLogList(prpLgeneralClaimTaskLogList);

		request.setAttribute("receiveComcode", userDto.getComCode());
		request.setAttribute("prpLregist", prpLregist);
		request.setAttribute("swflogDtoList", swflogDtoList);
		request.setAttribute("claimStatusList", claimStatusList);
		request.setAttribute("prpLgeneralClaimTaskLogList", prpLgeneralClaimTaskLogList);
		request.setAttribute("actionType", actionType);
		request.setAttribute("RISKCODE_DAZ", ConstantCodes.RISKCODE_DAZ);
		request.setAttribute("generalClaimDto", generalClaimDto);
	}
	
	/**
	 *  检查是否可以做代查勘
	 * @param userDto
	 * @param registNo
	 * @param infoMap
	 * @return
	 * @throws Exception
	 */
//	public List<String> checkGreneralClaim(UserDto userDto, String registNo, Map<String,?> infoMap) throws Exception{
//		List<String> claimStatusList = new ArrayList<String>();
//		String editType = infoMap.get("editType").toString();
//		// 是否已报案注销
//		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
//		if (prpLregist == null) {
//			throw new Exception("查詢不到相關信息，請確認您輸入的報案號無誤！");
//		}
////		claimStatusList.add(prpLregist);// 放入报案信息，用於页面展示
////		String strRiskType = this.getCodeService().translateRiskCodetoRiskType(prpLregist.getRiskCode());
//		if (prpLregist != null && prpLregist.getCancelDate() != null) {
//			claimStatusList.add("本案已於" + prpLregist.getCancelDate() + "註銷，不能再進行代查勘！");
//		}
//		// 是否已立案注销或结案
//		String claimNo = null;
//		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(registNo);
//		if (prpLclaimList != null && prpLclaimList.size() > 0) {
//			PrpLcompensate prpLcompensate = null;
//			PrpLprepay prplprepay = null;
//			boolean cancelDateFlag = true;
//			Date cancelDate = null;
//			for(PrpLclaim prpLclaim : prpLclaimList){
//				claimNo = prpLclaim.getClaimNo();
//				if (prpLclaim.getEndCaseDate() != null) {
//					claimStatusList.add("本案已於" + prpLclaim.getEndCaseDate() + "結案，不能再進行代查勘！");
//				}
//				// 是否存在核赔通过的计算书
//				List<PrpLcompensate> prpLcompensateList = prpLcompensateService.findByClaimNo(claimNo);
//				List<PrpLprepay> prplprepayList = prpLprepayService.findByClaimNo(claimNo);
//				if (prpLcompensateList != null && prpLcompensateList.size() > 0) {
//					for (int i = 0; i < prpLcompensateList.size(); i++) {
//						prpLcompensate = prpLcompensateList.get(i);
//						if ("1".equals(prpLcompensate.getUnderWriteFlag()) || "3".equals(prpLcompensate.getUnderWriteFlag())) {
//							claimStatusList.add("本案存在核賠通過的計算書（" + prpLcompensate.getCompensateNo() + "），不能再進行代查勘！");
//						}
//					}
//				}
//				if (prplprepayList != null && prplprepayList.size() > 0) {
//					for (int i = 0; i < prplprepayList.size(); i++) {
//						prplprepay = prplprepayList.get(i);
//						if ("1".equals(prplprepay.getUnderWriteFlag()) || "3".equals(prplprepay.getUnderWriteFlag())) {
//							claimStatusList.add("本案存在核賠通過的計算書（" + prplprepay.getPreCompensateNo() + "），不能再進行代查勘！");
//						}
//					}
//				}
//				// 是否重开赔案
//				QueryRule queryRule = QueryRule.getInstance();
//				queryRule.addEqual("id.claimNo", claimNo);
//				List<PrpLrecase> prpLrecaseList = prpLrecaseService.findPrpLrecase(queryRule);
//				if (prpLrecaseList != null && prpLrecaseList.size() > 0) {
//					claimStatusList.add("本案屬於重開賠案，不能再進行代查勘！");
//				}
//				if(prpLclaim.getCancelDate()==null){
//					cancelDateFlag =  false;
//				}else{
//					cancelDate = new DateTime(prpLclaim.getCancelDate());
//				}
//			}
//			if (cancelDateFlag) {
//				claimStatusList.add("本案已於" + cancelDate + "註銷，不能再進行代查勘！");
//			}
//		}
//		// 是否存在活动节点（本省）
//		String conditions = "";
//		if(editType.equals("guide")) {//代查勘只处理查勘节点任务。
//			conditions += "NODETYPE ='check' AND REGISTNO = '" + registNo +"' AND NODESTATUS IN ('0','2','3') ";
////			if(!ConstantCodes.CLASSCODE_Q.equals(strRiskType)) {//仅开放火险
////				claimStatusList.add("本案不是火險案件，不能進行代查勘！");
////			}
//		} else if(editType.equals("CaseTransfer")) {//案件转移，查询一条记录即可。
//			conditions += "NODETYPE ='regis' AND REGISTNO = '" + registNo + "' ";
//		} else {
//			conditions += "NODETYPE not in ('compe') AND REGISTNO = '" + registNo +"' AND NODESTATUS IN ('0','2','3') ";
//		}
//		//业务权限未添加
//		conditions += " ORDER BY NODENO";
////		List<SwfLog> swflogDtoList = swfLogService.findByConditions(conditions);
//		int count = swfLogService.getCount(conditions);
//		if (count < 1) {
//			claimStatusList.add("本案目前沒有可操作的查勘節點，不能進行代查勘！");
//		}
//		return claimStatusList;
//	}
	/**
	 * 查询代查勘的节点
	 * @param userDto
	 * @param registNo
	 * @param infoMap
	 * @return
	 * @throws Exception
	 */
//	public List<SwfLog> getSwflog(UserDto userDto, String registNo, Map<String,?> infoMap)throws Exception{
//		// 是否存在活动节点（本省）
//		String editType = infoMap.get("editType").toString();
//		String conditions = "";
//		if(editType.equals("guide")) {//代查勘只处理查勘节点任务。
//			conditions += "REGISTNO = '" + registNo +"' AND NODETYPE ='check' AND NODESTATUS IN ('0','2','3') ";
//		} else if(editType.equals("CaseTransfer")) {//案件转移，查询一条记录即可。
//			conditions += "REGISTNO = '" + registNo + "' and NODETYPE ='regis' ";
//		} else {
//			conditions += "REGISTNO = '" + registNo +"' AND NODETYPE not in ('compe') AND NODESTATUS IN ('0','2','3') ";
//		}
//		//业务权限未添加
//		conditions += " ORDER BY NODENO";
//		List<SwfLog> swflogDtoList = swfLogService.findByConditions(conditions);
//		return swflogDtoList;
//	}
	/**
	 * 通赔委托提交
	 * @param httpServletRequest
	 * @throws Exception
	 * @return String
	 */
	public GeneralClaimDto giveInsertViewToDto(HttpServletRequest httpServletRequest) throws Exception {
//		HttpSession session = httpServletRequest.getSession();
//		UserDto userDto = (UserDto) session.getAttribute("user");
		GeneralClaimDto generalClaimDto = new GeneralClaimDto();
//		String registNo = httpServletRequest.getParameter("registNo");
//		String receiveComcode = httpServletRequest.getParameter("receiveComcode");
//		httpServletRequest.setAttribute("swflogList", generateSwflogList(httpServletRequest));
		String actionType = DataUtils.dbNullToEmpty((String)httpServletRequest.getAttribute("actionType"));
		generalClaimDto.setActionType(actionType);
		generalClaimDto.setPrpLgeneralClaimTaskLogList(this.generateClaimTaskList(httpServletRequest,generalClaimDto));
		List<SwfLog> swfLogDtoList = this.generateSwflogList(httpServletRequest,generalClaimDto);
		generalClaimDto.setSwflogList(swfLogDtoList);
		this.generatePrpLclaim(httpServletRequest,generalClaimDto);
//		generalClaimService.giveInsert(swfLogDtoList,prpLgeneralClaimTaskLog);
//		userDto.setUserMessage("代查勘委托任務提交成功");
//		httpServletRequest.setAttribute("user", userDto);
		return generalClaimDto;
	}
	/**
	 * 设置理赔处理人员
	 * @param httpServletRequest
	 * @param generalClaimDto
	 * @return
	 * @throws Exception
	 */
	private List<PrpLclaim> generatePrpLclaim(HttpServletRequest httpServletRequest,GeneralClaimDto generalClaimDto) throws Exception{
		String actionType = DataUtils.dbNullToEmpty((String)httpServletRequest.getAttribute("actionType"));
		String[] handlerCode = (httpServletRequest.getParameterValues("prpLgeneralClaimTaskLogReceiveOperatorCode"));
		String[] handlerName = (httpServletRequest.getParameterValues("prpLgeneralClaimTaskLogReceiveOperatorName"));
		List<PrpLclaim> prpLclaimList = null;
		if("CaseTransfer".equals(actionType)&&handlerCode!=null) {
			String registNo = (httpServletRequest.getParameter("registNo")).trim();
			prpLclaimList = prpLclaimService.findByRegistNo(registNo);
			if(prpLclaimList!=null&&prpLclaimList.size()>0){
				for(PrpLclaim prpLclaim : prpLclaimList){
					prpLclaim.setHandlerCode(handlerCode[0]);
					prpLclaim.setHandlerName(handlerName[0]);
				}
			}
			generalClaimDto.setPrpLclaimList(prpLclaimList);
		}
		return prpLclaimList;
	}
	
	private List<SwfLog> generateSwflogList(HttpServletRequest httpServletRequest,GeneralClaimDto generalClaimDto) throws Exception {
 		List<SwfLog> resultList = new ArrayList<SwfLog>();
		ParamUtils paramUtils = new ParamUtils(httpServletRequest);
		String actionType = DataUtils.dbNullToEmpty((String)httpServletRequest.getAttribute("actionType"));
//		String registNo = (paramUtils.getParameter("registNo")).trim();
		String[] flowID = (paramUtils.getParameterValues("flowId"));
		String[] logNo = (paramUtils.getParameterValues("logNo"));
		String[] comCode = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogComCode"));
//		String[] comName = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogReceiveComName"));
		String[] handleDept = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogReceiveComCode"));
		String[] deptName = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogReceiveComName"));
		String[] handlerCode = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogReceiveOperatorCode"));
		String[] handlerName = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogReceiveOperatorName"));
		// 3.工作流数据
		SwfLog swfLogDto = null;
		if (flowID!=null&&flowID.length > 0) {
			for (int i = 0; i < flowID.length; i++) {
				if("CaseTransfer".equals(actionType)) {
					if(comCode!=null){
						generalClaimDto.setComCode(handleDept[i]);
						generalClaimDto.setFlowId(flowID[i]);
						swfLogDto = swfLogService.findSwfLog(flowID[i], Integer.parseInt(logNo[i]));
						if (CommonUtils.isEmpty(handlerCode[i])) {
							handlerCode[i] = "0";
						}
						swfLogDto.setHandlerCode(handlerCode[i]);
						swfLogDto.setHandlerName(handlerName[i]);
						resultList.add(swfLogDto);
//						swfLogService.updateComCode(flowID[i],comCode[i]);
					}
				} else if("TaskTransfer".equals(actionType)){
					swfLogDto = swfLogService.findSwfLog(flowID[i], Integer.parseInt(logNo[i]));
					if (!"compe".equals(swfLogDto.getNodeType())) {// 理算节点不置操作员，因为流程中的理算环节实际对应的是计算书节点，所以理算节点无法放弃任务。
						if(!CommonUtils.isEmpty(handleDept)) {
							swfLogDto.setHandleDept(handleDept[i]);
							swfLogDto.setDeptName(deptName[i]);
						}
						if(!CommonUtils.isEmpty(comCode)) {
							swfLogDto.setComCode(comCode[i]);
						}
						swfLogDto.setHandlerCode(handlerCode[i]);
						swfLogDto.setHandlerName(handlerName[i]);
						if (CommonUtils.isEmpty(handlerCode[i])) {
							handlerCode[i] = "0";
						}
					}
					resultList.add(swfLogDto);
				}
			}
		}
		
//		// 3.工作流数据
//		String strNodeName = "";
//		String strHandlerCode = "接收人列表：";
//		SwfLog swfLogDto = null;
//		SwfLog swfLogDtoTemp = null;
//		if (!CommonUtils.isEmpty(swfLogDtoList)) {
//			remark += "代查勘類型：" +editType +"；接收機構列表：";
//			for (int i = 0; i < swfLogDtoList.size(); i++) {
//				swfLogDtoTemp = swfLogDtoList.get(i);
//				if("CaseTransfer".equals(editType)) {
////					receiveComcode = swfLogDtoTemp.getComCode();
//					remark+= swfLogDtoTemp.getComCode() + ",";
//					strNodeName += "ALL,";
//					swfLogService.updateComCode(swfLogDtoTemp.getId().getFlowID(), swfLogDtoTemp.getComCode());
//				} else {
//					swfLogDto = swfLogService.findSwfLog(swfLogDtoTemp.getId().getFlowID(), swfLogDtoTemp.getId().getLogNo());
//					if (!"compe".equals(swfLogDto.getNodeName())) {// 理算节点不置操作员，因为流程中的理算环节实际对应的是计算书节点，所以理算节点无法放弃任务。
//						swfLogDto.setHandleDept(swfLogDtoTemp.getHandleDept());
//						swfLogDto.setDeptName(swfLogDtoTemp.getDeptName());
//						swfLogDto.setHandlerName(swfLogDtoTemp.getHandlerName());
//						swfLogDto.setHandlerCode(swfLogDtoTemp.getHandlerCode());
//						swfLogService.update(swfLogDto);
//					}
////					strNodeName += swfLogDto.getNodeName() + ",";
//					strHandlerCode += swfLogDto.getHandlerCode() + ",";
////					receiveComcode = swfLogDtoTemp.getHandleDept();
//					remark+= swfLogDtoTemp.getHandleDept() + ",";
//				}
//			}
//			remark+= strHandlerCode;
//		}
		return resultList;
	}
	private List<PrpLgeneralClaimTaskLog> generateClaimTaskList(HttpServletRequest httpServletRequest,GeneralClaimDto generalClaimDto) throws Exception {
		ParamUtils paramUtils = new ParamUtils(httpServletRequest);
		String registNo = (paramUtils.getParameter("registNo")).trim();
//		String[] flowID = (paramUtils.getParameterValues("flowId"));
//		String[] logNo = (paramUtils.getParameterValues("logNo"));
//		String[] comCode = (paramUtils.getParameterValues("comCode"));
		String[] prpLgeneralClaimTaskLogCurrentNodeType = httpServletRequest.getParameterValues("prpLgeneralClaimTaskLogCurrentNodeType");
		String actionType = httpServletRequest.getParameter("actionType");
		String[] prpLgeneralClaimTaskLogReceiveComCode = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogReceiveComCode"));
		String[] prpLgeneralClaimTaskLogReceiveComName = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogReceiveComName"));
		String[] prpLgeneralClaimTaskLogReceiveOperatorCode = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogReceiveOperatorCode"));
		String[] prpLgeneralClaimTaskLogReceiveOperatorName = (paramUtils.getParameterValues("prpLgeneralClaimTaskLogReceiveOperatorName"));
		String prpLgeneralClaimTaskLogExtendString1 = (paramUtils.getParameter("prpLgeneralClaimTaskLogExtendString1"));
		String[] prpLgeneralClaimTaskLogComCode = httpServletRequest.getParameterValues("prpLgeneralClaimTaskLogComCode");
		String[] prpLgeneralClaimTaskLogComName = httpServletRequest.getParameterValues("prpLgeneralClaimTaskLogComName");
		String[] prpLgeneralClaimTaskLogCurrentNode = httpServletRequest.getParameterValues("prpLgeneralClaimTaskLogCurrentNode");
		HttpSession session = httpServletRequest.getSession();
		UserDto userDto = (UserDto) session.getAttribute("user");
		String giveComCode = userDto.getComCode();
		String giveComName = prpDcompanyService.query(giveComCode).getComCName();
		String remark = null;
		String strHandlerCode = null;
		// 1.报案数据
		PrpLregist prpLregist = prpLregistService.findPrpLregist(registNo);
		// 2.立案数据
		String claimNo = "";
		int k = prpLgeneralClaimTaskLogCurrentNode.length-1;
		int s = prpLgeneralClaimTaskLogReceiveComCode.length-1;
		List<PrpLclaim> prpLclaimList = prpLclaimService.findByRegistNo(registNo);
		if (prpLclaimList != null && prpLclaimList.size() > 0) {
			claimNo = prpLclaimList.get(0).getClaimNo();
		}
		List<PrpLgeneralClaimTaskLog> prpLgeneralClaimTaskLogList = new ArrayList<PrpLgeneralClaimTaskLog>();
		if(prpLgeneralClaimTaskLogCurrentNodeType!=null){
			PrpLgeneralClaimTaskLog prpLgeneralClaimTask = null;
			if("CaseTransfer".equals(actionType)){
				String conditions = " REGISTNO = '" + registNo + "' and nodestatus in('0','2','3') ";
				List<SwfLog> swflogList= swfLogService.findByConditions(conditions);
				for (SwfLog swfLog : swflogList) {
					swfLog.setHandlerCode(prpLgeneralClaimTaskLogReceiveOperatorCode[s]);
					swfLog.setHandlerName(prpLgeneralClaimTaskLogReceiveOperatorName[s]);
					swfLog.setHandleDept(prpLgeneralClaimTaskLogReceiveComCode[s]);
					swfLog.setDeptName(prpLgeneralClaimTaskLogReceiveComName[s]);
					if (CommonUtils.isEmpty(prpLgeneralClaimTaskLogReceiveOperatorCode[s])) {
						prpLgeneralClaimTaskLogReceiveOperatorCode[s] = "0";
					}
					swfLogService.saveOrUpdate(swfLog);
				}
			}
			int i = 0;
			//委托查勘有多条
			if("Guide".equals(actionType)){
				i = 1;
			}
			for(;i<prpLgeneralClaimTaskLogCurrentNodeType.length;i++){
				remark = "代查勘類型：" +actionType +"；接收機構列表：";
				strHandlerCode = "接收人列表：";
				prpLgeneralClaimTask = new PrpLgeneralClaimTaskLog();
				prpLgeneralClaimTask.setSerialNo(prpLgeneralClaimTaskService.getSeqNextVal("PrpLgeneralClaimTask"));
				prpLgeneralClaimTask.setRegistNo(prpLregist.getRegistNo());
				prpLgeneralClaimTask.setClaimNo(claimNo);
				prpLgeneralClaimTask.setPolicyNo(prpLregist.getPolicyNo());
				prpLgeneralClaimTask.setRiskCode(prpLregist.getRiskCode());
				prpLgeneralClaimTask.setCurrentNode(prpLgeneralClaimTaskLogCurrentNode[i]);
				prpLgeneralClaimTask.setCurrentNodeType(prpLgeneralClaimTaskLogCurrentNodeType[i]);
				prpLgeneralClaimTask.setGiveComCode(giveComCode);
				prpLgeneralClaimTask.setGiveComName(giveComName);
				prpLgeneralClaimTask.setGiveOperatorCode(userDto.getUserCode());
				prpLgeneralClaimTask.setGiveOperatorName(userDto.getUserName());
				prpLgeneralClaimTask.setGiveTime(new DateTime(new Date(), DateTime.YEAR_TO_SECOND));
//				prpLgeneralClaimTask.setNodeStatus("1");
				prpLgeneralClaimTask.setComCode(CommonUtils.getValue(prpLgeneralClaimTaskLogComCode, i));
				prpLgeneralClaimTask.setComName(CommonUtils.getValue(prpLgeneralClaimTaskLogComName,i));
				prpLgeneralClaimTask.setFlag("");
				prpLgeneralClaimTask.setExtendString1(prpLgeneralClaimTaskLogExtendString1);
				prpLgeneralClaimTask.setReceiveComCode(prpLgeneralClaimTaskLogReceiveComCode[i]);
				prpLgeneralClaimTask.setReceiveComName(prpLgeneralClaimTaskLogReceiveComName[i]);
				prpLgeneralClaimTask.setReceiveOperatorCode(CommonUtils.getValue(prpLgeneralClaimTaskLogReceiveOperatorCode,i));
				prpLgeneralClaimTask.setReceiveOperatorName(CommonUtils.getValue(prpLgeneralClaimTaskLogReceiveOperatorName,i));
				prpLgeneralClaimTask.setReceiveTime(new DateTime(new Date(), DateTime.YEAR_TO_SECOND));
				prpLgeneralClaimTask.setNodeStatus("0");
				prpLgeneralClaimTask.setActionType(actionType);
				strHandlerCode += prpLgeneralClaimTask.getReceiveOperatorCode() + ",";
				remark+= prpLgeneralClaimTask.getCurrentNode() + ",";
				remark+= prpLgeneralClaimTask.getReceiveComCode() + ",";
				remark+= strHandlerCode;
				prpLgeneralClaimTask.setRemark(remark);
				prpLgeneralClaimTaskLogList.add(prpLgeneralClaimTask);
			}
		}
		return prpLgeneralClaimTaskLogList;
	}
	/**
	 * 查询代查勘用户，如果是带查勘用户，设置为暂存
	 * @param request
	 * @param registNo
	 * @param editType
	 * @return
	 * @throws Exception
	 */
	public boolean checkGuideUser(HttpServletRequest request,String registNo,String editType)throws Exception {
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("registNo", registNo);
		queryRule.addEqual("actionType", "Guide");
		List<PrpLgeneralClaimTaskLog> prpLgeneralClaimTaskLogList = prpLgeneralClaimTaskLogService.findPrpLgeneralClaimTaskLog(queryRule);
		boolean checkGuideUser = false;
		StringBuffer messages = new StringBuffer("");
		if(!CommonUtils.isEmpty(prpLgeneralClaimTaskLogList)){
			for(PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog : prpLgeneralClaimTaskLogList){
				if(!"4".equals(prpLgeneralClaimTaskLog.getNodeStatus())){
					if(user.getUserCode().equals(prpLgeneralClaimTaskLog.getReceiveOperatorCode())){
						prpLgeneralClaimTaskLog.setNodeStatus("2");
						checkGuideUser = true;
						prpLgeneralClaimTaskLogService.update(prpLgeneralClaimTaskLog);
					}else{
						messages.append("用戶：").append(prpLgeneralClaimTaskLog.getReceiveOperatorName());
						if("0".equals(prpLgeneralClaimTaskLog.getNodeStatus())){
							messages.append(" 查勘任務未處理！\n");
						}else if("2".equals(prpLgeneralClaimTaskLog.getNodeStatus())){
							messages.append(" 查勘任務正在處理！\n");
						}else if("4".equals(prpLgeneralClaimTaskLog.getNodeStatus())){
							messages.append(" 查勘任務處理完成！\n");
						}
					}
				}
			}
		}
		request.setAttribute("checkGuideUser",checkGuideUser);
		if(checkGuideUser){
			request.setAttribute("checkGuideMessages","");
		}else{
			messages.append("是否提交查勘任務？");
			request.setAttribute("checkGuideMessages",messages.toString());
		}
		return checkGuideUser;
	}
	/**
	 * 查询代查勘用户，如果是带查勘用户，设置为暂存
	 * @param request
	 * @param registNo
	 * @param editType
	 * @return
	 * @throws Exception
	 */
	public boolean saveGuideUser(HttpServletRequest request,String registNo,String editType)throws Exception {
		HttpSession session = request.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("registNo", registNo);
		queryRule.addEqual("actionType", "Guide");
		queryRule.addEqual("receiveOperatorCode", user.getUserCode());
		List<PrpLgeneralClaimTaskLog> prpLgeneralClaimTaskLogList = prpLgeneralClaimTaskLogService.findPrpLgeneralClaimTaskLog(queryRule);
		boolean checkGuideUser = false;
		if(!CommonUtils.isEmpty(prpLgeneralClaimTaskLogList)){
			for(PrpLgeneralClaimTaskLog prpLgeneralClaimTaskLog : prpLgeneralClaimTaskLogList){
				prpLgeneralClaimTaskLog.setNodeStatus("4");
				prpLgeneralClaimTaskLogService.update(prpLgeneralClaimTaskLog);
				checkGuideUser = true;
			}
		}
		return checkGuideUser;
	}
	/**
	 * 进入通赔接收处理页面
	 * @param httpServletRequest
	 * @throws Exception
	 * @return String
	 */
//	public void prepareReceiveInsert(HttpServletRequest httpServletRequest) throws Exception {
//		String registNo = httpServletRequest.getParameter("registNo");
//		Map GeneralClaimInfo = new HashMap();
//		GeneralClaimInfo = generalClaimService.prepareReceiveInsert(registNo);
//		List<Object> swflogDtoList = new ArrayList<Object>();
//		PrpLregist prpLregist = new PrpLregist();
//		PrpLgeneralClaimTask prplgeneralclaimtask = new PrpLgeneralClaimTask();
//		swflogDtoList = (List<Object>) GeneralClaimInfo.get("swflogDtoList");
//		prpLregist = (PrpLregist) GeneralClaimInfo.get("prpLregist");
//		prplgeneralclaimtask = (PrpLgeneralClaimTask) GeneralClaimInfo.get("prpLgeneralClaimTask");
//		httpServletRequest.setAttribute("swflogDtoList", swflogDtoList);
//		httpServletRequest.setAttribute("prpLregist", prpLregist);
//		httpServletRequest.setAttribute("prplgeneralclaimtask", prplgeneralclaimtask);
//	}

	/**
	 * 通赔接收提交
	 * @param httpServletRequest
	 * @throws Exception
	 * @return String
	 */
//	public void receiveInsert(HttpServletRequest httpServletRequest) throws Exception {
//		HttpSession session = httpServletRequest.getSession();
//		UserDto userDto = (UserDto) session.getAttribute("user");
//		ParamUtils paramUtils = new ParamUtils(httpServletRequest);
//		String registNo = (paramUtils.getParameter("registNo")).trim();
//		String[] flowID = (paramUtils.getParameterValues("flowid"));
//		String[] logNo = (paramUtils.getParameterValues("logno"));
//		String[] comCode = (paramUtils.getParameterValues("comcode"));
//		String[] handlerCode = (paramUtils.getParameterValues("handlercode"));
//		String[] handlerName = (paramUtils.getParameterValues("handlername"));
//		List<Object> swflogList = new ArrayList<Object>();
//		try {
//		} catch (Exception e) {
//			throw new Exception("數據異常！");
//		}
//		if (flowID.length > 0) {
//			for (int i = 0; i < flowID.length; i++) {
//				SwfLogDto swfLogDto = new SwfLogDto();
//				swfLogDto.setFlowID(flowID[i]);
//				swfLogDto.setLogNo(Integer.parseInt(logNo[i]));
//				swfLogDto.setComCode(comCode[i]);
//				swfLogDto.setHandlerCode(handlerCode[i]);
//				swfLogDto.setHandlerName(handlerName[i]);
//				swfLogDto.setRegistNo(registNo);
//				swflogList.add(swfLogDto);
//			}
//		}
//		generalClaimService.receiveInsert(swflogList, userDto);
//		userDto.setUserMessage("通賠任務提交成功");
//		httpServletRequest.setAttribute("user", userDto);
//	}

	/**
	 * 通赔接收查询
	 * @param request
	 * @throws Exception
	 * @return String
	 */
//	public Page receiveQuery(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
//		HttpSession session = httpServletRequest.getSession();
//		UserDto userDto = (UserDto) session.getAttribute("user");
//		// 查询条件
//		String conditions = "";
//		conditions = this.generateReceiveConditions(httpServletRequest);
//		return generalClaimService.receiveQuery(conditions, userDto, pageNo, recordPerPage);
//	}

	/**
	 * 通赔收回查询
	 * @param request
	 * @throws Exception
	 * @return String
	 */
//	public Page regainQuery(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
//		HttpSession session = httpServletRequest.getSession();
//		UserDto userDto = (UserDto) session.getAttribute("user");
//		// 查询条件
//		String conditions = "";
//		conditions = this.generateRegainConditions(httpServletRequest);
//		return generalClaimService.regainQuery(conditions, userDto, pageNo, recordPerPage);
//	}

	/**
	 * 查询历史通赔任务
	 * @param request
	 * @throws Exception
	 * @return String
	 */
//	public Page historyQuery(HttpServletRequest httpServletRequest, int pageNo, int recordPerPage) throws Exception {
//		HttpSession session = httpServletRequest.getSession();
//		UserDto userDto = (UserDto) session.getAttribute("user");
//		String conditions = "";
//		String generalType = "";
//		generalType = httpServletRequest.getParameter("generalType");
//		// 查询条件
//		conditions = this.generateHistoryConditions(httpServletRequest);
//		return generalClaimService.historyQuery(userDto, generalType, conditions, pageNo, recordPerPage);
//	}

	/**
	 * 当前案件是否通赔过，不在承保地。如果是异地，返回true,不是异地，返回false
	 * @param businessNo
	 * @throws Exception
	 * @return boolean
	 */
//	public boolean isGeneral(String businessNo, UserDto userDto) throws Exception {
//		boolean generalFlag = false;
//		BLGeneralClaimFacade blGeneralClaimFacade = new BLGeneralClaimFacade();
//		generalFlag = blGeneralClaimFacade.isGeneral(businessNo, userDto);
//		return generalFlag;
//	}

	public PrpLgeneralClaimTaskLogService getPrpLgeneralClaimTaskLogService() {
		return prpLgeneralClaimTaskLogService;
	}

	public void setPrpLgeneralClaimTaskLogService(PrpLgeneralClaimTaskLogService prpLgeneralClaimTaskLogService) {
		this.prpLgeneralClaimTaskLogService = prpLgeneralClaimTaskLogService;
	}

	public GeneralClaimService getGeneralClaimService() {
		return generalClaimService;
	}

	public void setGeneralClaimService(GeneralClaimService generalClaimService) {
		this.generalClaimService = generalClaimService;
	}

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
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

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	public PrpLgeneralClaimTaskService getPrpLgeneralClaimTaskService() {
		return prpLgeneralClaimTaskService;
	}

	public void setPrpLgeneralClaimTaskService(PrpLgeneralClaimTaskService prpLgeneralClaimTaskService) {
		this.prpLgeneralClaimTaskService = prpLgeneralClaimTaskService;
	}

}
