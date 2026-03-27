package com.sinosoft.claim.undwrt.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sinosoft.one.rule.domain.InputBOM;
/**
 * 核赔规则条件判断
 * @author 中科软
 *
 */

public class UndwrtRuleCondition implements InputBOM, Serializable {
	private static final long serialVersionUID = 1L;

	/**核赔等级*/
	private String leave = "1";
	/**險種代碼*/
	private String riskCode;

	/**用户的userCode*/
	private String userCode;

	/**审核的部门*/
	private String comCode;

	/**总核赔金额*/
	private double sumPaid = 0;
	/** 审核结果*/
	private boolean result = true;
	/**核保不通过的原因*/
	private String strResultMessage = "";
	/**是否走规则表*/
	private boolean rulesCheckFlag = false; 

	/**是否倒签*/
	private String backOperation;

	/**分险别的核赔金额*/
	private double kindSumRealPay = 0;

	/**是否实收*/
	private int realPayFlag;

	/**核赔类型*/
	private String uwType;
	
	/**所有计算书核赔金额之和*/
	private double sumSumPaid = 0;
	
	private List<String> resultList = new ArrayList<String>(5);
	
	private List<UndwrtRelationCondition> undwrtRelationList = null;
	private double simpleSumPaid = 0;
	private String simpelFlag;
	
