/*****************************************************************************
 * DESC       ：修理换件增加JS
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-08-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 * @description 清除一页的所有多行纪录
 * @param 页名称
 */

function deleteAllRows(pageCode) {
	var oTBODY = document.all(pageCode).tBodies.item(0);

	var intCount = getElementCount("prpLcomponentPartCode");

	for (var i = intCount - 1; i >= 1; i--) {
		deleteRowTableComponent(fm.prpLcomponentPartCode[i], pageCode, 1, 1);
	}
}



function deleteSystemRows(pageCode) {
	var oTBODY = document.all(pageCode).tBodies.item(0);

	var intCount = getElementCount("prpLcomponentPartCode");

	for (var i = intCount - 1; i >= 1; i--) {
		if (fm.prpLcomponentFlag[i].value == '1') {
			deleteRowTableComponent(fm.prpLcomponentPartCode[i], pageCode, 1, 1);
		}
	}
}


function showFittingsInfo() {

	deleteSystemRows('Component');

	var oldTarget = fm.target;
	var oldAction = fm.action;

	fm.target = "fraSubmit"
	fm.action = "/claim/DAA/certainLoss/DAAShowFittingsInfo.jsp?registNo=" + fm.prpLverifyLossRegistNo.value + "&lossItemCode=" + fm.prpLcarLossLossItemCode.value;

	fm.submit();

	fm.target = oldTarget;
	fm.action = oldAction;

}


/**
 * 从配件系统获取配件信息
 */

function getFittingsInfo(queryType) {
	var k = 1;
	var count = getElementCount("prpLcomponentPartCode");

	strURL = "/claim/pages/DAA/certainLoss/openFittingsSystemBefore.jsp?queryType=" + queryType + "&registNo=" + fm.RegistNo.value + "&policyNo=" + fm.PolicyNo.value + "&insurant=" + fm.prpLverifyLossInsuredName.value + "&lossItemCode=" + fm.prpLcarLossLossItemCode.value + "&licenseNo=" + fm.prpLcarLossLossItemName.value + "&repairfactorytype=" + fm.prpLrepairFeeRepairFactoryName.value + "&showpriceflag=0" + "&systemAreaCode=" + fm.SysAreaCode.value //系统价地区 
	+ "&localAreaCode=" + fm.LocalAreaCode.value //本地价地区（填写当前用户所在的分公司代码）
	+ "&vehCode=" + fm.prpLcarLossModelCode.value //车型编码          
	+ "&vehName=" + fm.prpLcarLossBrandName.value; //车型名称

	var newWindow = window.open(strURL, "打开配件系统", 'width=1010,height=670,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	newWindow.focus();
}


/**
  在表格下方添加一组数据，禁止非本模块调用
  参数为页代码名称和页原始数据代码名称
  例:insertRow("Engage","Engage_Data");
  返回插入行的序号（从1开始）
  */

function insertRowTableRepairFee(pageCode, dataPageCode, field) {
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
		if (elements[i].name == "carLossRepairFeeLossItemCode") {
			elements[i].value = index;
		}
	}

	//Reason:修理项目费用清单增加一项时，将上一项的险别代码与险别名称带到新增项目中
	var newSerialNo = fm.prpLrepairFeeSerialNo.length - 1;
	if (newSerialNo > 1) {
		fm.prpLrepairFeeKindCode[newSerialNo].value = fm.prpLrepairFeeKindCode[newSerialNo - 1].value;
		fm.prpLrepairFeeKindName[newSerialNo].value = fm.prpLrepairFeeKindName[newSerialNo - 1].value;
	}
	return true;
}

/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */

