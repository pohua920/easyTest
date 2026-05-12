/*****************************************************************************
 * DESC       ：查勘登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-06-03
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 检查查勘登记
 *@param       无
 *@return      通过返回true,否则返回false
 */

function checkForm() {
	fm.prpLthirdPartyLicenseNo[1].disabled = false;
	fm.prpLthirdPartyBrandName[1].disabled = false;
	fm.carKindCode[1].disabled = false;
	fm.licenseColorCode[1].disabled = false;
	return true;
}


/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */

function submitForm() {
	if (checkForm() == false) {
		return false;
	}

	fm.buttonSave.disabled = true;
	fm.submit();
	return true;
}

/**
 *@description 清除
 *@param       无
 *@return      通过返回true,否则返回false
 */

function resetForm() {
	if (window.confirm("確定要清除嗎？")) {
		location.href = location.href;
		return true;
	} else {
		return false;
	}
}


/**
 *@description 将案件转换为简易赔案
 *@return      通过返回true,否则返回false
 */

function changeToQuickCase() {
	var list = document.getElementsByName("prpLcheckRemark");
	if (list.length > 0) {
		var maxLength = 100;
		var property = list[0];
		var factLength = CheckStrLen(property.value);
		if (factLength > maxLength) {
			alert(i18n.check.noteLong + "(\"" + factLength + "\")" + i18n.check.maximumLength); //备注长度过长          ，最大长度为100
			return false;
		}
	}
	//需要在查勘信息中，根据具体信息情况判断是否可以直接进行简易赔案的转换，是初步筛选，比如有人伤信息等
	fm.prpLthirdPartyLicenseNo[1].disabled = false;
	fm.prpLthirdPartyBrandName[1].disabled = false;
	fm.carKindCode[1].disabled = false;
	fm.licenseColorCode[1].disabled = false;
	//由於入口成功之後，是要读registNo和quickCaseStatus所以要传参数的。
	fm.action = "/claim/quickCaseBeforeEdit.do?registNo=" + fm.prpLcheckRegistNo.value + "&quickCaseStatus=01&editType=ADD";
	fm.submit();




}
//检查字符长度
function CheckStrLen(value) {
	return value.replace(/[^\x00-\xff]/g, "**").length;
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	if (saveType == "4") {
		for (i = 1; i < fm.prpLpersonTracePersonNo.length; i++) {
			if (fm.prpLpersonTraceJobCode1[i].value != "") {
				if (fm.prpLpersonTraceJobCode2[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry2); //请选择二级行业！
					return false;
				}
				if (fm.prpLpersonTraceJobCode[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry3); //请选择三级行业！
					return false;
				}
			}
			if (fm.prpLpersonTraceJobCode2[i].value != "") {
				if (fm.prpLpersonTraceJobCode[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry3); //请选择三级行业！
					return false;
				}
			}
		}
		//如果輸入了从事行业，一级、二级和三级都要輸入
		if (fm.dealFastFlag) {
			if (fm.dealFastFlag[0].checked == true) {
				if (fm.indemnityDuty.value != '2') {
					alert(i18n.check.northPointsResponsibility); //北分快速处理必须同等责任！
					return false;
				}
			}
		}
		var prpLcheckDamageStartDate = fm.prpLcheckDamageStartDate.value;
		prpLcheckDamageStartDate = prpLcheckDamageStartDate.substring(0, 10);
		var prpLcheckCheckDate = fm.prpLcheckCheckDate.value;
		if (prpLcheckCheckDate < prpLcheckDamageStartDate) {
			alert(i18n.check.surveyDateAccident); //查勘日期不能在出险日期之前！！
			return false;
		}
		var errorMessage = "";
		var flag = false;
		var lossFlaglength = fm.all("prpLthirdPartyLossFlag").length;
		for (var k = 1; k < lossFlaglength; k++) {
			var lossFlag = fm.prpLthirdPartyLossFlag[k].value;
			var licenseNo = fm.prpLthirdPartyLicenseNo[k].value;

			if (lossFlag.length < 1) {
				if (licenseNo == null) licenseNo = "";
				errorMessage = errorMessage + "車牌號為：" + licenseNo + "的車是否受損不允許為空\n";
			}
		}
		var prpLcheckManageType = fm.prpLcheckManageType.value;
		if(prpLcheckManageType!="1"){
			var policeUnit = fm.prpLcheckPoliceUnit.value;
			if(trim(policeUnit).length==0){
				errorMessage = errorMessage + "警方單位不允許為空\n";
			}
			var policeName = fm.prpLcheckPoliceName.value;
			if(trim(policeName).length==0){
				errorMessage = errorMessage + "警員姓名不允許為空\n";
			}
		}
		var context = fm.prpLregistTextContextInnerHTML.value;
		if (trim(context).length < 1) {
			errorMessage = errorMessage + "查勘報告不允許為空\n";

		}
		// 查勘人判断
		var Checker1 = fm.prpLcheckChecker1.value;
		var Checker2 = fm.prpLcheckChecker2.value;
		if (Checker1 == Checker2) {
			errorMessage = errorMessage + "查勘人 1和查勘人 2不能為同一人;\n";
		}

		var thirdCarLicenses1 = document.getElementsByName('prpLthirdPartyLicenseNo');
		var carCount = thirdCarLicenses1.length;
		for (var i = carCount - 1; i > 0; i--) {
			for (var k = 0; k < i; k++) {
				if (thirdCarLicenses1[k].value != "") {
					if (thirdCarLicenses1[i].value == thirdCarLicenses1[k].value) {
						errorMessage = errorMessage + "輸入車牌號重復\n";
						break;
					}
				}
			}
		}

		if (fm.prpLcheckLiceseNo.value == null || fm.prpLcheckLiceseNo.value.length == 0) {
//			alert("牌照號碼不能為空!");
//			return false;
		}else{
			var re = /^[\u4e00-\u9fa5a-zA-Z0-9- ]{1,12}$/;
			if (fm.prpLcheckLiceseNo.value.search(re) == -1) {
				alert("輸入的牌照號碼格式不正確");
				return false;
			}
		}


		var carKindCodeList = document.getElementsByName('carKindCode');
		for (var i = 2; i < carCount; i++) {
			if (carKindCodeList[i].value != '98' && carKindCodeList[i].value != '99') {
				license = thirdCarLicenses1[i];
				var strInput = license.value;
				if (strInput == null || strInput.length == 0) {
					alert("損失資訊中的牌照號碼不能為空!");
					return false;
				}

				var re = /^[\u4e00-\u9fa5a-zA-Z0-9- ]{1,12}$/;
				if (strInput.search(re) == -1) {
					alert("損失資訊中輸入的牌照號碼格式不正確");
					return false;
				}
			}
		}

		var strPrpLdriverApanageCode = document.getElementsByName('prpLdriverApanageCode');
		var strMustInputFlag = document.getElementsByName('MustInputFlag');
		if (strMustInputFlag.length > 0 && strMustInputFlag[0].value == '1' && strPrpLdriverApanageCode.length > 1) {
			for (var i = 1; i < strPrpLdriverApanageCode.length; i++) {
				if (strPrpLdriverApanageCode[i].value == "") {
					errorMessage = errorMessage + "駕駛員屬地是必填項\n";
					break;
				}
			}
		}
		var TracePersonName = document.getElementsByName('prpLpersonTracePersonName');
		for (var i = 1; i < TracePersonName.length; i++) {
			if (TracePersonName[i].value == "") {
				errorMessage = errorMessage + "人傷跟蹤信息中傷者姓名不能為空\n";
				break;
			}
		}

		var flag = 1;//声明标示变量，1表示驾驶员信息中輸入的车牌号全部是已经輸入的车辆
		//校验身份证录入
		var driverName = document.getElementsByName('prpLdriverDriverName');
		var driverSex = document.getElementsByName('driverSex');
		var drivingCarType = document.getElementsByName('drivingCarType');
		var identifyNumber = document.getElementsByName('prpLdriverIdentifyNumber');
		var driverIdentity = document.getElementsByName("prpLdriverDriverIdentity");
		for (var i = 1; i < fm.prpLdriverLicenseNo.length; i++) {
			//遍历驾驶员信息中的所有车牌号
//			if (flag == 0 || fm.prpLdriverLicenseNo[i].value == "" || fm.prpLdriverLicenseNo[i].value == null) {
//				break; //有一个司机驾驶的车辆不是已经輸入的车辆或者为空就跳出循环
//			}
			//新增校验，不再提前退出（break）
			if (identifyNumber[i].value!="" && driverIdentity[i].value=='1' && !checkIdentifyNumber(identifyNumber[i].value, driverSex[i].value)) {
				alert("請爲駕駛員 " + driverName[i].value + " 錄入正確的身份證號碼");
				return false;
			}else if (identifyNumber[i].value!="" && driverIdentity[i].value=='3' && !checkUniformNo(identifyNumber[i].value)) {
				alert("請爲駕駛員 " + driverName[i].value + " 錄入正確的身份證號碼");
				return false;
			}
			if (flag == 1 &&!(fm.prpLdriverLicenseNo[i].value == "" || fm.prpLdriverLicenseNo[i].value == null)) { //如果上一个司机驾驶的车辆是已经輸入的车辆则进入循环
				flag = 0;
				for (var j = 1; j < fm.prpLthirdPartyLicenseNo.length; j++) {
					//遍历已经輸入车辆的车牌号
					if (fm.prpLdriverLicenseNo[i].value == fm.prpLthirdPartyLicenseNo[j].value) {
						//进行比较
						flag = 1;
						break;
					}
				}
			}
		}
		if (flag == 0) {
			errorMessage = errorMessage + i18n.check.driverDrivingVehicle; //驾驶员所驾驶的车辆必须是已经輸入的车辆\n
		}
		if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		}
		//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
		if (!validateForm(fm, 'ThirdParty_Data,Driver_Data')) {
			return false;
		}
		for (var i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
			if (isRightDutyPercent(fm.prpLthirdPartyDutyPercent[i]) == false) {
				return false;
			}
		}
		if (saveType == "4") {
			if (fm.scheduleCheck.checked == true) fm.messageToScheduleCheck.value = "1";

		}
	}
	fm.buttonSaveType.value = saveType;
	//代查勘提示功能
	var checkGuideMessages = $(":input[name='checkGuideMessages']").val();
	if(checkGuideMessages.length>0&&!confirm(checkGuideMessages)){
		return false;
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;

	//只读的域变成可读的
	ableAllInput();
	fm.prpLthirdPartyLicenseNo[1].disabled = false;
	fm.prpLthirdPartyBrandName[1].disabled = false;
	fm.carKindCode[1].disabled = false;
	fm.licenseColorCode[1].disabled = false;
	fm.submit();
}

