/*****************************************************************************
 * DESC       ：报案登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2013-09-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet() {
	//判断是否是共保、临分、股东业务信息
	var coinsFlag = fm.coinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var message = "";
	// 增加保单注销,报案时间，倒签单，股东业务，出险次数，临分共保等提示； 2005-7-28
	var tempReinsFlag = fm.tempReinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var message = "";

	var othFlag = fm.prpLclaimOthFlag.value;
	if (othFlag.substring(3, 4) == "1") {
		message = message + i18n.claim.policyCancelled;  //此保单已注销！\n
	}
	var payFee = parseInt(fm.payFee.value);
	if (payFee == -1) {
		alert(i18n.claim.premiumNotPaid);  //此保单保费未缴！\n
	} else if (payFee == 0) {
		alert(claim.premiumNotComplete);  //此保单保费未缴全！\n
	} else if (payFee == -2) {
		message = message + i18n.certainLoss.policyPremiumPay;  //此保单已缴未缴全,请慎重处理！！！
	}
	if (coinsFlag != 0) {
		message = message + i18n.claim.totalPolicy;  //本保单为联/共保单！\n
		message = message +i18n.claim.estimateAmount;  //估损金额请輸入分摊前的总估损金额！\n
	}
	if (message.length > 0) {
		alert(message);
	}

	if ("9999" == fm.prpLacciPersonAcciCode.value) {
		fm.prpLacciPersonAcciName.className = "input";
		fm.clickCount.value = "0";
	}
	if (fm.BaseCurrency1.value != CURRENCYINFO.LOCAL_CURRENCY) {
		alert(i18n.claim.singleCurrency + fm.BaseCurrency1.value + i18n.claim.useSparingly + fm.ExchRate1.value); // 此案件签单币别为
	}
	var damageDay = fm.prpLclaimDamageStartDate.value;
	var damage = new Date(damageDay.substring(0, 4), damageDay.substring(5, 7) - 1, damageDay.substring(8, 10));
	var claimStartDay = fm.prpLclaimStartDate.value;
	var claimStart = new Date(claimStartDay.substring(0, 4), claimStartDay.substring(5, 7) - 1, claimStartDay.substring(8, 10));
	var claim_damage = (damage.getTime() - claimStart.getTime()) / (24 * 60 * 60 * 1000);
	var claimEndDay = fm.prpLclaimEndDate.value;
	var claimEnd = new Date(claimEndDay.substring(0, 4), claimEndDay.substring(5, 7) - 1, claimEndDay.substring(8, 10));
	var claim_Start_End = (claimEnd.getTime() - claimStart.getTime()) / (24 * 60 * 60 * 1000);
	if(claim_Start_End>30){
		if(claim_damage <= 30){
			alert("保單生效後"+claim_damage+"天出險，請謹慎處理。");
		}
	}
	var reportDamageMessage = $(":input[name='reportDamageMessage']").val();
	if(reportDamageMessage != ""){
		alert(reportDamageMessage);
	}
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
	conutryCodeTrigger();
	return true;
}

/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */
function submitForm() {
	if (checkForm() == false) {
		return false;
	}

	fm.buttonSaveFinish.disabled = true;
	fm.buttonSaveFinishSubmit.disabled = true;
	fm.buttonSave.disabled = true;
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

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */
function saveForm(field, saveType) {
	if (saveType == "4" && checkUndwrt() == false) {
		return false;
	}
	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	if (fm.all("prpLclaimLossKindCode").length == undefined || fm.all("prpLclaimLossKindCode").length < 1) {
		alert(i18n.claim.enterInformation); //请输入估损信息！
		return false;
	}
	if (!validateForm(fm, "ClaimLoss_Data,Proposer_Data")) {
		return false;
	}
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
	if(!checkLossAmountForClaim(field, saveType)){
		return false;
	}
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END
	// 立案是不能修改事故被保险人信息 
	var insuredCode = fm.prpLregistInsuredCode.value;
	// var prpLacciPersonAcciCode = fm.prpLacciPersonAcciCode.value;
	// if (insuredCode != prpLacciPersonAcciCode) {
	// errorMessage = errorMessage + "被保险人与事故被保险人不一致\n";
	// }
	//立案环节提交增加危险单位不能为空的控制
	var dangerNoList = document.getElementsByName("prpLclaimLossDangerNo");
	for (var i = 0; i < dangerNoList.length; i++) {
		if (dangerNoList[i].value == null || dangerNoList[i].value == "") {
			errorMessage = errorMessage + "危险单位不能为空!\n"
			break;
		}
	}
	//立案环节提交增加危险单位不能为空的控制
	//判断是否还有申请未处理
	if (saveType == '4' && fm.AcciClaimFlag.value == 'N') {
		alert(i18n.commonAcci.claim.applicateProcessNotSubmit);  //还有调查申请没有处理，不能提交!
		return false;
	}
	var prpLclaimClaimType = fm.prpLclaimClaimType.value;
	if (prpLclaimClaimType.length < 1) {
		errorMessage = errorMessage + i18n.commonAcci.claim.caseTypeCodeNotEmpty;  //案件类型代码不允许为空\n
	}
	var prpLclaimClaimTypeName = fm.prpLclaimClaimTypeName.value;
	if (prpLclaimClaimTypeName.length < 1) {
		errorMessage = errorMessage + i18n.commonAcci.claim.caseTypeNameNotEmpty;  //案件类型名称不允许为空\n
	}

	var sumClaim = parseInt(fm.prpLclaimSumClaim.value);
	if (fm.prpLclaimClaimType.value.length > 0) {
		if (sumClaim <= 1000 && fm.prpLclaimClaimType.value != "1" && fm.prpLclaimClaimType.value != "0") {
			alert(i18n.commonAcci.claim.estimatAmountVictoryClaim);  //预估金额小於1000元案件类型应为一般赔案或速决赔案!
			return false;
		}
	}

	//textarea文本框设置值
	var context = fm.prpLltextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.claim.dangerNotAllowEmpty;  //出险摘要不允许为空\n
	}

	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//页面栏位隐藏，效验也去掉
//	if (saveType == 4) {
//		var prpLacciPersonIdentifyNumber = fm.prpLacciPersonIdentifyNumber.value;// 證件代碼
//		if(prpLacciPersonIdentifyNumber!="" && !checkIdentifyNumber(prpLacciPersonIdentifyNumber,"9")){
//			alert("請爲事故被保險人訊息錄入正確的身份證號");
//			return false;
//		}
//		if(fm.proposerIdentifyNumber.length != undefined && fm.proposerIdentifyNumber.length > 1){
//			var proposerIdentifyNumber = fm.proposerIdentifyNumber[1].value;// 證件代碼
//			if(proposerIdentifyNumber!="" && !checkIdentifyNumber(proposerIdentifyNumber,"9")){
//				alert("請爲索賠申請人訊息錄入正確的身份證號");
//				return false;
//			}
//		}
//	}
	var claim_days = fm.claim_days.value;
	var standardDays = fm.standardDays.value;
	if (claim_days == 0) {
		if (!confirm(i18n.claim.caseGreaterSystemTime + standardDays + i18n.claim.dayWhetherSubmit)) {  //立案天数大於系统规定时间     天，是否提交？
			return false;
		}
	}
	if (fm.all("prpLclaimLossKindCode").length == undefined || fm.all("prpLclaimLossKindCode").length < 1) {
		alert(i18n.claim.enterInformation);  //请输入估损信息！
		return false;
	}

// if (!checkProposer()) {
// return false;
// }
	var sumClaim = parseFloat(fm.prpLclaimSumClaim.value);
	if(!checkLoss()){
		return false;
	}
	if (sumClaim < 0) {
		alert(i18n.claim.lessZero);  //估损金额不能小於零！
		return false;
	}
	
	// 如果輸入了从事行业，一级、二级和三级都要輸入 start
	for (i = 1; i < fm.prpLpersonTracePersonNo.length; i++) {
		if (fm.prpLpersonTraceJobCode1[i].value != "") {
			if (fm.prpLpersonTraceJobCode2[i].value == "") {
				alert(i18n.certainLoss.pleaseSelectIndustry2);  //请选择二级行业！
				fm.prpLpersonTraceJobName2[i].focus();
				return false;
			}
			if (fm.prpLpersonTraceJobCode[i].value == "") {
				alert(i18n.certainLoss.pleaseSelectIndustry3);  //请选择三级行业！
				fm.prpLpersonTraceJobName[i].focus();
				return false;
			}
		}
		if (fm.prpLpersonTraceJobCode2[i].value != "") {
			if (fm.prpLpersonTraceJobCode[i].value == "") {
				alert(i18n.certainLoss.pleaseSelectIndustry3);  //请选择三级行业！
				fm.prpLpersonTraceJobName[i].focus();
				return false;
			}
		}
	}

	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();

	return true;
}

