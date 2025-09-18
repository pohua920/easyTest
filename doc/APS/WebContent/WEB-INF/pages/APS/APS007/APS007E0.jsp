<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "稽核檢核例外地址維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS007E0";
	String mDescription = "稽核檢核例外地址維護作業";
	String nameSpace = "/aps/007";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：FIR0238，處理人員：BJ085，需求單編號：FIR0238 稽核議題檢核-例外地址維護作業  start
-->
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

<s:url action="lnkGoUpdate?firRuleAddrExcp.oid=" namespace="/aps/007" var="goUpdate"/>
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
<s:form theme="simple" namespace="/aps/007" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">規則代號：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.ruleId" id="ruleId"/>
			</td>
			<td colspan="5"></td>
        	<td width="200px" align="right">規則序號：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.ruleSeq" id="ruleSeq"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">標的物地址：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.address" id="address"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">刪除註記：</td>
			<td width="285px" align="left">
				<s:select key="filter.deleteFlag" id="deleteFlag" theme="simple" list="#{'BLANK':'', 'N':'N', 'Y':'Y'}" />
			</td> 
			<td colspan="5"></td>       	
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
				nameSpace="/aps/007"
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
		<th>規則代號</th>	
		<th>規則序號</th>
		<th>標的物地址</th>
		<th>備註</th>
		<th>刪除註記</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="50px">
			<a href='${goUpdate}${row.oid}'>修改</a>
		</td>
		<td align="left"><s:property value="ruleId"/></td>
		<td align="left"><s:property value="ruleSeq"/></td>
		<td align="left"><s:property value="address"/></td>
		<td align="left"><s:property value="remark"/></td>
		<td align="left"><s:property value="deleteFlag"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>

</body>
</html>