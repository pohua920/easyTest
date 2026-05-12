/*
 * @(#)CommonCheckTaskAction.java	Feb 19, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.web;

import ins.framework.common.QueryRule;
import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
import javax.servlet.http.HttpSession;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDriskService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.compensate.service.facade.CompensateService;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpCcoins;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLcfeecoins;
import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLloss;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.model.PrpLregist;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import com.sinosoft.claim.schema.model.PrpLuser;
import com.sinosoft.claim.schema.model.UwNotion;
import com.sinosoft.claim.schema.model.WfLog;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsService;
import com.sinosoft.claim.schema.service.facade.PrpCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpCmainService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
// mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核
import com.sinosoft.claim.schema.service.facade.PrpLCitemKindService;
import com.sinosoft.claim.schema.service.facade.PrpLcfeecoinsService;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核
import com.sinosoft.claim.schema.service.facade.PrpLuserService;
import com.sinosoft.claim.schema.service.facade.UtiUwLevelService;
import com.sinosoft.claim.schema.service.facade.UwNotionService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.undwrt.util.CommonCheckTaskViewHelper;
//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增  START
import com.sinosoft.claim.undwrt.util.CommonDealTaskViewHelper;
import com.sinosoft.claim.undwrt.util.WfLogQueryViewHelper;
import com.sinosoft.claim.undwrt.util.UndwrtTaskDealViewHelper;
//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 END

import com.sinosoft.claim.undwrt.vo.PolicyAbstractInfoDto;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.AppConfig;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
public class CommonCheckTaskAction extends Struts2Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**承保url*/
	private String prpallUrl = "";
	/**指定人员开关*/
	private String undwrt_continuetask = "";
	/**联共保赔付金额分摊服务*/
	private PrpLcfeecoinsService prpLcfeecoinsService;
	/**立案服务*/
	private PrpLclaimService prpLclaimService;
	/**理算服务*/
	private PrpLcompensateService prpLcompensateService;
	/**预陪登记服务*/
	private PrpLprepayService prpLprepayService;
	/**赔款费用服务*/
	private PrpLchargeService prpLchargeService;
	/**险别服务*/
	private PrpDriskService prpDriskService;
	/**保存共保服务*/
	private PrpCcoinsService prpCcoinsService;
	/**保单服务*/
	private PrpCmainService prpCmainService;
	/**结案数据收集*/
	private EndorseViewHelper endorseViewHelper;
	/**代码翻译服务*/
	private CodeService codeService;
	/**基础数据配置服务*/
	private PrpDcodeService prpDcodeService;
	/**核赔级别服务*/
	private UtiUwLevelService utiUwLevelService;
	/**核赔意见服务*/
	private UwNotionService uwNotionService;
	/**核赔日志服务*/
	private WfLogService wfLogService; 
	/**核赔数据收集服务*/
	private CommonCheckTaskViewHelper commonCheckTaskViewHelper;
	/**備案服务*/
	private PrpLregistService prpLregistService;
	private CompensateService compensateService;
	private PrpCitemKindService prpCitemKindService;

	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
	/** 支付信息service */
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/**車體險訊息服務*/
	private PrpLuserService prpLuserService;
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END

	// mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核
	private PrpLCitemKindService prpLCitemKindService;

	//mantis：CLM0182，處理人員：CD078，需求單編號：新核心-車體險進廠維修提示訊息修改
	private boolean itemKindCheck;

	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增  START
	private UndwrtTaskDealViewHelper undwrtTaskDealViewHelper;//  整批核赔任务viewHelper
	private WfLogQueryViewHelper wfLogQueryViewHelper;
	/**核赔节点帮助类*/
	private CommonDealTaskViewHelper commonDealTaskViewHelper;
	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 END
	/**
	 * 任务校验
	 * @return
	 * @throws Exception
	 */
	public String commonCheckTask() throws Exception {
		this.clearErrorsAndMessages();
		String forward = "";
		HttpServletRequest req = this.getRequest();
		HttpSession session = req.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String flowId = req.getParameter("iFlowID");
		String logNo = req.getParameter("iLogNo");
		String editType = req.getParameter("EditType");
		String handType = req.getParameter("HandType");
		if(DataUtils.emptyToNull(flowId)!=null && DataUtils.emptyToNull(logNo)!=null){
			WfLog wfLog = this.getWfLogService().findByPrimaryKey(flowId, Integer.parseInt(logNo));
			if(wfLog != null){
				String nodeStatus = wfLog.getNodeStatus();
				String userCode = user.getUserCode();
				String businessNo = wfLog.getBusinessNo();
				String claimNo = wfLog.getClaimNo();//mantis： CLM0121 ，處理人員 ：DP0706，需求單編號：CLM0121.處理人員及經辦人員不可同時為核賠人員
				String tempSqlStr = "";
				forward = "success";
				PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
				if("deal".equals(editType)){
					if (("2".equals(nodeStatus) || "3".equals(nodeStatus)) && !userCode.equals(wfLog.getOperatorCode())) { // 正在处理或已处理未提交，如果是他人再处理，进行提示
						forward = "failure";
						req.setAttribute("content", wfLog.getOperatorName() + "已在處理該業務！");
					} else if (nodeStatus.equals("4")) { // 已提交或已关闭
						forward = "failure";
						req.setAttribute("content", "該工作流已處理流轉！");
					} else if (nodeStatus.equals("0")) {
						forward = "failure";
						req.setAttribute("content", "該工作流已處理完畢！");
					}
					//mantis：CLM0132，處理人員：CC009，需求單編號：核賠人員受款人ID檢核修改 START
					//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
					req.setAttribute("contentFlag", true);
					QueryRule queryRule = QueryRule.getInstance();
					queryRule.addEqual("id.compensateNo", businessNo);
					queryRule.addSql(" ((kindcode is null and certiType = '01') or (kindcode is not null and certiType = '02'))");
					List<PrpLpayObjectInfo> resultList = getPrpLpayObjectInfoService().findPrpLpayObjectInfo(queryRule);
					PrpLuser prpLuser = null;
					for(PrpLpayObjectInfo ploi:resultList){
						prpLuser = this.getPrpLuserService().findPrpLuserById(ploi.getUniformNo());
						if(null!=prpLuser && prpLuser.getUserCode().equals(user.getUserCode())){
							req.setAttribute("contentFlag", false);
							//req.setAttribute("content2", getText("prompt.compensate.cannotSameWithCheckuser"));//核賠經辦人員不可為賠付對象的受款人！
						}
					}
					//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
					//mantis：CLM0132，處理人員：CC009，需求單編號：核賠人員受款人ID檢核修改 END
					
					//mantis：CLM0150，處理人員：DP0706，需求單編號：.新核心-車資費用人員階級管控 START
					req.setAttribute("showCarFeeQuotaFlag", false);//預設為通過
					req.setAttribute("carFeeQuota", "0");//超過限額
					//(1)	需計算核賠人員審核該案件內是否有申請理賠經辦車資費用
					//用計算書號確認核賠人員觸擊審核案件是否有車資費用，取得申請車資費用理賠經辦身分證字號
					QueryRule queryChargeRule = QueryRule.getInstance();
					queryChargeRule.addEqual("id.compensateNo", wfLog.getBusinessNo());
					queryChargeRule.addSql(" chargeCode in ('T','U','V')");			
					List<PrpLcharge> prplchargeList = prpLchargeService.findPrpLcharge(queryChargeRule);
					Double thisPayAmount = 0d;//此次審核案件費用
					//有車險费用訊息
					if(prplchargeList != null && prplchargeList.size() > 0){
						PrpLcharge prpLcharge = prplchargeList.get(0);
						//取得理賠經辦人員 PAY.COMPENSATENO = FEE.COMPENSATENO AND PAY.KINDCODE IS NOT NULL and FEE.SERIALNO = PAY.SERIALNO
						QueryRule queryPrpLpayObjectInfoRule = QueryRule.getInstance();//PAY
						queryPrpLpayObjectInfoRule.addEqual("id.compensateNo", prpLcharge.getId().getCompensateNo());
						queryPrpLpayObjectInfoRule.addEqual("id.serialNo", prpLcharge.getId().getSerialNo());
						queryPrpLpayObjectInfoRule.addSql(" kindCode is not null");		
						List<PrpLpayObjectInfo> prpLpayObjectInfoRuleList = getPrpLpayObjectInfoService().findPrpLpayObjectInfo(queryPrpLpayObjectInfoRule);
						if(prpLpayObjectInfoRuleList!= null && prpLpayObjectInfoRuleList.size() > 0){
							PrpLpayObjectInfo prpLpayObjectInfo = prpLpayObjectInfoRuleList.get(0);//理賠經辦人員
							thisPayAmount = prpLpayObjectInfo.getPayAmount();
							//從維護資料表取得該人員費用限額
							PrpLuser prpLuserForFeeQuota = this.getPrpLuserService().findPrpLuserById(prpLpayObjectInfo.getUniformNo());
							if(prpLuserForFeeQuota != null){
								Double feeQuota = prpLuserForFeeQuota.getFeeQuota();//費用限額
								if(feeQuota != null){
									//取得核賠人員審核當月累積已核賠費用+此次審核案件費用
									Double sumPayAmount = prpLcompensateService.getSumPayAmountThisMonth(prpLpayObjectInfo.getUniformNo());//當月累積已核賠費用
									sumPayAmount+=thisPayAmount;
									//(2)	理賠經辦申請車資費用於審核當月累計(sumPayAmount)是否已超過可申請限額PrpLuser.FeeQuota 
									if(sumPayAmount > feeQuota){
										req.setAttribute("showCarFeeQuotaFlag", true);//超過限額
										req.setAttribute("carFeeQuota", sumPayAmount-feeQuota);//顯示超過的限額
									}
								}
							}
							
						}
					}
					//mantis：CLM0150，處理人員：DP0706，需求單編號：.新核心-車資費用人員階級管控 END
					
					//mantis： CLM0121 ，處理人員 ：DP0706，需求單編號：CLM0121.處理人員及經辦人員不可同時為核賠人員START
					//【PRPLCLAIM.OPERATORCODE】立案經辦人員代號
					//【PRPLCLAIM.HANDLERCODE】立案處理人員代號
					//【PRPLREGIST.OPERATORCODE】備案處理人員代號
					//【PRPLCOMPENSATE.HANDLERCODE】理算處理人員代號
					//【PRPLCOMPENSATE.OPERATORCODE】理算經辦人員代號
					PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
					if(prpLclaim != null){
						if(user.getUserCode().equals(prpLclaim.getOperatorCode())){
							forward = "failure";
							req.setAttribute("content", "該案件您為立案經辦人員，核賠人員不能是立案經辦人！");
							return forward;
						}
						if(user.getUserCode().equals(prpLclaim.getHandlerCode())){
							forward = "failure";
							req.setAttribute("content", "該案件您為立案處理人員，核賠人員不能是立案處理人！");
							return forward;
						}
						PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
						if(user.getUserCode().equals(prpLregist.getOperatorCode())){
							forward = "failure";
							req.setAttribute("content", "該案件您為備案處理人員，核賠人員不能是備案處理人！");
							return forward;
						}
					}
					if (prpLcompensate != null && user.getUserCode().equals(prpLcompensate.getHandlerCode())) {
						forward = "failure";
						req.setAttribute("content", "該案件您為理算處理人員，核賠人員不能是理算處理人！");
						return forward;
					}
					if (prpLcompensate != null && user.getUserCode().equals(prpLcompensate.getOperatorCode())) {
						forward = "failure";
						req.setAttribute("content", "該案件您為理算經辦人員，核賠人員不能是理算經辦人！");
						return forward;
					}
					//mantis： CLM0121 ，處理人員 ：DP0706，需求單編號：CLM0121.處理人員及經辦人員不可同時為核賠人員END
				}
				wfLog.setRiskCodeName(this.codeService.translateRiskCode(wfLog.getRiskCode(), true));
				req.setAttribute("wfLog", wfLog);
				List<UwNotion> notionList = new ArrayList<UwNotion>();//意见处理
				UwNotion tempUwNotion = null;
				if ("1".equals(nodeStatus)) {// 如果是新处理，默认'同意'
					notionList.add(new UwNotion());
					req.setAttribute("notionContent", notionList);
				} else {
					List<UwNotion> tempList = uwNotionService.findByConditions("flowid = '"+flowId+"' and logno = "+logNo);
					if(tempList!=null && !tempList.isEmpty()){
						StringBuffer notion = new StringBuffer("");
						for (UwNotion u : tempList) {
							notion.append(u.getHandleText().trim()); // 累加审批意见
							tempUwNotion = u;
						}
						tempUwNotion.setHandleText(notion.toString());
						notionList.add(tempUwNotion);
						req.setAttribute("notionContent", notionList);
					}
				}
				if ("22".equals(handType)) {//意见列表
					req.setAttribute("notionCode", prpDcodeService.findByConditions(" codetype='HpNotionCode'"));
				}
				if ("1".equals(nodeStatus) && !"query".equals(editType)) {
					wfLog.setOperatorCode(user.getUserCode());
					wfLog.setOperatorName(user.getUserName());
					wfLog.setDeptCode(user.getComCode());
					wfLog.setDeptName(user.getComName());
					wfLog.setNodeStatus("2");
					this.getWfLogService().update(wfLog);
				}
				tempSqlStr = " businessNo = '"+businessNo+"' order by serialNo ";
				List<PrpLcfeecoins> coinsList = prpLcfeecoinsService.findPrpLcfeecoins(QueryRule.getInstance().addSql(tempSqlStr));
				if (coinsList !=null && !coinsList.isEmpty()) {
					req.setAttribute("coinsFlag", "1");
				}
				PrpLcfeecoins prpLcfeecoins = new PrpLcfeecoins();
				prpLcfeecoins.setPrpLcfeecoinsList(coinsList);
				req.setAttribute("prpLcfeecoins", prpLcfeecoins);
				double coinUsCoinsRate = 1;
				// 联共保显示我司金额
				if (coinsList !=null && !coinsList.isEmpty()) {
					List<PrpCcoins> prpCcoinsList = prpCcoinsService.findByConditionsChiefFlag("policyno='" + prpLcompensate.getPolicyNo() + "' and CoinsCode='"+ConstantCodes.COMPANYCODE+"'");
					for (Iterator<PrpCcoins> iterator = prpCcoinsList.iterator(); iterator.hasNext();) {
						PrpCcoins prpCcoins = iterator.next();
						/*
						mantis： CLM0004，處理人員：David，需求單編號：CLM0004 ---start
						原因：此段程式導至計算書提交金額與核賠金額不一致所以拿掉。
						*/
//						BigDecimal bigCoinsRate = new BigDecimal(Double.toString(prpCcoins.getCoinsRate() / 100));
//						BigDecimal bigSumDutyPaid = new BigDecimal(Double.toString(prpLcompensate.getSumDutyPaid()));
//						BigDecimal bigSumNoDutyFee = new BigDecimal(Double.toString(prpLcompensate.getSumNoDutyFee()));
//						BigDecimal bigSumPaid = new BigDecimal(Double.toString(prpLcompensate.getSumPaid()));
//						BigDecimal bigSumThisPaid = new BigDecimal(Double.toString(prpLcompensate.getSumThisPaid()));
//						BigDecimal bigSumPrePaid = new BigDecimal(Double.toString(prpLcompensate.getSumPrePaid()));
//						prpLcompensate.setSumDutyPaid(bigSumDutyPaid.multiply(bigCoinsRate).doubleValue());
//						prpLcompensate.setSumNoDutyFee(bigSumNoDutyFee.multiply(bigCoinsRate).doubleValue());
//						prpLcompensate.setSumPaid(bigSumPaid.multiply(bigCoinsRate).doubleValue());
//						prpLcompensate.setSumThisPaid(bigSumThisPaid.multiply(bigCoinsRate).doubleValue());
//						prpLcompensate.setSumPrePaid(bigSumPrePaid.multiply(bigCoinsRate).doubleValue());
						/*
						mantis： CLM0004，處理人員：David，需求單編號：CLM0004 ---end
						*/
						
						//我方比例
						if(ConstantCodes.COMPANYCODE.equals(prpCcoins.getCoinsCode())){
							coinUsCoinsRate = prpCcoins.getCoinsRate()/100;
						}
					}
				}
				req.setAttribute("coinUsCoinsRate", coinUsCoinsRate);
				req.setAttribute("prpLcompensate", prpLcompensate);
				// 获取例外事项转存到页面
				if ("Y".equals(wfLog.getBusinessType())) {
					PrpLprepay prpLprepay = prpLprepayService.findPrpLprepay(businessNo);
					req.setAttribute("exceptions", prpLprepay.getExceptions());
				} else {
					req.setAttribute("exceptions", prpLcompensate.getExceptions());
				}
				tempSqlStr = " claimNo = '"+wfLog.getClaimNo()+"' and ( underWriteFlag = '1' or underWriteFlag = '3' ) ";
				List<PrpLcompensate> compensateList = prpLcompensateService.findByConditions(tempSqlStr);
				boolean havePaidFlag = compensateList != null && !compensateList.isEmpty();
				if (!havePaidFlag) {//无已审核通过的实赔，则查预赔
					List<PrpLprepay> prpLprepayList = prpLprepayService.findPrpLprepay(QueryRule.getInstance().addSql(tempSqlStr));
					havePaidFlag = prpLprepayList != null && !prpLprepayList.isEmpty();
				}
				PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(wfLog.getClaimNo());
				if ("1".equals(prpLclaim.getReplevyFlag())) {
					prpLcompensate.setReplevyFlag("1");
				} else {
					prpLcompensate.setReplevyFlag("0");
				}
				prpLcompensate.setReplevyRemark(prpLclaim.getReplevyRemark());
				req.setAttribute("prpLclaim", prpLclaim);//超出时间			
				String passDay = "0";
				Date applyPayDate = prpLclaim.getStartApplyPayDate();
				if(applyPayDate != null){
					long tempDate = (new Date().getTime()-applyPayDate.getTime())/(24*60*60*1000);
					if (tempDate > 15 && !havePaidFlag) {
						passDay = String.valueOf(tempDate);
					}
				}
				req.setAttribute("passDay", passDay);//超出时间
				// 增加判断是不是核赔初审岗人员，判断依据：只有nodeno=4的核赔权限，並且险类不为*和包含05
//				String conditions = "userCode='" + userCode + "' and nodeno='4' and uwtype in ('C','Y') and classcode <> '*' " +
//						"and  not exists (select 0 from prpdclass where prpdclass.classcode=UtiUwLevel.classcode and validstatus = '1' and prpdclass.riskcategory='D')";
				String chuShenGangFlag = "0";
//				if (utiUwLevelService.getCount(conditions) > 0){
//					String riskType = this.codeService.translateRiskCodetoRiskType(wfLog.getRiskCode());
//					if(!ConstantCodes.CLASSCODE_D.equals(riskType)){
//						chuShenGangFlag = "1";
//					}
//				}
				req.setAttribute("chuShenGangFlag", chuShenGangFlag);
				PrpCmain prpCmain = prpCmainService.findByPrimaryKey(prpLclaim.getPolicyNo());
				req.setAttribute("prpCmain", prpCmain);
				PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
				req.setAttribute("prpLregist", prpLregist);
				// 获取危险单位信息
				setLossInfo(req,wfLog,prpLclaim,prpCmain);
				// 增加保费未缴全提示
				commonCheckTaskViewHelper.setPayCase(req,prpCmain);
				//mantis： CLM0092 ，處理人員 ：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒
				String riskType = this.codeService.translateRiskCodetoRiskType(wfLog.getRiskCode());
				setCloseBetween(req,prpCmain,prpLcompensate,riskType);
				// 查找审核通过路径。
				commonCheckTaskViewHelper.getPassPath(req,wfLog);
				commonCheckTaskViewHelper.commonDealContent(req,prpCmain,prpLclaim,wfLog);

				// mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -- start
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
				String licenseNo = prpLregist.getLicenseNo();
				String damageStartDate = "";
				if (prpLregist.getDamageStartDate()!=null) {
					damageStartDate = df.format(prpLregist.getDamageStartDate());
				}
				String damageStartHour = "";
				if (prpLregist.getDamageStartHour()!=null) {
					damageStartHour = prpLregist.getDamageStartHour().split(":")[0];
				}
				String sum = prpLCitemKindService.checkLicenceNoAndDamageStartDate(licenseNo, damageStartDate, damageStartHour, null);
				if (org.apache.commons.lang3.StringUtils.isNotBlank(sum)) {
					if (Integer.parseInt(sum) > 2000) {
						req.setAttribute("checkChargeAmountMsg", "請確認是否需攤付代墊之醫詢費用");
					}
				}
				// mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -- end

			} else {
				forward = "failure";
				req.setAttribute("content", "工作流數據查詢失敗！");
			}
		} else {
			forward = "failure";
			req.setAttribute("content", "工作流數據查詢失敗！");
		}
		return forward;
	}
	
	/**
	 * 整批任务校验
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 * @return
	 * @throws Exception
	 */
	public String commonHeapCheckTask() throws Exception {
		this.clearErrorsAndMessages();
		String forward = "";
		HttpServletRequest req = this.getRequest();
		HttpServletResponse resp = this.getResponse();
		HttpSession session = req.getSession();
		UserDto user = (UserDto) session.getAttribute("user");
		String choseRiskCode = req.getParameter("riskCode");//險種
		String chosePayCodeType = req.getParameter("payCodeType");//賠付代號
		String choseUniformNo = req.getParameter("uniformNo");//賠付對象統一編號/身分證號
		String choseflowInTime1 = req.getParameter("flowInTime1");//提交時間(起)
		String choseflowInTime2 = req.getParameter("flowInTime2");//提交時間(迄)
		String choseNodeStatus = req.getParameter("nodeStatus");//狀態
		String flowId = req.getParameter("iFlowID");
		String logNo = req.getParameter("iLogNo");
		String editType = req.getParameter("EditType");
		String handType = req.getParameter("HandType");
		
		List<PrpLcompensate> PrpLcompensateList = wfLogQueryViewHelper.queryHeapTask(req);
		String content = "";
		String contentHid= "";

		for(PrpLcompensate prpLcompensate: PrpLcompensateList){
			String claimNo = prpLcompensate.getClaimNo();
			//--來源CLM0121
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
			if(prpLclaim != null){
				if(user.getUserCode().equals(prpLclaim.getOperatorCode())){
					content="該案件您為立案經辦人員，核賠人員不能是立案經辦人！";
					contentHid+="(1)cm.cn"+prpLclaim.getClaimNo()+"/cm.pn"+prpLclaim.getPolicyNo();
				}
				if(user.getUserCode().equals(prpLclaim.getHandlerCode())){
					content="該案件您為立案處理人員，核賠人員不能是立案處理人！";
					contentHid+="(2)cm.cn"+prpLclaim.getClaimNo()+"/cm.pn"+prpLclaim.getPolicyNo();
				}
				PrpLregist prpLregist = prpLregistService.findPrpLregist(prpLclaim.getRegistNo());
				if(user.getUserCode().equals(prpLregist.getOperatorCode())){
					content=prpLregist.getPolicyNo()+"該案件您為備案處理人員，核賠人員不能是備案處理人！";
					contentHid+="(3)rg.rn"+prpLregist.getRegistNo()+"/rg.pn"+prpLregist.getPolicyNo();
				}
			}
			if (prpLcompensate != null && user.getUserCode().equals(prpLcompensate.getHandlerCode())) {
				content="該案件您為理算處理人員，核賠人員不能是理算處理人！";
				contentHid+="(4)cs.rn"+prpLcompensate.getRegistNo()+"/cs.pn"+prpLcompensate.getPolicyNo()+"/cs.cn"+prpLcompensate.getClaimNo();
			}
			if (prpLcompensate != null && user.getUserCode().equals(prpLcompensate.getOperatorCode())) {
				content="該案件您為理算經辦人員，核賠人員不能是理算經辦人！";
				contentHid+="(5)cs.rn"+prpLcompensate.getRegistNo()+"/cs.pn"+prpLcompensate.getPolicyNo()+"/cs.cn"+prpLcompensate.getClaimNo();
			}
			//--來源CLM0121
		}
		req.setAttribute("content",content);
		req.setAttribute("contentHid",contentHid);
		int count = 0;
		for(PrpLcompensate plc:PrpLcompensateList){
			count+=plc.getSumThisPaid();
		}
		if(count == 0){
			req.setAttribute("rtnCount", count);
			undwrtTaskDealViewHelper.prepareHeapQuery(req, resp);
			forward = "noData";
		}else{
			forward = "success";
		}
		req.setAttribute("deptName",user.getComName());
		req.setAttribute("riskCode",choseRiskCode);
		req.setAttribute("riskCodeName",this.codeService.translateRiskCode(choseRiskCode, true));
		if ("22".equals(handType)) {//意见列表
			req.setAttribute("notionCode", prpDcodeService.findByConditions(" codetype='HpNotionCode'"));
		}
//		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim((String)req.getAttribute("lastClaimNo"));
		PrpCmain prpCmain = prpCmainService.findByPrimaryKey((String)req.getAttribute("lastPolicyNo"));
		req.setAttribute("prpCmain", prpCmain);
		
		DateTime systemTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		req.setAttribute("systemTime", systemTime);
		return forward;
	}
	
	/**
	 * 整批任务EXCEL
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 * @return
	 * @throws Exception
	 */
	public String heapToExcel() throws Exception {
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		wfLogQueryViewHelper.exportToExcel(request, response);
		return "none";
	}
	
	/**
	 * 核赔任务处理
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 * @return
	 * @throws Exception
	 */
	public String commonHeapDealTask() throws Exception {
		this.clearErrorsAndMessages();
		String forward = "";
		// 处理业务类型
		// 任务类型 save－保存审批任务 submit－提交审批任务
		HttpServletRequest req = this.getRequest();
		String dealType = req.getParameter("DealType");
		String wfLogIdArray = req.getParameter("wfLogFlowIdArray");
		String[] wfLogFlowIdArray = wfLogIdArray.substring(1).split(",");
		if ("undo".equals(dealType)) {
			for(String wflogInfo :wfLogFlowIdArray){
				String[] wflogAry = wflogInfo.split("@");
				String flowId = wflogAry[0]; // 工作流号
				int logNo = Integer.parseInt(wflogAry[1]); // 序号
				commonDealTaskViewHelper.undoTask(flowId, logNo);
				forward = "undoTask";
				req.setAttribute("content", "放棄任務成功！");
			}
		}
//		if (dealType.equals("save")) { // 暂存
//			commonDealTaskViewHelper.saveTask(req);
//			forward = "save";
//			req.setAttribute("content", "任務保存成功！");
//		} else if (dealType.equals("submit")) { // 提交任务
//			commonDealTaskViewHelper.submitTaskBefore(req);
//			String submitDirection = req.getParameter("SubmitDirection");
//			// add by caozhigang 20090401 start
//			// reason:保存下发修改和提交上级时的意见
//			String HandleText = req.getParameter("HandleText");
//			req.setAttribute("HandleText", HandleText);
//			// add by caozhigang 20090401 end
//			req.setAttribute("ClaimNo", req.getParameter("ClaimNo"));
//			req.setAttribute("notion", req.getParameter("notion"));
//			forward = submitDirection;
//		}
		// add by xukefeng 2006-12-01 增加放弃任务功能
		return forward;
	}

	//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 - start
	private void setCloseBetween(HttpServletRequest request, PrpCmain prpCmain,PrpLcompensate prpLcompensate, String riskType) {
		Calendar date = Calendar.getInstance();
	    date.setTime(prpCmain.getEndDate());
	    date.add(Calendar.DATE, -93);
	    //是否在閉鎖期內理算
		if(ConstantCodes.CLASSCODE_D.equals(riskType) && isEffectiveDate(prpLcompensate.getInputDate(),date.getTime(),prpCmain.getEndDate())){
			request.setAttribute("isCloseBetween", true);
		}else{
			request.setAttribute("isCloseBetween", false);
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
	 * 获取危险单位信息到页面
	 * @param req HttpServletRequest
	 * @throws Exception
	 */
	private void setLossInfo(HttpServletRequest req,WfLog wfLog,PrpLclaim prpLclaim,PrpCmain prpCmain) throws Exception {
		String handType = req.getParameter("HandType");
		if (handType != null && handType.equals("22")) {
			PolicyAbstractInfoDto policyAbstractInfoDto = commonCheckTaskViewHelper.getPolicyAbstractInfo(wfLog,prpCmain);
			req.setAttribute("PolicyAbstractInfoDto", policyAbstractInfoDto);
			// 查询所有费用信息
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", wfLog.getBusinessNo());
			List<PrpLcharge> prplchargeList = prpLchargeService.findPrpLcharge(queryRule);
			req.setAttribute("prplchargeList", prplchargeList);
		}
		// 标的信息
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		prpLclaim.setClassCode(prpCmain.getClassCode());
		List<PrpCitemKind> itemKindList = null;
		String riskType = this.codeService.translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			String insuredCode = prpLclaim.getInsuredCode();
			String insuredName = prpLclaim.getInsuredName();
			List<PrpCinsured> prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
			PrpCinsured prpCinsured = this.endorseViewHelper.getPrpCinsured(prpCinsuredList, insuredCode, insuredName);
			itemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCinsured.getId().getSerialNo());
		} else {
			itemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), CommonUtils.nullToEmpty(prpCmain.getPolicyType()));
		}
		List<PrpLloss> prpLlossList = compensateService.getPrpLlossForReplevy(prpLclaim.getClaimNo());
		PrpCitemKind prpCitemKindDto = null;
		PrpLloss prpLloss = null;
		List<PrpCitemKind> tempList = null;
		List<PrpCitemKind> prpCitemKindList = new ArrayList<PrpCitemKind>();
		String strRiskType = codeService.translateRiskCodetoRiskType(prpLclaim.getRiskCode());
		for (int j=0;j<itemKindList.size();j++) {
			prpCitemKindDto = itemKindList.get(j);
			if(prpLlossList.size()>0){
				for(int i=0;i<prpLlossList.size();i++){
					prpLloss = prpLlossList.get(i);
					if(prpCitemKindDto.getKindCode().equals(prpLloss.getKindCode())){
						prpCitemKindDto.setHisPaid(prpLloss.getSumLoss());
					}
				}
			}
			if(ConstantCodes.CLASSCODE_Z.equals(strRiskType)||ConstantCodes.CLASSCODE_G.equals(strRiskType)||ConstantCodes.CLASSCODE_Q.equals(strRiskType)) {//责任险标的选择。
				tempList = prpCitemKindService.generateVirtualKind(prpCitemKindDto);
				if (!CommonUtils.isEmpty(tempList)) {
					prpCitemKindList.addAll(tempList);
					continue;
				}
			}
			String itemCode = codeService.getItemCode(prpCitemKindDto);
			//mantis：CLM0182，處理人員：CD078，需求單編號：新核心-車體險進廠維修提示訊息修改 START
			String kindCode = prpCitemKindDto.getKindCode();
			if (ConstantCodes.KINDCODE_A01_01.equals(kindCode) || ConstantCodes.KINDCODE_A01_05.equals(kindCode) || ConstantCodes.KINDCODE_A01_07.equals(kindCode) ||
					ConstantCodes.KINDCODE_A01_09.equals(kindCode) || ConstantCodes.KINDCODE_A01_0G.equals(kindCode) || ConstantCodes.KINDCODE_A01_0A.equals(kindCode) ||
					ConstantCodes.KINDCODE_A01_0B.equals(kindCode) || ConstantCodes.KINDCODE_A01_0C.equals(kindCode) || ConstantCodes.KINDCODE_A01_14.equals(kindCode)){
				setItemKindCheck(true);
			}
			//mantis：CLM0182，處理人員：CD078，需求單編號：新核心-車體險進廠維修提示訊息修改 END
			prpCitemKindDto.setItemCode(itemCode);
			String itemName = codeService.getItemName(prpCitemKindDto);
			prpCitemKindDto.setItemDetailName(itemName);
			prpCitemKindList.add(prpCitemKindDto);
		}
