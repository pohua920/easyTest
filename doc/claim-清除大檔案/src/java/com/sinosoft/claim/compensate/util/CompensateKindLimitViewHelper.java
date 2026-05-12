package com.sinosoft.claim.compensate.util;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.bl.facade.BLPrpDdeprecateRateFacade;
import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.ConstantsCollection;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.StringConvert;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.dto.domain.PrpDdeprecateRateDto;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLgroovyKindService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/***
 * 车险限额取值辅助类
 * @author 中科软
 *
 */
public class CompensateKindLimitViewHelper {
	
    private static PrpLcompensateService getPrpLcompensateService() {
    	return (PrpLcompensateService) ServiceFactory.getService("prpLcompensateService");
	}
    
    private static PrpLpersonLossService getPrpLpersonLossService(){
    	return (PrpLpersonLossService) ServiceFactory.getService("prpLpersonLossService");
    }
    private static PrpLgroovyKindService getPrpLgroovyKindService(){
    	return (PrpLgroovyKindService) ServiceFactory.getService("prpLgroovyKindService");
    }

	/***
     * 限額取值方式 (属于每次事故型的)
     * 不用考虑人伤和车财分开，总险别不能超过限额
	 * @param claimNo 当前立案号
	 * @param policyNo 当前保单号
	 * @param itemKindList 当前保单承保险别
	 * @return
     * @throws Exception 
	 */
    public static void setLimitInfo(PolicyDto policyDto,PrpLclaim prpLclaim,HttpServletRequest request) throws Exception{
    	Map<String,PrpCitemKind> initMap = initPrpCitemKind(policyDto.getPrpCitemKindList());
    	double desprate = getDeprecateRate(policyDto,prpLclaim);//取折旧率
    	List<Map<String,Double>> pastCasePay = getPrpLcompensateService().getPastCasePay(prpLclaim.getPolicyNo(), prpLclaim.getClaimNo());
		Map<String,Double> pastCasePayAmount = (Map<String,Double>)pastCasePay.get(0);//历史险别赔付金额
		Map<String,Double> pastCasePayTimes = (Map<String,Double>)pastCasePay.get(1);//历史险别赔付次数
    	Map<String,Double> pastPrpLpersonLossPay = getPrpLcompensateService().getPastPrpLpersonLossPay(prpLclaim.getClaimNo());
    	Map<String,Double> pastPrpLlossPay = getPrpLcompensateService().getPastPrpLlossPay(prpLclaim.getClaimNo());
    	//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
    	Map<String,Double> pastPrpLlossPayE = getPrpLcompensateService().getPastPrpLlossPayE(prpLclaim.getClaimNo());
    	Map<String,Double> pastPrpLpersonLossPayE = getPrpLcompensateService().getPastPrpLpersonLossPayE(prpLclaim.getClaimNo());
    	//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
        
    	Map<String,Map<String,Object>> limitMap = new HashMap<String,Map<String,Object>>();
    	Map<String, Double> facMap = null;//因子代码键值对
    	String kindCode = "";
		for(PrpCitemKind prpCitemKind:policyDto.getPrpCitemKindList()){
			kindCode = prpCitemKind.getKindCode();
			facMap = new HashMap<String, Double>();
			facMap.put(Amount, prpCitemKind.getAmount());//设置因子保额
			facMap.put(DeductibleRate, prpCitemKind.getDeductibleRate()/100);//设置因子自负额比例
			facMap.put(DepreRate, desprate);//设置因子折旧率
			facMap.put(Premium, prpCitemKind.getPremium());//设置因子保费
			facMap.put(TotalPay, pastCasePayAmount.containsKey(kindCode)?pastCasePayAmount.get(kindCode):0d);
		    initKindAmount(prpCitemKind.getKindCode(),facMap,initMap);
		    //mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3
		    limitMap.put(kindCode,process(facMap, prpCitemKind,pastCasePayTimes, pastPrpLpersonLossPay, pastPrpLlossPay,pastPrpLlossPayE,pastPrpLpersonLossPayE));
		}
		processLimitAmountReferOther(limitMap);
		request.setAttribute("limitList", limitMap.values());
		//本案已赔付受害人信息
		request.setAttribute("pastPersonPayList", processPrpLpersonLossPay(pastPrpLpersonLossPay));
    }
    
