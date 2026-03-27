/*****************************************************************************
 * DESC       ：实赔的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-05-19
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 费用相关域整理
 *@param       无
 *@return      无
 */

function calChargeAmount(field) {
	var $prpLchargeObject = $(field).parents("tr[name='prpLchargeObject']");
	var $ChargeCode = $prpLchargeObject.find(":input[name='prpLchargeChargeCode']");
	var $ChargeAmount = $prpLchargeObject.find(":input[name='prpLchargeChargeAmount']");
	var $ChargeReport = $prpLchargeObject.find(":input[name='prpLchargeChargeReport']");
	var $Currency = $prpLchargeObject.find(":input[name='prpLchargeCurrency']");
	if($ChargeCode.val().length>0){
		if($parseFloat($ChargeAmount.val(),0)== 0){
			$ChargeAmount.val(pointTwo($ChargeReport.val(),$Currency.val()));
		}
	}else{
		$ChargeAmount.val(0);
	}
	if($parseFloat($ChargeAmount.val(),0)>$parseFloat($ChargeReport.val(),0)){
		alert("支付費用金額不能大於費用金額！");
		$ChargeAmount.val(0);
	}
	var exchRate = $parseFloat($prpLchargeObject.find(":input[name='prpLchargeExchRate']").val(),1);
	$prpLchargeObject.find(":input[name='prpLchargeChargeAmountNTD']").val(pointTwo($ChargeAmount.val()*exchRate));
	calFund();
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
	//增加收到客户索赔申请已过天数提示 begin】
	var passDayList = document.getElementsByName("passDay");
	if (passDayList.length > 0 && passDayList[0] != null && passDayList[0].value != 0) {
		alert(i18n.commonAcci.compensate.receiveCustomerOver + passDayList[0].value + i18n.commonAcci.compensate.dayPleaseDeal); // 收到客户索赔申请已过      //天，请尽快处理！
	}
	//增加收到客户索赔申请已过天数提示 end】
	//判断是否是共保单
//	var chiefFlag = fm.chiefflag.value;
//	var coinsFlag = fm.coinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var message = "";
	var payFee = parseInt(fm.payFee.value);
	var delinquentfeeCase = fm.delinquentfeeCase.value;

	if (payFee == -1) {
		alert(i18n.claim.premiumNotPaid); //此保单保费未缴！\n
	} else if (payFee == 0) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
		message = message + delinquentfeeCase + "\n";
	}
//	if (coinsFlag == 1) {
//		alert(i18n.commonAcci.compensate.policyPayGenerateInfoTotalLoss1); //本保单为主共保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
//	}
//	if (coinsFlag == 2 ) {
//		alert(i18n.commonAcci.compensate.policyPayGenerateInfoTotalLoss3); //本保单为从共保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
//	}
//	if (coinsFlag == 3) {
//		alert(i18n.commonAcci.compensate.policyPayGenerateInfoTotalLoss4); //本保单为从联保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
//	}
	if (tempReinsFlag != 0) {
		message = message + i18n.check.proBusiness; //此保单有临分业务！
	}
	if (message.length > 0) {
		alert(message);
	}
	//reasion:理算初始化时，签单币别不是CNY时，弹出当前兑换率
	if (fm.BaseCurrency1.value != CURRENCYINFO.LOCAL_CURRENCY) {
		alert(i18n.claim.singleCurrency + fm.BaseCurrency1.value + i18n.claim.useSparingly + fm.ExchRate1.value); //此案件签单币别为         //，不是CNY，请慎重处理！\n当前兑换率为
	}
	$("#Person").find("tr[name='personObject']").each(function(){
		var sumRealPayNTD = 0;
		$(this).find(":input[name='prpLpersonLossSumRealPayNTD']").each(function(i,n){
			sumRealPayNTD += $parseFloat(n.value,0);
		});
		$(this).find(":input[name='prpLpersonLossSumRealPay1NTD']").val(pointTwo(sumRealPayNTD));
	});
	calFund();
	return true;
}
function GenerateCtextFlag(flag) {
	fm.GenerateCompensateFlag.value = flag;
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

function setChangelossChargeFlag() {
	//目前只为了联共保判断而增加的,表示变化没有被联共保操作过
	if (fm.all("lossOrChargeHaveChanged") != null) {
		fm.lossOrChargeHaveChanged.value = '1';
	}
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	//如果结案类型是拒赔，则不得赔付标的损失赔偿（可以赔付赔款费用）begin
	if ("2" == fm.prpLcompensateFinallyFlag.value) {
		if (fm.prpLcompensateSumThisPaid.value > 0) {
			alert(i18n.compensate.notPay); //结案类型是拒赔，不得赔付标的损失赔偿（可以赔付赔款费用）!
			return false;
		}
	}
	// 如果结案类型是拒赔，则不得赔付标的损失赔偿（可以赔付赔款费用）end
	//提交时对送审进行判断
	if (saveType == "4" && checkUndwrt() == false) {
		return false;
	}
	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	//textarea文本框设置值
	//var context = fm.prpLctextContextInnerHTML.value;
	//if (context.length < 1) {
		//errorMessage = errorMessage + "赔款计算过程不允许为空\n"; //赔款计算过程不允许为空\n
	//}

	// 费用险别不能为空 begin
	var prpLchargeKindCodeList = document.getElementsByName("prpLchargeKindCode");
	var prpLchargeKindNameList = document.getElementsByName("prpLchargeKindName");
	for (var i = 1; i < prpLchargeKindCodeList.length; i++) {
		if (prpLchargeKindCodeList[i].value.replace(' ', '') == "" || prpLchargeKindNameList[i].value.replace(' ', '') == "") {
			errorMessage = errorMessage + i18n.compensate.riskNotEmpty; //险别信息不能为空！\n
		}
	}
	
	//reasion:增加联共保的判断
//	if (fm.chiefflag.value == "1" || fm.chiefflag.value == '3') {
//		if (fm.all("lossOrChargeHaveChanged") != null && fm.lossOrChargeHaveChanged.value == '1') {
//			errorMessage = errorMessage + i18n.compensate.amountsChanged; //金额已发生变化，请选择'生成联共保分摊信息'按钮，重新生成联共保信息後再保存！
//		}
//	}

	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
//	if (!validateForm(fm, 'Engage_Data,lLoss_Data,PersonFeeLoss_Data,Person_Data,Charge_Data')) {
//		return false;
//	}
	var underWriteFlag = fm.prpLcompensateUnderWriteFlag.value;
	if (saveType == 4) {
		//判断理算报告是不能为空的。	
		var context2 = fm.prpLltextContextInnerHTML.value;
		var lltext2title = fm.tdLltextTitle.value;
		if (context2.length < 1) {
			errorMessage = errorMessage + lltext2title + i18n.js.notAllowedEmpty; //不允许为空\n
			alert(errorMessage)
			return false;
		}
		//赔付标的信息或赔付人员信息二者其一必须填写，否则不能提交实赔
		var $prpLlossDtoSerialNo = $(":input[name='prpLlossDtoSerialNo']");
		var $prpLchargeSerialNo = $(":input[name='prpLchargeSerialNo']");
		var $prpLpersonLossSerialNo = $(":input[name='prpLpersonLossSerialNo']");
		if ($prpLlossDtoSerialNo.length<=1 && $prpLchargeSerialNo.length <= 1&&$prpLpersonLossSerialNo.length<=1) {
			alert(i18n.compensate.claimBook); //赔款计算书中的赔付标的，赔款费用至少有一条记录!
			return false;
		}
		
		if(!checkPrpLloss()||!checkPersonLoss()||!checkPrpLcharge()||!checkPrpLpayObjectInfo()||!checkPayAmount()||!checkEarthquakeFund()){
			return false;
		}
		if(!checkKindPay()){
			return false;
		}
		if (confirm("案件最终 赔款金额为：" + fm.prpLcompensateSumThisPaid.value + " ,费用金额为：" + fm.prpLcompensateSumNoDutyFee.value + " ,请确认！")) {} else {
			undisablebutton();
			return false;
		}
		//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
		errorMessage = "";
		for (var i = 1; i < fm.prpLpayObjectInfoBeneficiaryPhone.length; i++) {
		var prpLpayObjectInfoBeneficiaryPhone =fm.prpLpayObjectInfoBeneficiaryPhone[i].value;
		if (prpLpayObjectInfoBeneficiaryPhone.length > 0) {
			 if (prpLpayObjectInfoBeneficiaryPhone.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
			    		errorMessage =errorMessage +"賠款給付對象訊息:受款人"+i+"電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
			    	errorMessage =errorMessage +"賠款給付對象訊息:受款人"+i+"電話有誤\n";
			      }
			 }
		    		
		 }
		}
		for (var i = 1; i < fm.prpLchargeBeneficiaryPhone.length; i++) {
			var prpLchargeBeneficiaryPhone =fm.prpLchargeBeneficiaryPhone[i].value;
			if (prpLchargeBeneficiaryPhone.length > 0) {
				 if (prpLchargeBeneficiaryPhone.substr(0, 2)=='09'){
				    	reg =/^09[0-9]{8}$/;
				    	  if(!reg.test(prpLchargeBeneficiaryPhone)){
				    		errorMessage =errorMessage +"賠款費用:受款人"+i+"電話有誤\n";
				    	}
				  } else {
				      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
				      if (!reg.test(prpLchargeBeneficiaryPhone)){
				    	errorMessage =errorMessage +"賠款費用:受款人"+i+"電話有誤\n";
				      }
				 }
			    		
			 }
			}
		for (var i = 1; i < fm.prpLpersonLossMobilePhone.length; i++) {
		var prpLpersonLossMobilePhone =fm.prpLpersonLossMobilePhone[i].value;
		if (prpLpersonLossMobilePhone.length > 0) {
		    	 var reg =/^09[0-9]{8}$/;
		    	  if(!reg.test(prpLpersonLossMobilePhone)){
		    		  errorMessage =errorMessage +"受害人"+i+"手機電話有誤 \n";
		    	}  	
		  }
		}
		if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		}//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end
	}
	//檢查分攤金額是否正確
