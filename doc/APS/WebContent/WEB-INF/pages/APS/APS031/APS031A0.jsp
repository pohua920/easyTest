<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String title = "保單通訊資料產生及下載作業";
	String image = path + "/" + "images/";
	String GAMID = "APS031A0";
	String mDescription = "保單通訊資料產生及下載作業";
	String nameSpace = "/aps/031";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0427，處理人員：BJ085，需求單編號：FIR0427 個人險-保單通訊資料產生及下載作業 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	$(document).ready(function(){
		
		//小日曆
		$('#paramstartdate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#paramenddate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#parambasedate').datepicker({dateFormat:"yyyy/mm/dd"});
		
		defaultDate();
		
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
	});
	
	
	function checkDate(sDate,eDate){
		var adRegex=/^([0-9]\d{3}\/(0?[1-9]|1[012])\/(0?[1-9]|[12][0-9]|3[01]))*$/;
		if(!adRegex.test(sDate) || !adRegex.test(eDate)){
			return "請輸入正確日期格式;";
		}
		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '') || (sDate == '' && eDate == '')){
			return "日期區間必填且起迄日須同時輸入;";
		}
		var sDateArr = sDate.split("/");
		var tsDate = sDateArr[0]+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  eDateArr[0]+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			return "日期區間起日須<=迄日;";
		}
		return "";
	}
	
	function defaultDate(){
		var date = new Date(); 
		date.setDate(1); 
		date.setHours(-1);
		var month = (parseInt(date.getMonth())+1);
		if(month.length=1){
			month = "0"+ month;
		}
		var lastDay = date.getFullYear() +"/"+ month +"/"+ date.getDate();
		date.setDate(1);
		var day = date.getDate();
		if(day.length=1){
			day = "0"+ day;
		}
		var firstDay = date.getFullYear() +"/"+ month +"/"+ day;
	    $("#paramstartdate").val(firstDay);
	    $("#paramenddate").val(lastDay);
	    $("#parambasedate").val(lastDay);
	}
	
	function form_submit(type){
		if("btnExecute" == type && checkInputdata() && confirm("確定執行?")==true){ 
				$("#mainForm").attr("action","btnExecute.action");
				$("#mainForm").submit();
		}
		if (!$("#mainForm").valid()) {
			$.unblockUI();
		}
	}
	
	function checkInputdata(){
		var msg = "";
		if(!$("input[type='checkbox']").is(":checked")){
			msg += "請至少選擇一項險種;\n";
		}
		
		var dateMsg = checkDate($("#paramstartdate").val(),$("#paramenddate").val());
		if(dateMsg!=""){
			msg += dateMsg+"\n";
		}
		
		if($("#parambasedate").val()==""){
			msg += "基準日必填;\n";
		}
		
		if(msg != ""){
			alert(msg);
			return false;
		}
		return true;
	}
	
	function checkAll(){
		var paramriskcodes = document.getElementsByName("paramriskcode");
		for(var i=0;i<paramriskcodes.length;i++){
			$("input[name='paramriskcode']")[i].checked = true;
		}
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
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- clear form -->
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/031" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>轉入作業</b></span>
			</td>
			<td class="image" style="width:20px"></td>	
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><b>查詢作業</b></a></span></td>
			<td colspan="3" class="imageGray"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">險種(至少擇一)：</td>
			<td width="500px" align="left">
				<input type="button" value="全選" onclick="checkAll()"/>
				<s:checkboxlist key="paramriskcode" list="#{'A01,B01':'車險', 'GA,PA,TA,HP':'A&H', 'F02':'火險'}"  templateDir="template" template="checkboxlist.ftl" theme="simple"></s:checkboxlist>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">日期區間(必填)：</td>
			<td width="500px" align="left">
				<s:textfield key="paramstartdate" id="paramstartdate" theme="simple"/>~
				<s:textfield key="paramenddate" id="paramenddate" theme="simple"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">基準日(必填)：</td>
			<td width="500px" align="left">
				<s:textfield key="parambasedate" id="parambasedate" theme="simple"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right"></td>
			<td width="500px" align="left">
				<input type="button" value="執行" onclick="javascript:form_submit('btnExecute');"/>			
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">作業說明：</td>
			<td width="800px" align="left">
				1.本作業提供保單通訊資料產生，會分兩個階段產生資料與產生檔案。<br>
				2.選擇要產生的險別與資料區間，執行後至<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">查詢作業</a>檢視進度。<br>
				3.若仍有轉入作業未完成，本轉入作業會暫停使用，待轉入作業完成，即可進行下一批次。<br>
				4.若轉入作業，產生資料發生錯誤時，請先通知資訊部。<br>
				5.若轉入作業，產生檔案發生錯誤時，本轉入作業會暫停使用，可至<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">查詢作業</a>，執行重新產生檔案，或作廢本批次。<br>
				6.在尚未執行批次前，若要更改執行條件，請先至查詢作業，作廢該批次，再重新輸入新的條件。<br>
				7.<font color="red">每月3日凌晨XX:XX</font>，排程會自動產生上個月的資料，基準日為上個月的最後一日，不須額外設定。<br>
				例如:2/3自動排程撈簽單日期1/1~1/31，基準日為1/31之資料；<br>
				3/3自動排程撈簽單日期2/1~2/28，基準日為2/28之資料，以此類推。
			</td>
			<td colspan="5"></td>       	
		</tr>
  	</table>
</s:form>
</body>
</html>