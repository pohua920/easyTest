<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "車險再保註記設定維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS049E0";
	String mDescription = "車險再保註記設定維護作業 ";
	String nameSpace = "/aps/049";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：CAR0553，處理人員：DP0706，需求單編號：CAR0553.APS-車險再保註記設定維護功能   
-->
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

		$('#periodstartdate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#periodend').datepicker({dateFormat:"yyyy/mm/dd"});
	});

	function form_submit(type){
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		if("genExcel" == type){
			$("#mainForm").attr("action","btnGenExcel.action");
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

<s:url action="lnkGoUpdate?carReinsurancePlan.oid=" namespace="/aps/049" var="goUpdate"/>
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
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/049" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:220px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="220px" align="right">再保類型：</td>
			<td width="265px" align="left">
				<s:select key="filter.reinsurancetype" id="reinsurancetype" theme="simple" list="#{'':'', '1':'合約', '2':'臨分'}" />
			</td>
			<td width="220px" align="right">合約名稱：</td>
			<td width="265px" align="left">
				<s:textfield key="filter.contractname" id="contractname"/>
			</td>
			<td width="220px" align="right">合約期限(起)：</td>
			<td width="265px" align="left">
				<s:textfield key="filter.periodstartdate" id="periodstartdate" maxlength="10" size="10" theme="simple" />
			</td>
			<td width="220px" align="right">合約期限(迄)：</td>
			<td width="265px" align="left">
				<s:textfield key="filter.periodend" id="periodend" maxlength="10" size="10" theme="simple" />
			</td>
		</tr>
		<tr>
        	<td width="220px" align="right">業務來源代號：</td>
			<td width="265px" align="left">
				<s:textfield key="filter.bcode" id="bcode"/>
			</td>
			<td width="220px" align="right">車種代號：</td>
			<td width="265px" align="left">
				<s:textfield key="filter.carkindcode" id="carkindcode"/>
			</td> 
			<td width="220px" align="right">險種代號：</td>
			<td width="265px" align="left">
				<s:textfield key="filter.kindcode" id="kindcode"/>
			</td> 
			<td colspan="2"></td>       	
		</tr>
		<tr>
        	<td width="220px" align="right">分出單號：</td>
			<td width="265px" align="left">
				<s:textfield key="filter.bidno" id="bidno"/>
			</td>
			<td width="220px" align="right">車牌：</td>
			<td width="265px" align="left">
				<s:textfield key="filter.licenseno" id="licenseno"/>
			</td> 
			<td width="220px" align="right">效力註記：</td>
			<td width="265px" align="left">
				<s:select key="filter.deleteFlag" id="deleteFlag" theme="simple" list="#{'':'', 'N':'無效', 'Y':'有效'}" />
			</td> 
			<td colspan="2"></td> 
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>		
			<input type="button" value="下載" onclick="javascript:form_submit('genExcel');"/>
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
				nameSpace="/aps/049"
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
		<th>再保類型</th>	
		<th>合約名稱</th>
		<th>合約期限(起)</th>
		<th>合約期限(迄)</th>
		<th>業務來源代號</th>
		<th>車種代號</th>
		<th>險種代號</th>
		<th>保額(起)</th>
		<th>保額(迄)</th>
		<th>分出單號類別</th>
		<th>分出單號</th>
		<th>車牌</th>
		<th>備註</th>
		<th>效力註記</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="50px">
			<a href='${goUpdate}${row.oid}'>修改</a>
		</td>
		<td align="left"><s:property value="reinsurancetype"/></td>
		<td align="left"><s:property value="contractname"/></td>
		<td align="left"><fmt:formatDate value='${periodstartdate}' pattern='yyyy-MM-dd' /></td>
		<td align="left"><fmt:formatDate value='${periodend}' pattern='yyyy-MM-dd' /></td>
		<td align="left"><s:property value="bcode"/></td>
		<td align="left"><s:property value="carkindcode"/></td>
		<td align="left"><s:property value="kindcode"/></td>
		<td align="left"><s:property value="amountS"/></td>
		<td align="left"><s:property value="amountE"/></td>
		<td align="left">
			<c:if test="${bidnotype == '1'}">報價</c:if>
			<c:if test="${bidnotype == '2'}">要保</c:if>
			<c:if test="${bidnotype == '3'}">保單</c:if>
		</td>
		<td align="left"><s:property value="bidno"/></td>
		<td align="left"><s:property value="licenseno"/></td>
		<td align="left"><s:property value="mark"/></td>
		<td align="left">
			<c:if test="${deleteFlag == 'Y'}">有效</c:if>
			<c:if test="${deleteFlag == 'N'}">無效</c:if>
		</td>	
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>

</body>
</html>