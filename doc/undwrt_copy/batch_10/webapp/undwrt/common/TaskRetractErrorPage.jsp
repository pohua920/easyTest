<%@ page contentType="text/html; charset=GBK" %>
<%@ page isErrorPage="true"%>
<%@ page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
  <link href="/undwrt/css/Standard.css" rel="stylesheet" type="text/css">
</head>
<body >

      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td height="20" align="center" class="menu2"><img src="/undwrt/common/images/tanhao.gif"  border="0" align="absmiddle">&nbsp;&nbsp;<font size=4><b><s:text name='prompt.taskBackFail'/>£¡</b></font></td>
        </tr>
        <tr>
          <td align="left" class="menu"><font size=3><b><s:text name='undwrt.CheckAdvanceConditionErrorPage.reasonShow'/>£º</b></font></td>
        </tr>
        <tr id="trContent">
          <td align=left colspan="2"><br>   
          <font size=2>&nbsp;&nbsp;&nbsp;&nbsp;${errorMessage}!</font>
          </td>
        </tr>
          <tr>
          <td align=center colspan="2"> 
               <br><br><br><br>
              <input type="button" class="button" value="<s:text name='prompt.back'/>" onclick = "history.back(0)">
          </td>
        </tr>
      </table>
</body>
</html>