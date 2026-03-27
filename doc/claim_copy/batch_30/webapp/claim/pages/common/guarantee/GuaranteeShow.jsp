<%--
****************************************************************************
* DESC       ：申请担保查看主画面
* AUTHOR     ：
* CREATEDATE ：2009-06-16
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<%
	String claimNo = (String) request.getAttribute("claimNo");
%>
<html:html locale="true">
<head>
<title><s:text name="guarantee.sponsorshipFind" /></title>
<!-- 担保查看 -->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/DAA/endcase/js/DAAEndcaseEdit.js"></script>
<script src="/claim/common/js/ClaimPub.js"></script>
<script src="/claim/common/guarantee/js/GuaranteeEdit.js"></script>
<script language='javascript'
	function submitPass() {
		fm.editType.value = "pass";
		fm.submit()
	}
	function submitBack() {
		fm.editType.value = "back";
		fm.submit()
	}
 </script>
</head>
<body class="interface" onload="initPage();">
	<%-- 调用loadForm 初始化页面 --%>
<body class="interface" onload="">
	<form name=fm action="/claim/guarantee.do?actionType=undwrtSubmit" method="post" onsubmit="">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session
						.getAttribute("org.apache.struts.action.TOKEN")%>">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class=formtitle colspan="4">
					<s:text name="guarantee.sponsorshipFind" />
					<!-- 担保查看 -->
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpLclaim.claimNo" />
					：
					<!-- 立案号 -->
				</td>
				<td class="input">
					<input name="ClaimNo" class="readonly" readonly value="<bean:write name="prplguaranteeDto" property="claimNo"/>">
				</td>
				<td class="title">
					<s:text name="db.prpLclaimpolicy.policyNo" />
					：
					<!-- 保单号 -->
				</td>
				<td class="input">
					<input name="PolicyNo" class="readonly" readonly value="<bean:write name="prplguaranteeDto" property="policyNo"/>">
					<input type="hidden" name="registNo" value="<bean:write name="prplguaranteeDto" property="registNo"/>">
					<input type="hidden" name="riskCode" value="<bean:write name="prplguaranteeDto" property="riskCode"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.sponsorshipKindName" />
					：
					<!-- 担保种类名称 -->
				</td>
				<td class="input">
					<input type=text name="GuaranteeTypeCode" class="readonly" class="readonly" style="width: 27%" title="擔保種類名稱" value="<bean:write name="prplguaranteeDto" property="guaranteeTypeCode"/>" readonly>
					<input type=text name="GuaranteeTypeName" class="readonly" class="readonly" style="width: 27%" title="擔保種類名稱" value="<bean:write name="prplguaranteeDto" property="guaranteeTypeName"/>" readonly>
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipMoney" />
					：
					<!-- 担保金额 -->
				</td>
				<td class="input">
					<input name="SumGuarantee" readonly class="readonly" class="readonly" value="<bean:write name="prplguaranteeDto" property="sumGuarantee"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.sponsorshipExportTime" />
					：
					<!-- 担保出椐时间 -->
				</td>
				<td class="input">
					<input type="text" name="OfferTime" readonly class="readonly" class="readonly" value="<bean:write name="prplguaranteeDto" property="offerTime"/>">
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipKind" />
					：
					<!-- 担保币别 -->
				</td>
				<td class="input">
					<input type=text name="Currency" readonly class="readonly" style="width: 27%" title="幣別" value="<bean:write name="prplguaranteeDto" property="currency"/>">
					<input type=text name="CurrencyName" readonly class="readonly" style="width: 27%" title="幣別" value="<bean:write name="prplguaranteeDto" property="currencyName"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.whetherGuaranteeRecycling" />
					：
					<!-- 担保是否回收 -->
				</td>
				<td class="input">
					<html:select property="recycleFlag" disabled="true" style="width:204px" name="prplguaranteeDto">
						<html:option value="1">
							<s:text name="guarantee.recycling" />
						</html:option>
						<!-- 回收 -->
						<html:option value="0">
							<s:text name="guarantee.noRecycling" />
						</html:option>
						<!-- 不回收 -->
					</html:select>
				</td>
				<td class="title"></td>
				<td class="input"></td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.sponsorshipOutNumber" />
					<!-- 担保出具份数 -->
				</td>
				<td class="input">
					<bean:write name="prplguaranteeDto" property="offerCount" />
					<s:text name="guarantee.part" />
					<!-- 份 -->
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipNotReturnNumber" />
				</td>
				<!-- 担保未收回份数 -->
				<td class="input">
					<input name="offerCountNot" class="readonly" readonly value="<bean:write name="prplguaranteeDto" property="offerCountNot"/>">
					<s:text name="guarantee.part" />
					<!-- 份 -->
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="claim.applicant" />
					：
					<!-- 申请人 -->
				</td>
				<td class="input">
					<input name="applyPerson" class="readonly" readonly value="<bean:write name="prplguaranteeDto" property="applyPerson"/>">
				</td>
				<td class="title">
					<s:text name="print.reviewer" />
					：
					<!-- 审核人 -->
				</td>
				<td class="input">
					<input name="undwrtPersonName" class="readonly" readonly value="<bean:write name="prplguaranteeDto" property="undwrtPersonName"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.sponsorshipStartDate" />
					：
					<!-- 担保起期 -->
				</td>
				<td class="input">
					<input type="text" name="unValidStartDate" class="readonly" readonly value="<bean:write name="prplguaranteeDto" property="unvalidStartDate"/>">
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipEndDate" />
					：
					<!-- 担保止期 -->
				</td>
				<td class="input">
					<input type="text" name="unValidEndDate" class="readonly" readonly value="<bean:write name="prplguaranteeDto" property="unValidendDate"/>">
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="2" cellspacing="1">
			<tr>
				<td class="title" colspan="5">
					<s:text name="guarantee.checkInformation" />
					<!-- 审核信息 -->
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpDrate.serialNo" />
					<!-- 序号 -->
				</td>
				<td class="title">
					<s:text name="guarantee.nodeType" />
					<!-- 节点类型 -->
				</td>
				<td class="title">
					<s:text name="guarantee.operateName" />
					<!-- 操作员名称 -->
				</td>
				<td class="title">
					<s:text name="guarantee.dealIime" />
					<!-- 处理时间 -->
				</td>
				<td class="title">
					<s:text name="guarantee.dealAdivice" />
					<!-- 处理意见 -->
				</td>
			</tr>
			<logic:iterate name="prplguaranteeDto" id="guaranteeundwrtlog" property="guaranteeundwrtlogList">
				<tr>
					<td class="title">
						<bean:write name="guaranteeundwrtlog" property="serialNo" />
					</td>
					<td class="title">
						<bean:write name="guaranteeundwrtlog" property="nodeName" />
					</td>
					<td class="title">
						<bean:write name="guaranteeundwrtlog" property="operatorName" />
					</td>
					<td class="title">
						<bean:write name="guaranteeundwrtlog" property="submitTime" />
					</td>
					<td class="title">
						<bean:write name="guaranteeundwrtlog" property="handleText" />
					</td>
				</tr>
			</logic:iterate>
		</table>
		<input type="hidden" name="editType">
		<table class="common" align="center">
			<tr>
				<td class="button"></td>
				<td class="button">
					<input type="button" name="buttonSave" value="<s:text name="prompt.back" />" class="button"
					<!-- 返回 -->
					onclick="history.go(-1);">
				</td>
			</tr>
		</table>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
