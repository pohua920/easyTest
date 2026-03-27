function dbclickUwType(field, eventType, otherCondition) {
	fm.modelNo.value = "";
	fm.modelName.value = "";
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'UwType', '0,1', 'Y', otherCondition);
	} else if (eventType == "change") {
		code_CodeChange(field, 'UwType', '0,1', 'Y', otherCondition);
	}
}

function dbclickComCode(field, eventType) {
	if (trim(fm.uwType.value) == "") {
		alert(i18n.platform.pleaseSelectCheckedType);  //请先选择审核类型！
		return false;
	}
	fm.modelNo.value = "";
	fm.modelName.value = "";
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'comCode', '0,1', 'Y');
	} else if (eventType == "change") {
		code_CodeChange(field, 'comCode', '0,1', 'Y');
	}
}

function dbclickModelNo(field, eventType) {
	if (trim(fm.uwType.value) == "") {
		alert(i18n.platform.pleaseSelectCheckedType);  //请先选择审核类型！
		return false;
	}
	if (trim(fm.comCode.value) == "") {
		alert(i18n.platform.pleaseSelectCheckedDepartment);  //请先选择审核部门！
		return false;
	}

	var comCode = fm.comCode.value;
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'modelNoByComCode', '0,1', 'Y', comCode);
	} else if (eventType == "change") {
		code_CodeChange(field, 'modelNoByComCode', '0,1', 'Y', comCode);
	}
}

function dbclickNodeNo(field, eventType) {
	if (trim(fm.uwType.value) == "") {
		alert(i18n.platform.pleaseSelectCheckedType);  //请先选择审核类型！
		return false;
	}
	if (trim(fm.comCode.value) == "") {
		alert(i18n.platform.pleaseSelectCheckedDepartment);  //请先选择审核部门！
		return false;
	}
	if (trim(fm.riskCategoryCode.value) == "") {
		alert(i18n.platform.pleaseSelectClaimKind);  //请先选择险种大类！
		return false;
	}
	if (trim(fm.classCode.value) == "") {
		alert(i18n.platform.pleaseSelectClaim);  //请先选择险类！
		return false;
	}
	if (trim(fm.riskCode.value) == "") {
		alert(i18n.platform.pleaseSelectClaimOfKind);  //请先选择险种！
		return false;
	}
	if (trim(fm.modelNo.value) == "") {
		alert(i18n.platform.pleaseSelectClaimModel);  //请先选择模板！
		return false;
	}
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'nodeNoByModelNo', '0,1', 'Y', fm.modelNo.value);
	} else if (eventType == "change") {
		code_CodeChange(field, 'nodeNoByModelNo', '0,1', 'Y', fm.modelNo.value);
	}
}

function nextStep() {
	if (trim(fm.uwType.value) == "") {
		alert(i18n.platform.pleaseSelectCheckedType);  //请先选择审核类型！
		return false;
	}
	if (trim(fm.comCode.value) == "") {
		alert(i18n.platform.pleaseSelectCheckedDepartment);  //请先选择审核部门！
		return false;
	}
	if (trim(fm.modelNo.value) == "") {
		alert(i18n.platform.pleaseSelectClaimModel);  //请先选择模板！
		return false;
	}
	/*
	if(trim(fm.nodeNo.value) == "")
	{
		alert("请选择级别！");
		return false;
	}
	if(fm.selectedUsers.length == null || fm.selectedUsers.length == 0)
	{
		alert("请选择本级别人员！");
		return false;
	}
	setDatabaseUsers();
	setSelectedUsers();
	*/
	fm.submit();
}

function addUser() {
	var index = fm.databaseUsers.selectedIndex;
	var count = fm.selectedUsers.length;
	while (index >= 0) {
		fm.selectedUsers[count] = new Option(fm.databaseUsers[index].text, fm.databaseUsers[index].value);
		fm.databaseUsers[index] = null;
		index = fm.databaseUsers.selectedIndex;
		count = fm.selectedUsers.length;
	}
	/*
	var index = fm.databaseUsers.selectedIndex;
	var count = fm.selectedUsers.length;
	if(index < 0)
	{
		return;
	}
	fm.selectedUsers[count]=new Option(fm.databaseUsers[index].text, fm.databaseUsers[index].value);
	fm.databaseUsers[index]=null;
	*/
}

