<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "元大回饋檔排程查詢作業-明細資料";
	String image = path + "/" + "images/";
	String GAMID = "APS062E1";
	String mDescription = "元大回饋檔排程查詢作業";
	String nameSpace = "/aps/062";
%>
<!-- mantis：FIR0681，處理人員：DP0714，住火-APS元大回饋檔-排程查詢作業 -->
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
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/062" id="mainForm" name="mainForm">
	
<s:if test="dtlDevResults != null && 0 != dtlDevResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="dPageInfo" 
				nameSpace="/aps/062"
				currentPage="${dPageInfo.currentPage}" 
				pageSize="${dPageInfo.pageSize}"   
				selectOnChange="ddlDtlPageSizeChanged" 
				textOnChange="txtDtlChangePageIndex"  
				rowCount="${dPageInfo.rowCount}"
				pageCount="${dPageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>受理編號</th>	
		<th>狀態</th>
		<th>險種代碼</th>
		<th>資料類型</th>
		<th>對應核心單號</th>
		<th>保單生效日</th>
		<th>主被保險人</th>
		<th>分行</th>
		<th>歸屬單位</th>
		<th>服務人員</th>
	</tr>
	<s:iterator var="row" value="dtlDevResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><s:property value="orderseq"/></td>
		<td align="center">
			<c:choose>
				<c:when test="${orderseqStatus == '00'}">未處理</c:when>
				<c:when test="${orderseqStatus == '01'}">資料產生成功</c:when>
				<c:when test="${orderseqStatus == '02'}">檔案產生成功</c:when>
				<c:otherwise>未定義</c:otherwise>
			</c:choose>
		</td>
		<td align="center"><s:property value="riskcode"/></td>
		<td align="center">
			<c:choose>
				<c:when test="${dataType == '1'}">要保書</c:when>
				<c:when test="${dataType == '2'}">保單</c:when>
				<c:when test="${dataType == '3'}">批單</c:when>
				<c:otherwise>未定義</c:otherwise>
			</c:choose>
		</td>
		<td align="center"><s:property value="coreNo"/></td>
		<td align="center"><fmt:formatDate value='${startDate}' pattern='yyyy/MM/dd'/></td>
		<td align="center"><s:property value="insuredName"/></td>
		<td align="center"><s:property value="extraComcode"/></td>
		<td align="center"><s:property value="comcode"/></td>
		<td align="center"><s:property value="handler1Name"/>
			<c:if test="${handler1Name == 'S'}">正常</c:if>
			<c:if test="${handler1Name == 'D'}">複保險</c:if>
			<c:if test="${handler1Name == 'E'}">地震基金服務異常</c:if>
		</td>

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