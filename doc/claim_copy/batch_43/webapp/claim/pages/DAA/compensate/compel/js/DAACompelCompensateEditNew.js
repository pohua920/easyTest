/***
 * 校验肇事责任比例分配
 * @param field
 */

function checkIndemnityDuty(field) {
	var $indemnityDuty = $(":input[name='indemnityDuty']");
	var $prpLcompensateIndemnityDutyRate = $(":input[name='prpLcompensateIndemnityDutyRate']"); //本方
	var $prpLcompensateOppositeIndemnityDuty = $(":input[name='prpLcompensateOppositeIndemnityDuty']"); //对方
	var $prpLcompensateOtherIndemnityDuty = $(":input[name='prpLcompensateOtherIndemnityDuty']"); //其他
	if (field.name == 'indemnityDuty') {
		if (field.value == 1) {
			$prpLcompensateIndemnityDutyRate.val("100.0");
			$prpLcompensateOppositeIndemnityDuty.val("0.0");
		} else if (field.value == 2) { //主因
			$prpLcompensateIndemnityDutyRate.val("70.0");
			$prpLcompensateOppositeIndemnityDuty.val(point(30 - parseInt($prpLcompensateOtherIndemnityDuty.val()), 1));
		} else if (field.value == 3) { //同為肇事因素
			$prpLcompensateIndemnityDutyRate.val("50.0");
			$prpLcompensateOppositeIndemnityDuty.val(point(50 - parseInt($prpLcompensateOtherIndemnityDuty.val()), 1));
		} else if (field.value == 4) { //次因
			$prpLcompensateIndemnityDutyRate.val("30.0");
			$prpLcompensateOppositeIndemnityDuty.val(point(70 - parseInt($prpLcompensateOtherIndemnityDuty.val()), 1));
		} else if (field.value == 5) { //無責
			$prpLcompensateIndemnityDutyRate.val("0.0");
			$prpLcompensateOppositeIndemnityDuty.val(point(100 - parseInt($prpLcompensateOtherIndemnityDuty.val()), 1));
		}
	} else if (field.name == 'prpLcompensateIndemnityDutyRate') {
		var rate = parseInt(field.value);
		var other = parseInt($prpLcompensateOtherIndemnityDuty.val());
		if (rate == 100) {
			$indemnityDuty.val(1);
		} else if (rate < 100 && rate > 50) { //主因
			$indemnityDuty.val(2);
		} else if (rate == 50) { //同為肇事因素
			$indemnityDuty.val(3);
		//<!-- mantis：CLM0071 ，處理人員：BK007 蘇哲，需求單編號：CLM0071.車險理算節點修正 -->
		} else if (rate > 0 && rate < 50) { //次因
			$indemnityDuty.val(4);
		} else if (rate == 0) { //無責
			$indemnityDuty.val(5);
		} else {
			$indemnityDuty.val(6);
		}
	}
	var rate = parseInt($prpLcompensateIndemnityDutyRate.val());
	var Opposite = parseInt($prpLcompensateOppositeIndemnityDuty.val());
	if (rate + Opposite <= 100) {
		$prpLcompensateOtherIndemnityDuty.val(point(100 - rate - Opposite, 1));
	} else {
		alert("責任比例超過了100%，請重新選擇!");
		$prpLcompensateOtherIndemnityDuty.val("0.0");
	}
	//mantis： CLM0088 ，處理人員：BK007 蘇哲，需求單編號：CLM0088.本車肇責100%追償只能選擇否 -start
	if(rate == 100){
		var $replevyFlag = $(":input[name='replevyFlag']"); //追償註記
		$replevyFlag.val("0");
	}
	//mantis： CLM0088 ，處理人員：BK007 蘇哲，需求單編號：CLM0088.本車肇責100%追償只能選擇否 -end
}
/**
 * 强制险賠款計算訊息
 */

function calSumDutyPaid() {
	undisablebutton();
	var sumPay = 0; //强三的赔款总和 (目前只有 人伤 )
	var sumFeePay = 0.00; //记入赔款的费用之和
	var sumdutyPaid = 0.00;
	//人伤赔款合计
	var sumPaid = 0.00; //赔款合计
	$("#PrpLpersonLoss").find(":input[name='prpLpersonCommerceSumRealPay1']").each(function () {
		sumPaid += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
	});
	sumPay += sumPaid;
	//费用合计
	var sumFee = 0.00; //费用之和，
	$("#PrpLcharge").find(":input[name='prpLchargeChargeAmount']").each(function () {
		sumFee += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
	});
	//已预付赔款
	var sumPrePaid = parseFloat(fm.prpLcompensateSumPrePaid.value);
	fm.prpLcompensateSumDutyPaid.value = pointTwo(round(sumPay, 0), 0); //赔款合计
	fm.prpLcompensateSumNoDutyFee.value = pointTwo(round(sumFee, 0), 0); //费用合计
	fm.prpLcompensateSumPaid.value = pointTwo(round(sumPay + sumFee, 0), 0); //赔款总计
	fm.prpLcompensateSumThisPaid.value = pointTwo(round(sumPay - sumPrePaid, 0), 0); //本次赔付金额
	fm.prpLcompensateSumSelfValue.value = 0; //本案总自负额
	undisablebutton();
	setAccidentType();
}

