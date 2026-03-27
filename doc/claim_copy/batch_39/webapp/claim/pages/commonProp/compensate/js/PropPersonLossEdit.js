/*****************************************************************************
 * DESC       ：人员列表增加JS
 * AUTHOR     : 中科軟
 * CREATEDATE ： 2004-08-01
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/

function checkBeyondQuota(field) { //检查是否超出限额
	var fieldname = field.name;
	var kindName = "";
	var quota = $parseFloat($(":input[name='prpLcompensateSumAmount']").val(),0);
	var amount = 0.00;
//	if (fm.all("prpLlossDtoKindName")[findex]) {
//		kindName = fm.all("prpLlossDtoKindName")[findex].value;
//	} else if (fm.all("prpLlossDtoKindName")[findex]) {
//		kindName = fm.all("prpLchargeKindName")[findex].value;
//	}
	$("#lLoss").find(":input[name='prpLlossDtoSumRealPayNTD']").each(function(){
		amount = amount + $parseFloat(this.value,0);
	});
	$("#Charge").find(":input[name='prpLchargeSumRealPay']").each(function(){
		amount = amount + $parseFloat(this.value,0);
	});
	$("#Person").find(":input[name='prpLpersonLossSumRealPayNTD']").each(function(){
		amount = amount + $parseFloat(this.value,0);
	});
	if (amount > quota) {
		alert(kindName + i18n.commonLiab.compensate.lossAssessmentAmount + quota + i18n.commonLiab.compensate.yuan); //估损金额之和超过限额(     //)元
//		disablebutton();
		return false;
	}
}

/**
 * 插入一条新的lLoss之後的处理（可选方法）
 */

function afterInsertperson() {
	setPrpLpersonSerialNo();
}

/**
   删除本条WarnRegion之後的处理（可选方法）
 */

function afterDeleteperson(field) {

	setPrpLpersonSerialNo();
}

/**
 * 设置setPrpLpersonLossSerialNo
 */

function setPrpLpersonSerialNo() {
	var count = getElementCount("prpLpersonLossSerialNo");
	for (var i = 0; i < count; i++) {
		if (count != 1) {
			fm.prpLpersonLossSerialNo[i].value = i;
		}
	}
}



/**
  在表格下方添加一组数据，禁止非本模块调用
  参数为页代码名称和页原始数据代码名称
  例:insertRow("Engage","Engage_Data");
  返回插入行的序号（从1开始）
  */

function insertRowTable(pageCode, dataPageCode, field) {
	var index = 0; //当前table索引
	var elements = null;
	index = getElementOrder(field) - 1;
	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");

	for (var i = 0; i < oTBODYData.rows.length; i++) {
		oTBODY.appendChild(oTBODYData.rows[i].cloneNode(true));
	}
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "personLossSerialNo") {
			elements[i].value = index;
		}
	}
	return true;
}

/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */

function deleteRowTable(field, pageCode, pageDataRowsCount, controlRowsCount) {
	recentDeletedRowNo = parseInt(getElementOrder(field));
	var pageLossSerialNo = 0;
	pageLossSerialNo = parseInt(fm.personLossSerialNo(recentDeletedRowNo - 1).value);
	var order = recentDeletedRowNo - 1; //顺序改为以0开始
	var oTBODY = document.getElementsByName(pageCode)[pageLossSerialNo].tBodies.item(0);
	order = getRowCurrPage(field, pageCode);
	for (var i = 0; i < controlRowsCount; i++) {
		oTBODY.removeChild(oTBODY.rows[order * controlRowsCount]);
	}
	return recentDeletedRowNo - 1;
}

//得到当前的行所在当前页位置

function getRowCurrPage(field, DataPageCode) {
	var i = 0;
	var name = "";
	var elements;
	var fieldName = "";
	var countLoss = 0; //人员费用的记录数
	var firstIndexLoss = 0; //本人员的费用的起始index
	var recentRowNo = 0;
	var orderCurrent = 0; //当前行所在当前面的位置
	recentRowNo = parseInt(getElementOrder(field));
	var pageLossSerialNo = 0;
	pageLossSerialNo = parseInt(fm.personLossSerialNo(recentRowNo - 1).value);
	//index--; //顺序改为以0开始
	//得到Input域的名字
	elements = getTableElements("Person");

	for (i = 0; i < elements.length; i++) {
		//得到人员费用索引起始位置及人员费用的记录数
		if (elements[i].name == "personLossSerialNo") {

			if (parseInt(elements[i].value) == pageLossSerialNo) {
				countLoss = countLoss + 1;
				if (firstIndexLoss == 0) {
					firstIndexLoss = getElementOrder(elements[i]);
				}
			}
		}
	}

	orderCurrent = recentRowNo - firstIndexLoss;
	return orderCurrent;
}
/***
 * 证件类型，证件号码发生变更时，需要清空所有粉人伤费用赔付，因为，人员变动需要重新校验限额
 */
