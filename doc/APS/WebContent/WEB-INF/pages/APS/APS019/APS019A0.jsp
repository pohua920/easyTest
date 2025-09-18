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
		$('#exchdate').datepicker({dateFormat:"yyyy/mm/dd"});
		//mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正
		$('#batchExchdate').datepicker({dateFormat:"yyyy/mm/dd"});
		//validate
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"prpdexch.exchrate":{
					"required":true,
				},
				"prpdexch.exchdate":{
				"required":true,
				}
			},
			messages: {
				"prpdexch.exchrate":{
					"required":"請輸入平均匯率!",
				},
				"prpdexch.exchdate":{
					"required":"請輸入匯率日期!",
				},
			}
		});
	});
			
	function form_submit(type){
		 $("label").html('');
		if("create" == type){
			 $("#mainForm").attr("action","btnCreate.action");
			 $("#mainForm").submit();
		}
		
		if("clear" == type){
			if(confirm("請確認是否放棄修改並返回查詢頁面？")){
				$("#clearForm").submit();
			}
			return false;
		}
		
		/*mantis：MAR0029，處理人員：BJ085，需求單編號：MAR0029 水險兌換率幣別*/
		if("execute" == type){
			/*mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 start*/
			if($("#batchExchdate").val()==""){
				alert("使用批次上傳時，匯率日期不得為空。");
				return false;
			}
			if(confirm("若已有匯率日期為當日的匯率資料，則會將當日所有匯率資料刪除，再以檔案內容新增當日匯率資料，是否確定上傳?")){
			/*mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 end*/
				$("#mainFormUpload").attr("action","btnBatchUpload.action");
				$("#mainFormUpload").submit();
			}
			return false;
		}
	}
	
	/*mantis：MAR0029，處理人員：BJ085，需求單編號：MAR0029 水險兌換率幣別 start*/
	function ajaxAction(action){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 

		if(action == 'findExchrate'){
	   		var exchdate = $("#exchdate").val();
	   		var basecurrency = $("#basecurrency").val();
	    	path = contextPath + '/aps/ajax007/findExchdateByUK.action?exchdate='+exchdate+'&basecurrency='+basecurrency;
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
				ajaxSuccess(action, data);
			}
		});
	}
	//ajax成功時會回來的method
	function ajaxSuccess(action, data){
		if(action == 'findExchrate'){
			if(data.isExist){
				alert("此匯率日期已有此幣別匯率資料存在，無法新增! 如欲變更資料，請使用修改功能");
			}else{
				form_submit("create");
			}
		}
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert('操作失敗');
	}
	/*mantis：MAR0029，處理人員：BJ085，需求單編號：MAR0029 水險兌換率幣別 end*/
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
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
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>新增作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">單筆新增：</td>
			<td width="285px" align="left"></td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>			
		</tr>
		<tr>
			<td width="200px" align="right">幣別：</td>
			<td width="285px" align="left">
				<s:select key="prpdexch.basecurrency" id="basecurrency" list="currencyMap"/>
			</td>
			<td width="200px" align="right">匯率日期：</td>
			<td width="285px" align="left">
				<s:textfield key="prpdexch.exchdate" id="exchdate"  theme="simple" />
			</td>			
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
			<!-- mantis：MAR0029，處理人員：BJ085，需求單編號：MAR0029 水險兌換率幣別  -->
			<input type="button" value="新增" onclick="ajaxAction('findExchrate')"/>
			<input value="取消" type="button" onclick="javascript:form_submit('clear');"/>
		</td>
	</tr>
</table>
</s:form>
<!-- form結束 -->

<s:form theme="simple" namespace="/aps/012" id="mainFormUpload" name="mainFormUpload" enctype="multipart/form-data" method="POST" >
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr>
			<td width="200px" align="right">批次上傳：</td>
			<td align="left"></td>
		</tr>
		<tr>
			<td width="200px" align="right"></td>
			<td align="left">
				<s:file name="upload" label="File"/>
				<input type="button" value="EXCEL上傳" onclick="javascript:form_submit('execute');"/>
			</td>
			<!--mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 start -->
			<td width="200px" align="right">匯率日期：</td>
			<td width="285px" align="left">
				<s:textfield key="batchExchdate" id="batchExchdate"  theme="simple" />
			</td>	
			<!--mantis：MAR0050，處理人員：BJ085，需求單編號：MAR0050 兌換率設定檔修正 end -->
		</tr>
	</tbody>
</table>
</s:form>
</body>
</html>