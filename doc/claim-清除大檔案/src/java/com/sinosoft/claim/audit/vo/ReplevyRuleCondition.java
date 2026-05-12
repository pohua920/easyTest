package com.sinosoft.claim.audit.vo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.one.rule.domain.InputBOM;

/**
 * 追償審核規則對象
 * @author 中科軟
 */
public class ReplevyRuleCondition implements InputBOM, Serializable {
	private static final long serialVersionUID = 1L;
	/** 審核崗位 */
	private List<String> gradeCodes = new ArrayList<String>();
	/** 審核級別 */
	private String level = "";
	/** 法務預估金額 追償登錄登記之預估金額合計 */
	private double sumLoss = 0d;
	/** 實際追償金額 本次實際追償金額與歷次已追償金額合計 */
	private double sumRealPay = 0d;
	/** 是否簽結不予追償 sumRealPay ==0 0，是，-1，否 */
	private String replevyFlag = "";
	/** 最終审核结果*/
	private boolean result = false;
	/** 核保不通过的原因*/
	private String resultMessage = "您無權審核。";
	/** 可追償金額折讓比例  */
	private double percent = 0d;
	/** 总期数  */
	private int totalTimes = 1;
	private double chargeAmount = 0D;
	/** 規則的實際校驗結果  */
	private List<String> conditionsResult = new ArrayList<String>();

	public List<String> getGradeCodes() {
		return gradeCodes;
	}

	public void setGradeCodes(List<String> gradeCodes) {
		//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認 START
		if(null!=gradeCodes){
			int i=0;
			for(String s:gradeCodes){				
				System.out.println(++i+"CLM0144 gradeCodes:"+s);
			}
		}
		//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認 END
		this.gradeCodes = gradeCodes;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public double getSumLoss() {
		return sumLoss;
	}

	public void setSumLoss(double sumLoss) {
		this.sumLoss = sumLoss;
	}

	public double getSumRealPay() {
		return sumRealPay;
	}

	public void setSumRealPay(double sumRealPay) {
		this.sumRealPay = sumRealPay;
		this.replevyFlag = sumRealPay == 0 ? "0" : "-1";
		//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認
		System.out.println("CLM0144 sumRealPay:"+sumRealPay+" /this.replevyFlag="+this.replevyFlag);
	}

	public String getReplevyFlag() {
		return replevyFlag;
	}

	public boolean getResult() {
		return result;
	}

	public String getResultMessage() {
		return resultMessage;
	}
	
	
	public int getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}

	public List<String> getConditionsResult() {
		return conditionsResult;
	}

