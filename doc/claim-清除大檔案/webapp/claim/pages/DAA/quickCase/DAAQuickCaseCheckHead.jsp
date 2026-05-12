<%String advanceCaseStatus = (String) request.getAttribute("advanceCaseStatus");//案件状态
			String displayInputInfo = (String) request.getAttribute("displayInputInfo");//录入信息
			String displayUpload = (String) request.getAttribute("displayUpload");//上传事故数据
			//String displayUploadImage = (String)request.getAttribute("displayUploadImage");//上传影像资料
			String displayGetConfirm = (String) request.getAttribute("displayGetConfirm");//获取确认信息
			String advanceType = (String) request.getAttribute("advanceType"); //垫付赔案类型
			String disabled1 = (String) request.getAttribute("disabled");//只读状态
			String displayGetFromPlatForm = (String) request.getAttribute("displayGetFromPlatForm");
			String isSpecial = (String) request.getAttribute("isSpecial");
			if (isSpecial == null) {
				isSpecial = "0";
			}
			if (disabled1 == null) {
				disabled1 = "";
			}
			if (("".equals(advanceType)) || advanceType == null) {
				advanceType = "3";
			}
			if (displayInputInfo == null) {
				displayInputInfo = "display:none";
			}
			if (displayUpload == null) {
				displayUpload = "display:none";
			}
			//if(displayUploadImage==null)
			//{
			//displayUploadImage="display:none";
			// }
			if (displayGetConfirm == null) {
				displayGetConfirm = "display:none";
			}
			if (advanceCaseStatus == null) {
				advanceCaseStatus = "00";
			}
			if (displayGetFromPlatForm == null) {
				displayGetFromPlatForm = "display:none";
			}%>
