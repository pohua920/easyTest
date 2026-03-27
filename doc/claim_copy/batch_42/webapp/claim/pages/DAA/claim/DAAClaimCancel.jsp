<%--
****************************************************************************
* DESC       ：立案注销/拒赔处理页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-02-01
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<!--立案注销/拒赔处理入口-->
<%-- 页面样式  --%>
<%@ include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx }/pages/DAA/claim/js/DAAClaimEdit.js"></script>
<script language=javascript>
	function submitForm(submitType) {
		if (!validateForm(fm)) {
			return false;
		}
		//判断申请立案注销/拒赔的时候，一定要先选择一个需要进行操作的赔案号码。
		if (fm.editType.value == "ADD") {
			//判断选择的保单号码
			var claimCount = parseInt(fm.txtClaimCount.value);
			if (claimCount > 1) {

				for (i = 0; i < claimCount; i++) {

					if (fm.txtcheckadd[i].checked == true) {
						//设置需要申请注销拒赔的立案号码
						fm.prpLclaimClaimNo.value = fm.txtcheckadd[i].value;
					}
				}
			}
		}
		//alert("申请 "+fm.prpLclaimClaimNo.value)
		fm.submitType.value = submitType;
		// 必须先立案的

		if (fm.prpLclaimClaimNo.value == "") {

			//alert("必须先立案後，才能进行注销拒赔操作！");
			//  return false;
		}

		if (fm.editType.value == "CANCELEDIT" && submitType != '5') {
			if (window.confirm("需要列印拒賠/註銷報告嗎?")) {
				fm.prpLcancelclaimPrintFlag.value = "1";
			}
		}

		if (submitType == '5') {
			if (!window.confirm("請確認要將此註銷拒賠申請進行駁迴?")) {
				return false;
			}
		}

		if (submitType == '4') {
			if (!confirm("案件註銷/拒賠，案件處理流程將自動關閉！")) {
				return false;
			}

		}
		fm.buttonSave.disabled = true;
		fm.buttonCancel.disabled = true;
		fm.submit();
	}
</script>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindowNoBtn();
		$(window).resize(function() {
			initWindowNoBtn();
		});
	})
</script>
</head>
<s:if test="#attr.editType=='CANCELEDIT'">
	<body class=interface onload="initPage();readonlyAllInput();oMPC.style.visibility='visible';">
</s:if>
<s:else>
	<body class=interface onload="initPage();oMPC.style.visibility='visible';">
