<%-- 换件项目隐藏域 --%>
<table class="common" style="display: none" id="Component_Data" cellspacing="1" cellpadding="0">
	<tbody>
		<tr>
			<td class="input" style="width: 8%">
				<input type="hidden" class="common" name="prpLcomponentLicenseNo" value="">
				<input type="hidden" class="common" name="prpLcomponentLossItemCode" value="">
				<input type="hidden" class="readonly" readonly name="prpLcomponentSerialNo" value="">
				<input type="hidden" class="readonly" readonly name="prpLcomponentIndId" value="">
				<input type="hidden" name="prpLcomponentKindCode">
				<logic:equal name="advance" value="1">
					<input type="text" name="prpLcomponentKindName" class="codecode" ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
						onkeyup="code_CodeSelect(this,'PolicyKindCode','-1,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);">
				</logic:equal>
				<logic:notEqual name="advance" value="1">
					<input type="text" name="prpLcomponentKindName" class="codecode" ondblclick="kindNameSelect(this,'prpLcomponentLossItemCode');" onkeyup="kindNameSelect(this,'prpLcomponentLossItemCode');">
				</logic:notEqual>
			</td>
			<td class="input" style="width: 8%">
				<input name="prpLcomponentCompCode" type="hidden" class=common value="">
				<input name="prpLcomponentCompName" type="text" class=common value="">
			</td>
			<td class="input" style="width: 8%">
				<input name="prpLcomponentOriginalId" class=common value="">
			</td>
			<td class="input" style="width: 7%">
				<input name="prpLcomponentSys4SPrice" type="hidden" class=common value="">
				<input name="prpLcomponentNative4SPrice" type="text" readonly class=common value="">
			</td>
			<td class="input" style="width: 7%">
				<input name="prpLcomponentSysMarketPrice" type="hidden" class=common value="">
				<input name="prpLcomponentNativeMarketPrice" type="text" readonly class=common value="">
			</td>
			<td class="input" style="width: 7%">
				<input name="prpLcomponentSysMatchPrice" type="hidden" class=common value="">
				<input name="prpLcomponentNativeMatchPrice" type="text" readonly class=common value="">
			</td>
			<td class="input" style="width: 9%">
				<input name="prpLcomponentRepairFactoryFee" type="text" class=common value="0">
			</td>
			<td class="input" style="width: 9%">
				<input type="hidden" name="prpLcomponentVerpCompPrice" value="0">
				<!--input name="prpLcomponentSumDefLoss" type="text" class=common value="0"
         		onchange="checkSumDefLoss(this);"-->
				<input name="prpLcomponentMaterialFee" type="text" class=common value="0" onchange="checkSumDefLoss(this);">
			</td>
			<td class="input" style="width: 6%">
				<select name="prpLcomponentPriceType" class="three" style="width: 60px" onchange="changePriceType(this);setSumDefLoss(this);">
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
			<td class="input" style="width: 5%">
				<input name="prpLcomponentQuantity" type="text" class=common value="0" onchange="setSumDefLoss(this);">
			</td>
			<td class="input" style="width: 9%">
				<input name="prpLcomponentRestFee" type="text" class=common value="0" onchange="setSumDefLoss(this);">
			</td>
			<td class="input" style="width: 8%">
				<input name="prpLcomponentSumDefLoss" type="text" class=common value="0">
			</td>
			<td class="input" style="width: 5%">
				<input name="prpLcomponentRemark" type="text" class=common value="">
				<input name="prpLcomponentFlag" type="hidden" value="">
			</td>
			<td class="input" style="width: 4%">
				<div>
					<input type=button name="buttonComponentDelete" class="smallbutton" onclick="deleteRow(this,'Component');setSumDefLoss(this);" value="-" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tbody>
