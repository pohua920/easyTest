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
				<%-- 查看危險單位信息 --%>
				var submitStr = "getDangerUnit.do?policyNo=" + policyNo
						+ "&damageDate=" + damageDate + "&openerIndex=" + count
						+ "&PageType=CompensateCharge";
				window.open(submitStr,i18n.title.compensateEidt.dangerUnitInformation,'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
			}
		}
	}

	/*
	  删除本条WarnRegion之後的处理（可选方法）
	 */
	function afterDeleteCharge(field) {
		calFund(this);
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
										<input type="hidden" name="prpLchargeFlag">
										<input type="hidden" name="prpLchargeSerialNo" description="<s:text name='regist.prpLregist.serialNo'/>"><%--序号--%>
										<td class="input" style="width: 3%">
											<input name="prpLchargeDangerNo" class="codecode" value="1" onClick="viewDangerUnitCompensateCharge(this);" onkeyup="viewDangerUnitCompensateCharge(this);"
												onchange="viewDangerUnitCompensateCharge(this);">
										</td>
										<td class="input" style="width: 6%">
											<input name="prpLchargeKindCode" class="codecode" ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
												onchange="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);clearPrpLctextContextInnerHTML();"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
										</td>
										<td class="input" style="width: 6%">
											<input name="prpLchargeKindName" class="codename" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
												onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);clearPrpLctextContextInnerHTML();"
												onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="setInput(this);">
										</td>
										<td class="input" align="center" style="width: 6%">
											<input name="prpLchargeChargeCode" class="codecode" style="width: 95%"
												ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);"
												onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);"
												onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this,'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);" onblur="setInput(this);checkBeyondQuota(this);">
										</td>
										<td class="input" align="center" style="width: 8%">
											<input name="prpLchargeChargeName" class="codename" style="width: 95%"
												ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
												onkeyup="clearPayObject(this);clearPayment(this);setInput(this);checkBeyondQuota(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
												onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" onblur="setInput(this);checkBeyondQuota(this);">
										</td>
										<td class="input" style="width: 5%">
											<select name="prpLchargePayObjectType" class='common' style="width: 50px" onchange="clearPayObject(this);clearPayment(this);">
												<option value="B" selected>
													<s:text name="compensate.external" />
												</option>
												<%-- 外部 --%>
												<option value="A">
													<s:text name="compensate.internal" />
												</option>
												<%--内部  --%>
											</select>
										</td>
										<td class="input" style="width: 7%">
											<input name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="">
										</td>
										<td class="input" style="width: 10%">
											<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="" ondblclick="clearPayment(this);getPayObject(this);" onchange="clearPayment(this);getPayObject(this);"
												onkeyup="clearPayment(this);getPayObject(this);" onblur="">
										</td>
										<td class="input" align="center" style="width: 10%">
											<input type="text" name="prpLchargeCurrency" class="readonly" readonly style="width: 30%" value="${prpLcompensate.currency}">
											<input type="text" name="prpLchargeCurrencyName" class="readonly" readonly style="width: 59%" value="${prpLcompensate.currencyName}">
										</td>
										<td class="input" style="width: 10%">
											<input name="prpLchargeChargeReport" class="input" onchange="setInput(this);checkBeyondQuota(this);">
										</td>
										<td class="input" align="center" style="width: 10%">
											<input name="prpLchargeSumRealPay" class="input" readOnly="readonly" style="width: 95%" onchange="setInput(this);checkBeyondQuota(this);">
										</td>
										<td class="input" align="center" style="width: 9%">
											<input name="prpLchargeChargeAmount" class="input" readonly style="width: 95%" onchange="setInput(this);checkBeyondQuota(this);" onblur="isSameKindCode()">
										</td>
										<td class="input" style='width: 4%' align="center">
											<div>
												<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow(this,'Charge','prpLchargeSerialNo')" value="-" style="cursor: hand">
												<input type="hidden" name="prpLchargeFlag">
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="12">
											<table class="common" style="width: 100%">
												<tr>
													<td class="input" style="width: 15%"><s:text name='replevy.feePayment'/>：</td><%--费用支付方式--%>
													<td class="input" style="width: 18%">
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
													<td class="input" style="width: 15%"><s:text name="compensate.prpLpayObjectInfo.currency"/>：</td><%-- 支付幣別 --%>
													<td class="input" style="width: 85%">
														<s:select name="prpLchargeCurrencyForPayObject" class="common" listKey ="key" listValue="value" list="#request.prpLpayObjectInfoCurrencyList"/>
													</td>
													<td class="input" style="width: 10%"><s:text name="db.prpCinsured.identifytype"/>：</td><%-- 證件類型 --%>
													<td class="input" style="width: 20%">
														<s:select name="prpLchargeCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
													</td>
													<td class="input" style="width: 10%" />
													<td class="input" style="width: 20%" />
												</tr>
												<tr>
													<td class="input" style="width: 15%">
														<s:text name="compensate.paymentObject" />：
														<%-- 賠付對象 --%>
													</td>
													<td class="input" style="width: 18%">
														<input name="prpLchargeOwnerName" class="input">
														<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
													</td>
													<td class="input" style="width: 15%"><s:text name='common.compensate.uniformNoOrID'/>：</td><%--统一编号/身份证号--%>
													<td class="input" style="width: 18%">
														<input name="prpLchargeUniformNo" class="input">
														<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
													</td>
													<td class="input" style="width: 15%">
														<span name="spanCutBack" style="display: none"><s:text name='common.compensate.cutBack'/>：</span><%--禁背--%>
													</td>
													<td class="input" style="width: 18%">
														<span name="spanCutBack" style="display: none"><s:select name="prpLchargeCutBack" list="#{'1':'是','0':'否'}" listKey="key" listValue="value" value="1" /></span>
													</td>
												</tr>
												<tr name="bankInfo">
													<td class="input" style="width: 15%"><s:text name='common.compensate.bankCode'/>：</td><%--总行代号--%>
													<td class="input" style="width: 18%">
														<input name="prpLchargeBankCode" readOnly="readonly" class="readonly">
													</td>
													<td class="input" style="width: 15%"><s:text name='common.compensate.bankName'/>：</td><%--总行名称--%>
													<td class="input" style="width: 18%">
														<input name="prpLchargeBankName" readOnly="readonly" class="readonly">
													</td>
													<td class="input" style="width: 15%"><s:text name='common.compensate.accountCode'/>：</td><%--汇款账号--%>
													<td class="input" style="width: 18%">
														<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly">
													</td>
												</tr>
												<tr name="bankInfo">
													<td class="input" style="width: 15%"><s:text name="common.compensate.customBankCode"/>：</td><%--分行代号--%>
													<td class="input" style="width: 18%">
														<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly">
													</td>
													<td class="input" style="width: 15%"><s:text name='common.compensate.customBankName'/>：</td><%--分行名称--%>
													<td class="input" style="width: 18%">
														<input name="prpLchargeCustomBankName" readOnly="readonly" class="readonly">
													</td>
													<td class="input" style="width: 34%" colspan="2" align="center">
														<input class='bigbutton' type='button' name='buttonAddAcc' value='<s:text name='button.entryPaymentInfo.value' />' onclick="queryUser(this);" style="width: 180px">
														<%--輸入费用支付帳户信息 --%>
													</td>
												</tr>
												<tr>
												<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
												<!--  \claim\webapp\claim\pages\commonCargo\compensate\CargoCompensateChargeEdit.jsp -->
													<td class="input" style="width: 15%"><s:text name='common.compensate.areaCode'/>：</td><%--邮递区号--%>
													<td class="input" style="width: 18%">
														<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
														<input name="prpLchargeAreaCode" class="input" maxlength="3">
														<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
													</td>
													<td class="input" style="width: 15%"><s:text name='common.compensate.courierAddress'/>：</td><%--邮递地址--%>
													<td class="input" style="width: 52%" colspan="3">
														<input name="prpLchargeCourierAddress" class="input">
														<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
													</td>
												</tr>
											</table>
										</td>
										<td class="title"></td>
									</tr>
									<tr height="2" bgcolor="block">
										<td colspan="13"></td>
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
								<s:text name="claim.dangeSerialNum" />
							</td>
							<%-- 危险单位序号 --%>
							<td class="centertitle" style="width: 6%">
								<s:text name="regist.prpLregist.kindCode" />
							</td>
							<%-- 险别代码 --%>
							<td class="centertitle" style="width: 12%">
								<s:text name="certainLoss.prpLacciCheck.riskCName" />
							</td>
							<%-- 险种名称 --%>
							<td class="centertitle" style="width: 6%">
								<s:text name="compensate.costCode" />
							</td>
							<%--费用代码  --%>
							<td class="centertitle" style="width: 8%">
								<s:text name="compensate.costName" />
							</td>
							<%--费用名称  --%>
							<td class="centertitle" style="width: 5%">
								<s:text name="compensate.paymentType" />
							</td>
							<%-- 支付类别 --%>
							<td class="centertitle" style="width: 7%">
								<s:text name="quickCase.payObjectCode" />
							</td>
							<%-- 支付对象编码 --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.payNameObject" />
							</td>
							<%-- 支付对象名称 --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="regist.prpLregist.currency" />
							</td>
							<%--币别  --%>
							<td class="centertitle" style="width: 9%">
								<s:text name="commonAcci.compensate.totalAmount" />
							</td>
							<%--  总金额--%>
							<td class="centertitle" style="width: 9%">
								<s:text name="db.prpLafterward.sumRealPay" />
							</td>
							<%-- 计入赔款金额 --%>
							<td class="centertitle" style="width: 9%">
								<s:text name="db.prpLafterward.chargeAmount" />
								<%-- 费用金额 --%>
							</td>
							<td class="centertitle" style="width: 4%">操作</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=12 style="width: 96%">
								<s:text name="prompt.schedule.addRename11" />
							</td>
							<%-- (按"+"号键增加费用信息，按"-"号键删除信息)--%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('Charge',this,'prpLchargeSerialNo')" class="smallbutton" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:if test="${not empty requestScope.prpLcharge.prpLchargeList }">
							<c:forEach var="chargedtox" items="${requestScope.prpLcharge.prpLchargeList}" varStatus="status">
								<tr>
									<input type="hidden" name="prpLchargeFlag" value="${chargedtox.flag}">
									<input type="hidden" name="prpLchargeSerialNo" description="序号" value="${chargedtox.id.serialNo}">
									<td class="input" style="width: 6%">
										<input type=text name="prpLchargeDangerNo" class="codecode" value="${chargedtox.dangerNo}" onClick="viewDangerUnitCompensateCharge(this);" onkeyup="viewDangerUnitCompensateCharge(this);"
											onchange="viewDangerUnitCompensateCharge(this);">
									</td>
									<td class="input" style="width: 6%">
										<input name="prpLchargeKindCode" class="codecode" value="${chargedtox.kindCode}" ondblclick="code_CodeSelect(this, 'policyKindCodeOfPerson','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|');" 
											onchange="code_CodeSelect(this, 'policyKindCodeOfPerson','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|');clearPrpLctextContextInnerHTML();"
											onkeyup="code_CodeSelect(this, 'policyKindCodeOfPerson','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|');">
									</td>
									<td class="input" style="width: 6%">
										<input name="prpLchargeKindName" class="codename" value="${chargedtox.kindName}" ondblclick="code_CodeSelect(this, 'policyKindCodeOfPerson','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|');" 
											onchange="code_CodeSelect(this, 'policyKindCodeOfPerson','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|');clearPrpLctextContextInnerHTML();" 
											onkeyup="code_CodeSelect(this, 'policyKindCodeOfPerson','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|');"
											onblur="setInput(this);">
									</td>
									<td class="input" align="center" style="width: 6%">
										<input name="prpLchargeChargeCode" class="codecode" style="width: 95%" value="${chargedtox.chargeCode}"
											ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);"
											onkeyup="clearPayObject(this);clearPayment(this);setInput(this);checkBeyondQuota(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);"
											onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','0,1','Y','N',fm.prpLcompensateRiskCode.value);" onblur="setInput(this);checkBeyondQuota(this);">
									</td>
									<td class="input" align="center" style="width: 12%">
										<input name="prpLchargeChargeName" class="codename" style="width: 95%" value="${chargedtox.chargeName}"
											ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
											onkeyup="clearPayObject(this);clearPayment(this);setInput(this);checkBeyondQuota(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
											onchange="clearPayObject(this);clearPayment(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" onblur="setInput(this);checkBeyondQuota(this);">
									</td>
									<td class="input" style="width: 5%">
										<c:set var="tempSelectedValue" value="${chargedtox.payObjectType}" />
										<s:select name="prpLchargePayObjectType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.payObjectTypeList" />
									</td>
									<td class="input" style="width: 7%">
										<input name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="${chargedtox.payObjectCode}">
									</td>
									<td class="input" style="width: 10%">
										<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="${chargedtox.payObjectName}" ondblclick="clearPayment(this);getPayObject(this);"
											onchange="clearPayment(this);getPayObject(this);" onkeyup="clearPayment(this);getPayObject(this);" onblur="">
									</td>
									<td class="input" align="center" style="width: 10%">
										<input type="text" name="prpLchargeCurrency" class="readonly" readonly style="width: 30%" value="${chargedtox.currency}">
										<input type="text" name="prpLchargeCurrencyName" class="readonly" readonly style="width: 59%" value="${chargedtox.currencyName}">
									</td>
									<td class="input" style="width: 10%">
										<input name="prpLchargeChargeReport" class="input" value="<fmt:formatNumber value='${chargedtox.chargeReport}' pattern='#'/>" onchange="setInput(this);checkBeyondQuota(this);">
									</td>
									<td class="input" style="width: 12%">
										<input name="prpLchargeSumRealPay" class="input" readOnly="readonly" value="<fmt:formatNumber value='${chargedtox.sumRealPay}' pattern='#'/>" onchange="setInput(this);checkBeyondQuota(this);">
									</td>
									<td class="input" style="width: 9%">
										<input name="prpLchargeChargeAmount" readOnly="readonly" class="input" value="<fmt:formatNumber value='${chargedtox.chargeAmount}' pattern='#'/>">
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow(this,'Charge','prpLchargeSerialNo')" value="-" style="cursor: hand">
											<input type="hidden" name="prpLchargeFlag">
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="12">
										<table class="common" style="width: 100%">
											<tr>
												<td class="input" style="width: 15%">
													<s:text name="compensate.feePayment" />
													：
												</td>
												<!-- 费用支付方式 -->
												<td class="input" style="width: 18%">
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
												<td class="input" style="width: 15%"><s:text name="db.prpCinsured.identifytype"/>：</td><%-- 支付幣別 --%>
												<td class="input" style="width: 85%">
													<s:select name="prpLchargeCurrencyForPayObject" value="#attr.chargedtox.prpLpayObjectInfo.currency" class="common" listKey ="key" listValue="value" list="#request.prpLpayObjectInfoCurrencyList"/>
												</td>
												<td class="input" style="width: 10%"><s:text name="db.prpCinsured.identifytype"/>：</td><%-- 證件類型 --%>
												<td class="input" style="width: 20%">
													<c:set var="tempCertificateCode" value='${chargedtox.prpLpayObjectInfo.certificateCode}' />
													<s:select name="prpLchargeCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
												</td>
												<td class="input" style="width: 10%" />
												<td class="input" style="width: 20%" />
											</tr>
											<tr>
												<td class="input" style="width: 15%"><s:text name='compensate.paymentObject'/>：</td><%--赔付对象--%>
												<td class="right" style="width: 18%">
													<input type="text" class='input' name="prpLchargeOwnerName" value="<c:out value='${chargedtox.prpLpayObjectInfo.ownerName}'/>">
													<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
												</td>
												<td class="input" style="width: 15%"><s:text name='common.compensate.uniformNoOrID'/>：</td><%--统一编号/身份证号--%>
												<td class="right" style="width: 18%">
													<input type="text" class='input' name="prpLchargeUniformNo" value="<c:out value='${chargedtox.prpLpayObjectInfo.uniformNo}'/>">
													<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
												</td>
												<td class="input" style="width: 15%">
													<span name="spanCutBack" style="display: none"><s:text name='common.compensate.cutBack'/></span><%--禁背--%>
												</td>
												<td class="input" style="width: 18%">
													<span name="spanCutBack" style="display: none"> <c:set var="tempSelectedValue"
															value='${chargedtox.prpLpayObjectInfo.cutBack}' /> <s:select name="prpLchargeCutBack" list="#{'1':'是','0':'否'}" listKey="key" listValue="value" value="#attr.tempSelectedValue" />
													</span>
												</td>
											</tr>
											<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
												<td class="input" style="width: 15%"><s:text name='common.compensate.bankCode'/>：</td><%--总行代号--%>
												<td class="input" style="width: 18%">
													<input name="prpLchargeBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankCode}'/>">
												</td>
												<td class="input" style="width: 15%"><s:text name='common.compensate.bankName'/>：</td><%--总行名称--%>
												<td class="input" style="width: 18%">
													<input name="prpLchargeBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankName}'/>">
												</td>
												<td class="input" style="width: 15%"><s:text name='common.compensate.accountCode'/>：</td><%--汇款账号--%>
												<td class="input" style="width: 18%">
													<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.accountCode}'/>">
												</td>
											</tr>
											<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
												<td class="input" style="width: 15%"><s:text name="common.compensate.customBankCode"/>：</td><%--分行代号--%>
												<td class="input" style="width: 18%">
													<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankCode}'/>">
												</td>
												<td class="input" style="width: 15%"><s:text name='common.compensate.customBankName'/>：</td><%--分行名称--%>
												<td class="input" style="width: 18%">
													<input name="prpLchargeCustomBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankName}'/>">
												</td>
												<td class="input" style="width: 34%" colspan="2" align="center">
													<input class='bigbutton' type='button' name='buttonAddAcc' value='<s:text name='button.entryPaymentInfo.value' />' onclick="queryUser(this);" style="width: 180px;">
													<%--輸入费用支付帳户信息 --%>
												</td>
											</tr>
											<tr>
											<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
											<!--  \claim\webapp\claim\pages\commonCargo\compensate\CargoCompensateChargeEdit.jsp 2-->
												<td class="input" style="width: 15%"><s:text name='common.compensate.areaCode'/>：</td><%--邮递区号--%>
												<td class="right" style="width: 18%">
													<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
													<input type="text" class='input' name="prpLchargeAreaCode" value="<c:out value='${chargedtox.prpLpayObjectInfo.areaCode}'/>" maxlength="3">
													<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
												</td>
												<td class="input" style="width: 15%"><s:text name='common.compensate.courierAddress'/>：</td><%--邮递地址--%>
												<td class="right" style="width: 52%" colspan="3">
													<input type="text" class='input' name="prpLchargeCourierAddress" value="<c:out value='${chargedtox.prpLpayObjectInfo.courierAddress}'/>">
													<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
												</td>
											</tr>
										</table>
									</td>
									<td class="title"></td>
								</tr>
								<tr height="2" bgcolor="block">
									<td colspan="13"></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
