<%--
***************************************************************************
* Description: 再保信息界面
* Author     : Luyang
* CreateDate:  2005-1-12 14:30
* UpdateLog：  Name       Date            Reason/Contents
****************************************************************************
--%>

<%@page contentType="text/html;charset=GBK"%>
<%@page import = "java.util.*"%>  
<%@page import = "com.sinosoft.reins.utility.dto.domain.PrpDReinsDto"%>  
<%@page import = "com.sinosoft.reins.utility.bl.facade.BLPrpDReinsFacade"%>   
<%@page import = "com.sinosoft.utility.log.Log"%>
<%@page import = "com.sinosoft.utility.SysConst"%>

<%@page import = "com.sinosoft.utility.error.UserException"%>

<%  
  String ReinsCode      = request.getParameter("ReinsCode"); 
  String FinalReinsCode = request.getParameter("FinalReinsCode");
  String PayCode        = request.getParameter("PayCode");
  String FieldName      = request.getParameter("FieldName");
  BLPrpDReinsFacade blPrpDReinsFacade = new BLPrpDReinsFacade();
  String strWherePart   = "1=1";
  Collection result = null;
  try
  {
    if(ReinsCode != null && !ReinsCode.trim().equals("") && FieldName.trim().equals("ReinsCode"))
    {
      strWherePart = strWherePart + " AND ReinsCode like '%" + ReinsCode + "%' and ReinsKind ='0' order by ReinsCode";
    }else 
    if(FinalReinsCode != null && !FinalReinsCode.trim().equals("") && FieldName.trim().equals("FinalReinsCode"))
    {
      strWherePart = strWherePart + " AND ReinsCode like '%" + FinalReinsCode + "%' and ReinsKind ='0' order by ReinsCode";
    }else
    if(PayCode != null && !PayCode.trim().equals("") && FieldName.trim().equals("PayCode"))
    {
      strWherePart = strWherePart + " AND ReinsCode like '%" + PayCode + "%' and ReinsKind ='0' order by ReinsCode";
    }else
    {
       strWherePart = strWherePart  +" and ReinsKind ='0' order by ReinsCode";
    } 
    
   result = blPrpDReinsFacade.findByConditions(strWherePart);
   System.out.println("<s:text name='undwrt.pages.undwrtDeal.playingQueryDividePolicyReceiver'/>"+strWherePart);
  }
  catch(UserException ue)
  {
    request.getRequestDispatcher("/common/Message.jsp?content=<s:text name='undwrt.CommonDealList.queryDividePolicyerCodeFail'/>!" + ue.getErrorMessage()).forward(request,response);
  }
  catch(Exception e)
  {
    request.getRequestDispatcher("/common/Message.jsp?content=<s:text name='undwrt.CommonDealList.queryDividePolicyerCodeFail'/>!" + e.toString()).forward(request,response);
  }
  
%>  	
 <%
    String intIndex = request.getParameter("Index");
    int count;
 %>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>

 <html>
  <head>
    <title class=listtitle><s:text name='undwrt.pages.undwrtDeal.dividePolicyerList'/></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
    <!-- 公用函数 -->
    <!-- 本页函数 -->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
	</head>
	<body>
	<form name="fm" method="post" >
	  <table class="common" align="center" cellspacing="1" cellpadding="5">
	    <tr>
	       <input type="hidden" name="ReinsCode" class="readonly"  value="<%=ReinsCode%>">
	       <input type="hidden" name="Index" class="readonly"  value="<%=intIndex%>">
	    <tr>	    
      <tr class=listtitle>
        <td><s:text name='undwrt.pages.undwrtDeal.chooseDividePolicyer'/></td>
      </tr>
      				 
      <tr>
         
          <td class="input" width="350px">
	     <select name="SelReinsCode" class=code size="10"  ONDBLCLICK="selectReins(<%=intIndex%>)">
<%
       try
       {  
          for(Iterator iterator=result.iterator();iterator.hasNext();) 
          {
           PrpDReinsDto prpDReinsDto = new PrpDReinsDto();     
           prpDReinsDto = (PrpDReinsDto)iterator.next();      
%>   
              <option value=<%=prpDReinsDto.getReinsCode().trim()%>>
              <%=prpDReinsDto.getReinsCode().trim()%>-(<%=prpDReinsDto.getShortName().trim()%>)
              (<s:text name='undwrt.pages.undwrtDeal.standardPUL'/>)+<%=prpDReinsDto.getAssessLevel().trim()%>
              (A.M.Best)+<%=prpDReinsDto.getAssessLevel2().trim()%>
              (<s:text name='undwrt.pages.undwrtDeal.Moody'/>)+<%=prpDReinsDto.getAssessLevel3().trim()%>
              (Fitch)+<%=prpDReinsDto.getAssessLevel4().trim()%>
              (<s:text name='undwrt.pages.undwrtDeal.ChinaCreditRating'/>)+<%=prpDReinsDto.getAssessLevel5().trim()%>
              </option>
<%  
          }
          for(Iterator iterator=result.iterator();iterator.hasNext();) 
          {
           PrpDReinsDto prpDReinsDto = new PrpDReinsDto();
           prpDReinsDto = (PrpDReinsDto)iterator.next();
           
%>  
           <input type="hidden" name="ShortName" class="readonly" readonly value="<%=prpDReinsDto.getShortName().trim()%>"> 
           <input type="hidden" name="assessLevel" class="readonly" readonly value="<%=prpDReinsDto.getAssessLevel().trim()%>">  
           <input type="hidden" name="assessLevel2" class="readonly" readonly value="<%=prpDReinsDto.getAssessLevel2().trim()%>">  
           <input type="hidden" name="assessLevel3" class="readonly" readonly value="<%=prpDReinsDto.getAssessLevel3().trim()%>">  
           <input type="hidden" name="assessLevel4" class="readonly" readonly value="<%=prpDReinsDto.getAssessLevel4().trim()%>">  
           <input type="hidden" name="assessLevel5" class="readonly" readonly value="<%=prpDReinsDto.getAssessLevel5().trim()%>">  
           <input type="hidden" name="reinsType"    class="readonly" readonly value="<%=prpDReinsDto.getReinsType().trim()%>">          
<%  
          }
        }
       
        catch(Exception e)
        {    
          request.getRequestDispatcher("/common/Message.jsp?content=<s:text name='undwrt.CommonDealList.dividePolicyerQueryDealFail'/>!").forward(request,response);
        }
%>
      </tr>
      <tr>
          <td>*<s:text name='undwrt.CommonDealList.doubleClickChooseQuitWindow'/>*</td>
      </tr>	   
     </table>
    </form>
   <body>
 </html>