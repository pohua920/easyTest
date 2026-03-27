<%--
****************************************************************************
* DESC       ：不计免赔率信息页面
* AUTHOR     ：chenjie
* CREATEDATE ： 2013-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
* ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" style="display: none">
	<!--表示显示多行的-->
	<tr>
		<td class="common" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="ExceptLoss" onclick="showPage(this,exceptLoss1);">
			<s:text name="claim.regardlessFran" />
			<%-- 不计免赔率信息 --%>
			<br> <span style="display: none">
				<table class="common" style="display:" id="exceptLoss1_Data" cellspacing="1" cellpadding="1" style="width:100%">
					<tbody>
						<tr>
							<td class="centertitle" style="" align="center">
								<input name="exceptDeductibleKindCode" class="readonly" value=''>
							</td>
							<td class="centertitle" style="" align="center">
								<input name="exceptDeductibleKindName" class="readonly" value=''>
							</td>
							<td class="centertitle" style="" align="center">
								<input name="exceptDeductibleTreaty" class="readonly" value="M">
							</td>
							<td class="centertitle" style="" align="center">
								<input name="exceptDeductibleTreatyName" class="readonly" value="不计免赔特约">
							</td>
							<td class="centertitle" style="" align="center">
								<input name="exceptDeductibleRate" class="readonly" value=''>
							</td>
							<td class="centertitle" style="" align="center">
								<input name="exceptDeductiblePay" class="readonly" value='0'>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
			<%-- 多行输入展现域 --%>
			<span id="spanlLoss" style="display:">
				<table class="common" id="exceptLoss1" cellspacing="1" cellpadding="1" style="width: 100%">
					<thead>
						<tr>
							<td class="centertitle" style="width: 12%" align="center">
								<s:text name="claim.damagedRisk" />
							</td>
							<%-- 受损险别 --%>
							<td class="centertitle" style="width: 12%" align="center">
								<s:text name="regist.prpLregist.kindName" />
							</td>
							<%-- 险别名称 --%>
							<td class="centertitle" style="width: 18%" align="center">
								<s:text name="claim.specialArrangeAvoid" />
							</td>
							<%-- 免赔特约代码 --%>
							<td class="centertitle" style="width: 18%" align="center">
								<s:text name="claim.specialArrangeName" />
							</td>
							<%--免赔特约名称 --%>
							<td class="centertitle" style="width: 18%" align="center">
								<s:text name="claim.regardlessFran(%)" />
							</td>
							<%-- 不计免赔率(%) --%>
							<td class="centertitle" style="width: 18%" align="center">
								<s:text name="claim.compenPay" />
							</td>
							<%-- 赔偿金额 --%>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty requestScope.exceptLossList}">
							<c:forEach items="${requestScope.exceptLossList}" var="exceptDeductibleRateTemp">
								<tr>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleKindCode" class="readonly" value="${exceptDeductibleRateTemp.kindCode}">
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleKindName" class="readonly" value="${exceptDeductibleRateTemp.kindName}">
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleTreaty" class="readonly" value="M">
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleTreatyName" class="readonly" value="不计免赔特约">
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleRate" class="readonly" value="${exceptDeductibleRateTemp.exceptDeductibleRate}">
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductiblePay" class="readonly" value="<fmt:formatNumber value='${exceptDeductibleRateTemp.exceptDeductibleRatePay}' pattern='#'/>">
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
					<tfoot>
						<tr>
							<td class="centertitle" style="" align="center">
								<s:text name="claim.total" />
							</td>
							<%-- 总计 --%>
							<td class="centertitle" style="" align="center"></td>
							<td class="centertitle" style="" align="center"></td>
							<td class="centertitle" style="" align="center"></td>
							<td class="centertitle" style="" align="center"></td>
							<td class="centertitle" style="" align="center">
								<input name="exceptDeductibleRateAll" class="readonly" value='0'>
							</td>
						</tr>
					</tfoot>
				</table>
			</span>
		</td>
	</tr>
</table>