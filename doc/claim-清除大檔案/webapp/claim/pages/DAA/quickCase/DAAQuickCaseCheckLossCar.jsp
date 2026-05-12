<%--table class="common" style="display:none" id="CheckCarPart_Data" cellspacing="1" cellpadding="5">
  <tbody>
    <tr>
      <td class="common" style="wdith:11%"> 
      	<input type="hidden" name="checkPrpLthirdCarLossDtoLicenseNo" value="">
        <input type="hidden"  name="prpLthirdCarLossFlag">
        <input type="text" class="readonly" readonly name="checkPartySerialNo" description="序号" value="">
        <input type="hidden" name="checkPrpLthirdCarLossDtoItemNo" class="common"  maxlength=3 value="">
      </td>
      <td class="common" style="wdith:16%">
      	<html:select name="prpLthirdCarLossDto" property="partCode" styleClass="three" style="width:90%" onchange="getPartName(this);">
									<html:options collection="partCodeList" property="value" labelProperty="label" />
								</html:select>
								<input type="hidden" name="checkPrpLthirdCarLossDtoPartName" value="">
      </td>    
      <td class="common" style="wdith:16%">
        <input name="checkPrpLthirdCarLossDtoCompName" class="codename" 
        	value="" ondblclick="return openCompCodeWin(CheckCarPart,this);">
        <input type="hidden" name="checkPrpLthirdCarLossDtoCompCode" class="codename" 
        	value="">
      </td>
      <td class="common" style="wdith:11%"> 
        <input name="checkPrpLthirdCarLossDtoLossGrade" class="common" 
        	value="">
      </td>
      <td class="common" style="wdith:21%">
        <input name="checkPrpLthirdCarLossDtoLossDesc" class="common" 
        	value="">
      </td> 
      <td class="common" style='width:4%'  align="center">
      	<div>
        	<input type=button name="buttonCheckCarPartDelete"  class="smallbutton" onclick="deleteRow(this,'CheckCarPart')" value="-" style="cursor: hand">
      	</div>
      </td>
    </tr>
  </tbody>
</table--%>
<table class="common" style="width: 100%" id="CheckCarProp_Data" style="display:none" cellspacing="1" cellpadding="5">
	<tbody>
		<tr>
			<td class="common">
				<input type="hidden" class="common" name="checkPrpLthirdPropCarDtoLicenseNo" value="">
				<input type="text" class="readonly" readonly name="checkPropCarSerialNo" description="序号" value="">
				<input type="hidden" name="checkPrpLpropCarserialNo" class="common" maxlength=3 value="">
			</td>
			<td class="common">
				<input name="checkPrpLthirdPropCarDtoLossItemName" class="common" type="text" value="">
				<input name="checkPrpLthirdPropCarDtoLossItemCode" class="common" type="hidden" value="">
			</td>
			<td class="common">
				<input name="checkPrpLthirdPropCarDtoLossItemDesc" class="common" type="text" value="">
			</td>
			<td class="common" style='width: 4%' align="center">
				<div>
					<input type=button name="buttonCheckCarPropDelete" class="smallbutton" onclick="deleteRow(this,'CheckCarProp')" value="-" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tbody>
