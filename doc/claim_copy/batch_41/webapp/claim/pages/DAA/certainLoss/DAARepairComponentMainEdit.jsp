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
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<script language='javascript'>
	function setFlag0(pageCode, field) {
		fm.prpLcomponentFlag[fm.all("prpLcomponentFlag").length - 1].value = 0;
	}
</script>
<table id="RepairComponent" class=common cellpadding="5" cellspacing="1" width="1150px">
	<tr>
		<td>
			<span style="display: none">
				<table class="common" style="display: none" id="RepairFee_Data" cellpadding="5" cellspacing="1">
					<tbody>
						<tr>
							<td class="input" style="display: none">
								<input type="hidden" name="carLossRepairFeeLossItemCode" style="width: 20px">
								<input type="text" name="prpLrepairFeeKindCode" class="codecode" style="width: 40px">
							</td>
							<%--//判断並设置 prpLcarLoss1属性值 代替原来的java代码--%>
							<c:choose>
								<c:when test="${requestScope.certainLossDto!=null}">
									<c:set var="carLossSize" value="${fn:length(requestScope.certainLossDto.prpLcarLossList)}" />
									<c:set var="prpLcarLoss1" value="${requestScope.certainLossDto.prpLcarLossList[0]}" />
								</c:when>
								<c:otherwise>
									<c:set var="carLossSize" value="${fn:length(requestScope.verifyLossDto.prpLcarLossList)}" />
									<c:set var="prpLcarLoss1" value="${requestScope.verifyLossDto.prpLcarLossList[0]}" />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${pageScope.prpLcarLoss1.id.lossItemCode =='1'}">
									<td class="input" style="width: 20%;">
										<input type="text" name="prpLrepairFeeKindName" class="codename" style='width: 100%'
											ondblclick="code_CodeSelect(this,'PolicyKindCodeForMainCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onkeyup="code_CodeSelect(this,'PolicyKindCodeForMainCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
									</td>
								</c:when>
								<c:otherwise>
									<td class="input" style="width: 20%;">
										<input type="text" name="prpLrepairFeeKindName" class="codename" style='width: 100%'
											ondblclick="code_CodeSelect(this,'PolicyKindCodeForThirdCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onkeyup="code_CodeSelect(this,'PolicyKindCodeForThirdCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
									</td>
								</c:otherwise>
							</c:choose>
							<td class="input" style="display: none">
								<select name="prpLrepairFeePartCode" styleClass="three" style="width: 60px">
									<c:forEach items="${requestScope.partCodeList}" var="labelValueBean">
										<option value="${labelValueBean.key}">
											<c:out value="${labelValueBean.value}" />
										</option>
									</c:forEach>
								</select>
								<input type="hidden" name="prpLrepairFeePartName">
							</td>
							<td class="input" style="width: 30%">
								<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
								<input name="prpLrepairFeeCompName" class=common style="width: 100%" maxlength="100">
								<input type="hidden" name="prpLrepairFeeCompCode" value="9999">
							</td>
							<td class="input" style="width: 15%">
								<select name="prpLrepairFeeRepairType" style="width: 100%">
									<c:forEach items="${requestScope.repairTypes}" var="prpDcode">
										<option value="${prpDcode.id.codeCode}">
											<c:out value="${prpDcode.codeCName}" />
										</option>
									</c:forEach>
								</select>
							</td>
							<td class="input" style="display: none">
								<input name="prpLrepairFeeManHour" value="1" maxlength=10 class=common style='width: 50px' onBlur="getSumDefLoss(this,1);">
							</td>
							<td class="input" style="display: none">
								<input name="prpLrepairFeeManHourUnitPrice" value="1" maxlength=10 class="common" style='width: 60px' onBlur="getSumDefLoss(this,1);">
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLrepairFeeSumDefLoss" onblur="checkNum(this);sumRepairFee();" class=common style='width: 100%'>
							</td>
							<td class="input" style="display: none">
								<input name="prpLrepairFeeFirstSumDefLoss" class="readonly" readonly>
							</td>
							<td class="input" style="width: 20%">
								<input name="prpLrepairFeeRemark" maxlength=60 class=common style='width: 100%'>
							</td>
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
							<input type="hidden" name="prpLrepairFeeMaterialFee">
							<input type="hidden" name="prpLrepairFeeLossRate">
							<input type="hidden" name="prpLrepairFeeCurrency">
							<input type="hidden" name="prpLrepairFeeVeriRemark">
							<input type="hidden" name="prpLrepairFeeVeriManHour">
							<input type="hidden" name="prpLrepairFeeVeriManUnitPrice">
							<input type="hidden" name="prpLrepairFeeVeriManHourFee">
							<input type="hidden" name="prpLrepairFeeVeriMaterQuantity">
							<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice">
							<input type="hidden" name="prpLrepairFeeBackCheckRemark">
							<input type="hidden" name="prpLrepairFeeVeriMaterialFee">
							<input type="hidden" name="prpLrepairFeeVeriLossRate">
							<input type="hidden" name="prpLrepairFeeVeriSumLoss">
							<input type="hidden" name="prpLrepairFeeFlag">
							<input type="hidden" name="prpLrepairFeeIndId">
							<input type="hidden" name="prpLrepairFeeCompensateBackFlag">
							<td class="input" style='width: 5%;' align="center">
								<div>
									<input type=button name="buttonRepairFeeDelete" class="smallbutton" onclick="deleteRowTableRepairFee(this,'RepairFee',1,1);sumRepairFee();" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span style="display: none"> <%--/**添加换件项目导入的页签*/--%>
				<table class="common" style="display: none" id="Component_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="display: none">
								<input type="hidden" name="carLossComponentLossItemCode">
								<input type="hidden" name="prpLcomponentVeriSumDefLoss">
								<input type="hidden" name="prpLcomponentVeriVerpCompPrice">
								<input type="hidden" name="prpLcomponentKindCode" class="codecode"
									ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
							</td>
							<c:choose>
								<c:when test="${pageScope.prpLcarLoss1.id.lossItemCode =='1'}">
									<td class="input" style="width: 15%">
										<input type="text" name="prpLcomponentKindName" class="codecode" style='width: 100%;'
											ondblclick="code_CodeSelect(this,'PolicyKindCodeForMainCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onkeyup="code_CodeSelect(this,'PolicyKindCodeForMainCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
									</td>
								</c:when>
								<c:otherwise>
									<td class="input" style="width: 15%">
										<input type="text" name="prpLcomponentKindName" class="codecode" style='width: 100%;'
											ondblclick="code_CodeSelect(this,'PolicyKindCodeForThirdCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onkeyup="code_CodeSelect(this,'PolicyKindCodeForThirdCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
									</td>
								</c:otherwise>
							</c:choose>
							<td class="input" style="width: 23%">
								<input name="prpLcomponentCompName" class=common style='width: 100%' maxlength="100">
								<input name="prpLcomponentCompCode" type='hidden' value="">
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLcomponentOriginalId" class=common style='width: 100%'>
							</td>
							<td class="input" style="display: none">
								<input name="prpLcomponentSys4SPrice" class="common" readonly style='width: 100%'>
							</td>
							<td class="input" style="display: none">
								<input name="prpLcomponentSysMarketPrice" class="common" readonly style='width: 100%'>
							</td>
							<td class="input" style="display: none">
								<input name="prpLcomponentSysMatchPrice" class="common" readonly style='width: 100%'>
							</td>
							<td class="input" style="width: 4%">
								<input name="prpLcomponentNative4SPrice" class="common" style='width: 100%'>
							</td>
							<td class="input" style="width: 3%">
								<input name="prpLcomponentNativeMarketPrice" class="common" style='width: 100%'>
							</td>
							<td class="input" style="width: 3%">
								<input name="prpLcomponentNativeMatchPrice" class="common" style='width: 100%'>
								<input name="prpLcomponentFirstMaterialFee" type="hidden" class=readonly readonly style='width: 100%'>
							</td>
							<td class="input" style="display: none">
								<input name="prpLcomponentRepairFactoryFee" maxlength=10 class=common style='width: 100%'>
							</td>
							<td class="input" style='width: 5%;'>
								<input name="prpLcomponentMaterialFee" maxlength=10 class=common style="width: 100%" onBlur="return getSumDefLoss(this,2);setFirstMaterialFee(this);">
								<SPAN id="span_prpLcomponentSumDefLoss" name="span_prpLcomponentSumDefLoss"
									style="width: 500px; display: none; position: absolute; background-color: FFFFFF; border-color: #0066FF; border-width: thick;"> <%@include
										file="/pages/DAA/certainLoss/DAARepairComponentMessage.jsp"%>
								</SPAN>
							</td>
							<td class="input" style="display: none">
								<select name="prpLcomponentPriceType" class="three" style="width: 100%" onchange="changePriceType(this);">
									<option value="S">
										<s:text name="certainLoss.price" />
									</option>
									<!--专修价-->
									<option value="M">
										<s:text name="certainLoss.marketPrice" />
									</option>
									<!--市场价-->
									<option value="O">
										<s:text name="certainLoss.factoryPrice" />
									</option>
									<!--副厂价-->
								</select>
							</td>
							<td class="input" style='width: 5%;'>
								<input name="prpLcomponentQuantity" value="1" class=common style='width: 100%' onBlur="return getSumDefLoss(this,2);">
							</td>
							<td class="input" style="width: 5%">
								<s:select name="prpLcomponentIfRemain" listKey="key" listValue="value" list="#request.ifRemainList" style='width:100%' onChange="return sumComponentFee();" />
							</td>
							<td class="input" style='width: 5%;'>
								<input name="prpLcomponentRestFee" style='width: 100%' class=common onBlur="return sumComponentFee();">
							</td>
							<td class="input" style='width: 5%;'>
								<input name="prpLcomponentSumDefLoss" class=readonly readonly style="width: 100%" onchange="checkSumDefLoss(this);" onBlur="return getSumDefLoss(this,2);">
								<input name="prpLcomponentVerpCompPrice" type="hidden" class="common" readonly style='width: 100%' value="0">
								<input name="prpLcomponentVerpCompPriceLast" type="hidden">
							</td>
							<td class="input" style="width: 5%">
								<input name="verpoFlag" type="hidden" value="0">
								<input name="prpLcomponentRemark" maxlength=60 class=common style='width: 100%'>
								<input name="prpLcomponentFlag" type=hidden value=1>
								<input name="prpLcomponentIndId" type=hidden>
							</td>
							<input type="hidden" name="prpLcomponentSerialNo">
							<input type="hidden" name="prpLcomponentItemKindNo">
							<input type="hidden" name="prpLcomponentLossItemCode">
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
							<input type="hidden" name="prpLcomponentPartCode">
							<input type="hidden" name="prpLcomponentPartName">
							<input type="hidden" name="prpLcomponentManHourFee">
							<input type="hidden" name="prpLcomponentLossRate">
							<input type="hidden" name="prpLcomponentCurrency">
							<input type="hidden" name="prpLcomponentVeriRemark">
							<input type="hidden" name="prpLcomponentVeriQuantity">
							<input type="hidden" name="prpLcomponentVeriManHourFee">
							<input type="hidden" name="prpLcomponentVeriMaterFee">
							<input type="hidden" name="prpLcomponentVeriLossRate">
							<input type="hidden" name="prpLcomponentVeriRestFee">
							<input type="hidden" name="prpLcomponentBackCheckRemark">
							<input type="hidden" name="prpLcomponentSumVeriLoss">
							<input type="hidden" name="prpLcomponentCompensateBackFlag">
							<td class="input" style='width: 7%;' align="center">
								<div>
									<input type=button name="buttonComponentDelete" class="smallbutton" onclick="deleteRow(this,'Component');sumComponentFee();" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="SpanRepairComponent" cellspacing="1" cellpadding="0"> <c:set var="licenseNo" value="${requestScope.prpLcheck.licenseNo}" scope="page" /> <c:if test="${empty licenseNo}">
					<c:set var="licenseNo" value="" scope="page" />
				</c:if> <c:if test="${carLossSize > 0}">
					<c:choose>
						<c:when test="${requestScope.certainLossDto !=null}">
							<c:set var="prpLcarLossList" value="${requestScope.certainLossDto.prpLcarLossList}" scope="page" />
						</c:when>
						<c:otherwise>
							<c:set var="prpLcarLossList" value="${requestScope.verifyLossDto.prpLcarLossList}" scope="page" />
						</c:otherwise>
					</c:choose>
					<c:forEach items="${pageScope.prpLcarLossList}" var="prpLcarLoss">
						<c:set var="prpLrepairFeeRepairStartDate" value="" scope="page" />
						<c:set var="prpLrepairFeeRepairEndDate" value="" scope="page" />
						<c:set var="prpLrepairFeeHandlerCode" value="" scope="page" />
						<c:set var="prpLrepairFeeHandlerName" value="" scope="page" />
						<c:choose>
							<c:when test="${fn:length(requestScope.prpLrepairFee.repairFeeList)>0}">
								<c:forEach items="${requestScope.prpLrepairFee.repairFeeList}" var="prpLrepairFee">
									<c:if test="${prpLrepairFee.id.lossItemCode == prpLcarLoss.id.lossItemCode}">
										<c:set var="prpLrepairFeeRepairStartDate" value="${prpLrepairFee.repairStartDate}" scope="page" />
										<c:set var="prpLrepairFeeRepairEndDate" value="${prpLrepairFee.repairEndDate}" scope="page" />
										<c:set var="prpLrepairFeeHandlerCode" value="${prpLrepairFee.handlerCode}" scope="page" />
										<c:set var="prpLrepairFeeHandlerName" value="${prpLrepairFee.handlerName}" scope="page" />
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${fn:length(requestScope.prpLcomponent.componentList)>0}">
										<c:forEach items="${requestScope.prpLcomponent.componentList}" var="prpLcomponent">
											<c:if test="${prpLcomponent.id.lossItemCode == prpLcarLoss.id.lossItemCode}">
												<c:set var="prpLrepairFeeRepairStartDate" value="${prpLcomponent.repairStartDate}" scope="page" />
												<c:set var="prpLrepairFeeRepairEndDate" value="${prpLcomponent.repairEndDate}" scope="page" />
												<c:set var="prpLrepairFeeHandlerCode" value="${prpLcomponent.handlerCode}" scope="page" />
												<c:set var="prpLrepairFeeHandlerName" value="${prpLcomponent.handlerName}" scope="page" />
											</c:if>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:set var="prpLrepairFeeHandlerCode" value="${sessionScope.user.userCode}" scope="page" />
										<c:set var="prpLrepairFeeHandlerName" value="${sessionScope.user.userName}" scope="page" />
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:set var="sysAreaCode" value="${sessionScope.user.sysAreaCode}" />
						<c:set var="localAreaCode" value="${sessionScope.user.comCode}" />
						<c:choose>
							<c:when test="${fn:startsWith(localAreaCode,'44')}">
								<c:set var="localAreaCode" value="${(fn:substring(localAreaCode,0,4))}${'0000'}" />
							</c:when>
							<c:otherwise>
								<c:set var="localAreaCode" value="${(fn:substring(localAreaCode,0,2))}${'000000'}" />
							</c:otherwise>
						</c:choose>
						<c:set var="showPriceFlag" value="${sessionScope.user.showPriceFlag}" />
						<input type="hidden" name="carLossSize" value="${carLossSize}">
						<table class=common cellpadding="5" cellspacing="1">
							<tr>
								<td class="subformtitle" colspan="6">
									<s:text name="certainLoss.vehicleInformation" />
								</td>
								<!--理赔车辆信息-->
							</tr>
						</table>
						<table class=common cellpadding="5" cellspacing="1">
							<tr>
								<td class="left">
									<s:text name="certainLoss.carNumber" />：
									<!--车辆序号-->
									<input type="hidden" name="prpLcarLossSumVeriRest" value="${pageScope.prpLcarLoss.sumVeriRest}">
									<input type="hidden" name="prpLcarLossSumVerifyLoss" value="${pageScope.prpLcarLoss.sumVerifyLoss}">
									<input type="hidden" name="prpLcarLossLossDesc" value="${pageScope.prpLcarLoss.lossDesc}">
									<input type="hidden" name="prpLcarLossIndemnityDuty" value="${pageScope.prpLcarLoss.indemnityDuty}">
									<input type="hidden" name="prpLcarLossIndemnityDutyRate" value="${pageScope.prpLcarLoss.indemnityDutyRate}">
									<input type="hidden" name="prpLcarLossVeriIndeDutyRate" value="${pageScope.prpLcarLoss.veriIndeDutyRate}">
									<input type="hidden" name="prpLcarLossSumManageFeeRate" value="${pageScope.prpLcarLoss.sumManageFeeRate}">
									<input type="hidden" name="prpLcarLossBackCheckRemark" value="${pageScope.prpLcarLoss.backCheckRemark}">
									<input type="hidden" name="prpLcarLossRemark" value="${pageScope.prpLcarLoss.remark}">
									<input type="hidden" name="prpLcarLossOperatorCode" value="${pageScope.prpLcarLoss.operatorCode}">
									<input type="hidden" name="prpLcarLossApproverCode" value="${pageScope.prpLcarLoss.approverCode}">
									<input type="hidden" name="prpLcarLossFlag" value="${pageScope.prpLcarLoss.flag}">
									<input type="hidden" name="verifyPriceFlag" value="0">
									<input type="hidden" name="SysAreaCode" value="${sysAreaCode}">
									<input type="hidden" name="LocalAreaCode" value="${pageScope.localAreaCode}">
									<input type="hidden" name="ShowPriceFlag" value="${showPriceFlag}">
									<%--
										//用户价格权限查看的标记
									--%>
								</td>
								<td class="right">
									<input name="prpLcarLossLossItemCode" class="readonly" value="${pageScope.prpLcarLoss.id.lossItemCode}">
								</td>
								<c:choose>
									<c:when test="${fn:trim(pageScope.prpLcarLoss.id.lossItemCode) =='1'}">
										<td class="left">
											<s:text name="certainLoss.thirdCarLoss.car" />：
										</td>
										<!--标的车-->
										<!--modify -->
										<td class="right"></td>
										<td class="left"></td>
										<td class="right"></td>
							</tr>
							<tr>
								<td class="left">
									<s:text name="db.prpLlawsuit.licenseNo" />：
								</td>
								<!--车牌号码-->
								<input type="hidden" name="carName1" value="标的车">
								<td class="right">
									<c:choose>
										<c:when test="${param.editType == 'ADD'}">
											<input name="prpLcarLossLossItemName" class="input" value="${pageScope.licenseNo}">
											<img src="/claim/images/bgMarkMustInput.jpg">
										</c:when>
										<c:otherwise>
											<input name="prpLcarLossLossItemName" class="input" value="${pageScope.prpLcarLoss.lossItemName}">
											<img src="/claim/images/bgMarkMustInput.jpg">
										</c:otherwise>
									</c:choose>
								</td>
								<td class="left">
									<s:text name="db.prpDCarModel.modelName" />：
								</td>
								<!--车型名称-->
								<td class="right">
									<input type="hidden" name="prpLcarLossModelCode" class="codecode" description="厂牌型号" value="${pageScope.prpLcarLoss.modelCode}" ondblclick="code_CodeSelect(this,'modelCode','0,1','Y');"
										onchange="code_CodeChange(this,'modelCode','0,1','Y');" onkeyup="code_CodeSelect(this,'modelCode','0,1','Y');">
									<input type="text" name="prpLcarLossBrandName" class="codename" maxlength=50 description="厂牌型号名称" value="${pageScope.prpLcarLoss.brandName}"
										ondblclick="code_CodeSelect(this,'modelCode','-1,0','Y','N');" onchange="code_CodeChange(this,'modelCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'modelCode','-1,0','Y','N');">
								</td>
								<td class="left">
									<s:text name="certainLoss.thirdCarLoss.carKind" />：
								</td>
								<!--车辆种类-->
								<td class="right">
									<input name="prpLcarLossCarKindName" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.carKindName}">
								</td>
							</tr>
							<tr>
								<td class="left">
									<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyEngineNo" />
								</td>
								<!--发动机号-->
								<td class="right">
									<input name="prpLcarLossEngineNo" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.engineNo}">
								</td>
								<td class="left">
									<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyVINNo" />
								</td>
								<!--车架号(VIN码)-->
								<td class="right">
									<input name="prpLcarLossFrameNo" class="readonly" readonly="true" value="${pageScope.prpLcarLoss.frameNo}">
								</td>
								<td class="left"></td>
								<td class="right">
									<input name="prpLcarLossVINNo" type=hidden class="readonly" readonly="true" value="${pageScope.prpLcarLoss.VINNo}">
								</td>
							</tr>
							</c:when>
							<c:otherwise>
								<td class="left" color="red">
									<s:text name="certainLoss.thirdCarLoss.prpLcheckThirdCar" />
								</td>
								<!--三者车-->
								<td class="right">&nbsp;</td>
								<td class="left"></td>
								<td class="right"></td>
								</tr>
								<tr>
									<td class="left">
										<s:text name="db.prpLlawsuit.licenseNo" />
									</td>
									<!--号牌号码-->
									<input type="hidden" name="carName1" value="三者车">
									<td class="right">
										<input name="prpLcarLossLossItemName" class="input" value="${pageScope.prpLcarLoss.lossItemName}">
									</td>
									<td class="left">
										<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
									</td>
									<!--厂牌型号-->
									<td class="right">
										<input type="hidden" name="prpLcarLossModelCode" class="codecode" description="厂牌型号" value="${pageScope.prpLcarLoss.modelCode}" ondblclick="code_CodeSelect(this,'modelCode','0,1','Y');"
											onchange="code_CodeChange(this,'modelCode','0,1','Y');" onkeyup="code_CodeSelect(this,'modelCode','0,1','Y');">
										<input type="text" name="prpLcarLossBrandName" class="codename" maxlength=50 description="厂牌型号名称" value="${pageScope.prpLcarLoss.brandName}"
											ondblclick="code_CodeSelect(this,'modelCode','-1,0','Y','N');" onchange="code_CodeChange(this,'modelCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'modelCode','-1,0','Y','N');">
									</td>
									<td class="left">
										<s:text name="certainLoss.thirdCarLoss.carKind" />
									</td>
									<!--车辆种类-->
									<td class="right">
										<select name="carKindCode">
											<c:forEach items="${carKindCodes}" var="prpDcode">
												<option value="${prpDcode.id.codeCode}" <c:if test="${prpLthirdParty1.carKindCode ==prpDcode.id.codeCode}">
                                   <c:out value="selected"/></c:if>>
													<c:out value="${prpDcode.codeCName}" />
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td class="left">
										<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyEngineNo" />
									</td>
									<!--发动机号-->
									<td class="right">
										<input name="prpLcarLossEngineNo" class="input" value="${pageScope.prpLcarLoss.engineNo}">
									</td>
									<td class="left">
										<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyFrameNo" />
									</td>
									<!--车架号-->
									<td class="right">
										<input name="prpLcarLossFrameNo" class="input" value="${pageScope.prpLcarLoss.frameNo}">
									</td>
									<td class="left"></td>
									<td class="right">
										<input name="prpLcarLossVINNo" type=hidden class="input" value="${pageScope.prpLcarLoss.VINNo}">
									</td>
								</tr>
						</table>
						</c:otherwise>
						</c:choose>
						<input type="hidden" name="prpLcarLossLicenseColorCode" value="${pageScope.prpLcarLoss.licenseColorCode}">
						<input type="hidden" name="prpLcarLossCarKindCode" value="${pageScope.prpLcarLoss.carKindCode}">
						<input type="hidden" name="prpLcarLossInsureCarFlagName" value="${pageScope.prpLcarLoss.insureCarFlagName}">
						<input type="hidden" name="prpLcarLossInsureCarFlag" value="${pageScope.prpLcarLoss.insureCarFlag}">
						<input type="hidden" name="prpLcarLossInsureComName" value="${pageScope.prpLcarLoss.insureComName}">
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
									<input type="hidden" class=button name="certainLossCarModel" value="<s:text name='button.vehicleTrain.value'/>" onclick="openWinCertainLossCarModel();">
									<!--车辆定型-->
								</td>
								<td class="left">
									<s:text name="certainLoss.garageType" />：
								</td>
								<!--修理厂类型-->
								<td class="right">
									<%--                       <select name="prpLrepairFeeRepairFactoryCode" class="three" style="width:80%">--%>
									<%--                           <option value=""   <c:if test="${requestScope.prpLverifyLoss.repairFactoryCode==''}" ><c:out value="selected" /></c:if>></option>--%>
									<%--                           <option value="03" <c:if test="${requestScope.prpLverifyLoss.repairFactoryCode=='03'}"><c:out value="selected" /></c:if>>二类厂</option>                                  --%>
									<%--                           <option value="02" <c:if test="${requestScope.prpLverifyLoss.repairFactoryCode=='02'}"><c:out value="selected" /></c:if>>一类厂</option>                   --%>
									<%--                           <option value="01" <c:if test="${requestScope.prpLverifyLoss.repairFactoryCode=='01'}"><c:out value="selected" /></c:if>>4S店</option>--%>
									<%--                           <option value="04" <c:if test="${requestScope.prpLverifyLoss.repairFactoryCode=='04'}"><c:out value="selected" /></c:if>>其他</option>--%>
									<%--                        </select>--%>
									<c:set var="tempSelectedValue" value="${requestScope.prpLverifyLoss.repairFactoryCode}" />
									<s:select name="prpLrepairFeeRepairFactoryCode" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.feeRepairFactoryCodeList" />
									<input name="prpLrepairFeeRepairFactoryType" type="hidden" value="">
									<!--img src="/claim/images/bgMarkMustInput.jpg"-->
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
									<%-- <input name="prpLrepairFeeRepairStartDate" class="input" value="${pageScope.prpLrepairFeeRepairStartDate}">--%>
									<rc:rcDate name="prpLrepairFeeRepairStartDate" value="${pageScope.prpLrepairFeeRepairStartDate}" />
								</td>
								<td class="left">
									<s:text name="db.prpLcomponent.repairEndDate" />：
								</td>
								<!--约定交车日期-->
								<td class="right">
									<%-- <input name="prpLrepairFeeRepairEndDate" class="input" value="${pageScope.prpLrepairFeeRepairEndDate}">--%>
									<rc:rcDate name="prpLrepairFeeRepairEndDate" class="input" value="${pageScope.prpLrepairFeeRepairEndDate}" />
								</td>
								<td class="left"></td>
								<td class="right"></td>
							</tr>
							<tr>
								<td class="left">
									<s:text name="db.prpLsalvation.handlerCode" />：
								</td>
								<!--经办人代码-->
								<td class="right">
									<input name="prpLrepairFeeHandlerCode" class="codecode" value="${pageScope.prpLrepairFeeHandlerCode}" ondblclick="code_CodeSelect(this,'handerCode','0,1','Y');"
										onchange="code_CodeChange(this,'handerCode','0,1','Y');" onkeyup="code_CodeSelect(this,'handerCode','0,1','Y');">
								</td>
								<td class="left">
									<s:text name="certainLoss.managerName" />：
								</td>
								<!--经办人名称-->
								<td class="right">
									<input name="prpLrepairFeeHandlerName" class="codename" value="${pageScope.prpLrepairFeeHandlerName}" ondblclick="code_CodeSelect(this,'handerCode','-1,0','Y','N');"
										onchange="code_CodeChange(this,'handerCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'handerCode','-1,0','Y','N');">
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
									<input name="prpLcarLossCurrencyName" class="readonly" readonly="true" value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>">
									<input type="hidden" name="prpLcarLossCurrency" class="readonly" readonly="true" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
								</td>
								<td class="left">
									<s:text name="certainLoss.totalAmount" />：
								</td>
								<!--总定损金额-->
								<td class="right">
									<input name="prpLcarLossSumCertainLoss" class="readonly" readonly="readonly" value="<fmt:formatNumber value="${pageScope.prpLcarLoss.sumCertainLoss}" pattern="#"/>">
									<input name="prpLDeductible" type="hidden" readonly="true" value="${requestScope.prpLDeductible}">
								</td>
								<td class="left">
									<%--可选免赔额--%>
									<c:if test="${requestScope.prpLDeductible > 0.00}"><s:text name="certainLoss.optionalDeductible" />：</c:if>
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
									<%@include file="/pages/DAA/certainLoss/DAAComponentSubList.jsp"%>
								</td>
							</tr>
							<tr>
								<td class="title" style="width: 100%" colspan="4">
									<%--修理项目费用清单--%>
									<%@include file="/pages/DAA/certainLoss/DAARepairFeeSubList.jsp"%>
								</td>
							</tr>
							<tr STYLE="Display: none">
								<td class="title" style="width: 15%">
									<s:text name="certainLoss.needComplex" />:
								</td>
								<!--是否需要复勘-->
								<td class="input" style='width: 85%' colspan="3">
								<!-- 
		                          <input type="radio" name="prpLcarLossBackCheckFlag" value="1" <c:if test="${pageScope.prpLcarLoss.backCheckFlag=='1'}"><c:out value="checked"/></c:if>>是
		                          <input type="radio" name="prpLcarLossBackCheckFlag" value="0" <c:if test="${pageScope.prpLcarLoss.backCheckFlag=='0'}"><c:out value="checked"/></c:if>>否  
		                         -->
									<input type="radio" name="prpLcarLossBackCheckFlag" value="1">
									<s:text name="certainLoss.thirdCarLoss.yes" />
									<!--是-->
									<input type="radio" name="prpLcarLossBackCheckFlag" value="0" checked>
									<s:text name="certainLoss.thirdCarLoss.no" />
									<!--否-->
								</td>
							</tr>
							<tr>
								<td class="title" style="width: 15%"></td>
								<td class="input" style='width: 85%' colspan="3">
									<!--<input type="hidden" name="prpLrepairFeeVeriManHourFee" value="1">              -->
									<input type="hidden" name="prpLcarLossBackCheckFlagCheck" <c:if test="${pageScope.prpLcarLoss.backCheckFlag=='1'}"><c:out value="checked"/></c:if>>
									<input type="hidden" name="prpLcarLossBackCheckFlag" value="${pageScope.prpLcarLoss.backCheckFlag}">
								</td>
							</tr>
						</table>
					</c:forEach>
				</c:if>
			</span>
		</td>
	</tr>
</table>
<%-- 3. 核价、核损意见、备注 --%>
<%@include file="/pages/DAA/certainLoss/DAACertainLossOpinion.jsp"%>
<%-- 4. 定核损信息补充说明 --%>
<%@include file="/pages/DAA/certainLoss/DAACertainLossExtEdit.jsp"%>