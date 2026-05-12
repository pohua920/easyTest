<%@ include file="/common/taglibs.jsp"%>
<tr>
	<td colspan="12">
		<table class="common" style="width: 100%">
			<tr>
				<td class="title" style="width: 12%">
					<s:text name="replevy.feePayment" />：
				</td>
				<!-- 费用支付方式 -->
				<td class="input" style="width: 18%">
					<select name="prpLchargeOwnerShip" onchange="compensateChargeOwnerShip_change(this.options[this.selectedIndex].value,this)">
						<option value="B" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip=='B'}"><c:out value="selected"/></c:if>>
							<s:text name="compensate.remittance" />
						</option>
						<!-- 汇款 -->
						<!--						<option value="C" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip=='C'}"><c:out value="selected"/></c:if> ><s:text name="compensate.agentInfo.cash"/></option> 现金 -->
						<option value="Q" <c:if test="${chargedtox.prpLpayObjectInfo.ownerShip=='Q'}"><c:out value="selected"/></c:if>>
							<s:text name="compensate.agentInfo.cheque" />
						</option>
						<!-- 支票 -->
					</select>
				</td>
				<td style="width: 8%"></td>
				<td style="width: 18%"></td>
				<td style="width: 12%"></td>
				<td style="width: 18%"></td>
			</tr>
		</table>
	</td>
</tr>