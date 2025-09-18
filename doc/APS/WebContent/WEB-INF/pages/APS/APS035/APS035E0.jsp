<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "中信續件要保書查詢作業";
String image = path + "/" + "images/";
String GAMID = "APS035E0";
String mDescription = "中信續件要保書查詢作業";
String nameSpace = "/aps/035";
%>
<!-- mantis：FIR0459，處理人員：CC009，需求單編號：FIR0459 住火-APS中信續件要保書-排程查詢作業 -->
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
	
		$('#sDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#eDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#dtlStartdate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#dtlDataDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#dtlSDate').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#dtlEDate').datepicker({dateFormat:"yyyy/mm/dd"});
		
		//validate
	});
			
	function form_submit(type){
		 //$("label").html('');
		if("query" == type && checkMainInputData()){
			$("#mainForm").attr("action","btnQuery.action");
			$("#mainForm").submit();
		}
		if("query2" == type && checkDtlInputData()){
			 $("#mainForm").attr("action","btnQuery2.action");
			 $("#mainForm").submit();
		}	
	}
	
	function downloadPDF(pdfName,dcreate){
		if(pdfName == null || pdfName.lenght <0){
			alert("此保單無對應要保書，無法下載。");
		}else{
			var url = "/APS/aps/035/lnkDownloadPDF.action?aps035DetailVo.pdfName=" + pdfName 
			+ "&aps035DetailVo.dcreate=" + dcreate;
			
			window.location.href = url;
		}
	}
	
	function checkMainInputData(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		var fileStatus = $('#fileStatus').val();
		var batchNo = $('#batchNo').val();
		var batchSeq = $('#batchSeq').val();
		var filenameZip = $('#filenameZip').val();
		var policyno = $('#filenameZip').val();
		
		if(sDate == '' && eDate == '' && fileStatus == '' && batchNo == '' 
				&& batchSeq == '' && filenameZip == '' && policyno == ''){
			alert("請最少輸入一個查詢條件。");
			return false;
		}

		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '')){
			alert("執行起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = (parseInt(sDateArr[0]))+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  (parseInt(eDateArr[0]))+'/'+eDateArr[1]+'/'+eDateArr[2];
		if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) < 0){
			alert("執行起日須<=迄日");
			return false;
		}else if(((new Date(teDate) - new Date(tsDate))/(1000 * 60 * 60 * 24)) > 60){
			alert("執行起訖日應介於60天內");
			return false;
		}
		
		if(filenameZip != '' && filenameZip != null && filenameZip.slice(-4) != '.zip'){
			alert("ZIP檔名稱未四碼應為「.zip」。");
			return false;
		}
		
		return true;
	}
	
	function checkDtlInputData(){
		var sDate = $('#dtlSDate').val();
		var eDate = $('#dtlEDate').val();
		var dtlPolicyno = $('#dtlPolicyno').val();
		var dtlStartdate = $('#dtlStartdate').val();
		var dtlNameA = $('#dtlNameA').val();
		var dtlNameI = $('#dtlNameI').val();
		var dtlDataDate = $('#dtlDataDate').val();
		var coreYn = $('#coreYn').val();
		var dtlBatchNo = $('#dtlBatchNo').val();
		var dtlBatchSeq = $('#dtlBatchSeq').val();
		
		if(sDate == '' && eDate == '' && dtlPolicyno == '' && dtlStartdate == '' 
			&& dtlNameA == '' && dtlNameI == '' && dtlDataDate == '' && coreYn == ''
			&& dtlBatchNo =='' && dtlBatchSeq == ''){
			alert("請最少輸入一個查詢條件。");
			return false;
		}
			
		if((sDate == '' && eDate != '') || (sDate != '' && eDate == '')){
			alert("執行起迄日須同時輸入");
			return false;
		}
		var sDateArr = sDate.split("/");
		var tsDate = (parseInt(sDateArr[0]))+'/'+sDateArr[1]+'/'+sDateArr[2];
		var eDateArr = eDate.split("/");
		var teDate =  (parseInt(eDateArr[0]))+'/'+eDateArr[1]+'/'+eDateArr[2];
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
<s:url action="lnkGoDetail?" namespace="/aps/035" var="goDetail"/>
<s:url action="default" namespace="/aps/999" var="go999"/>
<s:url action="default" namespace="/aps/003" var="go003"/>
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
<s:form theme="simple" namespace="/aps/035" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>排程/清單-查詢作業</b></span></td>
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
			<td width="200px" align="right">檔案狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.fileStatus" id="fileStatus" list="fileStatusMap"/>
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.batchNo" id="batchNo" theme="simple" />
			</td>
			<td width="200px" align="right">批次序號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.batchSeq" id="batchSeq"  theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">ZIP檔名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.filenameZip" id="filenameZip" theme="simple" />
			</td>
			<td width="200px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policyno" id="policyno" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td align="center" colspan='4'>
				<h4><span>以下查詢條件僅限「明細清單查詢」使用</span></h4>
			</td>	
		</tr>	
		<tr>
			<td width="200px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.dtlPolicyno" id="dtlPolicyno" theme="simple" />
			</td>
			<td width="200px" align="right">生效日：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.dtlStartdate" id="dtlStartdate" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">主要保人：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.dtlNameA" id="dtlNameA" theme="simple" />
			</td>
			<td width="200px" align="right">主被保險人：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.dtlNameI" id="dtlNameI" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">資料產生日：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.dtlDataDate" id="dtlDataDate" theme="simple" />
			</td>
			<td width="200px" align="right">核心出單：</td>
			<td width="285px" align="left">
				<s:select key="filter.coreYn" id="coreYn" list="coreYnMap"/>
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.dtlBatchNo" id="dtlBatchNo" theme="simple" />
			</td>
			<td width="200px" align="right">批次序號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.dtlBatchSeq" id="dtlBatchSeq" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">執行日期：</td>
			<td width="285px" align="left">
				<table border="0" cellspacing="0" cellpadding="0" >
					<tr>
						<td><s:textfield key="filter.dtlSDate" id="dtlSDate" maxlength="10" size="10" theme="simple" />~</td>
						<td><s:textfield key="filter.dtlEDate" id="dtlEDate" maxlength="10" size="10" theme="simple" /></td>
					</tr>
				</table>
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left">
			</td>			
		</tr>
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="批次清單查詢" onclick="javascript:form_submit('query');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="明細清單查詢" onclick="javascript:form_submit('query2');"/>
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
				nameSpace="/aps/035"
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
		<th>批次序號</th>
		<th>檔案名稱</th>
		<th>檔案狀態</th>
		<th>資料筆數</th>
		<th>PDF檔個數</th>
		<th>執行人員</th>
		<th>執行時間</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left"><s:property value="batchNo"/></td>
		<td align="left"><s:property value="batchSeq"/></td>
		<td align="left"><s:property value="filenameZip"/></td>
		<td align="left">
			<c:if test="${fileStatus == 'S'}">正常</c:if>
			<c:if test="${fileStatus == 'L'}">缺檔</c:if>
			<c:if test="${fileStatus == 'E'}">ZIP檔案異常</c:if>
			<c:if test="${fileStatus == 'A'}">新增錯誤</c:if>
			<c:if test="${fileStatus == 'Z'}">檔案無資料</c:if>
		</td>
		<td align="left"><s:property value="dataQty"/></td>
		<td align="left"><s:property value="pdfQty"/></td>
		<td align="left"><s:property value="icreate"/></td>
		<td align="left"><fmt:formatDate value='${dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
	</tr>
	</s:iterator>
</table>
</s:if>

<s:if test="devResults2 != null && 0 != devResults2.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/035"
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
		<th>批次號碼-序號</th>
		<th>資料產生日</th>	
		<th>保單號碼</th>
		<th>生效日</th>
		<th>總保費</th>
		<th>主要保人姓名</th>
		<th>主被保險人姓名</th>
		<th>要保書</th>
		<th>狀態</th>
		<th>處理結果</th>
		<th>核心</th>
	</tr>
	<s:iterator var="row" value="devResults2">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="left">
			<s:property value="batchNo"/>-<s:property value="batchSeq"/>
		</td>
		<td align="left"><fmt:formatDate value='${dataDate}' pattern='yyyy/MM/dd'/></td>
		<td align="left"><s:property value="policyno"/></td>
		<td align="left"><fmt:formatDate value='${startdate}' pattern='yyyy/MM/dd'/></td>
		<td align="left">
			<fmt:formatNumber type = "number"  maxFractionDigits = "3" value = "${sumPremium}" />
		</td>
		<td align="left"><s:property value="nameA"/></td>
		<td align="left"><s:property value="nameI"/></td>
		<td align="left">
			<a href="javascript:void(0)" 
				onclick="downloadPDF('${row.pdfName}','${row.dcreate}')">下載</a>
		</td>
		<td align="left">${row.pStatus}</td>
		<td align="left" width="100">${row.pMemo}</td>
		<td align="left">
			<c:if test="${coreStatus == 'Y'}">Y</c:if>
			<c:if test="${coreStatus == 'N'}">N</c:if>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>

</s:form>
<!-- form結束 -->
</body>
</html>