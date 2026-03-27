<%--
***************************************************************************
* Description: 提交选择人员
* Author     : luyang
* CreateDate : 2005-2-24 9:09
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ include file="/common/i18njs.jsp"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<!-- 滚动条样式定义 -->
<%@ include file="/common/CommonStyle.html"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
   <head>
   <app:css/>
    <title><s:text name="undwrt.SubmitUserList.chooseSubmitPerson"/> </title>

    <!--样式-->
    <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
    <!--通用函数-->
    <script src="/undwrt/common/js/Common.js"></script>
    <!--通用任务处理函数-->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>

   </head>
<body class=interface>
<form name="fm">
  <table class="common" cellpadding="5" cellspacing="1" align="center">
     <tr>
       <td class="formtitle" colspan="1"><s:text name="undwrt.SubmitUserList.nodeBusinessPerson"/></td>
     </tr>
     <tr>
       <td class="input" align=center>
         <select name="people" style="width:100%">
           <logic:notEmpty name="submitUserList">
           <logic:iterate indexID="index" id="userList" name="submitUserList">
              <option value=<bean:write name="userList" property="userCode"/> ><bean:write name="userList" property="userCode"/>-<bean:write name="userList" property="userName"/></option>
           </logic:iterate>
           </logic:notEmpty>
         </select>
        </td>
     </tr>
  <table>
  <table class=sub>
     <td class=button>
       <IMG class="button" name="buttonSubmit" type="button" alt="<s:text name='prompt.ok'/>"  src="/undwrt/common/images/butOk.gif" onclick="return submitUser();">
     </td>
     <td class=button>
       <IMG name="buttonCancel" class="button" type="button" alt="<s:text name='prompt.cancel'/>"  src="/undwrt/common/images/butCancel.gif" onclick="window.close()">
     </td>
  </table>
</body>
</form>
</html>