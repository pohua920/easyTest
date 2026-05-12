<%--
****************************************************************************
* DESC       ：实赔录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-03-20
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
<head>
<title><s:text name="title.compensateBeforeEdit.adjustEntryPage" /> <%-- 理算录入子页面 --%></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language='javascript'>
	function initnm() {
		var a = window.dialogArguments;
		if (a[0] == '0') {
			fm1.ConsumeName.style.display = "none";
			fm1.prpLlossDtoConsume.style.display = "none";
			fm1.isLossAll.value = '是否全损';
			fm1.DaysName.value = a[1];
		} else {
			fm1.isLossAll.style.display = "none";
			fm1.prpLisLossAll.style.display = "none";
			fm1.ConsumeName.value = a[0];
			fm1.DaysName.value = a[1];
		}
	}

	function fuzhi() {
		var a = window.dialogArguments;
		if (a[0] == '0') {
			var a = new Array(parseFloat(fm1.prpLisLossAll.value),
					parseInt(fm1.prpLlossDtoDays.value));
			window.returnValue = a;
			window.close();
		} else {
			var a = new Array(parseFloat(fm1.prpLlossDtoConsume.value),
					parseInt(fm1.prpLlossDtoDays.value));
			window.returnValue = a;
			window.close();
		}
	}
</script>
</head>
<body onload="initnm();">
	<form name="fm1">
		<table class=common cellpadding="5" cellspacing="1">
			<tr>
				<td style="width: 50%">
					<input type=text name="ConsumeName" class='readonly' align="center" style="display:">
				</td>
				<td class="inputsubsub" style="width: 50%" align="right">
					<input name="prpLlossDtoConsume" class="common" style="display:" value='0.0'>
				</td>
			</tr>
			<tr>
				<td>
					<input type=text name="isLossAll" class='readonly' align="center" style="display:">
				</td>
				<td>
					<select name="prpLisLossAll" class='common' style="display:">
						<option value="1">
							<s:text name="certainLoss.thirdCarLoss.yes" />
						</option>
						<%-- 是 --%>
						<option value="2">
							<s:text name="certainLoss.thirdCarLoss.no" />
						</option>
						<%-- >否 --%>
					</select>
				</td>
			</tr>
			<tr>
				<td style="width: 50%">
					<input type=text name="DaysName" class='readonly' align="center">
				</td>
				<td class="inputsubsub" style="width: 50%" align="right">
					<input name="prpLlossDtoDays" class="common" style="width: 98%" value='0.0'>
				</td>
			</tr>
		</table>
	</form>
	<table class=common cellpadding="5" cellspacing="1">
		<tr>
			<td style="width: 100%" align=center>
				<input type="button" class=button name="buttonGenerateCtext" value="<s:text name='button.determine.value' />" onclick="fuzhi();">
			</td>
			<%-- 确定 --%>
		</tr>
	</table>
</body>
</html>