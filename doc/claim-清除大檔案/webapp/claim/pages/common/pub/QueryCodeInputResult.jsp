<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.Date;" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app"%>

<html>
<head>
<base target="_self">
<title><s:text name="title.pubBeforeEdit.pleaseSelect" /></title><%--«Î—°‘Ò--%>
<link href="/claim/css/Standard.css" rel="stylesheet" type="text/css">
<%@include file="/common/meta_js.jsp"%>
</head>
<body class="interface" onload="loadPage()" style="BORDER: #3D72D7 1px solid">
<form name="fm" action="${ctx}/processClaimCodeInput.do?actionType=queryContinue&t=<%(new Date()).toString();%>">
  <span id="cond" style="display:none">
		<textarea name="fieldIndex"><c:out value="${param.fieldIndex}" /></textarea>
		<textarea name="fieldValue"><c:out value="${param.fieldValue}" /></textarea>
		<textarea name="codeMethod"><c:out value="${param.codeMethod}" /></textarea>
		<textarea name="codeType"><c:out value="${param.codeType}" /></textarea>
		<textarea name="codeRelation"><c:out value="${param.codeRelation}" /></textarea>
		<textarea name="isClear"><c:out value="${param.isClear}" /></textarea>
		<textarea name="otherCondition"><c:out value="${param.otherCondition}" /></textarea>
		<textarea name="typeParam"><c:out value="${param.typeParam}" /></textarea>
		<textarea name="callBackMethod"><c:out value="${param.callBackMethod}" /></textarea>
		<textarea name="getDataMethod"><c:out value="${param.getDataMethod}" /></textarea>
		<input type="hidden" name="pageNo" value="<c:out value="${param.pageNo}" />" />
		<textarea name="rowsPerPage"><c:out value="${param.pageSize}" /></textarea>
		<textarea name="elementOrder"><c:out value="${param.elementOrder}" /></textarea>
		<textarea name="elementLength"><c:out value="${param.elementLength}" /></textarea>
  </span>
  <table class="common" cellpadding="2" cellspacing="0" align="center" style="display:" id="resultTab" >
    <tr>
      <td width=50% align="center"><input class="button" type="button" name="SelectIt" value="<s:text name='button.determine.value' />"
        onclick='setFieldValue()'></td>
      <td width=50% align="center"><input name="CancelIt" class="button" type="button" value="<s:text name='button.cancel.value' />"
        onclick='cancelFieldValue()'></td>
    </tr>
    <tr>
        <td colspan=2 align="center">
            <select name=codeselect class="one" size=20 style="width: 100%" <c:if test="${param.codeMethod=='query'}"><c:out value="multiple" /></c:if> ondblclick=setFieldValue() onkeydown=fieldOnKeyPress()>
                <c:forEach items="${requestScope.codeValues}" var="key" varStatus="stat">
                    <option value="${key}"><c:out value="${requestScope.codeLabels[stat.index]}" /></option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
      <td colspan="2" align="center"><app:navigate objectName="page" display="false"/></td>
    </tr>
  </table>
  <p id="Context" style="FONT-SIZE: 10pt;color:red"></p>
</form>
<script language='javascript'> 
    function loadPage(){
    	clearFieldValue();
        if(fm.getDataMethod.value!=""){
            fm.codeselect.innerText=eval("parent." + fm.getDataMethod.value);
        }
        resultTab.scrollIntoView(false);
    }
</script>
<script type="text/javascript">
    $(function(){
        $("select").click(function(){
            if(this.selectedIndex >= 0){
                var textValue = this.options[this.selectedIndex].text;
                $("#Context").text("Æî«∞ﬂx÷–É»»›£∫"+textValue);
            }else{
                $("#Context").text("");
            }
        });
    });
</script>
</body>
</html>
