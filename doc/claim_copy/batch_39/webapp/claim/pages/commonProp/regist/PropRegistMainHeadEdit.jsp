<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： weishixin
* CREATEDATE ： 2004-02-29
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<s:if test="#request.prpLregist.startHour==0">
	<s:set var="startHour" value="%{getText('modifySumClaim.comeEffect')}" scope="page"></s:set>
	<%-- 零时起至 --%>
</s:if>
<s:elseif test="#request.prpLregist.startHour==12">
	<s:set var="startHour" value="%{getText('regist.from')}" scope="page"></s:set>
	<%--十二时起至 --%>
</s:elseif>
<s:elseif test="#request.prpLregist.startHour==24">
	<s:set var="startHour" value="%{getText('regist.start')}" scope="page"></s:set>
	<%-- 二十四时起 --%>
</s:elseif>
<s:else>
	<s:set var="startHour" value="" scope="page"></s:set>
</s:else>
<s:if test="#request.prpLregist.endHour==0">
	<s:set var="endHour" value="%{getText('regist.until')}" scope="page"></s:set>
	<%--零时止  --%>
</s:if>
<s:elseif test="#request.prpLregist.endHour==12">
	<s:set var="endHour" value="%{getText('regist.end')}" scope="page"></s:set>
	<%-- 十二时止 --%>
</s:elseif>
<s:elseif test="#request.prpLregist.endHour==24">
	<s:set var="endHour" value="%{getText('modifySumClaim.hourEnd')}" scope="page"></s:set>
	<%--二十四时止  --%>
</s:elseif>
<s:else>
	<s:set var="endHour" value="" scope="page"></s:set>