/**
 * mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
 */
function checkLossAmountForClaim(field, saveType){
	var $span_prpDpolicyRules = $("span[name='span_prpDpolicyRules']").find(":input[name='init_data_rulCode']");
	var $prpLclaimAddressCode = $("input[name='prpLclaimAddressCode']");
	var check = true;//prpLclaimLossKindCode
	var amountCheck = true;
	var hasTr47 = false;
	//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 START
    //立案
	var hasTr48 = false;
	var taKindVal = "";
	$("input[name='init_data_hisKind']").each(function() {
		if($(this).val()=="TR47"||$(this).val()=="TR48" ){
			taKindVal = $(this).val();
		}
	});
	//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 END
	$("input[name='prpLclaimLossKindCode']").each(function() {
	    var kindCode = $(this).val();
	    //mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 START
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
		if(!check){
			alert("請修正事故地點");
			return false;
		}
		//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 END
	    	
	});
	//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示  START
	if(hasTr47 || hasTr48){//有TR47 僅能TR47不能帶其他險類
		$("input[name='prpLclaimLossKindCode']").each(function(i,n) {
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
				$("input[name='prpLclaimLossKindCode']").each(function() {
					var kindCode = $(this).val();
					//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示
					if(kindCode == "TR47" || kindCode == "TR48"){
						check = true;
					}
				});
			}
			if(!check){
				//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示
				alert("事故地點為指定地區時，必須有"+taKindVal+"海外突發疾病健康保險");
				return false;
			}
		});
	}
	if(!amountCheck && check){
		//地址以及險種 都驗證結束 來驗證金額
		$("input[name='prpLclaimLossKindCode']").each(function(i,n) {
			
			var _kindCode = $(this).val();
			var _Amount ="";
			var _SumClaim = "";
			$("input[name='prpLclaimLossAmount2']").each(function(i2,n2) {
				if(i==i2){
					_Amount = $(this).val();
				}
			});
			$("input[name='prpLclaimLossSumClaim']").each(function(i3,n3) {
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
			alert("預計給付金額"+taKindVal+"賠付金額必須小於等於保險金額!");
			return false;
		}
	}
	if(amountCheck && check){
		return true;
	}
}

//装载数据

function loadForm() {}

//索赔申请人

function checkProposer() {
	if (getRowsCount("Proposer") == 0) {
		errorMessage(i18n.commonAcci.claim.claimApplicantInfo);  //索赔申请人信息至少要有一条记录!

		return false;
	}

	for (var j = 1; j < fm.proposerName.length; j++) {
		if (isEmptyField(fm.proposerName[j])) {
			errorMessage(i18n.compel.first + j + i18n.commonAcci.claim.articleClaimNameNoEmpty);   //第       条索赔申请人姓名不能为空!
			fm.proposerName[j].focus();
			return false;
		}
		if (isEmptyField(fm.proposerIdentifyNumber[j])) {
			errorMessage(i18n.compel.first + j + i18n.commonAcci.claim.articleClaimCardNoEmpty);   //第     条索赔申请人身份证号码不能为空!
			fm.proposerIdentifyNumber[j].focus();
			return false;
		}
		if (isEmptyField(fm.relationCode[j])) {
			errorMessage("第" + j + "条索赔申请人身份證字號不能为空!");
			fm.relationCode[j].focus();
			return false;
		}
	}
	return true;
}

/**
 *@description 弹出查看留言页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?registNo=" + fm.prpLclaimRegistNo.value;
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=no,scrollbars=yes,width=600,Height=300");
}

/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relate() {
	var policyNo = fm.prpLclaimPolicyNo.value;
	var registNo = fm.prpLclaimRegistNo.value;
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核START
	var claimNo = fm.prpLclaimClaimNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo + "&claimNo="+claimNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
}

//得到一页的多行纪录的记录数
//页名称

function getRowsCount(PageCode) {
	var oTBODY = document.all(PageCode).tBodies.item(0);
	var intCount = oTBODY.rows.length;
	return intCount;
}

/**
 @description 汇总估损金额
 @param       无
 @return      无
 */
function collectClaimFee() {
	var compAmout = 0;
	var nowAmout = 0;

	for (var n = 1; n < fm.prpLclaimFeeSumClaim.length; n++) {
		nowAmout = parseFloat(fm.prpLclaimFeeSumClaim[n].value)
		compAmout = compAmout + nowAmout;
	}

	fm.prpLclaimSumClaim.value = pointTwo(compAmout);
	fm.prpLdangerRiskSumClaim.value = pointTwo(compAmout);

	return true;
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

	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}


/**
 * @description 汇总估损金额
 * @param 无
 * @return 无
 */
function collectClaimLoss() {
	var compAmout = 0;
	var nowAmout = 0;
	var exchCurrency = fm.prpLclaimCurrency.value; //得到当前币别类型
	var exchRate = 1; //兑换率

//	if (!checkLoss()) return false;
	//循环计算 估金额

	for (var n = 1; n < fm.prpLclaimLossSumClaim.length; n++) {
		nowAmout = parseFloat(fm.prpLclaimLossSumClaim[n].value)
//		for (j = 1; j < fm.baseCurrency.length; j++) {
//			if (fm.baseCurrency[j].value == fm.prpLclaimLossCurrency[n].value && fm.exchCurrency[j].value == exchCurrency) {
//				exchRate = fm.exchRate[j].value;
//			}	
//		}
		compAmout = compAmout + nowAmout * exchRate;
	}
	fm.prpLclaimSumClaim.value = pointTwo(compAmout);
	fm.prpLdangerRiskSumClaim.value = pointTwo(compAmout);
	return true;
}


/**
 * @description 校验索赔金额
 * @param 无
 * @return boolean
 */
function checkLoss() {
	//1.检查必须要有一条记录
	if (getRowsCount("ClaimLoss") == 0) {
		errorMessage("估损金额金额信息至少要有一条记录!");

		return false;
	}
	//2币别不能为空的

	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
			errorMessage("第" + j + "条估损金额中币别不能为空!");
			return false;
		}
		if (isEmptyField(fm.prpLclaimLossKindCode[j])) {
			errorMessage("第" + j + "条事故估损金额中险别代码不能为空!");
			return false;
		}

		//险别和币别相同的，必须合並
		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
			errorMessage("第" + j + "条事故估损金额中金额不能为空!");
			return false;
		}
	}
	return checkBeyondSumAmount();
}


