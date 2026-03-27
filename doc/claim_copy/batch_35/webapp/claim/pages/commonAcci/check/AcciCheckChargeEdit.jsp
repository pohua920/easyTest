<%--
****************************************************************************
* DESC       ：添加调查费用信息页面
* AUTHOR     ：孟冬冬
* CREATEDATE ： 2006-01-23
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="ChargeImg" onclick="showPage(this,spanCharge)">
			<%--调查费用--%>
			<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCost1" />
			<br> <span style="display: none">
				<table class="common" style="display: none" id="AcciCheckCharge_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" align="center" style="width: 10%">
								<input name="prpLAcciCheckChargeCode" class="codecode" style="width: 95%" ondblclick="code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.riskCode.value);" onkeyup="code_CodeSelect(this, 'ChargeCode','0,1','Y','N',fm.riskCode.value);">
							</td>
							<td class="input" align="center" style="width: 30%">
								<input name="prpLAcciCheckChargeName" class="codename" style="width: 95%" ondblclick="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.riskCode.value);" onkeyup="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N',fm.riskCode.value);">
							</td>
							<td class="input" align="center" style="width: 30%">
								<input type="text" name="prpLAcciCheckChargeCurrency" class="readonly" readonly value="${ prpLacciCheck.currency}" style="width: 30%" />
								<input type="text" name="prpLAcciCheckChargeCurrencyName" class="readonly" readonly value="${ prpLacciCheck.currencyName}" style="width: 65%" />
							</td>
							<td class="input" align="center" style="width: 26%">
								<input name="prpLAcciCheckChargeAmount" class="input" style="width: 95%" onchange="calFund();" />
							</td>
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow(this,'AcciCheckCharge'),calFund()" value="-" style="cursor: hand">
									<input type="hidden" name="prpLchargeFlag">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanCharge" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="AcciCheckCharge">
					<thead>
						<tr>
							<td class="centertitle" style="width: 10%">
								<%--费用代码--%>
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCostCode" />
							</td>
							<td class="centertitle" style="width: 30%">
								<%--费用名称--%>
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCostName" />
							</td>
							<td class="centertitle" style="width: 30%">
								<%--币别--%>
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCostValuta" />
							</td>
							<td class="centertitle" style="width: 26%">
								<%--费用金额--%>
								<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCostCount" />
							</td>
							<td class="title" style="width: 4%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=4 style="width: 96%">
								<s:text name="prompt.certify.addRemove" />
								<%--	(按"+"号键增加信息，按"-"号键删除信息)--%>
							</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('AcciCheckCharge')" class="smallbutton" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:if test="${acciCheckDto.prpLacciCheckChargeList!=null}">
							<c:forEach var="acciCheckCharge" items="${acciCheckDto.prpLacciCheckChargeList}" varStatus="index">
								<tr>
									<td class="input" align="center" style="width: 10%">
										<input name="prpLAcciCheckChargeCode" class="codecode" style="width: 95%" value="${acciCheckCharge.chargeCode }" ondblclick="code_CodeSelect(this, 'ChargeCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'ChargeCode','0,1','Y');">
									</td>
									<td class="input" align="center" style="width: 30%">
										<input name="prpLAcciCheckChargeName" class="codename" style="width: 95%" value="${acciCheckCharge.chargeName }" ondblclick="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'ChargeCode','-1,0','Y','N');">
									</td>
									<td class="input" align="center" style="width: 30%">
										<input type="text" name="prpLAcciCheckChargeCurrency" class="readonly" readonly style="width: 30%" value="${acciCheckCharge.currency }" />
										<input type="text" name="prpLAcciCheckChargeCurrencyName" class="readonly" style="width: 65%" value="${prpLacciCheck.currencyName}" />
									</td>
									<td class="input" align="center" style="width: 26%">
										<input name="prpLAcciCheckChargeAmount" class="input" style="width: 95%" onchange="calFund();" value="${acciCheckCharge.chargeAmount }">
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonChargeDelete" class="smallbutton" onclick="deleteRow(this,'AcciCheckCharge'),calFund()" value="-" style="cursor: hand">
											<input type="hidden" name="prpLchargeFlag">
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
