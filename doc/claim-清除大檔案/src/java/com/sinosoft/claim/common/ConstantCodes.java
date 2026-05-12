package com.sinosoft.claim.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*******************************************************************************
 * 常量代码
 * @author 中科软
 */
public final class ConstantCodes {

	public static final String TOP_USERCODE = "00000000";
	/** 总公司代码 */
	public static final String MAINCOMPANYCOMCODE = "00";
	public static final String DEMILITER = "|";
	public static final String VALID = "1"; // 有效
	public static final String INVALID = "0"; // 无效
	public static final long COUNTONEDAY = 24 * 3600 * 1000;
	public static final String YUI_CHARSET = "UTF-8";// YAHOO YUI 的编码
	public static final String LOCAL_CURRENCY = "NTD";// 本国货币
	public static final String LOCAL_CURRENCYNAME = "新台幣";// 本国货币名称
	public static final int DEFAULT_PAGENO = 1;//默认页码
	public static final int DEFAULT_ROWSPERPAGE = 20;//默认每页记录数
	public static final String MODIFYENDCA = "claim.regist.modifyendca";//结案后可修改
	public static final String MODIFYCLAIM = "claim.regist.modifyclaim";//立案后可修改
	public static final String EXCEEDING = "claim.compensate.exceeding";//强制险有超额赔付权限的人员，医疗可以超过20万
	public static final double MODIFYCLAIM_E_AMOUNT = 500000;//調整估損金額超過50萬元之理賠案件
	public static final double MODIFYCLAIM_DIFF_DAY = 5;//立案後超過五日
	
	//mantis：CLM0034 ，處理人員：DP0706，需求單編號：CLM0034調整估損金額功能增加時間角色判斷(一般理賠人員)START
	public static final double MODIFYCLAIM_DIFF_DAY_7 = 7;//立案後超過七日
	public static final String GRADECODE_005 = "005"; //"一般理賠人員"角色
	public static final String GRADECODE_003 = "003"; //"理賠助理"角色
	public static final String GRADECODE_009 = "009"; //"部門理賠科長"角色
	public static final double MODIFYCLAIM_E_AMOUNT2 = 1000000;//調整估損金額超過100萬元之理賠案件
	//mantis：CLM0034 ，處理人員：DP0706，需求單編號：CLM0034調整估損金額功能增加時間角色判斷(一般理賠人員)END
	
	public static final List<String> CARGO_RISKCODE = new ArrayList<String>();//貨物運輸險险种代码
	public static final List<String> LIMIT_FIELD = new ArrayList<String>();//限额对应栏位
	public static final List<String> ENDORSE_CANCEL = new ArrayList<String>();//保单失效批改类型列表
	public static final String COMPANYCODE = "18";//台壽保產物保險股份有限公司
	/** 批改类型 */
	public static final String EndorseType_19 = "19";// 保单注销
	public static final String EndorseType_21 = "21";// 全单退保
	public static final String EndorseType_54 = "54";// 保单停效
	public static final String EndorseType_50 = "50";// 中止保險合同
	public static final String EndorseType_98 = "98";// 全額退保
	
	public static final int YEAROFFSET = 1911;//明国年和公元年的差

	public static final class StaticNum {
		public static final int ZERO = 0;
		public static final int ONE = 1;
		public static final int TWO = 2;
		public static final int THREE = 3;
		public static final int SIX = 6;
		public static final int ELEVEN = 11;
		public static final int FIVE = 5;
	}

	/** 编辑类型 */
	public static final class EditType {
		/** 增加 */
		public static final String ADD = "ADD";
		/** 删除 */
		public static final String DELETE = "DELETE";
		/** 修改 */
		public static final String EDIT = "EDIT";
		/** 展示 */
		public static final String SHOW = "SHOW";
	}

	/** 语言 */
	public static final class Language {
		/** 英语 */
		public static final String ENGLISH = "E";
		/** 汉语 */
		public static final String CHINESE = "C";
	}

	/** 数据库处理语言 */
	public static final class SQLLanguage {
		/** 升序 */
		public static final String ASC = " asc ";
		/** 降序 */
		public static final String DESC = " desc ";
		/** 大於 */
		public static final String GREATER = " > ";
		/** 小於 */
		public static final String LESS = " < ";
		/** 並且 */
		public static final String AND = " AND ";
	}

