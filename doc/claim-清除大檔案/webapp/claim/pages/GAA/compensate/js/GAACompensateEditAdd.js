/**
 * 赔款支付方式改变时调用下JS
 */

function payObjectOwnerShipChange(field) {
	var $PrpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
	if ($(field).val() == "B") { //汇款
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo1']").show(); //显示统一编号
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo2']").hide(); //隐藏个人身份证号
		$PrpLpayObjectInfo.find("span[name='spanCutBack']").hide(); //隐藏禁背项
		$PrpLpayObjectInfo.find("tr[name='bankInfo']").show(); //显示银行支付账户讯息
		$PrpLpayObjectInfo.find("tr[name='AreaInfo']").show(); //显示邮递信息
		$PrpLpayObjectInfo.find("tr[name='PayDate']").hide(); //隐藏 现金支付需要的其他信息
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone1']").show(); //显示收款人电话
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone2']").hide(); //隐藏市内电话
		$PrpLpayObjectInfo.find("img[name='BeneficiaryPhoneIMG']").show(); //显示收款人电话必录的图片
	} else if ($(field).val() == "Q") { //支票
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo1']").show(); //显示统一编号
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo2']").hide(); //隐藏个人身份证号
		$PrpLpayObjectInfo.find("span[name='spanCutBack']").show(); //显示禁背项
		$PrpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //隐藏银行支付账户讯息
		$PrpLpayObjectInfo.find("tr[name='AreaInfo']").show(); //显示邮递信息
		$PrpLpayObjectInfo.find("tr[name='PayDate']").hide(); //隐藏 现金支付需要的其他信息
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone1']").show(); //显示收款人电话
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone2']").hide(); //隐藏市内电话
		$PrpLpayObjectInfo.find("img[name='BeneficiaryPhoneIMG']").show(); //显示收款人电话必录的图片
	} else if ($(field).val() == "C") { //现金
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo1']").hide(); //隐藏统一编号
		$PrpLpayObjectInfo.find("span[name='InfoUniformNo2']").show(); //显示个人身份证号
		$PrpLpayObjectInfo.find("span[name='spanCutBack']").hide(); //隐藏禁背项
		$PrpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //隐藏银行支付账户讯息
		$PrpLpayObjectInfo.find("tr[name='AreaInfo']").hide(); //隐藏邮递信息
		$PrpLpayObjectInfo.find("tr[name='PayDate']").show(); //显示现金支付需要的其他信息
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone1']").hide(); //隐藏收款人电话
		$PrpLpayObjectInfo.find("span[name='BeneficiaryPhone2']").show(); //显示市内电话
		$PrpLpayObjectInfo.find("img[name='BeneficiaryPhoneIMG']").hide(); //隐藏收款人电话必录的图片
		//现金支付 默认收款人为个人（赔付代号为健保局的情况不用设置）
		if ($(":input[name='prpLpayObjectInfoPaycodeType']").val() != "3") {
			$PrpLpayObjectInfo.find(":input[name='prpLpayObjectInfoPaymentKind']").val("4");
		}
	}
}
/***
 * 改变费用代码时的，现金支付的费用类型改为个人时，切换统一编号为个人身份证号
 */

function changePaymentKind(field) {
	var $prpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
	var ownerShip = $prpLpayObjectInfo.find(":input[name='prpLpayObjectInfoPaymentKind']").val();
	if (ownerShip == "C" && field.value == "4") {
		$prpLpayObjectInfo.find("span[name='InfoUniformNo1']").hide(); //隐藏统一编号
		$prpLpayObjectInfo.find("span[name='InfoUniformNo2']").show(); //显示个人身份证号
	} else {
		$prpLpayObjectInfo.find("span[name='InfoUniformNo1']").show(); //显示统一编号
		$prpLpayObjectInfo.find("span[name='InfoUniformNo2']").hide(); //隐藏个人身份证号
	}
}
/**
 * 賠付代號改变时
 * 如果賠付代號(賠案)值為3，費用代碼類型自動設為 6（費用類型：健保局）
 */

function setPrpLpayObjectInfoPaycodeType(field) {
	if (field.value == "3") { //健保局
		$("#PayAccountInfo").find("tr[name='PrpLpayObjectInfo']").each(function () {
			//赔案代号为健保局时，设置所有的費用代碼類为健保局
			var $paymentKind = $(this).find(":input[name='prpLpayObjectInfoPaymentKind']");
			var ownerShip = $(this).find(":input[name='prpLpayObjectInfoOwnerShip']").val();
			if (ownerShip == "C" && $paymentKind.val() == "4") { //该操作使得现金支付的费用类型由个人改为健保局时
				$(this).find("span[name='InfoUniformNo1']").show(); //显示统一编号
				$(this).find("span[name='InfoUniformNo2']").hide(); //隐藏个人身份证号
			}
			$paymentKind.val("6")
		});
	}
}

