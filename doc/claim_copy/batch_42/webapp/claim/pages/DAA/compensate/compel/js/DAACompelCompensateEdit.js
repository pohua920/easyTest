/*****************************************************************************
 * DESC       ：实赔的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-05-19
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 正在处理理算任务选择免赔率
 *@param       无
 *@return      打开选择免赔率页面
 */

function choseDeductibleRate() {
	pageUrl = "/claim/compensateBeforeEditList.do?" + "ClaimNo=" + fm.prpLcompensateClaimNo.value + "&swfLogFlowID=" + fm.swfLogFlowID.value + "&swfLogLogNo=" + fm.swfLogLogNo.value + "&riskCode=" + fm.prpLcompensateRiskCode.value + "&editType=RECHOSE&nodeType=compe&status=0" + "&businessNo=" + fm.prpLcompensateClaimNo.value + "&policyNo=" + fm.prpLcompensatePolicyNo.value + "&CompensateNo=" + fm.prpLcompensateCompensateNo.value + "&modelNo=1&rechoseFlag=1&nodeNo=12&compeCount=0";

	window.open(pageUrl, "", "resizable=0,scrollbars,width=550,height=320");
}
/**
 *@description 检查报案登记
 *@param       无
 *@return      通过返回true,否则返回false
 */

function checkForm() {

	return true;
}

function changeCompensateFlag(flag) {}

function GenerateCtextFlag(flag) {
	fm.GenerateCompensateFlag.value = flag;
}
/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {

	//显示强制险赔付汇总信息
	initRealPay();
	initCommerceCasualties(); //初始化伤亡情形CheckBox
	countPersonLoss(); //初始化统计受害人 一定要在initCommerceCasualties 之后
	calFundCommerce(); //初始化统计强制险赔款汇总
	var payFee = parseInt(fm.prpLcompensatePayFee.value);
	var errorMessage = "";
	if (payFee == -1) {
		errorMessage = errorMessage + "此保單保費未繳,請慎重處理！！！ \n";
	} else if (payFee == 0) {
		errorMessage = errorMessage + "此保單已繳未繳全,請慎重處理！！！ \n";
	}

	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	return true;
}
/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */

function submitDelete() {
	fm.buttonDelete.disabled = true;
	fm.submit();
	return true;
}
/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */

function submitForm() {
	fm.buttonApprove.disabled = true;
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
	var D3Count = 0;
	var D4Count = 0;
	var seatCount = parseInt(fm.prpLcompensateSeatCount.value);
	var D3D4Count = 0;

	var errorMessage = "";
	var prpLchargeKindCodeList = document.getElementsByName("prpLchargeKindCode"); //险别代码
	var prpLchargeKindNameList = document.getElementsByName("prpLchargeKindName"); //险别名称
	var prpLchargeChargeCodeList = document.getElementsByName("prpLchargeChargeCode"); //费用名称
	var prpLchargeOwnerNameList = document.getElementsByName("prpLchargeOwnerName"); //賠付對象
	var prpLchargeOwnerShipList = document.getElementsByName("prpLchargeOwnerShip"); //费用支付方式
	var prpLchargeUniformNoList = document.getElementsByName("prpLchargeUniformNo"); //ID/統一編號
	var prpLchargeBankCodeList = document.getElementsByName("prpLchargeBankCode"); //總行代號
	var prpLchargeBankNameList = document.getElementsByName("prpLchargeBankName"); //總行名稱
	var prpLchargeAccountCodeList = document.getElementsByName("prpLchargeAccountCode"); //匯款帳號
	var prpLchargeCustomBankCodeList = document.getElementsByName("prpLchargeCustomBankCode"); //分行代號
	var prpLchargeCustomBankNameList = document.getElementsByName("prpLchargeCustomBankName"); //分行名稱
	var prpLchargeAreaCodeList = document.getElementsByName("prpLchargeAreaCode"); //郵遞區號
	var prpLchargeCourierAddressList = document.getElementsByName("prpLchargeCourierAddress"); //郵遞地址
	var prpLchargeCertificateCodeList = document.getElementsByName("prpLchargeCertificateCode"); //證件類型
	for (var i = 1; i < prpLchargeOwnerNameList.length; i++) {
		var tempOwnerShip = prpLchargeOwnerShipList[i].value;
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
		 if(undefined!=prpLchargeAreaCodeList[i] && null!=prpLchargeAreaCodeList[i]){
			var oAreaCode = trim(prpLchargeAreaCodeList[i].value);
			var areaCode = trim(prpLchargeAreaCodeList[i].value).replace(/[^\d]/g,'');
			if(oAreaCode.length > 3){
				alert("第 " + (i) + " 条費用資訊‘郵遞區號’ 長度超過3位數!");
				return false; //
			} else 
			if(oAreaCode != areaCode){
				alert("第 " + (i) + " 条費用資訊‘郵遞區號’ 只能輸入數值!");
				return false; //
			}  
		 }
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
		if (prpLchargeKindCodeList[i].value == '' || prpLchargeKindNameList[i].value == '') {
			alert("第 " + (i) + " 条費用資訊‘險別代碼’、‘險別名稱’必須輸入!");
			return false; //
		} else if (prpLchargeChargeCodeList[i].value == '') {
			alert("第 " + (i) + " 条費用資訊‘費用名稱’必須輸入!");
			return false; //
		} else if (trim(prpLchargeOwnerNameList[i].value) == '') {
			alert("第 " + (i) + " 条費用資訊‘賠付對象’必須輸入!");
			return false; //
		} else if (trim(prpLchargeUniformNoList[i].value) == '') {
			alert("第 " + (i) + " 条費用資訊‘統一編號/身份證號’必須輸入!");
			return false; //
		} else if (tempOwnerShip == "B" && (trim(prpLchargeAccountCodeList[i].value) == '' || trim(prpLchargeBankCodeList[i].value) == '' || trim(prpLchargeBankNameList[i].value) == '' || trim(prpLchargeCustomBankCodeList[i].value) == '' || trim(prpLchargeCustomBankNameList[i].value) == '')) {
			alert("第 " + (i) + " 条費用支付方式爲匯款，必須輸入費用支付帳戶資訊!");
			return false;
		} else if (trim(prpLchargeAreaCodeList[i].value) == '' || trim(prpLchargeCourierAddressList[i].value) == '') {
			alert("第 " + (i) + " 条費用資訊‘郵遞區號’、‘郵遞地址’必須輸入!");
			return false; //
		} else if (prpLchargeCertificateCodeList[i].value == "01" && !checkIdentifyNumber(prpLchargeUniformNoList[i].value, "9")) {
			alert("請爲費用資訊中第  " + i + " 條錄入正確的身份證號");
			return false;
		} else if (prpLchargeCertificateCodeList[i].value == "02" && !checkUniformNo(prpLchargeUniformNoList[i].value)) {
			alert("請爲費用資訊中第  " + i + " 條錄入正確的統一編號");
			return false;
		}
	}
	var prpLcompensateSumClaim = document.getElementsByName("prpLcompensateSumClaim"); //预估金额
	for (var i = 0; i < prpLcompensateSumClaim.length; i++) {
		if (prpLcompensateSumClaim[i].value == "") {
			errorMessage = errorMessage + "預估金額不允許爲空！\n";
		}
	}
	var prpLpersonCommerceBirthday = document.getElementsByName("prpLpersonCommerceBirthday"); //预估金额
	for (var i = 1; i < prpLpersonCommerceBirthday.length; i++) {
		if (prpLpersonCommerceBirthday[i].value == "") {
			errorMessage = errorMessage + "出生年份不允許爲空！\n";
		}
	}
	var prpLpersonCommerceIdentifyNumber = document.getElementsByName("prpLpersonCommerceIdentifyNumber"); //预估金额
	for (var i = 1; i < prpLpersonCommerceIdentifyNumber.length; i++) {
		if (prpLpersonCommerceIdentifyNumber[i].value == "") {
			errorMessage = errorMessage + "身份證號不允許爲空！\n";
		}
	}
	var prpLpayObjectInfoOwnerShipList = document.getElementsByName("prpLpayObjectInfoOwnerShip"); //标的损失赔款支付方式
	if (prpLpayObjectInfoOwnerShipList.length <= 1) {
		alert("必須輸入至少一個賠付對象的訊息!");
		return false;
	}
	var prpLpayObjectInfoOwnerNameList = document.getElementsByName("prpLpayObjectInfoOwnerName"); //賠付對象
	var prpLpayObjectInfoPaymentKindList = document.getElementsByName("prpLpayObjectInfoPaymentKind"); //費用類型
	var prpLpayObjectInfoUniformNoList = document.getElementsByName("prpLpayObjectInfoUniformNo"); //ID/統一編號
	var prpLpayObjectInfoPayAmountList = document.getElementsByName("prpLpayObjectInfoPayAmount"); //理赔金

	var prpLpayObjectInfoBeneficiaryPhoneList = document.getElementsByName("prpLpayObjectInfoBeneficiaryPhone"); //受款人電話
	var prpLpayObjectInfoBankCodeList = document.getElementsByName("prpLpayObjectInfoBankCode"); //總行代號
	var prpLpayObjectInfoBankNameList = document.getElementsByName("prpLpayObjectInfoBankName"); //總行名稱
	var prpLpayObjectInfoAccountCodeList = document.getElementsByName("prpLpayObjectInfoAccountCode"); //匯款帳號
	var prpLpayObjectInfoCustomBankCodeList = document.getElementsByName("prpLpayObjectInfoCustomBankCode"); //分行代號
	var prpLpayObjectInfoCustomBankNameList = document.getElementsByName("prpLpayObjectInfoCustomBankName"); //分行名稱
	var prpLpayObjectInfoAreaCodeList = document.getElementsByName("prpLpayObjectInfoAreaCode"); //郵遞區號
	var prpLpayObjectInfoCourierAddressList = document.getElementsByName("prpLpayObjectInfoCourierAddress"); //郵遞地址
	var prpLpayObjectInfoMobilePhoneNoList = document.getElementsByName("prpLpayObjectInfoMobilePhoneNo"); //行動電話
	var prpLpayObjectInfoCertificateCodeList = document.getElementsByName("prpLpayObjectInfoCertificateCode") //證件類型

	for (var i = 1; i < prpLpayObjectInfoOwnerShipList.length; i++) {
		var tempShip = prpLpayObjectInfoOwnerShipList[i].value;
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
		if(undefined!=prpLpayObjectInfoAreaCodeList[i] && null!=prpLpayObjectInfoAreaCodeList[i]){
			var oAreaCode = trim(prpLpayObjectInfoAreaCodeList[i].value);
			var areaCode = trim(prpLpayObjectInfoAreaCodeList[i].value).replace(/[^\d]/g,'');
			if(oAreaCode.length > 3){
				alert("賠付對象 " + i + " ‘郵遞區號’ 長度超過3位數!");
				return false; //
			} else 
			if(oAreaCode != areaCode){
				alert("賠付對象 " + i + " ‘郵遞區號’ 只能輸入數值!");
				return false; //
			} 
		}
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
		if (trim(prpLpayObjectInfoOwnerNameList[i].value) == '') {
			alert("賠付對象 " + i + " ‘賠付對象’必須輸入!");
			return false; //
		} else if (trim(prpLpayObjectInfoPaymentKindList[i].value) == '') {
			alert("賠付對象 " + i + " ‘費用類型’必須輸入!");
			return false; //
		} else if (trim(prpLpayObjectInfoUniformNoList[i].value) == '') {
			var tempStr = (tempShip == 'C' ? "個人身份證號" : "證件代碼");
			alert("賠付對象 " + i + " ‘" + tempStr + "’必須輸入!");
			return false; //
		} else if (trim(prpLpayObjectInfoBeneficiaryPhoneList[i].value) == '' && tempShip != 'C') {
			alert("賠付對象 " + i + " ‘受款人電話’必須輸入!");
			return false; //
		} else if (tempShip == 'C' && (trim(prpLpayObjectInfoBeneficiaryPhoneList[i].value) == '' && trim(prpLpayObjectInfoMobilePhoneNoList[i].value) == '')) {
			alert("賠付對象 " + i + " 爲现金支付，‘市內電話’和'行動電話'必須輸入一项!");
			return false; //
		} else if (tempShip == 'B' && (trim(prpLpayObjectInfoBankCodeList[i].value) == '' || trim(prpLpayObjectInfoBankNameList[i].value) == '' || trim(prpLpayObjectInfoAccountCodeList[i].value) == '' || trim(prpLpayObjectInfoCustomBankCodeList[i].value) == '' || trim(prpLpayObjectInfoCustomBankNameList[i].value) == '')) {
			alert("賠付對象 " + i + " 費用支付方式爲匯款，必須輸入支付帳戶資訊!");
			return false; //
		} else if ((tempShip == 'B' || tempShip == 'Q') && (trim(prpLpayObjectInfoAreaCodeList[i].value) == '' || trim(prpLpayObjectInfoCourierAddressList[i].value) == '')) {
			alert("賠付對象 " + i + " ‘郵遞區號’、‘郵遞地址’必須輸入!");
			return false; //
		} else if (tempShip == 'C' && parseFloat(prpLpayObjectInfoPayAmountList[i].value) > 5000) {
			alert("賠付對象 " + i + " 費用支付方式爲現金，現金支付理賠金不得超過5000NTD!");
			return false; //
		} else if (tempShip == "C" && prpLpayObjectInfoPaymentKindList[i].value == "4") {
			if (prpLpayObjectInfoCertificateCodeList[i].value != "01") {
				alert("請爲 賠款給付對象訊息   賠付對象 " + i + " 錄入正確的證件類型");
				return false;
			}
			if (!checkIdentifyNumber(prpLpayObjectInfoUniformNoList[i].value, "9")) {
				alert("請爲 賠款給付對象訊息   賠付對象 " + i + " 錄入正確的身份證號");
				return false;
			}
		} else if (tempShip != "C" || prpLpayObjectInfoPaymentKindList[i].value != "4") {
			if (prpLpayObjectInfoCertificateCodeList[i].value == "02" && !checkUniformNo(prpLpayObjectInfoUniformNoList[i].value)) {
				alert("請爲賠款給付對象訊息中  賠付對象 " + i + " 錄入正確的統一編號");
				return false;
			} else if (prpLpayObjectInfoCertificateCodeList[i].value == "01" && !checkIdentifyNumber(prpLpayObjectInfoUniformNoList[i].value, "9")) {
				alert("請爲賠款給付對象訊息中  賠付對象 " + i + " 錄入正確的身份證號");
				return false;
			}
		}
	}
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}

	var propCount = document.getElementsByName('propSerialNo').length - 1;
	var personCount = document.getElementsByName('prpLpersonCommerceSerialNo').length - 1;
	var chargeCount = document.getElementsByName('prpLchargeSerialNo').length - 1;
	if (propCount < 1 && personCount < 1 && chargeCount < 1) {
		alert("必須輸入一條損失訊息,金額可以爲0");
		return false;
	}
	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	var context = fm.prpLctextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + "理算报告不允许为空\n";
	}
	for (var i = 1; i < fm.prpLpersonCommerceIdentifyNumber.length; i++) {
		var prpLpersonCommerceIdentifyNumber = fm.prpLpersonCommerceIdentifyNumber[i].value; //受害人身份证号
		var prpLpersonCommercePersonName = fm.prpLpersonCommercePersonName[i].value; //受害人名字
		var prpLpersonCommerceSex = fm.prpLpersonCommerceSex[i].value; //受害人性别
		if (!checkIdentifyNumber(prpLpersonCommerceIdentifyNumber, prpLpersonCommerceSex)) {
			errorMessage = "請爲受害人 " + prpLpersonCommercePersonName + " 錄入正確的身份證字號";
		}
	}
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}

	fm.target = "";
	var underWriteFlag = fm.prpLcompensateUnderWriteFlag.value;
	if (saveType == 4) {
		if (confirm("案件最終 賠款金額為：" + fm.prpLcompensateSumDutyPaid.value + " ,費用金額為：" + fm.prpLcompensateSumNoDutyFee.value + " ,請確認！")) {} else {
			undisablebutton();
			return false;
		}
	}
	//增加赔付额的验证
	if (!sumPrpLpayObjectInfopayAmount()) {
		return false;
	}
	var message = pettyCashAppropriation(); //1.	零用金撥補
	if (message.length > 0) {
		alert(message);
		return false;
	}
	field.disabled = true;
	fm.submit();
	return true;
}


