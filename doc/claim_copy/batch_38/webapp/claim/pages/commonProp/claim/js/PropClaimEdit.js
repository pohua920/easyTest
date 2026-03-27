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
	//增加保单注销,，股东业务，等提示； 2005-7-28
	var coinsFlag = fm.coinsFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var message = "";

	var othFlag = fm.prpLclaimOthFlag.value;
	if (coinsFlag != 0) {
		message = message + i18n.claim.totalPolicy; //本保单为联/共保单！\n
		message = message + i18n.claim.estimateAmount; //估损金额请輸入分摊前的总估损金额！\n
	}
	if (tempReinsFlag != 0) {
		message = message + i18n.check.proBusiness; //此保单有临分业务！\n
	}

	if (othFlag.substring(3, 4) == "1") {
		message = message + i18n.regist.orderOff; //此保单已注销！\n
	}
	var underWriteEndDate = fm.underWriteEndDate.value;
	var prpLclaimStartDate = fm.prpLclaimStartDate.value;

	var payFee = parseInt(fm.payFee.value);
	var delinquentfeeCase = fm.delinquentfeeCase.value;
	if (payFee == -1) {
		message = message + i18n.regist.useCaution; //此保单保费未缴,请慎重处理！！！ \n
	}else if (payFee == 0) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
		message = message + delinquentfeeCase + "\n";
	}
	if (message.length > 0) {
		alert(message);
	}
	if (fm.BaseCurrency1.value != CURRENCYINFO.LOCAL_CURRENCY) {
		alert(i18n.claim.singleCurrency + fm.BaseCurrency1.value + i18n.claim.useSparingly + fm.ExchRate1.value); //此案件签单币别为   //，不是CNY，请慎重处理！\n当前兑换率为
	}
	var reportDamageMessage = $(":input[name='reportDamageMessage']").val();
	if(reportDamageMessage != ""){
		alert(reportDamageMessage);
	}
	sumAmount();
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
	if (window.confirm(i18n.prompt.regist.isClear)) {//確定要清除嗎？
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
	// 立案提交增加险别不能为空的控制 begin
	var kindList = document.getElementsByName("prpLclaimLossKindCode");
	var kindNameList = document.getElementsByName("prpLclaimLossKindName");
	for (var i = 1; i < kindList.length; i++) {
		if (kindList[i].value == "" || kindNameList[i].value == "") {
			errorMessage = errorMessage + i18n.claim.risksCannotEmpty //险别不能为空!\n
			break;
		}
	}
	//立案环节提交增加危险单位不能为空的控制 begin
	var dangerNoList = document.getElementsByName("prpLclaimLossDangerNo");
	for (var i = 0; i < dangerNoList.length; i++) {
		if (dangerNoList[i].value == null || dangerNoList[i].value == "") {
			errorMessage = errorMessage + i18n.claim.dangerousCannotEmpty //危险单位不能为空!\n
			break;
		}
	}
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
//	if (claim_days == 0) {
//		if (!confirm(i18n.claim.caseGreaterSystemTime + standardDays + i18n.claim.dayWhetherSubmit)) {
//			return false;
//		}
//	}
	if (fm.all("prpLclaimLossKindCode").length == undefined || fm.all("prpLclaimLossKindCode").length < 1) {
		alert(i18n.claim.enterInformation); //请输入估损信息！
		return false;
	}
	if(!checkLoss()){
		return false;
	}
	collectClaimLoss();
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm, "ClaimLoss_Data")) {
		return false;
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	$("#buttonArea").find(":button").prop("disabled",true);
	fm.submit();
	return true;
}

//装载数据

function loadForm() {}

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
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}


//得到一页的多行纪录的记录数
//页名称

function getRowsCount(PageCode) {
	var oTBODY = document.all(PageCode).tBodies.item(0);
	var intCount = oTBODY.rows.length;
	return intCount;
}


//按钮单击事件，用於相同保单号码多报案的显示

//function buttonOnClick(strSubPageCode) {
//	var sameCount = parseInt(fm.PerilCount.value);
//
//	if (sameCount < 1) {
//		fm.button_Peril_Open_Context.disabled = true;
//		return;
//	}
//	showSubPage1(strSubPageCode);
//
//}

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
 @description 校验索赔金额
 @param       无
 @return      boolean
 */

function checkLoss() {
	var riskCode = fm.prpLclaimRiskCode.value
	//1.检查必须要有一条记录
	if (getRowsCount("ClaimLoss") == 0) {
		errorMessage(i18n.claim.oneRecord); //估损金额金额信息至少要有一条记录!

		return false;
	}
	//2币别不能为空的

	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.amountCannot); //第       //条估损金额中币别不能为空!
			return false;
		}
		if (isEmptyField(fm.prpLclaimLossKindCode[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.risksCode); //第		//条事故估损金额中险别代码不能为空!
			return false;
		}
		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.NotEmpty); //第		//条事故估损金额中金额不能为空!
			return false;
		}
	}
	return checkBeyondSumAmount();
}

