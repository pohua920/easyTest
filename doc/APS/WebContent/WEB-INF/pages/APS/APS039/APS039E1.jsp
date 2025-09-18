<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "中信保代網投查詢作業-明細資料";
	String image = path + "/" + "images/";
	String GAMID = "APS039E1";
	String mDescription = "中信保代網投查詢作業";
	String nameSpace = "/aps/039";
%>
<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業 -->
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
	});

	function form_submit(type){
		if("update" == type){
 			$("#mainForm").attr("action","btnUpdate.action");
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

<s:url action="lnkDownloadProve?" namespace="/aps/039" var="downloadProve"/>
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
	<s:form theme="simple" namespace="/aps/039" id="mainForm" name="mainForm">
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
			<tr>
				<td width="150px" align="right">批次號碼：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.batchNo"/>
					<s:hidden key="aps039DetailVo.batchNo" id="batchNo"></s:hidden>
					<s:hidden key="aps039DetailVo.fkOrderSeq" id="fkOrderSeq"></s:hidden>
					<s:hidden key="aps039DetailVo.batchSeq" id="batchSeq"></s:hidden>
				</td>
				<td width="150px" align="right">序號：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.batchSeq"/>
				</td>
			</tr>
			<tr>
				<td width="150px" align="right">受理編號：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.fkOrderSeq"/>
				</td>
				<td width="150px" align="right">保單號碼：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.policyno"/>
				</td>
			</tr>
			<tr>
				<td width="150px" align="right">受理編號狀態：</td>
				<td width="200px" align="left">
					<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 -->
					<c:choose>
						<c:when test="${aps039DetailVo.orderSeqStatus == '0'}">進件成功(WS成功)</c:when>
						<c:when test="${aps039DetailVo.orderSeqStatus == '1'}">進件失敗(WS失敗)</c:when>
						<c:when test="${aps039DetailVo.orderSeqStatus == '2'}">進件失敗(付款失敗)</c:when>
						<c:when test="${aps039DetailVo.orderSeqStatus == 'A'}">出單完成產生投保證明</c:when>
						<c:when test="${aps039DetailVo.orderSeqStatus == 'B'}">產生回饋檔成功</c:when>
						<c:when test="${aps039DetailVo.orderSeqStatus == 'C'}">產生回饋檔失敗</c:when>
						<c:when test="${aps039DetailVo.orderSeqStatus == 'H'}">產生投保證明失敗-SP</c:when>
						<c:when test="${aps039DetailVo.orderSeqStatus == 'I'}">產生投保證明失敗-PDF</c:when>
					</c:choose>
				</td>
				<td width="150px" align="right">扣款方式：</td>
				<td width="200px" align="left">
					<c:choose>
						<c:when test="${aps039DetailVo.debitType == '1'}">本行帳號</c:when>
						<c:when test="${aps039DetailVo.debitType == '2'}">本行信用卡</c:when>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td width="150px" align="right">投保證明產生時間：</td>
				<td width="200px" align="left">
					<fmt:formatDate value='${aps039DetailVo.printCtfTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
				</td>
				<td width="150px" align="right">投保證明：</td>
				<td width="200px" align="left">
					<c:if test="${not empty aps039DetailVo.oid}">
						<a href='${downloadProve}aps039DetailVo.oid=${aps039DetailVo.oid}'>下載</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<td width="150px" align="right">回饋檔產生時間：</td>
				<td width="200px" align="left">
					<fmt:formatDate value='${aps039DetailVo.bkTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
				</td>
				<td width="150px" align="right">回饋檔檔名：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.bkFilename"/>
				</td>
			</tr>
			<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 start-->
			<tr>
				<td width="150px" align="right">回饋內容：</td>
				<td width="200px" align="left">
					<c:choose>
						<c:when test="${aps039DetailVo.bkType == '01'}">已核保</c:when>
						<c:when test="${aps039DetailVo.bkType == '02'}">不核保</c:when>
					</c:choose>
				</td>
				<td width="150px" align="right">不核保原因：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.inscoFeedback"/>
				</td>
			</tr>
			<!-- mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業_保代網投付款失敗產生回饋檔 end-->
			<tr>
				<td width="150px" align="right">要保人：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.ownerName"/>
				</td>
				<td width="150px" align="right">遞送區域代碼：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.areaCode"/>
				</td>
			</tr>
			<tr>
				<td width="150px" align="right">收件人：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.recipient"/>
				</td>
				<td width="150px" align="right">收件郵遞區號：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.recipientZip"/>
				</td>
			</tr>
			<tr>
				<td width="150px" align="right">收件地址：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.recipientCity"/>
					<s:property value="aps039DetailVo.recipientCounty"/>
					<s:property value="aps039DetailVo.recipientAddr"/>
				</td>
			</tr>
			<tr>
				<td width="150px" align="right">備註：</td>
				<td width="200px" align="left">
					<s:textarea key="aps039DetailVo.remark" id="remark" style="margin: 0px; width: 500px; height: 46px;"/>
				</td>
			</tr>
			<tr>
				<td width="150px" align="right">建檔人員：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.icreate"/>
				</td>
				<td width="150px" align="right">建檔時間：</td>
				<td width="200px" align="left">
					<fmt:formatDate value='${aps039DetailVo.dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/>
				</td>
			</tr>
			<tr>
				<td width="150px" align="right">最後異動人員：</td>
				<td width="200px" align="left">
					<s:property value="aps039DetailVo.iupdate"/>
				</td>
				<td width="150px" align="right">最後異動時間：</td>
				<td width="200px" align="left">
					<fmt:formatDate value='${aps039DetailVo.dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
				</td>
			</tr>
		</table>
		<br/>
		<table width="970px" cellpadding="0" cellspacing="0" >
			<tr>
				<td align="center">
					<input type="button" value="儲存" onclick="javascript:form_submit('update');"/>
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnQuery.action?type=N"><input type="button" value="回上頁"/></a>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>