//	if(saveType == '4' && (fm.chiefflag.value == "1" || fm.chiefflag.value == '3')){
//		if(!checkChargeAmount()){
//			return false;
//		}
//	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
//  fm.submit();
//  return true;
	
	var riskCode = $("input[name='prpLcompensateRiskCode']").val();
	var claimNo = $(":input[name='prpLcompensateClaimNo']").val();
	var prpLpayObjectInfoUniformNoAry=[];
	for (var i = 1; i < fm.prpLpayObjectInfoUniformNo.length; i++) {
		//var prpLpayObjectInfoCertificateCode = fm.prpLpayObjectInfoCertificateCode[i].value; //證件類型
		var prpLpayObjectInfoUniformNo = fm.prpLpayObjectInfoUniformNo[i].value; //證件代碼
		prpLpayObjectInfoUniformNoAry.push(prpLpayObjectInfoUniformNo);
	}
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
		return false;
	}
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
	
}

/**
 * 效验赔款的金额
 * @return
 */
function checkPrpLloss(){
	var message = "";
	var $prpLpayObjectInfoCurrency = $("#PrpLpayObjectInfo").find(":input[name='prpLpayObjectInfoCurrency']");
	$("#lLoss").find("tr[name='prpLlossObject']").each(function(i, prpLlossObject){
		var kindCode = $(prpLlossObject).find(":input[name='prpLlossDtoKindCode']").val();
		if(kindCode==""){
			message += "第"+(i+1)+"條賠付標的訊息險別不能為空！\n";
			return false;
		}
		var sumRealPay = $(prpLlossObject).find(":input[name='prpLlossDtoSumRealPay']").val();
		if(!$.isNumeric(sumRealPay)){
			message += "第"+(i+1)+"條賠付標的訊息赔偿金额必須是數字！\n";
			return false;
		}
		var payObjectSerialNo = $(prpLlossObject).find(":input[name='prpLlossDtoPayObjectSerialNo']").val();
		var currency = $(prpLlossObject).find(":input[name='prpLlossDtoCurrency']").val();
		var payObjectAmount = 0;
		if(payObjectSerialNo != ""){
			payObjectSerialNo = payObjectSerialNo.split(";");
			for(var j=0;j<payObjectSerialNo.length;j++){
				var serialNo = payObjectSerialNo[j].split(":");
				if(currency!=$prpLpayObjectInfoCurrency.get(parseInt(serialNo[0]-1)).value){
					message += "第" + (i + 1) + "條賠付標的訊息的賠償幣別和‘支付對象訊息’的支付幣別不同，請重新分配!";
					return false;
				}
				if($.isNumeric(serialNo[0])&&$.isNumeric(serialNo[1])){
//					amounts[serialNo[0]] += parseFloat(serialNo[1]);
					payObjectAmount += parseFloat(serialNo[1]);
				}
			}
		}
		if(payObjectAmount != parseFloat(sumRealPay)){
			message += +"CLM9999 PASS~"+"第"+(i+1)+"條賠付標的訊息賠付金額和賠付對象訊息金額不相等！\n";
		//CLM9999 柏樺千萬別送  這邊關掉賠付金額可以減少一些麻煩，但有可能後續會出事
			return false;
		}
	});
	if(message.length>0){
		alert(message);
		return false;
	}
	return true;
}
/**
 * 效验人伤
 * @return
 */
