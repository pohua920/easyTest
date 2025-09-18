<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String title = "火險地址匯入及查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS005U0";
	String mDescription = "火險地址匯入及查詢作業";
	String nameSpace = "/aps/005";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入  start
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">

	function form_submit(type){
		var msg = "確定轉入將刪除現有資料，並以EXCEL之資料匯入系統？"; 
		if("btnFileUpd" == type){
			var ultype = $("#ultype").val();
			if(ultype === '2'){
				msg = "確定上傳檔案？"; 
			}
			if (confirm(msg)==true){ 
				$("#mainForm").attr("action","btnFileUpd.action");
				$("#mainForm").submit();
			    } else return false; 
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
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a>
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
<s:form theme="simple" action="btnFileUpd" namespace="/aps/005" id="mainForm" name="mainForm" method="POST" enctype="multipart/form-data">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
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
        	<td width="200px" align="right">上傳類型：</td>
			<td width="500px" align="left">
				<s:select key="ultype" id="ultype" theme="simple" list="#{'1':'1.全刪全增 ', '2':'2.部份增修'}" />
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">檔案路徑：</td>
			<td width="500px" align="left">
			<s:file name="upload" label="File"/>
				<input type="button" value="確定轉入" onclick="javascript:form_submit('btnFileUpd');"/>			
			</td>
			<td colspan="5"></td>
		</tr>
		
		<tr>
        	<td width="200px" align="right">作業說明：</td>
			<td width="800px" align="left">
				1.本作業提供火險地址樓層等級資料匯入，依據所選擇的「上傳類型」對地址資料庫進行全部刪除後新增，或是部份資料增修。<br>
				2.上傳成功僅代表檔案已存放至伺服器，每天晚上23:00另有排程作業執行資料轉入動作，請至查詢作業確認轉檔情況。<br>
				3.排程程式執行時若伺服器端有多個檔案，僅對最後一個檔案進行轉入，其餘未轉入的檔案狀態更改為「不處理」。<br>
				4.匯入格式請參閱<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnDowloadSample.action">SAMPLE</a>
			</td> 
			<td colspan="5"></td>       	
		</tr>
  	</table>

</s:form>

</body>
</html>