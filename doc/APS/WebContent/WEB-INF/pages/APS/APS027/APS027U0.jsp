<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String title = "業務人員所屬服務人員維護";
String image = path + "/" + "images/";
String GAMID = "APS027U0";
String mDescription = "業務人員所屬服務人員維護";
String nameSpace = "/aps/027";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<script language="JavaScript">
	function form_submit(type){
		if("update" === type && confirm("確認儲存修改?")){
			$("#mainForm").attr("action","btnUpdate.action");
			$("#mainForm").submit();
		}
		
		if("clear" === type){
			if(confirm("請確認是否放棄儲存並返回查詢頁面？")){
				$("#clearForm").submit();
			}
			return false;
		}
	}
	
	function ajaxAction(action,form){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 

		if(action == 'findHandlercodeVaild'){
	   		var orgicode = $("#unitcode").val();
	   		var cmem00 = $("#handlercode").val();
	    	path = contextPath + '/aps/ajax009/findHandlercodeVaildByParams.action?orgicode='+orgicode+'&cmem00='+cmem00;
		}
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {	},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				ajaxError(data, status);
			},
			success: function (data, status){
				ajaxSuccess(action, data, form);
			}
		});
	}
	//ajax成功時會回來的method
	function ajaxSuccess(action, data, form){
		if(action == 'findHandlercodeVaild'){
			if(data.isExist){
				form_submit("update");
			}else{
				alert("修改失敗，所屬服務人員無效或不存在，請確認。");
				return false;
			}
		}
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert('操作失敗');
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
<s:url action="default" namespace="/aps/027" var="goQuery"/>
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
<s:form theme="simple" action="default" namespace="/aps/027" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/027" id="mainForm" name="mainForm">
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr bgcolor="white">
				<td class="MainTdColor" align="center" width="200px">
					<span id="lbSearch"><b>修改作業</b></span></td>
				<td colspan="5" class="image"></td>
			</tr>
			<tr>
				<td width="120px" align="right">業務員代碼：</td>
				<td width="285px" align="left">
					<s:property value="prpdagent.agentcode"/>
				</td>
				<td width="120px" align="right">業務來源：</td>
				<td width="285px" align="left">
					<s:property value="prpdagent.businesssource"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">業務員姓名：</td>
				<td width="285px" align="left">
					<s:property value="prpdagent.agentname"/>
				</td>
				<td width="120px" align="right">登錄證號：</td>
				<td width="285px" align="left">
					<s:property value="prpdagent.logincode"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">歸屬部門：</td>
				<td width="285px" align="left">
					<s:property value="prpdagent.unitcode"/>
					<s:hidden name="prpdagent.unitcode" id="unitcode"/>
				</td>
				<td width="120px" align="right">所屬服務人員：</td>
				<td width="285px" align="left">
					<s:textfield key="prpdagent.handlercode" id="handlercode" size="10" maxlength="10"  theme="simple" />
				</td>			
			</tr>
		</tbody>
	</table>
	<table width="970px" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="center">
				<input value="存檔" type="button" onclick="ajaxAction('findHandlercodeVaild')"/>
				<input value="取消" type="button" onclick="javascript:form_submit('clear');"/>
			</td>
		</tr>
	</table>
	<s:hidden name="prpdagent.agentcode" id="agentcode"/>
</s:form>
<!-- form結束 -->
</body>
</html>