function checkPersonLoss(){
	var checkFlag = true;
    var $prpLpayObjectInfoCurrency = $("#PrpLpayObjectInfo").find(":input[name='prpLpayObjectInfoCurrency']");
    $("#Person").find("tr[name='personObject']").each(function (i, personObject) {
        var $personName = $(personObject).find(":input[name='prpLpersonLossPersonName']"); //人員姓名
        var $birthday = $(personObject).find(":input[name='prpLpersonLossBirthday']"); //出生日期
        var $certificateCode = $(personObject).find(":input[name='prpLpersonLossCertificateCode']");//證件類型
        var certificateCode = $certificateCode.val();
        var $identifyNumber = $(personObject).find(":input[name='prpLpersonLossIdentifyNumber']"); //身份证号
        var identifyNumber = $identifyNumber.val();
        var sex = $(personObject).find(":input[name='prpLpersonLossSex']").val(); //性别
        if ($.trim($personName.val()) == '') {
            checkFlag = alertMessage($personName[0], "第 " + (i + 1) + " 筆人員傷亡賠付訊息‘人員姓名’必須輸入!");
            return false; //跳出each
        } else if ($.trim($birthday.val()) == '') {
            checkFlag = alertMessage($birthday[0], "第 " + (i + 1) + " 筆人員傷亡賠付訊息‘出生年份’必須輸入!");
            return false; //跳出each
        } else {
            if(certificateCode == '01' || certificateCode =='02'){//證件類型為 身份證號，統一編號時需要校驗
                if($.trim(identifyNumber).length == 0){
                    checkFlag = alertMessage($identifyNumber[0], "第 " + (i + 1) + " 筆人員傷亡賠付訊息，證件類型為‘"+$certificateCode.find("option:selected").text()+"’,‘證件號碼’必須輸入!");
                    return false; //跳出each
                } else if((certificateCode == '01' && !checkIdentifyNumber(identifyNumber, sex))
                                || (certificateCode =='02' && !checkUniformNo(identifyNumber))){
                    checkFlag = alertMessage($identifyNumber[0], "第 " + (i + 1) + " 筆人員傷亡賠付訊息‘證件號碼’输入不正确!");
                    return false; //跳出each
                }
            }
            var $ll = $(personObject).find("tr[name='prpLpersonLossObject']");
            if($ll.length == 0){
                alert("第 " + (i + 1) + " 筆人員傷亡賠付訊息沒有錄入賠款費用訊息!");
                checkFlag = false;
                return false;
            }else{
                $ll.each(function (j, prpLpersonLossObject) {
                    var $prpLpersonLossKindCode = $(prpLpersonLossObject).find(":input[name='prpLpersonLossKindCode']"); //险别代码
                    var $prpLpersonLossLiabDetailCode = $(prpLpersonLossObject).find(":input[name='prpLpersonLossLiabDetailCode']"); //人伤费用类别代码
                    var $prpLpersonLossLiabDetailName = $(prpLpersonLossObject).find(":input[name='prpLpersonLossLiabDetailName']"); //人伤费用类别名称
                    var $sumRealPayNTD = $(this).find(":input[name='prpLpersonLossSumRealPayNTD']");
                    var $sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']");
                    var $currency = $(this).find(":input[name='prpLpersonLossCurrency']");
                    var $payObjectSerialNo = $(this).find(":input[name='prpLpersonLossPayObjectSerialNo']");
                    var payObjectSerialNo = $payObjectSerialNo.val();
                    if ($.trim($prpLpersonLossKindCode.val()) == '' ) {
                        checkFlag = alertMessage($prpLpersonLossKindCode[0], "第 " + (i + 1) + " 筆人員傷亡賠付訊息,第 " + (j + 1) + " 筆費用資訊 ‘險別’必須輸入!");
                        return false; //跳出each
                    } else if ($prpLpersonLossLiabDetailCode.val() == '' || $prpLpersonLossLiabDetailName.val() == '') {
                        checkFlag = alertMessage($prpLpersonLossLiabDetailCode[0], "第 " + (i + 1) + " 筆人員傷亡賠付訊息,第 " + (j + 1) + " 筆費用資訊 ‘費用類別’必須輸入!");
                        return false; //跳出子each
                    }else {
                        var sumRealPayNTD = 0;
                        if(payObjectSerialNo!=""){
                        	var payObjectValue = payObjectSerialNo.split(";");
	                        for (var k = 0; k < payObjectValue.length; k++) {
	                            var payObjectValueTemp = payObjectValue[k].split(":");
	                            if(parseInt(payObjectValueTemp[0]) > $prpLpayObjectInfoCurrency.length){
	                            	 alert("第" + (i + 1) + "筆人員傷亡賠付訊息,第 " + (j + 1) + " 筆費用資訊的賠付對象訊息錄入有誤，賠付對象 "+ payObjectValueTemp[0] +" 不存在!");
	                                 checkFlag = false;
	                                 return false;
	                            }
	                            if($currency.val()!=$prpLpayObjectInfoCurrency.get(parseInt(payObjectValueTemp[0]-1)).value){
	                            	 alert("第" + (i + 1) + "筆人員傷亡賠付訊息,第 " + (j + 1) + " 筆費用資訊的賠償幣別和‘支付對象訊息’的支付幣別不同，請重新分配!");
	                                 checkFlag = false;
	                                 return false;
	                            }
	                            sumRealPayNTD += parseFloat(payObjectValueTemp[1]);
	                        }
                        }
                        if (parseFloat($sumRealPay.val())-sumRealPayNTD != 0) {
                            alert("第" + (i + 1) + "筆人員傷亡賠付訊息,第 " + (j + 1) + " 筆費用資訊‘支付對象訊息’分配的理賠金額之和與‘賠償金額’不等，請重新分配!");
                            checkFlag = false;
                            return false;
                        }
                    }
                });
            }
            return checkFlag; //true 则continue each() false 则 break each()
        }
    });
    return checkFlag; //这个才是函数的校验结果
}
/***
 * 费用模块的校验
 * @returns {Boolean}
 */
