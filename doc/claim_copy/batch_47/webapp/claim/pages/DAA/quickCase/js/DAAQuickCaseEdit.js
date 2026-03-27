/*****************************************************************************
 * DESC       ：简易赔案的脚本函数页面(车险类的)
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2007-06-12
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/****************************************************************************
 * DESC       :MulLine.js, don't modify.
 * CREATEDATE :2006-09-11
 * MODIFYLIST :   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 *
 ****************************************************************************/
/********************
 * 增加保费是否实收提示
 * add by liping
 * 20080709
 *******************/

function initPaySet() {
	var payFee = fm.quickCasepayFlag.value;
	var message = "";
	if (payFee == -1) {
		message = message + "商业险保单保费未缴,请慎重处理！！！ \n";
	} else if (payFee == -2) {
		message = message + "商业险保单已缴未缴全,请慎重处理！！！ \n";
	}

	var compelPayFee = fm.compelQuickCasepayFlag.value;
	if (compelPayFee == -1) {
		message = message + i18n.quickCase.insurancePolicyPaidFull + "\n"; //交强险保单保费未缴,请慎重处理！！！ \n
	} else if (compelPayFee == -2) {
		message = message + i18n.quickCase.insurancePolicyPaidFullCarefully + "\n"; //交强险保单已缴未缴全,请慎重处理！！！ \n
	}
	if (message.length > 0) {
		alert(message);
	}
}

/********************
 * 定损
 * 定损损失车辆(增加)
 *******************/

function insertRowTableOfCertainLossCar(pageCode, dataPageCode, field, indexs) {

	var index = 0; //当前table索引
	var elements = null;

	if (indexs != "undefined" || indexs != null || indexs != "") {
		index = 0;
	}
	if (indexs == "undefined") {
		index = getElementOrder(field) - 1;
	}
	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");

	oTBODY.appendChild(oTBODYData.rows[oTBODYData.rows.length - 1].cloneNode(true));
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "RelateSerialNo") {
			elements[i].value = index;
		}
	}
	var count = getElementCount("prpLverifyLossLossItemCode");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.RelateSerialNo[j].value == index) {
			k++;
			fm.prpLverifyLossLossItemCode[j].value = k;
		}
	}
	return true;
}


/********************
 * 定损
 * 修理项目费用清单(增加)
 *******************/

function insertRowTableOfCertainLossRepair(pageCode, dataPageCode, field) {
	var index = 0; //当前table索引
	var elements = null;

	index = getElementOrder(field) - 1;

	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");

	oTBODY.appendChild(oTBODYData.rows[oTBODYData.rows.length - 1].cloneNode(true));

	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "prpLRepairFeeLossItemCode") {
			elements[i].value = index;
		}
	}

	var count = getElementCount("prpLRepairFeeSerialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.prpLRepairFeeLossItemCode[j].value == index) {
			k++;
			fm.prpLRepairFeeLossItemCode[j].value = index;
			fm.prpLrepairFeeLicenseNo[j].value = fm.prpLcarLossLossItemName[index].value;
			fm.prpLRepairFeeSerialNo[j].value = k;
		}
	}

	return true;
}


/********************
 * 定损
 * 定损车上损失财产(增加)
 *******************/

function insertRowTableOfCertainLossProp(pageCode, dataPageCode, field) {
	var index = 0; //当前table索引
	var elements = null;

	index = getElementOrder(field) - 1;
	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");

	oTBODY.appendChild(oTBODYData.rows[oTBODYData.rows.length - 1].cloneNode(true));
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "prpLPropCarSerialno") {
			elements[i].value = index;
		}
	}

	var count = getElementCount("prpLPropSerialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.prpLPropCarSerialno[j].value == index) {
			k++;
			fm.prpLPropSerialNo[j].value = k;
			fm.prpLpropCarDtoLicenseNo[j].value = fm.prpLcarLossLossItemName[index].value;
		}
	}

	return true;
}


/********************
 * 定损
 * 定损换件(增加)
 *******************/

function insertRowTableOfCertainLossComponent(pageCode, dataPageCode, field) {
	var index = 0; //当前table索引
	var elements = null;

	index = getElementOrder(field) - 1;

	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");

	oTBODY.appendChild(oTBODYData.rows[oTBODYData.rows.length - 1].cloneNode(true));
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "prpLcomponentLossItemCode") {
			elements[i].value = index;
		}
	}

	var count = getElementCount("prpLcomponentLossItemCode");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.prpLcomponentLossItemCode[j].value == index) {
			k++;
			fm.prpLcomponentSerialNo[j].value = k;
			fm.prpLcomponentIndId[j].value = k;
			fm.prpLcomponentLicenseNo[j].value = fm.prpLcarLossLossItemName[index].value;
		}
	}

	return true;
}

/********************
 * 定损
 * 定损损失车辆(删除)
 *******************/

function afterDeleteCertainLossCar(obj) {
	var index = 0;
	recentDeletedRowNo = parseInt(getElementOrder(obj));
	if (recentDeletedRowNo > 1) {
		index = parseInt(fm.RelateSerialNo(recentDeletedRowNo - 1).value);
	}
	var oTBODY = document.getElementById("CertainLossCar");
	elements = oTBODY.getElementsByTagName("INPUT");
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "RelateSerialNo") {
			elements[i].value = index;
		}
	}
	var count = getElementCount("prpLverifyLossLossItemCode");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.RelateSerialNo[j].value == index) {
			k++;
			fm.prpLverifyLossLossItemCode[j].value = k;
			var rowNo = getRowNo(fm.prpLverifyLossLossItemCode[j]);
			if (rowNo != 'undfined' && j == rowNo - 1) {
				//车上财产
				var order = document.getElementsByName("CertainLossCarProp")[rowNo - 1];
				var orderCount = order.getElementsByTagName("INPUT");
				for (var a = 1; a < orderCount.length; a++) {
					if (orderCount[a].name == "prpLPropCarSerialno") {
						orderCount[a].value = k;
					}
				}
				//换件项目
				var order = document.getElementsByName("Component")[rowNo - 1];
				var orderCount = order.getElementsByTagName("INPUT");
				for (var a = 1; a < orderCount.length; a++) {
					if (orderCount[a].name == "prpLcomponentLossItemCode") {
						orderCount[a].value = k;
					}
				}
				//修理项目
				var order = document.getElementsByName("RepairFee")[rowNo - 1];
				var orderCount = order.getElementsByTagName("INPUT");
				for (var a = 1; a < orderCount.length; a++) {
					if (orderCount[a].name == "prpLRepairFeeLossItemCode") {
						orderCount[a].value = k;
					}
				}
			}
		}
	}
}

/********************
 * 获取当前行号
 *******************/

function getRowNo(field) {
	var oTBODY = field;
	while (oTBODY.tagName != "TBODY" && oTBODY != null) {
		oTBODY = oTBODY.parentElement;
	}
	var tempElements = oTBODY.getElementsByTagName(field.tagName);
	var tempElementsCount = tempElements.length;
	recentDeletedRowNo = 1;
	for (var i = 0; i < tempElementsCount; i++) {
		if (tempElements[i].name == field.name) {
			recentDeletedRowNo++;
		}
		if (tempElements[i] == field) {
			break;
		}
	}
	return recentDeletedRowNo;
}

/********************
 * 定损
 * 定损损失车辆财产(删除)
 *******************/

function deleteCertainLossCarProp(obj) {
	recentDeletedRowNo = parseInt(getElementOrder(obj));

	var prpLPropCarSerialno = 0;
	prpLPropCarSerialno = parseInt(fm.prpLPropCarSerialno(recentDeletedRowNo - 1).value);

	var rowNo = getRowNo(obj);
	if (isNaN(prpLPropCarSerialno)) {
		var oTBODY = document.getElementsByName("CertainLossCarProp")[rowNo - 1].tBodies.item(0);
		oTBODY.removeChild(oTBODY.rows[0]);
	} else {
		var oTBODY = document.getElementsByName("CertainLossCarProp")[prpLPropCarSerialno].tBodies.item(0);
		order = getRowCurrPageForCertainLossCarProp(obj, "CertainLossCarProp");
		oTBODY.removeChild(oTBODY.rows[order]);
	}

	var count = getElementCount("prpLPropSerialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.prpLPropCarSerialno[j].value == prpLPropCarSerialno) {
			k++;
			fm.prpLPropSerialNo[j].value = k;
		}
	}
	return recentDeletedRowNo - 1;
}