/***
 * 费用支付方式发生改变时
 */

function chargeOwnerShipChange(field) {
	var $chargeObject = $(field).parents("table[name='chargeObject']")
	if (field.value == "B") { //汇款
		$chargeObject.find("span[name='spanCutBack']").hide(); //隐藏禁背
		$chargeObject.find("tr[name='bankInfo']").show(); //显示支付账户信息
	} else if (field.value == "Q") { //支票
		$chargeObject.find("span[name='spanCutBack']").show();
		$chargeObject.find("tr[name='bankInfo']").hide();
	}
}

/**
 * 坐标
 * @param obj
 * @returns {Number}
 */
function findPosX(obj){
    var curLeft = 0;
    if(obj.offsetParent){
      do{
        curLeft += obj.offsetLeft;
      }while(obj = obj.offsetParent);
    }else if(obj.x){
      curLeft += obj.x;
    }
    return curLeft;
  }
function findPosY(obj){
   var curTop = 0;
   if (obj.offsetParent){
    do{
      curTop += obj.offsetTop;
    }while(obj = obj.offsetParent);
  }else if(obj.y){
    curTop += obj.y;
  }
  return curTop;
}

function deletePrpLpayObjectInfo(field){//删除一个支付对象
    var $prpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
    var index = 0;
    $prpLpayObjectInfo.nextAll("tr[name='PrpLpayObjectInfo']").each(function(){
        var $serialNo = $(this).find("input[name='prpLpayObjectInfoSerialNo']");
        if(index ==0){
        	index = parseInt($serialNo.val())-1;
        }
        $(this).find("span[name='payObjectIndex']").html($serialNo.val()-1);
        $serialNo.val($serialNo.val()-1);
    });
    $prpLpayObjectInfo.remove();
    if(index==0){
    	index = $.find("input[name='prpLpayObjectInfoSerialNo']").length;
    }
    $.each($.find("input[name='prpLlossDtoPayObjectSerialNo']"),function(i,n){
    	if(i>0&&n.value!=""){
    		var payObjectValue = n.value.split(";");
			var payObjectValueTemp = "";
			for(var i=0;i<payObjectValue.length;i++){
				var payObjectTemp = payObjectValue[i].split(":");
				if(index<parseInt(payObjectTemp[0])){
					payObjectValueTemp += (parseInt(payObjectTemp[0])-1)+":"+payObjectTemp[1]+";";
				}else if(index>parseInt(payObjectTemp[0])){
					payObjectValueTemp += payObjectValue[i]+";";
				}
			}
			if(payObjectValueTemp!=""){
				payObjectValueTemp = payObjectValueTemp.substring(0, payObjectValueTemp.length-1);
			}
			n.value = payObjectValueTemp;
    	}
	});
	uLprpLPayObjectinfo();
 }

/**
 * 多个賠付對象讯息 
 */
var prpLfieldIndex = 0;
var prpLfieldName = 0;
function setPrpObjectinfoSerialNo(field){
	var odiv = document.getElementById("prpLPayObjectinfo");
	prpLfieldIndex = getElementOrder(field,document.forms[0])-1;
	prpLfieldName = field.name;
	var payObjectSerialNo = document.getElementsByName("payObjectSerialNo");
	var payObjectPayAmount = document.getElementsByName("payObjectPayAmount");
	for(var i=0;i<payObjectSerialNo.length;i++){
		payObjectSerialNo[i].checked = false;
		payObjectPayAmount[i].value = "";
	}
	if(field.value!=""){
		var payObjectValue = field.value.split(";");
		for(var i=0;i<payObjectValue.length;i++){
			var payObjectTemp = payObjectValue[i].split(":");
			if(payObjectSerialNo.length>=parseInt(payObjectTemp[0])){
				payObjectSerialNo[parseInt(payObjectTemp[0])-1].checked=true;
				payObjectPayAmount[parseInt(payObjectTemp[0])-1].value=payObjectTemp[1];
			}
		}
	}
	odiv.style.left = findPosX(field) - 3-300;
	odiv.style.top = findPosY(field) - 5;
	odiv.style.height = payObjectSerialNo.length==0?1*10:payObjectSerialNo.length*10;
	odiv.style.display = "block"
}

/**
 * 添加一个支付对象
 * 1、设置被添加对象的序号；
 * 2、赔案代号为健保局时设置默认费用类型
 * 3、设置赔付对象的讯息
 */

