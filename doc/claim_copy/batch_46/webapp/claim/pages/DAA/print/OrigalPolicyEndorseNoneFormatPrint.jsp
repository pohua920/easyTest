<%--
****************************************************************************
* DESC       ：原始保单及批单中的批单信息打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>

<%-- 引入bean类部分 --%> 
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.claim.util.*"%>

<%
	//System.out.println("---------1  以往批单--------------");
	//变量声明部分//变量声明部分
	String strPolicyNo = request.getParameter("PolicyNo"); //保险单号
	String strEndorseNo = ""; //取批单号
	String strInsuredName = ""; //被保险人姓名
	String strEndorDate = ""; //批改日期
	String strUnderWriteName = ""; //最终核批人名称
	String strOperatorCode = ""; //操作员代码姓名对应制单
	String strOperatorName = "";
	String strHandler1Code = ""; //归属业务员代码姓名对应经办
	String strHandler1Name = "";
	//String strTitle             = "大地财产保险股份有限公司机动车辆保险";  //DAA才显示，否则清空
	String strRiskCode = "";
	String strLanguage = "";
	String strWhere = "";
	int intPrpPtextCount = 0;
	int i = 0;

	//代码翻译变量
	boolean isChinese = true; //中文标志

	//对象定义部分
	PrpPheadDto prpPheadDto = new PrpPheadDto(); //批单的PrpPheadDto对象
	PrpPtextDto prpPtextDto = new PrpPtextDto(); //批单的PrpPtextDto对象

	//得到PolicyDto,endorseDto对象
	EndorseDto endorseDto = (EndorseDto) request.getAttribute("endorseDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");

	UICodeAction uiCodeAction = new UICodeAction();
	//得到批单对象
	strWhere = " PolicyNo='" + strPolicyNo + "'";
	//blPrpPhead.query(strWhere,0);
	if (endorseDto.getPrpPheadDtoList() != null && endorseDto.getPrpPheadDtoList().size() > 0) {
		//查询所有的批单，並且按降序排列
		strWhere = "PolicyNo='" + strPolicyNo + "' ORDER BY EndorseNo";
		//blPrpPhead.query(strWhere,0);
%>
<html>
<body bgcolor="#FFFFFF">
	<%
	//循环显示批单的打印界面  
		for (i = 0; i < endorseDto.getPrpPheadDtoList().size(); i++) {
			prpPheadDto = (PrpPheadDto) endorseDto.getPrpPheadDtoList().get(i);

			//险种
			strRiskCode = StringConvert.encode(prpPheadDto.getRiskCode());
			//if (!strRiskCode.equals(BusinessRuleUtil.getOuterCode(request,"RISKCODE_DAA")))
			//  strTitle = "";

			//语种
			strLanguage = StringConvert.encode(prpPheadDto.getLanguage());
			if (!strLanguage.equals("C"))
				isChinese = false;

			//保险单号
			strPolicyNo = StringConvert.encode(prpPheadDto.getPolicyNo());
			strEndorseNo = StringConvert.encode(prpPheadDto.getEndorseNo());

			//被保险人姓名
			strInsuredName = StringConvert.encode(prpPheadDto.getInsuredName());

			//批改日期
			//UICodeAction uiCodeAction = new UICodeAction();
			String strRiskType = uiCodeAction.translateRiskCodetoRiskType(strRiskCode);
			if (("D").equals(strRiskType)) {
				strEndorDate = prpPheadDto.getEndorDate().getYear() + " 年 " + prpPheadDto.getEndorDate().getMonth() + " 月 " + prpPheadDto.getEndorDate().getDate() + " 日 ";
			} else {
				strEndorDate = uiCodeAction.translateRiskCode(strRiskCode, isChinese);
			}

			//最终核批人名称
			strUnderWriteName = StringConvert.encode(prpPheadDto.getUnderWriteName());

			//操作员--对应制单
			strOperatorCode = StringConvert.encode(prpPheadDto.getOperatorCode());
			strOperatorName = uiCodeAction.translateUserCode(strOperatorCode, isChinese);

			//归属业务员代码对应经办   
			strHandler1Code = StringConvert.encode(prpPheadDto.getHandler1Code());
			strHandler1Name = uiCodeAction.translateUserCode(strHandler1Code, isChinese);

			//得到PrpPtextDto对象的记录数
			//  blPrpPtext    = blEndorse.getBLPrpPtext();

			//blPrpPtext        = new BLPrpPtext();
			//blPrpPtext.query(" EndorseNo = \"" + strEndorseNo +"\" ");
			//blPrpPtext.query(" EndorseNo = \'" + strEndorseNo +"\' ");
			//intPrpPtextCount = blPrpPtext.getSize(); 

			UIEndorseAction uiEndorseAction = new UIEndorseAction();
			EndorseDto endorseDtoTemp = uiEndorseAction.findByPrimaryKey(strEndorseNo);
			if (endorseDtoTemp.getPrpPtextDtoList() != null) {
				intPrpPtextCount = endorseDtoTemp.getPrpPtextDtoList().size();
			}
%>
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td align="center" style="font-family: 宋体; font-size: 18pt;">
				<br>批&nbsp;&nbsp;单&nbsp;&nbsp;<br>
				<br>
				<br>
			</td>
		</tr>
	</table>
	<style>
table.Normal,div.Normal {
	font-size: 10pt;
}

pre.Normal {
	margin: 0cm;
	font-siz: 10pt
}
</style>
	<script language="javascript">       
          </script>
	<div class=normal style="margin-left: 30">
		<!--批文头 -->
		<table class=normal border=0 cellspacing=0 cellpadding=0>
			<tr height=25>
				<td width=90>保险单号：</td>
				<td width=300><%=strPolicyNo%></td>
				<td width=90>批 单 号：</td>
				<td width=300><%=strEndorseNo%></td>
			</tr>
			<tr height=25>
				<td>被保险人：</td>
				<td><%=strInsuredName%></td>
				<td id="tdEndorDateRiskNameTitle">批改日期：</td>
				<td><%=strEndorDate%></td>
			</tr>
			<tr height=25>
				<td colspan=4>批&nbsp&nbsp&nbsp&nbsp文：</td>
			</tr>
		</table>
		<!--批文-->
		<table border="0" width="92%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
			<tr>
				<td align="left" valign="top" height="600">
					<%
    	for (int index = 0; index < intPrpPtextCount; index++) {
    				prpPtextDto = (PrpPtextDto) endorseDtoTemp.getPrpPtextDtoList().get(index);
    				String strEndorseText = StringConvert.toHTMLFormat(prpPtextDto.getEndorseText());
    %>
					<%=strEndorseText%><br>
					<%
    	}
    %>
				</td>
			</tr>
		</table>
		<!-- 表尾-->
		<div class=normal style="margin-left: 350; margin-top: 40">保险人签章:</div>
		<div class=normal style="margin-left: 350; margin-top: 30">
			<span class=Normal"> <%=DateTime.current().getYear()%> 年
			</span> <span class=Normal style="LEFT: 6cm;"> <%=DateTime.current().getMonth()%> 月
			</span> <span class=Normal style="LEFT: 6cm;"> <%=DateTime.current().getDate()%> 日
			</span>
		</div>
		<div class=Normal style="margin-top: 60">
			<span style="margin-left: 40;"> 核保： <%=strUnderWriteName%>
			</span> <span style="margin-left: 140;"> 制单： <%=strOperatorName%>
			</span> <span style="margin-left: 140;"> 经办： <%=strHandler1Name%>
			</span>
		</div>
	</div>
	<%-- 分页符,不是最後一条时增加一分页符 --%>
	<%
	if (i != endorseDto.getPrpPheadDtoList().size() - 1)
%>
	<br style="page-break-before: always" clear=all>
	<%
	}
	}
%>
	<div style="margin-top: 10"">
		<%-- include打印按钮 --%>
		<span class=Normal style="LEFT: 10cm; TOP: 1cm"> <jsp:include page="/common/print/PrintButton.jsp" />
		</span>
	</div>
</body>
</html>
