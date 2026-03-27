<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@ page import="com.sinosoft.claim.schema.model.*"%>
<%
	//强三
	String quaryPolicyNo = (String) request.getAttribute("quaryPolicyNo");
	String mainPolicyNo = (String) request.getAttribute("mainPolicyNo");
	String qsFlag = (String) request.getAttribute("qsFlag");
	String intPayFee = (String) request.getAttribute("intPayFee");
	String qs_PerilCount = (String) request.getAttribute("intPerilCount");
	String qs_RecentCount = (String) request.getAttribute("intRecentCount");

	PrpLregist prpLregist = (PrpLregist) request.getAttribute("prpLregist");
	String remark = (String) request.getAttribute("remark");
	if (remark == null) {
		remark = "";
	}
	String postCode = "";
	if (request.getAttribute("postcode") != null) {
		postCode = (String) request.getAttribute("postcode");
	}
	int intstartHour = 0;
	int intendHour = 0;
	String startHour = "";
	String endHour = "";
	intstartHour = prpLregist.getStartHour();
	intendHour = prpLregist.getEndHour();
	if (intstartHour == 0) {
		startHour = "零時起至";
	} else if (intstartHour == 12) {
		startHour = "十二時起至";
	} else if (intstartHour == 24) {
		startHour = "二十四時起";
	}
	if (intendHour == 12) {
		endHour = "十二時止";
	} else if (intendHour == 24) {
		endHour = "二十四時止";
	} else if (intendHour == 0) {
		endHour = "零時止";
	}
	String advanceType = prpLregist.getAdvanceType(); //垫付赔案类型
