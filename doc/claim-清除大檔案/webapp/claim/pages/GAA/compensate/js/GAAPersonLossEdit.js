/*****************************************************************************
 * DESC       ：人员列表增加JS
 * AUTHOR     : 中科軟
 * CREATEDATE ： 2004-08-01
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/

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
//查看历史赔付人员讯息

function showPersonHistory() {
	var claimNo = $(":input[name='prpLcompensateClaimNo']").val();
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
	insertRow('person',undefined,'prpLpersonLossSerialNo');
	var $prpLpersonLossObject = $("tr[name='prpLpersonLossObject']:last");
	$prpLpersonLossObject.find(":input[name='prpLpersonLossPersonName']").val(prpLpersonLoss.personName);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossSex']").val(prpLpersonLoss.sex);
//	$prpLpersonLossObject.find(":input[name='prpLpersonLossFamilyName']").val(prpLpersonLoss.familyName);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossBirthday']").val(prpLpersonLoss.birthday);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossBirthday_show_format_rcDate']").val(prpLpersonLoss.birthday_show_format_rcDate);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossAge']").val(prpLpersonLoss.age);
//	$prpLpersonLossObject.find(":input[name='prpLpersonLossIdentityOfInjuredPerson']").val(prpLpersonLoss.identityOfInjuredPerson);
//	$prpLpersonLossObject.find(":input[name='prpLpersonLossRideSituation']").val(prpLpersonLoss.rideSituation);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossIdentifyNumber']").val(prpLpersonLoss.identifyNumber);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossMedicalCode']").val(prpLpersonLoss.medicalCode);
//	$prpLpersonLossObject.find(":input[name='prpLpersonLossEndCaseAndRecoverFlag']").val(prpLpersonLoss.endCaseAndRecoverFlag);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossTelephoneNo']").val(prpLpersonLoss.telephoneNo);
//	$prpLpersonLossObject.find(":input[name='prpLpersonLossProsecutorsOffice']").val(prpLpersonLoss.prosecutorsOffice);
//	$prpLpersonLossObject.find(":input[name='prpLpersonLossCourtDoctor']").val(prpLpersonLoss.courtDoctor);
//	$prpLpersonLossObject.find(":input[name='prpLpersonLossMobilePhone']").val(prpLpersonLoss.mobilePhone);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossProsecutor']").val(prpLpersonLoss.prosecutor);
//	$prpLpersonLossObject.find(":input[name='prpLpersonLossGarageHeadName']").val(prpLpersonLoss.garageHeadName);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossHospitalCode']").val(prpLpersonLoss.hospitalCode);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossHospitalName']").val(prpLpersonLoss.hospitalName);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossDoctor']").val(prpLpersonLoss.doctor);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossArrangeRate']").val(prpLpersonLoss.arrangeRate);
	$prpLpersonLossObject.find(":input[name='prpLpersonLossIndemnityDutyRate']").val(prpLpersonLoss.indemnityDutyRate);
	return true;
}
/**
 * 合计人伤金额
 * @param field
 * @return
 */