function insertPrpLpayObjectInfo() {
	var $prpLpayObjectInfo = $("#PrpLpayObjectInfo_Data").find("tr[name='PrpLpayObjectInfo']").clone(true);
	var serialNo = $("#PayAccountInfo").find("tr[name='PrpLpayObjectInfo']").length;
	$prpLpayObjectInfo.find("span[name='payObjectIndex']").html(serialNo + 1); //设置序号
	$prpLpayObjectInfo.find("input[name='prpLpayObjectInfoSerialNo']").val(serialNo + 1);
	//当前赔付代号为健保局时，需设置費用類型为健保局
	if ($(":input[name='prpLpayObjectInfoPaycodeType']").val() == "3") {
		$prpLpayObjectInfo.find(":input[name='prpLpayObjectInfoPaymentKind']").val("6");
	}
	$prpLpayObjectInfo.appendTo("#PayAccountInfo");
	uLprpLPayObjectinfo();
	$("div[name='payObject']").eq(1).show();
}

function uLprpLPayObjectinfo(){
	var uiLi_first = '<li><input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="';
	var uiLi_list = ' 	賠付金額: <input  type="text" name="payObjectPayAmount" onblur="setPayObjectPayAmount();" value="" class="common" style="width:100px"/></li>';
	var uiLi = "";
	$.each($.find("input[name='prpLpayObjectInfoSerialNo']"),function(i,n){
		if(i>0){
			uiLi += uiLi_first+i+'" />賠付對象'+i+uiLi_list;
		}
	});
	if(uiLi==""){
		uiLi = "沒有賠款給付對象訊息，請錄入賠款給付對象。";
	}
	var odiv = document.getElementById("prpLPayObjectinfo");
	if(odiv.style.display!="none"){
	    odiv.style.display = "none";
    }
	 var uiodiv = document.getElementById("uLprpLPayObjectinfo");
	 uiodiv.innerHTML=uiLi;
 }
function setPayObjectPayAmount() {
	var payObjectSerialNo = document.getElementsByName("payObjectSerialNo");
	var payObjectPayAmount = document.getElementsByName("payObjectPayAmount");
	var currency = "";
	if(prpLfieldName=="prpLlossDtoPayObjectSerialNo"){
		currency = $(":input[name='prpLlossDtoCurrency']").get(prpLfieldIndex).value;
	}else{
		currency = $(":input[name='prpLpersonLossCurrency']").get(prpLfieldIndex).value;
	}
	var $prpLpayObjectInfoCurrency = $(":input[name='prpLpayObjectInfoCurrency']");
	var payObjectValue = "";
	for ( var i = 0; i < payObjectSerialNo.length; i++) {
		if (payObjectSerialNo[i].checked) {
			if(currency!=$prpLpayObjectInfoCurrency.get(payObjectSerialNo[i].value).value){
				alert("賠償幣別和賠付對象的支付幣別不同，不容許選擇");
				payObjectSerialNo[i].checked = false;
				continue;
			}
			payObjectValue += payObjectSerialNo[i].value;
			if (jQuery.isNumeric(payObjectPayAmount[i].value)) {
				payObjectValue += ":" + payObjectPayAmount[i].value;
			} else {
				payObjectValue += ":0";
			}
			payObjectValue += ";";
		}
	}
	if(payObjectValue!=""){
		payObjectValue = payObjectValue.substring(0, payObjectValue.length-1);
	}
	document.getElementsByName(prpLfieldName)[prpLfieldIndex].value = payObjectValue;
	setPrpLpayObjectInfoPayAmount();
}
function setPrpLpayObjectInfoPayAmount(){
	var prpLpayObjectInfoPayAmount = document.getElementsByName("prpLpayObjectInfoPayAmount");
	for(var i=0;i<prpLpayObjectInfoPayAmount.length;i++){
		prpLpayObjectInfoPayAmount[i].value = 0;
	}
	var prpLlossPayObjectSerialNo = document.getElementsByName("prpLlossDtoPayObjectSerialNo");
	for(var i=0;i<prpLlossPayObjectSerialNo.length;i++){
		if(prpLlossPayObjectSerialNo[i].value!=""){
			var payObjectValue = prpLlossPayObjectSerialNo[i].value.split(";");
			for(var j =0;j<payObjectValue.length;j++){
				var payObjectTemp = payObjectValue[j].split(":");
				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value =parseFloat(payObjectTemp[1]) + parseFloat(prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value);
			}
		}
	}
	var prpLpersonLossPayObjectSerialNo = document.getElementsByName("prpLpersonLossPayObjectSerialNo");
	for (var i = 0; i < prpLpersonLossPayObjectSerialNo.length; i++) {
		if (prpLpersonLossPayObjectSerialNo[i].value != "") {
			var payObjectValue = prpLpersonLossPayObjectSerialNo[i].value.split(";");
			for (var j = 0; j < payObjectValue.length; j++) {
				var payObjectTemp = payObjectValue[j].split(":");
				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value = parseFloat(payObjectTemp[1]) + parseFloat(prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].value);
//				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].readOnly=true;
//				prpLpayObjectInfoPayAmount[parseInt(payObjectTemp[0])].className="readonly";
			}
		}

	}
}

