<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "板信續保資料處理作業";
	String image = path + "/" + "images/";
	String GAMID = "APS016E0";
	String mDescription = "板信續保資料處理作業";
	String nameSpace = "/aps/016";
%>
<!-- mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業 start -->
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
	
		//加上小日曆
		$('#sDate').datepicker({dateFormat:"yy/mm/dd"});
		$('#eDate').datepicker({dateFormat:"yy/mm/dd"});

	});

	function form_submit(type){
		if(checkInputDate()){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();				
		}else return false;
	}
	
	function checkInputDate(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();

		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '')){
			alert("執行起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = (parseInt(sDateArr[0])+parseInt(1911))+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  (parseInt(eDateArr[0])+parseInt(1911))+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("執行起日須<=迄日");
			return false;
		}else if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) > 60){
			alert("執行起訖日應介於60天內");
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
	
	function checkFileStatus(fileStatus,dataqtyS){
		if(fileStatus != 'S' || parseInt(dataqtyS) <= 0){
			alert("檔案狀態為正常且成功筆數>0時才可以下載XLS");
			return false;
		}else if(fileStatus == 'S'){
			return true;
		}
	}
	
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start*/
	function confirmFixData(){
		if(!confirm("請確認是否要進行整批鎖定，鎖定後將無法再調整要保資料，包含剔退註記也無法調整。")){
			return false;
		}
	}
	/*mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end*/
	
</script>
</head>

<s:url action="lnkGoDetailQuery?" namespace="/aps/016" var="dtlQuery"/>
<s:url action="btnGenExcel?" namespace="/aps/016" var="genFile"/>
<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 -->
<s:url action="btnFixData?" namespace="/aps/016" var="fixData"/>
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
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/016" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">執行日期：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
			<td colspan="5"></td>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.batchNo" id="batchNo" />
			</td> 
			<td colspan="5"></td>   
		</tr>
		<tr>
        	<td width="200px" align="right">檔案狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.fileStatus" id="fileStatus" theme="simple" list="#{'BLANK':'', 'S':'正常', 'A':'新增暫存錯誤', 'Z':'檔案無資料'}" />
			</td> 
			<td colspan="5"></td> 
			<td width="200px" align="right">轉暫存檔狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.transStatus" id="transStatus" theme="simple" list="#{'BLANK':'', 'N':'未處理', 'Y':'已處理', 'E':'異常'}" />
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
				nameSpace="/aps/016"
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
		<th>批次號碼</th>	
		<th>執行時間</th>
		<th>預計筆數</th>
		<th>成功筆數</th>
		<th>失敗筆數</th>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 -->
		<th>剔退筆數</th>
		<th>檔案狀態</th>
		<th>轉暫存檔狀態</th>
		<th>檔案下載</th>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 -->
		<th>鎖定</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left">
		<a href='${dtlQuery}firAgtrnBatchMain.batchNo=${row.batchNo}'>
		<s:property value="batchNo"/></a>
		</td>
		<td align="left"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="left"><s:property value="dataqtyT"/></td>
		<td align="left"><s:property value="dataqtyS"/></td>
		<td align="left"><s:property value="dataqtyF"/></td>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 -->
		<td align="left"><s:property value="dataqtySf"/></td>
		<td align="left">
			<c:choose>
				<c:when test="${fileStatus == 'S'}">正常</c:when>
				<c:when test="${fileStatus == 'A'}">新增暫存錯誤</c:when>
				<c:when test="${fileStatus == 'Z'}">檔案無資料</c:when>
				<c:otherwise>未定義</c:otherwise>
			</c:choose>
		</td>
		
		<td align="left">
			<c:choose>
				<c:when test="${transStatus == 'N'}">未處理</c:when>
				<c:when test="${transStatus == 'Y'}">已處理</c:when>
				<c:when test="${transStatus == 'E'}">異常</c:when>
				<c:otherwise>未定義</c:otherwise>
			</c:choose>
		</td>
		<td align="left"><a href="${genFile}firAgtrnBatchMain.batchNo=${row.batchNo}" onclick="return checkFileStatus('${row.fileStatus}','${row.dataqtyS}');">下載XLS</a></td>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 start-->
		<td align="left">
			<c:choose>
				<c:when test="${dataqtyS > 0 && fileStatus == 'S' && dataqtyS > dataqtyFix + dataqtySf }">
					<a href="${fixData}firAgtrnBatchMain.batchNo=${row.batchNo}" onclick="return confirm('請確認是否要進行整批鎖定，鎖定後將無法再調整要保資料，包含剔退註記也無法調整。');">整批鎖定</a>
				</c:when>
				<c:when test="${dataqtyS > 0 && fileStatus == 'S' && dataqtyFix + dataqtySf == dataqtyS}">
					已鎖定
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
		</td>
		<!-- mantis：FIR0590，處理人員：BJ085，需求單編號：FIR0590 住火_APS板信續保作業_第二年優化_維護作業 end-->
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
<br/>
</body>
</html>