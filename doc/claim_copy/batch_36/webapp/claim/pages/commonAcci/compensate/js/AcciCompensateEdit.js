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
function setChargeAmount(field) {
	var $tr = findPageCodeObject(field,"Charge");
	var chargeCode = $tr.find(":input[name='prpLchargeChargeCode']").val();
	var chargeName = $tr.find(":input[name='prpLchargeChargeName']").val();
	var $ChargeAmount = $tr.find(":input[name='prpLchargeChargeAmount']");
	var $ChargeReport = $tr.find(":input[name='prpLchargeChargeReport']");
	var currency = $tr.find(":input[name='prpLchargeCurrency']").val();
	if(field == $ChargeReport.get(0)){
		$ChargeAmount.val($ChargeReport.val());
	}
	if ((chargeCode.length > 0) && (chargeName.length > 0)) {
		$ChargeReport.val(pointTwo($parseFloat($ChargeReport.val()),currency));
		$ChargeAmount.val(pointTwo($parseFloat($ChargeAmount.val()),currency));
	} else{
		$ChargeReport.val(0);
		$ChargeAmount.val(0);
	}
	if(parseFloat($ChargeAmount.val())>parseFloat($ChargeReport.val())){
		alert("總金額不能大於費用金額！");
		$ChargeAmount.val(0);
	}
	var exchRate = parseFloat($tr.find(":input[name='prpLchargeExchRate']").val());
	var chargeAmount = parseFloat($ChargeAmount.val());
	var $chargeAmountNTD = $tr.find(":input[name='prpLchargeChargeAmountNTD']");
	$chargeAmountNTD.val(pointTwo(chargeAmount*exchRate));
	sumPaid();
	setChangelossChargeFlag();
	if (fm.buttonCoins) {
		creatCoins();
		creatCoinsFlag('1');
		resetChangelossCharge();
	}
//	collectCompensate(field);
}
function sumPaid(){
	var sumDutyPaid = 0;
	$("#PersonLoss").find(":input[name='prpLpersonLossSumRealPayNTD']").each(function(){
		sumDutyPaid +=$parseFloat(this.value);
	});
	var sumNoDutyFee = 0;
	$("#Charge").find(":input[name='prpLchargeChargeAmountNTD']").each(function(){
		sumNoDutyFee +=$parseFloat(this.value);
	});
	// 标的损失赔款
	var $sumDutyPaid = $(":input[name='prpLcompensateSumDutyPaid']");
	$sumDutyPaid.val(pointTwo(sumDutyPaid));
	var $sumNoDutyFee = $(":input[name='prpLcompensateSumNoDutyFee']");
	$sumNoDutyFee.val(pointTwo(sumNoDutyFee));
	var $sumPrePaid = $(":input[name='prpLcompensateSumPrePaid']");
	var $sumPaid = $(":input[name='prpLcompensateSumPaid']");
	var $sumThisPaid = $(":input[name='prpLcompensateSumThisPaid']");
	$sumThisPaid.val(pointTwo(sumDutyPaid-$parseFloat($sumPrePaid.val())));
	$sumPaid.val(pointTwo(sumDutyPaid-$parseFloat($sumPrePaid.val())+sumNoDutyFee));
	if (fm.buttonCoins) {
		creatCoins();
		creatCoinsFlag('1');
		resetChangelossCharge();
	}
}
//add by zhangyurui 2009-02-27 修改费用代码是清空支付对象 begin

