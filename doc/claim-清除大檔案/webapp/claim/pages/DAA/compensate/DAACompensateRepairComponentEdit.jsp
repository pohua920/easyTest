<%@page pageEncoding="GBK"%>
<%--
**************************************************************************
* DESC       ：定損環節過程的修理/換件清單頁面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-03-06 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateCertainLoss.js"></script>
<span style="display: none;">
	<input type="hidden" name="prpLverifyLossFirstDefLoss" value="<fmt:formatNumber value="${prpLverifyLoss.firstDefLoss}" pattern="#" />">
	<input type="hidden" name="prpLverifyLossWarpDefLoss" value="<fmt:formatNumber value="${prpLverifyLoss.warpDefLoss}" pattern="#" />">
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
	<input type="hidden" name="prpLverifyLossCompensateFlag" value="${prpLverifyLoss.compensateFlag}" />
	<input type="hidden" name="prpLverifyLossHandlerCode" value="${prpLverifyLoss.handlerCode}" />
	<input type="hidden" name="prpLverifyLossHandlerName" value="${prpLverifyLoss.handlerName}" />
	<input type="hidden" name="certainLossFlag" value="${certainLossFlag }">
	<!-- mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 -->
	<input type="hidden" name="dayPerAmount" value="${requestScope.dayPerAmount}" />
	
	<!--车辆序号-->
	<c:forEach items="${prpLcarLossList}" var="prpLcarLoss">
		<input type="hidden" name="prpLcarLossLossItemCode" value="${prpLcarLoss.id.lossItemCode}">
		<input type="hidden" name="prpLcarLossLossItemName" value="${prpLcarLoss.lossItemName}">
		<input type="hidden" name="prpLcarLossSumVeriRest" value="${prpLcarLoss.sumVeriRest}">
		<input type="hidden" name="prpLcarLossSumVerifyLoss" value="${prpLcarLoss.sumVerifyLoss}">
		<input type="hidden" name="prpLcarLossLossDesc" value="${prpLcarLoss.lossDesc}">
		<input type="hidden" name="prpLcarLossIndemnityDuty" value="${prpLcarLoss.indemnityDuty}">
		<input type="hidden" name="prpLcarLossIndemnityDutyRate" value="${prpLcarLoss.indemnityDutyRate}">
		<input type="hidden" name="prpLcarLossVeriIndeDutyRate" value="${prpLcarLoss.veriIndeDutyRate}">
		<input type="hidden" name="prpLcarLossSumManageFeeRate" value="${prpLcarLoss.sumManageFeeRate}">
		<input type="hidden" name="prpLcarLossBackCheckRemark" value="${prpLcarLoss.backCheckRemark}">
		<input type="hidden" name="prpLcarLossRemark" value="${prpLcarLoss.remark}">
		<input type="hidden" name="prpLcarLossOperatorCode" value="${prpLcarLoss.operatorCode}">
		<input type="hidden" name="prpLcarLossApproverCode" value="${prpLcarLoss.approverCode}">
		<input type="hidden" name="prpLcarLossLicenseColorCode" value="${prpLcarLoss.licenseColorCode}">
		<input type="hidden" name="prpLcarLossLicenseCarKindCode" value="${prpLcarLoss.carKindCode}">
		<input type="hidden" name="prpLcarLossLicenseBrandName" value="${prpLcarLoss.brandName}">
		<input type="hidden" name="prpLcarLossLicenseModelCode" value="${prpLcarLoss.modelCode}">
		<input type="hidden" name="prpLcarLossLicenseEngineNo" value="${prpLcarLoss.engineNo}">
		<input type="hidden" name="prpLcarLossLicenseFrameNo" value="${prpLcarLoss.frameNo}">
		<input type="hidden" name="prpLcarLossLicenseVINNo" value="${prpLcarLoss.VINNo}">
		<input type="hidden" name="prpLcarLossInsureCarFlag" value="${prpLcarLoss.insureCarFlag}" />
		<input type="hidden" name="prpLcarLossInsureComCode" value="${prpLcarLoss.insureComCode}" />
		<input type="hidden" name="prpLcarLossInsureComName" value="${prpLcarLoss.insureComName}" />
		<input type="hidden" name="prpLcarLossFlag" value="${prpLcarLoss.flag}" />
	</c:forEach>
