<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String title = "業務人員所屬服務人員維護";
String image = path + "/" + "images/";
String GAMID = "APS027A0";
String mDescription = "業務人員所屬服務人員維護";
String nameSpace = "/aps/027";
%>
<!-- mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	$(document).ready(function(){
		
	});
	
	function form_submit(type){
		if("btnUpload" === type){
			 $("#mainForm").attr("action","btnBatchUpload.action");
			 $("#mainForm").submit();
		}
		if("btnDownloadFile" == type){
			var token = new Date().getTime();
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

 		    $("#token").val(token);
			$("#mainForm").attr("action","btnDownloadFile.action");
			$("#mainForm").submit();
			var pollDownload = setInterval(function() {
		        if (document.cookie.indexOf("aps027Download=" + token) > -1) {
		            $.unblockUI();
		            clearInterval(pollDownload);
		        }
		    }, 1000);
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
<s:url action="default" namespace="/aps/027" var="goQuery"/>
<body style="margin:0;text-align:left">
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
	<s:form theme="simple" action="default" namespace="/aps/027" id="clearForm" name="clearForm"/>
	<!--form 開始 -->
	<s:form theme="simple" namespace="/aps/027" id="mainForm" name="mainForm" enctype="multipart/form-data" method="POST">
	<s:hidden name="token" id="token"/>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
			<tbody>
				<tr bgcolor="white">
					<td class="SelectTdColor" style="width:200px" align="center">
						<span id="lbSearch"><a href='${goQuery}'><b>查詢作業</b></a></span>
					</td>
					<td class="imageGray" style="width:20px"></td>
					<td class="MainTdColor" style="width:200px" align="center">
						<span id="lbSearch"><b>批次作業上下傳</b></span></td>
					<td colspan="3" class="image"></td>
				</tr>
			</tbody>
		</table>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
			<tbody>
				<tr>
					<td width="500px" align="left">
					下載：<input type="button" value="下載" onclick="javascript:form_submit('btnDownloadFile');" />
					</td>
				</tr>
				<tr>
					<td width="500px" align="left">
					※下載的資料內容，為當下狀態是有效的所有業務員資料。
					</td>
				</tr>
				<tr>
					<td width="500px" align="left">
					上傳：<s:file name="upload" id="upload" label="File"/>
						<input type="button" value="EXCEL上傳" onclick="javascript:form_submit('btnUpload');"/>
					</td>
				</tr>
				<tr>
					<td width="500px" align="left">
					※僅接受excel格式，上傳格式的範例excel請參考附件。
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnDowloadSample.action">參考EXCEL</a><br>
					</td>
				</tr>
			</tbody>
		</table>
	</s:form>
	<!-- form結束 -->
</body>
</html>