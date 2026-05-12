<%--
****************************************************************************
* DESC       ：查勘/定损查询结果显示页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.domain.SwfLogDto"%>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<script src="/claim/common/js/showpage.js"> </script>
<html:base />
<script language="javascript">
  <%--案件状态标志处理--%>
<!--
	function submitForm() {
		fm.submit();//提交
	}
//-->
</script>
</head>
<body>
	<%
		SwfLogDto swfLogDto = null;
			SwfLogDto swfLogDtoTemp1 = (SwfLogDto) request.getAttribute("swfLogDto");
			String typeFlag = "2"; //默认定损
			String dealHref = ""; //处理功能，按钮上的联接
			String flowStr = ""; //用来传递flowid和logNo的後半字串後来又加入riskCode和editType,因为每条都传递的
	%>
	<logic:equal name="swfLogDto" property="nodeType" value="check">
		<%
			typeFlag = "1";
		%>
	</logic:equal>
	<table class="common" cellpadding="5" cellspacing="1">
		<tr>
			<td colspan=7 class="formtitle">
				<bean:write name="swfLogDto" property="nodeName" />
				<s:text name="schedule.taskList" />
				<!-- 任务清单 -->
			</td>
		</tr>
		<tr>
			<td class="centertitle">
				<s:text name="db.prpDrate.serialNo" />
			</td>
			<!-- 序号 -->
			<td class="centertitle">
				<s:text name="db.prpLclaim.registNo" />
			</td>
			<!-- 备案号码 -->
			<td class="centertitle">
				<s:text name="db.prpLclaim.policyNo" />
			</td>
			<!-- 保单号码 -->
			<td class="centertitle">
				<bean:write name="swfLogDto" property="nodeName" />
				<s:text name="compensate.underly" />
			</td>
			<!-- 标的 -->
			<td class="centertitle">
				<bean:write name="swfLogDto" property="nodeName" />
				<s:text name="backVisit.member" />
			</td>
			<!-- 员 -->
			<td class="centertitle">
				<s:text name="db.prpLclaimStatus.operatedate" />
			</td>
			<!-- 操作时间 -->
			<td class="centertitle">
				<s:text name="backVisit.operateBackVisit" />
			</td>
			<!-- 操作(回访) -->
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
				<tr class=common>
					<td align="center"><%=index + 1%></td>
					<%
						swfLogDto = (SwfLogDto) ((ArrayList) swfLogDtoTemp1.getSwfLogList()).get(index);
					%>
					<td align="center">
						<a href="/claim/registFinishQueryList.do?prpLregistRegistNo=<bean:write name='swfLogDto1' property='keyIn'/>&editType=SHOW&riskCode=<bean:write name="swfLogDto1" property="riskCode"/>"><bean:write
								name="swfLogDto1" property="keyIn" /></a>
					</td>
					<td align="center">
						<bean:write name="swfLogDto1" property="policyNo" />
					</td>
					<td align="center">
						<bean:write name="swfLogDto1" property="lossItemName" />
					</td>
					<td align="center">
						<bean:write name="swfLogDto1" property="handlerName" />
					</td>
					<td align="center">
						<bean:write name="swfLogDto1" property="handleTime" />
					</td>
					<td align="center">
						<%
							flowStr = "&swfLogFlowID=" + swfLogDto.getFlowID() + "&swfLogLogNo=" + swfLogDto.getLogNo() + "&status=" + swfLogDto.getNodeStatus() + "&riskCode=" + swfLogDto.getRiskCode() + "&editType=ADD" + "&nodeType=" + swfLogDto.getNodeType() + "&businessNo=" + swfLogDto.getBusinessNo() + "&keyIn=" + swfLogDto.getKeyIn() + "&policyNo=" + swfLogDto.getPolicyNo() + "&modelNo="
												+ swfLogDto.getModelNo() + "&nodeNo=" + swfLogDto.getNodeNo();

										dealHref = "/claim/backVisitBeforeEdit.do?RegistNo=" + swfLogDto.getKeyIn() + "&lossTypeFlag=" + typeFlag + "&insureCarFlag=" + swfLogDto.getInsureCarFlag() + "&lossItemCode=" + swfLogDto.getLossItemCode() + "&lossItemName=" + swfLogDto.getLossItemName() + "&newNode=true" + flowStr;
						%>
						<A href="<%=dealHref%>"><s:text name="backVisit.backVisit" /></A>
						<!-- 回访 -->
					</td>
				</tr>
				<%
					index++;
				%>
			</logic:iterate>
		</logic:notEmpty>
		<tr class="listtail">
			<td colspan="7">
				<s:text name="certainLoss.totalInquiries" />
				<!-- 共查询出 --><%=index%><s:text name="certainLoss.meetRecord" />
				<!-- 条满足条件的记录 -->
			</td>
		</tr>
	</table>
	</tr>
	</table>
</body>
</html:html>