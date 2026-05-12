<%--
****************************************************************************
* DESC       ：添加领款人信息录入页面[ 单证收集 ]
* AUTHOR     ： wenbin
* CREATEDATE ： 2007-11-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" style="display: none" id="PayeeText_Data" width="100%" cellspacing="1" cellpadding="5">
	<tbody>
		<tr>
			<td>
				<input type="hidden" name="prpLcertifyPayeePayeeTypeName" value="">
				<select name="prpLcertifyPayeePayeeTypeCode">
					<option value=""></option>
					<option value="01">
						<s:text name="account.personal" />
					</option>
					<%--个人--%>
					<option value="02">
						<s:text name="account.group" />
					</option>
					<%--团体--%>
				</select>
			</td>
			<td>
				<input type="hidden" name="prpLcertifyPayeeRelationsName" value="">
				<select name="prpLcertifyPayeeRelationsCode">
					<option value=""></option>
					<option value="01">
						<s:text name="regist.prpLregist.self" />
					</option>
					<%--本人--%>
					<option value="02">
						<s:text name="certify.beneficiary" />
					</option>
					<%--受益人--%>
					<option value="03">
						<s:text name="certify.groupClient" />
					</option>
					<%--委托人--%>
				</select>
			</td>
			<td>
				<input type="text" class="common" name="prpLcertifyPayeePayeeName">
			</td>
			<td>
				<input type="hidden" name="prpLcertifyPayeeLicenseTypeName" value="">
				<select name="prpLcertifyPayeeLicenseTypeCode">
					<option value=""></option>
					<option value="01">
						<s:text name="quickCase.cardId" />
					</option>
					<%--身份证--%>
					<option value="02">
						<s:text name="certainLoss.thirdCarLoss.dutyOther" />
					</option>
					<%--其他--%>
				</select>
			</td>
			<td>
				<input type="text" class="common" name="prpLcertifyPayeeLicenseCode">
			</td>
			<td>
				<input type="text" class="common" name="prpLcertifyPayeeLinker">
			</td>
			<td>
				<input type="text" class="common" name="prpLcertifyPayeeBankType">
			</td>
			<td>
				<input type="text" class="common" name="prpLcertifyPayeeBankCardNo">
			</td>
			<td>
				<input type="text" class="common" name="prpLcertifyPayeeLinkerTel">
			</td>
			<td>
				<div>
					<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'PayeeText')" value="-" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tbody>
</table>
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="subformtitle" colspan="4">
			<div align="left">
				<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistExtImg" onclick="showPage(this,PayeeText)">
				<s:text name="certify.payeeInfo" />
				<br>
			</div>
			<%--领款人信息--%>
		</td>
	</tr>
	<!--/table -->
	<!--span  id="payeeText" style="display:none" -->
	<tr>
		<td>
			<span id="spanClaimLoss">
				<table class="common" id="PayeeText" align="center" width="100%" cellspacing="1" cellpadding="1" style="display: none">
					<thead>
						<tr>
							<td class="subformtitle" style="width: 10%">
								<s:text name="certify.type" />
							</td>
							<%--类型--%>
							<td class="subformtitle" style="width: 10%">
								<s:text name="certify.elationshipInsured" />
							</td>
							<%--与被保险人的关系--%>
							<td class="subformtitle" style="width: 10%">
								<s:text name="claim.name" />
							</td>
							<%--姓名--%>
							<td class="subformtitle" style="width: 10%">
								<s:text name="certify.identifyType" />
							</td>
							<%--证件类别--%>
							<td class="subformtitle" style="width: 14%">
								<s:text name="db.prpLdriver.identifyNumber" />
							</td>
							<%--证件号码--%>
							<td class="subformtitle" style="width: 10%">
								<s:text name="db.prpLregist.linkerName" />
							</td>
							<%--联系人--%>
							<td class="subformtitle" style="width: 10%">
								<s:text name="db.prpLcompensate.bank" />
							</td>
							<%--开户银行--%>
							<td class="subformtitle" style="width: 14%">
								<s:text name="compensate.bankAccount" />
							</td>
							<%--银行帳号--%>
							<td class="subformtitle" style="width: 10%">
								<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFPhoneNumber" />
							</td>
							<%--联系电话--%>
							<td class="subformtitle" style="width: 2%"></td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=9>
								<s:text name="prompt.schedule.addRename7" />
							</td>
							<%--(按"+"号键增加领款人信息，按"-"号键删除信息)--%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertRow('PayeeText')" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:if test="${certifyDto.prpLcertifyPayeeList!=null}">
							<c:forEach items="${certifyDto.prpLcertifyPayeeList}" var="prpLcertifyPayee">
								<tr>
									<td>
										<input type="hidden" name="prpLcertifyPayeePayeeTypeName" value="">
										<select name="prpLcertifyPayeePayeeTypeCode">
											<option value=""></option>
											<option value="01" <c:if test="${prpLcertifyPayee.payeeTypeCode=='01'}">selected</c:if>><s:text name="account.personal" /></option>
											<option value="02" <c:if test="${prpLcertifyPayee.payeeTypeCode=='02'}">selected</c:if>><s:text name="account.group" /></option>
										</select>
									</td>
									<td>
										<input type="hidden" name="prpLcertifyPayeeRelationsName" value="">
										<select name="prpLcertifyPayeeRelationsCode">
											<option value=""></option>
											<option value="01" <c:if test="${prpLcertifyPayee.relationsCode=='01'}">selected</c:if>>
												<s:text name="regist.prpLregist.self" />
											</option>
											<%--本人--%>
											<option value="02" <c:if test="${prpLcertifyPayee.relationsCode=='02'}">selected</c:if>>
												<s:text name="certify.beneficiary" />
											</option>
											<%--受益人--%>
											<option value="03" <c:if test="${prpLcertifyPayee.relationsCode=='03'}">selected</c:if>>
												<s:text name="certify.groupClient" />
											</option>
											<%--委托人--%>
										</select>
									</td>
									<td>
										<input type="text" class="common" name="prpLcertifyPayeePayeeName" value="${prpLcertifyPayee.payeeName }">
									</td>
									<td>
										<input type="hidden" name="prpLcertifyPayeeLicenseTypeName" value="">
										<select name="prpLcertifyPayeeLicenseTypeCode">
											<option value=""></option>
											<option value="01" <c:if test="${prpLcertifyPayee.licenseTypeCode=='01'}">selected</c:if>>
												<s:text name="quickCase.cardId" />
											</option>
											<%--身份证--%>
											<option value="02" <c:if test="${prpLcertifyPayee.licenseTypeCode=='02'}">selected</c:if>>
												<s:text name="certainLoss.thirdCarLoss.dutyOther" />
											</option>
											<%--其他--%>
										</select>
									</td>
									<td>
										<input type="text" class="common" name="prpLcertifyPayeeLicenseCode" value="${prpLcertifyPayee.licenseCode }">
									</td>
									<td>
										<input type="text" class="common" name="prpLcertifyPayeeLinker" value="${prpLcertifyPayee.linker}">
									</td>
									<td>
										<input type="text" class="common" name="prpLcertifyPayeeBankType" value="${prpLcertifyPayee.bankType}">
									</td>
									<td>
										<input type="text" class="common" name="prpLcertifyPayeeBankCardNo" value="${prpLcertifyPayee.bankCardNo}">
									</td>
									<td>
										<input type="text" class="common" name="prpLcertifyPayeeLinkerTel" value="${prpLcertifyPayee.linkerTel}">
									</td>
									<td>
										<div>
											<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'PayeeText')" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
