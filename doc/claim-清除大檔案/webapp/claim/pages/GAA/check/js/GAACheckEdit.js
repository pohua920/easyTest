/*****************************************************************************
 * DESC       ：查勘登记的脚本函数页面
 * AUTHOR     ：中科軟
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
	//提交时对送审进行判断
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
	var motionFlag = document.getElementsByName("motionFlag");
	for(var i=1;i<motionFlag.length;i++){
		if(motionFlag[i].value == '1'){
			 if(fm.prpLpersonTraceHospital[i].value==null || fm.prpLpersonTraceHospital[i].value==''){
				errorMessage = errorMessage + i18n.db.prpLcheckLoss.serialNo + i + i18n.info.personTrack + i18n.certainLoss.hospitals + i18n.js.cannotEmpty +"\n"; //序号i的 人伤信息就医医院不允许为空\n
			}
		}
	}
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	for(i=1;i<fm.prpLpersonTraceJobName2.length;i++){
		fm.prpLpersonTraceJobCode[i].value = fm.prpLpersonTraceJobCode2[i].value;
		fm.prpLpersonTraceJobName[i].value = fm.prpLpersonTraceJobName2[i].value;
	}
	for (i = 1; i < fm.prpLpersonTracePersonNo.length; i++) {
		if (fm.prpLpersonTraceJobCode1[i].value != "") {
			if (fm.prpLpersonTraceJobCode2[i].value == "") {
				alert(i18n.certainLoss.pleaseSelectIndustry2); //请选择二级行业！
				return false;
			}
		}
	}
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm)) {
		return false;
	}
	//代查勘提示功能
	var checkGuideMessages = $(":input[name='checkGuideMessages']").val();
	if(checkGuideMessages.length>0&&!confirm(checkGuideMessages)){
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

//按钮单击事件，用於相同保单号码多报案的显示

/*function buttonOnClick(strSubPageCode) {
	var sameCount = parseInt(fm.PerilCount.value);

	if (sameCount < 1) {
		fm.button_Peril_Open_Context.disabled = true;
		return;
	}
	showSubPage1(strSubPageCode);

}*/

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
 */

function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}

//根据出现地址所属国内或国外判断双击域是否显示 begin
function countryFlag_change(countryFlag) {
	if (countryFlag != "1") {
		fm.countryCName.style.display = "none";
		fm.provinceName.style.display = "none";
		fm.prpLcheckAddressCode.style.display = "";
		fm.prpLcheckAddressName.style.display = "";
	} else {
		fm.countryCName.style.display = "";
		fm.provinceName.style.display = "none";
		fm.prpLcheckAddressCode.style.display = "none";
		fm.prpLcheckAddressName.style.display = "none";
	}
}

//代查勘委托书 begin

function heresyCheck() {
	if (fm.unitType.value == "0") {
		if (fm.prpLcheckHandleUnitCode.value != "" && fm.prpLcheckHandleUnitName.value != "") {
			var $claimNo = $(":input[name=prpLcheckClaimNo]");
			if($claimNo.val()!=""){
				window.open(contextRootPath+"/JRGAAGAACommissioned.do?claimNo=" + $claimNo.val() + "&HandleUnitName=" + fm.prpLcheckHandleUnitName.value, "NewWindow", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=750,Height=800");
			}else{
				alert("立案成功后才能列印委托書！"); //请选择代查勘处理机构，再进行委托书操作！
				return false;
			}
		} else {
			alert(i18n.check.operationAgain); //请选择代查勘处理机构，再进行委托书操作！
			return false;
		}
	} else {
		alert(i18n.check.surveyProcess); //只有当【查勘处理单位】为“系统外”时才可以进行委托书操作！
		return false;
	}
}
//代查勘委托书 begin
function changeLDamageAddress() {
	var prpLcheckDamageAddress = fm.prpLcheckDamageAddress.value; //出险地点
	if (trim(fm.prpLcheckCheckSite.value).length == 0) { //查勘地點
		fm.prpLcheckCheckSite.value = prpLcheckDamageAddress;
		fm.prpLcheckCheckSite.fireEvent('onchange');
	}
}
$(document).ready(function(){
	initDamageDate();
});