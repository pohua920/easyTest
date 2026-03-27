<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2013-03-06
* MODIFYLIST ：   Name       Date             Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<span style="display: none">
	<table class="common" style="display: none" id="RepairFee_Data" cellpadding="5" cellspacing="1">
		<tbody>
			<tr name="TrRepairFee">
				<td class="input" style="width: 20%;">
					<input type="hidden" name="carLossRepairFeeLossItemCode" style="width: 20px">
					<input type="hidden" name="prpLrepairFeeKindCode" class="codecode" style="width: 40px">
					<input type="text" name="prpLrepairFeeKindName" class="codename" style='width: 100%'
						ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
						onkeyup="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
					<input type="hidden" name="prpLrepairFeeItemKindNo" value="0">
				</td>
				<td class="input" style="width: 30%">
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type="text" name="prpLrepairFeeCompName" class=common style="width: 100%" maxlength="100">
					<input type="hidden" name="prpLrepairFeeCompCode" value="9999">
				</td>
				<td class="input" style="width: 15%">
					<select name="prpLrepairFeeRepairType" style="width: 100%">
						<c:forEach items="${requestScope.repairTypes}" var="prpDcode">
							<option value="${prpDcode.id.codeCode}">
								<c:out value="${prpDcode.codeCName}" />
							</option>
						</c:forEach>
					</select>
				</td>
				<td class="input" style="width: 10%;">
					<input type="text" name="prpLrepairFeeSumDefLoss" onchange="getRepairFeeSumLoss(this);" class="common" style='width:100%;'  value="">
					<input type="hidden" name="prpLrepairFeeManHour" maxlength=10 class=common style='width:100%;'   value="1">
					<input  type="hidden" name="prpLrepairFeeManHourUnitPrice" maxlength=10 class="common" style='width:100%;'  value="1" >
				</td>
				<td class="input" style="width: 20%">
					<input type="text" name="prpLrepairFeeRemark" maxlength=60 class=common style='width: 100%'>
					<input type="hidden" name="prpLrepairFeeFirstSumDefLoss" class="readonly" readonly>
					<input type="hidden" name="prpLrepairFeeSerialNo">
					<input type="hidden" name="prpLrepairFeeLossItemCode">
					<input type="hidden" name="prpLrepairFeeLicenseNo">
					<input type="hidden" name="prpLrepairFeeLicenseColorCode">
					<input type="hidden" name="prpLrepairFeeCarKindCode">
					<input type="hidden" name="prpLrepairFeeSanctioner">
					<input type="hidden" name="prpLrepairFeeApproverCode">
					<input type="hidden" name="prpLrepairFeeOperatorCode">
					<input type="hidden" name="prpLrepairFeeManHourFee">
					<input type="hidden" name="prpLrepairFeeMaterialFee">
					<input type="hidden" name="prpLrepairFeeLossRate">
					<input type="hidden" name="prpLrepairFeeCurrency">
					<input type="hidden" name="prpLrepairFeeVeriRemark">
					<input type="hidden" name="prpLrepairFeeVeriManHour">
					<input type="hidden" name="prpLrepairFeeVeriManUnitPrice">
					<input type="hidden" name="prpLrepairFeeVeriManHourFee">
					<input type="hidden" name="prpLrepairFeeVeriMaterQuantity">
					<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice">
					<input type="hidden" name="prpLrepairFeeBackCheckRemark">
					<input type="hidden" name="prpLrepairFeeVeriMaterialFee">
					<input type="hidden" name="prpLrepairFeeVeriLossRate">
					<input type="hidden" name="prpLrepairFeeVeriSumLoss">
					<input type="hidden" name="prpLrepairFeeFlag">
					<input type="hidden" name="prpLrepairFeeIndId">
					<input type="hidden" name="prpLrepairFeeCompensateBackFlag">
					<input type="hidden" name="prpLrepairFeePartCode" value="1">
					<input type="hidden" name="prpLrepairFeePartName" value="前部">
				</td>
				<td class="input" style='width: 5%;' align="center">
					<div>
						<input type=button name="buttonRepairFeeDelete" class="smallbutton" onclick="deleteRow(this,'RepairFee','prpLrepairFeeSerialNo');sumComponentRepairFee();" value="-" style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span> 
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<span id="spanRepairFee" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" id="RepairFee" cellpadding="5" cellspacing="1">
					<thead>
						<tr>
							<td class="subformtitle" colspan=6>
								<s:text name="certainLoss.costList" />
							</td>
							<!--修理项目费用清单-->
						</tr>
						<tr>
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpDrate.kindName" />
							</td>
							<!--险别名称-->
							<td class="centertitle" style="width: 30%">
								<s:text name="certainLoss.repairItemName" />
							</td>
							<!--修理项名称-->
							<td class="centertitle" style="width: 15%">
								<s:text name="certainLoss.types" />
							</td>
							<!--工时-->
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLrepairFee.manHourFee" />
							</td>
							<!--初始工时费-->
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpLrepairFee.remark" />
							</td>
							<!--备注-->
							<td class="centertitle" style="width: 5%;">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan=6>
								<table class="common" cellspacing="1" cellpadding="0" style="width: 100%;">
									<tbody>
										<tr>
											<td class="title" colspan="5" style="width: 95%" align="left">
												<s:text name="prompt.certainLoss.addRemoveCost" />
												<!--(按"+"号键增加修理项目费用信息，按"-"号键删除信息)-->
											</td>
											<td class="title" align="right" style="width: 5%">
												<div align="center">
													<input type="button" class=smallbutton value="+" onclick="insertRow('RepairFee',this,'prpLrepairFeeSerialNo')" name="buttonRepairFee" style="cursor: hand">
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<table cellpadding="4" cellspacing="1" class="common" align="center" style="width: 100%;">
									<tr>
										<td class='title' colspan="2" width="40%" align="center">
											<s:text name="certainLoss.laborTotals" />:
											<input type="text" class='readonly' readonly="true" style='width: 80px' name='SumDefLoss1' value="0">
										</td>
										<!--工时费合计-->
										<td class='title' colspan="3" width="40%" align="center">
											<s:text name="certainLoss.totalApproved" />:
											<input type="text" class='readonly' readonly="true" style='width: 80px' name='SumVerifyLoss1' value="0">
										</td>
										<!--核定工时费合计-->
									</tr>
								</table>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach items="${requestScope.prpLrepairFee.repairFeeList}" var="prpLrepairFee1">
							<tr name="TrRepairFee">
								<td class="input" style="width: 20%;">
									<input type="hidden" name="carLossRepairFeeLossItemCode" style="width: 20px" value="${pageScope.repairFeeNo - 1}">
									<input type="hidden" name="prpLrepairFeeKindCode" class="codecode" style='width:40px '  value="${prpLrepairFee1.kindCode}"
				                       ondblclick= "code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
				                       onkeyup= "code_CodeSelect(this,'PolicyKindCodeForCar','0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);">
									<input type="text" name="prpLrepairFeeKindName" class="codename"  style='width:100%;' value="${prpLrepairFee1.kindName}"
				  			             ondblclick="code_CodeSelect(this,'PolicyKindCodeForCar','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" 
				  			             onkeyup= "code_CodeSelect(this,'PolicyKindCodeForCar','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" >
				  			        <input type="hidden" name="prpLrepairFeeItemKindNo" value="${prpLrepairFee1.itemKindNo}">
								</td>
								<td class="input" style="width: 30%;">
									<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
									<input name="prpLrepairFeeCompName" class="common"  style="width:100%; <c:out value=""/>" maxlength="100" value="${prpLrepairFee1.compName}">
									<input type="hidden" name="prpLrepairFeeCompCode" value="${prpLrepairFee1.compCode}">
								</td>
								<td class="input" style="width: 15%;">
									<select name="prpLrepairFeeRepairType" style="width:100%;">
										<c:forEach items="${requestScope.repairTypes}" var="prpDcodeDto">
											<option value="${prpDcodeDto.id.codeCode}"
												<c:if test="${prpDcodeDto.id.codeCode==pageScope.prpLrepairFee1.repairType}">
					                             <c:out value="selected"/>
					                           </c:if>>
												<c:out value="${prpDcodeDto.codeCName}" />
											</option>
										</c:forEach>
									</select>
								</td>
								<td class="input" style="width: 10%;">
									<input type="text" name="prpLrepairFeeSumDefLoss" onchange="getRepairFeeSumLoss(this);" class="common" style='width:100%;'  value="<fmt:formatNumber value='${prpLrepairFee1.sumDefLoss}' pattern='#'/>">
									<input type="hidden" name="prpLrepairFeeManHour" maxlength=10 class=common style='width:100%;'   value="${prpLrepairFee1.manHour}" >
									<input  type="hidden" name="prpLrepairFeeManHourUnitPrice" maxlength=10 class="common" style='width:100%;'  value="<fmt:formatNumber value='${prpLrepairFee1.manHourUnitPrice}' pattern='#'/>">
								</td>
								<td class="input" style="width: 20%;">
									<input type="text" name="prpLrepairFeeRemark" class=common style='width:100%;'  value="${prpLrepairFee1.remark}">
									<input type="hidden" name="prpLrepairFeeFirstSumDefLoss" class="readonly" readonly style='width:100%;'  value="<fmt:formatNumber value='${prpLrepairFee1.firstSumDefLoss}' pattern='#'/>">
									<input type="hidden" name="prpLrepairFeeSerialNo" value="${prpLrepairFee1.id.serialNo}">
									<input type="hidden" name="prpLrepairFeeLossItemCode" value="${prpLrepairFee1.id.lossItemCode}">
									<input type="hidden" name="prpLrepairFeeLicenseNo" value="${prpLrepairFee1.licenseNo}">
									<input type="hidden" name="prpLrepairFeeLicenseColorCode" value="${prpLrepairFee1.licenseColorCode}">
									<input type="hidden" name="prpLrepairFeeCarKindCode" value="${prpLrepairFee1.carKindCode}">
									<input type="hidden" name="prpLrepairFeeSanctioner" value="${prpLrepairFee1.sanctioner}">
									<input type="hidden" name="prpLrepairFeeApproverCode" value="${prpLrepairFee1.approverCode}">
									<input type="hidden" name="prpLrepairFeeOperatorCode" value="${prpLrepairFee1.operatorCode}">
									<input type="hidden" name="prpLrepairFeeManHourFee" value="${prpLrepairFee1.manHourFee}">
									<input type="hidden" name="prpLrepairFeeMaterialFee" value="${prpLrepairFee1.materialFee}">
									<input type="hidden" name="prpLrepairFeeLossRate" value="${prpLrepairFee1.lossRate}">
									<input type="hidden" name="prpLrepairFeeCurrency" value="${prpLrepairFee1.currency}">
									<input type="hidden" name="prpLrepairFeeVeriRemark" value="${prpLrepairFee1.remark}">
									<input type="hidden" name="prpLrepairFeeVeriManHour" value="${prpLrepairFee1.veriManHour}">
									<input type="hidden" name="prpLrepairFeeVeriManUnitPrice" value="${prpLrepairFee1.veriManUnitPrice}">
									<input type="hidden" name="prpLrepairFeeVeriManHourFee" value="${prpLrepairFee1.veriManHourFee}">
									<input type="hidden" name="prpLrepairFeeVeriMaterQuantity" value="${prpLrepairFee1.veriMaterQuantity}">
									<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice" value="${prpLrepairFee1.veriMaterUnitPrice}">
									<input type="hidden" name="prpLrepairFeeVeriMaterialFee" value="${prpLrepairFee1.veriMaterialFee}">
									<input type="hidden" name="prpLrepairFeeVeriLossRate" value="${prpLrepairFee1.veriLossRate}">
									<input type="hidden" name="prpLrepairFeeVeriSumLoss" value="${prpLrepairFee1.veriSumLoss}">
									<input type="hidden" name="prpLrepairFeeBackCheckRemark" value="${prpLrepairFee1.backCheckRemark}">
									<input type="hidden" name="prpLrepairFeeFlag" value="${prpLrepairFee1.flag}">
									<input type="hidden" name="prpLrepairFeeIndId" value="${prpLrepairFee1.indId}">
									<input type="hidden" name="prpLrepairFeeCompensateBackFlag" value="${prpLrepairFee1.compensateBackFlag}">
									<input type="hidden" name="prpLrepairFeePartCode" value="${prpLrepairFee1.partCode}">
									<input type="hidden" name="prpLrepairFeePartName" value="${prpLrepairFee1.partName}">
								</td>
								<td class="input" style='width: 5%;' align="center">
									<div>
										<input type=button name="buttonRepairFeeDelete" class=smallbutton <c:out value="${pageScope.compensatebackDiasable}"/>
											onclick="deleteRow(this,'RepairFee','prpLrepairFeeSerialNo');sumComponentRepairFee();" value="-" style="cursor: hand">
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
