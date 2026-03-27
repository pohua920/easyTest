package com.sinosoft.undwrt.undwrtRule.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sinosoft.one.rule.domain.InputBOM;
import com.sinosoft.undwrt.undwrtRule.service.UndwrtRuleRiskKind;

/**
 * 要保書訊息數據類.
 */

public class BusinessProposalData extends BusinessData implements InputBOM {

	// private Logger logger =
	// LoggerFactory.getLogger(BusinessProposalData.class);

	/** 屬性審核結果. */
	private boolean result = true;

	/** 屬性規則引擎審核訊息. */
	private String strResultMessage;

	/** 屬性規則引擎校驗. */
	private boolean rulesCheckFlag = false;

	/** 屬性車輛使用年限. */
	private double useYears;

	/** 屬性車輛種類. */
	private String carKind;

	/** 屬性車輛使用性質. */
	private String useNature;

	/** 新增設備保額. */
	private double carDevice;

	/** 屬性保額限額. */
	private double limitAmount;
	
	/** 屬性保額. */
	private double amount;
	
	/** 屬性險種. */
	private String riskCode;
	
	private boolean limitedModelCode;
	
	private boolean limitedLicenseNo;
	
	private boolean limitedIdentification;
	
	private boolean limitedEngineNo;
	
	private boolean limitedCarKind;
	
	private double floatRateA;
	
	private double floatRateG;
	
	private String businessNature;
	
	private String hander1Code;

	private String businessType;

	private double costRate;
	
	private int policydays;
	
	private int endorsedays;
	
	private int canceldays;
	
	private String dutyLevel;
	
	private int insuredAge;
	
	private String isRenewal;
	
	private String constructType ="";
	
	//mantis： CAR0107，處理人員：DP0706，需求單編號：CAR0107: 增加限定投保名單檢核類別
	private boolean limitedLicenseNoAndIdno;

	/** 屬性險別保額存儲訊息. */
	private Map<String, UndwrtRuleRiskKind> riskKind = new HashMap<String, UndwrtRuleRiskKind>();
	
	private List BMInumber = new ArrayList();
	
	private int greaterNowDate;
	
	private String greaterEndDate="N";
	
	private String payKind="";
	//体位代号
	private List positionCodes=new ArrayList();
	//职业代号版本
	private  List occuVersions = new ArrayList();
	//批改类型
	private String endorType;
	
	private Map<String, Double> flexiableAmount;
	
	private int insurePeriod;
	
	private int extendPeriod;
	
	private double chgPremium;
	//专案代号
	private String projectCode;
	//核可文件編號
	private String approvalNo;
	
	private boolean isHaveClaim;
	/**
	 * 將險別保額訊息按照險別代碼放入Map.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @param undwrtRiskKind
	 *            險別保額訊息
	 */
	public void addRiskKind(String kindCode, UndwrtRuleRiskKind undwrtRiskKind) {
		riskKind.put(kindCode, undwrtRiskKind);
	}

	/**
	 * 獲取屬性險別保額存儲訊息.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 屬性險別保額存儲訊息的值
	 */
	public UndwrtRuleRiskKind getRiskKind(String kindCode) {
		return riskKind.get(kindCode);
	}