function getRowCurrPageForCertainLossCarProp(field, DataPageCode) {
	var i = 0;
	var name = "";
	var elements;
	var fieldName = "";
	var countLoss = 0;
	var firstIndexLoss = 0;
	var recentRowNo = 0;
	var orderCurrent = 0;
	recentRowNo = parseInt(getElementOrder(field));
	var index = 0;
	index = parseInt(fm.prpLPropCarSerialno(recentRowNo - 1).value);

	elements = getTableElements("CertainLossCar");

	for (i = 0; i < elements.length; i++) {
		if (elements[i].name == "prpLPropCarSerialno") {
			if (parseInt(elements[i].value) == index) {
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

/********************
 * 定损
 * 定损损失车辆修理费用(删除)
 *******************/

function deleteRepairFee(obj) {
	recentDeletedRowNo = parseInt(getElementOrder(obj));

	var index = 0;
	index = parseInt(fm.prpLRepairFeeLossItemCode(recentDeletedRowNo - 1).value);

	var order = recentDeletedRowNo - 1;

	var rowNo = getRowNo(obj);
	if (isNaN(index)) {
		var oTBODY = document.getElementsByName("RepairFee")[rowNo - 1].tBodies.item(0);
		oTBODY.removeChild(oTBODY.rows[0]);
	} else {
		var oTBODY = document.getElementsByName("RepairFee")[index].tBodies.item(0);
		order = getRowCurrPageForCertainLossCarRepairFee(obj, "RepairFee");
		oTBODY.removeChild(oTBODY.rows[order]);
	}

	var count = getElementCount("prpLRepairFeeSerialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.prpLRepairFeeLossItemCode[j].value == index) {
			k++;
			fm.prpLRepairFeeSerialNo[j].value = k;
		}
	}
	return recentDeletedRowNo - 1;
}

function getRowCurrPageForCertainLossCarRepairFee(field, DataPageCode) {
	var i = 0;
	var name = "";
	var elements;
	var fieldName = "";
	var countLoss = 0;
	var firstIndexLoss = 0;
	var recentRowNo = 0;
	var orderCurrent = 0;
	recentRowNo = parseInt(getElementOrder(field));
	var index = 0;
	index = parseInt(fm.prpLRepairFeeLossItemCode(recentRowNo - 1).value);

	elements = getTableElements("CertainLossCar");

	for (i = 0; i < elements.length; i++) {
		if (elements[i].name == "prpLRepairFeeLossItemCode") {
			if (parseInt(elements[i].value) == index) {
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


/********************
 * 定损
 * 定损损失车辆换件(删除)
 *******************/

function deleteComponent(obj) {
	recentDeletedRowNo = parseInt(getElementOrder(obj));

	var index = 0;
	index = parseInt(fm.prpLcomponentLossItemCode(recentDeletedRowNo - 1).value);

	var order = recentDeletedRowNo - 1;

	var rowNo = getRowNo(obj);
	if (isNaN(index)) {
		var oTBODY = document.getElementsByName("Component")[rowNo - 1].tBodies.item(0);
		oTBODY.removeChild(oTBODY.rows[0]);
	} else {
		var oTBODY = document.getElementsByName("Component")[index].tBodies.item(0);
		order = getRowCurrPageForCertainLossCarComponent(obj, "Component");
		oTBODY.removeChild(oTBODY.rows[order]);
	}

	var count = getElementCount("prpLcomponentSerialNo");

	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.prpLcomponentLossItemCode[j].value == index) {
			k++;
			fm.prpLcomponentSerialNo[j].value = k;
		}
	}

	return recentDeletedRowNo - 1;

}

function getRowCurrPageForCertainLossCarComponent(field, DataPageCode) {
	var i = 0;
	var name = "";
	var elements;
	var fieldName = "";
	var countLoss = 0;
	var firstIndexLoss = 0;
	var recentRowNo = 0;
	var orderCurrent = 0;
	recentRowNo = parseInt(getElementOrder(field));
	var index = 0;
	index = parseInt(fm.prpLcomponentLossItemCode(recentRowNo - 1).value);

	elements = getTableElements("CertainLossCar");

	for (i = 0; i < elements.length; i++) {
		if (elements[i].name == "prpLcomponentLossItemCode") {
			if (parseInt(elements[i].value) == index) {
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




/********************
 * 定损
 * 定损损失车辆财产(删除)
 *******************/

function deleteCertainLossProp(obj) {
	var oTBODY = document.getElementById("CertainLossProp").tBodies.item(0);
	var order = getRowNo(obj) - 1;
	oTBODY.removeChild(oTBODY.rows[order - 1]);
	return recentDeletedRowNo - 1;
}

/**
 *@description 简易赔案保存
 *@return      通过返回true,否则返回false
 */

function saveQuickForm(filed, saveType) {
	//reason:1.点提交暂存等按钮屏蔽其他按钮
	//			 2.暂存全部和提交时校验理算报告不允许为空
	disablebutton();
	var registNo = fm.registNo.value;
	var policyNo = fm.policyNo.value;
	var registRPolicyNo = fm.prpLRegistRPolicyNo.value;
	var errorMessage = "";
	var count = getElementCount("prpLlossDtoKindCode");
	var kindcode = "";
	if (saveType == '4') {
		if (count > 1) {
			for (var i = 0; i < count; i++) {
				kindcode = fm.all("prpLlossDtoKindCode")[i].value;
				if (kindcode == ConstantCodes.KINDCODE_D_BZ) {
					var contextCompel = fm.compelPrpLctextContextInnerHTML.value;
					if (contextCompel.length < 1) {
						errorMessage = errorMessage + " 交强险理算报告不允许为空\n";
					}
				} else if (kindcode != '' && kindcode.length > 0) {
					var context = fm.prpLctextContextInnerHTML.value;
					if (context.length < 1) {
						errorMessage = errorMessage + " 商业险理算报告不允许为空\n";
					}
				}
				if (errorMessage.length > 0) {
					break;
				}
			}

		} else {
			errorMessage = i18n.quickCase.underCompensatInfoDamagLeast; //赔付信息下赔付车损/物损信息至少要录入一条!
		}

		if (errorMessage.length > 0) {
			alert(errorMessage);
			undisablebutton();
			return false;
		}
		if (confirm("案件最終賠款金額為：" + fm.compensateSumDutyPaid.value + " ,費用金額為：" + fm.compensateSumNoDutyFee.value + " ,請確認！")) {} else {
			undisablebutton();
			return false;
		}
	}
	if (fm.prpLcheckDamageAreaCode.value == "") {
		alert(i18n.quickCase.dangerAreaNoEmpty); //出险区域不能为空！
		undisablebutton();
	} else if (fm.prpLcheckDamageAreaName.value == "") {
		alert(i18n.quickCase.dangerAreaNoEmpty); //出险区域不能为空！
		undisablebutton();
	} else {
		if ("1" == saveType) {
			fm.action = "/claim/quickCaseSave.do?registNo=" + registNo + "&policyNo=" + policyNo + "&saveType=check" + "&registRPolicyNo=" + registRPolicyNo;
			fm.submit();
			return true;
		}
		if ("2" == saveType) {
			fm.action = "/claim/quickCaseSave.do?registNo=" + registNo + "&policyNo=" + policyNo + "&saveType=certainLoss" + "&registRPolicyNo=" + registRPolicyNo;
			fm.submit();
			return true;
		}
		if ("3" == saveType) {
			fm.action = "/claim/quickCaseSave.do?registNo=" + registNo + "&policyNo=" + policyNo + "&saveType=all" + "&registRPolicyNo=" + registRPolicyNo;
			fm.submit();
			return true;
		}
		if ("4" == saveType) {
			if ('1' == fm.isSpecial.value && (fm.prplregistAdvance.value == '1' || fm.prplregistAdvance.value == '2')) //如果是配置了的机构和险别且为全责垫付状况
			{
				if ('00' == fm.advanceCaseStatus.value || '05' == fm.advanceCaseStatus.value || '10' == fm.advanceCaseStatus.value) {
					alert(i18n.compensate.interactPlatformNotSave); //与平台交互尚未完成，只能暂存！
					undisablebutton();
					return false;
				}
			}
			//判断配件价格是否本地化处理
			//测试本地化价格数据比较少，暂时屏蔽此部分
			if (checkNativePrice()) {
				var sumPaid = fm.compensateSumDutyPaid.value; // 总赔付金额
				if (sumPaid == 0) {
					alert(i18n.quickCase.compensatAmountZeroAdjust); //赔付金额为零，请确认理算是否处理！
					undisablebutton();
					return false;
				}
				if (sumPaid > 3000) {
					alert(i18n.quickCase.CompensatAmountNotAllow); //赔付金额超过3000不允许处理简易赔案！
					undisablebutton();
				} else {
					fm.action = "/claim/quickCaseSave.do?registNo=" + registNo + "&policyNo=" + policyNo + "&saveType=submit" + "&registRPolicyNo=" + registRPolicyNo;
					fm.submit();
					return true;
				}
			} else {
				alert(i18n.quickCase.accessorPriceNotLocalize); //配件价格未进行本地化，不允许处理简易赔案！
				undisablebutton();
				return true;
			}
		}
	}
	undisablebutton();
}


/********************
 * 查勘
 * 查勘损失车辆(增加)
 *******************/

function insertRowTableOfCheckCar(pageCode, dataPageCode, field) {
	var index = 0; //当前table索引
	var elements = null;

	index = getElementOrder(field) - 1;
	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");

	oTBODY.appendChild(oTBODYData.rows[oTBODYData.rows.length - 1].cloneNode(true));
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "checkRelateSerialNo") {
			elements[i].value = index;
		}
	}

	var count = getElementCount("checkRelateSerialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.checkRelateSerialNo[j].value == index) {
			k++;
			fm.checkPrpLthirdPartyDtoSerialNo[j].value = k;
		}
	}

	return true;
}


/********************
 * 查勘
 * 查勘损失车辆损失部位(增加)
 *******************/

function insertRowTableOfCheckCarPart(pageCode, dataPageCode, field) {
	var index = 0; //当前table索引
	var elements = null;
	var licenseNo = "";
	index = getElementOrder(field) - 1;
	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");
	oTBODY.appendChild(oTBODYData.rows[oTBODYData.rows.length - 1].cloneNode(true));
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "checkPrpLthirdCarLossDtoItemNo") {
			elements[i].value = index;
		}
	}

	var count = getElementCount("checkPrpLthirdCarLossDtoItemNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.checkPrpLthirdCarLossDtoItemNo[j].value == index) {
			k++;
			fm.checkPartySerialNo[j].value = k;
			fm.checkPrpLthirdCarLossDtoLicenseNo[j].value = fm.checkPrpLthirdPartyDtoLicenseNo[index].value;
		}
	}
	return true;
}


/********************
 * 查勘
 * 查勘损失车辆财产(增加)
 *******************/

function insertRowTableOfCheckCarProp(pageCode, dataPageCode, field) {
	var index = 0; //当前table索引
	var elements = null;
	index = getElementOrder(field) - 1;
	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");
	oTBODY.appendChild(oTBODYData.rows[oTBODYData.rows.length - 1].cloneNode(true));
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "checkPrpLpropCarserialNo") {
			elements[i].value = index;
		}
	}
	var count = getElementCount("checkPrpLpropCarserialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.checkPrpLpropCarserialNo[j].value == index) {
			k++;
			fm.checkPropCarSerialNo[j].value = k;
			fm.checkPrpLthirdPropCarDtoLicenseNo[j].value = fm.checkPrpLthirdPartyDtoLicenseNo[index].value;
		}
	}
	return true;
}

