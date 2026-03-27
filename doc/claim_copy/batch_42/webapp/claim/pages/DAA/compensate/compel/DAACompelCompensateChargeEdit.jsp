<%--
****************************************************************************
* DESC       ：添加费用赔款信息页面
* AUTHOR     ：理赔组 陈杰
* CREATEDATE ： 2013-03-14
* MODIFYLIST ：   Name       Date            Reason/Contents
* 
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<!--建立显示的輸入条，可以收缩显示的-->
<span style="display: none" id="Charge_Data">
	<table class="common" cellspacing="1" cellpadding="0" name="chargeObject">
		<tbody>
			<tr>
				<td class="input" style="width: 8%">
					<input type="hidden" name="prpLchargeFlag">
					<input type="hidden" name="prpLchargeSerialNo" description="序号">
					<input name="prpLchargeKindCode" class="codecode" style="width: 100%" maxlength="3"
						ondblclick="clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
						onchange="clearPaymentNew(this);code_CodeChange(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
						onkeyup="clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
				</td>
				<td class="input" style="width: 25%">
					<input name="prpLchargeKindName" class="codename"
						ondblclick="clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
						onchange="clearPaymentNew(this);code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
						onkeyup="clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
				</td>
				<td class="input" style="width: 15%">
					<input type="hidden" name="prpLchargeChargeCode" class="readonly" style="width: 100%"
						<s:if test='#attr.chargeType=="D"'>
							value = "D" readonly = true
						</s:if>
						<s:else>
							 ondblclick="clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode1','0,1','Y');"
							 onchange="clearPaymentNew(this);code_CodeChange(this, 'ChargeCode1','0,1','Y'); " onkeyup="clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode1','0,1','Y'); "
						</s:else>
					>
					<input name="prpLchargeChargeName" class="codename" style="width: 100%"
						<s:if test='#attr.chargeType=="D"'>
							value = "延遲利息" readonly = true
						</s:if>
						<s:else>
							ondblclick="clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N','B01');"
							onchange="clearPaymentNew(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N','B01');" onkeyup="clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N','B01');"
						</s:else>
					>
				</td>
				<td class="input" style="width: 11%">
					<s:select name="prpLchargePayObjectType" listKey="key" listValue="value" list="#request.payObjectTypeList" style="width:70px" />
				</td>
				<input type="hidden" name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="">
				<td class="input" style="width: 15%">
					<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="" ondblclick="code_CodeSelect(this, 'payObject','-1,0','Y','N');"
						onchange="code_CodeChange(this, 'payObject','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'payObject','-1,0','Y','N');">
				</td>
				<td class="input" style="width: 15%">
					<input type="hidden" name="prpLchargeCurrency" class="readonly" style="width: 100%" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
					<input name="prpLchargeChargeReport" class="input" style="width: 100%" onfocus="cacheData(this);" onchange="validateMoney(this);setRealPayNew(this);" title="費用金額">
				</td>
				<td class="input" style="width: 10%">
					<input name="prpLchargeChargeAmount" class="input" style="width: 100%" onfocus="cacheData(this);" onchange="validateMoney(this);setRealPayNew(this);" title="實際費用">
					<input name="prpLchargeSumRealPay" type="hidden" style="width: 100%" class='readonly' readonly>
				</td>
				<td class="input" align="center" style="width: 4%">
					<div>
						<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteChargeObject(this);" value="-" style="cursor: hand">
						<input type="hidden" name="prpLchargeFlag">
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="8">
					<table class="common" style="width: 100%">
						<tr>
							<td class="input" style="width: 15%">費用支付方式：</td>
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
							<td class="input" style="width: 15%">證件類型：</td>
							<td class="input" style="width: 18%">
								<s:select name="prpLchargeCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
							</td>
							<td class="input" style="width: 10%">洗錢狀態回覆：</td>
							<td class="input" style="width: 20%">
								<input name="prpLchargeAMLFlag" readOnly="readonly" class="readonly" >
							</td>
						</tr>
						<tr>
							<td class="input" style="width: 15%">
								<s:text name="compensate.paymentObject" />：
								<%-- 賠付對象 --%>
							</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeOwnerName" maxlength="50" class="input">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="input" style="width: 15%">統一編號/身份證號：</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeUniformNo" class="input">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="input" style="width: 15%">
								<span name="spanCutBack" style="display: none">禁背：</span>
							</td>
							<td class="input" style="width: 18%">
								<span name="spanCutBack" style="display: none">
									<select name="prpLchargeCutBack">
										<option value="0">否</option>
										<option value="1" selected="selected">是</option>
									</select>
								</span>
							</td>
						</tr>
						<tr name="bankInfo">
							<td class="input" style="width: 15%">總行代號：</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeBankCode" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 15%">總行名稱：</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeBankName" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 15%">匯款帳號：</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly">
							</td>
						</tr>
						<tr name="bankInfo">
							<td class="input" style="width: 15%">分行代號：</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 15%">分行名稱：</td>
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
						<!--  \claim\webapp\claim\pages\DAA\compensate\compel\DAACompelCompensateChargeEdit.jsp -->
							<td class="input" style="width: 15%">郵遞區號：</td>
							<td class="input" style="width: 18%">
								<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
								<input name="prpLchargeAreaCode" class="input" maxlength="3">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="input" style="width: 15%">郵遞地址：</td>
							<td class="input" style="width: 52%" colspan="3">
								<input name="prpLchargeCourierAddress" class="input">
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
</span>
<table class="common" align="center">
	<tr>
		<td class="common" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="ChargeImg" onclick="showPage(this,spanCharge);"> <b><s:text name="compensate.feePaymentInfo" /></b><br>
			<%-- 费用赔款信息 --%>
			<span id="spanCharge" style="display: none" cellspacing="1" cellpadding="0">
				<table class="common" cellpadding="5" cellspacing="1">
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
							<td class="centertitle" style="width: 15%">費用金額</td>
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
							<td class="title" colspan="7">
								<s:text name="prompt.compensate.addRemove" />
							</td>
							<%-- (按"+"号键增加费用赔款信息，按"-"号键删除信息) --%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertChargeObject();" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<tr>
							<td id="PrpLcharge" class="title" colspan=8>
								<c:forEach items="${requestScope.prpLcharge.prpLchargeList}" var="chargedtox">
									<table class="common" cellspacing="1" cellpadding="0" name="chargeObject">
										<tbody>
											<tr>
												<td class="input" style="width: 8%">
													<input type="hidden" name="prpLchargeFlag" value="<c:out value='${chargedtox.flag}'/>">
													<input type="hidden" name="prpLchargeSerialNo" description="序号" value="<c:out value='${chargedtox.id.serialNo}'/>">
													<input name="prpLchargeKindCode" class="codecode" style="width: 100%" maxlength="3" value="<c:out value='${chargedtox.kindCode}'/>"
														ondblclick="clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onchange="clearPaymentNew(this);code_CodeChange(this,'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onkeyup="clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','0,1','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
												</td>
												<td class="input" style="width: 25%">
													<input name="prpLchargeKindName" class="codename" value="<c:out value='${chargedtox.kindName}'/>"
														ondblclick="clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onchange="clearPaymentNew(this);code_CodeChange(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
														onkeyup="clearPaymentNew(this);code_CodeSelect(this, 'PolicyKindCode','-1,0','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
												</td>
												<td class="input" style="width: 15%;">
													<input type="hidden" name="prpLchargeChargeCode" class="readonly" style="width: 100%" value="<c:out value='${chargedtox.chargeCode}'/>"
													<s:if test='#attr.chargeType=="D"'>
														readonly = true
													</s:if>
													<s:else>
														ondblclick="clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode1','0,1','Y');" onchange="clearPaymentNew(this);code_CodeChange(this, 'ChargeCode1','0,1','Y');"
														onkeyup="clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode1','0,1','Y');"
													</s:else>
													>
													<input name="prpLchargeChargeName" class="codename" style="width: 100%" value="<c:out value='${chargedtox.chargeName}'/>"
													<s:if test='#attr.chargeType=="D"'>
														readonly = true
													</s:if>
													<s:else>
														ondblclick="clearPaymentNew(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N','B01');" onchange="clearPaymentNew(this);code_CodeChange(this, 'ChargeCode','-1,0','Y','N','B01');"
														onkeyup="clearPaymentNew(this);code_CodeSelect(this,'ChargeCode','-1,0','Y','N','B01');"
													</s:else>
													>
												</td>
												<td class="input" style="width: 11%">
													<c:set var="tempSelectedValue" value="${chargedtox.payObjectType}" />
													<s:select name="prpLchargePayObjectType" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.payObjectTypeList" style="width:70px" />
												</td>
												<td class="input" style="width: 15%">
													<input type="hidden" name="prpLchargePayObjectCode" class="readonly" readonly style="width: 100%" value="<c:out value='${chargedtox.payObjectCode}'/>">
													<input name="prpLchargePayObjectName" class="codename" style="width: 100%" value="<c:out value='${chargedtox.payObjectName}'/>"
														ondblclick="code_CodeSelect(this, 'payObject','-1,0','Y','N');" onchange="code_CodeChange(this, 'payObject','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'payObject','-1,0','Y','N');">
												</td>
												<td class="input" style="width: 15%">
													<input type="hidden" name="prpLchargeCurrency" class="readonly" style="width: 100%" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
													<input name="prpLchargeChargeReport" class="input" style="width: 100%" value="<fmt:formatNumber value="${chargedtox.chargeReport}" pattern="#"/>" onfocus="cacheData(this);"
														onchange="validateMoney(this);setRealPayNew(this);" title="費用金額">
												</td>
												<td class="input" style="width: 10%">
													<input name="prpLchargeChargeAmount" class="input" style="width: 100%" value="<fmt:formatNumber value="${chargedtox.chargeAmount}" pattern="#"/>" onfocus="cacheData(this);"
														onchange="validateMoney(this);setRealPayNew(this);" title="實際費用">
													<input name="prpLchargeSumRealPay" type="hidden" class='readonly' readonly style="width: 100%" value="<c:out value='${chargedtox.sumRealPay}'/>">
												</td>
												<td class="input" style='width: 4%' align="center">
													<div>
														<input type=button name="buttonChargeDelete" class=smallbutton onclick="deleteChargeObject(this);" value="-" style="cursor: hand">
														<input type="hidden" name="prpLchargeFlag">
													</div>
												</td>
											</tr>
											<tr>
												<td colspan="8">
													<table class="common" style="width: 100%">
														<tr>
															<td class="input" style="width: 15%">
																<s:text name="compensate.feePayment" />：
															</td>
															<!-- 费用支付方式 -->
															<td class="input" style="width: 18%">
																<select name="prpLchargeOwnerShip" onchange="chargeOwnerShipChange(this);">
																	<option value="B" <c:if test="${pageScope.chargedtox.ownerShip=='B'}"><c:out value="selected"/></c:if>>
																		<s:text name="compensate.remittance" />
																	</option>
																	<!-- 汇款 -->
																	<option value="Q" <c:if test="${pageScope.chargedtox.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
																		<s:text name="compensate.agentInfo.cheque" />
																	</option>
																	<!-- 支票 -->
																</select>
															</td>
															<td class="input" style="width: 15%">證件類型：</td>
															<td class="input" style="width: 18%">
																<c:set var="tempCertificateCode" value='${chargedtox.prpLpayObjectInfo.certificateCode}' />
																<s:select name="prpLchargeCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
															</td>
															<td class="input" style="width: 10%">洗錢狀態回覆：</td>
															<td class="input" style="width: 20%">
																<input name="prpLchargeAMLFlag" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.amlFlag}'/>">
															</td>
														</tr>
														<tr>
															<td class="input" style="width: 15%">賠付對象：</td>
															<td class="right" style="width: 18%">
																<input type="text" class='input' name="prpLchargeOwnerName" maxlength="50" value="<c:out value='${chargedtox.prpLpayObjectInfo.ownerName}'/>">
																<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
															</td>
															<td class="input" style="width: 15%">統一編號/身份證號：</td>
															<td class="right" style="width: 18%">
																<input type="text" class='input' name="prpLchargeUniformNo" value="<c:out value='${chargedtox.prpLpayObjectInfo.uniformNo}'/>">
																<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
															</td>
															<td class="input" style="width: 15%">
																<span name="spanCutBack" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}"> style="display:none" </c:if> >禁背：</span>
															</td>
															<td class="input" style="width: 18%">
																<span name="spanCutBack" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}"> style="display:none" </c:if>>
																	<select name="prpLchargeCutBack">
																		<option value="0" <c:if test="${chargedtox.prpLpayObjectInfo.cutBack == '0'}">selected</c:if>>
																			<s:text name="否" />
																		</option>
																		<option value="1" <c:if test="${chargedtox.prpLpayObjectInfo.cutBack == '1'}">selected</c:if>>
																			<s:text name="是" />
																		</option>
																	</select>
																</span>
															</td>
														</tr>
														<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
															<td class="input" style="width: 15%">總行代號：</td>
															<td class="input" style="width: 18%">
																<input name="prpLchargeBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankCode}'/>">
															</td>
															<td class="input" style="width: 15%">总行名稱：</td>
															<td class="input" style="width: 18%">
																<input name="prpLchargeBankName" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.bankName}'/>">
															</td>
															<td class="input" style="width: 15%">匯款帳號：</td>
															<td class="input" style="width: 18%">
																<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.accountCode}'/>">
															</td>
														</tr>
														<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
															<td class="input" style="width: 15%">分行代號：</td>
															<td class="input" style="width: 18%">
																<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly" value="<c:out value='${chargedtox.prpLpayObjectInfo.customBankCode}'/>">
															</td>
															<td class="input" style="width: 15%">分行名稱：</td>
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
														<!--  \claim\webapp\claim\pages\DAA\compensate\compel\DAACompelCompensateChargeEdit.jsp 2-->
															<td class="input" style="width: 15%">郵遞區號：</td>
															<td class="right" style="width: 18%">
																<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
																<input type="text" class='input' name="prpLchargeAreaCode" maxlength="3" value="<c:out value='${chargedtox.prpLpayObjectInfo.areaCode}'/>">
																<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
															</td>
															<td class="input" style="width: 15%">郵遞地址：</td>
															<td class="right" style="width: 52%" colspan="3">
																<input type="text" class='input' name="prpLchargeCourierAddress" value="<c:out value='${chargedtox.prpLpayObjectInfo.courierAddress}'/>">
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
							</td>
						</tr>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td class="left" style="width: 15%">獨立處理費用：
		</td>
		<td class="right" style="width: 85%">
			<input type="hidden" name="chargeType" value="${chargeType }"><%-- 延迟利息 --%>
			<input type="text" class="input" maxlength="10" style="width: 80px" name="prpLcompensateIndependentCosts"
				value="<fmt:formatNumber value='${requestScope.prpLcompensate.independentCosts}' pattern='#'/>" onfocus="cacheData(this);" onchange="validateMoney(this);calSumDutyPaid();" title="獨立處理費用">
		</td>
	</tr>
</table>