<%--
****************************************************************************
* DESC       ：结案中查询打印赔款计算书附页结果显示页面
* AUTHOR     ：zhaozhuo
* CREATEDATE ：2005-04-06
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html locale="true">
<head>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/endcase/js/DAAEndcaseEdit.js">
	
</script>
</head>
<body>
	<table class=common cellpadding="5" cellspacing="1">
		<s:iterator var="strcompensateNo" value="#attr.compensateNo.split(',')" status="compensateNo_status">
			<tr>
				<td width="33%">
					<s:text name="menu.claimPrint.indemnityCompensate" />
					${compensateNo_status.index }
				</td>
				<!--赔款计算书-->
				<td width="33%" align="center">${strcompensateNo }</td>
				<td width="33%" align="center" class="input">
					<input type="button" name=buttonPrint13 class='bigbutton' value="<s:text name='button.printPage.value'/>" onclick="return printForm1(this,'${strcompensateNo }','CompensateAdd');">
				</td>
				<!--打印附页-->
			</tr>
		</s:iterator>
	</table>
</body>
</html>