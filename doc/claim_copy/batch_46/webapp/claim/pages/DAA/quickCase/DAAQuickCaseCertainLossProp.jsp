<%-- 财产损失项目隐藏域--%>
<table class="common" style="display: none" id="CertainLossProp_Data" cellspacing="1" cellpadding="0">
	<tbody>
		<tr>
			<td class="input" style="width: 12%">
				<input type="hidden" name="propSerialNo" class=common value=''>
				<input type="text" name="prpLpropKindCode" class=codecode value="" ondblclick="kindCodeCarPropSelect(this);" onkeyup="kindCodeCarPropSelect(this);">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropKindName" class=codecode value="" value="" ondblclick="kindNameCarPropSelect(this);" onkeyup="kindNameCarPropSelect(this);">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropLossItemName" class=common value="">
			</td>
			<td class="input" style="width: 12%">
				<html:select name="prpLpropDto" property="feeTypeCode" style="width:80px">
					<html:options collection="FeeTypeCodeList" property="value" labelProperty="label" />
				</html:select>
				<input type="hidden" name="prpLpropDtoFeeTypeCode" value="">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropSumLoss" class=common value="">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropSumReject" class=common value="">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropSumDefLoss" class=common value="">
			</td>
			<td class="input" style="width: 12%">
				<input type="text" name="prpLpropRemark" class=common value="">
			</td>
			<td class="input" style="width: 4%">
				<div>
					<input type=button name="buttonCertainLossCarPropDelete" class="smallbutton" onclick="deleteRow(this,'CertainLossProp')" value="-" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tbody>
</table>
<table class="common" id="CertainLossProp" cellpadding="5" cellspacing="1">
	<thead>
		<tr>
			<td class="subformtitle" colspan=9>
				<s:text name="quickCase.propertyDamageList" />
			</td>
			<!-- 财产损失清单 -->
		</tr>
		<tr>
			<td class="centertitle" style="width: 12%">
				<s:text name="db.prpDkind.kindCode " />
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
				<s:text name="db.prpLprop.sumLoss " />
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
					<input type="button" class=smallbutton value="+" onclick="insertRow('CertainLossProp')" name="button" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<logic:notEmpty name="certainLossDto" property="prpLpropDtoList">
			<logic:iterate id="prpLpropDto" name="certainLossDto" property="prpLpropDtoList">
				<tr>
					<td class="input" style="width: 12%">
						<input type="hidden" name="propSerialNo" class=common value=''>
						<input type="text" name="prpLpropKindCode" class=codecode value="<bean:write name='prpLpropDto' property='kindCode' filter='true' />" ondblclick="kindCodeCarPropSelect(this);"
							onkeyup="kindCodeCarPropSelect(this);">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropKindName" class=codecode value="<bean:write name='prpLpropDto' property='kindName' filter='true' />" ondblclick="kindNameCarPropSelect(this);"
							onkeyup="kindNameCarPropSelect(this);">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropLossItemName" class=common value="<bean:write name='prpLpropDto' property='lossItemName' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<html:select name="prpLpropDto" property="feeTypeCode" style="width:80px">
							<html:options collection="FeeTypeCodeList" property="value" labelProperty="label" />
						</html:select>
						<input type="hidden" name="prpLpropDtoFeeTypeCode" value="<bean:write name='prpLpropDto' property='feeTypeCode' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropSumLoss" class=common value="<bean:write name='prpLpropDto' property='sumLoss' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropSumReject" class=common value="<bean:write name='prpLpropDto' property='sumReject' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropSumDefLoss" class=common value="<bean:write name='prpLpropDto' property='sumDefLoss' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<input type="text" name="prpLpropRemark" class=common value="<bean:write name='prpLpropDto' property='remark' filter='true' />">
					</td>
					<td class="input" style="width: 4%">
						<div>
							<input type=button name="buttonCertainLossCarPropDelete" class="smallbutton" onclick="deleteRow(this,'CertainLossProp')" value="-" style="cursor: hand">
						</div>
					</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</tbody>
</table>