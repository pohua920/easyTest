<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-01-17
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<script src="${ctx}/pages/DAA/regist/js/DAARegistMainEdit.js"></script>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<div style="background-color: #ffffff">
				<table class=common cellpadding="1" cellspacing="1">
					<p align="center">
						<c:if test="${not empty prpLregist.cancelDate}">
							<s:text name="regist.prpLregist.cancellation" />
						</c:if>
					</p>
					<input type="hidden" name="qsFlag" value="${qsFlag}">
					<input type=hidden name="qs_prpLregistStartDate" class="readonly" style="width: 80px" readonly="true" value="<fmt:formatDate value="${qs_prpCmainDto.startDate}" pattern="yyyy-MM-dd"/>">
					<input type=hidden name="qs_prpLregistEndDate" class="readonly" style="width: 80px" readonly="true" value="<fmt:formatDate value="${qs_prpCmainDto.endDate}" pattern="yyyy-MM-dd"/>">
					<input type="hidden" name="qs_prpLregistPayFee" value="${intPayFee}">
					<input type="hidden" name="qs_PerilCount" value="${intPerilCount}">
					<input type="hidden" name="qs_RecentCount" value="${intRecentCount}">
					<input type="hidden" name="quaryPolicyNo" value="${quaryPolicyNo}">
					<!--强三 -->
					<input type="hidden" name="prpLregistLFlag" value="${prpLregist.lflag}">
					<input type="hidden" name="prpLregistRiskCode" value="${prpLregist.riskCode}">
					<input type="hidden" name="prpLregistClassCode" value="${prpLregist.classCode}">
					<input type="hidden" name="prpLregistLanguage" value="${prpLregist.language}">
					<input type="hidden" name="prpLregistEstiCurrency" value="${prpLregist.estiCurrency}">
					<input type="hidden" name="prpLregistLicenseNo" value="${prpLregist.licenseNo}">
					<input type="hidden" name="prpLregistLicenseColorCode" value="${prpLregist.licenseColorCode}">
					<input type="hidden" name="prpLregistCarKindCode" value="${prpLregist.carKindCode}">
					<input type="hidden" name="prpLregistModelCode" value="${prpLregist.modelCode}">
					<input type="hidden" name="prpLregistEngineNo" value="${prpLregist.engineNo}">
					<input type="hidden" name="prpLregistFrameNo" value="${prpLregist.frameNo}">
					<input type="hidden" name="prpLregistRunDistance" value="${prpLregist.runDistance}">
					<input type="hidden" name="prpLregistUseYears" value="${prpLregist.useYears}">
					<input type="hidden" name="prpLregistBrandName" value="${prpLregist.brandName}">
					<input type="hidden" name="prpLregistTypeForDriver" value="Regist">
					<input type="hidden" name='prpLregistEditType' value="${prpLregist.editType}">
					<input type="hidden" name='prpLregistDrivingLicenseNo'>
					<input type="hidden" name='prpLregistDrivingName'>
					<input type="hidden" name='prpLregistDrivingSex'>
					<input type="hidden" name='prpLregistDrivingIdentifyNumber'>
					<input type="hidden" name='prpLregistDrivingAge'>
					<input type="hidden" name='prpLregistDrivingOccupation'>
					<input type="hidden" name='prpLregistDrivingOccupationName'>
					<input type="hidden" name='prpLregistDrivingEducation'>
					<input type="hidden" name='prpLregistDrivingEducationName'>
					<input type="hidden" name='prpLregistLossName' value="${prpLregist.lossName}">
					<input type="hidden" name='prpLregistDrivingUnitAddress'>
					<input type="hidden" name='prpLregistDrivingReceiveLicenseDate'>
					<input type="hidden" name='prpLregistDrivingCarType'>
					<input type="hidden" name='prpLregistDrivingAwardLicenseOrgan'>
					<input type="hidden" name="coreURL" value="<%=AppConfig.get("sysconst.Core_URL")%>">
					<%--加入报案出险延期天数 --%>
					<c:if test="${not empty configValue }">
						<input type="hidden" name='configValue' value="${configValue}">
					</c:if>
					<c:if test="${empty configValue}">
						<input type="hidden" name='configValue' value="99999">
					</c:if>
					<input type="hidden" name='riskcode' value="${prpLregist.riskCode}">
					<input type="hidden" name='policyno' value="${prpLregist.policyNo}">
					<input type="hidden" name='prpLregistPayFee' value="${prpLregist.payFlag}">
					<input type="hidden" name="RecentCount" value="${prpLregistDto1.recentCount}">
					<input type="hidden" name="RegistViewLimitDay" value="<%=AppConfig.get("sysconst.RegistViewLimitDay")%>">
					<input type="hidden" name="prpLregistFlowInTime" value="${prpLregist.flowInTime}">
					<input type="hidden" name="prpLregistSignDate" value="${prpLregist.signDate}">
					<input type="hidden" name="prpLregistUnderWriteEndDate" value="${prpLregist.underWriteEndDate}">
					<input type="hidden" name="prpLregistOthFlag" value="${prpLregist.othFlag}">
					<input type=hidden name="prpLregistOperatorCode" value="${prpLregist.operatorCode}">
					<input type=hidden name="prpLregistOperatorName" value="${prpLregist.operatorName}">
					<input type=hidden name="prpLregistMakeCom" value="${prpLregist.makeCom}">
					<input type=hidden name="prpLregistMakeComName" value="${prpLregist.makeComName}">
					<input type=hidden name="prpLregistInputDate" value="${prpLregist.inputDate}">
					<input type="hidden" name="acceptFlag" value="Y">
					<input type="hidden" name="repeatInsureFlag" value="N">
					<input type=hidden name="prpLregistEstimateFee" Class="input" style="width: 80px" value="${prpLregist.estimateFee}">
					<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
					<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
					<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
					<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
					<%-- 保单停效标志 等於54为停效--%>
					<input type="hidden" name="endorType" value="${endorType}">
					<input type="hidden" name="originalRequestURITemp" value="${sessionScope.originalRequestURITemp}">
					<s:set name="flagYes" value="%{getText('regist.prpLregist.yes')}"></s:set>
					<s:set name="flagNo" value="%{getText('regist.prpLregist.no')}"></s:set>
					<tr>
						<td class="left">
							<s:text name="db.prpLregist.registNo" />
							：
							<%-- 備案號碼 --%>
						</td>
						<td class="right" colspan="2">
							<input type="text" name="prpLregistRegistNo" class="readonly" readonly="true" value="${prpLregist.registNo}">
						</td>
						<c:choose>
							<c:when test="${requestScope.prpLregist.registType!='0'}">
								<td class="left">
									<s:text name="regist.prpLregist.sharingFlag" />
									：
									<%-- 同業共摊 --%>
								</td>
								<td class="right" colspan="2">
									<s:set name="flagYes" value="%{getText('regist.prpLregist.yes')}"></s:set>
									<s:set name="flagNo" value="%{getText('regist.prpLregist.no')}"></s:set>
									<%-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 --%>
									<s:radio list="#{'0':#flagNo,'1':#flagYes}" name="prpLregistSharingFlag" value="#request.prpLregist.sharingFlag" disabled="#request.registSharingFlagDisabled"/>
								</td>
							</c:when>
							<c:otherwise>
								<td class="left"></td>
								<td class="right" colspan="2">
									<input name="prpLregistSharingFlag" type="hidden" value="0">
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<td class="left">
							<s:text name="regist.prpLregist.registTime" />
							：
							<%-- 備案時間 --%>
						</td>
						<td class="right" >
							<%--  <input type="text" name="prpLregistReportDate" class="input"  maxlength="10" style="width:82px"  value="${prpLregist.reportDate}"><s:text name="regist.prpLregist.date"/>--%>
							<rc:rcDate name="prpLregistReportDate" title="備案日期" style="width:100px" value="${prpLregist.reportDate}" onchange="flashPage(this);"/>
							<s:text name="regist.prpLregist.date" />
							<input type="text" name="prpLregistReportHour" title="備案小時" class="input" maxlength="2" style="width: 25px" value="${prpLregist.reportHour}"  onchange="flashPage(this);">
							<s:text name="regist.prpLregist.hour" />
							<input type="text" name="prpLregistReportMinute" title="備案分鐘" class="input" maxlength="2" style="width: 25px" value="${prpLregist.reportMinute}" onchange="flashPage(this);">
							<s:text name="regist.prpLregist.minute" /><img src="/claim/images/bgMarkMustInput.jpg">
							<input type=hidden name="prpLregistDamageAreaPostCode" value="${prpLregist.damageAreaPostCode}">
						</td>
						<td class="left">
							<s:text name="db.prpLregist.reportType" />
							：
							<%-- 備案方式 --%>
						</td>
						<td class="right">
							<s:select name="reportType" list="#request.reportTypes" listKey="id.codeCode" listValue="codeCName" value="#request.prpLregist.reportType" />
						</td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="db.prpLregist.reportorName" />
							：
							<%-- 備案人 --%>
						</td>
						<td class="right">
							<div id="ad" style="display: ''">
								<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
								<input type="text" onchange='changeLxr();' name="prpLregistReportorName" class="input" maxlength="100" value="${prpLregist.reportorName}">
								<img src="${ctx}/images/bgMarkMustInput.jpg">
							</div>
						</td>
						<td class="left">
							<s:text name="prpLregist.reportorMobile" />
							：
							<%-- 備案人市話 --%>
						</td>
						<td class="right">
							<input type="text" onchange="changeReportorPhoneNumber()" id="prpLregistReportorPhoneNumber" name="prpLregistReportorPhoneNumber" class="input" maxlength="20"
								value="${prpLregist.reportorPhoneNumber}">
								<%--  mantis： CLM0001，處理人員：David，需求單編號：CLM0001 新增必填圖示--%>
								<img src="${ctx}/images/bgMarkMustInput.jpg">
						</td>
						<td class="left">
							備案人手機 ：
							<%-- 備案人手機 --%>
						</td>
						<td class="right">
							<input type="text" onchange="changeReportorMobile()" id="prpLregistReportorMobile" name="prpLregistReportorMobile" class="input" maxlength="20" value="${prpLregist.reportorMobile}">
						</td>
					</tr>
					<tr>
						<td class="left">
							駕駛人姓名 ：
							<%-- 駕駛人姓名 --%>
						</td>
						<td class="right">
							<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
							<input type="text" onchange="changeDriverInfo()" name="prpLregistLinkerName" class="input" maxlength="100" value="${prpLregist.linkerName}" />&nbsp;<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
						<td class="left">
							駕駛人電話 ：
							<%-- 駕駛人市話 --%>
						</td>
						<td class="right">
							<input type="text" onchange="changeDriverInfo()" name="prpLregistPhoneNumber" class="input" value="${prpLregist.phoneNumber}" maxlength="20">
						</td>
						<td class="left">
							駕駛人手機 ：
							<%-- 駕駛人手機 --%>
						</td>
						<td class="right">
							<input type="text" onchange="changeDriverInfo()" name="prpLregistDriverMobile" class="input" value="${prpLregist.driverMobile}" maxlength="20">
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="db.prpLregist.insuredPhoneNumber" />
							：
						</td>
						<td class="right">
							<input type="text" name="prpLregistInsuredPhoneNumber" class="input" maxlength="12" value="${prpLregist.insuredPhoneNumber}">
						</td>
						<td class="left">
							<s:text name="prpLregist.relationShip" />
							：
							<%-- 備案人與被保險人關係 --%>
						</td>
						<!-- mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START -->
						<td class="right" colspan="${request.isCompulsoryBchainClaimDisabled=='true'?3:1}">
							<s:select name="prpLregistRelationType" value="#request.prpLregist.relationType" listKey="key" listValue="value" list="#request.relationTypeList" />
						</td>
						<c:if test="${!request.isCompulsoryBchainClaimDisabled}">
						<td class="left">
							是否為強制險區塊鏈攤賠案件 ：
							<%-- 是否為強制險區塊鏈攤賠案件 --%>
						</td><!-- Y不卡 -->
						<td class="right"><!-- 當勾選【是】，於備案按下【提交】後，不卡控【人傷跟蹤訊息】的必填欄位填寫，且不需呼叫區塊鏈檢核。 -->
							<s:radio list="#{'N':'否','Y':'是'}" name="prpLregistIsCompulsoryBchainClaim" value="#request.prpLregist.isCompulsoryBchainClaim"/>
						</td>
						</c:if>
						<!-- mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END -->
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<div style="background-color: #ffffff">
				<table class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left">
							<s:text name="db.prpLregist.damageAddress" />
							：
						</td>
						<td class="right" colspan="5">
							<input type="text" name="prpLregistDamageAddress" style="width: 70%" Class="input" value="${prpLregist.damageAddress}">
							<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="regist.prpLregist.damageTime" />
							：
						</td>
						<td class="right">
							<script type="text/javascript">
								function flashPage(field) {
									if(checkRegistTime(field)){
										//mantis： CLM0187，處理人員：CD078，需求單編號：CLM0187.新核心-備案登記處理調整出險日期畫面重整確認
										return ;
										var damageDate = fm.prpLregistDamageStartDate.value;
										var damageHour = fm.prpLregistDamageStartHour.value;
										var damageMinute = fm.prpLregistDamageStartMinute.value;
										var vURL = "";
										if("${param.editType}"=="PERFECT"){
											vURL = "${ctx}/regist/registBeforeEdit.do?editType=PERFECT&prpLregistRegistNo=${param.prpLregistRegistNo}&prpCmainPolicyNo=${param.prpCmainPolicyNo}&&damageDate="+damageDate
													+"&damageHour="+damageHour+"&flushflag=true";
										} else if("${param.editType}"=="ADD"){
											vURL = "${ctx}/registBeforeEdit.do?prpCmainPolicyNo=${param.prpCmainPolicyNo}&editType=ADD&damageDate="+damageDate+"&damageHour="+damageHour+"&flushflag=true";
										} else {
											vURL = "${ctx}/registFinishQueryList.do?prpLregistRegistNo=${param.prpLregistRegistNo}&updateExt=true&swfLogFlowID=${param.swfLogFlowID}&swfLogLogNo=${param.swfLogLogNo}"
													+"&status=${param.status}&riskCode=${param.riskCode}&editType=${param.editType}&nodeType=${param.nodeType}&businessNo=${param.businessNo}&keyIn=${param.keyIn}"
													+"&policyNo=${param.policyNo}&modelNo=${param.modelNo}&nodeNo=${param.nodeNo}&dfFlag=${param.dfFlag}&actorId=${param.actorId}&processId=${param.processId}&flushflag=true"
													+"&damageDate="+damageDate+"&damageHour="+damageHour;
										}
										fm.action = vURL;
										fm.prpLthirdPartyLicenseNo[1].disabled = false;
										fm.prpLthirdPartyBrandName[1].disabled = false;
										fm.carKindCode[1].disabled = false;
										fm.licenseColorCode[1].disabled = false;
										fm.submit();
										return true;
									}
								}
							</script>
							<c:choose>
								<c:when test="${editType == 'PERFECT'}">
									<rc:rcDate name="prpLregistDamageStartDate" title="出險日期" style="width:100px" value="${prpLregist.damageStartDate}" class="readonly" readonly="true" wdatePicker="false"/>
									<s:text name="regist.prpLregist.date" />
									<input type="text" name="prpLregistDamageStartHour" title="出險小時" class="readonly" readonly="readonly" maxlength="2" style="width: 25px" value="${prpLregist.damageStartHour}" >
									<s:text name="regist.prpLregist.hour" />
									<input type="text" name="prpLregistDamageStartMinute" title="出險分鐘" class="readonly" readonly="readonly" maxlength="2" style="width: 25px" value="${prpLregist.damageStartMinute}" >
									分 <img src="${ctx}/images/bgMarkMustInput.jpg">
								</c:when>
								<c:otherwise>
									<rc:rcDate name="prpLregistDamageStartDate" title="出險日期" style="width:100px" value="${prpLregist.damageStartDate}" onkeypress="pressFullDate(fm.prpLregistDamageStartDate)" onblur="checkFullDate(fm.prpLregistDamageStartDate)" onchange="flashPage(this);" />
									<s:text name="regist.prpLregist.date" />
									<input type="text" name="prpLregistDamageStartHour" title="出險小時" class="input" maxlength="2" style="width: 25px" value="${prpLregist.damageStartHour}" onchange="flashPage(this);">
									<s:text name="regist.prpLregist.hour" />
									<input type="text" name="prpLregistDamageStartMinute" title="出險分鐘" class="input" maxlength="2" style="width: 25px" value="${prpLregist.damageStartMinute}" onchange="flashPage(this);">
									分 <img src="${ctx}/images/bgMarkMustInput.jpg">
								</c:otherwise>
							</c:choose>
						</td>
						<c:choose>
							<c:when test="${requestScope.prpLregist.registType!='1'}">
								<td class="left">
									任意險出險原因 ：
									<%-- 任意險出險原因 --%>
								</td>
								<td class="right">
									<input type="text" class="codecode" name="prpLregistDamageCode" style="width: 27%" value="${prpLregist.damageCode}"
										ondblclick="code_CodeSelect(this,'DamageCode','0,1','Y','Y',fm.prpLregistRiskCode.value);" onchange="code_CodeChange(this,'DamageCode','0,1','Y','Y',fm.prpLregistRiskCode.value);"
										onkeyup="code_CodeSelect(this,'DamageCode','0,1','Y','Y',fm.prpLregistRiskCode.value);">
									<input type="text" class="codecode" name="prpLregistDamageName" style="width: 48%" value="${prpLregist.damageName}"
										ondblclick="code_CodeSelect(this,'DamageCode','-1,0','Y','N',fm.prpLregistRiskCode.value);" onchange="code_CodeChange(this,'DamageCode','-1,0','Y','N',fm.prpLregistRiskCode.value);"
										onkeyup="code_CodeSelect(this,'DamageCode','-1,0','Y','N',fm.prpLregistRiskCode.value);">
									<img src="/claim/images/bgMarkMustInput.jpg">
								</td>
							</c:when>
							<c:otherwise>
								<td class="left">
									<input type="hidden" class="codecode" name="prpLregistDamageCode" />
								</td>
								<td class="right">
									<input type="hidden" class="codecode" name="prpLregistDamageName" />
								</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${requestScope.prpLregist.registType!='0'}">
								<td class="left">
									<s:text name="db.prpLregist.damageNameBZ" />
									：
									<%-- 強制險出險原因 --%>
								</td>
								<td class="right">
									<input type="text" class="codecode" name="prpLregistDamageCodeBZ" style="width: 27%" value="${prpLregist.damageCodeBZ}"
										ondblclick="code_CodeSelect(this,'DamageCodeBZ','0,1','Y','Y',fm.prpLregistRiskCode.value);" onchange="code_CodeChange(this,'DamageCodeBZ','0,1','Y','Y',fm.prpLregistRiskCode.value);"
										onkeyup="code_CodeSelect(this,'DamageCodeBZ','0,1','Y','Y',fm.prpLregistRiskCode.value);">
									<input type="text" class="codecode" name="prpLregistDamageNameBZ" style="width: 48%" value="${prpLregist.damageNameBZ}"
										ondblclick="code_CodeSelect(this,'DamageCodeBZ','-1,0','Y','N',fm.prpLregistRiskCode.value);" onchange="code_CodeChange(this,'DamageCodeBZ','-1,0','Y','N',fm.prpLregistRiskCode.value);"
										onkeyup="code_CodeSelect(this,'DamageCodeBZ','-1,0','Y','N',fm.prpLregistRiskCode.value);">
									<img src="/claim/images/bgMarkMustInput.jpg">
								</td>
							</c:when>
							<c:otherwise>
								<td class="left">
									<input type="hidden" class="codecode" name="prpLregistDamageCodeBZ" />
								</td>
								<td class="right">
									<input type="hidden" class="codecode" name="prpLregistDamageNameBZ" />
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<div style="background-color: #ffffff">
				<table class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left">
							<s:text name="db.prpLregist.receiverName" />
							：
							<%-- 接案人 --%>
						</td>
						<td class="right">
							<input type=hidden name="prpLregistHandlerCode" value="${prpLregist.handlerCode}">
							<input type="text" name="prpLregistReceiverName" class="codecode" style="width: 79%" value="${prpLregist.receiverName}" ondblclick="code_CodeSelect(this,'handerCode','-1,0','Y','N','ALL');"
								onchange="code_CodeChange(this,'handerCode','-1,0','Y','N','ALL');" onkeyup="code_CodeSelect(this,'handerCode','-1,0','Y','N','ALL');">
							&nbsp; <img src="/claim/images/bgMarkMustInput.jpg">
						</td>
						<td class="left">
							<s:text name="db.prpLregist.handleUnit" />
							：
							<%-- 事故處理部門 --%>
						</td>
						<td class="right">
							<input type="text" name="prpLregistHandleUnit" class="codecode" style="width: 27%" value="${prpLregist.handleUnit}" ondblclick="code_CodeSelect(this,'HandleUnit','0,1','Y');"
								onchange="code_CodeChange(this,'HandleUnit','0,1','Y');" onkeyup="code_CodeSelect(this,'HandleUnit','0,1','Y');">
							<input type="text" name="prpLregistHandleUnitName" class="codecode" style="width: 48%" value="${prpLregist.handleUnitName}" ondblclick="code_CodeSelect(this,'HandleUnit','-1,0','Y','N');"
								onchange="code_CodeChange(this,'HandleUnit','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'HandleUnit','-1,0','Y','N');">
							<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
						<td class="left">
							<s:text name="db.prpLregist.manageType" />
							：
							<%-- 事故處理類型 --%>
						</td>
						<td class="right">
							<input type="text" name="prpLregistManageType" class="codecode" style="width: 27%" value="${prpLregist.manageType}" ondblclick="code_CodeSelect(this,'Manage_Type','0,1','Y');"
								onchange="code_CodeChange(this,'Manage_Type','0,1','Y');" onkeyup="code_CodeSelect(this,'Manage_Type','0,1','Y');">
							<input type="text" name="prpLregistManageTypeName" class="codecode" style="width: 48%" value="${prpLregist.manageTypeName}" ondblclick="code_CodeSelect(this,'Manage_Type','-1,0','Y','N');"
								onchange="code_CodeChange(this,'Manage_Type','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'Manage_Type','-1,0','Y','N');">
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<div style="background-color: #ffffff">
				<table class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left">
							<s:text name="regist.prpLregist.firstSiteFlag" />
							：
							<%-- 是否第一現場報案 --%>
						</td>
						<td class="right" colspan="2">
							<s:radio list="#{'0':#flagNo,'1':#flagYes}" name="firstSiteFlag" value="#request.prpLregist.firstSiteFlag" />
						</td>
						<td class="title">
							<s:text name="regist.prpLregist.sendMesFlag" />
							：
							<%-- 是否发短信 --%>
						</td>
						<td class="input" colspan="2">
							<c:if test="${editType == 'ADD'}">
								<s:text name="regist.prpLregist.yes" />
								<input type="radio" name='sendMesFlag' value='1' checked>
								<s:text name="regist.prpLregist.no" />
								<input type="radio" name='sendMesFlag' value='0'>
							</c:if>
							<c:if test="${editType != 'ADD'}">
								<c:if test="${prpLregist.sendMesFlag == '0' }">
									<s:text name="regist.prpLregist.yes" />
									<input type="radio" name='sendMesFlag' value='1'>
									<s:text name="regist.prpLregist.no" />
									<input type="radio" name='sendMesFlag' value='0' checked>
								</c:if>
								<c:if test="${prpLregist.sendMesFlag != '0' }">
									<s:text name="regist.prpLregist.yes" />
									<input type="radio" name='sendMesFlag' value='1' checked>
									<s:text name="regist.prpLregist.no" />
									<input type="radio" name='sendMesFlag' value='0'>
								</c:if>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="left" style="width: 20%">
							<s:text name="regist.prpLregist.processingFlag" />
							：
							<%-- 是否需要現場處理 --%>
						</td>
						<td class="right" colspan="2">
							<s:select name="scheduleType" value="#request.prpLscheduleMainWF.scheduleType" listKey="key" listValue="value" list="#request.scheduleTypeList" />
						</td>
						<%
							/** add by chenjie 2013-03-13 start*/
						%>
						<td class="left" style="width: 20%">
							<s:text name="regist.prpLregist.selfCompensation" />
							：
							<%-- 互碰自賠標志 --%>
						</td>
						<td class="right" colspan="2">
							<s:select name="payselfFlag" value="#request.prpLregist.payselfFlag" listKey="key" listValue="value" list="#request.payselfFlagList" />
						</td>
						<%
							/** add by chenjie 2013-03-13 end*/
						%>
						<input type="text" style="width: 10%" name="prpLregistEstiCurrency" title="估損幣別" Class="codecode" value="${prpLregist.estiCurrency}" ondblclick="code_CodeSelect(this, 'currency','0','Y');"
							onkeyup="code_CodeSelect(this, 'currency','0','Y');" style="display:none">
						<input type="text" style="width: 35%" name="prpLregistEstimateLoss" title="估計損失" Class="input" value="<fmt:formatNumber value="${prpLregist.estimateLoss}" pattern="#"/>" style="display:none">
					</tr>
					<tr>
						<td class="left">
							<s:text name="regist.prpLregist.personLossFlag" />
							：
							<%-- 是否人伤 --%>
						</td>
						<td class="right" colspan="2">
							<s:radio list="#{'0':#flagNo,'1':#flagYes}" name="personLossFlag" value="#request.prpLregist.personLossFlag" />
						</td>
						<td class="left">
							<s:text name="regist.prpLregist.thirdLicenseNo" />
							：
							<%-- 三者牌照號碼 --%>
						</td>
						<td class="right" colspan="2">
							<input type="text" name="prpLregistthirdLicenseNo" class="input" maxlength="56" value="${prpLregist.thirdLicenseNo}" style="width: 50%">
						</td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="claim.phyDamage" />
							：
							<%-- 是否物损 --%>
						</td>
						<td class="right" colspan="2">
							<s:radio list="#{'0':#flagNo,'1':#flagYes}" name="propLossFlag" value="#request.prpLregist.propLossFlag" />
						</td>
						<c:choose>
							<c:when test="${requestScope.qsFlag=='Y'&& editType=='ADD'}">
								<td class="left">
									<s:text name="regist.prpLregist.registType" />
									：
									<%-- 備案類型 --%>
								</td>
								<td class="right" colspan="2">
									<select name="registType" style="width: 50%">
										<option value="0" <c:if test="${prpLregist.registType=='0'}"> selected="selected"</c:if>>
											<s:text name="regist.prpLregist.registType0" />
											<%-- 任意險單獨報案 --%>
										</option>
										<option value="1" <c:if test="${prpLregist.registType=='1'}"> selected="selected"</c:if>>
											<s:text name="regist.prpLregist.registType1" />
											<%-- 強製險單獨報案 --%>
										</option>
										<option value="2" <c:if test="${prpLregist.registType=='2'}"> selected="selected"</c:if>>
											<s:text name="regist.prpLregist.registType2" />
											<%-- 任意、強製險關聯報案 --%>
										</option>
									</select> <img src="/claim/images/bgMarkMustInput.jpg">
								</td>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${prpLregist.registType=='0' || prpLregist.registType=='1' || prpLregist.registType=='2'}">
										<td class="left">
											<s:text name="regist.prpLregist.registType" />
											：
											<%-- 備案類型 --%>
										</td>
										<td class="right" colspan="2">
											<select name="registType" style="width: 50%">
												<c:choose>
													<c:when test="${prpLregist.registType=='0'}">
														<option value="0" selected="selected">
															<s:text name="regist.prpLregist.registType0" />
															<%-- 任意險單獨報案 --%>
														</option>
													</c:when>
													<c:when test="${prpLregist.registType=='1'}">
														<option value="1" selected="selected">
															<s:text name="regist.prpLregist.registType1" />
															<%-- 強製險單獨報案 --%>
														</option>
													</c:when>
													<c:when test="${prpLregist.registType=='2'}">
														<option value="2" selected="selected">
															<s:text name="regist.prpLregist.registType2" />
															<%-- 任意、強製險關聯報案 --%>
														</option>
													</c:when>
													<c:otherwise></c:otherwise>
												</c:choose>
											</select>
										</td>
									</c:when>
									<c:otherwise>
										<td class="left"></td>
										<td class="right" colspan="2"></td>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
			</div>
		</td>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<div style="background-color: #ffffff">
				<table class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left">
							<s:text name="regist.prpLregist.remark" />
							：
							<%-- 事故經過及建議描述 --%>
						</td>
						<td class="right" colspan="5">
							<input type="text" name="prpLregistRemark" class="input" value="${prpLregist.remark}">
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<br>