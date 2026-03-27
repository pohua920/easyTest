<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ page import="java.util.*"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>

<html>
  <head>
  	<jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
	</head>
	<body >
		<form name="fm" method="post" action="/undwrt/hebaoTaskDeal.do?actionType=retractSubmit">
			<input type="hidden" name="handType" value="11">
			<input type="hidden" name="editType" value="deal"><!-- 王俊写死的，其他人用不到此页面应该可以写死 -->
			<table class="common" cellpadding="5" cellspacing="1" align="center">
				<tr class=listtitle>
				  <td  colspan="4"><s:text name="undwrt.HebaoRetractQuery.submitTaskRecallBack"/></td>
				</tr>
			  <tr class=listtitle>
			  <td class="title4"><s:text name="undwrt.HebaoRetractQuery.businessNo"/>：</td>
				  <td class="input2">
					  <input class=query type="text" name="businessNo" MaxLength="25">
					</td>
					<td class="input4">
					<Input class="longbutton" name="buttonSubmit" type="button" value="<s:text name='undwrt.pages.undwrtDeal.submitMissionWithdraw'/>" onclick="submitRetractForm();" >
		   　　 </td>
				</tr>
			</table>
		</form>
	</body>
</html>
