<%--
****************************************************************************
* DESC       ：赔付标的信息页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2004-10-18
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%--多行输入自定义JavaScript方法域--%>
<%--多行输入自定义JavaScript方法域--%>
<script language="javascript">
	//在下面加入本页自定义的JavaScript方法

	function viewDangerUnitCompensateLloss(field) {
		for ( var i = 1; i < fm.prpLlossDtoSerialNo.length; i++) {
			if (fm.prpLlossDtoDangerNo[i] == field) {
				var count = i;
				var policyNo = fm.policyno.value;
				var damageDate = fm.damageStartDate.value;
				var submitStr = "getDangerUnit.do?policyNo=" + policyNo
						+ "&damageDate=" + damageDate + "&openerIndex=" + count
						+ "&PageType=CompensateLloss";
				window.open(submitStr,
								'查看危险单位信息',
								'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
			}
		}
	}
	/*
	插入一条新的lLoss之後的处理（可选方法）
	 */
	function afterInsertlLoss() {
		setPrpLlossDtoSerialNo();
	}

	/*
	  删除本条WarnRegion之後的处理（可选方法）
	 */
	function afterDeletelLoss(field) {

		setPrpLlossDtoSerialNo();
	}

	/**
	 * 设置setPrpLlossDtoSerialNo
	 */
	function setPrpLlossDtoSerialNo() {
		var count = getElementCount("prpLlossDtoSerialNo");
		for ( var i = 0; i < count; i++) {
			if (count != 1) {
				fm.prpLlossDtoSerialNo[i].value = i;
			}
		}
	}
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center">
	<!--表示显示多行的-->
	<tr>
		<td class="subformtitle" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="lLossImg" onclick="showPage(this,spanlLoss)">
			<s:text name="compensate.dubang.payMarkInfo" />
			<br>
			<%--赔付标的信息--%>
			<span style="display: none">
				<table class="common" style="display: none" id="lLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="width: 4%">
								<div align="center">
									<input class="readonlyNo" readonly name="prpLlossDtoSerialNo" description="序号">
								</div>
							</td>
							<td class="subformtitle" style="width: 92%">
								<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
									<tbody>
										<!--是否超过保单中的限额标志域-->
										<!-- input type="hidden" name="prpLlossDtoOverAmount" -->
										<input type='hidden' name="prpLlossDtoLicenseNo">
										<input type='hidden' name='prpLlossDtoItemKindNo'>
										<input type='hidden' name='prpLlossDtoFamilyNo'>
										<input type='hidden' name="prpLlossDtoFeeTypeCode">
										<input type='hidden' name="prpLlossDtoFeeTypeName">
										<input type='hidden' name='prpLlossDtoFamilyName'>
										<input type='hidden' name='prpLlossDtoItemCode'>
										<input type='hidden' name='prpLlossDtoItemAddress'>
										<input type='hidden' name='prpLlossDtoBuyDate' value="2004/12/12">
										<input type='hidden' name='prpLlossDtoDepreRate'>
										<input type='hidden' name='prpLlossDtoCurrency1' value="CNY">
										<input type='hidden' name='prpLlossDtoCurrency4' value="CNY">
										<input type='hidden' name='prpLlossDtoFlag'>
										<input type='hidden' name='prpLlossDtoUnit'>
										<input type="hidden" name="prpLlossDtoLossQuantity">
										<input type="hidden" name="prpLlossDtoUnitPrice">
										<input type="hidden" name="prpLlossDtoIndemnityDutyRate">
										<tr>
											<td class="title" style="width: 20%">
												<s:text name="claim.dangeSerialNum" />：
											</td>
											<%--危险单位序号--%>
											<td>
												<input type=text name="prpLlossDtoDangerNo" class="codecode" value="1" onClick="viewDangerUnitCompensateLloss(this);">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="certainLoss.thirdCarLoss.prpLcheckRiskType" />：
											</td>
											<%--险别--%>
											<td class="input" style="width: 35%">
												<input type="input" name=prpLlossDtoKindCode class="codecode" style="width: 40" ondblclick="code_CodeSelect(this, 'PolicyKindCode');" onkeyup="code_CodeSelect(this, 'PolicyKindCode');">
												<input type="input" name=prpLlossDtoKindName class="codename" style="width: 110" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');">
												<img src="/claim/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="commonAcci.compensate.insuranceProjectName" />：
											</td>
											<%--保险项目名称--%>
											<td class="input" style="width: 35%">
												<input type='hidden' name='LossItemCode'>
												<input name="LossItemName" class='readonly' readonly>
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="certainLoss.lossMarkName" />：
											</td>
											<%--损失标的名称--%>
											<td class="input" style="width: 85%" colspan="3">
												<input name="prpLlossDtoLossName" class="common" style="width: 330px">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLperson.currency" />：
											</td>
											<%--币别--%>
											<td class="input" style="width: 35%" colspan="3">
												<input type="text" name="prpLlossDtoCurrency" class="readonly" readonly style="width: 30px" value="CNY" ondblclick="code_CodeSelect(this, 'Currency');" onkeyup="code_CodeSelect(this, 'Currency');">
												<input type="text" name="prpLlossDtoCurrencyName" class="readonly" readonly style="width: 90px" value="人民币" ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="regist.prpLregist.sumAmount" />：
											</td>
											<%--保险金额--%>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoAmount" class='readonly' readonly>
											</td>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLCitemKind.value" />：
											</td>
											<%--保险价值--%>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoItemValue" class="common" style="width: 180px">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLperson.currency" />：
											</td>
											<%--币别--%>
											<td class="input" style="width: 35%" colspan="3">
												<input type="text" name="prpLlossDtoCurrency2" class="readonly" readonly style="width: 30px" value="CNY" ondblclick="code_CodeSelect(this, 'Currency');" onkeyup="code_CodeSelect(this, 'Currency');">
												<input type="text" name="prpLlossDtoCurrency2Name" class="readonly" readonly style="width: 90px" value="人民币" ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="compensate.approvedLoss" />：
											</td>
											<%--核定损失--%>
											<td class="input">
												<input name="prpLlossDtoSumLoss" class="common" style="width: 180px">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLCitemKind.deductible" />：
											</td>
											<%--免赔额--%>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoDeductible" type='text' class="input" style="width: 180px">
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="db.prpLpersonloss.claimRate" />：
											</td>
											<%--赔付比例--%>
											<td class="input">
												<input name="prpLlossDtoClaimRate" class="common" style="width: 180px">%
											</td>
											<td class="title">
												<s:text name="db.prpLCitemKind.deductible" />：
											</td>
											<%--免赔额--%>
											<td class="input">
												<input name="prpLlossDtoDeductibleRate" class="common" style="width: 180px">%
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLperson.currency" />：
											</td>
											<%--币别--%>
											<td class="input" style="width: 35%" colspan="3">
												<input type="text" name="prpLlossDtoCurrency3" class="readonly" readonly style="width: 30px" value="CNY" ondblclick="code_CodeSelect(this, 'Currency');" onkeyup="code_CodeSelect(this, 'Currency');">
												<input type="text" name="prpLlossDtoCurrency3Name" class="readonly" readonly style="width: 90px" value="人民币" ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="claim.salvage" />：
											</td>
											<%--残值--%>
											<td class="input">
												<input name="prpLlossDtoSumRest" class="common" style="width: 180px">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="claim.compenPay" />：
											</td>
											<%--赔偿金额--%>
											<td class="input" style="width: 35%">
												<input name="prpLlossDtoSumRealPay" readonly style="width: 180px" class="input">
											</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td class="input" style="width: 4%">
								<div align="center">
									<input type=button name="buttonlLossDelete" onclick="deleteRow(this,'lLoss')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanlLoss" style="display: none"> <%-- 多行输入展现域 --%>
				<table id="lLoss" class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="title" style="width: 4%">
								<s:text name="db.prpLmedicine.serialNo" />
							</td>
							<%--序号--%>
							<td class="title" style="width: 96%" colspan=2>
								<s:text name="db.prpLregistText.context" />
							</td>
							<%--内容--%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=2 style="width: 96%">
								<s:text name="prompt.schedule.addRename12" />
							</td>
							<%--(按"+"号键增加险别信息，按"-"号键删除信息)--%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('lLoss')" name="buttonlLossInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
						<%
							if (strBaseCurrency != null && !"".equals(strBaseCurrency) && !"CNY".equals(strBaseCurrency)) {
						%>
						<tr>
							<td class="title" style="color: red">
								<s:text name="claim.signCurrencyCase" />:
							</td>
							<%--此案件签单币别为--%>
							<td>
								<input type=text name="BaseCurrency2" class="readonly" readonly style="color: red" value="<%=strBaseCurrency%>">
							</td>
						</tr>
						<tr>
							<td class="title" style="color: red">
								<s:text name="claim.currentExchangeRate" />:
							</td>
							<%--当前兑换率为--%>
							<td>
								<input type=text name="ExchRate2" class="readonly" readonly style="color: red" value="<%=strExchRate%>">
							</td>
						</tr>
						<%
							}
						%>
					</tfoot>
					<tbody>
						<%
							int index24 = 0;
						%>
						<logic:notEmpty name="prpLlossDto" property="prpLlossList">
							<logic:iterate id="lloss" name="prpLlossDto" property="prpLlossList">
								<tr>
									<td class="input" style="width: 4%">
										<div align="center">
											<input class="readonlyNo" readonly name="prpLlossDtoSerialNo" description="序号" value="<bean:write name='lloss' property='serialNo'/>">
										</div>
									</td>
									<td class="subformtitle" style="width: 92%">
										<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
											<tbody>
												<!--是否超过保单中的限额标志域-->
												<!-- input type="hidden" name="prpLlossDtoOverAmount" -->
												<input type='hidden' name="prpLlossDtoLicenseNo" value="<bean:write name='lloss' property='licenseNo'/>">
												<input type='hidden' name='prpLlossDtoItemKindNo' value="<bean:write name='lloss' property='itemKindNo'/>">
												<input type='hidden' name='prpLlossDtoFamilyNo' value="<bean:write name='lloss' property='familyNo'/>">
												<input type='hidden' name="prpLlossDtoFeeTypeCode" value="<bean:write name='lloss' property='feeTypeCode'/>">
												<input type='hidden' name="prpLlossDtoFeeTypeName" value="<bean:write name='lloss' property='feeTypeName'/>">
												<input type='hidden' name='prpLlossDtoFamilyName' value="<bean:write name='lloss' property='familyName'/>">
												<input type='hidden' name='prpLlossDtoItemCode' value="<bean:write name='lloss' property='itemCode'/>">
												<input type='hidden' name='prpLlossDtoItemAddress' value="<bean:write name='lloss' property='itemAddress'/>">
												<input type='hidden' name='prpLlossDtoBuyDate' value="<bean:write name='lloss' property='buyDate'/>">
												<input type='hidden' name='prpLlossDtoDepreRate' value="<bean:write name='lloss' property='depreRate'/>">
												<input type='hidden' name='prpLlossDtoCurrency1' value="<bean:write name='lloss' property='currency1'/>">
												<input type='hidden' name='prpLlossDtoCurrency4' value="<bean:write name='lloss' property='currency4'/>">
												<input type='hidden' name='prpLlossDtoFlag' value="<bean:write name='lloss' property='flag'/>">
												<input type='hidden' name='prpLlossDtoUnit' value="<bean:write name='lloss' property='unit'/>">
												<input type="hidden" name="prpLlossDtoLossQuantity" value="<bean:write name='lloss' property='lossQuantity'/>">
												<input type="hidden" name="prpLlossDtoUnitPrice" value="<bean:write name='lloss' property='unitPrice'/>">
												<input type="hidden" name="prpLlossDtoIndemnityDutyRate" value="<bean:write name='lloss' property='indemnityDutyRate'/>">
												<tr>
													<td class="title" style="width: 20%">
														<s:text name="claim.dangeSerialNum" />：
													</td>
													<%--危险单位序号--%>
													<td>
														<input type=text name="prpLlossDtoDangerNo" class="codecode" value="<bean:write name='lloss' property='dangerNo'/>" onClick="viewDangerUnitCompensateLloss(this);">
													</td>
													<td class="title" style="width: 15%">
														<s:text name="certainLoss.thirdCarLoss.prpLcheckRiskType" />：
													</td>
													<%--险别--%>
													<td class="input" style="width: 35%">
														<input type="input" name=prpLlossDtoKindCode class="codecode" style="width: 40" value="<bean:write name='lloss' property='kindCode'/>" ondblclick="code_CodeSelect(this, 'PolicyKindCode');" onkeyup="code_CodeSelect(this, 'PolicyKindCode');">
														<input type="input" name=prpLlossDtoKindName class="codename" style="width: 110" value="<bean:write name='lloss' property='kindName'/>" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');">
														<img src="/claim/images/bgMarkMustInput.jpg">
													</td>
													<td class="title" style="width: 15%">
														<s:text name="commonAcci.compensate.insuranceProjectName" />：
													</td>
													<%--保险项目名称--%>
													<td class="input" style="width: 35%">
														<input type='hidden' name='LossItemCode'>
														<input name="LossItemName" class='readonly' readonly>
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="certainLoss.lossMarkName" />：
													</td>
													<%--损失标的名称--%>
													<td class="input" style="width: 85%" colspan="3">
														<input name="prpLlossDtoLossName" class="common" style="width: 330px" value="<bean:write name='lloss' property='lossName'/>">
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLperson.currency" />：
													</td>
													<%--币别--%>
													<td class="input" style="width: 35%" colspan="3">
														<input type="text" name="prpLlossDtoCurrency" class="readonly" readonly style="width: 30px" value="<bean:write name='lloss' property='currency'/>" ondblclick="code_CodeSelect(this, 'Currency');" onkeyup="code_CodeSelect(this, 'Currency');">
														<input type="text" name="prpLlossDtoCurrencyName" class="readonly" readonly style="width: 90px" value="<bean:write name='lloss' property='currencyName'/>" ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="regist.prpLregist.sumAmount" />：
													</td>
													<%--保险金额--%>
													<td class="input" style="width: 35%">
														<input name="prpLlossDtoAmount" class='readonly' readonly value="<bean:write name='lloss' property='amount' format='##0.00'/>">
													</td>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLCitemKind.value" />：
													</td>
													<%--保险价值--%>
													<td class="input" style="width: 35%">
														<input name="prpLlossDtoItemValue" class="common" style="width: 180px" value="<bean:write name='lloss' property='itemValue' format='##0.00'/>">
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLperson.currency" />：
													</td>
													<%--币别--%>
													<td class="input" style="width: 35%" colspan="3">
														<input type="text" name="prpLlossDtoCurrency2" class="readonly" readonly style="width: 30px" value="<bean:write name='lloss' property='currency2'/>" ondblclick="code_CodeSelect(this, 'Currency');" onkeyup="code_CodeSelect(this, 'Currency');">
														<input type="text" name="prpLlossDtoCurrency2Name" class="readonly" readonly style="width: 90px" value="<bean:write name='lloss' property='currency2Name'/>" ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
													</td>
												</tr>
												<tr>
													<td class="title">
														<s:text name="compensate.approvedLoss" />：
													</td>
													<%--核定损失--%>
													<td class="input">
														<input name="prpLlossDtoSumLoss" class="common" style="width: 180px" value="<bean:write name='lloss' property='sumLoss' format='##0.00'/>">
													</td>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLCitemKind.deductible" />：
													</td>
													<%--免赔额--%>
													<td class="input" style="width: 35%">
														<input name="prpLlossDtoDeductible" type='text' class="input" style="width: 180px" value="<bean:write name='lloss' property='deductible' format='##0.00'/>">
													</td>
												</tr>
												<tr>
													<td class="title">
														<s:text name="db.prpLpersonloss.claimRate" />：
													</td>
													<%--赔付比例--%>
													<td class="input">
														<input name="prpLlossDtoClaimRate" class="common" style="width: 180px" value="<bean:write name='lloss' property='claimRate' format='##0.0000'/>">%
													</td>
													<td class="title">
														<s:text name="db.prpLCitemKind.deductible" />：
													</td>
													<%--免赔额--%>
													<td class="input">
														<input name="prpLlossDtoDeductibleRate" class="common" style="width: 180px" value="<bean:write name='lloss' property='deductibleRate' format='##0.0000'/>">%
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLperson.currency" />：
													</td>
													<%--币别--%>
													<td class="input" style="width: 35%" colspan="3">
														<input type="text" name="prpLlossDtoCurrency3" class="readonly" readonly style="width: 30px" value="<bean:write name='lloss' property='currency3'/>" ondblclick="code_CodeSelect(this, 'Currency');" onkeyup="code_CodeSelect(this, 'Currency');">
														<input type="text" name="prpLlossDtoCurrency3Name" class="readonly" readonly style="width: 90px" value="<bean:write name='lloss' property='currency3Name'/>" ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
													</td>
												</tr>
												<tr>
													<td class="title">
														<s:text name="claim.salvage" />：
													</td>
													<%--残值--%>
													<td class="input">
														<input name="prpLlossDtoSumRest" class="common" style="width: 180px" value="<bean:write name='lloss' property='sumRest' format='##0.00'/>">
													</td>
													<td class="title" style="width: 15%">
														<s:text name="claim.compenPay" />：
													</td>
													<%--赔偿金额--%>
													<td class="input" style="width: 35%">
														<input name="prpLlossDtoSumRealPay" readonly style="width: 180px" class="input" value="<bean:write name='lloss' property='sumRealPay' format='##0.00'/>">
													</td>
												</tr>
											</tbody>
										</table>
									</td>
									<td class="input" style='width: 4%'>
										<div align="center">
											<input type=button name="buttonlLossDelete" onclick="deleteRow(this,'lLoss')" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
								<%
									index24++;
								%>
							</logic:iterate>
						</logic:notEmpty>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
