<%--
***************************************************************************
* Description: 分保试算结果页面
* Author     : 项目组
* CreateDate:  2005-6-4 14:37
* UpdateLog：  Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page language="java" %>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

<%@page import="com.sinosoft.utility.error.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.undwrt.undwrtDeal.vo.ReinsTrialDangerInfoVo"%>
<%@page import="com.sinosoft.undwrt.common.vo.PrpTreinstrialViewInfoVo"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>
  
<%
  String certiNo   = request.getParameter("CertiNo");
  String certiType   = request.getParameter("CertiType");
  String strRetentionRate  = request.getParameter("RetentionRate");
  String strRetentionValue = request.getParameter("RetentionValue");
  strRetentionRate="1";          //供测试用，实际从数据库中取
  String strType = "";
  String strContent = "";  
  String disPlay = "";
  String userGradeValue = (String)request.getAttribute("prpUserGradeValue");
 
  if (userGradeValue.equals("0"))
  {
      disPlay = "none";
  }
  else
  {
      disPlay = "null";
  }
  try
  {
    if (strRetentionRate==null || strRetentionRate.equals("")) strRetentionRate = "0";
    if (strRetentionValue==null || strRetentionValue.equals("")) strRetentionValue = "0";  
  }
  catch(Exception e)
  { 
    strContent = e.toString();
    e.printStackTrace();
    out.println(strContent);
    throw e;
  }  
  if(!certiType.equals("E")){
%>
 <html> 
  <head> 
   <title><s:text name="undwrt.CommonReinsSimulate.dividePolicyTestResultResult"/></title>  
   <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
  </head>  
  <body class="interface">
    <form>
    <table class="common" cellpadding="5" cellspacing="1" align="center" width="100%">
      <tr class=listtitle>
        <td><s:text name="undwrt.CommonReinsSimulate.dividePolicyTestResult"/></td>
      </tr>
      <tr>
        <td class="title">
<%     
        out.println(strContent);
%>       
        </td>
      <tr>
    </table>
    &nbsp;
<table width="847" height="223" border="0" class="sub">
<%  
   Collection result=null;
   DecimalFormat idecimalFormat = new DecimalFormat("0.00");
   DecimalFormat idecimalFormatSix = new DecimalFormat("0.000000");
   
   DecimalFormat idecimalFormat1 = new DecimalFormat("#,##0.00");
   result = (Collection)request.getAttribute("ReinsTrialInfo");
   ReinsTrialDangerInfoVo reinsTrialDangerInfoDto = null; 
    Iterator iterator = result.iterator();
      while(iterator.hasNext())
      {
        reinsTrialDangerInfoDto = (ReinsTrialDangerInfoVo)iterator.next(); 
 %>

  <tr class=common>
    <td colspan="5" class="listtitle"><strong><s:text name="undwrt.CommonReinsSimulate.riskSerialSerialNo"/><%=reinsTrialDangerInfoDto.getDangerNo()%></strong></td>
  </tr>
  <tr>
    <td class="centertitle"><s:text name="undwrt.CommonReinsSimulate.divide"/>&nbsp;<s:text name="undwrt.CommonReinsSimulate.policy"/>&nbsp;<s:text name="undwrt.CommonReinsSimulate.side"/>&nbsp;<s:text name="undwrt.CommonReinsSimulate.style"/></td>
    <td class="centertitle"><s:text name="undwrt.CommonReinsSimulate.than"/>&nbsp;&nbsp;<s:text name="undwrt.CommonReinsSimulate.example"/></td>
    <td class="centertitle"><s:text name="undwrt.CommonReinsSimulate.policy"/>&nbsp;&nbsp;<s:text name="undwrt.CommonReinsSimulate.amount"/></td>
    <td class="centertitle"><s:text name="undwrt.CommonReinsSimulate.policy"/>&nbsp;&nbsp;<s:text name="undwrt.CommonReinsSimulate.amountNTD"/></td>
   <%-- <td class="centertitle">保&nbsp;&nbsp;费</td> --%>
  </tr>
<%
          PrpTreinstrialViewInfoVo prpTreinstrialViewInfoDto = null;
          double tolPremium = 0d;
          double tolAmount  = 0d;
          double tolShareRate = 0d;
          double tolAmountCNY =0d;
          Iterator iteratorReins = reinsTrialDangerInfoDto.getCollection().iterator();
          while(iteratorReins.hasNext())
          { prpTreinstrialViewInfoDto = (PrpTreinstrialViewInfoVo)iteratorReins.next();
            tolPremium = Double.parseDouble(idecimalFormat.format(tolPremium + prpTreinstrialViewInfoDto.getPremium()));
            tolAmount  = Double.parseDouble(idecimalFormat.format(tolAmount  + prpTreinstrialViewInfoDto.getAmount()));
            tolShareRate = Double.parseDouble(idecimalFormatSix.format(tolShareRate  + prpTreinstrialViewInfoDto.getShareRate())); 
            tolAmountCNY =Double.parseDouble(idecimalFormat.format(tolAmountCNY  + prpTreinstrialViewInfoDto.getAmount()*prpTreinstrialViewInfoDto.getExchratecny()));
 %>
  <tr class=common align="center">
    <td  class="formtitle1" align="center">
    <%	if(prpTreinstrialViewInfoDto.getRefNo().equals("<s:text name='undwrt.CommonReinsSimulate.extraAddMySelfRetention'/>")){%>
    <input class="formtitle1" style="color:red" name="shareRate" type="text" readonly="true" value="<s:text name='undwrt.CommonReinsSimulate.extraAddMySelfRetention'/>">
    <%}else{%>
    	<input class="formtitle1" name="shareRate" type="text" readonly="true" value="<%=prpTreinstrialViewInfoDto.getRefNo()%>">
    	 <%}%>
    </td>
    <td  align="right">
    	<input class="formtitle1" name="shareRate" type="text" readonly="true" align="right"
        value="<%=idecimalFormatSix.format(prpTreinstrialViewInfoDto.getShareRate())%>">
        </td>
    <td  align="right">
    	<input class="formtitle1" name="amount" type="text" id="amount" readonly="true" align="right"
        value="<%=idecimalFormat1.format(prpTreinstrialViewInfoDto.getAmount())%>">
        </td>
    <td  align="right">
    	<input class="formtitle1" name="amountcny" type="text" id="amountcny" readonly="true" align="right"
        value="<%=idecimalFormat1.format(prpTreinstrialViewInfoDto.getAmount()*prpTreinstrialViewInfoDto.getExchratecny())%>">
        </td>
  </tr>
  
<%   }
%>
     <tr class=common align="center">
      <td class="formtitle1"><s:text name="undwrt.CommonReinsSimulate.sum"/></td>
      <td align="center"><input class="formtitle1" name="tolShareRate" type="text" readonly="true" 
        value="<%=idecimalFormatSix
				.format(tolShareRate)%>"></td>
      <td align="center"><input class="formtitle1" name="tolAmount" type="text" readonly="true" 
        value="<%=idecimalFormat1
				.format(tolAmount)%>"></td>
	  <td align="center"><input class="formtitle1" name="tolAmountcny" type="text" readonly="true" 
        value="<%=idecimalFormat1
				.format(tolAmountCNY)%>"></td>
				
     </tr>
       
<%   }
 %>

  
</table>

    <table class=sub>
      <tr>
       <td class=button width="100%">
        <Input class="button" name="buttonClose" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.closed'/>" value="<s:text name='undwrt.pages.undwrtDeal.closed'/>" onclick="window.close()">
       </td>
      </tr>
     </table> 
    </form>
  </body>
  <%} else {%>
  <html> 
  <head> 
   <title><s:text name="undwrt.CommonReinsSimulate.dividePolicyTestResultResult"/></title>  
   <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
  </head>  
  <body class="interface">
    <form>
    <table class="common" cellpadding="5" cellspacing="1" align="center" width="100%">
      <tr class=listtitle>
        <td><s:text name="undwrt.CommonReinsSimulate.dividePolicyTestResult"/></td>
      </tr>
      <tr>
        <td class="title">
   </td>
      <tr>
    </table>
    &nbsp;
<table width="847" height="223" border="0" class="sub">
<%  
   Collection result=null;
   DecimalFormat idecimalFormat = new DecimalFormat("0.00");
   DecimalFormat idecimalFormatSix = new DecimalFormat("0.000000");
   
   DecimalFormat idecimalFormat1 = new DecimalFormat("#,##0.00");
   result = (Collection)request.getAttribute("ReinsTrialInfo");
   ReinsTrialDangerInfoVo reinsTrialDangerInfoDto = null; 
    Iterator iterator = result.iterator();
      while(iterator.hasNext())
      {
        reinsTrialDangerInfoDto = (ReinsTrialDangerInfoVo)iterator.next(); 
 %>

  <tr class=common>
    <td colspan="7" class="listtitle"><strong><s:text name="undwrt.CommonReinsSimulate.riskSerialSerialNo"/><%=reinsTrialDangerInfoDto.getDangerNo()%></strong></td>
  </tr>
  <tr>
    <td class="centertitle"><s:text name="undwrt.CommonReinsSimulate.divide"/>&nbsp;<s:text name="undwrt.CommonReinsSimulate.policy"/>&nbsp;<s:text name="undwrt.CommonReinsSimulate.side"/>&nbsp;<s:text name="undwrt.CommonReinsSimulate.style"/></td>
    <td class="centertitle"><s:text name="undwrt.CommonReinsSimulate.approvalAfterScale"/></td>
    <td class="centertitle"><s:text name="undwrt.CommonReinsSimulate.approvalAfterPolicyAmount"/></td>
    <td class="centertitle"><s:text name="undwrt.CommonReinsSimulate.changePolicyAmount"/></td>
<%
          PrpTreinstrialViewInfoVo prpTreinstrialViewInfoDto = null;
          double endorsePremium = 0d;
          double endorseAmount  = 0d;
          double endorseShareRate = 0d;
          double chgPremium      = 0d;
          double chgAmount       = 0d;
          Iterator iteratorReins = reinsTrialDangerInfoDto.getCollection().iterator();
          while(iteratorReins.hasNext())
          { prpTreinstrialViewInfoDto = (PrpTreinstrialViewInfoVo)iteratorReins.next();
            endorsePremium = Double.parseDouble(idecimalFormat.format(endorsePremium + prpTreinstrialViewInfoDto.getPremium()));
            endorseAmount  = Double.parseDouble(idecimalFormat.format(endorseAmount  + prpTreinstrialViewInfoDto.getAmount()));
            endorseShareRate = Double.parseDouble(idecimalFormatSix.format(endorseShareRate  + prpTreinstrialViewInfoDto.getShareRate())); 
            chgPremium     = Double.parseDouble(idecimalFormat.format(chgPremium + prpTreinstrialViewInfoDto.getChgPremium())); 
            chgAmount      = Double.parseDouble(idecimalFormat.format(chgAmount + prpTreinstrialViewInfoDto.getChgAmount()));
 %>
  <tr class=common>
    <td width="168" class="formtitle1" align="center">
    	  <%	if(prpTreinstrialViewInfoDto.getRefNo().equals("<s:text name='undwrt.CommonReinsSimulate.extraAddMySelfRetention'/>")){%>
    <input class="formtitle1" style="color:red" name="shareRate" type="text" readonly="true" value="<s:text name='undwrt.CommonReinsSimulate.extraAddMySelfRetention'/>">
    <%}else{%>
  <input class="formtitle1" name="shareRate" type="text" readonly="true" value="<%=prpTreinstrialViewInfoDto.getRefNo()%>">
    	 <%}%>
    	
    	</td>
    <td width="168"><input class="formtitle1" name="shareRate" type="text" readonly="true" 
        value="<%=idecimalFormatSix.format(prpTreinstrialViewInfoDto.getShareRate())%>"></td>
    <td width="168"><input class="formtitle1" name="amount" type="text" id="amount" readonly="true" 
        value="<%=idecimalFormat1.format(prpTreinstrialViewInfoDto.getAmount())%>"></td>
    <td width="168"><input class="formtitle1" name="premimu" type="text" id="premimu" readonly="true" 
        value="<%=idecimalFormat1.format(prpTreinstrialViewInfoDto.getChgAmount())%>"></td>
  </tr>
 
<%          }
%>
     <tr>
      <td class="centertitle"><s:text name="undwrt.CommonReinsSimulate.sum"/></td>
      <td class="centertitle"><input class="formtitle1" name="endorseShareRate" type="text" readonly="true" 
        value="<%=idecimalFormatSix
				.format(endorseShareRate)%>"></td>
      <td class="centertitle"><input class="formtitle1" name="endorseAmount" type="text" readonly="true" 
        value="<%=idecimalFormat1
				.format(endorseAmount)%>"></td>
      <td class="centertitle"><input class="formtitle1" name="chgPremium" type="text" readonly="true" 
        value="<%=idecimalFormat1
				.format(chgAmount)%>"></td>
     </tr>
<%       }
 %>

  
</table>

    <table class=sub>
      <tr>
       <td class=button width="100%">
        <Input class="button" name="buttonClose" type="button" alt="<s:text name='undwrt.close'/>" value="<s:text name='undwrt.pages.undwrtDeal.closed'/>" onclick="window.close()">
       </td>
      </tr>
     </table> 
    </form>
  </body>
  <% }%>
