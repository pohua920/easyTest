<%--
****************************************************************************
* DESC       ： 计算费用
* AUTHOR     ：理赔组
* CREATEDATE ： 2005-04-05
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%
	String findex = request.getParameter("findex");
%>
<html>
<head>
<!--对title处理-->
<title><s:text name="title.compensateBeforeEdit.costCalculate" /></title>
<%-- 费用计算 --%>
<app:css />
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script src="/claim/DAA/compensate/js/DAAPersonLossEdit.js"></script>
<script language="javascript">function loadForm() {

	var recordCount = 0; //记录序号
	//序号
	recordCount = <%=findex%> ;
	LiabDetail.innerHTML = dialogArguments.fm.prpLpersonLossLiabDetailCode[recordCount].value + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + dialogArguments.fm.prpLpersonLossLiabDetailName[recordCount].value;

	fm.prpLpersonLossLiabDetailCode.value = dialogArguments.fm.prpLpersonLossLiabDetailCode[recordCount].value;
	fm.prpLpersonLossLiabDetailName.value = dialogArguments.fm.prpLpersonLossLiabDetailName[recordCount].value;
	//15误工费，14护理费，34伙食补助费，17残补，19死补，21抚养费，12续医费，18残疾辅助器具费
	fm.UnitAmount.value = dialogArguments.fm.prpLpersonLossUnitAmount[recordCount].value;
	fm.LossQuantity.value = dialogArguments.fm.prpLpersonLossLossQuantity[recordCount].value;
	fm.HospitalDays.value = dialogArguments.fm.prpLpersonLossHospitalDays[recordCount].value;
	if (fm.prpLpersonLossLiabDetailCode.value == 15) {
		UnitAmount.innerHTML = '单价(元／30天)';
		LossQuantity.innerHTML = '住院天数';
		HospitalDays.innerHTML = '休息天数';
	} else if (fm.prpLpersonLossLiabDetailCode.value == 14) {
		UnitAmount.innerHTML = '单价(元／30天)';
		LossQuantity.innerHTML = '住院天数';
		HospitalDays.innerHTML = '人数';
	} else if (fm.prpLpersonLossLiabDetailCode.value == 34) {
		UnitAmount.innerHTML = '单价(元／30天)';
		LossQuantity.innerHTML = '住院天数';
		fm.HospitalDays.readOnly = true;
		fm.HospitalDays.className = "readonly";
	} else if (fm.prpLpersonLossLiabDetailCode.value == 17) {
		UnitAmount.innerHTML = '单价(元／年)';
		LossQuantity.innerHTML = '年数';
		HospitalDays.innerHTML = '伤残比例';
	} else if (fm.prpLpersonLossLiabDetailCode.value == 19) {
		UnitAmount.innerHTML = '单价(元／年)';
		LossQuantity.innerHTML = '年数';
		fm.HospitalDays.readOnly = true;
		fm.HospitalDays.className = "readonly";
	} else if (fm.prpLpersonLossLiabDetailCode.value == 21) {
		UnitAmount.innerHTML = '单价(元／年)';
		LossQuantity.innerHTML = '年数';
		fm.HospitalDays.readOnly = true;
		fm.HospitalDays.className = "readonly";
	} else if (fm.prpLpersonLossLiabDetailCode.value == 12) {
		UnitAmount.innerHTML = '核定医药费金额';
		LossQuantity.innerHTML = '比例';
		fm.HospitalDays.readOnly = true;
		fm.HospitalDays.className = "readonly";
	} else if (fm.prpLpersonLossLiabDetailCode.value == 18) {
		UnitAmount.innerHTML = '单价(元／个)';
		LossQuantity.innerHTML = '次数';
		fm.HospitalDays.readOnly = true;
		fm.HospitalDays.className = "readonly";
	}
}


/**
 * 将模式窗口中的数据保存到主界面
 */

