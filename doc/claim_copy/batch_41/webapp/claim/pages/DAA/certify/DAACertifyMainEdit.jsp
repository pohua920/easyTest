
<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 单证收集 ]
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-02-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td class="formtitle">
			<%-- 单证收集 --%>
			<s:text name="certainLoss.prpLcertifyCollect.prpLCertifyCollect" />
		</td>
	</tr>
</table>
<input type="hidden" name="coreURL" value="${core_URL }">
<table border="0" align="center" cellpadding="5" cellspacing="1" class="title" style="width: 100%">
	<tr>
		<td class="title">
			<%-- 报案号码:--%>
			<s:text name="certainLoss.prpLcertifyCollect.RegistNo" />
		</td>
		<td class="input">
			<input type="text" name="RegistNo" class="readonly" readonly="true" style="width: 220px" value="${prpLcertifyCollect.id.businessNo }">
			<input type="button" name="btRegistRelate" value="<s:text name='button.reportInfo.value' />" class='bigbutton' onclick="relateRegist();return false;">
		</td>
		<%-- 报案信息 --%>
		<td class="title">
			<%-- 保单号码:--%>
			<s:text name="certainLoss.prpLcertifyCollect.PolicyNo" />
		</td>
		<td class="input">
			<input type="text" name="PolicyNo" class="readonly" readonly="true" style="width: 220px" value="${prpLcertifyCollect.policyNo }">
			<input type="button" name="btPolicyRelate" value="<s:text name='button.dangerPolicyInfo.value' />" class='bigbutton'
				onclick="relateBeforePolicyNo('${prpLregist.policyNo}','${prpLregist.riskCode}','${prpLregist.damageStartDate}');">
		</td>
		<%-- 出险时保单信息 --%>
		<c:if test="${prpLregistRPolicyNo!=null}">
			<td class="input">
				<input type=text name="prplCheckPolicyBzNoShow" class="readonly" readonly="true" style="width: 140px" value="${prpLregistRPolicyNo.id.policyNo }">
				<input type="button" name="btPolicyRelate" value="<s:text name='button.mandaInsurInfo.value' />" class='bigbutton'
					onclick="relateBeforePolicyNo('${prpLregistRPolicyNo.id.policyNo}','${prpLregistRPolicyNo.riskCode}','${prpLregist.damageStartDate}');">
			</td>
		</c:if>
		<%-- 强制保单信息 --%>
		<input type="hidden" name="prpLcertifyCollectNoSubmitMsg" class="readonly" readonly="true" value="${prpLcertifyCollect.noSubmitMsg}">
		<input type="hidden" name="prpLcertifyCollectBusinessNo" value="${prpLcertifyCollect.id.businessNo}">
		<input type="hidden" name="prpLcertifyCollectPolicyNo" value="${prpLcertifyCollect.policyNo}">
		<input type="hidden" name="prpLcertifyCollectLossItemCode" value="${prpLcertifyCollect.id.lossItemCode}">
		<input type="hidden" name="prpLcertifyCollectLossItemName" value="${prpLcertifyCollect.lossItemName}">
		<input type="hidden" name="prpLcertifyCollectPicCount" value="${prpLcertifyCollect.picCount}">
		<input type="hidden" name="prpLcertifyCollectStartDate" value="${prpLcertifyCollect.startDate}">
		<input type="hidden" name="prpLcertifyCollectStartHour" value="${prpLcertifyCollect.startHour}">
		<input type="hidden" name="prpLcertifyCollectEndDate" value="${prpLcertifyCollect.endDate}">
		<input type="hidden" name="prpLcertifyCollectEndHour" value="${prpLcertifyCollect.endHour}">
		<input type="hidden" name="prpLcertifyCollectOperatorCode" value="${prpLcertifyCollect.operatorCode}">
		<input type="hidden" name="prpLcertifyCollectCaseFlag" value="${prpLcertifyCollect.caseFlag}">
		<input type="hidden" name="prpLcertifyCollectFlag" value="${prpLcertifyCollect.flag}">
		<input type="hidden" name="prpLcertifyCollectUploadYear" value="${prpLcertifyCollect.uploadYear}">
		<input type="hidden" name="prpLcertifyCollectRiskCode" value="${prpLcertifyCollect.riskCode}">
		<input type="hidden" name="policyNo" value="${policyNo }">
		<input type="hidden" name="riskCode" value="${riskCode}">
		<input type="hidden" name="swfLogFlowID" value="${swfLogFlowID}">
		<input type="hidden" name="swfLogLogNo" value="${swfLogLogNo}">
		<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
		<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
	</tr>
	<!-- 	String nodeType = request.getParameter("nodeType");
	if (!(nodeType.equals("check") || nodeType.equals("certa") || nodeType.equals("verif"))) {
 -->
	<c:if test="${param.nodeType!='check'&&param.nodeType!='certa'&&param.nodeType!='verif'}">
		<table border="0" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td class="title">
					<%-- 收集标志:--%>
					<s:text name="certainLoss.prpLcertifyCollect.collectFlag" />
				</td>
				<td class="input" style="width: 85%" style="valign:middle" colspan="3">
					<input type="radio" value='0' name="collectFlag" onclick="setCollectFlag(this);" <c:if test="${prpLcertifyCollect.collectFlag=='0'}">checked="checked"</c:if>>
					<%-- 不齐全--%>
					<s:text name="certainLoss.prpLcertifyCollect.noAll" />
					<input type="radio" value='1' name="collectFlag" onclick="setCollectFlag(this);" <c:if test="${prpLcertifyCollect.collectFlag=='1' }">checked="checked"</c:if>>
					<%-- 齐全 --%>
					<s:text name="certainLoss.prpLcertifyCollect.all" />
				</td>
			</tr>
			<s:set var="cltThirdCarFlagSize" value="0" scope="page" />
			<s:if test="#attr.prpLcertifyCollect.cltThirdCarFlag!=null&&#attr.prpLcertifyCollect.cltThirdCarFlag.length()>0">
				<s:set var="cltThirdCarFlagSize" value="#attr.prpLcertifyCollect.cltThirdCarFlag.length()" scope="page" />
			</s:if>
			<c:if test="${thirdPartyList!=null}">
				<c:forEach items="${thirdPartyList}" var="prpLthirdParty" varStatus="partyStatus">
					<c:if test="${prpLthirdParty.insureCarFlag!=null&&prpLthirdParty.insureCarFlag=='1'&&prpLthirdParty.id.serialNo==1}">
						<tr>
							<td class="title" style="width: 50%">
								<%-- 主车收集标志(车牌号码:--%>
								<s:text name="certainLoss.prpLcertifyCollect.cltInsureCarFlag" />
								${prpLthirdParty.licenseNo}):
							</td>
							<td class="input" style="width: 50%" style="valign:middle" colspan="3">
								<input type="radio" name="cltInsureCarFlag" value="0" <c:if test="${prpLcertifyCollect.cltInsureCarFlag=='0'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
								<%-- 不齐全--%>
								<s:text name="certainLoss.prpLcertifyCollect.noAll" />
								<input type="radio" name="cltInsureCarFlag" value="1" <c:if test="${prpLcertifyCollect.cltInsureCarFlag=='1' }">checked="checked"</c:if> onclick="setCollectFlag(this);" />
								<%-- 不齐全--%>
								<s:text name="certainLoss.prpLcertifyCollect.all" />
							</td>
						</tr>
					</c:if>
					<c:if test="${!(prpLthirdParty.insureCarFlag!=null&&prpLthirdParty.insureCarFlag=='1'&&prpLthirdParty.id.serialNo==1)}">
						<tr>
							<td class="title" style="width: 50%">
								<%-- 三者车收集标志(车牌号码:--%>
								<s:text name="certainLoss.prpLcertifyCollect.cltThirdCarFlag" />
								${prpLthirdParty.licenseNo}):
							</td>
							<td class="input" style="width: 50%" style="valign:middle" colspan="3">
								<c:if test="${cltThirdCarFlagSize>=partyStatus.index}">
									<input type="radio" name="cltThirdCarFlag${partyStatus.index }" value="0" <c:if test="${fn:substring(cltThirdCarFlag,partyStatus.index,partyStatus.index+1)=='0'}">checked</c:if>
										onclick="setCollectFlag(this);">
									<%-- 不齐全--%>
									<s:text name="certainLoss.prpLcertifyCollect.noAll" />
									<input type="radio" name="cltThirdCarFlag${partyStatus.index }" value="1" <c:if test="${fn:substring(cltThirdCarFlag,partyStatus.index,partyStatus.index+1)=='1'}">checked</c:if>
										onclick="setCollectFlag(this);">
									<%-- 齐全 --%>
									<s:text name="certainLoss.prpLcertifyCollect.all" />
								</c:if>
								<c:if test="${cltThirdCarFlagSize<partyStatus.index}">
									<input type="radio" name="cltThirdCarFlag${partyStatus.index }" value="0" checked onclick="setCollectFlag(this);">
									<%-- 不齐全--%>
									<s:text name="certainLoss.prpLcertifyCollect.noAll" />
									<input type="radio" name="cltThirdCarFlag${partyStatus.index }" value="1" onclick="setCollectFlag(this);">
									<%-- 齐全 --%>
									<s:text name="certainLoss.prpLcertifyCollect.all" />
								</c:if>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
			<input type="hidden" name="cltThirdCarCount" value="${fn:length(thirdPartyList) }">
			<tr>
				<td class="title">
					<%-- 人伤收集标志:--%>
					<s:text name="certainLoss.prpLcertifyCollect.cltPersonFlag" />
				</td>
				<td class="input" style="width: 85%" style="valign:middle" colspan="3">
					<input type="radio" name="cltPersonFlag" value="0" <c:if test="${prpLcertifyCollect.cltPersonFlag=='0'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
					<%-- 不齐全--%>
					<s:text name="certainLoss.prpLcertifyCollect.noAll" />
					<input type="radio" name="cltPersonFlag" value="1" <c:if test="${prpLcertifyCollect.cltPersonFlag=='1'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
					<%-- 不齐全--%>
					<s:text name="certainLoss.prpLcertifyCollect.all" />
				</td>
			</tr>
			<tr>
				<td class="title">
					<%-- 物损收集标志:--%>
					<s:text name="certainLoss.prpLcertifyCollect.cltPropFlag" />
				</td>
				<td class="input" style="width: 85%" style="valign:middle" colspan="3">
					<input type="radio" name="cltPropFlag" value="0" <c:if test="${prpLcertifyCollect.cltPropFlag=='0'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
					<%-- 不齐全--%>
					<s:text name="certainLoss.prpLcertifyCollect.noAll" />
					<input type="radio" name="cltPropFlag" value="1" <c:if test="${prpLcertifyCollect.cltPropFlag=='1'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
					<%-- 不齐全--%>
					<s:text name="certainLoss.prpLcertifyCollect.all" />
				</td>
			</tr>
			<c:if test="${strRiskCode=='RISKCODE_DAZ'}">
				<tr>
					<td class="title">
						<%-- 强制保险收集标志:--%>
						<s:text name="certainLoss.prpLcertifyCollect.compelFlag" />
					</td>
					<td class="input" style="width: 85%" style="valign:middle" colspan="3">
						<input type="radio" name="compelFlag" value="0" <c:if test="${prpLcertifyCollect.compelFlag=='0'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
						<%-- 不齐全--%>
						<s:text name="certainLoss.prpLcertifyCollect.noAll" />
						<input type="radio" name="compelFlag" value="1" <c:if test="${prpLcertifyCollect.compelFlag=='1'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
						<%-- 不齐全--%>
						<s:text name="certainLoss.prpLcertifyCollect.all" />
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="title">
					<%-- 盗抢收集标志:--%>
					<s:text name="certainLoss.prpLcertifyCollect.cltCarLossFlag" />
				</td>
				<td class="input" style="width: 85%" style="valign:middle" colspan="3">
					<input type="radio" name="cltCarLossFlag" value="0" <c:if test="${prpLcertifyCollect.cltCarLossFlag=='0'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
					<%-- 不齐全--%>
					<s:text name="certainLoss.prpLcertifyCollect.noAll" />
					<input type="radio" name="cltCarLossFlag" value="1" <c:if test="${prpLcertifyCollect.cltCarLossFlag=='1'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
					<%-- 不齐全--%>
					<s:text name="certainLoss.prpLcertifyCollect.all" />
				</td>
			</tr>
			<tr style='display: none'>
				<td class="title">
					<%-- 全损收集标志:--%>
					<s:text name="certainLoss.prpLcertifyCollect.cltAllLossFlag" />
				</td>
				<td class="input" style="width: 85%" style="valign:middle" colspan="3">
					<input type="radio" name="cltAllLossFlag" value="0" <c:if test="${prpLcertifyCollect.cltAllLossFlag=='0'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
					<%-- 不齐全--%>
					<s:text name="certainLoss.prpLcertifyCollect.noAll" />
					<input type="radio" name="cltAllLossFlag" value="1" <c:if test="${prpLcertifyCollect.cltAllLossFlag=='1'}">checked="checked"</c:if> onclick="setCollectFlag(this);" />
					<%-- 不齐全--%>
					<s:text name="certainLoss.prpLcertifyCollect.all" />
				</td>
			</tr>
			<tr>
				<td class="title" colspan="4" style="width: 100%">
					<%-- 案件处理意见:--%>
					<s:text name="certainLoss.prpLcertifyCollect.prpLcertifyCollectContent" />
				</td>
			</tr>
			<tr>
				<td class="title" style="text-align: center;" colspan="4">
					<textarea style="wrap: hard" rows="10" cols="60" name="prpLcertifyCollectContent">${prpLcertifyCollect.content}</textarea>
				</td>
			</tr>
		</table>
	</c:if>
	<!--}  -->
	<input type="hidden" name="nodeTypeUpload" value="${param.nodeType}">
	<input type="hidden" name="imageTypeListSize" value="${imageTypeListSize}">
	<%-- 2.单证主信息 --%>
	<%@include file="/pages/DAA/certify/DAAPrpLqualityCheckEdit.jsp"%>
	<%-- 4.报案信息补充说明 --%>
	<%@include file="/pages/DAA/regist/DAARegistExtEdit.jsp"%>