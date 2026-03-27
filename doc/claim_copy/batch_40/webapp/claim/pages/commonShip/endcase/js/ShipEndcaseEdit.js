/*****************************************************************************
 * DESC       ：结案登记的脚本函数页面
 * AUTHOR     ：liubvo
 * CREATEDATE ： 2004-06-28
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 检查结案登记
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
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet() {
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
 *@description 根据按钮状态保存结案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */
function saveForm(field, saveType) {
	if (!confirm("结案後不能再修改。\n确定结案吗？"))
		return false;

	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	//fm.action = "/claim/endcaseSave.do?step=step1";
	//textarea文本框设置值
	/*var context = fm.prpLltextContextInnerHTML.value;
  if(context.length<1){
    errorMessage = errorMessage + "结案报告不允许为空\n";
  } */
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm)) {
		return false;
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;

	fm.submit();

	return true;
}
//modify by huangyunzhong 20051027 和其他险一样,统一使用ClaimPub.js
/**
 *@description 弹出留言保存页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
/*
function openWinSave1(){
  var businessNo = fm.prpLclaimRegistNo.value;
  var policyNo = fm.prpLclaimPolicyNo.value;
  var claimNo = fm.prpLendcaseClaimNo1.value;
  var registNo = fm.prpLclaimRegistNo.value;
  msg=window.open("/claim/messageQueryInfo.do?businessNo=" +businessNo+ "&nodeType=endca&policyNo=" +policyNo+ "&claimNo=" +claimNo+ "&registNo=" +registNo,"NewWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=500,Height=300");
}
*/
/**
 *@description 弹出查看留言页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
/*
function openWinQuery(){
  var win;
  var messagedo="/claim/messageQueryList.do?registNo="+ fm.prpLclaimRegistNo.value;
  win=window.showModalDialog(messagedo,"NewWindow","status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}
*/
/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relateThis() {
	var policyNo = fm.prpLclaimPolicyNo.value;
	var registNo = fm.prpLclaimRegistNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
	//newWindow.focus = true; 
}