function checkPrpLcharge() {
   var checkFlag = true;
   var $tbody = $("#Charge").children("tbody");
   var $all = $tbody.children();//总的已存在的费用
   var alltr_length = $tbody.children("tr").length;
   var clength = $("#Charge_Data").children("tbody").children().length;//每个复制数据块的tr个数
   var $prpLchargeChargeAmount =  $("#Charge").find(":input[name='prpLchargeChargeAmount']");
   var $prpLchargeChargeReport =  $("#Charge").find(":input[name='prpLchargeChargeReport']");
   var $prpLchargeCurrency =  $("#Charge").find(":input[name='prpLchargeCurrency']");
   for(var i = 0;i < (alltr_length/clength);i++){
       var $chargeObject = $all.slice(i*clength,(i+1)*clength);//取得被删除的那几行
       var $kindCode = $chargeObject.find(":input[name='prpLchargeKindCode']"); //险别代码
       var $chargeCode = $chargeObject.find(":input[name='prpLchargeChargeCode']"); //费用名称
       var $chargeName = $chargeObject.find(":input[name='prpLchargeChargeName']");
       var $ownerName = $chargeObject.find(":input[name='prpLchargeOwnerName']"); //賠付對象
       var $certificateCode = $chargeObject.find(":input[name='prpLchargeCertificateCode']"); //證件類型
       var certificateCode = $certificateCode.val();
       var $uniformNo = $chargeObject.find(":input[name='prpLchargeUniformNo']"); //ID/統一編號
       var uniformNo = $uniformNo.val();
       var feeSerialNo = $chargeObject.find(":input[name='prpLchargeFeeSerialNo']").val();
       var currency = $chargeObject.find(":input[name='prpLchargeCurrency']").val();
       var ownerShip = $chargeObject.find(":input[name='prpLchargeOwnerShip']").val(); //費用支付方式
       var bankCode = $chargeObject.find(":input[name='prpLchargeBankCode']").val(); //總行代號
       var bankName = $chargeObject.find(":input[name='prpLchargeBankName']").val(); //總行名稱
       var accountCode = $chargeObject.find(":input[name='prpLchargeAccountCode']").val(); //匯款帳號
       var customBankCode = $chargeObject.find(":input[name='prpLchargeCustomBankCode']").val(); //分行代號
       var customBankName = $chargeObject.find(":input[name='prpLchargeCustomBankName']").val(); //分行名稱
       var areaCode = $chargeObject.find(":input[name='prpLchargeAreaCode']"); //郵遞區號
       var courierAddress = $chargeObject.find(":input[name='prpLchargeCourierAddress']"); //郵遞地址
       //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
       if(undefined!=areaCode && null!=areaCode){
	       var oAreaCode2 = $.trim(areaCode.val());
	       var areaCode2 = $.trim(areaCode.val()).replace(/[^\d]/g,'');
	       if(oAreaCode2.length > 3){
		       alert("第 " + (i+1) + " 筆費用資訊‘郵遞區號’ 長度超過3位數!");
		       checkFlag = false;
		       break;
	       } else
	       if(oAreaCode2 != areaCode2){
		       alert("第 " + (i+1) + " 筆費用資訊‘郵遞區號’ 只能輸入數值!");
		       checkFlag = false;
		       break;
	       } 
       }
       //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
       if ($.trim($kindCode.val()) == '') {
           checkFlag = alertMessage($kindCode[0], "第 " + (i + 1) + " 筆費用資訊‘險別代碼’必須輸入!");
           break;
       } else if ($.trim($chargeCode.val()) == '' || $.trim($chargeName.val()) == '') {
           checkFlag = alertMessage($chargeCode[0], "第 " + (i + 1) + " 筆費用資訊‘費用代碼’、‘費用名稱’必須輸入!");
           break;
       } else if ($.trim($ownerName.val()) == '') {
           checkFlag = alertMessage($ownerName[0], "第 " + (i + 1) + " 筆費用資訊‘賠付對象’必須輸入!");
           break;
       } else if (ownerShip == 'B' && ($.trim(accountCode).length == 0|| $.trim(bankCode).length == 0|| $.trim(bankName).length == 0|| $.trim(customBankCode).length == 0|| $.trim(customBankName).length == 0)) {
           alert("第 " + (i + 1) + " 筆費用支付方式爲匯款，必須輸入費用支付帳戶資訊!");
           checkFlag = false;
           break;
       } else if (((ownerShip == 'B' && customBankCode !="9999") || ownerShip=='Q') && ($.trim(areaCode).length == 0 || $.trim(courierAddress).length == 0)) {
           alert("第 " + (i + 1) + " 筆費用資訊‘郵遞區號’、‘郵遞地址’必須輸入!");
           checkFlag = false;
           break;
       }else {
           if(certificateCode == '01' || certificateCode =='02'){//證件類型為 身份證號，統一編號時需要校驗
               if($.trim(uniformNo).length == 0){
                   checkFlag = alertMessage($uniformNo[0], "第 " + (i + 1) + " 筆費用資訊，證件類型為‘"+$certificateCode.find("option:selected").text()+"’,‘證件號碼’必須輸入!");
                   break;
               }else if((certificateCode == '01' && !checkIdentifyNumber(uniformNo, "9"))
                               || (certificateCode =='02' && !checkUniformNo(uniformNo))){
                   checkFlag = alertMessage($uniformNo[0], "第 " + (i + 1) + " 筆費用資訊‘證件號碼’输入不正确!");
                   break;
               }
           }
       }
       if(feeSerialNo!=""){
    	   if(!$.isNumeric(feeSerialNo)||parseInt(feeSerialNo)>$prpLchargeChargeAmount.length||parseInt(feeSerialNo)==i+1){
    		   alert("第 " + (i + 1) + " 筆費用資訊‘代扣費用序號’輸入不正確!");
               checkFlag = false;
               break;
    	   }else if(currency!=$prpLchargeCurrency.get(parseInt(feeSerialNo)-1).value){
    		   alert("第 " + (i + 1) + " 筆費用資訊‘賠償幣別’和第"+feeSerialNo+"條‘賠償幣別’相同!");
               checkFlag = false;
               break;
    	   }else if(parseFloat($prpLchargeChargeAmount.get(i).value)!= parseFloat($prpLchargeChargeReport.get(parseInt(feeSerialNo)-1).value)-parseFloat($prpLchargeChargeAmount.get(parseInt(feeSerialNo)-1).value)){
    		  alert("請爲費用資訊第  " + (i +1) + " 條錄入支付費用金額必須和第 "+feeSerialNo+" 條錄入的費用金額和支付費用金額之差相等");
    		  checkFlag = false;
              break;
    	   }
       }else if($chargeCode.val()=="B"&&feeSerialNo==""){
    	   alert("第 " + (i + 1) + " 筆費用資訊費用為稅金‘代扣費用序號’必須輸入!");
           checkFlag = false;
           break;
       }
   }
   
   return checkFlag;
}
/***
 * 赔付对象讯息校验
 * @returns {Boolean}
 */
