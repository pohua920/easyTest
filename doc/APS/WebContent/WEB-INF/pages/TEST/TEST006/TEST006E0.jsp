<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "信用卡黑名單測試";
String image = path + "/" + "images/";
String GAMID = "TEST006E0";
String mDescription = "信用卡黑名單";
String nameSpace = "/test/006";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	
			
	function form_submit(type){
		
		if("query" == type){
			var creditCardNo = $("#creditCardNo").val();
			if(creditCardNo == "" || creditCardNo == null){
				alert("請輸入信用卡號");
				return false;
			}
				
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
<s:form theme="simple" action="btnQueryCancel" namespace="/test/006" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/test/006" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">信用卡卡號：</td>
			<td width="300px" align="left">
				<s:textfield key="creditCardBlackListVo.creditCardNo" id="creditCardNo" maxlength="16" size="16" theme="simple" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">回傳代碼：</td>
			<td width="300px" align="left">
				<s:textfield key="creditCardBlackListVo.resultCode" id="resultCode" maxlength="5" size="5" theme="simple" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">回傳訊息：</td>
			<td width="300px" align="left">
				<s:textfield key="creditCardBlackListVo.resultMsg" id="resultMsg" maxlength="50" size="50" theme="simple" />
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
</s:form>
<!-- form結束 -->
</body>
</html>