function deleteRowTableRepairFee(field, pageCode, pageDataRowsCount, controlRowsCount) {
	recentDeletedRowNo = parseInt(getElementOrder(field));
	var pageSerialNo = 0;
	try {
		pageSerialNo = parseInt(fm.carLossRepairFeeLossItemCode(recentDeletedRowNo - 1).value);
	} catch (e) {
		pageSerialNo = 0;
	}
	var order = recentDeletedRowNo - 2; //顺序改为以0开始
	var oTBODY = document.getElementsByName(pageCode)[0].tBodies.item(0);
	var computLine = 0;
	computLine = order + 1; //需要计算的行
	//可能是退回的时候，上面有双行的记录
	var backRows = getElementCount("txtRepairFeeBackFlag");
	if (controlRowsCount == 1) {
		if (backRows > 0) {
			order = backRows + order; //因为如果有backrws的话，行要多了
		}
	}

	if (controlRowsCount == 2) {
		order = order * 2;
	}
	var ManHour = parseFloat(fm.all("prpLrepairFeeManHour")[computLine].value);
	var manHourFee = parseFloat(fm.all("prpLrepairFeeMaterialFee")[computLine].value);
	var sumManHourFee = parseFloat(fm.SumDefLoss1.value);
	//判断总数字是不是空 
	if (isNaN(ManHour)) ManHour = 1;
	if (isNaN(manHourFee)) manHourFee = 0;
	if (isNaN(sumManHourFee)) sumManHourFee = 0;
	sumManHourFee = sumManHourFee - manHourFee;
	fm.SumDefLoss1.value = point(round(sumManHourFee, 0), 0);
	if (controlRowsCount == 2) {
		oTBODY.removeChild(oTBODY.rows[order]);
		oTBODY.removeChild(oTBODY.rows[order]);
	} else {
		for (var i = 0; i < controlRowsCount; i++) {
			oTBODY.removeChild(oTBODY.rows[order * controlRowsCount]);
		}
	}
	return recentDeletedRowNo - 1;
}

/**
 * 得到当前的行所在当前页位置
 */

