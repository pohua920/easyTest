<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String title = "高風險地區維護管理";
String image = path + "/" + "images/";
String GAMID = "APS021A0";
String mDescription = "高風險地區維護管理";
String nameSpace = "/aps/021";
%>
<!-- mantis：MAR0037，處理人員：BJ085，需求單編號：MAR0037 高風險地區新增維護檔 start -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	$(document).ready(function(){
		$("#startdate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});
		
		$("#enddate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});
		
		//validate
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"prpdhighareaMc.countycode":{
				"required":true
				},
				"prpdhighareaMc.shortcode":{
				"required":true
				},
				"prpdhighareaMc.countycname":{
				"required":true
				},
				"prpdhighareaMc.countyename":{
				"required":true
				},
				"prpdhighareaMc.startdate":{
				"required":true,
				"checkStartdate":""
				},
				"prpdhighareaMc.enddate":{
				"required":true,
				"checkEnddate":""
				}
			},
			messages: {
				"prpdhighareaMc.countycode":{
				"required":"請輸入地區別"
				},
				"prpdhighareaMc.shortcode":{
				"required":"請輸入國家簡碼"
				},
				"prpdhighareaMc.countycname":{
				"required":"請輸入國家中文名稱"
				},
				"prpdhighareaMc.countyename":{
				"required":"請輸入國家英文名稱"
				},
				"prpdhighareaMc.startdate":{
				"required":"請輸入啟用日期",
				"checkStartdate":"日期格式錯誤"
				},
				"prpdhighareaMc.enddate":{
				"required":"請輸入停用日期",
				"checkEnddate":"日期格式錯誤"
				}
			},
			submitHandler:function(form){
				ajaxAction('findPrpdhighareaMc',form);
			}
		});
	});
	
	$.validator.addMethod("checkStartdate", function(value,element,param) { 
		return checkDate($("#startdate").val());						
	},"");
	$.validator.addMethod("checkEnddate", function(value,element,param) { 
		return checkDate($("#enddate").val());						
	},"");
	
	function checkDate(date){
		var adRegex=/^([0-9]\d{3}\/(0+[1-9]|1[012])\/(0+[1-9]|[12][0-9]|3[01]))*$/;
		if(!adRegex.test(date)){
			return false;
		}
		return true;
	}
			
	function form_submit(type){
		if("create" == type){
			 $("#mainForm").attr("action","btnCreate.action");
			 $("#mainForm").submit();
		}
		
		if("clear" == type){
			if(confirm("請確認是否放棄新增並返回查詢頁面？")){
				$("#clearForm").submit();
			}
			return false;
		}
	}
	
	function ajaxAction(action,form){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 

		if(action == 'findPrpdhighareaMc'){
	   		var countycode = $("#countycode").val();
	   		var shortcode = $("#shortcode").val();
	    	path = contextPath + '/aps/ajax007/findPrpdhighareaMcByUK.action?countycode='+countycode+'&shortcode='+shortcode;
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
		if(action == 'findPrpdhighareaMc'){
			if(data.isExist){
				alert("此地區別與國家已存在，故無法儲存!");
				return false;
			}else{
				form.submit();
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
<s:url action="default" namespace="/aps/021" var="goQuery"/>
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
	<s:form theme="simple" action="default" namespace="/aps/021" id="clearForm" name="clearForm"/>
	<!--form 開始 -->
	<s:form theme="simple" namespace="/aps/021" id="mainForm" name="mainForm">
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
			<tbody>
				<tr bgcolor="white">
					<td class="MainTdColor" align="center" width="200px">
						<span id="lbSearch"><b>新增作業</b></span></td>
					<td colspan="5" class="image"></td>
				</tr>
				<tr>
					<td width="120px" align="right"><font color="#FF0000">*</font>地區別：</td>
					<td width="285px" align="left">
						<s:textfield key="prpdhighareaMc.countycode" id="countycode" size="10" maxlength="10" theme="simple" />
					</td>
					<td width="120px" align="right"><font color="#FF0000">*</font>國家簡碼：</td>
					<td width="285px" align="left">
						<s:textfield key="prpdhighareaMc.shortcode" id="shortcode" size="10" maxlength="20"  theme="simple" />
					</td>			
                    <!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 start -->
					<td width="200px" align="right"><font color="#FF0000">*</font>高風險地區：</td>
					<td width="150px" align="left">
						<s:radio key="prpdhighareaMc.status" id="status" list="#{'Y':'是','N':'否'}" value="'Y'" theme="simple" />
					</td>
					<!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 end -->
				</tr>
				<tr>
					<td width="120px" align="right"><font color="#FF0000">*</font>國家中文名稱：</td>
					<td width="285px" align="left">
						<s:textfield key="prpdhighareaMc.countycname" id="countycname" size="20" maxlength="60" theme="simple" />
					</td>
					<td width="120px" align="right"><font color="#FF0000">*</font>國家英文名稱：</td>
					<td width="285px" align="left">
						<s:textfield key="prpdhighareaMc.countyename" id="countyename" size="20" maxlength="200" theme="simple" />
					</td>			
                    <!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 start -->
					<td width="200px" align="right"><font color="#FF0000">*</font>經濟制裁國家：</td>
					<td width="150px" align="left">
						<s:radio key="prpdhighareaMc.sanctionsStatus" id="sanctionsStatus" list="#{'Y':'是','N':'否'}" value="'N'" theme="simple" />
					</td>			
                    <!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 end -->
				</tr>
				<tr>
					<td width="120px" align="right"><font color="#FF0000">*</font>啟用日期：</td>
					<td width="285px" align="left">
						<s:textfield key="prpdhighareaMc.startdate" id="startdate" size="10" maxlength="10" theme="simple" />
					</td>
					<td width="120px" align="right"><font color="#FF0000">*</font>停用日期：</td>
					<td width="285px" align="left">
						<s:textfield key="prpdhighareaMc.enddate" id="enddate" size="10" maxlength="10" theme="simple" />
					</td>
				</tr>
			</tbody>
		</table>
		<table width="970px" cellpadding="0" cellspacing="0" >
			<tr>
				<td align="center">
					<input type="button" value="新增" onclick="javascript:form_submit('create');"/>
					<input type="button" value="取消" onclick="javascript:form_submit('clear');"/>
				</td>
			</tr>
		</table>
	</s:form>
	<!-- form結束 -->
</body>
</html>