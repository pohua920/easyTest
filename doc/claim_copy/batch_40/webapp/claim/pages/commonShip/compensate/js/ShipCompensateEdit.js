/*****************************************************************************
 * DESC       ：实赔的脚本函数页面
 * AUTHOR     ：中科软
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 * @description 检查报案登记
 * @param 无
 * @return 通过返回true,否则返回false
 */
function checkForm(){
    return true;
}
/**
 * @description 设值页面的一些初始化信息
 * @param 无
 * @return 通过返回true,否则返回false
 */
function initSet(){
    initPayNTD();
    // 增加收到客户索赔申请已过天数提示 begin】
    var passDayList = document.getElementsByName("passDay");
    if (passDayList.length > 0 && passDayList[0] != null && passDayList[0].value != 0) {
        alert(i18n.commonAcci.compensate.receiveCustomerOver + passDayList[0].value + i18n.commonAcci.compensate.dayPleaseDeal); // 收到客户索赔申请已过 天，请尽快处理！
    }
    var coinsFlag = fm.coinsFlag.value;
    var message = "";
    var shareHolderFlag = fm.shareHolderFlag.value;
    if (coinsFlag == 1) {
        alert(i18n.commonAcci.compensate.policyPayGenerateInfoTotalLoss1); // 本保单为主共保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
    }
    if (coinsFlag == 2) {
        alert(i18n.commonAcci.compensate.policyPayGenerateInfoTotalLoss3); // 本保单为从共保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
    }
    if (coinsFlag == 3) {
        alert(i18n.commonAcci.compensate.policyPayGenerateInfoTotalLoss4); // 本保单为从联保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
    }
    var payFee = parseInt(fm.payFee.value);
    var delinquentfeeCase = fm.delinquentfeeCase.value;
    if (payFee == -1) {
        alert(i18n.claim.premiumNotPaid); // 此保单保费未缴！\n
    } else if (payFee == 0) {
    	message = message + i18n.certainLoss.policyPremiumPay; // 此保单已缴未缴全,请慎重处理！！！ \n
        message = message + delinquentfeeCase + "\n";
    }
    if (message.length > 0) {
        alert(message);
    }
    //出險時間的修改
    initDamageDate();
    return true;
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

/**
 * @description 提交
 * @param 无
 * @return 通过返回true,否则返回false
 */
function submitForm(){
    fm.submit();
    return true;
}
/**
 * @description 清除
 * @param 无
 * @return 通过返回true,否则返回false
 */
function resetForm(){
    if (window.confirm("確定要清除嗎？")) {
        location.href = location.href;
        return true;
    } else {
        return false;
    }
}

/**
 * @description 根据按钮状态保存报案数据
 * @param this
 * @param 保存状态
 * @return 通过返回true,否则返回false
 */
function saveForm(field,saveType){
    // 如果结案类型是拒赔，则不得赔付标的损失赔偿（可以赔付赔款费用）
    var finallyFlag = $(":input[name='prpLcompensateFinallyFlag']").val();
    var sumThisPaid = $(":input[name='prpLcompensateSumThisPaid']").val();
    if ("2" == finallyFlag) {
        if (parseFloat(sumThisPaid) != 0) {
            alert(i18n.commonLiab.compensate.closedTypesRejectClaim); // 结案类型是拒赔，不得赔付标的损失赔偿（可以赔付赔款费用）!
            return false;
        }
    }
    var errorMessage = "";
    fm.buttonSaveType.value = saveType;
    if (errorMessage.length > 0) {
        alert(errorMessage);
        return false;
    }
    var configCode = $(":input[name='configCode']").val();
    var shipModel = $(":input[name='prpLcompensateShipModel']").val();// 机型
    if ("RISKCODE_YAV" == configCode && $.trim(shipModel).length == 0) {
        alert("航空保險‘機型’必須錄入!");
        return false;
    }
    if("RISKCODE_YAV" == configCode){
    	var nationalityCode = $(":input[name='prpLcompensateNationalityCode']").val();
        var re = /^\d{1,10}\s{0,9}$/;
        if(!(nationalityCode.length <= 10 && re.test(nationalityCode))){
            alert("‘國籍編號’為十位文數字，不足十位者請在後面補空白！");
            return false;
        }
    }
    if (saveType == 4) {
    	var lossLen = $(":input[name='prpLlossDtoKindCode']").length;
     	var personLen = $(":input[name='prpLpersonLossKindCode']").length;
     	var chargeLen = $(":input[name='prpLchargeKindCode']").length;
     	if(lossLen<=1&&personLen<=1&&chargeLen<=1){
     		alert(i18n.compensate.claimBook); //赔款计算书中的赔付标的，赔款费用至少有一条记录!
     		return false;
     	}
        // 判断理算报告是不能为空的。
        if (!(checkPrpLloss() && checkPrpLpersonLoss() && checkPrpLcharge() && checkPrpLpayObjectInfo() && checkPayAmount())) {
            return false;
        }
		if(!checkKindPay()){
			return false;
		}
        var context = $(":input[name='prpLltextContextInnerHTML']").val();
        if ($.trim(context).length == 0) {
            alert("請選擇理算說明類型或錄入理算說明！");
            return false;
        }
        if (confirm("案件最终 赔款金额为：" + fm.prpLcompensateSumThisPaid.value + " ,费用金额为：" + fm.prpLcompensateSumNoDutyFee.value + " ,请确认！")) {} else {
			undisablebutton();
			return false;
		}
		//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
        errorMessage ="";
        for (var i = 1; i < fm.prpLpayObjectInfoBeneficiaryPhone.length; i++) {	
        	var prpLpayObjectInfoBeneficiaryPhone =fm.prpLpayObjectInfoBeneficiaryPhone[i].value; 
        	if (prpLpayObjectInfoBeneficiaryPhone.length > 0) {
			 if (prpLpayObjectInfoBeneficiaryPhone.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
			    		errorMessage =errorMessage +"受款人電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLpayObjectInfoBeneficiaryPhone)){
			    	errorMessage =errorMessage +"受款人電話有誤\n";
			      }
			}
		  }
        }
        for (var i = 1; i < fm.prpLpayObjectInfoMobilePhoneNo.length; i++) {	
	    var prpLpayObjectInfoMobilePhoneNo =  fm.prpLpayObjectInfoMobilePhoneNo[i].value; 
	    if (prpLpayObjectInfoMobilePhoneNo.length > 0) {
	    		  var reg =/^09[0-9]{8}$/;
	    		  if(!reg.test(prpLpayObjectInfoMobilePhoneNo)){
	    			  errorMessage =errorMessage +"行動電話有誤\n";
	    		  }
	    }
        }
        for (var i = 1; i < fm.prpLchargeBeneficiaryPhone.length; i++) {	
	    var prpLchargeBeneficiaryPhone = fm.prpLchargeBeneficiaryPhone[i].value;  
	    if (prpLchargeBeneficiaryPhone.length > 0) {
			 if (prpLchargeBeneficiaryPhone.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLchargeBeneficiaryPhone)){
			    		errorMessage =errorMessage +"受款人電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLchargeBeneficiaryPhone)){
			    	errorMessage =errorMessage +"受款人電話有誤\n";
			      }
			}
		  }
        }
        for (var i = 1; i < fm.prpLpersonLossMobilePhone.length; i++) {	
	    var prpLpersonLossMobilePhone =fm.prpLpersonLossMobilePhone[i].value;
		if (prpLpersonLossMobilePhone.length > 0) {
		    	 var reg =/^09[0-9]{8}$/;
		    	  if(!reg.test(prpLpersonLossMobilePhone)){
		    		  errorMessage =errorMessage +"受害人手機有誤";
		    	  }	
		  }
        }
		if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		}//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end 
    }
    $(":button").attr("disabled", true);;
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
//    fm.submit();
//    return true;
    
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
	    $(":button").attr("disabled", false);
		return false;
	}
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
	
}

