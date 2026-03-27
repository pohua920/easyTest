<%@ include file="/common/taglibs.jsp"%>
<table class="common" style="width: 100%">
	<tr>
		<td class="input" style="width: 15%">
			<s:text name="compensate.feePayment" />：
		</td>
		<!-- 费用支付方式 -->
		<td class="input" style="width: 35%">
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
		<td class="input" style="width: 15%">理賠金額：</td>
		<!-- 费用支付方式 -->
		<td class="input" style="width: 35%">
			<input name="prpLpayObjectInfoPayAmount" type="text" class="input" maxlength="8" style="width: 80px" value="<fmt:formatNumber value="${pageScope.prpLpayObject.payAmount}" pattern="#"/>"
				onfocus="cacheData(this);" onblur="validateMoney(this);" title="理賠金額">
		</td>
	</tr>
</table>