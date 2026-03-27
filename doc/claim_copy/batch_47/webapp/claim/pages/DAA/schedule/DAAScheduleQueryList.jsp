<%--
****************************************************************************
* DESC       ：调度查勘内容(sched)
* AUTHOR     ：
* CREATEDATE ：2004-08-04
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
								zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ page import = "java.util.Calendar"%>
<%@ page import="com.sinosoft.claim.dto.custom.UserDto" %>
<%@ page import = "com.sinosoft.sysframework.common.datatype.DateTime"%>

<%
  //得到本周周一与周日的日期
  //Date date = new Date();
  //String strMonday = ""; //date.getMondayOFWeek();
  //String strSunday = ""; //date.getSundayOFWeek();
  
  String strSunday =DateTime.current().toString();
  String strMonday = new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).addDay(-7).toString() ;
  UserDto user   = (UserDto)request.getSession().getAttribute("user"); 
%>
<script>
   function submitForm()
    {
      fm.searchFlag.value="true";
	  fm.pageNo.value="1";//查询後页面设为1
      fm.submit();//提交
    }
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
</script>
<%


%>
<html:html locale="true">

<head>
    <app:css />
    <STYLE>BODY {
                 SCROLLBAR-FACE-COLOR:#EFFAFF;
                 SCROLLBAR-HIGHLIGHT-COLOR:#4D9AC4;
                 SCROLLBAR-SHADOW-COLOR:#4D9AC4;
                 SCROLLBAR-3DLIGHT-COLOR:#EFFAFF;
                 SCROLLBAR-ARROW-COLOR:#EFFAFF;
                 SCROLLBAR-TRACK-COLOR:#EFFAFF;
                 SCROLLBAR-DARKSHADOW-COLOR:#EFFAFF;
                }
                </STYLE>  
  <title>
    <s:text name="title.scheduleBeforeEdit.schedulingTaskList" /><%--调度任务清单 --%>
  </title>
  <script src="/claim/common/js/showpage.js"> </script>
   <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  </script>
  
  <html:base/>
</head>

<body >

