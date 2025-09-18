<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "板信續保扣款前置檔產生作業";
	String image = path + "/" + "images/";
	String GAMID = "APS023E0";
	String mDescription = "板信續保扣款前置檔產生作業";
	String nameSpace = "/aps/023";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：FIR349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業  start
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
	});

	function form_submit(type){
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		if("genExcel" == type){
			$("#mainForm").attr("action","btnGenExcel.action");
			$("#mainForm").submit();
		}
		/* mantis：FIR349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 start*/
		if("downloadFile" == type){
			$("#mainForm").attr("action","btnDownloadExcel.action");
			$("#mainForm").submit();
		}
		/* mantis：FIR349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業 end*/
	}
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
	
	function ajaxAction(action){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 

		if(action == 'countPolicy'){
	   		var batchNo = $("#batchNo").val();
	   		if(batchNo == ""){
	   			alert("請輸入批次號碼!");
	   			return false;
	   		}
	    	path = contextPath + '/aps/ajax008/countPolicyByBatchNo.action?batchNo='+batchNo;
		}
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {	},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				ajaxError(data, status);
			},
			success: function (data, status){
				ajaxSuccess(action, data);
			}
		});
	}
	//ajax成功時會回來的method
	function ajaxSuccess(action, data){
		if(action == 'countPolicy'){
			if(data.isExist){
				if (confirm('批號'+data.batchNo+'有'+data.ntotal+'筆續保要保書，已出單'+data.npm+'筆。是否產生扣前置檔?')==true){
					form_submit('genExcel');
				}
				return false;
			}else{
				alert("查無資料，請確認輸入的批次號碼是否正確!");
			}
		}
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert('操作失敗');
	}
</script>
</head>

<%-- <s:url action="btnQuery2?firAddrImportlist.oid=" namespace="/aps/005" var="importFail"/> --%>
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
<s:form theme="simple" namespace="/aps/023" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>產生作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
			<td width="200px" align="right">批次號碼：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.batchNo" id="batchNo" size="25" maxlength="25" theme="simple" />
					<input type="button" value="查看批號" onclick="javascript:form_submit('query');"/>
					<input type="button" value="產生檔案" onclick="ajaxAction('countPolicy')"/>
					<!-- mantis：FIR349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業  -->
					<input type="button" value="批號資料下載" onclick="javascript:form_submit('downloadFile');"/>
			</td>
			<td colspan="5"></td>
		</tr>
		
		<tr>
        	<td width="200px" align="right">作業說明：</td>
			<td width="800px" align="left">
				1.本作業為AS400板信住火續保件產生扣款檔的前置作業。<br/>
				2.請先至APS系統「板信續保資料處理作業」查看欲產生扣款前置檔的批號，並將批次號碼複製到本作業內。<br/>
				3.選擇「查看批號」，確認此批號目前在新核心的出單狀況。<br/>
				<!-- mantis：FIR349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業  start-->
				4.若該批號所有續保件都已在新核心系統出單完成，可執行「產生檔案」進行扣款前置檔產生作業。<br/>
				5.「批號資料下載」可將目前查詢批號的結果轉成EXCEL。
				<!-- mantis：FIR349，處理人員：BJ085，需求單編號：FIR0349 外銀板信續件扣款前置檔產生作業  end-->
			</td> 
			<td colspan="5"></td>       	
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
				nameSpace="/aps/023"
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
		<th>續保單號</th>	
		<th>受理編號</th>
		<th>轉核心要保時間</th>
		<th>要保書號</th>
		<th>保單號碼</th>
		<th>歸屬單位</th>
		<th>服務人員</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><s:property value="oldpolicyno"/></td>
		<td align="center"><s:property value="orderseq"/></td>
		<td align="center"><fmt:formatDate value='${transPpsTime}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
		<td align="center"><s:property value="proposalno"/></td>
		<td align="center"><s:property value="policyno"/></td>
		<td align="center">
			<c:if test="${comcode != null && comcode != null}">
				<s:property value="comcode+comcname"/>			
			</c:if>
		</td>
		<td align="center">
			<c:if test="${handler1code != null && username != null}">
				<s:property value="handler1code+username"/>
			</c:if>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>
</s:form>

</body>
</html>