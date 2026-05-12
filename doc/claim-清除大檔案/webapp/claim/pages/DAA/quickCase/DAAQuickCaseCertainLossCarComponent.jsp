
<table class="common" cellpadding="5" cellspacing="1" id="Component">
	<thead>
		<tr>
			<td class="subformtitle" colspan=17>
				<s:text name="certainLoss.ProjectCosts" />
			</td>
			<!-- 零部件更换项目费用清单 -->
		</tr>
		<tr>
			<!-- 险别代码隐藏不显示 -->
			<!--td style="display:none" class="centertitle" style="width:0%" rowspan="2">
								险别代码 </td-->
			<td class="centertitle" style="width: 8%" rowspan="2">
				<s:text name="db.prpDrate.kindName" />
			</td>
			<!-- 险别名称 -->
			<td class="centertitle" style="width: 8%" rowspan="2">
				<s:text name="certainLoss.partName" />
			</td>
			<!-- 部件名称  -->
			<td class="centertitle" style="width: 8%" rowspan="2">
				<s:text name="certainLoss.originalEncoding" />
			</td>
			<!-- 原厂编码 -->
			<!--td colspan="3" class="centertitle" style="width:5%">
								中心报价 </td-->
			<td colspan="3" class="centertitle" style="width: 21%">
				<s:text name="certainLoss.localQuotes" />
			</td>
			<!-- 本地报价  -->
			<td class="centertitle" style="width: 9%" rowspan="2">
				<s:text name="certainLoss.repairQuotes" />
			</td>
			<!-- 修理厂报价 -->
			<td class="centertitle" style="width: 9%" rowspan="2">
				<s:text name="certainLoss.lossPrice" />
			</td>
			<!-- 定损单价  -->
			<td class="centertitle" style="width: 6%" rowspan="2">
				<s:text name="certainLoss.priceType" />
			</td>
			<!-- 价格类型  -->
			<td class="centertitle" style="width: 5%" rowspan="2">
				<s:text name="certainLoss.numberReplacement" />
			</td>
			<!-- 更换数量  -->
			<td class="centertitle" style="width: 9%" rowspan="2">
				<s:text name="print.salvValueAmount" />
			</td>
			<!-- 残值金额 -->
			<td rowspan="2" class="centertitle" style="width: 8%">
				<s:text name="certainLoss.subtotal" />
			</td>
			<!-- 小计 -->
			<td class="centertitle" style="width: 5%" rowspan="2">
				<s:text name="db.prpDcompany.remark" />
			</td>
			<!-- 备注 -->
			<td class="centertitle" style="width: 4%" rowspan="2"></td>
		</tr>
		<tr>
			<!-- 中心报价隐藏不需要显示 -->
			<!--td class="centertitle" style="width:9%">
								专修价 </td>
							<td class="centertitle" style="width:5%">
								市场价 </td>
							<td class="centertitle" style="width:5%">
								副厂价 </td-->
			<td class="centertitle" style="width: 7%">
				<s:text name="certainLoss.price" />
			</td>
			<!-- 专修价 -->
			<td class="centertitle" style="width: 7%">
				<s:text name="certainLoss.marketPrice" />
			</td>
			<!-- 市场价 -->
			<td class="centertitle" style="width: 7%">
				<s:text name="certainLoss.factoryPrice" />
			</td>
			<!-- 副厂价  -->
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td colspan="14" align="center">
				<input type="button" name=buttonChangeRepairFactoryCode class=bigbutton value="<s:text name="button.resetChargedPrice.value"/>" onclick="changeRepairFactoryCode(this);" style="cursor: hand">
				&nbsp;&nbsp;
				<!-- 清除定损单价 -->
				<input type="button" name=buttonGetFittings class=bigbutton value="<s:text name="button.importPartsSystem.value"/>" onclick="getFittingsInfo('Compent',this);">
				<!-- 从配件系统导入 -->
				<%-- 测试增加隐藏域用 --%>
				<input type="button" class=smallbutton style="display: none" value="+" onclick="insertRowTableOfCertainLossComponent('Component','Component_Data',this)" name="buttoncomponent" style="cursor: hand">
			</td>
		</tr>
		<tr>
			<td colspan="14">
				<table border="0" align="center" cellpadding="4" cellspacing="1" class="title" width="100%">
					<!--tr>
										<td class='left'>
											运费:</td>
										<td class='right'>
											<input name="prpLcarLossSumTransFee" class="input" style='width:80px'
												value="<bean:write name='prpLcarLossDto' property='sumTransFee' filter='true'/>"></td>
										<td class='left'>
											税金:</td>
										<td class='right'>
											<input name="prpLcarLossSumTax" class="readonly" readonly style='width:80px' 
												value="0.0"></td>
										<td class='left'>
											管理费:</td>
										<td class='right'>
											<input name="prpLcarLossSumManager" class="input" style='width:80px'
												value="<bean:write name='prpLcarLossDto' property='sumManager' filter='true'/>">%</td>
									</tr-->
					<tr>
						<td class='left'>
							<s:text name="certainLoss.TotalResiduals" />:
						</td>
						<!-- 残值合计 -->
						<td class='right'>
							<input type="hidden" name="selectCarFittings">
							<input name="prpLcarLossSumRest" class="readonly" readonly style='width: 80px' value="<bean:write name='prpLcarLossDto' property='sumRest' filter='true'/>">
						</td>
						<td class='left'></td>
						<td class='right'></td>
						<td class='left'>
							<s:text name="certainLoss.changeTotals" />:
						</td>
						<!-- 换件费合计 -->
						<td class='right'>
							<input name="SumDefLoss2" class="readonly" readonly style='width: 80px' value="<bean:write name='prpLcarLossDto' property='sumDefLoss2' filter='true'/>">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</tfoot>
	<tbody>
		<logic:notEmpty name="prpLcarLossDto" property="prpLcomponentDtoList">
			<logic:iterate id="prpLcomponentDto" name="prpLcarLossDto" property="prpLcomponentDtoList">
				<tr>
					<td class="input" style="width: 8%">
						<input type="hidden" class="common" name="prpLcomponentLicenseNo" value="<bean:write name='prpLcomponentDto' property='licenseNo' filter='true'/>">
						<input type="hidden" class="common" name="prpLcomponentLossItemCode" value="<bean:write name='prpLcomponentDto' property='lossItemCode' filter='true'/>">
						<input type="hidden" class="readonly" readonly name="prpLcomponentSerialNo" value="<bean:write name='prpLcomponentDto' property='serialNo' filter='true'/>">
						<input type="hidden" class="readonly" readonly name="prpLcomponentIndId" value="">
						<input type="hidden" name="prpLcomponentKindCode" value="<bean:write name='prpLcomponentDto' property='kindCode' filter='true'/>">
						<!-- add by lidonghui start 2007-09-06 无责垫付案件北分情况下除去过滤设置 -->
						<logic:equal name="advance" value="1">
							<input type="text" name="prpLcomponentKindName" class="codecode" value="<bean:write name='prpLcomponentDto' property='kindName' filter='true'/>"
								ondblclick="code_CodeSelect(this,'PolicyKindCode','-1,0','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);"
								onkeyup="code_CodeSelect(this,'PolicyKindCode','-1,1','Y','Y',fm.policyNo.value+'|'+fm.prpLRegistRPolicyNo.value);">
						</logic:equal>
						<logic:notEqual name="advance" value="1">
							<input type="text" name="prpLcomponentKindName" class="codecode" value="<bean:write name='prpLcomponentDto' property='kindName' filter='true'/>"
								ondblclick="kindNameSelect(this,'prpLcomponentLossItemCode');" onkeyup="kindNameSelect(this,'prpLcomponentLossItemCode');">
						</logic:notEqual>
						<!-- add by lidonghui end 2007-09-06 无责垫付案件北分情况下除去过滤设置 -->
					</td>
					<td class="input" style="width: 8%">
						<input name="prpLcomponentCompCode" type="hidden" class=common value="<bean:write name='prpLcomponentDto' property='compCode' filter='true' />">
						<input name="prpLcomponentCompName" type="text" class=common value="<bean:write name='prpLcomponentDto' property='compName' filter='true' />">
					</td>
					<td class="input" style="width: 8%">
						<input name="prpLcomponentOriginalId" class=common value="<bean:write name='prpLcomponentDto' property='originalId' filter='true' />">
					</td>
					<td class="input" style="width: 7%">
						<input name="prpLcomponentSys4SPrice" type="hidden" class=common value="<bean:write name='prpLcomponentDto' property='sys4SPrice' filter='true' />">
						<input name="prpLcomponentNative4SPrice" type="text" readonly class=common value="<bean:write name='prpLcomponentDto' property='native4SPrice' filter='true' />">
					</td>
					<td class="input" style="width: 7%">
						<input name="prpLcomponentSysMarketPrice" type="hidden" class=common value="<bean:write name='prpLcomponentDto' property='sysMarketPrice' filter='true' />">
						<input name="prpLcomponentNativeMarketPrice" type="text" readonly class=common value="<bean:write name='prpLcomponentDto' property='nativeMarketPrice' filter='true' />">
					</td>
					<td class="input" style="width: 7%">
						<input name="prpLcomponentSysMatchPrice" type="hidden" class=common value="<bean:write name='prpLcomponentDto' property='sysMatchPrice' filter='true' />">
						<input name="prpLcomponentNativeMatchPrice" type="text" readonly class=common value="<bean:write name='prpLcomponentDto' property='nativeMatchPrice' filter='true' />">
					</td>
					<td class="input" style="width: 9%">
						<input name="prpLcomponentRepairFactoryFee" type="text" class=common value="<bean:write name='prpLcomponentDto' property='repairFactoryFee' filter='true' />">
					</td>
					<td class="input" style="width: 9%">
						<input type="hidden" name="prpLcomponentVerpCompPrice" value="0">
						<!--input name="prpLcomponentSumDefLoss" type="text" class=common 
				         		value="<bean:write name='prpLcomponentDto' property='sumDefLoss' filter='true' />"
				         		onchange="checkSumDefLoss(this);"-->
						<input name="prpLcomponentMaterialFee" type="text" class=common value="<bean:write name='prpLcomponentDto' property='materialFee' filter='true' />" onchange="checkSumDefLoss(this);">
					</td>
					<td class="input" style="width: 6%">
						<select name="prpLcomponentPriceType" class="three" style="width: 60px" onchange="changePriceType(this);setSumDefLoss(this);">
							<option value="S">
								<s:text name="certainLoss.price" />
							</option>
							<!-- 专修价 -->
							<option value="M">
								<s:text name="certainLoss.marketPrice" />
							</option>
							<!-- 市场价 -->
							<option value="O">
								<s:text name="certainLoss.factoryPrice" />
							</option>
							<!-- 副厂价 -->
						</select>
					</td>
					<td class="input" style="width: 5%">
						<input name="prpLcomponentQuantity" type="text" class=common value="<bean:write name='prpLcomponentDto' property='quantity' filter='true' />" onchange="setSumDefLoss(this);">
					</td>
					<td class="input" style="width: 9%">
						<input name="prpLcomponentRestFee" type="text" class=common value="<bean:write name='prpLcomponentDto' property='restFee' filter='true' />" onchange="setSumDefLoss(this);">
					</td>
					<td class="input" style="width: 8%">
						<input name="prpLcomponentSumDefLoss" type="text" class=common value="<bean:write name='prpLcomponentDto' property='sumDefLoss' filter='true' />">
					</td>
					<td class="input" style="width: 5%">
						<input name="prpLcomponentRemark" type="text" class=common value="<bean:write name='prpLcomponentDto' property='remark' filter='true' />">
						<input name="prpLcomponentFlag" type="hidden" value="">
					</td>
					<td class="input" style="width: 4%">
						<div>
							<input type=button name="buttonComponentDelete" class="smallbutton" onclick="deleteRow(this,'Component');setSumDefLoss(this);" value="-" style="cursor: hand">
						</div>
					</td>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</tbody>
</table>