function checkPrpLpayObjectInfo() {
    var checkFlag = true;
    $("#PrpLpayObjectInfo").find("tr[name='PrpLpayObjectInfo']").each(function (i) {
        var $ownerName = $(this).find(":input[name='prpLpayObjectInfoOwnerName']"); //賠付對象
        var paymentKind = $(this).find(":input[name='prpLpayObjectInfoPaymentKind']").val(); //費用類型
        var $certificateCode = $(this).find(":input[name='prpLpayObjectInfoCertificateCode']"); //證件類型
        var certificateCode = $certificateCode.val();
        var $uniformNo = $(this).find(":input[name='prpLpayObjectInfoUniformNo']"); //ID/統一編號
        var uniformNo = $uniformNo.val();
        var ownerShip = $(this).find(":input[name='prpLpayObjectInfoOwnerShip']").val(); //費用支付方式
        var beneficiaryPhone = $(this).find(":input[name='prpLpayObjectInfoBeneficiaryPhone']").val(); //受款人電話
        var bankCode = $(this).find(":input[name='prpLpayObjectInfoBankCode']").val(); //總行代號
        var bankName = $(this).find(":input[name='prpLpayObjectInfoBankName']").val(); //總行名稱
        var accountCode = $(this).find(":input[name='prpLpayObjectInfoAccountCode']").val(); //匯款帳號
        var customBankCode = $(this).find(":input[name='prpLpayObjectInfoCustomBankCode']").val(); //分行代號
        var customBankName = $(this).find(":input[name='prpLpayObjectInfoCustomBankName']").val(); //分行名稱
        var areaCode = $(this).find(":input[name='prpLpayObjectInfoAreaCode']").val(); //郵遞區號
        var courierAddress = $(this).find(":input[name='prpLpayObjectInfoCourierAddress']").val(); //郵遞地址
        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
        var oAreaCode2 = $.trim(areaCode);
        var areaCode2 = $.trim(areaCode).replace(/[^\d]/g,'');
        if(oAreaCode2.length > 3){
        	alert("賠付對象 " + (i + 1) + " 費用資訊‘郵遞區號’ 長度超過3位數!");
        	checkFlag = false;
        	return false; //跳出each
        } else
        if(oAreaCode2 != areaCode2){
        	alert("賠付對象 " + (i + 1) + " 費用資訊‘郵遞區號’ 只能輸入數值!");
        	checkFlag = false;
        	return false; //跳出each
        } else
        //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
        if ($.trim($ownerName.val()) == '') {
            checkFlag = alertMessage($ownerName[0], "賠付對象 " + (i + 1) + " ‘賠付對象’必須輸入!");
            return false; //跳出each
        } else if (paymentKind.length == 0) {
            alert("賠付對象 " + (i + 1) + " ‘賠付類型’必須輸入!");
            checkFlag = false;
            return false; //跳出each
        } else if ($.trim(beneficiaryPhone).length == 0) {
            alert("賠付對象 " + (i + 1) + " ‘受款人電話’必須輸入!");
            checkFlag = false;
            return false; //跳出each
        } else if (ownerShip == 'B' && ($.trim(bankCode).length == 0|| $.trim(bankName).length == 0|| $.trim(accountCode).length == 0|| $.trim(customBankCode).length == 0|| $.trim(customBankName).length == 0)) {
            alert("賠付對象 " + (i + 1) + " 費用支付方式爲匯款，必須輸入支付帳戶資訊!");
            checkFlag = false;
            return false; //跳出each
        } else if (((ownerShip == 'B' && customBankCode !="9999") || ownerShip=='Q') && ($.trim(areaCode).length == 0|| $.trim(courierAddress).length == 0)) {
            alert("賠付對象 " + (i + 1) + " ‘郵遞區號’、‘郵遞地址’必須輸入!");
            checkFlag = false;
            return false; //跳出each
        } else {
            if(certificateCode == '01' || certificateCode =='02'){//證件類型為 身份證號，統一編號時需要校驗
                if($.trim(uniformNo).length == 0){
                    checkFlag = alertMessage($uniformNo[0], "第 " + (i + 1) + " 筆賠付對象資訊，證件類型為‘"+$certificateCode.find("option:selected").text()+"’,‘證件號碼’必須輸入!");
                    return false; //跳出each
                }else if((certificateCode == '01' && !checkIdentifyNumber(uniformNo, "9"))
                                || (certificateCode =='02' && !checkUniformNo(uniformNo))){
                    checkFlag = alertMessage($uniformNo[0], "第 " + (i + 1) + " 筆賠付對象資訊‘證件號碼’输入不正确!");
                    return false; //跳出each
                }
            }
        }
    });
    return checkFlag;
}
/***
 * 理赔金校验
 */
