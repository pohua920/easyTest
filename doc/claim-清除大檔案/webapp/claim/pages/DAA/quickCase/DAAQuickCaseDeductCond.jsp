<table class="common" id="DeductCond_DaTa" align="center">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="RegistPolicyRiskImg" onclick="showPage(this,CompensateDeductCond)">
			<s:text name="compensate.setFranchise" />
			<!-- 设置免赔率 -->
			<br>
			<table class="common" style="display: none" id="CompensateDeductCond" cellspacing="1" cellpadding="0">
				<tr>
					<td class="subformtitle" colspan="6">
						<s:text name="compensate.responsibilityFran" />
					</td>
					<!-- 责任免赔率 -->
				</tr>
				<tr>
					<td class='left'>
						<s:text name="certainLoss.thirdCarLoss.indemnityDuty" />
					</td>
					<!-- 事故责任 -->
					<td class='right'>
						<html:select name="prpLcheckDto" property="indemnityDuty" style="width:60%" onChange="changeIndemnityDuty();">
							<html:options collection="indemnityDutys" property="codeCode" labelProperty="codeCName" />
						</html:select>
					</td>
					<td class='left'>
						<s:text name="db.prpLclaim.indemnityDutyRate" />
					</td>
					<td class='right'>
						<input type="text" name="prpLcompensateIndemnityDutyRate" value="<bean:write name='prpLclaimDto' property='indemnityDutyRate'/>" onchange="changeIndemnityDuty();">
						%
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
				<%
					String disabled = "";
					String checked = "";
					String timeValue = "0";
					String timeStyle = "width:30;display:none";
					ArrayList prpLdeductCondDtolist = (ArrayList) request.getAttribute("prpLdeductCondDtolist");
					List prpDCodeList = (ArrayList) request.getAttribute("prpDCodeList");
					if (prpDCodeList != null && prpDCodeList.size() != 0) {
						for (int i = 0; i < prpDCodeList.size(); i++) {
							timeValue = "0";
							timeStyle = "width:30;display:none";
							disabled = "";
							checked = "";
							PrpDcodeDto prpDcodeDto = (PrpDcodeDto) prpDCodeList.get(i);
							String codecode = prpDcodeDto.getCodeCode();
							if (!"291".equals(codecode) && !"292".equals(codecode) && !"293".equals(codecode) && !"294".equals(codecode)) {
								if (prpLdeductCondDtolist != null && prpLdeductCondDtolist.size() > 0) {
									for (int k = 0; k < prpLdeductCondDtolist.size(); k++) {
										PrpLdeductCondDto prpLdeductCondDto = (PrpLdeductCondDto) prpLdeductCondDtolist.get(k);
										//System.out.println();//System.out.println(prpLdeductCondDto.getDeductCondCode()+"=========="+ prpDcodeDto.getCodeCode());//System.out.println();//System.out.println();
										if (prpLdeductCondDto.getDeductCondCode().equals(prpDcodeDto.getCodeCode())) {
											checked = "checked";
											timeValue = Integer.toString(prpLdeductCondDto.getTimes());
											if (prpLdeductCondDto.getTimes() > 0 && ("130".equals(prpLdeductCondDto.getDeductCondCode()) || "190".equals(prpLdeductCondDto.getDeductCondCode())))
												timeStyle = "width:30;display:";
										}
									}
								}
				%>
				<tr>
					<td class='left' colspan="3">
						<input type="checkbox" name="deductCondition" <%=disabled%> <%=checked%> value="<%=prpDcodeDto.getCodeCode()%>" onclick="displayTimes(this);initDeductCond();">
						<%=prpDcodeDto.getCodeCName()%>
						<input class="common" name="Times" style="<%=timeStyle%>" value="<%=timeValue%>" onchange="initDeductCond();">
						<br>
						<input type="hidden" name="deductConditionTemp" value="<%=prpDcodeDto.getCodeCode()%>">
						<input type="hidden" name="timesFlag" value="<%=prpDcodeDto.getFlag()%>">
						<input type="hidden" name="deductName" value="<%=prpDcodeDto.getCodeCName()%>">
					</td>
					<%
						}

								if ((prpDCodeList.size() - (i + 1)) > 0) {
									i = i + 1;
									PrpDcodeDto prpDcodeDto1 = (PrpDcodeDto) prpDCodeList.get(i);
									String codecode1 = prpDcodeDto1.getCodeCode();
									String checked1 = "";
									String timeValue1 = "0";
									String timeStyle1 = "width:30;display:none";
									if (!"291".equals(codecode1) && !"292".equals(codecode1) && !"293".equals(codecode1) && !"294".equals(codecode1)) {
										if (prpLdeductCondDtolist != null && prpLdeductCondDtolist.size() > 0) {
											for (int k = 0; k < prpLdeductCondDtolist.size(); k++) {
												PrpLdeductCondDto prpLdeductCondDto1 = (PrpLdeductCondDto) prpLdeductCondDtolist.get(k);
												if (prpLdeductCondDto1.getDeductCondCode().equals(prpDcodeDto1.getCodeCode())) {
													checked1 = "checked";
													timeValue1 = Integer.toString(prpLdeductCondDto1.getTimes());
													if (prpLdeductCondDto1.getTimes() > 0 && ("130".equals(prpLdeductCondDto1.getDeductCondCode()) || "190".equals(prpLdeductCondDto1.getDeductCondCode())))
														timeStyle1 = "width:30;display:";
												}
											}
										}
					%>
					<td class='left' colspan="3">
						<input type="checkbox" name="deductCondition" <%=disabled%> <%=checked1%> value="<%=prpDcodeDto1.getCodeCode()%>" onclick="displayTimes(this);initDeductCond();">
						<%=prpDcodeDto1.getCodeCName()%>
						<!-- modify by wangliguang 20080526 begin -->
						<!--  reason: 应该对新组织的timeValue1进行显示 -->
						<input class="common" name="Times" style="<%=timeStyle1%>" value="<%=timeValue1%>" onchange="initDeductCond();">
						<br>
						<!-- modify by wangliguang 20080526 end-->
						<input type="hidden" name="deductConditionTemp" value="<%=prpDcodeDto1.getCodeCode()%>">
						<input type="hidden" name="timesFlag" value="<%=prpDcodeDto1.getFlag()%>">
						<input type="hidden" name="deductName" value="<%=prpDcodeDto1.getCodeCName()%>">
					</td>
					<%
						}
								}
					%>
				</tr>
				<%
					}
					}
				%>
				<td class='button' colspan="2" style="align: center"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>