/*
 *常量配置信息 @ ConstantCodes.java为一一对应
 */
/** *****************币别代码配置***************** */
var CURRENCYINFO = {};
CURRENCYINFO.LOCAL_CURRENCY = "NTD"; // 本国货币
CURRENCYINFO.LOCAL_CURRENCYNAME = "新台幣"; // 本国货币名称

var MAXMEDICALPAY = 200000;  //醫療費用限额
var MAXCRIPPLEDPAY = 2000000;  //殘疾給付額限额
var MAXDEATHPAY  = 2000000; //死亡给付限额
var FeeA024 = 50000;//義齒器材及裝置費
var FeeA026 = 20000;//其他必要之醫療器材
var FeeA03 = 20000; //接送費用
var FeeA04 = 36000;//看護費用  每日限額 不超過30日
var FeeA00 = 200000;//總額
var RISKINFO = {};
/** *****************险类代码配置***************** */
RISKINFO.CLASSCODE_D05 = "05";
RISKINFO.CLASSCODE_E27 = "27";
RISKINFO.CLASSCODE_G08 = "08";
RISKINFO.CLASSCODE_Q01 = "01";
RISKINFO.CLASSCODE_Q22 = "22";
RISKINFO.CLASSCODE_T02 = "02";
RISKINFO.CLASSCODE_Y09 = "09";
RISKINFO.CLASSCODE_Y10 = "10";
RISKINFO.CLASSCODE_Z15 = "15";
RISKINFO.CLASSCODE_Z16 = "16";
RISKINFO.CLASSCODE_Z17 = "17";
RISKINFO.CLASSCODE_Z19 = "19";
/** *****************险种代码配置***************** */
RISKINFO.RISKCODE_DAA = "A01";
RISKINFO.RISKCODE_DAB = "0520";
RISKINFO.RISKCODE_DAC = "0530";
RISKINFO.RISKCODE_DAE = "0502";
RISKINFO.RISKCODE_DAF = "0509";
RISKINFO.RISKCODE_DAH = "0511";
RISKINFO.RISKCODE_DAJ = "0503";
RISKINFO.RISKCODE_DAS = "0505";
RISKINFO.RISKCODE_DAW = "0541";
RISKINFO.RISKCODE_DAZ = "B01";
RISKINFO.RISKCODE_DGB = "0510";
RISKINFO.RISKCODE_DGJ = "0517";
RISKINFO.RISKCODE_DJB = "0599";
RISKINFO.RISKCODE_DJF = "0512";
RISKINFO.RISKCODE_DJJ = "0516";
RISKINFO.RISKCODE_DJT = "0508";
RISKINFO.RISKCODE_DJY = "0513";
RISKINFO.RISKCODE_DQZ = "0518";
RISKINFO.RISKCODE_DTC = "0504";
/** ****************车险险别代码配置*************** */
RISKINFO.KINDCODE_D_A = "A"; // 机动车损失保险
RISKINFO.KINDCODE_D_A4 = "A4"; // 机动车保险选择汽车专修厂特约
RISKINFO.KINDCODE_D_AB = "AB"; // 机动车损失保险和第三者责任保险
RISKINFO.KINDCODE_D_B = "B"; // 第三者责任保险
RISKINFO.KINDCODE_D_B1 = "B1"; // 第三者人身伤亡保险
RISKINFO.KINDCODE_D_BZ = "21"; // 机动车交通事故责任强制险
RISKINFO.KINDCODE_D_C = "C"; // 代步机动车服务特约
RISKINFO.KINDCODE_D_C5 = "C5"; // 异地出险住宿费特约
RISKINFO.KINDCODE_D_C6 = "C6"; // 法律费用特约条款
RISKINFO.KINDCODE_D_C7 = "C7"; // 节假日行驶区域扩展特约
RISKINFO.KINDCODE_D_D1 = "D1"; // 车上人员责任险
RISKINFO.KINDCODE_D_D11 = "D11"; // 车上人员责任险(驾驶人)
RISKINFO.KINDCODE_D_D12 = "D12"; // 车上人员责任险(乘客)
RISKINFO.KINDCODE_D_D2 = "D2"; // 车上货物责任险
RISKINFO.KINDCODE_D_D3 = "D3"; // 驾驶员责任险
RISKINFO.KINDCODE_D_D4 = "D4"; // 乘客责任险
RISKINFO.KINDCODE_D_E = "E"; // 火灾、爆炸、自燃损失险
RISKINFO.KINDCODE_D_F = "F"; // 玻璃单独破碎险
RISKINFO.KINDCODE_D_FZ = "FZ"; // 车内附属装置单独被盗损失特约
RISKINFO.KINDCODE_D_G = "G"; // 全车盗抢险
RISKINFO.KINDCODE_D_G0 = "G0"; // 全车盗抢附加高尔夫球具盗窃险
RISKINFO.KINDCODE_D_H = "H"; // 车载货物掉落责任险
RISKINFO.KINDCODE_D_J = "J"; // 紧急救助特约
RISKINFO.KINDCODE_D_K1 = "K1"; // 起重、装卸、挖掘车辆损失扩展条款
RISKINFO.KINDCODE_D_K2 = "K2"; // 特种车辆固定设备、仪器损坏扩展条款
RISKINFO.KINDCODE_D_L = "L"; // 车身划痕损失险
RISKINFO.KINDCODE_D_LP = "LP"; // 换件特约
RISKINFO.KINDCODE_D_LT = "LT"; // 更换轮胎服务特约
RISKINFO.KINDCODE_D_M = "M"; // 不计免赔率特约
RISKINFO.KINDCODE_D_M1 = "M1"; // 可选免赔额特约
RISKINFO.KINDCODE_D_M2 = "M2"; // 多次出险增加免赔率特约条款
RISKINFO.KINDCODE_D_NX = "NX"; // 新车特约条款A
RISKINFO.KINDCODE_D_NY = "NY"; // 新车特约条款B
RISKINFO.KINDCODE_D_NZ = "NZ"; // 随车行李物品损失保险条款
RISKINFO.KINDCODE_D_R = "R"; // 交通事故精神损害赔偿责任保险
RISKINFO.KINDCODE_D_S = "S"; // 机动车出境保险
RISKINFO.KINDCODE_D_SC = "SC"; // 送油、充电服务特约
RISKINFO.KINDCODE_D_SZ = "SZ"; // 租车人人车失踪险
RISKINFO.KINDCODE_D_T = "T"; // 机动车停驶损失险
RISKINFO.KINDCODE_D_T1 = "T1"; // 机动车停驶损失险
RISKINFO.KINDCODE_D_TF = "TF"; // 拖车服务特约
RISKINFO.KINDCODE_D_TX = "TX"; // 约定区域通行费用特约
RISKINFO.KINDCODE_D_U = "U"; // 换件特约
RISKINFO.KINDCODE_D_V1 = "V1"; // 油污污染责任保险
RISKINFO.KINDCODE_D_W = "W"; // 无过失责任险
RISKINFO.KINDCODE_D_X = "X"; // 新增加设备损失保险
RISKINFO.KINDCODE_D_X1 = "X1"; // 发动机特别损失险
RISKINFO.KINDCODE_D_Y = "Y"; // 教练车特约
RISKINFO.KINDCODE_D_Z = "Z"; // 自燃损失险

