<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "信用卡欄位使用者控管設定";
	String image = path + "/" + "images/";
	String GAMID = "APS059E0";
	String mDescription = "信用卡欄位使用者控管設定";
	String nameSpace = "/aps/059";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis： HAS0254，處理人員：DP0706，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳END -->
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

		$("#startDate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});
		
		$("#endDate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});
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
<s:form theme="simple" namespace="/aps/059" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">使用者員編：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.codecode" id="codecode"/>
			</td>
			<td width="200px" align="right">是否有效：</td>
			<td width="285px" align="left">
				<s:select key="filter.validstatus" id="validstatus" theme="simple" list="#{'':'', '0':'失效', '1':'有效'}" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">使用者姓名：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.codecname" id="codecname"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">生效日期：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.validdate" id="startDate" maxlength="10" size="10" theme="simple" />~
				<s:textfield key="filter.invaliddate" id="endDate" maxlength="10" size="10" theme="simple" />
			</td>
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
				nameSpace="/aps/059"
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
		<th>使用者員編</th>
		<th>使用者姓名</th>
		<th>生效日</th>
		<th>到期日</th>
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
		<td align="center"><s:property value="codecode"/></td>
		<td align="center"><s:property value="codecname"/></td>
		<td align="center"><s:property value="validdate"/></td>
		<td align="center"><s:property value="invaliddate"/></td>
		<td align="center">
			<s:if test='validstatus == "1"'>Y</s:if>
			<s:if test='validstatus == "0"'>N</s:if>
		</td>
        <td align="center"><s:property value="updateuser"/></td>
        <td align="center"><s:property value="updatedate"/></td>
        <td align="center"><s:property value="createuser"/></td>
        <td align="center"><s:property value="createdate"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>

</body>
</html>