<%--
****************************************************************************
* DESC       ：调度中心设置登记录入/修改页面 
* AUTHOR     ：中科软
* CREATEDATE ：2005-08-30
* MODIFYLIST ：   Name       Date            Reason/Contents

****************************************************************************
--%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.*"%>
<html:html locale="true">
<head>
<!--对title处理-->
<title><s:text name="title.scheduleObject.schedulingInformationSettings" /></title>
<%--调度机构信息设置 --%>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<script language="javascript">
	function saveForm() {
		if (!validateForm(fm)) {
			return false;
		}

		//判断机构代码必须为8位

		var comcode = fm.prpDcompanyComCode.value;

		if (trim(comcode).toString().length != 8) {
			alert("输入的调度机构的代码必须为8位")
			return false;
		}

		fm.submit();

	}
</script>
<%
		//reason:做返回时候的show模式
		String editType = request.getParameter("editType");
		String displayModeReadOnly = ""; //展现模式

		if (editType.equals("SHOW")) {
			displayModeReadOnly = "readonlyAllInput();";
		}
%>
<body class="interface" onload="initPage();<%=displayModeReadOnly%>">
	<form name=fm action="/claim/scheduleObjectEdit.do" method="post" onsubmit="return validateForm(this);">
		<%-- 1.配置主信息 --%>
		<%@include file="/common/scheduleObject/ScheduleObjectMainEdit.jsp"%>
		<%-- 保存通用按钮 --%>
		<%@include file="/common/scheduleObject/ScheduleObjectSave.jsp"%>
		<logic:notEqual name='scheduleObjectDto' property='alertMessage' value="">
			<input type='text' class=readonly readonly style="color: red" value="<bean:write name='scheduleObjectDto' property='alertMessage' />">
		</logic:notEqual>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
