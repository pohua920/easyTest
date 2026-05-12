<%--
****************************************************************************
* DESC       ：添加费用赔款信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-02-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
function viewDangerUnitCompensateCharge(field) {
	for (var i = 1; i < fm.prpLchargeSerialNo.length; i++) {
		if (fm.prpLchargeDangerNo[i] == field) {
			var count = i;
			var policyNo = fm.policyno.value;
			var damageDate = fm.damageStartDate.value;
			var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=CompensateCharge";
			window
				.open(
					submitStr,
					'查看危险单位信息',
					'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
		}
	}
}

function ownerShip_change(field) {
	var $ownerShip = $(field);
	var $payFeeTD = $ownerShip.parents("td[name='payFeeTD']");
	if ($ownerShip.val() == "B") { //汇款
		$payFeeTD.find("span[name='spanCutBack']").hide(); //隐藏禁背
		$payFeeTD.find("tr[name='bankInfo']").show(); //开放银行帳户录入
	} else {
		$payFeeTD.find("tr[name='bankInfo']").hide(); //关闭银行帳户录入
	}
	if ($ownerShip.val() == "Q") { //支票
		$payFeeTD.find("span[name='spanCutBack']").show(); //显示禁背
		$payFeeTD.find("tr[name='bankInfo']").hide(); //隐藏银行帳户录入
	} else {
		$payFeeTD.find("span[name='spanCutBack']").hide(); //隐藏禁背
	}
}
</script>
<table class="common" align="center">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="ChargeImg" onclick="showPage(this,spanCharge);"><b><s:text name="compensate.feePaymentInfo" /></b><br>
			<%-- 费用赔款信息 --%>
			<span style="display: none" id="Charge_Data">
				<table class="common" cellspacing="1" cellpadding="0" name="chargeObject">
					<tbody>
						<tr>
							<input type="hidden" type=text name="prpLchargeDangerNo" class="codecode" value="1" onClick="viewDangerUnitCompensateCharge(this);">
							<td class="input" style="width: 8%">
								<%-- 险别代码 --%>
								<input type="hidden" name="prpLchargeFlag">
								<input type="hidden" name="prpLchargeSerialNo" description="序号">
								<input name="prpLchargeKindCode" class="codecode" style="width: 100%" maxlength="3"
									ondblclick="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="clearPayObject(this);clearPaymentNew(this);code_CodeChange(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onblur="">
							</td>
							<td class="input" style="width: 25%">
								<%-- 险别名称 --%>
								<input name="prpLchargeKindName" class="codename" style="width: 100%"
									ondblclick="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onchange="clearPayObject(this);clearPaymentNew(this);code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onkeyup="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
									onblur="">
							</td>
							<input name="prpLchargeChargeLicenseNo" type="hidden">
							<td class="input" style="width: 15%">
								<input type="hidden" name="prpLchargeChargeCode" class="codecode" style="width: 100%" ondblclick="code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.riskcode.value);"
									onchange="code_CodeChange(this, 'ChargeCode','0,1','Y','Y',fm.riskcode.value); " onkeyup="code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.riskcode.value); " readonly="readonly">
								<input name="prpLchargeChargeName" class="codename" style="width: 100%"
									ondblclick="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.riskcode.value);"
									onchange="clearPayObject(this);clearPaymentNew(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.riskcode.value);"
									onkeyup="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.riskcode.value);" readonly="readonly">
								<!-- 
				    <s:select name="prpLchargeChargeCode" list="#request.chargeCodeList" listKey="key" listValue="value" headerKey="" headerValue=""/>
				    <input name="prpLchargeChargeName" type="hidden" class="codename" style="width:100%">-->
							</td>
							<td class="input" style="width: 11%">
								<s:select name="prpLchargePayObjectType" listKey="key" listValue="value" list="#request.payObjectTypeList" style="width:70px" />
							</td>
							<td class="input" style="width: 15%">
								<input type="hidden" name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="">
								<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="" ondblclick="getPayObjectNew(this);" onblur="setPrpLchargeOwnerName(this);" onchange="clearPaymentNew(this);">
							</td>
							<input name="prpLchargeCurrency" class="readonly" style="width: 100%; display: none;" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
							<td class="input" style="width: 10%">
								<input name="prpLchargeChargeReport" class="input" style="width: 100%" onfocus="cacheData(this);" onchange="validateMoney(this);setRealPayNew(this);" value="0" title="費用金額">
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLchargeChargeAmount" class="input" style="width: 100%" onfocus="cacheData(this);" onchange="validateMoney(this);setRealPayNew(this);" value="0" title="實際費用">
								<input name="prpLchargeSumRealPay" type="hidden" style="width: 100%" class='readonly' readonly value="0">
								<input type='hidden' name="prpLchargeAmount">
								<input type='hidden' name="prpLchargeExceptDeductiblePay" value="0">
								<input type='hidden' name="prpLchargeExceptDeductibleRate" value="0">
								<input name="prpLchargeFlag" type="hidden">
							</td>
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteChargeObject(this);" value="-" style="cursor: hand">
									<input type="hidden" name="prpLchargeFlag">
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="8" name="payFeeTD">
								<!-- 一个费用资讯信息 -->
								<table class="common" style="width: 100%">
									<tr>
										<td class="input" style="width: 10%">費用支付方式：</td>
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
										<td class="input" style="width: 10%">證件類型：</td>
										<td class="input" style="width: 20%">
											<s:select name="prpLchargeCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
										</td>
										<td class="input" style="width: 10%">洗錢狀態回覆：</td>
										<td class="input" style="width: 20%">
											<input name="prpLchargeAMLFlag" readOnly="readonly" class="readonly" >
										</td>
									</tr>
									<tr>
										<td class="input" style="width: 10%">賠付對象：</td>
										<td class="input" style="width: 20%">
											<input name="prpLchargeOwnerName" class="input" maxlength="50">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
										<td class="input" style="width: 10%">統一編號/身份證號：</td>
										<td class="input" style="width: 20%">
											<input name="prpLchargeUniformNo" class="input" style="width: 160px" maxlength="20">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
										<td class="input" style="width: 10%">
											<span name="spanCutBack" style="display: none">禁背：</span>
										</td>
										<td class="input" style="width: 20%">
											<span name="spanCutBack" style="display: none"><s:select name="prpLchargeCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="1" /></span>
										</td>
									</tr>
									<tr name="bankInfo">
										<td class="input" style="width: 10%">總行代號：</td>
										<%-- 總行代號 --%>
										<td class="input" style="width: 20%">
											<input name="prpLchargeBankCode" readOnly="readonly" class="readonly">
										</td>
										<td class="input" style="width: 10%">總行名稱：</td>
										<%-- 總行名稱 --%>
										<td class="input" style="width: 20%">
											<input name="prpLchargeBankName" readOnly="readonly" class="readonly">
										</td>
										<td class="input" style="width: 10%">匯款帳號：</td>
										<%-- 银行帳号 --%>
										<td class="input" style="width: 20%">
											<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly">
										</td>
									</tr>
									<tr name="bankInfo">
										<td class="input" style="width: 10%">分行代號：</td>
										<%-- 總行代號 --%>
										<td class="input" style="width: 20%">
											<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly">
										</td>
										<td class="input" style="width: 10%">分行名稱：</td>
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
									<!--  \claim\webapp\claim\pages\DAA\compensate\DAACompensateChargeEdit.jsp -->
										<td class="input" style="width: 10%">郵遞區號：</td>
										<td class="input" style="width: 20%">
											<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
											<input name="prpLchargeAreaCode" class="input" maxlength="3">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
										<td class="input" style="width: 10%">郵遞地址：</td>
										<td class="input" style="width: 50%" colspan="3">
											<input name="prpLchargeCourierAddress" class="input" maxlength="50" value="">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="2" bgcolor="block">
							<td colspan="8"></td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanCharge" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="PrpLcharge">
					<thead>
						<tr>
							<td class="centertitle" style="width: 8%">
								<s:text name="regist.prpLregist.kindCode" />
							</td>
							<%-- 险别代码 --%>
							<td class="centertitle" style="width: 25%">
								<s:text name="regist.prpLregist.kindName" />
							</td>
							<%-- 险别名称 --%>
							<td class="centertitle" style="width: 15%">費用名稱</td>
							<%-- 费用名称 --%>
							<td class="centertitle" style="width: 11%">
								<s:text name="compensate.paymentType" />
							</td>
							<%-- 支付类别 --%>
							<td class="centertitle" style="width: 15%">
								<s:text name="compensate.payNameObject" />
							</td>
							<%-- 支付对象名称 --%>
							<td class="centertitle" style="width: 10%">費用金額</td>
							<%-- 费用金额 --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.actualCost" />
							</td>
							<%-- 实际费用 --%>
							<td class="centertitle" style="width: 4%">
								<s:text name="certify.operate" />
							</td>
							<%-- 操作 --%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=7>
								<s:text name="prompt.compensate.addRemove" />
							</td>
							<%-- (按"+"号键增加费用赔款信息，按"-"号键删除信息) --%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertChargeObject(this);" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<tr>
							<td class="title" colspan=8 id="ChargeTD">
								<c:if test="${not empty requestScope.prpLcharge.prpLchargeList }">
									<c:forEach var="chargedtox" items="${requestScope.prpLcharge.prpLchargeList}" varStatus="status">
										<table class="common" cellspacing="1" cellpadding="0" name="chargeObject">
											<tbody>
												<tr>
													<input type="hidden" name="prpLchargeDangerNo" class="codecode" value="<c:out value='${chargedtox.dangerNo}'/>" onClick="viewDangerUnitCompensateCharge(this);">
													<td class="input" style="width: 8%">
														<input type="hidden" name="prpLchargeFlag" value="<c:out value='${chargedtox.flag}'/>">
														<input type="hidden" name="prpLchargeSerialNo" description="序号" value="<c:out value='${chargedtox.id.serialNo}'/>">
														<input name="prpLchargeKindCode" class="codecode" style="width: 100%" maxlength="3" value="<c:out value='${chargedtox.kindCode}'/>"
															ondblclick="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
															onchange="clearPayObject(this);clearPaymentNew(this);code_CodeChange(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
															onkeyup="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
															readonly="readonly">
													</td>
													<td class="input" style="width: 25%">
														<input name="prpLchargeKindName" class="codename" value="<c:out value='${chargedtox.kindName}'/>"
															ondblclick="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
															onchange="clearPayObject(this);clearPaymentNew(this);code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
															onkeyup="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
															readonly="readonly">
													</td>
													<input name="prpLchargeChargeLicenseNo" type="hidden" value="<c:out value='${chargedtox.licenseNo}'/>">
													<td class="input" style="width: 15%">
														<input type="hidden" name="prpLchargeChargeCode" class="codecode" style="width: 100%" ondblclick="code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.riskcode.value);"
															onchange="code_CodeChange(this, 'ChargeCode','0,1','Y','Y',fm.riskcode.value); " onkeyup="code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.riskcode.value); " readonly="readonly"
															value='${chargedtox.chargeCode}'>
														<input name="prpLchargeChargeName" class="codename" style="width: 100%"
															ondblclick="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.riskcode.value);"
															onchange="clearPayObject(this);clearPaymentNew(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.riskcode.value);"
															onkeyup="clearPayObject(this);clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.riskcode.value);" readonly="readonly"
															value="<c:out value='${chargedtox.chargeName}'/>">
														<!-- 
				                   <c:set var="tempSelectedValue" value='${chargedtox.chargeCode}'/>
				                   <s:select name="prpLchargeChargeCode" list="#request.chargeCodeList" listKey="key" listValue="value" value="#attr.tempSelectedValue"/>
				                   <input type="hidden" name="prpLchargeChargeName" class="codename" style="width:100%" value="<c:out value='${chargedtox.chargeName}'/>" /> -->
													</td>
													<td class="input" style="width: 11%">
														<c:set var="tempSelectedValue" value="${chargedtox.payObjectType}" />
														<s:select name="prpLchargePayObjectType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.payObjectTypeList" style="width:70px" />
													</td>
													<td class="input" style="width: 15%">
														<input type="hidden" name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="<c:out value='${chargedtox.payObjectCode}'/>">
														<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="<c:out value='${chargedtox.payObjectName}'/>" ondblclick="getPayObjectNew(this);"
															onchange="clearPaymentNew(this);">
													</td>
													<input name="prpLchargeCurrency" class="readonly" style="width: 100%; display: none;" value="<c:out value='${chargedtox.currency}'/>">
													<td class="input" style="width: 10%">
														<input name="prpLchargeChargeReport" class="input" style="width: 100%" value="<fmt:formatNumber value='${chargedtox.chargeReport}' pattern='#'/>" onfocus="cacheData(this);"
															onchange="validateMoney(this);setRealPayNew(this);" title="費用金額">
													</td>
													<td class="input" style="width: 10%">
														<input name="prpLchargeChargeAmount" class="input" style="width: 100%" value="<fmt:formatNumber value='${chargedtox.chargeAmount}' pattern='#'/>" onfocus="cacheData(this);"
															onchange="validateMoney(this);setRealPayNew(this);" title="實際費用">
														<input name="prpLchargeSumRealPay" type="hidden" class='readonly' readonly style="width: 100%" value="<c:out value='${chargedtox.sumRealPay}'/>">
														<input type='hidden' name="prpLchargeExceptDeductiblePay" value="<c:out value='${chargedtox.exceptDeductiblePay}'/>">
														<input type='hidden' name="prpLchargeExceptDeductibleRate" value="<c:out value='${chargedtox.exceptDeductibleRate}'/>">
														<input type='hidden' name="prpLchargeAmount">
														<input name="prpLchargeFlag" type="hidden" value="<c:out value='${chargedtox.flag}'/>">
													</td>
													<td class="input" style='width: 4%' align="center">
														<div>
															<input type=button name="buttonChargeDelete" class=smallbutton onclick="deleteChargeObject(this);" value="-" style="cursor: hand">
															<input type="hidden" name="prpLchargeFlag">
														</div>
													</td>
												</tr>
												<tr>
													<td colspan="8" name="payFeeTD">
														<table class="common" style="width: 100%">
															<tr>
																<td class="input" style="width: 10%">
																	費用支付方式：
																</td>
																<!-- 费用支付方式 -->
																<td class="input" style="width: 20%">
																	<select name="prpLchargeOwnerShip" onchange="ownerShip_change(this);">
																		<option value="B" <c:if test="${pageScope.chargedtox.ownerShip=='B'}"><c:out value="selected"/></c:if>>
																			<s:text name="compensate.remittance" />
																		</option>
																		<!-- 汇款 -->
																		<option value="Q" <c:if test="${pageScope.chargedtox.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
																			<s:text name="compensate.agentInfo.cheque" />
																		</option>
																		<!-- 支票 -->
																		<!--<option value="C" <c:if test="${pageScope.chargedtox.ownerShip=='C'}"><c:out value="selected"/></c:if> ><s:text name="compensate.agentInfo.cash"/></option> -->
																		<!-- 现金 -->
																	</select>
																</td>
																<td class="input" style="width: 10%">證件類型：</td>
																<td class="input" style="width: 20%">
																	<c:set var="tempCertificateCode" value='${chargedtox.prpLpayObjectInfo.certificateCode}' />
																	<s:select name="prpLchargeCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
																</td>
																<td class="input" style="width: 10%">洗錢狀態回覆：</td>
																<td class="input" style="width: 20%">
																	<input name="prpLchargeAMLFlag" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.amlFlag}'/>" >
																</td>
															</tr>
															<tr>
																<td class="input" style="width: 10%">賠付對象：</td>
																<td class="input" style="width: 18%">
																	<input name="prpLchargeOwnerName" class="input" maxlength="50" value="<c:out value='${chargedtox.prpLpayObjectInfo.ownerName}'/>">
																	<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
																</td>
																<td class="input" style="width: 8%">統一編號/身份證號：</td>
																<td class="input" style="width: 18%">
																	<input name="prpLchargeUniformNo" class="input" style="width: 160px" maxlength="20" value="<c:out value='${chargedtox.prpLpayObjectInfo.uniformNo}'/>">
																	<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
																</td>
																<td class="input" style="width: 12%">
																	<span name="spanCutBack" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}">style="display: none"</c:if>>禁背：</span>
																</td>
																<td class="input" style="width: 18%">
																	<span name="spanCutBack" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}">style="display: none"</c:if>> <c:set var="tempSelectedValue"
																			value='${chargedtox.prpLpayObjectInfo.cutBack}' /> <s:select name="prpLchargeCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="#attr.tempSelectedValue" />
																	</span>
																</td>
															</tr>
															<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}">style="display: none"</c:if>>
																<td class="input" style="width: 10%">總行代號：</td>
																<%-- 總行代號 --%>
																<td class="input" style="width: 18%">
																	<input name="prpLchargeBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankCode}'/>">
																</td>
																<td class="input" style="width: 8%">總行名稱：</td>
																<%-- 總行名稱 --%>
																<td class="input" style="width: 18%">
																	<input name="prpLchargeBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankName}'/>">
																</td>
																<td class="input" style="width: 12%">匯款帳號：</td>
																<%-- 银行帳号 --%>
																<td class="input" style="width: 18%">
																	<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.accountCode}'/>">
																</td>
															</tr>
															<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}">style="display: none"</c:if>>
																<td class="input" style="width: 10%">分行代號：</td>
																<%-- 總行代號 --%>
																<td class="input" style="width: 18%">
																	<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankCode}'/>">
																</td>
																<td class="input" style="width: 8%">分行名稱：</td>
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
															<!--  \claim\webapp\claim\pages\DAA\compensate\DAACompensateChargeEdit.jsp 2-->
																<td class="input" style="width: 10%">郵遞區號：</td>
																<td class="input" style="width: 18%">
																	<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
																	<input name="prpLchargeAreaCode" class="input" maxlength="3" value="<c:out value='${chargedtox.prpLpayObjectInfo.areaCode}'/>">
																	<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
																</td>
																<td class="input" style="width: 8%">郵遞地址：</td>
																<td class="input" style="width: 18%" colspan="3">
																	<input name="prpLchargeCourierAddress" class="input" maxlength="50" value="<c:out value='${chargedtox.prpLpayObjectInfo.courierAddress}'/>">
																	<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr height="2" bgcolor="block">
													<td colspan="8"></td>
												</tr>
											</tbody>
										</table>
									</c:forEach>
								</c:if>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" style="display: none">
	<tr>
		<td class="left" style="width: 15%">獨立處理費用：</td>
		<td class="right" style="width: 85%">
			<input type="text" class="input" maxlength="10" style="width: 80px" name="prpLcompensateIndependentCosts"
				value="<fmt:formatNumber value='${requestScope.prpLcompensate.independentCosts}' pattern='#'/>" onfocus="cacheData(this);" onchange="validateMoney(this);calFundNew();" title="獨立處理費用">
		</td>
	</tr>
</table>