<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>单证系统</title>
</head>
<frameset id="mainFrame"  rows="83,*" cols="1113" framespacing="0" frameborder="no" border="0"  onbeforeunload="quit()" scrolling="yes">
  <frame src="${ctx}/common/Title.jsp" name="head" scrolling="no" id="head" noresize="noresize" />
  <frameset id="menuFrame"  rows="100%*" cols="18%,*" framespacing="0" frameborder="no" border="0"  onbeforeunload="quit()" scrolling="yes">
	  <frame src="${ctx}/common/showMenu.do" name="main" scrolling="auto" id="main" />
	  <frame src="${ctx}/common/Welcome.html" name="page" scrolling="auto" id="page" width="100%" height="100%"/>
 </frameset>
</frameset>
<noframes>
<body>
<noframes>您的浏览器不支持框架!</noframes>
</body>
<script language="javascript">
  function quit(){
      event.returnValue="是否确定要关闭页面?"; 
  }
</script>
</html>