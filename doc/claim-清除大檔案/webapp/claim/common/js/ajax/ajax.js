var http_request = null;

function send_request(url, value) { // 初始化、指定处理函数、发送请求的函数
	// 开始初始化XMLHttpRequest对象
	if (window.XMLHttpRequest) { // Mozilla 浏览器
		http_request = new XMLHttpRequest();　　　
		if (http_request.overrideMimeType) { // 设置MiME类别
			
			http_request.overrideMimeType("text/xml");
		}
	} else if (window.ActiveXObject) { // IE浏览器
		
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {}
		}
	}
	// 异常，创建对象实例失败
	if (!http_request) {
		window.alert(i18n.js.cannotCreateObject); //不能创建XMLHttpRequest对象实例.
		
		return false;
	}
	// 确定发送请求的方式和URL以及是否同步执行下段代码
	http_request.open("POST", url, true);
	http_request.send(null);

	http_request.onreadystatechange = function () {
		if (http_request.readyState == 4) { // 判断对象状态
			
			if (http_request.status == 200) { // 信息已经成功返回，开始处理信息
				
				var info = http_request.responseText;
				if (value == -1) {
					if (info != "" && info != null && info != undefined && info != "false") {
						var count = document.getElementsByName("nextHandlerCode").length;//定损人员
						var strTemp = info.split(";");
						fm.nextHandlerCode1.value = strTemp[0];
						fm.nextHandlerName1.value = strTemp[1];
						if(document.getElementsByName("prpLscheduleMainWFScheduleObjectID").value==""){
							fm.prpLscheduleMainWFScheduleObjectID.value = strTemp[2];
						}
						if(document.getElementsByName("prpLscheduleMainWFScheduleObjectName").value==""){
							fm.prpLscheduleMainWFScheduleObjectName.value = strTemp[3];
						}
						fm.checkScheduleCheckYesNo.checked = true;
						for (var i = 0; i < count; i++) {
							document.getElementsByName("nextHandlerCode")[i].value = strTemp[0];
							document.getElementsByName("nextHandlerName")[i].value = strTemp[1];
							document.getElementsByName("prpLscheduleItemScheduleObjectID")[i].value = fm.prpLscheduleMainWFScheduleObjectID.value;
							document.getElementsByName("prpLscheduleItemScheduleObjectName")[i].value = fm.prpLscheduleMainWFScheduleObjectName.value;
							document.getElementsByName("checkYesNo")[i].checked = true;
						}
					} else if (info == "false") {
						alert(i18n.js.employeeNotPermission); //该员工没有权限
						fm.reset();
						fm.btnCheckText.onClick = generateCheckText();
					} else { // 输入的工号不对时,清空所有
						var count = document.getElementsByName("nextHandlerCode").length;
						fm.nextHandlerCode1.value = "";
						fm.nextHandlerName1.value = "";
						//fm.prpLscheduleMainWFScheduleObjectID.value = "";
						//fm.prpLscheduleMainWFScheduleObjectName.value = "";
						for (var i = 0; i < count; i++) {
							document.getElementsByName("nextHandlerCode")[i].value = "";//查勘人员
							document.getElementsByName("nextHandlerName")[i].value = "";
							//document.getElementsByName("prpLscheduleItemScheduleObjectID")[i].value = "";
							//document.getElementsByName("prpLscheduleItemScheduleObjectName")[i].value = "";
						}
					}
				} else {
					var nextNodeNo = document.getElementsByName("getbackNodeType")[0].value;
					if (info != "" && info != null && info != undefined && info != "false") {
						var count = document.getElementsByName("nextHandlerCode").length;
						var strTemp = info.split(";");
						if(value == 0&&nextNodeNo=="check"){
							var nextNodeNo = document.getElementsByName("getbackNodeType")[0].value;
							document.getElementsByName("nextHandlerCode1")[value].value = strTemp[0];
							document.getElementsByName("nextHandlerName1")[value].value = strTemp[1];
							if(document.getElementsByName("prpLscheduleMainWFScheduleObjectID")[value].value==""){
								document.getElementsByName("prpLscheduleMainWFScheduleObjectID")[value].value = strTemp[2];
							}
							if(document.getElementsByName("prpLscheduleMainWFScheduleObjectName")[value].value==""){
								document.getElementsByName("prpLscheduleMainWFScheduleObjectName")[value].value = strTemp[3];
							}
							//document.getElementsByName("checkYesNo")[value].checked = true;
						}else {
							document.getElementsByName("nextHandlerCode")[value].value = strTemp[0];
							document.getElementsByName("nextHandlerName")[value].value = strTemp[1];
							if(document.getElementsByName("prpLscheduleItemScheduleObjectID")[value].value==""){
								document.getElementsByName("prpLscheduleItemScheduleObjectID")[value].value = strTemp[2];
							}
							if(document.getElementsByName("prpLscheduleItemScheduleObjectName")[value].value==""){
								document.getElementsByName("prpLscheduleItemScheduleObjectName")[value].value = strTemp[3];
							}
							document.getElementsByName("checkYesNo")[value].checked = true;
						}
					} else if (info == "false") {
						alert(i18n.js.employeeNotPermission); //该员工没有权限
						if(value==0&&nextNodeNo=="check"){
							document.getElementsByName("nextHandlerCode1")[value].value = strTemp[0];
							document.getElementsByName("nextHandlerName1")[value].value = strTemp[1];
							if(document.getElementsByName("prpLscheduleMainWFScheduleObjectID")[value].value==""){
								document.getElementsByName("prpLscheduleMainWFScheduleObjectID")[value].value = strTemp[2];
							}
							if(document.getElementsByName("prpLscheduleMainWFScheduleObjectName")[value].value==""){
								document.getElementsByName("prpLscheduleMainWFScheduleObjectName")[value].value = strTemp[3];
							}
						}else{
							document.getElementsByName("nextHandlerCode")[value].value = "";
							document.getElementsByName("nextHandlerName")[value].value = "";
							document.getElementsByName("prpLscheduleItemScheduleObjectID")[value].value = "";
							document.getElementsByName("prpLscheduleItemScheduleObjectName")[value].value = "";
						}
					} else {
						if(value == 0&&nextNodeNo=="check"){
							document.getElementsByName("nextHandlerCode1")[value].value = "";
							document.getElementsByName("nextHandlerName1")[value].value = "";
						}else{
							document.getElementsByName("nextHandlerCode")[value].value = "";
							document.getElementsByName("nextHandlerName")[value].value = "";
						}
						//document.getElementsByName("prpLscheduleItemScheduleObjectID")[value].value = "";
						//document.getElementsByName("prpLscheduleItemScheduleObjectName")[value].value = "";
					}
				}
			} else { // 页面不正常
				alert(i18n.js.pageError); //您所请求的页面有异常。
			}
		}
	}
}

