<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "臺銀簽署檔FD查詢作業_續保資料出單狀態查詢";
	String image = path + "/" + "images/";
	String GAMID = "APS057E1";
	String mDescription = "續保資料出單狀態查詢";
	String nameSpace = "/aps/057";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業 -->
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
		if("query" == type && checkMainQuery()){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		
		if("btnDownloadFile" == type){
			doBlockUI(type);
		}
	}
	
	function checkMainQuery(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		var orderseq = $('#orderseq').val();
		
		var adRegex=/^([0-9]\d{3}\/(0?[1-9]|1[012])\/(0?[1-9]|[12][0-9]|3[01]))*$/;
		if(!adRegex.test(sDate) || !adRegex.test(eDate)){
			alert("請輸入正確日期格式");
			return false;
		}
		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '')){
			alert("執行起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = sDateArr[0]+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  eDateArr[0]+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("執行起日須<=迄日");
			return false;
		}
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) > 120){
			alert("執行起訖日應介於120天內");
			return false;
		}
// 		if((sDate == '' || eDate == '') && orderseq == ''){
// 			alert("請最少輸入一個查詢條件。");
// 			return false;
// 		}
		return true;
	}
	
	function doBlockUI(type){
		var token = new Date().getTime();
		$.blockUI({
			//blockUI：設定頁面指定區域顯示執行中文字(如Loading...)並鎖定該區域限制輸入。
			border: 'none',
			padding: '15px',//padding：同時設定四個邊留白的捷徑屬性
			backgroundColor: '#000',//backgroundColor：訊息背景顏色
			color: '#fff',//color：訊息字樣顏色
			'-webkit-border-radius': '10px',//WebKit設定四邊的圓角為10px
			'-moz-border-radius': '10px',//Mozilla設定四邊的圓角為10px
			opacity: .5//opacity：指定表單和其控制項的透明度等級
		});

	    $("#token").val(token);
		$("#mainForm").attr("action",type + ".action");
		$("#mainForm").submit();
		var pollDownload = setInterval(function() {
	        if (document.cookie.indexOf(type + "=" + token) > -1) {
	            $.unblockUI();
	            clearInterval(pollDownload);
	        }
	    }, 1000);
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
<s:form theme="simple" namespace="/aps/057" id="mainForm" name="mainForm">
	<s:hidden name="token" id="token"/>
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/goQuery1.action"><b>臺銀簽署檔FD查詢</b></a></span>
			</td>
			<td class="imageGray" style="width:20px"></td>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>續保資料出單狀態查詢</b></span></td>
			<td class="image" style="width:20px"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<s:hidden key="queryType" id="queryType" value="query2"></s:hidden>
		<tr>
        	<td width="120px" align="right">接收日期：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
			<td width="120px" align="right">批次號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.batchNo" id="batchNo" theme="simple" />
			</td>
		</tr>
		<tr>
        	<td width="120px" align="right">續保單號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.oldpolicyno" id="oldpolicyno" theme="simple"/>
			</td>
			<td width="120px" align="right">受理編號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.orderseq" id="orderseq" theme="simple" />
			</td>
		</tr>
		<tr>
        	<td width="120px" align="right">資料比對註記：</td>
			<td width="285px" align="left">
				<s:select key="filter.compareFlag" id="compareFlag" theme="simple" list="#{'':'','Y':'比對完成','N':'比對未完成','E':'比對、檢核異常'}" />
			</td>
		</tr>
		<tr>
        	<td width="120px" align="right">說明：</td>
			<td width="1400px" align="left">
				本功能查詢台銀續保已鎖定需轉入核心出單資料與簽署檔簽署情形及出單狀況。<br>
			</td>
			<td colspan="5"></td>       	
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>
			<input type="button" value="下載xls" onclick="javascript:form_submit('btnDownloadFile');" />
		</td>
	</tr>
</table>
<s:if test="devResults1 != null && 0 != devResults1.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/057"
				currentPage="${pageInfo.currentPage}" 
				pageSize="${pageInfo.pageSize}"   
				selectOnChange="ddlPageSizeChanged1" 
				textOnChange="txtChangePageIndex1"  
				rowCount="${pageInfo.rowCount}"
				pageCount="${pageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>
			批次號碼
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="BATCH_NO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="BATCH_NO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			受理編號
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="ORDERSEQ" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="ORDERSEQ" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			續保單號
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="OLDPOLICYNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="OLDPOLICYNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>資料狀態</th>
		<th>
			簽署日期
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="FD_DATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="FD_DATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			轉核心要保書號
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="PROPOSALNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="PROPOSALNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
			轉核心保單號
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="POLICYNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query2" />
				<c:param name="filter.sortBy" value="POLICYNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>資料比對註記</th>
		<th>資料比對結果</th>
	</tr>
	<s:iterator var="row" value="devResults1">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="125px"><s:property value="batchNo"/></td>
		<td align="center"><s:property value="orderseq"/></td>
		<td align="center"><s:property value="oldpolicyno"/></td>
		<td align="center">
			<c:if test="${dataStatus == '0'}">未處理</c:if>
			<c:if test="${dataStatus == '1'}">APS暫存失敗</c:if>
			<c:if test="${dataStatus == '2'}">APS暫存成功</c:if>
			<c:if test="${dataStatus == '3'}">轉核心暫存成功</c:if>
			<c:if test="${dataStatus == '4'}">轉核心暫存失敗</c:if>
			<c:if test="${dataStatus == '5'}">轉核心要保成功</c:if>
			<c:if test="${dataStatus == '6'}">轉核心要保失敗</c:if>
			<c:if test="${dataStatus == '7'}">轉核心完成</c:if>
			<c:if test="${dataStatus == '8'}">轉核心失敗-收付失敗</c:if>
			<c:if test="${dataStatus == 'A'}">人工判定不轉核心</c:if>
			<c:if test="${dataStatus == 'B'}">臺銀已鎖定尚未比對簽署檔</c:if>
			<c:if test="${dataStatus == 'C'}">臺銀比對簽署檔不一致</c:if>
		</td>
		<td align="center"><fmt:formatDate value='${fdDate}' pattern='yyyy/MM/dd'/></td>
		<td align="center"><s:property value="proposalno"/></td>
		<td align="center"><s:property value="policyno"/></td>
		<td align="center">
			<c:if test="${compareFlag == 'N'}">未完成</c:if>
			<c:if test="${compareFlag == 'Y'}">完成</c:if>
			<c:if test="${compareFlag == 'E'}">比對、檢核異常</c:if>
		</td>
		<td align="center"><s:property value="compareResult"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>