function getRowCurrPageRepairFee(field, DataPageCode) {
	var i = 0;
	var name = "";
	var elements;
	var fieldName = "";
	var countLoss = 0; //人员费用的记录数
	var firstIndexLoss = 0; //本人员的费用的起始index
	var recentRowNo = 0;
	var orderCurrent = 0; //当前行所在当前面的位置
	recentRowNo = parseInt(getElementOrder(field));
	var pageSerialNo = 0;
	pageSerialNo = parseInt(fm.carLossRepairFeeLossItemCode(recentRowNo - 1).value);
	//index--; //顺序改为以0开始
	//得到Input域的名字
	elements = getTableElements("RepairComponent");

	for (i = 0; i < elements.length; i++) {
		//得到人员费用索引起始位置及人员费用的记录数
		if (elements[i].name == "carLossRepairFeeLossItemCode") {
			if (parseInt(elements[i].value) == pageSerialNo) {
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

function insertRowTableComponent(pageCode, dataPageCode, field) {
	var index = 0; //当前table索引
	var elements = null;
	index = getElementOrder(field) - 1;
	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");

	for (var i = 0; i < oTBODYData.rows.length; i++) {
		oTBODY.appendChild(oTBODYData.rows[i].cloneNode(true));
	}
	//Reason:零部件更换项目费用清单增加一项时，将上一项的险别代码与险别名称带到新增项目中
	var newSerialNo = fm.prpLcomponentSerialNo.length - 1;
	if (newSerialNo > 1) {
		fm.prpLcomponentKindCode[newSerialNo].value = fm.prpLcomponentKindCode[newSerialNo - 1].value;
		fm.prpLcomponentKindName[newSerialNo].value = fm.prpLcomponentKindName[newSerialNo - 1].value;
	}
//	if (fm.carLossComponentLossItemCode.length >= 0) {
//		initCarLossSum(0);
//	}
	return true;
}

/**
  删除控制按钮控制的行，禁止非本模块调用
  字段，页名称，数据页中控制按钮的个数，数据页中每个控制按钮的控制的TR的个数
  返回删除行的序号（从1开始）
 */

function deleteRowTableComponent(field, pageCode, pageDataRowsCount, controlRowsCount) {
	recentDeletedRowNo = parseInt(getElementOrder(field));
	var pageSerialNo = 0;

	try {
		pageSerialNo = parseInt(fm.carLossComponentLossItemCode(recentDeletedRowNo - 1).value);
	} catch (e) {
		pageSerialNo = 0;
	}

	var order = recentDeletedRowNo - 2; //顺序改为以0开始
	var oTBODY = document.getElementsByName(pageCode)[pageSerialNo].tBodies.item(0);
	var computLine = 0;
	computLine = order + 1; //需要计算的行
	var backRows = getElementCount("txtComponentBackFlag");
	if (controlRowsCount == 1) {
		if (backRows > 0) {
			order = backRows + order; //可能存在多行的

		}
	}

	if (controlRowsCount == 2) {
		order = order * 2
	}
	var DefLoss = parseFloat(fm.all("prpLcomponentSumDefLoss")[computLine].value); //换件定损金额
	var RestFee = parseFloat(fm.all("prpLcomponentRestFee")[computLine].value); //残值
	var sumRestFee = parseFloat(fm.prpLcarLossSumRest.value); //残值总计
	var sumDefLoss = parseFloat(fm.SumDefLoss2.value); //换件合计

	//判断总数字是不是空 
	if (isNaN(DefLoss)) DefLoss = 0;
	if (isNaN(RestFee)) RestFee = 0;
	if (isNaN(sumRestFee)) sumRestFee = 0;
	if (isNaN(sumDefLoss)) sumDefLoss = 0;


	sumRestFee = sumRestFee - RestFee;
	sumDefLoss = sumDefLoss - DefLoss;

	fm.prpLcarLossSumRest.value = point(round(sumRestFee, 0), 0);
	fm.SumDefLoss2.value = point(round(sumDefLoss, 0), 0);

	if (controlRowsCount == 2) {
		oTBODY.removeChild(oTBODY.rows[order]);
		oTBODY.removeChild(oTBODY.rows[order]);
	} else {
		for (var i = 0; i < controlRowsCount; i++) {
			oTBODY.removeChild(oTBODY.rows[order * controlRowsCount]);
		}
	}
	return recentDeletedRowNo - 1;
}

/**
 * 得到当前的行所在当前页位置
 */

function getRowCurrPageComponent(field, DataPageCode) {
	var i = 0;
	var name = "";
	var elements;
	var fieldName = "";
	var countLoss = 0; //人员费用的记录数
	var firstIndexLoss = 0; //本人员的费用的起始index
	var recentRowNo = 0;
	var orderCurrent = 0; //当前行所在当前面的位置
	recentRowNo = parseInt(getElementOrder(field));
	var pageSerialNo = 0;
	pageSerialNo = parseInt(fm.carLossComponentLossItemCode(recentRowNo - 1).value);
	//index--; //顺序改为以0开始
	//得到Input域的名字
	elements = getTableElements("RepairComponent");

	for (i = 0; i < elements.length; i++) {
		//得到人员费用索引起始位置及人员费用的记录数
		if (elements[i].name == "carLossComponentLossItemCode") {
			if (parseInt(elements[i].value) == pageSerialNo) {
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

//Reason:修理项目费用及换件项目增加时默认有3项目，增加时可一次增加3项目
//当编辑类型为Add时，页面修理项目费用、换件项目模块黙认有三项

function loadRepairFeeRow() {
	for (var i = 0; i < 3; i++) {
		certainLossInsertRow('RepairFee', 'RepairFee_Data');
		certainLossInsertRow('Component', 'Component_Data');
	}
}

/**
 * 在表格下方添加一组数据，禁止非本模块调用
 */

function certainLossInsertRow(pageCode, dataPageCode) {
	var index = 0; //当前table索引
	var elements = null;

	var oTBODY = document.getElementsByName(pageCode)[index].tBodies.item(0);
	var oTBODYData = document.getElementById(dataPageCode).tBodies.item(0);
	elements = oTBODY.getElementsByTagName("INPUT");

	for (var i = 0; i < oTBODYData.rows.length; i++) {
		oTBODY.appendChild(oTBODYData.rows[i].cloneNode(true));
	}
	for (var i = 0; i < elements.length; i++) {
		if (elements[i].name == "carLossRepairFeeLossItemCode") {
			elements[i].value = index;
		}
		if (elements[i].name == "carLossComponentLossItemCode") {
			elements[i].value = index;
		}
	}
	return true;
}

/**
 * 换件项目增加时，//一次增加三项目
 */

function insertThreeRowTableComponent(pageCode, dataPageCode, field) {
	fm.clickComponentFlag.value = "true";
	insertRowTableComponent(pageCode, dataPageCode, field);

}

/**
 * 修理项目费用增加时，一次增加三项目
 */

function insertThreeRowTableRepairFee(pageCode, dataPageCode, field) {
	for (var i = 0; i < 1; i++) {
		insertRowTableRepairFee(pageCode, dataPageCode, field);
	}
}



/**
 * 以下打开零件代码页面
 */

function openPrplRepairFeeCompWin(PageCode, Field) {
	var index = parseInt(getElementOrder(Field)) - 1;
	var partCode = fm.prpLrepairFeePartCode(index).value;
	var previousFlag = "repairFee"; //此标志位表示此页面来自修理费用清单模块
	var pageUrl = "/claim/pages/DAA/regist/DAARegistGetCompCode.jsp?strIndex=" + index + "&partCode=" + partCode + "&previousFlag=" + previousFlag;
	window.open(pageUrl, "openPrplRepairFeeCompWin", "resizable=0,scrollbars,dependent,alwaysRaised,width=230,height=450");
}

/**
 *以下打开零件代码页面
 */

function openPrplComponentCompWin(PageCode, Field) {
	var index = parseInt(getElementOrder(Field)) - 1;
	var partCode = fm.prpLcomponentPartCode(index).value;
	var previousFlag = "component"; //此标志位表示此页面来自换件清单模块
	var pageUrl = "/claim/pages/DAA/regist/DAARegistGetCompCode.jsp?strIndex=" + index + "&partCode=" + partCode + "&previousFlag=" + previousFlag;
	window.open(pageUrl, "openPrplComponentCompWin", "resizable=0,scrollbars,dependent,alwaysRaised,width=230,height=450");
}

/**
 *核损价格异议时，自动将核损金额自动赋值到定损
 */

function getVerifyRepairFee() {
	var count = getElementCount("prpLrepairFeeVeriSumLoss");
	for (var i = 1; i < count; i++) {
		fm.prpLrepairFeeManHour[i].value = fm.prpLrepairFeeVeriManHour[i].value;
		fm.prpLrepairFeeManHourUnitPrice[i].value = fm.prpLrepairFeeVeriManUnitPrice[i].value;

	}
	setVerifyRepairFeeNew();
	calculateAll();
}

/**
 *核损价格异议时，自动将核损金额自动赋值到定损
 */

function getVerifyComponent() {
	var count = getElementCount("prpLcomponentVeriQuantity");

	for (var i = 1; i < count; i++) {
		fm.prpLcomponentQuantity[i].value = fm.prpLcomponentVeriQuantity[i].value;
		fm.prpLcomponentMaterialFee[i].value = fm.prpLcomponentVeriMaterFee[i].value;
		fm.prpLcomponentSumDefLoss[i].value = fm.prpLcomponentSumVeriLoss[i].value;
	}
	if (count > 1) {
		fm.prpLcomponentQuantity[count - 1].focus();
	}
}

function setFactoryName() {
	if (fm.prpLrepairFeeRepairFactoryCode.value == 1) {
		fm.prpLrepairFeeRepairFactoryName.value = "4S店";
	}
	if (fm.prpLrepairFeeRepairFactoryCode.value == 2) {
		fm.prpLrepairFeeRepairFactoryName.value = "一类厂";
	}
	if (fm.prpLrepairFeeRepairFactoryCode.value == 3) {
		fm.prpLrepairFeeRepairFactoryName.value = "二类厂一下";
	}

}

function setVerifyRepairFeeNew() {

	var i = 0;
	var findex = 0;
	var manHourFee;
	var materialFee;
	var sumDefLoss;
	var ManHour;
	var quantity;

	for (i = 1; i < fm.all("prpLrepairFeeManHour").length; i++) {
		ManHour = parseFloat(fm.all("prpLrepairFeeManHour")[i].value);
		manHourFee = parseFloat(fm.all("prpLrepairFeeManHourUnitPrice")[i].value)
		if (isNaN(manHourFee) || manHourFee.length < 1) {
			manHourFee = 0;
		}
		if (manHourFee == 0 && materialFee == 0) {}
		if (isNaN(ManHour) || ManHour.length < 1) {
			ManHour = 0;
		}
		sumDefLoss = manHourFee * ManHour;
		fm.all("prpLrepairFeeSumDefLoss")[i].value = point(round(sumDefLoss, 0), 0);
		sumRepairFee();
	}
	return true;

}


function selectPriceTypes() {

	var flag = fm.clickComponentFlag.value;
	var count = getElementCount("prpLcomponentKindCode");
	var factoryName = fm.prpLrepairFeeRepairFactoryCode.value;
	if (flag == "true") {
		for (var index = 0; index < count; index++) {
			if (factoryName == 1)
				fm.prpLcomponentPriceType[index].value = "专修价";

			else if (factoryName == 2 || factoryName == 3)
				fm.prpLcomponentPriceType[index].value = "市场价";
			else
				fm.prpLcomponentPriceType[index].value = "";
		}


		return true;
	}
	return false;
}

function showManyCar() {
	window.open("/claim/pages/common/carsDamageCount/DAACarsDamageCount.htm");
}

function insertCarLossFittings(arrData, count) {

	for (var i = 0; i < arrData.length; i++) {
		insertRowTableComponent('Component', 'Component_Data', fm.buttonComponentInsert);
		var arrRow = new Array();
		arrRow = arrData[i];
		fm.prpLcomponentCompCode[i + count].value = arrRow.prpLcomponentCompCode;
		fm.prpLcomponentCompName[i + count].value = arrRow.prpLcomponentCompName;
		fm.prpLcomponentMaterialFee[i + count].value = arrRow.prpLcomponentMaterialFee;
		fm.prpLcomponentQuantity[i + count].value = arrRow.prpLcomponentQuantity;
		fm.prpLcomponentOriginalId[i + count].value = arrRow.prpLcomponentOriginalId;
		fm.prpLcomponentIndId[i + count].value = arrRow.prpLcomponentIndId;
		fm.prpLcomponentSys4SPrice[i + count].value = arrRow.prpLcomponentSys4SPrice;
		fm.prpLcomponentSysMarketPrice[i + count].value = arrRow.prpLcomponentSysMarketPrice;
		fm.prpLcomponentSysMatchPrice[i + count].value = arrRow.prpLcomponentSysMatchPrice;
		fm.prpLcomponentNative4SPrice[i + count].value = arrRow.prpLcomponentNative4SPrice;
		fm.prpLcomponentNativeMarketPrice[i + count].value = arrRow.prpLcomponentNativeMarketPrice;
		fm.prpLcomponentNativeMatchPrice[i + count].value = arrRow.prpLcomponentNativeMatchPrice;
		fm.prpLcomponentSumDefLoss[i + count].value = parseFloat(arrRow.prpLcomponentMaterialFee) * parseFloat(arrRow.prpLcomponentQuantity);
		fm.prpLcomponentRemark[i + count].value = arrRow.prpLcomponentRemark;
		fm.prpLcomponentFlag[i + count].value = arrRow.prpLcomponentFlag;
		fm.prpLcomponentPriceType[i + count].value = arrRow.prpLcomponentPriceType;
		fm.prpLcomponentMaterialFee[i + count].value = arrRow.prpLcomponentMaterialFee;
		getSumDefLoss(fm.prpLcomponentMaterialFee[i + count], 2);
		setFirstMaterialFee(fm.prpLcomponentMaterialFee[i + count]);
	}
}

/**
 *更改价格类型後，重新给定损单价赋值
 */

function changePriceType(obj) {
	var priceType = obj.value;
	var rowCount = getElementOrder(obj) - 1;
	if (priceType == "S" && fm.prpLcomponentNative4SPrice[rowCount].value != "") { //专修价
		//fm.prpLcomponentSumDefLoss[rowCount].value = fm.prpLcomponentNative4SPrice[rowCount].value;
		fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNative4SPrice[rowCount].value;
		fm.prpLcomponentSumDefLoss[rowCount].value = parseFloat(fm.prpLcomponentMaterialFee[rowCount].value) * parseFloat(fm.prpLcomponentQuantity[rowCount].value);
	}
	if (priceType == "M" && fm.prpLcomponentNativeMarketPrice[rowCount].value != "") { //市场价
		//fm.prpLcomponentSumDefLoss[rowCount].value = fm.prpLcomponentNativeMarketPrice[rowCount].value;
		fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNativeMarketPrice[rowCount].value;
		fm.prpLcomponentSumDefLoss[rowCount].value = parseFloat(fm.prpLcomponentMaterialFee[rowCount].value) * parseFloat(fm.prpLcomponentQuantity[rowCount].value);
	}
	if (priceType == "O" && fm.prpLcomponentNativeMatchPrice[rowCount].value != "") { //副厂价
		//fm.prpLcomponentSumDefLoss[rowCount].value = fm.prpLcomponentNativeMatchPrice[rowCount].value;
		fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNativeMatchPrice[rowCount].value;
		fm.prpLcomponentSumDefLoss[rowCount].value = parseFloat(fm.prpLcomponentMaterialFee[rowCount].value) * parseFloat(fm.prpLcomponentQuantity[rowCount].value);
	}
}

/**
 *定损单价不能大於本地报价
 */

function checkSumDefLoss(obj) {
	var rowCount = getElementOrder(obj) - 1;
	var priceType = fm.prpLcomponentPriceType[rowCount].value;
	var materialFee = fm.prpLcomponentMaterialFee[rowCount].value; //定损单价
	var compCode = fm.prpLcomponentCompCode[rowCount].value;
	if (compCode != "" && compCode != null && compCode != "00") {
		if (priceType == "S") { //专修价
			if (materialFee > fm.prpLcomponentNative4SPrice[rowCount].value) {
				alert(i18n.certainLoss.noFeePricePrice); //定损单价不能大於本地报价
				fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNative4SPrice[rowCount].value;
			}
		}
		if (priceType == "M") { //市场价
			if (materialFee > fm.prpLcomponentNativeMarketPrice[rowCount].value) {
				alert(i18n.certainLoss.noFeePricePrice); //定损单价不能大於本地报价
				fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNativeMarketPrice[rowCount].value;
			}
		}
		if (priceType == "O") { //副厂价
			if (materialFee > fm.prpLcomponentNativeMatchPrice[rowCount].value) {
				alert(i18n.certainLoss.noFeePricePrice); //定损单价不能大於本地报价
				fm.prpLcomponentMaterialFee[rowCount].value = fm.prpLcomponentNativeMatchPrice[rowCount].value;
			}
		}
	}
}

//清除定损单价

function changeRepairFactoryCode() {
	if (confirm("确认是否更新全部定损单价！")) {
		var factoryCode = fm.prpLrepairFeeRepairFactoryCode.value;
		if (factoryCode == "01") { //4s店
			var count = getElementCount("prpLcomponentNative4SPrice");
			for (var index = 1; index < count; index++) {
				fm.prpLcomponentPriceType[index].value = "S";
				fm.prpLcomponentMaterialFee[index].value = fm.prpLcomponentNative4SPrice[index].value;
				fm.prpLcomponentSumDefLoss[index].value = parseFloat(fm.prpLcomponentMaterialFee[index].value) * parseFloat(fm.prpLcomponentQuantity[index].value);
			}
		}
		if (factoryCode == "02") { //一类店
			var count = getElementCount("prpLcomponentNativeMarketPrice");
			for (var index = 1; index < count; index++) {
				fm.prpLcomponentPriceType[index].value = "M";
				fm.prpLcomponentMaterialFee[index].value = fm.prpLcomponentNativeMarketPrice[index].value;
				fm.prpLcomponentSumDefLoss[index].value = parseFloat(fm.prpLcomponentMaterialFee[index].value) * parseFloat(fm.prpLcomponentQuantity[index].value);
			}
		}
		if (factoryCode == "03") { //二类店
			var count = getElementCount("prpLcomponentNativeMarketPrice");
			for (var index = 1; index < count; index++) {
				fm.prpLcomponentPriceType[index].value = "M";
				fm.prpLcomponentMaterialFee[index].value = fm.prpLcomponentNativeMarketPrice[index].value;
				fm.prpLcomponentSumDefLoss[index].value = parseFloat(fm.prpLcomponentMaterialFee[index].value) * parseFloat(fm.prpLcomponentQuantity[index].value);
			}
		}
		if (factoryCode == "04") { //其他
			var count = getElementCount("prpLcomponentNativeMatchPrice");
			for (var index = 1; index < count; index++) {
				fm.prpLcomponentPriceType[index].value = "O";
				fm.prpLcomponentMaterialFee[index].value = fm.prpLcomponentNativeMatchPrice[index].value;
				fm.prpLcomponentSumDefLoss[index].value = parseFloat(fm.prpLcomponentMaterialFee[index].value) * parseFloat(fm.prpLcomponentQuantity[index].value);
			}
		}
	}
}
