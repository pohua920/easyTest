<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String title = "保經代分支機構維護";
String image = path + "/" + "images/";
String GAMID = "APS028U0";
String mDescription = "保經代分支機構維護";
String nameSpace = "/aps/028";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求 -->
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
				"prpdagentsub.agentsubname":{
				"required":true,
				},
				"prpdagentsub.addressname":{
				"required":true,
				},
				"prpdagentsub.email":{
				"checkEmail":""
				}
			},
			messages: {
				"prpdagentsub.agentsubname":{
				"required":"必入欄位不得為空。",
				},
				"prpdagentsub.addressname":{
				"required":"必入欄位不得為空。",
				},
				"prpdagentsub.email":{
				"checkEmail":"格式不正確。"
				}
			}
		});
		
		var kind = $("#kind").val();
		$('input[name="prpdagentsub.kind"]').each(function(i){
			if(kind == $(this).val()){
				$(this).prop("checked", true);
			}
		});
		
		$('input[name="prpdagentsub.kind"]').click(function () {
			var selected = $(this).val();
			$('input[name="prpdagentsub.kind"]').each(function(i){
				if($(this).val() != selected){
					$(this).prop("checked", false);
				}
			});
		});
	});
	
	$.validator.addMethod("checkEmail", function(value,element,param) { 
		return checkEmail($("#email").val());						
	},"");
	
	function checkEmail(val){
		var regex =/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		if(val!="" && !regex.test(val)){
			return false;
		}
		return true;
	}
	
	function form_submit(type){
		if("update" === type && confirm("確認儲存修改?")){
			//mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化START
			if(validate()){
				$("#mainForm").attr("action","btnUpdate.action");
				$("#mainForm").submit();
			}
			//mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化END
		}
		
		if("clear" === type){
			if(confirm("請確認是否放棄儲存並返回查詢頁面？")){
				$("#clearForm").submit();
			}
			return false;
		}
	}

	//mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化START
	function validate(){
		var message = "";
		var check = true;

		// 通訊電話格式檢查
		var phoneNumber = $('#phonenumber').val();	
		var regExp = /^[0-9]+$/;
		//mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 START
		if(!regExp.test(phoneNumber) || phoneNumber.substring(0,1) != "0"){
			alert("存檔失敗，通訊電話格式不正確，不能包含特殊符號，僅能輸入數字且須輸入區域碼以0開頭。");
			check = false;
		}
		//mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 END

		if(check){
			// 地址正規化檢查
			//取出ajax xml相對路徑
			var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
			var path = ''; 

			var address = $("#addressname").val();
		   	path = contextPath + '/aps/ajax011/formatAddressCheck.action';
			
			//執行ajax
			$.ajax({
				url : path,
				type: 'POST',
				data: {address:address},
				dataType: 'json',
				timeout: 10000,
				async: false,
				cache: false,
				error: function (data, status, e){
					message += " 地址正規化檢查失敗。"
					check = false;
				},
				success: function (data, status){
					if(data.errmsg != ''){//地址正規化不成功
						if(confirm("地址正規化不成功，確認是否存檔作業。")){
							check = true;
						}else{
							check = false;
						}
						
					// mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 START
					} else {
						$("#addressname").val(data.addressFormatted);
						$("#postcode").val(data.postcode);
					}
					// mantis：SALES0015，處理人員：CC009，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化 END
				}
			});
		}
		return check;
	}
	//mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化END
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
</script>
</head>
<s:url action="default" namespace="/aps/028" var="goQuery"/>
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
<s:form theme="simple" action="default" namespace="/aps/028" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/028" id="mainForm" name="mainForm">
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr bgcolor="white">
				<td class="MainTdColor" align="center" width="200px">
					<span id="lbSearch"><b>修改作業</b></span></td>
				<td colspan="5" class="image"></td>
			</tr>
		</tbody>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr>
				<td width="120px" align="right"><font color="#FF0000">*</font>保經代原有編號：</td>
				<td width="180px" align="left">
					<s:property value="aps028ResultVo.orgicode"/>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right"><font color="#FF0000">*</font>保經代編號：</td>
				<td width="180px" align="left">
					<s:property value="prpdagentsub.agentcode"/>
					<s:hidden key="prpdagentsub.agentcode"/>
				</td>
				<td width="120px" align="right">保經代名稱：</td>
				<td width="285px" align="left">
					<s:property value="aps028ResultVo.agentname"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right"><font color="#FF0000">*</font>保經代分支機構編號：</td>
				<td width="180px" align="left">
					<s:property value="prpdagentsub.agentsubcode"/>
					<s:hidden key="prpdagentsub.agentsubcode"/>
				</td>
				<td width="120px" align="right"><font color="#FF0000">*</font>保經代分支機構名稱：</td>
				<td width="285px" align="left">
					<s:textfield key="prpdagentsub.agentsubname" id="agentsubname" size="42" maxlength="80"  theme="simple" />
					<font size="1" color="#444444">(上限80個中文字)</font>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">郵遞區號：</td>
				<td width="180px" align="left">
					<s:textfield key="prpdagentsub.postcode" id="postcode" theme="simple" size="10" maxlength="20" />
				</td>
				<td width="120px" align="right"><font color="#FF0000">*</font>地址：</td>
				<td width="285px" align="left">
					<s:textfield key="prpdagentsub.addressname" id="addressname" size="40" maxlength="130"  theme="simple" />
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">電子郵箱：</td>
				<td width="180px" align="left">
					<s:textfield key="prpdagentsub.email" id="email" theme="simple" size="20" maxlength="120" />
				</td>
				<td width="120px" align="right">傳真號碼：</td>
				<td width="285px" align="left">
					<s:textfield key="prpdagentsub.faxnumber" id="faxnumber" size="15" maxlength="40"  theme="simple" />
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">通訊電話：</td>
				<td width="180px" align="left">
					<s:textfield key="prpdagentsub.phonenumber" id="phonenumber" theme="simple" size="15" maxlength="40" />
				</td>
				<td width="120px" align="right">是否有效：</td>
				<td width="285px" align="left">
					<s:radio key="prpdagentsub.validstatus" id="validstatus" list="#{'1':'有效','0':'無效'}" />
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">備註：</td>
				<td width="285px" align="left">
					<s:textarea key="prpdagentsub.remark" id="remark" theme="simple" maxlength="130" style="margin: 0px; width: 300px; height: 55px;"/>
					<br><font size="1" color="#444444">(上限130個中文字)</font>
				</td>
				<td width="120px" align="right">來源通路別：</td>
				<td width="285px" align="left">
					<s:checkbox key="prpdagentsub.kind" fieldValue="台壽" />台壽 &nbsp; &nbsp;
					<s:checkbox key="prpdagentsub.kind" fieldValue="全國經代" />全國經代 &nbsp; &nbsp;
					<s:checkbox key="prpdagentsub.kind" fieldValue="地區經代" />地區經代 &nbsp; &nbsp;
					<s:checkbox key="prpdagentsub.kind" fieldValue="銀行" />銀行
					<input type="hidden" id="kind" value="${prpdagentsub.kind}"/>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">建立人員：</td>
				<td width="285px" align="left">
					<s:property value="prpdagentsub.icreate"/>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">修改人員：</td>
				<td width="285px" align="left">
					<s:property value="prpdagentsub.iupdate"/>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">建立時間：</td>
				<td width="285px" align="left">
					<fmt:formatDate value='${prpdagentsub.dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">修改時間：</td>
				<td width="285px" align="left">
					<fmt:formatDate value='${prpdagentsub.dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
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
	<s:hidden name="prpdagent.agentcode" id="agentcode"/>
</s:form>
<!-- form結束 -->
</body>
</html>