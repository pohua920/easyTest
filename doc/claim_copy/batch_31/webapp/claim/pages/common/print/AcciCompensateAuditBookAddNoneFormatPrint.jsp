<%--
****************************************************************************
* DESC       ：意键险赔款理算书附页打印页面
* AUTHOR     ：liuwei
* CREATEDATE ：2010-03-04
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>

<%-- 引入bean类部分 --%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.util.*"%>

<%
	//变量声明部分
	String strCompensateNo = request.getParameter("CompensateNo"); //赔款理算书号
	String strTextType = "";
	String strContext = ""; //赔款理算书文字
	String strClaimNo = "";

	//对象定义部分

	PrpLctextDto prpLctextDto = null;
	int intCtextCount = 0; //CtextDto对象的记录数

	int index = 0;

	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");
	if (compensateDto != null && compensateDto.getPrpLcompensateDto() != null) {
		strClaimNo = compensateDto.getPrpLcompensateDto().getClaimNo();
	}

	//得到dbPrpLctext对象的记录数
	if (compensateDto.getPrpLctextDtoDtoList() != null) {
		intCtextCount = compensateDto.getPrpLctextDtoDtoList().size();
	}
%>

<script language="javascript">
function loadForm() {
	tdCompensateNo.innerHTML = '赔案编号：' + '<%=strClaimNo%>' + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;理算书号：' + '<%=strCompensateNo%>'; <%
	if (compensateDto.getPrpLctextDtoDtoList() != null) {
		//*****赔款计算文字表PrpLctext*****
		for (index = 0; index < intCtextCount; index++) {
			prpLctextDto = (PrpLctextDto) compensateDto.getPrpLctextDtoDtoList().get(index);
			strTextType = StringConvert.encode(prpLctextDto.getTextType());
			if (strTextType.charAt(0) == '5') {
				strContext = strContext + StringConvert.encode(prpLctextDto.getContext());
			}
		}
	}

	//检查计算书文字打出来是几行
	int intCtextCountTmp = 0; //textarea的行数
	//    String la="\\";
	//    String lala="\\r\\n";

	for (index = 0; index < strContext.length(); index++) {
		if (strContext.substring(index, index + 1).equals("\\")) {
			if (!(strContext.substring(index).length() < 4)) {
				if (strContext.substring(index, index + 4).equals("\\r\\n")) {
					intCtextCountTmp += 1; //只要有回车换行，intCtextCountTmp+1
				}
			}
		}
	}

	int x = 0;
	int y = 0;
	if (!(strContext.length() < 4)) //如果strContext.length()>=4，判断strContext结尾是文字，还是回车换行
	{
		x = strContext.length() - 4;
		y = strContext.length();
		if (!strContext.substring(x, y).equals("\\r\\n")) {
			intCtextCountTmp += 1;
		}
	} else
	//如果strContext不足1行，intCtextCountTmp = 1;
		intCtextCountTmp = 1;

	while (strContext.indexOf("\\r\\n") != -1)
		strContext = strContext.substring(0, strContext.indexOf("\\r\\n")) + "<br>" + strContext.substring(strContext.indexOf("\\r\\n") + "\\r\\n".length()); %>
		tdContext.innerHTML = '<%=strContext%>';
}
</script>
<html>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<body bgcolor="#FFFFFF" onload="loadForm();">
	<form name="fm">
		<!-- 标题部分 -->
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="3" height="40" align=top align=center style="font-family: 宋体; font-size: 14pt;">
					<B>意键险理赔赔款计算书附页<B>
				</td>
			</tr>
			<tr>
				<td width="10%"></td>
				<td width="90%" align=right id="tdCompensateNo" style="font-family: 宋体; font-size: 10pt;">理算书号：</td>
			</tr>
		</table>
		<!-- 主体部分 -->
		<table border=1 width="92%" height="90%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
			<tr>
				<td align="center" height="25" colspan="12">理算计算公式</td>
			</tr>
			<tr>
				<td id="tdContext" colspan="12" height="375" valign="top"></td>
			</tr>
		</table>
	</form>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
