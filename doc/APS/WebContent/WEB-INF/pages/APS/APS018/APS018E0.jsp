<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "查詢電子保單發送結果";
String image = path + "/" + "images/";
String GAMID = "APS018E0";
String mDescription = "查詢電子保單發送結果";
String nameSpace = "/aps/018";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style>
h4 {
   width: 100%; 
   text-align: center; 
   border-bottom: 1px solid #000; 
   line-height: 0.1em;
   margin: 10px 0 20px; 
} 

h4 span { 
    background:#efefef;; 
    padding:0 10px; 
}
</style>
<script language="JavaScript">
	$(document).ready(function(){
		//mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件
		showYesterday();
	});
	
	/*mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start*/
	function showYesterday(){
		var date = new Date();
		date.setTime(date.getTime()-24*60*60*1000);
		var year = String(date.getFullYear());
		var month = String((date.getMonth()+1));
		if(month.length == 1){
			month = 0+month;
		}
		var day = String(date.getDate());
		if(day.length == 1){
			day = 0+day;
		}
		var yesterday = year+month+day;
	 	if($("#gridtable").length<1 && ($("#policyno").val()=="" && $("#emailstatus").val()=="*" 
	 			&& $("#sDate").val()=="" && $("#eDate").val()=="")){
	 		$("#sDate").val(yesterday);
			$("#eDate").val(yesterday);
		}
	}
	/*mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end*/
			
	function form_submit(type){
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		if("runJob" == type){
			 $("#mainForm").attr("action","btnRunJob.action");
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
<s:url action="lnkGoEdit?" namespace="/aps/018" var="goEdit"/>
<s:url action="lnkGoCreate" namespace="/aps/018" var="goCreate"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
				  <a href='${goCreate}'><img src="${pageContext.request.contextPath}/images/New_Icon.gif" border="0"></a>
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>

<!-- clear form -->
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/018" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/018" id="mainForm" name="mainForm">
<s:hidden name="token" id="token"/>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policyno" id="policyno"  theme="simple" />
			</td>
			<!-- mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 -->
			<td width="200px" align="right">轉要保單日期(YYYYMMDD)：</td>
			<td width="285px" align="left">
				<!-- mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start-->
				<s:textfield key="filter.sDate" id="sDate"  theme="simple" />~
				<s:textfield key="filter.eDate" id="eDate"  theme="simple" />
				<!-- mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end-->
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">寄送狀態：</td>
			<td width="285px" align="left">
				<!-- mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 -->
				<s:select key="filter.emailstatus" id="emailstatus" list="#{'*':'全選','Y':'寄送成功','N':'尚未寄送'}"/>
			</td>
			<!-- mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) start -->
			<td width="200px" align="right">險別：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.riskcode" id="riskcode"  theme="simple" />
			</td>
			<!-- mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) end -->			
		</tr>
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>
			<s:if test="userInfo.userId == 'ce035' ">
				  	<input type="button" value="執行排程" onclick="javascript:form_submit('runJob');"/>
			</s:if>
			
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
				nameSpace="/aps/018"
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
		<!-- mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start-->
		<th>
			保單號碼
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="POLICYNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="POLICYNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			轉要保單日期
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="UNDERWRITEENDDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="UNDERWRITEENDDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			使用者EMAIL
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="APPLICANTEMAIL" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="APPLICANTEMAIL" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			EMAIL寄送時間
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="EMAILDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="EMAILDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			手機號碼
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="MOBILE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="MOBILE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			簡訊寄送時間
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="SMSDATEDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="SMSDATEDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<!-- mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end-->
		<!-- mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) start -->
		<th>
			險別
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="RISKCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/018/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="RISKCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<!-- mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) end -->
	</tr>
	<s:iterator var="row" value="devResults">
	<!-- mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 start-->
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF" align="center">
		<td><s:property value="policyno"/></td>
		<td><s:property value="underwriteenddate"/></td>
		<td><s:property value="applicantemail"/></td>
		<td><fmt:formatDate value='${emaildate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td><s:property value="mobile"/></td>
		<td><fmt:formatDate value='${smsdatedate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<!-- mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險) -->
		<td><s:property value="riskcode"/></td>
	</tr>
	<!-- mantis：FIR0299，處理人員：BJ085，需求單編號：FIR0299 查詢電子保單發送結果 新增查詢條件 end-->
	</s:iterator>
</table>
</s:if>
</s:form>
<!-- form結束 -->
</body>
</html>