/*****************************************************************************
 * DESC       ：报案登记的脚本函数页面
 * AUTHOR     ：weishixin
 * CREATEDATE ： 2013-09-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet() { //判断是否是共保、临分、股东业务信息
	var coinsFlag = fm.coinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var message = "";
	//add by qinyongli 增加保单注销,报案时间，倒签单，股东业务，出险次数，临分共保等提示； 2005-7-28
	var coinsFlag = fm.coinsFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var message = "";

	var othFlag = fm.prpLclaimOthFlag.value;
	if (othFlag.substring(3, 4) == "1") {
		message = message + i18n.claim.policyCancelled; //此保单已注销！\n
	}
	var payFee = parseInt(fm.payFee.value);
	if (payFee == -1) {
		alert(i18n.claim.premiumNotPaid); //此保单保费未缴！\n
	} else if (payFee == 0) {
		alert(i18n.claim.premiumNotComplete); //此保单保费未缴全！\n
	} else if (payFee == -2) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
	}
	if (coinsFlag != 0) {
		message = message + i18n.claim.totalPolicy; //本保单为联/共保单！\n
		message = message + i18n.claim.estimateAmount; //估损金额请輸入分摊前的总估损金额！\n
	}
	//   add by wzy 20090410  start 
	if (fm.BaseCurrency1.value != CURRENCYINFO.LOCAL_CURRENCY) {
		alert(i18n.claim.singleCurrency + fm.BaseCurrency1.value + i18n.claim.useSparingly + fm.ExchRate1.value); //此案件签单币别为    ，不是CNY，请慎重处理！\n当前兑换率为
	}
	var reportTimeMessage = $(":input[name='reportTimeMessage']").val();
	if(reportTimeMessage != ""){
		message += reportTimeMessage;
	}
	//   add by wzy 20090410  end 
	if (message.length > 0) {
		alert(message);
		return false;
	}
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
	if (window.confirm(i18n.button.confirmClear)) {
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
	//提交时对送审进行判断
	if (saveType == "4" && checkUndwrt() == false) {
		return false;
	}

	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	//立案环节提交增加危险单位不能为空的控制 begin
	var dangerNoList = document.getElementsByName("prpLclaimLossDangerNo");
	for (var i = 0; i < dangerNoList.length; i++) {
		if (dangerNoList[i].value == null || dangerNoList[i].value == "") {
			errorMessage = errorMessage + i18n.claim.dangerousCannotEmpty
			break;
		}
	}
	//立案环节提交增加危险单位不能为空的控制
	// reason:增加立案除外功能後，对於出险时间是否在保险期间内的判断，统一在後台进行。
	//增加追溯期和出案时间判断
	//var liabStartDate = fm.liabStartDate.value;
	//var startDate = fm.prpLclaimStartDate.value;
	//var endDate   = fm.prpLclaimEndDate.value;
	//var damageStartDate = fm.prpLclaimDamageEndDate.value;
	// if((damageStartDate < liabStartDate)&&liabStartDate!=0&&liabStartDate<startDate|| (damageStartDate > endDate)){
	//	errorMessage = errorMessage +"提示：出险时间在保险期间以外，不允许立案!\n";
	//}
	//textarea文本框设置值
	var context = fm.prpLltextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.claim.dangerNotAllowEmpty; //出险摘要不允许为空\n
	}
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}

	var claim_days = fm.claim_days.value;
	var standardDays = fm.standardDays.value;
	if (claim_days == 0) {
		if (!confirm(i18n.claim.caseGreaterSystemTime + standardDays + i18n.claim.dayWhetherSubmit)) { //立案天数大於系统规定时间   天，是否提交？
			return false;
		}
	}
	if (fm.all("prpLclaimLossKindCode").length == undefined || fm.all("prpLclaimLossKindCode").length < 1) {
		alert(i18n.claim.enterInformation); //请输入估损信息！
		return false;
	}

	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm, "ClaimLoss_Data")) {
		return false;
	}
	//當險別為‘CC’時，才需要判斷信用卡相關欄位的必輸項。begin
	//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
	//增加 saveType
	if(!checkCard(saveType)){
		return false;
	}
	// 當險別為‘CC’時，才需要判斷信用卡相關欄位的必輸項。end 
	//计算估损金额
	if (!checkLoss()) {
		return false;
	}
	collectClaimLoss();
	
//	var sumAmount = parseFloat(fm.prpLclaimSumAmount.value);
	var sumClaim = parseFloat(fm.prpLclaimSumClaim.value);
//	if (sumClaim > sumAmount) {
//		alert(i18n.claim.sumInsured); //估损金额不能大於保额！
//		return false;
//	}
	if (sumClaim < 0) {
		alert(i18n.claim.lessZero); //估损金额不能小於零！
		return false;
	}

	for(i=1;i<fm.prpLpersonTraceJobName2.length;i++){
		fm.prpLpersonTraceJobCode[i].value = fm.prpLpersonTraceJobCode2[i].value;
		fm.prpLpersonTraceJobName[i].value = fm.prpLpersonTraceJobName2[i].value;
	}
	var $motionFlag = $(":input[name='motionFlag']");
	var $prpLpersonTraceHospital = $(":input[name='prpLpersonTraceHospital']");
	var $prpLpersonTracePersonName = $(":input[name='prpLpersonTracePersonName']");
	for (i = 1; i < fm.prpLpersonTracePersonNo.length; i++) {
		if (fm.prpLpersonTraceJobCode1[i].value != "") {
			if (fm.prpLpersonTraceJobCode2[i].value == "") {
				alert(i18n.certainLoss.pleaseSelectIndustry2); //请选择二级行业！
				return false;
			}
		}
		if($prpLpersonTracePersonName[i].value==""){
			alert("第"+i+"條姓名不能為空！"); //请选择二级行业！
			return false;
		}
		if($motionFlag[i].value=="1"&&$prpLpersonTraceHospital[i].value==""){
			alert("第"+i+"條選擇了自行就醫必須要錄入就診醫院！"); //请选择二级行业！
			return false;
		}
	}
	/**
	//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
    var errorMessage="";
	if (saveType == "4") {
    var riskCode = $(":input[name='prpLclaimRiskCode']").val();
	var prpLclaimCreditHolderTel =fm.prpLclaimCreditHolderTel.value;
	if (prpLclaimCreditHolderTel.length > 0) {
		 if (prpLclaimCreditHolderTel.substr(0, 2)=='09'){
		    	reg =/^09[0-9]{8}$/;
		    	  if(!reg.test(prpLclaimCreditHolderTel)){
		    		errorMessage =errorMessage +"持卡人電話有誤\n";
		    	}
		  } else {
		      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
		      if (!reg.test(prpLclaimCreditHolderTel)){
		    	errorMessage =errorMessage +"持卡人電話有誤\n";
		      }
		}
	  }
	var prpLclaimCreditHolderPhone =fm.prpLclaimCreditHolderPhone.value;
    if (prpLclaimCreditHolderPhone.length > 0) {
	    	 var reg =/^09[0-9]{8}$/;
	    	  if(!reg.test(prpLclaimCreditHolderPhone)){
	    		  errorMessage =errorMessage +"持卡人手機有誤\n";
	    		}  
        }
    if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
  }//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end
   */
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();

	return true;
}

