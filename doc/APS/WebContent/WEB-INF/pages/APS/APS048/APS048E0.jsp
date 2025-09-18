<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "再保分進理賠維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS048E0";
	String mDescription = "再保分進理賠維護作業";
	String nameSpace = "/aps/048";
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
		var damageDate = $('#damageDate').val();
		var claimDate = $('#claimDate').val();
		
		var adRegex=/((0|1|2)(([0-9])\d\d)(0[1-9]|1[0-2])((0|1)[0-9]|2[0-9]|3[0-1]))$/;
		
		if(damageDate != "" && !adRegex.test(damageDate)){
			alert("出險日期格式錯誤");
			return false;
		}
		if(claimDate != "" && !adRegex.test(claimDate)){
			alert("受理日期格式錯誤");
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

<s:url action="lnkGoUpdate?reinsInwardClaimData.oid=" namespace="/aps/048" var="goUpdate"/>
<s:url action="lnkDelete?reinsInwardClaimData.oid=" namespace="/aps/048" var="goDelete"/>
<s:url action="lnkSubmit?reinsInwardClaimData.oid=" namespace="/aps/048" var="goSubmit"/>
<s:url action="lnkView?reinsInwardClaimData.oid=" namespace="/aps/048" var="goView"/>
<s:url action="lnkInsAudit?reinsInwardClaimData.oid=" namespace="/aps/048" var="goInsAudit"/>
<s:url action="lnkReinsAudit?reinsInwardClaimData.oid=" namespace="/aps/048" var="goReinsAudit"/>
<s:url action="lnkGoEndorse?reinsInwardClaimData.oid=" namespace="/aps/048" var="goEndorse"/>
<body style="margin:0;text-align:left">

<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
			<s:if test='roleType == "CR001"'>
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
<s:form theme="simple" action="btnQueryCancel" namespace="/car/048" id="clearForm" name="clearForm"/>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/048" id="mainForm" name="mainForm">
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
        	<td width="200px" align="right">中信產保單號碼：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.policyNo" id="policyNo" size="20"/>
			</td>
			<td width="200px" align="right">要保人名稱：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.applicantName" id="applicantName" size="20"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">中信產賠案號碼：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.claimNo" id="claimNo" size="20"/>
			</td>
			<td width="200px" align="right">被保人名稱：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.insuredName" id="insuredName" size="20"/>				
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">出險日期：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.damageDate" id="damageDate" size="8" maxlength="8"/>(yyyyMMdd西元年月日共8碼)
			</td> 
			<td width="200px" align="right">要保人ID：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.applicantId" id="applicantId" size="20"/>				
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">受理日期：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.claimDate" id="claimDate" size="8" maxlength="8"/>(yyyyMMdd西元年月日共8碼)
			</td> 
			<td width="200px" align="right">被保人ID：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.insuredId" id="insuredId" size="20"/>				
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right"></td>
			<td width="350px" align="left">
			</td> 
			<td width="200px" align="right">狀態：</td>
			<td width="350px" align="left">
				<s:select key="filter.status" id="status" theme="simple" list="#{'':'', '0':'理賠資料暫存', '1':'已立案', '2':'理賠部覆核通過', '3':'再保部覆核通過', '4':'已日結'}" />
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
				nameSpace="/aps/048"
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
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value=".TYPE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="P.TYPE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th width="60px">險種代碼</br>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="P.CLASSCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="P.CLASSCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>	
		<th>中信產</br>賠案號碼
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="C.CLAIM_NO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="C.CLAIM_NO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>賠次</br>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CTIME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CTIME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>出險日期</br>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DAMAGE_DATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DAMAGE_DATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>受理日期<br>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CLAIM_DATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="CLAIM_DATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>中信產</br>保單號碼
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="POLICY_NO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="POLICY_NO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>被保人
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="POLICY_NO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="POLICY_NO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>保險起日
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="START_DATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="START_DATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>保險迄日
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="END_DATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="END_DATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>狀態
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="STATUS" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/048/lnkSortQuery.action" var="sortURL">
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
			<s:if test='type == "C"'>賠款</s:if>
		</td>
		<td align="center"><s:property value="classcode"/></td>
		<td align="center"><s:property value="claimNo"/></td>
		<td align="center"><s:property value="ctime"/></td>
		<td align="center"><s:property value="damageDate"/></td>
		<td align="center"><s:property value="claimDate"/></td>
		<td align="center"><s:property value="policyNo"/></td>
		<td align="center"><s:property value="insuredName"/></td>
		<td align="center"><s:property value="startDate"/></td>
		<td align="center"><s:property value="endDate"/></td>
		<td align="center">
			<s:if test='status == "0"'>理賠資料暫存</s:if>
			<s:elseif test='status == "1"'>已立案</s:elseif>
			<s:elseif test='status == "2"'>理賠部覆核通過</s:elseif>
			<s:elseif test='status == "3"'>再保部覆核通過</s:elseif>
			<s:elseif test='status == "4"'>已日結</s:elseif>
		</td>
		<td align="center">
			<s:if test='status == "0"'>
				<s:if test='roleType == "CR001"'>
					<a href='${goUpdate}${row.oid}'>修改</a> 
					<a href='${goDelete}${row.oid}' onclick="return confirm('確定刪除這筆資料嗎?');">刪除</a>
					<a href='${goSubmit}${row.oid}' onclick="return confirm('確定送審這筆資料嗎?');">送審</a>				
				</s:if>
				<s:elseif test='roleType == "CR002" || roleType == "CR003"'>
					<a href='${goView}${row.oid}'>檢視</a> 
				</s:elseif>
			</s:if>
			<s:elseif test='status == "1"'>
				<s:if test='roleType == "CR001" || roleType == "CR003"'>
					<a href='${goView}${row.oid}'>檢視</a>
				</s:if>
				<s:elseif test='roleType == "CR002"'>
					<a href='${goInsAudit}${row.oid}'>險部覆核</a>
				</s:elseif>
			</s:elseif>
			<s:elseif test='status == "2"'>
				<s:if test='roleType == "CR001" || roleType == "CR002"'>
					<a href='${goView}${row.oid}'>檢視</a>
				</s:if>
				<s:elseif test='roleType == "CR003"'>
					<a href='${goReinsAudit}${row.oid}'>再保覆核</a>
				</s:elseif>
			</s:elseif>
			<s:elseif test='status == "4"'>
				<s:if test='roleType == "CR001" && type == "P"'>
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