<%--
****************************************************************************
* DESC       :添加主信息子块界面页面
* AUTHOR     :中科软
* MODIFYLIST :Name       Date            Reason/Contents
****************************************************************************
--%>
<script language="javascript">
	function selectHandleCodeByUnitType(field, eventType, coordinate, queryType){
		var unitType = fm.unitType.value;
		if (eventType == "dbclick" || eventType == "keyup") {
			if (unitType == 1) {
				code_CodeSelect(field, 'prpdcompany', coordinate, 'Y',
						queryType);
			}else {
				code_CodeSelect(field, 'prpdCustomerUnit', coordinate, 'Y',
						queryType);
			}
		} else if (eventType == "change") {
			if(unitType == 1) {
				code_CodeChange(field, 'prpdcompany', coordinate, 'Y',
						queryType);
			} else {
				code_CodeChange(field, 'prpdCustomerUnit', coordinate, 'Y',
						queryType);
			}
		}
	}
	function clearHandleUnitCode() {
		fm.prpLcheckHandleUnitCode.value = "";
		fm.prpLcheckHandleUnitName.value = "";
		if (fm.unitType.value == "1") {
			fm.freightHeresyCheck.style.display = "none";
		} else {
			fm.freightHeresyCheck.style.display = "";
		}
	}
