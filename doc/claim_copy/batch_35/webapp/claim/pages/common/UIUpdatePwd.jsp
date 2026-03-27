<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>

<%@ page import="com.sinosoft.claim.dto.custom.*"%>
<%
	UserDto userDto = (UserDto) session.getAttribute("user");
	String UserCode = (String) userDto.getUserCode();
	String passWord = (String) userDto.getPassword();
	String UserName = (String) userDto.getUserName();
%>

<html>
<head>
  <title>update password</title>
  <!-- 页面样式  -->
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <link rel="stylesheet" type="text/css" href="/claim/css/TagPage.css">
  
  <script language=Javascript>
  function mySubmit() {
		if (fm.oldPassword.value == "") {
			alert("原密码输入有误，请重新输入");
			fm.oldPassword.value = "";
			return false;
		} else if (fm.newPassword.value == "") {
			alert("请输入新密码");
			fm.newPassword.value = "";
			fm.retypeNewPassword.value = "";
		} else if (fm.newPassword.value != fm.retypeNewPassword.value) {
			alert("两次新密码输入不一致,请重新输入");
			fm.newPassword.value = "";
			fm.retypeNewPassword.value = "";
			return false;
		} else {
			fm.action = "/claim/UIUpdatePwd.do?actionType=update&typeFrom=out";
			fm.target = "_self";
			fm.submit();
		}
	}

	function relogin() {
		fm.action = "/claim/index.jsp";
		fm.target = "_self";
		fm.submit();
	}

	function myReset() {
		fm.reset();
	}

	function KeyDown() {
		if (event.keyCode == 13) {
			mySubmit();
		}
	}

	function showMessage() {
		var field = document.getElementById("showMessage");
		if (field != null && trim(field.value) != '') {
			alert(field.value);
		}
		var fields = document.getElementsByName("showEditMessage");
		for (var i = 0; i < fields.length; i++) {
			alert(fields[i].value);
		}
	}
  </script>
</head>
<body class="interface" onload="showMessage()">
	<form action="/claim/updatePwd.do" method=post name=fm>
		<table class=three align="center" style="width: 400">
			<tr>
				<td class=formtitle colspan="2">
					<s:text name="COMMON.userPassword" />
					<%-- 用户密码修改 --%>
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 50%">
					<s:text name="prpDuser.userName" />
					：
				</td>
				<%-- 用户名 --%>
				<td class="input" style="width: 50%">
					<input name='userName' class=readonly readonly value='<%=UserName%>'>
					<input type=hidden name='userCode' value='<%=UserCode%>'>
					<input type=hidden name='Password' value='<%=passWord%>'>
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="COMMON.originalPassword" />
					:
				</td>
				<%-- 原密码 --%>
				<td class="input">
					<input class=common type="password" name='oldPassword' onkeypress="KeyDown()">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="COMMON.newPassword" />
					:
				</td>
				<%-- 新密码 --%>
				<td class="input">
					<input class=common type="password" name='newPassword' onkeypress="KeyDown()" maxlength="20">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="COMMON.resetPassword" />
					:
				</td>
				<%-- 重复新密码 --%>
				<td class="input">
					<input class=common type="password" name='retypeNewPassword' onkeypress="KeyDown()" maxlength="20">
				</td>
			</tr>
			<tr align=center>
				<td colspan=8>
					<input class="button" type="button" alt=" 确 定 " value="<s:text name='button.determine.value'/>" onclick="mySubmit()">
					<%-- 确 定 --%>
					<input class="button" type="button" alt=" 重 写 " value="<s:text name='button.heavyWrite.value'/>" onclick="myReset()">
					<%--重 写 --%>
					<input class="button" type="button" alt="重新登陆" value="<s:text name='button.toLogin.value'/>" onclick="relogin()">
					<%-- 重新登陆 --%>
				</td>
			</tr>
		</table>
		<%@include file="/common/message/ShowMessage.jsp"%>
	</form>
</body>
</html>
