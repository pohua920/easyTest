/*****************************************************************************
 * DESC       ：报案登记的脚本函数页面
 * AUTHOR     ：中科软
 * MODIFYLIST ：   Name       Date            Reason/Contents
 ****************************************************************************/
/**
 *@description 检查报案登记
 *@param       无
 *@return      通过返回true,否则返回false
 */
function checkForm() {
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
	if (window.confirm(i18n.prompt.regist.isClear)) {
		var vURL = fm.originalRequestURITemp.value;	 
		location.href = vURL;
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
	if (!validateForm(fm, 'ThirdProp_Data')) {
		return false;
	}
	var errorMessage = "";
	//报案修改时，报案修改人必须輸入
	if (fm.modifyFlag != null) {
		if (fm.modifyFlag.value = "PERFECT" && fm.alterName.value.length < 1) {
			errorMessage = errorMessage + "报案修改人必须輸入！\n";
		}
	}
	//被保险人不准许为空 begin
	var insuredName = fm.prpLregistInsuredName.value;
	if (insuredName.length < 1) {
		errorMessage = errorMessage + i18n.commonAcci.regist.insurNotAllowNull; //被保险人不允许为空\n
	}
	// 被保险人不准许为空 end

	//增加输单日期和出险日期的判断，输单日期必须在出险日期之後
	var prpLregistStartDate = fm.prpLregistStartDate.value;
	var prpLregistEndDate = fm.prpLregistEndDate.value;
	var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;
	if(prpLregistDamageStartDate.length == 0 ){
		alert("請輸入出險日期！");
		return false;
	}
	var prpLregistReportDate = fm.prpLregistReportDate.value;//备案日期
	if(prpLregistReportDate.length == 0 ){
		alert("備案日期不能為空！");
		return false;
	}
	var prpLregistInputDate = fm.prpLregistInputDate.value;//输入日期
	var startDate = new Date(prpLregistStartDate.replace(/-/g,"/"));//起保日期
	startDate.setHours(parseInt(fm.prpLregistStartHour.value , 10),0,0);
	var endDate = new Date(prpLregistEndDate.replace(/-/g,"/"));//終保日期
	endDate.setHours(parseInt(fm.prpLregistEndHour.value , 10),0,0);
	var damageStartDate = new Date(prpLregistDamageStartDate.replace(/-/g,"/"));//出險日期
	var damageStartHour  = fm.prpLregistDamageStartHour.value;
	var damageStartMinute  = fm.prpLregistDamageStartMinute.value;
	damageStartDate.setHours(parseInt(damageStartHour , 10),parseInt(damageStartMinute , 10),0);
	var reportDate = new Date(prpLregistReportDate.replace(/-/g,"/"));//備案日期
	var reportHour  = fm.prpLregistReportHour.value;
	var reportMinute  = fm.prpLregistReportMinute.value;
	reportDate.setHours(parseInt(reportHour , 10),parseInt(reportMinute , 10),0);
	
	if (damageStartDate < startDate) {
		var StrMessage = "提示：出險時間在起運日之前，是否繼續？"; //提示：出险时间小於起运日期，是否继续？
		if (!confirm(StrMessage)) {
			return false;
		}
	}
	var registReportDate = fm.prpLregistReportDate_show_format_rcDate.value;
	if(registReportDate.length < 1){
		errorMessage = errorMessage + "備案日期不能為空！\n";
	}
	var registDamageStartDate = fm.prpLregistDamageStartDate_show_format_rcDate.value;
	if(registDamageStartDate.length < 1){
		errorMessage = errorMessage + "出險日期不能為空！\n";//出险时间不能为空
	}
	if(fm.prpLregistDamageCode.value.length < 1 ||  fm.prpLregistDamageName.value.length < 1){
		errorMessage = errorMessage + "出險原因不能為空！\n";//出险原因不能为空
	}
	var damageAddress = fm.prpLregistDamageAddress.value;
	if (damageAddress.length < 1) {
		errorMessage = errorMessage + "出險地點不能為空！\n"; //出险地点不允许为空\n
	}
	var linkerName = fm.prpLregistLinkerName.value;
	if (linkerName.length < 1) {
		errorMessage = errorMessage + "聯繫人不能為空！\n"; //联系人不允许为空\n
	}
	var context = fm.prpLregistTextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + "出險摘要不能為空！"; //出险摘要不允许为空\n
	}
	if (fm.acceptFlag[0].checked == false && fm.acceptFlag[1].checked == false) {
		errorMessage = errorMessage + "請選擇是否受理！\n"; //受理标志不允许为空\n
	}
	if (fm.repeatInsureFlag[0].checked == false && fm.repeatInsureFlag[1].checked == false) {
		errorMessage = errorMessage + "請選擇是否向其它保險公司投保！\n"; //是否向别的保险公司投保不允许为空\n
	}
	//reason:增加输单日期和出险日期的判断，输单日期必须在出险日期之後
	if (prpLregistInputDate < prpLregistDamageStartDate) {
		errorMessage = errorMessage + "出險日期不能大於備案輸入日期！\n"; //出险日期不能大於输单日期\n
	}
	if (reportDate < damageStartDate) {
		errorMessage = errorMessage + i18n.commonAcci.regist.getOutDangerNotDate; //出险日期不能大於报案日期\n
	}
	//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
	if (saveType == "4") {
	var prpLregistReportorPhoneNumber =fm.prpLregistReportorPhoneNumber.value;
	if (prpLregistReportorPhoneNumber.length > 0) {
		 if (prpLregistReportorPhoneNumber.substr(0, 2)=='09'){
		    	reg =/^09[0-9]{8}$/;
		    	  if(!reg.test(prpLregistReportorPhoneNumber)){
		    		errorMessage =errorMessage +"備案人電話有誤\n";
		    	}
		  } else {
		      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
		      if (!reg.test(prpLregistReportorPhoneNumber)){
		    	errorMessage =errorMessage +"備案人電話有誤\n";
		      }
		}
	  }
	
	  var  prpLregistPhoneNumber  =fm.prpLregistPhoneNumber.value;
	  if (prpLregistPhoneNumber.length > 0) {
			 if (prpLregistPhoneNumber.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLregistPhoneNumber)){
			    		errorMessage =errorMessage +"聯繫電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLregistPhoneNumber)){
			    	errorMessage =errorMessage +"聯繫電話有誤\n";
			      }
			}
		}
	  
	  for (var i = 1; i < fm.prpLrelatePersonPhoneNumber.length; i++) {
        var prpLrelatePersonPhoneNumber =fm.prpLrelatePersonPhoneNumber[i].value;
        if (prpLrelatePersonPhoneNumber.length > 0) {
			 if (prpLrelatePersonPhoneNumber.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLrelatePersonPhoneNumber)){
			    		errorMessage =errorMessage +"聯繫人"+i+"電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLrelatePersonPhoneNumber)){
			    	errorMessage =errorMessage +"聯繫人"+i+"電話有誤\n";
			      }
			}
		  }
        }
		for (var i = 1; i < fm.prpLrelatePersonMobile.length; i++) {
        var prpLrelatePersonMobile =fm.prpLrelatePersonMobile[i].value;
		if (prpLrelatePersonMobile.length > 0) {
		    	 var reg =/^09[0-9]{8}$/;
		    	  if(!reg.test(prpLrelatePersonMobile)){
		    		  errorMessage =errorMessage +"聯繫人"+i+"手機有誤\n ";
		    	}   	
		  }
        } 
    }//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end 
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//获取报案出险延期天数
	var delayDays = fm.configValue.value;
	var message = "";
	var regist_damage = (reportDate.getTime() - damageStartDate.getTime()) / (24 * 60 * 60 * 1000);
	if (regist_damage >= delayDays) {
		message = message + i18n.commonLiab.regist.reportExtensNumber + delayDays + i18n.regist.day; //提示：报案出险延期天数大於    天，\n
	}
	//貨物運輸險、商動險、貨物運送人責任險、海運和空運承攬人責任險是“保險期不確定，不需要提示訊息”。
	if(!(fm.riskcode.value=='MC' || fm.riskcode.value=='OP' || fm.riskcode.value=='TB' || fm.riskcode.value=='CL' || fm.riskcode.value=='CF' || fm.riskcode.value=='FL')){
		if ((damageStartDate < startDate) || (damageStartDate > endDate)) {
			errorMessage = errorMessage +i18n.regist.tipDangerTimeDuringReport+ "\n";//出险时间在保险期间以外,不予備案
		}
	}
	if (message.length > 0 && !confirm(message)) {
		return false;
	}
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	fm.buttonSaveType.value = saveType;
	field.disabled = true;
	fm.submit();
	return true;
}

