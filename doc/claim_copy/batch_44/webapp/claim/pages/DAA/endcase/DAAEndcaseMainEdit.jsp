<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 结案 ]
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-02-03
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<c:choose>
	<c:when test="${dfFlag!=null && !dfFlag eq ''&& dfFlag eq 'Y'}">
		<input type="hidden" name="dfFlag" value="${dfFlag}">
	</c:when>
	<c:otherwise>
		<input type="hidden" name="dfFlag" value="N">
	</c:otherwise>
</c:choose>
<table class=common cellpadding="5" cellspacing="1">
	<tr class=listtitle>
		<c:choose>
			<c:when test="${dfFlag!=null && !dfFlag eq '' && dfFlag eq 'Y'}">
				<td colspan="4" style="width: 100%">
					<s:text name="endcase.closedRegistration" />
					<!--结案登记 (垫付抢救费)-->
			</c:when>
			<c:otherwise>
				<td colspan="4" style="width: 100%">
					<s:text name="endcase.registration" />
					<!--结案登记-->
			</c:otherwise>
		</c:choose>
		<input type="hidden" name="prpLendcaseClaimNo1" value="${prpLclaim.claimNo}">
		<input type="hidden" name="prpLendcaseCertiNo" value=" ">
		<input type="hidden" name="prpLendcaseCertiType" value="C">
		<input type="hidden" name="prpLendcaseFlag" value="1">
		<input type="hidden" name="prpLendcaseCaseNo" value="${prpLcaseNo.id.caseNo}">
		<input type="hidden" name="prpLendcaseComCode" value="${prpLclaim.comCode}">
		<%--resson: 增加预赔号和赔款计算书号--%>
		<input type="hidden" name="prpLendcasepreCompensateNo" value="${prpLcaseNo.preCompensateNo}">
		<input type="hidden" name="prpLendcasecompensateNo" value="${prpLcaseNo.compensateNo}">
		<c:if test="${premiumFee!=null}">
			<input type="hidden" name="premiumFee" value="${premiumFee }">
		</c:if>
		<c:if test="${premiumFee==null}">
			<input type="hidden" name="premiumFee" value="1">
		</c:if>
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="certainLoss.claims" />
					</td>
					<!--赔案号-->
					<td class="right">
						<input type="text" name="prpLclaimClaimNo" title="賠案號碼" class="readonly" readonly="true" value="${prpLclaim.claimNo}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaimApprov.registNo" />
					</td>
					<!--报案号-->
					<td class="right">
						<input type="text" name="prpLclaimRegistNo" style="width: 100%" title="備案號碼" class="readonly" readonly="true" value="${prpLclaim.registNo}">
					</td>
					<td class="left">
						<s:text name="db.prpLregist.lflag" />
					</td>
					<!--理赔类型-->
					<td class="right">
						<c:if test="${prpLclaim.lflag=='D'}">
							<input type="text" name="prpLendcaseLFlag" title="理賠類型" class="readonly" readonly="true" value="<s:text name='endcase.agentCompensation'/>">
							<!--代理赔-->
						</c:if>
						<c:if test="${prpLclaim.lflag=='L'}">
							<input type="text" name="prpLendcaseLFlag" title="理賠類型" class="readonly" readonly="true" value="<s:text name='endcase.claimsProcessing'/>">
							<!--理赔-->
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
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="db.view_larrearage.policyNo" />
					</td>
					<!--保单号-->
					<td class="right">
						<input type="text" name="prpLclaimPolicyNo" style="width: 100%" class="readonly" readonly="true" value="${prpLclaim.policyNo }">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" onclick="relate2();return false;">
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.insuranceTime" />
					</td>
					<!--保险期间-->
					<td class="right" colspan="3">
						<%-- <input type="text" name="prpLendcaseStartDate" class="readonly" style="width:340px" readonly="true"  value="${prpLclaim.startDate} <s:text name='endcase.dayStart'/>${prpLclaim.endDate}<s:text name='endcase.dayEnd'/>">--%>
						<!--日 0 时 至 -->
						<!--日 24 时止-->
						<rc:rcDate name="prpLendcaseStartDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.startDate}" />
						<s:text name='endcase.dayStart' />
						<rc:rcDate name="endDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.endDate}" />
						<s:text name='endcase.dayEnd' />
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLCItemCar.clauseType" />
					</td>
					<!--条款类别-->
					<td class="right" colspan="3">
						<input class="readonly" type=text name="prpLclaimClauseType" readonly="true" style="width: 27%" value="${prpLclaim.clauseType}">
						<input class="readonly" type=text name="prpLclaimClauseName" readonly="true" style="width: 48%" value="${prpLclaim.clauseName}">
					</td>
					<td class="left">
						<s:text name="db.prpLsalvation.licenseNo" />
					</td>
					<!--号牌号码-->
					<td class="right">
						<input class="readonly" name="prpLclaimLicenseNo" readonly="true" value="${prpLclaim.licenseNo}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLlawsuit.licenseColorCode" />
					</td>
					<!--号牌底色-->
					<td class="right">
						<input class="readonly" name="prpLclaimLicenseColor" readonly="true" value="${prpLclaim.licenseColor}">
					</td>
					<td class="left">
						<s:text name="db.prpLlawsuit.brandName" />
					</td>
					<!--厂牌型号-->
					<td class="right">
						<input class="readonly" name="prpLclaimBrandName" readonly="true" value="${prpLclaim.brandName}">
					</td>
					<td class="left">
						<s:text name="certainLoss.thirdCarLoss.carKind" />
					</td>
					<!--车辆种类-->
					<td class="right">
						<input name="prpLclaimCarKind" class="readonly" readonly="true" value="${prpLclaim.carKind}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLreplevynew.currency" />
					</td>
					<!--币别-->
					<td class="right">
						<input class="readonly" readonly name="prpLclaimCurrencyName" value="<%=com.sinosoft.claim.common.ConstantCodes.LOCAL_CURRENCYNAME%>">
						<!--人民币-->
						<%--<s:text name='certainLoss.rmb'/>--%>
					</td>
					<td class="left">
						<s:text name="db.prpLCitemKind.amount" />
					</td>
					<!--保险金额-->
					<td class="right">
						<input class="readonly" name="prpLclaimSumAmount" readonly="true" value="<fmt:formatNumber pattern='#' value='${prpLclaim.sumAmount}'/>">
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
						<s:text name="regist.prpLregist.damageTime" />
					</td>
					<!--出险时间-->
					<td class="right">
						<%-- <input type=text name="prpLendcaseDamageStartDate" readonly="true" class="readonly" value="${prpLclaim.damageStartDate}<s:text name='regist.prpLregist.date'/>  ${prpLclaim.damageStartHour}<s:text name='regist.prpLregist.hour'/> ">--%>
						<!--日 -->
						<!--时-->
						<rc:rcDate name="prpLendcaseDamageStartDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.damageStartDate}" />
						<s:text name='regist.prpLregist.date' />
						${prpLclaim.damageStartHour}
						<s:text name='regist.prpLregist.hour' />
					</td>
					<td class="left">
						<s:text name="db.prpLregist.damageTypeCode" />
					</td>
					<!--事故原因-->
					<td class="right">
						<input type=text class="readonly" readonly="true" name="prpLclaimDamageTypeName" title="事故原因" value="${prpLclaim.damageTypeName}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageName" />
					</td>
					<!--出险原因-->
					<td class="right">
						<input type="text" name="prpLclaimDamageName" class="readonly" readonly="true" value="${prpLclaim.damageName}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.damageAreaName" />
					</td>
					<!--出险区域-->
					<td class="right">
						<input type="text" name="prpLclaimDamageAreaName" class="readonly" readonly="true" value="${prpLclaim.damageAreaName}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageAddress" />
					</td>
					<!--出险地点-->
					<td class="right">
						<input type="text" name="prpLclaimDamageAddress" class="readonly" readonly="true" value="${prpLclaim.damageAddress}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.claimDate" />
					</td>
					<!--立案日期-->
					<td class="right">
						<%-- <input type="text" name="prpLclaimClaimDate" class="readonly" readonly="true" value="${prpLclaim.claimDate}"> --%>
						<rc:rcDate name="prpLclaimClaimDate" class="readonly" readonly="true" wdatePicker="false" style="width:187px" value="${prpLclaim.claimDate}" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="left">
						<s:text name="certainLoss.thirdCarLoss.indemnityDuty" />
					</td>
					<!--事故责任-->
					<td class="right">
						<input type="text" name="prpLclaimIndemnityDutyName" class="readonly" readonly="true" value="${prpLclaim.indemnityDutyName}">
					</td>
					<td class="left">
						<s:text name="db.prpLpersonloss.indemnityDutyRate" />
					</td>
					<!--责任比例-->
					<td class="right">
						<input type="text" name="prpLclaimIndemnityDutyRate" class="readonly" readonly="true" value="${prpLclaim.indemnityDutyRate}">%
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.sumClaim" />
					</td>
					<!--保险损失金额-->
					<td class="right">
						<input type="text" name="prpLclaimSumClaim" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumClaim}' pattern='#'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLregist.claimType" />
					</td>
					<!--赔案类别-->
					<td class="right">
						<input type="text" name="prpLclaimClaimTypeName" class="readonly" readonly="true" value="${prpLclaim.claimTypeName}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLreplevynew.sumpaid" />
					</td>
					<!--赔付金额-->
					<td class="right">
						<input type="text" name="prpLclaimSumPaid" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumPaid}' pattern='#'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.caseType" />
					</td>
					<!--案件性质-->
					<td class="right">
						<input type="text" name="prpLclaimCaseType" class="readonly" readonly="true" value="${prpLclaim.caseType}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="endcase.policyBusiness" />
					</td>
					<!--保单业务归属部门-->
					<td class="right">
						<input type="text" name="prpLclaimComName" class="readonly" readonly="true" value="${prpLclaim.comName}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.handler1Code" />
					</td>
					<!--归属业务员-->
					<td class="right">
						<input type="text" name="prpLclaimHandler1Name" class="readonly" readonly="true" value="${prpLclaim.handler1Name}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.handlerCode" />
					</td>
					<!--经办人-->
					<td class="right">
						<input type="text" name="prpLclaimHandlerName" class="readonly" readonly="true" value="${prpLclaim.handlerName}">
					</td>
					<td class="left">
						<s:text name="endcase.claimsDepartment" />
					</td>
					<!--理赔部门-->
					<td class="input">
						<input type="text" name="prpLendcaseMakeCom" class="readonly" readonly="true" value="${prpLclaim.makeComName}">
					</td>
					<td class="left">
						<s:text name="db.prpLlawsuit.operatorCode" />
					</td>
					<!--操作员-->
					<td class="right">
						<input type="text" name="prpLregistOperatorName" class="readonly" readonly="true" value="${prpLclaim.operatorName}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLregist.inputDate" />
					</td>
					<!--输单日期-->
					<td class="right">
						<%-- <input type="text" name="prpLclaimInputDate" class="readonly" readonly="true" value="${prpLclaim.inputDate}"> --%>
						<rc:rcDate name="prpLclaimInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.inputDate}" />
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.endCaseDate" />
					</td>
					<!--结案日期-->
					<td class="right">
						<%-- <input type="text" name="prpLclaimEndCaseDate" class="readonly" readonly="true" value="${prpLclaim.endCaseDate}"> --%>
						<rc:rcDate name="prpLclaimEndCaseDate" class="readonly" readonly="true" wdatePicker="false" style="width:120px" value="${prpLclaim.endCaseDate}" />
					</td>
					<td class="left">
						<s:text name="endcase.closed" />
					</td>
					<!--结案员-->
					<td class="right">
						<input type="hidden" name="prpLclaimEndCaserCode"  value="${prpLclaim.endCaserCode}">
						<input type="text" name="prpLclaimEndCaserName" class="readonly" readonly="true" value="${prpLclaim.endCaserName}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLreplevy.cancelDate" />
					</td>
					<!--注销日期-->
					<td class="right">
						<%-- <input type="text" name="prpLclaimCancelDate" class="readonly" readonly="true" value="${prpLclaim.cancelDate}"> --%>
						<rc:rcDate name="prpLclaimCancelDate" class="readonly" readonly="true" wdatePicker="false" style="width:65px" value="${prpLclaim.cancelDate}" />
					</td>
					<td class="left">
						<s:text name="endcase.logPeople" />
					</td>
					<!--注销人-->
					<td class="right">
						<input type="text" name="prpLclaimDealerCode" class="readonly" readonly="true" value="${prpLclaim.dealerCode}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
