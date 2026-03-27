var SPLIT = "_";
var LIMIT00 = "00";// 保额
var LIMIT0A = "0A";// 保险期间赔付
var LIMIT0G = "0G";// 保险期间赔付计次 --險別選擇的時候校驗
var LIMIT1A = "1A";// 每次事故赔付
var LIMIT1B = "1B";// 每次事故财产 --
var LIMIT1C = "1C";// 每次事故人伤 --
var LIMIT1D = "1D";// 每次事故医疗
var LIMIT1E = "1E";// 每次事故残废
var LIMIT1F = "1F";// 每次事故死亡
var LIMIT2C = "2C";// 每一人/每次事故 人伤总
var LIMIT2D = "2D";// 每一人/每次事故 医疗总
var LIMIT2E = "2E";// 每一人/每次事故 残废总
var LIMIT2F = "2F";// 每一人/每次事故 死亡总

/***
 * 當前校驗用對象
 */
function GroovyKind(payKind,payValue) {
	this.payKind = payKind;//校驗對象
	this.payValue = payValue;//賠付金額
}
/***
 * 险别限额校验
 * @param type 1，财产；2，人伤
 * @param kindCode 
 * @param $obj 当前所在的记录行，财产即prpLlossObject，人伤即费用即当前人personObject
 * @param realPay 本次计算后的赔偿金额
 */
function checkLimit(type,$obj,kindCode){
	var groovyKindArray = getGroovyKindArray(kindCode);
	var checkFlag = false;// true：超限额；false：未超限额
	var flag = kindCode + SPLIT + "FLAG";
	var limitValue = jQuery.data($limitObject,flag);//0有控制，-1未控制
	if(limitValue == -1){//無實際控制，險別賠付跟總保額比
		var hisPayValue = getHisPayValue(kindCode + SPLIT + LIMIT1A);//本事故已核賠過的，limitKind對應項目已赔付
		var currPay = getCurrPay(groovyKindArray, kindCode + SPLIT + LIMIT1A);//本次事故的賠付
		var sumamount = jQuery.data($limitObject,"sumamount");//總保額
		if(hisPayValue + currPay > sumamount){//超過總保額
			alert("險別 " + kindCode + " （NTD） \n本次事故已賠付：" + Math.round(hisPayValue) + " ，\n本次計算書賠付合計：" + Math.round(currPay) + " ，\n保單總保額：" + Math.round(sumamount) + "，\n本次校驗結果：超出總保額。");
			return true;
		}
	}else{
		if ("1" == type) {// 财产部分的校验
			if (!checkFlag) {
				checkFlag = checkPerCase(kindCode, groovyKindArray, kindCode + SPLIT + LIMIT1B, "每次事故財產");
			}
		} else if ("2" == type) {// 人伤部分的校验
			if (!checkFlag) {
				checkFlag = checkPersonPerCase(kindCode, groovyKindArray, $obj)
			}
			if (!checkFlag) {
				checkFlag = checkPerCase(kindCode, groovyKindArray, kindCode + SPLIT + LIMIT1C, "每次事故人傷");
			}
		}
		if (!checkFlag) {
			checkFlag = checkPerCase(kindCode, groovyKindArray, kindCode + SPLIT + LIMIT1A, "每次事故");// 每次事故
		}
		if (!checkFlag) {
			checkFlag = checkCase(kindCode, groovyKindArray)// 保險期間賠付
		}
	}
	return checkFlag;
}

/***
 * 校驗每次事故賠付的
 * @param kindCode 險別
 * @param groovyKindArray 險別的各項匯總
 * @param limitKind 限制項目
 * @param limitDesc 限制項目描述
 * @returns {Boolean}
 */
function checkPerCase(kindCode,groovyKindArray,limitKind,limitDesc){
	var limitValue = jQuery.data($limitObject,limitKind);//上限值，-1未控制
	if(limitValue != undefined && limitValue != -1){
		var hisPayValue = getHisPayValue(limitKind);//本事故已核賠過的，limitKind對應項目已赔付
		var currPay = getCurrPay(groovyKindArray,limitKind);//本計算書，limitKind對應的項目賠付
		if (hisPayValue + currPay > limitValue) {// 已赔 + 本次赔 > 限额 ，拒赔
			alert("險別 " + kindCode + " （NTD） \n"+limitDesc+"賠付上限：" + Math.round(limitValue) + " ，\n本次事故已賠付：" + Math.round(hisPayValue) + " ，\n本次計算書賠付合計：" + Math.round(currPay) + " ，\n本次校驗結果：超出賠付上限 。");
			return true;
		}
	}
	return false;
}
/***
 * 校驗每次事故每一人賠付的
 * @param kindCode 險別
 * @param groovyKindArray 險別的各項匯總
 * @param limitKind 限制項目
 * @param limitDesc 限制項目描述
 * @param id 當前人員的唯一標誌
 * @returns {Boolean}
 */
