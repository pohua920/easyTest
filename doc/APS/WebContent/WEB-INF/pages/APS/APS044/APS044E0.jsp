<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "續保通知查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS044E0";
	String mDescription = "續保通知查詢作業";
	String nameSpace = "/aps/044";
%>
<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 -->
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
		$('#sDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#eDate').datepicker({dateFormat:"yyyy/mm/dd"});
	
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
	
</script>
</head>
<s:url action="lnkGoDetailQuery?" namespace="/aps/044" var="dtlQuery"/>
<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 -->
<s:url action="lnkGoDownload" namespace="/aps/044" var="downloadQuery"/>
<body style="margin: 0; text-align: left">
	<table cellspacing="1" cellpadding="1" width="970px" border="0">
		<tbody>
			<tr>
				<td width="485px"><img
					src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0"></td>
				<td align="right" width="485px">PGMID：<%=GAMID%></td>
			</tr>
			<tr>
				<td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
			</tr>
		</tbody>
	</table>
	<s:form theme="simple" namespace="/aps/034" id="mainForm"
		name="mainForm">
		<table id="table2" cellspacing="0" cellpadding="0" width="970px">
			<tr>
				<td class="MainTdColor" style="width: 200px" align="center">
					<span id="lbSearch"><b>查詢作業</b></span>
				</td>
				<td colspan="3" class="image"></td>
			</tr>
		</table>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
			<tr>
				<td width="200px" align="right">執行日期：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.sDate" id="sDate" />
					~<s:textfield key="filter.eDate" id="eDate" />
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">續保年月(YYYYMM)：</td>
				<td width="285px" align="left">
					<s:textfield maxlength="6" key="filter.rnYymm" id="rnYymm" />
				</td>
				<td colspan="5"></td>
			</tr>
			<tr>
				<td width="200px" align="right">續保單號：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.oldPolicyno" id="oldPolicyno" />
				</td>
			</tr>
		</table>
		<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start -->
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
			<tr>
				<td width="145px" align="right">說明：</td>
				<td width="550px" align="left">
				<span>本作業於每月5日22:30執行排程抓取次次月(如6/5抓8月)到期且於新核心出單之應續保明細(已排除註銷及退保)，</span><br>
				<span>若於排程之後進行批改，則資料件數可能會有差異。</span>
			</td>
			</tr>
		</table>
		<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end -->
		<table width="970px" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
					<input type="button" value="查詢" onclick="javascript:form_submit('query');" />
				</td>
			</tr>
		</table>
		<s:if test="devResults != null && 0 != devResults.size">
			<table cellspacing="1" cellpadding="1" border="0" width="970px">
				<tr>
					<td>
						<div align="right">
							<custom:ddlChangePage formId="mainForm" name="pageInfo"
								nameSpace="/aps/044" currentPage="${pageInfo.currentPage}"
								pageSize="${pageInfo.pageSize}"
								selectOnChange="ddlPageSizeChanged"
								textOnChange="txtChangePageIndex"
								rowCount="${pageInfo.rowCount}"
								pageCount="${pageInfo.pageCount}" />
						</div>
					</td>
				</tr>
			</table>
			<!--Grid Table-->
			<table border="1" id="gridtable" width="970px" border="0"
				class="main_table">
				<tr align="center">
					<th>
						批次號碼
						<c:url value="/aps/044/lnkSortQuery.action" var="sortURL">
							<c:param name="filter.sortBy" value="BATCH_NO" />
							<c:param name="filter.sortType" value="ASC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▲</a>
						<c:url value="/aps/044/lnkSortQuery.action" var="sortURL">
							<c:param name="filter.sortBy" value="BATCH_NO" />
							<c:param name="filter.sortType" value="DESC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▼</a>
					</th>
					<th>
						續保年月
						<c:url value="/aps/044/lnkSortQuery.action" var="sortURL">
							<c:param name="filter.sortBy" value="RN_YYMM" />
							<c:param name="filter.sortType" value="ASC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▲</a>
						<c:url value="/aps/044/lnkSortQuery.action" var="sortURL">
							<c:param name="filter.sortBy" value="RN_YYMM" />
							<c:param name="filter.sortType" value="DESC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▼</a>
					</th>
					<th>
						執行時間
						<c:url value="/aps/044/lnkSortQuery.action" var="sortURL">
							<c:param name="filter.sortBy" value="DCREATE" />
							<c:param name="filter.sortType" value="ASC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▲</a>
						<c:url value="/aps/044/lnkSortQuery.action" var="sortURL">
							<c:param name="filter.sortBy" value="DCREATE" />
							<c:param name="filter.sortType" value="DESC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▼</a>
					</th>
					<th>筆數</th>
					<th>重算地震保額</th>
					<th>重算火險保額</th>
					<th>產檔處理</th>
				</tr>
				<s:iterator var="row" value="devResults">
				
					<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
						<td align="left">
							<a href='${dtlQuery}batchNo=${row.batchNo}
							&rnYymm=${row.rnYymm}'>
							<s:property value="batchNo" /></a>
						</td>
						<td align="left"><s:property value="rnYymm" /></td>
						<td align="left">
							<fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss' />
						</td>
						<td align="left"><s:property value="dataqty" /></td>
						<td align="left"><s:property value="qcamtFlag" /></td>
						<td align="left"><s:property value="fcamtFlag" /></td>
						<td align="left">
							<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start -->
							<c:choose>
								<c:when test="${deleteFlag == 'Y'}"></c:when>
								<c:when test="${deleteFlag == 'N'}">
									<a href="${downloadQuery}?firRenewList.batchNo=${row.batchNo}">進入</a>
								</c:when>
							</c:choose>
							<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end -->
						</td>
					</tr>
				</s:iterator>
			</table>
		</s:if>
	</s:form>
</body>
</html>