<%--
****************************************************************************
* DESC       ：显示立案的险别估损金额页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-03-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>

<%@ page import="com.sinosoft.claim.common.ConstantCodes" %>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html xmlns:mpc>
<head>
  <title>調整估損金額</title>
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
  <%-- 标签页样式 --%>
  <jsp:include page="/behaviors/MpcStyle.jsp" />
	   <script type="text/javascript">
			//mpc调整
			$(function(){
			     initWindowNoBtn();
		         $(window).resize(function(){
					initWindowNoBtn();
		         });
			})
	   </script>
</head>
<body class="interface" onload="initPage();oMPC.style.visibility='visible'">
	<DIV id="mainLayer" class="mainLayerNoBtn">
		<form name="fm" action="/claim/modifySumClaim.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
			<input type="hidden" name="editType" value="modifySave">
			<!-- //mantis：CLM0035 ，處理人員：DP0706，需求單編號：CLM0035調整估損金額功能判斷是否超出限額  -->
			<input type="hidden" name="gradeLevel" value="${gradeLevel}"/>
			<mpc:container ID="oMPC">
				<mpc:page ID="tabMain" TABTITLE="<s:text name="claim.gusunInfo" />" TABTEXT="<s:text name="claim.gusunInfo" />">
					<%--立案估损基本信息 --%>
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<table border="0" align="center" cellpadding="4" cellspacing="1" class="title" style="width: 100%">
								<tr>
									<td colspan="4" class="formtitle">
										<s:text name="claim.gusunInfo" />
									</td>
									<%--立案估损基本信息 --%>
								</tr>
							</table>
							<table class=subtable cellpadding="0" cellspacing="1">
								<tr>
									<td>
										<table class=common cellpadding="1" cellspacing="1">
											<tr>
												<td class="left">
													<s:text name="check.claimNum" />
												</td>
												<%--赔案号 --%>
												<td class="right">
													<input type=text name="prpLclaimClaimNo" title="賠案號碼" class="readonly" readonly="true" value="${prpLclaim.claimNo}">
												</td>
												<td class="left">
													<s:text name="prompt.queRegist.RegistNo" />
												</td>
												<%--报案号 --%>
												<td class="right">
													<input type=text name="prpLclaimRegistNo" title="備案號碼" class="readonly" readonly="true" value="${prpLclaim.registNo}">
												</td>
												<TD class="left"></td>
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
													<s:text name="db.prpCmain.policyNo" />
												</td>
												<%-- 保单号--%>
												<td class="right">
													<input type=text name="policyno" class="readonly" readonly="true" value="${prpLclaim.policyNo}">
												</td>
												<td class="left">
													<s:text name="regist.prpLregist.insuranceTime" />
												</td>
												<%--保险期间 --%>
												<td class="right" colspan='3'>
													<%--<input type=text name="prpLclaimStartDate" title="起保日期" class="readonly" style="width:80px" readonly="true"  value="${prpLclaim.startDate}"><s:text name="modifySumClaim.comeEffect" />--%>
													<%-- 零时起至--%>
													<rc:rcDate name="prpLclaimStartDate" title="起保日期" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.startDate}" />
													<s:text name="modifySumClaim.comeEffect" />
													<%-- <input type=text name="prpLclaimEndDate"   title="終保日期" class="readonly" style="width:80px" readonly="true"   value="${prpLclaim.endDate}"><s:text name="modifySumClaim.hourEnd" />--%>
													<%--二十四时止 --%>
													<rc:rcDate name="prpLclaimEndDate" title="終保日期" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.endDate}" />
													<s:text name="modifySumClaim.hourEnd" />
												</td>
											</tr>
											<tr>
												<td class="left">
													<s:text name="db.prpLregist.insuredName" />
												</td>
												<%--被保险人 --%>
												<td class="right">${prpLclaim.insuredName}</td>
												<td class="left">
													<s:text name="db.prpDrate.currency" />
												</td>
												<%-- 币别--%>
												<td class="right">
													<input class="readonly" readonly name="prpLclaimCurrencyName" value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>">
													<input class="readonly" type=hidden name="prpLclaimCurrency" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
													<input class="readonly" type=hidden name="prpLclaimPolicyCurrency">
												</td>
												<td class="left">
													<s:text name="db.prpLloss.amount" />
												</td>
												<%--保险金额 --%>
												<td class="right">
													<input class="readonly" name="prpLclaimSumAmount" readonly="true" value="<fmt:formatNumber pattern='#' value='${prpLclaim.sumAmount}'/>">
													<input type="hidden" name="prpLclaimSumPremium" readonly="true" value="<fmt:formatNumber pattern='#' value='${prpLclaim.sumPremium}'/>">
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
													<s:text name="modifySumClaim.accidentTime" />
												</td>
												<%--事故时间 --%>
												<td class="right">
													<%--<input type=text name="prpLclaimDamageStartDate" title="事故時間" class="readonly" readonly maxlength="10" style="width:80px" value="${prpLclaim.damageStartDate}"> --%>
													<%--<s:text name="regist.prpLregist.date" />日 --%>
													<rc:rcDate name="prpLclaimDamageStartDate" title="事故時間" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${prpLclaim.damageStartDate}" />
													<input type="hidden" name="damageStartDate" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${prpLclaim.damageStartDate}'/>">
													<input type="hidden" name="damageStartHour" value="<c:out value='${prpLclaim.damageStartHour}'/>">
													<input type="hidden" name="familyno" value="${requestScope.familyno}">
												</td>
												<td class="left">
													<s:text name="modifySumClaim.caseTotalLossAmount" />
												</td>
												<%--案件估损金额合计 --%>
												<td class="right">
													<input type=text name="prpLclaimSumClaim" title="估損金額" Class="readonly" readonly value="<fmt:formatNumber pattern='#' value='${prpLclaim.sumClaim}'/>">
												</td>
												<td class="left"></td>
												<td class="right"></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</DIV>
					</CENTER>
				</mpc:page>
				<mpc:page ID="tabMain" TABTITLE="<s:text name="claim.amountInsurLossInfo" />" TABTEXT="<s:text name="claim.amountInsurLossInfo" />">
					<%--险别估损金额信息 --%>
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<%-- 险别估损金额信息 --%>
							<input type="hidden" name="prpLclaimRiskCode" value="${prpLclaim.riskCode}" />
							<input type="hidden" name="prpLdangerRiskSumClaim" value="${prpLclaim.sumClaim}" />
							<input type="hidden" name="prpLclaimEndorseNo" value="${prpLclaim.endorseNo }">
