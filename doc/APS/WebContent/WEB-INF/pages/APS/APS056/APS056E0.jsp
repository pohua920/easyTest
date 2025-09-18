<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "65歲高齡教育訓練例外設定維護功能";
	String image = path + "/" + "images/";
	String GAMID = "APS056E0";
	String mDescription = "65歲高齡教育訓練例外設定維護功能";
	String nameSpace = "/aps/056";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
		if("query" == type){
			$("#mainForm").attr("action","btnQuery.action");
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

<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoCreate.action">
			<img src="${pageContext.request.contextPath}/images/New_Icon.gif" border="0"></a>
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- clear form -->
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/056" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">險種別：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.codeename" id="codeename"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">業務來源：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.oldcodecode" id="oldcodecode"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">新/續件：</td>
			<td width="285px" align="left">
				<s:select key="filter.uppercode" id="uppercode" theme="simple" list="#{'':'', '0':'新件', '1':'續件'}" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">是否生效：</td>
			<td width="285px" align="left">
				<s:select key="filter.validstatus" id="validstatus" theme="simple" list="#{'':'', '1':'是', '0':'否'}" />
			</td>
		</tr>
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>
		</td>
	</tr>
</table>
<s:if test="devResults != null && 0 != devResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/056"
				currentPage="${pageInfo.currentPage}" 
				pageSize="${pageInfo.pageSize}"   
				selectOnChange="ddlPageSizeChanged" 
				textOnChange="txtChangePageIndex"  
				rowCount="${pageInfo.rowCount}"
				pageCount="${pageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th></th>
		<th>名稱</th>
		<th>險種</th>
		<th>新/續件</th>
		<th>業務來源</th>
		<th>是否生效</th>
		<th>異動人員</th>
		<th>異動日期</th>
		<th>建檔人員</th>
		<th>建檔日期</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="50px">
			<a href='${pageContext.request.contextPath}<%=nameSpace%>/lnkGoUpdate.action?prpdNewCode.codetype=${row.codetype}&prpdNewCode.codecode=${row.codecode}'>修改</a>
		</td>
		<td align="left"><s:property value="codecname"/></td>
		<td align="left"><s:property value="codeename"/></td>
		<td align="left">
			<s:if test='uppercode == "0"'>新件</s:if>
			<s:if test='uppercode == "1"'>續保件</s:if>
		</td>
		<td align="left"><s:property value="oldcodecode"/></td>
		<td align="left">
			<s:if test='validstatus == "1"'>Y</s:if>
			<s:if test='validstatus == "0"'>N</s:if>
		</td>
        <td align="left"><s:property value="updateuser"/></td>
        <td align="left"><s:property value="updatedate"/></td>
        <td align="left"><s:property value="createuser"/></td>
        <td align="left"><s:property value="createdate"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>

</body>
</html>