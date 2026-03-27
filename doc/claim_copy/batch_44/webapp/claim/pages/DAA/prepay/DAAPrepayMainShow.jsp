<!--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 立案 ]
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-05-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
<table cellpadding="5" cellspacing="1" class="common">
	<tr>
		<td>
			<input type="button" class=bigbutton name="message" value="<s:text name='button.disMessage.value'/>"
				onclick="openWinSave(fm.prpLprepayClaimNo.value,fm.prpLprepayPolicyNo.value,fm.prpLprepayRiskCode.value,'prepa',fm.prpLprepayClaimNo.value)">
		</td>
		<!--讨论留言-->
		<td>
			<input type="button" class=bigbutton name="messageView" value="<s:text name='button.viewMessage.value'/>" onclick="openWinQuery('claimNo',fm.prpLprepayClaimNo.value)">
		</td>
		<!--查看留言-->
	</tr>
</table>
<table class=common cellpadding="5" cellspacing="1">
	<tr class=listtitle>
		<td colspan="4">
			<s:text name="prepay.paymentRegistration" />
			<!--预付赔款登记-->
			<input type="hidden" name="prpLprepayUnderWriteFlag" value="0">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="prepay.paidAdvance" />：
		</td>
		<!--预付赔款号-->
		<td class="input" style="width: 35%">
			<input type=text name="prpLprepayPreCompensateNo" title="預付賠款號" maxlength="22" class="readonly" readonly="true" value="${prpLprepay.preCompensateNo}">
		</td>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.claims" />：
		</td>
		<!--赔案号-->
		<td class="input" style="width: 35%">
			<input type=text name="prpLprepayClaimNo" title="賠案號碼" maxlength="22" class="readonly" readonly="true" value="${prpLprepay.claimNo}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLlawsuit.policyNo" />：
		</td>
		<!--保单号-->
		<td class="input">
			<input type=text name="prpLprepayPolicyNo" class="readonly" readonly="true" style="width: 140px" value="${prpLprepay.policyNo}">
			<input type="image" name="btRelate" src="${ctx }/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.PolicyNo.value);">
		</td>
		<td class="title">
			<s:text name="db.prpLCItemCar.clauseType" />：
		</td>
		<!--条款类别-->
		<td class="input">
			<input class="readonly" type=text name="prpLprepayClauseName" readonly="true" style="width: 150px" value="${prpLprepay.clauseName}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="regist.prpLregist.insuranceTime" />：
		</td>
		<!--保险期间-->
		<td colspan="3" class="input">
			<input type=text name="prpLprepayStartDate" class="readonly" style="width: 340px" readonly="true"
				value="${prpLprepay.startDate}<s:text name='endcase.dayStart'/>  ${prpLprepay.endDate}<s:text name='endcase.dayEnd'/> ">
			<!--日 0 时 至-->
			<!--日 24 时止-->
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLsalvation.licenseNo" />:
		</td>
		<!--号牌号码-->
		<td class="input">
			<input class="readonly" name="prpLprepayLicenseNo" readonly="true" value="${prpLprepay.licenseNo}">
		</td>
		<td class="title">
			<s:text name="db.prpLlawsuit.licenseColorCode" />：
		</td>
		<!--号牌底色-->
		<td class="input">
			<input class="readonly" name="prpLprepayLicenseColor" readonly="true" value="${prpLprepay.licenseColor}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="certainLoss.thirdCarLoss.carKind" />：
		</td>
		<!--车辆种类-->
		<td class="input">
			<input name="prpLprepayCarKind" class="readonly" readonly="true" value="${prpLprepay.carKind}">
		</td>
		<td class="title">
			<s:text name="db.prpLregist.brandName" />：
		</td>
		<!--厂牌型号-->
		<td class="input">
			<input class="readonly" name="prpLprepayBrandName" readonly="true" value="${prpLprepay.brandName}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLCItemCar.engineNo" />：
		</td>
		<!--发动机号-->
		<td class="input">
			<input type="text" name="prpLprepayEngineNo" class="readonly" readonly="true" maxlength=20 description="发动机号" value="${prpLprepay.engineNo}">
		</td>
		<td class="title">
			<s:text name="db.prpLinvestigate.frameNo" />：
		</td>
		<!--车架号-->
		<td class="input">
			<input type="text" name="prpLprepayFrameNo" class="readonly" readonly="true" maxlength=20 description="车架号" value="${prpLprepay.frameNo}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />：
		</td>
		<!--出险时间-->
		<td colspan="3" class="input">
			<input type=text name="prpLprepayDamageStartDate" class="readonly" readonly="true" maxlength="10" style="width: 250px"
				value="${prpLprepay.damageStartDate}<s:text name='regist.prpLregist.date'/>  ${prpLprepay.damageStartHour}<s:text name='regist.prpLregist.hour'/> ">
			<!--日-->
			<!--时-->
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLclaim.damageAddress" />：
		</td>
		<!--出险地点-->
		<td colspan="3" class="input">
			<input type=text name="prpLprepayDamageAddress" title="出險地" style="width: 400px" class="readonly" readonly="true" value="${prpLprepay.damageAddress}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.estimateLoss" />：
		</td>
		<!--估损金额(CNY)-->
		<td class="input">
			<input class="readonly" readonly name="prpLprepaySumClaim" description="保险损失金额" value="<fmt:formatNumber value="${prpLprepay.sumClaim}" pattern="#"/>" value="">
		</td>
		<td class="title">
			<s:text name="db.prpLCitemKind.amount" />：
		</td>
		<!--保险金额(CNY)-->
		<td class="input">
			<input name="prpLprepaySumAmount" type="text" class="readonly" readonly="true" value="<fmt:formatNumber value="${prpLprepay.sumAmount}" pattern="#"/>">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLreplevynew.currency" />：
		</td>
		<!--币别-->
		<td class="input">
			<input class="readonly" style="width: 100" name="prpLprepayCurrencyName" value="<s:text name='certainLoss.rmb '/>">
			<img src="${ctx }/images/bgMarkMustInput.jpg">
			<!--人民币-->
		</td>
		<td class="title">
			<s:text name="db.prpLprepay.sumPrepaid" />：
		</td>
		<!--预赔金额-->
		<td class="input">
			<input name="prpLprepaySumPrePaid" type="text" class="readonly" readonly="true" style="width: 130" value="<fmt:formatNumber value="${prpLprepay.sumPrePaid}" pattern="#"/>">
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLclaim.comCode" />：
		</td>
		<!--业务归属机构-->
		<td class="input">
			<input type=text name="prpLprepayComName" title="業務歸屬機構" class="readonly" readonly="true" value="${prpLprepay.comName}">
		</td>
		<td class="title">
			<s:text name="db.prpLclaim.handler1Code" />：
		</td>
		<!--归属业务员-->
		<td class="input">
			<input type=text name="prpLprepayHandler1Name" title="歸屬業務員" class="readonly" readonly="true" value="${prpLprepay.handler1Name}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLlawsuit.handlerCode" />：
		</td>
		<!--经办人-->
		<td class="input">
			<input name="prpLprepayHandlerCode" class="readonly" readonly="true" style="width: 80" value="${prpLprepay.handlerCode}">
			<input name="prpLprepayHandlerName" class="readonly" readonly="true" style="width: 80" title="經辦人" value="${prpLprepay.handlerName}">
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
		<td class="title">
			<s:text name="db.prpLreplevy.statisticsYM" />：
		</td>
		<!--统计年月-->
		<td class="input">
			<input type="text" class="readonly" readonly="true" style="width: 130" name="prpLprepayStatisticsYM" value="${prpLprepay.statisticsYM}">
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
	</tr>
</table>
