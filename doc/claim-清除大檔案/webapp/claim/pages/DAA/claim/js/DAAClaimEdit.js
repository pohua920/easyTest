/*****************************************************************************
 * DESC       ：报案登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2013-09-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
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
/**
 *@description 检查报案登记
 *@param       无
 *@return      通过返回true,否则返回false
 */

function checkForm() {
	return true;
}

/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {
	var indemnityDuty = fm.indemnityDuty.value;
	if (indemnityDuty == "0")
		fm.prpLclaimIndemnityDutyRate.value = 100;

	//必须先验证是不是有第一辆车的情况下，才能进行设置的。
	if (fm.prpLthirdPartySerialNo.length > 1) {
		fm.buttonThirdPartyDelete[1].disabled = true;
		//涉案车辆信息中本保单车辆号牌底色、车辆种类、厂牌型号不允许修改
		fm.prpLthirdPartyBrandName[1].disabled = true;
		fm.carKindCode[1].disabled = true;
		fm.licenseColorCode[1].disabled = true;
//		fm.prpLthirdPartyLicenseNo[1].disabled = true;
//		fm.prpLthirdPartyLicenseNo[1].readOnly = true;
	}
	if (fm.buttonDriverDelete[1] != undefined) {
		fm.buttonDriverDelete[1].disabled = true;
	}

	var payFee = fm.prpLclaimIntPayFee.value;
	var errorMessage = "";
	if (payFee == -1) {
		errorMessage = errorMessage + i18n.certainLoss.policyPremiumNoPay; //此保单保费未缴,请慎重处理！！ \n
	}else if (payFee == 0) {
		errorMessage = errorMessage + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
	}
	if (errorMessage != "") {
		alert(errorMessage);
	}
	$("input:enabled").filter(":button,:submit,:reset").ajaxStart(function() {
		$(this).attr("disabled", true);//请求开始禁用按钮
	}).ajaxComplete(function() {
		$(this).attr("disabled", false);//请求完成恢复按钮
	});
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
	fm.buttonSaveFinish.disabled = true;
	fm.buttonSaveFinishSubmit.disabled = true;
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
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	var flag = fm.buttonSubmitFlag.value;
	if ("N" == flag) {
		return false;
	}
	//textarea文本框设置值
	var errorMessage = "";
	if (saveType == "4") {
		var context = fm.prpLltextContextInnerHTML.value;
		if (context.length < 1) {
			errorMessage = errorMessage + i18n.claim.dangerNotAllowEmpty; //出险摘要不允许为空\n
		}
		var thirdCarLicenses = document.getElementsByName('prpLthirdPartyLicenseNo');
		var carKindCodeList = document.getElementsByName('carKindCode');
		var prpLthirdPartyInsureComCodeList = document.getElementsByName('prpLthirdPartyInsureComCode');
		var prpLthirdPartyInsureComNameList = document.getElementsByName('prpLthirdPartyInsureComName');
		var prpLthirdPartyCarryingUnitList = document.getElementsByName('prpLthirdPartyCarryingUnit');
		var prpLthirdPartyInsuranceNoList = document.getElementsByName('prpLthirdPartyInsuranceNo');
		var prpLthirdPartyIsInsuranceList = document.getElementsByName('prpLthirdPartyIsInsurance');
		var prpLthirdPartyCarryingNumberList = document.getElementsByName('prpLthirdPartyCarryingNumber');
		var prpLthirdPartyInsuredIdentityList = document.getElementsByName('prpLthirdPartyInsuredIdentity');
		var carCount = document.getElementsByName('prpLthirdPartyLicenseNo').length;
		var riskCode = document.getElementsByName('prpLclaimRiskCode')[0].value;
		
		/*
		 mantis： CLM0042 ，處理人員：BK007 蘇哲，需求單編號：CLM0042「任意車險查詢平台」調整理賠資料傳輸-肇責未釐清，不計次 --- start
		  處理過程：
			增加“肇責類型”選項[４：肇責未釐清，不計次] ，限定任意車險在立案可以選擇，強制車險會跳出 "強制險賠案“肇責類型”不能選擇[４：肇責未釐清，不計次]！"
			參考文件 P:\01.需求變更\理賠\CLM0042.「任意車險查詢平台」調整理賠資料傳輸-肇責未釐清
		*/
		var prpLclaimPropAccidentType = fm.prpLclaimPropAccidentType.value;
		var prpLclaimCarAccidentType = fm.prpLclaimCarAccidentType.value;
		if(riskCode == "B01"){
			if(prpLclaimPropAccidentType == 4 || prpLclaimCarAccidentType == 4){
				alert("強制險賠案“肇責類型”不能選擇[４：肇責未釐清，不計次]！");
				return false;
			}
		}
		/*
		 mantis： CLM0042 ，處理人員：BK007 蘇哲，需求單編號：CLM0042「任意車險查詢平台」調整理賠資料傳輸-肇責未釐清 --- end
		*/
		
		if(carCount < 3 && riskCode == "B01"){
			alert("您好：強制險賠案請至少輸入一條“第三方車輛訊息”！");
			return false;
		}
		for (var i = 2; i < carCount; i++) {
			if (carKindCodeList[i] == null || carKindCodeList[i].value == "") {
				alert("車輛種類不能為空!");
				return false;
			}
			if (carKindCodeList[i].value != '98' && carKindCodeList[i].value != '99') {
				license = thirdCarLicenses[i];
				var strInput = license.value;
				if (strInput == null || strInput.length == 0) {
					alert("牌照號碼不能為空!");
					return false;
				}
				var re = /^[\u4e00-\u9fa5a-zA-Z0-9- ]{1,12}$/;
				if (strInput.search(re) == -1) {
					alert("輸入的牌照號碼格式不正確");
					return false;
				}
				if (prpLthirdPartyIsInsuranceList[i] == null || prpLthirdPartyIsInsuranceList[i].value == "") {
					alert("是否有保強制險不能為空!");
					return false;
				}
				if (prpLthirdPartyIsInsuranceList[i].value != '0') {
					if (prpLthirdPartyInsureComCodeList[i] == null || prpLthirdPartyInsureComCodeList[i].value == "") {
						alert("承保公司代號不能為空!");
						return false;
					}
					if (prpLthirdPartyInsureComNameList[i] == null || prpLthirdPartyInsureComNameList[i].value == "") {
						alert("承保公司名稱不能為空!");
						return false;
					}
					if (prpLthirdPartyInsuranceNoList[i] == null || prpLthirdPartyInsuranceNoList[i].value == "") {
						alert("強制保險證編號不能為空!");
						return false;
					}
				}
				if (prpLthirdPartyCarryingUnitList[i] == null || prpLthirdPartyCarryingUnitList[i].value == "") {
					alert("承載單位不能為空!");
					return false;
				}
				if (prpLthirdPartyCarryingNumberList[i] == null || prpLthirdPartyCarryingNumberList[i].value == "") {
					alert("承載數量不能為空!");
					return false;
				}
				if (prpLthirdPartyInsuredIdentityList[i] == null || prpLthirdPartyInsuredIdentityList[i].value == "") {
					alert("被保險人身份不能為空!");
					return false;
				}
			}
		}
		if (( !! fm.prpLclaimReceiptDate) && fm.prpLclaimReceiptDate.value == "") {
			alert("收件日期不允許為空");
			return false;
		}
		//ValidateData.js中的校验不起作用时，因为没有调用校验方法
		if (!validateForm(fm, 'ClaimLoss_Data,ThirdParty_Data,Driver_Data')) {
			return false;
		}
		for (i = 1; i < fm.prpLpersonTracePersonNo.length; i++) {
			if (fm.prpLpersonTraceJobCode1[i].value != "") {
				if (fm.prpLpersonTraceJobCode2[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry2); //请选择二级行业！
					fm.prpLpersonTraceJobName2[i].focus();
					return false;
				}
				if (fm.prpLpersonTraceJobCode[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry3); //请选择三级行业！
					fm.prpLpersonTraceJobName[i].focus();
					return false;
				}
			}
			if (fm.prpLpersonTraceJobCode2[i].value != "") {
				if (fm.prpLpersonTraceJobCode[i].value == "") {
					alert(i18n.certainLoss.pleaseSelectIndustry3); //请选择三级行业！
					fm.prpLpersonTraceJobName[i].focus();
					return false;
				}
			}
		}
		var thirdCarLicenses1 = document.getElementsByName('prpLthirdPartyLicenseNo');
		var carCount = thirdCarLicenses1.length;
		for (var i = carCount - 1; i > 0; i--) {
			for (var k = 0; k < i; k++) {
				if (thirdCarLicenses1[k].value != "") {
					if (thirdCarLicenses1[i].value == thirdCarLicenses1[k].value) {
						errorMessage = errorMessage + "輸入车牌号重复\n";
						break;
					}
				}
			}
		}
		//驾驶员所驾驶的车辆必须是已经輸入的车辆
		var flag = 1;
		//声明标示变量，1表示驾驶员信息中輸入的车牌号全部是已经輸入的车辆
		for (var i = 1; i < fm.prpLdriverLicenseNo.length; i++) {
			//遍历驾驶员信息中的所有车牌号
			if (flag == 0 || fm.prpLdriverLicenseNo[i].value == "" || fm.prpLdriverLicenseNo[i].value == null) {
				break; //有一个司机驾驶的车辆不是已经輸入的车辆或者为空就跳出循环
			}
			if (flag == 1) { //如果上一个司机驾驶的车辆是已经輸入的车辆则进入循环
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

		for (var i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
			if (isRightDutyPercent(fm.prpLthirdPartyDutyPercent[i]) == false) {
				return false;
			}
		}
		//验证估损金额
		if (!checkLoss()) {
			return false;
		}
		//校验身份证录入,且駕照號碼不能为空
		var driverName = document.getElementsByName('prpLdriverDriverName');
		var driverSex = document.getElementsByName('driverSex');
		var identifyNumber = document.getElementsByName('prpLdriverIdentifyNumber');
		var drivingLicenseNo = document.getElementsByName('prpLdriverDrivingLicenseNo');
		var driverIdentity = document.getElementsByName("prpLdriverDriverIdentity");
		for (var i = 1; i < driverName.length; i++) {
			if(identifyNumber[i].value==""){
				alert("請爲駕駛員 " + driverName[i].value + " 錄入身份證號碼！");
				return false;
			}else if(driverIdentity[i].value=="1" && !checkIdentifyNumber(identifyNumber[i].value, driverSex[i].value)) {
				alert("請爲駕駛員 " + driverName[i].value + " 錄入正確的身份證號碼！");
				return false;
			}else if(driverIdentity[i].value=="3" && !checkUniformNo(identifyNumber[i].value)) {
				alert("請爲駕駛員 " + driverName[i].value + " 錄入正確的統一編號");
				return false;
			}else if(drivingLicenseNo[i] == null || drivingLicenseNo[i].value==""){
				alert("請爲駕駛員 " + driverName[i].value + " 錄入駕照號碼！");
				return false;
			}
		}
		if(!checkAllLimitItemKind()){
			return false;
		}
	}
	$("#ClaimLoss").find("tr[name='prpLclaimLossObject']").each(function () {
		var kindCode = $(this).find(":input[name='prpLclaimLossKindCode']").val(); //險別
		var feeCategory = $(this).find(":input[name='prpLclaimLossFeeCategory']").val(); //範圍
		if (kindCode == "31" && feeCategory != "M" && feeCategory != "H" && feeCategory != "D") {
			errorMessage = "險別估損金額訊息：31險別的範圍只能是“醫療”、“殘廢”或“死亡”！";
			return false;
		} else if(kindCode == "32" && feeCategory != "C" && feeCategory != "G" && feeCategory != "O"){
			errorMessage = "險別估損金額訊息：32險別的範圍只能是“車損”、“物損”或“其他”！";
			return false;
		}
	});
	fm.buttonSaveType.value = saveType;
	//因为sumclaim值和分项不一致
	collectClaimLossNew(field);
	var SumClaim = parseFloat(fm.prpLclaimSumClaim.value);
	//当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	//必须先验证是不是有第一辆车的情况下，才能进行设置的。
	if (fm.prpLthirdPartySerialNo.length > 1) {
		fm.prpLthirdPartyBrandName[1].disabled = false;
		fm.carKindCode[1].disabled = false;
		fm.licenseColorCode[1].disabled = false;
		fm.prpLthirdPartyLicenseNo[1].disabled = false;
	}
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	var claim_days = fm.claim_days.value;
	var standardDays = fm.standardDays.value;
	if (claim_days == 0) {
		if (!confirm(i18n.claim.caseGreaterSystemTime + standardDays + i18n.claim.dayWhetherSubmit)) { //立案天数大於系统规定时间     天，是否提交？
			return false;
		}
	}
	field.disabled = true;
	fm.submit();

	return true;
}

function loadForm() {}


/**
 @description 校验索赔金额
 @param       无
 @return      boolean
 */

function checkLoss() {
	//1.检查必须要有一条记录
	if (getRowsCount("ClaimLoss") == 0) {
		errorMessage("估損金額金額信息至少要有一條記錄!");

		return false;
	}
	//2币别不能为空的
	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
			errorMessage("第" + j + "條估損金額中幣別不能為空!");
			fm.prpLclaimLossCurrency[j].focus();
			return false;
		}
		
		//modify by zhangmaoyu 20131122 reason:与ValidateData.js中的校验有重叠
//		if (isEmptyField(fm.prpLclaimLossKindCode[j])) {
//			errorMessage("第" + j + "條事故估損金額中險別代碼不能為空!");
//			return false;
//		}

		//险别和币别相同的，必须合並
		for (var n = j + 1; n < fm.prpLclaimLossCurrency.length; n++) {}

		//mantis：CLM0244 ，處理人員： DP0713 ，需求單編號：新核心-車險立案預估金額判斷新增不可為零
		if (isEmptyZeroField(fm.prpLclaimLossKindLoss[j])) {
			errorMessage("第" + j + "條估損金額中上報估損金額不能為零！");
			fm.prpLclaimLossSumClaim[j].focus();
			return false;
		}
	}
	return true;
}