	/**
	 * 判断是否为核赔险种
	 * @param riskCodes
	 * @return
	 */
	public boolean isUndwrtRiskCode(String param) {
		boolean flag = true;
		if (param != null && !"".equals(param)) {
			List<String> riskCodesList = Arrays.asList(param.split(","));
			flag = riskCodesList.contains("*");// 核赔全部机构
			if (!flag) {
				flag = riskCodesList.contains(riskCode);
			}
			flag = this.isUndwrtRelationRiskCode(param,flag);
		}
		if(flag){
			rulesCheckFlag = true;
		}
		return flag;
	}
	/**
	 * 判断 拆分险别 GA--> GA,HG
	 * PA--》PA，HP
	 * @param param
	 * @param flag
	 * @return
	 */
	public boolean isUndwrtRelationRiskCode(String param,boolean flag){
		if(flag){
			return flag;
		}
		if(undwrtRelationList==null||undwrtRelationList.size()<1){
			return false;
		}else{
			List<String> riskCodesList = Arrays.asList(param.split(","));
			for(UndwrtRelationCondition condition : undwrtRelationList){
				if(riskCodesList.contains(condition.getKindCode())){
					condition.setResult(true);
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 核赔类型
	 * @param param
	 * @return
	 */
	public boolean isUwType(String param){
		rulesCheckFlag = true;
		return true;
	}
	
	/**
	 * 核赔等级
	 * @param param
	 * @return
	 */
	public boolean isLeave(String  param){
		if(leave!=null&&!"".equals(leave)&&param!=null&&!"".equals(param)){
			boolean flag = Integer.parseInt(leave)>Integer.parseInt(param);
			if(flag){
				resultList.add("leave");
			}
		}
		return true;
	}
	
	/**
	 * 判断缴费标志
	 * @param realPayFlags
	 * @return
	 */
	public boolean isRealPayFlag(String param){
		boolean flag = true;
		if(param!=null&&!"".equals(param)){
			flag = this.realPayFlag>=Integer.parseInt(param);
			if(!flag){
				resultList.add("realPayFlag");
			}
		}
		return true;
	}
	/**
	 * 判断核赔机构
	 * @param comCodes
	 * @return
	 */
	public boolean isComCode(String param) {
		if (param != null && !"".equals(param)) {
			List<String> comCodeList = Arrays.asList(param.split(","));
			boolean isComCode = comCodeList.contains("*");
			if (!isComCode) {
				isComCode = comCodeList.contains(comCode);
			}
			if(!isComCode){
				resultList.add("comCode");
			}
		}
		return true;
	}
	
	/**
	 * 判斷核賠金額
	 * @param minLimit
	 * @param maxLimit
	 * @return
	 */
	public boolean isAmmounts(String param1, String param2) {
		boolean relation = false;
		if(undwrtRelationList!=null&&undwrtRelationList.size()>0){
			for(UndwrtRelationCondition condition : undwrtRelationList){
				if(condition.getResult()){
					relation = true;
					break;
				}
			}
		}
		double dMaxLimit = -1;
		double dMinLimit = -1;
		boolean isAmmounts = true;
		if(null!=param1&&!"".equals(param1)){
			dMinLimit = Double.parseDouble(param1.trim());
		}
		if(null!=param2&&!"".equals(param2)){
			dMaxLimit = Double.parseDouble(param2.trim());
		}
		if(relation){
			isAmmounts = this.isRelationAmmounts(dMinLimit,dMaxLimit);
		}else{
			if(dMinLimit>0&&dMaxLimit >= 0){
				isAmmounts = sumSumPaid > dMinLimit&&sumSumPaid <= dMaxLimit;
			}else if(dMinLimit>0){
				isAmmounts = sumSumPaid > dMinLimit;
			}else if(dMaxLimit>=0){
				isAmmounts = sumSumPaid <= dMaxLimit;
			}else{
				isAmmounts = true;
			}
		}
		if(!isAmmounts){
			resultList.add("ammounts");
		}
		return true;
	}
	/**
	 * 判斷核賠金額
	 * @param minLimit
	 * @param maxLimit
	 * @return
	 */
	public boolean isSimpleSumPaid(String param1, String param2) {
		if(!"1".equals(this.simpelFlag)){
			return true;
		}
		double dMaxLimit = -1;
		double dMinLimit = -1;
		boolean isAmmounts = true;
		if(null!=param1&&!"".equals(param1)){
			dMinLimit = Double.parseDouble(param1.trim());
		}
		if(null!=param2&&!"".equals(param2)){
			dMaxLimit = Double.parseDouble(param2.trim());
		}
		if(dMinLimit>0&&dMaxLimit >= 0){
			isAmmounts = simpleSumPaid > dMinLimit&&simpleSumPaid <= dMaxLimit;
		}else if(dMinLimit>0){
			isAmmounts = simpleSumPaid > dMinLimit;
		}else if(dMaxLimit>=0){
			isAmmounts = simpleSumPaid <= dMaxLimit;
		}else{
			isAmmounts = true;
		}
		if(!isAmmounts){
			resultList.add("simpleSumPaid");
		}
		return true;
	}
	/**
	 * 判断 拆分险别 GA--> GA,HG
	 * PA--》PA，HP
	 * @param param
	 * @param flag
	 * @return
	 */
	public boolean isRelationAmmounts(double dMinLimit, double dMaxLimit){
		boolean isAmmounts = true;
		if(undwrtRelationList==null||undwrtRelationList.size()<1){
			return false;
		}else{
			for(UndwrtRelationCondition condition : undwrtRelationList){
				if(condition.getResult()){
					if(isAmmounts){
						if(dMinLimit>0&&dMaxLimit >= 0){
							isAmmounts = condition.getSumSumPaid() > dMinLimit&&condition.getSumSumPaid() <= dMaxLimit;
						}else if(dMinLimit>0){
							isAmmounts = condition.getSumSumPaid() > dMinLimit;
						}else if(dMaxLimit>=0){
							isAmmounts = condition.getSumSumPaid() <= dMaxLimit;
						}else{
							isAmmounts = true;
						}
					}
					condition.setResult(false);
				}
			}
		}
		return isAmmounts;
	}
	/**
	 * 核赔结果
	 * @param param
	 */
	public void resultIsFalse(String param){
		if(resultList.size()>0){
			this.result = false;
			if(param!=null){
				for(String temp : resultList){
					int index = param.indexOf(temp);
					if(index>-1){
						int end = param.indexOf("||",index);
						if(end<index){
							end = param.length();
						}
						strResultMessage += param.substring(index+temp.length()+1, end)+"\n";
					}
				}
			}
		}
	}
	
	/**
	 * 返回结果
	 * @return
	 */
	public boolean getResult() {
		return result;
	} 

	public String getStrResultMessage() {
		return strResultMessage;
	}

	public void setStrResultMessage(String strResultMessage) {
		this.strResultMessage = strResultMessage;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public double getSumPaid() {
		return sumPaid;
	}

	public void setSumPaid(double sumPaid) {
		this.sumPaid = sumPaid;
	}

	public String getBackOperation() {
		return backOperation;
	}

	public void setBackOperation(String backOperation) {
		this.backOperation = backOperation;
	}

	public double getKindSumRealPay() {
		return kindSumRealPay;
	}

	public void setKindSumRealPay(double kindSumRealPay) {
		this.kindSumRealPay = kindSumRealPay;
	}

	public int getRealPayFlag() {
		return realPayFlag;
	}

	public void setRealPayFlag(int realPayFlag) {
		this.realPayFlag = realPayFlag;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public boolean getRulesCheckFlag() {
		return rulesCheckFlag;
	}

	public void setRulesCheckFlag(boolean rulesCheckFlag) {
		this.rulesCheckFlag = rulesCheckFlag;
	}

	public String getUwType() {
		return uwType;
	}

	public void setUwType(String uwType) {
		this.uwType = uwType;
	}

	public double getSumSumPaid() {
		return sumSumPaid;
	}

	public void setSumSumPaid(double sumSumPaid) {
		this.sumSumPaid = sumSumPaid;
	}

	public List<String> getResultList() {
		return resultList;
	}

	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}

	public List<UndwrtRelationCondition> getUndwrtRelationList() {
		return undwrtRelationList;
	}


	public void setUndwrtRelationList(List<UndwrtRelationCondition> undwrtRelationList) {
		this.undwrtRelationList = undwrtRelationList;
	}
	
	public double getSimpleSumPaid() {
		return simpleSumPaid;
	}
	
	public void setSimpleSumPaid(double simpleSumPaid) {
		this.simpleSumPaid = simpleSumPaid;
	}
	public String getSimpelFlag() {
		return simpelFlag;
	}
	public void setSimpelFlag(String simpelFlag) {
		this.simpelFlag = simpelFlag;
	}

}
