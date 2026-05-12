
<table class="common" id="CertainLossCarProp" cellpadding="5" cellspacing="1">
	<thead>
		<tr>
			<td class="subformtitle" colspan=9>
				<s:text name="quickCase.propertyDamageList" />
			</td>
			<!-- 财产损失清单 -->
		</tr>
		<tr>
			<td class="centertitle" style="width: 12%">
				<s:text name="db.prpDkind.kindCode" />
			</td>
			<!-- 险别代码 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="db.prpDrate.kindName" />
			</td>
			<!-- 险别名称 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="certainLoss.lostProperty" />
			</td>
			<!-- 损失财产名称 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="db.prpLprop.feeTypeName" />
			</td>
			<!-- 费用名称 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="db.prpLprop.sumLoss" />
			</td>
			<!-- 受损金额 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="db.prpLprop.sumReject" />
			</td>
			<!-- 剔除金额 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="certainLoss.lossAmount" />
			</td>
			<!-- 定损金额 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="db.prpDcompany.remark" />
			</td>
			<!-- 备注 -->
			<td class="centertitle" style="width: 4%"></td>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td class="title" colspan=8 style="width: 96%">
				<s:text name="prompt.quickCase.addPropertyDamage" />
			</td>
			<!-- (按"+"号键增加财产损失信息，按"-"号键删除信息) -->
			<td class="title" align="right" style="width: 4%">
				<div align="center">
					<input type="button" class=smallbutton value="+" onclick="insertRowTableOfCertainLossProp('CertainLossCarProp','CertainLossCarProp_Data',this)" name="button" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<logic:notEmpty name="prpLcarLossDto" property="prpLpropCarDtoList">
			<logic:iterate id="prpLpropCarDto" name="prpLcarLossDto" property="prpLpropCarDtoList">
				<tr>
					<td class="input" style="width: 12%">
						<input type="hidden" class="common" name="prpLpropCarDtoLicenseNo" value="<bean:write name='prpLpropCarDto' property='licenseNo' filter='true' />">
						<input type="hidden" class="common" name="prpLPropCarSerialno">
						<input type="hidden" name="prpLPropSerialNo" class=readonly readonly value="<bean:write name='prpLpropCarDto' property='serialNo' filter='true' />">
						<input type="text" name="prpLpropCarDtoKindCode" class=codecode value="<bean:write name='prpLpropCarDto' property='kindCode' filter='true' />"
							ondblclick="kindCodeSelect(this,'prpLPropCarSerialno');" onkeyup="kindCodeSelect(this,'prpLPropCarSerialno');">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropCarDtoKindName" class=codecode value="<bean:write name='prpLpropCarDto' property='kindName' filter='true' />"
							ondblclick="kindNameSelect(this,'prpLPropCarSerialno');" onkeyup="kindNameSelect(this,'prpLPropCarSerialno');">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropCarDtoLossItemName" class=common value="<bean:write name='prpLpropCarDto' property='lossItemName' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<html:select name="prpLpropCarDto" property="feeTypeCode" style="width:80px">
							<html:options collection="FeeTypeCodeList" property="value" labelProperty="label" />
						</html:select>
						<input type="hidden" name="prpLpropCarDtoFeeTypeCode" value="<bean:write name='prpLpropCarDto' property='feeTypeCode' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropCarDtoSumLoss" class=common value="<bean:write name='prpLpropCarDto' property='sumLoss' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropCarDtoSumReject" class=common value="<bean:write name='prpLpropCarDto' property='sumReject' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropCarDtoSumDefLoss" class=common value="<bean:write name='prpLpropCarDto' property='sumDefLoss' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropCarDtoRemark" class=common value="<bean:write name='prpLpropCarDto' property='remark' filter='true' />">
					</td>
					<td class="input" style="width: 4%">
						<div>
							<input type=button name="buttonCertainLossCarPropDelete" class="smallbutton" onclick="deleteRow(this,'CertainLossCarProp')" value="-" style="cursor: hand">
						</div>
					</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</tbody>
</table>