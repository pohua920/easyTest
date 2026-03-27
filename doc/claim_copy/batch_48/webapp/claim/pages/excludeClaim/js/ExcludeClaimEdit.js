/*****************************************************************************
 * DESC       ：立案除外的脚本函数页面
 * AUTHOR     ：caozhigang
 * CREATEDATE ： 2009-06-05
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
function KeyDown(actionType) {
	if (event.keyCode == 13 && fm.queryButton.disabled == false) {
		if (actionType == "insert") {
			insertQuery();
		} else {
			historyQuery();
		}
	} else {
		fm.queryButton.disabled = false;
	}
}

function clickable() {
	fm.queryButton.disabled = false;
}
/**
 *@description 查询
 *@param       无
 *@return      
 */
function insertQuery() {
	if ((fm.RegistNo.value.length > 3 && "D"==getClassCodeType(fm.RegistNo.value.substr(1, 2))|| (fm.PolicyNo.value.length > 3 && "D"==getClassCodeType(fm.PolicyNo.value.substr(1, 2))))) {
		alert(i18n.excludeClaim.featureAvailableInsurance); //抱歉，此功能只对非车险开放！
		return false;
	} else {
		fm.action = "/claim/excludeClaim.do?actionType=insertQuery";
		fm.target = "QueryResultFrame";
		fm.queryButton.disabled = true;
		fm.method = "post";
		fm.submit();
	}
}
/**
 *@description 进入除外处理页面
 *@param       无
 *@return      
 */
function prepareInsert(registNo) {
	fm.action = "/claim/excludeClaim.do?actionType=prepareInsert&registNo=" + registNo;
	fm.target = "fraInterface";
	fm.submit();
}



/**
 *@description 除外提交
 *@param       无
 *@return      
 */
function insert() {
	var registNo = fm.registNo.value;
	var userCode = fm.userCode.value;
	var excludereason = fm.excludereason.value;
	//去掉这个判断，用户不在是应00开头
//	if (userCode.substr(0, 2) !== "00") {
//		alert(i18n.excludeClaim.functionOpenCorporationPersonnel); //抱歉，此功能仅为总公司管理人员开放，您无权使用！
//		return false;
//	}
	if (excludereason.length < 1) {
		alert("請填寫除外原因!"); //请填写除外原因！
		return false;
	} else {
		fm.action = contextRootPath+"/claim/excludeClaim.do?actionType=insert&registNo=" + registNo;
		fm.method = "post";
		fm.submit();
	}
}


function historyQuery() {
	fm.action = "/claim/excludeClaim.do?actionType=historyQuery";
	fm.target = "QueryResultFrame";
	fm.queryButton.disabled = true;
	fm.submit();
}