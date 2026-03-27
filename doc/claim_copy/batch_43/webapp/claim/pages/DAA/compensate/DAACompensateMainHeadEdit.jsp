<%--
****************************************************************************
* DESC	   :添加主信息子块界面页面Head[ 实赔 ]
* AUTHOR	 :中科软
* CREATEDATE :2012-02-18
* MODIFYLIST :  Name	   Date			Reason/Contents
*		  ------------------------------------------------------
****************************************************************************
--%>
<%@page import="com.sinosoft.claim.schema.model.PrpLcompensate"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ page import="com.sinosoft.claim.util.BusinessRuleUtil"%>
<%
	String advanceCaseStatus = (String) request.getAttribute("advanceCaseStatus");//案件状态
	String displayInputInfo = (String) request.getAttribute("displayInputInfo");//录入信息
	String displayUpload = (String) request.getAttribute("displayUpload");//上传事故数据
	String displayGetConfirm = (String) request.getAttribute("displayGetConfirm");//获取确认信息
	String advanceType = (String) request.getAttribute("advanceType");//垫付赔案类型
	String disabled1 = (String) request.getAttribute("disabled");//只读状态
	String isSpecial = (String) request.getAttribute("isSpecial");
	if (disabled1 == null) {
		disabled1 = "";
	}
	if (advanceType == null) {
		advanceType = "3";
	}
	if (displayInputInfo == null) {
		displayInputInfo = "display:none";
	}
	if (displayUpload == null) {
		displayUpload = "display:none";
	}
	if (displayGetConfirm == null) {
		displayGetConfirm = "display:none";
	}
	if (advanceCaseStatus == null) {
		advanceCaseStatus = "00";
	}
	if (isSpecial == null) {
		isSpecial = "0";
	}
	String configCode = (String) request.getAttribute("configCode");
	PrpLcompensate prpLcompensate = (PrpLcompensate) request.getAttribute("prpLcompensate");
	//增被保险人联系电话  end
	int intstartHour = 0;
	int intendHour = 0;
	String startHour = "";
	String endHour = "";
	if (prpLcompensate != null) {
		intstartHour = prpLcompensate.getStartHour();
		intendHour = prpLcompensate.getEndHour();
	}
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
%>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td colspan="4">
			<input type="hidden" name="prpLcompensateCaseNo" value="<c:out value='${requestScope.prpLcompensate.caseNo}'/>">
			<input type="hidden" name="prpLcompensateTimes" value="<c:out value='${requestScope.prpLcompensate.times}'/>">
			<input type="hidden" name="prpLcompensateClassCode" value="<c:out value='${requestScope.prpLcompensate.classCode}'/>">
			<input type="hidden" name="prpLcompensateRiskCode" value="<c:out value='${requestScope.prpLcompensate.riskCode}'/>">
			<input type="hidden" name="prpLcompensateDeductCond" value="<c:out value='${requestScope.prpLcompensate.deductCond}'/>">
			<input type="hidden" name="prpLcompensatePreserveDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.preserveDate}' pattern='yyyy-MM-dd'/>">
			<input type="hidden" name="prpLcompensateCheckAgentCode" value="<c:out value='${requestScope.prpLcompensate.checkAgentCode}'/>">
			<input type="hidden" name="prpLcompensateCheckAgentName" value="<c:out value='${requestScope.prpLcompensate.checkAgentName}'/>">
			<input type="hidden" name="prpLcompensateSurveyorName" value="<c:out value='${requestScope.prpLcompensate.surveyorName}'/>">
			<input type="hidden" name="prpLcompensateCounterClaimerName" value="<c:out value='${requestScope.prpLcompensate.counterClaimerName}'/>">
			<input type="hidden" name="prpLcompensateDutyDescription" value="<c:out value='${requestScope.prpLcompensate.dutyDescription}'/>">
			<input type="hidden" name="prpLcompensateCurrency" value="<c:out value='${requestScope.prpLcompensate.currency}'/>">
			<input type="hidden" name="prpLcompensateSumLoss" value="<c:out value='${requestScope.prpLcompensate.sumLoss}'/>">
			<input type="hidden" name="prpLcompensateSumRest" value="<c:out value='${requestScope.prpLcompensate.sumRest}'/>">
			<input type="hidden" name="prpLcompensateReceiverName" value="<c:out value='${requestScope.prpLcompensate.receiverName}'/>">
			<input type="hidden" name="prpLcompensateBank" value="<c:out value='${requestScope.prpLcompensate.bank}'/>">
			<input type="hidden" name="prpLcompensateAccount" value="<c:out value='${requestScope.prpLcompensate.account}'/>">
			<input type="hidden" name="prpLcompensateMakeCom" value="<c:out value='${requestScope.prpLcompensate.makeCom}'/>">
			<input type="hidden" name="prpLcompensateComCode" value="<c:out value='${requestScope.prpLcompensate.comCode}'/>">
			<input type="hidden" name="prpLcompensateHandler1Code" value="<c:out value='${requestScope.prpLcompensate.handler1Code}'/>">
			<input type="hidden" name="prpLcompensateApproverCode" value="<c:out value='${requestScope.prpLcompensate.approverCode}'/>">
			<input type="hidden" name="prpLcompensateUnderWriteCode" value="<c:out value='${requestScope.prpLcompensate.underWriteCode}'/>">
			<input type="hidden" name="prpLcompensateUnderWriteName" value="<c:out value='${requestScope.prpLcompensate.underWriteName}'/>">
			<input type="hidden" name="prpLcompensateOperatorCode" value="<c:out value='${requestScope.prpLcompensate.operatorCode}'/>">
			<input type="hidden" name="prpLcompensateInputDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.inputDate}' pattern='yyyy-MM-dd HH:mm:ss'/>">
			<input type="hidden" name="coreURL" value="<%=AppConfig.get("sysconst.Core_URL")%>">
			<input type="hidden" name="prpLcompensateUnderWriteEndDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.underWriteEndDate}' pattern='yyyy-MM-dd'/>">
			<input type="hidden" name="prpLcompensateUnderWriteFlag" value="<c:out value='${requestScope.prpLcompensate.underWriteFlag}'/>">
			<input type="hidden" name="prpLcompensateFlag" value="<c:out value='${requestScope.prpLcompensate.flag}'/>">
			<!-- mantis：CLM0062 ，處理人員：BK007 蘇哲，需求單編號：CLM0062.AML換新的理賠新核心 -->
			<input type="hidden" name="riskcode" value="<c:out value='${requestScope.prpLcompensate.riskCode}'/>">
			<input type="hidden" name="policyno" value="<c:out value='${requestScope.prpLcompensate.policyNo}'/>">
			<input type="hidden" name="prpLcompensateClauseTypeCode" value="<c:out value='${requestScope.prpLcompensate.clauseTypeCode}'/>">
			<input type="hidden" name="prpLcompensateEscapeFlag" value="<c:out value='${requestScope.prpLcompensate.escapeFlag}'/>">
			<input type="hidden" name="prpLcompensatePurchasePrice" value="<c:out value='${requestScope.prpLcompensate.purchasePrice}'/>">
			<input type="hidden" name="prpLcompensatePrintFlag" value="0">
			<input type="hidden" name="damageStartDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.damageStartDate}' pattern='yyyy-MM-dd'/>">
			<input type="hidden" name="damageStartHour" value="<c:out value='${requestScope.prpLcompensate.damageStartHour}'/>">
			<input type="hidden" name="sumPaidAll" value="<c:out value='${requestScope.prpLcompensate.sumPaidAll}'/>">
			<input type="hidden" name="swfLogFlowID" value="<c:out value='${param.swfLogFlowID}'/>">
			<input type="hidden" name="swfLogLogNo" value="<c:out value='${param.swfLogLogNo}'/>">
			<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
			<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
			<input type="hidden" name="swfLogNodeType" value="<c:out value='${param.nodeType}'/>">
			<input type="hidden" name="prpLcompensatePayFee" value="<c:out value='${requestScope.payFlag}'/>">
