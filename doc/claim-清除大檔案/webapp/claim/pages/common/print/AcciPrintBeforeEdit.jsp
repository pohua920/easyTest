<%--
****************************************************************************
* DESC       ： 意健险列印前输入业务号页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-05-21
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@page import="java.util.Map"%>
<%@page import="com.sinosoft.claim.common.ConstantsCollection"%>

<%
	String strPrintType = request.getParameter("printType");
	String strTitleName = "";
	String strBizName = "";
	String strWherePart = "";
	int intCount = 0;
	String strMessage = "";
	String[] strprintInfo = ConstantsCollection.AcciPrintInfo.get(strPrintType).split("#!");
	strTitleName = strprintInfo[0];
	strBizName = strprintInfo[1];
%>
<html>
<head>
<title>理賠列印前輸入業務號</title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language='javascript'>
      function loadForm() {
      	TitleName.innerHTML = '<%=strTitleName%>';
      	fm.PrintType.value = '<%=strPrintType%>';
      	BizName.innerHTML = '<%=strBizName%>' + '：';
      	fm.BizName.value = '<%=strBizName%>';
      }

      function checkForm() {
      	if (fm.BizNo.length < 1) {
      		fm.BizNo.focus();
      		errorMessage(fm.BizName.value + "不能为空!");
      		return false;
      	}
      	return true;
      }

      function submitForm() {
      	if (checkForm() == true) {
      		var strUrl = "/claim/JRAcci"+fm.PrintType.value+".do?printType=" + fm.PrintType.value + "&businessNo=" + fm.BizNo.value;
      		printWindow(strUrl, "<%=strTitleName%>");
      	}
      }

      //显示列印窗口
      function printWindow(strURL, strWindowName) {
      	var pageWidth = screen.availWidth - 10;
      	var pageHeight = screen.availHeight - 30;
      	if (pageWidth < 100)
      		pageWidth = 100;
      	if (pageHeight < 100)
      		pageHeight = 100;
      	var newWindow = window
      		.open(
      			strURL,
      			strWindowName,
      			'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
      	newWindow.focus();
      	return newWindow;
      }
</script>
</head>
<body onload="loadForm();">
	<form name="fm" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle" id="TitleName"></td>
			</tr>
			<tr>
				<td class='title2' id="BizName"></td>
				<td class='input2'><input type='hidden' name='BizName'> <input class="common" type='text' name='BizNo' maxlength='25'></td>
			</tr>
			<tr>
				<td class="button" align="center" colspan="2"><input type=button value="下一步" class='button' onclick="submitForm();"> <input type='hidden' name="PrintType"></td>
			</tr>
		</table>
	</form>
</body>
</html>