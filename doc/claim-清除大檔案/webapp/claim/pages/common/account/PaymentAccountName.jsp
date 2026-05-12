<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<base target="_self">
<title><s:text name="title.account.payObjectNameInsert" /></title>
<!-- 支付对象名称录入 -->
<link href="${ctx }/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function choose() {
		var serialNo = fm.serialNo.value;
		var payObjectName = fm.ownerName.value;
		window.returnValue = payObjectName;//将输入的支付对象姓名带到页面里
		window.close();
	}
	function cancel() {
		window.returnValue = "";
		window.close();
	}
</script>
</head>
<body class="interface" style="BORDER: #3D72D7 1px solid">
	<form name="fm" action="" method="post">
		<input type="hidden" name="serialNo" value="${param.serialNo }">
		<table class="common" cellpadding="2" cellspacing="0" align="center" id="resultTab">
			<tr>
				<td width=50% align="center">
					<input class="button" type="button" name="SelectIt" value="<s:text name="prompt.ok"/>" onclick='choose()'>
				</td>
				<!-- 确定 -->
				<td width=50% align="center">
					<input name="CancelIt" class="button" type="button" value="<s:text name="prompt.cancel"/>" onclick='cancel()'>
				</td>
				<!-- 取消 -->
			</tr>
			<tr>
				<td width='50%' align="center">
					<s:text name="db.prpLpersonloss.personName" />:
					<!-- 人员名称 -->
				</td>
				<td width='50%'>
					<input type="text" class="common" name="ownerName" maxlength="15" value="<c:out value="${param.ownerName}"/>">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
