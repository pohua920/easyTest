package com.sinosoft.claim.compensate.web;

import ins.framework.utils.DataUtils;
import ins.framework.web.Struts2Action;

import java.net.URLDecoder;
//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數 START
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數 END
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.compensate.util.GroovyViewHelper;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
import com.sinosoft.claim.schema.model.PrpLregist;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLdisabilityLimitService;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
import com.sinosoft.claim.schema.service.facade.PrpLregistService;
//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核  START
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.common.util.EndorseViewHelper;
//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 END
import com.sinosoft.payment.common.util.DateUtil;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 车物损理赔金计算
 * @Description 
 * @author 中科软
 */
public class CompensateRealpayAction extends Struts2Action {

	/**
	 * @Fields serialVersionUID:
	 */
	private static final long serialVersionUID = 1L;
	/** 伤残等级Service */
	private PrpLdisabilityLimitService prpLdisabilityLimitService;
	private PrpLcompensateService prpLcompensateService;
	private CodeService codeService;
	private PrpLclaimService prpLclaimService;
	private PrpLpersonLossService prpLpersonLossService;
	private PrpLlossService prpLlossService;
	//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
	private PrpLregistService prpLregistService;
	//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 
	private EndorseViewHelper endorseViewHelper;

	public PrpLdisabilityLimitService getPrpLdisabilityLimitService() {
		return prpLdisabilityLimitService;
	}

	public void setPrpLdisabilityLimitService(PrpLdisabilityLimitService prpLdisabilityLimitService) {
		this.prpLdisabilityLimitService = prpLdisabilityLimitService;
	}