%>
<table class="subtable" cellspacing="0">
	<tr>
		<td>
			<div style="background-color: #ffffff">
				<table class=common cellspacing="1" cellpadding="1">
					<tr>
						<td class="left">
							<s:text name="db.prpCmain.policyNo" />：
						</td>
						<td class="right" colspan="3">
							<c:choose>
								<c:when test="${qsFlag=='Y'}">
									<input alt="任意險保單號" type="text" name="prpLregistPolicyNo" class="readonly" style="width: 220px" readonly="true" value="${prpLregist.policyNo}">
									<input alt="強制險保單號" type="text" name="mainPolicyNo" class="readonly" style="width: 220px" readonly="true" value="<%=mainPolicyNo%>">
									<input alt="點選此按鈕可獲得保單相應訊息" type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" border="0" onclick="relate(fm.prpLregistPolicyNo.value);return false;">
								</c:when>
								<c:when test="${qsFlag=='N'}">
									<input alt="任意險保單號" type="text" name="prpLregistPolicyNo" class="readonly" style="width: 220px" readonly="true" value="${prpLregist.policyNo}">
									<input alt="強制險保單號" type="hidden" name="mainPolicyNo" class="readonly" style="width: 220px" readonly="true" value="${prpLregist.policyNo}">
									<input alt="點選此按鈕可獲得保單相應訊息" type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" border="0" onclick="relate(fm.prpLregistPolicyNo.value);return false;">
								</c:when>
							</c:choose>
						</td>
						<td class="left">
							<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
						</td>
						<td class="right">
							<%-- 出险信息画面 --%>
							<%@include file="/pages/DAA/regist/DAAExistRegist.jsp"%>
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="db.prpLregist.insuredName" />：
						</td>
						<td class="right">
							<input type=text name="prpLregistInsuredName" title="被保險人名稱" class="readonly" readonly="true" value="${prpLregist.insuredName}">
						</td>
						<td class="left">
							<s:text name="db.prpLregist.insuredPhoneNumber" />：
						</td>
						<td class="right">
							<input type="text" name="policyInsuredPhoneNumber" class="readonly" maxlength="12" value="${prpLregist.policyInsuredPhoneNumber}">
						</td>
						<td class="right">
							<s:text name="db.prpLregist.insuredMobile" />：
							<%-- 被保险人手机 --%>
						</td>
						<td class="right">
							<input type="text" name="policyInsuredMobile" class="readonly" maxlength="12" value="${prpLregist.policyInsuredMobile}">
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="db.prpLregist.licenseNo" />：
						</td>
						<td class="right">
							<input type="text" name="prpLregistLicenseNo" class="readonly" maxlength="12" value="${prpLregist.licenseNo}">
						</td>
						<td class="left">
							<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyFrameNo" />
						</td>
						<td class="right">
							<input type="text" name="policyInsuredLicenseNumber" class="readonly" maxlength="12" value="${prpLregist.policyInsuredLicenseNumber}">
						</td>
						<td class="right"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="regist.prpLregist.insuranceTime" />：
						</td>
						<td class="right" colspan="2">
							<rc:rcDate name="prpLregistStartDate" title="起保日期" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLregist.startDate}" />
							<s:text name="regist.prpLregist.date" />&nbsp;${prpLregist.startHour}&nbsp;<s:text name="regist.prpLregist.hour" />起 至 
							<rc:rcDate name="prpLregistEndDate" title="終保日期" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLregist.endDate}" />
							<s:text name="regist.prpLregist.date" />&nbsp;${prpLregist.endHour}&nbsp;<s:text name="regist.prpLregist.hour" />止
							<input type="hidden" name="prpLregistStartHour" value="${prpLregist.startHour}">
							<input type="hidden" name="prpLregistEndHour" value="${prpLregist.endHour}">
						</td>
						<td class="right"></td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="db.prpLregist.handler1Name" />：
							<%-- 服務人員 --%>
						</td>
						<td class="right">
							<input type=hidden name="prpLregistHandler1Code" value="${prpLregist.handler1Code}">
							<input type=text name="prpLregistHandler1Name" title="服務人員" class="readonly" readonly="true" value="${prpLregist.handler1Name}">
						</td>
						<td class="left">
							<s:text name="regist.prpLregist.comName" />：
							<%-- 出單單位 --%>
						</td>
						<td class="right">
							<input type=hidden name="prpLregistComCode" value="${prpLregist.comCode}">
							<input type=text name="prpLregistComName" class="readonly" readonly="true" value="${prpLregist.comName}">
						</td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="db.prpLregist.insuredCode" />：
							<%-- 被保險人代碼 --%>
						</td>
						<td class="right">
							<input type=hidden name="prpLregistInsuredCode" class="readonly" readonly="true" value="${prpLregist.insuredCode}">
							${prpLregist.insuredCode}
						</td>
						<td class="left">
							<s:text name="db.prpCmain.insuredAddress" />：
							<%-- 被保險人地址 --%>
						</td>
						<td class="right">
							<input type=text name="prpLregistInsuredAddress" class="readonly" readonly="true" value="${prpLregist.insuredAddress}">
						</td>
						</td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="regist.prpLregist.comprehensive" />：
							<%-- 強制險承保公司 --%>
						</td>
						<td class="right">
							<input type=text name="Remark" class="readonly" readonly="readonly" maxlength="12" style="width: 220px" value="<%=remark%>">
						</td>
						<td class="left">
							<s:text name="regist.prpLregist.brandName" />：
							<%-- 廠牌車型 --%>
						</td>
						<td class="right">
							<input type=text name="prpLregistBrandName" class="readonly" readonly="true" style="width: 220px" value="${prpLregist.brandName}" />
						</td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="regist.prpLregist.areaCode" />：
							<%-- 郵遞區號 --%>
						</td>
						<td class="right">
							<input type=text name="PostCode" class="readonly" readonly="true" style="width: 220px" value="<%=postCode%>" />
						</td>
						<td class="left"></td>
						<td class="right"></td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="db.prpDagent.agentCode" />：
							<%-- 代理人代碼 --%>
						</td>
						<td class="right">
							<input type=text name="prpLregistAgentCode" class="readonly" readonly="true" maxlength="12" style="width: 220px" value="${prpLregist.agentCode}">
						</td>
						<td class="left">
							<s:text name="db.prpDagent.agentName" />：
							<%-- 代理人名稱 --%>
						</td>
						<td class="right">
							<input type=text name="prpLregistAgentName" class="readonly" readonly="true" style="width: 220px" value="${prpLregist.agentName}" />
						</td>
						<td class="right" colspan="2">
							<input type="hidden" name="damageDate" value="<%=request.getParameter("damageDate")%>">
							<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value'/>"
								onclick="backWardPolicy(fm.coreURL.value,fm.prpLregistPolicyNo.value,fm.prpLregistRiskCode.value,fm.prpLregistDamageStartDate.value);">
							<%-- 强三显示  --%>
							<c:if test="${not empty prpLregistRPolicyNo}">
								<input type="button" class="bigbutton" name="policyBackWard" value="<s:text name='button.mandaInsurInfo.value'/>"
									onclick="relateBeforePolicyNo('<c:out value="${prpLregistRPolicyNo.id.policyNo}"/>','<c:out value="${prpLregistRPolicyNo.riskCode}" />',fm.prpLregistDamageStartDate.value);">
							</c:if>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
