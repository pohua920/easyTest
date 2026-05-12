<%--
****************************************************************************
* DESC       ：调度定损查询内容(schel)
* AUTHOR     ：
* CREATEDATE ：2004-08-04
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<html:html locale="true">
<head>
<app:css />
<STYLE>
BODY {
	SCROLLBAR-FACE-COLOR: #EFFAFF;
	SCROLLBAR-HIGHLIGHT-COLOR: #4D9AC4;
	SCROLLBAR-SHADOW-COLOR: #4D9AC4;
	SCROLLBAR-3DLIGHT-COLOR: #EFFAFF;
	SCROLLBAR-ARROW-COLOR: #EFFAFF;
	SCROLLBAR-TRACK-COLOR: #EFFAFF;
	SCROLLBAR-DARKSHADOW-COLOR: #EFFAFF;
}
</STYLE>
<title><s:text name="title.scheduleBeforeEdit.schedulingTaskList" />
	<%--调度任务清单 --%></title>
<script src="/claim/common/js/showpage.js">
	
</script>
</script>
<html:base />
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form name="fm">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common" style="width: 100%">
			<tr>
				<td width="184" height="26" valign="bottom">
					<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="161" class="formtitle">
								<!-- 显示需要调度的内容 -->
								<s:text name="schedule.dispatchFeeTask" />
								<%--调度定损任务清单 --%>
							</td>
						</tr>
					</table>
				</td>
				<td valign="bottom">
					<font color="#666666">&nbsp;
				</td>
				</td>
			</tr>
		</table>
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1" style="width: 100%">
			<tr>
				<td class="centertitle" style="width: 10%">
					<s:text name="regist.prpLregist.status" />
				</td>
				<%--状态 --%>
				<td class="centertitle" style="width: 15%">
					<s:text name="prpLregist.registNo" />
				</td>
				<%--报案号 --%>
				<td class="centertitle" style="width: 10%">
					<s:text name="schedule.schedulingMark" />
				</td>
				<%--调度标的 --%>
				<td class="centertitle" style="width: 10%">
					<s:text name="check.schedulInfo" />
				</td>
				<%--调度信息 --%>
				<td class="centertitle" style="width: 15%">
					<s:text name="certainLoss.prpLscheduleMainWF.attemperDate" />
				</td>
				<%--调度时间 --%>
				<td class="centertitle" style="width: 10%">
					<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" />
				</td>
				<%--调度员 --%>
				<td class="centertitle" style="width: 10%">
					<s:text name="schedule.partFee" />
				</td>
				<%--定损员 --%>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLscheduleItemDto" property="scheduleItemList">
				<logic:iterate id="prpLcheckTaskList" name="prpLscheduleItemDto" property="scheduleItemList">
					<%
						if (index % 2 == 0)
							out.print("<tr class=listodd>");
						else
							out.print("<tr class=listeven>");
					%>
					<td align="center">
						<logic:equal name="prpLcheckTaskList" property="surveyTimes" value='0'>
							<s:text name="common.status.newSchedule" />
							<%--新调度 --%>
						</logic:equal>
						<logic:equal name="prpLcheckTaskList" property="surveyTimes" value='1'>
							<s:text name="common.status.submited" />
							<%--已提交 --%>
						</logic:equal>
					</td>
					<td>
						<a href="/claim/scheduleFinishQueryList.do?prpLscheduleMainWFRegistNo=<bean:write name='prpLcheckTaskList' property='registNo'/>&editType=SHOW&prpLscheduleMainWFScheduleID=1&scheduleType=schel">
							<bean:write name="prpLcheckTaskList" property="registNo" />
						</a>
					</td>
					<td>
						<bean:write name="prpLcheckTaskList" property="licenseNo" />
					</td>
					<td>
						<bean:write name="prpLcheckTaskList" property="resultInfo" />
					</td>
					<%
						// 金银牌客户标志
					%>
					<td>
						<bean:write name="prpLcheckTaskList" property="inputDate" />
					</td>
					<%
						// 是否被调度使用
					%>
					<td>
						<bean:write name="prpLcheckTaskList" property="operatorName" />
					</td>
					<td>
						<bean:write name="prpLcheckTaskList" property="nextHandlerName" />
					</td>
					<%
						// 承保险类 (DAA)
					%>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		</tr>
		<input type="hidden" name="editType" value="QUERY">
		<input type="hidden" name="scheduleType" value="schel">
		<table class="common" cellpadding="4" cellspacing="20">
			<tr>
			</tr>
			<tr>
			</tr>
			<table>
			</table>
			</form>
</body>
</html:html>