<!-- 			//mantis： CLM0092 ，處理人員：BK007 蘇哲，需求單編號：CLM0092.新核心-閉鎖期提醒 -->
			<input type="hidden" name="isCloseBetween" value="<c:out value='${requestScope.isCloseBetween}'/>">
			<input type="hidden" name="GenerateCompensateFlag" value="0">
			<input type="hidden" name="advanceCaseStatus" value="<%=advanceCaseStatus%>">
			<input type="hidden" name="isSpecial" value="<%=isSpecial%>">
			<input type="hidden" name="exceedingPayout" value="<c:out value='${exceedingPayout}'/>">
			<input type="hidden" name="prpLclaimCarAccidentType" value="${prpLclaim.carAccidentType}">
			<input type="hidden" name="prpLclaimPropAccidentType" value="${prpLclaim.propAccidentType}">
			<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START 
			<input type="hidden" name="registType" value="${registType}">-->
			<input type="hidden" name="defValue" value="N">
			<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END -->
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="compensate.computeBookNum" />：
					</td>
					<%-- 计算书号 --%>
					
					<td class="right" > <!--  colspan="2"> --><!-- mantis：CLM0111 ，處理人員：Bl061 張明財，需求單編號：CLM0111.新核心-理算結點「互沖計算書號」欄位變更 -->
						<input type=text name="prpLcompensateCompensateNo" title="計算書號" maxlength="23" class="readonly" style="width: 180px;" readonly="true"
							value="<c:out value='${requestScope.prpLcompensate.compensateNo}'/>">
					</td>
					<!-- mantis：CLM0111 ，處理人員：Bl061 張明財，需求單編號：CLM0111.新核心-理算結點「互沖計算書號」欄位變更  start-->
				    <c:choose>
						<c:when test="${not empty requestScope.prpLcompensate.mutualCompensateNo || not empty mutualCompensateNoList}">
							<td class="left">互沖計算書號碼：</td>
							 	<td class="right">
									<c:choose>
										<c:when test="${param.editType=='ADD'}">
											<select name="prpLcompensateMutualCompensateNo" onchange="getMutualCompe(this);">
												<c:if test="${empty param.prpLcompensateMutualCompensateNo}">
													<option value="" selected="selected"></option>
												</c:if>
												<c:forEach items="${requestScope.mutualCompensateNoList}" var="mutualCompensateNo">
													<option value="${mutualCompensateNo}" <c:if test="${mutualCompensateNo==param.prpLcompensateMutualCompensateNo}">selected="selected"</c:if>>${mutualCompensateNo}</option>
												</c:forEach>
											</select>
										</c:when>
										<c:otherwise>
											<input type="text" name="prpLcompensateMutualCompensateNo" class="readonly" readonly value="<c:out value='${requestScope.prpLcompensate.mutualCompensateNo}'/>">
										</c:otherwise>
									</c:choose>
								</td>
							</c:when>
							<c:otherwise>
								<td class="left"></td>
								<td class="right"></td>
							  </c:otherwise>
					</c:choose>
					<!-- mantis：CLM0111 ，處理人員：Bl061 張明財，需求單編號：CLM0111.新核心-理算結點「互沖計算書號」欄位變更  end-->
					<td class="left">
						<input type=button class="bigbutton" name="flowShow" value="<s:text name='button.flowChart.value' />" title="賠案流程圖"
							onclick="showWorkFlowerByClaimNo('<c:out value="${requestScope.prpLcompensate.claimNo}"/>')">
					</td>
					<%-- 赔案流程图 --%>
					<td class="right">
						<input type=hidden name="LFlag" title="理賠類型" maxlength="22" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLcompensate.lflag}'/>">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name='check.claimNum' />：
					</td>
					<%-- 赔案号 --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateClaimNo" readonly="true" value="<c:out value='${requestScope.prpLcompensate.claimNo}'/>">
						<input type="hidden" name="damageDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.damageStartDate}' pattern='yyyy-MM-dd'/>">
						<input type=button class="bigbutton" name="policyBackWard" value="出險時保單訊息"
							onclick="backWardPolicy(fm.coreURL.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,fm.damageDate.value,fm.prpLcompensateComCode.value);">
					</td>
					<td class="left">
						<s:text name="db.prpCmain.policyNo" />：
					</td>
					<%-- 保单号 --%>
					<td class="right">
						<input type=text name="prpLcompensatePolicyNo" style="width: 100%" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLcompensate.policyNo}'/>">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" onclick="relate(fm.prpLcompensatePolicyNo.value);return false;">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
					</td>
					<%-- 已出险次数 --%>
					<td class="right">
						<%-- 出险信息画面 --%>
						<%@include file="/pages/DAA/regist/DAAExistRegist.jsp"%>
					</td>
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
						<s:text name="certainLoss.prpLcheck.insuredName" />
					</td>
					<%-- 被保险人 --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateInsuredName" readonly="true" value="<c:out value='${requestScope.prpLcompensate.insuredName}'/>">
						<input type=hidden name="prpLcompensateCaseType" value="<c:out value='${requestScope.prpLcompensate.caseType}'/>">
						<input type=hidden name="prpLcompensateCaseTypeName" readonly="true" value="<c:out value='${requestScope.prpLcompensate.caseTypeName}'/>">
					</td>
					<!--增加被保险人联系电话-->
					<td class="left">
						<s:text name="db.prpLregist.insuredPhoneNumber" />：
					</td>
					<%-- 被保险人电话 --%>
					<td class="right">
						<c:choose>
							<c:when test="${empty requestScope.prpLcompensate.insuredPhoneNumber}">
								<c:if test="${not empty requestScope.prpLcheck.checkList}">
									<c:set var="insuredPhoneNumber" value="${requestScope.prpLcheck.checkList[0].insuredPhoneNumber}" />
								</c:if>
							</c:when>
							<c:otherwise>
								<c:set var="insuredPhoneNumber" value="${requestScope.prpLcompensate.insuredPhoneNumber}" />
							</c:otherwise>
						</c:choose>
						<input class="input" type="text" name="prpLcheckPhoneNumber" value="<c:out value='${insuredPhoneNumber}'/>">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.insuranceTime" />：
					</td>
					<%-- 保险期间 --%>
					<td class="right" colspan="2">
						<%-- <input type=text name="prpLcompensateStartDate" class="readonly" readonly="true"  value="<fmt:formatDate value='${requestScope.prpLcompensate.startDate}' pattern='yyyy-MM-dd'/> <%=startHour%> <c:out value='${requestScope.prpLcompensate.endDate}'/> <%=endHour%>">--%>
						<rc:rcDate name="prpLcompensateStartDate" class="readonly" style="width:80px" readonly="true" wdatePicker="false" value="${requestScope.prpLcompensate.startDate}" />
						<%=startHour%><rc:rcDate name="endDate" class="readonly" style="width:80px" readonly="true" wdatePicker="false" value="${requestScope.prpLcompensate.endDate}" />
						<%=endHour%>
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.selfCompensation" />：
					</td>
					<%--互碰自赔标志 --%>
					<td class="right" colspan="2">
						<c:set var="payselfFlag" value="0" />
						<c:choose>
							<c:when test="${not empty requestScope.prpLcheck.checkList}">
								<c:set var="payselfFlag" value="${requestScope.prpLcheck.checkList[0].payselfFlag}" />
							</c:when>
							<c:otherwise>
								<c:set var="payselfFlag" value="${requestScope.prpLcompensate.payselfFlag}" />
							</c:otherwise>
						</c:choose>
						<select name="payselfFlag" style="width: 60%">
							<c:choose>
								<c:when test="${payselfFlag=='0'}">
									<option value="0" selected="selected">
										<s:text name="regist.prpLregist.selfCompensationNo" />
									</option>
									<%--非互碰自赔 --%>
									<option value="1">
										<s:text name="regist.prpLregist.selfCompensationYes" />
									</option>
									<%--是互碰自赔 --%>
								</c:when>
								<c:otherwise>
									<option value="0">
										<s:text name="regist.prpLregist.selfCompensationNo" />
									</option>
									<%--非互碰自赔 --%>
									<option value="1" selected="selected">
										<s:text name="regist.prpLregist.selfCompensationYes" />
									</option>
									<%--是互碰自赔--%>
								</c:otherwise>
							</c:choose>
						</select>
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLCItemCar.clauseType" />：
					</td>
					<%-- 条款类别 --%>
					<td class="right" colspan=3>
						<input class="readonly" type=text name="prpLcompensateClauseName" readonly="true" value="<c:out value='${requestScope.prpLcompensate.clauseName}'/>">
					</td>
					<td class="left">
						<s:text name="certainLoss.thirdCarLoss.carKind" />：
					</td>
					<%-- 车辆种类 --%>
					<td class="right">
						<input name="prpLcompensateCarKind" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLcompensate.carKind}'/>">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLlawsuit.licenseNo" />：
					</td>
					<%-- 号牌号码 --%>
					<td class="right">
						<input class="readonly" name="prpLcompensateLicenseNo" readonly="true" value="<c:out value='${requestScope.prpLcompensate.licenseNo}'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLlawsuit.brandName" />：
					</td>
					<%-- 厂牌型号 --%>
					<td class="right">
						<input class="readonly" name="prpLcompensateBrandName" readonly="true" value="<c:out value='${requestScope.prpLcompensate.brandName}'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLlawsuit.licenseColorCode" />：
					</td>
					<%-- 号牌底色 --%>
					<td class="right">
						<input class="readonly" name="prpLcompensateLicenseColor" readonly="true" value="<c:out value='${requestScope.prpLcompensate.licenseColor}'/>">
					</td>
				</tr>
				<tr>
					<input type="hidden" name="prpLcompensateSeatCount" class="readonly" readonly="true" maxlength=20 description="座位数" value="<c:out value='${requestScope.prpLcompensate.seatCount}'/>">
					<td class="left">
						<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyFrameNo" />
					</td>
					<%-- 车架号 --%>
					<td class="right">
						<input type="text" name="prpLcompensateFrameNo" class="readonly" readonly="true" maxlength=20 description="车架号" value="<c:out value='${requestScope.prpLcompensate.frameNo}'/>">
					</td>
					<td class="left">
						<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyEngineNo" />
					</td>
					<%-- 发动机号 --%>
					<td class="right">
						<input type="text" name="prpLcompensateEngineNo" class="readonly" readonly="true" maxlength=20 description="发动机号" value="<c:out value='${requestScope.prpLcompensate.engineNo}'/>">
					</td>
					<td class="left"></td>
					<td class="right"></td>
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
					<td class="left"><s:text name="db.prpLclaim.damageName" />：</td>
					<td class="right">
						<c:choose>
							<c:when test="${requestScope.configCode=='RISKCODE_DAZ'}">
								<input type=text class="codecode" name="prpLcompensateDamageCode" style="width: 20%" title="出險原因" value="<c:out value='${requestScope.prpLcompensate.damageCode}'/>"
									ondblclick="code_CodeSelect(this, 'DamageCodeBZ','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" 
									onchange="code_CodeChange(this, 'DamageCodeBZ','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCodeBZ','0,1','Y','Y',fm.prpLcompensateRiskCode.value);">
								<input type=text class="codecode" name="prpLcompensateDamageName" title="出險原因" style="width: 75%" value="<c:out value='${requestScope.prpLcompensate.damageName}'/>"
									ondblclick="code_CodeSelect(this, 'DamageCodeBZ','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" 
									onchange="code_CodeChange(this, 'DamageCodeBZ','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCodeBZ','-1,0','Y','N',fm.prpLcompensateRiskCode.value);">
							</c:when>
							<c:otherwise>
								<input type=text class="codecode" name="prpLcompensateDamageCode" style="width: 20%" title="出險原因" value="<c:out value='${requestScope.prpLcompensate.damageCode}'/>"
									ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" 
									onchange="code_CodeChange(this, 'DamageCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);">
								<input type=text class="codecode" name="prpLcompensateDamageName" title="出險原因" style="width: 75%" value="<c:out value='${requestScope.prpLcompensate.damageName}'/>"
									ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" 
									onchange="code_CodeChange(this, 'DamageCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);">
							</c:otherwise>
						</c:choose>
						
					</td>
					<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START -->
					<td class="left"><!-- prpLcompensateSharingFlag 取消 -->
					</td>
					<td class="right">
					</td>
					
					<td class="left">是否為強制險同業攤賠案件 ：</td><!-- 預設:否 不用查詢區塊 選"是"在查 -->
					<td class="right"><s:radio list="#{'N':'否','Y':'是'}" name="prpLcompensateIsCompulsoryBchainClaim" value="#request.prpLcompensate.isCompulsoryBchainClaim"/></td><!-- ${requestScope.prpLcompensate.isCompulsoryBchainClaim} -->
					<!-- mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END -->
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
					</td>
					<%-- 出险时间 --%>
					<td class="right">
						<%--  <input type=text name="prpLcompensateDamageStartDate" class="readonly" readonly="true" maxlength="10" style="width:250px"
					value="<fmt:formatDate value='${requestScope.prpLcompensate.damageStartDate}' pattern='yyyy-MM-dd'/> 日 <c:out value='${requestScope.prpLcompensate.damageStartHour}'/> 時 <c:out value='${requestScope.prpLcompensate.damageStartMinute}'/> 分">
					--%>
						<rc:rcDate name="prpLcompensateDamageStartDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${requestScope.prpLcompensate.damageStartDate}" />
						日
						<c:out value='${requestScope.prpLcompensate.damageStartHour}' />
						時
						<c:out value='${requestScope.prpLcompensate.damageStartMinute}' />
						分
						<input type="hidden" name="DamageStartDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.damageStartDate}' pattern='yyyy-MM-dd'/>">
						<!-- mantis：CLM0216，處理人員：DP0714，新核心-新增車險醫詢費用提示檢核 -->
						<input type="hidden" name="DamageStartHour" value="${requestScope.prpLcompensate.damageStartHour}">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
					</td>
					<%-- 出险地点 --%>
					<td class="right">
						<input type=text name="prpLcompensateDamageAddress" title="出險地" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLcompensate.damageAddress}'/>">
					</td>
					<%
						/**无责垫付增加垫付赔案类型选择域 */
					%>
					<c:choose>
						<c:when test="${requestScope.advance=='1'}">
							<td class="left">
								<s:text name="check.payClaimType" />
							</td>
							<%-- 垫付赔案类型 --%>
							<td class="right">
								<select name="prplregistAdvance" onchange="changeAdvanceStatus(this);">
									<option value="1" <c:if test="${requestScope.advanceType=='1'}"><c:out value="selected"/></c:if>>
										<s:text name="check.payResponsib" />
									</option>
									<%-- 全责垫付 --%>
									<option value="2" <c:if test="${requestScope.advanceType=='2'}"><c:out value="selected"/></c:if>>
										<s:text name="check.noResponsib" />
									</option>
									<%-- 无责垫付 --%>
									<option value="3" <c:if test="${requestScope.advanceType=='3'}"><c:out value="selected"/></c:if>>
										<s:text name="check.other" />
									</option>
									<%-- 其它 --%>
								</select>
							</td>
						</c:when>
						<c:otherwise>
							<td class="left">&nbsp;&nbsp;</td>
							<td class="right">&nbsp;&nbsp;</td>
						</c:otherwise>
					</c:choose>
					<!--无责垫付增加垫付赔案类型选择域  -->
				</tr>
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.sumAmount" />：
					</td>
					<%--保险金额 --%>
					<td class="right">
						<input name="prpLcompensateSumAmount" type="text" class="readonly" readonly="true" value="<fmt:formatNumber value='${requestScope.prpLcompensate.sumAmount}' pattern='#' type='number'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLregist.estimateLoss" />：
					</td>
					<%-- 预估金额 --%>
					<td class="right">
						<input class="input" name="prpLcompensateSumClaim" title="預估金額" value="<fmt:formatNumber value='${requestScope.prpLcompensate.sumClaim}' pattern='#' type='number'/>" onfocus="cacheData(this);"
							onblur="validateMoney(this);" readonly="readonly">
						<img src="${ctx}/images/bgMarkMustInput.jpg">
					</td>
					<c:choose>
						<c:when test="${requestScope.advance=='1'}">
							<!--无责垫付增加垫付赔案类型选择域  -->
							<td class="left">
								<input type="text" name="displayInputInfo" style="<%=displayInputInfo%>" style="color:'#9B009B'" class="readonly" value="輸入信息"
									onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';" onMouseOut="this.style.color='#9B009B';" onclick="inputNullInfo();">
							</td>
							<td class="right">
								<input type="text" name="displayUpload" style="<%=displayUpload%>" style="color:'#9B009B'" class="readonly" value="上传事故信息" onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';"
									onMouseOut="this.style.color='#9B009B';" onclick="uploadToPlatForm('D5');">
								<input type="text" name="displayGetConfirm" <%=disabled1%> style="<%=displayGetConfirm%>" style="color:'#9B009B'" class="readonly" value="获取确认信息"
									onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';" onMouseOut="this.style.color='#9B009B';" onclick="getNullConfirm();">
							</td>
						</c:when>
						<c:otherwise>
							<td class="left">&nbsp;</td>
							<td class="right">&nbsp;</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${requestScope.configCode=='RISKCODE_DAZ'}">
							<td class="left">給付追償情況</td>
							<td class="right">
								<s:select name="prpLcompensatePaySituation" value="#request.prpLcompensate.paySituation" list="#request.paySituationList" listKey="key" listValue="value" />
								<img src="/claim/images/bgMarkMustInput.jpg">
							</td>
						</c:when>
						<c:otherwise>
							<td class="left">是否有殘餘物：</td>
							<td class="right">
								<s:select name="prpLcompensateRemnants" list="#{'0':'否','1':'是'}" value="#request.prpLcompensate.remnants" listKey="key" listValue="value" />
							</td>
						</c:otherwise>
					</c:choose>
					<td class="left">
						<s:text name="compensate.recovery" />：
					</td>
					<%-- 是否有追偿 --%>
					<td class="right">
						<!-- mantis：CLM0071 ，處理人員：BK007 蘇哲，需求單編號：CLM0071.車險理算節點修正 -->
						<select name="replevyFlag">
							<option value="0" <c:if test="${requestScope.prpLcompensate.replevyFlag == '0'}"><c:out value="selected" /></c:if>>
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%-- 否 --%>
							<option value="1" <c:if test="${requestScope.prpLcompensate.replevyFlag == '1'}"><c:out value="selected" /></c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%-- 是 --%>
						</select>
					</td>
					<c:choose>
						<c:when test="${requestScope.recaseFlag=='0'}">
							<td class="left">
								<s:text name="compensate.whetherCalculation" />：
							</td>
							<%-- 是否为案终计算书 --%>
							<td class="right">
								<input type="hidden" onclick="changePrpLcompensateFinallyFlag()" name="prpLcompensateFinallyFlag" value="0" checked>
								否
							</td>
						</c:when>
						<c:otherwise>
							<td class="left">
								<s:text name="compensate.reopenClaimCalcula" />
							</td>
							<%-- 重开赔案默认为案终计算书 --%>
							<td class="right">
								<input type="hidden" onclick="changePrpLcompensateFinallyFlag()" name="prpLcompensateFinallyFlag" value="1" checked>
								是
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td class="left">
						追償說明：
						<%-- 是否可能有追偿 --%>
					</td>
					<td class="right" colspan="3">
						<input name="prpLcompensateReplevyRemark" class="common" value="${prpLcompensate.replevyRemark }">
					</td>
					<!-- //mantis： CLM0091 ，處理人員：BK007 蘇哲，需求單編號：CLM0091.新核心-理算文件齊全日 -->
					<td class="left">文件收集齊全日:</td>
					<td class="right">
						<rc:rcDate name="prpLcompensateFileReadyDate" format="yyyy-MM-dd HH:mm" value="${prpLcompensate.fileReadyDate }"/>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<!-- //mantis： CLM0091 ，處理人員：BK007 蘇哲，需求單編號：CLM0091.新核心-理算文件齊全日 -->
				</tr>
				<tr>
					<td class="left">賠付代號：</td>
					<td class="right">
						<s:select name="prpLcompensatePayCode" id="prpLcompensatePayCode" cssStyle="width:250px" value="#request.prpLcompensate.payCode" list="#request.payCodeList" listKey="key" listValue="value" />
						<img src="/claim/images/bgMarkMustInput.jpg">
						<script type="text/javascript">
							$(function(){
								$("#prpLcompensatePayCode").bind("mouseover",function(){
									$(this).prop("title",$(this).children(":selected").text());
								});
							})
						</script>
					</td>
					<td class="left">全損/分損代號：</td>
					<td class="right">
						<s:select name="prpLcompensateLossType" value="#request.prpLcompensate.lossType" list="#request.lossTypeList" listKey="key" listValue="value" />
						<img src="${ctx }/images/bgMarkMustInput.jpg">
					</td>
					<td class="left"><s:text name="title.compensateEdit.speedFlag"/>：<%-- 赔款速度  --%></td>
					<td class="right"><s:select name="prpLcompensateSpeedFlag" value="#request.prpLcompensate.speedFlag" list="#request.speedFlagList" listKey="key" listValue="value" ></s:select></td>
				</tr>
				<tr>
					<td class="left">本車肇事責任：</td>
					<td class="right">
						<s:select name="indemnityDuty" value="#request.prpLcompensate.indemnityDuty" listKey="key" listValue="value" list="#request.selfIndemnityDutyNameList" onchange="checkIndemnityDuty(this);setAccidentType();replevyFlagRule();" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				<c:choose>
					<c:when test="${requestScope.configCode=='RISKCODE_DAZ'}">
						<td class="left">肇責類型：</td>
						<td class="right">