/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {
	//显示强制险赔付汇总信息
	initRealPay(); //初始化各人伤的赔付合计
	initCommerceCasualties(); //初始化伤亡情形CheckBox
	countPersonLoss(); //初始化统计受害人 一定要在initCommerceCasualties 之后
	calFundCommerce(); //初始化统计强制险赔款汇总
	//肇責百分比，理賠金額等變化時，重新設置肇責類型
	var payFee = parseInt(fm.prpLcompensatePayFee.value);
	var errorMessage = "";
	if (payFee == -1) {
		errorMessage += "此保單保費未繳,請慎重處理！！！ \n";
	} else if (payFee == -2) {
		errorMessage += "此保單已繳未繳全,請慎重處理！！！ \n";
	}
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
	fm.defValue.value = "N";
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

/***
 * 校验人伤信息的录入
 */
//校验受害人資訊信息录入

function checkPrpLpersonLoss() {
	var checkFlag = true;
	$("#PrpLpersonLoss").find("tr[name='prpLpersonLossObject']").each(function (i, personLossObject) {
		var $prpLpersonCommercePersonName = $(personLossObject).find(":input[name='prpLpersonCommercePersonName']"); //受害人
		var $prpLpersonCommerceBirthday = $(personLossObject).find(":input[name='prpLpersonCommerceBirthday']"); //出生年份
		var $prpLpersonLossPayObjectSerialNo = $(personLossObject).find(":input[name='prpLpersonLossPayObjectSerialNo']"); //赔付对象讯息
		var $prpLpersonCommerceIdentifyNumber = $(personLossObject).find(":input[name='prpLpersonCommerceIdentifyNumber']"); //身份证号
		var prpLpersonCommerceSex = $(personLossObject).find(":input[name='prpLpersonCommerceSex']").val(); //性别
		var prpLpersonCommerceIdentityOfInjuredPerson = $(personLossObject).find(":input[name='prpLpersonCommerceIdentityOfInjuredPerson']").val(); //受害人身份
		if ($.trim($prpLpersonCommercePersonName.val()) == '') {
			checkFlag = alertMessage($prpLpersonCommercePersonName[0], "第 " + (i + 1) + " 条受害人資訊‘人員姓名’必須輸入!");
			return false; //跳出each
		} else if ($.trim($prpLpersonCommerceBirthday.val()) == '') {
			checkFlag = alertMessage($prpLpersonCommerceBirthday[0], "第 " + (i + 1) + " 条受害人資訊‘出生年份’必須輸入!");
			return false; //跳出each
		}else if ($.trim($prpLpersonCommerceIdentifyNumber.val()) == '') {
			checkFlag = alertMessage($prpLpersonLossPayObjectSerialNo[0], "第 " + (i + 1) + " 条受害人資訊‘身份證號’必須輸入!");
			return false; //跳出each
		} else if (prpLpersonCommerceIdentityOfInjuredPerson == "1" && !checkIdentifyNumber($prpLpersonCommerceIdentifyNumber.val(), prpLpersonCommerceSex)) {
			checkFlag = alertMessage($prpLpersonLossPayObjectSerialNo[0], "第 " + (i + 1) + " 条受害人資訊‘身份證號’输入不正确!");
			return false; //跳出each
		} else {
			$(personLossObject).find("tr[name='prpLpersonFeeLossObject']").each(function (j, prpLpersonFeeLossObject) {
				var $prpLpersonLossLiabDetailCode = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonMedicalDetailCode']"); //人伤费用类别代码
				var $prpLpersonLossLiabDetailName = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonMedicalDetailName']"); //人伤费用类别名称
				if ($prpLpersonLossLiabDetailCode.val() == '' || $prpLpersonLossLiabDetailName.val() == '') {
					checkFlag = alertMessage($prpLpersonLossLiabDetailCode[0], "第 " + (i + 1) + " 条受害人資訊,第 " + (j + 1) + " 条費用資訊 ‘費用類別’必須輸入!");
					return false; //跳出子each
				}
			});
			return checkFlag; //true 则continue each() false 则 break each()
		}
	});
	return checkFlag; //这个才是函数的校验结果
}

/**
 * 校验费用讯息的录入
 */

function checkPrpLcharge() {
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
		//mantis： CLM0096 ，處理人員：BK007 蘇哲，需求單編號：CLM0096.新核心-給付對象資訊郵遞區號卡控數值-start
		var oAreaCode = trim(prpLchargeAreaCodeList[i].value);
		var areaCode = trim(prpLchargeAreaCodeList[i].value).replace(/[^\d]/g,'');
		if(oAreaCode != areaCode){
			alert("第 " + (i) + " 条費用資訊‘郵遞區號’ 只能輸入數值!");
			return false; //
		} else if (trim(prpLchargeKindCodeList[i].value) == '' || trim(prpLchargeKindNameList[i].value) == '') {
		//mantis： CLM0096 ，處理人員：BK007 蘇哲，需求單編號：CLM0096.新核心-給付對象資訊郵遞區號卡控數值-end
			alert("第 " + (i) + " 条費用資訊‘險別代碼’、‘險別名稱’必須輸入!");
			return false; //
		} else if (trim(prpLchargeChargeCodeList[i].value) == '') {
			alert("第 " + (i) + " 条費用資訊‘費用名稱’必須輸入!");
			return false; //
		} else if (trim(prpLchargeOwnerNameList[i].value) == '') {
			alert("第 " + (i) + " 条費用資訊‘賠付對象’必須輸入!");
			return false; //
		} else if (trim(prpLchargeUniformNoList[i].value) == '') {
			alert("第 " + (i) + " 条費用資訊‘統一編號/身份證號’必須輸入!");
			return false; //
		} else if (prpLchargeCertificateCodeList[i].value == "01" && !checkIdentifyNumber(prpLchargeUniformNoList[i].value, "9")) {
			alert("請爲費用資訊中第  " + i + " 條錄入正確的身份證號");
			return false;
		} else if (prpLchargeCertificateCodeList[i].value == "02" && !checkUniformNo(prpLchargeUniformNoList[i].value)) {
			alert("請爲費用資訊中第  " + i + " 條錄入正確的統一編號");
			return false;
		} else if (tempOwnerShip == "B" && (trim(prpLchargeAccountCodeList[i].value) == '' || trim(prpLchargeBankCodeList[i].value) == '' || trim(prpLchargeBankNameList[i].value) == '' || trim(prpLchargeCustomBankCodeList[i].value) == '' || trim(prpLchargeCustomBankNameList[i].value) == '')) {
			alert("第 " + (i) + " 条費用支付方式爲匯款，必須輸入費用支付帳戶資訊!");
			return false;
		} else if (trim(prpLchargeAreaCodeList[i].value) == '' || trim(prpLchargeCourierAddressList[i].value) == '') {
			alert("第 " + (i) + " 条費用資訊‘郵遞區號’、‘郵遞地址’必須輸入!");
			return false; //
		}
	}
	return true;
}
/***
 * 校验赔付对象讯息
 * @returns
 */

