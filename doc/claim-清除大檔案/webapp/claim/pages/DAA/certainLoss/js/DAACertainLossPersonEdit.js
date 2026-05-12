/*****************************************************************************
 * DESC       ：人员列表增加JS
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-08-01
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
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
 * 设置setPrpLpersonSerialNo
 */

function setPrpLpersonSerialNo() {
	var count = getElementCount("prpLpersonSerialNo");
	for (var i = 0; i < count; i++) {
		if (count != 1) {
			fm.prpLpersonSerialNo[i].value = i;
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
		if (elements[i].name == "personSerialNo") {
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
	var pageSerialNo = 0;
	pageSerialNo = parseInt(fm.personSerialNo(recentDeletedRowNo - 1).value);
	var order = recentDeletedRowNo - 1; //顺序改为以0开始
	var oTBODY = document.getElementsByName(pageCode)[pageSerialNo].tBodies.item(0);
	order = getRowCurrPage(field, pageCode);
	for (var i = 0; i < controlRowsCount; i++) {
		oTBODY.removeChild(oTBODY.rows[order * controlRowsCount]);
	}
	sumPersonLossFee();
	return recentDeletedRowNo - 1;
}

/**
 * 得到当前的行所在当前页位置
 */

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
	var pageSerialNo = 0;
	pageSerialNo = parseInt(fm.personSerialNo(recentRowNo - 1).value);
	//index--; //顺序改为以0开始
	//得到Input域的名字
	elements = getTableElements("Person");

	for (i = 0; i < elements.length; i++) {
		//得到人员费用索引起始位置及人员费用的记录数
		if (elements[i].name == "personSerialNo") {

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
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	if (saveType == "4") {
		for (i = 1; i < fm.prpLpersonSerialNo.length; i++) {
			if (fm.prpLpersonTraceJobCode1[i].value != "") {
				if (fm.prpLpersonTraceJobCode2[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry2); //请选择二级行业！
					//fm.prpLpersonTraceJobName2[i].focus();
					return false;
				}
				if (fm.prpLpersonJobCode[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry3); //请选择三级行业！
					//fm.prpLpersonTraceJobName[i].focus();
					return false;
				}
			}
			if (fm.prpLpersonTraceJobCode2[i].value != "") {
				if (fm.prpLpersonJobCode[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry3); //请选择三级行业！
					//fm.prpLpersonTraceJobName[i].focus();
					return false;
				}
			}
		}
		//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
		if (!validateForm(fm, 'PersonFeeLoss_Data,person_Data')) {
			return false;
		}
		if (!checkPersonFeeLoss()) {
			return false;
		}
		for (var i = 1; i < fm.prpLpersonPersonName.length; i++) {
			var prpLpersonPersonName = fm.prpLpersonPersonName[i].value;
			var prpLpersonPersonSex = fm.prpLpersonPersonSex[i].value;
			var prpLpersonIdentifyNumber = fm.prpLpersonIdentifyNumber[i].value;
			var prpLpersonKindCode = fm.prpLpersonKindCode[i].value;
			var prpLpersonKindName = fm.prpLpersonKindName[i].value;
			if (fm.prpLpersonIdentifyNumber[i].value.length > 0) {
				if (!checkIdentifyNumber(prpLpersonIdentifyNumber, prpLpersonPersonSex)) {
					alert("請爲傷者 " + prpLpersonPersonName + " 錄入正確的身份證號碼");
					return checkIdentifyNumber(prpLpersonIdentifyNumber, prpLpersonPersonSex);
				}
			}
			if(prpLpersonKindCode=="") {
				alert("“險別代碼”不能為空，請錄入！");
				return false;
			}
			if(prpLpersonKindName=="") {
				alert("“險別名稱”不能為空，請錄入！");
				return false;
			}
		}
	}
	sumPrpLverifyLossWarpDefLoss();
	fm.buttonSaveType.value = saveType;
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
}

function checkPersonFeeLoss() {
	var personCount = getElementCount("personSerialNo");
	var prpLpersonCount = getElementCount("prpLpersonSerialNo");
	var i = 0;
	var j = 0;
	var addNewRepairCompentRow = -1; //默认没有增加一条记录数据
	var compensatebackFlag = fm.prpLverifyLossCompensateFlag.value; //理算退回的标记
	if (personCount <= 1&&prpLpersonCount>1) {
		alert(i18n.certainLoss.casualtiesCostInfoOne); //人员伤亡费用清单信息至少录入一条!
		return false;
	}

	for (i = 1; i < fm.personSerialNo.length; i++) {
		if (((fm.prpLpersonFeeTypeCode[i].value).toString().length) < 1) {

			errorMessage(i18n.certainLoss.costCodeCannotEmpty); //费用代码不能为空!
			return false;
		}
		if (((fm.prpLpersonFeeTypeName[i].value).toString().length) < 1) {

			errorMessage(i18n.certainLoss.costNameCannotEmpty); //费用名称不能为空!
			return false;
		}

		if (fm.prpLpersonCompensateBackFlag[i].value != "1") addNewRepairCompentRow = 1;

	}
	//如果是理算回退的，並且没有增加新行的话，那么可以直接提交到理算 
	if (compensatebackFlag == "1" && addNewRepairCompentRow < 0) fm.NextComeBackCompensate.value = "1";
	return true;
}
//设置偏差金额
function sumPrpLverifyLossWarpDefLoss(){
	var prpLpropSumSumDefLoss = $("input[name='prpLpersonSumDefLossSum']").get(1).value;//定损金额
	$("input[name='prpLverifyLossWarpDefLoss']").val(prpLpropSumSumDefLoss);//偏差金额
	$("input[name='prpLverifyLossSumPreDefLoss']").val(prpLpropSumSumDefLoss);//定损金额
	$("input[name='prpLverifyLossSumDefLoss']").val(prpLpropSumSumDefLoss);//核损金额
}