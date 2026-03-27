/*
 * @(#)CommonDealSubmitViewHelper.java	Feb 20, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.util;

import ins.framework.common.QueryRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 START
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 END

//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
import com.sinosoft.app.common.util.StringUtil;
import com.sinosoft.claim.bl.facade.BLEndorFacade;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.dto.custom.EndorDto;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.reins.service.ReinsServiceManager;
import com.sinosoft.claim.reins.util.ReinsTranslateViewHelper;
import com.sinosoft.claim.reins.vo.ReinsClaimSummary;
import com.sinosoft.claim.reins.vo.ReinsLargeCase;
import com.sinosoft.claim.reins.vo.ReinsRepayCalResult;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLendor;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLrecase;
import com.sinosoft.claim.schema.model.UwNotion;
import com.sinosoft.claim.schema.model.WfLog;//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLendorService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLrecaseService;
import com.sinosoft.claim.schema.service.facade.SwfNodeService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.claim.schema.service.facade.UwNotionService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.undwrt.bl.facade.BLSWfPathFacade;
import com.sinosoft.undwrt.bl.facade.BLWfLogFacade;

//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
import java.util.Date;
import java.text.SimpleDateFormat;
import com.sinosoft.claim.common.util.BoCopyUtil;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.VehicleClaimApiLog;
import com.sinosoft.claim.schema.model.VehicleClaimApiLogId;
import com.sinosoft.claim.schema.service.facade.VehicleClaimApiLogService;
//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description 核賠提交的ViewHelper
 */
public class CommonDealSubmitViewHelper {
	/** 赔款计算书信息服务 */
	private PrpLcompensateService prpLcompensateService;
	/** 赔付标的信息服务 */
	private PrpLlossService prpLlossService;
	/** 理赔冲减保额信息服务 */
	private PrpLendorService prpLendorService;
	/** 保单基本信息服务 */
	private PrpCmainService prpCmainService;
	/** 险种配置信息服务 */
	private PrpDriskConfigService prpDriskConfigService;
	/** 重开赔案信息服务 */
	private PrpLrecaseService prpLrecaseService;
	/** 再保管理对象 */
	private ReinsServiceManager reinsServiceManager;
	/** 人员级别设置服务 */
	private UtiUwLevelService utiUwLevelService;
	/** 核保核赔处理意见服务 */
	private UwNotionService uwNotionService;
	/** 工作流日志服务 */
	private WfLogService wfLogService;
	/** 工作流节点信息服务 */
	private SwfNodeService swfNodeService;
	/** 立案信息服务 */
	private PrpLclaimService prpLclaimService;
	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 
	private WfLogQueryViewHelper wfLogQueryViewHelper;
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 
	private VehicleClaimApiLogService vehicleClaimApiLogService;