<!-- 						//mantis：CLM0073 ，處理人員：BK007 蘇哲，需求單編號：CLM0073.理賠系統-強制險肇責解鎖 -->
							<s:select name="prpLcompensateAccidentType" id="prpLcompensateAccidentType" value="#request.prpLcompensate.accidentType" listKey="key" listValue="value" list="#request.accidentTypeList" />
							<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
					</c:when>
					<c:otherwise>
						<td class="left"></td>
						<td class="right"></td>
					</c:otherwise>
				</c:choose>
		        <!-- mantis：CLM0111 ，處理人員：Bl061 張明財，需求單編號：CLM0111.新核心-理算結點「互沖計算書號」欄位變更start -->
		        <td class="left"></td>
				<td class="right"></td>
				 <!--    <c:choose>
						<c:when test="${not empty requestScope.prpLcompensate.mutualCompensateNo || not empty mutualCompensateNoList}">
							<td class="left">互沖計算書號碼：</td>
							 	<td class="right">
									<c:choose>
										<c:when test="${param.editType=='ADD'}">
											<select name="prpLcompensateMutualCompensateNo" onchange="getMutualCompe(this);">
												<c:if test="${empty param.prpLcompensateMutualCompensateNo}">
													<option value="" selected="selected"></option>
												</c:if>
												<c:forEach items="${requestScope.mutualCompensateNoList}" var="mutualCompensateNo">
													<option value="${mutualCompensateNo}" <c:if test="${mutualCompensateNo==param.prpLcompensateMutualCompensateNo}">selected="selected"</c:if>>${mutualCompensateNo}</option>
												</c:forEach>
											</select>
										</c:when>
										<c:otherwise>
											<input type="text" name="prpLcompensateMutualCompensateNo" class="readonly" readonly value="<c:out value='${requestScope.prpLcompensate.mutualCompensateNo}'/>">
										</c:otherwise>
									</c:choose>
								</td>
							</c:when>
							<c:otherwise>
								<td class="left"></td>
								<td class="right"></td>
							  </c:otherwise>
					</c:choose> -->
					<!-- mantis：CLM0111 ，處理人員：Bl061 張明財，需求單編號：CLM0111.新核心-理算結點「互沖計算書號」欄位變更 end -->
				</tr>
				<tr>
					<td class="left">本車肇責百分比：</td>
					<td class="right">
						<s:select name="prpLcompensateIndemnityDutyRate" value="#request.prpLcompensate.indemnityDutyRate" listKey="key" listValue="value" list="#request.indemnityDutyList"
							onchange="checkIndemnityDuty(this);setAccidentType();" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">對方車肇責百分比：</td>
					<td class="right">
						<s:select name="prpLcompensateOppositeIndemnityDuty" value="#request.prpLcompensate.oppositeIndemnityDuty" listKey="key" listValue="value" list="#request.indemnityDutyList"
							onchange="checkIndemnityDuty(this);" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">其他肇責百分比：</td>
					<td class="right">
						<s:select name="prpLcompensateOtherIndemnityDuty" value="#request.prpLcompensate.otherIndemnityDuty" listKey="key" listValue="value" list="#request.indemnityDutyList"
							onchange="checkIndemnityDuty(this);" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<c:if test="${requestScope.configCode=='RISKCODE_DAZ'}">
					<tr>
						<td class="left">是否涉及第29條代位情形：</td>
						<td class="right" colspan="3">
							<s:select name="prpLcompensateSubrogation" value="#request.prpLcompensate.subrogation" listKey="key" listValue="value" list="#request.subrogationList" />
						</td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
				</c:if>
				<c:if test="${requestScope.configCode!='RISKCODE_DAZ'}">
				<tr >
					<td class="left" >發票(和解書)簽收日：</td>
					<td class="right" >
						<rc:rcDate name="prpLcarInsuranceInvoiceDate" value="${prpLcarInsurance.invoiceDate }"/>
					</td>
					<td class="left"></td>
					<td class="right" colspan="3">
					</td>
				</tr>
				</c:if>
			</table>
		</td>
	</tr>
