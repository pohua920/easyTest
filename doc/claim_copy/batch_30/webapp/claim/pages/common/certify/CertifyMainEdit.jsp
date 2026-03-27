<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 单证收集 ]
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
	//显示保单
	function showPolicy(coreURL, strPolicyNo, strRiskCode) {
		var vURL = coreURL + fm.prpLcertifyCollectRiskCode.value
				+ '/tbcbpg/UIPrPoEn' + fm.prpLcertifyCollectRiskCode.value
				+ 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo='
				+ fm.prpLcertifyCollectPolicyNo.value + '&RiskCode='
				+ fm.prpLcertifyCollectRiskCode.value;
		window.open(vURL,'详细信息','width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
</script>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<table align="center" cellpadding="5" cellspacing="0" class="common">
	<tr>
		<input type="hidden" name="coreURL" value="${core_URL }">
		<c:if test="${editTypeOther!='SHOWTASK'}">
			<td class=button>
				<input class=bigbutton type="button" width="80" height="21" name="messageSave" value="<s:text name='button.claimsProcessingRecords.value' />"
					onclick="openWinSave(fm.RegistNo.value,'${prpLcertifyCollect.policyNo}',fm.riskCode.value,'certi','');">
			</td>
			<%--赔案处理记录--%>
			<%--
     <td><input type="button" name="prpLmessageSave" value="赔案处理记录" onclick="openWinSave()"></td> 
     <td><input type="button" name="prpLmessageView" value="查看留言" onClick="openWinQuery()"></td> 
     --%>
			<td class=button>
				<s:if test="(#request.editType=='ADD'||#request.editType=='EDIT') && #request.prpLcertifyCollect.status!=4">
					<input class=button type="button" name="buttonCertifyDirect" value="<s:text name='button.stateClaim.value' />" onClick="doCertifyDirect('${prpLcertifyCollect.id.businessNo}','certi');">
				</s:if>
			</td>
			<%--索赔清单--%>
			<%--
			<td class=button>
				<input class=button type="button" name="buttonCertifyDirect" value="<s:text name='button.stateNote.value' />" onClick="certifyDirectList('${prpLcertifyCollect.id.businessNo}','certi');">
			</td>--%>
			<%--索赔须知--%>
		</c:if>
	</tr>
</table>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td class="formtitle">
			<s:text name="certainLoss.prpLcertifyCollect.prpLCertifyCollect" />
		</td>
		<%--单证收集--%>
	</tr>
</table>
<table border="0" align="center" cellpadding="5" cellspacing="1" class="title" style="width: 100%">
	<tr>
		<td class="title">
			<s:text name="db.prpLcheckExt.registNo" />
			:
		</td>
		<%--报案号码--%>
		<td class="input">
			<input type="text" name="RegistNo" class="readonly" readonly="true" style="width: 200px" value="${prpLcertifyCollect.id.businessNo}">
			<input type="button" name="btRegistRelate" value="<s:text name='button.reportInfo.value' />" class='bigbutton' onclick="relateRegist();return false;">
			<%--报案信息--%>
		</td>
		<td class="title">
			<s:text name="db.prpLcheckExt.policyNo" />
			:
		</td>
		<%--保单号码--%>
		<td class="input">
			<input type="text" name="PolicyNo" class="readonly" readonly="true" style="width: 160px" value="${prpLcertifyCollect.policyNo}">
			<!-- 
                <input type="button" name="btPolicyRelate" value="保单信息" class='bigbutton' onclick="showPolicy(fm.coreURL.value,fm.PolicyNo.value);return false;">
                 -->
			<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value' />"
				onclick="backWardPolicy(fm.coreURL.value,fm.PolicyNo.value,fm.prpLcertifyCollectRiskCode.value,fm.prpLclaimDamageStartDate.value);">
			<%--出险时保单信息--%>
		</td>
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
		<input type="hidden" name="prpLclaimDamageStartDate" value="${prpLregist.damageStartDate}">
		<input type="hidden" name="policyNo" value="${prpLcertifyCollect.policyNo }">
		<input type="hidden" name="riskCode" value="${param.riskCode}">
		<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
		<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
		<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
		<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
		<input type="hidden" name="editType" value="${editType}">
		<input type="hidden" name="riskType" value="${riskType}"/>
	</tr>
</table>
<c:if test="${riskType=='E'}">
	<%@include file="/pages/common/certify/CertifyPayeeEdit.jsp"%>
</c:if>
<s:set var="strTitle" value="基本资料" scope="page" />
<table cellpadding="5" cellspacing="1" border="0" class="common">
<s:iterator value="#attr.imageTypeMap" var="imageType">
 <tr>
		<td class="input" style="width: 100%" colspan="3">
			<table cellpadding="5" cellspacing="1" border="0" class="common">
				<tr>
					<td class="centertitle" style="width: 100%" colspan="6">
					<s:property value="#attr.certifyTypeList[#imageType.key]"/><%-- 根据序号获取索赔清单分类名称--%>
				</tr>
				<tr>
					<td class="subformtitle" style="width: 10%">
						<s:text name="certainLoss.prpLcertifyCollect.requireSign" />
					</td>
					<%--需要标志--%>
					<td class="subformtitle" style="width: 60%">
						<s:text name="certainLoss.prpLcertifyCollect.billType" />
					</td>
					<%--清单类型--%>
					<td class="subformtitle" style="width: 10%">
						<s:text name="certainLoss.prpLcertifyCollect.isOnload" />
					</td>
					<%--是否上传--%>
					<td class="subformtitle" style="width: 10%">
						<s:text name="certainLoss.prpLcertifyCollect.read" />
					</td>
					<%--查看--%>
					<td class="subformtitle" style="width: 10%">
						<s:text name="certainLoss.prpLcertifyCollect.onload" />
					</td>
					<%--上传--%>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="input" style="width: 80%">
			<table cellpadding="0" cellspacing="1" border="0" class="title" style="width: 100%">
				<s:set var="imageTypeListSize" value="0" scope="page" />
				<s:set var="strImageList" value="" scope="page" />
				<s:if test="#attr.imageTypeList!=null">
					<s:set var="imageTypeListSize" value="#attr.imageTypeList.size()" scope="page" />
					<s:iterator value="#imageType.value" var="prpDcodeDto">
						<s:set var="alreadyUploadFlag" value="" scope="page" />
						<s:set var="requireUploadFlag" value="" scope="page" />
						<s:if test="#attr.prpLcertifyImg!=null&&#attr.prpLcertifyImg.certifyImgList!=null">
							<s:iterator value="#attr.prpLcertifyImg.certifyImgList" var="prpLcertifyImgDtoTemp">
								<s:if test="#attr.alreadyUploadFlag!='checked'&&#attr.prpLcertifyImgDtoTemp.typeCode==#attr.prpDcodeDto.id.codeCode">
									<s:set var="alreadyUploadFlag" value="'checked'" scope="page" />
								</s:if>
							</s:iterator>
						</s:if>
						<s:if test="#attr.prpLcertifyDirect!=null&&#attr.prpLcertifyDirect.certifyDirectList!=null">
							<s:iterator value="#attr.prpLcertifyDirect.certifyDirectList" var="prpLcertifyDirectDtoTemp">
								<s:if test="#attr.requireUploadFlag!='checked'&&#attr.prpLcertifyDirectDtoTemp.typeCode==#attr.prpDcodeDto.id.codeCode">
									<s:set var="requireUploadFlag" value="'checked'" scope="page" />
									<s:set var="strImageList" value="#attr.strImageList+#attr.prpDcodeDto.getCodeCode()+'@@'+ #attr.prpDcodeDto.getCodeCName() + '|'" scope="page" />
								</s:if>
							</s:iterator>
						</s:if>
						<tr>
							<td class="input" style="width: 10%">
								<input type="checkbox" name="prpLcertifyDirectCheck${prpDcodeDto.id.codeCode }"disabled  ${requireUploadFlag}>
							</td>
							<td class="input" style="width: 60%">
								<input type="text" name="prpLcertifyDirectTypeName${prpDcodeDto.id.codeCode}" class="readonly" readonly="true" value="${prpDcodeDto.codeCName }">
							</td>
							<td class="input" style="width: 10%">
								<input type="checkbox" name="prpLcertifyDirectUploadFlag${prpDcodeDto.id.codeCode}"disabled ${alreadyUploadFlag}>
							</td>
						</tr>
					</s:iterator>
				</s:if>
			</table>
		</td>
		<td class="input" style="width: 10%">
			<input class=button type="button" name="buttonView" value="<s:text name='button.view.value' />" onclick="doUploadFile('show');return false;">
			<%--查看--%>
		</td>
		<s:if test="#request.editTypeOther!='SHOWTASK'">
			<td class="input" style="width: 10%">
				<input class=button type="button" name="buttonUpload" value="<s:text name='button.upload.value' />" onclick="doUploadFile('upload');" <c:if test="${isCase=='Yes' }">disabled</c:if>>
				<%--上传--%>
			</td>
		</s:if>
		<s:else>
			<td class="input" style="width: 10%">
				<input class=button type="button" name="buttonUpload" value="<s:text name='button.upload.value' />" onclick="doUploadFile('upload');" disabled>
			</td>
		</s:else>
	</tr>
	</s:iterator>
</table>
<c:if test="${nodeType!='check'&&nodeType!='certa'&&nodeType!='verif'}">
	<table border="0" cellpadding="5" cellspacing="1" class="common">
		<tr>
			<td class="title" colspan="4" style="width: 100%">
				<s:text name="certify.prpLcertifyCollectContent" />
			</td>
			<%--案件处理意见--%>
		</tr>
		<tr>
			<td class="title" style="text-align: center;" colspan="0" colspan="4">
				<textarea style="wrap: hard" rows="10" cols="60" name="prpLcertifyCollectContent">${prpLcertifyCollect.content}</textarea>
			</td>
		</tr>
	</table>
</c:if>
<table border="0" cellpadding="5" cellspacing="1" class="common">
	<tr>
		<td colspan="1" width="40" class="title"><s:text name="commonAcci.compensate.receiveCustomerTime" /></td>
		<td colspan="1" width="50" class="title">
			<rc:rcDate name="startApplyPayDate" value="${prpLclaim.startApplyPayDate}"/>
		</td>
		<td class="title" style="text-align: left;" colspan="1"></td>
		<td class="title" style="text-align: center;" colspan="8"></td>
	</tr>
	<tr style ="display:none">
		<td colspan="1" width="40" class="title">
			<s:text name="certify.whetherInsure" />
			<%--是否涉及担保--%>
		</td>
		<td colspan="1" width="50" class="title">
			<s:if test="#request.prpLclaim.guaranteeFlag==''||#request.prpLclaim.guaranteeFlag=='0'||#request.prpLclaim.guaranteeFlag=='1'">
				<select name="guaranteeFlag">
					<option value="0" <s:if test="#request.prpLclaim.guaranteeFlag=='0'"> selected="selected"</s:if>>
						<s:text name="certainLoss.thirdCarLoss.no" />
					</option>
					<%--否--%>
					<option value="1" <s:if test="#request.prpLclaim.guaranteeFlag=='1'"> selected="selected"</s:if>>
						<s:text name="certainLoss.thirdCarLoss.yes" />
					</option>
						<%--是--%>
				</select>
			</s:if>
			<s:else>
				<select name="guaranteeFlag" disabled="true">
					<option value="1" <s:if test="#request.prpLclaim.guaranteeFlag=='1'"> selected="selected"</s:if>>
						<s:text name="certainLoss.thirdCarLoss.yes" />
					</option>
						<%--是--%>
					<option value="2" <s:if test="#request.prpLclaim.guaranteeFlag=='2'"> selected="selected"</s:if>>
						<s:text name="certainLoss.thirdCarLoss.yes" />
					</option>
						<%--是--%>
					<option value="3" <s:if test="#request.prpLclaim.guaranteeFlag=='3'"> selected="selected"</s:if>>
						<s:text name="certainLoss.thirdCarLoss.yes" />
					</option>
						<%--是--%>
					<option value="4" <s:if test="#request.prpLclaim.guaranteeFlag=='4'"> selected="selected"</s:if>>
						<s:text name="certainLoss.thirdCarLoss.yes" />
					</option>
						<%--是--%>
					<option value="5" <s:if test="#request.prpLclaim.guaranteeFlag=='5'"> selected="selected"</s:if>>
						<s:text name="certainLoss.thirdCarLoss.yes" />
					</option>
						<%--是--%>
					<option value="6" <s:if test="#request.prpLclaim.guaranteeFlag=='6'"> selected="selected"</s:if>>
						<s:text name="certainLoss.thirdCarLoss.yes" />
					</option>
						<%--是--%>
				</select>
			</s:else>
		</td>
		<td class="title" style="text-align: left;" colspan="1"></td>
		<td class="title" style="text-align: center;" colspan="8"></td>
	</tr>
	<tr>
		<td colspan="1" width="40" class="title">
			<input type="hidden" name="registNo" value="${prpLcertifyCollect.id.businessNo}">
			<s:if test="#request.editTypeOther!='SHOWTASK'">
				<!-- mantis：CLM0288 ，處理人員： DP0713 ，需求單編號：新核心-工程險(CM) 理賠功能開發 -->
				<input class='bigbutton' type='button' name='buttonAccCompensate' value="<s:text name='button.enterPaymentInfo.value' />" onclick="queryUserCompensate(this);">
				<%--录入支付帳户信息--%>
			</s:if>
			<s:else>
				<!-- mantis：CLM0288 ，處理人員： DP0713 ，需求單編號：新核心-工程險(CM) 理賠功能開發 -->
				<input class='bigbutton' type='button' name='buttonAccCompensate' value="<s:text name='button.enterPaymentInfo.value' />" onclick="queryUserCompensate(this);" disabled="disabled">
			</s:else>
		</td>
		<td colspan="1" width="50" class="title"></td>
		<td class="title" style="text-align: left;" colspan="1"></td>
		<td class="title" style="text-align: center;" colspan="8"></td>
	</tr>
</table>
<input type="hidden" name="nodeTypeUpload" value="certi">
<input type="hidden" name="imageTypeListSize" value="${imageTypeListSize }">
<input type="hidden" name="paramString" value="${paramString }">
<input type="hidden" name="remoteUrl" value="${remoteUrl }">
<input type="hidden" name="typeTreeXML" value="${typeTreeXML }">
<input type="hidden" name="paramString_show" value="${paramString_show }">
<input type="hidden" name="remoteUrl_show" value="${remoteUrl_show }">
<script type="text/javascript">
function doUploadFile(uploadType) {
	var url = "";
	if (uploadType != "upload") {
		url = fm.remoteUrl_show.value + "?" + fm.paramString_show.value + "&allowUpload=false&allowModifiedImage=true&bussNo=" + fm.RegistNo.value;
	} else {
		url = fm.remoteUrl.value + "?" + fm.paramString.value + "&allowUpload=true&allowModifiedImage=true&bussNo=" + fm.RegistNo.value;
	}
	var oldAction = fm.action;
	var oldTarget = fm.target;
	fm.action = url;
	fm.target = "fraSubmit";
	fm.submit();
	fm.action = oldAction;
	fm.target = oldTarget;
}
</script>