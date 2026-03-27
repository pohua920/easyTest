<%--
****************************************************************************
* DESC	   ：添加人员赔款费用信息页面
* AUTHOR	 ：中科软
* CREATEDATE ： 2013-02-20
* MODIFYLIST ：   Name	   Date			Reason/Contents
*		  ------------------------------------------------------
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
				var count	  = i;
				var policyNo   = fm.policyno.value;
				var damageDate = fm.damageStartDate.value;
				var submitStr  = "getDangerUnit.do?policyNo="+policyNo+"&damageDate="+damageDate+"&PageType=PersonLoss&openerIndex=" + count;  
				window.open(submitStr,'查看危險單位訊息','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
			}
		}
	}
 	/** 设置险别的保险金额  */
 	function setPersonLossAmount(field){
 		var $personObject = $(field).parents("tr[name='prpLpersonLossObject']");
 		var kindCode = $personObject.find(":input[name='prpLpersonLossKindCode']").val();
 		if(kindCode == ""){
 			return;
 		}
 		var $amount = $personObject.find(":input[name='prpLpersonLossAmount']");
 		for(var i=0;i<damageKind.length;i++){
 			if(damageKind[i]==kindCode){
 				$amount.val(damageItemAmount[i]);
 				break;
 			}
 		}
 		
 	}
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLpersonLossKindName'],:input[name='prpLpersonLossLiabDetailName'],:input[name='prpLpersonLossPayObjectSerialNo']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		})
	})
