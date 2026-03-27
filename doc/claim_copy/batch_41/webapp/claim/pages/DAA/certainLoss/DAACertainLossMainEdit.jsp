<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-03-04   
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%> 
<%@ include file="/common/taglibs.jsp"%>
<script>
//检查日期输入域
function checkFullDate(field) {
	field.value = trim(field.value);
	var strValue = field.value;
	var desc = field.description;
	//如果description属性不存在，则用name属性
	if (desc == null)
		desc = field.name;
	if (strValue == "") {
		return false;
	}
	if (strValue.length != 8 && strValue.length != 10) {
		errorMessage("请输入合法的" + desc + "\n类型为日期，格式为YYYY-MM-DD 或者YYYYMMDD，不足的位要补0");
		field.focus();
		field.select();
		return false;
	}
	if (isNumeric(strValue)) {
		if (strValue.length == 8) {
			strValue = strValue.substring(0, 4) + DATE_DELIMITER + strValue.substring(4, 6) + DATE_DELIMITER + strValue.substring(6);
			field.value = strValue;
		} else {
			errorMessage("请输入合法的" + desc + "\n类型为日期，格式为YYYY-MM-DD 或者YYYYMMDD");
			field.value = "";
			field.focus();
			field.select();
			return false;
		}
	}
	if (!isDate(strValue, DATE_DELIMITER) && !isDate(strValue) || strValue.substring(0, 1) == "0") {
		errorMessage("请输入合法的" + desc + "\n类型为日期，格式为YYYY-MM-DD 或者YYYYMMDD");
		field.value = "";
		field.focus();
		field.select();
		return false;
	}
	return true;
}

//对输入域是否是数字的校验

function isNumeric(strValue) {
	var result = regExpTest(strValue, /\d*[.]?\d*/g);
	return result;
}

//RegExt Test

function regExpTest(source, re) {
	var result = false;

	if (source == null || source == "")
		return false;

	if (source == re.exec(source))
		result = true;

	return result;
}

//对输入域按键时的日期校验

function pressFullDate(e) {
	var value = String.fromCharCode(e.keyCode);
	if ((value >= 0 && value <= 9) || value == "/" || value == "-")
		return true;
	else
		return false;
}

//对输入域是否是日期的校验，splitChar参数缺省为"-"

function isDate(date, splitChar) {
	var charSplit = (splitChar == null ? "-" : splitChar);
	var strValue = date.split(charSplit);

	if (strValue.length != 3) return false;

	var intYear = parseInt(strValue[0], 10);
	var intMonth = parseInt(strValue[1], 10) - 1;
	var intDay = parseInt(strValue[2], 10);

	var dt = new Date(intYear, intMonth, intDay);
	if (dt.getFullYear() != intYear ||
		dt.getMonth() != intMonth ||
		dt.getDate() != intDay
	) {
		return false;
	}
	return true;
}

function changeLxr() {
	var prpLregistReportorName = fm.prpLregistReportorName.value;

	if (trim(fm.prpLregistLinkerName.value).length == 0) {
		fm.prpLregistLinkerName.value = prpLregistReportorName;
	}
}
</script>
<%
	//取核心系统的地址
	String coreWebUrl = AppConfig.get("sysconst.Core_URL");
%>
<script language="javascript">
  /**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */   
 function relatePolicy() {
		var policyNo = fm.prplCheckPolicyNoShow.value;
		var linkURL = "<%=coreWebUrl%>" + "/0501/tbcbpg/UIPrPoEn0501Show.jsp?" + "BIZTYPE=POLICY" + "&BizNo=" + policyNo + "&SHOWTYPE=SHOW";
		var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
	}