function checkPayAmount(){
    var sumDutyPaid = 0;
    var sumPayAmount = 0;
    $("#PrpLpayObjectInfo").find(":input[name='prpLpayObjectInfoPayAmount']").each(function(){
        sumPayAmount +=parseFloat(this.value);
    });
    $("#lLoss").find(":input[name='prpLlossDtoSumRealPay']").each(function(){
   	 sumDutyPaid +=parseFloat(this.value);
    });
    $("#Person").find("tr[name='prpLpersonLossObject']").each(function(){
   	 var $sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']");
   	 sumDutyPaid +=parseFloat($sumRealPay.val());
    });
    if(Math.abs(sumDutyPaid - sumPayAmount)>1){
   	 alert("賠付對象的理賠金額之和與賠款金額不等。");
        return false;
    }
    return true;
}
/**
 @author      任轶群
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
 *@description 弹出查看留言页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?claimNo=" + fm.prpLcompensateClaimNo.value;

	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}

/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relate() {

	var policyNo = fm.prpLcompensatePolicyNo.value;
	var claimNo = fm.prpLcompensateClaimNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&claimNo=" + claimNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");

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
 @author      理赔组
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
 @author      任轶群
 @description 计算赔付标的和人员核损金额之和
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

//function calLoss() {
//	//定义变量
//	var dblSumLoss = 0;
//	var LossSumLoss = 0;
//	var PersonLossSumLoss = 0;
//	var i = 0;
//
//	for (i = 1; i < fm.all("prpLlossDtoSumLoss").length; i++) {
//		LossSumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[i].value);
//
//		if (isNaN(LossSumLoss))
//			LossSumLoss = 0;
//
//		dblSumLoss = dblSumLoss + LossSumLoss;
//	}
//	//fm.prpLcompensateSumLoss.value = point(round(dblSumLoss, 0), 0);
//}



function checkRepeatItemCode(field) {
	//取得当前的数据
	var fieldname = field.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	var kindCode = fm.all("prpLlossDtoKindCode")[findex].value;
	var dangerNo = fm.all("prpLlossDtoDangerNo")[findex].value;
	var itemCode = fm.all("prpLlossDtoItemCode")[findex].value;
	var itemName = fm.all("prpLlossDtoLossName")[findex].value;
	//得到已经輸入的险别和标的
	for (i = 1; i < fm.all("prpLlossDtoKindCode").length - 1; i++) {
		if (fm.all("prpLlossDtoDangerNo")[i].value == dangerNo && fm.all("prpLlossDtoKindCode")[i].value == kindCode && fm.all("prpLlossDtoItemCode")[i].value == itemCode && fm.all("prpLlossDtoLossName")[i].value == itemName && fm.all("prpLlossDtoSerialNo")[i].value != fm.all("prpLlossDtoSerialNo")[findex].value) {
			alert(i18n.commonLiab.compensate.inputRiskMarkExist); //您輸入的险别和标的已经存在
			fm.all("prpLlossDtoItemCode")[findex].value = "";
			fm.all("prpLlossDtoLossName")[findex].value = "";
			fm.all("prpLlossDtoAmount")[findex].value = "";
			fm.all("prpLlossDtoAmount")[findex].value = "";

//			fm.all("prpLlossDtoItemCode")[findex].focus();
		}
	}

	//reasion:根据选择险别和标的,自动显示免赔额,或免赔率
	if (kindCode.length > 0 && itemCode.length > 0) {

		var inputObject = field;
		var outputObject;
		var policyno = fm.policyno.value;
		var inputArgs = {
			kindCode1: kindCode,
			itemCode: itemCode,
			policyno: policyno
		};
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("getPrpcitemkind", param, "rollbackItemCode", inputObject, outputObject);
		DWREngine.setAsync(true);
	} else {
		fm.all("prpLlossDtoDeductible")[findex].value = "";
		fm.all("prpLlossDtoDeductibleRate")[findex].value = "";
	}

}

function rollbackItemCode(inputObject, outputObject, returnObject) {

	var fieldname = inputObject.name;
	var findex = 0;
	if (fm.all(fieldname).length != undefined) {
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == inputObject) {
				findex = i;
				break;
			}
		}
	}
	var prpCitemKindDto = returnObject;
	if(prpCitemKindDto.deductible==null){
		fm.all("prpLlossDtoDeductible")[findex].value = 0;
	}else{
		fm.all("prpLlossDtoDeductible")[findex].value = prpCitemKindDto.deductible;
	}
	if(prpCitemKindDto.deductibleRate==null){
		fm.all("prpLlossDtoDeductibleRate")[findex].value = 0;
	}else{
		fm.all("prpLlossDtoDeductibleRate")[findex].value = prpCitemKindDto.deductibleRate;
	}
	calRealpay(inputObject);

}

function isSameKindCode() {
	var count = getElementCount("prpLchargeSerialNo") - 1;
	if (count > 1) {

		for (var i = 1; i < count; i++) {

			if (fm.prpLchargeChargeCode[count].value == fm.prpLchargeChargeCode[i].value) {

				alert(i18n.compensate.sameCostNotEntry); //同种费用不能重复輸入!
				return false;
			}
		}

	}
	return true
}

function deleteallRow1(pageCode, dataPageCode) {
	var index = 0; //当前table索引
	　　
	var oTBODY = document.getElementsByName(pageCode)[0].tBodies.item;
	var oTBODYData = document.getElementById(pageCode).tBodies.item(0);
	var oldelementNumber = oTBODYData.rows.length;
	for (var i = 0; i < oldelementNumber; i++) {
		oTBODYData.removeChild(oTBODYData.rows[0]);
	}
}

function creatCoinsFlag(countFlag) {
	fm.countFlag.value = countFlag;
}




function calFund() {
	var dblSumDutyPaid = 0; // 责任赔款合计（=（赔款费用附加信息中）计入赔款金额+（赔付标的附加信息中）赔偿金额+（赔付人员附加信息中）赔付合计）
    var dblSumPaid = 0; // 赔款总计（=责任赔款合计+其它费用）dblSumDutyPaid + dblSumNoDutyFee
    var dblSumPrePaid = 0; // 预赔金额
    var dblSumNoDutyFee = 0; // 其它费用（（赔款费用附加信息中）费用金额 - 计入赔款金额）+ 独立处理费用 PrpLCharge.ChargeAmount-PrpLCharge.SumRealPay + PrpLcompensate.IndependentCosts
    var dblSumThisPaid = 0; // 实赔金额（=责任赔款合计－已预付赔款）dblSumPaid - dblSumPrePaid
    var chargeRealPay = 0; // 计入赔款的
    var chargeAmount = 0; // 实际费用
    var prpLlossRealPay = 0;
    var personLossRealPay = 0;
    // 车物损赔款
    $("#lLoss").find("input[name='prpLlossDtoSumRealPayNTD']").each(function(){
        prpLlossRealPay += $parseFloat(this.value,0);
    });
    // 人伤赔款信息
    $("#Person").find("input[name='prpLpersonLossSumRealPayNTD']").each(function(){
        personLossRealPay += $parseFloat(this.value,0);
    });
    // 费用资讯信息
    $("#Charge").find("input[name='prpLchargeChargeAmountNTD']").each(function(){
        chargeAmount += $parseFloat(this.value,0);
    });
    // 费用资讯信息
    $("#Charge").find("input[name='prpLchargeSumRealPay']").each(function(){
        chargeRealPay += $parseFloat(this.value,0);
    });
    dblSumDutyPaid = prpLlossRealPay + personLossRealPay + chargeRealPay;
    // 赔款金额：赔款金额之和，不包括费用
    $(":input[name='prpLcompensateSumDutyPaid']").val(Math.round(dblSumDutyPaid));
    // var $independentCosts = $(":input[name='prpLcompensateIndependentCosts']");
    // 费用金额：费用金额之和，不包括赔款金额 //+ 独立处理费用
    dblSumNoDutyFee = chargeAmount - chargeRealPay;
    $("input[name='prpLcompensateSumNoDutyFee']").val(Math.round(dblSumNoDutyFee));
    // 本案合计：赔款合计与费用之和
    dblSumPaid = dblSumDutyPaid + dblSumNoDutyFee;
    $(":input[name='prpLcompensateSumPaid']").val(Math.round(dblSumPaid));
    // 已预付赔款金额：预付赔款金额之和
    dblSumPrePaid = parseFloat(fm.prpLcompensateSumPrePaid.value, 10);
    if (isNaN(dblSumPrePaid)) {
        dblSumPrePaid = 0;
    }
    // 本次赔付金额：赔款合计减去已预付赔款
    dblSumThisPaid = dblSumDutyPaid - dblSumPrePaid;
    $(":input[name='prpLcompensateSumThisPaid']").val(Math.round(dblSumThisPaid));
    
    $(":input[name='lossSumPaid']").val(Math.round(prpLlossRealPay));
    $(":input[name='personLossSumPaid']").val(Math.round(personLossRealPay));
    $(":input[name='allSumPaid']").val(Math.round(dblSumDutyPaid));
	setChangelossChargeFlag();
	if (fm.buttonCoins) {
		creatCoins();
		creatCoinsFlag('1');
		resetChangelossCharge();
	}
}
//function rollbackCalFund(inputObject, outputObject, returnObject) {
//	var prpLcompensateDto = returnObject;
//	fm.prpLcompensateSumDutyPaid.value = point(round(prpLcompensateDto.sumDutyPaid, 0), 0);
//	fm.prpLcompensateSumNoDutyFee.value = point(round(prpLcompensateDto.sumNoDutyFee, 0), 0);
//	fm.prpLcompensateSumPaid.value = point(round(prpLcompensateDto.sumPaid, 0), 0);
//	fm.prpLcompensateSumThisPaid.value = point(round(prpLcompensateDto.sumThisPaid, 0), 0);
//	fm.prpLdangerRiskSumPaid.value = point(round(prpLcompensateDto.sumThisPaid, 0), 0); //危险单位赋值
//	fm.prpLcompensateSumRest.value = point(round(prpLcompensateDto.sumRest, 0), 0);
//	if (fm.buttonCoins) {
//		creatCoins();
//		creatCoinsFlag('1');
//		resetChangelossCharge();
//	}
//	undisablebutton();
//}

function calRealpay(field) {
	var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
	var sumLoss = $parseFloat($prpLlossObject.find(":input[name='prpLlossDtoSumLoss']").val(),0);
	var sumRest = $parseFloat($prpLlossObject.find(":input[name='prpLlossDtoSumRest']").val(),0);
	var claimRate = $parseFloat($prpLlossObject.find(":input[name='prpLlossDtoClaimRate']").val(),0);

	var $DeductibleRate = $prpLlossObject.find(":input[name='prpLlossDtoDeductibleRate']");
	var deductibleRate = $parseFloat($DeductibleRate.val(),0);
	var $Deductible = $prpLlossObject.find(":input[name='prpLlossDtoDeductible']");
	var deductible = $parseFloat($Deductible.val(),0);
	
	//免赔额、免赔率如果输入只能输入其中一项
	if(deductibleRate>0&&deductible>0){
		alert("自負額與自負額比率只可以輸入其中一項！");
		if(field!=$Deductible.get(0)){
			$Deductible.val(0);
		}else{
			$DeductibleRate.val(0);
		}
        return false;
	}
	//reasion:增加变化的量设置
	setChangelossChargeFlag();
	var sum = 0;
    if(deductible!=0){
        sum = (sumLoss-sumRest)*(claimRate/100) - deductible;
    }else{
        sum = (sumLoss-sumRest)*(claimRate/100)*(1-deductibleRate/100);
    }
    if(sum < 0 && sumLoss > 0){
    	sum = 0;
    }
	var isMain = 1;
	var $kindCode = $prpLlossObject.find(":input[name='prpLlossDtoKindCode']");
	var kindCodeFlag = jQuery.data($prpCitemKind,$kindCode.val());
	if(kindCodeFlag != undefined){
		isMain = kindCodeFlag.toString().substring(1,2);
	}
	var Amount = $parseFloat($prpLlossObject.find(":input[name='prpLlossDtoAmount']").val(),0);
	var ItemValue = $parseFloat($prpLlossObject.find(":input[name='prpLlossDtoItemValue']").val(),0);
	var riskCode = $(":input[name='prpLcompensateRiskCode']").val();
	var kindCode = $kindCode.val();
	var f = true;
	if(riskCode=="F02" && (kindCode == 'FR1' || kindCode == 'FR3' || kindCode == 'FR2')){
		//住宅火災及地震基本保險不設保額為限
	} else if(isMain == 1 && sum > ItemValue){
		alert(i18n.compensate.cannotItemValue); //赔偿金额不能大於保險價值
		f = false;
	} else if(isMain == 1 && sum > Amount){
		alert(i18n.compensate.cannotAmount); //赔偿金额不能大於保险金额
		f = false;
	}
	if(!f && riskCode!="F02"){//商火 大於保額或保險價值，清0
		sum = 0;
	}
	var exchRate = $parseFloat($prpLlossObject.find(":input[name='prpLlossDtoExchRate']").val(),1);
	var currency = $prpLlossObject.find(":input[name='prpLlossDtoCurrency']").val();
	$prpLlossObject.find(":input[name='prpLlossDtoSumRealPay']").val(pointTwo(sum,currency));
	$prpLlossObject.find(":input[name='prpLlossDtoSumRealPayNTD']").val(pointTwo(sum*exchRate));
	prpLlossIsPayForOther(field);
	calFund();
	return true;
	//计算责任赔款合计、赔款合计、其它费用、实赔金额
//	calFund(fm.prpLchargeSumRealPay);
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
//修改费用代码是清空支付对象 end

//当手工輸入支付对象後，检查该对象是否存在银行帳号信息 begin

function getAccountByPayObjectName(field, payObjectName) {
	var order = getElementOrder(field) - 1;
	var submitStr = "AccountCode.do?actionType=SearchWithPayObjectName&ownerName=" + payObjectName + "&serialNo=" + order;
	window.open(submitStr, '', 'resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600');
}
//当手工輸入支付对象後，检查该对象是否存在银行帳号信息 end

//修改费用代码或支付类型或支付对象时，清空相应的付款信息 begin

function clearPayment(field) {
	var i = getElementOrder(field) - 1;
	fm.prpLchargeChargeReport[i].value = 0;
	fm.prpLchargeSumRealPay[i].value = 0;
	fm.prpLchargeChargeAmount[i].value = 0;
	fm.prpLchargeAccountCode[i].value = "";
	fm.prpLchargeBankName[i].value = "";
	fm.prpLchargeBankCode[i].value = "";
	fm.prpLchargeCustomBankCode[i].value = "";
	fm.prpLchargeCustomBankName[i].value = "";
	fm.prpLchargeOwnerName[i].value = "";
	fm.prpLchargeUniformNo[i].value= "";
	fm.prpLchargeAreaCode[i].value = "";
	fm.prpLchargeCourierAddress[i].value= "";
	calChargeAmount(field);
	checkBeyondQuota(field);
}
// 修改费用代码或支付类型或支付对象时，清空相应的付款信息 end

//当支付对象为外部机构时，自动带出外部机构的银行帳号

function getExternAlagency(field, index) {
	var inputObject = field;
	var outputObject;
	var ChargeCode = fm.prpLchargeChargeCode[index].value;
	var PayObjectType = fm.prpLchargePayObjectType[index].value;
	var PayObjectCode = fm.prpLchargePayObjectCode[index].value;
	if ( PayObjectType == "B" && PayObjectCode != "") {
		var inputArgs = {
			comCode: PayObjectCode
		};
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("getExternAlagency", param, "rollbackExternAlagency", inputObject, outputObject);
		DWREngine.setAsync(true);
	} else {
		fm.prpLchargeOwnerShip[index].options[0].selected = true;
		fm.prpLchargeAccountCode[index].value = "";
		fm.prpLchargeBankName[index].value = "";
		fm.prpLchargeBankCode[index].value = "";
		fm.prpLchargeCustomBankCode[index].value = "";
		fm.prpLchargeCustomBankName[index].value = "";
		fm.prpLchargeOwnerName[index].value = "";
		fm.prpLchargeUniformNo[index].value= "";
		fm.prpLchargeAreaCode[index].value = "";
		fm.prpLchargeCourierAddress[index].value= "";
	}
}

function rollbackExternAlagency(inputObject, outputObject, returnObject) {
	var fieldname = inputObject.name;
	var findex = 0;
	if (fm.all(fieldname).length != undefined) {
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == inputObject) {
				findex = i;
				break;
			}
		}
	}
	var prplexternalagencyDto = returnObject;
	fm.prpLchargeOwnerShip[findex].options[0].selected = true;//費用支付方式
	fm.prpLchargeAccountCode[findex].value = prplexternalagencyDto.accountCode;//匯款帳號
	fm.prpLchargeBankName[findex].value = prplexternalagencyDto.bankName;//總行名稱
	fm.prpLchargeBankCode[findex].value = prplexternalagencyDto.bankCode;//總行代號
	fm.prpLchargeCustomBankCode[findex].value = prplexternalagencyDto.customBankCode;//分行代號
	fm.prpLchargeCustomBankName[findex].value = prplexternalagencyDto.customBankName;//分行名稱
	fm.prpLchargeOwnerName[findex].value = prplexternalagencyDto.comcname;//賠付對象
	fm.prpLchargeUniformNo[findex].value = prplexternalagencyDto.certifiCateCode;//統一編號/身份證號
	fm.buttonAddAcc[findex].disabled = false;
	undisablebutton();
}

/**
 * 確認是否完成了所有對分攤金額的修改
 * @param field
 */