function removeUser() {
	var index = fm.selectedUsers.selectedIndex;
	var count = fm.databaseUsers.length;
	while (index >= 0) {
		fm.databaseUsers[count] = new Option(fm.selectedUsers[index].text, fm.selectedUsers[index].value);
		fm.selectedUsers[index] = null;
		index = fm.selectedUsers.selectedIndex;
		count = fm.databaseUsers.length;
	}
	/*
	var index = fm.selectedUsers.selectedIndex;
	var count = fm.databaseUsers.length;
	if(index < 0)
	{
		return;
	}	
	fm.databaseUsers[count]=new Option(fm.selectedUsers[index].text, fm.selectedUsers[index].value);
	fm.selectedUsers[index]=null;
	*/
}

function removeAllUsers() {
	for (var i = fm.selectedUsers.length - 1; i >= 0; i--) {
		fm.databaseUsers[i] = new Option(fm.selectedUsers[i].text, fm.selectedUsers[i].value);
		fm.selectedUsers[i] = null;
	}
}

function addAllUsers() {
	var index = fm.selectedUsers.length;
	var count = fm.databaseUsers.length;
	for (var i = count - 1; i >= 0; i--) {
		fm.selectedUsers[index + i] = new Option(fm.databaseUsers[i].text, fm.databaseUsers[i].value);
		fm.databaseUsers[i] = null;
	}
}

function setDatabaseUsers() {
	var value = "";
	for (var i = 0; i < fm.databaseUsers.length; i++) {
		value = value + "<input type=hidden name='databaseUsers' value='" +
			fm.databaseUsers.options[i].value + "'>";
	}
	eval("databaseUsersDiv").innerHTML = value;
}

function setSelectedUsers() {
	var value = "";
	for (var i = 0; i < fm.selectedUsers.length; i++) {
		value = value + "<input type=hidden name='selectedUsers' value='" +
			fm.selectedUsers.options[i].value + "'>";
	}
	eval("selectedUsersDiv").innerHTML = value;
}

function riskCodeByClassCode(field, eventType) {
	if (eventType == "dbclick" || eventType == "keyup") {
		field.value = "";
		code_CodeQuery(field, 'riskcodeByClassCode', '0,1', 'Y', addCondition('prpDriskClassCode', fm.classCode.value));
	} else if (eventType == "change") {
		code_CodeChange(field, 'riskcodeByClassCode', '0,1', 'Y', addCondition('prpDriskClassCode', fm.classCode.value));
	}
}

function checkUtiUwLevel() {
	var userLength = fm.userName.length;
	if (userLength > 1) {
		for (var i = 1; i < userLength; i++) {
			var userName = fm.userName[i].value;
			var userComName = fm.userComName[i].value;
			var startDate = trim(fm.startDate[i].value);
			var endDate = trim(fm.endDate[i].value);
			var reg = new RegExp("([0-9]{4})-([0-9]{2})-([0-9]{2})");
			var r = startDate.match(reg);
			if (trim(fm.userCode[i].value) == "") {
				alert(userComName + i18n.platform.staffCannotEmpty);  //下的人员不能为空！
				return false;
			}
			if (trim(fm.underComCode[i].value) == "") {
				alert(i18n.platform.staff + userName + i18n.platform.auditInstitutionsCannotEmpty); //1人员： 2的审核机构不能为空！
				return false;
			}
			if (trim(fm.riskCode[i].value) == "") {
				alert(i18n.platform.staff + userName + i18n.platform.claimOfKindCannotEmpty);  //1人员： 2的险种不能为空！
				return false;
			}
			if (r == null) {
				alert(userName + i18n.platform.valueDateFormatNotCorrect);  //的任职起期格式不正确，应该为：yyyy-MM-dd。
				return false;
			}
			if (endDate != "") {
				r = endDate.match(reg);
				if (r == null) {
					alert(userName + i18n.platform.valueDateFormatNotCorrect);  //的任职起期格式不正确，应该为：yyyy-MM-dd。
					return false;
				}
			} else {
				var endDateYear = parseInt(startDate.substring(0, 4)) + 100;
				var endDateMonthDay = startDate.substring(4, 10);
				fm.endDate[i].value = endDateYear + endDateMonthDay;
			}
		}
	}
	return true;
}

function dbclickNodeNo(field, eventType) {
	if (trim(fm.modelNoQuery.value) == "") {
		alert(i18n.platform.pleaseSelectClaimModel);  //请先选择模板！
		return false;
	}
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'nodeNoByModelNo', '0,1', 'Y', fm.modelNoQuery.value);
	} else if (eventType == "change") {
		code_CodeChange(field, 'nodeNoByModelNo', '0,1', 'Y', fm.modelNoQuery.value);
	}
}