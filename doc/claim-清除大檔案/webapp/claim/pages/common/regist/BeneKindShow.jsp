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
<title><s:text name="title.regist.kindBenefitInformationShow" />
	<%--险种保益信息显示 --%></title>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginhigh="0">
	<form name="fm">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td class="prompttitle">
					<s:text name="db.prpDPersonPay.serialNo" />
					<%--序号 --%>
				</td>
				<td class="prompttitle">
					<s:text name="db.prpDRate_Car.kindCode" />
					<%--险别代码 --%>
				</td>
				<td class="prompttitle">
					<s:text name="db.prpDrate.kindName" />
					<%--险别名称 --%>
				</td>
				<td class="prompttitle">
					<s:text name="regist.markFineName" />
					<%--标的名细名称 --%>
				</td>
				<td class="prompttitle">
					<s:text name="db.prpLendor.currency" />
					<%--币别 --%>
				</td>
				<td class="prompttitle">
					<s:text name="db.prpLloss.amount" />
					<%--保险金额 --%>
				</td>
				<td class="prompttitle">
					<s:text name="db.prpDration.premium" />
					<%--保费 --%>
				</td>
      </tr> 
      <s:if test="#request.beneKindList!=null">
      <c:forEach var="prpCitemKind" items="${beneKindList}" varStatus="prpCitemKindIndex">
        <tr>        
          <td class="prompt">${prpCitemKindIndex.index+1}</td> 
          <td class="prompt">${prpCitemKind.kindCode}</td>
          <td class="prompt">${prpCitemKind.kindName}</td>
          <td class="prompt">${prpCitemKind.itemDetailName}</td>
          <td class="prompt">${prpCitemKind.currency}</td>
          <td class="prompt">${prpCitemKind.amount}</td>
          <td class="prompt">${prpCitemKind.premium}</td>
        </tr>    
        </c:forEach>
         </s:if>   
      <tr>  
        <td colspan=14 class="common" align="center">
          <input type=button name = 'button_Peril_Close_Context' class=button value='<s:text name="button.useOClose.value" />'  ACCESSKEY="O" onclick="window.close()">  
        </td> <%--(O)关闭 --%>
      </tr>
  </table>        
</form>  
</body>
</html>
