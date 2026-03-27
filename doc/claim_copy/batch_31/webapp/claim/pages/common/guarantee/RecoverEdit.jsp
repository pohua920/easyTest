<%--
****************************************************************************
* DESC       ：申请担保主画面
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
	session.putValue("oldRegistLastAccessedTime", "");
%>
<html:html locale="true">
<head>
<title><s:text name="title.guarantee.sponsorshipApplication" /></title>
<!-- 担保申请 -->
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/DAA/endcase/js/DAAEndcaseEdit.js"></script>
<script src="/claim/common/js/ClaimPub.js"></script>
<script src="/claim/common/guarantee/js/GuaranteeEdit.js"></script>
<script language='javascrip
	function resetForm() {
		fm.reset();
	}
	function edit(printType) {
		strUrl = '/claim/common/guarantee/print/MairineRightsTransferReportPrint.jsp?BizNo='
				+ fm.ClaimNo.value + '&PrintType=' + printType;
		printWindow(strUrl, "列印1");
	}
	function printWindow(strURL, strWindowName) {
		//add print liudaoping 2013-04-15
		//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
		return false;
		var pageWidth = screen.availWidth - 10;
		var pageHeight = screen.availHeight - 30;
		if (pageWidth < 100) {
			pageWidth = 100;
		}
		if (pageHeight < 100) {
			pageHeight = 100;
		}
		var newWindow = window
				.open(
						strURL,
						strWindowName,
						'width='
								+ pageWidth
								+ ',height='
								+ pageHeight
								+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
		newWindow.focus();
		return newWindow;
	}

  </script>
</head>
<body class="interface" onload="initPage();">
	<%-- 调用loadForm 初始化页面 --%>
<body class="interface" onload="">
	<form name=fm action="/claim/guarantee.do?actionType=recoverSave" method="post" onsubmit="">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session
						.getAttribute("org.apache.struts.action.TOKEN")%>">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class=formtitle colspan="4">
					<s:text name="title.guarantee.sponsorshipApplication" />
					<!-- 担保申请 -->
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
					<input type=text name="GuaranteeTypeCode" class="readonly" readonly style="width: 27%" title="擔保種類名稱" value="<bean:write name="prplguaranteeDto" property="guaranteeTypeCode"/>">
					<input type=text name="GuaranteeTypeName" class="readonly" readonly style="width: 27%" title="擔保種類名稱" value="<bean:write name="prplguaranteeDto" property="guaranteeTypeName"/>">
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipMoney" />
					：
					<!-- 担保金额 -->
				</td>
				<td class="input">
					<input name="SumGuarantee" class="text" readonly value="<bean:write name="prplguaranteeDto" property="sumGuarantee"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.sponsorshipExportTime" />
					：
					<!-- 担保出椐时间 -->
				</td>
				<td class="input">
					<input type="text" name="OfferTime" class="query" readonly value="<bean:write name="prplguaranteeDto" property="offerTime"/>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.OfferTime', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipKind" />
					：
					<!-- 担保币别 -->
				</td>
				<td class="input">
					<input type=text name="Currency" class="readonly" readonly style="width: 27%" title="幣別" value="<bean:write name="prplguaranteeDto" property="currency"/>">
					<input type=text name="CurrencyName" class="readonly" readonly style="width: 27%" title="幣別" value="<bean:write name="prplguaranteeDto" property="currencyName"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.whetherGuaranteeRecycling" />
					：
					<!-- 担保是否回收 -->
				</td>
				<td class="input">
					<html:select property="recycleFlag" style="width:204px" name="prplguaranteeDto" disabled="true">
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
				<td class="title">
					<s:text name="guarantee.sponsorshipOutNumber" />
				</td>
				<!-- 担保出具份数 -->
				<td class="input">
					<input name="offerCount" class="readonly" readonly value="<bean:write name="prplguaranteeDto" property="offerCount"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.thisSponsorshipNumber" />
					：
					<!-- 本次担保回收份数 -->
				</td>
				<td class="input">
					<input name="offerCountBack" class="text" value="<bean:write name="prplguaranteeDto" property="offerCountBack"/>">
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipNotReturnNumber" />
				</td>
				<!-- 担保未收回份数 -->
				<td class="input">
					<input name="offerCountNot" class="readonly" readonly value="<bean:write name="prplguaranteeDto" property="offerCountNot"/>">
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
					<input name="" class="readonly" value="<bean:write name="prplguaranteeDto" property="undwrtPersonName"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.sponsorshipStartDate" />
					：
					<!-- 担保起期 -->
				</td>
				<td class="input">
					<input type="text" name="unValidStartDate" class="query" readonly value="<bean:write name="prplguaranteeDto" property="unvalidStartDate"/>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.unValidStartDate', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipEndDate" />
					：
					<!-- 担保止期 -->
				</td>
				<td class="input">
					<input type="text" name="unValidEndDate" class="query" readonly value="<bean:write name="prplguaranteeDto" property="unValidendDate"/>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.unValidEndDate', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4"></td>
			</tr>
			<tr>
				<td class="input" colspan="4"></td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="SAVE">
		<table class="common" align="center">
			<tr>
				<td class="button">
					<input type="button" name="buttonSave" value=" <s:text name="form.save" />" class="button"
					<!-- 儲存  -->
					onclick="submitForm();">
				</td>
				<td class="button"></td>
			</tr>
		</table>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