//得到一页的多行纪录的记录数
//页名称

function getRowsCount(PageCode) {
	var oTBODY = document.all(PageCode).tBodies.item(0);
	var intCount = oTBODY.rows.length;
	return intCount;
}

/**
 @description 改变赔偿责任时触发，相应改变责任比例
 @param       无
 @return      无
 */

function changeIndemnityDuty() {
	var indemnityDuty = ""; //设置的值
	var i = 0; //循环使用
	fm.prpLclaimIndemnityDutyRate.readOnly = true;
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
		fm.prpLclaimIndemnityDutyRate.readOnly = false;
		break;
	}

	fm.prpLclaimIndemnityDutyRate.value = indemnityDuty;
	for (var i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
		if (fm.insureCarFlag[i].value == "1") {
			fm.prpLthirdPartyDutyPercent[i].value = indemnityDuty;
			return isRightDutyPercent(fm.prpLthirdPartyDutyPercent[i]);
		}
	}

}

function changeIndemnityDuty1() {
	var i = 0; //循环使用
	for (var i = 1; i < fm.prpLthirdPartySerialNo.length; i++) {
		if (fm.insureCarFlag[i].value == "1") {
			fm.prpLthirdPartyDutyPercent[i].value = fm.prpLclaimIndemnityDutyRate.value;
		}
	}
}


