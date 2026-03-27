/*****************************************************************************
 * DESC       ：通用的处理 关联 按钮的函数
 * AUTHOR     ：wangli
 * CREATEDATE ：2005-03-25
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 弹出关联页面
 *@param       prpLPolicyNo 保单号码
 *@return      通过返回true,否则返回false
 */
function relate(prpLPolicyNo, registNo) {
	var policyNo = prpLPolicyNo;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
	return false;
}


/**
 *@description 弹出留言保存页面
 *@param        businessNo 报案号
 *@param        policyNo   保单号
 *@param        riskCode   险种代码
 *@param        nodeType   节点类型
 *@param        claimNo    赔案号
 *@return      返回false
 */


function openWinSave(businessNo, policyNo, riskCode, nodeType, claimNo, insuredName) {
	if (undefined == insuredName) {
		insuredName = "";
	}
	msg = window.open("/claim/messageSave.do?businessNo=" + businessNo + "&nodeType=" + nodeType + "&policyNo=" + policyNo + "&riskCode=" + riskCode + "&claimNo=" + claimNo + "&insuredName=" + insuredName, "NewWindow", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=500,Height=600");
	return false;
}

/**
 *@description 弹出留言保存页面
 *@param       receiveParam  接受的参数名称（例如：RegistNo、ClaimNo）
 *@param       nodeName      报案号
 *@return      返回false
 */
function openWinQuery(receiveParam, nodeName) {

	var win;
	var messagedo = "/claim/messageQueryList.do?" + receiveParam + "=" + nodeName;
	//alert("receiveParam = "+receiveParam);
	//alert("nodeName = "+nodeName);
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");

	return false;
}



/**
 *@description 弹出单证页面
 *@param       actionName 处理该窗口的action名称
 *@param       paraName  参数名
 *@param       registNo  赔案号
 *@param       registNo  节点代码
 */
function openCertify(actionName, paraName, registNo, nodeType) {
	var win;
	var riskCode = "";
	if (fm.riskCode != undefined) {
		riskCode = fm.riskCode.value;
	} else if (fm.riskcode != undefined) {
		riskCode = fm.riskcode.value;
	} else if (fm.RiskCode != undefined) {
		riskCode = fm.RiskCode.value;
	} else if (fm.Riskcode != undefined) {
		riskCode = fm.Riskcode.value;
	}
	var messagedo = "/claim/" + actionName + ".do?" + paraName + "=" + registNo + "&editType=EDIT&nodeType=" + nodeType + "&riskCode=" + riskCode;
	//当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮 start
	if (opener == undefined) {
		messagedo = messagedo + "&ifclose=true";
	}
	//当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮 end
	win = window.open(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=700,Height=500");
}

/**
 * @description 弹出公估师评估作业页面
 * @param claimNo 赔案号
 * @param userName 理赔人员
 */
function openAssessor(claimNo) {
	msg = window.open("/claim/pages/common/pub/AssessorBeforeEdit.jsp?claimNo=" + claimNo, "NewWindow", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=800,Height=600");
	return false;
}
/**
 *@description 弹出任务查询页面
 *@param swfLogFlowID 流程号 
 */
function openWinTask(swfLogFlowID) {
	var newWindow = window.open("/claim/workflow/swfFlowBeforeQuery.do?&editType=taskView&swfLogFlowID=" + swfLogFlowID, "NewWindow", "width=800,height=600,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
	return false;
}



//按钮单击事件，用於相同保单号码多报案的显示

function buttonOnClick(actionName, policyNo, curRegistNo) {
	var sameCount = parseInt(fm.PerilCount.value);

	if (sameCount < 1) {
		fm.button_Peril_Open_Context.disabled = true;
		return;
	}

	var messagedo = "/claim/" + actionName + ".do?policyNo=" + policyNo + "&curRegistNo=" + curRegistNo;

	win = window.open(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,top=100,left=100,width=700,Height=500");
}

//按钮单击事件,用於根据被事故者带出受益人信息

function buttonOnClickOfBene(actionName) {
	//modify by lidonghui start 2007-05-13
	var policyno = fm.prpLregistPolicyNo.value;
	var prpLacciPersonAcciCode = fm.prpLacciPersonAcciCode.value;
	var serialNo = parseInt(fm.prpLacciPersonFamilyNo.value);
	var bizType = actionName;
	if (!prpLacciPersonAcciCode) {
		alert("請選擇事故者代碼");
		return;
	}
	if (serialNo < 1) {
		alert("請選擇事故者代碼");
		return;
	}

	var messagedo = "/claim/beneInfoShow.do?serialNo=" + serialNo + "&bizType=" + bizType + "&policyno=" + policyno + "&prpLacciPersonAcciCode=" + prpLacciPersonAcciCode;
	//var messagedo="/claim/beneInfoShow.do?bizType="+bizType+"&policyno="+policyno+"&prpLacciPersonAcciCode="+prpLacciPersonAcciCode;
	//modify by lidonghui end 2007-05-13 ??这里必须需要familyno,即serialno,才知道第几个人的信息，所以lidonghui这样修改有效保额是错误的。
	win = window.open(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,top=100,left=100,width=700,Height=500");
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

//按钮单击事件，用於条款的显示

function buttonOnClick1(fieldObject) {
	var intIndex = parseInt(fieldObject.num);
	var spanId = 'span_Engage_Context';
	if (isNaN(fm.button_Engage_Open_Context.length)) {} else { //多行
		spanId = 'span_Engage_Context' + "[" + intIndex + "]";
	}
	showSubPage2(spanId);
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



/**
 *@description 申请调查
 *@param       actionName 处理该窗口的action名称
 *@param       paraName  参数名
 *@param       registNo  赔案号
 *@param       registNo  节点代码
 */
function applySchedule(registNo, nodeType, swfLogFlowID, swfLogLogNo, nodeStatus, certiType, certiNo) {
	var win;

	//添加判断：certiNo如果为空则取报案号
	if (certiNo == '') {
		certiNo = registNo;
	}
	var messagedo = "/claim/specailCaseQuery.do?registNo=" + registNo + "&editType=ApplySchedule&nodeType=" + nodeType + "&swfLogFlowID=" + swfLogFlowID + "&swfLogLogNo=" + swfLogLogNo + "&nodeStatus=" + nodeStatus + "&certiType=" + certiType + "&certiNo=" + certiNo;
	//alert(messagedo);
	win = window.open(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=600,Height=400");
	// win =  window.open("/claim/"+actionName+".do?"+paraName+"="+registNo+"&editType=EDIT&nodeType="+nodeType+",NewWindow","status=no,resizable=yes,scrollbars=yes,width=700,Height=500");
	win.focus();
}

/**
 *@description 查看历次申请调查
 *@param       paraName  参数名
 *@param       registNo  赔案号
 *@param       registNo  节点代码
 */
function showScheduleHistory(registNo) {
	var messagedo = "/claim/check/lacciCheckBeforeQuery.do?RegistNo=" + registNo + "&editType=SHOW&pageNo=1";
	var win = window.open(messagedo, "processPrpLacciCheck", "status=no,resizable=yes,scrollbars=yes,width=800,Height=400");
	win.focus();
}
/**
 *@description 弹出立案分摊试算结果页面
 *@param       
 *@param       
 *@return      
 */
function startTrailClaim() {
	var win;
	//var claimNo = fm.prpLdangerClaimNo.value;
	var editType = fm.editType.value;
	var riskCode = fm.prpLclaimRiskCode.value;
	// add by liping 20061220 start
	var dangerNoLength = fm.prpLdangerDangerNo.length;
	var prpLdangerDangerNoAry = new Array();
	var prpLsumPay = new Array();
	if (undefined == dangerNoLength) {
		prpLdangerDangerNoAry[0] = fm.prpLdangerDangerNo.value;
		prpLsumPay[0] = fm.prpLclaimSumClaim.value;
	} else {
		for (var i = 0; i < fm.prpLdangerDangerNo.length; i++) {
			var sumLoss = 0.00;
			prpLdangerDangerNoAry[i] = fm.prpLdangerDangerNo[i].value;
			for (var j = 0; j < fm.prpLclaimLossSumClaim.length; j++) {
				var tempLoss = 0.00;
				if (fm.prpLclaimLossDangerNo[j].value == prpLdangerDangerNoAry[i]) {
					tempLoss = parseFloat(fm.prpLclaimLossSumClaim[j].value);
					if (isNaN(tempLoss)) {
						tempLoss = 0;
					}
					sumLoss += tempLoss;
				}
			}
			prpLsumPay[i] = sumLoss;
		}
	}
	//var prpLdangerDangerNo=fm.prpLdangerDangerNo.value;
	var prpLdangerPolicyNo = fm.prpLdangerPolicyNo.value;
	var prpLclaimDamageStartDate = fm.prpLclaimDamageStartDate.value;
	if (fm.prpLclaimDutySumClaim != null) {
		var prpLclaimSumClaim = fm.prpLclaimDutySumClaim.value;
	} else {
		var prpLclaimSumClaim = fm.prpLclaimSumClaim.value;
	}
	if (fm.EstiCurrency != null) {
		var prpLclaimCurrency = fm.EstiCurrency.value;
	} else {
		var prpLclaimCurrency = CURRENCYINFO.LOCAL_CURRENCY;
	}
	// add by liping 20061220 end
	//判断估损金额是否已经发生更改
	var theUrl = "/claim/reinsTrialResult.do?editType=" + editType + "&riskCode=" + riskCode + "&prpLdangerPolicyNo=" + prpLdangerPolicyNo + "&prpLdangerDangerNo=" + prpLdangerDangerNoAry + "&prpLclaimDamageStartDate=" + prpLclaimDamageStartDate + "&prpLclaimCurrency=" + prpLclaimCurrency + "&prpLclaimSumClaim=" + prpLsumPay;
	win = window.open(theUrl, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}
/**
 *@description 弹出理算分摊试算结果页面
 *@param       
 *@param       
 *@return      
 */
function startTrailCompensate() {

	var win;
	//判断实赔金额是否已经发生更改
	var claimNo = fm.prpLcompensateClaimNo.value;
	var editType = fm.editType.value;
	//var riskCode = fm.prpLclaimRiskCode.value;
	var compensateNo = fm.prpLcompensateCompensateNo.value;
	// add by liping 20061221 start
	var dangerNoLength = fm.prpLdangerDangerNo.length;
	var prpLdangerDangerNoAry = new Array();
	var prpLsumPay = new Array();
	var prpLsumPayCharge = new Array();
	var prpLsumPayLoss = new Array();
	var prpLsumPayPerson = new Array();
	if (undefined == dangerNoLength) {
		prpLdangerDangerNoAry[0] = fm.prpLdangerDangerNo.value;
		prpLsumPay[0] = fm.prpLcompensateSumPaid.value;
	} else {
		for (var i = 0; i < fm.prpLdangerDangerNo.length; i++) {
			prpLdangerDangerNoAry[i] = fm.prpLdangerDangerNo[i].value;
			prpLsumPay[i] = 0;
			var sumPay = 0.00;
			var temPay = 0.00;
			for (var j = 0; j < fm.prpLchargeSumRealPay.length; j++) {
				if (fm.prpLchargeDangerNo[j].value == prpLdangerDangerNoAry[i]) {
					temPay = parseFloat(fm.prpLchargeSumRealPay[j].value);
					if (isNaN(temPay)) {
						temPay = 0;
					}
					sumPay += temPay;
				}
			}
			if (undefined != fm.prpLlossDtoSumRealPay) {
				for (var j = 0; j < fm.prpLlossDtoSumRealPay.length; j++) {
					if (fm.prpLlossDtoDangerNo[j].value == prpLdangerDangerNoAry[i]) {
						temPay = parseFloat(fm.prpLlossDtoSumRealPay[j].value);
						if (isNaN(temPay)) {
							temPay = 0;
						}
						sumPay += temPay;
					}
				}
			}
			if (undefined != fm.prpLpersonLossSumRealPay) {
				for (var j = 0; j < fm.prpLpersonLossSumRealPay.length; j++) {
					if (fm.prpLpersonLossDangerNo[j].value == prpLdangerDangerNoAry[i]) {
						temPay = parseFloat(fm.prpLpersonLossSumRealPay[j].value);
						if (isNaN(temPay)) {
							temPay = 0;
						}
						sumPay += temPay;
					}
				}
			}
			if (isNaN(prpLsumPay[i])) {
				prpLsumPay[i] = 0;
			}
			prpLsumPay[i] = sumPay;
		}
	}
	var prpLdangerPolicyNo = fm.prpLdangerPolicyNo.value;
	var prpLdamageStartDate = fm.damageStartDate.value;
	if (fm.MergeCurrency != null && fm.MergeCurrency.value != "" && fm.MergeCurrency.value != null) {
		var prpLcompensateCurrency = fm.MergeCurrency.value;
	} else {
		var prpLcompensateCurrency = CURRENCYINFO.LOCAL_CURRENCY
	}
	var prpLcompensateSumPaid = fm.prpLcompensateSumPaid.value;
	// add by liping 20061220 end

	var theUrl = "/claim/reinsTrialResult.do?claimNo=" + claimNo + "&compensateNo=" + compensateNo + "&prpLdangerDangerNo=" + prpLdangerDangerNoAry + "&prpLdangerPolicyNo=" + prpLdangerPolicyNo + "&prpLdamageStartDate=" + prpLdamageStartDate + "&prpLcompensateCurrency=" + prpLcompensateCurrency + "&prpLcompensateSumPaid=" + prpLsumPay;
	win = window.open(theUrl, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}
/**
 *暂存任务放弃
 */
function giveupTemporarySave(nodeType) {

	if (!confirm("確認要放棄暫存任務？")) {
		return false;
	}
	if (nodeType == "check") {
		fm.action = "/claim/check/checkBeforeEdit.do?editType=giveupTemporarySave";
	}
	if (nodeType == "certa") {
		//alert("nodeType22="+nodeType)
		fm.action = "/claim/certainLoss/certainLossBeforeEdit.do?editType=giveupTemporarySave";
	}
	fm.submit();

}



/**
 *未处理任务的放弃函数
 */
function taskGiveup() {
	if (!confirm(i18n.giveupTask.confirmGiveup + "？")) {
		return false;
	}
	var editType = $(":input[name='editType']");
	var url = "/claim/giveupTask.do?editType=GIVUP";
	if(editType.val()!=undefined){
		editType.val("GIVUP");
		url = "/claim/giveupTask.do";
	}
	fm.action = url;
	fm.submit();

}

/**
 *查勘未处理任务的放弃函数
 */
function taskCheckGiveup() {
	if (!confirm(i18n.giveupTask.confirmGiveup + "？")) {
		return false;
	}
	var editType = $(":input[name='editType']");
	var url = "/claim/check/checkBeforeEdit.do?editType=GIVUP";
	if(editType.val()!=undefined){
		editType.val("GIVUP");
		url = "/claim/check/checkBeforeEdit.do";
	}
	fm.action = url;
	fm.submit();

}
/**
 *立案未处理任务的放弃函数
 */
function taskClaimGiveup() {
	if (!confirm(i18n.giveupTask.confirmGiveup + "？")) {
		return false;
	}
	var editType = $(":input[name='editType']");
	var url = "/claim/claimBeforeEdit.do?editType=GIVUP";
	if(editType.val()!=undefined){
		editType.val("GIVUP");
		url = "/claim/claimBeforeEdit.do";
	}
	fm.action = url;
	fm.submit();

}
/**
 *单证未处理任务的放弃函数
 */
function taskCertifyGiveup() {
	if (!confirm(i18n.giveupTask.confirmGiveup + "？")) {
		return false;
	}
	var editType = $(":input[name='editType']");
	var url = "/claim/certify/certifyBeforeEdit.do?editType=GIVUP";
	if(editType.val()!=undefined){
		editType.val("GIVUP");
		url = "/claim/certify/certifyBeforeEdit.do";
	}
	fm.action = url;
	fm.submit();
}

/**
 *预赔未处理任务的放弃函数
 *add by caozhigang 20090530
 */
function taskPrepayGiveup(prpLprepayClaimNo) {
	if (!confirm(i18n.giveupTask.confirmGiveup + "？")) {
		return false;
	}
	fm.action = "/claim/specailCase/prepayBeforeEdit.do?editType=GIVUP&ClaimNo=" + prpLprepayClaimNo;
	fm.submit();

}


function backWardPolicy(coreURL, registPolicyNo, registRiskCode, registDamageStartDate, comCode) {
	var SHOWTYPE = "SHOW";
	//var BizNo     =fm.prpLregistPolicyNo.value;
	//var RiskCode  =fm.prpLregistRiskCode.value;
	//var damageDate=fm.prpLregistDamageStartDate.value;
	//var vURL = '/prpall/' + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo='+ BizNo+'&RiskCode='+ RiskCode+'&damageDate='+ damageDate;

	//修改查看业务数据的接口方式

	//var vURL = coreURL + registRiskCode + '/tbcbpg/UIPrPoEn' + registRiskCode + 'Show.jsp?myComCode='+comCode+'&BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo='+ registPolicyNo+'&RiskCode='+ registRiskCode+'&damageDate='+ registDamageStartDate;
	var vURL = '/claim/pages/common/pub/PolicyShowCenter.jsp?myComCode=' + comCode + '&BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + registPolicyNo + '&RiskCode=' + registRiskCode + '&damageDate=' + registDamageStartDate + '&coreURL=' + coreURL;
	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//显示流程图信息(按流程号)

function showWorkFlowerByFlowID(strWorkFlowId) {
	var vURL = '/claim/workflow/swfFlowBeforeQuery.do?swfLogFlowID=' + strWorkFlowId + '&ifclose=true';
	window.open(vURL, '流程信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

//显示流程图信息(按报案号)

function showWorkFlowerByRegistNo(strRegistNo) {
	var vURL = '/claim/workflow/swfFlowBeforeQuery.do?registNo=' + strRegistNo + '&ifclose=true';
	window.open(vURL, '流程信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

//显示流程图信息(按报案号)

function showWorkFlowerByClaimNo(strClaimNo) {
	var vURL = '/claim/workflow/swfFlowBeforeQuery.do?claimNo=' + strClaimNo + '&ifclose=true';
	window.open(vURL, '流程信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

//按钮单击事件，显示帮助

function showHelp(spanID, leftMove) {
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

function hideHelp(spanID) {
	var span = eval(spanID);
	span.style.display = 'none';
}

//计算明国年的时间
function setRcDateTime(dp){
	var rcFormat = dp.cal.dateFmt;
	if(rcFormat!=null){
		if(rcFormat.lastIndexOf("y")==2){
			rcFormat = "y"+rcFormat;
		}else if(rcFormat.lastIndexOf("y")==1){
			rcFormat = "yy"+rcFormat;
		}else if(rcFormat.lastIndexOf("y")==0){
			rcFormat = "yyy"+rcFormat;
		}
	}
	if(rcFormat==null||rcFormat==""){
		rcFormat = "yyyy-MM-dd";
	}
	var name = this.name;
	name = name.replace("_show_format_rcDate", "");
	var field_rcs = document.getElementsByName(this.name);
	var fields = document.getElementsByName(name);
	for(var i=0;i<field_rcs.length;i++){
		if(field_rcs[i]==this){
			fields[i].value = dp.cal.getNewDateStr(rcFormat);
			break;
		}
	}
}
//没有日期格式的显示
//转换成明国年时间
function showRcDateTime(rcField,format){
	var rcFormat = format;
	if(rcFormat==null||rcFormat==""||rcFormat==undefined){
		rcFormat = "yyyy-MM-dd";
	}
	var name = rcField.name;
	name = name.replace("_show_format_rcDate", "");
	var strDate = rcField.realValue;
	var str = rcField.value;
	if(str=="" || strDate==null||strDate==undefined){
		strDate = "";
	}
	if(strDate!=""){
		strDate = formatDate(strDate,rcFormat);
	}
	var field_rcs = document.getElementsByName(rcField.name);
	var fields = document.getElementsByName(name);
	for(var i=0;i<field_rcs.length;i++){
		if(field_rcs[i]==rcField){
			fields[i].value = strDate;
			break;
		}
	}
}
function initDamageDate(){
	var inputName="";
	var $damageStartDate ="";
	var nodeType=$(":input[name='nodeType']").val();
	if(nodeType=="claim"){
		inputName="prpLclaimDamageStartDate";
	}else if(nodeType=="check"){
		inputName="prpLcheckDamageStartDate";
	}else if(nodeType=="compe"||nodeType=="compp"){
		inputName="prpLcompensateDamageStartDate";
	}
	$damageStartDate= $("input[name='"+inputName+"']");
	var beforeDamageDate = $damageStartDate.val();
	$damageStartDate.data("beforeDamageDate",beforeDamageDate);
	$damageStartDate.bind("propertychange",function(){
		var beforeDamageDate = $damageStartDate.data("beforeDamageDate");
		if(!(this.value == "" || beforeDamageDate == this.value)){
			setDamageDate(beforeDamageDate,this,$(":input[name='policyno']").val(),$(":input[name='registno']").val());
		}
	});
};
function setDamageDate(beforeDamageDate,damageStartDate,policyNo,registNo){
	var afterDamageDate=damageStartDate.value;
	var inputName=damageStartDate.name;
	$.ajax({
		type:"POST",
		url:"${ctx}/checkDamageDate.do",
		dataType: "text",
		data:"beforeDamageDate="+beforeDamageDate+"&afterDamageDate="+afterDamageDate+"&policyNo="+policyNo+"&registNo="+registNo,
		success:function(data){
			if (data=="true"){
				alert("此保单有批單，出險日期不能修改");
				var beforeDamageDateMG=formatDate(beforeDamageDate, "yyy-MM-dd");
				$("input[name='"+inputName+"']").val(beforeDamageDate);
				$("input[name^='"+inputName+"']:first").attr("realValue",beforeDamageDate);
				$("input[name^='"+inputName+"']:first").val(beforeDamageDateMG);
			}else{
				$(damageStartDate).data("beforeDamageDate",afterDamageDate);
			}
		}
	})
}
/**
 * 根据国家、省份、城市生成出现地址头 
 * @param field 需要添加的域
 * @param countryCName 外国域名称
 * @param addressName 国内省份名称
 * @return
 */
function showProvinceCity(field,countryCName,addressName) {
	var selectValue = $(field).attr("selectValue");
	if(selectValue==undefined||selectValue==null){
		selectValue = "";
	}
	var value = field.value;
	var countryFlag = $(":input[name='countryFlag']").val();
	var countryName = "";
	if(countryFlag == "0"){
		countryName = $(":input[name='"+addressName+"']").val();
	}else{
		countryName = $(":input[name='"+countryCName+"']").val();
	}
	if(countryName==undefined||countryName==null){
		countryName = "";
	}
	if(countryName!=""&&value.indexOf(countryName)<0){
		if(value.indexOf(selectValue)>-1){
			field.value = value.replace(selectValue,countryName);
		}else{
			field.value = countryName+value;
		}
		var r = field.createTextRange();
		r.collapse(false);
		r.select();
		$(field).attr("selectValue",countryName);
	}
}