/**
 *@description 设置画面的初始值
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {
	loadCheckExt();
	if (fm.buttonThirdPartyDelete[1] != undefined) {
		fm.buttonThirdPartyDelete[1].disabled = true;
	}

//	if (fm.prpLthirdPartyLicenseNo[1] != undefined) {
//		fm.prpLthirdPartyLicenseNo[1].disabled = true;
//	}

	if (fm.prpLthirdPartyBrandName[1] != undefined) {
		fm.prpLthirdPartyBrandName[1].disabled = true;
	}
	if (fm.carKindCode[1] != undefined) {
		fm.carKindCode[1].disabled = true;
	}
	if (fm.licenseColorCode[1] != undefined) {
		fm.licenseColorCode[1].disabled = true;
	}
	if (fm.buttonDriverDelete[1] != undefined) {
		fm.buttonDriverDelete[1].disabled = true;
	}
}
/**
 *@description 设置画面的初始值
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSetForCertainLoss() {
	loadCheckExt();
	fm.buttonThirdPartyDelete[1].disabled = true;
	fm.prpLthirdPartyLicenseNo[1].readOnly = true;
}
/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet1() {
	return true;
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function fileSave(field, saveType) {
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm, 'Certify_Data')) {
		return false;
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function fileSavePop(field, saveType) {
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm, 'Certify_Data')) {
		return false;
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.target = "_self";
	fm.submit();
}

/**
 *@description 查看制定的文件
 *@param       fileName，businessNo，uploadFileName
 */