	public void setConditionsResult(List<String> conditionsResult) {
		this.conditionsResult = conditionsResult;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	/**
	 * 簽結不予追償金額範圍
	 * @param minValue 下限 -1 直接否決
	 * @param maxValue 上限（含）-1表示無上限
	 * @return
	 */
	public boolean limitAmount(String minValue, String maxValue) {
		//設計如此是因為本條規則前面的CONDITION全部通過后，則可以根據此以後的CONDITION確定唯一結論，不必走本條以後的規則。
		boolean flag = true;
		//上條CONDITION判斷的結果，若為true則需要判斷本條，否則本函數直接返回true。以便達到最終結論的ACTION
		if ("-1".equals(maxValue)) {
			flag = Double.valueOf(minValue) <= Math.abs(this.getSumLoss());
		} else if("0".equals(minValue)){//最小0，包含
			flag = Math.abs(this.getSumLoss()) <= Double.valueOf(maxValue);
		} else {
			flag = Double.valueOf(minValue) < Math.abs(this.getSumLoss()) && Double.valueOf(maxValue) >= Math.abs(this.getSumLoss());
		}
		if(!flag){
			this.conditionsResult.add("limitAmount");
		}
		System.out.println("***********limitAmount:"+flag);
		return true;
	}
	/**
	 * 总期数权限效验
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public boolean limitTotalTimes(String minValue, String maxValue){
		//設計如此是因為本條規則前面的CONDITION全部通過后，則可以根據此以後的CONDITION確定唯一結論，不必走本條以後的規則。
		boolean flag = true;
		if ("-1".equals(maxValue)) {
			flag = Double.valueOf(minValue) <= this.getTotalTimes();
		} else if("0".equals(minValue)){//最小0，包含
			flag = this.getTotalTimes() <= Double.valueOf(maxValue);
		} else {
			flag = Double.valueOf(minValue) < this.getTotalTimes() && Double.valueOf(maxValue) >= this.getTotalTimes();
		}
		if(!flag){
			this.conditionsResult.add("limitTotalTimes");
		}
		System.out.println("***********limitTotalTimes:"+flag);
		return true;
	}
	/**
	 * 总期数权限效验
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	public boolean limitCharge(String minValue, String maxValue){
		//設計如此是因為本條規則前面的CONDITION全部通過后，則可以根據此以後的CONDITION確定唯一結論，不必走本條以後的規則。
		boolean flag = true;
		if ("-1".equals(maxValue)) {
			flag = Double.valueOf(minValue) <= this.getChargeAmount();
		} else if("0".equals(minValue)){//最小0，包含
			flag = this.getChargeAmount() <= Double.valueOf(maxValue);
		} else {
			flag = Double.valueOf(minValue) < this.getChargeAmount() && Double.valueOf(maxValue) >= this.getChargeAmount();
		}
		if(!flag){
			this.conditionsResult.add("limitCharge");
		}
		System.out.println("***********limitCharge:"+flag);
		return true;
	}
	/***
	 * 可追償金額折讓比例範圍
	 * @param minValue 下限 -1 表示无下限
	 * @param maxValue 上限（含）-1表示無上限
	 * @return
	 */
	public boolean limitPercent(String maxValue) {
		//設計如此是因為本條規則前面的CONDITION全部通過后，則可以根據此以後的CONDITION確定唯一結論，不必走本條以後的規則。
		//上條CONDITION判斷的結果，若為true則需要判斷本條，否則本函數直接返回true。以便達到最終結論的ACTION
		this.percent = (this.getSumLoss() - Math.abs(this.getSumRealPay()*this.getTotalTimes())) / this.getSumLoss();
		double max = Double.valueOf(maxValue);
		boolean flag = this.percent <= max;
		//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認
		System.out.println("CLM0144 /this.getSumLoss()="+this.getSumLoss()+"this.getSumRealPay()="+this.getSumRealPay()+" /this.getTotalTimes()="+this.getTotalTimes()+" /this.percent="+this.percent+" /max:"+max +" /maxValue:"+maxValue);
		if(!flag){
			this.conditionsResult.add("limitPercent");
		}
		System.out.println("***********limitPercent:"+flag);
		return true;
	}

	/**
	 * 校驗不通過返回的權限校驗訊息
	 * @param message
	 */
	public void setResultMessage(String message){
		if(this.conditionsResult.size()>0){
			this.result = false;
			this.resultMessage = "";
			//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認
			int text = 0;
			for(String str : this.conditionsResult){
				//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認
				System.out.println(++text+" CLM0144 str:"+str);
				int index = message.indexOf(str);
				if(index>-1){
					int end = message.indexOf("||", index);
					if(end<index){
						end = message.length();
					}
					this.resultMessage += message.substring(index+str.length()+1,end)+"\n\r";
				}
			}
		}else{
			this.result = true;
		}
		if(!this.getResult()){//若最終本條規則實際的判斷結果不為真，則表明本條權限校驗不通過。組織結論文字。;
			//mantis：CLM0144，處理人員：DP0713，需求單編號：CLM0144，新核心-追償審核流程錯誤問題確認
			System.out.println("CLM0144 this.getReplevyFlag()"+this.getReplevyFlag());
			if("-1".equals(this.getReplevyFlag())){
				this.resultMessage += "當前可追償折讓比例為"+new DecimalFormat("#0.00").format(percent*100)+"%。\n\r";
				if(this.totalTimes>1){
					this.resultMessage += "分期還款案件期數為"+this.totalTimes;
				}
			}else{
				this.resultMessage += "實際追償金額為 0，簽結不予追償。\n\r";
				this.resultMessage += "法務預估金額之和為新台幣"+new DecimalFormat("#").format(sumLoss);
			}
		}
		System.out.println(this.resultMessage);
	}
}