//意健险：如果预估金额小於1000元的属於简易（速决）案件

function changeCaseType() {
	var sumClaim = parseInt(fm.prpLclaimSumClaim.value);
	if (sumClaim <= 1000) {
		fm.prpLclaimClaimType.value = "1";
		fm.prpLclaimClaimTypeName.value = "速决赔案";
		fm.prpLclaimClaimType.disabled = true;
		fm.prpLclaimClaimTypeName.disabled = true;
	} else {
		fm.prpLclaimClaimType.disabled = false;
		fm.prpLclaimClaimTypeName.disabled = false;
	}
}
//汇总险别估损信息 ,根据币别汇总

function collectCurrency() {
	var collectCurr = "";
	var collectTemp = new Array();
	collectCurr = "分币别汇总结果:\n";
	for (var i = 1; i < fm.prpLclaimLossCurrency.length; i++) {
		var hasElement = false;
		var currency = fm.prpLclaimLossCurrency[i].value;
		var currencyName = fm.prpLclaimLossCurrencyName[i].value;
		var sumLossAmount = 0;
		//循环分币别统计
		for (var ii = 1; ii < fm.prpLclaimLossCurrency.length; ii++) {
			if (currency == fm.prpLclaimLossCurrency[ii].value) {
				sumLossAmount = sumLossAmount + parseFloat(fm.prpLclaimLossSumClaim[ii].value);
			}
		}
		//先判断当前数组中是否已有此币别,如果没有再进行保存
		for (var j = 0; j < collectTemp.length; j++) {
			if (collectTemp[j] == currency) {
				hasElement = true;
			}
		}
		//如果当前数组中有此元素，不再进行统计

		if (hasElement) {
			continue;
		}
		//存入数组中
		collectTemp[i - 1] = currency;
		collectCurr = collectCurr + currency + "  " + currencyName + "  " + sumLossAmount + "元\n";
	}
	if (collectCurr.length > 0) {
		alert(collectCurr);
		return false;
	}
}

