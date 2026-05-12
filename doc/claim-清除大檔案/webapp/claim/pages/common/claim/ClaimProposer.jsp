<%--
****************************************************************************
* DESC       ：添加驾驶员信息页面
* AUTHOR     ：weishixin
* CREATEDATE ： 2004-03-03
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>

	function afterInsertProposer() {
		setPrpLdriverSerialNo();

		var count = getElementCount("proposerIdentifyNo");
		for ( var i = 0; i < count; i++) {
			if (count != 1) {
				fm.proposerIdentifyNo[i].maxLength = 22;
			}
		}

		setButtonProposerInsertStatus();
	}

	/*
	  删除本条Proposer之後的处理（可选方法）
	 */

	function afterDeleteProposer(field) {
		setPrpLdriverSerialNo();
		setButtonProposerInsertStatus();
	}

	/**
	 * 设置setPrpLdriverSerialNo
	 */
	function setPrpLdriverSerialNo() {
		var count = getElementCount("prpLacciPersonSerialNo");
		for ( var i = 0; i < count; i++) {
			//alert("看看什么时候运行?count="+count+"  i="+i);
			if (count != 1) {
				fm.prpLacciPersonSerialNo[i].value = i;
			}
		}
	}

	/**
	 * 只允许有一个驾驶员
	 */
	function setButtonProposerInsertStatus() {
		var count = getElementCount("proposerName");
		if (count <= 1) {
			fm.buttonProposerInsert.disabled = false;
		} else {
			fm.buttonProposerInsert.disabled = true;
		}
	}
</script>
<table class="common" align="center" width="100%">
	<c:if test="${prpDexch.baseCurrency!=null&&prpDexch.baseCurrency!=''&&prpDexch.baseCurrency!=claimLocalCurrency}">
		<tr>
			<td class="title" colspan=3 style="color: red">
				<s:text name="claim.signCurrencyCase" />
				:
			</td>
			<%--此案件签单币别为--%>
			<td colspan=4>
				<input type=text name="BaseCurrency2" class="readonly" readonly
					style="color: red" value="${prpDexch.baseCurrency}">
			</td>
			<td class="title" colspan=3 style="color: red">
				<s:text name="claim.currentExchangeRate" />
				:
			</td>
			<%--当前兑换率为--%>
			<td colspan=4>
				<input type=text name="ExchRate2" class="readonly" readonly
					style="color: red" value="${prpDexch.baseCurrency}">
			</td>
		</tr>
	</c:if>
	<c:if test="${coinsFlag!=null}">
		<c:choose>
			<c:when
				test="${coinsFlag=='1'||coinsFlag=='3'||coinsFlag=='2'}">
				<tr>
					<td class="title" colspan=14 style="color: red">
						<s:text name="prompt.claim.guaranteeBusiness" />
					</td>
					<%--***共保业务，录入损失时请录入总损失--%>
				</tr>
			</c:when>
		</c:choose>

	</c:if>
