//创建求XMLHttpRequest对象

function createRequest(field, codeType, relation, bankLevel, upperBankCode, upperBankName) {
	var bankCode = "";
	var bankName = "";
	if (codeType == "codeName") {
		bankName = $.trim(field.value);
	} else {
		bankCode = $.trim(field.value);
	}
	upperBankCode = ""; //由于把总行代码和名称改成了只读，所以总行值不带入查询条件中
	upperBankName = "";
	var url = contextRootPath + "/common/autoBankAction.do";
	$.ajax({
		type: "get",
		url: url,
		cache: true,
		dataType: "text",
		data: {bankCode : bankCode , bankName : bankName , bankLevel : bankLevel , upperBankCode : upperBankCode , upperBankName : upperBankName},
		success: function (data) {
			if (data.length > 0) {
				popdiv(data, field, codeType, relation); //添加option选项
			} else {
				var odiv = document.getElementById("bankList");
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
//bankLevel 1代表总行，2代表分行
//获取医院的列表alert("\u5df2\u5230\u7b2c\u4e00\u9875");
var fieldIndex = 0;
var upperBankCode = "";
var upperBankName = "";

function getBank(field, codeType, relation, bankLevel) {
	fieldIndex = $("form input").index(field);
	if ($.trim(field.value) == "") {
		var res = relation.split(",");
		var $input = $("form input");
		$.each(res , function(i , re){
			$input.eq(fieldIndex + parseInt(re , 10)).val("");
		});
	}
	if ($.trim(field.value).length >= 1) { //由于把总行代码和名称改成了只读，所以永远只有一种查询方式
		createRequest(field, codeType, relation, bankLevel);
	} else {
		//隐藏弹出的body
		hidePopbody();
	}
}

//弹出DIV显示列表

function popdiv(stxt, field, codeType, relation) {
	var odiv = document.getElementById("bankList");
	var owidth = $(odiv).width();
	var oleft = findPosX(field);
	var otop = findPosY(field);
	var wwidth = $(window).width();
	if(oleft + owidth > wwidth){
		oleft = oleft + $(field).width() - owidth - 8;
	}
	odiv.style.left = oleft;
	odiv.style.top = otop + 22;
	if (trim(stxt) != "") {
		var jsonObj = eval(stxt);
		odiv.innerHTML = getlistring(trim(stxt), codeType, relation);
		odiv.style.height = "auto";
		odiv.style.display = "block";
		var h = $(odiv).height();
		if(jsonObj.length > 10){
			$(odiv).height((((h-10)*10)/jsonObj.length)+10);
		}
	} else {
		hidePopbody(); //隐藏弹出的body
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
//隐藏弹出的body

function hidePopbody() {
	var odiv = document.getElementById("bankList");
	if (odiv.style.display != "none") {
		odiv.innerHTML = "";
		odiv.style.display = "none";
	}
}

//获取列表的HTML代码

function getlistring(stxt, codeType, relation) {
	var jsonObj = eval(stxt); // JSON字符串转JSON对象
	var str = "<ul id='ultxt' style='list-style-type:none;padding:0;margin:0'>";
	var s1 = "<li onmousemove=\"limouseover(this)\" onmouseout=\"limouseout(this)\" onclick=\"liselect(this,'" + codeType + "','" + relation + "')\">";
	var s2 = "</li>";
	for (i = 0; i < jsonObj.length; i++) {
		var upperStr = "";
		//隐藏得到的upperBankCode upperBankName
		upperStr = "<input type='hidden' name='upperCode' value='" + jsonObj[i].upperBankCode + "' />" + "<input type='hidden' name='upperName' value='" + jsonObj[i].upperBankName + "' />"
		upperStr += "<input type='hidden' name='bankCode' value='" + jsonObj[i].bankCode + "' />" + "<input type='hidden' name='bankName' value='" + jsonObj[i].bankName + "' />"
		str = str + s1 + jsonObj[i].bankCode + "-" + jsonObj[i].bankName + "(" + jsonObj[i].bankShortName + ")" + upperStr + s2;
	}
	str = str + "</ul>";
	return str;
}

function liselect(obj, codeType, relation) {
	var params = new Array();
	params.push($(obj).find("input[name='bankCode']").val());
	params.push($(obj).find("input[name='bankName']").val());
	params.push($(obj).find("input[name='upperCode']").val());
	params.push($(obj).find("input[name='upperName']").val());
	var res = relation.split(",");
	var $input = $("form input");
	$.each(params , function(i , p){
		if(res[i]){
			$input.eq(fieldIndex + parseInt(res[i] , 10)).val(params[i]);
		}
	});
	document.getElementById("bankList").style.display = "none";
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

function isBank(field, codeType, bankLevel) {
	setTimeout(function () {
		var bankCode = "";
		var bankName = "";
		if ($.trim(field.value) == "") {
			return;
		}
		if (codeType == "codeName") {
			bankName = $.trim(field.value);
		} else {
			bankCode = $.trim(field.value);
		}
		var url = contextRootPath + "/common/verificationBank.do";
		$.ajax({
			type: "get",
			url: url,
			cache: true,
			dataType: "text",
			data: {bankCode : bankCode , bankName : bankName , bankLevel : bankLevel },
			success: function (data) {
				if ("true" != trim(data)) {
					field.value = "";
				}
			}
		});
	}, 500)
}

$(function () {
	$("body").click(hidePopbody);
});
//检查是否录入了总行代码和总行名称，没有录入就不让录分行

function checkUpperBank(field, bankLevel) {
	if ((fm.actionType.value == "AccountAddCompensate" || fm.actionType.value == "AccountAdd") && bankLevel == "2" && (trim(fm.prpdpaymentaccountBankCode.value) == "" || trim(fm.prpdpaymentaccountBankName.value) == "")) {
		alert("必須先錄入總行代碼和總行名稱！ ");
		fm.prpdpaymentaccountCustomBankCode.value = "";
		fm.prpdpaymentaccountCustomBankName.value = "";
		return false;
	} else if (fm.actionType.value == "queryUserCom" && bankLevel == "2") {
		var order = getElementOrder(field) - 1;
		var bankCode = document.getElementsByName("prpdpaymentaccountBankCode")[order].value;
		var bankName = document.getElementsByName("bankCode")[order].value;
		if (bankLevel == "2" && (trim(bankCode) == "" || trim(bankName) == "")) {
			alert("必須先錄入總行代碼和總行名稱！ ");
			document.getElementsByName("prpdpaymentaccountCustomBankCode")[order].value = "";
			document.getElementsByName("prpdpaymentaccountCustomBankName")[order].value = "";
			return false;
		}
	} else if (fm.actionType.value == "queryUser" && bankLevel == "2") {
		var order = getElementOrder(field) - 1;
		var bankCode = document.getElementsByName("prpdpaymentaccountBankCode")[order].value;
		var bankName = document.getElementsByName("prpdpaymentaccountBankName")[order].value;
		if (bankLevel == "2" && (trim(bankCode) == "" || trim(bankName) == "")) {
			alert("必須先錄入總行代碼和總行名稱！ ");
			document.getElementsByName("prpdpaymentaccountCustomBankCode")[order].value = "";
			document.getElementsByName("prpdpaymentaccountCustomBankName")[order].value = "";
			return false;
		}
	}
}
