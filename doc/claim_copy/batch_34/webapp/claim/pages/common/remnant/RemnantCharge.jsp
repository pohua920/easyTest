<%@ include file="/common/taglibs.jsp"%>

<span style="display: none;">
	<table class="common" cellspacing="1" cellpadding="0" id="Charge_Data">
		<tbody>
			<tr name="trCharge">
				<td class="input" style="width: 3%">
					<input type="text" name="prpLchargeSerialNo" description="序号" value="0" class="readonly" readonly="readonly">
				</td>
				<td class="title" colspan=9 style="width: 93%">
					<table class="common" cellspacing="1" cellpadding="0" name="chargeObject">
						<tr>
							<td class="input" style="width: 10%">
								<input type="hidden" type=text name="prpLchargeDangerNo" class="codecode" value="1" onClick="viewDangerUnitCompensateCharge(this);">
								<%-- 险别代码 --%>
								<input type="hidden" name="prpLchargeFlag">
								<input type="text" name="prpLchargeKindCode" class="codecode" style="width: 98%" maxlength="3"
									ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									>
							</td>
							<td class="input" style="width: 17%">
								<%-- 险别名称 --%>
								<input type="text" name="prpLchargeKindName" class="codename" style="width: 98%"
									ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									>
							</td>
							<td class="input" style="width: 22%">
								<input type="text" name="prpLchargeChargeCode" class="codecode" style="width: 20%"
									ondblclick="code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.riskCode.value);"
									onchange="code_CodeChange(this, 'ChargeCode','0,1','Y','Y',fm.riskCode.value); "
									onkeyup="code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.riskCode.value); " readonly="readonly">
								<input type="text" name="prpLchargeChargeName" class="codename" style="width: 68%"
									ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.riskCode.value);"
									onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.riskCode.value);"
									onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.riskCode.value);" readonly="readonly">
							</td>
							<td class="input" style="width: 10%">
								<input type="text" name="prpLchargeChargeReport" class="input" style="width: 98%" onfocus="cacheData(this);" onchange="setRealPay(this);" value="0" title="費用金額">
							</td>
							<td class="input" style="width: 11%">
								<input type="text" name="prpLchargeChargeAmount" class="input" style="width: 98%" onfocus="cacheData(this);" onchange="setRealPay(this);" value="0" title="實際費用">
								<input name="prpLchargeSumRealPay" type="hidden" style="width: 98%" class='readonly' readonly value="0">
								<input type='hidden' name="prpLchargeAmount">
								<input type='hidden' name="prpLchargeExceptDeductiblePay" value="0">
								<input type='hidden' name="prpLchargeExceptDeductibleRate" value="0">
								<input name="prpLchargeFlag" type="hidden">
							</td>
							<td class="input" style="width: 6%">
								<s:select name="prpLchargeCurrency" list="#request.prpLpayObjectInfoCurrencyList" onchange="setExchRateCharge(this);" listKey="key" listValue="key" style="width: 98%"></s:select>
							</td>
							<td class="input" style="width: 9%">
								<input type="text" name="prpLchargeExchRate" class="input" style="width: 96%" onfocus="cacheData(this);" onchange="setRealPay(this);" value="1" title="匯率">
							</td>
							<td class="input" style="width: 11%">
								<input type="text" name="prpLchargeCurrencyAmount"  onchange="setExchRateCharge(this);" class="readonly" style="width: 95%" readonly="readonly" value="0" title="費用金額(NTD)">
							</td>
							<td class="input" style="width: 5%">
								<input type="text" name="prpLchargeFeeSerialNo" class="input" style="width: 98%" value="" title="殘餘物序號">
							</td>
						</tr>
						<tr>
							<td colspan="9" name="payFeeTD" style="width: 100%">
								<!-- 一个费用资讯信息 -->
								<table class="common" style="width: 100%">
									<tr>
										<td class="input" style="width: 10%">
											對象类别
										</td>
										<td class="input" style="width: 20%">
											<s:select name="prpLchargePayObjectType" listKey="key" listValue="value" list="#request.payObjectTypeList" style="width:70px" />
										</td>
										<td class="input" style="width: 10%">
											對象名称 ：
										</td>
										<td class="input" style="width: 20%">
											<input type="hidden" name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="">
											<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="" ondblclick="getPayObject(this);" onblur="setPrpLchargeOwnerName(this);" onchange="clearPayment(this);">
										</td>
										<td class="input" style="width: 10%" />
										<td class="input" style="width: 20%" />
									</tr>
									<tr>
										<td class="input" style="width: 10%">
											費用支付方式：
										</td>
										<td class="input" style="width: 20%">
											<select name="prpLchargeOwnerShip" onchange="ownerShip_change(this)">
												<option value="B" selected="selected">
													<s:text name="compensate.remittance" />
													<!-- 汇款 -->
												</option>
												<option value="Q">
													<s:text name="compensate.agentInfo.cheque" />
													<!-- 支票 -->
												</option>
												<!--<option value="C"><s:text name="compensate.agentInfo.cash"/></option> -->
												<!-- 现金 -->
											</select>
										</td>
										<td class="input" style="width: 10%">
											證件類型：
										</td>
										<td class="input" style="width: 20%">
											<s:select name="prpLchargeCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
										</td>
										<td class="input" style="width: 10%" />
										<td class="input" style="width: 20%" />
									</tr>
									<tr>
										<td class="input" style="width: 10%">
											收取对象：
										</td>
										<td class="input" style="width: 20%">
											<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
											<input name="prpLchargeOwnerName" class="input" maxlength="100">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
										<td class="input" style="width: 10%">
											統一編號/身份證號：
										</td>
										<td class="input" style="width: 20%">
											<input name="prpLchargeUniformNo" class="input" style="width: 160px" maxlength="20">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
										<td class="input" style="width: 10%">
											<span name="spanCutBack" style="display: none">禁背：</span>
										</td>
										<td class="input" style="width: 20%">
											<span name="spanCutBack" style="display: none">
												<s:select name="prpLchargeCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="1" />
											</span>
										</td>
									</tr>
									<tr name="bankInfo">
										<td class="input" style="width: 10%">
											總行代號：
										</td>
										<%-- 總行代號 --%>
										<td class="input" style="width: 20%">
											<input name="prpLchargeBankCode" readOnly="readonly" class="readonly">
										</td>
										<td class="input" style="width: 10%">
											總行名稱：
										</td>
										<%-- 總行名稱 --%>
										<td class="input" style="width: 20%">
											<input name="prpLchargeBankName" readOnly="readonly" class="readonly">
										</td>
										<td class="input" style="width: 10%">
											匯款帳號：
										</td>
										<%-- 银行帳号 --%>
										<td class="input" style="width: 20%">
											<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly">
										</td>
									</tr>
									<tr name="bankInfo">
										<td class="input" style="width: 10%">
											分行代號：
										</td>
										<%-- 總行代號 --%>
										<td class="input" style="width: 20%">
											<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly">
										</td>
										<td class="input" style="width: 10%">
											分行名稱：
										</td>
										<%-- 總行名稱 --%>
										<td class="input" style="width: 20%">
											<input name="prpLchargeCustomBankName" readOnly="readonly" class="readonly">
										</td>
										<td class="input" style="width: 10%"></td>
										<td class="input" style="width: 20%">
											<input class='bigbutton' type='button' name='buttonAddPrpLcharge' value="<s:text name='button.entryPaymentInfo.value' />" onclick="queryUserNew(this);">
										</td>
										<%-- 录入费用支付帳户信息 --%>
									</tr>
									<tr>
									<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
									<!--\claim\webapp\claim\pages\common\remnant\RemnantCharge.jsp -->
										<td class="input" style="width: 10%">
											郵遞區號：
										</td>
										<td class="input" style="width: 20%">
											<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
											<input name="prpLchargeAreaCode" class="input" maxlength="3">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
										<td class="input" style="width: 10%">
											郵遞地址：
										</td>
										<td class="input" style="width: 50%" colspan="3">
											<input name="prpLchargeCourierAddress" class="input" maxlength="50" value="">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="2" bgcolor="block">
							<td colspan="9"></td>
						</tr>
					</table>
				</td>
				<td class="input" style='width: 4%' align="center">
					<div>
						<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow(this,'Charge','prpLchargeSerialNo');" value="-" style="cursor: hand">
						<input type="hidden" name="prpLchargeFlag">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<table class="common" align="center">
