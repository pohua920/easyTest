<%--
****************************************************************************
* DESC       ：显示(非车险)立案登记的险别估损金额页面
* AUTHOR     ：理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="subformtitle" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="ClaimLossImg" onclick="showPage(this,spanClaimLoss)">
			<s:text name="claim.amountInsurLossInfo" />
			<font color="#FF0000">*</font><br>
			<%--险别估损金额信息--%>
			<span style="display: none">
				<table class="common" style="display: none" id="ClaimLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="width: 30%" style="align:center">
								<input type=text name="prpLclaimLossKindCode" class="codecode" style="width: 30%" title="險別" ondblclick="code_CodeSelect(this,'PolicyKindCode');"
									onkeyup="code_CodeSelect(this,'PolicyKindCode');">
								<input type=text name="prpLclaimLossKindName" class="codecode" style="width: 60%" title="險別" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','always','none','post');">
							</td>
							<td class="input" style="width: 20%" align="center">
								<input type="text" name="prpLregistEstiCurrency" value="" class="codecode" style="width: 30%" title="幣別" ondblclick="code_CodeSelect(this, 'Currency');"
									onkeyup="code_CodeSelect(this, 'Currency');">
								<input type=text name="prpLregistEstiCurrencyName" class="codecode" style="width: 60%" title="幣別" value="" ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');"
									onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
							</td>
							<td class="input" style="width: 16%">
								<input name="prpLclaimLossSumClaim" class=common style="text-align: right" onblur=" collectClaimLoss();dutySum();">
							</td>
							<td class="input" name="prpLregsitLossFeeType" style="width: 10%">
								<select name="prpLregistLossFeeType">
									<option value="P">
										<s:text name="db.prpGradeExt.sumPaid" />
									</option>
									<%--赔款--%>
									<option value="Z">
										<s:text name="claim.cost" />
									</option>
									<%--费用--%>
								</select>
							</td>
							<td class="input" style="width: 8%">
								<input name="prpLclaimLossKindRest" class=common style="width: 50px">
							</td>
							<%
								//获得当前时间
							%>
							<td class="input" style="width: 10%">
								<input name="prpLclaimLossInputDate" class="readonly" readonly style="width: 85px" maxlength="10"
									value="<%=new com.sinosoft.sysframework.common.datatype.DateTime(com.sinosoft.sysframework.common.datatype.DateTime.current().toString(), com.sinosoft.sysframework.common.datatype.DateTime.YEAR_TO_DAY)%>">
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLclaimLossRemarkFlag" class="input" maxlength="100">
							</td>
							<td class="input" style='width: 4%' colspan="2" align="center">
								<div>
									<input type=button name="buttonClaimLossDelete" class="smallbutton" onclick="deleteRow(this,'ClaimLoss')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanClaimLoss" style="display:" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="ClaimLoss">
					<thead>
						<tr>
							<td class="centertitle" style="width: 30%">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckRaskType" />
							</td>
							<%--险别--%>
							<td class="centertitle" style="width: 20%">
								<s:text name="regist.prpLregist.currency" />
							</td>
							<%--币别--%>
							<td class="centertitle" style="width: 16%">
								<s:text name="claim.amountInsurLoss" />
							</td>
							<%--险别估损金额--%>
							<td class="centertitle" style="width: 10%">
								<s:text name="claim.costType" />
							</td>
							<%--费用类型--%>
							<td class="centertitle" style="width: 8%">
								<s:text name="claim.salvage" />
							</td>
							<%--残值--%>
							<td class="centertitle" style="width: 10%">
								<s:text name="db.view_larrearage.inputDate" />
							</td>
							<%--输入日期--%>
							<td class="centertitle" style="width: 10%">
								<s:text name="claim.adjustReason" />
							</td>
							<%--调整原因--%>
							<td class="centertitle" style="width: 4%">
								<input onclick="collectClaimLoss();dutySum();" type="button" class="button" value="<s:text name='button.summary.value' />">
								<%--汇总--%>
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=6 style="width: 96%">
								<s:text name="prompt.certify.addRemove" />
							</td>
							<%--(按"+"号键增加信息，按"-"号键删除信息)--%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class="smallbutton" onclick="insertRow('ClaimLoss')" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>