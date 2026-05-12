<%--
****************************************************************************
* DESC       :添加主信息子块界面页面Tail[ 实赔 ]
* AUTHOR     :理赔组
* CREATEDATE :2004-05-12
* MODIFYLIST :  Name       Date            Reason/Contents
*          ------------------------------------------------------
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
****************************************************************************
--%>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<%
					String sosMedicFee = (String) request.getAttribute("sosMedicFee");
					double sumDutyPaid = ((PrpLcompensateDto) request.getAttribute("prpLcompensateDto")).getSumDutyPaid();
					double compelSumDutyPaid = ((PrpLcompensateDto) request.getAttribute("compelPrpLcompensateDto")).getSumDutyPaid();
					System.out.println(compelSumDutyPaid + "#############" + sumDutyPaid);
					double sumNoDutyFee = ((PrpLcompensateDto) request.getAttribute("prpLcompensateDto")).getSumNoDutyFee();
					double compelSumNoDutyFee = ((PrpLcompensateDto) request.getAttribute("compelPrpLcompensateDto")).getSumNoDutyFee();
					double sumPaid = ((PrpLcompensateDto) request.getAttribute("prpLcompensateDto")).getSumPaid();
					double compelSumPaid = ((PrpLcompensateDto) request.getAttribute("compelPrpLcompensateDto")).getSumPaid();
				%>
				<tr>
					<td class="left">
						<s:text name="compensate.paymentAmount" />
					</td>
					<!-- 赔款合计 -->
					<td class="right">
						<input class="readonly" type=text name="compensateSumDutyPaid" readonly="true" value="<%=(sumDutyPaid + compelSumDutyPaid)%>">
					</td>
					<td class="left">
						<s:text name="claim.cost" />
					</td>
					<!-- 费用 -->
					<td class="right">
						<input type=text name="compensateSumNoDutyFee" class="readonly" readonly="true" value="<%=(sumNoDutyFee + compelSumNoDutyFee)%>">
					</td>
					<td class="left">
						<s:text name="quickCase.indemnityInTotal" />
					</td>
					<!-- 赔款总计 -->
					<td class="right">
						<input class="readonly" type=text name="compensateSumPaid" readonly="true" value="<%=(sumPaid + compelSumPaid)%>">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="quickCase.commercialInsuranceAmount" />
					</td>
					<!-- 商业险赔款合计 -->
					<td class="right">
						<!--input type=text name="prpLcompensateSumPrePaid" class="readonly" readonly="true"   style="width:140px" value="<bean:write name='prpLcompensateDto' property='sumPrePaid' filter='true' format='##0.00'/>"-->
						<input type="hidden" type=text name="prpLcompensateSumPrePaid" class="readonly" readonly="true" value="<%=sosMedicFee%>">
						<input class="readonly" type=text name="prpLcompensateSumDutyPaid" readonly="true" value="<bean:write name='prpLcompensateDto' property='sumDutyPaid' filter='true' format='##0.00'/>">
					</td>
					<td class="left">
						<s:text name="quickCase.commercialInsuranceCosts" />
					</td>
					<!-- 商业险费用 -->
					<td class="right">
						<input type="hidden" class="readonly" type=text name="prpLcompensateSumThisPaid" readonly="true"
							value="<bean:write name='prpLcompensateDto' property='sumThisPaid' filter='true' format='##0.00'/>">
						<input type=text name="prpLcompensateSumNoDutyFee" class="readonly" readonly="true" value="<bean:write name='prpLcompensateDto' property='sumNoDutyFee' filter='true' format='##0.00'/>">
					</td>
					<td class="left">
						<s:text name="quickCase.commercialInsuranceTotal" />
					</td>
					<!-- 商业险赔款总计 -->
					<td class="right">
						<input class="readonly" type="hidden" name="prpLcompensateSumSelfValue" readonly="true" value="<bean:write name='prpLcompensateDto' property='bank' format='##0.00'/>">
						<input class="readonly" type=text name="prpLcompensateSumPaid" readonly="true" value="<bean:write name='prpLcompensateDto' property='sumPaid' filter='true' format='##0.00'/>">
					</td>
					<input type="hidden" name="prpLcompensateHandlerCode" class="codecode" style="width: 27%" value="">
					<input type="hidden" name="prpLcompensateHandlerName" class="codename" style="width: 48%" title="經辦人" value="">
					<input type="hidden" type="text" class="common" name="prpLcompensateStatisticsYM" value="">
					<input type="hidden" type=text name="prpLcompensateChecker1" class="readonly" readonly="true" value="">
					<input type="hidden" class="input" type=text name="prpLcompensateRemark" value="">
					<input class="readonly" type="hidden" readonly=true type=text name="backReason" value="">
				</tr>
				<tr>
					<td class="left">
						<s:text name="quickCase.insurancIndemnitAmount" />
					</td>
					<!-- 交强险赔款合计 -->
					<td class="right">
						<!--input type=text name="prpLcompensateSumPrePaid" class="readonly" readonly="true"   style="width:140px" value="<bean:write name='prpLcompensateDto' property='sumPrePaid' filter='true' format='##0.00'/>"-->
						<input type="hidden" type=text name="prpLcompensateSumPrePaid" class="readonly" readonly="true" value="<%=sosMedicFee%>">
						<input class="readonly" type=text name="compelPrpLcompensateSumDutyPaid" readonly="true" value="<bean:write name='compelPrpLcompensateDto' property='sumDutyPaid' filter='true' format='##0.00'/>">
					</td>
					<td class="left">
						<s:text name="quickCase.payInsuranceCost" />
					</td>
					<!-- 交强险费用 -->
					<td class="right">
						<input type="hidden" class="readonly" type=text name="compelprpLcompensateSumThisPaid" readonly="true"
							value="<bean:write name='prpLcompensateDto' property='sumThisPaid' filter='true' format='##0.00'/>">
						<input type=text name="compelPrpLcompensateSumNoDutyFee" class="readonly" readonly="true"
							value="<bean:write name='compelPrpLcompensateDto' property='sumNoDutyFee' filter='true' format='##0.00'/>">
					</td>
					<td class="left">
						<s:text name="quickCase.insuranceIndemnityInTotal" />
					</td>
					<!-- 交强险赔款总计 -->
					<td class="right">
						<input class="readonly" type="hidden" name="prpLcompensateSumSelfValue" readonly="true" value="<bean:write name='prpLcompensateDto' property='bank' format='##0.00'/>">
						<input class="readonly" type=text name="compelPrpLcompensateSumPaid" readonly="true" value="<bean:write name='compelPrpLcompensateDto' property='sumPaid' filter='true' format='##0.00'/>">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