</table>
<%-- 修理项目隐藏域 --%>
<table class="common" style="display: none" id="RepairFee_Data" cellspacing="1" cellpadding="0">
	<tbody>
		<tr>
			<td class="input" style="width: 11%">
				<input type="hidden" class="common" name="prpLrepairFeeLicenseNo" value="">
				<input type="hidden" class="common" name="prpLRepairFeeLossItemCode" value="">
				<input type="hidden" name="prpLRepairFeeSerialNo" class=readonly readonly value="">
				<input type="hidden" name="prpLrepairFeeKindCode" class="codecode" value="">
				<logic:equal name="advance" value="1">
					<input type="text" name="prpLrepairFeeKindName" class="codecode" value="" ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
						onkeyup="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);">
				</logic:equal>
				<logic:notEqual name="advance" value="1">
					<input type="text" name="prpLrepairFeeKindName" class="codecode" value="" ondblclick="kindNameSelect(this,'prpLRepairFeeLossItemCode');"
						onkeyup="kindNameSelect(this,'prpLRepairFeeLossItemCode');">
				</logic:notEqual>
			</td>
			<td class="input" style="width: 8%">
				<html:select name="prpLrepairFeeDto" property="partCode">
					<html:options collection="partCodeList" property="value" labelProperty="label" />
				</html:select>
				<input name="prpLrepairFeePartCode" type="hidden" value="">
			</td>
			<td class="input" style="width: 11%">
				<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
				<input name="prpLrepairFeeCompName" class="codename" maxlength="100" ondblclick="return openPrplRepairFeeCompWin(RepairFee_Data,this);" ondblchange="return getCompCode(RepairFee_Data,this);"
					value="">
				<input type="hidden" name="prpLrepairFeeCompCode" value="">
			</td>
			<td class="input" style="width: 8%">
				<html:select name="prpLrepairFeeDto" property="repairType">
					<html:options collection="repairTypeCodes" property="codeCode" labelProperty="codeCName" />
				</html:select>
			</td>
			<td class="input" style="width: 8%">
				<input name="prpLrepairFeeManHour" class=common value="0" onchange="setSumRepairFee(this);">
			</td>
			<td class="input" style="width: 12%">
				<input name="prpLrepairFeeManHourUnitPrice" class="common" value="0" onchange="setSumRepairFee(this);">
			</td>
			<td class="input" style="width: 12%">
				<input name="prpLrepairFeeSumDefLoss" class="readonly" readonly value="">
			</td>
			<td class="input" style="width: 12%">
				<input name="prpLrepairFeeFirstSumDefLoss" class="readonly" readonly value="">
			</td>
			<td class="input" style="width: 14%">
				<input name="prpLrepairFeeRemark" class=common value="">
			</td>
			<td class="input" style="width: 4%">
				<div>
					<input type=button name="buttonRepairFeeDelete" class="smallbutton" onclick="deleteRow(this,'RepairFee');setSumRepairFee(this);" value="-" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tbody>
</table>
<%-- 财产损失项目隐藏域--%>
<table class="common" style="display: none" id="CertainLossCarProp_Data" cellspacing="1" cellpadding="0">
	<tbody>
		<tr>
			<td class="input" style="width: 12%">
				<input type="hidden" class="common" name="prpLpropCarDtoLicenseNo" value="">
				<input type="hidden" class="common" name="prpLPropCarSerialno">
				<input type="hidden" name="prpLPropSerialNo" class=common>
				<input type="text" name="prpLpropCarDtoKindCode" class=codecode value="" ondblclick="kindCodeSelect(this,'prpLPropCarSerialno');" onkeyup="kindCodeSelect(this,'prpLPropCarSerialno');">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropCarDtoKindName" class=codecode value="" ondblclick="kindNameSelect(this,'prpLPropCarSerialno');" onkeyup="kindNameSelect(this,'prpLPropCarSerialno');">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropCarDtoLossItemName" class=common value="">
			</td>
			<td class="input" style="width: 12%">
				<html:select name="prpLpropDto" property="feeTypeCode" style="width:80px">
					<html:options collection="FeeTypeCodeList" property="value" labelProperty="label" />
				</html:select>
				<input type="hidden" name="prpLpropCarDtoFeeTypeCode" value="">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropCarDtoSumLoss" class=common value="">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropCarDtoSumReject" class=common value="">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropCarDtoSumDefLoss" class=common value="">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropCarDtoRemark" class=common value="">
			</td>
			<td class="input" style="width: 4%">
				<div>
					<input type=button name="buttonCertainLossCarPropDelete" class="smallbutton" onclick="deleteRow(this,'CertainLossCarProp')" value="-" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tbody>
