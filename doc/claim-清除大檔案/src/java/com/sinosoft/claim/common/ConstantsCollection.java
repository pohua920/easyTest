package com.sinosoft.claim.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 理算 差异化需求部分 下拉框内容集
 * @author 中科软
 * 增加LinkedHashMap<String, String>(7)的默认初始容量，增加选项的时候，需要增加初始化的长度
 */
public class ConstantsCollection {

	/** 任意险 理算 赔付代号 */
	public static final Map<String, String> payCodeList = new LinkedHashMap<String, String>(7);
	static {
		payCodeList.put("1", "一次賠付結案");
		payCodeList.put("2", "免賠結案");
		payCodeList.put("3", "部份賠付");
		payCodeList.put("4", "最後一次賠付");
		payCodeList.put("5", "代位求償/殘餘物處理攤回");
		payCodeList.put("6", "已付賠款調整");
	}
	/** 强制险 理算 赔付代号 */
	public static final Map<String, String> qzPayCodeList = new LinkedHashMap<String, String>(9);
	static {
		qzPayCodeList.put("1", "一次賠付結案");
		qzPayCodeList.put("2", "免賠結案");
		qzPayCodeList.put("3", "部份賠付");
		qzPayCodeList.put("4", "最後一次賠付");
		qzPayCodeList.put("5", "代位求償/殘餘物處理攤回");
		qzPayCodeList.put("6", "已付賠款調整");
		qzPayCodeList.put("7", "處理多輛車肇事之攤賠，要求攤賠公司用");
		qzPayCodeList.put("8", "處理多輛車肇事之攤賠，被要求攤賠公司用");
		// mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -start
		qzPayCodeList.put("A", "肇責調整賠案，要求攤賠公司用");
		qzPayCodeList.put("B", "肇責調整賠案，被要求攤賠公司用");
		// mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -end
	}
	/** 理算 全損/分損代號 */
	public static final Map<String, String> lossTypeList = new LinkedHashMap<String, String>(3);
	static {
		lossTypeList.put("1", "全損");
		lossTypeList.put("2", "分損");
	}

	/** 理算 本車肇事責任 */
	public static final Map<String, String> selfIndemnityDutyNameList = new LinkedHashMap<String, String>(7);
	static {
		selfIndemnityDutyNameList.put("1", "全責");
		selfIndemnityDutyNameList.put("2", "主因");
		selfIndemnityDutyNameList.put("3", "同為肇事因素");
		selfIndemnityDutyNameList.put("4", "次因");
		selfIndemnityDutyNameList.put("5", "無責");
		selfIndemnityDutyNameList.put("6", "其他");
	}

	/** 理算 肇事类型 */
	public static final Map<String, String> accidentTypeList = new LinkedHashMap<String, String>(4);
	static {
		accidentTypeList.put("1", "有肇責，計次");
		accidentTypeList.put("2", "無肇責，不計次");
		accidentTypeList.put("3", "有肇責，不計次");
		// CLM0042 ，處理人員：BK007 蘇哲，需求單編號：CLM0042「任意車險查詢平台」調整理賠資料傳輸-肇責未釐清，不計次
		accidentTypeList.put("4", "肇責未釐清，不計次");
	}

	/** 理算 肇責百分比 */
	public static final Map<Double, String> indemnityDutyList = new LinkedHashMap<Double, String>(12);
	static {
		indemnityDutyList.put(10d, "10%");
		indemnityDutyList.put(20d, "20%");
		indemnityDutyList.put(30d, "30%");
		indemnityDutyList.put(40d, "40%");
		indemnityDutyList.put(50d, "50%");
		indemnityDutyList.put(60d, "60%");
		indemnityDutyList.put(70d, "70%");
		indemnityDutyList.put(80d, "80%");
		indemnityDutyList.put(90d, "90%");
		indemnityDutyList.put(100d, "100%");
		indemnityDutyList.put(0d, "0%");
	}

	/** 追偿 給付追償情況 */
	public static final Map<String, String> compelPaySituationList = new LinkedHashMap<String, String>(5);
	static {
		compelPaySituationList.put("", "");
		compelPaySituationList.put("3", "追償金已追償完畢結案");
		compelPaySituationList.put("4", "本次追償為分次追償");
		compelPaySituationList.put("6", "放棄追償");
		compelPaySituationList.put("7", "費用");
	}

	/** 理算 給付追償情況 */
	public static final Map<String, String> payOfCompList = new LinkedHashMap<String, String>(7);
	static {
		payOfCompList.put("1", "賠款已全數賠付給所有受害人結案");
		payOfCompList.put("2", "本次賠款為分次賠付給受害人");
		payOfCompList.put("3", "追償金已追償完畢結案");
		payOfCompList.put("4", "本次追償為分次追償");
		payOfCompList.put("5", "免賠結案");
		payOfCompList.put("6", "放棄追償");
	}

