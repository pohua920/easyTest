//创建求XMLHttpRequest对象

function createRequest(field, codeType, relation) {
	var hospitalCode = "";
	var hospitalName = "";
	if (codeType == "codeName") {
		hospitalName = field.value;
	} else {
		hospitalCode = field.value;
	}
	var url = contextRootPath + "/common/autoHospitalAction.do";
	$.ajax({
		type: "get",
		url: url,
		cache: false,
		dataType: "text",
		data: "hospitalCode=" + hospitalCode + "&hospitalName=" + hospitalName,
		success: function (data) {
			if (data.length > 0) {
				popdiv(data, field, codeType, relation); //添加option选项
			} else {
				var odiv = document.getElementById("hospitalList");
				odiv.innerHTML = "";
				odiv.style.display = "none";
			}
		}
	});
}

//发送请求，获取下一个列表框的列表数据
//参数oValue为当前列表框的选中值，此值作为下一个列表框的parentID号
//codeType=codeCode,codeName;
//codeType =0,1,2;
//获取医院的列表
var fieldIndex = 0;

function getHospital(field, codeType, relation) {
	fieldIndex = getElementIndexInForm(document.forms[0], field);
	if (field.value.length >= 1) {
		createRequest(field, codeType, relation);
	} else {
		//隐藏弹出的DIV
		hidePopdiv();
	}
}

//弹出DIV显示列表

function popdiv(stxt, field, codeType, relation) {
	var odiv = document.getElementById("hospitalList");
	odiv.style.left = findPosX(field) - 3;
	odiv.style.top = findPosY(field) - 5;
	if (trim(stxt) != "") {
		odiv.innerHTML = getlistring(trim(stxt), codeType, relation);
		odiv.style.display = "block";
	} else {
		hidePopdiv(); //隐藏弹出的DIV
	}
}

function findPosX(obj) {
	var curLeft = 0;
	if (obj.offsetParent) {
		do {
			curLeft += obj.offsetLeft;
		} while (obj = obj.offsetParent);
	} else if (obj.x) {
		curLeft += obj.x;
	}
	return curLeft;
}

function findPosY(obj) {
	var curTop = 0;
	if (obj.offsetParent) {
		do {
			curTop += obj.offsetTop;
		} while (obj = obj.offsetParent);
	} else if (obj.y) {
		curTop += obj.y;
	}
	return curTop;
}
//隐藏弹出的DIV

function hidePopdiv() {
	var odiv = document.getElementById("hospitalList");
	if (odiv!=null&&odiv!=undefined&&odiv.style.display != "none") {
		odiv.innerHTML = "";
		odiv.style.display = "none";
	}
}

//获取列表的HTML代码

function getlistring(stxt, codeType, relation) {
	var jsonObj = eval(stxt); // JSON字符串转JSON对象
	var str = "<ul id='ultxt' style='list-style-type:none;padding-left:0;margin-left:0'>";
	var s1 = "<li style='padding-left:5px;padding-right:5px' onmousemove=\"limouseover(this)\" onmouseout=\"limouseout(this)\" onclick=\"liselect(this,'" + codeType + "','" + relation + "')\">";
	var s2 = "</li>"
	for (i = 0; i < jsonObj.length; i++) {
		str = str + s1 + jsonObj[i].hospitalCode + "-" + jsonObj[i].hospitalName + s2
	}
	str = str + "</ul>"
	return str;
}

function liselect(obj, codeType, relation) {
	var openerFm = window.document.forms[0];
	var relations = new Array();
	if (relation.indexOf(",") > -1) {
		relations = relation.split(",");
	} else {
		relations[0] = relation;
	}

	var value = "";
	var rowValues = new Array();
	var selectedCount = 0;
	var selectedValue = obj.innerText;
	if (selectedValue.indexOf("-") > -1) {
		rowValues = selectedValue.split("-");
	} else {
		rowValues[0] = selectedValue;
	}
	var relationsCount = relations.length;
	for (var i = 0; i < relationsCount; i++) {
		relations[i] = trim(relations[i]);
		if (relations[i] == null || relations[i] == "") {
			continue;
		}
		value = rowValues[i];
		if (i >= rowValues.length) {
			break;
		}
		var field = null;
		var relation = parseInt(relations[i], 10);
		if (isNaN(relation)) {
			field = eval("openerFm." + relations[i]);
		} else {
			field = openerFm.elements[fieldIndex + relation];
			if (relation == 0) {
				field.focus();
			}
		}
		field.value = value;
	}
	document.getElementById("hospitalList").style.display = "none";
}

function limouseover(obj) {
	obj.style.background = "Blue";
	obj.style.color = "#FFFFFF";
}

function limouseout(obj) {
	obj.style.background = "";
	obj.style.color = "";
}
//验证医院是否存在

function isHospital(field, codeType) { 
	setTimeout(function () {
		var hospitalCode = "";
		var hospitalName = "";
		if (field.value == "") {
			return;
		}
		if (codeType == "codeName") {
			hospitalName = field.value;
		} else {
			hospitalCode = field.value;
		}
		var url = contextRootPath + "/common/verificationHospital.do";
		$.ajax({
			type: "get",
			url: url,
			cache: false,
			dataType: "text",
			data: "hospitalCode=" + hospitalCode + "&hospitalName=" + hospitalName,
			success: function (data) {
				if ("true" != trim(data)) {
					field.value = "";
				}
			}
		});
	}, 1000)
}
$(function () {
	$("div").click(hidePopdiv);
});