function confirmCheckChargeAmount(field){
	if(confirm("確認是否完成了所有對分攤金額的修改？")){
		checkCharge(field);
	}
}

/**
 * 分攤金額不能錄入小數
 * @param field
 */
function checkCharge(field){
	if( field.value.indexOf(".") != -1){
		alert("請注意:分攤金額不能錄入小數！");
		creatCoins();
		return false;
	}
//	checkChargeAmount();
}

/**
 * 對分攤金額校驗
 */
function checkChargeAmount(){
	var sumForPaid = 0;
	var sumForFee = 0;
	var sumForFeeOfUs = 0;
	var sumForPaidAll = 0;
	var sumForFeeAll = 0;
	for(var i = 1; i < fm.prpLcoinsCoinsSumpaid.length; i ++){
		if(fm.prpLcoinsLossFeeType[i].value == '0'){
			sumForPaid += parseFloat(fm.prpLcoinsCoinsSumpaid[i].value);
		}else{
			sumForFee += parseFloat(fm.prpLcoinsCoinsSumpaid[i].value);
			if(fm.prpLcoinsCoinsType[i].value == '1'){
				sumForFeeOfUs = parseFloat(fm.prpLcoinsCoinsSumpaid[i].value);
				sumForFeeAll += sumForFeeOfUs / parseFloat(fm.prpLcoinsCoinsRate[i].value)*100;
				sumForFeeAll = pointTwo(sumForFeeAll);
			}
		}
	}
	for(var j = 1; j < fm.prpLlossDtoSumRealPay.length; j ++){
		sumForPaidAll += parseFloat(fm.prpLlossDtoSumRealPay[j].value);
	}
	
	if(sumForPaid != sumForPaidAll){
		alert("請注意：賠款總金額與分攤賠款總金額不相等！");
		creatCoins();
		return false;
	}else if(sumForFee != sumForFeeAll){
		alert("請注意：費用總金額與分攤費用總金額不相等！");
		creatCoins();
		return false;
	}
	return true;
}
/**
 * 賠付比例= 保險金額/保險價值
 */
