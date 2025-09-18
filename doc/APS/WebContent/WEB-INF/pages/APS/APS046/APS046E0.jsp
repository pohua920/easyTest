<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "理賠資料提交及列印作業";
	String image = path + "/" + "images/";
	String GAMID = "APS046E0";
	String mDescription = "理賠資料提交及列印作業";
	String nameSpace = "/aps/046";
%>
<!-- mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 -->
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
		
		//validate
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"filter.wda00":{
					"required":true
				}
			},
			messages: {
				"filter.wda00":{
					"required":"請輸入資料年月!"
				}
			}
		});
	});
	
	function form_submit(){
		$("#mainForm").attr("action","btnQuery.action");
		$("#mainForm").submit();				
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
<body style="margin: 0; text-align: left">
	<table cellspacing="1" cellpadding="1" width="970px" border="0">
		<tbody>
			<tr>
				<td width="485px"><img
					src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0"></td>
				<td align="right" width="485px">PGMID：<%=GAMID%></td>
			</tr>
			<tr>
				<td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
			</tr>
		</tbody>
	</table>
	<s:form theme="simple" namespace="/aps/046" id="mainForm" name="mainForm">
		<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
			<tr>
				<td class="SelectTdColor" style="width:200px" align="center">
					<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoUpload.action"><b>轉檔作業</b></a></span>
				</td>
				<td class="imageGray" style="width:20px"></td>
				<td class="MainTdColor" style="width:200px" align="center">
					<span id="lbSearch"><b>查詢作業</b></span></td>
				<td class="image" style="width:20px"></td>
				<td class="SelectTdColor" style="width:200px" align="center">
					<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoPrint.action"><b>列印作業</b></a></span>
				<td class="imageGray" colspan="3"></td>
			</tr>
		</table>
		
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
			<tr>
				<td width="200px" align="right">資料年月(YYYMM)：</td>
				<td width="285px" align="left">
					<s:textfield key="filter.wda00" id="wda00" size="9" maxlength="9"/>
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">提交狀態：</td>
				<td width="285px" align="left">
					<s:select key="filter.isSubmit" id="isSubmit" 
					theme="simple" list="#{'Y':'已提交', 'N':'未提交'}" />
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">賠付對象：</td>
				<td width="285px" align="left">
					<s:select key="filter.wde06" id="wde06" 
					theme="simple" list="#{'':'', '27343253':'德誼', '36057744':'全虹', 'else':'其他'}" />
				</td>
				<td colspan="5"></td>
			</tr>
		</table>
		<table width="970px" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
					<input type="button" value="查詢" onclick="javascript:form_submit();" />
				</td>
			</tr>
		</table>
		
		<s:if test="devResults != null && 0 != devResults.size">
			<table cellspacing="1" cellpadding="1" border="0" width="970px">
				<tr>
					<td>
						<div align="right">
							<custom:ddlChangePage formId="mainForm" name="pageInfo"
								nameSpace="/aps/046" currentPage="${pageInfo.currentPage}"
								pageSize="${pageInfo.pageSize}"
								selectOnChange="ddlPageSizeChanged"
								textOnChange="txtChangePageIndex"
								rowCount="${pageInfo.rowCount}"
								pageCount="${pageInfo.pageCount}" />
						</div>
					</td>
				</tr>
			</table>
			<!--Grid Table-->
			<table border="1" id="gridtable" width="970px" border="0"
				class="main_table">
				<tr align="center">
					<th>賠案號碼</th>
					<th>賠付次數</th>
					<th>維修單號</th>
					<th>保單號碼</th>
					<th>賠付對象名稱</th>
					<th>賠付金額</th>
				</tr>
				<s:iterator var="row" value="devResults">
					<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
						<td align="left"><s:property value="wda02" /></td>
						<td align="left"><s:property value="wda03" /></td>
						<td align="left"><s:property value="reporder" /></td>
						<td align="left"><s:property value="wda04" /></td>
						<td align="left"><s:property value="wde07" /></td>
						<td align="left"><s:property value="wde22" /></td>
					</tr>
				</s:iterator>
			</table>
		</s:if>
	</s:form>
</body>
</html>