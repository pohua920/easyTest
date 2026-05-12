/*****************************************************************************
 * DESC       ：追偿的脚本函数页面(车险类的)
 * AUTHOR     ：中科軟
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/***
 * 給付追償情況為費用時，預估追償金額不得錄入
 * 切換給付追償情況的值，會將當前的預估追償訊息備份緩存，當選項切換非費用時，重新賦值
 */
function changePaySituation(field){
	var $Replevy = $("#Replevy");
	var $tbody = $Replevy.children("tbody");
	var $tempReplevyData = $tbody.children();
	var $times = $(":input[name='prpLreplevyTimes']");
	var times = 0;
	if($(field).val() == "4"){//分次追償
		$("tr[name='trPaySituationTimes']").show();
	}else{
		$("tr[name='trPaySituationTimes']").hide();
	}
	if ($(field).val() == "7") {// 費用不得錄入預估追償訊息
		$Replevy.data("bak", $tempReplevyData);
		$tbody.empty();
		times = parseInt($times.val()) - 1
		$times.data("times", times).val(times);
	} else {
		var $bak = $Replevy.data("bak");
		if ($tempReplevyData.length == 0 && $bak != undefined) {
			$tbody.append($bak);
		}
		var times = $times.data("times");
		if (times != undefined) {
			$times.removeData("times").val(times + 1);;
		}
	}
}

function initPayNTD(){
    var sumRealPay = 0;
    var exchRate = 0;
    $("#Replevy").find("tr[name='prpLlossObject']").each(function(){
        sumRealPay = parseFloat($(this).find(":input[name='prpLlossSumRealPay']").val());
        exchRate = parseFloat($(this).find(":input[name='prpLlossDtoExchRate']").val());
        $(this).find(":input[name='prpLlossDtoSumRealPayNTD']").val(Math.round(sumRealPay*exchRate));
    });
}

function initButton() {
	if (fm.buttonClaimLossDelete != undefined) {
		if (fm.all("buttonClaimLossDelete").length == undefined) {
			fm.buttonClaimLossDelete.disabled = true;
		} else {
			for (var i = 0; i < fm.all("buttonClaimLossDelete").length; i++) {
				fm.all("buttonClaimLossDelete")[i].disabled = true;
			}
		}
	}
	if (fm.buttonChargeDelete != undefined) {
		if (fm.all("buttonChargeDelete").length == undefined) {
			fm.buttonChargeDelete.disabled = true;
		} else {
			for (var index = 0; index < fm.all("buttonChargeDelete").length; index++) {
				fm.all("buttonChargeDelete")[index].disabled = true;
			}
		}
	}
	fm.buttonDriverInsert.disabled = true;
	//差异化
	fm.buttonPayPersonInfoInsert.disabled = true;
	if (fm.buttonPayPersonInfoDelete != undefined) {
		if (fm.all("buttonPayPersonInfoDelete").length == undefined) {
			fm.buttonPayPersonInfoDelete.disabled = true;
		} else {
			for (var index = 0; index < fm.all("buttonPayPersonInfoDelete").length; index++) {
				fm.all("buttonPayPersonInfoDelete")[index].disabled = true;
			}
		}
	}
}
function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}
function saveForm() {
	var $currCompelPayType = $(":input[name='prpLcompensateCompelPayType']");
	if(fm.riskCode.value==fm.outerCode.value && $currCompelPayType.val()==""){
		alert("請您錄入賠付類別！");
		return false;
	}
	var $repleviedName = $(":input[name='prpLreplevyRepleviedName']");
	if($.trim($repleviedName.val()).length==0){
		alertMessage($repleviedName[0],"請錄入被追償人名稱！");
		return false;
	}
	if($.trim($(":input[name='paySituation']").val()).length==0){
		alert("請錄入給付追償情況！");
		return false;
	}
	var editType = fm.editType.value;
	var compensateNo = fm.prpLcompensateCompensateNo.value;
	//追偿登录、追偿登录修改、追偿登录驳回修改
	var addFlag = (editType=="addQuery"||editType=="editQuery"||(compensateNo.length>2 && compensateNo.substring(compensateNo.length-2)=="00"));
	if(checkPrpLloss()){
		if(addFlag){
			fm.buttonSave.disabled = true;
			fm.submit();
		}else if(checkPrpLcharge() && checkPrpLpayObjectInfo() && checkPayAmount()){
			var sumRealPaySum = 0;
			$("#Replevy").find(":input[name='prpLlossDtoSumRealPayNTD']").each(function(){
				sumRealPaySum +=parseFloat(this.value);
			});
			var chargeAmountSum = 0;
			$("#Charge").find(":input[name='prpLchargeChargeAmount']").each(function(){
				chargeAmountSum +=parseFloat(this.value);
			});
			if(confirm("案件最終  追償收入為：" + Math.round(sumRealPaySum) + "（NTD），" + " 追償費用為：" + Math.round(chargeAmountSum) + "（NTD）。" + "請確認！")){
				fm.buttonSave.disabled = true;
				fm.submit();
			}
		}
	}
}

