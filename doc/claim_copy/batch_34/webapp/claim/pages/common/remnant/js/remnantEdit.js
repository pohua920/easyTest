/*****************************************************************************
 * DESC       ：残余物处理的脚本函数页面(车险类的)
 * AUTHOR     ：中科軟
 * CREATEDATE ：2013-08-13
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 * 页面初始化时，让残余物信息页面，支付对象信息的删除和新增按钮失效。
 */

function initSet() {
	fm.buttonPayObjectInfoInsert.disabled = true;
	if (fm.buttonPayObjectInfoDelete != undefined) {
		if (fm.all("buttonPayObjectInfoDelete").length == undefined) {
			fm.buttonPayObjectInfoDelete.disabled = true;
		} else {
			for ( var index = 0; index < fm.all("buttonPayObjectInfoDelete").length; index++) {
				fm.all("buttonPayObjectInfoDelete")[index].disabled = true;
			}
		}
	}
}

function saveForm() {
	if($(":input[name='prpLremnantKindCode']").length<=1&&$(":input[name='prpLchargeKindCode']").length<=1){
		alert("殘餘物訊息和費用訊息必須有一條，請錄入!");
		return false;
	}
	if(!checkRemnant()||!checkPayObject()||!checkPrpLcharge()){
		return false;
	}
	fm.submit();
	fm.buttonSave.disabled = true;
}
/**
 * 检查残余物信息
 * @return
 */
function checkRemnant() {
	var message = "";
	$("tr[name='trRemnant']").each(function(i, n) {
		if (i > 0) {
			var $KindCode = $(n).find(":input[name='prpLremnantKindCode']");
			if ($KindCode.val() == "") {
				message += "請錄入第 "+i+" 筆殘餘物訊息之“出險險種”！\n";
			}
			var $CurrencyPay = $(n).find(":input[name='prpLremnantCurrencyPay']");
			var $PayObjectSerialNo = $(n).find(":input[name='prpLremnantPayObjectSerialNo']");
			var pay = 0;
			if($PayObjectSerialNo.val()==""){
				message += "請錄入第 "+i+" 筆殘餘物訊息之“收取對象訊息”！\n";
			}else{
				var payObjectValue = $PayObjectSerialNo.val().split(";");
				for (var i = 0; i < payObjectValue.length; i++) {
					var payObjectTemp = payObjectValue[i].split(":");
					pay += parseFloat(payObjectTemp[1]);
				}
				if(parseFloat($CurrencyPay.val())!=pay){
					message += "第 "+i+" 筆殘餘物訊息之“收取對象訊息”分配金額與本筆記錄“實際金額”不等，請修正！\n";
				}
			}
			
		}
	});
	if(message.length>0){
		alert(message);
		return false;
	}
	return true;
}

/**
 * 检查赔付对象
 * @return
 */
function checkPayObject(){
	var message = "";
	$("tr[name='trPrpLpayObjectInfo']").each(function(i,n){
		if(i>0){
			var $PayAmount = $(n).find(":input[name='prpLpayObjectInfoPayAmount']");
			if (trim($PayAmount.val()) == '') {
				message += "請錄入收取對象 " + i + " 之 “收取金額”！\n";
			}
			if (trim($(n).find(":input[name='prpLpayObjectInfoOwnerName']").val()) == '') {
				message += "請錄入收取對象 " + i + " 的“收取對象名稱”！\n";
			}
			//mantis4683 調整規則 無需錄入銀行賬戶等資料
//			var $OwnerShip = $(n).find(":input[name='prpLpayObjectInfoOwnerShip']");
//			if ($OwnerShip.val() == 'B'
//					&& (trim($(n).find(":input[name='prpLpayObjectInfoBankCode']").val()) == ''
//							|| trim($(n).find(":input[name='prpLpayObjectInfoBankName']").val()) == ''
//							|| trim($(n).find(":input[name='prpLpayObjectInfoAccountCode']").val()) == ''
//							|| trim($(n).find(":input[name='prpLpayObjectInfoCustomBankCode']").val()) == ''
//							|| trim($(n).find(":input[name='prpLpayObjectInfoCustomBankName']").val()) == '')) {
//				message += "收取對象 " + i + " 支付方式爲匯款，請錄入帳戶訊息！\n";
//			}
			if (trim($(n).find(":input[name='prpLpayObjectInfoAreaCode']").val()) == '') {
				message += "請錄入收取對象 " + i + " 的“郵遞區號”！\n";
			}
			if (trim($(n).find(":input[name='prpLpayObjectInfoCourierAddress']").val()) == '') {
				message += "請錄入收取對象 " + i + " 的“郵遞地址”！\n";
			}
			var $CertificateCode = $(n).find(":input[name='prpLpayObjectInfoCertificateCode']");
			var $UniformNo = $(n).find(":input[name='prpLpayObjectInfoUniformNo']");
			if ($CertificateCode.val() == "01" && !checkIdentifyNumber($UniformNo.val(), "9")) {
				message += "收取對象 " + i + " 身份證號錄入不正確！\n";
			}
			if ($CertificateCode.val() == "02" && !checkUniformNo($UniformNo.val())) {
				message += "收取對象 " + i + " 統一編號錄入不正確！\n";
			}
			var sumPay = 0.00;
			$(":input[name='prpLremnantPayObjectSerialNo']").each(function(j,m){
				if(j>0){
					var payObjectPayAmount = m.value.split(";");
					for ( var j = 0; j < payObjectPayAmount.length; j++) {
						var payObject = payObjectPayAmount[j].split(":");
						if(parseInt(payObject[0])==i){
							sumPay += parseFloat(payObject[1]);
						}
					}
				}
			});
			if(parseFloat($PayAmount.val())!=sumPay){
				message += "收取對象 "+ i +" 之“支付金額”與實繳金額不相等，請修正！\n";
			}
		}
	});
	if(message.length>0){
		alert(message);
		return false;
	}
	return true;
}
//校验費用資訊

