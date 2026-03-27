<%--
****************************************************************************
* DESC       ：货运险增加受理人
* AUTHOR     ：理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<!--建立显示的录入条，可以收缩显示的-->
<tr>
	<td class="subformtitle" colspan="6" cellspacing="1" cellpadding="0"
		style="text-align: left">
		<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif"
			name="ThirdPropImg" onclick="showPage(this,spanThirdProp)">
		<!--modify by caopeng start at 2005-12-21-->
		<s:text name="regist.IncreasesContact" />
		<%-- 增加联系人 --%>
		<!--modify by caopeng end at 2005-12-21-->
		<span style="display: none">
			<table class="common" style="display: none" id="ThirdProp_Data"
				cellspacing="1" cellpadding="5">
				<tbody>
					<tr>
						<td class="input" style="width: 10%">
							<input name="prpLrelatePersonSeriaNo" class="readonly" readonly
								style="width: 75%" maxlength=3 value="1">
						</td>
						<td class="input" style="width: 15%">
							<input name="prpLrelatePersonPersonName" class="common"
								style="width: 90%">
						</td>
						<td class="input" style="width: 15%">
							<input name="prpLrelatePersonPhoneNumber" class="common"
								style="width: 90%">
						</td>
						<td class="input" style="width: 15%">
							<input name="prpLrelatePersonMobile" class="input"
								style="width: 90%">
						</td>
						<td class="input" style="width: 45%">
							<input name="prpLrelatePersonRemark" class="input"
								style="width: 90%">
						</td>
						<td class="input" style='width: 4%' align="center">
							<div>
								<input type=button name="buttonThirdPropDelete"
									class="smallbutton"
									onclick="deleteRow(this,'ThirdProp','prpLrelatePersonSeriaNo')"
									value="-" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</span> <span id="spanThirdProp" style="display: none" cellspacing="1"
			cellpadding="0"> <%-- 多行输入展现域 --%>
			<table class="common" style="width: 100%" id="ThirdProp">
				<thead>
					<tr>
						<td class="centertitle" style="width: 10%">
							<s:text name="regist.prpLregist.serialNo" />
						</td>
						<%-- 序号 --%>
						<td class="centertitle" style="width: 15%">
							<s:text name="claim.name" />
						</td>
						<%-- 姓名 --%>
						<td class="centertitle" style="width: 15%">
							<s:text name="scheduleCompany.fixedTelephone" />
						</td>
						<%-- 固定电话 --%>
						<td class="centertitle" style="width: 15%">
							<s:text name="regist.mobilePhones" />
						</td>
						<%--移动电话  --%>
						<td class="centertitle" style="width: 45%">
							<s:text name="db.prpLcomponent.remark" />
						</td>
						<%-- 备注 --%>
						<td class="title" style="width: 4%">&nbsp;</td>
					</tr>
				</thead>

				<tfoot>
					<tr>
						<td class="title" colspan=5 style="width: 96%">
							<s:text name="prompt.regist.linkPerson" />
							<%-- (按"+"号键增加联系人信息，按"-"号键删除信息) --%>
						</td>
						<td class="title" align="right" style="width: 4%">
							<div align="center">
								<input type="button" value="+" class="smallbutton"
									onclick="insertRow('ThirdProp',this,'prpLrelatePersonSeriaNo')"
									name="buttonDriverInsert" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody id="ThirdProp">
					<c:if test="${not empty prpLrelatePersonList}">
						<c:forEach items="${prpLrelatePersonList}" var="prpLrelatePerson">
							<tr>
								<td class="input" style="width: 10%">
									<input name="prpLrelatePersonSeriaNo" class="readonly" readonly
										style="width: 75%" maxlength=3
										value="${prpLrelatePerson.serialNo }" />
								</td>
								<td class="input" style="width: 15%">
									<input name="prpLrelatePersonPersonName" class="common"
										style="width: 90%" value="${prpLrelatePerson.personName }">
								</td>
								<td class="input" style="width: 15%">
									<input name="prpLrelatePersonPhoneNumber" class="common"
										style="width: 90%" value="${prpLrelatePerson.phoneNumber }">
								</td>
								<td class="input" style="width: 15%">
									<input name="prpLrelatePersonMobile" class="input"
										style="width: 90%" value="${prpLrelatePerson.mobile }">
								</td>
								<td class="input" style="width: 45%">
									<input name="prpLrelatePersonRemark" class="input"
										style="width: 90%" value="${prpLrelatePerson.remark }">
								</td>
								<td class="input" style='width: 4%' align="center">
									<div>
										<input type=button name="buttonThirdPropDelete"
											class="smallbutton"
											onclick="deleteRow(this,'ThirdProp','prpLrelatePersonSeriaNo')"
											value="-" style="cursor: hand">
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
