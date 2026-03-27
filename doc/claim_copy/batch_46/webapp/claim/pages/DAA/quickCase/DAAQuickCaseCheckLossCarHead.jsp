<table class=common cellpadding="1" cellspacing="1">
	<%
		String readOnly = ""; // 标的车信息只读
		String carName = "三者车"; //区分标的车和三者车
		String butdisabled = ""; // 标的车不允许删除
	%>
	<logic:equal name="prpLthirdPartyDto" property="insureCarFlag" value="1">
		<%
			readOnly = " readOnly ";
			carName = "标的车";
			butdisabled = "disabled";
		%>
	</logic:equal>
	<tr>
		<input name="checkPrpLthirdPartyDtoInsureCarFlag" type="hidden" value="<bean:write name='prpLthirdPartyDto' property='insureCarFlag' filter='true' />">
		<td class="common" style="TEXT-ALIGN: center" colspan=2 style="width:30%" <%=readOnly%>>
			<font color=red><%=carName%></font>
		</td>
		<td class="common" style="width: 10%">
			<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyLicenseNo" />
		</td>
		<!-- 车牌号码： -->
		<td class="common" colspan=3 style="width: 25%">
			<input name="checkPrpLthirdPartyDtoLicenseNo" class="input" <%=readOnly%> maxlength=20 value="<bean:write name='prpLthirdPartyDto' property='licenseNo' filter='true' />"
				onchange="getCarLossLicenseNo(this);" description="车牌号码">
		</td>
		<td class="common" style="width: 12%">
			<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyFrameNo" />
		</td>
		<!-- 车 架 号： -->
		<td class="common" style="width: 23%">
			<input type="text" name="checkPrpLthirdPartyDtoFrameNo" <%=readOnly%> class="input" maxlength=20 description="车架号" value="<bean:write name='prpLthirdPartyDto' property='frameNo' filter='true' />">
		</td>
	</tr>
	<tr>
		<td class="common" style="width: 10%">
			<s:text name="certainLoss.thirdCarLoss.carKind" />
		</td>
		<!-- 车辆种类： -->
		<td class="common" style="width: 20%">
			<html:select name="prpLthirdPartyDto" property="carKindCode" styleClass="one">
				<html:options collection="carKindCodes" property="codeCode" labelProperty="codeCName" />
			</html:select>
		</td>
		<td class="common" style="width: 10%">
			<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyEngineNo" />
		</td>
		<!-- 发动机号： -->
		<td class="common" colspan=3 style="width: 25%">
			<input type="text" name="checkPrpLthirdPartyDtoEngineNo" <%=readOnly%> class="input" maxlength=20 description="发动机号" value="<bean:write name='prpLthirdPartyDto' property='engineNo'/>">
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
			<input type="hidden" name="checkPrpLthirdPartyDtoModelCode" class="codecode" description="厂牌型号" value="<bean:write name='prpLthirdPartyDto' property='modelCode' filter='true' />">
			<input type="text" name="checkPrpLthirdPartyDtoBrandName" <%=readOnly%> class="codename" maxlength=50 description="厂牌型号名称" style="width: 90%"
				value="<bean:write name='prpLthirdPartyDto' property='brandName' filter='true' />">
		</td>
		<td class="title" style="width: 10%">
			<s:text name="certainLoss.thirdCarLoss.ThirdPartyInsureCom" />
		</td>
		<!-- 承保公司： -->
		<td id="ThirdPartyInsureComCodeInput" class="input" colspan=3 style="width: 25%">
			<input name="checkPrpLthirdPartyDtoInsureComCode" <%=readOnly%> class="input" description="承保公司代码" style="width: 30%"
				value="<bean:write name='prpLthirdPartyDto' property='insureComCode' filter='true' />">
			<input type="text" name="checkPrpLthirdPartyDtoInsureComName" <%=readOnly%> class="input" maxlength=50 description="承保公司名称" style="width: 55%"
				value="<bean:write name='prpLthirdPartyDto' property='insureComName' filter='true' />">
		</td>
		<td class="common" style="width: 12%">
			<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyUseYears" />
		</td>
		<!-- 车辆使用年限： -->
		<td class="common" style="width: 23%">
			<input type="input" name="checkPrpLthirdPartyDtoUseYears" <%=readOnly%> class="common" maxlength=5 description="车辆使用年限"
				value="<bean:write name='prpLthirdPartyDto' property='useYears' filter='true' />">
		</td>
	</tr>
	<tr>
		<td class="common" width=10%>
			<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyVINNo " />
		</td>
		<!-- VIN： -->
		<td class="common" width=20%>
			<input type="text" name="checkPrpLthirdPartyDtoVINNo" class="common" <%=readOnly%> value="">
		</td>
		<td class="common" style="width: 10%">
			<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyRunDistance" />
		</td>
		<!-- 行驶公里数： -->
		<td class="common" style="width: 25%" colspan=3>
			<input type="text" name="checkPrpLthirdPartyDtoRunDistance" <%=readOnly%> class="common" description="车辆已行驶公里数" maxlength=15
				value="<bean:write name='prpLthirdPartyDto' property='runDistance' filter='true' />">
		</td>
		<td class="common" id="tdDutyPercentTitle" style="width: 12%;">
			<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyDutyPercent" />
		</td>
		<!-- 责任比例： -->
		<td class="common" id="tdDutyPercentInput" style="width: 23%;">
			<input type="text" name="checkPrpLthirdPartyDtoDutyPercent" <%=readOnly%> class="common" maxlength=6 description="保险车辆对本车责任"
				value="<bean:write name='prpLthirdPartyDto' property='dutyPercent' filter='true' />">
			%
		</td>
	</tr>
	<%
		/*<tr>
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
		 <select name="prpLthirdPartyLossFlag"  >
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
			<input type="text" name="checkPrpLthirdPartyDtoDriverName" class="common" value="<bean:write name='prpLthirdPartyDto' property='driverName' filter='true' />">
		</td>
		<td class="common" style="width: 10%">
			<s:text name="db.prpCinsurednature.sex" />
		</td>
		<!-- 性别 -->
		<td class="common" style="width: 25%" colspan=3>
			<html:select name="prpLthirdPartyDto" property="driverSex" Style="width:70%">
				<html:options collection="driverSexs" property="codeCode" labelProperty="codeCName" />
			</html:select>
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
		<td class="common" id="tdDutyPercentTitle" style="width: 12%;">
			<s:text name="claim.driverComCode" />
		</td>
		<!-- 驾驶员属地 -->
		<td class="common" id="tdDutyPercentInput" style="width: 23%;">
			<input type=text class="codecode" name="checkPrpLthirdPartyDtoApanageCode" style="width: 27%" title="駕駛員屬地代碼" description="駕駛員屬地代碼"
				value="<bean:write name='prpLthirdPartyDto' property='apanageCode' filter='true' />" ondblclick="code_CodeSelect(this,'DriverApanage','0,1','Y');"
				onchange="code_CodeChange(this,'DriverApanage','0,1','Y');" onkeyup="code_CodeSelect(this,'DriverApanage','0,1','Y');">
			<input type=text class="codecode" name="checkPrpLthirdPartyDtoApanage" title="駕駛員屬地" description="駕駛員屬地" style="width: 48%"
				value="<bean:write name='prpLthirdPartyDto' property='apanage' filter='true' />" ondblclick="code_CodeSelect(this,'DriverApanage','-1,0','Y','N');"
				onchange="code_CodeChange(this,'DriverApanage','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'DriverApanage','-1,0','Y','N');">
		</td>
	</tr>
	<tr>
		<td class="common" width=10%>
			<s:text name="db.prpLregist.phoneNumber" />
		</td>
		<!-- 联系电话 -->
		<td class="common" width=20%>
			<input type="text" name="checkPrpLthirdPartyDtoDriverSeaRoute" class="common" description="" maxlength=15 value="<bean:write name='prpLthirdPartyDto' property='driverSeaRoute' filter='true' />">
		</td>
		<td class="common" style="width: 10%">
			<s:text name="db.prpCinsured.identifytype" />
		</td>
		<!-- 证件类型 -->
		<td class="common" style="width: 25%" colspan=3>
			<html:select name="prpLthirdPartyDto" property="drivingCarType" style="width:70%" onchange="checkType(this);">
				<%--<html:option value=" " >未指明 </html:option>--%>
				<html:option value="01">
					<s:text name="quickCase.cardId" />
				</html:option>
				<!-- 身份证 -->
				<html:option value="02">
					<s:text name="quickCase.residenceBooklet" />
				</html:option>
				<!-- 户口簿 -->
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
				<!-- 驾驶执照  -->
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
			<input type="text" name="checkPrpLthirdPartyDtoDrivingLicenseNo" class="common" maxlength='20' description=""
				value="<bean:write name='prpLthirdPartyDto' property='drivingLicenseNo' filter='true' />">
		</td>
	</tr>
</table>