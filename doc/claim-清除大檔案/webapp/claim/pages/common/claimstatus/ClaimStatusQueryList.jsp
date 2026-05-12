<%--
****************************************************************************
* DESC       : 理赔节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-05-28
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
<title>
	<!-- 根据节点的类型显示标题 --> <!-- 预赔节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="prepa">
		<s:text name="title.prepayBeforeEdit.titleName" />
	</logic:equal> <!-- 报案节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="regis">
		<s:text name="title.registBeforeEdit.titleName" />
	</logic:equal> <!-- 实赔节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="compe">
		<s:text name="title.compensateBeforeEdit.titleName" />
	</logic:equal> <!-- 立案节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="claim">
		<s:text name="title.claimBeforeEdit.titleName" />
	</logic:equal> <!-- 调度节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="sched">
		<s:text name="title.scheduleBeforeEdit.titleName" />
	</logic:equal> <!-- 查勘节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="check">
		<s:text name="title.checkBeforeEdit.titleName" />
	</logic:equal> <!-- 结案节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="endca">
		<s:text name="title.endcaseBeforeEdit.titleName" />
	</logic:equal> <!-- 单证收集节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="certi">
		<s:text name="title.certifyBeforeEdit.titleName" />
	</logic:equal> <!-- 定损节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="certa">
		<s:text name="title.certainLossBeforeEdit.titleName" />
	</logic:equal> <!-- 核损节点 --> <logic:equal name="prpLclaimStatusDto" property="nodeType" value="verif">
		<s:text name="title.verifyLossBeforeEdit.titleName" />
	</logic:equal>
</title>
<script src="/claim/common/js/showpage.js">
	
</script>
<html:base />
</head>
<%
	int colCount = 4; //表示一共4列
		//如果是调度的时候就是5列
		PrpLclaimStatusDto prpLclaimStatusDto = (PrpLclaimStatusDto) request.getAttribute("prpLclaimStatusDto");
		PrpLclaimStatusDto prpLclaimStatusDtoTemp = null;
		String registNoTemp = "";
