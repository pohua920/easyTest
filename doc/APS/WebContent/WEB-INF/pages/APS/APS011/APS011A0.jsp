<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "AML手動登錄作業";
	String image = path + "/" + "images/";
	String GAMID = "APS011A0";
	String mDescription = "AML手動登錄作業";
	String nameSpace = "/aps/011";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：OTH0087，處理人員：BJ085，需求單編號：OTH0087 AML手動登錄  start
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script language="JavaScript">
$(document).ready(function(){
	//validate有錯誤時的訊息
	<c:if test="${not empty errorMsg}">
	var msgg = "有錯誤\n";
		<c:forEach items="${errorMsg}" var="entry">   
			$('input[id^=<c:out value="${entry.key}" /> ]').css('background-color','yellow');
			<c:if test="${entry.value != ''}">
				msgg = msgg + 	'<c:out value="${entry.value}" />' + "\n";
			</c:if>
		</c:forEach> 
		alert(msgg);
	</c:if>
	
	//validate
	$("#mainForm").validate({
		isShowLabel:false,
		isAlertLocalMsg:false,
		rules: {
			"amlInsuredListVo.classCode":{
				"required":true,
			},
			"amlInsuredListVo.riskCode":{
				"required":true
			},
			"amlInsuredListVo.businessNo":{
				"required":true
			},
			"amlInsuredListVo.comCode":{
				"required":true
			},
			"amlInsuredListVo.type":{
				"required":true
			},
			"amlInsuredListVo.channelType":{
				"required":true
			},
			"amlInsuredListVo.resend":{
				"required":true,
				"checkResend":""
			},
			"amlInsuredListVo.prem":{
				"required":true,
				"checkPrem":""
			}
		},
		messages: {
			"amlInsuredListVo.classCode":{
				"required":"請選擇險別代碼!"
			},
			"amlInsuredListVo.riskCode":{
				"required":"請選擇險種代碼!"
			},
			"amlInsuredListVo.businessNo":{
				"required":"請輸入業務號!"
			},
			"amlInsuredListVo.comCode":{
				"required":"請輸入公司代號!"
			},
			"amlInsuredListVo.type":{
				"required":"請選擇作業類型!"
			},
			"amlInsuredListVo.channelType":{
				"required":"請選擇業務來源!"
			},
			"amlInsuredListVo.resend":{
				"required":"重送註記不可為空!",
				"checkResend":"重送註記必須為0或1"
			},
			"amlInsuredListVo.prem":{
				"required":"請輸入保費!",
				"checkPrem":"保費必須為數字，且不包含小數點!"
			}
		}
	});
	
	$("#mainForm").submit(function () {
		$.blockUI({
			//blockUI：設定頁面指定區域顯示執行中文字(如Loading...)並鎖定該區域限制輸入。
			border: 'none',
			padding: '15px',//padding：同時設定四個邊留白的捷徑屬性
			backgroundColor: '#000',//backgroundColor：訊息背景顏色
			color: '#fff',//color：訊息字樣顏色
			'-webkit-border-radius': '10px',//WebKit設定四邊的圓角為10px
			'-moz-border-radius': '10px',//Mozilla設定四邊的圓角為10px
			opacity: .5//opacity：指定表單和其控制項的透明度等級
		});
	});
	
	ajaxAction('findRiskCode');
	$("[id='amlInsuredListVo.classCode']").bind("change",function(){
		ajaxAction('findRiskCode');
	});
});

	$.validator.addMethod("checkResend", function(value,element,param) { 
		return checkResendVal('amlInsuredListVo.resend');						
	},"");
	$.validator.addMethod("checkPrem", function(value,element,param) { 
		return checkPremVal('amlInsuredListVo.prem');						
	},"");
	
	function form_submit(method){
		if('clear' === method){
			$("#clearForm").submit();
		}
		if('create' === method && checkDtl() && confirm("是否確認新增此筆資料？")===true){
			changeDate();
			$("#mainForm").attr("action","btnCreate.action");
			$("#mainForm").submit();
			if (!$("#mainForm").valid()) {
				$.unblockUI();
			}
		}
	}
	
	function changeDate(){
		var index = $("#hiddenIndex").val();
		for(var i=0;i<=index;i++){
			var birthday = $("[id='amlInsuredList["+i+"].birthday']");
			var estDate = $("[id='amlInsuredList["+i+"].estDate']");
			if(birthday.val()!="" && birthday.val()!= undefined){
				birthday.val((parseInt(birthday.val().substring(0,3))+parseInt(1911))+birthday.val().substring(3,9));
			}
			if(estDate.val()!="" && estDate.val() != undefined){
				estDate.val((parseInt(estDate.val().substring(0,3))+parseInt(1911))+estDate.val().substring(3,9));
			}
		}
	}

	function checkResendVal(resent) {
		var checkStr = $("[id='"+resent+"']").val();
		if(checkStr !=0 && checkStr !=1){
			return false;
		}
		return true;
	}

	function checkPremVal(prem){
		var checkStr = $("[id='"+prem+"']").val();
		var regExp = /^[1-9]\d*$/;
		if(regExp.test(checkStr) || checkStr==""){
			return true;
		}
		return false;
	}

	function checkDtl(){
		var index = $("#hiddenIndex").val();
		var name;
		var birthday;
		var estDate;
		var serialNo;
		var gender;
		var listedCabinetCompany;
		var msg = "";
		var checkClaimMsg="";
		var type = $("[id='amlInsuredListVo.type']").val();
		if(($(".addTable").length)<1){
			msg += "請至少輸入一筆要被保人、關係人資料";
		}
		for(var i=0;i<=index;i++){
			serialNo = $("[id='amlInsuredList["+i+"].serialNo']").val();
			name = $("[id='amlInsuredList["+i+"].name']").val();
			if(name!=undefined && name==""){
				msg += "請輸入第"+serialNo+"筆姓名!\n";
			}
			
			birthday = $("[id='amlInsuredList["+i+"].birthday']").val();
			estDate = $("[id='amlInsuredList["+i+"].estDate']").val();
			var regex=/^([0-9]\d{2}-(0+[1-9]|1[012])-(0+[1-9]|[12][0-9]|3[01]))*$/;
			if(birthday!=undefined && birthday!="" && !regex.test(birthday)){
				msg += "第"+serialNo+"筆生日日期格式錯誤!\n";
			}
			if(estDate!=undefined && estDate!="" && !regex.test(estDate)){
				msg += "第"+serialNo+"筆公司成立日期格式錯誤!\n";
			}
			var insuredType = $("[id='amlInsuredList["+i+"].insuredType']").val();
			var id = $("[id='amlInsuredList["+i+"].id']").val();
			if((id!="" && id!=undefined )
					&&((insuredType == 2 && id.length != 8)||(insuredType == 1 && id.length == 8))){
				msg += "第"+serialNo+"筆身分證字號/統編格式錯誤!\n";
			}

			if(type == "C"){
				checkClaimMsg = checkClaimData(i);			
			}else{
				gender = $("[id='amlInsuredList["+i+"].gender']").val();
				listedCabinetCompany = $("[id='amlInsuredList["+i+"].listedCabinetCompany']").val();
				if(insuredType == 2 && ((birthday!=undefined && birthday!="")||(gender!=undefined && gender!=""))){
					msg += "第"+serialNo+"筆資料身分別為法人，性別及生日欄位不需填寫~\n";
				}
				if(insuredType == 1 && ((estDate!=undefined && estDate!="")||(listedCabinetCompany!=undefined && listedCabinetCompany!=""))){
					msg += "第"+serialNo+"筆資料身分別為自然人，公司成立日期及上市櫃公司欄位不需填寫~\n";
				}
			}
		}
		if(msg != "" || checkClaimMsg!=""){
			msg += checkClaimMsg;
			alert("要被保人、關係人資料有誤!\n"+msg);
			return false;
		}
		return true;
	}

	function checkClaimData(index){
		var msg="";
		var gender = $("[id='amlInsuredList["+index+"].gender']").val();
		var birthday = $("[id='amlInsuredList["+index+"].birthday']").val();
		var nationCode = $("[id='amlInsuredList["+index+"].nationCode']").val();
		var dangerOccupation = $("[id='amlInsuredList["+index+"].dangerOccupation']").val();
		var estDate = $("[id='amlInsuredList["+index+"].estDate']").val();
		var listedCabinetCompany = $("[id='amlInsuredList["+index+"].listedCabinetCompany']").val();
		if((gender!="" && gender!=undefined)||(birthday!=""&&birthday!=undefined)||
				(nationCode!="" && nationCode!=undefined)||(dangerOccupation!="" && dangerOccupation!=undefined)||
				(estDate!="" && estDate!=undefined)||(listedCabinetCompany!="" && listedCabinetCompany!=undefined)){
			msg = "作業類型為理賠時，要被保人、關係人資料之性別、生日、國籍代碼、高風險職業、公司成立日期、上市櫃公司等欄位不需填寫!\n";
		}
		return msg;
	}

	function ajaxAction(action){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 

		if(action == 'findRiskCode'){
	   		var classcode = $("[id='amlInsuredListVo.classCode']").val();
	    	path = contextPath + '/aps/ajax003/findRiskCode.action?classcode='+classcode;
		}
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {	},
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
	//ajax成功時會回來的method
		function ajaxSuccess(action, data){
		//ajax回傳的data存在，將ajax回傳的data設定至頁面上
		var riskcode = $("[id='amlInsuredListVo.riskCode']");
		var classCode = $("[id='amlInsuredListVo.classCode']");
		if(data.riskcodes != null){
			if(action == 'findRiskCode'){
				riskcode.removeOption(/./);
				var da = data.riskcodes;
				for(var k in da){
					riskcode.addOption(k,da[k]);
				}
				riskcode.selectOptions(data.riskcode);	
			}
		}else{//ajax回傳的data不存在
			if(action == 'findRiskCode'){
				classCode.val('');
				riskcode.val('');
			}
		}		
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert('無資料');
	}

	function showMsg(msg){
		alert(msg);
	}

	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}	
