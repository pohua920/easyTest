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
		alert("請先選擇審核類型！");
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

function dbclickRiskCategory(field, eventType) {
	if (trim(fm.uwType.value) == "") {
		alert("請先選擇審核類型！");
		return false;
	}
	if (trim(fm.comCode.value) == "") {
		alert("請先選擇審核部門！");
		return false;
	}
	fm.classCode.value = "";
	fm.className.value = "";
	fm.riskCode.value = "";
	fm.riskName.value = "";
	fm.modelNo.value = "";
	fm.modelName.value = "";
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'RiskCategory', '0,1', 'Y');
	} else if (eventType == "change") {
		code_CodeChange(field, 'RiskCategory', '0,1', 'Y');
	}
}

function dbclickClassCode(field, eventType) {
	if (trim(fm.uwType.value) == "") {
		alert("請先選擇審核類型！");
		return false;
	}
	if (trim(fm.comCode.value) == "") {
		alert("請先選擇審核部門！");
		return false;
	}
	if (trim(fm.riskCategoryCode.value) == "") {
		alert("請先選擇險種大類！");
		return false;
	}
	fm.riskCode.value = "";
	fm.riskName.value = "";
	fm.modelNo.value = "";
	fm.modelName.value = "";
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'classCodeByRiskCategory', '0,1', 'Y', fm.riskCategoryCode.value);
	} else if (eventType == "change") {
		code_CodeChange(field, 'classCodeByRiskCategory', '0,1', 'Y', fm.riskCategoryCode.value);
	}
}

function dbclickRiskCode(field, eventType) {
	if (trim(fm.uwType.value) == "") {
		alert("請先選擇審核類型！");
		return false;
	}
	if (trim(fm.comCode.value) == "") {
		alert("请先选择审核部门！");
		return false;
	}
	if (trim(fm.riskCategoryCode.value) == "") {
		alert("請先選擇險種大類！");
		return false;
	}
	if (trim(fm.classCode.value) == "") {
		alert("請先選擇險類！");
		return false;
	}
	fm.modelNo.value = "";
	fm.modelName.value = "";
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeQuery(field, 'riskcodeByClassCode', '0,1', 'Y', addCondition('prpDriskClassCode', fm.classCode.value));
	} else if (eventType == "change") {
		code_CodeChange(field, 'riskcodeByClassCode', '0,1', 'Y', addCondition('prpDriskClassCode', fm.classCode.value));
	}
}

function dbclickModelNo(field, eventType) {
	if (trim(fm.uwType.value) == "") {
		alert("請先選擇審核類型！");
		return false;
	}
	if (trim(fm.comCode.value) == "") {
		alert("請先選擇審核部門！");
		return false;
	}
	if (trim(fm.riskCategoryCode.value) == "") {
		alert("請先選擇險種大類！");
		return false;
	}
	if (trim(fm.classCode.value) == "") {
		alert("請先選擇險類！");
		return false;
	}
	if (trim(fm.riskCode.value) == "") {
		alert("請先選擇險種！");
		return false;
	}
	var addonsUwType = null;
	if (trim(fm.uwType.value) == "P") //核保11
	{
		addonsUwType = "11";
	} else if (trim(fm.uwType.value) == "C" || trim(fm.uwType.value) == "Y") //核赔22
	{
		addonsUwType = "22";
	}
	var addonsCondition = fm.comCode.value + ";" + fm.riskCode.value + ";" + addonsUwType;
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'modelNoByComCodeRiskCode', '0,1', 'Y', addonsCondition);
	} else if (eventType == "change") {
		code_CodeChange(field, 'modelNoByComCodeRiskCode', '0,1', 'Y', addonsCondition);
	}
}

function dbclickNodeNo(field, eventType) {
	if (trim(fm.uwType.value) == "") {
		alert("請先選擇審核類型！");
		return false;
	}
	if (trim(fm.comCode.value) == "") {
		alert("請先選擇審核部門！");
		return false;
	}
	if (trim(fm.riskCategoryCode.value) == "") {
		alert("請先選擇險種大類！");
		return false;
	}
	if (trim(fm.classCode.value) == "") {
		alert("請先選擇險類！");
		return false;
	}
	if (trim(fm.riskCode.value) == "") {
		alert("請先選擇險種！");
		return false;
	}
	if (trim(fm.modelNo.value) == "") {
		alert("請先選擇模板！");
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
		alert("請選擇審核類型！");
		return false;
	}
	if (trim(fm.comCode.value) == "") {
		alert("請選擇審核部門！");
		return false;
	}
	if (trim(fm.riskCategoryCode.value) == "") {
		alert("請選擇險種大類！");
		return false;
	}
	if (trim(fm.classCode.value) == "") {
		alert("請選擇險類！");
		return false;
	}
	if (trim(fm.riskCode.value) == "") {
		alert("請選擇險種！");
		return false;
	}
	if (trim(fm.modelNo.value) == "") {
		alert("請選擇模板！");
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