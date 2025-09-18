<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "再保分進維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS036E0";
	String mDescription = "再保分進維護作業";
	String nameSpace = "/aps/036";
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
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		/*
		if("test" == type ){
			 $("#mainForm").attr("action","btnExcuteBatch.action");
			 $("#mainForm").submit();
		}
		*/
	}
	
	function checkInputDate(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		
		var adRegex=/^([0-9]\d{3}\/(0?[1-9]|1[012])\/(0?[1-9]|[12][0-9]|3[01]))*$/;
		if(!adRegex.test(sDate) || !adRegex.test(eDate)){
			alert("請輸入正確日期格式");
			return false;
		}
		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '')){
			alert("執行日期起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = sDateArr[0]+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  eDateArr[0]+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("執行日期起日須<=迄日");
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

<s:url action="lnkGoUpdate?reinsInwardMainData.oid=" namespace="/aps/036" var="goUpdate"/>
<s:url action="lnkDelete?reinsInwardMainData.oid=" namespace="/aps/036" var="goDelete"/>
<s:url action="lnkSubmit?reinsInwardMainData.oid=" namespace="/aps/036" var="goSubmit"/>
<s:url action="lnkView?reinsInwardMainData.oid=" namespace="/aps/036" var="goView"/>
<s:url action="lnkInsAudit?reinsInwardMainData.oid=" namespace="/aps/036" var="goInsAudit"/>
<s:url action="lnkReinsAudit?reinsInwardMainData.oid=" namespace="/aps/036" var="goReinsAudit"/>
<s:url action="lnkGoEndorse?reinsInwardMainData.oid=" namespace="/aps/036" var="goEndorse"/>
<body style="margin:0;text-align:left">

<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
			<s:if test='roleType == "RI001"'>
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoCreate.action">
				<img src="${pageContext.request.contextPath}/images/New_Icon.gif" border="0"></a>
			</s:if>
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- clear form -->
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/036" id="mainForm" name="mainForm">
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
        	<td width="200px" align="right">保單號碼：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.policyNo" id="policyNo" size="20"/>
			</td>
			<td width="200px" align="right">險種別：</td>
			<td width="350px" align="left">
				<s:select key="filter.classcode" id="classcode" theme="simple" list="#{'':'', 'A':'任意車險', 'B':'強制險', 'E':'工程險', 'F01':'商業火險', 'F02':'住火險', 'M':'水險', 'C':'新種險', 'C1':'傷害險'}" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">要保人名稱：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.applicantName" id="applicantName" size="20"/>
			</td> 
			<td width="200px" align="right">被保人名稱：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.insuredName" id="insuredName" size="20"/>				
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">要保人ID：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.applicantId" id="applicantId" size="20"/>
			</td> 
			<td width="200px" align="right">被保人ID：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.insuredId" id="insuredId" size="20"/>				
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">保單生效日：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.startDate" id="startDate" size="7"/>(yyyMMdd民國年月日共7碼)
			</td> 
			<td width="200px" align="right">保單到期日：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.endDate" id="endDate" size="7"/>(yyyMMdd民國年月日共7碼)			
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">簽單日(yyyyMMdd)：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.signDate" id="signDate" size="8"/>
			</td> 
			<td width="200px" align="right">狀態：</td>
			<td width="350px" align="left">
				<s:select key="filter.status" id="status" theme="simple" list="#{'':'', '0':'已建檔', '1':'待覆核', '2':'險部覆核通過', '3':'再保部覆核通過', '4':'已日結'}" />
			</td>
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>
			<!-- <input type="button" value="測試" onclick="javascript:form_submit('test');"/> -->
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
				nameSpace="/aps/036"
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
		<th width="50px">類型</br>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="TYPE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="TYPE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th width="60px">險種代碼</br>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CLASSCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CLASSCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>	
		<th>保單號</br>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="POLICY_NO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="POLICY_NO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>要保人
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="APPLICANT_NAME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="APPLICANT_NAME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>被保人
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INSURED_NAME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="INSURED_NAME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>保險起日<br>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="START_DATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="START_DATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>保險迄日<br>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="END_DATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="END_DATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>分進保費
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="NT_PREM" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="NT_PREM" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>狀態
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="STATUS" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/036/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="STATUS" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
		</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center">
			<s:if test='type == "P"'>保單</s:if>
			<s:if test='type == "T"'>批單</s:if>
		</td>
		<td align="center"><s:property value="classcode"/></td>
		<td align="center"><s:property value="policyNo"/></td>
		<td align="center"><s:property value="applicantName"/></td>
		<td align="center"><s:property value="insuredName"/></td>
		<td align="center"><s:property value="startDate"/></td>
		<td align="center"><s:property value="endDate"/></td>
		<td align="center"><fmt:setLocale value="zh_TW"/>&nbsp;<fmt:formatNumber value='${ntPrem}'/></td>
		<td align="center">
			<s:if test='status == "0"'>已建檔</s:if>
			<s:elseif test='status == "1"'>待覆核</s:elseif>
			<s:elseif test='status == "2"'>險部覆核通過</s:elseif>
			<s:elseif test='status == "3"'>再保覆核通過</s:elseif>
			<s:elseif test='status == "4"'>已日結</s:elseif>
		</td>
		<td align="center">
			
			<s:if test='status == "0"'>
				<s:if test='roleType == "RI001"'>
					<a href='${goUpdate}${row.oid}'>修改</a> 
					<a href='${goDelete}${row.oid}' onclick="return confirm('確定刪除這筆資料嗎?');">刪除</a>
					<a href='${goSubmit}${row.oid}' onclick="return confirm('確定送審這筆資料嗎?');">送審</a>				
				</s:if>
				<s:elseif test='roleType == "RI002" || roleType == "RI003"'>
					<a href='${goView}${row.oid}'>檢視</a> 
				</s:elseif>
			</s:if>
			<s:elseif test='status == "1"'>
				<s:if test='roleType == "RI001" || roleType == "RI003"'>
					<a href='${goView}${row.oid}'>檢視</a>
				</s:if>
				<s:elseif test='roleType == "RI002"'>
					<a href='${goInsAudit}${row.oid}'>險部覆核</a>
				</s:elseif>
			</s:elseif>
			<s:elseif test='status == "2"'>
				<s:if test='roleType == "RI001" || roleType == "RI002"'>
					<a href='${goView}${row.oid}'>檢視</a>
				</s:if>
				<s:elseif test='roleType == "RI003"'>
					<a href='${goReinsAudit}${row.oid}'>再保覆核</a>
				</s:elseif>
			</s:elseif>
			<s:elseif test='status == "4"'>
				<s:if test='roleType == "RI001" && type == "P"'>
					<a href='${goView}${row.oid}'>檢視</a>&nbsp;
					<a href='${goEndorse}${row.oid}'>新增批單</a>
				</s:if>
				<s:else>
					<a href='${goView}${row.oid}'>檢視</a>
				</s:else>
			</s:elseif>
			<s:else>
				<a href='${goView}${row.oid}'>檢視</a>
			</s:else>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>