/**
 * 代码录入--平台中心组控制，项目组不得修改，否则後果自负
 * 如果发现有问题或需求，请通知提供者
 * 如果方法没有用private开头,则方法是公开的,且保证向後兼容.
 * 最新加的方法在最後面
 */
var inCodeQuery = false; //避免查询的时候处理代码查询的onchange

/**
 * 添加条件，用於otherCondition
 * @param key key
 * @param value value
 */
function addCondition(key, value) {
	var retValue = key + "=" + value;
	return retValue;
}
/**
 * 准备选择代码,常用於录入数据
 * @param field 代码域本身
 * @param codeType 代码类型
 * @param codeRelation 相关赋值域偏移量，以","分割
 * @param isClear 查不到代码时是否清空相关域
 * @param otherCondition 其它条件(key=value,key=value)形式
 * @param callBackMethod 执行完後回调的方法
 * @param getDataMethod 取值方法，当codeType为custom时取数据用（暂不支持)
 */
function code_CodeSelect(field, codeType, codeRelation, isClear, otherCondition, callBackMethod, getDataMethod) {
	if (event.type == "keyup") {
		var charCode = window.event.keyCode;
		if (!(charCode == 13 & window.event.ctrlKey)) {
			return;
		}
	}
	inCodeQuery = true;
	var cf = document.all.CodeFrame;
	cf.style.display = "";
	field.style.display = "none";
	private_Code_CallService(field, "select", codeType, codeRelation, isClear, otherCondition, callBackMethod, getDataMethod);
	field.style.display = "";
	inCodeQuery = false;
}

/**
 * 准备查询代码,常用於查询域,可多选
 * @param field 代码域本身
 * @param codeType 代码类型
 * @param codeRelation 相关赋值域偏移量，以","分割
 * @param isClear 查不到代码时是否清空相关域
 * @param otherCondition 其它条件(key=value,key=value)形式
 * @param callBackMethod 执行完後回调的方法
 * @param getDataMethod 取值方法，当codeType为custom时取数据用（暂不支持)
 */
function code_CodeQuery(field, codeType, codeRelation, isClear, otherCondition, callBackMethod, getDataMethod) {
	if (event.type == "keyup") {
		var charCode = window.event.keyCode;
		if (!(charCode == 13 & window.event.ctrlKey)) {
			return;
		}
	}
	inCodeQuery = true;
	var cf = document.all.CodeFrame;
	cf.style.display = "";
	field.style.display = "none";
	private_Code_CallService(field, "query", codeType, codeRelation, isClear, otherCondition, callBackMethod, getDataMethod);
	field.style.display = "";
	inCodeQuery = false;
}

/**
 * 改变代码录入域的值时的操作
 * @param field 代码域本身
 * @param codeType 代码类型
 * @param codeRelation 相关赋值域偏移量，以","分割
 * @param isClear 查不到代码时是否清空相关域
 * @param otherCondition 其它条件(key=value,key=value)形式
 * @param callBackMethod 执行完後回调的方法
 * @param getDataMethod 回调方法，当codeType为custom时取回调方法的数据（暂不支持)
 */
function code_CodeChange(field, codeType, codeRelation, isClear, otherCondition, callBackMethod, getDataMethod) {
	if (inCodeQuery == true) {
		return;
	}
	var cf = document.all.CodeFrame;

	field.style.display = "none";
	cf.style.display = "none";

	private_Code_CallService(field, "change", codeType, codeRelation, isClear, otherCondition, callBackMethod, getDataMethod);
	field.style.display = "";
}

/**
 * 调用服务过程
 */
function private_Code_CallService(field, codeMethod, codeType, codeRelation, isClear, otherCondition, callBackMethod, getDataMethod) {
	var cf = document.all.CodeFrame;
	var eL = 0;
	var eT = 0;
	var p = field;
	while (p && p.tagName != "BODY") {
		eT += p.offsetTop;
		eL += p.offsetLeft;
		p = p.offsetParent;
	}
	cf.style.top = eT - field.offsetTop;
	cf.style.left = eL;
	var fieldIndex = getElementIndexInForm(fm, field);
	var fieldValue = field.value;
	if (fieldValue != null) {
		fieldValue = fieldValue.replace("*", "%"); //转换通配符
	}

	//相关域
	var relations = new Array();
	if (codeRelation.indexOf(",") > -1) {
		relations = codeRelation.split(",");
	} else {
		relations[0] = codeRelation;
	}
	//如果isClear为Y,则清空相关域
	if (isClear == "Y") {
		var relationsCount = relations.length;
		for (var i = 0; i < relationsCount; i++) {
			try {
				field.form.elements[fieldIndex + parseInt(relations[i], 10)].value = "";
			} catch (E) {}
		}
	}
	document.CodeFrame.codeQuery(fieldIndex, fieldValue, codeMethod, codeType, codeRelation, isClear, otherCondition, callBackMethod, getDataMethod);
}