	/** 分页缺省值-0 */
	public static final int PAGE_DEFU = 0;
	/** 页序号默认值-1 */
	public static final int PAGENO_INIT = 1;
	/** 页宽默认值-10 */
	public static final int PAGESIZE_INIT = 10;

	/** 查询时间间隔范围 */
	public static final int QUERY_DATE_AREA = -365;
	
	/** 代码配置 */
	public static final class CodeConfig {
		/** 业务模式 */
		public static final String BIZMODEL = "BIZ_MODEL";
		/** 用户 */
		public static final String USERCODE = "userCode";
		/** 机构 */
		public static final String COMCODE = "comCode";
		/** 机构递归向下查询 */
		public static final String COMCODEDOWN = "comCodeDown";
		/** 机构递归向上查询 */
		public static final String COMCODEUP = "comCodeUp";
		/** 二级机构 */
		public static final String COMCODE_2_LEVEL = "comCode2";
		/** 三级机构 */
		public static final String COMCODE_3_LEVEL = "comCode3";
		/** 一组代码之间的分割符 */
		public static final String GROUP_SEPARATOR = "_group_separator_";
		/** 字段之间的分割符 */
		public static final String FIELD_SEPARATOR = "_field_separator_";
	}
	
	/**险类代码配置*/
	public static final String CLASSCODE_D05 = "05";
	public static final String CLASSCODE_E27 = "27";
	public static final String CLASSCODE_G08 = "08";
	public static final String CLASSCODE_Q01 = "01";
	public static final String CLASSCODE_Q22 = "22";
	public static final String CLASSCODE_T02 = "02";
	public static final String CLASSCODE_Y09 = "09";
	public static final String CLASSCODE_Y10 = "10";
	public static final String CLASSCODE_Z15 = "15";
	public static final String CLASSCODE_Z16 = "16";
	public static final String CLASSCODE_Z17 = "17";
	public static final String CLASSCODE_Z19 = "19";