/***
 * 理赔金校验
 */
function checkPayAmount(){
    var sumThisPaid = parseFloat($(":input[name='SumThisPaid']").val());
    var sumPayAmount = 0;
    $("#PrpLpayObject").find("tr[name='PrpLpayObjectInfo']").each(function(){
    	var payAmount = $(this).find(":input[name='prpLpayObjectInfoPayAmount']").val();
    	var exchRate = $(this).find(":input[name='prpLpayObjectInfoExchRate']").val();
        sumPayAmount += parseFloat(payAmount)*parseFloat(exchRate);
    });
    if(Math.abs(sumThisPaid-sumPayAmount)>1){
        alert("追償對象的追償金額之和與本次追償收入不等。");
        return false;
    }
    return true;
}

/***
 * 校驗預估險別
 */
function checkPrpLloss(){
	var prpLlossObjects = $("#Replevy").find("tr[name='prpLlossObject']");
	var paySituation = $(":input[name='paySituation']").val();
	if(prpLlossObjects.length==0 && paySituation != "7"){//費用不能錄預估追償訊息
		alert("請錄入至少一筆預估追償訊息！");
		return false;
	}
	var flag = true;
	prpLlossObjects.each(function(i){
		var kindCode = $(this).find(":input[name='prpLlossKindCode']").val();
		var kindName = $(this).find(":input[name='prpLlossKindName']").val();
		var sumLoss = parseFloat($(this).find(":input[name='prpLlossSumLoss']").val());
		if($.trim(kindCode).length==0 || $.trim(kindName).length==0){
			alert("第 " + (i + 1) + "筆預估追償訊息沒有錄入險別！");
			flag = false;
			return false;
		}
		if(sumLoss==0){
			alert("險別'"+kindCode+kindName+"'法務預估金額為0，\r\n請錄入法務預估金額或者刪除該險別！");
			flag = false;
			return false;
		}
	});
	return flag;
}

/***
 * 校驗收取對象訊息
 * @returns {Boolean}
 */
function checkPrpLpayObjectInfo(){
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
	var prpLpayObjectInfoCertificateCodeList = document.getElementsByName("prpLpayObjectInfoCertificateCode"); //證件類型
	if(prpLpayObjectInfoOwnerShipList.length <= 1){
		alert("請至少錄入一個追償對象訊息！");
		return false;
	}
	for (var i = 1; i < prpLpayObjectInfoOwnerShipList.length; i++) {
		if (trim(prpLpayObjectInfoOwnerNameList[i].value) == '') {
			alert("追償對象 " + i + " ‘收取對象’必須輸入！");
			return false; //跳出each
		}
		if ((trim(fm.prpLpayObjectInfoUniformNo[i].value) != '' && prpLpayObjectInfoCertificateCodeList[i].value == "01") && !checkIdentifyNumber(fm.prpLpayObjectInfoUniformNo[i].value, '9')) {
			alert("請爲 追償對象" + i + " 錄入正確的身份證號！");
			return false;
		} else if ((trim(fm.prpLpayObjectInfoUniformNo[i].value) != '' && prpLpayObjectInfoCertificateCodeList[i].value == "02") && !checkUniformNo(fm.prpLpayObjectInfoUniformNo[i].value)) {
			alert("請爲 追償對象" + i + " 錄入正確的統一編號！");
			return false;
		}
	}
	return true;
}

