<table class="common" style="display: none" id="CheckProp_Data" cellspacing="1" cellpadding="5">
	<tbody>
		<tr>
			<td class="common">
				<input type="hidden" class="readonly" readonly name="checkPropSerialNo" description="ÐòºÅ" value="">
				<input type="text" name="checkPrpLpropserialNo" class="readonly" readonly maxlength=3 value="">
			</td>
			<td class="common">
				<input name="checkPrpLthirdPropDtoLossItemName" class="common" type="text" value="">
				<input type="hidden" name="checkPrpLthirdPropDtoLossItemCode" value="">
			</td>
			<td class="common">
				<input name="checkPrpLthirdPropDtoLossItemDesc" class="common" value="">
			</td>
			<td class="common" style='width: 4%' align="center">
				<div>
					<input type=button name="buttonCheckPropDelete" class="smallbutton" onclick="deleteRow(this,'CheckProp')" value="-" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tbody>
</table>
<table class="common" style="width: 100%" id="CheckProp" cellspacing="1" cellpadding="5">
	<thead>
		<tr class="listtitle">
			<td style="width: 9%">
				<s:text name="quickCase.lossNumber" />
			</td>
			<!-- ËðÊ§ÐòºÅ -->
			<td style="width: 27%">
				<s:text name="certainLoss.thirdCarLoss.lossName" />
			</td>
			<!-- ËðÊ§Ãû³Æ -->
			<td style="width: 60%">
				<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageDetail" />
			</td>
			<!-- ËðÊ§³Ì¶ÈÃèÊö -->
			<td style="width: 4%">&nbsp;</td>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td class="title" colspan=3>
				<s:text name="certainLoss.thirdCarLoss.promptLoss" />
			</td>
			<!-- (°´"+"ºÅ¼üÔö¼ÓËðÊ§²¿Î»ÐÅÏ¢£¬°´"-"ºÅ¼üÉ¾³ýÐÅÏ¢) -->
			<td class="title" align="right" style="width: 4%">
				<div align="center">
					<input type="button" value="+" <%=buttonReaOnly%> class=smallbutton onclick="insertRowTableOfCheckProp('CheckProp','CheckProp_Data',this)" name="buttonCheckPropInsert" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<%
			int propIndex = 0;
		%>
		<logic:notEmpty name="prpLthirdPartyDto" property="prpLthirdPropDtoList">
			<logic:iterate id="ThirdPropdtox" name="prpLthirdPartyDto" property="prpLthirdPropDtoList">
				<%
					propIndex++;
				%>
				<tr>
					<td class="common">
						<input type="hidden" class="readonly" readonly name="checkPropSerialNo" description="ÐòºÅ" value="<%=propIndex%>">
						<input type="text" name="checkPrpLpropserialNo" class="readonly" readonly maxlength=3 value="">
					</td>
					<td class="common">
						<input name="checkPrpLthirdPropDtoLossItemName" class="common" style="width: 90%" type="text" value="<bean:write name='ThirdPropdtox' property='lossItemName' filter='true' />">
						<input type="hidden" name="checkPrpLthirdPropDtoLossItemCode" class="common" value="<bean:write name='ThirdPropdtox' property='lossItemCode' filter='true' />">
					</td>
					<td class="common">
						<input name="checkPrpLthirdPropDtoLossItemDesc" class="common" value="<bean:write name='ThirdPropdtox' property='lossItemDesc' filter='true' />">
					</td>
					<td class="common" style='width: 4%' align="center">
						<div>
							<input type=button name="buttonCheckPropDelete" class="smallbutton" onclick="deleteRow(this,'CheckProp')" value="-" style="cursor: hand">
						</div>
					</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</tbody>
</table>