function viewFile(fileName, businessNo) {
	window.open("/claim/DAA/certify/CertifyViewFile.jsp?fileName=" + fileName + "&businessNo=" + businessNo, businessNo, "resizable=0,scrollbars=1,width=800,height=600");
}

/**
 * 隐藏输入框
 * @param field 元素
 * @param tableName tableName
 * @return 无
 */

function backSubPage(spanID) {
	var span = eval(spanID);
	if (span.style.display == 'none') {
		span.style.display = '';
	} else {
		span.style.display = 'none';
	}
}

/**
 @description 改变赔偿责任时触发，相应改变责任比例
 @param       无
 @return      无
 */

function changeIndemnityDuty() {
	var indemnityDuty = ""; //设置的值	  
	var i = 0; //循环使用


	switch (fm.indemnityDuty.value) {
	case "0":
		//全责
		indemnityDuty = "100";
		break;
	case "1":
		//主责
		indemnityDuty = "70";
		break;
	case "2":
		//同责
		indemnityDuty = "50";
		break;
	case "3":
		//次责
		indemnityDuty = "30";
		break;
	case "4":
		//无责
		indemnityDuty = "0.0";
		break;
	case "9":
		//其它
		indemnityDuty = "0.0";
		break;
	}


	for (var i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
		if (fm.insureCarFlag[i].value == "1") {
			fm.prpLthirdPartyDutyPercent[i].value = indemnityDuty;

			return isRightDutyPercent(fm.prpLthirdPartyDutyPercent[i]);
		}
	}
}