function clearPayObject(field) {
	var i = getElementOrder(field) - 1;
	var prpLchargePayObjectNameList = document.getElementsByName("prpLchargePayObjectName");
	var prpLchargePayObjectCodeList = document.getElementsByName("prpLchargePayObjectCode");
	prpLchargePayObjectNameList[i].value = "";
	prpLchargePayObjectCodeList[i].value = "";
}
//add by zhangyurui 2009-02-27 修改费用代码是清空支付对象 end
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
	var passDayList = document.getElementsByName("passDay");
	if (passDayList.length > 0 && passDayList[0] != null && passDayList[0].value != 0) {
		alert(i18n.commonAcci.compensate.receiveCustomerOver +passDayList[0].value + i18n.commonAcci.compensate.dayPleaseDeal); //收到客户索赔申请已过     天，请尽快处理！
	}
	var chiefflag = fm.chiefflag.value;
	var coinsFlag = fm.coinsFlag.value;
	var message = "";
	var shareHolderFlag = fm.shareHolderFlag.value;
	var payFee = parseInt(fm.payFee.value);
	if (payFee == -1) {
		alert(i18n.claim.premiumNotPaid); //此保单保费未缴！\n
	} else if (payFee == 0) {
		alert(i18n.claim.premiumNotComplete); //此保单保费未缴全！\n
	} else if (payFee == -2) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
	}
	if (coinsFlag == 1) {
		alert(i18n.commonAcci.compensate.policyPayGenerateInfoTotalLoss1); //本保单为主共保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
	}
	if (coinsFlag == 2) {
		alert(i18n.commonAcci.compensate.policyPayGenerateInfoTotalLoss3); //本保单为从共保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
	}
	if (coinsFlag == 3) {
		alert(i18n.commonAcci.compensate.policyPayGenerateInfoTotalLoss4); //本保单为从联保单,请注意生成联共保分摊信息!\n注意輸入损失时请輸入总共的损失！
	}
	if (message.length > 0) {
		alert(message);
	}
	//reasion:理算初始化时，签单币别不是CNY时，弹出当前兑换率
	if (fm.BaseCurrency1.value != CURRENCYINFO.LOCAL_CURRENCY) {
		alert(i18n.claim.singleCurrency + fm.BaseCurrency1.value + i18n.claim.useSparingly + fm.ExchRate1.value); //此案件签单币别为      ，不是CNY，请慎重处理！\n当前兑换率为
	}
	//设置给付类别为只读
	initPaymentType();
	initPrpLpersonloss();
	initCurrency();
	//mantis：CLM0292 ，處理人員：DP0713，需求單編號：新核心-日額保險金額卡控
	checkPayType1SumAmount(true);
	return true;
}
/** 设置汇率是否可以输入 */
function initCurrency(){
	$("tr[name='prpLpersonFeeLossPaymentTr']").each(function(){
		var $currency = $(this).find(":input[name='prpLpersonLossCurrency']");
		var $exchRate = $(this).find(":input[name='prpLpersonLossExchRate']");
		if($currency.val()==CURRENCYINFO.LOCAL_CURRENCY){
			$exchRate.attr("readOnly",true);
			$exchRate.val(1);
		}
	});
	$("tr[name='chargeObjectTr']").each(function(){
		var $currency = $(this).find(":input[name='prpLchargeCurrency']");
		var $exchRate = $(this).find(":input[name='prpLchargeExchRate']");
		if($currency.val()==CURRENCYINFO.LOCAL_CURRENCY){
			$exchRate.attr("readOnly",true);
			$exchRate.val(1);
		}
	});
	
}
//增加提示责任明细必须输入一项

