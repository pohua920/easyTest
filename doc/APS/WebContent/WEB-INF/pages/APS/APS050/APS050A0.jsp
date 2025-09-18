<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "行動裝置險批次號手動下載作業";
	String image = path + "/" + "images/";
	String GAMID = "APS050A0";
	String mDescription = "行動裝置險批次號手動下載作業";
	String nameSpace = "/aps/050";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：，處理人員：BJ016，需求單編號：行動裝置險批次號手動下載作業
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
			"rptBatchNo":{
				"required":true
			}

		},
		messages: {
			"rptBatchNo":{
				"required":"請輸入批次號!",
			}

		}
	});

});

	function form_submit(method){
		 $("label").html('');
		 if('download' == method){
			if(validate()){
				 $("#mainForm").attr("action","btnDownloadData.action");
				 $("#mainForm").submit();
			}
			
		 }
		 if('clear' == method){
			$("#clearForm").submit();
			return false;
		 }
	}

	function validate(){
		var message = "";
		var check = true;
		return check;
	}

	function showMsg(msg){
		alert(msg);
	}

	var msg = '<%=request.getAttribute("message")%>';
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}	
</script>
</head>
<body style="margin: 0; text-align: left">
<table id="table1" cellSpacing="1" cellPadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
		<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">
		<img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a>
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26"></td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2" width="970px"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- clear form -->
<s:form theme="simple" action="lnkGoDownloadData" namespace="/aps/050" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/050" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>手動下載作業</b></span></td>
			<td class="image" style="width: 20px"></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px">
		<tr>
			<td width="200px" align="right">批次號：</td>
			<td width="285px" align="left">
				<s:textfield key="rptBatchNo" id="rptBatchNo" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
		</tr>
	</table>
	<table width="970px">
		<tr>
			<td align="center"><input value="下載" type="button" onclick="javascript:form_submit('download');">&nbsp;&nbsp;&nbsp;&nbsp; <input value="取消" type="button" onclick="javascript:form_submit('clear');" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>