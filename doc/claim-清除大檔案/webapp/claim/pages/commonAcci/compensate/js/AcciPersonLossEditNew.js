/** 删除受害人资讯 */
function deletePrpLpersonLossObject(field){
	$(field).parents("tr[name='prpLpersonLossTr']").remove();
	setPersonNo_new();
	return true;
}
/** 新增受害人资讯 */
function insertPrpLpersonLossObject(){
	var $riskCode = $(":input[name='prpLcompensateRiskCode']");
	if($riskCode.val()!="TE"){
		if($("tr[name='prpLpersonLossTr']").length>=2){
			alert($riskCode.val()+"險種只能有一個受害人！請不要重複添加。");
			return true;
		}
	}
	$("#PersonLoss_Data").find("tr[name='prpLpersonLossTr']").clone(true).appendTo("#PersonLoss");
	setPersonNo_new();
	insertPrpLpersonInfo();
	return true;
}
/**
 * 设置非TE险种的人员信息
 * @return
 */
function insertPrpLpersonInfo(){
	var $riskCode = $(":input[name='prpLcompensateRiskCode']");
	if($riskCode.val()!="TE"){
		var $tr = $("tr[name='prpLpersonLossTr']").last();
		var $personName = $tr.find(":input[name='prpLpersonLossPersonName']");
		var $sex = $tr.find(":input[name='prpLpersonLossSex']");
		var $age = $tr.find(":input[name='prpLpersonLossAge']");
		var $familyNo = $tr.find(":input[name='prpLpersonLossFamilyNo']");
		var $IdentifyNumber = $tr.find(":input[name='prpLpersonLossIdentifyNumber']");
		var $SerialNo = $(":input[name='prpCinsuredSerialNo'][value='"+$familyNo.val()+"']");
		if($SerialNo.length>0){
			var $span = $SerialNo.parent();
			$personName.val($span.find(":input[name='prpCinsuredInsuredName']").val());
			$sex.val($span.find(":input[name='prpCinsuredSex']").val());
			$IdentifyNumber.val($span.find(":input[name='prpCinsuredIdentifyNumber']").val());
			var prpCinsuredAge = $span.find(":input[name='prpCinsuredAge']").val();
			if(prpCinsuredAge!=""){
				$age.val(prpCinsuredAge);
			}else{
				$age.val("0");
			}
			$personName.attr("readonly",true);
			$sex.attr("disabled",true);
			$age.attr("readonly",true);
		}
	}
	return true;
}
/** 增加受害人费用信息 */
function insertPrpLpersonFeeLossObject(field){
	var $cloneObject = $("#PersonFeeLoss_Data").find("tr[name='prpLpersonFeeLossTr']").clone(true);
	$(field).parents("table[name='PersonFeeLoss']").children("tbody").append($cloneObject);
	setPersonNo_new();
	return true;
}

