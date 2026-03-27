
<table class="common" id="RepairFee" cellpadding="5" cellspacing="1">
	<thead>
		<tr>
			<td class="subformtitle" colspan=10>
				<s:text name="certainLoss.costList" />
			</td>
			<!-- 修理项目费用清单 -->
		</tr>
		<tr>
			<!-- 险别名称隐藏不显示 -->
			<!--td class="centertitle"">险别名称</td-->
			<td class="centertitle" style="width: 11%">
				<s:text name="db.prpDrate.kindName" />
			</td>
			<!-- 险别名称 -->
			<td class="centertitle" style="width: 8%">
				<s:text name="certainLoss.repairParts" />
			</td>
			<!-- 修理部位 -->
			<td class="centertitle" style="width: 11%">
				<s:text name="certainLoss.thirdCarLoss.prpLcheckAccessoryName" />
			</td>
			<!-- 零件(项目)名称 -->
			<td class="centertitle" style="width: 8%">
				<s:text name="certainLoss.repairMethods" />
			</td>
			<!-- 修理方式 -->
			<td class="centertitle" style="width: 8%">
				<s:text name="db.prpLrepairFee.manHour" />
			</td>
			<!-- 工时 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="db.prpLrepairFee.manHourUnitPrice" />
			</td>
			<!-- 工时单价 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="db.prpLrepairFee.manHourFee" />
			</td>
			<!-- 工时费 -->
			<td class="centertitle" style="width: 12%">
				<s:text name="certainLoss.startCost" />
			</td>
			<!-- 初始工时费 -->
			<td class="centertitle" style="width: 14%">
				<s:text name="db.prpDcompany.remark" />
			</td>
			<!-- 备注 -->
			<td class="centertitle" style="width: 4%"></td>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td class="title" colspan=9 style="width: 96%">
				<s:text name="prompt.certainLoss.addRemoveCost" />
			</td>
			<!-- (按"+"号键增加修理项目费用信息，按"-"号键删除信息) -->
			<td class="title" align="right" style="width: 4%">
				<div align="center">
					<input type="button" class=smallbutton value="+" onclick="insertRowTableOfCertainLossRepair('RepairFee','RepairFee_Data',this)" name="buttonRepairFee" style="cursor: hand">
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="10">
				<table cellpadding="6" cellspacing="1" class="common">
					<tr>
						<td class='title' style="display: none" colspan="1" width="30%">
							<s:text name="certainLoss.costTotals" />:
							<input class='readonly' readonly="true" style='width: 80px' name='SumMaterialFee1'>
						</td>
						<!-- 材料费合计 -->
						<td class='title' colspan="4" width="40%">
							<s:text name="certainLoss.laborTotals" />:
							<input class='readonly' readonly="true" style='width: 80px' name='SumDefLoss1' value="<bean:write name='prpLcarLossDto' property='sumDefLoss1' filter='true'/>">
						</td>
						<!-- 工时费合计 -->
						<td class='title' style="display: none" colspan="4" width="40%">
							<s:text name="certainLoss.totalApproved" />:
							<input class='readonly' readonly="true" style='width: 80px' name='SumVerifyLoss1'>
						</td>
						<!-- 核定工时费合计 -->
					</tr>
				</table>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<logic:notEmpty name="prpLcarLossDto" property="prpLrepairFeeList">
			<logic:iterate id="prpLrepairFeeDto" name="prpLcarLossDto" property="prpLrepairFeeList">
				<tr>
					<td class="input" style="width: 11%">
						<input type="hidden" class="common" name="prpLrepairFeeLicenseNo" value="<bean:write name='prpLrepairFeeDto' property='licenseNo' filter='true'/>">
						<input type="hidden" class="common" name="prpLRepairFeeLossItemCode" value="<bean:write name='prpLrepairFeeDto' property='lossItemCode' filter='true'/>">
						<input type="hidden" name="prpLRepairFeeSerialNo" class=readonly readonly value="<bean:write name='prpLrepairFeeDto' property='serialNo' filter='true'/>">
						<input type="hidden" name="prpLrepairFeeKindCode" class="codecode" value="<bean:write name='prpLrepairFeeDto' property='kindCode' filter='true'/>">
						<logic:equal name="advance" value="1">
							<input type="text" name="prpLrepairFeeKindName" class="codecode" value="<bean:write name='prpLrepairFeeDto' property='kindName' filter='true'/>"
								ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
								onkeyup="code_CodeSelect(this,'PolicyKindCode','-1,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);">
						</logic:equal>
						<logic:notEqual name="advance" value="1">
							<input type="text" name="prpLrepairFeeKindName" class="codecode" value="<bean:write name='prpLrepairFeeDto' property='kindName' filter='true'/>"
								ondblclick="kindNameSelect(this,'prpLRepairFeeLossItemCode');" onkeyup="kindNameSelect(this,'prpLRepairFeeLossItemCode');">
						</logic:notEqual>
					</td>
					<td class="input" style="width: 8%">
						<html:select name="prpLrepairFeeDto" property="partCode">
							<html:options collection="partCodeList" property="value" labelProperty="label" />
						</html:select>
						<input type="hidden" name="prpLrepairFeePartCode' 
         				value="<bean:write name='prpLrepairFeeDto' property='partCode' filter='true' />">
					</td>
					<td class="input" style="width: 11%">
						<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
						<input name="prpLrepairFeeCompName" class="codename" maxlength="100" ondblclick="return openPrplRepairFeeCompWin(RepairFee_Data,this);" ondblchange="return getCompCode(RepairFee_Data,this);"
							value="<bean:write name='prpLrepairFeeDto' property='compName' filter='true' />">
						<input type="hidden" name="prpLrepairFeeCompCode" value="<bean:write name='prpLrepairFeeDto' property='compCode' filter='true' />">
					</td>
					<td class="input" style="width: 8%">
						<html:select name="prpLrepairFeeDto" property="repairType">
							<html:options collection="repairTypeCodes" property="codeCode" labelProperty="codeCName" />
						</html:select>
					</td>
					<td class="input" style="width: 8%">
						<input name="prpLrepairFeeManHour" class=common value="<bean:write name='prpLrepairFeeDto' property='manHour' filter='true' />" onchange="setSumRepairFee(this);">
					</td>
					<td class="input" style="width: 12%">
						<input name="prpLrepairFeeManHourUnitPrice" class="common" value="<bean:write name='prpLrepairFeeDto' property='manHourUnitPrice' filter='true' />" onchange="setSumRepairFee(this);">
					</td>
					<td class="input" style="width: 12%">
						<input name="prpLrepairFeeSumDefLoss" class="readonly" readonly value="<bean:write name='prpLrepairFeeDto' property='sumDefLoss' filter='true' />">
					</td>
					<td class="input" style="width: 12%">
						<input name="prpLrepairFeeFirstSumDefLoss" class="readonly" readonly value="<bean:write name='prpLrepairFeeDto' property='firstSumDefLoss' filter='true' />">
					</td>
					<td class="input" style="width: 14%">
						<input name="prpLrepairFeeRemark" class=common value="<bean:write name='prpLrepairFeeDto' property='remark' filter='true' />">
					</td>
					<td class="input" style="width: 4%">
						<div>
							<input type=button name="buttonRepairFeeDelete" class="smallbutton" onclick="deleteRow(this,'RepairFee');setSumRepairFee(this);" value="-" style="cursor: hand">
						</div>
					</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</tbody>
</table>