/********************
 * 查勘
 * 查勘损失财产(增加)
 *******************/

function insertRowTableOfCheckProp(pageCode, dataPageCode, field) {
	var index = 0; //当前table索引
	var elements = null;
	index = getElementOrder(field) - 1;
	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");
	oTBODY.appendChild(oTBODYData.rows[oTBODYData.rows.length - 1].cloneNode(true));
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "checkPropSerialNo") {
			elements[i].value = index;
		}
	}
	var count = getElementCount("checkPrpLpropserialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.checkPropSerialNo[j].value == index) {
			k++;
			fm.checkPrpLpropserialNo[j].value = k;
		}
	}
	return true;
}

/********************
 * 查勘
 * 查勘损失车辆(删除)
 *******************/
//判断定损是否存在该车信息

function isDelete(obj) {
	//reason:简易赔案的查勘中删除车辆正确判断
	var index = 0;
	index = getRowNo(obj) - 1;
	var certainLossCarCount = getElementCount("prpLcarLossLossItemName");
	var hasDelete = true;
	var checkCar = document.getElementsByName("checkPrpLthirdPartyDtoLicenseNo")[index].value;
	if (checkCar != "" && checkCar.length > 0) {
		for (var i = 0; i < certainLossCarCount; i++) {
			var certainLossCar = document.getElementsByName("prpLcarLossLossItemName")[i].value;
			if (checkCar == certainLossCar) {
				hasDelete = false;
			}
		}
		if (hasDelete == false) {
			alert(i18n.quickCase.pleaseRemoveLoss); //请先删除该车定损损失！
		} else {
			deleteRow(obj, 'CheckCar');
		}
	} else {
		deleteRow(obj, 'CheckCar');
	}
}

function afterDeleteCheckCar(obj) {
	recentDeletedRowNo = parseInt(getElementOrder(obj));
	var index = 0;
	if (recentDeletedRowNo > 1) {
		index = parseInt(fm.checkRelateSerialNo(recentDeletedRowNo - 1).value);
	}
	var oTBODY = document.getElementById("CheckCar");
	elements = oTBODY.getElementsByTagName("INPUT");

	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "checkRelateSerialNo") {
			elements[i].value = index;
		}
	}
	var count = getElementCount("checkPrpLthirdPartyDtoSerialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.checkRelateSerialNo[j].value == index) {
			k++;
			fm.checkPrpLthirdPartyDtoSerialNo[j].value = k;
		}
	}
}


/********************
 * 查勘
 * 查勘车辆损失部位(删除)
 *******************/

function deleteCheckCarPart(obj) {
	recentDeletedRowNo = parseInt(getElementOrder(obj));
	var index = 0;
	index = parseInt(fm.checkPrpLthirdCarLossDtoItemNo(recentDeletedRowNo - 1).value);
	var order = recentDeletedRowNo - 1;
	var rowNo = getRowNo(obj);
	if (isNaN(index)) {
		var oTBODY = document.getElementsByName("CheckCarPart")[rowNo - 1].tBodies.item(0);
		oTBODY.removeChild(oTBODY.rows[0]);
	} else {
		var oTBODY = document.getElementsByName("CheckCarPart")[index].tBodies.item(0);
		order = getRowCurrPageForCheckCarPart(obj, "CheckCarPart");
		oTBODY.removeChild(oTBODY.rows[order]);
	}
	var count = getElementCount("checkPrpLthirdCarLossDtoItemNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.checkPrpLthirdCarLossDtoItemNo[j].value == index) {
			k++;
			fm.checkPartySerialNo[j].value = k;
		}
	}
	return recentDeletedRowNo - 1;
}

