/*****************************************************************************
 * DESC       ：工作流模板的脚本函数页面
 * AUTHOR     ：weishixin
 * CREATEDATE ： 2004-08-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 检查报案登记
 *@param       无
 *@return      通过返回true,否则返回false
 */
function checkForm() {


	return validateForm(window.fm, "WorkFlowNode_Data,WorkFlowPath_Data");
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
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
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
	if (window.confirm("確定要清除嗎？")) {
		location.href = location.href;
		return true;
	} else {
		return false;
	}
}


/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet() {
	fm.buttonThirdPartyDelete[1].disabled = true;
	fm.prpLthirdPartyLicenseNo[1].disabled = true;
	return true;
}