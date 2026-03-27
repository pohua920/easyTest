/*****************************************************************************
 * DESC       ：报案登记的脚本函数页面
 * AUTHOR     ：中科软
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
	if (!validateForm(fm, 'ThirdProp_Data')) {
		return false;
	}
	var errorMessage = "";
	//出险日期必须大於等於起运日期
	var startDate = fm.prpLregistStartDate.value;
	var damageDate = fm.prpLregistDamageStartDate.value;
	if (damageDate < startDate) {
		StrMessage = i18n.commonShip.regist.dangerTimeLessWhetherContinue;
		if (!confirm(StrMessage)) {
			return false;
		}
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
	// 被保险人不准许为空 end
	var damageAddress = fm.prpLregistDamageAddress.value;
	if (damageAddress.length < 1) {
		errorMessage = errorMessage + i18n.regist.siteNotEmpty; //出险地点不允许为空\n
	}
	var linkerName = fm.prpLregistLinkerName.value;
	if (linkerName.length < 1) {
		errorMessage = errorMessage + i18n.regist.linkPeople; //联系人不允许为空\n
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
	//reason:增加输单日期和出险日期的判断，输单日期必须在出险日期之後
	var inputDate = fm.prpLregistInputDate.value;
	if (inputDate < damageDate) {
		errorMessage = errorMessage + i18n.regist.dateCannotGreaterDanger+"\n"; //出险日期不能大於输单日期\n
	}
	if (registDay < damageDay) {
		errorMessage = errorMessage + i18n.regist.greaterReported; //出险日期不能大於报案日期\n
	}
	var EstiCurrency = fm.prpLregistEstiCurrency.value;
	if (EstiCurrency.length < 1) {
		errorMessage = errorMessage + i18n.regist.moneyNotEmpty; //币别不允许为空\n
	}
	//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start 
	if(saveType=="4"){
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
		 var count = 0;
		    var prpLrelatePersonPhoneNumber ="";
		    var prpLrelatePersonMobile ="";
		    count = getElementCount("prpLrelatePersonPhoneNumber");
		    if (count > 1) {
		        for (var i = 1; i < count; i++) {
		            prpLrelatePersonPhoneNumber ="";
		            prpLrelatePersonPhoneNumber = fm.prpLrelatePersonPhoneNumber[i].value ;
		            if (prpLrelatePersonPhoneNumber.length > 0) {
		   			 if (prpLrelatePersonPhoneNumber.substr(0, 2)=='09'){
		   			    	reg =/^09[0-9]{8}$/;
		   			    	  if(!reg.test(prpLrelatePersonPhoneNumber)){
		   			    		errorMessage =errorMessage +"聯繫人電話有誤\n";
		   			    	}
		   			  } else {
		   			      reg =/^[0-9]{2,3}[0-9]{7,8}$/;
		   			      if (!reg.test(prpLrelatePersonPhoneNumber)){
		   			    	errorMessage =errorMessage +"聯繫人電話有誤\n";
		   			      }
		   			}
		   		  }
		    	}
		    }
		    count = getElementCount("prpLrelatePersonMobile");
		    	if (count > 1) {
	                for (var i = 1; i < count; i++) {
		               prpLrelatePersonMobile ="";
		               prpLrelatePersonMobile = fm.prpLrelatePersonMobile[i].value ;
		               if (prpLrelatePersonMobile.length > 0) {
		              		   var reg =/^09[0-9]{8}$/;
		              		   if(!reg.test(prpLrelatePersonMobile)){
		                  			errorMessage =errorMessage +"聯繫人手機有誤";
		              			}	
		                 }     
		    		}
		    } 
		
		
    } //mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start 
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//获取报案出险延期天数
	var delayDays = fm.configValue.value;
	var damageDay = fm.prpLregistDamageStartDate.value;
	var damage = new Date(damageDay.substring(0, 4), damageDay.substring(5, 7) - 1, damageDay.substring(8, 10));
	var registDay = fm.prpLregistReportDate.value;
	var regist = new Date(registDay.substring(0, 4), registDay.substring(5, 7) - 1, registDay.substring(8, 10));
	var message = "";
	var regist_damage = (regist.getTime() - damage.getTime()) / (24 * 60 * 60 * 1000);
	if (regist_damage >= delayDays) {
		message = message + i18n.commonLiab.regist.reportExtensNumber + delayDays + i18n.commonLiab.regist.day; //提示：报案出险延期天数大於    //天，\n
	}
	var startDate = fm.prpLregistStartDate.value;
	var endDate = fm.prpLregistEndDate.value;
	var damageStartDate = fm.prpLregistDamageStartDate.value;
	if ((damageStartDate < startDate) || (damageStartDate > endDate)) {
		message = message + i18n.regist.through; //出险时间在保险期间以外,是否通过？\n
	}
	if (message.length > 0 && !confirm(message)) {
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
	// 提示是否在保险期限内，是否距离保单起期或止期很近（10天）
	var checkFlag = fm.checkFlag.value;
	var sameCount = parseInt(fm.PerilCount.value);
	var RecentCount = parseInt(fm.RecentCount.value);
	var RegistViewLimitDay = parseInt(fm.RegistViewLimitDay.value);
	var payFee = parseInt(fm.prpLregistPayFee.value);
	var delinquentfeeCase = fm.delinquentfeeCase.value;
	var prpLregistStartDate = fm.prpLregistStartDate.value;
	var prpLregistEndDate = fm.prpLregistEndDate.value;
	var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;

	var startTenDay = new Date(prpLregistStartDate.substring(0, 4), prpLregistStartDate.substring(5, 7) - 1, prpLregistStartDate.substring(8, 10) - 1);
	var endTenDay = new Date(prpLregistEndDate.substring(0, 4), prpLregistEndDate.substring(5, 7) - 1, prpLregistEndDate.substring(8, 10));
	var DamageStartTen = new Date(prpLregistDamageStartDate.substring(0, 4), prpLregistDamageStartDate.substring(5, 7) - 1, prpLregistDamageStartDate.substring(8, 10));
	var StartTen = (DamageStartTen.getTime() - startTenDay.getTime()) / (24 * 60 * 60 * 1000);
	var EndTen = (endTenDay.getTime() - DamageStartTen.getTime()) / (24 * 60 * 60 * 1000);


	if (fm.prpLregistRegistNo.length < 10) {
		fm.messageSave.disabled = true;
		fm.messageView.disabled = true;
	}
	var shareHolderFlag = fm.shareHolderFlag.value;
	var message = "";
	//add by qinyongli 增加保单注销,报案时间，倒签单，股东业务，出险次数，临分共保等提示； 2005-7-28
	var othFlag = fm.prpLregistOthFlag.value;
	if (othFlag.substring(3, 4) == "1") {
		message = message + i18n.regist.orderOff; //此保单已注销！\n
		fm.buttonSave.disabled = true;
		fm.registPrint.disabled = true;
		fm.buttonCancel.disabled = true;
		fm.buttonSaveFinishSubmit.disabled = true;
	}
	var endorType = fm.endorType.value;
	if ("54" == endorType) {
		message = message + i18n.regist.orderStop; //此保单已停效！\n
	}
	var underWriteEndDate = fm.underWriteEndDate.value;
	var prpLregistStartDate = fm.prpLregistStartDate.value;
	if (underWriteEndDate > prpLregistStartDate) {
		message = message + i18n.regist.orderBusiness; //此保单为倒签单业务！\n
	}
	// 提示是否在保险期限内，是否距离保单起期或止期很近（10天）

	if (checkFlag != 0) { //checkFlag = select count(*) from prpPmain where policyNo=**  and underwriteFlag <>1 
		message = message + i18n.regist.orderCondition; //此保单还处在批改状态 ！\n
	}
	if (prpLregistDamageStartDate < prpLregistStartDate) {
		message = message + i18n.regist.withoutInsurance; //出险时间不在保险期间内！\n
	}
	if (prpLregistDamageStartDate > prpLregistEndDate) {
		message = message + i18n.regist.withoutInsurance; //出险时间不在保险期间内！\n
	}
	if (StartTen < 10) {
		message = message + i18n.regist.policyComeEffect + StartTen + i18n.regist.nextDayEffect+"\n"; //保单生效   //天後出险！\n
	}
	if (EndTen < 10) {
		message = message + i18n.regist.accidentTime + EndTen + i18n.regist.day+"\n"; //出险时间离止保日期只有     //天！\n
	}

	//判断是否是相同保单号码有1个以上的报案,只在登记的时候提示.

	var registNo = fm.prpLregistRegistNo.value;
	var sameCount = parseInt(fm.PerilCount.value);
	var policyNo = fm.prpLregistPolicyNo.value;

	//alert (sameCount);
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

	}

	if (payFee == -1) {
		message = message + i18n.regist.useCaution; //此保单保费未缴,请慎重处理！！！ \n
	} else if (payFee == -2) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
		message = message + delinquentfeeCase + "\n";
	}
	if (message.length > 0) {
		alert(message);
	}

	return true;
}

//reason: 统一使用ClaimPub.js 里的 openWinSave()方法，便於维护

/**
 *@description 弹出留言保存页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
/*
function openWinSave(){
   
   var businessNo = fm.prpLregistRegistNo.value;
   var policyNo = fm.prpLregistPolicyNo.value;
   var riskCode = fm.prpLregistRiskCode.value;
   msg=window.open("/claim/messageQueryInfo.do?businessNo=" +businessNo+ "&nodeType=regis&policyNo=" +policyNo+ "&riskCode=" +riskCode,"NewWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=500,Height=300");
}
*/


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


