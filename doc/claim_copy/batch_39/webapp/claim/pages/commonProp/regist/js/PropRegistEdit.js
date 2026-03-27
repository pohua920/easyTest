/*****************************************************************************
 * DESC       ：报案登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2013-09-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
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
	var errorMessage = "";

	if (!validateForm(fm)) {
		return false;
	}
	//报案修改时，报案修改人必须輸入
	if (fm.modifyFlag != null) {
		if (fm.modifyFlag.value = "PERFECT" && fm.alterName.value.length < 1) {
			errorMessage = errorMessage + i18n.regist.mustEntries; // 报案修改人必须輸入！\n
		}
	}
	var insuredName = fm.prpLregistInsuredName.value;
	if (insuredName.length < 1) {
		errorMessage = errorMessage + i18n.regist.notAllowNull; //被保险人不允许为空\n
	}
	var damageCode = fm.prpLregistDamageCode.value;
	var damageName = fm.prpLregistDamageName.value;
	if (damageCode == "" || damageName == "") {
		errorMessage = errorMessage + "出險原因不允許為空！\n";
	}
	var context = fm.prpLregistTextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.regist.summaryNotEmpty; //报案摘要不允许为空\n
	}
	if (fm.acceptFlag[0].checked == false && fm.acceptFlag[1].checked == false) {
		errorMessage = errorMessage + i18n.regist.acceptNotEmpty; //受理标志不允许为空\n
	}
	if (fm.repeatInsureFlag[0].checked == false && fm.repeatInsureFlag[1].checked == false) {
		errorMessage = errorMessage + i18n.regist.insuranceCompanies; //是否向别的保险公司投保不允许为空\n
	}

	//增加输单日期和出险日期的判断，输单日期必须在出险日期之後
	var prpLregistStartDate = fm.prpLregistStartDate.value;
	var prpLregistEndDate = fm.prpLregistEndDate.value;
	var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;
	if(prpLregistDamageStartDate.length == 0 ){
		alert("請輸入出險日期！");
		return false;
	}
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
	var reportDate = new Date(prpLregistReportDate.replace(/-/g,"/"));//備案日期
	var reportHour  = fm.prpLregistReportHour.value;
	var reportMinute  = fm.prpLregistReportMinute.value;
	reportDate.setHours(parseInt(reportHour , 10),parseInt(reportMinute , 10),0);
	
	var inputDate = new Date(prpLregistInputDate.replace(/-/g,"/"));//出單日期
	if (prpLregistInputDate < prpLregistDamageStartDate) {
		errorMessage = errorMessage + i18n.regist.dateCannotGreaterDanger+"\n"; //出险日期不能大於输单日期\n
	}
	if (reportDate < damageStartDate) {
		errorMessage = errorMessage + i18n.commonAcci.regist.getOutDangerNotDate; //出险日期不能大於报案日期\n
	}
	//获取报案出险延期天数
	var delayDays = fm.configValue.value;
	var regist_damage = (reportDate.getTime() - damageStartDate.getTime()) / (24 * 60 * 60 * 1000);
	var message = "";
	if (regist_damage >= delayDays) {
		message = i18n.commonLiab.regist.reportExtensNumber + ((delayDays <= 3) ? ( delayDays*24 + "小時" ) : "天") + "！"; //提示：报案出险延期天数大於   天\n
	}
	if ((damageStartDate < startDate) || (damageStartDate > endDate)) {
		errorMessage = errorMessage +i18n.regist.tipDangerTimeDuringReport+ "\n";//出险时间在保险期间以外,不予備案
	}
	if (message.length > 0 && !confirm(message)) {
		return false;
	}
	var othFlag = fm.prpLregistOthFlag.value;
	if(othFlag.length>2 && othFlag.substring(2,3)== "1"){
		errorMessage = errorMessage +i18n.regist.policyNotAllowReport+ "\n";
	}
	var prpLregistAddressCode = fm.prpLregistAddressCode.value;

	var prpLregistDamageAddress = fm.prpLregistDamageAddress.value;
	if (prpLregistDamageAddress.length < 1) {
		errorMessage = errorMessage + i18n.regist.siteNotEmpty; //出险地点不允许为空\n
	}
	var EstiCurrency = fm.prpLregistEstiCurrency.value;
	if (EstiCurrency.length < 1) {
		errorMessage = errorMessage + i18n.regist.moneyNotEmpty; //币别不允许为空\n
	}
	//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
	if (saveType =="4") {
	    var prpLregistReportorPhoneNumber =fm.prpLregistReportorPhoneNumber.value;
	    if (prpLregistReportorPhoneNumber.length > 0) {
			 if (prpLregistReportorPhoneNumber.substr(0, 2)=='09'){
			    	reg =/^09[0-9]{8}$/;
			    	  if(!reg.test(prpLregistReportorPhoneNumber)){
			    		errorMessage =errorMessage +"報案人電話有誤\n";
			    	}
			  } else {
			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
			      if (!reg.test(prpLregistReportorPhoneNumber)){
			    	errorMessage =errorMessage +"報案人電話有誤\n";
			      }
			}
		  }
	    
	    var prpLregistPhoneNumber = fm.prpLregistPhoneNumber.value;
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
	    var prpLrelatePersonMobile =  fm.prpLrelatePersonMobile[i].value; 
	    if (prpLrelatePersonMobile.length > 0) {
	    		  var reg =/^09[0-9]{8}$/;
	    		  if(!reg.test(prpLrelatePersonMobile)){
	    			  errorMessage =errorMessage +"聯繫人"+i+"手機有誤\n";
	    		  }
	      }
		}
 
	} //mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end 
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
	//判断是否是相同保单号码有1个以上的报案,只在登记的时候提示.
	var registNo = fm.prpLregistRegistNo.value;
	var sameCount = parseInt(fm.PerilCount.value);
	var policyNo = fm.prpLregistPolicyNo.value;
	// 增加保单注销,报案时间，倒签单，股东业务，出险次数，临分共保等提示
	var coinsFlag = fm.coinsFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var message = "";
	var othFlag = fm.prpLregistOthFlag.value;
	if (othFlag.substring(3, 4) == "1") {
		message = message + i18n.regist.orderOff; //此保单已注销！\n
		fm.buttonSave.disabled = true;
		//fm.registPrint.disabled = true;
		fm.buttonCancel.disabled = true;
		fm.buttonSaveFinishSubmit.disabled = true;
	}
	var endorType = fm.endorType.value;
	if ("54" == endorType) {
		message = message + i18n.regist.orderStop; //此保单已停效！\n
	}
	var underWriteEndDate = fm.underWriteEndDate.value;
	if (underWriteEndDate > prpLregistStartDate) {
		message = message + i18n.regist.orderBusiness; //此保单为倒签单业务！\n
	}
	if (coinsFlag != 0) {
		message = message + i18n.regist.thisPolicy; //此保单为联/共保单！\n
	}
	if (tempReinsFlag != 0) {
		message = message + i18n.regist.policyBusiness; // 此保单有临分业务！\n
	}
	// 提示是否在保险期限内，是否距离保单起期或止期很近（10天）

	if (checkFlag != 0) {
		errorMessage = errorMessage + i18n.regist.orderCondition; //此保单还处在批改状态 ！\n
	}
	if(prpLregistDamageStartDate!=""){
		if (damageStartDate < startDate || damageStartDate > endDate) {
			message = message + "出險時間不在保單保險期間內！\n"; //出险时间不在保险期间内！\n
		} else if((endDate.getTime() - startDate.getTime()) >= (30 * 24 * 60 * 60 * 1000)){//保險期間30天以上的增加如下提示
			StartTen = Math.round(StartTen);
			EndTen = Math.round(EndTen);
			if (StartTen < 10) {
				message = message + "保單生效 " + (StartTen == 0 ?"當天":(StartTen+"天後")) + " 出險！\n"; //保单生效    天後出险！\n
			}
			if (EndTen < 10) {
				message = message + "出險時間距離終保日期只有 " + EndTen + " 天！\n"; //出险时间离止保日期只有    天！\n
			}
		}
		if (((reportDate.getTime() - damageStartDate.getTime())/ (60 * 60 * 1000)) > 120) {
			message = message + "備案時間超過出險時間 120 小時！\n"; //備案時間超過出險120小时！\n
		}
	}
	if (payFee == -1) {
		message = message + i18n.regist.useCaution; //此保单保费未缴,请慎重处理！！！ \n
	}else if (payFee == 0) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
		message = message + delinquentfeeCase + "\n";
	}
	if (registNo.length < 1) {
		//说明是登记
		if (sameCount > 0) {
			message = message + i18n.regist.policyNumber + policyNo + i18n.regist.accident + sameCount + i18n.regist.view; // 保单号码为     //已经出险   //次，请查看出险次数信息！
			if (RecentCount > 0 && RegistViewLimitDay > 0) {
				message = message + i18n.regist.oneRecently + RegistViewLimitDay + i18n.regist.alreadyGetDanger + RecentCount + i18n.regist.times; //其中最近      //天已经出险    //次！ \n
			}
		} else {
			fm.button_Peril_Open_Context.disabled = true;
		}
		if (message.length > 0) {
			alert(message);
		}

	} else {
		if (message.length > 0) {
			alert(message);
		}
	}
}

/**
 *@description 弹出查看留言对话框
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?registNo=" + fm.prpLregistRegistNo.value;
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
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

//function showSubPage1(spanID, leftMove) {
//	var intLeftMove = (leftMove == null ? 0 : leftMove);
//	var span = eval(spanID );
//	var strTemp = span.id;
//
//	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
//	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y
//
//	ex = ex - 520;
//
//	if (ex < 0) {
//		ex = 0;
//	}
//	ex = ex - intLeftMove;
//
//	span.style.left = ex;
//	span.style.top = ey;
//	span.style.display = '';
//}

//触发该事件时，自动生成报案摘要

function generateRegistText() {
	if (fm.prpLregistTextContextInnerHTML.value==""||confirm(i18n.prompt.regist.isCoverOriginalContent)) {
		var prplRegistText = "       ";
		var prpLregistReportorName = fm.prpLregistReportorName.value;
		if (prpLregistReportorName.length > 0) {

			prplRegistText = prplRegistText + i18n.commonLiab.regist.registPerson + prpLregistReportorName;
		}
		var prpLregistReportDateTmp = fm.prpLregistReportDate.value;
		var prpLregistReportDate = (parseInt(prpLregistReportDateTmp.substring(0,4))-1911) + prpLregistReportDateTmp.substring(4,prpLregistReportDateTmp.length);
		if (prpLregistReportDate.length > 0) {
			prplRegistText = prplRegistText + i18n.common.expression.and + prpLregistReportDate + i18n.regist.prpLregist.date;
		}
		var prpLregistReportHour = fm.prpLregistReportHour.value;
		if (prpLregistReportHour.length > 0) {
			prplRegistText = prplRegistText + prpLregistReportHour + i18n.regist.prpLregist.hour;
		}
		var prpLregistReportMinute = fm.prpLregistReportMinute.value;
		if (prpLregistReportMinute.length > 0) {
			prplRegistText = prplRegistText + prpLregistReportMinute + i18n.regist.prpLregist.minute;
		}
		var reportType = $("select[name='reportType'] option:selected");
		if (reportType.length > 0) {
			prplRegistText = prplRegistText + reportType.text() + ":";
		}
		var prpLregistInsuredName = fm.prpLregistInsuredName.value;
		if (prpLregistInsuredName.length > 0) {
			prplRegistText = prplRegistText + prpLregistInsuredName;
		}
		var prpLregistDamageStartDateTmp = fm.prpLregistDamageStartDate.value;
		var prpLregistDamageStartDate = (parseInt(prpLregistDamageStartDateTmp.substring(0,4))-1911) + prpLregistDamageStartDateTmp.substring(4,prpLregistDamageStartDateTmp.length);
		if (prpLregistDamageStartDate.length > 0) {
			prplRegistText = prplRegistText + i18n.common.expression.and + prpLregistDamageStartDate + i18n.regist.prpLregist.date;
		}
		var prpLregistDamageStartHour = fm.prpLregistDamageStartHour.value;
		if (prpLregistDamageStartHour.length > 0) {
			prplRegistText = prplRegistText + prpLregistDamageStartHour + i18n.regist.prpLregist.hour;
		}
		var prpLregistDamageStartMinute = fm.prpLregistDamageStartMinute.value;
		if (prpLregistDamageStartMinute.length > 0) {
			prplRegistText = prplRegistText + prpLregistDamageStartMinute + i18n.regist.prpLregist.minute;
		}
		var prpLregistDamageName = fm.prpLregistDamageName.value;
		if (prpLregistDamageName.length > 0) {
			prplRegistText = prplRegistText + i18n.common.expression.because + prpLregistDamageName + i18n.common.expression.reason;
		}
		var prpLregistDamageAddress = fm.prpLregistDamageAddress.value;
		if (prpLregistDamageAddress.length > 0) {
			prplRegistText = prplRegistText + i18n.common.expression.on + prpLregistDamageAddress + i18n.common.expression.occurPlace;
		}
		var prpLregistLossName = fm.prpLregistLossName.value;
		if (prpLregistLossName.length > 0) {
			prplRegistText = prplRegistText + i18n.common.expression.DamageObject + prpLregistLossName + ",";
		}
		var prpLregistEstimateLoss = fm.prpLregistEstimateLoss.value;
		if (prpLregistEstimateLoss.length > 0) {
			prplRegistText = prplRegistText + i18n.print.estimateLoss + prpLregistEstimateLoss + i18n.common.expression.yuan;
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
//add by zhangyurui 2009-02-20 增加被保险人查询 begin

function getCinsured(field) {
	code_CodeSelect(field, 'getCinsured', '-1,0,1', 'Y', 'N', fm.policyno.value);
	document.getElementById("insuredCode").innerHTML = fm.prpLregistInsuredCode.value;
	var identifyNumber = $(":input[name='identifyNumber']").val();
	$(":input[name='prpCinsuredIdentifyNumber']").val(identifyNumber);
}
//add by zhangyurui 2009-02-20 增加被保险人查询 end
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
	
	if (countryFlag != "1") {
		fm.countryCName.style.display = "none";
		fm.provinceName.style.display = "none";
		fm.prpLregistAddressCode.style.display = "";
		fm.prpLregistAddressName.style.display = "";
	} else {
		fm.countryCName.style.display = "";
		fm.provinceName.style.display = "none";
		fm.prpLregistAddressCode.style.display = "none";
		fm.prpLregistAddressName.style.display = "none";
	}
}
/**
 * 查询同险的保单
 * @return
 */
function sameAddressPolicyNo(){
	var prpCaddressSameaddressNo = $(":input[name='prpCaddressSameAddressNo']").val();
	var prpLregistDamageStartDate = $(":input[name='prpLregistDamageStartDate']").val();
	var prpLregistDamageStartHour = $(":input[name='prpLregistDamageStartHour']").val();
	var prpLregistPolicyNo = $(":input[name='prpLregistPolicyNo']").val();
	if(prpCaddressSameaddressNo.length==0){
		alert("沒有同險號碼不能查詢同險保單！");
		return false;
	}else if(prpLregistDamageStartDate.length==0||prpLregistDamageStartHour.length==0){
		alert("出險時間为空不能查詢同險保單！");
		return false;
	}
	var url = contextRootPath+"/regist/sameAddressPolicyNo.do?prpCaddressSameaddressNo="
	+prpCaddressSameaddressNo+"&prpLregistDamageStartDate="+prpLregistDamageStartDate
	+"&prpLregistDamageStartHour="+prpLregistDamageStartHour+"&prpLregistPolicyNo="+prpLregistPolicyNo;
	win = window.open(url, "NewWindow", "status=no,resizable=yes,scrollbars=yes,top=100,left=100,width=900,Height=500");
	return true;
}