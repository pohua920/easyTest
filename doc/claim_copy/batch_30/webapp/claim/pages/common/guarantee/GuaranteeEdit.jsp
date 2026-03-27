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
<script language='javascript'>
	function resetForm() {
		fm.reset();
	}

	function edit(printType) {
		strUrl = '/claim/common/guarantee/print/MairineRightsTransferReportPrint.jsp?BizNo=' + fm.ClaimNo.value + '&PrintType=' + printType;
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
		var newWindow = window.open(strURL, strWindowName, 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
		newWindow.focus();
		return newWindow;
	}

	function unDisabled(index) {
		var buttonSaveList = document.getElementsByName("buttonPrint");
		var editRadioList = document.getElementsByName("editRadio");
		for (var i = 0; i < editRadioList.length; i++) {
			if (editRadioList[i].checked == true) {
				buttonSaveList[i].disabled = false;
			} else {
				buttonSaveList[i].disabled = true;
			}
		}
	}
  </script>
</head>
<body class="interface" onload="initPage();">
	<%-- 调用loadForm 初始化页面 --%>
<body class="interface" onload="">
	<form name=fm action="/claim/guarantee.do?actionType=save" method="post" onsubmit="">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute("org.apache.struts.action.TOKEN")%>">
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
					<input type=text name="GuaranteeTypeCode" class="codecode" style="width: 27%" title="擔保種類名稱" value="<bean:write name="prplguaranteeDto" property="guaranteeTypeCode"/>"
						ondblclick="code_CodeSelect(this,'GuaranteeType','0,1','Y');" onchange="code_CodeChange(this,'GuaranteeType','0,1','Y');" onkeyup="code_CodeSelect(this,'GuaranteeType','0,1','Y');">
					<input type=text name="GuaranteeTypeName" class="codecode" style="width: 27%" title="擔保種類名稱" value="<bean:write name="prplguaranteeDto" property="guaranteeTypeName"/>"
						ondblclick="code_CodeSelect(this,'GuaranteeType','-1,0','Y');" onchange="code_CodeChange(this,'GuaranteeType','-1,0','Y');" onkeyup="code_CodeSelect(this,'GuaranteeType','-1,0','Y');">
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipMoney" />
					：
					<!-- 担保金额 -->
				</td>
				<td class="input">
					<input name="SumGuarantee" class="text" value="<bean:write name="prplguaranteeDto" property="sumGuarantee"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.sponsorshipExportTime" />
					：
					<!-- 担保出椐时间 -->
				</td>
				<td class="input">
					<%
						String status = (String) request.getAttribute("status");
						if ("1".equals(status)) {
					%>
					<input type="text" name="OfferTime" class="query" readonly value="<%=new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).addYear(0)%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.OfferTime', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
					} else {
					%>
					<input type="text" name="OfferTime" class="query" readonly value="<bean:write name="prplguaranteeDto" property="offerTime"/>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.OfferTime', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
					}
					%>
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipKind" />
					：
					<!-- 担保币别 -->
				</td>
				<td class="input">
					<input type=text name="Currency" class="codecode" style="width: 27%" title="幣別" value="<bean:write name="prplguaranteeDto" property="currency"/>"
						ondblclick="code_CodeSelect(this,'currency','0,1','Y');" onchange="code_CodeChange(this,'currency','0,1','Y');" onkeyup="code_CodeSelect(this,'currency','0,1','Y');">
					<input type=text name="CurrencyName" class="codecode" style="width: 27%" title="幣別" value="<bean:write name="prplguaranteeDto" property="currencyName"/>"
						ondblclick="code_CodeSelect(this,'currency','-1,0','Y');" onchange="code_CodeChange(this,'currency','-1,0','Y');" onkeyup="code_CodeSelect(this,'currency','-1,0','Y');">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="guarantee.whetherGuaranteeRecycling" />
					：
					<!-- 担保是否回收 -->
				</td>
				<td class="input">
					<html:select property="recycleFlag" style="width:204px" name="prplguaranteeDto">
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
					<!-- 担保出具份数 -->
				</td>
				<td class="input">
					<input name="offerCount" class="text" value="<bean:write name="prplguaranteeDto" property="offerCount"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="claim.applicant" />
					：
					<!-- 申请人 -->
				</td>
				<td class="input">
					<input name="applyPerson" class="text" value="<bean:write name="prplguaranteeDto" property="applyPerson"/>">
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
					<%
					if ("1".equals(status)) {
					%>
					<input type="text" name="unValidStartDate" class="query" readonly value="<%=new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).addYear(0)%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.unValidStartDate', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
					} else {
					%>
					<input type="text" name="unValidStartDate" class="query" readonly value="<bean:write name="prplguaranteeDto" property="unvalidStartDate"/>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.unValidStartDate', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
					}
					%>
				</td>
				<td class="title">
					<s:text name="guarantee.sponsorshipEndDate" />
					：
					<!-- 担保止期 -->
				</td>
				<td class="input">
					<%
					if ("1".equals(status)) {
					%>
					<input type="text" name="unValidEndDate" class="query" readonly value="<%=new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).addYear(0)%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.unValidEndDate', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
					} else {
					%>
					<input type="text" name="unValidEndDate" class="query" readonly value="<bean:write name="prplguaranteeDto" property="unValidendDate"/>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.unValidEndDate', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
								DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
					}
					%>
				</td>
			</tr>
			<table>
				<tr>
					<td class="title" colspan='8'>
						<s:text name="guarantee.printLineAdjust" />
						<!-- 列印格式调整 -->
					</td>
				</tr>
				<%
							PrplguaranteeDto prplguaranteeDto = (PrplguaranteeDto) request.getAttribute("prplguaranteeDto");
					String buttonSaveType = prplguaranteeDto.getGuaranteeType();
				%>
				<tr>
					<td class="title">
						<input type="checkbox" name="editRadio" value="1" title="共 同 海 損 擔 保 函" onClick="unDisabled(0);" <%if((buttonSaveType.indexOf("1")>-1)){ %> checked="true" <%} %>>
					</td>
					<td class="title">
						<input type="button" name="buttonPrint" value="<s:text name="button.lossResponsibility.value" />"
						<!-- 共 同 海 损 担 保 函 -->
						class="bigbutton" onclick="edit('5');"
						<%if((buttonSaveType.indexOf("1")<0)){ %>
						disabled="true"
						<%} %>>
					</td>
					<td class="input">
						<input type="checkbox" name="editRadio" value="2" title="AVERAGE GUARANTEE(共損擔保函)" onClick="unDisabled(1);" <%if((buttonSaveType.indexOf("2")>-1)){ %> checked="true" <%} %>>
					</td>
					<td class="input">
						<input type="button" name="buttonPrint" value="<s:text name="button.totalGuarantee.value" />" class="superbigbutton"
						<!-- AVERAGE GUARANTEE(共损担保函) -->
						onclick="edit('7');"
						<%if((buttonSaveType.indexOf("2")<0)){ %>
						disabled="true"
						<%} %>>
					</td>
					<td class="title">
						<input type="checkbox" name="editRadio" value="3" title="救助擔保函" onClick="unDisabled(2);" <%if((buttonSaveType.indexOf("3")>-1)){ %> checked="true" <%} %>>
					</td>
					<td class="title">
						<input type="button" name="buttonPrint" value="<s:text name="button.helpGuarantee.value" />"
						<!-- 救助担保函 -->
						class="bigbutton" onclick="edit('8');"
						<%if((buttonSaveType.indexOf("3")<0)){ %>
						disabled="true"
						<%} %>>
					</td>
					<td class="input">
						<%--					<input type="button" name="buttonSave" value="货 物 详 细 情 况" class="bigbutton"--%>
						<%--						onclick="edit('6');">--%>
						<input type="checkbox" name="editRadio" value="4" title="船貨不可分割協議" onClick="unDisabled(3);" <%if((buttonSaveType.indexOf("4")>-1)){ %> checked="true" <%} %>>
					</td>
					<td class="input">
						<input type="button" name="buttonPrint" value="<s:text name="button.cargoInseparableAgreement.value" />"
						<!-- 船货不可分割协议 -->
						class="bigbutton" onclick="edit('9');"
						<%if((buttonSaveType.indexOf("4")<0)){ %>
						disabled="true"
						<%} %>>
					</td>
				</tr>
				<%
				if (!"1".equals(status)) {
				%>
				<tr>
					<td class="input" colspan="8">
						<s:text name="guarantee.checkedAdvice" />
						：
						<!-- 审核意见 -->
					</td>
				</tr>
				<tr>
					<td class="input" colspan="8">
						<textarea class="readonly" readonly style="wrap: hard" rows="5" cols="80" name="handleTextShow"><bean:write name="prplguaranteeDto" property="handleText" /></textarea>
					</td>
				</tr>
				<%
				}
				%>
				<tr>
					<td class="input" colspan="8">
						<s:text name="guarantee.submitRemark" />
						：
						<!-- 提交备注 -->
					</td>
				</tr>
				<tr>
					<td class="input" colspan="8">
						<textarea style="wrap: hard" rows="10" cols="80" name="handleText"></textarea>
					</td>
				</tr>
			</table>
			<input type="hidden" name="editType" value="SAVE">
			<table class="common" align="center">
				<tr>
					<td class="button">
						<input type="button" name="buttonSave" value=" <s:text name="button.helpGuarantee.value" />儲存 " class="button" onclick="submitForm();">
					</td>
					<td class="button"></td>
				</tr>
			</table>
			</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