function checkPrpLcharge() {
	var checkFlag = true;
	$("#Charge").find("table[name='chargeObject']").each(function (i) {
		var $prpLchargeKindCode = $(this).find(":input[name='prpLchargeKindCode']"); //险别代码
		var $prpLchargeKindName = $(this).find(":input[name='prpLchargeKindName']"); //险别名称
		var $prpLchargeChargeCode = $(this).find(":input[name='prpLchargeChargeCode']"); //费用名称
		var $prpLchargeChargeName = $(this).find(":input[name='prpLchargeChargeName']");
		var $prpLchargePayObjectName = $(this).find(":input[name='prpLchargeKindCode']"); //支付對象名稱
		var $prpLchargeOwnerName = $(this).find(":input[name='prpLchargeOwnerName']"); //賠付對象
		var $prpLchargeUniformNo = $(this).find(":input[name='prpLchargeUniformNo']"); //ID/統一編號
		var $prpLchargeOwnerShip = $(this).find(":input[name='prpLchargeOwnerShip']"); //費用支付方式
		var $prpLchargeBankCode = $(this).find(":input[name='prpLchargeBankCode']"); //總行代號
		var $prpLchargeBankName = $(this).find(":input[name='prpLchargeBankName']"); //總行名稱
		var $prpLchargeAccountCode = $(this).find(":input[name='prpLchargeAccountCode']"); //匯款帳號
		var $prpLchargeCustomBankCode = $(this).find(":input[name='prpLchargeCustomBankCode']"); //分行代號
		var $prpLchargeCustomBankName = $(this).find(":input[name='prpLchargeCustomBankName']"); //分行名稱
		var $prpLchargeAreaCode = $(this).find(":input[name='prpLchargeAreaCode']"); //郵遞區號
		var $prpLchargeCourierAddress = $(this).find(":input[name='prpLchargeCourierAddress']"); //郵遞地址
		var $prpLchargeFeeSerialNo = $(this).find(":input[name='prpLchargeFeeSerialNo']"); //殘餘物序號
		var $prpLchargeCertificateCode = $(this).find(":input[name='prpLchargeCertificateCode']"); //證件類型
        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
        if(undefined!=$prpLchargeAreaCode && null!=$prpLchargeAreaCode){
	        var oAreaCode2 = $.trim($prpLchargeAreaCode.val());
	        var areaCode2 = $.trim($prpLchargeAreaCode.val()).replace(/[^\d]/g,'');
	        if(oAreaCode2.length > 3){
	        	alert("第 " + (i + 1) + " 条費用資訊‘郵遞區號’ 長度超過3位數!");
	        	checkFlag = false;
	        	return false; //跳出each
	        } else
	        if(oAreaCode2 != areaCode2){
	        	alert("第 " + (i + 1) + " 条費用資訊‘郵遞區號’ 只能輸入數值!");
	        	checkFlag = false;
	        	return false; //跳出each
	        } 
        }
        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
		if ($prpLchargeKindCode.val() == '' || $prpLchargeKindName.val() == '') {
			checkFlag = alertMessage($prpLchargeKindCode[0], "第 " + (i + 1) + " 条費用資訊‘險別代碼’、‘險別名稱’必須輸入!");
			return false; //跳出each
		} else if ($prpLchargeChargeCode.val() == '') {
			checkFlag = alertMessage($prpLchargeChargeCode[0], "第 " + (i + 1) + " 条費用資訊‘費用名稱’必須輸入!");
			return false; //跳出each
		} else if ($.trim($prpLchargePayObjectName.val()) == '' || $.trim($prpLchargeOwnerName.val()) == '') {
			checkFlag = alertMessage($prpLchargeOwnerName[0], "第 " + (i + 1) + " 条費用資訊‘支付對象名稱’必須輸入!");
			return false; //跳出each
		}else if($prpLchargeCertificateCode.val()=="01"&& !checkIdentifyNumber($prpLchargeUniformNo.val(), "9")){
			checkFlag = alertMessage($prpLchargeUniformNo[0], "第 " + (i + 1) + " 条費用資訊‘統一編號/身份證號’必須輸入正確的身份證號碼!");
			return false; //跳出each
		}else if ($prpLchargeCertificateCode.val()=="02"&& !checkUniformNo($prpLchargeUniformNo.val())) {
			checkFlag = alertMessage($prpLchargeUniformNo[0], "第 " + (i + 1) + " 条費用資訊‘統一編號/身份證號’必須輸入正確的統一編號!");
			return false; //跳出each
		} else if ($prpLchargeOwnerShip.val() == 'B' && ($.trim($prpLchargeAccountCode.val()) == '' || $.trim($prpLchargeBankCode.val()) == '' || $.trim($prpLchargeBankName.val()) == '' || $.trim($prpLchargeCustomBankCode.val()) == '' || $.trim($prpLchargeCustomBankCode.val()) == '')) {
			alert("第 " + (i + 1) + " 条費用支付方式爲匯款，必須輸入費用支付帳戶資訊!");
			checkFlag = false;
			return false; //跳出each
		} else if ($.trim($prpLchargeAreaCode.val()) == '' || $.trim($prpLchargeCourierAddress.val()) == '') {
			alert("第 " + (i + 1) + " 条費用資訊‘郵遞區號’、‘郵遞地址’必須輸入!");
			checkFlag = false;
			return false; //跳出each
		}else if($prpLchargeFeeSerialNo.val()!=""){
			if(parseInt($prpLchargeFeeSerialNo.val())>$(":input[name='prpLremnantKindCode']").length){
				alert("第 " + (i + 1) + " 条費用資訊‘殘餘物序號’ 必須是存在的存在的殘餘物!");
				checkFlag = false;
				return false; //跳出each
			}
		}
	});
	return checkFlag;
}
function undwrt() {
	fm.action = "/claim/remnantSave.do?editType=undwrt";
	fm.submit();
	fm.buttonUndwrt.disabled = true;
}

