<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-02-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@page import="com.sinosoft.claim.schema.model.PrpCitemKind"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
 	function viewDangerUnitPersonLoss(field){
		for (var i=1;i<fm.prpLpersonLossSerialNo.length;i++){
		 if(fm.prpLpersonLossDangerNo[i]==field){
			 var count      = i;
			 var policyNo   = fm.policyno.value;
			 var damageDate = fm.damageStartDate.value;
			 var submitStr  = "getDangerUnit.do?policyNo="+policyNo+"&damageDate="+damageDate+"&PageType=PersonLoss&openerIndex=" + count;  
		   window.open(submitStr,'查看危险单位信息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
		  }
		}
	}
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLpersonLossKindName'],:input[name='prpLpersonLossLiabDetailName'],:input[name='prpLpersonLossPayObjectSerialNo']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		});
	})
</script>
<!--建立显示的录入条，可以收缩显示的-->
<span style="display: none;">
	<table class="common" style="display: none;" id="PersonFeeLoss_Data" cellspacing="1" cellpadding="0">
		<tbody>
			<tr name="prpLpersonFeeLossObject">
				<td class="input" style="width: 20%">
					<div align="center">
						<!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3  START-->
						<input type="text" name="prpLpersonLossKindCode" class="codecode" style="width: 20%;" readonly="readonly"
							ondblclick="code_CodeSelect(this,'PolicyKindCodeForPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);cleanChangeCodeSelectForPersonLossLiabDetailCode(this);"
							onchange="code_CodeChange(this,'PolicyKindCodeForPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);cleanChangeCodeSelectForPersonLossLiabDetailCode(this);"
							onkeyup="code_CodeSelect(this,'PolicyKindCodeForPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="inputKindControl(this,'prpLpersonLoss');clearPrpLpersonLoss(this);setAccidentType();">
						<input type="text" name="prpLpersonLossKindName" class="codename" style="width: 70%" readonly="readonly"
							ondblclick="code_CodeSelect(this, 'PolicyKindCodeForPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
							onchange="code_CodeChange(this, 'PolicyKindCodeForPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
							onkeyup="code_CodeSelect(this, 'PolicyKindCodeForPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="inputKindControl(this,'prpLpersonLoss');clearPrpLpersonLoss(this);setAccidentType();">
						<!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3  END-->
						<input type="hidden" name="prpLpersonLossItemKindNo">
						<%--<img src="${ctx }/images/bgMarkMustInput.jpg">--%>
					</div>
				</td>
				<td class="inputsubsub" align="center" style="width: 12%">
					<!-- 費用類別-->
					<input type="hidden" name="personLossSerialNo" style="width: 20%">
					<!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3  START-->
					<input name="prpLpersonLossLiabDetailCode" class="codecode" readonly="readonly" style="width: 20%;" title="人傷費用類別代碼" value="" ondblclick="getCodeSelectForPersonLossLiabDetailCode(this,'ondblclick','code');"
						onchange="getCodeSelectForPersonLossLiabDetailCode(this,'ondblclick','code');" onkeyup="getCodeSelectForPersonLossLiabDetailCode(this,'ondblclick','code');" onblur="clearPrpLpersonFeeLoss(this);">
					<input name="prpLpersonLossLiabDetailName" class="codename" readonly="readonly" style="width: 70%" title="人傷費用類別名稱" value="" ondblclick="getCodeSelectForPersonLossLiabDetailCode(this,'ondblclick','name');"
						onchange="getCodeSelectForPersonLossLiabDetailCode(this,'onchange','name');" onkeyup="getCodeSelectForPersonLossLiabDetailCode(this,'onkeyup','name');" onblur="clearPrpLpersonFeeLoss(this);">
					<input name="medicDeathFlag" type="hidden" title="人傷費用類別類型" value="">
					<!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3  END-->
				</td>
				<td class="inputsubsub" align="center" style="width: 10%">
					<!-- 殘廢等級-->
					<input type="hidden" name="prpLpersonLossUnitAmount" class="common" style="width: 65px">
					<input type="hidden" name="prpLpersonLossLossQuantity" class="common" style="width: 65px">
					<input type="hidden" name="prpLpersonLossHospitalDays">
					<s:select name="prpLdisabilityLimitRatingCode" listKey="key" listValue="value" list="#request.injuryGradeList" onchange="calRealpay2ForSunnyNew(this);" style="width:98%;" />
					<input name="prpLpersonLossInjuryGrade" type="hidden" value="">
				</td>
				<td class="inputsubsub" align="center" style="width: 8%">
					<!-- 核定賠償-->
					<input name="prpLpersonLossSumDefPay" class="common" style="width: 75px" value="0" title="核定賠償" onfocus="cacheData(this);" onchange="validateMoney(this);calRealpay2ForSunnyNew(this);">
				</td>
				<td class="inputsubsub" align="center" style="width: 8%">
					<!-- 強制險給付金額-->
					<input name="prpLpersonLossCompelPay" class="common" style="width: 75px" value="0" title="強制險給付金額" onfocus="cacheData(this);" onchange="validateMoney(this);calRealpay2ForSunnyNew(this);">
				</td>
				<td class="inputsubsub" align="center" style="width: 7%">
					<!-- 自負額 -->
					<input name="prpLpersonLossSumRest" class="common" style="width: 75px;" value="0" title="自負額" onfocus="cacheData(this);" onchange="validateMoney(this);calRealpay2ForSunnyNew(this);">
				</td>
				<td class="inputsubsub" align="center" style="width: 8%">
					<!-- 賠付金額 -->
					<input type="hidden" name="prpLpersonLossClaimRate" value="100">
					<input type="hidden" name="personCount" value="${requestScope.personCount}">
					<input name="prpLpersonLossSumRealPay" class="readonly" readonly style="width: 65px" value="0">
					<input type="hidden" name="prpLpersonLossSumLoss" value="0" class="common">
					<input type="hidden" name="prpLpersonLossFlag">
					<input type="hidden" name="prpLpersonLossFamilyNo">
					<input type="hidden" name="prpLpersonLossLiabCode">
					<input type="hidden" name="prpLpersonLossLiabName">
					<input type="hidden" name="prpLpersonLossJobCode">
					<input type="hidden" name="prpLpersonLossJobName">
					<input type="hidden" name="prpLpersonLossItemAddress">
					<input type="hidden" name="prpLpersonLossUnit">
					<input type="hidden" name="prpLpersonLossCurrency" value="${LOCAL_CURRENCY}">
					<input type="hidden" name="prpLpersonLossAmount">
					<input type="hidden" name="prpLpersonLossCurrency1" value="${LOCAL_CURRENCY}">
					<input type="hidden" name="prpLpersonLossItemValue">
					<input type="hidden" name="prpLpersonLossCurrency2" value="${LOCAL_CURRENCY}">
					<input type="hidden" name="prpLpersonLossDeductible" value="0">
					<input type="hidden" name="prpLpersonLossCurrency3" value="${LOCAL_CURRENCY}">
					<input type="hidden" name="prpLpersonLossCurrency4" value="${LOCAL_CURRENCY}">
					<input type='hidden' name="prpLpersonLossExceptDeductiblePay" value="0">
					<input type='hidden' name="prpLpersonLossExceptDeductibleRate" value="0">
				</td>
				<td class='input' style="width: 9%" title="请单击选择賠付對象讯息">
					<%-- 賠付對象序号 --%>
					<input class='common' type="text" name="prpLpersonLossPayObjectSerialNo" style="width: 98%;" value="" onclick="setPrpObjectinfoSerialNo(this);" readonly="readonly">
					<%--onblur="checkPayObjectSerialNo(this);" --%>
				</td>
				<td class='input' style="width: 5%">
					<s:select name="prpLpersonLossReservedEstimate" list="#attr.reservedEstimateList" onchange="setAccidentType()"></s:select>
				</td>
				<!-- delete by chenjie 20150601 需求變更-095 
				<td class='input' style="width: 10%" >
				</td>
				-->
				<td class="inputsubsub" style="width: 3%">
					<div align="center">
						<input type=button name="buttonPersonFeeLossDelete" class="smallbutton" onclick="deletePrpLpersonFeeLossObject(this);" value="-" readonly style="cursor: hand">
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</span>
<table class="common" align="center">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="PersonImg" onclick="showPage(this,spanPerson);changeCompensateFlag('1');"><b><s:text name="prompt.victims.message" /></b>
			<%--任意险受害人讯息--%>
			<%--赔付人员信息 --%>
			<br>
			<span style="display: none">
				<table class="common" style="display: none" id="Person_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr name="prpLpersonLossObject">
							<td class="subformtitle" style="width: 96%">
								<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
									<tbody>
										<tr>
											<input type="hidden" name="prpLpersonLossSerialNo">
											<input type='hidden' name="prpLpersonLossExceptDeductibleRate1" value="0">
											<input type="hidden" name="prpLpersonLossDangerNo" value="1" onClick="viewDangerUnitPersonLoss(this);">
											<input type="hidden" name="prpLpersonLossPersonNo">
											<td class="title" style="width: 15%">
												<s:text name="db.prpLperson.personName" />
												:
											</td>
											<%-- 人员姓名 --%>
											<td class="input" style="width: 18%">
												<input class='common' style="width: 110" name="prpLpersonLossPersonName" maxlength=20 title="人員姓名">
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<input type="hidden" name="prpLpersonLossAmountTmp">
											<td class="title" style="width: 15%">
												<s:text name="db.prpLperson.personSex" />
												：
											</td>
											<%-- 性别 --%>
											<td class="input" style="width: 18%">
												<select name="prpLpersonLossSex" class='common' style="width: 110">
													<option value="1"><s:text name="certainLoss.male" /></option>
													<%-- 男 --%>
													<option value="2"><s:text name="certainLoss.female" /></option>
													<%-- 女 --%>
												</select>
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
											<td class='title' style="width: 18%">
												<s:text name="db.prpLlawsuit.licenseNo" />
												：
											</td>
											<%-- 号牌号码 --%>
											<td class="input" style="width: 15%">
												<s:select name="prpLpersonLossFamilyName" listKey="licenseNo" listValue="licenseNo" list="#request.licenseNoList" headerKey="" headerValue="" style="width: 110" />
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLdriver.birthday" />
												：
											</td>
											<%-- 出生年份 --%>
											<td class="input" style="width: 18%">
												<rc:rcDate class='common' style="width: 110" name="prpLpersonLossBirthday" onchange="updatePersonLossAge(this);" title="出生年份" wdatePicker="true" />
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLpersonloss.age" />
												：
											</td>
											<%-- 年齡 --%>
											<td class="input" style="width: 18%">
												<input class='common' name="prpLpersonLossAge" style="width: 110" maxlength="3" title="年齡" onfocus="cacheData(this);" onchange="validateAge(this,1,120);">
											</td>
											<td class="title" style="width: 18%">
												<s:text name="db.prpLpersonLoss.identityOfInjuredPerson" />
												：
											</td>
											<%-- 受害人身份 --%>
											<td class="input" style="width: 15%">
												<s:select name="prpLpersonLossIdentityOfInjuredPerson" listKey="key" listValue="value" list="#request.identityOfInjuredPersonList" style="width: 150px" />
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLpersonLoss.rideSituation" />
												：
											</td>
											<%-- 出事當時乘坐狀況 --%>
											<td class="input" style="width: 18%">
												<s:select name="prpLpersonLossRideSituation" listKey="key" listValue="value" list="#request.rideSituationList" onchange="countPersonLossNumber();" style="width: 110" />
											</td>
											<td class="title" style="width: 15%">
												<s:text name="db.prpDDriver.identifyNumber" />
												：
											</td>
											<%-- 身份證號 --%>
											<td class="input" style="width: 18%">
												<input class='common' name="prpLpersonLossIdentifyNumber" style="width: 110" title="身份證號" onchange="changeIdentifyNumber(this);">
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 18%">
												<s:text name="db.prpLpersonLoss.medicalCode" />
												：
											</td>
											<%-- 受害人健保就醫代號 --%>
											<td class="input" style="width: 15%">
												<s:select name="prpLpersonLossMedicalCode" listKey="key" listValue="value" list="#request.medicalCodeList" style="width: 150px" />
											</td>
										</tr>
										<tr>
											<td class="title" colspan="4">
												<s:text name="db.prpLpersonLoss.endCaseAndRecoverFlag" />
												：
												<%-- 個別受害人醫療給付是否結案且待健保追償（返還） --%>
												<s:set name="flagYes" value="%{getText('regist.prpLregist.yes')}"></s:set>
												<s:set name="flagNo" value="%{getText('regist.prpLregist.no')}"></s:set>
												<s:select name="prpLpersonLossEndCaseAndRecoverFlag" list="#{'0':#flagNo,'1':#flagYes}" listKey="key" listValue="value" />
											</td>
											<td class="title" style="width: 18%">婚姻别：</td>
											<%-- 婚姻别 --%>
											<td class="input" style="width: 20%">
												<select name="prpLpersonLossIsMarried" style="width: 120px">
													<option value="1" selected="selected"><s:text name="db.prpLdriver.Married" /></option>
													<%-- 已婚 --%>
													<option value="2"><s:text name="db.prpLdriver.Unmarried" /></option>
													<%-- 未婚 --%>
												</select>
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLpersonLoss.telephoneNo" />
												：
											</td>
											<%-- 受害人电话 --%>
											<td class="input" style="width: 18%">
												<input class='common' name="prpLpersonLossTelephoneNo" style="width: 110" title="受害人市話">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLpersonLoss.prosecutorsOffice" />
												：
											</td>
											<%-- 地檢署 --%>
											<td class="input" style="width: 18%">
												<s:select name="prpLpersonLossProsecutorsOffice" listKey="key" listValue="value" list="#request.prosecutorsOfficeList" style="width: 100%" />
											</td>
											<td class="title" style="width: 18%">
												<s:text name="db.prpLpersonLoss.courtDoctor" />
												：
											</td>
											<%-- 法醫師/檢驗員姓名 --%>
											<td class="input" style="width: 15%">
												<input class='common' name="prpLpersonLossCourtDoctor" style="width: 110" title="法醫師/檢驗員姓名">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLpersonLoss.mobilePhone" />
												：
											</td>
											<%-- 受害人手機 --%>
											<td class="input" style="width: 18%">
												<input class='common' name="prpLpersonLossMobilePhone" style="width: 110" title="受害人手機">
											</td>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLpersonLoss.prosecutor" />
												：
											</td>
											<%-- 檢察官姓名 --%>
											<td class="input" style="width: 18%">
												<input class='common' name="prpLpersonLossProsecutor" style="width: 110" title="檢察官姓名">
											</td>
											<td class="title" style="width: 18%">
												<s:text name="db.prpLpersonLoss.garageHeadName" />
												：
											</td>
											<%-- 修車廠負責人姓名 --%>
											<td class="input" style="width: 15%">
												<input class='common' name="prpLpersonLossGarageHeadName" style="width: 110" title="修車廠負責人姓名">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLpersonLoss.hospital" />
												：
											</td>
											<%-- 醫院名稱 --%>
											<td class="input" colspan="3">
												<input name="prpLpersonLossHospitalCode" onkeyup="getHospital(this,'codeCode','0,1')" onblur="isHospital(this,'codeCode');" class='common' style="width: 110" title="醫院代碼">
												<input name="prpLpersonLossHospitalName" onkeyup="getHospital(this,'codeName','-1,0')" class='common' style="width: 280" title="醫院名稱">
											</td>
											<td class="title" style="width: 18%">
												<s:text name="db.prpLpersonLoss.doctor" />
												：
											</td>
											<%-- 醫師姓名 --%>
											<td class="input" style="width: 15%">
												<input class='common' name="prpLpersonLossDoctor" style="width: 110" title="醫師姓名">
											</td>
										</tr>
										<tr>
											<input type="hidden" class='common' name="prpLpersonLossArrangeRate" value="100" style="width: 110px" value="100">
											<td class="title" style="width: 15%">
												<s:text name="db.prpLpersonLoss.casualties" />
												：
											</td>
											<%-- 傷亡情形 --%>
											<td class="input" style="width: 18%">
												<s:select name="prpLpersonLossCasualties" listKey="key" listValue="value" list="#request.casualtiesList" style="width: 110" onchange="countPersonLossNumber();" />
											</td>
											<td class='title' style="width: 15%">
												<s:text name="db.prpLpersonLoss.indemnityDutyRate" />
												：
											</td>
											<%-- 肇事責任比率 --%>
											<td class='input' style="width: 18%">
												<input type="text" class='common' name="prpLpersonLossIndemnityDutyRate" style="width: 110px" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);" value="0" title="肇事責任比率">
												%
												<input type="hidden" name="prpLpersonLossDutyDeductibleRate">
												<input type="hidden" name="prpLpersonLossDeductibleRate">
												<input type="hidden" name="prpLpersonLossDriverDeductibleRate">
												<input type="hidden" name="prpLpersonLossMainKindDeductibleRate">
											</td>
											<td class='title' style="width: 18%">
												<s:text name="compensate.payTotal" />
												：
											</td>
											<%-- 赔付合计 --%>
											<td class='input' style="width: 15%">
												<input class='readonly' style='width: 110px' readonly name="prpLpersonLossSumRealPay1" value="0">
												<input type='hidden' name="prpLpersonLossSumDefPay1" value="0">
											</td>
										</tr>
										<tr>
											<td colspan="7">
												<table name="PrpLpersonFeeLoss" class="common" align="center" cellspacing="1" cellpadding="0">
													<thead>
														<tr>
															<td class="subformtitle" colspan="11">
																<s:text name="prompt.compensate.costInfo" />
																<%-- 费用信息 --%>
															</td>
														</tr>
														<tr>
															<td class="centertitle" style="width: 20%;">
																<s:text name="db.prpDrate.kindName" />
															</td>
															<%-- 险别名称 --%>
															<td class="centertitle" style="width: 12%;">
																<s:text name="claim.cost" />
															</td>
															<%-- 费用 --%>
															<td style="display: none" class="centertitle">
																<s:text name="db.prpLloss.unitPrice" />
															</td>
															<%-- 单价 --%>
															<td style="display: none" class="centertitle">
																<s:text name="db.prpLassureDetail.count" />
															</td>
															<%-- 数量 --%>
															<td style="display: none" class="centertitle">
																<s:text name="compensate.amountNucDamage" />
															</td>
															<%-- 核损金额 --%>
															<td class="centertitle" style="width: 10%;">
																<s:text name="db.prpLdisabilityLimit.ratingCode" />
															</td>
															<%-- 残废等级 --%>
															<td class="centertitle" style="width: 8%;">
																<s:text name="compensate.approvedCompen" />
															</td>
															<%-- 核定赔偿 --%>
															<td class="centertitle" style="width: 8%;">
																<s:text name="compensate.insuranceInde" />
															</td>
															<%-- 交强险赔款 --%>
															<td class="centertitle" style="width: 7%;">
																<s:text name="db.prpLpersonLoss.sumRest" />
															</td>
															<%-- 自負額 --%>
															<td class="centertitle" style="width: 8%;">
																<s:text name="db.prpLreplevynew.sumpaid" />
															</td>
															<%-- 赔付金额 --%>
															<td class="centertitle" style="width: 9%;">
																<s:text name="db.prpLpersonLoss.payObjectSerialNo" />
															</td>
															<%-- 赔付对象讯息 --%>
															<td class="centertitle" style="width: 5%;">保留預估</td>
															<!-- delete by chenjie 20150601 需求變更-095 
															<td class="centertitle" style="width: 10%;">肇責類型</td>
															-->
															<td class="centertitle" style="width: 3%">&nbsp;</td>
														</tr>
													</thead>
													<tfoot>
														<tr>
															<td class="titlesubsub" colspan="9" style="width: 97%"></td>
															<td class="title" align="right" style="width: 3%">
																<div align="center">
																	<input type="button" value="+" class="smallbutton" onclick="insertPrpLpersonFeeLossObject(this);" name="buttonPersonFeeLossInsert" readonly style="cursor: hand">
																</div>
															</td>
														</tr>
													</tfoot>
													<tbody>
													</tbody>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td class="input" style="width: 4%">
								<div align="center">
									<input type=button name="buttonPersonDelete" class="smallbutton" onclick="deletePrpLpersonLossObject(this);countPersonLossNumber();setAccidentType();" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
			<span id="spanPerson" style="display: none">
				<%-- 多行输入展现域 --%>
				<table id="person" class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<%--<td class="centertitle" style="width: 4%"><s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType" /></td> 险别 --%>
							<td class="centertitle" style="width: 100%" colspan=2>
								<s:text name="db.prpLregistText.context" />
							</td>
							<%-- 内容 --%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" style="width: 96%">
								<s:text name="prompt.compensate.addRemove03" />
								<%-- (按"+"號鍵增加受害人訊息，按"-"號鍵刪除訊息) --%>
								<input type="button" class="bigbutton" value="歷史賠付受害人訊息" name="buttonPersonHistory" onclick="showPersonHistory();" />
							</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class="smallbutton" onclick="insertPrpLpersonLossObject();countPersonLossNumber();" name="buttonPersonInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody id="PrpLpersonLoss">
						<script language="javascript">
					var damageKind = new Array();
					<c:forEach items="${damageKindList }" var="itemKindTemp" varStatus="itemKindStatus">
						damageKind[${itemKindStatus.index }]   ="${itemKindTemp.kindCode }";
					</c:forEach>
					</script>
						<c:set var="kindCode" value="" />
						<c:set var="personNo" value="0" />
						<c:set var="personSerialNo" value="1" />
						<c:forEach items="${requestScope.prpLpersonLoss.prpLpersonLossList}" var="prpLpersonLossTemp1">
							<c:if test="${prpLpersonLossTemp1.personNo!=pageScope.personNo}">
								<tr name="prpLpersonLossObject">
									<td class="subformtitle" style="width: 96%">
										<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
											<tbody>
												<tr>
													<input type="hidden" name="prpLpersonLossSerialNo" value="${pageScope.personSerialNo}">
													<input type='hidden' name="prpLpersonLossExceptDeductibleRate1" value="${prpLpersonLossTemp1.exceptDeductibleRate}">
													<input type="hidden" name="prpLpersonLossDangerNo" value="${prpLpersonLossTemp1.dangerNo}" onClick="viewDangerUnitPersonLoss(this);">
													<input type="hidden" name="prpLpersonLossPersonNo" value="${prpLpersonLossTemp1.personNo}">
													<td class="title" style="width: 15%">
														<s:text name="db.prpLperson.personName" />
														：
													</td>
													<%-- 人员姓名 --%>
													<td class="input" style="width: 18%">
														<input class='common' style="width: 110" name="prpLpersonLossPersonName" value="${prpLpersonLossTemp1.personName}">
														<img src="/claim/images/bgMarkMustInput.jpg">
													</td>
													<input type="hidden" name="prpLpersonLossAmountTmp">
													<td class="title" style="width: 15%">
														<s:text name="db.prpLperson.personSex" />
														：
													</td>
													<%-- 性别 --%>
													<td class="input" style="width: 18%">
														<select name="prpLpersonLossSex" class='common' style="width: 110">
															<option value="1" <c:if test="${fn:trim(prpLpersonLossTemp1.sex)=='1'}"><c:out value="selected"/></c:if>><s:text name="certainLoss.male" /></option>
															<%-- 男 --%>
															<option value="2" <c:if test="${fn:trim(prpLpersonLossTemp1.sex)=='2'}"><c:out value="selected"/></c:if>><s:text name="certainLoss.female" /></option>
															<%-- 女 --%>
														</select>
														<img src="/claim/images/bgMarkMustInput.jpg">
													</td>
													<td class='title' style="width: 18%">
														<s:text name="db.prpLlawsuit.licenseNo" />
														：
													</td>
													<%-- 号牌号码 --%>
													<td class="input" style="width: 15%">
														<c:set var="tempSelectedValue" value="${prpLpersonLossTemp1.familyName}" />
														<s:select name="prpLpersonLossFamilyName" value="#attr.tempSelectedValue" listKey="licenseNo" listValue="licenseNo" list="#request.licenseNoList" headerKey="" headerValue="" style="width: 110" />
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLdriver.birthday" />
														：
													</td>
													<%-- 出生年份 --%>
													<td class="input" style="width: 18%">
														<rc:rcDate class='common' style="width:110" name="prpLpersonLossBirthday" onchange="updatePersonLossAge(this);" value="${prpLpersonLossTemp1.birthday}" wdatePicker="true" />
														<img src="${ctx}/images/bgMarkMustInput.jpg">
													</td>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLperson.personAge" />
														：
													</td>
													<%-- 年齡 --%>
													<td class="input" style="width: 18%">
														<input class='common' name="prpLpersonLossAge" value="${prpLpersonLossTemp1.age}" style="width: 110" maxlength="3" title="年齡" onfocus="cacheData(this);" onchange="validateAge(this,1,120);">
													</td>
													<td class="title" style="width: 18%">
														<s:text name="db.prpLpersonLoss.identityOfInjuredPerson" />
														：
													</td>
													<%-- 受害人身份 --%>
													<td class="input" style="width: 15%">
														<c:set var="tempSelectedValue" value="${prpLpersonLossTemp1.identityOfInjuredPerson}" />
														<s:select name="prpLpersonLossIdentityOfInjuredPerson" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.identityOfInjuredPersonList" style="width: 150px" />
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLpersonLoss.rideSituation" />
														：
													</td>
													<%-- 出事當時乘坐狀況 --%>
													<td class="input" style="width: 18%">
														<c:set var="tempSelectedValue" value="${prpLpersonLossTemp1.rideSituation}" />
														<s:select name="prpLpersonLossRideSituation" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.rideSituationList" style="width: 110" onchange="countPersonLossNumber();" />
													</td>
													<td class="title" style="width: 15%">
														<s:text name="db.prpDDriver.identifyNumber" />
														：
													</td>
													<%-- 身份證號 --%>
													<td class="input" style="width: 18%">
														<input class='common' name="prpLpersonLossIdentifyNumber" style="width: 110" title="身份證號" value="${prpLpersonLossTemp1.identifyNumber}"  onchange="changeIdentifyNumber(this);">
														<img src="${ctx}/images/bgMarkMustInput.jpg">
													</td>
													<td class="title" style="width: 18%">
														<s:text name="db.prpLpersonLoss.medicalCode" />
														：
													</td>
													<%-- 受害人健保就醫代號 --%>
													<td class="input" style="width: 15%">
														<c:set var="tempSelectedValue" value="${prpLpersonLossTemp1.medicalCode}" />
														<s:select name="prpLpersonLossMedicalCode" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.medicalCodeList" style="width: 150px" />
													</td>
												</tr>
												<tr>
													<td class="title" colspan="4">
														<s:text name="db.prpLpersonLoss.endCaseAndRecoverFlag" />
														：
														<s:set name="flagYes" value="%{getText('regist.prpLregist.yes')}"></s:set>
														<s:set name="flagNo" value="%{getText('regist.prpLregist.no')}"></s:set>
														<c:set var="tempSelectedValue" value="${prpLpersonLossTemp1.endCaseAndRecoverFlag}" />
														<s:select name="prpLpersonLossEndCaseAndRecoverFlag" value="#attr.tempSelectedValue" list="#{'0':#flagNo,'1':#flagYes}" listKey="key" listValue="value" />
													</td>
													<td class="title" style="width: 18%">婚姻别：</td>
													<%-- 婚姻别 --%>
													<td class="input" style="width: 20%">
														<select name="prpLpersonLossIsMarried" style="width: 120px">
															<option value="1" <c:if test="${prpLpersonLossTemp1.isMarried=='1' }">selected</c:if>><s:text name="db.prpLdriver.Married" /></option>
															<%--已婚--%>
															<option value="2" <c:if test="${prpLpersonLossTemp1.isMarried=='2' }">selected</c:if>><s:text name="db.prpLdriver.Unmarried" /></option>
															<%--未婚--%>
														</select>
														<img src="${ctx }/images/bgMarkMustInput.jpg">
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLpersonLoss.telephoneNo" />
														：
													</td>
													<%-- 受害人市話 --%>
													<td class="input" style="width: 18%">
														<input class='common' name="prpLpersonLossTelephoneNo" value="${prpLpersonLossTemp1.telephoneNo}" style="width: 110" title="受害人電話">
													</td>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLpersonLoss.prosecutorsOffice" />
														：
													</td>
													<%-- 地檢署 --%>
													<td class="input" style="width: 18%">
														<c:set var="tempSelectedValue" value="${prpLpersonLossTemp1.prosecutorsOffice}" />
														<s:select name="prpLpersonLossProsecutorsOffice" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.prosecutorsOfficeList" style="width: 100%" />
													</td>
													<td class="title" style="width: 18%">
														<s:text name="db.prpLpersonLoss.courtDoctor" />
														：
													</td>
													<%-- 法醫師/檢驗員姓名 --%>
													<td class="input" style="width: 15%">
														<input class='common' name="prpLpersonLossCourtDoctor" value="${prpLpersonLossTemp1.courtDoctor}" style="width: 110" title="法醫師/檢驗員姓名">
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLpersonLoss.mobilePhone" />
														：
													</td>
													<%-- 受害人手機 --%>
													<td class="input" style="width: 18%">
														<input class='common' name="prpLpersonLossMobilePhone" value="${prpLpersonLossTemp1.mobilePhone}" style="width: 110" title="受害人手機">
													</td>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLpersonLoss.prosecutor" />
														：
													</td>
													<%-- 檢察官姓名 --%>
													<td class="input" style="width: 18%">
														<input class='common' name="prpLpersonLossProsecutor" value="${prpLpersonLossTemp1.prosecutor}" style="width: 110" title="檢察官姓名">
													</td>
													<td class="title" style="width: 18%">
														<s:text name="db.prpLpersonLoss.garageHeadName" />
														：
													</td>
													<%-- 修車廠負責人姓名 --%>
													<td class="input" style="width: 15%">
														<input class='common' name="prpLpersonLossGarageHeadName" value="${prpLpersonLossTemp1.garageHeadName}" style="width: 110" title="修車廠負責人姓名">
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLpersonLoss.hospital" />
														：
													</td>
													<%-- 醫院名稱 --%>
													<td class="input" colspan="3">
														<input class='common' name="prpLpersonLossHospitalCode" onkeyup="getHospital(this,'codeCode','0,1')" onblur="isHospital(this,'codeCode');" style="width: 110" title="醫院代碼" value="${prpLpersonLossTemp1.hospitalCode}">
														<input class='common' name="prpLpersonLossHospitalName" onkeyup="getHospital(this,'codeName','-1,0')" style="width: 280" title="醫院名稱" value="${prpLpersonLossTemp1.hospitalName}">
													</td>
													<td class="title" style="width: 18%">
														<s:text name="db.prpLpersonLoss.doctor" />
														：
													</td>
													<%-- 醫師姓名 --%>
													<td class="input" style="width: 15%">
														<input class='common' name="prpLpersonLossDoctor" value="${prpLpersonLossTemp1.doctor}" style="width: 110" title="醫師姓名">
													</td>
												</tr>
												<tr>
													<input type="hidden" class='common' name="prpLpersonLossArrangeRate" value="100" style="width: 110px" value="100">
													<td class="title" style="width: 15%">
														<s:text name="db.prpLpersonLoss.casualties" />
														：
													</td>
													<%-- 傷亡情形 --%>
													<td class="input" style="width: 18%">
														<c:set var="tempSelectedValue" value="${prpLpersonLossTemp1.casualties}" />
														<s:select name="prpLpersonLossCasualties" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.casualtiesList" style="width: 110" onchange="countPersonLossNumber();" />
													</td>
													<td class='title' style="width: 15%">
														<s:text name="db.prpLpersonLoss.indemnityDutyRate" />
														：
													</td>
													<%-- 肇事責任比率 --%>
													<td class='input' style="width: 18%">
														<input type="text" class='common' name="prpLpersonLossIndemnityDutyRate" style="width: 110px" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);"
															value="<fmt:formatNumber value='${prpLpersonLossTemp1.indemnityDutyRate}'  maxFractionDigits='2'/>" title="肇事責任比率">
														%
														<input type="hidden" name="prpLpersonLossDutyDeductibleRate" value="${prpLpersonLossTemp1.dutyDeductibleRate}">
														<input type="hidden" name="prpLpersonLossDeductibleRate" value="${prpLpersonLossTemp1.deductiblerate}">
														<input type="hidden" name="prpLpersonLossDriverDeductibleRate">
														<input type="hidden" name="prpLpersonLossMainKindDeductibleRate">
													</td>
													<td class='title' style="width: 15%">
														<s:text name="compensate.payTotal" />
														：
													</td>
													<%-- 赔付合计 --%>
													<td class='input' style="width: 18%">
														<input class='readonly' style='width: 110' readonly name="prpLpersonLossSumRealPay1" value="<fmt:formatNumber value='${prpLpersonLossTemp1.sumRealPay1}' pattern='#'/>">
														<input type='hidden' name="prpLpersonLossSumDefPay1" value="0">
													</td>
												</tr>
												<tr>
													<td colspan="7">
														<%-- 多行输入展现域 --%>
														<table name="PrpLpersonFeeLoss" class="common" align="center" cellspacing="1" cellpadding="0" style="width: 100%">
															<thead>
																<tr>
																	<td class="subformtitle" colspan="11">
																		<s:text name="prompt.compensate.costInfo" />
																	</td>
																</tr>
																<tr>
																	<td class="centertitle" style="width: 20%">
																		<s:text name="db.prpDrate.kindName" />
																	</td>
																	<%-- 险别名称 --%>
																	<td class="centertitle" style="width: 12%">
																		<s:text name="claim.cost" />
																	</td>
																	<%-- 费用类别 --%>
																	<td class="centertitle" style="width: 10%">
																		<s:text name="db.prpLdisabilityLimit.ratingCode" />
																	</td>
																	<%-- 残废等级 --%>
																	<td class="centertitle" style="width: 8%">
																		<s:text name="compensate.approvedCompen" />
																	</td>
																	<%-- 核定赔偿 --%>
																	<td class="centertitle" style="width: 8%">
																		<s:text name="compensate.insuranceInde" />
																	</td>
																	<%-- 强制险给付金额 --%>
																	<td class="centertitle" style="width: 7%">
																		<s:text name="db.prpLpersonLoss.sumRest" />
																	</td>
																	<%-- 自負額 --%>
																	<td class="centertitle" style="width: 8%">
																		<s:text name="db.prpLreplevynew.sumpaid" />
																	</td>
																	<%-- 赔付金额 --%>
																	<td class="centertitle" style="width: 9%">
																		<s:text name="db.prpLpersonLoss.payObjectSerialNo" />
																	</td>
																	<%-- 赔付对象讯息 --%>
																	<td class="centertitle" style="width: 5%;">保留預估</td>
																	<!-- delete by chenjie 20150601 需求變更-095 
																	<td class="centertitle" style="width: 10%;">肇責類型</td>
																	-->
																	<td class="centertitle" style="width: 3%">&nbsp;</td>
																</tr>
															</thead>
															<tfoot>
																<tr>
																	<td class="titlesubsub" colspan="9" style="width: 97%"></td>
																	<td class="title" align="right" style="width: 3%">
																		<div align="center">
																			<input type="button" value="+" class="smallbutton" onclick="insertPrpLpersonFeeLossObject(this);" name="buttonPersonFeeLossInsert" readonly style="cursor: hand">
																		</div>
																	</td>
																</tr>
															</tfoot>
															<tbody>
																<c:forEach items="${requestScope.prpLpersonLoss.prpLpersonLossList}" var="prpLpersonLossTemp2">
																	<c:if test="${prpLpersonLossTemp2.personNo==prpLpersonLossTemp1.personNo}">
																		<tr name="prpLpersonFeeLossObject">
																			<td class="input" style="width: 20%">
																				<div align="center">
																					<!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3  START-->
																					<input type="text" name="prpLpersonLossKindCode" class="codecode" style="width: 20%;" value="${prpLpersonLossTemp2.kindCode}"
																						ondblclick="code_CodeSelect(this,'PolicyKindCodeForPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);cleanChangeCodeSelectForPersonLossLiabDetailCode(this);"
																						onchange="code_CodeChange(this,'PolicyKindCodeForPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);cleanChangeCodeSelectForPersonLossLiabDetailCode(this);"
																						onkeyup="code_CodeSelect(this,'PolicyKindCodeForPerson','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="inputKindControl(this,'prpLpersonLoss');clearPrpLpersonLoss(this);setAccidentType();"
																						readonly="readonly">
																					<input type="text" name="prpLpersonLossKindName" class="codename" style="width: 70%" value="${prpLpersonLossTemp2.kindName}"
																						ondblclick="code_CodeSelect(this, 'PolicyKindCodeForPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
																						onchange="code_CodeChange(this, 'PolicyKindCodeForPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);"
																						onkeyup="code_CodeSelect(this, 'PolicyKindCodeForPerson','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);" onblur="inputKindControl(this,'prpLpersonLoss');clearPrpLpersonLoss(this);setAccidentType();"
																						readonly="readonly">
																					<!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3  END-->
																					<input type="hidden" name="prpLpersonLossItemKindNo" value="${prpLpersonLossTemp2.itemKindNo}">
																					<%--<img src="/claim/images/bgMarkMustInput.jpg"> --%>
																				</div>
																			</td>
																			<td class="inputsubsub" align="center" style="width: 12%">
																				<input type="hidden" name="personLossSerialNo" style="width: 30px" value="${pageScope.personSerialNo}">
																				<!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3  START-->
																				<input name="prpLpersonLossLiabDetailCode" class="codecode" style="width: 20%" title="人傷費用類別代碼" value="<c:out value='${prpLpersonLossTemp2.liabDetailCode}' />"
																					ondblclick="getCodeSelectForPersonLossLiabDetailCode(this,'ondblclick','code')" onchange="getCodeSelectForPersonLossLiabDetailCode(this,'onchange','code');"
																					onkeyup="getCodeSelectForPersonLossLiabDetailCode(this,'onkeyup','code');" readonly="readonly" onblur="clearPrpLpersonFeeLoss(this);">
																				<input name="prpLpersonLossLiabDetailName" class="codename" style="width: 70%" title="人傷費用類別名稱" value="<c:out value='${prpLpersonLossTemp2.liabDetailName}' />"
																					ondblclick="getCodeSelectForPersonLossLiabDetailCode(this,'ondblclick','name')" onchange="getCodeSelectForPersonLossLiabDetailCode(this,'onchange','name');"
																					onkeyup="getCodeSelectForPersonLossLiabDetailCode(this,'onkeyup','name');" readonly="readonly" onblur="clearPrpLpersonFeeLoss(this);">
																				<input name="medicDeathFlag" type="hidden" title="人傷費用類別類型" value="<c:out value='${prpLpersonLossTemp2.feeCategory}' />">
																				<!-- mantis：CLM0163，處理人員：DP0713，需求單編號：超額新商品上線險種E9、E3 END-->
																			</td>
																			<td class="inputsubsub" align="center" style="width: 10%">
																				<input type="hidden" name="prpLpersonLossUnitAmount" class="common" style="width: 65px" value="<c:out value='${prpLpersonLossTemp2.unitAmount}' />">
																				<input type="hidden" name="prpLpersonLossLossQuantity" class="common" style="width: 65px" value="<c:out value='${prpLpersonLossTemp2.lossQuantity}' />">
																				<input type="hidden" name="prpLpersonLossHospitalDays" value="<c:out value='${prpLpersonLossTemp2.hospitalDays}' />" onchange="calSumLoss(this);">
																				<c:set var="tempSelectedValue" value='${prpLpersonLossTemp2.injuryGrade}' />
																				<s:select name="prpLdisabilityLimitRatingCode" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.injuryGradeList" onchange="calRealpay2ForSunnyNew(this);" style="width:98%;" />
																				<input type="hidden" name="prpLpersonLossInjuryGrade" value="<c:out value='${prpLpersonLossTemp2.injuryGrade}'/>">
																			</td>
																			<td class="inputsubsub" align="center" style="width: 8%">
																				<input type="text" name="prpLpersonLossSumDefPay" class="common" style="width: 75px" value="<fmt:formatNumber value='${prpLpersonLossTemp2.sumDefPay}' pattern='#'/>" title="核定賠償" onfocus="cacheData(this);"
																					onchange="validateMoney(this);calRealpay2ForSunnyNew(this);">
																			</td>
																			<td class="inputsubsub" align="center" style="width: 8%">
																				<input name="prpLpersonLossCompelPay" class="common" style="width: 75px" value="<fmt:formatNumber value='${prpLpersonLossTemp2.compelPay}' pattern='#'/>" title="強制險給付金額" onfocus="cacheData(this);"
																					onchange="validateMoney(this);calRealpay2ForSunnyNew(this);">
																			</td>
																			<td class="inputsubsub" align="center" style="width: 7%">
																				<input name="prpLpersonLossSumRest" class="common" style="width: 75px" value="<fmt:formatNumber value='${prpLpersonLossTemp2.sumRest}' pattern='#'/>" title="自負額" onfocus="cacheData(this);"
																					onchange="validateMoney(this);calRealpay2ForSunnyNew(this);">
																			</td>
																			<td class="inputsubsub" align="center" style="width: 8%">
																				<input type="hidden" name="prpLpersonLossClaimRate" value="100">
																				<input name="prpLpersonLossSumRealPay" class="readonly" readonly style="width: 65px" value="<fmt:formatNumber value='${prpLpersonLossTemp2.sumRealPay}' pattern='#'/>">
																				<input type="hidden" name="prpLpersonLossSumLoss" class="common" style="width: 75px" value="<c:out value='${prpLpersonLossTemp2.sumLoss}' />">
																				<input type="hidden" name="prpLpersonLossFlag" value="<c:out value='${prpLpersonLossTemp2.flag}' />">
																				<input type='hidden' name="prpLpersonLossExceptDeductiblePay" value="<c:out value='${prpLpersonLossTemp2.exceptDeductiblePay}' />">
																				<input type='hidden' name="prpLpersonLossExceptDeductibleRate" value="<c:out value='${prpLpersonLossTemp2.exceptDeductibleRate}' />">
																				<input type="hidden" name="prpLpersonLossFamilyNo" value="<c:out value='${prpLpersonLossTemp2.familyNo}' />">
																				<input type="hidden" name="prpLpersonLossLiabCode" value="<c:out value='${prpLpersonLossTemp2.liabCode}' />">
																				<input type="hidden" name="prpLpersonLossLiabName" value="<c:out value='${prpLpersonLossTemp2.liabName}' />">
																				<input type="hidden" name="prpLpersonLossJobCode" value="<c:out value='${prpLpersonLossTemp2.jobCode}' />">
																				<input type="hidden" name="prpLpersonLossJobName" value="<c:out value='${prpLpersonLossTemp2.jobName}' />">
																				<input type="hidden" name="prpLpersonLossItemAddress" value="<c:out value='${prpLpersonLossTemp2.itemAddress}' />">
																				<input type="hidden" name="prpLpersonLossUnit" value="<c:out value='${prpLpersonLossTemp2.unit}' />">
																				<input type="hidden" name="prpLpersonLossCurrency" value="${LOCAL_CURRENCY}">
																				<input type="hidden" name="prpLpersonLossAmount" value="<c:out value='${prpLpersonLossTemp2.amount}' />">
																				<input type="hidden" name="prpLpersonLossCurrency1" value="${LOCAL_CURRENCY}">
																				<input type="hidden" name="prpLpersonLossItemValue" value="<c:out value='${prpLpersonLossTemp2.itemValue}' />">
																				<input type="hidden" name="prpLpersonLossCurrency2" value="${LOCAL_CURRENCY}">
																				<input type="hidden" name="prpLpersonLossDeductible" value="<c:out value='${prpLpersonLossTemp2.deductible}' />">
																				<input type="hidden" name="prpLpersonLossCurrency3" value="${LOCAL_CURRENCY}">
																				<input type="hidden" name="prpLpersonLossCurrency4" value="${LOCAL_CURRENCY}">
																			</td>
																			<td class='input' style="width: 9%" title="请单击选择賠付對象讯息">
																				<%-- 賠付對象序号 --%>
																				<input class='common' type="text" name="prpLpersonLossPayObjectSerialNo" style="width: 98%;" value="${prpLpersonLossTemp2.payObjectSerialNo}" onclick="setPrpObjectinfoSerialNo(this);" readonly="readonly">
																				<%--onblur="checkPayObjectSerialNo(this);" --%>
																			</td>
																			<td class='input' style="width: 5%">
																				<s:select name="prpLpersonLossReservedEstimate" list="#attr.reservedEstimateList" value="#attr.prpLpersonLossTemp2.reservedEstimate" onchange="setAccidentType()"></s:select>
																			</td>
																			<!-- delete by chenjie 20150601 需求變更-095 
																			<td class='input' style="width: 10%" >
																			</td>
																			-->
																			<td class="inputsubsub" style="width: 3%">
																				<div align="center">
																					<input type=button name="buttonPersonFeeLossDelete" class="smallbutton" onclick="deletePrpLpersonFeeLossObject(this);" value="-" readonly style="cursor: hand">
																				</div>
																			</td>
																		</tr>
																	</c:if>
																</c:forEach>
															</tbody>
														</table>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
									<td class="title" style="width: 10%">
										<div align="center">
											<input type=button name="buttonPersonDelete" class="smallbutton" onclick="deletePrpLpersonLossObject(this);countPersonLossNumber();" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
								<c:set var="kindCode" value="${prpLpersonLossTemp1.kindCode}" />
								<c:set var="personNo" value="${prpLpersonLossTemp1.personNo}" />
								<c:set var="personSerialNo" value="${pageScope.personSerialNo + 1}" />
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
<%--** 醫院名稱下拉显示的隐藏域 *--%>
<div id="hospitalList" style="background-color: FFFFFF; display: none; cursor: hand; position: absolute; width: 400px;" align="left"></div>
<div id="prpLPayObjectinfo" style='width: 300; display: none; position: absolute; background-color: FFFFFF;' class="common" align="left">
	<ul id="uLprpLPayObjectinfo" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<s:if test="#attr.prpLpayObjectInfo.prpLpayObjectInfoList==null||#attr.prpLpayObjectInfo.prpLpayObjectInfoList.size()==0">
			<li><s:text name="title.compensateEdit.notPaymentObject" />。</li>
			<%-- 沒有賠款給付對象訊息，請錄入賠款給付對象--%>
		</s:if>
		<s:else>
			<c:forEach var="prpLpayObjectInfoTemp" items="${prpLpayObjectInfo.prpLpayObjectInfoList}">
				<li><input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="${prpLpayObjectInfoTemp.id.serialNo}" /> <s:text name="compensate.paymentObject" />${prpLpayObjectInfoTemp.id.serialNo} <s:text
						name="db.prpLcfee.sumPaid" />: <input type="text" name="payObjectPayAmount" onblur="setPayObjectPayAmount();" value="" class="common" style="width: 100px" /></li>
				<%--赔付对象--%>
				<%--赔付金额--%>
			</c:forEach>
		</s:else>
	</ul>
	<ul align="center" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" name="closePrpObjectinfoSerialNo" onclick="hideSubPage(this,'prpLPayObjectinfo')" value="<s:text name='button.close.value' />" /></li>
	</ul>
