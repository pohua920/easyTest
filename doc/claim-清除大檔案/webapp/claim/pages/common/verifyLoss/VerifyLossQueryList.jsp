<%--
****************************************************************************
* DESC       ：核损结果
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
								zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLverifyLossDto"%>
<%
	//增加考虑车辆定损的单独信息显示的差异显示，当是车辆定损的情况下，需要
	String nodeType = request.getParameter("nodeType");
	System.out.println("nodeType:" + nodeType);
	String verifDisplay = "none";
	int colspan = 5;//合並的cols数目
	//车辆核损特别显示
	if ("verif".equals(nodeType)) {
		verifDisplay = "";
		colspan = 8;
	}
%>
<script language="javascript">
  <%--案件状态标志处理
	--%>
	submitForm()
	{
		if ((fm.InsuredNameSign.value == "=" && fm.InsuredName.value.length > 0)
				|| (fm.LicenseNoSign.value == "=" && fm.LicenseNo.value.length > 0)
				|| (fm.RegistNoSign.value == "=" && fm.RegistNo.value.length > 0)
				|| (fm.PolicyNoSign.value == "=" && fm.PolicyNo.value.length > 0)) {
			//输入了一个条件，可以查
		} else if ((fm.RegistNoSign.value == "=*" && fm.RegistNo.value.length > 8)
				|| (fm.PolicyNoSign.value == "=*" && fm.PolicyNo.value.length > 8)) {
			if ("D" == getClassCodeType(fm.RegistNo.value.substr(1, 2))
					|| "D" == getClassCodeType(fm.PolicyNo.value.substr(1, 2))) {
				alert("车险必须精确查询！");
				return false;
			} else {
				//非车险可以前9位模糊查询
			}
		} else {
			alert("車險必須輸入備案號碼、保單號碼、牌照號碼、被保險人其中一項精確查詢！\n非車險可以用備案號碼或者保單號碼的前9位進行模糊查詢！");
			return false;
		}
		var ref = "";
		for (i = 0; i < fm.status.length; i++) {
			if (fm.status[i].checked == true) {
				ref = ref + fm.status[i].value + ",";
			}
		}
		fm.searchFlag.value = "true";
		fm.pageNo.value = "1";//查询後页面设为1
		fm.caseFlag.value = ref;
		fm.submit();//提交
	}
	//-
