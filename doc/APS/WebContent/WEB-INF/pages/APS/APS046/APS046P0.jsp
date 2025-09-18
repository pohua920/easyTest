<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "理賠資料提交及列印作業";
	String image = path + "/" + "images/";
	String GAMID = "APS046P0";
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
	});
	
	function form_submit(type){
		var wda00 = $("#wda00").val();
		var wda02 = $("#wda02").val();
		var wda03 = $("#wda03").val();
		
		if(wda00 == ''){
			alert("請輸入「資料年月」!");
			return false;
		}
		
		if("print" == type){
			if((wda02 == '' && wda03 != '') || wda02 != '' && wda03 == ''){
				alert("單筆列印時請同時輸入「賠案號」及「賠付次數」!");
				return false;
			}
			
			var token = new Date().getTime();
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
			
			$("#token").val(token);
			$("#mainForm").attr("action","btnPrint.action");
			$("#mainForm").submit();
			
			var pollDownload = setInterval(function() {
		        if (document.cookie.indexOf("aps046Print=" + token) > -1) {
		            $.unblockUI();
		            clearInterval(pollDownload);
		        }
		    }, 1000);
		}
		
		if(type == "genFile"){
			var token = new Date().getTime();
			
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
			
			$("#token").val(token);
			
 		    $("#mainForm").attr("action","btnGenListFile.action");
	 		$("#mainForm").submit();
	 		
			var pollDownload = setInterval(function() {
		        if (document.cookie.indexOf("aps046GenFile=" + token) > -1) {
		            $.unblockUI();
		            clearInterval(pollDownload);
		        }
		    }, 1000);
	 		
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
<s:url action="lnkGoDetailQuery?" namespace="/aps/044" var="dtlQuery"/>
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
		<s:hidden name="token" id="token"/>
		<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
			<tr>
				<td class="SelectTdColor" style="width:200px" align="center">
					<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoUpload.action"><b>轉檔作業</b></a></span>
				</td>
				<td class="imageGray" style="width:20px"></td>
				<td class="SelectTdColor" style="width:200px" align="center">
					<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><b>查詢作業</b></a></span></td>
				<td class="imageGray" style="width:20px"></td>
				<td class="MainTdColor" style="width:200px" align="center">
					<span id="lbSearch"><b>列印作業</b></span>
				<td class="image" colspan="3"></td>
			</tr>
		</table>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
			<tr>
				<td width="200px" align="right">資料年月(YYYMM)：</td>
				<td width="285px" align="left">
					<s:textfield key="wda00" id="wda00" theme="simple" size="9" maxlength="9"/>
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">賠案號：</td>
				<td width="285px" align="left">
					<s:textfield key="wda02" id="wda02" theme="simple"/>
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">賠付次數：</td>
				<td width="285px" align="left">
					<s:textfield key="wda03" id="wda03" theme="simple"/>
				</td>
				<td colspan="5"></td>
			</tr>
		</table>
		<table width="970px" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
					<input type="button" value="列印計算書" onclick="javascript:form_submit('print');" />
					<input type="button" value="列印清單" onclick="javascript:form_submit('genFile');" />
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>