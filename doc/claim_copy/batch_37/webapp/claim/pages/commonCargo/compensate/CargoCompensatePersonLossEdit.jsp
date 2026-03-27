<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@include file="/common/taglibs.jsp"%>
<!--建立显示的录入条，可以收缩显示的-->
<span style="display: none">
	<table class="common" style="display: none" id="PersonFeeLoss_Data"
		cellspacing="1" cellpadding="0">
		<tbody>
			<tr>
				<td class="inputsubsub">
					<input type="hidden" name="personLossSerialNo" style="width: 20px">
					<input type="input" name="prpLpersonLossKindCode" class="codecode"
						ondblclick="code_CodeSelect(this,'PolicyKindCode');"
						onkeyup="code_CodeSelect(this,'PolicyKindCode');">
					<input type="input" name="prpLpersonLossKindName" class="codename"
						ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');"
						onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');">
				</td>

				<td class="inputsubsub">
					<input name="prpLpersonLossLiabDetailCode" type="hidden"
						ondblclick=" code_CodeSelect(this,'ChargeCode');"
						onkeyup=" code_CodeSelect(this,'ChargeCode');">
					<input name="prpLpersonLossLiabDetailName" class="codename"
						style="width: 70px"
						ondblclick="code_CodeSelect(this, 'ChargeCode','-1','always','none','post');"
						onkeyup=" code_CodeSelect(this, 'ChargeCode','-1','always','none','post');">
				</td>
				<td class="inputsubsub">
					<input type="hidden" name="prpLpersonLossCurrency" class="codecode"
						ondblclick="code_CodeSelect(this,'Currency');"
						onkeyup="code_CodeSelect(this,'Currency');">
					<input class="input" name="prpLpersonLossCurrencyName"
						class="codename"
						ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');"
						onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
				</td>

				<td class="inputsubsub">
					<input name="prpLpersonLossSumLoss" class="common"
						style="width: 70px">
				</td>
				<td class="inputsubsub">
					<input name="prpLpersonLossAmount" class="common"
						style="width: 70px">
				</td>
				<td class="inputsubsub">
					<input name="prpLpersonLossClaimRate" class="common"
						style="width: 70px">
				</td>
				<td class="inputsubsub">
					<input name="prpLpersonLossDeductible" class="common"
						style="width: 70px">
				</td>
				<td class="inputsubsub">
					<input name="prpLpersonLossSumRealPay" class="common"
						style="width: 70px">
					<input type="hidden" name="prpLpersonLossUnitAmount">
					<input type="hidden" name="prpLpersonLossLossQuantity">
					<input type="hidden" name="prpLpersonLossFamilyName">
					<input type="hidden" name="prpLpersonLossIndemnityDutyRate">
					<input type="hidden" name="prpLpersonLossDeductibleRate">
					<input type="hidden" name="prpLpersonLossItemKindNo">
					<input type="hidden" name="prpLpersonLossFamilyNo">
					<input type="hidden" name="prpLpersonLossIdentifyNumber">
					<input type="hidden" name="prpLpersonLossLiabCode">
					<input type="hidden" name="prpLpersonLossLiabName">
					<input type="hidden" name="prpLpersonLossJobCode">
					<input type="hidden" name="prpLpersonLossJobName">
					<input type="hidden" name="prpLpersonLossItemAddress">
					<input type="hidden" name="prpLpersonLossUnit">
					<input type="hidden" name="prpLpersonLossCurrency2" value="CNY">
					<input type="hidden" name="prpLpersonLossCurrency1" value="CNY">
					<input type="hidden" name="prpLpersonLossItemValue">
					<input type="hidden" name="prpLpersonLossSumRest">
					<input type="hidden" name="prpLpersonLossCurrency4" value="CNY">
					<input type="hidden" name="prpLpersonLossFlag">
					<input type="hidden" name="prpLpersonLossCurrency3" value="CNY">
				</td>

				<td class="inputsubsub">
					<div align="center">
						<input type=button name="buttonPersonFeeLossDelete"
							class="smallbutton"
							onclick="deleteRowTable(this,'PersonFeeLoss',1,1)" value="-"
							readonly style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>

