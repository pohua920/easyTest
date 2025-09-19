<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "關懷名單測試";
String image = path + "/" + "images/";
String GAMID = "TEST004E0";
String mDescription = "關懷名單測試";
String nameSpace = "/test/004";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	
			
	function form_submit(type){
		
		if("query" == type){
			$("#mainForm").attr("action","btnQuery.action");
			$("#mainForm").submit();
		}
		if("cancel" == type){
			$("#clearForm").submit();
		}		
	}
		


	</script>
</head>
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
<s:form theme="simple" action="btnQueryCancel" namespace="/test/004" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/test/004" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">ID：</td>
			<td width="300px" align="left">
				<s:textfield key="blackId" id="blackId" maxlength="100" size="100" theme="simple" />
			</td>
		</tr>
		
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="送出 " onclick="javascript:form_submit('query');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="清除" onclick="javascript:form_submit('cancel');"/>
		</td>
	</tr>
</table>
<table width="970px" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td width="200px" align="right">回傳結果：</td>
		<td width="285px" align="left">
			<s:textfield key="returnResult" id="returnResult" maxlength="1" size="1" theme="simple" /><BR>
		</td>
		<td width="200px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
</table>
</s:form>
<!-- form結束 -->
</body>
</html>