/***
 * 赔付标的校验
 */
function checkPrpLloss() {
    var checkFlag = true;
    var $prpLpayObjectInfoCurrency = $("#PrpLpayObject").find(":input[name='prpLpayObjectInfoCurrency']");
    $("#lLoss").find("tr[name='prpLlossObject']").each(function (i) {
        var $kindCode = $(this).find(":input[name='prpLlossDtoKindCode']");
        var $sumRealPayNTD = $(this).find(":input[name='prpLlossDtoSumRealPayNTD']");
        var $payObjectSerialNo = $(this).find(":input[name='prpLlossDtoPayObjectSerialNo']");
        var $sumRealPay = $(this).find(":input[name='prpLlossDtoSumRealPay']");
        var $currency = $(this).find(":input[name='prpLlossDtoCurrency']");
        var payObjectSerialNo = $payObjectSerialNo.val();
        if ($.trim($kindCode.val()) == '') {
            checkFlag = alertMessage($kindCode[0], "第 " + (i + 1) + " 筆賠付標的訊息‘險別’必須輸入!");
            return false; //跳出循环
        }else{
        		var sumRealPayNTD = 0;
        		if($.trim(payObjectSerialNo)!=""){
        			var payObjectValue = payObjectSerialNo.split(";");
	        		for (var j = 0; j < payObjectValue.length; j++) {
	        			var payObjectValueTemp = payObjectValue[j].split(":");
	        			if($currency.val()!= $prpLpayObjectInfoCurrency.get(parseInt(payObjectValueTemp[0])-1).value){
	        				alert("第" + (i + 1) + "筆賠付標的訊息的賠償幣別和‘支付對象訊息’的支付幣別不同，請重新分配!");
	            			checkFlag = false;
	            			return false;
	        			}
	        			sumRealPayNTD += parseFloat(payObjectValueTemp[1]);
	        		}
        		}
        		if (parseFloat($sumRealPay.val())-sumRealPayNTD != 0) {
        			alert("第" + (i + 1) + "筆賠付標的訊息‘支付對象訊息’分配的理賠金額之和與‘實賠金額’不等，請重新分配!");
        			checkFlag = false;
        			return false;
        		}
        }
    });
    return checkFlag;
 }
