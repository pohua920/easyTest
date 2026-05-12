<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@include file="/common/taglibs.jsp" %>
<link href="${ctx}/css/top.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="script" src="${ctx}/js/jquery-1.6.4.js"></script>
<script type="text/javascript" language="script" src="${ctx}/js/login.js"></script>
</head>

<body>

<table width="100%" height="85" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="58" background="${ctx}/images/bg_frametop.gif">
     		<form id='NewFMLogin' style="display: none" method="post" target="_top">
	            <table bgcolor="" width="380" border="0" cellspacing="0" cellpadding="0" style="display: none">
			          <tr>
			            <td ><input id="userCode" name="userCode" type="hidden" class="input1" value="${sessionScope.loginInfo.employe.userCode}" />
			            </td>
			            <td ><label id="userCodeError"><s:fielderror fieldName="error"></s:fielderror></label>  </td>
			          </tr>
			          <tr>
			            <td ><input id="passWord" name="passWord" type="hidden" class="input1"   value="${sessionScope.loginInfo.employe.plaintextPassWord}" />
			            </td>
			            <td width="25%"><label  id="passwordError"> </label></td>
			          </tr>
			          <tr>
			             <td><input id="comCode" name="comCode" type="hidden"   value=" " />
			             	 <input id ="sysFlag" name="sysFlag" type="hidden"   value="yjx" />	
			             </td>
			          </tr>
			     </table>
            </form>
    <form id="logoutfm" method="post" target="_top"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="398"><img src="${ctx}/images/img_logo01.gif" width="397" height="58" /></td>
         <td align="center" class="size1"><s:text name="common.logInstitutions"/><%--登录机构 --%>：
            <select name="SyscomCode" class="input1" id="headselect" onchange="loginNew();">
              <c:forEach items="${sessionScope.loginInfo.powerComcode }" var="temp">
                  <c:if test="${temp.comCode eq sessionScope.loginInfo.employe.loginComCode}">
                    <option value="${temp.comCode}" selected="selected">${temp.comCName}</option>
                  </c:if>
                  <c:if test="${temp.comCode ne  sessionScope.loginInfo.employe.loginComCode}">
                     <option value="${temp.comCode}">${temp.comCName}</option>
                  </c:if>
                           
              </c:forEach>

            </select>
           
         </td>  
        <td align="center" class="size1"><s:text name="common.clientCode"/><%--用户代码 --%>：${sessionScope.loginInfo.employe.userCode} 　<s:text name="workflow.institutions"/><%--机构 --%>：${sessionScope.loginInfo.employe.loginComCode}　    <s:text name="print.data"/><%--日期 --%>：${sessionScope.loginInfo.time}</td>
        <td width="74" align="left"><a href="#" onclick="logout(); return false;"  ><img src="${ctx}/images/img_exit.gif" width="44" height="20" border="0" /></a></td>
      </tr>
    </table>
    </form>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top" background="${ctx}/images/bg_helpline.gif">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td align="left"  width="400" class="size2">&nbsp;&nbsp;&nbsp;&nbsp;当前位置:<span id="menuLocal"></span> </td>
	        <td align="right" class="size2"><a href="${ctx}/web/rms/user/editPassWord.jsp"  target="fraInterface"class="size2">修改密码</a></td>
	        <td width="17"><img src="${ctx}/images/img_helpline.gif" width="17" height="27" /></td>
	        <td width="50" class="size2"><a href="#" class="size2"> </a></td>
	      </tr>
     </table>
    </td>
  </tr>
</table>
</body>
</html>
