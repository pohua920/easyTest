<%@page pageEncoding="GBK"%>
<%--
****************************************************************************
* DESC       ：定损环节过程的修理/换件清单页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13 
* MODIFYLIST ：   Name       Date            Reason/Contents
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.struts.util.LabelValueBean"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@page import="org.apache.struts.util.LabelValueBean"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%
	Collection collection = (Collection) request.getAttribute("partCodeList");
	Collection repairColl = (Collection) request.getAttribute("repairTypes");
%>
<table id="RepairComponent" class=common cellpadding="5" cellspacing="1">
	<tr>
		<td>
			<span style="display: none">
				<table class="common" style="display: none" id="RepairFee_Data" cellpadding="5" cellspacing="1">
					<tbody>
						<tr>
							<td class="input" style="display: none">
								<input type="hidden" name="carLossRepairFeeLossItemCode" style="width: 20px">
								<input type="text" name="prpLrepairFeeKindCode" class="codecode" style='width: 40px' ondblclick="code_CodeSelect(this,'PolicyKindCode');" onkeyup="code_CodeSelect(this,'PolicyKindCode');">
							</td>
							<%
								CertainLossDto certainLossDto1 = (CertainLossDto) request.getAttribute("certainLossDto");
								PrpLcarLossDto prpLcarLossDto1 = (PrpLcarLossDto) certainLossDto1.getPrpLcarLossDtoList().get(0);
								if (prpLcarLossDto1.getLossItemCode().trim().equals("1")) {
							%>
							<td class="input">
								<input type="text" name="prpLrepairFeeKindName" class="codename" style='width: 70px' ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');">
							</td>
							<%
								} else {
							%>
							<td class="input">
								<input type="text" name="prpLrepairFeeKindName" class="codename" style='width: 70px' ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','selectb','post');"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','selectb','post');">
							</td>
							<%
								}
							%>
							<td class="input">
								<select name="prpLrepairFeePartCode" styleClass="three">
									<%
										Iterator prpLPartCodeNameList = collection.iterator();
										while (prpLPartCodeNameList.hasNext()) {
											LabelValueBean labelValueBean = (LabelValueBean) prpLPartCodeNameList.next();
									%>
									<option value="<%=labelValueBean.getValue()%>"><%=labelValueBean.getLabel()%></option>
									<%
										}
									%>
								</select>
								<input type="hidden" name="prpLrepairFeePartName" value="前部">
							</td>
							<td class="input">
								<input name="prpLrepairFeeCompName" class="codename" style="width: 90%" ondblclick="return openPrplRepairFeeCompWin(RepairFee_Data,this);">
								<input type="hidden" name="prpLrepairFeeCompCode" value="9999">
							</td>
							<td class="input">
								<select name="prpLrepairFeeRepairType" styleClass="three" style="width: 70px">
									<%
										Iterator prpLrepairTypeList = repairColl.iterator();
										while (prpLrepairTypeList.hasNext()) {
											PrpDcodeDto prpDcodeDto = (PrpDcodeDto) prpLrepairTypeList.next();
											//System.out.println("prpDcodeDto.getCodeType():"+prpDcodeDto.getCodeType());
									%>
									<option value="<%=prpDcodeDto.getCodeCode()%>"><%=prpDcodeDto.getCodeCName()%></option>
									<%
										}
									%>
								</select>
							</td>
							<!--
                <td class="input">   
                  <input type="text" name="prpLrepairFeeCompCode" class="codecode" style='width:40px'              
                      ondblclick= "code_CodeSelect(this,'CompCode');"
                      onkeyup= "code_CodeSelect(this,'CompCode');">          
                </td> 
                <td class="input">   
                  <input type="text" name="prpLrepairFeeCompName" class="codename" style='width:70px'                    
  			             ondblclick="code_CodeSelect(this, 'CompCode','-1','always','none','post');"
  			             onkeyup= "code_CodeSelect(this, 'CompCode','-1','always','none','post');">       
                </td>
                 -->
							<td class="input" style="display: none">
								<input name="prpLrepairFeeManHour" value="1" class=common style='width: 70px' onBlur="getSumDefLoss(this,1);">
							</td>
							<td class="input">
								<input name="prpLrepairFeeManHourUnitPrice" class="readonly" readonly style='width: 70px' value="1">
							</td>
							<td class="input">
								<input name="prpLrepairFeeMaterialFee" class=common style='width: 70px' onBlur="getSumDefLoss(this,1);">
							</td>
							<td class="input">
								<input name="prpLrepairFeeSumDefLoss" class="readonly" readonly style='width: 70px'>
							</td>
							<td class="input">
								<input name="prpLrepairFeeRemark" class=common style='width: 100px'>
							</td>
							<input type="hidden" name="prpLrepairFeeSerialNo">
							<input type="hidden" name="prpLrepairFeeItemKindNo">
							<input type="hidden" name="prpLrepairFeeLossItemCode">
							<input type="hidden" name="prpLrepairFeeLicenseNo">
							<input type="hidden" name="prpLrepairFeeLicenseColorCode">
							<input type="hidden" name="prpLrepairFeeCarKindCode">
							<input type="hidden" name="prpLrepairFeeSanctioner">
							<input type="hidden" name="prpLrepairFeeApproverCode">
							<input type="hidden" name="prpLrepairFeeOperatorCode">
							<input type="hidden" name="prpLrepairFeeManHourFee">
							<input type="hidden" name="prpLrepairFeeLossRate">
							<input type="hidden" name="prpLrepairFeeCurrency">
							<input type="hidden" name="prpLrepairFeeVeriRemark">
							<input type="hidden" name="prpLrepairFeeVeriManHour">
							<input type="hidden" name="prpLrepairFeeVeriManUnitPrice">
							<input type="hidden" name="prpLrepairFeeVeriManHourFee">
							<input type="hidden" name="prpLrepairFeeVeriMaterQuantity">
							<input type="hidden" name="prpLrepairFeeVeriMaterUnitPrice">
							<input type="hidden" name="prpLrepairFeeVeriMaterialFee">
							<input type="hidden" name="prpLrepairFeeVeriLossRate">
							<input type="hidden" name="prpLrepairFeeVeriSumLoss">
							<input type="hidden" name="prpLrepairFeeFlag">
							<input type="hidden" name="prpLrepairFeeCompensateBackFlag">
							<input type="hidden" name="prpLrepairFeeFirstSumDefLoss">
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonRepairFeeDelete" class="smallbutton" onclick="deleteRowTableRepairFee(this,'RepairFee',1,1)" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span style="display: none">
				<table class="common" style="display: none" id="Component_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="display: none">
								<input type="hidden" name="carLossComponentLossItemCode" style="width: 20px">
								<input type="text" name="prpLcomponentKindCode" class="codecode" style='width: 40px' ondblclick="code_CodeSelect(this,'PolicyKindCode');" onkeyup="code_CodeSelect(this,'PolicyKindCode');">
							</td>
							<%
								if (prpLcarLossDto1.getLossItemCode().trim().equals("1")) {
							%>
							<td class="input">
								<input type="text" name="prpLcomponentKindName" class="codecode" style='width: 70px' ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','none','post');">
							</td>
							<%
								} else {
							%>
							<td class="input">
								<input type="text" name="prpLcomponentKindName" class="codecode" style='width: 70px' ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1','name','selectb','post');"
									onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1','name','selectb','post');">
							</td>
							<%
								}
							%>
							<!--Modify by chenrenda update begin 20050413-->
							<td class="input">
								<select name="prpLcomponentPartCode" styleClass="three" style='width: 50px'>
									<%
										Iterator prpLcomponentList = collection.iterator();
										while (prpLcomponentList.hasNext()) {
											LabelValueBean labelValueBean = (LabelValueBean) prpLcomponentList.next();
									%>
									<option value="<%=labelValueBean.getValue()%>"><%=labelValueBean.getLabel()%></option>
									<%
										}
									%>
								</select>
								<input type="hidden" name="prpLcomponentPartName" value="前部">
							</td>
							<td class="input">
								<input name="prpLcomponentCompName" class="codename" style='width: 60px' ondblclick="return openPrplComponentCompWin(Component_Data,this);">
								<input type="hidden" name="prpLcomponentCompCode" value="9999">
							</td>
							<!--
                 <td class="input">                
                  <input name="prpLcomponentPartDesc" class=common style='width:70px'  
                </td>
                 
                <td class="input">   
                  <input type="text" name="prpLcomponentCompCode" class="codecode" style='width:40px'             
                      ondblclick= "code_CodeSelect(this,'CompCode');"
                      onkeyup= "code_CodeSelect(this,'CompCode');">         
                </td>
                <td class="input">   
                  <input type="text" name="prpLcomponentCompName" class="codecode" style='width:70px'
  			             ondblclick="code_CodeSelect(this, 'CompCode','-1','always','none','post');"
  			             onkeyup= "code_CodeSelect(this, 'CompCode','-1','always','none','post');">      
                </td>
                 -->
							<td class="input">
								<input name="prpLcomponentQuantity" value="1" class=common style='width: 60px' onBlur="getSumDefLoss(this,2);">
							</td>
							<td class="input" style="display: none">
								<input name="prpLcomponentManHourFee" value="0" class=common style='width: 60px' onBlur="getSumDefLoss(this,2);">
							</td>
							<td class="input">
								<input name="prpLcomponentMaterialFee" class=common style='width: 60px' onBlur="getMaterialFee(this,2);">
							</td>
							<td class="input">
								<input name="prpLcomponentQuotedPrice" class=common style='width: 60px'>
							</td>
							<!--Modify by chenrenda add begin 增加残值列 20050414-->
							<td class="input">
								<input name="prpLcomponentRestFee" class=common style='width: 50px' onBlur="getSumDefLoss(this,2);calculateSumRestFee(this);">
							</td>
							<!--Modify by chenrenda add end 增加残值列 20050414-->
							<td class="input">
								<input name="prpLcomponentSumDefLoss" class="readonly" readonly style='width: 60px'>
							</td>
							<td class="input">
								<input name="prpLcomponentRemark" class=common style='width: 60px'>
							</td>
							<input type="hidden" name="prpLcomponentSerialNo">
							<input type="hidden" name="prpLcomponentItemKindNo">
							<input type="hidden" name="prpLcomponentLossItemCode">
							<input type="hidden" name="prpLcomponentLicenseNo">
							<input type="hidden" name="prpLcomponentLicenseColorCode">
							<input type="hidden" name="prpLcomponentCarKindCode">
							<input type="hidden" name="prpLcomponentMakeYear">
							<input type="hidden" name="prpLcomponentGearboxType">
							<input type="hidden" name="prpLcomponentQuoteCompanyGrade">
							<input type="hidden" name="prpLcomponentManageFeeRate">
							<input type="hidden" name="prpLcomponentRepairFactoryCode">
							<input type="hidden" name="prpLcomponentRepairFactoryName">
							<input type="hidden" name="prpLcomponentHandlerCode">
							<input type="hidden" name="prpLcomponentRepairStartDate">
							<input type="hidden" name="prpLcomponentRepairEndDate">
							<input type="hidden" name="prpLcomponentSanctioner">
							<input type="hidden" name="prpLcomponentApproverCode">
							<input type="hidden" name="prpLcomponentOperatorCode">
							<input type="hidden" name="prpLcomponentQueryPrice">
							<input type="hidden" name="prpLcomponentLossRate">
							<input type="hidden" name="prpLcomponentCurrency">
							<input type="hidden" name="prpLcomponentVeriRemark">
							<input type="hidden" name="prpLcomponentVeriQuantity">
							<input type="hidden" name="prpLcomponentVeriManHourFee">
							<input type="hidden" name="prpLcomponentVeriMaterFee">
							<input type="hidden" name="prpLcomponentVeriLossRate">
							<input type="hidden" name="prpLcomponentVeriRestFee">
							<input type="hidden" name="prpLcomponentSumVeriLoss">
							<input type="hidden" name="prpLcomponentFlag">
							<input type="hidden" name="prpLcomponentCompensateBackFlag">
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonComponentDelete" class="smallbutton" onclick="deleteRowTableComponent(this,'Component',1,1)" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="SpanRepairComponent" cellspacing="1" cellpadding="0"> <%
 	CertainLossDto certainLossDto = (CertainLossDto) request.getAttribute("certainLossDto");
 	PrpLrepairFeeDto prpLrepairFeeDto = (PrpLrepairFeeDto) request.getAttribute("prpLrepairFeeDto");
 	PrpLcomponentDto prpLcomponentDto = (PrpLcomponentDto) request.getAttribute("prpLcomponentDto");

 	int carLossSize = certainLossDto.getPrpLcarLossDtoList().size();
 	if (carLossSize > 0) {
 		for (int i = 0; i < carLossSize; i++) {
 			PrpLcarLossDto prpLcarLossDto = (PrpLcarLossDto) certainLossDto.getPrpLcarLossDtoList().get(i);
 			String prpLrepairFeeRepairFactoryCode = "";
 			String prpLrepairFeeRepairFactoryName = "";
 			String prpLrepairFeeRepairStartDate = "";
 			String prpLrepairFeeRepairEndDate = "";
 			String prpLrepairFeeHandlerCode = "";
 			String prpLrepairFeeHandlerName = "";

 			if (prpLrepairFeeDto.getRepairFeeList() != null) {

 				for (int index1 = 0; index1 < prpLrepairFeeDto.getRepairFeeList().size(); index1++) {

 					PrpLrepairFeeDto prpLrepairFeeDto1 = (PrpLrepairFeeDto) prpLrepairFeeDto.getRepairFeeList().get(index1);
 					if (prpLrepairFeeDto1.getLossItemCode().equals(prpLcarLossDto.getLossItemCode())) {
 						prpLrepairFeeRepairFactoryCode = prpLrepairFeeDto1.getRepairFactoryCode();
 						prpLrepairFeeRepairFactoryName = prpLrepairFeeDto1.getRepairFactoryName();
 						prpLrepairFeeRepairStartDate = prpLrepairFeeDto1.getRepairStartDate().toString();
 						prpLrepairFeeRepairEndDate = prpLrepairFeeDto1.getRepairEndDate().toString();
 						prpLrepairFeeHandlerCode = prpLrepairFeeDto1.getHandlerCode();
 						prpLrepairFeeHandlerName = prpLrepairFeeDto1.getHandlerName();
 						//System.out.println("***<@^@>***[经办人4]："+prpLrepairFeeHandlerCode);
 					}
 				}
 			} else if (prpLrepairFeeDto.getRepairFeeList() == null) {
 				prpLrepairFeeHandlerCode = user.getUserCode();
 				prpLrepairFeeHandlerName = user.getUserName();
 			}
 %> <input type="hidden" name="carLossSize" value="<%=carLossSize%>">
				<table class=common cellpadding="5" cellspacing="1">
					<tr>
						<td class="subformtitle" colspan="4">
							<s:text name="certainLoss.claimsVehicles" />
						</td>
						<!--理赔车辆-->
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="certainLoss.carNumber" />
							:
							<!--车辆序号-->
							<input type="hidden" name="prpLcarLossSumManager" value="<%=prpLcarLossDto.getSumManager()%>">
							<input type="hidden" name="prpLcarLossSumVeriRest" value="<%=prpLcarLossDto.getSumVeriRest()%>">
							<input type="hidden" name="prpLcarLossSumVeriManager" value="<%=prpLcarLossDto.getSumVeriManager()%>">
							<input type="hidden" name="prpLcarLossSumCertainLoss" value="<%=prpLcarLossDto.getSumCertainLoss()%>">
							<input type="hidden" name="prpLcarLossSumVerifyLoss" value="<%=prpLcarLossDto.getSumVerifyLoss()%>">
							<input type="hidden" name="prpLcarLossLossDesc" value="<%=prpLcarLossDto.getLossDesc()%>">
							<input type="hidden" name="prpLcarLossIndemnityDuty" value="<%=prpLcarLossDto.getIndemnityDuty()%>">
							<input type="hidden" name="prpLcarLossIndemnityDutyRate" value="<%=prpLcarLossDto.getIndemnityDutyRate()%>">
							<input type="hidden" name="prpLcarLossVeriIndeDutyRate" value="<%=prpLcarLossDto.getVeriIndeDutyRate()%>">
							<input type="hidden" name="prpLcarLossRemark" value="<%=prpLcarLossDto.getRemark()%>">
							<input type="hidden" name="prpLcarLossOperatorCode" value="<%=prpLcarLossDto.getOperatorCode()%>">
							<input type="hidden" name="prpLcarLossApproverCode" value="<%=prpLcarLossDto.getApproverCode()%>">
							<input type="hidden" name="prpLcarLossFlag" value="<%=prpLcarLossDto.getFlag()%>">
						</td>
						<td class="input" style='width: 35%'>
							<input name="prpLcarLossLossItemCode" class="readonly" readonly="true" style='width: 90px' value="<%=prpLcarLossDto.getLossItemCode()%>">
						</td>
						<%
							if (prpLcarLossDto.getLossItemCode().trim().equals("1")) {
						%>
						<td class="title" style="width: 15%">
							<s:text name="certainLoss.thirdCarLoss.car" />
						</td>
						<!--标的车-->
						<!--modify -->
						<td class="input" style="width: 35%">&nbsp;</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLlawsuit.licenseNo" />
							:
						</td>
						<!--号牌号码-->
						<td class="input" style='width: 35%'>
							<input name="prpLcarLossLossItemName" class="readonly" readonly="true" style='width: 90px' value="<%=prpLcarLossDto.getLossItemName()%>">
						</td>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLlawsuit.brandName" />
							:
						</td>
						<!--厂牌型号-->
						<td class="input" style="width: 35%">
							<input name="prpLcarLossBrandName" class="readonly" readonly="true" style='width: 90px' value="<%=prpLcarLossDto.getBrandName()%>">
						</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="certainLoss.thirdCarLoss.carKind" />
							:
						</td>
						<!--车辆种类-->
						<td class="input" style='width: 35%'>
							<input name="prpLcarLossCarKindName" class="readonly" readonly="true" style='width: 90px' value="<%=prpLcarLossDto.getCarKindName()%>">
						</td>
						<td class="title" style="width: 15%">
							<s:text name="db.prpCitem_car.engineNo" />
							:
						</td>
						<!--发动机号-->
						<td class="input" style="width: 35%">
							<input name="prpLcarLossEngineNo" class="readonly" readonly="true" style='width: 90px' value="<%=prpLcarLossDto.getEngineNo()%>">
						</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLCItemCar.frameNo" />
							:
						</td>
						<!--车架号-->
						<td class="input" style='width: 35%'>
							<input name="prpLcarLossFrameNo" class="readonly" readonly="true" style='width: 90px' value="<%=prpLcarLossDto.getFrameNo()%>">
						</td>
						<td class="title" style="width: 15%">
							<s:text name="certainLoss.vincodes" />
							:
						</td>
						<!--VIN码-->
						<td class="input" style="width: 35%">
							<input name="prpLcarLossVINNo" class="readonly" readonly="true" style='width: 160px' value="<%=prpLcarLossDto.getVINNo()%>">
						</td>
					</tr>
					<%
						} else {
					%>
					<td class="title" style="width: 15%">
						<font color="red"><s:text name="certainLoss.thirdCarLoss.thirdCar" /></font>
					</td>
					<!--三者车-->
					<td class="input" style="width: 35%">&nbsp;</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLlawsuit.licenseNo" />
							:
						</td>
						<!--号牌号码-->
						<td class="input" style='width: 35%'>
							<input name="prpLcarLossLossItemName" class="input" style='width: 90px' value="<%=prpLcarLossDto.getLossItemName()%>">
						</td>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLlawsuit.brandName" />
							:
						</td>
						<!--厂牌型号-->
						<td class="input" style="width: 35%">
							<input name="prpLcarLossBrandName" class="input" style='width: 90px' value="<%=prpLcarLossDto.getBrandName()%>">
						</td>
					</tr>
					<tr>
						<td class="common" style="width: 15%">
							<s:text name="certainLoss.thirdCarLoss.carKind" />
							:
						</td>
						<!--车辆种类-->
						<td class="common" style="width: 10%">
							<html:select name="prpLthirdParty1Dto" property="carKindCode">
								<html:options collection="carKindCodes" property="codeCode" labelProperty="codeCName" />
							</html:select>
						</td>
						<td class="title" style="width: 15%">
							<s:text name="db.prpCitem_car.engineNo" />
							:
						</td>
						<!--发动机号-->
						<td class="input" style="width: 35%">
							<input name="prpLcarLossEngineNo" class="input" style='width: 90px' value="<%=prpLcarLossDto.getEngineNo()%>">
						</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLCItemCar.frameNo" />
							:
						</td>
						<!--车架号-->
						<td class="input" style='width: 35%'>
							<input name="prpLcarLossFrameNo" class="input" style='width: 90px' value="<%=prpLcarLossDto.getFrameNo()%>">
						</td>
						<td class="title" style="width: 15%">
							<s:text name="certainLoss.vincodes" />
							:
						</td>
						<!--VIN码-->
						<td class="input" style="width: 35%">
							<input name="prpLcarLossVINNo" class="input" style='width: 160px' value="<%=prpLcarLossDto.getVINNo()%>">
						</td>
					</tr>
					<%
						}
					%>
					<input type="hidden" name="prpLcarLossLicenseColorCode" value="<%=prpLcarLossDto.getLicenseColorCode()%>">
					<input type="hidden" name="prpLcarLossCarKindCode" value="<%=prpLcarLossDto.getCarKindCode()%>">
					<input type="hidden" name="prpLcarLossInsureCarFlagName" value="<%=prpLcarLossDto.getInsureCarFlagName()%>">
					<input type="hidden" name="prpLcarLossInsureCarFlag" value="<%=prpLcarLossDto.getInsureCarFlag()%>">
					<input type="hidden" name="prpLcarLossInsureComName" value="<%=prpLcarLossDto.getInsureComName()%>">
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLcomponent.repairFactoryCode" />
							:
						</td>
						<!--修理厂代码-->
						<td class="input" style='width: 35%'>
							<input name="prpLrepairFeeRepairFactoryCode" class="input" style='width: 90px' value="<%=prpLrepairFeeRepairFactoryCode%>">
						</td>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLcomponent.repairFactoryName" />
							:
						</td>
						<!--修理厂名称-->
						<td class="input" style="width: 35%">
							<input name="prpLrepairFeeRepairFactoryName" class="input" style='width: 220px' value="<%=prpLrepairFeeRepairFactoryName%>">
						</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLcomponent.repairStartDate" />
							:
						</td>
						<!--进厂日期-->
						<td class="input" style='width: 35%'>
							<input name="prpLrepairFeeRepairStartDate" class="input" style='width: 220px' value="<%=prpLrepairFeeRepairStartDate%>">
						</td>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLcomponent.repairEndDate" />
							:
						</td>
						<!--约定交车日期-->
						<td class="input" style="width: 35%">
							<input name="prpLrepairFeeRepairEndDate" class="input" style='width: 220px' value="<%=prpLrepairFeeRepairEndDate%>">
						</td>
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLsalvation.handlerCode" />
							:
						</td>
						<!--经办人代码-->
						<td class="input" style='width: 35%'>
							<input name="prpLrepairFeeHandlerCode" class="codecode" style='width: 90px' value="<%=prpLrepairFeeHandlerCode%>" ondblclick="code_CodeSelect(this, 'HanderCode');"
								onchange="code_CodeChange(this,'HanderCode');" onkeyup="code_CodeSelect(this, 'HanderCode');">
						</td>
						<td class="title" style="width: 15%">
							<s:text name="certainLoss.managerName" />
							:
						</td>
						<!--经办人名称-->
						<td class="input" style="width: 35%">
							<input name="prpLrepairFeeHandlerName" class="codename" style='width: 220px' value="<%=prpLrepairFeeHandlerName%>" ondblclick="code_CodeSelect(this, 'HanderCode','-1','name','none','post');"
								onchange="code_CodeChange(this, 'HanderCode','-1','name','none','post');" onkeyup="code_CodeSelect(this, 'HanderCode','-1','name','none','post');">
					</tr>
					<tr>
						<td class="title" style="width: 15%">
							<s:text name="db.prpLlawsuit.currency" />
							:
						</td>
						<!--币别-->
						<td class="input" style='width: 85%' colspan="3">
							<input name="prpLcarLossCurrencyName" class="readonly" readonly="true" style='width: 220px' value="<s:text name='certainLoss.rmb '/>">
							<!--人民币-->
							<input type="hidden" name="prpLcarLossCurrency" class="readonly" readonly="true" style='width: 220px' value="<%=ConstantCodes.LOCAL_CURRENCY%>">
						</td>
					</tr>
					<td class="title" style="width: 100%" colspan="4"><%@include file="/DAA/certainLoss/DAACertainLossRepairFee.jsp"%></td>
					</tr>
					<tr>
						<td class="title" style="width: 100%" colspan="4"><%@include file="/DAA/certainLoss/DAACertainLossComponent.jsp"%></td>
					</tr>
					<%
						String lossItemCodeN = request.getParameter("lossItemCode");
								String nodeTypeN = request.getParameter("nodeType");
								String display = "none";
								if ("certa".equals(nodeTypeN)) {
									display = "";
								}
					%>
					<!-- <tr STYLE="Display:'<%=display%>'" > -->
					<tr STYLE="Display: none">
						<td class="title" style="width: 15%">
							<s:text name="certainLoss.needComplex" />
							:
						</td>
						<!--是否需要复勘-->
						<td class="input" style='width: 85%' colspan="3">
							<%
								String backCheckYes = "";
										String backCheckNo = "checked";

										if (prpLcarLossDto.getBackCheckFlag().equals("0")) {
											backCheckNo = "checked";
											backCheckYes = "";
										}
										if (prpLcarLossDto.getBackCheckFlag().equals("1")) {
											backCheckYes = "checked";
											backCheckNo = "";
										}

										//modify by 罗畅  暂时屏蔽 复勘 at 2010-06-29
										backCheckYes = "";
										backCheckNo = "checked";
							%>
							<input type="radio" name="prpLcarLossBackCheckFlag" value="1" <%=backCheckYes%>>
							<s:text name="certainLoss.thirdCarLoss.yes" />
							<!--是-->
							<input type="radio" name="prpLcarLossBackCheckFlag" value="0" <%=backCheckNo%>>
							<s:text name="certainLoss.thirdCarLoss.no" />
							<!--否-->
						</td>
					</tr>
					<tr>
						<%
							//System.out.println(prpLcarLossDto.getBackCheckFlag()+"----是否需要修复验车----------");
						%>
					
				</table> <%
 	}
 	}
 %>
			</span>
		</td>
	</tr>
</table>
