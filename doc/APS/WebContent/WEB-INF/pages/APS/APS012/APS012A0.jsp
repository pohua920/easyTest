<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "中信代理投保通知處理";
String image = path + "/" + "images/";
String GAMID = "APS012A0";
String mDescription = "中信代理投保通知處理";
String nameSpace = "/aps/012";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style>
h4 {
   width: 100%; 
   text-align: center; 
   border-bottom: 1px solid #000; 
   line-height: 0.1em;
   margin: 10px 0 20px; 
} 

h4 span { 
    background:#efefef;; 
    padding:0 10px; 
}
</style>
<script language="JavaScript">
	$(document).ready(function(){
		<c:if test="${not empty errorMsg}">
			var msgg = "有錯誤\n";
			<c:forEach items="${errorMsg}" var="entry">
				$('input[id^=<c:out value="${entry.key}" /> ]').css('background-color','yellow');
				<c:if test="${entry.value != ''}">
					msgg = msgg + '<c:out value="${entry.value}" />' + "\n";
				</c:if>
			</c:forEach>
			alert(msgg);
		</c:if>
	});
			
	function form_submit(type){
		 //$("label").html('');
		if("execute" == type){
			 $("#mainForm").attr("action","btnExecute.action");
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
<s:url action="default" namespace="/aps/012" var="goQuery"/>
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
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/012" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/012" id="mainForm" name="mainForm" enctype="multipart/form-data" method="POST" >
<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href='${goQuery}'><b>查詢作業</b></a></span>
			</td>
			<td class="imageGray" style="width:20px"></td>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>執行作業</b></span>
			</td>
			<td colspan="3" class="image" ></td>
		</tr>
</table>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr>
			<td width="200px" align="right">個金180檔案路徑：</td>
			<td align="left">
				<s:file name="upload1" label="File"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">法金180檔案路徑：</td>
			<td align="left">
				<s:file name="upload2" label="File"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">不通知續保件(EXCEL)檔案路徑：</td>
			<td align="left">
				<s:file name="upload3" label="File"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">作業說明：</td>
			<td width="285px" align="left">
				上傳後，請至查詢功能進行後續下載。若有同檔名匯入時，請先至查詢功能將原先匯入資料作廢。
				<input type="button" value="確定轉入" onclick="javascript:form_submit('execute');"/>
			</td>
		</tr>	
	</tbody>
</table>
</s:form>
<!-- form結束 -->
</body>
</html>