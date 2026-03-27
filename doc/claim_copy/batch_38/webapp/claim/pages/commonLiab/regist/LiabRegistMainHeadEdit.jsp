<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： 中科软
* MODIFYLIST ： Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<s:if test="#request.prpLregist.startHour==0">
	<s:set var="startHour" value="%{getText('modifySumClaim.comeEffect')}" scope="page"></s:set>
</s:if>
<s:elseif test="#request.prpLregist.startHour==12">
	<s:set var="startHour" value="%{getText('regist.from')}" scope="page"></s:set>
</s:elseif>
<s:elseif test="#request.prpLregist.startHour==24">
	<s:set var="startHour" value="%{getText('regist.start')}" scope="page"></s:set>
</s:elseif>
<s:else>
	<s:set var="startHour" value="" scope="page"></s:set>
</s:else>
<s:if test="#request.prpLregist.endHour==0">
	<s:set var="endHour" value="%{getText('regist.until')}" scope="page"></s:set>
</s:if>
<s:elseif test="#request.prpLregist.endHour==12">
	<s:set var="endHour" value="%{getText('regist.end')}" scope="page"></s:set>
</s:elseif>
<s:elseif test="#request.prpLregist.endHour==24">
	<s:set var="endHour" value="%{getText('modifySumClaim.hourEnd')}" scope="page"></s:set>
</s:elseif>
<s:else>
	<s:set var="endHour" value="" scope="page"></s:set>
