<%--
****************************************************************************
* DESC       ： 人员伤亡清单页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-03-13  
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<span style="display:" id="SpanPerson" cellspacing="1" cellpadding="1">
	<span style="display: none">
		<table class="common" style="display: none" id="personFeeLoss_Data" cellpadding="5" cellspacing="1">
			<tbody>
				<tr name="trPersonFeeLoss">
					<td class="input" style="width: 12%">
						<input type="hidden" name="personSerialNo">
						<input type="input" name="prpLpersonFeeTypeCode" class="codecode" ondblclick="code_CodeSelect(this,'PersonFeeType','0,1','Y','Y',fm.prpLverifyLossRiskCode.value);"
							onchange="code_CodeChange(this,'PersonFeeType','0,1','Y','Y',fm.prpLverifyLossRiskCode.value);" onkeyup="code_CodeSelect(this,'PersonFeeType','0,1','Y','Y',fm.prpLverifyLossRiskCode.value);">
					</td>
					<td class="input" style="width: 13%">
						<input type="input" name="prpLpersonFeeTypeName" class="codename" ondblclick="code_CodeSelect(this, 'PersonFeeType','-1,0','Y','N',fm.prpLverifyLossRiskCode.value);"
							onchange="code_CodeChange(this, 'PersonFeeType','-1,0','Y','N',fm.prpLverifyLossRiskCode.value);"
							onkeyup="code_CodeSelect(this, 'PersonFeeType','-1,0','Y','N',fm.prpLverifyLossRiskCode.value);">
					</td>
					<td class="input" style="width: 11%">
						<input name="prpLpersonSumLoss" class="common" onBlur="return calSumPersonDefLoss(this)">
					</td>
					<td class="input" style="width: 11%">
						<input name="prpLpersonSumReject" class="common" onBlur="return calSumPersonDefLoss(this)">
					</td>
					<td class="input" style="width: 11%">
						<input name="prpLpersonSumDefLoss" class="common" onBlur="return calSumPersonDefLoss(this)">
					</td>
					<td class="input" style="width: 35%">
						<input name="prpLpersonRejectReason" class="common">
						<input type="hidden" name="prpLpersonItemKindNo">
						<input type="hidden" name="prpLpersonFamilyNo">
						<input type="hidden" name="prpLpersonItemCode">
						<input type="hidden" name="prpLpersonUnit">
						<input type="hidden" name="prpLpersonTimes">
						<input type="hidden" name="prpLpersonVeriQuantity">
						<input type="hidden" name="prpLpersonVeriUnitLoss">
						<input type="hidden" name="prpLpersonVeriUnit">
						<input type="hidden" name="prpLpersonVeriTimes">
						<input type="hidden" name="prpLpersonVeriLossRate">
						<input type="hidden" name="prpLpersonVeriRemark">
						<input type="hidden" name="prpLpersonFlag">
						<input type="hidden" name="prpLpersonCompensateBackFlag">
					</td>
					<td class="input" style="width: 7%" colspan="4" align="right">
						<input type=button name="buttonPersonFeeLossDelete" class="smallbutton" onclick="deleteRowTable(this,'trPersonFeeLoss')" value="-" readonly style="cursor: hand">
					</td>
				</tr>
				<tr name="trPersonFeeLoss">
					<td class="input" colspan="2">
						核損信息：
					</td>
					<td class="input" style="width: 11%">
						<input name="prpLpersonVeriSumLoss" class="common" style=""  value="" onBlur="return calSumPersonVeriDefLoss(this);">
					</td>
					<td class="input" style="width: 11%">
						<input name="prpLpersonVeriSumReject" class="common" style=""  onBlur="return calSumPersonVeriDefLoss(this);">
					</td>
					<td class="input" style="width: 11%">
						<input name="prpLpersonVeriSumDefLoss" class="common" style=""  onBlur="return calSumPersonVeriDefLoss(this);">
					</td>
					<td class="input" style="width: 35%">
						<input name="prpLpersonVeriRejectReason" class="common" style=""  value="">
					</td>
					<td class="input" style="width: 7%" colspan="4" align="right">
					</td>
				</tr>
			</tbody>
		</table>