	/**险种代码配置*/
	public static final String RISKCODE_DAA = "A01";
	public static final String RISKCODE_DAB = "0520";
	public static final String RISKCODE_DAC = "0530";
	public static final String RISKCODE_DAE = "0502";
	public static final String RISKCODE_DAF = "0509";
	public static final String RISKCODE_DAH = "0511";
	public static final String RISKCODE_DAJ = "0503";
	public static final String RISKCODE_DAS = "0505";
	public static final String RISKCODE_DAW = "0541";
	public static final String RISKCODE_DAZ = "B01";
	public static final String RISKCODE_DGB = "0510";
	public static final String RISKCODE_DGJ = "0517";
	public static final String RISKCODE_DJB = "0599";
	public static final String RISKCODE_DJF = "0512";
	public static final String RISKCODE_DJJ = "0516";
	public static final String RISKCODE_DJT = "0508";
	public static final String RISKCODE_DJY = "0513";
	public static final String RISKCODE_DQZ = "0518";
	public static final String RISKCODE_DTC = "0504";
	/** *****************意健险险种代码配置***************** */
	public static final String RISKCODE_EPA = "PA";
	public static final String RISKCODE_EGA = "GA";
	public static final String RISKCODE_EHG = "HG";
	public static final String RISKCODE_EHP = "HP";
	public static final String RISKCODE_ETA = "TA";
	public static final String RISKCODE_ETE = "TE";
	public static final String RISKCODE_ETR = "TR";
	public static final String RISKCODE_EPL = "PL";
	/** *****************火险险种代码配置***************** */
	public static final String RISKCODE_QF01 = "F01";
	public static final String RISKCODE_QF02 = "F02";
	/** ****************车险险别代码配置*************** */
	public static final String KINDCODE_D_A = "A"; // 机动车损失保险
	public static final String KINDCODE_D_A4 = "A4"; // 机动车保险选择汽车专修厂特约
	public static final String KINDCODE_D_AB = "AB"; // 机动车损失保险和第三者责任保险
	public static final String KINDCODE_D_B = "B"; // 第三者责任保险
	public static final String KINDCODE_D_B1 = "B1"; // 第三者人身伤亡保险
	public static final String KINDCODE_D_BZ = "21"; // 机动车交通事故责任强制险
	public static final String KINDCODE_D_C = "C"; // 代步机动车服务特约
	public static final String KINDCODE_D_C5 = "C5"; // 异地出险住宿费特约
	public static final String KINDCODE_D_C6 = "C6"; // 法律费用特约条款
	public static final String KINDCODE_D_C7 = "C7"; // 节假日行驶区域扩展特约
	public static final String KINDCODE_D_D1 = "D1"; // 车上人员责任险
	public static final String KINDCODE_D_D11 = "D11"; // 车上人员责任险(驾驶人)
	public static final String KINDCODE_D_D12 = "D12"; // 车上人员责任险(乘客)
	public static final String KINDCODE_D_D2 = "D2"; // 车上货物责任险
	public static final String KINDCODE_D_D3 = "D3"; // 驾驶员责任险
	public static final String KINDCODE_D_D4 = "D4"; // 乘客责任险
	public static final String KINDCODE_D_E = "E"; // 火灾、爆炸、自燃损失险
	public static final String KINDCODE_D_F = "F"; // 玻璃单独破碎险
	public static final String KINDCODE_D_FZ = "FZ"; // 车内附属装置单独被盗损失特约
	public static final String KINDCODE_D_G = "G"; // 全车盗抢险
	public static final String KINDCODE_D_G0 = "G0"; // 全车盗抢附加高尔夫球具盗窃险
	public static final String KINDCODE_D_H = "H"; // 车载货物掉落责任险
	public static final String KINDCODE_D_J = "J"; // 紧急救助特约
	public static final String KINDCODE_D_K1 = "K1"; // 起重、装卸、挖掘车辆损失扩展条款
	public static final String KINDCODE_D_K2 = "K2"; // 特种车辆固定设备、仪器损坏扩展条款
	public static final String KINDCODE_D_L = "L"; // 车身划痕损失险
	public static final String KINDCODE_D_LP = "LP"; // 换件特约
	public static final String KINDCODE_D_LT = "LT"; // 更换轮胎服务特约
	public static final String KINDCODE_D_M = "M"; // 不计免赔率特约
	public static final String KINDCODE_D_M1 = "M1"; // 可选免赔额特约
	public static final String KINDCODE_D_M2 = "M2"; // 多次出险增加免赔率特约条款
	public static final String KINDCODE_D_NX = "NX"; // 新车特约条款A
	public static final String KINDCODE_D_NY = "NY"; // 新车特约条款B
	public static final String KINDCODE_D_NZ = "NZ"; // 随车行李物品损失保险条款
	public static final String KINDCODE_D_R = "R"; // 交通事故精神损害赔偿责任保险
	public static final String KINDCODE_D_S = "S"; // 机动车出境保险
	public static final String KINDCODE_D_SC = "SC"; // 送油、充电服务特约
	public static final String KINDCODE_D_SZ = "SZ"; // 租车人人车失踪险
	public static final String KINDCODE_D_T = "T"; // 机动车停驶损失险
	public static final String KINDCODE_D_T1 = "T1"; // 机动车停驶损失险
	public static final String KINDCODE_D_TF = "TF"; // 拖车服务特约
	public static final String KINDCODE_D_TX = "TX"; // 约定区域通行费用特约
	public static final String KINDCODE_D_U = "U"; // 换件特约
	public static final String KINDCODE_D_V1 = "V1"; // 油污污染责任保险
	public static final String KINDCODE_D_W = "W"; // 无过失责任险
	public static final String KINDCODE_D_X = "X"; // 新增加设备损失保险
	public static final String KINDCODE_D_X1 = "X1"; // 发动机特别损失险
	public static final String KINDCODE_D_Y = "Y"; // 教练车特约
	public static final String KINDCODE_D_Z = "Z"; // 自燃损失险