//装载数据

function loadForm() {
	//alert("set cadsfsdfl");
	//fm.carKindCode.disabled = true;
	//fm.carKindCode.readOnly = true;
	//alert("set carKindCode");
}

/**
 *@description 弹出留言保存页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinSave() {

	var businessNo = fm.prpLclaimRegistNo.value;
	var policyNo = fm.prpLclaimPolicyNo.value;
	var riskCode = fm.prpLclaimRiskCode.value;
	var claimNo = fm.prpLclaimClaimNo.value;
	msg = window.open("/claim/messageQueryInfo.do?businessNo=" + businessNo + "&nodeType=claim&policyNo=" + policyNo + "&riskCode=" + riskCode + "&claimNo=" + claimNo, "NewWindow", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=500,Height=300");
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

//Modify By sunhao add begin 2004-09-06
/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relate() {
	var policyNo = fm.prpLclaimPolicyNo.value;
	var registNo = fm.prpLclaimRegistNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}


/**
 @description 校验索赔金额
 @param       无
 @return      boolean
 */
//function checkFee() {
//	if (getRowsCount("LiabClaimFee") == 0) {
//		errorMessage(claim.oneRecord); //估损金额金额信息至少要有一条记录!
//
//		return false;
//	}
//	//alert (fm.prpLclaimFeeCurrency.length);
//
//	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
//		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
//			errorMessage(i18n.claim.cannotEmpty); //第"+ j+"条估损金额中币别不能为空!
//			fm.prpLclaimLossCurrency[j].focus();
//			return false;
//		}
//
//		for (var n = j + 1; n < fm.prpLclaimLossCurrency.length; n++) {
//			if ((fm.prpLclaimLossCurrency[n].value == fm.prpLclaimLossCurrency[j].value) && (fm.prpLclaimLossKindCode[n].value == fm.prpLclaimLossKindCode[j].value) && (fm.prpLclaimLossLossFeeType[n].value == fm.prpLclaimLossLossFeeType[j].value)) {
//				errorMessage(i18n.claim.twoRecords); //第"+ n+"条估损金额中险别、币别、费用类别和第"+ j+"条估损金额中险别、币别、费用类别一致，请合並这2条记录
//
//				fm.prpLclaimLossCurrency[n].focus();
//				return false;
//			}
//		}
//
//		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
//			errorMessage(i18n.claim.amountCannotEmpty); //第"+j+"条事故估损金额中金额不能为空!
//			fm.prpLclaimFeeSumClaim[j].focus();
//			return false;
//		}
//	}
//	return true;
//}

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
//	if (!checkFee()) return false;
	//循环计算 估金额
	for (var n = 1; n < fm.prpLclaimFeeSumClaim.length; n++) {
		nowAmout = parseFloat(fm.prpLclaimFeeSumClaim[n].value)
		compAmout = compAmout + nowAmout;
	}

	fm.prpLclaimSumClaim.value = pointTwo(compAmout);
	fm.prpLdangerRiskSumClaim.value = pointTwo(compAmout);
	return true;
}

