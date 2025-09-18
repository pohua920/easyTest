<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "兌換率維護管理";
String image = path + "/" + "images/";
String GAMID = "APS019U0";
String mDescription = "兌換率維護管理";
String nameSpace = "/aps/019";
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
		//validate
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"aps019EditVo.branchName":{
					"required":true,
				},
				"aps019EditVo.handler1code":{
					"required":true
				}
			},
			messages: {
				"aps019EditVo.branchName":{
					"required":"請輸入分行名稱!",
				},
				"aps019EditVo.handler1code":{
					"required":"請輸入服務人員!"
				}
			}
		});
	});
			
	function form_submit(type){
		 $("label").html('');
		if("update" == type){
			 $("#mainForm").attr("action","btnUpdate.action");
			 $("#mainForm").submit();
		}
		
		if("clear" == type){
			if(confirm("請確認是否放棄修改並返回查詢頁面？")){
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
	
	function ajaxAction(action){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 
		var paramHandler1code = $('#handler1code').val();
		
		if(action == 'findHandler1codeData'){
			document.getElementById("handler1codename").innerHTML = "";//服務人員姓名
			document.getElementById("comcode").innerHTML = "";//歸屬單位
			document.getElementById("comcname").innerHTML = "";//歸屬單位名稱
	    	path = contextPath + '/aps/ajax005/findHandler1codeData.action';
		}
		
		if(paramHandler1code.length <= 0){
			return;
		}

		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {	handler1code:paramHandler1code},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				ajaxError(data, status);
			},
			success: function (data, status){
				ajaxSuccess(action, data);
			}
		});
	}
	//ajax成功時會回來的method
		function ajaxSuccess(action, data){
		//ajax回傳的data存在，將ajax回傳的data設定至頁面上
		if(data.ajax005PrpduserVo != null){
			document.getElementById("handler1codename").innerHTML = data.ajax005PrpduserVo.username;//服務人員姓名
			document.getElementById("comcode").innerHTML = data.ajax005PrpduserVo.comcode;//歸屬單位
			document.getElementById("comcname").innerHTML = data.ajax005PrpduserVo.comcname;//歸屬單位名稱
		}else{//ajax回傳的data不存在
			alert("無回傳資料!!!");
		}
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert("無資料");
	}
	
</script>
</head>
<s:url action="default" namespace="/aps/019" var="goQuery"/>
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
<s:form theme="simple" action="default" namespace="/aps/019" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/019" id="mainForm" name="mainForm">
<s:hidden name="prpdexch.exchcurrency" id="exchcurrency"/>
<s:hidden name="prpdexch.exchdate" id="exchdate"/>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>修改作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">幣別：</td>
			<td width="285px" align="left">
				<s:select key="prpdexch.basecurrency" id="basecurrency" list="currencyMap"/>
			</td>
			<td width="200px" align="right">匯率日期：</td>
			<td width="285px" align="left"><s:property value="prpdexch.exchdate" /></td>			
		</tr>
		<tr>
			<td width="200px" align="right">買進即期匯率：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdexch.spotbuyrate" id="spotbuyrate"  theme="simple" />
			</td>
			<td width="200px" align="right">賣出即期匯率：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdexch.spotsoldrate" id="spotsoldrate"  theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">平均匯率：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdexch.exchrate" id="exchrate"  theme="simple" />
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>			
		</tr>
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="修改" onclick="javascript:form_submit('update');"/>
			<input value="取消" type="button" onclick="javascript:form_submit('clear');"/>
		</td>
	</tr>
</table>
</s:form>
<!-- form結束 -->
</body>
</html>