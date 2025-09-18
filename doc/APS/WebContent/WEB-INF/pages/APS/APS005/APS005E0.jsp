<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "火險地址匯入及查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS005E0";
	String mDescription = "火險地址匯入及查詢作業";
	String nameSpace = "/aps/005";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入  start
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
	
		//加上小日曆
		$('#sDate').datepicker({dateFormat:"yy/mm/dd"});
		$('#eDate').datepicker({dateFormat:"yy/mm/dd"});

	});

	function form_submit(type){
		if("query" == type){
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

<s:url action="btnQuery2?firAddrImportlist.oid=" namespace="/aps/005" var="importFail"/>
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
<s:form theme="simple" namespace="/aps/005" id="mainForm" name="mainForm">
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
        	<td width="200px" align="right">匯入日期：</td>
			<td width="285px" align="left">
			<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">上傳類型：</td>
			<td width="285px" align="left">
				<s:select key="filter.ultype" id="ultype" theme="simple" list="#{'*':'全部 ','1':'1.全刪全增 ', '2':'2.部份增修'}" />
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
        	<td width="200px" align="right">檔案狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.fileStatus" id="fileStatus" theme="simple" list="#{'All':'全部 ', 'N':'未處理', 'Y':'已處理', 'X':'不處理', 'E':'異常'}" />
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
				nameSpace="/aps/005"
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
		<th>匯入檔名</th>	
		<th>上傳類型</th>
		<th>檔案狀態</th>
		<th>資料筆數</th>
		<th>成功筆數</th>
		<th>失敗筆數</th>
		<th>備註</th>
		<th>建檔人員</th>
		<th>建檔日期</th>
		<th>異動人員</th>
		<th>異動日期</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left"><s:property value="filenameOri"/></td>
		<td align="left">
			<c:if test="${ultype == '1'}">全刪全增</c:if>
			<c:if test="${ultype == '2'}">部份增修</c:if>
		</td>
		<td align="left"><s:property value="fileStatus"/></td>
		<td align="left"><s:property value="qtyAll"/></td>
		<td align="left"><s:property value="qtyOk"/></td>
		<c:choose>
			<c:when test="${qtyNg >= 1}">
				<td align="left"><a href='${importFail}${row.oid}&firAddrImportlist.filenameNew=${filenameNew}'><s:property value="qtyNg"/></a></td>
			</c:when>
			<c:otherwise>
				<td align="left"><s:property value="qtyNg"/></td>
			</c:otherwise>
		</c:choose>
		<td align="left"><s:property value="remark"/></td>
		<td align="left"><s:property value="icreate"/></td>
		<td align="left"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="left"><s:property value="iupdate"/></td>
		<td align="left"><fmt:formatDate value='${dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>