/*****************************************************************************
 * DESC       ：通赔的脚本函数页面
 * AUTHOR     ：中科软
 * CREATEDATE ： 2013-03-05
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 引导页面提交
 *@param       无
 *@return      
 */
function KeyDown() {
	if (event.keyCode == 13 && fm.queryButton.disabled == false) {
		query();
	} else {
		fm.queryButton.disabled = false;
	}
}

function clickable() {
	fm.queryButton.disabled = false;
}

function guide() {
	var registNo = trim(fm.registNo.value);
	//	if(fm.registNo.value.substr(1,2)=="05"){
	//		alert("车险通赔请在新车险理赔系统中处理!");
	//		return false;
	//	}
	if (registNo == "") {
		alert(i18n.generalClaim.reportNumberCannotEmpty); //报案号不能为空!
		return false;
	}
	//
	if (registNo.length != 21) {
		alert(i18n.generalClaim.reportNumber21Long); //报案号应为21位长!
		return false;
	} else {
		fm.action = "/claim/generalClaim.do?actionType=guide&registNo=" + registNo;
		fm.method = "post";
		fm.submit();
	}
}
/**
 *@description 委托提交
 *@param       无
 *@return      
 */
function giveInsert() {
	var registNo = trim(fm.registNo.value);
	var permitFlag = fm.permitFlag.value;
	var receiveComcode = ""
	var nodeStatusList = document.getElementsByName("nodeStatus");
	var handlerCodeList = document.getElementsByName("handlerCode");
	var nodeNameList = document.getElementsByName("nodeName");
	var editType = $( "input[name='editType']" ).val();
	if (permitFlag == "NO") {
		alert(i18n.generalClaim.notConformCompensateUnableCompensate); //不符合通赔条件，不能进行通赔！
		return false;
	}
	if (nodeStatusList.length > 0) {
		for (var i = 0; i < nodeStatusList.length; i++) {
			if (nodeStatusList[i].value == "2" && (nodeNameList[i].value != "理算" && nodeNameList[i].value != "计算书" && nodeNameList[i].value != "特殊赔案" && nodeNameList[i].value != "立案")) {
				alert(i18n.generalClaim.processingTaskDoneThenCompensate); //存在处理中的暂存任务，请将该任务做完後，再进行通赔！
				return false;
			} else if (handlerCodeList[i].value.length >= 10 && nodeStatusList[i].value !== "2") {
				var j = i + 1;
				alert(i18n.compel.first + j + i18n.generalClaim.theTaskAlreadyOperate + handlerCodeList[i].value + i18n.generalClaim.theOperateGiveUpTaskCompensate);
				return false; //1第   2个任务已经被操作员  3占号，请该操作员放弃任务後再进行通赔！
			}
		}
	}
	if (editType=="TaskTransfer"){//任务转移
	} else if (editType=="CaseTransfer") {//案件转移
		if($( "input[name='comCode']" ).val()=="") {
			alert("“轉入單位代碼”不能為空，請錄入！");
			return false;
		}
		if($( "input[name='comName']" ).val()=="") {
			alert("“轉入單位名稱”不能為空，請錄入！");
			return false;
		}
	} else {//代查勘
		if($( "input[name='handleDept']:last" ).val()=="") {
			alert("“處理機構代碼”不能為空，請錄入！");
			return false;
		}
		if($( "input[name='deptName']:last" ).val()=="") {
			alert("“處理機構名稱”不能為空，請錄入！");
			return false;
		}
		if($( "input[name='handlercode']:last" ).val()=="") {
			alert("“操作員代碼”不能為空，請錄入！");
			return false;
		}
		if($( "input[name='handlername']:last" ).val()=="") {
			alert("“操作員名稱”不能為空，請錄入！");
			return false;
		}
	}
	fm.action = "/claim/generalClaim.do?actionType=giveInsert&registNo=" + registNo + "&receiveComcode=" + receiveComcode;
	fm.method = "post";
	fm.submit();
}

function receiveInsert() {
	var comcodes = document.getElementsByName("comcode");
	var handlercodes = document.getElementsByName("handlercode");
	if (comcodes.length > 0) {
		for (var i = 0; i < comcodes.length; i++) {
			if (trim(comcodes[i].value) == "") {
				var j = i + 1;
				alert(i18n.generalClaim.pleaseSelectFirst + j + i18n.generalClaim.taskProcessingOrganization); //1请选择第 2个任务的处理机构！
				return false;
			}
		}
	}
	if (handlercodes.length > 0) {
		for (var i = 0; i < handlercodes.length; i++) {
			if (trim(handlercodes[i].value) == "") {
				var j = i + 1;
				alert(i18n.generalClaim.pleaseSelectFirst + j + i18n.generalClaim.taskProcessingPerson); //1请选择第  2个任务的处理人员！
				return false;
			}
		}
	}
	fm.action = "/claim/generalClaim.do?actionType=receiveInsert";
	fm.target = "fraInterface";
	fm.submit();
}

function document.onkeydown() {
	if (event.keyCode == 13) {
		document.getElementById("button").click();
		return false;
	}
}

function query() {
	fm.action = "/claim/generalClaim.do?actionType=query";
	fm.target = "QueryResultFrame";
	fm.queryButton.disabled = true;
	fm.submit();
}

function prepareReceiveInsert(registNo) {
	fm.action = "/claim/generalClaim.do?actionType=prepareReceiveInsert&registNo=" + registNo;
	fm.target = "fraInterface";
	fm.submit();
}

function checkComCode(field, index, comCode, nodeName) {
	if (comCode=="") {
		alert(i18n.general.checkComCode);//请先选择处理机构！
		return false;
	}else if(nodeName==""){
		alert( "沒有可以修改的節點！");//请先选择处理机构！
		return false;
	} else {
		if (index == 1) {
			code_CodeSelect(field, 'queryUserHaveRights', '0,1', 'Y', '', comCode + "," + nodeName);
		} else {
			code_CodeSelect(field, 'queryUserHaveRights', '-1,0', 'Y', '', comCode + "," + nodeName);
		}
		return true;
	}
}

function regainQuery() {
	fm.action = "/claim/generalClaim.do?actionType=regainQuery";
	fm.target = "QueryResultFrame";
	fm.queryButton.disabled = true;
	fm.submit();
}

function prepareRegainInsert(registNo) {
	fm.action = "/claim/generalClaim.do?actionType=prepareRegainInsert&registNo=" + registNo;
	fm.target = "fraInterface";
	fm.submit();
}

function historyQuery() {
	fm.action = "/claim/generalClaim.do?actionType=historyQuery";
	fm.target = "QueryResultFrame";
	fm.queryButton.disabled = true;
	fm.submit();
}