</span>
	<table cellpadding="5" cellspacing="1" class="common">
		<tr class="common">
			<td colspan="4">
				<span style="display: none">
					<table class="common" style="display: none" align="center" name="tablePersonLoss" id="personLoss_Data" cellpadding="5" cellspacing="1">
						<tbody>
							<tr name="trPersonLoss">
								<td class="input" style="width: 3%;display: none;" valign="middle">
									<input class="readonly" readonly name="prpLpersonSerialNo" description="序号">
								</td>
								<td class="common">
									<table class="common" cellpadding="1" cellspacing="1" border="2">
										<tr>
											<td>
												<table cellpadding="1" cellspacing="1" class="common">
													<tr>
														<td class="left">
															<s:text name="certainLoss.victimName" />：
														</td>
														<!--伤者姓名-->
														<td class="right">
															<input name="prpLpersonPersonName" class="input">
															<img src="/claim/images/bgMarkMustInput.jpg">
														</td>
														<td class="left"></td>
														<td class="right"></td>
														<td class="left"></td>
														<td class="right"></td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="db.prpLperson.kindCode" />：
														</td>
														<!--险别代码-->
														<td class="right">
															<input type="input" name="prpLpersonKindCode" class="codecode" style="width: 90%" ondblclick="code_CodeSelect(this,'KindCodeForPerson','0,1','Y','Y',fm.RegistNo.value);"
																onchange="code_CodeChange(this, 'KindCodeForPerson','0,1','Y','Y',fm.RegistNo.value);" onkeyup="code_CodeSelect(this,'KindCodeForPerson','0,1','Y','Y',fm.RegistNo.value);">
															<img src="/claim/images/bgMarkMustInput.jpg">
														</td>
														<td class="left">
															<s:text name="db.prpLendor.kindName" />：
														</td>
														<!--险别名称-->
														<td class="right">
															<input type="input" name="prpLpersonKindName" class="codename" " style="width: 90%" ondblclick="code_CodeSelect(this, 'KindCodeForPerson','-1,0','Y','N',fm.RegistNo.value);"
																onchange="code_CodeChange(this, 'KindCodeForPerson','-1,0','Y','N',fm.RegistNo.value);" onkeyup="code_CodeSelect(this, 'KindCodeForPerson','-1,0','Y','N',fm.RegistNo.value);">
														</td>
														<td class="left">
															<s:text name="db.prpLsalvation.licenseNo" />：
														</td>
														<!--号牌号码-->
														<td class="right">
															<select name="prpLpersonFamilyName" style="width: 90%">
																<c:forEach items="${requestScope.LicenseNoList}" var="prpLthirdParty">
																	<option value="${pageScope.prpLthirdParty.licenseNo}">
																		<c:out value="${pageScope.prpLthirdParty.licenseNo}"></c:out>
																	</option>
																</c:forEach>
															</select><img src="/claim/images/bgMarkMustInput.jpg">
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="db.prpCinsurednature.sex" />：
														</td>
														<!--性别-->
														<td class="right">
															<select name="prpLpersonPersonSex" class='input' style="width: 90%">
																<option value="1">
																	<s:text name="certainLoss.male" />
																</option>
																<!--男-->
																<option value="2">
																	<s:text name="certainLoss.female" />
																</option>
																<!--女-->
															</select>
														</td>
														<td class="left">
															<s:text name="db.prpCname.age" />：
														</td>
														<!--年龄-->
														<td class="right">
															<input name="prpLpersonPersonAge" class="input">
														</td>
														<td class="left">
															<s:text name="db.prpCname.identifyNumber" />：
														</td>
														<!--身份证号码-->
														<td class="right">
															<input name="prpLpersonIdentifyNumber" class="input">
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="certainLoss.region" />：
														</td>
														<!--所在地区-->
														<td class="right">
															<input type="input" name="prpLpersonAreaCode" class="codecode" style="width: 27%" ondblclick="code_CodeSelect(this,'DamageAreaCode','0,1','Y');"
																onchange="code_CodeChange(this, 'DamageAreaCode','0,1','Y');" onkeyup="code_CodeSelect(this,'DamageAreaCode','0,1','Y');">
															<input type="input" name="prpLpersonAreaName" class="codename" style="width: 60%" ondblclick="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');"
																onchange="code_CodeChange(this, 'DamageAreaCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');">
															<img src="/claim/images/bgMarkMustInput.jpg">
														</td>
														<td class="left">
															<s:text name="db.prpCname.jobUnit" />：
														</td>
														<!--工作单位-->
														<td class="right" colspan="3">
															<input name="prpLpersonJobUnit" class="input" style="width: 96%">
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="certainLoss.standardSalary" />：
														</td>
														<!--标准工资-->
														<td class="right">
															<input name="prpLpersonMonthStdWage" class="input">
														</td>
														<td class="left">
															<s:text name="certainLoss.monthlyBonuses" />：
														</td>
														<!--月奖金-->
														<td class="right">
															<input type="input" name="prpLpersonMonthBonus" class="input">
														</td>
														<td class="left">
															<s:text name="certainLoss.subsidies" />：
														</td>
														<!--津（补）贴-->
														<td class="right">
															<input name="prpLpersonAllowance" class="input">
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="certainLoss.monthlyIncome" />：
														</td>
														<!--月收入小计-->
														<td class="right">
															<input type="input" name="prpLpersonMonthWage" class="input">
														</td>
														<td class="left">
															<s:text name="certainLoss.hospitals" />：
														</td>
														<!--就诊医院-->
														<td class="right">
															<input name="prpLpersonHospital" class="input">
														</td>
														<td class="left"></td>
														<td class="right"></td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="certainLoss.requiredPerson" />：
														</td>
														<!--需要护理人数-->
														<td class="right">
															<input name="prpLpersonNursePersons" class="input">
														</td>
														<td class="left">
															<s:text name="certainLoss.requiredDay" />：
														</td>
														<!--需要护理天数-->
														<td class="right">
															<input name="prpLpersonNurseDays" class="input">
														</td>
														<td class="left">
															<s:text name="certainLoss.results" />：
														</td>
														<!--诊断结果-->
														<td class="right">
															<input name="prpLpersonDiagnose" class="input">
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="certainLoss.degree" />：
														</td>
														<!--伤势程度-->
														<td class="right">
															<s:select name="prpLpersonWoundGrade" listKey="key" listValue="value" list="#request.woundGradeList" />
														</td>
														<td class="left">
															<s:text name="certainLoss.hospitalsDay" />：
														</td>
														<!--拟住院天数-->
														<td class="right">
															<input name="prpLpersonHospitalDays" class="input">
														</td>
														<td class="left">
															<s:text name="certainLoss.treatedDay" />：
														</td>
														<!--拟治疗天数-->
														<td class="right">
															<input name="prpLpersonCureDays" class="input">
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="certainLoss.needHospitals" />：
														</td>
														<!--是否需要转院治疗-->
														<td class="right">
															<s:select name="prpLpersonChangeHospital" listKey="key" listValue="value" list="#request.changeHospitalList" />
														</td>
														<td class="left">
															<s:text name="certainLoss.incomeSituation" />：
														</td>
														<!--收入情况-->
														<td class="right">
															<select name="prpLpersonFixedIncomeFlag" style="width: 90%">
																<c:forEach items="${requestScope.FixedIncomeFlagList}" var="labelValueBean">
																	<option value="${pageScope.labelValueBean.key}">
																		<c:out value="${pageScope.labelValueBean.value}"></c:out>
																	</option>
																</c:forEach>
															</select>
														</td>
														<td class="left">
															<s:text name="certainLoss.staffTypes" />：
														</td>
														<!--人员类型-->
														<td class="right">
															<select name="prpLpersonPayPersonType" style="width: 90%" onchange="setPropertyOfPage(this);">
																<c:forEach items="${requestScope.PayPersonTypeList}" var="labelValueBean">
																	<option value="${pageScope.labelValueBean.key}">
																		<c:out value="${pageScope.labelValueBean.value}"></c:out>
																	</option>
																</c:forEach>
															</select>
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="certainLoss.certainLoss" />：
														</td>
														<!--关联人员-->
														<td class="right">
															<input name="prpLpersonRelatePersonNo" class="input" onchange="return checkRelatePersonNo(this);">
														</td>
														<td class="left">
															<s:text name="verifyLoss.disableRatio" />：
														</td>
														<!--伤残比例-->
														<td class="right">
															<input name="prpLpersonLossRate" class="input">
															%
														</td>
														<td class="left">
															<s:text name="db.prpLreplevynew.currency" />：
														</td>
														<!--币别-->
														<td class="right">
															<input name="prpLpersonCurrencyName" class="readonly" style="width: 82%" readonly value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>">
															<!--人民币-->
															<input name="prpLpersonCurrency" type="hidden" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="certainLoss.admissionDate" />：
														</td>
														<!--入院日期-->
														<td class="right">
															<%-- <input type="input" name="prpLpersonInHospDate" class="codename" ondblclick="return getDateTime(this);">--%>
															<rc:rcDate name="prpLpersonInHospDate" style="width:90%" />
														</td>
														<td class="left">
															<s:text name="certainLoss.dischargeDate" />：
														</td>
														<!--出院日期-->
														<td class="right">
															<%--<input type="input" name="prpLpersonOutHospDate" class="codename" ondblclick="return getDateTime(this);">--%>
															<rc:rcDate name="prpLpersonOutHospDate" style="width:90%" />
														</td>
														<td class="left">
															<s:text name="certainLoss.dateFixed" />：
														</td>
														<!--定残日期-->
														<td class="right">
															<%--  <input type="input" name="prpLpersonRestDate" class="codename" ondblclick="return getDateTime(this);">--%>
															<rc:rcDate name="prpLpersonRestDate" style="width:90%" />
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="certainLoss.medicalNotes" />：
														</td>
														<!--续医情况说明-->
														<td class="right">
															<input name="prpLpersonFllowHospRemark" class="input">
														</td>
														<td class="left">
															<s:text name="certainLoss.industry" />：
														</td>
														<!--行业-->
														<td class="right" colspan="3">
															<input type="hidden" name="prpLpersonTraceJobCode1">
															<input type="text" name="prpLpersonTraceJobName1" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource1','-1,0','Y');"
																onkeyup="code_CodeSelect(this,'BusinessSource1','-1,0','Y');" onchange="code_CodeChange(this,'BusinessSource1','-1,0','Y');">
															<input type="hidden" name="prpLpersonTraceJobCode2">
															<input type="text" name="prpLpersonTraceJobName2" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource2','-1,0','Y');"
																onkeyup="code_CodeSelect(this,'BusinessSource2','-1,0','Y');" onchange="code_CodeChange(this,'BusinessSource2','-1,0','Y');">
															<input type="hidden" name="prpLpersonJobCode">
															<input type="text" name="prpLpersonJobName" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');"
																onchange="code_CodeChange(this,'BusinessSource','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');">
														</td>
													</tr>
													<tr>
														<td class="left">
															<s:text name="db.prpLcomponent.remark" />
														</td>
														<!--备注-->
														<td class="right" colspan="5">
															<input name="prpLpersonRemark" class="input" style="width: 90%">
															<input type="hidden" name="prpLpersonPersonNo" value="0">
														</td>
													</tr>
													<tr>
														<td class="common" style="width: 100%" colspan="6">
															<table class="common" cellpadding="5" cellspacing="1">
																<tr>
																	<td class="title" style="width: 4%" rowspan="6">
																		<s:text name="certainLoss.injuryCategories" />
																	</td>
																	<!--伤情类别-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck001" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck001Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.brainInjury" />
																	</td>
																	<!--颅脑损伤-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck002" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck002Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.ribFracture" />
																	</td>
																	<!--肋骨骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck003" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck003Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.fractureLimb" />
																	</td>
																	<!--下肢骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck004" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck004Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.injurySpleen" />
																	</td>
																	<!--脾脏损伤-->
																</tr>
																<tr>
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck005" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck005Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.facialInjury" />
																	</td>
																	<!--容貌损伤-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck006" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck006Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.spinalFractures1" />
																	</td>
																	<!--脊柱骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck007" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck007Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.handFractures" />
																	</td>
																	<!--手部骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck008" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck008Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.injuryPancreas" />
																	</td>
																	<!--胰脏损伤-->
																</tr>
																<tr>
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck009" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck009Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.neckInjuries" />
																	</td>
																	<!--颈部损伤-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck010" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck010Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.spinalFractures2" />
																	</td>
																	<!--脊髓骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck011" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck011Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.footFracture" />
																	</td>
																	<!--足部骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck012" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck012Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.kidneyInjury" />
																	</td>
																	<!--肾脏损伤-->
																</tr>
																<tr>
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck013" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck013Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.spineInjury" />
																	</td>
																	<!--颈椎损伤-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck014" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck014Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.boneFractures" />
																	</td>
																	<!--盆骨骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck015" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck015Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.heartInjury" />
																	</td>
																	<!--心脏损伤-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck016" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck016Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.otherIinjury" />
																	</td>
																	<!--其他内脏损伤-->
																</tr>
																<tr>
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck017" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck017Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.clavicleFracture" />
																	</td>
																	<!--锁骨骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck018" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck018Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.femoralHead" />
																	</td>
																	<!--股骨头骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck019" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck019Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.lungInjury" />
																	</td>
																	<!--肺部损伤-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck020" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck020Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.softInjury" />
																	</td>
																	<!--软组织挫伤-->
																</tr>
																<tr>
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck021" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck021Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.sternalFractures" />
																	</td>
																	<!--胸骨骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck022" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck022Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.fractureUpper" />
																	</td>
																	<!--上臂骨折-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck023" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck023Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.liverInjury" />
																	</td>
																	<!--肝脏损伤-->
																	<td class="title" style="width: 10%" align="center">
																		<input type="checkbox" name="woundCodeCheck024" onClick="return woundCodeChange(this);">
																		<input type="hidden" name="woundCodeCheck024Txt" value="0">
																	</td>
																	<td class="title" style="width: 14%">
																		<s:text name="certainLoss.otherDamage" />
																	</td>
																	<!--其他损伤-->
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td colspan="6">
															<span id="spanPersonFeeLoss"> <%-- 多行输入展现域 --%>
																<table id="personFeeLoss" name="tablePersonFeeLoss" class="common" cellpadding="5" cellspacing="1">
																	<thead>
																		<tr>
																			<td class="centertitle" colspan=10>
																				<s:text name="certainLoss.costInformation" />
																				<!--人员伤亡费用清单信息-->
																			</td>
																		</tr>
																		<tr>
																			<td class="centertitle" style="width: 12%">
																				<s:text name="compensate.costCode" />
																			</td>
																			<!--费用代码-->
																			<td class="centertitle" style="width: 13%">
																				<s:text name="compensate.costName" />
																			</td>
																			<!--费用名称-->
																			<td class="centertitle" style="width: 11%">
																				<s:text name="certainLoss.prpLperson.sumLoss" />
																			</td>
																			<!--报损金额-->
																			<td class="centertitle" style="width: 11%">
																				<s:text name="db.prpLprop.sumReject" />
																			</td>
																			<!--剔除金额-->
																			<td class="centertitle" style="width: 11%">
																				<s:text name="certainLoss.lossAmount" />
																			</td>
																			<!--定损金额-->
																			<td class="centertitle" style="width: 35%">
																				<s:text name="certainLoss.removedNote" />
																			</td>
																			<!--剔除情况说明-->
																			<td class="centertitle" style="width: 7%" colspan=4>操作</td>
																		</tr>
																	</thead>
																	<tfoot>
																		<tr>
																			<td class="title" colspan="6" style="width: 93%">
																				<s:text name="prompt.certainLoss.addRemoveIformation" />
																			</td>
																			<!--(按"+"号键增加人员伤亡费用信息，按"-"号键删除信息)-->
																			<td class="title" align="right" colspan="4" style="width: 7%">
																				<input type="button" class=smallbutton value="+" onclick="insertPersonFeeLoss(this);" name="buttonPersonFeeLossInsert" readonly style="cursor: hand">
																			</td>
																		</tr>
																	</tfoot>
																	<tbody>
																	</tbody>
																</table>
															</span>
														</td>
													</tr>
													<tr>
														<td colspan="6">
															<table class="common" style="width: 100%">
																<td class='title' colspan="2" width="30%">
																	<s:text name="certainLoss.prpLscheduleMainWF.LossSum" />
																	<input class='readonly' readonly="true" name='prpLpersonSumLossSum'>
																	<!--报损金额-->
																</td>
																<td class='title' colspan="2" width="30%">
																	<s:text name="db.prpLprop.sumReject" />:
																	<input class='readonly' readonly="true" name='prpLpersonSumRejectSum'>
																	<!--剔除金额-->
																</td>
																<td class='title' colspan="2" width="40%">
																	<s:text name="certainLoss.lossAmount" />:
																	<input class='readonly' readonly="true" name='prpLpersonSumDefLossSum'>
																	<!--定损金额-->
																</td>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
								<td class="input" style="width: 4%;display:none" >
									<div align="center">
										<input type=button name="buttonPersonDelete" class=smallbutton onclick="deleteRowTable(this,'trPersonLoss')" value="-" style="cursor: hand">
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</span>
			</td>
		</tr>
	</table>
	<table cellpadding="5" cellspacing="1" class="common">
		<tr class="common">
			<td colspan="4">
				<span id="spanPerson"> <%-- 多行输入展现域 --%>
					<table id="personLoss" name="tablePersonLoss" class="common" align="center" cellspacing="1" cellpadding="5">
						<thead>
							<tr>
								<td class="subformtitle" colspan="3" align="center">
									<s:text name="certainLoss.paymentInformation" />
									<%--赔付人员信息 --%>
								</td>
							</tr>
							<tr>
								<td class="subformtitle" style="width: 5%;display: none;">
									<s:text name="db.prpLreplevynew.serialNo" />
								</td>
								<!--序号-->
								<td class="subformtitle" style="width: 90%;" align="center">
									<s:text name="db.utiFile.text" />
								</td>
								<!--内容-->
								<td class="subformtitle" style="width: 5%;display:none">操作</td>
								<!--操作-->
							</tr>
						</thead>
						<tfoot style="display:none">
							<tr>
								<td class="title" colspan=3 align="right" style="display:none">
									<div align="center" >
										<input type="button" value="+" class=smallbutton onclick="insertPersonLoss(this);" name="buttonPersonInsert" style="cursor: hand">
									</div>
								</td>
							</tr>
						</tfoot>
						<tbody>
							
							<%--人伤核损是否都为只读 --%>
							<c:set var="veriwReadOnly" value="readonly" scope="page"/>
							<c:set var="veriwDisabled" value="disabled" scope="page"/>
							<c:set var="personNo" value="-1" scope="page"/>
							<c:forEach items="${requestScope.prpLperson.personList}" var="prpLperson1">
								<c:if test="${pageScope.prpLperson1.id.personNo !=pageScope.personNo}">
									<c:set var="personNo" value="${pageScope.prpLperson1.id.personNo}" scope="page"/>
									<tr name="trPersonLoss">
										<td class="input" style="width: 3%;display: none;" valign="middle">
											<input class="readonly" readonly name="prpLpersonSerialNo" description="<s:text name='db.prpCinsurednature.serialNo'/>" value="${prpLperson1.id.serialNo}">
											<!--序号-->
										</td>
										<td class="common">
											<table class="common" cellpadding="1" cellspacing="1" border="2">
												<tr>
													<td>
														<table cellpadding="1" cellspacing="1" class="common">
															<tr>
																<td class="left">
																	<s:text name="certainLoss.victimName" />：
																</td>
																<!--伤者姓名-->
																<td class="right">
																	<input name="prpLpersonPersonName" value="${pageScope.prpLperson1.personName}" ${veriwReadOnly } style="width: 82%" class="input">
																	<img src="/claim/images/bgMarkMustInput.jpg">
																</td>
																<c:if test="${pageScope.prpLperson1.kindCode=='B'}">
																	<td class="left">
																		<s:text name="certainLoss.threeWounded" />
																	</td>
																	<!--三者人伤-->
																</c:if>
																<c:if test="${pageScope.prpLperson1.kindCode=='D1'}">
																	<td class="left">
																		<s:text name="certainLoss.humanInjury" />
																	</td>
																	<!--车上人伤-->
																</c:if>
																<c:if test="${pageScope.prpLperson1.kindCode!='B' || pageScope.prpLperson1.kindCode!='D1'}">
																	<td class="left">
																		<s:text name="certainLoss.threeWounded" />
																	</td>
																	<!--三者人伤-->
																</c:if>
																<td class="right"></td>
																<td class="left"></td>
																<td class="right"></td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="db.prpDkind.kindCode" />：
																</td>
																<!--险别代码-->
																<td class="right">
																	<input type="input" name="prpLpersonKindCode" style="width: 82%" class="codecode" value="${pageScope.prpLperson1.kindCode}" ${veriwReadOnly } 
																		<c:if test="${veriwReadOnly==''}">
																			ondblclick="code_CodeSelect(this,'KindCodeForPerson','0,1','Y','Y',fm.RegistNo.value);" onchange="code_CodeChange(this,'KindCodeForPerson','0,1','Y','Y',fm.RegistNo.value);"
																			onkeyup="code_CodeSelect(this,'KindCodeForPerson','0,1','Y','Y',fm.RegistNo.value);"
																		</c:if>
																	>
																	<img src="/claim/images/bgMarkMustInput.jpg">
																</td>
																<td class="left" style="width: 12%">
																	<s:text name="db.prpDrate.kindName" />：
																</td>
																<!--险别名称-->
																<td class="right">
																	<input type="input" name="prpLpersonKindName" class="codename" value="${pageScope.prpLperson1.kindName}" ${veriwReadOnly } 
																		<c:if test="${veriwReadOnly=='' }">
																			ondblclick="code_CodeSelect(this,'KindCodeForPerson','-1,0','Y','N',fm.RegistNo.value);" onchange="code_CodeChange(this,'KindCodeForPerson','-1,0','Y','N',fm.RegistNo.value);"
																			onkeyup="code_CodeSelect(this,'KindCodeForPerson','-1,0','Y','N',fm.RegistNo.value);"
																		</c:if>
																	>
																	<img src="/claim/images/bgMarkMustInput.jpg">
																</td>
																<td class="left">
																	<s:text name="db.prpLsalvation.licenseNo" />：
																</td>
																<!--号牌号码-->
																<td class="right">
																	<select name="prpLpersonFamilyName"  ${veriwDisabled } style="width: 83%">
																		<c:forEach items="${requestScope.LicenseNoList}" var="prpLthirdParty">
																			<option value="${pageScope.prpLthirdParty.licenseNo}" <c:if test="${pageScope.prpLthirdParty.licenseNo == pageScope.prpLperson1.familyName}"><c:out value="selected=\"selected\""/></c:if>>
																				<c:out value="${pageScope.prpLthirdParty.licenseNo}"></c:out>
																			</option>
																		</c:forEach>
																	</select> <img src="/claim/images/bgMarkMustInput.jpg">
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="db.prpLpersonloss.sex" />：
																</td>
																<!--性别-->
																<td class="right">
																	<select name="prpLpersonPersonSex" value="${pageScope.prpLperson1.personSex}" ${veriwDisabled } class="three" style="width: 82%">
																		<option value="1" <c:if test="${pageScope.prpLperson1.personSex =='1'}"><c:out value="selected" /></c:if>>
																			<s:text name="certainLoss.male" />
																		</option>
																		<!--男-->
																		<option value="2" <c:if test="${pageScope.prpLperson1.personSex =='2'}"><c:out value="selected" /></c:if>>
																			<s:text name="certainLoss.female" />
																		</option>
																		<!--女-->
																	</select>
																</td>
																<td class="left">
																	<s:text name="db.prpLpersonloss.age" />：
																</td>
																<!--年龄-->
																<td class="right">
																	<input name="prpLpersonPersonAge" class="input" value="${pageScope.prpLperson1.personAge}" ${veriwReadOnly }>
																</td>
																<td class="left">
																	<s:text name="db.prpLpersonloss.identifyNumber" />：
																</td>
																<!--身份证号码-->
																<td class="right">
																	<input name="prpLpersonIdentifyNumber" value="${pageScope.prpLperson1.identifyNumber}" ${veriwReadOnly } class="input" style="width: 83%" >
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="certainLoss.region" />：
																</td>
																<!--所在地区-->
																<td class="right">
																	<input type="input" name="prpLpersonAreaCode" class="codecode" style="width: 27%" value="${pageScope.prpLperson1.areaCode}" ${veriwReadOnly }  
																		<c:if test="${veriwReadOnly=='' }">
																		ondblclick="code_CodeSelect(this,'DamageAreaCode','0,1','Y');" onchange="code_CodeChange(this,'DamageAreaCode','0,1','Y');"
																		onkeyup="code_CodeSelect(this,'DamageAreaCode','0,1','Y');"
																		</c:if>
																		>
																	<input type="input" name="prpLpersonAreaName" class="codename" style="width: 51%" value="${pageScope.prpLperson1.areaName}" ${veriwReadOnly } 
																		<c:if test="${veriwReadOnly=='' }">
																		ondblclick="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'DamageAreaCode','-1,0','Y','N');"
																		onkeyup="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');"
																		</c:if>
																		>
																	<img src="/claim/images/bgMarkMustInput.jpg">
																</td>
																<td class="left">
																	<s:text name="db.prpCname.jobUnit" />：
																</td>
																<!--工作单位-->
																<td class="right" colspan="3">
																	<input name="prpLpersonJobUnit" value="${pageScope.prpLperson1.jobUnit}" ${veriwReadOnly }  class="input" style="width: 93.5%" >
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="certainLoss.standardSalary" />：
																</td>
																<!--标准工资-->
																<td class="right">
																	<input name="prpLpersonMonthStdWage" value="<fmt:formatNumber value="${pageScope.prpLperson1.monthStdWage}" pattern="#"/>" ${veriwReadOnly } class="input" style="width: 82%" >
																</td>
																<td class="left">
																	<s:text name="certainLoss.monthlyBonuses" />：
																</td>
																<!--月奖金-->
																<td class="right">
																	<input type="input" name="prpLpersonMonthBonus" class="input" value="<fmt:formatNumber value="${pageScope.prpLperson1.monthBonus}" pattern="#"/>" ${veriwReadOnly }>
																</td>
																<td class="left">
																	<s:text name="certainLoss.subsidies" />：
																</td>
																<!--津（补）贴-->
																<td class="right">
																	<input name="prpLpersonAllowance" class="input" style="width: 83%" value="<fmt:formatNumber value="${pageScope.prpLperson1.allowance}" pattern="#"/>" ${veriwReadOnly }>
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="certainLoss.monthlyIncome" />：
																</td>
																<!--月收入小计-->
																<td class="right">
																	<input type="input" name="prpLpersonMonthWage" class="input" style="width: 82%" value="<fmt:formatNumber value="${pageScope.prpLperson1.monthWage}" pattern="#"/>" ${veriwReadOnly }>
																</td>
																<td class="left">
																	<s:text name="certainLoss.hospitals" />：
																</td>
																<!--就诊医院-->
																<td class="right">
																	<input name="prpLpersonHospital" class="input" value="${pageScope.prpLperson1.hospital}" ${veriwReadOnly }>
																</td>
																<td class="left"></td>
																<td class="right"></td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="certainLoss.requiredPerson" />：
																</td>
																<!--需要护理人数-->
																<td class="right">
																	<input name="prpLpersonNursePersons" class="input" style="width: 82%" value="${pageScope.prpLperson1.nursePersons}" ${veriwReadOnly }>
																</td>
																<td class="left">
																	<s:text name="certainLoss.requiredDay" />：
																</td>
																<!--需要护理天数-->
																<td class="right">
																	<input name="prpLpersonNurseDays" class="input" value="${pageScope.prpLperson1.nurseDays}" ${veriwReadOnly }>
																</td>
																<td class="left">
																	<s:text name="certainLoss.results" />：
																</td>
																<!--诊断结果-->
																<td class="right">
																	<input name="prpLpersonDiagnose" class="input" style="width: 83%" value="${pageScope.prpLperson1.diagnose}" ${veriwReadOnly }>
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="certainLoss.degree" />：
																</td>
																<!--伤势程度-->
																<td class="right">
																	<c:set var="tempSelectedValue" value="${pageScope.prpLperson1.woundGrade}" />
																	<s:select name="prpLpersonWoundGrade" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.woundGradeList" disabled="true"/>
																</td>
																<td class="left">
																	<s:text name="certainLoss.hospitalsDay" />：
																</td>
																<!--拟住院天数-->
																<td class="right">
																	<input name="prpLpersonHospitalDays" class="input" value="${pageScope.prpLperson1.hospitalDays}" ${veriwReadOnly }>
																</td>
																<td class="left">
																	<s:text name="certainLoss.treatedDay" />：
																</td>
																<!--拟治疗天数-->
																<td class="right">
																	<input name="prpLpersonCureDays" class="input" style="width: 83%" value="${pageScope.prpLperson1.cureDays}" ${veriwReadOnly }>
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="certainLoss.needHospitals" />：
																</td>
																<!--是否需要转院治疗-->
																<td class="right">
																	<c:set var="tempSelectedValue" value="${pageScope.prpLperson1.changeHospital}" />
																	<s:select name="prpLpersonChangeHospital" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.changeHospitalList" disabled="true"/>
																</td>
																<td class="left">
																	<s:text name="certainLoss.incomeSituation" />：
																</td>
																<!--收入情况-->
																<td class="right">
																	<select name="prpLpersonFixedIncomeFlag" style="width: 90%" ${veriwDisabled }>
																		<c:forEach items="${requestScope.FixedIncomeFlagList}" var="labelValueBean">
																			<option <c:if test="${prpLperson1.fixedIncomeFlag==labelValueBean.key }">selected="selected"</c:if> value="${pageScope.labelValueBean.key}">
																				<c:out value="${pageScope.labelValueBean.value}"></c:out>
																			</option>
																		</c:forEach>
																	</select>
																</td>
																<td class="left">
																	<s:text name="certainLoss.staffTypes" />：
																</td>
																<!--人员类型-->
																<td class="right">
																	<select name="prpLpersonPayPersonType" style="width: 83%" onchange="setPropertyOfPage(this);" ${veriwDisabled }>
																		<c:forEach items="${requestScope.PayPersonTypeList}" var="labelValueBean">
																			<option <c:if test="${prpLperson1.payPersonType==labelValueBean.key }">selected="selected"</c:if> value="${pageScope.labelValueBean.key}">
																				<c:out value="${pageScope.labelValueBean.value}"></c:out>
																			</option>
																		</c:forEach>
																	</select>
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="certainLoss.certainLoss" />：
																</td>
																<!--关联人员-->
																<td class="right">
																	<input name="prpLpersonRelatePersonNo" class="input" value="${pageScope.prpLperson1.relatePersonNo}" onchange="return checkRelatePersonNo(this);" ${veriwReadOnly }>
																</td>
																<td class="left">
																	<s:text name="verifyLoss.disableRatio" />：
																</td>
																<!--伤残比例-->
																<td class="right">
																	<input name="prpLpersonLossRate" class="input" style="width: 90%" value="${pageScope.prpLperson1.lossRate}" ${veriwReadOnly }>
																	%
																</td>
																<td class="left">
																	<s:text name="db.prpLreplevynew.currency" />：
																</td>
																<!--币别-->
																<td class="right">
																	<input name="prpLpersonCurrencyName" class="readonly" style="width: 82%" readonly value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>">
																	<!--人民币-->
																	<input name="prpLpersonCurrency" type="hidden" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="certainLoss.admissionDate" />：
																</td>
																<!--入院日期-->
																<c:set var="wdatePicker" value="true"/>
																<c:if test="${veriwReadOnly!='' }">
																	<c:set var="wdatePicker" value="false"/>
																</c:if>
																<td class="right">
																	<rc:rcDate name="prpLpersonInHospDate" style="width:90%" value="${pageScope.prpLperson1.inHospDate}" readonly="${veriwReadOnly }" wdatePicker="${wdatePicker}" class="input"/>
																</td>
																<td class="left">
																	<s:text name="certainLoss.dischargeDate" />：
																</td>
																<!--出院日期-->
																<td class="right">
																	<%--<input type="input" name="prpLpersonOutHospDate" style="width:83%" class="codename" value="${pageScope.prpLperson1.outHospDate}"  onclick="WdatePicker()" >--%>
																	<rc:rcDate name="prpLpersonOutHospDate" style="width:90%" value="${pageScope.prpLperson1.outHospDate}" readonly="${veriwReadOnly }" wdatePicker="${wdatePicker}" class="input"/>
																</td>
																<td class="left">
																	<s:text name="certainLoss.dateFixed" />：
																</td>
																<!--定残日期-->
																<td class="right">
																	<%--<input type="input" name="prpLpersonRestDate" class="codename" style="width:82%" value="${pageScope.prpLperson1.restDate}"  onclick="WdatePicker()" >--%>
																	<rc:rcDate name="prpLpersonRestDate" style="width:83%" value="${pageScope.prpLperson1.restDate}" readonly="${veriwReadOnly }" wdatePicker="${wdatePicker}" class="input"/>
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="certainLoss.medicalNotes" />：
																</td>
																<!--续医情况说明-->
																<td class="right">
																	<input name="prpLpersonFllowHospRemark" class="input" value="${pageScope.prpLperson1.fllowHospRemark}" ${veriwReadOnly }>
																</td>
																<td class="left">
																	<s:text name="certainLoss.industry" />：
																</td>
																<!--行业-->
																<td class="right" colspan="3">
																	<input type="hidden" name="prpLpersonTraceJobCode1" value="${pageScope.prpLperson1.jobCode1}">
																	<input type="text" name="prpLpersonTraceJobName1" class="codename" style="width: 100px" value="${pageScope.prpLperson1.jobName1}" ${veriwReadOnly } 
																	<c:if test="${veriwReadOnly=='' }">
																		ondblclick="code_CodeSelect(this,'BusinessSource1','-1,0','Y');" onkeyup="code_CodeSelect(this,'BusinessSource1','-1,0','Y');"
																		onchange="code_CodeChange(this,'BusinessSource1','-1,0','Y');"
																	</c:if>
																	>
																	<input type="hidden" name="prpLpersonTraceJobCode2" value="${pageScope.prpLperson1.jobCode2}">
																	<input type="text" name="prpLpersonTraceJobName2" class="codename" style="width: 100px" value="${pageScope.prpLperson1.jobName2}" ${veriwReadOnly }
																	<c:if test="${veriwReadOnly=='' }">
																		ondblclick="code_CodeSelect(this,'BusinessSource2','-1,0','Y');" onkeyup="code_CodeSelect(this,'BusinessSource2','-1,0','Y');"
																		onchange="code_CodeChange(this,'BusinessSource2','-1,0','Y');"
																	</c:if>
																	>
																	<input type="hidden" name="prpLpersonJobCode" value="${pageScope.prpLperson1.jobCode}">
																	<input type="text" name="prpLpersonJobName" class="codename" style="width: 100px" value="${pageScope.prpLperson1.jobName}" ${veriwReadOnly } 
																	<c:if test="${veriwReadOnly=='' }">
																		ondblclick="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');" onchange="code_CodeChange(this,'BusinessSource','-1,0','Y','N');"
																		onkeyup="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');"
																	</c:if>
																	>
																</td>
															</tr>
															<tr>
																<td class="left">
																	<s:text name="db.prpLcomponent.remark" />
																</td>
																<!--备注-->
																<td class="right" colspan="5">
																	<input name="prpLpersonRemark" class="input" style="width: 89%" value="${pageScope.prpLperson1.remark}" ${veriwReadOnly }>
																	<input type="hidden" name="prpLpersonPersonNo" value="${pageScope.prpLperson1.id.personNo}">
																</td>
															</tr>
															<tr>
																<td class="common" style="width: 100%" colspan="6">
																	<table class="common" style="width: 100%" cellpadding="1" cellspacing="1" border="0">
																		<tr>
																			<td class="title" style="width: 4%" rowspan="6">
																				<s:text name="certainLoss.injuryCategories" />
																			</td>
																			<!--伤情类别-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck001" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck001Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.brainInjury" />
																			</td>
																			<!--颅脑损伤-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck002" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck002Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.ribFracture" />
																			</td>
																			<!--肋骨骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck003" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck003Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.fractureLimb" />
																			</td>
																			<!--下肢骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck004" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck004Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.injurySpleen" />
																			</td>
																			<!--脾脏损伤-->
																		</tr>
																		<tr>
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck005" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck005Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.facialInjury" />
																			</td>
																			<!--容貌损伤-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck006" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck006Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.spinalFractures1" />
																			</td>
																			<!--脊柱骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck007" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck007Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.handFractures" />
																			</td>
																			<!--手部骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck008" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck008Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.injuryPancreas" />
																			</td>
																			<!--胰脏损伤-->
																		</tr>
																		<tr>
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck009" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck009Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.neckInjuries" />
																			</td>
																			<!--颈部损伤-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck010" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck010Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.spinalFractures2" />
																			</td>
																			<!--脊髓骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck011" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck011Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.footFracture" />
																			</td>
																			<!--足部骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck012" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck012Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.kidneyInjury" />
																			</td>
																			<!--肾脏损伤-->
																		</tr>
																		<tr>
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck013" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck013Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.spineInjury" />
																			</td>
																			<!--颈椎损伤-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck014" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck014Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.boneFractures" />
																			</td>
																			<!--盆骨骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck015" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck015Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.heartInjury" />
																			</td>
																			<!--心脏损伤-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck016" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck016Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.otherIinjury" />
																			</td>
																			<!--其他内脏损伤-->
																		</tr>
																		<tr>
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck017" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck017Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.clavicleFracture" />
																			</td>
																			<!--锁骨骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck018" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck018Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.femoralHead" />
																			</td>
																			<!--股骨头骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck019" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck019Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.lungInjury" />
																			</td>
																			<!--肺部损伤-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck020" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck020Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.softInjury" />
																			</td>
																			<!--软组织挫伤-->
																		</tr>
																		<tr>
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck021" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck021Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.sternalFractures" />
																			</td>
																			<!--胸骨骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck022" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck022Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.fractureUpper" />
																			</td>
																			<!--上臂骨折-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck023" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck023Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.liverInjury" />
																			</td>
																			<!--肝脏损伤-->
																			<td class="title" style="width: 10%" align="center">
																				<input type="checkbox" name="woundCodeCheck024" onClick="return woundCodeChange(this);" ${veriwDisabled }>
																				<input type="hidden" name="woundCodeCheck024Txt" value="0">
																			</td>
																			<td class="title" style="width: 14%">
																				<s:text name="certainLoss.otherDamage" />
																			</td>
																			<!--其他损伤-->
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td colspan="6">
																	<span id="spanPersonFeeLoss"> <%-- 多行输入展现域 --%>
																		<table id="personFeeLoss" name="tablePersonFeeLoss" class="common">
																			<thead>
																				<tr>
																					<td class="centertitle" colspan=10>
																						<s:text name="certainLoss.costInformation" />
																						<font color="#FF0000">*</font>
																						<!--人员伤亡费用清单信息-->
																					</td>
																				</tr>
																				<tr>
																					<td class="centertitle" style="width: 12%">
																						<s:text name="compensate.costCode" />
																					</td>
																					<!--费用代码-->
																					<td class="centertitle" style="width: 13%">
																						<s:text name="compensate.costName" />
																					</td>
																					<!--费用名称-->
																					<td class="centertitle" style="width: 11%">
																						<s:text name="certainLoss.prpLperson.sumLoss" />
																					</td>
																					<!--报损金额-->
																					<td class="centertitle" style="width: 11%">
																						<s:text name="db.prpLprop.sumReject" />
																					</td>
																					<!--剔除金额-->
																					<td class="centertitle" style="width: 11%">
																						<s:text name="certainLoss.lossAmount" />
																					</td>
																					<!--定损金额-->
																					<td class="centertitle" style="width: 35%">
																						<s:text name="certainLoss.removedNote" />
																					</td>
																					<!--剔除情况说明-->
																					<td class="centertitle" style="width: 7%" colspan="4">操作</td>
																				</tr>
																			</thead>
																			<tfoot>
																				<tr>
																					<td class="title" colspan="6" style="width: 93%">
																						<s:text name="prompt.certainLoss.addRemoveIformation" />
																					</td>
																					<!--(按"+"号键增加人员伤亡费用信息，按"-"号键删除信息)-->
																					<td class="title" align="right" style="width: 7%" colspan="4">
																						<input type="button" disabled="disabled" value="+" class="smallbutton" onclick="insertPersonFeeLoss(this);" name="buttonPersonFeeLossInsert" readonly
																							style="cursor: hand" ${veriwDisabled }>
																					</td>
																				</tr>
																			</tfoot>
																			<tbody>
																				<c:forEach items="${requestScope.prpLperson.personList}" var="prpLperson2">
																					<c:if test="${pageScope.prpLperson2.id.personNo == pageScope.prpLperson1.id.personNo}">
																						<c:set var="compensatebackReadOnly" value="" />
																						<c:set var="compensatebackDiasable" value="" />
																						<c:set var="compensatebackStyle" value="" />
																						<%--/**增加理算退回的判断*/--%>
																						<c:if test="${pageScope.prpLperson2.compensateBackFlag =='1'}">
																							<c:set var="compensatebackReadOnly" value="readOnly" />
																							<c:set var="compensatebackDiasable" value="disabled" />
																							<c:set var="compensatebackStyle" value="" />
																						</c:if>
																						
																						<tr name="trPersonFeeLoss">
																							<td class="input" style="width: 12%">
																								<input type="hidden" name="personSerialNo" style="" <c:out value="${veriwReadOnly}" /> value="${prpLperson1.id.serialNo}">
																								<input type="input" name="prpLpersonFeeTypeCode" class="readonly" style="" <c:out value="${veriwReadOnly}" /> value="${pageScope.prpLperson2.feeTypeCode}"
																								<c:if test="${empty veriwReadOnly}">
														                                           ondblclick= "code_CodeSelect(this,'PersonFeeType','0,1','Y','Y',fm.prpLverifyLossRiskCode.value);"
														                                           onchange="code_CodeChange(this,'PersonFeeType','0,1','Y','Y',fm.prpLverifyLossRiskCode.value);"
														                                           onkeyup= "code_CodeSelect(this,'PersonFeeType','0,1','Y','Y',fm.prpLverifyLossRiskCode.value);"      
														                                       </c:if>>
																							</td>
																							<td class="input" style="width: 13%">
																								<input type="input" name="prpLpersonFeeTypeName" class="readonly" style="" <c:out value="${veriwReadOnly}" /> value="${pageScope.prpLperson2.feeTypeName}"
																									<c:if test="${empty veriwReadOnly}">
														                                             ondblclick="code_CodeSelect(this, 'PersonFeeType','-1,0','Y','N',fm.prpLverifyLossRiskCode.value);"
														                                             onchange="code_CodeChange(this, 'PersonFeeType','-1,0','Y','N',fm.prpLverifyLossRiskCode.value);"
														                                             onkeyup= "code_CodeSelect(this, 'PersonFeeType','-1,0','Y','N',fm.prpLverifyLossRiskCode.value);"
														                                         </c:if>>
																							</td>
																							<td class="input" style="width: 11%">
																								<input name="prpLpersonSumLoss" class="readonly" style="" <c:out value="${veriwReadOnly}" />
																									value="<fmt:formatNumber value="${pageScope.prpLperson2.sumLoss}" pattern="#"/>" onBlur="return calSumPersonDefLoss(this)">
																							</td>
																							<td class="input" style="width: 11%">
																								<input name="prpLpersonSumReject" class="readonly" style="" <c:out value="${veriwReadOnly}" />
																									value="<fmt:formatNumber value="${pageScope.prpLperson2.sumReject}" pattern="#"/>" onBlur="return calSumPersonDefLoss(this);">
																							</td>
																							<td class="input" style="width: 11%">
																								<input name="prpLpersonSumDefLoss" class="readonly" style="" <c:out value="${veriwReadOnly}" />
																									value="<fmt:formatNumber value="${pageScope.prpLperson2.sumDefLoss}" pattern="#"/>" onBlur="return calSumPersonDefLoss(this);">
																							</td>
																							<td class="input" style="width: 35%">
																								<input name="prpLpersonRejectReason" class="readonly" style="" <c:out value="${veriwReadOnly}" /> value="${pageScope.prpLperson2.rejectReason}">
																								<input type="hidden" name="prpLpersonItemKindNo" value="${pageScope.prpLperson2.itemKindNo}">
																								<input type="hidden" name="prpLpersonFamilyNo" value="${pageScope.prpLperson2.familyNo}">
																								<input type="hidden" name="prpLpersonItemCode" value="${pageScope.prpLperson2.itemCode}">
																								<input type="hidden" name="prpLpersonUnit" value="${pageScope.prpLperson2.unit}">
																								<input type="hidden" name="prpLpersonTimes" value="${pageScope.prpLperson2.times}">
																								<input type="hidden" name="prpLpersonVeriQuantity" value="${pageScope.prpLperson2.veriQuantity}">
																								<input type="hidden" name="prpLpersonVeriUnitLoss" value="${pageScope.prpLperson2.veriUnitLoss}">
																								<input type="hidden" name="prpLpersonVeriUnit" value="${pageScope.prpLperson2.veriUnit}">
																								<input type="hidden" name="prpLpersonVeriTimes" value="${pageScope.prpLperson2.veriTimes}">
																								<input type="hidden" name="prpLpersonVeriLossRate" value="${pageScope.prpLperson2.veriLossRate}">
																								<input type="hidden" name="prpLpersonVeriRemark" value="${pageScope.prpLperson2.veriRemark}">
																								<input type="hidden" name="prpLpersonCompensateBackFlag" value="${pageScope.prpLperson2.compensateBackFlag}">
																								<input type="hidden" name="prpLpersonFlag" value="${pageScope.prpLperson2.flag}">
																							</td>
																							<td class="input" style="width: 7%" colspan="4" align="right">
																								<input type=button name="buttonPersonFeeLossDelete" class="smallbutton" <c:out value="${veriwDisabled}" />
																									onclick="deleteRowTable(this,'trPersonFeeLoss')" value="-" readonly style="cursor: hand">
																							</td>
																						</tr>
																						<tr name="trPersonFeeLoss">
																							<td class="input" colspan="2">
																								核損信息：
																							</td>
																							<td class="input" style="width: 11%">
																								<input name="prpLpersonVeriSumLoss" class="common" style="" value="<fmt:formatNumber value="${pageScope.prpLperson2.veriSumLoss}" pattern="#"/>" onBlur="return calSumPersonVeriDefLoss(this);" ${compensatebackReadOnly}>
																							</td>
																							<td class="input" style="width: 11%">
																								<input name="prpLpersonVeriSumReject" class="common" style=""  value="<fmt:formatNumber value="${pageScope.prpLperson2.veriSumReject}" pattern="#"/>" onBlur="return calSumPersonVeriDefLoss(this);" ${compensatebackReadOnly}>
																							</td>
																							<td class="input" style="width: 11%">
																								<input name="prpLpersonVeriSumDefLoss" class="common" style=""  value="<fmt:formatNumber value="${pageScope.prpLperson2.veriSumDefLoss}" pattern="#"/>" onBlur="return calSumPersonVeriDefLoss(this);" ${compensatebackReadOnly}>
																							</td>
																							<td class="input" style="width: 35%">
																								<input name="prpLpersonVeriRejectReason" class="common" style=""  value="${pageScope.prpLperson2.veriRejectReason}" ${compensatebackReadOnly}>
																							</td>
																							<td class="input" style="width: 7%" colspan="4" align="right">
																							</td>
																						</tr>
																					</c:if>
																				</c:forEach>
																			</tbody>
																		</table>
																	</span>
																</td>
															</tr>
															<tr>
																<td colspan="6">
																	<table class="common" style="width: 100%">
																		<td class='title' colspan="2" width="30%">
																			<s:text name="certainLoss.prpLscheduleMainWF.LossSum" />
																			<input class='readonly' readonly="true" style='width: 80px' name='prpLpersonSumLossSum'>
																			<!--报损金额-->
																		</td>
																		<td class='title' colspan="2" width="30%">
																			<s:text name="db.prpLprop.sumReject" />:
																			<input class='readonly' readonly="true" style='width: 80px' name='prpLpersonSumRejectSum'>
																			<!--剔除金额-->
																		</td>
																		<td class='title' colspan="2" width="40%">
																			<s:text name="certainLoss.lossAmount" />:
																			<input class='readonly' readonly="true" style='width: 80px' name='prpLpersonSumDefLossSum'>
																			<!--定损金额-->
																		</td>
																	</table>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
										<td class="input" style="width: 4%;display: none;">
											<div align="center">
												<input type=button name="buttonPersonDelete" class="smallbutton" onclick="deleteRowTable(this,'trPersonLoss')" value="-" style="cursor: hand">
											</div>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</span>
			</td>
		</tr>
	</table>
</span>
