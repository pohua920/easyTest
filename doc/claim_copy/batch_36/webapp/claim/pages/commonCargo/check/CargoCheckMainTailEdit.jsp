<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="0">
				<tr>
					<td class="left">
						<s:text name="check.shipName" />
						<%-- 货主名称 --%>
					</td>
					<td class="right">
						<input type="text" name="prpLextSalvor" class="input"
							value="${prpLext.salvor}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="db.prpCmain_cargo.conveyance" />
						<%-- 装载运输工具 --%>
					</td>
					<td class="right">
						<input name="prpLcheckCargoName" class="readonly" readonly="true" value="${prpLcarGo.blNo }" />
					</td>
					<td class="left">
						<s:text name="check.ladeBill" />
						<%-- 提单/运单 --%>
					</td>
					<td class="right">
						<input type="text" name="prpLextLoadingNo" class="input" value="${prpLext.remark}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="check.dischargeDate" />
						<%-- 卸货日期 --%>
					</td>
					<td class="right">
						<rc:rcDate name="prpLextUnloadDate" class="input" value="${prpLext.unloadDate}" />
					</td>
					<td class="left"></td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>