/**
 @author 中科软
 @description 增加一条赔付人员费用信息方法
 @param       无
 @return      无
 @see         UIMulLine#insertRow
 @see         UIMulLine#setRowRecordState
*/

function insertRowKind() {

	insertRow('Kind', 'Kind_Data')
}
/**
 *@description 实赔任务复核
 *@param       无
 *@return      通过返回true,否则返回false
 */

function approveSubmit() {
	if (checkForm() == false) {
		return false;
	}

	fm.buttonApprove.disabled = true;
	fm.submit();
	return true;
}

/**
 @author 中科软
 @description 生成理算报告
 @param       无
 @return      无
 */

function generateCtext() {

	if (checkForm() == false) {
		return false;
	}
	var oldAction = fm.action;
	var oldTarget = fm.target;

	fm.action = "/claim/compensate/compensateGenerate.do";
	fm.target = "fraCalculate";

	fm.submit();

	fm.action = oldAction;
	fm.target = oldTarget;

	return true;
}


/**
 @author 中科软
 @description 计算赔偿比例（改变实际价值时触发）
              若赔偿比例为空，则赔偿比例=保额/新车购置价 也就是  (保额/限额)/实际价值
 @param       field:触发域
 @return      boolean值，合法返回true,不合法返回false
 @see         UICommon.js#point、round
*/

function calLossClaimRate(field) {

	var fieldname = field.name;
	var findex = 0;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	var prpLlossDtoAmount = parseFloat(fm.all("prpLlossDtoAmount")[findex].value);

	var ClaimRate = 0;

	if (isNaN(prpLlossDtoAmount))
		prpLlossDtoAmount = 0;

	if (isEmptyField(field)) {
		field.value = prpLlossDtoAmount;
	}

	ClaimRate = point(round(prpLlossDtoAmount / parseFloat(field.value) * 100, 2), 2);
	if (ClaimRate > 100)
		ClaimRate = 100;
	fm.all("prpLlossDtoClaimRate")[findex].value = ClaimRate;

	calRealpay(field);

	return true;
}


/**
 @author 中科软
 @description 计算赔付标的中的赔偿金额（改变实际价值、核定损失、残值、责任比例时触发）
              计算赔款金额
              如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
              # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpay(field) {
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DeductibleRate; //免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔偿金额
	var temp;

	//取得当前的数据
	var fieldname = field.name;
	var findex = 0;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	SumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[findex].value);
	SumRest = parseFloat(fm.all("prpLlossDtoSumRest")[findex].value);
	ClaimRate = parseFloat(fm.all("prpLlossDtoClaimRate")[findex].value);
	DutyRate = parseFloat(fm.all("prpLlossDtoIndemnityDutyRate")[findex].value);
	DeductibleRate = parseFloat(fm.all("prpLlossDtoDeductibleRate")[findex].value);
	/* add by liuning begin 20040220 */
	if (fm.prpLcompensateRiskCode.value == "DAS" && fm.all("prpLlossDtoKindCode")[findex].value == "F") {
		DeductibleRate = 10.0;
		fm.LossDeductibleRate.value = point(round(DeductibleRate, 2), 1)
	}
	/* add by liuning end 20040220 */
	Deductible = parseFloat(fm.all("prpLlossDtoDeductible")[findex].value);
	//modify begin zhanqiang 20031126
	Amount = parseFloat(fm.all("prpLlossDtoAmount")[findex].value);
	if (isNaN(Amount))
		Amount = 0;
	//modify end zhanqiang 20031126
	if (isNaN(SumLoss))
		SumLoss = 0;
	if (isNaN(SumRest))
		SumRest = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;
	if (isNaN(Deductible))
		Deductible = 0;

	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
	 */
	temp = (SumLoss - SumRest) * ClaimRate * DutyRate;
	Deductibletemp = temp * DeductibleRate;

	if (fm.prpLcompensateRiskCode.value == "DAS" && fm.all("prpLlossDtoKindCode")[findex].value == "F" && Deductibletemp < 100.0 && temp > 0.0) {
		Deductibletemp = 100.0;

		Realpay = temp - Deductibletemp;
		if (temp > 0) {
			DeductibleRate = (Deductibletemp * 100.0) / temp;
			fm.all("prpLlossDtoDeductibleRate")[findex].value = point(round(DeductibleRate, 2), 2)
		}
	} else {
		Realpay = temp * (1 - DeductibleRate);
	}
	if ((fm.all("prpLlossDtoKindCode")[findex].value == "B" || fm.all("prpLlossDtoKindCode")[findex].value == "D2" || fm.all("prpLlossDtoKindCode")[findex].value == "W") && temp > Amount) {
		Realpay = Amount * (1 - DeductibleRate);
	}
	if (Realpay > Amount && Amount > 0) {
		Realpay = Amount;
	}
	//reason:险别是车损或三者，並且损失明细为27扣免赔，则赔偿金额=-残值
	if ((fm.all("prpLlossDtoKindCode")[findex].value == "A" || fm.all("prpLlossDtoKindCode")[findex].value == "B") && fm.all("prpLlossDtoFeeTypeCode")[findex].value == "27") {
		Realpay = SumRest * (-1);
	}
	fm.all("prpLlossDtoSumRealPay")[findex].value = point(round(Realpay, 2), 2);
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}


/**
 @author 中科软
 @description 分险别校验是否超过保单中的限额
 @param       KindCode:险别
 @param       PersonNo:人员序号
 @return      无
 */

function CheckLimitAmountByKindCode(KindCode, PersonNo) {
	if (checkCodeInQuery()) {
		return;
	}

	if (KindCode == "")
		return;

	//解除锁定,否则无法传入下一页
	fm.Sex.disabled = false;
	fm.PersonLossLicenseNo.disabled = false;

	//保存数据
	saveRecord("Loss");
	saveRowRecordToSingleTable("PersonLoss", "Kind");

	var oldAction = fm.action;
	var oldTarget = fm.target;

	fm.target = "fraSubmit"
	fm.action = "/ddccallweb/DAA/lp/compensate/UILDAACheckLimitAmountSubmit.jsp?KindCode=" + KindCode + "&PersonNo=" + PersonNo;

	fm.submit();

	fm.action = oldAction;
	fm.target = oldTarget;

	//清除数据
	clearRecord("Loss");
	clearRecord("PersonLoss");
	clearRowRecord("Kind");

	//load data
	loadRowRecord("PersonLoss", "Kind", "Kind_Data");

}

/**
 @author 中科软
 @description 计算责任赔款合计、赔款合计、其它费用、实赔金额
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calFund() {
	//定义变量
	var dblSumDutyPaid = 0; //责任赔款合计（=（赔款费用附加信息中）计入赔款金额+（赔付标的附加信息中）赔偿金额+（赔付人员附加信息中）赔付合计）
	var dblSumPaid = 0; //赔款合计（=责任赔款合计+其它费用）
	var dblSumPrePaid = 0; //预赔金额
	var dblSumNoDutyFee = 0; //其它费用（（赔款费用附加信息中）费用金额 - 计入赔款金额）
	var dblSumThisPaid = 0; //实赔金额（=责任赔款合计－已预付赔款）

	var chargeRealPay = 0;
	var chargeAmount = 0;
	var lossRealPay = 0;
	var personLossRealPay = 0;

	//计算责任赔款合计
	var i = 0;

	//1.赔款费用的计入赔款金额，以及其它费用
	var elements = getTableElements("Charge");
	for (i = 1; i < fm.all("prpLchargeSumRealPay").length; i++) {
		chargeRealPay = parseFloat(fm.all("prpLchargeSumRealPay")[i].value); //记入赔款
		chargeAmount = parseFloat(fm.all("prpLchargeChargeAmount")[i].value); //费用

		if (isNaN(chargeRealPay))
			chargeRealPay = 0;
		if (isNaN(chargeAmount))
			chargeAmount = 0;

		dblSumDutyPaid = dblSumDutyPaid + chargeRealPay;
		dblSumNoDutyFee = dblSumNoDutyFee + (chargeAmount - chargeRealPay);
	}

	var intLossCount = 0;
	var dblRealPayAB = 0; //modify by dengxh update at 20040914
	var dblRealPayA = 0; //车损险（A）总赔款金额
	var dblRealPayATotal = 0; //车损险（A）最终赔款金额，可能为空
	try {
		for (i = 1; i < fm.all("prpLlossDtoSumRealPay").length; i++) {
			lossRealPay = parseFloat(fm.all("prpLlossDtoSumRealPay")[i].value);

			if (isNaN(lossRealPay))
				lossRealPay = 0;

			dblSumDutyPaid = dblSumDutyPaid + lossRealPay;
			if ((fm.all("prpLlossDtoKindCode")[i].value == "A" || fm.all("prpLlossDtoKindCode")[i].value == "B") && fm.all("prpLlossDtoFeeTypeCode")[i].value == '27') {
				dblRealPayAB = dblRealPayAB + lossRealPay;
			}
			if (fm.all("prpLlossDtoKindCode")[i].value == "A") {
				dblRealPayA = dblRealPayA + lossRealPay;
			}

		}
	} catch (E) {}
	var deductibleAall = 0;
	if (isNaN(deductibleAall))
		deductibleAall = 0;
	//用车损险赔款减去免赔额
	dblRealPayATotal = dblRealPayA - deductibleAall;
	//加入域显示车损险最终赔款
	if (dblRealPayATotal > 0) {
		dblSumDutyPaid = dblSumDutyPaid - deductibleAall; //如果减去免赔後的赔款大於0，则在总赔款中减去免赔额；
	} else {
		dblSumDutyPaid = dblSumDutyPaid - dblRealPayA; //如果减去免赔後的赔款小於0,则在总赔款中减去赔款，即赔款为0；
	}
	//得到预赔金额
	dblSumPrePaid = parseFloat(fm.prpLcompensateSumPrePaid.value);
	if (isNaN(dblSumPrePaid))
		dblSumPrePaid = 0;

	//计算赔款合计
	dblSumPaid = dblSumDutyPaid + dblSumNoDutyFee;

	//计算实赔金额
	dblSumThisPaid = dblSumPaid - dblSumPrePaid;

	fm.prpLcompensateSumDutyPaid.value = point(round(dblSumDutyPaid, 2), 2);
	fm.prpLcompensateSumNoDutyFee.value = point(round(dblSumNoDutyFee, 2), 2);
	fm.prpLcompensateSumPaid.value = point(round(dblSumPaid, 2), 2);
	fm.prpLcompensateSumThisPaid.value = point(round(dblSumThisPaid, 2), 2);
	fm.prpLcompensateSumSelfValue.value = point(round(dblRealPayAB, 2), 2);
}


/**
 @author 中科软
 @description 修理天数修改时触发(只对车辆停驶险)
 @param       无
 @return      boolean值
*/

