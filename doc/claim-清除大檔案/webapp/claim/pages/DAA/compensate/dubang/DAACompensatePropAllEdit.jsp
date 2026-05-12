<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%
	
%>
<!--//==????-->
<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="PropPersonImg" onclick="showPage(this,spanPropPerson);">
<s:text name="compensate.dubang.commerPropThree" />
<br>
<%-- ?????? --%>
<span id="spanPropPerson" style="display: none">
	<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
		<tr>
			<td colspan="6" bgcolor="#003399"></td>
		</tr>
		<tr>
			<td rowspan="3" class='title' style="width: 10%">
				<s:text name="compensate.dubang.damageProperty" />
			</td>
			<%-- ???? --%>
			<td colspan="4" class="common" style="width: 100%">
				<span style="display: none">
					<table class="common" style="display: none" id="PropBFee_Data" cellspacing="1" cellpadding="5">
						<tbody>
							<tr>
								<td class="input" style="width: 18%">
									<input type="hidden" name="propSerialNo" class="input">
									<input type="text" name="propLicenseNo" class="input">
								</td>
								<td class="input" style="width: 18%">
									<input type="text" name="propName" class="input" value="??">
								</td>
								<td class="input" style="width: 18%">
									<input type="hidden" name="propFeeTypeCode" class='codecode' style="width: 30%" ondblclick="code_CodeSelect(this,'PropertyFeeType');" onchange="code_CodeChange(this, 'PropertyFeeType');"
										onkeyup="code_CodeSelect(this,'PropertyFeeType');" onblur="code_CodeChange(this,'PropertyFeeType',1);">
									<input name="propFeeTypeName" class='codename' style="width: 98%" value=" " ondblclick="code_CodeSelect(this, 'PropertyFeeType','-1','name','none','post');"
										onchange="code_CodeChange(this, 'PropertyFeeType','-1','name','none','post');" onkeyup="code_CodeSelect(this, 'PropertyFeeType','-1','name','none','post');">
								</td>
								<td class="input" style="width: 12%">
									<input type="text" name="propSumLoss" class="input" onblur="calSumPropAndPerson();">
								</td>
								<td class="input" style="width: 10%">
									<input type="text" name="propEliminate" class="input" onblur="calSumPropAndPerson();">
								</td>
								<td class="input" style="width: 10%">
									<input type="text" name="propSumDefPay" class="input" onblur="calSumPropAndPerson();">
								</td>
								<td class="input" style='width: 4%' align="center">
									<div>
										<input type=button name="buttonCertainLossExtDelete" class=smallbutton onclick="deleteRow(this,'PropBFee')" value="-" style="cursor: hand">
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</span>
				<table class="common" id="PropBFee" cellspacing="1" cellpadding="5">
					<thead>
						<tr>
							<td class="centertitle" style="width: 20%">
								<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyLicenseNo" />
							</td>
							<%-- ???? --%>
							<td class="centertitle" style="width: 20%">
								<s:text name="compensate.propertyName" />
							</td>
							<%-- ???? --%>
							<td class="centertitle" style="width: 20%">
								<s:text name="compensate.lossDetail" />
							</td>
							<%-- ???? --%>
							<td class="centertitle" style="width: 15%">
								<s:text name="compensate.approvedLoss" />
							</td>
							<%-- ???? --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.compel.removAmount" />
							</td>
							<%-- ???? --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="compensate.approvedCompen" />
							</td>
							<%-- ???? --%>
							<td class="centertitle" style="width: 5%">
								<s:text name="certify.operate" />
							</td>
							<%-- ?? --%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan=5>
								<s:text name="certainLoss.thirdCarLoss.promptLoss" />
							</td>
							<%-- (?"+"??????????,?"-"??????) --%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertRowTable('PropBFee','PropBFee_Data',this);" name="buttonPersonLossCareFeeInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					</tfoot>
					<tbody>
						<%%>
						<logic:notEmpty name="prpLlossDto" property="prpLlossList">
							<logic:iterate id="prpLlossList1" name="prpLlossDto" property="prpLlossList">
								<logic:equal name="prpLlossList1" property="kindCode" value="B">
									<tr>
										<td class="input" style="width: 18%">
											<input type="hidden" name="propSerialNo" class="input">
											<input type="text" name="propLicenseNo" class="input" value="<bean:write name='prpLlossList1' property='licenseNo'/>">
										</td>
										<td class="input" style="width: 18%">
											<input type="text" name="propName" class="input" value="<bean:write name='prpLlossList1' property='lossName'/>">
										</td>
										<td class="input" style="width: 18%">
											<input name="propFeeTypeCode" class="input" type=hidden value="<bean:write name='prpLlossList1' property='feeTypeCode'/>">
											<input type="text" name="propFeeTypeName" class="input" value="<bean:write name='prpLlossList1' property='feeTypeName'/>">
										</td>
										<td class="input" style="width: 12%">
											<input type="text" name="propSumLoss" class="input" value="<bean:write name='prpLlossList1' property='sumLoss'/>" onblur="calSumPropAndPerson();">
										</td>
										<td class="input" style="width: 10%">
											<input type="text" name="propEliminate" class="input" value="<bean:write name='prpLlossList1' property='sumRest'/>" onblur="calSumPropAndPerson();">
										</td>
										<td class="input" style="width: 10%">
											<input type="text" name="propSumDefPay" class="input" value="<bean:write name='prpLlossList1' property='sumRest'/>" onblur="calSumPropAndPerson();">
										</td>
										<td class="input" style='width: 4%' align="center">
											<div>
												<input type=button name="buttonCertainLossExtDelete" onclick="deleteRow(this,'PersonLossCareFee')" class="smallbutton" value="-">
											</div>
										</td>
									</tr>
								</logic:equal>
							</logic:iterate>
						</logic:notEmpty>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</span>
