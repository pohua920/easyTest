<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String title = "保經代分支機構維護";
String image = path + "/" + "images/";
String GAMID = "APS028A1";
String mDescription = "保經代分支機構維護";
String nameSpace = "/aps/028";
%>
<!-- mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求 -->
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
<s:url action="default" namespace="/aps/028" var="goQuery"/>
<body style="margin:0;text-align:left">
	<table cellspacing="1" cellpadding="1" width="970px" border="0">
		<tbody>
			   <tr>
				  <td width="485px">			      
					  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
					  <a href='${goQuery}'><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0"></a>
				  </td>
				  <td align="right" width="485px">PGMID：<%=GAMID%></td>
			   </tr>
			   <tr>
				   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
			   </tr>
		</tbody>		
	</table>

	<!-- clear form -->
	<s:form theme="simple" action="default" namespace="/aps/028" id="clearForm" name="clearForm"/>
	<!--form 開始 -->
	<s:form theme="simple" namespace="/aps/028" id="mainForm" name="mainForm" enctype="multipart/form-data" method="POST">
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
			<tbody>
				<tr bgcolor="white">
					<td class="SelectTdColor" style="width:200px" align="center">
						<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoCreate.action"><b>單筆新增</b></a></span>
					</td>
					<td class="imageGray" style="width:20px"></td>
					<td class="MainTdColor" style="width:200px" align="center">
						<span id="lbSearch"><b>批次上傳</b></span></td>
					<td colspan="3" class="image"></td>
				</tr>
			</tbody>
		</table>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
			<tbody>
				<tr>
		        	<td width="200px" align="right">批次上傳：</td>
					<td width="500px" align="left"></td>
					<td colspan="5"></td>
				</tr>
				<tr>
		        	<td width="200px" align="right"></td>
					<td width="500px" align="left">
					<s:file name="upload" id="upload" label="File"/>
						<input type="button" value="EXCEL上傳" onclick="javascript:form_submit('btnUpload');"/>			
					</td>
					<td colspan="5"></td>
				</tr>
				<tr>
		        	<td width="200px" align="right">作業說明：</td>
					<td width="800px" align="left">
						1.匯入格式請參閱<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnDowloadSample.action">範例檔案</a><br>
					</td>
					<td colspan="5"></td>       	
				</tr>
			</tbody>
		</table>
	</s:form>
	<!-- form結束 -->
</body>
</html>