	/**
	 * 车物损理赔金计算
	 * @return
	 * @throws Exception
	 */
	public String calPrpLlossRealpay() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			PrpLloss prpLloss = new PrpLloss();
			String kindCode = request.getParameter("kindCode");// 险别
			if (DataUtils.emptyToNull(kindCode) != null) {
				double sumDefPay = Double.valueOf(DataUtils.nullToZero(request.getParameter("sumDefPay")));// 核定赔偿
				double compelPay = Double.valueOf(DataUtils.nullToZero(request.getParameter("compelPay")));// 强制险给付金额
				double sumRest = Double.valueOf(DataUtils.nullToZero(request.getParameter("sumRest")));// 剔除金额、残值
				double depreRate = Double.valueOf(DataUtils.nullToZero(request.getParameter("depreRate")));// 折旧率
				double dutyDeductibleRate = Double.valueOf(DataUtils.nullToZero(request.getParameter("dutyDeductibleRate")));// 自负额比例
				double deductible = Double.valueOf(DataUtils.nullToZero(request.getParameter("deductible")));// 自负额
				double indemnityDutyRate = Double.valueOf(DataUtils.nullToZero(request.getParameter("indemnityDutyRate")));// 肇事責任比率
				prpLloss.setKindCode(kindCode);
				prpLloss.setSumDefPay(sumDefPay);
				prpLloss.setCompelPay(compelPay);
				prpLloss.setSumRest(sumRest);
				prpLloss.setDepreRate(depreRate);
				prpLloss.setIndemnityDutyRate(indemnityDutyRate);
				prpLloss.setDutyDeductibleRate(dutyDeductibleRate);
				prpLloss.setDeductible(deductible);
				prpLloss.setKindName(URLDecoder.decode(request.getParameter("kindName"), "UTF-8"));
				// 提取理算公式並计算理赔金
				// 差异化险种未录入，此处仍调旧的
				double sumRealPay = (Double) GroovyViewHelper.evaluate(prpLloss);
				prpLloss.setSumRealPay(sumRealPay);
				jsonMap.put("prpLloss", prpLloss);
			} else {
				jsonMap.put("errorMessage", getText("prompt.compensate.selectRiskScanAmount"));//"請選擇險別，在錄入金額。"
			}
		} catch (UserException e) {
			jsonMap.put("errorMessage", e.getErrorMessage());
		} catch (Exception e) {
			jsonMap.put("errorMessage", e.getMessage());
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	/**
	 * @Description: 
	 * @author 中科软
	 * @date May 11, 2013 9:09:00 PM
	 * @return
	 * @throws Exception
	 */
	public String calPrpLpersonLossRealpay() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			PrpLpersonLoss prpLpersonLoss = new PrpLpersonLoss();
			String kindCode = request.getParameter("kindCode");
			String liabDetailCode = request.getParameter("liabDetailCode");// 险别
			if (DataUtils.emptyToNull(kindCode) != null) {
				String claimNo = request.getParameter("claimNo");
				String ratingCode = request.getParameter("ratingCode");// 残废等级
				double sumDefPay = Double.valueOf(DataUtils.nullToZero(request.getParameter("sumDefPay")));// 核定赔偿
				double compelPay = Double.valueOf(DataUtils.nullToZero(request.getParameter("compelPay")));// 强制险给付金额
				double sumRest = Double.valueOf(DataUtils.nullToZero(request.getParameter("sumRest")));// 剔除金额、残值、自负额
				double indemnityDutyRate = Double.valueOf(DataUtils.nullToZero(request.getParameter("indemnityDutyRate")));// 肇事責任比率
				String medicDeathFlag = request.getParameter("medicDeathFlag");
				prpLpersonLoss.setKindCode(kindCode);
				prpLpersonLoss.setSumDefPay(sumDefPay);
				prpLpersonLoss.setCompelPay(compelPay);
				prpLpersonLoss.setLiabDetailCode(liabDetailCode);
				prpLpersonLoss.setSumRest(sumRest);
				prpLpersonLoss.setIndemnityDutyRate(indemnityDutyRate);
				prpLpersonLoss.setInjuryGrade(ratingCode);
				prpLpersonLoss.setFeeCategory(medicDeathFlag);
				prpLpersonLoss.setKindName(URLDecoder.decode(request.getParameter("kindName"), "UTF-8"));
				// 提取理算公式並计算理赔金
				// 差异化险种未录入，此处仍调旧的
				prpLpersonLoss.setClaimNo(claimNo);
				double sumRealPay = (Double) GroovyViewHelper.evaluate(prpLpersonLoss);
				prpLpersonLoss.setSumRealPay(sumRealPay);
				jsonMap.put("prpLpersonLoss", prpLpersonLoss);
			} else {
				jsonMap.put("errorMessage", getText("prompt.compensate.selectRiskScanAmount"));//"請選擇險別，在錄入金額。"
			}
		} catch (UserException e) {
			jsonMap.put("errorMessage", e.getErrorMessage());
		} catch (Exception e) {
			jsonMap.put("errorMessage", e.getMessage());
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}

	/**
	 * 强制险根据出险时间和伤残等级获取固定赔付
	 * @return
	 * @throws Exception
	 */
	public String getCrippledPay() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String claimNo = request.getParameter("claimNo");
		String ratingCode = request.getParameter("ratingCode");// 残废等级
		try {
			double limitFee = prpLdisabilityLimitService.getPrpLdisabilityLimitFee(claimNo, ratingCode);
			jsonMap.put("limitFee", limitFee);
			response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		} catch (Exception e) {
			//獲取	失能賠償失敗!
			jsonMap.put("errorMessage", getText("prompt.compensate.query") + ratingCode + getText("prompt.compensate.abandonPayFailed") + e.getMessage());
			response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		}
		return NONE;
	}
	
	/**
	 * 獲取本案車體險和責任險肇責情況
	 * @return
	 * @throws Exception
	 */
	public String getResponsAccidentType() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String claimNo = request.getParameter("claimNo");// 賠案號碼
			if (DataUtils.emptyToNull(claimNo) != null) {
				PrpLclaim prpLclaim = this.prpLclaimService.findPrpLclaim(claimNo);
				//獲取各個險種一結肇責類型
				jsonMap.put("claimPropAccidentType", prpLclaim.getPropAccidentType());
				jsonMap.put("claimCarAccidentType", prpLclaim.getCarAccidentType());
				String conditions = " compensateNo like 'C"+claimNo+"%' and ( underWriteFlag = '1' or underWriteFlag = '3' ) and mutualCompensateNo is null order by underWriteEndDate asc , times asc ";
				List<PrpLcompensate> compensateList = this.getPrpLcompensateService().findByConditions(conditions);
				if(!CommonUtils.isEmpty(compensateList)){//非一結
					List<PrpLloss> tempPrpLlossList = null;
					List<PrpLpersonLoss> tempPrpLpersonLossList = null;
					String tempKindCode = null;
					String compeCarAccidentType = "";//一結車體險肇責
					String compePropAccidentType = "";//一結責任險肇責
					List<String> carKindCode = this.codeService.getResponKindCode(1);//車體險險種
					List<String> propKindCode = this.codeService.getResponKindCode(0);//責任險險種
					double carPay = 0d;
					double propPay = 0d;
					for(PrpLcompensate compe : compensateList){
						if(CommonUtils.isEmpty(compeCarAccidentType) && !CommonUtils.isEmpty(compe.getAccidentType()) ){
							compeCarAccidentType = compe.getAccidentType();
						}
						if(CommonUtils.isEmpty(compePropAccidentType) && !CommonUtils.isEmpty(compe.getPropAccidentType()) ){
							compePropAccidentType = compe.getPropAccidentType();
						}
						if(!CommonUtils.isEmpty(compe.getAccidentType()) || !CommonUtils.isEmpty(compe.getPropAccidentType())){
							tempPrpLpersonLossList = this.prpLpersonLossService.findByConditions(" compensateNo = '"+compe.getCompensateNo()+"'");
							for(PrpLpersonLoss p : tempPrpLpersonLossList){
								tempKindCode = p.getKindCode();
								if(carKindCode.contains(tempKindCode)){
									carPay += p.getSumRealPay();
								} else if(propKindCode.contains(tempKindCode)){
									propPay += p.getSumRealPay();
								}
							}
							if(ConstantCodes.RISKCODE_DAZ.equals(compe.getRiskCode())){
								continue;
							}
							tempPrpLlossList = this.prpLlossService.findByConditions(" compensateNo = '"+compe.getCompensateNo()+"'");
							for(PrpLloss p : tempPrpLlossList){
								tempKindCode = p.getKindCode();
								if(carKindCode.contains(tempKindCode)){
									carPay += p.getSumRealPay();
								} else if(propKindCode.contains(tempKindCode)){
									propPay += p.getSumRealPay();
								}
							}
						}
					}
					jsonMap.put("carFlag", !CommonUtils.isEmpty(compeCarAccidentType));//有無車體險賠付標記
					jsonMap.put("propFlag", !CommonUtils.isEmpty(compePropAccidentType));//有無責任險賠付標記
					jsonMap.put("compeCarAccidentType", compeCarAccidentType);
					jsonMap.put("compePropAccidentType", compePropAccidentType);
					jsonMap.put("carPay", carPay);
					jsonMap.put("propPay", propPay);
				}
			} else {
				jsonMap.put("msg", "未取得賠案資料！");//"請選擇險別，在錄入金額。"
			}
		} catch (Exception e) {
			jsonMap.put("msg", e.getMessage());
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}
	
	/**
	 * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
	 * @return
	 * @throws Exception
	 */
	public String checkDateBetweenHaventDuplicateCase() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		int duplicateCase = 0;
		try {
			String policyNo = (String)request.getParameter("policyNo");
			Map<String,Date> duplicated = new HashMap<String, Date>();
			List<PrpLregist> prpLregistList = prpLregistService.findSameLicensenoRegist(policyNo);
			for(PrpLregist prpLregist:prpLregistList){
				String Licenseno = prpLregist.getLicenseNo();
				List<Date> listOfDates = getDaysBetweenDates(prpLregist.getDamageStartDate(), prpLregist.getDamageEndDate());
				for(Date day:listOfDates){
					String task = Licenseno+"_"+day;
					if(duplicated.get(task)==null){
						duplicated.put(task, day);
					}else{
						duplicateCase++;
					}
				}
			}
			jsonMap.put("duplicateCase", duplicateCase);
		} catch (Exception e) {
			jsonMap.put("errorMessage", e.getMessage());
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}
	/**
	 * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
	 * @return
	 */
	public List<Date> getDaysBetweenDates(Date startdate, Date enddate)
	{
	    List<Date> dates = new ArrayList<>();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(startdate);
	    
	    if(startdate.equals(enddate)){
	        Date result = calendar.getTime();
	        dates.add(result);
	        calendar.add(Calendar.DATE, 1);
	    }else{
		    while (calendar.getTime().before(enddate))
		    {
		        Date result = calendar.getTime();
		        dates.add(result);
		        calendar.add(Calendar.DATE, 1);
		    }
	    }
	    return dates;
	}
	/**
	 * mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數
	 * @return
	 * @throws Exception
	 */
	public String checkDateBeforeDamageDate() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Boolean rtn = null;
		try {
			String claimNo = (String)request.getParameter("claimNo");
			String rsd = (String)request.getParameter("repairStartDate");
			Date repairStartDate = null;
			if(null!=rsd && ""!=rsd){
				String[] tmp1 = rsd.split("-");
				
				repairStartDate = DateUtil.formatDate((1911+Integer.parseInt(tmp1[0]))+"-"+tmp1[1]+"-"+tmp1[2]+" 00:00:01", "yyyy-MM-dd HH:mm:ss");
			}
			Map<String,Date> duplicated = new HashMap<String, Date>();
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
			if(null!=prpLclaim.getDamageStartDate()){
				if(prpLclaim.getDamageStartDate().before(repairStartDate)){//進廠日期(repairStartDate)需大於等於出險時間(DamageStartDate)
					rtn = true;
				}else{
					rtn = false;
				}
			}
			jsonMap.put("beforeDamageDate", rtn);
		} catch (Exception e) {
			jsonMap.put("errorMessage", e.getMessage());
			jsonMap.put("beforeDamageDate", null);
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}
	

	/**
	 * mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核
	 * 保險額度單日保額(元)
	 * @return
	 * @throws Exception
	 */
	public String calDayPerAmount() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/html;charset=GBK");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			String policyNo = request.getParameter("policyNo");
			String kindCode = request.getParameter("kindCode");
			String dayPerAmount = "";
//			policyNo = "181223000112";//FOR TEST
//			kindCode = "0C";//FOR TEST
			List<PrpCitemKind> prpCitemKindList =this.endorseViewHelper.findPrpCitemKind(policyNo, kindCode);
			if(prpCitemKindList!=null && prpCitemKindList.size()>0){
				jsonMap.put("dayPerAmount", null!=prpCitemKindList.get(0).getModel()?prpCitemKindList.get(0).getModel():"0");
				jsonMap.put("quamtity", null!=prpCitemKindList.get(0).getQuantity()?prpCitemKindList.get(0).getQuantity():"0");
			}
		} catch (Exception e) {
			jsonMap.put("errorMessage", e.getMessage());
		}
		response.getWriter().write(JSONObject.fromObject(jsonMap).toString());
		return NONE;
	}
	

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}

	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

	//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數 START
	public PrpLregistService getPrpLregistService() {
		return prpLregistService;
	}

	public void setPrpLregistService(PrpLregistService prpLregistService) {
		this.prpLregistService = prpLregistService;
	}
	//mantis：CLM0175，處理人員：DP0713，需求單編號：新核心-車險計算書新增理賠已出險次數 END

	//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核  START
	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}
	//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核  END
	
}
