<%--
****************************************************************************
* DESC       ：受益人信息显示画面
* AUTHOR     ： Sinosoft
* CREATEDATE ： 2005-08-04
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%> 
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><s:text name="title.regist.benefitInformationShow" /></title>
<%--受益人信息显示 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginhigh="0">
<form name="fm">
  <table class="common" cellpadding="5" cellspacing="1">
 		 <s:if test="#request.beneInsuredList!=null && #request.beneInsuredList.size()>0">
       <tr >
        <td class="prompttitle"><s:text name="db.prpDrate.serialNo" /><%--序号 --%></td>
        <td class="prompttitle"><s:text name="regist.benefitName" /><%--受益人姓名 --%></td>
        <td class="prompttitle"><s:text name="db.prpDDriver.identifyNumber" /><%--身份证号 --%></td>
        <td class="prompttitle"><s:text name="regist.benefitRate" /><%--受益比例 --%></td>
      </tr> 
        <c:forEach var="prpCinsured" items="${beneInsuredList}" varStatus="beneInsuredListIndex">
        <tr>        
          <td class="prompt">${beneInsuredListIndex.index+1}</td> 
          <td class="prompt">${prpCinsured.insuredName}</td>
          <td class="prompt">${prpCinsured.identifyNumber}</td>
          <td class="prompt">${prpCinsured.benefitRate}</td>
        </tr>       
        </c:forEach>
        </s:if>
        <s:else>
            <tr><td colspan=14 class=common align=center>
            <font size=5>法定受益人</font>
            </td>
            </tr>
       </s:else>
      <tr>  
        <td colspan=14 class="common" align="center">
          <input type=button name = 'button_Peril_Close_Context' class=button value="<s:text name='button.useOClose.value' />"  ACCESSKEY="O" onclick="window.close()">  
        </td> <%--(O)关闭 --%>
      </tr>
  </table>        
</form>  
</body>
</html>