</s:else>
<table class="common" align="center" width="100%">
	<tr class=listtitle>
		<td colspan="4">
			<s:text name="menu.regist.main" />
			<%--报案登记--%>
			<c:if test="${prpLregist.cancelDate!=null&&prpLregist.cancelDate!=''}">
				<s:text name="prompt.regist.noteEliminate" />
				<%--(已注消)--%>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="title" colspan="2">
			<s:text name="menu.regist.main" />
			<%--报案登记--%>
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
			<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
			<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
			<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
			<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
			<input type="hidden" name="prpLregistOthFlag" value="${prpLregist.othFlag}">
			<input type="hidden" name="underWriteEndDate" value="${prpLregist.underWriteEndDate}">
			<input type="hidden" name="coreURL" value="${core_URL }">
			<input type="hidden" name='prpLregistPayFee' value="${prpLregist.payFlag}">
			<input type="hidden" name='delinquentfeeCase' value="${delinquentfeeCase}">
			<input type="hidden" name="RecentCount" value="${prpLregistDto1.recentCount}">
			<input type="hidden" name="RegistViewLimitDay" value="${registViewLimitDay }">
			<input type="hidden" name="prpLregistFlowInTime" value="${prpLregist.flowInTime}">
			<input type="hidden" name="prpLregistSignDate" value="${prpLregist.signDate}">
			<s:if test="#attr.shareHolderFlag!=null&&#attr.shareHolderFlag!=''">
				<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
			</s:if>
			<s:else>
				<input type="hidden" name="shareHolderFlag" value="0">
			</s:else>
			<!--  保单停效标志 等於54为停效 start -->
			<input type="hidden" name="endorType" value="${endorType}">
			<!--  保单停效标志 等於54为停效 end -->
			<input type="hidden" name="originalRequestURITemp" value="${sessionScope.originalRequestURITemp}">
		</td>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.riskCName" />
		</td>
		<%--险种名称:--%>
		<td class="title">${riskCName }</td>
	</tr>
	<tr>
		<%--加入报案出险延期天数 add by qinyongli--%>
		<s:if test="#attr.configValue!=null&&#attr.configValue!=''">
			<input type="hidden" name='configValue' value="${configValue}">
		</s:if>
		<s:else>
			<input type="hidden" name='configValue' value="99999">
		</s:else>
		<%--加入责任险追溯期 --%>
		<s:if test="#attr.liabStartDate!=null&&#attr.liabStartDate!=''">
			<input type="hidden" name='liabStartDate' value="${liabStartDate}">
		</s:if>
		<s:else>
			<input type="hidden" name='liabStartDate' value="none">
		</s:else>
		<td class="title" style="width: 15%">
			<s:text name="db.prpLregist.registNo" />
			:
		</td>
		<td class="input" style="width: 35%">
			<input type=text name="prpLregistRegistNo" class="readonly" readonly="true" value="${prpLregist.registNo}">
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="db.prpLregist.policyNo" />
			:
		</td>
		<td class="input" style="width: 35%" style="valign:middle">
			<input type=text name="prpLregistPolicyNo" class="readonly" readonly="true" style="width: 140px" value="${prpLregist.policyNo}">
			<input type="image" name="btRelate" src="${ctx}/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLregistPolicyNo.value);return false;">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.insuredCode" />
			:
		</td>
		<td class="input">
			<span id=insuredCode>${prpLregist.insuredCode}</span>
			<!-- </a>-->
		</td>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
			:
		</td>
		<%--已出险次数:--%>
		<td class="input">
			<%-- 出险信息画面 --%>
			<%@include file="/pages/commonLiab/regist/LiabExistRegist.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="title" id="InsuredNameID">
			<s:text name="db.prpLregist.insuredName" />
			:
		</td>
		<td class="input" colspan="1">
			<input type=hidden name="prpLregistInsuredCode" title="被保险人代码" class="readonly" readonly="true" value="${prpLregist.insuredCode}">
			<input type=text name="prpLregistInsuredName" title="被保险人名称" class="codecode" style="width: 80%" ondblclick="getCinsured(this);" onkeyup="getCinsured(this);" onchange="getCinsured(this);"
				value="${prpLregist.insuredName}">
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
		<td class="title">
			<s:text name="prpLregist.reportHour" />
			:
			<%--报案时间--%>
		</td>
		<td class="input">
			<rc:rcDate name="prpLregistReportDate" style="width:100px" value="${prpLregist.reportDate}" onchange="flashPage(this);"/>
			<s:text name="regist.prpLregist.date" />
			<input name="prpLregistReportHour" class="input" maxlength="2" style="width: 25px" value="${prpLregist.reportHour}" onchange="flashPage(this);">
			<s:text name="regist.prpLregist.hour" />
			<input name="prpLregistReportMinute" class="input" maxlength="2" style="width: 25px" value="${prpLregist.reportMinute}" onchange="flashPage(this);">
			<s:text name="regist.prpLregist.minute" />
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="regist.prpLregist.insuranceTime" />
		</td>
		<%--保险期间--%>
		<td class="input" colspan=2>
			<rc:rcDate name="prpLregistStartDate" title="起保日期" class="readonly" style="width:80px" readonly="true" wdatePicker="false" value="${prpLregist.startDate}" />
			<s:text name="regist.prpLregist.date" />&nbsp;${prpLregist.startHour}&nbsp;<s:text name="regist.prpLregist.hour" />起 至 
			<rc:rcDate name="prpLregistEndDate" title="終保日期" class="readonly" style="width:80px" readonly="true" wdatePicker="false" value="${prpLregist.endDate}" />
			<s:text name="regist.prpLregist.date" />&nbsp;${prpLregist.endHour}&nbsp;<s:text name="regist.prpLregist.hour" />止
			<input type="hidden" name="prpLregistStartHour" value="${prpLregist.startHour}">
			<input type="hidden" name="prpLregistEndHour" value="${prpLregist.endHour}">
		</td>
		<td class="input" colspan=2>
			<input type="hidden" name="damageDate" value="${ damageDate}">
			<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value' />"
				onclick="backWardPolicy(fm.coreURL.value,fm.prpLregistPolicyNo.value,fm.prpLregistRiskCode.value,fm.prpLregistDamageStartDate.value,fm.prpLregistComCode.value);">
		</td>
		<%--出险时保单信息--%>
	</tr>
	<s:if test="#attr.strRiskCode!=null&&#attr.strRiskCode.length()>2">
		<s:set var="strClassCode" value="#attr.strRiskCode.substring(0,2)" scope="page" />
	</s:if>
	<s:else>
		<s:set var="strClassCode" value="" scope="page" />
	</s:else>
	<s:if test="#attr.strClassCode!=''&&(#attr.strClassCode=='03'||#attr.strRiskCode=='2353'||#attr.strRiskCode=='2354')">
		<tr>
			<td class="title">
				<s:text name="commonLiab.regist.targetAddress" />
				:
			</td>
			<%--标的地址--%>
			<td class="input" colspan=3>${strAddress }</td>
		</tr>
	</s:if>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.reportorName" />
			:
		</td>
		<td class="input">
			<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
			<input type=text name="prpLregistReportorName" title="<s:text name="db.prpLregist.reportorName" />" class="input" maxlength="100" style="width: 120px" value="${prpLregist.reportorName}"
				onchange='changeLxr();'>
		</td>
		<td class="title">
			<s:text name="prpLregist.reportorNumber" />
			：
		</td>
		<%--报案人电话--%>
		<td class="input" 　colspan='2'>
			<input type=text name="prpLregistReportorPhoneNumber" class="input" style="width: 120px" maxlength="12" value="${prpLregist.reportorPhoneNumber}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.reportType" />
			:
		</td>
		<td class="input">
			<s:select name="reportType" list="#request.reportTypes" id="reportType" listKey="id.codeCode" listValue="codeCName" value="#request.prpLregist.reportType" styleClass="three" style="width:120px"></s:select>
		</td>
		<td class="title">
			<s:text name="regist.reportDate" />
			:
		</td>
		<td class="input">
			<rc:rcDate name="prpLregistInputDate" title="<s:text name='db.prpLreclaim.inputDate'/>" class="readonly" style="width:80px" readonly="readonly" wdatePicker="false" value="${prpLregist.inputDate}" />
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.linkerName" />
			:
		</td>
		<td class="input">
			<input type=text name="prpLregistLinkerName" title="<s:text name="db.prpDagent.linkerName" />" class="input" style="width: 80%" value="${prpLregist.linkerName}" />
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
		<td class="title">
			<s:text name="db.prpLregist.phoneNumber" />
			:
		</td>
		<td class="input">
			<input type=text name="prpLregistPhoneNumber" title="<s:text name="db.prpLregist.phoneNumber " />" class="input" style="width: 120px" value="${prpLregist.phoneNumber}">
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<!-- add by zhyi start -->
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.linkerAddress" />
			:
		</td>
		<td class="input" colspan=3>
			<input type=text name="prpLregistLinkerAddress" title="<s:text name="certainLoss.prpLcheck.Address" />" class="input" style="width: 92%" value="${prpLregist.linkerAddress}">
		</td>
	</tr>
	<!-- add by zhyi end -->
	<tr>
		<td class="title">
			<s:text name="regist.prpLregist.damageTime" />
			:
		</td>
		<script type="text/javascript">
			function flashPage(field) {
				if(checkRegistTime(field)){
					//mantis： CLM0187，處理人員：CD078，需求單編號：CLM0187.新核心-備案登記處理調整出險日期畫面重整確認
					return ;
					var damageStartDate = fm.prpLregistDamageStartDate.value;
					var damageStartHour  = fm.prpLregistDamageStartHour.value;
					var damageStartMinute  = fm.prpLregistDamageStartMinute.value;
					var vURL = "";
					if("${param.editType}"=="PERFECT"){
						vURL = "${ctx}/regist/registBeforeEdit.do?editType=PERFECT&prpLregistRegistNo=${param.prpLregistRegistNo}&prpCmainPolicyNo=${param.prpCmainPolicyNo}&&damageDate="+damageStartDate
								+"&damageHour="+damageStartHour+"&flushflag=true";
					} else if("${param.editType}"=="ADD"){
						vURL = "${ctx}/registBeforeEdit.do?prpCmainPolicyNo=${param.prpCmainPolicyNo}&editType=ADD&damageDate="+damageStartDate+"&damageHour="+damageStartHour+"&flushflag=true";
					} else {
						vURL = "${ctx}/registFinishQueryList.do?prpLregistRegistNo=${param.prpLregistRegistNo}&updateExt=true&swfLogFlowID=${param.swfLogFlowID}&swfLogLogNo=${param.swfLogLogNo}"
								+"&status=${param.status}&riskCode=${param.riskCode}&editType=${param.editType}&nodeType=${param.nodeType}&businessNo=${param.businessNo}&keyIn=${param.keyIn}"
								+"&policyNo=${param.policyNo}&modelNo=${param.modelNo}&nodeNo=${param.nodeNo}&dfFlag=${param.dfFlag}&actorId=${param.actorId}&processId=${param.processId}&flushflag=true"
								+"&damageDate="+damageStartDate+"&damageHour="+damageStartHour;
					}
					fm.action = vURL;
					fm.submit();
				}
			}
		</script>
		<%--出险时间--%>
		<td class="input">
			<c:choose>
				<c:when test="${editType == 'PERFECT'}">
					<c:set var="title">
						<s:text name='regist.prpLregist.damageTime' />
					</c:set>
					<rc:rcDate name="prpLregistDamageStartDate" title="${title}" style="width:100px" value="${prpLregist.damageStartDate}" class="readonly" readonly="true" wdatePicker="false"/>
					<s:text name="regist.prpLregist.date" />
					<%-- 日 --%>
					<input type="text" name="prpLregistDamageStartHour" title="<s:text name="db.prpLregist.damageHour" />" class="readonly" readonly="readonly" maxlength="2" style="width: 25px" value="${prpLregist.damageStartHour}" >
					<s:text name="regist.prpLregist.hour" />
					<%-- 时 --%>
					<input type="text" name="prpLregistDamageStartMinute" title="<s:text name="db.prpLregist.damageMinute" />" class="readonly" readonly="readonly" maxlength="2" style="width: 25px" value="${prpLregist.damageStartMinute}" >
					<s:text name="regist.prpLregist.minute" />
					<%-- 分 --%>
					<img src="${ctx}/images/bgMarkMustInput.jpg">
				</c:when>
				<c:otherwise>
					<c:set var="title">
						<s:text name='regist.prpLregist.damageTime' />
					</c:set>
					<rc:rcDate name="prpLregistDamageStartDate" title="${title}" style="width:100px" value="${prpLregist.damageStartDate}" onchange="flashPage(this);" />
					<s:text name="regist.prpLregist.date" />
					<%-- 日 --%>
					<input type="text" name="prpLregistDamageStartHour" title="<s:text name="db.prpLregist.damageHour" />" class="input" maxlength="2" style="width: 25px" value="${prpLregist.damageStartHour}"  onchange="flashPage(this);" >
					<s:text name="regist.prpLregist.hour" />
					<%-- 时 --%>
					<input type="text" name="prpLregistDamageStartMinute" title="<s:text name="db.prpLregist.damageMinute" />" class="input" maxlength="2" style="width: 25px" value="${prpLregist.damageStartMinute}"  onchange="flashPage(this);" >
					<s:text name="regist.prpLregist.minute" />
					<%-- 分 --%>
					<img src="${ctx}/images/bgMarkMustInput.jpg">
				</c:otherwise>
			</c:choose>
		</td>
		<td class="title">
			<s:text name="db.prpLregist.damageCode" />
			:
		</td>
		<td class="input">
			<input type=text class="codecode" name="prpLregistDamageCode" style="width: 40px" title="出险原因" value="${prpLregist.damageCode}"
				ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);" onchange="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
				onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);">
			<input type=text class="codecode" name="prpLregistDamageName" title="<s:text name="db.prpLregist.damageCode" />" style="width: 120px" value="${prpLregist.damageName}"
				ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);"
				onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);">
			<img src="${ctx}/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle"> <img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.damageAddress" />
			:
		</td>
		<td class="input" colspan='3'>
			<select name="countryFlag" style="width: 100px" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
				<option value="0">
					<s:text name="commonAcci.claim.domestic" />
					<%--国内--%>
				</option>
				<option value="1">
					<s:text name="commonAcci.claim.abroad" />
					<%--国外--%>
				</option>
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
			<s:text name="db.prpLregist.authorityUnit" />
			:
		</td>
		<%--憲警單位--%>
		<td class="input">
			<input type="text" name="prpLregistAuthorityUnit" title="<s:text name="db.prpLregist.authorityUnit"/>" class="input" style="width: 120px" value="${prpLregist.authorityUnit}">
		</td>
		<input type="hidden" name="prpLregistAddressCode_bak" title="<s:text name="db.prpLregist.damageAreaPostCode"/>" class="input" style="width: 80px" value="${prpLregist.addressCode}">
		<td class="title">
			<s:text name="commonAcci.regist.sendMesFlag" />
			:
		</td>
		<%--是否发短信--%>
		<td class="input">
			<c:if test="${editType=='ADD'}">
				<s:text name="certainLoss.thirdCarLoss.yes" />
				<%--是--%>
				<input type="radio" name='sendMesFlag' value='1' checked>
				<s:text name="certainLoss.thirdCarLoss.no" />
				<%--否--%>
				<input type="radio" name='sendMesFlag' value='0'>
			</c:if>
			<c:if test="${editType!='ADD'}">
				<s:text name="certainLoss.thirdCarLoss.yes" />
				<%--是--%>
				<input type="radio" name='sendMesFlag' value='1' <c:if test="${prpLregist.sendMesFlag=='1'}">checked</c:if>>
				<s:text name="certainLoss.thirdCarLoss.no" />
				<%--否--%>
				<input type="radio" name='sendMesFlag' value='0' <c:if test="${prpLregist.sendMesFlag=='0'}">checked</c:if>>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLperson.currency" />
			:
		</td>
		<%--币别--%>
		<td class="input">
			<input type="text" name="prpLregistEstiCurrency" value="${prpLregist.estiCurrency}" class="readonly" readonly style="width: 30%" title="<s:text name='db.prpDrate.currency'/>">
			<input type=text name="prpLregistEstiCurrencyName" class="readonly" readonly style="width: 60%" title="<s:text name='db.prpDrate.currency'/>" value="${prpLregist.estiCurrencyName}">
		</td>
		<td class="title">
			<s:text name="db.prpLregist.estimateLoss" />
			:
		</td>
		<%-- 报损金额 --%>
		<td class="input">
			<input type=text name="prpLregistEstimateLoss" title="<s:text name='print.estimateLoss'/>" Class="input" style="width: 80px"
				value="<fmt:formatNumber value='${prpLregist.estimateLoss}' pattern='#'/>">
		</td>
	</tr>