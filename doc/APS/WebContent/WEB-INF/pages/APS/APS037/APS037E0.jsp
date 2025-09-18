<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "保發中心-保單存摺PRODCEDURE轉中介Table排程維護";
String image = path + "/" + "images/";
String GAMID = "APS037E0";
String mDescription = "保發中心-保單存摺PRODCEDURE轉中介Table排程維護";
String nameSpace = "/aps/037";
%>
<!-- mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 -->
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
		if("query" == type && checkInputDate()){
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
	
</script>
</head>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>

<!--form 開始 -->
<s:form theme="simple" namespace="/aps/037" id="mainForm" name="mainForm">
<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td class="image" style="width:20px"></td>	
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoExecute.action"><b>手動執行作業</b></a></span></td>
			<td colspan="3" class="imageGray"></td>
		</tr>
</table>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.batchNo" id="batchNo"  theme="simple" />
			</td>
			<td width="200px" align="right">執行排程：</td>
			<td width="285px" align="left">
				<s:select key="filter.filename" id="filename" list="#{'BLANK':'',
				'SP_OTH_PASSBOOK_MAR_P':'SP_OTH_PASSBOOK_MAR_P','SP_OTH_PASSBOOK_MAR_E':'SP_OTH_PASSBOOK_MAR_E',
				'SP_OTH_PASSBOOK_FIR_P':'SP_OTH_PASSBOOK_FIR_P','SP_OTH_PASSBOOK_FIR_E':'SP_OTH_PASSBOOK_FIR_E',
				'SP_OTH_PASSBOOK_CAL_P':'SP_OTH_PASSBOOK_CAL_P','SP_OTH_PASSBOOK_CAL_E':'SP_OTH_PASSBOOK_CAL_E',
				'SP_OTH_PASSBOOK_CAR_P':'SP_OTH_PASSBOOK_CAR_P','SP_OTH_PASSBOOK_CAR_E':'SP_OTH_PASSBOOK_CAR_E',
				'SP_OTH_PASSBOOK_LOP_P':'SP_OTH_PASSBOOK_LOP_P','SP_OTH_PASSBOOK_LOP_E':'SP_OTH_PASSBOOK_LOP_E',
				'OTH_PASSBOOK_TO_400_A':'AS400_TO_PASSBOOK'}"/>
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">批次序號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.batchSerial" id="batchSerial" size="25" theme="simple" />
			</td>
			<td width="200px" align="right">險種別：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.riskcode" id="riskcode"  theme="simple" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">批次狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.status" id="status" list="#{'BLANK':'','Y':'Y','N':'N'}"/>
			</td>
			<td width="200px" align="right">產生類型：</td>
			<td width="285px" align="left">
				<s:select key="filter.procType" id="procType" list="#{'BLANK':'','1':'保單','2':'批單','3':'AS400'}"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">執行日期：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right"></td>
			<td width="285px" align="left">
			</td>
			<td width="200px" align="right"></td>
			<td width="300px" align="left">
			※如未輸入查詢條件，預設條件為批次狀態為'N'的案件
			</td>
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
				nameSpace="/aps/037"
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
		<th>
			批次號碼
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BATCH_NO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BATCH_NO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>	
		<th>
		批次序號
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BATCH_SERIAL" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BATCH_SERIAL" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
		險種
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="RISKCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="RISKCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
		類型
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="PROC_TYPE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="PROC_TYPE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
		狀態
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="STATUS" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="STATUS" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
		筆數
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="QTY" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="QTY" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
		執行程式名稱
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="FILENAME" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="FILENAME" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
		備註
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="REMARK" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="REMARK" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
		建檔人員
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ICREATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ICREATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>
		建檔日期
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DCREATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DCREATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>	
		</th>
		<th>
		最後異動日期
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DUPDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/037/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DUPDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="120px"><s:property value="batchNo"/></td>
		<td align="center"><s:property value="batchSerial"/></td>
		<td align="center"><s:property value="riskcode"/></td>
		<td align="center"><s:property value="procType"/></td>
		<td align="center"><s:property value="status"/></td>
		<td align="center"><s:property value="qty"/></td>
		<td align="center"><s:property value="filename"/></td>
		<td align="center"><s:property value="remark"/></td>
		<td align="center"><s:property value="icreate"/></td>
		<td align="center"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center"><fmt:formatDate value='${dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
<!-- form結束 -->
</body>
</html>