/**
 @description 汇总估损金额
 @param       无
 @return      无
 */
function collectClaimLoss() {
	var compAmout = 0.00;
	var nowAmout = 0.00;
//	var exchCurrency = fm.prpLclaimCurrency.value; //得到当前币别类型
//	var exchRate = 1; //兑换率
//	if (!checkLoss()) return false;
	//循环计算 估金额
	for (var n = 1; n < fm.prpLclaimLossSumClaim.length; n++) {
		if($.isNumeric(fm.prpLclaimLossSumClaim[n].value)){
			nowAmout = parseFloat(fm.prpLclaimLossSumClaim[n].value)
			compAmout = compAmout + nowAmout;
		}
	}
	fm.prpLclaimSumClaim.value = pointTwo(compAmout);
	fm.prpLdangerRiskSumClaim.value = pointTwo(compAmout);
	return true;
}

function collectSumClaim(field){
	var index = $(":input[name='"+field.name+"']").index(field);
	var kindLoss = $(":input[name='prpLclaimLossKindLoss']").get(index);
	if(!$.isNumeric(kindLoss.value)){
		kindLoss.value = 0;
	}
	var kindRest = $(":input[name='prpLclaimLossKindRest']").get(index);
	if(!$.isNumeric(kindRest.value)){
		kindRest.value = 0;
	}
	var sum = parseFloat(kindLoss.value)-parseFloat(kindRest.value);
	var sumClaim = $(":input[name='prpLclaimLossSumClaim']").get(index);
	if(sum<0){
		sum = 0;
	}
	if($.isNumeric(sum)){
		sumClaim.value = pointTwo(sum);
	}else{
		sumClaim.value = "0";
	}
	
}

/**
 * 險別估損金額=損失金額-自負額-剔除金額/殘值
 * 通過計算產生的‘險別估損金額’不可以修正。
 */
function calculateSumClaim(field){
	var index = $(":input[name='"+field.name+"']").index(field);
	var prpLclaimLossKindLoss = nullToZero(fm.prpLclaimLossKindLoss[index].value);//損失金額
	var prpLclaimLossDeductible = nullToZero(fm.prpLclaimLossDeductible[index].value);//自負額
	var prpLclaimLossKindRest = nullToZero(fm.prpLclaimLossKindRest[index].value);//殘值 
	//險別估損金額
	var lossSumClaim = prpLclaimLossKindLoss - prpLclaimLossDeductible - prpLclaimLossKindRest;
 
	fm.prpLclaimLossSumClaim[index].value =  lossSumClaim;
	checkBeyondSumAmount();
	//计算总估损金额
	collectClaimLoss();
}
/**
 * 计算总估损金额,并给prpLclaimLossSumClaim赋值
 */
function calculateTotalSumClaim(){
	var totalSumClaim = 0;//总損失金額
	for (var i = 0; i < fm.all("prpLclaimLossSumClaim").length; i++) {
		var prpLclaimLossSumClaim = nullToZero(fm.prpLclaimLossSumClaim[i].value);//損失金額
		totalSumClaim+= prpLclaimLossSumClaim;
	}
	fm.prpLclaimSumClaim.value = totalSumClaim;
}
/**
 * 将空值转化为0
 * @param field
 * @returns
 */
function nullToZero(field){
	var res = parseFloat(field);
	if(isNaN(res)){
		res = 0;
	}
	return res;
}
/**
 @description 校验索赔金额
 @param       无
 @return      boolean
 */
