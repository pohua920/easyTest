<%--
****************************************************************************
* DESC       ：显示货运险查勘详细信息
* AUTHOR     : 中科软
* MODIFYLIST ： Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" align="center" width="100%" style="display: none">
	<tr class=mline>
		<td class="subformtitle" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif"
				name="RegistTextImg" onclick="showPage(this,DetailText)">
			<s:text name="check.surveyDetails" />
			<%-- 查勘详细信息--%>
			<br>
			<table class="common" align="center" id="DetailText"
				style="display: none" cellspacing="1" cellpadding="5">
				<tbody>
					<tr>
						<td class="title">
							<s:text name="db.prpLregist.reportDate" />
							:
						</td>
						<%-- 报案日期 --%>
						<td class="input">
							<rc:rcDate name="prpLregistReportDate" class="readonly" readonly="true" wdatePicker="false" value="${prpLregist.reportDate}" />
						</td>
						<td class="title">
							<s:text name="check.applicationDate" />
							:
						</td>
						<%-- 申请查勘日期 --%>
						<td class="input">
							<rc:rcDate name="prpLextAppliCheckDate" class="readonly" readonly="true" wdatePicker="false" value="${prpLext.appliCheckDate}" />
						</td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="check.insuredPersonPhone" />
						</td>
						<%-- 投保人或代表名称及联系电话/传真 --%>
						<td class="input">
							<input type="text" name="prpLextAppliPhone" class="input"
								value="${prpLext.appliPhone}">
						</td>
						<td class="title"></td>
						<td class="input"></td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="check.insuredNumber" />
						</td>
						<%-- 被保险人或代表名称及联系电话/传真 --%>
						<td class="input">
							<input type="text" name="prpLextInsuredPhone" class="input"
								value="${prpLext.insuredPhone}">
						</td>
						<td class="title"></td>
						<td class="input"></td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="db.prpCcargoDetail.startDate" />
							:
						</td>
						<%-- 起运日期 --%>
						<td class="input">
							<input type="text" name="prplextSailStartDate" class="input"
								style="width: 140px"
								value="${prpLext.sailStartDate}">
						</td>
						<td class="title"></td>
						<td class="input"></td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="check.transportRoutes" />
							:
						</td>
						<%-- 运输路线 --%>
						<td class="input" colspan="3">
							<s:text name="prompt.from" />
							${prpLcarGo.startSiteName}
							<s:text name="claim.toThe" />
							${prpLcarGo.endSiteName}
						</td>
						<%-- 从 --%>
						<%-- 到 --%>
					</tr>
					<tr>
						<!-- 显示运输工具 由於此处的名称在业务系统里可能保存在不同的字段中，所以遇到问题时此处还需要进行处理modify by wuxiaodong begain 050903-->
						<td class="title">
							<s:text name="db.prpLclaimagent.conveyance" />
							:
						</td>
						<%-- 运输方式 --%>

						<td class="input"></td>


					</tr>
					<tr>
						<td class="title">
							<s:text name="db.prpCcargoDetail.sumAmount" />
							:
						</td>
						<%--保额  --%>
						<td class="input">
							<fmt:formatNumber value='${prpLext.sumAmount}' pattern='#'/>
						</td>
						<td class="title"></td>
						<td class="input"></td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="check.dductible" />
							:
						</td>
						<%-- 免赔 --%>
						<td class="input">
							<fmt:formatNumber value='${prpLext.limitAmount}' pattern='#'/>
						</td>
						<td class="title">
							<s:text name="check.price" />
							:
						</td>
						<%--货价 --%>
						<td class="input">
							<input type="text" name="prpLextSumValue" class="input"
								style="width: 140px"
								value="<fmt:formatNumber value='${prpLext.cargoValue}' pattern='#'/>"/>
						</td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="certainLoss.thirdCarLoss.ThirdPartyInsureCom" />
							:
						</td>
						<%-- 承保公司 --%>
						<td class="input">
							${prpLext.prpCompanyName}
						</td>
						<td class="title">
							<s:text name="check.surveyCompany" />
							:
						</td>
						<%-- 货损查勘公司 --%>
						<td class="input"></td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="check.ladeBill" />
							:
						</td>
						<%-- 提单/运单 --%>
						<td class="input"></td>
						<td class="title">
							<s:text name="check.invoiceNumber" />
							:
						</td>
						<%--发票号码NO  --%>
						<td class="input">
							<input type="text" name="prpLextInvoiceNo" class="input"
								style="width: 140px"
								value="${prpLext.value3}">
						</td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="check.commodityQuantity" />
							:
						</td>
						<%--货物名称及数量  --%>
						<td class="input">
							<input type="text" name="prpLextValue11" class="input"
								style="width: 140px"
								value="${prpLext.value1}">
						</td>
						<td class="title">
							<s:text name="check.dischargeDate" />
							:
						</td>
						<%-- 卸货日期 --%>
						<td class="input"></td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="regist.prpLregist.currency" />
							:
						</td>
						<%-- 币别 --%>
						<td class="input">
							<input type="text" name="prpLextCurrency"
								value="${prpLext.currency}"
								class="readonly" readonly style="width: 30%" title="币别"
								ondblclick="code_CodeSelect(this, 'Currency');"
								onkeyup="code_CodeSelect(this, 'Currency');">
							<input type=text name="prpLregistEstiCurrencyName"
								class="readonly" readonly style="width: 60%" title="币别"
								value="${prpLext.currencyCname}"
								ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');"
								onkeyup="code_CodeSelect(this, 'Currency','-1','always','none','post');">
						</td>
						<td class="title">
							<s:text name="db.prpLregist.estimateLoss" />
						</td>
						<%-- 估损金额 --%>
						<td class="input">
							<input type="text" name="prpLextValue2" class="input"
								style="width: 140px"
								value="<fmt:formatNumber value='${prpLext.value2}' pattern='#'/>" />
						</td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="check.residualValues" />
						</td>
						<%-- 残值数量 --%>
						<td class="input">
							<input type="text" name="prpLextRestQuantity" class="input"
								style="width: 140px"
								value="${prpLext.restQuantity}">
						</td>
						<td class="title"></td>
						<td class="input"></td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