//modify 更改出险时界面信息  
function flashPage() {
	var damageDate = fm.prpLregistDamageStartDate.value;
	var damageHour = fm.prpLregistDamageStartHour.value;
	var policyNo = fm.policyno.value;
	var vURL = '/claim/registBeforeEdit.do?prpCmainPolicyNo=' + policyNo + '&editType=ADD&damageDate=' + damageDate + '&damageHour=' + damageHour + '&modifyDamageDate=true';
	var oldAction = "/claim/registSave.do";
	var oldReportName = fm.prpLregistReportorName.value;
	var oldTarget = fm.target;

	fm.action = vURL;
	fm.target = oldTarget;
	fm.submit();

	fm.action = oldAction;
	fm.target = oldTarget;

	return true;
}


//触发该事件时，自动生成报案摘要

function generateRegistText() {
	if (confirm(i18n.button.confirmNewDangerInfo)) {
		var prplRegistText = "       ";
		var prpLregistReportorName = fm.prpLregistReportorName.value;
		if (prpLregistReportorName.length > 0) {
			//備案人
			prplRegistText = prplRegistText + i18n.db.prpLregist.reportorName + prpLregistReportorName;
		}
		var prpLregistReportDate = fm.prpLregistReportDate.value;
		if (prpLregistReportDate.length > 0) {
			//於   日
			prplRegistText = prplRegistText + i18n.print.yu + prpLregistReportDate + i18n.regist.prpLregist.date;
		}
		var prpLregistReportHour = fm.prpLregistReportHour.value;
		if (prpLregistReportHour.length > 0) {
			prplRegistText = prplRegistText + prpLregistReportHour + i18n.regist.prpLregist.hour; //时
		}
		var prpLregistReportMinute = fm.prpLregistReportMinute.value;
		if (prpLregistReportMinute.length > 0) {
			prplRegistText = prplRegistText + prpLregistReportMinute + i18n.regist.prpLregist.minute; //分
		}
		var reportType = $("select[name='reportType'] option:selected");
		if (reportType.length > 0) {
			prplRegistText = prplRegistText + reportType.text() + ":";
		}
		var prpLregistInsuredName = fm.prpLregistInsuredName.value;
		if (prpLregistInsuredName.length > 0) {
			prplRegistText = prplRegistText + prpLregistInsuredName;
		}
		var prpLregistInsuredName = fm.prpLregistInsuredName.value;
		if (prpLregistInsuredName.length > 0) {
			prplRegistText = prplRegistText + prpLregistInsuredName;
		}
		var prpLregistDamageStartDate = fm.prpLregistDamageStartDate.value;
		if (prpLregistDamageStartDate.length > 0) {
			prplRegistText = prplRegistText + i18n.print.yu + prpLregistDamageStartDate + i18n.regist.prpLregist.date;//於	日
		}
		var prpLregistDamageStartHour = fm.prpLregistDamageStartHour.value;
		if (prpLregistDamageStartHour.length > 0) {
			prplRegistText = prplRegistText + prpLregistDamageStartHour + i18n.regist.prpLregist.hour;//时
		}
		var prpLregistDamageStartMinute = fm.prpLregistDamageStartMinute.value;
		if (prpLregistDamageStartMinute.length > 0) {
			prplRegistText = prplRegistText + prpLregistDamageStartMinute + i18n.regist.prpLregist.minute;//分
		}
		var prpLregistDamageName = fm.prpLregistDamageName.value;
		if (prpLregistDamageName.length > 0) {
			prplRegistText = prplRegistText + i18n.prompt.regist.because + prpLregistDamageName + i18n.prompt.regist.reason;//由於	原因
		}
		var prpLregistDamageAddress = fm.prpLregistDamageAddress.value;
		if (prpLregistDamageAddress.length > 0) {
			prplRegistText = prplRegistText + i18n.print.at + prpLregistDamageAddress + i18n.prompt.regist.locateHappenAccident+",";//在	位置发生事故
		}
		var prpLregistLossName = fm.prpLregistLossName.value;
		if (prpLregistLossName.length > 0) {
			prplRegistText = prplRegistText + i18n.prompt.regist.lossNameIs + prpLregistLossName + ",";//受损标的物为
		}
		var prpLregistEstimateLoss = fm.prpLregistEstimateLoss.value;
		if (prpLregistEstimateLoss.length > 0) {
			prplRegistText = prplRegistText + i18n.print.estimatTotalLoss + prpLregistEstimateLoss + i18n.common.expression.yuan +".";//估计损失  元
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
// 增加被保险人查询 begin

function getCinsured(field) {
	code_CodeSelect(field, 'getCinsured', '-1,0', 'Y', 'N', fm.policyno.value);
	document.getElementById("insuredCode").innerHTML = fm.prpLregistInsuredCode.value;
}
// 增加被保险人查询 end

function countryFlag_change(countryFlag) {
	if (countryFlag == "0") {
		fm.foreignCountryName.style.display = "none";
	} else {
		fm.foreignCountryName.style.display = "";
	}

	fm.portCode.value = "";
	fm.portCName.value = "";
	fm.prpLregistDamageAddress.value = "";
}

function showPort(field) {
	var port = "^(" + fm.portCName.value + ")"
	port = new RegExp(port);
	if (field.value.search(port) != -1 && fm.portCName.value != "") {

	} else {
		field.value = "";
		if (fm.countryFlag.value == "0") {
			field.value = fm.portCName.value;
		} else {
			if (fm.language.value == "E") {
				field.value = fm.portCName.value + " " + fm.foreignCountryName.value;
			} else {
				field.value = fm.foreignCountryName.value + " " + fm.portCName.value;
			}
		}
	}
}

function clearPortCode() {
	fm.portCode.value = "";
	fm.portCName.value = "";
	fm.prpLregistDamageAddress.value = "";
}