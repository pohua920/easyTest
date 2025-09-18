<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "電子保單不寄送單位與業務來源設定";
	String image = path + "/" + "images/";
	String GAMID = "APS025E0";
	String mDescription = "電子保單不寄送單位與業務來源設定";
	String nameSpace = "/aps/025";
%>
<!-- mantis：FIR0369，處理人員：BJ085，需求單編號：FIR0369 增加電子保單不寄送單位與業務來源設定  start -->
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

<s:url action="lnkGoUpdate?firBatchmailExcludedata.sno=" namespace="/aps/025" var="goUpdate"/>
<s:url action="lnkGoCreate" namespace="/aps/025" var="goCreate"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
		<a href='${goCreate}'><img src="${pageContext.request.contextPath}/images/New_Icon.gif" border="0"></a>
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/025" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="100px" align="right">業務來源代碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.businessnature" id="businessnature" />
			</td> 
			<td colspan="5"></td>   
			<td width="100px" align="right">單位代碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.extracomcode" id="extracomcode" />
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

<s:if test="devResults != null && 0 != devResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/025"
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
		<th>
			序號
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="SNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="SNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>	
		<th>
			業務來源代碼
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BUSINESSNATURE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BUSINESSNATURE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			單位代碼
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="EXTRACOMCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="EXTRACOMCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			是否啟用
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ACTIVE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ACTIVE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			備註
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="REMARK" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="REMARK" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			新增人員
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ICREATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ICREATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			新增時間
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DCREATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DCREATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			異動人員
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IUPDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IUPDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			異動時間
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DUPDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/025/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DUPDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="90px"><a href='${goUpdate}${row.sno}'><s:property value="sno"/></a></td>
		<td align="center" width="120px"><s:property value="businessnature"/></td>
		<td align="center" width="90px"><s:property value="extracomcode"/></td>
		<td align="center" width="86px"><s:property value="active"/></td>
		<td align="center"><s:property value="remark"/></td>
		<td align="center" width="86px"><s:property value="icreate"/></td>
		<td align="center" width="110px"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center" width="86px"><s:property value="iupdate"/></td>
		<td align="center" width="110px"><fmt:formatDate value='${dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>


</body>
</html>