	public static final String USERNATURECODE_1 = "1";// 1是自用
	public static final String USERNATURECODE_2 = "2";// 2是营业
	/** 新险种险别配置 **/
	/** 以下是prpdkind已配置的部分 */
	public static final String KINDCODE_A01_01 = "01"; // 車體損失險甲式
	public static final String KINDCODE_A01_02 = "02"; // 颱風洪水險(詳如保單條款)
	public static final String KINDCODE_A01_03 = "03"; // 罷工，暴動，民眾騷擾險
	public static final String KINDCODE_A01_05 = "05"; // 車體損失險乙式
	public static final String KINDCODE_A01_07 = "07"; // 車對車碰撞車體損失險
	public static final String KINDCODE_A01_08 = "08"; // 重大事故車體損失保險
	public static final String KINDCODE_A01_09 = "09"; // 限額車對車碰撞損失險
	public static final String KINDCODE_A01_0A = "0A"; // 車體損失險甲式附加代車費用
	public static final String KINDCODE_A01_0B = "0B"; // 車體損失險乙式附加代車費用
	public static final String KINDCODE_A01_0C = "0C"; // 車體損失險丙式附加代車費用
	public static final String KINDCODE_A01_0F = "0F"; // 車體損失險乙式＋附加限定駕駛人
	public static final String KINDCODE_A01_0G = "0G"; // 車體損失險免追償附加條款
	public static final String KINDCODE_A01_0H = "0H"; // 機車限額碰撞車體損失保險
	public static final String KINDCODE_A01_0I = "0I"; // 機車火災事故車體損失保險
	public static final String KINDCODE_A01_0J = "0J"; // 車體損失險乙式
	//mantis： CLM0166，處理人員：DP0713，需求單編號：車體新商品上線險別0Y
	public static final String KINDCODE_A01_0Y = "0Y"; // 車體損失保險丙式自負額附加條款
	public static final String KINDCODE_A01_11 = "11"; // 汽車竊盜損失險
	public static final String KINDCODE_A01_12 = "12"; // 零件配件被竊損失險
	public static final String KINDCODE_A01_14 = "14"; // 汽車竊盜損失保險附加代車費用
	public static final String KINDCODE_A01_17 = "17"; // 汽車竊盜損失險全損免折舊
	public static final String KINDCODE_A01_18 = "18"; // 機車整車失竊限額損失保險
	public static final String KINDCODE_A01_1A = "1A"; // 汽車竊盜損失保險零配件被竊高額保障附加條
	public static final String KINDCODE_A01_1X = "1X"; // 竊盜損失差額補償保險(甲型)
	public static final String KINDCODE_A01_1Y = "1Y"; // 竊盜損失差額補償保險(乙型)
	public static final String KINDCODE_A01_1Z = "1Z"; // 竊盜損失差額補償保險(丙型)
	public static final String KINDCODE_A01_24 = "24"; // 第三人受酒類車禍補償附加條款
	public static final String KINDCODE_A01_26 = "26"; // 供教練開車汽車第三人責任險條款
	public static final String KINDCODE_A01_27 = "27"; // 第三人責任險附加慰問金條款
	public static final String KINDCODE_A01_31 = "31"; // 任意汽車第三人責任險傷害責任險
	public static final String KINDCODE_A01_32 = "32"; // 任意汽車第三人責任險財損責任險
	public static final String KINDCODE_A01_3A = "3A"; // 任意汽車第三人責任險傷害責任險
	public static final String KINDCODE_A01_3B = "3B"; // 任意汽車第三人責任險財損責任險
	public static final String KINDCODE_A01_3C = "3C"; // 任意汽車第三人責任險殘廢責任增額
	// mantis： CLM0135 ，處理人員： BK007 蘇哲 ，需求單編號：CLM0135.新核心-新增車險商品45
	public static final String KINDCODE_A01_45 = "45"; // 強制汽車責任保險駕駛人傷害附加條款(限車主本人)
	public static final String KINDCODE_A01_47 = "47"; // 機車強制責任險附加駕駛人傷害險
	public static final String KINDCODE_A01_48 = "48"; // 汽車強制責任險附加駕駛人傷害險
	public static final String KINDCODE_A01_49 = "49"; // 第三人責任附加駕駛人傷害保險
	public static final String KINDCODE_A01_50 = "50"; // 第三人責任附加駕駛人傷害保險
	public static final String KINDCODE_A01_51 = "51"; // 第三人附加乘客體傷責任保險
	public static final String KINDCODE_A01_52 = "52"; // 第三人附加僱主責任保險
	public static final String KINDCODE_A01_53 = "53"; // 第三人附加旅客責任險
	public static final String KINDCODE_A01_5A = "5A"; // 汽車交通事故駕駛人傷害險
	public static final String KINDCODE_A01_5B = "5B"; // 汽車客運業乘客責任險（每一人死殘）
	// mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -start
	public static final String KINDCODE_A01_5C = "5C"; // 汽車交通事故駕駛人傷害保險傷害醫療給付附加條款-傷害醫療保險金的給付（實支實付型）
	public static final String KINDCODE_A01_5D = "5D"; // 汽車交通事故駕駛人傷害保險傷害醫療給付附加條款-傷害醫療保險金的給付（日額型）
	// mantis： CLM0123 ，處理人員： BK007 蘇哲 ，需求單編號： CLM0123 新核心-新增商品5C、5D -end
	public static final String KINDCODE_A01_71 = "71"; // 汽車貨物運送人責任險－貨物
	public static final String KINDCODE_A01_72 = "72"; // 汽車貨物運送人責任險－貨櫃
	public static final String KINDCODE_A01_81 = "81"; // 汽車經銷商汽車車體損失險
	public static final String KINDCODE_A01_82 = "82"; // 汽車經銷商汽車竊盜損失險
	public static final String KINDCODE_A01_A = "A"; // 機動車損失保險
	public static final String KINDCODE_A01_E1 = "E1"; // 第三人責任超額保險（不含酒償險）
	public static final String KINDCODE_A01_E2 = "E2"; // 第三人責任超額保險（含酒償險）
	public static final String KINDCODE_A01_X1 = "X1"; // 車體損失險全損免折舊
	public static final String KINDCODE_A01_Y1 = "Y1"; // 道路救援保險附加條款
	public static final String KINDCODE_A01_Y2 = "Y2"; // 道路救援保險附加條款－計次型
	//mantis： CLM0134，處理人員：DP0706，需求單編號：CLM0134新增拖吊險商品Y3
	public static final String KINDCODE_A01_Y3 = "Y3"; // 道路救援費用附加條款-A型
	public static final String KINDCODE_A01_0K = "0K"; // 汽車車體損失保險丙式-免自負額車對車限定駕駛人附加條款
	public static final String KINDCODE_A01_0L = "0L"; // 汽車車體損失保險折舊率附加條款（甲式）
	public static final String KINDCODE_A01_0M = "0M"; // 汽車車體損失保險折舊率附加條款（乙式）
	public static final String KINDCODE_A01_1B = "1B"; // 汽車竊盜損失保險折舊率附加條款(甲式)
	public static final String KINDCODE_A01_1C = "1C"; // 汽車竊盜損失保險折舊率附加條款(乙式)
	//mantis： CLM0059 ，處理人員：BK007 蘇哲，需求單編號：CLM0059 短期車險
	public static final String KINDCODE_A01_9A = "9A"; // 限額車對車碰撞損失險-短期