</script>
</head>
<body style="margin: 0; text-align: left">
<table id="table1" cellSpacing="1" cellPadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
		<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a>
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2" width="970px"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- clear form -->
<s:form theme="simple" action="btnCreateCancel" namespace="/aps/011" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/011" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>新增作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="200px" align="right"><font color="#FF0000">*</font>險別代碼：</td>
			<td width="300px" align="left">
				<s:select list="#{'A':'A 任意險類', 'B':'B 強制險類', 'AB':'AB 車險-強制險+任意險','C':'C 責任險', 'C1':'C1 傷害暨健康險', 'M':'M 水險', 'F':'F 火險', 'E':'E 工程險'}" key="%{'amlInsuredListVo.classCode'}" id="%{'amlInsuredListVo.classCode'}" />				
			</td>
		</tr>
		<tr>
			<td width="200px" align="right"><font color="#FF0000">*</font>險種代碼：</td>
			<td width="800px" align="left">
				<s:select list="riskcodeMap" key="%{'amlInsuredListVo.riskCode'}" id="%{'amlInsuredListVo.riskCode'}" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right"><font color="#FF0000">*</font>業務號：</td>
			<td width="300px" align="left">
				<s:textfield key="%{'amlInsuredListVo.businessNo'}" id="%{'amlInsuredListVo.businessNo'}" size="20" maxlength="20" />
				(報價單號、要保號、保單號、批單號、備案號…；若同時投保強制車險及任意車險時，以傳送任意險為主)
			</td>
		</tr>
		<tr>
			<td width="200px" align="right"><font color="#FF0000">*</font>公司代號：</td>
			<td width="300px" align="left">
				<s:textfield key="%{'amlInsuredListVo.comCode'}" id="%{'amlInsuredListVo.comCode'}" size="2" maxlength="2" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right"><font color="#FF0000">*</font>作業類型：</td>
			<td width="300px" align="left">
				<s:select list="#{'':'','Q':'Q-報價', 'T':'T-要保', 'E':'E-批改', 'C':'C-理賠'}" key="%{'amlInsuredListVo.type'}" id="%{'amlInsuredListVo.type'}" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right"><font color="#FF0000">*</font>業務來源：</td>
			<td width="300px" align="left">
				<s:select list="#{'':'', '10':'10-業務員', '20':'20-保險經紀人', '30':'30-保險代理人', '40':'40-直接業務'}" key="%{'amlInsuredListVo.channelType'}" id="%{'amlInsuredListVo.channelType'}" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">台幣保費：</td>
			<td width="300px" align="left">
				<s:textfield key="%{'amlInsuredListVo.prem'}" id="%{'amlInsuredListVo.prem'}" size="10" maxlength="10" />
			</td>
		</tr>
		<s:hidden name="amlInsuredListVo.amlType" value="2"/>
		<s:hidden name="amlInsuredListVo.appCode" value="NEWIMS_PRPINS"/>
	</table>

	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>要被保人、關係人資料</b></span>
			</td>
			<td class="image"> 
			<input type="button" value="新增要被保人、關係人資料" onclick="addDtlTable()" style="margin-left:200px"/>
			</td>
		</tr>
	</table>
	<div id="addTableDiv"></div>
	<input type="hidden" id="hiddenIndex" name="hiddenIndex" value="-1"/>

	<table width="970px">
		<tr>
			<td align="center"><input value="送出" type="button" onclick="javascript:form_submit('create');">&nbsp;&nbsp;&nbsp;&nbsp; <input value="取消" type="button" onclick="javascript:form_submit('clear');" /></td>
		</tr>
	</table>