function withdrawal() {
	fm.action = "/claim/remnantSave.do?editType=withdrawal";
	fm.submit();
	fm.buttonWithdrawal.disabled = true;
}
/** 駁回修改 下發 */
function submitJunior(){
	fm.action = "/claim/audit/submitJunior.do?auditType=Replevy&editType=UNDWRT&businessNo="+fm.prpLcompensateCompensateNo.value;
	fm.submit();
	$("form:button").attr("disabled",true);
}
/** 提交上級 */
function submitSuperior(){
	fm.action = "/claim/audit/submitSuperior.do?auditType=Replevy&editType=UNDWRT&businessNo="+fm.prpLcompensateCompensateNo.value;
	fm.submit();
	$("form:button").attr("disabled",true);
}

function setPrpObjectinfoSerialNo(field) {
	var odiv = document.getElementById("prpLPayObjectinfo");
	prpLfieldIndex = getElementOrder(field, document.forms[0]) - 1;
	prpLfieldName = field.name;
	var payObjectSerialNo = document.getElementsByName("payObjectSerialNo");
	var payObjectPayAmount = document.getElementsByName("payObjectPayAmount");
	for (var i = 0; i < payObjectSerialNo.length; i++) {
		payObjectSerialNo[i].checked = false;
		payObjectPayAmount[i].value = "";
	}
	if (field.value != "") {
		var payObjectValue = field.value.split(";");
		for (var i = 0; i < payObjectValue.length; i++) {
			var payObjectTemp = payObjectValue[i].split(":");
			if (payObjectSerialNo.length >= parseInt(payObjectTemp[0])) {
				payObjectSerialNo[parseInt(payObjectTemp[0]) - 1].checked = true;
				payObjectPayAmount[parseInt(payObjectTemp[0]) - 1].value = payObjectTemp[1];
			}
		}
	}
	odiv.style.left = findPosX(field) - 303;
	odiv.style.top = findPosY(field) - 5;
	odiv.style.height = payObjectSerialNo.length == 0 ? 1 * 10 : payObjectSerialNo.length * 10;
	odiv.style.display = "block";
}