function checkPrpLpayObjectInfo() {
	var prpLpayObjectInfoOwnerShipList = document.getElementsByName("prpLpayObjectInfoOwnerShip"); //标的损失赔款支付方式
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
	var prpLpayObjectInfoCertificateCodeList = document.getElementsByName("prpLpayObjectInfoCertificateCode") //證件類型\
	
	for (var i = 1; i < prpLpayObjectInfoOwnerShipList.length; i++) {
		var tempShip = prpLpayObjectInfoOwnerShipList[i].value;
		//mantis： CLM0096 ，處理人員：BK007 蘇哲，需求單編號：CLM0096.新核心-給付對象資訊郵遞區號卡控數值-start
		var oAreaCode = trim(prpLpayObjectInfoAreaCodeList[i].value);
		var areaCode = trim(prpLpayObjectInfoAreaCodeList[i].value).replace(/[^\d]/g,'');
		if(oAreaCode != areaCode){
			alert("賠付對象 " + i + " ‘郵遞區號’ 只能輸入數值!");
			return false; //
		} else if (trim(prpLpayObjectInfoOwnerNameList[i].value) == '') {
		//mantis： CLM0096 ，處理人員：BK007 蘇哲，需求單編號：CLM0096.新核心-給付對象資訊郵遞區號卡控數值-end
			alert("賠付對象 " + i + " ‘賠付對象’必須輸入!");
			return false; //
		} else if (trim(prpLpayObjectInfoPaymentKindList[i].value) == '') {
			alert("賠付對象 " + i + " ‘費用類型’必須輸入!");
			return false; //
		} else if (trim(prpLpayObjectInfoUniformNoList[i].value) == '') {
			var tempStr = (tempShip == 'C' ? "個人身份證號" : "統一編號/身份證號");
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
		}else if (tempShip == 'B' && (trim(prpLpayObjectInfoAccountCodeList[i].value) == '0532940006586' && trim(prpLpayObjectInfoCustomBankCodeList[i].value) == '8080532' )) {
			//0532940006586 -強制險專戶帳號 ,8080532,玉山商業銀行-城中分行
			alert("賠付對象 " + i + " 帳戶訊息不可為強制險專戶帳號!");
			return false; //
		}  else if ((tempShip == 'B' || tempShip == 'Q') && (trim(prpLpayObjectInfoAreaCodeList[i].value) == '' || trim(prpLpayObjectInfoCourierAddressList[i].value) == '')) {
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
	return true;
}
/**
 * 校验基本信息界面录入
 * @returns
 */

function checkMainPage() {
	//mantis： CLM0091 ，處理人員：BK007 蘇哲，需求單編號：CLM0091.新核心-理算文件齊全日 - start
	var $prpLcompensateFileReadyDate = $(":input[name='prpLcompensateFileReadyDate']");
	if($.trim($prpLcompensateFileReadyDate.val()) == ''){
		return alertMessage($prpLcompensateFileReadyDate[0],"文件收集齊全日必須輸入!");
	}
	//mantis： CLM0091 ，處理人員：BK007 蘇哲，需求單編號：CLM0091.新核心-理算文件齊全日 - end
	//mantis：CLM0283 ，處理人員： DP0713 ，需求單編號：理算任務的理算文件備齊日不可大於該賠案新增當日日期(PA、TA、GA) START
	var riskCodeCheck =  $("input[name='prpLcompensateRiskCode']").val();
	if(riskCodeCheck == "PA" || riskCodeCheck=="TA" || riskCodeCheck=="GA"){
		var inputDateStr = $(":input[name='prpLcompensateFileReadyDate']").val();
		var inputDate = new Date(inputDateStr.replace(/-/g, '/')); // 轉換日期格式
		inputDate.setHours(0, 0, 0, 0); // 將目標時間歸零
		var today = new Date();
		today.setHours(0, 0, 0, 0); // 將今天時間歸零

		if (inputDate > today) {
	        alert("理算文件備齊日不可大於該賠案新增當日日期，請重新選擇！");
	        return false;
	   	}
	}
	//mantis：CLM0283 ，處理人員： DP0713 ，需求單編號：理算任務的理算文件備齊日不可大於該賠案新增當日日期(PA、TA、GA) END
	var $prpLcheckPhoneNumber = $(":input[name='prpLcheckPhoneNumber']");
	if ($.trim($prpLcheckPhoneNumber.val()) == '') {
		return alertMessage($prpLcheckPhoneNumber[0], "被保險人電話必須輸入!");
	}
	var self = $(":input[name='prpLcompensateIndemnityDutyRate']").val(); //本方肇责
	var opposite = $(":input[name='prpLcompensateOppositeIndemnityDuty']").val(); //对方肇责
	var other = $(":input[name='prpLcompensateOtherIndemnityDuty']").val(); //其他肇责
	if ((parseFloat(self) + parseFloat(opposite) + parseFloat(other)) != 100) {
		alert("本車、對方車、其他肇責百分比分配不正確!");
		return false;
	}
	//mantis： CLM0088 ，處理人員：BK007 蘇哲，需求單編號：CLM0088.本車肇責100%追償只能選擇否 -start
	if(parseFloat(self) == 100 && $(":input[name='replevyFlag']").val() != "0" ){
 		alert("本車肇責百分比與追償註記不符合!")
 		return false;
 	}
	//mantis： CLM0088 ，處理人員：BK007 蘇哲，需求單編號：CLM0088.本車肇責100%追償只能選擇否 -end
	var $prpLcompensateHandlerCode = $(":input[name='prpLcompensateHandlerCode']");
 	if ($.trim($prpLcompensateHandlerCode.val()) == '') {
 		return alertMessage($prpLcompensateHandlerCode[0], "業務經辦人必須輸入!");
 	}
// 	var personLen = $(":input[name='prpLpersonMedicalDetailCode']").length;
// 	var chargeLen = $(":input[name='prpLchargeKindCode']").length;
// 	if(personLen<=1&&chargeLen<=1){
// 		alert(i18n.compensate.personClaimBook); //赔款计算书中的赔付标的，赔款费用至少有一条记录!
// 		return false;
// 	}
	return true;
}
/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	//mantis：CLM0073 ，處理人員：BK007 蘇哲，需求單編號：CLM0073.理賠系統-強制險肇責解鎖 -start
	if(fm.prpLcompensateRiskCode.value == "B01"){
		if($("#prpLcompensateAccidentType").val() == 4){
			alert("強制險賠案“肇責類型”不能選擇[４：肇責未釐清，不計次]！");
			return false;
		}
	}
	//mantis：CLM0073 ，處理人員：BK007 蘇哲，需求單編號：CLM0073.理賠系統-強制險肇責解鎖 -end

	// mantis：CLM0229，處理人員：DP0714，新核心-醫詢費用強制任意累積費用調整 -- start
	var licenseNo = fm.prpLcompensateLicenseNo.value; //牌照號碼
	var damageDate = fm.DamageStartDate.value; //出險日期
	var damageHour = fm.DamageStartHour.value; //出險小時
	var compensateNo = fm.prpLcompensateCompensateNo.value;//計算書號

    $.ajax({
    	type : 'POST',
    	url : contextRootPath + "/compensate/checkLicenceNoAndDamageStartDate.do?licenseNo=" + licenseNo
    	+ "&damageDate=" + damageDate + "&damageHour=" + damageHour + "&compensateNo=" + compensateNo,
    	async : false,
    	cache : false,
    	dataType: "json",
    	contentType: "application/json; charset=utf-8",
    	success : function(data) {
    		var sumChargeAmount = parseInt(data.sumChargeAmount);
			for (var i=0; i<fm.prpLchargeSerialNo.length; i++) {
                if (typeof(fm.prpLchargeChargeCode[i]) !== 'undefined' && fm.prpLchargeChargeCode[i] !== null) {
					var chargeCode = fm.prpLchargeChargeCode[i].value; //費用名稱
					if ('Y' == chargeCode) { //代墊費用
						// 當賠案牌照及出險日期皆一致，且輸入金額大於2000元，則跳提示訊息
						var chargeReport = fm.prpLchargeChargeReport[i].value; // 費用金額
						sumChargeAmount += parseInt(chargeReport);
						
					}
				}
			}
			if (sumChargeAmount > 2000) {
				if (!confirm("請確認是否需攤付代墊之醫詢費用")) {
					return false;
				}
			}
    	},
    	error: function (jqXHR, textStatus, errorThrown) { 
    		alert("checkLicenceNoAndDamageStartDate ajax Error:" + errorThrown); 
    	}
    });
    // mantis：CLM0229，處理人員：DP0714，新核心-醫詢費用強制任意累積費用調整 -- end
	if (saveType == "4") {
		//mantis：CLM0146，處理人員：DP0713，需求單編號：新核心-賠款給付對象於零結時檢核不可大於一人 START
		var feeCount=0;
		//實際費用  费用合计
		var sumFee = 0.00; //费用之和，
		$("#PrpLcharge").find(":input[name='prpLchargeChargeAmount']").each(function () {
			sumFee += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
			feeCount++;
		});
		if(sumFee==0 && feeCount>0){
			alert("請清空費用資訊支付對象訊息。");
			return false;
		}
		var payCount=0;
		//理赔金額
		var prpLpayObjectInfoPayAmountList = document.getElementsByName("prpLpayObjectInfoPayAmount"); 
		var sumPay = 0;//理赔金額總和
		for (var i = 1; i < prpLpayObjectInfoPayAmountList.length; i++) {
			var tempPayShip = prpLpayObjectInfoPayAmountList[i].value;
			sumPay+=isNaN(tempPayShip)?0:tempPayShip;
			payCount++;
		}
		if(sumPay==0 && payCount > 0){
			alert("請清空賠款給付對象訊息。");
			return false; 
		}
		//mantis：CLM0146，處理人員：DP0713，需求單編號：新核心-賠款給付對象於零結時檢核不可大於一人 END
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
		var prpLchargeAreaCodeList = document.getElementsByName("prpLchargeAreaCode"); //郵遞區號
		for (var i = 1; i < prpLchargeAreaCodeList.length; i++) {
			if(undefined!=prpLchargeAreaCodeList[i] && null!=prpLchargeAreaCodeList[i]){
				var oAreaCode = trim(prpLchargeAreaCodeList[i].value);
				var areaCode = trim(prpLchargeAreaCodeList[i].value).replace(/[^\d]/g,'');
				if(oAreaCode.length > 3){
					alert("第 " + (i) + " 條費用資訊‘郵遞區號’ 長度超過3位數!");
					return false; //
				} else 
				if(oAreaCode != areaCode){
					alert("第 " + (i) + " 條費用資訊‘郵遞區號’ 只能輸入數值!");
					return false; //
				}  
			}
		}
		var prpLpayObjectInfoAreaCodeList = document.getElementsByName("prpLpayObjectInfoAreaCode"); //郵遞區號
		for (var i = 1; i < prpLpayObjectInfoAreaCodeList.length; i++) {
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
		}
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
		var errorMessage = "";
		//mantis：CLM0167，處理人員：DP0713，需求單編號：新核心-強制險失能等級檢核 START
		//必須放在errorMessage下面
		$("#PrpLpersonLoss").find("tr[name='prpLpersonFeeLossObject']").each(function () {
			if($(this).find(":input[name='prpLpersonMedicalDetailCode']").val()=="C00"){
				if($(this).find("#prpLdisabilityLimitRatingCode").val()==""){
					errorMessage+="失能等級資料不可為空，請確認並填入失能等級\n";
				}
			}
		});
		//mantis：CLM0167，處理人員：DP0713，需求單編號：新核心-強制險失能等級檢核 END
		var prpLcompensateSumClaim = document.getElementsByName("prpLcompensateSumClaim"); //预估金额
		for (var i = 0; i < prpLcompensateSumClaim.length; i++) {
			if (prpLcompensateSumClaim[i].value == "") {
				errorMessage += "預估金額不允許爲空！\n";
			}
		}
		//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
		for (var i = 1; i < fm.prpLpayObjectInfoBeneficiaryPhone.length; i++) {
			var prpLpayObjectInfoBeneficiaryPhone =fm.prpLpayObjectInfoBeneficiaryPhone[i].value;
			if (prpLpayObjectInfoBeneficiaryPhone.length > 0) {
				 if (prpLpayObjectInfoBeneficiaryPhone.substr(0, 2)=='09'){
				    	reg =/^09[0-9]{8}$/;
				    	  if(!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
				    		errorMessage =errorMessage +"受款人"+i+"電話有誤\n";
				    	}
				  } else {
				      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
				      if (!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
				    	errorMessage =errorMessage +"受款人"+i+"電話有誤\n";
				      }
				}
			    		
			 }
			}
			//行動電話
			for (var i = 1; i < fm.prpLpayObjectInfoMobilePhoneNo.length; i++) {
			var prpLpayObjectInfoMobilePhoneNo =fm.prpLpayObjectInfoMobilePhoneNo[i].value;
			if (prpLpayObjectInfoMobilePhoneNo.length > 0) {
				    var reg =/^09[0-9]{8}$/;
				    if(!reg.test(prpLpayObjectInfoMobilePhoneNo)){
				    	errorMessage =errorMessage +"受款人"+i+"行動電話有誤\n";
				    }
				    		
			 }
			}
			for (var i = 1; i < fm.prpLpersonCommerceTelephoneNo.length; i++) {
				var prpLpersonCommerceTelephoneNo =fm.prpLpersonCommerceTelephoneNo[i].value;
				if (prpLpersonCommerceTelephoneNo.length > 0) {
					 if (prpLpersonCommerceTelephoneNo.substr(0, 2)=='09'){
					    	reg =/^09[0-9]{8}$/;
					    	  if(!reg.test(prpLpersonCommerceTelephoneNo)){
					    		errorMessage =errorMessage +"受害人"+i+"電話有誤\n";
					    	}
					  } else {
					      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
					      if (!reg.test(prpLpersonCommerceTelephoneNo)){
					    	errorMessage =errorMessage +"受害人"+i+"電話有誤\n";
					      }
					}
				    		
				 }
				}
			for (var i = 1; i < fm.prpLpersonCommerceMobilePhone.length; i++) {
				var prpLpersonCommerceMobilePhone =fm.prpLpersonCommerceMobilePhone[i].value;
				if (prpLpersonCommerceMobilePhone.length > 0) {
					    var reg =/^09[0-9]{8}$/;
					    if(!reg.test(prpLpersonCommerceMobilePhone)){
					    	errorMessage =errorMessage +"受害人"+i+"行動電話有誤\n";
					    }
					    		
				}  
		}//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end
		if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		}

	    //mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	    var commMsg = "請確認賠付訊息內【人傷跟蹤訊息】頁簽之受害人身分證號及乘坐牌照號碼為必填。";
	    var compulsoryBchain = false;
		if(undefined!=fm.prpLcompensateIsCompulsoryBchainClaim && "undefined"!=fm.prpLcompensateIsCompulsoryBchainClaim
	    		&& fm.prpLcompensateIsCompulsoryBchainClaim.length>0){
	    	if("Y"==fm.prpLcompensateIsCompulsoryBchainClaim[1].value 
	    		&& fm.prpLcompensateIsCompulsoryBchainClaim[1].checked == true){
	    		compulsoryBchain = true;//不做區塊鏈查詢
	    	}
		
			if(!compulsoryBchain){//false查/true不查
				for (var i = 1; i < fm.prpLpersonCommerceIdentifyNumber.length; i++) {
					if (fm.prpLpersonCommerceIdentifyNumber[i].value == '') {//受害人的 身份證號
						alert(commMsg);
						return false;
					}
				}
				for (var i = 1; i < fm.prpLpersonCommerceFamilyName.length; i++) {
					if (fm.prpLpersonCommerceFamilyName[i].value == '') {//車牌號碼(但我不懂為何叫做familyName)
						alert(commMsg);
						return false;
					}
				}
				if(!compulsoryCaseQuery310()){//3.10 query blockChain
					return false;
				}
			}
		}
		//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
		//校验人伤录入、费用录入、赔付对象录入、最终赔付额的校验、每个受害人赔付是否超限校验
		if (checkMainPage() && checkPrpLpersonLoss() && checkPrpLcharge() && checkPrpLpayObjectInfo() && sumPrpLpayObjectInfopayAmount()) {
 			fm.buttonSaveType.value = saveType;
			var payObjectInfoPaycodeType = $(":input[name='prpLpayObjectInfoPaycodeType']").val();
			if(payObjectInfoPaycodeType != 3){
				if ($.trim($(":input[name='prpLctextContextInnerHTML']").html()) == "") {
					alert("理算報告必須輸入!");
					return false;
				}
			}
			if(!checkCompeMedicalDetail()){
 				return false;
 			}
			if(!checkLimit()){
 				return false;
 			}
			if(!checkKindPay()){
 				return false;
 			}
 			fm.target = "";
			var underWriteFlag = fm.prpLcompensateUnderWriteFlag.value;
			if (saveType == 4) {
				if (!confirm("案件最終 賠款金額為：" + fm.prpLcompensateSumDutyPaid.value + " ,費用金額為：" + fm.prpLcompensateSumNoDutyFee.value + " ,請確認！")) {
					undisablebutton();
					return false;
				}
			}
			field.disabled = true;
			$(":disabled[name='prpLpersonCommerceEndCaseAndRecoverFlag']").prop("disabled",false);
			$(":disabled[name='prpLpersonCommerceChasingLossesStatus']").prop("disabled",false);
			$("input[name='buttonSave']").attr("disabled",true);
 			$("input[name='buttonSaveFinishSubmit']").attr("disabled",true);
 			$("input[name='buttonCancel']").attr("disabled",true);
 			$("input[name='buttonGiveup']").attr("disabled",true);
 			$("input[name='buttonBack']").attr("disabled",true);
			  //mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
//			fm.submit();
//			return true;
			
			var riskCode = $("input[name='prpLcompensateRiskCode']").val();
			var claimNo = $(":input[name='prpLcompensateClaimNo']").val();
 			var prpLpayObjectInfoUniformNoAry=[];
 			for (var i = 1; i < fm.prpLpayObjectInfoUniformNo.length; i++) {
 	 			//var prpLpayObjectInfoCertificateCode = fm.prpLpayObjectInfoCertificateCode[i].value; //證件類型
 	 			var prpLpayObjectInfoUniformNo = fm.prpLpayObjectInfoUniformNo[i].value; //證件代碼
 	 			prpLpayObjectInfoUniformNoAry.push(prpLpayObjectInfoUniformNo);
 			}
 			debugger;
 			var checkSubmit = false;
			$.ajax({
				type : 'POST',
				url : contextRootPath + "/compensate/checkPayuserList.do?" +
						"prpLcompensateRiskCode=" +riskCode+"&" +
						"buttonSaveType="+saveType+"&" +
						"prpLcompensateClaimNo="+claimNo+"&" +
 						"prpLpayObjectInfoUniformNo="+prpLpayObjectInfoUniformNoAry,
				async : false,
				cache : false,
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				success : function(data) {
					debugger;
					if(data.message != ''){
						alert(data.message);
					}else{
						checkSubmit = true;
					}
				},
				error: function (jqXHR, textStatus, errorThrown) { 
					alert("saveForm ajax Error:"+errorThrown); 
				}
			});
 			if(checkSubmit){
				fm.submit();
				return true;
 			}else{
				field.disabled = false;
				$(":disabled[name='prpLpersonCommerceEndCaseAndRecoverFlag']").prop("disabled",true);
				$(":disabled[name='prpLpersonCommerceChasingLossesStatus']").prop("disabled",true);
				$("input[name='buttonSave']").attr("disabled",false);
	 			$("input[name='buttonSaveFinishSubmit']").attr("disabled",false);
	 			$("input[name='buttonCancel']").attr("disabled",false);
	 			$("input[name='buttonGiveup']").attr("disabled",false);
	 			$("input[name='buttonBack']").attr("disabled",false);
				return false;
 			}
			//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
		}
		
	} else {
		var prpLcompensateHandlerCode = document.getElementsByName("prpLcompensateHandlerCode"); //费用名称
		if (prpLcompensateHandlerCode.length > 0 && prpLcompensateHandlerCode[0].value == "") {
			alert("業務經辦人必須輸入!");
			return false; //
		}
		var prpLchargeChargeCodeList = document.getElementsByName("prpLchargeChargeCode"); //费用名称
		for (var i = 1; i < prpLchargeChargeCodeList.length; i++) {
			if (trim(prpLchargeChargeCodeList[i].value) == '') {
				alert("第 " + (i) + " 条費用資訊‘費用名稱’必須輸入!");
				return false; //
			}
		}
		fm.buttonSaveType.value = saveType;
		field.disabled = true;
		$(":disabled[name='prpLpersonCommerceEndCaseAndRecoverFlag']").prop("disabled",false);
		$(":disabled[name='prpLpersonCommerceChasingLossesStatus']").prop("disabled",false);
		$("input[name='buttonSave']").attr("disabled",true);
		$("input[name='buttonSaveFinishSubmit']").attr("disabled",true);
		$("input[name='buttonCancel']").attr("disabled",true);
		$("input[name='buttonGiveup']").attr("disabled",true);
		$("input[name='buttonBack']").attr("disabled",true);
		fm.submit();
		return true;
	}
}