	/**
	 * 檢察署
	 */
	public static final Map<String, String> prosecutorsOfficeList = new LinkedHashMap<String, String>(40);
	static {
		prosecutorsOfficeList.put("", "");
		prosecutorsOfficeList.put("1", "1         最高法院檢察署");
		prosecutorsOfficeList.put("2", "2         台灣高等法院檢察署");
		prosecutorsOfficeList.put("3", "3         台灣高等法院台中分院檢察署");
		prosecutorsOfficeList.put("4", "4         台灣高等法院台南分院檢察署");
		prosecutorsOfficeList.put("5", "5         台灣高等法院高雄分院檢察署");
		prosecutorsOfficeList.put("6", "6         台灣高等法院花蓮分院檢察署");
		prosecutorsOfficeList.put("7", "7         台灣高等法院金門分院檢察署");
		prosecutorsOfficeList.put("8", "8         台灣台北地方法院檢察署");
		prosecutorsOfficeList.put("9", "9         台灣士林地方法院檢察署");
		prosecutorsOfficeList.put("10", "10        台灣板橋地方法院檢察署");
		prosecutorsOfficeList.put("11", "11        台灣桃園地方法院檢察署");
		prosecutorsOfficeList.put("12", "12        台灣新竹地方法院檢察署");
		prosecutorsOfficeList.put("13", "13        台灣苗栗地方法院檢察署");
		prosecutorsOfficeList.put("14", "14        台灣台中地方法院檢察署");
		prosecutorsOfficeList.put("15", "15        台灣彰化地方法院檢察署");
		prosecutorsOfficeList.put("16", "16        台灣南投地方法院檢察署");
		prosecutorsOfficeList.put("17", "17        台灣雲林地方法院檢察署");
		prosecutorsOfficeList.put("18", "18        台灣嘉義地方法院檢察署");
		prosecutorsOfficeList.put("19", "19        台灣台南地方法院檢察署");
		prosecutorsOfficeList.put("20", "20        台灣高雄地方法院檢察署");
		prosecutorsOfficeList.put("21", "21        台灣屏東地方法院檢察署");
		prosecutorsOfficeList.put("22", "22        台灣台東地方法院檢察署");
		prosecutorsOfficeList.put("23", "23        台灣花蓮地方法院檢察署");
		prosecutorsOfficeList.put("24", "24        台灣宜蘭地方法院檢察署");
		prosecutorsOfficeList.put("25", "25        台灣基隆地方法院檢察署");
		prosecutorsOfficeList.put("26", "26        台灣澎湖地方法院檢察署");
		prosecutorsOfficeList.put("27", "27        福建金門地方法院檢察署");
		prosecutorsOfficeList.put("28", "28        福建連江地方法院檢察署");
		prosecutorsOfficeList.put("29", "29        國防部最高軍事法院檢察署");
		prosecutorsOfficeList.put("30", "30        國防部高等軍事法院檢察署");
		prosecutorsOfficeList.put("31", "31        國防部高等軍事法院高雄分院檢察署");
		prosecutorsOfficeList.put("32", "32        國防部北部地方軍事法院檢察署");
		prosecutorsOfficeList.put("33", "33        國防部南部地方軍事法院檢察署 中部檢察官辦公室");
		prosecutorsOfficeList.put("34", "34        國防部南部地方軍事法院檢察署");
		prosecutorsOfficeList.put("35", "35        國防部東部地方軍事法院檢察署");
		prosecutorsOfficeList.put("36", "36        國防部南部地方軍事法院澎湖軍事 檢察官辦公室");
		prosecutorsOfficeList.put("37", "37        國防部南部地方軍事法院金門軍事 檢察官辦公室");
		prosecutorsOfficeList.put("38", "38        國防部北部地方軍事法院馬祖軍事 檢察官辦公室");
	}
	/** 傷亡情形 */
	public static final Map<String, String> casualtiesList = new LinkedHashMap<String, String>(4);
	static {
		casualtiesList.put("1", "1.醫療");
		casualtiesList.put("2", "2.失能");
		casualtiesList.put("3", "3.死亡");
	}
	/**
	 * 强制险 - 人伤费用类型
	 */
	public static final Map<String, String> detailCodeList = new LinkedHashMap<String, String>(13);
	static {
		detailCodeList.put("A01", "A01  急救費用");
		detailCodeList.put("A021", "A021 自行負擔之病房費差額");
		detailCodeList.put("A022", "A022 膳食費");
		detailCodeList.put("A023", "A023 自行負擔之義肢器材及裝置費");
		detailCodeList.put("A024", "A024 義齒器材及裝置費");
		detailCodeList.put("A025", "A025 義眼器材及裝置費");
		detailCodeList.put("A026", "A026 其他非全民健保法所規定給付範圍之醫療材料(含輔助器材費用)及非具積極治療性之裝具");
		detailCodeList.put("A029", "A029 其他診療費用");
		detailCodeList.put("A03", "A03  護送費用");
		detailCodeList.put("A04", "A04  看護費用");
		detailCodeList.put("B00", "B00  死亡給付");
		detailCodeList.put("C00", "C00  失能給付加總");

	}
	/**
	 * 任意险 - 人伤給付類別代號
	 */
	public static final Map<String, String> payTypeCodeList = new LinkedHashMap<String, String>(5);
	static {
		payTypeCodeList.put("93", "93 醫療費用");
		payTypeCodeList.put("94", "94 死亡給付");
		payTypeCodeList.put("95", "95 失能給付");
		payTypeCodeList.put("99", "99 全部給付");
	}
	/**
	 * 伤残等级
	 */
	public static final Map<String, String> injuryGradeList = new LinkedHashMap<String, String>(17);
	static {
		injuryGradeList.put("", "");
		injuryGradeList.put("C01", "C01 一級失能");
		injuryGradeList.put("C02", "C02 二級失能");
		injuryGradeList.put("C03", "C03 三級失能");
		injuryGradeList.put("C04", "C04 四級失能");
		injuryGradeList.put("C05", "C05 五級失能");
		injuryGradeList.put("C06", "C06 六級失能");
		injuryGradeList.put("C07", "C07 七級失能");
		injuryGradeList.put("C08", "C08 八級失能");
		injuryGradeList.put("C09", "C09 九級失能");
		injuryGradeList.put("C10", "C10 十級失能");
		injuryGradeList.put("C11", "C11 十一級失能");
		injuryGradeList.put("C12", "C12 十二級失能");
		injuryGradeList.put("C13", "C13 十三級失能");
		injuryGradeList.put("C14", "C14 十四級失能");
		injuryGradeList.put("C15", "C15 十五級失能");

	}
	/** 理算 受害人身份 */
	public static final Map<String, String> identityOfInjuredPersonList = new LinkedHashMap<String, String>(5);
	static {
		identityOfInjuredPersonList.put("1", "自然人本國籍");
		identityOfInjuredPersonList.put("2", "自然人外國籍");
		identityOfInjuredPersonList.put("7", "自然人外國籍無居留證號或居留證號不符檢核邏輯");
		identityOfInjuredPersonList.put("8", "自然人本國籍身分證號不符檢核邏輯");
	}

	/** 理算 出事當時乘坐狀況 */
	public static final Map<String, String> rideSituationList = new LinkedHashMap<String, String>(6);
	static {
		rideSituationList.put("1", "本車上乘客");
		rideSituationList.put("3", "車外人員");
		rideSituationList.put("4", "對方車上乘客");
		rideSituationList.put("5", "對方車上駕駛");
		rideSituationList.put("6", "本車上駕駛");
	}

	/** 理算 受害人健保就醫 */
	public static final Map<String, String> medicalCodeList = new LinkedHashMap<String, String>(3);
	static {
		medicalCodeList.put("Y", "曾以健保就醫身份就醫");
		medicalCodeList.put("N", "未以健保就醫身份就醫");
	}

	/** 报案基本信息 備案人與被保險人關係 */
	public static final Map<String, String> relationTypeList = new LinkedHashMap<String, String>(8);
	static {
		relationTypeList.put("1", "本人");
		relationTypeList.put("2", "親屬");
		relationTypeList.put("3", "朋友");
		relationTypeList.put("7", "員工");
		relationTypeList.put("4", "業務員");
		relationTypeList.put("5", "代理人");
		relationTypeList.put("6", "修理廠");
		relationTypeList.put("9", "其他");
	}

	/** 報案基本信息 互碰自賠標志 */
	public static final Map<String, String> payselfFlagList = new LinkedHashMap<String, String>(3);
	static {
		payselfFlagList.put("0", "非互碰自賠");
		payselfFlagList.put("1", "是互碰自賠");
	}

	/** 報案基本信息 是否需要現場處理 */
	public static final Map<String, String> scheduleTypeList = new LinkedHashMap<String, String>(3);
	static {
		scheduleTypeList.put("ALLS", "需要");
		scheduleTypeList.put("NOCK", "不需要");
	}

	/** 報案受損信息 駕駛員信息 證件類型 */
	public static final Map<String, String> drivingCarTypeList = new LinkedHashMap<String, String>(5);
	static {
		drivingCarTypeList.put("01", "身份證字號");
		drivingCarTypeList.put("05", "駕駛證號碼");
		drivingCarTypeList.put("02", "居留證號碼");
		drivingCarTypeList.put("03", "護照號碼");
		drivingCarTypeList.put("99", "其他");
	}

