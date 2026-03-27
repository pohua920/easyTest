<%--
****************************************************************************
* DESC       :添加主信息子块界面页面Head[ 实赔 ]
* AUTHOR     :中科软
* MODIFYLIST :Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<c:if test="${not empty amountMap}">
	<c:forEach items="${amountMap}" var="amountMapTemp">
		<input type="hidden" name="kindCode" value="${amountMapTemp.key}">
		<input type="hidden" name="kindAmount" value="${amountMapTemp.value}">
	</c:forEach>
</c:if>
<script language="javascript">
	function changePrpLcompensateFinallyFlag() {
		var t = document.getElementById("prpLcompensateFinallyFlag");
		var v = t.options[t.selectedIndex].value;
		if ("0" == v) {
			fm.replevyFlag.disabled = false;
			fm.referLawFlag.disabled = false;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='common.certify.followupclaimDesc'/>";<%--后续理算说明--%>
		} else if ("1" == v) {
			fm.replevyFlag.disabled = false;
			fm.referLawFlag.disabled = true;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/> ";<%--理算报告--%>
		} else {
			fm.replevyFlag.disabled = true;
			fm.referLawFlag.disabled = true;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/>";<%--理算报告--%>
		}
	}
	function changePrpLcompensateFinallyFlag1() {
		fm.replevyFlag.disabled = false;
		fm.referLawFlag.disabled = true;
		Lltext.style.display = "";
		fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/>";<%--理算报告--%>
	}
	function changeFinallyFlagAndLltextContent() {
		var isPayForOtherList = document.getElementsByName("isPayForOther");
		//reason:理算报告用来保存"後续理算内容"
		var strtemp = "";
		var t = document.getElementById("prpLcompensateFinallyFlag");
		var v = t.options[t.selectedIndex].value;
		if ("0" == v) {
			alert("<s:text name='prompt.certify.message1'/>\n");<%--案件核赔通过后，不会自动结案，请手工结案！--%>
			fm.replevyFlag.disabled = false;
			fm.referLawFlag.disabled = false;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='common.certify.followupclaimDesc'/>";<%--后续理算说明--%>
			strtemp = fm.backLltextContent.value;
			fm.backLltextContent.value = fm.prpLltextContextInnerHTML.value;//备份现在的数据
			fm.prpLltextContextInnerHTML.value = strtemp;
		} else if ("1" == v) {
			if (isPayForOtherList.length > 0
					&& isPayForOtherList[0].checked == true) {
				alert("<s:text name='prompt.certify.message2'/>");<%--选择代付赔款，不准许选择为结案！--%>
				fm.prpLcompensateFinallyFlag.options[0].selected = true;
				fm.replevyFlag.disabled = false;
				fm.referLawFlag.disabled = false;
				Lltext.style.display = "";
				fm.tdLltextTitle.value = "<s:text name='common.certify.followupclaimDesc'/>";<%--后续理算说明--%>
				strtemp = fm.backLltextContent.value;
				fm.backLltextContent.value = fm.prpLltextContextInnerHTML.value;//备份现在的数据
				fm.prpLltextContextInnerHTML.value = strtemp;
				return;
			}
			alert("<s:text name='prompt.certify.message3'/>\n");<%--案件核赔通过后，将自动结案！--%>
			fm.replevyFlag.disabled = false;
			fm.referLawFlag.disabled = true;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/>";<%--理算报告--%>
			strtemp = fm.backLltextContent.value;
			fm.backLltextContent.value = fm.prpLltextContextInnerHTML.value;//备份现在的数据
			fm.prpLltextContextInnerHTML.value = strtemp;
		} else {
			alert("<s:text name='prompt.certify.message3'/>\n");<%--案件核赔通过后，将自动结案！--%>
			fm.replevyFlag.disabled = true;
			fm.referLawFlag.disabled = true;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/>";<%--理算报告--%>
			strtemp = fm.backLltextContent.value;
			fm.backLltextContent.value = fm.prpLltextContextInnerHTML.value;//备份现在的数据
			fm.prpLltextContextInnerHTML.value = strtemp;
		}
	}
	function changeFinallyFlag(type) {
		var prpLcompensateFinallyFlag = document
				.getElementById("prpLcompensateFinallyFlag");
		var prpLcompensateSumCoinForOther = document
				.getElementsByName("prpLcompensateSumCoinForOther");
		var prpLcompensateSumCoinForOtherFee = document
				.getElementsByName("prpLcompensateSumCoinForOtherFee");
		var prpLcompensateSumCoinForOtherBak = document
				.getElementsByName("prpLcompensateSumCoinForOtherBak");
		var prpLcompensateSumCoinForOtherFeeBak = document
				.getElementsByName("prpLcompensateSumCoinForOtherFeeBak");
		if (type == 1) {
			if (prpLcompensateFinallyFlag.length > 1) {
				fm.prpLcompensateFinallyFlag.options[0].selected = true;
			}
			if (prpLcompensateSumCoinForOther.length > 0
					&& prpLcompensateSumCoinForOtherBak.length > 0) {
				prpLcompensateSumCoinForOther[0].value = prpLcompensateSumCoinForOtherBak[0].value;
			}
			if (prpLcompensateSumCoinForOtherFee.length > 0
					&& prpLcompensateSumCoinForOtherFeeBak.length > 0) {
				prpLcompensateSumCoinForOtherFee[0].value = prpLcompensateSumCoinForOtherFeeBak[0].value;
			}
		} else if (type == 0) {
			if (prpLcompensateSumCoinForOther.length > 0
					&& prpLcompensateSumCoinForOtherBak.length > 0) {
				prpLcompensateSumCoinForOther[0].value = '0.0';
			}
			if (prpLcompensateSumCoinForOtherFee.length > 0
					&& prpLcompensateSumCoinForOtherFeeBak.length > 0) {
				prpLcompensateSumCoinForOtherFee[0].value = '0.0';
			}
		}
	}