/**
 * 出险时保单讯息
 */

function backWardPolicy() {
	var SHOWTYPE = "SHOW";
	var BizNo = fm.prpLcompensatePolicyNo.value;
	var RiskCode = fm.prpLcompensateRiskCode.value;
	var damageDate = fm.damageDate.value;
	var vURL = '/claim/pages/common/pub/PolicyShowCenter.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + BizNo + '&RiskCode=' + RiskCode + '&damageDate=' + damageDate;
	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//离开域时的数字校验

function checkInteger(field, MinValue, MaxValue) {
	field.value = trim(field.value);
	var strValue = field.value;
	if (strValue == "") {
		strValue = "0";
	}
	var desc = field.description;
	//如果description属性不存在，则用name属性
	if (desc == null) {
		desc = field.name;
	}
	MinValue = parseInt(MinValue, 10);
	if (isNaN(MinValue)) {
		MinValue = MIN_INTEGER;
	}
	MaxValue = parseInt(MaxValue, 10);
	if (isNaN(MaxValue)) {
		MaxValue = MAX_INTEGER;
	}
	var value = parseInt(strValue, 10);
	if (isNaN(value) || value > MaxValue || value < MinValue) {
		errorMessage("请输入合法的" + desc + "\n类型为数字(integer),最小值为" + MinValue + ",最大值为" + MaxValue);
		field.focus();
		field.select();
		return false;
	}
	return true;
}

/***
 * 校驗險別賠付是否超過預估
 */
function checkKindPay(){
	var personKindArray = new Array();//人傷賠付險別
	var personKindPayArray = new Array();//人傷險別賠付金額
	$("#PrpLpersonLoss").find(":input[name='prpLpersonCommerceSumRealPay1']").each(function(){
		personKindArray.push("21");
		personKindPayArray.push(this.value);
	});
	var claimNo = $(":input[name='prpLcompensateClaimNo']").val(); //取赔案号
	var checkresult = false;
	$.ajax({
		url : contextRootPath + "/compensate/checkKindPay.do",
		type: "POST",
		dataType : "json",
		async : false,
		data : {
			claimNo : claimNo,
			personKind : personKindArray.join(","),
			personKindPay : personKindPayArray.join(",")
		},
		success : function(data){
			if(data && data.msg){
				if(confirm(data.msg + " 請確認是否繼續 ？ ")){
					checkresult = true;
				}
			} else {
				checkresult = true;
			}
		},
		error : function(){
			alert("校驗險別賠付是否超過預估出現異常！");
		}
	});
	return checkresult;
}

/***
 * 設置肇責類型
 */
function setAccidentType(){
	//mantis：CLM0073 ，處理人員：BK007 蘇哲，需求單編號：CLM0073.理賠系統-強制險肇責解鎖 -start
//	var $selectAccidentType = $("#prpLcompensateAccidentType");
	//var $inputAccidentType = $(":input[name='prpLcompensateAccidentType']");
//	var accidentType = getAccidentType();
//	if(accidentType){
//		$selectAccidentType.val(accidentType);
//		//$inputAccidentType.val(accidentType);
//	}
	//mantis：CLM0073 ，處理人員：BK007 蘇哲，需求單編號：CLM0073.理賠系統-強制險肇責解鎖 -end
}
/***
 * 根據規則獲取肇責類型
 * @returns
 */
function getAccidentType(){
	var $divPreAccidentType = $("#divPreAccidentType");
	if($divPreAccidentType.length > 0 ){//險別已有賠付，屬於二結，二結取一結之肇責類型
		var accidentType = $divPreAccidentType.find(":input[name='kindAT_21']").val();
		if(accidentType == "1" || accidentType == "2"){
			//一結之肇責類型為1或2，二結必同一結肇責類型
			return accidentType;
		}
		//一結之肇責類型為3
	}
	//初次理算，或一結肇責為3
	var propKindPay = 0;
	var indemnityDutyRate = parseFloat($("#prpLcompensateIndemnityDutyRate").val());//本車肇責百分比
	$("#PrpLpersonLoss").find(":input[name='prpLpersonMedicalSumDefPay']").each(function(){
		propKindPay += isNaN(this.value) ? 0 : parseFloat(this.value);
	});
/*
	if(propKindPay == 0){// 無賠償金額
		return "3";
	}else{//有”賠償金額”
		return indemnityDutyRate > 0 ? "1" : "2";
	}
*/
	// 20160412 
/*
	強制險無保留預估欄位，所以肇責類型為"1"有肇責，計次、"2"無肇責，不計次、"3"有肇責，不計次。
	1. 一結肇責百分比為0時，肇責為2
	2. 一結肇責百分比大於0時，肇責為1或3(有賠款時選1，無賠款時選3)
*/
	if( indemnityDutyRate == 0 ){// 無肇責
		return "2";
	}
	// 有肇責
	if( propKindPay > 0 ){// 有賠償金額
		return "1";
	}
	// 無賠償金額
	return "3";
}

//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
var pre_registTextContext ="";
function compulsoryCaseQuery310(){
	var idNumber="";
	var idNumberType="";
	for (var i = 1; i < fm.prpLpersonCommerceIdentifyNumber.length; i++) {
		if (fm.prpLpersonCommerceIdentifyNumber[i].value != '') {
			idNumber+=","+fm.prpLpersonCommerceIdentifyNumber[i].value;
			idNumberType+=","+fm.prpLpersonLossIdNumberType[i].value;
		}
	}
	//prpLpersonLossIdNumberType 受害人身分證號類別  ID_NUMBER / ARC_NUMBER / PASSPORT_NUM
    var result=true;
    var damageStartDate = undefined!=fm.prpLcompensateDamageStartDate?fm.prpLcompensateDamageStartDate.value:fm.prpLcompensateDamageStartDate.value;
    var damageStartHour = undefined!=fm.DamageStartHour?fm.DamageStartHour.value:fm.DamageStartHour.value;
    var damageStartMinute = "00";//undefined!=fm.prpLpersonDamageStartMinute?fm.prpLpersonDamageStartMinute.value:fm.prpLpersonDamageStartMinute.value;
	var jsonData= (function(){
		$.ajax({
			url:contextRootPath + "/webservice/compulsoryCaseQuery310.do?"+
			"idNumber="+idNumber+
			"&idNumberType="+idNumberType+
			"&prpLregistDamageStartDate="+damageStartDate+
			"&prpLregistDamageStartHour="+damageStartHour+
			"&prpLregistDamageStartMinute="+damageStartMinute
			,async : false,
				cache : false,
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: 
			function (data) {
				result = false;
				var success=false;
				if(data.result != null && data.result != ''){
					var rtn = data.result.split("|");
					if(rtn[0] == '1'){
						success = true;
					}
					//0181 devMode START
					if(rtn[0] == '-1'){//僅有測試區才會出現的判斷
						//避免備案(備案不擋)送出後區塊鏈異常 且在立案被送出所已清空ID讓項目被擋在立案 使得USER比須回到備案修正
//						for (var i = 1; i < fm.prpLpersonCommerceIdentifyNumber.length; i++) {
//							fm.prpLpersonCommerceIdentifyNumber[i].value = "";
//						}
						if(confirm("測試環境!區塊鏈系統異常，是否跳過區塊鏈檢驗!!")){
							success = true;
						}else{
							return false;
						}
						
					}
					//0181 devMode END
					data.result = data.result.substring(2,data.result.length);//除去前兩碼判定碼(0|...)
				}else{
					//避免備案(備案不擋)送出後區塊鏈異常 且在立案被送出所已清空ID讓項目被擋在立案 使得USER比須回到備案修正
//					for (var i = 1; i < fm.prpLpersonCommerceIdentifyNumber.length; i++) {
//						fm.prpLpersonCommerceIdentifyNumber[i].value = "";
//					}
					alert("區塊鏈系統異常，請確認系統無誤後再行提交案件。");
					return false;
				}
				if(pre_registTextContext!=""){
					//寫回
					if(null!=fm.prpLctextContextInnerHTML && undefined!=fm.prpLctextContextInnerHTML){
						fm.prpLctextContextInnerHTML.value=fm.prpLctextContextInnerHTML.value.replace(pre_registTextContext,"");
					}else if(null!=fm.prpLltextContextInnerHTML && undefined!=fm.prpLltextContextInnerHTML){
						fm.prpLltextContextInnerHTML.value=fm.prpLltextContextInnerHTML.value.replace(pre_registTextContext,"");
					}
					//清除
					pre_registTextContext = "";
				}
				pre_registTextContext = data.result!=undefined?data.result:"";
				//寫入
				if(null!=fm.prpLctextContextInnerHTML && undefined!=fm.prpLctextContextInnerHTML){
					fm.prpLctextContextInnerHTML.value=fm.prpLctextContextInnerHTML.value+(data.result!=undefined?data.result:"");
				}else if(null!=fm.prpLltextContextInnerHTML && undefined!=fm.prpLltextContextInnerHTML){
					fm.prpLltextContextInnerHTML.value=fm.prpLltextContextInnerHTML.value+(data.result!=undefined?data.result:"");
				}
				
				if (!success) {
					result = false;
					$("#prpLcompensateSharingFlag1").attr('checked', true);
					
					alert("該案件同業已受理，請至區塊鏈平台確認。");
				} else{
					result = true;//通過可繼續
					$("#prpLcompensateSharingFlag0").attr('checked', true);
				}
			}
		});
		//alert("rtn:"+result);
	    return result;
	})();
	//alert("jsonData:"+jsonData);
	return jsonData;
}

function changeDef(){
	fm.defValue.value = 'Y';
}
//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END