function CheckDetail() {
	var erroMessage = "";
	if (document.getElementsByName('personLossSerialNo').length < 2) {
		erroMessage = erroMessage + "您需要在'被保險人賠付信息'欄增加至少一條'費用信息'!\n"
	}
	if (erroMessage.length > 0) {
		alert(erroMessage);
		return false;
	}
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

/***
 * mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核
 * 赔付对象讯息校验 222
 * @returns {Boolean}
 */
function checkPrpLpayObjectInfo2() {
	var checkFlag = true;
	$("#PayAccountInfo").find("tr[name='PrpLpayObjectInfo']").each(function (i) {
		var areaCode = $(this).find(":input[name='prpLpayObjectInfoAreaCode']"); //郵遞區號
		if(undefined!=areaCode && null!=areaCode){
			 var oAreaCode2 = trim(areaCode.val());
			 var areaCode2 = trim(areaCode.val()).replace(/[^\d]/g,'');
			 if(oAreaCode2.length > 3){
					alert("第 " + (i + 1) + " 条費用資訊‘郵遞區號’ 長度超過3位數!");
					checkFlag = false;
					return false; 
			 }else 
			 if(oAreaCode2 != areaCode2){
				alert("第 " + (i + 1) + " 条費用資訊‘郵遞區號’ 只能輸入數值!");
				checkFlag = false;
				return false; 
			 }
		}
    });
	return checkFlag;
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	//mantis：CLM0292 ，處理人員：DP0713，需求單編號：新核心-日額保險金額卡控 START
	if (checkPayType1SumAmount(false) == false) {
		return false;
	}
	//mantis：CLM0292 ，處理人員：DP0713，需求單編號：新核心-日額保險金額卡控 END
	if(saveType == "4"){
		if (checkUndwrt() == false) {
			return false;
		}
		//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
	    if (!(checkPrpLpayObjectInfo2())) {
	        return false;
	    }
	    //mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
		var errorMessage = "";
		if (fm.prpLcompensateNoPaidClaim.value == "1" && fm.prpLcompensateSumPaid.value != '0'){
			errorMessage = errorMessage + "零結賠案不計次為'是'，賠款金額只能為零！\n";
		}
		
		//如果輸入了一级巨灾代码，那么二级代码也必须輸入 
		//增加是否代付赔款判断
		var isPayForOther = document.getElementsByName("isPayForOther");
		if (isPayForOther.length > 1 && isPayForOther[0].checked == false && isPayForOther[1].checked == false) {
			errorMessage = errorMessage + "請選擇是否代付賠款\n";
		}
		// 增加是否代付赔款判断 end
		//mantis： CLM0119 ，處理人員：DP0728 蘇英碩，需求單編號：CLM0119.新核心-必填欄位文件齊備日 - start
		var $prpLcompensateFileReadyDate = $(":input[name='prpLcompensateFileReadyDate']");
		if($.trim($prpLcompensateFileReadyDate.val()) == ''){
			return alertMessage($prpLcompensateFileReadyDate[0],"理算文件備齊日必須輸入!");
		}
		//mantis：CLM0283 ，處理人員： DP0713 ，需求單編號：理算任務的理算文件備齊日不可大於該賠案新增當日日期(PA、TA、GA) START
		var riskCodeCheck = $("input[name='prpLcompensateRiskCode']").val();
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
		    
		//mantis： CLM0119 ，處理人員：DP0728 蘇英碩，需求單編號：CLM0119.新核心-必填欄位文件齊備日 - start
		// 增加提交时触发备注的onblur事件，来判断是否超限额 begin
	//	var prpLpersonLossRemarkList = document.getElementsByName("prpLpersonLossRemark");
	//	for (var i = 1; i < prpLpersonLossRemarkList.length; i++) {
	//		if (!checkAmount(prpLpersonLossRemarkList[i])) {
	//			return false;
	//		}
	//	}
		// 增加提交时触发备注的onblur事件，来判断是否超限额 end
		//判断是否还有申请未处理 2005-08-07
		if (fm.AcciClaimFlag.value == 'N') {
			alert(i18n.commonAcci.claim.applicateProcessNotSubmit); //还有调查申请没有处理，不能提交!
			return false;
		}
		if (fm.checkFlag.value == 'N') {
			alert(i18n.commonAcci.claim.applicateProcessNotSubmit); //还有调查申请没有处理，不能提交!
			return false;
		}
	
		var context = fm.prpLctextContextInnerHTML.value;
		if (context.length < 1) {
			errorMessage = errorMessage + "理算說明不允許為空\n";
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
		//行动电话
		for (var i = 1; i < fm.prpLpayObjectInfoMobilePhoneNo.length; i++) {
		var prpLpayObjectInfoMobilePhoneNo =fm.prpLpayObjectInfoMobilePhoneNo[i].value;
		if (prpLpayObjectInfoMobilePhoneNo.length > 0) {
			    var reg =/^09[0-9]{8}$/;
			    if(!reg.test(prpLpayObjectInfoMobilePhoneNo)){
			    	errorMessage =errorMessage +"受款人"+i+"行動電話有誤\n";
			    }
			    		
		}  
		}//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化  end 
		//增加联共保的判断
//		if (fm.chiefflag.value == "1" || fm.chiefflag.value == '3') {
//			if (fm.all("lossOrChargeHaveChanged") != null && fm.lossOrChargeHaveChanged.value == '1') {
//				errorMessage = errorMessage + i18n.commonAcci.compensate.amountChangedShareInfoSave; //金额已发生变化，请选择'生成联共保分摊信息'按钮，重新生成联共保信息後再保存！
//			}
//		}
		if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		}
		for (var i = 1; i < fm.prpLpayObjectInfoUniformNo.length; i++) {
 			var prpLpayObjectInfoCertificateCode = fm.prpLpayObjectInfoCertificateCode[i].value; //證件類型
 			var prpLpayObjectInfoUniformNo = fm.prpLpayObjectInfoUniformNo[i].value; //證件代碼
 			if (prpLpayObjectInfoCertificateCode == "01" && !checkIdentifyNumber(prpLpayObjectInfoUniformNo, "9")) {
 				alert("請爲賠款給付對象訊息  賠付對象 " + i + " 錄入正確的身份證號");
 				return false;
 			}
 			if (prpLpayObjectInfoCertificateCode == "02" && !checkUniformNo(prpLpayObjectInfoUniformNo)) {
 				alert("請爲賠款給付對象訊息  賠付對象 " + i + " 錄入正確的統一編號");
 				return false;
 			}
 		}
		//檢查分攤金額是否正確
//		if(fm.chiefflag.value == "1"){
//			if(!checkChargeAmount()){
//				return false;
//			}
//		}
     	var personLen = $(":input[name='prpLpersonLossKindCode']").length;
     	var chargeLen = $(":input[name='prpLchargeKindCode']").length;
     	if(personLen<=1&&chargeLen<=1){
     		alert(i18n.compensate.personClaimBook); //赔款计算书中的赔付标的，赔款费用至少有一条记录!
     		return false;
     	}
		//效验人员信息
		if(!checkPersonLoss()||!checkPrpLcharge()||!checkPayObjectInfo()){
			return false;
		}
		//校驗各險別賠付是否超出限額
		if(!checkKindPay()){
			return false;
		}
	}
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
	if(!checkLossAmountForComp(field, saveType)){
		return false;
	}
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核(理算) START
	if(!checkBeyondSumAmount()){
		return false;
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
	var checkflag = true;
	$("#Charge").find(":input[name='prpLchargeChargeCode']").each(function(i,e){
		if($.trim(e.value).length == 0){
			checkflag = alertMessage(e, "第 " + (i + 1) + " 筆費用資訊‘費用代碼’、‘費用名稱’必須輸入!");
			return false;
		}
	});
	if(!checkflag){
		return false;
	}
	if (confirm(i18n.commonAcci.compensate.caseFinallyIndemnity + fm.prpLcompensateSumThisPaid.value + i18n.commonAcci.compensate.feeAmount + fm.prpLcompensateSumNoDutyFee.value + i18n.commonAcci.compensate.pleaseConfirm)) {} else { //案件最终 赔款金额为：    ,费用金额为：     ,请确认！
		undisablebutton();
		return false;
	}
	$(":input[name='buttonSaveType']").val(saveType);
	$(":input[name='buttonSave']").attr("disabled",true);
	$(":input[name='buttonSaveFinishSubmit']").attr("disabled",true);
	$(":input[name='buttonCancel']").attr("disabled",true);
	$(":input[name='sendUndwrt']").attr("disabled",true);
	saveFromUnDisabled();
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
//	fm.submit();
//	return true;
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
	
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
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
		undisablebutton();
		return false;
	}
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
}

/**
 * mantis：CLM0292 ，處理人員：DP0713，需求單編號：新核心-日額保險金額卡控
 */
function checkPayType1SumAmount(onloadFlag){
	var clauseKindCheck = true;
	try{
		var $span_prpDclauseKindList = $("span[name='span_prpDclauseKind']").find(":input[name='init_data_ck_kindCode']");
		$("tr[name='prpLpersonFeeLossTr']").each(function(){
			var $kindCode = $(this).find(":input[name='prpLpersonLossKindCode']");
			var $paymentType = $(this).find("input[name='prpLpersonLossPaymentType']");//給付類別 (22)
			var $lossAmount = $(this).find(":input[name='prpLpersonLossAmount']");//保險金額
			var $lossSumRealPay = $(this).find(":input[name='prpLpersonLossSumRealPay']");//賠付金額
			var $lossSumRealPayNTD = $(this).find(":input[name='prpLpersonLossSumRealPayNTD']");//賠償金額 （NTD）
			
			$span_prpDclauseKindList.each(function(){
				var kindCodeVal = $(this).val();
				if($kindCode.val() == kindCodeVal && clauseKindCheck){
					$paymentType.val('22');
					$paymentType.prop("readonly", true);
					
					var accidentVal = $(this).siblings("input[name='init_data_ck_accident']");//日/天數--每一事故日數
					//(2)	賠償金額上限為小於或等於保單的”每一事故日數”x”保險金額”，
					var relPayAmount = parseInt(accidentVal.val(), 10) * parseInt($lossAmount.val(),10);
					if(!onloadFlag && !(parseInt($lossSumRealPayNTD.val(),10) <= relPayAmount)){
						alert("被保險人賠付訊息"+kindCodeVal+" 賠償金額>保險金上限，請確認。");
						clauseKindCheck =false;
						return false;
					}
				}
			});
		});
	}catch(e){}
	return clauseKindCheck;
}

/**
 * mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
 */
function checkLossAmountForComp(field, saveType){
	var $span_prpDpolicyRules = $("span[name='span_prpDpolicyRules']").find(":input[name='init_data_rulCode']");
	var $prpLclaimAddressCode = $("input[name='prpLcompensateAddressCode']");
	var check = true;//prpLclaimLossKindCode
	var amountCheck = true;
	var hasTr47 = false;
	//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 START
	//理算
	var hasTr48 = false;
	var taKindVal = "";//init_data_hisKind hisKind
	$("input[name='hisKind']").each(function() {
		if($(this).val()=="TR47"||$(this).val()=="TR48" ){
			taKindVal = $(this).val();
		}
	});
	//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 END
	$("input[name='prpLpersonLossKindCode']").each(function() {
	    var kindCode = $(this).val();
	    if(kindCode=="TR47"){//有TR47 檢查列表內是否為$prpLclaimAddressCode填寫
	    	hasTr47 = true;
	    	check = false;
			amountCheck = false;
	    	$span_prpDpolicyRules.each(function(){
				var rulKindCode = $(this).val();
				if($prpLclaimAddressCode.val() == rulKindCode){////901,902,903
					check = true;
				}
			});
	    	
	    }
	    //mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 START
	    if(kindCode=="TR48"){//有TR47 檢查列表內是否為$prpLclaimAddressCode填寫
	    	hasTr48 = true;
	    	check = false;
			amountCheck = false;
	    	$span_prpDpolicyRules.each(function(){
				var rulKindCode = $(this).val();
				if($prpLclaimAddressCode.val() == rulKindCode){////901,902,903
					check = true;
				}
			});
	    	
	    }
	    //mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 END
		if(!check){
			alert("請修正基本訊息的出險地點");
			return false;
		}
	});
	//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示  START
	if(hasTr47 || hasTr48){//有TR47 僅能TR47不能帶其他險類
		$("input[name='prpLpersonLossKindCode']").each(function() {
		    var kindCode = $(this).val();
		    if(kindCode!=""){
		    	if(!(kindCode=="TR47" || kindCode=="TR48") && check){
			    	check = false;
					alert(taKindVal+"海外突發疾病健康保險，不能再選擇其他險別");
					return false;
			    } 
		    }
		});
	}
	//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示  END
	if(check){
		$span_prpDpolicyRules.each(function() {
			var rulKindCode = $(this).val();
			if($prpLclaimAddressCode.val() == rulKindCode){
				check = false;//有選擇到事故區域FOR TR47，必須檢核
				amountCheck = false;
				$("input[name='prpLpersonLossKindCode']").each(function() {
					var kindCode = $(this).val();
					//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示
					if(kindCode == "TR47" || kindCode == "TR48"){
						check = true;
					}
				});
			}
			if(!check){
				//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示
				alert("出險地點為指定地區時，必須有"+taKindVal+"海外突發疾病健康保險");
				return false;
			}
		});
	}
	if(!amountCheck && check){
		//地址以及險種 都驗證結束 來驗證金額
		$("input[name='prpLpersonLossKindCode']").each(function(i,n) {
			
			var _kindCode = $(this).val();
			var _Amount ="";
			var _SumClaim = "";
			$("input[name='prpLpersonLossAmount']").each(function(i2,n2) {
				if(i==i2){
					_Amount = $(this).val();
				}
			});
			$("input[name='prpLpersonLossSumRealPay']").each(function(i3,n3) {
				if(i==i3){
					_SumClaim = $(this).val();
				}
			});
			//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示
			if(_kindCode=="TR47" || _kindCode=="TR48"){
				if(parseInt(_SumClaim,10)<=parseInt(_Amount,10)){
					amountCheck = true;
				}
			}
		});
		if(!amountCheck){
			//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示
			alert("賠損人的被保險人賠付訊息"+taKindVal+"賠付金額必須小於等於保險金額!");
			return false;
		}
	}
	if(amountCheck && check){
		return true;
	}
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

//索赔申请人----------------------------------------

function checkProposer() {
	if (getRowsCount("Proposer") == 0) {
		errorMessage("索賠申請人信息至少要有一條記錄!");

		return false;
	}

	for (var j = 1; j < fm.proposerName.length; j++) {
		if (isEmptyField(fm.proposerName[j])) {
			errorMessage("第" + j + "条索赔申请人姓名不能为空!");
			fm.proposerName[j].focus();
			return false;
		}
		if (isEmptyField(fm.proposerIdentifyNumber[j])) {
			errorMessage("第" + j + "条索赔申请人身份證字號不能为空!");
			fm.proposerIdentifyNumber[j].focus();
			return false;
		}
		if (isEmptyField(fm.relationCode[j])) {
			errorMessage("第" + j + "条索赔申请人身份證字號不能为空!");
			fm.relationCode[j].focus();
			return false;
		}
	}
	//若选择拒赔则必须输入拒赔原因
	if (fm.result.value == "0" && isEmptyField(fm.prpLcompensateRemark)) {
		errorMessage("請輸入拒付原因");
		fm.prpLcompensateRemark.focus();
		return false;
	}
	return true;
}


function getRowsCount(PageCode) {
	var oTBODY = document.all(PageCode).tBodies.item(0);
	var intCount = oTBODY.rows.length;
	return intCount;
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
 @description 生成审核批文
 @param       无
 @return      无
 */

function generateCtext(flag) {
	var erroMessage = "";
	if (document.getElementsByName('prpLpersonLossPersonNo').length < 2) {
		erroMessage = erroMessage + "您需要在'被保險人賠付訊息'欄增加至少一條'賠付訊息'!\n"
	}
	if (erroMessage.length > 0) {
		alert(erroMessage);
		return false;
	}
	if (checkForm() == false) {
		return false;
	}
	fm.GenerateCompensateFlag.value = flag;
	var oldAction = fm.action;
	var oldTarget = fm.target;
	$("tr[name='prpLpersonFeeLossPaymentTr']").each(function (){
		$(this).find(":input").each(function(i,n){
			if($(n).attr("disabled")=="true"||$(n).attr("disabled")=="disabled"){
				$(n).attr("cssDisabled",$(n).attr("disabled"));
				$(n).attr("disabled",false);
			}
		});
	})
	fm.action = "/claim/compensate/compensateGenerate.do";
	fm.target = "fraCalculate";
	fm.submit();
	fm.action = oldAction;
	fm.target = oldTarget;
	$("tr[name='prpLpersonFeeLossPaymentTr']").each(function (){
		$(this).find(":input").each(function(i,n){
			if($(n).attr("cssDisabled")=="true"||$(n).attr("cssDisabled")=="disabled"){
				$(n).attr("disabled",$(n).attr("cssDisabled"));
				$(n).removeAttr("cssDisabled");
			}
		});
	})
	return true;
}


//按钮单击事件，用於条款的显示
function buttonOnClick(fieldObject) {
	var intIndex = parseInt(fieldObject.num);
	var spanId = 'span_Engage_Context';
	if (isNaN(fm.button_Engage_Open_Context.length)) {} else { //多行
		spanId = 'span_Engage_Context' + "[" + intIndex + "]";
	}
	showSubPage2(spanId);
}


//按钮单击事件，用於相同保单号码多报案的显示

function buttonOnClick2(strSubPageCode) {
	var sameCount = parseInt(fm.PerilCount.value);

	if (sameCount < 1) {
		fm.button_Peril_Open_Context.disabled = true;
		return;
	}
	showSubPage2(strSubPageCode);

}

//显示输入框
//leftMove 默认值0，坐标左移leftMove

function showSubPage2(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;

	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}
//按钮单击事件，用於相同保单号码多报案的显示

function buttonOnClick(strSubPageCode) {
	var sameCount = parseInt(fm.PerilCount.value);

	if (sameCount < 1) {
		fm.button_Peril_Open_Context.disabled = true;
		return;
	}
	showSubPage1(strSubPageCode);

}

//显示输入框
//leftMove 默认值0，坐标左移leftMove

function showSubPage1(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;
	ey = ey + 10;
	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}


//function creatCoins() {
//	var countFlag = fm.countFlag.value;
//	if (isSameKindCode() == false) {
//		return false;
//	}
//	creatCoins();
//	return true;
//}

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

function deleteallRow1(pageCode, dataPageCode)　　 {　　
	var index = 0; //当前table索引
	　　
	var oTBODY = document.getElementsByName(pageCode)[0].tBodies.item;　　
	var oTBODYData = document.getElementById(pageCode).tBodies.item(0);
	var oldelementNumber = oTBODYData.rows.length;　　　　
	for (var i = 0; i < oldelementNumber; i++)　　 {　　
		oTBODYData.removeChild(oTBODYData.rows[0]);　　
	}　　
}

function creatCoinsFlag(countFlag) {
	fm.countFlag.value = countFlag;
}

function collectCompensate(field) {
	index = getElementOrder(field) - 1;
//	if (undefined != fm.prpLchargeSumRealPay[index] || undefined != fm.prpLchargeChargeReport[index]) {
//		if ((parseFloat(fm.prpLchargeSumRealPay[index].value) - parseFloat(fm.prpLchargeChargeReport[index].value)) > 0) {
//			alert(i18n.commonAcci.compensate.includedPaymentAmount); //计入赔款金额不能大於费用金额！
//			fm.prpLchargeChargeAmount[index].value = fm.prpLchargeChargeReport[index].value;
//			fm.prpLchargeSumRealPay[index].value = 0;
//		}
//	}

	var policyNo = fm.policyno.value;
	var riskCode = fm.prpLcompensateRiskCode.value;
	var baseCurrency = '';
	var exchCurrency = fm.MergeCurrency.value; //目标币别
	var chargeRealPay = "0";
	var chargeAmount = "0";
	var personLossRealPay = "0";
	for (var n = 1; n < fm.all("prpLchargeSumRealPay").length; n++) {
		baseCurrency = baseCurrency + "," + fm.prpLchargeCurrency[n].value;
		if (fm.prpLchargeSumRealPay[n].value == '')
			fm.prpLchargeSumRealPay[n].value = 0;
		if (fm.prpLchargeChargeReport[n].value == '')
			fm.prpLchargeChargeReport[n].value = 0;
		chargeRealPay = chargeRealPay + ',' + fm.prpLchargeSumRealPay[n].value;
		chargeAmount = chargeAmount + ',' + (fm.prpLchargeChargeReport[n].value - fm.prpLchargeSumRealPay[n].value);
	}
	//赔付人员
	var personLossData = getTableElements("PersonFeeLoss");
	var intPersonLossCount = personLossData.length;
	for (i = 1; i < fm.all("prpLpersonLossSumRealPay").length; i++) {
		if (isNaN(parseFloat(fm.all("prpLpersonLossSumRealPay")[i].value)))
			fm.all("prpLpersonLossSumRealPay")[i].value = "0";
		personLossRealPay = personLossRealPay + "," + fm.all("prpLpersonLossSumRealPay")[i].value;
	}
	var inputObject = field;
	var outputObject;
	dblSumPrePaid = fm.prpLcompensateSumPrePaid.value; //已预付赔款
	if (isNaN(dblSumPrePaid))
		dblSumPrePaid = 0;
	setChangelossChargeFlag();

	var inputArgs = {
		policyNo: policyNo,
		baseCurrency: baseCurrency,
		exchCurrency: exchCurrency,
		chargeRealPay: chargeRealPay,
		chargeAmount: chargeAmount,
		personLossRealPay: personLossRealPay,
		dblSumPrePaid: dblSumPrePaid,
		riskCode: riskCode
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("getSumRealPay", param, "rollbackCalFund", inputObject, outputObject);
	DWREngine.setAsync(true);
}


function rollbackCalFund(inputObject, outputObject, returnObject) {
	var prpLcompensateDto = returnObject;
	fm.prpLcompensateSumDutyPaid.value = point(round(prpLcompensateDto.sumDutyPaid, 0), 0);
	fm.prpLcompensateSumNoDutyFee.value = point(round(prpLcompensateDto.sumNoDutyFee, 0), 0);
	fm.prpLcompensateSumPaid.value = point(round(prpLcompensateDto.sumPaid, 0), 0);
	//reason:SumThisPaid（本次实赔金额）不应该包括费用金额，而sumpaid是包括费用的
	fm.prpLcompensateSumThisPaid.value = point(round(prpLcompensateDto.sumThisPaid, 0), 0);
	fm.prpLdangerRiskSumPaid.value = point(round(prpLcompensateDto.sumThisPaid, 0), 0);
	if (fm.buttonCoins) {
		creatCoins();
		creatCoinsFlag('1');
		resetChangelossCharge();
	}
	undisablebutton();
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
	//根据支付类型的不同，要求带出外部机构或手工輸入支付对象 begin
	if ( payObjectType == "A") { //手工輸入支付对象
		var serialNo = getElementOrder(field) - 1;
		var url = "/claim/pages/common/account/PaymentAccountName.jsp?serialNo=" + serialNo;
		var handle = window.showModalDialog(url, window, "dialogHide:yes;help:no;status:no;scroll:yes;dialogWidth:300px;dialogHeight:460px");
		if (handle == null || handle == "") {
			fm.prpLchargePayObjectName[serialNo].value = ""
		} else {
			getAccountByPayObjectName(field, handle);
			//fm.prpLchargeOwnerName[serialNo].value = fm.prpLchargeOwnerName[serialNo].value;
			//fm.prpLchargePayObjectName[serialNo].value = fm.prpLchargeOwnerName[serialNo].value;
		}
	} else { //带出外部机构
		code_CodeSelect(field, 'getPayObject', '-1,0', 'Y', 'N', chargeCode+"|"+payObjectType);
		//getExternAlagency(field, index);
	}
	//根据支付类型的不同，要求带出外部机构或手工輸入支付对象 end
}

function clearPrpLctextContextInnerHTML() {
	var prpLctextContextInnerHTMLList = document.getElementsByName("prpLctextContextInnerHTML");
	for (var i = 0; i < prpLctextContextInnerHTMLList.length; i++) {
		prpLctextContextInnerHTMLList[i].value = "";
	}
}

//当支付对象为外部机构时，自动带出外部机构的银行帳号

function getExternAlagency(field, index) {
	var inputObject = field;
	var outputObject;
	var ChargeCode = fm.prpLchargeChargeCode[index].value;
	var PayObjectType = fm.prpLchargePayObjectType[index].value;
	var PayObjectCode = fm.prpLchargePayObjectCode[index].value;
	if (PayObjectType == "B" && PayObjectCode != "") {
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
	fm.prpLchargeOwnerShip[findex].options[0].selected = true;
	fm.prpLchargeAccountCode[findex].value = prplexternalagencyDto.accountCode;
	fm.prpLchargeBankName[findex].value = prplexternalagencyDto.bankName;
	fm.prpLchargeBankCode[findex].value = prplexternalagencyDto.bankCode;
	fm.prpLchargeCustomBankCode[findex].value = prplexternalagencyDto.customBankCode;
	fm.prpLchargeCustomBankName[findex].value = prplexternalagencyDto.customBankName;
	fm.prpLchargeOwnerName[findex].value = prplexternalagencyDto.comcname;
	fm.prpLchargeUniformNo[findex].value = prplexternalagencyDto.certifiCateCode;
	fm.buttonAddAcc[findex].disabled = false;

	undisablebutton();
}
// 当支付对象为外部机构时，自动带出外部机构的银行帳号

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
	setChargeAmount(field);
}
//修改费用代码或支付类型或支付对象时，清空相应的付款信息 end
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
	var $prpLcoinsLossFeeType = $(":input[name='prpLcoinsLossFeeType']");
	var $prpLcoinsCoinsSumpaid = $(":input[name='prpLcoinsCoinsSumpaid']");
	var $prpLcoinsCoinsRate = $(":input[name='prpLcoinsCoinsRate']");
	$(":input[name='prpLcoinsCoinsSumpaid']").each(function(i,n){
		if($prpLcoinsLossFeeType[i].value == "0"){
			sumForPaid += $parseFloat($prpLcoinsCoinsSumpaid[i].value);
		}else{
			sumForFee += $parseFloat($prpLcoinsCoinsSumpaid[i].value);
			if($prpLcoinsLossFeeType[i].value  == "1"){
				sumForFeeOfUs = $parseFloat($prpLcoinsCoinsSumpaid[i].value);;
				sumForFeeAll = sumForFeeOfUs / ($parseFloat($prpLcoinsCoinsRate[i].value)/100);
			}
		}
	});
//	for(var i = 0; i < fm.prpLcoinsCoinsSumpaid.length; i ++){
//		if(fm.prpLcoinsTypeForShow[i].value == '0'){
//			sumForPaid += fm.prpLcoinsCoinsSumpaid[i].value;
//		}else{
//			sumForFee += fm.prpLcoinsCoinsSumpaid[i].value;
//			if(fm.prpLcoinsCoinsTypeShow[i].value == '1'){
//				sumForFeeOfUs = fm.prpLcoinsCoinsSumpaid[i].value;
//				sumForFeeAll = sumForFeeOfUs / fm.prpLcoinsCoinsRate[i].value;
//			}
//		}
//	}
	$(":input[name='prpLpersonLossSumRealPay']").each(function(i,n){
		if(i>0){
			sumForPaidAll += $parseFloat(n.value);
		}
	});
//	for(var j = 0; j < fm.prpLpersonLossSumRealPay.length; j ++){
//		sumForPaidAll += fm.prpLpersonLossSumRealPay[j].value;
//	}
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
