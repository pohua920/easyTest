<%--
****************************************************************************
* DESC       ：已提交/正在处理任务查询
* AUTHOR     ：理赔组
* CREATEDATE ：2013-03-26
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.schema.model.SwfLog"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
	<head>
		<title><s:text name="title.query.handIn" /> <%--已提交/正在处理任务查询 --%>
		</title>
		<%@include file="/common/meta_js.jsp"%>
		<%@include file="/common/i18njs_base.jsp"%>
		<%-- 页面样式  --%>
		<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
		<%-- 标签页样式 --%>
		<jsp:include page="/behaviors/MpcStyle.jsp" />
		<script type="text/javascript">
			function setSrc(urlName,urlValue) {
				document.getElementById(urlName).src = urlValue;
			}
			//mpc调整
			$(function() {
				initWindowNoBtn();
				$(window).resize(function() {
					initWindowNoBtn();
				});
			})
		</script>
	</head>
	<body class="interface" onload="oMPC.style.visibility='visible';" style="scroll: no;">
		<form name=fm1 method="post">
			<DIV id="mainLayer" class="mainLayerNoBtn">
				<mpc:container ID="oMPC">
					<%
						SwfLog swfLogTreeDto = (SwfLog) request.getAttribute("swfLog");
						List<SwfLog> treeSwfLogList = swfLogTreeDto.getSwfLogList();
						int certiIndex = 0;
						int endcaIndex = 0;
						Set<String> comppSet = new HashSet<String>();//存储计算书
						Set<String> speciSet = new HashSet<String>();//存数预赔信息 防重复显示
						String registNo = "";
						String claimNo = "";
						String policyNo = "";
						String riskCode = "";
						String riskType = "";
						SwfLog swfLogNodeDto = null;
						riskType =  (String)request.getAttribute("riskType");
						for (int i = 0; i < treeSwfLogList.size(); i++) {
							swfLogNodeDto = treeSwfLogList.get(i);
							String nodeName = swfLogNodeDto.getNodeName();
							String nodeType = swfLogNodeDto.getNodeType();
							String typeFlag = swfLogNodeDto.getTypeFlag();
							String flowStr = "&swfLogFlowID=" + swfLogNodeDto.getId().getFlowID() + "&swfLogLogNo=" + swfLogNodeDto.getId().getLogNo() + "&status=" + swfLogNodeDto.getNodeStatus() + "&riskCode=" + swfLogNodeDto.getRiskCode() + "&editType=SHOW" + "&editTypeOther=SHOWTASK" + "&nodeType=" + swfLogNodeDto.getNodeType() + "&businessNo=" + swfLogNodeDto.getBusinessNo() + "&policyNo="
									+ swfLogNodeDto.getPolicyNo() + "&modelNo=" + swfLogNodeDto.getModelNo() + "&nodeNo=" + swfLogNodeDto.getNodeNo()+"&editTypeOther=SHOWTASK";
							String strInfoLink = "";
							if ("regis".equals(nodeType)) {//报案信息
								registNo = swfLogNodeDto.getBusinessNo();
								policyNo = swfLogNodeDto.getPolicyNo();
								riskCode = swfLogNodeDto.getRiskCode();
								strInfoLink = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + swfLogNodeDto.getBusinessNo() + flowStr;
							} else if ("check".equals(nodeType)) {//查勘信息
								strInfoLink = "/claim/check/checkFinishQueryList.do?prpLcheckCheckNo=" + swfLogNodeDto.getKeyIn() + "&accicheckNo=" + swfLogNodeDto.getKeyIn() + "&lossItemCode=" + swfLogNodeDto.getLossItemCode() + "&lossItemName=" + swfLogNodeDto.getLossItemName() + "&insureCarFlag=" + swfLogNodeDto.getInsureCarFlag() + flowStr;
							} else if ("claim".equals(nodeType)) {//立案信息
								claimNo = swfLogNodeDto.getKeyOut();
								strInfoLink = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLogNodeDto.getKeyOut() + flowStr;
							} else if ("endca".equals(nodeType)) {//结案信息
								endcaIndex = endcaIndex + 1;
								if (endcaIndex > 1) {//结案信息只显示一条
									continue;
								}
								strInfoLink = "/claim/endcase/endcaseFinishQueryList.do?prpLendcaseEndcaseNo=" + swfLogNodeDto.getKeyIn() + flowStr;
							} else if ("certi".equals(nodeType)) {//单证信息
								certiIndex = certiIndex + 1;
								if (certiIndex > 1) {//单证信息只显示一条
									continue;
								}
								strInfoLink = "/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo=" + swfLogNodeDto.getKeyIn() + flowStr;
							} else if ("cance".equals(nodeType)) {//注销/拒赔信息
								strInfoLink = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLogNodeDto.getKeyIn() + flowStr;
							} else if ("compp".equals(nodeType)) {//计算书信息
								String compensateNo = swfLogNodeDto.getKeyOut();
								if (comppSet.contains(compensateNo)) {//不显示重复的计算书信息
									continue;
								} else {
									comppSet.add(compensateNo);
								}
								strInfoLink = "/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + swfLogNodeDto.getKeyOut() + flowStr;
							} else if ("speci".equals(nodeType)) {//特殊赔案信息
								String prepayPrepayNo = swfLogNodeDto.getKeyOut();
								if (speciSet.contains(prepayPrepayNo)) {//不显示重复的预赔信息
									continue;
								} else {
									speciSet.add(prepayPrepayNo);
								}
								if (typeFlag.equals("7") || typeFlag.equals("8") || typeFlag.equals("5")) {
									strInfoLink = "/claim/prepayFinishQueryList.do?prpLprepayPrepayNo=" + swfLogNodeDto.getKeyOut() + "&caseType=" + typeFlag + flowStr;
								} else {
									strInfoLink = "/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + swfLogNodeDto.getKeyOut() + "&caseType=" + typeFlag + flowStr;
								}
							}
							if (!swfLogNodeDto.getNodeStatus().equals("0") && !swfLogNodeDto.getNodeStatus().equals("5") && !"veric".equals(nodeType) && !"compe".equals(nodeType)) {
								if ("regis".equals(nodeType)) {
					%>
					<mpc:page ID="tabMain" TABTITLE="<%=nodeName%>" TABTEXT="<%=nodeName%>">
						<CENTER>
							<DIV name="tabMain" class="tabMain">
								<iframe id="<%=nodeType%><%=i%>" name="<%=nodeType%><%=i%>" src="<%=strInfoLink%>" style='Z-INDEX: 1; WIDTH: 100%; HEIGHT: 100%;' marginwidth='0' marginheight='0' hspace='0' vspace='0' frameborder='0' scrolling='AUTO'></iframe>
								<script type="text/javascript">
				                   var iframe_window=window.frames['<%=nodeType%><%=i%>'];
									iframe_window.window.alert = function() {
										return true;
									};
								</script>
							</DIV>
						</CENTER>
					</mpc:page>
					<%
								} else {
									if("check".equals(nodeType) && "E".equals(riskType)){ //意健险查勘为调查 
					%>
					<mpc:page ID="tabMain" TABTITLE="<s:text name='button.Survey.value' />" TABTEXT="<s:text name='button.Survey.value' />" onclick="setSrc('<%=nodeType%><%=i%>','<%=strInfoLink%>');">
					<%
									}else{
					%>
					<mpc:page ID="tabMain" TABTITLE="<%=nodeName%>" TABTEXT="<%=nodeName%>" onclick="setSrc('<%=nodeType%><%=i%>','<%=strInfoLink%>');">
					<%
									}
					%>
						<CENTER>
							<DIV name="tabMain" class="tabMain">
								<iframe id="<%=nodeType%><%=i%>" name="<%=nodeType%><%=i%>" src="" style='Z-INDEX: 1;  WIDTH: 100%; HEIGHT: 100%;' marginwidth='0' marginheight='0' hspace='0' vspace='0' frameborder='0' scrolling='AUTO'> </iframe>
								<script type="text/javascript">
				                    var iframe_window=window.frames['<%=nodeType%><%=i%>'];
									iframe_window.window.alert = function() {
										return true;
									};
								</script>
							</DIV>
						</CENTER>
					</mpc:page>
					<%
								}
							}
						}
					%>
				</mpc:container>
				<table id="btnCommon" class="common" align="left" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center">
							<input type="button" name="message" class="bigbutton" value="<s:text name="button.claimsProcessingRecords.value" />" onclick="openWinSave('<%=registNo%>','<%=policyNo%>','<%=riskCode%>','taskView','<%=claimNo%>');">
							<%--赔案处理记录 --%>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" name=buttonClose class='button' value="<s:text name="button.close.value" />" onclick=window.close();;>
							<%--关 闭 --%>
						</td>
					</tr>
				</table>
			</DIV>
		</form>
	</body>
</html>