/**
 * 校验费用讯息的录入
 */
function checkPrpLcharge() {
	var prpLchargeKindCodeList = document.getElementsByName("prpLchargeKindCode"); //险别代码
	var prpLchargeKindNameList = document.getElementsByName("prpLchargeKindName"); //险别名称
	var prpLchargeChargeCodeList = document.getElementsByName("prpLchargeChargeCode"); //费用名称
	var prpLchargeChargeAmountList = document.getElementsByName("prpLchargeChargeAmount"); //實際費用
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
	var paySituation = $(":input[name='paySituation']").val();
	if(paySituation == "7" && prpLchargeOwnerNameList.length <=1){//
		alert("給付追償情況為‘費用’，請錄入至少一筆費用資訊！");
		return false;
	}
	for (var i = 1; i < prpLchargeOwnerNameList.length; i++) {
		var tempOwnerShip = prpLchargeOwnerShipList[i].value;
        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
		if(undefined!=prpLchargeAreaCodeList[i] && null!=prpLchargeAreaCodeList[i]){
	        var oAreaCode2 = trim(prpLchargeAreaCodeList[i].value);
	        var areaCode2 = trim(prpLchargeAreaCodeList[i].value).replace(/[^\d]/g,'');
	        if(oAreaCode2.length > 3){
	        	alert("第 " + (i) + " 条費用資訊‘郵遞區號’ 長度超過3位數!");
	        	return false; //跳出each
	        } else
	        if(oAreaCode2 != areaCode2){
	        	alert("第 " + (i) + " 条費用資訊‘郵遞區號’ 只能輸入數值!");
	        	return false; //跳出each
	        } 
		}
        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
		if (trim(prpLchargeKindCodeList[i].value) == '' || trim(prpLchargeKindNameList[i].value) == '') {
			alert("第 " + (i) + " 筆費用資訊‘險別代碼’、‘險別名稱’必須輸入！");
			return false; //
		} else if (trim(prpLchargeChargeCodeList[i].value) == '') {
			alert("第 " + (i) + " 筆費用資訊‘費用名稱’必須輸入！");
			return false; //
		} else if (trim(prpLchargeOwnerNameList[i].value) == '') {
			alert("第 " + (i) + " 筆費用資訊‘賠付對象’必須輸入！");
			return false; //
		} else if (trim(prpLchargeUniformNoList[i].value) == '') {
			alert("第 " + (i) + " 筆費用資訊‘統一編號/身份證號’必須輸入！");
			return false; //
		} else if (prpLchargeCertificateCodeList[i].value == "01"
				&& !checkIdentifyNumber(prpLchargeUniformNoList[i].value, "9")) {
			alert("請爲第  " + i + " 筆費用資訊錄入正確的身份證號！");
			return false;
		} else if (prpLchargeCertificateCodeList[i].value == "02"
				&& !checkUniformNo(prpLchargeUniformNoList[i].value)) {
			alert("請爲第  " + i + " 筆費用資訊錄入正確的統一編號！");
			return false;
		} else if (tempOwnerShip == "B"
				&& parseFloat(prpLchargeChargeAmountList[i].value) > 0
				&& (trim(prpLchargeAccountCodeList[i].value) == ''
						|| trim(prpLchargeBankCodeList[i].value) == ''
						|| trim(prpLchargeBankNameList[i].value) == ''
						|| trim(prpLchargeCustomBankCodeList[i].value) == '' || trim(prpLchargeCustomBankNameList[i].value) == '')) {
			// 追償 费用负项时候不输入账号信息。
			alert("第 " + (i) + " 筆費用資訊‘費用支付方式’爲‘匯款’，必須輸入費用支付帳戶資訊!");
			return false;
		} else if (trim(prpLchargeAreaCodeList[i].value) == ''
				|| trim(prpLchargeCourierAddressList[i].value) == '') {
			alert("第 " + (i) + " 筆費用資訊‘郵遞區號’、‘郵遞地址’必須輸入!");
			return false; //
		}	
	}
	return true;
}


