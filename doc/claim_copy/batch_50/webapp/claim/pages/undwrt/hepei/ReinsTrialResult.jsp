<%--
****************************************************************************
* DESC       : 分摊试算结果
* AUTHOR     : 
* CREATEDATE : 2005-08-22
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<title><s:text name="title.claimBeforeEdit.shareTrialResult" /></title>
<%--分摊试算结果--%>
</head>

<body class="interface">
<table border="0" align="center" cellpadding="4" cellspacing="1"  class="common" >
  <tr class="title">
			<td colspan=5 class="subformtitle" align=center>
				<br>
				<font size="3"><b><s:text name="undwrt.hepei.dangerousUnitDetail" /></b></font><br>
			</td>
		</tr>
		<%--危险单位分摊明细--%>
		<tr class="title">
			<td class="subformtitle" width="10%">
				<s:text name="regist.prpLregist.serialNo" />
			</td>
			<%--序号--%>
			<td class="subformtitle" width="25%">
				<s:text name="claim.whichWay" />
			</td>
			<%--分保方式--%>
			<td class="subformtitle" width="25%">
				<s:text name="undwrt.hepei.nameContract" />
			</td>
			<%--合约名称--%>
			<td class="subformtitle" width="20%">
				<s:text name="undwrt.hepei.standBackShare" />
			</td>
			<%--摊回份额％--%>
			<td class="subformtitle" width="20%">
				<s:text name="compensate.shareAmount" />
			</td>
			<%--分摊金额--%>
  </tr>
  <c:if test="${reinsTrial!=null}">
  	<c:forEach items="${reinsTrial}" var="reinsRepayCalResult">
  	<tr>
	  <td class="input" width="10%">${reinsRepayCalResult.dangerNo}</td>
      <td class="input" width="25%">${reinsRepayCalResult.reinsModeName}</td>
      <td class="input" width="25%">${reinsRepayCalResult.treatyName}</td>
					<td class="input" width="20%">
						<fmt:formatNumber value="${reinsRepayCalResult.shareRate}" pattern="#" />
					</td>
					<td class="input" width="20%">
						<fmt:formatNumber value="${reinsRepayCalResult.sumPaid}" pattern="#" />
					</td>
  </tr>
 </c:forEach>
  </c:if>
   <tr>
			<td class="input" colspan=5 align=center>
				<br>
				<br>
				<input type="button" class="button" value="<s:text name='button.close.value' />" onclick="window.close()">
				<br>
			</td>
   </tr>
</table>
</body>
</html>