/*****************************************************************************
 * DESC       ：查勘登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-06-03
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 检查查勘登记
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
	//add by luochang at 2010-10-08 提交时对送审进行判断
	if (saveType == "4" && checkUndwrt() == false) {
		return false;
	}

	var errorMessage = "";
	var prpLcheckDamageStartDate = fm.prpLcheckDamageStartDate.value;
	prpLcheckDamageStartDate = prpLcheckDamageStartDate.substring(0, 10);
	var prpLcheckCheckDate = fm.prpLcheckCheckDate.value;
	if (prpLcheckCheckDate >= prpLcheckDamageStartDate) {} else {
		alert(i18n.check.surveyDateAccident); //查勘日期不能在出险日期之前！！
		return false;
	}
	fm.buttonSaveType.value = saveType;
	//设值复选框的值	
	var context = fm.prpLregistTextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.check.surveyReports; //查勘报告不允许为空;\n
	}
	var Checker1 = fm.prpLcheckChecker1.value;
	var Checker2 = fm.prpLcheckChecker2.value;
	if (Checker1 == Checker2) {
		errorMessage = errorMessage + i18n.check.cannotSamePerson; //查勘人 1和查勘人 2不能为同一人;\n
	}
	//行业类别判断
	var $prpLpersonTraceJobCode2 = $(":input[name='prpLpersonTraceJobCode2']");
	var $prpLpersonTraceJobName2 = $(":input[name='prpLpersonTraceJobName2']");
	var $prpLpersonTraceJobCode = $(":input[name='prpLpersonTraceJobCode']");
	var $prpLpersonTraceJobName = $(":input[name='prpLpersonTraceJobName']");
	$(":input[name='prpLpersonTraceJobCode1']").each(function(i,n){
		if($prpLpersonTraceJobCode2[i].value==""&&n.value!=""){
			errorMessage = errorMessage + "第"+i+"條從事行業不能為空！\n";
		}else{
			$prpLpersonTraceJobName[i].value = $prpLpersonTraceJobName2[i].value;
			$prpLpersonTraceJobCode[i].value = $prpLpersonTraceJobCode2[i].value;
		}
	})

	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm, "ClaimLoss_Data")) {
		return false;
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;

	fm.submit();
}

/**
 *@description 弹出查看留言页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?registNo=" + fm.prpLcheckRegistNo.value;
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}


/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relate() {
	var policyNo = fm.prpLcheckPolicyNo.value;
	var registNo = fm.prpLcheckRegistNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");

}
//Modify By sunhao add end 2004-09-06


/**
 *@description 设置画面的初始值
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {
	//判断是否是共保单
	var coinsFlag = fm.coinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var message = "";
	if (coinsFlag != 0) {
		message = message + i18n.claim.totalPolicy; //本保单为联/共保单！\n
	}
	if (tempReinsFlag != 0) {
		message = message + i18n.check.proBusiness; //此保单有临分业务！
	}
	if (message.length > 0) {
		alert(message);
	}
	return true;
}

/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet1() {
	return true;
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

/**
 *@description 处理索赔资料清单
 *@param       businessNo
 *@add by qinyongli 2005-8
 */

function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}

//add by luochang 2010-06-22 根据出现地址所属国内或国外判断双击域是否显示 begin

function countryFlag_change(countryFlag) {
	if (countryFlag != "1") {
		fm.countryCName.style.display = "none";
		fm.provinceName.style.display = "";
		fm.prpLcheckAddressCode.style.display = "";
		fm.prpLcheckAddressName.style.display = "";
	} else {
		fm.countryCName.style.display = "";
		fm.provinceName.style.display = "none";
		fm.prpLcheckAddressCode.style.display = "none";
		fm.prpLcheckAddressName.style.display = "none";
	}
}

//add by luochang 2010-12-10 代查勘委托书 begin

function heresyCheck() {
	if (fm.unitType.value == "0") {
		if (fm.prpLcheckHandleUnitCode.value != "" && fm.prpLcheckHandleUnitName.value != "") {
			window.open("claim/JRPropPropGeneralClaim.do?registNo=" + fm.registno.value, "NewWindow", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=750,Height=800");
		} else {
			alert(i18n.check.operationAgain); //请选择代查勘处理机构，再进行委托书操作！
			return false;
		}
	} else {
		alert(i18n.check.surveyProcess); //只有当【查勘处理单位】为“系统外”时才可以进行委托书操作！
		return false;
	}
}
//add by luochang 2010-12-10 代查勘委托书 begin
function changeLDamageAddress() {
	var prpLcheckDamageAddress = fm.prpLcheckDamageAddress.value; //出险地点
	if (trim(fm.prpLcheckCheckSite.value).length == 0) { //查勘地點
		fm.prpLcheckCheckSite.value = prpLcheckDamageAddress;
		fm.prpLcheckCheckSite.fireEvent('onchange');
	}
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
/**
 * 估损合计
 * @return
 */
function collectClaimLoss() {
	var estimateFee = 0;//费用
	var estimateLoss = 0;//赔款
	var prpLclaimLossLossFeeType = $("#ClaimLoss").find(":input[name='prpLclaimLossLossFeeType']");
	$("#ClaimLoss").find(":input[name='prpLclaimLossSumClaim']").each(function(i,n){
		if(prpLclaimLossLossFeeType.get(i).value=="Z"){
			estimateFee += parseFloat(n.value);
		}else{
			estimateLoss += parseFloat(n.value);
		}
	});
	$(":input[name='prpLcheckEstimateLoss']").val(point(round(estimateLoss, 0), 0));
	$(":input[name='prpLcheckEstimateFee']").val(point(round(estimateFee, 0), 0));
	return true;
}