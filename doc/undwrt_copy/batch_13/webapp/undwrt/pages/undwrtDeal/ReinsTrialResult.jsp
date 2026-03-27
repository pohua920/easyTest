<%--
****************************************************************************
* DESC       : ·ÖÌ¯ÊÔËã½á¹û
* AUTHOR     : 
* CREATEDATE : 2005-08-22
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
<title><s:text name="undwrt.pages.undwrtDeal.ReinsTrialResult"/></title>
</head>

<body class="interface">
<table border="0" align="center" cellpadding="4" cellspacing="1"  class="common" >
  <tr class="title"><td colspan=4 class="subformtitle" align=center><br><font size="3" ><b><s:text name="undwrt.pages.undwrtDeal.dangerComNumber"/>1</b></font><br></td></tr>
  <tr class="title">
      <td class="subformtitle"><s:text name="undwrt.pages.undwrtDeal.reinsuranceWay"/></td> 
      <td class="subformtitle"><s:text name="undwrt.pages.undwrtDeal.ratio"/>£¥</td>
      <td class="subformtitle"><s:text name="undwrt.pages.undwrtDeal.appraisalDamage"/></td>
      <td class="subformtitle"><s:text name="undwrt.pages.undwrtDeal.settledClaim"/></td>
  </tr>
  <s:if test="#request.reinsTrial!=null">
  <s:iterator id="prpLreinsTrial" status="statu" value="#request.dangerTrial">
  <tr>
      <td class="input"><s:property value="#prpLreinsTrial.dangerDesc" /></td>
      <td class="input"><fmt:formatNumber value="${shareRate}" pattern="00.000000"/></td>
      <td class="input"><fmt:formatNumber value="${sumClaim}" pattern="0.00"/></td>
      <td class="input"><fmt:formatNumber value="${sumPaid}" pattern="0.00"/></td>
  </tr>
   </s:iterator>
  </s:if>
   <tr>
        <td  class="input" colspan=4 align=center><br><br>
        <input type="button" class="button" value="<s:text name='undwrt.close'/>" onclick="window.close()"><br></td>
   </tr>
</table>
</body>
</html>