//		double r;
//		if(!"0".equals(policyDto.getPrpCmain().getCoinsFlag())){
//			
//		}
		req.setAttribute("prpCitemKindCollection", prpCitemKindList);
		// 肇事类型
		req.setAttribute("accidentTypeList", ConstantsCollection.accidentTypeList);

	}

	private List<PrpCitemKind> getFilterDistinct(List<PrpCitemKind> prpCitemKindList) {
		Map<String, PrpCitemKind> map = new HashMap<String, PrpCitemKind>();
		for (int i=0;i<prpCitemKindList.size();i++) {
			PrpCitemKind prpCitemKindDto = prpCitemKindList.get(i);
			map.put(prpCitemKindDto.getKindCode(), prpCitemKindDto);
		}
		return new ArrayList<PrpCitemKind>(map.values());
	}

	public String getPrpallUrl() {
		if (prpallUrl == null || "".equals(prpallUrl.trim())) {
			try {
				prpallUrl = AppConfig.get("sysconst.Core_URL");
			} catch (Exception e) {
				prpallUrl = "";
			}
		}
		return prpallUrl;
	}

	public void setPrpallUrl(String prpallUrl) {
		this.prpallUrl = prpallUrl;
	}

	public CommonCheckTaskViewHelper getCommonCheckTaskViewHelper() {
		return commonCheckTaskViewHelper;
	}

	public void setCommonCheckTaskViewHelper(CommonCheckTaskViewHelper commonCheckTaskViewHelper) {
		this.commonCheckTaskViewHelper = commonCheckTaskViewHelper;
	}

	public String getUndwrt_continuetask() {
		if (undwrt_continuetask == null || "".equals(undwrt_continuetask.trim())) {
			try {
				undwrt_continuetask = AppConfig.get("sysconst.UNDWRT_CONTINUETASK");
			} catch (Exception e) {
				undwrt_continuetask = "";
			}
		}
		return undwrt_continuetask;
	}

	public void setUndwrt_continuetask(String undwrt_continuetask) {
		this.undwrt_continuetask = undwrt_continuetask;
	}

	public PrpLcfeecoinsService getPrpLcfeecoinsService() {
		return prpLcfeecoinsService;
	}

	public void setPrpLcfeecoinsService(PrpLcfeecoinsService prpLcfeecoinsService) {
		this.prpLcfeecoinsService = prpLcfeecoinsService;
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

	public PrpLchargeService getPrpLchargeService() {
		return prpLchargeService;
	}

	public void setPrpLchargeService(PrpLchargeService prpLchargeService) {
		this.prpLchargeService = prpLchargeService;
	}

	public PrpDriskService getPrpDriskService() {
		return prpDriskService;
	}

	public void setPrpDriskService(PrpDriskService prpDriskService) {
		this.prpDriskService = prpDriskService;
	}

	public PrpCcoinsService getPrpCcoinsService() {
		return prpCcoinsService;
	}

	public void setPrpCcoinsService(PrpCcoinsService prpCcoinsService) {
		this.prpCcoinsService = prpCcoinsService;
	}

	public PrpCmainService getPrpCmainService() {
		return prpCmainService;
	}

	public void setPrpCmainService(PrpCmainService prpCmainService) {
		this.prpCmainService = prpCmainService;
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

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
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

	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}

	public CompensateService getCompensateService() {
		return compensateService;
	}

	public void setCompensateService(CompensateService compensateService) {
		this.compensateService = compensateService;
	}

	public PrpCitemKindService getPrpCitemKindService() {
		return prpCitemKindService;
	}

	public void setPrpCitemKindService(PrpCitemKindService prpCitemKindService) {
		this.prpCitemKindService = prpCitemKindService;
	}


	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(
			PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public PrpLuserService getPrpLuserService() {
		return prpLuserService;
	}

	public void setPrpLuserService(PrpLuserService prpLuserService) {
		this.prpLuserService = prpLuserService;
	}
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END

	//mantis：CLM0182，處理人員：CD078，需求單編號：新核心-車體險進廠維修提示訊息修改 START
	public boolean isItemKindCheck() {
		return itemKindCheck;
	}

	public void setItemKindCheck(boolean itemKindCheck) {
		this.itemKindCheck = itemKindCheck;
	}
	//mantis：CLM0182，處理人員：CD078，需求單編號：新核心-車體險進廠維修提示訊息修改 END

	// mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -- start
	public PrpLCitemKindService getPrpLCitemKindService() {
		return prpLCitemKindService;
	}

	public void setPrpLCitemKindService(PrpLCitemKindService prpLCitemKindService) {
		this.prpLCitemKindService = prpLCitemKindService;
	}
    // mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -- end
	
	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 START
	public UndwrtTaskDealViewHelper getUndwrtTaskDealViewHelper() {
		return undwrtTaskDealViewHelper;
	}
	public void setUndwrtTaskDealViewHelper(UndwrtTaskDealViewHelper undwrtTaskDealViewHelper) {
		this.undwrtTaskDealViewHelper = undwrtTaskDealViewHelper;
	}
	public WfLogQueryViewHelper getWfLogQueryViewHelper() {
		return wfLogQueryViewHelper;
	}
	public void setWfLogQueryViewHelper(WfLogQueryViewHelper wfLogQueryViewHelper) {
		this.wfLogQueryViewHelper = wfLogQueryViewHelper;
	}
	public CommonDealTaskViewHelper getCommonDealTaskViewHelper() {
		return commonDealTaskViewHelper;
	}
	
	public void setCommonDealTaskViewHelper(
			CommonDealTaskViewHelper commonDealTaskViewHelper) {
		this.commonDealTaskViewHelper = commonDealTaskViewHelper;
	}
	//mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增 END


}
