<%--
****************************************************************************
* DESC       ：综合查询赔款计算书结果显示页面
* Author     : 统计分析项目组
* CREATEDATE ：2005-07-04
* MODIFYLIST ：   Name       Date            Reason/Contents
*                 Wangct		2005-07-04			Created
****************************************************************************
--%>
<%@page errorPage="/UIErrorPage"%>
<%-- 引入bean类部分 --%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%
	//定义变量
	String strRiskCode = request.getParameter("RiskCode");
	String strRegistNo = request.getParameter("RegistNo");
	String strClaimNo = request.getParameter("ClaimNo");
	String strCompensateNo = request.getParameter("CompensateNo");
	String strBusinessType = request.getParameter("BusinessType");
	String strType = request.getParameter("Type");
	String strMainPage = "";
	String strIndexPage = "/claim/undwrt/common/WfLogUIZhQueryClaimIndex.jsp?ClaimNo=" + strClaimNo + "&RegistNo=" + strRegistNo + "&RiskCode=" + strRiskCode + "&CompensateNo=" + strCompensateNo + "&BusinessType=" + strBusinessType;

	if (strCompensateNo == null || strCompensateNo.trim().equals("")) {
		strMainPage = "/prpall/common/qry/UIZhQueryCompensateShow.jsp?Condition=ClaimNo=" + strClaimNo + "";
	} else {
		strMainPage = "/claim/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + strCompensateNo + "&editType=SHOW&riskCode=" + strRiskCode + "&ifclose=true";
	}
%>
<script language=javascript>
</script>
<style>
</style>
<html>
<head>
<title><s:text name="title.undwrtBeforeEdit.ClaimsInformation" /></title>
<%-- 理赔信息 --%>
<%-- 公用函数
  <script src="/prpall/common/pub/UICommon.js"></script> 
  页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<%-- 调用loadForm 初始化页面 --%>
<frameset ROWS="70,*">
	<frame NAME="fraIndex" SRC=<%=strIndexPage%>>
	<frame NAME="fraMain" SRC=<%=strMainPage%>>
</frameset>
</html>