/***
 * 人员伤亡赔付校验
 * @returns {Boolean}
 */
 function checkPrpLpersonLoss() {
    var checkFlag = true;
    var $prpLpayObjectInfoCurrency = $("#PrpLpayObject").find(":input[name='prpLpayObjectInfoCurrency']");
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
	                        for (var j = 0; j < payObjectValue.length; j++) {
	                            var payObjectValueTemp = payObjectValue[j].split(":");
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
    var $ChargeReport = $("#Charge").find(":input[name='prpLchargeChargeReport']");
    var $ChargeAmount = $("#Charge").find(":input[name='prpLchargeChargeAmount']");
    var $Currency = $("#Charge").find(":input[name='prpLchargeCurrency']");
    
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
	        	alert("第 " + (i + 1) + " 筆費用資訊‘郵遞區號’ 長度超過3位數!");
	        	checkFlag = false;
	        	break;
	        } else
	        if(oAreaCode2 != areaCode2){
	        	alert("第 " + (i + 1) + " 筆費用資訊‘郵遞區號’ 只能輸入數值!");
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
        } else {
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
        var $FeeSerialNo = $chargeObject.find(":input[name='prpLchargeFeeSerialNo']"); //险别代码
        if($chargeCode.val()=="B"&&$FeeSerialNo.val()==""){
        	 alertMessage($uniformNo[0], "第 " + (i + 1) + " 筆費用資訊‘代扣費用序號’必須輸入!");
        	 checkFlag = false;
        	 break;
        }else if($FeeSerialNo.val()!=""){
        	var feeSerialNo = parseInt($FeeSerialNo.val())-1;
        	if(feeSerialNo>=alltr_length/clength||feeSerialNo == i){
        		alertMessage($uniformNo[0], "第 " + (i + 1) + " 筆費用資訊‘代扣費用序號’输入不正确!");
           	 	checkFlag = false;
           	 	break;
        	}else if($Currency.get(i).value!=$Currency.get(feeSerialNo).value){
        		alertMessage($uniformNo[0], "第 " + (i + 1) + " 筆費用資訊‘代扣費用序號’對應的幣別不同!");
           	 	checkFlag = false;
           	 	break;
        	}else if(parseFloat($ChargeAmount.get(i).value)!=parseFloat($ChargeReport.get(feeSerialNo).value)-parseFloat($ChargeAmount.get(feeSerialNo).value)){
        		alertMessage($uniformNo[0], "第 " + (i + 1) + " 筆費用資訊‘代扣費用序號’對應的支付費用金額输入不正确!");
        		checkFlag = false;
           	 	break;
        	}
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
     $("#PrpLpayObject").find("tr[name='PrpLpayObjectInfo']").each(function (i) {
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
     $("#PrpLpayObject").find(":input[name='prpLpayObjectInfoPayAmount']").each(function(){
         sumPayAmount +=parseFloat(this.value);
     });
     $("#lLoss").find(":input[name='prpLlossDtoSumRealPay']").each(function(){
    	 sumDutyPaid +=parseFloat(this.value);
     });
     $("#Person").find("tr[name='prpLpersonLossObject']").each(function(){
    	 var $sumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']");
    	 sumDutyPaid +=parseFloat($sumRealPay.val());
     });
     if(Math.abs(sumDutyPaid-sumPayAmount)>1){
    	 alert("賠付對象的理賠金額之和與賠款金額不等。");
         return false;
     }
     return true;
 }
 
/**
 *@description 弹出留言保存页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinSave() {
	var policyNo = fm.prpLcompensatePolicyNo.value;
	var riskCode = fm.prpLcompensateRiskCode.value;
	var businessNo = fm.prpLcompensateClaimNo.value;
	var claimNo = fm.prpLcompensateClaimNo.value;
	msg = window.open("/claim/messageQueryInfo.do?businessNo=" + businessNo + "&nodeType=compe&policyNo=" + policyNo + "&riskCode=" + riskCode + "&claimNo=" + claimNo, "NewWindow", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=500,Height=300");
}


//如果是案终陪付，则显示结案报告。

function changePrpLcompensateFinallyFlag() {
	if (fm.prpLcompensateFinallyFlag[0].checked) {
		Lltext.style.display = "";
	} else {
		Lltext.style.display = "none";
	}
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
	return true;
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


function getPayObject(field) {
    var $chargeObject = findPageCodeObject(field,"Charge");
    var prpLchargeChargeCode = $chargeObject.find(":input[name='prpLchargeChargeCode']").val(); //费用名称
    if (prpLchargeChargeCode == null || prpLchargeChargeCode == "") {
        alert("請選擇費用名稱");
        return;
    } else {
        var serialNo = $(":input[name='" + field.name + "']").index($(field));
        var ownerName = $chargeObject.find(":input[name='prpLchargeOwnerName']").val();
        var url = "/claim/pages/common/account/PaymentAccountName.jsp?serialNo=" + serialNo + "&ownerName=" + ownerName;
        var handle = window.showModalDialog(url, window, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
        if (handle == null || handle == "") {
            $chargeObject.find(":input[name='prpLchargePayObjectCode']").val("");
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
//function getPayObject(field) {
//	var fieldName = field.name;
//	var fieldNameList = document.getElementsByName(fieldName);
//	var chargeCodeList = document.getElementsByName("prpLchargeChargeCode");
//	var prpLchargePayObjectType = document.getElementsByName("prpLchargePayObjectType");
//	var chargeCode;
//	var index;
//	var payObjectType;
//	for (var i = 0; i < fieldNameList.length; i++) {
//		if (fieldNameList[i] == field) {
//			index = i;
//			break;
//		}
//	}
//	if (chargeCodeList[index] == null || chargeCodeList[index].value == "") {
//		alert(i18n.replevy.nameCost); //请选择费用名称
//		return;
//	} else {
//		chargeCode = chargeCodeList[index].value;
//		payObjectType = prpLchargePayObjectType[index].value;
//		//add by liuwei at 2010-12-21 根据费用类型和支付类型的不同，要求带出外部机构或手工輸入支付对象 begin
//		if (payObjectType == "A") { //手工輸入支付对象
//			var serialNo = getElementOrder(field) - 1;
//			var url = "/claim/pages/common/account/PaymentAccountName.jsp?serialNo=" + serialNo;
//			var handle = window.showModalDialog(url, window, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
//			if (handle == null || handle == "") {
//				fm.prpLchargePayObjectName[serialNo].value = ""
//			} else {
//				fm.prpLchargePayObjectName[serialNo].value = handle;
//				fm.prpLchargeOwnerName[serialNo].value = handle;
//				getAccountByPayObjectName(field, handle);
//			}
//		} else { //带出外部机构
//			code_CodeSelect(field, 'getPayObject', '-1,0', 'Y', 'N', chargeCode + "|" + payObjectType);
//			//getExternAlagency(field, index);
//		}
//		//add by liuwei at 2010-12-21 根据费用类型和支付类型的不同，要求带出外部机构或手工輸入支付对象 end
//	}
//}
 //费用获取帳户信息

 function getAccountByPayObjectName(serialNo, payObjectName) {
    var submitStr = "AccountCode.do?actionType=SearchWithPayObjectName&ownerName=" + payObjectName + "&serialNo=" + serialNo;
    window.open(submitStr, '', 'resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600');
 }
//当支付对象为外部机构时，自动带出外部机构的银行帳号
//当手工輸入支付对象後，检查该对象是否存在银行帳号信息 begin
//function getAccountByPayObjectName(field, payObjectName) {
//	var order = getElementOrder(field) - 1;
//	var submitStr = "AccountCode.do?actionType=SearchWithPayObjectName&ownerName=" + payObjectName + "&serialNo=" + order;
//	window.open(submitStr, '', 'resizable=1,scrollbars=yes,overflow=scroll,width=600,height=600');
//}
/***
 * 清空支付對象訊息
 * @param field
 */
function clearPayObject(field) {
    var $chargeObject = findPageCodeObject(field,"Charge");
    $chargeObject.find(":input[name='prpLchargePayObjectCode']").val("");
    $chargeObject.find(":input[name='prpLchargePayObjectName']").val("");
}
/***
 * 費用相關金額初始化
 * @param field
 */
function setChargeInput(field){
    var $chargeObject = findPageCodeObject(field,"Charge");
    $chargeObject.find(":input[name='prpLchargeChargeReport']").val(0);
    $chargeObject.find(":input[name='prpLchargeChargeAmount']").val(0);
    $chargeObject.find(":input[name='prpLchargeChargeAmountNTD']").val(0);
    calFund();
}

/**
 * 切換費用代碼或名稱時，重置
 * @param field
 */
function clearPayment(field) {
    var $chargeObject = findPageCodeObject(field,"Charge");
    $chargeObject.find(":input[name='prpLchargeChargeReport']").val(0);
    $chargeObject.find(":input[name='prpLchargeChargeAmount']").val(0);
    $chargeObject.find(":input[name='prpLchargeChargeAmountNTD']").val(0);
    $chargeObject.find(":input[name='prpLchargeAccountCode']").val("");
    $chargeObject.find(":input[name='prpLchargeBankCode']").val("");
    $chargeObject.find(":input[name='prpLchargeBankName']").val("");
    $chargeObject.find(":input[name='prpLchargeCustomBankCode']").val("");
    $chargeObject.find(":input[name='prpLchargeCustomBankName']").val("");
    $chargeObject.find(":input[name='prpLpayObjectInfoBeneficiaryPhone']").val("");
    $chargeObject.find(":input[name='prpLchargeOwnerName']").val("");
    $chargeObject.find(":input[name='prpLchargeUniformNo']").val("");
    $chargeObject.find(":input[name='prpLchargeAreaCode']").val("");
    $chargeObject.find(":input[name='prpLchargeCourierAddress']").val("");
}
/**
 * 计算赔付信息
 */
function calFund(){
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
        prpLlossRealPay += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
    });
    // 人伤赔款信息
    $("#Person").find("input[name='prpLpersonLossSumRealPayNTD']").each(function(){
        personLossRealPay += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
    });
    // 费用资讯信息
    $("#Charge").find("input[name='prpLchargeChargeAmountNTD']").each(function(){
        chargeAmount += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
    });
    // 费用资讯信息
    $("#Charge").find("input[name='prpLchargeSumRealPay']").each(function(){
        chargeRealPay += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
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
    if (fm.buttonCoins) {
		creatCoins();
		creatCoinsFlag('1');
		resetChangelossCharge();
	}
}

/**
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
 * 计算标的赔付
 * @param field
 */
function calRealpay(field){
    var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
    var itemKindNo = $prpLlossObject.find(":input[name='prpLlossDtoItemKindNo']").val();
    var kindCode = $prpLlossObject.find(":input[name='prpLlossDtoKindCode']").val();
    if(kindCode.length == 0){
    	alert("請錄入財產賠付險別！");
    	return false;
    }
    var currency = $prpLlossObject.find(":input[name='prpLlossDtoCurrency']").val();
    var sumLoss = initValue($prpLlossObject.find(":input[name='prpLlossDtoSumLoss']"),0);//核定損失
    var sumRest = initValue($prpLlossObject.find(":input[name='prpLlossDtoSumRest']"),0);//殘值
    var claimRate = initValue($prpLlossObject.find(":input[name='prpLlossDtoClaimRate']"),0);//賠付比例
    var deductible = initValue($prpLlossObject.find(":input[name='prpLlossDtoDeductible']"),0);//自負額
    var deductibleRate = initValue($prpLlossObject.find(":input[name='prpLlossDtoDeductibleRate']"),0);//自負額比率
    var $sumRealPay = $prpLlossObject.find(":input[name='prpLlossDtoSumRealPay']");
    var exchRate = parseFloat($prpLlossObject.find(":input[name='prpLlossDtoExchRate']").val());
    var $sumRealPayNTD = $prpLlossObject.find(":input[name='prpLlossDtoSumRealPayNTD']");
    var amount = initValue($prpLlossObject.find(":input[name='prpLlossDtoAmount']"),0);//保額
    var sum = 0;
    if(deductible!=0){
        sum = (sumLoss-sumRest)*(claimRate*0.01) - deductible;
    }else{
        sum = (sumLoss-sumRest)*(claimRate*0.01)*(1-deductibleRate*0.01);
    }
    if(sum < 0){
    	sum = 0;
    }
    var sumRealPayNTD = Math.round(sum*exchRate);
    $sumRealPay.val(getFormatValueByCurrency(sum,currency));
    $sumRealPayNTD.val(sumRealPayNTD);
    if(sum != 0){
        var checkFlag = checkLimit("1",$prpLlossObject,kindCode);
        if(checkFlag){
            $sumRealPay.val(0);
            $sumRealPayNTD.val(0);
        }
    }
    prpLlossIsPayForOther(field);
    calFund();
    return true;
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
    var sumDefPay = initValue($prpLpersonLossObject.find(":input[name='prpLpersonLossSumDefPay']"),0);//核定賠償
    $prpLpersonLossObject.find(":input[name='prpLpersonLossSumLoss']").val(sumDefPay);
    var deductible = initValue($prpLpersonLossObject.find(":input[name='prpLpersonLossDeductible']"),0);//自負額
    var currency = $prpLpersonLossObject.find(":input[name='prpLpersonLossCurrency']").val();
    var $sumRealPay = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPay']");//賠償金額
    var $sumRealPayNTD = $prpLpersonLossObject.find(":input[name='prpLpersonLossSumRealPayNTD']");//賠償金額（NTD）
    var exchRate = parseFloat($prpLpersonLossObject.find(":input[name='prpLpersonLossExchRate']").val());//匯率
    var sum = sumDefPay - deductible;
    if(sum < 0){
    	sum = 0;
    }
    $sumRealPay.val(getFormatValueByCurrency(sum,currency));
    $sumRealPayNTD.val(Math.round(sum*exchRate));
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
        sumRealPay1NTD += (isNaN($(this).val()) ? 0 : parseFloat($(this).val()));
    });
    $personObject.find(":input[name='prpLpersonLossSumRealPay1NTD']").val(Math.round(sumRealPay1NTD));
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
	if ("prpLpersonLossCertificateCode" == field.name || "prpLpersonLossIdentifyNumber" == field.name) {
		desc = "人員ID（證件類型、證件號碼）";
	} else if ("prpLpersonLossCasualties" == field.name) {
		desc = "傷亡情形";
	}
	$sumRealPay.each(function(i,e){
		if (parseFloat($.trim(e.value)) != 0) {
			if (!checkFlag) {
				alert("修改" + desc + "后，該人員的各項賠付需要重新校驗限額，\n請在賠款金額會重置后重新計算！");
			}
			checkFlag = true;
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
		alertMessage($identifyNumber[0], "請錄入證件號碼!");
		return false;
	} else if ((certificateCode == '01' && !checkIdentifyNumber(identifyNumber, sex)) || (certificateCode == '02' && !checkUniformNo(identifyNumber))) {
		alertMessage($identifyNumber[0], "請錄入正確的" + $certificateCode.find("option:selected").text() + "!");
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
	$(deletObject).find(":input[name$='PayObjectSerialNo']").each(function(){
		calPayAmount(this.value);//处理赔付对象序号对应的赔付对象讯息
	});
	if ($personObject != null && $personObject.length > 0) {
		setSumRealPay1NTD($personObject);
	}
	calFund();
}
/**
 *删除本条赔付之後的处理
 */
function afterDeletePerson(deletObject,btnField,pageCode,csFieldName){
	$(deletObject).find(":input[name$='PayObjectSerialNo']").each(function(){
		calPayAmount(this.value);//处理赔付对象序号对应的赔付对象讯息
	});
	calFund();
}
/***
 * 设置标的赔付的赔付对象讯息
 * @param field
 */
var lastField = null;
function setPayObjectSerialNo(field){
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
            strLi += "收取對象"+this.value+"&nbsp;&nbsp;";
            strLi += "理赔金額:<input type=\"text\" onblur=\"setPayObjectPayAmount(this);\" name=\"payObjectPayAmount\"value=\""+payAmount+"\" class=\"input\" style=\"width:100px\"/>";
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
    var currency = "";
    if(lastField != null){
        if(lastField.name=="prpLlossDtoPayObjectSerialNo"){
        	var $prpLlossObject = $(lastField).parents("tr[name='prpLlossObject']");
        	currency = $prpLlossObject.find(":input[name='prpLlossDtoCurrency']").val();
        }else{
        	var $prpLpersonLossObject = $(lastField).parents("tr[name='prpLpersonLossObject']");
        	currency = $prpLpersonLossObject.find(":input[name='prpLpersonLossCurrency']").val();
        	
        }
    }
    if($.trim(serialNoStr)!=""){
        var serialNoStrArray = serialNoStr.split(";");
        for(var i = 0;i < serialNoStrArray.length; i++){
            var temp = serialNoStrArray[i].split(":");
            serialNoArray[i] = temp[0];
            amountArray[i] = temp[1];
        }
    }
    serialNoStr = "";
    var $prpLpayObjectInfoCurrency = $("#PrpLpayObject").find(":input[name='prpLpayObjectInfoCurrency']");
    $("#uLprpLPayObjectinfo").find(":checkbox[name='payObjectSerialNo']").each(function(){
        var $amount = $(this).next(":input[name='payObjectPayAmount']");
        if(this.checked){
        	if(currency!=$prpLpayObjectInfoCurrency.get(parseInt(this.value)-1).value){
	    		alert("賠償幣別和賠付對象的支付幣別不同，不容許選擇");
	    		this.checked = false;
	    		$amount.val();
	    	}
        	if(this.checked){
	            serialNoStr += this.value;
	            var payAmount = parseFloat($.trim($amount.val()));
	            if(!isNaN(payAmount)){
	                serialNoStr += ":"+pointTwo(payAmount,currency);
	                $amount.val(pointTwo(payAmount,currency));
	            }else{
	                serialNoStr += ":0";
	                $amount.val("0");
	            }
	            serialNoStr += ";";
        	}
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
    var $payAmount = $("#PrpLpayObject").find("input[name='prpLpayObjectInfoPayAmount']");
    
    for(var i = 0;i<serialNoArray.length;i++){
        if(parseFloat(amountArray[i])!=0){//偏移量 不为0
            var f = $payAmount.get(parseInt(serialNoArray[i])-1);
            var payObjectCurrency = $prpLpayObjectInfoCurrency.get(parseInt(serialNoArray[i])-1).value;
            f.value = pointTwo(parseFloat(f.value)+parseFloat(amountArray[i]),payObjectCurrency);
        }
    }
}
/***
 *币别切换
 */
function getPrpLlossDtoExchRate(field){
    var $prpLlossObject = $(field).parents("tr[name='prpLlossObject']");
    var $exchRate = $prpLlossObject.find(":input[name='prpLlossDtoExchRate']");
    if($(field).val() == CURRENCYINFO.LOCAL_CURRENCY){
        $exchRate.val(1);
    }else{
        var t = jQuery.data($exchToBase,$(field).val());
        if(t == undefined){
            alert($(field).val()+"對本位幣（NTD）的匯率沒有配置，請配置后在選擇");
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
 * 人伤赔付获取当前赔付币别对本位币NTD的汇率
 */
function getPrpLpersonLossExchRate(field){
    var $prpLpersonLossObject = $(field).parents("tr[name='prpLpersonLossObject']");
    //$prpLpersonLossObject.find(":input[name='prpLpersonLossExchRate']").val(jQuery.data($exchToBase,$(field).val()));
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
/**
 * 赔付对象切换币别
 * @param field
 * @return
 */
function getPrpLpayObjectInfoExchRate(field){
	var $PrpLpayObjectInfo = $(field).parents("tr[name='PrpLpayObjectInfo']");
	var $exchRate = $PrpLpayObjectInfo.find(":input[name='prpLpayObjectInfoExchRate']");
    if($(field).val() == CURRENCYINFO.LOCAL_CURRENCY){
        $exchRate.val(1);
    }else{
        var t = jQuery.data($exchToBase,$(field).val());
        if(t == undefined){
            alert($(field).val()+"對本位幣（NTD）的匯率沒有配置，請配置后在選擇");
            $(field).val(CURRENCYINFO.LOCAL_CURRENCY);
            $exchRate.val(1);
        }else{
            $exchRate.val(jQuery.data($exchToBase,$(field).val())); 
        }
    }
    $PrpLpayObjectInfo.find(":input[name='prpLpayObjectInfoCurrency']").val($(field).val());
}
/***
 * 费用讯息获取当前赔付币别对本位币NTD的汇率
 */
function getPrpLchargeExchRate(field){
    var $chargeObject = findPageCodeObject(field,"Charge");
    var $exchRate = $chargeObject.find(":input[name='prpLchargeExchRate']");
    var $currencyForPayObject = $chargeObject.find(":input[name='prpLchargeCurrencyForPayObject']");
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
    $chargeObject.find(":input[name='prpLchargeAccountCurrency']").val($(field).val());
    $currencyForPayObject.val($(field).val());
    setChargeAmountNTD(field);
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