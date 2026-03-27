<%@page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html>
<head>
<TITLE><s:text name="common.helpDocument"/><%--帮助文档 --%></TITLE>
</head>
<script type="text/javascript">
function open(type){

//document.getElementById(type).display="no";

}
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/yui2/fonts/fonts-min.css" />
<body class="yui-skin-sam" >
<br>
<br>

<table class=common   align="center">
<tr>
	<td style="font-size: 25px;font-family: 仿宋" align="center">
		<s:text name="common.helpDocument"/><%--帮助文档 --%>
	</td>
</tr>
</table>
<table class=common height="80" >
<tr>
	<td style="font-size: 18px;font-family: 仿宋" align="left">
		<s:text name="common.query1"/><%--一、使用环境 --%>
		<br>
		&nbsp;&nbsp;<s:text name="common.query2"/><%--1、Windows系列操作系统。 --%>
		<br>
		&nbsp;&nbsp;<s:text name="common.query3"/><%--2、内存256M以上。 --%>
		<br>
		&nbsp;&nbsp;<s:text name="common.query4"/><%--3、本系统限定使用IE系列浏览器，支持IE6、IE7、IE8版本；使用IE9版本浏览器，请切换到兼容模式。 --%>
	</td>
</tr>
<!--  
<tr>
	<td style="font-size: 18px;font-family: 仿宋" align="left">
		二、故障说明
		<br>
		&nbsp;&nbsp;1、若登录系统後菜单顯示异样，请查看IE版本是否符合要求。
		<br>
		&nbsp;&nbsp;2、若视频不能正常播放，请刷新页面後在IE上方运行activex控件。
	</td>
</tr>
-->
<tr>
	<td style="font-size: 18px;font-family: 仿宋" align="left">
		<s:text name="common.query5"/><%--二、操作说明 --%>
		   <font color="red">&nbsp;<s:text name="common.query6"/><%--（注意：由於操作手册较大，请下载到本地後再打开查阅） --%></font>
	</td>
</tr>
<tr>
	<td style="font-size: 18px;font-family: 仿宋" align="left">
		&nbsp;&nbsp;1、<a href="${ctx}/download/downloadFile?fileName=<%=java.net.URLEncoder.encode("活动量与关键工作管理平台操作手册-部门总.doc", "GBK")%>"><s:text name="common.workInstructionManual"/><%--部门周工作操作手册 --%></a><br>
		&nbsp;&nbsp;2、<a href="${ctx}/download/downloadFile?fileName=<%=java.net.URLEncoder.encode("活动量与关键工作管理平台操作手册-部门级人员.doc", "GBK")%>"><s:text name="common.workInstructionPersonManual"/><%--部门级人员周工作操作手册 --%></a><br>
		&nbsp;&nbsp;3、<a href="${ctx}/download/downloadFile?fileName=<%=java.net.URLEncoder.encode("活动量与关键工作管理平台操作手册-处室负责人.doc", "GBK")%>"><s:text name="common.officeWorkWeekOperationManual"/><%--处室周工作操作手册 --%></a><br>
		&nbsp;&nbsp;4、<a href="${ctx}/download/downloadFile?fileName=<%=java.net.URLEncoder.encode("活动量与关键工作管理平台操作手册-个人.doc", "GBK")%>"><s:text name="common.personalWorkWeekOperationManual"/><%--个人周工作操作手册 --%></a><br>
		<!-- &nbsp;&nbsp;5、<a href="${ctx}/common/活动量与关键工作管理平台操作手册-班子成员周工作及活动量.doc">班子成员周工作及活动量操作手册</a><br> -->
		&nbsp;&nbsp;5、<a href="${ctx}/download/downloadFile?fileName=<%=java.net.URLEncoder.encode("活动量与关键工作管理平台操作手册-周工作审阅.doc", "GBK")%>"><s:text name="common.weekOperationManual"/><%--周工作审阅操作手册 --%></a>
	</td>
</tr>
<!--
<tr>
<td>
1、部门计划
</td>
<td >
<object id="video" width="400" height="200" border="0" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA"> 
<param name="ShowDisplay" value="0"> 
<param name="ShowControls" value="1"> 
<param name="AutoStart" value="false"> 
<param name="AutoRewind" value="0"> 
<param name="PlayCount" value="0"> 
<param name="Appearance value="0 value="""> 
<param name="BorderStyle value="0 value="""> 
<param name="MovieWindowHeight" value="440"> 
<param name="MovieWindowWidth" value="520"> 
<param name="FileName" value=""> 
<param name="AnimationAtStart" value="1">
<param name="AllowScan" value="-1">
<param name="AllowChangeDisplaySize" value="-1">
<param name="AutoRewind" value="0">
<param name="Balance" value="0">

<embed width="500" height="250" border="0" showdisplay="0" showcontrols="1" autostart="1" autorewind="0" playcount="0" moviewindowheight="240" moviewindowwidth="320" filename="" src="${ctx}/pages/platform/user/部门计划.avi"> 
</embed> 
</object> 
</td>
</tr>
  -->
</table>
</body>
</html>