</script>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td class="title" colspan="4" style="display: none;">
			<s:text name="certainLoss.lossOfRegistration" />
			<!--定损登记-->
			<input type="hidden" name="coreURL" value="<%=coreWebUrl%>" />
			<input type="hidden" name="prpLverifyLossClaimNo" value="${prpLverifyLoss.claimNo}" />
			<input type="hidden" name="prpLverifyLossRiskCode" value="${prpLverifyLoss.riskCode}" />
			<input type="hidden" name="prpLverifyLossLicenseColorcode" value="${prpLverifyLoss.licenseColorCode}" />
			<input type="hidden" name="prpLverifyLossCarKindCode" value="${prpLverifyLoss.carKindCode}" />
			<input type="hidden" name="prpLverifyLossSumPreDefLoss" value="${prpLverifyLoss.sumPreDefLoss}">
			<input type="hidden" name="prpLverifyLossSumDefLoss" value="${prpLverifyLoss.sumDefLoss}" />
			<input type="hidden" name="prpLverifyLossMakeCom" value="${prpLverifyLoss.makeCom}" />
			<input type="hidden" name="prpLverifyLossComCode" value="${prpLverifyLoss.comCode}" />
			<input type="hidden" name="prpLverifyLossUnderWriteCode" value="${prpLverifyLoss.underWriteCode}" />
			<input type="hidden" name="prpLverifyLossUnderWriteName" value="${prpLverifyLoss.underWriteName}" />
			<input type="hidden" name="prpLverifyLossUnderWriteEndDate" value="${prpLverifyLoss.underWriteEndDate}" />
			<input type="hidden" name="prpLverifyLossUnderWriteFlag" value="${prpLverifyLoss.underWriteFlag}" />
			<input type="hidden" name="prpLverifyLossFlag" value="${prpLverifyLoss.flag}" />
			<input type="hidden" name="prpLverifyLossLossItemCode" value="${prpLverifyLoss.id.lossItemCode}" />
			<input type="hidden" name="prpLverifyLossNodeType" value="${prpLverifyLoss.id.nodeType}" />
			<input type="hidden" name="prpLverifyLossLossItemName" value="${prpLverifyLoss.lossItemName}" />
			<input type="hidden" name="prpLverifyLossInsureCarFlag" value="${prpLverifyLoss.insureCarFlag}" />
			<input type="hidden" name="prpLverifyLossRegistNo" value="${prpLverifyLoss.id.registNo}" />
			<input type="hidden" name="prpLverifyLossPolicyNo" value="${prpLverifyLoss.policyNo}" style="width: 140px" />
			<input type="hidden" name="prpLverifyLossInsuredName" value="${prpLverifyLoss.insuredName}" />
			<input type="hidden" name="prpLverifyLossLicenseNo" value="${prpLverifyLoss.licenseNo}" style="width: 140px" />
			<input type="hidden" name="prpLverifyLossLicenseColor" value="${prpLverifyLoss.licenseColor}" />
			<input type="hidden" name="prpLverifyLossCarKind" value="${prpLverifyLoss.carKind}" style="width: 140px" />
			<input type="hidden" name="prpLverifyLossClauseName" value="${prpLverifyLoss.clauseName}" />
			<input type="hidden" name="prpLverifyLossCurrencyName" value="${prpLverifyLoss.currencyName}" style="width: 140px" />
			<input type="hidden" name="prpLverifyLossCurrency" value="${prpLverifyLoss.currency}" />
			<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}" />
			<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}" />
			<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
			<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
			<input type="hidden" name="damageStartDate" value="${requestScope.prpLregist.damageStartDate}" />
			<input type="hidden" name="damageStartHour" value="${requestScope.prpLregist.damageStartHour}" />
			<input type="hidden" name="nextNodeNo" value="verif" />
			<%--
				//reason :增加理算退回定损的环节
			--%>
			<input type="hidden" name="prpLverifyLossCompensateFlag" value="${prpLverifyLoss.compensateFlag}" />
			<input type="hidden" name="prpLverifyLossCompensateOpinion" value="${prpLverifyLoss.compensateOpinion}" />
			<input type="hidden" name="prpLverifyLossCompensateBackDate" value="${prpLverifyLoss.compensateBackDate}" />
			<input type="hidden" name="prpLverifyLossCompensateApproverCode" value="${prpLverifyLoss.compensateApproverCode}" />
			<input type="hidden" name="prpLverifyLossStatus" value="${prpLverifyLoss.status}" />
			<%--
				//reason :此标志为1，说明增加了新的一条记录，所以不能直接提交到理算了，照常走
			--%>
			<input type="hidden" name="NextComeBackCompensate" />
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="db.prpLcomponent.policyNo" />：
					</td>
					<!--保单号-->
					<td class="right">
						<input type="text" name="prplCheckPolicyNoShow" style="width: 100%" value="${requestScope.prpLregist.policyNo}" class="readonly" readonly="true">
						<input type="button" name="btPolicyRelate" value="<s:text name='button.InsuranceInformation.value'/>" class='bigbutton'
							onclick="relateBeforePolicyNo('${requestScope.prpLregist.policyNo}','${requestScope.prpLregist.riskCode}','${requestScope.prpLregist.damageStartDate}');">
						<!--保单信息-->
					</td>
					<td class="left">
						<s:text name="certainLoss.claims" />：
					</td>
					<!--赔案号-->
					<td class="right">
						<input type="text" name="prplCheckClaimNoShow" class="readonly" readonly="true" style="width: 150px;" value="${prpLcheckTemp.claimNo}">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" onclick="relate(fm.prplCheckPolicyNoShow.value,fm.RegistNo.value);return false;">
					</td>
					<td class="left">
						<s:text name="db.prpLCItemCar.registNo" />：
					</td>
					<!--报案号-->
					<td class="right">
						<input type="text" name="prplCheckRegistNoShow" style="width: 100%" class="readonly" readonly="true" value="${requestScope.prpLregist.registNo}">
						<input type="button" name="btRegistRelate" value="<s:text name='button.reportedInformation.value'/>" class='bigbutton' onclick="relateRegist();return false;">
						<!--报案信息-->
					</td>
				</tr>
				<c:if test="${prpLregistRPolicyNo!=null}">
					<tr>
						<td class="left">
							<s:text name="certainLoss.policyNumber" />：
						</td>
						<!--强制保单号-->
						<td class="right" colspan="5">
							<input type="text" name="prplCheckPolicyBzNoShow" class="readonly" style="width: 150px;" readonly="true" value="${prpLregistRPolicyNo.id.policyNo}">
							<input type="button" name="btPolicyRelate" value="<s:text name='button.policyNumber.value'/>" class='bigbutton'
								onclick="relateBeforePolicyNo('${prpLregistRPolicyNo.id.policyNo}','${prpLregistRPolicyNo.riskCode}','${requestScope.prpLregist.damageStartDate}');">
							<!--强制保单信息-->
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
					</td>
					<!--已出险次数-->
					<td class="right">
						<%--
							/** 出险信息画面 */
						--%>
						<%@include file="/pages/DAA/regist/DAAExistRegist.jsp"%>
					</td>
					<%--
						/**无责垫付增加垫付赔案类型选择域  */
					--%>
					<c:choose>
						<c:when test="${advance =='1'}">
							<td class="left">
								<s:text name="certainLoss.payType" />
							</td>
							<!--垫付赔案类型-->
							<td class="right">
								<select name="prplregistAdvance" onchange="changeAdvanceStatus(this);">
									<option value="1" <c:if test="${fn:trim(advanceType)=='1'}"><c:out value="selected" /></c:if>>
										<s:text name="certainLoss.fullAdvance" />
									</option>
									<!--全责垫付-->
									<option value="2" <c:if test="${fn:trim(advanceType)=='2'}"><c:out value="selected" /></c:if>>
										<s:text name="certainLoss.notAdvance" />
									</option>
									<!--无责垫付-->
									<option value="4" <c:if test="${fn:trim(advanceType)=='4'}"><c:out value="selected" /></c:if>>
										<s:text name="certainLoss.notPlatform" />
									</option>
									<!--无责垫付（不与平台交互-->
									<option value="3" <c:if test="${advanceType == null ||fn:trim(advanceType)=='3'}"><c:out value="selected" /></c:if>>
										<s:text name="certainLoss.other" />
									</option>
									<!--其它-->
								</select>
							</td>
							<td class="left">
								<input type="text" name="getFromPlatForm" style="${display1==null?'display:none':display1}" style="color:'#9B009B'" class="readonly" value="<s:text name='certainLoss.entryInformation'/>"
									onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';" onMouseOut="this.style.color='#9B009B';" onclick="inputNullInfo();">
								<!--录入信息-->
							</td>
							<td class="right"></td>
						</c:when>
						<c:otherwise>
							<td class="left"></td>
							<td class="right"></td>
							<td class="left"></td>
							<td class="right"></td>
						</c:otherwise>
					</c:choose>
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
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
					</td>
					<!--出险时间-->
					<td class="right">
						<%-- <input type=text name="prpLregistDamageStartDate1" class="readonly" style="width:40%" readonly maxlength="10" value="${requestScope.prpLregist.damageStartDate}"><s:text name="title.registBeforeEdit.damage"/>日--%>
						<rc:rcDate name="prpLregistDamageStartDate1" class="readonly" readonly="true" wdatePicker="false" style="width:90px;" value="${requestScope.prpLregist.damageStartDate}" />
						日
						<input type=text name="prpLregistDamageStartHour1" class="readonly" style="width: 10%" readonly maxlength="2" value="${requestScope.prpLregist.damageStartHour}">
						時
						<input type=text name="prpLregistDamageStartMinute1" class="readonly" style="width: 10%" readonly maxlength="2" value="${requestScope.prpLregist.damageStartMinute}">
						分
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
					</td>
					<!--出险地点-->
					<td class="right">
						<input type=text name="prpLregistDamageAddress" class="readonly" readonly value="${requestScope.prpLregist.damageAddress}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="check.surveyTtime" />：
					</td>
					<!--查勘时间-->
					<td class="right">
						<%--  <input type=text name="prpLregistcheckDate" class="readonly" readonly value="${prpLcheckTemp.checkDate}">--%>
						<rc:rcDate name="prpLregistcheckDate" class="readonly" readonly="true" wdatePicker="false" value="${prpLcheckTemp.checkDate}" />
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckSite" />
					</td>
					<!--查勘地点-->
					<td class="right">
						<input type=text name="prpLregistcheckSite" class="readonly" readonly value="${prpLcheckTemp.checkSite}">
					</td>
					<td class="left" style="width: 13%">
						<s:text name="certainLoss.firstSite" />：
					</td>
					<!--是否第一现场查勘-->
					<td class="right">
						<c:if test="${prpLcheckTemp.firstSiteFlag=='0'}">
							<s:text name="certainLoss.thirdCarLoss.no" />
						</c:if>
						<!--否-->
						<c:if test="${prpLcheckTemp.firstSiteFlag=='1'}">
							<s:text name="certainLoss.thirdCarLoss.yes" />
						</c:if>
						<!--是-->
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.person1" />：
					</td>
					<!--查勘人1-->
					<td class="right">${prpLcheckTemp.checker1}</td>
					<td class="left">
						<s:text name="certainLoss.person2" />：
					</td>
					<!--查勘人2-->
					<td class="right">${prpLcheckTemp.checker2}</td>
					<td class="left"></td>
					<td class="right">
						<input type="button" class='bigbutton' value="<s:text name='button.viewInformation.value'/>" onclick="relateCheck();return false;">
					</td>
					<!--查看查勘信息-->
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
						<s:text name="certainLoss.lossTime" />：
					</td>
					<!--定损时间-->
					<td class="right">
						<rc:rcDate name="prpLverifyLossDefLossDate" class="readonly" readonly="true" wdatePicker="false" title="定損日期" value="${prpLverifyLoss.defLossDate}" />
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLscheduleMainWF.lossPerson" />
					</td>
					<!--定损人员-->
					<td class="right">
						<input name="prpLverifyLossHandlerCode" class="codecode" readonly style="width: 27%" value="${prpLverifyLoss.handlerCode}">
						<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
						<input name="prpLverifyLossHandlerName" class='codename' readonly maxlength="100" style="width: 48%" value="${prpLverifyLoss.handlerName}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<input type="hidden" name="prpLverifyLossFirstDefLoss" value="<fmt:formatNumber value="${prpLverifyLoss.firstDefLoss}" pattern="#" />">
<input type="hidden" name="prpLverifyLossWarpDefLoss" value="<fmt:formatNumber value="${prpLverifyLoss.warpDefLoss}" pattern="#" />">
<input type="hidden" name="PolicyNo" value="${requestScope.prpLregist.policyNo}">
<input type="hidden" name="RegistNo" value="${requestScope.prpLregist.registNo}">
<%--
	/** 5.报案信息补充说明 */
--%>
<%@include file="/pages/DAA/regist/DAARegistExtEdit.jsp"%>

