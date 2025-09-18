<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "中信新件回饋檔排程執行結果查詢作業";
String image = path + "/" + "images/";
String GAMID = "APS003E0";
String mDescription = "中信新件回饋檔排程執行結果查詢作業";
String nameSpace = "/aps/003";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style>
h4 {
   width: 100%; 
   text-align: center; 
   border-bottom: 1px solid #000; 
   line-height: 0.1em;
   margin: 10px 0 20px; 
} 

h4 span { 
    background:#efefef;; 
    padding:0 10px; 
}
</style>
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
		//$('#sDate').datePicker({"startDate":"01/01/1911","dateType" :"roc","dateFormat":"yyy/mm/dd"});
		//$('#eDate').datePicker({"startDate":"01/01/1911","dateType" :"roc","dateFormat":"yyy/mm/dd"});
		//$('#sDate').datePicker({"startDate":"01/01/1911","dateType" :"ad","dateFormat":"yyyy/mm/dd"});
		//$('#eDate').datePicker({"startDate":"01/01/1911","dateType" :"ad","dateFormat":"yyyy/mm/dd"});
		//$.datepicker.setDefaults($.datepicker.regional['zh-TW']);
		$('#sDate').datepicker({dateFormat:"yy/mm/dd"});
		$('#eDate').datepicker({dateFormat:"yy/mm/dd"});
		
		//validate
	});
			
	function form_submit(type){
		 //$("label").html('');
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		if("query2" == type){
			 $("#mainForm").attr("action","btnQuery2.action");
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

<!-- clear form -->
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/003" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/003" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">執行日期：</td>
			<td width="285px" align="left">
				<table border="0" cellspacing="0" cellpadding="0" >
					<tr>
						<td><s:textfield key="filter.sDate" id="sDate" maxlength="10" size="10" theme="simple" />~</td>
						<td><s:textfield key="filter.eDate" id="eDate" maxlength="10" size="10" theme="simple" /></td>
					</tr>
				</table>
			</td>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.batchNo" id="batchNo" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td width="200px" align="right">RST檔名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.filenameRst" id="filenameRst" theme="simple" />
			</td>
			<td width="200px" align="right">回饋類型：</td>
			<td width="285px" align="left">
				<s:select key="filter.rstType" id="rstType" list="rstTypeMap"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">受理編號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.fkOrderSeq" id="fkOrderSeq" theme="simple" />
			</td>
			<td width="200px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policyno" id="policyno"  theme="simple" />
			</td>			
		</tr>
		<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start-->
		<tr>
			<td width="200px" align="right">歸屬單位：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.comcode" id="comcode" theme="simple" />
			</td>
			<td width="200px" align="right">服務人員：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.handler1code" id="handler1code"  theme="simple" />
			</td>	
		<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end-->		
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
				nameSpace="/aps/003"
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
		<th>受理編號</th>
		<th>回饋類型</th>
		<th>核保狀態</th>
		<th>失敗原因</th>
		<th>保單號碼</th>
		<th>回饋檔檔名</th>
		<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start-->
		<th>歸屬單位</th>
		<th>服務人員</th>
		<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end-->
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left"><s:property value="batchNo"/></td>
		<td align="left"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="left"><s:property value="fkOrderSeq"/></td>
		<td align="left">
			<!-- mantis：FIR0498，處理人員：BJ085，需求單編號：FIR0498 中信保代網投_新件回饋檔排程查詢作業規格_新增保經代網投 -->
			<c:choose>
				<c:when test="${rstType == '1'}">資料異常</c:when>
				<c:when test="${rstType == '2'}">核心撤單</c:when>
				<c:when test="${rstType == '3'}">核心出單</c:when>
				<c:when test="${rstType == '4'}">續保叫單</c:when>
				<c:when test="${rstType == '5'}">保代網投</c:when>
				<c:otherwise>未定義</c:otherwise>
			</c:choose>
		</td>
		<td align="left">
			<c:choose>
				<c:when test="${inscoStatus == '01'}">已核保</c:when>
				<c:when test="${inscoStatus == '02'}">不核保</c:when>
				<c:otherwise>未定義</c:otherwise>
			</c:choose>
		</td>
		<td align="left"><s:property value="inscoFeedback"/></td>
		<td align="left"><s:property value="policyno"/></td>
		<td align="left"><s:property value="filenameRst"/></td>
		<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start-->
		<td align="left"><s:property value="comcode"/></td>
		<td align="left"><s:property value="handler1code"/></td>
		<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end-->
	</tr>
	</s:iterator>
</table>
</s:if>

</s:form>
<!-- form結束 -->
</body>
</html>