function changeLossQuantity(field) {
	var fieldname = field.name; //域名
	var i = 0;
	var findex = 0; //定位序号

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	var LossQuantity = parseFloat(fm.all("prpLlossDtoLossQuantity")[findex].value);
	var UnitPrice = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].value);

	if (isNaN(LossQuantity))
		LossQuatity = 0;
	if (isNaN(UnitPrice))
		UnitPrice = 0;

	var Quantity = parseInt(fm.all("prpLlossDtoLossQuantity")[findex].oldValue);
	var UnitAmount = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].oldValue);

	if (LossQuantity > Quantity) {
		errorMessage("修理天数不能大於承保的天数(" + Quantity + "天)!");
		fm.LossQuantity.focus();
		fm.LossQuantity.select();
		return false;
	}
	if (UnitPrice > UnitAmount) {
		errorMessage("单位赔偿金额不能大於承保的单位限额(" + UnitAmount + ")!");
		fm.UnitPrice.focus();
		fm.UnitPrice.select();
		return false;
	}

	fm.all("prpLlossDtoSumLoss")[findex].value = point(round(LossQuantity * UnitPrice, 2), 2);
	fm.all("prpLlossDtoSumLoss")[findex].onchange();

	return true;
}

//

/**
 @author 中科软
 @description 计算赔付标的和人员核损金额之和
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calLoss() {
	//定义变量
	var dblSumLoss = 0;
	var LossSumLoss = 0;
	var PersonLossSumLoss = 0;
	var i = 0;

	try {
		for (i = 1; i < fm.all("prpLlossDtoSumLoss").length; i++) {
			LossSumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[i].value);

			if (isNaN(LossSumLoss))
				LossSumLoss = 0;

			dblSumLoss = dblSumLoss + LossSumLoss;
		}
	} catch (E) {}

	for (i = 1; i < fm.all("prpLpersonMedicalSumLoss").length; i++) {
		PersonLossSumLoss = parseFloat(fm.all("prpLpersonMedicalSumLoss")[i].value);
		if (isNaN(PersonLossSumLoss))
			PersonLossSumLoss = 0

		dblSumLoss = dblSumLoss + PersonLossSumLoss;
	}

	fm.prpLcompensateSumLoss.value = point(round(dblSumLoss, 2), 2);
}

function checkInputPower(field) {
	var fieldname = field.name; //域名
	var i = 0;
	var findex = 0; //定位序号

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	if (fm.all("prpLlossDtoKindCode")[findex].value == "B" && fm.all("prpLlossDtoFeeTypeCode")[findex].value != "27") {
		alert("B險別不允許輸入殘值！");
		setReadonlyOfElementOfLoss(fm.all("prpLlossDtoSumRest")[findex]);
		fm.all("prpLlossDtoSumRest")[findex].value = "0.00";
	}
}


/**
 @author 中科软
 @description 使一个输入域设置为只读
 @param       iElement: 域
 @return      无
 */

function setReadonlyOfElementOfLoss(iElement) {
	if (iElement.type == "select-one") {
		iElement.disabled = true;
	} else if (iElement.type == "text") {
		iElement.onfocus = null;
		iElement.readOnly = true;
		iElement.className = "readonly";
	}
}


/**
 @author 中科软
 @description 使一个输入域设置为可输入
 @param       iElement: 域
 @return      无
 */

function undoSetReadonlyOfElementOfLoss(iElement) {
	if (iElement.type == "select-one") {
		iElement.disabled = false;
	} else {
		iElement.readOnly = false;
		iElement.className = 'common';

	}
}


//离开域时的数字校验

function checkInteger(field, MinValue, MaxValue) {
	field.value = trim(field.value);
	var strValue = field.value;
	if (strValue == "")
		strValue = "0";
	var desc = field.description;
	//如果description属性不存在，则用name属性
	if (desc == null)
		desc = field.name;

	MinValue = parseInt(MinValue, 10);
	if (isNaN(MinValue))
		MinValue = MIN_INTEGER;

	MaxValue = parseInt(MaxValue, 10);
	if (isNaN(MaxValue))
		MaxValue = MAX_INTEGER;
	var value = parseInt(strValue, 10);
	if (isNaN(value) || value > MaxValue || value < MinValue) {
		errorMessage("请输入合法的" + desc + "\n类型为数字(integer),最小值为" + MinValue + ",最大值为" + MaxValue);
		field.focus();
		field.select();
		return false;
	}
	return true;
}

/**
 @author 中科软
 @description 校验计入赔款金额不能超过费用金额
 @param       Field: 触发域
 @return      boolean: 合法为true,否则为false
 @see         UICommon.js#isEmpty
 */

function checkChargeAmount(Field) {
	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var ChargeAmount = 0; //费用金额
	var ChargeRealPay = 0; //计入赔款金额

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	if (fieldname == "prpLchargeChargeReport") {
		var chargeCode = fm.prpLchargeChargeCode[findex].value; //费用类别
		var chargeAmount = fm.prpLchargeChargeAmount[findex].value; //费用类别
		if (chargeCode == '03') {
			fm.all("prpLchargeSumRealPay")[findex].value = parseFloat(chargeAmount);
		} else {
			fm.all("prpLchargeSumRealPay")[findex].value = 0;
		}
		calFund();
		if (!(isEmptyField(Field)) && !(isEmptyField(fm.all("prpLchargeSumRealPay")[findex]))) {
			ChargeAmount = parseFloat(chargeAmount);
			ChargeRealPay = parseFloat(fm.all("prpLchargeSumRealPay")[findex].value);
		}

	} else if (fieldname == "prpLchargeChargeAmount") {
		//reason:赔款费用中计入赔款金额自动带出且为只读
		var chargeCode = fm.prpLchargeChargeCode[findex].value; //费用类别
		//reason:赔款费用中计入赔款金额自动带出且为只读
		if (chargeCode == '03') {
			fm.all("prpLchargeSumRealPay")[findex].value = Field.value;
		} else {
			fm.all("prpLchargeSumRealPay")[findex].value = 0;
		}
		calFund();
		if (!(isEmptyField(Field)) && !(isEmptyField(fm.all("prpLchargeSumRealPay")[findex]))) {
			ChargeAmount = parseFloat(Field.value);
			ChargeRealPay = parseFloat(fm.all("prpLchargeSumRealPay")[findex].value);
		}
	} else if (fieldname == "prpLchargeSumRealPay") {
		if (!(isEmptyField(fm.all("prpLchargeChargeAmount")[findex])) && !(isEmptyField(Field))) {
			ChargeAmount = parseFloat(fm.all("prpLchargeChargeAmount")[findex].value);
			ChargeRealPay = parseFloat(Field.value);
		}
	}

	if (ChargeAmount < ChargeRealPay) {
		if (fieldname == "prpLchargeChargeAmount") {
			errorMessage("计入赔款金额不能超过费用金额！");
			Field.focus();
			Field.select();
			return false;
		} else if (fieldname == "prpLchargeSumRealPay") {
			errorMessage("计入赔款金额不能超过费用金额！");
			fm.all("prpLchargeChargeAmount")[findex].focus();
			fm.all("prpLchargeChargeAmount")[findex].select();
			return false;
		}
	}

	//车身划痕损失险最高费用为5000
	if (fm.all("prpLchargeKindCode")[findex].value == "L" && parseFloat(fm.all("prpLchargeChargeAmount")[findex].value) > 5000) {
		errorMessage("车身划痕损失险最高赔款费用为5000元！");
		fm.all("prpLchargeChargeAmount")[findex].focus();
		fm.all("prpLchargeChargeAmount")[findex].select();
		return false;
	}

	return true;
}

/**
 @author 中科软
 @description 同险别的免赔率，责任比例要相等
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function checkKindCode(Field) {

	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var ChargeAmount = 0; //费用金额
	var ChargeRealPay = 0; //计入赔款金额

	var findFlag = 0;

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}

	//取得当前险别代码 
	var strValue = fm.prpLpersonLossKindCode[findex].value;
	if (strValue == "")
		return;
	//判断选择的险别是否为出险日期当时生效的险别
	for (var j = 0; j < damageKind.length; j++) {
		if (damageKind[j] == strValue) {
			findFlag = 1;
			break;
		}
	}
	if (findFlag == 0) {
		alert("您選擇的險別不是出險日期時的險別,請重新進行選擇");
		fm.prpLpersonLossKindCode[findex].value = "";
		return false;
	}

	//先要计算免赔率,责任比率

	//校验輸入险别
	if (Field.name.indexOf("prpLpersonLossKind") > -1) {
		if (fm.all("prpLpersonLossKindCode")[findex].value != "" &&
			fm.all("prpLpersonLossKindCode")[findex].value != "B" &&
			fm.all("prpLpersonLossKindCode")[findex].value != "B1" &&
			fm.all("prpLpersonLossKindCode")[findex].value != "D3" &&
			fm.all("prpLpersonLossKindCode")[findex].value != "D4" &&
			fm.all("prpLpersonLossKindCode")[findex].value != "Y") {
			alert("人員賠付只允許輸入第三者責任、駕駛員責任、乘客責任、交通事故精神損害！");
			fm.all("prpLpersonLossKindCode")[findex].value = "";
			fm.all("prpLpersonLossKindName")[findex].value = "";
			Field.focus();
		}
	}

	var kindCode = "";
	for (i = 0; i < fm.all("prpLpersonLossKindCode").length; i++) {
		kindCode = fm.all("prpLpersonLossKindCode")[i].value;
		if (kindCode == fm.all("prpLpersonLossKindCode")[findex].value) {
			//只修改本人的免赔率
			if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("prpLpersonLossSerialNo")[findex].value) {
				fm.all("prpLpersonLossIndemnityDutyRate")[i].value = fm.all("prpLpersonLossIndemnityDutyRate")[findex].value;
				fm.all("prpLpersonLossDeductibleRate")[i].value = fm.all("prpLpersonLossDeductibleRate")[findex].value;
			}
		}
	}
}




/**
 @author 中科软
 @description 计算赔付人员的赔款金额(改变责任比例时造成的赔款金额的改变)
              涉及所有当前行的费用信息
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpay2(field) {
	alert("calRealpay2");
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DeductibleRate; //免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔付金额
	var temp;

	var fieldname = field.name; //域名

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			findex1 = i;
			break;
		}
	}
	DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
	DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;
	//给变量赋值
	SumLoss = 0;
	SumRest = 0; //
	ClaimRate = 0;
	Deductible = 0; //
	Deductibletemp = 0;
	Realpay = 0;
	temp = 0;

	for (i = 1; i < fm.all("prpLpersonMedicalSumDefPay").length; i++) {
		if (fm.all("personLossSerialNo")[i].value == fm.all("prpLpersonLossSerialNo")[findex].value) {

			SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[i].value);
			ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[i].value);

			if (isNaN(SumLoss))
				SumLoss = 0;
			if (isNaN(ClaimRate))
				ClaimRate = 0;
			else
				ClaimRate = ClaimRate / 100;

			/*计算赔款金额
			 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
			 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
			 */
			temp = (SumLoss - SumRest) * ClaimRate * DutyRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例
			Deductibletemp = temp * DeductibleRate; //免赔= temp * 免赔率
			Realpay = temp * (1 - DeductibleRate);
			fm.all("prpLpersonMedicalSumDefPay")[i].value = point(round(Realpay, 2), 2);
		}
	}
	//计算赔付人员中的赔付合计
	calSumRealpay(field);
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

