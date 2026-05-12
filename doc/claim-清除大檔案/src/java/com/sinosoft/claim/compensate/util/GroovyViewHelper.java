package com.sinosoft.claim.compensate.util;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ins.framework.common.ServiceFactory;
import ins.framework.utils.DataUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.schema.model.PrpLloss;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.service.facade.PrpLdisabilityLimitService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * 自定义理賠金計算帮助类 1、配置因子 2、配置公式 3、配置各因子初始化值 initValue 4、
 * @Description 
 * @author 中科软
 */
public class GroovyViewHelper {

	private static PrpLdisabilityLimitService getLimitService() {
		return (PrpLdisabilityLimitService) ServiceFactory.getService("prpLdisabilityLimitService");
	}

	/**
	 * 计算入口
	 * @Description: 任意险理賠金計算
	 * @author 中科软
	 * @date Apr 26, 2013 5:24:25 PM
	 * @param object 车财损失对象PrpLloss；人伤损失对象：PrpLpersonLoss
	 * @return
	 * @throws Exception
	 */
	public static Object evaluate(Object object) throws Exception {
		String kindCode = "";// 险别
		double sumRealPay = 0d;
		
		if (object instanceof PrpLloss) {
			PrpLloss prpLloss = (PrpLloss) object;
			kindCode = prpLloss.getKindCode();
			if(!PRPLLOSS_MAP.containsKey(kindCode)){
				throw new UserException(0, -1, "理賠金計算", "車損、物損賠付不屬于‘"+prpLloss.getKindName()+"’的責任範圍!");
			}
			if(prpLloss.getSumDefPay() < 0){//损失为负 则不参与计算，直接返回
				return prpLloss.getSumDefPay();
			}
			sumRealPay =(Double)evaluate(initPrpLlossValue(prpLloss), PRPLLOSS_MAP.get(kindCode), kindCode);
		} else if (object instanceof PrpLpersonLoss) {
			PrpLpersonLoss prpLpersonLoss = (PrpLpersonLoss) object;
			kindCode = prpLpersonLoss.getKindCode();
			if(!PRPLPERSONLOSS_MAP.containsKey(kindCode)){
				throw new UserException(0, -1, "理賠金計算", "受害人損賠付不屬于‘"+prpLpersonLoss.getKindName()+"’責任範圍!");
			}
			if(prpLpersonLoss.getSumDefPay() < 0){//损失为负 则不参与计算，直接返回
				return prpLpersonLoss.getSumDefPay();
			}
			// 如果是残废
			if (("47".equals(kindCode) || "48".equals(kindCode)) && DataUtils.emptyToNull(prpLpersonLoss.getInjuryGrade()) != null) {
				return Double.valueOf(getLimitService().getPrpLdisabilityLimitFee(prpLpersonLoss.getClaimNo(), prpLpersonLoss.getInjuryGrade()));
			}
			sumRealPay =(Double)evaluate(initPrpLpersonLossValue(prpLpersonLoss), PRPLPERSONLOSS_MAP.get(kindCode), kindCode);
		} else {
			throw new UserException(0, -1, "理賠金計算", "輸入參數不正確，無法計算!");
		}
		return sumRealPay < 0 ? 0 : sumRealPay;
	}

	/**
	 * @author 中科软
	 * @date Apr 26, 2013 5:05:51 PM
	 * @param facMap 因子代码键值对
	 * @param content 公式内容
	 * @return 公式计算结果
	 * @throws UserException
	 */
	private static Object evaluate(Map<String, Double> facMap, String content, String kindCode) throws UserException {
		if (DataUtils.emptyToNull(content) == null) {
			throw new UserException(0, -1, "理賠金計算", "系統未配置險別 " + kindCode + " 的理算公式!");
		}
		Set<Entry<String, Double>> entrySet = facMap.entrySet();
		Binding binding = new Binding();
		for (Entry<String, Double> entry : entrySet) {
			binding.setVariable(entry.getKey(), entry.getValue());
		}
		GroovyShell shell = new GroovyShell(binding);
		try {
			return shell.evaluate(content);
		} catch (Exception e) {
			throw new UserException(0, -1, "理賠金計算", "公式：理賠金 = " + getCNcontent(content) + " 計算失敗");
		}
	}

