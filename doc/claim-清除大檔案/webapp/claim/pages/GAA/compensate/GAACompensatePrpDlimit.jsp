<%--
****************************************************************************
* DESC       ：赔偿限额/免赔额显示画面 
* AUTHOR     ：理赔组
* CREATEDATE ： 2004-10-18 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<tr class=mline>
		<td class="subformtitle" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="LimitImg" onclick="showPage(this,spanLimit)">
			<s:text name="db.prpClimit.limitFee" />
			<br>
			<%-- 赔偿限额/免赔额 --%>
			<span style="display: none">
				<table id="Limit_Data">
					<tbody>
						<tr>
							<td>
								<input class=readonly readonly style='display: none; width: 12px' name=Limit_Flag>
								<input class=readonly readonly style="width: 60px" name=LimitGrade>
							</td>
							<td>
								<input class=readonly readonly style="width: 60px" name=LimitNo>
							</td>
							<td>
								<input class=readonly readonly style="width: 150px" name=LimitType>
							</td>
							<td>
								<input class=readonly readonly style="width: 100px" name=PrpLimitCurrency>
							</td>
							<td>
								<input class=readonly readonly style="width: 150px" name=LimitFee>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanLimit" style="display: none">
				<table class="sub" style='width: 100%' id="Limit">
					<thead>
						<tr>
							<td class="centertitle" style="width: 15%">
								<s:text name="commonAcci.compensate.allSinglePart" />
							</td>
							<%-- 全单/部分 --%>
							<td class="centertitle" style="width: 15%">
								<s:text name="db.prpLpersonloss.itemKindNo" />
							</td>
							<%-- 标的险别序号 --%>
							<td class="centertitle" style="width: 40%">
								<s:text name="commonAcci.compensate.limitFranchiseType" />
							</td>
							<%--限额/免赔额类型 --%>
							<td class="centertitle" style="width: 15%">
								<s:text name="db.prpLlawsuit.currency" />
							</td>
							<%-- 币别 --%>
							<td class="centertitle" style="width: 15%">
								<s:text name="compensate.sumexcess" />
							</td>
							<%-- 保险金额/免赔 --%>
						</tr>
					</thead>
					<tbody>
						<logic:notEmpty name="prpClimitDto" property="prpClimitList">
							<logic:iterate id="limitList" name="prpClimitDto" property="prpClimitList">
								<tr class=oddrow>
									<td class="centertitle">
										<bean:write name='limitList' property='limitGrade' />
									</td>
									<td class="centertitle">
										<bean:write name='limitList' property='limitNo' />
									</td>
									<td class="centertitle">
										<bean:write name='limitList' property='limitTypeName' />
									</td>
									<td class="centertitle">
										<bean:write name='limitList' property='currencyName' />
									</td>
									<td class="centertitle">
										<bean:write name='limitList' property='limitFee' format='##0.00' />
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