</script>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="db.prpLcharge.policyNo" />
						<%-- 保单号 --%>
						<input type="hidden" name="referKind">
						<input type="hidden" name="prpLcheckRiskCode"
							value="${prpLcheck.riskCode }">
						<input type="hidden" name="riskCode"
							value="${prpLcheck.riskCode }">
						<input type="hidden" name="prpLcheckFlag"
							value="${prpLcheck.flag }">
						<input type="hidden" name="prpLcheckReferSerialNo"
							value="${prpLcheck.id.referSerialNo}"/>
						<input type="hidden" name="prpLcheckInsureCarFlag" value="">
						<input type="hidden" name="swfLogFlowID" class="common"
							value="${param.swfLogFlowID}">
						<input type="hidden" name="swfLogLogNo" class="common"
							value="${param.swfLogLogNo}">
						<input type="hidden" name="policyno"
							value="${prpLcheck.policyNo }">
						<input type="hidden" name="registno"
							value="${prpLcheck.id.registNo }">
						<input type="hidden" name="coreURL"
							value="${core_URL }">
						<input type=hidden name="prpLregistComCode" title="归属机构"
							class="ReadOnly" ReadOnly style="width: 120px"
							value="${prpLregist.comCode}"/>
						<input type="hidden" name="language" title="语种"
							value="${prpLregist.language }"/>
					</td>
					<td class="right">
						<input type="text" name="prpLcheckPolicyNo" class="readonly"
							readonly="true"
							value="${prpLcheck.policyNo }">
					</td>
					<td class="left" colspan="4">
						<input type="image" name="btRelate"
							src="/claim/images/butRelate.gif" border="0"
							onclick="relate(fm.prpLcheckPolicyNo.value);return false;">
						<input type="hidden" name="damageDate"
							value="${prpLcheck.damageStartDate}"/>
						<input type=button class="bigbutton" name="policyBackWard"
							value="<s:text name='button.dangerPolicyInfo.value'/>"
							onclick="backWardPolicy(fm.coreURL.value,fm.prpLcheckPolicyNo.value,fm.prpLcheckRiskCode.value,fm.damageDate.value,fm.prpLregistComCode.value);" />
						<%-- 出险时保单信息 --%>
					</td>
				</tr>
				<tr>
					<td class="left">
						<!-- 报案号 --> 
						<s:text name="prpLbpmMain.mainNo" />
					</td>
					<td class="right">
						<input type=text name="prpLcheckRegistNo" class="readonly" readonly="true"
							value="${prpLcheck.id.registNo }">
					</td>
					<td class="left">
						<s:text name="check.claimNum" />
						<%-- 赔案号 --%>
					</td>
					<td class="right">
						<input type="text" name="prpLcheckClaimNo" class="readonly"
							readonly="true"
							value="${prpLcheck.claimNo }" />
					</td>
					<td class="left">
						<s:text name="query.xianzhongName" />
						<%-- 险种名称 --%>
					</td>
					<td class="right">
						${riskCName}
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
						<s:text name="regist.prpLregist.damageTime" />
						<%-- 出险时间 --%>
					</td>
					<td class="right" colspan="2">
						<rc:rcDate name="prpLcheckDamageStartDate" class="readonly" readonly="true" wdatePicker="false" value="${prpLcheck.damageStartDate} 日 ${prpLcheck.damageStartHour} 時 ${prpLcheck.damageStartMinute} 分" />
					</td>
					<td class="right" colspan="3"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.damageAddress" />
						<%-- 出险地点 --%>
					</td>
					<td class="right" colspan="5">
						<select name="countryFlag" style="width: 100px"
							onchange="countryFlag_change(this.options[this.selectedIndex].value)">
							<option value="0">
								<s:text name="check.domestic" />
							</option>
							<%--  国内--%>
							<option value="1">
								<s:text name="check.abroad" />
							</option>
							<%-- 国外 --%>
						</select>
						<input type=text class="codecode" name="foreignCountryCode"
							style="display: none" />
						<input type=text class="codecode" name="foreignCountryName"
							style="display: none" title="选择国家名" style="width:120px"
							ondblclick="code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"
							onkeyup="code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"
							onchange="code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();" />
						<input type=text class="codecode" name="portCode"
							style="display: none" />
						<input type=text class="codecode" name="portCName" title="选择港口名"
							style="width: 120px"
							ondblclick="code_CodeSelect(this, 'portCode','-1,0','Y','N');"
							onkeyup="code_CodeSelect(this, 'portCode','-1,0','Y','N');"
							onchange="code_CodeSelect(this, 'portCode','-1,0','Y','N');" />
						<input type="text" name="prpLcheckDamageAddress" class="input"
							style="width: 350px"
							value="${prpLcheck.damageAddress}"
							onclick="showPort(this);" />
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="check.accidentCauses" />
						<%--出险原因  --%>
					</td>
					<td class="right">
						<select name="theMain">
							<option value="9000">
								<s:text name="check.all" />
							</option>
							<%-- 所有 --%>
							<option value="9500">
								<s:text name="check.naturalCategory" />
							</option>
							<%-- 自然灾害类 --%>
							<option value="9600">
								<s:text name="check.accidenTypes" />
							</option>
							<%-- 意外事故类 --%>
							<option value="9700">
								<s:text name="check.otherClasses" />
							</option>
							<%--  其它类 --%>
						</select>
					</td>
					<td class="left">
						<input name="prpLcheckDamageCode" class="codecode" maxlength=3
							description="出险原因"
							value="${prpLcheck.damageCode}"
							ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskCode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskCode.value);">
					</td>
					<td class="right">
						<input name="prpLcheckDamageName" class="codename" maxlength=20
							description="出险原因"
							value="${prpLcheck.damageName}"
							ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','Y',fm.riskCode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','Y',fm.riskCode.value);"><img src="/claim/images/bgMarkMustInput.jpg" />
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
						<s:text name="certainLoss.prpLcheck.checkType" />
						<%-- 查勘类型 --%>
					</td>
					<td class="right">
						<s:select list="#request.checkTypeList" name="checkType" listKey="key" listValue="value" value="#request.prpLcheck.checkType" />
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckDate" />
						<%-- 查勘日期 --%>
					</td>
					<td class="right">
						<rc:rcDate name="prpLcheckCheckDate" class="input" value="${prpLcheck.checkDate}" /><img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckSite" />
						<%-- 查勘地点 --%>
					</td>
					<td class="right" colspan="5">
						<input type="text" name="prpLcheckCheckSite" class="input"
							value="${prpLcheck.checkSite}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckChecker1" />
						<%-- 查勘人 1 --%>
					</td>
					<td class="right">
						<input name='prpLcheckChecker1' class='input' maxlength=20
							description="查勘人1"
							value="${prpLcheck.checker1}"><img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckChecker2" />
						<%-- 查勘人 2 --%>
					</td>
					<td class="right">
						<input name='prpLcheckChecker2' class='input' maxlength=20
							description="查勘人2"
							value="${prpLcheck.checker2}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckCheckCom" />
					</td>
					<%-- 查勘处理单位 --%>
					<td class="right">
						<select name='unitType' onchange="clearHandleUnitCode();">
							<option value="1">
								<s:text name="check.inSystem" />
							</option>
							<%-- 系统内 --%>
							<option value="0">
								<s:text name="check.outSystem" />
							</option>
							<%-- 系统外 --%>
						</select>
					</td>
					<td class="left">
						<c:if test="${not empty prpLcheck.handleUnitCode }">
							<input name="prpLcheckHandleUnitCode" class="codecode"
								maxlength=20 description="查勘处理单位代码"
								value="${prpLcheck.handleUnitCode}"
								ondblclick="selectHandleCodeByUnitType(this,'dbclick','0,1','Y');"
								onkeyup="selectHandleCodeByUnitType(this,'keyup','0,1','Y');">
						</c:if>
						<c:if test="${empty prpLcheck.handleUnitCode }">
							<input name="prpLcheckHandleUnitCode" class="codecode"
								maxlength=20 description="查勘处理单位代码" value="${session.user.comCode }"
								ondblclick="selectHandleCodeByUnitType(this,'dbclick','0,1','Y');"
								onkeyup="selectHandleCodeByUnitType(this,'keyup','0,1','Y');">
						</c:if>
					</td>
					<td class="right">
						<c:if test="${not empty prpLcheck.handleUnitCode }">
							<input name="prpLcheckHandleUnitName" class="readonly"
								maxlength=60 description="查勘处理单位"
								value="${prpLcheck.handleUnit}"
								readonly>
						</c:if>
						<c:if test="${empty prpLcheck.handleUnitCode}">
							<input name="prpLcheckHandleUnitName" class="readonly"
								maxlength=60 description="查勘处理单位" value="${session.user.comName }" readonly>
						</c:if>
					</td>
					<td class="left">
						<c:if test="${prpLcheck.unitType != '0' }">
							<input type=button class='button'
								value="<s:text name='button.attorney.value'/>"
								name="freightHeresyCheck" style="display: none"
								onclick="heresyCheck()" />
						</c:if>
						<c:if test="${prpLcheck.unitType == '0' }">
							<input type=button class='button'
								value="<s:text name='button.attorney.value'/>"
								name="freightHeresyCheck" onclick="heresyCheck()" />
							<%-- 委托书 --%>
						</c:if>
					</td>
					<td class="right">
						<input type=hidden name='prpLcheckCheckUnitName' style="width: 93%"
							maxlength=30 description="查勘处理单位"
							value="${prpLcheck.checkUnitName}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLrepairFee.remark" />
						<%-- 备注 --%>
					</td>
					<td class="right" colspan="5">
						<textarea style="width: 600px; overflow-x: visible;"
							name='prpLcheckRemark' rows=4 cols=40 title="备注">${prpLcheck.remark}</textarea>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
