<%--
****************************************************************************
* DESC       ： 责任險列印输入业务号页面
* AUTHOR     ： 中科軟
* CREATEDATE ： 2014-6-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>

<html>
<head>
  <title>責任險列印</title>
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="${ctx}/pages/commonLiab/print/js/LiabPrintBeforeEdit.js"></script>
</head>

<body>
<form name="fm" method="post" action="${ctx}/liabPrintBeforeEdit.do">
    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
	    <tr>
	        <td width="100%" class="formtitle" colspan="2">${requestScope.titleName}</td>
	    </tr>
	    <tr>
	    	<td  width="100%" align="center" colspan="2">
	    		<c:forEach items="${requestScope.bizNoTypes }" var="bizNotype">
	    			<tr>
		    			<td class='common' style="width:30%">${bizNotype.value }：</td>
		    			<td class='common' style="width:70%"> <input class="common" type='text' name="${bizNotype.key}"/></td>
	    			</tr>
	    		</c:forEach>
	    		<input type="hidden" name="printType" value="${requestScope.printType}"/>
	    	</td>
	 	<tr>
	        <td class="button" align="center" colspan="2">
	          <input type=button value="列印" class='button' onClick="submitForm();">
	      	</td>
	    </tr>
    </table>
</form>
</body>
</html>



