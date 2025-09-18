<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "信用卡欄位使用者控管設定";
	String image = path + "/" + "images/";
	String GAMID = "APS059A0";
	String mDescription = "信用卡欄位使用者控管設定";
	String nameSpace = "/aps/059";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis： HAS0254，處理人員：DP0706，需求單編號：HAS0254_傷害險中信銀行投調整信用卡加密及檔案上下傳END -->
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

	$("#mainForm").validate({
		isShowLabel:false,
		isAlertLocalMsg:false,
		rules: {
			"prpdNewCode.codecode":{
				"required":true,
			},
			"prpdNewCode.codecname":{
				"required":true
			},
			"prpdNewCode.validstatus":{
				"required":true
			},
			"prpdNewCode.validdate":{
				"required":true
			},
			"prpdNewCode.invaliddate":{
				"required":true
			},
		},
		messages: {
			"prpdNewCode.codecode":{
				"required":"請輸入使用者員編!",
			},
			"prpdNewCode.codecname":{
				"required":"請輸入使用者姓名!"
			},
			"prpdNewCode.validstatus":{
				"required":"請選擇是否有效!"
			},
			"prpdNewCode.validdate":{
				"required":"請輸入生效日!"
			},
			"prpdNewCode.invaliddate":{
				"required":"請輸入到期日!"
			},
			
		}
	});

	$("#startDate").datepicker({
		changeMonth: true,  
		changeYear: true,
		dateFormat:'yyyy/mm/dd'
	});
	
	$("#endDate").datepicker({
		changeMonth: true,  
		changeYear: true,
		dateFormat:'yyyy/mm/dd'
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
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/059" id="clearForm" name="clearForm" />
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/059" id="mainForm" name="mainForm">
	<table id="table2" cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width: 200px" align="center"><span id="lbSearch"><b>新增作業</b></span></td>
			<td class="image" style="width: 20px"></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="870px">
		<tr>
        	<td width="200px" align="right">使用者員編：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdNewCode.codecode" id="codecode"/>
			</td>
        	<td width="200px" align="right">是否有效：</td>
			<td width="285px" align="left">
				<s:select key="prpdNewCode.validstatus" id="validstatus" theme="simple" list="#{'':'', '0':'失效', '1':'有效'}" />
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">使用者姓名：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdNewCode.codecname" id="codecname"/>
			</td>
		</tr>
		<tr>
        	<td width="200px" align="right">生效日期：</td>
			<td width="285px" align="left">
				<table border="0" cellspacing="0" cellpadding="0" >
					<tr>
						<td><s:textfield key="prpdNewCode.validdate" id="startDate" maxlength="10" size="10" theme="simple" />~</td>
						<td><s:textfield key="prpdNewCode.invaliddate" id="endDate" maxlength="10" size="10" theme="simple" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="970px">
		<tr>
			<td align="center">
			    <input value="存檔" type="button" onclick="javascript:form_submit('create');">&nbsp;&nbsp;&nbsp;&nbsp;
			    <input value="取消" type="button" onclick="javascript:form_submit('clear');" />
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>