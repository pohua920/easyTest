<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "中信新件服務人員維護作業";
	String image = path + "/" + "images/";
	String GAMID = "APS014A0";
	String mDescription = "中信新件服務人員維護作業";
	String nameSpace = "/aps/014";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：FIR0182，處理人員：BJ085，需求單編號：FIR0182 中信新件服務人員維護作業  start
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
			"firCtbcDeptinfo.branchNo":{
				"required":true
			},
			"firCtbcDeptinfo.branchName":{
				"required":true,
			},
			"firCtbcDeptinfo.receivedBranch":{
				"required":true
			},
			"firCtbcDeptinfo.receivedBranchName":{
				"required":true
			},
			"firCtbcDeptinfo.handler1code":{
				"required":true
			}
		},
		messages: {
			"firCtbcDeptinfo.branchNo":{
				"required":"請輸入放款帳務行!",
			},
			"firCtbcDeptinfo.branchName":{
				"required":"請輸入放款帳務行名稱!",
			},
			"firCtbcDeptinfo.receivedBranch":{
				"required":"請輸入洽定文件歸屬行!"
			},
			"firCtbcDeptinfo.receivedBranchName":{
				"required":"請輸入洽定文件歸屬行名稱!"
			},
			"firCtbcDeptinfo.handler1code":{
				"required":"請輸入服務人員代號!"
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
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/014" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/014" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>新增作業</b></span></td>
			<td class="image" style="width: 20px"></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px">
		<tr>
			<td align="right"><font color="#FF0000">*</font>放款帳務行：</td>
			<td align="left">
				<s:textfield key="firCtbcDeptinfo.branchNo" id="branchNo" size="10" maxlength="10"/>
			</td>
			<td align="right"><font color="#FF0000">*</font>放款帳務行名稱：</td>
			<td align="left">
				<s:textfield key="firCtbcDeptinfo.branchName" id="branchName" size="20" maxlength="50" />
			</td>
		</tr>
		<tr>
			<td align="right"><font color="#FF0000">*</font>洽定文件歸屬行：</td>
			<td align="left">
				<s:textfield key="firCtbcDeptinfo.receivedBranch" id="receivedBranch" size="10" maxlength="10" />
			</td>
			<td align="right"><font color="#FF0000">*</font>洽定文件歸屬行名稱：</td>
			<td align="left">
				<s:textfield key="firCtbcDeptinfo.receivedBranchName" id="receivedBranchName" size="20" maxlength="50"/>
			</td>
		</tr>
		<tr>
			<td align="right"><font color="#FF0000">*</font>服務人員代號：</td>
			<td align="left">
				<s:textfield key="firCtbcDeptinfo.handler1code" id="handler1code" size="10" maxlength="14"/>
			</td>
			<td align="right"><font color="#FF0000">*</font>歸屬單位代號：</td>
			<td align="left">
				<s:textfield key="firCtbcDeptinfo.comcode" id="comcode" size="10" maxlength="10" readonly="true" cssClass="txtLabel"/>
			</td>
		</tr>
	</table>
	<table width="970px">
		<tr>
			<td align="center"><input value="存檔" type="button" onclick="javascript:form_submit('create');">&nbsp;&nbsp;&nbsp;&nbsp; <input value="取消" type="button" onclick="javascript:form_submit('clear');" /></td>
		</tr>
		<tr>
		<td align="left">
			註1：請注意，本資料僅供中信新件資料轉入核心取得對應服務人員及單位用。<br/>
			註2：「放款帳務行」及「洽訂文件歸屬行」為唯一值，無法設定一個以上的服務人員。
	    </td>
	</tr>
	</table>
</s:form>
</body>
</html>