    /***
     * 强制险理算每个受害人赔付限额的控制
     * @param prpLclaim
     * @param httpServletRequest
     * @throws Exception 
     */
	public static void setLimitInfo(PrpLclaim prpLclaim,HttpServletRequest request) throws Exception {
		String conditions = " compensateno in (select compensateno from prplcompensate where claimno = '"+prpLclaim.getClaimNo()
				+"' and (UNDERWRITEFLAG='1' or UNDERWRITEFLAG ='3')) order by identifynumber asc,liabdetailcode asc";
		List<PrpLpersonLoss> list = getPrpLpersonLossService().findByConditions(conditions);
		Map<String,Double> pastPrpLpersonLossPay = new HashMap<String,Double>();
		String key = "";
		for(PrpLpersonLoss p : list){
			key = p.getLiabDetailCode().charAt(0)+"_"+p.getIdentifyNumber();
			double sumRealPay = p.getSumRealPay();
			if(pastPrpLpersonLossPay.containsKey(key)){
				sumRealPay+=pastPrpLpersonLossPay.get(key);
			}
			pastPrpLpersonLossPay.put(key, sumRealPay);
		}
		request.setAttribute("limitInfoMap", pastPrpLpersonLossPay);
	}
    
    /***
     * 对每人每事故类型的 。处理每个受害人该险别已赔付情况
     * @param pastPrpLpersonLossPay
     * @return
     */
    private static Map<String,Double> processPrpLpersonLossPay(Map<String, Double> pastPrpLpersonLossPay) {
    	Map<String,Double> pastPay = new HashMap<String,Double>();
    	String[] keys = null;
    	List<String> limitForPerPersonType = getPrpLgroovyKindService().getLimitForPerPersonType(ConstantCodes.RISKCODE_DAA);
    	for(Entry<String, Double> entry : pastPrpLpersonLossPay.entrySet()){
    		keys = entry.getKey().split("_");
    		if(keys.length==2 && limitForPerPersonType.contains(keys[1])){
    			pastPay.put(entry.getKey(),entry.getValue());//处理所有已赔付受害人
    		}
    	}
		return pastPay;
	}

	/***
     * 对限额依赖其他险别的进行处理(24\26的特殊处理)
     * @param limitMap
     */
    private static void processLimitAmountReferOther(Map<String, Map<String, Object>> limitMap){
		Set<String> kindCodes = new HashSet<String>();
		kindCodes.addAll(limitMap.keySet());
		Map<String,String[]> limitReferOtherKind = getPrpLgroovyKindService().getLimitReferOtherKind(ConstantCodes.RISKCODE_DAA);
		kindCodes.retainAll(limitReferOtherKind.keySet());//限额同其他险别限额的
		Map<String,Object> limit = null;
		Map<String,Object> limitRefer = null;
		for(String kindCode : kindCodes){
			limit = limitMap.get(kindCode);
			double limitPropAmount = 0d;
			double limitPersonAmount = 0d;
			double limitAmount = 0d;
			String limitType = "";
			for(String otherKind : limitReferOtherKind.get(kindCode)){//可依赖的险别
				if(limitMap.containsKey(otherKind)){//已承保该险别
					limitRefer = limitMap.get(otherKind);
					limitType = String.valueOf(limitRefer.get("limitType"));
					if(limitAmount==0 && ConstantsCollection.KindCodeForPerson.contains(otherKind)){//没有人伤限额的时候继续往下取
						limitAmount = (Double)limitMap.get(otherKind).get("limitAmount");
						if("1".equals(limitType)){
							limitPersonAmount = (Double)limitMap.get(otherKind).get("limitPersonAmount");
						}
					}
					if(limitPropAmount==0 && (ConstantsCollection.KindCodeForCar.contains(otherKind)||ConstantsCollection.KindCodeForProp.contains(otherKind))){
						limitPropAmount =  (Double)limitMap.get(otherKind).get("limitAmount");//对车财的限额
					}
				}
			}
			limit.put("limitType", 2);//每次事故、财产单独部分
			limit.put("limitPropAmount", limitPropAmount);//每次事故财产限额
			limit.put("limitPersonAmount", limitPersonAmount);//每次事故/每人限额
			limit.put("limitAmount", limitAmount + limitPropAmount);//JS校验人伤限额的时候减掉limitPropAmount
		}
	}

