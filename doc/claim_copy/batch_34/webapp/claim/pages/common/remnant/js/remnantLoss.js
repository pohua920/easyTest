/**
 * 清空赔付对象
 * @param field
 * @return
 */
function clearPayObject(field) {
	var i = getElementOrder(field) - 1;
	var prpLchargePayObjectNameList = document.getElementsByName("prpLchargePayObjectName");
	var prpLchargePayObjectCodeList = document.getElementsByName("prpLchargePayObjectCode");
	prpLchargePayObjectNameList[i].value = "";
	prpLchargePayObjectCodeList[i].value = "";
}


/**清空当前赔付费用帳户*/

function clearPayment(field) {
	var $curr = $(field); //当前对象
	var $chargeObject = $curr.parents("table[name='chargeObject']"); //当前操作的费用资讯table
	$chargeObject.find("td[name='payFeeTD']").find("input[name^='prpLcharge']").val(""); //清空对象payFeeTD下所有input值
	$chargeObject.find("input[name='prpLchargeChargeReport']").val(0); //清空費用金額
	$chargeObject.find("input[name='prpLchargeChargeAmount']").val(0); //清空實際費用
}
//费用信息项发生改变 

function setRealPay(field) {
	if (isChange(field)) {
		var checkFlag = true; //默认通过检验
		var $chargeObject = $(field).parents("table[name='chargeObject']");
		var $chargeAmount = $chargeObject.find(":input[name='prpLchargeChargeAmount']");
		if (field.name == 'prpLchargeChargeReport') {
			$chargeAmount.val(field.value);
		} else if (field.name == 'prpLchargeChargeAmount') {
			if (parseFloat(field.value) > parseFloat($chargeObject.find(":input[name='prpLchargeChargeReport']").val())) {
				recoveryData(field); //否则恢复数据
				checkFlag = alertMessage(field, "實際費用不能大於费用金额!");
			}
		}
		var $exchRate = $chargeObject.find(":input[name='prpLchargeExchRate']");
		var $currencyAmount = $chargeObject.find(":input[name='prpLchargeCurrencyAmount']");
		var amount = parseFloat($chargeAmount.val())*parseFloat($exchRate.val());
		if($.isNumeric(amount)){
			amount = pointTwo(amount);
			$currencyAmount.val(amount);
		}else{
			$currencyAmount.val(0);
		}
	}
}
/**
 * 设置汇率
 * @param field
 * @return
 */
function setExchRateCharge(field){
	var $chargeObject = $(field).parents("table[name='chargeObject']");
	var $currency = $chargeObject.find(":input[name='prpLchargeCurrency']");
	var $exchRate = $chargeObject.find(":input[name='prpLchargeExchRate']");
	var $currencyPay = $chargeObject.find(":input[name='prpLchargeCurrencyAmount']");
	var exchRate = 1;
	if($currency.val()!=CURRENCYINFO.LOCAL_CURRENCY){
		var baseCurrency = $("#divExchToBase").find(":input[name='prpDexchBaseCurrency'][value='"+field.value+"']");
		if(baseCurrency!=undefined){
			exchRate = baseCurrency.parents("span[name='spanExchToBase']").find(":input[name='prpDexchExchRate']").val();
		}
		if(!$.isNumeric(exchRate)){
			exchRate = 1;
			alert(field.value+"對本位幣（"+CURRENCYINFO.LOCAL_CURRENCY+"）沒有配置匯率，請手工調整！");
		}
	}
	$exchRate.val(exchRate);
	var $realPay = $chargeObject.find(":input[name='prpLchargeChargeAmount']");
	var pay = pointTwo(parseFloat($realPay.val())*parseFloat($exchRate.val()));
	if($.isNumeric(pay)&&pay>=0){
		$currencyPay.val(pay);
	}else{
		$currencyPay.val(0);
	}
}
//获取支付对象

