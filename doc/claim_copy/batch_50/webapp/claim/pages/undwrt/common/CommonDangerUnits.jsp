<%-- ***************************************************************************
* Description: 拆分危险单位页面
* Author     : LUYANG
* CreateDate : 2005-5-4 20:30
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<tr class=common>
	<td colspan="4">
		<input type=hidden name="hiBusinessNo" value="${wfLog.businessNo}">
		<input type=hidden name="hiBusinessType" value="${wfLog.businessType}">
		<input type="hidden" name="riskUnitFlag" value="1">
		<%-- 是否需要拆分危险单位标志1为允许--%>
		<input type="hidden" name="hiRiskLevel" value="">
		<input type="hidden" name="hiRetCurrency" value="">
		<input type="hidden" name="hiRetentionValue" value="">
		<input type="hidden" name="hiDangerItemKind" value="">
		<input type="hidden" name="hiDangerFlag" value="">
		<input type="hidden" name="hiRiskLevelDesc" value="">
		<input type="hidden" name="includeAccident" value="Y">
		<span id="spanInfo">
			<%--投保单信息 --%>
			<table width=100%>
				<tr>
					<td width="100%">
						<table cellpadding="5" cellspacing="1" class="common" align="center" style="width: 100%">
							<%-- 保单信息 --%>
							<c:if test="${requestScope.prpCmain!=null}">
								<tr class=listtitle>
									<td colspan="4">
										<s:text name="undwrt.PolicyPaymentInformation" />
										<%-- 保单摘要和赔付摘要信息 --%>
									</td>
								</tr>
								<tr>
									<td class=title4>
										<s:text name="db.prpDdbs.riskCode" />：<%--险种 --%>
									</td>
									<td class=input4>${requestScope.prpCmain.riskCode}</td>
									<td class=title4>
										<s:text name="regist.prpLregist.comName" />：<%-- 归属机构 --%>
									</td>
									<td class=input4>${requestScope.prpCmain.comCode}</td>
								</tr>
								<tr>
									<td class=title4>
										<s:text name="certainLoss.prpLcheck.riskCName" />
										<%-- 险种名称 --%>
									</td>
									<td class=input4>${wfLog.riskCodeName}</td>
									<td class=title4>
										<s:text name="undwrt.OrganizationName" />：<%-- 归属机构名称 --%>
									</td>
									<td class=input4>${sessionScope.user.comName}</td>
								</tr>
								<tr>
									<td class=title4>
										<s:text name='db.prpLclaim.policyNo' />：<%-- 保单号 --%>
									</td>
									<td class=input4>${requestScope.prpCmain.policyNo }</td>
									<input type="hidden" name="riskCode" description="险种代码" value="${requestScope.prpCmain.riskCode}">
									<input type="hidden" name="hiClassCode" description="险类代码" value="${requestScope.prpCmain.classCode}">
									<c:choose>
										<c:when test="${requestScope.prpCmain.classCode=='M'}">
											<td class="title4">
												<s:text name="db.prpLclaim.setSailDate" />
											</td>
											<%--开航日期--%>
											<td class="input4">
												<rc:rcDate name="prpLextUnloadDate" title="<s:text name='db.prpLclaim.setSailDate'/>" class="readonly" wdatePicker="false" value="${requestScope.prpCmain.startDate}" />
											</td>
										</c:when>
										<c:otherwise>
											<td class=title4></td>
											<td class=input4>&nbsp;</td>
										</c:otherwise>
									</c:choose>
								</tr>
								<c:if test="${requestScope.prpCmain.classCode=='M'}">
									<tr>
										<td class=title4>
											船名：<%-- 船名 --%>
										</td>
										<td class=input4>
											<input type="text" name="prpLregistShipCName" class="readonly" readonly value="${requestScope.prpLregist.shipCName}">
										</td>
										<td class=title4>
											機型：<%-- 機型 --%>
										</td>
										<td class=input4>
											<input type="text" name="prpLregistShipModel" class="readonly" readonly style="width: 100px" value="${requestScope.prpLregist.shipModel}">
										</td>
									</tr>
									<tr>
										<td class=title4>
											出險原因：<%-- 出險原因 --%>
										</td>
										<td class=input4>
											<input type="text" name="prpLregistDamageName" class="readonly" readonly style="width: 150px" value="${requestScope.prpLregist.damageName}">
										</td>
										<td class=title4>
											出險日期：<%-- 出險日期 --%>
										</td>
										<td class=input4>
											<rc:rcDate name="prpLregistDamageStartDate" class="readonly" wdatePicker="false" title="<s:text name='regist.prpLregist.damageTime'/>" style="width:100px" value="${requestScope.prpLregist.damageStartDate}" />
											<%-- 出险时间 --%>
										</td>
									</tr>
								</c:if>
								<tr>
									<c:choose>
										<c:when test="${wfLog.riskCategory == 'E'}">
											<td class=title4>
												<s:text name="db.prpCmain.appliName" />：<%-- 投保人名称 --%>
											</td>
											<td class=input4>${requestScope.prpCmain.appliName}</td>
										</c:when>
										<c:otherwise>
											<td class=title4>
												<s:text name="db.prpLarrearageadd.insuredname" />：<%-- 被保险人名称 --%>
											</td>
											<td class=input4>${wfLog.insuredName}</td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${wfLog.riskCategory == 'Y'}">
											<td class=title4>
												<s:text name="db.prpCcargoDetail.startDate" />：<%-- 起运日期 --%>
											</td>
										</c:when>
										<c:otherwise>
											<td class=title4>
												<s:text name="regist.prpLregist.insuranceTime" />：<%-- 保险期间 --%>
											</td>
										</c:otherwise>
									</c:choose>
									<td class=input4>
										<rc:rcDate name="startDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${requestScope.prpCmain.startDate}" />
										&nbsp;
										<s:text name="prompt.to" />
										&nbsp;
										<%-- 至 --%>
										<rc:rcDate name="endDate" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${requestScope.prpCmain.endDate}" />
									</td>
								</tr>
								<tr>
									<td class=title4>
										<s:text name="db.view_loan.sumAmount" />：<%-- 总保险金额 --%>
									</td>
									<td class=input4>
										${requestScope.prpCmain.currency}&nbsp;
										<fmt:formatNumber value="${requestScope.prpCmain.sumAmount}" pattern="#" />
									</td>
									<td class=title4>
										<s:text name="db.prpCmain.sumPremium" />：<%-- 总保险费 --%>
									</td>
									<td class=input4>
										${requestScope.prpCmain.currency}&nbsp;
										<fmt:formatNumber value="${requestScope.prpCmain.sumPremium}" pattern="#" />
									</td>
								</tr>
								<c:set var="sumLoss" value="0.00" scope="page" />
								<c:if test="${requestScope.PolicyAbstractInfoDto!=null}">
									<c:set var="sumLoss" value="${requestScope.PolicyAbstractInfoDto.sumLoss}" scope="page" />
								</c:if>
								<c:if test="${param.HandType=='22'&&(wfLog.businessType=='C'||wfLog.businessType=='Y')}">
									<c:forEach var="prpLcharge" items="${requestScope.prplchargeList}" varStatus="stat">
										<c:if test="${stat.count%2!=0}">
											<tr>
										</c:if>
										<td class='title4'>
											<b>${prpLcharge.chargeName}：</b>
										</td>
										<td class='input4'>
											<b><fmt:formatNumber value="${prpLcharge.chargeAmount*prpLcharge.exchRate}" pattern="#" /></b>
										</td>
										<c:if test="${stat.count%2==0}">
											</tr>
										</c:if>
									</c:forEach>
									<c:if test="${(fn:length(requestScope.prplchargeList)+1)%2==0}">
										<td class=title4></td>
										<td class=input4></td>
										</tr>
									</c:if>
									<tr>
										<td class="title4">
											<b><s:text name="undwrt.SndardLoss" />(NTD)：</b>
											<%-- 标的损失 --%>
										</td>
										<td class="input4">
											<b><fmt:formatNumber value="${pageScope.sumLoss}" pattern="#" /></b>
										</td>
										<td class="title4">
											<b><s:text name="undwrt.Total" />(NTD)：</b>
											<%--合计  --%>
										</td>
										<td class="input4">
											<b><fmt:formatNumber value="${PolicyAbstractInfoDto.sumPaid}" pattern="#" /></b>
										</td>
									</tr>
									<c:if test="${wfLog.businessType=='C'}">
										<tr>
											<td class="title4">
												<input class='readonly' readonly type='text' value='<s:text name="undwrt.LossCompensation"/>(NTD)：' title='标的损失赔款之和，不包括费用！'>
												<%-- 标的损失赔款 --%>
											</td>
											<td class="input4">
												<input class="readonly" type=text name="prpLcompensateSumDutyPaid" readonly="true" value="<fmt:formatNumber value='${prpLcompensate.sumDutyPaid}' pattern='#'/>">
											</td>
											<td class="title4">
												<input class='readonly' readonly type='text' value='<s:text name="undwrt.ChargeAmount"/>(NTD)：' title='费用金额之和，不包括赔款金额！'>
												<%-- 费用金额 --%>
											</td>
											<td class="input4">
												<input type=text name="prpLcompensateSumNoDutyFee" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value='${prpLcompensate.sumNoDutyFee}' pattern='#'/>">
											</td>
										</tr>
										<tr>
											<td class="title4">
												<input class='readonly' readonly type='text' value='<s:text name="undwrt.CaseTotal"/>(NTD)：' title='赔款合计与费用之和！'>
												<%-- 本案合计 --%>
											</td>
											<td class="input4">
												<input class="readonly" type=text name="prpLcompensateSumPaid" readonly="true" value="<fmt:formatNumber value='${prpLcompensate.sumPaid}' pattern='#'/>">
											</td>
											<td class="title4">
												<input class='readonly' readonly type='text' value='<s:text name="undwrt.PaymentAmount"/>(NTD)：' title='预付赔款金额之和！'>
												<%-- 已预付赔款金额 --%>
											</td>
											<td class="input4">
												<input type=text name="prpLcompensateSumPrePaid" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value='${prpLcompensate.sumPrePaid}' pattern='#'/>">
											</td>
										</tr>
										<tr>
											<td class="title4">
												<input class='readonly' readonly type='text' value='<s:text name="undwrt.thisLossCompensation"/>(NTD)：' title='标的损失赔款减去已预付赔款！'>
												<%-- 本次标的损失赔款 --%>
											</td>
											<td class="input4">
												<input class="readonly" type=text name="prpLcompensateSumThisPaid" readonly="true" value="<fmt:formatNumber value='${prpLcompensate.sumThisPaid}' pattern='#'/>">
											</td>
											<td class="title4">
												<s:text name="claim.salvage" />(NTD)：<%-- 残值 --%>
											</td>
											<td class="input4">
												<input class="readonly" type=text readonly="true" name="prpLcompensateSumRest" value="<fmt:formatNumber value='${prpLcompensate.sumRest}' pattern='#'/>">
											</td>
										</tr>
										<c:if test="${wfLog.riskCategory == 'D'}">
											<tr>
												<td class="title4">
													<c:if test="${not empty prpLcompensate.accidentType}">
														<c:choose>
															<c:when test="${prpLcompensate.classCode == 'B'}">
																<s:text name="claim.accidentType" />：<%-- 肇責類型 --%>
															</c:when>
															<c:when test="${prpLcompensate.classCode == 'A'}">
																<s:text name="claim.carAccidentType" />：<%-- 車體險肇責類型 --%>
															</c:when>
															<c:otherwise></c:otherwise>
														</c:choose>
													</c:if>
												</td>
												<td class="input4">
													<c:if test="${not empty prpLcompensate.accidentType}">
														<s:select name="prpLcompensateAccidentType" cssStyle="" value="#request.prpLcompensate.accidentType" listKey="key" listValue="value" list="#request.accidentTypeList" disabled="true" />
													</c:if>
												</td>
												<td class="title4">
													<c:if test="${prpLcompensate.classCode == 'A' && not empty prpLcompensate.propAccidentType}">
														<s:text name="claim.propAccidentType" />：<!--  責任險肇責類型 -->
													</c:if>
												</td>
												<td class="input4">
													<c:if test="${prpLcompensate.classCode == 'A' && not empty prpLcompensate.propAccidentType}">
														<s:select name="prpLcompensatePropAccidentType" value="#request.prpLcompensate.propAccidentType" listKey="key" listValue="value" list="#request.accidentTypeList" disabled="true" />
													</c:if>
												</td>
											</tr>
										</c:if>
									</c:if>
								</c:if>
							</c:if>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<table cellpadding="5" cellspacing="1" class="common" align="center" style="width: 100%">
							<tr>
								<td class="title4">
									<s:text name="compensate.recovery" />：<%-- 是否有追偿 --%>
								</td>
								<td class="input4">
									<select name="replevyFlag">
										<option value="0" <c:if test="${requestScope.prpLcompensate.replevyFlag == '0'}"><c:out value="selected" /></c:if>><s:text name="certainLoss.thirdCarLoss.no" /></option>
										<%-- 否 --%>
										<option value="1" <c:if test="${requestScope.prpLcompensate.replevyFlag == '1'}"><c:out value="selected" /></c:if>><s:text name="certainLoss.thirdCarLoss.yes" /></option>
										<%-- 是 --%>
									</select>
								</td>
								<td class="title4">
									<s:text name="title.compensateEdit.speedFlag" />：<%-- 赔款速度  --%>
								</td>
								<td class="input4">
									<c:choose>
										<c:when test="${param.HandType=='query'}">
											<s:select name="prpLcompensateSpeedFlag" value="#request.prpLcompensate.speedFlag" list="#request.speedFlagList" listKey="key" listValue="value" disabled="true"></s:select>
										</c:when>
										<c:otherwise>
											<s:select name="prpLcompensateSpeedFlag" value="#request.prpLcompensate.speedFlag" list="#request.speedFlagList" listKey="key" listValue="value"></s:select>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td class="title4">追償說明：</td>
								<td class="input4" colspan="2">
									<input name="prpLcompensateReplevyRemark" class="common" value="${prpLcompensate.replevyRemark }">
								</td>
								<td class="input4"></td>
							</tr>
							<%--  车险显示文件收集完全日  --%>
							<c:if test="${wfLog.riskCategory == 'D'}">
								<tr>
									<td class="title4">文件收集完全日：</td>
									<td class="input4">
										<rc:rcDate name="prpLcompensateFileReadyDate" format="yyyy-MM-dd HH:mm" value="${prpLcompensate.fileReadyDate }" readonly="true" class="readonly" />
									</td>
									<td class="title4"></td>
									<td class="input4"></td>
								</tr>
							</c:if>
						</table>
					</td>
				</tr>
				<%-- 原始标的信息 --%>
				<tr>
					<td width="100%">
						<table cellpadding="5" cellspacing="1" class="common" align="center" style="width: 100%">
							<tr class=listtitle>
								<td colspan="9">
									<s:text name="dangerUnit.OriginalInformation" />
									<%-- 原始标的信息 --%>
								</td>
							</tr>
							<tr class=common>
								<td>
									<s:text name="regist.prpLregist.serialNo" />
									<%-- 序号 --%>
								</td>
								<td>
									<s:text name="undwrt.Risks" />
								</td>
								<%-- 险别 --%>
								<td>
									<s:text name="undwrt.StandardProject" />
									<%-- 标的项目 --%>
								</td>
								<td>
									<s:text name="regist.prpLregist.itemName" />
									<%-- 标的名称 --%>
								</td>
								<td>
									<s:text name="db.prpLclaimpolicy.currency" />
									<%-- 币别 --%>
								</td>
								<td>
									<s:text name="db.prpDration.amount" />
									<%-- 保额 --%>
								</td>
								<td>
									<s:text name="db.prpDration.premium" />
									<%-- 保费 --%>
								</td>
							</tr>
							<%-- mantis：CLM0182，處理人員： CD078，需求單編號：新核心-車體險進廠維修提示訊息修改 --%>
							<input type=hidden id="itemKindCheck" value="${itemKindCheck}">
							<c:forEach items="${requestScope.prpCitemKindCollection}" var="itemKind" varStatus="index_status">
								<tr class=common>
									<td>
										<input class="formtitle1" name="itemKindNo" readonly value="${index_status.count }">
									</td>
									<td style="width: 321px;">
										<input class="formtitle1" readonly value="${itemKind.kindName}" style="width: 321px;">
										<input type=hidden value="${itemKind.kindCode}">
									</td>
									<td>
										<input class="formtitle1" name="" readonly value="${itemKind.itemCode}">
									</td>
									<td style="width: 321px;">
										<c:set var="itemName" value="${itemKind.itemDetailName }" />
										<c:if test="${itemName==null||itemName=='' }">
											<c:set var="itemName" value="${itemKind.itemName }" />
										</c:if>
										<input class="formtitle1" name="" readonly value="${itemName}" style="width: 321px;">
									</td>
									<td>
										<input class="formtitle1" name="iCurrency" readonly value="${itemKind.currency}">
									</td>
									<c:set var="amountColor" value="" />
									<c:choose>
										<c:when test="${prpLcompensate.isPayForOther=='0' }">
											<c:if test="${itemKind.amount*coinUsCoinsRate<itemKind.hisPaid }">
												<c:set var="amountColor" value='style="background-color: red;" title="賠償金額超過保額"' />
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${itemKind.amount<itemKind.hisPaid }">
												<c:set var="amountColor" value='style="background-color: red;" title="賠償金額超過保額"' />
											</c:if>
										</c:otherwise>
									</c:choose>
									<td ${amountColor }>
										<input class="formtitle1" name="iAmount" readonly value="<fmt:formatNumber value='${itemKind.amount}' pattern='#'/>">
									</td>
									<td>
										<input class="formtitle1" name="iPremium" readonly value="<fmt:formatNumber value='${itemKind.premium}' pattern='#'/>">
										<input type="hidden" name="calculateFlag" value="${itemKind.calculateFlag}">
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</span>
	</td>
</tr>