	//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 20190816 新增可賠付商品險種(E5,E6,E7) start
	public static final String KINDCODE_A01_E5 = "E5"; //第三人責任保險超額責任-乙式（不含酒償險）
	public static final String KINDCODE_A01_E6 = "E6"; //第三人責任保險超額責任-乙式（含酒償險）
	public static final String KINDCODE_A01_E7 = "E7"; //第三人責任保險超額責任-乙式（營業車）
	//mantis： CLM0016，處理人員：David，需求單編號：CLM0016 end
	
	//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 START
	public static final String KINDCODE_A01_E3 = "E3";//第三人責任附加超額險A式
	public static final String KINDCODE_A01_E9 = "E9";//第三人死亡及失能超額責任保險
	//mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END
	
	public static final String KINDCODE_A01_93 = "93";// 汽車任意第三人責任險(每一體傷)
	public static final String KINDCODE_A01_94 = "94";// 汽車任意第三人責任險（每一死亡）
	public static final String KINDCODE_A01_95 = "95";// 汽車任意第三人責任險（每一殘廢）
	public static final String KINDCODE_A01_0D = "0D";// 車體損失限乙式限定駕駛人-舊
	public static final String KINDCODE_A01_0E = "0E";// 車體損失險乙式限定駕駛人-舊
	public static final String KINDCODE_A01_16 = "16";// 汽車車體損失險甲乙丙式附加代車傳保發用
	public static final String KINDCODE_A01_22 = "22";// 汽車竊盜損失保險附加代車費用傳保發用
	
	public static final String KINDCODE_A01_3H = "3H"; // 優良駕駛人第三人責任保險傷害責任險－假日
	public static final String KINDCODE_A01_3F = "3F"; // 優良駕駛人第三人責任保險傷害責任險－平日
	public static final String KINDCODE_A01_3D = "3D"; // 優良駕駛人第三人責任保險傷害責任險－全時
	public static final String KINDCODE_A01_3I = "3I"; // 優良駕駛人第三人責任保險財損責任險－假日
	public static final String KINDCODE_A01_3G = "3G"; // 優良駕駛人第三人責任保險財損責任險－平日
	public static final String KINDCODE_A01_3E = "3E"; // 優良駕駛人第三人責任保險財損責任險－全時
	
