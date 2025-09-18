<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "測試";
String image = path + "/" + "images/";
String GAMID = "APS998E0";
String mDescription = "測試";
String nameSpace = "/aps/998";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
			
	function form_submit(type){
		 //$("label").html('');
		 if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		 if("query2" == type){
			 $("#mainForm").attr("action","btnQuery2.action");
			 $("#mainForm").submit();
		}
		 if("query3" == type){
			 $("#mainForm").attr("action","btnQuery3.action");
			 $("#mainForm").submit();
		}
		if("cancel" == type){
			$("#clearForm").submit();
		}		
	}
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
	
	function ajaxAction(action,type){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = contextPath + '/aps/ajax002/stage1.action';
		if(action == 'stage2'){
			path = contextPath + '/aps/ajax002/stage2.action';
		}
		var resultChallenge = $("#ResultChallenge").val();
		
		//執行ajax********************
		$.ajax({
			url : path,
			type: 'POST',
			data: {challenge:resultChallenge},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				ajaxError(data, status);
			},
			success: function (data, status){
				ajaxSuccess(action, data);
			}
		});
	}
	
	function ajaxSuccess(action, data){
		//ajax回傳的data存在，將ajax回傳的data設定至頁面上
		if(data.isExist == 'true'){
			if(action == 'stage1'){
				var b64TACert = data.b64TACert;
				$("#ATCert").val(b64TACert);
				var b64DVCert = data.b64DVCert;
				$("#DVCert").val(b64DVCert);
			}else if(action == 'stage2'){
				var sigB64 = data.sigB64;
				$("#SignedData").val(sigB64);
			}
		}else{//ajax回傳的data不存在
			if(action == 'stage1'){
				$("#ATCert").val("");
				$("#DVCert").val("");
			}else if(action == 'stage2'){
				$("#SignedData").val("");
			}
		}
	}
	
	function ajaxError(data, status){
		alert('無資料');
	}
</script>
</head>
<body style="margin:0;text-align:left">

<span id="SendObject" ></span><!--For IE-->
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>

<!-- clear form -->
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/998" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/998" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>測試作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right"></td>
			<td width="285px" align="left">
			<!--  
				<s:textfield key="calcDate" id="calcDate" maxlength="8" size="8" theme="simple" />YYYYMMDD
				-->
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left">
			<!--
				<s:textfield key="firPremcalcTmp.channelType" id="channelType" maxlength="2" size="2" theme="simple" />
				-->
			</td>			
		</tr>
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">
			請先選擇讀卡機再操作以下功能：(ListEID)
		</td>
		<td align="left">
			<select name="slotDescription" id="slotDescription" style="visibility: visible;">
				<option value="請選擇卡片">請選擇卡片</option>
			</select>
			<input type="button" name="DetectCard" id="DetectCard" value="偵測卡片">
		</td>
	</tr>
</table>
<div style="width:970px;">
<h1 style="text-align: center;">公開區</h1>
<table width="970px" cellpadding="1" cellspacing="0" border="0">
	<tr>
		<td width="200px" align="right">讀公開區：</td>
		<td width="285px" align="left">
			<input type="hidden" name="ResultEIDPublicRawData" id="ResultEIDPublicRawData">
				CAN <input id="CAN" name="CAN" type="text" value="" /> 或
				MRZ <input id="MRZ" name="MRZ" type="text" value="" />或
				證件號碼 <input id="cardNo" name="cardNo" type="text" value="" /><BR />
				<input type="checkbox" value="1" id="PubDG2" checked> (公)DG2
				<input type="checkbox" value="1" id="PubDG11" checked> (公)DG11
				<input type="checkbox" value="1" id="PubDG12" checked> (公)DG12
				<input type="button" value="讀取" name="GetEIDPublicData" id="GetEIDPublicData"
					onClick="GetEIDPublicData_onclick();">
		</td>
	</tr>
	<tr>
		<td width="200px" align="center" colspan="2">
			------------------------------------------------------------回傳訊息------------------------------------------------------------
		</td>
	</tr>
	<tr>
		<td width="200px" align="right">錯誤代碼：</td>
		<td width="285px" align="left">
			<input type="text" name="returnCodePublic" id="returnCodePublic" />
		</td>
	</tr>
	<tr>
		<td width="200px" align="right">內容：</td>
		<td width="285px" align="left">
				<TEXTAREA name="ResultEIDPublicData" id="ResultEIDPublicData" rows="15" cols="65"></TEXTAREA>
		</td>
	</tr>
	<tr>
		<td width="200px" align="right">照片：</td>
		<td width="285px" align="left">
				<span id = "PubPic"></span>
		</td>
	</tr>
	<!--  
	<tr>
		<td width="200px" align="right">原始資料：</td>
		<td width="285px" align="left">
				<TEXTAREA name="ResultEIDPublicRawData" id="ResultEIDPublicRawData" rows="8" cols="65"></TEXTAREA>
		</td>
	</tr>
	-->
