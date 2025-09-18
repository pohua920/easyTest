<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "兌換率維護管理";
String image = path + "/" + "images/";
String GAMID = "APS019E0";
String mDescription = "兌換率維護管理";
String nameSpace = "/aps/019";
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
		$('#strExchdate').datepicker({dateFormat:"yyyy/mm/dd"});
		//$('#exchdate').datePicker({"startDate":"01/01/1911","dateType" :"ad","dateFormat":"yyyy/mm/dd"});
	});
			
	function form_submit(type){
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		if("lnkGoEdit" == type){
			 $("#mainForm").attr("action","lnkGoEdit.action");
			 $("#mainForm").submit();
		}
	}
	
	function goEdit(basecurrency, exchcurrency, exchdate){
		$("#editBasecurrency").val(basecurrency);
		$("#editExchcurrency").val(exchcurrency);
		$("#editExchdate").val(exchdate);
		form_submit("lnkGoEdit");
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
<s:url action="lnkGoEdit?" namespace="/aps/019" var="goEdit"/>
<s:url action="lnkGoCreate" namespace="/aps/019" var="goCreate"/>
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
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/019" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/019" id="mainForm" name="mainForm">
<s:hidden name="token" id="token"/>
<s:hidden name="prpdexch.basecurrency" id="editBasecurrency"/>
<s:hidden name="prpdexch.exchcurrency" id="editExchcurrency"/>
<s:hidden name="prpdexch.exchdate" id="editExchdate"/>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">幣別：</td>
			<td width="285px" align="left">
				<s:select key="filter.basecurrency" id="basecurrency" list="currencyMap"/>
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>			
		</tr>
		<tr>
			<td width="200px" align="right">匯率日期：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.strExchdate" id="strExchdate"  theme="simple" />
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>			
		</tr>

	</tbody>
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
				nameSpace="/aps/019"
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
		<!--mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 start -->	
		<th>幣別
			<c:url value="/aps/019/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BASECURRENCY" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/019/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BASECURRENCY" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<!--mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 end -->
		<th>買入即期</th>
		<th>賣出即期</th>
		<th>平均匯率</th>
		<th>匯率日期</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left">
		<%java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy/MM/dd"); %>
		<fmt:formatDate value="${row.exchdate}" var="formattedDate" 
                type="date" pattern="yyyy-MM-dd" />
		<a href="javascript:goEdit('${row.basecurrency}', '${row.exchcurrency}', '<fmt:formatDate pattern = "yyyy/MM/dd" value = "${row.exchdate}" />');">修改</a>
		</td>
		<td align="left"><s:property value="basecurrency"/></td>
		<td align="left"><s:property value="spotbuyrate"/></td>
		<td align="left"><s:property value="spotsoldrate"/></td>
		<td align="left"><s:property value="exchrate"/></td>
		<td align="left"><s:property value="exchdate"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
<!-- form結束 -->
</body>
</html>