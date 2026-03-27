<%@ page contentType="text/html; charset=GBK" %>
<html>
<%@ include file="/common/taglibs.jsp"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<%@ include file="/common/meta_js.jsp"%>
<head>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="${ctx}/pages/dwr/engine.js"></script>
<script src="${ctx}/pages/dwr/util.js"></script>
<script src="${ctx}/pages/dwr/interface/uiAccountCodeAction.js"></script>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<script type="text/javascript">
 function submitFormComsate(accountCode, accountCurrency, accountType, bankCode, bankName, accountName, customerCode, ownerType, ownerName, certificateType, certificateCode, ownerPhoneNo, accountCurrency, index1) {
		try {
			var bankList = document.getElementsByName("bankName");
			if (bankList.length > index1) {
				window.opener.fm.prpLCompensateBankName.value = bankList[index1].value;
			}
			window.opener.fm.prpLCompensateAccountCode.value = accountCode;
			window.opener.fm.prpLCompensateAccountCurrency.value = accountCurrency;
			window.opener.fm.prpLCompensateAccountType.value = accountType;
			if (accountType == '1') {
				window.opener.fm.prpLCompensateAccountTypeShow.value = '存折';
			} else if (accountType == '2') {
				window.opener.fm.prpLCompensateAccountTypeShow.value = '信用卡';
			} else if (accountType == '3') {
				window.opener.fm.prpLCompensateAccountTypeShow.value = '储值卡';
			} else if (accountType == '4') {
				window.opener.fm.prpLCompensateAccountTypeShow.value = '其他';
			}
			window.opener.fm.prpLCompensateBankCode.value = bankCode;
			window.opener.fm.prpLCompensateCustomBankName.value = bankName;
			window.opener.fm.prpLCompensateOwnerName.value = ownerName;
			window.opener.fm.prpLCompensateCertificateCode.value = certificateCode;
			window.opener.fm.prpLCompensateOwnerPhoneNo.value = ownerPhoneNo;
		} catch (e) {}
		window.close();
		return;
	}
 </script>
