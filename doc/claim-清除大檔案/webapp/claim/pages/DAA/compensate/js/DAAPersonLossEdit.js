/*****************************************************************************
 * DESC       ：人员列表增加JS
 * AUTHOR     : 中科軟
 * CREATEDATE ： 2004-08-01
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 * 插入一条新的lLoss之後的处理（可选方法）
 */

function afterInsertperson() {
	setPrpLpersonSerialNo();
	for (i = 0; i < fm.all("prpLpersonLossSerialNo").length; i++) {
		var prpLcompensateIndemnityDutyRate = document.getElementsByName("prpLcompensateIndemnityDutyRate");
		fm.prpLpersonLossIndemnityDutyRate[i].value = parseInt(prpLcompensateIndemnityDutyRate[prpLcompensateIndemnityDutyRate.length - 1].value);
	}
}

/*
   删除本条WarnRegion之後的处理（可选方法）
 */

function afterDeletePerson(field) {
	setPrpLpersonSerialNo();
	setPersonSerialNo();
	initEvryTypeRealPay();
	calFund();
	calLoss();
}

/**
 * 减去一条人伤信息之後设置setPersonLossSerialNo
 */

function setPersonSerialNo() {
	var count1 = getElementCount("prpLpersonLossSerialNo");
	var count2 = getElementCount("personLossSerialNo");
	var flag = 0;
	var j = 0;
	for (var i = 0; i < count1; i++) {
		if (count1 != 1) {
			while (j < count2) {
				if (count2 != 1) {
					if (j + 1 != count2) {
						if (fm.personLossSerialNo[j].value != fm.personLossSerialNo[j + 1].value) {
							fm.personLossSerialNo[j].value = parseInt(fm.prpLpersonLossSerialNo[i].value);
							j++;
							break;
						} else {
							fm.personLossSerialNo[j].value = parseInt(fm.prpLpersonLossSerialNo[i].value);
							j++;
						}
					} else {
						fm.personLossSerialNo[j].value = parseInt(fm.prpLpersonLossSerialNo[i].value);
						flag = 1;
					}
					if (flag == 1) {
						return true;
					}
				}
			}
		}
	}
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
 * 插入一条新的lLoss之後的处理（可选方法）
 */

function afterInsertPersonCommerce() {
	setPrpLpersonCommerceSerialNo();
}

/*
   删除本条WarnRegion之後的处理（可选方法）
 */

function afterDeletePersonCommerce(field) {

	setPrpLpersonCommerceSerialNo();
}

/**
 * 设置setPrpLpersonLossSerialNo
 */

function setPrpLpersonCommerceSerialNo() {
	var count = getElementCount("prpLpersonCommerceSerialNo");

	for (var i = 0; i < count; i++) {
		if (count != 1) {
			fm.prpLpersonCommerceSerialNo[i].value = i;
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
		if (elements[i].name == "prpLpersonLossAmount") {
			elements[i].value = fm.all("prpLpersonLossAmountTmp")[index].value;
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

/**
 @author 中科软
 @description 计算赔偿金额（费用代码的时候触发）
 @param       field:触发域
*/

function calculateFee(field) {

}




/**
  在表格下方添加一组数据，禁止非本模块调用
  参数为页代码名称和页原始数据代码名称
  例:insertRow("Engage","Engage_Data");
  返回插入行的序号（从1开始）
  */

function insertRowMDTable(pageCode, dataPageCode, field) {
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
		if (elements[i].name == "personMedicalSerialNo") {
			elements[i].value = index;
		}
		if (elements[i].name == "prpLpersonLossAmount") {
			elements[i].value = fm.all("prpLpersonLossAmountTmp")[index].value;
		}

	}
	return true;
}




/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */

function deleteRowMDTable(field, pageCode, pageDataRowsCount, controlRowsCount) {
	recentDeletedRowNo = parseInt(getElementOrder(field));
	var pageLossSerialNo = 0;
	pageLossSerialNo = parseInt(fm.personMedicalSerialNo(recentDeletedRowNo - 1).value);
	var order = recentDeletedRowNo - 1; //顺序改为以0开始
	var oTBODY = document.getElementsByName(pageCode)[pageLossSerialNo].tBodies.item(0);
	order = getRowMDCurrPage(field, pageCode);
	//在删除该行之前，定位到所在PersonFeeMedical的table。
	var $personFeeMedicalTable = $(field).parents("table[name='PersonFeeMedical']");
	for (var i = 0; i < controlRowsCount; i++) {
		oTBODY.removeChild(oTBODY.rows[order * controlRowsCount]);
	}
	//删除后调用countPrpLpersonMedicalDetailCodeNew方法
	countPrpLpersonMedicalDetailCodeNew($personFeeMedicalTable[0]);
	return recentDeletedRowNo - 1;
}

//作用：在删除费用讯息的一行后，重新计算赔付金额，并判断是否超过限额，如果超过，则只显示限额。

function countPrpLpersonMedicalDetailCodeNew(field) {
	var A00 = 0.0;
	var B00 = 0.0;
	var C00 = 0.0;
	var prpLpersonMedicalDetailCode = $(field).find("input[name='prpLpersonMedicalDetailCode']"); //费用代码
	var prpLpersonMedicalSumDefPay = $(field).find("input[name='prpLpersonMedicalSumDefPay']"); //核定赔偿
	var SumRealPay = 0;
	var flag = "wealthQuota";
	$.each(prpLpersonMedicalDetailCode, function (i, n) {
		var value = 0;
		if (isNumeric(prpLpersonMedicalSumDefPay[i].value)) {
			value = parseFloat(prpLpersonMedicalSumDefPay[i].value);
		} else {
			prpLpersonMedicalSumDefPay[i].value = 0;
		}
		if (n.value.indexOf("B") > -1) {
			flag = "medicalQuota";
			B00 += value; //死亡给付
		} else if (n.value.indexOf("C") > -1) {
			flag = "medicalQuota";
			C00 += value; //残疾给付
		} else if (n.value.indexOf("A") > -1) {
			A00 += value; //医疗给付
		}
		SumRealPay += value;
	});
	var prpLpersonCommerceSumRealPay1 = $(field).parents("table").find("input[name='prpLpersonCommerceSumRealPay1']");
	if (fm.exceedingPayout.value == "false") {
		if (flag == "wealthQuota") {
			if (SumRealPay > wealthQuota) {
				SumRealPay = wealthQuota;
			}
		} else if (flag == "medicalQuota") {
			if (SumRealPay > medicalQuota) {
				SumRealPay = medicalQuota;
			}
		}
	}
	prpLpersonCommerceSumRealPay1[0].value = SumRealPay;
	var PersonFeeMedicalCount = $(field).parents().children("table[name='PersonFeeMedicalCount']");
	var prpLPersonLossA00 = PersonFeeMedicalCount.find("input[name='prpLPersonLossA00']");
	prpLPersonLossA00.val(A00);
	var prpLPersonLossB00 = PersonFeeMedicalCount.find("input[name='prpLPersonLossB00']");
	prpLPersonLossB00.val(B00);
	var prpLPersonLossC00 = PersonFeeMedicalCount.find("input[name='prpLPersonLossC00']");
	prpLPersonLossC00.val(C00);
}

//得到当前的行所在当前页位置

function getRowMDCurrPage(field, DataPageCode) {
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
	pageLossSerialNo = parseInt(fm.personMedicalSerialNo(recentRowNo - 1).value);
	//index--; //顺序改为以0开始
	//得到Input域的名字
	elements = getTableElements("PersonCommerce");

	for (i = 0; i < elements.length; i++) {
		//得到人员费用索引起始位置及人员费用的记录数
		if (elements[i].name == "personMedicalSerialNo") {

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


/**
  在表格下方添加一组数据，禁止非本模块调用
  参数为页代码名称和页原始数据代码名称
  例:insertRow("Engage","Engage_Data");
  返回插入行的序号（从1开始）
  */

function insertRowDTable(pageCode, dataPageCode, field) {
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
		if (elements[i].name == "personDeformitySerialNo") {
			elements[i].value = index;
		}
		if (elements[i].name == "prpLpersonLossAmount") {
			elements[i].value = fm.all("prpLpersonLossAmountTmp")[index].value;
		}

	}
	return true;
}




/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */

function deleteRowDTable(field, pageCode, pageDataRowsCount, controlRowsCount) {
	recentDeletedRowNo = parseInt(getElementOrder(field));
	var pageLossSerialNo = 0;
	pageLossSerialNo = parseInt(fm.personDeformitySerialNo(recentDeletedRowNo - 1).value);
	var order = recentDeletedRowNo - 1; //顺序改为以0开始
	var oTBODY = document.getElementsByName(pageCode)[pageLossSerialNo].tBodies.item(0);
	order = getRowDCurrPage(field, pageCode);
	for (var i = 0; i < controlRowsCount; i++) {
		oTBODY.removeChild(oTBODY.rows[order * controlRowsCount]);
	}
	return recentDeletedRowNo - 1;
}




//得到当前的行所在当前页位置

function getRowDCurrPage(field, DataPageCode) {
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
	pageLossSerialNo = parseInt(fm.personDeformitySerialNo(recentRowNo - 1).value);
	//index--; //顺序改为以0开始
	//得到Input域的名字
	elements = getTableElements("PersonCommerce");

	for (i = 0; i < elements.length; i++) {
		//得到人员费用索引起始位置及人员费用的记录数
		if (elements[i].name == "personDeformitySerialNo") {

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


function setpersonLossSumDefPay(field) {

	var serialNo = 0; //定位
	var fieldName = field.name;
	for (var i = 1; i < fm.all(fieldName).length; i++) {
		if (field == fm.all(fieldName)[i]) {
			serialNo = i;
			break;
		}
	}


	var sumLoss = parseFloat(fm.prpLpersonLossSumLoss[serialNo].value);

	if (isNaN(sumLoss)) {
		sumLoss = 0;
	}


	fm.prpLpersonLossSumDefPay[serialNo].value = point(round(sumLoss, 0), 0);

}

function checkPersonRealPay4(field) {
	if (field.value != "" && field.value != "0") {
		var findex;
		var kindCode;
		var Amount;
		var fieldname = field.name;
		for (var i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == field) {
				findex = i;
				break;
			}
		}

		kindCode = fm.prpLpersonLossKindCode[findex].value;
		if (kindCode != "") {
			for (var index1 = 0; index1 < fm.prpLlossDtoKindCodeShow.length; index1++) {
				if (fm.prpLlossDtoKindCodeShow[index1].value == kindCode)
					Amount = fm.kindAmount[index1].value;
			}
			if (isNaN(Amount)) {} else {
				if (parseInt(fm.prpLpersonLossSumDefPay1[findex].value) > parseInt(Amount)) {
					calPersonRealpay(field);
				}
			}
		}
	}
}
//用来计算伤残等级後获取赔付金额的计算

function prpLdisabilityLimitFee(field) {
	var fieldName = field.name;
	var fields = document.getElementsByName(fieldName);
	var index = 0;
	for (var i = 1; i < fields.length; i++) {
		if (fields[i] == field) {
			index = i;
			break;
		}
	}
	if (index < 1) {
		return;
	}
	var riskCode = fm.prpLcompensateRiskCode.value;
	var prpLdisabilityLimitRatingCode;
	var prpLpersonLossLiabDetailCode; //费用类型
	var prpLpersonLossInjuryGrade; //伤残等级
	if (riskCode == RISKINFO.RISKCODE_DAZ) {
		prpLdisabilityLimitRatingCode = document.getElementsByName("prpLdisabilityLimitRatingCode")[index];
		prpLpersonLossLiabDetailCode = document.getElementsByName("prpLpersonMedicalDetailCode")[index]; //费用类型
		prpLpersonLossInjuryGrade = document.getElementsByName("prpLpersonMedicalInjuryGrade")[index]; //伤残等级
		if (prpLpersonLossLiabDetailCode.value == "C00") {
			prpLdisabilityLimitRatingCode.disabled = false;
		} else {
			prpLdisabilityLimitRatingCode.disabled = true;
			prpLdisabilityLimitRatingCode.value = "";
			prpLpersonLossInjuryGrade.value = "";
		}
		prpLpersonLossInjuryGrade.value = prpLdisabilityLimitRatingCode.value;
	} else {
		prpLdisabilityLimitRatingCode = document.getElementsByName("prpLdisabilityLimitRatingCode")[index];
		prpLpersonLossLiabDetailCode = document.getElementsByName("prpLpersonLossLiabDetailCode")[index]; //费用类型
		prpLpersonLossInjuryGrade = document.getElementsByName("prpLpersonLossInjuryGrade")[index]; //伤残等级
		prpLpersonLossInjuryGrade.value = prpLdisabilityLimitRatingCode.value;
		var personLossSerialNo = document.getElementsByName("personLossSerialNo")[index];
		var prpLpersonLossSerialNo = document.getElementsByName("prpLpersonLossSerialNo");
		var prpLpersonLossKindCode = document.getElementsByName("prpLpersonLossKindCode");
		var kindCode = "";
		for (var i = 0; i < prpLpersonLossSerialNo.length; i++) {
			if (prpLpersonLossSerialNo[i].value == personLossSerialNo.value) {
				kindCode = prpLpersonLossKindCode[i].value;
				break;
			}
		}
		if (kindCode == "" || (kindCode != "48" && kindCode != "47")) {
			return;
		}
	}
	if (prpLpersonLossInjuryGrade.value == "") {
		return;
	}
	var prpLcompensateClaimNo = fm.prpLcompensateClaimNo.value;
	var inputArgs = {
		claimNo: prpLcompensateClaimNo,
		ratingCode: prpLpersonLossInjuryGrade.value
	};
	var inputObject = field;
	var outputObject = index;
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("getPrpLdisabilityLimitFee", param, "prpLdisabilityLimitFeeSucees", inputObject, outputObject);
	DWREngine.setAsync(true);
}

function prpLdisabilityLimitFeeSucees(inputObject, outputObject, returnObject) {
	var riskCode = fm.prpLcompensateRiskCode.value;
	if (riskCode == RISKINFO.RISKCODE_DAZ) {
		var prpLpersonMedicalSumLoss = document.getElementsByName("prpLpersonMedicalSumLoss")[outputObject]; //损失金额
		var prpLpersonMedicalSumDefPay = document.getElementsByName("prpLpersonMedicalSumDefPay")[outputObject]; //核定赔偿
		prpLpersonMedicalSumLoss.value = returnObject;
		prpLpersonMedicalSumDefPay.value = returnObject;
		prpLpersonMedicalSumDefPay.onblur();
	} else {
		var prpLpersonLossSumDefPay = document.getElementsByName("prpLpersonLossSumDefPay")[outputObject]; //核定赔偿
		prpLpersonLossSumDefPay.value = returnObject;
		prpLpersonLossSumDefPay.onchange();
	}
	undisablebutton();
}
//统计死亡伤残人数

function countPersonLossNumber() {
	var riskCode = fm.prpLcompensateRiskCode.value;
	if (riskCode == RISKINFO.RISKCODE_DAZ) {
		var prpLpersonCommerceCasualties = document.getElementsByName("prpLpersonCommerceCasualties"); //伤亡情形
		if (prpLpersonCommerceCasualties.length <= 1) {
			return true;
		}
		var carArrar = new Array(new Array(3), new Array(4));
		for (var i = 0; i < 3; i++) {
			carArrar[i] = new Array(4);
			for (var j = 0; j < 4; j++) {
				carArrar[i][j] = 0;
			}
		}
		var prpLpersonCommerceRideSituation = document.getElementsByName("prpLpersonCommerceRideSituation"); //出事當時乘坐狀況
		document.getElementsByName("personLossNumber")[0].value = prpLpersonCommerceCasualties.length - 1; //理赔人数
		for (var i = 1; i < prpLpersonCommerceCasualties.length; i++) {
			if (prpLpersonCommerceRideSituation[i].value == "1" || prpLpersonCommerceRideSituation[i].value == "6") {
				//本车
				if (!isNaN(prpLpersonCommerceCasualties[i].value)) {
					carArrar[0][prpLpersonCommerceCasualties[i].value]++;
				}
			} else if (prpLpersonCommerceRideSituation[i].value == "4" || prpLpersonCommerceRideSituation[i].value == "5") {
				//对方车
				if (!isNaN(prpLpersonCommerceCasualties[i].value)) {
					carArrar[1][prpLpersonCommerceCasualties[i].value]++;
				}
			} else {
				//車外人員
				if (!isNaN(prpLpersonCommerceCasualties[i].value)) {
					carArrar[2][prpLpersonCommerceCasualties[i].value]++;
				}
			}
		}
		var carName = ["car", "threeCar", "outerCar", "MedicalNumber", "DisabilityNumber", "DeathNumber"];
		for (var i = 0; i < 3; i++) {
			for (var j = 0; j < 3; j++) {
				document.getElementsByName(carName[i] + carName[3 + j])[0].value = carArrar[i][j + 1]; //死亡人数
			}
		}
	} else {
		var prpLpersonLossCasualties = document.getElementsByName("prpLpersonLossCasualties"); //伤亡情形
		if (prpLpersonLossCasualties.length <= 1) {
			return true;
		}
		var carArrar = new Array(new Array(3), new Array(4));
		for (var i = 0; i < 3; i++) {
			carArrar[i] = new Array(4);
			for (var j = 0; j < 4; j++) {
				carArrar[i][j] = 0;
			}
		}
		var prpLpersonLossRideSituation = document.getElementsByName("prpLpersonLossRideSituation"); //出事當時乘坐狀況
		document.getElementsByName("personLossNumber")[0].value = prpLpersonLossRideSituation.length - 1; //理赔人数
		for (var i = 1; i < prpLpersonLossRideSituation.length; i++) {
			if (prpLpersonLossRideSituation[i].value == "1" || prpLpersonLossRideSituation[i].value == "6") {
				//本车
				if (!isNaN(prpLpersonLossCasualties[i].value)) {
					carArrar[0][prpLpersonLossCasualties[i].value]++;
				}
			} else {
				//对方车
				if (!isNaN(prpLpersonLossCasualties[i].value)) {
					carArrar[1][prpLpersonLossCasualties[i].value]++;
				}
			}
		}
		var carName = ["car", "threeCar", "MedicalNumber", "DisabilityNumber", "DeathNumber"];
		for (var i = 0; i < 2; i++) {
			for (var j = 0; j < 3; j++) {
				document.getElementsByName(carName[i] + carName[2 + j])[0].value = carArrar[i][j + 1]; //死亡人数
			}
		}
	}
}

function countPrpLpersonMedicalDetailCode(field) {
	var A00 = 0.0;
	var B00 = 0.0;
	var C00 = 0.0;
	var PersonFeeMedical = $(field).parents("table[name='PersonFeeMedical']");
	var prpLpersonMedicalDetailCode = PersonFeeMedical.find("input[name='prpLpersonMedicalDetailCode']"); //费用代码
	var prpLpersonMedicalSumDefPay = PersonFeeMedical.find("input[name='prpLpersonMedicalSumDefPay']"); //核定赔偿
	var SumRealPay = 0;
	var flag = "wealthQuota";
	$.each(prpLpersonMedicalDetailCode, function (i, n) {
		var value = 0;
		if (isNumeric(prpLpersonMedicalSumDefPay[i].value)) {
			value = parseFloat(prpLpersonMedicalSumDefPay[i].value);
		} else {
			prpLpersonMedicalSumDefPay[i].value = 0;
		}
		if (n.value.indexOf("B") > -1) {
			flag = "medicalQuota";
			B00 += value; //死亡给付
		} else if (n.value.indexOf("C") > -1) {
			flag = "medicalQuota";
			C00 += value; //残疾给付
		} else if (n.value.indexOf("A") > -1) {
			A00 += value; //医疗给付
		}
		SumRealPay += value;
	});
	var prpLpersonCommerceSumRealPay1 = $("input[name='prpLpersonCommerceSumRealPay1']"); //赔付合计
	$.each($("table[name='PersonFeeMedical']"), function (i, n) {
		if (PersonFeeMedical[0] == n) {
			if (fm.exceedingPayout.value == "false") {
				if (flag == "wealthQuota") {
					if (SumRealPay > wealthQuota) {
						SumRealPay = SumRealPay - field.value;
						alert("該傷亡情形最高給付限額為20萬，賠付金額超過給付限額，請調整給付金額！");
						field.value = 0;
					}
				} else if (flag == "medicalQuota") {
					if (SumRealPay > medicalQuota) {
						SumRealPay = SumRealPay - field.value;
						alert("該傷亡情形最高給付限額為220萬，賠付金額超過給付限額，請調整給付金額！");
						field.value = 0;
					}
				}
			}
			prpLpersonCommerceSumRealPay1[i].value = SumRealPay;
		}
	});
	var PersonFeeMedicalCount = PersonFeeMedical.parents().children("table[name='PersonFeeMedicalCount']");
	var prpLPersonLossA00 = PersonFeeMedicalCount.find("input[name='prpLPersonLossA00']");
	prpLPersonLossA00.val(A00);
	var prpLPersonLossB00 = PersonFeeMedicalCount.find("input[name='prpLPersonLossB00']");
	prpLPersonLossB00.val(B00);
	var prpLPersonLossC00 = PersonFeeMedicalCount.find("input[name='prpLPersonLossC00']");
	prpLPersonLossC00.val(C00);
}

function countPrpLpersonMedicalDetailCodeALL() {
	var prpLpersonCommerceSumRealPay1 = $("input[name='prpLpersonCommerceSumRealPay1']"); //赔付合计
	var prpLpersonCommerceCasualties = $("select[name='prpLpersonCommerceCasualties']"); //傷亡情形
	$.each($("table[name='PersonFeeMedical']"), function (i, n) {
		if (i > 0) {
			var A00 = 0.0;
			var B00 = 0.0;
			var C00 = 0.0;
			var SumRealPay = 0;
			var PersonFeeMedical = $(n);
			var prpLpersonMedicalDetailCode = PersonFeeMedical.find("input[name='prpLpersonMedicalDetailCode']");
			var prpLpersonMedicalSumDefPay = PersonFeeMedical.find("input[name='prpLpersonMedicalSumDefPay']");
			$.each(prpLpersonMedicalDetailCode, function (j, m) {
				var value = 0;
				if (isNumeric(prpLpersonMedicalSumDefPay[j].value)) {
					value = parseFloat(prpLpersonMedicalSumDefPay[j].value);
				} else {
					prpLpersonMedicalSumDefPay[j].value = 0;
				}
				if (m.value.indexOf("B") > -1) {
					B00 += value;
				} else if (m.value.indexOf("C") > -1) {
					C00 += value;
				} else if (m.value.indexOf("A") > -1) {
					A00 += value;
				}
				SumRealPay += value;
			});
			if (fm.exceedingPayout.value == "false") {
				var prpLpersonCommerceCasualtiesValue = prpLpersonCommerceCasualties[i].value;
				if (prpLpersonCommerceCasualtiesValue == "1") {
					if (SumRealPay > wealthQuota) {
						SumRealPay = wealthQuota;
					}
				} else if (prpLpersonCommerceCasualtiesValue == "3") {
					if (SumRealPay > medicalQuota) {
						SumRealPay = medicalQuota;
					}
				}
			}
			prpLpersonCommerceSumRealPay1[i].value = SumRealPay;
			var PersonFeeMedicalCount = PersonFeeMedical.parents().children("table[name='PersonFeeMedicalCount']");
			var prpLPersonLossA00 = PersonFeeMedicalCount.find("input[name='prpLPersonLossA00']");
			prpLPersonLossA00.val(A00);
			var prpLPersonLossB00 = PersonFeeMedicalCount.find("input[name='prpLPersonLossB00']");
			prpLPersonLossB00.val(B00);
			var prpLPersonLossC00 = PersonFeeMedicalCount.find("input[name='prpLPersonLossC00']");
			prpLPersonLossC00.val(C00);
		}
	});
}

//统计任意险人伤的总金额

function countPrpLpersonLossSumDefPay1ALL() {
	var prpLpersonLossSumDefPay1 = $("input[name='prpLpersonLossSumRealPay1']"); //赔付合计
	$.each($("table[name='PrpLpersonFeeLoss']"), function (i, n) {
		if (i > 0) {
			var SumRealPay = 0;
			var PrpLpersonFeeLoss = $(n);
			var prpLpersonLossSumRealPay = PrpLpersonFeeLoss.find("input[name='prpLpersonLossSumRealPay']");
			$.each(prpLpersonLossSumRealPay, function (j, m) {
				if (isNumeric(m.value)) {
					SumRealPay += parseFloat(m.value);
				}
			});
			prpLpersonLossSumDefPay1[i].value = SumRealPay;
		}
	})
}

function clearSumDefPay(field) {
	var prpLpersonMedicalSumDefPay = $(field).parents("tr").find("input[name='prpLpersonMedicalSumDefPay']");
	var prpLpersonMedicalDetailCode = $(field).parents("tr").find("input[name='prpLpersonMedicalDetailCode']"); //费用代码
	if ((prpLpersonMedicalDetailCode[0].value.indexOf("B") > -1 || prpLpersonMedicalDetailCode[0].value.indexOf("C") > -1) && prpLpersonMedicalSumDefPay[0].value > medicalQuota) {
		prpLpersonMedicalSumDefPay[0].value = 0;
	} else if (prpLpersonMedicalDetailCode[0].value.indexOf("A") > -1 && prpLpersonMedicalSumDefPay[0].value > wealthQuota) {
		prpLpersonMedicalSumDefPay[0].value = 0;
	}
	countPrpLpersonMedicalDetailCode(prpLpersonMedicalSumDefPay[0]);
}

//查看历史赔付人员讯息

function showPersonHistory() {
	var claimNo = document.getElementsByName("prpLcompensateClaimNo")[0].value;
	var url = contextRootPath + "/compensate/compensatePersonHistory.do?prpLcompensateClaimNo=" + claimNo;
	window.open(url, "歷史賠付受害人訊息");
}
//增加赔付人员信息

function addPersonHistory(prpLpersonLoss) {
	var flag = true;
	$(":input[name='prpLpersonLossIdentifyNumber']").each(function () {
		if (prpLpersonLoss.identifyNumber == this.value) {
			flag = false;
			return false;
		}
	})
	if (!flag) {
		return flag;
	}
	//插入一条人伤信息
	insertPrpLpersonLossObject();
	countPersonLossNumber();
	var $prpLpersonLossObject = $("tr[name='prpLpersonLossObject']:last");
	$prpLpersonLossObject.find(":input[name='prpLpersonLossPersonName']").val(prpLpersonLoss.personName);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossSex']").val(prpLpersonLoss.sex);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossFamilyName']").val(prpLpersonLoss.familyName);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossBirthday']").val(prpLpersonLoss.birthday);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossBirthday_show_format_rcDate']").val(prpLpersonLoss.birthday_show_format_rcDate);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossAge']").val(prpLpersonLoss.age);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossIdentityOfInjuredPerson']").val(prpLpersonLoss.identityOfInjuredPerson);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossRideSituation']").val(prpLpersonLoss.rideSituation);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossIdentifyNumber']").val(prpLpersonLoss.identifyNumber);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossMedicalCode']").val(prpLpersonLoss.medicalCode);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossEndCaseAndRecoverFlag']").val(prpLpersonLoss.endCaseAndRecoverFlag);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossTelephoneNo']").val(prpLpersonLoss.telephoneNo);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossProsecutorsOffice']").val(prpLpersonLoss.prosecutorsOffice);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossCourtDoctor']").val(prpLpersonLoss.courtDoctor);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossMobilePhone']").val(prpLpersonLoss.mobilePhone);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossProsecutor']").val(prpLpersonLoss.prosecutor);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossGarageHeadName']").val(prpLpersonLoss.garageHeadName);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossHospitalCode']").val(prpLpersonLoss.hospitalCode);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossHospitalName']").val(prpLpersonLoss.hospitalName);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossDoctor']").val(prpLpersonLoss.doctor);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossArrangeRate']").val(prpLpersonLoss.arrangeRate);
	//强制险的伤残类型不同，不复制这部分
	if (prpLpersonLoss.kindCode != RISKINFO.KINDCODE_D_BZ) {
		$prpLpersonLossObject.find(":input[name='prpLpersonLossCasualties']").val(prpLpersonLoss.casualties);
	}
	$prpLpersonLossObject.find(":input[name='prpLpersonLossIndemnityDutyRate']").val(prpLpersonLoss.indemnityDutyRate);
	return true;
}
//任意险 出生年份 关联 年龄 
function updatePersonLossAge(field){
	var age = 0;
	if(field.realValue != "" && field.realValue != null){
		var birthday = new Date(field.realValue.replace("-","/"));
		var now = new Date();//获得系统当前时间
		age = now.getFullYear()-birthday.getFullYear();
		var nextDate = getNextYearFullDate(field.realValue,age);
		var temp = compareFullDate(nextDate,convertFullDateToString(now));
		if(temp > 0 ){
			age -= 1;
		}
		if(age < 0 ){
			age = 0;
		}
	}else{
		age = "";
	}
	var index = $("input[name='"+field.name+"']").index(field);
	$("input[name='prpLpersonLossAge']").get(index).value = age;
}