function checkPersonCase(kindCode,groovyKindArray,limitKind,limitDesc,id){
	var limitValue = jQuery.data($limitObject, limitKind);// 上限值，-1未控制
	if (limitValue != undefined && limitValue != -1) {
		limitKind = limitKind + SPLIT + id;//每一人的情況，
		var hisPayValue = getHisPayValue(limitKind);// 本事故，該人員已核賠過的limitKind對應項目
		var currPay = getCurrPay(groovyKindArray, limitKind);// 本計算書，本人員的limitKind對應的項目賠付
		if (hisPayValue + currPay > limitValue) {// 已赔 + 本次赔 > 限额 ，拒赔
			alert("險別 " + kindCode + " （NTD） \n" + limitDesc + "賠付上限：" + Math.round(limitValue) + " ，\n本次事故該人員已賠付：" + Math.round(hisPayValue) + " ，\n本次計算書該人員賠付合計：" + Math.round(currPay) + " ，\n本次校驗結果：超出賠付上限。");
			return true;
		}
	}
	return false;
}
/***
 * 校驗每次事故每人賠付
 * @param kindCode 險別
 * @param groovyKindArray 險別的各項匯總
 * @param $obj 當前人傷對象
 * @param limitKind 限制項目
 * @param limitDesc 限制項目描述
 * @returns {Boolean}
 */
function checkPersonPerCase(kindCode,groovyKindArray,$obj){
	var checkFlag = false;//true：超限额；false：未超限额
	var certificateCode = $obj.find(":input[name='prpLpersonLossCertificateCode']").val();// 證件類型
	var identifyNumber = $obj.find(":input[name='prpLpersonLossIdentifyNumber']").val();// 證件號碼
	var id = $.trim(certificateCode) + SPLIT + $.trim(identifyNumber);// id 人員的唯一標誌
	var casualties = $obj.find(":input[name='prpLpersonLossCasualties']").val();// 傷亡情形
	if ("1" == casualties) {
		if (!checkFlag) {
			checkFlag = checkPersonCase(kindCode,groovyKindArray,kindCode + SPLIT + LIMIT2D,"每一人每次事故醫療",id);
		}
		if (!checkFlag) {
			checkFlag = checkPerCase(kindCode,groovyKindArray,kindCode + SPLIT + LIMIT1D,"每次事故醫療");
		}
	} else if ("2" == casualties) {
		if (!checkFlag) {
			checkFlag = checkPersonCase(kindCode,groovyKindArray,kindCode + SPLIT + LIMIT2E,"每一人每次事故殘廢",id);
		}
		if (!checkFlag) {
			checkFlag = checkPerCase(kindCode,groovyKindArray,kindCode + SPLIT + LIMIT1E,"每次事故殘廢");
		}
	} else if ("3" == casualties) {
		if (!checkFlag) {
			checkFlag = checkPersonCase(kindCode,groovyKindArray,kindCode + SPLIT + LIMIT2F,"每一人每次事故死亡",id);
		}
		if (!checkFlag) {
			checkFlag = checkPerCase(kindCode,groovyKindArray,kindCode + SPLIT + LIMIT1F,"每次事故死亡");
		}
	}
	return checkFlag;
}

/***
 * 校驗保險期間賠付
 * @param kindCode
 * @param groovyKindArray
 * @param limitKind
 */
function checkCase(kindCode,groovyKindArray){
	var limitKind = kindCode + SPLIT + LIMIT0A;
	var limitValue = jQuery.data($limitObject,limitKind);//上限值，-1未控制
	if(limitValue != undefined && limitValue != -1){
		var hisPayValue = getHisPayValue(limitKind);//本事故已核賠過的，limitKind對應項目已赔付
		var currPay = getCurrPay(groovyKindArray,limitKind);//本計算書，limitKind對應的項目賠付
		if (hisPayValue + currPay > limitValue) {// 已赔 + 本次赔 > 限额 ，拒赔
			alert("險別 " + kindCode + " （NTD） \n保險期間賠付上限：" + Math.round(limitValue) + " ， \n已累計賠付：" + Math.round(hisPayValue) + " ，\n本次計算書賠付合計：" + Math.round(currPay) + " ，\n本次校驗結果：不通過 。");
			return true;
		}
	}
	return false;
}

/***
 * 获取该项的已赔付
 * @param limitKind
 * @returns
 */
function getHisPayValue(limitKind){
	var hisPayValue = jQuery.data($hisPayObject, limitKind);//
	if (hisPayValue == undefined) {// 沒有歷史賠付，默認0
		hisPayValue = 0;
	}
	return hisPayValue;
}

