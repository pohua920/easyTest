<%--
****************************************************************************
* DESC       : 理赔节点查询新案件调度的结果页面(其中包含调度任務分配的算法)
* AUTHOR     : lixiang
* CREATEDATE ：2004-07-21
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%


%>
<html:html locale="true">
<meta http-equiv="Refresh" content="200;url=/claim/scheduleRegistNew.do">
<meta http-equiv="Pragma" content="no-cache">
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
<title><s:text name="title.schedule.schedulingTask" />
	<%--待调度任務 --%></title>
<script src="/claim/common/js/showpage.js"> </script>
<script language='javascript'>
    function cmdOK_onclick()
    {
	document.location.reload();
    }
  
  </script>
<html:base />
</head>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="common" style="width: 200%">
		<tr>
			<td width="184" height="26" valign="bottom">
				<table width="184" height="19" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="161" class="formtitle">
							<!-- 显示需要调度的内容 -->
							<s:text name="schedule.schedulingTaskProcessing" />
							<%--待调度任務处理 --%>
						</td>
					</tr>
				</table>
			</td>
			<td valign="bottom">
				<font color="#666666">&nbsp;
			</td>
			<td align="left">
				<font color="#FF0080">&nbsp;<s:text name="schedule.enterTaskNumber" />
					<%--按下报案登记号，进入调度处理 --%>&nbsp;&nbsp; <input type=button name=bgetScheduleNew class='bigbutton' value="获取新分案任務" style="align: center" onclick="cmdOK_onclick();">
			</td>
		</tr>
	</table>
	<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1" style="width: 250%">
		<tr>
			<td class="centertitle" style="width: 3%">
				<s:text name="db.prpDrate.serialNo" />
				<%--序号 --%>
			</td>
			<td class="centertitle" style="width: 7%">
				<s:text name="schedule.reportRegistrateNo" />
				<%--报案登记号 --%>
			</td>
			<td class="centertitle" style="width: 4%">
				<s:text name="db.prpLclaimStatus.status" />
				<%--案件状态 --%>
			</td>
			<%           
             //reason:显示调度停留时间%>
			<td class="centertitle" style="width: 6%">
				<s:text name="schedule.adjustStayTime" />
				<%--调度停留时间 --%>
			</td>
			<td class="centertitle" style="width: 4%">
				<s:text name="db.prpDcustomer_Unit.customerKind" />
				<%--客户类型 --%>
			</td>
			<td class="centertitle" style="width: 4%">
				<s:text name="db.prpLcheck.checkType" />
				<%--查勘类型 --%>
			</td>
			<td class="centertitle" style="width: 4%">
				<s:text name="schedule.coverTypes" />
				<%--承保类型 --%>
			</td>
			<td class="centertitle" style="width: 12%">
				<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFScheduleAddress" />
				<%--查勘地址 --%>
			</td>
			<td class="centertitle" style="width: 11%">
				<s:text name="schedule.licenseAddress" />
				<%--出险地址 --%>
			</td>
			<td class="centertitle" style="width: 6%">
				<s:text name="schedule.licensePlateNumber" />
				<%--出险车牌号 --%>
			</td>
			<td class="centertitle" style="width: 5%">
				<s:text name="schedule.carOwnerName" />
				<%--车主姓名 --%>
			</td>
			<td class="centertitle" style="width: 6%">
				<s:text name="db.prpLregist.phoneNumber" />
				<%--联系电话 --%>
			</td>
			<td class="centertitle" style="width: 3%">
				<s:text name="schedule.checkedTimes" />
				<%--查勘次数 --%>
			</td>
			<td class="centertitle" style="width: 8%">
				<s:text name="prompt.queRegist.PolicyNo" />
				<%--保单号 --%>
			</td>
			<td class="centertitle" style="width: 6%">
				<s:text name="schedule.underwritingSubbranch" />
				<%--承保支公司 --%>
			</td>
			<td class="centertitle" style="width: 5%">
				<s:text name="schedule.reportWay" />
				<%--报案途径 --%>
			</td>
			<%// 以下为隐藏的 列，为了减少重复取数据库信息的问题%>
		</tr>
		<%int index=0;%>
		<logic:notEmpty name="prpLscheduleNewDto" property="scheduleNewList">
			<logic:iterate id="prpLscheduleNewTaskList" name="prpLscheduleNewDto" property="scheduleNewList">
				<%
          if(index %2== 0)
               out.print("<tr class=listodd>");
          else 
               out.print("<tr class=listeven>");
