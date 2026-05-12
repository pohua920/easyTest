<%--
****************************************************************************
* DESC       ：不计免赔率信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-03-11
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" style="display: none">
	<!--表示显示多行的-->
	<tr>
		<td class="common" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="ExceptLoss" onclick="showPage(this,exceptLoss1);">
			<s:text name="claim.regardlessFran" />
			<!-- 不计免赔率信息 -->
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
								<input name="exceptDeductibleTreatyName" class="readonly" value="<s:text name="quickCase.avoidCompensate" />">
								<!-- 不计免赔特约 -->
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
								<!-- 受损险别 -->
							</td>
							<td class="centertitle" style="width: 12%" align="center">
								<s:text name="db.prpDrate.kindName" />
								<!-- 险别名称 -->
							</td>
							<td class="centertitle" style="width: 18%" align="center">
								<s:text name="claim.specialArrangeAvoid" />
								<!-- 免赔特约代码 -->
							</td>
							<td class="centertitle" style="width: 18%" align="center">
								<s:text name="claim.specialArrangeName" />
								<!-- 免赔特约名称 -->
							</td>
							<td class="centertitle" style="width: 18%" align="center">
								<s:text name="claim.regardlessFran(%)" />
								<!-- 不计免赔率(%) -->
							</td>
							<td class="centertitle" style="width: 18%" align="center">
								<s:text name="claim.compenPay" />
								<!-- 赔偿金额 -->
							</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="prpLclaimLoss" items="${claimDto.prpLclaimLossList}">
							<c:if test="${prpLclaimLoss.kindCode=='M'}">
								<tr>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleKindCode" class="readonly" value='${prpLclaimLoss.kindCodeSub}'>
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleKindName" class="readonly" value='${prpLclaimLoss.kindNameSub}'>
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleTreaty" class="readonly" value="M">
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleTreatyName" class="readonly" value="<s:text name="quickCase.avoidCompensate" />">
										<!-- 不计免赔特约 -->
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductibleRate" class="readonly" value='${prpLclaimLoss.acciDeductibleRate}'>
									</td>
									<td class="centertitle" style="" align="center">
										<input name="exceptDeductiblePay" class="readonly" value='${prpLclaimLoss.sumClaim}'>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td class="centertitle" style="" align="center">
								<s:text name="claim.total" />
								<!-- 总计 -->
							</td>
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
