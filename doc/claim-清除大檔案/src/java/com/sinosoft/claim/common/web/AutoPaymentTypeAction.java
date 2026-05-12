package com.sinosoft.claim.common.web;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLpaymentType;
import com.sinosoft.claim.schema.service.facade.PrpLpaymentTypeService;

import ins.framework.web.Struts2Action;

/**
 * 自动提示给付类别信息
 * @author 中科软
 *
 */
public class AutoPaymentTypeAction extends Struts2Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8552168125905172061L;
	/** 承保范围 */
	private String contractingScope;
	/** 给付类别*/
	private String paymentType;
	/** 给付类别1*/
	private String paymentType1;
	/** 给付类别2*/
	private String paymentType2;
	private String codeType;
	/** 给付类别service*/
	private PrpLpaymentTypeService prpLpaymentTypeService;
	/** 
	 * 查询出给付类别
	 */
	public String autoPaymentType()throws Exception{
		String result = null;
		try {
			PrpLpaymentType prpLpaymentType = new PrpLpaymentType();
			prpLpaymentType.setContractingScope(contractingScope);
			prpLpaymentType.getId().setType(paymentType);
			prpLpaymentType.setType1(paymentType1);
			prpLpaymentType.setType2(paymentType2);
			List<PrpLpaymentType> list = prpLpaymentTypeService.findPrpLpaymentType(prpLpaymentType,codeType, 1, 0);
			if(list.size()>0){
				StringBuffer sb = new StringBuffer("[");
				for(PrpLpaymentType paymentTypeTemp : list){
					sb.append("{\"type\":\"").append(paymentTypeTemp.getId().getType())
					.append("\",\"type1\":\"").append(paymentTypeTemp.getType1())
					.append("\",\"type2\":\"").append(paymentTypeTemp.getType2())
					.append("\",\"content\":\"").append(paymentTypeTemp.getContent())
					.append("\",\"injuryGrade\":\"").append(paymentTypeTemp.getInjuryGrade())
					.append("\",\"paymentRate\":\"").append(paymentTypeTemp.getPaymentRate())
					.append("\"},");
				}
				result = sb.substring(0, sb.length() - 1) + "]";
			}else{
				result = "{\"message\":\"沒有找到數據\"}";
			}
		} catch (Exception e) {
			result = "{\"message\":\"數據錯誤\"}";
			e.printStackTrace();
		}
		this.renderJSON(result);
		return NONE;
	}
	
	public String verificationPaymentType()throws Exception{
		String result = null;
		try {
			PrpLpaymentType prpLpaymentType = new PrpLpaymentType();
			prpLpaymentType.setContractingScope(contractingScope);
			prpLpaymentType.getId().setType(paymentType);
			prpLpaymentType.setType1(paymentType1);
			prpLpaymentType.setType2(paymentType2);
			if("paymentType2".equals(codeType)){
				List<PrpLpaymentType> list = prpLpaymentTypeService.getPrpLpaymentType(prpLpaymentType, codeType);
				if(list.size()>0){
					StringBuffer sb = new StringBuffer("{\"message\":\"true\",\"result\":[");
					for(PrpLpaymentType paymentTypeTemp : list){
						sb.append("{\"type\":\"").append(paymentTypeTemp.getId().getType())
						.append("\",\"type1\":\"").append(paymentTypeTemp.getType1())
						.append("\",\"type2\":\"").append(paymentTypeTemp.getType2())
						.append("\",\"content\":\"").append(paymentTypeTemp.getContent())
						.append("\",\"injuryGrade\":\"").append(paymentTypeTemp.getInjuryGrade())
						.append("\",\"paymentRate\":\"").append(paymentTypeTemp.getPaymentRate())
						.append("\"},");
					}
					result = sb.substring(0, sb.length() - 1) + "]}";
				}else{
					result = "{\"message\":\"false\"}";
				}
			}else{
				Long count = prpLpaymentTypeService.countPrpLpaymentType(prpLpaymentType, codeType);
				if(count>0){
					result = "{\"message\":\"true\"}";
				}else{
					result = "{\"message\":\"false\"}";
				}
			}
		} catch (Exception e) {
			result = "{\"message\":\"false\"}";
		}
		this.renderJSON(result);
		return NONE;
	}
	
	public String getContractingScope() {
		return contractingScope;
	}
	public void setContractingScope(String contractingScope) {
		this.contractingScope = contractingScope;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentType1() {
		return paymentType1;
	}
	public void setPaymentType1(String paymentType1) {
		this.paymentType1 = paymentType1;
	}
	public String getPaymentType2() {
		return paymentType2;
	}
	public void setPaymentType2(String paymentType2) {
		this.paymentType2 = paymentType2;
	}
	public PrpLpaymentTypeService getPrpLpaymentTypeService() {
		return prpLpaymentTypeService;
	}
	public void setPrpLpaymentTypeService(PrpLpaymentTypeService prpLpaymentTypeService) {
		this.prpLpaymentTypeService = prpLpaymentTypeService;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

}
