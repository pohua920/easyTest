<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "火險地址維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS024E0";
	String mDescription = "火險地址維護作業";
	String nameSpace = "/aps/024";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：FIR0357，處理人員：BJ085，需求單編號：FIR0357 火險地址維護作業  start
-->
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
		
		/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
		<c:if test="${checkExport}">
			var check = true;
			<c:if test="${checkAgainExportMsg != ''}">
				if(confirm("${checkAgainExportMsg}")){
					
				} else {
					check = false;
				}
			</c:if>
			if(check){
				$("#mainForm").attr("action","checkExport.action");
				$("#mainForm").submit();
			}
		</c:if>
		
		<c:if test="${exportMsg}">
			ajaxAction("exportFirAddrCkdata");
			alert("已開始產生檔案，請過一段時間後再點選「正規化結果下載」進行EXCEL下載。");
		</c:if>
		/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
		
		$("#sDate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});
		
		$("#eDate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});
	});

	function checkInputDate(){
		var sDate = $('#sDate').val();
		var eDate = $('#eDate').val();
		
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
			alert("簽單日期起日須<=迄日");
			return false;
		}
		return true;
	}
	
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
	function form_submit(type){
		if("query" == type){
			//mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業
			if($('#sDate').val() !='' || $('#eDate').val() != '' || $('#policyno').val() !='' 
					|| $('#stdAddressLike').val() != '' || $('#validFlag').val() != '') {
				if(checkInputDate()){
					$("#mainForm").attr("action","btnQuery.action");
					$("#mainForm").submit();				
				}else return false;
			}else {
				alert("請最少輸入一個查詢條件");
				return false;
			}
		}else if('formattedQuery' == type) {
			//mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業
			if($('#sDate').val() !='' || $('#eDate').val() != '' || $('#policyno').val() !='' 
				|| $('#stdAddressLike').val() != '' || $('#validFlag').val() != '' || $('#formattedResult').val() != ''
				|| $('#formattedCity').val() != '' || $('#formattedDistrict').val() != '') {
				if(checkInputDate()){
					$("#mainForm").attr("action","btnFormattedQuery.action");
					$("#mainForm").submit();				
				}else return false;
			}else {
				alert("請最少輸入一個查詢條件");
				return false;
			}
		}else if('export' == type) {
			//mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業
			if($('#sDate').val() !='' || $('#eDate').val() != '' || $('#policyno').val() !='' 
				|| $('#stdAddressLike').val() != '' || $('#validFlag').val() != '' || $('#formattedResult').val() != ''
				|| $('#formattedCity').val() != '' || $('#formattedDistrict').val() != '') {
				if(checkInputDate() && confirm("本功能將依畫面查詢條件產生EXCEL檔，請確認是否執行？")){
					$("#mainForm").attr("action","btnExport.action");
					$("#mainForm").submit();				
				}else return false;
			}else {
				alert("請最少輸入一個查詢條件");
				return false;
			}
		}else if('download' == type) {
			$("#mainForm").attr("action","btnDownload.action");
			$("#mainForm").submit();
		}
	}
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
	
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
	function ajaxAction(action){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 

		if(action == 'exportFirAddrCkdata'){
	   		var policyno = $("#policyno").val();
	   		var sDate = $("#sDate").val();
	   		var eDate = $("#eDate").val();
	   		//mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業
	   		var stdAddressLike = $("#stdAddressLike").val();
	   		var validFlag = $("#validFlag").val();
	   		var formattedResult = $("#formattedResult").val();
	   		var exportFlag = $("#exportFlag").val();
	   		var formattedCity = $("#formattedCity").val();
	   		var formattedDistrict = $("#formattedDistrict").val();
	   		var batchNo = "<%=request.getAttribute("batchNo")%>";
	    	path = contextPath + '/aps/ajax014/exportFirAddrCkdata.action';
		}
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			//mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業
			data: {policyno:policyno,sDate:sDate,eDate:eDate,stdAddressLike:stdAddressLike,
				validFlag:validFlag,formattedResult:formattedResult,exportFlag:exportFlag,
				formattedCity:formattedCity,formattedDistrict:formattedDistrict,batchNo:batchNo},
			dataType: 'json',
			timeout: 10000,
			async: true,
			cache: false,
			error: function (data, status, e){
				
			},
			success: function (data, status){
				
			}
		});
	}
	/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
	
</script>
</head>