</s:else>
<form name=fm action="${ctx }/claimCancel.do" method="post" onsubmit="return validateForm(this);">
	<c:if test="${editType == 'ADD' || editType == 'EDIT' || editType == 'CANCEL' || editType == 'CANCELEDIT'}">
		<s:token></s:token>
	</c:if>
	<DIV id="mainLayer" class="mainLayerNoBtn">
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="claim.initCancelreject" />" TABTEXT="<s:text name="claim.initCancelreject" />">
				<%-- 立案注销/拒赔处理 --%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<table cellpadding="5" cellspacing="1" class=common>
							<tr class=listtitle>
								<td colspan="4">
									<s:text name="claim.initCancelreject" />
									<input type="hidden" name="swfLogFlowID" class="common" value="${swfLogFlowID }">
									<input type="hidden" name="swfLogLogNo" class="common" value="${swfLogLogNo }">
									<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
									<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
									<input type="hidden" name="prpLclaimComCode" class="readonly" title="歸屬機構" maxlength="22" readonly="true" value="${prpLclaim.comCode}">
								</td>
								<%-- 立案注销/拒赔处理 --%>
							</tr>
						</table>
						<table class=subtable cellpadding="0" cellspacing="1">
							<td>
								<table class=common cellpadding="1" cellspacing="1">
									<s:set var="CancelEditStyle" value="" scope="page" />
									<s:set var="rowcount" value="0" scope="page" />
									<s:if test="#attr.editType!='CANCELEDIT'&&#attr.editType!='CANCELSHOW'">
										<s:set var="haveCheck" value="'checked=\'checked\''" scope="page" />
										<s:set var="haveCheckdisable" value="'disabled'" scope="page" />
										<s:set var="haveCancled" value="" scope="page" />
										<s:set var="CancelEditStyle" value="'Style=\'display:none\''" scope="page" />
										<s:iterator var="prpLclaim1" value="#attr.prpLclaim.claimList">
											<s:set var="haveCheckdisable" value="" scope="page" />
											<s:set var="haveCancled" value="" scope="page" />
											<s:set var="rowcount" value="%{#attr.rowcount+1}" scope="page" />
											<s:if test="#attr.prpLclaim1.endCaseDate==null||#attr.prpLclaim1.endCaseDate==''">
												<s:set var="haveCheckdisable" value="" scope="page" />
												<s:set var="haveCancled" value="" scope="page" />
											</s:if>
											<tr>
												<td class="left">
													<s:text name="db.prpLclaim.claimNo" />：
												</td>
												<td class="right">
													<input type="hidden" name="selectToCancle" value="">
													<input type="hidden" name="haveCancled" value="${haveCancled }">
													<input type="radio" class="" name="txtcheckadd" style="width: 20px" value='${prpLclaim1.claimNo}' ${haveCheck } ${haveCheckdisable }>
													&nbsp;
													<input type="text" name="prpLclaimClaimNo1" class="readonly" title="賠案號碼" maxlength="22" readonly="true" value="${prpLclaim1.claimNo}">
												</td>
												<td class="left">
													<s:text name="db.prpLclaim.policyNo" />:
												</td>
												<td class="right">
													<input type="text" name="prpLclaimPolicyNo1" style="width: 100%" class="readonly" title="保單號碼" maxlength="22" readonly="true" value="${prpLclaim1.policyNo}">
												<td class="left"></td>
												<td class="right"></td>
											</tr>
											<s:set value="0" var="haveCheck" scope="page" />
										</s:iterator>
									</s:if>
									<input type="hidden" name="txtClaimCount" value="${rowcount }">
									<tr ${CancelEditStyle }>
										<td class="left">
											<s:text name="db.prpLclaim.claimNo" />
										</td>
										<td class="right">
											<input type="text" name="prpLclaimClaimNo" class="readonly" title="立案號碼" maxlength="22" readonly="true" value="${prpLclaim.claimNo}">
										</td>
										<td class="left"></td>
										<td class="right"></td>
										<td class="left"></td>
										<td class="right"></td>
									</tr>
									<tr>
										<td class="left">
											<s:text name="db.prpLclaim.registNo" />：
											<%--备案号码--%>
										</td>
										<td class="right">
											<input type="text" name="prpLclaimRegistNo" class="readonly" title="備案號碼" maxlength="22" readonly="true" value="${prpLclaim.registNo}">
										</td>
										<td class="left">
											<s:text name="claim.fileNumber" />：
										</td>
										<%-- 归档号 --%>
										<td class="right">
											<input type=text name="prpLclaimCaseNo" title="歸檔號碼" class="readonly" readonly="true" maxlength="22" style="width: 140px" value="${prpLclaim.caseNo}">
										</td>
										<td class="left">
											<s:text name="claim.cancelReject" />：
										</td>
										<%-- 注销/拒赔 --%>
										<td class="right">
											<select name="caseType" id="caseType" style="width: 72%">
												<s:if test="#attr.editType=='CANCELEDIT'||#attr.editType=='CANCELSHOW'">
													<c:if test="${prpLclaim.caseType=='0'}">
														<option value="0">
															<s:text name="claim.logOut" />
														</option>
														<%-- 注销 --%>
													</c:if>
													<c:if test="${prpLclaim.caseType=='1'}">
														<option value="1">
															<s:text name="claim.rejectClaim" />
														</option>
														<%-- 拒赔 --%>
													</c:if>
													<c:if test="${prpLclaim.caseType=='3'}">
														<option value="3">
															<s:text name="claim.francLogout" />
														</option>
														<%-- 免赔额注销 --%>
													</c:if>
													<input type="hidden" name="caseType" value="${prpLclaim.caseType}" />
												</s:if>
												<s:else>
													<option value="0">
														<s:text name="claim.logOut" />
													</option>
													<%-- 注销 --%>
													<option value="1">
														<s:text name="claim.rejectClaim" />
													</option>
													<%-- 拒赔 --%>
													<option value="3">
														<s:text name="claim.francLogout" />
													</option>
													<%-- 免赔额注销 --%>
												</s:else>
											</select>
										</td>
									</tr>
									<!--原因：非车险不要以下信息-->
									<%
										request.setAttribute("CLASSCODE_D_A", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_A);
										request.setAttribute("CLASSCODE_D_B", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_B);
									%>
									<s:if test="#attr.com_sinosoft_type==#attr.CLASSCODE_D_A||#attr.com_sinosoft_type==#attr.CLASSCODE_D_B">
										<tr>
											<td class="left">
												<s:text name="certainLoss.thirdCarLoss.prpLthirdCarLicenseNo" />
											</td>
											<%-- 车牌号 --%>
											<td class="right">
												<input type=text name="prpLregistLicenseNo" title="牌照號碼" class="readonly" value="${prpLregist.licenseNo}">
											</td>
											<td class="left">
												<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
											</td>
											<%-- 厂牌型号 --%>
											<td class="right">
												<input type=text name="prpLregistBrandName" title="廠牌型號" class="readonly" value="${prpLregist.brandName}">
											</td>
											<td class="left"></td>
											<td class="right"></td>
										</tr>
									</s:if>
									<tr>
										<td class="left">
											<s:text name="db.view_larrearage.policyNo" />：
										</td>
										<%-- 保单号 --%>
										<td class="right">
											<input type=text name="prpLclaimPolicyNo" title="保單號碼" class="readonly" value="${prpLclaim.policyNo}">
										</td>
										<td class="left">
											<s:text name="regist.prpLregist.damageTime" />：
										</td>
										<%-- 出险时间 --%>
										<td class="right">
											<%--<input type=text name="prpLclaimDealerName" title="出險時間" class="readonly" value="${prpLclaim.damageStartDate}"> --%>
											<rc:rcDate name="prpLclaimDealerName" title="出險時間" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.damageStartDate}" />
										</td>
										<td class="left">
											<s:text name="regist.prpLregist.damageAddress" />：
										</td>
										<%-- 出险地点 --%>
										<td class="right">
											<input type=text name="prpLclaimDamageAddress" title="出險地點" class="readonly" value="${prpLclaim.damageAddress}">
										</td>
									</tr>
									<tr>
										<td class="left">
											<s:text name="certainLoss.prpLcheck.insuredName" />
										</td>
										<%-- 被保险人 --%>
										<td class="right">
											<input type=text name="prpLclaimInsuredName" title="被保險人" class="readonly" value="${prpLclaim.insuredName}">
										</td>
										<td class="left">
											<s:if test="#attr.editType=='CANCELSHOW'">
												<s:text name="claim.operatPeople" />：
												</s:if>
											<%-- 操作人 --%>
											<s:else>
												<s:text name="claim.applicant" />：
												</s:else>
											<%-- 申请人 --%>
										</td>
										<td class="right">
											<input type="hidden" name="prpLclaimDealerCode" title="註銷賠案申請人" class="readonly" value="${prpLclaim.dealerCode}">
											<input type=text name="prpLclaimDealerName" title="註銷賠案申請人" class="readonly" value="${prpLclaim.dealerName}">
										</td>
										<td class="left">
											<s:if test="#attr.editType=='CANCELSHOW'">
												<s:text name="db.prpLclaimStatus.operatedate" />：
												</s:if>
											<%-- 操作时间 --%>
											<s:else>
												<s:text name="claim.applyTime" />：
												</s:else>
										</td>
										<%-- 申请时间 --%>
										<td class="right">
											<%-- <input type=text name="prpLclaimCancelDate" title="申請時間" class="readonly" value="${prpLclaim.cancelDate}">--%>
											<rc:rcDate name="prpLclaimCancelDate" title="申請時間" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.cancelDate}" />
										</td>
									</tr>
								</table>
								<table class=common cellpadding="1" cellspacing="1">
									<tr>
										<td class="right" colspan="6">
											<s:text name="db.prpLclaim.cancelReason" />
										</td>
										<%-- 注销/拒赔原因 --%>
									</tr>
									<tr>
										<td class="input" colspan="6" align="center">
											<textarea name='prpLclaimContext' wrap="hard" rows=15 cols=80 class=common>${prpLclaim.cancelReason}</textarea>
										</td>
									</tr>
								</table>
							</td>
						</table>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<input type=hidden name=submitType value=0 />
					<!--取消按钮-->
					<s:if test="#attr.editType=='CANCELSHOW'">
						<%-- 返回 --%>
						<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="javascript:history.go(-1);">
					</s:if>
					<s:else>
						<!--确定按钮-->
						<input type=button name=buttonSave class='button' value="<s:text name='button.submit.value' />" onClick="submitForm('4');">
						<!--取消按钮-->
						<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="javascript:history.go(-1);">
						<s:if test="#attr.editType=='CANCELEDIT'">
							<%-- 退回申请 --%>
							<input type=button name=buttonBack class='button' value="<s:text name='button.returneApplicate.value' />" onClick="return submitForm('5');">
						</s:if>
					</s:else>
					<input type=hidden name="editType" title="操作類型" class="readonly" value="${prpLclaim.editType}">
					<input type=hidden name="nodeType" title="節點類型" class="readonly" value="<s:property value='#parameters.nodeType'/>">
					<input type=hidden name="businessNo" title="業務號碼" class="readonly" value="<s:property value='#parameters.bussinessNo'/>">
					<input type=hidden name="prpLcancelclaimPrintFlag" title="業務號碼" class="readonly" value='0'>
					<input type="hidden" name="riskCode" title="險種代碼" class="readonly" value="${prpLclaim.riskCode}">
				</TD>
			</TR>
		</TABLE>
	</DIV>
</form>
</body>
</html>