function saveModelForm() {
	var UnitAmount = 0;
	var LossQuantity = 0;
	var HospitalDays = 0;
	UnitAmount = parseFloat(fm.UnitAmount.value);
	LossQuantity = parseInt(fm.LossQuantity.value);
	HospitalDays = parseInt(fm.HospitalDays.value);

	if (isNaN(UnitAmount)) UnitAmount = 0;
	if (isNaN(LossQuantity)) LossQuantity = 0;
	if (isNaN(HospitalDays)) HospitalDays = 0;

	var SumLoss = 0;
	if (fm.prpLpersonLossLiabDetailCode.value == 15) {
		SumLoss = UnitAmount * (LossQuantity + HospitalDays);
	} else if (fm.prpLpersonLossLiabDetailCode.value == 14) {
		SumLoss = UnitAmount * LossQuantity * HospitalDays;
	} else if (fm.prpLpersonLossLiabDetailCode.value == 34) {
		SumLoss = UnitAmount * LossQuantity;
	} else if (fm.prpLpersonLossLiabDetailCode.value == 17) {
		SumLoss = UnitAmount * LossQuantity * HospitalDays;
	} else if (fm.prpLpersonLossLiabDetailCode.value == 19) {
		SumLoss = UnitAmount * LossQuantity;
	} else if (fm.prpLpersonLossLiabDetailCode.value == 21) {
		SumLoss = UnitAmount * LossQuantity;
	} else if (fm.prpLpersonLossLiabDetailCode.value == 12) {
		SumLoss = UnitAmount * LossQuantity;
	} else if (fm.prpLpersonLossLiabDetailCode.value == 18) {
		SumLoss = UnitAmount * LossQuantity;
	}
	//opener.document.fm.prpLpersonLossUnitAmount[<%=findex%>].value=point(round(UnitAmount,0),0);
	dialogArguments.fm.prpLpersonLossUnitAmount[ <%=findex%> ].value = point(round(UnitAmount, 0), 0);
	dialogArguments.fm.prpLpersonLossLossQuantity[ <%=findex%> ].value = LossQuantity;
	dialogArguments.fm.prpLpersonLossHospitalDays[ <%=findex%> ].value = HospitalDays;
    dialogArguments.fm.prpLpersonLossSumLoss[<%=findex %>].value=point(round(SumLoss,0),0);
    dialogArguments.fm.prpLpersonLossSumLoss[<%=findex %>].onchange();
    window.close();
	}
//-->
</script>
</head>
<body class="interface" onload="loadForm();">
	<form name=fm action="" method="post">
		<table class=common cellpadding="5" cellspacing="1">
			<tr class=listtitle>
				<td colspan=3>
					<s:text name="compensate.costCalculate" />
				</td>
			</tr>
			<%-- 费用计算 --%>
			<tr>
				<td class="title" style="width: 40%">
					<s:text name="compensate.lossDetail" />:
				</td>
				<%-- 损失明细 --%>
				<td class="input" style="width: 60%" colspan="2" id="LiabDetail"></td>
			</tr>
			<tr>
				<td class="title" style="width: 40%" id="UnitAmount"></td>
				<td class="title" style="width: 30%" id="LossQuantity"></td>
				<td class="title" style="width: 30%" id="HospitalDays"></td>
			</tr>
			<input type="hidden" name="prpLpersonLossLiabDetailCode" style="width: 90%" class="common">
			<input type="hidden" name="prpLpersonLossLiabDetailName" style="width: 90%" class="common">
			<tr>
				<td class="input">
					<input type="text" name="UnitAmount" style="width: 90%" class="common">
				</td>
				<td class="input">
					<input type="text" name="LossQuantity" style="width: 90%" class="common">
				</td>
				<td class="input">
					<input type="text" name="HospitalDays" style="width: 90%" class="common">
				</td>
			</tr>
		</table>
		<table cellpadding="10" cellspacing="0" width="100%">
			<tr>
				<td class=button style="width: 50%" align="center">
					<!--确定按钮-->
					<input type="button" name=buttonSave class='button' value="<s:text name='button.confirm.value' />" onclick="return saveModelForm();">
				</td>
				<td class=button style="width: 50%" align="center">
					<!--取消按钮-->
					<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="window.close();">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>