function notGrandClaim(registNo, riskcode, swfLogFlowID, swfLogLogNo) {
	fm.action = "/claim/claimBeforeCancel.do?editType=NOTGRANDCLAIM&RegistNo=" + registNo + "&riskCode=" + riskcode + "swfLogFlowID=" + swfLogFlowID + "swfLogLogNo=" + swfLogLogNo;
	fm.submit();
	return true;
}


/**
 * 改造思路：1.前台以familyName+kindCode作为name名称添加保额隐藏域，在调用该方法时会取到该被保险人对应的险别保额
 * 2.将同一险种加和，与该险种保额进行比较得出是否超过保额
 */

function checkBeyondSumAmount() {
	var flag = true;
	var errorMessage = "";
	debugger;
	var $Amount = $("#ClaimLoss").find(":input[name='prpLclaimLossAmount']");
	var $LossFeeType = $("#ClaimLoss").find(":input[name='prpLclaimLossLossFeeType']");
	var $KindCode = $("#ClaimLoss").find(":input[name='prpLclaimLossKindCode']");
	var $ItemKindNo = $("#ClaimLoss").find(":input[name='prpLclaimLossItemKindNo']");
	
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核
	var $riskCode = fm.prpLclaimRiskCode;
	
	var amountArray = new Array();
	var kindCodeArray = new Array();
	var sumClaimArray = new Array();
	var payoutTimeArray = new Array();
	var kindCodes = "";
	$("#ClaimLoss").find(":input[name='prpLclaimLossSumClaim']").each(function(i,n){
		if(!$.isNumeric(n.value)){
			n.value = "0";
		}
		if (parseFloat(n.value) < 0) {
			errorMessage += "第"+(i+1)+"條"+i18n.claim.lessZero; // 估损金额不能小於零
			flag = false;
			return flag;
		}
		var $sumClaimList = $(":input[name='"+$KindCode[i].value +"_"+ $ItemKindNo[i].value+"']");
		$Amount[i].value = $sumClaimList.val();
		var $span = $sumClaimList.parents("span[name='spanPayoutTime']");
		var $payoutTime = $span.find(":input[name='payoutTime']");
		if($LossFeeType.get(i).value=="P"){
			kindCodes = $KindCode.get(i).value;
			var index = $.inArray(kindCodes, kindCodeArray);
			if(index>-1){
				sumClaimArray[index] += parseFloat(n.value);
			}else{
				kindCodeArray.push(kindCodes);
				amountArray.push(parseFloat($Amount.get(i).value));
				sumClaimArray.push(parseFloat(n.value));
				payoutTimeArray.push($payoutTime.val());
			}
		}
	});
	$.each(kindCodeArray,function(i,n){
		if(payoutTimeArray[i]!="P"&&$.isNumeric(payoutTimeArray[i])){
			//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
			if(kindCodeArray[i]=="PAF4"){
				//跳過 PAF4 檢核
				//alert("PAF4");
			}else 
			//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
			//mantis：CLM0274，處理人員：DP0713，需求單編號：新核心- TA海外突發疾病修改 START
			if(kindCodeArray[i]=="TR47"){//(claim)
				//payoutTimeArray找出這個倍率來源
				if(sumClaimArray[i]>amountArray[i]*parseFloat(payoutTimeArray[i])){
					errorMessage += kindCodeArray[i]+"險別賠付預估金額應小於等於 ‘保險金額×保額賠付倍數’請檢核後重新輸入。"; //估损金额不能大於保额
					flag = false;
					return false;
				}
			}else
			//mantis：CLM0274，處理人員：DP0713，需求單編號：新核心- TA海外突發疾病修改 END
			//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 START
			if(kindCodeArray[i]=="TR48"){//(claim)
				debugger;
				if(sumClaimArray[i]>amountArray[i]){
					errorMessage += kindCodeArray[i]+"險別賠付預估金額應小於等於 ‘保險金額’請檢核後重新輸入。"; //估损金额不能大於保额
					flag = false;
					return false;
				}
			}else
			//mantis：CLM0298 ，處理人員：DP0713，需求單編號：新核心-TR48辨識非地區保額增加規則警示 END
			if(sumClaimArray[i]>amountArray[i]*parseFloat(payoutTimeArray[i])){
				errorMessage += kindCodeArray[i]+"險別賠付預估金額應小於等於 ‘保險金額×保額賠付倍數’請檢核後重新輸入。"; //估损金额不能大於保额
				flag = false;
				return false;
			}
		}
		
	});

	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
	if(undefined!=$riskCode && null!=$riskCode && null!=$riskCode.value && $riskCode.value=="PA"){
		var errorMessageForHospitalize="";
		var prpLcompensateHospitalizedDays = fm.prpLclaimHospitalizedDays;
		var totalPAFamount = 0;
		
		var PAF456_SUMLOSS = undefined!=fm.PAF456_SUMLOSS && null!=fm.PAF456_SUMLOSS && ""!=fm.PAF456_SUMLOSS.value?fm.PAF456_SUMLOSS.value:0.0;
		var PAF7_AMOUNT = undefined!=fm.PAF7_AMOUNT && null!=fm.PAF7_AMOUNT && ""!=fm.PAF7_AMOUNT.value?fm.PAF7_AMOUNT.value:0.0;
		
		debugger;
		$.each(kindCodeArray,function(i,n){
			if(payoutTimeArray[i]!="P"&&$.isNumeric(payoutTimeArray[i])){
				
				if(kindCodeArray[i]=="PAF4"){
					prpLclaimHospitalizedDays = prpLcompensateHospitalizedDays.value;//本次住院天數
					if(undefined!=prpLcompensateHospitalizedDays && null!=prpLcompensateHospitalizedDays 
							&& ""==prpLcompensateHospitalizedDays.value && !(isNaN(prpLcompensateHospitalizedDays.value))){
						if(""!=errorMessageForHospitalize){
							errorMessageForHospitalize+="\r\n";
						}
						errorMessageForHospitalize +="請輸入本次住院天數";
						flag = false;
					}else{
						totalPAFamount += parseInt(amountArray[i],10)*parseFloat(prpLclaimHospitalizedDays); 
						if(sumClaimArray[i]>amountArray[i]*parseFloat(prpLclaimHospitalizedDays)){
							if(""!=errorMessageForHospitalize){
								errorMessageForHospitalize+="\r\n";
							}
//							errorMessageForHospitalize += kindCodeArray[i]+"險別賠付預估金額("+sumClaimArray[i]+")應小於等於("+amountArray[i]+")X("+parseFloat(prpLclaimHospitalizedDays)+")="+amountArray[i]*parseFloat(prpLclaimHospitalizedDays)+" ‘本次住院天數×保險日額’請檢核後重新輸入。"; //估损金额不能大於保额
							errorMessageForHospitalize += kindCodeArray[i]+"險別賠付預估金額應小於等於 ‘本次住院天數×保險金額’，請檢核後重新輸入。"; //估损金额不能大於保额
							flag = false;
						}
					}
				}
				if(kindCodeArray[i]=="PAF5"){
					totalPAFamount += parseInt(sumClaimArray[i],10);
					if(sumClaimArray[i]>amountArray[i]){
						if(""!=errorMessageForHospitalize){
							errorMessageForHospitalize+="\r\n";
						}
						errorMessageForHospitalize += kindCodeArray[i]+"險別賠付預估金額應小於等於 "+kindCodeArray[i]+"‘保險金額’，請檢核後重新輸入。"; //估损金额不能大於保额
						flag = false;
					}
				}
				if(kindCodeArray[i]=="PAF6"){
					totalPAFamount += parseInt(sumClaimArray[i],10);
					if(sumClaimArray[i]>amountArray[i]){
						if(""!=errorMessageForHospitalize){
							errorMessageForHospitalize+="\r\n";
						}
						errorMessageForHospitalize += kindCodeArray[i]+"險別賠付預估金額應小於等於 "+kindCodeArray[i]+"‘保險金額’，請檢核後重新輸入。"; //估损金额不能大於保额
						flag = false;
					}
				}
				if(kindCodeArray[i]=="PAF7"){
					if(""!=errorMessageForHospitalize){
						errorMessageForHospitalize+="\r\n";
					}
					errorMessageForHospitalize += kindCodeArray[i]+"險別不應存在’請刪除後重新送出。"; //PAF7 找保單要
					flag = false;
				}
			}
		});
		debugger;
		if((parseInt(totalPAFamount,10)+parseInt(PAF456_SUMLOSS,10)) > parseInt(PAF7_AMOUNT,10)){
//			errorMessageForHospitalize+= "\r\n已經核賠("+PAF456_SUMLOSS+")本次預計給付金額("+totalPAFamount+")的加總("+(parseInt(totalPAFamount,10)+parseInt(PAF456_SUMLOSS,10))+")超過 (>) 保單("+fm.policyno.value+")金額估損總金額("+PAF7_AMOUNT+")。";
			errorMessageForHospitalize+= "\r\n本次預計給付金額的加總超過保單金額估損總金額 ，請檢核後重新輸入。";
			flag = false;
		}
		if(""!=errorMessageForHospitalize){
			errorMessage = errorMessageForHospitalize;//PA優先先出這個錯誤訊息
		}
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
	
	if(errorMessage != ""){
		alert(errorMessage);
	}
	return flag;
}
//定位立案页面中险别估损信息标签页中的责任双击，並拼接条件。

function before_code_CodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod) {
	var thisRow = parseInt(getElementOrder(field));
	var prpLclaimLossKindCode = fm.prpLclaimLossKindCode[thisRow - 1].value;
	otherCondition = otherCondition + '|' + prpLclaimLossKindCode + "|" + document.getElementsByName("prpLclaimLossKindCode")[0].value;
	code_CodeSelect(field, codeType, codeRelation, isClear, isQueryCode, otherCondition, callBackMethod, getDataMethod);
}


