<!--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 结案 ]
* AUTHOR     ： 中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
<table class=common cellpadding="5" cellspacing="1">
	<tr class=listtitle>
		<td style="width: 100%">
			<s:text name="commonLiab.endcase.registrateSettle" />
			<%--结案登记--%>
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="title">
						<s:text name="query.xianzhongName" /> ：
						<input type="hidden" name="prpLendcaseClaimNo1" value="${prpLclaim.claimNo}"> <input type="hidden" name="prpLendcaseCertiNo" value=" "> <input type="hidden" name="prpLendcaseCertiType" value="C"> <input type="hidden"
							name="prpLendcaseFlag" value="1"> <input type="hidden" name="prpLendcaseCaseNo" value="${prpLcaseNo.id.caseNo}"> <input type="hidden" name="prpLendcaseComCode" value="${prpLclaim.comCode}"> <input type="hidden"
							name="prpNotBackCount" value="${prpLcaseNo.notBackCount}"> <input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}"> <input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}"> <input
							type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>"> <input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
					</td>
					<td class="input">
						${riskCName }
					</td>
					<%--险种名称--%>
					<td class="title"></td>
					<td class="input"></td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="certainLoss.claims" />
						:
					</td>
					<%--赔案号--%>
					<td class="input">
						<input type="text" name="prpLclaimClaimNo" title="赔案号" class="readonly" style="width: 160px" readonly="true" value="${prpLclaim.claimNo}">
					</td>
					<td class="title">
						<s:text name="db.prpLcompensate.lflag" />
						:
					</td>
					<%--理赔类型--%>
					<td class="input">
						<c:if test="${prpLclaim.lflag=='D'}">
							<input type="text" name="prpLendcaseLFlag" title="理赔类型" style="width: 160px" class="readonly" readonly="true" value="代理赔">
						</c:if>
						<c:if test="${prpLclaim.lflag=='L'}">
							<input type="text" name="prpLendcaseLFlag" title="理赔类型" style="width: 160px" class="readonly" readonly="true" value="理赔">
						</c:if>
					</td>
				</tr>
				<!--增加保费是否已经实收信息-->
				<c:if test="${not empty premiumFee}">
					<input type="hidden" name="premiumFee" value="${premiumFee }">
				</c:if>
				<c:if test="${not empty premiumFee}">
					<input type="hidden" name="premiumFee" value="1">
				</c:if>
				<tr>
					<td class="title">
						<s:text name="prpLregist.registNo" />
						:
					</td>
					<%--报案号--%>
					<td class="input">
						<input type="text" name="prpLclaimRegistNo" class="readonly" readonly="true" style="width: 160px" value="${prpLclaim.registNo}">
					</td>
					<td class="title">
						<s:text name="db.prpCmain.policyNo" />
						:
					</td>
					<%--保单号--%>
					<td class="input">
						<input type="text" style="width: 140px" name="prpLclaimPolicyNo" class="readonly" readonly="true" value="${prpLclaim.policyNo}">
						<input type="image" style="" name="btRelate" src="${ctx}/images/butRelate.gif" align="middle" width="54" height="17" border="0"
							onclick="relate(fm.prpLclaimPolicyNo.value, fm.prpLclaimRegistNo.value);return false;">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLclaim.businessNature" />
						：
					</td>
					<%--业务性质--%>
					<td class="input">
						<input type="text" name="prpLclaimBusinessNatureName" class="readonly" readonly="true" value="${prpLclaim.businessNature}">
					</td>
					<td class="title">
						<s:text name="db.prpLclaim.language" />
						：
					</td>
					<%--语种--%>
					<td class="input">
						<input type="text" name="prpLclaimLanguageName" class="readonly" readonly="true" style="width: 160px" value="${prpLclaim.language}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLCMain.insuredName" />
						:
					</td>
					<%--被保险人名称--%>
					<td class="input" colspan="3">
						<input type="text" name="prpLclaimInsuredName" title="被保险人" style="width: 330px" class="readonly" readonly="true" value="${prpLclaim.insuredName}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="regist.prpLregist.insuranceTime" />
						：
					</td>
					<%--保险期间--%>
					<td class="input" colspan="3">
						<input type="text" name="prpLendcaseStartDate" class="readonly" style="width: 340px" readonly="true" value="${prpLclaim.startDate} 日 0 时 至 ${prpLclaim.endDate} 日 24 时止">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLperson.currency" />
						：
					</td>
					<%--币别--%>
					<td class="title">
						<input type="text" name="prpLclaimCurrency" class="readonly" readonly="true" value="${prpLclaim.currency}">
					</td>
					<td class="title">
						<s:text name="regist.prpLregist.sumAmount" />
						：
					</td>
					<%--保险金额--%>
					<td class="input">
						<input type="text" name="prpLclaimSumAmount" class="readonly" style="width: 160px" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumAmount}' pattern='#'/>">
					</td>
				</tr>
				<tr>
					<td class="title">
						出險時間：
					</td>
					<%--出险时间--%>
					<td class="input" >
						<rc:rcDate name="prpLendcaseDamageStartDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.damageStartDate}" />
						<s:text name='regist.prpLregist.date' />
						${prpLclaim.damageStartHour}
						<s:text name='regist.prpLregist.hour' />
					</td>
					<td class="title">
						出險原因：
					</td>
					<%--出险原因--%>
					<td class="input" >
						<input type="text" name="prpLclaimDamageName" style="width: 380px" class="readonly" readonly="true" value="${prpLclaim.damageName}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
					</td>
					<%--出险地点--%>
					<td class="input" colspan="3">
						<input type="text" name="prpLclaimDamageAddress" style="width: 380px" class="readonly" readonly="true" value="${prpLclaim.damageAddress}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLclaim.lossName" />
						：
					</td>
					<%--受损标的--%>
					<td class="input" colspan="3">
						<input type="text" name="prpLclaimLossName" style="width: 380px" class="readonly" readonly="true" value="${prpLclaim.lossName}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLclaim.claimDate" />
						：
					</td>
					<%--立案日期--%>
					<td class="input" colspan="3">
						<rc:rcDate name="prpLclaimClaimDate" class="readonly" readonly="true" wdatePicker="false" style="width:187px" value="${prpLclaim.claimDate}" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLregist.estimateLoss" />（${prpLclaim.currency}）：
					</td>
					<%--估损金额--%>
					<td class="input">
						<input type="text" name="prpLclaimSumClaim" style="width: 180px" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumClaim}' pattern='#'/>">
					</td>
					<td class="title">
						<s:text name="db.prpLCMain.sumClaim" />（${prpLclaim.currency}）：
					</td>
					<%--赔付金额--%>
					<td class="input">
						<input type="text" name="prpLclaimSumPaid" style="width: 180px" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumPaid}' pattern='#'/>">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="endcase.policyBusiness" />
						：
					</td>
					<%--保单业务归属部门--%>
					<td class="input">
						<input type="text" name="prpLclaimComName" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.comName}">
					</td>
					<td class="title">
						<s:text name="db.prpLclaim.handler1Code" />
						：
					</td>
					<%--归属业务员--%>
					<td class="input">
						<input type="text" name="prpLclaimHandler1Name" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.handler1Name}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLclaim.agentCode" />
						：
					</td>
					<%--代理人--%>
					<td class="input">
						<input type="text" name="prpLclaimAgentCode" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.agentCode}">
					</td>
					<td class="title">
						<s:text name="db.prpLclaim.handlerCode" />
						：
					</td>
					<%--经办人--%>
					<td class="input">
						<input type="text" name="prpLclaimHandlerName" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.handlerName}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="endcase.claimsDepartment" />
						：
					</td>
					<%--理赔部门--%>
					<td class="input">
						<input type="text" name="prpLendcaseMakeCom" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.makeComName}">
					</td>
					<td class="title">
						<s:text name="db.prpLlawsuit.operatorCode" />
						：
					</td>
					<%--操作员--%>
					<td class="input">
						<input type="text" name="prpLregistOperatorName" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.operatorName}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLlawsuit.inputDate" />
						：
					</td>
					<%--输单日期--%>
					<td class="input" colspan="3">
						<input type="text" name="prpLclaimInputDate" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.inputDate}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLreplevy.endCaseDate" />
						：
					</td>
					<%--结案日期--%>
					<td class="input">
						<input type="text" name="prpLclaimEndCaseDate" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.endCaseDate}">
					</td>
					<td class="title">
						<s:text name="endcase.closed" />
						：
					</td>
					<%--结案员--%>
					<td class="input">
						<input type="hidden" name="prpLclaimEndCaserCode" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.endCaserCode}"> <input type="text" name="prpLclaimEndCaseName" class="readonly" readonly="true"
							value="${prpLclaim.endCaserName}">
					</td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="certify.whetherInsure" />
						：
					</td>
					<%--是否涉及担保--%>
					<td class="input">
						<select name="guaranteeFlag" style="width: 50%" disabled="true">
							<option <c:if test="${prpLclaim.guaranteeFlag=='0' }">selected="selected"</c:if> value="0"><s:text name="certainLoss.thirdCarLoss.no" /></option>
							<%--否--%>
							<option <c:if test="${prpLclaim.guaranteeFlag=='' }">selected="selected"</c:if> value=""><s:text name="certainLoss.thirdCarLoss.no" /></option>
							<%--否--%>
							<option <c:if test="${prpLclaim.guaranteeFlag=='1' }">selected="selected"</c:if> value="1"><s:text name="certainLoss.thirdCarLoss.yes" /></option>
							<%--是--%>
							<option <c:if test="${prpLclaim.guaranteeFlag=='2' }">selected="selected"</c:if> value="2"><s:text name="certainLoss.thirdCarLoss.yes" /></option>
							<%--是--%>
							/option>
							<option <c:if test="${prpLclaim.guaranteeFlag=='3' }">selected="selected"</c:if> value="3"><s:text name="certainLoss.thirdCarLoss.yes" /></option>
							<%--是--%>
							/option>
							<option <c:if test="${prpLclaim.guaranteeFlag=='4' }">selected="selected"</c:if> value="4"><s:text name="certainLoss.thirdCarLoss.yes" /></option>
							<%--是--%>
							/option>
							<option <c:if test="${prpLclaim.guaranteeFlag=='5' }">selected="selected"</c:if> value="5"><s:text name="certainLoss.thirdCarLoss.yes" /></option>
							<%--是--%>
							/option>
							<option <c:if test="${prpLclaim.guaranteeFlag=='6' }">selected="selected"</c:if> value="6"><s:text name="certainLoss.thirdCarLoss.yes" /></option>
							<%--是--%>
							/option>
						</select>
					</td>
					<td class="title"></td>
					<td class="input"></td>
				</tr>
				<tr>
					<td class="title">
						<s:text name="db.prpLreplevy.cancelDate" />
						：
					</td>
					<%--注销日期--%>
					<td class="input">
						<input type="text" name="prpLclaimCancelDate" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.cancelDate}">
					</td>
					<td class="title">
						<s:text name="endcase.logPeople" />
						:
					</td>
					<%--注销人--%>
					<td class="input">
						<input type="text" name="prpLclaimDealerCode" style="width: 180px" class="readonly" readonly="true" value="${prpLclaim.dealerCode}">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>