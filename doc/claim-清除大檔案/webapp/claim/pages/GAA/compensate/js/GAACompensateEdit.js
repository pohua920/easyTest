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

function setInput(field) {
//	disablebutton();
	var i = getElementOrder(field) - 1;
//	if(fm.all("prpLchargeChargeCode")[i].value == "03"||fm.all("prpLchargeChargeName")[i] == "施救费"){
//		fm.all("prpLchargeSumRealPay")[i].value=fm.all("prpLchargeChargeReport")[i].value;
//		fm.prpLchargeChargeAmount[i].value = 0;
//	}else 
	if ((fm.all("prpLchargeChargeCode")[i].value.length > 0) && (fm.all("prpLchargeChargeName")[i].value.length > 0)) {
		fm.prpLchargeSumRealPay[i].value = 0;//计入赔款金额=0
		if(field!=fm.all("prpLchargeChargeAmount")[i]){
			fm.prpLchargeChargeAmount[i].value = fm.all("prpLchargeChargeReport")[i].value;//费用金额 = 总金额
		}
	} else if ((fm.all("prpLchargeChargeCode")[i].value.length <= 0) || (fm.all("prpLchargeChargeName")[i].value.length <= 0)) {
		fm.prpLchargeSumRealPay[i].value = 0;
		fm.prpLchargeChargeAmount[i].value = 0;
		fm.prpLchargeChargeReport[i].value = 0;
	}
	if(fm.prpLchargeChargeAmount[i].value-fm.all("prpLchargeChargeReport")[i].value>0){
		alert("實際費用不能大於費用金額！請從新錄入。");
		fm.prpLchargeChargeAmount[i].value = 0;
	}
	calCurrencySumPay(field,"3");
//	calFund();
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
	initPayNTD();
	//增加收到客户索赔申请已过天数提示 begin】
	var passDayList = document.getElementsByName("passDay");
	if (passDayList.length > 0 && passDayList[0] != null && passDayList[0].value != 0) {
		alert(i18n.commonAcci.compensate.receiveCustomerOver + passDayList[0].value + i18n.commonAcci.compensate.dayPleaseDeal); // 收到客户索赔申请已过      //天，请尽快处理！
	}
	//增加收到客户索赔申请已过天数提示 end】
	//判断是否是共保单
	var chiefFlag = fm.chiefflag.value;
	var coinsFlag = fm.coinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var message = "";
	var payFee = parseInt(fm.payFee.value);
	var delinquentfeeCase = fm.delinquentfeeCase.value;

	if (payFee == -1) {
		alert(i18n.claim.premiumNotPaid); //此保单保费未缴！\n
	}else if (payFee == 0) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
		message = message + delinquentfeeCase + "\n";
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
	//當保單承保CA險種時，進入理算頁面，需要彈出提示訊息
	var riskCode = $(":input[name='prpLcompensateRiskCode']").val();
	if(riskCode=="CA"){
		alert("本賠案需檢視是否承保A33和133條款之自負額調整。");
	}
	initPersonLoss();
	return true;
}

/**
 * 人伤初始化信息
 * @return
 */
