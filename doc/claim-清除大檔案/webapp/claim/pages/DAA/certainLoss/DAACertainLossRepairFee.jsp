<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2004-06-01
* MODIFYLIST ：   Name       Date             Reason/Contents
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<span id="spanRepairFee" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" id="RepairFee" cellpadding="5" cellspacing="1">
					<thead>
						<tr>
							<td class="subformtitle" colspan=10>
								<s:text name="certainLoss.costList" />
							</td>
							<!--修理项目费用清单-->
						</tr>
						<tr>
							<td style="display: none" class="centertitle" style="width:10%">
								<s:text name="db.prpLcomponent.kindCode" />
								险别代码
							</td>
							<!---->
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLendor.kindName" />
							</td>
							<!--险别名称-->
							<!--Modify by chenrenda update begin 20050413-->
							<td class="centertitle" style="width: 10%">
								<s:text name="certainLoss.repairParts" />
							</td>
							<!--修理部位-->
							<td class="centertitle" style="width: 10%">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckAccessoryName" />
							</td>
							<!--零件(项目)名称-->
							<!--                  
				              <td class="centertitle" style="width:10%">修理项目代码</td>
				              <td class="centertitle" style="width:10%">修理项目名称</td>
				            -->
							<!--Modify by chenrenda update end 20050413-->
							<td class="centertitle" style="width: 10%">
								<s:text name="certainLoss.repairMethods" />
							</td>
							<!--修理方式-->
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLrepairFee.manHour" />
								工时
							</td>
							<!---->
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLrepairFee.manHourFee" />
								工时费
							</td>
							<!---->
							<td class="centertitle" style="display: none" style="width:10%">
								<s:text name="certainLoss.accessories" />
							</td>
							<!--辅料费-->
							<td class="centertitle" style="width: 10%">
								<s:text name="certainLoss.lossAmount" />
							</td>
							<!--定损金额-->
							<td class="centertitle" style="width: 16%">
								<s:text name="db.prpLcomponent.remark" />
							</td>
							<!--备注-->
							<td class="centertitle" style="width: 4%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=8 style="width: 96%">
								<s:text name="prompt.certainLoss.addRemoveCost" />
								)
								<!--(按"+"号键增加修理项目费用信息，按"-"号键删除信息-->
								<!--Modify by chenrenda update begin 核损价格异议时，自动将核损金额自动赋值到定损 20050421-->
								<%
									if (flag == null || flag.length() < 1) {
									} else {
								%>
								<logic:equal name="prpLverifyLossDto" property="verifyOpinion" value="02">
									<input type="button" class=bigbutton value="<s:text name='button.DamageAmounts.value'/>" onclick="getVerifyRepairFee();" name="buttonAgreeVerifyRepairFeeLoss" style="cursor: hand">
									<!--同意核损金额-->
								</logic:equal>
								<%
									}
								%>
							</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<!--Modify by chenrenda update begin 增加时可一次增加3项目 20050123-->
									<!--<input type="button" value="+" onclick="insertRowTableRepairFee('RepairFee','RepairFee_Data',this)" name="buttonRepairFee" style="cursor: hand">-->
									<input type="button" class=smallbutton value="+" onclick="insertThreeRowTableRepairFee('RepairFee','RepairFee_Data',this)" name="buttonRepairFee" style="cursor: hand">
									<!--Modify by chenrenda update end 20050123-->
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="10">
								<table cellpadding="6" cellspacing="1" class="common">
									<tr>
										<td class='title' style="display: none" colspan="1" width="30%">
											<s:text name="certainLoss.laborTotals" />
											:
											<input class='readonly' readonly="true" style='width: 80px' name='SumManHourFee1'>
											<!--工时费合计-->
										</td>
										<td class='title' style="display: none" colspan="1" width="30%">
											<s:text name="certainLoss.costTotals" />
											:
											<input class='readonly' readonly="true" style='width: 80px' name='SumMaterialFee1'>
											<!--材料费合计-->
										</td>
										<td class='title' colspan="4" width="40%">
											<s:text name="certainLoss.totalRepair" />
											:
											<input class='readonly' readonly="true" style='width: 80px' name='SumDefLoss1'>
											<!--修理合计-->
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</tfoot>
					</tfoot>
					<tbody>
						<%
							indexCertainLoss = 0;
						%>
						<%
							String compensatebackReadOnly = ""; //如果是由理算退回的，那么这行记录就应该显示得是只读的
						%>
						<%
							String compensatebackDiasable = ""; //如果是由理算退回的，那么这行记录就应该显示得是只读的
						%>
						<%
							String compensatebackStyle = ""; //如果是由理算退回的，那么这行记录就应该显示得是只读的
						%>
						<%
							repairFeeNo = 0;
							if (prpLrepairFeeDto.getRepairFeeList() != null) {
								for (int index1 = 0; index1 < prpLrepairFeeDto.getRepairFeeList().size(); index1++) {
									PrpLrepairFeeDto prpLrepairFeeDto1 = (PrpLrepairFeeDto) prpLrepairFeeDto.getRepairFeeList().get(index1);
									if (prpLrepairFeeDto1.getLossItemCode().equals(prpLcarLossDto.getLossItemCode())) {
										repairFeeNo = Integer.parseInt(prpLrepairFeeDto1.getLossItemCode());
										//增加理算退回的判断
										compensatebackReadOnly = "";
										compensatebackDiasable = "";
										compensatebackStyle = "";
										if ("1".equals(prpLrepairFeeDto1.getCompensateBackFlag())) {
											compensatebackStyle = ";background:#CECECE";
											compensatebackReadOnly = "readOnly";
											compensatebackDiasable = "disabled";
										}
						%>
						<%
							if (flag == null || flag.length() < 1) {
						%>
						<tr>
							<td class="input" style="display: none">
								<input type="hidden" name="carLossRepairFeeLossItemCode" style="width:20px<%=compensatebackStyle%>" <%=compensatebackReadOnly%> value="<%=repairFeeNo - 1%>">
								<input type="text" name="prpLrepairFeeKindCode" class="codecode" style='width: 40px' value="<%=prpLrepairFeeDto1.getKindCode()%>" <%if (compensatebackReadOnly.equals("")) {%>
									ondblclick="code_CodeSelect(this,'PolicyKindCode','-1','name','none','post');" onkeyup="code_CodeSelect(this,'PolicyKindCode','-1','name','none','post');" <%}%>>
								<!--modify by liyanjie 20051020 add :,'-1','name','none','post' -->
							</td>
							<td class="input">
								<input type="text" name="prpLrepairFeeKindName" class="codename" style='width:70px<%=compensatebackStyle%>' <%=compensatebackReadOnly%> value="<%=prpLrepairFeeDto1.getKindName()%>"
									<%if (compensatebackReadOnly.equals("")) {%> ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','selectb','post');"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','selectb','post');" <%}%>>
							</td>
							<!--Modify by chenrenda update begin 20050413-->
							<td class="input">
								<select name="prpLrepairFeePartCode" styleClass="three" onchange="getPrplRepairFeePartName(this);">
									<%
										Iterator prpLrepairFeeList = collection.iterator();
														while (prpLrepairFeeList.hasNext()) {
															LabelValueBean labelValueBean = (LabelValueBean) prpLrepairFeeList.next();
															//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang
															if ("1".equals(prpLrepairFeeDto1.getCompensateBackFlag()) && (!labelValueBean.getValue().trim().equals(prpLrepairFeeDto1.getPartCode().trim())))
																continue;
									%>
									<option value="<%=labelValueBean.getValue()%>" <%=((labelValueBean.getValue().trim().equals(prpLrepairFeeDto1.getPartCode().trim())) ? "selected" : "")%>><%=labelValueBean.getLabel()%></option>
									<%
										}
									%>
								</select>
								<input type="hidden" name="prpLrepairFeePartName" value="<%=prpLrepairFeeDto1.getPartName()%>">
							</td>
							<td class="input">
								<input name="prpLrepairFeeCompName" class="codename" style="width: 90%" <%if (compensatebackReadOnly.equals("")) {%> ondblclick="return openPrplRepairFeeCompWin(RepairFee_Data,this);" <%}%>
									value="<%=prpLrepairFeeDto1.getCompName()%>">
								<input type="hidden" name="prpLrepairFeeCompCode" value="<%=prpLrepairFeeDto1.getCompCode()%>">
							</td>
							<td class="input">
								<select name="prpLrepairFeeRepairType" styleClass="three" style="width: 70px">
									<%
										Iterator prpLrepairTypeList2 = repairColl.iterator();
														while (prpLrepairTypeList2.hasNext()) {
															PrpDcodeDto prpDcodeDto = (PrpDcodeDto) prpLrepairTypeList2.next();
															//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang
															if ("1".equals(prpLrepairFeeDto1.getCompensateBackFlag()) && !(prpDcodeDto.getCodeCode().trim().equals(prpLrepairFeeDto1.getRepairType().trim())))
																continue;
									%>
									<option value="<%=prpDcodeDto.getCodeCode()%>"
										<%=((prpDcodeDto.getCodeCode().trim().equals(prpLrepairFeeDto1.getRepairType().trim())) ? "selected" : "")%>><%=prpDcodeDto.getCodeCName()%></option>
									<%
										}
									%>
								</select>
							</td>
							<!--
                 <td class="input">   
                  <input type="text" name="prpLrepairFeeCompCode" class="codecode" style='width:40px' value="<%=prpLrepairFeeDto1.getCompCode()%>"                     
                      ondblclick= "code_CodeSelect(this,'CompCode');"
                      onkeyup= "code_CodeSelect(this,'CompCode');">          
                </td>
                <td class="input">   
                  <input type="text" name="prpLrepairFeeCompName" class="codename" style='width:70px' value="<%=prpLrepairFeeDto1.getCompName()%>"                     
  			             ondblclick="code_CodeSelect(this, 'CompCode','-1','always','none','post');"
  			             onkeyup= "code_CodeSelect(this, 'CompCode','-1','always','none','post');">      
                </td> 
                 -->
							<!--Modify by chenrenda update end 20050413-->
							<td class="input" style="display: none">
								<input name="prpLrepairFeeManHour" class=common style='width:70px<%=compensatebackStyle%>' <%=compensatebackReadOnly%> value="1" onBlur="return getSumDefLoss(this,1);">
							</td>
							<td class="input">
								<input name="prpLrepairFeeManHourUnitPrice" class="readonly" readonly style='width: 70px' value="<%=prpLrepairFeeDto1.getManHourUnitPrice()%>">
							</td>
							<td class="input">
								<input name="prpLrepairFeeMaterialFee" class=common style='width:70px<%=compensatebackStyle%>' <%=compensatebackReadOnly%> value="<%=prpLrepairFeeDto1.getMaterialFee()%>"
									onBlur="return getSumDefLoss(this,1);">
							</td>
							<td class="input">
								<input name="prpLrepairFeeMaterialFee" class="readonly" readonly style='width: 70px' value="<%=prpLrepairFeeDto1.getMaterialFee()%>">
							</td>
							<td class="input">
								<input name="prpLrepairFeeRemark" class=common style='width:100px<%=compensatebackStyle%>' <%=compensatebackReadOnly%> value="<%=prpLrepairFeeDto1.getRemark()%>">
							</td>
							<input type="hidden" name="prpLrepairFeeSerialNo" value="<%=prpLrepairFeeDto1.getSerialNo()%>">
							<input type="hidden" name="prpLrepairFeeItemKindNo" value="<%=prpLrepairFeeDto1.getItemKindNo()%>">
							<input type="hidden" name="prpLrepairFeeLossItemCode" value="<%=prpLrepairFeeDto1.getLossItemCode()%>">
							<input type="hidden" name="prpLrepairFeeLicenseNo" value="<%=prpLrepairFeeDto1.getLicenseNo()%>">
							<input type="hidden" name="prpLrepairFeeLicenseColorCode" value="<%=prpLrepairFeeDto1.getLicenseColorCode()%>">
							<input type="hidden" name="prpLrepairFeeCarKindCode" value="<%=prpLrepairFeeDto1.getCarKindCode()%>">
							<input type="hidden" name="prpLrepairFeeSanctioner" value="<%=prpLrepairFeeDto1.getSanctioner()%>">
							<input type="hidden" name="prpLrepairFeeApproverCode" value="<%=prpLrepairFeeDto1.getApproverCode()%>">
							<input type="hidden" name="prpLrepairFeeOperatorCode" value="<%=prpLrepairFeeDto1.getOperatorCode()%>">
							<input type="hidden" name="prpLrepairFeeManHourFee" value="<%=prpLrepairFeeDto1.getManHourFee()%>">
							<input type="hidden" name="prpLrepairFeeLossRate" value="<%=prpLrepairFeeDto1.getLossRate()%>">
							<input type="hidden" name="prpLrepairFeeCurrency" value="<%=prpLrepairFeeDto1.getCurrency()%>">
							<input type="hidden" name="prpLrepairFeeVeriRemark" value="<%=prpLrepairFeeDto1.getRemark()%>">
							<input type="hidden" name="prpLrepairFeeVeriManHour" value="<%=prpLrepairFeeDto1.getVeriManHour()%>">
							<input type="hidden" name="prpLrepairFeeVeriManUnitPrice" value="<%=prpLrepairFeeDto1.getVeriManUnitPrice()%>">
							<input type="hidden" name="prpLrepairFeeVeriManHourFee" value="<%=prpLrepairFeeDto1.getVeriManHourFee()%>">
							<input type="hidden" name="prpLrepairFeeVeriMaterQuantity" value="<%=prpLrepairFeeDto1.getVeriMaterQuantity()%>">
							<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice" value="<%=prpLrepairFeeDto1.getVeriMaterUnitPrice()%>">
							<input type="hidden" name="prpLrepairFeeVeriMaterialFee" value="<%=prpLrepairFeeDto1.getVeriMaterialFee()%>">
							<input type="hidden" name="prpLrepairFeeVeriLossRate" value="<%=prpLrepairFeeDto1.getVeriLossRate()%>">
							<input type="hidden" name="prpLrepairFeeVeriSumLoss" value="<%=prpLrepairFeeDto1.getVeriSumLoss()%>">
							<input type="hidden" name="prpLrepairFeeFlag" value="<%=prpLrepairFeeDto1.getFlag()%>">
							<input type="hidden" name="prpLrepairFeeCompensateBackFlag" value="<%=prpLrepairFeeDto1.getCompensateBackFlag()%>">
							<input type="hidden" name="prpLrepairFeeFirstSumDefLoss" value="<%=prpLrepairFeeDto1.getFirstSumDefLoss()%>">
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonRepairFeeDelete" class=smallbutton <%=compensatebackDiasable%> onclick="deleteRowTableRepairFee(this,'RepairFee',1,1)" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
						<%
							} else {
						%>
						<tr>
							<td class="input" style="display: none">
								<input type="hidden" name="carLossRepairFeeLossItemCode" style="width:20px<%=compensatebackStyle%>" <%=compensatebackReadOnly%> value="<%=repairFeeNo - 1%>">
								<input type="text" name="prpLrepairFeeKindCode" class="codecode" style='width: 40px' value="<%=prpLrepairFeeDto1.getKindCode()%>" <%if (compensatebackReadOnly.equals("")) {%>
									ondblclick="code_CodeSelect(this,'PolicyKindCode');" onkeyup="code_CodeSelect(this,'PolicyKindCode');" <%}%>>
							</td>
							<td class="input">
								<input type="text" name="prpLrepairFeeKindName" class="codename" style='width:70px<%=compensatebackStyle%>' <%=compensatebackReadOnly%> value="<%=prpLrepairFeeDto1.getKindName()%>"
									<%if (compensatebackReadOnly.equals("")) {%> ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','selectb','post');"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','selectb','post');" <%}%>>
							</td>
							<!--Modify by chenrenda update begin 20050413-->
							<td class="input">
								<select name="prpLrepairFeePartCode" styleClass="three" onchange="getPrplRepairFeePartName(this);">
									<%
										Iterator prpLrepairFeeList = collection.iterator();
														while (prpLrepairFeeList.hasNext()) {
															LabelValueBean labelValueBean = (LabelValueBean) prpLrepairFeeList.next();
															//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang
															if ("1".equals(prpLrepairFeeDto1.getCompensateBackFlag()) && (!labelValueBean.getValue().trim().equals(prpLrepairFeeDto1.getPartCode().trim())))
																continue;
									%>
									<option value="<%=labelValueBean.getValue()%>" <%=((labelValueBean.getValue().trim().equals(prpLrepairFeeDto1.getPartCode().trim())) ? "selected" : "")%>><%=labelValueBean.getLabel()%></option>
									<%
										}
									%>
								</select>
								<!--
                  <html:select name="prpLrepairFeeDto" property="prpLrepairFeePartCode" styleClass="three" onchange="getPrplRepairFeePartName(this);">
                    <html:options  collection="partCodeList" property="value" labelProperty="label"/>
                  </html:select>-->
								<input type="hidden" name="prpLrepairFeePartName" value="<%=prpLrepairFeeDto1.getPartName()%>">
							</td>
							<td class="input">
								<input name="prpLrepairFeeCompName" class="codename" style="width:90%<%=compensatebackStyle%>" <%=compensatebackReadOnly%> <%if (compensatebackReadOnly.equals("")) {%>
									ondblclick="return openPrplRepairFeeCompWin(RepairFee_Data,this);" <%}%> value="<%=prpLrepairFeeDto1.getCompName()%>">
								<input type="hidden" name="prpLrepairFeeCompCode" value="<%=prpLrepairFeeDto1.getCompCode()%>">
							</td>
							<td class="input">
								<select name="prpLrepairFeeRepairType" styleClass="three" style="width: 70px">
									<%
										Iterator prpLrepairTypeList3 = repairColl.iterator();
														while (prpLrepairTypeList3.hasNext()) {
															PrpDcodeDto prpDcodeDto = (PrpDcodeDto) prpLrepairTypeList3.next();
															//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang
															if ("1".equals(prpLrepairFeeDto1.getCompensateBackFlag()) && !(prpDcodeDto.getCodeCode().trim().equals(prpLrepairFeeDto1.getRepairType().trim())))
																continue;
									%>
									<option value="<%=prpDcodeDto.getCodeCode()%>"
										<%=((prpDcodeDto.getCodeCode().trim().equals(prpLrepairFeeDto1.getRepairType().trim())) ? "selected" : "")%>><%=prpDcodeDto.getCodeCName()%></option>
									<%
										}
									%>
								</select>
							</td>
							<!--
                 <td class="input">   
                  <input type="text" name="prpLrepairFeeCompCode" class="codecode" style='width:40px' value="<%=prpLrepairFeeDto1.getCompCode()%>"                     
                      ondblclick= "code_CodeSelect(this,'CompCode');"
                      onkeyup= "code_CodeSelect(this,'CompCode');">          
                </td>
                <td class="input">   
                  <input type="text" name="prpLrepairFeeCompName" class="codename" style='width:70px' value="<%=prpLrepairFeeDto1.getCompName()%>"                     
  			             ondblclick="code_CodeSelect(this, 'CompCode','-1','always','none','post');"
  			             onkeyup= "code_CodeSelect(this, 'CompCode','-1','always','none','post');">      
                </td> 
                 -->
							<!--Modify by chenrenda update end 20050413-->
							<td class="input" style="display: none">
								<input name="prpLrepairFeeManHour" class=common style='width:70px<%=compensatebackStyle%>' <%=compensatebackReadOnly%> value="1" onBlur="return getSumDefLoss(this,1);">
							</td>
							<td class="input">
								<input name="prpLrepairFeeManHourUnitPrice" class="readonly" readonly style='width: 70px' value="<%=prpLrepairFeeDto1.getManHourUnitPrice()%>">
							</td>
							<td class="input">
								<input name="prpLrepairFeeMaterialFee" class=common style='width:70px<%=compensatebackStyle%>' <%=compensatebackReadOnly%> value="<%=prpLrepairFeeDto1.getMaterialFee()%>"
									onBlur="return getSumDefLoss(this,1);">
							</td>
							<td class="input">
								<input name="prpLrepairFeeMaterialFee" class="readonly" readonly style='width: 70px' value="<%=prpLrepairFeeDto1.getMaterialFee()%>">
							</td>
							<td class="input">
								<input name="prpLrepairFeeRemark" class=common style='width:100px<%=compensatebackStyle%>' <%=compensatebackReadOnly%> value="<%=prpLrepairFeeDto1.getRemark()%>">
							</td>
							<input type="hidden" name="prpLrepairFeeSerialNo" value="<%=prpLrepairFeeDto1.getSerialNo()%>">
							<input type="hidden" name="prpLrepairFeeItemKindNo" value="<%=prpLrepairFeeDto1.getItemKindNo()%>">
							<input type="hidden" name="prpLrepairFeeLossItemCode" value="<%=prpLrepairFeeDto1.getLossItemCode()%>">
							<input type="hidden" name="prpLrepairFeeLicenseNo" value="<%=prpLrepairFeeDto1.getLicenseNo()%>">
							<input type="hidden" name="prpLrepairFeeLicenseColorCode" value="<%=prpLrepairFeeDto1.getLicenseColorCode()%>">
							<input type="hidden" name="prpLrepairFeeCarKindCode" value="<%=prpLrepairFeeDto1.getCarKindCode()%>">
							<input type="hidden" name="prpLrepairFeeSanctioner" value="<%=prpLrepairFeeDto1.getSanctioner()%>">
							<input type="hidden" name="prpLrepairFeeApproverCode" value="<%=prpLrepairFeeDto1.getApproverCode()%>">
							<input type="hidden" name="prpLrepairFeeOperatorCode" value="<%=prpLrepairFeeDto1.getOperatorCode()%>">
							<input type="hidden" name="prpLrepairFeeManHourFee" value="<%=prpLrepairFeeDto1.getManHourFee()%>">
							<input type="hidden" name="prpLrepairFeeLossRate" value="<%=prpLrepairFeeDto1.getLossRate()%>">
							<input type="hidden" name="prpLrepairFeeCurrency" value="<%=prpLrepairFeeDto1.getCurrency()%>">
							<input type="hidden" name="prpLrepairFeeVeriManHourFee" value="<%=prpLrepairFeeDto1.getVeriManHourFee()%>">
							<input type="hidden" name="prpLrepairFeeVeriMaterQuantity" value="<%=prpLrepairFeeDto1.getVeriMaterQuantity()%>">
							<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice" value="<%=prpLrepairFeeDto1.getVeriMaterUnitPrice()%>">
							<input type="hidden" name="prpLrepairFeeVeriLossRate" value="<%=prpLrepairFeeDto1.getVeriLossRate()%>">
							<input type="hidden" name="prpLrepairFeeFlag" value="<%=prpLrepairFeeDto1.getFlag()%>">
							<input type="hidden" name="prpLrepairFeeCompensateBackFlag" value="<%=prpLrepairFeeDto1.getCompensateBackFlag()%>">
							<input type="hidden" name="prpLrepairFeeFirstSumDefLoss" value="<%=prpLrepairFeeDto1.getFirstSumDefLoss()%>">
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type="hidden" name="txtRepairFeeBackFlag">
									<input type=button name="buttonRepairFeeDelete" class="smallbutton" <%=compensatebackDiasable%> onclick="deleteRowTableRepairFee(this,'RepairFee',1,2)" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
						<tr>
							<td class="input" colspan="4">&nbsp;</td>
							<td class="input">&nbsp;</td>
							<td class="input">
								<input name="prpLrepairFeeVeriManHour" class="readonly" only style='width: 70px' value="<%=prpLrepairFeeDto1.getVeriSumLoss()%>">
							</td>
							<td class="input" style="display: none">
								<input name="prpLrepairFeeVeriManUnitPrice" class="readonly" only style='width: 70px' value="<%=prpLrepairFeeDto1.getVeriManUnitPrice()%>">
							</td>
							<td class="input" style="display: none">
								<input name="prpLrepairFeeVeriMaterialFee" class="readonly" only style='width: 70px' value="<%=prpLrepairFeeDto1.getVeriMaterialFee()%>">
							</td>
							<td class="input">
								<input name="prpLrepairFeeVeriSumLoss" class="readonly" readonly style='width: 70px' value="<%=prpLrepairFeeDto1.getVeriSumLoss()%>">
							</td>
							<td class="input">
								<input name="prpLrepairFeeVeriRemark" class="readonly" only style='width: 100px' value="<%=prpLrepairFeeDto1.getVeriRemark()%>">
							</td>
							<td class="input" style='width: 4%' align="center">
						</tr>
						<%
							}
									}
								}
							}
						%>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
