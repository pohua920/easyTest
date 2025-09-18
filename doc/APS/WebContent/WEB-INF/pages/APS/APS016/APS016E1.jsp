<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "板信續保資料處理作業-明細資料";
	String image = path + "/" + "images/";
	String GAMID = "APS016E1";
	String mDescription = "板信續保資料處理作業";
	String nameSpace = "/aps/016";
%>
<!-- mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業 start -->
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

<s:url action="lnkGoUpdate?" namespace="/aps/016" var="goUpdate"/>
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
<s:form theme="simple" namespace="/aps/016" id="mainForm" name="mainForm">
	
<s:if test="dtlDevResults != null && 0 != dtlDevResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="dPageInfo" 
				nameSpace="/aps/016"
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
		<th width="30px">序號</th>
		<th width="90px">
			資料狀態
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="DATA_STATUS" />
				<c:param name="dPageInfo.filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="DATA_STATUS" />
				<c:param name="dPageInfo.filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>	
		<th width="100px">
			續保單號
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="OLDPOLICYNO" />
				<c:param name="dPageInfo.filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="OLDPOLICYNO" />
				<c:param name="dPageInfo.filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th width="100px">
			受理<br>編號
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="ORDERSEQ" />
				<c:param name="dPageInfo.filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="ORDERSEQ" />
				<c:param name="dPageInfo.filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start-->
		<th width="70px">
			服務<br>人員
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="HANDLER1CODE" />
				<c:param name="dPageInfo.filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="HANDLER1CODE" />
				<c:param name="dPageInfo.filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th width="100px">
			轉核心<br>時間
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="TRANS_PPS_TIME" />
				<c:param name="dPageInfo.filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="TRANS_PPS_TIME" />
				<c:param name="dPageInfo.filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end-->
		<th width="30px">
			鎖定
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="FIX_USER" />
				<c:param name="dPageInfo.filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="FIX_USER" />
				<c:param name="dPageInfo.filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start-->
		<th width="30px">
			剔退
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="SF_FLAG" />
				<c:param name="dPageInfo.filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="SF_FLAG" />
				<c:param name="dPageInfo.filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th width="30px">
			已修改
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="ISUPDATE" />
				<c:param name="dPageInfo.filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/016/lnkSortQuery.action" var="sortURL">
				<c:param name="dPageInfo.filter.sortBy" value="ISUPDATE" />
				<c:param name="dPageInfo.filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end-->
		<th width="250px">異常<br>訊息</th>
		<th>提醒<br>訊息</th>
	</tr>
	<s:iterator var="row" value="dtlDevResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center">
		<a href='${goUpdate}aps016DetailVo.batchNo=${row.batchNo}&aps016DetailVo.batchSeq=${row.batchSeq}'>
		<s:property value="batchSeq"/></a>
		</td>
		<td align="center">
			<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 -->
			<c:choose>
				<c:when test="${dataStatus == '0'}">未處理</c:when>
				<c:when test="${dataStatus == '1'}">APS暫存失敗</c:when>
				<c:when test="${dataStatus == '2'}">APS暫存成功</c:when>
				<c:when test="${dataStatus == '3'}">轉核心暫存成功</c:when>
				<c:when test="${dataStatus == '4'}">轉核心暫存失敗</c:when>
				<c:when test="${dataStatus == '5'}">轉核心要保成功</c:when>
				<c:when test="${dataStatus == '6'}">轉核心要保失敗</c:when>
				<c:when test="${dataStatus == '7'}">轉核心完成</c:when>
				<c:when test="${dataStatus == '8'}">轉核心失敗-寫入收付失敗</c:when>
				<c:when test="${dataStatus == 'A'}">人工判定不轉核心</c:when>
				<c:otherwise>未定義</c:otherwise>
			</c:choose>
		</td>
		<td align="center"><s:property value="oldpolicyno"/></td>
		<td align="center"><s:property value="orderseq"/></td>
		<td align="center">
			<c:if test="${handler1code != null && username != null}">
				<s:property value="handler1code+username"/>			
			</c:if>
		</td>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 -->
		<td align="center"><fmt:formatDate value='${transPpsTime}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center">
			<c:choose>
				<c:when test="${fixUser == null}">N</c:when>
				<c:otherwise>Y</c:otherwise>
			</c:choose>
		</td>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start-->
		<td align="center"><s:property value="sfFlag"/></td>
		<td align="center"><s:property value="isupdate"/></td>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end-->
		<td align="center"><s:property value="checkErrMsg"/></td>
		<td align="center"><s:property value="checkWarnMsg"/></td>

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