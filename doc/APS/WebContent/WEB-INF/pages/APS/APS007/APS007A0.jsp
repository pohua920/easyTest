<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "稽核檢核例外地址維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS007A0";
	String mDescription = "稽核檢核例外地址維護作業";
	String nameSpace = "/aps/007";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：FIR0238，處理人員：BJ085，需求單編號：FIR0238 稽核議題檢核-例外地址維護作業  start
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
			"firRuleAddrExcp.ruleId":{
				"required":true,
				"checkIdVal":""
			},
			"firRuleAddrExcp.ruleSeq":{
				"required":true,
				"checkSeqVal":""
			},
			"firRuleAddrExcp.address":{
				"required":true
			},
			"firRuleAddrExcp.deleteFlag":{
				"required":true
			}
		},
		messages: {
			"firRuleAddrExcp.ruleId":{
				"required":"請輸入規則代號!",
				"checkIdVal":"規則代號格式錯誤"
			},
			"firRuleAddrExcp.ruleSeq":{
				"required":"請輸入規則序號!",
				"checkSeqVal":"規則序號格式錯誤"
			},
			"firRuleAddrExcp.address":{
				"required":"請輸入標的物地址!"
			},
			"firRuleAddrExcp.deleteFlag":{
				"required":"請選擇刪除註記!"
			}
		}
	});

});
	function form_submit(method){
		 $("label").html('');
		 if('create' == method){
			 $("#mainForm").attr("action","btnCreate.action");
			 $("#mainForm").submit();
		 }
		 if('clear' == method){
				if (confirm("請確認是否放棄新增並返回查詢頁面？")==true){
					$("#clearForm").submit();
				}
			return false;
		 }
	}
	
	$.validator.addMethod("checkIdVal", function(value,element,param) { 
		return checkVal('ruleId');						
	},"");
	$.validator.addMethod("checkSeqVal", function(value,element,param) { 
		return checkVal('ruleSeq');						
	},"");
	
	function checkVal(str) {
		var checkStr = $('#'+str).val();
		var check = false;
	    var regExp = /^[0-9\w]+$/;
	    if (regExp.test(checkStr)){
	        check = true;	    	
	    }
	    return check;
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
<table id="table1" cellSpacing="1" cellPadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
		<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">
		<img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a> 
		<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26"></td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2" width="970px"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- clear form -->
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/007" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/007" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>新增作業</b></span></td>
			<td class="image" style="width: 20px"></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px">
		<tr>
			<td align="right"><font color="#FF0000">*</font>規則代號：</td>
			<td align="left"><s:textfield key="firRuleAddrExcp.ruleId" id="ruleId" size="8" maxlength="8"/></td>
			<td align="right"><font color="#FF0000">*</font>規則序號：</td>
			<td align="left"><s:textfield key="firRuleAddrExcp.ruleSeq" id="ruleSeq" size="8" maxlength="2" /></td>
		</tr>
		<tr>
			<td align="right"><font color="#FF0000">*</font>標的物地址：</td>
			<td align="left"><s:textfield key="firRuleAddrExcp.address" id="address" size="60" maxlength="200" /></td>
		</tr>
		<tr>
			<td align="right">備註：</td>
			<td align="left"><s:textfield key="firRuleAddrExcp.remark" id="remark" size="60" maxlength="500"/></td>
		</tr>
		<tr>
			<td align="right"><font color="#FF0000">*</font>刪除註記：</td>
			<td align="left">
			<s:select key="firRuleAddrExcp.deleteFlag" id="deleteFlag" theme="simple" list="#{'N':'N', 'Y':'Y'}"/>
			</td>
		</tr>
	</table>
	<table width="970px">
		<tr>
			<td align="center"><input value="存檔" type="button" onclick="javascript:form_submit('create');">&nbsp;&nbsp;&nbsp;&nbsp; <input value="取消" type="button" onclick="javascript:form_submit('clear');" /></td>
		</tr>
		<tr>
		<!-- mantis：FIR0540，處理人員：BJ085，需求單編號：FIR0540 住火_中信保代網投-APS_稽核檢核例外地址維護作業原FIR0238 start -->
		<td align="left">
			註：本維護作業提供以下稽核檢核規則建檔，輸入其他規則代號/序號將無法被WebService服務處理。<br>
			(FIR002-00)地址樓層數不可超過總樓層數例外判斷使用。<br>
			(FIR003-00)多門牌併同一地址出單例外判斷使用。
	    </td>
	    <!-- mantis：FIR0540，處理人員：BJ085，需求單編號：FIR0540 住火_中信保代網投-APS_稽核檢核例外地址維護作業原FIR0238 end -->
	</tr>
	</table>
</s:form>
</body>
</html>