%>
<body>
	<table class="common" cellpadding="4" cellspacing="1">
		<tr>
			<td colspan=4 class="formtitle">
				<!-- 根据节点的类型显示内容 -->
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="regis">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedReportInfo" />
						<%--未处理报案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.intreatingReportInfo" />
						<%--正处理报案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedReportInfo" />
						<%--已处理报案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedReportInfo" />
						<%--已提交报案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedReportInfo" />
						<%--已撤消报案信息--%>
					</logic:equal>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="claim">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedRecordInfo" />
						<%--未处理立案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.intreatingRecordInfo" />
						<%--正处理立案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedRecordInfo" />
						<%--已处理立案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedRecordInfo" />
						<%--已提交立案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedRecordInfo" />
						<%--已撤消立案信息--%>
					</logic:equal>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="prepa">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedAdvanceInfo" />
						<%--未处理预赔信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.intreatingAdvanceInfo" />
						<%--正处理预赔信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedAdvanceInfo" />
						<%--已处理预赔信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedAdvanceInfo" />
						<%--已提交预赔信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedAdvanceInfo" />
						<%--已撤消预赔信息--%>
					</logic:equal>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="compe">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedRealLossInfo" />
						<%--未处理实赔信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.intreatingRealLossInfo" />
						<%--正处理实赔信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedRealLossInfo" />
						<%--已处理实赔信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedRealLossInfo" />
						<%--已提交实赔信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedRealLossInfo" />
						<%--已撤消实赔信息--%>
					</logic:equal>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="sched">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedSchedulingInfo" />
						<%--未处理调度信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.intreatingSchedulingInfo" />
						<%--正处理调度信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedSchedulingInfo" />
						<%--已处理调度信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedSchedulingInfo" />
						<%--已提交调度信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedSchedulingInfo" />
						<%--已撤消调度信息--%>
					</logic:equal>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="check">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedMentionedInfo" />
						<%--未处理查勘信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.intreatingMentionedInfo" />
						<%--正处理查勘信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedMentionedInfo" />
						<%--已处理查勘信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedMentionedInfo" />
						<%--已提交查勘信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedMentionedInfo" />
						<%--已撤消查勘信息--%>
					</logic:equal>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="endca">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedCaseInfo" />
						<%--未处理结案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.intreatingCaseInfo" />
						<%--正处理结案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedCaseInfo" />
						<%--已处理结案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedCaseInfo" />
						<%--已提交结案信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedCaseInfo" />
						<%--已撤消结案信息--%>
					</logic:equal>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="certi">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedCollectionInfo" />
						<%--未处理单证收集信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.treatedCollectionInfo1" />
						<%--正处理单证收集信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedCollectionInfo2" />
						<%--已处理单证收集信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedCollectionInfo" />
						<%--已提交单证收集信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedCollectionInfo" />
						<%--已撤消单证收集信息--%>
					</logic:equal>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="certa">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedFeeInfo" />
						<%--未处理定损信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.treatedFeeInfo1" />
						<%--正处理定损信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedFeeInfo2" />
						<%--已处理定损信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedFeeInfo" />
						<%--已提交定损信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedFeeInfo" />
						<%--已撤消定损信息--%>
					</logic:equal>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="verif">
					<logic:equal name="prpLclaimStatusDto" property="status" value="1">
						<s:text name="common.status.untreatedDamageInfo" />
						<%--未处理核损信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="2">
						<s:text name="common.status.treatedDamageInfo1" />
						<%--正处理核损信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="3">
						<s:text name="common.status.treatedDamageInfo2" />
						<%--已处理核损信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="4">
						<s:text name="common.status.submitedDamageInfo" />
						<%--已提交核损信息--%>
					</logic:equal>
					<logic:equal name="prpLclaimStatusDto" property="status" value="5">
						<s:text name="common.status.revokedDamageInfo" />
						<%--已撤消核损信息--%>
					</logic:equal>
				</logic:equal>
			</td>
		</tr>
		<tr>
			<td class="centertitle">
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="prepa">
					<s:text name="db.prpLprepay.preCompensateNo" />
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="regis">
					<s:text name="db.prpLclaimApprov.registNo" />
					<%--报案号--%>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="compe">
					<s:text name="certainLoss.realCostNo" />
					<%--实赔号--%>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="claim">
					<s:text name="certainLoss.claims" />
					<%--赔案号--%>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="check">
					<s:text name="db.prpLclaimApprov.registNo" />
					<%--报案号--%>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="endca">
					<s:text name="claim.fileNumber" />
					<%--归档号--%>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="certi">
					<s:text name="db.prpLclaimApprov.registNo" />
					<%--报案号--%>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="certa">
					<s:text name="db.prpLclaimApprov.registNo" />
					<%--报案号--%>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="verif">
					<s:text name="db.prpLclaimApprov.registNo" />
					<%--报案号--%>
				</logic:equal>
				<logic:equal name="prpLclaimStatusDto" property="nodeType" value="sched">
					<s:text name="db.prpLclaimApprov.registNo" />
					<%--报案号--%>
				</logic:equal>
			</td>
			<logic:equal name="prpLclaimStatusDto" property="nodeType" value="sched">
				<td class="centertitle">
					<s:text name="check.scheduleNo" />
				</td>
				<%--调度号--%>
			</logic:equal>
			<td class="centertitle">
				<s:text name="db.prpLregist.policyNo" />
			</td>
			<logic:equal name="prpLclaimStatusDto" property="nodeType" value="certa">
				<td class="centertitle">
					<s:text name="schedule.typeFee" />
				</td>
				<%--定损类型--%>
			</logic:equal>
			<logic:equal name="prpLclaimStatusDto" property="nodeType" value="verif">
				<td class="centertitle">
					<s:text name="schedule.damageType" />
				</td>
				<%--核损类型--%>
			</logic:equal>
			<td class="centertitle">
				<s:text name="db.prpLregist.operatorCode" />
			</td>
			<td class="centertitle">
				<s:text name="db.prpLregist.inputDate" />
			</td>
		</tr>
		<%
			int index = 0;
		%>
		<%
			if (prpLclaimStatusDto.getClaimList() != null) {
					ArrayList list = (ArrayList) prpLclaimStatusDto.getClaimList();
					for (int i = 0; i < list.size(); i++) {
						prpLclaimStatusDtoTemp = (PrpLclaimStatusDto) list.get(i);
		%>
		<%
			if (index % 2 == 0)
							out.print("<tr class=listodd>");
						else
							out.print("<tr class=listeven>");
		%>
		<td align="center">
			<%
				if (prpLclaimStatusDto.getNodeType().equals("prepa")) {
			%>
			<a
				href="/claim/prepayFinishQueryList.do?prpLprepayPrepayNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				}
			%>
			<%
				if (prpLclaimStatusDto.getNodeType().equals("regis")) {
			%>
			<a
				href="/claim/registFinishQueryList.do?prpLregistRegistNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				}
			%>
			<%
				if (prpLclaimStatusDto.getNodeType().equals("compe")) {
			%>
			<a
				href="/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				}
			%>
			<%
				if (prpLclaimStatusDto.getNodeType().equals("claim")) {
			%>
			<a
				href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				}
			%>
			<%
				if (prpLclaimStatusDto.getNodeType().equals("check")) {
			%>
			<a
				href="/claim/checkFinishQueryList.do?prpLcheckCheckNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				}
			%>
			<%
				if (prpLclaimStatusDto.getNodeType().equals("endca")) {
			%>
			<a
				href="/claim/endcaseFinishQueryList.do?prpLendcaseEndcaseNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				}
			%>
			<%
				if (prpLclaimStatusDto.getNodeType().equals("certi")) {
			%>
			<a
				href="/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				}
			%>
			<%
				if (prpLclaimStatusDto.getNodeType().equals("certa")) {
								if (!registNoTemp.equals(prpLclaimStatusDtoTemp.getBusinessNo())) {
			%>
			<a href="/claim/certainLossBeforeEdit.do?RegistNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=SelectLossType"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				} else {
			%>
			&nbsp;
			<%
				}
							}
			%>
			<%
				if (prpLclaimStatusDto.getNodeType().equals("verif")) {
			%>
			<a
				href="/claim/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>&lossTypeFlag=<%=prpLclaimStatusDtoTemp.getTypeFlag()%>"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				}
			%>
			<%
				if (prpLclaimStatusDto.getNodeType().equals("sched")) {
			%>
			<a
				href="/claim/scheduleFinishQueryList.do?prpLscheduleMainWFRegistNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>&prpLscheduleMainWFScheduleID=<%=prpLclaimStatusDtoTemp.getSerialNo()%>"><%=prpLclaimStatusDtoTemp.getBusinessNo()%></a>
			<%
				}
			%>
		</td>
		<%
			if (prpLclaimStatusDto.getNodeType().equals("sched")) {
		%>
		<td align="center"><%=prpLclaimStatusDtoTemp.getSerialNo()%></td>
		<%
			}
		%>
		<td align="center"><%=prpLclaimStatusDtoTemp.getPolicyNo()%></td>
		<%
			if (prpLclaimStatusDto.getNodeType().equals("certa")) {
							if (prpLclaimStatusDtoTemp.getStatus().equals("5")) {
		%>
		<td align="center">
			<a
				href="/claim/certainLossFinishQueryList.do?prpLverifyLossRegistNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>&lossTypeFlag=<%=prpLclaimStatusDtoTemp.getTypeFlag()%>&flag=1"><%=prpLclaimStatusDtoTemp.getTypeFlagName()%></a>
		</td>
		<%
			} else {
		%>
		<td align="center">
			<a
				href="/claim/certainLossFinishQueryList.do?prpLverifyLossRegistNo=<%=prpLclaimStatusDtoTemp.getBusinessNo()%>&editType=<%=prpLclaimStatusDto.getEditType()%>&riskCode=<%=prpLclaimStatusDtoTemp.getRiskCode()%>&lossTypeFlag=<%=prpLclaimStatusDtoTemp.getTypeFlag()%>"><%=prpLclaimStatusDtoTemp.getTypeFlagName()%></a>
		</td>
		<%
			}
						}
		%>
		<%
			if (prpLclaimStatusDto.getNodeType().equals("verif")) {
		%>
		<td align="center"><%=prpLclaimStatusDtoTemp.getTypeFlagName()%></td>
		<%
			}
		%>
		<td align="center"><%=prpLclaimStatusDtoTemp.getHandlerCode()%></td>
		<td align="center"><%=prpLclaimStatusDtoTemp.getInputDate()%></td>
		</tr>
		<%
			index++;
						registNoTemp = prpLclaimStatusDtoTemp.getBusinessNo();
					}
				}
		%>
		<tr class="listtail">
			<logic:equal name="prpLclaimStatusDto" property="nodeType" value="sched">
				<%
					colCount = 5;
				%>
			</logic:equal>
			<td colspan=<%=colCount + 1%>>
				<s:text name="certainLoss.totalInquiries" />
				<%--共查询出--%><%=index%><s:text name="certainLoss.meetRecord" />
				<%--条满足条件的记录--%>
			</td>
		</tr>
	</table>
	</tr>
	</table>
</body>
</html:html>