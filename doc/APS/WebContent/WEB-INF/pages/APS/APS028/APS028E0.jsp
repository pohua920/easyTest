<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "保經代分支機構維護";
	String image = path + "/" + "images/";
	String GAMID = "APS028E0";
	String mDescription = "保經代分支機構維護";
	String nameSpace = "/aps/028";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求 -->
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
		
		var validstatus = $("input[name='filter.validstatus']:checked");
		if(typeof(validstatus.val()) == "undefined"){
			$("input[name='filter.validstatus']")[0].checked = true;
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

<s:url action="lnkGoUpdate?" namespace="/aps/028" var="goUpdate"/>
<s:url action="btnRemove?" namespace="/aps/028" var="goDelete"/>
<s:url action="lnkGoCreate" namespace="/aps/028" var="goCreate"/>
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
<s:form theme="simple" namespace="/aps/028" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="120px" align="right">保經代編號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.agentcode" id="agentcode" />
			</td> 
			<td width="120px" align="right">保經代機構分支編號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.agentsubcode" id="agentsubcode" />
			</td> 
		</tr>
		<tr>
			<td width="120px" align="right">保經代原有編號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.orgicode" id="orgicode" />
			</td> 
			<td width="120px" align="right">機構是否有效：</td>
			<td width="285px" align="left">
				<s:radio key="filter.validstatus" id="validstatus" list="#{'1':'有效','0':'無效'}" />
			</td> 
		</tr>
		<tr>
			<td width="120px" align="right">分支機構名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.agentsubnameLike" id="agentsubnameLike" />
			</td> 
			<td width="120px" align="right">來源通路別：</td>
			<td width="285px" align="left">
				<s:checkboxlist key="filter.kindList" list="%{kindList}" value="%{selectedKindList}" templateDir="template" template="checkboxlist.ftl" theme="simple"></s:checkboxlist>
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
				nameSpace="/aps/028"
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
		<th></th>
		<th>
			保經代編號<br>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.AGENTCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.AGENTCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>	
		<th>
			保經代<br>原有編號<br>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="B.ORGICODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="B.ORGICODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>	
		<th>
			分支機構<br>編號<br>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.AGENTSUBCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.AGENTSUBCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			分支機構名稱<br>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.AGENTSUBNAME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.AGENTSUBNAME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			地址<br>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.ADDRESSNAME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.ADDRESSNAME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			通訊電話<br>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.PHONENUMBER" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.PHONENUMBER" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			電子信箱<br>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.EMAIL" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.EMAIL" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			來源<br>通路別<br>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.KIND" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.KIND" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			是否<br>有效<br>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.VALIDSTATUS" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/028/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="A.VALIDSTATUS" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="25px"><a href='${goUpdate}aps028ResultVo.agentcode=${row.agentcode}&aps028ResultVo.agentsubcode=${row.agentsubcode}&aps028ResultVo.orgicode=${row.orgicode}'>修改</a></td>
		<td align="center" width="25px"><a href='${goDelete}aps028ResultVo.agentcode=${row.agentcode}&aps028ResultVo.agentsubcode=${row.agentsubcode}' onclick="return confirm('是否要刪除此筆資料，請確認。')">刪除</a></td>
		<td align="center" width="65px"><s:property value="agentcode"/></td>
		<td align="center" width="50px"><s:property value="orgicode"/></td>
		<td align="center"><s:property value="agentsubcode"/></td>
		<td align="center"><s:property value="agentsubname"/></td>
		<td align="center"><s:property value="addressname"/></td>
		<td align="center" width="65px"><s:property value="phonenumber"/></td>
		<td align="center"><s:property value="email"/></td>
		<td align="center"><s:property value="kind"/></td>
		<td align="center">
			<s:if test="validstatus==0">N</s:if>
			<s:if test="validstatus==1">Y</s:if>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>


</body>
</html>