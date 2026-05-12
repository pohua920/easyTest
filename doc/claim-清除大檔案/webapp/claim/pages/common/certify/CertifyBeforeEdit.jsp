<%--
****************************************************************************
* DESC       ：录入单证前输入报案号页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-05
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<title><s:text name="title.certainLossBeforeEdit.entryDocument" /></title>
<%--录入单证--%>
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script language="javascript">
  function submitForm() {
    fm.buttonNext.disabled = true;
    fm.submit(); //提交
  }
</script>
</head>

<body  onload="initPage();">
<form name="fm" action="${ctx }/certifyBeforeEdit.do"  method="post"  onsubmit="return validateForm(this);">

    <table  border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=2 class="formtitle">
					<s:text name="certify.inputReportNo(registration documents)" />
				</td>
			</tr>
			<%--输入报案号（单证登记）--%>
      <tr>
				<td class='title2' align="center">
					<s:text name="prpLregist.registNo" />：
				</td>
				<%--报案号--%>
        <td class='input2' >
          <input type=text name="RegistNo" class="common" value="RDAA200431000000000035">
        </td>
        </tr>
        <tr>
        <td class='button' align="center" colspan="2">
					<input type="button" class='button' value="<s:text name='button.next.value' />" name="buttonNext" onclick="submitForm();">
					<%--下一步--%>
        </td>
      </tr>
    </table>
    <input type="hidden" name="editType" value="ADD">
  </form>
</body>
</html>




