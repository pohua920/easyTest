<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2004-06-01
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<span id="spanRepairFee" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="RepairFee">
					<thead>
						<tr>
							<c:choose>
								<c:when test="${param.nodeType =='backc'}">
									<td class="centertitle" colspan=11>
										<s:text name="certainLoss.costList" />
									</td>
									<%--修理项目费用清单 --%>
								</c:when>
								<c:otherwise>
									<td class="centertitle" colspan=10>
										<s:text name="certainLoss.costList" />
									</td>
									<%--修理项目费用清单 --%>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td class="centertitle" style="width: 20%">
								<s:text name="regist.prpLregist.kindName" />
							</td>
							<%--险别名称 --%>
							<td class="centertitle" style="display: none">
								<s:text name="certainLoss.repairParts" />
							</td>
							<%--修理部位 --%>
							<td class="centertitle" style="width: 30%">
								<s:text name="certainLoss.repairItemName" />
							</td>
							<%--修理项名称 --%>
							<td class="centertitle" style="width: 15%">
								<s:text name="certainLoss.types" />
							</td>
							<%--工种类型 --%>
							<td class="centertitle" style="display: none">
								<s:text name="db.prpLrepairFee.manHour" />
							</td>
							<%--工时 --%>
							<td class="centertitle" style="display: none">
								<s:text name="db.prpLrepairFee.manHourFee" />
							</td>
							<%--工时费 --%>
							<td class="centertitle" style="display: none">
								<s:text name="db.prpLrepairFee.materialFee" />
							</td>
							<%--材料费 --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLrepairFee.manHourFee" />
							</td>
							<%--工时费 --%>
							<td class="centertitle" style="width: 25%">
								<s:text name="db.prpLcomponent.remark" />
							</td>
							<%--备注 --%>
							<c:if test="${param.nodeType == 'backc'}">
								<td class="centertitle" style="width: 10%">
									<s:text name="verifyLoss.multipleOpinion" />
								</td>
								<%--复勘意见 --%>
							</c:if>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="10">
								<table class=common cellpadding="5" cellspacing="1">
									<tr style="display: none">
										<td class='title' style="display: none" width="30%">
											<s:text name="certainLoss.laborTotals" />:
											<%--工时费合计 --%>
											<input class='readonly' readonly="true" style='width: 80px' name='SumManHourFee1'>
										</td>
										<td class='title' style="display: none" width="30%">
											<s:text name="certainLoss.costTotals" />:
											<%--材料费合计 --%>
											<input class='readonly' readonly="true" style='width: 80px' name='SumMaterialFee1'>
										</td>
										<td class='input' style="width: 12%" colspan="2">
											<s:text name="certainLoss.floatingRatio" />:
											<%--浮动比例 --%>
											<input name="prpLcarLossSumFloatRate" class="input" style='width: 50px' value="${pageScope.prpLcarLoss.sumFloatRate}" onBlur="sumRepairFee();">
											%
										</td>
									</tr>
									<tr>
										<td class='title' width="40%">
											<s:text name="certainLoss.totalRepair" />:
											<%--修理合计 --%>
											<input class='readonly' readonly="true" style='width: 80px' name='SumDefLoss1' value="">
										</td>
										<td class='title' width="40%">
											<s:text name="verifyLoss.shallCombined" />:
											<%--核定修理合计 --%>
											<input name="SumVerifyLoss1" class="readonly" readonly style='width: 80px'>
									</tr>
								</table>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:set var="repairFeeNo" value="0" />
						<c:forEach items="${requestScope.prpLrepairFee.repairFeeList}" var="prpLrepairFee1">
							<c:if test="${pageScope.prpLrepairFee1.id.lossItemCode == pageScope.prpLcarLoss.id.lossItemCode}">
								<c:set var="repairFeeNo" value="${pageScope.prpLrepairFee1.id.lossItemCode}" />
								<tr>
									<td class="input" style="display: none">
										<input type="hidden" name="carLossRepairFeeLossItemCode" style="width: 100%;" value="${pageScope.repairFeeNo-1}">
										<input type="text" name="prpLrepairFeeKindCode" class="codecode" style='width: 100%;' value="${pageScope.prpLrepairFee1.kindCode}"
											ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1','Y','N',fm.policyno.value);" onkeyup="code_CodeSelect(this,'PolicyKindCode','0,1','Y','N',fm.policyno.value);">
									</td>
									<td class="input" align="left" style="width: 20%;">
										<input type="text" name="prpLrepairFeeKindName" class="readonly" readonly style='width: 100%' value="${pageScope.prpLrepairFee1.kindName}"
											ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value);" onkeyup="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','N',fm.policyno.value);">
									</td>
									<td class="input" style="display: none" align="center">
										<input type="text" name="prpLrepairFeePartName" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLrepairFee1.partName}">
										<input type="hidden" name="prpLrepairFeePartCode" value="${pageScope.prpLrepairFee1.partCode}">
									</td>
									<td class="input" style="display: none" align="center">
										<input type="text" name="prpLrepairFeeCompCode" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLrepairFee1.compCode}">
									</td>
									<td class="input" align="left" style="width: 30%;">
										<input type="text" name="prpLrepairFeeCompName" class="readonly" readonly
											style="width:100%;<c:if test="${fn:startsWith(pageScope.prpLrepairFee1.flag,'1')}"><c:out value=";color:'#FF0000'"/></c:if>"
											value="${pageScope.prpLrepairFee1.compName}">
									</td>
									<td class="input" align="center" style="width: 15%;">
										<input type="hidden" name="prpLrepairFeeRepairType" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLrepairFee1.repairType}">
										<input type="text" name="prpLrepairFeeRepairTypeName" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLrepairFee1.repairTypeName}">
									</td>
									<td class="input" style="display: none">
										<input name="prpLrepairFeeManHour" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLrepairFee1.manHour}">
									</td>
									<td class="input" style="display: none">
										<input name="prpLrepairFeeManHourUnitPrice" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLrepairFee1.manHourUnitPrice}">
									</td>
									<td class="input" style="display: none">
										<input name="prpLrepairFeeMaterialFee" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLrepairFee1.materialFee}">
									</td>
									<td class="input" style="width: 10%;">
										<input name="prpLrepairFeeSumDefLoss" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value="${pageScope.prpLrepairFee1.sumDefLoss}" pattern="#"/>">
									</td>
									<td class="input" style="width: 25%;">
										<input name="prpLrepairFeeRemark" class="readonly" readonly style='width: 100%;' value="${pageScope.prpLrepairFee1.veriRemark}">
										<input name="prpLrepairFeeIndId" type=hidden value="${pageScope.prpLrepairFee1.indId}">
									</td>
									<c:choose>
										<c:when test="${param.nodeType =='backc'}">
											<td class="input">
												<input name="prpLrepairFeeBackCheckRemark" class="input" style='width: 100px' value="${pageScope.prpLrepairFee1.backCheckRemark}">
											</td>
										</c:when>
										<c:otherwise>
											<input type="hidden" name="prpLrepairFeeBackCheckRemark" value="${pageScope.prpLrepairFee1.backCheckRemark}">
										</c:otherwise>
									</c:choose>
									<input type="hidden" name="prpLrepairFeeSerialNo" value="${pageScope.prpLrepairFee1.id.serialNo}">
									<input type="hidden" name="prpLrepairFeeItemKindNo" value="${pageScope.prpLrepairFee1.itemKindNo}">
									<input type="hidden" name="prpLrepairFeeLossItemCode" value="${pageScope.prpLrepairFee1.id.lossItemCode}">
									<input type="hidden" name="prpLrepairFeeLicenseNo" value="${pageScope.prpLrepairFee1.licenseNo}">
									<input type="hidden" name="prpLrepairFeeLicenseColorCode" value="${pageScope.prpLrepairFee1.licenseColorCode}">
									<input type="hidden" name="prpLrepairFeeCarKindCode" value="${pageScope.prpLrepairFee1.carKindCode}">
									<input type="hidden" name="prpLrepairFeeSanctioner" value="${pageScope.prpLrepairFee1.sanctioner}">
									<input type="hidden" name="prpLrepairFeeApproverCode" value="${pageScope.prpLrepairFee1.approverCode}">
									<input type="hidden" name="prpLrepairFeeOperatorCode" value="${pageScope.prpLrepairFee1.operatorCode}">
									<input type="hidden" name="prpLrepairFeeManHourFee" value="${pageScope.prpLrepairFee1.manHourFee}">
									<input type="hidden" name="prpLrepairFeeLossRate" value="${pageScope.prpLrepairFee1.lossRate}">
									<input type="hidden" name="prpLrepairFeeCurrency" value="${pageScope.prpLrepairFee1.currency}">
									<input type="hidden" name="prpLrepairFeeVeriManHourFee" value="${pageScope.prpLrepairFee1.veriManHourFee}">
									<input type="hidden" name="prpLrepairFeeVeriMaterQuantity" value="${pageScope.prpLrepairFee1.veriMaterQuantity}">
									<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice" value="${pageScope.prpLrepairFee1.veriMaterUnitPrice}">
									<input type="hidden" name="prpLrepairFeeVeriLossRate" value="${pageScope.prpLrepairFee1.veriLossRate}">
									<input type="hidden" name="prpLrepairFeeFlag" value="${pageScope.prpLrepairFee1.flag}">
									<input type="hidden" name="prpLrepairFeeCompensateBackFlag" value="${pageScope.prpLrepairFee1.compensateBackFlag}">
								</tr>
								<tr>
									<td class="input" colspan="3" style="width: 65%;">&nbsp;</td>
									<td class="input" style="display: none">
										<input name="prpLrepairFeeVeriManHour" class="readonly" readonly value="${pageScope.prpLrepairFee1.veriManHour}">
									</td>
									<td class="input" style="display: none">
										<input name="prpLrepairFeeVeriManUnitPrice" value="${pageScope.prpLrepairFee1.veriManUnitPrice}" onBlur="return getSumDefLossVerify(this,1);">
									</td>
									<td class="input" style="display: none">
										<input name="prpLrepairFeeVeriMaterialFee" class="input" value="${pageScope.prpLrepairFee1.veriMaterialFee}" onBlur="return getSumDefLossVerify(this,1);">
									</td>
									<td class="input" style="width: 10%;">
										<input name="prpLrepairFeeVeriSumLoss" class="input" onblur="sumRepairFee();" style='width: 100%' value="<fmt:formatNumber value="${pageScope.prpLrepairFee1.veriSumLoss}" pattern="#"/>">
									</td>
									<td class="input" style="width: 25%;">
										<input name="prpLrepairFeeVeriRemark" class="input" style='width: 100%' value="${pageScope.prpLrepairFee1.veriRemark}">
									</td>
									<c:if test="${param.nodeType == 'backc'}">
										<td class="input">&nbsp;</td>
									</c:if>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>