<%--
****************************************************************************
* DESC       ：定损调度查询输入界面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%
	//得到本周周一与周日的日期
	//Date date = new Date();
	//String strMonday = ""; //date.getMondayOFWeek();
	//String strSunday = ""; //date.getSundayOFWeek();

	String strSunday = DateTime.current().toString();
	String strMonday = new DateTime(DateTime.current().addMonth(-1),
			DateTime.YEAR_TO_DAY).toString();
	UserDto user = (UserDto) request.getSession().getAttribute("user");
%>
<script>
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
</script>
<html:html locale="true">
<head>
<title><s:text name="title.certainLossBeforeEdit.titleName" /></title>
<%--查询定损信息 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onLoad="initPage();document.onkeydown();">
	<form name="fm" action="/claim/scheduleCheckQuery.do" method="post" onSubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="title.certainLossBeforeEdit.titleName" />
					<%--查询定损信息 --%>
				</td>
			</tr>
			<tr>
				<td width="12%" class='title' style="width: 10%">
					<s:text name="db.prpLregist.registNo" />
					<%--报案号 --%>
					:
				</td>
				<td width="27%" class='input' style="width: 25%">
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="registNo" class="input" style='width: 70%'>
				</td>
				<td width="12%" class='title' style="width: 10%">
					<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" />
					<%--调度员 --%>
					:
				</td>
				<td width="41%" class='input' style="width: 25%">
					<input type=text name="handlerCode" class="codecode" style="width: 100px" title="分案員" value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
						onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');">
					<input type=text name="handlerName" readonly class="codecode" style="width: 30%" title="分案員" value="" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
						onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
				</td>
				<td width="8%" rowspan=4 class='button' style="width: 20%">&nbsp;</td>
			</tr>
			<tr>
				<td class='title' style="width: 10%">
					<s:text name="schedule.peopleFee" />
					<%--定损人 --%>
					:
				</td>
				<td class='input' style="width: 25%">
					<input type=text name="NhandlerCode" class="codecode" title="定損人" value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');"
						style="width: 30%">
					<input type=text name="NhandlerName" class="codename" title="定損人" value="" style="width: 55%" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
						onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
				</td>
				<td class='title' style="width: 10%">
					<s:text name="db.prpCitem_car.licenseNo" />
					<%--车牌号码 --%>
					:
				</td>
				<td class='input' style="width: 25%">
					<input type=text name="licenseNo" class="input">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="manage.startTime" />
					<%--开始时间 --%>
					:
				</td>
				<td class='input'>
					<input name="startDate" class="input" value=<%=strMonday%>>
				</td>
				<td class='title'>
					<s:text name="manage.endTime" />
					<%--结束时间 --%>
					:
				</td>
				<td class='input'>
					<input name="endDate" class="input" value=<%=strSunday%>>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="schedule.feeProcessingState" />
					<%--定损处理状态 --%>
					:
				</td>
				<td colspan=3 class='input'>
					<input type=checkbox name="checkFlag0">
					<s:text name="common.status.untreated" />
					<%--未处理 --%>
					<input type=checkbox name="checkFlag2">
					<s:text name="common.status.intreating" />
					<%--正处理 --%>
					<input type=checkbox name="checkFlag4">
					<s:text name="common.status.submited" />
				</td>
				<%--已提交 --%>
			</tr>
		</table>
		<div align="center">
			<input type="hidden" name="editType" value="QUERYCERTAINLOSS">
			<input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
			<span class="button" style="width: 20%"> <input name="submit" type=submit id="button" class='button' value="<s:text name='button.query.value' />">
			</span>
		</div>
	</form>
</body>
</html:html>