</table>
<script language="javascript">
/***
 * 互沖計算書切換時，獲取要互沖的計算書的訊息
 * @param field
 */
function getMutualCompe(field){
    if($.trim($(field).val())!=""){
        var url = "${ctx}/compensate/beforeCompeMutualImpulse.do?ClaimNo=${param.ClaimNo}&caseType=${param.caseType}&swfLogFlowID=${param.swfLogFlowID}&swfLogLogNo=${param.swfLogLogNo}&status=0&riskCode=${param.riskCode}&editType=ADD&nodeType=compe&businessNo=${param.businessNo}&keyIn=${param.keyIn}&policyNo=${param.policyNo}&modelNo=${param.modelNo}&nodeNo=${param.nodeNo}&dfFlag=${param.dfFlag}&actorId=${param.actorId}&processId=${param.processId}&compeCount=${param.compeCount}";
        url += "&prpLcompensateMutualCompensateNo="+$(field).val();
        window.location.href = url;
    }
}
$(document).ready(function () {
	$('#prpLcompensateSubrogation').on('change', function() {
		if ('${requestScope.configCode}'!='RISKCODE_DAZ') return ;
		if (this.value=='N') {
			$('#replevyFlag').val('0') ;
			$('#replevyFlagSel').val('0') ;
		}else{
			$('#replevyFlag').val('1') ;
			$('#replevyFlagSel').val('1') ;
		} ;	
	}) ;
	$('#prpLcompensateIndemnityDutyRate').on('change', function() {
		replevyFlagRule() ;
	}) ;
	$('#tabMain').on('click', function() {
		replevyFlagRule() ;
	}) ;
});
function replevyFlagRule() {
	if ('${requestScope.configCode}'!='RISKCODE_DAA') return ;
	var SumRealPay = parseInt($('#prpLcompensateSumThisPaid').val()) ;
	var DutyRate = $('#prpLcompensateIndemnityDutyRate').val() ;
	var isExistKind = false ;
	$('input[name="prpLlossDtoKindCode"]').each(function() {
		if ($(this).val()!="") {
		    if ("01,02,03,05,07,08,09,0F,0G,0H,0I,0J,0K,0R,0S".indexOf($(this).val())>=0) {
		    	isExistKind = true ;
		    }
		}
	});
	if ((DutyRate!='100.0')&&(SumRealPay>3000)&&(isExistKind)) {
		$('#replevyFlag').val('1') ;
		$('#replevyFlagSel').val('1') ;
	}else{
		$('#replevyFlag').val('0') ;
		$('#replevyFlagSel').val('0') ;
	}
}
</script>
<c:if test="${ not empty requestScope.firstCompeAccidentTpye }">
	<div id="divPreAccidentType" style="display: none">
		<c:forEach items="${requestScope.firstCompeAccidentTpye}" var="typeMap">
			<input name="kindAT_${typeMap.key}" value="${typeMap.value}" disabled="disabled"/>
		</c:forEach>
	</div>
</c:if>