function getPayObject(field) {
	var $chargeObject = $(field).parents("table[name='chargeObject']");
	var prpLchargeChargeCode = $chargeObject.find(":input[name='prpLchargeChargeCode']").val(); //费用名称
	var prpLchargePayObjectType = $chargeObject.find(":input[name='prpLchargePayObjectType']").val(); //支付类别
	if (prpLchargeChargeCode == null || prpLchargeChargeCode == "") {
		alert("請選擇費用名稱");
		return;
	} else {
		var serialNo = $(":input[name='" + field.name + "']").index($(field));
		var ownerName = field.value;
		var url = "/claim/pages/common/account/PaymentAccountName.jsp?serialNo=" + serialNo + "&ownerName=" + ownerName;
		var handle = window.showModalDialog(url, window, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
		if (handle == null || handle == "") {
			$chargeObject.find(":input[name='prpLchargePayObjectName']").val("");
			$chargeObject.find(":input[name='prpLchargePayObjectName']").val("");
		} else {
			$chargeObject.find(":input[name='prpLchargePayObjectName']").val(handle);
			//fm.prpLchargeOwnerName[serialNo].value = handle;
			if ($chargeObject.find(":input[name='prpLchargeOwnerShip']").val() == 'B') { //汇款
				getAccountByPayObjectName(serialNo, handle);
			}
		}
	}
}
//费用获取帳户信息

function getAccountByPayObjectName(serialNo, payObjectName) {
	var submitStr = "/claim/AccountCode.do?actionType=SearchWithPayObjectName&ownerName=" + payObjectName + "&serialNo=" + serialNo;
	window.open(submitStr, '', 'resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600');
}
//修改支付对象名称时，自动修改支付对象

function setPrpLchargeOwnerName(field) {
	var $chargeObject = $(field).parents("table[name='chargeObject']");
	$chargeObject.find(":input[name='prpLchargeOwnerName']").val(field.value);
}
function queryUserNew(field) {
	var registNo = $(":input[name='prpLcompensateRegistNo']").val();
	var btnName = field.name;
	var ownerName = "";
	var actionType = "";
	var uniformNo = "";
	var certificateCode = "";
	var accountCode = "";
	var serialno = $(":input[name='" + btnName + "']").index($(field));
	if ('buttonAddPrpLpayObjectInfo' == btnName) {
		actionType = "queryUserCom";
		uniformNo = $(":input[name='prpLpayObjectInfoUniformNo']").eq(serialno).val();
		certificateCode = $(":input[name='prpLpayObjectInfoCertificateCode']").eq(serialno).val();
		accountCode= $(":input[name='prpLpayObjectInfoAccountCode']").eq(serialno).val();
	} else if ('buttonAddPrpLcharge' == btnName) {
		actionType = "queryUser";
		uniformNo = $(":input[name='prpLchargeUniformNo']").eq(serialno).val();
		certificateCode = $(":input[name='prpLchargeCertificateCode']").eq(serialno).val();
		accountCode= $(":input[name='prpLchargeAccountCode']").eq(serialno).val();
	}
	if (actionType != "") {
		var submitStr = "AccountCode.do?registNo=" + registNo + "&serialNo=" + serialno + "&actionType=" + actionType + "&certificateCode=" + certificateCode+ "&uniformNo=" + uniformNo+ "&accountCode=" + accountCode;
		window.open(submitStr, "Print", "resizable=1,scrollbars=yes,overflow=scroll,width=980,height=600");
	}
 }
function ownerShip_change(field) {
	var $ownerShip = $(field);
	var $payFeeTD = $ownerShip.parents("td[name='payFeeTD']");
	if ($ownerShip.val() == "B") { //汇款
		$payFeeTD.find("span[name='spanCutBack']").hide(); //隐藏禁背
		$payFeeTD.find("tr[name='bankInfo']").show(); //开放银行帳户录入
	} else {
		$payFeeTD.find("tr[name='bankInfo']").hide(); //关闭银行帳户录入
	}
	if ($ownerShip.val() == "Q") { //支票
		$payFeeTD.find("span[name='spanCutBack']").show(); //显示禁背
		$payFeeTD.find("tr[name='bankInfo']").hide(); //隐藏银行帳户录入
	} else {
		$payFeeTD.find("span[name='spanCutBack']").hide(); //隐藏禁背
	}
}
function viewDangerUnitCompensateCharge(field) {
	for (var i = 1; i < fm.prpLchargeSerialNo.length; i++) {
		if (fm.prpLchargeDangerNo[i] == field) {
			var count = i;
			var policyNo = fm.prpLcompensatePolicyNo.value;
			var damageDate = fm.damageStartDate.value;
			var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=CompensateCharge";
			window.open(submitStr,'查看危险单位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
		}
	}
}
/*
 * 计算实缴金额*
 * @param field
 * @return
 */
function checkNumber(field){
	if(!$.isNumeric(field.value)){
		alert("請輸入正確金額！");
		field.value = "0";
	}
	var $tr = $(field).parents("tr[name='trRemnant']");
	var $auctionAmount = $tr.find(":input[name='prpLremnantAuctionAmount']");
	var $backAmount = $tr.find(":input[name='prpLremnantBackAmount']");
	var pay = parseFloat($auctionAmount.val())+parseFloat($backAmount.val());
	var $realPay = $tr.find(":input[name='prpLremnantRealPay']");
	if($.isNumeric(pay)&&pay>=0){
		$realPay.val(pay);
	}else{
		$realPay.val(0);
	}
	pay = parseFloat($realPay.val());
	var $exchRate = $tr.find(":input[name='prpLremnantExchRate']");
	var $currencyPay = $tr.find(":input[name='prpLremnantCurrencyPay']");
	pay = pointTwo(pay*parseFloat($exchRate.val()));
	if($.isNumeric(pay)&&pay>=0){
		$currencyPay.val(pay);
	}else{
		$currencyPay.val(0);
	}
}
/**
 * 设置汇率
 * @param field
 * @return
 */
function setExchRate(field){
	var $tr = $(field).parents("tr[name='trRemnant']");
	var $currency = $tr.find(":input[name='prpLremnantCurrency']");
	var $exchRate = $tr.find(":input[name='prpLremnantExchRate']");
	var $currencyPay = $tr.find(":input[name='prpLremnantCurrencyPay']");
	var exchRate = 1;
	if($currency.val()!=CURRENCYINFO.LOCAL_CURRENCY){
		var baseCurrency = $("#divExchToBase").find(":input[name='prpDexchBaseCurrency'][value='"+field.value+"']");
		if(baseCurrency!=undefined){
			exchRate = baseCurrency.parents("span[name='spanExchToBase']").find(":input[name='prpDexchExchRate']").val();
		}
		if(!$.isNumeric(exchRate)){
			exchRate = 1;
			alert(field.value+"對本位幣（"+CURRENCYINFO.LOCAL_CURRENCY+"）沒有配置匯率，請手工調整！");
		}
	}
	$exchRate.val(exchRate);
	var $realPay = $tr.find(":input[name='prpLremnantRealPay']");
	var pay = pointTwo(parseFloat($realPay.val())*parseFloat($exchRate.val()));
	if($.isNumeric(pay)&&pay>=0){
		$currencyPay.val(pay);
	}else{
		$currencyPay.val(0);
	}
}

/**
 * 增加赔付对象
 * @return
 */
function uLprpLPayObjectinfo() {
 	var uiLi_first = '<li><input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="';
		var uiLi_list = ' 	收取金額: <input  type="text" name="payObjectPayAmount" onblur="setPayObjectPayAmount();" value="" class="common" style="width:100px"/></li>';
 	var uiLi = "";
 	$.each($.find("input[name='prpLpayObjectInfoSerialNo']"), function (i, n) {
 		if (i > 0) {
				uiLi += uiLi_first+i+'" />收取對象'+i+uiLi_list;
 		}
 	});
 	if (uiLi == "") {
 		uiLi = "沒有賠款收取對象訊息，請錄入賠款收取對象。";
 	}
 	var odiv = document.getElementById("prpLPayObjectinfo");
 	if (odiv.style.display != "none") {
 		odiv.style.display = "none";
 	}
 	var uiodiv = document.getElementById("uLprpLPayObjectinfo");
 	uiodiv.innerHTML = uiLi;
 }

var prpLfieldIndex = 0;
var prpLfieldName = 0;

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

function setPayObjectPayAmount() {
	var payObjectSerialNo = document.getElementsByName("payObjectSerialNo");
	var payObjectPayAmount = document.getElementsByName("payObjectPayAmount");

	var payObjectValue = "";
	for (var i = 0; i < payObjectSerialNo.length; i++) {
		if (payObjectSerialNo[i].checked) {
			payObjectValue += payObjectSerialNo[i].value;
			if (isNumeric(payObjectPayAmount[i].value)) {
				payObjectValue += ":" + payObjectPayAmount[i].value;
			} else {
				payObjectValue += ":0";
			}
			payObjectValue += ";";
		}
	}
	if (payObjectValue != "") {
		payObjectValue = payObjectValue.substring(0, payObjectValue.length - 1);
	}
	document.getElementsByName(prpLfieldName)[prpLfieldIndex].value = payObjectValue;
	setPrpLpayObjectInfoPayAmount();
}

function setPrpLpayObjectInfoPayAmount() {
	var prpLpayObjectInfoPayAmount = document.getElementsByName("prpLpayObjectInfoPayAmount");
	for (var i = 0; i < prpLpayObjectInfoPayAmount.length; i++) {
		prpLpayObjectInfoPayAmount[i].value = 0;
	}
	var payObjectSerialNo = document.getElementsByName("prpLremnantPayObjectSerialNo");
	for (var i = 0; i < payObjectSerialNo.length; i++) {
		if (payObjectSerialNo[i].value != "") {
			var payObjectValue = payObjectSerialNo[i].value.split(";");
			for (var j = 0; j < payObjectValue.length; j++) {
				var payObjectTemp = payObjectValue[j].split(":");
				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value = parseFloat(payObjectTemp[1]) + parseFloat(prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value);
				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].readOnly=true;
				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].className="readonly";
			}
		}

	}
	
}

