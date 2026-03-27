<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  start-->
<%@ page contentType="text/html; charset=GBK"%>
	<%@ include file="/common/taglibs.jsp"%>
	<%@include file="/common/meta_css.jsp"%>
	<%@include file="/common/i18njs.jsp"%>
	<%@include file="/common/meta_js.jsp"%>

<html>
<head>

<script type="text/javascript" >

	$(function(){
		$("#subTemp").on("keypress", function (e) {
			if (e.keyCode == 13) {
				$('#cardNo', window.parent.document).val($("#subTemp").val());
				$('#fm', window.parent.document).attr("actionType","login");
				$('#fm', window.parent.document).submit();
			}
		});
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title></title>
</head>
<body class="body_12" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form id="fm" name="fm" action="" method="post">
		<table  border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top">
						<input type="password" name="subTemp" id="subTemp" />
					</td>
				</tr>
		</table>
	</form>
</body>
</html>
<!-- mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證  end-->