function collectCurrency() {
	var collectCurr = "";
	var collectTemp = new Array();
	collectCurr = i18n.modifySumClaim.summary + "\n"; //分币别汇总结果:\n
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
		collectCurr = collectCurr + currency + "  " + currencyName + "  " + sumLossAmount + i18n.modifySumClaim.money + "\n"; //.00元\n
	}
	if (collectCurr.length > 0) {
		alert(collectCurr);
		return false;
	}
}

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
	var riskCode = $(":input[name='prpLclaimRiskCode']").val();

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
		var kindCode = $KindCode.get(i).value;
		if($LossFeeType.get(i).value=="P"){
			//mantis3645: 住宅火災及地震基本保險不設保額為限
			if(riskCode=="F02" && (kindCode == 'FR1' || kindCode == 'FR3' || kindCode == 'FR2')){
				return true;
			}
			kindCodes = kindCode + "_"+$ItemCode.get(i).value;
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
			errorMessage += "險別"+kindCodes[0]+"，標的物"+kindCodes[1]+i18n.prompt.claim.sumInsured; //估损金额不能大於保额
			flag = false;
			return false;
		}
	});
	if(errorMessage != ""){
		alert(errorMessage);
	}
	return flag;
}
/**
 * 險別估損金額=損失金額-自負額-剔除金額/殘值
 * 通過計算產生的‘險別估損金額’不可以修正。
 */
function calculateSumClaim(field){
	var $KindRest = $(":input[name='prpLclaimLossKindRest']");
	$(":input[name='prpLclaimLossSumClaim']").each(function(i,n){
		if(!$.isNumeric(n.value)){
			n.value = "0";
		}
		if(!$.isNumeric($KindRest.get(i).value)){
			$KindRest.get(i).value = "0";
		}
	});
	checkBeyondSumAmount();
	collectClaimLoss();
}

//add by luochang 2010-06-22 根据出现地址所属国内或国外判断双击域是否显示 begin

function countryFlag_change(countryFlag) {
	if (countryFlag != "1") {
		fm.countryCName.style.display = "none";
		fm.provinceName.style.display = "none";
		fm.cityName.style.display = "";
	} else {
		fm.countryCName.style.display = "";
		fm.provinceName.style.display = "none";
		fm.cityName.style.display = "none";
	}
}
/** 合并所有的保险金额 */
function sumAmount(){
	var amount = $(":input[name='prpLclaimLossAmount']");
	var sumAmount = 0;
	$("#ClaimLoss").find(":input[name='prpLclaimLossAmount']").each(function(){
		if($.isNumeric(this.value)){
			sumAmount += parseFloat(this.value);
		}
	});
	$(":input[name='prpLclaimSumAmount']").val(pointTwo(sumAmount));
}

//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 START
function initenableEditTableField(){
	if(undefined!=fm.buttonReturn){
		fm.buttonReturn.disabled = false; //返回按鈕可使用
	}
	fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
	fm.prpLclaimDamageStartDate_show_format_rcDate.readOnly = false; //出險時間
	fm.prpLclaimDamageStartHour.readOnly = false; //出險小時
	fm.prpLclaimDamageStartMinute.readOnly = false; //出險分鐘
	fm.cityCode.readOnly = true; //出險地區代碼
	fm.cityName.readOnly = true; //出險地區名稱
	
	fm.countryFlag.disabled = true;//出險地點代碼
	fm.cityName.disabled = true;
	fm.prpLclaimDamageAddress.readOnly = true; 
	
	fm.prpLclaimReceiptDate_show_format_rcDate.disabled = true;//收件日期
	fm.ReplevyLimitDate_show_format_rcDate.disabled = true;//追償時效 
	fm.prpLclaimLossName.disabled = true;//受損標的
	fm.prpLclaimReplevyRemark.disabled = true;//追償說明
	fm.replevyFlag.disabled=true;//是否可能有追償 :replevyFlag
	fm.thirdComFlag.disabled=true;//是否有其他理賠中介機構 :
	fm.referLawFlag.disabled=true;//是否涉及訴訟
	//TAB DISABLE
	$('[TABTEXT="估損金額訊息"]')
    .off('click')
    .removeAttr('onclick')
    .css({
        'pointer-events': 'none',
        'user-select': 'none'
    })
    .prop('disabled', true);
	//TAB DISABLE
	$('[TABTEXT="出險摘要"]')
    .off('click')
    .removeAttr('onclick')
    .css({
        'pointer-events': 'none',
        'user-select': 'none'
    })
    .prop('disabled', true);
	//TAB DISABLE
	$('[TABTEXT="危險單位訊息"]')
    .off('click')
    .removeAttr('onclick')
    .css({
        'pointer-events': 'none',
        'user-select': 'none'
    })
    .prop('disabled', true);
	
	$("input[name='prpLclaimDamageStartDate_show_format_rcDate']").attr('style','width:100px');
	$("input[name='prpLclaimDamageStartHour']").attr('style','width: 25px');
	$("input[name='prpLclaimDamageStartMinute']").attr('style','width: 25px');
	//$("input[name='cityCode']").attr('style','width: 27%');
	//$("input[name='cityName']").attr('style','width: 48%');
}

