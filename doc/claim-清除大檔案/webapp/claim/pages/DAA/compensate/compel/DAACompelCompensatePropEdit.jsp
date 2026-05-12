<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：理赔组 陈杰
* CREATEDATE ： 2013-03-14
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<!--//==财产如下-->
<table cellpadding="0" cellspacing="1" class="common" style="display: none; width: 100%">
	<tr>
		<td algin="left">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="PropPersonImg" onclick="showPage(this,spanPropPerson);">強制保險車損/物損信息&nbsp;<font color="#FF0000">注意：如果不是“互碰自赔”，车牌号码栏位輸入的是标的车车牌号则为“无责代赔”</font><br>
			<span id="spanPropPerson" style="">
				<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
					<tr>
						<td colspan="6" bgcolor="#003399"></td>
					</tr>
					<span style="display: none">
						<table class="common" style="display: none" id="PropCompelFee_Data" cellspacing="1" cellpadding="5">
							<tbody>
								<tr>
									<td class='input' style="width: 8%">
										<select name="typetype" class="common" onclick="">
											<option selected value="2">物损</option>
											<option value="1">车损</option>
										</select>
									</td>
									<td class="input" style="width: 8%">
										<input type="hidden" name="propSerialNo" class="input">
										<input type="text" name="propLicenseNo" class="input" onblur="checkBeyondQuota(this);calCompelSumPropAndPerson();calSumDutyPaid();clearPrpLctext();">
									</td>
									<td class="input" style="width: 8%">
										<input type="text" name="propName" class="input" value="车辆">
									</td>
									<input type="hidden" name="propFeeTypeCode" class='codecode' style="width: 30%" ondblclick="code_CodeSelect(this,'PropertyFeeType','0,1','Y');"
										onchange="code_CodeChange(this, 'PropertyFeeType','0,1','Y');" onkeyup="code_CodeSelect(this,'PropertyFeeType','0,1','Y');" onblur="code_CodeChange(this,'PropertyFeeType',1);">
									<input type="hidden" name="propFeeTypeName" class='codename' style="width: 98%" value=" " ondblclick="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');"
										onchange="code_CodeChange(this, 'PropertyFeeType','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PropertyFeeType','-1,0','Y','N');">
									<td class="input" style="width: 6%">
										<input type="text" name="propSumLoss" class="input" onblur="if(checkBeyondQuota(this)){calCompelSumPropAndPerson(); calSumDutyPaid();clearPrpLctext();}">
									</td>
									<td class="input" style="width: 6%">
										<input type="text" name="propEliminate" class="input" onblur="calCompelSumPropAndPerson();calSumDutyPaid();clearPrpLctext();">
									</td>
									<td class="input" style="width: 6%">
										<input type="text" name="propSumDefPay" class="input" onchange="makeDisabledFalse(this);" onblur="if(checkBeyondQuota(this)){calCompelSumPropAndPerson();calSumDutyPaid();clearPrpLctext();}">
									</td>
									<td class="inputsubsub">
										<div>
											<input type=button name="buttonCertainLossExtDelete" class=smallbutton onclick="deleteRow(this,'PropCompelFee');calCompelSumPropAndPerson();calSumDutyPaid();clearPrpLctext();" value="-"
												style="cursor: hand">
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</span>
					<table class="common" id="PropCompelFee" cellspacing="1" cellpadding="5">
						<thead>
							<tr>
								<td class="centertitle" style="width: 5%">名 稱</td>
								<td class="centertitle" style="width: 5%">車牌號碼</td>
								<td class="centertitle" style="width: 5%">財物名稱</td>
								<!-- <td class="centertitle" style="width:8%">损失赔偿类型</td> -->
								<td class="centertitle" style="width: 6%">核定損失</td>
								<td class="centertitle" style="width: 6%">剔出金額</td>
								<td class="centertitle" style="width: 6%">核定賠償</td>
								<td class="centertitle" style="width: 5%">操作</td>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<td class="title" colspan="6">(按"+"號鍵增加損失部位信息，按"-"號鍵刪除信息)</td>
								<td class="title" align="right" style="width: 4%">
									<div align="center">
										<input type="button" value="+" class=smallbutton onclick="insertRowTable('PropCompelFee','PropCompelFee_Data',this);" name="buttonPropCompelInsert" style="cursor: hand">
									</div>
								</td>
							</tr>
						</tfoot>
						<tbody>
							<%
								request.setAttribute("KINDCODE_D_BZ", com.sinosoft.claim.common.ConstantCodes.KINDCODE_D_BZ);
							%>
							<c:if test="${not empty requestScope.prpLloss.prpLlossList}">
								<c:forEach items="${requestScope.prpLloss.prpLlossList}" var="prpLloss1">
									<c:if test="${prpLloss1.kindCode ==KINDCODE_D_BZ}">
										<tr>
											<td class='input' style="width: 7%">
												<select name="typetype" class="common" onclick="">
													<option value="1">車損</option>
													<option selected value="2">物損</option>
												</select>
											</td>
											<td class="input" style="width: 8%">
												<input type="hidden" name="propSerialNo" class="input">
												<input type="text" name="propLicenseNo" class="input" value="<c:out value='${prpLloss1.licenseNo}'/>"
													onblur="checkBeyondQuota(this);calCompelSumPropAndPerson();calSumDutyPaid();clearPrpLctext();">
											</td>
											<td class="input" style="width: 5%">
												<input type="text" name="propName" class="input" value="<c:out value='${prpLloss1.lossName}'/>">
											</td>
											<input name="propFeeTypeCode" class="input" type=hidden value="<c:out value='${prpLloss1.feeTypeCode}'/>">
											<input type="hidden" name="propFeeTypeName" class="input" value="<c:out value='${prpLloss1.feeTypeName}'/>">
											<td class="input" style="width: 6%">
												<input type="text" name="propSumLoss" class="input" value="<c:out value='${prpLloss1.sumLoss}'/>"
													onblur="if(checkBeyondQuota(this)){calCompelSumPropAndPerson();calSumDutyPaid();clearPrpLctext();}">
											</td>
											<td class="input" style="width: 6%">
												<input type="text" name="propEliminate" class="input" value="<c:out value='${prpLloss1.sumRest}'/>" onblur="calCompelSumPropAndPerson();calSumDutyPaid();clearPrpLctext();">
											</td>
											<td class="input" style="width: 6%">
												<input type="text" name="propSumDefPay" class="input" value="<c:out value='${prpLloss1.sumDefPay}'/>" onchange="makeDisabledFalse(this);"
													onblur="if(checkBeyondQuota(this)){calCompelSumPropAndPerson();calSumDutyPaid();clearPrpLctext();}">
											</td>
											<td class="inputsubsub">
												<div>
													<input type=button name="buttonCertainLossExtDelete" onclick="deleteRow(this,'PropCompelFee');calCompelSumPropAndPerson();calSumDutyPaid();clearPrpLctext();" class="smallbutton" value="-">
												</div>
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</table>
			</span>
		</td>
	</tr>
</table>
