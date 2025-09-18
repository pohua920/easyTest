<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String title = "電子保單不寄送單位與業務來源設定";
String image = path + "/" + "images/";
String GAMID = "APS025U0";
String mDescription = "電子保單不寄送單位與業務來源設定";
String nameSpace = "/aps/025";
%>
<!-- mantis：FIR0369，處理人員：BJ085，需求單編號：FIR0369 增加電子保單不寄送單位業務來源設定 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<script language="JavaScript">
	$(document).ready(function(){
		//validate
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"firBatchmailExcludedata.businessnature":{
				"checkBusinessnature":""
				},
				"firBatchmailExcludedata.extracomcode":{
				"checkExtracomcode":""
				}
			},
			messages: {
				"firBatchmailExcludedata.businessnature":{
				"checkBusinessnature":"僅能輸入英文或數字"
				},
				"firBatchmailExcludedata.extracomcode":{
				"checkExtracomcode":"僅能輸入英文或數字"
				}
			}
		});
	});
	
	$.validator.addMethod("checkBusinessnature", function(value,element,param) { 
		return checkInput($("#businessnature").val());						
	},"");
	$.validator.addMethod("checkExtracomcode", function(value,element,param) { 
		return checkInput($("#extracomcode").val());						
	},"");
	
	function checkInput(val){
		var regex =/^[a-zA-Z0-9]*$/;
		console.log("val:"+val);
		if(!regex.test(val)){
			return false;
		}
		return true;
	}
	
	function form_submit(type){
		if("update" === type && $("#businessnature").val() === "" && $("#extracomcode").val()===""){
			alert("業務來源與單位必須至少輸入一個");
			return false;
		}else if("update" === type && confirm("確認儲存修改?")){
			 $("#mainForm").attr("action","btnUpdate.action");
			 $("#mainForm").submit();
		}
		
		if("clear" == type){
			if(confirm("請確認是否放棄儲存並返回查詢頁面？")){
				$("#clearForm").submit();
			}
			return false;
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
<s:url action="default" namespace="/aps/025" var="goQuery"/>
<body style="margin:0;text-align:left">
	<table cellspacing="1" cellpadding="1" width="970px" border="0">
		<tbody>
			   <tr>
				  <td width="485px">			      
					  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
					  <a href='${goQuery}'><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0"></a>
				  </td>
				  <td align="right" width="485px">PGMID：<%=GAMID%></td>
			   </tr>
			   <tr>
				   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
			   </tr>
		</tbody>		
	</table>
<!-- clear form -->
<s:form theme="simple" action="default" namespace="/aps/025" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/025" id="mainForm" name="mainForm">
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr bgcolor="white">
				<td class="MainTdColor" align="center" width="200px">
					<span id="lbSearch"><b>修改作業</b></span></td>
				<td colspan="5" class="image"></td>
			</tr>
			<tr>
				<td width="120px" align="right">業務來源代碼：</td>
				<td width="285px" align="left">
					<s:textfield key="firBatchmailExcludedata.businessnature" id="businessnature" size="12" maxlength="12" theme="simple" />
				</td>
				<td width="120px" align="right">單位代碼：</td>
				<td width="285px" align="left">
					<s:textfield key="firBatchmailExcludedata.extracomcode" id="extracomcode" size="20" maxlength="20"  theme="simple" />
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">是否啟用：</td>
				<td width="285px" align="left">
					<s:select key="firBatchmailExcludedata.active" id="active" list="#{'Y':'是', 'N':'否'}" theme="simple" />
				</td>
			</tr>
		</tbody>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tbody>
			<tr>
				<td width="167px" align="right">備註：</td>
				<td width="400px" align="left">
					<s:textarea key="firBatchmailExcludedata.remark" id="remark" maxlength="600" style="width: 570px; height: 50px;" theme="simple" />
				</td>
			</tr>
		</tbody>
	</table>
	<table width="970px" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="center">
				<input value="存檔" type="button" onclick="javascript:form_submit('update');"/>
				<input value="取消" type="button" onclick="javascript:form_submit('clear');"/>
			</td>
		</tr>
	</table>
	<s:hidden name="firBatchmailExcludedata.sno" id="sno"/>
</s:form>
<!-- form結束 -->
</body>
</html>