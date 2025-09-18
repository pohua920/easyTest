<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "AS400外銀續保匯入查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS029E0";
	String mDescription = "AS400外銀續保匯入查詢作業";
	String nameSpace = "/aps/029";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入-->
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
		$('#sDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#eDate').datepicker({dateFormat:"yyyy/mm/dd"});

	});

	function form_submit(type){
		if("query" == type && checkInputDate()){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
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
			alert("匯入日期起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = sDateArr[0]+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  eDateArr[0]+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("匯入日期起日須<=迄日");
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

<s:url action="btnQuery2?" namespace="/aps/029" var="importSuccess"/>
<s:url action="btnQuery3?" namespace="/aps/029" var="importFail"/>
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
<s:form theme="simple" namespace="/aps/029" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoUpload.action"><b>轉入作業</b></a></span>
			</td>
			<td class="imageGray" style="width:20px"></td>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">業務來源：</td>
			<td width="350px" align="left">
				<!-- mantis：FIR0619，處理人員：CD078，需求單編號：FIR0619 住火_臺銀續保作業_AS400資料匯入新增臺銀格式 -->
				<!-- mantis：FIR0666，處理人員：DP0714，住火_元大續保作業_AS400資料匯入新增元大格式 -->
				<s:select key="filter.businessnature" id="businessnature" theme="simple" list="#{'':'','I99065':'I99065-板信','I99060':'I99060-日盛','I99004':'I99004-臺銀','I00006':'I00006-元大'}" />
			</td>
			<td></td>
		</tr>
		<tr>
        	<td width="200px" align="right">匯入日期：</td>
			<td width="350px" align="left">
			<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
			<td></td>
		</tr>
		<tr>
        	<td width="200px" align="right">續保年月：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.rnYyyymm" id="rnYyyymm"/>(西元年YYYYMM，例如：202110)
			</td> 
			<td></td>       	
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
				nameSpace="/aps/029"
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
		<th>業務來源</th>	
		<th>續保年月</th>
		<th>匯入檔名</th>
		<th>資料<br>筆數</th>
		<th>成功<br>筆數</th>
		<th>失敗<br>筆數</th>
		<th>備註</th>
		<th>建檔<br>人員</th>
		<th>建檔日期</th>
		<th>異動<br>人員</th>
		<th>異動日期</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><s:property value="businessnature"/></td>
		<td align="center"><s:property value="rnYyyymm"/></td>
		<td width="23%" align="center"><s:property value="filename"/></td>
		<td align="center"><s:property value="qtyAll"/></td>
		<c:choose>
			<c:when test="${qtyOk >= 1}">
				<td align="center"><a href='${importSuccess}firAgtrnAs400DataUplist.rnYyyymm=${rnYyyymm}&firAgtrnAs400DataUplist.businessnature=${businessnature}'><s:property value="qtyOk"/></a></td>
			</c:when>
			<c:otherwise>
				<td align="center"><s:property value="qtyOk"/></td>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${qtyNg >= 1}">
				<td align="center">
					<a href='${importFail}firAgtrnAs400DataUplist.rnYyyymm=${rnYyyymm}&firAgtrnAs400DataUplist.businessnature=${businessnature}'><s:property value="qtyNg"/></a>
				</td>
			</c:when>
			<c:otherwise>
				<td align="center"><s:property value="qtyNg"/></td>
			</c:otherwise>
		</c:choose>
		<td width="27%" align="center"><s:property value="remark"/></td>
		<td align="center"><s:property value="icreate"/></td>
		<td align="center"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center"><s:property value="iupdate"/></td>
		<td align="center"><fmt:formatDate value='${dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>