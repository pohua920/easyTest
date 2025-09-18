<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "行動裝置險保單查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS050E0";
	String mDescription = "行動裝置險保單查詢作業";
	String nameSpace = "/aps/050";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：，處理人員：BJ016，需求單編號：行動裝置險保單查詢作業
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script language="JavaScript">
$(document).ready(function(){
	//validate有錯誤時的訊息
	<c:if test="${not empty errorMsg}">
	var msgg = "有錯誤\n";
		<c:forEach items="${errorMsg}" var="entry">   
			$('input[id^=<c:out value="${entry.key}" /> ]').css('background-color','yellow');
			<c:if test="${entry.value != ''}">
				msgg = msgg + 	'<c:out value="${entry.value}" />' + "\n";
			</c:if>
		</c:forEach> 
		alert(msgg);
	</c:if>
	
	//validate
	$("#mainForm").validate({
		isShowLabel:false,
		isAlertLocalMsg:false,
		rules: {
			"rptBatchNo":{
				"required":true
			}

		},
		messages: {
			"rptBatchNo":{
				"required":"請輸入批次號!",
			}

		}
	});
	
	var ele=document.getElementsByName('cbTransactionId');  
	var hdTransactionIds = $("#hdTransactionIds").val();
	for(var i=0; i<ele.length; i++){  
        if(ele[i].type=='checkbox' && ele[i].value.length > 0){
        	if(hdTransactionIds.indexOf(ele[i].value) >= 0){
        		ele[i].checked=true; 
        	}
        }
    } 

	//$('#batchDate').datepicker({dateFormat:"yyyy/mm/dd"});
	//$('#startDate').datepicker({dateFormat:"yyyy/mm/dd"});
	//$('#endDate').datepicker({dateFormat:"yyyy/mm/dd"});
});

	function form_submit(method){
		 $("label").html('');
		 if('query' == method){
			 $("#hdTransactionIds").val("");
			if(validate()){
				 $("#mainForm").attr("action","btnQuery.action");
				 $("#mainForm").submit();
			}
		 }
		 if('clear' == method){
			$("#clearForm").submit();
			return false;
		 }
		if('sendTerminationNotice' == method){
			var hasValue = checkCbTransactionId();
	        if(hasValue){
				$("#mainForm").attr("action","btnSendTerminationNotice.action");
				$("#mainForm").submit();
	        } else {
	        	alert("請至少選擇1個保單!");
	        }
		}
		
		if('proposalFileCheck' == method){
			var hasValue = checkCbTransactionId();
	        if(hasValue){
				$("#mainForm").attr("action","btnProposalFileCheck.action");
				$("#mainForm").submit();
	        } else {
	        	alert("請至少選擇1個保單!");
	        }
		}
		
		if('sendTerminationNoticeCancel' == method){
			var hasValue = checkCbTransactionId();
	        if(hasValue){
				$("#mainForm").attr("action","btnSendTerminationNoticeCancel.action");
				$("#mainForm").submit();
	        } else {
	        	alert("請至少選擇1個保單!");
	        }
		}
		
		if('sendTerminationNoticeUnpaid' == method){
			var hasValue = checkCbTransactionId();
	        if(hasValue){
				$("#mainForm").attr("action","btnSendTerminationNoticeUnpaid.action");
				$("#mainForm").submit();
	        } else {
	        	alert("請至少選擇1個保單!");
	        }
		}
		
		if('genExcel' == method){
			$("#mainForm").attr("action","btnGenExcel.action");
			$("#mainForm").submit();
		}
		
		if('sendProposalInsufficientNotify' == method){
			var check = confirm('確定執行[要保書不全通知]功能嗎？');

			if (check) {
				var hasValue = checkCbTransactionId();
		        if(hasValue){
					$("#mainForm").attr("action","btnSendProposalInsufficientNotify.action");
					$("#mainForm").submit();
		        } else {
		        	alert("請至少選擇1個保單!");
		        }
			}
		}
	}

	function validate(){
		var message = "";
		var check = true;
		return check;
	}

	function showMsg(msg){
		alert(msg);
	}
	
	function funcSelectAll()
	{
		var selectAll=document.getElementsByName("selectAllCheck");
		var ele=document.getElementsByName('cbTransactionId');  
		if(selectAll[0].checked==true)
	   {
			for(var i=0; i<ele.length; i++){  
	            if(ele[i].type=='checkbox')  
	                ele[i].checked=true;  
	        }  
		} else {
			for(var i=0; i<ele.length; i++){  
	            if(ele[i].type=='checkbox')  
	                ele[i].checked=false;  
	        }  
		}          
	}
	
	function checkCbTransactionId(){
		var ele=document.getElementsByName('cbTransactionId');  
		var selectValue = "";
		var hasValue = false;
        for(var i=0; i<ele.length; i++){  
            if(ele[i].type=='checkbox' && ele[i].checked){
            	selectValue += ele[i].value + ",";
            	hasValue = true;
            }
        } 
        $("#hdTransactionIds").val(selectValue);
        return hasValue;
	}

	var msg = '<%=request.getAttribute("message")%>';
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}	
</script>
</head>
<s:url action="lnkGoDetail?" namespace="/aps/050" var="goDetail"/>
<s:url action="lnkGoDownloadData" namespace="/aps/050" var="goDownloadData"/>
<body style="margin: 0; text-align: left">
<table id="table1" cellSpacing="1" cellPadding="1" width="1400px" border="0">
	<tr>
		<td width="485px">
		<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">
		<!-- mantis：MOB0022，處理人員：CE035，需求單編號：MOB0022 匯入核心排程發信告知結果、修正手機險排程時間、修正保批單號資料型態、洗錢條件檢核補上批單號、遠傳資料批次下載使用者改ce035  --> 
		<s:if test="userInfo.userId == 'ce035' ">
			<a href='${goDownloadData}'>遠傳手動資料批次號下載</a>
		</s:if>
		<img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a>
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26"></td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2" width="1400px"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- clear form -->
<s:form theme="simple" action="default" namespace="/aps/050" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/050" id="mainForm" name="mainForm">
	<s:hidden name="hdTransactionIds"  id="hdTransactionIds"/>
	<table id="table2" cellspacing="0" cellpadding="0" width="1400px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>查詢作業</b></span></td>
			<td class="image" style="width: 20px"></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="1400px">
		<tr>
			<td width="200px" align="right">收件日：</td>
			<td width="285px" align="left">
			<%-- mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面  start--%>