function showAcciName(field) {

	var flag = fm.clickCount.value;
	fieldName = field.name;
	if (fieldName == "prpLacciPersonAcciCode") {
		flag = "1";
	} else if (fieldName == "prpLacciPersonAcciName" && flag == "1") {
		flag = "1";
	} else {
		flag = "0";
	}
	if ("1" == flag) {
		if (fieldName == "prpLacciPersonAcciCode") {
			code_CodeSelect(field, 'prpCinsured', '0,1,2,3,4,5', 'Y', 'Y', fm.policyno.value);
		} else {
			code_CodeSelect(field, 'prpCinsured', '-1,0,1,2,3,4', 'Y', 'N', fm.policyno.value);
		}
		var accitCode = fm.prpLacciPersonAcciCode.value;
		if ("9999" == accitCode) {
			//modify by liuwei at 2011-04-20 对选择自定义事故者时做判断 start
			var termFlag = document.getElementsByName("termFlag");
			if (termFlag.length > 0 && termFlag[0].checked == true) {
				fm.prpLacciPersonAcciName.className = "input";
				fm.clickCount.value = "0";
			} else {
				alert(i18n.commonAcci.claim.customAcciChooseGroup);  //自定义事故者时必须选择免导团单！
				fm.prpLacciPersonAcciName.className = "codecode";
				fm.clickCount.value = "1";
				fm.prpLacciPersonAcciCode.value = '';
				fm.prpLacciPersonAcciName.value = '';
			}
			//modify by liuwei at 2011-04-20 对选择自定义事故者时做判断 end
		} else {
			fm.prpLacciPersonAcciName.className = "codecode";
			fm.clickCount.value = "1";
		}
	} else {
		fm.prpLacciPersonAcciName.className = "input";
		fm.clickCount.value = "0";
	}
}

