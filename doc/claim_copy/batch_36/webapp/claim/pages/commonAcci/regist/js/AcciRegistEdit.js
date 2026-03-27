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
	var message = "";
	if (fm.modifyFlag != null) {
		if (fm.modifyFlag.value = "PERFECT" && fm.alterName.value.length < 1) {
			errorMessage = errorMessage + i18n.commonAcci.endcase.reportModifierInput; //报案修改人必须輸入！\n
		}
	}
	//事故原因不允许为空 begin
	var damageName = fm.prpLregistDamageName.value;
	if (damageName.length < 1) {
		errorMessage = errorMessage + i18n.commonAcci.endcase.causeAccidentNotEmpty; //事故原因不允许为空\n
	}
	// 被保险人不准许为空 begin
	var insuredName = fm.prpLregistInsuredName.value;
	if (insuredName.length < 1) {
		errorMessage = errorMessage + i18n.commonAcci.regist.insurNotAllowNull; //被保险人不允许为空\n
	}
	//被保险人不准许为空 end
	// 控制报案环节当选择自定义事故者时，被保险人必须选择团单免导 begin
	var prpLacciPersonAcciCode = document.getElementsByName("prpLacciPersonAcciCode");
	var termFlag = document.getElementsByName("termFlag");
	if (prpLacciPersonAcciCode.length > 0 && prpLacciPersonAcciCode[0].value == "9999" && termFlag.length > 0 && termFlag[0].checked == false) {
		errorMessage = errorMessage + i18n.commonAcci.regist.customAccidentChooseGroup; //自定义事故者时必须选择免导团单！\n
	}
	// 控制报案环节当选择自定义事故者时，被保险人必须选择团单免导 end
	var insuredCode = fm.prpLregistInsuredCode.value;
	var linkerName = fm.prpLregistLinkerName.value;
	if (linkerName.length < 1) {
		errorMessage = errorMessage + i18n.commonAcci.regist.contactNotAllowdEmpty; //联系人不允许为空\n
	}
	var prpLregistDamageAddress = fm.prpLregistDamageAddress.value;
	if (prpLregistDamageAddress.length < 1) {
		errorMessage = errorMessage + i18n.commonAcci.regist.contactLocationNotAllowdEmpty; //事故地点不允许为空\n
	}
	var context = fm.prpLregistTextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.claim.dangerNotAllowEmpty; //出险摘要不允许为空\n
	}
	//add 20051207 start reason:事故类型：必录项 不带入默认项（需求）
	var damageTypeCode = fm.prpLregistDamageTypeCode.value;
	if (damageTypeCode.length < 1) {
		errorMessage = errorMessage + i18n.commonAcci.check.accidentTypeCodeNoEmpty; //事故类型代码不允许为空\n
	}
	var damageTypeName = fm.prpLregistDamageTypeName.value;
	if (damageTypeName.length < 1) {
		errorMessage = errorMessage + i18n.commonAcci.check.accidentTypeNameNoEmpty; //事故类型名称不允许为空\n
	}
	//事故类型：必录项 不带入默认项（需求）
	if (fm.acceptFlag[0].checked == false && fm.acceptFlag[1].checked == false) {
		errorMessage = errorMessage + i18n.commonAcci.regist.acceptMarksNoEmpty; //受理标志不允许为空\n
	}
	if (fm.repeatInsureFlag[0].checked == false && fm.repeatInsureFlag[1].checked == false) {
		errorMessage = errorMessage + i18n.commonAcci.regist.whetherOtherCompanyNoEmpty; //是否向别的保险公司投保不允许为空\n
	}
	if (fm.prpLregistReceiverName.value == "") {
		alert(i18n.commonAcci.regist.receiveCaseNotAllowNull) //接案人姓名不能为空！
		return false;
	}

