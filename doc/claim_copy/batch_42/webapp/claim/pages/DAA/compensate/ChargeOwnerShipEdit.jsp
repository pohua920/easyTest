<%@ include file="/common/taglibs.jsp"%>
<table class="common" style="width: 100%">
	<tr>
		<td class="input" style="width: 10%">
			<s:text name="compensate.feePayment" />：
		</td>
		<!-- 费用支付方式 -->
		<td class="input" style="width: 20%">
			<select name="prpLchargeOwnerShip" onchange="ownerShip_change(this);">
				<option value="B" <c:if test="${pageScope.chargedtox.ownerShip=='B'}"><c:out value="selected"/></c:if>>
					<s:text name="compensate.remittance" />
				</option>
				<!-- 汇款 -->
				<option value="Q" <c:if test="${pageScope.chargedtox.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
					<s:text name="compensate.agentInfo.cheque" />
				</option>
				<!-- 支票 -->
				<!--<option value="C" <c:if test="${pageScope.chargedtox.ownerShip=='C'}"><c:out value="selected"/></c:if> ><s:text name="compensate.agentInfo.cash"/></option> -->
				<!-- 现金 -->
			</select>
		</td>
		<td class="input" style="width: 70%"></td>
	</tr>
</table>