/**
 @author 中科软
 @description 计算赔付人员中的赔付合计(改变单价、数目、赔偿比例、
              责任比例时造成的赔款金额的改变)
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calSumRealpay(field) {

	var findex = 0; //定位序号
	var i = 0;
	var Realpay; //赔款金额
	var SumRealpay; //赔付合计

	var fieldname = field.name; //域名

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	//给变量赋值
	Realpay = 0;
	SumRealpay = 0;

	//计算赔付合计
	for (i = 1; i < fm.all("prpLpersonMedicalSumDefPay").length; i++) {
		if (fm.all("personLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			Realpay = parseFloat(fm.all("prpLpersonMedicalSumDefPay")[i].value);
			if (isNaN(Realpay))
				Realpay = 0;

			SumRealpay = SumRealpay + Realpay;
		}
	}
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (getElementCount("personLossSerialNo") > 1) {
			if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
				findex1 = i;
				break;
			}
		}
	}
	alert(SumRealpay);
	//将用来显示的赔付合计设置到界面上
	fm.all("prpLpersonLossSumRealPay1")[findex1].value = point(round(SumRealpay, 2), 2);
}


/**
 @author 中科软
 @description 计算赔付人员的核损金额(改变单价和数目时造成的核损金额的改变)
 @param       Field: 触发域
 @return      无
 @see         UICommon.js#point、round
*/

function calSumLoss(Field) {

	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var UnitPrice; //单价
	var Quantity; //数量

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	//给变量赋值
	UnitPrice = parseFloat(fm.all("prpLpersonLossUnitAmount")[findex].value);
	Quantity = parseFloat(fm.all("prpLpersonLossLossQuantity")[findex].value);
	SumLoss = 0;

	//计算核损金额
	if (isNaN(UnitPrice))
		UnitPrice = 0;
	if (isNaN(Quantity))
		Quantity = 0;

	SumLoss = UnitPrice * Quantity;
	fm.all("prpLpersonLossSumLoss")[findex].value = point(round(SumLoss, 2), 2);

	//计算赔付人员的赔款金额
	calRealpay1(Field);

	//计算赔付标的和人员核损金额之和
	calLoss();
}

/**
 @author 中科软
 @description 计算赔付人员的赔款金额(改变单价、数目和赔偿比例时造成的赔款金额的改变)
              只涉及触发域所对应的当前行的费用信息
 @param       Field: 触发域
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpay1(Field) {
	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DeductibleRate; //免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔付金额
	var temp;

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}


	//给变量赋值
	SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[findex].value);
	SumRest = 0; //
	ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[findex].value);
	DeductibleRate = 0;
	Deductible = 0; //
	Deductibletemp = 0;
	DutyRate = 0;
	Realpay = 0;
	temp = 0;
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			findex1 = i;
			break;
		}
	}
	DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
	DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
	if (isNaN(SumLoss))
		SumLoss = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;

	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
	 */
	temp = (SumLoss - SumRest) * ClaimRate * DutyRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例
	Deductibletemp = temp * DeductibleRate; //免赔= temp * 免赔率
	Realpay = temp * (1 - DeductibleRate);
	fm.all("prpLpersonMedicalSumDefPay")[findex].value = point(round(Realpay, 2), 2);
	//计算赔付人员中的赔付合计
	calSumRealpay(Field);
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}


/**
 @author 中科软
 @description 赔付金额修改时触发
 @param       Field:触发域
 @return      boolean值
*/

function changePersonLossRealpay(Field) {
	var fieldname = Field.name;
	var i = 0;
	var findex = 0;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}

	var PersonLossSumLoss = fm.all("prpLpersonLossSumLoss")[findex].value;
	var PersonLossRealpay = parseFloat(Field.value);

	if (isNaN(PersonLossSumLoss))
		PersonLossSumLoss = 0;
	if (isNaN(PersonLossRealpay))
		PersonLossRealpay = 0;

	if (PersonLossRealpay > PersonLossSumLoss) {
		errorMessage("赔付金额不能大於核损金额!");
		Field.focus();
		Field.select();
		return false;
	}
	//计算赔付人员中的赔付合计
	calSumRealpay(Field);
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
	return true;
}

/**
 @author 中科软
 @description 改变理赔类型时校验
 @param       Field:触发域
 @return      boolean型，合法返回true,不合法返回false
 */

function changeLFlag(Field) {
	if (Field.value == 'D') {
		errorMessage("非国内货运险赔款计算书理赔类型不能为D!");
		Field.focus();
		return false;
	}
	return true;
}



/**
 @author 中科软 //页面初始话的时候用
 @description 计算赔付人员中的赔付合计(改变单价、数目、赔偿比例、
              责任比例时造成的赔款金额的改变)
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calSumRealpayInit() {
	var i = 0;
	var Realpay = 0; //赔款金额
	var SumRealpay = 0; //赔付合计
	//定位
	for (i = 0; i < fm.all("prpLpersonLossSumRealPay1").length; i++) {
		//给变量赋值
		Realpay = 0;
		SumRealpay = 0;

		//计算赔付合计
		for (j = 0; j < fm.all("prpLpersonMedicalSumDefPay").length; j++) {
			if (fm.all("personLossSerialNo")[j].value == fm.all("prpLpersonLossSerialNo")[i].value) {
				Realpay = parseFloat(fm.all("prpLpersonMedicalSumDefPay")[j].value);
				if (isNaN(Realpay))
					Realpay = 0;

				SumRealpay = SumRealpay + Realpay;
			}
		}
		//将用来显示的赔付合计设置到界面上
		fm.all("prpLpersonLossSumRealPay1")[i].value = point(round(SumRealpay, 2), 2);
	}
	return true;
}

/**
 @author 中科软
 @description 分险别校验是否超过保单中的限额
 @param       KindCode:险别
 @param       PersonNo:人员序号
 @return      无
 */

function getDeductibleRate(Field, Type) {
	var index = 0;
	var KindCode = "";

	if (Type == "lLoss") //赔付标的
	{
		index = getElementOrder(Field) - 1;
		KindCode = fm.all("prpLlossDtoKindCode")[index].value;
	} else if (Type == "Charge") {
		index = getElementOrder(Field) - 1;
		KindCode = fm.prpLchargeKindCode[index].value;
	} else {
		index = getElementOrder(Field) - 1;
		KindCode = fm.prpLpersonLossKindCode[index].value;
	}

	if (KindCode == "")
		return;

	var oldTarget = fm.target;
	var oldAction = fm.action;

	fm.target = "fraSubmit";
	fm.action = "/claim/pages/DAA/compensate/sunny/DAAGetDeductibleRateSubmit.jsp?KindCode=" + KindCode + "&Type=" + Type + "&Index=" + index;
	fm.submit();
	fm.target = oldTarget;
	fm.action = oldAction;

}


/**
 @author 中科软
 @description 险别是车损或三者，並且损失明细为27扣免赔时，允许輸入残值，因为赔偿金额=-残值
 @param       KindCode:险别
 @param       PersonNo:人员序号
 @return      无
 */

function changeLossSumRestShowStyle(Field) //赔付标的信息中若险别为“三者险”则置残值为空，且只读
{
	var index1 = 0;
	index1 = getElementOrder(Field) - 1;
	if (fm.prpLlossDtoKindCode[index1].value != "M") {
		fm.button_Loss_Refresh.disabled = true;
	} else if (fm.prpLlossDtoKindCode[index1].value == "M") {
		fm.button_Loss_Refresh.disabled = false;
	}
	if (fm.prpLlossDtoKindCode[index1].value == "B" && fm.prpLlossDtoFeeTypeCode[index1].value != "27") {
		setReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
		fm.prpLlossDtoSumRest[index1].value = "";
	} else {
		undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
	}
}

function changeLossClaimRate(Field) //赔付标的信息中若险别为“车损险”则置赔付比例为（A险别的保额除以新车购置价），且赔付比例只读
{
	var index1 = 0;
	index1 = getElementOrder(Field) - 1;
	if (fm.prpLlossDtoKindCode[index1].value == "A") {
		var escapeFlag = fm.prpLcompensateEscapeFlag.value;
		if (!(escapeFlag.length > 1 && escapeFlag.substring(1, 2) == "Y")) {
			var purchasePrice = parseFloat(fm.prpLcompensatePurchasePrice.value);
			var AKindCodeAmount = parseFloat(fm.prpLlossDtoAmount[index1].value);
			if (purchasePrice > 0 && purchasePrice != AKindCodeAmount) {
				var lossClaimRate = AKindCodeAmount * 100.00 / purchasePrice;
				fm.prpLlossDtoClaimRate[index1].value = point(round(lossClaimRate, 2), 2);
				setReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
			}
		}
	} else {
		if (fm.prpLlossDtoFeeTypeCode[index1].value != "27" && fm.prpLlossDtoKindCode[index1].value != "M")
			undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
	}
}
//reason:增加自负额