	/** 理赔类型的列表 */
	public static final Map<String, String> claimFlagList = new LinkedHashMap<String, String>(3);
	static {
		claimFlagList.put("A", "自辦");
		claimFlagList.put("B", "委外");
	}
	/** 立案 估损金额调整 */
	public static final Map<String, String> lossLossFeeTypeList = new LinkedHashMap<String, String>(3);
	static {
		lossLossFeeTypeList.put("P", "賠款");
		lossLossFeeTypeList.put("Z", "費用");
	}
	/** 立案 範圍 */
	public static final Map<String, String> lossFeeCategoryList = new LinkedHashMap<String, String>(7);
	static {
		lossFeeCategoryList.put("C", "車損");
		lossFeeCategoryList.put("G", "物損");
		lossFeeCategoryList.put("M", "醫療");
		lossFeeCategoryList.put("H", "失能");
		lossFeeCategoryList.put("D", "死亡");
		lossFeeCategoryList.put("O", "其他");
	}
	/** 强制险立案 範圍 */
	public static final Map<String, String> lossFeeCategoryListBZ = new LinkedHashMap<String, String>(5);
	static {
		lossFeeCategoryListBZ.put("M", "醫療");
		lossFeeCategoryListBZ.put("H", "失能");
		lossFeeCategoryListBZ.put("D", "死亡");
		lossFeeCategoryListBZ.put("O", "其他");
	}
	/** 理算 费用资讯 费用名称 */
	public static final Map<String, String> chargeCodeList = new LinkedHashMap<String, String>(10);
	static {
		chargeCodeList.put("A", "其它費用");
		chargeCodeList.put("B", "稅金");
		chargeCodeList.put("S", "律師費");
		chargeCodeList.put("T", "車資（付理賠）");
		chargeCodeList.put("U", "車資（強制險）");
		chargeCodeList.put("V", "車資（技術員）");
		chargeCodeList.put("W", "調查費");
		chargeCodeList.put("X", "訴訟費");
		chargeCodeList.put("Y", "代墊費用");
	}

	/** 理算 赔付对象 費用類型 */
	public static final Map<String, String> paymentKindList = new LinkedHashMap<String, String>(7);
	static {
		paymentKindList.put("1", "修車廠");
		paymentKindList.put("2", "材料商");
		paymentKindList.put("3", "公司行號");
		paymentKindList.put("4", "個人");
		paymentKindList.put("5", "公證公司");
		// paymentKindList.put("6", "健保局");
		paymentKindList.put("7", "同業");
	}
	/** 理算 水险赔付对象 費用類型 */
	public static final Map<String, String> shipPaymentKindList = new LinkedHashMap<String, String>(23);
	static {
		shipPaymentKindList.put("1","修車廠");
		shipPaymentKindList.put("2","材料行");
		shipPaymentKindList.put("3","公司行號");
		shipPaymentKindList.put("4","個人");
		shipPaymentKindList.put("5","公證公司");
		shipPaymentKindList.put("6","健保局");
		shipPaymentKindList.put("7","同業");
		shipPaymentKindList.put("9","其他");
		shipPaymentKindList.put("A","其他費用");
		shipPaymentKindList.put("B","稅金");
		shipPaymentKindList.put("S","律師費");
		shipPaymentKindList.put("T","車資(付理賠)");
		shipPaymentKindList.put("U","車資(強制險)");
		shipPaymentKindList.put("V","車資(技術員)");
		shipPaymentKindList.put("W","調查費");
		shipPaymentKindList.put("X","訴訟費");
		shipPaymentKindList.put("Y","代墊費用");
		shipPaymentKindList.put("Z","SettlingFee(估理費用)");
		shipPaymentKindList.put("C","G.A.contribution(共損分攤)");
		shipPaymentKindList.put("D","延遲利息");
		shipPaymentKindList.put("E","HandlingFee(處理費用)");
		shipPaymentKindList.put("F","AwardContribution(拖救分攤)");
	}

	/** 查勘 查勘类型 */
	public static final Map<String, String> checkTypeList = new LinkedHashMap<String, String>(3);
	static {
		checkTypeList.put("L", "查勘");
		checkTypeList.put("D", "代查勘");
	}
	/** 水险查勘 查勘类型 */
	public static final Map<String, String> shipCheckTypeList = new LinkedHashMap<String, String>(3);
	static {
		shipCheckTypeList.put("L", "自行查勘");
		shipCheckTypeList.put("D", "委外查勘");
		shipCheckTypeList.put("N", "不查勘");
	}
	/** 查勘 傷亡類型 */
	public static final Map<String, String> traceFlagList = new LinkedHashMap<String, String>(3);
	static {
		traceFlagList.put("1", "傷殘");
		traceFlagList.put("2", "死亡");
	}
	/** 查勘 是否自行就醫 */
	public static final Map<String, String> motionFlagList = new LinkedHashMap<String, String>(3);
	static {
		motionFlagList.put("0", "否");
		motionFlagList.put("1", "是");
	}
	/** 定損 修理廠類型 */
	public static final Map<String, String> feeRepairFactoryCodeList = new LinkedHashMap<String, String>(6);
	static {
		feeRepairFactoryCodeList.put("", "");
		feeRepairFactoryCodeList.put("03", "車商保代廠");
		feeRepairFactoryCodeList.put("02", "原廠");
		feeRepairFactoryCodeList.put("01", "簽約廠");
		feeRepairFactoryCodeList.put("04", "一般廠");
	}
	/** 定損 修理廠類型 */
	public static final Map<String, String> ifRemainList = new LinkedHashMap<String, String>(3);
	static {
		ifRemainList.put("1", "是");
		ifRemainList.put("0", "否");
	}
	/** 定損 本車是否受損 */
	public static final Map<String, String> lossFlagList = new LinkedHashMap<String, String>(4);
	static {
		lossFlagList.put("", "");
		lossFlagList.put("1", "是");
		lossFlagList.put("0", "否");
	}
	/** 人傷定損 傷勢程度 */
	public static final Map<String, String> woundGradeList = new LinkedHashMap<String, String>(4);
	static {
		woundGradeList.put("1", "傷");
		woundGradeList.put("2", "殘");
		woundGradeList.put("3", "死亡");
	}
	/** 人傷定損 是否需要轉院治療 */
	public static final Map<String, String> changeHospitalList = new LinkedHashMap<String, String>(3);
	static {
		changeHospitalList.put("1", "是");
		changeHospitalList.put("0", "否");
	}
	/** 調度 案件狀態 */
	public static final Map<String, String> exigenceGreeList = new LinkedHashMap<String, String>(3);
	static {
		exigenceGreeList.put("1", "一般");
		exigenceGreeList.put("0", "緊急");
	}
	/** 理算 支付類別 */
	public static final Map<String, String> payObjectTypeList = new LinkedHashMap<String, String>(3);
	static {
		payObjectTypeList.put("A", "自辦");
		payObjectTypeList.put("B", "委外");
	}
	/** 立案 受损信息 駕駛人區別 */
	public static final Map<String, String> driverDistrictList = new LinkedHashMap<String, String>(4);
	static {
		driverDistrictList.put("1", "己車己開");
		driverDistrictList.put("2", "己車借他人開");
		driverDistrictList.put("4", "法人車駕駛（被保險人資料欄身分別勾稽）");
	}
	/** 本車駕駛員与被保險人關係 */
	public static final Map<String, String> thirdPartyRelationshipList = new LinkedHashMap<String, String>(6);
	static {
		//1. 本人 2. 親友 3. 員工 4. 租車  5. 偷竊 6. 其他
		/*
		   mantis： CLM0001，處理人員：David，需求單編號：CLM0001 --- start
		        處理過程：新核心資訊系統報送格式變更
		 */
//		thirdPartyRelationshipList.put("1", "被保險人本人");
//		thirdPartyRelationshipList.put("2", "被保險人親友");
//		thirdPartyRelationshipList.put("3", "被保險人員工");
//		thirdPartyRelationshipList.put("4", "租用被保險車輛");
//		thirdPartyRelationshipList.put("5", "偷          竊");
//		thirdPartyRelationshipList.put("6", "其          他");
		
		thirdPartyRelationshipList.put("1", "被保險人本人");
		thirdPartyRelationshipList.put("A", "自然人車配偶");
		thirdPartyRelationshipList.put("B", "自然人車直系親屬");
		thirdPartyRelationshipList.put("C", "自然人車兄弟姊妹");
		thirdPartyRelationshipList.put("D", "自然人車其他");
		thirdPartyRelationshipList.put("E", "法人車負責人");
		thirdPartyRelationshipList.put("F", "法人車負責人家屬");
		thirdPartyRelationshipList.put("G", "法人車受僱者");
		thirdPartyRelationshipList.put("4", "租用被保險車輛");
		thirdPartyRelationshipList.put("5", "其          他");
		
		
		/* mantis： CLM0001，處理人員：David，需求單編號：CLM0001 --- end */
		
	}
	/** 身份信息列表 */
	public static final Map<String, String> identityList = new LinkedHashMap<String, String>(7);
	static {
		identityList.put("1", "1-自然人本國籍");
		identityList.put("2", "2-自然人外國籍");
		identityList.put("3", "3-法人、非法人團體或機關");
		identityList.put("7", "7-自然人外國籍居留證號不符檢核邏輯");
		identityList.put("8", "8-自然人本國籍居留證號不符檢核邏輯");
		identityList.put("9", "9-法人、非法人團體或機關統一編號不符檢核邏輯");
	}
	/** 給付追償情況 */
	public static final Map<String, String> paySituationList = new LinkedHashMap<String, String>(6);
	static {
		paySituationList.put("1", "賠款已全數賠付給所有受害人結案");
		paySituationList.put("2", "本次賠款為分次賠付給受害人");
		// paySituationList.put("3", "追償金已追償完畢結案");
		// paySituationList.put("4", "本次追償為分次追償");
		paySituationList.put("5", "免賠結案");
		// paySituationList.put("6", "放棄追償");
	}
	/** 健保局追償狀況 */
	public static final Map<String, String> chasingLossesStatusList = new LinkedHashMap<String, String>(5);
	static {
		chasingLossesStatusList.put("1", "本賠案無健保追償情形");
		chasingLossesStatusList.put("2", "本賠案尚待健保追償");
		chasingLossesStatusList.put("3", "健保全數付清");
		chasingLossesStatusList.put("4", "本次健保追償為分次追償");
	}
	