	/**
	 * 檢查是否包含此險別.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 檢查結果
	 */
	//方法改造以逗号隔开的每一个险别是否包含
	public boolean containsRiskKind(String kindCode) {
		System.out.println("正在进行规则校验，请稍等...");
		System.out.println("================>>" + kindCode);
		this.rulesCheckFlag = true; // true表示已经进行了规则校验
		boolean flag = false;
		String[] temp = kindCode.split(",");
		for (int i=0;i<temp.length;i++)
		{
			if(flag = getRiskKind(temp[i]) != null)
			{
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println("【kindCode is " + kindCode + "!】");
		}
		// logger.info("kindCode is {},flag is {}", kindCode, flag);
		return flag;
	}

	/**
	 * 獲取屬性險別的保額.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 屬性險別保額的值
	 */
	public double getKindAmount(String kindCode) {
		double amount = this.getRiskKind(kindCode).getAmount();
		// logger.info("kindCode is {},amount is {}", kindCode, amount);
		return amount;
	}
	/**
	 * 獲取屬性險別的保額.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 屬性險別保額的值
	 */
	public boolean getPermission(String kindCode) {
		String[] kindCodeSplit = kindCode.split(",");
		double amount = Double.valueOf(kindCodeSplit[kindCodeSplit.length-1]);
		for(int j=0;j<kindCodeSplit.length-1;j++)
		{
			if(getRiskKind(kindCodeSplit[j])!=null && getRiskKind(kindCodeSplit[j]).getAmount()>amount)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * 獲取屬性險別的佣金比例.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 屬性佣金比例的值
	 */
	public double getKindCostRate(String kindCode) {
		double kindcostRate = this.getRiskKind(kindCode).getKindCostRate();
		// logger.info("kindCode is {},amount is {}", kindCode, amount);
		return kindcostRate;
	}
	
	/**
	 * 判斷是否包含此險別.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 判斷結果 true包含 false 不包含
	 */
	public boolean hasKind(String kindCode) {
		return this.riskKind.containsKey(kindCode);
	}

	
	public boolean compareAmount(String param){
		
		String strParam[] = param.split(",");
		String kindCode = strParam[0];
		String amount1 = strParam[1];
		String amount2 = strParam[2];
		
		if(getKindAmount(kindCode)>Double.parseDouble(amount1) && getKindAmount(kindCode)<=Double.parseDouble(amount2)){
			return true;
		}
		if(null==strResultMessage || "".equals(strResultMessage))
		{
			strResultMessage="undwrt.rule.amountTooLarge";
		}
		result=false;
		return false;
	}
	
	public boolean compareAmount2(String param){
		
		String strParam[] = param.split(",");
		String kindCode = strParam[0];
		String amount1 = strParam[1];
		if(getKindAmount(kindCode)<=Double.parseDouble(amount1)){
			return true;
		}
		if(null==strResultMessage || "".equals(strResultMessage))
		{
			strResultMessage="undwrt.rule.amountTooLarge";
		}
		result=false;
		return false;
	}
	public boolean evaluateFloatRateG(String param){
		//mantis： CAR0535，處理人員：CC009，需求單編號：CAR0535.自動核保規則變更,任意係數卡控調整 start
		if(null == param || "".equals(param)){
			return true;
		}
		//mantis： CAR0535，處理人員：CC009，需求單編號：CAR0535.自動核保規則變更,任意係數卡控調整 end
		//mantis： CAR0058，處理人員：Sam，需求單編號：CAR0058 自動核保規則修正-責任險與車體險係數小於等於改為小於不等於
		if(floatRateG < Double.parseDouble(param))
		{
			return true;
		}
		if(null==strResultMessage || "".equals(strResultMessage))
		{
			strResultMessage="undwrt.rule.badRecords";
		}
		result=false;
		return false;
	}
	public boolean evaluateUseYears(String param){
		
		//需求变更，不再校验使用年限，时间紧暂时不删除该校验方案20140113
		return true;
//		if(useYears<Double.parseDouble(param))
//		{
//			return true;
//		}
//		if(null==strResultMessage || "".equals(strResultMessage))
//		{
//			strResultMessage="車輛太陳舊！";
//		}
//		result=false;
//		return false;
	}
	public boolean evaluateFloatRateA(String param){
		//mantis： CAR0535，處理人員：CC009，需求單編號：CAR0535.自動核保規則變更,任意係數卡控調整 start
		if(null == param || "".equals(param)){
			return true;
		}
		//mantis： CAR0535，處理人員：CC009，需求單編號：CAR0535.自動核保規則變更,任意係數卡控調整 end
		//mantis： CAR0058，處理人員：Sam，需求單編號：CAR0058 自動核保規則修正-責任險與車體險係數小於等於改為小於不等於
		if(floatRateA < Double.parseDouble(param))
		{
			return true;
		}
		if(null==strResultMessage || "".equals(strResultMessage))
		{
			strResultMessage="undwrt.rule.badRecords";
		}
		result=false;
		return false;
	}
	/**
	 * 匹配車齡.
	 * 
	 * @param carAge
	 *            車齡
	 * @return 匹配結果 true 匹配成功 false 匹配失敗
	 */
	public boolean matchAge(String useYears) {
		// InternationalizationUtil internal = new InternationalizationUtil();
		boolean hasCarAge = false;
		/*
		 * System.out.println(internal.getText("undwrt.service.task.business") +
		 * "==" + this.useYears); System.out
		 * .println(internal.getText("undwrt.service.task.configuration") + "=="
		 * + useYears);
		 */
		if (this.useYears > Integer.parseInt(useYears)) {
			hasCarAge = true;
		}
		return hasCarAge;
	}

	/**
	 * 匹配车辆种类.
	 * 
	 * @param carKindCode
	 *            the car kind code
	 * @return true, if successful
	 */
	public boolean matchCarKind(String carKindCode) {
		boolean matchFlag = false;
		if (carKindCode.indexOf(this.carKind) > -1) {
			System.out.println("【carKindCode is " + carKindCode + "!】");
			matchFlag = true;
		}
		if(true==limitedCarKind)
		{
			if(null==strResultMessage || "".equals(strResultMessage))
			{
				strResultMessage="undwrt.rule.limitCarKind";
			}
			result=false;
		}
		return matchFlag;
	}

	public boolean hasRiskCode(String riskCode)
	{
		this.rulesCheckFlag = true; // true表示已经进行了规则校验
		if(riskCode.indexOf(this.riskCode)>-1)
		{
			return true;
		}
		else
		{
			if(null==strResultMessage || "".equals(strResultMessage))
			{
				//strResultMessage="非任意險A01險種，已轉人工核保！";
			}
		 return false;
		}
	}
	
	public boolean checkModelCode(String isPara)
	{
		if("N".equals(isPara))
		{
			return true;
		}
		if(limitedModelCode)
		{
			if(null==strResultMessage || "".equals(strResultMessage))
			{
				strResultMessage="undwrt.rule.limitCarType";
			}
			result=false;
			return false;
		}
		return true;
	}
	
	public boolean checkLicenseNo(String isPara)
	{
		if("N".equals(isPara))
		{
			return true;
		}
		if(limitedLicenseNo)
		{
			if(null==strResultMessage || "".equals(strResultMessage))
			{
				strResultMessage="undwrt.rule.limitLicenseNo";
			}
			result=false;
			return false;
		}
		
		//mantis： CAR0107，處理人員：DP0706，需求單編號：CAR0107: 增加限定投保名單檢核類別 START
		if(limitedLicenseNoAndIdno)
		{
			if(null==strResultMessage || "".equals(strResultMessage))
			{
				strResultMessage="undwrt.rule.limitLicenseNoAndIdno";
			}
			result=false;
			return false;
		}
		//mantis： CAR0107，處理人員：DP0706，需求單編號：CAR0107: 增加限定投保名單檢核類別 END
		
		return true;
		//return checkInformation(isPara,limitedLicenseNo);
	}
	
	public boolean checkIdentifyNumber(String isPara)
	{
		//return checkInformation(isPara,limitedIdentification);
		if("N".equals(isPara))
		{
			return true;
		}
		if(limitedIdentification)
		{
			if(null==strResultMessage || "".equals(strResultMessage))
			{
				strResultMessage="undwrt.rule.limitIdentifyNumber";
			}
			result=false;
			return false;
		}
		return true;
	}
	
	public boolean checkEngineNo(String isPara)
	{
		//return checkInformation(isPara,limitedEngineNo);
		if("N".equals(isPara))
		{
			return true;
		}
		if(limitedEngineNo)
		{
			if(null==strResultMessage || "".equals(strResultMessage))
			{
				strResultMessage="undwrt.rule.limitEngineNo";
			}
			result=false;
			return false;
		}
		return true;
	}
	
	public  boolean checkInformation(String isPara,boolean info)
	{
		if("N".equals(isPara))
		{
			return true;
		}
		if(info)
		{
			if(null==strResultMessage || "".equals(strResultMessage))
			{
				strResultMessage="undwrt.rule.fileInvalid";
			}
			result=false;
			return false;
		}
		return true;
	}
	
	public void resultIsTure(String strMessage)
	{
		if(null==strResultMessage || "".equals(strResultMessage))
		{
			this.strResultMessage = strMessage;
			this.result = true;
		}
	}
	/**
	 * 设置action返回结果.
	 * 
	 * @param strMessage
	 *            the str message
	 */
	public void resultIsFalse(String strMessage) {
		this.strResultMessage = strMessage;
		this.result = false;
	}
	
	public boolean getHeadOfKindCode(String head) {
		 Collection<UndwrtRuleRiskKind> c = riskKind.values();
		 UndwrtRuleRiskKind ruleRiskKind = new UndwrtRuleRiskKind();
		 Iterator it = c.iterator();
		while(it.hasNext())
			{
			 ruleRiskKind = (UndwrtRuleRiskKind) it.next();
			 //方法改造，伤害险保险期间规则只针对PA,GA20140805 by wangJun
				if(head.indexOf(ruleRiskKind.getKindCode().substring(0,2))>-1)
				{
					return true;
				}
				break;
			}
			return false;
	}
	public boolean evaluateBMI(String param)
	{
		String[] temp = param.split(",");
		String num1 = temp[1];
		String num2 = temp[2];
		for(int i=0;i<BMInumber.size();i++)
		{
			if(temp[0].equals("0"))
			{
				double number=(double) BMInumber.get(i);
				if(number<Double.parseDouble(num1) || number>Double.parseDouble(num2))
				{
					return true;
				}
			}
			else if(temp[0].equals("1"))
			{
				double number=(double) BMInumber.get(i);
				if(number>=Double.parseDouble(num1) && number>Double.parseDouble(num2))
				{
					return true;
				}
			}
		}
		return false;
	}
	//体位代号评估
	public boolean evaPositionCode(String param)
	{
		
		for(int i=0;i<positionCodes.size();i++)
		{
			if(null!=positionCodes.get(i) && !positionCodes.get(i).equals(param))
			{
				return true;
			}
		}
		return false;
	}
	//职业版本代号评估
	public boolean evaoccuVersions(String param)
	{
		
		for(int i=0;i<occuVersions.size();i++)
		{
			if(null!=occuVersions.get(i) && occuVersions.get(i).equals(param))
			{
				return true;
			}
		}
		return false;
	}
	//工程代号
	public boolean inConstructType(String constructType)
	{
		if(constructType.indexOf(this.constructType)>-1)
		{
			return true;
		}
			return false;
	}
	public boolean evaluateFlexible(String param)
	{
		String[] s = param.split(",");
		double flex = flexiableAmount.get(s[0])==null ? 0:flexiableAmount.get(s[0]);
		if(flex>Integer.valueOf(s[1]))
		{
			return true;
		}
		return false;
	}
	/**
	 * 檢查是否只此險別.
	 * 
	 * @param kindCode
	 *            險別代碼
	 * @return 檢查結果
	 */
	//方法改造以逗号隔开的每一个险别是否包含
	public boolean onlyContainsRiskKind(String kindCode) {
		System.out.println("正在进行规则校验，请稍等...");
		System.out.println("================>>" + kindCode);
		this.rulesCheckFlag = true; // true表示已经进行了规则校验
		boolean flag = false;
		String[] temp = kindCode.split(",");
		if(temp.length==1) {
			Collection<UndwrtRuleRiskKind> c = riskKind.values();
			UndwrtRuleRiskKind ruleRiskKind = new UndwrtRuleRiskKind();
			Iterator it = c.iterator();
			if(c.size()==1) {
				while(it.hasNext()) {
					ruleRiskKind = (UndwrtRuleRiskKind) it.next();
					if(temp[0].equals(ruleRiskKind.getKindCode())) {
						flag = true;
					}
				}
			}
			
		}
		if (flag) {
			System.out.println("【kindCode is " + kindCode + "!】");
		}
		// logger.info("kindCode is {},flag is {}", kindCode, flag);
		result = flag;
		return flag;
	}
	
	public boolean checkClaim(String isPara)
	{
		this.rulesCheckFlag = true;//add by yjm 20160118 4681： 屬性規則引擎校驗设置 start
		if("N".equals(isPara))
		{
			return true;
		}
		if(isHaveClaim)
		{
			if(null==strResultMessage || "".equals(strResultMessage))
			{
				strResultMessage="前二年有理賠出險記錄";
			}
			result=false;
			return false;
		}
		return true;
	}
	
	public boolean checkConstructAmount(String param){
		this.rulesCheckFlag = true;//add by yjm 20160118 4681：屬性規則引擎校驗设置 start
		String strParam[] = param.split(",");
		String constrct = strParam[0];
		String amount1 = strParam[1];
		Map<String,String> constructMap = new HashMap<String,String>();
		constructMap.put("1", "特一等");
		constructMap.put("2", "特二等");
		constructMap.put("3", "頭等");
		constructMap.put("5", "二等");
		if(constrct.equals(getConstructType())){
			if((new BigDecimal(getAmount())).compareTo(new BigDecimal(amount1))>=0){
				if(null==strResultMessage || "".equals(strResultMessage))
				{
					strResultMessage=constructMap.get(constrct)+"保額超過或等於"+"NT$"+amount1+"萬";
				}
				result=false;
				return false;
			}
		}else{
			if(constructMap.get(getConstructType())==null){
				strResultMessage="建築等級非特一等、特二等、頭等、二等。";
			}
			result=false;
			return false;
		}
		return true;
	}
	
	/**
	 * 返回结果.
	 * 
	 * @return 屬性審核結果的值
	 */
	public boolean getResult() {
		return result;
	}

	/**
	 * 設置屬性審核結果.
	 * 
	 * @param result
	 *            待設置的審核結果的值
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * Checks if is rules check flag.
	 * 
	 * @return true, if is rules check flag
	 */
	public boolean isRulesCheckFlag() {
		return rulesCheckFlag;
	}

	/**
	 * 設置屬性規則引擎校驗.
	 * 
	 * @param rulesCheckFlag
	 *            待設置的規則引擎校驗的值
	 */
	public void setRulesCheckFlag(boolean rulesCheckFlag) {
		this.rulesCheckFlag = rulesCheckFlag;
	}

	/**
	 * 獲取屬性規則引擎審核訊息.
	 * 
	 * @return 屬性規則引擎審核訊息的值
	 */
	public String getStrResultMessage() {
		return strResultMessage;
	}

	/**
	 * 設置屬性規則引擎審核訊息.
	 * 
	 * @param strResultMessage
	 *            待設置的規則引擎審核訊息的值
	 */
	public void setStrResultMessage(String strResultMessage) {
		this.strResultMessage = strResultMessage;
	}

	/**
	 * 獲取屬性車輛使用年限.
	 * 
	 * @return 屬性車輛使用年限的值
	 */

	public double getUseYears() {
		return useYears;
	}
	/**
	 * 設置屬性車輛使用年限.
	 * 
	 * @param useYears
	 *            待設置的車輛使用年限的值
	 */
	public void setUseYears(double useYears) {
		this.useYears = useYears;
	}

	/**
	 * 獲取屬性車輛種類.
	 * 
	 * @return 屬性車輛種類的值
	 */
	public String getCarKind() {
		return carKind;
	}

	/**
	 * 設置屬性車輛種類.
	 * 
	 * @param carKind
	 *            待設置的車輛種類的值
	 */
	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	/**
	 * 獲取屬性車輛使用性質.
	 * 
	 * @return 屬性車輛使用性質的值
	 */
	public String getUseNature() {
		return useNature;
	}

	/**
	 * 設置屬性車輛使用性質.
	 * 
	 * @param useNature
	 *            待設置的車輛使用性質的值
	 */
	public void setUseNature(String useNature) {
		this.useNature = useNature;
	}

	/**
	 * Gets the 新增設備保額.
	 * 
	 * @return the 新增設備保額
	 */
	public double getCarDevice() {
		return carDevice;
	}

	/**
	 * Sets the 新增設備保額.
	 * 
	 * @param carDevice
	 *            the new 新增設備保額
	 */
	public void setCarDevice(double carDevice) {
		this.carDevice = carDevice;
	}

	/**
	 * 獲取屬性保額限額.
	 * 
	 * @return 屬性保額限額的值
	 */
	public double getLimitAmount() {
		return limitAmount;
	}

	/**
	 * 設置屬性保額限額.
	 * 
	 * @param limitAmount
	 *            待設置的保額限額的值
	 */
	public void setLimitAmount(double limitAmount) {
		this.limitAmount = limitAmount;
	}
	/**
	 * 獲取屬性保額.
	 * 
	 * @return 屬性保額的值
	 */
	public double getAmount() {
		this.rulesCheckFlag = true;
		return amount;
	}
	/**
	 * 設置屬性保額.
	 * 
	 * @param amount
	 *            待設置的保額的值
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * 獲取屬性險種.
	 * 
	 * @return 屬性險種的值
	 */
	public String getRiskCode() {
		this.rulesCheckFlag = true; 
		return riskCode;
	}
	/**
	 * 設置屬性險種.
	 * 
	 * @param amount
	 *            待設置的險種的值
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public boolean isLimitedModelCode() {
		return limitedModelCode;
	}

	public void setLimitedModelCode(boolean limitedModelCode) {
		this.limitedModelCode = limitedModelCode;
	}

	public boolean isLimitedLicenseNo() {
		return limitedLicenseNo;
	}

	public void setLimitedLicenseNo(boolean limitedLicenseNo) {
		this.limitedLicenseNo = limitedLicenseNo;
	}

	public boolean isLimitedIdentification() {
		return limitedIdentification;
	}

	public void setLimitedIdentification(boolean limitedIdentification) {
		this.limitedIdentification = limitedIdentification;
	}

	public boolean isLimitedEngineNo() {
		return limitedEngineNo;
	}

	public void setLimitedEngineNo(boolean limitedEngineNo) {
		this.limitedEngineNo = limitedEngineNo;
	}

	public double getFloatRateA() {
		return floatRateA;
	}

	public void setFloatRateA(double floatRateA) {
		this.floatRateA = floatRateA;
	}

	public double getFloatRateG() {
		return floatRateG;
	}

	public void setFloatRateG(double floatRateG) {
		this.floatRateG = floatRateG;
	}

	public boolean isLimitedCarKind() {
		return limitedCarKind;
	}

	public void setLimitedCarKind(boolean limitedCarKind) {
		this.limitedCarKind = limitedCarKind;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessNature() {
		return businessNature;
	}
	
	public String getHander1Code() {
		return hander1Code;
	}

	public void setHander1Code(String hander1Code) {
		this.hander1Code = hander1Code;
	}

	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	public double getCostRate() {
		return costRate;
	}

	public void setCostRate(double costRate) {
		this.costRate = costRate;
	}

	public int getPolicydays() {
		return policydays;
	}

	public void setPolicydays(int policydays) {
		this.policydays = policydays;
	}

	public int getEndorsedays() {
		return endorsedays;
	}

	public void setEndorsedays(int endorsedays) {
		this.endorsedays = endorsedays;
	}

	public int getCanceldays() {
		return canceldays;
	}

	public void setCanceldays(int canceldays) {
		this.canceldays = canceldays;
	}

	public String getDutyLevel() {
		return dutyLevel;
	}

	public void setDutyLevel(String dutyLevel) {
		this.dutyLevel = dutyLevel;
	}

	public int getInsuredAge() {
		return insuredAge;
	}

	public void setInsuredAge(int insuredAge) {
		this.insuredAge = insuredAge;
	}

	public String getIsRenewal() {
		return isRenewal;
	}

	public void setIsRenewal(String isRenewal) {
		this.isRenewal = isRenewal;
	}

	public List getBMInumber() {
		return BMInumber;
	}

	public void setBMInumber(List bMInumber) {
		BMInumber = bMInumber;
	}

	public int getGreaterNowDate() {
		return greaterNowDate;
	}

	public void setGreaterNowDate(int greaterNowDate) {
		this.greaterNowDate = greaterNowDate;
	}

	public String getGreaterEndDate() {
		return greaterEndDate;
	}

	public void setGreaterEndDate(String greaterEndDate) {
		this.greaterEndDate = greaterEndDate;
	}

	public boolean getPayKind(String payMethod) {
		if(!payKind.equals(payMethod))
		{
			return true;
		}
		return false;
	}

	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}

	public List getPositionCodes() {
		return positionCodes;
	}

	public void setPositionCodes(List positionCodes) {
		this.positionCodes = positionCodes;
	}

	public List getOccuVersions() {
		return occuVersions;
	}

	public void setOccuVersions(List occuVersions) {
		this.occuVersions = occuVersions;
	}

	public String getEndorType() {
		return endorType;
	}

	public void setEndorType(String endorType) {
		this.endorType = endorType;
	}

	public String getConstructType() {
		return constructType;
	}

	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}

	public Map<String, Double> getFlexiableAmount() {
		return flexiableAmount;
	}

	public void setFlexiableAmount(Map<String, Double> flexiableAmount) {
		this.flexiableAmount = flexiableAmount;
	}

	public int getInsurePeriod() {
		return insurePeriod;
	}

	public void setInsurePeriod(int insurePeriod) {
		this.insurePeriod = insurePeriod;
	}

	public int getExtendPeriod() {
		return extendPeriod;
	}

	public void setExtendPeriod(int extendPeriod) {
		this.extendPeriod = extendPeriod;
	}

	public double getChgPremium() {
		return chgPremium;
	}

	public void setChgPremium(double chgPremium) {
		this.chgPremium = chgPremium;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}

	public boolean isHaveClaim() {
		return isHaveClaim;
	}

	public void setHaveClaim(boolean isHaveClaim) {
		this.isHaveClaim = isHaveClaim;
	}
	
	//mantis： CAR0107，處理人員：DP0706，需求單編號：CAR0107: 增加限定投保名單檢核類別 START
	public boolean isLimitedLicenseNoAndIdno() {
		return limitedLicenseNoAndIdno;
	}

	public void setLimitedLicenseNoAndIdno(boolean limitedLicenseNoAndIdno) {
		this.limitedLicenseNoAndIdno = limitedLicenseNoAndIdno;
	}
	//mantis： CAR0107，處理人員：DP0706，需求單編號：CAR0107: 增加限定投保名單檢核類別 END
}