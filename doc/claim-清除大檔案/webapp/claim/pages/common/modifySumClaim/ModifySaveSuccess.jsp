
<%@ page contentType="text/html; charset=GBK"%>
<html>
<head>
<title><s:text name="db.prpLregist.registNo" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body class="interface">
	<table cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" align="center">
		<tr>
			<td class=formtitle colspan="2">
				<s:text name="prompt.system.title" />
			</td>
		</tr>
		<tr>
			<td class="common">
				<img src='/claim/images/bgClaimSuccess.gif'>
			</td>
			<td class="common">
				<%
					String message = (String) request.getAttribute("successMessage");
					String messageNo = (String) request.getAttribute("successNO");
					out.println(message);
				%><br>
				<%=messageNo%>
			</td>
		</tr>
	</table>
</body>
</html>
