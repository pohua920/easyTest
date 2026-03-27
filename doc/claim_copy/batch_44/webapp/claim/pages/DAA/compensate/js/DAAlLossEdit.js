/*****************************************************************************
 * DESC       ：人员列表增加JS
 * AUTHOR     : 理赔组
 * CREATEDATE ： 2004-08-01
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 * 插入一条新的lLoss之後的处理（可选方法）
 */
function afterInsertlLoss() {

	setPrpLlossSerialNo();
}

/*
   删除本条WarnRegion之後的处理（可选方法）
 */
function afterDeletelLoss(field) {
	initEvryTypeRealPay();
	calFund();
	calLoss();
	setPrpLlossSerialNo();
}

/**
 * 设置setPrpLlLossLossSerialNo
 */
function setPrpLlossSerialNo() {
	var count = getElementCount("prpLlossDtoSerialNo");
	for (var i = 0; i < count; i++) {
		if (count != 1) {
			fm.prpLlossDtoSerialNo[i].value = i;
		}
	}
}

/**
  在表格下方添加一组数据，禁止非本模块调用
  参数为页代码名称和页原始数据代码名称
  例:insertRow("Engage","Engage_Data");
  返回插入行的序号（从1开始）
  */
function insertRowTableL(pageCode, dataPageCode, field, kindCode, trueKindCode, trueKindName, itemKindNo, dutyDeductibleRate, deductibleRate, driverDeductibleRate, unitAmount, amount) {
	var index = 0; //当前table索引
	var elements = null;
	var numIndex = 0;
	index = getElementOrder(field) - 1;
	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(kindCode);
	elements = oTBODY.getElementsByTagName("INPUT");

	for (var i = 0; i < oTBODYData.rows.length; i++) {
		oTBODY.appendChild(oTBODYData.rows[i].cloneNode(true));
	}
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "lossDtoSerialNo") {
			elements[i].value = index;
		}
	}

	var newField;
	var tempElements = oTBODY.getElementsByTagName("INPUT");
	for (var j = 0; j < tempElements.length; j++) {
		if (tempElements[j].name == "prpLlossDtoKindCode") {
			newField = tempElements[j];
		}
	}
	index = getElementOrder(newField) - 1;

	fm.prpLlossDtoKindCode[index].value = trueKindCode;
	fm.prpLlossDtoKindName[index].value = trueKindName;

	//设置事故责任比例为该案的事故责任比例
	fm.prpLlossDtoIndemnityDutyRate[index].value = fm.prpLcompensateIndemnityDutyRate.value;

	if (fm.prpLlossDtoKindCode[index - 1].value != fm.prpLlossDtoKindCode[index].value) {
		fm.licenseNo[index].value = "";
	} else {
		fm.licenseNo[index].value = fm.licenseNo[index - 1].value;
	}

	fm.prpLlossDtoItemKindNo[index].value = itemKindNo;
	fm.prpLlossDtoDutyDeductibleRate[index].value = dutyDeductibleRate;
	fm.prpLlossDtoDeductibleRate[index].value = deductibleRate;
	fm.prpLlossDtoDriverDeductibleRate[index].value = driverDeductibleRate;
	fm.prpLlossDtoUnitPrice[index].value = unitAmount;
	fm.prpLlossDtoAmount[index].value = amount;
}

/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */
function deleteRowTableL(field, pageCode, pageDataRowsCount, controlRowsCount) {
	recentDeletedRowNo = parseInt(getElementOrder(field));
	var pageLossSerialNo = 0;
	pageLossSerialNo = parseInt(fm.lossDtoSerialNo(recentDeletedRowNo - 1).value);
	var order = recentDeletedRowNo - 1; //顺序改为以0开始
	var oTBODY = document.getElementsByName(pageCode)[pageLossSerialNo].tBodies.item(0);
	order = getRowCurrPageL(field, pageCode);
	for (var i = 0; i < controlRowsCount; i++) {
		oTBODY.removeChild(oTBODY.rows[order * controlRowsCount]);
	}

	calRealpayForDuBang(field);
	calLoss();
	return recentDeletedRowNo - 1;
}

//得到当前的行所在当前页位置

function getRowCurrPageL(field, DataPageCode) {
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
	pageLossSerialNo = parseInt(fm.lossDtoSerialNo(recentRowNo - 1).value);

	//index--; //顺序改为以0开始
	//得到Input域的名字
	elements = getTableElements("lLoss");

	for (i = 0; i < elements.length; i++) {
		//得到人员费用索引起始位置及人员费用的记录数
		if (elements[i].name == "lossDtoSerialNo") {

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



function setlossSumDefPay(field) {

	var serialNo = 0; //定位
	var fieldName = field.name;
	for (var i = 1; i < fm.all(fieldName).length; i++) {
		if (field == fm.all(fieldName)[i]) {
			serialNo = i;
			break;
		}
	}


	var sumLoss = parseFloat(fm.prpLlossDtoSumLoss[serialNo].value);
	var sumRest = parseFloat(fm.prpLlossDtoSumRest[serialNo].value);

	if (isNaN(sumRest)) {
		sumRest = 0;
	}

	if (isNaN(sumLoss)) {
		sumLoss = 0;
	}

	var sumDefPay = sumLoss - sumRest;

	fm.prpLlossDtoSumDefPay[serialNo].value = point(round(sumDefPay, 0), 0);


}