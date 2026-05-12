<%@include file="/common/taglibs.jsp"%>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<%
	/**被 + 的輸入域*/
%>
<span style="display: none" id="PrpLpayObjectInfo_Data">
	<table style="width: 100%" class="common">
		<tbody>
			<tr name="PrpLpayObjectInfo">
				<td class="subformtitle" style="width: 96%">
					<table class="common" style="width: 100%">
						<tr>
							<td class="input" colspan="6">
								<b>賠付對象&nbsp;<span name="payObjectIndex"> </span>：
								</b>
								<input type="hidden" name="prpLpayObjectInfoSerialNo" value="">
								<div name="payObject" style="display: none">
									<font color="red">請注意: 若賠付對象為法人者, 請在“統一編號” 欄位輸入該公司之八碼統一編號, 若賠付對象為個人者, 請在“統一編號” 欄位輸入該人員之十碼個人身份證字號</font>
								</div>
							</td>
						</tr>
						<tr>
							<td class="input" style="width: 15%">賠款支付方式：</td>
							<td class="input" style="width: 18%">
								<select name="prpLpayObjectInfoOwnerShip" onchange="payObjectInfoOwnerShip(this)">
									<option value="B" selected="selected">
										<s:text name="compensate.remittance" />
									</option>
									<!-- 汇款 -->
									<option value="Q">
										<s:text name="compensate.agentInfo.cheque" />
									</option>
									<!-- 支票 -->
								</select>
							</td>
							<td class="input" style="width: 15%">理賠金額C1：</td>
							<!-- 费用支付方式 -->
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoPayAmount" readonly type="text" class="readonly" maxlength="8" style="width: 80px" value="0" onfocus="cacheData(this);" onblur="validateMoney(this);" title="理賠金額">
								<img src="${ctx}/images/bgMarkMustInput.jpg">
							</td>
							<td class="input" style="width: 15%">洗錢狀態回覆：</td>
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoAMLFlag" readonly type="text" class="readonly" maxlength="8" style="width: 80px" value="" >
							</td>
						</tr>
						<tr>
							<td class="input" style="width: 15%">賠付對象：</td>
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoOwnerName" class="input" maxlength="50" value="">
								<img src="${ctx}/images/bgMarkMustInput.jpg">
							</td>
							<td class="input" style="width: 15%">費用類型：</td>
							<td class="input" style="width: 18%">
								<s:select name="prpLpayObjectInfoPaymentKind" list="#request.paymentKindList" listKey="key" listValue="value" headerKey="" headerValue=""></s:select>
								<img src="${ctx}/images/bgMarkMustInput.jpg">
							</td>
							<td class="input" style="width: 15%">證件類型：</td>
							<td class="input" style="width: 18%">
								<s:select name="prpLpayObjectInfoCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
							</td>
						</tr>
						<tr>
							<td class="input" style="width: 15%">統一編號/身份證號：</td>
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoUniformNo" class="input" maxlength="25" value="">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="input" style="width: 15%">受款人電話：</td>
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoBeneficiaryPhone" class="input" maxlength="20" value="">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="input" style="width: 15%">
								<span name="spanCutBack" style="display: none">禁背：</span>
							</td>
							<td class="input" style="width: 18%">
								<span name="spanCutBack" style="display: none"><s:select name="prpLpayObjectInfoCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="1"/></span>
							</td>
						</tr>
						<tr name="bankInfo">
							<td class="input" style="width: 15%">總行代號：</td>
							<%-- 總行代號 --%>
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoBankCode" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 15%">總行名稱：</td>
							<%-- 總行名稱 --%>
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoBankName" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 15%">匯款帳號：</td>
							<%-- 银行帳号 --%>
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoAccountCode" readOnly="readonly" class="readonly">
							</td>
						</tr>
						<tr name="bankInfo">
							<td class="input" style="width: 15%">分行代號：</td>
							<%-- 分行代號 --%>
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoCustomBankCode" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 15%">分行名稱：</td>
							<%-- 分行名稱 --%>
							<td class="input" style="width: 18%">
								<input name="prpLpayObjectInfoCustomBankName" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 33%" colspan="2">
								<input class='bigbutton' type='button' name='buttonAddPrpLpayObjectInfo' value="<s:text name='button.entryPaymentInfo.value' />" onclick="queryUserNew(this);">
							</td>
							<%-- 录入费用支付帳户信息 --%>
						</tr>
						<tr>
							<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
							<!-- \webapp\claim\pages\DAA\compensate\DAACompensatePayObject.jsp -->
							<td class="input" style="width: 15%">郵遞區號：</td>
							<td class="input" style="width: 18%">
								<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
								<input name="prpLpayObjectInfoAreaCode" class="input" maxlength="3">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="input" style="width: 15%">郵遞地址：</td>
							<td class="input" style="width: 51%" colspan="3">
								<input name="prpLpayObjectInfoCourierAddress" class="input" maxlength="50" value="">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
						</tr>
					</table>
				</td>
				<td class="input" style="width: 4%;">
					<div>
						<input type=button name="buttonPayAccountInfoDelete" class="smallbutton" onclick="deletePrpLpayObjectInfo(this);" value="-" style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<table class="common" align="center">
	<tr>
		<td class="common">
			<img style="cursor: hand;" src="${ctx}/images/butCollapseBlue.gif" name="PayAccountInfoImg" onclick="showPage(this,spanPayAccountInfo);"><b>賠款給付對象訊息</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<s:set var="prpLpayObjectInfoPaycodeType" value="" scope="page" />
			<s:if test="#attr.prpLpayObjectInfo.prpLpayObjectInfoList!=null&&#attr.prpLpayObjectInfo.prpLpayObjectInfoList.size()>0">
				<s:set var="prpLpayObjectInfoPaycodeType" value="#attr.prpLpayObjectInfo.prpLpayObjectInfoList.get(0).paycodeType" scope="page" />
			</s:if>
			賠付代號（賠案）： <select name="prpLpayObjectInfoPaycodeType" style="width: 100px">
				<option value="1" <s:if test="#attr.prpLpayObjectInfoPaycodeType==1">selected="selected"</s:if>>一般賠案</option>
				<option value="2" <s:if test="#attr.prpLpayObjectInfoPaycodeType==2">selected="selected"</s:if>>同業</option>
				<option value="3" <s:if test="#attr.prpLpayObjectInfoPaycodeType==3">selected="selected"</s:if>>健保局</option>
			</select>
			<span id="spanPayAccountInfo" style="display: none">
				<table class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="centertitle" colspan=2>賠付對象訊息</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" style="width: 96%">(按"+"號鍵增加賠付對象訊息，按"-"號鍵刪除賠付對象訊息)</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertPrpLpayObjectInfo();" name="buttonPayAccountInfoInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody id="PayAccountInfo">
						<c:forEach items="${requestScope.prpLpayObjectInfo.prpLpayObjectInfoList}" var="prpLpayObject" varStatus="stat">
							<tr name="PrpLpayObjectInfo">
								<td class="subformtitle" style="width: 96%">
									<table class="common" style="width: 100%">
										<tr>
											<td class="input" colspan="6">
												<b>賠付對象&nbsp;<span name="payObjectIndex"><c:out value="${prpLpayObject.id.serialNo}" /></span></b>
												<input type="hidden" name="prpLpayObjectInfoSerialNo" value="<c:out value="${prpLpayObject.id.serialNo}"/>">
												<c:if test="${stat.index==0}">
													<div name="payObject">
														<font color="red">請注意: 若賠付對象為法人者, 請在“統一編號” 欄位輸入該公司之八碼統一編號, 若賠付對象為個人者, 請在“統一編號” 欄位輸入該人員之十碼個人身份證字號</font>
													</div>
												</c:if>
											</td>
										</tr>
										<tr>
											<td class="input" style="width: 15%">賠款支付方式：：</td>
											<td class="input" style="width: 18%">
												<select name="prpLpayObjectInfoOwnerShip" onchange="payObjectInfoOwnerShip(this)">
													<option value="B" <c:if test="${pageScope.prpLpayObject.ownerShip=='B'}"><c:out value="selected"/></c:if>>
														<s:text name="compensate.remittance" />
													</option>
													<!-- 汇款 -->
													<option value="Q" <c:if test="${pageScope.prpLpayObject.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
														<s:text name="compensate.agentInfo.cheque" />
													</option>
													<!-- 支票 -->
													<!--<option value="C" <c:if test="${pageScope.chargedtox.ownerShip=='C'}"><c:out value="selected"/></c:if> ><s:text name="compensate.agentInfo.cash"/></option> -->
													<!-- 现金 -->
												</select>
											</td>
											<td class="input" style="width: 15%">理賠金額C2：</td>
											<!-- 费用支付方式 -->
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoPayAmount" type="text" readonly class="readonly" maxlength="8" style="width: 80px" value="<fmt:formatNumber value="${pageScope.prpLpayObject.payAmount}" pattern="#"/>"
													onfocus="cacheData(this);" onblur="validateMoney(this);" title="理賠金額">
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="input" style="width: 15%">洗錢狀態回覆：</td>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoAMLFlag" readonly type="text" class="readonly" maxlength="8" style="width: 80px" value="<c:out value="${prpLpayObject.amlFlag}"/>">
											</td>
										</tr>
										<tr>
											<td class="input" style="width: 15%">賠付對象：</td>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoOwnerName" class="input" maxlength="50" value="<c:out value="${prpLpayObject.ownerName}"/>">
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="input" style="width: 15%">費用類型：</td>
											<td class="input" style="width: 18%">
												<c:set var="tempSelectedValue" value='${prpLpayObject.paymentKind}' />
												<s:select name="prpLpayObjectInfoPaymentKind" list="#request.paymentKindList" listKey="key" listValue="value" value="#attr.tempSelectedValue"></s:select>
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="input" style="width: 15%">證件類型：</td>
											<td class="input" style="width: 18%">
												<c:set var="tempCertificateCode" value='${prpLpayObject.certificateCode}' />
												<s:select name="prpLpayObjectInfoCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
											</td>
										</tr>
										<tr>
											<td class="input" style="width: 15%">統一編號/身份證號：</td>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoUniformNo" class="input" maxlength="25" value="<c:out value="${prpLpayObject.uniformNo}"/>">
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
											<td class="input" style="width: 15%">受款人電話：</td>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoBeneficiaryPhone" class="input" maxlength="20" value="<c:out value="${prpLpayObject.beneficiaryPhone}"/>">
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
											<td class="input" style="width: 15%">
												<span name="spanCutBack" <c:if test="${pageScope.prpLpayObject.ownerShip!='Q'}"> style="display: none;" </c:if>>禁背：</span>
											</td>
											<td class="input" style="width: 18%">
												<span name="spanCutBack" <c:if test="${pageScope.prpLpayObject.ownerShip!='Q'}"> style="display: none;" </c:if>> <c:set var="tempSelectedValue" value='${prpLpayObject.cutBack}' />
													<s:select name="prpLpayObjectInfoCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="#attr.tempSelectedValue" /></span>
											</td>
										</tr>
										<tr name="bankInfo" <c:if test="${pageScope.prpLpayObject.ownerShip!='B'}">style="display: none;"</c:if>>
											<td class="input" style="width: 15%">總行代號：</td>
											<%-- 總行代號 --%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoBankCode" readOnly="readonly" class="readonly" value="<c:out value="${prpLpayObject.bankCode}"/>">
											</td>
											<td class="input" style="width: 15%">總行名稱：</td>
											<%-- 總行名稱 --%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoBankName" readOnly="readonly" class="readonly" value="<c:out value="${prpLpayObject.bankName}"/>">
											</td>
											<td class="input" style="width: 15%">匯款帳號：</td>
											<%-- 银行帳号 --%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoAccountCode" readOnly="readonly" class="readonly" value="<c:out value="${prpLpayObject.accountCode}"/>">
											</td>
										</tr>
										<tr name="bankInfo" <c:if test="${pageScope.prpLpayObject.ownerShip!='B'}">style="display: none;"</c:if>>
											<td class="input" style="width: 15%">分行代號：</td>
											<%-- 分行代號 --%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoCustomBankCode" readOnly="readonly" class="readonly" value="<c:out value="${prpLpayObject.customBankCode}"/>">
											</td>
											<td class="input" style="width: 15%">分行名稱：</td>
											<%-- 分行名稱 --%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoCustomBankName" readOnly="readonly" class="readonly" value="<c:out value="${prpLpayObject.customBankName}"/>">
											</td>
											<td class="input" style="width: 33%" colspan="2">
												<input class='bigbutton' type='button' name='buttonAddPrpLpayObjectInfo' value="<s:text name='button.entryPaymentInfo.value' />" onclick="queryUserNew(this);">
											</td>
											<%-- 录入费用支付帳户信息 --%>
										</tr>
										<tr>
											<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
											<!-- \webapp\claim\pages\DAA\compensate\DAACompensatePayObject.jsp -->
											<td class="input" style="width: 15%">郵遞區號：</td>
											<td class="input" style="width: 18%">
												<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
												<input name="prpLpayObjectInfoAreaCode" class="input" maxlength="3" value="<c:out value="${prpLpayObject.areaCode}"/>">
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
											<td class="input" style="width: 15%">郵遞地址：</td>
											<td class="input" style="width: 51%" colspan="3">
												<input name="prpLpayObjectInfoCourierAddress" class="input" maxlength="50" value="<c:out value="${prpLpayObject.courierAddress}"/>">
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
										</tr>
									</table>
								</td>
								<td class="input" style="width: 4%;">
									<div>
										<input type=button name="buttonPayAccountInfoDelete" class="smallbutton" onclick="deletePrpLpayObjectInfo(this);" value="-" style="cursor: hand">
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
