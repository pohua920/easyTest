<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "AS400外銀續保匯入查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS029E1";
	String mDescription = "AS400外銀續保匯入查詢作業";
	String nameSpace = "/aps/029";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
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
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/029" id="mainForm" name="mainForm">
<s:if test="successDevResults != null && 0 != successDevResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td width="11%"></td>
		<td>業務來源：<s:property value="firAgtrnAs400DataUplist.businessnature"/></td>
		<td>續保年月：<s:property value="firAgtrnAs400DataUplist.rnYyyymm"/></td>
		<td width="30%"></td>
	</tr>
</table>
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="ePageInfo" 
				nameSpace="/aps/029"
				currentPage="${ePageInfo.currentPage}" 
				pageSize="${ePageInfo.pageSize}"   
				selectOnChange="ddlEPageSizeChanged" 
				textOnChange="txtEChangePageIndex"  
				rowCount="${ePageInfo.rowCount}"
				pageCount="${ePageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>續保保單號</th>
		<th>保險起期</th>
		<th>外牆</th>
		<th>屋頂</th>
		<th>樓層</th>
		<th>坪數</th>
		<th>年份</th>
		<th>火險保額</th>
		<th>保費</th>
		<th>地震保額</th>
		<th>保費</th>
		<th>要保</th>
		<th>被保</th>
		<th>建築結構中文說明</th>
	</tr>
	<s:iterator var="row" value="successDevResults">
		<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
			<td width="10%" align="center"><s:property value="oldpolicyno"/></td>
			<td align="center"><s:property value="startdate"/></td>
			<td align="center"><s:property value="wallmaterial"/></td>
			<td align="center"><s:property value="roofmaterial"/></td>
			<td align="center"><s:property value="sumfloors"/></td>
			<td align="center"><s:property value="buildarea"/></td>
			<td align="center"><s:property value="buildyears"/></td>
			<td align="center"><s:property value="amountF"/></td>
			<td align="center"><s:property value="premiumF"/></td>
			<td align="center"><s:property value="amountQ"/></td>
			<td align="center"><s:property value="premiumQ"/></td>
			<td align="center"><s:property value="postcodeA"/></td>
			<td align="center"><s:property value="postcodeI"/></td>
			<td align="center"><s:property value="structureText"/></td>
		</tr>
	</s:iterator>
</table>
</s:if>
<p>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><input type="button" value="回上頁"/></a>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>