/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet() {
	//提示是否在保险期限内，是否距离保单起期或止期很近（10天）
	var checkFlag = fm.checkFlag.value;
	var sameCount = parseInt(fm.PerilCount.value);
	var RecentCount = parseInt(fm.RecentCount.value);
	var RegistViewLimitDay = parseInt(fm.RegistViewLimitDay.value);
	var payFee = parseInt(fm.prpLregistPayFee.value);
	var delinquentfeeCase = fm.delinquentfeeCase.value;

	var prpLregistStartDate = fm.prpLregistStartDate.value;
	var prpLregistEndDate = fm.prpLregistEndDate.value;
	var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;
	var prpLregistReportDate = fm.prpLregistReportDate.value;//备案日期
	var prpLregistInputDate = fm.prpLregistInputDate.value;//输入日期
	var startDate = new Date(prpLregistStartDate.replace(/-/g,"/"));//起保日期
	startDate.setHours(parseInt(fm.prpLregistStartHour.value , 10),0,0);
	var endDate = new Date(prpLregistEndDate.replace(/-/g,"/"));//終保日期
	endDate.setHours(parseInt(fm.prpLregistEndHour.value , 10),0,0);
	var damageStartDate = new Date(prpLregistDamageStartDate.replace(/-/g,"/"));//出險日期
	var damageStartHour  = fm.prpLregistDamageStartHour.value;
	var damageStartMinute  = fm.prpLregistDamageStartMinute.value;
	damageStartDate.setHours(parseInt(damageStartHour , 10),parseInt(damageStartMinute , 10),0);
	var StartTen = (damageStartDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
	var EndTen = (endDate.getTime() - damageStartDate.getTime()) / (24 * 60 * 60 * 1000);
	//備案日期
	var reportDate = new Date(prpLregistReportDate.replace(/-/g,"/"));
	var reportHour  = fm.prpLregistReportHour.value;
	var reportMinute  = fm.prpLregistReportMinute.value;
	reportDate.setHours(parseInt(reportHour , 10),parseInt(reportMinute , 10),0);
	
	if (fm.prpLregistRegistNo.length < 10) {
		fm.messageSave.disabled = true;
		fm.messageView.disabled = true;
	}
	var shareHolderFlag = fm.shareHolderFlag.value;
	var message = "";
	//增加保单注销,报案时间，倒签单，股东业务，出险次数，临分共保等提示
	var othFlag = fm.prpLregistOthFlag.value;
	if (othFlag.substring(3, 4) == "1") {
		message = message + i18n.claim.policyCancelled; //此保单已注销！\n
		fm.buttonSave.disabled = true;
		fm.registPrint.disabled = true;
		fm.buttonCancel.disabled = true;
		fm.buttonSaveFinishSubmit.disabled = true;
	}
	var endorType = fm.endorType.value;
	if ("54" == endorType) {
		message = message + i18n.commonAcci.regist.policyStopWork; //此保单已停效！\n
	}
	var underWriteEndDate = fm.underWriteEndDate.value;
	if (underWriteEndDate > prpLregistStartDate) {
		message = message + i18n.commonAcci.regist.policySingleBusiness; //此保单为倒签单业务！\n
	}
	//提示是否在保险期限内，是否距离保单起期或止期很近（10天）
	if (checkFlag != 0) {
		message = message + i18n.regist.orderCondition; //此保单还处在批改状态 ！\n
	}
	if (damageStartDate < startDate || damageStartDate > endDate) {
		message = message + "出險時間不在保單保險期間內！\n"; //出险时间不在保险期间内！\n
	}
	if ((prpLregistInputDate > prpLregistEndDate) || (prpLregistInputDate < prpLregistStartDate)) {
		message = message + "備案輸入日期不在保單保險期間內！\n"; //備案輸入日期不在保險區間！\n
	}
	if ((reportDate.getTime() - damageStartDate.getTime()) > (30 * 24 * 60 * 60 * 1000) ) {
		message = message + i18n.regist.registLateDamage30Day+"\n"; //備案時間超過出險30天！\n
	}
	
	//判断是否是相同保单号码有1个以上的报案,只在登记的时候提示.
	var registNo = fm.prpLregistRegistNo.value;
	var sameCount = parseInt(fm.PerilCount.value);
	var policyNo = fm.prpLregistPolicyNo.value;
	if (registNo.length < 1) {
		//说明是登记
		if (sameCount > 0) {
			message = message + i18n.regist.policyNumber + policyNo + i18n.commonAcci.regist.alreadyDanger + sameCount + i18n.commonAcci.regist.timesCheckInformation; //保单号码为   已经出险    次，请查看出险次数信息！
			if (RecentCount > 0 && RegistViewLimitDay > 0) {
				message = message + i18n.regist.oneRecently + RegistViewLimitDay + i18n.regist.alreadyGetDanger + RecentCount + i18n.regist.times; //其中最近   天已经出险   次！ \n
			}
		} else {
			fm.button_Peril_Open_Context.disabled = true;
		}
	}
	if (payFee == -1) {
		message = message + i18n.certainLoss.policyPremiumNoPay; //此保单保费未缴,请慎重处理！！！ \n
	}else if (payFee == 0) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
		message = message + delinquentfeeCase + "\n";
	}
	if (message.length > 0) {
		alert(message);
	}
	return true;
}