	public static final String KINDCODE_A01_0N = "0N"; // 車體損失險駕駛人附加條款
	public static final String KINDCODE_A01_0P = "0P"; // 車體損失險駕駛人附加條款(A型)
	public static final String KINDCODE_A01_0Q = "0Q"; // 車體損失險駕駛人附加條款(B型)
	/** 需求變更131 */
	public static final String KINDCODE_A01_S1 = "S1"; // 車碰車代車費用
	public static final String KINDCODE_A01_S2 = "S2"; // 竊盜代車費用
	
	public static final String KINDCODE_A01_0S="0S"; //汽車車體損失保險乙式限額不明受損附加條款
	public static final String KINDCODE_A01_0T="0T"; //汽車車體損失保險新車全損免折舊附加條款
	public static final String KINDCODE_A01_3P="3P"; //汽車保險車隊附加條款(限法人專用)
	public static final String KINDCODE_A01_3N="3N"; //汽車第三人責任保險刑事訴訟律師費用補償附加條款
	public static final String KINDCODE_A01_1D="1D"; //汽車竊盜損失保險新車全損免折舊附加條款
	

	//mantis：CLM0196 ，處理人員：DP0713，需求單編號：CLM0196 新核心-任車新商品車體險0X
	//可使用財損、車損
	public static final String KINDCODE_A01_0X="0X"; //營業用丙式車對車碰撞車體損失險
	
	//mantis：CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC START
	//可使用財損、車損區START
	public static final String KINDCODE_A01_A1="A1"; //車體損失險甲式
	public static final String KINDCODE_A01_A2="A2"; //車體損失險乙式
	public static final String KINDCODE_A01_A3="A3"; //車對車碰撞車體損失險
	public static final String KINDCODE_A01_B1="B1"; //汽車竊盜損失險
	public static final String KINDCODE_A01_C2="C2"; //任意汽車第三人責任險財損責任險
	//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4
	public static final String KINDCODE_A01_C4="C4"; //計程車專用汽車第三人責任保險-財損(電動車) <-但是被要求放在任意跟D1 F1 C3同區塊
	//可使用財損、車損區END
	//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
	public static final String KINDCODE_A01_G2="G2";//G2-營業汽車駕駛人責任保險-第三人財損
	public static final String KINDCODE_A01_G3="G3";//G3-營業汽車駕駛人責任保險-車體損失
	//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統END
	
	//可使用人傷
	public static final String KINDCODE_A01_C1="C1"; //任意汽車第三人責任險傷害責任險
	//mantis：CLM0195 ，處理人員：DP0713，需求單編號：CLM0195 新核心-任車新商品電動車車體險ABC END
	//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4 START
	public static final String KINDCODE_A01_D1="D1";//旅客責任保險(電動車)
	public static final String KINDCODE_A01_F1="F1";//雇主責任保險(電動車)
	public static final String KINDCODE_A01_C3="C3";//計程車專用汽車第三人責任保險-傷害(電動車)
	//mantis：CLM0233，處理人員： DP0713 ，需求單編號：新核心-車險新商品增加險別D1F1C3C4 END
	//mantis： CLM0008 ，處理人員： David ，需求單編號： CLM0008   原因  新增可賠付新商品險種
	public static final String KINDCODE_A01_33 = "33"; // 任意汽車第三人責任保險-單一保額
	//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統 START
	public static final String KINDCODE_A01_G1="G1";//G1-營業汽車駕駛人責任保險-第三人傷害
	public static final String KINDCODE_A01_G4="G4";//G4-營業汽車駕駛人責任保險-旅客體傷
	//mantis：CLM0295 ，處理人員： DP0713 ，需求單編號：新核心-配合車險新商品G1~G4調整理賠系統END
	
	/** 可赔付人伤增加54 */
	public static final String KINDCODE_A01_54="54"; //機車乘客體傷責任保險
	
	public static final String KINDCODE_B01_BZ = "21";
	/** 以下是prpdkind 未配置、险种对照表有的部分 */
	public static final Map<String,String> carClassMap = new HashMap<String,String>();//判断是否是车险,D是车险,null,或者非D不是车险
	