function getValue(Field) {
	var index1 = 0;
	index1 = getElementOrder(Field) - 1;
	if ((fm.prpLlossDtoKindCode[index1].value == "A" || fm.prpLlossDtoKindCode[index1].value == "B") && fm.prpLlossDtoFeeTypeCode[index1].value == "27") {
		var countA = 0;
		var countB = 0;
		for (var i = 0; i < fm.all("prpLlossDtoKindCode").length; i++) {
			if (fm.all("prpLlossDtoFeeTypeCode")[i].value == '27') {
				if (fm.all("prpLlossDtoKindCode")[i].value == "A") {
					countA++;
					if (countA >= 2) {
						errorMessage("赔付标的中每个险别下的扣免赔只能輸入一次!");
						return false;
					}
				} else if (fm.all("prpLlossDtoKindCode")[i].value == "B") {
					countB++;
					if (countB >= 2) {
						errorMessage("赔付标的中每个险别下的扣免赔只能輸入一次!");
						return false;
					}
				}
			}
		}
		if (countA < 2 && countB < 2) {
			setReadonlyOfElementOfLoss(fm.prpLlossDtoItemValue[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoSumLoss[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoIndemnityDutyRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoLossQuantity[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoDeductibleRate[index1]);
			undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);

			var KindCode = "";
			KindCode = fm.prpLlossDtoKindCode[index1].value;

			var oldTarget = fm.target;
			var oldAction = fm.action;

			fm.target = "fraSubmit"
			fm.action = "/claim/pages/DAA/compensate/DAAGetValueSubmit.jsp?KindCode=" + KindCode + "&PolicyNo=" + fm.prpLcompensatePolicyNo.value + "&Index=" + index1;
			fm.submit();

			fm.target = oldTarget;
			fm.action = oldAction;
		}
	} else {
		undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
		undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoItemValue[index1]);
		undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoSumLoss[index1]);
		if (fm.prpLlossDtoKindCode[index1].value == 'T') {
			undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoLossQuantity[index1]);
			undoSetReadonlyOfElementOfLoss(fm.prpLlossDtoUnitPrice[index1]);
		} else {
			setReadonlyOfElementOfLoss(fm.prpLlossDtoLossQuantity[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoUnitPrice[index1]);
		}

		if (fm.prpLlossDtoKindCode[index1].value == "B" && fm.prpLlossDtoFeeTypeCode[index1].value != "27") {
			setReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
		}
		if (fm.prpLlossDtoKindCode[index1].value == "A" && fm.prpLcompensatePurchasePrice.value != fm.prpLlossDtoAmount[index1].value) {
			setReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
		}

		if (fm.prpLlossDtoKindCode[index1].value != "M") {
			fm.button_Loss_Refresh.disabled = true;
		}
		if (fm.prpLlossDtoKindCode[index1].value == "M") {
			fm.button_Loss_Refresh.disabled = false;
			setReadonlyOfElementOfLoss(fm.prpLlossDtoSumRest[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoItemValue[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoSumLoss[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoIndemnityDutyRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoLossQuantity[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoClaimRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoDeductibleRate[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoFeeTypeCode[index1]);
			setReadonlyOfElementOfLoss(fm.prpLlossDtoFeeTypeName[index1]);
		}
	}
}


function checkLossDeductibleRate(Field) {
	if (parseFloat(Field.value) < parseFloat(Field.oldValue)) {
		alert("免賠率只能上調不允許下調！");
		Field.value = Field.oldValue;
		calRealpay(Field);
		Field.focus();
		return false;
	}
	return true;
}
/**
 @author 中科软
 @description 汇总
 @param       无
 @return      无
 */

function showLossCollect() {
	var newWindow = window.open("/claim/DAA/compensate/DAALossCollect.jsp", "new", "width=500,height=220,top=200,left=200,scrollbars=yes");
}

/**
 @author 中科软
 @description 按险别名称，项目名称汇总信息
 @param       无
 @return      返回一个包含险别名称，项目名称，核损金额，赔偿金额的数组
 */

function getLoss() {
	var arrayLoss;
	var arrayLossCollect = new Array();
	var i = 0
	var j = 0;
	var existFlag = false;
	for (i = 1; i < fm.all("lossDtoSerialNo").length; i++) {
		arrayLoss = new Array();

		arrayLoss["LossKindName"] = fm.prpLlossDtoKindName[i].value;
		arrayLoss["LossName"] = fm.prpLlossDtoLossName[i].value;
		arrayLoss["LossSumLoss"] = parseFloat(fm.prpLlossDtoSumLoss[i].value);
		arrayLoss["LossRealPay"] = parseFloat(fm.prpLlossDtoSumRealPay[i].value);

		if (isNaN(arrayLoss["LossSumLoss"]))
			arrayLoss["LossSumLoss"] = 0;
		if (isNaN(arrayLoss["LossRealPay"]))
			arrayLoss["LossRealPay"] = 0;
		//按险别名称，项目名称汇总信息
		for (j = 0; j < arrayLossCollect.length; j++) {
			if (arrayLossCollect[j]["LossKindName"] == arrayLoss["LossKindName"] && arrayLossCollect[j]["LossName"] == arrayLoss["LossName"]) {
				existFlag = true;
				break;
			} else {
				existFlag = false;
			}
		}

		if (!existFlag) {
			arrayLossCollect[j] = arrayLoss; //一个汇总项
		} else {
			arrayLossCollect[j]["LossSumLoss"] = arrayLossCollect[j]["LossSumLoss"] + arrayLoss["LossSumLoss"];
			arrayLossCollect[j]["LossRealPay"] = arrayLossCollect[j]["LossRealPay"] + arrayLoss["LossRealPay"];
		}
	}
	return arrayLossCollect;
}

/**
 @author 中科软
 @description 刷新赔付标的中不计免赔险别的不计免赔值
 @param       无
 @return      无
 */

function refreshLoss(Field) {
	var index = 0;
	index = getElementOrder(Field) - 1;

	var i = 0;
	var j = 0;
	var Deductible = 0;
	var LossDeductibleRate = 0;
	var PersonLossDeductibleRate = 0;
	var sumLossRealPay = 0;
	var strFlag = "";
	var bFind = false;

	var strRiskCode = fm.prpLcompensateRiskCode.value;
	var LossDeductibleRate1 = 0;
	var PersonLossDeductibleRate1 = 0;
	var strIsSafeLoad = fm.prpLcompensateDeductCond.value;
	strIsSafeLoad = strIsSafeLoad.substring(2, 3); //取违反安全装载特殊免赔条件

	//查找是否輸入了不计免赔险
	for (i = 0; i < fm.all("prpLlossDtoSerialNo").length; i++) {
		if (fm.prpLlossDtoKindCode[i].value == 'M') {
			bFind = true;
			break;
		}
	}

	//没有輸入不计免赔险则无需刷新
	if (bFind == false)
		return;

	var oldAction = fm.action;
	var oldTarget = fm.target;
	fm.action = "/claim/DAA/compensate/DAAGetDeductibleSubmit.jsp?PolicyNo=" + fm.prpLcompensatePolicyNo.value + "&DamageStartDate=" + fm.DamageStartDate.value + "&Index=" + index;
	fm.target = "fraCalculate";

	fm.submit();

	fm.action = oldAction;
	fm.target = oldTarget;
}



/**
 @author 中科软
 @description 计算赔付标的中的赔偿金额（改变实际价值、核定损失、残值、责任比例时触发）
              计算赔款金额
              如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
              # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpayForSunny(field) {
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DutyDeductibleRate; //事故责任免赔率
	var DeductibleRate; //免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var mainKindCode; //主险代码
	var DeductibleRateOfMainKind; //主险的绝对免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔偿金额
	var temp;
	var unitPrice;
	//取得当前的数据
	var fieldname = field.name;
	var findex = 0;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	SumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[findex].value);
	SumRest = parseFloat(fm.all("prpLlossDtoSumRest")[findex].value);
	ClaimRate = parseFloat(fm.all("prpLlossDtoClaimRate")[findex].value);
	DutyRate = parseFloat(fm.all("prpLlossDtoIndemnityDutyRate")[findex].value);
	DutyDeductibleRate = parseFloat(fm.all("prpLlossDtoDutyDeductibleRate")[findex].value);
	DeductibleRate = parseFloat(fm.all("prpLlossDtoDeductibleRate")[findex].value);
	DriverDeductibleRate = parseFloat(fm.all("prpLlossDtoDriverDeductibleRate")[findex].value);
	unitPrice = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].value);
	if (fm.prpLcompensateRiskCode.value == "DAS" && fm.all("prpLlossDtoKindCode")[findex].value == "F") {
		DeductibleRate = 10.0;
		fm.LossDeductibleRate.value = point(round(DeductibleRate, 2), 1)
	}
	Deductible = parseFloat(fm.all("prpLlossDtoDeductible")[findex].value);
	Amount = parseFloat(fm.all("prpLlossDtoAmount")[findex].value);
	if (isNaN(Amount))
		Amount = 0;
	if (isNaN(SumLoss))
		SumLoss = 0;
	if (isNaN(SumRest))
		SumRest = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;
	if (isNaN(DutyDeductibleRate))
		DutyDeductibleRate = 0;
	else
		DutyDeductibleRate = DutyDeductibleRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;

	if (isNaN(DriverDeductibleRate))
		DriverDeductibleRate = 0;
	else
		DriverDeductibleRate = DriverDeductibleRate / 100;

	if (isNaN(Deductible))
		Deductible = 0;

	if (isNaN(unitPrice))
		unitPrice = 0;


	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
	 */
	temp = (SumLoss - SumRest) * ClaimRate * DutyRate;
	if (fm.all("prpLlossDtoKindCode")[findex].value == "C" || fm.all("prpLlossDtoKindCode")[findex].value == "L") {
		temp = (SumLoss - SumRest) * ClaimRate;
	}

	Deductibletemp = temp * DeductibleRate;
	if (fm.prpLcompensateRiskCode.value == "DAS" && fm.all("prpLlossDtoKindCode")[findex].value == "F" && Deductibletemp < 100.0 && temp > 0.0) {
		Deductibletemp = 100.0;

		Realpay = temp - Deductibletemp;
		if (temp > 0) {
			DeductibleRate = (Deductibletemp * 100.0) / temp;
			fm.all("prpLlossDtoDeductibleRate")[findex].value = point(round(DeductibleRate, 2), 2)
		}
	} else {
		//个别附加险要获得主险的绝对免赔率
		if (fm.all("prpLlossDtoKindCode")[findex].value == "D2" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D3" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D4" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "H") {
			mainKindCode = "B";
		}
		if (fm.all("prpLlossDtoKindCode")[findex].value == "G0" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "L" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Z" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "X") {
			mainKindCode = "A";
		}
		for (var j = 0; j < fm.all("prpLlossDtoKindCode").length; j++) {
			if (fm.all("prpLlossDtoKindCode")[j].value == mainKindCode) {
				DeductibleRateOfMainKind = fm.all("prpLlossDtoDeductibleRate")[j].value;
				break;
			}
		}
		//应 要求,屏蔽掉主险的责任免赔率
		DeductibleRateOfMainKind = 0;
		if (fm.all("prpLlossDtoKindCode")[findex].value == "D2" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D3" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D4" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "G0" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "L" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Z" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "X" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "H") {
			fm.all("PrpLlossDtoMainKindDuctibleRate")[findex].value = DeductibleRateOfMainKind;
			Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate) * (1 - DeductibleRateOfMainKind / 100);
		} else if (fm.all("prpLlossDtoKindCode")[findex].value == "T" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "C") {
			Realpay = (temp * (1 - DutyDeductibleRate) - unitPrice) * (1 - DriverDeductibleRate);

		} else {
			Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate);
		}
	}

	if ((fm.all("prpLlossDtoKindCode")[findex].value == "B" || fm.all("prpLlossDtoKindCode")[findex].value == "D2" || fm.all("prpLlossDtoKindCode")[findex].value == "W") && temp > Amount) {
		Realpay = Amount * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate);
	}
	if (Realpay > Amount && Amount > 0) {
		Realpay = Amount;
	}
	if ((fm.all("prpLlossDtoKindCode")[findex].value == "A" || fm.all("prpLlossDtoKindCode")[findex].value == "B") && fm.all("prpLlossDtoFeeTypeCode")[findex].value == "27") {
		Realpay = SumRest * (-1);
	}
	fm.all("prpLlossDtoSumRealPay")[findex].value = point(round(Realpay, 2), 2);

	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

function checkAmount(kindCode, amount) {
	var sumAmountOfKind = 0;
	for (i = 1; i < fm.all("prpLlossDtoKindCode").length; i++) {
		if (fm.all("prpLlossDtoKindCode")[i].value == kindCode) {
			sumAmountOfKind = sumAmountOfKind + parseFloat(fm.all("prpLlossDtoSumRealPay")[i].value);
		}
	}
	alert("sumAmountOfKind" + sumAmountOfKind);
	alert("amount" + amount);
	if (sumAmountOfKind > parseFloat(amount)) {
		return false;
	} else {
		return true;
	}
}


/**
 @author 中科软
 @description 计算赔付人员的赔款金额(改变责任比例时造成的赔款金额的改变)
              涉及所有当前行的费用信息
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpay2ForSunny(field) {
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例

	var DutyDeductibleRate; //事故责任免赔率



	var DriverDeductibleRate; //驾驶员免赔率
	var DeductibleRate; //绝对免赔率
	var MainKindDeductibleRate; //所在主险的绝对免赔率


	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var ArrangeRate; //协商赔款比例
	var Realpay; //赔付金额
	var temp;

	var fieldname = field.name; //域名

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	//如果本条费用信息不存在则返回
	if (getElementCount("personLossSerialNo") <= findex) {
		return;
	}
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (getElementCount("personLossSerialNo") > 1) {
			if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
				findex1 = i;
				break;
			}
		}
	}
	DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
	ArrangeRate = parseFloat(fm.all("prpLpersonLossArrangeRate")[findex1].value);
	DutyDeductibleRate = parseFloat(fm.all("prpLpersonLossDutyDeductibleRate")[findex1].value);
	DriverDeductibleRate = parseFloat(fm.all("prpLpersonLossDriverDeductibleRate")[findex1].value);
	DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
	MainKindDeductibleRate = parseFloat(fm.all("prpLpersonLossMainKindDeductibleRate")[findex1].value);
	Amount = parseFloat(fm.all("prpLpersonLossAmount")[findex1].value);
	if (isNaN(ArrangeRate))
		ArrangeRate = 0;
	else
		ArrangeRate = ArrangeRate / 100;

	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;

	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;

	if (isNaN(DutyDeductibleRate))
		DutyDeductibleRate = 0;
	else
		DutyDeductibleRate = DutyDeductibleRate / 100;

	if (isNaN(DriverDeductibleRate))
		DriverDeductibleRate = 0;
	else
		DriverDeductibleRate = DriverDeductibleRate / 100;

	if (isNaN(MainKindDeductibleRate))
		MainKindDeductibleRate = 0;
	else
		MainKindDeductibleRate = MainKindDeductibleRate / 100;


	//给变量赋值
	SumLoss = 0;
	SumRest = 0; //
	ClaimRate = 0;
	Deductible = 0; //
	Deductibletemp = 0;
	Realpay = 0;
	temp = 0;
	for (i = 1; i < fm.all("prpLpersonMedicalSumDefPay").length; i++) {
		if (fm.all("personLossSerialNo")[i].value == fm.all("prpLpersonLossSerialNo")[findex].value) {

			SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[i].value);
			ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[i].value);

			if (isNaN(SumLoss))
				SumLoss = 0;
			if (isNaN(ClaimRate))
				ClaimRate = 0;
			else
				ClaimRate = ClaimRate / 100;

			/*计算赔款金额
			 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
			 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
			 */
			temp = (SumLoss - SumRest) * ClaimRate * DutyRate * ArrangeRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例 * 协商赔偿比例
			Deductibletemp = temp * DeductibleRate; //免赔= temp * 免赔率
			MainKindDeductibleRate = 0;
			//部分险别需要承上所在主险的绝对免赔率
			if (fm.all("prpLpersonLossKindCode")[findex].value == "D2" ||
				fm.all("prpLpersonLossKindCode")[findex].value == "D3" ||
				fm.all("prpLpersonLossKindCode")[findex].value == "D4" ||
				fm.all("prpLpersonLossKindCode")[findex].value == "Y" ||
				fm.all("prpLpersonLossKindCode")[findex].value == "G0" ||
				fm.all("prpLpersonLossKindCode")[findex].value == "L" ||
				fm.all("prpLpersonLossKindCode")[findex].value == "Z" ||
				fm.all("prpLpersonLossKindCode")[findex].value == "X" ||
				fm.all("prpLpersonLossKindCode")[findex].value == "H") {
				Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate) * (1 - MainKindDeductibleRate);
			} else {
				Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate);
			}

			fm.all("prpLpersonMedicalSumDefPay")[i].value = point(round(Realpay, 2), 2);
		}
	}

	//计算赔付人员中的赔付合计
	calSumRealpay(field);

	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();

}




