<table class="common" align="center">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="RegistPolicyRiskImg" onclick="showPage(this,DAACompensateDeductCond)">
			<s:text name="compensate.setFranchise" />
			<!-- 设置免赔率 -->
			<br>
			<table class="common" style="display: none" id="DAACompensateDeductCond" cellspacing="1" cellpadding="0">
				<tr>
					<td class="subformtitle" colspan="6">
						<s:text name="compensate.responsibilityFran" />
					</td>
					<!-- 责任免赔率 -->
				</tr>
				<tr>
					<td class='left'>
						<s:text name="certainLoss.prpLcheck.indemnityDuty " />
					</td>
					<!-- 事故责任 -->
					<td class='right'>
						<html:select name="prpLcompensateDto" property="indemnityDuty" style="width:60%" onChange="changeIndemnityDuty();">
							<html:options collection="indemnityDutys" property="codeCode" labelProperty="codeCName" />
						</html:select>
					</td>
					<td class='left'>
						<s:text name="db.prpLclaim.indemnityDutyRate" />
					</td>
					<td class='right'>
						<input type="text" name="prpLcompensateIndemnityDutyRate" value="<bean:write name='prpLcompensateDto' property='indemnityDutyRate'/>" onchange="changeIndemnityDuty();">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="subformtitle" colspan="6">
						<s:text name="compensate.AbsFranchise" />
					</td>
					<!-- 绝对免赔率 -->
				</tr>
				<%--          	<tr>--%>
				<%--          		<td class='left'   colspan="6">--%>
				<%--          				            <input type="hidden" name="deductCondition" value="">--%>
				<%--									<input type="hidden" name="deductCondition" value="">--%>
				<%--									<input type="hidden" name="deductConditionTemp" value="">--%>
				<%--									<input type="hidden" name="deductConditionTemp" value="">--%>
				<%--									<input type="hidden" name="Times" value="0">--%>
				<%--									<input type="hidden" name="Times" value="0">--%>
				<%--									<input type="hidden" name="timesFlag" value="">--%>
				<%--									<input type="hidden" name="timesFlag" value="">--%>
				<%--									<input type="hidden" name="deductName" value="">--%>
				<%--									<input type="hidden" name="deductName" value="">--%>
				<%
					String cptDisabled = "";
					String cptChecked = "";
					String cptTimeValue = "0";
					String cptTimeStyle = "width:30;display:none";
					ArrayList cptPrpLdeductCondDtolist = (ArrayList) request.getAttribute("prpLdeductCondDtolist");
					List cptPrpDCodeList = (ArrayList) request.getAttribute("prpDCodeList");
					if (cptPrpDCodeList != null && cptPrpDCodeList.size() != 0) {
						for (int i = 0; i < cptPrpDCodeList.size(); i++) {
							cptTimeValue = "0";
							cptTimeStyle = "width:30;display:none";
							cptDisabled = "";
							cptChecked = "";
							PrpDcodeDto cptPrpDcodeDto = (PrpDcodeDto) cptPrpDCodeList.get(i);
							if (cptPrpLdeductCondDtolist != null && cptPrpLdeductCondDtolist.size() > 0) {

								for (int k = 0; k < cptPrpLdeductCondDtolist.size(); k++) {
									PrpLdeductCondDto cptPrpLdeductCondDto = (PrpLdeductCondDto) prpLdeductCondDtolist.get(k);
									//System.out.println();//System.out.println(prpLdeductCondDto.getDeductCondCode()+"=========="+ prpDcodeDto.getCodeCode());//System.out.println();//System.out.println();
									if (cptPrpLdeductCondDto.getDeductCondCode().equals(cptPrpDcodeDto.getCodeCode())) {
										cptChecked = "checked";
										cptDisabled = "disabled";
										cptTimeValue = Integer.toString(cptPrpLdeductCondDto.getTimes());
										if (cptPrpLdeductCondDto.getTimes() > 0 && ("130".equals(cptPrpLdeductCondDto.getDeductCondCode()) || "190".equals(cptPrpLdeductCondDto.getDeductCondCode())))
											cptTimeStyle = "width:30;display:";
									}

								}
							}
				%>
				<tr>
					<td class='left' colspan="6">
						<input type="checkbox" name="deductCondition" <%=cptDisabled%> <%=cptChecked%> value="<%=cptPrpDcodeDto.getCodeCode()%>" onclick="displayTimes(this);initDeductCond();">
						<%=cptPrpDcodeDto.getCodeCName()%>
						<input class="common" name="Times" style="<%=cptTimeStyle%>" value="<%=cptTimeValue%>" onchange="initDeductCond();">
						<br>
						<input type="hidden" name="deductConditionTemp" value="<%=cptPrpDcodeDto.getCodeCode()%>">
						<input type="hidden" name="timesFlag" value="<%=cptPrpDcodeDto.getFlag()%>">
						<input type="hidden" name="deductName" value="<%=cptPrpDcodeDto.getCodeCName()%>">
					</td>
				</tr>
				<%
					}
					}
				%>
				<%--          		</td>	--%>
				<%--          		--%>
				<%--          		<tr>--%>
				<td class='button' colspan="2" style="align: center">
					<!---<input type="button" class='button' value="确定" onclick="initDeductCond();">---->
				</td>
				<%--		</tr>--%>
				</tr>
			</table>
		</td>
	</tr>
</table>