	/***
     * 处理保额依赖其主险的
     * @param kindCode 险别
     * @param limit 限制对象
     * @param initMap 当前所有承保险别信息
     */
    private static void initKindAmount(String kindCode,Map<String, Double> limit,Map<String, PrpCitemKind> initMap) {
    	Map<String,String[]> amountReferMainKind = getPrpLgroovyKindService().getAmountReferMainKind(ConstantCodes.RISKCODE_DAA);
    	if(amountReferMainKind.containsKey(kindCode)){
    		for(String temp :amountReferMainKind.get(kindCode)){
    			//mantis： CLM0083，處理人員：BK007 蘇哲，需求單編號：CLM0083 0G抓錯主險的問題
				if(initMap.containsKey(temp) && initMap.get(temp).getAmount() > 0){//当前已承保的主险
					limit.put(Amount, initMap.get(temp).getAmount());
					limit.put(DeductibleRate, initMap.get(temp).getDeductibleRate()/100);//设置因子自负额比例
					break;
				}
			}
		}
	}

	/***
     * 对每个险别进行处理
     * @param facMap 当前计算限额需要的代码
     * @param prpCitemKind 当前险别对象
     * @param pastCasePayTimes 当前承保所有险别已赔付的次数
     * @param pastPrpLpersonLossPay 本案已赔付的人伤
     * @param pastPrpLlossPay 本案已赔付的车财
     * 1.每次事故类型的按limitAmount进行限制
     * 2.每人/每次事故的财产按limitPropAmount为限额，每人限额按limitAmount限制
     * 3.每人/每次事故不赔财产的按limitAmount进行限制
     * 4.计次型的按limitMeter进行限制 -1，本次可赔付；0，本次不可赔付
     * 5.每一人/每次事故 可赔车财的分开限制（如果还参考其他险别的，可能限额不一样 24、26）
     * 
     */
    //mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3
	private static Map<String,Object> process(Map<String, Double> facMap,PrpCitemKind prpCitemKind,
			Map<String,Double> pastCasePayTimes,Map<String,Double> pastPrpLpersonLossPay,
			Map<String, Double> pastPrpLlossPay,Map<String, Double> pastPrpLlossPayE,Map<String,Double> pastPrpLpersonLossPayE) {
		Map<String, Object> limit = new HashMap<String, Object>();// 每个险别的限制参数
		String kindCode = prpCitemKind.getKindCode();
		double limitAmount = Math.ceil(evaluate(facMap, kindCode));// 为方便录入，限额向上取整。
		if (limitAmount < 0) {// 过去算累计的险别没有做控制，超额赔付计算后出现负数的情况
			limitAmount = 0d;
		}
		limit.put("limitResidue", -1d);// 不属于累计型的
		List<String> limitForCumulativeType = getPrpLgroovyKindService().getLimitForCumulativeType(ConstantCodes.RISKCODE_DAA);
		if (limitForCumulativeType.contains(kindCode)) {
			limit.put("limitTotalPay", facMap.get(TotalPay));// 已累计赔付
			limit.put("limitResidue", limitAmount);// 累计赔付达到上限不可赔付
		}
		limit.put("limitAmount", limitAmount);// 本次事故限额
		limit.put("limitKindCode", kindCode);
		limit.put("limitKindName", prpCitemKind.getKindName());
		limit.put("limitFlag", "0");// 是否接受限额控制 0 接受限制
		List<String> KindCodeForNoLimit = getPrpLgroovyKindService().getKindCodeForNoLimit(ConstantCodes.RISKCODE_DAA);
		if (KindCodeForNoLimit.contains(prpCitemKind.getKindCode())) {
			limit.put("limitFlag", "1");// 不接受限额控制
		}
		limit.put("limitMeter", "-1");// 默认本次赔付无限制
		Map<String, Integer> limitForMeterType = getPrpLgroovyKindService().getLimitForMeterType(ConstantCodes.RISKCODE_DAA);
		if (limitForMeterType.containsKey(kindCode) && pastCasePayTimes.containsKey(kindCode) && limitForMeterType.get(kindCode) - pastCasePayTimes.get(kindCode) <= 0) {// 计次型的
			limit.put("limitMaxNum", limitForMeterType.get(kindCode));// 可赔付次数
			limit.put("limitMeter", "0");// 超过赔付次数，不可赔付
		}
		// 本案该险别已赔付
		double prpLlossPay = pastPrpLlossPay.containsKey(kindCode) ? pastPrpLlossPay.get(kindCode) : 0d;
		double prpLpersonLossPay = pastPrpLpersonLossPay.containsKey(kindCode) ? pastPrpLpersonLossPay.get(kindCode) : 0d;
		limit.put("limitPastPay", prpLlossPay + prpLpersonLossPay);

		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		// 超額險別已賠付
		double prpLlossPayE = pastPrpLlossPayE.containsKey(kindCode) ? pastPrpLlossPayE.get(kindCode) : 0d;
		double prpLpersonLossPayE = pastPrpLpersonLossPayE.containsKey(kindCode) ? pastPrpLpersonLossPayE.get(kindCode) : 0d;
		limit.put("limitPastPayE", prpLlossPayE + prpLpersonLossPayE);
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
		// 限制类型
		limit.put("limitType", 0);// 每次事故
		List<String> limitForPerPersonType = getPrpLgroovyKindService().getLimitForPerPersonType(ConstantCodes.RISKCODE_DAA);
		if (limitForPerPersonType.contains(kindCode)) {// 属于每一人/每次事故
			limit.put("limitType", 1);// 每一人/每次事故 只对人
			limit.put("limitPersonAmount", limitAmount);
			String model = DataUtils.dbNullToEmpty(prpCitemKind.getModel());
			if (!"".equals(model)) {// 每一人限额
				double limitPersonAmount = Double.parseDouble(model.split("/")[0]) * 10000;
				limit.put("limitPersonAmount", limitPersonAmount);
			}
			if (getPrpLgroovyKindService().getLimitReferOtherKind(ConstantCodes.RISKCODE_DAA).containsKey(kindCode)) {
				limit.put("limitPersonPastPay", prpLpersonLossPay);
			}
		}
		limit.put("limitDeductible", 0d);
		limit.put("limitDeductibleRate", 0d);
		String deductibleType = prpCitemKind.getDeductibleType();
		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START
		String preConfirm = "請確認 "+kindCode+"-"+prpCitemKind.getKindName();//汽車車體損失保險丙式自負額附加條款
		limit.put("limitDeductibleTypeConfirm", "");
		limit.put("limitDeductibleCount", "0");//有效保期內已使用次數
		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END
		if (DataUtils.emptyToNull(deductibleType) != null) {
			if ("1".equals(deductibleType)) {// 1时Deductible有自负额的具体值
				limit.put("limitDeductible", prpCitemKind.getDeductible());
				//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y
				limit.put("limitDeductibleTypeConfirm", preConfirm+"1 新台幣 - "+prpCitemKind.getDeductible());
			} else if ("3".equals(deductibleType)) {// 3时DeductibleRate有自负额比例的值
				limit.put("limitDeductibleRate", prpCitemKind.getDeductibleRate());
				//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y
				limit.put("limitDeductibleTypeConfirm", preConfirm+"3 百分比 - "+prpCitemKind.getDeductibleRate());
			} else if ("2".equals(deductibleType)) {// 存的代号
				int deductible = prpCitemKind.getDeductible().intValue();// 代号123
				//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START
				if (deductible > 0 && deductible < 5) {// 目前只有123档 20231206 (+4) 增兩個一個有效一個(+5)無自負額所以limitDeductible,limitDeductibleRate都是 空
					String str = "3000/5000/7000,5000/8000,5000/8000/10000,3000/5000/8000";// 对应代号123档的自负额
					String[] args = (str.split(",")[deductible - 1]).split("/");
					int pastPayTimes = 0;
					if (pastCasePayTimes.containsKey(kindCode)) {
						pastPayTimes = pastCasePayTimes.get(kindCode).intValue();
					}
					limit.put("limitDeductibleCount", pastPayTimes);//有效保期內已使用次數
					if (pastPayTimes <= args.length - 1) {
						limit.put("limitDeductible", Double.valueOf(args[pastPayTimes]));
						limit.put("limitDeductibleTypeConfirm", preConfirm+"2 代號 - " +Double.valueOf(args[pastPayTimes]));
					} else {
						limit.put("limitDeductible", Double.valueOf(args[args.length - 1]));
						limit.put("limitDeductibleTypeConfirm", preConfirm+"2 代號 - "+Double.valueOf(args[args.length - 1]));
					}
				}
				//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END
			}
		}
		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START
		if(kindCode.equals("07")){
			limit.put("limitDeductible", 0);
		}
		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END
		return limit;
		// 保额依赖其他险别的
	}
    /***
     * 计算限额
     * @param facMap
     * @param kindCode 险别
     * @return
     * @throws UserException 
     */
    private static double evaluate(Map<String, Double> facMap,String kindCode){
		try {
			Set<Entry<String, Double>> entrySet = facMap.entrySet();
			Binding binding = new Binding();
			for (Entry<String, Double> entry : entrySet) {
				binding.setVariable(entry.getKey(), entry.getValue());
			}
			GroovyShell shell = new GroovyShell(binding);
			if(LimitForSpecialAmount.containsKey(kindCode)){
				return (Double) shell.evaluate(LimitForSpecialAmount.get(kindCode));
			}
			return (Double) shell.evaluate(Common);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0d;
    }
    /***
     * 取折旧率
     * @param policyDto
     * @return
     * @throws Exception 
     */
    private static double getDeprecateRate(PolicyDto policyDto,PrpLclaim prpLclaim) throws Exception {
    	List<PrpCitemCar> prpCitemCarList = policyDto.getPrpCitemCarList();
    	if(prpCitemCarList!=null && !prpCitemCarList.isEmpty()){
    		PrpCitemCar prpCitemCar = prpCitemCarList.get(0);
			String clauseType = prpCitemCar.getClauseType();
			String useNatureCode = prpCitemCar.getUseNatureCode();
			Calendar startDate = Calendar.getInstance();
			startDate.setTime(policyDto.getPrpCmain().getStartDate());//从起保日期
			startDate.set(Calendar.HOUR_OF_DAY, policyDto.getPrpCmain().getStartHour());
			Calendar damageDate = Calendar.getInstance();
			damageDate.setTime(prpLclaim.getDamageStartDate());//到出险日期
			String standardTime = StringConvert.toStandardTime(prpLclaim.getDamageStartHour());
			damageDate.set(Calendar.HOUR_OF_DAY,Integer.parseInt(standardTime.substring(0, 2)));
			damageDate.set(Calendar.MINUTE,Integer.parseInt(standardTime.substring(3, 5)));
			if(CommonUtils.isEmpty(clauseType)) {//
				clauseType = "01";
				if ("1".equals(useNatureCode)) {
					clauseType = "01";
				} else if("2".equals(useNatureCode)){
					clauseType = "02";// 其他
				}
				List<PrpCitemKind> prpCitemKindList = policyDto.getPrpCitemKindList();
				for(PrpCitemKind prpCitemKind : prpCitemKindList){
					if(ConstantCodes.KINDCODE_A01_0L.equals(prpCitemKind.getKindCode())||ConstantCodes.KINDCODE_A01_1B.equals(prpCitemKind.getKindCode())){
						clauseType = "03";
						break;
					}else if(ConstantCodes.KINDCODE_A01_0M.equals(prpCitemKind.getKindCode())||ConstantCodes.KINDCODE_A01_1C.equals(prpCitemKind.getKindCode())){
						clauseType = "04";
						break;
					}
				}
			}
			int carKindCode = getMonth(startDate,damageDate,1);
			BLPrpDdeprecateRateFacade blPrpDdeprecateRateFacade = new BLPrpDdeprecateRateFacade();
			PrpDdeprecateRateDto prpDdeprecateRateDto = blPrpDdeprecateRateFacade.findByPrimaryKey(ConstantCodes.RISKCODE_DAA, clauseType, String.valueOf(carKindCode));
			if (prpDdeprecateRateDto != null) {
				return Double.parseDouble(prpDdeprecateRateDto.getPerMonthRate());
			}
    	}
		return 0;
	}
    /***
     * 获取起保时间和出险时间的月差 
     * 0：一个月内
     */
    private static int getMonth(Calendar start,Calendar end,int mouth){
    	while(true){
        	start.add(Calendar.MONTH, 1);//加一个月
        	if(start.compareTo(end)>0){//不在出险日期之后（包含出险当日）
        		break;
        	}
        	mouth++;
    	}
    	return mouth;
    }

	/**
     * 将险别处理成险别代码：险别信息 的格式
     * @param itemKindList
     * @return
     */
    private static Map<String,PrpCitemKind> initPrpCitemKind(List<PrpCitemKind> itemKindList){
    	Map<String,PrpCitemKind> initMap = new HashMap<String,PrpCitemKind>();
    	for(PrpCitemKind p : itemKindList){
    		initMap.put(p.getKindCode(), p);
    	}
    	return initMap;
    }
    
    
//    
//    /**
//     * 限额取值类型（计次）
//     */
//    private static final Map<String,Integer> LimitForMeterType = new HashMap<String,Integer>();
//    static{
//    	LimitForMeterType.put(ConstantCodes.KINDCODE_A01_Y1,5);
//    	LimitForMeterType.put(ConstantCodes.KINDCODE_A01_Y2,1);
//    }
//    /***
//     * 限额取值类型（保險期間累計）
//     */
//    private static final List<String> LimitForCumulativeType = new ArrayList<String>();
//    static{
//        LimitForCumulativeType.add(ConstantCodes.KINDCODE_A01_0H);    //機車限額碰撞車體損失保險 機車碰撞險    
//        LimitForCumulativeType.add(ConstantCodes.KINDCODE_A01_0J);    //車體損失險乙式(營業大客車專用)
//        LimitForCumulativeType.add(ConstantCodes.KINDCODE_A01_12);    //零件配件被竊損失險    零件被竊險
//        LimitForCumulativeType.add(ConstantCodes.KINDCODE_A01_27);  //第三人責任險附加慰問金條款    附加慰問金
//    }
//    
//    /***
//     * 限额取值类型 (每一人/每次事故)
//     * 此项若增加需严格校验prpCitemKind的model值
//     */
//    private static final List<String> LimitForPerPersonType = new ArrayList<String>();
//    static{
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_3C);//任意汽車第三人責任險失能責任增額
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_31);//任意汽車第三人責任險傷害責任險
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_26);//供教練開車汽車第三人責任險條款 
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_24);//第三人受酒類車禍補償附加條款
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_3A);//任意汽車第三人責任險傷害責任險 
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_52);//第三人附加僱主責任保險 
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_53);//第三人附加旅客責任險 
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_51);//第三人附加乘客體傷責任保險
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_5B);//汽車客運業乘客責任險（每一人死殘）
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_27);//第三人責任險附加慰問金條款
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_3H);//優良駕駛人第三人責任保險傷害責任險－假日
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_3F);//優良駕駛人第三人責任保險傷害責任險－平日
//        LimitForPerPersonType.add(ConstantCodes.KINDCODE_A01_3D);//優良駕駛人第三人責任保險傷害責任險－全時
//    }
//    /***
//     * 保额依赖其主险的
//     * key：险别；value：所属主险
//     */
//    private static final Map<String,String[]> AmountReferMainKind = new HashMap<String,String[]>();
//    static {
//    	String[] physicalKind = {ConstantCodes.KINDCODE_A01_01,ConstantCodes.KINDCODE_A01_05,ConstantCodes.KINDCODE_A01_0J,ConstantCodes.KINDCODE_A01_07,ConstantCodes.KINDCODE_A01_09}; 
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_X1,physicalKind);//車體免折舊  01\05\0J\
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_0G,physicalKind);//附加免追償  01\05\0J\
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_02,physicalKind);//颱風洪水險  01\05\0J\
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_03,physicalKind);//罷工暴動險  01\05\0J\
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_0L,physicalKind);//車體損失險免折舊甲式  0L\01
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_0M,physicalKind);//車體損失險免折舊乙式   0M\05
//    	String [] referMainkind = {ConstantCodes.KINDCODE_A01_01,ConstantCodes.KINDCODE_A01_05,ConstantCodes.KINDCODE_A01_07};
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_0N,referMainkind);
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_0P,referMainkind);
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_0Q,referMainkind);
//    	String[] theftKind = {ConstantCodes.KINDCODE_A01_11};
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_1B,theftKind);//竊盜免折舊  1B
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_1C,theftKind);//竊盜免折舊  1C
//    	AmountReferMainKind.put(ConstantCodes.KINDCODE_A01_17,theftKind);//竊盜免折舊  11
//    }
//    
//    /***
//     * 限额依赖其他险别的，需要在所有计算完成后进行处理
//     * KEY:险别；value：赔付时限额依赖的险别
//     */
//    private static final Map<String,String[]> LimitReferOtherKind = new HashMap<String,String[]>();
//    static {
//    	String[] wineKind = {ConstantCodes.KINDCODE_A01_31,ConstantCodes.KINDCODE_A01_32,ConstantCodes.KINDCODE_A01_3A,ConstantCodes.KINDCODE_A01_3B,ConstantCodes.KINDCODE_A01_3C}; 
//    	LimitReferOtherKind.put(ConstantCodes.KINDCODE_A01_24,wineKind);//附加酒償險  (31,32,3A,3B,3C)
//    	String[] thirdKind = {ConstantCodes.KINDCODE_A01_31,ConstantCodes.KINDCODE_A01_32}; 
//    	LimitReferOtherKind.put(ConstantCodes.KINDCODE_A01_26,thirdKind);//教練第三人  (31,32)
//    }
    
    private static final Map<String,String> LimitForSpecialAmount   = new HashMap<String,String>();
    static {
        //只能賠付一次    保險金額*賠償率*自負額*95%
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_1Y,"P001*(1-P003)*P002*0.95"); 
        //只能賠付一次    保險金額*賠償率*自負額*95%+保險金額*折舊率*95%
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_1X,"P001*(1-P003)*P002*0.95+P001*P003*0.95");
        //只能賠付一次    保險金額*折舊率*95% 
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_1Z,"P001*P003*0.95");
        //每次事故    以保險金額*賠償率*(1-自負額比例)為上限 
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_11,"P001*(1-P003)*(1-P002)");
        //每次事故    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0D,"P001*(1-P003)");
        //每次事故    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0E,"P001*(1-P003)");
        //每次事故    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0F,"P001*(1-P003)");
        //每次事故    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_01,"P001*(1-P003)");
        //每次事故    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_05,"P001*(1-P003)");
        //每次事故    以保險金額*賠償率為上限 
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_07,"P001*(1-P003)");
         //只能賠付一次    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_08,"P001*(1-P003)");
        //只能賠付一次    以保險金額*折舊率*自負額比例為上限(保额取主险)
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_17,"P001*P003*(1-P002)");
        //只能賠付一次    以保險金額*折舊率*自負額比例為上限(保额取主险)
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_1B,"P001*P003*(1-P002)");
        //只能賠付一次    以保險金額*折舊率*自負額比例為上限(保额取主险)
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_1C,"P001*P003*(1-P002)");
        //只能賠付一次    以保險金額*折舊率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_X1,"P001*P003");
        //只能賠付一次    以保險金額*折舊率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0L,"P001*P003");
        //只能賠付一次    以保險金額*折舊率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0M,"P001*P003");
        //以日額*90天為上限（保额是日额）
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_49,"P001*90");
        // mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -start
        //以日額*90天為上限（保额是日额）
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_5D,"P001*90");
        // mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -end
        //機車限額碰撞車體損失保險    保險期間累計 以保險金額為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0H,"P001-P004");
        //車體損失險乙式(營業大客車專用) 保險期間累計 以保險金額為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0J,"P001-P004");
        //零件配件被竊損失險  保險期間累計  以保險金額為上限 
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_12,"P005*6-P004");
        //第三人責任險附加慰問金條款  保險期間累計 第三人責任險附加慰問金條款
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_27,"P001-P004");
        //每次事故    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0K,"P001*(1-P003)");
        
      //每次事故    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0P,"P001*(1-P003)");
      //每次事故    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0N,"P001*(1-P003)");
      //每次事故    以保險金額*賠償率為上限
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_0Q,"P001*(1-P003)");
      //汽車車體損失保險乙式限額不明受損附加條款    以保險金額為上限
        LimitForSpecialAmount.put("0S","P001-P004");
        
        /** 需求變更131 */
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_S1,"P001-P004");
        LimitForSpecialAmount.put(ConstantCodes.KINDCODE_A01_S2,"P001-P004");
    }
    
    /*** 任意险别的通用限额 是已保额为上限 、通用限额取值方式是每次事故 */
    private static final String Common = "P001";//通用限额 是以保额为上线
    private static final String Amount = "P001";//计算因子 ，保额
    private static final String DeductibleRate = "P002";//计算因子 ，自负额比例
    private static final String DepreRate = "P003";//计算因子 ，折旧率
    private static final String TotalPay = "P004";//计算因子，已累计赔付
    private static final String Premium = "P005";//计算因子，保费
}