//重新计算页面金额
function calSumPaidAll() {
	var SumThisPaid = 0.00;
	var SumPaidAll = parseFloat(fm.OldSumPaidAll.value);
	$("#Replevy").find(":input[name='prpLlossDtoSumRealPayNTD']").each(function(){
		SumThisPaid +=parseFloat(this.value);
	});
	fm.SumThisPaid.value = Math.round(SumThisPaid);
	fm.prpLcompensateSumDutyPaid.value = Math.round(SumThisPaid);
	fm.SumPaidAll.value = Math.round(SumPaidAll+SumThisPaid);
}
//重新计算页面金额
function calSumFeeAll() {
	var SumThisCharge = 0.00;
	$("#Charge").find(":input[name='prpLchargeChargeAmount']").each(function(){
		SumThisCharge +=parseFloat(this.value);
	});
	var SumFeeAll = parseFloat(fm.OldSumFeeAll.value);
	fm.SumThisCharge.value = Math.round(SumThisCharge);
	fm.SumFeeAll.value = Math.round(SumFeeAll + SumThisCharge);
}
function undwrt() {
	fm.action = "/claim/replevySave.do?editType=UNDWRT";
	fm.submit();
	$("form:button").attr("disabled",true);
}

function withdrawal() {
	fm.action = "/claim/replevySave.do?editType=WITHDRAWAL";
	fm.submit();
	$("form:button").attr("disabled",true);
}
/** 提交上級 */
function submitSuperior(){
	fm.action = "/claim/audit/submitSuperior.do?auditType=Replevy&editType=UNDWRT&businessNo="+fm.prpLcompensateCompensateNo.value;
	fm.submit();
	$("form:button").attr("disabled",true);
}
/** 駁回修改 下發 */
function submitJunior(){
	fm.action = "/claim/audit/submitJunior.do?auditType=Replevy&editType=UNDWRT&businessNo="+fm.prpLcompensateCompensateNo.value;
	fm.submit();
	$("form:button").attr("disabled",true);
}
//显示打印窗口

