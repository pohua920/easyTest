<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "中信保代網投查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS039E0";
	String mDescription = "中信保代網投查詢作業";
	String nameSpace = "/aps/039";
%>
<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業  -->
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
		}
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
</script>
</head>

<s:url action="lnkGoDetailQuery?" namespace="/aps/039" var="dtlQuery"/>
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
<s:form theme="simple" namespace="/aps/039" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="200px" align="right">送件日期：</td>
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
			<td width="200px" align="right">受理編號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.fkOrderSeq" id="fkOrderSeq" />
			</td>
			<td colspan="5"></td>
			<td width="200px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policyno" id="policyno" />
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
			<td width="200px" align="right">要保人姓名：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.ownerName" id="ownerName" />
			</td>
			<td colspan="5"></td>
			<td width="200px" align="right">遞送區域代碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.areaCode" id="areaCode" />
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
			<td width="200px" align="right">受理編號狀態：</td>
			<td width="285px" align="left">
				<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 -->
				<s:select key="filter.orderSeqStatus" id="orderSeqStatus" theme="simple" list="#{'BLANK':'', '0':'進件成功(WS成功)', '1':'進件失敗(WS失敗)', 'A':'出單完成產生投保證明', 'B':'產生回饋檔成功', 
				'C':'產生回饋檔失敗', 'H':'產生投保證明失敗-SP', 'I':'產生投保證明失敗-PDF','2':'進件失敗(付款失敗)'}" />
			</td> 
			<td colspan="5"></td> 
			<td width="200px" align="right">扣款方式：</td>
			<td width="285px" align="left">
				<s:select key="filter.debitType" id="debitType" theme="simple" 
				list="#{'BLANK':'', '1':'本行帳號', '2':'本行信用卡'}" />
			</td> 
			<td colspan="5"></td>
		</tr>
		<tr>
			<td width="200px" align="right">保單寄送方式：</td>
			<td width="285px" align="left">
				<s:select key="filter.sendType" id="sendType" theme="simple" list="#{'BLANK':'', '1':'紙本', '2':'電子'}" />
			</td> 
			<td colspan="5"></td> 
			<td width="200px" align="right">保單寄送銀行：</td>
			<td width="285px" align="left">
				<s:select key="filter.sendToBank" id="sendToBank" theme="simple" list="#{'BLANK':'', '1':'產險寄送中信銀', '2':'保戶寄送中信銀'}" />
			</td> 
			<td colspan="5"></td>
		</tr>
		<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 start-->
		<tr>
			<td width="200px" align="right">回饋內容：</td>
			<td width="285px" align="left">
				<s:select key="filter.bkType" id="bkType" theme="simple" list="#{'BLANK':'', '01':'已核保', '02':'不核保'}" />
			</td> 
			<td colspan="5"></td> 
		</tr>
		<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 end-->
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
					nameSpace="/aps/039"
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
			<th>受理編號</th>	
			<th>送件時間</th>
			<th>保單號碼</th>
			<th>狀態</th>
			<th>區域代碼</th>
			<th>要保人</th>
			<th>寄送方式</th>
			<th>寄送銀行</th>
			<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 -->
			<th>回饋結果</th>
			<th>備註</th>
		</tr>
	<s:iterator var="row" value="devResults">
		<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
			<td align="center" width="110px">
				<a href='${dtlQuery}firCtbcB2b2c.batchNo=${row.batchNo}&firCtbcB2b2c.batchSeq=${row.batchSeq}&firCtbcB2b2c.fkOrderSeq=${row.fkOrderSeq}'>
				<s:property value="fkOrderSeq"/></a></td>
			<td align="center" width="110px"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
			<td align="center" width="110px"><s:property value="policyno"/></td>
			<td align="center">
				<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 -->
				<c:choose>
					<c:when test="${orderSeqStatus == '0'}">進件成功(WS成功)</c:when>
					<c:when test="${orderSeqStatus == '1'}">進件失敗(WS失敗)</c:when>
					<c:when test="${orderSeqStatus == '2'}">進件失敗(付款失敗)</c:when>
					<c:when test="${orderSeqStatus == 'A'}">出單完成產生投保證明</c:when>
					<c:when test="${orderSeqStatus == 'B'}">產生回饋檔成功</c:when>
					<c:when test="${orderSeqStatus == 'C'}">產生回饋檔失敗</c:when>
					<c:when test="${orderSeqStatus == 'H'}">產生投保證明失敗-SP</c:when>
					<c:when test="${orderSeqStatus == 'I'}">產生投保證明失敗-PDF</c:when>
				</c:choose>
			</td>
			<td align="center"><s:property value="areaCode"/></td>
			<td align="center"><s:property value="ownerName"/></td>
			<td align="center">
				<c:choose>
					<c:when test="${sendType == '1'}">紙本</c:when>
					<c:when test="${sendType == '2'}">電子</c:when>
				</c:choose>
			</td>
			<td align="center">
				<c:choose>
					<c:when test="${sendToBank == '1'}">產險寄送中信銀</c:when>
					<c:when test="${sendToBank == '2'}">保戶寄送中信銀</c:when>
				</c:choose>
			</td>
			<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 start -->
			<td align="center">
				<c:choose>
					<c:when test="${bkType == '01'}">已核保</c:when>
					<c:when test="${bkType == '02'}">不核保</c:when>
				</c:choose>
			</td>
			<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 end-->
			<td align="center"><s:property value="remark"/></td>
		</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
<br/>
</body>
</html>