/**
 * 按钮单击事件，用於条款的显示
 */

function buttonOnClick1(fieldObject) {
	var intIndex = parseInt(fieldObject.num);
	var spanId = 'span_Engage_Context';

	if (isNaN(fm.button_Engage_Open_Context.length)) {} else { //多行
		spanId = 'span_Engage_Context' + "[" + intIndex + "]";
	}
	showSubPage2(spanId);
}
/**
 *显示输入框
 *leftMove 默认值0，坐标左移leftMove
 */

function showSubPage2(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;

	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}


function generateNoClaimText() {
	var prpLlText = "";
	var strSpace = "    ";
	var prpLclaimInsuredName = fm.prpLclaimInsuredName.value;
	var message1 = strSpace + "您的理賠申請本公司已經獲悉，根據保險條款及相關法律，並經審慎核定您所提供的有關資料與證明，本公司認為，您的申請事由不能成立，並做如下處理： \n";
	var message2 = strSpace + "不予立案並退件/不予給付保險金/退還解約金" + CURRENCYINFO.LOCAL_CURRENCY + "0.00元 ,解除本保險合約。\n";
	var message3 = strSpace + "本公司做出上述決定的理由是：\n\n";
	var message4 = strSpace + "若您對本公司的處理有異議，可於接到本通知之日起十日內向本公司理賠部門尋求解釋。若您覺得仍無法獲得滿意的答复，您還享有以下權利：\n";
	var message5 = strSpace + "向仲裁機關申請仲裁/向人民法院提起訴訟 \n";
	var message6 = strSpace + "請申請並審慎運用您的上述權利。\n";
	prpLlText = "尊敬的" + prpLclaimInsuredName + "女士/先生：\n" + message1;
	prpLlText = prpLlText + message2 + message3 + message4 + message5 + message6;
	fm.prpLclaimContext.value = prpLlText;
	return true;
}

