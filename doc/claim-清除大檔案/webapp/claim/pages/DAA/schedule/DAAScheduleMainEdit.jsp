<%@ include file="/common/taglibs.jsp"%>
<%--
	String checkedCommiFlag = ""; //双代标识是否已选择
	String disabledCommiFlag = "disabled"; //双代标识是否为只读的
--%>
<script language="javascript">
	
<%--案件双代标志--%>
	function checkCommiFlag() {
		if (fm.CheckBoxCommiFlag.checked = "true") {
			if (!confirm('选择双代案件後,将全部清空下面查勘定损的调度内容,您确定要对此案件进行双代处理吗？')) {
				return false;
			}
		}
	}
</script>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
	<tr>
		<input type="hidden" name="swfLogFlowID" class="common" value="${param.swfLogFlowID}">
		<input type="hidden" name="swfLogLogNo" class="common" value="${param.swfLogLogNo}">
		<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
		<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
		<input type="hidden" name="prpLscheduleMainWFScheduleType" class="common" value="${prpLscheduleMainWF.scheduleType}">
		<input type="hidden" name="clauseType" value="${prpLregist.clauseType}">
		<input type="hidden" name="provinceCode" value="${provinceCode}">
		<input type="hidden" name="selectcomcode" value="">
		<input type="hidden" name="editType" value="${param.swfLogLogNo}">
		<input type="hidden" name="isChecked" value="${param.swfLogLogNo}">
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<c:set var="strtitleTemp" value="" />
					<c:if test="${saveType1 eq 'GETBACKEDIT' }">
						<c:set var="strtitleTemp" value="改派" />
					</c:if>
					<td>
						<input type="hidden" value="调度<c:out value="${strtitleTemp}"/>处理" />
					</td>
				</tr>
				<input type="hidden" class="red1" readonly name="prplRegistFlag" value="">
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.registNo" />：
					</td>
					<%--备案号码--%>
					<td class="right">
						<input type=text name="prpLscheduleMainWFRegistNo" style="width: 100%" title="備案號碼" class="readonly" readonly="true" value="${prpLscheduleMainWF.id.registNo}">
						<input type="hidden" name="prpLscheduleMainWFScheduleID" title="分案號碼" class="readonly" readonly="true" value="${prpLscheduleMainWF.id.scheduleID}">
						<input type="hidden" name="prpLscheduleMainWFRiskCode" value="${param.riskCode}">
					</td>
					<td class="left">
						<%-- 保单号 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.registNo" />
					</td>
					<td class="right">
						<input type=text name="prpLscheduleMainWFPolicyNo" style="width: 100%" title="保單號碼" class="readonly" readonly="true" value="${prpLscheduleMainWF.policyNo}">
						<!-- 调度平台可查看当前最新保单及报案信息 -->
					</td>
					<td class="left">
						<input alt="點選此按钮可获得保单相关信息" type="image" name="btRelate" src="/claim/images/butRelate.gif" onclick="relate(fm.prpLscheduleMainWFPolicyNo.value);return false;">
					</td>
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
						<%-- 联系人姓名 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFLinkerName" />
					</td>
					<td class="right">
						<input class="readonly" readonly name="prpLscheduleMainWFLinkerName" value="${prpLscheduleMainWF.linkerName}">
					</td>
					<td class="left">
						<%-- 联系电话 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFPhoneNumber" />
					</td>
					<td class="right">
						<input class="readonly" readonly name="prpLscheduleMainWFPhoneNumber" value="${prpLscheduleMainWF.phoneNumber}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<%-- 代理人代码 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.prpLregistAgentCode" />
					</td>
					<td class="right">
						<input type=text name="prpLregistAgentCode" title="代理人代碼" class="readonly" readonly="true" value="${prpLscheduleMainWF.agentCode}">
					</td>
					<td class="left">
						<%-- 代理人名称 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.prpLregistAgentName" />
					</td>
					<td class="right">
						<input type=text name="prpLregistAgentName" title="代理人名稱" class="readonly" readonly="true" value="${prpLscheduleMainWF.agentName}">
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
						<%-- 理赔处理机构 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFClaimComCode" />
					</td>
					<td class="right">
						<input name="prpLscheduleMainWFClaimComCode" style="width: 50%" class="readonly" readonly value="${prpLscheduleMainWF.claimComCode}">
						<input name="prpLscheduleMainWFComName" style="width: 56%" class="readonly" readonly value="${prpLscheduleMainWF.claimComName}">
					</td>
					<td class="left">
						<%-- 调度员 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" />
					</td>
					<td class="right">
						<input class="readonly" readonly name="prpLscheduleMainWFOperatorCode" type=hidden value="${prpLscheduleMainWF.operatorCode}">
						<input class="readonly" readonly name="prpLscheduleMainWFOperatorName" value="${prpLscheduleMainWF.operatorName}">
					</td>
					<td class="left">
						<%-- 报损金额CNY --%>
						<s:text name="certainLoss.prpLscheduleMainWF.prpLregistEstimateLoss" />
					</td>
					<td class="right">
						<input class="readonly" readonly name="prpLregistEstimateLoss" value="<fmt:formatNumber pattern='#' value="${prpLscheduleMainWF.estimateLoss}"/>">
						<input type="hidden" class="readonly" readonly name="prpLcheckloss" value="<fmt:formatNumber pattern='#' value="${prpLscheduleMainWF.estimateFee}"/>">
					</td>
				</tr>
				<c:if test="${requestScope.prpLregistRPolicyNo !=null}">
					<tr>
						<td class="left">
							<%-- 强制保单号码 --%>
							<s:text name="certainLoss.prpLscheduleMainWF.prpLRegistRPolicyNo" />
						</td>
						<td class="right">
							<input type="text" name="prpLRegistRPolicyNo" class="readonly" readonly="true" value="${prpLregistRPolicyNo.id.policyNo}">
						</td>
						<td class="left"></td>
						<td class="right"></td>
						<td class="left"></td>
						<td class="right"></td>
					</tr>
				</c:if>
				<tr>
					<td class="left">
						<%-- 调度时间 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.attemperDate" />
					</td>
					<td class="right">
						<%-- <input class="readonly" readonly name="prpLscheduleMainWFInputDate" style="width:40%" value="${prpLscheduleMainWF.inputDate}" >日--%>
						<rc:rcDate name="prpLscheduleMainWFInputDate" class="readonly" readonly="true" wdatePicker="false" style="width:50%" value="${prpLscheduleMainWF.inputDate}" />
						<input class="readonly" readonly name="prpLscheduleInputHour" style="width: 10%" value="${prpLscheduleMainWF.inputHour}">
						時
						<input class="readonly" readonly name="prpLscheduleInputMinute" style="width: 10%" value="${prpLscheduleMainWF.inputMinute}" style="display:none">
						<!-- 分 -->
					</td>
					<td class="left">
						<%-- 调度次数 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.attemperTimes" />
					</td>
					<td class="right">
						<!--原因：次数大於1时将次数颜色变红色-->
						<c:if test="${prpLscheduleMainWF.surveyNo > 1 }">
							<font color=red> ${prpLscheduleMainWF.surveyNo} </font>
						</c:if>
						<c:if test="${prpLscheduleMainWF.surveyNo <= 1 }">
				        ${prpLscheduleMainWF.surveyNo}
				        </c:if>
						<input type=hidden hclass="readonly" readonly name="prpLscheduleMainWFSurveyNo" value="${prpLscheduleMainWF.surveyNo}">
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
					<td class="right">
						<%-- 出险情况 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFRegistText" />
					</td>
					<td class="right" colspan="5"></td>
				</tr>
				<tr>
					<td class="input" align="center" colspan="6">
						<textarea style="wrap: hard" rows="10" cols="60" name="prpLscheduleMainWFRegistText">${prpLscheduleMainWF.registText}</textarea>
					</td>
				</tr>
				<input type="hidden" name="prpLscheduleMainWFDtoCommiFlag" value="${prpLscheduleMainWF.commiFlag}">
				<input type="hidden" name="prpLregistDamageStartDate" value="${prpLregist.damageStartDate}">
				<input type="hidden" name="prpLregistDamageStartHour" value="${prpLregist.damageStartHour}">
				<input type="hidden" name="prpLregistDamageStartMinute" value="${prpLregist.damageStartMinute}">
				<input type="hidden" name="prpLregistReportDate" value="${prpLregist.reportDate}">
				<input type="hidden" name="prpLregistReportHour" value="${prpLregist.reportHour}">
				<input type="hidden" name="prpLregistReportMinute" value="${prpLregist.reportMinute}">
			</table>
		</td>
	</tr>
</table>
<br>