RISKINFO.CLASSCODE_D_A = "A";
RISKINFO.CLASSCODE_D_B = "B";

function getClassCodeType(code){
	var flag = "";
	if(RISKINFO.RISKCODE_DAZ==code){
		flag = "D";
	}else if(RISKINFO.RISKCODE_DAA==code){
		flag = "D";
	}else if(RISKINFO.RISKCODE_DAA==code){
		flag = "D";
	}else if(RISKINFO.CLASSCODE_D_A==code){
		flag = "D";
	}else if(RISKINFO.CLASSCODE_D_B==code){
		flag = "D";
	}
	return flag;
}

/**乘載類別*/
var LOADKIND_P = ['01','02','03','05','07','09','14','15','21','22','23','24','32','34'];//人數
var LOADKIND_T = ['06','10','13','18','20','30','31'];//噸數

/**乘載人數、噸位*/
//-1代表小于等于，0代表等于，1代表大于等于
var QUANTITY_P = {"01":"2,0", "02":"2,0", "03":"10,-1", "04":"10,-1", "05":"10,1", "07":"10,-1", "08":"10,-1", "09":"10,1", "11":"10,-1", "12":"10,1", "14":"10,-1", "15":"10,-1", "16":"10,-1", "17":"10,1", "19":"10,-1", "21":"10,-1", "22":"10,-1", "23":"10,1", "24":"10,1", "32":"2,0", "34":"2,0"};
var QUANTITY_T = {"04":"3.5,-1", "06":"3.5,1", "08":"3.5,-1", "10":"3.5,1", "11":"3.5,-1", "12":"3.5,1", "16":"3.5,-1", "17":"3.5,1", "19":"3.5,-1", "20":"3.5,1"};