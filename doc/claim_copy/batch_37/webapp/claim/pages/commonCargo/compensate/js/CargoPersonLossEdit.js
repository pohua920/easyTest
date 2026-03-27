/*****************************************************************************
 * DESC       ：人员列表增加JS
 * AUTHOR     : 理赔组
 * CREATEDATE ： 2004-08-01
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
function checkBeyondQuota(field) { //检查是否超出限额

	var quota = 0;
	var fieldname = field.name;
	var findex = 0;
	var kindName = "";
	var countCharge = getElementCount("prpLchargeChargeCode");
	var countPersonLoss = getElementCount("prpLpersonLossSumRealPay1");
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	if (fm.all("prpLlossDtoAmountDisplay")[findex]) {
		quota = parseFloat(fm.all("prpLlossDtoAmountDisplay")[findex].value);
	} else {
		quota = parseFloat(fm.all("prpLlossDtoAmountDisplay")[0].value);
	}

	var amount = 0.00;
	if (fm.all("prpLlossDtoKindName")[findex]) {
		kindName = fm.all("prpLlossDtoKindName")[findex].value;
	} else if (fm.all("prpLlossDtoKindName")[findex]) {
		kindName = fm.all("prpLchargeKindName")[findex].value;
	}
	var kindNum = fm.all("prpLlossDtoKindCode").length;
	if (kindNum != 'undefined' && kindNum > 1) {
		for (var i = 1; i < kindNum; i++) {
			if (fm.prpLlossDtoSumRealPay[i]) {
				if (fm.prpLlossDtoSumRealPay[i].value != "") {
					amount = amount + parseFloat(fm.prpLlossDtoSumRealPay[i].value);
				}
			}
		}
	}
	for (var i = 0; i < countCharge; i++) {
		if (fm.prpLchargeSumRealPay[i]) {
			if (fm.prpLchargeSumRealPay[i].value != "") {
				amount = amount + parseFloat(fm.prpLchargeSumRealPay[i].value);
			}

		}
	}
	for (var i = 0; i < countPersonLoss; i++) {
		if (fm.prpLpersonLossSumRealPay1[i]) {
			if (fm.prpLpersonLossSumRealPay1[i].value != "") {
				amount = amount + parseFloat(fm.prpLpersonLossSumRealPay1[i].value);
			}
		}
	}
	if (amount > quota) {
		alert(i18n.compensate.estimateAmount + quota + i18n.compensate.money); //估损金额之和超过限额（    //）元
		disablebutton();
		return false;
	}
}


/**
 * 插入一条新的lLoss之後的处理（可选方法）
 */
function afterInsertperson() {
	setPrpLpersonSerialNo();
}

/*
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
	//order = order - pageDataRowsCount;  //去掉隐含域中的控制按钮的个数
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