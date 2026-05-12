<%--
****************************************************************************
* DESC       ：申请诉讼主画面
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
<title><s:text name="title.referlawBeforeEdit.litigationInfoEdit" /></title>
<%--涉诉信息编辑--%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/DAA/endcase/js/DAAEndcaseEdit.js"></script>
<script src="/claim/common/js/ClaimPub.js"></script>
<script src="/claim/common/guarantee/js/GuaranteeEdit.js"></script>
<script language='javascript'>
	function resetForm() {
		fm.reset();
	}
	function submitForm() {
		fm.action = '/claim/referlaw.do?actionType=editSave';
		fm.submit();
	}
</script>
</head>
<body class="interface" onload="initPage();">
	<%-- 调用loadForm 初始化页面 --%>
<body class="interface" onload="">
	<form name=fm action="/claim/referlaw.do?actionType=editSave" method="post" onsubmit="">
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session
						.getAttribute("org.apache.struts.action.TOKEN")%>">
		<input type="hidden" name="riskCode" value="<bean:write name='prplreferlawDto' property="riskcode"/>">
		<input type="hidden" name="serialno" value="<bean:write name='prplreferlawDto' property="serialno"/>">
		<span style="display: none" id="Lawyerspan">
			<table width="100%" cellpadding="0" cellspacing="1" class="common" id="Lawyer_Data" style="display: none">
				<tbody>
					<tr>
						<td>
							<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
							<input name="LawyerName" class="input" value="" maxlength="100">
						</td>
						<td>
							<input name="Lawoffice" class="input" value="" maxlength="40">
						</td>
						<td>
							<input name="Phone" class="input" value="" maxlength="15">
						</td>
						<td>
							<input name="Email" class="input" value="" maxlength="25">
						</td>
						<td>
							<input name="Place" class="input" value="" maxlength="40">
						</td>
						<td>
							<input name="PostCode" class="input" value="" maxlength="6">
						</td>
						<td>
							<select name='validstatus'>
								<option value="1">
									<s:text name="common.status.effective" />
									<%--有效--%>
								</option>
								<option value="0">
									<s:text name="common.status.invalid" />
									<%--无效--%>
								</option>
							</select>
						</td>
						<td class="input" style='width: 4%'>
							<div align="center">
								<input type=button name="buttonPropDelete" class=smallbutton onclick="deleteRow(this,'Lawyer')" value="-" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</span>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class=formtitle colspan="4">
					<s:text name="referlaw.relatedInfoInput" />
					<%--涉诉信息录入--%>
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="prpLclaim.claimNo" />
					：
					<%--立案号--%>
				</td>
				<td class="input">
					<input name="ClaimNo" class="readonly" readonly value="<bean:write name='prplreferlawDto' property="claimno"/>">
				</td>
				<td class="title">
					<s:text name="db.view_larrearage.policyNo" />
					：
					<%--保单号--%>
				</td>
				<td class="input">
					<input name="PolicyNo" class="readonly" readonly value="<bean:write name='prplreferlawDto' property="policyno"/>">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="regist.prpLregist.registNo" />
					：
					<%--报案号--%>
				</td>
				<td class="input">
					<input name="RegistNo" class="readonly" readonly value="<bean:write name='prplreferlawDto' property="registno"/>">
				</td>
				<td class="title"></td>
				<td class="input"></td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpLreplevynew.lawamount" />
					：
					<%--诉讼金额--%>
				</td>
				<td class="input">
					<input type=text name="SumReferlaw" title="訴訟金額" value="<bean:write name='prplreferlawDto' property="sumreferlaw"/>">
				</td>
				<td class="title">
					<s:text name="referlaw.litigationAmount" />
					<%--诉讼金额币别--%>
				</td>
				<td class="title">
					<input type=text name="Currency" class="codecode" style="width: 27%" title="幣別" value="<bean:write name="prplreferlawDto" property="currency"/>"
						ondblclick="code_CodeSelect(this,'currency','0,1','Y');" onchange="code_CodeChange(this,'currency','0,1','Y');" onkeyup="code_CodeSelect(this,'currency','0,1','Y');">
					<input type=text name="CurrencyName" class="codecode" style="width: 27%" title="幣別" value="<bean:write name="prplreferlawDto" property="currencyname"/>"
						ondblclick="code_CodeSelect(this,'currency','1,0','Y');" onchange="code_CodeChange(this,'currency','1,0','Y');" onkeyup="code_CodeSelect(this,'currency','1,0','Y');">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="db.prpLreplevynew.suitfee" />
					<%--诉讼费用--%>
				</td>
				<td class="input">
					<input type=text name="SumReferlawFee" title="訴訟費用" value="<bean:write name='prplreferlawDto' property="sumreferlawfee"/>">
				</td>
				<td class="title">
					<s:text name="referlaw.litigationCostMoney" />
					<%--诉讼费用币别--%>
				</td>
				<td class="input">
					<input type=text name="CurrencyFee" class="codecode" style="width: 27%" title="費用幣別" value="<bean:write name="prplreferlawDto" property="currencyfee"/>"
						ondblclick="code_CodeSelect(this,'currency','0,1','Y');" onchange="code_CodeChange(this,'currency','0,1','Y');" onkeyup="code_CodeSelect(this,'currency','0,1','Y');">
					<input type=text name="CurrencyFeeName" class="codecode" style="width: 27%" title="費用幣別" value="<bean:write name="prplreferlawDto" property="currencyfeename"/>"
						ondblclick="code_CodeSelect(this,'currency','1,0','Y');" onchange="code_CodeChange(this,'currency','1,0','Y');" onkeyup="code_CodeSelect(this,'currency','1,0','Y');">
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="referlaw.lawsuitStartTime" />
					：
					<%--诉讼起始时间--%>
				</td>
				<td class="input">
					<%
						String status = "0";
							if ("1".equals(status)) {
					%>
					<input type="text" name="ValidStartDate" class="query" readonly value="<%=new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).addYear(0)%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.ValidStartDate', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
						} else {
					%>
					<input type="text" name="ValidStartDate" class="query" readonly value="<bean:write name="prplreferlawDto" property="validstartdate"/>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.ValidStartDate', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
						}
					%>
				</td>
				<td class="title">
					<s:text name="referlaw.lawsuitEndTime" />
					：
					<%--诉讼终止时间--%>
				</td>
				<td class="input">
					<%
						if ("1".equals(status)) {
					%>
					<input type="text" name="ValidEndDate" class="query" readonly value="<%=new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).addYear(0)%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.ValidEndDate', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
						} else {
					%>
					<input type="text" name="ValidEndDate" class="query" readonly value="<bean:write name="prplreferlawDto" property="validenddate"/>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onclick="TogglePopupCalendarWindow('document.fm.ValidEndDate', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
							DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
					<%
						}
					%>
				</td>
			</tr>
			<tr>
				<td class="title">
					<s:text name="referlaw.whetherWin" />
					<%--是否胜诉--%>
				</td>
				<td class="input">
					<html:select property="successflag" style="width:204px" name="prplreferlawDto">
						<html:option value="0">
							<s:text name="common.status.yetWin" />
						</html:option>
						<%--尚未胜诉--%>
						<html:option value="1">
							<s:text name="common.status.alreadyWon" />
						</html:option>
						<%--已胜诉--%>
						<html:option value="2">
							<s:text name="common.status.losing" />
						</html:option>
						<%--败诉--%>
						<html:option value="9">
							<s:text name="certainLoss.thirdCarLoss.dutyOther" />
						</html:option>
						<%--其他--%>
					</html:select>
				</td>
				<td class="title"></td>
				<td class="input"></td>
			</tr>
			<tr>
				<td class="title" colspan="4">
					<s:text name="referlaw.litigationReason" />
					<%--诉讼原因--%>
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<textarea class="text" style="wrap: hard" rows="5" cols="80" name="referlawreason"><bean:write name="prplreferlawDto" property="referlawreason" /></textarea>
				</td>
			</tr>
			<tr>
				<td class=formtitle colspan="4">
					<s:text name="referlaw.attorneyInfo" />
					<%--代理律师信息--%>
				</td>
				</td>
			<tr>
				<td colspan="4" class="title">
					<span>
						<table width="100%" class="common" cellpadding="1" id="Lawyer">
							<thead>
								<tr>
									<td>
										<s:text name="claim.name" />
										<%--姓名--%>
									</td>
									<td>
										<s:text name="referlaw.ownershipLawFirm" />
										<%--归属律师事务所--%>
									</td>
									<td>
										<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFPhoneNumber" />
										<%--联系电话--%>
									</td>
									<td>
										<s:text name="backVisit.email" />
										<%--电子邮件--%>
									</td>
									<td>
										<s:text name="db.prpCinsured.postAddress" />
										<%--通讯地址--%>
									</td>
									<td>
										<s:text name="db.prpCinsuredartif.postCode" />
										<%--邮编--%>
									</td>
									<td>
										<s:text name="referlaw.validity" />
										<%--是否有效--%>
									</td>
									<td>
										<s:text name="certify.delete" />
										<%--删除--%>
									</td>
								</tr>
							</thead>
							<tbody>
								<logic:iterate id="prpllawyerDto" name="prplreferlawDto" property="prpllawyerDtoList">
									<tr>
										<td>
											<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
											<input name="LawyerName" class="input" value="<bean:write name="prpllawyerDto" property="name" />" maxlength="100">
										</td>
										<td>
											<input name="Lawoffice" class="input" value="<bean:write name="prpllawyerDto" property="lawoffice" />" maxlength="40">
										</td>
										<td>
											<input name="Phone" class="input" value="<bean:write name="prpllawyerDto" property="phone" />" maxlength="15">
										</td>
										<td>
											<input name="Email" class="input" value="<bean:write name="prpllawyerDto" property="email" />" maxlength="25">
										</td>
										<td>
											<input name="Place" class="input" value="<bean:write name="prpllawyerDto" property="place" />" maxlength="45">
										</td>
										<td>
											<input name="PostCode" class="input" value="<bean:write name="prpllawyerDto" property="postcode" />" maxlength="6">
										</td>
										<td>
											<html:select name="prpllawyerDto" property="validstatus" style="width:50%">
												<html:option value="1">
													<s:text name="common.status.effective" />
												</html:option>
												<%--有效--%>
												<html:option value="0">
													<s:text name="common.status.invalid" />
												</html:option>
												<%--无效--%>
											</html:select>
										</td>
										<td>
											<div align="center">
												<input type=button name="buttonPropDelete" class=smallbutton onclick="deleteRow(this,'Lawyer')" value="-" style="cursor: hand">
											</div>
										</td>
									</tr>
								</logic:iterate>
							</tbody>
							<tfoot>
								<tr>
									<td class="title" colspan=7 style="width: 97%">
										<s:text name="prompt.schedule.addRename8" />
										<%--(按"+"号键增加财产损失清单信息，按"-"号键删除信息)--%>
									</td>
									<td class="title" align="right" style="width: 3%">
										<div align="center">
											<input type="button" value="+" onclick="insertRow('Lawyer')" class=smallbutton name="buttonDriverInsert" style="cursor: hand">
										</div>
									</td>
								</tr>
							</tfoot>
						</table>
					</span>
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<s:text name="referlaw.instanceVerdict1" />
					：
					<%--法院一审判决情况--%>
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<textarea class="text" style="wrap: hard" rows="5" cols="80" name="adjudgmentOne"><bean:write name="prplreferlawDto" property="adjudgmentone" /></textarea>
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<s:text name="referlaw.instanceVerdict2" />
					：
					<%--法院二审判决情况--%>
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<textarea class="text" style="wrap: hard" rows="5" cols="80" name="adjudgmentTwo"><bean:write name="prplreferlawDto" property="adjudgmenttwo" /></textarea>
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<s:text name="referlaw.instanceVerdict3" />
					：
					<%--法院三审判决情况--%>
				</td>
			</tr>
			<tr>
				<td class="input" colspan="4">
					<textarea class="text" style="wrap: hard" rows="5" cols="80" name="adjudgmentThree"><bean:write name="prplreferlawDto" property="adjudgmentthree" /></textarea>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="SAVE">
		<table class="common" align="center">
			<tr>
				<td class="button">
					<input type="button" name="buttonSave" value="<s:text name='button.save.value' />" class="button" onclick="submitForm();">
				</td>
				<td class="button"></td>
			</tr>
		</table>
	</form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
