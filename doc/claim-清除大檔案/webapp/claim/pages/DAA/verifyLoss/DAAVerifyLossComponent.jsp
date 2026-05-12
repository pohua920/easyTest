<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<span id="spanComponent" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="Component">
					<thead>
						<tr>
							<c:choose>
								<c:when test="${param.nodeType == 'backc'}">
									<td class="centertitle" colspan="12">
										<s:text name="certainLoss.ProjectCosts" />
									</td>
									<%--零部件更换项目费用清单 --%>
								</c:when>
								<c:otherwise>
									<td class="centertitle" colspan="12">
										<s:text name="certainLoss.ProjectCosts" />
									</td>
									<%--零部件更换项目费用清单 --%>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td style="display: none" class="centertitle" rowspan="2">
								<s:text name="regist.prpLregist.kindCode" />
							</td>
							<%--险别代码 --%>
							<td class="centertitle" style="width: 15%" rowspan="2">
								<s:text name="db.prpLCitemKind.kindName" />
							</td>
							<%--险别名称 --%>
							<td class="centertitle" style="width: 23%" rowspan="2">
								<s:text name="certainLoss.partName" />
							</td>
							<%--零配件名稱 --%>
							<td class="centertitle" style="width: 10%" rowspan="2">
								<s:text name="certainLoss.originalEncoding" />
							</td>
							<%--原廠編碼 --%>
							<td class="centertitle" style="display: none" rowspan="2">
								<s:text name="certainLoss.repairQuotes" />
							</td>
							<%--修理厂报价 --%>
							<td class="centertitle" style="display: none" rowspan="2">
								<s:text name="certainLoss.priceType" />
							</td>
							<%--价格类型 --%>
							<td colspan="3" class="centertitle" style="display: none">
								<s:text name="verifyLoss.centerQuote" />
							</td>
							<%--中心报价 --%>
							<td colspan="3" class="centertitle" style="width: 15%">
								<s:text name="certainLoss.localQuotes" />
							</td>
							<%--本地报价 --%>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="verifyLoss.priceFee" />
							</td>
							<%--定损价格 --%>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="db.prpLCitemKind.quantity" />
							</td>
							<%--数量 --%>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="certainLoss.whetherRecycling" />
								<!--是否回收-->
							</td>
							<td class="centertitle" style="width: 5%" rowspan="2">
								<s:text name="print.salvValueAmount" />
							</td>
							<%--残值金额 --%>
							<td rowspan="2" class="centertitle" style="width: 5%">
								<s:text name="certainLoss.subtotal" />
							</td>
							<%--小计 --%>
							<td class="centertitle" style="width: 12%" rowspan="2">
								<s:text name="db.prpLcomponent.remark" />
							</td>
							<%--备注 --%>
							<td class="centertitle" style="display: none" colspan=3 rowspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td class="centertitle" style="width: 5%; display: none">
								<s:text name="certainLoss.price" />
							</td>
							<%--中古價--%>
							<td class="centertitle" style="width: 5%; display: none">
								<s:text name="certainLoss.marketPrice" />
							</td>
							<%--原廠价 --%>
							<td class="centertitle" style="width: 5%; display: none">
								<s:text name="certainLoss.factoryPrice" />
							</td>
							<%--副廠價 --%>
							<td class="centertitle" style="width: 6%">
								<s:text name="certainLoss.price" />
							</td>
							<%--中古價 --%>
							<td class="centertitle" style="width: 6%">
								<s:text name="certainLoss.marketPrice" />
							</td>
							<%--原廠价 --%>
							<td class="centertitle" style="width: 6%">
								<s:text name="certainLoss.factoryPrice" />
							</td>
							<%--副廠價 --%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="12">
								<table border="0" align="center" cellpadding="4" cellspacing="1" class="title" width="100%">
									<tr style="display: none">
										<td class='title' style="width: 2%"></td>
										<td class='title' style="width: 8%" align="right">
											<s:text name="certainLoss.freight" />:
										</td>
										<%--运费 --%>
										<td class='input' style="width: 8%">
											<input name="prpLcarLossSumTransFee" class='input' style='width: 80px' value="<fmt:formatNumber value='${pageScope.prpLcarLoss.sumTransFee}' pattern='#'/>" onBlur="sumComponentFee();">
										</td>
										<td class='title' style="width: 5%" align="right">
											<!-- 税金:  -->
										</td>
										<td class='input' style="width: 12%">
											<input name="prpLcarLossSumTax" class="readonly" readonly style='width: 80px; display: none' value="${pageScope.prpLcarLoss.sumTax}" onBlur="sumComponentFee();">
										</td>
										<td class='title' style="width: 10%" align="right">
											<s:text name="certainLoss.managementFee" />:
										</td>
										<%--管理费 --%>
										<td class='input' style="width: 12%">
											<input name="prpLcarLossSumManager" class='input' style='width: 80px' value="${pageScope.prpLcarLoss.sumManager}" onBlur="sumComponentFee();">
											%
										</td>
										<input name="SumVerifyLoss2" type="hidden" class="readonly" readonly style='width: 80px'>
									</tr>
									<tr>
										<td class='title' style="width: 2%"></td>
										<td class='title' style="width: 12%" align="right">
											<s:text name="certainLoss.TotalResiduals" />:
										</td>
										<%--残值合计 --%>
										<td class='input' style="width: 12%">
											<input type=hidden name="prpLcarLossSumManageFeeRate">
											<input name="prpLcarLossSumRest" style='width: 80px' class="readonly" readonly value="<fmt:formatNumber value='${pageScope.prpLcarLoss.sumRest}' pattern='#'/>" onBlur="sumComponentFee();">
										</td>
										<td class='title' style="width: 12%" align="right"></td>
										<td class='input' style="width: 12%"></td>
										<td class='title' style="width: 17%" align="right">
											<s:text name="verifyLoss.approvalChangeFee" />:
										</td>
										<%--核定换件费合计 --%>
										<td class='input' style="width: 17%" colspan=7>
											<input name="SumDefLoss2" class="readonly" readonly style='width: 80px' value="${pageScope.prpLcarLoss.sumVerifyLoss}">
										</td>
										<input type=hidden class='readonly' class="input" readonly="true" style='width: 80px' name='SumManHourFee2'>
										<input type=hidden class='readonly' readonly="true" class="input" style='width: 80px' name='SumMaterialFee2'>
									</tr>
								</table>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:set var="color" value=";#000000" />
						<c:set var="materialFee" value="0" />
						<c:set var="sys4SPrice" value="0" />
						<c:set var="sysMarketPrice" value="0" />
						<c:set var="sysMatchPrice" value="0" />
						<c:set var="native4SPrice" value="0" />
						<c:set var="nativeMarketPrice" value="0" />
						<c:set var="nativeMatchPrice" value="0" />
						<c:set var="componentNo" value="0" />
						<c:forEach items="${requestScope.prpLcomponent.componentList}" var="prpLcomponent1">
							<c:if test="${pageScope.prpLcomponent1.id.lossItemCode == pageScope.prpLcarLoss.id.lossItemCode}">
								<c:set var="componentNo" value="${pageScope.prpLcomponent1.id.lossItemCode}" />
								<c:set var="materialFee" value="${pageScope.prpLcomponent1.materialFee}" />
								<c:set var="sys4SPrice" value="${pageScope.prpLcomponent1.sys4SPrice}" />
								<c:set var="sysMarketPrice" value="${pageScope.prpLcomponent1.sysMarketPrice}" />
								<c:set var="sysMatchPrice" value="${pageScope.prpLcomponent1.sysMatchPrice}" />
								<c:set var="native4SPrice" value="${pageScope.prpLcomponent1.native4SPrice}" />
								<c:set var="nativeMarketPrice" value="${pageScope.prpLcomponent1.nativeMarketPrice}" />
								<c:set var="nativeMatchPrice" value="${pageScope.prpLcomponent1.nativeMatchPrice}" />
								<c:choose>
									<c:when test="${pageScope.prpLcomponent1.priceType =='S'}">
										<c:choose>
											<c:when test="${pageScope.materialFee > pageScope.sys4SPrice || pageScope.materialFee > pageScope.native4SPrice}">
												<c:set var="color" value=";background:#FF0000" />
											</c:when>
											<c:otherwise>
												<c:set var="color" value="" />
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:when test="${pageScope.prpLcomponent1.priceType =='M' || pageScope.prpLcomponent1.priceType =='O'}">
										<c:choose>
											<c:when
												test="${pageScope.materialFee > pageScope.sysMarketPrice || pageScope.materialFee > pageScope.sysMatchPrice || pageScope.materialFee > pageScope.nativeMarketPrice || pageScope.materialFee > pageScope.nativeMatchPrice}">
												<c:set var="color" value=";background:#FF0000" />
											</c:when>
											<c:otherwise>
												<c:set var="color" value="" />
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
								<tr>
									<td class="input" style="display: none">
										<input type="hidden" name="carLossComponentLossItemCode" value="${pageScope.componentNo-1}">
										<input type="text" name="prpLcomponentKindCode" class="codecode" style='width: 100%;' value="${pageScope.prpLcomponent1.kindCode}"
											ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','N',fm.policyno.value);" onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','N',fm.policyno.value);">
									</td>
									<td class="input" style="width: 15%;">
										<input type="text" name="prpLcomponentKindName" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLcomponent1.kindName}"
											1ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value);" 1onkeyup="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value);">
									</td>
									<td class="input" style="width: 23%;">
										<input name="prpLcomponentCompCode" type='hidden' value="${pageScope.prpLcomponent1.compCode}">
										<input name="prpLcomponentCompName" maxlength="80" class=readonly readonly style="width:100%;
                     						<c:if test="${fn:startsWith(pageScope.prpLcomponent1.flag,'1')}"><c:out value=";color:'#FF0000'"/></c:if>"
											value="${pageScope.prpLcomponent1.compName}">
									</td>
									<td class="input" style="width: 10%;">
										<input type="text" name="prpLcomponentOriginalId" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLcomponent1.originalId}">
									</td>
									<td class="input" style="display: none">
										<input name="prpLcomponentRepairFactoryFee" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.repairFactoryFee}' pattern='#'/>">
									</td>
									<td class="input" style="display: none">
										<select name="prpLcomponentPriceType" class="three" style="width: 100%;">
											<option value="S" <c:if test="${pageScope.prpLcomponent1.priceType =='S'}"><c:out value="selected"/></c:if>>
												<s:text name="certainLoss.price" />
											</option>
											<%--专修价 --%>
											<option value="M" <c:if test="${pageScope.prpLcomponent1.priceType =='M'}"><c:out value="selected"/></c:if>>
												<s:text name="certainLoss.marketPrice" />
											</option>
											<%--市场价 --%>
											<option value="O" <c:if test="${pageScope.prpLcomponent1.priceType =='O'}"><c:out value="selected"/></c:if>>
												<s:text name="certainLoss.factoryPrice" />
											</option>
											<%--副厂价 --%>
										</select>
									</td>
									<td class="input" style="display: none">
										<input name="prpLcomponentSys4SPrice" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.sys4SPrice}' pattern='#'/>">
									</td>
									<td class="input" style="display: none">
										<input name="prpLcomponentSysMarketPrice" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.sysMarketPrice}' pattern='#'/>">
									</td>
									<td class="input" style="display: none">
										<input name="prpLcomponentSysMatchPrice" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.sysMatchPrice}' pattern='#'/>">
									</td>
									<td class="input" style="width: 4%;">
										<input name="prpLcomponentNative4SPrice" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.native4SPrice}' pattern='#'/>">
									</td>
									<td class="input" style="width: 3%;">
										<input name="prpLcomponentNativeMarketPrice" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.nativeMarketPrice}' pattern='#'/>">
									</td>
									<td class="input" style="width: 3%;">
										<input name="prpLcomponentNativeMatchPrice" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.nativeMatchPrice}' pattern='#'/>">
									</td>
									<td class="input" style="width: 5%;">
										<input name="prpLcomponentMaterialFee" class=common readonly style='100%;${pageScope.color}' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.materialFee}' pattern='#'/>">
									</td>
									<td class="input" style="width: 5%;">
										<input name="prpLcomponentQuantity" class=common readonly style='width: 100%;' value="${pageScope.prpLcomponent1.quantity}">
									</td>
									<td class="input" align="center" style="width: 5%;">
										<c:set var="tempSelectedValue" value="${pageScope.prpLcomponent1.ifRemain}" />
										<s:select value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.ifRemainList" disabled="true" cssStyle="width:100%;" />
										<input name="prpLcomponentIfRemain" type=hidden value="${pageScope.prpLcomponent1.ifRemain}">
									</td>
									<td class="input" style="width: 5%;">
										<input name="prpLcomponentRestFee" class=common readonly style='width: 100%;' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.restFee}' pattern='#'/>">
									</td>
									<td class="input" style="width: 5%;">
										<input name="prpLcomponentSumDefLoss" class=readonly readonly style='width:100%;${pageScope.color}' value="<fmt:formatNumber value='${pageScope.prpLcomponent1.sumDefLoss}' pattern='#'/>">
										<input name="prpLcomponentVerpCompPrice" type="hidden" class="common" style="width: 100%;" value="${pageScope.prpLcomponent1.verpCompPrice}">
										<input name="prpLcomponentVerpCompPriceLast" type="hidden" value="${pageScope.prpLcomponent1.verpCompPrice}">
									</td>
									<td class="input" style="width: 12%;">
										<input name="prpLcomponentRemark" class=readonly readonly style='width: 100%;' value="${pageScope.prpLcomponent1.remark}">
										<input name="prpLcomponentFlag" type=hidden value="${pageScope.prpLcomponent1.flag}">
										<input name="prpLcomponentIndId" type=hidden value="${pageScope.prpLcomponent1.indId}">
									</td>
									<td class="input" style="display: none">
										<input type="hidden" name="prpLcomponentSerialNo" value="${pageScope.prpLcomponent1.id.serialNo}">
										<input type="hidden" name="prpLcomponentItemKindNo" value="${pageScope.prpLcomponent1.itemKindNo}">
										<input type="hidden" name="prpLcomponentLossItemCode" value="${pageScope.prpLcomponent1.id.lossItemCode}">
										<input type="hidden" name="prpLcomponentLicenseNo" value="${pageScope.prpLcomponent1.licenseNo}">
										<input type="hidden" name="prpLcomponentLicenseColorCode" value="${pageScope.prpLcomponent1.licenseColorCode}">
										<input type="hidden" name="prpLcomponentCarKindCode" value="${pageScope.prpLcomponent1.carKindCode}">
										<input type="hidden" name="prpLcomponentMakeYear" value="${pageScope.prpLcomponent1.makeYear}">
										<input type="hidden" name="prpLcomponentGearboxType" value="${pageScope.prpLcomponent1.gearboxType}">
										<input type="hidden" name="prpLcomponentQuoteCompanyGrade" value="${pageScope.prpLcomponent1.quoteCompanyGrade}">
										<input type="hidden" name="prpLcomponentManageFeeRate" value="${pageScope.prpLcomponent1.manageFeeRate}">
										<input type="hidden" name="prpLcomponentRepairFactoryCode" value="${pageScope.prpLcomponent1.repairFactoryCode}">
										<input type="hidden" name="prpLcomponentRepairFactoryName" value="${pageScope.prpLcomponent1.repairFactoryName}">
										<input type="hidden" name="prpLcomponentHandlerCode" value="${pageScope.prpLcomponent1.handlerCode}">
										<input type="hidden" name="prpLcomponentRepairStartDate" value="${pageScope.prpLcomponent1.repairStartDate}">
										<input type="hidden" name="prpLcomponentRepairEndDate" value="${pageScope.prpLcomponent1.repairEndDate}">
										<input type="hidden" name="prpLcomponentSanctioner" value="${pageScope.prpLcomponent1.sanctioner}">
										<input type="hidden" name="prpLcomponentApproverCode" value="${pageScope.prpLcomponent1.approverCode}">
										<input type="hidden" name="prpLcomponentOperatorCode" value="${pageScope.prpLcomponent1.operatorCode}">
										<input type="hidden" name="prpLcomponentQueryPrice" value="${pageScope.prpLcomponent1.queryPrice}">
										<input type="hidden" name="prpLcomponentQuotedPrice" value="${pageScope.prpLcomponent1.quotedPrice}">
										<input type="hidden" name="prpLcomponentPartCode" value="${pageScope.prpLcomponent1.partCode}">
										<input type="hidden" name="prpLcomponentPartName" value="${pageScope.prpLcomponent1.partName}">
										<input type="hidden" name="prpLcomponentManHourFee" value="${pageScope.prpLcomponent1.manHourFee}">
										<input type="hidden" name="prpLcomponentBackCheckRemark" value="${pageScope.prpLcomponent1.backCheckRemark}">
										<input type="hidden" name="prpLcomponentLossRate" value="${pageScope.prpLcomponent1.lossRate}">
										<input type="hidden" name="prpLcomponentCurrency" value="${pageScope.prpLcomponent1.currency}">
										<input type="hidden" name="prpLcomponentVeriManHourFee" value="${pageScope.prpLcomponent1.veriManHourFee}">
										<input type="hidden" name="prpLcomponentVeriLossRate" value="${pageScope.prpLcomponent1.veriLossRate}">
										<input type="hidden" name="prpLcomponentCompensateBackFlag" value="${pageScope.prpLcomponent1.compensateBackFlag}">
									</td>
								</tr>
								<c:choose>
									<c:when test="${param.nodeType == 'backc'}">
										<tr>
											<td class="input" colspan="3" style="width: 48%;">&nbsp;</td>
											<td class="input" style="width: 10%;">
												<input name="prpLcomponentVeriMaterFee" class="input" style='width: 100%;' value="<fmt:formatNumber value="${pageScope.prpLcomponent1.veriMaterFee}" pattern="#"/>">
											</td>
											<td class="input" style="width: 5%;">
												<input name="prpLcomponentVeriQuantity" class="input" style='width: 100%;' value="${pageScope.prpLcomponent1.veriQuantity}" onBlur="getVeriSumDefLoss(this);">
											</td>
											<td class="input" style="width: 5%;">
												<input name="prpLcomponentVeriRestFee" class="input" style='width: 100%;' value="<fmt:formatNumber value="${pageScope.prpLcomponent1.veriRestFee}" pattern="#"/>"
													onBlur="sumComponentFee();">
											</td>
											<td class="input" colspan="6">&nbsp;</td>
											<td class="input" style="width: 5%;">
												<input name="prpLcomponentVeriSumDefLoss" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLcomponent1.sumVeriLoss}" onBlur="getVeriSumDefLoss(this);">
												<input type="hidden" name="prpLcomponentVeriVerpCompPrice" class="readonly" readonly style='width: 50px' value="${pageScope.prpLcomponent1.sumVeriLoss}">
											</td>
											<td class="input" style="width: 12%;">
												<input name="prpLcomponentVeriRemark" class="input" style='width: 100%;' value="${pageScope.prpLcomponent1.veriRemark}">
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td class="input" colspan="6" style="width: 58%;">
												<s:text name="certainLoss.nuclearDamage" />:
											</td>
											<%--核损意见 --%>
											<td class="input" style="width: 5%;">
												<input name="prpLcomponentVeriMaterFee" class="input" style='width: 100%;' value="<fmt:formatNumber value="${pageScope.prpLcomponent1.veriMaterFee}" pattern="#"/>"
													onBlur="getVeriSumDefLoss(this);">
											</td>
											<td class="input" colspan="2" style="width: 10%;">
												<input name="prpLcomponentVeriQuantity" class="input" style='width: 45%;' value="${pageScope.prpLcomponent1.veriQuantity}" onBlur="getVeriSumDefLoss(this);">
											</td>
											<td class="input" style="width: 5%;">
												<input name="prpLcomponentVeriRestFee" class="input" style='width: 100%;' value="<fmt:formatNumber value="${pageScope.prpLcomponent1.veriRestFee}" pattern="#"/>"
													onBlur="sumComponentFee();">
											</td>
											<td class="input" style="width: 5%;">
												<input name="prpLcomponentVeriSumDefLoss" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value="${pageScope.prpLcomponent1.sumVeriLoss}" pattern="#"/>"
													onBlur="getVeriSumDefLoss(this);">
												<input name="prpLcomponentVeriVerpCompPrice" type="hidden" class="readonly" readonly value="${pageScope.prpLcomponent1.sumVeriLoss}">
											</td>
											<td class="input" style="width: 12%;">
												<input name="prpLcomponentVeriRemark" class="input" style='width: 100%;' value="${pageScope.prpLcomponent1.veriRemark}">
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
	<tr>
</table>