/**
 * @param field 计算本位币金额
 * @param type ,1-表示财产损失，2-表示人员损失，3-表示费用损失
 * @return
 */
function calCurrencySumPay(field,type){
	var index = $(":input[name='"+field.name+"']").index(field);
	if(type==1){
		var exchRate = $(":input[name='prpLlossDtoExchRate']").get(index).value;
		var sumRealPay = $(":input[name='prpLlossDtoSumRealPay']").get(index).value;
		var currencySumPay = parseFloat(sumRealPay)*parseFloat(exchRate);
		if(!$.isNumeric(currencySumPay)){
			currencySumPay = 0;
		}
		$(":input[name='prpLlossDtoSumRealPayNTD']").get(index).value=pointTwo(currencySumPay);
	}else if(type==2){
		var exchRate = $(":input[name='prpLpersonLossExchRate']").get(index).value;
		var sumRealPay = $(":input[name='prpLpersonLossSumRealPay']").get(index).value;
		var currencySumPay = parseFloat(sumRealPay)*parseFloat(exchRate);
		if(!$.isNumeric(currencySumPay)){
			currencySumPay = 0;
		}
		$(":input[name='prpLpersonLossCurrencySumPay']").get(index).value=pointTwo(currencySumPay);
	}else if(type==3){
		var exchRate = $(":input[name='prpLchargeExchRate']").get(index).value;
		var sumRealPay = $(":input[name='prpLchargeChargeAmount']").get(index).value;
		var currencySumPay = parseFloat(sumRealPay)*parseFloat(exchRate);
		if(!$.isNumeric(currencySumPay)){
			currencySumPay = 0;
		}
		$(":input[name='prpLchargeChargeAmountNTD']").get(index).value=pointTwo(currencySumPay);
	}
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
	calFund();
}
/**
 * 计算赔付比例，不用进行四舍五入处理
 * @param field
 * @return
 */
function setClaimRate(field){
	var $tr = $(field).parents("tr[name='prpLlossObject']");
	var itemValue = parseFloat($tr.find(":input[name='prpLlossDtoItemValue']").val());
	var amount = parseFloat($tr.find(":input[name='prpLlossDtoAmount']").val());
	if(itemValue>0&&amount>0){
		var claimRate = amount/itemValue*100;
		if(claimRate>=0&&claimRate<=100){
			claimRate = claimRate.toString();
			if(claimRate.indexOf(".")>-1){
				claimRate = claimRate.toString().substring(0, claimRate.indexOf("."))
			}
			$tr.find(":input[name='prpLlossDtoClaimRate']").val(claimRate);
		}
	}
	calRealpay(field);
}
/**
 * 获取最大的赔付额
 * @param field
 * @return
 */
function getCompensateLossHisPaid(field){
	var $tr = $(field).parents("tr[name='prpLlossObject']");
	var policyNo = $(":input[name='prpLcompensatePolicyNo']").val();
	var kindCode = $tr.find(":input[name='prpLlossDtoKindCode']").val();
	var itemCode = $tr.find(":input[name='prpLlossDtoItemCode']").val();
	if(kindCode!=""&&itemCode!=""){
		var url = contextRootPath+"/compensate/compensateLossHisPaid.do";
		$.ajax({
			type:"get",
			url:url,
			cache:false,
			dataType:"json",
			data:"prpLloss.policyNo="+policyNo+"&prpLloss.kindCode="+kindCode+"&prpLloss.itemCode="+itemCode,
			success:function(data){
				$tr.find(":input[name='prpLlossDtoHisPaid']").val(pointTwo(data.hisPaid));
			}
		});
	}
}

function checkBeyondQuota(field) { //检查是否超出限额
	var quota = 0;
	var fieldname = field.name;
	var findex = 0;
	var kindName = "";
//	var countCharge = getElementCount("prpLchargeChargeCode");
//	var countPersonLoss = getElementCount("prpLpersonLossSumRealPay1");
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	if (fm.all("prpLlossDtoAmountDisplay")[findex]) {
		quota = parseFloat(fm.all("prpLlossDtoAmountDisplay")[findex].value);
	} else {
		quota = parseFloat(fm.all("prpLlossDtoAmountDisplay")[0].value);
	}

	var amount = 0.00;
	if (fm.all("prpLlossDtoKindName")[findex]) {
		kindName = fm.all("prpLlossDtoKindName")[findex].value;
	}
	var kindNum = fm.all("prpLlossDtoKindCode").length;
	if (kindNum != 'undefined' && kindNum > 1) {
		for (var i = 1; i < kindNum; i++) {
			if (fm.prpLlossDtoSumRealPayNTD[i]) {
				if (fm.prpLlossDtoSumRealPayNTD[i].value != "") {
					amount = amount + parseFloat(fm.prpLlossDtoSumRealPayNTD[i].value);
				}
			}
		}
	}
//	for (var i = 0; i < countPersonLoss; i++) {
//		if (fm.prpLpersonLossSumRealPay1[i]) {
//			if (fm.prpLpersonLossSumRealPay1[i].value != "") {
//				amount = amount + parseFloat(fm.prpLpersonLossSumRealPay1[i].value);
//			}
//		}
//	}
	if (amount > quota) {
		alert(kindName + i18n.commonLiab.compensate.lossAssessmentAmount + quota + i18n.commonLiab.compensate.yuan); //估损金额之和超过限额(     //)元
		//disablebutton();
		//field.value="";
		return false;
	}
}