</s:else>
<table class="common" align="center" width="100%">
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.registNo" />:
			<%-- 报案登记 --%>
			<input type="hidden" name="prpLregistLFlag" value="L">
			<input type="hidden" name="prpLregistRiskCode" value="${prpLregist.riskCode}">
			<input type="hidden" name="prpLregistClassCode" value="${prpLregist.classCode}">
			<input type="hidden" name="prpLregistLanguage" value="${prpLregist.language}">
			<input type="hidden" name="prpLregistLicenseNo" value="${prpLregist.licenseNo}">
			<input type="hidden" name="prpLregistLicenseColorCode" value="${prpLregist.licenseColorCode}">
			<input type="hidden" name="prpLregistCarKindCode" value="${prpLregist.carKindCode}">
			<input type="hidden" name="prpLregistModelCode" value="${prpLregist.modelCode}">
			<input type="hidden" name="prpLregistEngineNo" value="${prpLregist.engineNo}">
			<input type="hidden" name="prpLregistFrameNo" value="${prpLregist.frameNo}">
			<input type="hidden" name="prpLregistRunDistance" value="${prpLregist.runDistance}">
			<input type="hidden" name="prpLregistUseYears" value="${prpLregist.useYears}">
			<input type="hidden" name="prpLregistBrandName" value="${prpLregist.brandName}">
			<input type="hidden" name="prpLregistTypeForDriver" value="Regist">
			<input type="hidden" name='prpLregistEditType' value="${prpLregist.editType}">
			<input type="hidden" name='prpLregistDrivingLicenseNo'>
			<input type="hidden" name='prpLregistDrivingName'>
			<input type="hidden" name='prpLregistDrivingSex'>
			<input type="hidden" name='prpLregistDrivingIdentifyNumber'>
			<input type="hidden" name='prpLregistDrivingAge'>
			<input type="hidden" name='prpLregistDrivingOccupation'>
			<input type="hidden" name='prpLregistDrivingOccupationName'>
			<input type="hidden" name='prpLregistDrivingEducation'>
			<input type="hidden" name='prpLregistDrivingEducationName'>
			<input type="hidden" name='prpLregistDrivingUnitAddress'>
			<input type="hidden" name='prpLregistDrivingReceiveLicenseDate'>
			<input type="hidden" name='prpLregistDrivingCarType'>
			<input type="hidden" name='prpLregistDrivingAwardLicenseOrgan'>
			<input type="hidden" name="prpLregistLossQuantity" value="${prpLregist.lossQuantity}">
			<input type="hidden" name="prpLregistRunDistance" value="${prpLregist.runDistance}">
			<input type="hidden" name='riskcode' value="${prpLregist.riskCode}">
			<input type="hidden" name='policyno' value="${prpLregist.policyNo}">
			<input type="hidden" name='registno' value="${prpLregist.registNo}">
			<input type="hidden" name="prpLregistOthFlag" value="${prpLregist.othFlag}">
			<input type="hidden" name="underWriteEndDate" value="${prpLregist.underWriteEndDate}">
			<input type="hidden" name="coreURL" value="${core_URL}">
			<%--加入报案出险延期天数--%>
			<c:if test="${configValue!=null&&configValue!=''}">
				<input type="hidden" name='configValue' value="${configValue}">
			</c:if>
			<c:if test="${configValue==null||configValue==''}">
				<input type="hidden" name='configValue' value="99999">
			</c:if>
			<input type="hidden" name='prpLregistPayFee' value="${prpLregist.payFlag}">
			<input type="hidden" name='delinquentfeeCase' value="${delinquentfeeCase}">
			<input type="hidden" name="RecentCount" value="${prpLregistDto1.recentCount}">
			<input type="hidden" name="RegistViewLimitDay" value="${registViewLimitDay }">
			<input type="hidden" name="prpLregistFlowInTime" value="${prpLregist.flowInTime}">
			<input type="hidden" name="prpLregistSignDate" value="${prpLregist.signDate}">
			<input type="hidden" name="endorType" value="${endorType}">
			<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
			<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
			<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
			<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
			<input type="hidden" name="originalRequestURITemp" value="${sessionScope.originalRequestURITemp}">
		</td>
		<td class="input" style="width: 35%">
			<input type=text name="prpLregistRegistNo" class="readonly" readonly value="${prpLregist.registNo}">
		</td>
		<td class="title">
			<s:text name="query.xianzhongName" />
			:
		</td>
		<%-- 险种名称 --%>
		<td class="title">${riskCName }</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.insuredCode" />
			:
		</td>
		<td class="input">
			<span id=insuredCode>${prpLregist.insuredCode}</span>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="db.prpLregist.policyNo" />
			:
		</td>
		<td class="input" style="width: 35%" style="valign:middle">
			<input type=text name="prpLregistPolicyNo" class="readonly" readonly="true" style="width: 130px" value="${prpLregist.policyNo}">
			<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" style="width: 50px" height="17" border="0" onclick="relate(fm.prpLregistPolicyNo.value);return false;">
		</td>
	</tr>
	<tr>
		<td class="title"><s:text name="db.prpCmain.insuredID"/>:
		</td>
		<td class="input">
		<input type=text name="prpCinsuredIdentifyNumber" class="readonly" readonly value="${prpCinsured.identifyNumber}">
		</td>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
		</td>
		<%-- 已出险次数 --%>
		<td class="input">
			<%-- 出险信息画面 --%>
			<%@include file="/pages/commonProp/regist/PropExistRegist.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="title" id="InsuredNameID">
			<s:text name="db.prpLregist.insuredName" />:
		</td>
		<td class="input" >
			<input type=hidden name="prpLregistInsuredCode" title="<s:text name='db.prpCmain.insuredCode'/>" class="readonly" readonly="true" value="${prpLregist.insuredCode}"><%-- 被保险人代码 --%>
			<span id="prpLregistInsuredNameSpan"> <input type=text name="prpLregistInsuredName" title="<s:text name='db.prpCmain.insuredName'/>" class="codecode" style="width: 30%" ondblclick="getCinsured(this);"
					onkeyup="getCinsured(this);" onchange="getCinsured(this);" value="${prpLregist.insuredName}"><%-- 被保险人名称 --%>
					<input type="hidden" name="identifyNumber" class="readonly" readonly="true" value="${prpCinsured.identifyNumber}">
			</span> <img src="${ctx }/images/bgMarkMustInput.jpg">
			<c:if test="${termFlag=='1'}">
				<input type="checkbox" name="termFlag" value="1" onclick="termTypeChangge();">
				<font color="red"><s:text name="regist.fromSingle" /></font>
				<%-- 免导团单 --%>
				<br>
				<input type=button class="bigbutton" name="showHelpButton" value="<s:text name='button.operationGuide.value'/>" onclick="showHelp('helpText',0)">
				<%-- 免导团单操作指南 --%>
				<span id="helpText" style='width: 800; display: none; position: absolute; background-color: FFFFFF;'>
					<table class=common cellpadding="5" cellspacing="1" style="position: absolute;">
						<tr>
							<td>
								<font color="#ff3366"> <B>① </B> <s:text name="prompt.regist.select" /> <%-- 当系统默认带出的被保险人名称不是本次事故出险的被保险人时，请双击被保险人名称双击域,在弹出的对话框中选择。 --%> <br> <B>② </B> <s:text
										name="prompt.regist.select1" /> <%-- 当在弹出的对话框中无法选择到所需被保险人时，关闭该对话框，並勾选报案页面上的“免导团单”标志，勾选後在被保险人名称栏手工输入。 --%> <br> <B>③ </B> <s:text name="prompt.regist.select2" /> <%-- 当完成“②”後，如还需輸入事故者信息，请双击“事故者代码”双击域，在弹出的对话框中选择“9999--自定义”，然後将事故者信息中的“姓名”输入域中的“自定义”字样，改为与操作“②”輸入的被保险人名称一致。 --%>
								</font>
							</td>
						</tr>
						<tr>
							<td colspan=14 class="common">
								<input type=button name='hideHelpButton' value='<s:text name="button.closeHelp.value"/>' class="button" ACCESSKEY="O" onclick="hideHelp('helpText')">
								<%-- 关闭帮助 --%>
							</td>
						</tr>
					</table>
				</span>
			</c:if>
		</td>
		<td class="title">同險號碼:
		</td>
		<td class="input">
		<input type="text" name="prpCaddressSameAddressNo" class="readonly" readonly value="${prpLregist.sameAddressNo}" style="width: 150px;">
		<input type="button" value="同險保單" onclick="return sameAddressPolicyNo();" class="button">
		</td>
	</tr>
	<!--modify by qinyongli 2005-07-22 start 联共保和股东信息-->
	<c:if test="${coinsFlag!=null&&coinsFlag!=''}">
		<input type="hidden" name="coinsFlag" value="${coinsFlag}">
	</c:if>
	<c:if test="${coinsFlag==null||coinsFlag==''}">
		<input type="hidden" name="coinsFlag" value="0">
	</c:if>
	<c:if test="${shareHolderFlag!=null&&shareHolderFlag!=''}">
		<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
	</c:if>
	<c:if test="${shareHolderFlag==null||shareHolderFlag==''}">
		<input type="hidden" name="shareHolderFlag" value="0">
	</c:if>
	<c:if test="${tempReinsFlag!=null&&tempReinsFlag!=''}">
		<input type="hidden" name="tempReinsFlag" value="${tempReinsFlag}">
	</c:if>
	<c:if test="${tempReinsFlag==null||tempReinsFlag==''}">
		<input type="hidden" name="tempReinsFlag" value="0">
	</c:if>
	<tr>
		<td class="title">
			<s:text name="regist.prpLregist.insuranceTime" />:
		</td>
		<%-- 保险期间 --%>
		<td class="input">
			<rc:rcDate name="prpLregistStartDate" title="起保日期" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLregist.startDate}" /><%-- 起保日期 --%>
			<s:text name="regist.prpLregist.date" />&nbsp;${prpLregist.startHour}&nbsp;<s:text name="regist.prpLregist.hour" />起 至 
			<rc:rcDate name="prpLregistEndDate" title="終保日期" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLregist.endDate}" /><%-- 终保日期 --%>
			<s:text name="regist.prpLregist.date" />&nbsp;${prpLregist.endHour}&nbsp;<s:text name="regist.prpLregist.hour" />止
			<input type="hidden" name="prpLregistStartHour" value="${prpLregist.startHour}">
			<input type="hidden" name="prpLregistEndHour" value="${prpLregist.endHour}">
		</td>
		<td class="title">
			<s:text name="db.prpLregist.reportType" />:
		</td>
		<td class="input" colspan="3">
			<s:select name="reportType" list="#request.reportTypes" listKey="id.codeCode" listValue="codeCName" value="#request.prpLregist.reportType" styleClass="three" style="width:120px"></s:select>
		</td>
	</tr>
	<s:if test="#request.strRiskCode!=null">
		<s:set var="strClassCode" value="#request.strRiskCode.substring(0,2)" scope="page"></s:set>
	</s:if>
	<s:else>
		<s:set var="strClassCode" value="" scope="page"></s:set>
	</s:else>
	<s:if test="#attr.strClassCode!=''&&('03'.endsWith(#attr.strClassCode)||'2353'==#request.strRiskCode||'2354'==#request.strRiskCode)">
		<tr>
			<td class="title">
				<s:text name='commonLiab.regist.targetAddress' />:
			</td>
			<%-- 标的地址  --%>
			<td class="input" colspan=3>${strAddress }</td>
		</tr>
	</s:if>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.reportorName" />:
		</td>
		<td class="input">
			<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
			<input type="text" name="prpLregistReportorName" title="<s:text name='db.prpLregist.reportorName'/>" class="input" maxlength="100" style="width: 120px" value="${prpLregist.reportorName}"><%-- 报案人 --%>
		</td>
		<td class="title">
			<s:text name="prpLregist.reportorNumber" />:
		</td>
		<%-- 报案人电话 --%>
		<td class="input">
			<input type=text name="prpLregistReportorPhoneNumber" class="input" style="width: 120px" maxlength="12" value="${prpLregist.reportorPhoneNumber}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="prpLregist.reportHour" />:
		</td>
		<%-- 报案时间 --%>
		<td class="input">
			<rc:rcDate name="prpLregistReportDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLregist.reportDate}" />
			<s:text name="regist.prpLregist.date" />
			<input name="prpLregistReportHour"  maxlength="2" style="width: 20px" class="readonly" readonly="true" value="${prpLregist.reportHour}">
			<s:text name="regist.prpLregist.hour" />
			<%-- 时 --%>
			<input name="prpLregistReportMinute"  maxlength="2" style="width: 20px" class="readonly" readonly="true" value="${prpLregist.reportMinute}">
			<s:text name="regist.prpLregist.minute" />
			<%-- 分 --%>
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
		<td class="title">
			<s:text name="regist.reportDate" />
			:
		</td>
		<%-- 报案輸入日期 --%>
		<td class="input">
			<rc:rcDate name="prpLregistInputDate" title="<s:text name='db.prpCprofit.inputDate'/>" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLregist.inputDate}" /><%-- 輸入日期 --%>
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.linkerName" />
			:
		</td>
		<td class="input">
			<input type="text" name="prpLregistLinkerName" title="<s:text name='db.prpDagent.linkerName'/>" class="input" style="width: 120px" value="${prpLregist.linkerName}" /><%-- 联系人 --%>
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
		<td class="title">
			<s:text name="db.prpLregist.phoneNumber" />
			:
		</td>
		<td class="input">
			<input type="text" name="prpLregistPhoneNumber" title="<s:text name='db.prpLregist.phoneNumber'/>" class="input" style="width: 120px" value="${prpLregist.phoneNumber}"><%-- 联系电话 --%>
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.linkerAddress" />
			:
		</td>
		<td class="input" colspan="3">
			<input type=text name="prpLregistLinkerAddress" title="<s:text name='certainLoss.prpLcheck.Address'/>" class="input" style="width: 92%" value="${prpLregist.linkerAddress}"><%-- 联系人地址 --%>
		</td>
	</tr>
	<%@ include file="/pages/commonProp/regist/PropRegistLinkmanEdit.jsp"%>
	<tr>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
		</td>
		<script type="text/javascript">
			function flashPage(field) {
				if(checkRegistTime(field)){
					//mantis： CLM0187，處理人員：CD078，需求單編號：CLM0187.新核心-備案登記處理調整出險日期畫面重整確認
					return ;
					var damageDate = fm.prpLregistDamageStartDate.value;
					var damageHour = fm.prpLregistDamageStartHour.value;
					var damageMinute = fm.prpLregistDamageStartMinute.value;
					var vURL = "";
					if("${param.editType}"=="PERFECT"){
						vURL = "${ctx}/regist/registBeforeEdit.do?editType=PERFECT&prpLregistRegistNo=${param.prpLregistRegistNo}&prpCmainPolicyNo=${param.prpCmainPolicyNo}&&damageDate="+damageDate
								+"&damageHour="+damageHour+"&flushflag=true";
					} else if("${param.editType}"=="ADD"){
						vURL = "${ctx}/registBeforeEdit.do?prpCmainPolicyNo=${param.prpCmainPolicyNo}&editType=ADD&damageDate="+damageDate+"&damageHour="+damageHour+"&flushflag=true";
					} else {
						vURL = "${ctx}/registFinishQueryList.do?prpLregistRegistNo=${param.prpLregistRegistNo}&updateExt=true&swfLogFlowID=${param.swfLogFlowID}&swfLogLogNo=${param.swfLogLogNo}"
								+"&status=${param.status}&riskCode=${param.riskCode}&editType=${param.editType}&nodeType=${param.nodeType}&businessNo=${param.businessNo}&keyIn=${param.keyIn}"
								+"&policyNo=${param.policyNo}&modelNo=${param.modelNo}&nodeNo=${param.nodeNo}&dfFlag=${param.dfFlag}&actorId=${param.actorId}&processId=${param.processId}&flushflag=true"
								+"&damageDate="+damageDate+"&damageHour="+damageHour;
					}
					fm.action = vURL;
					fm.submit();
					return true;
				}
			}
		</script>
		<%-- 出险时间 --%>
		<td class="input">
			<c:choose>
				<c:when test="${editType == 'PERFECT'}">
					<rc:rcDate name="prpLregistDamageStartDate" style="width:100px" value="${prpLregist.damageStartDate}" class="readonly" readonly="true" wdatePicker="false"/><%-- 出险时间 --%>
					<s:text name="regist.prpLregist.date" />
					<input type="text" name="prpLregistDamageStartHour" title="<s:text name='db.prpLregist.damageHour'/>" class="readonly" readonly="readonly" maxlength="2" style="width: 25px" value="${prpLregist.damageStartHour}" ><%-- 出险小时 --%>
					<s:text name="regist.prpLregist.hour" />
					<%-- 时 --%>
					<input type="text" name="prpLregistDamageStartMinute" title="<s:text name='db.prpLregist.damageMinute'/>" class="readonly" readonly="readonly" maxlength="2" style="width: 25px" value="${prpLregist.damageStartMinute}" ><%-- 出险分钟 --%>
					<s:text name="regist.prpLregist.minute" />
					<%-- 分 --%>
					<img src="${ctx }/images/bgMarkMustInput.jpg">
				</c:when>
				<c:otherwise>
					<rc:rcDate name="prpLregistDamageStartDate" style="width:100px" value="${prpLregist.damageStartDate}" onchange="flashPage(this);"/><%-- 出险时间 --%>
					<s:text name="regist.prpLregist.date" />
					<input type="text" name="prpLregistDamageStartHour" title="<s:text name='db.prpLregist.damageHour'/>" class="input" maxlength="2" style="width: 25px" value="${prpLregist.damageStartHour}" onchange="flashPage(this);"><%-- 出险小时 --%>
					<s:text name="regist.prpLregist.hour" />
					<%-- 时 --%>
					<input type="text" name="prpLregistDamageStartMinute" title="<s:text name='db.prpLregist.damageMinute'/>" class="input" maxlength="2" style="width: 25px" value="${prpLregist.damageStartMinute}" onchange="flashPage(this);"><%-- 出险分钟 --%>
					<s:text name="regist.prpLregist.minute" />
					<%-- 分 --%>
					<img src="${ctx }/images/bgMarkMustInput.jpg">
				</c:otherwise>
			</c:choose>
		</td>
		<td class="title">
			<s:text name="db.prpLregist.damageCode" />
			:
		</td>
		<td class="input">
			<input type=text class="codecode" name="prpLregistDamageCode" style="width: 40px" title="<s:text name='db.prpLregist.damageCode'/>" value="${prpLregist.damageCode}"
				ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLregistRiskCode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLregistRiskCode.value);"
				onchange="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLregistRiskCode.value);"><%-- 出险原因 --%>
			<input type=text class="codecode" name="prpLregistDamageName" title="<s:text name='db.prpLregist.damageCode'/>" style="width: 110px" value="${prpLregist.damageName}"
				ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLregistRiskCode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLregistRiskCode.value);"
				onchange="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLregistRiskCode.value);"><%-- 出险原因 --%>
			<img src="${ctx }/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle"> <img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.areaPostCode" />
			:
		</td>
		<td class="input" colspan='3'>
			<input type="hidden" name="prpLregistAddressCode_bak" title="<s:text name='db.prpLregist.areaPostCode'/>" class="input" style="width: 120px" value="${prpLregist.addressCode}"><%-- 出险地邮政编码 --%>
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.damageAddress" />
			:
		</td>
		<td class="input" colspan='3'>
			<select name="countryFlag" style="width:100px" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
				<option value="0">
					<s:text name="commonAcci.claim.domestic" />
				</option>
				<%-- 国内 --%>
				<option value="1">
					<s:text name="commonAcci.claim.abroad" />
				</option>
				<%-- 国外 --%>
			</select>
			<input type=text class="codecode" name="countryCode" style="display: none" />
			<input type=text class="codecode" name="countryCName" style="display: none" title="<s:text name='common.select.country'/>" style="width:120px"
				ondblclick="code_CodeSelect(this, 'CountryCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'CountryCode','-1,0','Y','N');" onchange="code_CodeSelect(this, 'CountryCode','-1,0','Y','N');" />
			<%-- 选择国家名 --%>
			<input type=hidden class="codecode" name="provinceCode" value="710000" />
			<input type=hidden class="codecode" name="provinceName" title="<s:text name='common.select.province'/>" ondblclick="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"
				onkeyup="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onchange="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" value="" />
			<%-- 选择省 --%>
			<input type=hidden class="codecode" name="cityCode" style="display: none" />
			<input type=hidden class="codecode" name="cityName" title="<s:text name='common.select.city'/>" style="width: 120px"
				ondblclick="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onkeyup="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);"
				onchange="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" />
			<%-- 选择市 --%>
			<input type=text class="codecode" name="prpLregistAddressCode" style="width: 40px" title="<s:text name='regist.prpLregist.areaCode'/>" value="${prpLregist.addressCode}"
				ondblclick="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" onkeyup="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" onchange="code_CodeSelect(this, 'PostCode','0,1','Y','Y');">
			<%-- 郵遞區號 --%>
			<input type=text class="codecode" name="prpLregistAddressName" title="<s:text name='db.prpLclaim.damageAreaName'/>" style="width: 110px" value="${prpLregist.addressName}"
				ondblclick="code_CodeSelect(this, 'PostCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PostCode','-1,0','Y','N');" onchange="code_CodeSelect(this, 'PostCode','-1,0','Y','N');">
			<%-- 出險地區 --%>
			<input type=text name="prpLregistDamageAddress" title="<s:text name='db.prpLregist.damageAddress'/>" Class="input" style="width: 350px" value="${prpLregist.damageAddress}"
				onclick="showProvinceCity(this,'countryCName','prpLregistAddressName');" selectValue="${prpLregist.addressName}">
			<%-- 出险地点 --%>
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="regist.prpLregist.currency" />
			:
		</td>
		<%--  币别--%>
		<td class="input" colspan=3>
			<input type="text" name="prpLregistEstiCurrency" value="${prpLregist.estiCurrency}" class="readonly" readonly style="width: 10%" title="<s:text name='db.prpDrate.currency'/>"><%-- 币别 --%>
			<input type=text name="prpLregistEstiCurrencyName" class="readonly" readonly style="width: 20%" title="<s:text name='db.prpDrate.currency'/>" value="${prpLregist.estiCurrencyName}"><%-- 币别 --%>
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLclaimfee.sumClaim" />
		</td>
		<%-- 报损金额 --%>
		<td class="input" colspan=3>
			<input type=text name="prpLregistEstimateLoss" title="<s:text name='print.estimateLoss'/>" Class="input" style="width: 120px" value='<fmt:formatNumber value="${prpLregist.estimateLoss}" pattern="#"/>' onblur="checkLength(this);"> <%-- 估计损失 --%>
		</td>
		<input type=hidden name="prpLregistEstimateFee" title="<s:text name='common.report.charge'/>" Class="input" style="width: 120px" value='<fmt:formatNumber value="${prpLregist.estimateFee}" pattern="#"/>'><%-- 报损费用 --%>
	</tr>
