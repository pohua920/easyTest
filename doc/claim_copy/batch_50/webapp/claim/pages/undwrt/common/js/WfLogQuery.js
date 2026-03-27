function validateForm() {
	var nodeStatusObj = document.fm.nodeStatus;
	if (fm.EditType.value == "deal" || fm.EditType.value == "query") {
		if (nodeStatusObj.item(0).checked == false && nodeStatusObj.item(1).checked == false &&
			nodeStatusObj.item(2).checked == false && nodeStatusObj.item(3).checked == false &&
			nodeStatusObj.item(4).checked == false && nodeStatusObj.item(5).checked == false) {
			alert("必须选择任務状态！");
			return false;
		}
		//核赔已处理完毕任務查询增加必输入查询条件 add by liping 080623
		if (nodeStatusObj.item(5).checked == true) {
			if (trim(fm.businessNo.value) == '' && trim(fm.policyNo.value) == '' && trim(fm.claimNo.value) == '') {
				alert("請輸入 業務號 或 保單號 或 立案號 進行查詢！");
				return false;
			}
		}
	}
	fm.submit();
}

function boundCheckBox(controlField, checkBoxField) {
	var count = 0;
	try {
		count = checkBoxField.length;
	} catch (E) {}
	if (isNaN(count)) {
		checkBoxField.checked = controlField.checked;
	} else {
		for (var i = 0; i < count; i++) {
			checkBoxField[i].checked = controlField.checked;
		}
	}
}

function validateFlowInTime() {
	var start = fm.flowInTime1.value;
	var end = fm.flowInTime2.value;
	if (start != "" && !regExpTest(start, /[\d]{4}-[\d]{1,2}-[\d]{1,2}/)) {
		errorMessage("请输入合法的起始提交时间，格式为YYYY-MM-DD。");
		fm.flowInTime1.focus();
		fm.flowInTime1.select();
		return false;
	}
	if (end != "" && !regExpTest(end, /[\d]{4}-[\d]{1,2}-[\d]{1,2}/)) {
		errorMessage("请输入合法的终止提交时间，格式为YYYY-MM-DD。");
		fm.flowInTime2.focus();
		fm.flowInTime2.select();
		return false;
	}
}

function checkNodeStatus(nodeStatusValue) {
	var editType = $(":input[name='EditType']").val();
	if(editType=="query"){
		return;
	}
	var nodeStatusObj = document.fm.nodeStatus;
	if (nodeStatusValue == "1" || nodeStatusValue == "2" || nodeStatusValue == "3" || nodeStatusValue == "5") {
		nodeStatusObj.item(4).checked = false;
		nodeStatusObj.item(5).checked = false;
	} else if (nodeStatusValue == "4") {
		nodeStatusObj.item(0).checked = false;
		nodeStatusObj.item(1).checked = false;
		nodeStatusObj.item(2).checked = false;
		nodeStatusObj.item(3).checked = false;
		nodeStatusObj.item(5).checked = false;
	} else if (nodeStatusValue == "0") {
		nodeStatusObj.item(0).checked = false;
		nodeStatusObj.item(1).checked = false;
		nodeStatusObj.item(2).checked = false;
		nodeStatusObj.item(3).checked = false;
		nodeStatusObj.item(4).checked = false;
	}
}

function changeField(categoryValue, handType) {
	if (handType == "11") {
		if (categoryValue == "1") {
			licenseNoId.style.display = "";
			identifyId.style.display = "none";
			contractId.style.display = "none";
		} else if (categoryValue == "4") {
			licenseNoId.style.display = "none";
			identifyId.style.display = "";
			contractId.style.display = "none";
		} else if (categoryValue == "2") {
			licenseNoId.style.display = "none";
			identifyId.style.display = "none";
			contractId.style.display = "";
		} else {
			licenseNoId.style.display = "none";
			identifyId.style.display = "none";
			contractId.style.display = "none";
		}
	}
}

function buildRiskCodeSelect(riskCategoryField, riskCodeField) {
	$(riskCodeField).width(function(){
		//固定寬，確保riskCodeField不會因為empty而導致寬度動態變化
		return $(this).data("w") || $(this).data("w",$(this).width() + 5).data("w");
	})
	$(riskCodeField).empty().append(function(){
		var e = this;
		var category = $(riskCategoryField).val();
		if(category == ""){
			return "";
		} else {
			var riskObjects = $.map(riskCodes , function(o){
				return category == o.riskCategory ? o : null;
			});
			$.each(riskObjects , function( i , r){
				var $o = $("<option/>").prop({value : r.riskCode , title : r.riskName});
				$o.text(r.riskCode + " - " + r.riskName);
				$(e).append($o);
			});
		}
	});
}