/***
 * 從險別的各項匯總中讀取某項的賠付
 * @param groovyKindArray 計算書險別的各項匯總，
 * @param limitKind 限制項目
 * @returns
 */
function getCurrPay(groovyKindArray,limitKind){
	var curryPay = 0;
	jQuery.each(groovyKindArray, function(i,e){
		if (e.payKind == limitKind) {// 數組中已有該項的賠付
			curryPay = e.payValue;
			return false;// 跳出each
		}
	})
	return curryPay;
}

/***
 * 當前計算書險別各項賠付的匯總
 * @param $obj
 * @param kindCode
 */
function getGroovyKindArray(kindCode){
	var groovyKindArray = new Array();
	$("#lLoss").find("tr[name='prpLlossObject']").each(function(i,e){// 財產部分險別各項賠付匯總，與CompensateLimitViewHelper.getHisPayPrpLloss逻辑一致
		var tempKindCode = $(e).find(":input[name='prpLlossDtoKindCode']").val();
		if (tempKindCode == kindCode) {
			var sumRealPayNTD = $(e).find(":input[name='prpLlossDtoSumRealPayNTD']").val();
			var tempPay = sumRealPayNTD.length == 0 ? 0 : parseFloat(sumRealPayNTD);
			getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT0A, tempPay);
			getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT1A, tempPay);
			getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT1B, tempPay);
		}
	});
	$("#Person").find("tr[name='personObject']").each(function(i,e){// 人傷部分險別各項賠付匯總，與CompensateLimitViewHelper.getHisPayPrpLpersonLoss逻辑一致
		var certificateCode = $(e).find(":input[name='prpLpersonLossCertificateCode']").val();// 證件類型
		var identifyNumber = $(e).find(":input[name='prpLpersonLossIdentifyNumber']").val();// 證件號碼
		var id = $.trim(certificateCode) + SPLIT + $.trim(identifyNumber);// id 人員的唯一標誌
		var casualties = $(e).find(":input[name='prpLpersonLossCasualties']").val();// 傷亡情形
		$(e).find("tr[name='prpLpersonLossObject']").each(function(j,f){
			var tempKindCode = $(f).find(":input[name='prpLpersonLossKindCode']").val();
			if (tempKindCode == kindCode) {
				var sumRealPayNTD = $(f).find(":input[name='prpLpersonLossSumRealPayNTD']").val();
				var tempPay = sumRealPayNTD.length == 0 ? 0 : parseFloat(sumRealPayNTD);
				getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT0A, tempPay);// 保险期间赔付
				getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT1A, tempPay);// 本次事故赔付
				getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT1C, tempPay);// 本次事故人伤總
				getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT2C + SPLIT + id, tempPay);// 每一人/每次事故人伤
				if ("1" == casualties) {
					getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT1D, tempPay);// 每次事故醫療
					getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT2D + SPLIT + id, tempPay);// 每一人/每次事故醫療
				} else if ("2" == casualties) {
					getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT1E, tempPay);// 每次事故殘廢
					getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT2E + SPLIT + id, tempPay);// 每一人/每次事故殘廢
				} else if ("3" == casualties) {
					getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT1F, tempPay);// 每次事故死亡
					getGroovyKind(groovyKindArray, kindCode + SPLIT + LIMIT2F + SPLIT + id, tempPay);// 每一人/每次事故死亡
				}
			}
		})
	});
	return groovyKindArray;
}

/**
 * 當前計算書險別的的賠付訊息，分類匯總，存儲
 * @param groovyKindArray 當前配置校驗的各項賠付訊息
 * @param limit 校驗項
 * @param tempPay 賠付金額
 */
function getGroovyKind(groovyKindArray,payKind,payValue){
	var has = false;
	jQuery.each(groovyKindArray, function(i,e){
		if (e.payKind == payKind) {// 數組中已有該項的賠付，則累計下金額
			e.payValue = e.payValue + payValue;
			has = true;
			return false;// 跳出each
		}
	})
	if (!has) {
		groovyKindArray.push(new GroovyKind(payKind, payValue));
	}
}

/***
 * 校验是否超计次
 * @param limitStr 校验项目
 * @param kindCode 险别
 * @param limitValue
 * @returns
 */
function checkLimit_0g(kindCode){
	var limitKind = kindCode + SPLIT + LIMIT0G;
	var limitValue = jQuery.data($limitObject, limitKind);
	if (limitValue != undefined && limitValue != -1) {
		var hisPayValue = jQuery.data($hisPayObject, limitKind);
		if (limitKind != undefined && limitKind >= limitValue) {// 已赔付次数 >= 限制的赔付次数 ，拒赔
			alert("險別 " + kindCode + " 保險期間賠付計次已達上限 " + limitValue + " 次。");
			return true;
		}
	}
	return false;
}