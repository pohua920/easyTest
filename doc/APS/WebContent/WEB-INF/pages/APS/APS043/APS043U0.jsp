<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "登錄證到期通知排程";
	String image = path + "/" + "images/";
	String GAMID = "APS043U0";
	String mDescription = "登錄證到期通知排程";
	String nameSpace = "/aps/043";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：Sales0024，處理人員：CC009，需求單編號：Sales0024 銷管系統-業務員登錄證到期提醒Mail與簡訊通知排程 -->
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
		
		$('#eYyyymm').datepicker({dateFormat:"yyyy/mm/dd"});
		$('#mYyyymm').datepicker({dateFormat:"yyyy/mm/dd"});
	});
	
	function form_submit(type){
		var msg = "※注意，如已有執行過會重複執行";
		var eYyyymm = $('#eYyyymm').val();
		var mYyyymm = $('#mYyyymm').val();
		if("mailExcute" == type && checkDate(eYyyymm) && confirm(msg)==true){
			$("#mainForm").attr("action","mailExcute.action");
			$("#mainForm").submit();
		}else if ("phoneExcute" == type && checkDate(mYyyymm)){
// 			$("#mainForm").attr("action","runjob.action");
			$("#mainForm").attr("action","phoneExcute.action");
			$("#mainForm").submit();
		}else if("mailQuery" == type){
			$("#mainForm").attr("action","mailQuery.action");
			$("#mainForm").submit();
		}else if("mailUpdate" == type && $("#email").val()!=""){
			$("#mainForm").attr("action","mailUpdate.action");
			$("#mainForm").submit();
		}
		if (!$("#mainForm").valid()) {
			$.unblockUI();
		}
	}
	
	function checkDate(date){
		var adRegex=/^([0-9]\d{3}\/(0?[1-9]|1[012])\/(0?[1-9]|[12][0-9]|3[01]))*$/;
		if(date == null  || date == ''){
			alert("請輸入執行日期");
			return false;
		}
		if(!adRegex.test(date)){
			alert("執行日期請輸入正確日期格式");
			return false;
		}
		return true;
	}
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	msg = msg.replace(';', ';\n');
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
</script>
</head>

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
<s:form theme="simple" action="btnDataImport" namespace="/aps/043" id="mainForm" name="mainForm" method="POST" enctype="multipart/form-data">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>手動執行排程作業</b></a></span>
			</td>
			<td class="image" style="width:20px"></td>
			<td class="SelectTdColor" style="width:200px" align="center">
				<span id="lbSearch"><a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><b>查詢作業</b></span></td>
			<td class="imageGray" style="width:20px"></td>
			<td colspan="5"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr>
        	<td width="120px" align="left">1.指定日期發送E-Mail  執行日期：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.eYyyymm" id="eYyyymm" theme="simple"/>
				<input type="button" value="執行" onclick="javascript:form_submit('mailExcute');"/>
			</td>
		</tr>
		<tr>
        	<td width="120px" align="left">2.指定日期產簡訊發送檔  執行日期：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.mYyyymm" id="mYyyymm" theme="simple"/>	
				<input type="button" value="執行" onclick="javascript:form_submit('phoneExcute');"/>
			</td>
		</tr>
		<tr>
        	<td width="120px" align="left">3.調整發送者MAIL  UserMail：</td>
			<td width="285px" align="left">
				<s:textfield key="filter.email" id="email" theme="simple" />
				<input type="button" value="查詢" onclick="javascript:form_submit('mailQuery');"/>
				<input type="button" value="更新" onclick="javascript:form_submit('mailUpdate');"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">※如要發送多位請用「,」分隔</td>
			<td width="800px" align="left">
			</td>
		</tr>
  	</table>
  	<table width="970px" cellpadding="0" cellspacing="0" >
</table>
</s:form>

</body>
</html>