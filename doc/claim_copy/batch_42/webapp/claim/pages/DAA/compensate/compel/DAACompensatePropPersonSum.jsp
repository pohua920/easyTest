<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：理赔组 陈杰
* CREATEDATE ： 2013-03-14
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center">
	<tr>
		<td class="left">
			&nbsp;&nbsp;<b>受害人數彙整</b>
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1" id="PersonLossNumberCount">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						理賠人數<img src="${ctx}/images/bgMarkMustInput.jpg">
					</td>
					<%--理賠人數--%>
					<td class="right" colspan="5">
						<input class="readonly" type=text name="personLossNumber" id="personLossNumber" readonly="true" value="0" style="width: 60px">
					</td>
				</tr>
				<tr>
					<td class="left">
						本車傷亡情形<img src="${ctx}/images/bgMarkMustInput.jpg">
					</td>
					<%--本車傷亡情形--%>
					<td class="right"></td>
					<td class="left">
						對方車傷亡情形<img src="${ctx}/images/bgMarkMustInput.jpg">
					</td>
					<%--對方車傷亡情形 --%>
					<td class="right"></td>
					<td class="left">
						車外人傷亡情形<img src="${ctx}/images/bgMarkMustInput.jpg">
					</td>
					<%--車外人傷亡情形  --%>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">醫療人數</td>
					<%--醫療人數 --%>
					<td class="right">
						<input class="readonly" type=text name="carMedicalNumber" readonly="true" value="0">
					</td>
					<td class="left">醫療人數</td>
					<%--醫療人數 --%>
					<td class="right">
						<input class="readonly" type=text name="threeCarMedicalNumber" readonly="true" value="0">
					</td>
					<td class="left">醫療人數</td>
					<%--醫療人數 --%>
					<td class="right">
						<input class="readonly" type="text" name="outerCarMedicalNumber" readonly="true" value="0">
					</td>
				</tr>
				<tr>
					<td class="left">失能人數</td>
					<%--失能人數 --%>
					<td class="right">
						<input class="readonly" type=text name="carCrippledNumber" readonly="true" value="0">
					</td>
					<td class="left">失能人數</td>
					<%--失能人數 --%>
					<td class="right">
						<input class="readonly" type=text name="threeCarCrippledNumber" readonly="true" value="0">
					</td>
					<td class="left">失能人數</td>
					<%--失能人數 --%>
					<td class="right">
						<input class="readonly" type="text" name="outerCarCrippledNumber" readonly="true" value="0">
					</td>
				</tr>
				<tr>
					<td class="left">死亡人數</td>
					<%--死亡人數 --%>
					<td class="right">
						<input class="readonly" type=text name="carDeathNumber" readonly="true" value="0">
					</td>
					<td class="left">死亡人數</td>
					<%--死亡人數 --%>
					<td class="right">
						<input class="readonly" type=text name="threeCarDeathNumber" readonly="true" value="0">
					</td>
					<td class="left">死亡人數</td>
					<%--死亡人數 --%>
					<td class="right">
						<input class="readonly" type="text" name="outerCarDeathNumber" readonly="true" value="0">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<input type="hidden" name="configCode" value="<c:out value='${requestScope.configCode}' />">
<c:if test="${not empty requestScope.limitMap}">
	<c:forEach items="${requestScope.limitMap}" var="map">
		<input type="hidden" name="limitType" value="<c:out value='${map.key}'/>">
		<input type="hidden" name="limitFee" value="<c:out value='${map.value}'/>">
	</c:forEach>
</c:if>
<table cellpadding="0" cellspacing="1" class=common style="width: 100%">
	<tr>
		<td class="common">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="CommerceImg" onclick="showPage(this,spanCommerce);"> <b>強制險賠款計算訊息</b><br> <span id="spanCommerce" style="display:">
				<table id="PersonLossCareFee" cellspacing="1" cellpadding="1" style="width: 100%">
					<thead>
						<tr>
							<td class="centertitle" style="width: 10%;" align="center">損失項目</td>
							<td class="centertitle" style="width: 15%;" align="center">損失合計</td>
							<td class="centertitle" style="width: 15%;" align="center">核定賠償金</td>
							<td class="centertitle" style="width: 30%;" align="center">強制保險限額</td>
							<td class="centertitle" style="width: 15%;" align="center">賠款金額</td>
							<td class="centertitle" style="width: 15%;" align="center">實賠金額總計</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="6">
								<table class=subtable cellspacing="1" cellpadding="1">
									<tr>
										<td class="input" style="width: 10%;">醫療費用</td>
										<td class="input" style="width: 15%;">
											<input type="text" name="medicalSumLoss" class="readonly" readonly value="0">
										</td>
										<td class="input" style="width: 15%;">
											<input type="text" name="medicalSumDefPay" class="readonly" readonly value="0">
										</td>
										<td class="input" style="width: 30%;" align="center">
											<script type="text/javascript">
												document.write(MAXMEDICALPAY / 10000);
											</script>
											萬×受傷人數
										</td>
										<td class="input" style="width: 15%;">
											<input type="text" name="medicalSumRelPay" class="readonly" readonly value="0">
										</td>
										<td class="input" style="width: 15%;" rowspan="3">
											<input type="text" name="totalPay" class="readonly" readonly value="0">
										</td>
									</tr>
									<tr>
										<td class="input" style="width: 10%;">失能給付</td>
										<td class="input" style="width: 15%;">
											<input type="text" name="crippledSumLoss" class="readonly" readonly value="0">
										</td>
										<td class="input" style="width: 15%;">
											<input type="text" name="crippledSumDefPay" class="readonly" readonly value="0">
										</td>
										<td class="input" style="width: 30%;" align="center">失能給付額之和</td>
										<td class="input" style="width: 15%;">
											<input type="text" name="crippledSumRelPay" class="readonly" readonly value="0">
										</td>
									</tr>
									<tr>
										<td class="input" style="width: 10%;">死亡給付</td>
										<td class="input" style="width: 15%;">
											<input type="text" name="deathSumLoss" class="readonly" readonly value="0">
										</td>
										<td class="input" style="width: 15%;">
											<input type="text" name="deathSumDefPay" class="readonly" readonly value="0">
										</td>
										<td class="input" style="width: 30%;" align="center">
											<script type="text/javascript">
												document.write(MAXDEATHPAY / 10000);
											</script>
											萬×死亡人數
										</td>
										<td class="input" style="width: 15%;">
											<input type="text" name="deathSumRelPay" class="readonly" readonly value="0">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>