<table class="common" align="center">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif"
				name="PersonImg" onclick="showPage(this,spanPerson)">
			<s:text name="certainLoss.paymentInformation" />
			<br>
			<%--赔付人员信息  --%>
			<span style="display: none">
				<table class="common" style="display: none" id="Person_Data"
					cellspacing="1" cellpadding="0">
					<tbody>

						<tr>
							<td class="input" style="width: 4%">
								<div align="center">
									<input class="readonlyNo" readonly
										name="prpLpersonLossSerialNo" description="<s:text name='regist.prpLregist.serialNo'/>"><%--序号--%>
								</div>
							</td>
							<td class="subformtitle" style="width: 92%">
								<table cellpadding="0" cellspacing="1" class="common">
									<tbody>
										<tr>
											<input type="hidden" name="prpLpersonLossPersonNo">
											<td class="title">
												<s:text name="db.prpLperson.personName" />
												：
											</td>
											<%-- 人员姓名 --%>
											<td class="input">
												<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
												<input class='common' style="width: 160px"
													name="prpLpersonLossPersonName" maxlength="100"
													description="<s:text name='db.prpLperson.personName' />">
												<img src="/claim/images/bgMarkMustInput.jpg">
											</td>
											<td class="title">
												<s:text name="db.prpCCarDriver.sex" />
												：
											</td>
											<%-- 性别 --%>
											<td class="input">
												<select name="prpLpersonLossSex" class='common'
													style="width: 160px">
													<option value="1">
														<s:text name="certainLoss.male" />
													</option>
													<%--男 --%>
													<option value="2">
														<s:text name="certainLoss.female" />
													</option>
													<%--女  --%>
												</select>
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="db.prpLpersonloss.age" />
												：
											</td>
											<%-- 年龄 --%>
											<td class="input">
												<input class='common' name="prpLpersonLossAge"
													style="width: 160px" maxlength="3" description="<s:text name='db.prpLpersonloss.age'/>">
											</td>
											<td class="title">
												<s:text name="compensate.paymentsTotal" />
												：
											</td>
											<%--赔付合计(CNY)  --%>
											<td class="input">
												<input type="text" class='readonly' readonly
													style="width: 160px" name="prpLpersonLossSumRealPay1">
											</td>
										</tr>

										<tr>
											<td colspan="4">
												<span id="spanPersonFeeLoss"> <%-- 多行输入展现域 --%>
													<table id="PersonFeeLoss" name="PersonFeeLoss"
														class="common" align="center" cellspacing="1"
														cellpadding="0">
														<thead>
															<tr>
																<td class="subformtitle" colspan="9">
																	<s:text name="prompt.compensate.costInfo" />
																</td>
																<%--费用信息  --%>
															</tr>
															<tr>
																<td class="centertitle">
																	<s:text
																		name="certainLoss.thirdCarLoss.prpLcheckRiskType" />
																</td>
																<%-- 险别 --%>
																<td class="centertitle">
																	<s:text
																		name="commonAcci.certainLoss.responsibilityDetail" />
																</td>
																<%-- 责任明细 --%>
																<td class="centertitle">
																	<s:text name="db.prpLlawsuit.currency" />
																</td>
																<%-- 币别 --%>
																<td class="centertitle">
																	<s:text name="compensate.amountNucDamage" />
																</td>
																<%-- 核损金额 --%>
																<td class="centertitle">
																	<s:text name="db.prpLmedicine.indemnityLimit" />
																</td>
																<%-- 赔偿限额 --%>
																<td class="centertitle">
																	<s:text
																		name="commonAcci.compensate.compensatPercentage" />
																</td>
																<%-- 赔偿比例% --%>
																<td class="centertitle">
																	<s:text name="db.prpLCitemKind.deductible" />
																</td>
																<%-- 免赔额 --%>
																<td class="centertitle">
																	<s:text name="db.prpLCMain.sumClaim" />
																</td>
																<%--赔付金额  --%>
																<td class="centertitle" style="width: 3%"></td>
															</tr>
														</thead>
														<tfoot>
															<tr>
																<td class="titlesubsub" colspan="8" style="width: 97%"></td>
																<td class="title" align="right" style="width: 4%">
																	<div align="center">
																		<input type="button" value="+" class="smallbutton"
																			onclick="insertRowTable('PersonFeeLoss','PersonFeeLoss_Data',this);"
																			name="buttonPersonFeeLossInsert" readonly
																			style="cursor: hand">
																	</div>
																</td>
															</tr>
														</tfoot>

														<tbody>

														</tbody>
													</table>
												</span>
											</td>
										</tr>

									</tbody>
								</table>
							</td>
							<td class="input" style="width: 4%">
								<div align="center">
									<input type=button name="buttonPersonDelete"
										class="smallbutton" onclick="deleteRow(this,'Person')"
										value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanPerson" style="display: none"> <%-- 多行输入展现域 --%>
				<table id="person" class="common" align="center" cellspacing="1"
					cellpadding="0">
					<thead>
						<tr>
							<td class="title" style="width: 4%">
								<s:text name="db.prpLcheckExt.serialNo" />
							</td>
							<%-- 序号 --%>
							<td class="title" style="width: 96%" colspan=2>
								<s:text name="db.prpLregistText.context" />
							</td>
							<%-- 内容 --%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=2 style="width: 96%">
								<s:text name="prompt.compensate.addRemove02" />
							</td>
							<%-- (按"+"号键增加赔付人员信息，按"-"号键删除赔付人员信息) --%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('person')"
										class="smallbutton" name="buttonPersonInsert"
										style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:set value="0" var="personNo" scope="page" />
						<c:forEach var="prpLpersonLossTemp"
							items="${prpLpersonLosss.prpLpersonLossList}"
							varStatus="prpLpersonLoss_status">
							<c:if test="${prpLpersonLossTemp.personNo!=personNo}">
								<tr>
									<td class="input" style="width: 4%">
										<div align="center">
											<input class="readonlyNo" readonly
												name="prpLpersonLossSerialNo" description="<s:text name='regist.prpLregist.serialNo'/>"
												value="${personNo+1 }"> <%--序号--%>
										</div>
									</td>
									<td class="subformtitle" style="width: 92%">
										<table cellpadding="0" cellspacing="1" class="common">
											<tbody>
												<tr>
													<input type="hidden" name="prpLpersonLossPersonNo"
														value="${prpLpersonLossTemp.personNo}">
													<td class="title">
														<s:text name="db.prpLperson.personName" />
														：
													</td>
													<%-- 人员姓名 --%>
													<td class="input">
														<input class='common' style="width: 160px"
															name="prpLpersonLossPersonName"
															value="${prpLpersonLossTemp.personName}" maxlength=20
															description="<s:text name='db.prpLperson.personName' />">
														<img src="/claim/images/bgMarkMustInput.jpg">
													</td>
													<td class="title">
														<s:text name="db.prpCCarDriver.sex" />
														：
													</td>
													<%-- 性别 --%>
													<td class="input">
														<select name="prpLpersonLossSex" class='common'
															style="width: 160px">
															<option value="1"
																<c:if test="${${prpLpersonLossTemp.sex=='1' }">selected</c:if>>
																<s:text name="certainLoss.male" />
															</option>
															<%--男 --%>
															<option value="2"
																<c:if test="${${prpLpersonLossTemp.sex=='2' }">selected</c:if>>
																<s:text name="certainLoss.female" />
															</option>
															<%--女  --%>
														</select>
													</td>
												</tr>
												<tr>
													<td class="title">
														<s:text name="db.prpLpersonloss.age" />
														：
													</td>
													<%-- 年龄 --%>
													<td class="input">
														<input class='common' name="prpLpersonLossAge"
															style="width: 160px" value="${prpLpersonLossTemp.age}"
															maxlength="3" description="<s:text name='db.prpLpersonloss.age'/>">
													</td>
													<td class="title">
														<s:text name="compensate.paymentsTotal" />
														：
													</td>
													<%--赔付合计(CNY)  --%>
													<td class="input">
														<input type="text" class='readonly' readonly
															style="width: 160px" name="prpLpersonLossSumRealPay1">
													</td>
												</tr>
												<tr>
													<td colspan="4">
														<span id="spanPersonFeeLoss"> <%-- 多行输入展现域 --%>
															<table id="PersonFeeLoss" name="PersonFeeLoss"
																class="common" align="center" cellspacing="1"
																cellpadding="0">
																<thead>
																	<tr>
																		<td class="subformtitle" colspan="9">
																			<s:text name="prompt.compensate.costInfo" />
																		</td>
																		<%--费用信息  --%>
																	</tr>
																	<tr>
																		<td class="centertitle">
																			<s:text
																				name="certainLoss.thirdCarLoss.prpLcheckRiskType" />
																		</td>
																		<%-- 险别 --%>
																		<td class="centertitle">
																			<s:text
																				name="commonAcci.certainLoss.responsibilityDetail" />
																		</td>
																		<%-- 责任明细 --%>
																		<td class="centertitle">
																			<s:text name="db.prpLlawsuit.currency" />
																		</td>
																		<%-- 币别 --%>
																		<td class="centertitle">
																			<s:text name="compensate.amountNucDamage" />
																		</td>
																		<%-- 核损金额 --%>
																		<td class="centertitle">
																			<s:text name="db.prpLmedicine.indemnityLimit" />
																		</td>
																		<%-- 赔偿限额 --%>
																		<td class="centertitle">
																			<s:text
																				name="commonAcci.compensate.compensatPercentage" />
																		</td>
																		<%-- 赔偿比例% --%>
																		<td class="centertitle">
																			<s:text name="db.prpLCitemKind.deductible" />
																		</td>
																		<%-- 免赔额 --%>
																		<td class="centertitle">
																			<s:text name="db.prpLCMain.sumClaim" />
																		</td>
																		<%--赔付金额  --%>
																		<td class="centertitle" style="width: 4%"></td>
																	</tr>
																</thead>
																<tfoot>
																	<tr>
																		<td class="titlesubsub" colspan="8" style="width: 97%"></td>
																		<td class="title" align="right" style="width: 4%">
																			<div align="center">
																				<input type="button" value="+" class="smallbutton"
																					onclick="insertRowTable('PersonFeeLoss','PersonFeeLoss_Data',this);"
																					name="buttonPersonFeeLossInsert" readonly
																					style="cursor: hand">
																			</div>
																		</td>
																	</tr>
																</tfoot>

																<tbody>
																	<c:forEach var="prpLpersonLossTemp2"
																		items="${prpLpersonLoss.prpLpersonLossList}"
																		varStatus="prpLpersonLoss2_status">
																		<c:if
																			test="${prpLpersonLossTemp2.personNo==personNo+1}">
																			<tr>

																				<td class="inputsubsub">
																					<input type="hidden" name="personLossSerialNo"
																						style="width: 20px" value="${personNo+1 }">
																					<input type="input" name="prpLpersonLossKindCode"
																						class="codecode"
																						value="${prpLpersonLossTemp.kindCode }"
																						ondblclick="code_CodeSelect(this,'PolicyKindCode');"
																						onkeyup="code_CodeSelect(this,'PolicyKindCode');">
																					<input type="input" name="prpLpersonLossKindName"
																						class="codename"
																						value="${prpLpersonLossTemp.kindName }"
																						ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');"
																						onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');">
																				</td>

																				<td class="inputsubsub">
																					<input name="prpLpersonLossLiabDetailCode"
																						type="hidden"
																						value="${prpLpersonLossTemp2.liabDetailCode }"
																						ondblclick=" code_CodeSelect(this,'ChargeCode');"
																						onkeyup=" code_CodeSelect(this,'ChargeCode');">
																					<input name="prpLpersonLossLiabDetailName"
																						class="codename" style="width: 70px"
																						value="${prpLpersonLossTemp2.liabDetailName }"
																						ondblclick="code_CodeSelect(this, 'ChargeCode','-1','always','none','post');"
																						onkeyup=" code_CodeSelect(this, 'ChargeCode','-1','always','none','post');">
																				</td>
																				<td class="inputsubsub">
																					<input type="hidden" name="prpLpersonLossCurrency"
																						value="${prpLpersonLossTemp2.currency }"
																						class="codecode"
																						ondblclick="code_CodeSelect(this,'Currency');"
																						onkeyup="code_CodeSelect(this,'Currency');">
																					<input class="input"
																						name="prpLpersonLossCurrencyName"
																						value="${prpLpersonLossTemp2.currencyName }"
																						class="codename"
																						ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');"
																						onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
																				</td>

																				<td class="inputsubsub">
																					<input name="prpLpersonLossSumLoss" class="common"
																						style="width: 70px"
																						value="${prpLpersonLossTemp2.sumLoss }">
																				</td>
																				<td class="inputsubsub">
																					<input name="prpLpersonLossAmount" class="common"
																						style="width: 70px"
																						value="${prpLpersonLossTemp2.amount }">
																				</td>
																				<td class="inputsubsub">
																					<input name="prpLpersonLossClaimRate"
																						class="common" style="width: 70px"
																						value="${prpLpersonLossTemp2.claimRate }">
																				</td>
																				<td class="inputsubsub">
																					<input name="prpLpersonLossDeductible"
																						class="common" style="width: 70px"
																						value="${prpLpersonLossTemp2.deductible }">
																				</td>
																				<td class="inputsubsub">
																					<input name="prpLpersonLossSumRealPay"
																						class="common" style="width: 70px"
																						value="${prpLpersonLossTemp2.sumRealPay }">
																					<input type="hidden"
																						name="prpLpersonLossUnitAmount"
																						value="${prpLpersonLossTemp2.unitAmount}">
																					<input type="hidden"
																						name="prpLpersonLossLossQuantity"
																						value="${prpLpersonLossTemp2.lossQuantity}">
																					<input type="hidden"
																						name="prpLpersonLossFamilyName"
																						value="${prpLpersonLossTemp2.familyName}">
																					<input type="hidden"
																						name="prpLpersonLossIndemnityDutyRate"
																						value="${prpLpersonLossTemp2.indemnityDutyRate}">
																					<input type="hidden"
																						name="prpLpersonLossDeductibleRate"
																						value="${prpLpersonLossTemp2.deductibleRate}">
																					<input type="hidden"
																						name="prpLpersonLossItemKindNo"
																						value="${prpLpersonLossTemp2.itemKindNo}">
																					<input type="hidden" name="prpLpersonLossFamilyNo"
																						value="${prpLpersonLossTemp2.familyNo}">
																					<input type="hidden"
																						name="prpLpersonLossIdentifyNumber"
																						value="${prpLpersonLossTemp2.identifyNumber}">
																					<input type="hidden" name="prpLpersonLossLiabCode"
																						value="${prpLpersonLossTemp2.liabCode}">
																					<input type="hidden" name="prpLpersonLossLiabName"
																						value="${prpLpersonLossTemp2.liabName}">
																					<input type="hidden" name="prpLpersonLossJobCode"
																						value="${prpLpersonLossTemp2.jobCode}">
																					<input type="hidden" name="prpLpersonLossJobName"
																						value="${prpLpersonLossTemp2.jobName}">
																					<input type="hidden"
																						name="prpLpersonLossItemAddress"
																						value="${prpLpersonLossTemp2.itemAddress}">
																					<input type="hidden" name="prpLpersonLossUnit"
																						value="${prpLpersonLossTemp2.unit}">
																					<input type="hidden" name="prpLpersonLossCurrency2"
																						value="${prpLpersonLossTemp2.currency2}">
																					<input type="hidden" name="prpLpersonLossCurrency1"
																						value="${prpLpersonLossTemp2.currency1}">
																					<input type="hidden" name="prpLpersonLossItemValue"
																						value="${prpLpersonLossTemp2.itemValue}">
																					<input type="hidden" name="prpLpersonLossSumRest"
																						value="${prpLpersonLossTemp2.sumRest}">
																					<input type="hidden" name="prpLpersonLossCurrency4"
																						value="${prpLpersonLossTemp2.currency4}">
																					<input type="hidden" name="prpLpersonLossFlag"
																						value="${prpLpersonLossTemp2.flag}">
																					<input type="hidden" name="prpLpersonLossCurrency3"
																						value="${prpLpersonLossTemp2.currency3}">

																				</td>
																				<td class="inputsubsub">
																					<div align="center">
																						<input type=button
																							name="buttonPersonFeeLossDelete"
																							class="smallbutton"
																							onclick="deleteRowTable(this,'PersonFeeLoss',1,1)"
																							value="-" readonly style="cursor: hand">
																					</div>
																				</td>
																			</tr>
																		</c:if>
																	</c:forEach>
																</tbody>
															</table>
														</span>
													</td>
												</tr>

											</tbody>
										</table>
									</td>
									<td class="input" style="width: 4%">
										<div align="center">
											<input type=button name="buttonPersonDelete"
												class="smallbutton" onclick="deleteRow(this,'Person')"
												value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
								<c:set value="${prpLpersonLossTemp.personNo}" var="personNo"
									scope="page" />
							</c:if>
						</c:forEach>
					</tbody>
				</table>

			</span>
		</td>
	</tr>
</table>
</span>
