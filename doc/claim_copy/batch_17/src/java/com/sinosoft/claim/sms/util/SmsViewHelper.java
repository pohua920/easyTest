package com.sinosoft.claim.sms.util;


import ins.framework.common.QueryRule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDriskConfigService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.dto.custom.UserDto;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDagent;
import com.sinosoft.claim.schema.model.PrpDriskConfig;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLsmsTemplate;
import com.sinosoft.claim.schema.service.facade.PrpDagentService;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLsmsTemplateService;
import com.sinosoft.claim.sms.service.facade.SmsService;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.AppConfig;

public class SmsViewHelper {
	/** 立案service */
	private PrpLclaimService prpLclaimService;
	/** 理算接口 */
	private PrpLcompensateService prpLcompensateService;
	/** 机构代码接口 */
	private PrpDcompanyService prpDcompanyService;
	/** 用户接口 */
	private PrpDuserService prpDuserService;
	/** 赔付对象接口 */
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/** 简讯模板接口 */
	private PrpLsmsTemplateService prpLsmsTemplateService;
	/** 发送简讯接口 */
	private SmsService smsService;
	private PrpDriskConfigService prpDriskConfigService;
	/** 批单viewHelper */
	private EndorseViewHelper endorseViewHelper;
	/** 台壽通路營業人員  */
	private PrpDagentService prpDagentService;
	private CodeService codeService;
	/**
	 * 发送简讯方法,返回值，-1表示没有发送过，0表示发送失败，1表示发送成功。
	 * @param businessNo
	 * @param nodeType
	 * @return
	 * @throws Exception
	 */
	public String sendSms(String businessNo,String nodeType) throws Exception{
		String success = "-1";
		try {
			String claimNo = null;
			if("veric".equals(nodeType)){
				PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
				claimNo = prpLcompensate.getClaimNo();
				//不包含負向調整、同業共攤、追償件
				if(!CommonUtils.isEmpty(prpLcompensate.getMutualCompensateNo())){
					return success;
				}
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.compensateNo", businessNo);
				queryRule.addEqual("id.certiType", PrpLpayObjectInfo.CERTITYPE_PAYOBJECT);
				List<PrpLpayObjectInfo>prpLpayObjectInfoList = prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule);
				String paycodeType = "";
				if(prpLpayObjectInfoList.size()>0){
					paycodeType = prpLpayObjectInfoList.get(0).getPaycodeType();
				}
				//不包含負向調整、同業共攤、追償件
				if("2".equals(paycodeType)||"3".equals(paycodeType)){
					return success;
				}
			}else if("claim".equals(nodeType)){
				claimNo = businessNo;
			}else{
				return success;
			}
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
			String configValue = this.getConfigValue(prpLclaim.getComCode(),prpLclaim.getRiskCode(),"CLAIM_SMS");
			if(!"1".equals(configValue)){
				return success;
			}
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("nodeType", nodeType);
			queryRule.addSql(" (riskCode like '%"+prpLclaim.getRiskCode()+"%' or riskCode is null )");
			queryRule.addEqual("validstatus", "1");
			List<PrpLsmsTemplate> prpLsmsTemplateList = prpLsmsTemplateService.findPrpLsmsTemplate(queryRule);
			
			if(prpLsmsTemplateList.size()>0){
				Map<String,Object> data= this.getData(businessNo, nodeType);
				success =  smsService.sendSms(prpLsmsTemplateList, data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 获取发送短信的数据
	 * @param claimNo
	 * @param nodeType
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getData(String businessNo,String nodeType) throws Exception{
		Map<String,Object> data = new HashMap<String,Object>();
		String claimNo = businessNo;
		UserDto userDto = (UserDto) ActionContext.getContext().getSession().get("user");
		//核赔节点
		if("veric".equals(nodeType)){
			PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
			claimNo = prpLcompensate.getClaimNo();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 8);
			calendar.set(Calendar.MINUTE, 30);
			data.put("submit_Date", smsService.getSubmitDate(calendar.getTime()));
//			data.put("payDate", "${payDate}");
			data.put("compensateNo", businessNo.substring(1));
			data.put("payMobile", getPayObjectMobile(businessNo));
		}
		PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
		String policyNo = prpLclaim.getPolicyNo();
		String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
		String damageHour = prpLclaim.getDamageStartHour();
		PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
		String riskType = this.codeService.translateRiskCodetoRiskType(prpCmain.getRiskCode());
		List<PrpCinsured> prpCinsuredList = null;
		if(ConstantCodes.CLASSCODE_E.equals(riskType)){
			String insuredCode = prpLclaim.getInsuredCode();
			String insuredName = prpLclaim.getInsuredName();
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour, insuredCode, insuredName);
		} else {
			prpCinsuredList = this.endorseViewHelper.findPrpCinsuredFromCopy(policyNo, damageDate, damageHour);
		}
		if("claim".equals(nodeType)){
			data.put("damageDate",getYearOffset(prpLclaim.getDamageStartDate()));
			data.put("companyPhone", this.getConfigValue(prpLclaim.getComCode(),prpLclaim.getRiskCode(),"CLAIM_SMS_COMPANY_MOBLIE"));
			data.put("insuredMobile", getInsuredMobile(prpCinsuredList, prpLclaim.getInsuredCode()));
		}
		String insuredName = prpLclaim.getInsuredName();
		data.put("insuredName",insuredName);
		data.put("userDto", userDto);
		data.put("claimName", userDto.getUserName());
		data.put("claimMobile", this.getConfigValue(prpLclaim.getComCode(),prpLclaim.getRiskCode(),"CLAIM_SMS_MOBLIE"));
		String handlerCode = prpCmain.getAgentCode();
		if(CommonUtils.isEmpty(handlerCode)){
			handlerCode = prpCmain.getHandlerCode();
		}
		this.getPrpDagentMobile(handlerCode,data);
		data.put("claimNo", claimNo);
		data.put("businessNo", businessNo);
		data.put("policyNo", prpLclaim.getPolicyNo());
		return data;
	}
	/**
	 * 设置明国年时间
	 * @param date
	 * @return
	 */
	public static String getYearOffset(Date date){
		DateTime dateTime = null;
		if(date==null){
			dateTime = DateTime.current();
		}else{
			dateTime = new DateTime(date);
		}
		dateTime = dateTime.addYear(-ConstantCodes.YEAROFFSET);
		DateFormat dateFormat = new SimpleDateFormat("yyy年MM月dd日");
		return dateFormat.format(dateTime);
	}
	/**
	 * 查询被保险人手机
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public String getInsuredMobile(List<PrpCinsured>prpCinsuredList,String insuredCode) throws Exception{
		StringBuffer mobile = new StringBuffer("");
		for(PrpCinsured prpCinsured :prpCinsuredList){
			if("1".equals(prpCinsured.getInsuredFlag())){
				if(!CommonUtils.isEmpty(prpCinsured.getMobile())){
					mobile.append(","+prpCinsured.getMobile());
				}else if(!CommonUtils.isEmpty(prpCinsured.getPhoneNumber())){
					mobile.append(","+prpCinsured.getPhoneNumber());
				}
			}
		}
		if(mobile.length()>0){
			return mobile.substring(1);
		}
		return "";
	}
	/**
	 * 查询赔付对象的电话
	 * @param compensateNo
	 * @return
	 * @throws Exception
	 */
	public String getPayObjectMobile(String compensateNo) throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", compensateNo);
		List<PrpLpayObjectInfo>prpLpayObjectInfoList = prpLpayObjectInfoService.findPrpLpayObjectInfo(queryRule);
		StringBuffer mobile = new StringBuffer("");
		for(PrpLpayObjectInfo prpLpayObjectInfo : prpLpayObjectInfoList){
			if(!CommonUtils.isEmpty(prpLpayObjectInfo.getMobilePhoneNo())){
				mobile.append(","+prpLpayObjectInfo.getMobilePhoneNo());
			}else if(!CommonUtils.isEmpty(prpLpayObjectInfo.getBeneficiaryPhone())){
				mobile.append(","+prpLpayObjectInfo.getBeneficiaryPhone());
			}
		}
		if(mobile.length()>0){
			return mobile.substring(1);
		}
		return "";
	}
	