//	if (fm.prpLacciPersonAcciCode.value == "") {
//		alert(i18n.commonAcci.regist.pleaseInputInfo) //请输入事故者信息
//		return false;
//	}
	//获取报案出险延期天数
	var delayDays = fm.configValue.value;
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
	
	if (damageStartDate < startDate || damageStartDate > endDate) {
		if (!confirm(i18n.regist.through)) {
			return false;
		}
	}
	var inputDate = new Date(prpLregistInputDate.replace(/-/g,"/"));//出單日期
	var regist_damage = Math.round((reportDate.getTime() - damageStartDate.getTime()) / (24 * 60 * 60 * 1000));
	if (regist_damage >= delayDays) {
		message = message + i18n.commonAcci.regist.reportExtenseNo + delayDays + i18n.commonAcci.regist.dayWhetherPass; //报案出险延期天数大於     天，是否通过？\n
		if (!confirm(message)) {
			return false;
		}
	}
	if (prpLregistInputDate < prpLregistDamageStartDate) {
		errorMessage = errorMessage + i18n.regist.dateCannotGreaterDanger+"\n"; //出险日期不能大於输单日期\n
	}
	if (reportDate < damageStartDate) {
		errorMessage = errorMessage + i18n.commonAcci.regist.getOutDangerNotDate; //出险日期不能大於报案日期\n
	}
	var othFlag = fm.prpLregistOthFlag.value;
	if(othFlag.length>2 && othFlag.substring(2,3)== "1"){
		errorMessage = errorMessage +"保單已退保，不允許備案!"+ "\n";
	}

	// 如果輸入了从事行业，一级、二级和三级都要輸入 start
	for (i = 1; i < fm.prpLpersonTracePersonNo.length; i++) {
		if (fm.prpLpersonTraceJobCode1[i].value != "") {
			if (fm.prpLpersonTraceJobCode2[i].value == "") {
				alert(i18n.certainLoss.pleaseSelectIndustry2); //请选择二级行业！
				fm.prpLpersonTraceJobName2[i].focus();
				return false;
			}
			if (fm.prpLpersonTraceJobCode[i].value == "") {
				alert(i18n.certainLoss.pleaseSelectIndustry3); //请选择三级行业！
				fm.prpLpersonTraceJobName[i].focus();
				return false;
			}
		}
		if (fm.prpLpersonTraceJobCode2[i].value != "") {
			if (fm.prpLpersonTraceJobCode[i].value == "") {
				alert(i18n.certainLoss.pleaseSelectIndustry3); //请选择三级行业！
				fm.prpLpersonTraceJobName[i].focus();
				return false;
			}
		}
	}
	//mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 start
	if (saveType =="4") {
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
	} //mantis： CLM0105，處理人員：BL061 張明財，需求單編號：CLM0105 新核心-手機正規化 end
	//如果輸入了从事行业，一级、二级和三级都要輸入 end
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
	var payFee = parseInt(fm.prpLregistPayFee.value);

	//增加保单注销，倒签单，股东业务等提示； 
	var message = "";
	var shareHolderFlag = fm.shareHolderFlag.value;
	var othFlag = fm.prpLregistOthFlag.value;
	if (othFlag.substring(3, 4) == "1") {
		message = message + i18n.claim.policyCancelled; //此保单已注销！\n
		fm.buttonSave.disabled = true;
		//fm.registPrint.disabled = true;
		fm.buttonCancel.disabled = true;
		fm.buttonSaveFinishSubmit.disabled = true;
	}
	var policyCancelFlag = $(":input[name='policyCancelFlag']").val();
	if (policyCancelFlag=="1"){
		message = message + i18n.regist.policyNotAllowReport+"\n";// 保单已退保，不允许报案！
		fm.buttonSave.disabled = true;
		fm.buttonCancel.disabled = true;
		fm.buttonSaveFinishSubmit.disabled = true;
	}
	var endorType = fm.endorType.value;
	if ("54" == endorType) {
		message = message + i18n.commonAcci.regist.policyStopWork; //此保单已停效！\n
	}
	var underWriteEndDate = fm.underWriteEndDate.value;
	var prpLregistStartDate = fm.prpLregistStartDate.value;
	if (compareDate(underWriteEndDate,prpLregistStartDate)==1) {
		message = message + i18n.commonAcci.regist.policySingleBusiness; //此保单为倒签单业务！\n
	}
	if (payFee == -1) {
		message = message + i18n.certainLoss.policyPremiumNoPay; //此保单保费未缴,请慎重处理！！！ \n
	} else if (payFee == 0) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
	}
	
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
	}
	if (message.length > 0) {
		alert(message);
	}

	//判断是否是相同保单号码有1个以上的报案,只在登记的时候提示.

	var registNo = fm.prpLregistRegistNo.value;
	var sameCount = parseInt(fm.PerilCount.value);
	var policyNo = fm.prpLregistPolicyNo.value;

	if (registNo.length < 1) {
		//说明是登记
		if (sameCount > 0) {
			alert(i18n.regist.policyNumber + policyNo + i18n.commonAcci.regist.alreadyDanger + sameCount + i18n.commonAcci.regist.timesCheckInformation); //保单号码为   已经出险    次，请查看出险次数信息！
		} else {
			fm.button_Peril_Open_Context.disabled = true;
		}

	}
	if ( Math.round(StartTen) > 30 ) {
		return false;
	}
	if ("9999" == fm.prpLacciPersonAcciCode.value) {
		fm.prpLacciPersonAcciName.className = "input";
		fm.clickCount.value = "0";
	}
	return true;
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
	alert(i18n.commonAcci.regist.firstCarMustVehicle); //涉案车辆的第一辆车必须为保单车辆
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