function calPersonLoss(field){
	$tr = $(field).parents("tr[name='prpLpersonFeeLossObject']");
	var $sumDefPay = $tr.find(":input[name='prpLpersonLossSumDefPay']");//核定赔偿
	var $sumRest = $tr.find(":input[name='prpLpersonLossSumRest']");//自負額
	var $sumRealPay = $tr.find(":input[name='prpLpersonLossSumRealPay']");//賠付金額
	var $exchRate = $tr.find(":input[name='prpLpersonLossExchRate']");//匯率
	var $currencySumPay = $tr.find(":input[name='prpLpersonLossCurrencySumPay']");//賠付金額NTD
	var $sumLoss = $tr.find(":input[name='prpLpersonLossSumLoss']");//损失
	var $claimRate = $tr.find(":input[name='prpLpersonLossClaimRate']");//损失比例
	sumRealPay = $sumDefPay.val()-$sumRest.val();
	sumRealPay = sumRealPay * $claimRate.val()/100;
	if($.isNumeric(sumRealPay)){
		$sumLoss.val($sumDefPay.val());
		$sumRealPay.val(sumRealPay);
		var currencySumPay = sumRealPay*$exchRate.val();
		if($.isNumeric(currencySumPay)){
			$currencySumPay.val(pointTwo(currencySumPay));
		}
	}
	$table = $(field).parents("tr[name='prpLpersonLossObject']");
	var sumRealPay1 = 0;
	$table.find(":input[name='prpLpersonLossCurrencySumPay']").each(function(){
		if($.isNumeric(this.value)){
			sumRealPay1 = sumRealPay1 + parseFloat(this.value);
		}
	});
	var $sumRealPay1 = $table.find(":input[name='prpLpersonLossSumRealPay1']");//賠付金額合計
	$sumRealPay1.val(sumRealPay1);
	calFund();
}
//校验受害人資訊信息录入
function checkPrpLpersonLoss() {
	var checkFlag = true;
	$("#Person").find("tr[name='personObject']").each(function (i, personLossObject) {
		var $prpLpersonLossPersonName = $(personLossObject).find(":input[name='prpLpersonLossPersonName']"); //受害人
		var $prpLpersonLossBirthday = $(personLossObject).find(":input[name='prpLpersonLossBirthday']"); //出生年份
		var $prpLpersonLossIdentifyNumber = $(personLossObject).find(":input[name='prpLpersonLossIdentifyNumber']"); //身份证号
		var prpLpersonLossSex = $(personLossObject).find(":input[name='prpLpersonLossSex']").val(); //性别
//		var prpLpersonLossIdentityOfInjuredPerson = $(personLossObject).find(":input[name='prpLpersonLossIdentityOfInjuredPerson']").val(); //受害人身份
		if ($.trim($prpLpersonLossPersonName.val()) == '') {
			checkFlag = alertMessage($prpLpersonLossPersonName[0], "第 " + (i + 1) + " 条受害人資訊‘人員姓名’必須輸入!");
			return false; //跳出each
		} else if ($.trim($prpLpersonLossBirthday.val()) == '') {
			checkFlag = alertMessage($prpLpersonLossBirthday[0], "第 " + (i + 1) + " 条受害人資訊‘出生年份’必須輸入!");
			return false; //跳出each
		} else if ($.trim($prpLpersonLossIdentifyNumber.val()) == '') {
			checkFlag = alertMessage($prpLpersonLossIdentifyNumber[0], "第 " + (i + 1) + " 条受害人資訊‘身份證號’必須輸入!");
			return false; //跳出each
		} else if (!checkIdentifyNumber($prpLpersonLossIdentifyNumber.val(), prpLpersonLossSex)) {
			checkFlag = alertMessage($prpLpersonLossIdentifyNumber[0], "第 " + (i + 1) + " 条受害人資訊‘身份證號’输入不正确!");
			return false; //跳出each
		} else {
			$(personLossObject).find("tr[name='prpLpersonLossObject']").each(function (j, prpLpersonFeeLossObject) {
				var $prpLpersonLossPayObjectSerialNo = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonLossPayObjectSerialNo']"); //赔付对象讯息
				var $prpLpersonLossKindCode = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonLossKindCode']"); //险别代码
				var $prpLpersonLossKindName = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonLossKindName']"); //险别名称
				var $prpLpersonLossLiabDetailCode = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonLossLiabDetailCode']"); //人伤费用类别代码
				var $prpLpersonLossLiabDetailName = $(prpLpersonFeeLossObject).find(":input[name='prpLpersonLossLiabDetailName']"); //人伤费用类别名称
				if ($.trim($prpLpersonLossKindCode.val()) == '' || $.trim($prpLpersonLossKindName.val()) == '') {
					checkFlag = alertMessage($prpLpersonLossKindCode[0], "第 " + (i + 1) + " 条受害人資訊‘險別’必須輸入!");
					return false; //跳出each
				} else if ($prpLpersonLossLiabDetailCode.val() == '' || $prpLpersonLossLiabDetailName.val() == '') {
					checkFlag = alertMessage($prpLpersonLossLiabDetailCode[0], "第 " + (i + 1) + " 条受害人資訊,第 " + (j + 1) + " 条費用資訊 ‘費用類別’必須輸入!");
					return false; //跳出子each
				}
//				else if ($.trim($prpLpersonLossPayObjectSerialNo.val()) == '') {
//					checkFlag = alertMessage($prpLpersonLossPayObjectSerialNo[0], "第 " + (i + 1) + " 条受害人資訊,第 " + (j + 1) + " 条費用資訊‘賠付對象讯息’必須輸入!");
//					return false; //跳出each
//				}
			});
			return checkFlag; //true 则continue each() false 则 break each()
		}
	});
	return checkFlag; //这个才是函数的校验结果
}
//校验赔付对象
function checkPrpLpayObjectInfo() {
	var checkFlag = true;
	$("#PayAccountInfo").find("tr[name='PrpLpayObjectInfo']").each(function (i) {
		var $prpLpayObjectInfoOwnerName = $(this).find(":input[name='prpLpayObjectInfoOwnerName']"); //賠付對象
		var $prpLpayObjectInfoPaymentKind = $(this).find(":input[name='prpLpayObjectInfoPaymentKind']"); //費用類型
		var $prpLpayObjectInfoUniformNo = $(this).find(":input[name='prpLpayObjectInfoUniformNo']"); //ID/統一編號
		var $prpLpayObjectInfoOwnerShip = $(this).find(":input[name='prpLpayObjectInfoOwnerShip']"); //費用支付方式

		var $prpLpayObjectInfoBeneficiaryPhone = $(this).find(":input[name='prpLpayObjectInfoBeneficiaryPhone']"); //受款人電話
		var $prpLpayObjectInfoBankCode = $(this).find(":input[name='prpLpayObjectInfoBankCode']"); //總行代號
		var $prpLpayObjectInfoBankName = $(this).find(":input[name='prpLpayObjectInfoBankName']"); //總行名稱
		var $prpLpayObjectInfoAccountCode = $(this).find(":input[name='prpLpayObjectInfoAccountCode']"); //匯款帳號
		var $prpLpayObjectInfoCustomBankCode = $(this).find(":input[name='prpLpayObjectInfoCustomBankCode']"); //分行代號
		var $prpLpayObjectInfoCustomBankName = $(this).find(":input[name='prpLpayObjectInfoCustomBankName']"); //分行名稱
		var $prpLpayObjectInfoAreaCode = $(this).find(":input[name='prpLpayObjectInfoAreaCode']"); //郵遞區號
		var $prpLpayObjectInfoCourierAddress = $(this).find(":input[name='prpLpayObjectInfoCourierAddress']"); //郵遞地址
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
		if(undefined!=$prpLpayObjectInfoAreaCode && null!=$prpLpayObjectInfoAreaCode && undefined!=$prpLpayObjectInfoAreaCode.value){
			var oAreaCode = trim($prpLpayObjectInfoAreaCode.value);
			var areaCode = trim($prpLpayObjectInfoAreaCode.value).replace(/[^\d]/g,'');
			if(oAreaCode.length > 3){
				alert("賠付對象 " + (i + 1)+ " ‘郵遞區號’ 長度超過3位數!");
				return false; //
			} else 
			if(oAreaCode != areaCode){
				alert("賠付對象 " + (i + 1)+ " ‘郵遞區號’ 只能輸入數值!");
				return false; //
			}  
		}
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
		if ($.trim($prpLpayObjectInfoOwnerName.val()) == '') {
			checkFlag = alertMessage($prpLpayObjectInfoOwnerName[0], "賠付對象 " + (i + 1) + " ‘賠付對象’必須輸入!");
			return false; //跳出each
		} else if ($prpLpayObjectInfoPaymentKind.val() == '') {
			alert("賠付對象 " + (i + 1) + " ‘費用類型’必須輸入!");
			checkFlag = false;
			return false; //跳出each
		} else if ($prpLpayObjectInfoUniformNo.val() == '') {
			alert("賠付對象 " + (i + 1) + " ‘統一編號/身份證號’必須輸入!");
			checkFlag = false;
			return false; //跳出each
		} else if ($prpLpayObjectInfoBeneficiaryPhone.val() == '') {
			alert("賠付對象 " + (i + 1) + " ‘受款人電話’必須輸入!");
			checkFlag = false;
			return false; //跳出each
		} else if ($prpLpayObjectInfoOwnerShip.val() == 'B' && ($.trim($prpLpayObjectInfoBankCode.val()) == '' || $.trim($prpLpayObjectInfoBankName.val()) == '' || $.trim($prpLpayObjectInfoAccountCode.val()) == '' || $.trim($prpLpayObjectInfoCustomBankCode.val()) == '' || $.trim($prpLpayObjectInfoCustomBankName.val()) == '')) {
			alert("賠付對象 " + (i + 1) + " 費用支付方式爲匯款，必須輸入支付帳戶資訊!");
			checkFlag = false;
			return false; //跳出each
		} else if ($.trim($prpLpayObjectInfoAreaCode.val()) == '' || $.trim($prpLpayObjectInfoCourierAddress.val()) == '') {
			alert("賠付對象 " + (i + 1) + " ‘郵遞區號’、‘郵遞地址’必須輸入!");
			checkFlag = false;
			return false; //跳出each
		}
	});
	//验证prpLlossPayObjectSerialNo支付对象是否录入
	//prpLpersonLossPayObjectSerialNo 支付对象的信息是否存在
	var $prpLpayObjectInfoPayAmount = $.find(":input[name='prpLpayObjectInfoPayAmount']"); //賠付對象
	var serialNo = $prpLpayObjectInfoPayAmount.length;
	var $prpLlossPayObjectSerialNo = $.find(":input[name='prpLlossDtoPayObjectSerialNo']"); //賠付序号
	var $prpLlossDtoSumRealPayNTD = $.find(":input[name='prpLlossDtoSumRealPayNTD']"); //賠付金额
	//去掉预陪的金额
	var prpLcompensateSumPrePaid = $.find(":input[name='prpLcompensateSumPrePaid']")[0].value;
	if (null != prpLcompensateSumPrePaid && "" != prpLcompensateSumPrePaid) {
		prpLcompensateSumPrePaid = parseFloat(prpLcompensateSumPrePaid);
	} else {
		prpLcompensateSumPrePaid = 0;
	}
	var message = "";
	var $payAmount = $("body");
	$("#lLoss").find("tr[name='prpLlossObject']").each(function(i,e){
		var sumRealPay = $(e).find(":input[name='prpLlossDtoSumRealPay']").val();
		var serialNo = $(e).find(":input[name='prpLlossDtoPayObjectSerialNo']").val();
		var currency = $(e).find(":input[name='prpLlossDtoCurrency']").val();
		if (serialNo.value != "") {
			if(!setPay(serialNo,sumRealPay,prpLcompensateSumPrePaid,$payAmount)){
				message += "第" + (i + 1) + "筆賠付標的訊息，賠付對象的金額與賠償金額不等，請重新輸入!\n";
			}else if(!chekcCurrency(serialNo,currency)){
				message += "第" + (i + 1) + "筆賠付標的訊息，賠付對象的支付幣別與賠償金額幣別不同，請重新輸入!\n";
			}
		}
	})
	$("#Person").find("tr[name='personObject']").each(function(i,e){
		var sumRealPay1NTD = $(e).find(":input[name='prpLpersonLossSumRealPay1NTD']").val();
		var sum = 0;
		$(e).find("tr[name='prpLpersonLossObject']").each(function(j,f){
			var sumRealPay = $(f).find(":input[name='prpLpersonLossSumRealPay']").val();
			var sumRealPayNTD = $(f).find(":input[name='prpLpersonLossSumRealPayNTD']").val();
			var serialNo = $(f).find(":input[name='prpLpersonLossPayObjectSerialNo']").val();
			var currency = $(e).find(":input[name='prpLpersonLossCurrency']").val();
			if(!setPay(serialNo,sumRealPay,prpLcompensateSumPrePaid,$payAmount)){
				message += "第" + (i + 1) + "筆受害人讯息，第" + (j + 1) + "筆費用訊息，賠付對象的金額與賠償金額不等，請重新輸入!\n";
			}else if(!chekcCurrency(serialNo,currency)){
				message += "第" + (i + 1) + "筆受害人讯息，第" + (j + 1) + "筆費用訊息，賠付對象的支付幣別與賠償幣別不同，請重新輸入!\n";
			}
			sum += parseFloat(sumRealPayNTD);
		});
		if (parseFloat(sumRealPay1NTD) != sum) {
			message += "第" + (i + 1) + "筆受害人讯息的賠付金額合計與其各分項賠付金額（NTD）之和不等!\n";
		}
	});
	$.each($prpLpayObjectInfoPayAmount, function (i, n) {
		if (i > 0) {
			if(jQuery.isNumeric(n.value)){
				if (jQuery.data($payAmount, (i + "")) != parseFloat(n.value)) {
					message += "第" + i + "筆賠付對象訊息理賠金額與計算書個賠付對象訊息的賠付之和不等,請重新輸入!\n";
				}
			} else {
				message += "第" + i + "筆賠付對象訊息沒有輸入理賠金額\n";
			}
		}
	});
	if (message.length > 0) {
		alert(message);
		checkFlag = false;
		return false;
	}
	return checkFlag;
}