function initPersonLoss(){
	$("tr[name='prpLpersonLossObject']").each(function(){
		var sumRealPay1 = 0;
		$(this).find(":input[name='prpLpersonLossCurrencySumPay']").each(function(){
			if($.isNumeric(this.value)){
				sumRealPay1 = sumRealPay1 + parseFloat(this.value);
			}
		});
		var $sumRealPay1 = $(this).find(":input[name='prpLpersonLossSumRealPay1']");//賠付金額合計
		$sumRealPay1.val(sumRealPay1);
	});
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
 * mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核
 * @returns {Boolean}
 */
function checkPayObjectInfo(){
	var checkFlag = true;
	$("input[name='prpLchargeAreaCode']").each(function (i) {
		var areaCode = $(this).val(); //郵遞區號
		var oAreaCode2 = trim(areaCode);
		var areaCode2 = trim(areaCode).replace(/[^\d]/g,'');
		if(oAreaCode2.length > 3){
			alert("第 " + (i) + " 条賠款費用資訊‘郵遞區號’ 長度超過3位數! ");
			checkFlag = false;
			return false; 
        }else 
		if(oAreaCode2 != areaCode2){
			alert("第 " + (i) + " 条賠款費用資訊‘郵遞區號’ 只能輸入數值! ");
			checkFlag = false;
			return false; 
		}
    });
	if(checkFlag){
		$("[name='PrpLpayObjectInfo']").find("tr[name='AreaInfo']").each(function (i) {
			var areaCode = $(this).find(":input[name='prpLpayObjectInfoAreaCode']").val(); //郵遞區號
			var oAreaCode2 = trim(areaCode);
			var areaCode2 = trim(areaCode).replace(/[^\d]/g,'');
			if(oAreaCode2.length > 3){
				alert("賠付對象 " + (i) + " 費用資訊‘郵遞區號’ 長度超過3位數! ");
				checkFlag = false;
				return false; 
	        }else 
			if(oAreaCode2 != areaCode2){
				alert("賠付對象 " + (i) + " 費用資訊‘郵遞區號’ 只能輸入數值! ");
				checkFlag = false;
				return false; 
			}
	    });
	}
	return checkFlag;
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 START
	if (saveType == "4" && checkPayObjectInfo() == false) {
		return false;
	}
	//mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 END
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
	if (fm.chiefflag.value == "1") {
		if (fm.all("lossOrChargeHaveChanged") != null && fm.lossOrChargeHaveChanged.value == '1') {
			errorMessage = errorMessage + i18n.compensate.amountsChanged; //金额已发生变化，请选择'生成联共保分摊信息'按钮，重新生成联共保信息後再保存！
		}
	}

	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm, 'Engage_Data,lLoss_Data,PersonFeeLoss_Data,Person_Data,Charge_Data')) {
		return false;
	}
	var underWriteFlag = fm.prpLcompensateUnderWriteFlag.value;
	if (saveType == 4) {
		//判断理算报告是不能为空的。	
		var context2 = fm.prpLltextContextInnerHTML.value;
		var lltext2title = fm.tdLltextTitle.value;
		var context = $(":input[name='prpLltextContextInnerHTML']").val();
		if ($.trim(context).length == 0) {
			alert("請選擇理算說明類型或錄入理算說明！");
			return false;
		}

		//赔付标的信息或赔付人员信息二者其一必须填写，否则不能提交实赔
		var lossLen = $(":input[name='prpLlossDtoKindCode']").length;
	 	var personLen = $(":input[name='prpLpersonLossKindCode']").length;
	 	var chargeLen = $(":input[name='prpLchargeKindCode']").length;
	 	
		if (lossLen <=1 && personLen<=1 && chargeLen <=1) {
			alert(i18n.compensate.claimBook); //赔款计算书中的赔付标的，赔款费用至少有一条记录!
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
		if(!checkPrpLpersonLoss()){
			return false;
		}
		if(!checkPrpLpayObjectInfo()){
			return false;
		}
		if(!checkPrpLcharge()){
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
		 errorMessage ="";
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
		for (var i = 1; i < fm.prpLpayObjectInfoMobilePhoneNo.length; i++) {
			var prpLpayObjectInfoMobilePhoneNo =fm.prpLpayObjectInfoMobilePhoneNo[i].value;
			if (prpLpayObjectInfoMobilePhoneNo.length > 0) {
	    		  var reg =/^09[0-9]{8}$/;
	    		  if(!reg.test(prpLpayObjectInfoMobilePhoneNo)){
	    			  errorMessage =errorMessage +"受款人"+i+"行動電話有誤\n";
	    		  }
			}
		 }
		for (var i = 1; i < fm.prpLpersonLossTelephoneNo.length; i++) {
			var prpLpersonLossTelephoneNo =fm.prpLpersonLossTelephoneNo[i].value;
			if (prpLpersonLossTelephoneNo.length > 0) {
	    	 if (prpLpersonLossTelephoneNo.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLpersonLossTelephoneNo)){
			    		errorMessage =errorMessage +"受害人"+i+"電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLpersonLossTelephoneNo)){
			    	errorMessage =errorMessage +"受害人"+i+"電話有誤\n";
			      }
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
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 START
	field.disabled = true;
//	fm.submit();
	
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
		field.disabled = false;
		return false;
	}
	
	//mantis：CLM0126，處理人員：DP0713，需求單編號：受款人ID檢核 END
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


//按钮单击事件，用於条款的显示

/*function buttonOnClick(fieldObject) {
	var intIndex = parseInt(fieldObject.num);
	var spanId = 'span_Engage_Context';
	if (isNaN(fm.button_Engage_Open_Context.length)) {} else { //多行	
		spanId = 'span_Engage_Context' + "[" + intIndex + "]";
	}
	showSubPage2(spanId);
}*/


/**
 @author      任轶群
 @description 计算赔付标的和人员核损金额之和
 @param       无
 @return      无
 @see         UICommon.js#point、round
*/

function calLoss() {
	//定义变量
	var dblSumLoss = 0;
	var LossSumLoss = 0;
	var PersonLossSumLoss = 0;
	var i = 0;

	for (i = 1; i < fm.all("prpLlossDtoSumLoss").length; i++) {
		LossSumLoss = parseFloat(fm.all("prpLlossDtoSumLoss")[i].value);

		if (isNaN(LossSumLoss))
			LossSumLoss = 0;

		dblSumLoss = dblSumLoss + LossSumLoss;
	}
	//fm.prpLcompensateSumLoss.value = point(round(dblSumLoss, 0), 0);
}



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
	var $curr = $(field).parents("tr[name='prpLlossObject']");
	var kindCode = $curr.find(":input[name='prpLlossDtoKindCode']").val();
	var dangerNo = $curr.find(":input[name='prpLlossDtoDangerNo']").val();
	var itemCode = $curr.find(":input[name='prpLlossDtoItemCode']").val();
	$("#lLoss").find("tr[name='prpLlossObject']").each(function(i,e){
		if(e != $curr[0]){
			var prpLlossDtoDangerNo = $(e).find(":input[name='prpLlossDtoDangerNo']").val();
			var prpLlossDtoKindCode = $(e).find(":input[name='prpLlossDtoKindCode']").val();
			var prpLlossDtoItemCode = $(e).find(":input[name='prpLlossDtoItemCode']").val();
			if(prpLlossDtoDangerNo == dangerNo && prpLlossDtoKindCode !="" && prpLlossDtoKindCode == kindCode && prpLlossDtoItemCode !="" && prpLlossDtoItemCode == itemCode){
				alert(i18n.commonLiab.compensate.inputRiskMarkExist); //您輸入的险别和标的已经存在
				$curr.find(":input[name='prpLlossDtoItemCode']").val("");
				$curr.find(":input[name='prpLlossDtoLossName']").val("");
				$curr.find(":input[name='prpLlossDtoAmount']").val(0);
				return false;
			}
		}
	});
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
		$curr.find(":input[name='prpLlossDtoDeductible']").val(0);
		$curr.find(":input[name='prpLlossDtoDeductibleRate']").val(0);
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

function creatCoinsFlag(countFlag) {
	fm.countFlag.value = countFlag;
}




function calFund() {
	setChangelossChargeFlag();
	var policyNo = fm.policyno.value;
	var riskCode = fm.prpLcompensateRiskCode.value;
	var baseCurrency = '';
	var exchCurrency = fm.MergeCurrency.value; //目标币别
	var chargeRealPay = 0;
	var chargeAmount = 0;
	var personLossRealPay = 0;	
	for (i = 1; i < fm.all("prpLchargeChargeAmountNTD").length; i++) {
		if (fm.all("prpLchargeChargeAmountNTD")[i].value == '' || isNaN(fm.all("prpLchargeChargeAmountNTD")[i].value)){
			fm.all("prpLchargeChargeAmountNTD")[i].value = "0";
		}
		chargeRealPay += parseFloat(fm.all("prpLchargeChargeAmountNTD")[i].value);
	}
	//赔付财产
	var lossRealPay = 0;
	var dblSumRest = 0;
	var dblSumPrePaid = fm.prpLcompensateSumPrePaid.value;
	if (dblSumPrePaid == '' || isNaN(dblSumPrePaid)){
		dblSumPrePaid = 0;
	}
	for (i = 1; i < fm.all("prpLlossDtoSumRealPayNTD").length; i++) {
		if (fm.all("prpLlossDtoSumRealPayNTD")[i].value == '' || isNaN(fm.all("prpLlossDtoSumRealPayNTD")[i].value)){
			fm.all("prpLlossDtoSumRealPayNTD")[i].value = "0";
		}
		if (fm.all("prpLlossDtoSumRest")[i].value == '' || isNaN(fm.all("prpLlossDtoSumRest")[i].value)){
			fm.all("prpLlossDtoSumRest")[i].value = "0";
		}
		lossRealPay += parseFloat(fm.all("prpLlossDtoSumRealPayNTD")[i].value);
		dblSumRest += parseFloat(fm.all("prpLlossDtoSumRest")[i].value)*parseFloat(fm.all("prpLlossDtoExchRate")[i].value);

	}
	for (i = 1; i < fm.all("prpLpersonLossSumRealPayNTD").length; i++) {
		if (fm.all("prpLpersonLossSumRealPayNTD")[i].value == '' || isNaN(fm.all("prpLpersonLossSumRealPayNTD")[i].value)){
			fm.all("prpLpersonLossSumRealPayNTD")[i].value = "0";
		}
		if (fm.all("prpLpersonLossSumRest")[i].value == '' || isNaN(fm.all("prpLpersonLossSumRest")[i].value)){
			fm.all("prpLpersonLossSumRest")[i].value = "0";
		}
		lossRealPay = lossRealPay + parseFloat(fm.all("prpLpersonLossSumRealPayNTD")[i].value);
		dblSumRest = dblSumRest + parseFloat(fm.all("prpLpersonLossSumRest")[i].value) * parseFloat(fm.all("prpLpersonLossExchRate")[i].value);
	}
	fm.prpLcompensateSumDutyPaid.value = point(round(lossRealPay, 0), 0);
	fm.prpLcompensateSumNoDutyFee.value = point(round(chargeRealPay, 0), 0);
	fm.prpLcompensateSumPaid.value = point(round(lossRealPay+chargeRealPay, 0), 0);
	fm.prpLcompensateSumThisPaid.value = point(round(lossRealPay-dblSumPrePaid, 0), 0);
	fm.prpLdangerRiskSumPaid.value = point(round(lossRealPay+chargeRealPay, 0), 0); //危险单位赋值
	fm.prpLcompensateSumRest.value = point(round(dblSumRest, 0), 0);
	if (fm.buttonCoins) {
		creatCoins();
		creatCoinsFlag('1');
		resetChangelossCharge();
	}
}



function rollbackCalFund(inputObject, outputObject, returnObject) {
	var prpLcompensateDto = returnObject;
	fm.prpLcompensateSumDutyPaid.value = point(round(prpLcompensateDto.sumDutyPaid, 0), 0);
	fm.prpLcompensateSumNoDutyFee.value = point(round(prpLcompensateDto.sumNoDutyFee, 0), 0);
	fm.prpLcompensateSumPaid.value = point(round(prpLcompensateDto.sumPaid, 0), 0);
	fm.prpLcompensateSumThisPaid.value = point(round(prpLcompensateDto.sumThisPaid, 0), 0);
	fm.prpLdangerRiskSumPaid.value = point(round(prpLcompensateDto.sumThisPaid, 0), 0); //危险单位赋值
	fm.prpLcompensateSumRest.value = point(round(prpLcompensateDto.sumRest, 0), 0);
	if (fm.buttonCoins) {
		creatCoins();
		creatCoinsFlag('1');
		resetChangelossCharge();
	}
	undisablebutton();
}

function rollbackCalRealpay(inputObject, outputObject, returnObject) {
	var prpLlossDto = returnObject;
	var fieldname = inputObject.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}

	fm.all("prpLlossDtoSumRealPay")[findex].value = prpLlossDto.sumRealPay;
	var isMain = 1;
	var kindCode = fm.all("prpLlossDtoKindCode")[findex].value;
	var kindCodeFlag = kindCode.substring(0, 1);
	if (kindCodeFlag == 0) {
		isMain = 1;
	} else {
		isMain = 0;
	}
	var Amount;
	if (fm.all("prpLlossDtoAmountDisplay")[findex]) {
		Amount = parseFloat(fm.all("prpLlossDtoAmountDisplay")[findex].value);
	} else {
		Amount = parseFloat(fm.all("prpLlossDtoAmountDisplay")[0].value);
	}
	if (isMain == 1 && prpLlossDto.sumRealPay > Amount) {
		alert(i18n.compensate.cannotAmount); //赔偿金额不能大於保险金额
		fm.all("prpLlossDtoSumLoss")[findex].focus;
		return false;
	}
	calCurrencySumPay(inputObject,"1");
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
	setInput(field);
	//checkBeyondQuota(field);
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
	for(var i = 0; i < fm.prpLcoinsCoinsSumpaid.length; i ++){
		if(fm.prpLcoinsTypeForShow[i].value == '0'){
			sumForPaid += fm.prpLcoinsCoinsSumpaid[i].value;
		}else{
			sumForFee += fm.prpLcoinsCoinsSumpaid[i].value;
			if(fm.prpLcoinsCoinsTypeShow[i].value == '1'){
				sumForFeeOfUs = fm.prpLcoinsCoinsSumpaid[i].value;
				sumForFeeAll = sumForFeeOfUs / fm.prpLcoinsCoinsRate[i].value;
			}
		}
	}
	for(var j = 0; j < fm.prpLlossDtoSumRealPay.length; j ++){
		sumForPaidAll += fm.prpLlossDtoSumRealPay[j].value;
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
}
$(document).ready(function(){
	initDamageDate();
});


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