function calRealpay(field) {
    var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
    var kindCode = $prpLlossObject.find(":input[name='prpLlossDtoKindCode']").val();
    if(kindCode.length == 0){
    	alert("請錄入財產賠付險別！");
    	return false;
    }
    var currency = $prpLlossObject.find(":input[name='prpLlossDtoCurrency']").val();
    var sumLoss = initValue($prpLlossObject.find(":input[name='prpLlossDtoSumLoss']"), 0, currency);// 核定損失
    var sumRest = initValue($prpLlossObject.find(":input[name='prpLlossDtoSumRest']"), 0, currency);// 殘值
    var claimRate = initValue($prpLlossObject.find(":input[name='prpLlossDtoClaimRate']"), 0);// 賠付比例
    var deductible = initValue($prpLlossObject.find(":input[name='prpLlossDtoDeductible']"), 0, currency);// 自負額
    var deductibleRate = initValue($prpLlossObject.find(":input[name='prpLlossDtoDeductibleRate']"), 0);// 自負額比率
    var $sumRealPay = $prpLlossObject.find(":input[name='prpLlossDtoSumRealPay']");
    var exchRate = parseFloat($prpLlossObject.find(":input[name='prpLlossDtoExchRate']").val());
    var $sumRealPayNTD = $prpLlossObject.find(":input[name='prpLlossDtoSumRealPayNTD']");
    var sum = (sumLoss - sumRest) * (claimRate * 0.01);
    if(sumLoss > 0 && sum < 0){//核定損失为正，则赔偿金额不得小于0
        sum = 0;
    }
    var sum1 = sum - deductible;
    sum = sum1 < 0 ? 0 : sum1;
    var sum2 = sum * (1 - deductibleRate * 0.01);
    if (sum2 < 0) {
        sum2 = 0;
    }
    if (sum2 < sum) {
        sum = sum2;
    }
    $sumRealPay.val(getFormatValueByCurrency(sum, currency));
    $sumRealPayNTD.val(Math.round(sum * exchRate));
    if(sum != 0){
        var checkFlag = checkLimit("1",$prpLlossObject,kindCode);
        if(checkFlag){
            $sumRealPay.val(0);
            $sumRealPayNTD.val(0);
        }
    }
    prpLlossIsPayForOther(field);
    calFund();
}

/***
 *險別變更時，清空賠付
 */
function clearPrpLloss(field){
	if($.trim(field.value).length == 0){
		var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
		var $sumRealPay = $prpLlossObject.find(":input[name='prpLlossDtoSumRealPay']");
		var $sumRealPayNTD = $prpLlossObject.find(":input[name='prpLlossDtoSumRealPayNTD']");
		if(parseFloat($sumRealPay.val()) != 0){
			$sumRealPay.val(0);
			$sumRealPayNTD.val(0);
			calFund();
		}
	}
}

/***
 * 根据币别获取金额值，NTD删除小数，其他精确到2位小数
 * @param formatValue 格式化的金額
 * @param currency 當前金額的幣別
 * @returns 格式化后的金額
 */
function getFormatValueByCurrency(formatValue,currency){
    if ("NTD" == currency || formatValue % 1 == 0) {
        return Math.round(formatValue);
    } else {
        return round(formatValue, 2);
    }
}

/***
 * jquery 对象设置默认值
 * @param $object
 * @param defaultValue
 * @param 值的币别 金额处理时才有
 */
function initValue($object,defaultValue,exchCurrency){
    var v = $object.val();
    var f = parseFloat(v);
    if ($.trim(v).length == 0 || isNaN(f)) {
        $object.val(defaultValue);
        return defaultValue;
    } else {
        if ("NTD" == exchCurrency || f % 1 == 0) {
            $object.val(Math.round(f));
        }else{
            $object.val(round(f, 2));
        }
    }
    return f;
}

/***
 * 計算人傷賠付
 * @param field
 */
