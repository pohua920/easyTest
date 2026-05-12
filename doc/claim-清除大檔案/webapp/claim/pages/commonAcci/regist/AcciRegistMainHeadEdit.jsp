<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： 中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
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
		<td class="title">
			<s:text name="db.prpLregist.registNo" />:
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
			<input type="hidden" name='prpCmainPolicyNo' value="${prpLregist.policyNo}">
			<input type="hidden" name="coreURL" value="${core_URL }">
			<input type="hidden" name='underWriteEndDate' value="${prpLregist.underWriteEndDate}">
			<input type="hidden" name="prpLregistOthFlag" value="${prpLregist.othFlag}">
			<input type="hidden" name='prpLregistPayFee' value="${prpLregist.payFlag}">
			<!--添加标志位，用於提交表单时判断时否还有申请调查未提交。 2005-08-04-->
			<input type="hidden" name="AcciClaimFlag" value="${com_sinosoft_acciFlag }">
			<!-- 保单停效标志 等於54为停效 start -->
			<input type="hidden" name="endorType" value="${endorType}">
			<input type="hidden" name="policyCancelFlag" value="${policyCancelFlag}">
			<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
			<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
			<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
			<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
			<!-- 保单停效标志 等於54为停效 end -->
			<input type="hidden" name="originalRequestURITemp" value="${sessionScope.originalRequestURITemp}">
		</td>
		<td class="input" style="width: 35%">
			<input type=text name="prpLregistRegistNo" class="readonly" readonly="true" value="${prpLregist.registNo}">
		</td>
		<td class="title">
			<s:text name="certainLoss.prpLcheck.riskCName" />:
			<%--险种名称--%>
		</td>
		<td class="title">${riskCName }</td>
	</tr>
	<tr>
		<%--加入报案出险延期天数 --%>
		<s:if test="#request.configValue!=null&&#request.configValue!=''">
			<input type="hidden" name='configValue' value="${configValue}">
		</s:if>
		<s:else>
			<input type="hidden" name='configValue' value="99999">
		</s:else>
		<s:if test="#request.shareHolderFlag!=null&&#request.shareHolderFlag!=''">
			<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
		</s:if>
		<s:else>
			<input type="hidden" name="shareHolderFlag" value="0">
		</s:else>
		<%--加入报案出险延期天数 --%>
		<td class="title">
			<s:text name="db.prpLregist.insuredCode" />:
		</td>
		<td class="input">
			<span id=insuredCode>${prpLregist.insuredCode} </span>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="db.prpLregist.policyNo" />:
		</td>
		<td class="input" style="width: 35%" style="valign:middle">
			<input type=text name="prpLregistPolicyNo" class="readonly" readonly="true" style="width: 170px" value="${prpLregist.policyNo}">
			<input type="image" name="btRelate" src="${ctx }/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLregistPolicyNo.value);return false;">
		</td>
	</tr>
	<tr>
		<td class="title">
			被保險人ID:
		</td>
		<td class="input">
			<input type=text name="prpCinsuredIdentifyNumber" class="readonly" readonly="true" value="${prpCinsured.identifyNumber}">
		</td>
		<td class="title">
			<s:text name="certainLoss.prpLacciCheck.prpLacciCheckDamageTimes" />:
			<%--已出事故次数--%>
		</td>
		<td class="input">
			<%-- 出险信息画面 --%>
			<%@include file="/pages/common/regist/ExistRegist.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="title" id="InsuredNameID">
			<s:text name="db.prpLregist.insuredName" />:
		</td>
		<td class="input" title="">
			<input type=hidden name="prpLregistInsuredCode" title="被保險人代碼" class="readonly" readonly="true" value="${prpLregist.insuredCode}">
			<span id="prpLregistInsuredNameSpan"> <input type=text name="prpLregistInsuredName" title="被保險人名稱" style="width: 40%" class="codecode" value="${prpLregist.insuredNameShow}" ondblclick="getCinsured(this);" onkeyup="getCinsured(this);" onchange="getCinsured(this);">
			<input type="hidden" name="identifyNumber" class="readonly" readonly="true" value="${prpCinsured.identifyNumber}">
			</span> <img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
		<td class="title" id="sumAmount">
			<s:text name="commonAcci.regist.amount" />NTD:
			<%--保险金额NTD--%>
		</td>
		<td class="input">
			<input type=text name="prpLregistSumAmount" title="保險金額" class="readonly" readonly="true" value='<fmt:formatNumber value="${prpLregist.sumAmount}" pattern="#"/>'>
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="regist.prpLregist.insuranceTime" />:
			<%--保险期间--%>
		</td>
		<td class="input" colspan=1>
			<rc:rcDate name="prpLregistStartDate" title="起保日期" style="width: 80px" readonly="true" class="readonly" value="${prpLregist.startDate}" />
			<s:text name="regist.prpLregist.date" />&nbsp;${prpLregist.startHour}&nbsp;<s:text name="regist.prpLregist.hour" />起 至 
			<rc:rcDate name="prpLregistEndDate" title="終保日期" class="readonly" style="width: 80px" readonly="true" value="${prpLregist.endDate}" />
			<s:text name="regist.prpLregist.date" />&nbsp;${prpLregist.endHour}&nbsp;<s:text name="regist.prpLregist.hour" />止
			<input type="hidden" name="prpLregistStartHour" value="${prpLregist.startHour}">
			<input type="hidden" name="prpLregistEndHour" value="${prpLregist.endHour}">
		</td>
		<td class="input" colspan=3>
			<input type="hidden" name="damageDate" value='<s:property value="#parameters.damageDate"/>'>
			<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value' />" onclick="backWardPolicy(fm.coreURL.value,fm.prpLregistPolicyNo.value,fm.prpLregistRiskCode.value,fm.prpLregistDamageStartDate.value,fm.prpLregistComCode.value);">
		</td>
		<%--出险时保单信息--%>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.reportorName" />:
		</td>
		<td class="input">
			<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
			<input type=text name="prpLregistReportorName" title="報案人" class="input" maxlength="100" style="width: 120px" value="${prpLregist.reportorName}" onchange='changeLxr();'>
		</td>
		<td class="title">
			<s:text name="prpLregist.reportorNumber" />:
			<%--报案人电话--%>
		</td>
		<td class="input">
			<input type=text name="prpLregistReportorPhoneNumber" class="input" style="width: 120px" maxlength="12" value="${prpLregist.reportorPhoneNumber}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="prpLregist.reportHour" />:
			<%--报案时间--%>
		</td>
		<td class="input">
			<rc:rcDate name="prpLregistReportDate" class="input" style="width: 100px" value="${prpLregist.reportDate}" onchange="flashPage(this);"/>
			<s:text name="regist.prpLregist.date" />
			<%--日--%>
			<input type=text name="prpLregistReportHour" class="input" maxlength="2" style="width: 25px" value="${prpLregist.reportHour}" onchange="flashPage(this);">
			<s:text name="regist.prpLregist.hour" />
			<%--时--%>
			<input type=text name="prpLregistReportMinute" class="input" maxlength="2" style="width: 25px" value="${prpLregist.reportMinute}" onchange="flashPage(this);">
			<s:text name="regist.prpLregist.minute" />
			<%--分--%>
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
		<td class="title">
			<s:text name="commonAcci.regist.sendMessage" />:
			<%--是否发短信--%>
		</td>
		<td class="input" colspan='1'>
			<s:if test="#editType=='ADD'">
				<s:text name="certainLoss.thirdCarLoss.yes" />
				<%--是--%>
				<input type="radio" name='sendMesFlag' value='1' checked>
				<s:text name="certainLoss.thirdCarLoss.no" />
				<%--否--%>
				<input type="radio" name='sendMesFlag' value='0'>
			</s:if>
			<s:else>
				<s:text name="certainLoss.thirdCarLoss.yes" />
				<%--是--%>
				<input type="radio" name='sendMesFlag' <c:if test="${prpLregist.sendMesFlag=='1' }">checked</c:if> value='1'>
				<s:text name="certainLoss.thirdCarLoss.no" />
				<%--否--%>
				<input type="radio" name='sendMesFlag' <c:if test="${prpLregist.sendMesFlag=='0' }">checked</c:if> value='0'>
			</s:else>
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.reportType" />:
			<%--报案类型--%>
		</td>
		<td class="input">
			<s:select name="reportType" list="#request.reportTypes" value="#request.prpLregist.reportType" listKey="id.codeCode" listValue="codeCName" styleClass="three" style="width:120px"></s:select>
		</td>
		<td class="title">
			<s:text name="regist.reportDate" />:
			<%--輸單日期--%>
		</td>
		<td class="input">
			<rc:rcDate name="prpLregistInputDate" title="輸單日期" class="readonly" style="width: 80px" readonly="true" value="${prpLregist.inputDate}" />
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.linkerName" />:
		</td>
		<td class="input">
			<input type=text name="prpLregistLinkerName" title="<s:text name="db.prpLregist.linkerName" />" class="input" style="width: 120px" value="${prpLregist.linkerName}" />
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
		<td class="title">
			<s:text name="db.prpLregist.phoneNumber" />:
		</td>
		<td class="input">
			<!--<img src="/claim/images/bgMarkMustInput.jpg">-->
			<input type=text name="prpLregistPhoneNumber" title="<s:text name="db.prpLregist.phoneNumber" />" class="input" style="width: 120px" value="${prpLregist.phoneNumber}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="backVisit.contactAddress" />:
			<%--联系地址--%>
		</td>
		<td class="input">
			<input type=text name="prpLregistLinkerAddress" title="<s:text name="backVisit.contactAddress" />" class="input" style="width: 240px" value="${prpLregist.linkerAddress}" />
		</td>
		<td class="title">
			<s:text name="claim.claim.relationship" />:
			<%--與被保險人關係--%>
		</td>
		<td class="input">
			<!-- 从数据库中取值-->
			<select name="prpLregistClauseType" title="<s:text name="claim.claim.relationship" />" class="input" style="width: 100px">
				<option value="1" <c:if test="${prpLregist.clauseType=='1' }">selected</c:if>>
					<s:text name="regist.prpLregist.self" />
					<%--本人--%>
				</option>
				<option value="2" <c:if test="${prpLregist.clauseType=='2' }">selected</c:if>>
					<s:text name="regist.prpLregist.agentName" />
					<%--代理人--%>
				</option>
				<option value="3" <c:if test="${prpLregist.clauseType=='3' }">selected</c:if>>
					<s:text name="regist.prpLregist.other" />
					<%--其他--%>
				</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="title" colspan="4" width="100%">
			<table class="common" align="center" width="100%">
				<tr>
					<td class="subformtitle" style="text-align: left;display: none">
						<img style="cursor: hand;" src="${ctx }/images/butExpandBlue.gif" name="RegistPolicyRiskImg" onclick="showPage(this,RegistPolicyInfo)">
						<s:text name="commonAcci.claim.insuredAccidentInfo" />
						<%--事故被保险人信息--%>
						<br>
						<table class="common" align="center" id="RegistPolicyInfo">
							<tbody>
								<tr>
									<td class="prompttitle" style="width: 20%; display: none">
										<s:text name="commonAcci.claim.accidentCode" />
										<%--事故者代码--%>
									</td>
									<td class="prompttitle" style="width: 20%">
										<s:text name="claim.name" />
										<%--姓名--%>
									</td>
									<td class="prompttitle" style="width: 10%">
										<s:text name="db.prpLperson.personSex" />
										<%--性别--%>
									</td>
									<td class="prompttitle" style="width: 10%">
										<s:text name="db.prpLperson.personAge" />
										<%--年龄--%>
									</td>
									<td class="prompttitle" style="width: 25%">
										<s:text name="db.prpLregist.identifyNumber" />
										<%--身份证号--%>
									</td>
									<td class="prompttitle" style="width: 5%">
										<s:text name="commonAcci.claim.beneficiaryInfo" />
										<%--受益人信息--%>
									</td>
									<td class="prompttitle" style="width: 10%">
										<s:text name="commonAcci.regist.effectCoverage" />
										<%--有效保额--%>
									</td>
								</tr>
								<tr>
									<s:if test="#request.insuredNameFlag=='Ture'">
										<td class="input" align=center style="width: 20%; display: none">
											<input type="text" name="prpLacciPersonAcciCode" value="${prpLacciPerson.acciCode}" class="input" title="事故者代碼">
										</td>
										<input type="hidden" name="clickCount">
										<td class="input" align=center style="width: 20%">
											<input type=text name="prpLacciPersonAcciName" title="事故者姓名" value="${prpLacciPerson.acciName}" class="input" title="事故者姓名">
											<img src="${ctx }/images/bgMarkMustInput.jpg">
										</td>
									</s:if>
									<s:else>
										<td class="input" align=center style="width: 20%; display: none">
											<input type="hidden" name="clickCount" value="1">
											<input type="text" name="prpLacciPersonAcciCode" value="${prpLacciPerson.acciCode}" title="事故者代碼" class="codecode" ondblclick="showAcciName(this);" onkeyup="showAcciName(this);" onchange="showAcciName(this);">
										</td>
										<td class="input" align=center style="width: 20%">
											<input type=text name="prpLacciPersonAcciName" title="事故者姓名" value="${prpLacciPerson.acciName}" class="codecode" title="事故者姓名" ondblclick="showAcciName(this);" onkeyup="showAcciName(this);" onchange="showAcciName(this);">
											<img src="${ctx }/images/bgMarkMustInput.jpg">
										</td>
									</s:else>
									<td class="input" align=center style="width: 10%">
										<select name="prpLacciPersonSex" title="性别" class="input">
											<option value="0" <c:if test="${prpLacciPerson.sex=='0' }">selected</c:if>>
												<s:text name="commonAcci.regist.unknown" />
												<%--未知--%>
											</option>
											<option value="1" <c:if test="${prpLacciPerson.sex=='1' }">selected</c:if>>
												<s:text name="certainLoss.male" />
												<%--男--%>
											</option>
											<option value="2" <c:if test="${prpLacciPerson.sex=='2' }">selected</c:if>>
												<s:text name="certainLoss.female" />
												<%--女--%>
											</option>
										</select>
									</td>
									<td class="input" align=center style="width: 10%">
										<input type=text name="prpLacciPersonAge" title="年齡" class="input" value="${prpLacciPerson.age}">
									</td>
									<td class="input" align=center style="width: 25%">
										<input type=text name="prpLacciPersonIdentifyNumber" title="身份證號" class="input" value="${prpLacciPerson.identifyNumber}">
										<%-- 隐藏被保险人序号 --%>
										<input type=hidden name="prpLacciPersonFamilyNo" value="${prpLacciPerson.familyNo}" class="input" />
									</td>
									<td class="input" align=center style="width: 5%">
										<%@include file="/pages/common/regist/Beneficiary.jsp"%>
									</td>
									<td class="input" align=center style="width: 10%">
										<%@include file="/pages/common/regist/Benerisk.jsp"%>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="general.damageStartDate" />:
			<%--事故时间--%>
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
		<td class="input">
			<c:choose>
				<c:when test="${editType == 'PERFECT'}">
					<rc:rcDate name="prpLregistDamageStartDate" title="事故時間" class="readonly" readonly="true" wdatePicker="false" style="width: 100px" value="${prpLregist.damageStartDate}" />
					<s:text name="regist.prpLregist.date" />
					<input type="text" name="prpLregistDamageStartHour" title="事故小時" class="readonly" readonly="readonly" maxlength="2" style="width: 25px" value="${prpLregist.damageStartHour}" >
					<s:text name="regist.prpLregist.hour" /><%--时--%>
					<input type="text" name="prpLregistDamageStartMinute" title="事故分鐘" class="readonly" readonly="readonly" maxlength="2" style="width: 25px" value="${prpLregist.damageStartMinute}" >
					<s:text name="regist.prpLregist.minute" /><%--分--%>
					<img src="${ctx }/images/bgMarkMustInput.jpg">
				</c:when>
				<c:otherwise>
					<rc:rcDate name="prpLregistDamageStartDate" title="事故時間" class="input" style="width: 100px" value="${prpLregist.damageStartDate}" onchange="flashPage(this);"/>
					<s:text name="regist.prpLregist.date" />
					<input type="text" name="prpLregistDamageStartHour" title="事故小時" class="input" maxlength="2" style="width: 25px" value="${prpLregist.damageStartHour}" onchange="flashPage(this);">
					<s:text name="regist.prpLregist.hour" /><%--时--%>
					<input type="text" name="prpLregistDamageStartMinute" title="事故分鐘" class="input" maxlength="2" style="width: 25px" value="${prpLregist.damageStartMinute}" onchange="flashPage(this);">
					<s:text name="regist.prpLregist.minute" /><%--分--%>
					<img src="${ctx }/images/bgMarkMustInput.jpg">
				</c:otherwise>
			</c:choose>
		</td>
		<td class="title">
			<s:text name="db.prpLregist.damageCode" />:
			<%--出險原因--%>
		</td>
		<td class="input">
			<!-- 文本框改为输入域-->
			<!-- reason: 非车险部新需求-->
			<input type="text" class="codecode" name="prpLregistDamageCode" style="width: 40px" title="出險原因" value="${prpLregist.damageCode}" ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);" onchange="code_CodeChange(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);">
			<input type="text" class="codecode" name="prpLregistDamageName" title="出險原因" style="width: 170px" value="${prpLregist.damageName}" ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeChange(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);">
			<img src="${ctx }/images/bgMarkMustInput.jpg">
			<!--文本框改为输入域-->
		</td>
	</tr>
	<tr>
		<td class="title">
			<%--<s:text name="db.prpLregist.damageAddress" />--%>
			<s:text name="certainLoss.thirdCarLoss.info9" />
			<%--事故地点:--%>
		</td>
		<td class="input" colspan='3'>
			<select name="countryFlag" style="width: 100px" onchange="countryFlag_change(this.options[this.selectedIndex].value)" style="display: none" >
				<option value="0">
					<s:text name="commonAcci.claim.domestic" />
					<%--国内--%>
				</option>
				<option value="1" selected="selected">
					<s:text name="commonAcci.claim.abroad" />
					<%--国外--%>
				</option>
			</select>
			<!-- mantis：CLM0274，處理人員：DP0713，需求單編號：新核心- TA海外突發疾病修改 START-->
			<c:choose>
			    <c:when test="${prpLregist.riskCode == 'TA'}">
			    	<input type=text class="codecode" name="addressCode" style="width:50px" value="${prpLregist.addressCode}" ondblclick="code_CodeSelect(this, 'CountryCode_CTN','0,1','Y','Y');" onkeyup="code_CodeSelect(this, 'CountryCode_CTN','0,1','Y','Y');" onchange="code_CodeSelect(this, 'CountryCode_CTN','0,1','Y','Y');"/>
					<input type=text class="input" name="prpLregistDamageAddress" title="事故地點" style="width:450px" value="${prpLregist.damageAddress}" ondblclick="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onchange="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" />
					<input type=text class="codecode" name="provinceCode" style="display: none" />
					<input type=text class="codecode" name="provinceName" title="選擇省" style="width: 120px;display: none" ondblclick="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onchange="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" />
					<input type=text class="codecode" name="cityCode" style="display: none" />
					<input type=text class="codecode" name="cityName" title="選擇市" style="width: 120px;display: none" ondblclick="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onkeyup="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onchange="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" />
					<!-- input type=text name="prpLclaimDamageAddress" title="事故地點" class="input" style="display: none" value="${prpLclaim.damageAddress}" onclick="showProvinceCity(this,'countryCName','cityName');"-->
			    </c:when>
				<c:otherwise>
					<input type=text class="codecode" name="addressCode" style="display: none"/>
					<input type=text class="codecode" name="countryCode" style="display: none" />
					<input type=text class="codecode" name="countryCName" title="選擇地域名" style="width:80px" ondblclick="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" onchange="code_CodeSelect(this, 'CountryCode_CTN','-1,0','Y','N');" />
					<input type=text class="codecode" name="provinceCode" style="display: none" />
					<input type=text class="codecode" name="provinceName" title="選擇省" style="width: 80px;display: none" ondblclick="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onchange="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" />
					<input type=text class="codecode" name="cityCode" style="display: none" />
					<input type=text class="codecode" name="cityName" title="選擇市" style="width: 80px;display: none" ondblclick="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onkeyup="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onchange="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" />
					<input type=text name="prpLregistDamageAddress" title="事故地點" Class="input" style="width: 300px" value="${prpLregist.damageAddress}" onclick="showProvinceCity(this,'countryCName','cityName');">
					
				</c:otherwise>
			</c:choose>
			<!-- mantis：CLM0274，處理人員：DP0713，需求單編號：新核心- TA海外突發疾病修改 END-->
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="prpLregist.damageTypeCode" />:
			<%--事故类型--%>
		</td>
		<td class="input">
			<input type="text" name="prpLregistDamageTypeCode" class="codecode" style="width: 20%" title="事故類型" value="${prpLregist.damageTypeCode}" ondblclick="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y','0000');" onkeyup="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y','0000');" onchange="code_CodeSelect(this, 'DamageTypeCode','0,1','Y','Y','0000');">
			<input type=text name="prpLregistDamageTypeName" class="codecode" style="width: 60%" title="事故類型" value="${prpLregist.damageTypeName}" ondblclick="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N','0000');" onkeyup="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N','0000');" onchange="code_CodeSelect(this, 'DamageTypeCode','-1,0','Y','N','0000');">
			<img src="${ctx }/images/bgMarkMustInput.jpg">
		</td>
		<td class="title">
			<s:text name="db.prpLperson.currency" />:
			<%--币别--%>
		</td>
		<td class="input">
			<input type="text" name="prpLregistEstiCurrency" value="${prpLregist.estiCurrency}" class="readonly" readonly style="width: 30%" title="幣別">
			<input type=text name="prpLregistEstiCurrencyName" class="readonly" readonly style="width: 60%" title="幣別" value="${prpLregist.estiCurrencyName}">
		</td>
	</tr>
	<input type="hidden" name="prpLregistEstimateLoss" title="預計給付金額" Class="input" style="width: 80px" value="<fmt:formatNumber value='${prpLregist.estimateLoss}' pattern='#'/>">
	<input type="hidden" name="prpLregistEstimateFee" title="預計費用金額" Class="input" style="width: 80px" value="<fmt:formatNumber value='${prpLregist.estimateFee}' pattern='#'/>">