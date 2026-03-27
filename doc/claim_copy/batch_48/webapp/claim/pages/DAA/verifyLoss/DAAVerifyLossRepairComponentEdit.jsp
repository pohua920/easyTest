<%@page pageEncoding="GBK"%>
<%--
****************************************************************************
* DESC       ：核损环节过程的修理/换件清单页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13  
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<table id="RepairComponent" class=common cellpadding="5" cellspacing="1">
	<tr>
		<td style="width: 100%">
			<span style="display: none">
				<table class="common" style="display: none" id="RepairFee_Data" cellspacing="1" cellpadding="5">
					<tbody>
						<tr>
							<td class="input">
								<input type="hidden" name="carLossRepairFeeLossItemCode" style="width: 20px">
								<input type="text" name="prpLrepairFeeKindCode" class="codecode" style='width: 40px'
									ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
							<td class="input">
								<input type="text" name="prpLrepairFeeKindName" class="codename" style='width: 70px'
									ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
							<td class="input">
								<input type="text" name="prpLrepairFeeCompCode" class="codecode" style='width: 40px' ondblclick="code_CodeSelect(this,'CompCode');" onchange="code_CodeChange(this, 'CompCode');"
									onkeyup="code_CodeSelect(this,'CompCode');">
							</td>
							<td class="input">
								<input type="text" name="prpLrepairFeeCompName" class="codename" style='width: 70px' maxlength="80" ondblclick="code_CodeSelect(this, 'CompCode','-1','always','none','post');"
									onchange="code_CodeChange(this, 'CompCode','-1','name','none','post');" onkeyup="code_CodeSelect(this, 'CompCode','-1','always','none','post');">
								<input type="hidden" name="prpLrepairFeeRepairType" class="readonly" readonly>
							</td>
							<td class="input" style="display: none">
								<input name="prpLrepairFeeManHour" value="1" class="readonly" readonly style='width: 70px'>
							</td>
							<td class="input">
								<input name="prpLrepairFeeManHourUnitPrice" class="readonly" readonly style='width: 70px'>
							</td>
							<td class="input" style="display: none">
								<input name="prpLrepairFeeMaterialFee" class="readonly" readonly style='width: 70px'>
							</td>
							<td class="input">
								<input name="prpLrepairFeeSumDefLoss" class="readonly" readonly style='width: 70px'>
							</td>
							<td class="input">
								<input name="prpLrepairFeeRemark" class="readonly" readonly style='width: 100px'>
								<input name="prpLrepairFeeIndId" type=hidden />
							</td>
							<c:choose>
								<c:when test="${param.nodeType =='backc'}">
									<td class="input">
										<input name="prpLrepairFeeBackCheckRemark" class="readonly" readonly style='width: 100px'>
									</td>
								</c:when>
								<c:otherwise>
									<input type="hidden" name="prpLrepairFeeBackCheckRemark">
								</c:otherwise>
							</c:choose>
							<input type="hidden" name="prpLrepairFeeSerialNo">
							<input type="hidden" name="prpLrepairFeeItemKindNo">
							<input type="hidden" name="prpLrepairFeeLossItemCode">
							<input type="hidden" name="prpLrepairFeeLicenseNo">
							<input type="hidden" name="prpLrepairFeeLicenseColorCode">
							<input type="hidden" name="prpLrepairFeeCarKindCode">
							<input type="hidden" name="prpLrepairFeeSanctioner">
							<input type="hidden" name="prpLrepairFeeApproverCode">
							<input type="hidden" name="prpLrepairFeeOperatorCode">
							<input type="hidden" name="prpLrepairFeeManHourFee">
							<input type="hidden" name="prpLrepairFeeLossRate">
							<input type="hidden" name="prpLrepairFeeCurrency">
							<input type="hidden" name="prpLrepairFeeVeriRemark">
							<input type="hidden" name="prpLrepairFeeVeriManHour">
							<input type="hidden" name="prpLrepairFeeVeriManUnitPrice">
							<input type="hidden" name="prpLrepairFeeVeriManHourFee">
							<input type="hidden" name="prpLrepairFeeVeriMaterQuantity">
							<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice">
							<input type="hidden" name="prpLrepairFeeVeriMaterialFee">
							<input type="hidden" name="prpLrepairFeeVeriLossRate">
							<input type="hidden" name="prpLrepairFeeVeriSumLoss">
							<input type="hidden" name="prpLrepairFeeFlag">
							<input type="hidden" name="prpLrepairFeeCompensateBackFlag">
						</tr>
					</tbody>
				</table>
			</span> <span style="display: none">
				<table class="common" style="display: none" id="Component_Data" cellspacing="1" cellpadding="5">
					<tbody>
						<tr>
							<td class="input">
								<input type="hidden" name="carLossComponentLossItemCode" style="width: 20px">
								<input type="text" name="prpLcomponentKindCode" class="codecode" style='width: 40px'
									ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
							<td class="input">
								<input type="text" name="prpLcomponentKindName" class="codename" style='width: 70px'
									ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
							<td class="input">
								<input name="prpLcomponentCompCode" type='hidden'>
								<input name="prpLcomponentCompName" class=common readonly style='width: 65px' maxlength="80">
							</td>
							<td class="input">
								<input type="text" name="prpLcomponentOriginalId" class="common" readonly style='width: 50px'>
							</td>
							<td class="input">
								<input name="prpLcomponentMaterialFee" class=common readonly style='width: 55px'>
							</td>
							<td class="input">
								<input name="prpLcomponentReqairFactoryFee" class=common style='width: 55px'>
							</td>
							<td class="input">
								<select name="prpLcomponentPriceType" class="three" style="width: 60px">
									<option value="专修价" selected>
										<s:text name="certainLoss.price" />
									</option>
									<%--专修价 --%>
									<option value="市场价">
										<s:text name="certainLoss.marketPrice" />
									</option>
									<%--市场价 --%>
								</select>
							</td>
							<td class="input">
								<input name="prpLcomponentQuantity" class=common readonly style='width: 30px'>
							</td>
							<td class="input">
								<input name="prpLcomponentSys4SPrice" class="common" readonly style='width: 40px'>
							</td>
							<td class="input">
								<input name="prpLcomponentSysMarketPrice" class="common" readonly style='width: 40px'>
							</td>
							<td class="input">
								<input name="prpLcomponentSysMatchPrice" class="common" readonly style='width: 30px'>
							</td>
							<td class="input">
								<input name="prpLcomponentNative4SPrice" class="common" readonly style='width: 40px'>
							</td>
							<td class="input">
								<input name="prpLcomponentNativeMarketPrice" class="common" readonly style='width: 40px'>
							</td>
							<td class="input">
								<input name="prpLcomponentNativeMatchPrice" class="common" readonly style='width: 30px'>
							</td>
							<input name="prpLcomponentVerpCompPrice" class="common" type="hidden" readonly style='width: 55px'>
							<td class="input">
								<input name="prpLcomponentRemark" class=common readonly style='width: 60px'>
								<input name="prpLcomponentFlag" type=hidden>
								<input name="prpLcomponentIndId" type=hidden>
								<input name="prpLcomponentIfRemain" type=hidden />
							</td>
							<c:choose>
								<c:when test="${param.nodeType =='backc'}">
									<td class="input">
										<input name="prpLcomponentBackCheckRemark" class="readonly" readonly style='width: 100px'>
									</td>
								</c:when>
								<c:otherwise>
									<input type="hidden" name="prpLcomponentBackCheckRemark">
								</c:otherwise>
							</c:choose>
							<input type="hidden" name="prpLcomponentSerialNo">
							<input type="hidden" name="prpLcomponentItemKindNo">
							<input type="hidden" name="prpLcomponentLossItemCode">
							<input type="hidden" name="prpLcomponentManHourFee">
							<input type="hidden" name="prpLcomponentSumDefLoss">
							<input type="hidden" name="prpLcomponentPartCode">
							<input type="hidden" name="prpLcomponentPartName">
							<input type="hidden" name="prpLcomponentRestFee">
							<input type="hidden" name="prpLcomponentLicenseNo">
							<input type="hidden" name="prpLcomponentLicenseColorCode">
							<input type="hidden" name="prpLcomponentCarKindCode">
							<input type="hidden" name="prpLcomponentMakeYear">
							<input type="hidden" name="prpLcomponentGearboxType">
							<input type="hidden" name="prpLcomponentQuoteCompanyGrade">
							<input type="hidden" name="prpLcomponentManageFeeRate">
							<input type="hidden" name="prpLcomponentRepairFactoryCode">
							<input type="hidden" name="prpLcomponentRepairFactoryName">
							<input type="hidden" name="prpLcomponentHandlerCode">
							<input type="hidden" name="prpLcomponentRepairStartDate">
							<input type="hidden" name="prpLcomponentRepairEndDate">
							<input type="hidden" name="prpLcomponentSanctioner">
							<input type="hidden" name="prpLcomponentApproverCode">
							<input type="hidden" name="prpLcomponentOperatorCode">
							<input type="hidden" name="prpLcomponentQueryPrice">
							<input type="hidden" name="prpLcomponentQuotedPrice">
							<input type="hidden" name="prpLcomponentLossRate">
							<input type="hidden" name="prpLcomponentCurrency">
							<input type="hidden" name="prpLcomponentVeriManHourFee">
							<input type="hidden" name="prpLcomponentVeriRestFee">
							<input type="hidden" name="prpLcomponentVeriLossRate">
							<input type="hidden" name="prpLcomponentSumVeriLoss">
							<input type="hidden" name="prpLcomponentCompensateBackFlag">
						</tr>
					</tbody>
				</table>
			</span> <span id="SpanRepairComponent" cellspacing="1" cellpadding="0"> <c:forEach items="${requestScope.verifyLossDto.prpLcarLossList}" var="prpLcarLoss">
					<c:set var="prpLrepairFeeRepairStartDate" value="" />
					<c:set var="prpLrepairFeeRepairEndDate" value="" />
					<c:set var="prpLrepairFeeHandlerCode" value="" />
					<c:set var="prpLrepairFeeHandlerName" value="" />
					<c:if test="${not empty requestScope.prpLrepairFee.repairFeeList}">
						<c:forEach items="${requestScope.prpLrepairFee.repairFeeList}" var="prpLrepairFee1">
							<c:if test="${pageScope.prpLrepairFee1.id.lossItemCode == pageScope.prpLcarLoss.id.lossItemCode}">
								<c:set var="prpLrepairFeeRepairStartDate" value="${pageScope.prpLrepairFee1.repairStartDate}" />
								<c:set var="prpLrepairFeeRepairEndDate" value="${pageScope.prpLrepairFee1.repairEndDate}" />
								<c:set var="prpLrepairFeeHandlerCode" value="${pageScope.prpLrepairFee1.handlerCode}" />
								<c:set var="prpLrepairFeeHandlerName" value="${pageScope.prpLrepairFee1.handlerName}" />
							</c:if>
						</c:forEach>
					</c:if>
					<input type="hidden" name="carLossSize" value="${fn:length(requestScope.verifyLossDto.prpLcarLossList)}">
					<table class=common cellpadding="5" cellspacing="1">
						<tr>
							<td class="centertitle" colspan="4">
								<s:text name="certainLoss.claimsVehicles" />
							</td>
							<%--理赔车辆 --%>
						</tr>
					</table>
					<table class=subtable cellpadding="0" cellspacing="1">
						<tr>
							<td>
								<table class=common cellpadding="1" cellspacing="1">
									<tr>
										<td class="left">
											<s:text name="certainLoss.carNumber" />：
											<%--车辆序号 --%>
											<input type="hidden" name="prpLcarLossSumVeriRest" value="${pageScope.prpLcarLoss.sumVeriRest}">
											<input type="hidden" name="prpLcarLossSumCertainLoss" value="${pageScope.prpLcarLoss.sumCertainLoss}">
											<input type="hidden" name="prpLcarLossSumVerifyLoss" value="${pageScope.prpLcarLoss.sumVerifyLoss}">
											<input type="hidden" name="prpLcarLossLossDesc" value="${pageScope.prpLcarLoss.lossDesc}">
											<input type="hidden" name="prpLcarLossSumVeriManager" value="${pageScope.prpLcarLoss.sumVeriManager}">
											<input type="hidden" name="prpLcarLossSumManageFeeRate" value="${pageScope.prpLcarLoss.sumManageFeeRate}">
											<input type="hidden" name="prpLcarLossIndemnityDuty" value="${pageScope.prpLcarLoss.indemnityDuty}">
											<input type="hidden" name="prpLcarLossIndemnityDutyRate" value="${pageScope.prpLcarLoss.indemnityDutyRate}">
											<input type="hidden" name="prpLcarLossVeriIndeDutyRate" value="${pageScope.prpLcarLoss.veriIndeDutyRate}">
											<input type="hidden" name="prpLcarLossRemark" value="${pageScope.prpLcarLoss.remark}">
											<input type="hidden" name="prpLcarLossOperatorCode" value="${pageScope.prpLcarLoss.operatorCode}">
											<input type="hidden" name="prpLcarLossApproverCode" value="${pageScope.prpLcarLoss.approverCode}">
											<input type="hidden" name="prpLcarLossFlag" value="${pageScope.prpLcarLoss.flag}">
											<input type="hidden" name="prpLcarLossBackCheckFlag" value="${pageScope.prpLcarLoss.backCheckFlag}">
										</td>
										<td class="right">
											<input name="prpLcarLossLossItemCode" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.id.lossItemCode}">
										</td>
										<c:choose>
											<c:when test="${pageScope.prpLcarLoss.id.lossItemCode =='1'}">
												<td class="left">
													<s:text name="print.markCar" />：
												</td>
												<%--标的车 --%>
											</c:when>
											<c:otherwise>
												<td class="left">
													<font color="red"><s:text name="certainLoss.thirdCarLoss.thirdCar" />：</font>
												</td>
												<%--三者车 --%>
											</c:otherwise>
										</c:choose>
										<td class="right"></td>
										<td class="left"></td>
										<td class="right"></td>
									</tr>
									<tr>
										<td class="left">
											<s:text name="db.prpLlawsuit.licenseNo" />：
										</td>
										<%--号牌号码 --%>
										<td class="right">
											<input name="prpLcarLossLossItemName" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.lossItemName}">
										</td>
										<td class="left">
											<s:text name="db.prpLlawsuit.brandName" />：
										</td>
										<%--厂牌型号 --%>
										<td class="right">
											<input name="prpLcarLossBrandName" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.brandName}">
										</td>
										<td class="left">
											<s:text name="certainLoss.thirdCarLoss.carKind" />：
										</td>
										<%--车辆种类 --%>
										<td class="right">
											<input name="prpLcarLossCarKindName" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.carKindName}">
										</td>
									</tr>
									<tr>
										<td class="left">
											<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyEngineNo" />
										</td>
										<%--发动机号 --%>
										<td class="right">
											<input name="prpLcarLossEngineNo" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.engineNo}">
										</td>
										<td class="left">
											<s:text name="db.prpLCItemCar.frameNo" />：
										</td>
										<%--车架号 --%>
										<td class="right">
											<input name="prpLcarLossFrameNo" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.frameNo}">
										</td>
										<td class="left"></td>
										<td class="right">
											<input type=hidden name="prpLcarLossVINNo" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.VINNo}">
										</td>
									</tr>
									<input type="hidden" name="prpLcarLossLicenseColorCode" value="${pageScope.prpLcarLoss.licenseColorCode}">
									<input type="hidden" name="prpLcarLossCarKindCode" value="${pageScope.prpLcarLoss.carKindCode}">
									<input type="hidden" name="prpLcarLossInsureCarFlagName" value="${pageScope.prpLcarLoss.insureCarFlagName}">
									<input type="hidden" name="prpLcarLossInsureCarFlag" value="${pageScope.prpLcarLoss.insureCarFlag}">
									<input type="hidden" name="prpLcarLossInsureComName" value="${pageScope.prpLcarLoss.insureComName}">
									<tr>
										<td class="left">
											<s:text name="db.prpLcomponent.repairFactoryCode" />：
										</td>
										<%--修理厂代码 --%>
										<td class="right">
											<input name="prpLrepairFeeRepairFactoryCode" type=hidden class="readonly" readonly="true" value="${requestScope.prpLverifyLoss.repairFactoryCode}">
											<c:choose>
												<c:when test="${requestScope.prpLverifyLoss.repairFactoryCode =='03'}">
													<input name="prpLrepairFeeRepairFactoryCode1" class="readonly" readonly="true" value="二类厂">
												</c:when>
												<c:when test="${requestScope.prpLverifyLoss.repairFactoryCode =='02'}">
													<input name="prpLrepairFeeRepairFactoryCode1" class="readonly" readonly="true" value="一类厂">
												</c:when>
												<c:when test="${requestScope.prpLverifyLoss.repairFactoryCode =='01'}">
													<input name="prpLrepairFeeRepairFactoryCode1" class="readonly" readonly="true" value="4S店">
												</c:when>
												<c:otherwise>
													<input name="prpLrepairFeeRepairFactoryCode1" class="readonly" readonly="true" value="">
												</c:otherwise>
											</c:choose>
										</td>
										<td class="left">
											<s:text name="db.prpLcomponent.repairFactoryName" />：
										</td>
										<%--修理厂名称 --%>
										<td class="right">
											<input name="prpLrepairFeeRepairFactoryName" class="readonly" value="${requestScope.prpLverifyLoss.repairFactoryName}">
										</td>
										<td class="left">
											<s:text name="db.prpLcomponent.repairStartDate" />：
										</td>
										<%--进厂日期 --%>
										<td class="right">
											<%--  <input name="prpLrepairFeeRepairStartDate" class="readonly" value="${pageScope.prpLrepairFeeRepairStartDate}">--%>
											<rc:rcDate name="prpLrepairFeeRepairStartDate" class="readonly" readonly="true" wdatePicker="false" value="${pageScope.prpLrepairFeeRepairStartDate}" />
										</td>
									</tr>
									<tr>
										<td class="left">
											<s:text name="db.prpLcomponent.repairEndDate" />：
										</td>
										<%--约定交车日期 --%>
										<td class="right">
											<%--   <input name="prpLrepairFeeRepairEndDate" class="readonly" value="${pageScope.prpLrepairFeeRepairEndDate}">--%>
											<rc:rcDate name="prpLrepairFeeRepairEndDate" class="readonly" readonly="true" wdatePicker="false" value="${pageScope.prpLrepairFeeRepairEndDate}" />
										</td>
										<td class="left">
											<s:text name="db.prpLsalvation.handlerCode" />：
										</td>
										<%--经办人代码 --%>
										<td class="right">
											<input name="prpLrepairFeeHandlerCode" class="readonly" value="${pageScope.prpLrepairFeeHandlerCode}" ondblclick="code_CodeSelect(this, 'HanderCode');"
												onchange="code_CodeChange(this, 'HanderCode');" onkeyup="code_CodeSelect(this, 'HanderCode');">
										</td>
										<td class="left">
											<s:text name="certainLoss.managerName" />：
										</td>
										<%--经办人名称 --%>
										<td class="right">
											<input name="prpLrepairFeeHandlerName" class="readonly" value="${pageScope.prpLrepairFeeHandlerName}" ondblclick="code_CodeSelect(this, 'HanderCode','-1','always','none','post');"
												onchange="code_CodeChange(this, 'HanderCode','-1','name','none','post');" onkeyup="code_CodeSelect(this, 'HanderCode','-1','always','none','post');">
										</td>
									</tr>
									<tr>
										<td class="left">
											<s:text name="regist.prpLregist.currency" />：
										</td>
										<%--币别 --%>
										<td class="right">
											<input name="prpLcarLossCurrencyName" class="readonly" readonly="true" value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>">
											<input type="hidden" name="prpLcarLossCurrency" class="readonly" readonly="true" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
										</td>
										<td class="left">
											<s:text name="certainLoss.lossAmount" />：
										</td>
										<%--定损金额 --%>
										<td class="right">
											<input name="CarSumPreDefLoss" class="readonly" readonly="true" value="<fmt:formatNumber value='${requestScope.prpLverifyLoss.sumPreDefLoss}' pattern='#'/>">
										</td>
										<td class="left">
											<s:text name="compensate.amountNucDamage" />：
										</td>
										<%--核损金额 --%>
										<td class="right">
											<input name="CarSumDefLoss" class="readonly" readonly="true" value="<fmt:formatNumber value='${requestScope.prpLverifyLoss.sumDefLoss}' pattern='#'/>">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table class=common cellpadding="5" cellspacing="1">
						<c:choose>
							<c:when test="${param.nodeType == 'backc'}">
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="verifyLoss.repairVehicle" />:
									</td>
									<%--修复验车意见 --%>
									<td class="input" style='width: 85%' colspan="3">
										<input name="prpLcarLossBackCheckRemark" class="input" style='width: 440px' value="${pageScope.prpLcarLoss.backCheckRemark}">
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="prpLcarLossBackCheckRemark" value="${pageScope.prpLcarLoss.backCheckRemark}">
							</c:otherwise>
						</c:choose>
						<tr>
							<td class="title" style="width: 80%" colspan="4">
								<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossComponent.jsp"%>
							</td>
						</tr>
						<tr>
							<td class="title" style="width: 80%" colspan="4">
								<%@include file="/pages/DAA/verifyLoss/DAAVerifyLossRepairFee.jsp"%>
							</td>
						</tr>
					</table>
				</c:forEach>
			</span>
		</td>
	</tr>
</table>