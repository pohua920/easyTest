<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2013-03-06
* MODIFYLIST ：   Name       Date             Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<span id="spanRepairFee" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" id="RepairFee" cellpadding="5" cellspacing="1">
					<thead>
						<tr>
							<td class="subformtitle" colspan=20>
								<s:text name="certainLoss.costList" />
							</td>
							<!--修理项目费用清单-->
						</tr>
						<tr>
							<td style="display: none" class="centertitle">
								<s:text name="db.prpLCitemKind.kindCode" />
							</td>
							<!--险别代码-->
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpDrate.kindName" />
							</td>
							<!--险别名称-->
							<td class="centertitle" style="display: none">
								<s:text name="certainLoss.repairParts" />
							</td>
							<!--修理部位-->
							<td class="centertitle" style="width: 30%">
								<s:text name="certainLoss.repairItemName" />
							</td>
							<!--修理项名称-->
							<td class="centertitle" style="width: 15%">
								<s:text name="certainLoss.types" />
							</td>
							<!--工种类型-->
							<td class="centertitle" style="display: none">
								<s:text name="db.prpLrepairFee.manHour" />
							</td>
							<!--工时-->
							<td class="centertitle" style="display: none">
								<s:text name="db.prpLrepairFee.manHourUnitPrice" />
							</td>
							<!--工时单价-->
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLrepairFee.manHourFee" />
							</td>
							<!--工时费-->
							<td class="centertitle" style="display: none">
								<s:text name="certainLoss.startCost" />
							</td>
							<!--初始工时费-->
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpLrepairFee.remark" />
							</td>
							<!--备注-->
							<td class="centertitle" style="width: 5%;">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan=15>
								<table class="common" cellspacing="1" cellpadding="0" width="1150px">
									<tbody>
										<tr>
											<td class="title" colspan=11 style="width: 96%" align="left">
												<s:text name="prompt.certainLoss.addRemoveCost" />
												<!--(按"+"号键增加修理项目费用信息，按"-"号键删除信息)-->
												<c:set var="lossItemCode" value="${pageScope.prpLcarLoss.id.lossItemCode}" />
												<c:if test="${not empty param.flag}">
													<c:if test="${requestScope.prpLverifyLoss.verpOpinion =='03'}">
														<input type="button" class=bigbutton value="<s:text name='button.agreedMoney.value'/>" onclick="getVerifyRepairFee();" name="buttonAgreeVerifyRepairFeeLoss" style="cursor: hand">
														<!--同意核价金额-->
													</c:if>
												</c:if>
											</td>
											<td class="title" align="right" style="width: 4%">
												<div align="center">
													<input type="button" class=smallbutton value="+" onclick="insertThreeRowTableRepairFee('RepairFee','RepairFee_Data',this)" name="buttonRepairFee" style="cursor: hand">
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="5">
								<table cellpadding="4" cellspacing="1" class="common" align="center">
									<tr style="display: none">
										<input type="hidden" name="flag" value="${param.flag}">
										<input type="hidden" name="SumManHourFee1">
										<td class='title' style="display: none" colspan="1" width="30%">
											<s:text name="certainLoss.costTotals" />:
											<input class='readonly' readonly="true" style='width: 80px' name='SumMaterialFee1'>
										</td>
										<!--材料费合计-->
										<!-- add by zhyi 20110905 fubon=2422 start -->
										<td class='input' style="width: 12%" align="center">
											<s:text name="certainLoss.floatingRatio" />:
											<input name="prpLcarLossSumFloatRate" class="input" style='width: 50px' value="${pageScope.prpLcarLoss.sumFloatRate }" onBlur="sumRepairFee();">
											%
										</td>
										<!--浮动比例-->
									</tr>
									<tr>
										<td class='title' colspan="" width="40%" align="center">
											<s:text name="certainLoss.laborTotals" />:
											<input class='readonly' readonly="true" style='width: 80px' name='SumDefLoss1'>
										</td>
										<!--工时费合计-->
										<td class='title' colspan="" width="40%" align="center">
											<s:text name="certainLoss.totalApproved" />:
											<input class='readonly' readonly="true" style='width: 80px' name='SumVerifyLoss1'>
										</td>
										<!--核定工时费合计-->
									</tr>
								</table>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<%--//如果是由理算退回的，那么这行记录就应该显示得是只读的--%>
						<c:set var="indexCertainLoss" value="0" />
						<c:set var="compensatebackReadOnly" value="" />
						<c:set var="compensatebackDiasable" value="" />
						<c:set var="compensatebackStyle" value="" />
						<c:set var="repairFeeNo" value="0" />
						<c:if test="${requestScope.prpLrepairFee.repairFeeList !=null}">
							<c:forEach items="${requestScope.prpLrepairFee.repairFeeList}" var="prpLrepairFee1">
								<c:if test="${pageScope.prpLrepairFee1.id.lossItemCode == pageScope.prpLcarLoss.id.lossItemCode}">
									<c:set var="repairFeeNo" value="${pageScope.prpLrepairFee1.id.lossItemCode}" />
									<c:set var="compensatebackReadOnly" value="" />
									<c:set var="compensatebackDiasable" value="" />
									<c:set var="compensatebackStyle" value="" />
									<c:if test="${'1'==pageScope.prpLrepairFee1.compensateBackFlag}">
										<c:set var="compensatebackStyle" value="" />
										<c:set var="compensatebackReadOnly" value="readOnly" />
										<c:set var="compensatebackDiasable" value="disabled" />
									</c:if>
									<c:choose>
										<c:when test="${'3' != param.status}">
											<%--//定损 --%>
											<tr>
												<td class="input" style="display: none">
													<input type="hidden" name="carLossRepairFeeLossItemCode" style="width: 20px" value="${pageScope.repairFeeNo - 1}">
													<input type="text" name="prpLrepairFeeKindCode" class="codecode" style='width:40px ${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/>
														value="${prpLrepairFee1.kindCode}"
														<c:if test="${empty pageScope.compensatebackReadOnly}">
								                       ondblclick= "code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
								                       onkeyup= "code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
								                      </c:if>>
												</td>
												<td class="input" style="width: 20%;">
													<input type="text" name="prpLrepairFeeKindName" class="codename" <c:out value="${pageScope.compensatebackReadOnly}"/> style='width:100%;${pageScope.compensatebackStyle}'
														value="${prpLrepairFee1.kindName}"
														<c:if test="${empty pageScope.compensatebackReadOnly}">
			  			          <c:choose>
			  			          <c:when test="${pageScope.lossItemCode =='1'}">
			  			             ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
			  			             onkeyup= "code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 			          
			  			          </c:when>
			  			          <c:otherwise>
			  			             ondblclick="code_CodeSelect(this,'PolicyKindCodeForThirdCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
			  			             onkeyup= "code_CodeSelect(this,'PolicyKindCodeForThirdCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"  			          
			  			          </c:otherwise>
			  			          </c:choose>
		                      </c:if>>
												</td>
												<td class="input" style="display: none">
													<select name="prpLrepairFeePartCode" styleClass="three" style="width: 60px">
														<c:forEach items="${requestScope.partCodeList}" var="labelValueBean">
															<%--//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang --%>
															<c:if test="${!('1'==pageScope.prpLrepairFee1.compensateBackFlag && pageScope.labelValueBean.key!=pageScope.prpLrepairFee1.partCode)}">
																<option value="${labelValueBean.key}"
																	<c:if test="${pageScope.labelValueBean.key==pageScope.prpLrepairFee1.partCode}">
		                             <c:out value="selected"/>
		                           </c:if>>
																	<c:out value="${labelValueBean.value}" />
																</option>
															</c:if>
														</c:forEach>
													</select>
													<input type="hidden" name="prpLrepairFeePartName" value="${prpLrepairFee1.partName}">
												</td>
												<td class="input" style="width: 30%;">
													<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
													<input name="prpLrepairFeeCompName" class="common" <c:out value="${pageScope.compensatebackReadOnly}"/>
														style="width:100%;
		                   <c:if test="${(not empty pageScope.prpLrepairFee1.flag) && fn:startsWith(pageScope.prpLrepairFee1.flag,'1')}">
		                     <c:out value=";color:'#FF0000'" />
		                   </c:if>
							<c:out value="${pageScope.compensatebackStyle}"/>"
														maxlength="100" value="${prpLrepairFee1.compName}">
													<input type="hidden" name="prpLrepairFeeCompCode" value="${prpLrepairFee1.compCode}">
												</td>
												<td class="input" style="width: 15%;">
													<select name="prpLrepairFeeRepairType" style="width:100%;${pageScope.compensatebackStyle}">
														<c:forEach items="${requestScope.repairTypes}" var="prpDcodeDto">
															<%--//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang --%>
															<c:if test="${!('1'==pageScope.prpLrepairFee1.compensateBackFlag && prpDcodeDto.id.codeCode!=pageScope.prpLrepairFee1.repairType)}">
																<option value="${prpDcodeDto.id.codeCode}"
																	<c:if test="${prpDcodeDto.id.codeCode==pageScope.prpLrepairFee1.repairType}">
										                             <c:out value="selected"/>
										                           </c:if>>
																	<c:out value="${prpDcodeDto.codeCName}" />
																</option>
															</c:if>
														</c:forEach>
													</select>
												</td>
												<td class="input" style="display: none">
													<input name="prpLrepairFeeManHour" maxlength=10 class=common style='width:100%;${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/>
														value="${prpLrepairFee1.manHour}" onBlur="return getSumDefLoss(this,1);">
												</td>
												<td class="input" style="display: none">
													<input name="prpLrepairFeeManHourUnitPrice" maxlength=10 class="common" style='width:100%;${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/>
														value="<fmt:formatNumber value='${prpLrepairFee1.manHourUnitPrice}' pattern='#'/>" onBlur="return getSumDefLoss(this,1);">
												</td>
												<td class="input" style="width: 10%;">
													<input name="prpLrepairFeeSumDefLoss" onblur="checkNum(this);sumRepairFee();" class="common" style='width:100%;${pageScope.compensatebackStyle}'
														<c:out value="${pageScope.compensatebackReadOnly}"/> value="<fmt:formatNumber value='${prpLrepairFee1.sumDefLoss}' pattern='#'/>">
												</td>
												<td class="input" style="display: none">
													<input name="prpLrepairFeeFirstSumDefLoss" class="readonly" readonly style='width:100%;${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/>
														value="<fmt:formatNumber value='${prpLrepairFee1.firstSumDefLoss}' pattern='#'/>">
												</td>
												<td class="input" style="width: 20%;">
													<input name="prpLrepairFeeRemark" class=common style='width:100%;${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/> value="${prpLrepairFee1.remark}">
												</td>
												<input type="hidden" name="prpLrepairFeeSerialNo" value="${prpLrepairFee1.id.serialNo}">
												<input type="hidden" name="prpLrepairFeeItemKindNo" value="${prpLrepairFee1.itemKindNo}">
												<input type="hidden" name="prpLrepairFeeLossItemCode" value="${prpLrepairFee1.id.lossItemCode}">
												<input type="hidden" name="prpLrepairFeeLicenseNo" value="${prpLrepairFee1.licenseNo}">
												<input type="hidden" name="prpLrepairFeeLicenseColorCode" value="${prpLrepairFee1.licenseColorCode}">
												<input type="hidden" name="prpLrepairFeeCarKindCode" value="${prpLrepairFee1.carKindCode}">
												<input type="hidden" name="prpLrepairFeeSanctioner" value="${prpLrepairFee1.sanctioner}">
												<input type="hidden" name="prpLrepairFeeApproverCode" value="${prpLrepairFee1.approverCode}">
												<input type="hidden" name="prpLrepairFeeOperatorCode" value="${prpLrepairFee1.operatorCode}">
												<input type="hidden" name="prpLrepairFeeManHourFee" value="${prpLrepairFee1.manHourFee}">
												<input type="hidden" name="prpLrepairFeeMaterialFee" value="${prpLrepairFee1.materialFee}">
												<input type="hidden" name="prpLrepairFeeLossRate" value="${prpLrepairFee1.lossRate}">
												<input type="hidden" name="prpLrepairFeeCurrency" value="${prpLrepairFee1.currency}">
												<input type="hidden" name="prpLrepairFeeVeriRemark" value="${prpLrepairFee1.remark}">
												<input type="hidden" name="prpLrepairFeeVeriManHour" value="${prpLrepairFee1.veriManHour}">
												<input type="hidden" name="prpLrepairFeeVeriManUnitPrice" value="${prpLrepairFee1.veriManUnitPrice}">
												<input type="hidden" name="prpLrepairFeeVeriManHourFee" value="${prpLrepairFee1.veriManHourFee}">
												<input type="hidden" name="prpLrepairFeeVeriMaterQuantity" value="${prpLrepairFee1.veriMaterQuantity}">
												<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice" value="${prpLrepairFee1.veriMaterUnitPrice}">
												<input type="hidden" name="prpLrepairFeeVeriMaterialFee" value="${prpLrepairFee1.veriMaterialFee}">
												<input type="hidden" name="prpLrepairFeeVeriLossRate" value="${prpLrepairFee1.veriLossRate}">
												<input type="hidden" name="prpLrepairFeeVeriSumLoss" value="${prpLrepairFee1.veriSumLoss}">
												<input type="hidden" name="prpLrepairFeeBackCheckRemark" value="${prpLrepairFee1.backCheckRemark}">
												<input type="hidden" name="prpLrepairFeeFlag" value="${prpLrepairFee1.flag}">
												<input type="hidden" name="prpLrepairFeeIndId" value="${prpLrepairFee1.indId}">
												<input type="hidden" name="prpLrepairFeeCompensateBackFlag" value="${prpLrepairFee1.compensateBackFlag}">
												<td class="input" style='width: 5%;' align="center">
													<div>
														<input type=button name="buttonRepairFeeDelete" class=smallbutton <c:out value="${pageScope.compensatebackDiasable}"/>
															onclick="deleteRowTableRepairFee(this,'RepairFee',1,1);sumRepairFee();" value="-" style="cursor: hand">
													</div>
												</td>
											</tr>
										</c:when>
										<c:otherwise>
											<%--//非定损- 退回定损--%>
											<input type="hidden" name="verifRowNumber">
											<tr>
												<td class="input" style="display: none">
													<input type="hidden" name="carLossRepairFeeLossItemCode" style="width: 100%;" value="${pageScope.repairFeeNo -1}">
													<input type="text" name="prpLrepairFeeKindCode" class="codecode" <c:out value="${pageScope.compensatebackReadOnly}"/> style='width:40px${pageScope.compensatebackStyle}'
														value="${prpLrepairFee1.kindCode}"
														<c:if test="${empty pageScope.compensatebackReadOnly}"> 
		                      ondblclick= "code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
		                      onkeyup= "code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
		                     </c:if>>
												</td>
												<td class="input" style="width: 20%;">
													<input type="text" name="prpLrepairFeeKindName" class="codename" <c:out value="${pageScope.compensatebackReadOnly}"/> style="width:100%;${pageScope.compensatebackStyle}"
														value="${prpLrepairFee1.kindName}"
														<c:if test="${empty pageScope.compensatebackReadOnly}">
			  			          <c:choose>
			  			          <c:when test="${pageScope.lossItemCode =='1'}">
		  			                 ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
		  			                 onkeyup= "code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
			  			          </c:when>
			  			          <c:otherwise>
		  			                 ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
		  			                 onkeyup= "code_CodeSelect(this,'PolicyKindCodeForCar','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
			  			          </c:otherwise>
			  			          </c:choose>
		                      </c:if>>
												</td>
												<td class="input" style="display: none">
													<select name="prpLrepairFeePartCode" styleClass="three" style="width: 100%;">
														<c:forEach items="${requestScope.partCodeList}" var="labelValueBean">
															<%--//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang --%>
															<c:if test="${!('1'==pageScope.prpLrepairFee1.compensateBackFlag && pageScope.labelValueBean.key!=pageScope.prpLrepairFee1.partCode)}">
																<option value="${labelValueBean.key}"
																	<c:if test="${pageScope.labelValueBean.key==pageScope.prpLrepairFee1.partCode}">
		                             <c:out value="selected"/>
		                           </c:if>>
																	<c:out value="${labelValueBean.value}" />
																</option>
															</c:if>
														</c:forEach>
													</select>
													<input type="hidden" name="prpLrepairFeePartName" value="${prpLrepairFee1.partName}">
												</td>
												<td class="input" style="width: 30%;">
													<input name="prpLrepairFeeCompName" class="common" <c:out value="${pageScope.compensatebackReadOnly}"/>
														style="width:100%;
		                   <c:if test="${(not empty pageScope.prpLrepairFee1.flag) && fn:startsWith(pageScope.prpLrepairFee1.flag,'1')}">
		                     <c:out value=";color:'#FF0000'" />
		                   </c:if>
							<c:out value="${pageScope.compensatebackStyle}"/>"
														maxlength="80" value="${prpLrepairFee1.compName}">
													<input type="hidden" name="prpLrepairFeeCompCode" value="${prpLrepairFee1.compCode}">
												</td>
												<td class="input" style="width: 15%;">
													<select name="prpLrepairFeeRepairType" styleClass="three" style="width:100%;${pageScope.compensatebackStyle}">
														<c:forEach items="${requestScope.repairTypes}" var="prpDcodeDto">
															<%--//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang --%>
															<c:if test="${!('1'==pageScope.prpLrepairFee1.compensateBackFlag && prpDcodeDto.id.codeCode!=pageScope.prpLrepairFee1.repairType)}">
																<option value="${prpDcodeDto.id.codeCode}"
																	<c:if test="${prpDcodeDto.id.codeCode==pageScope.prpLrepairFee1.repairType}">
		                             <c:out value="selected"/>
		                           </c:if>>
																	<c:out value="${prpDcodeDto.codeCName}" />
																</option>
															</c:if>
														</c:forEach>
													</select>
												</td>
												<td class="input" style="display: none">
													<input name="prpLrepairFeeManHour" class=common style='width:100%;${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/> value="${prpLrepairFee1.manHour}"
														onBlur="return getSumDefLoss(this,1);">
												</td>
												<td class="input" style="display: none">
													<input name="prpLrepairFeeManHourUnitPrice" class="common" style='width:100%;${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/>
														value="${prpLrepairFee1.manHourUnitPrice}" onBlur="return getSumDefLoss(this,1);">
												</td>
												<td class="input" style="width: 10%;">
													<input name="prpLrepairFeeSumDefLoss" class="common" onblur="checkNum(this);" style='width:100%;${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/>
														value="<fmt:formatNumber value='${prpLrepairFee1.sumDefLoss}' pattern='#'/>">
												</td>
												<td class="input" style="display: none">
													<input name="prpLrepairFeeFirstSumDefLoss" class="readonly" readonly style='width:100%;${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/>
														value="${prpLrepairFee1.firstSumDefLoss}" />
												</td>
												<td class="input" style="width: 20%;">
													<input name="prpLrepairFeeRemark" class=common style='width:100%;${pageScope.compensatebackStyle}' <c:out value="${pageScope.compensatebackReadOnly}"/> value="${prpLrepairFee1.remark}">
												</td>
												<input type="hidden" name="prpLrepairFeeSerialNo" value="${prpLrepairFee1.id.serialNo}">
												<input type="hidden" name="prpLrepairFeeItemKindNo" value="${prpLrepairFee1.itemKindNo}">
												<input type="hidden" name="prpLrepairFeeLossItemCode" value="${prpLrepairFee1.id.lossItemCode}">
												<input type="hidden" name="prpLrepairFeeLicenseNo" value="${prpLrepairFee1.licenseNo}">
												<input type="hidden" name="prpLrepairFeeLicenseColorCode" value="${prpLrepairFee1.licenseColorCode}">
												<input type="hidden" name="prpLrepairFeeCarKindCode" value="${prpLrepairFee1.carKindCode}">
												<input type="hidden" name="prpLrepairFeeSanctioner" value="${prpLrepairFee1.sanctioner}">
												<input type="hidden" name="prpLrepairFeeApproverCode" value="${prpLrepairFee1.approverCode}">
												<input type="hidden" name="prpLrepairFeeOperatorCode" value="${prpLrepairFee1.operatorCode}">
												<input type="hidden" name="prpLrepairFeeManHourFee" value="${prpLrepairFee1.manHourFee}">
												<input type="hidden" name="prpLrepairFeeMaterialFee" value="${prpLrepairFee1.materialFee}">
												<input type="hidden" name="prpLrepairFeeLossRate" value="${prpLrepairFee1.lossRate}">
												<input type="hidden" name="prpLrepairFeeCurrency" value="${prpLrepairFee1.currency}">
												<input type="hidden" name="prpLrepairFeeVeriManHourFee" value="${prpLrepairFee1.veriManHourFee}">
												<input type="hidden" name="prpLrepairFeeVeriMaterialFee" value="${prpLrepairFee1.veriMaterialFee}">
												<input type="hidden" name="prpLrepairFeeVeriMaterQuantity" value="${prpLrepairFee1.veriMaterQuantity}">
												<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice" value="${prpLrepairFee1.veriMaterUnitPrice}">
												<input type="hidden" name="prpLrepairFeeVeriLossRate" value="${prpLrepairFee1.veriLossRate}">
												<input type="hidden" name="prpLrepairFeeBackCheckRemark" value="${prpLrepairFee1.backCheckRemark}">
												<input type="hidden" name="prpLrepairFeeFlag" value="${prpLrepairFee1.flag}">
												<input type="hidden" name="prpLrepairFeeIndId" value="${prpLrepairFee1.indId}">
												<input type="hidden" name="prpLrepairFeeCompensateBackFlag" value="${prpLrepairFee1.compensateBackFlag}">
												<td class="input" style='width: 5%; display: none' align="center">
													<div>
														<input type="hidden" name="txtRepairFeeBackFlag">
														<input type="button" name="buttonRepairFeeDelete" class="smallbutton" <c:out value="${pageScope.compensatebackDiasable}"/>
															onclick="deleteRowTableRepairFee(this,'RepairFee',1,2);sumRepairFee();" value="-" style="cursor: hand">
													</div>
												</td>
											</tr>
											<tr>
												<td class="input" colspan="3" style="width: 55%;">&nbsp;</td>
												<td class="input" style="display: none">
													<input name="prpLrepairFeeVeriManHour"
														<c:choose>
			                     <c:when test="${(not empty param.nodeType) && (param.nodeType =='certa' || param.nodeType =='verif')}">
			                        class="readonly" readonly 
			                     </c:when>
			                     <c:otherwise>
			                        class="input"
			                     </c:otherwise>
			                   </c:choose>
														style='width: 100%;' value="${prpLrepairFee1.veriManHour}"
														<c:if test="${(not empty param.nodeType)&&!(param.nodeType =='certa' || param.nodeType =='verif')}">
			                      onBlur="return getSumDefLossVerify(this,1);"
			                   </c:if>>
												</td>
												<td class="input" style="display: none">
													<input name="prpLrepairFeeVeriManUnitPrice"
														<c:choose>
			                     <c:when test="${(not empty param.nodeType) && (param.nodeType =='certa' || param.nodeType =='verif')}">
			                        class="readonly" readonly 
			                     </c:when>
			                     <c:otherwise>
			                        class="input"
			                     </c:otherwise>
			                   </c:choose>
														style='width: 100%;' value="${prpLrepairFee1.veriManUnitPrice}"
														<c:if test="${(not empty param.nodeType)&&!(param.nodeType =='certa' || param.nodeType =='verif')}">
			                      onBlur="return getSumDefLossVerify(this,1);"
			                   </c:if>>
												</td>
												<td class="input" style="width: 10%;">
													<input name="prpLrepairFeeVeriSumLoss" class="readonly" readonly style='width: 100%;' value="<fmt:formatNumber value='${prpLrepairFee1.veriSumLoss}' pattern='#'/>" class="input">
												</td>
												<td class="input" style="width: 20%;">
													<input name="prpLrepairFeeVeriRemark" class="readonly" readonly style='width: 100%;' value="${prpLrepairFee1.veriRemark}">
												</td>
												<td class="input" style='width: 5%' align="center">&nbsp;</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
