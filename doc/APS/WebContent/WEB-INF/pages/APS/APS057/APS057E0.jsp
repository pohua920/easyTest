<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "臺銀簽署檔FD查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS057E0";
	String mDescription = "臺銀簽署檔FD查詢作業";
	String nameSpace = "/aps/057";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0624，處理人員：BJ085，需求單編號：FIR0624 住火_臺銀續保作業_臺銀FD檔查詢作業-->
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
	}
	
	function checkMainQuery(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		var policyNo = $('#policyNo').val();
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
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) > 60){
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
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>臺銀簽署檔FD查詢</b></a></span>
			</td>
			<td class="image" style="width:20px"></td>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/goQuery2.action"><b>續保資料出單狀態查詢</b></span></td>
			<td class="imageGray" style="width:20px"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<s:hidden key="queryType" id="queryType" value="query1"></s:hidden>
		<tr>
        	<td width="200px" align="right">接收日期：</td>
			<td width="250px" align="left">
			<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
			<td width="200px" align="right">批次號碼：</td>
			<td width="250px" align="left">
				<s:textfield key="filter.batchNo" id="batchNo" theme="simple" size="20" maxlength="25" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">受理編號：</td>
			<td width="250px" align="left">
				<s:textfield key="filter.orderseq" id="batchNo" theme="simple" size="20" maxlength="20"/>
			</td>
			<td width="200px" align="right">類型：</td>
			<td width="250px" align="left">
				<s:select key="filter.fdType" id="fdType" theme="simple" list="#{'':'','新保':'新保','續保':'續保','批改':'批改'}" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">資料比對註記：</td>
			<td width="250px" align="left">
				<s:select key="filter.compareFlag" id="compareFlag" theme="simple" list="#{'':'','Y':'比對完成','N':'比對未完成','E':'比對、檢核異常'}" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">說明：</td>
			<td width="1200px" align="left">
				1.每小時執行排程取得臺銀SFTP簽署檔資料，此功能供查詢目前簽署檔資料及比對續件資料狀態。<br>
				2.「資料比對註記」：<br>
					&ensp;&ensp;&ensp;A.完成:續保資料鎖定並比對完成，狀態變更為可轉核心。<br>
					&ensp;&ensp;&ensp;B.未完成:尚未比對鎖定續保資料或鎖定續保資料查無此筆簽署檔受理資料。<br>
					&ensp;&ensp;&ensp;C.比對、檢核異常:與鎖定後續保資料比對時有資料不符合或是簽署檔資料異常。<br>
				3.比對、檢核異常狀態若確認後需調整 續保資料後重新轉核心。<br>
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
				<c:param name="queryType" value="query1" />
				<c:param name="filter.sortBy" value="BATCH_NO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/057/lnkSortQuery.action" var="sortURL">
				<c:param name="queryType" value="query1" />
				<c:param name="filter.sortBy" value="BATCH_NO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>類型</th>
		<th>受理編號</th>
		<th>簽署日期</th>
		<th>保單生效日(火險)</th>
		<th>保單生效日(地震)</th>
		<th>火險保額</th>
		<th>地震險保額</th>
		<th>資料比對註記</th>
		<th>資料比對結果</th>
		<th>核心保單號</th>
	</tr>
	<s:iterator var="row" value="devResults1">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="125px"><s:property value="batchNo"/></td>
		<td align="center" width="30px"><s:property value="fdType"/></td>
		<td align="center" width="130px"><s:property value="orderseq"/></td>
		<td align="center" width="60px"><fmt:formatDate value='${fdDate}' pattern='yyyy/MM/dd'/></td>
		<td align="center" width="60px"><fmt:formatDate value='${startdateF}' pattern='yyyy/MM/dd'/></td>
		<td align="center" width="60px"><fmt:formatDate value='${startdateQ}' pattern='yyyy/MM/dd'/></td>
		<td align="center" width="50px"><s:property value="amountF"/></td>
		<td align="center" width="50px"><s:property value="amountQ"/></td>
		<td align="center" width="40px">
			<c:if test="${compareFlag == 'N'}">未完成</c:if>
			<c:if test="${compareFlag == 'Y'}">完成</c:if>
			<c:if test="${compareFlag == 'E'}">比對、檢核異常</c:if>
		</td>
		<td align="center"><s:property value="compareResult"/></td>
		<td align="center" width="100px"><s:property value="policyno"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>