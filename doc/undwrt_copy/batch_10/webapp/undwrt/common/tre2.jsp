<%--
***************************************************************************
* Description: 菜单
* Author     : luyang
* CreateDate:  2004-12-22 14:53
* UpdateLog：  Name       Date            Reason/Contents
****************************************************************************
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig" %>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>


<html>
 <head>
    <link href="/undwrt/css/Standard.css" rel="stylesheet" type="text/css">
    <!-- JavaScript脚本-->
    <script languge=JavaScript>
      function showMenu(divID)
      {
        if (divID.style.display == "")
          divID.style.display="none";
        else
          divID.style.display="";
      }
      function loadForm()
      {
        var i;
        for(i=0;i<document.getElementsByTagName("A").length;i++)
        {
          document.getElementsByTagName("A")[i].onclick = clickme;
        }
      }

      function clickme()
      {
    	  parent.parent.fraTitle.setTitle(this.innerText);
    	  parent.parent.fraSet.cols = "180,*,0%";
        
      }

      function relogon()
      {
      	//parent.fraSet.cols = "0%,*,0%";
      	parent.parent.location="/undwrt/index.jsp";
      }
      function showTaskMessage()
      {
        parent.fraInterface.action="/undwrt/taskMessage.do";
        parent.fm.target="fraInterface";
        alert(22);
        parent.fm.submit();
      }

    </script>
           <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
        </head>
        <body bgcolor="EFF1FE" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
         <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
           <td > 
             <table width="180" height="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="180" height="100%" class="menu">
                  <IFRAME width=180 height=100% src="${ctx}/undwrt/common/processUtiMenu.do?actionType=showMenu&taskCode=showMenu&menuStyle=simple">
                  </IFRAME>                  
                </td>
               <td></td>
              </tr>
             </table>
           </td>
          </tr>
         </table>
        </body>
      </html>