/**
 @author 中科软
 @description 计算赔付人员的赔款金额(改变单价、数目和赔偿比例时造成的赔款金额的改变)
              只涉及触发域所对应的当前行的费用信息
 @param       Field: 触发域
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpay1ForSunny(Field) {
	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例

	var DutyDeductibleRate; //事故责任免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var DeductibleRate; //绝对免赔率
	var MainKindDeductibleRate; //所在主险的绝对免赔率

	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var Realpay; //赔付金额
	var temp;

	//定位
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}


	//给变量赋值
	SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[findex].value);
	SumRest = 0; //
	ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[findex].value);
	DeductibleRate = 0;
	Deductible = 0; //
	Deductibletemp = 0;
	DutyRate = 0;
	Realpay = 0;
	temp = 0;
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			findex1 = i;
			break;
		}
	}
	DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
	DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
	DutyDeductibleRate = parseFloat(fm.all("prpLpersonLossDutyDeductibleRate")[findex1].value);
	DriverDeductibleRate = parseFloat(fm.all("prpLpersonLossDriverDeductibleRate")[findex1].value);
	MainKindDeductibleRate = parseFloat(fm.all("prpLpersonLossMainKindDeductibleRate")[findex1].value);
	Amount = parseFloat(fm.all("prpLpersonLossAmount")[findex1].value);
	if (isNaN(SumLoss))
		SumLoss = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;

	if (isNaN(DutyDeductibleRate))
		DutyDeductibleRate = 0;
	else
		DutyDeductibleRate = DutyDeductibleRate / 100;

	if (isNaN(DriverDeductibleRate))
		DriverDeductibleRate = 0;
	else
		DriverDeductibleRate = DriverDeductibleRate / 100;

	if (isNaN(MainKindDeductibleRate))
		MainKindDeductibleRate = 0;
	else
		MainKindDeductibleRate = MainKindDeductibleRate / 100;


	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;

	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 * # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
	 */

	temp = (SumLoss - SumRest) * ClaimRate * DutyRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例
	Deductibletemp = temp * DeductibleRate; //免赔= temp * 免赔率

	//部分险别需要承上所在主险的绝对免赔率
	if (fm.all("prpLpersonLossKindCode")[findex1].value == "D2" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "D3" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "D4" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "Y" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "G0" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "L" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "Z" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "X" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "H") {
		Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate) * (1 - MainKindDeductibleRate);
	} else {
		Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate);
	}
	if (temp > Amount) {
		Realpay = Amount * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DriverDeductibleRate);
	}
	fm.all("prpLpersonMedicalSumDefPay")[findex].value = point(round(Realpay, 2), 2);

	//计算赔付人员中的赔付合计
	calSumRealpay(Field);

	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

function changePrpLcompensateFinallyFlag() {
	if (fm.prpLcompensateFinallyFlag[0].checked) {
		Lltext.style.display = "";
	} else {
		Lltext.style.display = "none";
	}
}

function backWardPolicy() {
	var SHOWTYPE = "SHOW";
	var BizNo = fm.prpLcompensatePolicyNo.value;
	var RiskCode = fm.prpLcompensateRiskCode.value;
	var damageDate = fm.damageDate.value;
	//   var vURL = '/prpall/' + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo='+ BizNo+'&RiskCode='+ RiskCode+'&damageDate='+ damageDate;
	var vURL = '/claim/pages/common/pub/PolicyShowCenter.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate;
	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


function getArrangeRate(field) {
	var fieldname = field.name;
	var i = 0;
	var ArrangeRate_A;
	var prpLlossDtoKindCode_A;


	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	prpLlossDtoKindCode_A = fm.all("prpLlossDtoKindCode")[findex].value;
	ArrangeRate_A = parseFloat(fm.all("prpLlossDtoArrangeRate")[findex].value);

	if (isNaN(ArrangeRate_A) || ArrangeRate_A.length < 1) {
		ArrangeRate_A = 0;
	}

	for (i = 1; i < fm.all("prpLlossDtoArrangeRate").length; i++) {
		if (fm.all("prpLlossDtoKindCode")[i].value == prpLlossDtoKindCode_A) {
			fm.all("prpLlossDtoArrangeRate")[i].value = point(round(ArrangeRate_A, 2), 2);
		}
	}
	return true;

}

function getIndemnityDutyRate(field) {
	var fieldname = field.name;
	var i = 0;
	var findex = 0;
	var propSumLoss;
	var propSumReject;
	var propSumDefLoss;
	var IndemnityDutyRate_A;
	var prpLlossDtoKindCode_A;


	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	prpLlossDtoKindCode_A = fm.all("prpLlossDtoKindCode")[findex].value;
	IndemnityDutyRate_A = parseFloat(fm.all("prpLlossDtoIndemnityDutyRate")[findex].value);

	if (isNaN(IndemnityDutyRate_A) || IndemnityDutyRate_A.length < 1) {
		IndemnityDutyRate_A = 0;
	}
	for (i = 1; i < fm.all("prpLlossDtoIndemnityDutyRate").length; i++) {
		if (fm.all("prpLlossDtoKindCode")[i].value == prpLlossDtoKindCode_A) {
			fm.all("prpLlossDtoIndemnityDutyRate")[i].value = point(round(IndemnityDutyRate_A, 2), 2);
		}
	}
	return true;
}

/**
 @author 中科软
 @description 计算赔付标的中的赔偿金额（改变实际价值、核定损失、残值、责任比例时触发）
              计算赔款金额
              如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
              # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpayForDuBang(field) {
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DutyDeductibleRate; //事故责任免赔率
	var DeductibleRate; //免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var mainKindCode; //主险代码
	var DeductibleRateOfMainKind; //主险的绝对免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var ArrangeRate; //协商赔偿比例
	var Realpay; //赔偿金额
	var temp;
	var unitPrice;
	var Amount;

	var fieldname = field.name;
	var findex = 0;
	//modify by caopeng start at 2005-12-23 增加try,catch块，屏蔽一些非正常操作带来的js错误
	try {
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == field) {
				findex = i;
				break;
			}
		}

		SumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[findex].value);
		SumRest = parseFloat(fm.all("prpLlossDtoSumRest")[findex].value);
		ClaimRate = parseFloat(fm.all("prpLlossDtoClaimRate")[findex].value);
		DutyRate = parseFloat(fm.all("prpLlossDtoIndemnityDutyRate")[findex].value);
		//协商赔偿比例
		ArrangeRate = parseFloat(fm.all("prpLlossDtoArrangeRate")[findex].value);

		DutyDeductibleRate = parseFloat(fm.all("prpLlossDtoDutyDeductibleRate")[findex].value);
		DeductibleRate = parseFloat(fm.all("prpLlossDtoDeductibleRate")[findex].value);
		unitPrice = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].value);
		Amount = parseFloat(fm.all("prpLlossDtoAmount")[findex].value);
	} catch (E) {

	}
	if (isNaN(Amount))
		Amount = 0;
	if (isNaN(SumLoss))
		SumLoss = 0;
	if (isNaN(SumRest))
		SumRest = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;
	if (isNaN(ArrangeRate))
		ArrangeRate = 0;
	else
		ArrangeRate = ArrangeRate / 100;
	if (isNaN(DutyDeductibleRate))
		DutyDeductibleRate = 0;
	else
		DutyDeductibleRate = DutyDeductibleRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;
	if (isNaN(Deductible))
		Deductible = 0;

	if (isNaN(unitPrice))
		unitPrice = 0;

	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 协商免赔率 * 责任比例 * （1 - 免赔率）
	 */

	try {
		var kindCode = fm.all("prpLlossDtoKindCode")[findex].value;
		if (kindCode == "C" || kindCode == "L" || kindCode == "G") {
			//这些险种不需要按责任比例计算
			temp = (SumLoss - SumRest) * ClaimRate * ArrangeRate;
		} else {
			//其它险种需要按责任比例计算
			temp = (SumLoss - SumRest) * ClaimRate * ArrangeRate * DutyRate;
		}
		Deductibletemp = temp * DeductibleRate;

		//============个别附加险要获得主险的绝对免赔率
		if (fm.all("prpLlossDtoKindCode")[findex].value == "D2" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D3" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D4" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "H") {
			mainKindCode = "B";
		}
		if (fm.all("prpLlossDtoKindCode")[findex].value == "G0" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "L" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Z" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "X") {
			mainKindCode = "A";
		}
		for (var j = 0; j < fm.all("prpLlossDtoKindCode").length; j++) {
			if (fm.all("prpLlossDtoKindCode")[j].value == mainKindCode) {
				DeductibleRateOfMainKind = fm.all("prpLlossDtoDeductibleRate")[j].value;
				break;
			}
		}
		//应 要求,屏蔽掉主险的责任免赔率
		DeductibleRateOfMainKind = 0;
		if (fm.all("prpLlossDtoKindCode")[findex].value == "D2" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D3" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "D4" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Y" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "G0" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "L" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "Z" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "X" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "H") {
			fm.all("PrpLlossDtoMainKindDuctibleRate")[findex].value = DeductibleRateOfMainKind;
			Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DeductibleRateOfMainKind / 100);
		} else if (fm.all("prpLlossDtoKindCode")[findex].value == "T" ||
			fm.all("prpLlossDtoKindCode")[findex].value == "C") {
			//停驶、代步车调整
			Realpay = (temp * (1 - DutyDeductibleRate) - unitPrice);

		} else {
			Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate);
		}

		//reason:实赔处理，车上货物责任险、无过失责任险超限时系统的赔付计算不对
		if ((fm.all("prpLlossDtoKindCode")[findex].value == "B" || fm.all("prpLlossDtoKindCode")[findex].value == "D2" || fm.all("prpLlossDtoKindCode")[findex].value == "W") && temp > Amount) {
			if (Amount > 0) {
				Realpay = Amount * (1 - DutyDeductibleRate) * (1 - DeductibleRate);
			}
		}
		if (Realpay > Amount && Amount > 0) {
			Realpay = Amount;
		}
		//reason:险别是车损或三者，並且损失明细为27扣免赔，则赔偿金额=-残值
		if ((fm.all("prpLlossDtoKindCode")[findex].value == "A" || fm.all("prpLlossDtoKindCode")[findex].value == "B") && fm.all("prpLlossDtoFeeTypeCode")[findex].value == "27") {
			Realpay = SumRest * (-1);
		}
		fm.all("prpLlossDtoSumRealPay")[findex].value = point(round(Realpay, 2), 2);
	} catch (E) {}
	calFund();

}

