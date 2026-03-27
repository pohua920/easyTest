package com.sinosoft.claim.compensate.util;

import ins.framework.common.QueryRule;
import ins.framework.common.ServiceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.claim.common.service.facade.CommonService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.vo.PolicyDto;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLgroovyKind;
import com.sinosoft.claim.schema.service.facade.PrpLgroovyKindService;
import com.sinosoft.sysframework.common.util.DataUtils;

/***
 * 非車限額取值
 * @author 中科軟
 */
public class CompensateLimitViewHelper {

	private CompensateLimitViewHelper() {
	}
	private static final String SPLIT = "_";//连接线
	private static final String LIMIT00 = "00";// 保额
	private static final String LIMIT0A = "0A";// 保险期间赔付
	private static final String LIMIT0G = "0G";// 保险期间赔付计次
	private static final String LIMIT1A = "1A";// 每次事故赔付
	private static final String LIMIT1B = "1B";// 每次事故财产
	private static final String LIMIT1C = "1C";// 每次事故人伤
	private static final String LIMIT1D = "1D";// 每次事故医疗
	private static final String LIMIT1E = "1E";// 每次事故残废
	private static final String LIMIT1F = "1F";// 每次事故死亡
	private static final String LIMIT2C = "2C";// 每一人/每次事故 人伤总
	private static final String LIMIT2D = "2D";// 每一人/每次事故 医疗总
	private static final String LIMIT2E = "2E";// 每一人/每次事故 残废总
	private static final String LIMIT2F = "2F";// 每一人/每次事故 死亡总
	
	
	/***
	 * @param policyDto
	 * @param prpLclaim
	 * @param request
	 * @throws Exception
	 */
	public void setLimitInfo(PolicyDto policyDto, PrpLclaim prpLclaim, HttpServletRequest request) throws Exception {
		Map<String, List<PrpCitemKind>> kindMap = initPrpCitemKind(policyDto.getPrpCitemKindList());
		PrpCmain prpCmain = policyDto.getPrpCmain();
		String policyNo = prpCmain.getPolicyNo();
		String claimNo = "";
		if (prpLclaim != null) {
			claimNo = prpLclaim.getClaimNo();
		}
		// 获取保单历史（非本次）赔付
		Map<String, Double> hisPayMap = this.getHisPay(policyNo, claimNo);// 获取每个险别的00（保险期间已赔付）,06（保险期间已赔付次数）
		if (DataUtils.emptyToNull(claimNo) != null) {
			this.getHisPayPrpLloss(hisPayMap, claimNo);// 本案已赔付财产
			this.getHisPayPrpLpersonLoss(hisPayMap, claimNo);// 本案已赔付人伤，
		}
		String conditions = " riskCode = '" + prpCmain.getRiskCode() + "' order by kindCode asc , limitOrder asc ";
		List<PrpLgroovyKind> list = this.getPrpLgroovyKindService().findPrpLgroovyKind(QueryRule.getInstance().addSql(conditions));
		// 組織好每個險別的限制訊息
		Map<String, List<PrpLgroovyKind>> gkMap = new LinkedHashMap<String, List<PrpLgroovyKind>>();
		if (list != null && !list.isEmpty()) {
			String tempKey = null;
			List<PrpLgroovyKind> tempValue = null;
			for (PrpLgroovyKind g : list) {
				tempKey = g.getKindCode();
				if (gkMap.containsKey(tempKey)) {
					gkMap.get(tempKey).add(g);
				} else {
					tempValue = new ArrayList<PrpLgroovyKind>();
					tempValue.add(g);
					gkMap.put(tempKey, tempValue);
				}
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("prpcmain", prpCmain);
		params.put("prplclaim", prpLclaim);
		params.put("allkindlist", policyDto.getPrpCitemKindList());
		// 处理每个险别的限额讯息
		Map<String, Double> limitMap = new HashMap<String, Double>();
		if (!kindMap.isEmpty()) {
			String tempKey = null;
			List<PrpCitemKind> tempValue = null;
			Map<String, PrpLgroovyKind> groovy = null;
			List<String> tempList = null;
			Double amount = null;
			String[] limitKind = { LIMIT00, LIMIT0A, LIMIT0G, LIMIT1A, LIMIT1B, LIMIT1C, LIMIT1D, LIMIT1E, LIMIT1F, LIMIT2C, LIMIT2D, LIMIT2E, LIMIT2F };
			for (Entry<String, List<PrpCitemKind>> entry : kindMap.entrySet()) {
				tempKey = entry.getKey();
				tempValue = entry.getValue();
				amount = tempValue.get(0).getAmount();
				limitMap.put(tempKey + SPLIT + LIMIT00, amount);
				params.put("kindlist", tempValue);
				groovy = this.getLimitGroovy(gkMap.get(tempKey));
				tempList = new ArrayList<String>();
				for (String str : limitKind) {
					this.process(limitMap, tempList,tempKey, str, groovy, tempValue, params);
				}
				limitMap.put(tempKey + SPLIT + "FLAG", 0d);//記錄該險別是否有校驗
				if(tempList.isEmpty()){//没有配置任何限制，
					amount = limitMap.get(tempKey + SPLIT + LIMIT00);
					if (amount != null && amount > 0) {//保額不為0，以保額為每次事故上限，
						limitMap.put(tempKey + SPLIT + LIMIT1A, amount);//
					}else{
						limitMap.put(tempKey + SPLIT + "FLAG", -1d);//這種情況，則會跟總保額去校驗
					}
				}
			}
		}
		limitMap.put("sumamount", prpCmain.getSumAmount() == null ? 0d : prpCmain.getSumAmount());
		request.setAttribute("KindHisPayMap", hisPayMap);
		request.setAttribute("KindLimitMap", limitMap);
	}

	private void process(Map<String, Double> limitMap, List<String> tempList, String kindCode, String limit, Map<String, PrpLgroovyKind> groovy, List<PrpCitemKind> kindList, Map<String, Object> params) {
		params.put("kindcode", kindCode);
		params.put("amount", limitMap.get(kindCode + SPLIT + LIMIT00));
		Double limitValue = null;
		PrpLgroovyKind tempGroovy = groovy.get(limit);
		if (tempGroovy != null) {
			if (DataUtils.emptyToNull(tempGroovy.getLimitValue()) != null) {
				limitValue = Double.parseDouble(tempGroovy.getLimitValue());
			} else if (DataUtils.emptyToNull(tempGroovy.getLimitJsCode()) != null) {
				try {
					limitValue = (Double) ScriptEngineHelper.eval(tempGroovy.getId(), tempGroovy.getLimitJsCode(), params);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// --限额类型 0，保险期间累计；1，每次事故；2，每一人/每次事故；
		// --限额项目 0 ，保額； A，人伤+财产；B，财产；C，人伤；D，医疗；E，残废；F，死亡；G，计次；
		// --至少有一项，每次事故的限制
		// --组合情况 ：
		// --00保额取值、0A保险期间赔付、0G保险期间计次
		// --1A每次事故赔付、1B每次事故财产、1C每次事故人伤、1D每次事故医疗、1D每次事故残废、1F每次事故死亡、
		// --2C每一人/每次事故 人伤总、2D每一人/每次事故 医疗总、2E每一人/每次事故 残废总、2F每一人/每次事故 死亡总、
		if(limitValue !=null && ! LIMIT00.equals(limit) && !LIMIT0G.equals(limit)){
			tempList.add(limit);//记录限制的项目，每个险别校验完毕，若集合为空，则会给一条默认的每次事故
		}
		if(!(limitValue == null && LIMIT00.equals(limit)) ){//没有配置保额，
			limitMap.put(kindCode + SPLIT + limit, limitValue == null ? -1d : limitValue);//-1表示无限制，
		}
	}
	
	/***
	 * 将限制讯息组织入Map
	 * @param tempgk
	 * @return
	 */
	private Map<String, PrpLgroovyKind> getLimitGroovy(List<PrpLgroovyKind> tempgk) {
		Map<String, PrpLgroovyKind> temp = new HashMap<String, PrpLgroovyKind>();
		if (!CommonUtils.isEmpty(tempgk)) {
			for (PrpLgroovyKind p : tempgk) {
				temp.put(p.getLimitType() + p.getLimitKind(), p);
			}
		}
		return temp;
	}
	/**
	 * 将险别处理成险别代码：险别信息 的格式
	 * @param itemKindList
	 * @return
	 */
	private static Map<String, List<PrpCitemKind>> initPrpCitemKind(List<PrpCitemKind> itemKindList) {
		Map<String, List<PrpCitemKind>> initMap = new LinkedHashMap<String, List<PrpCitemKind>>();
		String tempKey = "";
		List<PrpCitemKind> list = null;
		for (PrpCitemKind p : itemKindList) {
			tempKey = p.getKindCode();
			if(initMap.containsKey(tempKey)){
				initMap.get(tempKey).add(p);
			} else {
				list = new ArrayList<PrpCitemKind>();
				list.add(p);
				initMap.put(tempKey, list);
			}
		}
		return initMap;
	}
	/**
	 * 获取本案已赔付人伤。
	 * @param claimNo
	 * @return
	 */
	private void getHisPayPrpLpersonLoss(Map<String, Double> hisPayMap, String claimNo) {
		StringBuffer statements = new StringBuffer("");
		statements.append(" SELECT N.KINDCODE,N.PAYOBJECTSERIALNO,N.CASUALTIES,N.IDENTIFYNUMBER,N.CERTIFICATECODE FROM ");
		statements.append(" PRPLCOMPENSATE M INNER JOIN PRPLPERSONLOSS N ON M.COMPENSATENO = N.COMPENSATENO WHERE ");
		StringBuffer conditions = new StringBuffer("");
		conditions.append(" (M.UNDERWRITEFLAG = '1' OR M.UNDERWRITEFLAG = '3') AND M.CLAIMNO = '" + claimNo + "'");
		statements.append(conditions).append(" ORDER BY M.INPUTDATE ASC,N.PERSONNO ASC , N.SERIALNO ASC ");
		List<?> result = this.getCommonService().findByStatements(statements.toString());
		String tempKindCode = "";
		String id = "";//代表伤员唯一的ID 证件类型 + 证件号码一致
		String tempPayObjectserialNo = "";
		String casualties = "";//傷亡情形
		if (result != null && !result.isEmpty()) {
			Object[] objs = null;
			for (Object obj : result) {
				objs = (Object[]) obj;
				if (objs[0] != null) {
					tempKindCode = String.valueOf(objs[0]);
					tempPayObjectserialNo = (String) objs[1];
					casualties = String.valueOf(objs[2]);
					id = DataUtils.dbNullToEmpty(String.valueOf(objs[3])) + SPLIT + DataUtils.dbNullToEmpty(String.valueOf(objs[4]));
					double realPay = this.getRealPay(tempPayObjectserialNo);
					// （相加部分为limitType + limitKind ）
					this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT0A, realPay);// 保险期间赔付
					this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT1A, realPay);// 本次事故赔付
					this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT1C, realPay);// 本次事故人伤總
					this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT2C + SPLIT + id, realPay);// // 每一人/每次事故人伤
					if ("1".equals(casualties)) {
						this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT1D, realPay);// 每次事故醫療
						this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT2D + SPLIT + id, realPay);// 每一人/每次事故醫療
					} else if ("2".equals(casualties)) {
						this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT1E, realPay);// 每次事故失能
						this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT2E + SPLIT + id, realPay);// 每一人/每次事故失能
					} else if ("3".equals(casualties)) {
						this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT1F, realPay);// 每次事故死亡
						this.setPay(hisPayMap, tempKindCode + SPLIT + LIMIT2F + SPLIT + id, realPay);// 每一人/每次事故死亡
					}
				}
			}
		}
	}

	/**
	 * 获取本案已赔付。
	 * @param claimNo
	 * @return
	 */
	private void getHisPayPrpLloss(Map<String, Double> hisPayMap, String claimNo) {
		StringBuffer statements = new StringBuffer("");
		statements.append(" SELECT N.COMPENSATENO,N.KINDCODE,N.PAYOBJECTSERIALNO FROM ");
		statements.append(" PRPLCOMPENSATE M INNER JOIN PRPLLOSS N ON M.COMPENSATENO = N.COMPENSATENO WHERE ");
		StringBuffer conditions = new StringBuffer("");
		conditions.append(" (M.UNDERWRITEFLAG = '1' OR M.UNDERWRITEFLAG = '3') AND M.CLAIMNO = '" + claimNo + "'");
		statements.append(conditions).append(" ORDER BY M.INPUTDATE ASC,N.SERIALNO ASC ");
		List<?> result = this.getCommonService().findByStatements(statements.toString());
		String tempCompensateNo = "";
		String tempKindCode = "";
		String tempPayObjectserialNo = "";
		if (result != null && !result.isEmpty()) {
			Object[] objs = null;
			for (Object obj : result) {
				objs = (Object[]) obj;
				if (objs[0] != null) {
					tempCompensateNo = String.valueOf(objs[0]);
					tempKindCode = String.valueOf(objs[1]);
					tempPayObjectserialNo = (String)objs[2];
					double realPay = this.getRealPay(tempPayObjectserialNo);
					this.setPay(tempCompensateNo, hisPayMap, tempKindCode + SPLIT + LIMIT0A, realPay);// 保险期间赔付
					this.setPay(tempCompensateNo, hisPayMap, tempKindCode + SPLIT + LIMIT1A, realPay);// 每次事故赔付
					this.setPay(tempCompensateNo, hisPayMap, tempKindCode + SPLIT + LIMIT1B, realPay);// 每次事故财产
				}
			}
		}
	}
	
	/**
	 * 获取保单历史赔付的讯息
	 * @param policyNo
	 * @param claimNo
	 * @return
	 */
	public Map<String, Double> getHisPay(String policyNo, String claimNo) {
		Map<String, Double> hisPayMap = new HashMap<String, Double>();
		StringBuffer statements = new StringBuffer("");
		statements.append(" SELECT M.CLAIMNO,N.COMPENSATENO,N.KINDCODE,N.PAYOBJECTSERIALNO FROM ");
		statements.append(" PRPLCOMPENSATE M INNER JOIN PRPLLOSS N ON M.COMPENSATENO = N.COMPENSATENO WHERE ");
		StringBuffer conditions = new StringBuffer("");
		conditions.append(" (M.UNDERWRITEFLAG = '1' OR M.UNDERWRITEFLAG = '3') AND M.POLICYNO='" + policyNo + "' ");
		if (DataUtils.emptyToNull(claimNo) != null) {
			conditions.append(" AND M.CLAIMNO != '" + claimNo + "' ");
		}
		Map<String, Set<String>> temp = new HashMap<String, Set<String>>();
		statements.append(conditions).append(" ORDER BY M.INPUTDATE ASC,N.SERIALNO ASC ");
		this.getHisPay(statements.toString(), temp, hisPayMap);// 处理财损部分
		statements = new StringBuffer("");
		statements.append(" SELECT M.CLAIMNO,N.COMPENSATENO,N.KINDCODE,N.PAYOBJECTSERIALNO FROM ");
		statements.append(" PRPLCOMPENSATE M INNER JOIN PRPLPERSONLOSS N ON M.COMPENSATENO = N.COMPENSATENO WHERE ");
		statements.append(conditions).append(" ORDER BY M.INPUTDATE ASC,N.PERSONNO ASC , N.SERIALNO ASC");
		this.getHisPay(statements.toString(), temp, hisPayMap);// 处理人伤部分
		for (Entry<String, Set<String>> entry : temp.entrySet()) {
			hisPayMap.put(entry.getKey(), Double.valueOf(entry.getValue().size()));
		}
		return hisPayMap;
	}

	/***
	 * 处理险别历史赔付讯息
	 * @param statements
	 * @param hisPayTimes
	 * @param hisPayMap
	 */
	private void getHisPay(String statements, Map<String, Set<String>> hisPayTimes, Map<String, Double> hisPayMap) {
		List<?> result = this.getCommonService().findByStatements(statements);
		String tempCompensateNo = "";
		String tempKindCode = "";
		String tempPayObjectserialNo = "";
		String tempClaimNo = "";
		if (result != null && !result.isEmpty()) {
			Object[] objs = null;
			for (Object obj : result) {
				objs = (Object[]) obj;
				if (objs[0] != null) {
					tempClaimNo = String.valueOf(objs[0]);
					tempCompensateNo = String.valueOf(objs[1]);
					tempKindCode = String.valueOf(objs[2]);
					tempPayObjectserialNo = (String)objs[3];
					this.setTimes(hisPayTimes, tempClaimNo, tempKindCode + SPLIT + LIMIT0G);// 保险期间已賠付計次
					double realPay = this.getRealPay(tempPayObjectserialNo);
					this.setPay(tempCompensateNo, hisPayMap, tempKindCode + SPLIT + LIMIT0A, realPay);// 保险期间已累計赔付
				}
			}
		}
	}

	/***
	 * 处理险别已赔付
	 * @param hisPayAmount
	 * @param compensateNo
	 * @param kindCodeKey
	 * @param payObjectserialNo
	 */
	private void setPay(String compensateNo, Map<String, Double> hisPayAmount, String kindCodeKey, double realPay) {
		if (compensateNo.startsWith("R")) {// 追偿部分要扣掉
			realPay = realPay * (-1);
		}
		this.setPay(hisPayAmount, kindCodeKey, realPay);
	}

	/***
	 * 处理每个险别的赔付次数
	 * @param temp
	 * @param claimNo
	 * @param kindCode
	 */
	private void setTimes(Map<String, Set<String>> temp, String claimNo, String kindCode) {
		if (temp.containsKey(kindCode)) {
			temp.get(kindCode).add(claimNo);
		} else {
			Set<String> set = new HashSet<String>();
			set.add(claimNo);
			temp.put(kindCode, set);
		}
	}

	/***
	 * 赔付对象序号中取险别的赔付金额
	 * @param payObjectserialNo
	 * @return
	 */
	private double getRealPay(String payObjectserialNo) {
		double d = 0d;
		if (DataUtils.emptyToNull(payObjectserialNo) != null) {
			String[] str = payObjectserialNo.split(";");
			for (String s : str) {
				d += Double.parseDouble(s.substring(s.indexOf(":") + 1));
			}
		}
		return d;
	}

	/***
	 * 处理赔付的信息
	 * @param map
	 * @param key
	 */
	private void setPay(Map<String, Double> map, String key, Double addValue) {
		double payAmount = 0d;
		if (map.containsKey(key)) {
			payAmount = map.get(key).doubleValue();
		}
		map.put(key, payAmount + addValue);
	}

	public static CompensateLimitViewHelper getInstance() {
		return new CompensateLimitViewHelper();
	}

	private PrpLgroovyKindService getPrpLgroovyKindService() {
		return (PrpLgroovyKindService) ServiceFactory.getService("prpLgroovyKindService");
	}

	private CommonService getCommonService() {
		return (CommonService) ServiceFactory.getService("commonService");
	}
}