function updateClaimEditForm(field,saveType){
	debugger;
	if (enableFeature(field,saveType)){
		return true;
	}
	inputProcessing(true);
	fm.buttonBack.disabled = false; //返回按鈕可使用
	fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
	return false;
}

function enableFeature(field,saveType){
	// 檢查出險原因
	if(fm.prpLclaimDamageCode.value.length == 0){
		alert("請輸入出險原因！");
		fm.buttonBack.disabled = false; //返回按鈕可使用
		fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
		return false;
	}
	// 檢查出險日期
	if(fm.prpLclaimDamageStartDate.value.length == 0){
		alert("請輸入出險日期！");
		fm.buttonBack.disabled = false; //返回按鈕可使用
		fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
		return false;
	}
	if (fm.prpLclaimDamageStartHour.value.length == 0){
		alert("請輸入出險小時！");
		fm.buttonBack.disabled = false; //返回按鈕可使用
		fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
		return false;
	}
	var strDamageStartMinute = ""!=fm.prpLclaimDamageStartMinute.value?fm.prpLclaimDamageStartMinute.value:"01";
	fm.prpLclaimDamageStartMinute.value = strDamageStartMinute;
	var fullDamageStartDate = fm.prpLclaimDamageStartDate.value +" "+fm.prpLclaimDamageStartHour.value +":"+fm.prpLclaimDamageStartMinute.value;
	var prpLclaimReceiptDate = fm.prpLclaimReceiptDate.value;
	if(!(new Date(prpLclaimReceiptDate.replace(/-/g,'/')) > new Date(fullDamageStartDate.replace(/-/g,'/')))){
		alert("出險時間不可大於收件時間！");
		fm.buttonBack.disabled = false; //返回按鈕可使用
		fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
		return false;
	}

	if (!checkDamageDate()){ //確認出險日期是否在保期內
		fm.buttonBack.disabled = false; //返回按鈕可使用
		fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
		return false;
	}
	
	inputProcessing(false);
	field.disabled = true;
	fm.submit();

	return true;
}

function inputProcessing(field){
	var len = document.all.length;

	for(var i=0; i<len; i++){
	  var tempElements = document.all(i);
	  if(tempElements.tagName=="INPUT"){
	      if(tempElements.type=="radio"){
	          //將選擇域變為可讀
	       tempElements.disabled=field;
	      }else if(tempElements.type=="button"){
		      //將選擇域變為可讀
	    	  tempElements.disabled = field;
	      }else if(tempElements.type=="checkbox"){
	    	  tempElements.disabled = field;
	      }
	  } else if(tempElements.tagName=="SELECT" || tempElements.tagName=="select"){
	      //將選擇域變為可讀
		  tempElements.disabled = field;
	  } else if(tempElements.tagName=="IMG"){
		  //將選擇域變為可讀
		  tempElements.disabled = field;
	  }
	}
}

function checkDamageDate(){
	var prpLregistStartDate = fm.prpLregistStartDate.value; //起保日
	var prpLregistEndDate = fm.prpLregistEndDate.value; //終保日
	
	var startDate = new Date(prpLregistStartDate.replace(/-/g,"/"));//起保日期
	startDate.setHours(parseInt(fm.prpLregistStartHour.value , 10),0,0);
	var endDate = new Date(prpLregistEndDate.replace(/-/g,"/"));//終保日期
	endDate.setHours(parseInt(fm.prpLregistEndHour.value , 10),0,0);
	var damageStartDate = new Date(fm.prpLclaimDamageStartDate.value.replace(/-/g,"/"));//出險日期
	var damageStartHour  = fm.prpLclaimDamageStartHour.value;
	if (fm.prpLclaimDamageStartHour.value.length < 2) {
	    fm.prpLclaimDamageStartHour.value = '0' + fm.prpLclaimDamageStartHour.value;
	}
	var damageStartMinute  = fm.prpLclaimDamageStartMinute.value;
	if (fm.prpLclaimDamageStartMinute.value.length < 2) {
	    fm.prpLclaimDamageStartMinute.value = '0' + fm.prpLclaimDamageStartMinute.value;
	}
	damageStartDate.setHours(parseInt(damageStartHour , 10),parseInt(damageStartMinute , 10),0);
	if ((damageStartDate < startDate) || (damageStartDate > endDate)) {
//		alert(i18n.claim.tipDangerTimeDuringReport); // 提示：出險時間在保險期間以外，不允許修改!
		alert("提示：出險時間在保險期間以外，不允許修改!"); // 提示：出險時間在保險期間以外，不允許修改!
		fm.buttonBack.disabled = false; //返回按鈕可使用
		fm.buttonSaveFinishSubmit.disabled = false; //提交按鈕可使用
		return false;
	}
	return true;
}
//mantis：CLM0272 ，處理人員：DP0713，需求單編號：新核心-立案修改功能新增火險修改 END