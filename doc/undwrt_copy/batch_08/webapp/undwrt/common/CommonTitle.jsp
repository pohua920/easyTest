<!--***************************************************************************
* Description: 标题页面
* Author     : luyang
* CreateDate:  2004-12-22 14:52
* UpdateLog：  Name       Date            Reason/Contents
*
****************************************************************************-->

<%--	@page import="com.sinosoft.utility.string.Str"%>
<%@page import="com.sinosoft.utility.string.ChgDate"  --%>
<%@page import="com.sinosoft.utility.string.Date" %>
<%@ page contentType="text/html; charset=gbk"%>
<%
  Date today = new Date();
  today.setDateDelimiter("/");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<!-- 页面样式  -->
		<link rel='stylesheet' type='text/css' href='/undwrt/css/Standard.css'>
		<script language="JavaScript">
			function showHideFrame()
		  {
		    try
		    {
  		    	if(parent.fraSet.cols==	"0%,*,0")
  		    	{
  					parent.fraSet.cols = "180,*,0";
  					spanMenuPowerImage.innerText = "隱藏功能菜單";
  		        }
  		        else if(parent.fraSet.cols=="180,*,0")
  		        {
  		         	parent.fraSet.cols = "0%,*,0";
  		         	spanMenuPowerImage.innerText = "顯示功能菜單";
  		        }
  		      	else if(parent.fraSet.cols=="0%,*,180"){
		        	parent.fraSet.cols = "180,*,180";
		        	spanMenuPowerImage.innerText = "隱藏功能菜單";
		        }
  		        else if(parent.fraSet.cols=="180,*,180"){
  		        	parent.fraSet.cols = "0%,*,180";
  		         	spanMenuPowerImage.innerText = "顯示功能菜單";
  		        }
  		     }
	         catch(re){}
		  }
		  
		   function menuSwitch(menuSwitchInput)
		    {
		        if(menuSwitchInput.value=="close")
		        {
		             menuSwitchInput.src="/undwrt/images/open-menu.gif";
		             menuSwitchInput.value="open";
		             parent.fraSet.cols = "0,*";
		            return;
		        }
		        if(menuSwitchInput.value=="open")
		        {
		             menuSwitchInput.src="/undwrt/images/close-menu.gif";
		             menuSwitchInput.value="close";
		             parent.fraSet.cols = "180,*";
		             return;
		        }
		    }
		  
		  //装载窗口
		  function loadForm()
		  {
		  }
		  //设置操作员信息
		  function setTitleInfo(Operator,ComName)
		  {
		    try
		    {
    		  spanOperator.innerHTML = Operator;
    		  spanComName.innerHTML = ComName;
  		  }
	      catch(re){}
		  }
		  function setTitle(title)
    	  {
            textfield.value=title;
          }
		  function setCommand(text){
			    document.all("command").innerText="　"+text;
			}
		  function showChat(){
			   try
			      {
			      	var left = parent.fraSet.cols.substring(0,parent.fraSet.cols.lastIndexOf(",") + 1);
			      	var right = parent.fraSet.cols.substring(parent.fraSet.cols.lastIndexOf(",") + 1);
			      	if(right == "0")
			        {
			          parent.fraSet.cols = left + "180";
			        }
			        else if(right == "180")
			        {
			          parent.fraSet.cols = left + "0";
			        }
			      }
			      catch(re)
			      {}  
			  }
		</script>
	</head>
<%
  String strUserCode = (String)session.getAttribute("userCode");
  String strUserName = (String)session.getAttribute("userName");
  String strUserPassword = (String)session.getAttribute("password");
  String strComCode = (String)session.getAttribute("comCode");
  String strComName = (String)session.getAttribute("comName");
  String strGradeCode = (String)session.getAttribute("gradeCodes");
%>
  <body onLoad="loadForm()" leftmargin="0" topmargin="0" marginwidth="0" marginhigh="0" style="background:url(/undwrt/images/body_bg.gif) top repeat-x; padding-top:3px;">
   
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="59" align="center" valign="bottom">
        	<table width="95%" border="0" cellspacing="0" cellpadding="0">
      		   <tr>
            		<td style="padding:0px 0 0px 9px;">
                    	<img src="/undwrt/images/logo.png" height="63px" width="412px"></td>
            		<td align="right" valign="top" width="100%">
            		<table width="90%" border="0" cellspacing="0" cellpadding="0" style="background:url(/undwrt/images/top_c.gif) repeat-x;">
              	    <tr>
                    <td align="left" width="5%"><img src="/undwrt/images/top_l.gif"/></td>
                     <td align="right" width="45%"><span id="spanOperator"><%=strUserName%></span></td>
                    <td align="right" width="25%"><%=strComName%></td>
                    <td align="right" width="20%"><%=today.get(Date.YEAR)-1911%>-<%=today.get(Date.MONTH)%>-<%=today.get(Date.DATE)%></td>
                    <td align="right" width="5%"><img src="/undwrt/images/top_r.gif"/></td>
                    </tr>
               </table>
           </td>
       </tr>
	</table>
	</td>
	</tr>
	
	<tr>
        <td height="26" align="center">
        	<table width="90%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	          	<td width="10%" class="font1">
	          		<span id=spanMenuPowerImage onClick="showHideFrame();" style="cursor: pointer"><nobr>隱藏功能菜單</nobr></span>
	          		<%--
	          			<input name="menuSwitch" type="image" onclick="menuSwitch(this)" value="close" src="../images/close-menu.gif" align="middle">
	          		--%>	      			
	      		</td>
	            <td class="font1" align="left">
	            	<nobr>您當前所處的位置</nobr> 
	            	<input type="text" name="textfield" id="command" class="readonlytop"/>
	            </td>
	             <td align="right"  style="display:block">
		      		<span style="margin-right: 2px;;"><a href="javascript:void(0)" onclick="showChat();" style="color:white">即時通訊</a> </span>
		       </td>
	           </tr>
        	</table>
        </td>
      </tr>
	</table>
    </body>
</html>







