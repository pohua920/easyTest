<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "難字案件維護";
String image = path + "/" + "images/";
String GAMID = "APS015A0";
String mDescription = "難字案件維護";
String nameSpace = "/aps/015";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style>
h4 {
   width: 100%; 
   text-align: center; 
   border-bottom: 1px solid #000; 
   line-height: 0.1em;
   margin: 10px 0 20px; 
} 

h4 span { 
    background:#efefef;; 
    padding:0 10px; 
}
</style>
<script language="JavaScript">
	$(document).ready(function(){

	});
			
	function form_submit(type){
		 //$("label").html('');
		if("create" == type){
			if(validateForm(type)){
				$("#mainForm").attr("action","btnCreate.action");
				 $("#mainForm").submit();
			}
		}
	}
	
	function validateForm(type){
		if("create" == type){
			var errorMsg = "";
			var dataType = $("#datatype").val();
			var ownerId = $("#ownerid").val();
			var theName = $("#thename").val();
			if(ownerId.length <= 0){
				errorMsg += "資料內容為必填欄位;";
			}
			if(dataType == "1" && theName.length <= 0){
				errorMsg += "資料型態：身份證字號，請輸入姓名;";
			}
			if(dataType == "2" && theName.length > 0){
				errorMsg += "資料型態：保單號碼，不需輸入姓名;";
			}
			if(errorMsg.length > 0){
				alert(errorMsg);
				return false;
			}
		}
		return true;
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
<s:url action="default" namespace="/aps/015" var="goQuery"/>
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
<s:form theme="simple" action="btnCreateCancel" namespace="/aps/015" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/015" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>新增作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">資料型態：</td>
			<td width="285px" align="left">
				<s:select key="firCtbcRewNoshowword.datatype" id="datatype" list="#{'1':'身分證字號','2':'保單號碼'}"/>
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>			
		</tr>
		<tr>
			<td width="200px" align="right">資料內容：</td>
			<td width="285px" align="left">
				<s:textfield key="firCtbcRewNoshowword.ownerid" id="ownerid" theme="simple" />
			</td>
			<td width="200px" align="right">姓名：</td>
			<td width="285px" align="left">
				<s:textfield key="firCtbcRewNoshowword.thename" id="thename" theme="simple" />
			</td>			
		</tr>
		
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="新增" onclick="javascript:form_submit('create');"/>
		</td>
	</tr>
</table>
</s:form>
<!-- form結束 -->
</body>
</html>