/**
 @author 中科软
 @description 计算赔付标的中的赔偿金额（改变实际价值、核定损失、残值、责任比例时触发）
              计算赔款金额
              如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
              # 此条取消----如果免赔额：（核定损失 - 残值）* 赔偿比例 * 责任比例  - 免赔额
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calRealpayForDuBangAll() {
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例
	var DutyDeductibleRate; //事故责任免赔率
	var DeductibleRate; //免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var mainKindCode; //主险代码
	var DeductibleRateOfMainKind; //主险的绝对免赔率
	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var ArrangeRate; //协商赔偿比例
	var Realpay; //赔偿金额
	var temp;
	var unitPrice;
	var Amount;

	var findex = 0;
	for (i = 1; i < fm.all("prpLlossDtoSumLoss").length; i++) {
		findex = i;
		SumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[findex].value);
		SumRest = parseFloat(fm.all("prpLlossDtoSumRest")[findex].value);
		ClaimRate = parseFloat(fm.all("prpLlossDtoClaimRate")[findex].value);
		DutyRate = parseFloat(fm.all("prpLlossDtoIndemnityDutyRate")[findex].value);
		//协商赔偿比例
		ArrangeRate = parseFloat(fm.all("prpLlossDtoArrangeRate")[findex].value);
		DutyDeductibleRate = parseFloat(fm.all("prpLlossDtoDutyDeductibleRate")[findex].value);
		DeductibleRate = parseFloat(fm.all("prpLlossDtoDeductibleRate")[findex].value);
		unitPrice = parseFloat(fm.all("prpLlossDtoUnitPrice")[findex].value);
		Amount = parseFloat(fm.all("prpLlossDtoAmount")[findex].value);
		if (isNaN(Amount)) Amount = 0;
		if (isNaN(SumLoss)) SumLoss = 0;
		if (isNaN(SumRest)) SumRest = 0;
		if (isNaN(Deductible)) Deductible = 0;
		if (isNaN(unitPrice)) unitPrice = 0;

		if (isNaN(ClaimRate))
			ClaimRate = 0;
		else
			ClaimRate = ClaimRate / 100;

		if (isNaN(DutyRate))
			DutyRate = 0;
		else
			DutyRate = DutyRate / 100;

		if (isNaN(ArrangeRate))
			ArrangeRate = 0;
		else
			ArrangeRate = ArrangeRate / 100;

		if (isNaN(DutyDeductibleRate))
			DutyDeductibleRate = 0;
		else
			DutyDeductibleRate = DutyDeductibleRate / 100;

		if (isNaN(DeductibleRate))
			DeductibleRate = 0;
		else
			DeductibleRate = DeductibleRate / 100;

		/*计算赔款金额
		 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
		 */
		var kindCode = fm.all("prpLlossDtoKindCode")[findex].value;
		if (kindCode == "C" || kindCode == "L" || kindCode == "G") {
			temp = (SumLoss - SumRest) * ClaimRate * ArrangeRate;
		} else {
			temp = (SumLoss - SumRest) * ClaimRate * ArrangeRate * DutyRate;
		}
		//============个别附加险要获得主险的绝对免赔率
		if (kindCode == "D2" ||
			kindCode == "D3" ||
			kindCode == "D4" ||
			kindCode == "Y" ||
			kindCode == "H") {
			mainKindCode = "B";
		}
		if (kindCode == "G0" ||
			kindCode == "L" ||
			kindCode == "Z" ||
			kindCode == "Y" ||
			kindCode == "X") {
			mainKindCode = "A";
		}

		for (var j = 0; j < fm.all("prpLlossDtoKindCode").length; j++) {
			if (fm.all("prpLlossDtoKindCode")[j].value == mainKindCode) {
				DeductibleRateOfMainKind = fm.all("prpLlossDtoDeductibleRate")[j].value;
				break;
			}
		}
		//应 要求,屏蔽掉主险的责任免赔率
		DeductibleRateOfMainKind = 0;
		if (kindCode == "D2" ||
			kindCode == "D3" ||
			kindCode == "D4" ||
			kindCode == "Y" ||
			kindCode == "G0" ||
			kindCode == "L" ||
			kindCode == "Z" ||
			kindCode == "X" ||
			kindCode == "H") {
			fm.all("PrpLlossDtoMainKindDuctibleRate")[findex].value = DeductibleRateOfMainKind;
			Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - DeductibleRateOfMainKind / 100);
		} else if (kindCode == "T" || kindCode == "C") {
			//停驶、代步车调整
			Realpay = (temp * (1 - DutyDeductibleRate) - unitPrice);
		} else {
			Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate);
		}
		//reason:实赔处理，车上货物责任险、无过失责任险超限时系统的赔付计算不对
		if ((kindCode == "B" || kindCode == "D2" || kindCode == "W") && temp > Amount) {
			if (Amount > 0) {
				Realpay = Amount * (1 - DutyDeductibleRate) * (1 - DeductibleRate);
			}
		}
		if (Realpay > Amount && Amount > 0) {
			Realpay = Amount;
		}
		//reason:险别是车损或三者，並且损失明细为27扣免赔，则赔偿金额=-残值
		if ((kindCode == "A" || kindCode == "B") && fm.all("prpLlossDtoFeeTypeCode")[findex].value == "27") {
			Realpay = SumRest * (-1);
		}
		//modify by dengxh end 20040904
		fm.all("prpLlossDtoSumRealPay")[findex].value = point(round(Realpay, 2), 2);
	}
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

function calRealpay1ForDuBang(Field) {
	var fieldname = Field.name; //域名
	var i = 0;
	var findex = 0; //定位序号
	var SumLoss; //核损金额
	var SumRest; //残值
	var ClaimRate; //赔偿比例

	var DutyDeductibleRate; //事故责任免赔率
	var DriverDeductibleRate; //驾驶员免赔率
	var DeductibleRate; //绝对免赔率
	var MainKindDeductibleRate; //所在主险的绝对免赔率

	var Deductible; //免赔额
	var Deductibletemp; //免赔
	var DutyRate; //责任比例
	var ArrangeRate; //协商赔偿比例
	var Realpay; //赔付金额
	var temp;
	var Amount;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == Field) {
			findex = i;
			break;
		}
	}
	//============给变量赋值
	SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[findex].value);
	ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[findex].value);
	SumRest = 0;
	DeductibleRate = 0;
	Deductible = 0;
	Deductibletemp = 0;
	DutyRate = 0;
	ArrangeRate = 0;
	Realpay = 0;
	temp = 0;
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
			findex1 = i;
			break;
		}
	}
	DutyRate = parseFloat(fm.all("prpLpersonLossIndemnityDutyRate")[findex1].value);
	ArrangeRate = parseFloat(fm.all("prpLpersonLossArrangeRate")[findex1].value);
	DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
	DutyDeductibleRate = parseFloat(fm.all("prpLpersonLossDutyDeductibleRate")[findex1].value);
	MainKindDeductibleRate = parseFloat(fm.all("prpLpersonLossMainKindDeductibleRate")[findex1].value);
	Amount = parseFloat(fm.all("prpLpersonLossAmount")[findex1].value);
	//================初始化===================
	if (isNaN(SumLoss)) SumLoss = 0;
	if (isNaN(ClaimRate))
		ClaimRate = 0;
	else
		ClaimRate = ClaimRate / 100;
	if (isNaN(DeductibleRate))
		DeductibleRate = 0;
	else
		DeductibleRate = DeductibleRate / 100;

	if (isNaN(DutyDeductibleRate))
		DutyDeductibleRate = 0;
	else
		DutyDeductibleRate = DutyDeductibleRate / 100;

	if (isNaN(MainKindDeductibleRate))
		MainKindDeductibleRate = 0;
	else
		MainKindDeductibleRate = MainKindDeductibleRate / 100;
	if (isNaN(DutyRate))
		DutyRate = 0;
	else
		DutyRate = DutyRate / 100;

	if (isNaN(ArrangeRate))
		ArrangeRate = 0;
	else
		ArrangeRate = ArrangeRate / 100;


	/*计算赔款金额
	 * 如果免赔高：（核定损失 - 残值）* 赔偿比例 * 责任比例 * （1 - 免赔率）
	 */
	temp = (SumLoss - SumRest) * ClaimRate * DutyRate * ArrangeRate; //temp=（核定损失 - 残值）* 赔偿比例 * 责任比例
	Deductibletemp = temp * DeductibleRate; //免赔= temp * 免赔率


	//================绝对免赔率==============================
	//部分险别需要承上所在主险的绝对免赔率
	if (fm.all("prpLpersonLossKindCode")[findex1].value == "D2" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "D3" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "D4" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "Y" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "G0" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "L" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "Z" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "X" ||
		fm.all("prpLpersonLossKindCode")[findex1].value == "H") {
		Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate) * (1 - MainKindDeductibleRate);
	} else {
		Realpay = temp * (1 - DutyDeductibleRate) * (1 - DeductibleRate);
	}
	if (temp > Amount) {
		Realpay = Amount * (1 - DutyDeductibleRate) * (1 - DeductibleRate);
	}
	fm.all("prpLpersonMedicalSumDefPay")[findex].value = point(round(Realpay, 2), 2);

	//计算赔付人员中的赔付合计
	calSumRealpay(Field);
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}

function setAllIndemnityDutyRate() {
	var i = 0;
	var findex = 0;
	var propSumLoss;
	var propSumReject;
	var propSumDefLoss;
	var IndemnityDutyRate_A;
	var prpLlossDtoKindCode_A;


	IndemnityDutyRate_A = parseFloat(fm.prpLcompensateIndemnityDutyRate.value);
	if (isNaN(IndemnityDutyRate_A) || IndemnityDutyRate_A.length < 1) {
		IndemnityDutyRate_A = 0;
	}

	for (i = 1; i < fm.all("prpLlossDtoIndemnityDutyRate").length; i++) {
		fm.all("prpLlossDtoIndemnityDutyRate")[i].value = point(round(IndemnityDutyRate_A, 2), 2);
	}
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		fm.all("prpLpersonLossIndemnityDutyRate")[i].value = point(round(IndemnityDutyRate_A, 2), 2);
	}

	calRealpayForDuBangAll();

	if (fm.all("prpLpersonLossIndemnityDutyRate").length > 0) {

		for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
			calRealpay2ForSunny(fm.all("prpLpersonLossIndemnityDutyRate")[i]);
		}
	}
	return true;
}

function setRealPay() {
	for (i = 1; i < fm.all("prpLchargeSerialNo").length; i++) {
//		if (fm.all("prpLchargeChargeCode")[i].value == "03") {
//			fm.all("prpLchargeSumRealPay")[i].value = fm.all("prpLchargeChargeAmount")[i].value;
//		} else {
			fm.all("prpLchargeSumRealPay")[i].value = 0;
//		}
	}
	calFund();
}

function testljy() {
	//定位
	for (i = 0; i < fm.all("prpLpersonCommerceSerialNo").length; i++) {
		alert(fm.all("prpLpersonCommerceSerialNo")[i].value);
	}
}


function calCompelSumPropAndPerson() {
	disablebutton();
	var i = 0;
	var findex = 0; //定位序号
	var qindex = 0;
	var SumLoss; //核损金额
	var UnitPrice; //单价
	var Quantity; //数量
	var AmountPrice = 0;
	var allPropPrice = 0;
	var allMedicalPrice = 0;
	var allDeformityPrice = 0;

	var allPropPriceSumLoss = 0;
	var allMedicalPriceSumLoss = 0;
	var allDeformityPriceSumLoss = 0;
	var allPropPriceRest = 0;
	var allMedicalPriceRest = 0;
	var allDeformityPriceRest = 0;

	var duty = fm.indemnityDuty.value; //责任
	var configCode = fm.configCode.value;
	var quotaBZ_D = 0; //死亡給付
	var quotaBZ_M = 0; //醫療費用
	var quotaBZ_P = 0; //殘疾給付
	var personLossNumber = ["car", "threeCar", "outerCar"];
	for (var i = 0; i < personLossNumber.length; i++) {
		var DeathNumber = document.getElementsByName(personLossNumber[i] + "DeathNumber")[0];
		quotaBZ_D += parseFloat(DeathNumber.value) * medicalQuota;
		var MedicalNumber = document.getElementsByName(personLossNumber[i] + "MedicalNumber")[0];
		quotaBZ_M += parseFloat(MedicalNumber.value) * wealthQuota;
	}
	var wealthTitle = ["醫療費用", 0, 0, 0, 0, 0, 0];
	var medicalTitle = ["殘疾給付", 0, 0, 0, 0, 0, 0];
	var deformityTitle = ["死亡給付", 0, 0, 0, 0, 0, 0];
	var MedicalNumberPrice = [0, 0];
	var DisabilityNumberPrice = [0, 0];
	var DeathNumberPrice = [0, 0];
	$("table[name='PersonFeeMedical']").each(function () {

		var prpLpersonMedicalDetailCode = $(this).find("input[name='prpLpersonMedicalDetailCode']"); //取 费用代码
		var prpLpersonMedicalSumLoss = $(this).find("input[name='prpLpersonMedicalSumLoss']"); //核定损失
		var prpLpersonMedicalSumDefPay = $(this).find("input[name='prpLpersonMedicalSumDefPay']"); //核定赔偿
		$.each(prpLpersonMedicalDetailCode, function (i, n) {
			var value = 0;
				if(jQuery.isNumeric(prpLpersonMedicalSumDefPay[i].value)){
				value = parseFloat(prpLpersonMedicalSumDefPay[i].value);
			} else {
				prpLpersonMedicalSumDefPay[i].value = 0;
			}
			var sumLoss = parseFloat(prpLpersonMedicalSumLoss.get(i).value);
			if (!isNaN(value)) {
				if (n.value.indexOf("A") > -1) {
					if (!isNaN(sumLoss)) {
						MedicalNumberPrice[0] += sumLoss;
					}
					MedicalNumberPrice[1] += value;
				} else if (n.value.indexOf("C") > -1) {
					if (!isNaN(sumLoss)) {
						DisabilityNumberPrice[0] += sumLoss;
					}
					DisabilityNumberPrice[1] += value;
				} else { //如果伤亡情形下拉框的值为3，则将所有的"核定损失"金额加入"死亡给付"的 损失合计
					if (!isNaN(sumLoss)) {
						DeathNumberPrice[0] += sumLoss;
					}
					DeathNumberPrice[1] += value;
				}
			}
		});
	});
	//互碰自赔标志
	var payselfFlag = fm.payselfFlag.value;
	//无责代赔
	var nullallPropPrice = 0;
	quotaBZ_P = DisabilityNumberPrice[1];
	qindex++;
	wealthTitle[qindex] = MedicalNumberPrice[0];
	medicalTitle[qindex] = DisabilityNumberPrice[0];
	deformityTitle[qindex] = DeathNumberPrice[0];
	qindex++;
	wealthTitle[qindex] = 0;
	medicalTitle[qindex] = 0;
	deformityTitle[qindex] = 0;
	qindex++;
	wealthTitle[qindex] = MedicalNumberPrice[1];
	medicalTitle[qindex] = DisabilityNumberPrice[1];
	deformityTitle[qindex] = DeathNumberPrice[1];
	qindex++;
	wealthTitle[qindex] = quotaBZ_M;
	medicalTitle[qindex] = quotaBZ_P;
	deformityTitle[qindex] = quotaBZ_D;
	qindex++;
	if (fm.exceedingPayout.value == "false" && MedicalNumberPrice[1] > quotaBZ_M) {
		wealthTitle[qindex] = quotaBZ_M;
	} else {
		wealthTitle[qindex] = MedicalNumberPrice[1];
	}
	if (DisabilityNumberPrice[1] > quotaBZ_P) {
		medicalTitle[qindex] = quotaBZ_P;
	} else {
		medicalTitle[qindex] = DisabilityNumberPrice[1];
	}

	if (fm.exceedingPayout.value == "false" && allDeformityPrice > quotaBZ_D) {
		deformityTitle[qindex] = quotaBZ_D;
	} else {
		deformityTitle[qindex] = DeathNumberPrice[1];
	}
	for (x = 0; x < fm.all("wealth").length; x++) {
		fm.wealth[x].value = wealthTitle[x];
	}
	for (x = 0; x < fm.all("medical").length; x++) {
		fm.medical[x].value = medicalTitle[x];
	}
	for (x = 0; x < fm.all("diedeformity").length; x++) {
		fm.diedeformity[x].value = deformityTitle[x];
	}
	fm.totalPay.value = wealthTitle[5] + medicalTitle[5] + deformityTitle[5];
	fm.prpLdangerRiskSumPaid.value = point(round(wealthTitle[5] + medicalTitle[5] + deformityTitle[5], 0), 0);
	undisablebutton();
}