<title><s:text name="title.account.accountInformationList" /></title>
<!-- 帳户信息列表 -->
</head>
<body style="overflow: scroll">
	<form name="fm" action="${ctx}/AccountCode.do" method="post" autocomplete="off">
		<input type="hidden" name="actionType" value="<c:out value='${param.actionType}'/>">
		<input type="hidden" name="businessType" value="<c:out value='${param.businessType}'/>">
		<input type="hidden" name="registNo" value="<c:out value='${requestScope.registNo}'/>">
		<input type="hidden" name="serialNo" value="<c:out value='${requestScope.serialNo}'/>">
		<input type="hidden" name="comCodeForAcc" value="<c:out value='${sessionScope.user.comCode}'/>">
		<input type="hidden" name="userName" value="<c:out value='${sessionScope.user.userName}'/>">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr align="center">
				<td>
					<input type="button" class="bigbutton" name="button" value="<s:text name="button.account.addAccountInformation"/>"
						onClick="javascript:paymentaccountAddCompensate('<c:out value='${requestScope.registNo}'/>','<c:out value='${requestScope.ownerName}'/>');">
				</td>
				<!-- 新增帳户信息 -->
			</tr>
			<tr>
				<td bgcolor="black"></td>
			</tr>
		</table>
		<c:forEach items="${requestScope.PaymentAccounList}" var="prpdpaymentaccountDto" varStatus="stat">
			<table border="0" cellpadding="5" cellspacing="1" class="common"
				onClick="submitFormComsate('<c:out value="${prpdpaymentaccountDto.accountCode}" />','<c:out value="${prpdpaymentaccountDto.accountCurrency}" />','<c:out value="${prpdpaymentaccountDto.accountType}" />','<c:out value="${prpdpaymentaccountDto.bankCode}" />','<c:out value="${prpdpaymentaccountDto.bankName}" />','<c:out value="${prpdpaymentaccountDto.accountName}" />','<c:out value="${prpdpaymentaccountDto.customerCode}" />','<c:out value="${prpdpaymentaccountDto.ownerType}" />','<c:out value="${prpdpaymentaccountDto.ownerName}" />','<c:out value="${prpdpaymentaccountDto.certificateType}" />','<c:out value="${prpdpaymentaccountDto.certificateCode}" />','<c:out value="${prpdpaymentaccountDto.ownerPhoneNo}" />','<c:out value="${prpdpaymentaccountDto.accountCurrency}" />','<c:out value="${stat.index}"/>')";>
				<tr>
					<td colspan="4" align="center" class="top">
						<strong><s:text name="account.accountInformation" />
							<!-- 帳户信息 --> <c:out value='${stat.index+1}' /> <s:text name="account.accountName" />
							<!--帳户名称：--> <c:out value="${prpdpaymentaccountDto.accountName}" /></strong>
					</td>
				</tr>
				<tr>
					<td width="20%" class="page">
						<s:text name="db.prpLcompensate.account" />
						:
					</td>
					<!-- 银行帐号： -->
					<td width="30%" class="page">
						<input type="text" class="common" name="prpdpaymentaccountAccountCode" value="<c:out value="${prpdpaymentaccountDto.accountCode}" />">
						<img src="/claim/images/imgMustInput.gif" />
						<input type="text" class="common" name="prpdpaymentaccountAccountCurrency" value="<c:out value="${prpdpaymentaccountDto.accountCurrency}" />">
					</td>
					<td width="20%" class="page">
						<s:text name="compensate.accountCurrencyType" />
						:
					</td>
					<!-- 帳户类型： -->
					<td width="30%" class="page">
						<select name="prpdpaymentaccountAccountType" class="common">
							<option value="1" <c:if test="${prpdpaymentaccountDto.accountType=='1'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.passbook" />
							</option>
							<!-- 存折 -->
							<option value="2" <c:if test="${prpdpaymentaccountDto.accountType=='2'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.creditCard" />
							</option>
							<!-- 信用卡 -->
							<option value="3" <c:if test="${prpdpaymentaccountDto.accountType=='3'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.CARDS" />
							</option>
							<!-- 储值卡 -->
							<option value="4" <c:if test="${prpdpaymentaccountDto.accountType=='4'}"><c:out value="selected"/></c:if>>
								<s:text name="regist.prpLregist.other" />
							</option>
							<!-- 其他 -->
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" class="page">
						<s:text name="account.headquartersCode" />
						<!-- 总行代码： -->
					</td>
					<td width="30%" class="page">
						<select name="prpdpaymentaccountBankCode" class="common">
							<option value="102" <c:if test="${prpdpaymentaccountDto.bankCode=='102'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName1" />
							</option>
							<!-- 中国工商银行 -->
							<option value="103" <c:if test="${prpdpaymentaccountDto.bankCode=='103'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName2" />
							</option>
							<!-- 中国农业银行 -->
							<option value="104" <c:if test="${prpdpaymentaccountDto.bankCode=='104'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName3" />
							</option>
							<!-- 中国银行 -->
							<option value="105" <c:if test="${prpdpaymentaccountDto.bankCode=='105'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName4" />
							</option>
							<!-- 中国建设银行 -->
							<option value="106" <c:if test="${prpdpaymentaccountDto.bankCode=='106'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName5" />
							</option>
							<!-- 民生银行 -->
							<option value="107" <c:if test="${prpdpaymentaccountDto.bankCode=='107'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName6" />
							</option>
							<!-- 农村信用社 -->
							<option value="108" <c:if test="${prpdpaymentaccountDto.bankCode=='108'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName7" />
							</option>
							<!-- 兴业银行 -->
							<option value="109" <c:if test="${prpdpaymentaccountDto.bankCode=='109'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName8" />
							</option>
							<!-- 中信实业银行 -->
							<option value="110" <c:if test="${prpdpaymentaccountDto.bankCode=='110'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName9" />
							</option>
							<!-- 国家开发银行 -->
							<option value="111" <c:if test="${prpdpaymentaccountDto.bankCode=='111'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName10" />
							</option>
							<!-- 国家进出口银行 -->
							<option value="112" <c:if test="${prpdpaymentaccountDto.bankCode=='112'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName11" />
							</option>
							<!-- 农业发展银行 -->
							<option value="113" <c:if test="${prpdpaymentaccountDto.bankCode=='113'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName12" />
							</option>
							<!-- 恒丰银行 -->
							<option value="114" <c:if test="${prpdpaymentaccountDto.bankCode=='114'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName13" />
							</option>
							<!--住房公积金管理中心  -->
							<option value="1200" <c:if test="${prpdpaymentaccountDto.bankCode=='1200'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName14" />
							</option>
							<!--邮政储汇  -->
							<option value="1701" <c:if test="${prpdpaymentaccountDto.bankCode=='1701'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName15" />
							</option>
							<!--香港上海汇丰银行  -->
							<option value="1702" <c:if test="${prpdpaymentaccountDto.bankCode=='1702'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName16" />
							</option>
							<!-- 东亚银行 -->
							<option value="1703" <c:if test="${prpdpaymentaccountDto.bankCode=='1703'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName17" />
							</option>
							<!--标准渣打银行  -->
							<option value="1704" <c:if test="${prpdpaymentaccountDto.bankCode=='1704'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName18" />
							</option>
							<!-- 荷兰商业银行 -->
							<option value="1705" <c:if test="${prpdpaymentaccountDto.bankCode=='1705'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName19" />
							</option>
							<!-- 恒生银行 -->
							<option value="1706" <c:if test="${prpdpaymentaccountDto.bankCode=='1706'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName20" />
							</option>
							<!-- 大华银行 -->
							<option value="1707" <c:if test="${prpdpaymentaccountDto.bankCode=='1707'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName21" />
							</option>
							<!--法国里昂信贷银行  -->
							<option value="1708" <c:if test="${prpdpaymentaccountDto.bankCode=='1708'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName22" />
							</option>
							<!-- 法国巴黎银行 -->
							<option value="1709" <c:if test="${prpdpaymentaccountDto.bankCode=='1709'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName23" />
							</option>
							<!-- 美国花旗银行 -->
							<option value="1710" <c:if test="${prpdpaymentaccountDto.bankCode=='1710'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName24" />
							</option>
							<!-- 美国摩根大通银行 -->
							<option value="1711" <c:if test="${prpdpaymentaccountDto.bankCode=='1711'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName25" />
							</option>
							<!--美国银行  -->
							<option value="1712" <c:if test="${prpdpaymentaccountDto.bankCode=='1712'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName26" />
							</option>
							<!-- 美国运通银行 -->
							<option value="1713" <c:if test="${prpdpaymentaccountDto.bankCode=='1713'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName27" />
							</option>
							<!-- 德国商业银行 -->
							<option value="1714" <c:if test="${prpdpaymentaccountDto.bankCode=='1714'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName28" />
							</option>
							<!-- 德意志银行 -->
							<option value="1715" <c:if test="${prpdpaymentaccountDto.bankCode=='1715'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName29" />
							</option>
							<!-- 日本三井住友银行 -->
							<option value="1716" <c:if test="${prpdpaymentaccountDto.bankCode=='1716'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName30" />
							</option>
							<!-- 日本东京三菱银行 -->
							<option value="1717" <c:if test="${prpdpaymentaccountDto.bankCode=='1717'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName31" />
							</option>
							<!--日本横滨银行  -->
							<option value="1718" <c:if test="${prpdpaymentaccountDto.bankCode=='1718'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName32" />
							</option>
							<!-- 日本日联银行 -->
							<option value="1719" <c:if test="${prpdpaymentaccountDto.bankCode=='1719'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName33" />
							</option>
							<!-- 瑞士信贷第一波士顿银行 -->
							<option value="1720" <c:if test="${prpdpaymentaccountDto.bankCode=='1720'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName34" />
							</option>
							<!--瑞士信贷银行  -->
							<option value="1721" <c:if test="${prpdpaymentaccountDto.bankCode=='1721'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName35" />
							</option>
							<!-- 瑞士银行 -->
							<option value="1722" <c:if test="${prpdpaymentaccountDto.bankCode=='1722'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName36" />
							</option>
							<!-- 古巴国民银行 -->
							<option value="1723" <c:if test="${prpdpaymentaccountDto.bankCode=='1723'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName37" />
							</option>
							<!-- 韩国产业银行 -->
							<option value="1724" <c:if test="${prpdpaymentaccountDto.bankCode=='1724'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName38" />
							</option>
							<!--韩亚银行  -->
							<option value="1725" <c:if test="${prpdpaymentaccountDto.bankCode=='1725'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName39" />
							</option>
							<!-- 加拿大皇家银行 -->
							<option value="1726" <c:if test="${prpdpaymentaccountDto.bankCode=='1726'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName40" />
							</option>
							<!-- 马来西亚马来亚银行 -->
							<option value="1727" <c:if test="${prpdpaymentaccountDto.bankCode=='1727'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName41" />
							</option>
							<!-- 泰国盘谷银行 -->
							<option value="301" <c:if test="${prpdpaymentaccountDto.bankCode=='301'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName42" />
							</option>
							<!-- 交通银行 -->
							<option value="302" <c:if test="${prpdpaymentaccountDto.bankCode=='302'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName43" />
							</option>
							<!-- 中信实业银行 -->
							<option value="303" <c:if test="${prpdpaymentaccountDto.bankCode=='303'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName44" />
							</option>
							<!-- 中国光大银行 -->
							<option value="304" <c:if test="${prpdpaymentaccountDto.bankCode=='304'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName45" />
							</option>
							<!-- 华夏银行 -->
							<option value="305" <c:if test="${prpdpaymentaccountDto.bankCode=='305'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName46" />
							</option>
							<!-- 中国民生银行 -->
							<option value="307" <c:if test="${prpdpaymentaccountDto.bankCode=='307'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName47" />
							</option>
							<!-- 深圳发展银行 -->
							<option value="308" <c:if test="${prpdpaymentaccountDto.bankCode=='308'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName48" />
							</option>
							<!-- 招商银行 -->
							<option value="309" <c:if test="${prpdpaymentaccountDto.bankCode=='309'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName49" />
							</option>
							<!--福建兴业银行  -->
							<option value="310" <c:if test="${prpdpaymentaccountDto.bankCode=='310'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName50" />
							</option>
							<!-- 上海浦东发展银行 -->
							<option value="313" <c:if test="${prpdpaymentaccountDto.bankCode=='313'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName51" />
							</option>
							<!-- 城市商业银行 -->
							<option value="314" <c:if test="${prpdpaymentaccountDto.bankCode=='314'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName52" />
							</option>
							<!-- 厦门银行 -->
							<option value="401" <c:if test="${prpdpaymentaccountDto.bankCode=='401'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName53" />
							</option>
							<!-- 城市信用合作社 -->
							<option value="402" <c:if test="${prpdpaymentaccountDto.bankCode=='402'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName54" />
							</option>
							<!-- 农村信用社（含北京农村商业银行） -->
							<option value="403" <c:if test="${prpdpaymentaccountDto.bankCode=='403'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName55" />
							</option>
							<!-- 中国邮政储蓄银行 -->
							<option value="501" <c:if test="${prpdpaymentaccountDto.bankCode=='501'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName56" />
							</option>
							<!-- 广东发展银行 -->
							<option value="783" <c:if test="${prpdpaymentaccountDto.bankCode=='783'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName57" />
							</option>
							<!-- 平安银行 -->
							<option value="781" <c:if test="${prpdpaymentaccountDto.bankCode=='781'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName58" />
							</option>
							<!-- 厦门国际银行 -->
							<option value="701" <c:if test="${prpdpaymentaccountDto.bankCode=='701'}"><c:out value="selected"/></c:if>>
								<s:text name="compensate.bankName59" />
							</option>
							<!-- 上海农村商业银 -->
						</select>
						<c:if test="${prpdpaymentaccountDto.bankCode=='102'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName1"/>'>
						</c:if>
						<!-- 中国工商银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='103'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName2"/>'>
						</c:if>
						<!-- 中国农业银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='104'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName3"/>'>
						</c:if>
						<!-- 中国银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='105'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName4"/>'>
						</c:if>
						<!-- 中国建设银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='106'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName5"/>'>
						</c:if>
						<!-- 民生银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='107'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName6"/>'>
						</c:if>
						<!-- 农村信用社 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='108'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName7"/>'>
						</c:if>
						<!-- 兴业银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='109'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName8"/>'>
						</c:if>
						<!-- 中信实业银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='110'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName9"/>'>
						</c:if>
						<!-- 国家开发银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='111'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName10"/>'>
						</c:if>
						<!-- 国家进出口银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='112'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName11"/>'>
						</c:if>
						<!-- 农业发展银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='113'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName12"/>'>
						</c:if>
						<!-- 恒丰银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='114'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName13"/>'>
						</c:if>
						<!--住房公积金管理中心  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1200'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName14"/>'>
						</c:if>
						<!--邮政储汇  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1701'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName15"/>'>
						</c:if>
						<!--香港上海汇丰银行  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1702'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName16"/>'>
						</c:if>
						<!-- 东亚银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1703'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName17"/>'>
						</c:if>
						<!--标准渣打银行  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1704'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName18"/>'>
						</c:if>
						<!-- 荷兰商业银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1705'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName19"/>'>
						</c:if>
						<!-- 恒生银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1706'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName20"/>'>
						</c:if>
						<!-- 大华银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1707'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName21"/>'>
						</c:if>
						<!--法国里昂信贷银行  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1708'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName22"/>'>
						</c:if>
						<!-- 法国巴黎银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1709'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName23"/>'>
						</c:if>
						<!-- 美国花旗银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1710'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName24"/>'>
						</c:if>
						<!-- 美国摩根大通银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1711'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName25"/>'>
						</c:if>
						<!--美国银行  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1712'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName26"/>'>
						</c:if>
						<!-- 美国运通银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1713'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName27"/>'>
						</c:if>
						<!-- 德国商业银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1714'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName28"/>'>
						</c:if>
						<!-- 德意志银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1715'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName29"/>'>
						</c:if>
						<!-- 日本三井住友银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1716'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName30"/>'>
						</c:if>
						<!-- 日本东京三菱银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1717'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName31"/>'>
						</c:if>
						<!--日本横滨银行  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1718'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName32"/>'>
						</c:if>
						<!-- 日本日联银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1719'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName33"/>'>
						</c:if>
						<!-- 瑞士信贷第一波士顿银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1720'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName34"/>'>
						</c:if>
						<!--瑞士信贷银行  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1721'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName35"/>'>
						</c:if>
						<!-- 瑞士银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1722'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName36"/>'>
						</c:if>
						<!-- 古巴国民银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1723'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName37"/>'>
						</c:if>
						<!-- 韩国产业银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1724'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName38"/>'>
						</c:if>
						<!--韩亚银行  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1725'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName39"/>'>
						</c:if>
						<!-- 加拿大皇家银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1726'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName40"/>'>
						</c:if>
						<!-- 马来西亚马来亚银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='1727'}">
							<input type='hidden' name='bankName' value='<s:text name="compensate.bankName41"/>'>
						</c:if>
						<!-- 泰国盘谷银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='301'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName42"/>'>
						</c:if>
						<!-- 交通银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='302'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName43"/>'>
						</c:if>
						<!-- 中信实业银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='303'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName44"/>'>
						</c:if>
						<!-- 中国光大银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='304'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName45"/>'>
						</c:if>
						<!-- 华夏银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='305'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName46"/>'>
						</c:if>
						<!-- 中国民生银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='307'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName47"/>'>
						</c:if>
						<!-- 深圳发展银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='308'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName48"/>'>
						</c:if>
						<!-- 招商银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='309'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName49"/>'>
						</c:if>
						<!--福建兴业银行  -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='310'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName50"/>'>
						</c:if>
						<!-- 上海浦东发展银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='313'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName51"/>'>
						</c:if>
						<!-- 城市商业银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='314'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName52"/>'>
						</c:if>
						<!-- 厦门银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='401'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName53"/>'>
						</c:if>
						<!-- 城市信用合作社 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='402'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName54"/>'>
						</c:if>
						<!-- 农村信用社（含北京农村商业银行） -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='403'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName55"/>'>
						</c:if>
						<!-- 中国邮政储蓄银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='501'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName56"/>'>
						</c:if>
						<!-- 广东发展银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='783'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName57"/>'>
						</c:if>
						<!-- 平安银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='781'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName58"/>'>
						</c:if>
						<!-- 厦门国际银行 -->
						<c:if test="${prpdpaymentaccountDto.bankCode=='701'}">
							<input type='hidden' name='bankName' value=' <s:text name="compensate.bankName59"/>'>
						</c:if>
						<!-- 上海农村商业银 -->
					</td>
					<td width="20%" class="page">
						<s:text name="db.prpDcustomer_Unit.bank" />
						:
					</td>
					<!-- 开户银行： -->
					<td width="30%" class="page">
						<input type="text" class="common" id="prpdpaymentaccountBankName" name="prpdpaymentaccountBankName" maxlength="50" onblur="return limitLength(value,50,'开户银行','prpdpaymentaccountBankName');"
							value="<c:out value="${prpdpaymentaccountDto.bankName}" />">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="page">
						<s:text name="account.accountName" />
					</td>
					<!-- 帳户名称： -->
					<td width="30%" class="page">
						<input type="text" class="common" name="prpdpaymentaccountAccountName" value="<c:out value="${prpdpaymentaccountDto.accountName}" />">
					</td>
					<td width="20%" class="page">
						<s:text name="db.prpDcustomer_Unit.customerCode" />
						:
					</td>
					<!-- 客户代码： -->
					<td width="30%" class="page">
						<input type="text" class="common" name="prpdpaymentaccountCustomerCode" value="<c:out value="${prpdpaymentaccountDto.customerCode}" />">
					</td>
				</tr>
				<tr>
					<td width="20%" class="page" style='display: none'>
						<s:text name="db.prpUserGrade.userCode" />
						:
					</td>
					<!-- 员工代码： -->
					<td width="30%" class="page" style='display: none'>
						<input type="text" class="common" name="prpdpaymentaccountUserCode" value="<c:out value="${prpdpaymentaccountDto.userCode}" />">
					</td>
					<td width="20%" class="page" style='display: none'>
						<s:text name="account.maintenanceUnitCode" />
					</td>
					<!-- 维修单位代码： -->
					<td width="30%" class="page" style='display: none'>
						<input type="text" class="common" name="prpdpaymentaccountVehicleComCode" value="<c:out value="${prpdpaymentaccountDto.vehicleComCode}" />">
					</td>
				</tr>
				<tr>
					<td width="20%" class="page">
						<s:text name="account.accountOwnershipAttribute" />
					</td>
					<!-- 帳户归属人属性 ： -->
					<td width="30%" class="page">
						<select name="prpdpaymentaccountOwnerType" class="common">
							<option value="1" <c:if test="${prpdpaymentaccountDto.ownerType=='1'}"><c:out value="selected"/></c:if>>
								<s:text name="account.personal" />
							</option>
							<!-- 个人 -->
							<option value="2" <c:if test="${prpdpaymentaccountDto.ownerType=='2'}"><c:out value="selected"/></c:if>>
								<s:text name="account.enterprise" />
							</option>
							<!-- 企业 -->
						</select>
					</td>
					<td width="20%" class="page">
						<s:text name="account.accountOwnershipPersonName" />
					</td>
					<!-- 帳户归属人姓名(支付对象帳户名称)： -->
					<td width="30%" class="page">
						<input type="text" class="common" name="prpdpaymentaccountOwnerName" value="<c:out value="${prpdpaymentaccountDto.ownerName}" />">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="page">
						<s:text name="account.accountOwnershipCertificateType" />
					</td>
					<!-- 帳号归属人证件类型： -->
					<td width="30%" class="page">
						<s:select name="prpdpaymentaccountCertificateType" class="common" value="#attr.prpdpaymentaccountDto.certificateType" listKey="key" listValue="value"
							list="#request.prpdpaymentaccountCertificateTypeList" />
					</td>
					<td width="20%" class="page">
						<s:text name="account.accountOwnershipPersonCode" />
					</td>
					<!-- 帳号归属人证件代码： -->
					<td width="30%" class="page">
						<input type="text" class="common" name="prpdpaymentaccountCertificateCode" value="<c:out value="${prpdpaymentaccountDto.certificateCode}" />">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="page">
						<s:text name="account.accountOwnershipPhoneNumber" />
					</td>
					<!-- 帳户归属人联系电话： -->
					<td width="30%" class="page">
						<input type="text" class="common" name="prpdpaymentaccountOwnerPhoneNo" value="<c:out value="${prpdpaymentaccountDto.ownerPhoneNo}" />">
						<img src="/claim/images/imgMustInput.gif" />
					</td>
					<td width="20%" class="page">
						<s:text name="account.operatorCode" />
					</td>
					<!-- 操作人员代码： -->
					<td width="30%" class="page">
						<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorCode" value="<c:out value="${prpdpaymentaccountDto.operatorCode}" />">
					</td>
				</tr>
				<tr>
					<td width="20%" class="page">
						<s:text name="account.operationsPeople" />
					</td>
					<!-- 操作人归属机构： -->
					<td width="30%" class="page">
						<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorComcode" value="<c:out value="${prpdpaymentaccountDto.operatorComCode}" />">
					</td>
					<td width="20%" class="page">
						<s:text name="account.operationsPeopleName" />
					</td>
					<!-- 操作人员姓名： -->
					<td width="30%" class="page">
						<input type="text" class="readonly" readonly name="prpdpaymentaccountOperatorName" value="<c:out value="${prpdpaymentaccountDto.operatorName}" />">
					</td>
				</tr>
				<tr>
					<td width="20%" class="page">
						<s:text name="account.firstCollectionDate" />
					</td>
					<!-- 第一次采集日期： -->
					<td width="30%" class="page">
						<input type="text" class="readonly" readonly name="prpdpaymentaccountOperateDate" value="<c:out value="${prpdpaymentaccountDto.operateDate}" />">
					</td>
					<td width="20%" class="page">
						<s:text name="account.updateDate" />
					</td>
					<!-- 更新日期： -->
					<td width="30%" class="page">
						<input type="text" class="readonly" readonly name="prpdpaymentaccountUpdateDate" value="<c:out value="${prpdpaymentaccountDto.updateDate}" />">
					</td>
				</tr>
				<tr>
					<td width="20%" class="page" style='display: none'>
						<s:text name="account.theProcedure" />
						<br>
						<br>
					</td>
					<!-- 采集环节： -->
					<td width="30%" class="page" style='display: none'>
						<input type="text" class="hidden" name="prpdpaymentaccountOperateSys" value="<c:out value="${prpdpaymentaccountDto.operateSys}" />">
						<br>
						<br>
					</td>
					<td width="20%" class="page" style='display: none'>
						<s:text name="account.whetherUsedPaid" />
						<br>
						<br>
					</td>
					<!-- 是否已经用於实收实付： -->
					<td width="30%" class="page" style='display: none'>
						<input type="text" class="hidden" name="prpdpaymentaccountUsedOrNot" value="<c:out value="${prpdpaymentaccountDto.usedOrNot}" />">
						<br>
						<br>
					</td>
				</tr>
				<tr>
					<td width="20%" class="page">
						<s:text name="db.prpDcompany.remark" />
						:
					</td>
					<!-- 备注： -->
					<td width="80%" class="page" colspan="3">
						<input type="text" class="common" name="prpdpaymentaccountRemark" value="<c:out value="${prpdpaymentaccountDto.remark}" />">
						<input type="hidden" name="prpdpaymentaccountValidStatus" value="<c:out value="${prpdpaymentaccountDto.validStatus}" />">
					</td>
				</tr>
				<tr>
					<td bgcolor="black" colspan="4"></td>
				</tr>
			</table>
			<br>
		</c:forEach>
	</form>
</body>
</html>
