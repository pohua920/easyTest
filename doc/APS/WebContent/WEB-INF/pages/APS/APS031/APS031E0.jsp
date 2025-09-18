<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "保單通訊資料產生及下載作業";
	String image = path + "/" + "images/";
	String GAMID = "APS031E0";
	String mDescription = "保單通訊資料產生及下載作業";
	String nameSpace = "/aps/031";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：FIR0427，處理人員：BJ085，需求單編號：FIR0427 個人險-保單通訊資料產生及下載作業-->
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
		/*
		if("test" == type ){
			 $("#mainForm").attr("action","btnExcuteBatch.action");
			 $("#mainForm").submit();
		}
		*/
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
			alert("執行日期起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = sDateArr[0]+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  eDateArr[0]+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("執行日期起日須<=迄日");
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

<s:url action="btnVoidData?bno=" namespace="/aps/031" var="goVoid"/>
<s:url action="btnDownloadFile?" namespace="/aps/031" var="downloadFile"/>
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
<s:form theme="simple" namespace="/aps/031" id="mainForm" name="mainForm">
<s:hidden name="token" id="token"/>
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoCreate.action"><b>轉入作業</b></a></span>
			</td>
			<td class="imageGray" style="width:20px"></td>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">執行日期：</td>
			<td width="350px" align="left">
			<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
			<td width="200px" align="right">批次號碼：</td>
			<td width="350px" align="left">
				<s:textfield key="filter.bno" id="bno" theme="simple"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">狀態：</td>
			<td width="350px" align="left">
				<s:select key="filter.datastatus" id="datastatus" theme="simple" 
				list="#{'BLANK':'請選擇', '0':'尚未產生資料', '1':'產生資料中', '2':'產生資料失敗', '3':'產生資料成功', '4':'產生檔案中', '5':'產生檔案失敗', '6':'產生檔案成功'}" />
			</td> 
			<td></td>
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>
			<!-- <input type="button" value="測試" onclick="javascript:form_submit('test');"/> -->
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
				nameSpace="/aps/031"
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
		<th width="110px">批次號碼
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BNO" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="BNO" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>	
		<th>資料日期區間
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="PARAMSTARTDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="PARAMSTARTDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>基準日<br>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="PARAMBASEDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="PARAMBASEDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>險種<br>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="PARAMRISKCODE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="PARAMRISKCODE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>執行時間<br>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DDATASTART" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DDATASTART" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>執行<br>人員<br>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ICREATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="ICREATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>異動時間
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DUPDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DUPDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>異動<br>人員<br>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IUPDATE" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="IUPDATE" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>資料總筆數<br>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DATACOUNT" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DATACOUNT" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>狀態<br>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DATASTATUS" />
				<c:param name="filter.sortType" value="ASC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▲</a>
			<c:url value="/aps/031/lnkSortQuery.action" var="sortURL">
				<c:param name="filter.sortBy" value="DATASTATUS" />
				<c:param name="filter.sortType" value="DESC" />
			</c:url>
			<a href='<c:out value="${sortURL}" />'>▼</a>
		</th>
		<th>檔案下載</th>
		<th>作廢</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><s:property value="bno"/></td>
		<td align="center"><s:property value="paramstartdate"/>~<s:property value="paramenddate"/></td>
		<td align="center"><s:property value="parambasedate"/></td>
		<td align="center">
			<s:if test='paramriskcode.contains("A01,B01")'>車險</s:if>
			<s:if test='paramriskcode.contains("GA,PA,TA,HP")'>A&H</s:if>
			<s:if test='paramriskcode.contains("F02")'>火險</s:if>
		</td>
		<td align="center"><fmt:formatDate value='${ddatastart}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center"><s:property value="icreate"/></td>
		<td align="center"><fmt:formatDate value='${dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center"><s:property value="iupdate"/></td>
		<td align="center"><s:property value="datacount"/></td>
		<td align="center">
			<s:if test='datastatus == "0"'>尚未產生資料</s:if>
			<s:elseif test='datastatus == "1"'>產生資料中</s:elseif>
			<s:elseif test='datastatus == "2"'>產生資料失敗</s:elseif>
			<s:elseif test='datastatus == "3"'>產生資料成功</s:elseif>
			<s:elseif test='datastatus == "4"'>產生檔案中</s:elseif>
			<s:elseif test='datastatus == "5"'>產生檔案失敗</s:elseif>
			<s:elseif test='datastatus == "6"'>產生檔案成功</s:elseif>
		</td>
		<td align="center">
			<s:if test='datastatus == "6"'><a href='${downloadFile}bno=${row.bno}'><input type="button" value="下載"></a></s:if>
			<s:else></s:else>
		</td>
		<td align="center">
			<s:if test='datastatus == "6" || datastatus == "1" || datastatus == "4"'></s:if>
			<s:elseif test=' isvoid == "Y"'>已作廢</s:elseif>
			<s:elseif test='isvoid == "N"'>
				<a href='${goVoid}${row.bno}'><input type="button" value="作廢" ></a>
			</s:elseif>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>