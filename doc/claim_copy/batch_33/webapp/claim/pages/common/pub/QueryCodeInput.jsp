<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<% 
  response.setHeader("Cache-Control","no-store");
  response.setHeader("Pragrma","no-cache");
  response.setDateHeader("Expires",0);
%>
<html>
<base target="_self">
<body class="interface" onload="submitCodeSelectForm()"  style="display:none">
<form name="fm" action="/claim/processClaimCodeInput.do?actionType=query">
  <span id=cond>
    <textarea name=fieldIndex></textarea>
    <textarea name=fieldValue></textarea>
    <textarea name=codeMethod></textarea>
    <textarea name=codeType></textarea>
    <textarea name=codeRelation></textarea>
    <textarea name=isClear></textarea>
    <textarea name=isQueryCode></textarea>
    <textarea name=otherCondition></textarea>
    <textarea name=callBackMethod></textarea>
    <textarea name=getDataMethod></textarea>
    <textarea name=pageNo></textarea>
    <textarea name=rowsPerPage></textarea>
    <textarea name=elementOrder></textarea>
    <textarea name=elementLength></textarea>
    <textarea name=actionType>query</textarea>
  </span> 
</form>
<script language='javascript'>
function submitCodeSelectForm() {
	var obj = window.dialogArguments.prototype;
	fm.fieldIndex.value = obj.fieldIndex;
	fm.fieldValue.value = obj.fieldValue;
	fm.codeMethod.value = obj.codeMethod;
	fm.codeType.value = obj.codeType;
	fm.codeRelation.value = obj.codeRelation;
	fm.isClear.value = obj.isClear;
	fm.isQueryCode.value = obj.isQueryCode;
	fm.otherCondition.value = obj.otherCondition;
	fm.callBackMethod.value = obj.callBackMethod;
	fm.getDataMethod.value = obj.getDataMethod;
	fm.pageNo.value = obj.pageNo;
	fm.rowsPerPage.value = obj.rowsPerPage;
	fm.elementOrder.value = obj.elementOrder;
	fm.elementLength.value = obj.elementLength;

	if (fm.isClear.value == undefined || fm.isClear.value == "null") {
		fm.isClear.value = "Y";
	}
	if (fm.isClear.value == undefined || fm.isClear.value == "null") {
		fm.isQueryCode.value = "Y";
	}
	if (fm.otherCondition.value == undefined || fm.otherCondition.value == "null") {
		fm.otherCondition.value = "";
	}
	if (fm.callBackMethod.value == undefined || fm.callBackMethod.value == "null") {
		fm.callBackMethod.value = "";
	}
	if (fm.getDataMethod.value == undefined || fm.getDataMethod.value == "null") {
		fm.getDataMethod.value = "";
	}
	if (fm.pageNo.value == undefined || fm.pageNo.value == "null") {
		fm.pageNo.value = "1";
	}
	if (fm.rowsPerPage.value == undefined || fm.rowsPerPage.value == "null") {
		fm.rowsPerPage.value = "20";
	}
	if (fm.elementOrder.value == undefined || fm.elementOrder.value == "null") {
		fm.elementOrder.value = 0;
	}
	if (fm.elementLength.value == undefined || fm.elementLength.value == "null") {
		fm.elementLength.value = 1;
	}
	document.forms[0].submit();
}
</script>
</body>
</html>