<%@ page contentType="text/html; charset=GBK"%>
<%--
**************************************************************************
* DESC       ：理算录入支付帳号页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-03-4
* MODIFYLIST ：   Name       Date            Reason/Contents
**************************************************************************
--%>
<html>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoBank.js"></script>
<title>支付对象</title>
</head>
<body style="overflow: hidden" onload="fm.buttonSubmit.disabled = true;">
	<form name="fm" method="post" autocomplete="off">
		<input type="hidden" name="actionType" value="<c:out value='${param.actionType}' />">
		<input type="hidden" name="comType" value="<c:out value='${param.comType}' />">
		<input type="hidden" name="businessNo" value="<c:out value='${param.businessNo}' />">
		<input type="hidden" name="businessType" value="<c:out value='${param.businessType}' />">
		<input type="hidden" name="saveType">
		<input type="hidden" name="registNo" value="<c:out value='${requestScope.prpdpaymentaccountDto.registNo}' />">
		<input type="hidden" name="serialNo" value="<c:out value='${param.serialNo}' />">
		<input type="hidden" name="comCodeForAcc" value="<c:out value='${sessionScope.user.comCode}' />">
		<input type="hidden" name="userName" value="<c:out value='${sessionScope.user.userName}' />">
		<input type="hidden" name="certificateCode" value="<c:out value='${param.certificateCode}'/>">
		<input type="hidden" name="uniformNo" value="<c:out value='${param.uniformNo}'/>">
		<input type="hidden" name="accountCode" value="<c:out value='${param.accountCode}'/>">
		<table border="0" cellpadding="5" cellspacing="1" class="subtable">
			<tr>
				<td colspan="4" align="center" class="common">
					<strong>支付對象</strong>
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">帳號歸屬人證件類型：</td>
				<td width="30%" class="right">
					<s:select name="prpdpaymentaccountCertificateType" id="prpdpaymentaccountCertificateType" class="input" value="#attr.prpdpaymentaccountDto.certificateType" listKey="key" listValue="value"
						list="#request.prpdpaymentaccountCertificateTypeList" />
					<c:if test="${not empty param.certificateCode}">
						<script type="text/javascript">
							$("#prpdpaymentaccountCertificateType").val("${param.certificateCode}");
						</script>
					</c:if>
				</td>
				<td width="20%" class="left">帳號歸屬人證件代碼：</td>
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountCertificateCode" id="prpdpaymentaccountCertificateCode" maxlength="20" style="width:120px" value="<c:out value='${param.uniformNo}' />">
					<img src="/claim/images/imgMustInput.gif" />
					<input type="button" class="bigbutton" name="search" value="查詢" style="width: 60px"
						onClick="queryByCertificateCode();">
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">銀行帳號：</td>
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountAccountCode" maxlength="50" onChange="fm.buttonSubmit.disabled = true;"
						value="<c:out value='${requestScope.prpdpaymentaccountDto.accountCode}'/>">
					<img src="/claim/images/imgMustInput.gif" />
					<input type="hidden" class="common" name="prpdpaymentaccountAccountCurrency" value="<c:out value='${requestScope.prpdpaymentaccountDto.accountCurrency}'/>">
					<a href="javascript:checkAccountNo(fm.prpdpaymentaccountAccountCode.value,'<c:out value='${requestScope.serialNo}'/>','<c:out value='${requestScope.prpdpaymentaccountDto.registNo}'/>');"><U>檢測帳號是否存在</U></a>
				</td>
				<td width="20%" class="left">帳戶類型：</td>
				<td width="30%" class="right">
					<select name="prpdpaymentaccountAccountType" class="input">
						<option value="1" lable="存摺" <c:if test="${requestScope.prpdpaymentaccountDto.accountType=='1'}"><c:out value="selected"/></c:if>>存摺</option>
						<option lable="信用卡" value="2" <c:if test="${requestScope.prpdpaymentaccountDto.accountType=='2'}"><c:out value="selected"/></c:if>>信用卡</option>
						<option lable="儲值卡" value="3" <c:if test="${requestScope.prpdpaymentaccountDto.accountType=='3'}"><c:out value="selected"/></c:if>>儲值卡</option>
						<option lable="其他" value="4" <c:if test="${requestScope.prpdpaymentaccountDto.accountType=='4'}"><c:out value="selected"/></c:if>>其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">總行代碼：</td>
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountBankCode" onkeyup="getBank(this,'codeCode','0,1','1');" onblur="isBank(this,'codeCode','1');"
						value="<c:out value='${prpdpaymentaccountDto.bankCode}'/>" />
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">總行名稱：</td>
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountBankName" onkeyup="getBank(this,'codeName','-1,0','1');" value="<c:out value='${prpdpaymentaccountDto.bankName}'/>">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">分行代號:</td>
				<!-- 分行代號： -->
				<td width="30%" class="right">
					<input type="text" class="input" id="prpdpaymentaccountCustomBankCode" name="prpdpaymentaccountCustomBankCode" maxlength="10" onblur="isBank(this,'codeCode','2');"
						value="<c:out value='${requestScope.prpdpaymentaccountDto.customBankCode}' />" onkeyup="getBank(this,'codeCode','0,1,-2,-1','2');">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">分行名稱：</td>
				<td width="30%" class="right">
					<input type="text" class="input" maxlength="100" id="prpdpaymentaccountCustomBankName" name="prpdpaymentaccountCustomBankName" onblur="isBank(this,'codeName','2');"
						value="<c:out value='${requestScope.prpdpaymentaccountDto.customBankName}' />" onkeyup="getBank(this,'codeName','-1,0,-3,-2','2');">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">帳戶名稱：</td>
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountAccountName" maxlength="120" value="<c:out value='${requestScope.prpdpaymentaccountDto.accountName}'/>">
				</td>
				<td width="20%" class="left">客戶代碼：</td>
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountCustomerCode" maxlength="20" value="<c:out value='${requestScope.prpdpaymentaccountDto.customerCode}'/>">
				</td>
			</tr>
			<tr style='display: none'>
				<td width="20%" class="left">員工代碼：</td>
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountUserCode" value="<c:out value='${requestScope.prpdpaymentaccountDto.userCode}'/>">
				</td>
				<td width="20%" class="left">維修單位代碼：</td>
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountVehicleComCode" value="<c:out value='${requestScope.prpdpaymentaccountDto.vehicleComCode}'/>">
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">帳戶歸屬人屬性：</td>
				<td width="30%" class="right">
					<select name="prpdpaymentaccountOwnerType" class="input">
						<option value="1" <c:if test="${requestScope.prpdpaymentaccountDto.ownerType=='1'}"><c:out value="selected"/></c:if>>
							<s:text name="common.personal" />
						</option>
						<option value="2" <c:if test="${requestScope.prpdpaymentaccountDto.ownerType=='2'}"><c:out value="selected"/></c:if>>
							<s:text name="common.enterprise" />
						</option>
					</select>
				</td>
				<td width="20%" class="left">帳戶歸屬人姓名(支付對象帳戶名稱)：</td>
				<td width="30%" class="right">
					<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
					<input type="text" class="input" name="prpdpaymentaccountOwnerName" maxlength="100" value="<c:out value='${requestScope.prpdpaymentaccountDto.ownerName}' />">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">帳戶歸屬人聯繫電話：</td>
				<td width="30%" class="right">
					<input type="text" class="input" name="prpdpaymentaccountOwnerPhoneNo" maxlength="30" id="prpdpaymentaccountOwnerPhoneNoId"
						value="<c:out value='${requestScope.prpdpaymentaccountDto.ownerPhoneNo}'/>">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">操作人員代碼：</td>
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorCode" value="<c:out value='${requestScope.prpdpaymentaccountDto.operatorCode}'/>">
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">操作人歸屬機構：</td>
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorComcode" value="<c:out value='${requestScope.prpdpaymentaccountDto.operatorComCode}'/>">
				</td>
				<td width="20%" class="left">操作人員姓名：</td>
				<td width="30%" class="right">
					<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorName" value="<c:out value='${requestScope.prpdpaymentaccountDto.operatorName}'/>">
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">第一次採集日期：</td>
				<td width="30%" class="right">
	         <rc:rcDate class="readonly" readonly="readonly" wdatePicker="false" name="prpdpaymentaccountOperateDate" value="${requestScope.prpdpaymentaccountDto.operateDate}"/>
				</td>
				<td width="20%" class="left">更新日期：</td>
				<td width="30%" class="right">
	         <rc:rcDate class="readonly" readonly="readonly" wdatePicker="false" name="prpdpaymentaccountUpdateDate" value="${requestScope.prpdpaymentaccountDto.updateDate}"/>
				</td>
			</tr>
			<tr style="display: none">
				<td width="20%" class="left">
					採集環節：<br>
				</td>
				<td width="30%" class="right">
					<input type="text" class="hidden" name="prpdpaymentaccountOperateSys" value="<c:out value='${requestScope.prpdpaymentaccountDto.operateSys}'/>">
					<br>
				</td>
				<td width="20%" class="left">
					是否已經用於實收實付：<br>
				</td>
				<td width="30%" class="right">
					<input type="text" class="hidden" name="prpdpaymentaccountUsedOrNot" value="<c:out value='${requestScope.prpdpaymentaccountDto.usedOrNot}'/>">
					<br>
				</td>
			</tr>
				<!--<td width="20%" class="left">賠付對象</td> -->
				<!-- 赔付对象： -->
				<!--<td width="30%" class="right">-->
				<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
				<input type="hidden" class="input" name="prpdpaymentaccountCompensateOwnerName" maxlength="100" value="<c:out value='${requestScope.prpdpaymentaccountDto.compensateOwnerName}'/>">
				<!--<img src="/claim/images/imgMustInput.gif"/>
	    </td>
	    <td width="20%" class="left">統一編號:</td>-->
				<!-- 统一编号 -->
				<!--<td width="30%" class="right"> -->
				<input type="hidden" class="input" name="prpdpaymentaccountUniformNo" maxlength="25" value="<c:out value='${requestScope.prpdpaymentaccountDto.uniformNo}'/>">
				<!--<img src="/claim/images/imgMustInput.gif"/>
	    </td>
	  </tr>-->
			<tr>
				<td width="20%" class="left">郵遞區號：</td>
				<!-- 邮政区号 -->
				<td width="30%" class="right">
					<input type="text" class='input' name="prpdpaymentaccountAreaCode" maxlength="20" value="<c:out value='${requestScope.prpdpaymentaccountDto.areaCode}'/>">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
				<td width="20%" class="left">郵政地址：</td>
				<!-- 邮政地址 -->
				<td width="30%" class="right">
					<input type="text" class='input' name="prpdpaymentaccountCourierAddress" maxlength="100" value="<c:out value='${requestScope.prpdpaymentaccountDto.courierAddress}'/>">
					<img src="/claim/images/imgMustInput.gif" />
				</td>
			</tr>
			<tr>
				<td width="20%" class="left">備註：</td>
				<td width="80%" class="right" colspan="3">
					<input type="text" class="input" name="prpdpaymentaccountRemark" maxlength="500" value="<c:out value='${requestScope.prpdpaymentaccountDto.remark}'/>">
					<input type="hidden" name="prpdpaymentaccountValidStatus" value="<c:out value='${requestScope.prpdpaymentaccountDto.validStatus}'/>">
				</td>
			</tr>
		</table>
		<br />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr align="center">
				<td>
					<input type="button" class="button" name="buttonSubmit" value="提 交" onClick="submitPaymentaccountCompen();">
					<c:if test="${requestScope.liWaiFlag=='1'}">
						&nbsp;&nbsp;
						<input type="button" class="button" name="buttonLiWai" value="例 外" onClick="LiWai();">
					</c:if>
				</td>
			</tr>
		</table>
		<div id="bankList" style="margin:0; padding:5px;border: #acacac 1px solid;background-color: FFFFFF;display: none; cursor: hand; position: absolute; width: 400px;overflow: auto" align="left"></div>
	</form>
</body>
</html>