</script>
<!--建立显示的录入条，可以收缩显示的-->
<span style="display: none;">
	<table class="common" style="display: none;" id="PersonFeeLoss_Data" cellspacing="1" cellpadding="0">
		<tbody>
			<tr name="prpLpersonLossObject">
				<td class="input" style="width: 18%">
					<div align="center">
						<input type="hidden" name="serialNo">
						<input type="hidden" name="personLossSerialNo">
						<input type="text" name="prpLpersonLossKindCode" class="codecode" style="width: 20%;" readonly="readonly" onblur="clearPrpLpersonLoss(this);"
							ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" >
						<input type="text" name="prpLpersonLossKindName" class="codename" style="width: 70%" readonly="readonly" onblur="clearPrpLpersonLoss(this);"
							ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" >
						<input type="hidden" name="prpLpersonLossItemKindNo" value="0">
					</div>
				</td>
				<td class="inputsubsub" align="center" style="width: 18%">
					<!-- 費用類別-->
					<input type="text" name="prpLpersonLossLiabDetailCode" class="codecode" readonly="readonly" style="width: 20%;" title="人傷費用類別代碼" value=""
						ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);"
						onchange="code_CodeChange(this, 'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);"
						onkeyup="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);" >
					<input type="text" name="prpLpersonLossLiabDetailName" class="codename" readonly="readonly" style="width: 70%" title="人傷費用類別名稱" value=""
						ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);"
						onchange="code_CodeChange(this, 'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);"
						onkeyup="code_CodeSelect(this,'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);" >
					<input name="medicDeathFlag" type="hidden" title="人傷費用類別類型" value="">
				</td>
				<td class="inputsubsub" align="center" style="width: 8%">
					<!-- 核定賠償-->
					<input type="text" name="prpLpersonLossSumDefPay" class="common" style="width: 75px" value="0" title="核定賠償" onchange="calRealpayForPerson(this);">
				</td>
				<td class="inputsubsub" align="center" style="width: 8%">
					<!-- 自負額 -->
					<input type="text" name="prpLpersonLossDeductible" class="common" style="width: 70px;" value="0" title="自負額" onchange="calRealpayForPerson(this);">
				</td>
				<td class="inputsubsub" align="center" style="width: 8%">
					<!-- 賠付金額 -->
					<input type="text" name="prpLpersonLossSumRealPay" class="readonly" readonly style="width: 75px" value="0">
				</td>
				<td class="inputsubsub" align="center" style="width: 8%">
					<select name="prpLpersonLossCurrency" class="input" style="width: 50px" onchange="getPrpLpersonLossExchRate(this);">
						<c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
							<option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>><c:out value="${tempMap.key}" /></option>
						</c:forEach>
					</select>
				</td>
				<td class="inputsubsub" align="center" style="width: 8%">
					<input type="text" name="prpLpersonLossExchRate"  value="1"  onchange="calRealpayForPerson(this);" readonly="readonly" class="input" style="width: 70px">
				</td>
				<td class="inputsubsub" align="center" style="width: 8%">
					<input type="text" name="prpLpersonLossSumRealPayNTD" class="readonly" readonly style="width: 75px" value="0">
				</td>
				<td class="input" style="width: 9%" title="请单击选择賠付對象讯息">
					<%-- 賠付對象序号 --%>
					<input type="text" name="prpLpersonLossPayObjectSerialNo" class="common" style="width: 98%;" value="" onclick="setPrpObjectinfoSerialNo(this);" readonly="readonly">
					<input type="hidden" name="prpLpersonLossClaimRate" value="100">
					<input type="hidden" name="personCount" value="${requestScope.personCount}">
					<input type="hidden" name="prpLpersonLossSumLoss" value="0" class="common">
					<input type="hidden" name="prpLpersonLossFlag">
					<input type="hidden" name="prpLpersonLossFamilyNo">
					<input type="hidden" name="prpLpersonLossLiabCode">
					<input type="hidden" name="prpLpersonLossLiabName">
					<input type="hidden" name="prpLpersonLossJobCode">
					<input type="hidden" name="prpLpersonLossJobName">
					<input type="hidden" name="prpLpersonLossItemAddress">
					<input type="hidden" name="prpLpersonLossUnit">
					<input type="hidden" name="prpLpersonLossAmount" value="0">
					<input type="hidden" name="prpLpersonLossCurrency1" value="${LOCAL_CURRENCY }">
					<input type="hidden" name="prpLpersonLossItemValue">
					<input type="hidden" name="prpLpersonLossCurrency2" value="${LOCAL_CURRENCY }">
					<input type="hidden" name="prpLpersonLossCurrency3" value="${LOCAL_CURRENCY }">
					<input type="hidden" name="prpLpersonLossSumRest" value="0">
					<input type="hidden" name="prpLpersonLossCurrency4" value="${LOCAL_CURRENCY }">
					<input type='hidden' name="prpLpersonLossExceptDeductiblePay" value="0">
					<input type='hidden' name="prpLpersonLossExceptDeductibleRate" value="0">
					<input type="hidden" name="prpLpersonLossUnitAmount" class="common" style="width: 65px" value="0"> 
					<input type="hidden" name="prpLpersonLossLossQuantity" class="common" style="width: 65px" value="0"> 
					<input type="hidden" name="prpLpersonLossHospitalDays" value="0" >
					<input type="hidden" name="prpLpersonLossInjuryGrade" value="">
					<input type="hidden" name="prpLpersonLossDeductibleRate" value="0">
				</td>
				<td class="input" style="width: 4%" align="ceter">
					<s:select name="prpLpersonLossReservedEstimate" list="#attr.reservedEstimateList" ></s:select>
				</td>
				<td class="inputsubsub" style="width: 3%">
					<div align="center">
						<input type=button name="buttonPersonFeeLossDelete" class="smallbutton" onclick="deleteRow(this,'PersonFeeLoss','serialNo','personLossSerialNo');" value="-" readonly style="cursor: hand">
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
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="PersonImg" onclick="showPage(this,spanPerson);">
			<s:text name="受害人訊息" />
			<br> 
			<span style="display: none">
				<table class="common" style="display: none" id="Person_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr name="personObject">
							<td class="input" style="width: 4%">
								<div align="center">
									<input type="text" class="readonly" readonly name="prpLpersonLossSerialNo" style="width: 25px">
									<input type="hidden" name="prpLpersonLossPersonNo">
									<input type="hidden" name="prpLpersonLossDangerNo" value="1" onClick="viewDangerUnitPersonLoss(this);">
								</div>
							</td>
							<td class="subformtitle" style="width: 92%">
								<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
									<tbody>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="db.prpLperson.personName" />:<%-- 人员姓名 --%>
											</td>
											<td class="input" style="width: 32%"">
												<input class="input" style="width: 160px" name="prpLpersonLossPersonName" maxlength=20 title="人員姓名">
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 18%">
												<s:text name="db.prpLperson.personSex" />：<%-- 性别 --%>
											</td>
											<td class="input" style="width: 32%">
												<select name="prpLpersonLossSex" class="common" style="width: 50px">
													<option value="1"><s:text name="certainLoss.male" /><%-- 男 --%></option>
													<option value="2"><s:text name="certainLoss.female" /><%-- 女 --%></option>
												</select>
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
										</tr>
										<tr>
											<td class='title' style="width: 18%">出生日期：</td>
											<td class="input" style="width: 32%">
												<rc:rcDate class="common" style="width: 110px" name="prpLpersonLossBirthday" onchange="updatePersonLossAge(this);" title="出生年份" wdatePicker="true" />
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 18%">
												<s:text name="db.prpLpersonloss.age" />：<%-- 年齡 --%>
											</td>
											<td class="input" style="width: 32%">
												<input class="input" name="prpLpersonLossAge" style="width: 50px" maxlength="3" title="年齡" onfocus="cacheData(this);" onchange="validateAge(this,1,120);">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 18%">身份證字號：</td>
											<td class="input" style="width: 32%">
												<input type="hidden" name="prpLpersonLossCertificateCode" value="" class="common">
												<input type="text" class="input" name="prpLpersonLossIdentifyNumber" style="width: 160px" title="身份證字號" onchange="resetSumRealPay(this);">
												<img src="${ctx}/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 18%">是否以健保身份就診：</td>
											<td class="input" style="width: 32%">
												<select name="prpLpersonLossMedicalCode" class="input">
													<option value="Y" selected="selected">是</option>
													<option value="N">否</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 18%">醫院名稱：</td>
											<td class="input" style="width: 32%">
												<input type="text" class="input" name="prpLpersonLossHospitalCode" onkeyup="getHospital(this,'codeCode','0,1')" onblur="isHospital(this,'codeCode');" style="width: 100px" title="醫院代碼">
												<input type="text" class="input" name="prpLpersonLossHospitalName" onkeyup="getHospital(this,'codeName','-1,0')" style="width: 180px" title="醫院名稱">
											</td>
											<td class="title" style="width: 18%">醫師姓名：</td>
											<td class="input" style="width: 32%">
												<input type="text" class="input" name="prpLpersonLossDoctor" style="width: 110px" title="醫師姓名">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 18%">受害人電話：</td>
											<td class="input" style="width: 32%">
												<input type="text" name="prpLpersonLossTelephoneNo" value="" style="width: 110px" class="input" title="受害人電話">
											</td>
											<td class="title" style="width: 18%">憲警單位：</td>
											<td class="input" style="width: 32%">
												<input type="text" name="prpLpersonLossPoliceUnits" value="" class="input">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 18%">傷亡情形：</td>
											<td class="input" style="width: 32%">
												<s:select name="prpLpersonLossCasualties" listKey="key" listValue="value" list="#request.casualtiesList" cssClass="input" cssStyle="width: 110px" onchange="resetSumRealPay(this);" />
											</td>
											<td class="title" style="width: 18%">肇事責任比率：</td>
											<td class="input" style="width: 32%">
												<input type="text" class="input" name="prpLpersonLossIndemnityDutyRate" style="width: 110px" title="肇事責任比率" value="0" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);">
												%
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">補充保費：</td>
											<td class="input" style="width: 18%">
												<input type="text" name="prpLpersonLossAddPremium" value="0" class="input" style="width: 110px">
											</td>
											<td class='title' style="width: 18%">賠付金額合計：</td>
											<td class="input" style="width: 15%">
												<input type="text" class='readonly' style='width: 110px' readonly name="prpLpersonLossSumRealPay1NTD" value="0">
												<input type='hidden' name="prpLpersonLossSumDefPay1" value="0">
											</td>
										</tr>
										<tr>
											<td colspan="7">
												<table name="prpLpersonFeeLoss" class="common" align="center" cellspacing="1" cellpadding="0">
													<thead>
														<tr>
															<td class="subformtitle" colspan="11">
																<s:text name="prompt.compensate.costInfo" /><%-- 费用信息 --%>
															</td>
														</tr>
														<tr>
															<td class="centertitle" style="width: 18%">
																<s:text name="db.prpDrate.kindName" /><%-- 险别名称 --%>
															</td>
															<td class="centertitle" style="width: 18%">
																<s:text name="claim.cost" /><%-- 费用类别 --%>
															</td>
															<td class="centertitle" style="width: 8%">
																<s:text name="compensate.approvedCompen" /><%-- 核定赔偿 --%>
															</td>
															<td class="centertitle" style="width: 8%">
																<s:text name="db.prpLpersonLoss.sumRest" /><%-- 自負額 --%>
															</td>
															<td class="centertitle" style="width: 8%">
																<s:text name="db.prpLreplevynew.sumpaid" /><%-- 赔付金额 --%>
															</td>
															<td class="centertitle" style="width: 8%">賠償幣別</td>
															<td class="centertitle" style="width: 8%">匯率</td>
															<td class="centertitle" style="width: 8%">賠償金額（NTD）</td>
															<td class="centertitle" style="width: 9%">
																<s:text name="db.prpLpersonLoss.payObjectSerialNo" /><%-- 赔付对象讯息 --%>
															</td>
															<td class="centertitle" style="width: 4%">保留預估</td>
															<td class="centertitle" style="width: 3%">&nbsp;</td>
														</tr>
													</thead>
													<tfoot>
														<tr>
															<td class="titlesubsub" colspan="10" style="width: 97%"></td>
															<td class="title" align="right" style="width: 3%">
																<div align="center">
																	<input type="button" value="+" class="smallbutton" onclick="insertRow('PersonFeeLoss',this,'serialNo','personLossSerialNo');" name="buttonPersonFeeLossInsert" readonly style="cursor: hand">
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
									<input type=button name="buttonPersonDelete" class="smallbutton" onclick="deleteRow(this,'Person','prpLpersonLossSerialNo');" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
			<span id="spanPerson" style="display: none"> <%-- 多行输入展现域 --%>
				<table id="Person" class="common" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="centertitle" style="width: 4%">
								<s:text name="db.prpLcheckExt.serialNo" /><%-- 序号 --%>
							</td>
							<td class="centertitle" style="width: 96%" colspan=2>
								<s:text name="db.prpLregistText.context" /><%-- 内容 --%>
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=2 style="width: 96%">(按"+"號鍵增加人員傷亡賠付訊息，按"-"號鍵刪除訊息)</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('Person',this,'prpLpersonLossSerialNo')" class="smallbutton" name="buttonPersonInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:set var="personNo" value="0" scope="page" />
						<c:set var="personSerialNo" value="1" scope="page" />
						<c:forEach var="tempPerson" items="${requestScope.prpLpersonLoss.prpLpersonLossList}">
							<c:if test="${tempPerson.personNo - pageScope.personNo != 0}">
								<tr name="personObject">
									<td class="input" style="width: 4%">
										<div align="center">
											<input type="text" class="readonly" readonly name="prpLpersonLossSerialNo" style="width: 25px" value="${pageScope.personSerialNo}">
											<input type="hidden" name="prpLpersonLossPersonNo" value="${tempPerson.personNo}">
											<input type="hidden" name="prpLpersonLossDangerNo" value="${tempPerson.dangerNo}" onClick="viewDangerUnitPersonLoss(this);">
										</div>
									</td>
									<td class="subformtitle" style="width: 92%">
										<table cellpadding="0" cellspacing="1" class="common" style="width: 100%">
											<tbody>
												<tr>
													<td class="title" style="width: 15%">
														<s:text name="db.prpLperson.personName" />:<%-- 人员姓名 --%>
													</td>
													<td class="input" style="width: 32%"">
														<input type="text" class="input" style="width: 160px" name="prpLpersonLossPersonName" maxlength=20 title="人員姓名" value="${tempPerson.personName}">
														<img src="${ctx}/images/bgMarkMustInput.jpg">
													</td>
													<td class="title" style="width: 18%">
														<s:text name="db.prpLperson.personSex" />：<%-- 性别 --%>
													</td>
													<td class="input" style="width: 32%">
														<select name="prpLpersonLossSex" class="input" style="width: 50px">
															<option value="1" <c:if test="${fn:trim(tempPerson.sex)=='1'}"><c:out value="selected"/></c:if>><s:text name="certainLoss.male" />
																<%-- 男 --%></option>
															<option value="2" <c:if test="${fn:trim(tempPerson.sex)=='2'}"><c:out value="selected"/></c:if>><s:text name="certainLoss.female" /></option>
														</select>
														<img src="${ctx }/images/bgMarkMustInput.jpg">
													</td>
												</tr>
												<tr>
													<td class='title' style="width: 18%">出生日期：</td>
													<td class="input" style="width: 32%">
														<rc:rcDate class="common" style="width: 110px" name="prpLpersonLossBirthday" onchange="updatePersonLossAge(this);" title="出生年份" wdatePicker="true" value="${tempPerson.birthday}"/>
														<img src="${ctx}/images/bgMarkMustInput.jpg">
													</td>
													<td class="title" style="width: 18%">
														<s:text name="db.prpLpersonloss.age" />：<%-- 年齡 --%>
													</td>
													<td class="input" style="width: 32%">
														<input type="text" class="input" name="prpLpersonLossAge" style="width: 50px" maxlength="3" title="年齡" onfocus="cacheData(this);" onchange="validateAge(this,1,120);" value="${tempPerson.age}">
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 18%">身份證字號：</td>
													<td class="input" style="width: 32%">
														<input type="hidden" name="prpLpersonLossCertificateCode" value="" class="common">
														<input type="text" class="input" name="prpLpersonLossIdentifyNumber" style="width: 160px" title="身份證字號" onchange="resetSumRealPay(this);" value="${tempPerson.identifyNumber}">
														<img src="${ctx}/images/bgMarkMustInput.jpg">
													</td>
													<td class="title" style="width: 18%">是否以健保身份就診：</td>
													<td class="input" style="width: 32%">
														<select name="prpLpersonLossMedicalCode" class="input">
															<option value="Y" <c:if test="${tempPerson.medicalCode=='Y'}"><c:out value="selected"/></c:if>>是</option>
															<option value="N" <c:if test="${tempPerson.medicalCode=='N'}"><c:out value="selected"/></c:if>>否</option>
														</select>
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 18%">醫院名稱：</td>
													<td class="input" style="width: 32%">
														<input type="text" class="input" name="prpLpersonLossHospitalCode" onkeyup="getHospital(this,'codeCode','0,1')" onblur="isHospital(this,'codeCode');" style="width: 100px" title="醫院代碼" value="${tempPerson.hospitalCode}">
														<input type="text" class="input" name="prpLpersonLossHospitalName" onkeyup="getHospital(this,'codeName','-1,0')" style="width: 180px" title="醫院名稱" value="${tempPerson.hospitalName}">
													</td>
													<td class="title" style="width: 18%">醫師姓名：</td>
													<td class="input" style="width: 32%">
														<input type="text" class="input" name="prpLpersonLossDoctor" style="width: 110px" title="醫師姓名" value="${tempPerson.doctor}" >
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 18%">受害人電話：</td>
													<td class="input" style="width: 32%">
														<input type="text" name="prpLpersonLossTelephoneNo" value="${tempPerson.telephoneNo}" style="width: 110px" class="input" title="受害人電話">
													</td>
													<td class="title" style="width: 18%">憲警單位：</td>
													<td class="input" style="width: 32%">
														<input type="text" name="prpLpersonLossPoliceUnits" class="input" value="${tempPerson.policeUnits}">
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 18%">傷亡情形：</td>
													<td class="input" style="width: 32%">
														<c:set var="tempSelectedValue" value="${tempPerson.casualties}" />
														<s:select name="prpLpersonLossCasualties" listKey="key" listValue="value" list="#request.casualtiesList" value="#attr.tempSelectedValue" cssClass="input" cssStyle="width: 110px" onchange="resetSumRealPay(this);" />
													</td>
													<td class="title" style="width: 18%">肇事責任比率：</td>
													<td class="input" style="width: 32%">
														<input type="text" class="input" name="prpLpersonLossIndemnityDutyRate" style="width: 110px" title="肇事責任比率" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);" value="<fmt:formatNumber value='${tempPerson.indemnityDutyRate}'  maxFractionDigits='2'/>" >%
													</td>
												</tr>
												<tr>
													<td class="title" style="width: 18%">補充保費：</td>
													<td class="input" style="width: 32%">
														<input type="text" name="prpLpersonLossAddPremium" value="<fmt:formatNumber value='${tempPerson.addPremium }' pattern="#"/>" class="input" style="width: 110px">
													</td>
													<td class='title' style="width: 18%">賠付金額合計：</td>
													<td class="input" style="width: 32%">
														<input type="text" class='readonly' style='width: 110px' readonly name="prpLpersonLossSumRealPay1NTD" value="<fmt:formatNumber value='${tempPerson.sumRealPay1}' pattern='#'/>">
														<input type='hidden' name="prpLpersonLossSumDefPay1" value="0">
													</td>
												</tr>
												<tr>
													<td colspan="7">
														<%-- 多行输入展现域 --%>
														<table name="prpLpersonFeeLoss" class="common" align="center" cellspacing="1" cellpadding="0" style="width: 100%">
															<thead>
																<tr>
																	<td class="subformtitle" colspan="11">
																		<s:text name="prompt.compensate.costInfo" />
																	</td>
																</tr>
																<tr>
																	<td class="centertitle" style="width: 18%">
																		<s:text name="db.prpDrate.kindName" /><%-- 险别名称 --%>
																	</td>
																	<td class="centertitle" style="width: 18%">
																		<s:text name="claim.cost" /><%-- 费用类别 --%>
																	</td>
																	<td class="centertitle" style="width: 8%">
																		<s:text name="compensate.approvedCompen" /><%-- 核定赔偿 --%>
																	</td>
																	<td class="centertitle" style="width: 8%">
																		<s:text name="db.prpLpersonLoss.sumRest" /><%-- 自負額 --%>
																	</td>
																	<td class="centertitle" style="width: 8%">賠償金額</td>
																	<td class="centertitle" style="width: 8%">賠償幣別</td>
																	<td class="centertitle" style="width: 8%">匯率</td>
																	<td class="centertitle" style="width: 8%">賠償金額（NTD）</td>
																	<td class="centertitle" style="width: 9%">
																		<s:text name="db.prpLpersonLoss.payObjectSerialNo" /><%-- 赔付对象讯息 --%>
																	</td>
																	<td class="centertitle" style="width: 4%">保留預估</td>
																	<td class="centertitle" style="width: 3%">&nbsp;</td>
																</tr>
															</thead>
															<tfoot>
																<tr>
																	<td class="titlesubsub" colspan="10" style="width: 97%"></td>
																	<td class="title" align="right" style="width: 3%">
																		<div align="center">
																			<input type="button" value="+" class="smallbutton" onclick="insertRow('PersonFeeLoss',this,'serialNo','personLossSerialNo');" name="buttonPersonFeeLossInsert" readonly style="cursor: hand">
																		</div>
																	</td>
																</tr>
															</tfoot>
															<tbody>
																<c:set var="tempSerial" value="0" scope="page" />
																<c:forEach items="${requestScope.prpLpersonLoss.prpLpersonLossList}" var="tempPrpLpersonLosss">
																	<c:if test="${tempPrpLpersonLosss.personNo == tempPerson.personNo}">
																		<c:set var="tempSerial" value="${tempSerial + 1}" />
																		<tr name="prpLpersonLossObject">
																			<td class="input" style="width: 18%">
																				<div align="center">
																					<input type="hidden" name="serialNo" value="${tempSerial}">
																					<input type="hidden" name="personLossSerialNo" value="${tempPerson.personNo}"><%-- 归属父类的序号 --%>
																					<input type="text" name="prpLpersonLossKindCode" class="codecode" style="width: 20%;" readonly="readonly" onblur="clearPrpLpersonLoss(this);" value="${tempPrpLpersonLosss.kindCode}" 
																						ondblclick="code_CodeSelect(this,'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" >
																					<input type="text" name="prpLpersonLossKindName" class="codename" style="width: 70%" readonly="readonly" onblur="clearPrpLpersonLoss(this);" value="${tempPrpLpersonLosss.kindName}"
																						ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" >
																					<input type="hidden" name="prpLpersonLossItemKindNo" value="${tempPrpLpersonLosss.itemKindNo}">
																				</div>
																			</td>
																			<td class="inputsubsub" align="center" style="width: 18%">
																				<!-- 費用類別-->
																				<input type="text" name="prpLpersonLossLiabDetailCode" class="codecode" readonly="readonly" style="width: 20%;" title="人傷費用類別代碼" value="<c:out value='${tempPrpLpersonLosss.liabDetailCode}' />"
																					ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);" >
																				<input type="text" name="prpLpersonLossLiabDetailName" class="codename" readonly="readonly" style="width: 70%" title="人傷費用類別名稱" value="<c:out value='${tempPrpLpersonLosss.liabDetailName}' />" 
																					ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);" >
																				<input name="medicDeathFlag" type="hidden" title="人傷費用類別類型" value="<c:out value='${tempPrpLpersonLosss.feeCategory}' />" >
																			</td>
																			<td class="inputsubsub" align="center" style="width: 8%">
																				<!-- 核定賠償-->
																				<input type="text" name="prpLpersonLossSumDefPay" class="input" style="width: 75px" title="核定賠償" onchange="calRealpayForPerson(this);" value="<fmt:formatNumber value='${tempPrpLpersonLosss.sumDefPay}' pattern='#0.##'/>">
																			</td>
																			<td class="inputsubsub" align="center" style="width: 8%">
																				<!-- 自負額 -->
																				<input type="text" name="prpLpersonLossDeductible" class="input" style="width: 70px;" title="自負額" onchange="calRealpayForPerson(this);" value="<fmt:formatNumber value='${tempPrpLpersonLosss.deductible}' pattern='#0.##'/>" >
																			</td>
																			<td class="inputsubsub" align="center" style="width: 8%">
																				<!-- 賠付金額 -->
																				<input type="text" name="prpLpersonLossSumRealPay" class="readonly" readonly style="width: 75px" value="<fmt:formatNumber value='${tempPrpLpersonLosss.sumRealPay}' pattern='#0.##'/>" >
																			</td>
																			<td class="inputsubsub" align="center" style="width: 8%">
																				<select name="prpLpersonLossCurrency" class="input" style="width: 50px" onchange="getPrpLpersonLossExchRate(this);">
																					<c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
																						<option value="${tempMap.key}" <c:if test="${tempMap.key==tempPrpLpersonLosss.currency}">selected="selected"</c:if>><c:out value="${tempMap.key}" /></option>
																					</c:forEach>
																				</select>
																			</td>
																			<td class="inputsubsub" align="center" style="width: 8%">
																				<input type="text" name="prpLpersonLossExchRate" onchange="calRealpayForPerson(this);" value="${tempPrpLpersonLosss.exchRate }" readonly="readonly" class="input" style="width: 70px" >
																			</td>
																			<td class="inputsubsub" align="center" style="width: 8%">
																				<input type="text" name="prpLpersonLossSumRealPayNTD" class="readonly" readonly style="width: 75px" value="0">
																			</td>
																			<td class="input" style="width: 9%" title="请单击选择賠付對象讯息">
																				<%-- 賠付對象序号 --%>
																				<input type="text" name="prpLpersonLossPayObjectSerialNo" class="common" style="width: 98%;" onclick="setPrpObjectinfoSerialNo(this);" readonly="readonly" value="${tempPrpLpersonLosss.payObjectSerialNo}">
																				<input type="hidden" name="prpLpersonLossClaimRate" value="100">
																				<input type="hidden" name="personCount" value="${requestScope.personCount}">
																				<input type="hidden" name="prpLpersonLossSumLoss" class="common" value="<c:out value='${tempPrpLpersonLosss.sumLoss}' />">
																				<input type="hidden" name="prpLpersonLossFlag" value="<c:out value='${tempPrpLpersonLosss.flag}' />">
																				<input type="hidden" name="prpLpersonLossFamilyNo" value="<c:out value='${tempPrpLpersonLosss.familyNo}' />">
																				<input type="hidden" name="prpLpersonLossLiabCode" value="<c:out value='${tempPrpLpersonLosss.liabCode}' />">
																				<input type="hidden" name="prpLpersonLossLiabName" value="<c:out value='${tempPrpLpersonLosss.liabName}' />">
																				<input type="hidden" name="prpLpersonLossJobCode" dvalue="<c:out value='${tempPrpLpersonLosss.jobCode}' />">
																				<input type="hidden" name="prpLpersonLossJobName" value="<c:out value='${tempPrpLpersonLosss.jobName}' />">
																				<input type="hidden" name="prpLpersonLossItemAddress" value="<c:out value='${tempPrpLpersonLosss.itemAddress}' />">
																				<input type="hidden" name="prpLpersonLossUnit" value="<c:out value='${tempPrpLpersonLosss.unit}' />">
																				<input type="hidden" name="prpLpersonLossAmount" value="<c:out value='${tempPrpLpersonLosss.amount}' />">
																				<input type="hidden" name="prpLpersonLossCurrency1" value="<c:out value='${tempPrpLpersonLosss.currency1}' />">
																				<input type="hidden" name="prpLpersonLossItemValue" value="<c:out value='${tempPrpLpersonLosss.itemValue}' />">
																				<input type="hidden" name="prpLpersonLossCurrency2" value="<c:out value='${tempPrpLpersonLosss.currency2}' />">
																				<input type="hidden" name="prpLpersonLossCurrency3" value="<c:out value='${tempPrpLpersonLosss.currency3}' />">
																				<input type="hidden" name="prpLpersonLossSumRest" value="${tempPrpLpersonLosss.sumRest}">
																				<input type="hidden" name="prpLpersonLossCurrency4" value="<c:out value='${tempPrpLpersonLosss.currency4}' />">
																				<input type='hidden' name="prpLpersonLossExceptDeductiblePay" value="<c:out value='${tempPrpLpersonLosss.exceptDeductiblePay}' />">
																				<input type='hidden' name="prpLpersonLossExceptDeductibleRate" value="<c:out value='${tempPrpLpersonLosss.exceptDeductibleRate}' />">
																				<input type="hidden" name="prpLpersonLossUnitAmount" class="common" style="width: 65px" value="<c:out value='${tempPrpLpersonLosss.unitAmount}' />"> 
																				<input type="hidden" name="prpLpersonLossLossQuantity" class="common" style="width: 65px" value="<c:out value='${tempPrpLpersonLosss.lossQuantity}' />"> 
																				<input type="hidden" name="prpLpersonLossHospitalDays" value="<c:out value='${tempPrpLpersonLosss.hospitalDays}' />">
																				<input type="hidden" name="prpLpersonLossInjuryGrade" value="<c:out value='${tempPrpLpersonLosss.injuryGrade}'/>">
																				<input type="hidden" name="prpLpersonLossDeductibleRate" value="${tempPrpLpersonLosss.deductiblerate}">
																			</td>
																			<td class="input" style="width: 4%" align="ceter">
																				<s:select name="prpLpersonLossReservedEstimate" list="#attr.reservedEstimateList" value="#attr.tempPrpLpersonLosss.reservedEstimate"></s:select>
																			</td>
																			<td class="inputsubsub" style="width: 3%">
																				<div align="center">
																					<input type=button name="buttonPersonFeeLossDelete" class="smallbutton" onclick="deleteRow(this,'PersonFeeLoss','serialNo','personLossSerialNo');" value="-" readonly style="cursor: hand">
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
											<input type=button name="buttonPersonDelete" class="smallbutton" onclick="deleteRow(this,'Person','prpLpersonLossSerialNo');" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
								<c:set var="personNo" value="${tempPerson.personNo}" />
								<c:set var="personSerialNo" value="${pageScope.personSerialNo + 1}" />
							</c:if>
						</c:forEach>
					</tbody>
				</table>
		</span></td>
	</tr>
