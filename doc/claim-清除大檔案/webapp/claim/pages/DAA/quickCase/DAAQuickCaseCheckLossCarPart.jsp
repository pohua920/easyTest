
<table class="common" style="width: 100%" id="CheckCarPart" cellspacing="1" cellpadding="5">
	<thead>
		<tr class="listtitle">
			<td style="width: 9%">
				<s:text name="quickCase.lossNumber" />
			</td>
			<!-- 损失序号 -->
			<td style="width: 22%">
				<s:text name="certainLoss.thirdCarLoss.prpLchecDemagePart" />
			</td>
			<!-- 损失部位 -->
			<td style="width: 22%">
				<s:text name="certainLoss.thirdCarLoss.prpLcheckAccessoryName" />
			</td>
			<!-- 零件(项目)名称 -->
			<td style="width: 22%">
				<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageLevel" />
			</td>
			<!-- 损失程度级别 -->
			<td style="width: 21%">
				<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageDetail" />
			</td>
			<!-- 损失程度描述 -->
			<td style="width: 4%">&nbsp;</td>
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td class="title" colspan=5>
				<s:text name="certainLoss.thirdCarLoss.promptLoss" />
			</td>
			<!-- (按"+"号键增加损失部位信息，按"-"号键删除信息) -->
			<td class="title" align="right" style="width: 4%">
				<div align="center">
					<input type="button" value="+" class=smallbutton onclick="insertRowTableOfCheckCarPart('CheckCarPart','CheckCarPart_Data',this)" name="buttonCheckCarPartInsert" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<logic:notEmpty name="prpLthirdPartyDto" property="prpLthirdCarLossDtoList">
			<logic:iterate id="thirdCarLossdtox" name="prpLthirdPartyDto" property="prpLthirdCarLossDtoList">
				<tr>
					<td class="common" style="wdith: 11%">
						<input type="hidden" name="checkPrpLthirdCarLossDtoLicenseNo" value="<bean:write name='thirdCarLossdtox' property='licenseNo'/>">
						<input type="hidden" name="prpLthirdCarLossFlag">
						<input type="text" class="readonly" readonly name="checkPartySerialNo" description="序号" value="<bean:write name='thirdCarLossdtox' property='serialNo'/>">
						<input type="hidden" name="checkPrpLthirdCarLossDtoItemNo" class="common" maxlength=3 value="<bean:write name='thirdCarLossdtox' property='itemNo'/>">
					</td>
					<td class="common" style="wdith: 16%">
						<html:select name="thirdCarLossdtox" property="partCode" styleClass="three" style="width:90%" onchange="getPartName(this);">
							<html:options collection="partCodeList" property="value" labelProperty="label" />
						</html:select>
						<input type="hidden" name="checkPrpLthirdCarLossDtoPartName" value="<bean:write name='thirdCarLossdtox' property='partName'/>">
					</td>
					<td class="common" style="wdith: 16%">
						<input name="checkPrpLthirdCarLossDtoCompName" class="codename" style="width: 90%" value="<bean:write name='thirdCarLossdtox' property='compName'/>"
							ondblclick="return openCompCodeWin(CheckCarPart,this);">
						<input type="hidden" name="checkPrpLthirdCarLossDtoCompCode" value="<bean:write name='thirdCarLossdtox' property='compCode'/>">
					</td>
					<td class="common" style="wdith: 11%">
						<input name="checkPrpLthirdCarLossDtoLossGrade" class="common" value="<bean:write name='thirdCarLossdtox' property='lossGrade' filter='true' />">
					</td>
					<td class="common" style="wdith: 21%">
						<input name="checkPrpLthirdCarLossDtoLossDesc" class="common" value="<bean:write name='thirdCarLossdtox' property='lossDesc' filter='true' />">
					</td>
					<td class="common" style='width: 4%' align="center">
						<div>
							<input type=button name="buttonCheckCarPartDelete" class="smallbutton" onclick="deleteRow(this,'CheckCarPart')" value="-" style="cursor: hand">
						</div>
					</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</tbody>
</table>