<table class=subtable cellpadding="0" id="checkMain_DaTa" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<input type="hidden" name="checkIsSubmit" value="<%=request.getAttribute("checkIsSubmit")%>">
					<input type="hidden" name="comCode" value="<bean:write name='prpLregistDto' property='comCode' filter='true' />">
					<input type="hidden" name="swfLogFlowID" value="<%=request.getParameter("swfLogFlowID")%>">
					<input type="hidden" name="swfLogLogNo" value="<%=request.getParameter("swfLogLogNo")%>">
					<input type="hidden" name="riskCode" value="<bean:write name="quickCaseDto" property="riskCode" filter='true' />">
					<input type="hidden" name="compelRiskCode" value="<bean:write name="quickCaseDto" property="compelRiskCode" filter='true' />">
					<input type="hidden" name="prpLcompensateMakeCom" value="<bean:write name="quickCaseDto" property="makeCom" filter='true' />">
					<input type="hidden" name="prpLcompensateComCode" value="<bean:write name="quickCaseDto" property="comCode" filter='true' />">
					<input type="hidden" name="prpLcompensateHandlerCode" value="<bean:write name="quickCaseDto" property="handlerCode" filter='true' />">
					<input type="hidden" name="prpLcompensateOperatorCode" value="<bean:write name="quickCaseDto" property="operatorCode" filter='true' />">
					<input type="hidden" name="prpLcompensateSumAmount" value="<bean:write name="quickCaseDto" property="sumAmount" filter='true' />">
					<input type="hidden" name="compelPrpLcompensateSumAmount" value="<bean:write name="quickCaseDto" property="compelSumAmount" filter='true' />">
					<input type="hidden" name="certainLossDataAddFlag" value="N">
					<input type="hidden" name="GenerateCompensateFlag" value="0">
					<input type="hidden" name="advanceCaseStatus" value=<%=advanceCaseStatus%>>
					<input type="hidden" name="isSpecial" value=<%=isSpecial%>>
					<input type="hidden" name="quickCasepayFlag" value=<%=request.getAttribute("payFlag")%> />
					<input type="hidden" name="compelQuickCasepayFlag" value=<%=request.getAttribute("compelPayFlag")%> />
					<td class="left">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckPolicyNo" />
					</td>
					<!-- 保 单 号： -->
					<td class="right" colspan="5">
						<input type="text" class="readonly" readonly name="policyNo" style="width: 20%" value="<bean:write name='quickCaseDto' property='policyNo' filter='true' />">
						<input type="text" class="readonly" readonly name="prpLRegistRPolicyNo" style="width: 20%" value="<bean:write name='quickCaseDto' property='registRpolicyNo' filter='true' />">
						<input type=button class="bigbutton" name="policyBackWard" style="width: 90px" value="强制保单信息" onclick="backWardCompelPolicy();">
						<input type=button class="bigbutton" style="width: 90px" name="policyBackWard" value="出险时保单信息" onclick="backWardPolicy();">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" onclick="relate(fm.policyNo.value);return false;">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckRegistNo" />
					</td>
					<!-- 报 案 号： -->
					<td class="right">
						<input type=text name="registNo" class="readonly" readonly style="width: 80%" value="<bean:write name='prpLcheckDto' property='registNo' filter='true' />">
						<img name=btshowRegistInfo type="image" src="/claim/images/bgmore.gif" title="顯示報案訊息" onclick="relateRegist();return false;">
					</td>
					<td class="left">
						<s:text name="quickCase.claimNo" />
					</td>
					<!-- 赔 案 号： -->
					<td class="right">
						<input type="text" name="prpLclaimNo" class="readonly" readonly="true" value="<bean:write name='quickCaseDto' property='claimNo' filter='true' />">
					</td>
					<td class="left">
						<input type="text" name="compelPrpLclaimNo" class="readonly" readonly="true" value="<bean:write name='quickCaseDto' property='registRclaimNo' filter='true' />">
					</td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="compensate.computeBookNum" />：
					</td>
					<!-- 计算书号 -->
					<td class="right" colspan="5">
						<input type=text name="prpLcompensateNo" class="readonly" readonly="true" style="width: 20%" value="<bean:write name='quickCaseDto' property='compensateNo' filter='true' />">
						<input type=text name="compelPrpLcompensateNo" class="readonly" readonly="true" style="width: 20%" value="<bean:write name='quickCaseDto' property='registRcompensateNo' filter='true' />">
						<input type="hidden" name="prpLcompensateTimes" class="readonly" readonly="true" style="width: 20%" value="1">
						<input type="hidden" name="prpLcompensateSumRest" value="<bean:write name='prpLcompensateDto' property='sumRest' />">
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
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
					</td>
					<!-- 已出险次数： -->
					<td class="right">
						<input type="hidden" name="escapeFlag2" value="N">
						<input type="hidden" name="prpLlossDtoIsLossAll" value="N">
						<!--input type=text name="PerilCount" class="readonly" readonly="true" style="width:50%;text-align='center';color:'#9B009B'" value="">
      <input title="點選此處可獲得已出險相關訊息"  type=button ACCESSKEY="." value='...' class="smallbutton" name='button_Peril_Open_Context' onclick="buttonOnClick('perilInfoShow',fm.policyNo,value,fm.prpLclaimNo.value);"-->
						<%@include file="/DAA/regist/DAAExistRegist.jsp"%>
					</td>
					<td class="left">
						<s:text name="db.prpLregist.section" />
					</td>
					<!-- 事故管界 -->
					<td class="right">
						<input type=text name="prpLcheckSection" class="codecode" style="width: 27%" title="事故管界" value="<%=request.getAttribute("section")%>" ondblclick="code_CodeSelect(this,'Section','0,1','Y');"
							onchange="code_CodeChange(this,'Section','0,1','Y');" onkeyup="code_CodeSelect(this,'Section','0,1','Y');">
						<input type=text name="prpLcheckSectionName" class="codecode" style="width: 48%" title="事故管界" value="<%=request.getAttribute("sectionName")%>"
							ondblclick="code_CodeSelect(this,'Section','-1,0','Y','N');" onchange="code_CodeChange(this,'Section','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'Section','-1,0','Y','N');">
					</td>
					<%
						UserDto user = (UserDto) session.getAttribute("user");
					%>
					<td class="left">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDamageAddress" />
					</td>
					<!-- 事故地点: -->
					<td class="right">
						<input name="prpLcheckAcciAddressCode" class="codecode" style="width: 27%" maxlength=3 description="事故地点" value="<bean:write name='prpLcheckDto' property='acciAddressCode'/>"
							ondblclick="code_CodeSelect(this, 'AcciAddress','0,1','Y','Y',fm.prpLcheckSection.value);" onchange="code_CodeChange(this, 'AcciAddress','0,1','Y','Y',fm.prpLcheckSection.value);"
							onkeyup="code_CodeSelect(this, 'AcciAddress','0,1','Y','Y',fm.prpLcheckSection.value);">
						<input name="prpLcheckAcciAddressName" class="codename" style="width: 44%" maxlength=20 description="事故地点" value="<bean:write name='prpLcheckDto' property='acciAddressName'/>"
							ondblclick="code_CodeSelect(this, 'AcciAddress','-1,0','Y','N',fm.prpLcheckSection.value);" onchange="code_CodeChange(this, 'AcciAddress','-1,0','Y','N',fm.prpLcheckSection.value);"
							onkeyup="code_CodeSelect(this, 'AcciAddress','-1,0','Y','N',fm.prpLcheckSection.value);">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
					</td>
					<!-- 出险时间： -->
					<td class="right">
						<input type="text" name="prpLcheckDamageStartDate" maxlength="10" value="<bean:write name='prpLcheckDto' property='damageStartDate' filter='true' />">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageCase" />
					</td>
					<!-- 出险原因： -->
					<td class="right">
						<input name="prpLcheckDamageCode" class="codecode" style="width: 27%" maxlength=3 description="出险原因" value="<bean:write name='prpLcheckDto' property='damageCode' filter='true' />"
							ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskCode.value);" onchange="code_CodeChange(this, 'DamageCode','0,1','Y','Y',fm.riskCode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskCode.value);">
						<input name="prpLcheckDamageName" class="codename" style="width: 60%" maxlength=20 description="出险原因" value="<bean:write name='prpLcheckDto' property='damageName' filter='true' />"
							ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskCode.value);" onchange="code_CodeChange(this, 'DamageCode','-1,0','Y','N',fm.riskCode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskCode.value);">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTypeCase" />
					</td>
					<!-- 事故原因： -->
					<td class="right">
						<input name="prpLcheckDamageTypeCode" class="codecode" style="width: 27%" maxlength=3 description="事故原因" value="<bean:write name='prpLcheckDto' property='damageTypeCode' filter='true' />"
							ondblclick="code_CodeSelect(this, 'DamageTypeCode','0,1','Y');" onchange="code_CodeChange(this, 'DamageTypeCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'DamageTypeCode','0,1','Y');">
						<input name="prpLcheckDamageTypeName" class="codename" style="width: 44%" maxlength=20 description="事故原因" value="<bean:write name='prpLcheckDto' property='damageTypeName' filter='true' />"
							ondblclick="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'DamageTypeCode','-1,0','Y','N');"
							onkeyup="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N');">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageArea" />
					</td>
					<!-- 出险区域： -->
					<td class="right">
						<input name="prpLcheckDamageAreaCode" class="codecode" style="width: 27%" description="出险網域" value="<bean:write name='prpLcheckDto' property='damageAreaCode' filter='true' />"
							ondblclick="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');" onchange="code_CodeChange(this, 'DamageAreaCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');">
						<input name="prpLcheckDamageAreaName" class="codename" style="width: 58%" description="出险網域" value="<bean:write name='prpLcheckDto' property='damageAreaName' filter='true' />"
							ondblclick="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'DamageAreaCode','-1,0','Y','N');"
							onkeyup="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
					</td>
					<!-- 出险地点： -->
					<td class="right" colspan='3'>
						<input type="text" name="prpLcheckDamageAddress" class="input" value="<bean:write name='prpLcheckDto' property='damageAddress' filter='true' />">
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
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckDate" />
					</td>
					<!-- 查勘日期： -->
					<td class="right">
						<input type="text" name="prpLcheckCheckDate" class="input" value="<bean:write name='prpLcheckDto' property='checkDate' filter='true' />">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckSite" />
					</td>
					<!-- 查勘地点： -->
					<td class="right" colspan='3'>
						<input type="text" name="prpLcheckCheckSite" class="input" value="<bean:write name='prpLcheckDto' property='checkSite' filter='true' />">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.lossItemName" />
					</td>
					<!-- 车牌号码： -->
					<td class="right">
						<input type="text" name="lossItemName" class="readonly" readonly="true" value="<bean:write name='prpLcheckDto' property='lossItemName'/>">
						<input type="hidden" name="lossItemCode" class="readonly" readonly="true" value="<%=request.getParameter("lossItemCode")%>">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.firstSiteFlag" />
					</td>
					<!-- 是否第一现场： -->
					<td class="right">
						<html:radio name="prpLcheckDto" property="firstSiteFlag" value="0" />
						<s:text name="certainLoss.thirdCarLoss.no" />
						<!-- 否 -->
						<html:radio name="prpLcheckDto" property="firstSiteFlag" value="1" />
						<s:text name="certainLoss.thirdCarLoss.yes" />
					</td>
					<!-- 是 -->
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckType" />
					</td>
					<!-- 查勘类型： -->
					<td class="right">
						<select name="checkType"><option value="L">
								<s:text name="check.mentHereunde" />
							</option>
							<!-- 查勘 -->
							<option value="D">
								<s:text name="check.generSurvey" />
							</option></select>
					</td>
					<!-- 代查勘 -->
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckChecker1" />
					</td>
					<!-- 查勘人 1： -->
					<td class="right">
						<input name='prpLcheckChecker1' class='input' maxlength=20 description="查勘人1" value="<bean:write name='prpLcheckDto' property='checker1' filter='true' />">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckChecker2" />
					</td>
					<!-- 查勘人 2： -->
					<td class="right">
						<input name='prpLcheckChecker2' style="width: 74%" class='input' maxlength=20 description="查勘人2" value="<bean:write name='prpLcheckDto' property='checker2' filter='true' />">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpDagent.linkerName" />
					</td>
					<!-- 联系人 -->
					<td class="right">
						<input type=text name="prpLregistLinkerName" title="聯系人" class="ReadOnly" ReadOnly value="<bean:write name='prpLregistDto' property='linkerName' filter='true' />" />
					</td>
					<td class="left">
						<s:text name="db.prpLregist.phoneNumber" />
					</td>
					<!-- 联系电话 -->
					<td class="right">
						<input type=text name="prpLregistPhoneNumber" title="聯系電話" class="ReadOnly" ReadOnly value="<bean:write name='prpLregistDto' property='phoneNumber' filter='true' />">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLregistInsuredAddress" />
					</td>
					<!-- 联系人地址： -->
					<td class="right">
						<input type=text name="prpLregistInsuredAddress" class="ReadOnly" ReadOnly value="<bean:write name='prpLregistDto' property='insuredAddress' filter='true' />">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLregistDamageAreaPostCode" />
					</td>
					<!-- 联系人邮编： -->
					<td class="right">
						<input type=text name="prpLregistDamageAreaPostCode" class="ReadOnly" ReadOnly maxlength=6 description="出险地邮政编码"
							value="<bean:write name='prpLregistDto' property='damageAreaPostCode' filter='true' />">
					</td>
					<logic:equal name="advance" value="1">
						<td class="left">
							<s:text name="check.payClaimType" />
						</td>
						<!-- 垫付赔案类型 -->
						<td class="right">
							<select name="prplregistAdvance" onchange="changeAdvanceStatus(this);">
								<option value="1" <%=advanceType.trim().equals("1") ? "selected" : ""%>>
									<s:text name="check.payResponsib" />
								</option>
								<!-- 全责垫付 -->
								<option value="2" <%=advanceType.trim().equals("2") ? "selected" : ""%>>
									<s:text name="check.noResponsib" />
								</option>
								<!-- 无责垫付 -->
								<option value="3" <%=advanceType.trim().equals("3") ? "selected" : ""%>>
									<s:text name="check.other" />
								</option>
								<!-- 其它 -->
							</select>
						</td>
						<td class="left">
							<input type="text" name="displayInputInfo" style=<%=displayInputInfo%> style="color:'#9B009B'" class="readonly" value="輸入信息" onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';"
								onMouseOut="this.style.color='#9B009B';" onclick="inputNullInfo();">
							<input type="text" name="displayGetFromPlatForm" style=<%=displayGetFromPlatForm%> style="color:'#9B009B'" class="readonly" value="获取平台信息"
								onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';" onMouseOut="this.style.color='#9B009B';" onclick="getInfoFromPlatForm();">
						</td>
						<td class="right">
							<input type="text" name="displayUpload" style="<%=displayUpload%>" style="color:'#9B009B'" class="readonly" value="上传事故信息" onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';"
								onMouseOut="this.style.color='#9B009B';" onclick="uploadToPlatForm('D5');">
							<%--input type="text" name="displayUploadImage" style="<%=displayUploadImage %>" style="color:'#9B009B'" class="readonly"  value="上传影像信息" onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';" onMouseOut="this.style.color='#9B009B';" onclick="uploadToPlatForm('DA');"--%>
							<input type="text" name="displayGetConfirm" <%=disabled1%> style="<%=displayGetConfirm%>" style="color:'#9B009B'" class="readonly" value="获取确认信息"
								onMouseOver="this.style.color='#FF0000';this.style.cursor='hand';" onMouseOut="this.style.color='#9B009B';" onclick="getNullConfirm();">
						</td>
					</logic:equal>
					<logic:notEqual name="advance" value="1">
						<td class="left"></td>
						<td class="right"></td>
						<td class="left"></td>
						<td class="right"></td>
					</logic:notEqual>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckRemark" />
					</td>
					<!-- 备  注： -->
					<td class="right" colspan='5'>
						<input name='prpLcheckRemark' class='input' maxlength=80 style="width: 94%" value="<bean:write name='prpLcheckDto' property='remark' filter='true' />">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>