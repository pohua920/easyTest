
function checkViewModel() {
	if (document.getElementById("editType").value == "view") {
		var inputs = document.getElementsByTagName("input");
		for (var i = 0; i < inputs.length; i++) {
			if (inputs[i].className.indexOf("except") == -1) {
				inputs[i].setAttribute("disabled", "disabled");
			}
			//inputs[i].removeAttribute("onclick");
		}
		var selects = document.getElementsByTagName("select");
		for (var i = 0; i < selects.length; i++) {
			if (selects[i].className.indexOf("except") == -1) {
				selects[i].setAttribute("disabled", "disabled");
			}
		}
	}
}
YAHOO.util.Event.onDOMReady(checkViewModel);
function changeCitycode(province) {
	var cityCodeSlectSpan = document.getElementById("cityCodeSlectSpanId");
	var sUrl = "${ctx}/dictionary/getAreaCode.do?ParentCode=" + province.value;
	var handleSuccess = function (resonpse) {
		var pre = "<select name='prpdRegulation.cityCode' id='proviceCode' onchange='changeCountycode(this)' style='width: 150px;'><option value=''>\u8bf7\u9009\u62e9</option>";
		var tail = "</select>";
		cityCodeSlectSpan.innerHTML = pre + resonpse.responseText + tail;
		var countyCodeSlectSpan = document.getElementById("countyCodeSlectSpanId");
		countyCodeSlectSpan.innerHTML = "<select name='prpdRegulation.countyCode' style='width: 150px;'><option value=''>\u8bf7\u9009\u62e9</option></select>";
	};
	var handleFailure = function (resonpse) {
		alert("\u83b7\u53d6\u4ee3\u7801\u5931\u8d25");
	};
	var callback = {success:handleSuccess, failure:handleFailure};
	var transaction = YAHOO.util.Connect.asyncRequest("POST", sUrl, callback, "");
}
function changeCountycode(city) {
	var countyCodeSlectSpan = document.getElementById("countyCodeSlectSpanId");
	var sUrl = "${ctx}/dictionary/getAreaCode.do?ParentCode=" + city.value;
	var handleSuccess = function (resonpse) {
		//modify duanfa 20110728 start 修改市的时候，地区的默认选择为空
		//var pre = "<select name='prpdRegulation.countyCode' style='width: 150px;'>";
		var pre = "<select name='prpdRegulation.countyCode' style='width: 150px;'><option value=''>\u8bf7\u9009\u62e9</option>";
		//modify duanfa 20110728 end
		var tail = "</select>";
		countyCodeSlectSpan.innerHTML = pre + resonpse.responseText + tail;
	};
	var handleFailure = function (resonpse) {
		alert("\u83b7\u53d6\u4ee3\u7801\u5931\u8d25");
	};
	var callback = {success:handleSuccess, failure:handleFailure};
	var transaction = YAHOO.util.Connect.asyncRequest("POST", sUrl, callback, "");
}

//add by duanfa20110825
function checkInput(){
	YAHOO.quote.data.datacheck("fm");
}
function checkForm() {
	//modify by duanfa20110823
	if(document.getElementsByName("prpdRegulation.countyCode")[0].value==''){
		alert("适用范围必须选择");
		return false;
	}
	if(YAHOO.quote.data.datacheck("fm")){
		document.getElementById("Sub_button").disabled=true;
		return true;
	}
	return false;
}
function checkNumber(input) {
	var number = input.value;
	if (number >= 0 && number <= 100) {
	} else {
		alert("\u683c\u5f0f\u9519\u8bef,\u57280-100\u4e4b\u95f4");
		input.value = "";
	}
}
function updateImage() {
	var busiNum = document.getElementById("fileCode").value;
	var url = "../image/getRequestXml.do?busi_num=" + busiNum;
	var handleSuccess = function (o) {
		var reqXml = o.responseText;
		window.open("  http://10.0.13.89:5211/SunECM/ImageScanUpdateAction.action?xml=" + reqXml, "image");
	};
	var handleFailure = function (o) {
		if (o.responseText !== undefined) {
			var msg = i18n.errors.deletefail + "!\n" + o.status + " " + o.statusText;
			alert("\u83b7\u53d6\u8bf7\u6c42xml\u5931\u8d25\uff01");
		}
	};
	var callback = {success:handleSuccess, failure:handleFailure};
	var callback = {success:handleSuccess, failure:handleFailure};
	var req = YAHOO.util.Connect.asyncRequest("POST", url, callback, "");
}
function viewImage(busiNum) {
	var url = "../image/getRequestXml.do?busi_num=" + busiNum;
	var handleSuccess = function (o) {
		var reqXml = o.responseText;
		window.open("http://10.0.13.89:5211/SunECM/ImageQueryAction.action?xml=" + reqXml, "image");
	};
	var handleFailure = function (o) {
		if (o.responseText !== undefined) {
			var msg = i18n.errors.deletefail + "!\n" + o.status + " " + o.statusText;
			alert("\u83b7\u53d6\u8bf7\u6c42xml\u5931\u8d25\uff01");
		}
	};
	var callback = {success:handleSuccess, failure:handleFailure};
	var callback = {success:handleSuccess, failure:handleFailure};
	var req = YAHOO.util.Connect.asyncRequest("POST", url, callback, "");
}
//modify by duanfa20110825 start 必须选中列，否则提示 ,单条审批可以通过
function passAll(regulationForm,flag) {
	var chkbox = document.getElementsByName('regulationCode');
	var selectVal = document.getElementById('commentSelect').value;
    var count=0;
    for(var i=0;i<chkbox.length;i++){
        if(chkbox[i].checked){
            count++;
        }
    }
	if (count == 0&&flag) {
		alert("没有选中列！");
	}else if(selectVal=='退回处理'){
		alert('与审核片语不符');
	}else{
		regulationForm.submit();
	}
}

function rejectAll(regulationForm,flag) {
	var chkbox = document.getElementsByName('regulationCode');
	var selectVal = document.getElementById('commentSelect').value;
    var count=0;
    for(var i=0;i<chkbox.length;i++){
        if(chkbox[i].checked){
            count++;
        }
    }
	if (count == 0&&flag) {
		alert("没有选中列！");
	}else if(selectVal=='通过'){
		alert('与审核片语不符');
	}else{
		regulationForm.action = "checkRejectRegulation.do";
		regulationForm.submit();
	}
}
//modify by duanfa20110825 end
function changeComments(selectDom,inputId){
	textArea = document.getElementById(inputId);
	textArea.value=selectDom.value;
}
//add by duanfa20110729
function colseWin(){
	window.open('','_parent','');
   window.close(); 
}