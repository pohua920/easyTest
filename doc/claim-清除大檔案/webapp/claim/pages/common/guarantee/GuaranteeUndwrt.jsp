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
<%@page import="com.sinosoft.claim.dto.domain.PrplguaranteeDto"%>
<%
	String claimNo = (String) request.getAttribute("claimNo");
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
<script language='javascript'
	function submitPass() {
		fm.editType.value = "pass";
		fm.submit()
	}
	function submitBack() {
		fm.editType.value = "back";
		fm.submit()
	}
	function print(type) {
		var strUrl = '';
		strUrl = '/claim/common/guarantee/print/UIEditReportPrint.jsp?BizNo='
				+ fm.ClaimNo.value + '&BizType=3&showOnly=yes&PrintType='
				+ type;
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
	<form name=fm action="/claim/guarantee.do?actionType=undwrtSubmit" method="post" onsubmit="">
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
			<tr>
				<td class="input" colspan="4">
					<s:text name="guarantee.submitRemark" />
					：
					<!-- 提交备注 -->
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<textarea class="readonly" readonly style="wrap: hard" rows="4" cols="80" name="handleTextShow"><bean:write name="prplguaranteeDto" property="handleText" /></textarea>
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<s:text name="guarantee.checkedAdvice" />
					：
					<!-- 审核意见 -->
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<textarea style="wrap: hard" rows="4" cols="80" name="handleText"></textarea>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType">
		<%
			PrplguaranteeDto prplguaranteeDto = (PrplguaranteeDto) request
						.getAttribute("prplguaranteeDto");
				String buttonSaveType = prplguaranteeDto.getGuaranteeType();
		%>
		<table class="common" align="center">
			<tr>
				<td class="button">
					<input type="button" name="buttonSave" value="<s:text name="button.lossResponsibility.value" />" class="bigbutton"
					<!-- 共 同 海 损 担 保 函 -->
					onclick="print('5');"
					<%
						if ((buttonSaveType.indexOf("1") < 0)) {
					%>
					disabled="true"
					<%
						}
					%>>
					<%--					<input type="button" name="buttonSave" value="收据及权益转让书-列印" class="bigbutton"--%>
					<%--						onclick="print('3');">--%>
				</td>
				<td class="button">
					<input type="button" name="buttonSave" value="<s:text name="button.totalGuarantee.value" />" class="superbigbutton"
					<!-- AVERAGE GUARANTEE(共损担保函) -->
					onclick="print('7');"
					<%
						if ((buttonSaveType.indexOf("2") < 0)) {
					%>
					disabled="true"
					<%
						}
					%>>
					<%--					<input type="button" name="buttonSave" value="Receipt and Subrogation Form--列印" class="bigbutton"--%>
					<%--						onclick="print('4');">--%>
				</td>
			</tr>
			<tr>
				<td class="button">
					<input type="button" name="buttonSave" value="<s:text name="button.helpGuarantee.value" />" class="bigbutton"
					<!-- 救助担保函 -->
					onclick="print('8');"
					<%
						if ((buttonSaveType.indexOf("3") < 0)) {
					%>
					disabled="true"
					<%
						}
					%>>
				</td>
				<td class="button">
					<input type="button" name="buttonSave" value="<s:text name="button.cargoInseparableAgreement.value" />" class="bigbutton"
					<!-- 船货不可分割协议 -->
					onclick="print('9');"
					<%
						if ((buttonSaveType.indexOf("4") < 0)) {
					%>
					disabled="true"
					<%
						}
					%>>
					<%--					<input type="button" name="buttonSave" value="货 物 详 细 情 况" class="bigbutton"--%>
					<%--						onclick="print('6');">--%>
					<%--					<input type="button" name="buttonSave" value="法定代表人身份证明书" class="bigbutton"--%>
					<%--						onclick="print('10');">--%>
					<%--					<input type="button" name="buttonSave" value="授　权　委　托　书" class="bigbutton"--%>
					<%--						onclick="print('11');">--%>
				</td>
			</tr>
			<tr>
				<td class="button">
					<%--					<input type="button" name="buttonSave" value="初始化" class="bigbutton"--%>
					<%--						onclick="edit();">--%>
				</td>
				<td class="button"></td>
			</tr>
		</table>
		<table class="common" align="center">
			<tr>
				<td class="button">
					<input type="button" name="buttonSave" value="<s:text name="button.checkPass.value" />" class="button"
					<!-- 审核通过 -->
					onclick="submitPass();">
				</td>
				<td class="button">
					<input type="button" name="buttonSave" value="<s:text name="guarantee.return" />" class="button"
					<!-- 打回 -->
					onclick="submitBack();">
				</td>
			</tr>
		</table>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