/***
 * 比较每个赔付对象序号的金额合计与当前项的赔付金额NTD比较，并缓存
 * @param serialNo
 * @param sumRealPay
 * @param sumPrePaid
 * @param $payAmount
 */
function setPay(serialNo,sumRealPay,sumPrePaid,$payAmount){
	var sum = 0;
	if(serialNo!=""){
		var serialNoArray = serialNo.split(";");
		for (var i = 0; i < serialNoArray.length; i++) {
			var str = serialNoArray[i].split(":");
			var payObjectSerialNo = str[0];
			var payObjectAmount = parseFloat(str[1]);
			sum += payObjectAmount;
			var d = jQuery.data($payAmount, payObjectSerialNo);
			if(d != undefined){
				jQuery.data($payAmount,payObjectSerialNo,d + payObjectAmount);
			}else{
				jQuery.data($payAmount,payObjectSerialNo,payObjectAmount);
			}
		}
	}
	if(sum != parseFloat(sumRealPay) && ((sum + sumPrePaid) != parseFloat(sumRealPay))){
		return false;
	}
	return true;
}
/***
 * 检查赔付金额币别和赔付对象币别是否相等。
 * @param serialNo
 * @param sumRealPay
 * @param sumPrePaid
 * @param $payAmount
 */
function chekcCurrency(serialNo,currency){
	if(serialNo!=""){
		var serialNoArray = serialNo.split(";");
		var $prpLpayObjectInfoCurrency = $(":input[name='prpLpayObjectInfoCurrency']");
		for (var i = 0; i < serialNoArray.length; i++) {
			var str = serialNoArray[i].split(":");
			var payObjectSerialNo = parseInt(str[0]);
			if(currency!=$prpLpayObjectInfoCurrency.get(payObjectSerialNo).value){
				return false;
			}
		}
	}
	return true;
}
/**
 * 检查费用
 * @return
 */