</table>
<%-- 车辆损失隐藏域 --%>
<table class="common" style="display: none" id="CertainLossCar_Data" cellspacing="1" cellpadding="0">
	<tbody>
		<tr>
			<td class="common" style="width: 4%">
				<input type="hidden" name="certainLossAdd" value="Y">
				<input type="text" class="readonly" readonly name="prpLverifyLossLossItemCode" value="">
				<input type="hidden" class="common" name="RelateSerialNo" value="">
			</td>
			<td class="common" colspan="1" style="widht: 90%">
				<%-- 车辆损失基本信息 --%>
				<%
					//@include file="/DAA/quickCase/DAAQuickCaseCertainLossCarHead.jsp"
				%>
				<table class="common" align="center" cellpadding="5" cellspacing="1">
					<tr>
						<td class="common" style="TEXT-ALIGN: center" colspan=2 style="width:30%">
							<font color=red><s:text name="certainLoss.thirdCarLoss.thirdCar" /></font>
						</td>
						<!--三者车-->
						<td class="left">
							<s:text name="title.registBeforeEdit.damage" />
							<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyLicenseNo" />
						</td>
						<!--车牌号码-->
						<td class="right">
							<input type="hidden" name="prpLcarLossInsureCarFlag" value="0">
							<input class="common" type="text" name="prpLcarLossLossItemName" value="">
						</td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="title.registBeforeEdit.damage" />
							<s:text name="db.prpLlawsuit.brandName" />
						</td>
						<!--厂牌型号-->
						<td class="right">
							<input type="hidden" name="prpLcarLossModelCode" class="codecode" description="厂牌型号" value="" ondblclick="code_CodeSelect(this,'modelCode','0,1','Y');"
								onchange="code_CodeChange(this,'modelCode','0,1','Y');" onkeyup="code_CodeSelect(this,'modelCode','0,1','Y');">
							<input type="text" name="prpLcarLossBrandName" class="codename" maxlength=50 description="厂牌型号名称" value="" ondblclick="code_CodeSelect(this,'modelCode','-1,0','Y','N');"
								onchange="code_CodeChange(this,'modelCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'modelCode','-1,0','Y','N');">
						</td>
						<td class="left">
							<s:text name="certainLoss.thirdCarLoss.carKind" />
						</td>
						<!--车辆种类-->
						<td class="right">
							<input name="prpLcarLossCarKindCode" class="readonly" readonly="true" value="">
						</td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="certainLoss.garageType" />
						</td>
						<!--修理厂类型-->
						<td class="right">
							<select name="prpLcarLossRepairFactoryCode" class="three" style="width: 80%">
								<option value=""></option>
								<option value="02">
									<s:text name="certainLoss.factory1" />
								</option>
								<!--一类厂-->
								<option value="03">
									<s:text name="certainLoss.factory2" />
								</option>
								<!--二类厂-->
								<option value="01">
									<s:text name="certainLoss.shop" />
								</option>
								<!--4S店-->
								<option value="04">
									<s:text name="certainLoss.other" />
								</option>
								<!--其它-->
							</select>
							<input name="prpLcarLossRepairFactoryType" type="hidden" value="">
						</td>
						<td class="left">
							<s:text name="db.prpLrepairFee.repairFactoryName" />
						</td>
						<!--修理厂名称-->
						<td class="right">
							<input name="prpLcheckLossRepairFactoryName" class="input" value="">
						</td>
						<td class="left">
							<s:text name="certainLoss.totalAmount" />
						</td>
						<!--总定损金额-->
						<td class="right">
							<input name="prpLcarLossSumCertainLoss" class="readonly" readonly="true" value="0">
						</td>
					</tr>
				</table>
				<%-- 修理项目费用清单 --%>
				<%
					//@include file="/DAA/quickCase/DAAQuickCaseCertainLossCarRePair.jsp"
				%>
				<table class="common" cellpadding="5" cellspacing="1">
					<tr>
						<td class="common" colspan="4">
							<table class="common" id="RepairFee" cellpadding="5" cellspacing="1">
								<thead>
									<tr>
										<td class="subformtitle" colspan=10>
											<s:text name="certainLoss.costList" />
										</td>
										<!-- 修理项目费用清单 -->
									</tr>
									<tr>
										<!-- 险别名称隐藏不显示 -->
										<!--td class="centertitle"">险别名称</td-->
										<td class="centertitle" style="width: 11%">
											<s:text name="db.prpDrate.kindName" />
										</td>
										<!-- 险别名称 -->
										<td class="centertitle" style="width: 8%">
											<s:text name="certainLoss.repairParts" />
										</td>
										<!-- 修理部位 -->
										<td class="centertitle" style="width: 11%">
											<s:text name="certainLoss.thirdCarLoss.prpLcheckAccessoryName" />
										</td>
										<!-- 零件(项目)名称 -->
										<td class="centertitle" style="width: 8%">
											<s:text name="certainLoss.repairMethods" />
										</td>
										<!-- 修理方式 -->
										<td class="centertitle" style="width: 8%">
											<s:text name="db.prpLrepairFee.manHour" />
										</td>
										<!-- 工时 -->
										<td class="centertitle" style="width: 12%">
											<s:text name="db.prpLrepairFee.manHourUnitPrice" />
										</td>
										<!-- 工时单价 -->
										<td class="centertitle" style="width: 12%">
											<s:text name="db.prpLrepairFee.manHourFee" />
										</td>
										<!-- 工时费 -->
										<td class="centertitle" style="width: 12%">
											<s:text name="certainLoss.startCost" />
										</td>
										<!-- 初始工时费  -->
										<td class="centertitle" style="width: 14%">
											<s:text name="db.prpDcompany.remark" />
										</td>
										<!-- 备注 -->
										<td class="centertitle" style="width: 4%"></td>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<td class="title" colspan=9 style="width: 96%">
											<s:text name="prompt.certainLoss.addRemoveCost" />
											<!--  (按"+"号键增加修理项目费用信息，按"-"号键删除信息) -->
										</td>
										<td class="title" align="right" style="width: 4%">
											<div align="center">
												<input type="button" class=smallbutton value="+" onclick="insertRowTableOfCertainLossRepair('RepairFee','RepairFee_Data',this)" name="buttonRepairFee" style="cursor: hand">
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="10">
											<table cellpadding="6" cellspacing="1" class="common">
												<tr>
													<td class='title' style="display: none" colspan="1" width="30%">
														<s:text name="certainLoss.costTotals" />
														<!-- 材料费合计 -->:
														<input class='readonly' readonly="true" style='width: 80px' name='SumMaterialFee1'>
													</td>
													<td class='title' colspan="4" width="40%">
														<s:text name="certainLoss.laborTotals" />
														<!-- 工时费合计 -->:
														<input class='readonly' readonly="true" style='width: 80px' name='SumDefLoss1' value="0">
													</td>
													<td class='title' colspan="4" width="40%">
														<s:text name="certainLoss.totalApproved" />
														<!-- 核定工时费合计 -->:
														<input class='readonly' readonly="true" style='width: 80px' name='SumVerifyLoss1'>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</tfoot>
								<tbody>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
				<%-- 换件项目费用清单 --%>
				<%
					//@include file="/DAA/quickCase/DAAQuickCaseCertainLossCarComponent.jsp"
				%>
				<table class="common" cellpadding="5" cellspacing="1">
					<tr>
						<td class="common">
							<table class="common" cellpadding="5" cellspacing="1" id="Component">
								<thead>
									<tr>
										<td class="subformtitle" colspan=17>
											<s:text name="certainLoss.ProjectCosts" />
											<!-- 零部件更换项目费用清单 -->
										</td>
									</tr>
									<tr>
										<!-- 险别代码隐藏不显示 -->
										<!--td style="display:none" class="centertitle" style="width:0%" rowspan="2">
												险别代码 </td-->
										<td class="centertitle" style="width: 8%" rowspan="2">
											<s:text name="db.prpDrate.kindName" />
											<!-- 险别名称  -->
										</td>
										<td class="centertitle" style="width: 8%" rowspan="2">
											<s:text name="certainLoss.partName" />
											<!-- 部件名称 -->
										</td>
										<td class="centertitle" style="width: 8%" rowspan="2">
											<s:text name="certainLoss.originalEncoding" />
											<!-- 原厂编码  -->
										</td>
										<!--td colspan="3" class="centertitle" style="width:5%">
												中心报价 </td-->
										<td colspan="3" class="centertitle" style="width: 21%">
											<s:text name="certainLoss.localQuotes" />
											<!-- 本地报价  -->
										</td>
										<td class="centertitle" style="width: 9%" rowspan="2">
											<s:text name="certainLoss.repairQuotes" />
											<!-- 修理厂报价  -->
										</td>
										<td class="centertitle" style="width: 9%" rowspan="2">
											<s:text name="certainLoss.lossPrice" />
											<!-- 定损单价 -->
										</td>
										<td class="centertitle" style="width: 6%" rowspan="2">
											<s:text name="certainLoss.priceType" />
											<!-- 价格类型  -->
										</td>
										<td class="centertitle" style="width: 5%" rowspan="2">
											<s:text name="certainLoss.numberReplacement" />
											<!-- 更换数量  -->
										</td>
										<td class="centertitle" style="width: 9%" rowspan="2">
											<s:text name="print.salvValueAmount" />
											<!-- 残值金额  -->
										</td>
										<td rowspan="2" class="centertitle" style="width: 8%">
											<s:text name="certainLoss.subtotal" />
											<!-- 小计  -->
										</td>
										<td class="centertitle" style="width: 5%" rowspan="2">
											<s:text name="db.prpDcompany.remark" />
											<!-- 备注  -->
										</td>
										<td class="centertitle" style="width: 4%" rowspan="2"></td>
									</tr>
									<tr>
										<!-- 中心报价隐藏不需要显示 -->
										<!--td class="centertitle" style="width:9%">
												专修价 </td>
											<td class="centertitle" style="width:5%">
												市场价 </td>
											<td class="centertitle" style="width:5%">
												副厂价 </td-->
										<td class="centertitle" style="width: 7%">
											<s:text name="certainLoss.price" />
											<!-- 专修价  -->
										</td>
										<td class="centertitle" style="width: 7%">
											<s:text name="certainLoss.marketPrice" />
											<!-- 市场价  -->
										</td>
										<td class="centertitle" style="width: 7%">
											<s:text name="certainLoss.factoryPrice" />
											<!-- 副厂价 -->
										</td>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<td colspan="14" align="center">
											<input type="button" name=buttonChangeRepairFactoryCode class=bigbutton value="<s:text name="button.resetChargedPrice.value"/>" onclick="changeRepairFactoryCode(this);" style="cursor: hand">
											&nbsp;&nbsp;
											<!--清除定损单价-->
											<input type="button" name=buttonGetFittings class=bigbutton value="<s:text name="button.importPartsSystem.value"/>" onclick="getFittingsInfo('Compent',this);">
											<!-- 从配件系统导入 -->
											<input type="button" class=smallbutton style="display: none" value="+" onclick="insertRowTableOfCertainLossComponent('Component','Component_Data',this)" name="buttoncomponent"
												style="cursor: hand">
										</td>
									</tr>
									<tr>
										<td colspan="14">
											<table border="0" align="center" cellpadding="4" cellspacing="1" class="title" width="100%">
												<!--tr>
														<td class='left'>
															运费:</td>
														<td class='right'>
															<input name="prpLcarLossSumTransFee" class="input" style='width:80px'
																value=""
																onBlur="sumComponentFee();"></td>
														<td class='left'>
															税金:</td>
														<td class='right'>
															<input name="prpLcarLossSumTax" class="readonly" readonly style='width:80px' 
																value=""
																onBlur="sumComponentFee();"></td>
														<td class='left'>
															管理费:</td>
														<td class='right'>
															<input name="prpLcarLossSumManager" class="input" style='width:80px'
																value=""
																onBlur="sumComponentFee();">%</td>
													</tr-->
												<tr>
													<td class='left'>
														<s:text name="certainLoss.TotalResiduals" />
														<!-- 残值合计 -->:
													</td>
													<td class='right'>
														<input type="hidden" name="selectCarFittings">
														<input name="prpLcarLossSumRest" class="input" style='width: 80px' type="hidden" value="">
													</td>
													<td class='left'></td>
													<td class='right'></td>
													<td class='left'>
														<s:text name="certainLoss.changeTotals" />
														<!-- 换件费合计 -->:
													</td>
													<td class='right'>
														<input name="SumDefLoss2" class="readonly" readonly style='width: 80px'>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</tfoot>
								<tbody>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
				<%-- 车上财产损失信息 --%>
				<%
					//@include file="/DAA/quickCase/DAAQuickCaseCertainLossCarProp.jsp"
				%>
				<table class="common" cellpadding="5" cellspacing="1">
					<tr>
						<td class="common" colspan="4">
							<table class="common" id="CertainLossCarProp" cellpadding="5" cellspacing="1">
								<thead>
									<tr>
										<td class="subformtitle" colspan=9>
											<s:text name="quickCase.propertyDamageList" />
										</td>
										<!-- 财产损失清单 -->
									</tr>
									<tr>
										<td class="centertitle" style="width: 12%">
											<s:text name="db.prpDkind.kindCode" />
										</td>
										<!-- 险别代码 -->
										<td class="centertitle" style="width: 12%">
											<s:text name="db.prpDrate.kindName" />
										</td>
										<!--险别名称  -->
										<td class="centertitle" style="width: 12%">
											<s:text name="certainLoss.lostProperty" />
										</td>
										<!-- 损失财产名称 -->
										<td class="centertitle" style="width: 12%">
											<s:text name="db.prpLprop.feeTypeName" />
										</td>
										<!-- 费用名称 -->
										<td class="centertitle" style="width: 12%">
											<s:text name="db.prpLprop.sumLoss" />
										</td>
										<!-- 受损金额 -->
										<td class="centertitle" style="width: 12%">
											<s:text name="db.prpLprop.sumReject" />
										</td>
										<!-- 剔除金额 -->
										<td class="centertitle" style="width: 12%">
											<s:text name="certainLoss.lossAmount" />
										</td>
										<!-- 定损金额 -->
										<td class="centertitle" style="width: 12%">
											<s:text name="db.prpDcompany.remark" />
										</td>
										<!-- 备注 -->
										<td class="centertitle" style="width: 4%"></td>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<td class="title" colspan=8 style="width: 96%">
											<s:text name="prompt.quickCase.addPropertyDamage" />
										</td>
										<!-- (按"+"号键增加财产损失信息，按"-"号键删除信息) -->
										<td class="title" align="right" style="width: 4%">
											<div align="center">
												<input type="button" class=smallbutton value="+" onclick="insertRowTableOfCertainLossProp('CertainLossCarProp','CertainLossCarProp_Data',this)" name="button" style="cursor: hand">
											</div>
										</td>
									</tr>
								</tfoot>
								<tbody>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<td class="title" align="right" style="width: 4%">
				<input type="button" class=smallbutton value="-" onclick="deleteRow(this,'CertainLossCar')" name="buttonCertainLossCarDelete">
			</td>
		</tr>
	</tbody>
