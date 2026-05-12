<%--
****************************************************************************
* DESC       ：添加赔款费用信息页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2013-07-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@include file="/common/taglibs.jsp"%>
<!--建立显示的輸入条，可以收缩显示的-->
<script language='javascript'>
	//在下面加入本页自定义的JavaScript方法
	function viewDangerUnitCompensateCharge(field) {
		for ( var i = 1; i < fm.prpLchargeSerialNo.length; i++) {
			if (fm.prpLchargeDangerNo[i] == field) {
				var count = i;
				var policyNo = fm.policyno.value;
				var damageDate = fm.damageStartDate.value;
				field.value = "";
				var submitStr = "getDangerUnit.do?policyNo=" + policyNo
						+ "&damageDate=" + damageDate + "&openerIndex=" + count
						+ "&PageType=CompensateCharge";
				window.open(submitStr,'查看危險單位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
			}
		}
	}
	/*
	插入一条新的之後的处理（可选方法）
	 */
	function afterInsertCharge() {
		setPrpLchargeSerialNo();
	}

	/*
	  删除本条WarnRegion之後的处理（可选方法）
	 */
	function afterDeleteCharge(field) {
		setPrpLchargeSerialNo();
	}

	/**
	 * 设置setPrpLchargeSerialNo
	 */
	function setPrpLchargeSerialNo() {
		var count = getElementCount("prpLchargeSerialNo");
		for ( var i = 0; i < count; i++) {
			if (count != 1) {
				fm.prpLchargeSerialNo[i].value = i;
			}
		}
	}
</script>
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr class="common">
		<td colspan="5" style="text-align: left">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="ChargeImg" onclick="showPage(this,spanCharge)">
			<s:text name="check.compCosts" />
			<br>
			<%-- 赔款费用 --%>
			<span style="display: none">
				<table class=subtable cellpadding="0" cellspacing="1">
					<tr>
						<td>
							<table class=common style="display: none" id="Charge_Data" cellpadding="0" cellspacing="1" width="100%" name="chargeObject">
								<tbody>
									<tr>
										<td  class="input" style="width: 3%">
											<input type="text" name="prpLchargeSerialNo" description="<s:text name='regist.prpLregist.serialNo'/>" class="readonly" readonly="readonly"><%--序号--%>
										</td>
										<td class="input" style="width: 4%">
											<input type="hidden" name="prpLchargeFlag">
											<input name="prpLchargeDangerNo" class="codecode" value="1" onClick="viewDangerUnitCompensateCharge(this);" onkeyup="viewDangerUnitCompensateCharge(this);"
												onchange="viewDangerUnitCompensateCharge(this);">
										</td>
										<td class="input" style="width: 7%">
											<input name="prpLchargeKindCode" class="codecode" ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
												onchange="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);clearPrpLctextContextInnerHTML();"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
										</td>
										<td class="input" style="width: 10%">
											<input name="prpLchargeKindName" class="codename" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
												onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);clearPrpLctextContextInnerHTML();"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="setInput(this);">
											<input type="hidden" name="prpLchargeItemKindNo" value="0">
										</td>
										<td class="input" align="center" style="width: 7%">
											<input name="prpLchargeChargeCode" class="codecode" 
												ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
												onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
												onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this,'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" onblur="setInput(this);">
										</td>
										<td class="input" align="center" style="width: 10%">
											<input name="prpLchargeChargeName" class="codename" 
												ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
												onkeyup="clearPayObject(this);clearPayment(this);setInput(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
												onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" onblur="setInput(this);">
										</td>
										<td class="input" style="width: 6%">
											<s:select name="prpLchargePayObjectType" style="width: 55px" value="B" cssClass="common" listKey="key" listValue="value" list="#request.payObjectTypeList" />
										</td>
										<td class="input" style="width: 6%">
											<input name="prpLchargePayObjectCode" class="readonly" readonly value="">
										</td>
										<td class="input" style="width: 8%">
											<input name="prpLchargePayObjectName" class="codename"  value="" ondblclick="clearPayment(this);getPayObject(this);" onchange="clearPayment(this);getPayObject(this);"
												onkeyup="clearPayment(this);getPayObject(this);" onblur="">
										</td>
										<td class="input" style="width: 8%">
											<input name="prpLchargeChargeReport" class="input" onchange="setInput(this);" value="0">
										</td>
										<td class="input" align="center" style="width: 6%">
											<input name="prpLchargeSumRealPay" type="text" class="input" readOnly="readonly" style="display: none;" onchange="setInput(this);" value="0">
											<input name="prpLchargeChargeAmount" type="text" class="input" value="0" onchange="setInput(this);" onblur="isSameKindCode()">
										</td>
										<td class="input" align="center" style="width: 7%">
											<s:select name="prpLchargeCurrency" list="#request.prpLpayObjectInfoCurrencyList" onchange="getPrpLchargeExchRate(this);" style="width:98%;" class="common" listKey ="key" listValue="value" />
										</td>
										<td class="input" align="center" style="width: 4%">
											<input name="prpLchargeExchRate" value="1" onchange="calCurrencySumPay(this,3);" readonly="readonly" class="input" >
										</td>
										<td class="input" style="width: 6%">
											<input name="prpLchargeChargeAmountNTD" class="input" readonly="readonly" value="0">
										</td>
										<td class="input" style="width: 3%">
											<input name="prpLchargeFeeSerialNo" class="input"  value="">
										</td>
										<td class="input" style='width: 4%' align="center">
											<div>
												<input type=button name="buttonChargeDelete" class="smallbutton" onclick="directDeleteRow(this,'Charge',1,3);afterInsertCharge();calFund();" value="-" style="cursor: hand">
												<input type="hidden" name="prpLchargeFlag">
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="15">
											<table class="common" style="width: 100%">
												<tr>
													<td class="input" style="width: 10%"><s:text name='replevy.feePayment'/>：</td><%--费用支付方式--%>
													<td class="input" style="width: 20%">
														<select name="prpLchargeOwnerShip" onchange="chargeOwnerShipChange(this)">
															<option value="B" selected="selected">
																<s:text name="compensate.remittance" />
																<!-- 汇款 -->
															</option>
															<option value="Q">
																<s:text name="compensate.agentInfo.cheque" />
																<!-- 支票 -->
															</option>
														</select>
													</td>
													<td class="input" style="width: 10%">支付幣別：</td>
													<td class="input" style="width: 25%">
														<s:select name="prpLchargeAccountCurrency" class="common" listKey ="key" listValue="value" list="#request.prpLpayObjectInfoCurrencyList"/>
														<input type="hidden" name="prpLchargeCurrencyForPayObject" value="${LOCAL_CURRENCY }">
													</td>
													<td class="input" style="width: 10%">證件類型：</td>
													<td class="input" style="width: 25%">
														<s:select name="prpLchargeCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
													</td>
												</tr>
												<tr>
													<td class="input" style="width: 10%">賠付類型：</td><%--賠付類型--%>
													<td class="input" style="width: 20%">
														<s:select name="prpLchargePaymentKind" value="'4'" list="#request.paymentKindList" listKey="key" listValue="value" ></s:select>
														<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
													</td>
													<td class="input" style="width: 10%"></td>
													<td class="input" style="width: 25%">
													</td>
													<td class="input" style="width: 10%"></td>
													<td class="input" style="width: 25%">
													</td>
												</tr>
												<tr>
													<td class="input" style="width: 10%">
														<s:text name="compensate.paymentObject" />：
														<%-- 賠付對象 --%>
													</td>
													<td class="input" style="width: 20%">
														<input name="prpLchargeOwnerName" class="input" maxlength="30">
														<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
													</td>
													<td class="input" style="width: 10%"><s:text name='common.compensate.uniformNoOrID'/>：</td><%--统一编号/身份证号--%>
													<td class="input" style="width: 25%">
														<input name="prpLchargeUniformNo" class="input">
														<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
													</td>
													<td class="input" style="width: 10%">
														<span name="spanCutBack" style="display: none"><s:text name='common.compensate.cutBack'/>：</span><%--禁背--%>
													</td>
													<td class="input" style="width: 25%">
														<span name="spanCutBack" style="display: none"><s:select name="prpLchargeCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="1" /></span>
													</td>
												</tr>
												<tr name="bankInfo">
													<td class="input" style="width: 10%"><s:text name='common.compensate.bankCode'/>：</td><%--总行代号--%>
													<td class="input" style="width: 20%">
														<input name="prpLchargeBankCode" readOnly="readonly" class="readonly">
													</td>
													<td class="input" style="width: 10%"><s:text name='common.compensate.bankName'/>：</td><%--总行名称--%>
													<td class="input" style="width: 25%">
														<input name="prpLchargeBankName" readOnly="readonly" class="readonly">
													</td>
													<td class="input" style="width: 10%"><s:text name='common.compensate.accountCode'/>：</td><%--汇款账号--%>
													<td class="input" style="width: 25%">
														<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly">
													</td>
												</tr>
												<tr name="bankInfo">
													<td class="input" style="width: 10%"><s:text name="common.compensate.customBankCode"/>：</td><%--分行代号--%>
													<td class="input" style="width: 20%">
														<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly">
													</td>
													<td class="input" style="width: 10%"><s:text name='common.compensate.customBankName'/>：</td><%--分行名称--%>
													<td class="input" style="width: 25%">
														<input name="prpLchargeCustomBankName" readOnly="readonly" class="readonly">
													</td>
													<td class="input" colspan="2" align="center">
														<input class='bigbutton' type='button' name='buttonAddAcc' value='<s:text name='button.entryPaymentInfo.value' />' onclick="queryUser(this);" style="width: 180px">
														<%--輸入费用支付帳户信息 --%>
													</td>
												</tr>
												<tr>
												<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
												<!-- \claim\webapp\claim\pages\GAA\compensate\GAACompensateChargeEdit.jsp -->
													<td class="input" style="width: 10%"><s:text name='common.compensate.areaCode'/>：</td><%--邮递区号--%>
													<td class="input" style="width: 20%">
														<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
														<input name="prpLchargeAreaCode" class="input"  maxlength="3">
														<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
													</td>
													<td class="input" style="width: 10%"><s:text name='common.compensate.courierAddress'/>：</td><%--邮递地址--%>
													<td class="input" colspan="3">
														<input name="prpLchargeCourierAddress" class="input">
														<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
													</td>
												</tr>
											</table>
										</td>
										<td class="input" style="width: 4%"></td>
									</tr>
									<tr height="2" bgcolor="block">
										<td colspan="16"></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</table> <br>
			</span> <span id="spanCharge" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="Charge" name="chargeObject">
					<thead>
						<tr>
							<td class="centertitle" style="width: 3%">
								序號
							</td>
							<td class="centertitle" style="width: 4%">
								<s:text name="claim.dangeSerialNum" />
							</td>
							<%-- 危险单位序号 --%>
							<td class="centertitle" style="width: 7%">
								<s:text name="regist.prpLregist.kindCode" />
							</td>
							<%-- 险别代码 --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="certainLoss.prpLacciCheck.riskCName" />
							</td>
							<%-- 险种名称 --%>
							<td class="centertitle" style="width: 7%">
								<s:text name="compensate.costCode" />
							</td>
							<%--费用代码  --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.costName" />
							</td>
							<%--费用名称  --%>
							<td class="centertitle" style="width: 6%">
								<s:text name="compensate.paymentType" />
							</td>
							<%-- 支付类别 --%>
							<td class="centertitle" style="width: 6%">
								<s:text name="quickCase.payObjectCode" />
							</td>
							<%-- 支付对象编码 --%>
							<td class="centertitle" style="width: 8%">
								<s:text name="compensate.payNameObject" />
							</td>
							<%-- 支付对象名称 --%>
							<td class="centertitle" style="width: 8%">
								費用金額
							</td>
							<td class="centertitle" style="width: 6%">
								實際費用
							</td>
							<td class="centertitle" style="width: 7%">
								<s:text name="regist.prpLregist.currency" />
							</td>
							<%--币别  --%>
							<td class="centertitle" style="width: 4%">
								匯率
							</td>
							<td class="centertitle" style="width: 6%">
								費用金額NTD
							</td>
							<td class="centertitle" style="width: 3%">
								代扣稅序號
							</td>
							<td class="centertitle" style="width: 4%">操作</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=15 style="width: 96%">
								<s:text name="prompt.schedule.addRename11" />
							</td>
							<%-- (按"+"号键增加费用信息，按"-"号键删除信息)--%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('Charge')" class="smallbutton" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:if test="${not empty requestScope.prpLcharge.prpLchargeList }">
							<c:forEach var="chargedtox" items="${requestScope.prpLcharge.prpLchargeList}" varStatus="status">
								<tr>
									<td class="input" style="width: 3%">
										<input type="hidden" name="prpLchargeFlag" value="${chargedtox.flag}">
										<input type="text" name="prpLchargeSerialNo" description="序号" value="${chargedtox.id.serialNo}"  class="readonly" readonly="readonly">
									</td>
									<td class="input" style="width: 4%">
										<input type=text name="prpLchargeDangerNo" class="codecode" value="${chargedtox.dangerNo}" onClick="viewDangerUnitCompensateCharge(this);" onkeyup="viewDangerUnitCompensateCharge(this);"
											onchange="viewDangerUnitCompensateCharge(this);">
									</td>
									<td class="input" style="width: 7%">
										<input name="prpLchargeKindCode" class="codecode" value="${chargedtox.kindCode}" ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onchange="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);clearPrpLctextContextInnerHTML();"
											onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
									</td>
									<td class="input" style="width: 10%">
										<input name="prpLchargeKindName" class="codename" value="${chargedtox.kindName}" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
											onblur="setInput(this);">
										<input type="hidden" name="prpLchargeItemKindNo" value="${chargedtox.itemKindNo}">
									</td>
									<td class="input" align="center" style="width: 7%">
										<input name="prpLchargeChargeCode" class="codecode" value="${chargedtox.chargeCode}"
											ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
											onkeyup="clearPayObject(this);clearPayment(this);setInput(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
											onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" onblur="setInput(this);">
									</td>
									<td class="input" align="center" style="width: 10%">
										<input name="prpLchargeChargeName" class="codename"  value="${chargedtox.chargeName}"
											ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
											onkeyup="clearPayObject(this);clearPayment(this);setInput(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
											onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" onblur="setInput(this);">
									</td>
									<td class="input" style="width: 6%">
										<c:set var="tempSelectedValue" value="${chargedtox.payObjectType}" />
										<s:select name="prpLchargePayObjectType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.payObjectTypeList" />
									</td>
									<td class="input" style="width: 6%">
										<input name="prpLchargePayObjectCode" class="readonly" readonly  value="${chargedtox.payObjectCode}">
									</td>
									<td class="input" style="width: 8%">
										<input name="prpLchargePayObjectName" class="codename" value="${chargedtox.payObjectName}" ondblclick="clearPayment(this);getPayObject(this);"
											onchange="clearPayment(this);getPayObject(this);" onkeyup="clearPayment(this);getPayObject(this);" onblur="">
									</td>
									<td class="input" style="width: 8%">
										<input name="prpLchargeChargeReport" class="input" value="<fmt:formatNumber value='${chargedtox.chargeReport}' pattern='#.##'/>" onchange="setInput(this);">
									</td>
									<td class="input" style="width: 6%">
										<input name="prpLchargeSumRealPay" class="input" readOnly="readonly" value="<fmt:formatNumber value='${chargedtox.sumRealPay}' pattern='#'/>" onchange="setInput(this);" style="display: none;">
										<input name="prpLchargeChargeAmount" type="text" class="input" onchange="setInput(this);" value="<fmt:formatNumber value='${chargedtox.chargeAmount}' pattern='#.##'/>">
									</td>
									<td class="input" align="center" style="width: 7%">
										<s:select name="prpLchargeCurrency" list="#request.prpLpayObjectInfoCurrencyList" value="#attr.chargedtox.currency" onchange="getPrpLchargeExchRate(this);" style="width:98%;" class="common" listKey ="key" listValue="value" />
									</td>
									<td class="input" align="center" style="width: 4%">
										<input name="prpLchargeExchRate" value="${chargedtox.exchRate}" onchange="calCurrencySumPay(this,3);" readonly="readonly" class="input">
									</td>
									<td class="input" style="width: 6%">
										<input name="prpLchargeChargeAmountNTD" class="input" value="0" readonly="readonly">
									</td>
									<td class="input" style="width: 3%">
										<input name="prpLchargeFeeSerialNo" class="input"  value="${chargedtox.feeSerialNo}">
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonChargeDelete" class="smallbutton" onclick="directDeleteRow(this,'Charge',1,3);afterInsertCharge();calFund();" value="-" style="cursor: hand">
											<input type="hidden" name="prpLchargeFlag">
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="15">
										<table class="common" style="width: 100%">
											<tr>
												<td class="input" style="width: 10%">
													<s:text name="compensate.feePayment" />：
												</td>
												<!-- 费用支付方式 -->
												<td class="input" style="width: 20%">
													<select name="prpLchargeOwnerShip" onchange="chargeOwnerShipChange(this);">
														<option value="B" <c:if test="${pageScope.chargedtox.prpLpayObjectInfo.ownerShip=='B'}"><c:out value="selected"/></c:if>>
															<s:text name="compensate.remittance" />
														</option>
														<!-- 汇款 -->
														<option value="Q" <c:if test="${pageScope.chargedtox.prpLpayObjectInfo.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
															<s:text name="compensate.agentInfo.cheque" />
														</option>
														<!-- 支票 -->
													</select>
												</td>
												<td class="input" style="width: 10%">支付幣別：</td>
												<td class="input" style="width: 25%">
													<s:select name="prpLchargeAccountCurrency" value="#attr.chargedtox.prpLpayObjectInfo.accountCurrency" class="common" listKey ="key" listValue="value" list="#request.prpLpayObjectInfoCurrencyList"/>
													<input type="hidden" name="prpLchargeCurrencyForPayObject" value="${chargedtox.prpLpayObjectInfo.currency }">
												</td>
												<td class="input" style="width: 10%">證件類型：</td>
												<td class="input" style="width: 25%">
													<c:set var="tempCertificateCode" value='${chargedtox.prpLpayObjectInfo.certificateCode}' />
													<s:select name="prpLchargeCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
												</td>
											</tr>
											<tr>
												<td class="input" style="width: 10%">賠付類型：</td><%--賠付類型--%>
												<td class="input" style="width: 20%">
													<s:select name="prpLchargePaymentKind" value="#attr.chargedtox.prpLpayObjectInfo.paymentKind" list="#request.paymentKindList" listKey="key" listValue="value" ></s:select>
													<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
												</td>
												<td class="input" style="width: 10%"></td>
												<td class="input" style="width: 25%">
												</td>
												<td class="input" style="width: 10%"></td>
												<td class="input" style="width: 25%">
												</td>
											</tr>
											<tr>
												<td class="input" style="width: 10%"><s:text name='compensate.paymentObject'/>：</td><%--赔付对象--%>
												<td class="right" style="width: 20%">
													<input type="text" class='input' name="prpLchargeOwnerName" value="<c:out value='${chargedtox.prpLpayObjectInfo.ownerName}'/>" maxlength="30">
													<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
												</td>
												<td class="input" style="width: 10%"><s:text name='common.compensate.uniformNoOrID'/>：</td><%--统一编号/身份证号--%>
												<td class="right" style="width: 25%">
													<input type="text" class='input' name="prpLchargeUniformNo" value="<c:out value='${chargedtox.prpLpayObjectInfo.uniformNo}'/>">
													<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
												</td>
												<td class="input" style="width: 10%">
													<span name="spanCutBack" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}"> style="display:none" </c:if>><s:text name='common.compensate.cutBack'/></span><%--禁背--%>
												</td>
												<td class="input" style="width: 25%">
													<span name="spanCutBack" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}"> style="display:none" </c:if>> 
														<c:set var="tempSelectedValue" value='${chargedtox.prpLpayObjectInfo.cutBack}' />
														<s:select name="prpLchargeCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="#attr.tempSelectedValue" />
													</span>
												</td>
											</tr>
											<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
												<td class="input" style="width: 10%"><s:text name='common.compensate.bankCode'/>：</td><%--总行代号--%>
												<td class="input" style="width: 20%">
													<input name="prpLchargeBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankCode}'/>">
												</td>
												<td class="input" style="width: 10%"><s:text name='common.compensate.bankName'/>：</td><%--总行名称--%>
												<td class="input" style="width: 25%">
													<input name="prpLchargeBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankName}'/>">
												</td>
												<td class="input" style="width: 10%"><s:text name='common.compensate.accountCode'/>：</td><%--汇款账号--%>
												<td class="input" style="width: 25%">
													<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.accountCode}'/>">
												</td>
											</tr>
											<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
												<td class="input" style="width: 10%"><s:text name="common.compensate.customBankCode"/>：</td><%--分行代号--%>
												<td class="input" style="width: 20%">
													<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankCode}'/>">
												</td>
												<td class="input" style="width: 10%"><s:text name='common.compensate.customBankName'/>：</td><%--分行名称--%>
												<td class="input" style="width: 25%">
													<input name="prpLchargeCustomBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankName}'/>">
												</td>
												<td class="input" colspan="2" align="center">
													<input class='bigbutton' type='button' name='buttonAddAcc' value='<s:text name='button.entryPaymentInfo.value' />' onclick="queryUser(this);" style="width: 180px;">
													<%--輸入费用支付帳户信息 --%>
												</td>
											</tr>
											<tr>
												<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
												<!--  \claim\webapp\claim\pages\GAA\compensate\GAACompensateChargeEdit.jsp 2-->
												<td class="input" style="width: 10%"><s:text name='common.compensate.areaCode'/>：</td><%--邮递区号--%>
												<td class="right" style="width: 20%">
													<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
													<input type="text" class='input' name="prpLchargeAreaCode" value="<c:out value='${chargedtox.prpLpayObjectInfo.areaCode}'/>" maxlength="3">
													<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
												</td>
												<td class="input" style="width: 10%"><s:text name='common.compensate.courierAddress'/>：</td><%--邮递地址--%>
												<td class="right"  colspan="3">
													<input type="text" class='input' name="prpLchargeCourierAddress" value="<c:out value='${chargedtox.prpLpayObjectInfo.courierAddress}'/>">
													<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
												</td>
											</tr>
										</table>
									</td>
									<td class="input" style="width: 4%"></td>
								</tr>
								<tr height="2" bgcolor="block">
									<td colspan="16"></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