</table>
<%--** 醫院名稱下拉显示的隐藏域 *--%>
<div id="hospitalList" style="background-color: FFFFFF; display: none; cursor: hand; position: absolute; width: 400px;" align="left"></div>
<div id="prpLPayObjectinfo" style='width: 300; display: none; position: absolute; background-color: FFFFFF;' class="common" align="left">
	<ul id="uLprpLPayObjectinfo" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<s:if test="#attr.prpLpayObjectInfo.prpLpayObjectInfoList==null||#attr.prpLpayObjectInfo.prpLpayObjectInfoList.size()==0">
			<li><s:text name="title.compensateEdit.notPaymentObject" />。</li><%-- 沒有賠款給付對象訊息，請錄入賠款給付對象--%>
		</s:if>
		<s:else>
			<c:forEach var="prpLpayObjectInfoTemp" items="${prpLpayObjectInfo.prpLpayObjectInfoList}">
				<li><input type="checkbox" onclick="setPayObjectPayAmount();" name="payObjectSerialNo" value="${prpLpayObjectInfoTemp.id.serialNo}" /><s:text name="compensate.paymentObject" />${prpLpayObjectInfoTemp.id.serialNo} <s:text name="db.prpLcfee.sumPaid" />: <input
					type="text" name="payObjectPayAmount" onblur="setPayObjectPayAmount();" value="" class="common" style="width: 100px" /></li><%--赔付对象--%><%--赔付金额--%>
			</c:forEach>
		</s:else>
	</ul>
	<ul align="center" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
		<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" name="closePrpObjectinfoSerialNo" onclick="hideSubPage(this,'prpLPayObjectinfo')"
			value="<s:text name='button.close.value' />" /></li>
	</ul>
</div>