function collectCurrency() {
	var collectCurr = "";
	var collectTemp = new Array();
	collectCurr = "分幣別匯總結果:\n";
	for (var i = 1; i < fm.prpLclaimLossCurrency.length; i++) {
		var hasElement = false;
		var currency = fm.prpLclaimLossCurrency[i].value;
		var currencyName = fm.prpLclaimLossCurrencyName[i].value;
		var sumLossAmount = 0;
		var claimLossSumClaim = 0;
		//循环分币别统计
		for (var ii = 1; ii < fm.prpLclaimLossCurrency.length; ii++) {
			if (currency == fm.prpLclaimLossCurrency[ii].value) {
				claimLossSumClaim = parseFloat(fm.prpLclaimLossSumClaim[ii].value);
				sumLossAmount = sumLossAmount + claimLossSumClaim;
			}
		}

		if (currency == CURRENCYINFO.LOCAL_CURRENCY) { //获取不计免赔额
			sumLossAmount = sumLossAmount + parseFloat(fm.exceptDeductibleRateAll.value);
		}
		//先判断当前数组中是否已有此币别,如果没有再进行保存
		for (var j = 0; j < collectTemp.length; j++) {
			if (collectTemp[j] == currency) {
				hasElement = true;
			}
		}
		//如果当前数组中有此元素，不再进行统计
		if (hasElement) {
			continue;
		}
		//存入数组中
		collectTemp[i - 1] = currency;
		collectCurr = collectCurr + currency + "  " + currencyName + "  " + pointTwo(round(sumLossAmount, 0), 0) + "元\n";

	}
	if (collectCurr.length > 0) {
		alert(collectCurr);
		return false;
	}
}