</script>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<input type="hidden" name="prpLcompensateCaseNo" value="${prpLcompensate.caseNo }">
						<c:if test="${not empty coinsFlag}">
							<input type="hidden" name="coinsFlag" value="${coinsFlag}">
						</c:if>
						<c:if test="${empty coinsFlag}">
							<input type="hidden" name="coinsFlag" value="0">
						</c:if>
						<input type="hidden" name="prpLcompensateCaseNo"
							value="${prpLcompensate.caseNo}">
						<input type="hidden" name="prpLcompensateClassCode"
							value="${prpLcompensate.classCode}">
						<input type="hidden" name="prpLcompensateRiskCode"
							value="${prpLcompensate.riskCode}">
						<input type="hidden" name="prpLcompensateDeductCond"
							value="${prpLcompensate.deductCond}">
						<input type="hidden" name="prpLcompensatePreserveDate"
							value="${prpLcompensate.preserveDate}">
						<input type="hidden" name="prpLcompensateCheckAgentCode"
							value="${prpLcompensate.checkAgentCode}">
						<input type="hidden" name="prpLcompensateCheckAgentName"
							value="${prpLcompensate.checkAgentName}">
						<input type="hidden" name="prpLcompensateSurveyorName"
							value="${prpLcompensate.surveyorName}">
						<input type="hidden" name="prpLcompensateDutyDescription"
							value="${prpLcompensate.dutyDescription}">
						<input type="hidden" name="prpLcompensateCurrency"
							value="${prpLcompensate.currency}">
						<input type="hidden" name="prpLcompensateSumLoss"
							value="${prpLcompensate.sumLoss}">
						<input type="hidden" name="prpLcompensateReceiverName"
							value="${prpLcompensate.receiverName}">
						<input type="hidden" name="prpLcompensateBank"
							value="${prpLcompensate.bank}">
						<input type="hidden" name="prpLcompensateAccount"
							value="${prpLcompensate.account}">
						<input type="hidden" name="prpLcompensateMakeCom"
							value="${prpLcompensate.makeCom}">
						<input type="hidden" name="prpLcompensateComCode"
							value="${prpLcompensate.comCode}">
						<input type="hidden" name="prpLcompensateHandlerCode"
							value="${prpLcompensate.handlerCode}">
						<input type="hidden" name="prpLcompensateHandler1Code"
							value="${prpLcompensate.handler1Code}">
						<input type="hidden" name="prpLcompensateApproverCode"
							value="${prpLcompensate.approverCode}">
						<input type="hidden" name="prpLcompensateUnderWriteCode"
							value="${prpLcompensate.underWriteCode}">
						<input type="hidden" name="prpLcompensateUnderWriteName"
							value="${prpLcompensate.underWriteName}">
						<input type="hidden" name="prpLcompensateOperatorCode"
							value="${prpLcompensate.operatorCode}">
						<input type="hidden" name="prpLcompensateInputDate"
							value="<fmt:formatDate value='${requestScope.prpLcompensate.inputDate}' pattern='yyyy-MM-dd HH:mm:ss'/>">
						<input type="hidden" name="prpLcompensateUnderWriteEndDate"
							value="${prpLcompensate.underWriteEndDate}">
						<input type="hidden" name="prpLcompensateUnderWriteFlag"
							value="${prpLcompensate.underWriteFlag}">
						<input type="hidden" name="prpLcompensateFlag"
							value="${prpLcompensate.flag}">
						<input type="hidden" name="riskcode"
							value="${prpLcompensate.riskCode}">
						<input type="hidden" name="policyno"
							value="${prpLcompensate.policyNo}">
						<input type="hidden" name="registno"
							value="${prpLcompensate.registNo}">
						<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
						<input type="hidden" name="swfLogLogNo"
							value="${param.swfLogLogNo}">
						<input type="hidden" name="status" value="${param.status}">
						<input type="hidden" name="clauseType" value="">
						<input type="hidden" name="GenerateCompensateFlag" value="0">
						<input type="hidden" name='payFee' value="${payFlag}">
						<input type="hidden" name='BaseCurrency1' value="${prpDexch.baseCurrency}">
						<input type="hidden" name='ExchRate1' value="${prpDexch.exchRate}">
						<input type="hidden" name='delinquentfeeCase' value="${delinquentfeeCase}">
						<input type="hidden" name="coreURL" value="${coreURL}">
						<input type="hidden" name="prpLcompensateCaseType" value="">
						<input type="hidden" name="prpLcompensateStartDate" value="${prpLcompensate.startDate }<s:text name='endcase.dayStart'/> ${prpLcompensate.endDate}<s:text name='endcase.dayEnd'/>"><%--日 0 时 至--%><%--日 24 时止--%>
						<input type="hidden" name="prpLcompensateClauseType" value="${prpLcompensate.clauseType}">
						<input type="hidden" name="prpLcompensateClauseName"
							value="${prpLcompensate.clauseName}">
						<input type="hidden" name="prpLcompensateLicenseNo"
							value="${prpLcompensate.licenseNo}">
						<input type="hidden" name="prpLcompensateCarKind"
							value="${prpLcompensate.carKind}">
						<input type="hidden" name="prpLcompensateLicenseColor"
							value="${prpLcompensate.licenseColor}">
						<input type="hidden" name="prpLcompensateBrandName"
							value="${prpLcompensate.brandName}">
						<input type="hidden" name="prpLcompensateEngineNo"
							value="${prpLcompensate.engineNo}">
						<input type="hidden" name="prpLcompensateFrameNo"
							value="${prpLcompensate.frameNo}">
						<input type="hidden" name="prpLcompensateDamageStartDate"
							value="${prpLcompensate.damageStartDate} <s:text name='regist.prpLregist.date'/> ${prpLcompensate.damageStartHour} <s:text name='regist.prpLregist.hour'/> ${prpLcompensate.damageStartMinute} <s:text name='regist.prpLregist.minute'/>"><%--日--%><%--时--%><%--分--%>
						<input type="hidden" name="prpLcompensateDamageAddress"
							value="${prpLcompensate.damageAddress}">
						<input type="hidden" name="prpLcompensateSumAmount"
							value="${prpLcompensate.sumAmount}">
						<input type="hidden" name="prpLcompensateSumClaim"
							value="${prpLcompensate.sumClaim}">
						<input type="hidden" name="damageStartDate"
							value="${prpLcompensate.damageStartDate}">
						<input type="hidden" name="damageStartHour" value="${prpLclaim.damageStartHour}">
						<input type="hidden" name="sumPaidAll"
							value="${prpLcompensate.sumPaidAll}">
						<!--增加 股东业务信息-->
						<c:if test="${not empty shareHolderFlag}">
							<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
						</c:if>
						<c:if test="${empty shareHolderFlag}">
							<input type="hidden" name="shareHolderFlag" value="0">
						</c:if>
						<s:text name="query.xianzhongName" />
						<%-- 险种名称 --%>
					</td>
					<td class="right">${riskCName}</td>
					<td class="left"></td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right">
						<input type=hidden name="LFlag" title="<s:text name='db.prpLcompensate.lflag'/>" maxlength="22"
							class="readonly" readonly="true" value="${prpLcompensate.lflag}"><%--理赔类型--%>
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLlawsuit.policyNo" />
					</td>
					<%-- 保单号 --%>
					<td class="right">
						<input type=text name="prpLcompensatePolicyNo" class="readonly"
							readonly="true" value="${prpLcompensate.policyNo }">
					</td>
					<td class="left">
						<input type="image" name="btRelate"
							src="/claim/images/butRelate.gif" align="middle"
							onclick="relate(fm.prpLcompensatePolicyNo.value);return false;">
					</td>
					<td class="right"></td>
					<td class="left">
						<s:text name="db.prpLclaim.insuredName" />
					</td>
					<%-- 被保险人 --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateInsuredName" readonly="true"
							value="${prpLcompensate.insuredName }">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="compensate.computeBookNum" />
					</td>
					<%--计算书号  --%>
					<td class="right">
						<input type=text name="prpLcompensateCompensateNo" title="<s:text name='compensate.computeBookNum' />"
							maxlength="22" class="readonly" readonly="true"
							value="${prpLcompensate.compensateNo}"><%-- 计算书号 --%>
					</td>
					<td class="left" colspan="2">
						<input type=button class="bigbutton" name="flowShow"
							value="<s:text name='button.flowChart.value'/>" title="<s:text name='button.flowChart.value'/>"
							onclick="showWorkFlowerByClaimNo('${prpLcompensate.claimNo}')">
						<%--赔案流程图  --%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="check.claimNum" />
					</td>
					<%-- 赔案号 --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateClaimNo"
							readonly="true"
							value="${prpLcompensate.claimNo}">
						<input type="hidden" name="damageDate"
							value="${prpLcompensate.damageStartDate}">
					</td>
					<td class="left" colspan="2">
						<input type=button class="bigbutton" name="policyBackWard"
							value="<s:text name='button.dangerPolicyInfo.value'/>"
							onclick="backWardPolicy(fm.coreURL.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,fm.damageDate.value,fm.prpLcompensateComCode.value);">
						<%-- 出险时保单信息 --%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="compensate.paymentObject" />
					</td>
					<%--赔付对象  --%>
					<td class="right">
						<input class="common" type=text
							name="prpLcompensateCounterclaimername" title="<s:text name="compensate.paymentObject"/>"
							value="${prpLcompensate.counterClaimerName}"><%-- 赔付对象 --%>
					</td>
					<td class="left">
						<s:text name="db.prpLCMain.claimTimes" />
					</td>
					<%-- 赔付次数 --%>
					<td class="right">
						<input type="text" name="prpLcompensateTimes" class="readonly"
							readonly
							value="${prpLcompensate.times}">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
					</td>
					<%-- 已出险次数 --%>
					<td class="right">
						<%@include file="/pages/common/regist/ExistRegist.jsp"%>
					</td>
				</tr>
				<s:if test="#coinsFlag == '1'">
					<tr>
						<td class="left">
							<s:text name="commonAcci.compensate.whetherPaidReparat" />
						</td>
						<%-- 是否代付赔款 --%>
						<td class="right">
							<input type="radio" name="isPayForOther" onclick="changeFinallyFlag(1);"
								<c:if test="${prpLcompensate.isPayForOther == '1'}">"checked"</c:if> value="1">
							<s:text name="certainLoss.thirdCarLoss.yes" /><%-- 是 --%>
							<input type="radio" name="isPayForOther" onclick="changeFinallyFlag(0);"
								<c:if test="${prpLcompensate.isPayForOther == '0'}">"checked"</c:if> value="0">
							<s:text name="certainLoss.thirdCarLoss.no" /><%--否  --%>
						</td>
					</tr>
				</s:if>
				<tr>
					<s:if test="#recaseFlag == '0' || #editType == 'SHOW'">
						<td class="title" style="width: 15%">
							<s:text name="compensate.closedType" />
						</td>
						<%--  结案类型--%>
						<td class="input" colspan="3">
							<select id="prpLcompensateFinallyFlag" name="prpLcompensateFinallyFlag" style="width: 110px;"
								onchange="return changeFinallyFlagAndLltextContent();"> 
								<option value="0" <c:if test="${prpLcompensate.finallyFlag == '0'}">"selected"</c:if> >
									<s:text name="compensate.partiallyOpen" />
								</option>
								<%-- 部分未结 --%>
								<option value="1"
									<c:if test="${prpLcompensate.finallyFlag == '1'}">"selected"</c:if> >
									<s:text name="claim.case" />
								</option>
								<%--结案  --%>
								<option value="2"
									<c:if test="${prpLcompensate.finallyFlag == '2'}">"selected"</c:if> >
									<s:text name="claim.rejectClaim" />
								</option>
								<%-- 拒赔 --%>
							</select>
						</td>
					</s:if>
					<s:else>
						<td class="title" style="width: 15%">
							<s:text name="compensate.claimsType" />
						</td>
						<%-- 重开赔案结案类型 --%>
						<td class="input" colspan="3">
							<input type="hidden" name="prpLcompensateFinallyFlag" value="1">
							<s:text name="claim.case" />
							<%-- 结案 --%>
						</td>
					</s:else>
					<td class="left"></td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="commonAcci.compensate.receiveCustomerTime" />
					</td>
					<%--接收客户索赔申请时间  --%>
					<td class="right">
						<rc:rcDate name="startApplyPayDate" value="${prpLclaim.startApplyPayDate}" />
					</td>
					<td class="left"></td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certify.whetherInsure" />
					</td>
					<%-- 是否涉及担保 --%>
					<td class="right">
						<s:if test="#prpLclaim.guaranteeFlag == '' || #prpLclaim.guaranteeFlag == '0' || #prpLclaim.guaranteeFlag == '1'">
							<select name="guaranteeFlag">
								<option value="0">
									<s:text name="certainLoss.thirdCarLoss.no" />
								</option>
								<%-- 否 --%>
								<option value="1">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<%--是  --%>
							</select>
						</s:if>
						<s:else>
							<select name="guaranteeFlag">
								<option value="1">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="2">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="3">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="4">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="5">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="6">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
							</select>
						</s:else>
					</td>
					<td class="left">
						<s:text name="commonAcci.claim.involvedLitigat" />
					</td>
					<%--  是否涉及诉讼--%>
					<td class="right">
						<s:if test="#prpLclaim.referLawFlag == '' || #prpLclaim.referLawFlag == '0' || #prpLclaim.referLawFlag == '1'">
							<s:if test="#recaseFlag == '0' || #editType == 'SHOW'">
								<select name="referLawFlag">
									<option value="0">
										<s:text name="certainLoss.thirdCarLoss.no" />
									</option>
									<option value="1">
										<s:text name="certainLoss.thirdCarLoss.yes" />
									</option>
								</select>
							</s:if>
							<s:else>
								<select name="referLawFlag" disabled="true">
									<option value="0">
										<s:text name="certainLoss.thirdCarLoss.no" /><%-- 否 --%>
									</option>
									<option value="1">
										<s:text name="certainLoss.thirdCarLoss.yes" /><%--是  --%>
									</option>
								</select>
							</s:else>
						</s:if>
						<s:else>
							<select name="referLawFlag" disabled="true">
								<option value="1">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="2">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="3">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="4">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="5">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="6">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
							</select>
						</s:else>
					</td>
					<td class="left">
						<s:text name="claim.possibleRec" />
					</td>
					<%-- 是否可能有追偿 --%>
					<td class="right">
						<s:if test="#prpLclaim.replevyFlag == '' || #prpLclaim.replevyFlag == '0' || #prpLclaim.replevyFlag == '1'">
							<select name="replevyFlag">
								<option value="0">
									<s:text name="certainLoss.thirdCarLoss.no" />
								</option>
								<option value="1">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
							</select>
						</s:if>
						<s:else>
							<select name="replevyFlag" disabled="true">
								<option value="1">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="2">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="3">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="4">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="5">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="6">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
							</select>
						</s:else>
					</td>
				</tr>
				<input type="hidden" name="prpLcompensateIndemnityDuty" class="readonly" readonly="true"
					value="${prpLcompensate.indemnityDuty}">
				<input type=hidden name="prpLcompensateIndemnityDutyName"
					class="readonly" readonly="true" value=" ">
				<input type="hidden" name="prpLcompensateIndemnityDutyRate"
					class="readonly" readonly="true" value="0">
			</table>
		</td>
	</tr>
</table>
