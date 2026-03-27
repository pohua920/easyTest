<table class="common" style="width: 100%" id="CheckCarProp" cellspacing="1" cellpadding="5">
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
					<input type="button" value="+" class=smallbutton onclick="insertRowTableOfCheckCarProp('CheckCarProp','CheckCarProp_Data',this)" name="buttonCheckCarPropInsert" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<%
			int carPropIndex = 0;
		%>
		<logic:notEmpty name="prpLthirdPartyDto" property="carThirdPropDtoList">
			<logic:iterate id="ThirdPropdtox" name="prpLthirdPartyDto" property="carThirdPropDtoList">
				<%
					carPropIndex++;
				%>
				<tr>
					<td class="common">
						<input type="hidden" name="checkPrpLthirdPropCarDtoLicenseNo" value="<bean:write name='ThirdPropdtox' property='licenseNo' filter='true'/>">
						<input type="text" class="readonly" readonly name="checkPropCarSerialNo" description="ÐòºÅ" value="<%=carPropIndex%>">
						<input type="hidden" name="checkPrpLpropCarserialNo" class="common" maxlength=3 value="">
					</td>
					<td class="common">
						<input type="text" name="checkPrpLthirdPropCarDtoLossItemName" class="common" style="width: 90%" value="<bean:write name='ThirdPropdtox' property='lossItemName' filter='true' />">
						<input type="hidden" name="checkPrpLthirdPropCarDtoLossItemCode" class="common" value="<bean:write name='ThirdPropdtox' property='lossItemCode' filter='true' />">
					</td>
					<td class="common">
						<input name="checkPrpLthirdPropCarDtoLossItemDesc" class="common" value="<bean:write name='ThirdPropdtox' property='lossItemDesc' filter='true' />">
					</td>
					<td class="common" style='width: 4%' align="center">
						<div>
							<input type=button name="buttonCheckCarPropDelete" class="smallbutton" onclick="deleteRow(this,'CheckCarProp')" value="-" style="cursor: hand">
						</div>
					</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</tbody>
</table>
