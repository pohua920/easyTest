<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "板信資料交換處理作業-檔案下載清單";
	String image = path + "/" + "images/";
	String GAMID = "APS009E2";
	String mDescription = "板信資料交換處理作業";
	String nameSpace = "/aps/009";
%>
<!-- mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start -->
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

<s:url action="downloadBopFile?" namespace="/aps/009" var="dowGenFile"/>
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
<s:form theme="simple" namespace="/aps/009" id="mainForm" name="mainForm">
<!-- 	<table id="table2"  cellspacing="0" cellpadding="0" width="970px"> -->
<!-- 		<tr> -->
<!-- 			<td class="MainTdColor" style="width:200px" align="center"> -->
<%-- 				<span id="lbSearch"><b>查詢作業</b></span></td> --%>
<!-- 			<td colspan="3" class="image"></td> -->
<!-- 		</tr> -->
<!-- 	</table> -->
	
<s:if test="gefDevResults != null && 0 != gefDevResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="gPageInfo" 
				nameSpace="/aps/009"
				currentPage="${gPageInfo.currentPage}" 
				pageSize="${gPageInfo.pageSize}"   
				selectOnChange="ddlGefPageSizeChanged" 
				textOnChange="txtGefChangePageIndex"  
				rowCount="${gPageInfo.rowCount}"
				pageCount="${gPageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>批次號碼</th>	
		<th>檔案名稱</th>
		<th>產生人員</th>
		<th>產生時間</th>
	</tr>
	<s:iterator var="row" value="gefDevResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><s:property value="batchNo"/></td>
		<td align="center"><a href='${dowGenFile}firAgtBatchGenfile.fileName=${row.fileName}&firAgtBatchGenfile.batchNo=${row.batchNo}'><s:property value="fileName"/></a></td>
		<td align="center"><s:property value="icreate"/></td>
		<td align="center"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>

	</tr>
	</s:iterator>
</table>
<br/>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnQuery.action?type=N"><input type="button" value="回上頁"/></a>
		</td>
	</tr>
</table>
</s:if>
</s:form>
</body>
</html>