</table>
<table id="CertainLossCar" name="CertainLossCar" class="common" align="center" cellspacing="1" cellpadding="0">
	<thead>
		<tr class=listtitle>
			<td style="width: 4%">
				<s:text name="db.prpDrate.serialNo " />
			</td>
			<!-- 序号  -->
			<td style="width: 96%" colspan="2">
				<s:text name="db.utiFile.text" />
			</td>
			<!-- 内容 -->
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td class="title" colspan=3 align="right" style="width: 4%">
				<input type="button" class=smallbutton value="+" onclick="insertRowTableOfCertainLossCar('CertainLossCar','CertainLossCar_Data',this);" name="buttonCertainLossCarInsert">
			</td>
		</tr>
	</tfoot>
	<tbody>
		<logic:notEmpty name="certainLossDto" property="prpLcarLossDtoList">
			<%
				String hiddenButdisabled = "";
					int index = 0;
			%>
			<logic:iterate id="prpLcarLossDto" name="certainLossDto" property="prpLcarLossDtoList">
				<%
					ArrayList delete = (ArrayList) request.getAttribute("delete");
							if (delete != null) {
								hiddenButdisabled = (String) delete.get(index);
							}
				%>
				<tr>
					<td class="common" style="width: 4%">
						<input type="hidden" name="certainLossAdd" value="N">
						<input type="text" class="readonly" readonly name="prpLverifyLossLossItemCode" value="<bean:write name='prpLcarLossDto' property='lossItemCode' filter='true' />">
						<input type="hidden" class="common" name="RelateSerialNo" value="1">
					</td>
					<td class="common" colspan="1" style="widht: 90%">
						<%-- 车辆损失基本信息 --%>
						<%@include file="/DAA/quickCase/DAAQuickCaseCertainLossCarHead.jsp"%>
						<%-- 修理项目费用清单 --%>
						<%@include file="/DAA/quickCase/DAAQuickCaseCertainLossCarRepair.jsp"%>
						<%-- 换件项目费用清单 --%>
						<%@include file="/DAA/quickCase/DAAQuickCaseCertainLossCarComponent.jsp"%>
						<%-- 车上财产损失信息 --%>
						<%@include file="/DAA/quickCase/DAAQuickCaseCertainLossCarProp.jsp"%>
					</td>
					<td class="title" align="right" style="width: 4%">
						<input type="hidden" name="buttonFlag" value='<%=hiddenButdisabled%>'>
						<input type="button" class=smallbutton value="-" <%=butdisabled%> onclick="beforeDelectRow(this)" name="buttonCertainLossCarDelete">
					</td>
				</tr>
				<%
					index++;
				%>
			</logic:iterate>
		</logic:notEmpty>
	</tbody>
</table>
<script language="javascript">
//嵌套标签页实现脚本

function selectTag(showContent, selfObj) {
	// 操作标签
	var tag = document.getElementById("tags").getElementsByTagName("li");
	var taglength = tag.length;
	for (i = 0; i < taglength; i++) {
		tag[i].className = "";
	}
	selfObj.parentNode.className = "selectTag";
	// 操作内容
	for (i = 0; j = document.getElementById("tagContent" + i); i++) {
		j.style.display = "none";
	}
	document.getElementById(showContent).style.display = "block";
}

//嵌套标签页样式设置

function setStyle() {
	var tag = document.getElementById("tagContent").getElementsByTagName(
		"div");
	var taglength = tag.length;
	for (i = 0; i < taglength; i++) {
		tag[i].style.width = document.body.offsetWidth;
		tag[i].style.Height = document.body.offsetHeight;
	}
}
</script>