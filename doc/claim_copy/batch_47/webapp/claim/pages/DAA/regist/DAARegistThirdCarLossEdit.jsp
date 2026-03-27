<%--
****************************************************************************
* DESC       ：添加损失部位信息页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2004-12-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
****************************************************************************
--%>
   <!--建立显示的录入条，可以收缩显示的-->
   
    <script language='javascript'>
		   //<!--    
	      //在下面加入本页自定义的JavaScript方法
	/*
	        插入一条新的之後的处理（可选方法） 
	      */
	
	function afterInsertThirdCarLoss() {
		setPrpLthirdCarLossSerialNo();
	}
	
	/*
	        删除本条WarnRegion之後的处理（可选方法）
	      */
	
	function afterDeleteThirdCarLoss(field) {
		setPrpLthirdCarLossSerialNo();
	}
	
	/** 
	 * 设置setPrpLthirdCarLossSerialNo
	 */
	
	function setPrpLthirdCarLossSerialNo() {
		var count = getElementCount("prpLthirdCarLossSerialNo");
		for (var i = 0; i < count; i++) {
			//alert("看看什么时候运行?count="+count+"  i="+i); 
			if (count != 1) {
				fm.prpLthirdCarLossSerialNo[i].value = i;
			}
		}
	}
		   //-->      
    </script>
<table class="common" cellspacing="1" cellpadding="5">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="ThirdCarLossImg" onclick="showPage(this,spanThirdCarLoss)">
			<s:text name="certainLoss.thirdCarLoss.prpLchecDemagePart" />
			<br> <span style="display: none">
				<table class="common" style="display: none" id="ThirdCarLoss_Data" cellspacing="1" cellpadding="5">
					<tbody>
						<tr>
							<td class="input">
								<input type="hidden" name="prpLthirdCarLossFlag">
								<input type="hidden" name="prpLthirdCarLossSerialNo" description="序号">
								<input name="prpLthirdCarLossItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="1">
								<img src="/claim/images/bgMarkMustInput.jpg">
							</td>
							<td class="input">
								<input name="prpLthirdCarLossLicenseNo" class="readonly" readonly style="width: 90%" value="<bean:write name='prpLregistDto' property='licenseNo' filter='true' />">
							</td>
							<td class="input">
								<html:select name="prpLthirdCarLossDto" property="partCode" styleClass="three" style="width:120px">
									<html:options collection="partCodeList" property="value" labelProperty="label" />
								</html:select>
								<input type="hidden" name="partName">
							</td>
							<!--<td class="input"> 
                  <input name="prpLthirdCarLossCompCode" class="codecode" style="width:60%"                  
                      ondblclick= "code_CodeSelect(this,'CarPartCode');"
                      onkeyup= "code_CodeSelect(this,'CarPartCode');">
                       <img src="/claim/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle">
                       <img src="/claim/images/bgMarkMustInput.jpg">    
                </td>-->
							<td class="input">
								<input name="compName" class="codename" style="width: 90%" ondblclick="return openCompCodeWin(ThirdCarLoss_Data,this);">
								<input type="hidden" name="compCode">
							</td>
							<td class="input">
								<input name="prpLthirdCarLossLossGrade" class="input" style="width: 90%">
							</td>
							<td class="input">
								<input name="prpLthirdCarLossLossDesc" class="input" style="width: 90%">
							</td>
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonThirdCarLossDelete" class="smallbutton" onclick="deleteRow(this,'ThirdCarLoss')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanThirdCarLoss" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="ThirdCarLoss" cellspacing="1" cellpadding="5">
					<thead>
						<tr>
							<td class="centertitle">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageItemNo" />
							</td>
							<%-- 损失项目序号 --%>
							<td class="centertitle">
								<s:text name="db.prpLregist.licenseNo" />
							</td>
							<%-- 损车牌号码 --%>
							<td class="centertitle">
								<s:text name="certainLoss.thirdCarLoss.prpLchecDemagePart" />
							</td>
							<%-- 损失部位 --%>
							<td class="centertitle">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckAccessoryName" />
							</td>
							<%-- 零件(项目)名称 --%>
							<td class="centertitle">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageLevel" />
							</td>
							<%-- 损失程度级别 --%>
							<td class="centertitle" style="width: 21%">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageDetail" />
							</td>
							<%-- 损失程度描述 --%>
							<td class="centertitle" style="width: 4%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=6>
								<s:text name="certainLoss.thirdCarLoss.promptLoss" />
							</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertRow('ThirdCarLoss')" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					</tfoot>
					<tbody>
						<% int indexThirdCarLoss=0;%>
						<logic:notEmpty name="prpLthirdCarLossDto" property="thirdCarLossList">
							<logic:iterate id="thirdCarLossdtox" name="prpLthirdCarLossDto" property="thirdCarLossList">
								<tr>
									<td class="input">
										<input type="hidden" name="prpLthirdCarLossFlag" value="<bean:write name='thirdCarLossdtox' property='flag'/>">
										<input type="hidden" name="prpLthirdCarLossSerialNo" description="序号" value="<bean:write name='thirdCarLossdtox' property='serialNo'/>">
										<input name="prpLthirdCarLossItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="<bean:write name='thirdCarLossdtox' property='itemNo'/>">
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="input">
										<input name="prpLthirdCarLossLicenseNo" class="readonly" readonly style="width: 90%" value="<bean:write name='thirdCarLossdtox' property='licenseNo'/>">
									</td>
									<!--Reason:根据大地需求损失部位显示为列表方式,显示零件名称-->
									<td class="input">
										<html:select name="thirdCarLossdtox" property="partCode" styleClass="three" style="width:120px">
											<html:options collection="partCodeList" property="value" labelProperty="label" />
										</html:select>
										<input type="hidden" name="partName" value="<bean:write name='thirdCarLossdtox' property='partName'/>">
									</td>
									<!--
                <td class="input">
                  <input name="prpLthirdCarLossCompCode" class="codecode" style="width:60%" value="<bean:write name='thirdCarLossdtox' property='compCode'/>"          
                      ondblclick= "code_CodeSelect(this,'CarPartCode');"
                      onkeyup= "code_CodeSelect(this,'CarPartCode');"> 
                      <img src="/claim/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle"> 
                       <img src="/claim/images/bgMarkMustInput.jpg">   
                </td>
                -->
									<td class="input">
										<input name="compName" class="codename" style="width: 90%" value="<bean:write name='thirdCarLossdtox' property='compName'/>" ondblclick="return openCompCodeWin(ThirdCarLoss,this);">
										<input type="hidden" name="compCode" value="<bean:write name='thirdCarLossdtox' property='compCode'/>">
									</td>
									<td class="input">
										<input name="prpLthirdCarLossLossGrade" class="input" style="width: 90%" value="<bean:write name='thirdCarLossdtox' property='lossGrade'/>">
									</td>
									<td class="input">
										<input name="prpLthirdCarLossLossDesc" class="input" style="width: 90%" value="<bean:write name='thirdCarLossdtox' property='lossDesc'/>">
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonThirdCarLossDelete" class=smallbutton onclick="deleteRow(this,'ThirdCarLoss')" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
								<%    indexThirdCarLoss++;%>
							</logic:iterate>
						</logic:notEmpty>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