/**
 *@description 保存定损数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveCertainLossForm(field) {
	//校验  
	//人员伤亡，财产损失，三者必录一项才能进行
	var carLossRepairFeeLossItemCode = fm.carLossRepairFeeLossItemCode.length;
	var carLossComponentLossItemCode = fm.carLossComponentLossItemCode.length;
	var personSerialNo = fm.personSerialNo.length;
	var prpLpropSerialNo = fm.prpLpropSerialNo.length;
	if (carLossRepairFeeLossItemCode == undefined && carLossComponentLossItemCode == undefined && personSerialNo == undefined && prpLpropSerialNo == undefined) {
		alert(i18n.check.casualtiesPropertyLosses); //车损（修理/换件），人员伤亡，财产损失，三者至少輸入一项
		return false;
	}

	if (checkPropKindCode() == false)
		return false;
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法

	if (!validateForm(fm, 'RepairFee_Data,Component_Data,PersonFeeLoss_Data,Person_Data,Prop_Data')) {
		return false;
	}

	//如果是提交，判断是否不是nextNodeNo或者人员为空！！！！
	if (saveType == "4") {
		if (trim(fm.nextNodeNo.value).length < 1) {
			alert(i18n.check.selectSubmitNextNode) //请选择要提交的下一个节点！
			return false;
		}
		if (trim(fm.nextHandlerCode.value).length < 1) {
			alert(i18n.check.selectSubmitNextPeople) //请选择要提交的下一个人！
			return false;
		}

	}

	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
}


/**
 *@description 处理索赔资料清单
 *@param       businessNo
 */

function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}


/**
 *@description 打印索赔须知清单  2005-08-09
 *@param       businessNo
 */

function certifyDirectList(businessNo, nodeType) {
	//add print liudaoping 2013-04-15
	//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
	return false;
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirectPrint&nodeType=" + nodeType, "Print", "resizable=0,scrollbars=1,width=800,height=600");
}

