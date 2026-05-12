<%--
****************************************************************************
* DESC       ：预赔修改列表页面
* AUTHOR     ：
* CREATEDATE ：2003-03-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page errorPage="/UIErrorPage"%>
<%-- 引入bean类部分 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.string.*"%>
<%@page import="com.ddcc.utiall.blsvr.*"%>
<%@page import="com.ddcc.ddccall.blsvr.lp.*"%>
<%@page import="com.ddcc.ddccall.schema.*"%>
<%@page import="com.ddcc.utility.*"%>
<%@page import="com.sinosoft.claim.util.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.UICodeAction"%>
--%>
<html>
<head>
<title><s:text name="title.prepayBeforeEdit.reportQueryList" /></title>
<%--报案查询列表--%>
<%-- 公用函数 --%>
<script src="/ddccallweb/common/pub/UICommon.js"></script>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/ddccallweb/css/Standard.css">
</head>
<body>
	<form name="fm">
		<table class="common" align=center cellpadding="5" cellspacing="1">
			<tr class=listtitle>
				<td>
					<s:text name="regist.prpLregist.registNo" />
				</td>
				<%--报案号--%>
				<td>
					<s:text name="db.view_larrearage.policyNo" />
					：
				</td>
				<%--保单号--%>
				<td>
					<s:text name="db.prpLlawsuit.operatorCode" />
				</td>
				<%--操作员--%>
				<td>
					<s:text name="check.enterTime" />
				</td>
				<%--输入时间--%>
			</tr>
			<%--
<%
  String trClass = "";
  String strRiskCode = (String)session.getValue("RiskCode"); 
  String strLink = "";
  String strCondition   = "";
  String strRegistNo  = "";
  String strPolicyNo  = "";
  int    i            = 0;
  BLPrpLregist blPrpLregist = new BLPrpLregist();
  PrpLregistSchema prpLregistSchema = new PrpLregistSchema();
  /* modify for utipower begin*/                  
  //  UtiPower utiPower = (UtiPower)session.getValue("Power");
  /* modify for utipower end*/

  BLPrpDuser blPrpDuser = new BLPrpDuser();  
  UICodeAction uiCodeAction = new UICodeAction();
  String strRiskType = uiCodeAction.translateRiskCodetoRiskType(strRiskCode);
  /*拼查询条件*/

  strCondition = " 1=1 "
               + Str.convertString("RegistNo",request.getParameter("RegistNo"),request.getParameter("RegistNoSign"))
               + Str.convertString("PolicyNo",request.getParameter("PolicyNo"),request.getParameter("PolicyNoSign"));
  
  if("D".equals(strRiskType))
  {
    strCondition +=" AND (CancelDate IS NULL OR CancelDate = '')";
    strCondition +=" AND (DealerCode IS NULL OR DealerCode = '')";
    strCondition +=" AND registno NOT IN (SELECT DISTINCT registno FROM prplcheck)";
  }
  
  strCondition +=" AND registno NOT IN (SELECT DISTINCT registno FROM prplclaim)";

     //modofy by weishixin add begin 20031021
    //原因：增加权限控制
    String PowerUserCode  = (String)session.getValue("UserCode");  //用户
    String PowerRiskCode  = (String)session.getValue("RiskCode");  //险种
    String PowerTaskCode  = SysConst.getProperty("TASKCODE_LPBA") ;  //任务代码
    String PowerTableName = "PrpLregist"  ;                          //权限表名
    
    UtiPower utiPower = new UtiPower();
    strCondition = strCondition + utiPower.addPower(PowerUserCode,PowerRiskCode,PowerTaskCode,PowerTableName);   //得到权限查询条件
    
    //modofy by weishixin add end 20031021 
  
    strRiskCode = (String)session.getValue("RiskCode"); 

  /*modify by zhanjie modify begin 20030815*/
  //只查询当前险种的数据。
    strCondition +=" AND riskcode ='"+strRiskCode+"'";
  /*modify by zhanjie modify end 20030815*/


  
  blPrpLregist.query(strCondition);
  
  for(i=0; i < blPrpLregist.getSize() ; i++) 
  {
    prpLregistSchema = blPrpLregist.getArr(i);
    if(i%2==0)
    {
      trClass = "listodd";
    }
    else
    {
      trClass = "listeven";
    }

    strRegistNo = prpLregistSchema.getRegistNo();
    strRiskCode = prpLregistSchema.getRiskCode();

    //派发到对应险种的编辑页面
    if ("D".equals(strRiskType))
      strRiskCode = BusinessRuleUtil.getOuterCode(request,"RISKCODE_DAA");
    
    strLink = "/ddccallweb/" + strRiskCode + "/lp/regist/UIL" + strRiskCode + "RegistInput.jsp?"  
            + SysConst.getProperty("EDITTYPE") +"="+ SysConst.getProperty("EDITTYPE_UPDATE")
            +"&RegistNo="+ strRegistNo;
%>
      <tr class="common">
        <td><a href="<%=strLink%>"><%=strRegistNo%></a></td>
        <td><%=prpLregistSchema.getPolicyNo()%></td>
        <td><%=blPrpDuser.translateCode(prpLregistSchema.getOperatorCode(),true)%></td>
        <td><%=prpLregistSchema.getInputDate()%></td>
      </tr>
<%
  }
%>
        <tr>
          <td  colspan=5 class=input align=center>共查询出 <%=i%> 条满足条件的记录</td>
        </tr>
--%>
		</table>
	</form>
</body>
</html>