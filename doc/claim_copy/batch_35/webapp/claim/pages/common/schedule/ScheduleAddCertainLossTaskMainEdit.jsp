<%@ page contentType="text/html; charset=GBK"%>
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
	<tr>
		<td class="formtitle" colspan="4">
			<s:text name="schedule.managementFeeScheduleTarget" />
		</td>
	</tr>
	<%--定损调度标的管理 --%>
	<tr>
		<td class="title" style="width: 16%">
			<s:text name="db.prpLclaim.registNo" />
			:
		</td>
		<%--备案号码--%>
		<td class="input" style="width: 34%">
			<input type=text name=prpLscheduleMainWFRegistNo title="備案號碼" maxlength="22" class="readonly" readonly="true" value="${prpLregist.registNo}">
			<input type=hidden name=prpLscheduleMainWFScheduleID value="1">
			<input type=hidden name=scheduleType value="sched">
			<input type=hidden name=status value="2">
		</td>
		<td class="title" style="width: 14%; valign: bottom">
			<s:text name="prompt.queRegist.PolicyNo" />
			<%--保单号 --%>
			:
		</td>
		<td class="input" style="width: 36%; valign: middle">
			<input type=text title="保單號碼" class="readonly" readonly="true" style="width: 140px" value="${prpLregist.policyNo}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFLinkerName" />
			<%--联系人姓名 --%>
		</td>
		<td class="input">
			<input class="readonly" readonly value="${prpLregist.linkerName}">
		</td>
		<td class="title">
			<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFPhoneNumber" />
			<%--联系电话 --%>
		</td>
		<td class="input">
			<input class="readonly" readonly value="${prpLregist.phoneNumber}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.insuredName" />
			<%--被保险人 --%>
		</td>
		<td class="input" colspan=3>
			<input class="readonly" readonly style="width: 60px" value="${prpLregist.insuredName}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.damageAddress" />
			<%--出险地点 --%>
			:
		</td>
		<td class="input" colspan=3>
			<input name="prplregistDamageAddress" class="readonly" readonly style="width: 60px" value="${prpLregist.damageAddress}">
		</td>
	</tr>
</table>
</table>