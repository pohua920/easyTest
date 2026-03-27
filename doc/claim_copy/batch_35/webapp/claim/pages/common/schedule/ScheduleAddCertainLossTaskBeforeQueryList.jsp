<%--
****************************************************************************
* DESC       ：新增定损调度查询结果显示页面
* AUTHOR     ：wangli
* CREATEDATE ：2005-04-12
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
<%@ page import="java.util.Calendar"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%
	//得到本周周一与周日的日期
	//Date date = new Date();
	//String strMonday = ""; //date.getMondayOFWeek();
	//String strSunday = ""; //date.getSundayOFWeek();

	String strSunday = DateTime.current().toString();
	String strMonday = new DateTime(DateTime.current().addDay(-4),DateTime.YEAR_TO_DAY).toString();
%>
<html:html locale="true">
<head>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
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
<title><s:text name="schedule.addDamageAdjustQueryShowView" />
	<%--新增定损调度查询结果显示页面 --%></title>
<script src="/claim/common/js/showpage.js"> </script>
<html:base />
<script language="javascript">
  function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
  <%--案件状态标志处理--%>
  <!--
   function submitForm()
    {
      fm.searchFlag.value="true";
	  fm.pageNo.value="1";//查询後页面设为1
      fm.submit();//提交
    }
  //--> 
  </script>
</head>
<body onLoad="initPage();document.onkeydown();">
	<form name="fm" action="/claim/scheduleCheckQuery.do" method="post" onSubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="formtitle" colspan="4">
					<s:text name="schedule.addDamageAdjustQuery" />
					<%--新增定损调度查询 --%>
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="db.prpLregist.registNo" />
					<%--报案号 --%>
					:
				</td>
				<td class='input' style="width: 25%">
					<select class=query name="registNoSign" style="width: 40px">
						<option value="=">=</option>
						<option value="*">*</option>
					</select>
					<input type=text name="registNo" class="input" style="width: 140px">
				</td>
				<td class='title' style="width: 15%">
					<s:text name="db.prpCitem_car.licenseNo" />
					<%--车牌号码 --%>
					:
				</td>
				<td class='input'>
					<select class=query name="prpLscheduleItemLicenseNoSign" style="width: 40px">
						<option value="=">=</option>
						<%--<option value="*">*</option>--%>
					</select>
					<input name="prpLscheduleItemLicenseNo" class="input" style="width: 140px">
				</td>
				<td class='button' style="width: 20%" rowspan=6>&nbsp;</td>
			</tr>
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="manage.startTime" />
					<%--开始时间 --%>
					:
				</td>
				<td class='input'>
					<input name="startDate" class="input" style="width: 120px" value=<%=strMonday%>>
					<img src="/claim/images/bgcalendar.gif" align="middle" style='cursor: hand'
						onClick="TogglePopupCalendarWindow('document.fm.startDate', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class='title' style="width: 15%">
					<s:text name="manage.endTime" />
					<%--结束时间 --%>
					:
				</td>
				<td class='input'>
					<input name="endDate" class="input" style="width: 120px" value=<%=strSunday%>>
					<img src="/claim/images/bgcalendar.gif" align="middle" style='cursor: hand'
						onClick="TogglePopupCalendarWindow('document.fm.endDate', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
			</tr>
			<tr>
				<td class='title' style="width: 15%">
					<s:text name="db.prpLclaim.insuredName" />
					<%--被保险人 --%>
					：
				</td>
				<td class='input' style="width: 25%" colspan=3>
					<select class=query name="InsuredNameSign" style="width: 40px">
						<option value="=">=</option>
						<option value="*">*</option>
					</select>
					<input type=text name="InsuredName" class="input" style="width: 140px" value="">
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="hidden" name="editType" value="ADDQUERY">
			<input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
			<span class="button" style="width: 20%"> <input name="submit1" type=button id="button" class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
			</span>
			<input name="searchFlag" type="hidden" id="searchFlag">
		</div>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan="8" class="formtitle">
					<s:text name="schedule.addDamageAdjustList" />
					<%--新增定损调度任务清单 --%>
				</td>
			</tr>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.status" />
					<%--案件状态 --%>
				</td>
				<td class="centertitle">
					<s:text name="db.prpLrepairFee.registNo" />
					<%--报案号 --%>
				</td>
				<td class="centertitle">
					<s:text name="db.prpLdriver.licenseNo" />
					<%--车牌号 --%>
				</td>
				<td class="centertitle">
					<s:text name="prompt.queRegist.Operator" />
					<%--操作员 --%>
				</td>
				<td class="centertitle">
					<s:text name="claim.intoTime" />
					<%--流入时间 --%>
				</td>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="swfLogDto" property="swfLogList">
				<logic:iterate id="swfLogDto1" name="swfLogDto" property="swfLogList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<td align="center">
						<logic:equal name="swfLogDto1" property="nodeStatus" value='0'>
							<s:text name="common.status.newSchedule" />
							<%--新调度 --%>
						</logic:equal>
						<logic:equal name="swfLogDto1" property="nodeStatus" value='1'>
							<s:text name="common.status.untreated" />
							<%--未处理 --%>
						</logic:equal>
						<logic:equal name="swfLogDto1" property="nodeStatus" value='2'>
							<s:text name="db.prpLregist.damageAddress" />
							<%--正处理 --%>
						</logic:equal>
						<logic:equal name="swfLogDto1" property="nodeStatus" value='3'>
							<s:text name="schedule.returnDeal" />
							<%--回退处理 --%>
						</logic:equal>
						<logic:equal name="swfLogDto1" property="nodeStatus" value='4'>
							<s:text name="common.status.submited" />
							<%--已提交  --%>
						</logic:equal>
						<logic:equal name="swfLogDto1" property="nodeStatus" value='5'>
							<s:text name="schedule.return" />
							<%--已回退 --%>
						</logic:equal>
						<logic:equal name="swfLogDto1" property="nodeStatus" value='6'>
							<s:text name="common.status.revoked" />
							<%--已撤消 --%>
						</logic:equal>
					</td>
					<td align="center">
						<a
							href="/claim/scheduleAddCertainLossTask.do?businessNo=<bean:write name='swfLogDto1' property='keyIn'/>&editType=ADDSHOW&swfLogFlowID=<bean:write name='swfLogDto1' property='flowID'/>&swfLogLogNo=<bean:write name='swfLogDto1' property='logNo'/>&policyNo=<bean:write name='swfLogDto1' property='policyNo'/>&nodeStatus=<bean:write name='swfLogDto1' property='nodeStatus'/>&riskCode=<bean:write name='swfLogDto1' property='riskCode'/>"><bean:write
								name="swfLogDto1" property="businessNo" /></a>
					</td>
					<td align="center">
						<bean:write name="swfLogDto1" property="lossItemName" />
					</td>
					<td align="center">
						<bean:write name="swfLogDto1" property="handlerName" />
					</td>
					<td align="center">
						<bean:write name="swfLogDto1" property="flowInTime" />
					</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="5">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="swfLogDto" property="turnPageDto" />
							<%
								SwfLogDto swfLogDto = (SwfLogDto) request
											.getAttribute("swfLogDto");
									int curPage = swfLogDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="ADDQUERY">
		</tr>
		</table>
	</form>
</body>
</html:html>
