<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>BOC</title>
<link href="${ctx}/pages/style/mian_demo.css" rel="stylesheet"	type="text/css" />
<%@ include file="/widgets/loading/loading.jsp"%>
<script type="text/javascript" src="${ctx}/pages/js/anylinkvertical.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/yahoo.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/event.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/dom.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/logger.js"></script>
<script type="text/javascript"
	src="${ctx}/pages/logo/js/treeview-debug.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/changestyle.js"></script>
<script type="text/javascript" src="${ctx}/pages/logo/js/date.js"></script>
</head>
<body class="main">
 <table width="100%" height="100%" border="0" cellpadding="7" cellspacing="0" >
  <tr> 
    <td width="233" align="middle" bgcolor="#f5f3f4" vAlign="top" > 
        <table width="100%" height="100%" border="0" cellpadding="7" cellspacing="0" bgcolor="#f5f3f4">
        	 <tr> 
               <td width="200" align="middle" vAlign="top" > 
         			<s:property value="menuContent" escape="false" />
         			</td>
	         </tr>
        </table>
    </td>
	</tr>
  </table>

</body>
</html>
