<%@ page session="false" %>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sinosoft.platform.ipservice.IpSelectAction"%>

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

<!-- modify by duanfa 20110728 start 修改样式 -->
<!--<table width="1004" height="100%" border="0" cellpadding="0" cellspacing="0" background="${ctx}/pages/login_images/bglogon_4.gif">
  <tr>
    <td rowspan="2" valign="top" class="bgleft"><img src="${ctx}/pages/login_images/imglogon_1.jpg" /></td>
    <td valign="top" ><img src="${ctx}/pages/login_images/imglogon_2.jpg" /></td>
    <td rowspan="2" valign="top" class="bgright"><img src="${ctx}/pages/login_images/imglogon_3.jpg"/></td>
  </tr>
  
  <tr>
    <td height="200" bgcolor="#f8f8f8" class="bg">
    	<table width="358" border="0" cellspacing="0" cellpadding="0" >
      <tr>
        <td height="8"><img src="${ctx}/pages/login_images/bglogontop.gif" /></td>
      </tr>
      <tr>
        <td height="120" align="center" background="${ctx}/pages/login_images/bglogon_2.gif">
        	<table width="90%"  border="0" cellspacing="0" cellpadding="5">
          <tr>
            <td align="center">
			          <table width="100%" border="0" cellspacing="0" cellpadding="5">
                      <tr> 
                        <td height="30" align="right"><img src="${ctx}/pages/login_images/imgusername.gif" ></td>
                        <td><input class="logon" type="text" name="userCode" maxlength="10" tabindex="1" value=""/></td>
                        <td >&nbsp;</td>
                      </tr>
                      <tr> 
                        <td width="20%" height="30" align="right"><img src="${ctx}/pages/login_images/imgpassword.gif"></td>
                         <td width="55%"><span class="username">
	                        <input class="logon" type="password" name="password" tabindex="2"/>
	                      </span></td>
	                      <td width="25%" align="center"><input type="image" name="imageField" src="${ctx}/pages/login_images/btn_login.gif" tabindex="3"/></td>  
                      </tr>
                    </table>			
                  </td>
          </tr>
          <tr>
             <td colspan="2" align="center" style="color='#FF3E39'"><%
			//String login_error=request.getParameter("login_error");
			//if(login_error!=null){
		%>
			用户名或密码错误!
		<%//}%>                 </td>
            </tr>
          

        </table></td>
      </tr>
      <tr>
        <td height="7"><img src="${ctx}/pages/login_images/bglogondown.gif" /></td>
      </tr>
      <tr>
        <td class="copyright"><img src="${ctx}/pages/login_images/imgcopyright.gif" align="absmiddle" /> 技术支持 中科软科技股份有限公司 </td>
      </tr>
    </table></td>
    </tr>
  <tr>
    <td height="100%" colspan="3" class="bgleft"></td>
    </tr>
</table>
-->
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="top" style="padding-top:145px;"><table width="685" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="66">　<img src="${ctx}/pages/login_images/img_logonlogo.png" /></td>
      </tr>
      <tr>
        <td valign="top" class="logon"><table width="250" border="0" cellspacing="0" cellpadding="3" id="table2">
          <tr>
            <td><input class="logon" type="text" name="userCode" maxlength="10" tabindex="1" value=""/></td>
			<td>&nbsp;</td>
          </tr>
          <!-- modify by duanfa20110823 添加验证码 -->
          <tr> 
            <td > <input class="logon" type="password" name="password" tabindex="2"/></td>
          </tr>
          <tr> 
            <td ><input name="verifyCode" id='validateCode' maxlength="100" type="text" class="logon" style="width: 100px;" >
            	<img id="codeImage" src="${ctx}/saaUserPower/imageValidate.do" onclick="reloadImg(this)" width="50" height="20" border="0">
            </td>
            <td align="right">
				<input type="image" name="imageField" src="${ctx}/pages/login_images/BtnLogon.gif"  style="width:65px;height:25px;" tabindex="3"/>
            </td>
          </tr>
		  <tr>
		      <td colspan="2">
		         <%
			String login_error=request.getParameter("login_error");
			if(login_error!=null){
		%>
			用户名或密码错误!
		<%}%>         
			  </td>
		   </tr>
        </table>
        </td>
      </tr>
      <tr>
        <td class="copyright">技术支持 中科软科技股份有限公司 </td>
      </tr>
    </table></td>
  </tr>
</table>
<!-- modify by duanfa 20110728 end 修改样式 -->
</form>
</body>


</html>