<%-- 				<s:textfield key="filter.batchDate" id="batchDate" size="20" maxlength="50" /> --%>
				<table border="0" cellspacing="0" cellpadding="0" >
					<tr>
						<td><s:textfield key="filter.batchDateStart" id="batchDateStart" maxlength="10" size="10" theme="simple" />~</td>
						<td><s:textfield key="filter.batchDateEnd" id="batchDateEnd" maxlength="10" size="10" theme="simple" /></td>
					</tr>
				</table>
				<%-- mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面  end--%>
			</td>
			<td width="200px" align="right">合約編號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.contractId" id="contractId" size="20" maxlength="50" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">要保人姓名：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.name" id="name" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right">要保人ID：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.customerId" id="customerId" size="20" maxlength="50" /><!-- mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修正APS查詢畫面要保人ID抓取資料欄位值 -->
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">手機門號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.msisdn" id="msisdn" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right">方案名稱：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.featureName" id="featureName" size="20" maxlength="50" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">保險起日：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.startDate" id="startDate" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right">保險迄日：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.endDate" id="endDate" size="20" maxlength="50" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">保單號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.policyNo" id="policyNo" size="20" maxlength="50" />
			</td>
			<td width="200px" align="right">批單號：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.endorseNo" id="endorseNo" size="20" maxlength="50" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">受理檔狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.dataStatus" id="dataStatus" list="dataStatusMap"/>
			</td>
			<!-- mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 -->
			<td width="200px" align="right">要保書狀態：</td>
			<td width="285px" align="left">
				<s:select key="filter.formType" id="formType" list="formTypeMap"/>
			</td>
		</tr>
		<!-- mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 start-->
		<tr>
			<!-- antis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢 START-->
			<td width="200px" align="right" style="color: blue;">TX_ID：</td>
			<td width="285px" align="left" style="color: blue;">
				<s:textfield key="filter.txId" id="txId" size="50" maxlength="45" />
			</td>
			<!-- antis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢 END-->
			<td width="200px" align="right">要保書是否完整：</td>
			<td width="285px" align="left">
				<s:select key="filter.proposalFileCheck" id="proposalFileCheck" theme="simple" list="#{'':'','Y':'Y', 'N':'N'}"/>
			</td>
		</tr>
		<!-- mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 end-->
	</table>
	<table width="1400px">
		<tr>
			<td align="center">
				<input value="查詢" type="button" onclick="javascript:form_submit('query');">&nbsp;&nbsp;&nbsp;&nbsp; 
				<!--  
				<input value="發送終止通知書簡訊(自取消)" type="button" onclick="javascript:form_submit('sendTerminationNoticeCancel');">&nbsp;&nbsp;&nbsp;&nbsp; 
				<input value="發送終止通知書簡訊(未繳費)" type="button" onclick="javascript:form_submit('sendTerminationNoticeUnpaid');">&nbsp;&nbsp;&nbsp;&nbsp; 
				<input value="重送終止通知書簡訊" type="button" onclick="javascript:form_submit('sendTerminationNotice');">&nbsp;&nbsp;&nbsp;&nbsp; 
				<input value="重新寄發保單簡訊" type="button" onclick="javascript:form_submit('query');">&nbsp;&nbsp;&nbsp;&nbsp; 
				-->
				<input value="匯出EXCEL" type="button" onclick="javascript:form_submit('genExcel');">&nbsp;&nbsp;&nbsp;&nbsp; 
				<input value="要保書審核通過" type="button" onclick="javascript:form_submit('proposalFileCheck');">&nbsp;&nbsp;&nbsp;&nbsp; 
				<input value="要保書不全通知" type="button" onclick="javascript:form_submit('sendProposalInsufficientNotify');">&nbsp;&nbsp;&nbsp;&nbsp; 
				<input value="取消" type="button" onclick="javascript:form_submit('clear');" />
			</td>
		</tr>
	</table>