function checkDeductible() {
	var sumCertainLossA = 0.0;
	var tempLossA = 0;
	var prpLDeductible = parseFloat(fm.prpLDeductible.value);
	var tempflag = "0";
	var prpLlossDtoCompelPayCount = getElementCount("prpLclaimLossKindCode");
	//换件费
	for (var index = 1; index < prpLlossDtoCompelPayCount; index++) {

		var kindCode = trim(fm.prpLclaimLossKindCode[index].value);
		if (kindCode == RISKINFO.KINDCODE_D_A) {
			tempLossA = parseFloat(fm.prpLclaimLossSumClaim[index].value);
			if (isNaN(tempLossA)) {
				tempLossA = 0;
			}
			sumCertainLossA = sumCertainLossA + tempLossA;
		} else {
			tempflag = "1";
		}
	}
	if (sumCertainLossA < prpLDeductible && tempflag != "1") {
		alert(i18n.certainLoss.optionalFranchiseProcess); //车损险定损金额小於可选免赔额，请做免赔额注销处理
		return false;
	}
}

function backWardPolicy() {
	//测试中没有发现用到这个方法
	var SHOWTYPE = "SHOW";
	var BizNo = fm.prpLclaimPolicyNo.value;
	var RiskCode = fm.prpLclaimRiskCode.value;
	var damageDate = fm.damageDate.value;
	var coreURL = fm.coreURL.value;
	var vURL = '/claim/pages/common/pub/PolicyShowCenter.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate + '&coreURL=' + coreURL;
	window.open(vURL, '詳細信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function changeSumClaim() {
	var sumClaimNum = fm.all("prpLclaimLossKindCode").length;

	if (sumClaimNum != 'undefined' && sumClaimNum > 1) {
		for (var i = 1; i < sumClaimNum; i++) {
			calSumClaim(fm.all("prpLclaimLossKindCode")[i]);
		}
	}
}


function buttonOnClick3(fieldObject) {
	var intIndex = parseInt(getElementOrder(fieldObject) - 1);
	var spanId = 'span_Engage_Context00';
	if (isNaN(fm.button_Engage_Open_Context00.length)) {} else { //多行
		spanId = 'span_Engage_Context00' + "[" + intIndex + "]";
	}
	showSubPage3(spanId);
}

/**
 * 显示输入框
 * leftMove 默认值0，坐标左移leftMove
 */

function showSubPage3(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;

	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}

function judgeKindCode(Field) {
	var findFlag = 0;
	var fieldname = Field.name;
	var i = 0;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	var strValue = fm.prpLclaimLossKindCode[findex].value;
	//判断选择的险别是否为出险日期当时生效的险别
	for (var j = 0; j < damageKind.length; j++) {
		if (damageKind[j] == strValue) {
			findFlag = 1;
			break;
		}
	}
	if (findFlag == 0) {
		alert(i18n.claim.selecNotDateDangerAgain); //您选择的险别不是出险日期时的险别,请重新进行选择
		fm.prpLclaimLossKindCode[findex].value = "";
		return false;
	}

}

function afterDeleteClaimLoss(field) {
	collectClaimLoss(field);
	calculateDutySum(field);

}

function dutySum() {

	var compAmout = 0;
	var nowAmout = 0;
	//循环计算 估金额
	for (var n = 1; n < fm.prpLclaimLossSumClaim.length; n++) {
		nowAmout = parseFloat(fm.prpLclaimLossSumClaim[n].value)
		compAmout = compAmout + nowAmout;
	}
	fm.prpLclaimDutySumClaim.value = pointTwo(round(compAmout, 0), 0);
	fm.prpLclaimSumClaim.value = pointTwo(round(compAmout, 0), 0);
}

/** 刷新总免赔额 */

function flashExceptDeductibleRateAll() {
	var sumAccount = 0;
	for (var i = 1; i < fm.exceptDeductibleKindCode.length; i++) {
		sumAccount = sumAccount + parseFloat(fm.exceptDeductiblePay[i].value);
	}
	fm.exceptDeductibleRateAll.value = pointTwo(sumAccount);
}

/** 刷新不计免赔额拦数据 */

function flashExceptDeductible() {
	var sumClaimNum = fm.all("prpLclaimLossKindCode").length;
	if (sumClaimNum != 'undefined' && sumClaimNum > 1) {
		for (var i = 1; i < sumClaimNum; i++) {
			insertRow2(fm.all("prpLclaimLossKindCode")[i]);
		}
	}
}

/** 用於在不计免赔栏中添加数据*/

function insertRow2(field) {
	var flag = 0;
	var kind;
	var fieldname = field.name;
	var findex = 0;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	kind = fm.prpLclaimLossKindCode[findex].value;
	if (isEmptyField(field)) { //如果还未确定险别，放弃添加
		flag = 2;
	} else {
		if (fm.exceptDeductibleKindCode) { //判断不计免赔栏中是否已经有了这个险别
			for (var index = 0; index < fm.exceptDeductibleKindCode.length; index++) {
				if (fm.exceptDeductibleKindCode[index].value != "") {
					if (fm.exceptDeductibleKindCode[index].value == kind) {
						flag = 2;
					}
				}
			}
		}
	}

	if (flag == 0) {
		checkExceptDeductible(kind, field);
	}
}

/**用於在不计免赔栏中删除数据*/

function deleteRow2(field) {
	var fieldname = field.name;
	var fieldValue = "";
	var lossKind = "";
	var flag = 0;
	var flag1 = 0;
	var countLoss = 0;
	var findexExcept = 0;
	var findexLoss = 0;
	var exceptDeductibleRateAll = 0;
	var order = getElementOrder(field);
	if (fm.exceptDeductibleKindCode) {
		fieldValue = fm.prpLclaimLossKindCode[order - 1].value;
		lossKind = "fm.prpLclaimLossAcciDeductiblePay";
	}
	//判断不计免赔栏中是否有该险别
	for (var index = 0; index < fm.exceptDeductibleKindCode.length; index++) {
		if (fm.exceptDeductibleKindCode[index].value != "") {
			if (fm.exceptDeductibleKindCode[index].value == fieldValue) {
				flag = 2;
				findexExcept = index;
			}
		}
	}

	if (flag == 2) { //如果没有该险种的不计免赔就放弃以下操作
		if (fm.prpLclaimLossKindCode) { //判断该险别是否还有其它损失
			for (var index1 = 0; index1 < fm.prpLclaimLossKindCode.length; index1++) {
				flag1 = 0;
				if (fm.prpLclaimLossKindCode[index1].value == fieldValue) {
					if (fm.all(fieldname)[index1] == field) {
						findexLoss = index1;
						flag1 = 1;
					}
					if ("P" == fm.prpLclaimLossLossFeeType[index1].value && flag1 == 0) {
						countLoss++;
					}
				}
			}
		}

		if (countLoss > 0) {
			if (eval(lossKind + "[" + findexLoss + "]")) {
				if (fm.exceptDeductiblePay[findexExcept].value != "" && eval(lossKind + "[" + findexLoss + "].value") != "") {
					fm.exceptDeductiblePay[findexExcept].value = pointTwo(parseFloat(fm.exceptDeductiblePay[findexExcept].value) - parseFloat(eval(lossKind + "[" + findexLoss + "].value")));
				}
			}
		} else {
			deleteRow(fm.exceptDeductibleKindCode[findexExcept], 'exceptLoss1');
		}

		for (var j = 1; j < fm.exceptDeductibleKindCode.length; j++) {
			exceptDeductibleRateAll = exceptDeductibleRateAll + parseFloat(fm.exceptDeductiblePay[j].value);
		}

		fm.exceptDeductibleRateAll.value = pointTwo(exceptDeductibleRateAll);
	}
}

/**用於检测不计免赔栏中的险种是否和受损险种一致*/

function checkExcept(field) {
	var order = getElementOrder(field);
	var flag = 0;
	if (fm.exceptDeductibleKindCode) {
		for (var index = 1; index < fm.exceptDeductibleKindCode.length; index++) {
			if (fm.exceptDeductibleKindCode[index].value != "") {
				if (fm.prpLclaimLossKindCode) {
					for (var index1 = 1; index1 < fm.prpLclaimLossKindCode.length; index1++) {
						if (fm.prpLclaimLossKindCode[index1].value != "" && "P" == fm.prpLclaimLossLossFeeType[index1].value) {
							if (fm.prpLclaimLossKindCode[index1].value == fm.exceptDeductibleKindCode[index].value) {
								flag = 1;
							}
						}
					}
				}
			}

			if (flag == 0 && fm.exceptDeductibleKindCode[index].value != "") {
				if (parseFloat(fm.exceptDeductibleRateAll.value) != 0) {
					fm.exceptDeductibleRateAll.value = pointTwo(parseFloat(fm.exceptDeductibleRateAll.value) - parseFloat(fm.exceptDeductiblePay[index].value));
				}
				deleteRow(fm.exceptDeductibleKindCode[index], 'exceptLoss1');
			}

			if (flag == 1 && fm.prpLclaimLossKindCode1[order - 1].value == fm.exceptDeductibleKindCode[index].value && fm.prpLclaimLossKindCode[order - 1].value == "") {
				fm.exceptDeductiblePay[index].value = pointTwo(parseFloat(fm.exceptDeductiblePay[index].value) - parseFloat(fm.prpLclaimLossAcciDeductiblePay[order - 1].value));
				fm.exceptDeductibleRateAll.value = pointTwo(parseFloat(fm.exceptDeductibleRateAll.value) - parseFloat(fm.prpLclaimLossAcciDeductiblePay[order - 1].value));
				fm.prpLclaimLossKindCode1[order - 1].value = "";
				if ("P" == fm.prpLclaimLossLossFeeType[order - 1].value) {
					fm.prpLclaimLossAcciDeductiblePay[order - 1].value = 0;
					fm.prpLclaimLossAcciDeductibleRate[order - 1].value = 0;
				}
			}
			flag = 0;
		}
	}
}
// mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能Start
function initenableEditTableField(){
	if(undefined!=fm.buttonReturn){
		fm.buttonReturn.disabled = false; //返回按鈕可使用
	}
	fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
	fm.prpLclaimDamageStartDate_show_format_rcDate.readOnly = false; //出險時間
	fm.prpLclaimDamageStartHour.readOnly = false; //出險小時
	fm.prpLclaimDamageStartMinute.readOnly = false; //出險分鐘
	fm.prpLclaimDamageAreaCode.readOnly = false; //出險地區代碼
	fm.prpLclaimDamageAreaName.readOnly = false; //出險地區名稱
	$("input[name='prpLclaimDamageStartDate_show_format_rcDate']").attr('style','width:100px');
	$("input[name='prpLclaimDamageStartHour']").attr('style','width: 25px');
	$("input[name='prpLclaimDamageStartMinute']").attr('style','width: 25px');
	$("input[name='prpLclaimDamageAreaCode']").attr('style','width: 27%');
	$("input[name='prpLclaimDamageAreaName']").attr('style','width: 48%');
}

function updateClaimEditForm(field,saveType){
	if (enableFeature(field,saveType)){
		return true;
	}
	inputProcessing(true);
	fm.buttonReturn.disabled = false; //返回按鈕可使用
	fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
	return false;
}

function enableFeature(field,saveType){
	// 檢查出險日期
	if(fm.prpLclaimDamageStartDate.value.length == 0){
		alert("請輸入出險日期！");
		return false;
	}
	if (fm.prpLclaimDamageStartHour.value.length == 0){
		alert("請輸入出險小時！");
		return false;
	}
	var strDamageStartMinute = ""!=fm.prpLclaimDamageStartMinute.value?fm.prpLclaimDamageStartMinute.value:"01";
	fm.prpLclaimDamageStartMinute.value = strDamageStartMinute;
	var fullDamageStartDate = fm.prpLclaimDamageStartDate.value +" "+fm.prpLclaimDamageStartHour.value +":"+fm.prpLclaimDamageStartMinute.value;
	var prpLclaimReceiptDate = fm.prpLclaimReceiptDate.value;
	if(!(new Date(prpLclaimReceiptDate.replace(/-/g,'/')) > new Date(fullDamageStartDate.replace(/-/g,'/')))){
		alert("出險時間不可大於收件時間！");
		return false;
	}
	if(fm.prpLclaimDamageAreaCode.value.length == 0 ){
		alert("出險地區代號不得為空！");
		return false;
	}
	if (fm.prpLclaimDamageAreaName.value.length == 0){
		alert("出險地區名稱不得為空！");
		return false;
	}
	if (!checkDamageDate()){ //確認出險日期是否在保期內
		return false;
	}
	
	inputProcessing(false);
	field.disabled = true;
	fm.submit();

	return true;
}

function inputProcessing(field){
	var len = document.all.length;

	for(var i=0; i<len; i++){
	  var tempElements = document.all(i);
	  if(tempElements.tagName=="INPUT"){
	      if(tempElements.type=="radio"){
	          //將選擇域變為可讀
	       tempElements.disabled=field;
	      }else if(tempElements.type=="button"){
		      //將選擇域變為可讀
	    	  tempElements.disabled = field;
	      }else if(tempElements.type=="checkbox"){
	    	  tempElements.disabled = field;
	      }
	  } else if(tempElements.tagName=="SELECT" || tempElements.tagName=="select"){
	      //將選擇域變為可讀
		  tempElements.disabled = field;
	  } else if(tempElements.tagName=="IMG"){
		  //將選擇域變為可讀
		  tempElements.disabled = field;
	  }
	}
}

function checkDamageDate(){
	var prpLregistStartDate = fm.prpLregistStartDate.value; //起保日
	var prpLregistEndDate = fm.prpLregistEndDate.value; //終保日
	
	var startDate = new Date(prpLregistStartDate.replace(/-/g,"/"));//起保日期
	startDate.setHours(parseInt(fm.prpLregistStartHour.value , 10),0,0);
	var endDate = new Date(prpLregistEndDate.replace(/-/g,"/"));//終保日期
	endDate.setHours(parseInt(fm.prpLregistEndHour.value , 10),0,0);
	var damageStartDate = new Date(fm.prpLclaimDamageStartDate.value.replace(/-/g,"/"));//出險日期
	var damageStartHour  = fm.prpLclaimDamageStartHour.value;
	var damageStartMinute  = fm.prpLclaimDamageStartMinute.value;
	damageStartDate.setHours(parseInt(damageStartHour , 10),parseInt(damageStartMinute , 10),0);
	if ((damageStartDate < startDate) || (damageStartDate > endDate)) {
//		alert(i18n.claim.tipDangerTimeDuringReport); // 提示：出險時間在保險期間以外，不允許修改!
		alert("提示：出險時間在保險期間以外，不允許修改!"); // 提示：出險時間在保險期間以外，不允許修改!
		return false;
	}
	return true;
}
//mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能End
