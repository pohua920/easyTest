<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-06-03 
* MODIFYLIST ：   Name       Date            Reason/Contents
*               增加代碼選擇的onchange事件，同時支持名稱與代碼的相互選擇
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.schema.model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="ins.framework.utils.DataUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
function showRegistInfo() {
	var win;
	var registShowDo = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + fm.prpLcheckRegistNo.value + "&editType=SHOW&riskCode=" + fm.prpLcheckRiskCode.value
	var newWindow = window.open(registShowDo, "DAARegistEdit", 'width=640,height=300,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	newWindow.focus();
}

/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relatePolicy() {
	var policyNo = fm.prpLcheckPolicyNo.value;
	var linkURL = "http://192.168.60.12:8101/ddccallweb/DAA/tbcbpg/UIPrPoEnDAAShow.jsp?" + "BIZTYPE=POLICY" + "&BizNo=" + policyNo + "&SHOWTYPE=SHOW";
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
/**
 *@description 弹出报案的画面
 *@param       无
 *@return      通过返回true,否 则返回false
 */

function relateRegist() {
	var registNo = fm.prpLcheckRegistNo.value;
	var linkURL = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + registNo + "&editType=SHOW&riskCode=" + fm.riskcode.value;
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}

/**
 *@description 设置画面的初始值
 *@param       无
 *@return      通过返回true,否则返回false
 */

function loadCheckExt() { 
			<%ArrayList<?> checkExtArray = null;
			if (((PrpLcheckExt) request.getAttribute("prpLcheckExt")).getPrpLcheckExtList() != null) {
				checkExtArray = (ArrayList<?>) ((PrpLcheckExt) request.getAttribute("prpLcheckExt")).getPrpLcheckExtList();
				int intValue = -1;
				int indexCheckExt = 0;
				String[] intvalue1 = {};
				String[] intvalue2 = {};
				String[] intvalue3 = {};
				String[] intvalue4 = {};
				for (indexCheckExt = 0; indexCheckExt < checkExtArray.size(); indexCheckExt++) {
					PrpLcheckExt prpLcheckExt1 = new PrpLcheckExt();
					prpLcheckExt1 = (PrpLcheckExt) checkExtArray.get(indexCheckExt);
					intValue = -1;
					if (prpLcheckExt1.getColumnValue() != null) {
						if (prpLcheckExt1.getColumnValue().trim().equals("1")) {
							intValue = 1;
						} else if (prpLcheckExt1.getColumnValue().trim().equals("0")) {
							intValue = 0;
						}
						//reason:查勘扩展信息用颜色间隔区分，增加一个不确定选项
						else if (prpLcheckExt1.getColumnValue().trim().equals("2"))
							intValue = 2;
						else if (prpLcheckExt1.getColumnValue().trim().equals("3"))
							intValue = 3;
						else if (prpLcheckExt1.getColumnValue().trim().equals("4"))
							intValue = 4;
						else if (prpLcheckExt1.getColumnValue().trim().equals("5"))
							intValue = 5;
						else if (prpLcheckExt1.getColumnValue().trim().equals("6"))
							intValue = 6;
						else if (prpLcheckExt1.getColumnValue().trim().equals("7"))
							intValue = 7;
						else if (prpLcheckExt1.getColumnValue().trim().equals("8"))
							intValue = 8;
						else if (prpLcheckExt1.getColumnValue().trim().equals("9"))
							intValue = 9;
						else if (prpLcheckExt1.getColumnValue().trim().equals("10"))
							intValue = 10;
						else if (prpLcheckExt1.getColumnValue().trim().equals("11"))
							intValue = 11;
						else if (prpLcheckExt1.getColumnValue().trim().equals("12"))
							intValue = 12;
						else if (prpLcheckExt1.getColumnValue().trim().equals("13"))
							intValue = 13;
						else if (prpLcheckExt1.getColumnValue().trim().equals("14"))
							intValue = 14;
						else if (prpLcheckExt1.getColumnValue().trim().equals("15"))
							intValue = 15;
					}
					if ("CheckExt04".equals(prpLcheckExt1.getId().getColumnName())) {
						intvalue1 = prpLcheckExt1.getColumnValue().split(",");
					}
					if ("CheckExt18".equals(prpLcheckExt1.getId().getColumnName())) {
						intvalue2 = prpLcheckExt1.getColumnValue().split(",");
					}
					if ("CheckExt20".equals(prpLcheckExt1.getId().getColumnName())) {
						if (prpLcheckExt1.getRemark() != null) {
							intvalue3 = prpLcheckExt1.getRemark().split(",");
						}
						if ("0".equals(prpLcheckExt1.getColumnValue())) {%>
							fm.CheckExtText201.readOnly = false;
							fm.CheckExtText202.readOnly = false; <%}
					}

					if ("CheckExt22".equals(prpLcheckExt1.getId().getColumnName())) {
						if (prpLcheckExt1.getRemark() != null) {
							intvalue4 = prpLcheckExt1.getRemark().split(",");
						}
						if ("0".equals(prpLcheckExt1.getColumnValue())) {%>
							fm.CheckExtText221.readOnly = false;
							fm.CheckExtText222.readOnly = false; <%}
					}
					if (prpLcheckExt1.getRemark() == null) {
						prpLcheckExt1.setRemark("");
					}
					if (intValue > -1) {%>
						fm. <%=prpLcheckExt1.getId().getColumnName()%> ['<%=intValue%>'].checked = true;
						<%if ("CheckExt25".equals(prpLcheckExt1.getId().getColumnName())) {%>
							fm.CheckExtText25.value = '<%=prpLcheckExt1.getRemark()%>' <%}
						if ("CheckExt26".equals(prpLcheckExt1.getId().getColumnName())) {%>
							fm.CheckExtText26.value = '<%=prpLcheckExt1.getRemark()%>' <%}
					}
					if ("CheckExt23".equals(prpLcheckExt1.getId().getColumnName())) {%>
					fm.CheckExtText23.value = '<%=DataUtils.nullToEmpty(prpLcheckExt1.getRemark())%>' <%}
					if (intvalue1.length > 0 && "CheckExt04".equals(prpLcheckExt1.getId().getColumnName())) {
						for (int i = 0; i < intvalue1.length; i++) {%>
						fm. <%=prpLcheckExt1.getId().getColumnName()%> ['<%=DataUtils.getInteger(intvalue1[i])%>'].checked = true; <%}
					}
					if (intvalue2.length > 0 && "CheckExt18".equals(prpLcheckExt1.getId().getColumnName())) {
						for (int j = 0; j < intvalue2.length; j++) {%>
						fm. <%=prpLcheckExt1.getId().getColumnName()%> ['<%=DataUtils.getInteger(intvalue2[j])%>'].checked = true; <%}
					}
					if (intvalue3.length > 0 && "CheckExt20".equals(prpLcheckExt1.getId().getColumnName())) {
						for (int j = 0; j < intvalue3.length; j++) {%>
						fm. <%="CheckExtText20" + (j + 1)%> .value = '<%=intvalue3[j]%>' <%}
					}
					if (intvalue4.length > 0 && "CheckExt22".equals(prpLcheckExt1.getId().getColumnName())) {
						for (int j = 0; j < intvalue4.length; j++) {%>
						fm. <%="CheckExtText22" + (j + 1)%> .value = '<%=intvalue4[j]%>' <%}
					}
					if ("CheckExt27".equals(prpLcheckExt1.getId().getColumnName())) {%>
					fm.CheckExtText27.value = '<%=DataUtils.nullToEmpty(prpLcheckExt1.getRemark())%>' <%}
				}
			}%>
}

/*
	 协会校验事故管界和事故地点控制
	 */

function checkSection(Address) {
	var Section = document.getElementsByName("prpLregistSection");
	var SectionName = document.getElementsByName("prpLregistSectionName");
	if (Section.length > 0 && SectionName.length > 0) {
		if (Section[0].value == null || Section[0].value == "" || SectionName[0].value == null || SectionName[0].value == "") {
			alert("请先选择事故管界!");
			Address.value = "";
			return false;
		}
	}
	if (Address.name == "prpLcheckAcciAddressCode") {
		code_CodeSelect(Address, 'AcciAddress', '0,1', 'Y', 'Y', fm.prpLregistSection.value);
	} else {
		code_CodeSelect(Address, 'AcciAddress', '-1,0', 'Y', 'N', fm.prpLregistSection.value);
	}
}
</script>
<%
	String isChecked1 = "checked";
	String isChecked2 = "";
	String display1 = (String) request.getAttribute("display1");
	String advanceType = (String) request.getAttribute("advanceType");
	String isSpecial = (String) request.getAttribute("isSpecial");

	String insuredPhoneNumber = "";
	PrpLcheck prpLchecks = (PrpLcheck) request.getAttribute("prpLcheck");
	String lossItemName = (String) request.getParameter("lossItemName");

	if (advanceType == null) {
		advanceType = "3";
	}
	if (display1 == null) {
		display1 = "display:none";
	}
	if (isSpecial == null) {
		isSpecial = "0";
	}
	if (lossItemName == null) {
		lossItemName = "";
	}
%>
<c:if test="${prpLcheck.dealFastFlag=='1'}">
	<%
		isChecked2 = "checked";
			isChecked1 = "";
	%>
</c:if>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<input type="hidden" name="referKind" value="${prpLcheck.referKind}">
				<input type="hidden" name="prpLcheckMakeCom" value="${prpLregist.makeCom}">
				<input type="hidden" name="prpLcheckRiskCode" value="${prpLcheck.riskCode}">
				<input type="hidden" name="prpLcheckCheckUnitName" value="${prpLcheck.checkUnitName}">
				<input type="hidden" name="prpLcheckFlag" value="${prpLcheck.flag}">
				<input type="hidden" name="prpLcheckReferSerialNo" value="${prpLcheck.id.referSerialNo}">
				<input type="hidden" name="prpLcheckInsureCarFlag" value="${prpLcheck.insureCarFlag}">
				<input type="hidden" name="swfLogFlowID" class="common" value="${param.swfLogFlowID}">
				<input type="hidden" name="swfLogLogNo" class="common" value="${param.swfLogLogNo}">
				<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
				<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
				<input type="hidden" name="coreURL" value="<%=AppConfig.get("sysconst.Core_URL")%>">
				<input type="hidden" name="checkNature" value="${prpLcheck.checkNature}">
				<%--          <input type="hidden" name="claimType" value="${prpLcheck.claimType}">    --%>
				<input type="hidden" name="damageAddressType" value="${prpLcheck.damageAddressType}">
				<input type="hidden" name="hasExtColumn" value="0">
				<input type="hidden" name="isSpecial" value="<%=isSpecial%>">
				<input type="hidden" name="damageDate" value="${prpLcheck.damageStartDate}">
				<%
					if (((PrpLcheckExt) request.getAttribute("prpLcheckExt")).getPrpLcheckExtList() != null) {
						if (((PrpLcheckExt) request.getAttribute("prpLcheckExt")).getPrpLcheckExtList().size() > 0) {
				%>
				<input type="hidden" name="hasExtColumn" value="1">
				<%
					}
					}
				%>
				<input type=hidden name="prpLregistComCode" title="歸屬機構" class="ReadOnly" ReadOnly style="width: 120px" value="${prpLregist.comCode}" />
				<%
					PrpLregist prpLregistForBeijing = (PrpLregist) request.getAttribute("prpLregist");
					if (prpLregistForBeijing != null && prpLregistForBeijing.getComCode().length() > 2 && "11".equals(prpLregistForBeijing.getComCode().substring(0, 2))) {
				%>
				<input type='hidden' name='MustInputFlag' value='1' />
				<%
					}
				%>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.lossItemName" />
					</td>
					<%-- 车牌号 --%>
					<td class="left">
						<input type=text name="licenseNo" class="readonly" readonly="true" value="<%=DataUtils.dbNullToEmpty(lossItemName)%>">
					</td>
					<td class="left">
						<%-- 保单号码 --%>
						<s:text name="db.prpCmain.policyNo" />：
					</td>
					<td class="right" colspan="5">
						<input type="text" name="prpLcheckPolicyNo" style="width: 40%" class="readonly" readonly="true" value="${prpLcheck.policyNo}">
						<c:if test="${not empty prpLregistRPolicyNo}">
							<input type="text" name="prpLRegistRPolicyNo" style="width: 30%" class="readonly" readonly="true" value="${prpLregistRPolicyNo.id.policyNo}">
							<c:if test="${not empty prpLregistRPolicyNo.id.policyNo}">
								<input type=button class="bigbutton" name="policyBackWard" style="width: 110px" value="<s:text name='button.mandaInsurInfo.value' />"
									onclick="relateBeforePolicyNo('${prpLregistRPolicyNo.id.policyNo}','${prpLregistRPolicyNo.riskCode}',fm.prpLcheckDamageStartDate.value);">
							</c:if>
							<%-- 强制保单信息 --%>
						</c:if>
						<input type=button class="bigbutton" style="width: 110px" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value' />"
							onclick="backWardPolicy(fm.coreURL.value,fm.prpLcheckPolicyNo.value,fm.prpLcheckRiskCode.value,fm.damageDate.value,fm.prpLregistComCode.value);">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" onclick="relate(fm.prpLcheckPolicyNo.value);return false;">
					</td>
					<%-- 出险时保单信息 --%>
				</tr>
				<tr>
					<td class="left">
						<%-- 报案号 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckRegistNo" />
					</td>
					<td class="right">
						<input type=text name="prpLcheckRegistNo" class="readonly" style="width: 100%" readonly="true" value="${prpLcheck.id.registNo}">
						<img name=btshowRegistInfo src="/claim/images/bgmore.gif" title="顯示備案訊息" onclick="relateRegist();return false;">
					</td>
					<td class="left">
						<%-- 赔案号--%>
						<s:text name="db.prpLclaim.claimNo" />：
					</td>
					<td class="right">
						<input type="text" name="prpLcheckClaimNo" class="readonly" style="width: 100%" readonly="true" value="${prpLcheck.claimNo}">
					</td>
					<!--无责垫付增加垫付赔案类型选择域  -->
					<td class="left">
						<%-- 已出险次数 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />:
					</td>
					<td class="right">
						<%-- 出险信息画面 --%>
						<%@include file="/pages/DAA/regist/DAAExistRegist.jsp"%>
					</td>
					<%--<input type="text" name="getFromPlatForm2" style="<%=display2 %>" style="color:'#9B009B'" class="readonly" value="从平台获取数据" onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';" onMouseOut="this.style.color='#9B009B';" onclick="getInfoFromPlatForm();">--%>
					<input type="text" name="getFromPlatForm1" style="<%=display1%>" style="color:'#9B009B'" class="readonly" value="輸入信息" onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';"
						onMouseOut="this.style.color='#9B009B';" onclick="inputNullInfo();">
					</td>
					<!--无责垫付增加垫付赔案类型选择域  -->
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<%
						if (prpLregistForBeijing != null && prpLregistForBeijing.getComCode().length() > 2 && "11".equals(prpLregistForBeijing.getComCode().substring(0, 2))) {
					%>
					<td class="left">
						<s:text name="db.prpLregist.section" />
					</td>
					<%-- 事故管界 --%>
					<td class="right">
						<input type=text name="prpLregistSection" class="codecode" style="width: 27%" title="事故管界" value="${section}" ondblclick="code_CodeSelect(this,'Section','0,1','Y');"
							onchange="code_CodeChange(this,'Section','0,1','Y');" onkeyup="code_CodeSelect(this,'Section','0,1','Y');">
						<input type=text name="prpLregistSectionName" class="codecode" style="width: 48%" title="事故管界" value="${sectionName}" ondblclick="code_CodeSelect(this,'Section','-1,0','Y','N');"
							onchange="code_CodeChange(this,'Section','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'Section','-1,0','Y','N');">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDamageAddress" />
					</td>
					<%-- 事故地点 --%>
					<td class="right">
						<input name="prpLcheckAcciAddressCode" class="codecode" style="width: 27%" maxlength=3 description="事故地点" value="${prpLcheck.acciAddressCode}" ondblclick="checkSection(this);"
							onchange="checkSection(this);" onkeyup="checkSection(this);">
						<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
						<input name="prpLcheckAcciAddressName" class="codename" style="width: 60%" maxlength="100" description="事故地点" value="${prpLcheck.acciAddressName}" ondblclick="checkSection(this);"
							onchange="checkSection(this);" onkeyup="checkSection(this);">
						<img src="/claim/images/bgMarkMustInput.jpg" style="display: none">
					</td>
					<%
						}
					%>
					<!--无责垫付增加垫付赔案类型选择域  -->
					<c:if test="${advance=='1'}">
						<td class="left">
							<s:text name="check.payClaimType" />
						</td>
						<%-- 垫付赔案类型 --%>
						<td class="right">
							<select name="prplregistAdvance" onchange="changeAdvanceStatus(this);">
								<option value="1" <%=advanceType.trim().equals("1") ? "selected" : ""%>>
									<s:text name="check.payResponsib" />
								</option>
								<%-- 全责垫付 --%>
								<option value="2" <%=advanceType.trim().equals("2") ? "selected" : ""%>>
									<s:text name="check.noResponsib" />
								</option>
								<%-- 无责垫付 --%>
								<option value="4" <%=advanceType.trim().equals("4") ? "selected" : ""%>></option>
								<s:text name="check.withoutAdv(doNotInteract)" />
								<%-- 无责垫付（不与平台交互） --%>
								<option value="3" <%=advanceType.trim().equals("3") ? "selected" : ""%>>
									<s:text name="check.other" />
								</option>
								<%-- 其它 --%>
							</select>
						</td>
					</c:if>
					<c:if test="${advance!='1'}">
						<td class="left"></td>
						<td class="right"></td>
					</c:if>
					<!--无责垫付增加垫付赔案类型选择域  -->
				</tr>
				<tr>
					<td class="left">
						<%--出险时间--%>
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
					</td>
					<td class="right">
						<%-- <input type="text" name="prpLcheckDamageStartDate" class="readonly" readonly="true" maxlength="10"  value="${prpLcheck.damageStartDate} 日 ${prpLcheck.damageStartHour} 时 ${prpLcheck.damageStartMinute} 分">--%>
						<rc:rcDate name="prpLcheckDamageStartDate" class="readonly" readonly="true" wdatePicker="false"
							value="${prpLcheck.damageStartDate} 日 ${prpLcheck.damageStartHour} 時 ${prpLcheck.damageStartMinute} 分" />
					</td>
					<td class="left">警方單位：</td>
					<td class="right">
						<input name='prpLcheckPoliceUnit' style="width: 80%" class='input' description="警方單位" value="${prpLcheck.policeUnit}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">警員姓名：</td>
					<td class="right" >
						<input name='prpLcheckPoliceName' style="width: 80%" class='input' description="警員姓名" value="${prpLcheck.policeName}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<%-- 事故原因 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTypeCase" />
					</td>
					<td class="right">
						<input name="prpLcheckDamageTypeCode" class="codecode" style="width: 27%" maxlength=3 description="事故原因" value="${prpLcheck.damageTypeCode}"
							ondblclick="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);" onchange="code_CodeChange(this, 'DamageTypeCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);"
							onkeyup="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);">
						<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
						<input name="prpLcheckDamageTypeName" class="codename" style="width: 44%" maxlength="100" description="事故原因" value="${prpLcheck.damageTypeName}"
							ondblclick="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);" onchange="code_CodeChange(this, 'DamageTypeCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);"
							onkeyup="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);">
						<!--  <img src="/claim/images/bgMarkMustInput.jpg">  modify by zhyi 2011-08-05 fubon-2193-->
					</td>
					<c:choose>
						<c:when test="${requestScope.prpLregist.registType!='1'}">
							<td class="left">
								<%-- 出险原因 --%>
								<s:text name="certainLoss.prpLcheck.prpLcheckDamageCase" />
							</td>
							<td class="right">
								<input name="prpLcheckDamageCode" class="codecode" style="width: 27%" maxlength=3 description="出险原因" value="${prpLcheck.damageCode}"
									ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);" onchange="code_CodeChange(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);">
								<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
								<input name="prpLcheckDamageName" class="codename" style="width: 60%" maxlength="100" description="出险原因" value="${prpLcheck.damageName}"
									ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);" onchange="code_CodeChange(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);">
								<img src="/claim/images/bgMarkMustInput.jpg">
							</td>
						</c:when>
						<c:otherwise>
							<td class="left">
								<input type="hidden" class="codecode" name="prpLcheckDamageCode" />
							</td>
							<td class="right">
								<input type="hidden" class="codecode" name="prpLcheckDamageName" />
							</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${requestScope.prpLregist.registType!='0'}">
							<td class="left">
								<%-- 强制险出险原因 --%>
								<s:text name="db.prpLclaim.damageNameBZ" />：
							</td>
							<td class="right">
								<input name="prpLcheckDamageCodeBZ" class="codecode" style="width: 27%" maxlength=3 description="出险原因" value="${prpLcheck.damageCodeBZ}"
									ondblclick="code_CodeSelect(this, 'DamageCodeBZ','0,1','Y','Y',fm.prpLcheckRiskCode.value);" onchange="code_CodeChange(this, 'DamageCodeBZ','0,1','Y','Y',fm.prpLcheckRiskCode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCodeBZ','0,1','Y','Y',fm.prpLcheckRiskCode.value);">
								<input name="prpLcheckDamageNameBZ" class="codename" style="width: 60%" maxlength=20 description="出险原因" value="${prpLcheck.damageNameBZ}"
									ondblclick="code_CodeSelect(this, 'DamageCodeBZ','-1,0','Y','N',fm.prpLcheckRiskCode.value);" onchange="code_CodeChange(this, 'DamageCodeBZ','-1,0','Y','N',fm.prpLcheckRiskCode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCodeBZ','-1,0','Y','N',fm.prpLcheckRiskCode.value);">
								<img src="/claim/images/bgMarkMustInput.jpg">
							</td>
						</c:when>
						<c:otherwise>
							<td class="left">
								<input type="hidden" class="codecode" name="prpLcheckDamageCodeBZ" />
							</td>
							<td class="right">
								<input type="hidden" class="codecode" name="prpLcheckDamageNameBZ" />
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td class="left">
						<%-- 出险区域 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageArea" />
					</td>
					<td class="right">
						<s:if test="#request.editType=='ADD'">
							<input name="prpLcheckDamageAreaCode" class="codecode" style="width: 27%" description="出险網域" value="${prpLclaim.damageAreaCode}" ondblclick="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');"
								onchange="code_CodeChange(this, 'DamageAreaCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');">
							<input name="prpLcheckDamageAreaName" class="codename" style="width: 44%" description="出险網域" value="${prpLclaim.damageAreaName}"
								ondblclick="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'DamageAreaCode','-1,0','Y','N');"
								onkeyup="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');">
						</s:if>
						<s:else>
							<input name="prpLcheckDamageAreaCode" class="codecode" style="width: 27%" description="出险網域" value="${prpLcheck.damageAreaCode}" ondblclick="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');"
								onchange="code_CodeChange(this, 'DamageAreaCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');">
							<input name="prpLcheckDamageAreaName" class="codename" style="width: 44%" description="出险網域" value="${prpLcheck.damageAreaName}"
								ondblclick="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'DamageAreaCode','-1,0','Y','N');"
								onkeyup="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');">
						</s:else>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="db.prpLregist.manageType" />：
					</td>
					<%-- 事故处理类型 --%>
					<s:if test="#request.editType!='ADD'">
						<td class="right">
							<input type=text name="prpLcheckManageType" class="codecode" style="width: 27%" title="事故處理類型" value="${prpLcheck.manageType}" ondblclick="code_CodeSelect(this,'Manage_Type','0,1','Y');"
								onchange="code_CodeChange(this,'Manage_Type','0,1','Y');" onkeyup="code_CodeSelect(this,'Manage_Type','0,1','Y');">
							<input type=text name="prpLcheckManageTypeName" class="codecode" style="width: 60%" title="事故處理類型" value="${prpLcheck.manageTypeName}"
								ondblclick="code_CodeSelect(this,'Manage_Type','-1,0','Y','N');" onchange="code_CodeChange(this,'Manage_Type','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'Manage_Type','-1,0','Y','N');">
					</s:if>
					<s:else>
						<td class="right">
							<input type=text name="prpLcheckManageType" class="codecode" style="width: 27%" title="事故處理類型" value="${prpLregist.manageType}" ondblclick="code_CodeSelect(this,'Manage_Type','0,1','Y');"
								onchange="code_CodeChange(this,'Manage_Type','0,1','Y');" onkeyup="code_CodeSelect(this,'Manage_Type','0,1','Y');">
							<input type=text name="prpLcheckManageTypeName" class="codecode" style="width: 60%" title="事故處理類型" value="${prpLregist.manageTypeName}"
								ondblclick="code_CodeSelect(this,'Manage_Type','-1,0','Y','N');" onchange="code_CodeChange(this,'Manage_Type','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'Manage_Type','-1,0','Y','N');">
					</s:else>
					<!-- add by zhyi fubon-2193 end -->
					<td class="left">
						<%-- 出险地点 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
					</td>
					<td class="right" colspan='3'>
						<input type="text" name="prpLcheckDamageAddress" class="input" value="${prpLcheck.damageAddress}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="check.dangerPaper" />:
					</td>
					<%-- 出险经过摘要 --%>
					<td class="right" colspan='7'>${registContext}</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<%-- 查勘日期--%>
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckDate" />
					</td>
					<td class="right">
						<!--点选日期到秒-->
						<s:if test="#request.editType!='SHOW'">
							<%-- <input type="text" name="prpLcheckCheckDate" class="input" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())%>"
          		onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
          		--%>
							<rc:rcDate name="prpLcheckCheckDate" class="input" defaultValue="0" format="yyyy-MM-dd HH:mm:ss" />
						</s:if>
						<s:else>
							<%-- <input type="text" name="prpLcheckCheckDate" class="input" value="<fmt:formatDate value="${prpLcheck.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
          		onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/>
          		--%>
							<rc:rcDate name="prpLcheckCheckDate" class="readonly" readonly="true" wdatePicker="false" value="${prpLcheck.checkDate}" format="yyyy-MM-dd HH:mm:ss" />
						</s:else>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<%-- 查勘地点 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckSite" />
					</td>
					<td class="right" colspan='3'>
						<input type="text" name="prpLcheckCheckSite" class="input" value="${prpLcheck.checkSite}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<!--  <td class="left">
		  <%-- 车牌号码 --%><s:text name="certainLoss.prpLcheck.lossItemName"/></td>
          <td class="right">
             <input type="text" name="lossItemName" class="readonly" readonly="true" value="${prpLcheck.lossItemName}">
            <input type="hidden" name="lossItemCode" class="readonly" readonly="true"  value="${param.lossItemCode}">
          </td>-->
					<td class="left">
						<%-- 事故责任 --%>
						<s:text name="certainLoss.prpLcheck.indemnityDuty" />
					</td>
					<td class="right">
						<%--
            <s:select name="prpLcheck" property="indemnityDuty" styleClass="three" onclick="changeIndemnityDuty();">
              <html:options collection="indemnityDutys" property="codeCode" labelProperty="codeCName"/>
            </s:select>
             --%>
						<s:select list="#request.indemnityDutys" name="indemnityDuty" listKey="id.codeCode" listValue="codeCName" value="#request.prpLcheck.indemnityDuty" />
					</td>
					<td class="left">
						<%-- 是否第一现场 --%>
						<s:text name="certainLoss.prpLcheck.firstSiteFlag" />
					</td>
					<td class="right">
						<%--
            <html:radio  name="prpLcheck" property="firstSiteFlag" value="0"/>否
        	<html:radio  name="prpLcheck.firstSiteFlag" value="1"/>是
          --%>
						<s:radio list="#{'0':'否','1':'是'}" name="firstSiteFlag" value="#request.prpLcheck.firstSiteFlag" />
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.lossItemName" />
					</td>
					<%--车牌号： --%>
					<td class="right">
						<input name='prpLcheckLiceseNo' style="width: 80%" class='input' maxlength=20 description="车牌号" value="${prpLcheck.licenseNo}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<%-- 查勘类型 --%>
						<s:text name="certainLoss.prpLcheck.checkType" />
					</td>
					<td class="right">
						<s:select list="#request.checkTypeList" name="checkType" listKey="key" listValue="value" value="#request.prpLcheck.checkType" />
					</td>
					<td class="left">
						<%-- 查勘人 1--%>
						<s:text name="certainLoss.prpLcheck.prpLcheckChecker1" />
					</td>
					<td class="right">
						<input name='prpLcheckChecker1' class='input' maxlength=20 description="查勘人1" value="${prpLcheck.checker1}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<%--查勘人 2 --%>
						<s:text name="certainLoss.prpLcheck.prpLcheckChecker2" />
					</td>
					<td class="right">
						<input name='prpLcheckChecker2' style="width: 80%" class='input' maxlength=20 description="查勘人2" value="${prpLcheck.checker2}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.insuredName" />
					</td>
					<%-- 被保险人 --%>
					<s:if test="#request.editType!='ADD'">
						<td class="right">
							<input type=text name="prpLregistLinkerName" title="被保險人" class="input" value="${prpLcheck.insuredName}">
						</td>
					</s:if>
					<s:else>
						<td class="right">
							<input type=text name="prpLregistLinkerName" title="聯系人" class="input" value="${prpCinsured.insuredName}" />
						</td>
					</s:else>
					<td class="left">
						<s:text name="db.prpLregist.insuredPhoneNumber" />
						：
					</td>
					<%-- 被保险人电话 --%>
					<td class="right">
						<%--查堪环节的”被保险人联系电话 “栏信息同报案环节自动带出，取消*设置 --%>
						<s:if test="#request.editType!='ADD'">
							<input type=text name="prpLregistPhoneNumber" title="被保險人電話" class="input" value="${prpLcheck.insuredPhoneNumber}">
						</s:if>
						<s:else>
							<input type=text name="prpLregistPhoneNumber" title="被保險人電話" class="input" value="${prpLregist.insuredPhoneNumber}">
						</s:else>
					</td>
					<td class="left">
						<s:text name="db.prpLregist.insuredMobile" />：
					</td>
					<%-- 被保险人手机 --%>
					<td class="right">
						<s:if test="#request.editType!='ADD'">
							<input type=text name="prpLregistMobile" title="被保險人手機" class="input" value="${prpLcheck.insuredMobile}">
						</s:if>
						<s:else>
							<input type=text name="prpLregistMobile" title="被保險人手機" class="input" value="${prpLregist.policyInsuredMobile}">
						</s:else>
					</td>
				</tr>
				<tr>
					<td class="left">
						<%-- 联系人地址 --%>
						<s:text name="certainLoss.prpLcheck.prpLregistInsuredAddress" />
					</td>
					<td class="right" colspan="3">
						<input type=text name="prpLregistInsuredAddress" size="100" class="ReadOnly" ReadOnly value="${prpLregist.insuredAddress}">
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.selfCompensation" />：
					</td>
					<%--互碰自赔标志 --%>
					<td class="right">
						<s:select name="payselfFlag" value="#prpLcheck.payselfFlag" listKey="key" listValue="value" list="#request.payselfFlagList" />
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="check.foundFreeName" />：
					</td>
					<%-- 报案人姓名 --%>
					<s:if test="#request.editType!='ADD'">
						<td class="right">
							<input type=text name="reportorName" title="備案人姓名" class="input" value="${prpLcheck.reportorName}" />
						</td>
					</s:if>
					<s:else>
						<td class="right">
							<input type=text name="reportorName" title="聯系人" class="input" value="${prpLregist.reportorName}" />
						</td>
					</s:else>
					<td class="left">
						<s:text name="prpLregist.reportorNumber" />：
					</td>
					<%-- 报案人电话 --%>
					<s:if test="#request.editType!='ADD'">
						<td class="right">
							<input type=text name="reportorPhoneNumber" title="備案人電話" class="input" value="${prpLcheck.reportorPhoneNumber}">
						</td>
					</s:if>
					<s:else>
						<td class="right">
							<input type=text name="reportorPhoneNumber" title="備案人電話" class="input" value="${prpLregist.reportorPhoneNumber}">
						</td>
					</s:else>
					<td class="right"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLsalvation.driverName" />：
					</td>
					<%-- 驾驶员姓名 --%>
					<s:if test="#request.editType!='ADD'">
						<td class="right">
							<input type=text name="linkerName" title="聯系人" class="input" value="${prpLcheck.linkerName}" />
						</td>
					</s:if>
					<s:else>
						<td class="right">
							<input type=text name="linkerName" title="聯系人" class="input" value="${prpLregist.linkerName}" />
						</td>
					</s:else>
					<td class="left">
						<s:text name="check.foundFreeTel" />：
					</td>
					<%-- 驾驶员电话 --%>
					<td class="right">
						<s:if test="#request.editType!='ADD'">
							<input type=text name="phoneNumber" title="聯系電話" class="input" value="${prpLcheck.phoneNumber}">
						</s:if>
						<s:else>
							<input type=text name="phoneNumber" title="聯系電話" class="input" value="${prpLregist.phoneNumber}">
						</s:else>
					</td>
					<td class="left">
						<s:text name="db.prpLregist.driverMobile" />：
					</td>
					<%-- 驾驶人手机 --%>
					<td class="right">
						<s:if test="#request.editType!='ADD'">
							<input type=text name="driverMobile" title="駕駛人手機" class="input" value="${prpLcheck.driverMobile}">
						</s:if>
						<s:else>
							<input type=text name="driverMobile" title="駕駛人手機" class="input" value="${prpLregist.driverMobile}">
						</s:else>
					</td>
				</tr>
				<tr>
					<td class="left">
						<%--联系人邮递区号 --%>
						<s:text name="certainLoss.prpLcheck.prpLregistDamageAreaPostCode" />
					</td>
					<td class="right">
						<input type=text name="prpLregistDamageAreaPostCode" class="ReadOnly" ReadOnly maxlength=6 description="出险地邮政编码" value="${prpLregist.linkerPostCode}">
					</td>
					<td class="left">
						<s:text name="check.claimType" />：
					</td>
					<%-- 赔案类型 --%>
					<td class="right">
						<s:select list="#request.claimTypes" name="claimType" listKey="id.codeCode" listValue="codeCName" value="#request.prpLregist.claimType" />
					</td>
					<c:if test="${dealFast=='1'}">
						<td class="left">
							<s:text name="check.northRapidPro" />
						</td>
						<%-- 北分快速处理 --%>
						<td class="right">
							<input type="radio" name="dealFastFlag" value="1" <%=isChecked2%>>
							是
							<input type="radio" name="dealFastFlag" value="0" <%=isChecked1%>>
							否
						</td>
					</c:if>
					<td class="right"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckRemark" />
					</td>
					<td class="right" colspan='5'>
						<input name='prpLcheckRemark' class='input' maxlength=80 style="width: 94%" value="${prpLcheck.remark}">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>