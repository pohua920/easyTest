<%--
*****************************************************
*DESC          : 修改用户密码
*AUTHOR        : 理赔组
*MODIFYLIST     :     NAME     DATE    REASON
*                  ---------------------------------
*****************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.claim.dto.custom.*"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%
	UserDto userDto = (UserDto) session.getAttribute("user");
	String UserCode = (String) userDto.getUserCode();
	String UserName = (String) userDto.getUserName();
%>
<html>
<head>
<title>update password</title>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script language="Javascript" src="/claim/common/js/psw.js"></script>
<script language=Javascript>
  
	function mySubmit() {
		var newPassword = fm.newPassword.value;
		var retypeNewPassword = fm.retypeNewPassword.value;
		if(newPassword.length < 6){
			alert("新密碼長度不得少於6位！");
			return;
		}
		if (fm.newPassword.value != fm.retypeNewPassword.value) {
			alert("新密碼錄入不一致！");
			return;
		}
		fm.submit();
	}
	function myReset() {
		fm.reset();
	}
</script>
</head>
<body class="interface">
	<form action="${ctx}/updatepwd/updatePwd.do" method=post name=fm>
		<table class=three align="center" style="width: 400">
			<tr>
				<td class=formtitle colspan="2">
					<s:text name="COMMON.userPassword" />
					<%--用户密码修改--%>
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 50%">
					<s:text name="prpDuser.userName" />
					：
				</td>
				<%--用户名--%>
				<td class="input" style="width: 50%">
					<input name='userName' class=readonly readonly value='<%=UserName%>'>
					<input type=hidden name='userCode' value='<%=UserCode%>'>
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="COMMON.originalPassword" />
					:
				</td>
				<%--原密码--%>
				<td class="input">
					<input class=common type="password" name='oldPassword'>
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="COMMON.newPassword" />
					:
				</td>
				<%--新密码--%>
				<td class="input">
					<input class=common type="password" name='newPassword' maxlength="20">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="COMMON.resetPassword" />
					:
				</td>
				<%--重复新密码--%>
				<td class="input">
					<input class=common type="password" name='retypeNewPassword' maxlength="20">
				</td>
			</tr>
			<tr align=center>
				<td>
					<input class="button" type="button" alt=" 确 定 " value="<s:text name='button.determine.value' />" onclick="mySubmit()">
				</td>
				<td>
					<input class="button" type="button" alt=" 重 写 " value="<s:text name='button.heavyWrite.value' />" onclick="myReset()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