<form name="fm" action="/claim/scheduleCheckQuery.do"  method="post" onSubmit="return validateForm(this);">
  
    <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
	  <tr> 
           
            <td class="formtitle" colspan="4"><s:text name="schedule.schedulingInfoQuery" /></td></tr><%--查询调度信息 --%>
      <tr>
        <td width="8%" class='title' style="width:15%"><s:text name="prpLregist.registNo" />:</td><%--报案号 --%>
        <td width="25%" class='input' style="width:20%">
        <select class=query name="registNoSign" style="width:40px">
         <option value="=">=</option>
            <option value="=*">=*</option>
           
          </select> 
        <input type=text name="registNo" class="input" style="width:140px">
        </td>
        <td width="9%" class='title' style="width:15%"><s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFOperatorCode" /></td><%--调度员: --%>
       <td width="28%" class='input'  style="width:20%">
          <input type=text name="handlerCode" class="codecode"  style="width:100px" title="分案員" value=""
                 ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
                 onkeyup= "code_CodeSelect(this, 'handerCode','0,1','Y');"      >
            <input type=text name="handlerName" readonly class="codecode"  style="width:30%" title="分案員" value=""
                 ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
                 onkeyup= "code_CodeSelect(this, 'handerCode','-1,0','Y','N');"> 
        </td>
        <td width="1%" rowspan=5 class='button' style="width:10%">&nbsp;</td> 
      </tr>
      
      <tr> 
     
      <td class='title' ><s:text name="check.schedulObject" />: </td><%--调度对象 --%>
       <td class='input' >
       <input type=text class="codecode" name="scheduleObjectID" 
                    style="width:35%" title="具體單位" value=""
                    ondblclick="code_CodeSelect(this, 'prpdcompany','0,1','Y');"
                    onkeyup= "code_CodeSelect(this, 'prpdcompany','0,1','Y');"  >
       <input type=text class="codecode"  name="scheduleObjectName"  readonly  title="具體單位" style="width:60%" value=""
                    ondblclick="code_CodeSelect(this, 'prpdcompany','-1,0','Y','N');"
                    onkeyup= "code_CodeSelect(this, 'prpdcompany','-1,0','Y','N');">
        </td>
      
        <td class='title'><s:text name="certainLoss.thirdCarLoss.prpLthirdPartyLicenseNo" /></td><%--车牌号码: --%>
        <td class='input'>
        <select class=query name="prpLscheduleItemLicenseNoSign" style="width:40px">
            <option value="=">=</option>
            <%--<option value="*">*</option>--%>
            
          </select> 
        <input name="prpLscheduleItemLicenseNo" class="input" style="width:140px">
       </td>
      </tr>
       <tr>
	     <td class='title' ><s:text name="schedule.schedulingType" />:</td><%--调度类型 --%>
       <td class='input'>
        <select name ="scheduleType" style="width:150px">
        <option value="sched" selected> <s:text name="check.mentHereunde" /></option><%--查勘 --%>
        <option value="schel"> <s:text name="compensate.fee" /></option><%--定损 --%>
        </select>
      </td>
        
       
        <td width="8%" class='title' style="width:15%"><s:text name="db.view_larrearage.insuredname" />:</td><%--被保险人名称 --%>
        <td width="21%" class='input' style="width:25%">
        <select class=query name="InsuredNameSign" style="width:40px">
         <option value="=">=</option>
            <option value="=*">=*</option>
           
          </select> 
        <input type=text name="InsuredName" class="input" style="width:140px">
        </td>
      
      </tr>


      <tr>
      <td class='title'><s:text name="manage.startTime" />:</td><%--开始时间 --%>
       <td class='input'>
        <input name="startDate" class="input" style="width:120px" value=<%=strMonday%>>
        <img src="/claim/images/bgcalendar.gif" align="middle" style='cursor: hand' onClick="TogglePopupCalendarWindow('document.fm.startDate', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')">      </td>
      <td class='title' ><s:text name="manage.endTime" />: </td><%--结束时间 --%>
      <td class='input' >
        <input  name="endDate" class="input" style="width:120px" value = <%=strSunday%>>
        <img src="/claim/images/bgcalendar.gif" align="middle" style='cursor: hand' onClick="TogglePopupCalendarWindow('document.fm.endDate', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()-15) %>', '<%=(new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).getYear()+2) %>')">      </td>
    </tr>
    
    <tr>
     <td class='title' ><s:text name="schedule.schedulingCondite" />:</td><%--调度状态 --%>
        <td class='input' colspan="3">
        <input type=checkbox name="checkFlag0" ><s:text name="common.status.newSchedule" /><%--新调度 --%>
        <%//<input type=checkbox name="checkFlag2" >正处理%>
        <input type=checkbox name="checkFlag4" ><s:text name="common.status.submited" /><%--已提交 --%>
      
        </td>
        
         
    </tr>
    <tr>
    <td class="title" style="color:red" colspan="4">
   <s:text name="prompt.schedule.query1" /><br><%-- "="符号，必须精确查询。 --%>
    <s:text name="prompt.schedule.query2" /><%--"=*"符号，前匹配後模糊的查询。 --%>
    </td>
  </tr>
    </table>
    <div align="center">
      <input type="hidden" name="editType" value="QUERY">
      <input type="hidden" name=comcode value="<%=user.getComCode()%>">
      <span class="button" style="width:10%">
      <input name="submit1" id="button" type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
      </span>
      <input name="searchFlag" type="hidden" id="searchFlag">
    </div>
  
 <table  class="common" cellpadding="5" cellspacing="1" >
 <tr>

           <td colspan="8" class="formtitle"><s:text name="schedule.schedulSurvey" /></td><%--调度查勘任务清单 --%>

          </tr>
     <tr>
     <td class="centertitle"  style="width:10%"><s:text name="regist.prpLregist.status" /></td><%--状态 --%>
        <td class="centertitle" style="width:18%"><s:text name="db.prpLclaimApprov.registNo" /></td><%--报案号 --%>
        <td class="centertitle" style="width:10%"><s:text name="check.schedulInfo" /></td><%--调度信息 --%>
        <td class="centertitle" style="width:15%"><s:text name="certainLoss.prpLscheduleMainWF.attemperDate" /></td><%--调度时间 --%>
        <td class="centertitle" style="width:10%"><s:text name="check.schedulOpera" /></td><%--调度操作员 --%>
        <td class="centertitle" style="width:10%"><s:text name="check.surveyOperator" /></td><%--查勘操作员 --%>
     </tr>

  <%int index=0;%>
     <logic:notEmpty  name="prpLscheduleMainWFDto"  property="scheduleList" > 
     <logic:iterate id="prpLcheckTaskList"  name="prpLscheduleMainWFDto"  property="scheduleList">  
<%
          if(index %2== 0)
               out.print("<tr class=listodd>");
          else 
               out.print("<tr class=listeven>");
%>

        <td align="center">
        <logic:equal name="prpLcheckTaskList" property="checkFlag" value='0' >
         <s:text name="common.status.newSchedule" /><%--新调度 --%>
        </logic:equal>
     
        <logic:equal name="prpLcheckTaskList" property="checkFlag" value='4' >
         <s:text name="common.status.submited" /><%--已提交 --%>
        </logic:equal>
               
        </td>
        <%// 报案受理号 %>
         <td > 
        <a href="/claim/scheduleFinishQueryList.do?prpLscheduleMainWFRegistNo=<bean:write name='prpLcheckTaskList' property='registNo'/>&editType=SHOW&prpLscheduleMainWFScheduleID=1&scheduleType=schel"> <bean:write name="prpLcheckTaskList" property="registNo"/>
        </a></td>       
        <td ><bean:write name="prpLcheckTaskList" property="checkInfo"/></td>        <%// 金银牌客户标志   %>
        <td ><bean:write name="prpLcheckTaskList" property="inputDate"/></td>         <%// 是否被调度使用   %>      
        <td ><bean:write name="prpLcheckTaskList" property="operatorName"/></td>            <%// 预约查勘(定损)   %>
        <td ><bean:write name="prpLcheckTaskList" property="nextHandlerName"/></td>            <%// 承保险类 (DAA)   %>
    
       </tr>
<%        index++;%>
      </logic:iterate>
      </logic:notEmpty>
    </table> 
  </tr>
    <input type="hidden" name="editType" value="QUERY"> 
    <input type="hidden" name="scheduleType" value="sched">   
    
  <table class="common" cellpadding="4" cellspacing="20"> 
    <tr>
    </tr>
    <tr>
    
    </tr>
  <table>
    
  </table>
</form>
</body>


</html:html>