function findPosX(obj) {
	var curLeft = 0;
	if (obj.offsetParent) {
		do {
			curLeft += obj.offsetLeft;
		} while (obj = obj.offsetParent);
	} else if (obj.x) {
		curLeft += obj.x;
	}
	return curLeft;
}

function findPosY(obj) {
	var curTop = 0;
	if (obj.offsetParent) {
		do {
			curTop += obj.offsetTop;
		} while (obj = obj.offsetParent);
	} else if (obj.y) {
		curTop += obj.y;
	}
	return curTop;
}

function afterInsertPayObjectInfo(insertPayObjectInfo,pageCode,pageCode_Data,btnField,csFieldName,psFieldName){
	uLprpLPayObjectinfo();
	//mantis4683 調整規則 無需錄入銀行賬戶等資料
	//收取對象默認為買受人
	var $trBuyer = $("#spanBuyer").find("tr[name='trBuyer']");
	if($trBuyer.length > 0 ){//
		var $buyer = $trBuyer.last();//默認為最新添加的那筆買受人資料
		var prplbuyerBuyerName = $buyer.find(":input[name='prplbuyerBuyerName']").val();
		var prplbuyerUniformNo = $buyer.find(":input[name='prplbuyerUniformNo']").val();
		var prplbuyerAddress = $buyer.find(":input[name='prplbuyerAddress']").val();
		var prplbuyerLinkPhone = $buyer.find(":input[name='prplbuyerLinkPhone']").val();
		var $insertPayObjectInfo = $(insertPayObjectInfo);
		$insertPayObjectInfo.find(":input[name='prpLpayObjectInfoOwnerName']").val($.trim(prplbuyerBuyerName));
		$insertPayObjectInfo.find(":input[name='prpLpayObjectInfoUniformNo']").val($.trim(prplbuyerUniformNo));
		$insertPayObjectInfo.find(":input[name='prpLpayObjectInfoCourierAddress']").val($.trim(prplbuyerAddress));
		$insertPayObjectInfo.find(":input[name='prpLpayObjectInfoBeneficiaryPhone']").val($.trim(prplbuyerLinkPhone));
		if($.trim(prplbuyerUniformNo).length > 0 ){
			var $prpLpayObjectInfoCertificateCode = $insertPayObjectInfo.find(":input[name='prpLpayObjectInfoCertificateCode']");
			if(checkIdentifyNumber(prplbuyerUniformNo, "9")){//身份證號
				$prpLpayObjectInfoCertificateCode.val("01");
			} else if(checkUniformNo(prplbuyerUniformNo)){//企業統一編號
				$prpLpayObjectInfoCertificateCode.val("02");
			} else {
				$prpLpayObjectInfoCertificateCode.val("99");
			}
		}
	}
}