//根据出现地址所属国内或国外判断双击域是否显示 begin

function countryFlag_change(countryFlag) {
	if (countryFlag != "1") {
		fm.countryCName.style.display = "none";
		fm.provinceName.style.display = "";
		fm.cityName.style.display = "";
	} else {
		fm.countryCName.style.display = "";
		fm.provinceName.style.display = "none";
		fm.cityName.style.display = "none";
	}
}

/**
 * mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
 * 计算保险金额(claim)
 * @param field
 * @return
 */
function getAmount(field){
	//var $tr = $(field).parents("tr[name='prpLpersonFeeLossPaymentTr']");
	var $riskCode = fm.prpLclaimRiskCode;
	var $tr = $(field).parents("tr").each(function(){
	
		var $itemKindNo = $(this).find(":input[name='prpLclaimLossKindCode']");
		$prpLclaimLossAmount2 = $(this).find(":input[name='prpLclaimLossAmount2']"); //保險金額
		//$prpLclaimLossSumClaim = $(this).find(":input[name='prpLclaimLossSumClaim']");//預計給付金額
		var amount = 0;
		if($riskCode.value == "TA"){
			if($itemKindNo.val()!="" && $(field).val()==$itemKindNo.val()){//辨識同個kindcode的改變依據
				var $span_prpCitemKind = $("span[name='span_prpCitemKind']").find(":input[name='init_data_hisKind'][value='"+$itemKindNo.val()+"']").parent();
				var $amount= $span_prpCitemKind.find(":input[name='init_data_amount']");
				
				$prpLclaimLossAmount2.val($amount.val());//保險金額
				if($itemKindNo.val()!="" && $itemKindNo.val()=="TR47"){
					
					//var $coverageratio= $span_prpCitemKind.find(":input[name='init_data_coverageratio']");
					var coverageratio = "1";
					
					var $prpLclaimAddressCode = $("input[name='prpLclaimAddressCode']");
					var $span_prpCitemKind = $("span[name='span_prpDpolicyRules']").find(":input[name='init_data_rulCode'][value='"+$prpLclaimAddressCode.val()+"']").parent();
					var $rulMultiplier= $span_prpCitemKind.find(":input[name='init_data_rulMultiplier']");
					//alert($itemKindNo.val()+"/"+$amount.val()+"/"+$rulMultiplier.val());
					if($rulMultiplier.length > 0 && $rulMultiplier.val().length > 0){
						coverageratio = parseFloat($rulMultiplier.val());
					}
					amount = parseFloat($amount.val()) * coverageratio;
					$prpLclaimLossAmount2.val(amount);
					
					//把原本用於檢核的 spanPayoutTime 檢核隱藏欄位更新
					var $span_payoutTime = $("span[name='spanPayoutTime']").find(":input[name='TR47']").parent();
					$val_payoutTime = $span_payoutTime.find(":input[name='payoutTime']");
					$val_payoutTime.val(coverageratio);
					//prpLclaimLossSumClaim
					
					
				}
			}
		}
	//if(amount==0){
		//amount = $parseFloat($(":input[name='prpLcompensateSumAmount']").val());
	//}
		return amount;
	});
}
/**
 * mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
 * @param field(claim)
 */
function conutryCodeTrigger(field){//FOR TA
	$("input[name='prpLclaimLossKindCode']").each(function() {
	    getAmount($(this));
	});
	collectClaimLoss();
}
/**
 * mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
 * 计算赔付金额 意健险理算公式 01-住院醫療(HG) 人工輸入金額 02-身故 保險金額 03-殘廢或重大燒燙傷 保險金額 * 殘廢給付比例%
 * 21-醫療限額（實支實付） 人工輸入金額 22-Min【（住院日數 * 醫療日額）＋（未住院日數 * 醫療日額* 骨折給付比例），保險金額】
 * 23-費用補償 人工輸入金額
 * (claim)
 */
function sumRealPay(field){
	if(undefined!=fm.editType.value && fm.editType.value=="SHOW"){
		return;
	};
	var amount = getAmount(field);
	collectClaimLoss();
}