	/**
	 * 台壽通路營業人員
	 * 台壽通路通訊處主管
	 * 台壽通路通訊處主管手机号码
	 * @param handlerCode
	 * @return
	 * @throws Exception
	 */
	public void getPrpDagentMobile(String handlerCode,Map<String,Object> data) throws Exception{
		String salesUser = AppConfig.get("sysconst.SALES_USER");
		PrpDagent prpDagent = prpDagentService.findSalesPrpDagent(salesUser, handlerCode);
		//营业人员手机号码和姓名
		data.put("handlerName",prpDagent.getAgentName());
		data.put("handlerMobile",prpDagent.getMobileNo());
		data.put("departmentMobile",prpDagent.getMobile());
	}
	
	/**
	 * 查询是否发送简讯配置
	 * @param riskCode
	 * @return
	 */
	public String  getConfigValue(String comCode,String riskCode,String configCode){
		String configValue = "";
		try {
			if(comCode==null){
				comCode = "00";
			}
			String conditions = " (comCode='"+comCode+"' or comCode ='00') and configCode='"+configCode+"' and (riskCode='0000' or riskCode='"+riskCode+"') order by comCode desc,riskCode desc";
			List<PrpDriskConfig> list = prpDriskConfigService.findByConditions(conditions);
			if(list.size()>0){
				configValue = list.get(0).getConfigValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configValue;
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

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}
	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}
	public PrpDuserService getPrpDuserService() {
		return prpDuserService;
	}
	public void setPrpDuserService(PrpDuserService prpDuserService) {
		this.prpDuserService = prpDuserService;
	}
	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}
	public void setPrpLpayObjectInfoService(PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}
	public PrpLsmsTemplateService getPrpLsmsTemplateService() {
		return prpLsmsTemplateService;
	}
	public void setPrpLsmsTemplateService(PrpLsmsTemplateService prpLsmsTemplateService) {
		this.prpLsmsTemplateService = prpLsmsTemplateService;
	}
	public SmsService getSmsService() {
		return smsService;
	}
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public PrpDriskConfigService getPrpDriskConfigService() {
		return prpDriskConfigService;
	}

	public void setPrpDriskConfigService(PrpDriskConfigService prpDriskConfigService) {
		this.prpDriskConfigService = prpDriskConfigService;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpDagentService getPrpDagentService() {
		return prpDagentService;
	}

	public void setPrpDagentService(PrpDagentService prpDagentService) {
		this.prpDagentService = prpDagentService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

}
