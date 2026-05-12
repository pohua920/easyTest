<%@ page contentType="text/html; charset=GBK"%>
<html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<%@ include file="/common/meta_js.jsp"%>
<script>
	</script>
<head>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoBank.js"></script>
<title><s:text name="account.payObject" /></title>
<!-- 支付对象 -->
</head>
<body style="overflow: hidden" onload="fm.buttonSubmit.disabled = true;">
	<form name="fm" method="post" autocomplete="off">
		<input type="hidden" name="actionType" value="<c:out value='${param.actionType}'/>">
		<input type="hidden" name="comType" value="<c:out value='${param.comType}'/>">
		<input type="hidden" name="businessNo" value="<c:out value='${param.businessNo}'/>">
		<input type="hidden" name="businessType" value="<c:out value='${param.businessType}'/>">
		<input type="hidden" name="saveType">
		<input type="hidden" name="registNo" value="<c:out value='${param.registNo}'/>">
		<input type="hidden" name="serialNo" value="<c:out value='${param.serialNo}'/>">
		<input type="hidden" name="comCodeForAcc" value="<c:out value='${sessionScope.user.comCode}'/>">
		<input type="hidden" name="userName" value="<c:out value='${sessionScope.user.userName}'/>">
		<input type="hidden" name="certificateCode" value="<c:out value='${param.certificateCode}'/>">
		<input type="hidden" name="uniformNo" value="<c:out value='${param.uniformNo}'/>">
		<input type="hidden" name="accountCode" value="<c:out value='${param.accountCode}'/>">
		<table border="0" cellpadding="5" cellspacing="1" class="subtable">
			<tr>
				<td colspan="4" align="center" class="common">
					<strong><s:text name="account.payObject" /> </strong>
				</td>
				<!-- 支付对象 -->
			</tr>
			<tr>
				<td width="20%" class="left"><s:text name="account.accountOwnershipCertificateType" /></td>
				<td width="30%" class="right">
					<s:select name="prpdpaymentaccountCertificateType" id="prpdpaymentaccountCertificateType" class="input" value="#attr.prpdpaymentaccountDto.certificateType" listKey="key" listValue="value"
						list="#request.prpdpaymentaccountCertificateTypeList" />
					<c:if test="${not empty param.certificateCode}">
						<script type="text/javascript">
							$("#prpdpaymentaccountCertificateType").val("${param.certificateCode}");
						</script>
					</c:if>
				</td>
				<td width="20%" class="left"><s:text name="account.accountOwnershipPersonCode" /></td>
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountCertificateCode" id="prpdpaymentaccountCertificateCode" maxlength="20" style="width:120px" value="<c:out value='${param.uniformNo}' />">
					<img src="/claim/images/imgMustInput.gif" />
					<input type="button" class="bigbutton" name="search" value="查詢" style="width: 60px"
						onClick="queryByCertificateCode();">
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">
					<s:text name="db.prpLcompensate.account" />：
				</td>
				<!-- 银行帐号： -->
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountAccountCode" maxlength="50" onChange="fm.buttonSubmit.disabled = true;"
						value="<c:out value='${requestScope.prpdpaymentaccountDto.accountCode}' />">
					<img src="/claim/images/imgMustInput.gif" />
					<input type="hidden" class="input" name="prpdpaymentaccountAccountCurrency" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
					<a href="javascript:checkAccountNo(fm.prpdpaymentaccountAccountCode.value,'<c:out value='${requestScope.serialNo}'/>','<c:out value='${requestScope.prpdpaymentaccountDto.registNo}' />');"><U>检测帳号是否存在</U>
					</a>
				</td>
				<td width="20%" class="left">
					<s:text name="compensate.accountCurrencyType" />：
				</td>
				<!-- 帳户类型： -->
				<td width="30%" class="right">
					<select name="prpdpaymentaccountAccountType" class="input">
						<option value="1" <c:if test="${requestScope.prpdpaymentaccountDto.accountType=='1'}"><c:out value="selected"/></c:if>>
							<s:text name="compensate.passbook" />
						</option>
						<!-- 存折 -->
						<option value="2" <c:if test="${requestScope.prpdpaymentaccountDto.accountType=='2'}"><c:out value="selected"/></c:if>>
							<s:text name="compensate.creditCard" />
						</option>
						<!-- 信用卡 -->
						<option value="3" <c:if test="${requestScope.prpdpaymentaccountDto.accountType=='3'}"><c:out value="selected"/></c:if>>
							<s:text name="compensate.CARDS" />
						</option>
						<!-- 储值卡 -->
						<option value="4" <c:if test="${requestScope.prpdpaymentaccountDto.accountType=='4'}"><c:out value="selected"/></c:if>>
							<s:text name="regist.prpLregist.other" />
						</option>
						<!-- 其他 -->
					</select>
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">
					<s:text name="account.headquartersCode" />
					<!-- 总行代码： -->
				</td>
				<td width="30%" class="right">
					<input name="prpdpaymentaccountBankCode" type="text" class="readonly" readonly onkeyup="getBank(this,'codeCode','0,1','1');" onblur="isBank(this,'codeCode','1');"
						value='${requestScope.prpdpaymentaccountDto.bankCode}' />
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">總行名稱：</td>
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountBankName" onkeyup="getBank(this,'codeName','-1,0','1');" value='${requestScope.prpdpaymentaccountDto.bankName}' />
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">分行代號:</td>
				<!-- 分行代號： -->
				<td width="30%" class="right">
					<input type="text" class="input" id="prpdpaymentaccountCustomBankCode" name="prpdpaymentaccountCustomBankCode" maxlength="10" onkeyup="getBank(this,'codeCode','0,1,-2,-1','2');"
						onblur="isBank(this,'codeCode','2');" value="<c:out value='${requestScope.prpdpaymentaccountDto.customBankCode}' />">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">分行名稱:</td>
				<!-- 分行名稱： -->
				<td width="30%" class="right">
					<input type="text" class="input" id="prpdpaymentaccountCustomBankName" name="prpdpaymentaccountCustomBankName" maxlength="100" onblur="isBank(this,'codeName','2');"
						onkeyup="getBank(this,'codeName','-1,0,-3,-2','2');" value="<c:out value='${requestScope.prpdpaymentaccountDto.customBankName}' />">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">
					<s:text name="account.accountName" />
				</td>
				<!-- 帳户名称： -->
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountAccountName" maxlength="120" value="<c:out value='${requestScope.prpdpaymentaccountDto.accountName}' />">
				</td>
				<td width="20%" class="left">
					<s:text name="db.prpDcustomer_Unit.customerCode" />：
				</td>
				<!-- 客户代码： -->
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountCustomerCode" maxlength="20" value="<c:out value='${requestScope.prpdpaymentaccountDto.customerCode}' />">
				</td>
			</tr>
			<tr style='display: none'>
				<td width="20%" class="left">
					<s:text name="db.prpUserGrade.userCode" />:
				</td>
				<!-- 员工代码： -->
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountUserCode" value="<c:out value='${requestScope.prpdpaymentaccountDto.userCode}' />">
				</td>
				<td width="20%" class="left">
					<s:text name="account.maintenanceUnitCode" />
				</td>
				<!-- 维修单位代码： -->
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountVehicleComCode" value="<c:out value='${requestScope.prpdpaymentaccountDto.vehicleComCode}' />">
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">
					<s:text name="account.accountOwnershipAttribute" />
				</td>
				<!-- 帳户归属人属性 ： -->
				<td width="30%" class="right">
					<select name="prpdpaymentaccountOwnerType" class="input">
						<option value="1" <c:if test="${requestScope.prpdpaymentaccountDto.ownerType=='1'}"><c:out value="selected"/></c:if>>
							<s:text name="account.personal" />
						</option>
						<!-- 个人 -->
						<option value="2" <c:if test="${requestScope.prpdpaymentaccountDto.ownerType=='2'}"><c:out value="selected"/></c:if>>
							<s:text name="account.enterprise" />
						</option>
						<!-- 企业 -->
					</select>
				</td>
				<td width="20%" class="left">
					<s:text name="account.accountOwnershipPersonName" />
				</td>
				<!-- 帳户归属人姓名(支付对象帳户名称)： -->
				<td width="30%" class="right">
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type="text" class="input" name="prpdpaymentaccountOwnerName" maxlength="100" value="<c:out value='${requestScope.prpdpaymentaccountDto.ownerName}' />">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">
					<s:text name="account.accountOwnershipPhoneNumber" />
				</td>
				<!-- 帳户归属人联系电话： -->
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountOwnerPhoneNo" maxlength="30" value="<c:out value='${requestScope.prpdpaymentaccountDto.ownerPhoneNo}' />">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">
					<s:text name="account.operatorCode" />
				</td>
				<!-- 操作人员代码： -->
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorCode" value="<c:out value='${requestScope.prpdpaymentaccountDto.operatorCode}' />">
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">
					<s:text name="account.operationsPeople" />
				</td>
				<!-- 操作人归属机构： -->
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorComcode" value="<c:out value='${requestScope.prpdpaymentaccountDto.operatorComCode}' />">
				</td>
				<td width="20%" class="left">
					<s:text name="account.operationsPeopleName" />
				</td>
				<!-- 操作人员姓名： -->
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorName" value="<c:out value='${requestScope.prpdpaymentaccountDto.operatorName}' />">
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">
					<s:text name="account.firstCollectionDate" />
				</td>
				<!-- 第一次采集日期： -->
				<td width="30%" class="right">
						<rc:rcDate class="readonly" readonly="readonly" wdatePicker="false"
							name="prpdpaymentaccountOperateDate"
							value="${requestScope.prpdpaymentaccountDto.operateDate}"/>
				</td>
				<td width="20%" class="left">
					<s:text name="account.updateDate" />
				</td>
				<!-- 更新日期： -->
				<td width="30%" class="right">
					<rc:rcDate class="readonly" readonly="readonly" wdatePicker="false"
							name="prpdpaymentaccountUpdateDate"
							value="${requestScope.prpdpaymentaccountDto.updateDate}"/>
				</td>
			</tr>
			<tr style='display: none'>
				<td width="20%" class="left">
					<s:text name="account.theProcedure" />
					<br> <br>
				</td>
				<!-- 采集环节： -->
				<td width="30%" class="right">
					<input type="text" class="hidden" name="prpdpaymentaccountOperateSys" value="<c:out value='${requestScope.prpdpaymentaccountDto.operateSys}' />">
					<br>
				</td>
				<td width="20%" class="left">
					<s:text name="account.whetherUsedPaid" />
					<br> <br>
				</td>
				<td width="30%" class="right">
					<input type="text" class="hidden" name="prpdpaymentaccountUsedOrNot" value="<c:out value='${requestScope.prpdpaymentaccountDto.usedOrNot}' />">
					<br>
				</td>
			</tr>
				<input type="hidden" class="common" name="prpdpaymentaccountCompensateOwnerName" maxlength="24" value="<c:out value='${prpdpaymentaccount.compensateOwnerName}'/>">
				<input type="hidden" class="common" name="prpdpaymentaccountUniformNo" maxlength="25" value="<c:out value='${prpdpaymentaccount.uniformNo}'/>">
			<tr>
				<td width="20%" class="left">郵遞區號:</td>
				<!-- 邮政区号 -->
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountAreaCode" maxlength="20" value="<c:out value='${prpdpaymentaccount.areaCode}'/>">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">郵政地址:</td>
				<!-- 邮政地址 -->
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountCourierAddress" maxlength="100" value="<c:out value='${prpdpaymentaccount.courierAddress}'/>">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">
					<s:text name="db.prpDcompany.remark" />
					：
				</td>
				<!-- 备注： -->
				<td width="80%" class="right" colspan="3">
					<input type="text" class="input" name="prpdpaymentaccountRemark" maxlength="500" value="<c:out value='${requestScope.prpdpaymentaccountDto.remark}' />">
					<input type="hidden" name="prpdpaymentaccountValidStatus" value="<c:out value='${requestScope.prpdpaymentaccountDto.validStatus}' />">
				</td>
			</tr>
		</table>
		<br />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr align="center">
				<td>
					<input type="button" class="button" name="buttonSubmit" value="<s:text name="button.submit.value"/>" onClick="submitPaymentaccount();">
					<c:if test="${requestScope.liWaiFlag=='1'}">
						&nbsp;&nbsp;
						<input type="button" class="button" name="buttonLiWai" value="<s:text name="button.casesOutside.value"/>" onClick="LiWai();">
					</c:if>
				</td>
			</tr>
		</table>
		<div id="bankList" style="margin:0; padding:5px;border: #acacac 1px solid;background-color: FFFFFF; display: none; cursor: hand; position: absolute; width: 400px;overflow: auto;" align="left"></div>
	</form>
</body>
</html>
