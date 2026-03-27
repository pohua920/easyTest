<%--
****************************************************************************
* DESC       ：代查勘委托页面
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-03-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html; charset=GBK" %>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
	<%@include file="/common/meta_css.jsp"%>
	<%@include file="/common/meta_js.jsp"%>
  <title><s:text name="general.guide"/></title><%--代查勘委托页面 --%>
 <script type="text/javascript">
  function guide(field) {
	  var registNo = trim(fm.registNo.value);
		if (registNo == "") {
			alert(i18n.generalClaim.reportNumberCannotEmpty); //报案号不能为空!
			return false;
		}
		if (registNo.length != 21) {
			alert(i18n.generalClaim.reportNumber21Long); //报案号应为21位长!
			return false;
		}else{
			fm.action = "/claim/generalClaimBeforeEdit.do";
			// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
			field.disabled = true;
			fm.submit();
		}
	}
</script>
</head>
<body>
<form name="fm" action="" method="post" >
  <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
  <tr><td colspan=2 class="formtitle"><s:text name="general.enterRegistNo"/></td></tr><%--请输入报案号 --%>
    <tr>
      <td class="title2"  align="center"><s:text name="prpLregist.registNo"/>：</td><%--报案号 --%>
      <td class="input2">
        <input type="text" name="registNo" class="common" style="width: 50%;">
        <input type="hidden" name="actionType" class="common" value="${param.actionType}">
      </td>
      </tr>
      <tr>
      <td class="button"   colspan=2 align="center">
        <input type=button class="button" class="button" value="<s:text name="button.next.value"/>"  onclick="guide(this)"><%--下一步 --%>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