function calRealpayForPerson(field){
    var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
    var kindCode = $prpLpersonLossObject.find(":input[name='prpLpersonLossKindCode']").val();
    if(kindCode.length == 0){
    	alert("請錄入人傷費用賠付險別！");
    	return false;
    }
    var currency = $prpLpersonLossObject.find(":input[name='prpLpersonLossCurrency']").val();
    var sumDefPay = initValue($prpLpersonLossObject.find(":input[name='prpLpersonLossSumDefPay']"),0,currency);//核定賠償
    $prpLpersonLossObject.find(":input[name='prpLpersonLossSumLoss']").val(sumDefPay);
    var deductible = initValue($prpLpersonLossObject.find(":input[name='prpLpersonLossDeductible']"),0,currency);//自負額
    var $sumRealPay = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPay']");//賠償金額
    var $sumRealPayNTD = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPayNTD']");//賠償金額（NTD）
    var exchRate = parseFloat($prpLpersonLossObject.find(":input[name='prpLpersonLossExchRate']").val());//匯率
    var sum = sumDefPay - deductible;
    if(sumDefPay > 0 && sum < 0){//核定赔偿为正，则赔偿金额不得小于0
        sum = 0;
    }
    $sumRealPay.val(getFormatValueByCurrency(sum, currency));
    $sumRealPayNTD.val(Math.round(sum * exchRate));
    var $personObject = $(field).parents("tr[name='personObject']");//找到其所在的父塊
    if(sum != 0){
        var checkFlag = checkLimit("2",$personObject,kindCode);
        if(checkFlag){
            $sumRealPay.val(0);
            $sumRealPayNTD.val(0);
        }
    }
    setSumRealPay1NTD($personObject);
    prpLpersonLossIsPayForOther(field);
    calFund();
}


/***
 * 人員傷亡賠付，計算總賠償金額（NTD）：
 */
function setSumRealPay1NTD($personObject){
    var sumRealPay1NTD = 0;
    $personObject.find(":input[name='prpLpersonLossSumRealPayNTD']").each(function(){
        sumRealPay1NTD += (isNaN($(this).val()) ? 0 : parseFloat($(this).val(),10));
    });
    var addPremium = $personObject.find(":input[name='prpLpersonLossAddPremium']").val();//补充保费
    $personObject.find(":input[name='prpLpersonLossSumRealPay1NTD']").val(Math.round(sumRealPay1NTD-addPremium));
}

/***
 * 人伤险别变更时，重新清空下赔付
 * @param field
 * @returns
 */
function clearPrpLpersonLoss(field){
	if($.trim(field.value).length == 0){
		var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
		var $sumRealPay = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPay']");//賠償金額
		var $sumRealPayNTD = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPayNTD']");//賠償金額（NTD）
		if(parseFloat($sumRealPay.val()) != 0){
			$sumRealPay.val(0);
			$sumRealPayNTD.val(0);
			var $personObject = $(field).parents("tr[name='personObject']");
			setSumRealPay1NTD($personObject);
			calFund();
		}
	}
}

/***
 * 证件类型，证件号码发生变更时，需要清空所有粉人伤费用赔付，因为，人员变动需要重新校验限额
 */
function resetSumRealPay(field){
	var $personObject = $(field).parents("tr[name='personObject']");
	var $sumRealPay = $personObject.find(":input[name='prpLpersonLossSumRealPay']");
	var $sumRealPayNTD = $personObject.find(":input[name='prpLpersonLossSumRealPayNTD']");
	var checkFlag = false;
	var desc = "";
	if ("prpLpersonLossIdentifyNumber" == field.name) {
		desc = "人員 身份證字號";
	} else if ("prpLpersonLossCasualties" == field.name) {
		desc = "傷亡情形";
	}
	$sumRealPay.each(function(i,e){
		if (parseFloat($.trim(e.value)) != 0) {
			if (!checkFlag) {
				alert("修改" + desc + "后，該人員的各項賠付需要重新校驗限額，\n請在賠款金額會重置后重新計算！");
			}
			checkFlag == true;
			e.value = 0;
			$sumRealPayNTD.get(i).value = 0;
		}
	});
	if(checkFlag){
		setSumRealPay1NTD($personObject);
		calFund();
	}
}
/***
 * 添加人傷費用前的校驗，必須錄入證件號碼，好確定人員的唯一性。
 */
