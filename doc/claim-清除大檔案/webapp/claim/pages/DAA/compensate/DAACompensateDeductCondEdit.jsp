<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" style="display: none">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx}/images/butCollapseBlue.gif" name="RegistPolicyRiskImg" onclick="showPage(this,CompensateDeductCond)"> <b><s:text name="compensate.setFranchise" /></b>
			<%-- 设置免赔率 --%>
			<br>
			<table class="common" style="display: none" id="CompensateDeductCond" cellspacing="1" cellpadding="0">
				<tr>
					<td class="subformtitle" colspan="6">
						<s:text name="compensate.responsibilityFran" />
					</td>
				</tr>
				<%-- 责任免赔率 --%>
				<tr>
					<td class='left'>
						<s:text name="certainLoss.thirdCarLoss.indemnityDuty" />
					</td>
					<%-- 事故责任 --%>
					<td class='right'>
						<select name="indemnityDuty" style="width: 60%" onChange="changeIndemnityDuty();">
							<c:forEach items="${requestScope.indemnityDutys}" var="prpDcode">
								<option value="<c:out value='${pageScope.prpDcode.id.codeCode}' />" <c:if test="${requestScope.prpLcompensate.indemnityDuty==pageScope.prpDcode.id.codeCode}">selected="selected"</c:if>>
									<c:out value="${pageScope.prpDcode.codeCName}" />
								</option>
							</c:forEach>
						</select>
					</td>
					<td class='left'>
						<s:text name="db.prpLclaim.indemnityDutyRate" />
					</td>
					<td class='right'>
						<input type="text" name="prpLcompensateIndemnityDutyRate" value="${prpLcompensate.indemnityDutyRate}" onchange="changeIndemnityDuty();">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="subformtitle" colspan="6">
						<s:text name="compensate.AbsFranchise" />
					</td>
					<%-- 绝对免赔率 --%>
				</tr>
				<c:if test="${not empty requestScope.prpDCodeList}">
					<c:forEach items="${requestScope.prpDCodeList}" var="prpDCode">
						<c:set var="timeValue" value="0" />
						<c:set var="timeStyle" value="width:30;display:none" />
						<c:set var="disabled" value="" />
						<c:set var="checked" value="" />
						<c:if test="${not empty requestScope.prpLdeductCondlist}">
							<c:forEach items="${requestScope.prpLdeductCondlist}" var="prpLdeductCond">
								<c:if test="${prpLdeductCond.id.deductCondCode==prpDCode.id.codeCode}">
									<c:set var="checked" value="checked" />
									<c:set var="timeValue" value="${prpLdeductCond.times}" />
									<c:if test="${prpLdeductCond.times>0 && ('130'==prpLdeductCond.id.deductCondCode||'190'==prpLdeductCond.id.deductCondCode)}">
										<c:set var="timeStyle" value="width:30;display:block" />
									</c:if>
								</c:if>
							</c:forEach>
						</c:if>
						<tr>
							<td class='left' colspan="6">
								<input type="checkbox" name="deductCondition" <c:out value="${disabled}"/> <c:out value="${checked}"/> value="${prpDCode.id.codeCode}" onclick="displayTimes(this);initDeductCond();">
								<c:out value="${prpDCode.codeCName}" />
								<input class="common" name="Times" style="${timeStyle}" value="${timeValue}" onchange="initDeductCond();">
								<br>
								<input type="hidden" name="deductConditionTemp" value="<c:out value='${prpDCode.id.codeCode}' />">
								<input type="hidden" name="timesFlag" value="<c:out value='${prpDCode.flag}' />">
								<input type="hidden" name="deductName" value="<c:out value='${prpDCode.codeCName}' />">
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<td class='button' colspan="2" style="align: center"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>