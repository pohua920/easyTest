<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "車險廠牌車型代號外部資料單筆維護功能";
	String image = path + "/" + "images/";
	String GAMID = "APS053E0";
	String mDescription = "車險廠牌車型代號外部資料單筆維護功能";
	String nameSpace = "/aps/053";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：CAR0563，處理人員：CD078，需求單編號：CAR0563 廠牌車型代號外部資料單筆維護查詢作業   -->
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
		$('#sDate').datepicker({dateFormat:"yyyy/mm/dd",changeMonth: true,monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"], 
		      changeYear: true,showButtonPanel: true,showMonthAfterYear: true});
		$('#eDate').datepicker({dateFormat:"yyyy/mm/dd",changeMonth: true,monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"], 
		      changeYear: true,showButtonPanel: true,showMonthAfterYear: true});
		$('#sValiddate').datepicker({dateFormat:"yyyy/mm/dd",changeMonth: true,monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"], 
		      changeYear: true,showButtonPanel: true,showMonthAfterYear: true});
		$('#eValiddate').datepicker({dateFormat:"yyyy/mm/dd",changeMonth: true,monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"], 
		      changeYear: true,showButtonPanel: true,showMonthAfterYear: true});
	});

	function checkInputDate(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		var sValiddate = $('#sValiddate').val();
		var eValiddate = $('#eValiddate').val();
		
		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '')){
			alert("製造起迄日須同時輸入");
			return false;
		}
		if((sValiddate == '' && eValiddate != '') || (sValiddate != '' && eValiddate == '')){
			alert("生效起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = (parseInt(sDateArr[0])+parseInt(1911))+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  (parseInt(eDateArr[0])+parseInt(1911))+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("製造起日須<=迄日");
			return false;
		}
		var sValiddateArr = sValiddate.split("/");
		var tsValiddateDate = (parseInt(sValiddateArr[0])+parseInt(1911))+'/'+sValiddateArr[1]+'/'+sValiddateArr[2];
		var eValiddateArr = eValiddate.split("/");
		var teValiddateDate =  (parseInt(eValiddateArr[0])+parseInt(1911))+'/'+eValiddateArr[1]+'/'+eValiddateArr[2];
		if(((new Date(teValiddateDate) - new Date(tsValiddateDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("生效起日須<=迄日");
			return false;
		}
		return true;
	}

	function form_submit(type){
		if (checkInputDate()){
			if("query" == type){
				 $("#mainForm").attr("action","btnQuery.action");
				 $("#mainForm").submit();
			}
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

<s:url action="lnkGoUpdate?prpdcarmodelext.oid=" namespace="/aps/053" var="goUpdate"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoCreate.action">
			<img src="${pageContext.request.contextPath}/images/New_Icon.gif" border="0"></a>
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- clear form -->
<!-- form 開始 -->
<s:url action="downloadFile" namespace="/aps/053" var="downloadFile"/>
<s:form theme="simple" namespace="/aps/053" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="200px" align="right">國產/進口：</td>
			<td width="285px" align="left">
				<s:select key="filter.countrycode" id="countrycode" theme="simple" list="#{'':'', '1':'國產', '2':'進口'}" />
			</td>
        	<td width="200px" align="right">廠牌：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.carbrand" id="carbrand"/>
			</td>
        	<td width="200px" align="right">名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.modelname" id="modelname"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">車種：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.carkind" id="carkind"/>
			</td>
        	<td width="200px" align="right">車種大類代號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.carkindext" id="carkindext"/>
			</td> 
        	<td width="200px" align="right">用途：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.application1" id="application1"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">生效日期：</td>
        	<td width="285px" align="left">
				<table border="0" cellspacing="0" cellpadding="0" >
					<tr>
						<td><s:textfield key="filter.sValiddate" id="sValiddate" maxlength="10" size="10" theme="simple" />~</td>
						<td><s:textfield key="filter.eValiddate" id="eValiddate" maxlength="10" size="10" theme="simple" /></td>
					</tr>
				</table>
			</td>	
        	<td width="200px" align="right">製造日期：</td>
        	<td width="285px" align="left">
				<table border="0" cellspacing="0" cellpadding="0" >
					<tr>
						<td><s:textfield key="filter.sDate" id="sDate" maxlength="10" size="10" theme="simple" />~</td>
						<td><s:textfield key="filter.eDate" id="eDate" maxlength="10" size="10" theme="simple" /></td>
					</tr>
				</table>
			</td>			       	
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢" onclick="javascript:form_submit('query');"/>
			<a href='${downloadFile}'/><input type="button" value="下載"></input>
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
				nameSpace="/aps/053"
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
		<th></th>
		<th>序號</th>	
		<th>廠牌</th>
		<th>廠牌別稱</th>
		<th>名稱</th>
		<th>國產/進口</th>
		<th>車種</th>
		<th>車種大類代號</th>
		<th>排氣量</th>
		<th>噸數</th>
		<th>用途</th>
		<th>乘載數量</th>
		<th>門數</th>
		<th>燃料</th>
		<th>排檔型式</th>
		<th>製造日期</th>
		<th>生效日期(起)</th>
		<th>生效日期(迄)</th>
		<th>備註</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="50px">
			<a href='${goUpdate}${row.oid}'>修改</a>
		</td>
		<td align="left"><s:property value="oid"/></td>
		<td align="left"><s:property value="carbrand"/></td>
		<td align="left"><s:property value="carbrandext"/></td>
		<td align="left"><s:property value="modelname"/></td>
		<td align="left">
			<s:if test='countrycode == "1"'>國產</s:if>
			<s:if test='countrycode == "2"'>進口</s:if>
		</td>
		<td align="left"><s:property value="carkind"/></td>
		<td align="left"><s:property value="carkindext"/></td>
		<td align="left"><s:property value="exhaustscale"/></td>
		<td align="left"><s:property value="tcount"/></td>		
		<td align="left"><s:property value="application1"/></td>
		<td align="left"><s:property value="seatcount"/></td>
		<td align="left"><s:property value="doors"/></td>
		<td align="left"><s:property value="fueltype"/></td>
		<td align="left"><s:property value="geartype"/></td>
		<td align="left"><s:property value="makedate"/></td>
		<td align="left"><s:property value="validdate"/></td>
		<td align="left"><s:property value="invaliddate"/></td>
		<td align="left"><s:property value="mark"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>

</body>
</html>