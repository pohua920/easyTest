<%--
****************************************************************************
* DESC       ：检查是否占号时调用此页面
* AUTHOR     ：理赔项目组
* CREATEDATE ：2004-09-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html; charset=GBK" %>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*" %>
<%@page import="com.sinosoft.claim.dto.domain.SwfLogDto" %>
<%@page import="com.sinosoft.claim.dto.custom.*" %>
<%@page import="com.sinosoft.sysframework.common.util.*" %>
<%@page import="com.sinosoft.sysframework.common.datatype.*" %>
<%@page import="com.sinosoft.sysframework.exceptionlog.*" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>

<%
 

        UIWorkFlowAction  uiWorkFlowAction = new UIWorkFlowAction();
        String flowID = request.getParameter("flowID");
        String logNo= request.getParameter("logNo");
        String bussinessNo=request.getParameter("bussinessNo");
        String bussinessOut=request.getParameter("bussinessOut");
        String nodeStatus=request.getParameter("nodeStatus");
        String nodeType=request.getParameter("nodeType");
        boolean retHoldNode=false;
        boolean executeResult=true;
        UserDto user = (UserDto)session.getAttribute("user");
        String userCode = user.getUserCode();
        String userName = user.getUserName();
        SwfLogDto swfLogDto = new SwfLogDto();
        swfLogDto = null; 
         
       try{
           swfLogDto = uiWorkFlowAction.holdNode(flowID,logNo,userCode,userName);
          } catch (SecurityException ex)
       {
     
       } catch (Exception ex){
      out.println("捕捉到了异常");
      ex.printStackTrace() ;
    } finally {   
    
    }
        
   
%>
 

<html>
<title><s:text name="title.workflow.checkOaQuestion" />
	<%--检查工作流分配占号的问题 --%></title>
    
<form name='fm' method='POST' >

   <input type="hidden" name=checkResult value='<%=swfLogDto.getHoldNode()%>'>
   <input type="hidden" name=userCode value='<%=swfLogDto.getHandlerCode()%>'>
   <input type="hidden" name=userName value='<%=swfLogDto.getHandlerName()%>'>
   <input type="hidden" name=executeResult value='<%=executeResult%>'>
   <input type="hidden" name="userLastAction" class="common" value="">
   <input type="hidden" name="editType" class="common" value="">
   <input type="hidden" name="RegistNo" class="common" value="">
   <input type="hidden" name="ClaimNo" class="common" value="">
 
   //占号和後续处理,业务处理，没办法因为时间的不同步的问题
  <script language='javascript'>
    var retResult=false;
    var nodeType = <%=nodeType%>
    var bussinessNo=<%=bussinessNo%>
     var bussinessOut=<%=bussinessOut%>
     var nodeStatus=<%=nodeStatus%>;
      
try {
           blnRet =fm.checkResult.value;
    if (retResult == "false") {
  	          alert ("该案件已经被代码:'"+otherUserCode+"',名称:'"+otherUserName+"'的用户所占用,请选择其它案件！")
    } else {}

            
} catch (E) {}
    </script>
   
  
</form>
</html>

