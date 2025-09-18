<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "聯邦新件資料交換查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS041E3";
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
		
		$('#signSDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#signEDate').datepicker({dateFormat:"yyyy/mm/dd"});
		
		$('#unSDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#unEDate').datepicker({dateFormat:"yyyy/mm/dd"});

	});
	
	function form_submit(type){
		if("query" == type && checkMainQuery()){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
	}
	
	function checkMainQuery(){
		var signSDate = $('#signSDate').val();
		var signEDate = $('#signEDate').val();
		var unSDate = $('#unSDate').val();
		var unEDate = $('#unEDate').val();
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		var policyNo = $('#policyNo').val();
		
		var adRegex=/^([0-9]\d{3}\/(0?[1-9]|1[012])\/(0?[1-9]|[12][0-9]|3[01]))*$/;
		if(!adRegex.test(signSDate) || !adRegex.test(signEDate)){
			alert("要保建檔日期請輸入正確日期格式");
			return false;
		}
		if(!adRegex.test(unSDate) || !adRegex.test(unEDate)){
			alert("簽單日期請輸入正確日期格式");
			return false;
		}
		if(!adRegex.test(sDate) || !adRegex.test(eDate)){
			alert("生效日期請輸入正確日期格式");
			return false;
		}
		
		if((signSDate == '' && signEDate != '') || (signSDate != '' && signEDate == '')){
			alert("要保建檔日期執行起迄日須同時輸入");
			return false;
		}
		if((unSDate == '' && unEDate != '') || (unSDate != '' && unEDate == '')){
			alert("簽單日期執行起迄日須同時輸入");
			return false;
		}
		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '')){
			alert("生效日期執行起迄日須同時輸入");
			return false;
		}
		
		var signSDateArr = signSDate.split("/");
		var signTsDate = signSDateArr[0]+'/'+signSDateArr[1]+'/'+signSDateArr[2];
		var signEDateArr = signEDate.split("/");
		var signTeDate =  signEDateArr[0]+'/'+signEDateArr[1]+'/'+signEDateArr[2];
		if(((new Date(signTeDate) - new Date(signTsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("要保建檔日期執行起日須<=迄日");
			return false;
		}
		if(((new Date(signTeDate) - new Date(signTsDate))/(1000 * 60 * 60 * 24)) > 120){
			alert("要保建檔日期執行起訖日應介於120天內");
			return false;
		}
		
		var unSDateArr = unSDate.split("/");
		var unTsDate = unSDateArr[0]+'/'+unSDateArr[1]+'/'+unSDateArr[2];
		var unEDateArr = unEDate.split("/");
		var unTeDate =  unEDateArr[0]+'/'+unEDateArr[1]+'/'+unEDateArr[2];
		if(((new Date(unTeDate) - new Date(unTsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("簽單日期執行起日須<=迄日");
			return false;
		}
		if(((new Date(unTeDate) - new Date(unTsDate))/(1000 * 60 * 60 * 24)) > 120){
			alert("簽單日期執行起訖日應介於120天內");
			return false;
		}
		
		var sDateArr = sDate.split("/");
		var tsDate = sDateArr[0]+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  eDateArr[0]+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("生效日期執行起日須<=迄日");
			return false;
		}
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) > 120){
			alert("生效日期執行起訖日應介於120天內");
			return false;
		}
		
		
		if((signSDate == '' || signEDate == '') && (unSDate == '' || unEDate == '') && 
				(sDate == '' || eDate == '') && policyNo == ''){
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
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/goQuery2.action"><b>受理編號狀態查詢</b></span></td>
			<td class="imageGray" style="width:20px"></td>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>核心出單狀況查詢</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<s:hidden key="queryType" id="queryType" value="query3"></s:hidden>
		<tr>
        	<td width="600px" align="right">要保建檔日期：</td>
			<td width="285px" align="left" colspan="3">
				<s:textfield key="filter.signSDate" id="signSDate"/>~<s:textfield key="filter.signEDate" id="signEDate"/>
			</td>
			<td width="500px" align="right">簽單日期：</td>
			<td width="2000px" align="left" colspan="0">
				<s:textfield key="filter.unSDate" id="unSDate"/>~<s:textfield key="filter.unEDate" id="unEDate"/>
			</td>
		</tr>
		<tr>
        	<td width="500px" align="right">生效日期：</td>
			<td width="285px" align="left" colspan="3">
				<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
			<td width="500px" align="right">保單號碼：</td>
			<td width="1000px" align="left" colspan="0">
				<s:textfield key="filter.policyNo" id="policyNo" theme="simple" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">說明：</td>
			<td width="2600px" align="left">
				本功能查詢新核心要保檔業源為I99080(聯邦)之出單情況，用於追蹤核心出單時是否輸入正確受理編號。<br>
				請最少輸入一組查詢條件。<br>
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
			險種
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="RISKCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="RISKCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			要保書號
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="PROPOSALNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="PROPOSALNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>受理編號</th>
		<th>保單號碼</th>
		<th>
			接收時間
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="DCREATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="DCREATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>主被保險人</th>
		<th>分行名稱</th>
		<th>
			生效日期
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="STARTDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="STARTDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			簽單日期
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="UNDERWRITEENDDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/041/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query3" />
				<c:param name="filter.sortBy" value="UNDERWRITEENDDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>回饋</th>
	</tr>
	<s:iterator var="row" value="devResults1">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center">
			<c:if test="${riskcode == 'F01'}">商火</c:if>
			<c:if test="${riskcode == 'F02'}">住火</c:if>
		</td>
		<td align="center"><s:property value="proposalNo"/></td>
		<td align="center"><s:property value="orderseq"/></td>
		<td align="center"><s:property value="policyNo"/></td>
		<td align="center"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center"><s:property value="insuredName"/></td>
		<td align="center"><s:property value="extracomName"/></td>
		<td align="center"><fmt:formatDate value='${startDate}' pattern='yyyy/MM/dd'/></td>
		<td align="center"><fmt:formatDate value='${underwriteenddate}' pattern='yyyy/MM/dd'/></td>
		<td align="center"><s:property value="fbStatus"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>