function getClaimRate(field) {
	var i = getElementOrder(field) - 1;
	var claimRate = 0;//赔付比例
	var amount = 0;//保险金额
	var itemValue = 0;//保险价值
	amount=parseFloat(fm.prpLlossDtoAmount[i].value);
	itemValue=parseFloat(fm.prpLlossDtoItemValue[i].value);
	if(itemValue < amount){
		fm.prpLlossDtoClaimRate[i].value='100';
	}else{
		ClaimRate=amount/itemValue*100;
		fm.prpLlossDtoClaimRate[i].value=pointTwo(ClaimRate);
	}
	fm.prpLlossDtoClaimRate[i].fireEvent('onchange');
	return true;
}
/**
 * 格式换数值
 * @param value
 * @param defaultValue
 * @return
 */
function $parseFloat(value,defaultValue){
	if($.isNumeric(value)){
		return parseFloat(value);
	}
	return defaultValue;
}
/**
 * 效验地震基金讯息
 * @return
 */
function checkEarthquakeFund(){
	var $tr = $("#EarthquakeFund").find("tr[name='TrEarthquakeFund']");
	if($tr.length>0){
		var DamageStartDate = $("#EarthquakeFund").find(":input[name='prpLearthquakeFundDamageStartDate']").val();
		var DamageStartHour = $("#EarthquakeFund").find(":input[name='prpLearthquakeFundDamageStartHour']").val();
		var DamageStartMinute = $("#EarthquakeFund").find(":input[name='prpLearthquakeFundDamageStartMinute']").val();
		if(DamageStartDate==""||DamageStartHour==""||DamageStartMinute==""){
			alert("地震基金跨簽單中出險日期不能為空！");
			return false;
		}else if(!$.isNumeric(DamageStartHour)){
			alert("地震基金跨簽單中出險日期小時為整數！");
			return false;
		}else if(!$.isNumeric(DamageStartMinute)){
			alert("地震基金跨簽單中出險日期分鐘為整數！");
			return false;
		}
	}
	var checkFlag = true;
	$tr.each(function(){
		var Times = $(this).find(":input[name='prpLearthquakeFundTimes']").val();
		if(!$.isNumeric(Times)){
			alert("地震基金跨簽單中賠次為整數！");
			checkFlag = false;
			return false;
		}
	});
	return checkFlag;
}

/***
 * 校驗險別賠付是否超過預估
 */
function checkKindPay(){
	var propKindArray = new Array();//物損賠付險別
	var propKindPayArray = new Array();//物損險別賠付金額
	var personKindArray = new Array();//人傷賠付險別
	var personKindPayArray = new Array();//人傷險別賠付金額
	var tempIndex;
	var $kindCode;
	var $sumRealPay;
	$kindCode = $("#spanlLoss").find(":input[name='prpLlossDtoKindCode']");
	$sumRealPay = $("#spanlLoss").find(":input[name='prpLlossDtoSumRealPayNTD']");
	$kindCode.each(function(i,e){
		propKindArray.push(e.value);
		propKindPayArray.push($sumRealPay.get(i).value);
	});
	$kindCode = $("#spanPerson").find(":input[name='prpLpersonLossKindCode']");
	$sumRealPay = $("#spanPerson").find(":input[name='prpLpersonLossSumRealPayNTD']");
	$kindCode.each(function(i,e){
		personKindArray.push(e.value);
		personKindPayArray.push($sumRealPay.get(i).value);
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
			propKind : propKindArray.join(","),
			propKindPay : propKindPayArray.join(","),
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