function resetSumRealPay(field){
	var $personObject = $(field).parents("tr[name='personObject']");
	var $sumRealPay = $personObject.find(":input[name='prpLpersonLossSumRealPay']");
	var $sumRealPayNTD = $personObject.find(":input[name='prpLpersonLossSumRealPayNTD']");
	var checkFlag = false;
	var desc = "";
	if ("prpLpersonLossCertificateCode" == field.name || "prpLpersonLossIdentifyNumber" == field.name) {
		desc = "人員ID（證件類型、證件號碼）";
	} else if ("prpLpersonLossCasualties" == field.name) {
		desc = "傷亡情形";
	}
	$sumRealPay.each(function(i,e){
		if (parseFloat($.trim(e.value)) != 0) {
			if (!checkFlag) {
				alert("修改" + desc + "后，該人員的各項賠付需要重新校驗限額，\n請在賠款金額會重置后重新計算！");
			}
			checkFlag = true;
			e.value = 0;
			$sumRealPayNTD.get(i).value = 0;
		}
	});
	if(checkFlag){
		setSumRealPay1NTD(field);
		calFund();
	}
}
/***
 * 人員傷亡賠付，計算總賠償金額（NTD）：
 */
function setSumRealPay1NTD(field){
    var sumRealPay1NTD = 0;
    var $personObject = $(field).parents("tr[name='personObject']");
    $personObject.find(":input[name='prpLpersonLossSumRealPayNTD']").each(function(){
        sumRealPay1NTD += $parseFloat(this.value,0);
    });
    $personObject.find(":input[name='prpLpersonLossSumRealPay1NTD']").val(Math.round(sumRealPay1NTD));
}
/***
 * 人伤险别变更时，重新清空下赔付
 * @param field
 * @returns
 */
function clearPrpLpersonLoss(field){
	if($.trim(field.value).length == 0){
		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
		var $sumRealPay = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPay']");//賠償金額
		var $sumRealPayNTD = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPayNTD']");//賠償金額（NTD）
		if(parseFloat($sumRealPay.val()) != 0){
			$sumRealPay.val(0);
			$sumRealPayNTD.val(0);
		}
		setSumRealPay1NTD(field);
		calFund();
	}
}
/***
 * 計算人傷賠付
 * @param field
 */
function calRealpayForPerson(field){
    var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
    var kindCode = $prpLpersonLossObject.find(":input[name='prpLpersonLossKindCode']").val();
    if(kindCode.length == 0){
    	alert("請錄入人傷費用賠付險別！");
    	return false;
    }
    var sumDefPay = $parseFloat($prpLpersonLossObject.find(":input[name='prpLpersonLossSumDefPay']").val(),0);//核定賠償
    $prpLpersonLossObject.find(":input[name='prpLpersonLossSumLoss']").val(sumDefPay);
    var deductible = $parseFloat($prpLpersonLossObject.find(":input[name='prpLpersonLossDeductible']").val(),0);//自負額
    var currency = $prpLpersonLossObject.find(":input[name='prpLpersonLossCurrency']").val();
    var $sumRealPay = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPay']");//賠償金額
    var $sumRealPayNTD = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPayNTD']");//賠償金額（NTD）
    var exchRate = $parseFloat($prpLpersonLossObject.find(":input[name='prpLpersonLossExchRate']").val(),1);//匯率
    var sum = sumDefPay - deductible;
    if(sum < 0){
    	sum = 0;
    }
    $sumRealPay.val(pointTwo(sum,currency));
    $sumRealPayNTD.val(pointTwo(sum*exchRate));
    setSumRealPay1NTD(field);
    prpLpersonLossIsPayForOther(field);
    calFund();
}