function backWardPolicy(url) {
	var SHOWTYPE = "SHOW";
	var BizNo = fm.prpLcheckPolicyNo.value;
	var RiskCode = fm.prpLcheckRiskCode.value;
	var damageDate = fm.damageDate.value;
	var vURL = '/claim/pages/common/pub/PolicyShowCenter.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate + '&coreURL=' + url;
	window.open(vURL, '詳細訊息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}



function checkSchedule() {
	var scheduleCheckFlag = fm.scheduleCheckFlag.value;
	if (scheduleCheckFlag == "true") {
		if (fm.scheduleCheck.checked)
			return true;
		else
			return false;

	}
	return true;
}

function afterInitPage() {}
/**
 * @description 垫付信息确认
 */

function confirmFromPlatForm() {
	var comCode = '12000000'; //fm.prpLcheckMakeCom.value;
	var nullReportNo = '605072007119992000139'; //fm.prpLcheckRegistNo.value;
	var inputObject;
	var outputObject;
	var inputArgs = {
		comCode1: comCode,
		nullReportNo1: nullReportNo
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("getAdvanceConfirm", param, "rollbackAdvanceConfirm", inputObject, outputObject);
	DWREngine.setAsync(true);
}
/**
 *垫付信息确认
 */

function rollbackAdvanceConfirm(inputObject, outputObject, returnObject) {
	var returnInfo = returnObject;
	if (returnInfo.returnType == '1') //确认信息上传成功
		alert(i18n.check.paymentClaimCode + returnInfo.advanceNo); //垫付赔案编码：
	else
		alert(returnInfo.errorMessage);
	undisablebutton();
}
/**
 * 控制按钮的可见和不可见状态
 */

function changeAdvanceStatus(field) {
	if (field.value == '1') //全责垫付
	{
		fm.getFromPlatForm1.style.display = "";
		undoDisabledButton('buttonArea');
	} else if (field.value == '2') //无责垫付
	{
		fm.getFromPlatForm1.style.display = "none";
		alert(i18n.check.pleaseTurnIntoClaim); //请转入简易赔案处理！
		disabledAllButton('buttonArea');
		fm.buttonQuickCase.disabled = false;
	} else //其他
	{
		fm.getFromPlatForm1.style.display = "none";
		undoDisabledButton('buttonArea');
	}
}
//輸入无责方的信息
//用来打开一个新的页面然後重新輸入无责方的信息

function inputNullInfo() {
	var registNo = fm.prpLcheckRegistNo.value;
	var newWindow = window.open("/claim/advance.do?registNo=" + registNo + "&flagAgent=1", "NewWindow", "width=640,height=300,top=0,left=0,toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
//加载页面时调用的函数

function initAdvance() {
	var isSpecial = fm.isSpecial.value;
	if (isSpecial == '1' && fm.prplregistAdvance.value == '2') {
		fm.getFromPlatForm1.style.display = "none";
		alert(i18n.check.pleaseTurnIntoClaim); //请转入简易赔案处理！
		disabledAllButton('buttonArea');
		fm.buttonQuickCase.disabled = false;
	}
}
/**
 *@description 检查报案时所有车辆輸入责任比例之和小於100%
 *@param       无
 *@return      通过返回true,否则返回false
 */

function checkPrpLthirdPartyDutyPercent() {
	var thirdPartyDutyPercent = 0;
	for (var i = 1; i < fm.prpLthirdPartyDutyPercent.length; i++) {
		if (fm.prpLthirdPartyDutyPercent[i].value != null && fm.prpLthirdPartyDutyPercent[i].value != 0) {
			thirdPartyDutyPercent = thirdPartyDutyPercent + parseInt(fm.prpLthirdPartyDutyPercent[i].value);
		}
	}
	if (thirdPartyDutyPercent > 100) {
		alert(i18n.check.vehicleLiabilityRatioAgain); //所有车辆责任比例之和大於100，请重新输入
		return false;
	} else {
		return true;
	}
}