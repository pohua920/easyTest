<%--
****************************************************************************
* DESC       ：操作成功提示页面
* AUTHOR     ：zhaoning
* CREATEDATE ：2009-06-11
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>


<!-- 滚动条样式定义 -->
<html locale="true">
<head>
<jsp:include page="/common/meta_css.jsp" />
<jsp:include page="/common/meta_js.jsp" />
</head>
<!--通用函数-->
<script src="/undwrt/common/js/Common.js"></script>
<script language="javascript">
	//锁定键盘后退与IE上后退的键
	function onKeyDown() 
	{
		if( (event.altKey) || ((event.keyCode == 8) && 
			(event.srcElement.type != "text" && 
			event.srcElement.type != "textarea" && 
			event.srcElement.type != "password")) || 
			((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) || 
			(event.keyCode == 116) ){
		    event.keyCode = 0; 
		    event.returnValue = false; 
		  }
	}
	document.onkeydown = onKeyDown; 
	function stop(){   //这个是禁用鼠标右键 
	  return false; 
	}
	document.oncontextmenu=stop; 
</script>
<body >
<form name ="fm">
  <table align="center" class=common >
    <tr class=common>
       <td align="center" height=70px>
            <!-- add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin-->	
           <img src="/undwrt/common/images/success.gif" align="absmiddle"><%=request.getAttribute("content")%>  
           <a href="${pageContext.request.contextPath}/undwrt/index.jsp">跳到登陆界面</a> 
           <!-- add by songzhewen 20170328 mantis5446: 验收问题：各系统密碼到期用舊密碼登入後直接跳密碼修改begin-->	
       </td>
    </tr>
  </table>
</form>
</body>
</html>
