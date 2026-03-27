<%@ page session="false" %>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
//@ page import="com.sinosoft.platform.ipservice.IpSelectAction"
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta name="keywords" content="Central Authentication Service,JA-SIG,CAS" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/style/index.css'/>" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/style/layout_index.css'/>" />
<link rel="stylesheet" href="<c:url value='/style/home.css'/>" type="text/css" media="all" />
<link rel="stylesheet" href="<c:url value='/style/jasig.css'/>" type="text/css" media="all" />

<title>应用工具管理系统</title>
<link href="${ctx}/style/style_all.css" rel="stylesheet" type="text/css" />
<!-- modify update by tongziliang 2011-09-29 raeson: 修改登录页面的样式 -->  
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link></head>



<script language="javascript">	
	if (self!=top){
    	top.location=self.location;
 	 }
function submitForm(){
	if(fm.password.value == ""||fm.userCode.value == ""||fm.verifyCode.value == ""){
		alert("请输入账号,密码和验证码！");
		return false;
	}else{
		return true;
	}
  
}
function init(){
  fm.userCode.focus();
  fm.userCode.select();
}
//add by duanfa20110823 
function reloadImg(Img){
			Img.src = "${ctx}/saaUserPower/imageValidate.do?temp=" + Math.random();
		}

</script>
</head>
<body class="logon" onLoad="init();">

<form name="fm"  action="${ctx}/saaUserPower/login.do" method="post" onSubmit="return submitForm();">

<input type="hidden" value="dms" name="svrCode">
<input type="hidden" value="nameAndPwd" name="loginMethod">


<table width="1011" height="479" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<td width="360"><img src="${ctx}/style/images/image_01.jpg" /></td>
<td width="609"><img src="${ctx}/style/images/image_02.jpg" /></td>
<td width="60"><img src="${ctx}/style/images/image_03.jpg" /></td>
</tr>
<tr>
<td><img src="${ctx}/style/images/image_04.jpg" /></td>
<td class="login_bg"><table width="607" height="304" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="308" height="304">&nbsp;</td>
    <td width="299" valign="top"><table width="298" height="296" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="32" height="72">&nbsp;</td>
        <td width="227" align="center">&nbsp;</td>
        <td width="39">&nbsp;</td>
      </tr>
      <tr>
        <td height="21">&nbsp;</td>
        <td height="21" class="login_writ">用户名/USER:</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="21">&nbsp;</td>
        <td height="21"><input class="login" type="text" name="userCode" maxlength="10" tabindex="1" value=""/></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="22">&nbsp;</td>
        <td height="24"  class="login_writ">密码/PASSWORD:</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="21">&nbsp;</td>
        <td height="21"><input class="login" type="password" name="password" tabindex="2"/></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="21">&nbsp;</td>
        <td height="21"  class="login_writ">验证码/AUTH CODE:
        
        </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="19">&nbsp;</td>
        <td height="25"><input name="verifyCode" id='validateCode' maxlength="100" type="text" class="login" style="width: 100px;" >&nbsp;<img id="codeImage" src="${ctx}/saaUserPower/imageValidate.do" onclick="reloadImg(this)" width="50" height="20" border="0">
<!--        <span class="login_writ02"><a href="#">看不清换一个</a></span>--></td>                                                           
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="19">&nbsp;</td>
        <td height="25" class="prompting"><%
			String login_error=request.getParameter("login_error");
			if(login_error!=null){
		%>
			用户名或密码错误!
		<%}%>      </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="91">&nbsp;</td>
        <td align="center">
<!--        <input type="image" name="imageField" src="${ctx}/pages/login_images/BtnLogon.gif"  style="width:65px;height:25px;" tabindex="3"/>-->
        <input style="cursor: hand;" value="" type="image" class="button_01" src="${ctx}/pages/login_images/BtnLogon.gif"
	 onmouseover="this.style.backgroundPosition='left -42px'" 
	 onmouseout="this.style.backgroundPosition='left top'" />
	 </td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table></td>
<td><img src="${ctx}/style/images/image_06.jpg" /></td>
</tr>
<tr>
<td><img src="${ctx}/style/images/image_07.jpg" /></td>
<td><img src="${ctx}/style/images/image_08.jpg" /></td>
<td><img src="${ctx}/style/images/image_09.jpg" /></td>
<!-- end by tongziliang 2011-09-06 -->
</tr>
</table>
</form>
</body>
</html>