function checkLoss() {
	//1.检查必须要有一条记录
	if (getRowsCount("ClaimLoss") == 0) {
		errorMessage(i18n.claim.oneRecord);
		return false;
	}
	//2币别不能为空的

	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.amountCannot);
			return false;
		}

		if (isEmptyField(fm.prpLclaimLossKindCode[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.risksCode);
			return false;
		}

		//险别和币别相同的，必须合並
		for (var n = j + 1; n < fm.prpLclaimLossCurrency.length; n++) {
			if ((fm.prpLclaimLossCurrency[n].value == fm.prpLclaimLossCurrency[j].value) && (fm.prpLclaimLossKindCode[n].value == fm.prpLclaimLossKindCode[j].value) && (fm.prpLclaimLossLossFeeType[n].value == fm.prpLclaimLossLossFeeType[j].value) &&(fm.prpLclaimLossItemCode[n].value == fm.prpLclaimLossItemCode[j].value)) {
				errorMessage(i18n.compel.first + n + i18n.commonShip.claim.estimatLossAmountCost + j + i18n.commonShip.claim.estimatLossAmountCost2);
				return false;
			}
		}

		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.NotEmpty);
			return false;
		}
	}
	return checkBeyondSumAmount();
}
//汇总险别估损信息 ,根据币别汇总
function collectCurrency() {
	var collectCurr = "";
	var collectTemp = new Array();
	collectCurr = i18n.modifySumClaim.summary+"\n";
	for (var i = 1; i < fm.prpLclaimLossCurrency.length; i++) {
		var hasElement = false;
		var currency = fm.prpLclaimLossCurrency[i].value;
		var currencyName = fm.prpLclaimLossCurrencyName[i].value;
		var sumLossAmount = 0.00;
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
		collectCurr = collectCurr + currency + "  " + currencyName + "  " + sumLossAmount + i18n.modifySumClaim.money + "\n";
	}
	if (collectCurr.length > 0) {
		alert(collectCurr);
		return false;
	}
}
//add by qinyongli 查看出险时保单信息,在业务系统中进行保单还原
/**function backWardPolicy(){
     var SHOWTYPE  ="SHOW";
     var BizNo     =fm.prpLclaimPolicyNo.value;
     var RiskCode  =fm.prpLclaimRiskCode.value;
     var damageDate=fm.damageDate.value;
     var vURL = '/prpall/' + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo='+ BizNo+'&RiskCode='+ RiskCode+'&damageDate='+ damageDate;
     window.open(vURL,'详细信息','width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}*/


/**
 *立案估损增加估损不允许大於保额控制
 */
function checkBeyondSumAmount() {
	var flag = true;
	var errorMessage = "";
	var $Amount = $("#ClaimLoss").find(":input[name='prpLclaimLossAmount']");
	var $LossFeeType = $("#ClaimLoss").find(":input[name='prpLclaimLossLossFeeType']");
	var $KindCode = $("#ClaimLoss").find(":input[name='prpLclaimLossKindCode']");
	var $ItemCode = $("#ClaimLoss").find(":input[name='prpLclaimLossItemCode']");
	
	var amountArray = new Array();
	var kindCodeArray = new Array();
	var sumClaimArray = new Array();
	var kindCodes = "";
	$("#ClaimLoss").find(":input[name='prpLclaimLossSumClaim']").each(function(i,n){
		if (parseFloat(n.value) < 0) {
			errorMessage += "第"+(i+1)+"條"+i18n.claim.lessZero; // 估损金额不能小於零
			flag = false;
			return flag;
		}
		if($LossFeeType.get(i).value=="P"){
			kindCodes = $KindCode.get(i).value+"_"+$ItemCode.get(i).value;
			var index = $.inArray(kindCodes, kindCodeArray);
			if(index>-1){
				sumClaimArray[index] += parseFloat(n.value);
			}else{
				kindCodeArray.push(kindCodes);
				amountArray.push(parseFloat($Amount.get(i).value));
				sumClaimArray.push(parseFloat(n.value));
			}
		}
	});
	$.each(kindCodeArray,function(i,n){
		if(sumClaimArray[i]>amountArray[i]){
			kindCodes = kindCodeArray[i].split("_");
			errorMessage += "險別"+kindCodes[0]+",標的物"+kindCodes[1]+i18n.prompt.claim.sumInsured; //估损金额不能大於保额
			flag = false;
			return false;
		}
	});
	if(errorMessage != ""){
		alert(errorMessage);
	}
	return flag;
}
//add by zhaolu 20060908 end 

//add by luochang 2010-06-22 根据出现地址所属国内或国外判断双击域是否显示 begin

