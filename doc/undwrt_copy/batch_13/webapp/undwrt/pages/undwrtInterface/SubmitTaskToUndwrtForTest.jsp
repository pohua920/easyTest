<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

<html>
<head>
<title><s:text name="undwrt.pages.undwrtInterface.SubmitTaskToUndwrtForTest"/></title>
<jsp:include page="/common/meta_css.jsp" />
<jsp:include page="/common/meta_js.jsp" />
</head>
<body>
	<form name="fm" method="post" action="">
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class=listtitle>
				<td  colspan="4"><s:text name="undwrt.pages.undwrtInterface.SubmitTaskToUndwrtForTest"/></td>
			</tr>
			  	<tr class=listtitle>
			  	<td class="title4"><s:text name="undwrt.pages.undwrtDeal.certiNo"/>：</td>
				<td class="input2">
					<input class=query type="text" name="businessNo" MaxLength="25">
				</td>
				<td class="input4">
					<Input class="longbutton" name="buttonUnderwrite" type="button" 
						alt="<s:text name='undwrt.pages.undwrtInterface.submitNuclearInsurance'/>" value="<s:text name='undwrt.pages.undwrtInterface.submitNuclearInsurance'/>" onclick="submitForm();">
				</td>
			</tr>
		</table>
	</form>
<script type="text/javascript">
	function submitForm() {
		if (confirm("<s:text name='undwrt.pages.undwrtInterface.SubmitTaskToUndwrtForTest.confirm'/>")) {
			fm.buttonUnderwrite.disabled = true;//提交核保后提交核保按钮变灰失效不可用。
			fm.action = "/undwrt/undwrtSubmit/underwriteSubmit.do";
			fm.submit();
			return true;
		} else {
			return false;
		}
	}
</script>

</body>
</html>