<s:if test="devResults != null && 0 != devResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="1400px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="pageInfo" 
				nameSpace="/aps/050"
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
<table border="1" id="gridtable" width="1400px" border="0" class="main_table">
	<tr align="center">
		<th>
			<input type=checkbox name='selectAllCheck' onClick='javascript:funcSelectAll()' value='Select All'>全選</input>
		</th>
		<th>收件日</th>	
		<th>合約編號</br><span style="color:blue;">TX_ID</span></th><!-- mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢 -->
		<th>要保人姓名</th>
		<th>要保人ID</th>
		<th>手機門號</th>
		<th>方案名稱</th>
		<th>保險起日</th>
		<th>保險迄日</th>
		<th>安達處理狀態</th>
		<th>安達處理說明</th>
		<th>受理檔狀態</th>
		<th>保單號碼</th>
		<th>批單號碼</th>
		<th>要保書狀態</th><!-- mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 -->
		<th>要保書是否完整</th>
		<th>門市編號</th>
		<th>批改內容</th>
	</tr>
	<s:iterator var="row" value="devResults">
	<!-- mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢 START-->
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF" 
	style="color:<c:choose><c:when test="${dataSrc == 'ESTORE'}">blue</c:when><c:otherwise>black</c:otherwise></c:choose>">
		<td align="center" width="50px">
			<input type="checkbox" name="cbTransactionId"  id="cbTransactionId" value="${row.transactionId}" onClick='javascript:funcSelect()'/>
		</td>
		<td align="left"><s:property value="batchDate"/></td>
		<td align="left">
			<a href='${goDetail}aps050DetailResultVo.transactionId=${row.transactionId}'>
				<c:choose>
					<c:when test="${dataSrc == 'ESTORE'}"><span style="color:blue;"><s:property value="txId"/></span></c:when>
					<c:otherwise><s:property value="contractId"/></c:otherwise>
				</c:choose>
			</a><!-- mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢 END-->
		</td>
		<td align="left"><s:property value="name"/></td>
		<td align="left"><s:property value="customerId"/></td>
		<td align="left"><s:property value="msisdn"/></td>
		<td align="left"><s:property value="featureName"/></td>
		<td align="left"><s:property value="startDate"/></td>
		<td align="left"><s:property value="endDate"/></td>
		<td align="left">
			<c:if test="${chubbReturnStatus == '1'}">成功</c:if>
			<c:if test="${chubbReturnStatus == '2'}">失敗</c:if>
			<c:if test="${chubbReturnStatus == '3'}">不受理</c:if>
		</td>
		<td align="left"><s:property value="chubbReturnMsg"/></td>
		<td align="left">
			<c:if test="${dataStatus == 'INIT'}">初始</c:if>
			<c:if test="${dataStatus == 'READY'}">可匯入核心中介資料表</c:if>
			<c:if test="${dataStatus == 'FAIL'}">匯入核心中介資料表失敗</c:if>
			<c:if test="${dataStatus == 'FINISH'}">已匯入核心中介資料表</c:if>
			<c:if test="${dataStatus == 'PROCESS'}">執行匯入中</c:if>
		</td>
		<td align="left"><s:property value="policyNo"/></td>
		<td align="left"><s:property value="endorseNo"/></td>
		<!-- mantis：MOB0025，處理人員：CE035，需求單編號：MOB0025 新增要保書紙本進件流程，修改APS查詢畫面 -->
		<td align="left"><s:property value="formType"/>
			<c:if test="${formType == 'E'}">電子</c:if>
			<c:if test="${formType == 'N'}">紙本</c:if>
			<!-- mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢 -->
			<c:if test="${formType == 'W'}">ESTORE</c:if>
		</td>
		<td align="left"><s:property value="proposalFileCheck"/></td>
		<td align="left"><s:property value="storeId"/></td>
		<td align="left">
			<c:if test="${transactionType == 'MODIFY'}">資料修改</c:if>
			<c:if test="${transactionType == 'CANCEL'}">退保</c:if>
		</td>
	</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
</body>
</html>