/**
 * 按下确定按钮时的操作
 */
function setFieldValue() {
	var cf = parent.document.all.CodeFrame;
	cf.style.display = "none";
	//相关域
	var relations = new Array();
	if (fm.codeRelation.value.indexOf(",") > -1) {
		relations = fm.codeRelation.value.split(",");
	} else {
		relations[0] = fm.codeRelation.value;
	}
	var fieldIndex = parseInt(fm.fieldIndex.value, 10);
	var value = "";

	//如果codeMethod为change
	if (fm.codeMethod.value == "change") {
		if (fm.codeselect.options.length == 1) {
			fm.codeselect.selectedIndex = 0;
		}
		if (fm.codeselect.options.length == 0) {
			if (fm.isClear.value == "Y") {
				var relationsCount = relations.length;
				for (var i = 0; i < relationsCount; i++) {
					parent.document.fm.elements[fieldIndex + parseInt(relations[i], 10)].value = "";
				}
			}
			if (fm.callBackMethod.value != "") {
				eval("parent." + fm.callBackMethod.value);
			}
			return; //查不到代码时返回
		}
	}

	if (fm.codeselect.selectedIndex < 0) {
		cf.style.display = "";
		showMessage("请选择代码");
		fm.codeselect.selectedIndex = 0;
		return false;
	}

	if (fm.codeMethod.value == "query") { //query方法允许多选
		//取值
		var rowValues = new Array();
		var values = new Array();
		var selectedCount = 0; //选中的代码的个数
		for (var i = 0; i < fm.codeselect.length; i++) {
			if (fm.codeselect.options[i].selected == true) {
				rowValues = new Array();
				var selectedValue = fm.codeselect.options[i].value;
				if (selectedValue.indexOf(FIELD_SEPARATOR) > -1) {
					rowValues = selectedValue.split(FIELD_SEPARATOR);
				} else {
					rowValues[0] = selectedValue;
				}
				values[selectedCount++] = rowValues;
			}
		}
		//赋值
		var relationsCount = relations.length;
		for (var i = 0; i < relationsCount; i++) {
			value = values[0][i];
			for (var j = 1; j < selectedCount; j++) {
				if (i >= values.length) {
					value = value + "," + values[j][values.length - 1];
				} else {
					value = value + "," + values[j][i];
				}
			}
			parent.document.fm.elements[fieldIndex + parseInt(relations[i], 10)].value = value;
		}
	} else { //非query方法都只允许单选
		//取值
		var values = new Array();
		if (fm.codeselect.value.indexOf(FIELD_SEPARATOR) > -1) {
			values = fm.codeselect.value.split(FIELD_SEPARATOR);
		} else {
			values[0] = fm.codeselect.value;
		}
		//赋值
		var relationsCount = relations.length;
		for (var i = 0; i < relationsCount; i++) {
			if (i >= values.length) {
				value = values[values.length - 1];
			} else {
				value = values[i];
			}
			parent.document.fm.elements[fieldIndex + parseInt(relations[i], 10)].value = value;
		}
	}

	if (fm.callBackMethod.value != "") {
		eval("parent." + fm.callBackMethod.value);
	}
	try {
		parent.document.fm.elements[fieldIndex].focus();
	} catch (E) {}
}

/**
 * 按下取消按钮时的操作
 */
function cancelFieldValue() {
	//如果isClear等於Y则清空相关域
	if (fm.isClear.value == "Y") {
		var relations = new Array();
		if (fm.codeRelation.value.indexOf(",") > -1) {
			relations = fm.codeRelation.value.split(",");
		} else {
			relations[0] = fm.codeRelation.value;
		}

		var fieldIndex = parseInt(fm.fieldIndex.value, 10);
		var relationsCount = relations.length;
		for (var i = 0; i < relationsCount; i++) {
			parent.document.fm.elements[fieldIndex + parseInt(relations[i], 10)].value = "";
		}
	}
	var cf = parent.document.all.CodeFrame;
	cf.style.display = "none";

	if (fm.callBackMethod.value != "") {
		eval("parent." + fm.callBackMethod.value);
	}

	try {
		parent.document.fm.elements[fieldIndex].focus();
	} catch (E) {}
}

/**
 * 按键时的操作
 */
function fieldOnKeyPress() {
	var charCode = window.event.keyCode;
	if (charCode == 13) {
		setFieldValue();
	} else if (charCode == 32) {
		cancelFieldValue();
	}
}