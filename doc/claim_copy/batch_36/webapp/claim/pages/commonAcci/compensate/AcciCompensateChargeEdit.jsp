<%--
****************************************************************************
* DESC       ：添加赔款费用信息页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<!--建立显示的录入条，可以收缩显示的-->
<script language='javascript'>
//在下面加入本页自定义的JavaScript方法

function viewDangerUnitCompensateCharge(field) {
    for (var i = 1; i < fm.prpLchargeSerialNo.length; i++) {
        if (fm.prpLchargeDangerNo[i] == field) {
            var count = i;
            var policyNo = fm.policyno.value;
            var damageDate = fm.damageStartDate.value;
            field.value = "";
            var submitStr = "getDangerUnit.do?policyNo=" + policyNo + "&damageDate=" + damageDate + "&openerIndex=" + count + "&PageType=CompensateCharge";
            window
                .open(
                    submitStr,
                    '查看危险单位信息',
                    'width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
        }
    }
}
</script>

<%--赔款费用--%>
<span style="display: none" id="Charge_span">
	<table class="common" cellspacing="1" cellpadding="0" id="Charge_Data">
		<tbody>
			<tr name="chargeObjectTr">
				<td class="input" style="width: 3%">
					<input type="text" name="prpLchargeSerialNo" class="readonly" value="0" readonly="readonly" >
				</td>
				<td class="input" style="width: 4%">
					<input type="text" name="prpLchargeDangerNo" class="codecode" value="1" onClick="viewDangerUnitCompensateCharge(this);" onchange="viewDangerUnitCompensateCharge(this);" onkeyup="viewDangerUnitCompensateCharge(this);">
				</td>
				<td class="input" style="width: 5%">
					<input type="hidden" name="prpLchargeFlag">
					<input type="text" name="prpLchargeKindCode" class="codecode" ondblclick="code_CodeSelect(this, 'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');"
						onchange="code_CodeSelect(this, 'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');clearPrpLctextContextInnerHTML();"
						onkeyup="code_CodeSelect(this, 'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');">
				</td>
				<td class="input" style="width: 15%">
					<input type="text" name="prpLchargeKindName" class="codename" ondblclick="code_CodeSelect(this, 'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');"
						onchange="code_CodeSelect(this, 'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');clearPrpLctextContextInnerHTML();"
						onkeyup="code_CodeSelect(this, 'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');"
					>
					<input type="hidden" name="prpLchargeItemKindNo" value="0">
				</td>
				<td class="input" style="width: 4%">
					<input type="text" name="prpLchargeChargeCode" class="codecode" ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
						onchange="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);clearPrpLctextContextInnerHTML();"
						onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
						onblur="clearPayObject(this);">
				</td>
				<td class="input" style="width: 10%">
					<input type="text" name="prpLchargeChargeName" class="codename" ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
						onchange="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);clearPrpLctextContextInnerHTML();"
						onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
						onblur="clearPayObject(this);">
				</td>
				<td class="input" style="width: 8%">
					<select name="prpLchargePayObjectType" class='common' style="width: 80px" onchange="clearPayObject(this);clearPayment(this);">
						<option value="B" selected>
							<s:text name="compensate.external" />
						</option>
						<%--外部--%>
						<option value="A">
							<s:text name="compensate.internal" />
						</option>
						<%--内部--%>
					</select>
					<input type="hidden" name="prpLchargePayObjectCode" class="readonly" readonly value="">
				</td>
				<td class="input" style="width: 8%">
					<input type="text" name="prpLchargePayObjectName" class="codename" style="width: 130px" value="" ondblclick="clearPayment(this);getPayObject(this);" onchange="clearPayment(this);getPayObject(this);" onkeyup="clearPayment(this);getPayObject(this);">
				</td>
				<td class="input" style="width: 8%">
					<input type="text" name="prpLchargeChargeReport" class="input" onchange="setChargeAmount(this);">
				</td>
				<td class="input" style="width: 8%">
					<input type="text" name="prpLchargeChargeAmount" class="input" onchange="setChargeAmount(this);">
					<input type="hidden" name="prpLchargeSumRealPay" class="input" readOnly="readonly" onchange="setChargeAmount(this);" value="0">
				</td>
				<td class="input" style="width: 6%">
					<select name="prpLchargeCurrency" class="common" style="width: 50px" onchange="getPrpLchargeExchRate(this);">
						<c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
							<option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>>
								<c:out value="${tempMap.key}" />
							</option>
						</c:forEach>
					</select>
					<input type="hidden" style="width: 50%" name="prpLchargeCurrencyName" class="readonly" readonly value="${prpLcompensate.currencyName }">
				</td>
				<td class="input" style="width: 6%">
					<input type="text" name="prpLchargeExchRate" style="width: 70px" value="1" class="input" onchange="setChargeAmount(this);">
				</td>
				<td class="input" style="width: 8%">
					<input type="text" name="prpLchargeChargeAmountNTD" style="width: 70px" class="common" readonly="readonly" value="0">
				</td>
				<td class="input" style="width: 4%">
					<input type="text" name="prpLchargeFeeSerialNo" class="input" style="width: 30px" maxlength="2">
				</td>
				<td class="input" align="center" style="width: 3%">
					<div>
						<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow(this,'Charge','prpLchargeSerialNo');sumPaid();clearPrpLctextContextInnerHTML();" value="-" style="cursor: hand">
						<input type="hidden" name="prpLchargeFlag">
					</div>
				</td>
			</tr>
			<tr name="chargePayObjectTr">
				<td colspan="14">
					<table class="common" style="width: 100%">
						<tr>
							<td class="input" style="width: 15%">
								費用支付方式：
							</td>
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
							<%--mantis：CLM0113 ，處理人員：BK007 蘇哲，需求單編號：CLM0113  傷害險增加AML功能 start--%>
							<td class="input" style="width: 15%">證件類型：</td>
							<td class="input" style="width: 18%">
								<%-- 支付幣別： --%>
								<input type="hidden" name="prpLchargeAccountCurrency" value="${LOCAL_CURRENCY }"/>
								<input type="hidden" name="prpLchargeCurrencyForPayObject" value="${LOCAL_CURRENCY }">
								
								<s:select name="prpLchargeCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
							</td>
							<td class="input" style="width: 10%">洗錢狀態回覆：</td>
							<td class="input" style="width: 20%">
								<input name="prpLchargeAMLFlag" readOnly="readonly" class="readonly" >
							</td>
							<%--mantis：CLM0113 ，處理人員：BK007 蘇哲，需求單編號：CLM0113  傷害險增加AML功能 end--%>
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
							<td class="input" style="width: 15%">
								統一編號/身份證號：
							</td>
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
										<option value="0">
											否
										</option>
										<option value="1" selected="selected">
											是
										</option>
									</select>
								</span>
							</td>
						</tr>
						<tr name="bankInfo">
							<td class="input" style="width: 15%">
								總行代號：
							</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeBankCode" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 15%">
								總行名稱：
							</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeBankName" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 15%">
								匯款帳號：
							</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeAccountCode" readOnly="readonly" class="readonly">
							</td>
						</tr>
						<tr name="bankInfo">
							<td class="input" style="width: 15%">
								分行代號：
							</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeCustomBankCode" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 15%">
								分行名稱：
							</td>
							<td class="input" style="width: 18%">
								<input name="prpLchargeCustomBankName" readOnly="readonly" class="readonly">
							</td>
							<td class="input" style="width: 34%" colspan="2"
								align="center">
								<input class='bigbutton' type='button' name='buttonAddAcc' value="<s:text name='button.entryPaymentInfo.value' />" onclick="queryUser(this)" style="width: 180px">
								<%--輸入费用支付帳户信息 --%>
							</td>
						</tr>
						<tr>
						<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
						<!-- \claim\webapp\claim\pages\commonAcci\compensate\AcciCompensateChargeEdit.jsp -->
							<td class="input" style="width: 15%">
								郵遞區號：
							</td>
							<td class="input" style="width: 18%">
								<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
								<input type="text" name="prpLchargeAreaCode" class="input" value="${prpCinsured.postCode }"  maxlength="3">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
							<td class="input" style="width: 15%">
								郵遞地址：
							</td>
							<td class="input" style="width: 52%" colspan="3">
								<input type="text" name="prpLchargeCourierAddress" class="input" value="${prpCinsured.postAddress }">
								<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
							</td>
						</tr>
					</table>
				</td>
				<td class="title"></td>
			</tr>
			<tr height="2" bgcolor="block">
				<td colspan="15"></td>
			</tr>
		</tbody>
	</table>
</span>
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr class="common">
		<td colspan="4" align="left">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="ChargeImg" onclick="showPage(this,spanCharge)">
			<s:text name="check.compCosts" />
			<br>
			<span id="spanCharge" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="Charge">
					<thead>
						<tr>
							<td class="centertitle" style="width: 3%">
								序號
							</td>
							<td class="centertitle" style="width: 4%">
								<s:text name="claim.dangeSerialNum" /><%--危险单位序号--%>
							</td>
							<td class="centertitle" style="width: 5%">
								<s:text name="regist.prpLregist.kindCode" /><%-- 险别代码 --%>
							</td>
							<td class="centertitle" style="width: 15%">
								<s:text name="regist.prpLregist.kindName" /><%-- 险别名称 --%>
							</td>
							<td class="centertitle" style="width: 4%">
								<s:text name="compensate.costCode" /><%--费用代码--%>
							</td>
							<td class="centertitle" style="width: 10%">
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCostName" /><%-- 费用名称 --%>
							</td>
							<td class="centertitle" style="width: 8%">
								<s:text name="compensate.paymentType" /><%-- 支付类别 --%>
							</td>
							<td class="centertitle" style="width: 8%">
								<s:text name="compensate.payNameObject" /><%-- 支付对象名称 --%>
							</td>
							<td class="centertitle" style="width: 8%">
								<s:text name="commonAcci.compensate.totalAmount" /><%-- 总金额 --%>
							</td>
							<td class="centertitle" style="width: 8%">
								費用金額
							</td>
							<td class="centertitle" style="width: 6%">
								<s:text name="regist.prpLregist.currency" /><%-- 币别 --%>
							</td>
							<td class="centertitle" style="width: 6%">
								匯率
							</td>
							<td class="centertitle" style="width: 8%">
								費用金額 （NTD）
							</td>
							<td class="centertitle" style="width: 4%">
								代扣費用序號
							</td>
							<td class="centertitle" style="width: 3%">&nbsp;&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=14 style="width: 97%">
								<s:text name="prompt.schedule.addRename11" />
							</td>
							<%--(按"+"号键增加费用信息，按"-"号键删除信息)--%>
							<td class="title" align="right" style="width: 3%">
								<div align="center">
									<input type="button" class="smallbutton" value="+" onclick="insertRow('Charge',this,'prpLchargeSerialNo');" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
					<c:forEach var="chargedtox" items="${prpLcharge.prpLchargeList}">
						<tr name="chargeObjectTr">
							<td class="input" style="width: 3%">
								<input type="text" name="prpLchargeSerialNo" class="readonly" readonly="readonly" value="${chargedtox.id.serialNo}" >
							</td>
							<td class="input" style="width: 4%">
								<input type="text" name="prpLchargeDangerNo" class="codecode" value="${chargedtox.dangerNo}" onClick="viewDangerUnitCompensateCharge(this);" onchange="viewDangerUnitCompensateCharge(this);" onkeyup="viewDangerUnitCompensateCharge(this);">
							</td>
							<td class="input" style="width: 5%">
								<input type="hidden" name="prpLchargeFlag" value="${chargedtox.flag}">
								<input type="text" name="prpLchargeKindCode" class="codecode" value="${chargedtox.kindCode}" ondblclick="code_CodeSelect(this, 'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');" 
								onchange="code_CodeSelect(this, 'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');clearPrpLctextContextInnerHTML();" 
								onkeyup="code_CodeSelect(this, 'policyKindCodeOfPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');">
							</td>
							<td class="input" style="width: 15%">
								<input type="text" name="prpLchargeKindName" class="codename" value="${chargedtox.kindName}" ondblclick="code_CodeSelect(this, 'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');" 
									onchange="code_CodeSelect(this, 'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');clearPrpLctextContextInnerHTML();" 
									onkeyup="code_CodeSelect(this, 'policyKindCodeOfPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value+'|'+'${prpLclaim.familyNo}');"
								>
								<input type="hidden" name="prpLchargeItemKindNo" value="${chargedtox.itemKindNo}">
							</td>
							<td class="input" style="width: 4%">
								<input type="text" name="prpLchargeChargeCode" class="codecode" value="${chargedtox.chargeCode}" ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" onchange="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);clearPrpLctextContextInnerHTML();"
									onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" onblur="clearPayObject(this);">
							</td>
							<td class="input" style="width: 10%">
								<input type="text" name="prpLchargeChargeName" class="codename" value="${chargedtox.chargeName}" ondblclick="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" onchange="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);clearPrpLctextContextInnerHTML();"
									onkeyup="clearPayObject(this);clearPayment(this);code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);">
							</td>
							<td class="input" style="width: 8%">
								<select name="prpLchargePayObjectType" class='common' style="width: 80px" onchange="clearPayObject(this);clearPayment(this);">
									<option value="B" <c:if  test="${chargedtox.payObjectType=='B' }">selected</c:if>>
										<s:text name="compensate.external" />
									</option>
									<%--外部--%>
									<option value="A" <c:if test="${chargedtox.payObjectType=='A' }">selected</c:if>>
										<s:text name="compensate.internal" />
									</option>
									<%--内部--%>
								</select>
								<input type="hidden" name="prpLchargePayObjectCode" class="readonly" readonly value="${chargedtox.payObjectCode}">
							</td>
							<td class="input" style="width: 8%">
								<input type="text" name="prpLchargePayObjectName" class="codename" style="width: 130px" value="${chargedtox.payObjectName}" ondblclick="clearPayment(this);getPayObject(this);" onchange="clearPayment(this);getPayObject(this);" onkeyup="clearPayment(this);getPayObject(this);">
							</td>
							<td class="input" style="width: 8%">
								<input type="text" name="prpLchargeChargeReport" class="input" value="<fmt:formatNumber value='${chargedtox.chargeReport}' pattern='#.##'/>" onchange="setChargeAmount(this);clearPrpLctextContextInnerHTML();">
							</td>
							<td class="input" style="width: 8%">
								<input type="text" name="prpLchargeChargeAmount"  class="input" value="<fmt:formatNumber value='${chargedtox.chargeAmount}' pattern='#.##'/>" onchange="setChargeAmount(this)";>
								<input type="hidden" name="prpLchargeSumRealPay" class="input" readOnly="readonly" value="<fmt:formatNumber value='${chargedtox.sumRealPay}' pattern='#.##'/>" onchange="setChargeAmount(this);">
							</td>
							<td class="input" style="width: 6%">
								<select name="prpLchargeCurrency" class="common" style="width: 50px" onchange="getPrpLchargeExchRate(this);">
                                     <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                                         <option value="${tempMap.key}" <c:if test="${tempMap.key==chargedtox.currency}">selected="selected"</c:if>><c:out value="${tempMap.key}"/></option>
                                     </c:forEach>
                                 </select>
								<input style="width: 50%" type="hidden" name="prpLchargeCurrencyName" class="readonly" readonly value="${chargedtox.currencyName}">
							</td>
							<td class="input" style="width: 6%">
								<input type="text" name="prpLchargeExchRate" value="${chargedtox.exchRate}" style="width: 70px" class="input" onchange="setChargeAmount(this);">
							</td>
							<td class="input" style="width: 8%">
								<input type="text" name="prpLchargeChargeAmountNTD" class="common" readonly="readonly" value="<fmt:formatNumber value='${chargedtox.chargeAmount*chargedtox.exchRate}' pattern='#'/>" style="width: 70px">
							</td>
							<td class="input" style="width: 4%">
								<input type="text" name="prpLchargeFeeSerialNo" class="common" value="${chargedtox.feeSerialNo}"  style="width: 30px" maxlength="2">
							</td>
							<td class="input" align="center" style="width: 3%">
								<div>
									<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow(this,'Charge','prpLchargeSerialNo');sumPaid();clearPrpLctextContextInnerHTML();" value="-" style="cursor: hand">
									<input type="hidden" name="prpLchargeFlag">
								</div>
							</td>
						</tr>
						<tr name="chargePayObjectTr">
							<td colspan="14">
								<table class="common" style="width: 100%">
									<tr>
										<td class="input" style="width: 15%">
											費用支付方式：
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
										<%--mantis：CLM0113 ，處理人員：BK007 蘇哲，需求單編號：CLM0113  傷害險增加AML功能 start--%>
										<td class="input" style="width: 15%">證件類型：</td>
										<td class="input" style="width: 18%">
											<%-- 支付幣別： --%>
											<input type="hidden" name="prpLchargeAccountCurrency" value="${chargedtox.prpLpayObjectInfo.accountCurrency }"/>
											<input type="hidden" name="prpLchargeCurrencyForPayObject" value="${chargedtox.prpLpayObjectInfo.currency}">
										
											<c:set var="tempCertificateCode" value='${chargedtox.prpLpayObjectInfo.certificateCode}' />
											<s:select name="prpLchargeCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
										</td>
										<td class="input" style="width: 10%">洗錢狀態回覆：</td>
										<td class="input" style="width: 20%">
											<input name="prpLchargeAMLFlag" readOnly="readonly" class="readonly"  value="<c:out value='${chargedtox.prpLpayObjectInfo.amlFlag}'/>">
										</td>
										<%--mantis：CLM0113 ，處理人員：BK007 蘇哲，需求單編號：CLM0113  傷害險增加AML功能 end--%>
									</tr>
									<tr>
										<td class="input" style="width: 15%">賠付對象：</td>
										<td class="right" style="width: 18%">
											<input type="text" class='input' name="prpLchargeOwnerName" value="<c:out value='${chargedtox.prpLpayObjectInfo.ownerName}'/>">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
										<td class="input" style="width: 15%">統一編號/身份證號：</td>
										<td class="right" style="width: 18%">
											<input type="text" class='input' name="prpLchargeUniformNo" value="<c:out value='${chargedtox.prpLpayObjectInfo.uniformNo}'/>">
											<img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
										</td>
										<td class="input" style="width: 15%">
											<span name="spanCutBack" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip!='Q'}"> style="display:none" </c:if>>禁背</span>
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
									<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo!=null&&chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
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
									<tr name="bankInfo" <c:if test="${chargedtox.prpLpayObjectInfo!=null&&chargedtox.prpLpayObjectInfo.ownerShip!='B'}"> style="display:none" </c:if>>
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
									<!-- \claim\webapp\claim\pages\commonAcci\compensate\AcciCompensateChargeEdit.jsp 2-->
										<td class="input" style="width: 15%">郵遞區號：</td>
										<td class="right" style="width: 18%">
											<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
											<input type="text" class='input' name="prpLchargeAreaCode" value="<c:out value='${chargedtox.prpLpayObjectInfo.areaCode}'/>" maxlength="3">
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
							<td class="title"></td>
						</tr>
						<tr height="2" bgcolor="block">
							<td colspan="15"></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>