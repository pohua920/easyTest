package com.sinosoft.claim.compensate.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpayObjectInfo;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.service.facade.PrpLlossService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectInfoService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;

import ins.framework.common.QueryRule;
import ins.framework.web.Struts2Action;

public class CompensatePersonHistoryAction  extends Struts2Action {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 人伤损失Service */
	private PrpLpersonLossService prpLpersonLossService;
	/** 立案号码 */
	private String prpLcompensateClaimNo;
	/** 保单号码*/
	private String prpLcompensatePolicyNo;
	private String prpLpersonLossIdentifyNumber;
	/** 代码处理Service */
	private CodeService codeService;
	/** 賠付對象service*/
	private PrpLpayObjectInfoService prpLpayObjectInfoService;
	/**
	 * 车物损对象
	 */
	private PrpLlossService prpLlossService;
	private PrpLloss prpLloss;
	/**
	 * 查询历史赔付人信息
	 * @return
	 * @throws Exception
	 */
	public String compensatePersonHistory()throws Exception{
		if(prpLcompensateClaimNo!=null&&!"".equals(prpLcompensateClaimNo)){
			List<PrpLpersonLoss> prpLpersonLossTemp = prpLpersonLossService.findPersonHistory(prpLcompensateClaimNo);
			PrpLpersonLoss prpLpersonLoss = null;
			String kindName = null;
			String strPersonNo = null;
			PrpLpersonLoss temp = null;
			//用map去掉重复的值
			Map<String,PrpLpersonLoss> prpLpersonLossMap = new HashMap<String,PrpLpersonLoss>(prpLpersonLossTemp.size());
			Map<String, String> payCodeTypeMap = new HashMap<String, String>(prpLpersonLossTemp.size());
			for(int i=0;i<prpLpersonLossTemp.size();i++){
				prpLpersonLoss = prpLpersonLossTemp.get(i);
				kindName = codeService.translateCode(prpLpersonLoss.getRiskCode(),prpLpersonLoss.getKindCode(),true);
				prpLpersonLoss.setKindName(kindName);
				strPersonNo = prpLpersonLoss.getId().getCompensateNo()+prpLpersonLoss.getPersonNo();
				temp = prpLpersonLossMap.get(strPersonNo);
				if(temp!=null){
					temp.setSumRealPay(temp.getSumRealPay()+prpLpersonLoss.getSumRealPay());
					prpLpersonLossMap.put(strPersonNo, temp);
				}else{
					prpLpersonLossMap.put(strPersonNo, prpLpersonLoss);
				}
				String payCodeTypeTmp = payCodeTypeMap.get(strPersonNo);
				if(payCodeTypeTmp == null){
					String classCode = codeService.translateClassCodeByRiskCode(prpLpersonLoss.getRiskCode());
					if(classCode.equals(ConstantCodes.CLASSCODE_D_B)){
						List<PrpLpayObjectInfo> prpLpayObjectInfoList = this.prpLpayObjectInfoService.findPrpLpayObjectInfo(QueryRule.getInstance().addEqual("id.compensateNo", prpLpersonLoss.getId().getCompensateNo()));
						if(!CommonUtils.isEmpty(prpLpayObjectInfoList)){
							payCodeTypeMap.put(strPersonNo, ConstantsCollection.prpLpayObjectInfoPaycodeTypeList.get(prpLpayObjectInfoList.get(0).getPaycodeType()));
						}else{
							payCodeTypeMap.put(strPersonNo, "");
						}
					}else{
						payCodeTypeMap.put(strPersonNo,ConstantsCollection.prpLpayObjectInfoPaycodeTypeList.get("1"));
					}
				}
			}
			HttpServletRequest request = this.getRequest();
			request.setAttribute("prpLpersonLossList", prpLpersonLossMap.values());
			request.setAttribute("identityOfInjuredPersonList", ConstantsCollection.identityOfInjuredPersonList);
			request.setAttribute("payCodeTypeMap", payCodeTypeMap);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询理算赔付额和最大赔付额
	 * @return
	 * @throws Exception
	 */
	public String compensatePersonHisPaid()throws Exception{
		double maxPaid = 0;
		double hisPaid = 0;
		try {
			if(prpLcompensatePolicyNo!=null&&!"".equals(prpLcompensatePolicyNo)){
				List<PrpLpersonLoss> personLossList = prpLpersonLossService.findPersonHisPaid(prpLcompensatePolicyNo, prpLpersonLossIdentifyNumber);
				for(PrpLpersonLoss prpLpersonLoss :	personLossList){
					if(maxPaid<prpLpersonLoss.getSumRealPay()){
						maxPaid = prpLpersonLoss.getSumRealPay();
					}
					hisPaid += prpLpersonLoss.getSumRealPay();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String messages = "{\"maxPaid\":"+maxPaid+",\"hisPaid\":"+hisPaid+"}";
		this.renderJSON(messages);
		return null;
	}
	/**
	 * 查询理算累计赔付额
	 * @return
	 * @throws Exception
	 */
	public String compensateLossHisPaid()throws Exception{
		double maxPaid = 0;
		double hisPaid = 0;
		try {
			if(prpLloss!=null){
				hisPaid = prpLlossService.findLossHisPaid(prpLloss);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String messages = "{\"maxPaid\":"+maxPaid+",\"hisPaid\":"+hisPaid+"}";
		this.renderJSON(messages);
		return null;
	}

	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}

	public String getPrpLcompensateClaimNo() {
		return prpLcompensateClaimNo;
	}

	public void setPrpLcompensateClaimNo(String prpLcompensateClaimNo) {
		this.prpLcompensateClaimNo = prpLcompensateClaimNo;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpLpayObjectInfoService getPrpLpayObjectInfoService() {
		return prpLpayObjectInfoService;
	}

	public void setPrpLpayObjectInfoService(
			PrpLpayObjectInfoService prpLpayObjectInfoService) {
		this.prpLpayObjectInfoService = prpLpayObjectInfoService;
	}

	public String getPrpLcompensatePolicyNo() {
		return prpLcompensatePolicyNo;
	}

	public void setPrpLcompensatePolicyNo(String prpLcompensatePolicyNo) {
		this.prpLcompensatePolicyNo = prpLcompensatePolicyNo;
	}

	public String getPrpLpersonLossIdentifyNumber() {
		return prpLpersonLossIdentifyNumber;
	}

	public void setPrpLpersonLossIdentifyNumber(String prpLpersonLossIdentifyNumber) {
		this.prpLpersonLossIdentifyNumber = prpLpersonLossIdentifyNumber;
	}

	public PrpLlossService getPrpLlossService() {
		return prpLlossService;
	}

	public void setPrpLlossService(PrpLlossService prpLlossService) {
		this.prpLlossService = prpLlossService;
	}

	public PrpLloss getPrpLloss() {
		return prpLloss;
	}

	public void setPrpLloss(PrpLloss prpLloss) {
		this.prpLloss = prpLloss;
	}
	
}