->
</script>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/common/js/showpage.js"> </script>
<html:base />
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/verifyLossQuery.do" method="post" onSubmit="return validateForm(this);">
		<table width="90%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="certainLoss.nuclearDamageQuery" />
				</td>
			</tr>
			<%-- 查询核损信息 --%>
			<tr>
				<td class='title'>
					<s:text name="prpLregist.registNo " />
					：
				</td>
				<%-- 报案号 --%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLlawsuit.policyNo " />
					：
				</td>
				<%-- 保单号 --%>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />
					:
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />
					:
				</td>
				<%-- 操作时间 --%>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="query" style="width: 40%">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onClick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
						.getYear() - 15%>', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY)
						.getYear() + 2%>')">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.status" />
					:
				</td>
				<%-- 案件状态 --%>
				<td class='input'>
					<input type="hidden" name="caseFlag" value="">
					<!--<input type="checkbox" name="status" value="1">未处理-->
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%-- 正处理 --%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%-- 已提交 --%>
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.view_larrearage.insuredname" />
					:
				</td>
				<%-- 被保险人名称 --%>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%-- "="符号，必须精确查询。 --%>
					>
					<s:text name="prompt.schedule.query2" />
					<br>
					<%-- "=*"符号，前匹配後模糊的查询。 --%>
					<s:text name="prompt.schedule.query3" />
					<br>
					<%-- 车险必须输入报案号、保单号、车牌号、被保险人其中一项精确查询！--%>
					<s:text name="prompt.schedule.query4 " />
					<%-- 非车险可以用报案号或者保单号的前9位进行模糊查询！ --%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
					<input type="hidden" name="nodeType2" value="<%=request.getParameter("nodeType")%>">
					<input type="hidden" name="editType2" value="SHOW">
					<input name="searchFlag" type="hidden" id="searchFlag">
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=<%=colspan%> class="formtitle">
					<s:text name="certainLoss.nuclearDamageQuery" />
				</td>
			</tr>
			<%-- 查询核损信息 --%>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.status" />
				</td>
				<%-- 案件状态 --%>
				<td class="centertitle">
					<s:text name="db.prpLregist.registNo" />
				</td>
				<%-- 报案号 --%>
				<td class="centertitle">
					<s:text name="db.prpLclaim.policyNo" />
				</td>
				<td class="centertitle" Style="display:<%=verifDisplay%>">
					<s:text name="db.prpCitem_car.licenseNo" />
				</td>
				<%-- 车牌号码 --%>
				<td class="centertitle" Style="display:<%=verifDisplay%>">
					<s:text name="regist.prpLregist.insureCar" />
				</td>
				<%-- 保单车辆 --%>
				<td class="centertitle">
					<s:text name="backCheck.nuclearRomanCode" />
				</td>
				<%-- 核损人代码 --%>
				<td class="centertitle">
					<s:text name="backCheck.nuclearRomanDate" />
				</td>
				<%-- 核损日期 --%>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLverifyLossDto" property="verifyLossList">
				<logic:iterate id="prpLverifyLoss1" name="prpLverifyLossDto" property="verifyLossList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<tr class=common>
						<td align="center">
							<logic:equal name="prpLverifyLoss1" property="status" value='1'>
								<s:text name="common.status.untreated" />
								<%-- 未处理 --%>
							</logic:equal>
							<logic:equal name="prpLverifyLoss1" property="status" value='2'>
								<s:text name="common.status.intreating" />
								<%-- 正处理 --%>
							</logic:equal>
							<logic:equal name="prpLverifyLoss1" property="status" value='3'>
								<s:text name="common.status.treated" />
								</c:if>
								<%--已处理  --%>
							</logic:equal>
							<logic:equal name="prpLverifyLoss1" property="status" value='4'>
								<s:text name="common.status.submited" />
								<%-- 已提交 --%>
							</logic:equal>
							<logic:equal name="prpLverifyLoss1" property="status" value='5'>
								<s:text name="common.status.revoked" />
								<%--已撤消  --%>
							</logic:equal>
						</td>
						<td align="center">
							<a
								href="/claim/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=<bean:write name='prpLverifyLoss1' property='registNo'/>&editType=<bean:write name='prpLverifyLossDto' property='editType'/>&riskCode=<bean:write name='prpLverifyLoss1' property='riskCode'/>&lossItemCode=<bean:write name='prpLverifyLoss1' property='lossItemCode'/>&nodeType=verif">
								<bean:write name="prpLverifyLoss1" property="registNo" />
							</a>
						</td>
						<td align="center">
							<logic:iterate id="currelatepolicyNo" name="prpLverifyLoss1" property="relatepolicyNo">
								<bean:write name="currelatepolicyNo" />
								<br>
							</logic:iterate>
						</td>
						<td align="center" Style="display:<%=verifDisplay%>">
							<bean:write name="prpLverifyLoss1" property="licenseNo" />
						</td>
						<td align="center" Style="display:<%=verifDisplay%>">
							<logic:equal name="prpLverifyLoss1" property="insureCarFlag" value='1'>
								<s:text name="print.markCar" />
								<%--标的车 --%>
							</logic:equal>
							<logic:notEqual name="prpLverifyLoss1" property="insureCarFlag" value='1'>
								<s:text name="certainLoss.thirdCarLoss.thirdCar" />
								<%--三者车 --%>
							</logic:notEqual>
						</td>
						<td align="center">
							<bean:write name="prpLverifyLoss1" property="handlerCode" />
						</td>
						<!--<td align="center"><bean:write name="prpLverifyLoss1" property="defLossDate"/></td>-->
						<td align="center">
							<bean:write name="prpLverifyLoss1" property="underWriteEndDate" />
						</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="<%=colspan%>">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLverifyLossDto" property="turnPageDto" />
							<%
								PrpLverifyLossDto prpLverifyLossDto = (PrpLverifyLossDto) request
											.getAttribute("prpLverifyLossDto");
									int curPage = prpLverifyLossDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</tr>
		</table>
		<input type="hidden" name="nodeType" value="<%=request.getParameter("nodeType")%>">
		<input type="hidden" name="editType" value="<%=request.getParameter("editType")%>">
	</form>
</body>
</html:html>