	/**
	 * 得到计算公式的中文表示法
	 * @param content 公式内容
	 * @return
	 */
	private static String getCNcontent(String content) {
		for (Entry<String, String> entry : FAC_CN.entrySet()) {
			content = content.replaceAll(entry.getKey(), entry.getValue());
		}
		return content;
	}

	/**
	 * 初始化任意险车财理算各因子的值
	 * @author 中科软
	 * @param prpLloss
	 * @return
	 */
	private static Map<String, Double> initPrpLlossValue(PrpLloss prpLloss) {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put(PrpLloss_sumDefPay, prpLloss.getSumDefPay());// 核定赔偿
		map.put(PrpLloss_depreRate, prpLloss.getDepreRate() / 100);// 折旧率
		map.put(PrpLloss_deductibleRate, prpLloss.getDutyDeductibleRate() / 100);// 免赔率/自负额比例\现在取的是事故责任免赔率
		map.put(PrpLloss_deductible, prpLloss.getDeductible());// 免赔额/自负额
		map.put(PrpLloss_sumRest, prpLloss.getSumRest());// 属性剔除金额/残值/损余
		map.put(PrpLloss_indemnityDutyRate, prpLloss.getIndemnityDutyRate() / 100);// 責任比率
		return map;
	}

	/**
	 * 初始化任意險人傷理算各因子的值
	 * @author 中科软
	 * @param prpLpersonLoss 
	 * @return
	 */
	private static Map<String, Double> initPrpLpersonLossValue(PrpLpersonLoss prpLpersonLoss) {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put(PrpLpersonLoss_sumDefPay, prpLpersonLoss.getSumDefPay());// 核定赔偿
		map.put(PrpLpersonLoss_deductible, prpLpersonLoss.getSumRest());// 免赔额/自负额\现取的是\sumRest
		map.put(PrpLpersonLoss_compelPay, prpLpersonLoss.getCompelPay());// 强制险赔付金额
		return map;
	}

	/** 任意險車財理算公式 计算因子：核定赔偿 */
	private static final String PrpLloss_sumDefPay = "P001";
	/** 任意險車財理算公式 计算因子：折旧率 */
	private static final String PrpLloss_depreRate = "P002";
	/** 任意險車財理算公式 计算因子 ：免赔率/自负额比例*/
	private static final String PrpLloss_deductibleRate = "P003";
	/** 任意險車財理算公式 计算因子 ：免赔额/自负额*/
	private static final String PrpLloss_deductible = "P004";
	/** 任意險車財理算公式 计算因子：属性剔除金额/残值/损余 */
	private static final String PrpLloss_sumRest = "P005";
	/** 任意險車財理算公式 计算因子：肇事責任比率 */
	private static final String PrpLloss_indemnityDutyRate = "P006";
	/** 任意險人傷理赔金计算因子：核定赔偿 */
	private static final String PrpLpersonLoss_sumDefPay = "P101"; // 因子：
	/** 任意險人傷理赔金计算因子：自负额 */
	private static final String PrpLpersonLoss_deductible = "P102";
	/** 任意險人傷理赔金计算因子：強制險給付金額 */
	private static final String PrpLpersonLoss_compelPay = "P103";
	/** 理赔金计算各因子对应的中文名称 */
	private static final Map<String, String> FAC_CN = new HashMap<String, String>();
	static {
		FAC_CN.put(PrpLloss_sumDefPay, "核定賠償");
		FAC_CN.put(PrpLloss_depreRate, "折舊率");
		FAC_CN.put(PrpLloss_deductibleRate, "自負額比率");
		FAC_CN.put(PrpLloss_deductible, "自負額");
		FAC_CN.put(PrpLloss_sumRest, "殘值");
		FAC_CN.put(PrpLloss_indemnityDutyRate, "肇事責任比率");

		FAC_CN.put(PrpLpersonLoss_sumDefPay, "核定賠償");
		FAC_CN.put(PrpLpersonLoss_deductible, "自負額");
		FAC_CN.put(PrpLpersonLoss_compelPay, "強制險給付金額");
	}

