<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "電信詐騙盜打維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS051E0";
	String mDescription = "電信詐騙盜打維護作業";
	String nameSpace = "/aps/051";
%>
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
	
		//小日曆
		//$('#sDate').datepicker({dateFormat:"yyyy/mm/dd"});
		//$('#eDate').datepicker({dateFormat:"yyyy/mm/dd"});
	});

	function form_submit(type){
		
		if("query" == type){
			if(checkInputDate()){
				$("#mainForm").attr("action","btnQuery.action");
				$("#mainForm").submit();				
			}
		}
		/*
		if("test" == type ){
			 $("#mainForm").attr("action","btnExcuteBatch.action");
			 $("#mainForm").submit();
		}
		*/
		if("cancel" == type){
			$("#clearForm").attr("action","btnQueryCancel.action");
			$("#clearForm").submit();
		}	
	}
	
	function checkInputDate(){
		var eventDate = $('#eventDate').val();
		var adRegex=/((0|1|2)(([0-9])\d\d)-(0[1-9]|1[0-2])-((0|1)[0-9]|2[0-9]|3[0-1]))$/;
		
		if(eventDate != "" && !adRegex.test(eventDate)){
			alert("事故日期格式錯誤");
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

<s:url action="lnkGoSubmit?fetclaimTypecm.oid=" namespace="/aps/051" var="lnkGoSubmit"/>
<s:url action="lnkGoView?fetclaimTypecm.oid=" namespace="/aps/051" var="lnkGoView"/>
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
<s:form theme="simple" action="btnQueryCancel" namespace="/car/051" id="clearForm" name="clearForm"/>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/051" id="mainForm" name="mainForm">
<s:hidden name="token" id="token"/>
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	
		<tr>
        	<td width="200px" align="right">維修單號：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.mtnNo" id="mtnNo" size="12" maxlength="12"/>
			</td>
			<td width="200px" align="right">事故日期：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.eventDate" id="eventDate" size="10" maxlength="10"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">賠案號碼：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.iclaimno" id="iclaimno" size="30" maxlength="30"/>
			</td>
			<td width="200px" align="right">理賠類型：</td>
			<td width="350px" align="left">
			    <!-- mantis：MOB0021，處理人員：DP0714，新商品上線APS作業調整 -->
				<s:select key="filter.claimType" id="claimType" theme="simple" list="#{'':'', 'M':'盜打', 'C':'詐騙', 'E':'爆炸'}" />				
			</td>
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="清除" onclick="javascript:form_submit('cancel');"/>
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
				nameSpace="/aps/051"
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
		<th width="50px">賠案<br/>類型<br/>
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CLAIM_TYPE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CLAIM_TYPE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>維修單號
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="MTN_NO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="MTN_NO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>賠案號碼
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ICLAIMNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ICLAIMNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>事故日期
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="EVENT_DATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="EVENT_DATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>輸入日期
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CREATDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CREATDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>API資料狀態
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="API_STATUS" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="API_STATUS" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>XML資料狀態
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="XML_STATUS" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/051/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="XML_STATUS" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center">
			<s:if test='claimType == "M"'>盜打</s:if>
			<s:if test='claimType == "C"'>詐騙</s:if>
		</td>
		<td align="center"><s:property value="mtnNo"/></td>
		<td align="center"><s:property value="iclaimno"/></td>
		<td align="center"><s:property value="eventDate"/></td>
		<td align="center"><s:date name="creatdate" format = "yyyy-MM-dd HH:mm:ss" var="formattedVal"/> <s:property value="%{#formattedVal}"/></td>
		<td align="center">
			<s:if test='apiStatus == "0"'>資料暫存
				<a href='${lnkGoSubmit}${row.oid}' >API發送</a>
			</s:if>
			<s:if test='apiStatus == "1"'>發送失敗-<s:property value="retcode"/>-<s:property value="message"/>
				<a href='${lnkGoSubmit}${row.oid}' >API發送</a>
			</s:if>
			<s:if test='apiStatus == "2"'>已發送  <a href='${lnkGoView}${row.oid}' >資料檢視</a></s:if>
		</td>
		<td align="center">
			<s:if test='apiStatus == "2"'>
				<s:if test='xmlStatus == null'>
					<a href='${lnkGoSubmit}${row.oid}' >XML上傳</a>
				</s:if>
				<s:if test='xmlStatus == "0"'>暫存 <a href='${lnkGoSubmit}${row.oid}' >XML上傳</a></s:if>
				<s:if test='xmlStatus == "1"'>上傳失敗 <a href='${lnkGoSubmit}${row.oid}' >XML上傳</a>
				</s:if>
				<s:if test='xmlStatus == "2"'>已上傳 <a href='${lnkGoView}${row.oid}' >資料檢視</a></s:if>			
			</s:if>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>