function queryByUserCode(field, saveType) {
	var inputname = field.name;//
	var nextNodeNo = "";
	var $comcode;
	var $comcname;
	var $usercode;
	var $username;
	if(inputname == "nextHandlerCode1" || inputname == "nextHandlerName1"){// 查勘
		nextNodeNo = "check";
		$usercode = $(":input[name='nextHandlerCode1']");
		$username = $(":input[name='nextHandlerName1']");
		$comcode = $(":input[name='prpLscheduleMainWFScheduleObjectID']");
		$comcname = $(":input[name='prpLscheduleMainWFScheduleObjectName']");
	} else if(inputname == "nextHandlerCode" || inputname == "nextHandlerName" ){//定損
		var index = $(":input[name='"+inputname+"']").index(field);// 取得當前元素的索引
		nextNodeNo = $(":input[name='nextNodeNo']").eq(index).val();
		$usercode = $(":input[name='nextHandlerCode']");
		$username = $(":input[name='nextHandlerName']");
		$comcode = $(":input[name='prpLscheduleItemScheduleObjectID']");
		$comcname = $(":input[name='prpLscheduleItemScheduleObjectName']");
	}
	var nextHandlerCode = $.trim($usercode.val());
	var ScheduleObjectID = $.trim($comcode.val());
	if($.trim(nextNodeNo) != "" && ((/[A-Z]\d{4}/.test(nextHandlerCode)) || (/[A-Z]{2}\d{3}/.test(nextHandlerCode)))){ 
		$.ajax({
			url : contextRootPath + "/schedule/scheduleBeforeEdit.do?editType=ajaxQuery&nextNodeNo=" + nextNodeNo + "&nextHandlerCode=" + nextHandlerCode + "&ScheduleObjectID=" + ScheduleObjectID,
			type : "get",
			dataType :"json",
			async : false,
			cache : true,
			success :function(data){
				if(data.scheduleUserCode == ""){
					var desc = "查勘";
					if(nextNodeNo == "certa"){
						desc = "車輛定損";
					} else if(nextNodeNo == "wound"){
						desc = "人傷定損";
					} else if(nextNodeNo == "propc"){
						desc = "財產定損";
					}
					alert("用戶 "+nextHandlerCode + (ScheduleObjectID != "" ? " 在 "+ ScheduleObjectID + " 單位" : "" )+ "無 "+( desc )+" 任務處理權限！");
				}
				$usercode.val(data.scheduleUserCode);
				$username.val(data.scheduleUserName);
				$comcode.val(data.scheduleComcode);
				$comcname.val(data.scheduleComCName);
			}
		});
	}
}