	/** 以下是险类映射关系配置 */
	public static final String CLASSCODE_D = "D";//车险
	public static final String CLASSCODE_Q = "Q";//火险
	public static final String CLASSCODE_E = "E";//意健险
	public static final String CLASSCODE_Y = "Y";//水险
	public static final String CLASSCODE_G = "G";//工程险
	public static final String CLASSCODE_Z = "Z";//责任险
	public static final String CLASSCODE_D_A = "A";//商业车险
	public static final String CLASSCODE_D_B = "B";//强制车险
	public static final Map<String, String> riskCodeProcessId = new HashMap<String, String>();
	
	/** 以下是水险险种映射关系配置 */
	public static final String RISKCODE_AV = "AV";//航空保險
	public static final String RISKCODE_CF = "CF";//商業動產流動綜合保險
	public static final String RISKCODE_CL = "CL";//貨物運送人責任保險
	public static final String RISKCODE_EV = "EV";//娛樂漁業漁船意外責任保險
	public static final String RISKCODE_EW = "EW";//遊艇意外責任保險
	public static final String RISKCODE_FL = "FL";//承攬運送人責任保險
	public static final String RISKCODE_FW = "FW";//漁業漁船船員僱主責任保險
	public static final String RISKCODE_FV = "FV";//漁船險
	public static final String RISKCODE_MC = "MC";//貨物運輸保險
	public static final String RISKCODE_OH = "OH";//船體保險
	public static final String RISKCODE_FD = "FD";//FD-員工誠實保證保險,责任险
	public static final String RISKCODE_CC = "CC";//信用卡綜合保險,责任险
	//mantis：CLM0128，處理人員：DP0713，需求單編號：新核心-藝術品AR立案錯誤問題
	public static final String RISKCODE_AR = "AR";//藝術品綜合保險

	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 START
	/** 以下是RTC參數名稱轉換配置 */
	public static final String RTC_CODE_TYPE_RELATIONTYPE = "relationType";//這裡放置為對方的KEY NAME //備案人與被保險人關係

	public static final Map<String,String> rtcCodeTypeMap = new HashMap<String,String>();
	public static final String CHANNEL_SOURCE_RTC = "001";//RTC視訊
	
	/** 以下是CLM參數名稱轉換配置 */
	public static final String CLM_CODE_TYPE_RELATIONTYPE = "relationType";
	
	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 END
	
	static {
		init();
	}

	/***
	 * 数据初始化
	 */
	private static void init() {
		carClassMap.put(RISKCODE_DAA, "D");
		carClassMap.put(RISKCODE_DAZ, "D");
		carClassMap.put("A", "D");
		carClassMap.put("B", "D");
		riskCodeProcessId.put("D","claim_D");
		riskCodeProcessId.put("E","claim_E");
		riskCodeProcessId.put("Q","claim_Q");
		riskCodeProcessId.put("G","claim_G");
		riskCodeProcessId.put("Z","claim_Z");
		riskCodeProcessId.put("Y","claim_Y");
		riskCodeProcessId.put("AUDIT", "claim_audit");
		CARGO_RISKCODE.addAll(Arrays.asList("MC,OP,TB".split(",")));//初始化貨物運輸險
		LIMIT_FIELD.addAll(Arrays.asList("perHumanInjury,perAccidentDeaths,perAccidentDamage,perHumanDeath,periodMaxAmount".split(",")));
		ENDORSE_CANCEL.addAll(Arrays.asList(new String[] {EndorseType_19, EndorseType_21,EndorseType_50,EndorseType_54,EndorseType_98}));
		carClassMap.put(RISKCODE_EPA,"E");
		carClassMap.put(RISKCODE_EGA,"E");
		carClassMap.put(RISKCODE_EHG,"E");
		carClassMap.put(RISKCODE_EHP,"E");
		carClassMap.put(RISKCODE_ETA,"E");
		carClassMap.put(RISKCODE_ETE,"E");
		carClassMap.put(RISKCODE_ETR,"E");
		carClassMap.put(RISKCODE_EPL,"E");
		
		carClassMap.put(RISKCODE_QF01,"Q");
		carClassMap.put(RISKCODE_QF02,"Q");
		
		//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 START
		rtcCodeTypeMap.put(RTC_CODE_TYPE_RELATIONTYPE, CLM_CODE_TYPE_RELATIONTYPE);
		//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 END
	}
}