</table>
</div>
<div style="width:970px;">
<h1 style="text-align: center;">加密區</h1>
<table width="970px" cellpadding="1" cellspacing="0" border="0">
	<!--  -
	<tr>
		<td width="200px" align="right">讀加密區(取得憑證)：</td>
		<td width="285px" align="left">
				ATCert: <INPUT name="ATCert" id="ATCert"
					value="" /><BR>
				DVCert: <INPUT name="DVCert" id="DVCert"
					value="" /><BR>
				<input type="button" value="GetStage1" name="GetStage1" id="GetStage1"
					onClick="ajaxAction('stage1','');">
		</td>
	</tr>
	-->
	<tr>
		<td width="200px" align="right">讀加密區(步驟1：輸入PIN1密碼)：</td>
		<td width="285px" align="left">
			<input type="hidden" name="ATCert" id="ATCert">
			<input type="hidden" name="DVCert" id="DVCert">
			<input type="hidden" name="returnCodePrivateDataStep1" id="returnCodePrivateDataStep1">
			<input type="hidden" name="ResultPrivateDataStep1" id="ResultPrivateDataStep1">
			
				Pin1: <INPUT name="pin1" id="pin1" type="password" value="" />
				<input type="button" value="驗證密碼" name="GetEIDPrivateData" id="GetEIDPrivateDataStep1"
					onClick="GetEIDPrivateData_onclick();">
		</td>
	</tr>
	<!--  
	<tr>
		<td width="200px" align="center" colspan="2">
			------------------------------------------------------------回傳訊息------------------------------------------------------------
		</td>
	</tr>
	<tr>
		<td width="200px" align="right">錯誤代碼：</td>
		<td width="285px" align="left">
			<input type="text" name="returnCodePrivateDataStep1" id="returnCodePrivateDataStep1" />
		</td>
	</tr>
	<tr>
		<td width="200px" align="right">結果：</td>
		<td width="285px" align="left">
			<TEXTAREA name="ResultPrivateDataStep1" id="ResultPrivateDataStep1" rows="8" cols="65"></TEXTAREA>
		</td>
	</tr>
	-->
	<!--  
	<tr>
		<td width="200px" align="right">讀加密區(取得第二階段憑證)：</td>
		<td width="285px" align="left">
				ResultChallenge(from output1): <INPUT name="ResultChallenge" id="ResultChallenge" readonly="true"/><br>
				<input type="button" value="GetStage2" name="GetStage2" id="GetStage2"
					onClick="ajaxAction('stage2','');">
		</td>
	</tr>
	-->
	<tr>
		<td width="200px" align="right">讀加密區(步驟2：取得資料)：</td>
		<td width="285px" align="left">
			<input type="hidden" name="ResultChallenge" id="ResultChallenge">
			<input type="hidden" name="handle" id="handle">
			<input type="hidden" name="SignedData" id="SignedData">
			<input type="hidden" name="ResultEIDPrivateRawData" id="ResultEIDPrivateRawData">
			<!--  
				handle(from output1): <INPUT name="handle" id="handle" /><BR>
				SignedData(from 機關端軟體): <INPUT name="SignedData" id="SignedData" /><BR>
				-->
				<input type="checkbox" value="1" name="DG3" id="DG3" checked> (加)DG3
				<input type="checkbox" value="1" name="DG4" id="DG4" checked> (加)DG4
				<input type="checkbox" value="1" name="DG5" id="DG5" checked> (加)DG5
				<input type="checkbox" value="1" name="DG6" id="DG6" checked> (加)DG6
				<input type="checkbox" value="1" name="DG7" id="DG7" checked> (加)DG7<br>
				<input type="checkbox" value="1" name="DG8" id="DG8" checked> (加)DG8
				<input type="checkbox" value="1" name="DG9" id="DG9" checked> (加)DG9
				<input type="checkbox" value="1" name="DG11" id="DG11" checked> (公)DG11
				<input type="checkbox" value="1" name="DG12" id="DG12" checked> (公)DG12
				<input type="checkbox" value="1" name="DG13" id="DG13" checked> (戶)DG13<br>
				<input type="button" value="取得資料" name="GetEIDPrivateDataStep2"
					id="GetEIDPrivateDataStep2" onClick="GetEIDPrivateDataStep2_onclick();">
		</td>
	</tr>
	<tr>
		<td width="200px" align="center" colspan="2">
			------------------------------------------------------------回傳訊息------------------------------------------------------------
		</td>
	</tr>
	<tr>
		<td width="200px" align="right">錯誤代碼：</td>
		<td width="285px" align="left">
			<input type="text" name="returnCodePrivateDataStep2" id="returnCodePrivateDataStep2" />
		</td>
	</tr>
	<tr>
		<td width="200px" align="right">結果：</td>
		<td width="285px" align="left">
			<TEXTAREA name="ResultPrivateData2" id="ResultPrivateData2" rows="30" cols="65"></TEXTAREA>
		</td>
	</tr>
	<tr>
		<td width="200px" align="right">照片：</td>
		<td width="285px" align="left">
				<span id = "PriPic"></span>
		</td>
	</tr>
	<!--  
	<tr>
		<td width="200px" align="right">原始資料：</td>
		<td width="285px" align="left">
				<TEXTAREA name="ResultEIDPrivateRawData" id="ResultEIDPrivateRawData" rows="8" cols="65"></TEXTAREA>
		</td>
	</tr>
	-->
