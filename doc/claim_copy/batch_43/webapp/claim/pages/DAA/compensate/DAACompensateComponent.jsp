<%--
****************************************************************************
* DESC       ：换件信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-03-06
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<span style="display: none"> <%--/**添加换件项目导入的页签*/--%>
	<table class="common" style="display: none" id="Component_Data" cellspacing="1" cellpadding="0">
		<tbody>
			<tr name="TrComponent">
				<td class="input" style="width: 15%">
					<input type="hidden" name="carLossComponentLossItemCode">
					<input type="hidden" name="prpLcomponentVeriSumDefLoss">
					<input type="hidden" name="prpLcomponentVeriVerpCompPrice">
					<input type="hidden" name="prpLcomponentKindCode" class="codecode" ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
						onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
					<input type="text" name="prpLcomponentKindName" class="codecode" style='width: 100%;' ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
						onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
					<input type="hidden" name="prpLcomponentItemKindNo" value="1">
				</td>
				<td class="input" style="width: 23%">
					<input type="text"  name="prpLcomponentCompName" class=common style='width: 100%' maxlength="100">
					<input type='hidden' name="prpLcomponentCompCode"  value="">
				</td>
				<td class="input" style="width: 10%">
					<input type="text"  name="prpLcomponentOriginalId" class=common style='width: 100%'>
					<input type="hidden" name="prpLcomponentSys4SPrice" class="common" readonly style='width: 100%'>
					<input type="hidden" name="prpLcomponentSysMarketPrice" class="common" readonly style='width: 100%'>
					<input type="hidden" name="prpLcomponentSysMatchPrice" class="common" readonly style='width: 100%'>
				</td>
				<td class="input" style="width: 4%">
					<input type="text" name="prpLcomponentNative4SPrice" class="common" style='width: 100%'>
				</td>
				<td class="input" style="width: 3%">
					<input type="text" name="prpLcomponentNativeMarketPrice" class="common" style='width: 100%'>
				</td>
				<td class="input" style="width: 3%">
					<input type="text" name="prpLcomponentNativeMatchPrice" class="common" style='width: 100%'>
					<input type="hidden" name="prpLcomponentFirstMaterialFee"  class=readonly readonly style='width: 100%'>
					<input type="hidden" name="prpLcomponentRepairFactoryFee" maxlength=10 class=common style='width: 100%'>
				</td>
				<td class="input" style='width: 5%;'>
					<input type="text" name="prpLcomponentMaterialFee" maxlength=10 class=common style="width: 100%" onchange="getComponentSumLoss(this);">
				</td>
				<td class="input" style='width: 5%;'>
					<input type="text" name="prpLcomponentQuantity" value="1" class=common style='width: 100%' onchange="getComponentSumLoss(this);">
				</td>
				<td class="input" style="width: 5%">
					<s:select name="prpLcomponentIfRemain" listKey="key" listValue="value" list="#request.ifRemainList" style='width:100%' onchange="getComponentSumLoss(this);" />
				</td>
				<td class="input" style='width: 5%;'>
					<input type="text" name="prpLcomponentRestFee" style='width: 100%' class=common onchange="getComponentSumLoss(this);">
				</td>
				<td class="input" style='width: 5%;'>
					<input type="text" name="prpLcomponentSumDefLoss" class=readonly readonly style="width: 100%" onchange="getComponentSumLoss(this);">
					<input type="hidden"  name="prpLcomponentVerpCompPrice" class="common" readonly style='width: 100%' value="0">
					<input type="hidden" name="prpLcomponentVerpCompPriceLast" >
				</td>
				<td class="input" style="width: 5%">
					<input type="text" name="prpLcomponentRemark" maxlength=60 class=common style='width: 100%'>
					<input name="verpoFlag" type="hidden" value="0">
					<input name="prpLcomponentFlag" type=hidden value="0">
					<input name="prpLcomponentIndId" type=hidden>
					<input type="hidden" name="prpLcomponentSerialNo">
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
					<input type="hidden" name="prpLcomponentPriceType" value="S">
				</td>
				<td class="input" style='width: 7%;' align="center">
					<div>
						<input type=button name="buttonComponentDelete" class="smallbutton" onclick="deleteRow(this,'Component','prpLcomponentSerialNo');sumComponentRepairFee();" value="-" style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<span id="spanComponent" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="Component">
					<thead>
						<tr>
							<td class="subformtitle" colspan=13>
								<s:text name="certainLoss.ProjectCosts" />
								<!--零部件更换项目费用清单-->
							</td>
						</tr>
						<tr>
							<td class="centertitle" style="width: 15%" rowspan="2">
								<s:text name="db.prpLendor.kindName" />
								<!--险别名称-->
							</td>
							<td class="centertitle" style="width: 23%" rowspan="2">
								<s:text name="certainLoss.partName" />
								<!--部件名称-->
							</td>
							<td class="centertitle" style="width: 10%" rowspan="2">
								<s:text name="certainLoss.originalEncoding" />
								<!--原厂编码-->
							</td>
							<td colspan="3" class="centertitle" style="width: 15%">
								<s:text name="certainLoss.localQuotes" />
								<!--本地报价-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="certainLoss.lossPrice" />
								<!--定损单价-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="certainLoss.numberReplacement" />
								<!--更换数量-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="certainLoss.whetherRecycling" />
								<!--是否回收-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="db.prpLpersonloss.sumRest" />
								<!--残值-->
							</td>
							<td rowspan="2" class="centertitle" style="width: 5%">
								<s:text name="certainLoss.subtotal" />
								<!--小计-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="db.prpLcomponent.remark" />
								<!--备注-->
							</td>
							<td class="centertitle" style="width: 7%;" rowspan="2">
								&nbsp; &nbsp;
							</td>
						</tr>
						<tr>
							<td class="centertitle" style="width: 6%">
								<s:text name="certainLoss.price" />
								<!--专修价-->
							</td>
							<td class="centertitle" style="width: 6%">
								<s:text name="certainLoss.marketPrice" />
								<!--市场价-->
							</td>
							<td class="centertitle" style="width: 6%">
								<s:text name="certainLoss.factoryPrice" />
								<!--副厂价-->
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan=13>
								<table class="common" cellspacing="1" cellpadding="0" style="width: 100%;">
									<tbody>
										<tr>
											<td colspan="12" align="left" style="width: 93%;">
												<s:text name="prompt.certainLoss.addRemoveChange" />
											</td>
											<!--(按"+"号键增加换件信息，按"-"号键删除信息)-->
											<td class="title" align="right" style="width: 7%">
												<div align="center">
													<input type="button" value="+" class=smallbutton onclick="insertRow('Component',this,'prpLcomponentSerialNo');" name="buttonGetFittings" style="cursor: hand">
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="13">
								<table border="0" align="center"  cellspacing="1" class="title" style="width: 100%;">
									<tr>
										<td class='title' style="width: 2%"></td>
										<td class='title' style="width: 12%" align="right">
											<s:text name="certainLoss.TotalResiduals" />:
											<!--残值合计-->
										</td>
										<td class='input' style="width: 12%">
											<input type="hidden" name="selectCarFittings">
											<input type="text" name="prpLcarLossSumRest" class="readonly" readonly value="<fmt:formatNumber value='${prpLcarLoss.sumRest}' pattern='#'/>" >
										</td>
										<td class='title' style="width: 5%" align="right"></td>
										<td class='title' style="width: 8%" align="right"></td>
										<td class='title' style="width: 16%" align="right">
											<s:text name="certainLoss.changeTotals" />:
											<!--换件费合计-->
										</td>
										<td class='input' style="width: 12%">
											<input type="text" name="SumDefLoss2" class="readonly" readonly style='width: 80px'>
										</td>
										<td class='title' style="width: 17%" align="right"></td>
										<td class='input' style="width: 17%" colspan="5">
											<input name="SumVerifyLoss2" type="hidden" class="readonly" readonly style='width: 80px' value="${prpLcarLoss.sumVerifyLoss }">
											<input type=hidden class='readonly' class="input" readonly="true" style='width: 80px' name='SumManHourFee2'>
											<input type=hidden class='readonly' readonly="true" class="input" style='width: 80px' name='SumMaterialFee2'>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</tfoot>
					<tbody >
						<c:forEach items="${requestScope.prpLcomponent.componentList}" var="prpLcomponent">
							<tr name="TrComponent">
								<td class="input" style="width: 10%;">
									<input type="hidden" name="carLossComponentLossItemCode"  style="width: 20px" value="${prpLcomponent.id.lossItemCode-1 }">
									<input type="hidden" name="prpLcomponentVeriSumDefLoss" value="<fmt:formatNumber value='${prpLcomponent.sumVeriLoss }' pattern='#'/>">
									<input type="hidden" name="prpLcomponentVeriVerpCompPrice" value="${prpLcomponent.sumVeriLoss }">
									<input type="hidden" name="prpLcomponentKindCode" class="codecode" style="width: 100%;"  value="${prpLcomponent.kindCode}"
                                          ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
                                          onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
                                       >
									<input type="text" name="prpLcomponentKindName" class="codecode" style="width: 100%;" value="${prpLcomponent.kindName}"
                                       	ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
                                           onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
                                   	>
                                   	<input type="hidden" name="prpLcomponentItemKindNo" value="${prpLcomponent.itemKindNo}">
								</td>
								<td class="input" style="width: 23%;">
									<input  name="prpLcomponentCompCode" type='hidden' value="${prpLcomponent.compCode}" >
									<input type="text" name="prpLcomponentCompName" class="common" maxlength="100" style="width: 100%;"  value="${prpLcomponent.compName}" >
								</td>
								<td class="input" style="width: 10%;">
									<input type="text" name="prpLcomponentOriginalId" class="common" style="width: 100%;" value="${prpLcomponent.originalId}" >
									<input type="hidden" name="prpLcomponentSys4SPrice" class="common" style="width: 100%;" value="${prpLcomponent.sys4SPrice}">
									<input name="prpLcomponentSysMarketPrice" type="hidden" class="common" readonly style="width: 100%;"  value="${prpLcomponent.sysMarketPrice}">
									<input name="prpLcomponentSysMatchPrice" type="hidden" class="common" readonly style="width: 100%; " value="${prpLcomponent.sysMatchPrice}">
								</td>
								<td class="input" style="width: 4%">
									<input type="text" name="prpLcomponentNative4SPrice" class="common" style="width: 100%; "  value="<fmt:formatNumber value='${prpLcomponent.native4SPrice}' pattern='#'/>">
								</td>
								<td class="input" style="width: 3%">
									<input type="text" name="prpLcomponentNativeMarketPrice" class="common" style="width: 100%; "  value="<fmt:formatNumber value='${prpLcomponent.nativeMarketPrice}' pattern='#'/>">
								</td>
								<td class="input" style="width: 3%">
									<input name="prpLcomponentNativeMatchPrice" class="common" style="width: 100%; "  value="<fmt:formatNumber value='${prpLcomponent.nativeMatchPrice}' pattern='#'/>">
								</td>
								<input name="prpLcomponentFirstMaterialFee" type="hidden" class=readonly readonly style='width: 100%;'>
								<td class="input" style="display: none">
									<input type="text" name="prpLcomponentRepairFactoryFee" class=common style='width: 100%;' maxlength=10 value="<fmt:formatNumber value='${prpLcomponent.repairFactoryFee}' pattern='#'/>">
								</td>
								<td class="input" style="width: 5%;">
									<input type="text" name="prpLcomponentMaterialFee" class=common maxlength=10 style="width: 100%; " value="<fmt:formatNumber value='${prpLcomponent.materialFee}' pattern='#'/>" onchange="getComponentSumLoss(this);">
								</td>
								<td class="input" style="width: 5%;">
									<input type="text" name="prpLcomponentQuantity" class="common" style="width: 100%; "  value="${prpLcomponent.quantity}" onchange="getComponentSumLoss(this);">
								</td>
								<td class="input" align="center" style="width: 5%;">
									<c:set var="tempSelectedValue" value="${prpLcomponent.ifRemain}" />
									<s:select name="prpLcomponentIfRemain" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.ifRemainList" style='width: 100%' onchange="getComponentSumLoss(this);" />
								</td>
								<td class="input" style="width: 5%;">
									<input type="text" name="prpLcomponentRestFee" class="common" style='width: 100%;' value="<fmt:formatNumber value='${prpLcomponent.restFee}' pattern='#'/>" onchange="getComponentSumLoss(this);">
								</td>
								<td class="input" style="width: 5%;">
									<input type="text" name="prpLcomponentSumDefLoss" class="readonly=" readonly="readonly" style="width: 100%; "  value="<fmt:formatNumber value='${prpLcomponent.sumDefLoss}' pattern='#'/>" >
									<input name="prpLcomponentVerpCompPrice" type="hidden" class="common"  value="${prpLcomponent.verpCompPrice}">
									<input name="prpLcomponentVerpCompPriceLast" type="hidden" value="${prpLcomponent.verpCompPrice}">
								</td>
								<td class="input" style="width: 5%;">
									<input type="text" name="prpLcomponentRemark" maxlength=60 class=common style="width: 100%; "  value="${prpLcomponent.remark}">
									<input name="verpoFlag" type="hidden" value="${prpLcomponent.verpoFlag}">
									<input name="prpLcomponentFlag" type=hidden value="${prpLcomponent.flag}">
									<input name="prpLcomponentIndId" type=hidden value="${prpLcomponent.indId}">
									<input type="hidden" name="prpLcomponentSerialNo" value="${prpLcomponent.id.serialNo}">
									<input type="hidden" name="prpLcomponentLossItemCode" value="${prpLcomponent.id.lossItemCode}">
									<input type="hidden" name="prpLcomponentLicenseNo" value="${prpLcomponent.licenseNo}">
									<input type="hidden" name="prpLcomponentLicenseColorCode" value="${prpLcomponent.licenseColorCode}">
									<input type="hidden" name="prpLcomponentCarKindCode" value="${prpLcomponent.carKindCode}">
									<input type="hidden" name="prpLcomponentMakeYear" value="${prpLcomponent.makeYear}">
									<input type="hidden" name="prpLcomponentGearboxType" value="${prpLcomponent.gearboxType}">
									<input type="hidden" name="prpLcomponentQuoteCompanyGrade" value="${prpLcomponent.quoteCompanyGrade}">
									<input type="hidden" name="prpLcomponentManageFeeRate" value="${prpLcomponent.manageFeeRate}">
									<input type="hidden" name="prpLcomponentRepairFactoryCode" value="${prpLcomponent.repairFactoryCode}">
									<input type="hidden" name="prpLcomponentRepairFactoryName" value="${prpLcomponent.repairFactoryName}">
									<input type="hidden" name="prpLcomponentHandlerCode" value="${prpLcomponent.handlerCode}">
									<input type="hidden" name="prpLcomponentRepairStartDate" value="${prpLcomponent.repairStartDate}">
									<input type="hidden" name="prpLcomponentRepairEndDate" value="${prpLcomponent.repairEndDate}">
									<input type="hidden" name="prpLcomponentSanctioner" value="${prpLcomponent.sanctioner}">
									<input type="hidden" name="prpLcomponentApproverCode" value="${prpLcomponent.approverCode}">
									<input type="hidden" name="prpLcomponentOperatorCode" value="${prpLcomponent.operatorCode}">
									<input type="hidden" name="prpLcomponentQueryPrice" value="${prpLcomponent.queryPrice}">
									<input type="hidden" name="prpLcomponentQuotedPrice" value="${prpLcomponent.quotedPrice}">
									<input type="hidden" name="prpLcomponentPartCode" value="${prpLcomponent.partCode}">
									<input type="hidden" name="prpLcomponentPartName" value="${prpLcomponent.partName}">
									<input type="hidden" name="prpLcomponentManHourFee" value="${prpLcomponent.manHourFee}">
									<input type="hidden" name="prpLcomponentBackCheckRemark" value="${prpLcomponent.backCheckRemark}">
									<input type="hidden" name="prpLcomponentLossRate" value="${prpLcomponent.lossRate}">
									<input type="hidden" name="prpLcomponentCurrency" value="${prpLcomponent.currency}">
									<input type="hidden" name="prpLcomponentVeriRemark" value="${prpLcomponent.remark}">
									<input type="hidden" name="prpLcomponentVeriQuantity" value="${prpLcomponent.veriQuantity}">
									<input type="hidden" name="prpLcomponentVeriMaterFee" value="<fmt:formatNumber value='${prpLcomponent.veriMaterFee}' pattern='#'/>">
									<input type="hidden" name="prpLcomponentVeriManHourFee" value="${prpLcomponent.veriManHourFee}">
									<input type="hidden" name="prpLcomponentVeriLossRate" value="${prpLcomponent.veriLossRate}">
									<input type="hidden" name="prpLcomponentVeriRestFee" value="<fmt:formatNumber value='${prpLcomponent.veriRestFee}' pattern='#'/>">
									<input type="hidden" name="prpLcomponentCompensateBackFlag" value="${prpLcomponent.compensateBackFlag}">
									<input type="hidden" name="prpLcomponentPriceType" value="${prpLcomponent.priceType}">
								</td>
								<td class="input" style='width: 7%;' align="center">
									<div>
										<input type=button name="buttonComponentDelete" class=smallbutton onclick="deleteRow(this,'Component','prpLcomponentSerialNo');sumComponentRepairFee();" value="-" style="cursor: hand">
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>