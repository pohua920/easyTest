<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2004-06-01
* MODIFYLIST ：   Name       Date            Reason/Contents
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<span id="spanComponent" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="Component">
					<thead>
						<tr>
							<td class="subformtitle" colspan=12>
								<s:text name="certainLoss.ProjectCosts" />
								<!--零部件更换项目费用清单-->
							</td>
						</tr>
						<tr>
							<td style="display: none" class="centertitle" style="width:8%">
								<s:text name="db.prpDkind.kindCode" />
							</td>
							<!--险别代码 -->
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLCitemKind.kindName" />
							</td>
							<!--险别名称-->
							<td class="centertitle" style="width: 10%">
								<s:text name="certainLoss.partsParts" />
							</td>
							<!--部件部位-->
							<td class="centertitle" style="width: 10%">
								<s:text name="certainLoss.partName" />
							</td>
							<!--部件名称-->
							<!--Modify by chenrenda update end 20050413-->
							<td class="centertitle" style="width: 8%">
								<s:text name="db.prpLperson.quantity" />
							</td>
							<!--数量-->
							<td style="display: none" class="centertitle" style="width:8%">
								<s:text name="db.prpLcomponent.manHourFee" />
							</td>
							<!--工时费-->
							<td class="centertitle" style="width: 8%">
								<s:text name="certainLoss.reportedprices" />
							</td>
							<!--上报价格-->
							<td class="centertitle" style="width: 8%">
								<s:text name="certainLoss.systemPrice" />
							</td>
							<!--系统价-->
							<td class="centertitle" style="width: 8%">
								<s:text name="db.prpLpersonloss.sumRest" />
							</td>
							<!--残值-->
							<td class="centertitle" style="width: 8%">
								<s:text name="certainLoss.lossAmount" />
							</td>
							<!--定损金额-->
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpDshipclass.remark" />
							</td>
							<!--备注-->
							<td class="centertitle" style="width: 4%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=9 style="width: 96%">
								<s:text name="prompt.certainLoss.addRemove" />
								<!--(按"+"号键增加零部件更换项目费用信息，按"-"号键删除信息)-->
								<%
									if (flag == null || flag.length() < 1) {
									} else {
								%>
								<logic:equal name="prpLverifyLossDto" property="verifyOpinion" value="02">
									<input type="button" class=bigbutton value="<s:text name='button.DamageAmounts.value'/>" onclick="getVerifyComponent();" name="buttonAgreeVerifyComponentLoss" style="cursor: hand">
									<!--同意核损金额-->
								</logic:equal>
								<%
									}
								%>
							</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<!--<input type="button" value="+" onclick="insertRowTableComponent('Component','Component_Data',this)" name="buttonComponentInsert" style="cursor: hand">-->
									<input type="button" class=smallbutton value="+" onclick="insertThreeRowTableComponent('Component','Component_Data',this)" name="buttonComponentInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="12">
								<table border="0" align="center" cellpadding="4" cellspacing="1" class="title" width="100%">
									<tr>
										<td class='input' width="30%">
											<s:text name="certainLoss.residualvalueTotal" />
											:
											<input name="prpLcarLossSumRest" class='readonly' readonly="true" style='width: 140px' value="<%=prpLcarLossDto.getSumRest()%>">
											<!--残值总计-->
											<input type=hidden name="prpLcarLossSumManageFeeRate">
										</td>
										<td class='input' width="30%">
											<s:text name="certainLoss.accessoriesPrices" />
											:
											<!--配件的价格是否含-->
											<input type="checkbox" name="prpLcarLossCheckBox1" checked>
											<s:text name="certainLoss.managementFee" />
											<!--管理费-->
											<input type="checkbox" name="prpLcarLossCheckBox2" checked>
											<s:text name="certainLoss.scot" />
											<!--税金-->
											<input type="checkbox" name="prpLcarLossCheckBox3" checked>
											<s:text name="certainLoss.freight" />
											<!--运费-->
											<input name="prpLcarLossSumManageFeeRate" type="hidden" value="<%=prpLcarLossDto.getSumManageFeeRate()%>" onBlur="return getMaterialFee(this,2);">
										</td>
										<td class='title' width="40%">&nbsp;</td>
									</tr>
									<tr>
										<td class='title' style="display: none" width="30%">
											<s:text name="certainLoss.totalParts" />
											:
											<input class='readonly' readonly="true" style='width: 80px' name='SumManHourFee2'>
											<!--部件合计-->
										</td>
										<td class='title' style="display: none" width="30%">
											<s:text name="certainLoss.totalwork" />
											:
											<input class='readonly' readonly="true" style='width: 80px' name='SumMaterialFee2'>
											<!--工时合计-->
										</td>
										<td class='title' colspan=4 colwidth="100%">
											<s:text name="certainLoss.totalchange" />
											:
											<input class='readonly' readonly="true" style='width: 80px' name='SumDefLoss2'>
											<!--换件合计-->
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
							String compensatebackReadOnly1 = ""; //如果是由理算退回的，那么这行记录就应该显示得是只读的
						%>
						<%
							String compensatebackDiasable1 = ""; //如果是由理算退回的，那么这行记录就应该显示得是只读的
						%>
						<%
							String compensatebackStyle1 = ""; //如果是由理算退回的，那么这行记录就应该显示得是只读的
						%>
						<%
							componentNo = 0;
							if (prpLcomponentDto.getComponentList() != null) {
								for (int index1 = 0; index1 < prpLcomponentDto.getComponentList().size(); index1++) {
									PrpLcomponentDto prpLcomponentDto1 = (PrpLcomponentDto) prpLcomponentDto.getComponentList().get(index1);
									if (prpLcomponentDto1.getLossItemCode().equals(prpLcarLossDto.getLossItemCode())) {
										componentNo = Integer.parseInt(prpLcomponentDto1.getLossItemCode());
										//增加理算退回的判断
										compensatebackReadOnly1 = "";
										compensatebackDiasable1 = "";
										compensatebackStyle1 = "";
										if ("1".equals(prpLcomponentDto1.getCompensateBackFlag())) {
											compensatebackStyle1 = ";background:#CECECE";
											compensatebackReadOnly1 = "readOnly";
											compensatebackDiasable1 = "disabled";
										}
						%>
						<%
							//System.out.println("--------定核损标志 flag-----"+flag); 
										if (flag == null || flag.length() < 1) {
											//System.out.println("--------定核损标志 flag-----"+flag);
						%>
						<tr>
							<td class="input" style="display: none">
								<input type="hidden" name="carLossComponentLossItemCode" style="width: 20px" value="<%=componentNo - 1%>">
								<input type="text" name="prpLcomponentKindCode" class="codecode" style='width:40px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getKindCode()%>"
									<%if (compensatebackReadOnly1.equals("")) {%> ondblclick="code_CodeSelect(this,'PolicyKindCode');" onkeyup="code_CodeSelect(this,'PolicyKindCode');" <%}%>>
							</td>
							<td class="input">
								<input type="text" name="prpLcomponentKindName" class="codecode" style='width:70px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getKindName()%>"
									<%if (compensatebackReadOnly1.equals("")) {%> ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');" <%}%>>
							</td>
							<!--Modify by chenrenda update begin 20050413-->
							<td class="input">
								<select name="prpLcomponentPartCode" styleClass="three" style='width: 50px'>
									<%
										Iterator prpLcomponentList3 = collection.iterator();
														while (prpLcomponentList3.hasNext()) {
															LabelValueBean labelValueBean = (LabelValueBean) prpLcomponentList3.next();
															//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang
															if ("1".equals(prpLcomponentDto1.getCompensateBackFlag()) && (!labelValueBean.getValue().trim().equals(prpLcomponentDto1.getPartCode().trim())))
																continue;
									%>
									<option value="<%=labelValueBean.getValue()%>" <%=((labelValueBean.getValue().trim().equals(prpLcomponentDto1.getPartCode().trim())) ? "selected" : "")%>><%=labelValueBean.getLabel()%></option>
									<%
										}
									%>
								</select>
								<input type="hidden" name="prpLcomponentPartName" value="<%=prpLcomponentDto1.getPartName()%>">
							</td>
							<td class="input">
								<input name="prpLcomponentCompName" class="codename" style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> <%if (compensatebackReadOnly1.equals("")) {%>
									ondblclick="return openPrplComponentCompWin(Component_Data,this);" <%}%> value="<%=prpLcomponentDto1.getCompName()%>">
								<input type="hidden" name="prpLcomponentCompCode" value="<%=prpLcomponentDto1.getCompCode()%>">
							</td>
							<!--
                 <td class="input">                
                  <input name="prpLcomponentPartDesc" class=common style='width:70px'   value="<%=prpLcomponentDto1.getPartDesc()%>" >
                </td>
                
                <td class="input">   
                  <input type="text" name="prpLcomponentCompCode" class="codecode" style='width:40px' value="<%=prpLcomponentDto1.getCompCode()%>"                      
                      ondblclick= "code_CodeSelect(this,'CompCode');"
                      onkeyup= "code_CodeSelect(this,'CompCode');">         
                </td>
                <td class="input">   
                  <input type="text" name="prpLcomponentCompName" class="codecode" style='width:70px'   value="<%=prpLcomponentDto1.getCompName()%>"                    
  			             ondblclick="code_CodeSelect(this, 'CompCode','-1','always','none','post');"
  			             onkeyup= "code_CodeSelect(this, 'CompCode','-1','always','none','post');">      
                </td>
                 -->
							<td class="input">
								<input name="prpLcomponentQuantity" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getQuantity()%>"
									onBlur="return getSumDefLoss(this,2);">
							</td>
							<td class="input" style="display: none">
								<input name="prpLcomponentManHourFee" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getManHourFee()%>"
									onBlur="return getSumDefLoss(this,2);">
							</td>
							<td class="input">
								<input name="prpLcomponentMaterialFee" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getMaterialFee()%>"
									onBlur="return getMaterialFee(this,2);">
							</td>
							<td class="input">
								<input name="prpLcomponentQuotedPrice" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getQuotedPrice()%>">
								<!--onBlur="return getMaterialFee(this,2);">-->
							</td>
							<td class="input">
								<input name="prpLcomponentRestFee" class=common style='width:50px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getRestFee()%>"
									onBlur="getSumDefLoss(this,2);calculateSumRestFee(this);">
							</td>
							<td class="input">
								<input name="prpLcomponentMaterialFee" class="readonly" readonly style='width: 60px' value="<%=prpLcomponentDto1.getMaterialFee()%>">
							</td>
							<td class="input">
								<input name="prpLcomponentRemark" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getRemark()%>">
							</td>
							<input type="hidden" name="prpLcomponentSerialNo" value="<%=prpLcomponentDto1.getSerialNo()%>">
							<input type="hidden" name="prpLcomponentItemKindNo" value="<%=prpLcomponentDto1.getItemKindNo()%>">
							<input type="hidden" name="prpLcomponentLossItemCode" value="<%=prpLcomponentDto1.getLossItemCode()%>">
							<input type="hidden" name="prpLcomponentLicenseNo" value="<%=prpLcomponentDto1.getLicenseNo()%>">
							<input type="hidden" name="prpLcomponentLicenseColorCode" value="<%=prpLcomponentDto1.getLicenseColorCode()%>">
							<input type="hidden" name="prpLcomponentCarKindCode" value="<%=prpLcomponentDto1.getCarKindCode()%>">
							<input type="hidden" name="prpLcomponentMakeYear" value="<%=prpLcomponentDto1.getMakeYear()%>">
							<input type="hidden" name="prpLcomponentGearboxType" value="<%=prpLcomponentDto1.getGearboxType()%>">
							<input type="hidden" name="prpLcomponentQuoteCompanyGrade" value="<%=prpLcomponentDto1.getQuoteCompanyGrade()%>">
							<input type="hidden" name="prpLcomponentManageFeeRate" value="<%=prpLcomponentDto1.getManageFeeRate()%>">
							<input type="hidden" name="prpLcomponentRepairFactoryCode" value="<%=prpLcomponentDto1.getRepairFactoryCode()%>">
							<input type="hidden" name="prpLcomponentRepairFactoryName" value="<%=prpLcomponentDto1.getRepairFactoryName()%>">
							<input type="hidden" name="prpLcomponentHandlerCode" value="<%=prpLcomponentDto1.getHandlerCode()%>">
							<input type="hidden" name="prpLcomponentRepairStartDate" value="<%=prpLcomponentDto1.getRepairStartDate()%>">
							<input type="hidden" name="prpLcomponentRepairEndDate" value="<%=prpLcomponentDto1.getRepairEndDate()%>">
							<input type="hidden" name="prpLcomponentSanctioner" value="<%=prpLcomponentDto1.getSanctioner()%>">
							<input type="hidden" name="prpLcomponentApproverCode" value="<%=prpLcomponentDto1.getApproverCode()%>">
							<input type="hidden" name="prpLcomponentOperatorCode" value="<%=prpLcomponentDto1.getOperatorCode()%>">
							<input type="hidden" name="prpLcomponentQueryPrice" value="<%=prpLcomponentDto1.getQueryPrice()%>">
							<input type="hidden" name="prpLcomponentLossRate" value="<%=prpLcomponentDto1.getLossRate()%>">
							<input type="hidden" name="prpLcomponentCurrency" value="<%=prpLcomponentDto1.getCurrency()%>">
							<input type="hidden" name="prpLcomponentVeriRemark" value="<%=prpLcomponentDto1.getRemark()%>">
							<input type="hidden" name="prpLcomponentVeriQuantity" value="<%=prpLcomponentDto1.getVeriQuantity()%>">
							<input type="hidden" name="prpLcomponentVeriManHourFee" value="<%=prpLcomponentDto1.getVeriManHourFee()%>">
							<input type="hidden" name="prpLcomponentVeriMaterFee" value="<%=prpLcomponentDto1.getVeriMaterFee()%>">
							<input type="hidden" name="prpLcomponentVeriLossRate" value="<%=prpLcomponentDto1.getVeriLossRate()%>">
							<input type="hidden" name="prpLcomponentSumVeriLoss" value="<%=prpLcomponentDto1.getSumVeriLoss()%>">
							<input type="hidden" name="prpLcomponentVeriRestFee" value="<%=prpLcomponentDto1.getVeriRestFee()%>">
							<input type="hidden" name="prpLcomponentFlag" value="<%=prpLcomponentDto1.getFlag()%>">
							<input type="hidden" name="prpLcomponentCompensateBackFlag" value="<%=prpLcomponentDto1.getCompensateBackFlag()%>">
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonComponentDelete" class=smallbutton <%=compensatebackDiasable1%> onclick="deleteRowTableComponent(this,'Component',1,1)" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
						<%
							} else {
						%>
						<tr>
							<td class="input" style="display: none">
								<input type="hidden" name="carLossComponentLossItemCode" style="width: 20px" value="<%=componentNo - 1%>">
								<input type="text" name="prpLcomponentKindCode" class="codecode" style='width:40px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getKindCode()%>"
									<%if (compensatebackReadOnly1.equals("")) {%> ondblclick="code_CodeSelect(this,'PolicyKindCode');" onkeyup="code_CodeSelect(this,'PolicyKindCode');" <%}%>>
							</td>
							<td class="input">
								<input type="text" name="prpLcomponentKindName" class="codecode" style='width:70px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getKindName()%>"
									<%if (compensatebackReadOnly1.equals("")) {%> ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');" <%}%>>
							</td>
							<td class="input">
								<select name="prpLcomponentPartCode" styleClass="three" style='width: 50px'>
									<%
										Iterator prpLcomponentList2 = collection.iterator();
														while (prpLcomponentList2.hasNext()) {
															LabelValueBean labelValueBean = (LabelValueBean) prpLcomponentList2.next();
															//如果是理算退回的，那么只有当等於数据的那条记录才被增加到界面上,如果不是这样的，照常 lixiang
															if ("1".equals(prpLcomponentDto1.getCompensateBackFlag()) && (!labelValueBean.getValue().trim().equals(prpLcomponentDto1.getPartCode().trim())))
																continue;
									%>
									<option value="<%=labelValueBean.getValue()%>" <%=((labelValueBean.getValue().trim().equals(prpLcomponentDto1.getPartCode().trim())) ? "selected" : "")%>><%=labelValueBean.getLabel()%></option>
									<%
										}
									%>
								</select>
								<input type="hidden" name="prpLcomponentPartName" value="<%=prpLcomponentDto1.getPartName()%>">
							</td>
							<td class="input">
								<input name="prpLcomponentCompName" class="codename" style='width: 60px' <%if (compensatebackReadOnly1.equals("")) {%> ondblclick="return openPrplComponentCompWin(Component_Data,this);" <%}%>
									value="<%=prpLcomponentDto1.getCompName()%>">
								<input type="hidden" name="prpLcomponentCompCode" value="<%=prpLcomponentDto1.getCompCode()%>">
							</td>
							<td class="input">
								<input name="prpLcomponentQuantity" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getQuantity()%>"
									onBlur="return getSumDefLoss(this,2);">
							</td>
							<td class="input" style="display: none">
								<input name="prpLcomponentManHourFee" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getManHourFee()%>"
									onBlur="return getSumDefLoss(this,2);">
							</td>
							<td class="input">
								<input name="prpLcomponentMaterialFee" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getMaterialFee()%>"
									onBlur="return getMaterialFee(this,2);">
							</td>
							<td class="input">
								<input name="prpLcomponentQuotedPrice" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getQuotedPrice()%>">
								<!--onBlur="return getMaterialFee(this,2);">-->
							</td>
							<td class="input">
								<input name="prpLcomponentRestFee" class=common style='width:50px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getRestFee()%>"
									onBlur="calculateSumRestFee(this);getSumDefLoss(this,2);">
							</td>
							<td class="input">
								<input name="prpLcomponentMaterialFee" class="readonly" style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> readonly value="<%=prpLcomponentDto1.getMaterialFee()%>">
							</td>
							<td class="input">
								<input name="prpLcomponentRemark" class=common style='width:60px<%=compensatebackStyle1%>' <%=compensatebackReadOnly1%> value="<%=prpLcomponentDto1.getRemark()%>">
							</td>
							<input type="hidden" name="prpLcomponentSerialNo" value="<%=prpLcomponentDto1.getSerialNo()%>">
							<input type="hidden" name="prpLcomponentItemKindNo" value="<%=prpLcomponentDto1.getItemKindNo()%>">
							<input type="hidden" name="prpLcomponentLossItemCode" value="<%=prpLcomponentDto1.getLossItemCode()%>">
							<input type="hidden" name="prpLcomponentLicenseNo" value="<%=prpLcomponentDto1.getLicenseNo()%>">
							<input type="hidden" name="prpLcomponentLicenseColorCode" value="<%=prpLcomponentDto1.getLicenseColorCode()%>">
							<input type="hidden" name="prpLcomponentCarKindCode" value="<%=prpLcomponentDto1.getCarKindCode()%>">
							<input type="hidden" name="prpLcomponentMakeYear" value="<%=prpLcomponentDto1.getMakeYear()%>">
							<input type="hidden" name="prpLcomponentGearboxType" value="<%=prpLcomponentDto1.getGearboxType()%>">
							<input type="hidden" name="prpLcomponentQuoteCompanyGrade" value="<%=prpLcomponentDto1.getQuoteCompanyGrade()%>">
							<input type="hidden" name="prpLcomponentManageFeeRate" value="<%=prpLcomponentDto1.getManageFeeRate()%>">
							<input type="hidden" name="prpLcomponentRepairFactoryCode" value="<%=prpLcomponentDto1.getRepairFactoryCode()%>">
							<input type="hidden" name="prpLcomponentRepairFactoryName" value="<%=prpLcomponentDto1.getRepairFactoryName()%>">
							<input type="hidden" name="prpLcomponentHandlerCode" value="<%=prpLcomponentDto1.getHandlerCode()%>">
							<input type="hidden" name="prpLcomponentRepairStartDate" value="<%=prpLcomponentDto1.getRepairStartDate()%>">
							<input type="hidden" name="prpLcomponentRepairEndDate" value="<%=prpLcomponentDto1.getRepairEndDate()%>">
							<input type="hidden" name="prpLcomponentSanctioner" value="<%=prpLcomponentDto1.getSanctioner()%>">
							<input type="hidden" name="prpLcomponentApproverCode" value="<%=prpLcomponentDto1.getApproverCode()%>">
							<input type="hidden" name="prpLcomponentOperatorCode" value="<%=prpLcomponentDto1.getOperatorCode()%>">
							<input type="hidden" name="prpLcomponentQueryPrice" value="<%=prpLcomponentDto1.getQueryPrice()%>">
							<input type="hidden" name="prpLcomponentLossRate" value="<%=prpLcomponentDto1.getLossRate()%>">
							<input type="hidden" name="prpLcomponentCurrency" value="<%=prpLcomponentDto1.getCurrency()%>">
							<input type="hidden" name="prpLcomponentVeriLossRate" value="<%=prpLcomponentDto1.getVeriLossRate()%>">
							<input type="hidden" name="prpLcomponentFlag" value="<%=prpLcomponentDto1.getFlag()%>">
							<input type="hidden" name="prpLcomponentCompensateBackFlag" value="<%=prpLcomponentDto1.getCompensateBackFlag()%>">
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type="hidden" name="txtComponentBackFlag">
									<input type=button name="buttonComponentDelete" class=smallbutton <%=compensatebackDiasable1%> onclick="deleteRowTableComponent(this,'Component',1,2)" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
						<tr>
							<td class="input" colspan="3">&nbsp;</td>
							<td class="input">
								<input name="prpLcomponentVeriQuantity" class="readonly" readonly style='width: 70px' value="<%=prpLcomponentDto1.getVeriQuantity()%>">
							</td>
							<td class="input" style="display: none">
								<input name="prpLcomponentVeriManHourFee" class="readonly" readonly style='width: 70px' value="<%=prpLcomponentDto1.getVeriManHourFee()%>">
							</td>
							<td class="input">
								<input name="prpLcomponentVeriMaterFee" class="readonly" readonly style='width: 70px' value="<%=prpLcomponentDto1.getVeriMaterFee()%>">
							</td>
							<td class="input"></td>
							<td class="input">
								<input name="prpLcomponentVeriRestFee" class="readonly" readonly style='width: 70px' value="<%=prpLcomponentDto1.getVeriRestFee()%>">
							</td>
							<td class="input">
								<input name="prpLcomponentSumVeriLoss" class="readonly" readonly style='width: 70px' value="<%=prpLcomponentDto1.getSumVeriLoss()%>">
							</td>
							<td class="input">
								<input name="prpLcomponentVeriRemark" class="readonly" readonly style='width: 70px' value="<%=prpLcomponentDto1.getVeriRemark()%>">
							</td>
							<td class="input" style='width: 4%' align="center"></td>
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