function print() {
	return false;
	if (fm.compensateNo.value.length < 1) {
		alert("请输入查询条件!");
		return false;
	}
	var pageWidth = screen.availWidth - 10;
	var pageHeight = screen.availHeight - 30;
	if (pageWidth < 100)
		pageWidth = 100;

	if (pageHeight < 100)
		pageHeight = 100;

	var strURL = "/claim/replevyBeforeQuery.do?editType=PRINT&compensateNo=" + fm.compensateNo.value;

	var newWindow = window.open(strURL, '追偿计算书列印', 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');

	newWindow.focus();
	return newWindow;
}

/***
 * 删除或情况追偿收入讯息后，重新计算本条记录涉及的收取对象的金额
 * @param $prpLlossObject 追偿收入讯息的Jquery对象
 */
function calPayAmount($prpLlossObject) {
	var serialNoStr = $prpLlossObject.find(":input[name='prpLlossPayObjectSerialNo']").val(); //賠付對象
	if($.trim(serialNoStr)!=""){
		var serialNoStrArray = serialNoStr.split(";");
		var $payAmount = $("#PrpLpayObject").find("input[name='prpLpayObjectInfoPayAmount']");
		for(var i = 0;i < serialNoStrArray.length; i++){
			var temp = serialNoStrArray[i].split(":");
			if(parseFloat(temp[1]) != 0){
				var f = $payAmount.get(parseInt(temp[0])-1);
				f.value = Math.round(parseFloat(f.value) - parseFloat(temp[1]));
			}
		}
	}
}

var lastField = null;
function setPrpObjectinfoSerialNo(field) {
	var $serial = $("#PrpLpayObject").find(":input[name='prpLpayObjectInfoSerialNo']");
	if($serial.length > 0){//存在收取對象
		lastField = field;
		var serialNoStr = field.value.split(";");
		var serialNo = new Array();
		var amount = new Array();
		for(var i = 0;i < serialNoStr.length; i++){
			var temp = serialNoStr[i].split(":");
			serialNo[i] = temp[0];
			amount[i] = temp[1];
		}
		var strLi = "";
		$serial.each(function(){
			var index = jQuery.inArray(this.value,serialNo);
			var check = index==-1?"":"checked=\"checked\"";
			var payAmount = index==-1?"":amount[index];
			strLi += "<li>";
			strLi += "<input type=\"checkbox\" onclick=\"setPayObjectPayAmount(this);\" name=\"payObjectSerialNo\" "+check+" value=\""+this.value+"\"/>";
			strLi += "追償對象"+this.value+"&nbsp;&nbsp;";
			strLi += "追償金額:<input type=\"text\" onblur=\"setPayObjectPayAmount(this);\" name=\"payObjectPayAmount\"value=\""+payAmount+"\" class=\"input\" style=\"width:100px\"/>";
			strLi += "</li>";
		});
		$("#uLprpLPayObjectinfo").html(strLi);
		showDiv(field,$("#divPayObjectinfo"));
	}
}

/***
 * 1.设置赔付收取對象訊息；
 * 2.更新追偿对象的金额
 */
function setPayObjectPayAmount(field) {
	var serialNoArray = new Array();
	var amountArray = new Array();
	var serialNoStr = $(lastField).val();
	if($.trim(serialNoStr)!=""){
		var serialNoStrArray = serialNoStr.split(";")
		for(var i = 0;i < serialNoStrArray.length; i++){
			var temp = serialNoStrArray[i].split(":");
			serialNoArray[i] = temp[0];
			amountArray[i] = temp[1];
		}
	}
	serialNoStr = "";
	var $prpLlossObject = $(lastField).parents("tr[name='prpLlossObject']");
	var currency = $prpLlossObject.find(":input[name='prpLlossCurrency']").val();
	var $prpLpayObjectInfoAccountCurrency = $(":input[name='prpLpayObjectInfoAccountCurrency']");
	$("#uLprpLPayObjectinfo").find(":checkbox[name='payObjectSerialNo']").each(function(){
		var $amount = $(this).next(":input[name='payObjectPayAmount']");
		if(this.checked&&currency!=$prpLpayObjectInfoAccountCurrency.get(this.value).value){
			alert("賠償幣別和賠付對象的支付幣別不同，不容許選擇");
			this.checked = false;
		}
		if(this.checked){
			serialNoStr += this.value;
			var payAmount = getFormatValueByCurrency(parseFloat($.trim($amount.val())),currency);
			if(!isNaN(payAmount)){
				serialNoStr += ":"+payAmount;
				$amount.val(payAmount);
			}else{
				serialNoStr += ":0";
				$amount.val("0");
			}
			serialNoStr += ";";
		}
		//计算金额
		var index = jQuery.inArray(this.value,serialNoArray);//原对象是否存在
		if(index==-1){//原来未选中
			if(this.checked){//本次选中的
				serialNoArray.push(this.value);
				amountArray.push($amount.val());
			}
		}else{//原来已选中
			if(this.checked){//本次计算金额偏移
				amountArray[index] = parseFloat($amount.val())-parseFloat(amountArray[index]);
			}else{
				amountArray[index] = parseFloat(amountArray[index])*-1;
			}
		}
	});
	if (serialNoStr != "") {//设置赔付对象序号
		serialNoStr = serialNoStr.substring(0, serialNoStr.length - 1);
	}
	if(lastField != null){
		$(lastField).val(serialNoStr);
	}
	var $payAmount = $("#PrpLpayObject").find(":input[name='prpLpayObjectInfoPayAmount']");
	var $AccountCurrency = $("#PrpLpayObject").find(":input[name='prpLpayObjectInfoAccountCurrency']");
	var serialNo = 0;
	for(var i = 0;i<serialNoArray.length;i++){
		if(parseFloat(amountArray[i])!=0){//偏移量 不为0
			serialNo = parseInt(serialNoArray[i])-1;
			var f = $payAmount.get(serialNo);
			f.value = getFormatValueByCurrency(parseFloat(f.value)+parseFloat(amountArray[i]),$AccountCurrency.get(serialNo).value);
		}
	}
}

function getPayObject(field) {
	var fieldName = field.name;
	var fieldNameList = document.getElementsByName(fieldName);
	var chargeCodeList = document.getElementsByName("prpLchargeChargeCode");
	var prpLchargePayObjectType = document.getElementsByName("prpLchargePayObjectType");
	var chargeCode;
	var index;
	var payObjectType;
	for (var i = 0; i < fieldNameList.length; i++) {
		if (fieldNameList[i] == field) {
			index = i;
			break;
		}
	}
		chargeCode = chargeCodeList[index].value;
		payObjectType = prpLchargePayObjectType[index].value;
		// 根据费用类型和支付类型的不同，要求带出外部机构或手工輸入支付对象 begin
		if ( payObjectType == "A") { //手工輸入支付对象
			var serialNo = getElementOrder(field) - 1;
			var url = "/claim/pages/common/account/PaymentAccountName.jsp?serialNo=" + serialNo;
			var handle = window.showModalDialog(url, window, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
			if (handle == null || handle == "") {
				fm.prpLchargePayObjectName[serialNo].value = ""
			} else {
				getAccountByPayObjectName(field, handle);
			}
		} else { //带出外部机构
			code_CodeSelect(field, 'getPayObject', '-1,0', 'Y', 'N', chargeCode + "|" + payObjectType);
			//getExternAlagency(field, index);
		}
		// 根据费用类型和支付类型的不同，要求带出外部机构或手工輸入支付对象 end
}
//修改费用代码是清空支付对象 begin

function clearPayObject(field) {
	var i = getElementOrder(field) - 1;
	var prpLchargePayObjectNameList = document.getElementsByName("prpLchargePayObjectName");
	var prpLchargePayObjectCodeList = document.getElementsByName("prpLchargePayObjectCode");
	prpLchargePayObjectNameList[i].value = "";
	prpLchargePayObjectCodeList[i].value = "";
}
function getAccountByPayObjectName(field, payObjectName) {
	var order = getElementOrder(field) - 1;
	var submitStr = "AccountCode.do?actionType=SearchWithPayObjectName&ownerName=" + payObjectName + "&serialNo=" + order;
	window.open(submitStr, '', 'resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600');
}

//mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -start
//肇事责任比例
function checkIndemnityDuty(field) {
	var $indemnityDutyRate = $(":input[name='prpLcompensateIndemnityDutyRate']"); //本車肇責百分比
	var $oppositeIndemnityDuty = $(":input[name='prpLcompensateOppositeIndemnityDuty']"); //對方車肇責百分比
	var $otherIndemnityDuty = $(":input[name='prpLcompensateOtherIndemnityDuty']"); //其他肇責百分比
	var rate = parseInt($indemnityDutyRate.val());
	var opposite = parseInt($oppositeIndemnityDuty.val());
	var other = parseInt($otherIndemnityDuty.val());
	if (field.name == 'prpLcompensateIndemnityDutyRate') {
		if((100 - rate - other) <=0){
			opposite = 0;
			other = 100 - rate;
		}else if((100 - rate - other) >0){
			opposite = 100 - rate - other;
		}
	}else if (field.name == 'prpLcompensateOppositeIndemnityDuty') {
		if ((100 - rate - opposite) <= 0) {
			opposite = 100 - rate;
			other = 0;
		} else if ((100 - rate - opposite) > 0) {
			other = 100 - rate - opposite;
		}
	} else {
		if ((100 - rate - other) <= 0) {
			opposite = 0;
			other = 100 - rate;
		} else if ((100 - rate - other) > 0) {
			opposite = 100 - rate - other;
		}
	}
	
	$indemnityDutyRate.val(rate + ".0"); //下拉框中的值是double类型，必须做如此转换才能赋值=。=
	$oppositeIndemnityDuty.val(opposite + ".0"); //
	$otherIndemnityDuty.val(other + ".0"); //
}
//mantis：CLM0076 ，處理人員：BK007  蘇哲，需求單編號：CLM0076 強制險新核心-賠款代號(肇責分攤 -end