</table>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="common">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif"
				name="prpLacciPersonImg" onclick="showPage(this,spanClaimProposer)">
			<s:text name="claim.claimApplicantInfo" />
			<br>
			<%--索赔申请人信息--%>

			<table cellpadding="5" cellspacing="1" class="common"
				id="Proposer_Data" style="display: none">
				<tbody>
					<tr>
						<td style="width: 5%" class=common>
							<div align="left">
								<input class="readonlyNo" readonly name="prpLacciPersonSerialNo"
									description="序号">
							</div>
						</td>
						<td class=common colspan=5 style="width: 91%">
							<table cellpadding="2" cellspacing="1" class="common">
								<tr>
									<!-----索赔人信息start----------------->
									<td class="input" style='width: 11%'>
										<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
										<input name="proposerName" class="input" style="width: 70%"
											maxlength="100" description="索赔申请人姓名">
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="input" style='width: 25%' align="center">
										<input name="proposerIdentifyNumber" class="input"
											style="width: 70%" maxlength=20 description="索赔申请人身份證字號">
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="input" style="width: 20%" align="center">
										<select name="relationCode">
											<option value="1">
												<s:text name="claim.insuredHimself" />
											</option>
											<%--被保险人本人--%>
											<option value="2">
												<s:text name="claim.appointBeneficiary" />
											</option>
											<%--指定受益人--%>
											<option value="3">
												<s:text name="claim.insuredHeir" />
											</option>
											<%--被保险人之继承人--%>
											<option value="4">
												<s:text name="claim.guardianInsur" />
											</option>
											<%--被保险人之监护人--%>
											<option value="5">
												<s:text name="certify.groupClient" />
											</option>
											<%--委托人--%>
										</select>
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="input" style='width: 15%' align="center">
										<input name="proposerPhone" class="common" style="width: 95%"
											maxlength=20 description="联系电话">
									</td>
									<td class="input" style='width: 20%' align="center">
										<input name="proposerAddress" class="common"
											style="width: 95%" maxlength=100 description="通信地址">
										<input type=hidden name="personFamilyNo"
											value="${prpLclaim.familyNo}" class="input" />
										<input type=hidden name="claimNo" value="${prpLclaim.claimNo}"
											class="input" />
										<input type=hidden name="proposerPolicyNo"
											value="${prpLclaimDto.policyNo}" class="input" />

									</td>
								</tr>
							</table>
						</td>
						<!-----索赔人信息end------------------->
						<td class="input" style='width: 4%'>
							<div align="right">
								<input type=button name="buttonProposerDelete" class=smallbutton
									onclick="deleteRow(this,'Proposer')" value="-"
									style="cursor: hand">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<!--======================================================================================-->
			<span id="spanClaimProposer" style="display: none"> <%-- 多行输入展现域 --%>
				<table class=common id="Proposer" cellpadding="5" cellspacing="1">
					<thead>
						<tr class=listtitle>
							<td style="width: 5%">
								<s:text name="regist.prpLregist.serialNo" />
							</td>
							<%--序号--%>
							<td style="width: 10%">
								<s:text name="claim.name" />
							</td>
							<%--姓名--%>
							<td style="width: 25%">
								<s:text name="db.prpLpersonloss.identifyNumber" />
							</td>
							<%--身份证号码--%>
							<td style="width: 20%">
								<s:text name="claim.relationAccident" />
							</td>
							<%--与事故者关系--%>
							<td style="width: 15%">
								<s:text
									name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFPhoneNumber" />
							</td>
							<%--联系电话--%>
							<td style="width: 21%">
								<s:text name="db.prpDcustomer_Idv.linkAddress" />
							</td>
							<%--通信地址--%>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan=6 align="center" class="title">
								<s:text name="prompt.certify.addRemove" />
							</td>
							<%--(按"+"号键增加信息，按"-"号键删除信息)--%>
							<td class="title" colspan=1 align="right">
								<input type="button" value="+" class=smallbutton
									onclick="insertRow('Proposer')" name="buttonProposerInsert"
									style="cursor: hand"><%--只允许有一个驾驶员--%>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:if test="${not empty prpLacciPerson.prpLacciPersonList}">
							<c:forEach var="prpLacciPerson"
								items="${prpLacciPerson.prpLacciPersonList}" varStatus="index">
								<c:if test="${index.index % 2 == 0}">
									<tr class=oddrow>
								</c:if>
								<c:if test="${index.index % 2 != 0}">
									<tr class=oddrow>
								</c:if>
								<td class="input" style="width: 4%">
									<div align="center">
										<input name="prpLacciPersonSerialNo" class="readonlyno"
											readonly="true" value="${prpLacciPerson.id.serialNo}">
									</div>
								</td>
								<td class="common" colspan=5>
									<table cellpadding="5" cellspacing="1" class="common">
										<tr>
											<td class="input" style='width: 10%'>
												<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
												<input name="proposerName" class="input" style="width: 70%"
													maxlength="100" value="${prpLacciPerson.acciName}"
													title="索賠人姓名">
												<img src="/claim/images/bgMarkMustInput.jpg">
											</td>
											<td class="input" style='width: 25%'>
												<input name="proposerIdentifyNumber" class="input"
													style="width: 70%" maxlength=20
													value="${prpLacciPerson.identifyNumber}" title="身份證字號">
												<img src="/claim/images/bgMarkMustInput.jpg">
											</td>
											<td class="input" style="width: 20%">

												<!--select name="prpLacciPerson" property="relationName" -->
												<select name="relationCode">
													<option value="1" <c:if test="${prpLacciPerson.relationCode=='1'}">selected</c:if>>
														<s:text name="claim.insuredHimself" />
													</option>
													<%--被保险人本人--%>
													<option value="2" <c:if test="${prpLacciPerson.relationCode=='2'}">selected</c:if>>
														<s:text name="claim.appointBeneficiary" />
													</option>
													<%--指定受益人--%>
													<option value="3" <c:if test="${prpLacciPerson.relationCode=='3'}">selected</c:if>>
														<s:text name="claim.insuredHeir" />
													</option>
													<%--被保险人之继承人--%>
													<option value="4" <c:if test="${prpLacciPerson.relationCode=='4'}">selected</c:if>>
														<s:text name="claim.guardianInsur" />
													</option>
													<%--被保险人之监护人--%>
													<option value="5" <c:if test="${prpLacciPerson.relationCode=='5'}">selected</c:if>>
														<s:text name="certify.groupClient" />
													</option>
													<%--委托人--%>
												</select>
												<!--  <input type=hidden name="proposerRelation" value="<bean:write name='prpLacciPerson' property='relationCode'/>" class="input" /> -->
												<input type=hidden name="claimNo"
													value="${prpLacciPerson.id.certiNo}" class="input" />
											</td>
											<td class="input" style='width: 15%'>
												<input name="proposerPhone" class="input" style="width: 95%"
													maxlength=20 value="${prpLacciPerson.phone}" title="索賠人電話">
											</td>
											<td class="input" style="width: 20%">
												<input name="proposerAddress" class="input"
													style="width: 80%" maxlength=100
													value="${prpLacciPerson.address}" title="索賠人地址">
											</td>
										</tr>
									</table>
								</td>
								<td class="title" style="width: 4%">
									<div align="center">
										<input type=button name="buttonProposerDelete"
											class=smallbutton onclick="deleteRow(this,'Proposer')"
											value="-" style="cursor: hand">
									</div>
								</td>
	</tr>
	</c:forEach>
	</c:if>
	</tbody>
</table>
</td>
</tr>
</table>
</span>
</table>

