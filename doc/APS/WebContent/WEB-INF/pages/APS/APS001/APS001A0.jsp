<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "APS001利害關係人權限維護";
	String image = path + "/" + "images/";
	String GAMID = "APS001A0";
	String mDescription = "APS001利害關係人權限維護";
	String nameSpace = "/aps/001";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			"rs000Au.au01":{
				"required":true
			}
		},
		messages: {
			"rs000Au.au01":{
				"required":"請輸入員工編號!"
			}
		}
	});

});
	function form_submit(method){
		 $("label").html('');
		 if('create' == method){
			 $("#mainForm").attr("action","btnCreate.action");
			 $("#mainForm").submit();
		 }
		 if('clear' == method){
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
</script>
</head>
<body style="margin: 0; text-align: left">
	<table id="table1" cellSpacing="1" cellPadding="1" width="970px" border="0">
		<tr>
			<td width="485px"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a> <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26"></td>
			<td align="right" width="485px">PGMID：<%=GAMID%></td>
		</tr>
		<tr>
			<td align="center" colSpan="2" width="970px"><h3><%=title%></h3></td>
		</tr>
	</table>

	<!-- clear form -->
	<s:form theme="simple" action="btnCreateCancel" namespace="/aps/001" id="clearForm" name="clearForm" />
	<!-- form 開始 -->
	<s:form theme="simple" namespace="/aps/001" id="mainForm" name="mainForm">
		<table id="table2" cellspacing="0" cellpadding="0" width="970px">
			<tr>
				<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>新增作業</b></span></td>
				<td class="image" style="width: 20px"></td>
				<td colspan="2"></td>
			</tr>
		</table>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px">
			<tr>
				<td align="right">員工編號：</td>
				<td align="left"><s:textfield key="rs000Au.au01" id="au01" maxLength="5" size="5" /></td>
				<td align="right">員工姓名：</td>
				<td align="left"><s:textfield key="rs000Au.au02" id="au02" maxLength="10" size="10" /></td>
			</tr>
			<tr>
				<td align="right">部門代號：</td>
				<td align="left"><s:textfield key="rs000Au.au03" id="au03" maxLength="5" size="5" /></td>

				<td align="right">部門名稱：</td>
				<td align="left"><s:textfield key="rs000Au.au04" id="au04" maxLength="10" size="10" /></td>
			</tr>

			<tr>
				<td align="right">0.利害關係人交易限額查詢:</td>
				<td><s:checkbox name="rs000Au.au05" fieldValue="0" /></td>
				<td align="right">1.利害關係人交易資料新增、維護、明細表:</td>
				<td><s:checkbox name="rs000Au.au05" fieldValue="1" /></td>
				<td align="right">3.利害關係人交易控管表:</td>
				<td><s:checkbox name="rs000Au.au05" fieldValue="3" /></td>
			</tr>
			<tr>
				<td align="right">4.利害關係人交易限額設定:</td>
				<td><s:checkbox name="rs000Au.au05" fieldValue="4" /></td>

				<td align="right">5.基本及事業資料維護:</td>
				<td><s:checkbox name="rs000Au.au05" fieldValue="5" /></td>
				<td align="right">6.利害關係人交易限額預警設定:</td>
				<td><s:checkbox name="rs000Au.au05" fieldValue="6" /></td>
			</tr>
			<tr>
				
				<td align="right">7.利害關係人交易限額警示清單:</td>
				<td><s:checkbox name="rs000Au.au05" fieldValue="7" /></td>
			</tr>
		</table>
		<table width="970px">
			<tr>
				<td align="center"><input value="儲存" type="button" onclick="javascript:form_submit('create');">&nbsp;&nbsp;&nbsp;&nbsp; <input value="清除" type="button" onclick="javascript:form_submit('clear');" /></td>
			</tr>
		</table>
	</s:form>
</body>
</html>