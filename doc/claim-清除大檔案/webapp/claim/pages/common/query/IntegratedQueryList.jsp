<%--
****************************************************************************
* DESC       ：综合查询结果列表页面
* AUTHOR     ：刘伟
* CREATEDATE ：2011-10-30
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@page import="java.util.List"%>
<%@page import="com.sinosoft.claim.dto.domain.SwfLogDto"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLcompensateDto"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import = "com.sinosoft.sysframework.common.datatype.DateTime"%>
<%
	UserDto userDto = (UserDto) session.getAttribute("user");
	String comCode = userDto.getComCode();
	String comCName = userDto.getComName();
%>
<html locale="true">
<head>
    <app:css />
	<title>综合查询结果列表页</title>
	<script src="/claim/common/js/showpage.js"></script>
	<jsp:include page="/common/pub/StaticJavascript.jsp" />
	<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
	<script language="javascript">
	function submitForm() {
		if (fm.claimNodeCode.value=="") {
			alert("请选择赔案类型！");
			return false;
		}
		fm.condition.value="";
		fm.pageNo.value="1";
		fm.submit();
	}
	</script>
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/integratedQuery.do?editType=query" method="post" onsubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">综合查询信息</td>
			</tr>
			<tr>
				<td class='title'>归属机构：</td>
				<td class='input'>
					<input type="hidden" name="comCode" value="<%=comCode%>">
					<input type=text name="comCName" class="codename" title="出單單位" value="<%=comCName%>" style="width: 55%" ondblclick="code_CodeSelect(this, 'prpdcompany2','-1,0','Y','N','<%=comCode%>');"
						onchange="code_CodeSelect(this, 'prpdcompany2','-1,0','Y','N','<%=comCode%>');" onkeyup="code_CodeSelect(this, 'prpdcompany2','-1,0','Y','N','<%=comCode%>');">
				</td>
				<td class='title'>操作人员：</td>
				<td class='input'>
					<select name="userNameSign" class=tag>
						<option value="=">=</option>
					</select>
					<input type=text name="userName" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>赔案类型：</td>
				<td class='input'>
					<input type="hidden" name="claimNodeCode">
					<input type=text name="claimNodeName" class="codename" title="賠案類型" value="" style="width: 55%" ondblclick="code_CodeSelect(this, 'ClaimNode','-1,0','Y','N');"
						onchange="code_CodeSelect(this, 'ClaimNode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'ClaimNode','-1,0','Y','N');">
				</td>
				<td class='title'>处理时间：</td>
				<td class='input'>
					<input type=text style="width: 85" name="statStartDate" class="query" readonly="readonly" value="<%=new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY)%>">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle"
						onClick="TogglePopupCalendarWindow('document.fm.statStartDate', '<%=(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					到
					<input type=text style="width: 85" name="statEndDate" class="query" readonly="readonly" value="<%=new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY)%>">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle"
						onClick="TogglePopupCalendarWindow('document.fm.statEndDate', '<%=(new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() + 2%>')">
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<bean:message key='button.query.value' />" onClick="submitForm();">
				</td>
			</tr>
		</table>
		<table bgcolor="#2D8EE1" class="common" cellpadding="4" cellspacing="1">
			<tr>
				<td class="centertitle" width="5%">序号</td>
				<td class="centertitle" width="20%">业务号</td>
				<td class="centertitle" width="20%">保单号</td>
				<td class="centertitle" width="9%">险种名称</td>
				<td class="centertitle" width="20%">被保险人</td>
				<td class="centertitle" width="10%">处理人员</td>
				<td class="centertitle" width="8%">赔案类型</td>
				<td class="centertitle" width="8%">赔案状态</td>
			</tr>
			<%
				String claimNodeCode = (String) request.getAttribute("claimNodeCode");
				if ("recover".equals(claimNodeCode)) {//追偿
					PrpLcompensateDto prpLcompensateDto = (PrpLcompensateDto) request.getAttribute("prpLcompensateDto");
					List<PrpLcompensateDto> prpLcompensateList = (ArrayList<PrpLcompensateDto>) prpLcompensateDto.getPrpLcompensateList();
					int index = 1;
					for (Iterator<PrpLcompensateDto> iterator = prpLcompensateList.iterator(); iterator.hasNext();) {
						PrpLcompensateDto prpLcompensateDto2 = iterator.next();
						String strInfoLink = "/claim/replevyFinishQueryList.do?editType=SHOW&compensateNo=" + prpLcompensateDto2.getCompensateNo();
			%>
			<tr class=listodd>
				<td align="center"><%=index%></td>
				<td align="center">
					<a href="<%=strInfoLink%>"><%=prpLcompensateDto2.getCompensateNo()%></a>
				</td>
				<td align="center"><%=prpLcompensateDto2.getPolicyNo()%></td>
				<td align="center"><%=prpLcompensateDto2.getRiskCodeName()%></td>
				<td align="center"><%=prpLcompensateDto2.getInsuredName()%></td>
				<td align="center">
					<%
						if ("1".equals(prpLcompensateDto2.getUnderWriteFlag())) {
					%>
					<%=prpLcompensateDto2.getUnderWriteName()%>
					<%
						} else {
					%>
					<%=prpLcompensateDto2.getOperatorCode()%>
					<%
						}
					%>
				</td>
				<td align="center">追偿</td>
				<td align="center">
					<%
						if ("0".equals(prpLcompensateDto2.getUnderWriteFlag())) {
					%>
					待审核
					<%
						} else if ("1".equals(prpLcompensateDto2.getUnderWriteFlag())) {
					%>
					审核通过
					<%
						} else if ("3".equals(prpLcompensateDto2.getUnderWriteFlag())) {
					%>
					追偿修改
					<%
						}
					%>
				</td>
			</tr>
			<%
				index++;
					}
			%>
			<tr class="listtail">
				<td colspan="8">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLcompensateDto" property="turnPageDto" />
							<%
								int curPage = prpLcompensateDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
			<%
				} else {
					SwfLogDto swfLogTreeDto = (SwfLogDto) request.getAttribute("swfLogDto");
					List<SwfLogDto> swfLogList = (ArrayList<SwfLogDto>) swfLogTreeDto.getSwfLogList();
					int index = 1;
					for (Iterator<SwfLogDto> iterator = swfLogList.iterator(); iterator.hasNext();) {
						SwfLogDto swfLogDto = iterator.next();
						String nodeType = swfLogDto.getNodeType();
						String status = swfLogDto.getNodeStatus();
						String typeFlag = swfLogDto.getTypeFlag();
						String flowStr = "&swfLogFlowID=" + swfLogDto.getFlowID() + "&swfLogLogNo=" + swfLogDto.getLogNo() + "&status=" + swfLogDto.getNodeStatus() + "&riskCode=" + swfLogDto.getRiskCode() + "&editType=SHOW" + "&nodeType=" + swfLogDto.getNodeType() + "&businessNo=" + swfLogDto.getBusinessNo() + "&policyNo=" + swfLogDto.getPolicyNo() + "&modelNo=" + swfLogDto.getModelNo() + "&nodeNo="
								+ swfLogDto.getNodeNo();
						String strInfoLink = "";
						if ("regis".equals(nodeType)) {//报案信息
							strInfoLink = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + swfLogDto.getBusinessNo() + "&editType=SHOW&riskCode=" + swfLogDto.getRiskCode();
						}

						if ("sched".equals(nodeType)) {//调度信息
							strInfoLink = "/claim/scheduleFinishQueryList.do?prpLscheduleMainWFRegistNo=" + swfLogDto.getRegistNo() + "&editType=SHOW&prpLscheduleMainWFScheduleID=1&scheduleType=schel";
						}

						if ("check".equals(nodeType)) {//查勘信息
							String accicheckNo = "";
							//if ("27".equals(swfLogDto.getRiskCode().substring(0, 2))) {
								//accicheckNo = swfLogDto.getKeyIn();
							//}
							strInfoLink = "/claim/checkFinishQueryList.do?prpLcheckCheckNo=" + swfLogDto.getRegistNo() + "&editType=SHOW&riskCode=" + swfLogDto.getRiskCode() + "&checkNo=" + swfLogDto.getRegistNo() + "&accicheckNo=" + accicheckNo;
						}

						if ("claim".equals(nodeType)) {//立案信息
							strInfoLink = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLogDto.getKeyOut() + "&editType=SHOW&riskCode=" + swfLogDto.getRiskCode();
						}

						if ("certa".equals(nodeType)) {//定损信息
							strInfoLink = "/claim/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogDto.getRegistNo() + "&editType=SHOW&riskCode=" + swfLogDto.getRiskCode() + "&lossItemCode=" + swfLogDto.getLossItemCode() + "&lossItemName=" + swfLogDto.getLossItemName();
						}

						if ("wound".equals(nodeType)) {//人伤定损信息
							strInfoLink = "/claim/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogDto.getRegistNo() + "&editType=SHOW&riskCode=" + swfLogDto.getRiskCode() + "&lossItemCode=" + swfLogDto.getLossItemCode() + "&lossItemName=" + swfLogDto.getLossItemName();
						}

						if ("propc".equals(nodeType)) {//财产定损信息
							strInfoLink = "/claim/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogDto.getRegistNo() + "&editType=SHOW&riskCode=" + swfLogDto.getRiskCode() + "&lossItemCode=" + swfLogDto.getLossItemCode() + "&lossItemName=" + swfLogDto.getLossItemName();
						}

						if ("verif".equals(nodeType)) {//核损信息
							strInfoLink = "/claim/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLogDto.getRegistNo() + "&editType=SHOW&riskCode=" + swfLogDto.getRiskCode() + "&lossItemCode=" + swfLogDto.getLossItemCode() + "&nodeType=verif";
						}

						if ("certi".equals(nodeType)) {//单证信息
							strInfoLink = "/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo=" + swfLogDto.getKeyIn() + "&nodeType=certi&editType=SHOW&riskCode=" + swfLogDto.getRiskCode();
						}

						if ("compp".equals(nodeType)) {//计算书信息
							strInfoLink = "/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + swfLogDto.getKeyOut() + "&editType=SHOW&riskCode=" + swfLogDto.getRiskCode();
						}

						if ("veric".equals(nodeType)) {//核赔信息
							strInfoLink = "/claim/CommonCheckTask.do?iFlowID=" + swfLogDto.getiFlowID() + "&iLogNo=" + swfLogDto.getiLogNo() + "&EditType=query&HandType=22&iRiskCode=" + swfLogDto.getRiskCode() + "&BusinessNo=" + swfLogDto.getBusinessNo() + "&iBusinessType=" + swfLogDto.getBusinessType() + "&iBusinessNo=" + swfLogDto.getiBusinessNo() + "&iModelNo=" + swfLogDto.getiModelNo()
									+ "&iNodeNo=" + swfLogDto.getiNodeNo();
						}

						if ("speci".equals(nodeType)) {//预赔信息
							if ("7".equals(typeFlag) || "8".equals(typeFlag) || "5".equals(typeFlag)) {
								strInfoLink = "/claim/prepayFinishQueryList.do?prpLprepayPrepayNo=" + swfLogDto.getKeyOut() + "&caseType=" + typeFlag + flowStr;
							} else {
								strInfoLink = "/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + swfLogDto.getKeyOut() + "&caseType=" + typeFlag + flowStr;
							}
						}

						if ("endca".equals(nodeType)) {//结案信息
							strInfoLink = "/claim/endcaseFinishQueryList.do?prpLendcaseEndcaseNo=" + swfLogDto.getKeyIn() + flowStr;
						}

						if ("cance".equals(nodeType)) {//已注销信息
							strInfoLink = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLogDto.getKeyIn() + flowStr;
						}
			%>
			<tr class=listodd>
				<td align="center">
					<%=index%>
				</td>
				<td align="center">
					<%
						if ("0".equals(status) || "1".equals(status) || "5".equals(status)) {
					%>
					<a href=javascript:alert('该节点目前没有信息')><%=swfLogDto.getRegistNo()%></a>
					<%
						} else {
					%>
					<a href="<%=strInfoLink%>"><%=swfLogDto.getRegistNo()%></a>
					<%
						}
					%>
				</td>
				<td align="center">
					<%=swfLogDto.getPolicyNo()%>
				</td>
				<td align="center">
					<%=swfLogDto.getRiskCodeName()%>
				</td>
				<td align="center">
					<%=swfLogDto.getInsuredName()%>
				</td>
				<td align="center">
					<%=(swfLogDto.getHandlerName().split("-"))[0]%>
				</td>
				<td align="center">
					<%
						if ("regis".equals(nodeType)) {
					%>
					报案
					<%
						} else if ("sched".equals(nodeType)) {
					%>
					调度
					<%
						} else if ("check".equals(nodeType)) {
					%>
					查勘
					<%
						} else if ("claim".equals(nodeType)) {
					%>
					立案
					<%
						} else if ("certa".equals(nodeType)) {
					%>
					定损
					<%
						} else if ("wound".equals(nodeType)) {
					%>
					人伤定损
					<%
						} else if ("propc".equals(nodeType)) {
					%>
					财产定损
					<%
						} else if ("verif".equals(nodeType)) {
					%>
					核损
					<%
						} else if ("certi".equals(nodeType)) {
					%>
					单证
					<%
						} else if ("compp".equals(nodeType)) {
					%>
					计算书
					<%
						} else if ("veric".equals(nodeType)) {
					%>
					核赔
					<%
						} else if ("speci".equals(nodeType)) {
					%>
					预赔
					<%
						} else if ("endca".equals(nodeType)) {
					%>
					结案
					<%
						} else if ("cance".equals(nodeType)) {
					%>
					註銷/拒赔
					<%
						} else if ("recover".equals(nodeType)) {
					%>
					追偿
					<%
						}
					%>
				</td>
				<td align="center">
					<%
						if ("noendca".equals(claimNodeCode) && "endca".equals(nodeType) && "4".equals(status)) {
					%>
					重开赔案
					<%
						} else {
									if ("0".equals(status)) {
					%>
					待处理
					<%
						} else if ("1".equals(status)) {
					%>
					未处理
					<%
						} else if ("2".equals(status)) {
					%>
					正在处理
					<%
						} else if ("3".equals(status)) {
					%>
					回退处理
					<%
						} else if ("4".equals(status)) {
					%>
					已处理
					<%
						} else if ("5".equals(status)) {
					%>
					已回退
					<%
						} else if ("6".equals(status)) {
					%>
					已撤消
					<%
						} else if ("9".equals(status)) {
					%>
					通赔待接收
					<%
						}
								}
					%>
				</td>
			</tr>
			<%
				index++;
					}
			%>
			<tr class="listtail">
				<td colspan="8">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="swfLogDto" property="turnPageDto" />
							<%
								int curPage = swfLogTreeDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
			<%
				}
			%>
			<input type="hidden" name="editType" value="query">
			<input type="hidden" name="claimNodeCode1" value="<bean:write name='claimNodeCode'/>">
		</table>
	</form>
</body>
</html>