function beforeInsertPersonFeeLoss(pageCode,pageCode_Data,btnField,csFieldName,psFieldName){
	var $personObject = $(btnField).parents("tr[name='personObject']");
	var sex = $personObject.find(":input[name='prpLpersonLossSex']").val();
	var $certificateCode = $personObject.find(":input[name='prpLpersonLossCertificateCode']");
	var certificateCode = $certificateCode.val();
	var $identifyNumber = $personObject.find(":input[name='prpLpersonLossIdentifyNumber']");
	var identifyNumber = $identifyNumber.val();
	if ($.trim(identifyNumber) == "") {
		alertMessage($identifyNumber[0], "請錄入身份證字號!");
		return false;
	} else if (!checkIdentifyNumber(identifyNumber, sex)) {
		alertMessage($identifyNumber[0], "請錄入正確的身份證字號!");
		return false;
	}
	return true;
}
/***
 * 刪除人傷訊息之前先找到被刪除對象所屬的人員，方便刪除后操作
 */
var $personObject = null;
function beforeDeletePersonFeeLoss(btnField,pageCode,csFieldName,psFieldName){
    $personObject = $(btnField).parents("tr[name='personObject']");
    return true;
}
/**
 *删除本条赔付之後的处理
 */
function afterDeletePersonFeeLoss(deletObject,btnField,pageCode,csFieldName){
    if($personObject!=null && $personObject.length > 0){
        setSumRealPay1NTD($personObject); 
    }
    calFund();
}
/**
 *删除本条赔付之後的处理
 */
function afterDeletePerson(deletObject,btnField,pageCode,csFieldName){
    calFund();
}

/***
 * 初始化个赔付、费用的NTD赔付金额
 */
function initPayNTD(){
    var sumRealPay = 0;
    var exchRate = 0;
    $("#lLoss").find("tr[name='prpLlossObject']").each(function(){
        sumRealPay = parseFloat($(this).find(":input[name='prpLlossDtoSumRealPay']").val());
        exchRate = parseFloat($(this).find(":input[name='prpLlossDtoExchRate']").val());
        $(this).find(":input[name='prpLlossDtoSumRealPayNTD']").val(Math.round(sumRealPay*exchRate));
    });
    $("#Person").find("tr[name='personObject']").each(function(i,personObject){
        $(personObject).find("tr[name='prpLpersonLossObject']").each(function(j,prpLpersonLossObject){
            sumRealPay = parseFloat($(prpLpersonLossObject).find(":input[name='prpLpersonLossSumRealPay']").val());
            exchRate = parseFloat($(prpLpersonLossObject).find(":input[name='prpLpersonLossExchRate']").val());
            var $sumRealPayNTD = $(prpLpersonLossObject).find(":input[name='prpLpersonLossSumRealPayNTD']");
            $sumRealPayNTD.val(Math.round(sumRealPay*exchRate));
        });
        setSumRealPay1NTD($(personObject));
    });
    $("#Charge").find("tbody tr").each(function(){
        var $chargeAmount = $(this).find(":input[name='prpLchargeChargeAmount']");
        var $exchRate = $(this).find(":input[name='prpLchargeExchRate']");
        if($chargeAmount.length > 0 && $exchRate.length > 0){
            sumRealPay = parseFloat($chargeAmount.val());
            exchRate = parseFloat($exchRate.val());
            $(this).find(":input[name='prpLchargeChargeAmountNTD']").val(Math.round(sumRealPay*exchRate));
        }
    });
    calFund();
}
/***
 * 人伤赔付获取当前赔付币别对本位币NTD的汇率
 */
function getPrpLpersonLossExchRate(field){
	var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
	$prpLpersonLossObject.find(":input[name='prpLpersonLossExchRate']").val(jQuery.data($exchToBase,$(field).val()));
	var $exchRate = $prpLpersonLossObject.find(":input[name='prpLpersonLossExchRate']");
	if($(field).val()==CURRENCYINFO.LOCAL_CURRENCY){
		$exchRate.val(1);
	}else{
		var t = jQuery.data($exchToBase,$(field).val());
		if(t == undefined){
			alert($(field).val()+"對本位幣（NTD）的匯率沒有配置，請自行調整");
			$(field).val(CURRENCYINFO.LOCAL_CURRENCY);
			$exchRate.val(1);
		}else{
			$exchRate.val(jQuery.data($exchToBase,$(field).val())); 
		}
	}
	$prpLpersonLossObject.find(":input[name='prpLpersonLossCurrency2']").val($(field).val());
	$prpLpersonLossObject.find(":input[name='prpLpersonLossCurrency3']").val($(field).val());
	$prpLpersonLossObject.find(":input[name='prpLpersonLossCurrency4']").val($(field).val());
	calRealpayForPerson(field);
}
/***
 * 人伤赔付获取当前赔付币别对本位币NTD的汇率
 */