<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="ChargeImg" onclick="showPage(this,spanCharge);">
			<b><s:text name="compensate.feePaymentInfo" />
			</b>
			<br>
			<%-- 费用赔款信息 --%>
		</td>
	</tr>
</table>
<span id="spanCharge" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
	<table class="common" cellpadding="5" cellspacing="1" id="Charge">
		<thead>
			<tr>
				<td class="centertitle" style="width: 3%">序號</td>
				<td class="centertitle" style="width: 10%">
					<s:text name="regist.prpLregist.kindCode" />
					<%-- 险别代码 --%>
				</td>
				<td class="centertitle" style="width: 15%">
					<s:text name="regist.prpLregist.kindName" />
					<%-- 险别名称 --%>
				</td>
				<td class="centertitle" style="width: 20%">
					費用名稱
				</td>
				<td class="centertitle" style="width: 10%">
					費用金額
				</td>
				<td class="centertitle" style="width: 10%">
					<s:text name="compensate.actualCost" />
				</td>
				<td class="centertitle" style="width: 5%">幣別</td>
				<td class="centertitle" style="width: 8%">匯率</td>
				<td class="centertitle" style="width: 10%">費用金額(NTD)</td>
				<td class="centertitle" style="width: 5%">殘餘物序號</td>
				<td class="centertitle" style="width: 4%">
					<s:text name="certify.operate" />
					<%-- 操作 --%>
				</td>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td class="title" colspan=10>
					<s:text name="prompt.compensate.addRemove" />
				</td>
				<%-- (按"+"号键增加费用赔款信息，按"-"号键删除信息) --%>
				<td class="title" align="right" style="width: 4%">
					<div align="center">
						<input type="button" value="+" class=smallbutton onclick="insertRow('Charge',this,'prpLchargeSerialNo');" name="buttonDriverInsert" style="cursor: hand">
					</div>
				</td>
			</tr>
		</tfoot>
		<tbody>
			<c:forEach var="chargedtox" items="${remnantDto.prpLchargeList}" varStatus="status">
				<tr name="trCharge">
					<td class="input" style="width: 3%">
						<input type="text" name="prpLchargeSerialNo" description="序號" value="<c:out value='${chargedtox.id.serialNo}'/>" class="readonly" readonly="readonly">
					</td>
					<td class="title" colspan=9 style="width: 93%">
						<table class="common" cellspacing="1" cellpadding="0" name="chargeObject">
							<tr>
								<td class="input" style="width: 10%">
									<input type="hidden" name="prpLchargeDangerNo" class="codecode" value="<c:out value='${chargedtox.dangerNo}'/>" onClick="viewDangerUnitCompensateCharge(this);">
									<input type="hidden" name="prpLchargeFlag" value="<c:out value='${chargedtox.flag}'/>">
									<input type="text" name="prpLchargeKindCode" class="codecode" style="width: 98%" maxlength="3" value="<c:out value='${chargedtox.kindCode}'/>"
										ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.fm.prpLcompensatePolicyNo.value.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										readonly="readonly">
								</td>
								<td class="input" style="width: 17%">
									<input type="text" name="prpLchargeKindName" class="codename" value="<c:out value='${chargedtox.kindName}'/>" style="width: 98%"
										ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.prpLcompensatePolicyNo.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
										readonly="readonly">
								</td>
								<td class="input" style="width: 22%">
									<input type="text" name="prpLchargeChargeCode" class="codecode" style="width: 20%"
										ondblclick="code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.riskCode.value);"
										onchange="code_CodeChange(this, 'ChargeCode','0,1','Y','Y',fm.riskCode.value); "
										onkeyup="code_CodeSelect(this, 'ChargeCode', '0,1', 'Y', 'Y', fm.riskCode.value);"
										readonly="readonly" value='${chargedtox.chargeCode}'>
									<input name="prpLchargeChargeName" class="codename" style="width: 68%"
										ondblclick="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.riskCode.value);"
										onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.riskCode.value);"
										onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode', '-1,0', 'Y', 'N', fm.riskCode.value);"
										readonly="readonly" value="<c:out value='${chargedtox.chargeName}'/>">
								</td>
								<td class="input" style="width: 10%">
									<input name="prpLchargeChargeReport" class="input" style="width: 98%" value="<fmt:formatNumber value='${chargedtox.chargeReport}' pattern='#'/>"
										onfocus="cacheData(this);" onchange="setRealPay(this);" title="費用金額">
								</td>
								<td class="input" style="width: 11%">
									<input name="prpLchargeChargeAmount" class="input" style="width: 98%" value="<fmt:formatNumber value='${chargedtox.chargeAmount}' pattern='#'/>"
										onfocus="cacheData(this);" onchange="setRealPay(this);" title="實際費用">
									<input name="prpLchargeSumRealPay" type="hidden" class='readonly' readonly style="width: 98%" value="<c:out value='${chargedtox.sumRealPay}'/>">
									<input type='hidden' name="prpLchargeExceptDeductiblePay" value="<c:out value='${chargedtox.exceptDeductiblePay}'/>">
									<input type='hidden' name="prpLchargeExceptDeductibleRate" value="<c:out value='${chargedtox.exceptDeductibleRate}'/>">
									<input type='hidden' name="prpLchargeAmount">
									<input name="prpLchargeFlag" type="hidden" value="<c:out value='${chargedtox.flag}'/>">
								</td>
								<td class="input" style="width: 6%">
									<s:select name="prpLchargeCurrency" list="#request.prpLpayObjectInfoCurrencyList" value="#attr.chargedtox.currency" listKey="key" listValue="key" style="width: 98%"></s:select>
								</td>
								<td class="input" style="width: 9%">
									<input type="text" name="prpLchargeExchRate" class="input" style="width: 98%" onfocus="cacheData(this);" onchange="setRealPay(this);" value="${chargedtox.exchRate }" title="匯率">
								</td>
								<td class="input" style="width: 11%">
									<input type="text" name="prpLchargeCurrencyAmount" class="readonly" style="width: 96%" readonly="readonly" title="費用金額(NTD)" value="<fmt:formatNumber value='${chargedtox.chargeAmount*chargedtox.exchRate}' pattern='#'/>">
								</td>
								<td class="input" style="width: 5%">
									<input type="text" name="prpLchargeFeeSerialNo" class="input" style="width: 98%" value="${chargedtox.feeSerialNo }" title="殘餘物序號" >
								</td>
								</tr>
								<tr>
								<td colspan="9" name="payFeeTD" style="width: 100%">
									<table class="common" style="width: 100%">
										<tr>
											<td class="input" style="width: 10%">
												對象类别
											</td>
											<td class="input" style="width: 20%">
												<s:select name="prpLchargePayObjectType" listKey="key" listValue="value" list="#request.payObjectTypeList" value="#attr.chargedtox.payObjectType" style="width:70px" />
											</td>
											<td class="input" style="width: 10%">
												對象名称 ：
											</td>
											<td class="input" style="width: 20%">
												<input type="hidden" name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="<c:out value='${chargedtox.payObjectCode}'/>">
												<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="<c:out value='${chargedtox.payObjectName}'/>" ondblclick="getPayObject(this);" onblur="setPrpLchargeOwnerName(this);" onchange="clearPayment(this);">
											</td>
											<td class="input" style="width: 10%" />
											<td class="input" style="width: 20%" />
										</tr>
										<tr>
											<td class="input" style="width: 10%">
												費用支付方式：
											</td>
											<!-- 费用支付方式 -->
											<td class="input" style="width: 20%">
												<select name="prpLchargeOwnerShip" onchange="ownerShip_change(this);">
													<option value="B"
														<c:if test="${pageScope.chargedtox.ownerShip=='B'}"><c:out value="selected"/></c:if>>
														<s:text name="compensate.remittance" />
													</option>
													<!-- 汇款 -->
													<option value="Q"
														<c:if test="${pageScope.chargedtox.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
														<s:text name="compensate.agentInfo.cheque" />
													</option>
												</select>
											</td>
											<td class="input" style="width: 10%">
												證件類型：
											</td>
											<td class="input" style="width: 20%">
												<c:set var="tempCertificateCode" value='${chargedtox.prpLpayObjectInfo.certificateCode}' />
												<s:select name="prpLchargeCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
											</td>
											<td class="input" style="width: 10%" />
												<td class="input" style="width: 20%" />
										</tr>
										<tr>
											<td class="input" style="width: 10%">
												賠付對象：
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargeOwnerName" class="input" maxlength="50" value="<c:out value='${chargedtox.prpLpayObjectInfo.ownerName}'/>">
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
											<td class="input" style="width: 8%">
												統一編號/身份證號：
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLchargeUniformNo" class="input" style="width: 160px" maxlength="20" value="<c:out value='${chargedtox.prpLpayObjectInfo.uniformNo}'/>">
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
											<td class="input" style="width: 12%">
												<span name="spanCutBack"
													<c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}">style="display: none"</c:if>>禁背：
												</span>
											</td>
											<td class="input" style="width: 18%">
												<span name="spanCutBack"
													<c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}">style="display: none"</c:if>>
													<c:set var="tempSelectedValue" value='${chargedtox.prpLpayObjectInfo.cutBack}' />
													<s:select name="prpLchargeCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="#attr.tempSelectedValue" />
												</span>
											</td>
										</tr>
										<tr name="bankInfo"
											<c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}">style="display: none"</c:if>>
											<td class="input" style="width: 10%">
												總行代號：
											</td>
											<%-- 總行代號 --%>
											<td class="input" style="width: 18%">
												<input name="prpLchargeBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankCode}'/>">
											</td>
											<td class="input" style="width: 8%">
												總行名稱：
											</td>
											<%-- 總行名稱 --%>
											<td class="input" style="width: 18%">
												<input name="prpLchargeBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankName}'/>">
											</td>
											<td class="input" style="width: 12%">
												匯款帳號：
											</td>
											<%-- 银行帳号 --%>
											<td class="input" style="width: 18%">
												<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.accountCode}'/>">
											</td>
										</tr>
										<tr name="bankInfo"
											<c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}">style="display: none"</c:if>>
											<td class="input" style="width: 10%">
												分行代號：
											</td>
											<%-- 總行代號 --%>
											<td class="input" style="width: 18%">
												<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankCode}'/>">
											</td>
											<td class="input" style="width: 8%">
												分行名稱：
											</td>
											<%-- 總行名稱 --%>
											<td class="input" style="width: 18%">
												<input name="prpLchargeCustomBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankName}'/>">
											</td>
											<td class="input" style="width: 12%" colspan="2">
												<input class='bigbutton' type='button' name='buttonAddPrpLcharge' value="<s:text name='button.entryPaymentInfo.value' />" onclick="queryUserNew(this);">
											</td>
											<%-- 录入费用支付帳户信息 --%>
										</tr>
										<tr>
										<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
										<!--\claim\webapp\claim\pages\common\remnant\RemnantCharge.jsp 2-->
											<td class="input" style="width: 10%">
												郵遞區號：
											</td>
											<td class="input" style="width: 18%">
												<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
												<input name="prpLchargeAreaCode" class="input" maxlength="3" value="<c:out value='${chargedtox.prpLpayObjectInfo.areaCode}'/>">
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
											<td class="input" style="width: 8%">
												郵遞地址：
											</td>
											<td class="input" style="width: 18%" colspan="3">
												<input name="prpLchargeCourierAddress" class="input" maxlength="50" value="<c:out value='${chargedtox.prpLpayObjectInfo.courierAddress}'/>">
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr height="2" bgcolor="block">
								<td colspan="9"></td>
							</tr>
						</table>
					</td>
					<td class="input" style='width: 4%' align="center">
						<div>
							<input type=button name="buttonChargeDelete" class=smallbutton onclick="deleteRow(this,'Charge','prpLchargeSerialNo');" value="-" style="cursor: hand">
							<input type="hidden" name="prpLchargeFlag">
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</span>