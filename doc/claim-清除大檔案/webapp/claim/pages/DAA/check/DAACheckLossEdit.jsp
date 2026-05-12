<%--
****************************************************************************
* DESC       ：查勘事故估损金额画面
* AUTHOR     ： 中科软
* CREATEDATE ： 2004-06-03
* MODIFYLIST ：   Name       Date            Reason/Contents
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<script language='javascript'>
	//在下面加入本页自定义的JavaScript方法
	/*
	  插入一条新的之後的处理（可选方法）注意afterInsert後面必须是span的名字，而且区分大小写
	 */
	function afterInsertCheckLoss() {
		setPrpLcheckLossSerialNo();
	}

	/*
	  删除本条WarnRegion之後的处理（可选方法）
	 */
	function afterDeleteCheckLoss(field) {
		setPrpLcheckLossSerialNo();
	}

	/**
	 * 设置setPrpLcheckLossSerialNo
	 */
	function setPrpLcheckLossSerialNo() {
		var count = getElementCount("prpLcheckLossSerialNo");
		for ( var i = 0; i < count; i++) {
			//alert("看看什么时候运行?count="+count+"  i="+i); 
			if (count != 1) {
				fm.prpLcheckLossSerialNo[i].value = i;
			}
		}
	}
</script>
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="subformtitle" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="CheckLossImg" onclick="showPage(this,spanCheckLoss)">
			<s:text name="check.compCosts" />
			<br>
			<%-- 赔款费用 --%>
			<span style="display: none">
				<table class="common" style="display: none" id="CheckLoss_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="title" style="width: 20%">
								<input type="hidden" name="prpLcheckLossFlag">
								<input type="hidden" name="prpLcheckLossLossFeeType">
								<input type="text" class="input" style="width: 80px" name="prpLcheckLossReferSerialNo">
								<input type="hidden" name="prpLcheckLossSerialNo" description="序号">
							</td>
							<td class="title" style="width: 16%">
								<input type="text" name="prpLcheckLossKindCode" class="codecode" style="width: 50px" maxlength=3 ondblclick="code_CodeSelect(this, 'PolicyKindCode');"
									onchange="code_CodeChange(this, 'PolicyKindCode');" onkeyup="code_CodeSelect(this, 'PolicyKindCode');">
							</td>
							<td class="title" style="width: 20%">
								<input type="text" name="prpLcheckLossKindName" class="codename" style="width: 90%" ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');"
									onchange="code_CodeChange(this, 'PolicyKindCode','-1','name','none','post');" onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');">
							</td>
							<td class="title" style="width: 20%">
								<html:select name="prpLcheckLossDto" property="lossFeeType">
									<html:option value="1">
										<s:text name="check.vehicle" />
									</html:option>
									<%-- 车辆 --%>
									<html:option value="2">
										<s:text name="check.personnel" />
									</html:option>
									<%-- 人员 --%>
									<html:option value="3">
										<s:text name="check.property" />
									</html:option>
									<%-- 财产 --%>
								</html:select>
							</td>
							<td class="title" style="width: 20%">
								<input type="text" name="prpLcheckLossLossFee" class="input" style="width: 100%; align: right">
							</td>
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonCheckLossDelete" class="smallbutton" onclick="deleteRow(this,'CheckLoss')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanCheckLoss" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="CheckLoss">
					<thead>
						<tr>
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpLcheckLoss.referSerialNo" />
							</td>
							<%-- 关联理赔车辆序号 --%>
							<td class="centertitle" style="width: 16%">
								<s:text name="db.prpLCitemKind.kindCode" />
							</td>
							<%-- 险别代码 --%>
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpLCitemKind.kindName" />
							</td>
							<%-- 险别名称 --%>
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpLcheckLoss.lossFeeType" />
							</td>
							<%-- 金额类型 --%>
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpLcheckLoss.lossFee" />
							</td>
							<%-- 损失金额 --%>
							<td class="title" style="width: 4%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=5 style="width: 96%">
								<s:text name="prompt.check.addRemove" />
							</td>
							</td>
							<%-- (按"+"号键增加赔款费用信息，按"-"号键删除信息) --%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class="smallbutton" onclick="insertRow('CheckLoss')" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					</tfoot>
					<tbody>
						<%
							int indexCharge = 0;
						%>
						<logic:notEmpty name="prpLcheckLossDto" property="prpLcheckLossList">
							<logic:iterate id="checkLossdtox" name="prpLcheckLossDto" property="prpLcheckLossList">
								<tr>
									<td class="input">
										<input type="hidden" name="prpLcheckLossLossFeeType" value="<bean:write name='checkLossdtox' property='lossFeeType'/>">
										<input type="text" class="input" style="width: 80px" name="prpLcheckLossReferSerialNo" value="<bean:write name='checkLossdtox' property='referSerialNo'/>">
										<input type="hidden" name="prpLcheckLossFlag" value="<bean:write name='checkLossdtox' property='flag'/>">
										<input type="hidden" name="prpLcheckLossSerialNo" description="序号" value="<bean:write name='checkLossdtox' property='serialNo'/>">
									</td>
									<td class="input">
										<input type="text" name="prpLcheckLossKindCode" class="codecode" style="width: 50px" maxlength=3 value="<bean:write name='checkLossdtox' property='kindCode'/>"
											ondblclick="code_CodeSelect(this, 'PolicyKindCode');" onchange="code_CodeChange(this, 'PolicyKindCode');" onkeyup="code_CodeSelect(this, 'PolicyKindCode');">
									</td>
									<td class="input">
										<input type="text" name="prpLcheckLossKindName" class="codename" style="width: 90%" value="<bean:write name='checkLossdtox' property='kindName'/>"
											ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');" onchange="code_CodeChange(this, 'PolicyKindCode','-1','name','none','post');"
											onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');">
									</td>
									<td class="input">
										<html:select name="checkLossdtox" property="lossFeeType">
											<html:option value="1">
												<s:text name="check.vehicle" />
											</html:option>
											<%-- 车辆 --%>
											<html:option value="2">
												<s:text name="check.personnel" />
											</html:option>
											<%-- 人员 --%>
											<html:option value="3">
												<s:text name="check.property" />
											</html:option>
											<%-- 财产 --%>
										</html:select>
									</td>
									<td class="input">
										<input type="text" name="prpLcheckLossLossFee" class="input" style="width: 100%; text-align: right" value="<bean:write name='checkLossdtox' property='lossFee' format='##.00' />">
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonCheckLossDelete" class="smallbutton" onclick="deleteRow(this,'CheckLoss')" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
								<%
									indexCharge++;
								%>
							</logic:iterate>
						</logic:notEmpty>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
