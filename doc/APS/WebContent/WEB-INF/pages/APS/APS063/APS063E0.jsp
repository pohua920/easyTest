<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "已出單資料拋轉收件報備系統";
	String image = path + "/" + "images/";
	String GAMID = "APS063E0";
	String mDescription = "已出單資料拋轉收件報備系統";
	String nameSpace = "/aps/063";
%>
<!-- mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	$(document).ready(function(){
		 $("#pass").val('');
	
	});
	
	function form_submit(type){
		if("execute" == type){
			 $("#mainForm").attr("action","btnExecute.action");
			 $("#mainForm").submit();
		}
		
		
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
	<table cellspacing="1" cellpadding="1" width="970px" border="0">
		<tbody>
			<tr>
				<td width="485px"><img
					src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0"></td>
				<td align="right" width="485px">PGMID：<%=GAMID%></td>
			</tr>
			<tr>
				<td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
			</tr>
		</tbody>
	</table>
	<s:form theme="simple" namespace="/aps/060" id="mainForm"
		name="mainForm">
		<table id="table2" cellspacing="0" cellpadding="0" width="970px">
			<tr>
				<td class="MainTdColor" style="width: 200px" align="center">
					<span id="lbSearch"><b>AS400出單資料轉介</b></span>
				</td>
				<td colspan="3" class="image"></td>
			</tr>
		</table>
		<table width="970px" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left">
					Password:
				</td>
				<td align="left">
					<s:textfield key="filter.pass" id="pass" autocomplete="off"/>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<input type="button" value="確定" onclick="javascript:form_submit('execute');" />
				</td>
			</tr>
		</table>
		<table width="970px" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" colspan="2">
					<br/><br/>
					${message}
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>