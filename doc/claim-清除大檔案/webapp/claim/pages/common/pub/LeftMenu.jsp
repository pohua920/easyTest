<%--
 *************************************************************************
 * 程序名称: LeftMenu.jsp
 * 程序功能: 左边菜单页面
 * 最近更新人: lixiang
 * 最近更新日期: 2004-06-14 
 ****************************************************************************
--%>

<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.ui.control.action.UIPowerInterface"%>
<%@ page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@ page import="com.sinosoft.claim.util.BusinessRuleUtil"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%
	//原因：加入了操作权限校验
	UserDto userDto = (UserDto) session.getAttribute("user");
	String riskCode = userDto.getRiskCode();
	String comCode = userDto.getComCode();
	if (riskCode == null)
		riskCode = BusinessRuleUtil.getOuterCode(request, "RISKCODE_DAA");
	String userCode = userDto.getUserCode(); //用户代码
	String checkCode = "";//校验代码
	String taskCode = "";//得到任务代码
	UIPowerInterface uiPowerInterface = new UIPowerInterface(); //权限校验
	boolean checkPower = false; //是否通过校验
%>
<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<app:css />
<STYLE>
v\:* {
	BEHAVIOR: url(#default#VML)
}
</STYLE>
<%-- JavaScript脚本--%>
<script language="javaScript">
		
           function showMenu(elementId){
           		var obj = document.getElementById(elementId);
            	if (obj.style.display == "")
               		obj.style.display="none";
             	else
               		obj.style.display="";
           }
           function clickme() { parent.fraSet.cols = "134,*,0%";}
	   	   function relogon(){	
      			top.location="/claim/index.jsp";
	       }
		</script>
<script language="JavaScript" type="text/javascript" src="/claim/common/js/leftMenu.js">
		</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#EFF1Fe" height="100%">
		<tr>
			<td id="menuTreeTD" valign="top" bgcolor="#EFF1Fe">
				<%
	//是否有权限
	checkCode = AppConfig.get("sysconst.CHECKCODE_EXE");
	taskCode = AppConfig.get("sysconst.TASKCODE_LP");
	checkPower = false;
	checkPower = uiPowerInterface.checkPower(userCode, taskCode, checkCode, riskCode);
	if (checkPower) {
%>
				<%@include file="/common/pub/testMenu.jsp"%>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!--添加修改密码功能 2005-08-27-->
					<tr>
						<td width="15%" height=25 align="center" valign="middle">
							<img src="/claim/images/cpArrowFunction.gif">
						</td>
						<td width="85%" class="menu1">
							<html:link page="/common/pub/UIUpdatePwd.jsp" target="fraInterface">
								<font color=000000> <s:text name="pub.changePassword" /></font>
								<%--修改密码--%>
							</html:link>
						</td>
					</tr>
					<tr>
						<td width="15%" height=25 align="center" valign="middle">
							<img src="/claim/images/cpArrowFunction.gif">
						</td>
						<td width="85%" class="menu1">
							<html:link page="/common/logout.jsp" target="_top">
								<font color=000000> <s:text name="pub.logBack" /></font>
								<%--重新登录--%>
							</html:link>
						</td>
					</tr>
				</table>
			</td>
			<td width="3" background="/claim/images/menuVerticalLine.gif">
				<input type="hidden" id="checkedId" name="checkedId" title="122" value="1">
				<input type="hidden" id="checkedName" name="checkedName" value="">
			</td>
		</tr>
		<%
		}
	%>
	</table>
</body>
</html>