function getPrpLpayObjectInfoExchRate(field){
	var $PrpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
	var $exchRate = $PrpLpayObjectInfo.find(":input[name='prpLpayObjectInfoExchRate']");
	if($(field).val()==CURRENCYINFO.LOCAL_CURRENCY){
		$exchRate.val(1);
	}else{
		var t = jQuery.data($exchToBase,$(field).val());
		if(t == undefined){
			alert($(field).val()+"對本位幣（NTD）的匯率沒有配置，請自行調整");
			$(field).val(CURRENCYINFO.LOCAL_CURRENCY);
			$exchRate.val(1);
		}else{
			$exchRate.val(jQuery.data($exchToBase,$(field).val())); 
		}
	}
	$PrpLpayObjectInfo.find(":input[name='prpLpayObjectInfoCurrency']").val($(field).val());
}
/***
 *币别切换
 */
function getPrpLlossDtoExchRate(field){
    var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
    var $exchRate = $prpLlossObject.find(":input[name='prpLlossDtoExchRate']");
    if($(field).val()==CURRENCYINFO.LOCAL_CURRENCY){
        $exchRate.val(1);
    }else{
        var t = jQuery.data($exchToBase,$(field).val());
        if(t == undefined){
            alert($(field).val()+"對本位幣（NTD）的匯率沒有配置，請自行調整！");
            $(field).val(CURRENCYINFO.LOCAL_CURRENCY);
            $exchRate.val(1);
        }else{
            $exchRate.val(jQuery.data($exchToBase,$(field).val())); 
        }
    }
    $prpLlossObject.find(":input[name='prpLlossDtoCurrency2']").val($(field).val());
    $prpLlossObject.find(":input[name='prpLlossDtoCurrency3']").val($(field).val());
    $prpLlossObject.find(":input[name='prpLlossDtoCurrency4']").val($(field).val());
    calRealpay(field);
}
/***
 *币别切换
 */
function getPrpLchargeExchRate(field){
	var index = $(":input[name='prpLchargeCurrency']").index(field);
    var $exchRate = $($(":input[name='prpLchargeExchRate']").get(index));
    if($(field).val()==CURRENCYINFO.LOCAL_CURRENCY){
        $exchRate.val(1);
    }else{
        var t = jQuery.data($exchToBase,$(field).val());
        if(t == undefined){
            alert($(field).val()+"對本位幣（NTD）的匯率沒有配置，請自行調整！");
            $(field).val(CURRENCYINFO.LOCAL_CURRENCY);
            $exchRate.val(1);
        }else{
            $exchRate.val(jQuery.data($exchToBase,$(field).val())); 
        }
    }
    $($(":input[name='prpLchargeAccountCurrency']").get(index)).val(field.value);
	$(":input[name='prpLchargeCurrencyForPayObject']").get(index).value==field.value;
	calCurrencySumPay(field,"3");
}
/***
 *判断是否超过保额
 */
function prpLlossIsPayForOther(field){
	var isPayForOther = $(":input[name='isPayForOther']:checked");
	if(isPayForOther.val()=="0"){
		 var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
		 var amount = $prpLlossObject.find(":input[name='prpLlossDtoAmount']").val();
		 var coinUsCoinsRate = $(":input[name='coinUsCoinsRate']").val();
		 var sumRealPayNTD = $prpLlossObject.find(":input[name='prpLlossDtoSumRealPayNTD']").val();
		 if($.isNumeric(amount)&&$.isNumeric(sumRealPayNTD)&&$.isNumeric(coinUsCoinsRate)){
			 amount = parseFloat(amount)*parseFloat(coinUsCoinsRate);
			 sumRealPayNTD = parseFloat(sumRealPayNTD);
			 if(amount>0&&sumRealPayNTD>0&&sumRealPayNTD>amount){
				 alert("選擇不代付賠款，賠款金額大於我方保險金額"+amount+"，請檢查是否輸入正確！");
			 }
		 }
	}
}
/***
 *判断是否超过保额
 */
function prpLpersonLossIsPayForOther(field){
	var isPayForOther = $(":input[name='isPayForOther']:checked");
	if(isPayForOther.val()=="0"){
		 var $personObject = $(field).parents("tr[name='prpLpersonLossObject']");
		 var amount = $personObject.find(":input[name='prpLpersonLossAmount']").val();
		 var coinUsCoinsRate = $(":input[name='coinUsCoinsRate']").val();
		 var sumRealPayNTD = $personObject.find(":input[name='prpLpersonLossSumRealPayNTD']").val();
		 if($.isNumeric(amount)&&$.isNumeric(sumRealPayNTD)&&$.isNumeric(coinUsCoinsRate)){
			 amount = parseFloat(amount)*parseFloat(coinUsCoinsRate);
			 sumRealPayNTD = parseFloat(sumRealPayNTD);
			 if(amount>0&&sumRealPayNTD>0&&sumRealPayNTD>amount){
				 alert("選擇不代付賠款，賠款金額大於我方保險金額"+amount+"，請檢查是否輸入正確！");
			 }
		 }
	}
}