	/** 死亡場所 */
	public static final Map<String,String> deathPlaceList = new LinkedHashMap<String,String>(7);
	static{
		deathPlaceList.put("","");
		deathPlaceList.put("01","01－醫院");
		deathPlaceList.put("02","02－診所");
		deathPlaceList.put("03","03－助產所");
		deathPlaceList.put("04","04－自宅");
		deathPlaceList.put("05","05－其它");
	}
	public static final Map<String,String> deathMannerList = new LinkedHashMap<String,String>(8);
	static{
		deathMannerList.put("01","01－病死或自然死");
		deathMannerList.put("02","02－意外（死）");
		deathMannerList.put("03","03－自殺");
		deathMannerList.put("04","04－他殺");
		deathMannerList.put("05","05－不詳");
		deathMannerList.put("06","06－未勾選");
		deathMannerList.put("07","07－其它");
	}

	/** 主標的車輛損失 */
	public static final List<String> MainCarLoss = new ArrayList<String>(35);
	static {
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_18);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0I);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0H);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_08);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_09);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_X1);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0A);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0B);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0C);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0D);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0E);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0F);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0G);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_01);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_02);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_03);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_05);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_07);
		//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0Y);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_1A);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_11);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_12);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_14);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_16);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_17);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_22);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_81);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_82);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0J);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_1X);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_1Y);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_1Z);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_Y1);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_Y2);
		//mantis： CLM0134，處理人員：DP0706，需求單編號：CLM0134新增拖吊險商品Y3
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_Y3);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0K);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0L);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0M);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_1B);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_1C);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0N);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0P);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0Q);
		MainCarLoss.add("0S");
		/** 需求變更131 */
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_S1);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_S2);
		
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0S);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0T);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_1D);
		
		//mantis： CLM0008 ，處理人員： David ，需求單編號： CLM0008   原因  新增可賠付新商品險種-車損
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_33);
		//mantis： CLM0059 ，處理人員：BK007 蘇哲，需求單編號：CLM0059 短期車險
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_9A);
		//mantis：CLM0196 ，處理人員：DP0713，需求單編號：CLM0196 新核心-任車新商品車體險0X
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_0X);
		//mantis：CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC START
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_A1);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_A2);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_A3);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_B1);
		MainCarLoss.add(ConstantCodes.KINDCODE_A01_C2);
		//mantis：CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC END
		
		
	}
	/** 被保險人/駕駛人 */
	public static final List<String> InsAnddriver = new ArrayList<String>(6);
	static {
		InsAnddriver.add(ConstantCodes.KINDCODE_A01_47);
		InsAnddriver.add(ConstantCodes.KINDCODE_A01_48);
		InsAnddriver.add(ConstantCodes.KINDCODE_A01_49);
		InsAnddriver.add(ConstantCodes.KINDCODE_A01_50);
		InsAnddriver.add(ConstantCodes.KINDCODE_A01_5A);
		// mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -start
		InsAnddriver.add(ConstantCodes.KINDCODE_A01_5C);
		InsAnddriver.add(ConstantCodes.KINDCODE_A01_5D);
		// mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -end
		// mantis： CLM0135 ，處理人員： BK007 蘇哲 ，需求單編號：CLM0135.新核心-新增車險商品45
		InsAnddriver.add(ConstantCodes.KINDCODE_A01_45);
	}
	/** 主车人 伤 */
	public static final List<String> MainPersonLoss = new ArrayList<String>(16);
	static {
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_93);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_94);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_95);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_E1);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_E2);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_24);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_26);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_27);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_3A);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_3C);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_31);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_52);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_53);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_51);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_5B);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_3H);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_3F);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_3D);
		
		//mantis： CLM0008 ，處理人員： David ，需求單編號： CLM0008   原因  新增可賠付新商品險種-財損
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_33);
		
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816新增可賠付商品險種(E5,E6,E7) start
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_E5);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_E6);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_E7);
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 end
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_E3);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_E9);
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
		
		//mantis： CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC 
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_C1);
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4(財:D1F1C3 /人:C4) START
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_D1);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_F1);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_C3);
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4 END
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_G1);
		MainPersonLoss.add(ConstantCodes.KINDCODE_A01_G4);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 END
	}
	/** 主车财损 */
	public static final List<String> MainPropLoss = new ArrayList<String>(11);
	static {
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_E1);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_E2);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_24);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_26);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_3B);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_32);
		//mantis： CLM0008 ，處理人員： David ，需求單編號： CLM0008   原因  新增可賠付新商品險種-財損
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_33);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_71);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_72);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_Y1);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_Y2);
		//mantis： CLM0134，處理人員：DP0706，需求單編號：CLM0134新增拖吊險商品Y3
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_Y3);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_3I);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_3G);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_3E);
		MainPropLoss.add("0S");
		
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816新增可賠付商品險種(E5,E6,E7) start
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_E5);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_E6);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_E7);
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 end
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_E3);//第三人責任附加超額險A式
		//mantis：CLM0196 ，處理人員：DP0713，需求單編號：CLM0196 新核心-任車新商品車體險0X
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_0X);
		//mantis： CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC START
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_A1);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_A2);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_A3);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_B1);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_C2);
		//mantis： CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC END
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4(財:D1F1C3 /人:C4) 
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_C4);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_G2);
		MainPropLoss.add(ConstantCodes.KINDCODE_A01_G3);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 END
	}
	/** 三者車 */
	public static final List<String> ThirdCarLoss = new ArrayList<String>(11);
	static {
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_0H);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_09);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_E1);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_E2);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_24);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_26);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_3B);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_32);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_Y1);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_Y2);
		//mantis： CLM0134，處理人員：DP0706，需求單編號：CLM0134新增拖吊險商品Y3
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_Y3);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_3I);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_3G);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_3E);
		ThirdCarLoss.add("0S");		
		
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816新增可賠付商品險種(E5,E6,E7) start
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_E5);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_E6);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_E7);
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 end
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_E3);//第三人責任附加超額險A式
		//mantis： CLM0059 ，處理人員：BK007 蘇哲，需求單編號：CLM0059 短期車險
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_9A);
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4(財:D1F1C3 /人:C4) 
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_C4);
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4(財:D1F1C3 /人:C4) 
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_G2);
		ThirdCarLoss.add(ConstantCodes.KINDCODE_A01_G3);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 END
	}
	/** 三者物 */
	public static final List<String> ThirdPropLoss = new ArrayList<String>(11);
	static {
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_E1);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_E2);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_24);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_26);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_3B);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_32);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_71);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_72);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_Y1);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_Y2);
		//mantis： CLM0134，處理人員：DP0706，需求單編號：CLM0134新增拖吊險商品Y3
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_Y3);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_3I);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_3G);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_3E);
		ThirdPropLoss.add("0S");
		
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816新增可賠付商品險種(E5,E6,E7) start
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_E5);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_E6);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_E7);
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 end
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_E3);
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4(財:D1F1C3 /人:C4) 
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_C4);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_G2);
		ThirdPropLoss.add(ConstantCodes.KINDCODE_A01_G3);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 END
	}
	/** 三者人 */
	public static final List<String> ThirdPersonLoss = new ArrayList<String>(22);
	static {
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_93);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_94);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_95);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_47);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_48);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_E1);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_E2);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_24);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_26);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_27);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_3A);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_3C);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_31);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_49);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_50);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_51);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_52);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_53);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_5A);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_5B);
		// mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -start
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_5C);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_5D);
		// mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -end
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_B01_BZ);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_3H);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_3F);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_3D);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_3N);
		
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816新增可賠付商品險種(E5,E6,E7) start
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_E5);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_E6);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_E7);
		//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 end
		
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_E3);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_E9);
		//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END

		//mantis： CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_C1);

		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4(財:D1F1C3 /人:C4) START
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_C3);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_G1);
		ThirdPersonLoss.add(ConstantCodes.KINDCODE_A01_G4);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 END
	}
	/** 可赔人伤部分 InsAnddriver、MainPersonLoss、ThirdPersonLoss之并集 */
	public static final List<String> KindCodeForPerson = new ArrayList<String>(6);
	static {
		KindCodeForPerson.addAll(InsAnddriver);
		KindCodeForPerson.removeAll(MainPersonLoss);
		KindCodeForPerson.addAll(MainPersonLoss);
		KindCodeForPerson.removeAll(ThirdPersonLoss);
		KindCodeForPerson.addAll(ThirdPersonLoss);
		/** 可赔付人伤增加54 */
		KindCodeForPerson.add(ConstantCodes.KINDCODE_A01_54);
		}

	/** 可赔财损部分 MainPropLoss、ThirdPropLoss 之并集 */
	public static final List<String> KindCodeForProp = new ArrayList<String>(4);
	static {
		KindCodeForProp.addAll(MainPropLoss);
		KindCodeForProp.removeAll(ThirdPropLoss);
		KindCodeForProp.addAll(ThirdPropLoss);
	}
	/** 可赔车损部分 MainCarLoss、ThirdCarLoss 之并集 */
	public static final List<String> KindCodeForCar = new ArrayList<String>(4);
	static {
		KindCodeForCar.addAll(MainCarLoss);
		KindCodeForCar.removeAll(ThirdCarLoss);
		KindCodeForCar.addAll(ThirdCarLoss);
	}
	/** 可赔费用部分 目前为车、财、人之合集之并集 */
	public static final List<String> KindCodeForCharge = new ArrayList<String>(6);
	static {
		KindCodeForCharge.addAll(KindCodeForPerson);
		KindCodeForCharge.removeAll(KindCodeForCar);
		KindCodeForCharge.addAll(KindCodeForCar);
		KindCodeForCharge.removeAll(KindCodeForProp);
		KindCodeForCharge.addAll(KindCodeForProp);
	}

	/** 所有险别 目前为车、财、人之合集之并集 */
	public static final List<String> KindCodeForAll = new ArrayList<String>(6);
	static {
		KindCodeForAll.addAll(KindCodeForPerson);
		KindCodeForAll.removeAll(KindCodeForCar);
		KindCodeForAll.addAll(KindCodeForCar);
		KindCodeForAll.removeAll(KindCodeForProp);
		KindCodeForAll.addAll(KindCodeForProp);
	}

	/** 承載單位 */
	public static final Map<String, String> partyCarryingUnitList = new LinkedHashMap<String, String>(3);
	static {
		partyCarryingUnitList.put("P", "載客人數");
		partyCarryingUnitList.put("T", "總重噸數");
	}

	public static final Map<String, String> oraErrorMessage = new LinkedHashMap<String, String>(15);
	static {
		oraErrorMessage.put("ORA-00907", "數據庫查詢語句表達式錯誤");
		oraErrorMessage.put("ORA-00933", "數據庫查詢語句表達式錯誤");
		oraErrorMessage.put("ORA-00936", "數據庫查詢語句表達式錯誤");
		oraErrorMessage.put("ORA-01841", "數據庫查詢語句日期格式錯誤");
		oraErrorMessage.put("ORA-01861", "數據庫查詢語句日期格式錯誤");
		oraErrorMessage.put("ORA-00001", "要插入的數據違反了主鍵約束");
		oraErrorMessage.put("ORA-01841", "數據庫查詢語句日期格式錯誤");
		oraErrorMessage.put("ORA-01861", "數據庫查詢語句日期格式錯誤");
		oraErrorMessage.put("ORA-00933", "數據庫查詢語句表達式錯誤");
		oraErrorMessage.put("ORA-00907", "數據庫查詢語句表達式錯誤");
		oraErrorMessage.put("ORA-00936", "數據庫查詢語句表達式錯誤");
		oraErrorMessage.put("ORA-00904", "要查詢的字段在數據庫表中不存在");
		oraErrorMessage.put("ORA-01401", "插入的數據超過了數據庫字段的長度限制");
		oraErrorMessage.put("ORA-00054", "要操作的數據已經被其他用戶占用");
	}

	/** 不受限额控制的险别 */
	public static final List<String> KindCodeForNoLimit = new ArrayList<String>(6);
	static {
		// KindCodeForNoLimit.add(ConstantCodes.KINDCODE_A01_12);
		// KindCodeForNoLimit.add(ConstantCodes.KINDCODE_A01_24);
		// KindCodeForNoLimit.add(ConstantCodes.KINDCODE_A01_X1);
		// KindCodeForNoLimit.add(ConstantCodes.KINDCODE_A01_17);
		// KindCodeForNoLimit.add(ConstantCodes.KINDCODE_A01_0G);
		// KindCodeForNoLimit.add(ConstantCodes.KINDCODE_A01_26);
	}

	/** 险别 简要提示 */
	public static final Map<String, List<String>> kindShortTitle = new LinkedHashMap<String, List<String>>(3);
	static {
		String kindCI = "21";
		String[] titleCI = { "傷害醫療給付", "失能給付", "死亡給付" };
		kindShortTitle.put(kindCI, Arrays.asList(titleCI));
	}

	/** 医疗需特殊处理 需查分为每一人，每一事故两条记录 */
	public static final List<String> PrintMedicalKind = new ArrayList<String>(11);
	static {
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_47);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_48);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_27);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_50);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_5B);
		// mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_5C);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_31);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_3A);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_51);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_52);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_53);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_3H);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_3F);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_3D);
		//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4(財:D1F1C3 /人:C4) START
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_C3);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_G1);
		PrintMedicalKind.add(ConstantCodes.KINDCODE_A01_G4);
		//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 END
	}
	
	/** 支付对象 帳號歸屬人證件類型 */
	public static final Map<String, String> prpdpaymentaccountCertificateTypeList = new LinkedHashMap<String, String>(6);
	static {
		prpdpaymentaccountCertificateTypeList.put("01", "身份證號");
		prpdpaymentaccountCertificateTypeList.put("02", "統一編號");
		prpdpaymentaccountCertificateTypeList.put("03", "護照號碼");
		prpdpaymentaccountCertificateTypeList.put("04", "居留證號碼");
		prpdpaymentaccountCertificateTypeList.put("99", "其他");
	}
	/** 打印页面初始化信息 */
	public static final Map<String, String> AcciPrintInfo = new LinkedHashMap<String, String>(6);
	static {
		AcciPrintInfo.put("ClaimApplication", "理賠申請書#!賠案號碼");
		AcciPrintInfo.put("PaymentNotice", "保險金給付通知書#!理賠計算書號碼");
		AcciPrintInfo.put("Report", "調查報告#!備案號碼");
		AcciPrintInfo.put("Revocation", "撤銷申請理賠同意書#!賠案號碼或備案號碼");
		AcciPrintInfo.put("Remittance", "匯款同意書#!理賠計算書號碼");
		AcciPrintInfo.put("Receipt", "賠款同意書暨領款收據#!理賠計算書號碼");
		AcciPrintInfo.put("Commissioned", "委託公證申請單#!賠案號碼");
		AcciPrintInfo.put("Contract", "債權讓與契約暨通知書#!理賠計算書號碼");
		AcciPrintInfo.put("Investigative", "查案單#!賠案號碼");
		AcciPrintInfo.put("Notification", "補件通知函#!賠案號碼");
		AcciPrintInfo.put("Compensate", "理賠計算書#!理算計算書號碼");
	}
	/** 殘餘物費用代碼 */
	public static final Map<String, String> RemnantCostList = new LinkedHashMap<String, String>(4);
	static {
		RemnantCostList.put("1", "1-殘餘物處理");
		RemnantCostList.put("2", "2-失竊車處理");
		RemnantCostList.put("3", "3-失竊車返還自負額");
	}
	/** 追償 強制險賠付類別 */
	public static final Map<String, String> compelPayTypeList = new LinkedHashMap<String, String>(6);
	static {
		compelPayTypeList.put("", "");
		compelPayTypeList.put("1", "1-傷害");
		compelPayTypeList.put("2", "2-失能");
		compelPayTypeList.put("3", "3-死亡");
	}

	/** 火险调查方式*/
	public static final Map<String, String> checkNatureList = new LinkedHashMap<String, String>(2);
	static {
		checkNatureList.put("0", "自行調查");
		checkNatureList.put("1", "委外調查");
		checkNatureList.put("2", "複合調查");
	}
	/** 火險打印頁面初始化 報表名稱信息*/
	public static final Map<String,String> PROPPRINTTYPEINFO = new LinkedHashMap<String,String>();
	static {
		PROPPRINTTYPEINFO.put("PropReplevyReport", "火險追償計算書");
		PROPPRINTTYPEINFO.put("PropPaymentAcceptance", "火險賠款接受書");
		PROPPRINTTYPEINFO.put("PropRegistReport", "火險出險報告");
		PROPPRINTTYPEINFO.put("PropGeneralClaim", "委託書");
		PROPPRINTTYPEINFO.put("PropRemittanceForm", "匯款同意書");
		PROPPRINTTYPEINFO.put("PropClaimApplicationForm", "理賠申請書");
		PROPPRINTTYPEINFO.put("PropClaimDisposeReport", "理賠處理報告");
		PROPPRINTTYPEINFO.put("PropCoinsCompensate", "聯共保計算書");
		PROPPRINTTYPEINFO.put("PropClaimCompensateReport", "火險賠款計算書");
		PROPPRINTTYPEINFO.put("PropLossList", "火險損失清單");
		PROPPRINTTYPEINFO.put("PropBankAgreement", "銀行同意書");
		PROPPRINTTYPEINFO.put("PropPrpinsClaimInformation", "火險承保理賠訊息");
		
	}
	/** 水險打印頁面初始化 報表名稱信息*/
	public static final Map<String,String> SHIPPRINTTYPEINFO = new LinkedHashMap<String,String>();
	static {
		SHIPPRINTTYPEINFO.put("CargoRemnant", "貨物運輸險殘餘物理算書");
		SHIPPRINTTYPEINFO.put("CargoClaimApplication", "貨物運輸險索賠函");
		SHIPPRINTTYPEINFO.put("CargoCommissioned", "貨物運輸險委託公證申請單");
		SHIPPRINTTYPEINFO.put("CargoSubrogation", "貨物運輸險代位追償權利書");
		SHIPPRINTTYPEINFO.put("CargoTransfer", "貨物運輸險權利轉讓書");
		SHIPPRINTTYPEINFO.put("CargoCompensate", "貨物運輸險賠款理算書");
		SHIPPRINTTYPEINFO.put("CargoRecovery", "貨物運輸險追償理算書");
		SHIPPRINTTYPEINFO.put("ShipRemnant", "殘餘物理算書");
		SHIPPRINTTYPEINFO.put("ShipClaimApplication", "理賠申請書");
		SHIPPRINTTYPEINFO.put("ShipRemittance", "匯款同意書");
		SHIPPRINTTYPEINFO.put("ShipReceipt", "賠款同意書暨領款收據");
		SHIPPRINTTYPEINFO.put("ShipCommissioned", "委託公證申請單");
		SHIPPRINTTYPEINFO.put("ShipContract", "債權讓與契約暨通知書");
		SHIPPRINTTYPEINFO.put("ShipRevocation", "撤銷申請理賠同意書");
		SHIPPRINTTYPEINFO.put("ShipCompensate", "賠款理算書");
		SHIPPRINTTYPEINFO.put("ShipReconciliation", "和解書");
		SHIPPRINTTYPEINFO.put("ShipRecovery", "追償理算書");
	}
	/** 责任险打印頁面初始化 報表名稱信息*/
	public static final Map<String,String> LIABPRINTTYPEINFO = new LinkedHashMap<String,String>();
	static {
		LIABPRINTTYPEINFO.put("LiabNotification","補件通知函");
		LIABPRINTTYPEINFO.put("LiabRemnant","殘餘物理算書");
		LIABPRINTTYPEINFO.put("LiabInvestigative","查案單");
		LIABPRINTTYPEINFO.put("LiabRevocation","撤銷申請理賠同意書");
		LIABPRINTTYPEINFO.put("LiabReconciliation","和解書");
		LIABPRINTTYPEINFO.put("LiabRemittance","匯款同意書");
		LIABPRINTTYPEINFO.put("LiabCompensate","理賠計算書");
		LIABPRINTTYPEINFO.put("LiabReceipt","賠款同意書暨領款收據");
		LIABPRINTTYPEINFO.put("LiabCommissioned","委託公證申請單");
		LIABPRINTTYPEINFO.put("LiabClaimApplication","理賠申請書");
		LIABPRINTTYPEINFO.put("LiabContract","債權讓與契約暨通知書");
		LIABPRINTTYPEINFO.put("LiabSingleNote","旅行業責任保險理賠照會單");
		LIABPRINTTYPEINFO.put("LiabCard","信用卡不便險理賠申請書");
		LIABPRINTTYPEINFO.put("LiabCardAppend","信用卡附加旅平險理賠申請書");
		LIABPRINTTYPEINFO.put("LiabCardComplex","信用卡綜合保險全球購物理賠申請書");
		LIABPRINTTYPEINFO.put("LiabDocument","應備文件");
		LIABPRINTTYPEINFO.put("LiabCardDocument","信用卡綜合保險應備文件");
		
	}

	/** 工程险打印頁面初始化 報表名稱信息*/
	public static final Map<String,String> GAAPRINTTYPEINFO = new LinkedHashMap<String,String>();
	static {
		GAAPRINTTYPEINFO.put("GAAClaimApplication", "理賠申請書");
		GAAPRINTTYPEINFO.put("GAARemittance", "匯款同意書");
		GAAPRINTTYPEINFO.put("GAAReceipt", "賠款同意書暨領款收據");
		GAAPRINTTYPEINFO.put("GAACommissioned", "委託公證申請單");
		GAAPRINTTYPEINFO.put("GAAContract", "債權讓與契約暨通知書 ");		
		GAAPRINTTYPEINFO.put("GAAContract", "撤銷申請理賠同意書 ");		
		GAAPRINTTYPEINFO.put("GAARevocation", "債權讓與契約暨通知書 ");
		GAAPRINTTYPEINFO.put("GAANotification", "補件通知函  ");
		GAAPRINTTYPEINFO.put("GAAInvestigative", "查案單 ");
		GAAPRINTTYPEINFO.put("GAAReconciliation", "和解書 ");
		GAAPRINTTYPEINFO.put("GAARemnant", "残余物理算书 ");
		GAAPRINTTYPEINFO.put("GAACompensate", "理賠計算書");
		//mantis：CLM0072 ，處理人員：BK007 蘇哲，需求單編號：CLM0072.工程險追償理算書
		GAAPRINTTYPEINFO.put("GAAReplevyReport", "追償計算書");	 
		
	}

	/** 打印頁面初始化 業務號類型信息*/
	public static final Map<String, String> BIZNOTYPE = new LinkedHashMap<String, String>();
	static {
		BIZNOTYPE.put("compensateNo", "計算書號碼");
		BIZNOTYPE.put("claimNo", "賠案號碼");
		BIZNOTYPE.put("registNo", "備案號碼");
		BIZNOTYPE.put("policyNo", "保單號碼");
	}
	/** 聯共保 */
	public static final Map<String,String> COINSFLAG = new LinkedHashMap<String, String>();
	static {
		COINSFLAG.put("1", "共保");
		COINSFLAG.put("3", "聯保");
	}
	/** 是否共保*/
	public static final Map<String,String> ISCOINSFLAG = new LinkedHashMap<String, String>();
	static {
		ISCOINSFLAG.put("0", "否");
		ISCOINSFLAG.put("1", "是");
	}
	/** 賠付代號*/
	public static final Map<String, String> prpLpayObjectInfoPaycodeTypeList = new LinkedHashMap<String, String>(4);
	static{
		prpLpayObjectInfoPaycodeTypeList.put("1", "1 - 一般賠案");
		prpLpayObjectInfoPaycodeTypeList.put("2", "2 - 同業");
		prpLpayObjectInfoPaycodeTypeList.put("3", "3 - 健保局");
	}
	/**
	 * 赔款速度别
	 */
	public static final Map<String,String> speedFlagList = new LinkedHashMap<String,String>(3);
	static{
		// mantis： CLM0106 ，處理人員：BK007  蘇哲，需求單編號：CLM0106.新核心案件賠付速別預設值更改為速件 -start
		speedFlagList.put("Y", "Y-速賠件");
		speedFlagList.put("N", "N-正常件");
		// mantis： CLM0106 ，處理人員：BK007  蘇哲，需求單編號：CLM0106.新核心案件賠付速別預設值更改為速件 -end
		speedFlagList.put("1", "1-外幣");
	}
	/**
	 * 结案类型
	 */
	public static final Map<String,String> closedTypeList = new LinkedHashMap<String,String>(3);
	static{
		closedTypeList.put("1","結案");
		closedTypeList.put("0","分次賠付");
		closedTypeList.put("2","拒賠");
		closedTypeList.put("3","免賠");
	}
	/**
	 * 单证类型
	 */
	public static final Map<String, String> certifyTypeList = new LinkedHashMap<String, String>(9);
	static {
		certifyTypeList.put("0", "索賠清單");
		certifyTypeList.put("1", "理賠申請書");
		certifyTypeList.put("2", "費用單據");
		certifyTypeList.put("3", "事故處理証明");
		certifyTypeList.put("4", "承保標的資料");
		certifyTypeList.put("5", "第三人財損資料");
		certifyTypeList.put("6", "第三人體傷資料");
		certifyTypeList.put("7", "相片");
		certifyTypeList.put("9", "其它");
	}
	/**
	 * 水险残废等级
	 */
	public static final Map<String, String> injuryGradeList_Y = new LinkedHashMap<String, String>(17);
	static {
		injuryGradeList_Y.put("", "");
		injuryGradeList_Y.put("1", "第一級");
		injuryGradeList_Y.put("2", "第二級");
		injuryGradeList_Y.put("3", "第三級");
		injuryGradeList_Y.put("4", "第 四級");
		injuryGradeList_Y.put("5", "第五級");
		injuryGradeList_Y.put("6", "第六級");
		injuryGradeList_Y.put("7", "第七級");
		injuryGradeList_Y.put("8", "第八級");
		injuryGradeList_Y.put("9", "第九級");
		injuryGradeList_Y.put("10", "第十級");
		injuryGradeList_Y.put("11", "第十一級");
	}
	
	/**
	 * 水险残废等级
	 */
	public static final Map<String, String> CompensateContext = new LinkedHashMap<String, String>(17);
	static {
		StringBuffer text = new StringBuffer("");
		text.append("1.保戶元祥金屬自TAICHUNG以海運出口BRASS WIRE至CAT LAI PORT, VN").append("\r\n");
		text.append("  數量總計2 STEEL ROBBINS，貨運公司前往領貨時發現貨物外觀磨損 ").append("\r\n");
		text.append("  ，卻未索取事故證明，逕自提領貨物至收貨人工廠，屬保單承保範圍。").append("\r\n");
		text.append("2.6卷電線（487 KG）經收貨人確認已無法使用，並請3家收購商報價，").append("\r\n"); 
		text.append("  殘值金額最高為USD816.33，經公證公司確認價格合理屬實。").append("\r\n");
		text.append("3.理算金額：487 KG X USD7.1/KG X 1.1 - USD816.33 = USD2,987.14。").append("\r\n");
		text.append("4.擬匯款賠付上述金額與收貨人HAI AU DEVELOPMENT CO., LTD後結案。").append("\r\n");
		text.append("  以上，呈請核示。").append("\r\n");
		CompensateContext.put("1", text.toString());
		text = new StringBuffer("");
		text.append("1.被保險人宏安汽車運輸股份有限公司之司機陳建名先生，於民國101年7月9日駕駛").append("\r\n");
		text.append("  承保之營業大貨車，車牌號碼：3J-907，，承載詠讚實業有限公司所屬之震動馬達一批，").append("\r\n");
		text.append("  於運送中行經高雄市大寮區光明路一段與正心路口時，因閃避機車，導致承載之貨物掉").append("\r\n");
		text.append("  落地面而受損，屬保單承保範圍。").append("\r\n");
		text.append("2.貨主詠讚實業有限公司原本主張受損之馬達全損，並向宏安汽車運輸股份有限公司求").append("\r\n");
		text.append("  償NTD121,000；經多次溝通，貨主才同意以修復方式處理，並由宏安汽車支付修理費用，").append("\r\n");
		text.append("  與貨主簽訂和解書。").append("\r\n");
		text.append("3.本案總損失金額為：馬達修復費用NT$18,000。扣除自負額NT$10,000後，理算損失").append("\r\n");
		text.append("  金額為NT$8,000。").append("\r\n");
		text.append("4.擬支付公證費用NT$12,075予寶島全球保險公證有限公司後結案。").append("\r\n");
		text.append("  以上，呈請核示。").append("\r\n");
		CompensateContext.put("2", text.toString());
		text = new StringBuffer("");
		text.append("1.被保險人從LIVORNO進口汽車零件到KEELUNG，因運送途中").append("\r\n");
		text.append("  發生意外事故，造成部份零件受損，屬保單承保範圍。").append("\r\n");
		text.append("2.本案為共保案件，LEADER為華南20%，我承保比例為1%。").append("\r\n");
		text.append("3.理算金額：NTD2,768 X 1% = NTD28。").append("\r\n");
		text.append("4.擬開票支付上述金額，並由信誼菁英轉交被保險人。").append("\r\n");
		text.append("  以上，呈請核示。").append("\r\n");
		CompensateContext.put("3", text.toString());
		text = new StringBuffer("");
		text.append("1.被保險人從BUSAN進口TFT-LCD MODULE至SHANGHAI，因運送途中").append("\r\n");
		text.append("  發生意外事故，造成貨物受損，屬保單承保範圍。").append("\r\n");
		text.append("2.本案為共保案件，LEADER為富邦20%，我承保比例為2%。").append("\r\n");
		text.append("3.理算金額：NTD55,371 X 2% = NTD1,107。").append("\r\n");
		text.append("4.擬匯款支付上述金額予富邦產險後結案。").append("\r\n");
		text.append("  以上，呈請核示。").append("\r\n");
		CompensateContext.put("4", text.toString());
		text = new StringBuffer("");
		text.append("1.被保險人自INDONESIA以海運方式進口METHANOL IN BULK").append("\r\n");
		text.append("  至MAILIAO, TAIWAN，卸貨時發現短少11.391M/T，屬保單承保範圍。").append("\r\n");
		text.append("2.本案為共保案件，LEADER為台產(25%)，我司承保比例為4%。").append("\r\n");
		text.append("3.理算金額：(USD360.54(11.391-2,250.09*0.5%)*29.056 + NTD17,144)*4%").append("\r\n");
		text.append("            = (NTD1,477 + NTD17,144)*4% = NTD745").append("\r\n");
		text.append("4.擬支票賠付上述金額予台灣醋酸化學股份有限公司後結案。").append("\r\n");
		text.append("  以上，呈請核示。").append("\r\n");
		CompensateContext.put("5", text.toString());
	}
	public static final Map<String,String> subrogationList = new LinkedHashMap<String, String>(17);
	static {
		subrogationList.put("N", "未涉及第29條代位情形");
		subrogationList.put("1", "飲用酒類或其他類似物後駕駛汽車");
		subrogationList.put("2", "吸食毒品、迷幻藥、麻醉藥品或其他相類似管制藥品");
		subrogationList.put("3", "故意行為所致");
		subrogationList.put("4", "從事犯罪行為或逃避合法拘捕");
		subrogationList.put("5", "違反道路交通管理處罰條例第二十一條或第二十一條之一規定");
	}
	
	public static final Map<String,String> transportTypeList = new LinkedHashMap<String, String>(17);
	static {
		transportTypeList.put("1", "海運");
		transportTypeList.put("2", "空運");
		transportTypeList.put("3", "快遞");
		transportTypeList.put("4", "郵包");
		transportTypeList.put("5", "台灣本島內陸運輸");
		transportTypeList.put("6", "小三通");
	}
	
	public static final Map<String,String> importTypeList = new LinkedHashMap<String, String>(17);
	static {
		importTypeList.put("1", "進口");
		importTypeList.put("2", "出口");
		importTypeList.put("3", "本島內陸運輸");
	}
	
	public static final Map<String,String> limitTypeList = new LinkedHashMap<String, String>(17);
	static {
		limitTypeList.put("perHumanInjury","64");
		limitTypeList.put("perAccidentDeaths","68");
		limitTypeList.put("perAccidentDamage","66");
		limitTypeList.put("perHumanDeath","62");
		limitTypeList.put("periodMaxAmount","65");
	}
	public static final Map<String,String> limitTypeNameList = new LinkedHashMap<String, String>(17);
	static {
		limitTypeNameList.put("64", "每一個人體傷或死亡");
		limitTypeNameList.put("68", "每一事故體傷或死亡");
		limitTypeNameList.put("66", "每一事故財產損失");
		limitTypeNameList.put("62", "每一事故最高責任");
		limitTypeNameList.put("65", "保險期間內最高責任");
	}
	/**
	 * 車體險估價單有無當事人簽署
	 */
	public static final Map<String,String> writtenEstimateList = new LinkedHashMap<String,String>(3);
	static{
		writtenEstimateList.put("N", "N");
		writtenEstimateList.put("Y", "Y");
	}
	/**
	 * 單一車輛自行碰撞事故統計代碼
	 */
	public static final Map<String,String> collisionCountList = new LinkedHashMap<String,String>(3);
	static{
		collisionCountList.put("1", "1-有憲警機關處理且有警察局道路交通事故初步分析研判表");
		collisionCountList.put("2", "2-有憲警機關處理，但無警察局道路交通事故初步分析研判表");
		collisionCountList.put("3", "3-無憲警機關處理");
		collisionCountList.put("9", "9-非單一車輛自行碰撞事故或非車體損失險");
	}
	
	/* #083 第三次修改 需求变更 增加憑證類型 */
	public static final Map<String,String> certificateTypeList = new LinkedHashMap<String,String>(2);
	static{
		certificateTypeList.put("1", "發票");
		certificateTypeList.put("0", "非發票");
	}
	/**
	 * 單一車輛自行碰撞事故統計代碼
	 */
	public static final Map<String,String> reservedEstimateList = new LinkedHashMap<String,String>(3);
	static{
		reservedEstimateList.put("N","否");
		reservedEstimateList.put("Y","是");
	}
	
	/**
	 * 賠付代號(賠案)
	 * mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增
	 */
	public static final Map<String,String> payCodeTypeList = new LinkedHashMap<String,String>(3);
	static{
		payCodeTypeList.put("1","一般賠案");
		payCodeTypeList.put("2","同業");
		payCodeTypeList.put("3","健保局");
	}
}