<s:url action="lnkGoUpdate?firAddrCkdata.oid=" namespace="/aps/024" var="goUpdate"/>
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
<s:form theme="simple" namespace="/aps/024" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="100px" align="right">保單號碼：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policyno" id="policyno" />
			</td> 
			<td colspan="5"></td>   
			<td width="100px" align="right">簽單日期：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.sDate" id="sDate"/>~<s:textfield key="filter.eDate" id="eDate"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
			<td width="100px" align="right">轉換後地址：</td>
			<td width="285px" align="left">
				<!--mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業-->
				<s:textfield key="filter.stdAddressLike" id="stdAddressLike" size="70"/>
			</td> 
			<td colspan="5"></td>   
		</tr>
		<tr>
			<td width="100px" align="right">生效註記：</td>
			<td width="285px" align="left">
				<s:select key="filter.validFlag" id="validFlag" theme="simple" list="#{'':'', '0':'無效', '1':'有效'}" />
			</td> 
			<td colspan="5"></td>   
		</tr>
		<!-- mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 START -->
		<tr>
			<td width="100px" align="right">正規化_結果：</td>
			<td width="285px" align="left">
				<s:select key="filter.formattedResult" id="formattedResult" theme="simple" 
					list="#{'':'', 'Y':'成功', 'N':'失敗', 'F':'服務無回應', 'U':'人工修正'}" />
			</td> 
			<td colspan="5"></td>   
			<td width="125px" align="right">正規化_匯出選項：</td>
			<td width="285px" align="left">
				<s:select key="filter.exportFlag" id="exportFlag" theme="simple" list="#{'1':'有效保單', '2':'全部'}" />
			</td>
			<td colspan="5"></td>
		</tr>
		<tr>
			<td width="100px" align="right">正規化_縣市：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.formattedCity" id="formattedCity" />
			</td> 
			<td colspan="5"></td>   
			<td width="100px" align="right">正規化_鄉鎮區：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.formattedDistrict" id="formattedDistrict"/>
			</td>
			<td colspan="5"></td>
		</tr>
		<!-- mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 END -->
  	</table>
  	
  	<!-- mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 START -->
  	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
  		<tr>
        	<td width="100px" align="right" valign="top">作業說明：</td>
			<td width="800px" align="left">
				1.查詢時請最少輸入一個條件。「轉換後地址」支援局部地址查詢。(資料量大，請盡量縮小查詢範圍。)<br/>
				2.本作業提供「火險地址匯入及查詢作業」匯入地址後維護地址資料，可針對個別地址進行停用/啟用或是資料修改。<br/>
				3.個別地址進行資料修改時「不會」重新執行地址正規化，若有修改標的物地址，請自行調整正規化相關欄位。<br/>
				4.「正規化結果查詢」將依畫面查詢條件進行查詢，並將結果顯示於畫面上。<br/>
				5.「正規化結果匯出」將依畫面查詢條件進行查詢，並將結果產生成EXCEL。<br/>
				6.「正規化結果下載」，點選「正規化結果匯出」後間隔一段時間，可點選本按鈕進行excel下載。<br/>
			</td> 
			<td colspan="5"></td>       	
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="樓層等級查詢" onclick="javascript:form_submit('query');"/>
			<input type="button" value="正規化結果查詢" onclick="javascript:form_submit('formattedQuery');"/>
			<input type="button" value="正規化結果匯出" onclick="javascript:form_submit('export');"/>
			<input type="button" value="正規化結果下載" onclick="javascript:form_submit('download');"/>
		</td>
	</tr>
	</table>
	<!-- mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 END -->

<s:if test="devResults != null && 0 != devResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/024"
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
		<th>修改</th>	
		<th>保單號碼</th>
		<th>簽單<br>日期</th>
		<th>原始地址</th>
		<th>轉換後地址</th>
		<th>等級</th>
		<th>樓層</th>
		<!--mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業start -->
		<th>建築<br>年度</th>
		<th>外牆</th>
		<th>屋頂</th>
		<!--mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業end -->
		<th>註記</th>
		<th>備註</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="30px"><a href='${goUpdate}${row.oid}'>修改</a></td>
		<td align="center" width="120px"><s:property value="policyno"/></td>
		<td align="center" width="55px"><s:property value="underwriteenddate"/></td>
		<td align="center"><s:property value="oriAddress"/></td>
		<td align="center"><s:property value="stdAddress"/></td>
		<td align="center" width="30px"><s:property value="addrStructure"/></td>
		<td align="center" width="30px"><s:property value="addrSumfloors"/></td>
		<!--mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業start -->
		<td align="center" width="30px"><s:property value="buildyears"/></td>
		<td align="center" width="30px"><s:property value="wallmaterial"/></td>
		<td align="center" width="30px"><s:property value="roofmaterial"/></td>
		<!--mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業end -->
		<td align="center" width="30px">
			<c:if test="${validFlag == 0}">無效</c:if>
			<c:if test="${validFlag == 1}">有效</c:if>
		</td>
		<td align="center"><s:property value="remark"/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<!-- mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start -->
<s:if test="devResults2 != null && 0 != devResults2.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/024"
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
		<th>修改</th>	
		<th>保單號碼</th>
		<th>簽單<br>日期</th>
		<th>原始地址</th>
		<th>正規化結果</th>
		<th>正規化狀態碼</th>
		<th>正規化訊息</th>
		<th>註記</th>
	</tr>
	<s:iterator var="row" value="devResults2">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center" width="30px"><a href='${goUpdate}${row.oid}'>修改</a></td>
		<td align="center" width="120px"><s:property value="policyno"/></td>
		<td align="center" width="55px"><s:property value="underwriteenddate"/></td>
		<td align="center"><s:property value="oriAddress"/></td>
		<td align="center">
			<c:if test="${formattedResult == 'Y'}">成功</c:if>
			<c:if test="${formattedResult == 'N'}">失敗</c:if>
			<c:if test="${formattedResult == 'F'}">服務無回應</c:if>
			<c:if test="${formattedResult == 'U'}">人工修正</c:if>
		</td>
		<td align="center"><s:property value="formattedCode"/></td>
		<td align="center"><s:property value="formattedMsg"/></td>
		<td align="center" width="30px">
			<c:if test="${validFlag == 0}">無效</c:if>
			<c:if test="${validFlag == 1}">有效</c:if>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
<!-- mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end -->
<br/>
</s:form>


</body>
</html>