	/**
	 * 提交任务
	 * @param req HttpServletRequest
	 * @throws Exception
	 */
	public int submitTask(HttpServletRequest req) throws Exception {
		int flag = 0;
		try {
			//核赔权限按照人的机构，所以需要传送人的所属机构
			UserDto userDto = (UserDto) req.getSession().getAttribute("user");
			String FlowId = req.getParameter("FlowId");
			int ModelNo = Integer.parseInt((String) req.getParameter("ModelNo"));
			int NodeNo = Integer.parseInt((String) req.getParameter("selectNodeNo"));
			String BusinessType = req.getParameter("BusinessType");
//			BLUtiUwLevelFacade blUtiUwLevelFacade = new BLUtiUwLevelFacade();
			//String conditionsLevel = " 1=1 ";
			// 需要增加险类控制，否则因为模板不同可能导致无法找到路径
			//String classcode = req.getParameter("hiClassCode");
			//conditionsLevel = " USERCODE = '" + userDto.getUserCode() + "' AND UWTYPE = '" + BusinessType + "' AND (CLASSCODE LIKE '%" + classcode + "%' OR CLASSCODE = '*') ORDER BY NODENO DESC";
//			ArrayList<?> utiUwLevelDtoList = (ArrayList<?>) blUtiUwLevelFacade.findByConditions(conditionsLevel);
//			List<UtiUwLevel> utiUwLevelList = utiUwLevelService.findByConditions(conditionsLevel);
//			int currendNodeNo = Integer.parseInt(req.getParameter("NodeNo"));
//			if (utiUwLevelList != null && utiUwLevelList.size() > 0) {
//				currendNodeNo =  utiUwLevelList.get(0).getId().getNodeNo();
//			} else {
//			}
			String BusinessNo = req.getParameter("BusinessNo");
			String FlowStatus = req.getParameter("FlowStatus");
			String Flag = req.getParameter("Flag");
			//String selectNodeName = req.getParameter("selectNodeName");
			String OperatorCode = req.getParameter("SelectUser");
			String userCode = userDto.getUserCode();
			Flag = "1"; // 0表示从业务系统提交到双核，1表示双核系统内部提交
			// 对於审核通过节点调用高级条件进行判断]
			/**
			 * 业务部门要求看到下发修改的意见
			 */
			// 审核通过後也进行保存审核意见;
			// 保存审批意见，更新工作流日志表中处理部门、处理人员代码及名称、处理时间、节点状态(3)。
			String HandleText = req.getParameter("HandleText");
			if (HandleText == null) {
				HandleText = "";
			}
			UwNotion uwNotion = new UwNotion();
			uwNotion.getId().setFlowId(req.getParameter("FlowId"));
			uwNotion.getId().setLogNo(Integer.parseInt(req.getParameter("LogNo")));
			uwNotion.setHandleText(HandleText);
			uwNotion.setBusinessNo(BusinessNo);
			uwNotion.getId().setLineNo(1);
			uwNotion.setClaimNo(req.getParameter("ClaimNo"));
			uwNotion.setNotion(req.getParameter("notion"));

			// 将HandleText拆分成多条 变成多个uwNotionDto对象批量插入uwNotion表
//			new BLUwNotionFacade().saveNotion(uwNotion);
			uwNotionService.saveNotion(uwNotion);
			
			//快速赔案
			String prpLcompensateSpeedFlag = req.getParameter("prpLcompensateSpeedFlag");
			String replevyFlag = req.getParameter("replevyFlag");
			String replevyRemark = req.getParameter("prpLcompensateReplevyRemark");
			PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(BusinessNo);
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(req.getParameter("ClaimNo"));
			if(prpLclaim!=null&&!CommonUtils.isEmpty(replevyFlag)){
				prpLclaim.setReplevyFlag(replevyFlag);
				prpLclaim.setReplevyRemark(replevyRemark);
				prpLclaimService.saveOrUpdate(prpLclaim);
			}
			if(prpLcompensate!=null&&!CommonUtils.isEmpty(prpLcompensateSpeedFlag)){
				prpLcompensate.setSpeedFlag(prpLcompensateSpeedFlag);
				prpLcompensateService.saveOrUpdate(prpLcompensate);
			}
			Map<String,String> infoMap = new HashMap<String,String>();
			infoMap.put("comCode", userDto.getComCode());
			infoMap.put("replevyFlag", req.getParameter("replevyFlag"));
			flag = wfLogService.submitTask(FlowId, ModelNo, NodeNo, BusinessType, BusinessNo, FlowStatus, Flag, userCode, OperatorCode,infoMap);
			if (flag >= 0) {
				req.setAttribute("content", "任務提交成功！");
//				PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(BusinessNo);
				if (prpLcompensate != null && ("1".equals(prpLcompensate.getUnderWriteFlag()) || "3".equals(prpLcompensate.getUnderWriteFlag()))) {// 核赔通过後冲减保额
//					沒有 L NZ T C5 的險種 ，所以先註解
					//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒
//					EndorDto endorDto = new EndorDto();
//					ArrayList<PrpLendor> prpLendorList = new ArrayList<PrpLendor>();
//					int flag1 = 0;
//					QueryRule queryRule = QueryRule.getInstance();
//					queryRule.addEqual("id.compensateNo", BusinessNo);
//					List<PrpLloss> collection = prpLlossService.findPrpLloss(queryRule);
//					if (collection.size() > 0) {
//						String[] strflag = new String[10];
//						int i = 0;
//						for (int k = 0; k < collection.size(); k++) {
//							PrpLloss prpLloss = collection.get(k);
//							if ("L".equals(prpLloss.getKindCode()) || "NZ".equals(prpLloss.getKindCode()) || "T".equals(prpLloss.getKindCode()) || "C5".equals(prpLloss.getKindCode())) {
//								PrpLendor prpLendor = new PrpLendor();
//								prpLendor.setClaimNo(prpLcompensate.getClaimNo());
//								prpLendor.getId().setPolicyNo(prpLloss.getPolicyNo());
//								prpLendor.getId().setCompensateNo(BusinessNo);
//								prpLendor.getId().setItemKindNo(new BigDecimal(prpLloss.getItemKindNo()));
//								prpLendor.setItemCode(prpLloss.getItemCode());
//								prpLendor.setKindCode(prpLloss.getKindCode());
//								if ("".equals(prpLloss.getKindName())) {
//									if ("L".equals(prpLloss.getKindCode())) {
//										prpLloss.setKindName("車身劃痕損失險");
//									} else if ("NZ".equals(prpLloss.getKindCode())) {
//										prpLloss.setKindName("隨車行李物品損失保險條款");
//									} else if ("T".equals(prpLloss.getKindCode())) {
//										prpLloss.setKindName("機動車停駛損失險");
//									} else {
//										prpLloss.setKindName("異地出險住宿費特約");
//									}
//								}
//								strflag[i++] = prpLloss.getKindName();
//								prpLendor.setKindName(prpLloss.getKindName());
//								prpLendor.setCurrency(prpLloss.getCurrency());
//								prpLendor.setEndorAmount(new BigDecimal(prpLloss.getSumRealPay()));
//								prpLendor.setInputDate(prpLcompensate.getInputDate());
//								prpLendor.setFlag("");
//								prpLendorList.add(prpLendor);
//								flag1 = 1;
//							}
//						}
//						if (flag1 == 1) {
//							endorDto.setPrpLendorList(prpLendorList);
//							BLEndorFacade blEndorFacade = new BLEndorFacade();
//							blEndorFacade.save(endorDto);
//							String strflag1 = "提示:該案";
//							for (int j = 0; j < i; j++) {
//								strflag1 = strflag1 + strflag[j] + "險進行了沖減保額！\n";
//							}
//							strflag1 = strflag1 + "任務提交成功！";
//							req.setAttribute("content", strflag1);
//						}
//					}
					// 增加重大赔案和现金赔款摊回提示
//					空迴圈所以先註解
//					String riskCode = prpLcompensate.getRiskCode();
//					ReinsClaimSummary reinsClaimSummary = ReinsTranslateViewHelper.getReinsClaimSummary(BusinessNo);
//					Collection<ReinsLargeCase> reinsLargeCaseCollection = reinsServiceManager.getReinsService().getLargeCashLoss(reinsClaimSummary);
					String strInfo = "";
					//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒
//					if (reinsLargeCaseCollection != null) {
//						for (Iterator<ReinsLargeCase> i = reinsLargeCaseCollection.iterator(); i.hasNext();) {
//
//							ReinsLargeCase reinsLargeCase = (ReinsLargeCase) i.next();
//							//f (reinsLargeCase.getLargeLoss() == Boolean.TRUE) {
//								//strInfo = "該業務為重大賠案,請盡快通知總公司相關險種承保人,進行相應處理!";
//							//}
//							//if (reinsLargeCase.getCashLoss() == Boolean.TRUE) {
//								//strInfo = "該業務需進行現金賠款攤回,請盡快通知總公司相關險種承保人,進行相應攤回處理!";
//							//}
//						}
//					}

					ReinsClaimSummary reinsClaimSummaryTemp = ReinsTranslateViewHelper.getCompensateReinsClaimSummary(BusinessNo);
					Collection<ReinsRepayCalResult> reinsRepayCalResultCollection = reinsServiceManager.getReinsService().repaySimulate(reinsClaimSummaryTemp);

					String str = "";
					if (reinsRepayCalResultCollection != null) {
						for (Iterator<ReinsRepayCalResult> i = reinsRepayCalResultCollection.iterator(); i.hasNext();) {
							ReinsRepayCalResult reinsRepayCalResult = (ReinsRepayCalResult) i.next();
							String modeName = reinsRepayCalResult.getReinsModeName();
							if (modeName != null && "臨分".equals(modeName.trim())) {
								str = "該業務涉及臨時分出,請盡快通知總公司相關險種核保人,進行臨時賠案通知及攤賠處理!";
								break;
							}
						}
					}
					req.setAttribute("reinisContent", strInfo);
					req.setAttribute("reinisContentFlag", str);
					// add by liping 08-04-03 start

					// modify by liuwei at 2011-02-22
					// 结案追加数据核赔通过後上传平台、自动结案核赔通过後结案数据上传平台 start
					//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒
					//空判斷所以先註解
//					if (null != BusinessType && BusinessType.charAt(0) == 'C') {
//						PrpCmain prpCmain = prpCmainService.findByPrimaryKey(prpLcompensate.getPolicyNo());
//						String comCode = prpCmain.getComCode();
//						String comCodeSub = comCode;
//						if (isRecase(prpLcompensate.getClaimNo())) {// 重开赔案，走结案追加接口
//							PrpDriskConfig prpdRiskConfig1 = prpDriskConfigService.findByPrimaryKey(comCodeSub, riskCode, "CIOTHER_TRANSPORT_MODE");
//							if (prpdRiskConfig1 != null && "1".equals(prpdRiskConfig1.getConfigValue())) {// 中科软交强险结案追加数据上传
//							}
//
//							PrpDriskConfig prpdRiskConfig2 = prpDriskConfigService.findByPrimaryKey(comCodeSub, riskCode, "BIZOTHER_TRANSPORT_MODE");
//							if (prpdRiskConfig2 != null && "1".equals(prpdRiskConfig2.getConfigValue())) {// 中科软商业险结案追加数据上传
//							}
//						} else {// 结案接口
//							String autoEndCaseFlag = AppConfig.get("sysconst.AutoEndCase");
//							if ((BusinessNo.substring(0, 1).equals("3") || BusinessNo.equals("C")|| BusinessNo.equals("D")) && "1".equals(autoEndCaseFlag) && ("1".equals(prpLcompensate.getFinallyFlag()) || "2".equals(prpLcompensate.getFinallyFlag()))) {// 自动结案
//								PrpDriskConfig prpdRiskConfig1 = prpDriskConfigService.findByPrimaryKey(comCodeSub, riskCode, "CI_TRANSPORT_MODE");
//								if (prpdRiskConfig1 != null && "1".equals(prpdRiskConfig1.getConfigValue())) {// 易保交强险结案数据上传
//								}
//
//								PrpDriskConfig prpdRiskConfig2 = prpDriskConfigService.findByPrimaryKey(comCodeSub, riskCode, "CIOTHER_TRANSPORT_MODE");
//								if (prpdRiskConfig2 != null && "1".equals(prpdRiskConfig2.getConfigValue())) {// 中科软交强险结案数据上传
//								}
//
//								PrpDriskConfig prpDriskConfigDto3 = prpDriskConfigService.findByPrimaryKey(comCodeSub, riskCode, "BIZ_TRANSPORT_MODE");
//								if (prpDriskConfigDto3 != null && "1".equals(prpDriskConfigDto3.getConfigValue())) {// 易保商业险结案数据上传
//								}
//
//								PrpDriskConfig prpDriskConfig4 = prpDriskConfigService.findByPrimaryKey(comCodeSub, riskCode, "BIZOTHER_TRANSPORT_MODE");
//								if (prpDriskConfig4 != null && "1".equals(prpDriskConfig4.getConfigValue())) {// 中科软商业险结案数据上传
//								}
//							}
//						}
//					}
					// modify by liuwei at 2011-02-22
					// 结案追加数据核赔通过後上传平台、自动结案核赔通过後结案数据上传平台 end
				}
				//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單(打3.14區塊鏈 撤銷的排程) START
				//FlowStatus = 1 下發修改/0通過  ，下發修改要把區塊鏈更新(這段可能要移動 找:CLM0277 可能要把退回撤回撤銷 3.14/ 3.17 寫這裡)
				if(StringUtil.isNotBlank(prpLcompensate.getIsCompulsoryBchainClaim())
						&&prpLcompensate.getIsCompulsoryBchainClaim().equals("N")){
					QueryRule queryRuleBefore = QueryRule.getInstance();
					queryRuleBefore.addEqual("compensateNo", prpLcompensate.getCompensateNo());
					queryRuleBefore.addDescOrder("id.logId");//抓最後一筆
					List<VehicleClaimApiLog> vcalBeforeList = vehicleClaimApiLogService.findVehicleClaimApiLog(queryRuleBefore);
					VehicleClaimApiLog vehicleClaimApiLogBefore = new VehicleClaimApiLog();
					if(null!=vcalBeforeList && vcalBeforeList.size()>0){
						vehicleClaimApiLogBefore = vcalBeforeList.get(0);
					}
					VehicleClaimApiLog vehicleClaimApiLog = new VehicleClaimApiLog();
					//複製之前Create的內容
					BoCopyUtil.convert(vehicleClaimApiLogBefore, vehicleClaimApiLog, VehicleClaimApiLog.class, null, null);
					//複製之前Create的內容
//					vehicleClaimApiLog.setCarNo(vehicleClaimApiLogBefore.getCarNo());
//					vehicleClaimApiLog.setClaimNo(vehicleClaimApiLogBefore.getClaimNo());
//					vehicleClaimApiLog.setCompensateNo(vehicleClaimApiLogBefore.getCompensateNo());
//					vehicleClaimApiLog.setPolicyNo(vehicleClaimApiLogBefore.getPolicyNo());
//					vehicleClaimApiLog.setPersonName(vehicleClaimApiLogBefore.getPersonName());
//					vehicleClaimApiLog.setIdentifyNumber(vehicleClaimApiLogBefore.getIdentifyNumber());
					
					
					VehicleClaimApiLogId vehicleClaimApiLogId = new VehicleClaimApiLogId();
					
					if(null!=vehicleClaimApiLogBefore && null!=vehicleClaimApiLogBefore.getId()
							&& null!=vehicleClaimApiLogBefore.getId().getLogId()
							&& vehicleClaimApiLogBefore.getId().getLogId().length()>0){
				        //一定有before
				        String befNum=vehicleClaimApiLogBefore.getId().getLogId().substring(14);
				        int number = Integer.parseInt(befNum,10);//抓最後三碼
						// 格式化流水號為3位數，不足補0
				        String sequenceStr = String.format("%03d",number+1);
	//					vehicleClaimApiLogId.setLogId(formattedDate+sequenceStr);//編碼規則：西元年+月+日+時+分+秒    (抓原本CREATE的時間)+流水碼三碼
						vehicleClaimApiLogId.setLogId(vehicleClaimApiLogBefore.getId().getLogId().substring(0,14)+sequenceStr);//編碼規則：西元年+月+日+時+分+秒+流水碼三碼
						vehicleClaimApiLog.setId(vehicleClaimApiLogId);
						
						vehicleClaimApiLog.setCreateTime(new Date());
						
						
						//打向區塊鏈3.14 撤回
						vehicleClaimApiLog.setStatus("PENDING");
						vehicleClaimApiLog.setApiCode("API 3.14");//PENDING+api code(API 號碼)=排程
						
						//把複製過來資料整理 不該有的清空
						vehicleClaimApiLog.setApiUrl("192.168");
						vehicleClaimApiLog.setCreateTime(new Date());
						
						vehicleClaimApiLog.setStartTime(null);
						vehicleClaimApiLog.setEndTime(null);
						vehicleClaimApiLog.setRequestJson(null);
						vehicleClaimApiLog.setResponseJson(null);
						vehicleClaimApiLog.setUnderWriteFlag("2");//核賠不通過
						vehicleClaimApiLog.setUpdateTime(null);
						
						vehicleClaimApiLogService.save(vehicleClaimApiLog);
					}else{
						System.out.println("遺漏區塊鏈資料!!");
					}
					//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
				}
			}
		} catch (UserException usee) {
			throw usee;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return flag;
	}

	/**
	 * 提交[批次]任务
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 * @param req HttpServletRequest
	 * @throws Exception
	 */
	public int submitHeapTask(HttpServletRequest req) throws Exception {
		int flag = 0;
		try {
			//核赔权限按照人的机构，所以需要传送人的所属机构
			UserDto userDto = (UserDto) req.getSession().getAttribute("user");
			int NodeNo = Integer.parseInt((String) req.getParameter("selectNodeNo"));

			List<PrpLcompensate> PrpLcompensateList = wfLogQueryViewHelper.queryHeapTask(req);
			int success = 0;
			int fail = 0;
			for(PrpLcompensate prpLcompensate:PrpLcompensateList){
//				
				String statement = 
						"Select * From wflog Where " +
						" logno=2 And businessno='" + prpLcompensate.getCompensateNo() + "'";
				List<WfLog> WfLogList = (List<WfLog>) wfLogService.findByStatement(statement);
				if(null!=WfLogList && WfLogList.size()>0){
					WfLog wfLog = WfLogList.get(0);
					System.out.println("-=-=-=-=-=START(wfLog.0.id:"+wfLog.getId().getFlowId()+")"+WfLogList.size()+"-=-=-=-=-=-=-=");
					if(wfLog.getId().getFlowId().equals("0025012011584354604")){
						System.out.println("測試點:wfLog.0.id="+wfLog.getId().getFlowId());
					}

					String FlowId = wfLog.getId().getFlowId();
					Integer LogNo = wfLog.getId().getLogNo();
					int ModelNo = wfLog.getModelNo();
//					int NodeNo = wfLog.getNodeNo();
					String BusinessType = wfLog.getBusinessType();
					String BusinessNo = wfLog.getBusinessNo();
					String FlowStatus = wfLog.getFlowStatus();
					String Flag = wfLog.getFlag();
					String OperatorCode = wfLog.getOperatorCode();
					String userCode = userDto.getUserCode();
					
					Flag = "1"; // 0表示从业务系统提交到双核，1表示双核系统内部提交
//					// 对於审核通过节点调用高级条件进行判断]
//					/**
//					 * 业务部门要求看到下发修改的意见
//					 */
//					// 审核通过後也进行保存审核意见;
//					// 保存审批意见，更新工作流日志表中处理部门、处理人员代码及名称、处理时间、节点状态(3)。
					String HandleText = req.getParameter("HandleText");
					if (HandleText == null) {
						HandleText = "";
					}
					UwNotion uwNotion = new UwNotion();
					uwNotion.getId().setFlowId(FlowId);
					uwNotion.getId().setLogNo(LogNo);
					uwNotion.setHandleText(HandleText);
					uwNotion.setBusinessNo(BusinessNo);
					uwNotion.getId().setLineNo(1);
					uwNotion.setClaimNo(wfLog.getClaimNo());
					uwNotion.setNotion(req.getParameter("notion"));
					System.out.println("FlowId:"+FlowId+"||LogNo:"+wfLog.getId().getLogNo()+"||HandleText:"+HandleText+"||BusinessNo:"+BusinessNo+"||ClaimNo:"+wfLog.getClaimNo());
//					// 将HandleText拆分成多条 变成多个uwNotionDto对象批量插入uwNotion表
					uwNotionService.saveNotion(uwNotion);
//					
//					//快速赔案
					String prpLcompensateSpeedFlag = req.getParameter("prpLcompensateSpeedFlag");
					String replevyFlag = req.getParameter("replevyFlag");
					String replevyRemark = req.getParameter("prpLcompensateReplevyRemark");
					PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(wfLog.getClaimNo());
					if(prpLclaim!=null&&!CommonUtils.isEmpty(replevyFlag)){
						prpLclaim.setReplevyFlag(replevyFlag);
						prpLclaim.setReplevyRemark(replevyRemark);
						prpLclaimService.saveOrUpdate(prpLclaim);
					}
					PrpLcompensate prpLcompensate4Save = prpLcompensateService.findPrpLcompensate(BusinessNo);
					if(prpLcompensate4Save!=null&&!CommonUtils.isEmpty(prpLcompensateSpeedFlag)){
//						prpLcompensate.setSpeedFlag(prpLcompensateSpeedFlag);
						prpLcompensate4Save.setSpeedFlag(prpLcompensateSpeedFlag);
						prpLcompensateService.saveOrUpdate(prpLcompensate4Save);
					}
					Map<String,String> infoMap = new HashMap<String,String>();
					infoMap.put("comCode", userDto.getComCode());
					infoMap.put("replevyFlag", prpLcompensate.getReplevyFlag());
					System.out.println("prpLcompensateSpeedFlag:"+prpLcompensateSpeedFlag+"||replevyFlag:"+prpLcompensate.getReplevyFlag()+"||");
					System.out.println("FlowId:"+FlowId+"||ModelNo:"+ModelNo+"||NodeNo:"+NodeNo+"||BusinessType:"+BusinessType+"||BusinessNo:"+BusinessNo);
					System.out.println("FlowStatus:"+FlowStatus+"||userCode:"+userCode+"||OperatorCode:"+OperatorCode+"||infoMap:"+infoMap.get("comCode")+"/"+infoMap.get("replevyFlag"));
					flag = wfLogService.submitTask(FlowId, ModelNo, NodeNo, BusinessType, BusinessNo, FlowStatus, Flag, userCode, OperatorCode,infoMap);

					req.setAttribute("content", "任務提交成功！");
					if (flag <= 0) {
						fail++;
						req.setAttribute("content", "任務提交失敗！");
						//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						System.out.println("任務提交失敗="+flag);
					}else{
						success+=flag;
						System.out.println("任務提交成功="+("+"+flag+"="+success));
					}
					System.out.println("-=-=-=-=-=END-=-=-=-=-=-=-=");
					

				}
			}
		} catch (UserException usee) {
			throw usee;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return flag;
	}
	
	/**
	 * 获取前进路径列表
	 * @param modelNo int
	 * @param nodeNo int
	 * @param businessType String
	 * @param businessNo String
	 * @param defaultFlag String
	 * @param comCode String
	 * @throws Exception
	 * @return Collection
	 */
	public Collection<?> getPathes(int modelNo, int nodeNo, String businessType, String businessNo, String defaultFlag, String comCode) throws UserException, Exception {
		Collection<?> resultList = new BLSWfPathFacade().getPathes(modelNo, nodeNo, businessType, businessNo, defaultFlag, comCode);
		return resultList;
	}

	/**
	 * 查询路径
	 * @param modelNo
	 * @param nodeNo
	 * @param businessType
	 * @param businessNo
	 * @param defaultFlag
	 * @param comCode
	 * @param batchFlag
	 * @return
	 * @throws UserException
	 * @throws Exception
	 */
	public Collection<?> getPathes(int modelNo, int nodeNo, String businessType, String businessNo, String defaultFlag, String comCode, String batchFlag) throws UserException, Exception {
		try {
			// add by luyang reason: 增加高级条件判断
			Collection<?> resultList = new BLSWfPathFacade().getPathes(modelNo, nodeNo, businessType, businessNo, defaultFlag, comCode);
			return resultList;
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取回退路径列表
	 * @param flowID String
	 * @param logNo int
	 * @throws Exception
	 * @return Collection
	 */
	public Collection<?> getBackList(String flowID, int logNo, int nodeNo) throws Exception {
		Collection<?> resultList = new BLWfLogFacade().getBackList(flowID, logNo, nodeNo);
		return resultList;
	}

	/**
	 * 获取指定人员列表
	 * @param modelNo int
	 * @param nodeNo int
	 * @param businessType String
	 * @param businessNo String
	 * @param flag String
	 * @return Collection
	 * @throws Exception
	 */
	public Collection<?> getSubmitUserList(int modelNo, int nodeNo, String businessType, String businessNo, String flag) throws Exception {
		try {
			Collection<?> resultList = new BLWfLogFacade().getSubmitUserList(modelNo, nodeNo, businessType, businessNo, flag);
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * 查询是否有重开赔案
	 * @param claimNo
	 * @return
	 * @throws Exception
	 */
	public boolean isRecase(String claimNo) throws Exception {
		boolean blnReturn = false; // 为false为无重开
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.claimNo", claimNo);
		List<PrpLrecase> list = prpLrecaseService.findPrpLrecase(queryRule);
		if (list != null && list.size() > 0) {
			blnReturn = true; // 有重开
		}
		return blnReturn;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

	public PrpLendorService getPrpLendorService() {
		return prpLendorService;
	}

	public void setPrpLendorService(PrpLendorService prpLendorService) {
		this.prpLendorService = prpLendorService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
	}

	public PrpLrecaseService getPrpLrecaseService() {
		return prpLrecaseService;
	}

	public void setPrpLrecaseService(PrpLrecaseService prpLrecaseService) {
		this.prpLrecaseService = prpLrecaseService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public ReinsServiceManager getReinsServiceManager() {
		return reinsServiceManager;
	}

	public void setReinsServiceManager(ReinsServiceManager reinsServiceManager) {
		this.reinsServiceManager = reinsServiceManager;
	}

	public UtiUwLevelService getUtiUwLevelService() {
		return utiUwLevelService;
	}

	public void setUtiUwLevelService(UtiUwLevelService utiUwLevelService) {
		this.utiUwLevelService = utiUwLevelService;
	}

	public UwNotionService getUwNotionService() {
		return uwNotionService;
	}

	public void setUwNotionService(UwNotionService uwNotionService) {
		this.uwNotionService = uwNotionService;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

	public SwfNodeService getSwfNodeService() {
		return swfNodeService;
	}

	public void setSwfNodeService(SwfNodeService swfNodeService) {
		this.swfNodeService = swfNodeService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 START
	public WfLogQueryViewHelper getWfLogQueryViewHelper() {
		return wfLogQueryViewHelper;
	}

	public void setWfLogQueryViewHelper(WfLogQueryViewHelper wfLogQueryViewHelper) {
		this.wfLogQueryViewHelper = wfLogQueryViewHelper;
	}
	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 END
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	public VehicleClaimApiLogService getVehicleClaimApiLogService() {
		return vehicleClaimApiLogService;
	}

	public void setVehicleClaimApiLogService(
			VehicleClaimApiLogService vehicleClaimApiLogService) {
		this.vehicleClaimApiLogService = vehicleClaimApiLogService;
	}
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
}
