<%--
****************************************************************************
* DESC       ：理赔冲减保额的处理
* Author     : 中国大地项目组
* CREATEDATE ：2003-09-24
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@page errorPage="/UIErrorPage"%>
<%-- 引入bean类部分 --%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.string.*"%>
<%@page import="com.sinosoft.utility.string.Date"%>
<%@page import="com.sinosoft.utiall.dbsvr.*"%>
<%@page import="com.sinosoft.utiall.dbsvr.*"%>
<%@page import="com.sinosoft.prpall.blsvr.lp.*"%>
<%@page import="com.sinosoft.prpall.pubfun.PubTools"%>
<%@page import="com.sinosoft.prpall.pubfun.Bill"%>
<%@page import="com.sinosoft.prpall.blsvr.pg.BLEndorse"%>
<%@page import="com.sinosoft.prpall.blsvr.pg.BLPrpPtext"%>
<%@page import="com.sinosoft.prpall.blsvr.cb.*"%>
<%@page import="com.sinosoft.prpall.schema.*"%>
<%
	//定义变量

	BLPolicy blPolicy = null;
	BLEndorse blEndorse = null;
	Bill bill = new Bill();
	String EndorseNo = "";
	String strContent = "";
	String strMessage = "";
	try {
		String strPtext = request.getParameter("oldPtext");

		blPolicy = ((BLPolicy) session.getValue("Policy"));
		blEndorse = ((BLEndorse) session.getValue("Endorse"));
		//批文重新赋值
		PrpPtextSchema prpPtextSchema = new PrpPtextSchema();
		prpPtextSchema.setEndorseNo(blEndorse.getBLPrpPhead().getArr(0).getEndorseNo());
		prpPtextSchema.setPolicyNo(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
		prpPtextSchema.setLineNo("1");
		prpPtextSchema.setEndorseText(strPtext);
		prpPtextSchema.setFlag("");
		blEndorse.setBLPrpPtext(new BLPrpPtext());
		blEndorse.getBLPrpPtext().setArr(prpPtextSchema);
		EndorseNo = blEndorse.getBLPrpPhead().getArr(0).getEndorseNo();
		blEndorse.settleBeforeSave(blPolicy);
		blEndorse.save(blPolicy);
		strContent = "沖減保額成功,產生批單" + EndorseNo;
%>
<%-- 包含公共信息显示页面 --%>
<jsp:include page="/common/pub/UIMessagePage.jsp">
	<jsp:param name="Picture" value="S" />
	<jsp:param name="Content" value="<%=strContent%>" />
</jsp:include>
<%
	} catch (Exception e) {
		//System.out.println("冲减保额失败");
		bill.putNo(SysConfig.getProperty("ENDORSRE_TABLE"), EndorseNo);
		//System.out.println("fail");      
		strContent = "冲减保额失败！！！";
%>
<%-- 包含公共信息显示页面 --%>
<jsp:include page="/common/pub/UIMessagePage.jsp">
	<jsp:param name="Picture" value="F" />
	<jsp:param name="Content" value="<%=strContent%>" />
</jsp:include>
<%
	}
%>