</table>
<table id="CheckCar_Data" class="common" style="display: none" align="center" cellspacing="1" cellpadding="0">
	<tbody>
		<tr>
			<td class="common" style="width: 4%">
				<input type="text" class="readonly" readonly name="checkPrpLthirdPartyDtoSerialNo" value="">
				<input type="hidden" class="common" name="checkRelateSerialNo" value="">
				<input type="hidden" name="checkCarAdd" value="Y">
			</td>
			<td class="common" style="width: 90%">
				<table class=common cellpadding="1" cellspacing="1">
					<tr>
						<input name="checkPrpLthirdPartyDtoInsureCarFlag" type="hidden" value="">
						<td class="common" style="TEXT-ALIGN: center" colspan=2 style="width:30%">
							<font color=red><s:text name="certainLoss.thirdCarLoss.thirdCar" /></font>
						</td>
						<!-- 三者车 -->
						<td class="common" style="width: 10%">
							<s:text name="certainLoss.prpLcheck.lossItemName" />
						</td>
						<!-- 车牌号码： -->
						<td class="common" colspan=3 style="width: 25%">
							<input name="checkPrpLthirdPartyDtoLicenseNo" class="input" maxlength=20 description="车牌号码" value="">
						</td>
						<td class="common" style="width: 12%">
							<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyFrameNo" />
						</td>
						<!-- 车 架 号： -->
						<td class="common" style="width: 23%">
							<input type="text" name="checkPrpLthirdPartyDtoFrameNo" class="input" maxlength=20 description="车架号" value="">
						</td>
					</tr>
					<tr>
						<td class="common" style="width: 10%">
							<s:text name="certainLoss.thirdCarLoss.carKind" />
						</td>
						<!-- 车辆种类： -->
						<td class="common" style="width: 20%"></td>
						<td class="common" style="width: 10%">
							<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyEngineNo" />
						</td>
						<!-- 发动机号： -->
						<td class="common" colspan=3 style="width: 25%">
							<input type="text" name="checkPrpLthirdPartyDtoEngineNo" value="" class="input" maxlength=20 description="发动机号">
						</td>
						<td class="title" style="width: 12%">
							<s:text name="certainLoss.thirdCarLoss.licenseColor" />
						</td>
						<!-- 号牌底色： -->
						<td class="input" style="width: 23%">
							<html:select name="prpLthirdPartyDto" property="licenseColorCode">
								<html:options collection="licenseColorCodes" property="codeCode" labelProperty="codeCName" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="title" style="width: 10%">
							<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyBrandName" />
						</td>
						<!-- 厂牌型号： -->
						<td id="prpLthirdPartyBrandName" class="common" style="width: 20%">
							<input type="hidden" name="checkPrpLthirdPartyDtoModelCode" class="codecode" description="厂牌型号" value="" ondblclick="code_CodeSelect(this,'modelCode','0,1','Y');"
								onchange="code_CodeChange(this,'modelCode','0,1','Y');" onkeyup="code_CodeSelect(this,'modelCode','0,1','Y');">
							<input type="text" name="checkPrpLthirdPartyDtoBrandName" class="codename" maxlength=50 description="厂牌型号名称" style="width: 90%" value=""
								ondblclick="code_CodeSelect(this,'modelCode','-1,0','Y','N');" onchange="code_CodeChange(this,'modelCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'modelCode','-1,0','Y','N');">
						</td>
						<td class="title" style="width: 10%">
							<s:text name="certainLoss.thirdCarLoss.ThirdPartyInsureCom" />
						</td>
						<!-- 承保公司： -->
						<td id="ThirdPartyInsureComCodeInput" class="input" colspan=3 style="width: 25%">
							<input name="checkPrpLthirdPartyDtoInsureComCode" class="input" description="承保公司代码" style="width: 30%" value="">
							<input type="text" name="checkPrpLthirdPartyDtoInsureComName" class="input" maxlength=50 description="承保公司名称" style="width: 55%" value="">
						</td>
						<td class="common" style="width: 12%">
							<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyUseYears" />
						</td>
						<!-- 车辆使用年限： -->
						<td class="common" style="width: 23%">
							<input type="input" name="checkPrpLthirdPartyDtoUseYears" class="common" maxlength=5 description="车辆使用年限" value="">
						</td>
					</tr>
					<tr>
						<td class="common" width=10%>
							<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyVINNo" />
						</td>
						<!-- 	VIN： -->
						<td class="common" width=20%>
							<input type="text" name="checkPrpLthirdPartyDtoVINNo" class="common" value="">
						</td>
						<td class="common" style="width: 10%">
							<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyRunDistance" />
						</td>
						<!-- 行驶公里数： -->
						<td class="common" style="width: 25%" colspan=3>
							<input type="text" name="checkPrpLthirdPartyDtoRunDistance" class="common" description="车辆已行驶公里数" maxlength=15 value="">
						</td>
						<td class="common" id="tdDutyPercentTitle" style="width: 12%;">
							<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyDutyPercent" />
						</td>
						<!-- 责任比例： -->
						<td class="common" id="tdDutyPercentInput" style="width: 23%;">
							<input type="text" name="checkPrpLthirdPartyDtoDutyPercent" class="common" maxlength=6 description="保险车辆对本车责任" value="">
							%
						</td>
					</tr>
					<%
						/*
						 <tr>
						 <td class="common" style="width:10%">交强险责任比率</td>
						 <td class="common" style="width:20%"></td>
						 </tr>
						
						 <tr>
						 <td class="title" style="width:10%">损失金额:</td>
						 <td class="input" style="width:20%">
						 <input type="text" name="checkPrpLthirdPartyDtoLossFee" class="common" 
						 value="" ></td>
						 <td class="title" >本车是否受损：</td>
						 <td class="input" style="width:10%">
						 <select name="checkPrpLthirdPartyDtoLossFlag"  >
						 <option value=""  > </option>
						 <option value="1" selected >是</option>
						 <option value="0"  >否</option></select></td>
						 <td class="input" colspan="5" /> </td>
						 </tr>*/
					%>
					<tr>
						<td class="common" width=10%>
							<s:text name="db.prpDDriver.driverName" />
						</td>
						<!-- 驾驶员姓名 -->
						<td class="common" width=20%>
							<input type="text" name="checkPrpLthirdPartyDtoDriverName" class="common" value="">
						</td>
						<td class="common" style="width: 10%">
							<s:text name="db.prpCinsurednature.sex" />
						</td>
						<!-- 性别 -->
						<td class="common" style="width: 25%" colspan='3'>
							<html:select name="prpLdriverDto" property="driverSex" Style="width:70%">
								<html:options collection="driverSexs" property="codeCode" labelProperty="codeCName" />
							</html:select>
							<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
						<td class="common" style="width: 12%">
							<s:text name="claim.driverComCode" />
						</td>
						<!-- 驾驶员属地 -->
						<td class="common" style="width: 23%">
							<input type=text class="codecode" name="checkPrpLthirdPartyDtoApanageCode" style="width: 27%" title="駕駛員屬地代碼" description="駕駛員屬地代碼" ondblclick="code_CodeSelect(this,'DriverApanage','0,1','Y');"
								onchange="code_CodeChange(this,'DriverApanage','0,1','Y');" onkeyup="code_CodeSelect(this,'DriverApanage','0,1','Y');">
							<input type=text class="codecode" name="checkPrpLthirdPartyDtoApanage" title="駕駛員屬地" description="駕駛員屬地" style="width: 48%" ondblclick="code_CodeSelect(this,'DriverApanage','-1,0','Y','N');"
								onchange="code_CodeChange(this,'DriverApanage','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'DriverApanage','-1,0','Y','N');">
						</td>
					</tr>
					<tr>
						<td class="common" style="width: 10%">
							<s:text name="db.prpLregist.phoneNumber" />
						</td>
						<!-- 联系电话 -->
						<td class="common" style="width: 20%">
							<input type="text" name="checkPrpLthirdPartyDtoDriverSeaRoute" value="" class="common" description="" maxlength=15>
						</td>
						<td class="common" style="width: 10%">
							<s:text name="db.prpCinsured.identifytype" />
						</td>
						<!-- 证件类型 -->
						<td class="common" style="width: 25%" colspan='3'>
							<html:select name="prpLdriverDto" property="drivingCarType" style="width:70%">
								<%--<html:option value=" " >未指明 </html:option>--%>
								<html:option value="01">
									<s:text name="quickCase.cardId" />
								</html:option>
								<!-- 身份证      -->
								<html:option value="02">
									<s:text name="quickCase.residenceBooklet" />
								</html:option>
								<!-- 户口簿  -->
								<html:option value="03">
									<s:text name="quickCase.passport" />
								</html:option>
								<!-- 护照 -->
								<html:option value="04">
									<s:text name="quickCase.come" />
								</html:option>
								<!-- 军官证 -->
								<html:option value="05">
									<s:text name="quickCase.drivingLicense" />
								</html:option>
								<!-- 驾驶执照 -->
								<html:option value="06">
									<s:text name="quickCase.returnCard" />
								</html:option>
								<!-- 返乡证 -->
								<html:option value="99">
									<s:text name="check.other" />
								</html:option>
								<!-- 其它 -->
							</html:select>
							<img src="/claim/images/bgMarkMustInput.jpg">
						</td>
						<td class="common" id="tdDutyPercentTitle" style="width: 12%;">
							<s:text name="db.prpLdriver.identifyNumber" />
						</td>
						<!-- 证件号码 -->
						<td class="common" id="tdDutyPercentInput" style="width: 23%;">
							<input type="text" name="checkPrpLthirdPartyDtoDrivingLicenseNo" class="common" maxlength='20' description="" value="">
						</td>
					</tr>
				</table>
				<%
					/*
					 <table class="common" style="width:100%" id="CheckCarPart" cellspacing="1" cellpadding="5">
					 <thead>
					 <tr class="listtitle">
					 <td style="width:9%">损失序号</td>
					 <td style="width:22%">损失部位</td>
					 <td style="width:22%">零件(项目)名称</td>
					 <td style="width:22%">损失程度级别</td>
					 <td style="width:21%">损失程度描述</td>
					 <td style="width:4%" >&nbsp;</td> 
					 </tr>
					 </thead>
					 <tfoot>
					 <tr>
					 <td class="title" colspan=5 >(按"+"号键增加损失部位信息，按"-"号键删除信息)</td>
					 <td class="title" align="right" style="width:4%">
					 <div align="center">
					 <input type="button" value="+" class=smallbutton onclick="insertRowTableOfCheckCarPart('CheckCarPart','CheckCarPart_Data',this)" name="buttonCheckCarPartInsert" style="cursor: hand">
					 </div>
					 </td>
					 </tr>
					 </tfoot> 
					 <tbody>
					 </tbody>
					 </table>
					 <table class="common" style="width:100%" id="CheckCarProp" cellspacing="1" cellpadding="5">
					 <thead>
					 <tr class="listtitle">
					 <td style="width:9%">损失序号</td>
					 <td style="width:27%">损失名称</td>
					 <td style="width:60%">损失程度描述</td>
					 <td style="width:4%" >&nbsp;</td> 
					 </tr>
					 </thead>
					 <tfoot>
					 <tr>
					 <td class="title" colspan=3 >(按"+"号键增加损失部位信息，按"-"号键删除信息)</td>
					 <td class="title" align="right" style="width:4%">
					 <div align="center">
					 <input type="button" value="+" class=smallbutton onclick="insertRowTableOfCheckCarProp('CheckCarProp','CheckCarProp_Data',this)" name="buttonCheckCarPropInsert" style="cursor: hand">
					 </div>
					 </td>
					 </tr>
					 </tfoot> 
					 <tbody>
					 </tbody>
					 </table>
					 */
				%>
			</td>
			<td class="title" align="right" style="width: 4%">
				<input type="button" class=smallbutton value="-" onclick="isDelete(this);" name="buttonCheckCarDelete">
			</td>
	</tbody>
