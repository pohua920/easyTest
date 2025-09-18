<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "65歲高齡教育訓練例外設定維護功能";
	String image = path + "/" + "images/";
	String GAMID = "APS056U0";
	String mDescription = "65歲高齡教育訓練例外設定維護功能";
	String nameSpace = "/aps/056";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			"prpdNewCode.codecname":{
				"required":true,
			},
			"prpdNewCode.oldcodecode":{
				"required":true
			},
			"prpdNewCode.codeename":{
				"required":true
			},
			"prpdNewCode.validstatus":{
				"required":true
			},
			"prpdNewCode.uppercode":{
				"required":true
			},
		},
		messages: {
			"prpdNewCode.codecname":{
				"required":"請輸入例外名稱!",
			},
			"prpdNewCode.oldcodecode":{
				"required":"請輸入業務來源!"
			},
			"prpdNewCode.codeename":{
				"required":"請輸入險種別!"
			},
			"prpdNewCode.validstatus":{
				"required":"請選擇是否生效!"
			},
			"prpdNewCode.uppercode":{
				"required":"請選擇新/續件!"
			},
		}
	});
});

	function form_submit(type){
	 	$("label").html('');
			if("update" == type){
				$("#mainForm").attr("action","btnUpdate.action");
				$("#mainForm").submit();
			}
			if("clear" == type){
				if(confirm("請確認是否放棄修改並返回查詢頁面？")){
					$("#clearForm").submit();
				}
				return false;
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
<table id="Table11" cellSpacing="1" cellPadding="1" width="970px" border="0">
		<tr>
		<td width="485px">
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoCreate.action"><img src="${pageContext.request.contextPath}/images/New_Icon.gif" border="0"></a>
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
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/056" id="clearForm" name="clearForm"/>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/056" id="mainForm" name="mainForm">	
    <s:hidden key="prpdNewCode.codetype" id="codetype"></s:hidden>
    <s:hidden key="prpdNewCode.codecode" id="codecode"></s:hidden>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr bgcolor="white">
			<td class="MainTdColor" width="200px" align="center">
				<span id="lbSearch"><b>修改作業</b></span>
			</td>
			<td colspan="2" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table1" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">例外名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdNewCode.codecname" id="codecname"/>
			</td>
        	<td width="200px" align="right">業務來源：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdNewCode.oldcodecode" id="oldcodecode"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">險種別：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdNewCode.codeename" id="codeename"/>
			</td>
        	<td width="200px" align="right">是否生效：</td>
			<td width="285px" align="left">
				<s:select key="prpdNewCode.validstatus" id="validstatus" theme="simple" list="#{'':'', '1':'是', '0':'否'}" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">新/續件：</td>
			<td width="285px" align="left">
				<s:select key="prpdNewCode.uppercode" id="uppercode" theme="simple" list="#{'':'', '0':'新件', '1':'續件'}" />
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">建檔人員：</td>
			<td width="200px" align="left">
				<s:property value="prpdNewCode.createuser"/>
			</td>
			<td width="150px" align="right">建檔日期：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${prpdNewCode.createdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">最後異動人員：</td>
			<td width="200px" align="left">
				<s:property value="prpdNewCode.updateuser"/>
			</td>
			<td width="150px" align="right">最後異動日期：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${prpdNewCode.updatedate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
	</table>
	<table width="970px">
		<tr>
			<td align="center">
				<input value="存檔" type="button" onclick="javascript:form_submit('update');">&nbsp;&nbsp;&nbsp;&nbsp;
			    <input value="取消" type="button" onclick="javascript:form_submit('clear');"/>
		    </td>
		</tr>
	</table>
</s:form>
</body>
</html>