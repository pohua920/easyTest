<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "聯邦新件資料交換查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS041E1";
	String mDescription = "聯邦新件資料交換查詢作業";
	String nameSpace = "/aps/041";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 -->
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
<s:url action="downloadDtlFile?" namespace="/aps/041" var="downloadDtlFile"/>
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
<s:form theme="simple" namespace="/aps/041" id="mainForm" name="mainForm">
<s:hidden key="queryType" id="queryType" value="dtlQuery"></s:hidden>
<s:hidden key="firAgtBatchMain.batchNo" id="batchNo"></s:hidden>
<s:hidden key="firAgtBatchMain.batchType" id="batchType"></s:hidden>
<s:if test="devResults2 != null && devResults2.size != 0">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="dPageInfo" 
				nameSpace="/aps/041"
				currentPage="${ePageInfo.currentPage}" 
				pageSize="${ePageInfo.pageSize}"   
				selectOnChange="ddlEPageSizeChanged2" 
				textOnChange="txtEChangePageIndex2"  
				rowCount="${ePageInfo.rowCount}"
				pageCount="${ePageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>
			受理編號
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="ORDERSEQ" />
				<c:param name="sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="ORDERSEQ" />
				<c:param name="sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			險種
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="RISKCODE" />
				<c:param name="sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="RISKCODE" />
				<c:param name="sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>資料類型</th>
		<th>
			保單生效日
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="STARTDATE" />
				<c:param name="sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="STARTDATE" />
				<c:param name="sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>主被保險人</th>
		<th>分行名稱</th>
		<th>分行</th>
		<th>
			核心保單號
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="POLICYNO" />
				<c:param name="sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="POLICYNO" />
				<c:param name="sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			簽單日
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="UNDERWRITEENDDATE" />
				<c:param name="sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="dtlQuery" />
				<c:param name="firAgtBatchMain.batchNo" value='${firAgtBatchMain.batchNo}' />
				<c:param name="firAgtBatchMain.batchType" value="${firAgtBatchMain.batchType}" />
				<c:param name="sortBy" value="UNDERWRITEENDDATE" />
				<c:param name="sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
	</tr>
	<s:iterator var="row" value="devResults2">
		<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
			<td align="center"><s:property value="orderseq"/></td>
			<td align="center"><s:property value="riskcode"/></td>
			<td align="center">
			<c:if test="${isPdf == 'Y'}">
				<a href='${downloadDtlFile}firAgtBatchGenfile.fileName=${row.orderseq}&firAgtBatchGenfile.batchNo=${row.orderseq}'>
			</c:if>
			要保書
			</td>
			<td align="center"><fmt:formatDate value='${startDate}' pattern='yyyy/MM/dd'/></td>
			<td align="center"><s:property value="insuredName"/></td>
			<td align="center"><s:property value="branchName"/></td>
			<td align="center"><s:property value="branchNo"/></td>
			<td align="center"><s:property value="policyNo"/></td>
			<td align="center"><fmt:formatDate value='${underwriteenddate}' pattern='yyyy/MM/dd'/></td>
		</tr>
	</s:iterator>
</table>
</s:if>

<s:if test="devResults3 != null && 0 != devResults3.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="dPageInfo" 
				nameSpace="/aps/041"
				currentPage="${ePageInfo.currentPage}" 
				pageSize="${ePageInfo.pageSize}"   
				selectOnChange="ddlEPageSizeChanged2" 
				textOnChange="txtEChangePageIndex2"  
				rowCount="${ePageInfo.rowCount}"
				pageCount="${ePageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>受理編號</th>
		<th>狀態</th>
		<th>險種</th>
		<th>對應核心保批號</th>
		<th>生效日</th>
		<th>簽單日</th>
		<th>主被保險人</th>
		<th>分行名稱</th>
		<th>受理狀況</th>
		<th>保單號</th>
	</tr>
	<s:iterator var="row" value="devResults3">
		<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
			<td align="center"><s:property value="orderseq"/></td>
			<td align="center">
			<c:if test="${orderseqStatus == '00'}">未處理</c:if>
			<c:if test="${orderseqStatus == '01'}">資料產生成功</c:if>
			<c:if test="${orderseqStatus == '02'}">檔案產生成功</c:if>
			<c:if test="${orderseqStatus == '11'}">資料接收成功</c:if>
			<c:if test="${orderseqStatus == '12'}">資料更新成功</c:if>	
			</td>
			<td align="center"><s:property value="riskcode"/></td>
			<td align="center"><s:property value="coreNo"/></td>
			<td align="center"><fmt:formatDate value='${startDate}' pattern='yyyy/MM/dd'/></td>
			<td align="center"><fmt:formatDate value='${underwriteenddate}' pattern='yyyy/MM/dd'/></td>
			<td align="center"><s:property value="insuredName"/></td>
			<td align="center"><s:property value="branchName"/></td>
			<td align="center">
			<c:if test="${orderType == '1'}">受理件</c:if>
			<c:if test="${orderType == '2'}">批加</c:if>
			<c:if test="${orderType == '3'}">批減</c:if>
			<c:if test="${orderType == '4'}">文批</c:if>
			</td>
			<td align="center"><s:property value="policyNo"/></td>
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