<%--						<input type="hidden" name="damageStartDate" value="${prpLclaim.damageStartDate}">--%>
							<c:choose>
								<c:when test="${RiskType=='D'}">
									<script src="${ctx }/pages/common/modifySumClaim/js/DAAClaimEditDWR.js"></script>
									<script src="${ctx }/pages/common/modifySumClaim/js/DAAClaimEditNew.js"></script>
									<input type="hidden" name='indemnityDuty' description="事故责任" value="${prpLclaim.indemnityDuty}">
									<input type="hidden" name='prpLclaimIndemnityDutyRate' description="责任比例" value="${prpLclaim.indemnityDutyRate}">
									<%@include file="/pages/common/modifySumClaim/DAAClaimLossEdit.jsp"%>
									<%-- 不计免赔率信息 --%>
									<%@include file="/pages/common/modifySumClaim/DAAClaimExceptDeductibleRateEdit.jsp"%>
								</c:when>
								<c:when test="${RiskType=='Q'}"><!-- 火險 -->
									<script src="${ctx }/pages/common/modifySumClaim/js/PropClaimEdit.js"></script>
									<%@include file="/pages/common/modifySumClaim/PropClaimLossEdit.jsp"%>
								</c:when>
								<c:when test="${RiskType=='G'}"><!-- 工程險 -->
									<script src="${ctx }/pages/common/modifySumClaim/js/PropClaimEdit.js"></script>
									<%@include file="/pages/common/modifySumClaim/GAAClaimLossEdit.jsp"%>
								</c:when>
								<c:when test="${RiskType=='Z'}"><!-- 責任險 -->
									<script src="${ctx }/pages/common/modifySumClaim/js/PropClaimEdit.js"></script>
									<%@include file="/pages/common/modifySumClaim/LiabClaimLossEdit.jsp"%>
								</c:when>
								<c:when test="${RiskType=='E'}"><!-- 傷害險 -->
									<script src="${ctx }/pages/common/modifySumClaim/js/PropClaimEdit.js"></script>
									<%@include file="/pages/common/modifySumClaim/AcciClaimLossEdit.jsp"%>
								</c:when>
								<c:otherwise>
									<script src="${ctx }/pages/common/modifySumClaim/js/PropClaimEdit.js"></script>
									<%@include file="/pages/common/modifySumClaim/ClaimLossEdit.jsp"%>
								</c:otherwise>
							</c:choose>
						</DIV>
					</CENTER>
				</mpc:page>
				<mpc:page ID="tabMain" TABTITLE="<s:text name="claim.dangerousUnitInfo" />" TABTEXT="<s:text name="claim.dangerousUnitInfo" />">
					<%--危险单位信息 --%>
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<%-- 1.指定危险单位信息 --%>
							<%@include file="/pages/common/claim/ClaimRiskUnit.jsp"%>
						</DIV>
					</CENTER>
				</mpc:page>
			</mpc:container>
			<table id="btnCommon" class="common">
				<tr>
					<td align="center">
						<input type="button" name=buttonSaveFinishSubmit class='button' value='<s:text name="form.save" />' onclick="submitform();">
						<%--保存 --%>
						<input type="button" name=buttonBack class='button' value='<s:text name="prompt.back" />' onclick="back();">
						<%--返回 --%>
						<script language="javascript">
							function back() {
								fm.action = "/claim/modifySumClaim.do?editType=back";
								fm.submit();
							}
							function submitform() {
								//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
								if (!validateForm(fm, 'ClaimLoss_Data')) {
									return false;
								}
								if (!checkLoss()) {
									return false;
								}
								for ( var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
									fm.prpLclaimLossFeeCategory[j].disabled = false;
									fm.prpLclaimLossLossFeeType[j].disabled = false;
								}
								fm.submit();
							}
							//mantis：CLM0035 ，處理人員：DP0706，需求單編號：CLM0035調整估損金額功能判斷是否超出限額START
							// gradeLevel規則如下
							// 0 = 003/009(理賠助理/部門理賠科長)&comcode =00(總公司)
							// 1 = 003/009(理賠助理/部門理賠科長)&comcode !=00(非公司)
							// 2 = 005(一般理賠人員)
							function checkLoss() {
								var sum = 0;
								var limitAmt = 1000000;
								for ( var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
									if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
										//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
										errorMessage("第" + j + "條估損金額中幣别不能為空!");
										//fm.prpLclaimLossCurrency[j].focus();
										return false;
									}
									if (isEmptyField(fm.prpLclaimLossKindCode[j])) {
										//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
										errorMessage("第" + j + "條估損金額中險别代碼不能為空!");
										//fm.prpLclaimLossKindCode[j].focus();
										return false;
									}
									if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
										//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
										errorMessage("第" + j + "條估損金額中金額不能為空!");
										//fm.prpLclaimLossSumClaim[j].focus();
										return false;
									}else{
										//gradeLevel != 0 非(理賠助理/部門理賠科長)&comcode =00(總公司)
										if(fm.gradeLevel.value != '0' && Number(fm.prpLclaimLossSumClaim[j].value) > limitAmt){
											//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
											errorMessage("第" + j + "條估損金額中金額超過100萬元，僅能由總公司覆核人員進行修改!");
											return false;
										}
										sum += Number(fm.prpLclaimLossSumClaim[j].value);
									}
									if(${RiskType=='D'}){
										var kindCode = fm.prpLclaimLossKindCode[j].value;
										var feeCategory = fm.prpLclaimLossFeeCategory[j].value;
										if(kindCode == "31" && feeCategory != "M" && feeCategory != "H" && feeCategory != "D"){
											//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
											errorMessage("第" + j + "條估損金額中31險別的範圍只能是“醫療”、“失能”或“死亡”！");
											return false;
										} else if(kindCode == "32" && feeCategory != "C" && feeCategory != "G" && feeCategory != "O"){
											//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
											errorMessage("第" + j + "條估損金額中32險別的範圍只能是“車損”、“物損”或“其他”！");
											return false;
										}
									}
								}
								
								//分公司人員調整權限案件估損金額不可大於100萬
								//gradeLevel != 0 非(理賠助理/部門理賠科長)&comcode =00(總公司)
								if(fm.gradeLevel.value != '0' && sum > limitAmt){
									//mantis： CLM0160 ，處理人員：DP0713 ，需求單編號：預估金額100萬卡控問題
									errorMessage("估損合計金額超過100萬元，僅能由總公司覆核人員進行修改!");
									return false;
								}
								
								return true;
							}
							//mantis：CLM0035 ，處理人員：DP0706，需求單編號：CLM0035調整估損金額功能判斷是否超出限額END
						</script>
					</td>
				</tr>
			</table>
		</form>
	</DIV>
</body>
</html>