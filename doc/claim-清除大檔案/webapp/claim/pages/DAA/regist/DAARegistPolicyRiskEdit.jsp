<%--
****************************************************************************
* DESC       ：显示承保险别的页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-03-17
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" width="100%">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butExpandBlue.gif" name="RegistPolicyRiskImg" onclick="showPage(this,RegistPolicyRisk)">
			<s:text name="regist.prpLregist.policyKindCode" />
			<br>
			<%--保单承保险别位置上移动，保单号後，只显示承保险别--%>
			<table class="common" cellpadding="5" cellspacing="1" id="RegistPolicyRisk">
				</tbody>
				<%--报案对象--%>
				<tr>
					<td class="prompttitle">
						<s:text name="regist.prpLregist.serialNo" />
					</td>
					<td class="prompttitle">
						<s:text name="regist.prpLregist.kindCode" />
					</td>
					<td class="prompttitle">
						<s:text name="regist.prpLregist.kindName" />
					</td>
					<%--非车非意非责险要加标的名称--%>
					<%
						request.setAttribute("CLASSCODE_D_A", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_A);
						request.setAttribute("CLASSCODE_D_B", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_B);
					%>
					<c:if test="${prpLregist.classCode != '04' && (prpLregist.classCode != CLASSCODE_D_A&&prpLregist.classCode != CLASSCODE_D_B) && prpLregist.classCode != '06' && prpLregist.classCode != '07'}">
						<td class="prompttitle">
							<s:text name="regist.prpLregist.itemName" />
						</td>
					</c:if>
					<%--添加保额/限额信息--%>
					<td class="prompttitle">
						<s:text name="regist.prpLregist.limitAmount" />
					</td>
					<td class="prompttitle">
							<s:text name="regist.prpLregist.currency" />
					</td>
					<c:if test="${com_sinosoft_forward=='ADDEAA'}">
						<td class="prompttitle">
							<s:text name="regist.prpLregist.itemDetailName" />
						</td>
						<td class="prompttitle">
							<s:text name="regist.prpLregist.currency" />
						</td>
						<td class="prompttitle">
							<s:text name="regist.prpLregist.unitAmount" />
						</td>
						<td class="prompttitle">
							<s:text name="regist.prpLregist.sumQuantity" />
						</td>
						<td class="prompttitle">
							<s:text name="regist.prpLregist.sumPremium" />
						</td>
						<td class="prompttitle">
							<s:text name="regist.prpLregist.sumAmount" />
						</td>
					</c:if>
				</tr>
				<c:forEach var="prpCitemKind" items="${prpCitemKind.prpCitemKindList}" varStatus="indexCitemKind">
					<c:if test="${indexCitemKind.index %2== 0}">
						<tr class=listodd>
					</c:if>
					<c:if test="${indexCitemKind.index %2!= 0}">
						<tr class=listeven>
					</c:if>
					<tr class=common>
						<td>${indexCitemKind.index+1}</td>
						<td>${prpCitemKind.kindCode}</td>
						<td>${prpCitemKind.kindName}</td>
						<%--非车非意非责险要加标的名称--%>
						<c:if test="${prpLregist.classCode != '04'}">
							<c:if test="${prpLregist.classCode != CLASSCODE_D_A&&prpLregist.classCode != CLASSCODE_D_B}">
								<c:if test="${prpLregist.classCode != '06'}">
									<c:if test="${prpLregist.classCode != '07'}">
										<td>
											<%--${prpCitemKind.itemDetailName} --%>
											${prpCitemKind.itemName}
										</td>
									</c:if>
								</c:if>
							</c:if>
						</c:if>
						<%--添加保额/限额信息--%>
						<td>
							<fmt:formatNumber value="${prpCitemKind.amount}" pattern="#" />
						</td>
						<td>${prpCitemKind.currency}</td>
						<c:if test="${com_sinosoft_forward=='ADDEAA'}">
							<td>${prpCitemKindList.itemDetailName}</td>
							<td>${prpCitemKindList.currency}</td>
							<td>
								<fmt:formatNumber value="${prpCitemKindList.unitAmount}" pattern="#" />
							</td>
							<td>${prpCitemKindList.quantity}</td>
							<td>
								<fmt:formatNumber value="${prpCitemKindList.premium}" pattern="#" />
							</td>
							<td>
								<fmt:formatNumber value="${prpCitemKindList.amount}" pattern="#" />
							</td>
						</c:if>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</td>
	</tr>
</table>