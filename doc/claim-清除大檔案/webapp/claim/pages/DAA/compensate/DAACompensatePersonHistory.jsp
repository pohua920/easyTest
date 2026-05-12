<%--
****************************************************************************
* DESC       : 理赔歷史賠付受害人訊息列表显示页面
* AUTHOR     : Sunhao
* CREATEDATE : 2004-07-28
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<title>歷史賠付受害人訊息</title>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard_green.css">
</head>
<body class="interface">
	<form action="">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr class=listtitle>
				<td width="5%" align="center" nowrap>
					<input type="checkbox" name="personCheckBoxAll" value="0" onclick="checkBoxAll();" />
				</td>
				<td width="20%" align="center" nowrap>人員姓名</td>
				<td width="10%" align="center" nowrap>身份證號</td>
				<td width="10%" align="center" nowrap>性別</td>
				<td width="20%">受害人身份</td>
				<td width="10%">出生年份</td>
				<td width="10%">賠付代號</td>
				<td width="10%">賠款總金額</td>
			</tr>
			<c:forEach items="${prpLpersonLossList}" var="prpLpersonLossTemp" varStatus="messageStatus">
				<c:if test="${messageStatus.count%2==0}">
					<tr class=listodd>
				</c:if>
				<c:if test="${messageStatus.count%2!=0}">
					<tr class=listeven>
				</c:if>
				<td align="center">
					<input type="checkbox" name="prpLpersonLossCheckBox" value="0" />
				</td>
				<td align="center">${prpLpersonLossTemp.personName }</td>
				<td align="center">${prpLpersonLossTemp.identifyNumber }</td>
				<td align="center">
					<c:if test="${prpLpersonLossTemp.sex=='1' }">男</c:if>
					<c:if test="${prpLpersonLossTemp.sex=='2' }">女</c:if>
				</td>
				<td align="center">${identityOfInjuredPersonList[prpLpersonLossTemp.identityOfInjuredPerson]}</td>
				<td align="center">
					<rc:rcDate name="birthday" class="readonly" readonly="true" wdatePicker="false" style="width:100%" value="${prpLpersonLossTemp.birthday}" />
				</td>
				<c:set var="compensateNoPersonNo" value="${prpLpersonLossTemp.id.compensateNo}${prpLpersonLossTemp.personNo}"/>
				<td align="center">${payCodeTypeMap[pageScope.compensateNoPersonNo]}</td>
				<td align="center">
					<fmt:formatNumber value="${prpLpersonLossTemp.sumRealPay}" pattern="#" />
						<span style="display: none;">
							<input type="input" name="sumRealPay" value="${prpLpersonLossTemp.sumRealPay}" class="readonly" readonly="readonly">
							<input type="input" name="personName" value="${prpLpersonLossTemp.personName }" class="readonly" readonly="readonly">
							<input type="input" name="identifyNumber" value="${prpLpersonLossTemp.identifyNumber }" class="readonly" readonly="readonly">
							<input type="hidden" name="sex" value="${prpLpersonLossTemp.sex }" class="readonly" readonly="readonly">
							<input type="hidden" name="identityOfInjuredPerson" value="${prpLpersonLossTemp.identityOfInjuredPerson }" class="readonly" readonly="readonly">
							<input type="input" name="mobilePhone" value="${prpLpersonLossTemp.mobilePhone }" class="readonly" readonly="readonly">
							<input type="input" name="telephoneNo" value="${prpLpersonLossTemp.telephoneNo }" class="readonly" readonly="readonly">
							<input type="input" name="kindCode" value="${prpLpersonLossTemp.kindCode }" class="readonly" readonly="readonly"><%--牌照號碼 --%>
							<input type="input" name="kindName" value="${prpLpersonLossTemp.kindName }" class="readonly" readonly="readonly"><%--牌照號碼 --%>
							<input type="input" name="itemKindNo" value="${prpLpersonLossTemp.itemKindNo }" class="readonly" readonly="readonly"><%--牌照號碼 --%>
							<input type="input" name="familyName" value="${prpLpersonLossTemp.familyName }" class="readonly" readonly="readonly"><%--牌照號碼 --%>
							<input type="input" name="age" value="${prpLpersonLossTemp.age }" class="readonly" readonly="readonly"><%--年齡 --%>
							<input type="input" name="rideSituation" value="${prpLpersonLossTemp.rideSituation }" class="readonly" readonly="readonly"><%--出事當時乘坐狀況 --%>
							<input type="input" name="medicalCode" value="${prpLpersonLossTemp.medicalCode }" class="readonly" readonly="readonly"><%--受害人健保就醫代號 --%>
							<input type="input" name="payObjectSerialNo" value="${prpLpersonLossTemp.payObjectSerialNo }" class="readonly" readonly="readonly"><%--賠付對象讯息 --%>
							<input type="input" name="endCaseAndRecoverFlag" value="${prpLpersonLossTemp.endCaseAndRecoverFlag }" class="readonly" readonly="readonly">
							<%--個別受害人醫療給付是否結案且待健保追償（返還）： --%>
							<input type="input" name="prosecutorsOffice" value="${prpLpersonLossTemp.prosecutorsOffice }" class="readonly" readonly="readonly"><%--地檢署 --%>
							<input type="input" name="courtDoctor" value="${prpLpersonLossTemp.courtDoctor }" class="readonly" readonly="readonly"><%--法醫師/檢驗員姓名 --%>
							<input type="input" name="prosecutor" value="${prpLpersonLossTemp.prosecutor}" class="readonly" readonly="readonly"><%--檢察官姓名--%>
							<input type="input" name="garageHeadName" value="${prpLpersonLossTemp.garageHeadName}" class="readonly" readonly="readonly"><%--修車廠負責人姓名 --%>
							<input type="input" name="hospitalCode" value="${prpLpersonLossTemp.hospitalCode}" class="readonly" readonly="readonly"><%--醫院代碼 --%>
							<input type="input" name="hospitalName" value="${prpLpersonLossTemp.hospitalName}" class="readonly" readonly="readonly"><%--醫院名稱--%>
							<input type="input" name="doctor" value="${prpLpersonLossTemp.doctor}" class="readonly" readonly="readonly"><%--醫師姓名 --%>
							<input type="input" name="casualties" value="${prpLpersonLossTemp.casualties}" class="readonly" readonly="readonly"><%--傷亡情形 --%>
							<input type="input" name="indemnityDutyRate" value="${prpLpersonLossTemp.indemnityDutyRate}" class="readonly" readonly="readonly"><%--肇事責任比率 --%>
					</span>
				</td>
				</tr>
			</c:forEach>
			<tfoot>
				<tr>
					<td colspan="8" align="center">
						<input type="button" class="button" value="添加" onclick="insertPersonLoss();">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="關閉" onclick="closeWindow();">
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
</body>
<script type="text/javascript">
function insertPersonLoss() {
	var selectIndex = false;
	var prpLpersonLoss;
	var prpLpersonLossCheckBox = document.getElementsByName("prpLpersonLossCheckBox");
	var personName = document.getElementsByName("personName");
	var identifyNumber = document.getElementsByName("identifyNumber");
	var sex = document.getElementsByName("sex");
	var identityOfInjuredPerson = document.getElementsByName("identityOfInjuredPerson");
	var birthday = document.getElementsByName("birthday");
	var birthday_show_format_rcDate = document.getElementsByName("birthday_show_format_rcDate"); //明国年时间
	var telephoneNo = document.getElementsByName("telephoneNo");
	var mobilePhone = document.getElementsByName("mobilePhone");
	var kindCode = document.getElementsByName("kindCode");
	var kindName = document.getElementsByName("kindName");
	var itemKindNo = document.getElementsByName("itemKindNo");
	var familyName = document.getElementsByName("familyName");
	var age = document.getElementsByName("age");
	var rideSituation = document.getElementsByName("rideSituation");
	var medicalCode = document.getElementsByName("medicalCode");
	var payObjectSerialNo = document.getElementsByName("payObjectSerialNo");
	var endCaseAndRecoverFlag = document.getElementsByName("endCaseAndRecoverFlag");
	var prosecutorsOffice = document.getElementsByName("prosecutorsOffice");
	var courtDoctor = document.getElementsByName("courtDoctor");
	var prosecutor = document.getElementsByName("prosecutor");
	var garageHeadName = document.getElementsByName("garageHeadName");
	var hospitalCode = document.getElementsByName("hospitalCode");
	var hospitalName = document.getElementsByName("hospitalName");
	var doctor = document.getElementsByName("doctor");
	var casualties = document.getElementsByName("casualties");
	var indemnityDutyRate = document.getElementsByName("indemnityDutyRate");
	var message = "";
	var flag = true;
	for (var i = 0; i < prpLpersonLossCheckBox.length; i++) {
		if (prpLpersonLossCheckBox[i].checked) {
			selectIndex = true;
			prpLpersonLoss = new Object();
			prpLpersonLoss.personName = personName[i].value;
			prpLpersonLoss.identifyNumber = identifyNumber[i].value;
			prpLpersonLoss.sex = sex[i].value;
			prpLpersonLoss.identityOfInjuredPerson = identityOfInjuredPerson[i].value;
			prpLpersonLoss.birthday = birthday[i].value;
			prpLpersonLoss.birthday_show_format_rcDate = birthday_show_format_rcDate[i].value;
			prpLpersonLoss.telephoneNo = telephoneNo[i].value;
			prpLpersonLoss.mobilePhone = mobilePhone[i].value;
			prpLpersonLoss.kindCode = kindCode[i].value;
			prpLpersonLoss.kindName = kindName[i].value;
			prpLpersonLoss.itemKindNo = itemKindNo[i].value;
			prpLpersonLoss.familyName = familyName[i].value;
			prpLpersonLoss.age = age[i].value;
			prpLpersonLoss.rideSituation = rideSituation[i].value;
			prpLpersonLoss.medicalCode = medicalCode[i].value;
			prpLpersonLoss.payObjectSerialNo = payObjectSerialNo[i].value;
			prpLpersonLoss.endCaseAndRecoverFlag = endCaseAndRecoverFlag[i].value;
			prpLpersonLoss.prosecutorsOffice = prosecutorsOffice[i].value;
			prpLpersonLoss.courtDoctor = courtDoctor[i].value;
			prpLpersonLoss.prosecutor = prosecutor[i].value;
			prpLpersonLoss.garageHeadName = garageHeadName[i].value;
			prpLpersonLoss.hospitalCode = hospitalCode[i].value;
			prpLpersonLoss.hospitalName = hospitalName[i].value;
			prpLpersonLoss.doctor = doctor[i].value;
			prpLpersonLoss.casualties = casualties[i].value;
			prpLpersonLoss.indemnityDutyRate = indemnityDutyRate[i].value;
			flag = window.opener.addPersonHistory(prpLpersonLoss);
			if (!flag) {
				message += "第" + (i + 1) + "條人傷訊息添加失敗!人傷訊息中不容許重複!\n";
			}
		}
	}
	if (selectIndex) {
		if (message == "") {
			closeWindow();
		} else {
			alert(message);
		}
	} else {
		alert("沒有選擇任何人員，請選擇!");
	}
}

function closeWindow() {
	window.close();
}

function checkBoxAll() {
	var checkBoxAll = document.getElementsByName("personCheckBoxAll")[0];
	var prpLpersonLossCheckBox = document.getElementsByName("prpLpersonLossCheckBox");
	for (var i = 0; i < prpLpersonLossCheckBox.length; i++) {
		prpLpersonLossCheckBox[i].checked = checkBoxAll.checked;
	}
}
</script>
</html>