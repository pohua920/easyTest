<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "業務人員所屬服務人員維護";
	String image = path + "/" + "images/";
	String GAMID = "APS027E0";
	String mDescription = "業務人員所屬服務人員維護";
	String nameSpace = "/aps/027";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護 -->
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
		
		var handlerStatus = $("input[name='filter.handlerStatus']:checked");
		if(typeof(handlerStatus.val()) == "undefined"){
			$("input[name='filter.handlerStatus']")[2].checked = true;
		}
	});

	function form_submit(type){
		if("query" == type ){
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

<s:url action="lnkGoUpdate?prpdagent.agentcode=" namespace="/aps/027" var="goUpdate"/>
<!-- mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 -->
<s:url action="lnkGobatchUploadAndDownload" namespace="/aps/027" var="GobatchUploadAndDownload"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/027" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<!-- mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 START -->
			<td class="image" style="width:20px"></td>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href='${GobatchUploadAndDownload}'><b>批次作業上下傳</b></a></span>
			</td>
			<td colspan="3" class="imageGray"></td>
			<!-- mantis：SALES0014，處理人員：CC009，需求單編號：SALES0014 銷管系統-APS-所屬服務人員維護新增批次上下傳功能 END -->
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="120px" align="right">業務員代碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.agentcode" id="agentcode" />
			</td> 
			<td width="120px" align="right">業務來源：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.businesssource" id="businesssource" />
			</td> 
		</tr>
		<tr>
			<td width="120px" align="right">業務員姓名：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.agentname" id="agentname" />
			</td> 
			<td width="120px" align="right">登錄證號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.logincode" id="logincode" />
			</td> 
		</tr>
		<tr>
			<td width="120px" align="right">歸屬部門：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.unitcodeLike" id="unitcodeLike" />
			</td> 
			<td width="120px" align="right">所屬服務人員尚未指派：</td>
			<td width="285px" align="left">
				<s:radio key="filter.handlerStatus" id="handlerStatus" list="#{'N':'未指派(N)','Y':'已指派(Y)','ALL':'全部'}"  theme="simple"/>
			</td> 
		</tr>
		<tr>
			<td width="120px" align="right">所屬服務人員：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.handlercode" id="handlercode" />
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
				nameSpace="/aps/027"
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
		<th></th>
		<th>
			業務員代碼
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="AGENTCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="AGENTCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>	
		<th>
			業務員姓名
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="AGENTNAME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="AGENTNAME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			登錄證號
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="LOGINCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="LOGINCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			業務來源
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BUSINESSSOURCE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BUSINESSSOURCE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			歸屬部門
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="UNITCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="UNITCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			所屬服務人員
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="HANDLERCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/027/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="HANDLERCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><a href='${goUpdate}${row.agentcode}'>修改</a></td>
		<td align="center"><s:property value="agentcode"/></td>
		<td align="center"><s:property value="agentname"/></td>
		<td align="center"><s:property value="logincode"/></td>
		<td align="center"><s:property value="businesssource"/></td>
		<td align="center"><s:property value="unitcode"/></td>
		<td align="center"><s:property value="handlercode"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>


</body>
</html>