	/**
	 * 任意險車財理算公式配置
	 */
	private static final Map<String, String> PRPLLOSS_MAP = new HashMap<String, String>();
	static {
		// //車體險 核定賠償 ×（1－折舊率）- 自負額 ＝理賠金
		// PRPLLOSS_MAP.put("", "P001*(1-P002)-P004");
		// //整車失竊 核定賠償 ×（1－折舊率） × （1－自負額比率）＝ 理賠金
		// PRPLLOSS_MAP.put("", "P001*(1-P002)*(1-P003)");
		// 車體損失險甲式 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("01", "P001*(1-P002)-P005-P004");
		// 車體損失險乙式 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("05", "P001*(1-P002)-P005-P004");
		// 車對車碰撞車體損失險 核定賠償 ×（1－折舊率）- 殘值 ＝理賠金
		PRPLLOSS_MAP.put("07", "P001*(1-P002)-P005");
		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y START
		// 車體損失保險丙式自負額附加條款  核定賠償 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("0Y", "P001-P004");
		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y END
		// 重大事故車體損失保險 核定賠償 ×（1－折舊率）- 殘值 ＝理賠金
		PRPLLOSS_MAP.put("08", "P001*(1-P002)-P005");
		// 限額車對車碰撞損失險 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("09", "P001");
		// 車體損失險乙式＋附加限定駕駛人 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("0F", "P001*(1-P002)-P005-P004");
		// 車體損失險免追償附加條款 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("0G", "P001*(1-P002)-P005-P004");
		// 機車限額碰撞車體損失保險 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("0H", "P001");
		// 機車火災事故車體損失保險 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("0I", "P001");
		// 車體損失險乙式 核定賠償 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("0J", "P001-P004");
		// 颱風洪水險 ( 詳如保單條款 ) 核定賠償 ×（1－折舊率）- 殘值＝理賠金
		PRPLLOSS_MAP.put("02", "P001*(1-P002)-P005");
		// 罷工，暴動，民眾騷擾險 核定賠償 ×（1－折舊率）- 殘值＝理賠金
		PRPLLOSS_MAP.put("03", "P001*(1-P002)-P005");
		// 車體損失險全損免折舊 核定賠償 × 折舊率 ＝ 理賠金
		PRPLLOSS_MAP.put("X1", "P001*P002");
		// 車體損失險甲式附加代車費用 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("0A", "P001");
		// 車體損失險乙式附加代車費用 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("0B", "P001");
		// 車體損失險丙式附加代車費用 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("0C", "P001");
		// 汽車竊盜損失險 核定賠償 ×（1－折舊率） × （1－自負額比率）- 殘值＝ 理賠金
		PRPLLOSS_MAP.put("11", "P001*(1-P002)*(1-P003)-P005");
		// 零件配件被竊損失險 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("12", "P001");
		// 汽車竊盜損失保險零配件被竊高額保障附加條 核定賠償 ×（1－自負額比率）＝ 理賠金
		PRPLLOSS_MAP.put("1A", "P001*(1-P003)");
		// 竊盜損失差額補償保險 ( 甲型 ) 核定賠償 ×（自負額比率 + 折舊率） × 0.95＝ 理賠金
		PRPLLOSS_MAP.put("1X", "P001*(P003+P002)*0.95");
		// 竊盜損失差額補償保險 ( 乙型 ) 核定賠償 × 自負額比率 × 0.95＝ 理賠金
		PRPLLOSS_MAP.put("1Y", "P001*P003*0.95");
		// 竊盜損失差額補償保險 ( 丙型 ) 核定賠償 × 折舊率 × 0.95＝ 理賠金
		PRPLLOSS_MAP.put("1Z", "P001*P002*0.95");
		// 汽車竊盜損失保險附加代車費用 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("14", "P001");
		// 汽車竊盜損失險全損免折舊 核定賠償 ×折舊率 × (1－自負額比率)＝ 理賠金
		PRPLLOSS_MAP.put("17", "P001*P002*(1-P003)");
		// 機車整車失竊限額損失保險 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("18", "P001");
		// 任意汽車第三人責任險財損責任險 核定賠償 × 肇事責任比率 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("32", "P001*P006-P004");
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4
		PRPLLOSS_MAP.put("C4", "P001*P006-P004");
		// 任意汽車第三人責任險財損責任險 核定賠償 × 肇事責任比率 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("3B", "P001*P006-P004");
		// 汽車貨物運送人責任險－貨物 核定賠償 × 肇事責任比率 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("71", "P001*P006-P004");
		// 汽車貨物運送人責任險－貨櫃 核定賠償 × 肇事責任比率 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("72", "P001*P006-P004");
		// 汽車經銷商汽車車體損失險 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("81", "P001*(1-P002)-P005-P004");
		// 汽車經銷商汽車竊盜損失險 核定賠償 ×（1－折舊率） × （1－自負額比率）- 殘值＝ 理賠金
		PRPLLOSS_MAP.put("82", "P001*(1-P002)*(1-P003)-P005");
		// 道路救援保險附加條款 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("Y1", "P001");
		// 道路救援保險附加條款－計次型 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("Y2", "P001");	
		//mantis： CLM0134，處理人員：DP0706，需求單編號：CLM0134新增拖吊險商品Y3
		//道路救援費用附加條款-A型
		PRPLLOSS_MAP.put("Y3", "P001");
		// 第三人受酒類車禍補償附加條款    核定賠償 × 肇事責任比率 - 自負額 ＝理賠金(赔付车物损时同32、3B)
		PRPLLOSS_MAP.put("24", "P001*P006-P004");
		//第三人責任超額保險（不含酒償險）
		PRPLLOSS_MAP.put("E1", "P001");
		//第三人責任超額保險（含酒償險）
		PRPLLOSS_MAP.put("E2", "P001");

		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		//第三人死亡及失能超額責任保險  賠償金額=核定賠償金額
		PRPLLOSS_MAP.put("E9", "P001");
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
		
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816新增可賠付商品險種(E5,E6,E7) start
		//第三人責任保險超額責任-乙式（不含酒償險）賠償金額=核定賠償金額
		PRPLLOSS_MAP.put("E5", "P001");
		//第三人責任保險超額責任-乙式（含酒償險）賠償金額=核定賠償金額
		PRPLLOSS_MAP.put("E6", "P001");
		//第三人責任保險超額責任-乙式（營業車）賠償金額=核定賠償金額
		PRPLLOSS_MAP.put("E7", "P001");
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 end
		
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		//第三人責任附加超額險A式 賠償金額=核定賠償金額
		PRPLLOSS_MAP.put("E3", "P001");
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
				
		//26 供教練開車汽車第三人責任險條款  核定賠償 × 肇事責任比率 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("26", "P001*P006-P004");
		//16  汽車車體損失險甲乙丙式附加代車傳保發用    核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("16", "P001");
		//22 汽車竊盜損失保險附加代車費用傳保發用    核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("22", "P001");
		//0D 車體損失限乙式限定駕駛人 舊  核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("0D", "P001*(1-P002)-P005-P004");
		//0E 車體損失險乙式限定駕駛人-舊  核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("0E", "P001*(1-P002)-P005-P004");
		//汽車車體損失保險丙式-免自負額車對車限定駕駛人附加條款 核定賠償 ×（1－折舊率）- 殘值 ＝理賠金
		PRPLLOSS_MAP.put("0K", "P001*(1-P002)-P005");
		// 優良駕駛人第三人責任保險財損責任險－假日 核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("3I", "P001");
		// 優良駕駛人第三人責任保險財損責任險－平日 核定賠償  ＝理賠金
		PRPLLOSS_MAP.put("3G", "P001");
		// 優良駕駛人第三人責任保險財損責任險－全時  核定賠償 ＝理賠金
		PRPLLOSS_MAP.put("3E", "P001");
		//0L 車體險折舊率附件條款甲式  核定賠償*折舊率 ＝理賠金
		PRPLLOSS_MAP.put("0L", "P001*P002");
		//0M 車體險折舊率附件條款乙式  核定賠償*折舊率 ＝理賠金
		PRPLLOSS_MAP.put("0M", "P001*P002");
		//1B 竊盜險折舊率附件條款甲式     核定賠償*折舊率*（1-自負額比率） ＝理賠金
		PRPLLOSS_MAP.put("1B", "P001*P002*(1-P003)");
		//1C 竊盜險折舊率附件條款甲式     核定賠償*折舊率*（1-自負額比率） ＝理賠金
		PRPLLOSS_MAP.put("1C", "P001*P002*(1-P003)");
		// 車體損失險甲式 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("0N", "P001*(1-P002)-P005-P004");
		// 車體損失險甲式 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("0P", "P001*(1-P002)-P005-P004");
		// 車體損失險甲式 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("0Q", "P001*(1-P002)-P005-P004");
		// 汽車車體損失保險乙式限額不明受損附加條款    核定賠償  ＝ 理賠金
		PRPLLOSS_MAP.put("0S", "P001");
		// 汽車車體損失保險新車全損免折舊附加條款   核定賠償  ＝ 理賠金
		PRPLLOSS_MAP.put("0T", "P001");
		// 汽車竊盜損失保險新車全損免折舊附加條款    核定賠償  ＝ 理賠金
		PRPLLOSS_MAP.put("1D", "P001");
		//mantis： CLM0059 ，處理人員：BK007 蘇哲，需求單編號：CLM0059 短期車險-start
		// 限額車對車碰撞損失險 核定賠償-短期 ＝理賠金
		PRPLLOSS_MAP.put(ConstantCodes.KINDCODE_A01_9A, "P001");
		//mantis： CLM0059 ，處理人員：BK007 蘇哲，需求單編號：CLM0059 短期車險-end
		
		/** 需求變更131 */
		PRPLLOSS_MAP.put(ConstantCodes.KINDCODE_A01_S1, "P001");
		PRPLLOSS_MAP.put(ConstantCodes.KINDCODE_A01_S2, "P001");
		
		//mantis： CLM0008 ，處理人員： David ，需求單編號： CLM0008   原因  新增可賠付新商品險種
		//33任意汽車第三人責任保險-單一保額  核定賠償 × 肇事責任比率 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("33", "P001*P006-P004");
		
		//mantis：CLM0196 ，處理人員：DP0713，需求單編號：CLM0196 新核心-任車新商品車體險0X
		//0X:營業用丙式車對車碰撞車體損失險 同(A3同07)- 核定賠償 ×（1－折舊率）- 殘值 ＝理賠金
		PRPLLOSS_MAP.put("0X", "P001*(1-P002)-P005");

		//mantis：CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC START
		//A1:車體損失險甲式 同01 - 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("A1", "P001*(1-P002)-P005-P004");
		//A2:車體損失險乙式 同05 - 核定賠償 ×（1－折舊率）- 殘值 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("A2", "P001*(1-P002)-P005-P004");
		//A3:車對車碰撞車體損失險 同07 - 核定賠償 ×（1－折舊率）- 殘值 ＝理賠金
		PRPLLOSS_MAP.put("A3", "P001*(1-P002)-P005");
		
		//B1:汽車竊盜損失險 同11 - 核定賠償 ×（1－折舊率） × （1－自負額比率）- 殘值＝ 理賠金
		PRPLLOSS_MAP.put("B1", "P001*(1-P002)*(1-P003)-P005");
		//C2:任意汽車第三人責任險財損責任險 同32 - 核定賠償 × 肇事責任比率 - 自負額 ＝理賠金
		PRPLLOSS_MAP.put("C2", "P001*P006-P004");
		//mantis：CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC END

		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
		// G2(32)：賠償金額 = 核定賠償 × 肇事責任比率 - 自負額
		PRPLLOSS_MAP.put("G2", "P001*P006-P004");
		// G3(07)：賠償金額 = 核定賠償 - 自負額
		PRPLLOSS_MAP.put("G3", "P001-P004");
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 END
	}
	/**
	 * 任意險人傷理算公式配置
	 */
	private static final Map<String, String> PRPLPERSONLOSS_MAP = new HashMap<String, String>();
	static {
		// mantis： CLM0135 ，處理人員： BK007 蘇哲 ，需求單編號：CLM0135.新核心-新增車險商品45
		// 45 強制汽車責任保險駕駛人傷害附加條款(限車主本人) 核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put(ConstantCodes.KINDCODE_A01_45, "P101");
		// 47 機車強制責任險附加駕駛人傷害險 核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put("47", "P101");
		// 48 汽車強制責任險附加駕駛人傷害險 核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put("48", "P101");
		// 31 任意汽車第三人責任險傷害責任險 31險種 = 93險種+94險種之理賠金
		PRPLPERSONLOSS_MAP.put("31", "P101-P103");
		// 93 汽車任意第三人責任險 核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("93", "P101-P103");
		// 94 汽車任意第三人責任險（每一死亡） 核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("94", "P101-P103");
		// 95 汽車任意第三人責任險（每一失能） 核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("95", "P101-P103");
		// 27 第三人責任險附加慰問金條款 核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put("27", "P101");
		// 49 第三人責任附加駕駛人傷害保險 核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put("49", "P101");
		// 50 第三人責任附加駕駛人傷害保險 核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put("50", "P101");
		// 51 第三人附加乘客體傷責任保險 核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("51", "P101-P103");
		// 52 第三人附加僱主責任保險 核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("52", "P101-P103");
		// 53 第三人附加旅客責任險 核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("53", "P101-P103");
		// 3A 任意汽車第三人責任險傷害責任險 核定賠償 - 強制險給付金額 - 自負額= 理賠金
		PRPLPERSONLOSS_MAP.put("3A", "P101-P103-P102");
		// 3C 任意汽車第三人責任險失能責任增額 核定賠償 - 強制險給付金額 - 自負額= 理賠金
		PRPLPERSONLOSS_MAP.put("3C", "P101-P103-P102");
		// 5A 汽車交通事故駕駛人傷害險 核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put("5A", "P101");
		// 5B 汽車客運業乘客責任險（每一人死殘） 核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("5B", "P101-P103");
		// mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -start
		// 5C 汽車交通事故駕駛人傷害保險傷害醫療給付附加條款-傷害醫療保險金的給付（實支實付型） 核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put(ConstantCodes.KINDCODE_A01_5C, "P101");
		// 5D 汽車交通事故駕駛人傷害保險傷害醫療給付附加條款-傷害醫療保險金的給付（日額型） 核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put(ConstantCodes.KINDCODE_A01_5D, "P101");
		// mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -end

		//第三人受酒類車禍補償附加條款  核定賠償 - 強制險給付金額＝理賠金(赔付人伤时同31)
		PRPLPERSONLOSS_MAP.put("24", "P101-P103");
		//第三人責任超額保險（不含酒償險）
		PRPLPERSONLOSS_MAP.put("E1", "P101");
		//第三人責任超額保險（含酒償險）
		PRPLPERSONLOSS_MAP.put("E2", "P101");

		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		//第三人死亡及失能超額責任保險  賠償金額=核定賠償金額
		PRPLPERSONLOSS_MAP.put("E9", "P101");
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
		
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816新增可賠付商品險種(E5,E6,E7) start
		//第三人責任保險超額責任-乙式（不含酒償險）賠償金額=核定賠償金額
		PRPLPERSONLOSS_MAP.put("E5", "P101");
		//第三人責任保險超額責任-乙式（含酒償險）賠償金額=核定賠償金額
		PRPLPERSONLOSS_MAP.put("E6", "P101");
		//第三人責任保險超額責任-乙式（營業車）賠償金額=核定賠償金額
		PRPLPERSONLOSS_MAP.put("E7", "P101");
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 end
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		//第三人責任附加超額險A式 賠償金額=核定賠償金額
		PRPLPERSONLOSS_MAP.put("E3", "P101");
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
		
		//26 供教練開車汽車第三人責任險條款  ＝31險種+32險種應賠金額
		PRPLPERSONLOSS_MAP.put("26", "P101-P103");
		// 3H 優良駕駛人第三人責任保險傷害責任險－假日 31險種 = 93險種+94險種之理賠金
		PRPLPERSONLOSS_MAP.put("3H", "P101-P103");
		// 3F 優良駕駛人第三人責任保險傷害責任險－平日險種 = 93險種+94險種之理賠金
		PRPLPERSONLOSS_MAP.put("3F", "P101-P103");
		// 3D 優良駕駛人第三人責任保險傷害責任險－全時險種 = 93險種+94險種之理賠金
		PRPLPERSONLOSS_MAP.put("3D", "P101-P103");
		
		// 汽車竊盜損失保險新車全損免折舊附加條款    核定賠償  ＝ 理賠金
		//mantis： CLM0108，處理人員：BK007 蘇哲，需求單編號：CLM0108 新核心-車3N險修復
		PRPLPERSONLOSS_MAP.put("3N", "P101");
		
		//21强制险   核定賠償 = 理賠金
		PRPLPERSONLOSS_MAP.put("21", "P101");
		//54機車乘客體傷責任保險    核定賠償  ＝ 理賠金
		PRPLPERSONLOSS_MAP.put("54", "P101");
		
		//mantis： CLM0008 ，處理人員： David ，需求單編號： CLM0008   原因  新增可賠付新商品險種
		//33任意汽車第三人責任保險-單一保額  核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("33", "P101-P103");
		
		//mantis：CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC START
		//C1:任意汽車第三人責任險傷害責任險  同31 - 31險種 = 93險種+94險種之理賠金<--抄31的，但是這說明跟公式對不上
		PRPLPERSONLOSS_MAP.put("C1", "P101-P103");
		//mantis：CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC END
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4 START
		// D1(51) 汽車任意第三人責任險（每一失能） 核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("D1", "P101-P103");
		// F1(53) 第三人附加旅客責任險 核定賠償 - 強制險給付金額 = 理賠金
		PRPLPERSONLOSS_MAP.put("F1", "P101-P103");
		// C3(31)任意汽車第三人責任險傷害責任險 31險種 = 93險種+94險種之理賠金(DOC:93險種 + 95險種 + 94險種之理賠金 - 自負額)
		PRPLPERSONLOSS_MAP.put("C3", "P101-P103-P102");
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4 END
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
		// G1(31)：賠償金額 = (93險種 + 95險種 + 94險種)之理賠金 - 自負額
		PRPLPERSONLOSS_MAP.put("G1", "P101-P103-P102");
		// G4(53)：賠償金額 = 核定賠償 - 強制險給付金額
		PRPLPERSONLOSS_MAP.put("G4", "P101-P103");
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 END
	}

}
