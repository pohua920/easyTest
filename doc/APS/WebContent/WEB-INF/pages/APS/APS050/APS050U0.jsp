<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "行動裝置險保單查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS050U0";
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

});

	function form_submit(method){
		 $("label").html('');
		 if('query' == method){
			if(validate()){
				 $("#mainForm").attr("action","btnQuery.action");
				 $("#mainForm").submit();
			}
			
		 }
		 if('clear' == method){
			$("#clearForm").submit();
			return false;
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

	var msg = '<%=request.getAttribute("message")%>';
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}	
</script>
</head>
<body style="margin: 0; text-align: left">
<table id="table1" cellSpacing="1" cellPadding="1" width="1300px" border="0">
	<tr>
		<td width="485px">
		<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">
		<img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a>
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26"></td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2" width="1300px"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- form 開始 -->
<s:url action="lnkDownloadProposal?" namespace="/aps/050" var="downloadProposal"/>
<s:form theme="simple" namespace="/aps/050" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="1300px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>明細作業</b></span></td>
			<td class="image" style="width: 20px"></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="1300px">
		<tr>
			<td width="200px" align="right">商品料號：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.prodno"/>
			</td>
			<td width="200px" align="right">商品名稱：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.prodname"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">產品識別碼(IME/SN)：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.imei"/>
			</td>
			<td width="200px" align="right"> 設備種類：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.type"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">設備廠牌：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.brand"/>
			</td>
			<td width="200px" align="right">設備型號：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.model"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">設備空機價：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.rrp"/>
			</td>
			<td width="200px" align="right">手機門號：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.msisdn"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">設備購買日：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.purchaseDate"/>
			</td>
			<td width="200px" align="right">是否為遠傳/全虹出貨：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.isFetSupply"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">門市編號：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.storeId"/>
			</td>
			<td width="200px" align="right">門市名稱：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.storeName"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">保險批改內容：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.endorseContent"/>
			</td>
			<td width="200px" align="right">要保書&批改申請書檔案：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.proposalFileName"/>
				<c:if test="${aps050DetailResultVo.proposalFileName} != '' ">
					<a href='${downloadProposal}aps050DetailResultVo.transactionId=${aps050DetailResultVo.transactionId}'>預覽</a>
				</c:if>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">理賠註記：</td>
			<td width="285px" align="left">
				<s:property value="aps050DetailResultVo.isClaim"/>
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>
		</tr>
	</table>
	<table border="1" id="gridtable" width="1300px" border="0" class="main_table">
		<tr align="center">
			<!-- mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢 START-->
			<th>合約編號</br><span style="color:blue;">TX_ID</span></th>
			<th>申請書編號</th>
			<th>保單號碼</th>
			<th>門號</th>
			<th>被保險人姓名</th>
			<th>門市屬性</th>
			<th>門市名稱</th>
			<th>招攬人員登錄證字號</th>
			<th>門市編號</th>
			<th>操作人員代碼/門市編號</th>
		</tr>
		<tr bgcolor="#EFEFEF"
		style="color:<c:choose><c:when test="${aps050DetailResultVo.dataSrc == 'ESTORE'}">blue</c:when><c:otherwise>black</c:otherwise></c:choose>">
			<td align="left">
				<c:choose>
					<c:when test="${aps050DetailResultVo.dataSrc == 'ESTORE'}"><span style="color:blue;"><s:property value="aps050DetailResultVo.txId"/></span></c:when>
					<c:otherwise><s:property value="aps050DetailResultVo.contractId"/></c:otherwise>
				</c:choose>
			</td>
			<!-- mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 ESTORE查詢 END-->
			<td align="left"><s:property value="aps050DetailResultVo.applicationId"/></td>
			<td align="left"><s:property value="aps050DetailResultVo.policyNo"/></td>
			<td align="left"><s:property value="aps050DetailResultVo.msisdn"/></td>
			<td align="left"><s:property value="aps050DetailResultVo.insuredName"/></td>
			<td align="left"><s:property value="aps050DetailResultVo.channelTypeName"/></td>
			<td align="left"><s:property value="aps050DetailResultVo.storeName"/></td>
			<td align="left"><s:property value="aps050DetailResultVo.salesId"/></td>
			<td align="left"><s:property value="aps050DetailResultVo.storeId"/></td>
			<td align="left"><s:property value="aps050DetailResultVo.employeeId"/></td>
		</tr>	
	</table>
	<table width="1300px">
		<tr>
			<td align="center"><input value="回上頁" type="button" onclick="javascript:form_submit('query');">&nbsp;&nbsp;&nbsp;&nbsp; <input value="取消" type="button" onclick="javascript:form_submit('clear');" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>