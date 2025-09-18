<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String title = "高風險地區維護管理";
String image = path + "/" + "images/";
String GAMID = "APS021U0";
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

		$("#strStartdate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});

		$("#strEnddate").datepicker({
			changeMonth: true,  
			changeYear: true,
			dateFormat:'yyyy/mm/dd'
		});

		//validate
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"prpdhighareaMc.countycname":{
				"required":true
				},
				"prpdhighareaMc.countyename":{
				"required":true
				},
				"prpdhighareaMc.strStartdate":{
				"required":true,
				"checkStartdate":""
				},
				"prpdhighareaMc.strEnddate":{
				"required":true,
				"checkEnddate":""
				}
			},
			messages: {
				"prpdhighareaMc.countycname":{
				"required":"請輸入國家中文名稱!"
				},
				"prpdhighareaMc.countyename":{
				"required":"請輸入國家英文名稱!"
				},
				"prpdhighareaMc.strStartdate":{
				"required":"請輸入啟用日期!",
				"checkStartdate":"日期格式錯誤"
				},
				"prpdhighareaMc.strEnddate":{
				"required":"請輸入停用日期!",
				"checkEnddate":"日期格式錯誤"
				}
			}
		});
	});
	
	$.validator.addMethod("checkStartdate", function(value,element,param) { 
		return checkDate($("#strStartdate").val());						
	},"");
	$.validator.addMethod("checkEnddate", function(value,element,param) { 
		return checkDate($("#strEnddate").val());						
	},"");
	
	function checkDate(date){
		var adRegex=/^([0-9]\d{3}\/(0+[1-9]|1[012])\/(0+[1-9]|[12][0-9]|3[01]))*$/;
		if(!adRegex.test(date)){
			return false;
		}
		return true;
	}
	
	function form_submit(type){
		if("update" == type){
			 $("#mainForm").attr("action","btnUpdate.action");
			 $("#mainForm").submit();
		}
		
		if("clear" == type){
			if(confirm("請確認是否放棄存檔並返回查詢頁面？")){
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
					<span id="lbSearch"><b>修改作業</b></span></td>
				<td colspan="5" class="image"></td>
			</tr>
			<tr>
				<td width="120px" align="right">地區別：</td>
				<td width="285px" align="left">
					<s:textfield key="prpdhighareaMc.countycode" id="countycode" readonly="true" cssClass="txtLabel" theme="simple" />
				</td>
				<td width="120px" align="right">國家簡碼：</td>
				<td width="285px" align="left">
					<s:textfield key="prpdhighareaMc.shortcode" id="shortcode" readonly="true" cssClass="txtLabel"   theme="simple" />
				</td>			
                <!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 start -->
				<td width="200px" align="right"><font color="#FF0000">*</font>高風險地區：</td>
				<td width="150px" align="left">
					<s:radio key="prpdhighareaMc.status" id="status" list="#{'Y':'是','N':'否'}" theme="simple" />
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
					<s:radio key="prpdhighareaMc.sanctionsStatus" id="sanctionsStatus" list="#{'Y':'是','N':'否'}" theme="simple" />
				</td>
				<!-- mantis：MAR0079，處理人員：DP0714，APS高風險地區新增經濟制裁註記及核心檢核 end -->
			</tr>
			<tr>
				<td width="120px" align="right"><font color="#FF0000">*</font>啟用日期：</td>
				<td width="285px" align="left">
					<s:textfield key="prpdhighareaMc.strStartdate" id="strStartdate" size="10" maxlength="10" theme="simple" />
				</td>
				<td width="120px" align="right"><font color="#FF0000">*</font>停用日期：</td>
				<td width="285px" align="left">
					<s:textfield key="prpdhighareaMc.strEnddate" id="strEnddate" size="10" maxlength="10" theme="simple" />
				</td>
			</tr>
		</tbody>
	</table>
	<table width="970px" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="center">
				<input value="修改" type="button" onclick="javascript:form_submit('update');"/>
				<input value="取消" type="button" onclick="javascript:form_submit('clear');"/>
			</td>
		</tr>
	</table>
</s:form>
<!-- form結束 -->
</body>
</html>