</div>
<%--** 受害人信息汇总 *--%>
<table class="common" align="center">
	<tr>
		<td class="left">
			&nbsp;&nbsp;<b><s:text name="title.compensateEdit.victimsCompiled" /></b>
		</td>
		<%--** 受害人数汇整 *--%>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1" id="PersonLossNumberCount">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1" style="width: 100%">
				<tr>
					<td class="left" style="width: 20%">
						<s:text name="title.compensateEdit.claimsNo" />
					</td>
					<%--理賠人數--%>
					<td class="right" style="width: 13%">
						<input class="readonly" readonly="readonly" type=text name="personLossNumber" value="0">
					</td>
					<td class="left" style="width: 20%">
						<s:text name="title.compensateEdit.carSituations" />
					</td>
					<%--本車傷亡情形--%>
					<td class="right" style="width: 13%"></td>
					<td class="left" style="width: 20%">
						<s:text name="title.compensateEdit.thirdPartyInsNo" />
					</td>
					<%--第三人責任險傷亡人數 --%>
					<td class="right" style="width: 13%"></td>
				</tr>
				<tr>
					<td class="left" style="width: 20%"></td>
					<td class="right" style="width: 13%"></td>
					<td class="left" style="width: 20%">
						<s:text name="title.compensateEdit.deathNo" />
					</td>
					<%--死亡人數 --%>
					<td class="right" style="width: 13%">
						<input class="readonly" readonly="readonly" type=text name="carDeathNumber" value="0">
					</td>
					<td class="left" style="width: 20%">
						<s:text name="title.compensateEdit.deathNo" />
						<%--死亡人數 --%>
					</td>
					<td class="right" style="width: 13%">
						<input class="readonly" readonly="readonly" type="text" name="threeCarDeathNumber" value="0">
					</td>
				</tr>
				<tr>
					<td class="left" style="width: 20%"></td>
					<td class="right" style="width: 13%"></td>
					<td class="left" style="width: 20%">
						<s:text name="title.compensateEdit.disabledNo" />
					</td>
					<%--殘廢人數 --%>
					<td class="right" style="width: 13%">
						<input class="readonly" readonly="readonly" type=text name="carCrippledNumber" value="0">
					</td>
					<td class="left" style="width: 20%">
						<s:text name="title.compensateEdit.disabledNo" />
					</td>
					<%--殘廢人數 --%>
					<td class="right" style="width: 13%">
						<input class="readonly" readonly="readonly" type="text" name="threeCarCrippledNumber" value="0">
					</td>
				</tr>
				<tr>
					<td class="left" style="width: 20%"></td>
					<td class="right" style="width: 13%"></td>
					<td class="left" style="width: 20%">
						<s:text name="title.compensateEdit.medicalNo" />
					</td>
					<%--醫療人數 --%>
					<td class="right" style="width: 13%">
						<input class="readonly" readonly="readonly" type=text name="carMedicalNumber" value="0">
					</td>
					<td class="left" style="width: 20%">
						<s:text name="title.compensateEdit.medicalNo" />
					</td>
					<%--醫療人數 --%>
					<td class="right" style="width: 13%">
						<input class="readonly" readonly="readonly" type="text" name="threeCarMedicalNumber" value="0">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>