</table>
</div>
</s:form>
<!-- form結束 -->
<script type="text/javascript" src="/APS/js/ChtICToken_SelectCard.js"></script>
<script type="text/javascript" charset="UTF-8">
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//選擇讀卡機
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	var g_selectSlot, g_DetectCard;
	var browserNotSupportMsg = "此瀏覽器不支援本功能，請升級至IE10以上或是使用Chrome、FireFox等瀏覽器";
	
	initHTML(); //初始化
	function initHTML() {
		//綁元件
		g_selectSlot = document.getElementById("slotDescription");//選擇讀卡機
		g_DetectCard = document.getElementById("DetectCard");
		//確定元件是否存在
		if (!g_DetectCard) {
			alert("g_DetectCard");
			return;
		}
		if (!g_selectSlot) {
			alert("g_selectSlot 元件不存在");
			return;
		} else {
			//g_selectSlot.style.visibility="hidden";
		}
		//偵測click下拉式選單：只有第一次click下拉式選單動作才去取讀卡機，之後就會取消，改成要按按鈕「偵測卡片」才行
		if (g_selectSlot.addEventListener) {
			g_selectSlot.addEventListener("click", DetectCard_click_function);
		} else if (g_selectSlot.attachEvent) { //IE8
			g_selectSlot.attachEvent("onclick", DetectCard_click_function);
		} else {
			alert(browserNotSupportMsg);
			return;
		}
		//偵測按按鈕「偵測卡片」
		if (g_DetectCard.addEventListener) {
			g_DetectCard.addEventListener("click", DetectCard_click_function);
		} else if (g_DetectCard.attachEvent) {//IE8
			g_DetectCard.attachEvent("onclick", DetectCard_click_function);
		} else {
			alert(browserNotSupportMsg);
			return;
		}
	}
	function DetectCard_click_function() {
		//偵測讀卡元件
		$("#slotDescription").empty();//清除select,以免重新偵測時又出現
		var l_oToken = getICToken();
		if (!l_oToken) {
			alert("讀卡元件不存在");
		} else {
			//初始化讀卡機選擇
			l_oToken.EIDgoodDay(initialCardReaders);
		}
	}
	//ListEid
	function initialCardReaders() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			var defaultOpt = document.createElement('option');
			defaultOpt.value = "請選擇卡片";
			defaultOpt.innerHTML = "請選擇卡片";
			defaultOpt.selected = false;
			g_selectSlot.appendChild(defaultOpt);
			var l_ReaderCount = l_oToken.countSlotID();
			//加入可以選擇的讀卡機
			for (var index = 0; index < l_ReaderCount; index++) {
				var l_ReaderName = l_oToken.getSlotName(index);
				var opt = document.createElement('option');
				opt.value = index;
				opt.innerHTML = l_ReaderName;
				g_selectSlot.appendChild(opt);
			}
			//偵測change下拉式選單選項：有取得讀卡機後，再加入onchange功能 (並移除偵測click)
			if (g_selectSlot.options[0].innerHTML == "請選擇卡片") { //若下拉式選單仍為初始內容才新增此EventListener
				if (g_selectSlot.addEventListener) {
					g_selectSlot.addEventListener("change", selectSlotChange_function);
					g_selectSlot.removeEventListener("click", DetectCard_click_function); //移除偵測click
				} else if (g_selectSlot.attachEvent) {//IE8
					g_selectSlot.attachEvent("onchange", selectSlotChange_function);
					g_selectSlot.detachEvent("onclick", DetectCard_click_function);
				} else {
					alert(browserNotSupportMsg);
					return;
				}
			}
		} else {
			alert("初始化IC卡失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
			if (l_oToken.RetObj.RCode == 2202 || l_oToken.RetObj.RCode == 3102 || l_oToken.RetObj.RMsg.indexOf("2200") >= 0) {
				window.location = pluginDownload;
				alert("請安裝或更新跨平台網頁元件");
			}
		}
	}
	function selectSlotChange_function() {
		if (g_selectSlot.options[g_selectSlot.selectedIndex].innerHTML != "請選擇卡片") {
			var l_oToken = getICToken();
			var l_Return = l_oToken.setActiveSlotID(g_selectSlot.options[g_selectSlot.selectedIndex].value);
			if (l_Return.RCode != 0) {
				alert("選擇讀卡機失敗! 錯誤碼：" + l_Return.RCode + ", 原因：" + l_Return.RMsg);
				return;
			}
		}
	}
	//base64 decode
	function b64DecodeUnicode(str) {
    return decodeURIComponent(atob(str).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//goodDay_onclick()()
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	function goodDay_onclick() {
		var l_oToken = getICToken();
		l_oToken.goodDay(goodDayMsg);
	}//function goodDay_onclick()(){
	function goodDayMsg() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			alert("初始化IC成功! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		} else {
			alert("初始化IC失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}//if (l_oToken.RetObj.RCode==0)
	}//function goodDayMsg()
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//getSmartCardID_onclick()
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	function getSmartCardID_onclick() {
		var l_oToken = getICToken();
		l_oToken.EIDgoodDay(getSmartCardIDDo);
	}//function getSmartCardID_onclick()
	function getSmartCardIDDo() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			//alert("初始化IC成功! 錯誤碼："+l_oToken.RetObj.RCode+", 原因："+l_oToken.RetObj.RMsg);
			var l_Return = l_oToken.setActiveSlotID(g_selectSlot.options[g_selectSlot.selectedIndex].value); //使用選擇的讀卡機
			if (l_Return.RCode == 0) {
				l_oToken.getSmartCardID(getSmartCardIDMsg);
			} else {
				alert("使用選擇的讀卡機失敗! 錯誤碼：" + l_Return.RCode + ", 原因：" + l_Return.RMsg);
				return;
			}
		} else {
			alert("初始化IC失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}//if (l_oToken.RetObj.RCode==0)
	}//function getSmartCardID(){
	function getSmartCardIDMsg() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			alert("取得卡號：" + l_oToken.RetObj.CardID);
		} else {
			alert("讀取卡號失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}//if (l_oToken.RetObj.RCode==0)
	}//function getSmartCardIDMsg()

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GetEIDPublicData_onclick()
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	function GetEIDPublicData_onclick() {
		document.getElementById("ResultEIDPublicData").value = "";
		$("#PubPic").html("");
		var l_oToken = getICToken();
		l_oToken.EIDgoodDay(GetEIDPublicDataDo);
	}
	function GetEIDPublicDataDo() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			var l_Return = l_oToken.setActiveSlotID(g_selectSlot.options[g_selectSlot.selectedIndex].value);
			//使用選擇的讀卡機
			if (l_Return.RCode == 0) {
				//var dgData = ["DG2","DG11","DG12"];
				var dgData = [];
				if (document.getElementById("PubDG11").checked) {
					dgData.push("DG11");
				}
				if (document.getElementById("PubDG12").checked) {
					dgData.push("DG12");
				}
				if (document.getElementById("PubDG2").checked) {
					dgData.push("DG2");
				}
				l_oToken.getEIDPublicData(document.getElementById("CAN").value, document.getElementById("MRZ").value
					,document.getElementById("cardNo").value, dgData, GetEIDPublicDataMsg);
			} else {
				alert("使用選擇的讀卡機失敗! 錯誤碼：" + l_Return.RCode + ", 原因：" + l_Return.RMsg);
				return;
			}
		} else {
			alert("初始化IC失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}
	}

	function GetEIDPublicDataMsg() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			document.getElementById("returnCodePublic").value = l_oToken.RetObj.RCode;
			console.log("l_oToken.RetObj.DG ",l_oToken.RetObj.DG );
			if (document.getElementById("PubDG11").checked) {
				document.getElementById("ResultEIDPublicData").value = "中文姓名:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.name) +"\n"
				+"外文姓名:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.nameForeign)+"\n"
				+"統一編號:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.rocID)+"\n"
				+"出生日期(西元):"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.birthday)+"\n"
				+"結婚狀態:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.marriageStatus)+"\n"
				+"證件號碼:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.cardNum)+"\n"
				+"應換領日期(西元):"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.renewalDate)+"\n"
				+"製證日期(西元):"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.manufactureDate)+"\n"
				+"驗證資訊:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.certificationInfo)+"\n"
				+"姓名羅馬拼音:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.nameRoman)+"\n";
			}
			if (document.getElementById("PubDG12").checked) {
				document.getElementById("ResultEIDPublicData").value = document.getElementById("ResultEIDPublicData").value 
				+"役別:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg12.militaryService)+"\n"
				+"戶籍地址:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg12.residenceAddress)+"\n";
			}
			if (document.getElementById("PubDG2").checked) {
				var image = new Image();
				image.src = 'data:image/png;base64,'+l_oToken.RetObj.DG.dg2.pic300dpi;
				document.getElementById("PubPic").appendChild(image);
			}
			document.getElementById("ResultEIDPublicRawData").value = JSON.stringify(l_oToken.RetObj.eidRawdata);
			//alert("讀公開區成功!");
		} else {
			alert("讀公開區失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GetEIDHouseholdData_onclick()
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	function GetEIDHouseholdData_onclick() {
		var l_oToken = getICToken();
		l_oToken.EIDgoodDay(GetEIDHouseholdDataDo);
	}
	function GetEIDHouseholdDataDo() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			var l_Return = l_oToken.setActiveSlotID(g_selectSlot.options[g_selectSlot.selectedIndex].value); 
			//使用選擇的讀卡機
			if (l_Return.RCode == 0) {
				l_oToken.getEIDHouseHoldData(GetEIDHouseholdDataMsg);
			} else {
				alert("使用選擇的讀卡機失敗! 錯誤碼：" + l_Return.RCode + ", 原因：" + l_Return.RMsg);
				return;
			}
		} else {
			alert("初始化IC失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}
	}
	function GetEIDHouseholdDataMsg() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			console.log("l_oToken.RetObj " + JSON.stringify(l_oToken.RetObj));
			document.getElementById("returnCodeHousehold").value = l_oToken.RetObj.RCode;
			document.getElementById("ResultEIDHouseholdData").value = 
			"戶籍地址(到村里):" + b64DecodeUnicode(l_oToken.RetObj.DG.dg13.residenceAddress1) 
			+"\n戶籍地址(鄰):"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg13.residenceAddress2) 
			+"\n戶籍地址(到村里鄰）:" + b64DecodeUnicode(l_oToken.RetObj.DG.dg13.residenceAddress3) +"\n";
			alert("讀戶籍區成功!");
		} else {
			alert("讀戶籍區失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//GetEIDPrivateData_onclick()
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	function GetEIDPrivateData_onclick() {
		
		ajaxAction('stage1','');
		var l_oToken = getICToken();
		l_oToken.EIDgoodDay(GetEIDPrivateDataStep1Do);
	}
	function GetEIDPrivateDataStep1Do() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			var l_Return = l_oToken.setActiveSlotID(g_selectSlot.options[g_selectSlot.selectedIndex].value); 
			//使用選擇的讀卡機
			if (l_Return.RCode == 0) {
				l_oToken.getEIDPrivateDataStep1(document.getElementById("pin1").value, document.getElementById("DVCert").value,
					document.getElementById("ATCert").value, GetEIDPrivateDataStep1Msg);
			} else {
				alert("讀加密區步驟一失敗! 錯誤碼：" + l_Return.RCode + ", 原因：" + l_Return.RMsg);
				return;
			}
		} else {
			alert("初始化IC失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}
	}
	function GetEIDPrivateDataStep1Msg() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			console.log("l_oToken.RetObj " + JSON.stringify(l_oToken.RetObj));
			document.getElementById("returnCodePrivateDataStep1").value = l_oToken.RetObj.RCode;
			document.getElementById("ResultPrivateDataStep1").value = JSON.stringify(l_oToken.RetObj);
			document.getElementById("ResultChallenge").value = l_oToken.RetObj.Challenge;
			document.getElementById("handle").value = l_oToken.RetObj.Handle;
			alert("讀加密區1成功!");
		} else {
			alert("讀加密區1失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}
	}
	function GetEIDPrivateDataStep2_onclick() {
		document.getElementById("ResultPrivateData2").value = "";
		$("#PriPic").html("");
		ajaxAction('stage2','');
		var l_oToken = getICToken();
		var l_Return = l_oToken.setActiveSlotID(g_selectSlot.options[g_selectSlot.selectedIndex].value); 
		//使用選擇的讀卡機
		if (l_Return.RCode == 0) {
			//var dgData =["SOD","DG3","DG4","DG5","DG6","DG7","DG8","DG9","DG11","DG12","DG13"];
			
			var dgData = [];
			dgData.push("SOD");
			if (document.getElementById("DG3").checked) {
				dgData.push("DG3");
			}
			if (document.getElementById("DG4").checked) {
				dgData.push("DG4");
			}
			if (document.getElementById("DG5").checked) {
				dgData.push("DG5");
			}
			if (document.getElementById("DG6").checked) {
				dgData.push("DG6");
			}
			if (document.getElementById("DG7").checked) {
				dgData.push("DG7");
				}
			if (document.getElementById("DG8").checked) {
				dgData.push("DG8");
			}
			if (document.getElementById("DG9").checked) {
				dgData.push("DG9");
			}
			if (document.getElementById("DG11").checked) {
				dgData.push("DG11");
			}
			if (document.getElementById("DG12").checked) {
				dgData.push("DG12");
			}
			if (document.getElementById("DG13").checked) {
				dgData.push("DG13");
			}
			
			l_oToken.getEIDPrivateDataStep2(dgData, document.getElementById("SignedData").value
				, document.getElementById("handle").value, GetEIDPrivateDataStep2Msg);
		} else {
			alert("讀加密區步驟二失敗! 錯誤碼：" + l_Return.RCode + ", 原因：" + l_Return.RMsg);
			return;
		}
	}
	function GetEIDPrivateDataStep2Msg() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			document.getElementById("returnCodePrivateDataStep2").value = l_oToken.RetObj.RCode;
			if (document.getElementById("DG3").checked) {
				if(l_oToken.RetObj.DG.dg3.spouseName!=null){
					document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"配偶姓名:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg3.spouseName)+"\n";
				}
				if(l_oToken.RetObj.DG.dg3.spouseNameRoman!=null){
					document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"配偶姓名羅馬拼音:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg3.spouseNameRoman)+"\n";
				}
			}
			if (document.getElementById("DG4").checked) {
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"性別:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg4.sex)+"\n";
			}
			if (document.getElementById("DG6").checked) {
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"父欄位名:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg6.dadNameField)+"\n";
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"父姓名:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg6.dadName)+"\n";
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"父姓名羅馬拼音:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg6.dadNameRoman)+"\n";
			}
			if (document.getElementById("DG7").checked) {
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"母欄位名:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg7.momNameField)+"\n";
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"母姓名:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg7.momName)+"\n";
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"母姓名羅馬拼音:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg7.momNameRoman)+"\n";
			}
			if (document.getElementById("DG8").checked) {
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"出生地代碼:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg8.birthPlaceCode)+"\n";
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"出生地:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg8.birthPlace)+"\n";
			}
			if (document.getElementById("DG9").checked) {
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"證件號碼後六碼:"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg9.can)+"\n";
			}
			if (document.getElementById("DG11").checked) {
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"中文姓名:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.name) +"\n"
				+"外文姓名:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.nameForeign)+"\n"
				+"統一編號:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.rocID)+"\n"
				+"出生日期(西元):"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.birthday)+"\n"
				+"結婚狀態:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.marriageStatus)+"\n"
				+"證件號碼:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.cardNum)+"\n"
				+"應換領日期(西元):"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.renewalDate)+"\n"
				+"製證日期(西元):"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.manufactureDate)+"\n"
				+"驗證資訊:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.certificationInfo)+"\n"
				+"姓名羅馬拼音:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg11.nameRoman)+"\n";
			}
			if (document.getElementById("DG12").checked) {
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"役別:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg12.militaryService)+"\n"
				+"戶籍地址:"+b64DecodeUnicode(l_oToken.RetObj.DG.dg12.residenceAddress)+"\n";
			}
			if (document.getElementById("DG13").checked) {
				document.getElementById("ResultPrivateData2").value = document.getElementById("ResultPrivateData2").value 
				+"戶籍地址(到村里):" + b64DecodeUnicode(l_oToken.RetObj.DG.dg13.residenceAddress1) +"\n戶籍地址(鄰):"+ b64DecodeUnicode(l_oToken.RetObj.DG.dg13.residenceAddress2) +"\n戶籍地址(到村里鄰）:" + b64DecodeUnicode(l_oToken.RetObj.DG.dg13.residenceAddress3) +"\n";
			}
			if (document.getElementById("DG5").checked) {
				var image = new Image();
				image.src = 'data:image/png;base64,'+l_oToken.RetObj.DG.dg5.pic600dpi;
				document.getElementById("PriPic").appendChild(image);
			}
			//document.getElementById("ResultPrivateData2").value = JSON.stringify(l_oToken.RetObj.DG);
			document.getElementById("ResultEIDPrivateRawData").value = JSON.stringify(l_oToken.RetObj.eidRawdata);
			alert("讀加密區成功!");
		} else {
			alert("讀加密區失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}
	}
	
	//////ListINFO_onclick()
	function ListINFO_onclick() {
		var l_oToken = getICToken();
		l_oToken.goodDay(EEgetSignatureCert);
	}
	function EEgetSignatureCert() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			alert("初始化IC成功! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
			//讀憑證(1) Signature
			l_oToken.getB64Certificate(1, EEgetEncCert);
		} else { alert("讀取卡號失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg); }
	}
	function EEgetEncCert() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			alert("Certificate(1 Signature)=" + l_oToken.RetObj.Certificate + " >>>> Return code=" + l_oToken.RetObj.RCode);
			document.getElementById("CertSig").value = l_oToken.RetObj.Certificate;
			//讀憑證(2) Enc
			l_oToken.getB64Certificate(2, EESinSHA256);
		} else {
			alert("讀取IC卡憑證1失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}
	}
	function EESinSHA256() {
		var l_oToken = getICToken();
		if (l_oToken.RetObj.RCode == 0) {
			document.getElementById("CertEnc").value = l_oToken.RetObj.Certificate;
		} else {
			alert("讀取IC卡憑證1失敗! 錯誤碼：" + l_oToken.RetObj.RCode + ", 原因：" + l_oToken.RetObj.RMsg);
		}
	}
</script>
</body>
</html>