/** 删除受害人费用信息 */
function deletePrpLpersonFeeLossObject(field){
	$(field).parents("tr[name='prpLpersonFeeLossTr']").remove();// 删除当前费用信息
	// 重新计算当前受害人的賠付金額
	setPersonNo_new();
}
/** 插入医院信息 */
function insertPersonHospitalObject(field){
	var $cloneObject = $("#PersonLoss_Data").find("tr[name='prpLpersonHospitalTr']").clone(true);
	$(field).parents("table[name='PersonHospital']").children("tbody").append($cloneObject);
	setPersonNo_new();
	return true;
}
/** 删除医院信息 */
function deletePersonHospitalObject(field){
	var $prpLPersonHospitalTr=$(field).parents("table[name='PersonHospital']").children("tbody").children("tr");
	if($prpLPersonHospitalTr.length<=1){
		alert("受害人訊息必須要有一条就診醫院！");
		return false;
	}
	$(field).parents("tr[name='prpLpersonHospitalTr']").remove();// 删除当前费用信息
	// 重新计算当前受害人的賠付金額
	setPersonNo_new();
	return true;
}
/** 重新计算序号 */
function setPersonNo_new(){
	var personLossPersonNo = $("input[name='personLossPersonNo']");// 重新计算prpLpersonPersonNo
	$("tr[name='prpLpersonLossTr']").each(function(i,n){
		if(i>0){
			personLossPersonNo[i].value = i;
			var prpLpersonLossPersonNo = $(n).find("input[name='prpLpersonLossPersonNo']");
			$.each(prpLpersonLossPersonNo,function(j,m){
				m.value = i;
			});
			var hospitalPersonNo = $(n).find("input[name='hospitalPersonNo']");
			$.each(hospitalPersonNo,function(j,m){
				m.value = i;
			});
		}
	})
}
/** 查看危险单位信息 */
function viewDangerUnitPersonLoss(field) {
    for (var i = 1; i < document.getElementsByName("personLossPersonNo").length; i++) {
        if (fm.prpLpersonLossDangerNo[i] == field) {
            var count = i;
            var policyNo = fm.policyno.value;
            var damageDate = fm.damageStartDate.value;
            //field.value = "";
            var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&PageType=PersonLoss&openerIndex=" + count;
            window.open(
                    submitStr,
                    '查看危险单位信息',
                    'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
        }
    }
}
/** 设置给付类别是否可以输入 */
function initPaymentType(){
	$("tr[name='prpLpersonFeeLossTr']").each(function(i,n){
		var $paymentType = $(this).find(":input[name='prpLpersonLossPaymentType']");
		if(i!=0){
			var $prpLpersonLossItemKindNo = $(this).find(":input[name='prpLpersonLossItemKindNo']");
			var $itemKindNo = $("span[name='span_prpCitemKind']").find(":input[name='itemKindNo'][value='"+$prpLpersonLossItemKindNo.val()+"']");
			if($itemKindNo.length>0){
				var $span_prpCitemKind = $itemKindNo.parent();
				var commodityCode = $span_prpCitemKind.find(":input[name='commodityCode']").val();
				if(commodityCode.length>4){
					if(commodityCode.substring(3,5)=="24"||commodityCode.substring(3,5)=="30"){
						setPaymentTypeReadonly($paymentType.get(0),false);
					}else{
						setPaymentTypeReadonly($paymentType.get(0),true);
					}
				}else{
					setPaymentTypeReadonly($paymentType.get(0),false);
				}
				
			}
			if($paymentType.val()=="22"){
				setPaymentFractureReadonly($paymentType.get(0),false);
			}else{
				setPaymentFractureReadonly($paymentType.get(0),true);
			}
//			var $sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']");
//			if($paymentType.val()=="01"||$paymentType.val()=="21"||$paymentType.val()=="23"){
//				$sumRealPay.attr("readonly",false);
//			}else{
//				$sumRealPay.attr("readonly",true);
//			}
		}
		setDeathDisplay($paymentType.get(0));
	});
	countSumRealPay();
}
/**
 * 初始化人伤信息
 * @return
 */
function initPrpLpersonloss(){
	var $riskCode = $(":input[name='prpLcompensateRiskCode']");
	if($riskCode.val()!="TE"){
		var $tr = $("tr[name='prpLpersonLossTr']").last();
		var $personName = $tr.find(":input[name='prpLpersonLossPersonName']");
		var $sex = $tr.find(":input[name='prpLpersonLossSex']");
		var $age = $tr.find(":input[name='prpLpersonLossAge']");
		$personName.attr("readonly",true);
		$sex.attr("disabled",true);
		$age.attr("readonly",true);
	}
	return true;
}
/** 设置死亡信息的显示隐藏 */
function setDeathDisplay(field){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var $paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']");
	$tr.nextAll().each(function(){
		if($paymentType.val()=="02"){
			$(this).show();
		}else{
			$(this).hide();
		}
	});
	
}
/** 设置骨折程度，骨折部位，未住院日数为只读 */
function setPaymentFractureReadonly(field,flag){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	$tr.find(":input[name='prpLpersonLossFractureSite']").attr("disabled",flag);
	$tr.find(":input[name='prpLpersonLossNotHospitalDays']").attr("disabled",flag);
	$tr.find(":input[name='prpLpersonLossFractureDegree']").attr("disabled",flag);
}
/** 设置给付类别为只读 */
function setPaymentTypeReadonly(field,flag){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	$tr.find(":input[name='prpLpersonLossPaymentType']").attr("readonly",flag);
	$tr.find(":input[name='prpLpersonLossPaymentType1']").attr("readonly",flag);
	$tr.find(":input[name='prpLpersonLossPaymentType2']").attr("readonly",flag);
	$tr.find(":input[name='prpLpersonLossPaymentType']").attr("disabled",flag);
	$tr.find(":input[name='prpLpersonLossPaymentType1']").attr("disabled",flag);
	$tr.find(":input[name='prpLpersonLossPaymentType2']").attr("disabled",flag);
}
/** 非24、30險種（險種商品代碼第4～5碼不是24與30之險種）統一用給付代號：23 2300 0000 */
function setPaymentType_24_30(field){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var $kindCode = $tr.find(":input[name='prpLpersonLossKindCode']");
	var $prpLpersonLossItemKindNo = $tr.find(":input[name='prpLpersonLossItemKindNo']");
	var $itemKindNo = $("span[name='span_prpCitemKind']").find(":input[name='itemKindNo'][value='"+$prpLpersonLossItemKindNo.val()+"']");
	if($itemKindNo.length>0){
		var $span_prpCitemKind = $itemKindNo.parent();
		var commodityCode = $span_prpCitemKind.find(":input[name='commodityCode']").val();
		$tr.find(":input[name='prpLpersonLossAmount']").val($span_prpCitemKind.find(":input[name='amount']").val());
		//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
		var $prpLpersonLossKindCode = $tr.find(":input[name='prpLpersonLossKindCode']");
		if($prpLpersonLossKindCode.val()=="TR47"){
			var amount = getAmount(field);
			$tr.find(":input[name='prpLpersonLossAmount']").val(amount);
			return false;
		}
		//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
		var $paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']");
		var $paymentType1 = $tr.find(":input[name='prpLpersonLossPaymentType1']");
		var $paymentType2 = $tr.find(":input[name='prpLpersonLossPaymentType2']");
		if(commodityCode.length>4){
			if(commodityCode.substring(3,5)=="24"||commodityCode.substring(3,5)=="30"){
				setPaymentTypeReadonly($paymentType.get(0),false);
				clearPaymentType($paymentType.get(0),"paymentType");
			}else{
				setPaymentTypeReadonly($paymentType.get(0),true);
				$paymentType.val("23");
				setCacheValue($paymentType.get(0));
				$paymentType1.val("2300");
				setCacheValue($paymentType1.get(0));
				$paymentType2.val("0000");
				setCacheValue($paymentType2.get(0));
//				$tr.find(":input[name='prpLpersonLossSumRealPay']").attr("readonly",false);
				$tr.find(":input[name='prpLpersonLossContractingScope']").val("23");
				var url = contextRootPath+"/common/verificationPaymentType.do";
				var contractingScope = 23;
				var paymentType = $paymentType.val();
				var paymentType1 = $paymentType1.val();
				var paymentType2 = $paymentType2.val();
				var codeType ="paymentType2";
				$.ajax({
					type:"get",
			 		url:url,
			 		cache:false,
			 		dataType:"json",
			 		data:"contractingScope="+contractingScope+"&paymentType="+paymentType+"&paymentType1="+paymentType1+"&paymentType2="+paymentType2+"&codeType="+codeType,
					success:function(data){
						if(data.message=="true"){
							setPaymentType2(data,field);
						}
					}
				});
			}
		}else{
			setPaymentTypeReadonly(field,false);
			clearPaymentType(field,"paymentType");
		}
	}
	
}
/** 计算未住院日数 */
function sumHospitalDays(field){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var $paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']");
	if($paymentType.val()=="22"){
		var $notHospitalDays = $tr.find(":input[name='prpLpersonLossNotHospitalDays']");
		var $paymentType1 = $tr.find(":input[name='prpLpersonLossPaymentType1']");
		var fractureSite = $tr.find(":input[name='prpLpersonLossFractureSite']").find("option:selected").attr("title");
		if($.trim($paymentType1.val())!=""&&fractureSite!=""){
			if($paymentType1.val().length>1&&$.isNumeric($paymentType1.val())){
				var type1Value = $paymentType1.val().substring($paymentType1.val().length-2,$paymentType1.val().length);
				var days = $parseInt(fractureSite)-$parseInt(type1Value)>0?$parseInt(fractureSite)-$parseInt(type1Value):0;
				$notHospitalDays.val(days);
			}
		}
	}
	sumRealPay(field);
}
/** 效验理算规则 */
function checkpaymentType(field,codeType){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	if(codeType=="paymentType"){
		var $paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']");
		if($paymentType.val()=="22"){
			setPaymentFractureReadonly(field,false);
		}else{
			setPaymentFractureReadonly(field,true);
		}
//		var $sumRealPay = $tr.find(":input[name='prpLpersonLossSumRealPay']");
//		if($paymentType.val()=="01"||$paymentType.val()=="21"||$paymentType.val()=="23"){
//			$sumRealPay.attr("readonly",false);
//		}else{
//			$sumRealPay.attr("readonly",true);
//		}
		setDeathDisplay(field);
	}else if(codeType=="paymentType1"){
		sumHospitalDays(field);
	}
	sumRealPay(field);
}
/**
 * 计算赔付金额 意健险理算公式 01-住院醫療(HG) 人工輸入金額 02-身故 保險金額 03-殘廢或重大燒燙傷 保險金額 * 殘廢給付比例%
 * 21-醫療限額（實支實付） 人工輸入金額 22-Min【（住院日數 * 醫療日額）＋（未住院日數 * 醫療日額* 骨折給付比例），保險金額】
 * 23-費用補償 人工輸入金額
 */
function sumRealPay(field){
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
	if(undefined!=fm.editType.value && fm.editType.value=="SHOW"){
		return;
	}
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var $paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']");
	var amount = getAmount(field);
	var sumRealPayValue = 0;
	if($paymentType.val()=="02"){
		sumRealPayValue = amount;
	}else if($paymentType.val()=="03"){
		var $PaymentRate = $tr.find(":input[name='prpLpersonLossPaymentRate']");
		sumRealPayValue = amount * $parseFloat($PaymentRate.val())/100;
	}else if($paymentType.val()=="22"){
		var $notHospitalDays = $tr.find(":input[name='prpLpersonLossNotHospitalDays']");
		var $paymentType1 = $tr.find(":input[name='prpLpersonLossPaymentType1']");
		var fractureSite = $tr.find(":input[name='prpLpersonLossFractureSite']").find("option:selected").attr("title");
		var type1Value = 0;
		if($.trim($paymentType1.val())!=""){
			if($paymentType1.val().length>1&&$.isNumeric($paymentType1.val())){
				type1Value = $paymentType1.val().substring($paymentType1.val().length-2,$paymentType1.val().length);
			}
		}
		var fractureDegree = $tr.find(":input[name='prpLpersonLossFractureDegree']").find("option:selected").attr("title");
		var daySum = getDayAmount(field,"dayAmount");
		//保额赔付倍数
		var maxRealPay = daySum * getDayAmount(field,"coverageratio");
		if(maxRealPay==0){
			maxRealPay = amount;
		}
		sumRealPayValue = $parseFloat(type1Value)*daySum+$parseFloat($notHospitalDays.val())*daySum*$parseFloat(fractureDegree)/100;
		if(sumRealPayValue<maxRealPay){
			sumRealPayValue = sumRealPayValue;
		}else{
			sumRealPayValue = maxRealPay;
		}
	}
	var $sumRealPay = $tr.find(":input[name='prpLpersonLossSumRealPay']");
	var currency = $tr.find(":input[name='prpLpersonLossCurrency']").val();
	var $sumLoss = $tr.find(":input[name='prpLpersonLossSumLoss']");
	if($.isNumeric(sumRealPayValue)){
		sumRealPayValue = pointTwo(sumRealPayValue,currency);
		$sumRealPay.val(sumRealPayValue);
		$sumLoss.val(sumRealPayValue);
	}else{
		$sumRealPay.val(0);
		$sumLoss.val(0);
	}
	countSumRealPay(field);
}
/**
 * 计算保险金额
 * @param field
 * @return
 */
function getAmount(field){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var $itemKindNo = $tr.find(":input[name='prpLpersonLossItemKindNo']");
	var amount = 0;
	if($itemKindNo.val()!=""){
		var $span_prpCitemKind = $("span[name='span_prpCitemKind']").find(":input[name='itemKindNo'][value='"+$itemKindNo.val()+"']").parent();
		var $amount= $span_prpCitemKind.find(":input[name='amount']");
		var $coverageratio= $span_prpCitemKind.find(":input[name='coverageratio']");
		//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
		var $riskCode = $(":input[name='prpLcompensateRiskCode']");
		var $rulMultiplier="";
		if($riskCode.val()=="TA"){
			debugger;
			var prpLpersonLossKindCode = $tr.find(":input[name='prpLpersonLossKindCode']").val();//險別 
			if(prpLpersonLossKindCode=="TR47"){

				var $prpLclaimAddressCode = $("input[name='prpLcompensateAddressCode']");
				var $span_prpDpolicyRules = $("span[name='span_prpDpolicyRules']").find(":input[name='init_data_rulCode'][value='"+$prpLclaimAddressCode.val()+"']").parent();
				$rulMultiplier= $span_prpDpolicyRules.find(":input[name='init_data_rulMultiplier']").val();
				if($rulMultiplier!=""){
					$coverageratio.val($rulMultiplier);
				}
			}
		}
		var coverageratio = "1";
		if($coverageratio.length > 0 && $coverageratio.val().length > 0){
			coverageratio = parseFloat($coverageratio.val());
		}
		//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
		amount = $parseFloat($amount.val()) * coverageratio;
	}
	if(amount==0){
		amount = $parseFloat($(":input[name='prpLcompensateSumAmount']").val());
	}
	return amount;
}
/**
 * 计算醫療日額
 * @param field
 * @return
 */
function getDayAmount(field,name){
	var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var $itemKindNo = $tr.find(":input[name='prpLpersonLossItemKindNo']");
	var amount = 0;
	if($itemKindNo.val()!=""){
		var $span_prpCitemKind = $("span[name='span_prpCitemKind']").find(":input[name='itemKindNo'][value='"+$itemKindNo.val()+"']").parent();
		var $amount= $span_prpCitemKind.find(":input[name='"+name+"']");
		amount = $parseFloat($amount.val());
	}
	return amount;
}
/** 统计赔款中金额 */
function countSumRealPay(field){
	if(field!=null&&field!=undefined){
		var amount = getAmount(field);
		var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
		var $paymentType = $tr.find(":input[name='prpLpersonLossPaymentType']");
		var $sumRealPay = $tr.find(":input[name='prpLpersonLossSumRealPay']");
		var $sumRealPayNTD = $tr.find(":input[name='prpLpersonLossSumRealPayNTD']");
		var $ExchRate = $tr.find(":input[name='prpLpersonLossExchRate']");
		var currency = $tr.find(":input[name='prpLpersonLossCurrency']").val();
		$sumRealPay.val(pointTwo($parseFloat($sumRealPay.val()),currency));
		var $sumLoss = $tr.find(":input[name='prpLpersonLossSumLoss']");
		$sumLoss.val($sumRealPay.val());
		var sumRealPayNTD = $parseFloat($sumRealPay.val()) * $parseFloat($ExchRate.val());
		$sumRealPayNTD.val(pointTwo(sumRealPayNTD));
		if($paymentType.val()=="01"||$paymentType.val()=="21"||$paymentType.val()=="23"){
			//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
			var prpLpersonLossKindCode = $tr.find(":input[name='prpLpersonLossKindCode']").val();//險別 
			if(prpLpersonLossKindCode=="PAF4"){
				var hospDays = fm.prpLcompensateHospitalizedDays.value;
				amount = amount * hospDays;
			} else
			//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
			if(sumRealPayNTD>$parseFloat(amount)){
				alert("賠付金額不能大於保險金額！請重新輸入。");
				$sumRealPay.val(0);
				$sumRealPayNTD.val(0);
			}
		}
		prpLpersonLossIsPayForOther(field);
	}
	$("#PersonLoss").find("tr[name='prpLpersonLossTr']").each(function(i,tr){
		var $sumRealPay1 = $(tr).find(":input[name='prpLpersonLossSumRealPay1']");
		var sumRealPay1 = 0;
		$(tr).find(":input[name='prpLpersonLossSumRealPayNTD']").each(function(){
			sumRealPay1 += $parseFloat(this.value);
		});
		$sumRealPay1.val(pointTwo(sumRealPay1));
	})
	sumPaid();
}

/**
 * mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核(理賠)
 */
function checkBeyondSumAmount() {
	var flag = true;
	var errorMessage = "";
	
	var $riskCode = $(":input[name='prpLcompensateRiskCode']");
	var prpLclaimHospitalizedDays="0";
	var prpLcompensateSumHospitalizedDay = "0";
	if($riskCode.val()=="PA"){
		var totalPAF5amount=0;
		var totalPAF6amount=0;
		var totalPAFamount =0;
		var errorMessageForHospitalize = "";
		$("tr[name='prpLpersonFeeLossTr']").each(function(i,n){
			var prpLpersonLossKindCode = $(this).find(":input[name='prpLpersonLossKindCode']").val();//險別 
			var prpLpersonLossAmount = $(this).find(":input[name='prpLpersonLossAmount']").val();// 保險金額
			var prpLpersonLossSumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']").val();//賠付金額
			var prpLpersonLossPaf4SumLossHit = $(":input[name='prpLpersonLossPaf4SumLossHit']");//paf4之前已經付過的總費用
			if("PAF4"==prpLpersonLossKindCode ||"PAF5"==prpLpersonLossKindCode||"PAF6"==prpLpersonLossKindCode||"PAF7"==prpLpersonLossKindCode){
				if("PAF4"==prpLpersonLossKindCode ){
					prpLcompensateHospitalizedDays = fm.prpLcompensateHospitalizedDays;//本次住院天數
					prpLcompensateSumHospitalizedDay = fm.prpLcompensateSumHospitalizedDay;//本次事故累計住院天數(不含本次):
					
					if(undefined!=prpLcompensateSumHospitalizedDay && null!=prpLcompensateSumHospitalizedDay 
							&& ""==prpLcompensateSumHospitalizedDay.value && !(isNaN(prpLcompensateSumHospitalizedDay.value))){
						prpLcompensateSumHospitalizedDay.value = "0";
					}
					if(undefined!=prpLcompensateHospitalizedDays && null!=prpLcompensateHospitalizedDays 
							&& ""==prpLcompensateHospitalizedDays.value && !(isNaN(prpLcompensateHospitalizedDays.value))){
						if(""!=errorMessageForHospitalize){
							errorMessageForHospitalize+="\r\n";
						}
						errorMessageForHospitalize +="請輸入本次住院天數";
						flag = false;
					}else{
//						totalPAFamount += parseInt(prpLpersonLossAmount,10) * (parseFloat(prpLcompensateHospitalizedDays.value)+parseFloat(prpLcompensateSumHospitalizedDay.value));
						totalPAFamount += parseInt(prpLpersonLossSumRealPay,10); 
						if(parseInt(prpLpersonLossSumRealPay,10)> parseInt(prpLpersonLossAmount,10) * (parseFloat(prpLcompensateHospitalizedDays.value)+parseFloat(prpLcompensateSumHospitalizedDay.value)) - parseFloat(prpLpersonLossPaf4SumLossHit.val())){
							if(""!=errorMessageForHospitalize){
								errorMessageForHospitalize+="\r\n";
							}
//							errorMessageForHospitalize += prpLpersonLossKindCode+"險別賠付預估金額("+prpLpersonLossSumRealPay+")應小於等於("+prpLpersonLossAmount+")X("+parseFloat(prpLcompensateHospitalizedDays.value)+"+"+parseFloat(prpLcompensateSumHospitalizedDay.value)+")-"+ parseFloat(prpLpersonLossPaf4SumLossHit.val())+")="+(parseInt(prpLpersonLossAmount,10) * (parseFloat(prpLcompensateHospitalizedDays.value)+parseFloat(prpLcompensateSumHospitalizedDay.value)) - parseFloat(prpLpersonLossPaf4SumLossHit.val()))+" ‘本次住院天數×保險日額(本次數+累積次數)’請檢核後重新輸入。"; //估损金额不能大於保额
							errorMessageForHospitalize += prpLpersonLossKindCode+"險別賠付預估金額應小於等於 ‘本次住院天數×保險金額(本次數+累積次數)’，請檢核後重新輸入。"; //估损金额不能大於保额
							flag = false;
						}
					}
				}
				if("PAF5"==prpLpersonLossKindCode ){
					totalPAF5amount+= parseInt(prpLpersonLossSumRealPay,10);
					totalPAFamount += parseInt(prpLpersonLossSumRealPay,10);
					if(parseInt(prpLpersonLossSumRealPay,10)>parseInt(prpLpersonLossAmount,10)){
						if(""!=errorMessageForHospitalize){
							errorMessageForHospitalize+="\r\n";
						}
//						errorMessageForHospitalize += prpLpersonLossKindCode+"險別賠付預估金額("+prpLpersonLossSumRealPay+")應小於等於 ‘保險日額("+prpLpersonLossAmount+")’請檢核後重新輸入。"; //估损金额不能大於保额
						errorMessageForHospitalize += prpLpersonLossKindCode+"險別賠付預估金額應小於等於 "+prpLpersonLossKindCode+"‘保險金額’，請檢核後重新輸入。"; //估损金额不能大於保额
						flag = false;
					}
				}
				if("PAF6"==prpLpersonLossKindCode ){
					totalPAFamount += parseInt(prpLpersonLossSumRealPay,10);
					totalPAF6amount+= parseInt(prpLpersonLossSumRealPay,10);
					if(parseInt(prpLpersonLossSumRealPay,10)>parseInt(prpLpersonLossAmount,10)){
						if(""!=errorMessageForHospitalize){
							errorMessageForHospitalize+="\r\n";
						}
//						errorMessageForHospitalize += prpLpersonLossKindCode+"險別賠付預估金額("+prpLpersonLossSumRealPay+")應小於等於 ‘保險日額("+prpLpersonLossAmount+")’請檢核後重新輸入。"; //估损金额不能大於保额
						errorMessageForHospitalize += prpLpersonLossKindCode+"險別賠付預估金額應小於等於 "+prpLpersonLossKindCode+"‘保險金額’，請檢核後重新輸入。"; //估损金额不能大於保额
						flag = false;
					}
				}
				if("PAF7"==prpLpersonLossKindCode ){
					if(""!=errorMessageForHospitalize){
						errorMessageForHospitalize+="\r\n";
					}
					errorMessageForHospitalize += prpLpersonLossKindCode+"險別不應存在’，請刪除後重新送出。"; //PAF7 找保單要
					flag = false;
				}
			}
		});
		var obj_PAF5SL = $(":input[name='PAF5_SUMLOSS']");
		var obj_PAF6SL = $(":input[name='PAF6_SUMLOSS']");
		var obj_PAF456 = $(":input[name='PAF456_SUMLOSS']");
		var obj_PAF5 = $(":input[name='PAF5_AMOUNT']");
		var obj_PAF6 = $(":input[name='PAF6_AMOUNT']");
		var obj_PAF7 = $(":input[name='PAF7_AMOUNT']");
		var PAF5_SUMLOSS = undefined!=obj_PAF5SL && null!=obj_PAF5SL && ""!=obj_PAF5SL.val()?obj_PAF5SL.val():0.0;
		var PAF6_SUMLOSS = undefined!=obj_PAF6SL && null!=obj_PAF6SL && ""!=obj_PAF6SL.val()?obj_PAF6SL.val():0.0;
		var PAF456_SUMLOSS = undefined!=obj_PAF456 && null!=obj_PAF456 && ""!=obj_PAF456.val()?obj_PAF456.val():0.0;
		var PAF5_AMOUNT = undefined!=obj_PAF5 && null!=obj_PAF5 && ""!=obj_PAF5.val()?obj_PAF5.val():0.0;
		var PAF6_AMOUNT = undefined!=obj_PAF6 && null!=obj_PAF6 && ""!=obj_PAF6.val()?obj_PAF6.val():0.0;
		var PAF7_AMOUNT = undefined!=obj_PAF7 && null!=obj_PAF7 && ""!=obj_PAF7.val()?obj_PAF7.val():0.0;
		//alert("PAF456_SUMLOSS:"+PAF456_SUMLOSS+"/PAF7_AMOUNT:"+PAF7_AMOUNT);
		if((parseInt(totalPAF5amount,10)+parseInt(PAF5_SUMLOSS,10)) > parseInt(PAF5_AMOUNT,10)){
			if(""!=errorMessageForHospitalize){
				errorMessageForHospitalize+="\r\n";
			}
//			errorMessageForHospitalize+= "已經核賠("+PAF5_SUMLOSS+")本次預計給付金額("+totalPAF5amount+")的加總("+(parseInt(totalPAF5amount,10)+parseInt(PAF5_SUMLOSS,10))+")超過 (>) 保單("+fm.policyno.value+")金額估損總金額("+PAF5_AMOUNT+")。";
			errorMessageForHospitalize+= "本次預計給付金額的加總超過PAF5保單金額估損總金額，請檢核後重新輸入。";
			flag = false;
		}
		if((parseInt(totalPAF6amount,10)+parseInt(PAF6_SUMLOSS,10)) > parseInt(PAF6_AMOUNT,10)){
			if(""!=errorMessageForHospitalize){
				errorMessageForHospitalize+="\r\n";
			}
//			errorMessageForHospitalize+= "已經核賠("+PAF6_SUMLOSS+")本次預計給付金額("+totalPAF6amount+")的加總("+(parseInt(totalPAF6amount,10)+parseInt(PAF6_SUMLOSS,10))+")超過 (>) 保單("+fm.policyno.value+")金額估損總金額("+PAF6_AMOUNT+")。";
			errorMessageForHospitalize+= "本次預計給付金額的加總超過PAF6保單金額估損總金額，請檢核後重新輸入。";
			flag = false;
		}
		if((parseInt(totalPAFamount,10)+parseInt(PAF456_SUMLOSS,10)) > parseInt(PAF7_AMOUNT,10)){
//			errorMessageForHospitalize+= "\r\n已經核賠("+PAF456_SUMLOSS+")本次預計給付金額("+totalPAFamount+")的加總("+(parseInt(totalPAFamount,10)+parseInt(PAF456_SUMLOSS,10))+")超過 (>) 保單("+fm.policyno.value+")金額估損總金額("+PAF7_AMOUNT+")。";
			errorMessageForHospitalize+= "\r\n本次預計給付金額的加總超過保單金額估損總金額，請檢核後重新輸入。";
			flag = false;
		}
		
		if(""!=errorMessageForHospitalize){//errorMessage在原本fun checkBeyondSumAmount內分開使用 所以跟著相同邏輯存errMsg
			errorMessage = errorMessageForHospitalize;//PA優先先出這個錯誤訊息
		}
	}
	
	if(errorMessage != ""){
		alert(errorMessage);
	}
	return flag;
}
//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
/**
 * 判断赔付人员身份证号是否唯一
 * 
 * @param field
 * @return
 */
function checkPersonIdentifyNumber(field){
	var $tr = $(field).parents("tr[name='prpLpersonLossTr']");
	var identifyNumber = $tr.find(":input[name='prpLpersonLossIdentifyNumber']");
	if(identifyNumber.val()==""){
		$tr.find(":input[name='prpLpersonLossMaxPaid']").val("0");
	 	$tr.find(":input[name='prpLpersonLossHisPaid']").val("0");
		return true;
	}
	var prpLpersonLossSex = $tr.find(":input[name='prpLpersonLossSex']");
	if(!checkIdentifyNumber(identifyNumber.val(),prpLpersonLossSex.val())){
		identifyNumber.val("");
		$tr.find(":input[name='prpLpersonLossMaxPaid']").val("0");
	 	$tr.find(":input[name='prpLpersonLossHisPaid']").val("0");
		alert("請錄入合法的身份證訊息！");
		return false;
	}
	$("tr[name='prpLpersonLossTr']").not($tr).each(function(i,n){
		if(i>0){
			var $identifyNumber = $(n).find(":input[name='prpLpersonLossIdentifyNumber']");
			if($identifyNumber.val()==identifyNumber.val()){
				alert("此人在第"+i+"條訊息中存在，請重新錄入");
				$tr.find(":input[name='prpLpersonLossMaxPaid']").val("0");
			 	$tr.find(":input[name='prpLpersonLossHisPaid']").val("0");
			 	identifyNumber.val("");
				return false;
			}
		}
	});
	var policyNo = $(":input[name='prpLcompensatePolicyNo']").val();
	// 查询历史赔付信息
	if(identifyNumber.val()!=""){
		var url = contextRootPath+"/compensate/compensatePersonHisPaid.do";
	     $.ajax({
			type:"get",
	 		url:url,
	 		cache:false,
	 		dataType:"json",
			data:"prpLcompensatePolicyNo="+policyNo+"&prpLpersonLossIdentifyNumber="+field.value,
			success:function(data){
	    	 	$tr.find(":input[name='prpLpersonLossMaxPaid']").val(data.maxPaid);
	    	 	$tr.find(":input[name='prpLpersonLossHisPaid']").val(data.hisPaid);
			}
		});
	}
}
/** 转换成浮点型数据 */
function $parseFloat(value){
	if($.isNumeric(value)){
		return parseFloat(value);
	}
	return 0;
}
/** 转换成整型数据 */
function $parseInt(value){
	if($.isNumeric(value)){
		return parseInt(value);
	}
	return 0;
}
/**
 * 提交的时候效验人伤信息
 * @return
 */
function checkPersonLoss(){
	var messages = "";
	$("tr[name='prpLpersonLossTr']").each(function (i,n){
		if(i>0){
			var $personName = $(n).find(":input[name='prpLpersonLossPersonName']");
			if($personName.val()==""){
				messages += "第"+i+"條請錄入人員姓名！\n";
			}
			var $age = $(n).find(":input[name='prpLpersonLossAge']");
			if($age.val()==""){
				messages += "第"+i+"條請錄入人員年齡！\n";
			}
			if($parseInt($age.val())<0){
				messages += "第"+i+"條錄入人員年齡不正確，請重新錄入！\n";
			}
			var $identifyNumber = $(n).find(":input[name='prpLpersonLossIdentifyNumber']");
			if($identifyNumber.val()==""){
				messages += "第"+i+"條請錄入人員身份證號！\n";
			}
			var sumRealPay1 = 0;
			$(n).find("tr[name='prpLpersonFeeLossPaymentTr']").each(function (){
				var $kindCode = $(this).find(":input[name='prpLpersonLossKindCode']");
				if($kindCode.val()==""){
					messages += "第"+i+"條請錄入險別代碼！\n";
				}
				var $kindName = $(this).find(":input[name='prpLpersonLossKindName']");
				if($kindName.val()==""){
					messages += "第"+i+"條請錄入險別名称！\n";
				}
				var $paymentType = $(this).find(":input[name='prpLpersonLossPaymentType']");
				if($paymentType.val()==""){
					messages += "第"+i+"條請錄入給付類別！\n";
				}
				var $paymentType1 = $(this).find(":input[name='prpLpersonLossPaymentType1']");
				if($paymentType1.val()==""){
					messages += "第"+i+"條請錄入給付類別1！\n";
				}
				var $paymentType2 = $(this).find(":input[name='prpLpersonLossPaymentType2']");
				if($paymentType2.val()==""){
					messages += "第"+i+"條請錄入給付類別2！\n";
				}
				var $notHospitalDays = $(this).find(":input[name='prpLpersonLossNotHospitalDays']");
				if($notHospitalDays.val()!=""){
					if($.isNumeric($notHospitalDays.val())){
						if($parseInt($notHospitalDays.val())<0){
							messages += "第"+i+"條未住院日數不能錄入負數！\n";
						}
					}else{
						messages += "第"+i+"條未住院日數請錄入數字！\n";
					}
				}
				var $sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']");
				var $sumRealPayNTD = $(this).find(":input[name='prpLpersonLossSumRealPayNTD']");
				if($sumRealPay.val()==""){
					messages += "第"+i+"條請錄入賠付金額 ！\n";
				}else if(!$.isNumeric($sumRealPay.val())){
					messages += "第"+i+"條賠付金額 必須為數字！\n";
				}else{
					sumRealPay1 += $parseFloat($sumRealPayNTD.val());
				}
//					var $payObjectSerialNo = $(this).find(":input[name='prpLpersonLossPayObjectSerialNo']");
//					if($payObjectSerialNo.val()==""){
//						messages += "第"+i+"條請錄入賠付對象訊息 ！\n";
//					}
			})
			var $sumRealPay1 = $(n).find(":input[name='prpLpersonLossSumRealPay1']");
			if(sumRealPay1!=$parseFloat($sumRealPay1.val())){
				messages += "第"+i+"條賠付金額和總金額不相等 ！\n";
			}
		}
	});
	if(messages!=""){
		alert(messages);
		return false;
	}
	return true;
}
/**
 * 效验核定损失金额和赔付对象金额是否相等
 * @return
 */
function checkPayObjectInfo(){
	var $PayAmount = $(":input[name='prpLpayObjectInfoPayAmount']");
	var amounts = new Array($PayAmount.length);
	for(var i=0;i<amounts.length;i++){
		amounts[i] = 0;
	}
	var messages = "";
	$(":input[name='prpLpayObjectInfoOwnerName']").each(function (i,n){
		if(i>0){
			if(n.value==""){
				messages += "請爲賠款給付對象訊息  賠付對象 "+i+" 錄入賠付對象名稱\n";
			}
		}
	})
	var $OwnerShip = $(":input[name='prpLpayObjectInfoOwnerShip']");
	$(":input[name='prpLpayObjectInfoAccountCode']").each(function (i,n){
		if(i>0){
			if($OwnerShip.get(i).value=="B"){
				if(n.value==""){
					messages += "請爲賠款給付對象訊息  賠付對象 "+i+" 錄入匯款帳號\n";
				}
			}
		}
	})
	var $payCurrency = $(":input[name='prpLpayObjectInfoAccountCurrency']");
	var $payExchRate = $(":input[name='prpLpayObjectInfoExchRate']");
	$("tr[name='prpLpersonLossTr']").each(function (i,n){
		if(i>0){
			$(n).find("tr[name='prpLpersonFeeLossPaymentTr']").each(function (){
				var $SumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']");
				var $PayObjectSerialNo = $(this).find(":input[name='prpLpersonLossPayObjectSerialNo']");
				var $Currency = $(this).find(":input[name='prpLpersonLossCurrency']");
				var $ExchRate = $(this).find(":input[name='prpLpersonLossExchRate']");
				var payObjectAmount = 0;
				if($PayObjectSerialNo.val()!=""){
					var payObjectSerialNo = $PayObjectSerialNo.val().split(";");
					for(var j=0;j<payObjectSerialNo.length;j++){
						var serialNo = payObjectSerialNo[j].split(":");
						amounts[serialNo[0]] += $parseFloat(serialNo[1]);
						payObjectAmount += $parseFloat(serialNo[1]);
						//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核
						if(undefined!=$payCurrency[serialNo[0]] && null!=$payCurrency[serialNo[0]]){
							if($payCurrency[serialNo[0]].value!=$Currency.val()){
								messages += "第"+i+"條賠付金額幣別和賠付對象訊息金額支付幣別不相同！\n";
							}else if($parseFloat($payExchRate[serialNo[0]].value)!=$parseFloat($ExchRate.val())){
								messages += "第"+i+"條賠付金額匯率和賠付對象訊息金額支付匯率不相同！\n";
							}
						}
					}
				}
				if(Math.abs(payObjectAmount - $parseFloat($SumRealPay.val()))>0.001){
					messages += "第"+i+"條賠付金額和賠付對象訊息金額不相等！\n";
				}
			})
		}
	})
	$PayAmount.each(function (i,n){
		if(i>0){
			if(amounts[i]!=$parseFloat(n.value)){
				messages += "第"+i+"條賠付對象訊息和賠付金額不相等！\n";
			}
		}
	})
	if(messages!=""){
		alert(messages);
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
	var kindCode;
	var sumRealPayNTD;
	$("#PersonLoss").find("tr[name='prpLpersonFeeLossPaymentTr']").each(function(){
		kindCode = $(this).find(":input[name='prpLpersonLossKindCode']").val();
		sumRealPayNTD = $(this).find(":input[name='prpLpersonLossSumRealPayNTD']").val();
		personKindArray.push(kindCode);
		personKindPayArray.push(sumRealPayNTD);
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