function checkPrpLcharge(){
	var message = "";
	var checkFlag = true;
	var $prpLchargeCertificateCode = $(":input[name='prpLchargeCertificateCode']"); //證件類型
	var $prpLchargeChargeCode = $(":input[name='prpLchargeChargeCode']");
	var $prpLchargeFeeSerialNo = $(":input[name='prpLchargeFeeSerialNo']");//代扣税序号
	var $prpLchargeChargeAmount = $(":input[name='prpLchargeChargeAmount']");//实际费用金额
	var $prpLchargeChargeReport = $(":input[name='prpLchargeChargeReport']");//費用金額
	var $prpLchargeCurrency = $(":input[name='prpLchargeCurrency']");//实际费用金额
	$(":input[name='prpLchargeUniformNo']").each(function(i,n){
		if(i>0){
			if ($prpLchargeCertificateCode[i].value == "01" && !checkIdentifyNumber(n.value, "9")) {
				message += "請爲費用資訊第  " + i + " 條錄入正確的身份證號\n";
				return false;
			}
			if ($prpLchargeCertificateCode[i].value == "02" && !checkUniformNo(n.value)) {
				message += "請爲費用資訊第  " + i + " 條錄入正確的統一編號\n";
				return false;
			}
			if($prpLchargeFeeSerialNo[i].value!=""){
				var feeSerialNo = parseInt($prpLchargeFeeSerialNo[i].value);
				if(!$.isNumeric(feeSerialNo)||feeSerialNo>$prpLchargeFeeSerialNo.length||feeSerialNo == i){
					message += "請爲費用資訊第  " + i + " 條錄入正確的代扣費用序號\n";
					return false;
				}
				if($prpLchargeCurrency[i].value != $prpLchargeCurrency[feeSerialNo].value){
					message += "請爲費用資訊第  " + i + " 條錄入幣別必須和第 "+feeSerialNo+" 條錄入的幣別相同\n";
					return false;
				}
				if(parseFloat($prpLchargeChargeAmount[i].value)!= parseFloat($prpLchargeChargeReport[feeSerialNo].value)-parseFloat($prpLchargeChargeAmount[feeSerialNo].value)){
					message += "請爲費用資訊第  " + i + " 條錄入實際費用金額必須和第 "+feeSerialNo+" 條錄入的費用金額和實際費用金額之差相等\n";
					return false;
				}
			}
			if($prpLchargeChargeCode[i].value=="B"){
				//B代扣稅，代扣費用序號為必輸項
				if($prpLchargeFeeSerialNo[i].value==""){
					message += "請爲費用資訊第  " + i + " 條錄入代扣費用序號\n";
					return false;
				}
			}
			
		}
	});
	if (message.length > 0) {
		alert(message);
		checkFlag = false;
		return false;
	}
	return checkFlag;
}
//理算校验金额录入 最好在失去焦点时校验
function validateMoney(field){
   if($.trim(field.value)==""||isNaN(field.value)){
      recoveryData(field);
      return alertMessage(field,$(field).attr("title")+"必須是正確的金額輸入!");
   }
   return true;
}
