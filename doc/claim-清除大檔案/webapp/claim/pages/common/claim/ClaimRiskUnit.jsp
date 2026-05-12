<%--
****************************************************************************
* DESC       ：指定危险单位界面()，
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@	page contentType="text/html; charset=GBK" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<c:if test="${empty requestScope.display}">
	<input type="hidden" name="prpLdangerRiskSumPaid" value="" />
	<input type="hidden" name="prpLdangerRiskSumClaim" value="" />
</c:if>
<table class="common" width="100%" align="center">
	<tr class=mline>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,CompensateText)">
			<s:text name="claim.dangerousUnitInfo" />
			<%--危险单位信息--%>
			<br>
			<table class="common" align="left" id="CompensateText" style="display: none" cellspacing="1">
				<tbody>
					<tr class="common">
						<td class="subformtitle" width="10%">
							<s:text name="claim.dangeSerialNum" />
						</td>
						<%--危险单位序号--%>
						<td class="subformtitle" width="20%">
							<s:text name="claim.dangerUnitDescription" />
						</td>
						<%--危险单位描述--%>
						<td class="subformtitle" width="30%">
							<s:text name="claim.addressDescripte" />
						</td>
						<%--地址描述--%>
					</tr>
					<c:forEach items="${requestScope.ReinsDangerUnitCollection}" var="reinsDangerUnit" varStatus="stat">
						<%--最後一个对象，需要设置，要不forEach外围取不到*--%>
						<c:if test="${stat.last}">
							<c:set var="reinsDangerUnitLast" value="${reinsDangerUnit}" scope="page" />
						</c:if>
						<tr class="common">
							<td width="10%" align=center>
								<input class="input" type=hidden name="prpLdangerDangerNo" value="${reinsDangerUnit.dangerNo}" />
								<c:out value='${reinsDangerUnit.dangerNo}' />
							</td>
							<td width="20%">
								<input class="input" type=hidden name="prpLdangerUnitDesc" value="${reinsDangerUnit.dangerDesc}" />
								<c:out value='${reinsDangerUnit.dangerDesc}' />
							</td>
							<td width="30%">
								<input class="input" type=hidden name="prpLdangerAddressName" value="${reinsDangerUnit.addressName}" />
								<c:out value='${reinsDangerUnit.addressName}' />
							</td>
						</tr>
					</c:forEach>
					<c:if test="${not empty pageScope.reinsDangerUnitLast}">
						<tr class="common">
							<input class="input" type=hidden name="prpLdangerPolicyNo" value="${reinsDangerUnitLast.policyNo}" />
							<td colspan="3">
								<c:choose>
									<c:when test="${empty requestScope.prpLcompensate.insuredName}">
										<input class="button" type="button" value="<s:text name='button.shareTrial.value' />" onclick="startTrailClaim()">
										<%--分摊试算--%>
									</c:when>
									<c:otherwise>
										<input class="button" type="button" value="<s:text name='button.shareTrial.value' />" onclick="startTrailCompensate()">
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</td>
	</tr>
</table>