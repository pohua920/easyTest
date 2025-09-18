<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "聯邦新件資料交換查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS041E2";
	String mDescription = "聯邦新件資料交換查詢作業";
	String nameSpace = "/aps/041";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 -->
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
	
		//小日曆
		$('#sDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#eDate').datepicker({dateFormat:"yyyy/mm/dd"});

	});
	
	function form_submit(type){
		if("query" == type && checkMainQuery()){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
	}
	
	function checkMainQuery(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		var orderseq = $('#orderseq').val();
		
		var adRegex=/^([0-9]\d{3}\/(0?[1-9]|1[012])\/(0?[1-9]|[12][0-9]|3[01]))*$/;
		if(!adRegex.test(sDate) || !adRegex.test(eDate)){
			alert("請輸入正確日期格式");
			return false;
		}
		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '')){
			alert("執行起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = sDateArr[0]+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  eDateArr[0]+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("執行起日須<=迄日");
			return false;
		}
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) > 120){
			alert("執行起訖日應介於120天內");
			return false;
		}
		if((sDate == '' || eDate == '') && orderseq == ''){
			alert("請最少輸入一個查詢條件。");
			return false;
		}
		return true;
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
<s:form theme="simple" namespace="/aps/041" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/goQuery1.action"><b>資料交換查詢</b></a></span>
			</td>
			<td class="imageGray" style="width:20px"></td>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>受理編號狀態查詢</b></span></td>
			<td class="image" style="width:20px"></td>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/goQuery3.action"><b>核心出單狀況查詢</b></span></td>
			<td colspan="3" class="imageGray"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<s:hidden key="queryType" id="queryType" value="query2"></s:hidden>
		<tr>
        	<td width="120px" align="right">接收日期：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
			<td width="120px" align="right">受理編號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.orderseq" id="orderseq" theme="simple" />
			</td>
		</tr>
		<tr>
        	<td width="120px" align="right">說明：</td>
			<td width="1400px" align="left">
				本功能以聯邦提供之要保受理檔取得對應核心資料，用於確認每一筆受理檔目前在核心的出單情況。<br>
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
<s:if test="devResults1 != null && 0 != devResults1.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/041"
				currentPage="${pageInfo.currentPage}" 
				pageSize="${pageInfo.pageSize}"   
				selectOnChange="ddlPageSizeChanged1" 
				textOnChange="txtChangePageIndex1"  
				rowCount="${pageInfo.rowCount}"
				pageCount="${pageInfo.pageCount}"/>
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
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="ORDERSEQ" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="ORDERSEQ" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			險種
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="RISKCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="RISKCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			接收時間
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="DCREATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="DCREATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>主被保險人</th>
		<th>分行</th>
		<th>
			生效日期
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="STARTDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="STARTDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			簽單日期
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="UNDERWRITEENDDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="UNDERWRITEENDDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			核心保單號
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="POLICYNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="POLICYNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>回饋</th>
	</tr>
	<s:iterator var="row" value="devResults1">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><s:property value="orderseq"/></td>
		<td align="center"><s:property value="riskcode"/></td>
		<td align="center"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center"><s:property value="insuredName"/></td>
		<td align="center"><s:property value="branchName"/></td>
		<td align="center"><fmt:formatDate value='${startDate}' pattern='yyyy/MM/dd'/></td>
		<td align="center"><fmt:formatDate value='${underwriteenddate}' pattern='yyyy/MM/dd'/></td>
		<td align="center"><s:property value="policyNo"/></td>
		<td align="center"><s:property value="fbStatus"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>