%>
				<td>
					<%=index+1%>
				</td>
				<%// 报案受理号
               %>
				<td>
					<a
						href="/claim/scheduleDealRegist.do?prpLscheduleMainWFRegistNo=<bean:write name='prpLscheduleNewTaskList' property='registNo'/>&editType=ADD&prpLscheduleMainWFSurveyNo=<bean:write name='prpLscheduleNewTaskList' property='surveyNo'/>">
						<bean:write name="prpLscheduleNewTaskList" property="registNo" />
					</a>
				</td>
				<td>
					<input type=hidden name=prpLscheduleNewscheduleFlag value="<bean:write name='prpLscheduleNewTaskList' property='scheduleFlag'/>">
					<logic:equal name='prpLscheduleNewTaskList' property='scheduleFlag' value="0">
						<s:text name="common.status.newSchedule" />
						<%--新调度 --%>
					</logic:equal>
					<logic:equal name='prpLscheduleNewTaskList' property='scheduleFlag' value="3">
						<s:text name="schedule.returnAndDeal " />
						<%--退回並处理 --%>
					</logic:equal>
				</td>
				<%// 是否被调度使用   %>
				<% 
     //reason:显示调度停留时间          %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="waitTime" />
				</td>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="signcustType" />
				</td>
				<%// 金银牌客户标志   %>
				<td>
					<input type=hidden name=prpLscheduleNewbookFlag value="<bean:write name='prpLscheduleNewTaskList' property='bookFlag'/>">
					<logic:equal name='prpLscheduleNewTaskList' property='bookFlag' value="0">
						<s:text name="schedule.notAppointment" />
						<%--非预约 --%>
					</logic:equal>
					<logic:notEqual name='prpLscheduleNewTaskList' property='bookFlag' value="0">
						<s:text name="schedule.appointment" />
						<%--预约 --%>
					</logic:notEqual>
				</td>
				<%// 预约查勘(定损)   %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="riskCode" />
				</td>
				<%// 承保险类 (DAA)   %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="damageAddress" />
				</td>
				<%// 出险地点         %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="damageAddressType" />
				</td>
				<%// 出险地点分类     %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="lostLicenseNo" />
				</td>
				<%// 出险车牌号码     %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="contactName" />
				</td>
				<%// 车主姓名         %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="phoneNumber" />
				</td>
				<%// 联系电话         %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="surveyNo" />
				</td>
				<%// 已查勘次数       %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="policyNo" />
				</td>
				<%// 保单号           %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="insureComCode" />
				</td>
				<%// 承保支公司       %>
				<td>
					<bean:write name="prpLscheduleNewTaskList" property="reportWay" />
				</td>
				<%// 报案途径         %>
				<!--
        <td ><bean:write name="prpLscheduleNewTaskList" property="allKindCode"/></td>          <%// 险种代码（投保   %>            
        
        <td ><bean:write name="prpLscheduleNewTaskList" property="operatorCode"/></td>        <%// 操作员编码(包含  %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="surveyLicenseNo"/></td>     <%// 查勘车牌号码(用  %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="damageCode"/></td>          <%// 出险原因代码 ▲  %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="damageName"/></td>          <%// 出险原因说明     %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="damageTypeCode"/></td>      <%// 事故类型代码(车  %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="damageTypeName"/></td>      <%// 事故类型说明 ▲  %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="damageAreaCode"/></td>      <%// 出险区域代码 ▲  %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="damageAreaName"/></td>      <%// 出险区域名称     %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="addressCode"/></td>         <%// 地址编码         %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="damageAreaPostCode"/></td>  <%// 出险地点邮政编? %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="accidentDetail"/></td>      <%// 出险情况         %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="surveyType"/></td>          <%// 查勘地点类型（? %>
        
        <td ><bean:write name="prpLscheduleNewTaskList" property="customerName"/></td>        <%// 被保险人(单位)? %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="surveyAddress"/></td>       <%// 查勘地址         %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="surveyDate"/></td>          <%//                  %>
        <td ><bean:write name="prpLscheduleNewTaskList" property="scheduleInfo"/></td>        <%// 调度信息         %> 
        <td ><bean:write name="prpLscheduleNewTaskList" property="flag"/></td>   
                    <%// 标识             %>
       -->
				</tr>
				<%        index++;%>
			</logic:iterate>
		</logic:notEmpty>
		<tr class="listtail">
			<td colspan="16">
				<s:text name="prompt.check.total" />
				<%--共有--%><%= index --%><s:text name="schedule.query1" />
				<%--条待调度任務需要进行处理 --%>
			</td>
		</tr>
	</table>
	</tr>
	<table class="common" cellpadding="4" cellspacing="20">
		<tr>
		</tr>
		<tr>
		</tr>
		<table>
		</table>
</body>
</html:html>