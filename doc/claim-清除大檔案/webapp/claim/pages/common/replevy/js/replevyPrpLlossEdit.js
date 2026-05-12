/***
 * 校驗實際追償金額錄入，不得超過各險別的上限
 */
function checkSumRealPay(){
	var checkFlag = true;
	$("#PrpLloss").find("tr[name='prpLlossObject']").each(function(){
		var kindCode = $(this).find(":input[name='prpLlossKindCode']").val();
		var kindName = $(this).find(":input[name='prpLlossKindName']").val();
		var sumRealPay =  parseFloat($(this).find(":input[name='prpLlossSumRealPay']").val());
		var $limitObject = $("#limitList").find("div[name='limitObject_"+kindCode+"']");
		if($limitObject.length > 0){
			var limitSumLoss = parseFloat($limitObject.find(":input[name='limitSumLoss']").val());
			var limitSumRealPay = parseFloat($limitObject.find(":input[name='limitSumRealPay']").val());
			if(limitSumLoss - Math.abs(limitSumRealPay) < sumRealPay){
				checkFlag = false;
				var messages = "險別"+kindName;
				messages +="賠款為："+Math.round(limitSumLoss)+"，已追償金額："+Math.round(Math.abs(limitSumRealPay))+"。\r\n";
				messages +="本次實際追償金額不得超過："+(limitSumLoss-Math.abs(limitSumRealPay));
				alert(messages);
				return false;
			}
		}
	});
	return checkFlag;
}
/***
 * 追償訊息校驗
 * 預估追償金額不得小於追償金額
 * @param field
 */
function controlPrpLlossOther(field){
	var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
	var $currKindCode = $prpLlossObject.find(":input[name='prpLlossKindCode']");
	var $currKindName = $prpLlossObject.find(":input[name='prpLlossKindName']");
	var $prpLlossSumLoss = $prpLlossObject.find(":input[name='prpLlossSumLoss']"); //預估追償金額
	var $prpLlossSumRealPay = $prpLlossObject.find(":input[name='prpLlossSumRealPay']"); //追償金額
	if($.trim($currKindCode.val()).length==0){
		return alertMessage($currKindCode[0], "請您先錄入險別訊息！");
	}
	if(field.name=="prpLlossSumLoss"){
		var currency = $prpLlossObject.find(":input[name='prpLlossCurrency']").val();
	    initValue($prpLlossSumLoss,0,currency);
	}
	if(field.name=="prpLlossSumRealPay"){
		setRealPayNTD(field);
	}
	return true;
}

/***
 * 控制不能錄入重複的險別、控制強制險賠付類別不能相同
 * @param field
 */
function controlPrpLloss(field){
	var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
	var $currKindCode = $prpLlossObject.find(":input[name='prpLlossKindCode']");
	var $currKindName = $prpLlossObject.find(":input[name='prpLlossKindName']");
	var kindCode = $currKindCode.val();
	if($.trim(kindCode).length!=0){
		var otherKindCodes = $("#PrpLloss").find(":input[name='prpLlossKindCode'][value='"+kindCode+"']").not($currKindCode[0]).length;
		if(otherKindCodes > 0){
			alertMessage($currKindCode[0], "險別'"+$currKindName.val()+"'預估追償訊息已存在，請勿重複錄入！");
			$currKindCode.val("");
			$currKindName.val("");
		}else{
			var limitSumLoss = $("div[name='limitObject_"+kindCode+"']").find(":input[name='limitSumLoss']").val();
			$prpLlossObject.find(":input[name='prpLlossSumDefPay']").val(Math.round(limitSumLoss));
		}
	}
}

/***
 * 校驗預估金額，規則：本次追償合計 <= 總賠付額度-已追償的 
 */
function validateReplevy(field){
	var prpLlossReplevy = 0;
	$("#PrpLloss").find(":input[name='prpLlossSumLoss']").each(function(){
		prpLlossReplevy += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
	});
	var SumLoss = parseFloat($(":input[name='SumLoss']").val());//總賠償金額
	var OldSumPaidAll = Math.abs(parseFloat($(":input[name='OldSumPaidAll']").val()));//已追偿金额
	if((prpLlossReplevy+OldSumPaidAll)>SumLoss){
		var messages = "本案總賠償金額："+Math.round(SumLoss)+";";
		if(parseFloat(OldSumPaidAll)!=0){
			messages+="已追償金額："+Math.round(OldSumPaidAll)+";\r\n";
		}
		messages+="本次預估追償金額之和不得超過："+Math.round(SumLoss-OldSumPaidAll)+"。";
		recoveryData(field);
		return alertMessage(field, messages);
	}
	return true;
}

/***
 * 跟換險別時，清空本條錄入的預估追償訊息
 * @param field
 */
function clearPrpLloss(field) {
	var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
	calPayAmount($prpLlossObject);
	$prpLlossObject.find(":input[name='prpLlossCompelPayType']").val(""); //賠付類別
	$prpLlossObject.find(":input[name='prpLlossSumLoss']").val(0); //預估追償金額
	$prpLlossObject.find(":input[name='prpLlossPayObjectSerialNo']").val(""); //收取對象訊息
	$prpLlossObject.find(":input[name='prpLlossRemark']").val(""); //備註
}


function setRealPayNTD(field){
    var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
    var currency = $prpLlossObject.find(":input[name='prpLlossCurrency']").val();
    var sumRealPay = initValue($prpLlossObject.find(":input[name='prpLlossSumRealPay']"),0,currency);
    var exchRate = parseFloat($prpLlossObject.find(":input[name='prpLlossDtoExchRate']").val());
    $prpLlossObject.find(":input[name='prpLlossDtoSumRealPayNTD']").val(Math.round(sumRealPay*exchRate));
    calSumPaidAll();
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
    $prpLlossObject.find(":input[name='prpLlossCurrency2']").val($(field).val());
    $prpLlossObject.find(":input[name='prpLlossCurrency3']").val($(field).val());
    $prpLlossObject.find(":input[name='prpLlossCurrency4']").val($(field).val());
    setRealPayNTD(field);
}
/***
 *币别切换
 */
function getPrpLpayObjectInfoExchRate(field){
    var $PrpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
    var $exchRate = $PrpLpayObjectInfo.find(":input[name='prpLpayObjectInfoExchRate']");
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
    $PrpLpayObjectInfo.find(":input[name='prpLpayObjectInfoCurrency']").val(field.value);
}
