<table class="common" align="center" cellpadding="5" cellspacing="1">
	<tr>
		<%
				String readOnly = ""; // 标的车信息只读
				String carName = "三者车"; //区分标的车和三者车
				String butdisabled = ""; // 标的车不允许删除
			%>
		<logic:equal name="prpLcarLossDto" property="insureCarFlag" value="1">
			<%
					readOnly = " readOnly ";
						carName = "标的车";
						butdisabled = "disabled";
				%>
		</logic:equal>
		<%
				String sysAreaCode = user.getSysAreaCode();
				String localAreaCode = user.getComCode();
				if (localAreaCode.substring(0, 4).equals("4403")) {
					localAreaCode = "4403000000";
				} else {
					localAreaCode = localAreaCode.substring(0, 2) + "000000"; //取分公司机构代码
				}
				String showPriceFlag = user.getShowPriceFlag();//取用户价格权限查看的标记
				//System.out.println("++++++++++++++++++++++++++++"+localAreaCode);
			%>
		<input type="hidden" name="LocalAreaCode" value="<%=localAreaCode%>">
		<input type="hidden" name="ShowPriceFlag" value="<%=showPriceFlag%>">
		<td class="common" style="TEXT-ALIGN: center" colspan=2 style="width:30%" <%=readOnly%>>
			<font color=red><%=carName%></font>
		</td>
		<td class="left">
			<s:text name="db.prpCitem_car.licenseNo" />
			:
		</td>
		<!-- 车牌号码 -->
		<td class="right">
			<input type="hidden" name="prpLcarLossInsureCarFlag" value="<bean:write name='prpLcarLossDto' property='insureCarFlag' filter='true' />">
			<input class="common" type="text" name="prpLcarLossLossItemName" <%=readOnly%> value="<bean:write name='prpLcarLossDto' property='lossItemName' filter='true' />">
		</td>
		<td class="left"></td>
		<td class="right"></td>
	</tr>
	<tr>
		<td class="left">
			<s:text name="db.prpLregist.brandName" />
		</td>
		<!-- 厂牌型号 -->
		<td class="right">
			<input type="hidden" name="prpLcarLossModelCode" class="codecode" description="厂牌型号" value="<bean:write name='prpLcarLossDto' property='modelCode' filter='true' />"
				ondblclick="code_CodeSelect(this,'modelCode','0,1','Y');" onchange="code_CodeChange(this,'modelCode','0,1','Y');" onkeyup="code_CodeSelect(this,'modelCode','0,1','Y');">
			<input type="text" name="prpLcarLossBrandName" class="codename" maxlength=50 description="厂牌型号名称" value="<bean:write name='prpLcarLossDto' property='brandName' filter='true' />"
				ondblclick="code_CodeSelect(this,'modelCode','-1,0','Y','N');" onchange="code_CodeChange(this,'modelCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'modelCode','-1,0','Y','N');">
		</td>
		<td class="left">
			<s:text name="certainLoss.thirdCarLoss.carKind" />
		</td>
		<!-- 车辆种类 -->
		<td class="right">
			<html:select name="prpLcarLossDto" property="carKindCode">
				<html:options collection="carKindCodes" property="codeCode" labelProperty="codeCName" />
			</html:select>
			<input name="prpLcheckLossCarKindCode" type="hidden" value="<bean:write name='prpLcarLossDto' property='carKindCode' filter='true' />">
		</td>
		<td class="left"></td>
		<td class="right"></td>
	</tr>
	<tr>
		<td class="left">
			<s:text name="certainLoss.garageType" />
		</td>
		<!-- 修理厂类型 -->
		<td class="right">
			<select name="prpLcarLossRepairFactoryCode" class="three" style="width: 80%">
				<option value="" <logic:equal name='prpLcarLossDto' property='repairFactoryCode' value="">selected</logic:equal>></option>
				<option value="03" <logic:equal name='prpLcarLossDto' property='repairFactoryCode' value="03">selected</logic:equal>>
					<s:text name="certainLoss.factory2" />
				</option>
				<!-- 二类厂 -->
				<option value="02" <logic:equal name='prpLcarLossDto' property='repairFactoryCode' value="02">selected</logic:equal>>
					<s:text name="certainLoss.factory1" />
				</option>
				<!-- 一类厂 -->
				<option value="01" <logic:equal name='prpLcarLossDto' property='repairFactoryCode' value="01">selected</logic:equal>>
					<s:text name="certainLoss.shop" />
				</option>
				<!-- 4S店  -->
				<option value="04" <logic:equal name='prpLcarLossDto' property='repairFactoryCode' value="04">selected</logic:equal>>
					<s:text name="check.other" />
				</option>
				<!-- 其它 -->
			</select>
			<input name="prpLcarLossRepairFactoryType" type="hidden" value="">
		</td>
		<td class="left">
			<s:text name="db.prpLrepairFee.repairFactoryName" />
		</td>
		<!-- 修理厂名称 -->
		<td class="right">
			<input name="prpLcheckLossRepairFactoryName" class="input" value="<bean:write name='prpLcarLossDto' property='repairFactoryName' filter='true' />">
		</td>
		<td class="left">
			<s:text name="certainLoss.totalAmount" />
		</td>
		<!-- 总定损金额 -->
		<td class="right">
			<input name="prpLcarLossSumCertainLoss" class="readonly" readonly="true" value="<bean:write name='prpLcarLossDto' property='sumCertainLoss' filter='true' />">
		</td>
	</tr>
</table>