</span>
<span id="SpanRepairComponent" cellspacing="1" cellpadding="0">
	<table class=common cellpadding="5" cellspacing="1">
		<tr>
			<td class="subformtitle" colspan="6">
				<s:text name="certainLoss.damageLoss" />
			</td>
			<!--定损员定损-->
		</tr>
	</table>
	<table class=common cellpadding="5" cellspacing="1">
		<tr>
			<td class="left"></td>
			<td class="right">
				<input type="hidden" class=button name="certainLossCarModel"
					value="<s:text name='button.vehicleTrain.value'/>"
					onclick="openWinCertainLossCarModel();">
				<!--车辆定型-->
			</td>
			<td class="left">
				<s:text name="certainLoss.garageType" />：
			</td>
			<!--修理厂类型-->
			<td class="right">
				<c:set var="tempSelectedValue" value="${requestScope.prpLverifyLoss.repairFactoryCode}" />
				<s:select name="prpLrepairFeeRepairFactoryCode" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.feeRepairFactoryCodeList" />
				<input name="prpLrepairFeeRepairFactoryType" type="hidden" value="">
			</td>
			<td class="left">
				<s:text name="db.prpLrepairFee.repairFactoryName" />：
			</td>
			<!--修理厂名称-->
			<td class="right">
				<input name="prpLrepairFeeRepairFactoryName" class="input" value="${requestScope.prpLverifyLoss.repairFactoryName }">
			</td>
		</tr>
		<tr>
			<td class="left">
				<s:text name="db.prpLcomponent.repairStartDate" />：
			</td>
			<!--进厂日期-->
			<td class="right">
				<!-- mantis：CLM0221 ，處理人員：DP0713，需求單編號：新核心-車體險車輛資料完工日期欄位調整  -->
				<rc:rcDate name="prpLrepairFeeRepairStartDate" value="${prpLrepairFee.repairStartDate}" format="yyyy-MM-dd" onchange="calculateFinishAndDayCount();"/>
			</td>
			<td class="left">
				<s:text name="db.prpLcomponent.repairEndDate" />：
			</td>
			<!--约定交车日期-->
			<td class="right">
				<rc:rcDate name="prpLrepairFeeRepairEndDate" class="input" value="${prpLrepairFee.repairEndDate}" />
			</td>
			<!-- mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 START -->
			<!-- mantis：CLM0221 ，處理人員：DP0713，需求單編號：新核心-車體險車輛資料完工日期欄位調整 START -->
			<c:if test="${prpLverifyLoss.riskCode=='A01'}">
				<td class="left">完工日期：<span id="finishDate">${prpLrepairFee.completeDate}</span>
					<input type="hidden" name="prpLrepairFeeCompleteDate" class="readonly" readonly="true" value="${prpLrepairFee.completeDate}" format="yyyy-MM-dd">
					<!-- mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 -->
					<input type="hidden" name="prpLcomponentCompleteDate" class="readonly" readonly="true" value="${prpLrepairFee.completeDate}" format="yyyy-MM-dd">
				</td>
				<td class="right">代步車使用天數：<span id="dayCount">${prpLrepairFee.courtesyCarUseDates}</span>
					<input type="hidden" name="prpLrepairFeeCourtesyCarUseDates" class="readonly" readonly="true" value="${prpLrepairFee.courtesyCarUseDates}">
					<!-- mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 -->
					<input type="hidden" name="prpLcomponentCourtesyCarUseDates" class="readonly" readonly="true" value="${prpLrepairFee.courtesyCarUseDates}">
				</td>
			</c:if>
			<!-- mantis：CLM0221 ，處理人員：DP0713，需求單編號：新核心-車體險車輛資料完工日期欄位調整 END -->
			<!-- mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 END -->
		</tr>
		<tr>
			<td class="left">
				<s:text name="db.prpLsalvation.handlerCode" />：
			</td>
			<!--经办人代码-->
			<td class="right">
				<input name="prpLrepairFeeHandlerCode" class="codecode" value="${prpLrepairFee.handlerCode}" ondblclick="code_CodeSelect(this,'handerCode','0,1','Y');" onchange="code_CodeChange(this,'handerCode','0,1','Y');" onkeyup="code_CodeSelect(this,'handerCode','0,1','Y');">
			</td>
			<td class="left">
				<s:text name="certainLoss.managerName" />：
			</td>
			<!--经办人名称-->
			<td class="right">
				<input name="prpLrepairFeeHandlerName" class="codename" value="${prpLrepairFee.handlerName}"
					ondblclick="code_CodeSelect(this,'handerCode','-1,0','Y','N');" onchange="code_CodeChange(this,'handerCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'handerCode', '-1,0', 'Y', 'N');">
			</td>
			<td class="left"></td>
			<td class="right"></td>
		</tr>
		<tr>
			<td class="left">
				<s:text name="db.prpLreplevynew.currency" />：
			</td>
			<!--币别-->
			<td class="right">
				<input name="prpLcarLossCurrencyName" class="readonly" readonly="true" value="${LOCAL_CURRENCY }">
				<input type="hidden" name="prpLcarLossCurrency" class="readonly" readonly="true" value="${LOCAL_CURRENCY }">
			</td>
			<td class="left">
				<s:text name="certainLoss.totalAmount" />：
			</td>
			<!--总定损金额-->
			<td class="right">
				<input name="prpLcarLossSumCertainLoss" class="readonly" readonly="readonly" value="<fmt:formatNumber value="${prpLcarLoss.sumCertainLoss}" pattern="#"/>">
				<input name="prpLDeductible" type="hidden" readonly="true" value="${requestScope.prpLDeductible}">
			</td>
			<td class="left">
				<%--可选免赔额--%>
				<c:if test="${requestScope.prpLDeductible > 0.00}">
					<s:text name="certainLoss.optionalDeductible" />：</c:if>
			</td>
			<td class="right">
				<c:if test="${requestScope.prpLDeductible > 0.00}">
					<c:out value="${requestScope.prpLDeductible}" />
				</c:if>
			</td>
		</tr>
	</table>
	<table class=common cellpadding="5" cellspacing="1">
		<tr>
			<td class="title" style="width: 100%" colspan="4">
				<%--换件费用清单--%>
				<%@include file="/pages/DAA/compensate/DAACompensateComponent.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="title" style="width: 100%" colspan="4">
				<%--修理项目费用清单--%>
				<%@include file="/pages/DAA/compensate/DAACompnesateRepairFee.jsp"%>
			</td>
		</tr>
		<tr>
			<td class="title" style="width: 15%"></td>
			<td class="input" style='width: 85%' colspan="3">
				<input type="hidden" name="prpLcarLossBackCheckFlagCheck" <c:if test="${prpLcarLoss.backCheckFlag=='1'}"><c:out value="checked"/></c:if>>
				<input type="hidden" name="prpLcarLossBackCheckFlag" value="${prpLcarLoss.backCheckFlag}">
			</td>
		</tr>
	</table>
</span>