function getRowCurrPageForCheckCarPart(field, DataPageCode) {
	var i = 0;
	var name = "";
	var elements;
	var fieldName = "";
	var countLoss = 0;
	var firstIndexLoss = 0;
	var recentRowNo = 0;
	var orderCurrent = 0;
	recentRowNo = parseInt(getElementOrder(field));
	var index = 0;
	index = parseInt(fm.checkPrpLthirdCarLossDtoItemNo(recentRowNo - 1).value);
	elements = getTableElements("CheckCarPart");
	for (i = 0; i < elements.length; i++) {
		if (elements[i].name == "checkPrpLthirdCarLossDtoItemNo") {
			if (parseInt(elements[i].value) == index) {
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


/********************
 * 查勘
 * 查勘车辆损失财产(删除)
 *******************/

function deleteCheckCarProp(obj) {
	recentDeletedRowNo = parseInt(getElementOrder(obj));
	var index = 0;
	index = parseInt(fm.checkPrpLpropCarserialNo(recentDeletedRowNo - 1).value);
	var order = recentDeletedRowNo - 1;
	var rowNo = getRowNo(obj);
	if (isNaN(index)) {
		var oTBODY = document.getElementsByName("CheckCarProp")[rowNo - 1].tBodies.item(0);
		oTBODY.removeChild(oTBODY.rows[0]);
	} else {
		var oTBODY = document.getElementsByName("CheckCarProp")[index].tBodies.item(0);
		order = getRowCurrPageForCheckCarPorp(obj, "CheckCarProp");
		oTBODY.removeChild(oTBODY.rows[order]);
	}
	var count = getElementCount("checkPrpLpropCarserialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.checkPrpLpropCarserialNo[j].value == index) {
			k++;
			fm.checkPropCarSerialNo[j].value = k;
		}
	}
	return recentDeletedRowNo - 1;
}

function getRowCurrPageForCheckCarPorp(field, DataPageCode) {
	var i = 0;
	var name = "";
	var elements;
	var fieldName = "";
	var countLoss = 0;
	var firstIndexLoss = 0;
	var recentRowNo = 0;
	var orderCurrent = 0;
	recentRowNo = parseInt(getElementOrder(field));
	var index = 0;
	index = parseInt(fm.checkPrpLpropCarserialNo(recentRowNo - 1).value);
	elements = getTableElements("CheckCar");
	for (i = 0; i < elements.length; i++) {
		if (elements[i].name == "checkPrpLpropCarserialNo") {
			if (parseInt(elements[i].value) == index) {
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


/********************
 * 查勘
 * 查勘损失财产(删除)
 *******************/

function afterDeleteCheckProp(obj) {
	recentDeletedRowNo = parseInt(getElementOrder(obj));
	var index = 0;
	if (recentDeletedRowNo > 1) {
		index = parseInt(fm.checkPrpLpropserialNo(recentDeletedRowNo - 1).value);
	}
	var oTBODY = document.getElementById("CheckProp");
	elements = oTBODY.getElementsByTagName("INPUT");

	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "checkPrpLpropserialNo") {
			elements[i].value = index;
		}
	}
	var count = getElementCount("checkPropSerialNo");
	var k = 0;
	for (var j = 1; j < count; j++) {
		if (fm.checkPrpLpropserialNo[j].value == index) {
			k++;
			fm.checkPropSerialNo[j].value = k;
		}
	}
}


//以下打开零件代码页面

function openCompCodeWin(PageCode, Field) {
	var index = parseInt(getElementOrder(Field)) - 1;
	var partCode = fm.partCode(index).value;
	var previousFlag = "checkPart";
	var pageUrl = "/claim/DAA/quickCase/DAAQuickGetCompCode.jsp?strIndex=" + index + "&partCode=" + partCode + "&previousFlag=" + previousFlag;
	window.open(pageUrl, "openCompCodeWin", "resizable=0,scrollbars,dependent,alwaysRaised,width=230,height=450");
}
//以下打开零件代码页面

function openPrplRepairFeeCompWin(PageCode, Field) {
	var index = parseInt(getElementOrder(Field)) - 1;
	var partCode = fm.partCode(index).value;
	var previousFlag = "repairFee"; //此标志位表示此页面来自修理费用清单模块
	var pageUrl = "/claim/DAA/quickCase/DAAQuickGetCompCode.jsp?strIndex=" + index + "&partCode=" + partCode + "&previousFlag=" + previousFlag;
	window.open(pageUrl, "openPrplRepairFeeCompWin", "resizable=0,scrollbars,dependent,alwaysRaised,width=230,height=450");
}

//取得零件代码与名称

function getCompCode(field) {
	var compCodeName = field;
	var index = compCodeName.indexOf("-");
	var compCode = compCodeName.substring(0, index);
	var compName = compCodeName.substring(index + 1);
	var previousFlag = fm.txtPreviousFlag.value;
	var index1 = fm.txtIndex.value;
	if (previousFlag == "repairFee") {
		window.opener.fm.prpLrepairFeeCompName[index1].value = compName;
		window.opener.fm.prpLrepairFeeCompCode[index1].value = compCode;
		window.close();
	}
	if (previousFlag == "checkPart") {
		window.opener.fm.checkPrpLthirdCarLossDtoCompName[index1].value = compName;
		window.opener.fm.checkPrpLthirdCarLossDtoCompCode[index1].value = compCode;
		window.close();
	}

}

//收集查勘车辆损失信息

function getCheckDataList() {
	var checkDataList;
	checkDataList = new Array();
	var checkData;
	for (i = 1; i <= getRowsCount("CheckCar"); i++) {
		if (fm.checkCarAdd[i].value == "Y") {
			checkData = new Array();
			checkData.LicenseNo = fm.checkPrpLthirdPartyDtoLicenseNo[i].value;
			checkData.checkPrpLthirdPartyDtoModelCode = fm.checkPrpLthirdPartyDtoModelCode[i].value;
			checkData.checkPrpLthirdPartyDtoBrandName = fm.checkPrpLthirdPartyDtoBrandName[i].value;
			fm.checkCarAdd[i].value = "N";
			checkDataList[checkDataList.length] = checkData;
		}
	}
	return checkDataList;
}


//定损车辆信息赋值

function setCertainLossDataList() {
	var CheckDataList = new Array();
	CheckDataList = getCheckDataList();
	var CertainLossDataList = 0;
	CertainLossDataList = getCertainLossDataList();
	var CertainLossDataAll = getRowsCount("CertainLossCar");
	var index = 1;
	var CertainLossList = CertainLossDataList.length;
	var deleteIndex = (CertainLossDataAll) - (CertainLossDataList.length);
	index = CertainLossDataAll + 1;
	for (var i = 0; i < CheckDataList.length; i++) {
		insertRowTableOfCertainLossCar('CertainLossCar', 'CertainLossCar_Data', null, index);
		fm.prpLcarLossLossItemName[index].value = CheckDataList[i].LicenseNo;
		fm.prpLcarLossBrandName[index].value = CheckDataList[i].checkPrpLthirdPartyDtoBrandName;
		fm.prpLcarLossModelCode[index].value = CheckDataList[i].checkPrpLthirdPartyDtoModelCode;
		index++;
	}

}

//收集定损车辆信息

function getCertainLossDataList() {
	var CertainLossDataList = new Array();
	var CertainLossData;
	var index = 0;
	for (var i = 1; i <= getRowsCount("CertainLossCar"); i++) {
		if (fm.certainLossAdd[i].value == "Y") {
			CertainLossData = new Array();
			CertainLossData.LicenseNo = fm.prpLcarLossLossItemName[i].value;
			CertainLossDataList[CertainLossDataList.length] = CertainLossData;
		}
	}
	return CertainLossDataList;
}


//刷新定损车辆信息

function flashCertainLossList() {
	setCertainLossDataList();
}

//得到一页的多行纪录的记录数
//页名称

function getRowsCount(PageCode) {
	var oTBODY = document.all(PageCode).tBodies.item(0);
	var intCount = oTBODY.rows.length;
	return intCount;
}


/**
 * 从配件系统获取配件信息
 */

function getFittingsInfo(queryType, obj) {
	var selectedCarFittings = "";
	var k = 1;
	//取得车辆序号
	var carSerialNo = parseInt(getElementOrder(obj)) - 1;
	if (fm.prpLcarLossRepairFactoryCode[carSerialNo].value == "") {
		alert(i18n.quickCase.pleaseFirstChooseType); //请先选择修理厂类型！
	} else {
		var componentDataList = getComponentDataList(carSerialNo);
		if (componentDataList.length > 0) {
			for (var i = 0; i < componentDataList.length; i++) {
				if (k == 1) {
					selectedCarFittings = componentDataList[i].prpLcomponentKindCode + "^" + componentDataList[i].prpLcomponentKindName + "^" + componentDataList[i].prpLcomponentCompCode + "^" + componentDataList[i].prpLcomponentIndId + "^" + componentDataList[i].prpLcomponentOriginalId + "^" + componentDataList[i].prpLcomponentMaterialFee + "^" + componentDataList[i].prpLcomponentQuantity + "^" + componentDataList[i].prpLcomponentSumDefLoss + "^" + componentDataList[i].prpLcomponentRemark + "^" + componentDataList[i].prpLcomponentPriceType; //增加价格类型
					k++;
					continue;
				}
				if (k > 1) {
					selectedCarFittings += "≡" + componentDataList[i].prpLcomponentKindCode + "^" //[0] //修改用"_",连接选择其他的连接方式"≡"
					+ componentDataList[i].prpLcomponentKindName + "^" //[1]
					+ componentDataList[i].prpLcomponentCompCode + "^" //[2]
					+ componentDataList[i].prpLcomponentIndId + "^" //[3]                       
					+ componentDataList[i].prpLcomponentOriginalId + "^" //[4]
					+ componentDataList[i].prpLcomponentMaterialFee + "^" //[5]                          
					+ componentDataList[i].prpLcomponentQuantity + "^" //[6]                                                   
					+ componentDataList[i].prpLcomponentSumDefLoss + "^" //[7]      
					+ componentDataList[i].prpLcomponentRemark + "^" //[8]
					+ componentDataList[i].prpLcomponentPriceType; //增加价格类型 //[9]
					k++;
				}
			}
		}
		fm.selectCarFittings.value = selectedCarFittings;
		//reason: 当有多辆车损时，ShowPriceFlag和LocalAreaCode应从数组中取
		if (fm.ShowPriceFlag[0] == undefined) {
			ShowPriceFlag = fm.ShowPriceFlag.value;
		} else {
			ShowPriceFlag = fm.ShowPriceFlag[0].value;
		}
		if (fm.LocalAreaCode[0] == undefined) {
			LocalAreaCode = fm.LocalAreaCode.value;
		} else {
			LocalAreaCode = fm.LocalAreaCode[0].value;
		}
		strURL = "/claim/DAA/certainLoss/openFittingsSystemBefore.jsp?queryType=" + queryType + "&registNo=" + fm.registNo.value + "&policyNo=" + fm.policyNo.value + "&insurant=123" + "&lossItemCode=" + carSerialNo + "&licenseNo=" + fm.prpLcarLossLossItemName[carSerialNo].value + "&repairfactorytype=" + fm.prpLcarLossRepairFactoryCode[carSerialNo].value + "&showpriceflag=" + ShowPriceFlag //价格权限
		+ "&systemAreaCode=11" + "&localAreaCode=" + LocalAreaCode //本地价地区（填写当前用户所在的分公司代码）
		+ "&requestType=PART" //请求类型，是全量还是增量 新增加的
		+ "&vehCode=" + fm.prpLcarLossModelCode[carSerialNo].value + "&vehName=" + fm.prpLcarLossBrandName[carSerialNo].value + "&callType=quickCase";
		var newWindow = window.open(strURL, "打开配件系统", 'width=1010,height=670,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		newWindow.focus();
	}
}

//广信返回信息赋值给页面

function quickCaseInsertCarLossFittings(arrData, lossItemCode) {
	var componentDataList = getComponentDataList(lossItemCode);
	var kindCode = "";
	var kindName = "";
	if (fm.prpLcarLossInsureCarFlag[lossItemCode].value == "1") {
		kindCode = RISKINFO.KINDCODE_D_A;
		kindName = "车辆损失保险";
	} else {
		kindCode = "B";
		kindName = "第三者责任保险";
	}
	for (var i = 0; i < arrData.length; i++) {
		insertRowTableOfCertainLossComponent('Component', 'Component_Data', fm.buttoncomponent[lossItemCode]);
		var allRow = getElementCount("prpLcomponentLossItemCode");
		var arrRow = new Array();
		arrRow = arrData[i];
		for (var k = 0; k < allRow; k++) {
			if (fm.prpLcomponentLossItemCode[k].value == lossItemCode && fm.prpLcomponentCompCode[k].value == "") {
				fm.prpLcomponentKindCode[k].value = kindCode;
				fm.prpLcomponentKindName[k].value = kindName;
				fm.prpLcomponentCompCode[k].value = arrRow.prpLcomponentCompCode;
				fm.prpLcomponentCompName[k].value = arrRow.prpLcomponentCompName;
				fm.prpLcomponentMaterialFee[k].value = arrRow.prpLcomponentMaterialFee;
				fm.prpLcomponentQuantity[k].value = arrRow.prpLcomponentQuantity;
				fm.prpLcomponentOriginalId[k].value = arrRow.prpLcomponentOriginalId;
				fm.prpLcomponentIndId[k].value = arrRow.prpLcomponentIndId;
				fm.prpLcomponentSys4SPrice[k].value = arrRow.prpLcomponentSys4SPrice;
				fm.prpLcomponentSysMarketPrice[k].value = arrRow.prpLcomponentSysMarketPrice;
				fm.prpLcomponentSysMatchPrice[k].value = arrRow.prpLcomponentSysMatchPrice;
				fm.prpLcomponentNative4SPrice[k].value = arrRow.prpLcomponentNative4SPrice;
				fm.prpLcomponentNativeMarketPrice[k].value = arrRow.prpLcomponentNativeMarketPrice;
				fm.prpLcomponentNativeMatchPrice[k].value = arrRow.prpLcomponentNativeMatchPrice;
				fm.prpLcomponentRemark[k].value = arrRow.prpLcomponentRemark;
				fm.prpLcomponentFlag[k].value = arrRow.prpLcomponentFlag;
				fm.prpLcomponentPriceType[k].value = arrRow.prpLcomponentPriceType;
				fm.prpLcomponentSumDefLoss[k].value = arrRow.prpLcomponentSumDefLoss;
			}
		}
	}
}


function getComponentDataList(carRows) {
	var ComponentDataList = new Array();
	var ComponentList;
	var allRow = getElementCount("prpLcomponentLossItemCode");
	if (allRow > 1) {
		for (var i = 0; i < allRow; i++) {
			if (fm.prpLcomponentLossItemCode[i].value == carRows) {
				ComponentList = new Array();
				ComponentList.prpLcomponentCompCode = fm.prpLcomponentCompCode[i].value;
				ComponentList.prpLcomponentCompName = fm.prpLcomponentCompName[i].value;
				ComponentList.prpLcomponentMaterialFee = fm.prpLcomponentMaterialFee[i].value;
				ComponentList.prpLcomponentQuantity = fm.prpLcomponentQuantity[i].value;
				ComponentList.prpLcomponentOriginalId = fm.prpLcomponentOriginalId[i].value;
				ComponentList.prpLcomponentIndId = fm.prpLcomponentIndId[i].value;
				ComponentList.prpLcomponentSys4SPrice = fm.prpLcomponentSys4SPrice[i].value;
				ComponentList.prpLcomponentSysMarketPrice = fm.prpLcomponentSysMarketPrice[i].value;
				ComponentList.prpLcomponentSysMatchPrice = fm.prpLcomponentSysMatchPrice[i].value;
				ComponentList.prpLcomponentNative4SPrice = fm.prpLcomponentNative4SPrice[i].value;
				ComponentList.prpLcomponentNativeMarketPrice = fm.prpLcomponentNativeMarketPrice[i].value;
				ComponentList.prpLcomponentNativeMatchPrice = fm.prpLcomponentNativeMatchPrice[i].value;
				ComponentList.prpLcomponentSumDefLoss = fm.prpLcomponentSumDefLoss[i].value;
				ComponentList.prpLcomponentRemark = fm.prpLcomponentRemark[i].value;
				ComponentList.prpLcomponentFlag = fm.prpLcomponentFlag[i].value;
				ComponentDataList[ComponentDataList.length] = ComponentList;
			}
		}
	}
	return ComponentDataList;
}

//刷新理算赔付信息

function flashCompensateList() {
	setCompensateList();
}
//理算赔付信息取值

function setCompensateList() {

	var CertainLossList = new Array();
	CertainLossList = getCertainLossList();
	CarPropLossList = getCarPropLossList();
	CertainPropList = getCertainPropList();
	var CompensateList = 0;
	var CompensateAll = getRowsCount("CompensateLoss");
	var index = 0;
	var i = 0;
	if (fm.certainLossDataAddFlag.value == 'Y') {
		for (var j = 0; j < CompensateAll; j++) {
			if (fm.all("prpLlossDtoKindCode")[j + 1].value != ConstantCodes.KINDCODE_D_BZ) {
				deleteRow(fm.all("buttonCompensateLossDelete")[j + 1], 'CompensateLoss');
				j--;
			} else if (fm.all("prpLlossDtoKindCode")[j + 1].value == ConstantCodes.KINDCODE_D_BZ) {

			}
			CompensateAll = getRowsCount("CompensateLoss");

		}
		CompensateAll = getRowsCount("CompensateLoss");
		index = CompensateAll + 1;
		for (var i = 0; i < CertainLossList.length; i++) {
			insertRow('CompensateLoss');
			fm.prpLlossDtoKindCode[index].value = CertainLossList[i].kindCode;
			fm.prpLlossDtoKindName[index].value = CertainLossList[i].kindName;
			fm.licenseNo[index].value = CertainLossList[i].LicenseNo;
			fm.prpLlossDtoLossName[index].value = CertainLossList[i].certainPrpLcarLossBrandName;
			fm.prpLlossDtoSumLoss[index].value = CertainLossList[i].certainLoss;
			fm.prpLlossDtoSumDefPay[index].value = CertainLossList[i].certainLoss;
			inputControl(fm.prpLlossDtoKindCode[index]);
			checkExcept4();
			insertRow2(fm.prpLlossDtoKindCode[index]);
			inputControl2(fm.prpLlossDtoSumLoss[index]);
			calRealpay(fm.prpLlossDtoSumLoss[index]);
			index++;
		}
		for (var n = 0; n < CarPropLossList.length; n++) {
			insertRow('CompensateLoss');
			fm.prpLlossDtoKindCode[index].value = CarPropLossList[n].kindCode;
			fm.prpLlossDtoKindName[index].value = CarPropLossList[n].kindName;
			fm.licenseNo[index].value = CarPropLossList[n].licenseNo;
			fm.prpLlossDtoLossName[index].value = CarPropLossList[n].lossItemName;
			fm.prpLlossDtoSumLoss[index].value = CarPropLossList[n].sumLoss;
			fm.prpLlossDtoSumDefPay[index].value = CarPropLossList[n].sumDefLoss;
			inputControl(fm.prpLlossDtoKindCode[index]);
			checkExcept4();
			insertRow2(fm.prpLlossDtoKindCode[index]);
			inputControl2(fm.prpLlossDtoSumLoss[index]);
			calRealpay(fm.prpLlossDtoSumLoss[index]);
			index++;
		}
		for (var q = 0; q < CertainPropList.length; q++) {
			insertRow('CompensateLoss');
			fm.prpLlossDtoKindCode[index].value = CertainPropList[q].kindCode;
			fm.prpLlossDtoKindName[index].value = CertainPropList[q].kindName;
			fm.prpLlossDtoLossName[index].value = CertainPropList[q].propLossItemName;
			fm.prpLlossDtoSumLoss[index].value = CertainPropList[q].sumLoss;
			fm.prpLlossDtoSumDefPay[index].value = CertainPropList[q].sumDefLoss;
			inputControl(fm.prpLlossDtoKindCode[index]);
			checkExcept4();
			insertRow2(fm.prpLlossDtoKindCode[index]);
			inputControl2(fm.prpLlossDtoSumLoss[index]);
			calRealpay(fm.prpLlossDtoSumLoss[index]);
			index++;
		}
	}
	fm.certainLossDataAddFlag.value = 'N';
}

//收集理算信息

function getCompensateList() {
	var CompensateList = new Array();
	var CompensateData;
	var index = 0;
	for (var i = 1; i <= getRowsCount("CompensateLoss"); i++) {
		if (fm.compensateAdd[i].value == "Y") {
			CompensateData = new Array();
			CompensateData.LicenseNo = fm.licenseNo[i].value;
			CompensateData.certainPrpLlossDtoLossName = fm.prpLlossDtoLossName[i].value;
			CompensateData.certainLoss = fm.prpLlossDtoSumLoss[i].value;
			CompensateList[CompensateList.length] = CompensateData;
		}
	}
	return CompensateList;
}

//理算收集定损车辆信息

function getCertainLossList() {
	var CertainLossList = new Array();
	var ItemKindList = new Array();
	var CertainLossData;
	var index = 0;
	for (var i = 1; i <= getRowsCount("CertainLossCar"); i++) {
		CertainLossData = new Array();
		ItemKindList = getItemKindCode(fm.prpLverifyLossLossItemCode[i]);
		if (ItemKindList.length > 0) {
			CertainLossData.kindCode = ItemKindList[0].kindCode;
			CertainLossData.kindName = ItemKindList[0].kindName;
			CertainLossData.LicenseNo = fm.prpLcarLossLossItemName[i].value;
			CertainLossData.certainPrpLcarLossBrandName = fm.prpLcarLossBrandName[i].value;
			CertainLossData.certainLoss = fm.prpLcarLossSumCertainLoss[i].value;
			CertainLossList[CertainLossList.length] = CertainLossData;
		}
	}
	return CertainLossList;
}

//理算搜集定损标的车财产信息

function getCarPropLossList() {
	var CarPropLossList = new Array();
	var CarPropLossData;
	for (var i = 1; i < (fm.prpLpropCarDtoKindCode.length); i++) {
		CarPropLossData = new Array();
		CarPropLossData.kindCode = fm.prpLpropCarDtoKindCode[i].value;
		CarPropLossData.kindName = fm.prpLpropCarDtoKindName[i].value;
		CarPropLossData.licenseNo = fm.prpLpropCarDtoLicenseNo[i].value;
		CarPropLossData.lossItemName = fm.prpLpropCarDtoLossItemName[i].value;
		CarPropLossData.sumLoss = fm.prpLpropCarDtoSumLoss[i].value;
		CarPropLossData.sumDefLoss = fm.prpLpropCarDtoSumDefLoss[i].value;
		CarPropLossList[CarPropLossList.length] = CarPropLossData;
	}
	return CarPropLossList;
}

//理算搜集定损车外财产信息

function getCertainPropList() {
	var CertainPropList = new Array();
	var CertainPropData;
	for (var i = 1; i < getRowsCount("CertainLossProp") + 1; i++) {
		CertainPropData = new Array();
		CertainPropData.kindCode = fm.prpLpropKindCode[i].value;
		CertainPropData.kindName = fm.prpLpropKindName[i].value;
		CertainPropData.propLossItemName = fm.prpLpropLossItemName[i].value;
		CertainPropData.sumLoss = fm.prpLpropSumLoss[i].value;
		CertainPropData.sumDefLoss = fm.prpLpropSumDefLoss[i].value;
		CertainPropList[CertainPropList.length] = CertainPropData;
	}
	return CertainPropList;
}

//获取车辆损失险别

function getItemKindCode(field) {
	var lossItemCode = field.value;
	var ItemKindList = new Array();
	var ItemKindData;
	var lossItemCode = field.value;
	for (var i = 0; i < fm.prpLrepairFeeKindCode.length; i++) {
		ItemKindData = new Array();
		if (fm.prpLRepairFeeLossItemCode[i].value == lossItemCode && fm.prpLrepairFeeKindCode[i].value != "") {
			ItemKindData.kindCode = fm.prpLrepairFeeKindCode[i].value;
			ItemKindData.kindName = fm.prpLrepairFeeKindName[i].value;
			ItemKindList[ItemKindList.length] = ItemKindData;
			return ItemKindList;
		}
	}
	for (var i = 0; i < fm.prpLcomponentKindCode.length; i++) {
		ItemKindData = new Array();
		if (fm.prpLcomponentLossItemCode[i].value == lossItemCode && fm.prpLcomponentKindCode[i].value != "") {
			ItemKindData.kindCode = fm.prpLcomponentKindCode[i].value;
			ItemKindData.kindName = fm.prpLcomponentKindName[i].value;
			ItemKindList[ItemKindList.length] = ItemKindData;
			return ItemKindList;
		}
	}
	return ItemKindList;
}

//计算工时费合计

function setSumRepairFee(obj) {
	var index = 0;
	var rowNo = parseInt(getElementOrder(obj));
	if (rowNo == 1) {
		index = 1;
		var sumCertainLoss = round(fm.SumDefLoss2[index].value, 2);
		fm.SumDefLoss1[index].value = 0.0;
		fm.prpLcarLossSumCertainLoss[index].value = point(round(sumCertainLoss, 0), 0);
	} else {
		if (rowNo > 1) {
			index = parseInt(fm.prpLRepairFeeLossItemCode(rowNo - 1).value);
		}
		var allRowNo = getElementCount("prpLRepairFeeLossItemCode");

		var strSumRepairFee = "0"; //当前车辆总修理工时费
		var strRepairFee; //当前车辆单条修理费
		var strmanHour; //工时
		var strmanHourUnitPrice; //工时单价
		var sumRepairFee;
		for (var i = 0; i < allRowNo; i++) {
			if (fm.prpLRepairFeeLossItemCode[i].value == index) {
				if (fm.prpLrepairFeeManHour[i].value == null || fm.prpLrepairFeeManHour[i].value == "") {
					strmanHour = "0";
				} else {
					strmanHour = fm.prpLrepairFeeManHour[i].value;
				}
				if (fm.prpLrepairFeeManHourUnitPrice[i].value == null || fm.prpLrepairFeeManHourUnitPrice[i].value == "") {
					strmanHourUnitPrice = "0";
				} else {
					strmanHourUnitPrice = fm.prpLrepairFeeManHourUnitPrice[i].value;
				}
				strRepairFee = round(strmanHour, 2) * round(strmanHourUnitPrice, 2);
				fm.prpLrepairFeeSumDefLoss[i].value = point(round(strRepairFee, 0), 0);
			}

			strSumRepairFee = round(strSumRepairFee, 2) + round(strRepairFee, 2);
			strRepairFee = "0";
		}
		fm.SumDefLoss1[index].value = point(round(strSumRepairFee, 0), 0);
		var sumCertainLoss = round(fm.SumDefLoss2[index].value, 2) + round(fm.SumDefLoss1[index].value, 2);
		fm.prpLcarLossSumCertainLoss[index].value = point(round(sumCertainLoss, 0), 0);
		fm.certainLossDataAddFlag.value = 'Y';
		return sumRepairFee;
	}
}

//换件费合计

function setSumDefLoss(obj) {
	var index = 0;
	fm.certainLossDataAddFlag.value = 'Y';
	var rowNo = parseInt(getElementOrder(obj));
	if (rowNo == 1) {
		index = 1;
		var sumCertainLoss = round(fm.SumDefLoss1[index].value, 2);
		fm.SumDefLoss2[index].value = 0.0;
		fm.prpLcarLossSumCertainLoss[index].value = point(round(sumCertainLoss, 0), 0);
	} else {
		if (rowNo > 1) {
			index = parseInt(fm.prpLcomponentLossItemCode(rowNo - 1).value);
		}
		var allRowNo = getElementCount("prpLcomponentLossItemCode");
		var strSumDefLoss2 = "0"; //当前车辆总换件费
		var strSumDefLoss = "0"; //当前车辆单条换件费小计
		var strMaterialFee = "0"; //定损单价
		var strQuantity = "0"; //数量
		var strRestFee = "0"; //残值
		var strSumRest = "0"; //总残值
		var strSumRestTemp = "0";
		for (var i = 0; i < allRowNo; i++) {
			if (fm.prpLcomponentLossItemCode[i].value == index) {
				if (fm.prpLcomponentMaterialFee[i].value == null || fm.prpLcomponentMaterialFee[i].value == "") {
					strMaterialFee = "0";
				} else {
					strMaterialFee = fm.prpLcomponentMaterialFee[i].value;
				}
				if (fm.prpLcomponentQuantity[i].value == null || fm.prpLcomponentQuantity[i].value == "") {
					strQuantity = "0";
				} else {
					strQuantity = fm.prpLcomponentQuantity[i].value;
				}
				if (fm.prpLcomponentRestFee[i].value == null || fm.prpLcomponentRestFee[i].value == "") {
					strRestFee = "0";
				} else {
					strRestFee = fm.prpLcomponentRestFee[i].value;
				}
				strSumDefLoss = round(strMaterialFee, 2) * round(strQuantity, 2) - round(strRestFee, 2);
				strSumRestTemp =
					fm.prpLcomponentSumDefLoss[i].value = point(round(strSumDefLoss, 0), 0);
			}
			strSumRest = round(strRestFee, 2) + round(strSumRest, 2);
			strSumDefLoss2 = round(strSumDefLoss2, 2) + round(strSumDefLoss, 2);
		}
		fm.prpLcarLossSumRest[index].value = point(round(strSumRest, 0), 0);
		fm.SumDefLoss2[index].value = point(round(strSumDefLoss2, 0), 0);
		var sumCertainLoss = round(fm.SumDefLoss2[index].value, 2) + round(fm.SumDefLoss1[index].value, 2);
		fm.prpLcarLossSumCertainLoss[index].value = point(round(sumCertainLoss, 0), 0);
		return strSumDefLoss2;
	}
}

/**
 * 功能：将输入域变成只读，同时将CSS的属性变成只读
 * return true/false
 */

function readonlyCheckInput() {
	var testStr = "";
	var tempElements = null;

	for (i = 0; i < oMPC.all("tabMain")[0].all.length; i++) {
		//alert(document.all(i).tagName);
		if (oMPC.all("tabMain")[0].all(i).tagName == "INPUT") {
			tempElements = oMPC.all("tabMain")[0].all(i);
			//将输入域变为只读
			if (tempElements.type == "text") {
				if (tempElements.name != "prpLcompensateIndemnityDutyRate" && tempElements.name != "displayInputInfo" && tempElements.name != "displayGetFromPlatForm" && tempElements.name != "displayUpload" && tempElements.name != "displayGetConfirm") {
					tempElements.style.fontSize = "11pt";
					tempElements.style.borderTop = "none";
					tempElements.style.borderBottom = "none";
					tempElements.style.borderRight = "none";
					tempElements.style.borderLeft = "none";
					//tempElements.style.width="80%";
					tempElements.style.color = "#000000";
					tempElements.style.backgroundColor = "#F4F9FF";
					tempElements.readOnly = true;
				}
			}
			//将输入域变为只读
			if (tempElements.type == "radio") {
				tempElements.disabled = true;
			}

		}
		//将选择域变为只读

		if (document.all(i).tagName == "SELECT") {
			tempElements = document.all(i);
			if (tempElements.name != "indemnityDuty" && tempElements.name != "prplregistAdvance") {
				tempElements.disabled = true;
			}
		}
		//将选择域变为只读
		if (document.all(i).tagName == "TEXTAREA") {
			tempElements = document.all(i);
			tempElements.readOnly = true;
			tempElements.style.backgroundColor = "RGB(247,247,247)";
		}

	}

}

/**
 * 功能： 按钮域的按钮域变成可读
 * @param tableID 含有按钮的表ID
 */

function disabledCheckButton(tableId) {
	var elements = getTableElements(tableId);

	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "buttonBack") { //如果名字为buttonBack则继续
			continue;
		}
		//将button设成不可用
		if (elements[i].type == "button") {
			elements[i].disabled = true;
		}
		//将submit设成不可用
		if (elements[i].type == "submit") {
			elements[i].disabled = true;
		}
		//将reset设成不可用
		if (elements[i].type == "reset") {
			elements[i].disabled = true;
		}

	}
}


function backWardPolicy() {
	//测试中没有用到这个方法
	var BizNo;
	var RiskCode;
	if (fm.policyNo.value == "" || fm.policyNo.value == null) {
		alert(i18n.quickCase.caseNoCommercial); //该案件不存在商业险保单！
	} else {
		BizNo = fm.policyNo.value;
		RiskCode = fm.riskCode.value;
		var SHOWTYPE = "SHOW";
		var damageDate = fm.prpLcheckDamageStartDate.value;
		//		var vURL = '/prpall/' + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate;
		var vURL = '/claim/pages/common/pub/PolicyShowCenter.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate;
		window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}


function backWardCompelPolicy() {
	var BizNo;
	var RiskCode;
	if (fm.prpLRegistRPolicyNo.value == "" || fm.policyNo.value == null) {
		alert(i18n.quickCase.caseNoInsurancePolicy); //该案件不存在交强险保单！
	} else {
		BizNo = fm.prpLRegistRPolicyNo.value;
		RiskCode = fm.compelRiskCode.value;
		var SHOWTYPE = "SHOW";
		var damageDate = fm.prpLcheckDamageStartDate.value;
		var vURL = '/prpall/' + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate;
		window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

//定损单价不能大於本地报价

function checkSumDefLoss(obj) {
	var rowCount = getElementOrder(obj) - 1;
	var priceType = fm.prpLcomponentPriceType[rowCount].value;
	var materialFee = fm.prpLcomponentMaterialFee[rowCount].value;
	var compCode = fm.prpLcomponentCompCode[rowCount].value;
	if (compCode != "" && compCode != null && compCode != "00") {
		if (priceType == "S") { //专修价
			if (materialFee > fm.prpLcomponentNative4SPrice[rowCount].value) {
				alert(i18n.certainLoss.noFeePricePrice); //定损单价不能大於本地报价
				fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNative4SPrice[rowCount].value;
			} else {
				setSumDefLoss(obj);
			}
		}
		if (priceType == "M") { //市场价
			if (materialFee > fm.prpLcomponentNativeMarketPrice[rowCount].value) {
				alert(i18n.certainLoss.noFeePricePrice); //定损单价不能大於本地报价
				fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNativeMarketPrice[rowCount].value;
			} else {
				setSumDefLoss(obj);
			}
		}
		if (priceType == "O") { //副厂价
			if (materialFee > fm.prpLcomponentNativeMatchPrice[rowCount].value) {
				alert(i18n.certainLoss.noFeePricePrice); //定损单价不能大於本地报价
				fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNativeMatchPrice[rowCount].value;
			} else {
				setSumDefLoss(obj);
			}
		}
	}
}

//更改价格类型後，重新给定损单价赋值

function changePriceType(obj) {
	var priceType = obj.value;
	var rowCount = getElementOrder(obj) - 1;
	if (priceType == "S") { //专修价
		fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNative4SPrice[rowCount].value;
	}
	if (priceType == "M") { //市场价
		fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNativeMarketPrice[rowCount].value;
	}
	if (priceType == "O") { //副厂价
		fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNativeMatchPrice[rowCount].value;
	}
}
//判断配件价格时否本地化处理

function checkNativePrice() {
	var count = getElementCount("prpLcomponentPriceType");
	for (var i = 1; i < count; i++) {
		var priceType = fm.prpLcomponentPriceType[i].value;
		var native4SPrice = fm.prpLcomponentNative4SPrice[i].value;
		var nativeMarketPrice = fm.prpLcomponentNativeMarketPrice[i].value;
		var nativeMatchPrice = fm.prpLcomponentNativeMatchPrice[i].value;
		if (priceType == "S" && native4SPrice == "0.0") { //专修价
			return false;
		}
		if (priceType == "M" && nativeMarketPrice == "0.0") { //市场价
			return false;
		}
		if (priceType == "O" && nativeMatchPrice == "0.0") { //副厂价
			return false;
		}
	}
	return true;
}

//清除定损单价

function changeRepairFactoryCode(obj) {
	if (confirm("确认是否更新全部定损单价！")) {
		var carSerialNo = parseInt(getElementOrder(obj)) - 1;
		var factoryCode = fm.prpLcarLossRepairFactoryCode[carSerialNo].value;
		if (factoryCode == "01") { //4s店
			var count = getElementCount("prpLcomponentNative4SPrice");
			for (var index = 1; index < count; index++) {
				if (fm.prpLcomponentLossItemCode[index].value == carSerialNo) {
					fm.prpLcomponentPriceType[index].value = "S";
					fm.prpLcomponentMaterialFee[index].value = fm.prpLcomponentNative4SPrice[index].value;
					setSumDefLoss(fm.prpLcomponentPriceType[index]);
				}
			}
		}
		if (factoryCode == "02") { //一类店
			var count = getElementCount("prpLcomponentNativeMarketPrice");
			for (var index = 1; index < count; index++) {
				if (fm.prpLcomponentLossItemCode[index].value == carSerialNo) {
					fm.prpLcomponentPriceType[index].value = "M";
					fm.prpLcomponentMaterialFee[index].value = fm.prpLcomponentNativeMarketPrice[index].value;
					setSumDefLoss(fm.prpLcomponentPriceType[index]);
				}
			}
		}
		if (factoryCode == "03") { //二类店
			var count = getElementCount("prpLcomponentNativeMarketPrice");
			for (var index = 1; index < count; index++) {
				if (fm.prpLcomponentLossItemCode[index].value == carSerialNo) {
					fm.prpLcomponentPriceType[index].value = "M";
					fm.prpLcomponentMaterialFee[index].value = fm.prpLcomponentNativeMarketPrice[index].value;
					setSumDefLoss(fm.prpLcomponentPriceType[index]);
				}
			}
		}
		if (factoryCode == "04") { //其他
			var count = getElementCount("prpLcomponentNativeMatchPrice");
			for (var index = 1; index < count; index++) {
				if (fm.prpLcomponentLossItemCode[index].value == carSerialNo) {
					fm.prpLcomponentPriceType[index].value = "O";
					fm.prpLcomponentMaterialFee[index].value = fm.prpLcomponentNativeMatchPrice[index].value;
					setSumDefLoss(fm.prpLcomponentPriceType[index]);
				}
			}
		}
	}
}
//险别过滤

function kindCodeSelect(obj, field) {
	var index = parseInt(getElementOrder(obj));
	if (field == "prpLcomponentLossItemCode") {
		var carCount = fm.prpLcomponentLossItemCode[index - 1].value;
	}
	if (field == "prpLRepairFeeLossItemCode") {
		var carCount = fm.prpLRepairFeeLossItemCode[index - 1].value;
	}
	if (field == "prpLPropCarSerialno") {
		var carCount = fm.prpLPropCarSerialno[index - 1].value;
	}
	var carType = fm.prpLcarLossInsureCarFlag[carCount].value;
	if (carType == "1") {
		code_CodeSelect(obj, 'kindCodeInsureCar', '0,1', 'Y', 'Y', fm.policyNo.value + '|' + fm.prpLRegistRPolicyNo.value);
	} else {
		code_CodeSelect(obj, 'kindCodeThirdCar', '0,1', 'Y', 'Y', fm.policyNo.value + '|' + fm.prpLRegistRPolicyNo.value);
	}
}

function kindNameSelect(obj, field) {
	var index = parseInt(getElementOrder(obj));
	if (field == "prpLcomponentLossItemCode") {
		var carCount = fm.prpLcomponentLossItemCode[index - 1].value;
	}
	if (field == "prpLRepairFeeLossItemCode") {
		var carCount = fm.prpLRepairFeeLossItemCode[index - 1].value;
	}
	if (field == "prpLPropCarSerialno") {
		var carCount = fm.prpLPropCarSerialno[index - 1].value;
	}

	var carType = fm.prpLcarLossInsureCarFlag[carCount].value;
	if (carType == "1") {
		code_CodeSelect(obj, 'kindCodeInsureCar', '-1,0', 'Y', 'N', fm.policyNo.value + '|' + fm.prpLRegistRPolicyNo.value);
	} else {
		code_CodeSelect(obj, 'kindCodeThirdCar', '-1,0', 'Y', 'N', fm.policyNo.value + '|' + fm.prpLRegistRPolicyNo.value);
	}
}

function kindCodeCarPropSelect(obj) {
	code_CodeSelect(obj, 'kindCodeThirdCar', '0,1', 'Y', 'Y', fm.policyNo.value + '|' + fm.prpLRegistRPolicyNo.value);
}

function kindNameCarPropSelect(obj) {
	code_CodeSelect(obj, 'kindCodeThirdCar', '-1,0', 'Y', 'N', fm.policyNo.value + '|' + fm.prpLRegistRPolicyNo.value);
}

function openCertify(registNo) {
	window.open("/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo=" + registNo + "&editType=EDIT&nodeType=quickCase", "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=700,Height=500");
}

function backCheckSelect() {
	if (fm.backCheckTypeFlag.value != "") {
		if (fm.backCheckTypeFlag.value != "3") {
			if (fm.backCheckType.name == "backCheckType") {
				fm.backCheckType.disabled = false;
			}
		}
		fm.prpLBackCheckDtoRemark.disabled = false;
		fm.prpLBackCheckDtoRemark.style.fontSize = "11pt";
		fm.prpLBackCheckDtoRemark.style.color = "#000000";
		fm.prpLBackCheckDtoRemark.style.backgroundColor = "#FFFFFF";
		fm.prpLBackCheckDtoRemark.style.BORDER = "#FFFFFF";
		fm.prpLBackCheckDtoRemark.style.borderTop = "#009966 1px solid";
		fm.prpLBackCheckDtoRemark.style.borderBottom = "#009966 1px solid";
		fm.prpLBackCheckDtoRemark.style.borderRight = "#009966 1px solid";
		fm.prpLBackCheckDtoRemark.style.borderLeft = "#009966 1px solid";
		fm.prpLBackCheckDtoRemark.style.width = "260px";
		fm.prpLBackCheckDtoRemark.readOnly = false;
	}
}