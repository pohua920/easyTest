<!--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 结案 ]
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-06-28
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<input type="hidden" name="prpLendcaseClaimNo1" value="${prpLclaim.claimNo}">
					<input type="hidden" name="prpLendcaseCertiNo" value=" ">
					<input type="hidden" name="prpLendcaseCertiType" value="C">
					<input type="hidden" name="prpLendcaseFlag" value="1">
					<input type="hidden" name="prpLendcaseCaseNo" value="${prpLcaseNo.id.caseNo}">
					<input type="hidden" name="prpLendcaseComCode" value="${prpLclaim.comCode}">
					<input type="hidden" name="prpNotBackCount" value="${prpLcaseNo.notBackCount}">
					<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
					<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
					<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
					<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
					<td class="left">
						<s:text name="certainLoss.prpLcheck.riskCName" />
					</td>
					<%--- 险种名称 --%>
					<td class="right">${riskCName }</td>
					<td class="left">
						<s:text name="check.claimNum" />
					</td>
					<%--- 赔案号 --%>
					<td class="right">
						<input type="text" name="prpLclaimClaimNo" title="<s:text name='check.claimNum'/>" class="readonly" readonly="true" value="${prpLclaim.claimNo}"><%--赔案号码--%>
					</td>
					<td class="left">
						<s:text name="db.prpLcompensate.lflag" />
					</td>
					<%---理赔类型  --%>
					<td class="right">
						<c:if test="${prpLclaim.lflag=='D'}">
							<input type="text" name="prpLendcaseLFlag" title="<s:text name='db.prpLregist.lflag'/>" class="readonly" readonly="true" value="<s:text name='compensate.generationClaim'/>">
							<%--理赔类型--%><%--- 代理赔 --%>
						</c:if>
						<c:if test="${prpLclaim.lflag=='L'}">
							<input type="text" name="prpLendcaseLFlag" title="<s:text name='db.prpLregist.lflag'/>" class="readonly" readonly="true" value="<s:text name='endcase.claimsProcessing'/>">
							<%--理赔类型--%><%--- 理赔 --%>
						</c:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<!-- start 增加保费是否已经实收信息-->
				<c:if test="${not empty premiumFee}">
					<input type="hidden" name="premiumFee" value="${premiumFee }">
				</c:if>
				<c:if test="${empty premiumFee}">
					<input type="hidden" name="premiumFee" value="1">
				</c:if>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.businessNature" />
					</td>
					<%---业务性质  --%>
					<td class="right">
						<input type="text" name="prpLclaimBusinessNatureName" class="readonly" readonly="true" value="${prpLclaim.businessNature}">
					</td>
					<td class="left">
						<s:text name="db.prpLregist.language" />
					</td>
					<%--- 语种 --%>
					<td class="right">
						<input type="text" name="prpLclaimLanguageName" class="readonly" readonly="true" value="${prpLclaim.language}">
					</td>
					<td class="left">
						<s:text name="prpLbpmMain.mainNo" />
					</td>
					<%--- 报案号 --%>
					<td class="right">
						<input type="text" name="prpLclaimRegistNo" class="readonly" readonly="true" value="${prpLclaim.registNo}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLlawsuit.policyNo" />
					</td>
					<%--- 保单号 --%>
					<td class="right">
						<input type="text" name="prpLclaimPolicyNo" class="readonly" readonly="true" value="${prpLclaim.policyNo}">
						<br>
						<input type="image" name="btRelate" src="${ctx}/images/butRelate.gif" align="middle" width="54" height="17" border="0"
							onclick="relate(fm.prpLclaimPolicyNo.value, fm.prpLclaimRegistNo.value);return false;">
					</td>
					<td class="left"></td>
					<td class="right"></td>
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
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left"></td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpCmain.insuredName" />
					</td>
					<%--- 被保险人名称 --%>
					<td class="right">
						<input type="text" name="prpLclaimInsuredName" title="<s:text name='db.prpCmain.insured'/>" class="readonly" readonly="true" value="${prpLclaim.insuredName}"><%--- 被保险人 --%>
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.sumAmount" />
					</td>
					<%---保险金额  --%>
					<td class="right">
						<input type="text" name="prpLclaimSumAmount" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumAmount}' pattern='#'/>">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.insuranceTime" />
					</td>
					<%--- 保险期间 --%>
					<td class="right" colspan="2">
						<%--<input type="text" name="prpLendcaseStartDate" style="width:275px" class="readonly"  readonly="true"  value="${prpLclaim.startDate} <s:text name='endcase.dayStart'/> ${prpLclaim.endDate} <s:text name='endcase.dayEnd'/>"> --%>
						<%--- 日 0 时 至 --%>
						<%--- 日 24 时止 --%>
						<rc:rcDate name="prpLendcaseStartDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.startDate}" />
						<s:text name='endcase.dayStart' />
						<rc:rcDate name="endDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.endDate}" />
						<s:text name='endcase.dayEnd' />
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.currency" />
					</td>
					<%---币别  --%>
					<td class="left">
						<input type="text" name="prpLclaimCurrency" class="readonly" readonly="true" value="${prpLclaim.currency}">
					</td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.damageTime" />
					</td>
					<%---出险时间  --%>
					<td class="right">
						<%--  <input type=text name="prpLendcaseDamageStartDate" title="出险时间" readonly="true" class="readonly"  value="${prpLclaim.damageStartDate} <s:text name='regist.prpLregist.date'/> ${prpLclaim.damageStartHour} <s:text name='regist.prpLregist.hour'/>"> --%>
						<%--- 日 --%>
						<%--- 时 --%>
						<rc:rcDate name="prpLendcaseDamageStartDate" title="<s:text name='regist.prpLregist.damageTime'/>" class="readonly" readonly="true" style="width:80px" wdatePicker="false" value="${prpLclaim.damageStartDate}" /><%--出险时间--%>
						<s:text name='regist.prpLregist.date' />
						${prpLclaim.damageStartHour}
						<s:text name='regist.prpLregist.hour' />
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.damageCode" />
					</td>
					<%--- 出险原因 --%>
					<td class="right">
						<input type="text" name="prpLclaimDamageName" class="readonly" readonly="true" value="${prpLclaim.damageName}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageAddress" />
					</td>
					<%--- 出险地点 --%>
					<td class="right">
						<input type="text" name="prpLclaimDamageAddress" class="readonly" readonly="true" value="${prpLclaim.damageAddress}">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.lossName" />
					</td>
					<%--- 受损标的 --%>
					<td class="right">
						<input type="text" name="prpLclaimLossName" class="readonly" readonly="true" value="${prpLclaim.lossName}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.claimDate" />
					</td>
					<%---立案日期  --%>
					<td class="right">
						<%--<input type="text" name="prpLclaimClaimDate"   class="readonly" readonly="true" value="${prpLclaim.claimDate}">  --%>
						<rc:rcDate name="prpLclaimClaimDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.claimDate}" />
					</td>
					<td class="left">
						<s:text name="db.prpLregist.estimateLoss" />
						<input type="text" name="prpLclaimCurrency" style="width: 20%" class="readonly" readonly="true" value="${prpLclaim.currency}">
					</td>
					<%--- 估损金额 --%>
					<td class="right">
						<input type="text" name="prpLclaimSumClaim" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumClaim}' pattern='#'/>">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name='db.prpLcfee.sumPaid'/><%--赔付金额--%>
						<input type="text" name="prpLclaimCurrency" class="readonly" readonly="true" value="${prpLclaim.currency}" style="width: 20%;">
					</td>
					<td class="right" cellspacing="3">
						<input type="text" name="prpLclaimSumPaid" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumPaid}' pattern='#'/>">
					</td>
					<td class="left">
						<span  style ="display:none">
							<s:text name="certify.whetherInsure" />
						</span>
					</td>
					<%--- 是否涉及担保 --%>
					<td class="right" >
						<span style ="display:none">
						<select name="guaranteeFlag" id="guaranteeFlag" style="width: 50%" disabled="true">
							<option value="0" <c:if test="${prpLclaim.guaranteeFlag=='0' }">selected="selected"</c:if>>
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--- 否 --%>
							<option value="" <c:if test="${prpLclaim.guaranteeFlag=='' }">selected="selected"</c:if>>
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--- 否 --%>
							<option value="1" <c:if test="${prpLclaim.guaranteeFlag=='1' }">selected="selected"</c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%--- 是 --%>
							<option value="2" <c:if test="${prpLclaim.guaranteeFlag=='2' }">selected="selected"</c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%--- 是 --%>
							<option value="3" <c:if test="${prpLclaim.guaranteeFlag=='3' }">selected="selected"</c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%--- 是 --%>
							<option value="4" <c:if test="${prpLclaim.guaranteeFlag=='4' }">selected="selected"</c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%--- 是 --%>
							<option value="5" <c:if test="${prpLclaim.guaranteeFlag=='5' }">selected="selected"</c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%--- 是 --%>
							<option value="6" <c:if test="${prpLclaim.guaranteeFlag=='6' }">selected="selected"</c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%--- 是 --%>
						</select>
						</span>
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
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="endcase.policyBusiness" />
					</td>
					<%--- 保单业务归属部门 --%>
					<td class="right">
						<input type="text" name="prpLclaimComName" class="readonly" readonly="true" value="${prpLclaim.comName}">
					</td>
					<td class="left">
						<s:text name="db.prpLregist.handler1Code" />
					</td>
					<%--- 归属业务员 --%>
					<td class="right">
						<input type="text" name="prpLclaimHandler1Name" class="readonly" readonly="true" value="${prpLclaim.handler1Name}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.agentCode" />
					</td>
					<%--- 代理人 --%>
					<td class="right">
						<input type="text" name="prpLclaimAgentCode" class="readonly" readonly="true" value="${prpLclaim.agentCode}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLregist.handler1Name" />
					</td>
					<%--- 经办人 --%>
					<td class="right">
						<input type="text" name="prpLclaimHandlerName" class="readonly" readonly="true" value="${prpLclaim.handlerName}">
					</td>
					<td class="left">
						<s:text name="endcase.claimsDepartment" />
					</td>
					<%--- 理赔部门 --%>
					<td class="right">
						<input type="text" name="prpLendcaseMakeCom" class="readonly" readonly="true" value="${prpLclaim.makeComName}">
					</td>
					<td class="left">
						<s:text name="db.prpLlawsuit.operatorCode" />
					</td>
					<%--- 操作员 --%>
					<td class="right">
						<input type="text" name="prpLregistOperatorName" class="readonly" readonly="true" value="${prpLclaim.operatorName}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLlawsuit.inputDate" />
					</td>
					<%--- 输单日期 --%>
					<td class="right" colspan="3">
						<%--  <input type="text" name="prpLclaimInputDate"   class="readonly" readonly="true" value="${prpLclaim.inputDate}"> --%>
						<rc:rcDate name="prpLclaimInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.inputDate}" />
					</td>
					<td class="left">
						<s:text name="db.prpLreplevy.endCaseDate" />
					</td>
					<%--- 结案日期 --%>
					<td class="right">
						<%-- <input type="text" name="prpLclaimEndCaseDate"  class="readonly" readonly="true" value="${prpLclaim.endCaseDate}"> --%>
						<rc:rcDate name="prpLclaimEndCaseDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.endCaseDate}" />
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="endcase.closed" />
					</td>
					<%--- 结案员 --%>
					<td class="right">
						<input type="hidden" name="prpLclaimEndCaserCode" class="readonly" readonly="true" value="${prpLclaim.endCaserCode}">
						<input type="text" name="prpLclaimEndCaseName" class="readonly" readonly="true" value="${prpLclaim.endCaserName}">
					</td>
					<td class="left">
						<s:text name="db.prpLreplevy.cancelDate" />
					</td>
					<%--- 注销日期 --%>
					<td class="right">
						<%--<input type="text" name="prpLclaimCancelDate"   class="readonly" readonly="true" value="${prpLclaim.cancelDate}">  --%>
						<rc:rcDate name="prpLclaimCancelDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.cancelDate}" />
					</td>
					<td class="left">
						<s:text name="endcase.logPeople" />
					</td>
					<%--- 注销人 --%>
					<td class="right">
						<input type="text" name="prpLclaimDealerCode" class="readonly" readonly="true" value="${prpLclaim.dealerCode}">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