</table>
<table id="CheckCar" name="CheckCar" class="common" align="center" cellspacing="1" cellpadding="0">
	<thead>
		<tr class=listtitle>
			<td style="width: 4%">
				<s:text name="db.prpLprop.serialNo" />
			</td>
			<!-- 序号 -->
			<td style="width: 96%" colspan="2">
				<s:text name="certainLoss.thirdCarLoss.prpLcheckContent" />
			</td>
			<!-- 内　　容  -->
		</tr>
	</thead>
	<tfoot>
		<tr>
			<td class="title" colspan=3 align="right" style="width: 4%">
				<input type="button" <%=buttonReaOnly%> class=smallbutton value="+" onclick="insertRowTableOfCheckCar('CheckCar','CheckCar_Data',this);" name="buttonCheckCarInsert">
			</td>
		</tr>
	</tfoot>
	<tbody>
		<logic:notEmpty name="checkDto" property="prpLthirdPartyDtoList">
			<%
				String hiddenButdisabled = "";
					int index = 0;
			%>
			<logic:iterate id="prpLthirdPartyDto" name="checkDto" property="prpLthirdPartyDtoList">
				<%
					ArrayList delete = (ArrayList) request.getAttribute("delete");
							System.out.println("&&&index=" + index);
							if (delete != null) {
								hiddenButdisabled = (String) delete.get(index);
								System.out.println("&&&hiddenButdisabled+" + hiddenButdisabled);
							}
				%>
				<tr>
					<td class="common" style="width: 4%">
						<input type="text" class="readonly" readonly name="checkPrpLthirdPartyDtoSerialNo" value="<bean:write name='prpLthirdPartyDto' property='serialNo' filter='true' />">
						<input type="hidden" class="common" name="checkRelateSerialNo" value="0">
						<input type="hidden" name="checkCarAdd" value="N">
					</td>
					<td class="common" style="width: 90%">
						<%----%>
						<%@include file="/DAA/quickCase/DAAQuickCaseCheckLossCarHead.jsp"%>
						<%----%>
						<%
							//@include file="/DAA/quickCase/DAAQuickCaseCheckLossCarPart.jsp"
						%>
						<%----%>
						<%
							//@include file="/DAA/quickCase/DAAQuickCaseCheckLossCarProp.jsp"
						%>
					</td>
					<td class="title" align="right" style="width: 4%">
						<input type="hidden" name="buttonFlag" value='<%=hiddenButdisabled%>'>
						<input type="button" class=smallbutton <%=butdisabled%> value="-" onclick="beforeDelectCheckCarRow(this,'CheckCar')" name="buttonCheckCarDelete">
					</td>
				</tr>
				<%
					index++;
				%>
			</logic:iterate>
		</logic:notEmpty>
	</tbody>
</table>
