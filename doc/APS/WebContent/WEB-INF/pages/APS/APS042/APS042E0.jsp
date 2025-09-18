<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "複保險通知轉檔作業";
	String image = path + "/" + "images/";
	String GAMID = "APS042E0";
	String mDescription = "複保險通知轉檔作業";
	String nameSpace = "/aps/042";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 -->
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
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- clear form -->
<!-- form 開始 -->
<s:url action="downloadFile?" namespace="/aps/042" var="downloadFile"/>
<s:form theme="simple" namespace="/aps/042" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoUpload.action"><b>轉檔作業</b></a></span>
			</td>
			<td class="imageGray" style="width:20px"></td>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td class="image" style="width:20px"></td>
			<td colspan="5"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<s:hidden key="queryType" id="queryType" value="query1"></s:hidden>
		<tr>
        	<td width="120px" align="right">批次號碼：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.batchNo" id="batchNo" theme="simple" />
			</td>
			<td width="120px" align="right">通知年月(YYYYMM)：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.rpYyyymm" id="rpYyyymm"/>
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
				nameSpace="/aps/042"
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
		<th>批次號碼</th>
		<th>通知年月</th>
		<th>檔案名稱</th>
		<th>資料筆數</th>
		<th>狀態</th>
		<th>備註</th>
		<th>轉入人員</th>
		<th>轉入時間</th>
		<th>檔案下載</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><s:property value="batchNo"/></td>
		<td align="center"><s:property value="rpYyyymm"/></td>
		<td align="center"><s:property value="filename"/></td>
		<td align="center"><s:property value="dataQty"/></td>
		<td align="center">
			<c:if test="${fileStatus == 'N'}">未處理</c:if>
			<c:if test="${fileStatus == 'S'}">轉檔完成</c:if>
			<c:if test="${fileStatus == 'Z'}">檔案無資料</c:if>
			<c:if test="${fileStatus == 'E'}">執行異常</c:if>
			<c:if test="${fileStatus == ''}">未定義</c:if>
		</td>
		<td align="center"><s:property value="remark"/></td>
		<td align="center"><s:property value="icreate"/></td>
		<td align="center"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center">
			<c:if test="${fileStatus == 'S'}">
				<a href='${downloadFile}batchNo=${row.batchNo}'><input type="button" value="下載">
			</c:if>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>