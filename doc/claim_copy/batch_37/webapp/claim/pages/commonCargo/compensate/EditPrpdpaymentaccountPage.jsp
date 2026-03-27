<%@	page contentType="text/html; charset=GBK" language="java"%>
<%@include file="/common/taglibs.jsp"%>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<table class="common" style="width: 100%" id="PrpLpayObjectInfo_Data" style="display:none">
	<tbody>
		<tr name="PrpLpayObjectInfo">
			<td class="subformtitle" style="width: 96%">
				<table class="common" style="width: 100%">
					<tr>
						<td class="input" colspan="6">
							<b><s:text name="compensate.paymentObject"/>&nbsp;<span name="payObjectIndex"> </span></b><%--赔付对象--%>
							<input type="hidden" name="prpLpayObjectInfoSerialNo" value="">
							<div name="payObject" style="display: none">
								<font color="red"><s:text name="prompt.compensate.message2"/></font>
								<%--請注意: 若賠付對象為法人者, 請在“統一編號” 欄位輸入該公司之八碼統一編號, 若賠付對象為個人者, 請在“統一編號” 欄位輸入該人員之十碼個人身份證字號--%>
							</div>
						</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%"><s:text name="common.compensate.claimPayment"/>：</td><%--赔款支付方式--%>
						<!-- 标的损失赔款支付方式 -->
						<td class="input" style="width: 18%">
							<select name="prpLpayObjectInfoOwnerShip" style="width: 50%" onchange="payObjectOwnerShipChange(this);">
								<option value="B" selected="selected">
									<s:text name="compensate.remittance" />
								</option>
								<!-- 汇款 -->
								<!--<option value="C">
									<s:text name="compensate.agentInfo.cash" />
								</option>-->
								<!-- 现金 -->
								<option value="Q">
									<s:text name="compensate.agentInfo.cheque" />
								</option>
								<!-- 支票 -->
							</select>
						</td>
						<td class="title" style="width: 15%"><s:text name="common.compensate.payAmount"/>：</td>
						<%--理賠金額 --%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoPayAmount" class="readonly" readonly onfocus="cacheData(this);" value="0" onchange="validateMoney(this);" title="<s:text name='common.compensate.payAmount'/>" style="width: 80px;" />
							<img src="${ctx}/images/bgMarkMustInput.jpg">
						</td>
						<td class="title" style="width:15%">支付幣別：</td>
						<td class="input" style="width:18%">
							<s:select name="prpLpayObjectInfoCurrency" class="common" listKey ="key" listValue="value" list="#request.prpLpayObjectInfoCurrencyList"/>
						</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%"><s:text name="compensate.paymentObject"/>：</td><%--赔付对象--%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoOwnerName" class="input" maxlength="120" value="${requestScope.prpLpayObjectInfo.ownerName}">
							<img src="${ctx}/images/bgMarkMustInput.jpg">
						</td>
						<td class="title" style="width: 15%"><s:text name="claim.costType"/>：</td><%--费用类型--%>
						<td class="input" style="width: 18%">
							<select name="prpLpayObjectInfoPaymentKind" style="width: 50%" onchange="changePaymentKind(this);">
								<option value="1"><s:text name="common.compensate.garage"/></option><%--修車廠--%>
								<option value="2"><s:text name="common.compensate.materialSupplier"/></option><%--材料商--%>
								<option value="3"><s:text name="common.compensate.companyNo"/></option><%--公司行號--%>
								<option value="4" selected="selected"><s:text name="common.compensate.personal"/></option><%--個人--%>
								<option value="5"><s:text name="common.compensate.notaryCorporation"/></option><%--公證公司--%>
								<option value="6"><s:text name="common.compensate.NHI"/></option><%--健保局--%>
							</select> <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
						</td>
						<td class="title" style="width: 15%"><s:text name="db.prpCinsured.identifytype"/>：</td><%-- 證件類型 --%>
						<td class="input" style="width: 18%">
							<s:select name="prpLpayObjectInfoCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
						</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<span name="InfoUniformNo1"><s:text name="common.compensate.uniformNoOrID"/>：</span> <span name="InfoUniformNo2" style="display: none"><s:text name="common.compensate.personalIDNumber"/>：</span>
						</td><%--統一編號/身份證號--%><%--個人身份證號--%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoUniformNo" class="input" value="" />
							<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
						</td>
						<td class="title" style="width: 15%">
							<span name="BeneficiaryPhone1"><s:text name="common.compensate.payeePhone"/>：</span><span name="BeneficiaryPhone2" style="display: none"><s:text name="common.compensate.localCalls"/>：</span>
						</td><%--受款人電話--%><%--市內電話--%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoBeneficiaryPhone" class="input" value="" />
							<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" name="BeneficiaryPhoneIMG" />
						</td>
						<td class="title" style="width: 15%">
							<span name="spanCutBack" style="display: none;"><s:text name="common.compensate.cutBack"/>：</span><%--禁背--%>
						</td>
						<td class="input" style="width: 18%">
							<span name="spanCutBack" style="display: none"><s:select name="prpLpayObjectInfoCutBack" list="#{'1':'是','0':'否'}" listKey="key" listValue="value" /></span>
						</td>
					</tr>
					<tr name="bankInfo">
						<td class="title" style="width: 15%"><s:text name="common.compensate.bankCode"/>：</td><%--总行代号--%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoBankCode" value="" readOnly="readonly" class="readonly" />
						</td>
						<td class="title" style="width: 15%"><s:text name="common.compensate.bankName"/>：</td><%--总行名称--%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoBankName" value="" readOnly="readonly" class="readonly">
						</td>
						<td class="title" style="width: 15%"><s:text name="common.compensate.accountCode"/>：</td><%--汇款账号--%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoAccountCode" value="" readOnly="readonly" class="readonly">
						</td>
					</tr>
					<tr name="bankInfo">
						<td class="title" style="width: 15%"><s:text name="common.compensate.customBankCode"/>：</td><%--分行代号--%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoCustomBankCode" value="" readOnly="readonly" class="readonly">
						</td>
						<td class="title" style="width: 15%"><s:text name="common.compensate.customBankName"/>：</td><%--分行名称--%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoCustomBankName" value="" readOnly="readonly" class="readonly">
						</td>
						<td class="title" style="width: 33%" colspan="2" align="center">
							<!-- 录入赔款支付帳户信息 -->
							<input class='bigbutton' type='button' name='buttonAccCompensate' style="width: 180px;" value='<s:text name="button.inputPaymentInformation.value" />' onclick="queryUserCompensate(this);">
						</td>
					</tr>
					<tr name="AreaInfo">
						<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
						<!--  \webapp\claim\pages\commonCargo\compensate\EditPrpdpaymentaccountPage.jsp -->
						<td class="title" style="width: 15%"><s:text name="common.compensate.areaCode"/>：</td><%--邮递区号--%>
						<td class="input" style="width: 18%">
							<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
							<input name="prpLpayObjectInfoAreaCode" class="input" maxlength="3">
							<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
						</td>
						<td class="title" style="width: 15%"><s:text name="common.compensate.courierAddress"/>：</td><%--邮递地址--%>
						<td class="input" style="width: 50%" colspan="3">
							<input name="prpLpayObjectInfoCourierAddress" class="input">
							<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
						</td>
					</tr>
					<tr name="PayDate" style="display: none">
						<td class="title" style="width: 15%"><s:text name="common.compensate.paymentDate"/>：</td><%--付款日期--%>
						<td class="input" style="width: 18%">
							<rc:rcDate name="prpLpayObjectInfoPayDate" class="input" />
						</td>
						<td class="title" style="width: 15%"><s:text name="regist.mobilePhones"/>：</td><%--行动电话--%>
						<td class="input" style="width: 18%">
							<input name="prpLpayObjectInfoMobilePhoneNo" class="input">
						</td>
						<td class="title" style="width: 15%"></td>
						<td class="input" style="width: 18%"></td>
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
<table class="common" align="center" style="width: 100%">
	<tr>
		<td class="common">
			<img style="cursor: hand;" src="${ctx}/images/butCollapseBlue.gif" <%--賠款給付對象資訊--%> name="ChargeImg" onclick="showPage(this,spanPayAccountInfo);"> <b><s:text name="common.compensate.paymentinfo"/></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<s:set var="prpLpayObjectInfoPaycodeType" value="" scope="page" />
			<c:if test="${not empty requestScope.prpLpayObjectInfo.prpLpayObjectInfoList}">
			   <s:set var="prpLpayObjectInfoPaycodeType" value="#request.prpLpayObjectInfo.prpLpayObjectInfoList.get(0).paycodeType" scope="page" />
			</c:if>
			<s:text name="common.compensate.payoutCode"/><select name="prpLpayObjectInfoPaycodeType" style="width: 100px" onchange="setPrpLpayObjectInfoPaycodeType(this);"><%--赔付代号（赔案）--%>
				<option value="1" <s:if test="#attr.prpLpayObjectInfoPaycodeType==1">selected="selected"</s:if>><s:text name="common.compensate.pei"/></option><%--一般赔案--%>
				<option value="2" <s:if test="#attr.prpLpayObjectInfoPaycodeType==2">selected="selected"</s:if>><s:text name="common.compensate.interbank"/></option><%--同业--%>
				<option value="3" <s:if test="#attr.prpLpayObjectInfoPaycodeType==3">selected="selected"</s:if>><s:text name="common.compensate.NHI"/></option><%--健保局--%>
			</select> <span id="spanPayAccountInfo" style="display: none">
				<table class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="centertitle" colspan=2><s:text name="common.compensate.paymentObjectinfo"/></td><%--赔付对象讯息--%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" style="width: 96%"><s:text name="common.compensate.addmessage1"/></td><%--（按“+”号键的增加赔付对象的讯息，按“ - ”号键删除赔付对象讯息）--%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertPrpLpayObjectInfo();" name="buttonPayAccountInfoInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody id="PayAccountInfo">
						<c:forEach var="prpLpayObject" items="${requestScope.prpLpayObjectInfo.prpLpayObjectInfoList}" varStatus="stat">
							<tr name="PrpLpayObjectInfo">
								<td class="subformtitle" style="width: 96%">
									<table class="common" style="width: 100%">
										<tr>
											<td class="input" colspan="6">
												<b><s:text name="compensate.paymentObject"/>&nbsp;<span name="payObjectIndex"><c:out value="${prpLpayObject.id.serialNo}" /></span></b><%--赔付对象--%>
												<input type="hidden" name="prpLpayObjectInfoSerialNo" value="<c:out value="${prpLpayObject.id.serialNo}"/>">
												<c:if test="${stat.index==0}">
													<div name="payObject">
														<font color="red"><s:text name="prompt.compensate.message2"/></font>
														<%--请注意：若赔付对象为法人者，请在“统一编号”栏位输入该公司之八码统一编号，若赔付对象为个人者，请在“统一编号”栏位输入该人员之十码个人身份证字号--%>
													</div>
												</c:if>
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%"><s:text name='common.compensate.claimPayment'/>：</td><%--赔款支付方式--%>
											<td class="input" style="width: 18%">
												<select name="prpLpayObjectInfoOwnerShip" style="width: 50%" onchange="payObjectOwnerShipChange(this);">
													<option value="B" <c:if test="${prpLpayObject.ownerShip=='B'}"><c:out value="selected"/></c:if>>
														<s:text name="compensate.remittance" />
													</option>
													<!-- 汇款 -->
													<!-- <option value="C" <c:if test="${prpLpayObject.ownerShip=='C'}"><c:out value="selected"/></c:if>>
														<s:text name="compensate.agentInfo.cash" />
													</option>-->
													<!-- 现金 -->
													<option value="Q" <c:if test="${prpLpayObject.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
														<s:text name="compensate.agentInfo.cheque" />
													</option>
													<!-- 支票 -->
												</select>
											</td>
											<td class="title" style="width: 15%"><s:text name="common.compensate.payAmount"/>：</td><%--理赔金额--%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoPayAmount" class="readonly" readonly value="<fmt:formatNumber value="${prpLpayObject.payAmount}" pattern="#"/>" onfocus="cacheData(this);"
													onchange="validateMoney(this);" title="<s:text name="common.compensate.payAmount"/>" style="width: 80px;" /><%--理賠金額 --%>
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width:15%">支付幣別：</td>
											<td class="input" style="width:18%">
												<s:select name="prpLpayObjectInfoCurrency" class="common" value="#attr.prpLpayObjectInfo.currency" listKey ="key" listValue="value" list="#request.prpLpayObjectInfoCurrencyList"/>
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%"><s:text name="compensate.paymentObject"/>：</td><%--赔付对象--%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoOwnerName" class="input" maxlength="120" value="${prpLpayObject.ownerName}">
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 15%"><s:text name="claim.costType"/>：</td><%--费用类型--%>
											<td class="input" style="width: 18%">
												<select name="prpLpayObjectInfoPaymentKind" style="width: 50%" onchange="changePaymentKind(this);">
													<option value="1" <c:if test="${prpLpayObject.paymentKind=='1'}"> <c:out value="selected"/></c:if>><s:text name="common.compensate.garage"/></option><%--修车厂--%>
													<option value="2" <c:if test="${prpLpayObject.paymentKind=='2'}"> selected</c:if>><s:text name="common.compensate.materialSupplier"/></option><%--材料商--%>
													<option value="3" <c:if test="${prpLpayObject.paymentKind=='3'}"> selected</c:if>><s:text name="common.compensate.companyNo"/></option><%--公司行号--%>
													<option value="4" <c:if test="${prpLpayObject.paymentKind=='4'}"> selected</c:if>><s:text name="common.compensate.personal"/></option><%--个人--%>
													<option value="5" <c:if test="${prpLpayObject.paymentKind=='5'}"> selected</c:if>><s:text name="common.compensate.notaryCorporation"/></option><%--公证公司--%>
													<option value="6" <c:if test="${prpLpayObject.paymentKind=='6'}"> selected</c:if>><s:text name="common.compensate.NHI"/></option><%--健保局--%>
												</select> <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
											<td class="input" style="width: 15%">證件類型：</td>
											<td class="input" style="width: 18%">
												<c:set var="tempCertificateCode" value='${prpLpayObject.certificateCode}' />
												<s:select name="prpLpayObjectInfoCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<span name="InfoUniformNo1" <c:if test="${prpLpayObject.ownerShip=='C' && prpLpayObject.paymentKind=='4'}">style="display:none;"</c:if>><s:text name="common.compensate.uniformNoOrID"/>：</span> <%--统一编号/身份证号--%>
												<span name="InfoUniformNo2" <c:if test="${!(prpLpayObject.ownerShip=='C' && prpLpayObject.paymentKind=='4')}">style="display:none;"</c:if>><s:text name="common.compensate.personalIDNumber"/>：</span><%--个人身份证号--%>
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoUniformNo" class="input" value="${prpLpayObject.uniformNo}" />
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
											<td class="title" style="width: 15%">
												<span name="BeneficiaryPhone1" <c:if test="${prpLpayObject.ownerShip=='C'}">style="display:none;"</c:if>><s:text name="common.compensate.payeePhone"/>：</span> <%--受款人电话--%>
												<span name="BeneficiaryPhone2" <c:if test="${prpLpayObject.ownerShip!='C'}">style="display:none;"</c:if>><s:text name="common.compensate.localCalls"/>：</span><%--市内电话--%>
											</td>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoBeneficiaryPhone" class="input" value="${prpLpayObject.beneficiaryPhone}" />
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" name="BeneficiaryPhoneIMG" />
											</td>
											<td class="title" style="width: 15%">
												<span name="spanCutBack" <c:if test="${prpLpayObject.ownerShip!='Q'}">style="display:none;"</c:if>><s:text name="common.compensate.cutBack"/>：</span><%--禁背--%>
											</td>
											<td class="input" style="width: 18%">
												<span name="spanCutBack" <c:if test="${prpLpayObject.ownerShip!='Q'}">style="display:none;"</c:if>> <select name="prpLpayObjectInfoCutBack" class='common' style="width: 50%">
														<option value="1" <c:if test="${prpLpayObject.cutBack=='1'}"> selected</c:if>><s:text name="regist.prpLregist.yes"/></option><%--是--%>
														<option value="0" <c:if test="${prpLpayObject.cutBack=='0'}"> selected</c:if>><s:text name="regist.prpLregist.no"/></option><%--否--%>
												</select>
												</span>
											</td>
										</tr>
										<tr name="bankInfo" <c:if test="${prpLpayObject.ownerShip!='B'}">style="display:none;"</c:if>>
											<td class="title" style="width: 15%"><s:text name="common.compensate.bankCode"/>：</td><%--总行代号--%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoBankCode" readOnly="readonly" class="readonly" value="${prpLpayObject.bankCode}" />
											</td>
											<td class="title" style="width: 15%"><s:text name="common.compensate.bankName"/>：</td><%--总行名称--%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoBankName" readOnly="readonly" class="readonly" value="${prpLpayObject.bankName}">
											</td>
											<td class="title" style="width: 15%"><s:text name="common.compensate.accountCode"/>：</td><%--汇款账号--%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoAccountCode" readOnly="readonly" class="readonly" value="${prpLpayObject.accountCode}">
											</td>
										</tr>
										<tr name="bankInfo" <c:if test="${prpLpayObject.ownerShip!='B'}">style="display:none;"</c:if>>
											<td class="title" style="width: 15%"><s:text name="common.compensate.customBankCode"/>：</td><%--分行代号--%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoCustomBankCode" readOnly="readonly" class="readonly" value="${prpLpayObject.customBankCode}">
											</td>
											<td class="title" style="width: 15%"><s:text name="common.compensate.customBankName"/>：</td><%--分行名称--%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoCustomBankName" readOnly="readonly" class="readonly" value="${prpLpayObject.customBankName}">
											</td>
											<td class="title" style="width: 33%" colspan="2" align="center">
												<!-- 录入赔款支付帳户信息 -->
												<input class='bigbutton' type='button' name='buttonAccCompensate' style="width: 180px;" value='<s:text name="button.inputPaymentInformation.value" />' onclick="queryUserCompensate(this);">
											</td>
										</tr>
										<tr name="AreaInfo" <c:if test="${prpLpayObject.ownerShip=='C'}">style="display:none"</c:if>>
											<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
											<!--  \webapp\claim\pages\commonCargo\compensate\EditPrpdpaymentaccountPage.jsp 2-->
											<td class="title" style="width: 15%"><s:text name="common.compensate.areaCode"/>：</td><%--邮递区号--%>
											<td class="input" style="width: 18%">
												<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
												<input name="prpLpayObjectInfoAreaCode" class="input" value="${prpLpayObject.areaCode}" maxlength="3"/>
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
											<td class="title" style="width: 15%"><s:text name="common.compensate.courierAddress"/>：</td><%--邮递地址--%>
											<td class="input" style="width: 50%" colspan="3">
												<input name="prpLpayObjectInfoCourierAddress" class="input" value="${prpLpayObject.courierAddress}">
												<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
											</td>
										</tr>
										<tr name="PayDate" <c:if test="${prpLpayObject.ownerShip!='C'}">style="display:none"</c:if>>
											<td class="title" style="width: 15%"><s:text name="common.compensate.paymentDate"/>：</td><%--付款日期--%>
											<td class="input" style="width: 18%">
												<rc:rcDate name="prpLpayObjectInfoPayDate" class="input" value="${prpLpayObject.payDate}" />
											</td>
											<td class="title" style="width: 15%"><s:text name="regist.mobilePhones"/>：</td><%--行动电话--%>
											<td class="input" style="width: 18%">
												<input name="prpLpayObjectInfoMobilePhoneNo" class="input" value="${prpLpayObject.mobilePhoneNo}">
											</td>
											<td class="title" style="width: 15%"></td>
											<td class="input" style="width: 18%"></td>
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