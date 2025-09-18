<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "理賠資料提交及列印作業";
	String image = path + "/" + "images/";
	String GAMID = "APS046U0";
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
		
		$(function(){
			$("#mainForm").submit(function() {
				$.blockUI({
					//blockUI：設定頁面指定區域顯示執行中文字(如Loading...)並鎖定該區域限制輸入。
					border: 'none',
					padding: '15px',//padding：同時設定四個邊留白的捷徑屬性
					backgroundColor: '#000',//backgroundColor：訊息背景顏色
					color: '#fff',//color：訊息字樣顏色
					'-webkit-border-radius': '10px',//WebKit設定四邊的圓角為10px
					'-moz-border-radius': '10px',//Mozilla設定四邊的圓角為10px
					opacity: .5//opacity：指定表單和其控制項的透明度等級
				});
	    	});
		});
		
	});
	
	function form_submit(type){
		var wda00 = $("#wda00").val();
		
		if(wda00 == ''){
			alert("請輸入資料年月!");
			return false;
		}
		
		if(confirm("是否確認提交"+wda00+"資料？")===true){
			$("#mainForm").attr("action","btnSubmit.action");
			$("#mainForm").submit();		
			if (!$("#mainForm").valid()) {
				$.unblockUI();
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
<s:url action="lnkGoDownload" namespace="/aps/034" var="downloadQuery"/>
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
	<s:form theme="simple" namespace="/aps/046" id="mainForm"
		name="mainForm">
		<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
			<tr>
				<td class="MainTdColor" style="width:200px" align="center">
					<span id="lbSearch"><b>轉檔作業</b></span>
				</td>
				<td class="image" style="width:20px"></td>
				<td class="SelectTdColor" style="width:200px" align="center">
					<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><b>查詢作業</b></a></span></td>
				<td class="imageGray" style="width:20px"></td>
				<td class="SelectTdColor" style="width:200px" align="center">
					<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoPrint.action"><b>列印作業</b></a></span>
				<td class="imageGray" colspan="3"></td>
			</tr>
		</table>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
			<tr>
				<td width="200px" align="right">資料年月(YYYMM)：</td>
				<td width="300px" align="left">
					<s:textfield key="wda00" id="wda00" theme="simple" size="9" maxlength="9"/>
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">賠付總金額：</td>
				<td width="300px" align="left">
					<s:property value="sumWda35"/>
					<input type="hidden" name="sumWda35" id="sumWda35">
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right"></td>
				<td width="300px" align="left">
					<input type="button" value="提交" onclick="javascript:form_submit('submit');">
				</td>
				<td colspan="5"></td>
			</tr>
		</table>
	</s:form>
</body>
</html>