function noChange() {
	fm.insureCarFlag[1].value = 1;
	alert(i18n.regist.car); //涉案车辆的第一辆车必须为保单车辆
	return true;
}

/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relate() {

	var policyNo = fm.prpLregistPolicyNo.value;
	var registNo = fm.prpLregistRegistNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");

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
//触发该事件时，自动生成报案摘要

function generateRegistText() {
	var isCoverOriginalContent = i18n.prompt.regist.isCoverOriginalContent;
	if (fm.prpLregistTextContextInnerHTML.value==""||confirm(isCoverOriginalContent)) {
		var prplRegistText = "       ";
		var prpLregistReportorName = fm.prpLregistReportorName.value;
		if (prpLregistReportorName.length > 0) {
			prplRegistText = prplRegistText + "報案人" + prpLregistReportorName;
		}
		var prpLregistReportDate = fm.prpLregistReportDate.value;
		if (prpLregistReportDate.length > 0) {
			prplRegistText = prplRegistText + "於" + prpLregistReportDate + "日";
		}
		var prpLregistReportHour = fm.prpLregistReportHour.value;
		if (prpLregistReportHour.length > 0) {
			prplRegistText = prplRegistText + prpLregistReportHour + "時";
		}
		var prpLregistReportMinute = fm.prpLregistReportMinute.value;
		if (prpLregistReportMinute.length > 0) {
			prplRegistText = prplRegistText + prpLregistReportMinute + "分";
		}
		var reportType = $("select[name='reportType'] option:selected");
		if (reportType.length > 0) {
			prplRegistText = prplRegistText + reportType.text() + ":";
		}
		var prpLregistInsuredName = fm.prpLregistInsuredName.value;
		if (prpLregistInsuredName.length > 0) {
			prplRegistText = prplRegistText + prpLregistInsuredName;
		}
		var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;
		if (prpLregistDamageStartDate.length > 0) {
			prplRegistText = prplRegistText + "於" + prpLregistDamageStartDate + "日";
		}
		var prpLregistDamageStartHour = fm.prpLregistDamageStartHour.value;
		if (prpLregistDamageStartHour.length > 0) {
			prplRegistText = prplRegistText + prpLregistDamageStartHour + "時";
		}
		var prpLregistDamageStartMinute = fm.prpLregistDamageStartMinute.value;
		if (prpLregistDamageStartMinute.length > 0) {
			prplRegistText = prplRegistText + prpLregistDamageStartMinute + "分";
		}
		var prpLregistDamageName = fm.prpLregistDamageName.value;
		if (prpLregistDamageName.length > 0) {
			prplRegistText = prplRegistText + "由於" + prpLregistDamageName + "原因";
		}
		var prpLregistDamageAddress = fm.prpLregistDamageAddress.value;
		if (prpLregistDamageAddress.length > 0) {
			prplRegistText = prplRegistText + "在" + prpLregistDamageAddress + "位置發生事故，";
		}
		var prpLregistLossName = fm.prpLregistLossName.value;
		if (prpLregistLossName.length > 0) {
			prplRegistText = prplRegistText + "受損標的物為" + prpLregistLossName + ",";
		}
		var prpLregistEstimateLoss = fm.prpLregistEstimateLoss.value;
		if (prpLregistEstimateLoss.length > 0) {
			prplRegistText = prplRegistText + "估計損失" + prpLregistEstimateLoss + "台幣.";
		}
		fm.prpLregistTextContextInnerHTML.value = prplRegistText;
	}

}

function changeLxr() {
	var prpLregistReportorName = fm.prpLregistReportorName.value;

	if (trim(fm.prpLregistLinkerName.value).length == 0) {
		fm.prpLregistLinkerName.value = prpLregistReportorName;
	}
}

//增加被保险人查询 begin
function getCinsured(field) {
	code_CodeSelect(field, 'getCinsured', '-1,0', 'Y', 'N', fm.policyno.value);
	document.getElementById("insuredCode").innerHTML = fm.prpLregistInsuredCode.value;
}

function countryFlag_change(countryFlag) {
	if (countryFlag == "0") {
		fm.prpLregistAddressCode.style.display = "";
		fm.prpLregistAddressName.style.display = "";
	} else {
		fm.prpLregistAddressCode.style.display = "none";
		fm.prpLregistAddressName.style.display = "none";
		fm.prpLregistAddressCode.value = "";
		fm.prpLregistAddressName.value = "";
	}
	fm.prpLregistDamageAddress.value = "";
}
function clearPortCode() {
	fm.prpLregistDamageAddress.value = "";
}