function countryFlag_change(countryFlag) {
	if (countryFlag != "1") {
		fm.countryCName.style.display = "none";
		//fm.provinceName.style.display = "";
		//fm.cityName.style.display = "";
		fm.prpLclaimAddressCode.style.display = "";
		fm.prpLclaimAddressName.style.display = "";
	} else {
		fm.countryCName.style.display = "";
		//fm.provinceName.style.display = "none";
		//fm.cityName.style.display = "none";
		fm.prpLclaimAddressCode.style.display = "none";
		fm.prpLclaimAddressName.style.display = "none";
	}
}

//当卡别为其他时，改变录入框的只读样式 begin
function changeOtherCardTypeValue(field){
	var otherCard = $("[name='prpLclaimCreditCardOtherType']");
	if(field.value == 4){ //卡别为其他，将录入框置为可读
		otherCard.attr("class","input");
		otherCard.removeAttr("readonly");
	}else{
		otherCard.attr("class","readOnly");
		otherCard.attr("readonly","readonly");
		otherCard.val("");
	}
}
/**
 * 判断信用卡信息是否录入
 * @return
 */
function checkCard(saveType){
	var riskCode = $(":input[name='prpLclaimRiskCode']").val();
	if(riskCode == "CC"){
		var  prpLclaimCreditBankCode = $.trim($("[name='prpLclaimCreditBankCode']").val());
		if(prpLclaimCreditBankCode.length < 1){
			alert("請選擇发卡银行！");
			return false;
		}
		var  prpLclaimCreditCardCode = $.trim($("[name='prpLclaimCreditCardCode']").val());
		if(prpLclaimCreditCardCode.length < 1){
			alert("請選擇信用卡卡別！");
			return false;
		}
		var  prpLclaimCreditCardNo = $.trim($("[name='prpLclaimCreditCardNo']").val());
		if(prpLclaimCreditCardNo.length < 1){
			alert("請錄入信用卡號碼！");
			return false;
		}
		var  prpLclaimCreditValidDate = $.trim($("[name='prpLclaimCreditValidDate']").val());
		if(prpLclaimCreditValidDate.length < 1){
			alert("請錄入信用卡到期年月！");
			return false;
		}
		var  prpLclaimCreditHolderName = $.trim($("[name='prpLclaimCreditHolderName']").val());
		if(prpLclaimCreditHolderName.length < 1){
			alert("請錄入信用卡持卡人姓名！");
			return false;
		}
		var prpLclaimCreditHolderIdentifyNumber = $.trim($("[name='prpLclaimCreditHolderIdentifyNumber']").val());
		if(prpLclaimCreditHolderIdentifyNumber.length < 1){
			alert("請錄入信用卡持卡人身份證字號！");
			return false;
		}
		var  prpLclaimCreditHolderTel = $.trim($("[name='prpLclaimCreditHolderTel']").val());
		if(prpLclaimCreditHolderTel.length < 1){
			alert("請錄入信用卡持卡人電話！");
			return false;
		}
		var  prpLclaimCreditHolderPhone = $.trim($("[name='prpLclaimCreditHolderPhone']").val());
		if(prpLclaimCreditHolderPhone.length < 1){
			alert("請錄入信用卡持卡人手機！");
			return false;
		}
		var  prpLclaimCreditHolderAddress = $.trim($("[name='prpLclaimCreditHolderAddress']").val());
		if(prpLclaimCreditHolderAddress.length < 1){
			alert("請錄入信用卡持卡人居住地址！");
			return false;
		}
		//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
	    var errorMessage="";
		if (saveType == "4") {
	    var riskCode = $(":input[name='prpLclaimRiskCode']").val();
		var prpLclaimCreditHolderTel =fm.prpLclaimCreditHolderTel.value;
		if (prpLclaimCreditHolderTel.length > 0) {
			 if (prpLclaimCreditHolderTel.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLclaimCreditHolderTel)){
			    		errorMessage =errorMessage +"持卡人電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLclaimCreditHolderTel)){
			    	errorMessage =errorMessage +"持卡人電話有誤\n";
			      }
			}
		  }
		var prpLclaimCreditHolderPhone =fm.prpLclaimCreditHolderPhone.value;
	    if (prpLclaimCreditHolderPhone.length > 0) {
		    	 var reg =/^09[0-9]{8}$/;
		    	  if(!reg.test(prpLclaimCreditHolderPhone)){
		    		  errorMessage =errorMessage +"持卡人手機有誤\n";
		    		}  
	        }
	    if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		}
	  }//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end
	}
	return true;
}
//当卡别为其他时，改变录入框的只读样式 end
$(document).ready(function(){
	initDamageDate();
});