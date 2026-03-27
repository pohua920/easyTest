function dbclickRiskCategory(field, eventType) {
	fm.classCode.value = "";
	fm.className.value = "";
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'riskCategoryByClassCode', '0,1', 'Y', fm.classCode.value);
	} else if (eventType == "change") {
		code_CodeChange(field, 'riskCategoryByClassCode', '0,1', 'Y', fm.classCode.value);
	}
}

function dbclickClassCode(field, eventType) {
	if (trim(fm.riskCategoryCode.value) == "") {
		alert("請先選擇險種大類！");
		return false;
	}
	if (eventType == "dbclick" || eventType == "keyup") {
		code_CodeSelect(field, 'classCodeByRiskCategory', '0,1', 'Y', fm.riskCategoryCode.value);
	} else if (eventType == "change") {
		code_CodeChange(field, 'classCodeByRiskCategory', '0,1', 'Y', fm.riskCategoryCode.value);
	}
}

function doInsert() {
	//fm.remark.focus();
	if (trim(fm.uwType.value) == "") {
		alert("請選擇審核類型！");
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
	if (trim(fm.factorCode.value) == "") {
		alert("請輸入因子代碼！");
		return false;
	}
	if (trim(fm.factorName.value) == "") {
		alert("請輸入因子名稱！");
		return false;
	}
	if (trim(fm.multiSelectFlag.value) == "") {
		alert("請選擇因子類型！");
		return false;
	}
	if (trim(fm.multiSelectFlag.value) == "S") {
		if (trim(fm.factorAttr.value) == "") {
			alert("請選擇因子屬性！");
			return false;
		}
	}
	if (trim(fm.multiSelectFlag.value) == "C") {
		if (trim(fm.codeCmbfCode.value) == "") {
			alert("請輸入組合因子代碼！");
			return false;
		}
		if (trim(fm.codeCmbfDesc.value) == "") {
			alert("請輸入組合因子描述！");
			return false;
		}
		if (trim(fm.descCmbfDesc.value) == "") {
			alert("請輸入賦值列標題！");
			return false;
		}
		if (trim(fm.descCmbfAttr.value) == "") {
			alert("請選擇組合因子屬性！");
			return false;
		}
	}
	if (confirm("確實要儲存嗎？")) {
		fm.submit();
	}
}