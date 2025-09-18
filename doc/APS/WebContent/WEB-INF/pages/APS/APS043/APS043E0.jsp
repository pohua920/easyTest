<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "登錄證到期通知排程";
	String image = path + "/" + "images/";
	String GAMID = "APS043E0";
	String mDescription = "登錄證到期通知排程";
	String nameSpace = "/aps/043";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 -->
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
		
		$('#esDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#eeDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#lsDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#leDate').datepicker({dateFormat:"yyyy/mm/dd"});
	});
	
	function form_submit(type){
		if("query" == type && checkMainQuery()){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
	}
	
	function checkMainQuery(){
		var esDate = $('#esDate').val();
		var eeDate = $('#eeDate').val();
		var lsDate = $('#lsDate').val();
		var leDate = $('#leDate').val();
		var logincode = $('#logincode').val();
		
		var adRegex=/^([0-9]\d{3}\/(0?[1-9]|1[012])\/(0?[1-9]|[12][0-9]|3[01]))*$/;
		if(!adRegex.test(esDate) || !adRegex.test(eeDate)){
			alert("Email發送日期請輸入正確日期格式");
			return false;
		}
		if(!adRegex.test(lsDate) || !adRegex.test(leDate)){
			alert("登錄證到期日請輸入正確日期格式");
			return false;
		}
		if((esDate == '' && eeDate != '') || (esDate != '' && eeDate == '')){
			alert("Email發送日期起迄日須同時輸入");
			return false;
		}
		if((lsDate == '' && leDate != '') || (lsDate != '' && leDate == '')){
			alert("登錄證到期起迄日須同時輸入");
			return false;
		}
		var sDateArr = esDate.split("/");
		var tsDate = sDateArr[0]+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eeDate.split("/");
		var teDate =  eDateArr[0]+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("Email發送日期起日須<=迄日");
			return false;
		}
		var sDateArr = lsDate.split("/");
		var tsDate = sDateArr[0]+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = leDate.split("/");
		var teDate =  eDateArr[0]+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("登錄證到期起日須<=迄日");
			return false;
		}
		if(esDate == '' && eeDate == '' && lsDate == '' && leDate == '' && logincode == ''){
			alert("查詢條件擇一必入");
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
<s:form theme="simple" namespace="/aps/043" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoJob.action"><b>手動執行排程作業</b></a></span>
			</td>
			<td class="imageGray" style="width:20px"></td>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td class="image" style="width:20px"></td>
			<td colspan="5"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="120px" align="right">Email發送日期：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.esDate" id="esDate"/>~<s:textfield key="filter.eeDate" id="eeDate"/>
			</td>
		</tr>
		<tr>
        	<td width="120px" align="right">登錄證到期日：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.lsDate" id="lsDate"/>~<s:textfield key="filter.leDate" id="leDate"/>
			</td>
		</tr>
		<tr>
        	<td width="120px" align="right">登錄證字號：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.logincode" id="logincode"/>
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
				nameSpace="/aps/043"
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
		<th>發送日期</th>
		<th>登錄證字號</th>
		<th>姓名</th>
		<th>E-mail</th>
		<th>登錄證到期</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><fmt:formatDate value='${maillog}' pattern='yyyy/MM/dd'/></td>
		<td align="center"><s:property value="logincode"/></td>
		<td align="center"><s:property value="agentname"/></td>
		<td align="center"><s:property value="email"/></td>
		<td align="center"><fmt:formatDate value='${loginenddate}' pattern='yyyy/MM/dd'/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>