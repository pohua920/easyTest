<%--
****************************************************************************
* DESC       ：调度查勘内容
* AUTHOR     ：
* CREATEDATE ：2004-08-04
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	
%>
<%
	//得到本周周一与周日的日期
	//Date date = new Date();
	//String strMonday = ""; //date.getMondayOFWeek();
	//String strSunday = ""; //date.getSundayOFWeek();

	String strSunday = DateTime.current().toString();
	String strMonday = new DateTime(DateTime.current().addDay(-2),
			DateTime.YEAR_TO_DAY).toString();
%>
<script>
function submitForm()
    {
      fm.searchFlag.value="true";
	  fm.pageNo.value="1";//查询後页面设为1
      fm.submit();//提交
    }
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
</script>
<html:html>
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
<title><s:text name="title.scheduleBeforeEdit.schedulTaskList" />
	<%--调度取回任务清单 --%></title>
<script src="/claim/common/js/showpage.js"> </script>
</script>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<html:base />
</head>
<body onLoad="initPage();document.onkeydown();">
	<form name="fm" action="/claim/scheduleGetBackQuery.do" method="post" onSubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td class='title' style="width: 10%">
					<s:text name="regist.prpLregist.registNo" />:
				</td>
				<%--报案号 --%>
				<td class='input' style="width: 25%">
					<select class=query name="registNoSign" style="width: 40px">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>&nbsp;
					<input type=text name="registNo" class="input" style="width: 75%">
				</td>
				<td class='title' style="width: 10%">
					<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyLicenseNo" />
				</td>
				<%--车牌号码: --%>
				<td class='input' style="width: 25%">
					<select class=query name="licenseNoSign" style="width: 40px">
						<option value="=">=</option>
						<%--<option value="*">*</option>--%>
					</select> &nbsp;
					<input type=text name="licenseNo" class="input" style="width: 140px">
				</td>
				<td class='button' style="width: 20%" rowspan=4>&nbsp;</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" />
				</td>
				<%--调度员: --%>
				<td class='input'>
					<input type=text name="handlerCode" class="codecode" style="width: 100px" title="分案員" value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
						onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');">
					<input type=text name="handlerName" class="codecode" readonly style="width: 30%" title="分案員" value="">
				</td>
				<td class='title'>
					<s:text name="schedule.surveyFees" />:
				</td>
				<%--查勘/定损人 --%>
				<td class='input'>
					<input type=text name="NhandlerCode" class="codecode" style="width: 100px" title="查勘/定損人員" value="" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
						onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');">
					<input type=text name="NhandlerName" class="codecode" readonly style="width: 30%" title="查勘/定損人員" value="">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="manage.startTime" />:
				</td>
				<%--开始时间 --%>
				<td class='input'>
					<input name="startDate" class="input" style="width: 120px" value=<%=strMonday%>>
					<img src="/claim/images/bgcalendar.gif" align="middle" style='cursor: hand'
						onClick="TogglePopupCalendarWindow('document.fm.startDate', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class='title'>
					<s:text name="manage.endTime" />:
				</td>
				<%--结束时间 --%>
				<td class='input'>
					<input name="endDate" class="input" style="width: 120px" value=<%=strSunday%>>
					<img src="/claim/images/bgcalendar.gif" align="middle" style='cursor: hand'
						onClick="TogglePopupCalendarWindow('document.fm.endDate', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 10)%>')">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="schedule.schedulingType1" />:
				</td>
				<%--调度去向类型 --%>
				<td class='input' colspan=3>
					<select name="nodeType" style="width: 150px">
						<option value="check" selected>
							<s:text name="check.mentHereunde" />
						</option>
						<%--查勘 --%>
						<option value="certa">
							<s:text name="schedule.fee" />
						</option>
						<%--车辆定损 --%>
						<option value="wound">
							<s:text name="regist.prpLregist.personLossFlag" />
						</option>
						<%--人伤 --%>
						<option value="propc">
							<s:text name="compensate.dubang.damageProperty" />
						</option>
						<%--财产损失 --%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%--"="符号，必须精确查询。 --%>
					<s:text name="prompt.schedule.query2" />
					<%-- "=*"符号，前匹配後模糊的查询。 --%>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="hidden" name="searchFlag" id="flag">
			<input type="hidden" name="editType" value="GETBACKQUERY">
			<input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
			<span class="button" style="width: 20%"> <input name="submit1" id="button" type=button class='button' value="<s:text name='button.query.value'/>" onClick="submitForm();">
			</span>
		</div>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan="6" class="formtitle">
					<s:text name="schedule.scheduling" />
					<%--调度 --%>
					<bean:write name="swfLogDto" property="nodeName" />
					<s:text name="schedule.taskList" />
				</td>
				<%--任务清单 --%>
			</tr>
			<tr>
				<td class="centertitle" style="width: 6%">
					<s:text name="regist.prpLregist.serialNo" />
				</td>
				<%--序号 --%>
				<td class="centertitle" style="width: 18%">
					<s:text name="schedule.reportRegistrateNo" />
				</td>
				<%--报案登记号 --%>
				<td class="centertitle" style="width: 20%">
					<s:text name="certainLoss.prpLscheduleMainWF.attemperDate" />
				</td>
				<%--调度时间 --%>
				<td class="centertitle" style="width: 10%">
					<s:text name="check.schedulOpera" />
				</td>
				<%--调度操作员 --%>
				<td class="centertitle" style="width: 10%">
					<s:text name="schedule.surveyPersonn" />
				</td>
				<%-->查勘/定损人员 --%>
				<td class="centertitle" style="width: 28%">
					<s:text name="schedule.schedulObjectName" />
				</td>
				<%--调度对象名称 --%>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="swfLogDto" property="swfLogList">
				<logic:iterate id="prpLcheckTaskList" name="swfLogDto" property="swfLogList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<td align="center">
						<%=index + 1%>
					</td>
					<%
						String scheduleType = "sched";//调度类型
					%>
					<td>
						<%
							//GETBACKEDIT 这个变量绝对重要，不可以随便修改，关系到保存
						%>
						<%
							scheduleType = "schel";
						%>
						<logic:equal name="swfLogDto" property="nodeType" value="certa"></logic:equal>
						<a
							href="/claim/schedulegetBackEdit.do?prpLscheduleMainWFRegistNo=<bean:write name='prpLcheckTaskList' property='keyIn'/>&editType=GETBACKEDIT&nodeType=<bean:write name='prpLcheckTaskList' property='nodeType'/>&lossItemCode=<bean:write name='prpLcheckTaskList' property='lossItemCode'/>&scheduleType=<%=scheduleType%>&swfLogFlowID=<bean:write name='prpLcheckTaskList' property='flowID'/>&swfLogLogNo=<bean:write name='prpLcheckTaskList' property='logNo'/>&policyNo=<bean:write name='prpLcheckTaskList' property='policyNo'/>&handleDept=<bean:write name='prpLcheckTaskList' property='handleDept'/>&riskCode=<bean:write name='prpLcheckTaskList' property='riskCode'/>">
							<bean:write name="prpLcheckTaskList" property="keyIn" />
						</a>
					</td>
					<td>
						<bean:write name="prpLcheckTaskList" property="flowInTime" />
					</td>
					<%
						// 是否被调度使用
					%>
					<td>
						<bean:write name="prpLcheckTaskList" property="beforeHandlerName" />
					</td>
					<%
						// 预约查勘(定损)
					%>
					<td>
						<bean:write name="prpLcheckTaskList" property="handlerName" />
					</td>
					<td>
						<bean:write name="prpLcheckTaskList" property="lossItemName" />
					</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		</tr>
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