//意健险在报案时可以选择是否呈报，是：1 否：0

function changePrplregistReportFlag() {
	if (fm.prplregistReportFlag[0].checked) {
		ReportFlag.style.display = "";
	} else {
		ReportFlag.style.display = "none";
	}
}
//modify add by huangyunzhong 20051025 调整为点击被保险人代码後弹出新网页，原页面保留

function showPersonInsured(prpDcustomerIdvCustomerCode) {
	var prpCmainPolicyNo = fm.prpCmainPolicyNo.value
	var messagedo = "/claim/processPrpDcustomerIdv.do?actionType=prepareUpdate&prpCmainPolicyNo=" + prpCmainPolicyNo + "&prpDcustomerIdvCustomerCode=" + prpDcustomerIdvCustomerCode + "& target='_blank'";
	win = window.open(messagedo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
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
				alert(i18n.commonAcci.regist.customAccidentChooseGroup); //自定义事故者时必须选择免导团单！
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

function changeLxr() {
	var prpLregistReportorName = fm.prpLregistReportorName.value;

	if (trim(fm.prpLregistLinkerName.value).length == 0) {
		fm.prpLregistLinkerName.value = prpLregistReportorName;
	}
}

function getCinsured(field) {
	var damageDate = fm.prpLregistDamageStartDate.value;
	var damageHour = fm.prpLregistDamageStartHour.value;
	var policyNo  = fm.policyno.value;
	if($.trim(damageDate).length == 0 || $.trim(damageHour).length == 0){
		alert("請錄入出險時間！");
		return;
	}
	code_CodeSelect(field, 'getCinsured', '-1,0,1', 'Y', 'N', fm.policyno.value + "|" + damageDate + "|" + damageHour);
	document.getElementById("insuredCode").innerHTML = fm.prpLregistInsuredCode.value;
	var identifyNumber = $(":input[name='identifyNumber']").val();
	var insuredName = $(":input[name='prpLregistInsuredName']").val();
	$(":input[name='prpCinsuredIdentifyNumber']").val(identifyNumber);
	$(":input[name='prpLacciPersonAcciName']").val(insuredName);
	$(":input[name='prpLacciPersonIdentifyNumber']").val(identifyNumber);
}
//add by zhangyurui 2009-02-20 增加被保险人查询 end

//add by luochang 2010-06-22 根据出现地址所属国内或国外判断双击域是否显示 begin

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