function gotoPage(strMethod) {
	if (strMethod == "First") {
		fm.pageNo.value = 1;
	} else if (strMethod == "Previous") {
		fm.pageNo.value = parseInt(fm.pageNo.value) - 1;
	} else if (strMethod == "Next") {
		fm.pageNo.value = parseInt(fm.pageNo.value) + 1;
	} else if (strMethod == "Final") {
		fm.pageNo.value = fm.PageCount.value;
	} else if (strMethod == "Personal") {
		if (parseInt(fm.Personal.value) < 1 || parseInt(fm.Personal.value) > parseInt(fm.PageCount.value)) {
			alert("沒有這一頁，請重試！");
			fm.Personal.focus();
			return false;
		} else {
			fm.pageNo.value = fm.Personal.value;
		}
	}
	fm.action = "/claim/newWfLogQuery.do?actionType=queryContinue";
	fm.submit();
}

function prepareBatchSubmit(handType, actionType) {
	var n = 0;
	if (fm.checkboxSelect != null) {
		for (var i = 1; i < fm.checkboxSelect.length; i++) {
			if (fm.checkboxSelect[i].checked == true) {
				n = n + 1;
			}
		}
	}
	if (n == 0) {
		alert("请选择记录！");
	} else {
		fm.action = "/claim/commonBatchTaskDeal.do?actionType=" + actionType;
		fm.submit();
	}
}

function batchSubmit(actionType, message) {
	if (trim(fm.HandleText.value) == "") {
		alert("審批意見不能爲空！");
		return;
	}
	if (confirm("確實要" + message + "嗎？")) {
		fm.action = "/claim/commonBatchTaskDeal.do?actionType=" + actionType;
		fm.submit();
	}
}

function viewClaimInfo() {
	var businessType = fm.BusinessType.value;
	var businessNo = fm.BusinessNo.value;
	var strRiskCode = fm.strRiskCode.value;
	var strRegistNo = fm.RegistNo.value;
	var strClaimNo = fm.ClaimNo.value;
	if (businessType == "C") //计算书
	{
		var strURL = "/claim/undwrt/common/WfLogUIZhQueryClaimDetailShow.jsp?RiskCode=" + strRiskCode + "&RegistNo=" + strRegistNo + "&ClaimNo=" + strClaimNo + "&CompensateNo=" + businessNo + "&BusinessType=" + businessType;

		var newWindow = window.open(strURL, "CLAIMDETAIL", 'width=640,height=480,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
		newWindow.focus();
		//openWindow(strURL,"CLAIMDETAIL");
	}
	if (businessType == "Y") //预赔
	{}
}

function showPolicyInfo() {
	var vBizType = "POLICY";
	var vRiskCode = fm.riskCode.value;
	var vPolicyNo = fm.PolicyNo.value;
	var vPrpallUrl = fm.prpallUrl.value;

	if (vRiskCode == "OAZ" || vRiskCode == "BAZ" || vRiskCode == "JPB") {
		vRiskCode = "OTH";
	}
	var vURL = vPrpallUrl + '/' + vRiskCode + '/tbcbpg/UIPrPoEn' + vRiskCode + 'Show.jsp?BIZTYPE=' + vBizType +
		'&SHOWTYPE=SHOW&BizNo=' + vPolicyNo + '&RiskCode=' + vRiskCode;
	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
}

function showHistoryEndorses() {
	var index = 0;
	for (var i = 0; i < fm.radio.length; i++) {
		if (fm.radio.item(i).checked) {
			index = i;
			break;
		}
	}
	var vPolicyNo = fm.policyNo[i].value;
	var vRiskCode = fm.riskCode[i].value;

	if (vPolicyNo == "") {
		alert("沒有提供有效的保單號！");
		return;
	}
	var vURL = "/claim/taskDealHistoryProposal.do?actionType=showHistoryEndorses&policyNo=" + vPolicyNo;
	window.open(vURL, '详细信息', 'width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
}