</s:form>

<script type="text/javascript">
	//新增要被保人、關係人資料區塊
	function addDtlTable(){
		var serialNoIndex = $(".addTable").eq(-1).index();
		var index = parseFloat($("#hiddenIndex").val());
		var table = $('<div class="addTable"><table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">'
				//序號
				+'<tr><td width="200px" align="right"><font color="#FF0000">*</font>序號：</td>'
				+'<td width="300px" align="left">'
				+'<s:textfield class="cserialNo" key="amlInsuredList['+parseInt(index+1)+'].serialNo" id="amlInsuredList['+parseInt(index+1)+'].serialNo" size="2" maxlength="2" value="'+parseInt(serialNoIndex+2)+'" readonly="true"/></td>'
				+'<td colspan="5"></td></tr>'
				//身分別
				+'<tr><td width="200px" align="right"><font color="#FF0000">*</font>身分別：</td>'
				+'<td width="300px" align="left">'
				+'<s:select list="#{\"1\":\"1 - 自然人\", \"2\":\"2 - 法人\"}" key="amlInsuredList['+parseInt(index+1)+'].insuredType" id="amlInsuredList['+parseInt(index+1)+'].insuredType"/></td></tr>'
				//身分類型
				+'<tr><td width="200px" align="right"><font color="#FF0000">*</font>身分類型：</td>'
				+'<td width="300px" align="left">'
				+'<s:select list="#{\"2\":\"2 - 要保人\", \"1\":\"1 - 被保人\", \"8\":\"8 - 賠付對象\", \"9\":\"9 - 受益人\", \"3\":\"3 - 銀行\", \"4\":\"4 - 船名\", \"5\":\"5 - 飛機\", \"6\":\"6 - 國家\", \"7\":\"7 - 收貨人\"}" key="amlInsuredList['+parseInt(index+1)+'].insuredFlag" id="amlInsuredList['+parseInt(index+1)+'].insuredFlag"/></td></tr>'
				//身分證字號/統編
				+'<tr><td width="200px" align="right">身分證字號/統編：</td>'
				+'<td width="300px" align="left">'
				+'<s:textfield key="amlInsuredList['+parseInt(index+1)+'].id" id="amlInsuredList['+parseInt(index+1)+'].id" size="10" maxlength="50"/></td></tr>'
				//姓名
				+'<tr><td width="200px" align="right"><font color="#FF0000">*</font>姓名：</td>'
				+'<td width="300px" align="left">'
				+'<s:textfield key="amlInsuredList['+parseInt(index+1)+'].name" id="amlInsuredList['+parseInt(index+1)+'].name" size="50" maxlength="200"/></td></tr>'
				//英文姓名
				+'<tr><td width="200px" align="right">英文姓名：</td>'
				+'<td width="300px" align="left">'
				+'<s:textfield key="amlInsuredList['+parseInt(index+1)+'].enName" id="amlInsuredList['+parseInt(index+1)+'].enName" size="50" maxlength="200"/></td></tr>'
				//性別
				+'<tr><td width="200px" align="right">性別：</td>'
				+'<td width="300px" align="left">'
				+'<s:select list="#{\"\":\"\" , \"M\":\"M - 男\", \"F\":\" F - 女\"}" key="amlInsuredList['+parseInt(index+1)+'].gender" id="amlInsuredList['+parseInt(index+1)+'].gender"/>'
				+'(法人或是理賠時不需填寫)</td></tr>'
				//生日
				+'<tr><td width="200px" align="right">生日yyy-MM-dd：</td>'
				+'<td width="300px" align="left">'
				+'<s:textfield key="amlInsuredList['+parseInt(index+1)+'].birthday" id="amlInsuredList['+parseInt(index+1)+'].birthday" size="10" maxlength="10"/>'
				+'(法人或是理賠時不需填寫)</td></tr>'
				//國籍代碼
				+'<tr><td width="200px" align="right">國籍代碼：</td>'
				+'<td width="300px" align="left">'
				+'<s:textfield key="amlInsuredList['+parseInt(index+1)+'].nationCode" id="amlInsuredList['+parseInt(index+1)+'].nationCode" size="4" maxlength="4"/>'
				+'例如：TW(理賠不需填寫)</td></tr>'
				//高風險職業
				+'<tr><td width="200px" align="right">高風險職業：</td>'
				+'<td width="300px" align="left">'
				+'<s:select list="#{\"\":\"\" , \"Y\":\"Y - 是\", \"N\":\"N - 否\"}" key="amlInsuredList['+parseInt(index+1)+'].dangerOccupation" id="amlInsuredList['+parseInt(index+1)+'].dangerOccupation"/>'
				+'(理賠不需填寫)</td></tr>'
				//公司成立日期
				+'<tr><td width="200px" align="right">公司成立日期yyy-MM-dd：</td>'
				+'<td width="300px" align="left">'
				+'<s:textfield key="amlInsuredList['+parseInt(index+1)+'].estDate" id="amlInsuredList['+parseInt(index+1)+'].estDate" size="10" maxlength="10"/>'
				+'(自然人、理賠不需填寫)</td></tr>'
				//上市櫃公司
				+'<tr><td width="200px" align="right">上市櫃公司：</td>'
				+'<td width="300px" align="left">'
				+'<s:select list="#{\"\":\"\" , \"Y\":\"Y - 是\", \"N\":\"N - 否\"}" key="amlInsuredList['+parseInt(index+1)+'].listedCabinetCompany" id="amlInsuredList['+parseInt(index+1)+'].listedCabinetCompany"/>'
				+'(自然人、理賠不需填寫)</td></tr>'
				+'</table>'+'<input type="button" value="刪除" onClick="deleteDtlTable(this)" style="margin-left:467px"/></div>');
			table.appendTo($("#addTableDiv"));
			$("#hiddenIndex").val(parseInt(index+1));
	}

	function deleteDtlTable(delIndex){
		$(delIndex).parent().remove();
		if($(".addTable").length >= 1){
			for(i=0;i<$(".addTable").length;i++){
				$(".cserialNo").eq(i).val(i+1);
			}
		}
	}
</script>
</body>
</html>