//清空理算报告

function clearPrpLctext() {
	var Context = document.getElementsByName('prpLctextContextInnerHTML');
	if (Context.length > 0) {
		Context[0].value = '';
	}
}


function checkBeyondQuota(field) {
	var findex = 0;
	var i = 0;
	var duty = fm.indemnityDuty.value; //责任
	var configCode = fm.configCode.value;
	var quotaBZ_D = 0;
	var quotaBZ_M = 0;
	var quotaBZ_P = 0;
	var inputObject = field;
	var fieldname = inputObject.name;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}

	if ('RISKCODE_DAZ' == configCode) {
		var length = document.getElementsByName("prpLpersonCommerceCasualties").length;
		if (length > 1) {
			length = length - 1;
		}
		quotaBZ_M = wealthQuota; //强制险医疗限额是20万
		quotaBZ_D = medicalQuota; //强制险死亡是200万
	}



	//财产+施救费
	var propSumDefPay = 0;
	var propfee = 0;
	var rescuFee = 0;
	//互碰自赔标志
	var payselfFlag = fm.payselfFlag.value;
	//无责代赔
	var licenseNo = trim(fm.prpLcompensateLicenseNo.value);
	var nulllicenseNo = "";
	var nullpropfee = 0;
	for (i = 1; i < fm.all("propFeeTypeName").length; i++) {
		propSumDefPay = parseFloat(fm.propSumDefPay[i].value);
		nulllicenseNo = trim(fm.propLicenseNo[i].value);
		if (isNaN(propSumDefPay)) propSumDefPay = 0;
		if ((licenseNo == nulllicenseNo) && (payselfFlag == "0")) {
			nullpropfee += propSumDefPay;
		} else {
			propfee += propSumDefPay;
		}
	}
	for (i = 1; i < fm.all("prpLchargeKindCode").length; i++) {
		rescuFee = parseFloat(fm.prpLchargeSumRealPay[i].value);
		if (isNaN(rescuFee)) rescuFee = 0;
		propfee += rescuFee;
	}

	if (fm.propSumLoss[findex]) {
		var propSumLoss = fm.propSumLoss[findex].value;
		if (isNaN(propSumLoss)) {
			propSumLoss = 0;
		}
		var propSumDefPay = fm.propSumDefPay[findex].value;
		if (isNaN(propSumDefPay)) {
			propSumDefPay = 0;
		}
		if (parseFloat(propSumLoss) < parseFloat(propSumDefPay)) {
			alert("核定賠償金額大於核定損失金額！");
			field.value = 0;
		}
	}

	//人伤医疗
	var medicSumDefPay = 0;
	var medicFee = 0;
	var deathSumDefPay = 0;
	var deathFee = 0;
	var feecategory = '';
	for (i = 1; i < fm.all("prpLpersonMedicalDetailCode").length; i++) {
		feecategory = fm.medicDeathFlag[i].value;
		if (feecategory == 'M') {
			medicSumDefPay = parseFloat(fm.prpLpersonMedicalSumDefPay[i].value);
			if (isNaN(medicSumDefPay)) {
				medicSumDefPay = 0;
			}
			medicFee += medicSumDefPay;
		}
		if (feecategory == 'D') {
			deathSumDefPay = parseFloat(fm.prpLpersonMedicalSumDefPay[i].value);
			if (isNaN(deathSumDefPay)) {
				deathSumDefPay = 0;
			}
			deathFee += deathSumDefPay;
		}

	}

	if (fm.prpLpersonMedicalSumLoss[findex]) {
		var propSumLoss = fm.prpLpersonMedicalSumLoss[findex].value;
		if (isNaN(propSumLoss)) {
			propSumLoss = 0;
		}
		var propSumDefPay = fm.prpLpersonMedicalSumDefPay[findex].value;
		if (isNaN(propSumDefPay)) {
			propSumDefPay = 0;
		}
		if (parseFloat(propSumLoss) < parseFloat(propSumDefPay)) {
			alert("核定賠償金額大於核定損失金額！");
			field.value = 0;
		}
	}
	var length = document.getElementsByName("prpLpersonMedicalSumDefPay").length;
	if ((fm.exceedingPayout.value == "false" || fm.exceedingPayout.value == "") && length > findex) {
		if (medicFee > quotaBZ_M) {
			alert("人員醫療核定賠償超過了限額（" + quotaBZ_M + "元） ");
			fm.prpLpersonMedicalSumDefPay[findex].value = 0;
			return true;
		}

		if (deathFee > quotaBZ_D) {
			alert("人員死亡傷殘核定賠償超過了限額（" + quotaBZ_D + "元） ");
			fm.prpLpersonMedicalSumDefPay[findex].value = 0;
			return true;
		}
	}
	return true;

}

function makeDisabledFalse(field) {
	var serialNo = 0; //定位
	var fieldName = field.name;
	for (var j = 0; j < fm.all(fieldName).length; j++) {
		if (field == fm.all(fieldName)[j]) {
			serialNo = j;
			break;
		}
	}

	for (var k = 1; k < fm.all("propSumDefPay").length; k++) {
		if (serialNo == k) {
			continue;
		} else {
			fm.all("propSumDefPay")[k].disabled = false;;

		}

	}



}

function changeIndemnityDuty() {
	if (fm.indemnityDuty.value == '9') {
		if (parseInt(fm.prpLcompensateIndemnityDutyRate.value) > 100) {
			alert("責任比例不能大於100%");
			return false;
		}
	} else {
		alert("責任比例不能修改!");
		return false;
	}
	return true;
}

function showManyCar() {
	window.open("common/carsDamageCount/DAACarsDamageCount.htm");
}

function showPropFirst() {
	if (fm.propSerialNo) {
		for (var index = 1; index < fm.propSerialNo.length; index++) {
			if (fm.propName[index].value == '车辆')
				fm.typetype[index].value = '1';
			else
				fm.typetype[index].value = '2';
		}
	}
}
//用来打开一个新的页面然後重新輸入无责方的信息

function inputNullInfo() {
	var registNo = fm.prpLregistExtRegistNo.value;
	var newWindow = window.open("/claim/advance.do?registNo=" + registNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
//控制按钮的可见和不可见状态

function changeAdvanceStatus(field) {
	if (field.value == '1') //全责垫付
	{
		fm.displayInputInfo.style.display = "";

		if (fm.advanceCaseStatus.value == '' || fm.advanceCaseStatus.value == '00') {
			fm.displayUpload.style.display = "";
			fm.displayGetConfirm.style.display = "none";
		} else if (fm.advanceCaseStatus.value == '05') {
			fm.displayUpload.style.display = "";
			fm.displayGetConfirm.style.display = "none";
		} else if (fm.advanceCaseStatus.value == '10') {
			fm.displayUpload.style.display = "none";
			fm.displayGetConfirm.style.display = "";
		} else {
			fm.displayUpload.style.display = "none";
			fm.displayGetConfirm.style.display = "";
			fm.displayGetConfirm.disabled = true;
		}
	} else //其他
	{
		fm.displayInputInfo.style.display = "none";
		fm.displayUpload.style.display = "none";
		fm.displayGetConfirm.style.display = "none";
	}
}
//上传事故信息,上传影像资料信息

function uploadToPlatForm(uploadType) {
	var inputObject = uploadType;
	var outputObject;
	var registNo = fm.prpLregistExtRegistNo.value;
	var comCode = fm.prpLcompensateComCode.value;
	var inputArgs = {
		uploadType1: uploadType,
		registNo1: registNo,
		comCode1: comCode
	};
	var param = DWRUtil.getValues(inputArgs);
	disabledAllButton('buttonArea');
	DWREngine.setAsync(false);
	dwrInvokeData("getPrpLagentDto", param, "rollbackPrpLagentDto", inputObject, outputObject);
	DWREngine.setAsync(true);
}

function rollbackPrpLagentDto(inputObject, outputObject, returnObject) {
	var returnInfo = returnObject;
	var uploadType = inputObject;
	if (uploadType == 'DA') {
		if (!window.confirm(returnInfo.errorMessage)) //给操作员反馈信息
		{
			fm.displayUpload.style.display = "";
			fm.displayGetConfirm.style.display = "none";
			fm.advanceCaseStatus.value = "05";
		}
	} else if (uploadType == 'D5') {
		if (returnInfo.returnType == '1') {
			fm.advanceCaseStatus.value = "10";
			fm.displayUpload.style.display = "none";
			fm.displayGetConfirm.style.display = "";
		} else {
			alert(returnInfo.errorMessage);
		}
	}
	undoDisabledButton('buttonArea');
}
//全责方获取确认信息

function getNullConfirm() {
	var inputObject;
	var outputObject;
	var registNo = fm.prpLregistExtRegistNo.value;
	var comCode = fm.prpLcompensateComCode.value;
	var inputArgs = {
		registNo1: registNo,
		comCode1: comCode
	};
	var param = DWRUtil.getValues(inputArgs);
	disabledAllButton('buttonArea');
	DWREngine.setAsync(false);
	dwrInvokeData("nullConfirmInfo", param, "rollbackGetNullConfirm", inputObject, outputObject);
	DWREngine.setAsync(true);
}

function rollbackGetNullConfirm(inputObject, outputObject, returnObject) {
	var returnInfo = returnObject;
	if (returnInfo.returnType == 1 && returnInfo.advanceResponseReturnDto.nullComments == '') {
		alert("無責方已經確認通過！")
		fm.advanceCaseStatus.value = "20";
		fm.displayUpload.style.display = "none";
		fm.displayGetConfirm.style.display = "";
		fm.displayGetConfirm.disabled = true;
	} else {
		alert(returnInfo.errorMessage);
	}
	undoDisabledButton('buttonArea');
}
//死亡伤残赔付限额 一個受害人的醫療費保險金額上限20萬，死亡傷殘保險金額上限200萬
//擁有超額賠付許可權的理賠員，可以對同一個受害人，做大於保險金額（20萬，醫療費用，不包含死殘）賠付窗體：同現有窗體樣式

function personLossLimit() {
	var message = "";

	if (fm.exceedingPayout.value == "false" || fm.exceedingPayout.value == "") {
		return message;
	}
	$.each($("select[name='prpLpersonCommerceCasualties']"), function (i, n) {
		if (i > 0) {
			var sum = 0.0;
			var PersonFeeMedical = $($("table[name='PersonFeeMedical']").get(i));
			var prpLpersonMedicalSumDefPay = PersonFeeMedical.find("input[name='prpLpersonMedicalSumDefPay']");
			$.each(prpLpersonMedicalSumDefPay, function (j, m) {
				var value = parseFloat(m.value);
				sum += value;
			});
			if (n.value == "1") {
				if (sum > wealthQuota) {
					message += "第" + i + "筆人傷信息醫療費用大於20萬\n";
				}
			} else {
				if (sum > medicalQuota) {
					message += "第" + i + "筆人傷信息死亡傷殘費用大於200萬\n";
				}
			}
		}
	});
	return message;
}
//1.	零用金撥補：

function pettyCashAppropriation() {
	var prpLpayObjectInfoOwnerShip = document.getElementsByName("prpLpayObjectInfoOwnerShip"); //C现金
	var prpLpayObjectInfoPayAmount = document.getElementsByName("prpLpayObjectInfoPayAmount"); //理赔金额
	var message = "";
	for (var i = 1; i < prpLpayObjectInfoOwnerShip.length; i++) {
		if(!jQuery.isNumeric(prpLpayObjectInfoPayAmount[i].value)){
			message += "第" + i + "筆支付對象沒有輸入賠款金額!";
			continue;
		}
		if (prpLpayObjectInfoOwnerShip[i].value == "C") {
			if (parseFloat(prpLpayObjectInfoPayAmount[i].value) > 5000) {
				message += "第" + i + "筆支付對象，現金支付不能超過5000新台蔽";
			}
		}
	}
	return message;
}